package com.nec.asia.nic.framework.security.ad.impl;

import java.io.UnsupportedEncodingException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;

import javax.naming.Name;
import javax.naming.directory.Attribute;
import javax.naming.directory.Attributes;
import javax.naming.directory.BasicAttribute;
import javax.naming.directory.BasicAttributes;
import javax.naming.directory.DirContext;
import javax.naming.directory.ModificationItem;
import javax.naming.ldap.LdapContext;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.apache.log4j.Logger;
import org.hibernate.criterion.Order;
import org.owasp.esapi.StringUtilities;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.ldap.core.ContextMapper;
import org.springframework.ldap.core.DirContextAdapter;
import org.springframework.ldap.core.DirContextOperations;
import org.springframework.ldap.core.DistinguishedName;
import org.springframework.ldap.core.LdapTemplate;
import org.springframework.ldap.core.support.LdapContextSource;
import org.springframework.ldap.filter.AndFilter;
import org.springframework.ldap.filter.EqualsFilter;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.nec.asia.nic.framework.PaginatedResult;
import com.nec.asia.nic.framework.admin.code.dao.ParametersDao;
import com.nec.asia.nic.framework.admin.code.domain.Parameters;
import com.nec.asia.nic.framework.admin.code.domain.ParametersId;
import com.nec.asia.nic.framework.admin.rbac.domain.Users;
import com.nec.asia.nic.framework.admin.rbac.service.UserService;
import com.nec.asia.nic.framework.security.ad.ActiveDirectoryService;
import com.nec.asia.nic.framework.security.ad.exception.ActiveDirectoryServiceException;
import com.nec.asia.nic.framework.security.ad.helper.UserDTO;
import com.nec.asia.nic.framework.security.ldap.exceptions.IncorrectPasswordException;
import com.nec.asia.nic.framework.springSupport.SpringServiceManager;

/**
 * @author setia_budiyono
 *
 */

/* 
 * Modification History:
 * 13 Nov 2013 setia_budiyono : Allowed failover for ldap service
 * 23 Dec 2013 (chris): update AD field mapping
 * 20 Jun 2014 (jp): added resetPasswordExpiry
 * 21 Apr 2017 (chris): change to use annotation to load properties - remove bean _ldapService in spring config xml
 */
@Service("activeDirectoryService")
@Configuration
@Transactional
public class ActiveDirectoryServiceImpl implements ActiveDirectoryService{
	public static int UF_PASSWD_NOTREQD = 0x0020;
	public static int UF_PASSWD_CANT_CHANGE = 0x0040;
	public static int UF_NORMAL_ACCOUNT = 0x0200;
	public static int UF_DONT_EXPIRE_PASSWD = 0x10000;
	public static int ACCOUNTDISABLE = 	0x0002;
	
	private static final Logger _logger = Logger.getLogger(ActiveDirectoryServiceImpl.class);
	
	private LdapTemplate ldapTemplate;
	
	@Value("${ldap.primary_hostname}")
	private String primaryHostName;
	@Value("${ldap.secondary_hostname}")
	private String secondaryHostName;
	
	@Value("${ldap.port}")
	private String portNumber;
	@Value("${ldap.user}")
	private String authUser;
	@Value("${ldap.password}")
	private String authPassword;
	@Value("${ldap.base}")
	private String base;
	@Value("${ldap.ssl}")
	private boolean sslEnabled;
	@Value("${ldap.ssl.port}")
	private String sslPortNumber;
	
	private String[] multiIpAddress;
	
	@Value("${ldap.truststore.file}")
	private String keyStore;
	@Value("${ldap.truststore.password}")
	private String trustStorePassword;

	@Autowired
	private ParametersDao parametersDao;
	
	protected static final String PASSWORD_EXPIRY = "PASSWORD_EXPIRY";
	
	public void create(UserDTO user) throws ActiveDirectoryServiceException {
		this.createUser(user, user.getPassword());
	}
	
