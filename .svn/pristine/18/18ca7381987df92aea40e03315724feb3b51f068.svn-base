package com.nec.asia.nic.framework.security.service.impl;

import java.net.InetAddress;
import java.util.Date;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.nec.asia.nic.framework.admin.audit.domain.AuditAccessLogs;
import com.nec.asia.nic.framework.admin.code.dao.ParametersDao;
import com.nec.asia.nic.framework.admin.rbac.dao.FunctionsDao;
import com.nec.asia.nic.framework.admin.rbac.dao.RolesDao;
import com.nec.asia.nic.framework.admin.rbac.dao.UsersDao;
import com.nec.asia.nic.framework.admin.rbac.dao.WorkstationsDao;
import com.nec.asia.nic.framework.admin.rbac.domain.Functions;
import com.nec.asia.nic.framework.admin.rbac.domain.Roles;
import com.nec.asia.nic.framework.admin.rbac.domain.Users;
import com.nec.asia.nic.framework.admin.rbac.domain.Workstations;
import com.nec.asia.nic.framework.report.dao.AuditAccessLogDao;
import com.nec.asia.nic.framework.security.ldap.dao.UserADDao;
import com.nec.asia.nic.framework.security.ldap.domain.ActiveDirectoryUser;
import com.nec.asia.nic.framework.security.ldap.exceptions.ChangePasswordException;
import com.nec.asia.nic.framework.security.ldap.exceptions.IncorrectPasswordException;
import com.nec.asia.nic.framework.security.ldap.exceptions.UserNotFoundException;
import com.nec.asia.nic.framework.security.service.SecurityService;
import com.nec.asia.nic.framework.security.service.ADUser;
import com.nec.asia.nic.framework.security.service.exception.EmptyInputException;
import com.nec.asia.nic.framework.security.service.exception.ExistingActiveDirectoryException;
import com.nec.asia.nic.framework.security.service.exception.MaxLengthException;
import com.nec.asia.nic.framework.security.service.exception.PasswordExpiredException;
import com.nec.asia.nic.framework.security.service.exception.SitecodeMismatchException;
import com.nec.asia.nic.framework.security.service.exception.UserAccountInactiveException;
import com.nec.asia.nic.framework.security.service.exception.UserAccountInvalidException;
import com.nec.asia.nic.framework.security.service.exception.UserCRUDException;
import com.nec.asia.nic.utils.mappers.ObjectMapper;


/* 
 * Modification History:
 *  
 * 	18 Jun 2014 (jp): add getStatus for AdminWS.getAuthorizedFunctions
 *  24 Jun 2014 (jp): change getStatus to validateUser and improve implementation for AdminWS.getAuthorizedFunctions
 *  19 Aug 2014 (chris): added server id in audit log
 *
 */
/**
 * TODO remove this class.
 */
@Service("securityService")
@Transactional
public class SecurityServiceImpl implements SecurityService {
	private static Logger logger = Logger.getLogger(SecurityServiceImpl.class);
	
	//Hibernate DAO
	@Autowired
	private UsersDao usersDao;
	@Autowired
	private RolesDao rolesDao;
	@Autowired
	private WorkstationsDao workstationsDao;
	@Autowired
	private FunctionsDao functionDao;
	@Autowired
	private ParametersDao parametersDao;
	@Autowired
	private AuditAccessLogDao auditAccessLogDao;
	
	//LDAP DAO
	//@Autowired
	//private UserADDao userADDao;
	
