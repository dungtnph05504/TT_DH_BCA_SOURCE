package com.nec.asia.nic.comp.trans.service.exception;

import com.nec.asia.nic.framework.exception.CoreException;

/**
 * @author chris_wong
 *
 */
public class InventoryRefServiceException extends CoreException {
	
	/**
	 * Instantiates a new InventoryRefServiceException exception.
	 *
	 * @param message the message
	 */
	public InventoryRefServiceException(String message) {
		super(message);
		
	}

	/**
	 * Instantiates a new InventoryRefServiceException exception.
	 *
	 * @param cause the cause
	 */
	public InventoryRefServiceException(Throwable cause) {
		super(cause);
		
	}

	/**
	 * Instantiates a new InventoryRefServiceException exception.
	 *
	 * @param message the message
	 * @param cause the cause
	 */
	public InventoryRefServiceException(String message, Throwable cause) {
		super(message, cause);
		
	}
	
	/**
	 * Instantiates a new InventoryRefServiceException exception.
	 *
	 * @param message the message
	 * @param errorCode the error code 
	 */
	public InventoryRefServiceException(String message, String errorCode) {
		super(message, errorCode);
	}
	
	/**
	 * The Constructor.
	 *
	 * @param message the message
	 * @param errorCode the error code 
	 * @param cause the cause
	 */
	public InventoryRefServiceException(String message, String errorCode, Throwable cause) {
		super(message, errorCode, cause);
	}
}
