package com.nec.asia.nic.framework.admin.code.exception;

import com.nec.asia.nic.framework.exception.CoreException;


/**
 * @author aparna_sharma
 *
 */
public class FlexiGridException extends CoreException {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Instantiates a new FlexiGridException exception.
	 *
	 * @param message the message
	 */
	public FlexiGridException(String message) {
		super(message);
		
	}

	/**
	 * Instantiates a new FlexiGridException exception.
	 *
	 * @param cause the cause
	 */
	public FlexiGridException(Throwable cause) {
		super(cause);
		
	}

	/**
	 * Instantiates a new FlexiGridException exception.
	 *
	 * @param message the message
	 * @param cause the cause
	 */
	public FlexiGridException(String message, Throwable cause) {
		super(message, cause);
		
	}
}
