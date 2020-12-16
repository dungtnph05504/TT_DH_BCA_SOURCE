package com.nec.asia.nic.comp.trans.service.exception;

import com.nec.asia.nic.framework.exception.CoreException;

/**
 * @author chris_wong
 *
 */
public class DataPackServiceException extends CoreException {
	
	/**
	 * Instantiates a new DataPackServiceException exception.
	 *
	 * @param message the message
	 */
	public DataPackServiceException(String message) {
		super(message);
		
	}

	/**
	 * Instantiates a new DataPackServiceException exception.
	 *
	 * @param cause the cause
	 */
	public DataPackServiceException(Throwable cause) {
		super(cause);
		
	}

	/**
	 * Instantiates a new DataPackServiceException exception.
	 *
	 * @param message the message
	 * @param cause the cause
	 */
	public DataPackServiceException(String message, Throwable cause) {
		super(message, cause);
		
	}
}
