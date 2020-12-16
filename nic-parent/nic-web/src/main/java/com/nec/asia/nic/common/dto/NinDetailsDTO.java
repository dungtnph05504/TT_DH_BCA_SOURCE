/**
 * 
 */
package com.nec.asia.nic.common.dto;

import java.io.Serializable;
import java.util.Map;

/**
 * @author aparna_sharma
 *
 */
public class NinDetailsDTO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String dob;
	private String gender;
	private String genderDesc;
	private String maritalStatusDesc;
	private String maritalStatus;
	private String address1;
	private String address2;
	private String address3;
	private String address4;
	private String address5;
	private String address6;
	private String updatedBy;
	private String updateDateTime;
	private String maidenName;
	private String fatherName;
	private String fatherSurname;
	private String fatherNin;
	private String motherNin;
	private String spouseName;
	private String spouseSurname;
	private String spouseNin;
	private String motherName;
	private String motherSurname;
	private String dod;
	private String nin;
	
	private String photo;
	private String signature;
	private String optionSurname;
	private Map<String, String> optionSurnameMap;
	private String surname;
	private String firstname;
	private String surnameBirth;
	private String firstNameLine1;
	private String firstNameLine2;
	private String firstNameLine3;
	private String surnameLine1;
	private String surnameLine2;
	private String surnameLine3;
	private String surnameBirthLine1;
	private String surnameBirthLine2;
	private String surnameBirthLine3;
	private boolean firstNameLenExceedFlag;
	private boolean surnameLenExceedFlag;
	private boolean surnameAtBirthLenExceedFlag;
	
	private String originalFirstname;
	private String originalSurname;
	private String originalSurnameBirth;
	private String overseasAddress;
	private String overseasAddressCountry;
	private String preferredMailingAddress;
	
	private String dobApprox;
	
	public NinDetailsDTO(){

	}
	
	public NinDetailsDTO(String empty){
		
	}
	
	
	public String getSurname() {
		return surname;
	}
	public void setSurname(String surname) {
		this.surname = surname;
	}
	public String getFirstname() {
		return firstname;
	}
	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}
	public String getSurnameBirth() {
		return surnameBirth;
	}
	public void setSurnameBirth(String surnameBirth) {
		this.surnameBirth = surnameBirth;
	}
	public String getDob() {
		return dob;
	}
	public void setDob(String dob) {
		this.dob = dob;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public String getMaritalStatus() {
		return maritalStatus;
	}
	public void setMaritalStatus(String maritalStatus) {
		this.maritalStatus = maritalStatus;
	}
	public String getAddress1() {
		return address1;
	}
	public void setAddress1(String address1) {
		this.address1 = address1;
	}
	public String getAddress2() {
		return address2;
	}
	public void setAddress2(String address2) {
		this.address2 = address2;
	}
	public String getAddress3() {
		return address3;
	}
	public void setAddress3(String address3) {
		this.address3 = address3;
	}
	public String getAddress4() {
		return address4;
	}
	public void setAddress4(String address4) {
		this.address4 = address4;
	}
	public String getUpdatedBy() {
		return updatedBy;
	}
	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}
	public String getUpdateDateTime() {
		return updateDateTime;
	}
	public void setUpdateDateTime(String updateDateTime) {
		this.updateDateTime = updateDateTime;
	}
	public String getMaidenName() {
		return maidenName;
	}
	public void setMaidenName(String maidenName) {
		this.maidenName = maidenName;
	}
	public String getFatherName() {
		return fatherName;
	}
	public void setFatherName(String fatherName) {
		this.fatherName = fatherName;
	}
	public String getFatherSurname() {
		return fatherSurname;
	}
	public void setFatherSurname(String fatherSurname) {
		this.fatherSurname = fatherSurname;
	}
	public String getMotherSurname() {
		return motherSurname;
	}
	public void setMotherSurname(String motherSurname) {
		this.motherSurname = motherSurname;
	}
	public String getDod() {
		return dod;
	}
	public void setDod(String dod) {
		this.dod = dod;
	}




	public String getNin() {
		return nin;
	}




	public void setNin(String nin) {
		this.nin = nin;
	}




	public String getMotherName() {
		return motherName;
	}




	public void setMotherName(String motherName) {
		this.motherName = motherName;
	}

	/**
	 * @return the fatherNin
	 */
	public String getFatherNin() {
		return fatherNin;
	}

	/**
	 * @param fatherNin the fatherNin to set
	 */
	public void setFatherNin(String fatherNin) {
		this.fatherNin = fatherNin;
	}

	/**
	 * @return the motherNin
	 */
	public String getMotherNin() {
		return motherNin;
	}

	/**
	 * @param motherNin the motherNin to set
	 */
	public void setMotherNin(String motherNin) {
		this.motherNin = motherNin;
	}

	/**
	 * @return the spouseName
	 */
	public String getSpouseName() {
		return spouseName;
	}

	/**
	 * @param spouseName the spouseName to set
	 */
	public void setSpouseName(String spouseName) {
		this.spouseName = spouseName;
	}

	/**
	 * @return the spouseSurname
	 */
	public String getSpouseSurname() {
		return spouseSurname;
	}

	/**
	 * @param spouseSurname the spouseSurname to set
	 */
	public void setSpouseSurname(String spouseSurname) {
		this.spouseSurname = spouseSurname;
	}

	/**
	 * @return the spouseNin
	 */
	public String getSpouseNin() {
		return spouseNin;
	}

	/**
	 * @param spouseNin the spouseNin to set
	 */
	public void setSpouseNin(String spouseNin) {
		this.spouseNin = spouseNin;
	}

	public String getGenderDesc() {
		return genderDesc;
	}

	public void setGenderDesc(String genderDesc) {
		this.genderDesc = genderDesc;
	}

	public String getMaritalStatusDesc() {
		return maritalStatusDesc;
	}

	public void setMaritalStatusDesc(String maritalStatusDesc) {
		this.maritalStatusDesc = maritalStatusDesc;
	}

	public String getPhoto() {
		return photo;
	}

	public void setPhoto(String photo) {
		this.photo = photo;
	}

	public String getSignature() {
		return signature;
	}

	public void setSignature(String signature) {
		this.signature = signature;
	}

	public String getOptionSurname() {
		return optionSurname;
	}

	public void setOptionSurname(String optionSurname) {
		this.optionSurname = optionSurname;
	}

	public Map<String, String> getOptionSurnameMap() {
		return optionSurnameMap;
	}

	public void setOptionSurnameMap(Map<String, String> optionSurnameMap) {
		this.optionSurnameMap = optionSurnameMap;
	}

	public boolean isFirstNameLenExceedFlag() {
		return firstNameLenExceedFlag;
	}

	public void setFirstNameLenExceedFlag(boolean firstNameLenExceedFlag) {
		this.firstNameLenExceedFlag = firstNameLenExceedFlag;
	}

	public boolean isSurnameLenExceedFlag() {
		return surnameLenExceedFlag;
	}

	public void setSurnameLenExceedFlag(boolean surnameLenExceedFlag) {
		this.surnameLenExceedFlag = surnameLenExceedFlag;
	}

	public boolean isSurnameAtBirthLenExceedFlag() {
		return surnameAtBirthLenExceedFlag;
	}

	public void setSurnameAtBirthLenExceedFlag(boolean surnameAtBirthLenExceedFlag) {
		this.surnameAtBirthLenExceedFlag = surnameAtBirthLenExceedFlag;
	}

	public String getFirstNameLine1() {
		return firstNameLine1;
	}

	public void setFirstNameLine1(String firstNameLine1) {
		this.firstNameLine1 = firstNameLine1;
	}

	public String getFirstNameLine2() {
		return firstNameLine2;
	}

	public void setFirstNameLine2(String firstNameLine2) {
		this.firstNameLine2 = firstNameLine2;
	}

	public String getFirstNameLine3() {
		return firstNameLine3;
	}

	public void setFirstNameLine3(String firstNameLine3) {
		this.firstNameLine3 = firstNameLine3;
	}

	public String getSurnameLine1() {
		return surnameLine1;
	}

	public void setSurnameLine1(String surnameLine1) {
		this.surnameLine1 = surnameLine1;
	}

	public String getSurnameLine2() {
		return surnameLine2;
	}

	public void setSurnameLine2(String surnameLine2) {
		this.surnameLine2 = surnameLine2;
	}

	public String getSurnameLine3() {
		return surnameLine3;
	}

	public void setSurnameLine3(String surnameLine3) {
		this.surnameLine3 = surnameLine3;
	}

	public String getSurnameBirthLine1() {
		return surnameBirthLine1;
	}

	public void setSurnameBirthLine1(String surnameBirthLine1) {
		this.surnameBirthLine1 = surnameBirthLine1;
	}

	public String getSurnameBirthLine2() {
		return surnameBirthLine2;
	}

	public void setSurnameBirthLine2(String surnameBirthLine2) {
		this.surnameBirthLine2 = surnameBirthLine2;
	}

	public String getSurnameBirthLine3() {
		return surnameBirthLine3;
	}

	public void setSurnameBirthLine3(String surnameBirthLine3) {
		this.surnameBirthLine3 = surnameBirthLine3;
	}

	public String getAddress5() {
		return address5;
	}

	public void setAddress5(String address5) {
		this.address5 = address5;
	}

	public String getAddress6() {
		return address6;
	}

	public void setAddress6(String address6) {
		this.address6 = address6;
	}
	public String getOriginalFirstname() {
		return originalFirstname;
	}

	public void setOriginalFirstname(String originalFirstname) {
		this.originalFirstname = originalFirstname;
	}

	public String getOriginalSurname() {
		return originalSurname;
	}

	public void setOriginalSurname(String originalSurname) {
		this.originalSurname = originalSurname;
	}

	public String getOriginalSurnameBirth() {
		return originalSurnameBirth;
	}

	public void setOriginalSurnameBirth(String originalSurnameBirth) {
		this.originalSurnameBirth = originalSurnameBirth;
	}
	
	public String getOverseasAddress() {
		return overseasAddress;
	}
	public void setOverseasAddress(String overseasAddress) {
		this.overseasAddress = overseasAddress;
	}
	public String getPreferredMailingAddress() {
		return preferredMailingAddress;
	}
	public void setPreferredMailingAddress(String preferredMailingAddress) {
		this.preferredMailingAddress = preferredMailingAddress;
	}

	public String getOverseasAddressCountry() {
		return overseasAddressCountry;
	}

	public void setOverseasAddressCountry(String overseasAddressCountry) {
		this.overseasAddressCountry = overseasAddressCountry;
	}

	public String getDobApprox() {
		return dobApprox;
	}

	public void setDobApprox(String dobApprox) {
		this.dobApprox = dobApprox;
	}

}
