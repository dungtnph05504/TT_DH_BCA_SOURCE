package com.nec.asia.nic.framework.job.scheduler.dataAccess.type;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.enums.Enum;


/**
 * The Class JobStatus.
 *
 * @author boon_wui
 * @since 14 April 208
 * @version 1.0
 */
public class JobStatus extends Enum {
	
	/** The states idx. */
	private static Map<String, JobStatus> statesIdx = new HashMap<String, JobStatus>();

	/** The key. */
	private String key;
	
	/** The description. */
	private String description;
	
	/** The Constant Stopped. */
	public static final JobStatus Stopped = new JobStatus("S", "Stopped", "S");
	
	/** The Constant Waiting. */
	public static final JobStatus Waiting = new JobStatus("W", "Waiting", "W");
	
	/** The Constant Running. */
	public static final JobStatus Running = new JobStatus("R", "Running", "R");

	/**
	 * Instantiates a new job status.
	 *
	 * @param value the value
	 * @param description the description
	 * @param key the key
	 */
	private JobStatus(String value, String description, String key) {
		super(value);
		this.key = key;
		this.description = description;
		statesIdx.put(this.key, this);
	}

	/**
	 * Gets the single instance of JobStatus.
	 *
	 * @param key the key
	 * @return single instance of JobStatus
	 */
	public static JobStatus getInstance(String key) {
		return (JobStatus) statesIdx.get(key);
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