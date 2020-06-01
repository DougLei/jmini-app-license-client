package com.douglei.mini.license.client;

/**
 * 自动的授权文件验证执行器
 * @author DougLei
 */
class AutoLicenseValidateExecutor extends Thread{
	private final long defaultSleep = 1000*60*60*24;
	private AutoLicenseValidator validator;
	
	public AutoLicenseValidateExecutor(String name, AutoLicenseValidator validator) {
		super(name);
		this.validator = validator;
	}

	@Override
	public void run() {
		while(true) {
			// TODO 要计算出具体要sleep多久
			try {
				Thread.sleep(10000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
			System.out.println("========> 验证结果：" + validator.getResult());
			System.out.println("========> 剩余天数： " + validator.getLeftDays());
			
			if(validator.autoVerify() != null)
				break;
		}
	}
}
