/**
 * 
 */
package com.nec.asia.nic.framework.admin.rbac.service.impl;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nec.asia.nic.framework.PaginatedResult;
import com.nec.asia.nic.framework.admin.code.dao.ParametersDao;
import com.nec.asia.nic.framework.admin.code.domain.Parameters;
import com.nec.asia.nic.framework.admin.code.domain.ParametersId;
import com.nec.asia.nic.framework.admin.job.dto.UserDto;
import com.nec.asia.nic.framework.admin.rbac.dao.FunctionsDao;
import com.nec.asia.nic.framework.admin.rbac.dao.RolesDao;
import com.nec.asia.nic.framework.admin.rbac.dao.UsersDao;
import com.nec.asia.nic.framework.admin.rbac.dao.WorkstationsDao;
import com.nec.asia.nic.framework.admin.rbac.domain.Functions;
import com.nec.asia.nic.framework.admin.rbac.domain.Roles;
import com.nec.asia.nic.framework.admin.rbac.domain.Users;
import com.nec.asia.nic.framework.admin.rbac.domain.Workstations;
import com.nec.asia.nic.framework.admin.rbac.service.UserService;
import com.nec.asia.nic.framework.admin.site.domain.SiteRepository;
import com.nec.asia.nic.framework.admin.site.service.SiteService;
import com.nec.asia.nic.framework.security.ad.ActiveDirectoryService;
import com.nec.asia.nic.framework.security.ad.exception.ActiveDirectoryServiceException;
import com.nec.asia.nic.framework.security.ad.helper.UserDTO;
import com.nec.asia.nic.framework.security.ldap.exceptions.ChangePasswordException;
import com.nec.asia.nic.framework.security.ldap.exceptions.IncorrectPasswordException;
import com.nec.asia.nic.framework.security.ldap.exceptions.UserNotFoundException;
import com.nec.asia.nic.framework.security.service.ADUser;
import com.nec.asia.nic.framework.security.service.exception.ExistingActiveDirectoryException;
import com.nec.asia.nic.framework.security.service.exception.UserCRUDException;
import com.nec.asia.nic.framework.service.impl.DefaultBusinessServiceImpl;
import com.nec.asia.nic.utils.HelperClass;
import com.nec.asia.nic.utils.mappers.ObjectMapper;

/**

 * @author Ambrocio,Paulo Johannes D.
 * @Company: NEC ASIA PACIFIC PTE
 * @since : Jul 12, 2013
 * <p>
 *	 Simple implementation for User Service
 * </p>
 * 
 * Modification History :
 * 19 Nov 2013 setia_budiyono : modified all methods on implementation class 
 */
/* 
 * Modification History:
 *  
 * 23 Dec 2013 (chris): add user mapping
 * 01 Jul 2014 jp : added username and workstation id in changepassword and resetpassword
 * 11 Sep 2014 (prasad): fix user role mapping not saved in Admin Console.Create User Page.
 * 20 Jul 2017 (chris): to use slf4j logger instead of log4j.
 */
