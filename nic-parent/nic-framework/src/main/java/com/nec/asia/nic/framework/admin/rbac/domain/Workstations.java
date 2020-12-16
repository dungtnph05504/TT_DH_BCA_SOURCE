package com.nec.asia.nic.framework.admin.rbac.domain;

// Generated May 16, 2013 6:44:55 PM by Hibernate Tools 3.4.0.CR1

import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

public class Workstations implements java.io.Serializable {

	private String wkstnId;
	private String wkstnDesc;
	private String wkstnType;
	private Boolean accessibleFlag;
	private String counterPriority;
	private String siteCode;
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
	
	
	
	Comparator<Roles> comparator= new Comparator<Roles>(){
		@Override
		public int compare(Roles o1, Roles o2) {				
			
			return o1.getRoleId().compareTo(o2.getRoleId());
		}
	};
	
	
	public Workstations() {
	}

	public Workstations(String wkstnId) {
		this.wkstnId = wkstnId;
	}

	public Workstations(String wkstnId, String wkstnDesc, String wkstnType,
			Boolean accessibleFlag, String counterPriority, String siteCode,
			String systemId, String createBy, String createWkstnId,
			Date createDate, String updateBy, String updateWkstnId,
			Date updateDate, String deleteBy, String deleteWkstnId,
			Date deleteDate, Boolean deleteFlag
			) {
		this.wkstnId = wkstnId;
		this.wkstnDesc = wkstnDesc;
		this.wkstnType = wkstnType;
		this.accessibleFlag = accessibleFlag;
		this.counterPriority = counterPriority;
		this.siteCode = siteCode;
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

	}
	
	

	

	public String getWkstnId() {
		return this.wkstnId;
	}

	public void setWkstnId(String wkstnId) {
		this.wkstnId = wkstnId;
	}

	public String getWkstnDesc() {
		return this.wkstnDesc;
	}

	public void setWkstnDesc(String wkstnDesc) {
		this.wkstnDesc = wkstnDesc;
	}

	public String getWkstnType() {
		return this.wkstnType;
	}

	public void setWkstnType(String wkstnType) {
		this.wkstnType = wkstnType;
	}

	public Boolean getAccessibleFlag() {
		return this.accessibleFlag;
	}

	public void setAccessibleFlag(Boolean accessibleFlag) {
		this.accessibleFlag = accessibleFlag;
	}

	public String getCounterPriority() {
		return this.counterPriority;
	}

	public void setCounterPriority(String counterPriority) {
		this.counterPriority = counterPriority;
	}

	public String getSiteCode() {
		return this.siteCode;
	}

	public void setSiteCode(String siteCode) {
		this.siteCode = siteCode;
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