	@Override
	public boolean isUserExists(String userId) throws ActiveDirectoryServiceException {
		List<UserDTO> users=this.findUser(userId);
		if (CollectionUtils.isNotEmpty(users)){
			return true;	
		}else{
			return false;
		}
	}
	
	/*
	 * Field mapping: AD attribute vs UserDTO
	 * 
	 * cn                         : UserName (UserId)
	 * sAMAccountName             : UserId
	 * userPrincipalName          : UserId
	 * uid                        : UserId
	 * sn                         : Surname
	 * givenName                  : GivenName
	 * employeeID                 : EmployeeId
	 * mail                       : Email
	 * physicalDeliveryOfficeName : SiteCode
	 */
	public void update(UserDTO user) throws ActiveDirectoryServiceException {
		
		List<ModificationItem> modificationList = new ArrayList<ModificationItem>();
		addModificationItem(modificationList,LdapContext.REPLACE_ATTRIBUTE,"givenName", getNullSafeString(user.getGivenName()));
		addModificationItem(modificationList,LdapContext.REPLACE_ATTRIBUTE,"sn", getNullSafeString(user.getSurname()));
		addModificationItem(modificationList,LdapContext.REPLACE_ATTRIBUTE,"mail", getNullSafeString(user.getEmail()));
		addModificationItem(modificationList,LdapContext.REPLACE_ATTRIBUTE,"employeeID", getNullSafeString(user.getEmployeeId()));
		//[chris] [23 Dec 2013] update mapping
		addModificationItem(modificationList,LdapContext.REPLACE_ATTRIBUTE,"physicalDeliveryOfficeName", getNullSafeString(user.getSiteCode()));
		
		this.getLdapTemplate().modifyAttributes(this.buildUserDn(user.getUsername()), modificationList.toArray(new ModificationItem[]{}));		
	}
	
	
	public List<UserDTO> findUser(String userId)throws ActiveDirectoryServiceException{
		AndFilter filter = new AndFilter();
		filter.and(new EqualsFilter("objectclass","person")).and(new EqualsFilter("sAMAccountName",userId));
		List<UserDTO> userList = getLdapTemplate().search(DistinguishedName.EMPTY_PATH, filter.encode(), new UserDTOContextMapper());
		return userList;
	}
	
	
	public void delete(String userId) throws ActiveDirectoryServiceException {
		
		if (StringUtilities.isEmpty(userId)) 
			throw new ActiveDirectoryServiceException("user ID cannot be empty.");
		
		Name dn = buildUserDn(userId);
		getLdapTemplate().unbind(dn);
	}

	
	/*public boolean authenticate(String userId, String password) throws ActiveDirectoryServiceException {
		AndFilter filter = new AndFilter();
		filter.and(new EqualsFilter("objectclass","person")).and(new EqualsFilter("sAMAccountName",userId)).and(new EqualsFilter("userPassword",password));
		List<UserDTO> userDtoList = getLdapTemplate().search(DistinguishedName.EMPTY_PATH, filter.encode(), new NsysUserContextMapper());
		if(userDtoList.size() > 0 ){
			return true;
		}else{
			return false;
		}
	}*/

	public boolean authenticate(String userId, String password) throws ActiveDirectoryServiceException {
		StringBuffer filter = new StringBuffer("(sAMAccountName=")
		                           .append(userId).append(")");
		//StringBuffer filter = new StringBuffer("(uid=").append(userId).append(")");
		return this.getLdapTemplate().authenticate("", filter.toString(), password);
	}
		
	
	@Deprecated
	public void changePassword (String userId, String password) throws ActiveDirectoryServiceException {
		if (StringUtils.isBlank(userId) || StringUtils.isBlank(password))
			throw new ActiveDirectoryServiceException("User ID or Password cannot be empty");
		
		UserDTO userDto = new UserDTO();
		userDto.setUserId(userId);
		this.update(userDto, null, password);
	}

	
//	public boolean changePassword (String userId, String oldPassword, String newPassword) 
//				throws ActiveDirectoryServiceException , IncorrectPasswordException{
//		return this.changePassword(userId, oldPassword, newPassword, true);
//	}

	
	public boolean changePassword (String userId, String oldPassword, String newPassword) 
			throws ActiveDirectoryServiceException , IncorrectPasswordException{
		return this.changePassword(userId, oldPassword, newPassword, true, "", "");
	}
	
