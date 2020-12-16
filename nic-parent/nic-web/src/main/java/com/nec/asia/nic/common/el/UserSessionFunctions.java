package com.nec.asia.nic.common.el;

import com.nec.asia.nic.web.session.UserSession;

/**

 * @author Ambrocio,Paulo Johannes D.
 * @Company: NEC ASIA PACIFIC PTE
 * @since : Jul 19, 2013
 * <p>
 *	Decription here
 * </p>
 * 
 *
 */
public class UserSessionFunctions {

	private UserSessionFunctions() {
		
	}

	public static boolean isPreviledged(String function,UserSession userSession){
		if(userSession==null)
			return false;
		if(userSession.getFunctions()==null)
			return false;
		return userSession.getFunctions().contains(function);
	}
}
