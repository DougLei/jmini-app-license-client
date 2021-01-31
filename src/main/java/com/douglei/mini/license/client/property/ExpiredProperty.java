package com.douglei.mini.license.client.property;

import java.io.File;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

import com.douglei.mini.license.client.ValidationResult;
import com.douglei.tools.serialize.JdkSerializeProcessor;

/**
 * 
 * @author DougLei
 */
public class ExpiredProperty extends DateProperty {
	public static final String name = "expired";
	private final String lastSystemTimeFilePath = System.getProperty("user.home") + File.separatorChar + ".lst" + File.separatorChar + "lst"; // 记录上一次系统时间的文件路径
	private int leftDays; // 剩余天数
	
	public ExpiredProperty(String value) {
		super(name, value);
	}
	
	public String getValue() {
		return value;
	}
	
	/**
	 * 验证有效期
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
				public String getCode_() {
					return "file.expired";
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
			LocalDateTime lastSystemTime = JdkSerializeProcessor.deserializeFromFile(LocalDateTime.class, lastSystemTimeFile);
			if(current.isBefore(lastSystemTime)) {
				return new ValidationResult() {
					
					@Override
					public String getMessage() {
						return "系统时间错误";
					}
					
					@Override
					public String getCode_() {
						return "system.time.error";
					}
				};
			}
		}
		JdkSerializeProcessor.serialize2File(current, lastSystemTimeFilePath);
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
