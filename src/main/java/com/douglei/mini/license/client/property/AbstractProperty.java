package com.douglei.mini.license.client.property;

/**
 * 
 * @author DougLei
 */
abstract class AbstractProperty {
	protected String name;
	protected String value;
	
	protected AbstractProperty(String name, String value){
		this.name = name;
		this.value = value;
	}
	
	/**
	 * 获取属性的名称
	 * @return
	 */
	public String getName() {
		return name;
	}

	/**
	 * 获取属性的值
	 * @return
	 */
	public String getValue() {
		return value;
	}

	/**
	 * 获取属性的配置内容: name=value
	 * @return
	 */
	public String getContent() {
		return name + '=' + value;
	}
}
