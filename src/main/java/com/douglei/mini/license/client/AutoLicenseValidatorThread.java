package com.douglei.mini.license.client;

import java.util.Calendar;

/**
 * 自动的授权文件验证器线程
 * @author DougLei
 */
class AutoLicenseValidatorThread extends Thread{
	private AutoLicenseValidator validator;
	
	public AutoLicenseValidatorThread(String name, AutoLicenseValidator validator) {
		super(name);
		this.validator = validator;
	}

	@Override
	public void run() {
		while(true) {
			try {
				Thread.sleep(sleep());
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			if(validator.autoVerify() != null)
				break;
			System.out.println("========> 验证结果：" + validator.getResult());
			System.out.println("========> 剩余天数： " + validator.getLeftDays());
		}
	}
	
	// 获取需要sleep的毫秒数, 获得当前日期到零点之间的毫秒数, 并加2000毫秒延迟
	private long sleep() {
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.DAY_OF_MONTH, 1);
		calendar.set(Calendar.HOUR_OF_DAY, 0);
	    calendar.set(Calendar.MINUTE, 0);
	    calendar.set(Calendar.SECOND, 0);
	    calendar.set(Calendar.MILLISECOND, 0);
		return calendar.getTimeInMillis() - System.currentTimeMillis() + 2000;
	}
}
