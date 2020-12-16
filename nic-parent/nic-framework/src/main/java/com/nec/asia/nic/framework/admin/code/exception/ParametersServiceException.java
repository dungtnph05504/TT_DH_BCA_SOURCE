package com.nec.asia.nic.framework.admin.code.exception;

import com.nec.asia.nic.framework.exception.CoreException;

/**
 * @author chris_wong
 *
 */
public class ParametersServiceException extends CoreException {
	
	/**
	 * Instantiates a new ParametersServiceException exception.
	 *
	 * @param message the message
	 */
	public ParametersServiceException(String message) {
		super(message);
		
	}

	/**
	 * Instantiates a new ParametersServiceException exception.
	 *
	 * @param cause the cause
	 */
	public ParametersServiceException(Throwable cause) {
		super(cause);
		
	}

	/**
	 * Instantiates a new ParametersServiceException exception.
	 *
	 * @param message the message
	 * @param cause the cause
	 */
	public ParametersServiceException(String message, Throwable cause) {
		super(message, cause);
		
	}
}
