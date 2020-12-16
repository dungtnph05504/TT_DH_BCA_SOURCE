package com.nec.asia.nic.framework.security.service;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import com.nec.asia.nic.framework.admin.rbac.domain.Roles;

/**

 * @author Ambrocio,Paulo Johannes D.
 * @Company: NEC ASIA PACIFIC PTE
 * @since : Jun 14, 2013
 * <p>
 *	Decription here
 * </p>
 * 
 *
 */

/* 
 * Modification History:
 *  
 * 23 Dec 2013 (chris): change UserId and UserName
 */
public class ADUser implements Serializable {
	private String firstName;
	
	/*
	 *  ldap Name: initials
	 */
	private String middleInitial;
	
	/*
	 *  ldap Name: sn
	 */
	private String lastName;
	
	/*
	 *  ldap Name: streetAddress
	 */
	private String streetAddress;
	
	/*
	 *  ldap Name: l
	 */	
	private String city;
	
	/*
	 *  ldap Name: st
	 */	
	private String provinceOrState;
	
	/*
	 *  ldap Name: co
	 */	
	private String country;
	
	/*
	 *  ldap Name: postalCode
	 */	
	private String postalCode;
	
	/*
	 *  ldap Name: telephoneNumber
	 */	
	private String mainPhone;
	
	
	/*
	 *  ldap Name: mobile
	 */	
	private String mobile;
	
	
	/*
	 * ldap Name: homePhone
	 */
	private String homePhone;
	
	/*
	 * ldap Name: ipPhone
	 */
	private String ipPhone;

	/*
	 * ldap Name: description
	 */
	private String description;
	

	/*
	 * ldap Name: title
	 */
	private String jobTitle;
	


	
	/*
	 * ldap Name: physicalDeliveryOfficeName
	 */
	private String officeAddress;
	

	/*
	 * ldap Name: mail
	 */
	private String email;
	
	
	/*
	 * ldap Name: department
	 */
	private String department;
	

	/*
	 * ldap Name: company
	 */
	private String company;
	
	
	/*
	 * ldap Name: 
	 * used for other fields
	 */
	private String domain;
	
	/*
	 * ldap Name: sAMAccountName
	 * 
	 */
	private String userName;
	
	
	/*
	 * ldap Name: userpassword
	 * 
	 */
	private String password;
	/*
	 * ldap Name: officer
	 * 
	 */
	private String officer; //added by Prasad


	private String organizationalUnitString;
	
	private String distinguishedName;
	
	private int userAccountControl;
	
	private String userId;
	private Boolean sysAdminFlag;
	private String siteCode;
	private Date userStartDate;
	private Date userEndDate;
	private boolean activeIndicator;
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
	private boolean deleteFlag;
	private Date dateOfPwdExpiry;
	private String siteGroupCode;
	private String position;
	
