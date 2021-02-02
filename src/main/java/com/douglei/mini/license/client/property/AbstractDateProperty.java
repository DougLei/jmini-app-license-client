package com.douglei.mini.license.client.property;

import java.time.LocalDate;
import java.util.Calendar;

import com.douglei.tools.datatype.DateFormatUtil;

/**
 * 日期属性
 * @author DougLei
 */
abstract class AbstractDateProperty extends AbstractProperty {
	private LocalDate date;
	
	protected AbstractDateProperty(String name, String value) {
		super(name, value);
		
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(DateFormatUtil.getFormat("yyyy-MM-dd").parse(value));
		this.date = LocalDate.of(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH)+1, calendar.get(Calendar.DAY_OF_MONTH));	
	}
	
	
	
	/**
	 * 获取日期实例
	 * @return
	 */
	protected final LocalDate getDate() {
		return date;
	}
}