	public boolean changePassword (String userId, String oldPassword, String newPassword ,String userName, String workstationId) 
			throws ActiveDirectoryServiceException , IncorrectPasswordException{
		return this.changePassword(userId, oldPassword, newPassword, true, userName, workstationId);
	}
	
	public boolean resetPassword (String userId, String newPassword) 
			throws ActiveDirectoryServiceException, IncorrectPasswordException {
		return this.changePassword(userId, null, newPassword, false, "", "");
	}
	
	public boolean resetPassword (String userId, String newPassword,String userName, String workstationId) 
				throws ActiveDirectoryServiceException, IncorrectPasswordException {
		return this.changePassword(userId, null, newPassword, false, userName, workstationId);
	}

		
	/*
	 * Private methods
	 */
	
	private synchronized LdapTemplate getLdapTemplate() throws ActiveDirectoryServiceException{
		try {
			if (ldapTemplate==null){
				System.setProperty( "javax.net.ssl.trustStore", keyStore );
				System.setProperty( "javax.net.ssl.trustStorePassword", trustStorePassword );
				_logger.debug("Keystore=" + keyStore);
				LdapContextSource ldapContextSource=new LdapContextSource();
				String[] urls=null;
				if (this.multiIpAddress==null && StringUtils.isNotBlank(primaryHostName) && StringUtils.isNotBlank(secondaryHostName)) {
					this.multiIpAddress = new String[] { primaryHostName, secondaryHostName};
				}
				if (this.multiIpAddress!=null && this.multiIpAddress.length > 0) {
					urls=new String[this.multiIpAddress.length];
					for (int i = 0; i < this.multiIpAddress.length; i++) {
						if (this.isSslEnabled()) {
							urls[i]="ldaps://" + this.multiIpAddress[i] + ":" + this.sslPortNumber;						
						}
						else {
							urls[i]="ldap://" + this.multiIpAddress[i] + ":" + this.portNumber;
						}
						_logger.debug("Connect to LDAP Ip Address "+ i +"=" + this.multiIpAddress[i]);							
					}
					ldapContextSource.setUrls(urls);
					ldapContextSource.setUserDn(this.authUser);
					ldapContextSource.setPassword(this.authPassword);
					ldapContextSource.setBase(this.base); 
					ldapContextSource.afterPropertiesSet();
					_logger.debug("Login to AD using UserID: " + this.authUser);
					_logger.debug("Setting to AD base: " + this.base);
					ldapTemplate=new LdapTemplate(ldapContextSource);			
				}
			}
			return ldapTemplate;
		} catch (Exception e) {
			e.printStackTrace();
			throw new ActiveDirectoryServiceException("Error setting the LDAP server settings.");
		}
	}
	

