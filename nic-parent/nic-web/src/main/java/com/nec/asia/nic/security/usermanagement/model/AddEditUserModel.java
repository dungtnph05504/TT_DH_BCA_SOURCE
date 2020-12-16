/**
 * 
 */
package com.nec.asia.nic.security.usermanagement.model;

import java.util.List;
import java.util.Map;

/**

 * @author Ambrocio,Paulo Johannes D.
 * @Company: NEC ASIA PACIFIC PTE
 * @since : Jul 25, 2013
 * <p>
 *	Decription here
 * </p>
 * 
 *
 */
/* 
 * Modification History:
 *  
 * 23 Dec 2013 (chris): add user i
 */
public class AddEditUserModel {
	private String firstName;
	private String middleInitial;
	private String lastName;
	private String streetAddress;
	private String city;
	private String provinceOrState;
	private String country;
	private String postalCode;
	private String mainPhone;
	private String mobile;
	private String homePhone;
	private String ipPhone;
	private String description;
	private String jobTitle;
	private String officeAddress;
	private String email;
	private String department;
	private String company;
	private String userName;
	private String password;
	private String organizationalUnitString;
	private List<String> unassignedRoles;
	private List<String> assignedRoles;
	private String officer; //added by Prasad
	private String siteCode;
	private String position;
	private Map<String, String> sitCodeMap;//added by prasad
	private String mode; // Added by Swapna
	private String userId; //added by chris [23 Dec 2013]
	//private String passwordTemp; //added by Hoald [21 Sep 2020]
	private Map<String, String> unassignedRoleMaps; //added by tue [01 Feb 2016]
	private Map<String, String> assignedRoleMaps; //added by tue [01 Feb 2016]
	private Map<String, String> departmentMaps;
	private Map<String, String> positionMaps;
	
//	public String getPasswordTemp() {
//		return passwordTemp;
//	}
//
//	public void setPasswordTemp(String passwordTemp) {
//		this.passwordTemp = passwordTemp;
//	}

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

	
	// modified By prasad
	
	private String businessId;
	/**
	 * @return the businessId
	 */
	public String getBusinessId() {
		return businessId;
	}

	/**
	 * @param businessId the businessId to set
	 */
	public void setBusinessId(String businessId) {
		this.businessId = businessId;
	}

	public String getMode() {
		return mode;
	}

	public void setMode(String mode) {
		this.mode = mode;
	}

