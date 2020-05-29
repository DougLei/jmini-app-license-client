package com.douglei.mini.license.client.property;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.douglei.mini.license.client.ValidationResult;
import com.douglei.tools.utils.serialize.JdkSerializeProcessor;

/**
 * 
 * @author DougLei
 */
public class ExpiredProperty extends Property {
	
	public ExpiredProperty(String value) {
		super("expired", value);
	}
	
	public String getValue() {
		return value;
	}
	
	/**
	 * 验证有效期
	 * @return
	 */
	public ValidationResult verify() {
		Date current = new Date();
		ValidationResult result = verifySystemTime(current);
		if(result == null && (getExpiredDate().getTime() - current.getTime()) < 0) {
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
	 * 记录上一次系统时间的文件路径
	 */
	private final String lastSystemTimeFilePath = System.getProperty("user.home") + File.separatorChar + ".lst" + File.separatorChar + "lst";
	
	/**
	 * 验证系统时间是否被修改
	 * @param current
	 * @return
	 */
	private ValidationResult verifySystemTime(Date current) {
		File lastSystemTimeFile = new File(lastSystemTimeFilePath);
		if(lastSystemTimeFile.exists()) {
			Date lastSystemTime = JdkSerializeProcessor.deserializeFromFile(Date.class, lastSystemTimeFile);
			if((lastSystemTime.getTime() - current.getTime()) > 0) {
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
		}else {
			JdkSerializeProcessor.serialize2File(current, lastSystemTimeFilePath);
		}
		return null;
	}
	
	private Date expiredDate;
	private Date getExpiredDate() {
		if(expiredDate == null) {
			try {
				expiredDate = new SimpleDateFormat("yyyy-MM-dd").parse(value);
			} catch (ParseException e) {
			}		
		}
		return expiredDate;
	}
}
