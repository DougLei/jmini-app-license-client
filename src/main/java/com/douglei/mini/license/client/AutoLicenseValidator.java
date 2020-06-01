package com.douglei.mini.license.client;

import org.quartz.CronScheduleBuilder;
import org.quartz.JobBuilder;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SchedulerFactory;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;

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
			result = verifyFirst();
			if(result != null) {
				try {
					Scheduler scheduler = schedulerFactory.getScheduler();
					JobDataMap data  = new JobDataMap();
					data.put("AutoLicenseValidator", this);
					
					JobDetail job = JobBuilder.newJob(AutoLicenseValidateExecutor.class).withIdentity("license", "validator").usingJobData(data).build();
					Trigger trigger = TriggerBuilder.newTrigger().withSchedule(CronScheduleBuilder.cronSchedule("10 0 0 * * ? *")).build(); // 每晚零点10秒执行
					scheduler.scheduleJob(job, trigger);
					scheduler.start();
					start = true;
				} catch (SchedulerException e) {
					result = new ValidationResult() {
						@Override
						public String getMessage() {
							return "启动自动验证授权文件功能失败";
						}
						@Override
						protected String getCode_() {
							return "start.auto.license.validator.fail";
						}
					};
				}
			}
		}
		return this;
	}
	
	/**
	 * 自动验证
	 */
	void autoVerify() {
		if(result == null)
			result = verify();
	}
	
	/**
	 * 获取验证结果, 验证结果只要为null, 则证明验证通过
	 * @return
	 */
	public ValidationResult getResult() {
		return result;
	}

	@Override
	public int getLeftDays() {
		if(result == null)
			return super.getLeftDays();
		return 0;
	}
}
