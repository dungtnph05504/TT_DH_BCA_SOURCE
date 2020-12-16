/**
 * 
 */
package com.nec.asia.nic.framework.security.ldap.exceptions;

import com.nec.asia.nic.framework.exception.CoreException;

/**

 * @author Ambrocio,Paulo Johannes D.
 * @Company: NEC ASIA PACIFIC PTE
 * @since : Jun 17, 2013
 * <p>
 *	Decription here
 * </p>
 * 
 *
 */
public class ChangePasswordException extends CoreException {
	/**
	 * Instantiates a new ChangePasswordException exception.
	 *
	 * @param message the message
	 */
	public ChangePasswordException(String message) {
		super(message);
		
	}

	/**
	 * Instantiates a new ChangePasswordException exception.
	 *
	 * @param cause the cause
	 */
	public ChangePasswordException(Throwable cause) {
		super(cause);
		
	}

	/**
	 * Instantiates a new ChangePasswordException exception.
	 *
	 * @param message the message
	 * @param cause the cause
	 */
	public ChangePasswordException(String message, Throwable cause) {
		super(message, cause);
		
	}

}
