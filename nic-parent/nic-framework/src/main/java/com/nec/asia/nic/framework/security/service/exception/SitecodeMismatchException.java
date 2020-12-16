package com.nec.asia.nic.framework.security.service.exception;

import com.nec.asia.nic.framework.exception.CoreException;

public class SitecodeMismatchException extends CoreException{

	
	public SitecodeMismatchException() {
		super();
		// TODO Auto-generated constructor stub
	}

	public SitecodeMismatchException(String message, Throwable cause) {
		super(message, cause);
		// TODO Auto-generated constructor stub
	}

	public SitecodeMismatchException(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}
	
	
	public SitecodeMismatchException(String message, String errorCode) {
		super(message);
		this.errorCode=errorCode;
		// TODO Auto-generated constructor stub
	}
	


}
