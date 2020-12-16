package com.nec.asia.nic.framework.job.scheduler.exception;

import com.nec.asia.nic.framework.exception.CoreException;


/**
 * The Class JobScheduleException.
 *
 * @author boon_wui
 * @since 14 April 208
 * @version 1.0
 */
public class JobScheduleException extends CoreException{

	/**
	 * The Constructor.
	 */
	public JobScheduleException() {
		super("Job Scheduling Exception.");
	}

	/**
	 * The Constructor.
	 * 
	 * @param message
	 *            the message
	 */
	public JobScheduleException(String message) {
		super(message);
	}

	/**
	 * The Constructor.
	 * 
	 * @param message
	 *            the message
	 * @param cause
	 *            the cause
	 */
	public JobScheduleException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * The Constructor.
	 * 
	 * @param cause
	 *            the cause
	 */
	public JobScheduleException(Throwable cause) {
		super("Job Scheduling Exception. Cause: "+cause.getMessage(),cause);
	}
}
