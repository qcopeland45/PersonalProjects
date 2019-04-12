//import java.io.File;
//import java.io.FileInputStream;
//import java.io.FileNotFoundException;
//import java.io.IOException;
//import java.io.InputStream;
//import java.math.BigInteger;
//import java.security.InvalidKeyException;
//import java.security.KeyFactory;
//import java.security.NoSuchAlgorithmException;
//import java.security.PrivateKey;
//import java.security.SecureRandom;
//import java.security.Signature;
//import java.security.SignatureException;
//import java.security.cert.Certificate;
//import java.security.cert.CertificateException;
//import java.security.cert.CertificateFactory;
//import java.security.spec.InvalidKeySpecException;
//import java.security.spec.PKCS8EncodedKeySpec;
//import java.util.Arrays;
//import javax.crypto.Mac;
//import javax.crypto.spec.SecretKeySpec;
//
//
//
//
//public class TlsMain {
//
//	
//	
//	public static byte[] getNonce(String fileName) throws CertificateException, FileNotFoundException {
//		
//		SecureRandom random = new SecureRandom();
//		byte clientNonce[] = new byte[32];
//		random.nextBytes(clientNonce);
//		
////		CertificateFactory certificateFactory = CertificateFactory.getInstance("X.509");
////		
////		InputStream certInputStream = new FileInputStream(fileName);
////		Certificate certificate = certificateFactory.generateCertificate(certInputStream);
//	
//		
//		return clientNonce;
//		
//	}
//	
//	public static Certificate getCert(String fileName) throws FileNotFoundException, CertificateException {
//		
//		
//		CertificateFactory certificateFactory = CertificateFactory.getInstance("X.509");
//		
//		InputStream certInputStream = new FileInputStream(fileName);
//		Certificate certificate = certificateFactory.generateCertificate(certInputStream);
//		
//		
//		return certificate;
//		
//	}
//	
//	
//
//	
//	public static byte[] readDerFiles(String file) throws IOException {
//		
//		InputStream is = new FileInputStream(file);
//		long fileSize = new File(file).length();
//		byte byteArr[] = new byte[(int) fileSize];
//		
//		
//		is.read(byteArr);
//		is.close();
//		return byteArr;
//		
//		
//		
//	}
//	
//	public static PrivateKey getPrivateKey(String fileName) throws NoSuchAlgorithmException, IOException, InvalidKeySpecException {
//		
//		InputStream is = new FileInputStream(fileName);
//		byte[] fileBytes = is.readAllBytes();
//		PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(fileBytes);
//		KeyFactory key = KeyFactory.getInstance("RSA");
//		PrivateKey privateKey = key.generatePrivate(keySpec);
//		
//		return privateKey;
//	}
//	
//
//	public static byte[] signKey(PrivateKey privateKey, BigInteger publicKey) throws NoSuchAlgorithmException, InvalidKeyException, SignatureException {
//		
//		Signature signature = Signature.getInstance("SHA256WithRSA");
//		signature.initSign(privateKey);
//		signature.update(publicKey.toByteArray());
//
//		return signature.sign();
//		
//	}
//	
//	public static boolean verifyCertificate(Certificate certToVerify) {
//		
//		try {
//			Certificate cert = getCert("CAcertificate.pem");
//			certToVerify.verify(cert.getPublicKey());
//		}
//		catch (Exception e) {
//			e.printStackTrace();
//			return false;
//		}
//			 
//		return true;
//		
//	}
//	
//	
//	public static boolean verifySignedKey(Certificate certToVerify, BigInteger publicKey, byte[] signedKey) throws SignatureException, InvalidKeyException, NoSuchAlgorithmException {
//		
//		if (!verifyCertificate(certToVerify))
//			return false;
//			
//		Signature signature = Signature.getInstance("SHA256WithRSA");
//		signature.initVerify(certToVerify);
//		signature.update(publicKey.toByteArray());
//		
//		
//		return signature.verify(signedKey);
//		
//		
//	}
//	
//	public static BigInteger calculatePublicKey(BigInteger P, BigInteger G , BigInteger privateKey) {
//
//		return G.modPow(privateKey, P);
//		
//	}
//	
//	
//	public static BigInteger calculateSharedSecret(BigInteger sharedKey, BigInteger N, BigInteger privateKey) {
//
//		return sharedKey.modPow(privateKey, N);
//	}
//	
//	//helper to concatenate two byte arrays
//	public static byte[] concat(byte[] a, byte[] b) {
//        int lenA = a.length;
//        int lenB = b.length;
//        byte[] c = Arrays.copyOf(a, lenA + lenB);
//        System.arraycopy(b, 0, c, lenA, lenB);
//        return c;
//    }
//	
//	public static byte[] getFirst16(byte[] arr) {
//		
//		byte[] ret = new byte[16];
//		for (int i = 0; i < 16; i++) {
//			ret[i] = arr[i];
//			
//		}
//		return ret;
//		
//	}
//	
//	public static byte[] hkdfExpand(byte[] sharedSecret, String tag) throws NoSuchAlgorithmException, InvalidKeyException {
//
//		Mac mac	= Mac.getInstance("HmacSHA256");
//		byte[] addOn = { 1 };
//		byte[] keyBytes = concat(sharedSecret, addOn);
//		byte[] tagBytes = tag.getBytes();
//		byte[] bytes = concat(keyBytes, tagBytes);
//		SecretKeySpec key = new SecretKeySpec(sharedSecret, "HmacSHA256");
//		mac.init(key);
//		
//		byte[] okm = mac.doFinal(bytes);
//		byte[] first16 = new byte[16];
//		first16 = getFirst16(okm);
//		
//		assert(first16.length == 16); //sanity check 
//		
//		return first16;
//		
//	}
//	
//	//make secret keys 
////	public static void makeSecretKeys(byte[] nonce, BigInteger sharedSecret) throws InvalidKeyException, NoSuchAlgorithmException {
////		
////		
////		byte[] start = sharedSecret.toByteArray();
////		
////		
////		byte[] serverEncrypt = hkdfExpand(prk, "server encrypt");
////		byte[] clientEncrypt = hkdfExpand(serverEncrypt, "client encrypt");
////		byte[] serverMAC = hkdfExpand(clientEncrypt, "server MAC");
////		byte[] clientMAC = hkdfExpand(serverMAC, "client MAC");
////		byte[] serverIV = hkdfExpand(clientMAC, "server IV");
////		byte[] clientIV = hkdfExpand(serverIV, "client IV");
////		
////		
////	}
//	
//	
//	
//	
//	
//	public static void main(String[] args) throws CertificateException, NoSuchAlgorithmException, InvalidKeySpecException, IOException, InvalidKeyException, SignatureException {
//		
//		
//		
//		String serverFile = "CASignedServerCertificate.pem"; // server
//		String clientFile = "CASignedClientCertificate.pem"; // client
//		String serverPrivateKeyFile = "serverPrivateKey.der"; // server
//		String clientPrivateKeyFile = "clientPrivateKey.der";
//		
//		
//		//int temp = random.nextInt();
//		//BigInteger bigRand = new BigInteger(Integer.toString(temp));
//		
//		BigInteger G = new BigInteger("14");
//		BigInteger P = new BigInteger("3231700607131100730033891"
//				+ "392642382824881794124114023911284200975140074"
//				+ "170663435422261968941736356934711790173790970"
//				+ "419175460587320919502885375898618562215321217"
//				+ "541251490177452027023579607823624888424618947"
//				+ "758764110592864609941172324542662252219323054"
//				+ "091903768052423551912567971587011700105805587"
//				+ "7651038861847280257976054903569732561526167081"
//				+ "3393617995413364765591603683178967290731783845"
//				+ "8968063967190097720219416864722587103141133642"
//				+ "9319536193471636533209717077448227988588565369"
//				+ "2086452966360772502689555059283627511211740969"
//				+ "7299806841055435958486658329164213621823107899"
//				+ "0999448652468262416972035911852507045361090559");
//
//		
//		SecureRandom random = new SecureRandom();
//		int x = random.nextInt();
//		BigInteger DH_ServerPrivateKey = new BigInteger(Integer.toString(x)); //server local private key 
//		
//		int y = random.nextInt();
//		BigInteger DH_ClientPrivateKey = new BigInteger(Integer.toString(y)); //client local private key 
//		
//		byte[] nonce = getNonce(serverFile); 		
//	
//		
//		
//		PrivateKey serRSA_PrivateKey = getPrivateKey(serverPrivateKeyFile); // server
//		BigInteger DH_serverPublicKey = calculatePublicKey(P, G, DH_ServerPrivateKey);
//		PrivateKey cliRSA_PrivateKey = getPrivateKey(clientPrivateKeyFile); // client
//		BigInteger DH_clientPublicKey = calculatePublicKey(P, G, DH_ClientPrivateKey);
//		
//		// you have a signed key at this point
//		byte[] serverSignedKey = signKey(serRSA_PrivateKey, DH_serverPublicKey);
//		byte[] clientSignedKey = signKey(cliRSA_PrivateKey, DH_clientPublicKey);
//		
//		
//		Certificate serverCertificate = getCert(serverFile);
//		Certificate clientCertificate = getCert(clientFile);
//		
//		//verifying the server and client 
//		Boolean serverVerified = verifySignedKey(serverCertificate, DH_serverPublicKey, serverSignedKey);
//		Boolean clientVerified = verifySignedKey(clientCertificate, DH_clientPublicKey, clientSignedKey);
//		
//		//need to do this part twice for client and server
//		if (serverVerified && clientVerified) {
//			
//			BigInteger serverSharedKey = calculateSharedSecret(DH_clientPublicKey, P, DH_ServerPrivateKey);
//			BigInteger clientSharedKey = calculateSharedSecret(DH_serverPublicKey, P, DH_ClientPrivateKey);
//			
//			assert(serverSharedKey == clientSharedKey);
//			byte[] sharedSecret = serverSharedKey.toByteArray(); // just for naming convention and less confusion 
//			System.out.println("VERIFIED");
//			
//			// generate MAC objects
//			
//			Mac mac = Mac.getInstance("HmacSHA256");
//			SecretKeySpec keySpec = new SecretKeySpec(nonce, "HmacSHA256");
//			mac.init(keySpec);
//			byte[] prk = mac.doFinal();
//			
//			
//			//TODO generate the 6 keys !!!!!!!
//			byte[] serverEncrypt = hkdfExpand(prk, "server encrypt");
//			byte[] clientEncrypt = hkdfExpand(serverEncrypt, "client encrypt");
//			byte[] serverMAC = hkdfExpand(clientEncrypt, "server MAC");
//			byte[] clientMAC = hkdfExpand(serverMAC, "client MAC");
//			byte[] serverIV = hkdfExpand(clientMAC, "server IV");
//			byte[] clientIV = hkdfExpand(serverIV, "client IV");
//			
//		
//			
//			System.out.println(serverEncrypt.toString());
//			System.out.println(clientEncrypt.toString());
//			System.out.println(serverMAC.toString());
//			System.out.println(clientMAC.toString());
//			System.out.println(serverIV.toString());
//			System.out.println(clientIV.toString());
//			
//			System.out.println(serverSharedKey);
//			System.out.println(clientSharedKey);
//		}
//		else {
//			System.out.println("NOT VERIFIED");
//		}	
//		
//	}
//	
//	
//	
//	
//	
//	
//}//end of class





