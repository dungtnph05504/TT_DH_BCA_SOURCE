package com.nec.asia.nic.framework.security.ldap.active.directory.utils;

/**
 * @author paulo_ambrocio
 * @Company: NEC ASIA PACIFIC PTE
 * @since : May 30, 2013
 * <p>
 *	An exception thrown by LdapAuthenticator
 * </p>
 * 
 */
public class LdapException extends Exception {




	/**
	 * 
	 * 
	 */
	private static final long serialVersionUID = -6938433958197471874L;

	public LdapException() {
		super();
		
	}

	public LdapException(String message, Throwable cause,
			boolean enableSuppression, boolean writableStackTrace) {
		//super(message, cause, enableSuppression, writableStackTrace);
		
	}

	public LdapException(String message, Throwable cause) {
		super(message, cause);
	
	}

	public LdapException(String message) {
		super(message);

	}

	public LdapException(Throwable cause) {
		super(cause);

	}

}
