package com.douglei.mini.license.client;

import java.util.Calendar;

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
//				Thread.sleep(sleep());
				Thread.sleep(5000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
			System.out.println("========> 验证结果：" + validator.getResult());
			System.out.println("========> 剩余天数： " + validator.getLeftDays());
			
			if(validator.autoVerify() != null)
				break;
		}
	}
	
	// 获取需要sleep的毫秒数
	private long sleep() {
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.DAY_OF_MONTH, 1);
		calendar.set(Calendar.HOUR_OF_DAY, 0);
	    calendar.set(Calendar.MINUTE, 0);
	    calendar.set(Calendar.SECOND, 0);
	    calendar.set(Calendar.MILLISECOND, 0);
		return calendar.getTimeInMillis() - System.currentTimeMillis();
	}
	
	public static void main(String[] args) {
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.DAY_OF_MONTH, 0);
		calendar.set(Calendar.HOUR_OF_DAY, 0);
	    calendar.set(Calendar.MINUTE, 0);
	    calendar.set(Calendar.SECOND, 0);
	    calendar.set(Calendar.MILLISECOND, 0);
		System.out.println(calendar.getTimeInMillis() - System.currentTimeMillis());
	}
}