	public SecurityServiceImpl() {
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public List<Roles> getRolesForWorstation(String workstaionid) {
		
		Workstations wrkStation = workstationsDao.findById(workstaionid);
		if(wrkStation==null)
			return null;
		if(wrkStation.getRoles()!=null)
			return new LinkedList<Roles>(wrkStation.getRoles());
		return null;
	}

	@Override
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
			res=new LinkedList<String>();
			res.addAll(resSet);
		}
		return res;
	}

	@Override
	public List<String> getFunctionIdsForUserId(String userId) {
		Users user = usersDao.findById(userId);
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
			res=new LinkedList<String>();
			res.addAll(resSet);
		}
	
		return res;
	}
	
	

	@Override
	public List<String> getFunctions(String userId, String workstationid) {
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
			res = new LinkedList<String>(resSet);
		return res;
	}

	@Override
	public List<String> getFunctions(String userId, String workstationid, String siteCode) {
		
		return getFunctions(userId,workstationid);
	}
	
	
	@Override	
	public void validateUser(String userId, String workstationid, String siteCode) throws Exception{
		long startTimeMs = System.currentTimeMillis();
		String serverId = "NIC"; 
		//get server Id
		try {
			java.net.InetAddress localMachine = InetAddress.getLocalHost();
			serverId = localMachine.getHostName().toUpperCase();
		} catch (Exception e) {
		}
		logger.info("inside getStatus userId: "+userId);
		logger.info("inside getStatus workstationid: "+workstationid);
		logger.info("inside getStatus siteCode: "+siteCode);
		
		
		Users userDBO = usersDao.findById(userId);
		
		if (userDBO != null){
			logger.info("User DBO contents:"+ReflectionToStringBuilder.toString(userDBO, ToStringStyle.MULTI_LINE_STYLE));
		}

		if (StringUtils.isEmpty(userId) || StringUtils.isEmpty(workstationid)){
			//result = "GF-E1001";
			logger.info("1 or more mandatory fields are not passed");
			throw new EmptyInputException("Invalid field is inputted.","GF-E1001");
		}else if (userId.length() > 64){
			//result = "GF-E1003";
			logger.info("Max length of field is reached.");
			throw new MaxLengthException("Max length of field is reached.", "GF-E1003");
		}else if (userDBO == null){
			//result = "GF-E2001";
			logger.info("User is null - User Account is invalid.");
			throw new UserAccountInvalidException("User Account is invalid.", "GF-E2001");
			
		}else if(userDBO.getDateOfPwdExpiry() != null && userDBO.getDateOfPwdExpiry().before(new Date()) ){
			//result = "GF-E2101";
			String logMsg = "<logInfoDTO><Reason>GF-E2101 - PASSWORD EXPIRED</Reason><Remark>"+ "Password for user "+userId+
							" has expired on "+userDBO.getDateOfPwdExpiry()+"</Remark></logInfoDTO>";
			logger.info("password expired:"+logMsg);
			
			AuditAccessLogs obj =  new AuditAccessLogs();
			obj.setUserId(userId);
			obj.setWkstnId(workstationid);
			obj.setFunctionName("getAuthorizedFunctions");
			obj.setErrorFlag("Y");
			obj.setAuditDate(new Date());
			obj.setAccessLogData(logMsg);
			obj.setServerId(serverId);
			obj.setTimeTaken(System.currentTimeMillis()-startTimeMs);

			auditAccessLogDao.save(obj);
			throw new PasswordExpiredException("Password Expired for the login user.", "GF-E2101");
			
		}else if(StringUtils.isNotEmpty(siteCode) && StringUtils.isNotEmpty(userDBO.getSiteCode()) && !StringUtils.equalsIgnoreCase(userDBO.getSiteCode(), siteCode)){
			//result = "GF-E2004";
			logger.info("User is not authorized with the selected Site.");
			throw new SitecodeMismatchException("User is not authorized with the selected Site.", "GF-E2004");
		}else if(!userDBO.getActiveIndicator()){
			//result = "GF-E2003";
			logger.info("User Account is inactive.");
			throw new UserAccountInactiveException("User Account is inactive.", "GF-E2003");
		}else{
			//result = "GF-I1000";
			logger.info("Get Authorized Function Completed.");
		}
	}
	
	
	@Override
	public List<Roles> getRolesForUser(String userName) {
		Users userDBO = usersDao.findById(userName);
		if(userDBO == null)
			return null;
		if(userDBO.getRoles()!=null)
			return new LinkedList<Roles>(userDBO.getRoles());
		return null;
	}

	@Override
	public Roles getRole(String roleName) {
		return rolesDao.findById(roleName);	
	}

	@Override
	public Workstations getWorkstations(String workStaionId) {
		return workstationsDao.findById(workStaionId);
	}

	@Override
	public String createRole(Roles role) {
		role.setCreateDate(new Date());
		role.setUpdateDate(new Date());
		return rolesDao.save(role);
	}

	@Override
	public void updateRole(Roles role) {
		role.setUpdateDate(new Date());
		rolesDao.saveOrUpdate(role);
	}

	@Override
	public void deleteRole(Roles role) {
		rolesDao.delete(role);
	}
	
	
	public void updateUserInfo(Users userDBO) {
		try{
			usersDao.updateUserInfo(userDBO);
		}catch(Exception ex){
			logger.error("Error occurred while updating the user info. Exception: "+ex.getMessage());
		}
	}

	@Override
	public String createWorkstations(Workstations workstationDBO) {
		workstationDBO.setCreateDate(new Date());
		workstationDBO.setUpdateDate(new Date());
		return workstationsDao.save(workstationDBO);
	}

	@Override
	public void updateWorkstations(Workstations workstationDBO) {
		workstationDBO.setUpdateDate(new Date());
		workstationsDao.saveOrUpdate(workstationDBO);
	}

	@Override
	public void deleteWorkstations(Workstations workstationDBO) {
		workstationsDao.save(workstationDBO);
	}

