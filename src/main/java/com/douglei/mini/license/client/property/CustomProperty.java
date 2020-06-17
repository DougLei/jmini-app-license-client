package com.douglei.mini.license.client.property;

/**
 * 
 * @author DougLei
 */
public class CustomProperty extends Property {

	public CustomProperty(String name, String value) {
		super(name, value);
	}
	
	public String getName() {
		return name;
	}
	public String getValue() {
		return value;
	}
}
