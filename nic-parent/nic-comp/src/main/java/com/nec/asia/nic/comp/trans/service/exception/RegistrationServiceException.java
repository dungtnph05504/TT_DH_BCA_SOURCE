package com.nec.asia.nic.comp.trans.service.exception;

import com.nec.asia.nic.framework.exception.CoreException;



public class RegistrationServiceException extends CoreException {

	/**
	 * The Constructor.
	 */
	public RegistrationServiceException() {
		super("Transaction Service Exception encountered.");
	}

	/**
	 * The Constructor.
	 * 
	 * @param message the message
	 */
	public RegistrationServiceException(String message) {
		super(message);
	}

	/**
	 * The Constructor.
	 * 
	 * @param message the message
	 * @param cause the cause
	 */
	public RegistrationServiceException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * The Constructor.
	 * 
	 * @param cause the cause
	 */
	public RegistrationServiceException(Throwable cause) {
		super("Transaction Service Exception encountered. Cause: " + cause.getMessage(), cause);
	}

}
