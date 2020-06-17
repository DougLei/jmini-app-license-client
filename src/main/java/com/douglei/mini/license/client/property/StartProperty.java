package com.douglei.mini.license.client.property;

import java.time.LocalDate;

import com.douglei.mini.license.client.ValidationResult;

/**
 * 
 * @author DougLei
 */
public class StartProperty extends DateProperty {
	public static final String name = "start";
	
	public StartProperty(String value) {
		super(name, value);
	}
	
	/**
	 * 验证有效期
	 * @return
	 */
	public ValidationResult verify() {
		if(LocalDate.now().isBefore(getDate())) {
			return new ValidationResult() {
				
				@Override
				public String getMessage() {
					return "授权文件未激活";
				}
				
				@Override
				public String getCode_() {
					return "file.not.active";
				}
			};
		}
		return null;
	}
}
