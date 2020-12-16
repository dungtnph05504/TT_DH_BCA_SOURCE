/**
 * 
 */
package com.nec.asia.nic.security.workstationmanagement.model;

import java.util.List;
import java.util.Map;

public class AddEditWorkstationModel {
	private String wkstnId;
	private String wkstnDesc;
	private String siteCode;
	private List<String> unassignedRoles;
	private List<String> assignedRoles;
	private Map<String, String> unassignedRoleMaps; //added by tue [01 Feb 2016]
	private Map<String, String> assignedRoleMaps; //added by tue [01 Feb 2016]
	private Map<String, String> sitCodeMap;//added by prasad


	public AddEditWorkstationModel() {
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * @return the unassignedRoleMaps
	 */
	public Map<String, String> getUnassignedRoleMaps() {
		return unassignedRoleMaps;
	}

	/**
	 * @param unassignedRoleMaps the unassignedRoleMaps to set
	 */
	public void setUnassignedRoleMaps(Map<String, String> unassignedRoleMaps) {
		this.unassignedRoleMaps = unassignedRoleMaps;
	}

	/**
	 * @return the assignedRoleMaps
	 */
	public Map<String, String> getAssignedRoleMaps() {
		return assignedRoleMaps;
	}

	/**
	 * @param assignedRoleMaps the assignedRoleMaps to set
	 */
	public void setAssignedRoleMaps(Map<String, String> assignedRoleMaps) {
		this.assignedRoleMaps = assignedRoleMaps;
	}

	public String getWkstnId() {
		return wkstnId;
	}

	public void setWkstnId(String wkstnId) {
		this.wkstnId = wkstnId;
	}

	public String getWkstnDesc() {
		return wkstnDesc;
	}

	public void setWkstnDesc(String wkstnDesc) {
		this.wkstnDesc = wkstnDesc;
	}

	/**
	 * @return the siteCode
	 */
	public String getSiteCode() {
		return siteCode;
	}

	/**
	 * @param siteCode the siteCode to set
	 */
	public void setSiteCode(String siteCode) {
		this.siteCode = siteCode;
	}

	/**
	 * @return the unassignedRoles
	 */
	public List<String> getUnassignedRoles() {
		return unassignedRoles;
	}

	/**
	 * @param unassignedRoles the unassignedRoles to set
	 */
	public void setUnassignedRoles(List<String> unassignedRoles) {
		this.unassignedRoles = unassignedRoles;
	}

	/**
	 * @return the assignedRoles
	 */
	public List<String> getAssignedRoles() {
		return assignedRoles;
	}

	/**
	 * @param assignedRoles the assignedRoles to set
	 */
	public void setAssignedRoles(List<String> assignedRoles) {
		this.assignedRoles = assignedRoles;
	}
	

	public Map<String, String> getSitCodeMap() {
		return sitCodeMap;
	}

	public void setSitCodeMap(Map<String, String> sitCodeMap) {
		this.sitCodeMap = sitCodeMap;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "AddEditWorkStationModel [workstationId=" + wkstnId + ", workstationDesc="
				+ wkstnDesc + ", siteCode=" + siteCode + 
				", unassignedRoles=" + unassignedRoles + ", assignedRoles="
				+ assignedRoles + "]";
	}
	
}
