package com.nec.asia.nic.framework.security.ldap.domain;

import java.io.Serializable;

import org.apache.commons.lang.StringUtils;

public class ActiveDirectoryUser implements Serializable {
	
	
	private int id;
	
	/*
	 * ldap Name: givenName
	 */
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
	private String officer;


	private String organizationalUnitString;
	
	private String distinguishedName;
	
	private int userAccountControl;
	
	public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
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
	
	
	
	public String getuserPrincipalName(){
		return userName + "@" + domain;
	}
	
	public String getUID(){
		return userName;
	}
	
	public String getCN(){
		return firstName + " " +  (StringUtils.isBlank(middleInitial)?" ":middleInitial +". ") +lastName;
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
	
	public boolean isDisable(){
		return ((this.userAccountControl & 2) ==2);
	}
	/**
	 * @return the officer
	 */
	public String getOfficer() {
		return officer;
	}


	/**
	 * @param officer the officer to set
	 */
	public void setOfficer(String officer) {
		this.officer = officer;
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((city == null) ? 0 : city.hashCode());
		result = prime * result + ((company == null) ? 0 : company.hashCode());
		result = prime * result + ((country == null) ? 0 : country.hashCode());
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
		result = prime * result + id;
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
				+ ((streetAddress == null) ? 0 : streetAddress.hashCode());
		result = prime * result + userAccountControl;
		result = prime * result
				+ ((userName == null) ? 0 : userName.hashCode());
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
		ActiveDirectoryUser other = (ActiveDirectoryUser) obj;
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
		if (id != other.id)
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
		if (streetAddress == null) {
			if (other.streetAddress != null)
				return false;
		} else if (!streetAddress.equals(other.streetAddress))
			return false;
		if (userAccountControl != other.userAccountControl)
			return false;
		if (userName == null) {
			if (other.userName != null)
				return false;
		} else if (!userName.equals(other.userName))
			return false;
		return true;
	}


	@Override
	public String toString() {
		return "User [id=" + id + ", firstName=" + firstName
				+ ", middleInitial=" + middleInitial + ", lastName=" + lastName
				+ ", streetAddress=" + streetAddress + ", city=" + city
				+ ", provinceOrState=" + provinceOrState + ", country="
				+ country + ", postalCode=" + postalCode + ", mainPhone="
				+ mainPhone + ", mobile=" + mobile + ", homePhone=" + homePhone
				+ ", ipPhone=" + ipPhone + ", description=" + description
				+ ", jobTitle=" + jobTitle + ", officeAddress=" + officeAddress
				+ ", email=" + email + ", department=" + department
				+ ", company=" + company + ", domain=" + domain + ", userName="
				+ userName + ", password=" + password
				+ ", organizationalUnitString=" + organizationalUnitString
				+ ", distinguishedName=" + distinguishedName
				+ ", userAccountControl=" + userAccountControl +", officer=" + officer+ "]";
	}


	


	
	



	
	
	
}
