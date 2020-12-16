package com.nec.asia.nic.framework.admin.rbac.domain;

// Generated May 16, 2013 6:44:55 PM by Hibernate Tools 3.4.0.CR1

import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;



/* Modification History
 * 16 JUN 2014 (jp) - add DATE_OF_PWD_EXPIRY
 * 
 * 
 * */

public class Users implements java.io.Serializable {

	private String userId;
	private Boolean sysAdminFlag;
	private String siteCode;
	private Date userStartDate;
	private Date userEndDate;
	private Boolean activeIndicator;
	private String systemId;
	private String createBy;
	private String createWkstnId;
	private Date createDate;
	private String updateBy;
	private String updateWkstnId;
	private Date updateDate;
	private String deleteBy;
	private String deleteWkstnId;
	private Date deleteDate;
	private Boolean deleteFlag;
	private Set<Roles> roles;
	private Date dateOfPwdExpiry;
	private String siteGroupCode;
	private String userName;
	private String position;
	private String department;
	private String passwordTemp;

	Comparator<Roles> comparator= new Comparator<Roles>(){
		@Override
		public int compare(Roles o1, Roles o2) {				
			
			return o1.getRoleId().compareTo(o2.getRoleId());
		}
	};
	
	
	public Users() {
	}

	public Users(String userId) {
		this.userId = userId;
	}

	public Users(String userId, Boolean sysAdminFlag, String siteCode,
			Date userStartDate, Date userEndDate, Boolean activeIndicator,
			String systemId, String createBy, String createWkstnId,
			Date createDate, String updateBy, String updateWkstnId,
			Date updateDate, String deleteBy, String deleteWkstnId,
			Date deleteDate, Boolean deleteFlag, Set<Roles> roles,
			Date dateOfPwdExpiry, String siteGroupCode, String userName,
			String position, String department/*, String passwordTemp*/) {
		this.userId = userId;
		this.sysAdminFlag = sysAdminFlag;
		this.siteCode = siteCode;
		this.userStartDate = userStartDate;
		this.userEndDate = userEndDate;
		this.activeIndicator = activeIndicator;
		this.systemId = systemId;
		this.createBy = createBy;
		this.createWkstnId = createWkstnId;
		this.createDate = createDate;
		this.updateBy = updateBy;
		this.updateWkstnId = updateWkstnId;
		this.updateDate = updateDate;
		this.deleteBy = deleteBy;
		this.deleteWkstnId = deleteWkstnId;
		this.deleteDate = deleteDate;
		this.deleteFlag = deleteFlag;
		this.roles = roles;
		this.dateOfPwdExpiry = dateOfPwdExpiry;
		this.siteGroupCode = siteGroupCode;
		this.userName = userName;
		this.position = position;
		this.department = department;
		this.passwordTemp = passwordTemp;
	}

	public String getPasswordTemp() {
		return passwordTemp;
	}

	public void setPasswordTemp(String passwordTemp) {
		this.passwordTemp = passwordTemp;
	}

	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPosition() {
		return position;
	}

	public void setPosition(String position) {
		this.position = position;
	}

	public String getSiteGroupCode() {
		return siteGroupCode;
	}

	public void setSiteGroupCode(String siteGroupCode) {
		this.siteGroupCode = siteGroupCode;
	}

	public Date getDateOfPwdExpiry() {
		return dateOfPwdExpiry;
	}

	public void setDateOfPwdExpiry(Date dateOfPwdExpiry) {
		this.dateOfPwdExpiry = dateOfPwdExpiry;
	}

	public String getUserId() {
		return this.userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public Boolean getSysAdminFlag() {
		return this.sysAdminFlag;
	}
	
	public boolean isSysAdminFlag() {
		return this.sysAdminFlag!=null && this.sysAdminFlag.booleanValue();
	}

	public void setSysAdminFlag(Boolean sysAdminFlag) {
		this.sysAdminFlag = sysAdminFlag;
	}

	public String getSiteCode() {
		return this.siteCode;
	}

	public void setSiteCode(String siteCode) {
		this.siteCode = siteCode;
	}

	public Date getUserStartDate() {
		return this.userStartDate;
	}

	public void setUserStartDate(Date userStartDate) {
		this.userStartDate = userStartDate;
	}

	public Date getUserEndDate() {
		return this.userEndDate;
	}

	public void setUserEndDate(Date userEndDate) {
		this.userEndDate = userEndDate;
	}

	public Boolean getActiveIndicator() {
		return this.activeIndicator;
	}
	
	public boolean isActiveIndicator() {
		return this.activeIndicator!=null && this.activeIndicator.booleanValue();
	}

	public void setActiveIndicator(Boolean activeIndicator) {
		this.activeIndicator = activeIndicator;
	}

	public String getSystemId() {
		return this.systemId;
	}

	public void setSystemId(String systemId) {
		this.systemId = systemId;
	}

	public String getCreateBy() {
		return this.createBy;
	}

	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}

