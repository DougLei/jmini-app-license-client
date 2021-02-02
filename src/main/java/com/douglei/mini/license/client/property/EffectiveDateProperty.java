package com.douglei.mini.license.client.property;

import java.time.LocalDate;

import com.douglei.mini.license.client.ValidationResult;

/**
 * 
 * @author DougLei
 */
public class EffectiveDateProperty extends AbstractDateProperty {
	public static final String NAME = "effective-date";
	
	public EffectiveDateProperty(String value) {
		super(NAME, value);
	}
	
	/**
	 * 验证授权文件的生效日期
	 * @return
	 */
	public ValidationResult verify() {
		if(LocalDate.now().isBefore(getDate())) {
			return new ValidationResult() {
				
				@Override
				public String getMessage() {
					return "授权文件未到生效日期";
				}
				
				@Override
				public String getCode() {
					return "license.file.not.effective.date";
				}
			};
		}
		return null;
	}
}
