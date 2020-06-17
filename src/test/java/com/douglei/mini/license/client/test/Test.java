package com.douglei.mini.license.client.test;

import com.douglei.mini.license.client.AutoLicenseValidator;
import com.douglei.mini.license.client.ValidationResult;

public class Test {
	public static void main(String[] args) {
		AutoLicenseValidator validator = new AutoLicenseValidator("MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCMgkmuD9EuilX8hAx+nQfcAu/fKmla1wm08ogCV4akbSXG1AKkzYvf7i6JEwxMGQbqPfbHX7w1XqshJfuJwvjs9fEGPwMd4pSMk8Fia5CMMruenYfciN56QMwQLRNaJmgYCkYtphqjT7rf2NQ2hfHo3Z/TxbNidsWs3REHt19IOQIDAQAB");
		
		validator.start();
		ValidationResult result = validator.getResult();
		
		System.out.println("========> 系统启动, 首次验证结果：" + result);
		System.out.println("========> 系统启动, 首次剩余天数： " + validator.getLeftDays());
		System.out.println(validator.getCustomValue("user.count"));
		System.out.println(validator.getCustomValue("user.count2"));
	}
}
