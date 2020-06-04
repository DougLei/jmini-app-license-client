package com.douglei.mini.license.client.test;

import com.douglei.mini.license.client.AutoLicenseValidator;
import com.douglei.mini.license.client.ValidationResult;

public class Test {
	public static void main(String[] args) {
		AutoLicenseValidator validator = new AutoLicenseValidator("MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCNS+GObBQEWPPga+VZUlXZ6L3BPxkVstkDk3qfeGMz7A41ivwCObuuZHMQxtSdFA1lK5AISZgwoY/09AS5CwkdTPLshH0AShx3r0zuUkP8zYzXZMhe7jC8tZtoGRUiNdBXDEptj/QWjAnLpZjPovUbeQB1szs3WgiKhJGDFM98FwIDAQAB");
		
		validator.start();
		ValidationResult result = validator.getResult();
		
		System.out.println("========> 系统启动, 首次验证结果：" + result);
		System.out.println("========> 系统启动, 首次剩余天数： " + validator.getLeftDays());
	}
}
