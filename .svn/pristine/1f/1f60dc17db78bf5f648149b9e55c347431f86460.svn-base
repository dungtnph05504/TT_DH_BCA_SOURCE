package com.nec.asia.nic.framework.admin.code.domain.type;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.enums.Enum;

/**
 * Parameters type enumerator.
 * 
 * @author chris_wong
 * @version 1.0
 */
public class ParameterType extends Enum {

	private static final long serialVersionUID = -4400424809650413761L;

	private static Map<String, ParameterType> statesIdx = new HashMap<String, ParameterType>();

	private String key;

	public static final ParameterType Date = new ParameterType("DATE", "DATE");
	public static final ParameterType Datetime = new ParameterType("DATETIME", "DATETIME");
	public static final ParameterType Code = new ParameterType("CODE", "CODE");
	public static final ParameterType Boolean = new ParameterType("BOOLEAN", "BOOLEAN");
	public static final ParameterType Integer = new ParameterType("INTEGER", "INTEGER");
	public static final ParameterType NumberRange = new ParameterType("NUMBER_RANGE", "NUMBER_RANGE");
	public static final ParameterType Percentage = new ParameterType("PERCENTAGE", "PERCENTAGE");
	public static final ParameterType String = new ParameterType("STRING", "STRING");
	public static final ParameterType LongText = new ParameterType("LONG_TEXT","LONG_TEXT");
	public static final ParameterType Object = new ParameterType("OBJECT", "OBJECT");

	private ParameterType(String value, String key) {
		super(value);
		this.key = key;
		statesIdx.put(this.key, this);
	}

	public static ParameterType getInstance(String key) {
		return (ParameterType) statesIdx.get(key);
	}

	public static Collection<ParameterType> getAllInstances() {
		return statesIdx.values();
	}

	public String getKey() {
		return key;
	}

	public boolean equals(String key) {
		if ((this.key).equals(key)) {
			return true;
		} else {
			return false;
		}
	}
}
