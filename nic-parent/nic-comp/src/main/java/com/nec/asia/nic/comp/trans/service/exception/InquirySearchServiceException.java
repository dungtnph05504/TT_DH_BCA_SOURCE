package com.nec.asia.nic.comp.trans.service.exception;

import com.nec.asia.nic.framework.exception.CoreException;

/**
 * @author setia_budiyono
 *
 */
public class InquirySearchServiceException extends CoreException {
	
	/**
	 * Instantiates a new NicIssuanceServiceException client exception.
	 *
	 * @param message the message
	 */
	public InquirySearchServiceException(String message) {
		super(message);
		
	}

	/**
	 * Instantiates a new idserver webservice client exception.
	 *
	 * @param cause the cause
	 */
	public InquirySearchServiceException(Throwable cause) {
		super(cause);
		
	}

	/**
	 * Instantiates a new InquirySearchServiceException exception.
	 *
	 * @param message the message
	 * @param cause the cause
	 */
	public InquirySearchServiceException(String message, Throwable cause) {
		super(message, cause);
		
	}
}
