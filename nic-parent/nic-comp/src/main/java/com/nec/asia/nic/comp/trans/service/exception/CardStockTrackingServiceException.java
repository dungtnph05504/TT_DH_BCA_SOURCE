package com.nec.asia.nic.comp.trans.service.exception;

import com.nec.asia.nic.framework.exception.CoreException;


/* 
 * Modification History:
 *  
 * 11 Feb 2014 (chris): init class
 */
public class CardStockTrackingServiceException extends CoreException {

	/**
	 * The Constructor.
	 */
	public CardStockTrackingServiceException() {
		super("Card Stock Tracking Service Exception encountered.");
	}

	/**
	 * The Constructor.
	 * 
	 * @param message the message
	 */
	public CardStockTrackingServiceException(String message) {
		super(message);
	}

	/**
	 * The Constructor.
	 * 
	 * @param message the message
	 * @param cause the cause
	 */
	public CardStockTrackingServiceException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * The Constructor.
	 * 
	 * @param cause the cause
	 */
	public CardStockTrackingServiceException(Throwable cause) {
		super("Card Stock Tracking Service Exception encountered. Cause: " + cause.getMessage(), cause);
	}


	/**
	 * Instantiates a new CardStockTrackingServiceException exception.
	 *
	 * @param message the message
	 * @param errorCode the error code 
	 */
	public CardStockTrackingServiceException(String message, String errorCode) {
		super(message, errorCode);
	}
	
	/**
	 * The Constructor.
	 *
	 * @param message the message
	 * @param errorCode the error code 
	 * @param cause the cause
	 */
	public CardStockTrackingServiceException(String message, String errorCode, Throwable cause) {
		super(message, errorCode, cause);
	}

}
