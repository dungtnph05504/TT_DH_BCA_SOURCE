package com.nec.asia.nic.framework.security.service.exception;

import com.nec.asia.nic.framework.exception.CoreException;

public class WorkstationInvalidException extends CoreException{

	
	public WorkstationInvalidException() {
		super();
		// TODO Auto-generated constructor stub
	}

	public WorkstationInvalidException(String message, Throwable cause) {
		super(message, cause);
		// TODO Auto-generated constructor stub
	}

	public WorkstationInvalidException(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}
	
	
	public WorkstationInvalidException(String message, String errorCode) {
		super(message);
		this.errorCode=errorCode;
		// TODO Auto-generated constructor stub
	}
	


}
