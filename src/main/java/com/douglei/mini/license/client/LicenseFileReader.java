package com.douglei.mini.license.client;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * 
 * @author DougLei
 */
public class LicenseFileReader {
	
	/**
	 * 读取并获得授权文件
	 * @param publicKey 公钥
	 * @return
	 * @throws IOException 
	 */
	public LicenseFile read(String publicKey) throws IOException {
		return read(LicenseConstants.FILE_NAME, publicKey);
	}
	
	/**
	 * 读取并获得授权文件
	 * @param resource 授权文件的资源路径(基于java项目的路径)
	 * @param publicKey 公钥
	 * @return
	 * @throws IOException 
	 */
	public LicenseFile read(String resource, String publicKey) throws IOException {
		LicenseFile file = new LicenseFile();
		InputStream input = LicenseFileReader.class.getClassLoader().getResourceAsStream(resource);
		if(input != null) {
			ByteArrayOutputStream out = new ByteArrayOutputStream(300);
			int privateKey = input.read();
			int separator = input.read()^privateKey;
			int b;
			while((b=input.read()) > -1) {
				b = b^privateKey;
				if(b == separator) {
					file.setContent(new String(out.toByteArray()));
					out.reset();
				}else {
					out.write(b);
				}
			}
		}
		return file;
	}
}
