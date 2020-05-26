package com.douglei.mini.license.client;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

public class Test {
	public static void main(String[] args) throws Exception {
		FileInputStream fis = new FileInputStream(new File("C:\\Users\\Administrator.USER-20190119TM\\License\\license.lf"));
		FileOutputStream fos = new FileOutputStream(new File("C:\\Users\\Administrator.USER-20190119TM\\License\\license2222.lf"));
		
		int privateKey = fis.read();
		int b;
		
		while((b=fis.read()) > -1) {
			b = b^privateKey;
			fos.write(b);
			
		}
		
		fos.close();
		fis.close();
		
	}
}
