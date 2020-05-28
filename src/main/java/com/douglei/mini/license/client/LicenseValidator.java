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
	 * 验证所有的信息
	 * @return
	 */
	public ValidationResult verifyAll() {
		ValidationResult validationResult = verifySignature();
		if(validationResult == null)
			validationResult = verifyExpired();
		if(validationResult == null)
			validationResult = verifyIp();
		if(validationResult == null)
			validationResult = verifyMac();
		return validationResult;
	}
	
	/**
	 * 验证有效期
	 * @return
	 */
	public ValidationResult verifyExpired() {
		return licenseFile.expired.verify();
	}
	
	/**
	 * 验证ip
	 * @return
	 */
	public ValidationResult verifyIp() {
		if(licenseFile.ip != null)
			return licenseFile.ip.verify();
		return null;
	}
	
	/**
	 * 验证mac
	 * @return
	 */
	public ValidationResult verifyMac() {
		if(licenseFile.mac != null)
			return licenseFile.mac.verify();
		return null;
	}

	/**
	 * 验证签名
	 * @return
	 */
	public ValidationResult verifySignature() {
		return licenseFile.signature.verify(publicKey, licenseFile.getContentDigest());
	}
}
