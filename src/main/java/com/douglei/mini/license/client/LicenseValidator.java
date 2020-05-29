package com.douglei.mini.license.client;

/**
 * 授权文件验证器
 * @author DougLei
 */
public class LicenseValidator {
	private String publicKey; // 公钥
	private LicenseFile licenseFile; // 授权文件实例
	
	public LicenseValidator(String publicKey) {
		this.publicKey = publicKey;
		this.licenseFile = new LicenseFileReader().read();
	}
	
	/**
	 * 初次验证所有的信息, 包括签名, 用在系统启动时
	 * @return
	 */
	public ValidationResult verifyFirst() {
		ValidationResult result = licenseFile.signature.verify(publicKey, licenseFile.getContentDigest());
		if(result == null)
			result = verify();
		return result;
	}
	
	/**
	 * 验证验证所有的信息, 不包括签名, 用在系统运行时
	 * @return
	 */
	public ValidationResult verify() {
		ValidationResult result = licenseFile.expired.verify();
		if(result == null && licenseFile.ip != null)
			result = licenseFile.ip.verify();
		if(result == null && licenseFile.mac != null)
			result = licenseFile.mac.verify();
		return result;
	}
}
