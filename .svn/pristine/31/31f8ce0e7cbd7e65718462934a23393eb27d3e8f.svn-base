package com.nec.asia.nic.framework.job.scheduler.dataAccess.type;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.enums.Enum;


/**
 * The Class Frequency.
 *
 * @author boon_wui
 * @since 14 April 208
 * @version 1.0
 */
public class Frequency extends Enum {
	
	/** The states idx. */
	private static Map<String, Frequency> statesIdx = new HashMap<String, Frequency>();

	/** The key. */
	private String key;
	
	/** The description. */
	private String description;
	
//	/** The Constant Specific. */
//	public static final Frequency Specific = new Frequency("1", "Specific Date/Time", "1");
//	
//	/** The Constant Daily. */
//	public static final Frequency Daily = new Frequency("2", "Daily", "2");
//	
//	/** The Constant Weekly. */
//	public static final Frequency Weekly = new Frequency("3", "Weekly", "3");
//	
//	/** The Constant Monthly. */
//	public static final Frequency Monthly = new Frequency("4", "Monthly", "4");
//	
//	/** The Constant Yearly. */
//	public static final Frequency Yearly = new Frequency("5", "Yearly", "5");
//	
//	/** The Constant RepeatAtInterval. */
//	public static final Frequency RepeatAtInterval = new Frequency("6", "Repeat at Interval", "6");	
//	
//	/** The Constant RepeatInDefinitely. */
//	public static final Frequency RepeatInDefinitely = new Frequency("7", "Repeat InDefinitely", "7");
////	public static final Frequency RepeatBetweenTime = new Frequency("8", "Repeat Between Time", "8");
	/*Phúc vietsub 6/4/2019*/
	/** The Constant Specific. */
	public static final Frequency Specific = new Frequency("1", "Ngày/giờ cụ thể", "1");
	
	/** The Constant Daily. */
	public static final Frequency Daily = new Frequency("2", "Hàng ngày", "2");
	
	/** The Constant Weekly. */
	public static final Frequency Weekly = new Frequency("3", "Hàng tuần", "3");
	
	/** The Constant Monthly. */
	public static final Frequency Monthly = new Frequency("4", "Hàng tháng", "4");
	
	/** The Constant Yearly. */
	public static final Frequency Yearly = new Frequency("5", "Hàng năm", "5");
	
	/** The Constant RepeatAtInterval. */
	public static final Frequency RepeatAtInterval = new Frequency("6", "Lặp lại trong khoảng", "6");	
	
	/** The Constant RepeatInDefinitely. */
	public static final Frequency RepeatInDefinitely = new Frequency("7", "Lặp lại vô định", "7");

	/**
 * Instantiates a new frequency.
 *
 * @param value the value
 * @param description the description
 * @param key the key
 */
private Frequency(String value, String description, String key) {
		super(value);
		this.key = key;
		this.description = description;
		statesIdx.put(this.key, this);
	}

	/**
	 * Gets the single instance of Frequency.
	 *
	 * @param key the key
	 * @return single instance of Frequency
	 */
	public static Frequency getInstance(String key) {
		return (Frequency) statesIdx.get(key);
	}

	/**
	 * Gets the all instances.
	 *
	 * @return the all instances
	 */
	public static Collection getAllInstances() {
		return statesIdx.values();
	}

	/**
	 * Gets the key.
	 *
	 * @return the key
	 */
	public String getKey() {
		return key;
	}

	/**
	 * Gets the description.
	 *
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * Equals.
	 *
	 * @param key the key
	 * @return true, if successful
	 */
	public boolean equals(String key) {
		if (String.valueOf(this.key).equals(key)) {
			return true;
		} else {
			return false;
		}
	}
}