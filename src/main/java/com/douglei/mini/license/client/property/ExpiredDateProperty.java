package com.douglei.mini.license.client.property;

import java.io.File;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

import com.douglei.mini.license.client.ValidationResult;
import com.douglei.tools.JdkSerializeUtil;

/**
 * 
 * @author DougLei
 */
public class ExpiredDateProperty extends AbstractDateProperty {
	public static final String NAME = "expired-date";
	private String lastSystemTimeFilePath; // 记录上一次系统时间的文件路径
	private int leftDays; // 剩余天数
	
	public ExpiredDateProperty(String value) {
		super(NAME, value);
		this.lastSystemTimeFilePath = System.getProperty("user.home") + File.separatorChar +"lst";
	}
	
	/**
	 * 验证截止日期
	 * @return
	 */
	public ValidationResult verify() {
		LocalDateTime current = LocalDateTime.now();
		ValidationResult result = verifySystemTime(current);
		if(result == null && (leftDays = ((int)(ChronoUnit.DAYS.between(current.toLocalDate(), getDate())+1))) <= 0) {
			result = new ValidationResult() {
				
				@Override
				public String getMessage() {
					return "授权文件已过期";
				}
				
				@Override
				public String getCode() {
					return "license.file.expired";
				}
			};
		}
		return result;
	}
	
	/**
	 * 验证系统时间是否被修改
	 * @param current
	 * @return
	 */
	private ValidationResult verifySystemTime(LocalDateTime current) {
		File lastSystemTimeFile = new File(lastSystemTimeFilePath);
		if(lastSystemTimeFile.exists()) {
			LocalDateTime lastSystemTime = JdkSerializeUtil.deserialize4File(LocalDateTime.class, lastSystemTimeFile);
			if(current.isBefore(lastSystemTime)) {
				return new ValidationResult() {
					
					@Override
					public String getMessage() {
						return "系统时间异常";
					}
					
					@Override
					public String getCode() {
						return "license.system.time.error";
					}
				};
			}
		}
		JdkSerializeUtil.serialize4File(current, lastSystemTimeFile);
		return null;
	}
	
	/**
	 *  获取剩余有效天数
	 * @return
	 */
	public int getLeftDays() {
		if(leftDays < 0)
			return 0;
		return leftDays;
	}
}
