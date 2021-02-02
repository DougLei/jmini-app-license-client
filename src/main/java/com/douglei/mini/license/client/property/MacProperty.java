package com.douglei.mini.license.client.property;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Arrays;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.douglei.mini.license.client.ValidationResult;
import com.douglei.tools.ExceptionUtil;
import com.douglei.tools.StringUtil;

/**
 * 
 * @author DougLei
 */
public class MacProperty extends HardwareProperty {
	public static final String NAME = "mac";
	private static final Logger logger = LoggerFactory.getLogger(MacProperty.class);
	private String[] macs;
	
	public MacProperty(String value) {
		super(NAME, value);
		
		if(StringUtil.unEmpty(value)) {
			macs = value.split(",");
			for (int i = 0; i < macs.length; i++) 
				macs[i] = macs[i].trim();
		}
	}
	
	/**
	 * 验证mac
	 * @return
	 */
	public ValidationResult verify() {
		if(macs == null)
			return null;
		
		String localhostMAC = getLocalhostMAC();
		if(localhostMAC == null)
			return null;
		
		for(String mac : macs) {
			if(localhostMAC.equals(mac)) 
				return null;
		}
		return new ValidationResult() {
			
			@Override
			public String getMessage() {
				return "本机MAC地址["+localhostMAC+"]不合法，合法的MAC地址包括" + Arrays.toString(macs);
			}
			
			@Override
			public String getCode() {
				return "license.mac.unlegal";
			}
			
			@Override
			public Object[] getParams() {
				return new Object[] {localhostMAC, Arrays.toString(macs)};
			}
		};
	}
	
	// 获取本机的mac地址
	private String getLocalhostMAC() {
		InetAddress inetAddress = getInetAddress();
		if(inetAddress != null) {
			try {
				NetworkInterface networkInterface = NetworkInterface.getByInetAddress(inetAddress);
				byte[] macByte = networkInterface.getHardwareAddress();
				StringBuilder sb = new StringBuilder(20);
				for (int i = 0; i < macByte.length; i++) {
					sb.append(String.format("%02X%s", macByte[i], (i<macByte.length-1)?"-":""));
				}
				return sb.toString();
			} catch (SocketException e) {
				logger.error("无法获取主机的MAC信息: {}", ExceptionUtil.getStackTrace(e));
			}
		}
		return null;
	}
}
