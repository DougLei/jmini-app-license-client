package com.douglei.mini.license.client;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.douglei.mini.license.client.property.CustomProperty;

/**
 * 授权验证app入口
 * @author DougLei
 */
public final class LicenseValidateApp {
	private static final Logger logger = LoggerFactory.getLogger(LicenseValidateApp.class);
	private String publicKey; // 公钥
	private LicenseFile licenseFile; // 授权文件实例
	private boolean startup;
	protected ValidationResult result; // 验证结果
	
	public LicenseValidateApp(String publicKey) {
		this.publicKey = publicKey;
		this.licenseFile = new LicenseFileReader().read();
	}

	/**
	 * 进行验证, 并返回验证结果
	 * @return 
	 */
	ValidationResult verifyByThread() {
		if(result == null) 
			result = licenseFile.expiredDate.verify();
		if(result == null && licenseFile.ip != null)
			licenseFile.ip.verify();
		if(result == null && licenseFile.mac != null)
			result = licenseFile.mac.verify();
		return result;
	}
	
	/**
	 * 启动验证
	 */
	public void startup() {
		if(startup)
			return;
		
		startup = true;
		
		result = licenseFile.signature.verify(publicKey, licenseFile.getContentDigest());
		if(result == null)
			result = licenseFile.effectiveDate.verify();
		if(result == null)
			result = verifyByThread();
		
		logger.info("系统启动, 对授权文件进行验证, {}", this);
		
		if(result == null) 
			new LicenseValidateThread("license.validate.thread", this).start();
	}
	
	/**
	 * 获取验证结果, 验证结果只要为null, 则证明验证通过
	 * @return
	 */
	public ValidationResult getResult() {
		if(startup)
			return result;
		
		return new ValidationResult() {
			
			@Override
			public String getMessage() {
				return "验证程序未启动";
			}
			
			@Override
			public String getCode() {
				return "license.app.unstartup";
			}
		};
	}
	
	/**
	 * 获取剩余有效天数
	 * @return
	 */
	public int getLeftDays() {
		if(startup)
			return licenseFile.expiredDate.getLeftDays();
		return -1;
	}
	
	/**
	 * 获取指定name的自定义授权信息的值
	 * @param name
	 * @return
	 */
	public String getCustomValue(String name){
		if(startup && licenseFile.customs != null) {
			for(CustomProperty custom : licenseFile.customs) {
				if(custom.getName().equals(name)) 
					return custom.getValue();
			}
		}
		return null;
	}
	
	@Override
	public String toString() {
		if(startup)
			return String.format("授权文件验证结果: %s, 剩余有效天数: %d", result==null?"正常":result, getLeftDays());
		return "验证程序未启动";
	}
}