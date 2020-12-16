package com.nec.asia.nic.framework.job.scheduler.exception;

import com.nec.asia.nic.framework.exception.CoreException;


/**
 * The Class CreateJobExecutionException.
 *
 * @author boon_wui
 * @since 14 April 208
 * @version 1.0
 */
public class CreateJobExecutionException extends CoreException {

	/**
	 * The Constructor.
	 */
	public CreateJobExecutionException() {
		super("Failed to create Job Execution History.");
	}

	/**
	 * The Constructor.
	 * 
	 * @param message
	 *            the message
	 */
	public CreateJobExecutionException(String message) {
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
	public CreateJobExecutionException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * The Constructor.
	 * 
	 * @param cause
	 *            the cause
	 */
	public CreateJobExecutionException(Throwable cause) {
		super("Failed to create job execution history. Cause: "+cause.getMessage(), cause);
	}
}
