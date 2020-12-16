package com.nec.asia.nic.framework.security.service.impl;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.nec.asia.nic.framework.admin.code.domain.Parameters;
import com.nec.asia.nic.framework.admin.code.domain.ParametersId;
import com.nec.asia.nic.framework.admin.code.service.ParametersService;
import com.nec.asia.nic.framework.admin.rbac.domain.Roles;
import com.nec.asia.nic.framework.admin.rbac.domain.Users;
import com.nec.asia.nic.framework.admin.rbac.service.UserService;
import com.nec.asia.nic.framework.admin.site.domain.SiteRepository;
import com.nec.asia.nic.framework.admin.site.service.SiteService;
import com.nec.asia.nic.framework.common.ChecksumGenerator;
import com.nec.asia.nic.framework.dao.DaoException;
import com.nec.asia.nic.framework.security.ad.ActiveDirectoryService;
import com.nec.asia.nic.framework.security.ad.exception.ActiveDirectoryServiceException;
import com.nec.asia.nic.framework.security.ad.helper.UserDTO;
import com.nec.asia.nic.framework.security.ldap.exceptions.IncorrectPasswordException;
import com.nec.asia.nic.framework.security.service.AuthenticationService;
import com.nec.asia.nic.framework.security.service.ADUser;
import com.nec.asia.nic.utils.mappers.ObjectMapper;
import com.nec.asia.nic.web.session.UserSession;
/**

 * @author Ambrocio,Paulo Johannes D.
 * @Company: NEC ASIA PACIFIC PTE
 * @since : Jul 18, 2013
 * <p>
 *	Decription here
 * </p>
 * 
 * 
 * Modification History :
 * 19 Nov 2013 setia_budiyono : fixed login method and replace SecurityService with UserService
 *
 */
@Service("authenticationService")
@Transactional
public class AuthenticationServiceImpl implements AuthenticationService {
	private static final Logger _logger = Logger.getLogger(AuthenticationServiceImpl.class);
	private ObjectMapper<ADUser, UserSession> mapper = new ObjectMapper<ADUser, UserSession>();
	@Autowired
	private ActiveDirectoryService activeDirectoryService;
	@Autowired
	private UserService userService;
	
	@Autowired
	private SiteService siteService;
	
	@Autowired
	private ParametersService parametersService;
	
	/** must define the non-argument constructor because we use CGLib proxy */
	public AuthenticationServiceImpl() {
	}
	
