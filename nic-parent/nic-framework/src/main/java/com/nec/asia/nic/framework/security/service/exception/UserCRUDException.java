/**
 * 
 */
package com.nec.asia.nic.framework.security.service.exception;

import com.nec.asia.nic.framework.exception.CoreException;

/**

 * @author Ambrocio,Paulo Johannes D.
 * @Company: NEC ASIA PACIFIC PTE
 * @since : Jun 20, 2013
 * <p>
 *	Decription here
 * </p>
 * 
 *
 */
public class UserCRUDException extends CoreException {
	/**
	 * Instantiates a new UserCRUDException exception.
	 *
	 * @param message the message
	 */
	public UserCRUDException(String message) {
		super(message);
		
	}

	/**
	 * Instantiates a new UserCRUDException exception.
	 *
	 * @param cause the cause
	 */
	public UserCRUDException(Throwable cause) {
		super(cause);
		
	}

	/**
	 * Instantiates a new UserCRUDException exception.
	 *
	 * @param message the message
	 * @param cause the cause
	 */
	public UserCRUDException(String message, Throwable cause) {
		super(message, cause);
		
	}

}
