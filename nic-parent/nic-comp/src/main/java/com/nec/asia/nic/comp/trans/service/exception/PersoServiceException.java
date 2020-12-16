package com.nec.asia.nic.comp.trans.service.exception;

import com.nec.asia.nic.framework.exception.CoreException;

/**
 * @author chris_wong
 *
 */
public class PersoServiceException extends CoreException {
	
	/**
	 * Instantiates a new PersoServiceException exception.
	 *
	 * @param message the message
	 */
	public PersoServiceException(String message) {
		super(message);
		
	}

	/**
	 * Instantiates a new PersoServiceException exception.
	 *
	 * @param cause the cause
	 */
	public PersoServiceException(Throwable cause) {
		super(cause);
		
	}

	/**
	 * Instantiates a new PersoServiceException exception.
	 *
	 * @param message the message
	 * @param cause the cause
	 */
	public PersoServiceException(String message, Throwable cause) {
		super(message, cause);
		
	}
}
