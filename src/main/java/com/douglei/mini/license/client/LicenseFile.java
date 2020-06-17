package com.douglei.mini.license.client;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import com.douglei.mini.license.client.property.CustomProperty;
import com.douglei.mini.license.client.property.ExpiredProperty;
import com.douglei.mini.license.client.property.IpProperty;
import com.douglei.mini.license.client.property.MacProperty;
import com.douglei.mini.license.client.property.SignatureProperty;
import com.douglei.mini.license.client.property.StartProperty;

/**
 * 授权文件实例
 * @author DougLei
 */
public class LicenseFile {
	/**
	 * 文件后缀
	 */
	public final String suffix = ".license"; 
	
	protected StartProperty start;
	protected ExpiredProperty expired;
	protected IpProperty ip;
	protected MacProperty mac ;
	protected SignatureProperty signature;
	
	protected List<CustomProperty> customs;
	
	/**
	 * 获取授权文件的内容摘要, 用于签名和验证签名
	 * @return
	 */
	protected byte[] getContentDigest() {
		StringBuilder content = new StringBuilder(500);
		content.append(start.getContent());
		content.append(expired.getContent());
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
		}
		return null;
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
			case StartProperty.name:
				start = new StartProperty(value);
				break;
			case ExpiredProperty.name:
				expired = new ExpiredProperty(value);
				break;
			case IpProperty.name:
				ip = new IpProperty(value);
				break;
			case MacProperty.name:
				mac = new MacProperty(value);
				break;
			case SignatureProperty.name:
				signature = new SignatureProperty(value);
				break;
			default:
				if(customs == null)
					customs = new ArrayList<CustomProperty>();
				customs.add(new CustomProperty(name, value));
		}
	}
}
