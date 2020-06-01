package com.douglei.mini.license.client;

/**
 * 自动的授权文件验证执行器
 * @author DougLei
 */
class AutoLicenseValidateExecutor extends Thread{
	private AutoLicenseValidator validator;
	
	public AutoLicenseValidateExecutor(String name, AutoLicenseValidator validator) {
		super(name);
		this.validator = validator;
	}

	@Override
	public void run() {
		while(true) {
			try {
				Thread.sleep(10000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
			if(validator.autoVerify() != null)
				break;
			System.out.println("========> 验证结果：" + validator.getResult());
			System.out.println("========> 剩余天数： " + validator.getLeftDays());
		}
	}
}
