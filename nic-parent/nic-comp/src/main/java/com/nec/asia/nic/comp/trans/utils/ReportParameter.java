package com.nec.asia.nic.comp.trans.utils;


/**
 * The Class ReportDefinition.
 *
 * @author Peddi Swapna
 * @version 1.0
 * @since 20 Dec 2013
 */
public class ReportParameter {
	
	/** The name. */
	private String name;
	
	/** The param type. */
	private Class paramType;
	
	/** The default value. */
	private String defaultValue;
	
	/**
	 * Gets the name.
	 *
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * Sets the name.
	 *
	 * @param name the new name
	 */
	public void setName(String name) {
		this.name = name;
	}
	
	/**
	 * Gets the param type.
	 *
	 * @return the param type
	 */
	public Class getParamType() {
		return paramType;
	}
	
	/**
	 * Sets the param type.
	 *
	 * @param paramType the new param type
	 */
	public void setParamType(Class paramType) {
		this.paramType = paramType;
	}
	
	/**
	 * Gets the default value.
	 *
	 * @return the default value
	 */
	public String getDefaultValue() {
		return defaultValue;
	}
	
	/**
	 * Sets the default value.
	 *
	 * @param defaultValue the new default value
	 */
	public void setDefaultValue(String defaultValue) {
		this.defaultValue = defaultValue;
	}
	
}
