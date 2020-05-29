package com.douglei.mini.license.client.property;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Arrays;
import java.util.Enumeration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.douglei.mini.license.client.ValidationResult;
import com.douglei.tools.utils.ExceptionUtil;

/**
 * 
 * @author DougLei
 */
public class IpProperty extends Property {
	private static final Logger logger = LoggerFactory.getLogger(IpProperty.class);
	
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
				return "当前服务器IP地址 "+localhostIp+" 不合法，合法的IP地址包括 " + Arrays.toString(getIps());
			}
			
			@Override
			protected String getCode_() {
				return "ip.unlegal";
			}
			
			@Override
			public Object[] getI18nParams() {
				return new Object[] {localhostIp, Arrays.toString(getIps())};
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
		Enumeration<InetAddress> inetAddresses = null;
		InetAddress inetAddress = null;
		String ipv4 = null;
		
		try {
			Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces();
			while(en.hasMoreElements()) {
				inetAddresses = en.nextElement().getInetAddresses();
				while(inetAddresses.hasMoreElements()) {
					inetAddress = inetAddresses.nextElement();
					if(inetAddress instanceof Inet4Address) {
						ipv4 = inetAddress.getHostAddress();
						if(!ipv4.equals("127.0.0.1")) {
							return ipv4;
						}
					}
				}
			}
		} catch (SocketException e) {
			logger.error("无法获取主机的ip信息: {}", ExceptionUtil.getExceptionDetailMessage(e));
		}
		return null;
	}
}
