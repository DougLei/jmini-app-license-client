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
	 * 读取并获得授权文件实例
	 * @return
	 * @throws IOException 
	 */
	public LicenseFile read() throws IOException {
		return read(LicenseConstants.FILE_NAME);
	}
	
	/**
	 * 读取并获得授权文件实例
	 * @param resource 授权文件的资源路径(基于java项目的路径)
	 * @return
	 * @throws IOException 
	 */
	public LicenseFile read(String resource) throws IOException {
		LicenseFile file = new LicenseFile();
		try(InputStream input = LicenseFileReader.class.getClassLoader().getResourceAsStream(resource)){
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
		}
		return file;
	}
	
	public static void main(String[] args) throws IOException {
		boolean result = new LicenseFileReader().read().initVerify("MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCAJx6gQqsJEhFhIIqoO8/VlcyG8FH1BmFluKtQ0PmUzD9ofzBcvy4pdWgQ63v4hkCUOTAI5a/yLn/UFhYT7HNcpY63tJGic9tiGPM/tvgh10OIQsJclhMQqCiC3qBdVF7mkY5N5WMQW2wlk4yJY024ARa0Wc4EFSkhECKvV7obiwIDAQAB");
		System.out.println(result);
	}
}
