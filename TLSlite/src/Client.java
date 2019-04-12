import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.math.BigInteger;
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


public class Client {

	
	//member variables
	private byte[] nonce, signedKey;
	private static SecretKeySpec serverEncrypt, clientEncrypt, serverMAC, clientMAC;
	private static IvParameterSpec serverIV, clientIV;
	private BigInteger DH_PrivateKey, DH_PublicKey;
	private PrivateKey RSA_PrivateKey;
	private Certificate certificate;
	private Socket socket;

	//constructor
	public Client() {
		try {
			this.nonce = TLS.getNonce();
			SecureRandom random = new SecureRandom();
			this.certificate = TLS.createCertificate("CASignedClientCertificate.pem");
			this.RSA_PrivateKey = TLS.generatePrivateKey("clientPrivateKey.der");
			this.DH_PrivateKey = new BigInteger(Integer.toString(random.nextInt()));
			this.DH_PublicKey = TLS.calculatePublicKey(DH_PrivateKey);
			this.signedKey = TLS.signKey(RSA_PrivateKey, DH_PublicKey);
			this.socket = new Socket("127.0.0.1", 8080);
		}
		catch (Exception e){
			e.printStackTrace();
		}
	}
	
	
	
	public static void main(String[] args) {
		
		try {
			Client client = new Client();
			System.out.println("INSIDE CLIENT");
		
			OutputStream os = client.socket.getOutputStream(); 
			InputStream is = client.socket.getInputStream();
			ObjectOutputStream objOut = new ObjectOutputStream(os);
			ObjectInputStream objIn = new ObjectInputStream(is);
			ByteArrayOutputStream byteStream = new ByteArrayOutputStream();
			
			objOut.writeObject(client.nonce);
			byteStream.write(client.nonce);
			
			//receieving certificate and DH keys from server 
			Certificate cert = (Certificate) objIn.readObject();
			BigInteger DHkey = (BigInteger) objIn.readObject();
			byte[] DHSignedKey = (byte[]) objIn.readObject();
			
			
			//writing bytes  from server to input byte array
			byteStream.write(cert.getEncoded());
			byteStream.write(DHkey.toByteArray());
			byteStream.write(DHSignedKey);
			
			
			//send stuff over the wire to the server
			objOut.writeObject(client.certificate);
			objOut.writeObject(client.DH_PublicKey);
			objOut.writeObject(client.signedKey);
			
			//writing to bytes to output byte array
			byteStream.write(client.certificate.getEncoded());
			byteStream.write(client.DH_PublicKey.toByteArray());
			byteStream.write(client.signedKey);
			
			
			//need to verify the server with its certificate
			boolean isVeryfied = TLS.verifySignedKey(cert, DHkey, DHSignedKey);
			//assert(isVeryfied); // do if statement refactoring 
			
			if (!isVeryfied) {
				System.out.println("Server and Client unable to connect, quiting program..");
				System.exit(-1);
			}
			
			BigInteger secretKey = TLS.calculateSharedSecret(DHkey, client.DH_PrivateKey);
//			System.out.println("CLIENT SECRET KEY " + secretKey); 
			
			//create MAC keys
			makeSecretKeys(client.nonce, secretKey);
			
			byte[] serverMsgLog = (byte[]) objIn.readObject(); //getting message from server
			byte[] myMsgLog = macMessage(byteStream.toByteArray(), serverMAC);
			
			if (Arrays.equals(serverMsgLog, myMsgLog)) {
				System.out.println("MAC CHECKED OUT");
			}
			else {
				System.out.println("MAC NOT THE SAME");
			}
			
			byteStream.writeBytes(serverMsgLog);
			byte[] myMacMsg = macMessage(byteStream.toByteArray(), clientMAC);
			objOut.writeObject(myMacMsg);
			
			byte[] encrypted = (byte[]) objIn.readObject();
			Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
			cipher.init(Cipher.DECRYPT_MODE, serverEncrypt, serverIV);
			byte[] decryptedwithMac = cipher.doFinal(encrypted);
			//get everything up to 32 bits of the file
			byte[] decrypted = Arrays.copyOf(decryptedwithMac, decryptedwithMac.length - 32);
			
			//testing code only 
//			String s = new String(decrypted, 0, decrypted.length-32);
//			System.out.println("Decypted message length " + s);
			
			FileOutputStream fos = new FileOutputStream("testfile2_output.pdf");
			fos.write(decrypted);
			
			
			String msgToSendToServer = "Message to client has been received from Server, Thank you quincy";
			cipher.init(Cipher.ENCRYPT_MODE, clientEncrypt, clientIV);
			byte[] msgBytes = msgToSendToServer.getBytes();
			byte[] macFileBytesToserver = macMessage(msgBytes, clientEncrypt);
			byte[] concatArrToServer = TLS.concat(msgBytes, macFileBytesToserver);
			
			objOut.writeObject(cipher.doFinal(concatArrToServer));
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

