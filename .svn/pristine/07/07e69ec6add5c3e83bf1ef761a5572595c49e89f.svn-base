/* Copyright 2006-2008 NEC Asia Pte Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of NEC Asia Pte Ltd.
 * The use is subject to license terms from NEC Asia Pte Ltd.
 *
 */

package com.nec.asia.nic.framework.exception;

import org.apache.log4j.Logger;


/**
 * Parent class for all Exceptions.
 *
 * @version $Revision: 1.1.1.1 $
 * @author $Author: alvin $
 */
/* 
 * Modification History:
 *  
 * 19 Sep 2013 (chris): add error code.
 */
public class CoreException extends Exception {
	
	/** The Constant errorLogger. */
	protected static final Logger errorLogger = Logger.getLogger("ERRORS");
	protected String errorCode;
	
	/**
	 * Default Constructor.
	 */
	public CoreException() {
		errorLogger.error(this.getMessage(), this);
	}
	
	/**
	 * The Constructor.
	 *
	 * @param message the message
	 */
	public CoreException(String message) {
		super(message);
		errorLogger.error(message, this);
	}

	/**
	 * The Constructor.
	 *
	 * @param message the message
	 * @param errorCode the error code 
	 */
	public CoreException(String message, String errorCode) {
		super(message);
		this.errorCode=errorCode;
		errorLogger.error(message, this);
	}
	
	/**
	 * The Constructor.
	 * 
	 * @param cause the cause
	 */
	public CoreException(Throwable cause) {
		super("CoreException. Cause: "+cause.getMessage(), cause);
		if(!(cause instanceof CoreException)) {
			errorLogger.error(this.getMessage(),this);
		}		
	}
	
	/**
	 * The Constructor.
	 *
	 * @param message the message
	 * @param cause the cause
	 */
	public CoreException(String message, Throwable cause) {
		super(message, cause);
		if(!(cause instanceof CoreException)) {
			errorLogger.error(message,this);
		}
	}
	
	/**
	 * The Constructor.
	 *
	 * @param message the message
	 * @param errorCode the error code 
	 * @param cause the cause
	 */
	public CoreException(String message, String errorCode, Throwable cause) {
		super(message);
		this.errorCode=errorCode;
		if(!(cause instanceof CoreException)) {
			errorLogger.error(message,this);
		}
	}
	
	public String getErrorCode() {
		return this.errorCode;
	}
}
