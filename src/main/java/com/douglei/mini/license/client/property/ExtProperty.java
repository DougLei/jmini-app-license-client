package com.douglei.mini.license.client.property;

import com.douglei.mini.license.client.ExtValidateMode;

/**
 * 
 * @author DougLei
 */
public class ExtProperty extends Property {
	private String originName;
	private ExtValidateMode vm;
	
	public ExtProperty(String name, String value, boolean init) {
		super(name, value);
		if(init) { // 是否初始化扩展信息的内容
			int dot = name.lastIndexOf(".");
			this.originName = name.substring(0, dot);
			this.vm = toVM(name.substring(dot+1));
		}
	}
	
	/**
	 * 根据验证模式名, 获取对应的验证模式枚举实例
	 * @param vmName
	 * @return
	 */
	private ExtValidateMode toVM(String vmName) {
		if(ExtValidateMode.START.name().equals(vmName))
			return ExtValidateMode.START;
		if(ExtValidateMode.RUN.name().equals(vmName))
			return ExtValidateMode.RUN;
		return ExtValidateMode.ALL;
	}

	public String getOriginName() {
		return originName;
	}
	public String getValue() {
		return value;
	}
	public boolean startVM() {
		return vm == ExtValidateMode.START || vm == ExtValidateMode.ALL;
	}
	public boolean runVM() {
		return vm == ExtValidateMode.RUN || vm == ExtValidateMode.ALL;
	}
}
