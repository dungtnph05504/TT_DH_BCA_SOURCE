package com.nec.asia.nic.comp.trans.service.exception;

import com.nec.asia.nic.framework.exception.CoreException;

/**
 * @author setia_budiyono
 *
 */
/* 
 * Modification History:
 *  
 * 07 Feb 2014 (chris): add error code for handle issuance date for suspend/cancel. 
 */
public class IssuanceServiceException extends CoreException {
	
	/**
	 * Instantiates a new IssuanceServiceException exception.
	 *
	 * @param message the message
	 */
	public IssuanceServiceException(String message) {
		super(message);
		
	}

	/**
	 * Instantiates a new IssuanceServiceException exception.
	 *
	 * @param cause the cause
	 */
	public IssuanceServiceException(Throwable cause) {
		super(cause);
		
	}

	/**
	 * Instantiates a new IssuanceServiceException exception.
	 *
	 * @param message the message
	 * @param cause the cause
	 */
	public IssuanceServiceException(String message, Throwable cause) {
		super(message, cause);
		
	}
	


	/**
	 * Instantiates a new TransactionServiceException exception.
	 *
	 * @param message the message
	 * @param errorCode the error code 
	 */
	public IssuanceServiceException(String message, String errorCode) {
		super(message, errorCode);
	}
	
	/**
	 * The Constructor.
	 *
	 * @param message the message
	 * @param errorCode the error code 
	 * @param cause the cause
	 */
	public IssuanceServiceException(String message, String errorCode, Throwable cause) {
		super(message, errorCode, cause);
	}
}
