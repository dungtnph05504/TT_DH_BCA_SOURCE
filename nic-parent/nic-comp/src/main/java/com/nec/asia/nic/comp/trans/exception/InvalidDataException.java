package com.nec.asia.nic.comp.trans.exception;

import com.nec.asia.nic.framework.exception.CoreException;

/**
 * @author chris_wong
 *
 */
public class InvalidDataException extends CoreException {

	private static final long serialVersionUID = 1L;

	/**
	 * Instantiates a new InvalidDataException exception.
	 *
	 * @param message the message
	 */
	public InvalidDataException(String message) {
		super(message);
		
	}

	/**
	 * Instantiates a new DataPackServiceException exception.
	 *
	 * @param cause the cause
	 */
	public InvalidDataException(Throwable cause) {
		super(cause);
		
	}

	/**
	 * Instantiates a new DataPackServiceException exception.
	 *
	 * @param message the message
	 * @param cause the cause
	 */
	public InvalidDataException(String message, Throwable cause) {
		super(message, cause);
		
	}
}
