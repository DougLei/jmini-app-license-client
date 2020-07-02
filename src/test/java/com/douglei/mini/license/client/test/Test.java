package com.douglei.mini.license.client.test;

import com.douglei.mini.license.client.AutoLicenseValidator;
import com.douglei.mini.license.client.ValidationResult;

public class Test {
	public static void main(String[] args) {
		AutoLicenseValidator validator = new AutoLicenseValidator("MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCTkyOXn446xW06n/k2YUZ3BbPxcEEKCSZoBsp7MMzVu2HoFkUhE4H2tzbzo7cfsAmUEm0N6jtf533XZxv2slbSPmc8lNt8Nm05DQEvCGgov6QB41x1XelGAn7q5MTCFlFqlVyilvDMh0iI6u80cttEeaFkrhAxV2+RgWUTQ0dcDwIDAQAB");
		
		validator.start();
		ValidationResult result = validator.getResult();
		
		System.out.println("========> 系统启动, 首次验证结果：" + result);
		System.out.println("========> 系统启动, 首次剩余天数： " + validator.getLeftDays());
	}
}
