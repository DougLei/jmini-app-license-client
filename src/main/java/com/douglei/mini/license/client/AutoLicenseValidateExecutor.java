package com.douglei.mini.license.client;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

/**
 * 自动的授权文件验证执行器
 * @author DougLei
 */
public class AutoLicenseValidateExecutor implements Job{

	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		AutoLicenseValidator validator = (AutoLicenseValidator) context.getJobDetail().getJobDataMap().get("AutoLicenseValidator");
		validator.autoVerify();
		
		System.out.println("========> 验证结果：" + validator.getResult());
		System.out.println("========> 剩余天数： " + validator.getLeftDays());
	}
}
