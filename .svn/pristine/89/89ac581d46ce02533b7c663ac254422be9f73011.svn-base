package com.nec.asia.nic.framework.security.service;

import com.nec.asia.nic.framework.security.ldap.exceptions.IncorrectPasswordException;
import com.nec.asia.nic.web.session.UserSession;

/**

 * @author Ambrocio,Paulo Johannes D.
 * @Company: NEC ASIA PACIFIC PTE
 * @since : Jun 14, 2013
 * <p>
 *	Decription here
 * </p>
 * 
 * Modification History :
 * 18 Nov 2013 setia_budiyono : modified authenticateUser, resetPassword and changePassword method
 *
 */
public interface AuthenticationService {
	

	public UserSession login(String userId, String clearPassword, String workstationId) throws IncorrectPasswordException;
	
//	public boolean logout(UserSession userSession);
//	public void resetPassword(String userName, String newClearPassword) throws ChangePasswordException ;
//	public void changePassword(String userId, String password, String newClearPassword) throws ChangePasswordException, IncorrectPasswordException;
//	public List<String> getFunctions(String userId,String workstationid);
//	public boolean authenticateUser(String userId, String password) throws IncorrectPasswordException ;
}
