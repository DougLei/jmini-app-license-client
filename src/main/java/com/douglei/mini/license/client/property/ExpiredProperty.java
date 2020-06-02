package com.douglei.mini.license.client.property;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Calendar;

import com.douglei.mini.license.client.ValidationResult;

/**
 * 
 * @author DougLei
 */
public class ExpiredProperty extends Property {
	private int leftDays; // 剩余天数
	
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
		if((leftDays = ((int)(ChronoUnit.DAYS.between(LocalDate.now(), getExpiredDate())+1))) <= 0) {
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
	
	private LocalDate expiredDate;
	private LocalDate getExpiredDate() {
		if(expiredDate == null) {
			try {
				Calendar calendar = Calendar.getInstance();
				calendar.setTime(new SimpleDateFormat("yyyy-MM-dd").parse(value));
				expiredDate = LocalDate.of(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH)+1, calendar.get(Calendar.DAY_OF_MONTH));
			} catch (ParseException e) {
				e.printStackTrace();
			}		
		}
		return expiredDate;
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
