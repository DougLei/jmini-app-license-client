package com.douglei.mini.license.client.property;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Arrays;
import java.util.Enumeration;

import com.douglei.mini.license.client.ValidationResult;

/**
 * 
 * @author DougLei
 */
public class MacProperty extends Property {
	
	public MacProperty(String value) {
		super("mac", value);
	}
	
	/**
	 * 验证mac
	 * @return
	 */
	public ValidationResult verify() {
		String localhostMac = getLocalhostMac();
		for(String mac : getMacs()) {
			if(localhostMac.equals(mac)) {
				return null;
			}
		}
		return new ValidationResult() {
			
			@Override
			public String getMessage() {
				return "当前服务器MAC地址 "+localhostMac+" 不合法，合法的MAC地址包括 " + Arrays.toString(getMacs());
			}
			
			@Override
			protected String getCode_() {
				return "mac.unlegal";
			}
			
			@Override
			public Object[] getI18nParams() {
				return new Object[] {localhostMac, Arrays.toString(getMacs())};
			}
		};
	}
	
	private String[] macs;
	private String[] getMacs() {
		if(macs == null) {
			macs = value.split(",");
			for (int i = 0; i < macs.length; i++) {
				macs[i] = macs[i].trim();
			}
		}
		return macs;
	}
	
	// 获取本机的mac地址
	private String getLocalhostMac() {
		// TODO 这里想办法获取到本机的mac地址
		return null;
	}
	
	public static void main(String[] args) throws Exception {
		Enumeration<NetworkInterface> en  = NetworkInterface.getNetworkInterfaces();
		while(en.hasMoreElements()) {
			NetworkInterface networkInterface = en.nextElement();
			
			Enumeration<InetAddress> inetAddresses = networkInterface.getInetAddresses();
			while(inetAddresses.hasMoreElements()) {
				System.out.println(inetAddresses.nextElement().getHostAddress());
			}
			
		}
	}
}