//	@Override
//	public void createUser(ADUser user)throws UserCRUDException,ExistingActiveDirectoryException{
//		try {
//			ObjectMapper<ADUser, ActiveDirectoryUser> adUserMapper = new ObjectMapper<ADUser, ActiveDirectoryUser>();
//			ObjectMapper<ADUser, Users> dbUserMapper = new ObjectMapper<ADUser, Users>();
//			ActiveDirectoryUser adUser = new ActiveDirectoryUser();
//			Users dbUser = new Users();
//			adUserMapper.mapObject(user, adUser);
//			dbUserMapper.mapObject(user, dbUser);
//			dbUser.setUserId(adUser.getUserName());
//			dbUser.setActiveIndicator(true);
//			dbUser.setCreateDate(new Date());
//			dbUser.setUpdateDate(new Date());
//			// check if an existing user is available in AD
//			ActiveDirectoryUser adExistUser = userADDao.findUserByCN(adUser.getCN());
//			if(adExistUser!=null)
//				throw new ExistingActiveDirectoryException("User : " + user.getCN() + " already exist in the Active Directory");
//			int x = userADDao.save(adUser);
//			if(x<0)
//				throw new UserCRUDException("Error in creating the user in active directory");
//			usersDao.save(dbUser);
//		} catch (Exception e) {
//			logger.error(e);
//			if(e instanceof ExistingActiveDirectoryException)
//				throw (ExistingActiveDirectoryException)e;
//			throw new UserCRUDException(e);
//		}
//	}

//	@Override
//	public void updateUser(ADUser user) throws UserCRUDException {
//		try {
//			ObjectMapper<ADUser,ActiveDirectoryUser> adUserMapper= new ObjectMapper<ADUser,ActiveDirectoryUser>();
//			ObjectMapper<ADUser,Users> dbUserMapper = new ObjectMapper<ADUser,Users>();
//			ActiveDirectoryUser adUser = new ActiveDirectoryUser();
//			Users dbUser = new Users();
//			adUserMapper.mapObject(user, adUser);
//			dbUserMapper.mapObject(user, dbUser);
//			dbUser.setActiveIndicator(true);
//			
//			dbUser.setUpdateDate(new Date());
//			userADDao.saveOrUpdate(adUser);
//			usersDao.saveOrUpdate(dbUser);
//		} catch (Exception e) {
//			logger.error(e);
//			throw new UserCRUDException(e);
//			
//		}
//
//	}

