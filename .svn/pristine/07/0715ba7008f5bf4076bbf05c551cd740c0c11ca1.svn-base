package com.nec.asia.nic.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Locale;
import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Currently not use
 * 
 * @author bright_zheng
 *
 */
public class Config {
	private static final Logger logger = LoggerFactory.getLogger(Config.class);
	private static String GLOBAL_CONFIG_FILE_PRE = "/GlobalConfig";
	private static String GLOBAL_CONFIG_FILE_SUFFIX = ".properties";

	private static ResourceBundle rb;
	

	static{
		InputStream is = null;
		String propFile = "";
		try {
			Locale locale = Locale.getDefault();
			propFile = GLOBAL_CONFIG_FILE_PRE + "_" + locale.toString() + GLOBAL_CONFIG_FILE_SUFFIX;
			is = Config.class.getClassLoader().getResourceAsStream(propFile);
			rb = new PropertyResourceBundle(is);
		} catch (Exception e) {
			logger.error("Could not initialize gloabal config file of {}", propFile);			
		} finally {
			try {
				if (is != null)
					is.close();
			} catch (IOException exception) {
				//ignore
			}
		}
	}

	public Config() {
	}

	/**
	 * 
	 * 
	 * @param name
	 * @return
	 */
	public static String getString(String name){
		return getString(name,"");
	}

	/**
	 * 
	 * 
	 * @param name
	 * @param valDefine
	 * @return
	 */
	public static String getString(String name, String defaultValue) {
		String ret = defaultValue;
		//ret = properties.getProperty(name);
		ret = rb.getString(name);
		if (ret == null || ret.length() == 0)
			ret = defaultValue;
		return ret;
	}

	/**
	 * 
	 * 
	 * @param name
	 * @return
	 */
	public static int getInt(String name) {
		return getInt(name,-1);
	}

	/**
	 * 
	 * 
	 * @param name
	 * @param valDefine
	 * @return
	 */
	public static int getInt(String name, int defaultValue) {
		int ret = defaultValue;
		//String temp = properties.getProperty(name);
		String temp = rb.getString(name);
		if (temp != null && temp.length() > 0)
			ret = Integer.parseInt(temp);
		return ret;
	}


	/**
	 * 
	 * 
	 * @param name
	 * @return
	 */
	public static boolean getBoolean(String name) {
		return getBoolean(name, false);
	}

	/**
	 * 
	 * 
	 * @param name
	 * @param valDefine
	 * @return
	 */
	public static boolean getBoolean(String name, boolean defaultValue) {
		boolean ret = defaultValue;
		String temp = rb.getString(name);
		if (temp != null && temp.length() > 0)
			ret = Boolean.getBoolean(temp);		
		return ret;
	}
}