	/**
	 * Private create method that will be used to create users into LDAP after execute createUser
	 * 
	 * @param user
	 * @param wkstnId
	 * @param password
	 */
	private void createUser(UserDTO user, String password)throws ActiveDirectoryServiceException{
		
		 if (StringUtils.isEmpty(password))
			 throw new ActiveDirectoryServiceException ("Password cannot be empty");
		
		 Attributes personAttributes = new BasicAttributes();
	     BasicAttribute personBasicAttribute = new BasicAttribute("objectclass");
	     
	     personBasicAttribute.add("top");
	     personBasicAttribute.add("person");
	     personBasicAttribute.add("organizationalPerson");
	     personBasicAttribute.add("user");
	     
	     personAttributes.put(personBasicAttribute);

	     personAttributes.put("givenName", getNullSafeString(user.getGivenName()));
	     personAttributes.put("cn", user.getUserId());
	     personAttributes.put("sn", getNullSafeString(user.getSurname()) );
	     personAttributes.put("userPrincipalName", user.getUserId());
	     personAttributes.put("sAMAccountName", user.getUserId());
	     personAttributes.put("uid", getNullSafeString(user.getUserId()) );
		 personAttributes.put("mail", getNullSafeString(user.getEmail()) );
		 personAttributes.put("employeeID", getNullSafeString(user.getEmployeeId()) );
		 personAttributes.put("physicalDeliveryOfficeName", getNullSafeString(user.getSiteCode()) );

	     Attribute passAttr = this.createPasswordAttribute(password);
         Attribute userCont = new BasicAttribute("userAccountControl",Integer.toString(UF_NORMAL_ACCOUNT +UF_DONT_EXPIRE_PASSWD ));
		     
	     personAttributes.put(passAttr );		     
	     personAttributes.put(userCont);
		     
	     getLdapTemplate().bind(this.buildUserDn(user.getUserId()), null, personAttributes);
	}
	
	
	/**
	 * Private void update method that will be used to update users into LDAP after updating User
	 * @deprecated
	 * 
	 * @param user
	 * @param roleId
	 * @param password
	 */
	private void update(UserDTO user, String roleId, String password) throws ActiveDirectoryServiceException{
		Name dn = buildUserDn(user.getUserId(), roleId);
		DirContextOperations context = getLdapTemplate().lookupContext(dn);
		mapToUserContext(user, context, password);
		getLdapTemplate().modifyAttributes(context);
	}
	
	//[chris] [23 Dec 2013] update mapping
	/*
	 * Field mapping: AD attribute vs UserDTO
	 * 
	 * cn                         : UserName (UserId)
	 * sAMAccountName             : UserId
	 * userPrincipalName          : UserId
	 * uid                        : UserId
	 * sn                         : Surname
	 * givenName                  : GivenName
	 * employeeID                 : EmployeeId
	 * mail                       : Email
	 * physicalDeliveryOfficeName : SiteCode
	 */
	private static class UserDTOContextMapper implements ContextMapper {
		public Object mapFromContext(Object ctx){
			DirContextAdapter context = (DirContextAdapter) ctx;
			UserDTO userDto = new UserDTO();
			userDto.setUsername(context.getStringAttribute("cn"));
			userDto.setUserId(context.getStringAttribute("sAMAccountName"));
			userDto.setSurname(context.getStringAttribute("sn"));
			userDto.setGivenName(context.getStringAttribute("givenName"));
			if (StringUtils.isNotBlank(context.getStringAttribute("middleName")))  
				userDto.setMiddleName(context.getStringAttribute("middleName"));
			if (StringUtils.isNotBlank(context.getStringAttribute("employeeID")))
				userDto.setEmployeeId(context.getStringAttribute("employeeID"));
			if (StringUtils.isNotBlank(context.getStringAttribute("mail")))
				userDto.setEmail(context.getStringAttribute("mail"));
			if (StringUtils.isNotBlank(context.getStringAttribute("physicalDeliveryOfficeName")))
				userDto.setSiteCode(context.getStringAttribute("physicalDeliveryOfficeName"));
			return userDto;
		}
	}
	
    private Name buildUserDn (String userId, String organizationUnit){
	      DistinguishedName dn = new DistinguishedName();
	      // dn.add("ou", organizationUnit);
	      dn.add("cn", userId);
	      return dn;
	}
	
    private Name buildUserDn (String dnValue) {
	      DistinguishedName dn = new DistinguishedName();
	      dn.add("cn", dnValue);
	      return dn;
	}
   
