

import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.MessageDigest;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.Signature;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

import javax.crypto.Cipher;

import org.apache.commons.codec.binary.Hex;
import org.apache.commons.codec.digest.DigestUtils;

public class Server {
	private static String publicKeyString = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCF/vyavP2pj+sRVg11crSzPzqm4UMx4vNro0Krg9i3gmDcLWYGbkKBwTyfHhSmm7CQzfZLirEI1IUQuZBWsNRONgjhAMO/fL/65Znpc+nGAN64IADV2XLEh2BW36dbZHyTt6Bv2BFgeEx3YZEv6ym3jON1SqatmCr8z5hO+Tp5LQIDAQAB";
	private static String privateKeyString = "MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBAIX+/Jq8/amP6xFWDXVytLM/OqbhQzHi82ujQquD2LeCYNwtZgZuQoHBPJ8eFKabsJDN9kuKsQjUhRC5kFaw1E42COEAw798v/rlmelz6cYA3rggANXZcsSHYFbfp1tkfJO3oG/YEWB4THdhkS/rKbeM43VKpq2YKvzPmE75OnktAgMBAAECgYBQTJBAgsVYmeB60SDbo1Panyec3h2QhbruZDYN5sM2pbE6ARLFA8pjS9PyXuptNZvIC0Vv27tf2UuszCdNbEatgfZ+SelkI25VAdiVqBekBmVZWkYtDAR0b7avNCxwTerCXoYFXEhHlxAJ/0ihvLJzrixpy6PbxuUkb/aH5yUL2QJBAL1YA5NNTtXb9h53PrRg5Z2hwg9ouJig/W4ipYM3MyMGv1gCCn3rfPfxKkM/YUu6c5XC52F+ZBpY93av9/pt5lsCQQC1KvAr7f+effcWmKN4Jpe7/tfmiojX1r3hb91hUO5y9A+uht2WPHFewCvx1aPGm8HKaTUk5LWFYCnXtVrAgQUXAkEAqdVoX5fYPrst5+XNuL2xN9L/3hJwgXbl2wP4sdmxZJmIx8gM4SV40mzfG7Kd8u8q/gn0wN49q+RhE3SDWX16QQJAGlxlFf9LcoRhr6gGITV8Xp52S9VclAtrFaJxU/eyuodIwNxe4b1pwZ+0xTuQ2RVQ3WLxmKyuvPmBHSIGUnxzgQJBAJXqcWjWzGFI7wATa7VHpcgPn1XgFIYAXOJFj8c1agu/aVAdScprzS6uCUuNUc/vX6ZKPDkk4StHs1r51+2EvnU=";
	private static String content = "Hello World! 这个就是我要加密的字符串！";
	
	public static void main(String[] args) {
//		生成公钥私钥对();
//		公钥加密私钥解密();
//		私钥签名公钥验证();
		获取文件的摘要();
	}
	
	public static void 生成公钥私钥对() {
		try {
			KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
			SecureRandom secureRandom = new SecureRandom("douglei.com".getBytes());
			keyPairGenerator.initialize(1024, secureRandom); // 初始化密码对生成器, 密钥大小的范围为:96~1024
			KeyPair keyPair = keyPairGenerator.genKeyPair();
			
			System.out.println("公钥：" +Base64.getEncoder().encodeToString(keyPair.getPublic().getEncoded()));
			System.out.println("私钥：" + Base64.getEncoder().encodeToString(keyPair.getPrivate().getEncoded()));
		} catch (Exception e) {
			e.printStackTrace();
		} 
	}
	
	private static RSAPublicKey 获取公钥实例() {
		try {
			KeyFactory keyFactory = KeyFactory.getInstance("RSA");
			X509EncodedKeySpec keySpec = new X509EncodedKeySpec(Base64.getDecoder().decode(publicKeyString));
			return (RSAPublicKey) keyFactory.generatePublic(keySpec);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	private static RSAPrivateKey 获取私钥实例() {
		try {
			KeyFactory keyFactory = KeyFactory.getInstance("RSA");
			PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(Base64.getDecoder().decode(privateKeyString));
			return (RSAPrivateKey) keyFactory.generatePrivate(keySpec);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static void 公钥加密私钥解密() { // 如果反过来则可以实现私钥加密公钥解密
		byte[] result = null;
		// 使用公钥加密
		try {
			Cipher cipher = Cipher.getInstance("RSA");
			cipher.init(Cipher.ENCRYPT_MODE, 获取公钥实例());
			result = cipher.doFinal(content.getBytes());
			System.out.println("公钥加密后的内容为：" + Base64.getEncoder().encodeToString(result));
		}catch (Exception e) {
			e.printStackTrace();
		}
		
		// 使用私钥解密
		try {
			Cipher cipher = Cipher.getInstance("RSA");
			cipher.init(Cipher.DECRYPT_MODE, 获取私钥实例());
			result = cipher.doFinal(result);
			System.out.println("私钥解密后的内容为："+ new String(result));
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void 私钥签名公钥验证() {
		String result = null;
		try {
			PrivateKey privateKey = 获取私钥实例();
			Signature signature = Signature.getInstance("SHA1WithRSA");
			signature.initSign(privateKey);
			signature.update(content.getBytes());
			
			result = Base64.getEncoder().encodeToString(signature.sign());
			System.out.println("私钥签名后的内容为：" + result);
		}catch (Exception e) {
			e.printStackTrace();
		}
		
		
		try {
			PublicKey publicKey = 获取公钥实例();
			Signature signature = Signature.getInstance("SHA1WithRSA");
			signature.initVerify(publicKey);
			signature.update(content.getBytes());
			
			System.out.println("公钥验证签名的结果为：" + signature.verify(Base64.getDecoder().decode(result)));
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	public static void 获取文件的摘要() {
		try {
			MessageDigest digest = MessageDigest.getInstance("SHA-1");
			
			digest.update(content.getBytes());
			digest.update(content.getBytes());
			
			byte[] result = digest.digest();
			System.out.println(Hex.encodeHexString(result));
			
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
