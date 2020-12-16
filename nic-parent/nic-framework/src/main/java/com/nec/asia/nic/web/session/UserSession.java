package com.nec.asia.nic.web.session;

import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import org.apache.commons.lang.StringUtils;



/**
 * 
 * @author Alvin Chua
 *
 */
/**
 * Modification History
 * 28 Oct 2010 (Mahesh) Modified to support additional attributes  
 * 
 * 02 May 2013 (Peddi Swapna): Hard coded the systemId to 'ITF'.
 */
public class UserSession{
	public final static String attributeName = "ATTRIB_USERSESSION";

	private String userId;
	private String userName;
	private String workstationId;
	private String systemId="ITF";
	private Date loginDateTime;
	private String domain;
	private String scope;
	private Set<String> functions = new HashSet<String>();
	private Set<String> roles = new HashSet<String>();
	private int daysToExpire = 0;
	private String firstName;
	private String placeId;

	private HashMap<String, String> attributes = new HashMap<String, String>();
	
	public HashMap<String, String> getAttributes() {
		return attributes;
	}

	public void setAttributes(HashMap<String, String> attributes) {
		this.attributes = attributes;
	}
	
	

	public String getPlaceId() {
		return placeId;
	}

	public void setPlaceId(String placeId) {
		this.placeId = placeId;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getWorkstationId() {
		return workstationId;
	}

	public void setWorkstationId(String workstationId) {
		this.workstationId = workstationId;
	}

	public Date getLoginDateTime() {
		return loginDateTime;
	}

	public void setLoginDateTime(Date loginDateTime) {
		this.loginDateTime = loginDateTime;
	}

	public Set<String> getFunctions() {
		return functions;
	}

	public void setFunctions(Set<String> functions) {
		this.functions = functions;
	}

	public void addFunction(String functionId) {
		this.functions.add(functionId);
	}

	public Set<String> getRoles() {
		return roles;
	}

	public void setRoles(Set<String> roles) {
		this.roles = roles;
	}

	public void addRole(String roleId) {
		this.roles.add(roleId);
	}

	public String getFunctionsList() {
		String functionStr = StringUtils.join(this.functions.iterator(), " ");
		return functionStr;
	}

	public int getDaysToExpire() {
		return daysToExpire;
	}

	public void setDaysToExpire(int daysToExpire) {
		this.daysToExpire = daysToExpire;
	}

	public String getSystemId() {
		return systemId;
	}

	public void setSystemId(String systemId) {
		this.systemId = systemId;
	}

	public String getDomain() {
		return domain;
	}

	public void setDomain(String domain) {
		this.domain = domain;
	}

	public String getScope() {
		return scope;
	}

	public void setScope(String scope) {
		this.scope = scope;
	}

	/**
	 * @return the firstName
	 */
	public String getFirstName() {
		return firstName;
	}

	/**
	 * @param firstName the firstName to set
	 */
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

}
