package com.nec.asia.nic.framework.job.scheduler.exception;

import com.nec.asia.nic.framework.exception.CoreException;


/**
 * The Class UpdateJobException.
 *
 * @author boon_wui
 * @since 14 April 208
 * @version 1.0
 */
public class UpdateJobException extends CoreException{

	/**
	 * The Constructor.
	 */
	public UpdateJobException() {
		super("Failed to update Job.");
	}

	/**
	 * The Constructor.
	 * 
	 * @param message
	 *            the message
	 */
	public UpdateJobException(String message) {
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
	public UpdateJobException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * The Constructor.
	 * 
	 * @param cause
	 *            the cause
	 */
	public UpdateJobException(Throwable cause) {
		super("Failed to update job. Cause: " + cause.getMessage(), cause);
	}
}