	/**
	 * 
	 */
	public AddEditUserModel() {
		// TODO Auto-generated constructor stub
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

	/**
	 * @return the middleInitial
	 */
	public String getMiddleInitial() {
		return middleInitial;
	}

	/**
	 * @param middleInitial the middleInitial to set
	 */
	public void setMiddleInitial(String middleInitial) {
		this.middleInitial = middleInitial;
	}

	/**
	 * @return the lastName
	 */
	public String getLastName() {
		return lastName;
	}

	/**
	 * @param lastName the lastName to set
	 */
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	/**
	 * @return the streetAddress
	 */
	public String getStreetAddress() {
		return streetAddress;
	}

	/**
	 * @param streetAddress the streetAddress to set
	 */
	public void setStreetAddress(String streetAddress) {
		this.streetAddress = streetAddress;
	}

	/**
	 * @return the city
	 */
	public String getCity() {
		return city;
	}

	/**
	 * @param city the city to set
	 */
	public void setCity(String city) {
		this.city = city;
	}

	/**
	 * @return the provinceOrState
	 */
	public String getProvinceOrState() {
		return provinceOrState;
	}

	/**
	 * @param provinceOrState the provinceOrState to set
	 */
	public void setProvinceOrState(String provinceOrState) {
		this.provinceOrState = provinceOrState;
	}

	/**
	 * @return the country
	 */
	public String getCountry() {
		return country;
	}

	/**
	 * @param country the country to set
	 */
	public void setCountry(String country) {
		this.country = country;
	}

	/**
	 * @return the postalCode
	 */
	public String getPostalCode() {
		return postalCode;
	}

	/**
	 * @param postalCode the postalCode to set
	 */
	public void setPostalCode(String postalCode) {
		this.postalCode = postalCode;
	}

	/**
	 * @return the mainPhone
	 */
	public String getMainPhone() {
		return mainPhone;
	}

	/**
	 * @param mainPhone the mainPhone to set
	 */
	public void setMainPhone(String mainPhone) {
		this.mainPhone = mainPhone;
	}

	/**
	 * @return the mobile
	 */
	public String getMobile() {
		return mobile;
	}

	/**
	 * @param mobile the mobile to set
	 */
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	/**
	 * @return the homePhone
	 */
	public String getHomePhone() {
		return homePhone;
	}

	/**
	 * @param homePhone the homePhone to set
	 */
	public void setHomePhone(String homePhone) {
		this.homePhone = homePhone;
	}

	/**
	 * @return the ipPhone
	 */
	public String getIpPhone() {
		return ipPhone;
	}

	/**
	 * @param ipPhone the ipPhone to set
	 */
	public void setIpPhone(String ipPhone) {
		this.ipPhone = ipPhone;
	}

	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * @return the jobTitle
	 */
	public String getJobTitle() {
		return jobTitle;
	}

	/**
	 * @param jobTitle the jobTitle to set
	 */
	public void setJobTitle(String jobTitle) {
		this.jobTitle = jobTitle;
	}

	/**
	 * @return the officeAddress
	 */
	public String getOfficeAddress() {
		return officeAddress;
	}

	/**
	 * @param officeAddress the officeAddress to set
	 */
	public void setOfficeAddress(String officeAddress) {
		this.officeAddress = officeAddress;
	}

	/**
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * @param email the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * @return the department
	 */
	public String getDepartment() {
		return department;
	}

	/**
	 * @param department the department to set
	 */
	public void setDepartment(String department) {
		this.department = department;
	}

	/**
	 * @return the company
	 */
	public String getCompany() {
		return company;
	}

	/**
	 * @param company the company to set
	 */
	public void setCompany(String company) {
		this.company = company;
	}

	/**
	 * @return the userName
	 */
	public String getUserName() {
		return userName;
	}

	/**
	 * @param userName the userName to set
	 */
	public void setUserName(String userName) {
		this.userName = userName;
	}

	/**
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * @param password the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * @return the organizationalUnitString
	 */
	public String getOrganizationalUnitString() {
		return organizationalUnitString;
	}

	/**
	 * @param organizationalUnitString the organizationalUnitString to set
	 */
	public void setOrganizationalUnitString(String organizationalUnitString) {
		this.organizationalUnitString = organizationalUnitString;
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
	/**
	 * @return the officer
	 */
	public String getOfficer() {
		return officer;
	}

	/**
	 * @param employeeId the officer to set
	 */
	public void setOfficer(String officer) {
		this.officer = officer;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "AddEditUserModel [firstName=" + firstName + ", middleInitial="
				+ middleInitial + ", lastName=" + lastName + ", streetAddress="
				+ streetAddress + ", city=" + city + ", provinceOrState="
				+ provinceOrState + ", country=" + country + ", postalCode="
				+ postalCode + ", mainPhone=" + mainPhone + ", mobile="
				+ mobile + ", homePhone=" + homePhone + ", ipPhone=" + ipPhone
				+ ", description=" + description + ", jobTitle=" + jobTitle
				+ ", officeAddress=" + officeAddress + ", email=" + email
				+ ", department=" + department + ", company=" + company
				+ ", userName=" + userName + ", password=" + password
				+ ", organizationalUnitString=" + organizationalUnitString
				+ ", unassignedRoles=" + unassignedRoles + ", assignedRoles="
				+ assignedRoles +  ", officer="+ officer + ", siteCode="+ siteCode+ "]"; 
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
	 * @return the sitCodeMap
	 */
	public Map<String, String> getSitCodeMap() {
		return sitCodeMap;
	}

	/**
	 * @param sitCodeMap the sitCodeMap to set
	 */
	public void setSitCodeMap(Map<String, String> sitCodeMap) {
		this.sitCodeMap = sitCodeMap;
	}

	/**
	 * @return the userId
	 */
	public String getUserId() {
		return userId;
	}

	/**
	 * @param userId the userId to set
	 */
	public void setUserId(String userId) {
		this.userId = userId;
	}

	public Map<String, String> getDepartmentMaps() {
		return departmentMaps;
	}

	public void setDepartmentMaps(Map<String, String> departmentMaps) {
		this.departmentMaps = departmentMaps;
	}

	public Map<String, String> getPositionMaps() {
		return positionMaps;
	}

	public void setPositionMaps(Map<String, String> positionMaps) {
		this.positionMaps = positionMaps;
	}

	public String getPosition() {
		return position;
	}

	public void setPosition(String position) {
		this.position = position;
	}
	
	
}