    /**
     * @deprecated
     */
	private void mapToUserContext(UserDTO person, DirContextOperations context, String userPassword) {
	      context.setAttributeValues("objectclass", new String[] {"top", "person"});
	      context.setAttributeValue("cn", person.getUserId());
	      context.setAttributeValue("sn", person.getUsername());
	     // context.setAttributeValue("userPassword", userPassword);
	      context.setAttributeValue("sAMAccountName", person.getUserId());
	      context.setAttributeValue("uid", person.getUserId());  
	}
	
	/**
	 * Create Unicode password for Active Directory
	 * @param password
	 * @return
	 */
	private Attribute createPasswordAttribute (String password) {
		String quotedPassword = "\"" + password + "\"";
    	byte[] newUnicodePassword=null;
        try {
        	newUnicodePassword = quotedPassword.getBytes("UTF-16LE");
		} catch (UnsupportedEncodingException e1) {
			_logger.error(e1);
		} 
		return new BasicAttribute("unicodePwd", newUnicodePassword);
	}
	
	
	
	
	public boolean changePassword (String userId, String oldPassword, String newPassword, boolean verifyPassword, String userName, String workstationId) 
			throws ActiveDirectoryServiceException, IncorrectPasswordException {
		boolean isValid = false;
		
		_logger.info("xxx inside changePassword");
		
		if (StringUtils.isBlank(userId) || StringUtils.isBlank(newPassword) )
		throw new ActiveDirectoryServiceException("User ID or Old Password or New Password cannot be empty");
		

		List<UserDTO> adUserList = this.findUser(userId);
		if ( CollectionUtils.isEmpty(adUserList) ) 
		throw new ActiveDirectoryServiceException("[User ID]= " + userId + " that provided is not found.");
		UserDTO adUser = adUserList.get(0);
		
		
		if (verifyPassword ) {
			if (!this.authenticate(userId, oldPassword))
				throw new IncorrectPasswordException("Invalid Password.");
		}
		
		
		Attribute newPasswordAttr = this.createPasswordAttribute(newPassword);
		ModificationItem[] mods = new ModificationItem[1];
		mods[0] = new ModificationItem(DirContext.REPLACE_ATTRIBUTE, newPasswordAttr);
		
		getLdapTemplate().modifyAttributes(this.buildUserDn(adUser.getUsername()),mods);
		isValid = true;
		//[2017] moved logic to UserService
		//_logger.info("xxx inside changePassword before reset of password expiry");
		//this.resetPasswordExpiry(userId,userName,workstationId);
		//_logger.info("xxx inside changePassword after reset of password expiry");
		return isValid;

	}

//	private void resetPasswordExpiry(String userId, String userName, String workstationId) throws ActiveDirectoryServiceException{
//		try{
//			String updateBy = (StringUtils.isEmpty(userName)) ? "SYSTEM" : userName;
//			String updateWkstn = (StringUtils.isEmpty(workstationId)) ? getHostName() : workstationId;
//			
//			int expiry = 90;
//			try {
//				_logger.info("xxx parameter parametersDao:"+parametersDao); 
//				Parameters parameter = parametersDao.findById(new ParametersId("SYSTEM", PASSWORD_EXPIRY));
//				_logger.info("xxx parameter:"+parameter.getParaShortValue()); 
//				expiry = Integer.valueOf(parameter.getParaShortValue());
//			} catch (Throwable t) {
//			}
//			_logger.info("Password Expiry is:"+expiry);
//			Calendar c = Calendar.getInstance();
//			c.add(Calendar.DATE, expiry);
//			
//			UserService userService = (UserService) SpringServiceManager.getBean("userService");
//
//			Users user = userService.findById(userId);
//			if (user != null){
//				_logger.info("user contents:"+ReflectionToStringBuilder.toString(user, ToStringStyle.MULTI_LINE_STYLE));
//				
//				user.setDateOfPwdExpiry(c.getTime());
//				user.setUpdateBy(updateBy);
//				user.setUpdateDate(Calendar.getInstance().getTime());
//				user.setUpdateWkstnId(updateWkstn);
//				
//				userService.saveOrUpdate(user);
//			}
//		} catch(Exception e){
//			throw new ActiveDirectoryServiceException("Exception in resetPasswordExpiry:"+e.getMessage(),e);
//		}
//	}

