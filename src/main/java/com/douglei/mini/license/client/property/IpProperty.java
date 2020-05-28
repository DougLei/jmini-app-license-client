package com.douglei.mini.license.client.property;

import java.util.Arrays;

import com.douglei.mini.license.client.ValidationResult;

/**
 * 
 * @author DougLei
 */
public class IpProperty extends Property {
	
	public IpProperty(String value) {
		super("ip", value);
	}
	
	/**
	 * 验证ip
	 * @return
	 */
	public ValidationResult verify() {
		String localhostIp = getLocalhostIp();
		for(String ip : getIps()) {
			if(localhostIp.equals(ip)) {
				return null;
			}
		}
		return new ValidationResult() {
			
			@Override
			public String getMessage() {
				return "当前服务器IP地址不合法，合法的IP地址包括 " + Arrays.toString(getIps());
			}
			
			@Override
			protected String getCode_() {
				return "ip.unlegal";
			}
			
			@Override
			public Object[] getI18nParams() {
				return new Object[] {Arrays.toString(getIps())};
			}
		};
	}
	
	private String[] ips;
	private String[] getIps() {
		if(ips == null) {
			ips = value.split(",");
			for (int i = 0; i < ips.length; i++) {
				ips[i] = ips[i].trim();
			}
		}
		return ips;
	}
	
	// 获取到本机ip地址
	private String getLocalhostIp() {
		// TODO 这里想办法获取到本机的ip地址
		return null;
	}
}
