package com.douglei.mini.license.client;

import java.util.Map;

/**
 * 针对扩展信息的授权验证器, 需要由设计扩展信息的人员实现该接口
 * @author DougLei
 */
public interface ExtLicenseValidator {

	/**
	 * 系统启动时的验证
	 * @param extMap 对应验证模式的扩展信息map集合
	 * @return 返回null, 表示验证通过
	 */
	default ValidationResult verifyByStart(Map<String, String> extMap) {
		return null;
	}

	/**
	 * 系统运行时的验证
	 * @param extMap 对应验证模式的扩展信息map集合
	 * @return 返回null, 表示验证通过
	 */
	default ValidationResult verify(Map<String, String> extMap) {
		return null;
	}
}
