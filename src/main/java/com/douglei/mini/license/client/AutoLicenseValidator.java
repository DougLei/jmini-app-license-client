package com.douglei.mini.license.client;

/**
 * 自动的授权文件验证器
 * @author DougLei
 */
public class AutoLicenseValidator extends LicenseValidator{
	private boolean start; // 标识是否已经启动
	private ValidationResult result; // 验证结果
	
	public AutoLicenseValidator(String publicKey) {
		super(publicKey);
	}
	
	// 实现定时验证授权文件的线程 
	private final String name = "auto.license.validator"; 
	private final Thread validatorThread = new AutoLicenseValidateExecutor(name, this);
	
	/**
	 * 启动验证
	 * @return 返回启动时验证的结果
	 */
	public ValidationResult start() {
		if(!start) {
			result = verifyFirst();
			if(result == null) {
				validatorThread.start();
				start = true;
			}
		}
		return result;
	}
	
	/**
	 * 自动验证
	 * @return 并返回验证结果
	 */
	ValidationResult autoVerify() {
		if(result == null)
			result = verify();
		return result;
	}
	
	/**
	 * 获取验证结果, 验证结果只要为null, 则证明验证通过
	 * @return
	 */
	public ValidationResult getResult() {
		return result;
	}
}