//	@Override
//	public ADUser findUserByUserName(String userName, String organizationalUnit) {
//		ObjectMapper<ActiveDirectoryUser, ADUser> adUserMapper= new ObjectMapper<ActiveDirectoryUser, ADUser>();
//		ObjectMapper<Users, ADUser> dbUserMapper = new ObjectMapper<Users, ADUser>();
//		ADUser user=null;
//		//retieve aduser & DB user
//		ActiveDirectoryUser adUser = userADDao.findUserByUserName(userName,organizationalUnit);
//		Users dbUser = usersDao.findById(userName);
//		if(adUser!=null && dbUser!=null){
//			user=new ADUser();
//			adUserMapper.mapObject(adUser, user);
//			dbUserMapper.mapObject(dbUser, user);
//		}
//		return user;
//	}

//	@Override
//	public ADUser findUserByUserName(String userName) {
//		ObjectMapper<ActiveDirectoryUser, ADUser> adUserMapper= new ObjectMapper<ActiveDirectoryUser, ADUser>();
//		ObjectMapper<Users, ADUser> dbUserMapper = new ObjectMapper<Users, ADUser>();
//		ADUser user=null;
//		//retieve aduser & db user
//		ActiveDirectoryUser adUser = userADDao.findUserByUserName(userName);
//		Users dbUser = usersDao.findById(userName);
//		if(adUser!=null && dbUser!=null){
//			user=new ADUser();
//			adUserMapper.mapObject(adUser, user);
//			dbUserMapper.mapObject(dbUser, user);
//		}
//		return user;
//	}


//	@Override
//	public boolean changePassword(String userName, String oldPassword,
//			String newPassword) throws ChangePasswordException,
//			UserNotFoundException, IncorrectPasswordException {
//
//		return userADDao.changePassword(userName, oldPassword, newPassword);
//	}
//	
//	@Override
//	public boolean authenticate(String userName, String password) throws UserNotFoundException, IncorrectPasswordException {
//
//		return userADDao.authenticate(userName, password);
//	}
//
//	@Override
//	public boolean resetPassword(String userName, String newPassword)
//			throws ChangePasswordException, UserNotFoundException {
//		
//		return userADDao.resetPassword(userName, newPassword);
//	}


//	@Override
//	public void deleteUser(ADUser user) throws UserCRUDException {
//		try {
//			ObjectMapper<ADUser, ActiveDirectoryUser> adUserMapper = new ObjectMapper<ADUser, ActiveDirectoryUser>();
//			ObjectMapper<ADUser, Users> dbUserMapper = new ObjectMapper<ADUser, Users>();
//			ActiveDirectoryUser adUser = new ActiveDirectoryUser();
//			Users dbUser = new Users();
//			adUserMapper.mapObject(user, adUser);
//			dbUserMapper.mapObject(user, dbUser);
//			this.userADDao.delete(adUser);
//			this.usersDao.delete(dbUser);
//		} catch (Exception e) {
//			logger.error(e);
//			throw new UserCRUDException(e);
//		}
//	}
	
	@Override
	public Functions getFunctions(String functionId) {
		return functionDao.findById(functionId);
	}
	
	public UsersDao getUsersDao() {
		return usersDao;
	}

	public void setUsersDao(UsersDao usersDao) {
		this.usersDao = usersDao;
	}

	public RolesDao getRolesDao() {
		return rolesDao;
	}

	public void setRolesDao(RolesDao rolesDao) {
		this.rolesDao = rolesDao;
	}

	public WorkstationsDao getWorkstationsDao() {
		return workstationsDao;
	}

	public void setWorkstationsDao(WorkstationsDao workstationsDao) {
		this.workstationsDao = workstationsDao;
	}

//	public UserADDao getUserADDao() {
//		return userADDao;
//	}
//
//	public void setUserADDao(UserADDao userADDao) {
//		this.userADDao = userADDao;
//	}

	public FunctionsDao getFunctionDao() {
		return functionDao;
	}

	public void setFunctionDao(FunctionsDao functionDao) {
		this.functionDao = functionDao;
	}
	
	public AuditAccessLogDao getAuditAccessLogDao() {
		return auditAccessLogDao;
	}

	public void setAuditAccessLogDao(AuditAccessLogDao auditAccessLogDao) {
		this.auditAccessLogDao = auditAccessLogDao;
	}

}
