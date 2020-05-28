package com.douglei.mini.license.client.property;

import com.douglei.mini.license.client.ValidationResult;

/**
 * 
 * @author DougLei
 */
public class MacProperty extends Property {

	public MacProperty(String value) {
		super("mac", value);
	}
	
	/**
	 * 验证mac
	 * @return
	 */
	public ValidationResult verify() {
		return null;
	}
}
