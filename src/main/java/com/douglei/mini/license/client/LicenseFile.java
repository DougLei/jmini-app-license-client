package com.douglei.mini.license.client;

import java.lang.reflect.Field;
import java.security.KeyFactory;
import java.security.PublicKey;
import java.security.Signature;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.douglei.tools.utils.ExceptionUtil;

/**
 * 
 * @author DougLei
 */
public final class LicenseFile {
	private static final Logger logger = LoggerFactory.getLogger(LicenseFile.class);
	LicenseFile(){}
	
	/** 这里的属性名, 与{@link LicenseConstants} 中KEY_XXX的值一致 */
	private String type;
	private String expired;
	private String ip;
	private String mac;
	private String signature;
	
	/**
	 * 设置授权文件内容
	 * @param content
	 */
	void setContent(String content) {
		int equalSignIndex = getEqualSignIndex(content);
		try {
			Field field = getClass().getDeclaredField(content.substring(0, equalSignIndex));
			field.setAccessible(true);
			field.set(this, content.substring(equalSignIndex+1));
			field.setAccessible(false);
		} catch (Exception e) {
			logger.error("在给属性{}赋值{}时, 出现异常信息: {}", content.substring(0, equalSignIndex), content.substring(equalSignIndex+1), ExceptionUtil.getExceptionDetailMessage(e));
		} 
	}
	
	// 获取等号的下标, 用以拆分key和value
	private int getEqualSignIndex(String content) {
		for(int i=0;i<content.length();i++) {
			if(content.charAt(i) == '=') {
				return i;
			}
		}
		throw new IllegalArgumentException("在内容{}中没有找到=标识, 无法进行key和value的截取") ;
	}

	
	/**
	 * 初始化验证, 该操作会验证所有信息
	 * @param publicKey 公钥
	 * @return
	 */
	public boolean initVerify(String publicKey) {
		try {
			KeyFactory keyFactory = KeyFactory.getInstance("RSA");
			X509EncodedKeySpec keySpec = new X509EncodedKeySpec(Base64.getDecoder().decode(publicKey));
			PublicKey publicKey_ =  (RSAPublicKey) keyFactory.generatePublic(keySpec);
			
			Signature signature_ = Signature.getInstance("SHA1WithRSA");
			signature_.initVerify(publicKey_);
			signature_.update(getContentDigest());
			
			return signature_.verify(Base64.getDecoder().decode(signature));
		}catch (Exception e) {
			logger.error("在使用公钥验证签名时出现异常: {}", ExceptionUtil.getExceptionDetailMessage(e));
		}
		return false;
	}
	// 获取授权文件的内容摘要
	private byte[] getContentDigest() {
		StringBuilder content = new StringBuilder(500);
		if(type != null)
			content.append(LicenseConstants.KEY_TYPE).append('=').append(type);
		if(expired != null)
			content.append(LicenseConstants.KEY_EXPIRED).append('=').append(expired);
		if(ip != null)
			content.append(LicenseConstants.KEY_IP).append('=').append(ip);
		if(mac != null)
			content.append(LicenseConstants.KEY_MAC).append('=').append(mac);
		return Util.getDigest(content.toString());
	}
	
	/**
	 * 获取验证器
	 * @param publicKey 公钥
	 * @return
	 */
	public LicenseFileValidator getValidator(String publicKey) {
		
	}
}
