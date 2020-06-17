package com.douglei.mini.license.client;

/**
 * 授权文件验证器
 * @author DougLei
 */
class LicenseValidator {
	private String publicKey; // 公钥
	private LicenseFile licenseFile; // 授权文件实例
	private ExtLicenseValidator extLicenseValidator; // 扩展的授权文件验证器
	
	public LicenseValidator(String publicKey) {
		this(publicKey, null);
	}
	public LicenseValidator(String publicKey, ExtLicenseValidator extLicenseValidator) {
		this.publicKey = publicKey;
		this.licenseFile = new LicenseFileReader().read();
		this.extLicenseValidator = extLicenseValidator;
	}
	
	/**
	 * 系统启动时验证
	 * @return
	 */
	protected ValidationResult verifyByStart() {
		ValidationResult result = licenseFile.signature.verify(publicKey, licenseFile.getContentDigest());
		if(result == null)
			result = licenseFile.start.verify();
		if(result == null)
			result = commonVerify();
		if(result == null && extLicenseValidator != null && licenseFile.existsStartExtMap())
			result = extLicenseValidator.verifyByStart(licenseFile.startExtMap);
		return result;
	}
	
	/**
	 * 系统运行时验证
	 * @return
	 */
	protected ValidationResult verify() {
		ValidationResult result = commonVerify();
		if(result == null && extLicenseValidator != null && licenseFile.existsRunExtMap())
			result = extLicenseValidator.verify(licenseFile.runExtMap);
		return result;
	}
	
	/**
	 * 通用验证
	 * @return
	 */
	private ValidationResult commonVerify() {
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
}