@Service("userService")
public class UserServiceImpl extends DefaultBusinessServiceImpl<Users, String, UsersDao>
						implements UserService {
	
	//private static final Logger _logger = Logger.getLogger(UserServiceImpl.class);
	@Autowired
	private RolesDao rolesDao;
	@Autowired
	private WorkstationsDao workstationsDao;
	@Autowired
	private FunctionsDao functionDao;
	@Autowired
	private ActiveDirectoryService activeDirectoryService;
	
	@Autowired
	private ParametersDao parametersDao;
	
	@Autowired
	private SiteService siteService;
	
	/** must define the non-argument constructor because we use CGLib proxy */
	public UserServiceImpl() {
		super();
	}
	
	@Autowired
	public UserServiceImpl(UsersDao dao) {
		this.dao = dao;
	}
	
	public List<Roles> getRolesForWorstation(String workstationid) {
		Workstations wrkStation = workstationsDao.findById(workstationid);
		if(wrkStation==null)
			return null;
		if(wrkStation.getRoles()!=null)
			return new ArrayList<Roles>(wrkStation.getRoles());
		return null;
	}
	
	public List<String> getFunctionIdsForWorkstation(String workstationid) {
		Workstations workstations = workstationsDao.findById(workstationid);
		if(workstations == null)
			return null;
		if(workstations.getRoles()==null)
			return null;
		List<String> res = null;
		Set<String> resSet = new HashSet<String>();
		
		for(Roles wkrole: workstations.getRoles()){
			if(wkrole.getFunctions()==null)
				continue;
			for(Functions func: wkrole.getFunctions()){
				resSet.add(func.getFunctionId());
			}
		}
		if(resSet.size()>0){
			res=new ArrayList<String>();
			res.addAll(resSet);
		}
		return res;
	}
//	
	public List<String> getFunctionIdsForUserId(String userId) {
		Users user = this.findById(userId);
		if(user==null)
			return null;
		if(user.getRoles() == null)
			return null;
		List<String> res = null;
	
		Set<String> resSet = new HashSet<String>();
		for(Roles userRole: user.getRoles()){
			if(userRole.getFunctions()==null)
				continue;
			for(Functions func: userRole.getFunctions()){
				resSet.add(func.getFunctionId());
			}
		}
		if(resSet.size()>0){
			res=new ArrayList<String>();
			res.addAll(resSet);
		}
	
		return res;
	}
//	
	public List<String> getFunctions(String userId, String workstationid) {
		logger.info("getFunctions({}, {})", userId, workstationid);
		List<String> workStationFunctions = getFunctionIdsForWorkstation(workstationid);
		List<String> userFunctions = getFunctionIdsForUserId(userId);
		Set<String> resSet=new HashSet<String>();
		List<String> res = null;
		if(userFunctions!=null)
			for(String func: userFunctions){
				if(workStationFunctions!=null)
					if(workStationFunctions.contains(func))
						resSet.add(func);
			}
		
		if(resSet.size() > 0)
			res = new ArrayList<String>(resSet);
		logger.info("getFunctions Result.size: {}.", res.size());
		return res;
	}
	
	public List<Roles> getRolesForUser(String userName) {
		Users users = this.findById(userName);
		if(users == null)
			return null;
		if(users.getRoles()!=null)
			return new ArrayList<Roles>(users.getRoles());
		return null;
	}
	
//	public Functions getFunctionById(String functionId) {
//		return this.functionDao.findById(functionId);
//	}
	
//	public Roles getRole(String roleName) {
//		return this.rolesDao.findById(roleName);
//	}
	
//	public Workstations getWorkstations(String workStationId) {
//		return this.workstationsDao.findById(workStationId);
//	}
	
	
	
	
//	public String createRole(Roles role) {
//		role.setCreateDate(new Date());
//		role.setUpdateDate(new Date());
//		return this.rolesDao.save(role);
//	}
//	
//	public void updateRole(Roles role) {
//		role.setUpdateDate(new Date());
//		this.rolesDao.saveOrUpdate(role);
//	}
//
//	public void deleteRole(Roles role) {
//		this.rolesDao.delete(role);
//	}
	
//	public String createWorkstations(Workstations workstations) {
//		workstations.setCreateDate(new Date());
//		workstations.setUpdateDate(new Date());
//		return this.workstationsDao.save(workstations);
//	}
//
//	public void updateWorkstations(Workstations workstations) {
//		workstations.setUpdateDate(new Date());
//		this.workstationsDao.saveOrUpdate(workstations);
//	}
//	
//	public void deleteWorkstations(Workstations workstations) {
//		this.workstationsDao.delete(workstations);
//	}
	
	public void createUser(ADUser user) throws UserCRUDException,ExistingActiveDirectoryException {
		try {
			
			Users dbUser = new Users();
			dbUser.setUserId(user.getUserId());
			dbUser.setActiveIndicator(true);
			dbUser.setCreateDate(new Date());
			dbUser.setUpdateDate(new Date());
			dbUser.setRoles(user.getRoles()); //11 Sep 2014
			
			//[tuenv][12 Jan 2016] -start addition
			dbUser.setUserStartDate(new Date());
			dbUser.setSiteCode(user.getSiteCode());
			dbUser.setCreateBy(user.getCreateBy());
			dbUser.setCreateWkstnId(user.getCreateWkstnId());
			dbUser.setSystemId(user.getSystemId());
			 
			dbUser.setSysAdminFlag(false);
			dbUser.setDeleteFlag(false);
			dbUser.setSiteGroupCode(user.getSiteGroupCode());
			
			/*Phúc thêm phòng ban + chức vụ*/
			dbUser.setDepartment(user.getDepartment());
			dbUser.setPosition(user.getPosition());
			dbUser.setUserName(user.getUserName());
			dbUser.setPasswordTemp(user.getPassword());
			/*
			// check if an existing user is available in AD			
			if(this.activeDirectoryService.isUserExists(user.getUserId()))
				throw new ExistingActiveDirectoryException("User ID : " + user.getUserId() + " already exist in the Active Directory");
			*/
			
			UserDTO adUser = new UserDTO();
			//[chris] [23 Dec 2013] start
			//adUser.setUserId(user.getUserId());
			//adUser.setEmail(user.getEmail());
			//adUser.setPassword(user.getPassword());
			parseUserDTO(user, adUser);
			logger.debug("[createUser] before activeDirectoryService.create() userId:"+adUser.getUserId()+", username:"+adUser.getUsername());
			//[chris] [23 Dec 2013] end
			/*
			this.activeDirectoryService.create(adUser);
			*/ 	 
			if(this.activeDirectoryService.isUserExists(user.getUserId())){
				logger.debug("[createUser] before activeDirectoryService.update() userId:"+adUser.getUserId()+", username:"+adUser.getUsername());
				this.activeDirectoryService.update(adUser);
			}else{
				logger.debug("[createUser] before activeDirectoryService.create() userId:"+adUser.getUserId()+", username:"+adUser.getUsername());
				this.activeDirectoryService.create(adUser);
			} 
			this.save(dbUser);
		} catch (ActiveDirectoryServiceException adse) {
			throw new UserCRUDException (adse);
		}
	}

	public void updateUser(ADUser user) throws UserCRUDException {
		try {
			ObjectMapper<ADUser,Users> userMapper = new ObjectMapper<ADUser,Users>();
			UserDTO adUser = new UserDTO();
			Users dbUser = new Users();
			userMapper.mapObject(user, dbUser);
			
			dbUser.setDeleteFlag(user.isDeleteFlag()); 
			dbUser.setSysAdminFlag(user.getSysAdminFlag()); 
			dbUser.setSiteGroupCode(user.getSiteGroupCode());
			dbUser.setActiveIndicator(true);
			dbUser.setSiteCode(user.getSiteCode());
			dbUser.setDepartment(user.getDepartment());
			dbUser.setPosition(user.getPosition());
			dbUser.setUserName(user.getUserName());
			//update active directory user
			//[chris] [23 Dec 2013] start
			//adUser.setEmail(user.getEmail());
			//adUser.setSiteCode(user.getSiteCode());
			parseUserDTO(user, adUser);
			//[chris] [23 Dec 2013] end
			
			dbUser.setUpdateDate(new Date());
			this.saveOrUpdate(dbUser);			

			if(this.activeDirectoryService.isUserExists(user.getUserId())){
				logger.debug("[updateUser] before activeDirectoryService.update() userId:"+adUser.getUserId()+", username:"+adUser.getUsername());
				this.activeDirectoryService.update(adUser);
			}else{
				logger.debug("[updateUser] before activeDirectoryService.create() userId:"+adUser.getUserId()+", username:"+adUser.getUsername());
				this.activeDirectoryService.create(adUser);
			} 
		} catch (ActiveDirectoryServiceException adse) {
			throw new UserCRUDException (adse);
		} catch (Exception e) {
			throw new UserCRUDException(e);
		}
	}
	
	public void deleteUser(String userId) throws UserCRUDException {
	    try {
			//this.deleteUser(userId);
			this.delete(userId);
			
			this.activeDirectoryService.delete(userId);			
		}catch (ActiveDirectoryServiceException adse) {
			adse.printStackTrace();
			logger.error("Error in deleteUser.", adse);
			throw new UserCRUDException (adse);
		}
	}
	
	public boolean changePassword(String userId, String oldPassword,
			String newPassword) throws ChangePasswordException, UserNotFoundException, IncorrectPasswordException {
		
		
		return changePassword(userId,oldPassword,newPassword,"","");

	}
    
	//new with workstation id and username
	public boolean changePassword(String userId, String oldPassword,
			String newPassword, String userName, String workstationId) throws ChangePasswordException, UserNotFoundException, IncorrectPasswordException {
		boolean success = false;
		try {
			if ( !this.activeDirectoryService.isUserExists(userId) )
				   throw new UserNotFoundException ("User is not found for [userId]=" + userId);
			
			//this.activeDirectoryService.changePassword(userId, oldPassword, newPassword); 
			this.activeDirectoryService.changePassword(userId, oldPassword, newPassword,userName,workstationId); 
			//update DB
			this.resetPasswordExpiry(userId, userName, workstationId);
			
			success = true;
		}catch (ActiveDirectoryServiceException ase) {
			logger.error("Error in changePassword.",ase);
			throw new ChangePasswordException (ase);
		}catch (UserNotFoundException unfe) {
			logger.error("Error in changePassword.",unfe);
			throw unfe;
		}catch (IncorrectPasswordException ipe) {
			logger.error("Error in changePassword.",ipe);
			throw ipe;
		}catch (Exception ex) {
			throw new ChangePasswordException (ex);
		}
		
		return success;

	}
    
	public boolean resetPassword(String userId, String newPassword)
			throws ChangePasswordException, UserNotFoundException {
		return resetPassword(userId,newPassword,"","");
	}
	
	public boolean resetPassword(String userId, String newPassword, String userName, String workstationId)
			throws ChangePasswordException, UserNotFoundException {
		boolean success = false;
		try {
			if ( !this.activeDirectoryService.isUserExists(userId) )
				   throw new UserNotFoundException ("User is not found for [userId]=" + userId);
			this.activeDirectoryService.resetPassword(userId, newPassword); 
			//update DB
			this.resetPasswordExpiry(userId, userName, workstationId);
			success = true;
		}catch (ActiveDirectoryServiceException ase) {
			logger.error("Error in resetPassword.", ase);
			throw new ChangePasswordException (ase);
		}catch (IncorrectPasswordException ase) {
			logger.error("Error in resetPassword.", ase);
			throw new ChangePasswordException (ase);
		}
		
		return success;
	}
	
	private void resetPasswordExpiry(String userId, String userName, String workstationId) throws ActiveDirectoryServiceException{
		try{
			String updateBy = (StringUtils.isEmpty(userName)) ? "SYSTEM" : userName;
			String updateWkstn = (StringUtils.isEmpty(workstationId)) ? getHostName() : workstationId;
			
			int expiry = 90;
			try {
				Parameters parameter = parametersDao.findById(new ParametersId(PARAM_SCOPE_SYSTEM, PARAM_NAME_PASSWORD_EXPIRY));
				expiry = Integer.valueOf(parameter.getParaShortValue());
			} catch (Throwable t) {
			}
			logger.info("Password Expiry is:"+expiry);
			Calendar c = Calendar.getInstance();
			c.add(Calendar.DATE, expiry);

			Users user = this.findById(userId);
			if (user != null){
				logger.info("user contents:"+ReflectionToStringBuilder.toString(user, ToStringStyle.MULTI_LINE_STYLE));
				
				user.setDateOfPwdExpiry(c.getTime());
				user.setUpdateBy(updateBy);
				user.setUpdateDate(Calendar.getInstance().getTime());
				user.setUpdateWkstnId(updateWkstn);
				logger.info("before reset of password expiry");
				this.saveOrUpdate(user);
				logger.info("after reset of password expiry");
			}
		} catch(Exception e){
			throw new ActiveDirectoryServiceException("Exception in resetPasswordExpiry:"+e.getMessage(),e);
		}
	}
	
	private String getHostName(){
		String hostname = "";
		try {
			hostname = InetAddress.getLocalHost().getHostName().toUpperCase();
		} catch (UnknownHostException e) {
			hostname="";
		}
		return hostname;
	}
	
	
	public boolean isUserExistedInAdAndDB (String userId) throws UserCRUDException {
		boolean userExistedAd = false;
		boolean userExistedDb = false;
		boolean isUserNotValid = false;
		
		try {
			userExistedAd = this.activeDirectoryService.isUserExists(userId);
			if (!userExistedAd) {
				Users entityUser = this.findById(userId);
				if (entityUser!=null)
					userExistedDb = true;
			}
			
			if (userExistedAd || userExistedDb)
				isUserNotValid = true;			
			
		}catch (ActiveDirectoryServiceException adse) {
			logger.error("Error in isUserExistedInAdAndDB.", adse);
			isUserNotValid = false;
		}
		
		return isUserNotValid;
		
	}
	
	
	public boolean isUserExistedInAd (String userId) throws UserCRUDException { 
		try {
			return this.activeDirectoryService.isUserExists(userId);
		}catch (ActiveDirectoryServiceException adse) {
			logger.error("Error in isUserExistedInAd.", adse);
			throw new UserCRUDException ("isUserExists call to AD failed");
		} 
	}
	
	public boolean validateUser (String userId) throws UserCRUDException, ExistingActiveDirectoryException {
		boolean valid = false;
		
		if (StringUtils.isEmpty(userId))
			throw new UserCRUDException ("User ID cannot be empty");
		
		if ( this.findById(userId)!=null )
			throw new UserCRUDException ("User ID is existed on Database");
		
		try {
			if( this.activeDirectoryService.isUserExists(userId) )
				throw new ExistingActiveDirectoryException ("User is existed on LDAP");
			
			valid = true;
		}catch (ActiveDirectoryServiceException adse) {
			throw new ExistingActiveDirectoryException("Error occoured on LDAP", adse);
		}
		
		return valid;
	}

	public ADUser findUserByUserId (String userId) throws UserCRUDException {
		Users dbUser = this.findById(userId);
		ADUser user=null;
		
		try {
			if (dbUser!=null ) {
				List<UserDTO> userList = this.activeDirectoryService.findUser(userId);
				UserDTO userDto = null;
				user=new ADUser();
				ObjectMapper<Users, ADUser> userMapper = new ObjectMapper<Users, ADUser>();
				userMapper.mapObject(dbUser, user);
				user.setSysAdminFlag(dbUser.getSysAdminFlag());
				if (dbUser.getDeleteFlag()!=null)
					user.setDeleteFlag(dbUser.getDeleteFlag());
				if (dbUser.getActiveIndicator()!=null)
					user.setActiveIndicator(dbUser.getActiveIndicator());
				if ( CollectionUtils.isNotEmpty(userList) ) {
					userDto = userList.get(0);
					//[chris] [23 Dec 2013] start
					//user.setFirstName(userDto.getGivenName()); 
					this.parseUser(userDto, user);
					//[chris] [23 Dec 2013] end
				}
			}			
		}catch (ActiveDirectoryServiceException adse) {
			throw new UserCRUDException(adse);
		}
		
		return user;
	}
	
	/*
	 * Field mapping: User vs UserDTO
	 * 
	 * FirstName     : GivenName
	 * LastName      : Surname
	 * MiddleInitial : EmployeeId
	 * SiteCode      : SiteCode
	 * Email         : Email
	 * UserId        : UserId (sAMAccountName)
	 * UserName      : Username (cn) 
	 */
	//to set Info load from AD
	private void parseUser(UserDTO userDto, ADUser user) {
		user.setFirstName(userDto.getGivenName()); 
		user.setLastName(userDto.getSurname());
		if (StringUtils.isNotBlank(userDto.getEmployeeId())) {
			user.setMiddleInitial(userDto.getEmployeeId());
		}
		if (StringUtils.isNotBlank(userDto.getSiteCode()) && StringUtils.isBlank(user.getSiteCode())) {
			user.setSiteCode(userDto.getSiteCode());
		}
		user.setEmail(userDto.getEmail());
		if (StringUtils.isNotBlank(userDto.getUserId()) && StringUtils.isBlank(user.getUserId())) {
			user.setUserId(userDto.getUserId());
		}
		if (StringUtils.isNotBlank(userDto.getUsername()) && StringUtils.isBlank(user.getUserName())) {
			user.setUserName(userDto.getUsername());
		}
	}
	
	//to update Info into AD
	private void parseUserDTO(ADUser user, UserDTO userDto) {
		if (StringUtils.isNotBlank(user.getUserId())) {
			userDto.setUserId(user.getUserId());
		}
		if (StringUtils.isNotBlank(user.getUserId())) {
			if (StringUtils.isBlank(user.getUserName()))
				userDto.setUsername(user.getUserId());
			else 
				userDto.setUsername(user.getUserName());
		} else {
			userDto.setUsername(user.getUserName());
		}
		userDto.setEmail(user.getEmail());
		userDto.setPassword(user.getPassword());
		
		userDto.setGivenName(user.getFirstName());
		userDto.setSurname(user.getLastName());
		if (StringUtils.isNotBlank(user.getMiddleInitial())) {
			userDto.setEmployeeId(user.getMiddleInitial());
		}
		if (StringUtils.isNotBlank(user.getSiteCode())) {
			userDto.setSiteCode(user.getSiteCode());
		}
	}
	
	@Override
	public PaginatedResult<Users> findAllForPagination(Users user, int pageNo, int pageSize, Order order){
		return this.dao.findAllForPagination(user, pageNo, pageSize, order);
	}

	@Override
	public PaginatedResult<UserDTO> findAdUsersNotInDb(String search_firstName, String search_middleName,
			String search_surName, String search_userid, int pageNo, int pageSize) {
		logger.info("findAdUsersNotInDb()");

		// clean search filters
		search_firstName = this.cleanSearchFilter(search_firstName);
		search_middleName = this.cleanSearchFilter(search_middleName);
		search_surName = this.cleanSearchFilter(search_surName);
		search_userid = this.cleanSearchFilter(search_userid);

		//
		List<UserDTO> usersInAd = this.getUsersFromAd(search_firstName, search_middleName, search_surName,
				search_userid);
		logger.info("usersInAd:" + (usersInAd == null?null:usersInAd.size()));

		// sort
		usersInAd = this.sortUsersFromAd(usersInAd);
		logger.info("after sortUsersFromAd:usersInAd:" + (usersInAd == null?null:usersInAd.size()));

		//
		return this.buildPaginatedResult(usersInAd, pageNo, pageSize);
	}

	private List<UserDTO> sortUsersFromAd(List<UserDTO> usersInAd) {

		if (usersInAd == null) {
			return usersInAd;
		}

		if (usersInAd.size() <= 1) {
			return usersInAd;
		}

		boolean switchDone = true;
		while (switchDone) {
			switchDone = false;
			for (int i = 0; i <= (usersInAd.size() - 1 - 1); i++) {
				UserDTO item0 = usersInAd.get(i);
				UserDTO item1 = usersInAd.get(i + 1);
				if (item0.getUserId().compareToIgnoreCase(item1.getUserId()) > 0) {
					usersInAd.set(i, item1);
					usersInAd.set(i + 1, item0);
					switchDone = true;
				}
			}
		}

		return usersInAd;
	}

	private PaginatedResult<UserDTO> buildPaginatedResult(List<UserDTO> usersInAd, int pageNo, int pageSize) {

		PaginatedResult<UserDTO> paginatedResult = new PaginatedResult<UserDTO>();

		paginatedResult.setTotal(usersInAd.size());

		paginatedResult.setPage(pageNo);

		List<UserDTO> itemsInPage = new ArrayList<UserDTO>();
		if (usersInAd.size() > 0) {
			int firstIndex = (pageNo - 1) < 0 ? 0 : (pageNo - 1) * pageSize;
			for (int i = 0; i < usersInAd.size(); i++) {
				if (i >= firstIndex && i < firstIndex + pageSize) {
					itemsInPage.add(usersInAd.get(i));
				}
			}
		}
		paginatedResult.setRows(itemsInPage);

		return paginatedResult;
	}

	private List<UserDTO> getUsersFromAd(String search_firstName, String search_middleName, String search_surName,
			String search_userid) {

		if (this.isSearchFilterBlank(search_firstName, search_middleName, search_surName, search_userid)) {
			return new ArrayList<UserDTO>();
		}

		// get all from adlist based on search keys
		List<UserDTO> usersInAd = null;
		try {
			usersInAd = this.activeDirectoryService.findUsers(search_firstName, search_middleName, search_surName,
					search_userid);
		} catch (Exception e) {
			logger.error("error getUsersFromAd");
			e.printStackTrace();
			usersInAd = null;
		}
		if (usersInAd == null) {
			usersInAd = new ArrayList<UserDTO>();
		}
		logger.info("after findUsers: usersInAd:" + (usersInAd == null?null:usersInAd.size()));

		// remove those already in db
		usersInAd = this.removeUsersThatAreInDb(usersInAd);
		logger.info("after findUsers: removeUsersThatAreInDb:" + (usersInAd == null?null:usersInAd.size()));

		return usersInAd;
	}

	private boolean isSearchFilterBlank(String search_firstName, String search_middleName, String search_surName,
			String search_userid) {
		return (StringUtils.isBlank(search_firstName) && StringUtils.isBlank(search_middleName)
				&& StringUtils.isBlank(search_surName) && StringUtils.isBlank(search_userid));
	}

	private String cleanSearchFilter(String searchFilter) {

		if (StringUtils.isBlank(searchFilter)) {
			return null;
		} else {
			return searchFilter.trim();
		}
	}

	private List<UserDTO> removeUsersThatAreInDb(List<UserDTO> usersInAd) {

		try {
			Iterator<UserDTO> i = usersInAd.iterator();
			while (i.hasNext()) {
				UserDTO s = i.next();
				if (this.isUserExistedInDb(s.getUserId().toUpperCase())) {
					i.remove();
				}
			}
			return usersInAd;
		} catch (Exception e) {
			return new ArrayList<UserDTO>();
		}
	}

	@Override
	public boolean isUserExistedInDb(String userId) throws UserCRUDException {
		try {
			Users entityUser = this.findById(userId);
			if (entityUser != null) {
				return true;
			} else {
				return false;
			}
		} catch (Exception e) {
			logger.error("Error in isUserExistedInDb.", e);
			throw new UserCRUDException("isUserExistedInDb call to findById failed");
		}
	}

	@Override
	public List<Users> findByRoles(String roleId) {
		try {
			List<Users> list = this.dao.findByRoles(roleId);
			return list;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@Override
	public List<Users> findBySiteCode(String siteCode) {
		try {
			List<Users> list = this.dao.findBySiteCode(siteCode);
			return list;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public Users findByUserId(String userId) {
		try {
			Users entityUser = this.findById(userId);
			if (entityUser != null) {
				return entityUser;
			}
		} catch (Exception e) {
			logger.error("Error in isUserExistedInDb.", e);
			e.printStackTrace();
			//throw new UserCRUDException("isUserExistedInDb call to findById failed");
		}
		return null;
	}

	@Override
	public List<Users> getListUserBySiteGroupAndRole(String siteGroup,
			String role) {
		List<Users> list = this.dao.getListUserBySiteGroupAndRole(siteGroup, role);
		return list;
	}
	
	@Override
	public List<Users> getListUserBySiteCode(String siteCode) {
		List<Users> list = this.dao.getListUserBySiteCode(siteCode);
		return list;
	}

	@Override
	public PaginatedResult<UserDto> findAllForPaginationDto(Users user,
			int pageNo, int pageSize, Order order) {
		try {
			PaginatedResult<UserDto> pr = new PaginatedResult<>();
			PaginatedResult<Users> result = this.dao.findAllForPagination(user, pageNo, pageSize, order);
			List<Users> list = null;
			if(result != null && result.getRows() != null){
				list = result.getRows();
				int i = (pageNo - 1) * pageSize;
				List<UserDto> listDto = new ArrayList<UserDto>();
				for(Users users : list){
					UserDto dto = new UserDto();
					i++;
					dto.setStt(i);
					dto.setUserName(users.getUserName());
					dto.setUserId(users.getUserId());
					String roles = "";
					int j = 0;
					for(Roles role : users.getRoles()){
						roles += role.getRoleDesc();
						j++;
						if(j < users.getRoles().size()){
							roles += "/";
						}
					}
					dto.setRoles(roles);
					dto.setPosition(users.getPosition());
					SiteRepository site = siteService.getSiteRepoById(users.getSiteCode());
					dto.setSiteCode(site != null ? site.getSiteName() : "");
					dto.setCreateDate(HelperClass.convertDateToString2(users.getCreateDate()));
					dto.setDeleteFlag(users.getDeleteFlag() ? "Y" : "N");
					listDto.add(dto);
				}
				pr.setRows(listDto);
				pr.setPage(pageNo);
				pr.setTotal(result.getTotal());
				return pr;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public PaginatedResult<UserDto> findAllForPaginationDto(Users user,
			String nameUser, String loginUser, String siteCode, Boolean flag,
			int pageNo, int pageSize, Order order) {
		try {
			PaginatedResult<UserDto> pr = new PaginatedResult<>();
			PaginatedResult<Users> result = this.dao.findAllForPagination(user, nameUser, loginUser, siteCode, flag, pageNo, pageSize, order);
			List<Users> list = null;
			if(result != null && result.getRows() != null){
				list = result.getRows();
				int i = (pageNo - 1) * pageSize;
				List<UserDto> listDto = new ArrayList<UserDto>();
				for(Users users : list){
					UserDto dto = new UserDto();
					i++;
					dto.setStt(i);
					dto.setUserName(users.getUserName());
					dto.setUserId(users.getUserId());
					String roles = "";
					int j = 0;
					for(Roles role : users.getRoles()){
						roles += role.getRoleDesc();
						j++;
						if(j < users.getRoles().size()){
							roles += "/";
						}
					}
					dto.setRoles(roles);
					dto.setPosition(users.getPosition());
					SiteRepository site = siteService.getSiteRepoById(users.getSiteCode());
					dto.setSiteCode(site != null ? site.getSiteName() : "");
					dto.setCreateDate(HelperClass.convertDateToString2(users.getCreateDate()));
					dto.setDeleteFlag(users.getDeleteFlag() ? "Y" : "N");
					listDto.add(dto);
				}
				pr.setRows(listDto);
				pr.setPage(pageNo);
				pr.setTotal(result.getTotal());
				return pr;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
}
