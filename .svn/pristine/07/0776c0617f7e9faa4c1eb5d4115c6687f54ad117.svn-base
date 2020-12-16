/**
 * 
 */
package com.nec.asia.nic.framework.admin.rbac.service;

import java.util.List;

import org.hibernate.criterion.Order;

import com.nec.asia.nic.framework.PaginatedResult;
import com.nec.asia.nic.framework.admin.job.dto.UserDto;
import com.nec.asia.nic.framework.admin.rbac.domain.Functions;
import com.nec.asia.nic.framework.admin.rbac.domain.Roles;
import com.nec.asia.nic.framework.admin.rbac.domain.Users;
import com.nec.asia.nic.framework.admin.rbac.domain.Workstations;
import com.nec.asia.nic.framework.security.ad.helper.UserDTO;
import com.nec.asia.nic.framework.security.ldap.exceptions.ChangePasswordException;
import com.nec.asia.nic.framework.security.ldap.exceptions.IncorrectPasswordException;
import com.nec.asia.nic.framework.security.ldap.exceptions.UserNotFoundException;
import com.nec.asia.nic.framework.security.service.ADUser;
import com.nec.asia.nic.framework.security.service.exception.ExistingActiveDirectoryException;
import com.nec.asia.nic.framework.security.service.exception.UserCRUDException;
import com.nec.asia.nic.framework.service.BusinessService;

/**

 * @author Ambrocio,Paulo Johannes D.
 * @Company: NEC ASIA PACIFIC PTE
 * @since : Jul 12, 2013
 * <p>
 *	Decription here
 * </p>
 * 
 * Modification History :
 * 19 Nov 2013 setia_budiyono : Add additional methods related to user management
 * 11 Dec 2013 setia_budiyono : Add findUserByUserId method
 */
/* 
 * Modification History:
 *  
 * 01 Jul 2014 jp : added username and workstation id in changepassword and resetpassword
 */
public interface UserService extends BusinessService<Users, String> {
	
	public static final String PARAM_SCOPE_SYSTEM = "SYSTEM";
	public static final String PARAM_NAME_PASSWORD_EXPIRY = "PASSWORD_EXPIRY";
	
	public List<Roles> getRolesForWorstation(String workstationid);
	public List<String> getFunctionIdsForWorkstation(String workstationid);
	public List<String> getFunctionIdsForUserId(String userId);
	public List<String> getFunctions(String userId,String workstationid);
//	public Functions getFunctionById(String functionId);
//	public Roles getRole(String roleName);
//	public Workstations getWorkstations(String workStationId);
	
	public List<Roles> getRolesForUser(String userName);	
//	public String createRole(Roles role);
//	public void updateRole(Roles role);
//	public void deleteRole(Roles role);
	
//	public String createWorkstations(Workstations workstation);
//	public void updateWorkstations(Workstations workstation);
//	public void deleteWorkstations(Workstations workstation);
	
	public void createUser(ADUser user) throws UserCRUDException,ExistingActiveDirectoryException;
	public void updateUser(ADUser user) throws UserCRUDException;
	public void deleteUser(String userId) throws UserCRUDException;
	
	public boolean changePassword(String userId, String oldPassword,
			String newPassword) throws ChangePasswordException,
			UserNotFoundException, IncorrectPasswordException;
    
	public boolean changePassword(String userId, String oldPassword,
			String newPassword, String userName, String workstationId) throws ChangePasswordException,
			UserNotFoundException, IncorrectPasswordException;
	
	public boolean resetPassword(String userId, String newPassword)
			throws ChangePasswordException, UserNotFoundException;
	
	public boolean resetPassword(String userId, String newPassword, String userName, String workstationId)
			throws ChangePasswordException, UserNotFoundException;
	
	public boolean isUserExistedInAdAndDB (String userId) throws UserCRUDException;
	
	public boolean validateUser (String userId) throws UserCRUDException, ExistingActiveDirectoryException;
	
	public ADUser findUserByUserId (String userId) throws UserCRUDException;
	public PaginatedResult<Users> findAllForPagination(Users user, int pageNo,
			int pageSize, Order order);
	
	public PaginatedResult<UserDto> findAllForPaginationDto(Users user, int pageNo,
			int pageSize, Order order);
	public PaginatedResult<UserDto> findAllForPaginationDto(Users user, String nameUser, String loginUser, String siteCode, Boolean flag, int pageNo, int pageSize, Order order);

	
	public boolean isUserExistedInAd (String userId) throws UserCRUDException;
	
	public boolean isUserExistedInDb (String userId) throws UserCRUDException;
	
	public PaginatedResult<UserDTO> findAdUsersNotInDb(String search_firstName, String search_middleName,
			String search_surName, String search_userid, int pageNo, int pageSize);
	public List<Users> findByRoles(String roleId);
	
	public Users findByUserId(String userId);
	
	public List<Users> getListUserBySiteGroupAndRole(String siteGroup, String role);
	
	public List<Users> getListUserBySiteCode(String siteCode);
	
	public List<Users> findBySiteCode(String siteCode);
}
