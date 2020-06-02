package com.douglei.mini.license.client.test;

import com.douglei.mini.license.client.AutoLicenseValidator;
import com.douglei.mini.license.client.ValidationResult;

public class Test {
	public static void main(String[] args) {
		AutoLicenseValidator validator = new AutoLicenseValidator("MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCZISPugceSxGnW7qZ65xQndQJKh6pE41Oldhwd7kBf3D965efhjDLi3teeK20EWJpfdnX8oPn+rF/3WoP0tMXRyB2sQxLqy4YScx7zJ6DVAgyd5+toaMK6UxwOrDX9VrYU8QYVWDA3GR4bYak0tPA1KMHKil3rrRUJkhSb6w6jmQIDAQAB");
		
		validator.start();
		ValidationResult result = validator.getResult();
		
		System.out.println("========> 系统启动, 首次验证结果：" + result);
		System.out.println("========> 系统启动, 首次剩余天数： " + validator.getLeftDays());
	}
}
