package com.douglei.mini.license.client;

import org.quartz.SchedulerFactory;

/**
 * 自动的授权文件验证器
 * @author DougLei
 */
public class AutoLicenseValidator extends LicenseValidator{
	private SchedulerFactory schedulerFactory;
	private boolean start; // 标识是否已经启动
	private ValidationResult result; // 验证结果
	
	public AutoLicenseValidator(String publicKey, SchedulerFactory schedulerFactory) {
		super(publicKey);
		this.schedulerFactory = schedulerFactory;
	}

	/**
	 * 启动验证
	 * @return 返回自身实例
	 */
	public AutoLicenseValidator start() {
		if(!start) {
			start = true;
			
//			Scheduler scheduler = schedulerFactory.getScheduler();
			
			result = verifyFirst();
		}
		return this;
	}
	
	/**
	 * 获取验证结果, 验证结果只要为null, 则证明验证通过
	 * @return
	 */
	public ValidationResult getResult() {
		return result;
	}
	
//	public static void main(String[] args) throws Exception {
//		SchedulerFactory schedulerFactory = new StdSchedulerFactory();
//		Scheduler scheduler = schedulerFactory.getScheduler();
//		
//		JobDetail job = JobBuilder.newJob(MyJob.class).withIdentity("test", "license").build();
//		Trigger trigger = TriggerBuilder.newTrigger()
//				.startNow()
//				.withSchedule(CronScheduleBuilder.cronSchedule("* * * * * ? *"))
//				.build();
//		
//		scheduler.scheduleJob(job, trigger);
//		scheduler.start();
//	}
}
