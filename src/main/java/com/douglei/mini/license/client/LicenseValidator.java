package com.douglei.mini.license.client;

import com.douglei.mini.license.client.property.CustomProperty;

/**
 * 授权文件验证器
 * @author DougLei
 */
class LicenseValidator {
	private String publicKey; // 公钥
	private LicenseFile licenseFile; // 授权文件实例
	
	public LicenseValidator(String publicKey) {
		this.publicKey = publicKey;
		this.licenseFile = new LicenseFileReader().read();
	}
	
	/**
	 * 启动启动时验证
	 * @return
	 */
	protected ValidationResult verifyByStart() {
		ValidationResult result = licenseFile.signature.verify(publicKey, licenseFile.getContentDigest());
		if(result == null)
			result = licenseFile.start.verify();
		if(result == null)
			result = verify();
		return result;
	}
	
	/**
	 * 系统运行时验证
	 * @return
	 */
	protected ValidationResult verify() {
		ValidationResult result = licenseFile.expired.verify();
		if(result == null && licenseFile.ip != null)
			result = licenseFile.ip.verify();
		if(result == null && licenseFile.mac != null)
			result = licenseFile.mac.verify();
		return result;
	}
	
	/**
	 * 获取剩余有效天数
	 * @return
	 */
	public int getLeftDays() {
		return licenseFile.expired.getLeftDays();
	}
	
	/**
	 * 获取指定name的自定义授权信息的值
	 * @param name
	 * @return
	 */
	public String getCustomValue(String name){
		if(licenseFile.customs != null) {
			for(CustomProperty custom : licenseFile.customs) {
				if(custom.getName().equals(name)) 
					return custom.getValue();
			}
		}
		return null;
	}
}
