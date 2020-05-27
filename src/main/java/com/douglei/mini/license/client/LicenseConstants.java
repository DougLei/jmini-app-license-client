package com.douglei.mini.license.client;

/**
 * license常量
 * @author DougLei
 */
public class LicenseConstants {
	
	/**
	 * 授权文件文件名
	 */
	public static final String FILE_NAME = "license";
	
	// ---------------------------------------------------------
	/**
	 * 授权文件中key: 类型
	 */
	public static final String KEY_TYPE = "type";
	
	/**
	 * 授权文件中key: 有效期, 格式为 yyyy-MM-dd
	 */
	public static final String KEY_EXPIRED = "expired";
	
	/**
	 * 授权文件中key: ip地址
	 */
	public static final String KEY_IP = "ip";
	
	/**
	 * 授权文件中key: mac地址
	 */
	public static final String KEY_MAC = "mac";
	
	/**
	 * 授权文件中key: 签名
	 */
	public static final String KEY_SIGNATURE = "signature";
}
