package com.douglei.mini.license.client;

/**
 * 自动的授权文件验证器
 * @author DougLei
 */
public class AutoLicenseValidator extends LicenseValidator{
	private final Thread validatorThread;// 定时验证授权文件的线程 
	private boolean start; // 标识是否已经启动
	private ValidationResult result; // 验证结果
	
	public AutoLicenseValidator(String publicKey) {
		super(publicKey);
		validatorThread = new AutoLicenseValidatorThread("auto.license.validator", this);
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
	 * 启动验证
	 */
	public void start() {
		if(!start) {
			start = true;
			result = verifyFirst();
			if(result == null) 
				validatorThread.start();
		}
	}
	
	/**
	 * 获取验证结果, 验证结果只要为null, 则证明验证通过
	 * @return
	 */
	public ValidationResult getResult() {
		return result;
	}
	
	/**
	 * 是否启动
	 * @return
	 */
	public boolean isStart() {
		return start;
	}
}