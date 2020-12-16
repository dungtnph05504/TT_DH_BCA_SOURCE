package com.nec.asia.nic.comp.trans.service.exception;

import com.nec.asia.nic.framework.exception.CoreException;



public class NicUploadJobServiceException extends CoreException {

	/**
	 * Instantiates a new NicUploadJobServiceException exception.
	 *
	 * @param message the message
	 */
	public NicUploadJobServiceException(String message) {
		super(message);
		
	}

	/**
	 * Instantiates a new NicUploadJobServiceException exception.
	 *
	 * @param cause the cause
	 */
	public NicUploadJobServiceException(Throwable cause) {
		super(cause);
		
	}

	/**
	 * Instantiates a new NicUploadJobServiceException exception.
	 *
	 * @param message the message
	 * @param cause the cause
	 */
	public NicUploadJobServiceException(String message, Throwable cause) {
		super(message, cause);
		
	}

}