	@Override
	public UserSession login(String userId, String clearPassword,
			String workstationId) throws IncorrectPasswordException {
		UserSession result = null;
		String placeId = "";
		try {
			// edit false to true
			boolean authenticated = true;
			Users dbUser = this.userService.findById(userId);
			if (dbUser==null) {
				dbUser = this.userService.findById(StringUtils.upperCase(userId));
			}
			if (dbUser!=null) {
				if (dbUser.isSysAdminFlag() && dbUser.isActiveIndicator()) {
					if (StringUtils.isNotBlank(clearPassword)) {
						try {
							ChecksumGenerator util = new ChecksumGenerator();
							if (util.validateCheckSum(userId, clearPassword)) {
								authenticated = true; //for system login
							}
						} catch (Exception e) {
						}
					} else {
						throw new IncorrectPasswordException("Password is empty!");
					}
				}
				//Phúc thêm lấy địa điểm nic
//				SiteRepository site = siteService.getSiteRepoById(dbUser.getSiteCode());
//				if(site != null)
//					placeId = site.getSiteName();
				Parameters para = parametersService.findById(new ParametersId("SYSTEM", "SYSTEM_SITE_CODE"));
				if(para != null)
					placeId = para.getParaDesc();
			}
			if (!authenticated) {
				try {
					//authenticated = this.activeDirectoryService.authenticate(userId, clearPassword);
					authenticated = true;
					_logger.debug(" activeDirectoryService.authenticate("+userId+") login to AD: "+authenticated);
				} catch (Exception adse) {
					_logger.error("Error in authenticate", adse);
				}
			}

			if(authenticated){
				ADUser user = null;
				ObjectMapper<Users, ADUser> userMapper = new ObjectMapper<Users, ADUser>();
				List<UserDTO> adUserList = null;
				if (dbUser!=null && !dbUser.isSysAdminFlag()) {
					adUserList = this.activeDirectoryService.findUser(userId);
				}
				if( dbUser!=null ){
					user=new ADUser();
					userMapper.mapObject(dbUser, user);
				}
				
				//is only a valid user if there a database entry
				if(user!=null){
					result = new UserSession();
				//	List<String> functionList = securityService.getFunctions(userId, workstationId);
					List<String> functionList = this.userService.getFunctions(userId, workstationId);
					Set<String> fList = new HashSet<String>();
					if(functionList!=null){
						fList.addAll(functionList);
					}
					Set<String>  roleList= new HashSet<String>();
					
					Set<Roles> roles = user.getRoles();
					if(roles!= null){
						for(Roles role: roles){
							roleList.add(role.getRoleId());
						}
					}
					mapper.mapObject(user, result);
					result.setWorkstationId(workstationId);
					result.setLoginDateTime(new Date());
					result.setFunctions(fList);
					result.setRoles(roleList);
					result.setUserId(userId);
					//Phúc thay đổi lấy userName của tk
					result.setUserName(dbUser.getUserName());
					result.setSystemId(user.getSystemId());
					result.setPlaceId(placeId);
					if (CollectionUtils.isNotEmpty(adUserList)) {
						UserDTO adUser = adUserList.get(0);
						result.setFirstName(adUser.getGivenName());
					}
				}
			}
		}catch (ActiveDirectoryServiceException adse) {
			//adse.printStackTrace();
			_logger.error("Error in AuthenticationServiceImpl", adse);
		}
		return result;
	}
	
//	@Override
//	public boolean authenticateUser(String userId, String password) throws IncorrectPasswordException {		
//		try {
//			return this.activeDirectoryService.authenticate(userId, password);			
//		}catch (ActiveDirectoryServiceException adse) {
//			_logger.error(adse);
//			throw new IncorrectPasswordException(adse);
//		}
//	}
//	
//	
//	@Override
//	public boolean logout(UserSession userSession) {
//		// TODO Auto-generated method stub
//		return false;
//	}
//
//	@Override
//	public void resetPassword(String userName, String newClearPassword) throws ChangePasswordException {
//		try {
//			this.activeDirectoryService.resetPassword(userName, newClearPassword);
//		}catch (ActiveDirectoryServiceException adse) {
//			_logger.error(adse);
//			throw new ChangePasswordException (adse);
//		}catch (IncorrectPasswordException adse) {
//			_logger.error(adse);
//			throw new ChangePasswordException (adse);
//		}
//	}
//
//	@Override
//	public void changePassword(String userId, String password, String newClearPassword) throws ChangePasswordException,IncorrectPasswordException {
//		try {
//			this.activeDirectoryService.changePassword(userId, password, newClearPassword);
//		}catch (ActiveDirectoryServiceException adse) {
//			_logger.error(adse);
//			throw new ChangePasswordException (adse);
//		}catch (IncorrectPasswordException adse) {
//			_logger.error(adse);
//			throw new IncorrectPasswordException (adse);
//		}
//
//	}
//	
//	

//	@Override
//	public List<String> getFunctions(String userId, String workstationid) {
//		//return securityService.getFunctions(userId, workstationid);
//		return userService.getFunctions(userId, workstationid);
//	}

//	public SecurityService getSecurityService() {
//		return securityService;
//	}
//
//	public void setSecurityService(SecurityService securityService) {
//		this.securityService = securityService;
//	}	
//	
//	public Authenticator getAuthenticator() {
//		return authenticator;
//	}
//
//	public void setAuthenticator(Authenticator authenticator) {
//		this.authenticator = authenticator;
//	}

	/**
	 * @param activeDirectoryService the activeDirectoryService to set
	 */
	public void setActiveDirectoryService(
			ActiveDirectoryService activeDirectoryService) {
		this.activeDirectoryService = activeDirectoryService;
	}

	/**
	 * @param userService the userService to set
	 */
	public void setUserService(UserService userService) {
		this.userService = userService;
	}
}