	public String getCreateWkstnId() {
		return this.createWkstnId;
	}

	public void setCreateWkstnId(String createWkstnId) {
		this.createWkstnId = createWkstnId;
	}

	public Date getCreateDate() {
		return this.createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public String getUpdateBy() {
		return this.updateBy;
	}

	public void setUpdateBy(String updateBy) {
		this.updateBy = updateBy;
	}

	public String getUpdateWkstnId() {
		return this.updateWkstnId;
	}

	public void setUpdateWkstnId(String updateWkstnId) {
		this.updateWkstnId = updateWkstnId;
	}

	public Date getUpdateDate() {
		return this.updateDate;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

	public String getDeleteBy() {
		return this.deleteBy;
	}

	public void setDeleteBy(String deleteBy) {
		this.deleteBy = deleteBy;
	}

	public String getDeleteWkstnId() {
		return this.deleteWkstnId;
	}

	public void setDeleteWkstnId(String deleteWkstnId) {
		this.deleteWkstnId = deleteWkstnId;
	}

	public Date getDeleteDate() {
		return this.deleteDate;
	}

	public void setDeleteDate(Date deleteDate) {
		this.deleteDate = deleteDate;
	}

	public Boolean getDeleteFlag() {
		return this.deleteFlag;
	}
	
	public boolean isDeleteFlag() {
		return this.deleteFlag!=null && this.deleteFlag.booleanValue();
	}

	public void setDeleteFlag(Boolean deleteFlag) {
		this.deleteFlag = deleteFlag;
	}

	
	

	/**
	 * @author Ambrocio,Paulo Johannes D.
	 * @Company: NEC ASIA PACIFIC PTE
	 * @since : Jul 12, 2013
	 * <p>
	 *	Convinience method to check if a user contains a certain role
	 * </p>
	 * @param roles
	 * @return
	 */
	public boolean containsRole(Roles role){
		if(roles==null)
			return false;
		List<Roles> roleList = new LinkedList<Roles>(this.roles);
		Collections.sort(roleList,this.comparator);
		int index = Collections.binarySearch(roleList,role,this.comparator);
		return index >= 0;
	}

	/**
	 * @author Ambrocio,Paulo Johannes D.
	 * @Company: NEC ASIA PACIFIC PTE
	 * @since : Jul 12, 2013
	 * <p>
	 *	Convinience method to add certain role
	 * </p>
	 * @param role
	 * @return
	 */
	public boolean addRole(Roles role){
		if(roles==null)
		 roles = new HashSet<Roles>();
		return roles.add(role);
	}
	
	/**
	 * @author Ambrocio,Paulo Johannes D.
	 * @Company: NEC ASIA PACIFIC PTE
	 * @since : Jul 12, 2013
	 * <p>
	 *	Convinience method to remove certain role
	 * </p>
	 * @param role
	 * @return
	 */
	public boolean removeRole(Roles role){
		if(roles==null)
			return false;
		List<Roles> roleList = new LinkedList<Roles>(this.roles);
		Collections.sort(roleList,this.comparator);
		int index = Collections.binarySearch(roleList,role,this.comparator);
		if(index >= 0){
			Roles toRemove = roleList.get(index);
			return this.roles.remove(toRemove);
		}
		return false;
		
	}
	
	/**
	 * @author Ambrocio,Paulo Johannes D.
	 * @Company: NEC ASIA PACIFIC PTE
	 * @since : Jul 12, 2013
	 * <p>
	 *	Decription here
	 * </p>
	 * @param role
	 * @return
	 */
	public boolean setRoles(Set<Roles> roles){
		this.roles = roles;
		return true;
	}

	/**
	 * @return the roles
	 */
	public Set<Roles> getRoles() {
		return roles;
	}
		



	
	
}
