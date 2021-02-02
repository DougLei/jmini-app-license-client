package com.douglei.mini.license.client.property;

import java.net.InetAddress;
import java.util.Arrays;

import com.douglei.mini.license.client.ValidationResult;
import com.douglei.tools.StringUtil;

/**
 * 
 * @author DougLei
 */
public class IpProperty extends HardwareProperty {
	public static final String NAME = "ip";
	private String[] ips;
	
	public IpProperty(String value) {
		super(NAME, value);
		
		if(StringUtil.unEmpty(value)) {
			ips = value.split(",");
			for (int i = 0; i < ips.length; i++) 
				ips[i] = ips[i].trim();
		}
	}
	
	/**
	 * 验证ip
	 * @return
	 */
	public ValidationResult verify() {
		if(ips == null)
			return null;
		
		String localhostIP = getLocalhostIP();
		if(localhostIP == null)
			return null;
		
		for(String ip : ips) {
			if(localhostIP.equals(ip)) 
				return null;
		}
		return new ValidationResult() {
			
			@Override
			public String getMessage() {
				return "本机IP地址["+localhostIP+"]不合法，合法的IP地址包括" + Arrays.toString(ips);
			}
			
			@Override
			public String getCode() {
				return "license.ip.unlegal";
			}
			
			@Override
			public Object[] getParams() {
				return new Object[] {localhostIP, Arrays.toString(ips)};
			}
		};
	}
	
	// 获取到本机ip地址
	private String getLocalhostIP() {
		InetAddress inetAddress = getInetAddress();
		if(inetAddress != null) 
			return inetAddress.getHostAddress();
		return null;
	}
}
