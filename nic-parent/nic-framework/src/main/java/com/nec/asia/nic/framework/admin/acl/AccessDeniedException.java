package com.nec.asia.nic.framework.admin.acl;

/**
 * AccessDeniedException thrown when accessing URL not granted
 * 
 * @author bright_zheng
 *
 */
public class AccessDeniedException extends Exception{
	private static final long serialVersionUID = -2443240832561006575L;


	public AccessDeniedException() {
		super("Access denied");
	}

	public AccessDeniedException(String message) {
		super(message);
	}

	public AccessDeniedException(String message, Throwable cause) {
		super(message, cause);
	}

	public AccessDeniedException(Throwable cause) {
		super("Access denied. Cause: "+cause.getMessage(),cause);
	}
}
