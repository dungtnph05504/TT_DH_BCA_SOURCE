package com.nec.asia.nic.framework.security.service;

import java.util.List;

import com.nec.asia.nic.framework.admin.rbac.domain.Functions;
import com.nec.asia.nic.framework.admin.rbac.domain.Roles;
import com.nec.asia.nic.framework.admin.rbac.domain.Workstations;
import com.nec.asia.nic.framework.security.ldap.domain.ActiveDirectoryUser;
import com.nec.asia.nic.framework.security.ldap.exceptions.ChangePasswordException;
import com.nec.asia.nic.framework.security.ldap.exceptions.IncorrectPasswordException;
import com.nec.asia.nic.framework.security.ldap.exceptions.UserNotFoundException;
import com.nec.asia.nic.framework.security.service.exception.ExistingActiveDirectoryException;
import com.nec.asia.nic.framework.security.service.exception.UserCRUDException;


/* 
 * Modification History:
 *  
 * 	18 Jun 2014 (jp): add getStatus for AdminWS.getAuthorizedFunctions
 *  24 Jun 2014 (jp): change getStatus to validateUser and improve implementation for AdminWS.getAuthorizedFunctions
 */

public interface SecurityService {
	
	//List<ADUser> findAllByOrganizationalUnitName(String orgunit);
	List<Roles> getRolesForWorstation(String workstationid);
	List<String> getFunctionIdsForWorkstation(String workstationid);
	List<String> getFunctionIdsForUserId(String userId);
	List<String> getFunctions(String userId,String workstationid);
	List<String> getFunctions(String userId,String workstationid,String siteCode);
//	String getStatus(String userId,String workstationid,String siteCode);
	void validateUser(String userId,String workstationid,String siteCode) throws Exception;
	List<Roles> getRolesForUser(String userName);
	Functions getFunctions(String functionId);
	Roles getRole(String roleName);
	Workstations getWorkstations(String workStationId);
	
	String createRole(Roles role);
	void updateRole(Roles role);
	void deleteRole(Roles role);
	
	String createWorkstations(Workstations workstation);
	void updateWorkstations(Workstations workstation);
	void deleteWorkstations(Workstations workstation);
	
	//void createUser(ADUser user) throws UserCRUDException,ExistingActiveDirectoryException;
	//void updateUser(ADUser user) throws UserCRUDException;
	//void deleteUser(ADUser user) throws UserCRUDException;
	//ADUser findUserByUserName(String userName,String organizationalUnit);
	//ADUser findUserByUserName(String userName);
	
	//boolean changePassword(String userName, String oldPassword,	String newPassword) throws ChangePasswordException,	UserNotFoundException, IncorrectPasswordException;
    //boolean resetPassword(String userName, String newPassword) throws ChangePasswordException, UserNotFoundException;
    
	//public boolean authenticate(String userName, String password) throws UserNotFoundException, IncorrectPasswordException;
    
    

}
