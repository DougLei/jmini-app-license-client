package com.douglei.mini.license.client;

public class ZTest {
	public static void main(String[] args) {
		// 正确的公钥
//		LicenseValidator validator = new LicenseValidator("MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCl6UcBeX/BVclovzVcM8Yd2lgRtsakKEz8plCNVXV5vInurzMUJTike3qK1uxKVzWwdpuA7Kfkvg1GLPBBicq/GiXd0rwN3c0r0gbsrwyCy1Mn609krd6sjUsexCXZ5Wa7Id5CJpR+hJcMlpZPx/eXgF9O5qLmYpqDyCg8SHaoYQIDAQAB");
		// 错误的公钥
		LicenseValidator validator = new LicenseValidator("MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCEY2DZ1CkMJmLdfE5QVbeAGnVzSZOfRx51OwFDXn1Yoo0mC2HOF/Rk5VqcrTYq/fZRDuiKkFBKfBT/86q9o6YvaHTA96vPUuyFwQ8ma7f2bmrKUq0amoCpN2uhv218ESe8iEPGzmf6h8EcX+EkC2GLjSI5NyBLdHqTaBO/XNh+UQIDAQAB");
		System.out.println(validator.verifySignature());
		
	}
}
