package com.douglei.mini.license.client.property;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Calendar;

/**
 * 日期属性
 * @author DougLei
 */
abstract class DateProperty extends Property{
	
	protected DateProperty(String name, String value) {
		super(name, value);
	}
	
	private LocalDate date;
	protected LocalDate getDate() {
		if(date == null) {
			try {
				Calendar calendar = Calendar.getInstance();
				calendar.setTime(new SimpleDateFormat("yyyy-MM-dd").parse(value));
				date = LocalDate.of(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH)+1, calendar.get(Calendar.DAY_OF_MONTH));
			} catch (ParseException e) {
				e.printStackTrace();
			}		
		}
		return date;
	}
}
