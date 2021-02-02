package com.douglei.mini.license.client;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.douglei.mini.license.client.property.CustomProperty;
import com.douglei.mini.license.client.property.EffectiveDateProperty;
import com.douglei.mini.license.client.property.ExpiredDateProperty;
import com.douglei.mini.license.client.property.IdProperty;
import com.douglei.mini.license.client.property.IpProperty;
import com.douglei.mini.license.client.property.MacProperty;
import com.douglei.mini.license.client.property.SignatureProperty;
import com.douglei.tools.ExceptionUtil;

/**
 * 授权文件实例
 * @author DougLei
 */
public class LicenseFile {
	private static final Logger logger =LoggerFactory.getLogger(LicenseFile.class);
	public final String suffix = ".license"; // 文件后缀
	protected IdProperty id;
	protected EffectiveDateProperty effectiveDate;
	protected ExpiredDateProperty expiredDate;
	protected IpProperty ip;
	protected MacProperty mac ;
	protected SignatureProperty signature;
	protected List<CustomProperty> customs;
	
	/**
	 * 获取授权文件的内容摘要, 用于签名和验证签名
	 * @return
	 */
	protected byte[] getContentDigest() {
		StringBuilder content = new StringBuilder(300);
		content.append(id.getContent());
		content.append(effectiveDate.getContent());
		content.append(expiredDate.getContent());
		if(ip != null)
			content.append(ip.getContent());
		if(mac != null)
			content.append(mac.getContent());
		if(customs != null)
			customs.forEach(custom -> content.append(custom.getContent()));
		try {
			MessageDigest digest = MessageDigest.getInstance("SHA-1");
			digest.update(content.toString().getBytes());
			return digest.digest();
		} catch (NoSuchAlgorithmException e) {
			logger.error("获取授权文件的内容摘要, 用于签名和验证签名时出现异常: {}", ExceptionUtil.getStackTrace(e));
			throw new RuntimeException("获取授权文件的内容摘要, 用于签名和验证签名时出现异常", e);
		}
	}
	
	/**
	 * 设置授权文件内容
	 * @param content
	 * @throws ParseException 
	 */
	void setContent(String content) throws ParseException {
		int equalSignIndex = content.indexOf("=");
		String name = content.substring(0, equalSignIndex);
		String value = content.substring(equalSignIndex+1);
		switch(name) {
			case IdProperty.NAME:
				id = new IdProperty(value);
				break;
			case EffectiveDateProperty.NAME:
				effectiveDate = new EffectiveDateProperty(value);
				break;
			case ExpiredDateProperty.NAME:
				expiredDate = new ExpiredDateProperty(id.getValue(), value);
				break;
			case IpProperty.NAME:
				ip = new IpProperty(value);
				break;
			case MacProperty.NAME:
				mac = new MacProperty(value);
				break;
			case SignatureProperty.NAME:
				signature = new SignatureProperty(value);
				break;
			default:
				if(customs == null)
					customs = new ArrayList<CustomProperty>();
				customs.add(new CustomProperty(name, value));
		}
	}
}
