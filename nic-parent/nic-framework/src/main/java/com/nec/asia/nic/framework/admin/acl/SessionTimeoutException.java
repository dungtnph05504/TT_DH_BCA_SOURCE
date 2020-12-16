package com.nec.asia.nic.framework.admin.acl;

/**
 * 
 * @author boonseng_ong
 *
 */
public class SessionTimeoutException extends Exception{
	/**
	 * 
	 */
	private static final long serialVersionUID = 299060266983153820L;

	public SessionTimeoutException() {
		super("Session timeout");
	}

	public SessionTimeoutException(String message) {
		super(message);
	}

	public SessionTimeoutException(String message, Throwable cause) {
		super(message, cause);
	}

	public SessionTimeoutException(Throwable cause) {
		super("Session timeout. Cause: "+cause.getMessage(),cause);
	}
}
