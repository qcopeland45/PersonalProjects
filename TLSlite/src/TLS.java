import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigInteger;
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.SecureRandom;
import java.security.Signature;
import java.security.SignatureException;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.Arrays;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

public class TLS {
	
	
	//member variables
	public static final BigInteger G = new BigInteger("14");
	public static final BigInteger P = new BigInteger("3231700607131100730033891"
			+ "392642382824881794124114023911284200975140074"
			+ "170663435422261968941736356934711790173790970"
			+ "419175460587320919502885375898618562215321217"
			+ "541251490177452027023579607823624888424618947"
			+ "758764110592864609941172324542662252219323054"
			+ "091903768052423551912567971587011700105805587"
			+ "7651038861847280257976054903569732561526167081"
			+ "3393617995413364765591603683178967290731783845"
			+ "8968063967190097720219416864722587103141133642"
			+ "9319536193471636533209717077448227988588565369"
			+ "2086452966360772502689555059283627511211740969"
			+ "7299806841055435958486658329164213621823107899"
			+ "0999448652468262416972035911852507045361090559");
	
	
	public static byte[] getNonce() throws CertificateException, FileNotFoundException {
		
		SecureRandom random = new SecureRandom();
		byte clientNonce[] = new byte[32];
		random.nextBytes(clientNonce);
	
		return clientNonce;
		
	}
	
	public static Certificate createCertificate(String fileName) throws FileNotFoundException, CertificateException {
		
		CertificateFactory certificateFactory = CertificateFactory.getInstance("X.509");
		InputStream certInputStream = new FileInputStream(fileName);
		Certificate certificate = certificateFactory.generateCertificate(certInputStream);
		
		
		return certificate;
		
	}
	

	
	public static byte[] readDerFiles(String file) throws IOException {
		
		InputStream is = new FileInputStream(file);
		long fileSize = new File(file).length();
		byte byteArr[] = new byte[(int) fileSize];
		
		is.read(byteArr);
		is.close();
		return byteArr;
		
		
		
	}
	
	public static PrivateKey generatePrivateKey(String fileName) throws NoSuchAlgorithmException, IOException, InvalidKeySpecException {
		
		InputStream is = new FileInputStream(fileName);
		byte[] fileBytes = is.readAllBytes();
		PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(fileBytes);
		KeyFactory key = KeyFactory.getInstance("RSA");
		PrivateKey privateKey = key.generatePrivate(keySpec);
		
		return privateKey;
	}
	

	public static byte[] signKey(PrivateKey privateKey, BigInteger publicKey) throws NoSuchAlgorithmException, InvalidKeyException, SignatureException {
		
		Signature signature = Signature.getInstance("SHA256WithRSA");
		signature.initSign(privateKey);
		signature.update(publicKey.toByteArray());

		return signature.sign();
		
	}
	
	public static boolean verifyCertificate(Certificate certToVerify) {
		
		try {
			Certificate cert = createCertificate("CAcertificate.pem");
			certToVerify.verify(cert.getPublicKey());
		}
		catch (Exception e) {
			e.printStackTrace();
			return false;
		}
			 
		return true;
		
	}
	
	
	public static boolean verifySignedKey(Certificate certToVerify, BigInteger publicKey, byte[] signedKey) throws SignatureException, InvalidKeyException, NoSuchAlgorithmException {
		
		if (!verifyCertificate(certToVerify))
			return false;
			
		Signature signature = Signature.getInstance("SHA256WithRSA");
		signature.initVerify(certToVerify);
		signature.update(publicKey.toByteArray());
		
		
		return signature.verify(signedKey);
		
		
	}
	
	public static BigInteger calculatePublicKey(BigInteger privateKey) {

		return G.modPow(privateKey, P);
		
	}
	
	
	public static BigInteger calculateSharedSecret(BigInteger sharedKey, BigInteger privateKey) {

		return sharedKey.modPow(privateKey, P);
	}
	
	//helper to concatenate two byte arrays
	public static byte[] concat(byte[] a, byte[] b) {
        int lenA = a.length;
        int lenB = b.length;
        byte[] c = Arrays.copyOf(a, lenA + lenB);
        System.arraycopy(b, 0, c, lenA, lenB);
        return c;
    }
	
	public static byte[] getFirst16(byte[] arr) {
		
		byte[] ret = new byte[16];
		for (int i = 0; i < 16; i++) {
			ret[i] = arr[i];
			
		}
		return ret;
		
	}
	
	public static byte[] hdkfExpand(byte[] sharedSecret, String tag) throws NoSuchAlgorithmException, InvalidKeyException {

		Mac mac	= Mac.getInstance("HmacSHA256");
		byte[] addOn = { 1 };
		byte[] keyBytes = concat(sharedSecret, addOn);
		byte[] tagBytes = tag.getBytes();
		byte[] bytes = concat(keyBytes, tagBytes);
		SecretKeySpec key = new SecretKeySpec(sharedSecret, "HmacSHA256");
		mac.init(key);
		
		byte[] okm = mac.doFinal(bytes);
		byte[] first16 = new byte[16];
		first16 = getFirst16(okm);
		
		assert(first16.length == 16); //sanity check 
		return first16;
		
	}
	
	
	
	public static byte[] concatArray(byte[] a, byte[] b) {
		
		byte[] retArr = new byte[a.length + b.length];
		
		int j = 0;
		for (int i = 0; i < retArr.length; i++) {
			
			if (i < a.length) {
				retArr[i] = a[i];
			}
			else {
				retArr[i] = b[j++];
		
			}
		}
		
		return retArr;
		
	}
	
	
	
	

}
