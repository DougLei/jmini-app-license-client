package com.douglei.mini.license.client;

/**
 * 验证结果
 * @author DougLei
 */
public abstract class ValidationResult {
	private static final Object[] EMPTY_ARRAY = new Object[0];
	
	/**
	 * 返回message
	 * @return
	 */
	public abstract String getMessage();
	
	/**
	 * 返回code, 后续可以集成国际化
	 * @return
	 */
	public abstract String getCode();
	
	/**
	 * 匹配国际化消息中的占位符参数
	 * @return
	 */
	public Object[] getParams() {
		return EMPTY_ARRAY;
	}
	
	@Override
	public String toString() {
		return getMessage();
	}
}
