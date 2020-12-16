package com.nec.asia.nic.framework.security.ad;

import java.util.List;

import org.hibernate.criterion.Order;

import com.nec.asia.nic.framework.PaginatedResult;
import com.nec.asia.nic.framework.admin.rbac.domain.Users;
import com.nec.asia.nic.framework.security.ad.exception.ActiveDirectoryServiceException;
import com.nec.asia.nic.framework.security.ad.helper.UserDTO;
import com.nec.asia.nic.framework.security.ldap.exceptions.IncorrectPasswordException;


/**
 * @author setia_budiyono
 *
 */
public interface ActiveDirectoryService {
	
	public void create(UserDTO user) throws ActiveDirectoryServiceException;
	public void update(UserDTO user) throws ActiveDirectoryServiceException;
	public void delete(String userId) throws ActiveDirectoryServiceException;
	
	@Deprecated
	public void changePassword (String userId, String password) throws ActiveDirectoryServiceException;

	public boolean changePassword (String userId, String oldPassword, String newPassword) 
			throws ActiveDirectoryServiceException, IncorrectPasswordException;

	public boolean changePassword (String userId, String oldPassword, String newPassword, String userName, String workstationId) 
			throws ActiveDirectoryServiceException, IncorrectPasswordException;
	
	public boolean resetPassword (String userId, String newPassword) 
			throws ActiveDirectoryServiceException, IncorrectPasswordException;
	
	public boolean resetPassword (String userId, String newPassword, String userName, String workstationId) 
			throws ActiveDirectoryServiceException, IncorrectPasswordException;

	public boolean isUserExists(String userId)throws ActiveDirectoryServiceException;
	
	public List<UserDTO> findUser(String userId)throws ActiveDirectoryServiceException;
	
	public boolean authenticate(String userId, String password) throws ActiveDirectoryServiceException; 
	
	public List<UserDTO> findUsers(String search_firstName, String search_middleName, String search_surName,
			String search_userid) throws ActiveDirectoryServiceException; 
}
