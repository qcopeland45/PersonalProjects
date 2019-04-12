import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.math.BigInteger;
import java.net.ServerSocket;
import java.net.Socket;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.SecureRandom;
import java.security.cert.Certificate;
import java.util.Arrays;
import javax.crypto.Cipher;
import javax.crypto.Mac;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;


public class Server {

	
	//member variables
	private byte[] nonce, signedKey;
	private static SecretKeySpec serverEncrypt, clientEncrypt, serverMAC, clientMAC;
	private static IvParameterSpec serverIV, clientIV;
	private BigInteger DH_PrivateKey, DH_PublicKey;
	private PrivateKey RSA_PrivateKey;
	private Certificate certificate;
	private Socket socket;
	private ServerSocket ss;
	
	//constructor
	public Server() {
		try {
			SecureRandom random = new SecureRandom();
			this.certificate = TLS.createCertificate("CASignedServerCertificate.pem");
			this.RSA_PrivateKey = TLS.generatePrivateKey("serverPrivateKey.der");
			this.DH_PrivateKey = new BigInteger(Integer.toString(random.nextInt()));
			this.DH_PublicKey = TLS.calculatePublicKey(DH_PrivateKey);
			this.signedKey = TLS.signKey(RSA_PrivateKey, DH_PublicKey);
			this.ss = new ServerSocket(8080);
			this.socket = ss.accept();
		}
		catch (Exception e){
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		
		try {
			System.out.println("Server running..."); 
			Server server = new Server();
		
			InputStream is = server.socket.getInputStream();
			OutputStream os = server.socket.getOutputStream();
			ObjectInputStream objIn = new ObjectInputStream(is);
			ObjectOutputStream objOut = new ObjectOutputStream(os);
			ByteArrayOutputStream byteStream = new ByteArrayOutputStream();
			
			//recieving nonce from client
			byte[] nonce = (byte[]) objIn.readObject();
			byteStream.write(nonce);
			
			//sending certificate and DH keys to client 
			objOut.writeObject(server.certificate);
			objOut.writeObject(server.DH_PublicKey);
			objOut.writeObject(server.signedKey);
			
			//writing to byte array
			byteStream.write(server.certificate.getEncoded());
			byteStream.write(server.DH_PublicKey.toByteArray());
			byteStream.write(server.signedKey);
			
			
			//receieving certificate and DH keys from client 
			Certificate certificate = (Certificate) objIn.readObject();
			BigInteger DHKey = (BigInteger) objIn.readObject();
			byte[] privateKey = (byte[]) objIn.readObject();
			
			//writing bytes  from client to input byte array
			byteStream.write(certificate.getEncoded());
			byteStream.write(DHKey.toByteArray());
			byteStream.write(privateKey);
			
			//need to verify the client with its certificate
			boolean isVeryfied = TLS.verifySignedKey(certificate, DHKey, privateKey);
			//assert(isVeryfied); // do if statement refactoring 
			
			if (!isVeryfied) {
				System.out.println("Server and Client unable to connect, quiting program..");
				System.exit(-1);
			}
			
			BigInteger secretKey = TLS.calculateSharedSecret(DHKey, server.DH_PrivateKey);
			//System.out.println("SERVER SECRET KEY " + secretKey);
			
			//create MAC keys
			makeSecretKeys(nonce, secretKey);
			
			byte[] macMessage = macMessage(byteStream.toByteArray(), serverMAC);
			objOut.writeObject(macMessage);
			byte[] cliMsgLog = (byte[]) objIn.readObject(); //getting message from client
			byteStream.writeBytes(macMessage);
			byte[] myMsgLog = macMessage(byteStream.toByteArray(), clientMAC);
			
			if (Arrays.equals(cliMsgLog, myMsgLog)) {
				System.out.println("MAC CHECKED OUT");
			}
			else {
				System.out.println("MAC NOT THE SAME");
			}
			
			// reading in large file
			FileInputStream fis = new FileInputStream("recFile.pdf");
			byte[] fileBytes = fis.readAllBytes();
			byte[] macFileBytes = macMessage(fileBytes, serverEncrypt);
			byte[] concatArr = TLS.concatArray(fileBytes, macFileBytes);
			//System.out.println("COMBINED ARRAY " + Arrays.toString(concatArr));
			
//			///testing code only
//			String s = "From server";
//			byte[] testArrFile = s.getBytes();
//			byte[] macTestFileBytes = macMessage(testArrFile, serverEncrypt);
//			byte[] concatArr = TLS.concatArray(testArrFile, macTestFileBytes);
			
			
			Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
			cipher.init(Cipher.ENCRYPT_MODE, serverEncrypt, serverIV);
			byte[] encrypted = cipher.doFinal(concatArr);
			objOut.writeObject(encrypted);
			
			
			byte[] msgFromClient =  (byte[]) objIn.readObject();
			cipher.init(Cipher.DECRYPT_MODE, clientEncrypt, clientIV);
			byte[] msgFromCliDecryptedcrypted = cipher.doFinal(msgFromClient);
			byte[] decrypted = Arrays.copyOf(msgFromCliDecryptedcrypted, msgFromCliDecryptedcrypted.length - 32);
			String successConfirm = new String(decrypted);
			System.out.println("received from client: " + successConfirm);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		
		
	}
	
	
	public static void makeSecretKeys(byte[] clientNonce, BigInteger sharedSecret) throws NoSuchAlgorithmException, InvalidKeyException {
        byte[] ss = sharedSecret.toByteArray();

        Mac sha256HMAC = Mac.getInstance("HmacSHA256");
        SecretKeySpec keySpec = new SecretKeySpec(clientNonce, "HmacSHA256");
        sha256HMAC.init(keySpec);
        byte[] prk = sha256HMAC.doFinal(ss);

        serverEncrypt = new SecretKeySpec(TLS.hdkfExpand(prk, "server encrypt"), "AES");
        clientEncrypt = new SecretKeySpec(TLS.hdkfExpand(serverEncrypt.getEncoded(), "client encrypt"), "AES");

        serverMAC = new SecretKeySpec(TLS.hdkfExpand(clientEncrypt.getEncoded(), "server MAC"), "AES");
        clientMAC = new SecretKeySpec(TLS.hdkfExpand(serverMAC.getEncoded(), "client MAC"), "AES");

        serverIV = new IvParameterSpec(TLS.hdkfExpand(clientMAC.getEncoded(), "server IV"));
        clientIV = new IvParameterSpec(TLS.hdkfExpand(serverIV.getIV(), "client IV"));

    }
	
	
	public static byte[] macMessage(byte[] message, SecretKeySpec spec) throws InvalidKeyException, NoSuchAlgorithmException {
		
		Mac sha256HMAC = Mac.getInstance("HmacSHA256");
        sha256HMAC.init(spec);
        byte[] msg = sha256HMAC.doFinal(message);
		return msg;
		
		
		
	}
	
	
}
