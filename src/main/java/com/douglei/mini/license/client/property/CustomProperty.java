package com.douglei.mini.license.client.property;

/**
 * 自定义授权属性
 * @author DougLei
 */
public class CustomProperty extends AbstractProperty {

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
