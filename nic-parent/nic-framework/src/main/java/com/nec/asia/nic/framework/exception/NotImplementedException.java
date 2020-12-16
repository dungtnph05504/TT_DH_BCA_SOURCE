package com.nec.asia.nic.framework.exception;

import com.nec.asia.nic.framework.exception.CoreException;


/**
 * Not Implemented Exception.
 * 
 * @author alvin_chua
 * @version 1.0
 * @since 24 July 2012
 *  
 */
public class NotImplementedException extends RuntimeException {

	/**
	 * The Constructor.
	 */
	public NotImplementedException() {
		super("Not Implemented Exception encountered.");
	}

	/**
	 * The Constructor.
	 * 
	 * @param message
	 *            the message
	 */
	public NotImplementedException(String message) {
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
	public NotImplementedException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * The Constructor.
	 * 
	 * @param cause
	 *            the cause
	 */
	public NotImplementedException(Throwable cause) {
		super(
				"Not Implemented Exception encountered. Cause: "
						+ cause.getMessage(), cause);
	}

}
