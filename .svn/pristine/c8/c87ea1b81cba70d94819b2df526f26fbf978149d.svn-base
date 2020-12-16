package com.nec.asia.nic.framework.job.scheduler.dataAccess.type;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.enums.Enum;


/**
 * The Class ActionOnEvent.
 *
 * @author boon_wui
 * @since 14 April 208
 * @version 1.0
 */
public class ActionOnEvent extends Enum {
	
	/** The states idx. */
	private static Map<String, ActionOnEvent> statesIdx = new HashMap<String, ActionOnEvent>();

	/** The key. */
	private String key;
	
	/** The description. */
	private String description;
	
	/** The Constant None. */
//	public static final ActionOnEvent None = new ActionOnEvent("1", "None", "1");
//	
//	/** The Constant NotifyEmail. */
//	public static final ActionOnEvent NotifyEmail = new ActionOnEvent("2", "Notify by Email", "2");
//	
//	/** The Constant Rerun. */
//	public static final ActionOnEvent Rerun = new ActionOnEvent("3", "Rerun", "3");
//	
//	/** The Constant RerunNotifyEmail. */
//	public static final ActionOnEvent RerunNotifyEmail = new ActionOnEvent("4", "Rerun and Notify by Email", "4");
	/*Phúc fix 6/4/2019*/
	public static final ActionOnEvent None = new ActionOnEvent("1", "Không", "1");
	
	/** The Constant NotifyEmail. */
	public static final ActionOnEvent NotifyEmail = new ActionOnEvent("2", "Notify by Email", "2");
	
	/** The Constant Rerun. */
	public static final ActionOnEvent Rerun = new ActionOnEvent("3", "Chạy lại", "3");
	
	/** The Constant RerunNotifyEmail. */
	public static final ActionOnEvent RerunNotifyEmail = new ActionOnEvent("4", "Rerun and Notify by Email", "4");

	/**
	 * Instantiates a new action on event.
	 *
	 * @param value the value
	 * @param description the description
	 * @param key the key
	 */
	private ActionOnEvent(String value, String description, String key) {
		super(value);
		this.key = key;
		this.description = description;
		statesIdx.put(this.key, this);
	}

	/**
	 * Gets the single instance of ActionOnEvent.
	 *
	 * @param key the key
	 * @return single instance of ActionOnEvent
	 */
	public static ActionOnEvent getInstance(String key) {
		return (ActionOnEvent) statesIdx.get(key);
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