	private Calendar calendar = Calendar.getInstance();
	private Set<Roles> roles;
	
	
	Comparator<Roles> comparator= new Comparator<Roles>(){
		@Override
		public int compare(Roles o1, Roles o2) {				
			
			return o1.getRoleId().compareTo(o2.getRoleId());
		}
	};
	
	
	
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
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getMiddleInitial() {
		return middleInitial;
	}
	public void setMiddleInitial(String middleInitial) {
		this.middleInitial = middleInitial;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getStreetAddress() {
		return streetAddress;
	}
	public void setStreetAddress(String streetAddress) {
		this.streetAddress = streetAddress;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getProvinceOrState() {
		return provinceOrState;
	}
	public void setProvinceOrState(String provinceOrState) {
		this.provinceOrState = provinceOrState;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public String getPostalCode() {
		return postalCode;
	}
	public void setPostalCode(String postalCode) {
		this.postalCode = postalCode;
	}
	public String getMainPhone() {
		return mainPhone;
	}
	public void setMainPhone(String mainPhone) {
		this.mainPhone = mainPhone;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getHomePhone() {
		return homePhone;
	}
	public void setHomePhone(String homePhone) {
		this.homePhone = homePhone;
	}
	public String getIpPhone() {
		return ipPhone;
	}
	public void setIpPhone(String ipPhone) {
		this.ipPhone = ipPhone;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getJobTitle() {
		return jobTitle;
	}
	public void setJobTitle(String jobTitle) {
		this.jobTitle = jobTitle;
	}
	public String getOfficeAddress() {
		return officeAddress;
	}
	public void setOfficeAddress(String officeAddress) {
		this.officeAddress = officeAddress;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getDepartment() {
		return department;
	}
	public void setDepartment(String department) {
		this.department = department;
	}
	public String getCompany() {
		return company;
	}
	public void setCompany(String company) {
		this.company = company;
	}
	public String getDomain() {
		return domain;
	}
	public void setDomain(String domain) {
		this.domain = domain;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getOrganizationalUnitString() {
		return organizationalUnitString;
	}
	public void setOrganizationalUnitString(String organizationalUnitString) {
		this.organizationalUnitString = organizationalUnitString;
	}
	public String getDistinguishedName() {
		return distinguishedName;
	}
	public void setDistinguishedName(String distinguishedName) {
		this.distinguishedName = distinguishedName;
	}
	public int getUserAccountControl() {
		return userAccountControl;
	}
	public void setUserAccountControl(int userAccountControl) {
		this.userAccountControl = userAccountControl;
	}
	public Boolean getSysAdminFlag() {
		return sysAdminFlag;
	}
	public void setSysAdminFlag(Boolean sysAdminFlag) {
		this.sysAdminFlag = sysAdminFlag;
	}
	public String getSiteCode() {
		return siteCode;
	}
	public void setSiteCode(String siteCode) {
		this.siteCode = siteCode;
	}
	public Date getUserStartDate() {
		return userStartDate;
	}
	public void setUserStartDate(Date userStartDate) {
		this.userStartDate = userStartDate;
	}
	public Date getUserEndDate() {
		return userEndDate;
	}
	public void setUserEndDate(Date userEndDate) {
		this.userEndDate = userEndDate;
	}
	public boolean isActiveIndicator() {
		return ((this.userAccountControl & 2) ==2);
	}
	public void setActiveIndicator(boolean activeIndicator) {
		this.activeIndicator = activeIndicator;
	}
	public String getSystemId() {
		return systemId;
	}
	public void setSystemId(String systemId) {
		this.systemId = systemId;
	}
	public String getCreateBy() {
		return createBy;
	}
	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}
	public String getCreateWkstnId() {
		return createWkstnId;
	}
	public void setCreateWkstnId(String createWkstnId) {
		this.createWkstnId = createWkstnId;
	}
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	public String getUpdateBy() {
		return updateBy;
	}
	public void setUpdateBy(String updateBy) {
		this.updateBy = updateBy;
	}
	public String getUpdateWkstnId() {
		return updateWkstnId;
	}
	public void setUpdateWkstnId(String updateWkstnId) {
		this.updateWkstnId = updateWkstnId;
	}
	public Date getUpdateDate() {
		return updateDate;
	}
	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}
	public String getDeleteBy() {
		return deleteBy;
	}
	public void setDeleteBy(String deleteBy) {
		this.deleteBy = deleteBy;
	}
	public String getDeleteWkstnId() {
		return deleteWkstnId;
	}
	public void setDeleteWkstnId(String deleteWkstnId) {
		this.deleteWkstnId = deleteWkstnId;
	}
	public Date getDeleteDate() {
		return deleteDate;
	}
	public void setDeleteDate(Date deleteDate) {
		this.deleteDate = deleteDate;
	}
	public Boolean isDeleteFlag() {
		return deleteFlag;
	}
	public void setDeleteFlag(boolean deleteFlag) {
		this.deleteFlag = deleteFlag;
	}
	public Date getDateOfPwdExpiry() {
		return dateOfPwdExpiry;
	}
	public void setDateOfPwdExpiry(Date dateOfPwdExpiry) {
		this.dateOfPwdExpiry = dateOfPwdExpiry;
	}

	
	public boolean isDisable(){
		return ((this.userAccountControl & 2) ==2);
	}
	
	public String getuserPrincipalName(){
		return userName + "@" + domain;
	}
	
	public String getCN(){
		return firstName + " " +  (middleInitial==null||middleInitial.equals("")?" ":middleInitial +". ") +lastName;
	}
	
	
	//[chris] [23 Dec 2013] update userId
	//public String getUserId() {
	//	return this.userName;
	//}
	public String getUserId() {
		return this.userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	//[chris] [23 Dec 2013] update userId
	
	/**
	 * @return the employeeId
	 */
	public String getOfficer() {
		return officer;
	}

	/**
	 * @param employeeId the employeeId to set
	 */
	public void setOfficer(String officer) {
		this.officer = officer;
	}
	/**
	 * @return the roles
	 */
	public Set<Roles> getRoles() {
		return roles;
	}
	/**
	 * @param roles the roles to set
	 */
	public void setRoles(Set<Roles> roles) {
		this.roles = roles;
	}
	
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (activeIndicator ? 1231 : 1237);
		result = prime * result + ((city == null) ? 0 : city.hashCode());
		result = prime * result + ((company == null) ? 0 : company.hashCode());
		result = prime * result + ((country == null) ? 0 : country.hashCode());
		result = prime * result
				+ ((createBy == null) ? 0 : createBy.hashCode());
		result = prime * result
				+ ((createDate == null) ? 0 : createDate.hashCode());
		result = prime * result
				+ ((createWkstnId == null) ? 0 : createWkstnId.hashCode());
		result = prime * result
				+ ((deleteBy == null) ? 0 : deleteBy.hashCode());
		result = prime * result
				+ ((deleteDate == null) ? 0 : deleteDate.hashCode());
		result = prime * result + (deleteFlag ? 1231 : 1237);
		result = prime * result
				+ ((deleteWkstnId == null) ? 0 : deleteWkstnId.hashCode());
		result = prime * result
				+ ((department == null) ? 0 : department.hashCode());
		result = prime * result
				+ ((description == null) ? 0 : description.hashCode());
		result = prime
				* result
				+ ((distinguishedName == null) ? 0 : distinguishedName
						.hashCode());
		result = prime * result + ((domain == null) ? 0 : domain.hashCode());
		result = prime * result + ((email == null) ? 0 : email.hashCode());
		result = prime * result
				+ ((firstName == null) ? 0 : firstName.hashCode());
		result = prime * result
				+ ((homePhone == null) ? 0 : homePhone.hashCode());
		result = prime * result + ((ipPhone == null) ? 0 : ipPhone.hashCode());
		result = prime * result
				+ ((jobTitle == null) ? 0 : jobTitle.hashCode());
		result = prime * result
				+ ((lastName == null) ? 0 : lastName.hashCode());
		result = prime * result
				+ ((officer == null) ? 0 : officer.hashCode());
		result = prime * result
				+ ((mainPhone == null) ? 0 : mainPhone.hashCode());
		result = prime * result
				+ ((middleInitial == null) ? 0 : middleInitial.hashCode());
		result = prime
				* result
				+ ((roles == null) ? 0 : roles.hashCode());
		result = prime * result + ((mobile == null) ? 0 : mobile.hashCode());
		result = prime * result
				+ ((officeAddress == null) ? 0 : officeAddress.hashCode());
		result = prime
				* result
				+ ((organizationalUnitString == null) ? 0
						: organizationalUnitString.hashCode());
		result = prime * result
				+ ((password == null) ? 0 : password.hashCode());
		result = prime * result
				+ ((postalCode == null) ? 0 : postalCode.hashCode());
		result = prime * result
				+ ((provinceOrState == null) ? 0 : provinceOrState.hashCode());
		result = prime * result
				+ ((siteCode == null) ? 0 : siteCode.hashCode());
		result = prime * result
				+ ((streetAddress == null) ? 0 : streetAddress.hashCode());
		result = prime * result
				+ ((sysAdminFlag == null) ? 0 : sysAdminFlag.hashCode());
		result = prime * result
				+ ((systemId == null) ? 0 : systemId.hashCode());
		result = prime * result
				+ ((updateBy == null) ? 0 : updateBy.hashCode());
		result = prime * result
				+ ((updateDate == null) ? 0 : updateDate.hashCode());
		result = prime * result
				+ ((updateWkstnId == null) ? 0 : updateWkstnId.hashCode());
		result = prime * result + userAccountControl;
		result = prime * result
				+ ((userEndDate == null) ? 0 : userEndDate.hashCode());
		result = prime * result
				+ ((userName == null) ? 0 : userName.hashCode());
		result = prime * result
				+ ((userStartDate == null) ? 0 : userStartDate.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ADUser other = (ADUser) obj;
		if (activeIndicator != other.activeIndicator)
			return false;
		if (city == null) {
			if (other.city != null)
				return false;
		} else if (!city.equals(other.city))
			return false;
		if (company == null) {
			if (other.company != null)
				return false;
		} else if (!company.equals(other.company))
			return false;
		if (country == null) {
			if (other.country != null)
				return false;
		} else if (!country.equals(other.country))
			return false;
		if (createBy == null) {
			if (other.createBy != null)
				return false;
		} else if (!createBy.equals(other.createBy))
			return false;
		if (createDate == null) {
			if (other.createDate != null)
				return false;
		} else {
			if (other.createDate == null)
				return false;
			
			calendar.setTime(createDate);
			calendar.set(Calendar.HOUR_OF_DAY, 0);
			calendar.set(Calendar.MINUTE, 0);
			calendar.set(Calendar.SECOND, 0);
			calendar.set(Calendar.MILLISECOND, 0);
			Date localDate = calendar.getTime();
			calendar.setTime(other.getCreateDate());
			calendar.set(Calendar.HOUR_OF_DAY, 0);
			calendar.set(Calendar.MINUTE, 0);
			calendar.set(Calendar.SECOND, 0);
			calendar.set(Calendar.MILLISECOND, 0);
			Date otherDate = calendar.getTime();
			if (!localDate.equals(otherDate))
			return false;
		}
		if (createWkstnId == null) {
			if (other.createWkstnId != null)
				return false;
		} else if (!createWkstnId.equals(other.createWkstnId))
			return false;
		if (deleteBy == null) {
			if (other.deleteBy != null)
				return false;
		} else if (!deleteBy.equals(other.deleteBy))
			return false;
		if (deleteDate == null) {
			if (other.deleteDate != null)
				return false;
		} else{ 
			
			if(other.deleteDate==null)
				return false;
			
			calendar.setTime(deleteDate);
			calendar.set(Calendar.HOUR_OF_DAY, 0);
			calendar.set(Calendar.MINUTE, 0);
			calendar.set(Calendar.SECOND, 0);
			calendar.set(Calendar.MILLISECOND, 0);
			Date localDate = calendar.getTime();
			calendar.setTime(other.getDeleteDate());
			calendar.set(Calendar.HOUR_OF_DAY, 0);
			calendar.set(Calendar.MINUTE, 0);
			calendar.set(Calendar.SECOND, 0);
			calendar.set(Calendar.MILLISECOND, 0);
			Date otherDate = calendar.getTime();
			
			if (!localDate.equals(otherDate))
				return false;
		}
		if (deleteFlag != other.deleteFlag)
			return false;
		if (deleteWkstnId == null) {
			if (other.deleteWkstnId != null)
				return false;
		} else if (!deleteWkstnId.equals(other.deleteWkstnId))
			return false;
		if (department == null) {
			if (other.department != null)
				return false;
		} else if (!department.equals(other.department))
			return false;
		if (description == null) {
			if (other.description != null)
				return false;
		} else if (!description.equals(other.description))
			return false;
		if (distinguishedName == null) {
			if (other.distinguishedName != null)
				return false;
		} else if (!distinguishedName.equals(other.distinguishedName))
			return false;
		if (domain == null) {
			if (other.domain != null)
				return false;
		} else if (!domain.equals(other.domain))
			return false;
		if (email == null) {
			if (other.email != null)
				return false;
		} else if (!email.equals(other.email))
			return false;
		if (firstName == null) {
			if (other.firstName != null)
				return false;
		} else if (!firstName.equals(other.firstName))
			return false;
		if (homePhone == null) {
			if (other.homePhone != null)
				return false;
		} else if (!homePhone.equals(other.homePhone))
			return false;
		if (ipPhone == null) {
			if (other.ipPhone != null)
				return false;
		} else if (!ipPhone.equals(other.ipPhone))
			return false;
		if (jobTitle == null) {
			if (other.jobTitle != null)
				return false;
		} else if (!jobTitle.equals(other.jobTitle))
			return false;
		if (lastName == null) {
			if (other.lastName != null)
				return false;
		} else if (!lastName.equals(other.lastName))
			return false;
		if (officer == null) {
			if (other.officer != null)
				return false;
		} else if (!officer.equals(other.officer))
			return false;
		if (mainPhone == null) {
			if (other.mainPhone != null)
				return false;
		} else if (!mainPhone.equals(other.mainPhone))
			return false;
		if (middleInitial == null) {
			if (other.middleInitial != null)
				return false;
		} else if (!middleInitial.equals(other.middleInitial))
			return false;
		if (roles == null) {
			if (other.roles != null)
				return false;
		} else if (!roles.equals(other.getRoles()))
			return false;
		if (mobile == null) {
			if (other.mobile != null)
				return false;
		} else if (!mobile.equals(other.mobile))
			return false;
		if (officeAddress == null) {
			if (other.officeAddress != null)
				return false;
		} else if (!officeAddress.equals(other.officeAddress))
			return false;
		if (organizationalUnitString == null) {
			if (other.organizationalUnitString != null)
				return false;
		} else if (!organizationalUnitString
				.equals(other.organizationalUnitString))
			return false;
		if (password == null) {
			if (other.password != null)
				return false;
		} else if (!password.equals(other.password))
			return false;
		if (postalCode == null) {
			if (other.postalCode != null)
				return false;
		} else if (!postalCode.equals(other.postalCode))
			return false;
		if (provinceOrState == null) {
			if (other.provinceOrState != null)
				return false;
		} else if (!provinceOrState.equals(other.provinceOrState))
			return false;
		if (siteCode == null) {
			if (other.siteCode != null)
				return false;
		} else if (!siteCode.equals(other.siteCode))
			return false;
		if (streetAddress == null) {
			if (other.streetAddress != null)
				return false;
		} else if (!streetAddress.equals(other.streetAddress))
			return false;
		if (sysAdminFlag == null) {
			if (other.sysAdminFlag != null)
				return false;
		} else if (!sysAdminFlag.equals(other.sysAdminFlag))
			return false;
		if (systemId == null) {
			if (other.systemId != null)
				return false;
		} else if (!systemId.equals(other.systemId))
			return false;
		if (updateBy == null) {
			if (other.updateBy != null)
				return false;
		} else if (!updateBy.equals(other.updateBy))
			return false;
		if (updateDate == null) {
			if (other.updateDate != null)
				return false;
		} else { 
			
			if(other.updateDate==null)
				return false;
			
			calendar.setTime(deleteDate);
			calendar.set(Calendar.HOUR_OF_DAY, 0);
			calendar.set(Calendar.MINUTE, 0);
			calendar.set(Calendar.SECOND, 0);
			calendar.set(Calendar.MILLISECOND, 0);
			Date localDate = calendar.getTime();
			
			
			calendar.setTime(other.getUpdateDate());
			calendar.set(Calendar.HOUR_OF_DAY, 0);
			calendar.set(Calendar.MINUTE, 0);
			calendar.set(Calendar.SECOND, 0);
			calendar.set(Calendar.MILLISECOND, 0);
			Date otherDate = calendar.getTime();
			
			if (!localDate.equals(otherDate))
				return false;
	
		}
		if (updateWkstnId == null) {
			if (other.updateWkstnId != null)
				return false;
		} else if (!updateWkstnId.equals(other.updateWkstnId))
			return false;
		if (userAccountControl != other.userAccountControl)
			return false;
		if (userEndDate == null) {
			if (other.userEndDate != null)
				return false;
		} else if (!userEndDate.equals(other.userEndDate))
			return false;
		if (userName == null) {
			if (other.userName != null)
				return false;
		} else if (!userName.equals(other.userName))
			return false;
		if (userStartDate == null) {
			if (other.userStartDate != null)
				return false;
		} else if (!userStartDate.equals(other.userStartDate))
			return false;
		return true;
	}
	@Override
	public String toString() {
		return "User [firstName=" + firstName + ", middleInitial="
				+ middleInitial + ", lastName=" + lastName + ", streetAddress="
				+ streetAddress + ", city=" + city + ", provinceOrState="
				+ provinceOrState + ", country=" + country + ", postalCode="
				+ postalCode + ", mainPhone=" + mainPhone + ", mobile="
				+ mobile + ", homePhone=" + homePhone + ", ipPhone=" + ipPhone
				+ ", description=" + description + ", jobTitle=" + jobTitle
				+ ", officeAddress=" + officeAddress + ", email=" + email
				+ ", department=" + department + ", company=" + company
				+ ", domain=" + domain + ", userName=" + userName
				+ ", password=" + password + ", organizationalUnitString="
				+ organizationalUnitString + ", distinguishedName="
				+ distinguishedName + ", userAccountControl="
				+ userAccountControl + ", sysAdminFlag=" + sysAdminFlag
				+ ", siteCode=" + siteCode + ", userStartDate=" + userStartDate
				+ ", userEndDate=" + userEndDate + ", activeIndicator="
				+ activeIndicator + ", systemId=" + systemId + ", createBy="
				+ createBy + ", createWkstnId=" + createWkstnId
				+ ", createDate=" + createDate + ", updateBy=" + updateBy
				+ ", updateWkstnId=" + updateWkstnId + ", updateDate="
				+ updateDate + ", deleteBy=" + deleteBy + ", deleteWkstnId="
				+ deleteWkstnId + ", deleteDate=" + deleteDate
				+ ", deleteFlag=" + deleteFlag + ", roles="
				+ roles + ", officer="+ officer + "]";
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
		return roles.contains(role);
	}
}
