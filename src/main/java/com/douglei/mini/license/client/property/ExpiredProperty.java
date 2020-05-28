package com.douglei.mini.license.client.property;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.douglei.mini.license.client.ValidationResult;

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
		if((getExpiredDate().getTime() - new Date().getTime()) < 0) {
			return new ValidationResult() {
				
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
