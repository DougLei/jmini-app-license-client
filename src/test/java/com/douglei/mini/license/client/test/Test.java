package com.douglei.mini.license.client.test;

import com.douglei.mini.license.client.LicenseValidateApp;
import com.douglei.mini.license.client.ValidationResult;

public class Test {
	public static void main(String[] args) {
		LicenseValidateApp validator = new LicenseValidateApp("MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCvaZOJtOYKTbHA1aL1q7N7WN+Iru2A82Ql0MTuLZop6MUDwdNk6wOo3yyDkva8It/i/yqkPfHycbUtbYWvF7nwG7QK3JzkfJI9cbCRrU8abawQ0RD/8Js+Jk/s731lXjk+U4WZs686IZ9XbDOcY6HUoVPBvJZQVycbk50eBm8rmQIDAQAB");
		
		validator.startup();
		ValidationResult result = validator.getResult();
		
		System.out.println("========> 系统启动, 首次验证结果：" + result);
		System.out.println("========> 系统启动, 首次剩余天数： " + validator.getLeftDays());
		System.out.println(validator.getCustomValue("user.count"));
		System.out.println(validator.getCustomValue("user.count2"));
	}
}
