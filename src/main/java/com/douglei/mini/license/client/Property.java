package com.douglei.mini.license.client;

/**
 * 
 * @author DougLei
 */
enum Property {
	TYPE,
	EXPIRED,
	IP,
	MAC,
	SIGNATURE;
	
	protected String name;
	protected String configValue;
	protected Object value;
}
