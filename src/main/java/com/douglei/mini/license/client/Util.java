package com.douglei.mini.license.client;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * 
 * @author DougLei
 */
public class Util {
	
	/**
	 * 获取内容摘要
	 * @param content
	 * @return
	 */
	public static byte[] getDigest(String content) {
		try {
			MessageDigest digest = MessageDigest.getInstance("SHA-1");
			digest.update(content.getBytes());
			return digest.digest();
		} catch (NoSuchAlgorithmException e) {
			return null;
		}
	}
}
