package com.douglei.mini.license.client.property;

import java.security.KeyFactory;
import java.security.PublicKey;
import java.security.Signature;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

import com.douglei.mini.license.client.ValidationResult;

/**
 * 
 * @author DougLei
 */
public class SignatureProperty extends AbstractProperty {
	public static final String NAME = "signature";
	
	public SignatureProperty(String value) {
		super(NAME, value);
	}
	
	/**
	 * 验证签名
	 * @param publicKey
	 * @return
	 */
	public ValidationResult verify(String publicKey, byte[] contentDigest){
		try {
			KeyFactory keyFactory = KeyFactory.getInstance("RSA");
			X509EncodedKeySpec keySpec = new X509EncodedKeySpec(Base64.getDecoder().decode(publicKey));
			PublicKey publicKey_ =  (RSAPublicKey) keyFactory.generatePublic(keySpec);
			
			Signature signature_ = Signature.getInstance("SHA1WithRSA");
			signature_.initVerify(publicKey_);
			signature_.update(contentDigest);
			
			if(signature_.verify(Base64.getDecoder().decode(value)))
				return null;
		} catch (Exception e) {
			return new ValidationResult() {
				@Override
				public String getMessage() {
					return "公钥不合法";
				}
				@Override
				public String getCode() {
					return "license.public.key.unlegal";
				}
			};
		}
		return new ValidationResult() {
			@Override
			public String getMessage() {
				return "签名不合法";
			}
			@Override
			public String getCode() {
				return "license.signature.unlegal";
			}
		};
	}
}
