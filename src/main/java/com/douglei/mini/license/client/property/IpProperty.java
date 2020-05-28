package com.douglei.mini.license.client.property;

import com.douglei.mini.license.client.ValidationResult;

/**
 * 
 * @author DougLei
 */
public class IpProperty extends Property {

	public IpProperty(String value) {
		super("ip", value);
	}
	
	/**
	 * 验证ip
	 * @return
	 */
	public ValidationResult verify() {
		return null;
	}
}
