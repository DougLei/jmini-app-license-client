package com.douglei.mini.license.client;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.douglei.mini.license.client.property.ExpiredProperty;
import com.douglei.mini.license.client.property.ExtProperty;
import com.douglei.mini.license.client.property.IpProperty;
import com.douglei.mini.license.client.property.MacProperty;
import com.douglei.mini.license.client.property.SignatureProperty;
import com.douglei.mini.license.client.property.StartProperty;

/**
 * 授权文件实例
 * @author DougLei
 */
public class LicenseFile {
	protected StartProperty start;
	protected ExpiredProperty expired;
	protected IpProperty ip;
	protected MacProperty mac ;
	protected SignatureProperty signature;
	
	protected List<ExtProperty> exts;
	Map<String, String> startExtMap; // 系统启动时的扩展信息map
	Map<String, String> runExtMap; // 系统运行时的扩展信息map
	
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
		if(exts != null)
			exts.forEach(ext -> content.append(ext.getContent()));
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
				if(exts == null)
					exts = new ArrayList<ExtProperty>();
				exts.add(new ExtProperty(name, value, true));
		}
	}
	
	/**
	 * 是否存在系统启动时, 需要验证的扩展信息map集合
	 * @return
	 */
	boolean existsStartExtMap() {
		if(startExtMap == null) {
			if(exts != null) {
				for (ExtProperty ep : exts) {
					if(ep.startVM()) {
						if(startExtMap == null)
							startExtMap = new HashMap<String, String>(16);
						startExtMap.put(ep.getOriginName(), ep.getValue());
					}
				}
			}
			if(startExtMap == null)
				startExtMap = Collections.emptyMap();
		}
		return !startExtMap.isEmpty();
	}

	/**
	 * 是否存在系统运行时, 需要验证的扩展信息map集合
	 * @return
	 */
	boolean existsRunExtMap() {
		if(runExtMap == null) {
			if(exts != null) {
				for (ExtProperty ep : exts) {
					if(ep.runVM()) {
						if(runExtMap == null)
							runExtMap = new HashMap<String, String>(16);
						runExtMap.put(ep.getOriginName(), ep.getValue());
					}
				}
			}
			if(runExtMap == null)
				runExtMap = Collections.emptyMap();
		}
		return !runExtMap.isEmpty();
	}
}
