package com.nec.asia.nic.framework.admin.rbac.domain;

// Generated May 16, 2013 6:44:55 PM by Hibernate Tools 3.4.0.CR1

import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

public class Roles implements java.io.Serializable {

	private String roleId;
	private String roleDesc;
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
	private Set<Functions> functions;
	
	private String delFlag;
	private int stt;

	
	Comparator<Functions> comparator= new Comparator<Functions>(){
		@Override
		public int compare(Functions o1, Functions o2) {				
			
			return o1.getFunctionId().compareTo(o2.getFunctionId());
		}
	};
	
	
	public Roles() {
	}

	public Roles(String roleId) {
		this.roleId = roleId;
	}

	public Roles(String roleId, String roleDesc, String systemId,
			String createBy, String createWkstnId, Date createDate,
			String updateBy, String updateWkstnId, Date updateDate,
			String deleteBy, String deleteWkstnId, Date deleteDate,
			Boolean deleteFlag) {
		this.roleId = roleId;
		this.roleDesc = roleDesc;
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

	
	public String getDelFlag() {
		return delFlag;
	}

	public void setDelFlag(String delFlag) {
		this.delFlag = delFlag;
	}

	public int getStt() {
		return stt;
	}

	public void setStt(int stt) {
		this.stt = stt;
	}

	public String getRoleId() {
		return this.roleId;
	}

	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}

	public String getRoleDesc() {
		return this.roleDesc;
	}

	public void setRoleDesc(String roleDesc) {
		this.roleDesc = roleDesc;
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
	 * @return the functions
	 */
	public Set<Functions> getFunctions() {
		return functions;
	}

	/**
	 * @param functions the functions to set
	 */
	public void setFunctions(Set<Functions> functions) {
		this.functions = functions;
	}

	
	/**
	 * @author Ambrocio,Paulo Johannes D.
	 * @Company: NEC ASIA PACIFIC PTE
	 * @since : Jul 12, 2013
	 * <p>
	 *	Convinience method to check if a role contains a certain function
	 * </p>
	 * @param function
	 * @return
	 */
	public boolean containsFunction(Functions function){
		if(functions==null)
			return false;
		List<Functions> functionList = new LinkedList<Functions>(this.functions);
		Collections.sort(functionList,this.comparator);
		int index = Collections.binarySearch(functionList,function,this.comparator);
		return index >= 0;
	}

	/**
	 * @author Ambrocio,Paulo Johannes D.
	 * @Company: NEC ASIA PACIFIC PTE
	 * @since : Jul 12, 2013
	 * <p>
	 *	Convinience method to add certain function
	 * </p>
	 * @param function
	 * @return
	 */
	public boolean addRole(Functions function){
		if(this.functions==null)
		 functions = new HashSet<Functions>();
		return functions.add(function);
	}
	
	/**
	 * @author Ambrocio,Paulo Johannes D.
	 * @Company: NEC ASIA PACIFIC PTE
	 * @since : Jul 12, 2013
	 * <p>
	 *	Convinience method to remove certain function
	 * </p>
	 * @param function
	 * @return
	 */
	public boolean removeRole(Functions function){
		List<Functions> functionList = new LinkedList<Functions>(this.functions);
		Collections.sort(functionList,this.comparator);
		int index = Collections.binarySearch(functionList,function,this.comparator);
		if(index >= 0){
			Functions toRemove = functionList.get(index);
			return this.functions.remove(toRemove);
		}
		return false;
	}

	@Override
	public String toString() {
		return "Roles [roleId=" + roleId + ", roleDesc=" + roleDesc
				+ ", systemId=" + systemId + ", createBy=" + createBy
				+ ", createWkstnId=" + createWkstnId + ", createDate="
				+ createDate + ", updateBy=" + updateBy + ", updateWkstnId="
				+ updateWkstnId + ", updateDate=" + updateDate + ", deleteBy="
				+ deleteBy + ", deleteWkstnId=" + deleteWkstnId
				+ ", deleteDate=" + deleteDate + ", deleteFlag=" + deleteFlag
				+ ", functions=" + functions + ", delFlag=" + delFlag
				+ ", stt=" + stt + ", comparator=" + comparator + "]";
	}

}
