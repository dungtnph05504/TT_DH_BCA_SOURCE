package com.nec.asia.nic.utils;

import java.lang.reflect.InvocationTargetException;
import java.util.Date;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.beanutils.converters.DateConverter;
import org.apache.commons.beanutils.converters.SqlDateConverter;
import org.apache.commons.lang.StringUtils;


public class BaseDTOMapper {

	static {
		ConvertUtils.register(new DateConverter(null), java.util.Date.class);  
        ConvertUtils.register(new SqlDateConverter(null), java.sql.Date.class);  
	}
	public static final String YES_FLAG = "Y";
	public static final String NO_FLAG = "N";
	
	public static void copyProperties(Object target, Object source) {
		try {
			BeanUtils.copyProperties(target, source);
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
	}
	
	protected String convertDateToString8(Date date) {
		String dateString = null;
		if (date!=null) {
			dateString = DateUtil.parseDate(date, DateUtil.FORMAT_DDMMYYYY);
		}
		return dateString;
	}
	
	protected String convertDateToString11(Date date) {
		String dateString = null;
		if (date!=null) {
			dateString = DateUtil.parseDate(date, DateUtil.FORMAT_DD_MMM_YYYY);
		}
		return dateString;
	}
	
	protected Boolean convertFlagToBoolean(String flag) {
		Boolean result = null;
		if (StringUtils.equals(flag, YES_FLAG)) {
			result = Boolean.TRUE;
		} else if (StringUtils.equals(flag, NO_FLAG)) {
			result = Boolean.FALSE;
		}
		return result;
	}
	
	protected String convertBooleanToFlag(Boolean flagBoolean) {
		String result = null;
		if (Boolean.TRUE.equals(flagBoolean)) {
			result = YES_FLAG;
		} else if (Boolean.FALSE.equals(flagBoolean)) {
			result = NO_FLAG;
		}
		return result;
	}
}