	private String getHostName(){
		String hostname = "";
		try {
			hostname = InetAddress.getLocalHost().getHostName().toUpperCase();
		} catch (UnknownHostException e) {
			hostname="";
		}
		return hostname;
	}
	
	
	
	private static void addModificationItem(List<ModificationItem> modif,int ldapOperation,String attributeName,String attributeValue){
		if(StringUtils.isNotBlank(attributeValue)){
			modif.add(new ModificationItem(ldapOperation, new BasicAttribute(attributeName, getNullSafeString(attributeValue))));
		}
	}
	
	
	private static String getNullSafeString(String string){
		//return string==null?" ":string;
		return StringUtils.isEmpty(string) ? " " : string;
	}
	
	/**
	 * @param portNumber the portNumber to set
	 */
	public void setPortNumber(String portNumber) {
		this.portNumber = portNumber;
	}


	/**
	 * @param ldapTemplate the ldapTemplate to set
	 */
	public void setLdapTemplate(LdapTemplate ldapTemplate) {
		this.ldapTemplate = ldapTemplate;
	}


	/**
	 * @param authUser the authUser to set
	 */
	public void setAuthUser(String authUser) {
		this.authUser = authUser;
	}


	/**
	 * @param authPassword the authPassword to set
	 */
	public void setAuthPassword(String authPassword) {
		this.authPassword = authPassword;
	}


	/**
	 * @param base the base to set
	 */
	public void setBase(String base) {
		this.base = base;
	}

	/**
	 * @return the sslEnabled
	 */
	public boolean isSslEnabled() {
		return sslEnabled;
	}

	/**
	 * @param sslEnabled the sslEnabled to set
	 */
	public void setSslEnabled(boolean sslEnabled) {
		this.sslEnabled = sslEnabled;
	}

	/**
	 * @param sslPortNumber the sslPortNumber to set
	 */
	public void setSslPortNumber(String sslPortNumber) {
		this.sslPortNumber = sslPortNumber;
	}

	/**
	 * @return the multiIpAddress
	 */
	public String[] getMultiIpAddress() {
		return multiIpAddress;
	}

	/**
	 * @param multiIpAddress the multiIpAddress to set
	 */
	public void setMultiIpAddress(String[] multiIpAddress) {
		this.multiIpAddress = multiIpAddress;
	}

	/**
	 * @param keyStore the keyStore to set
	 */
	public void setKeyStore(String keyStore) {
		this.keyStore = keyStore;
	}

	/**
	 * @param trustStorePassword the trustStorePassword to set
	 */
	public void setTrustStorePassword(String trustStorePassword) {
		this.trustStorePassword = trustStorePassword;
	}

	@Override
	public List<UserDTO> findUsers(String search_firstName, String search_middleName, String search_surName,
			String search_userid) throws ActiveDirectoryServiceException {
		
		AndFilter filter = new AndFilter();
		
		filter.and(new EqualsFilter("objectclass","person"));
		
		if (StringUtils.isNotBlank(search_firstName)){
			filter.and(new EqualsFilter("givenName",search_firstName));
		}
		if (StringUtils.isNotBlank(search_middleName)){
			filter.and(new EqualsFilter("middleName",search_middleName));
		}
		if (StringUtils.isNotBlank(search_surName)){
			filter.and(new EqualsFilter("sn",search_surName));
		}
		if (StringUtils.isNotBlank(search_userid)){
			filter.and(new EqualsFilter("sAMAccountName",search_userid));
		}
		 
		return getLdapTemplate().search(DistinguishedName.EMPTY_PATH, filter.encode(), new UserDTOContextMapper()); 
	}
}