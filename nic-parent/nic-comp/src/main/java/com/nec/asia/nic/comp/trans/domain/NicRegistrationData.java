package com.nec.asia.nic.comp.trans.domain;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * NicRegistrationData
 */
/*
 * Modification History
 * 
 * 03 Jun 2016 (chris) : add new field aliasName
 */

@Entity
// @Indexed(index="Persons")
@Table(name = "EPP_REGISTRATION_DATA")
public class NicRegistrationData implements java.io.Serializable {

	/**
	 * The serialVersionUID
	 */
	private static final long serialVersionUID = 1L;

	// @DocumentId
	private String transactionId;

	private Long workflowJobId;

	private Boolean workflowJobCompleteFlag;

	private String createBy;

	private String createWkstnId;

	private Date createDatetime;

	private String updateBy;

	private String updateWkstnId;

	private Date updateDatetime;

	// @Field(index=Index.YES, analyze=Analyze.YES, store=Store.NO)
	private String surnameFull;

	private String surnameLine1;

	private String surnameLine2;

	private Boolean surnameLenExceedFlag;

	// @Field(index=Index.YES, analyze=Analyze.YES, store=Store.NO)
	private String firstnameFull;

	private String firstnameLine1;

	private String firstnameLine2;

	private Boolean firstnameLenExceedFlag;

	// @Field(index=Index.YES, analyze=Analyze.YES, store=Store.NO)
	private String middlenameFull;

	private String middlenameLine1;

	private String middlenameLine2;
	private Boolean middlenameLenExceedFlag;

	private String aliasName;
	private String nationality;

	private Date dateOfBirth;
	private String defDateOfBirth;
	private String approxDob;

	private String printDob;

	private String placeOfBirth;

	private String gender;

	private String maritalStatus;

	private String printRemarks1;

	private String printRemarks2;

	private String addressLine1;

	private String addressLine2;

	private String addressLine3;

	private String addressLine4;
	private String addressLine5;
	private String addressTempDetail;
	private String addressTempVillage;
	private String addressTempDistrict;
	private String addressTempProvince;
	private String addressTempNation;
	private String overseasAddress;
	private String overseasCountry;
	private Date dateNin;
	private String addressNin;
	private String job;
	private String religion;
	private String nation;
	private String travelPurpose;
	private String acquiredCitizenship;
	private String foreignPassportHolder;
	private String occupationDesc;
	private String officeContactNo;
	private String officeAddress;
	private String contactNo;
	private String email;
	private Boolean fpBypassDecision;
	private String fpBypassBy;
	private Date fpBypassDatetime;
	private Integer totalFp;
	private String fpIndicator;
	private String fpQuality;
	private String fpPattern;
	private String fpEncode;
	private Boolean signatureFlag;
	private Boolean facialIndicator;
	private String fatherFullname;
	private Date fatherDateOfBirth;
	private String fatherDefDateOfBirth;
	private String fatherCitizenship;
	private String fatherLost;
	private String motherFullname;
	private Date motherDateOfBirth;
	private String motherDefDateOfBirth;
	private String motherCitizenship;
	private String motherLost;
	private String spouseFullname;
	private Date spouseDateOfBirth;
	private String spouseDefDateOfBirth;
	private String spouseCitizenship;
	private String spouseLost;
	private String numDecision;
	private String nameDepartment;
	private String position;
	private String ownerType;
	private Date dayDecision;
	private String createByName;
	private String searchName;

	public NicRegistrationData(String transactionId, Long workflowJobId,
			Boolean workflowJobCompleteFlag, String createBy,
			String createWkstnId, Date createDatetime, String updateBy,
			String updateWkstnId, Date updateDatetime, String surnameFull,
			String surnameLine1, String surnameLine2,
			Boolean surnameLenExceedFlag, String firstnameFull,
			String firstnameLine1, String firstnameLine2,
			Boolean firstnameLenExceedFlag, String middlenameFull,
			String middlenameLine1, String middlenameLine2,
			Boolean middlenameLenExceedFlag, String aliasName,
			String nationality, Date dateOfBirth, String defDateOfBirth,
			String approxDob, String printDob, String placeOfBirth,
			String gender, String maritalStatus, String printRemarks1,
			String printRemarks2, String addressLine1, String addressLine2,
			String addressLine3, String addressLine4, String addressLine5,
			String addressTempDetail, String addressTempVillage,
			String addressTempDistrict, String addressTempProvince,
			String addressTempNation, String overseasAddress,
			String overseasCountry, Date dateNin, String addressNin,
			String job, String religion, String nation, String travelPurpose,
			String acquiredCitizenship, String foreignPassportHolder,
			String occupationDesc, String officeContactNo,
			String officeAddress, String contactNo, String email,
			Boolean fpBypassDecision, String fpBypassBy, Date fpBypassDatetime,
			Integer totalFp, String fpIndicator, String fpQuality,
			String fpPattern, String fpEncode, Boolean signatureFlag,
			Boolean facialIndicator, String fatherFullname,
			Date fatherDateOfBirth, String fatherDefDateOfBirth,
			String fatherCitizenship, String fatherLost, String motherFullname,
			Date motherDateOfBirth, String motherDefDateOfBirth,
			String motherCitizenship, String motherLost, String spouseFullname,
			Date spouseDateOfBirth, String spouseDefDateOfBirth,
			String spouseCitizenship, String spouseLost, String numDecision,
			String nameDepartment, String position, String ownerType,
			Date dayDecision, String createByName, String searchName) {
		super();
		this.transactionId = transactionId;
		this.workflowJobId = workflowJobId;
		this.workflowJobCompleteFlag = workflowJobCompleteFlag;
		this.createBy = createBy;
		this.createWkstnId = createWkstnId;
		this.createDatetime = createDatetime;
		this.updateBy = updateBy;
		this.updateWkstnId = updateWkstnId;
		this.updateDatetime = updateDatetime;
		this.surnameFull = surnameFull;
		this.surnameLine1 = surnameLine1;
		this.surnameLine2 = surnameLine2;
		this.surnameLenExceedFlag = surnameLenExceedFlag;
		this.firstnameFull = firstnameFull;
		this.firstnameLine1 = firstnameLine1;
		this.firstnameLine2 = firstnameLine2;
		this.firstnameLenExceedFlag = firstnameLenExceedFlag;
		this.middlenameFull = middlenameFull;
		this.middlenameLine1 = middlenameLine1;
		this.middlenameLine2 = middlenameLine2;
		this.middlenameLenExceedFlag = middlenameLenExceedFlag;
		this.aliasName = aliasName;
		this.nationality = nationality;
		this.dateOfBirth = dateOfBirth;
		this.defDateOfBirth = defDateOfBirth;
		this.approxDob = approxDob;
		this.printDob = printDob;
		this.placeOfBirth = placeOfBirth;
		this.gender = gender;
		this.maritalStatus = maritalStatus;
		this.printRemarks1 = printRemarks1;
		this.printRemarks2 = printRemarks2;
		this.addressLine1 = addressLine1;
		this.addressLine2 = addressLine2;
		this.addressLine3 = addressLine3;
		this.addressLine4 = addressLine4;
		this.addressLine5 = addressLine5;
		this.addressTempDetail = addressTempDetail;
		this.addressTempVillage = addressTempVillage;
		this.addressTempDistrict = addressTempDistrict;
		this.addressTempProvince = addressTempProvince;
		this.addressTempNation = addressTempNation;
		this.overseasAddress = overseasAddress;
		this.overseasCountry = overseasCountry;
		this.dateNin = dateNin;
		this.addressNin = addressNin;
		this.job = job;
		this.religion = religion;
		this.nation = nation;
		this.travelPurpose = travelPurpose;
		this.acquiredCitizenship = acquiredCitizenship;
		this.foreignPassportHolder = foreignPassportHolder;
		this.occupationDesc = occupationDesc;
		this.officeContactNo = officeContactNo;
		this.officeAddress = officeAddress;
		this.contactNo = contactNo;
		this.email = email;
		this.fpBypassDecision = fpBypassDecision;
		this.fpBypassBy = fpBypassBy;
		this.fpBypassDatetime = fpBypassDatetime;
		this.totalFp = totalFp;
		this.fpIndicator = fpIndicator;
		this.fpQuality = fpQuality;
		this.fpPattern = fpPattern;
		this.fpEncode = fpEncode;
		this.signatureFlag = signatureFlag;
		this.facialIndicator = facialIndicator;
		this.fatherFullname = fatherFullname;
		this.fatherDateOfBirth = fatherDateOfBirth;
		this.fatherDefDateOfBirth = fatherDefDateOfBirth;
		this.fatherCitizenship = fatherCitizenship;
		this.fatherLost = fatherLost;
		this.motherFullname = motherFullname;
		this.motherDateOfBirth = motherDateOfBirth;
		this.motherDefDateOfBirth = motherDefDateOfBirth;
		this.motherCitizenship = motherCitizenship;
		this.motherLost = motherLost;
		this.spouseFullname = spouseFullname;
		this.spouseDateOfBirth = spouseDateOfBirth;
		this.spouseDefDateOfBirth = spouseDefDateOfBirth;
		this.spouseCitizenship = spouseCitizenship;
		this.spouseLost = spouseLost;
		this.numDecision = numDecision;
		this.nameDepartment = nameDepartment;
		this.position = position;
		this.ownerType = ownerType;
		this.dayDecision = dayDecision;
		this.createByName = createByName;
		this.searchName = searchName;
	}

	public String getSpouseLost() {
		return spouseLost;
	}

	public void setSpouseLost(String spouseLost) {
		this.spouseLost = spouseLost;
	}

	public String getSearchName() {
		return searchName;
	}

	public void setSearchName(String searchName) {
		this.searchName = searchName;
	}

	public NicRegistrationData() {

	}

	public String getCreateByName() {
		return createByName;
	}

	public void setCreateByName(String createByName) {
		this.createByName = createByName;
	}

	public String getAddressLine5() {
		return addressLine5;
	}

	public void setAddressLine5(String addressLine5) {
		this.addressLine5 = addressLine5;
	}

	public String getOverseasAddress() {
		return overseasAddress;
	}

	public void setOverseasAddress(String overseasAddress) {
		this.overseasAddress = overseasAddress;
	}

	public String getOverseasCountry() {
		return overseasCountry;
	}

	public void setOverseasCountry(String overseasCountry) {
		this.overseasCountry = overseasCountry;
	}

	public String getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}

	public Long getWorkflowJobId() {
		return workflowJobId;
	}

	public void setWorkflowJobId(Long workflowJobId) {
		this.workflowJobId = workflowJobId;
	}

	public Boolean getWorkflowJobCompleteFlag() {
		return workflowJobCompleteFlag;
	}

	public void setWorkflowJobCompleteFlag(Boolean workflowJobCompleteFlag) {
		this.workflowJobCompleteFlag = workflowJobCompleteFlag;
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

	public Date getCreateDatetime() {
		return createDatetime;
	}

	public void setCreateDatetime(Date createDatetime) {
		this.createDatetime = createDatetime;
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

	public Date getUpdateDatetime() {
		return updateDatetime;
	}

	public void setUpdateDatetime(Date updateDatetime) {
		this.updateDatetime = updateDatetime;
	}

	public String getSurnameFull() {
		return surnameFull;
	}

	public void setSurnameFull(String surnameFull) {
		this.surnameFull = surnameFull;
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

	public Boolean getSurnameLenExceedFlag() {
		return surnameLenExceedFlag;
	}

	public void setSurnameLenExceedFlag(Boolean surnameLenExceedFlag) {
		this.surnameLenExceedFlag = surnameLenExceedFlag;
	}

	public String getFirstnameFull() {
		return firstnameFull;
	}

	public void setFirstnameFull(String firstnameFull) {
		this.firstnameFull = firstnameFull;
	}

	public String getFirstnameLine1() {
		return firstnameLine1;
	}

	public void setFirstnameLine1(String firstnameLine1) {
		this.firstnameLine1 = firstnameLine1;
	}

	public String getFirstnameLine2() {
		return firstnameLine2;
	}

	public void setFirstnameLine2(String firstnameLine2) {
		this.firstnameLine2 = firstnameLine2;
	}

	public Boolean getFirstnameLenExceedFlag() {
		return firstnameLenExceedFlag;
	}

	public void setFirstnameLenExceedFlag(Boolean firstnameLenExceedFlag) {
		this.firstnameLenExceedFlag = firstnameLenExceedFlag;
	}

	public String getMiddlenameFull() {
		return middlenameFull;
	}

	public void setMiddlenameFull(String middlenameFull) {
		this.middlenameFull = middlenameFull;
	}

	public String getMiddlenameLine1() {
		return middlenameLine1;
	}

	public void setMiddlenameLine1(String middlenameLine1) {
		this.middlenameLine1 = middlenameLine1;
	}

	public String getMiddlenameLine2() {
		return middlenameLine2;
	}

	public void setMiddlenameLine2(String middlenameLine2) {
		this.middlenameLine2 = middlenameLine2;
	}

	public Boolean getMiddlenameLenExceedFlag() {
		return middlenameLenExceedFlag;
	}

	public void setMiddlenameLenExceedFlag(Boolean middlenameLenExceedFlag) {
		this.middlenameLenExceedFlag = middlenameLenExceedFlag;
	}

	public String getAliasName() {
		return aliasName;
	}

	public void setAliasName(String aliasName) {
		this.aliasName = aliasName;
	}

	public String getNationality() {
		return nationality;
	}

	public void setNationality(String nationality) {
		this.nationality = nationality;
	}

	public Date getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(Date dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	public String getDefDateOfBirth() {
		return defDateOfBirth;
	}

	public void setDefDateOfBirth(String defDateOfBirth) {
		this.defDateOfBirth = defDateOfBirth;
	}

	public String getApproxDob() {
		return approxDob;
	}

	public void setApproxDob(String approxDob) {
		this.approxDob = approxDob;
	}

	public String getPrintDob() {
		return printDob;
	}

	public void setPrintDob(String printDob) {
		this.printDob = printDob;
	}

	public String getPlaceOfBirth() {
		return placeOfBirth;
	}

	public void setPlaceOfBirth(String placeOfBirth) {
		this.placeOfBirth = placeOfBirth;
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

	public String getPrintRemarks1() {
		return printRemarks1;
	}

	public void setPrintRemarks1(String printRemarks1) {
		this.printRemarks1 = printRemarks1;
	}

	public String getPrintRemarks2() {
		return printRemarks2;
	}

	public void setPrintRemarks2(String printRemarks2) {
		this.printRemarks2 = printRemarks2;
	}

	public String getAddressLine1() {
		return addressLine1;
	}

	public void setAddressLine1(String addressLine1) {
		this.addressLine1 = addressLine1;
	}

	public String getAddressLine2() {
		return addressLine2;
	}

	public void setAddressLine2(String addressLine2) {
		this.addressLine2 = addressLine2;
	}

	public String getAddressLine3() {
		return addressLine3;
	}

	public void setAddressLine3(String addressLine3) {
		this.addressLine3 = addressLine3;
	}

	public String getAddressLine4() {
		return addressLine4;
	}

	public void setAddressLine4(String addressLine4) {
		this.addressLine4 = addressLine4;
	}

	public String getAddressTempDetail() {
		return addressTempDetail;
	}

	public void setAddressTempDetail(String addressTempDetail) {
		this.addressTempDetail = addressTempDetail;
	}

	public String getAddressTempVillage() {
		return addressTempVillage;
	}

	public void setAddressTempVillage(String addressTempVillage) {
		this.addressTempVillage = addressTempVillage;
	}

	public String getAddressTempDistrict() {
		return addressTempDistrict;
	}

	public void setAddressTempDistrict(String addressTempDistrict) {
		this.addressTempDistrict = addressTempDistrict;
	}

	public String getAddressTempProvince() {
		return addressTempProvince;
	}

	public void setAddressTempProvince(String addressTempProvince) {
		this.addressTempProvince = addressTempProvince;
	}

	public String getAddressTempNation() {
		return addressTempNation;
	}

	public void setAddressTempNation(String addressTempNation) {
		this.addressTempNation = addressTempNation;
	}

	public Date getDateNin() {
		return dateNin;
	}

	public void setDateNin(Date dateNin) {
		this.dateNin = dateNin;
	}

	public String getAddressNin() {
		return addressNin;
	}

	public void setAddressNin(String addressNin) {
		this.addressNin = addressNin;
	}

	public String getJob() {
		return job;
	}

	public void setJob(String job) {
		this.job = job;
	}

	public String getReligion() {
		return religion;
	}

	public void setReligion(String religion) {
		this.religion = religion;
	}

	public String getNation() {
		return nation;
	}

	public void setNation(String nation) {
		this.nation = nation;
	}

	public String getTravelPurpose() {
		return travelPurpose;
	}

	public void setTravelPurpose(String travelPurpose) {
		this.travelPurpose = travelPurpose;
	}

	public String getAcquiredCitizenship() {
		return acquiredCitizenship;
	}

	public void setAcquiredCitizenship(String acquiredCitizenship) {
		this.acquiredCitizenship = acquiredCitizenship;
	}

	public String getForeignPassportHolder() {
		return foreignPassportHolder;
	}

	public void setForeignPassportHolder(String foreignPassportHolder) {
		this.foreignPassportHolder = foreignPassportHolder;
	}

	public String getOccupationDesc() {
		return occupationDesc;
	}

	public void setOccupationDesc(String occupationDesc) {
		this.occupationDesc = occupationDesc;
	}

	public String getOfficeContactNo() {
		return officeContactNo;
	}

	public void setOfficeContactNo(String officeContactNo) {
		this.officeContactNo = officeContactNo;
	}

	public String getOfficeAddress() {
		return officeAddress;
	}

	public void setOfficeAddress(String officeAddress) {
		this.officeAddress = officeAddress;
	}

	public String getContactNo() {
		return contactNo;
	}

	public void setContactNo(String contactNo) {
		this.contactNo = contactNo;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Boolean getFpBypassDecision() {
		return fpBypassDecision;
	}

	public void setFpBypassDecision(Boolean fpBypassDecision) {
		this.fpBypassDecision = fpBypassDecision;
	}

	public String getFpBypassBy() {
		return fpBypassBy;
	}

	public void setFpBypassBy(String fpBypassBy) {
		this.fpBypassBy = fpBypassBy;
	}

	public Date getFpBypassDatetime() {
		return fpBypassDatetime;
	}

	public void setFpBypassDatetime(Date fpBypassDatetime) {
		this.fpBypassDatetime = fpBypassDatetime;
	}

	public Integer getTotalFp() {
		return totalFp;
	}

	public void setTotalFp(Integer totalFp) {
		this.totalFp = totalFp;
	}

	public String getFpIndicator() {
		return fpIndicator;
	}

	public void setFpIndicator(String fpIndicator) {
		this.fpIndicator = fpIndicator;
	}

	public String getFpQuality() {
		return fpQuality;
	}

	public void setFpQuality(String fpQuality) {
		this.fpQuality = fpQuality;
	}

	public String getFpPattern() {
		return fpPattern;
	}

	public void setFpPattern(String fpPattern) {
		this.fpPattern = fpPattern;
	}

	public String getFpEncode() {
		return fpEncode;
	}

	public void setFpEncode(String fpEncode) {
		this.fpEncode = fpEncode;
	}

	public Boolean getSignatureFlag() {
		return signatureFlag;
	}

	public void setSignatureFlag(Boolean signatureFlag) {
		this.signatureFlag = signatureFlag;
	}

	public Boolean getFacialIndicator() {
		return facialIndicator;
	}

	public void setFacialIndicator(Boolean facialIndicator) {
		this.facialIndicator = facialIndicator;
	}

	public String getFatherFullname() {
		return fatherFullname;
	}

	public void setFatherFullname(String fatherFullname) {
		this.fatherFullname = fatherFullname;
	}

	public Date getFatherDateOfBirth() {
		return fatherDateOfBirth;
	}

	public void setFatherDateOfBirth(Date fatherDateOfBirth) {
		this.fatherDateOfBirth = fatherDateOfBirth;
	}

	public String getFatherDefDateOfBirth() {
		return fatherDefDateOfBirth;
	}

	public void setFatherDefDateOfBirth(String fatherDefDateOfBirth) {
		this.fatherDefDateOfBirth = fatherDefDateOfBirth;
	}

	public String getFatherCitizenship() {
		return fatherCitizenship;
	}

	public void setFatherCitizenship(String fatherCitizenship) {
		this.fatherCitizenship = fatherCitizenship;
	}

	public String getFatherLost() {
		return fatherLost;
	}

	public void setFatherLost(String fatherLost) {
		this.fatherLost = fatherLost;
	}

	public String getMotherFullname() {
		return motherFullname;
	}

	public void setMotherFullname(String motherFullname) {
		this.motherFullname = motherFullname;
	}

	public Date getMotherDateOfBirth() {
		return motherDateOfBirth;
	}

	public void setMotherDateOfBirth(Date motherDateOfBirth) {
		this.motherDateOfBirth = motherDateOfBirth;
	}

	public String getMotherDefDateOfBirth() {
		return motherDefDateOfBirth;
	}

	public void setMotherDefDateOfBirth(String motherDefDateOfBirth) {
		this.motherDefDateOfBirth = motherDefDateOfBirth;
	}

	public String getMotherCitizenship() {
		return motherCitizenship;
	}

	public void setMotherCitizenship(String motherCitizenship) {
		this.motherCitizenship = motherCitizenship;
	}

	public String getMotherLost() {
		return motherLost;
	}

	public void setMotherLost(String motherLost) {
		this.motherLost = motherLost;
	}

	public String getSpouseFullname() {
		return spouseFullname;
	}

	public void setSpouseFullname(String spouseFullname) {
		this.spouseFullname = spouseFullname;
	}

	public Date getSpouseDateOfBirth() {
		return spouseDateOfBirth;
	}

	public void setSpouseDateOfBirth(Date spouseDateOfBirth) {
		this.spouseDateOfBirth = spouseDateOfBirth;
	}

	public String getSpouseDefDateOfBirth() {
		return spouseDefDateOfBirth;
	}

	public void setSpouseDefDateOfBirth(String spouseDefDateOfBirth) {
		this.spouseDefDateOfBirth = spouseDefDateOfBirth;
	}

	public String getSpouseCitizenship() {
		return spouseCitizenship;
	}

	public void setSpouseCitizenship(String spouseCitizenship) {
		this.spouseCitizenship = spouseCitizenship;
	}

	public String getNumDecision() {
		return numDecision;
	}

	public void setNumDecision(String numDecision) {
		this.numDecision = numDecision;
	}

	public String getNameDepartment() {
		return nameDepartment;
	}

	public void setNameDepartment(String nameDepartment) {
		this.nameDepartment = nameDepartment;
	}

	public String getPosition() {
		return position;
	}

	public void setPosition(String position) {
		this.position = position;
	}

	public String getOwnerType() {
		return ownerType;
	}

	public void setOwnerType(String ownerType) {
		this.ownerType = ownerType;
	}

	public Date getDayDecision() {
		return dayDecision;
	}

	public void setDayDecision(Date dayDecision) {
		this.dayDecision = dayDecision;
	}

	/*
	 * private String overseasAddress;
	 * 
	 * private String overseasCountry;
	 * 
	 * private String fatherSurname;
	 * 
	 * private String fatherFirstname;
	 * 
	 * private String fatherMiddlename;
	 * 
	 * private String fatherCitizenship;
	 * 
	 * private String motherSurname;
	 * 
	 * private String motherFirstname;
	 * 
	 * private String motherMiddlename;
	 * 
	 * private String motherCitizenship;
	 * 
	 * private String spouseSurname;
	 * 
	 * private String spouseFirstname;
	 * 
	 * private String spouseMiddlename;
	 * 
	 * private String spouseCitizenship;
	 * 
	 * private String travelPurpose;
	 * 
	 * private String acquiredCitizenship;
	 * 
	 * private String foreignPassportHolder;
	 * 
	 * private String occupationDesc;
	 * 
	 * private String officeContactNo;
	 * 
	 * private String officeAddress;
	 * 
	 * private Boolean fpBypassDecision;
	 * 
	 * private String fpBypassBy;
	 * 
	 * private Date fpBypassDatetime;
	 * 
	 * private Integer totalFp;
	 * 
	 * private String fpIndicator;
	 * 
	 * private String fpQuality;
	 * 
	 * private String fpPattern;
	 * 
	 * private String fpEncode;
	 * 
	 * private Boolean signatureFlag;
	 * 
	 * private Boolean facialIndicator;
	 * 
	 * private String email;
	 * 
	 * private String contactNo;
	 * 
	 * private String aliasName; //03 Jun 2016 - added for page 3 printing
	 * 
	 * private String job; //tunt private Integer methodReceive; private Boolean
	 * prioritize; private String religion; private String nation; private
	 * String addressNin; private Date dateNin; private Integer typeChild;
	 * private String addressReceive; private String addressCompany; private
	 * Date fatherDob; private Date motherDob; private Date spouseDob; private
	 * String addressTempNum; private String addressTempStreet; private String
	 * addressTempGuild; private String addressTempDistrict; private String
	 * addressTempCity; private String addressTempNation; private String
	 * stManWorking; private String numDecision; private String govDecision;
	 * private String signerDecision; private String nameDepartment; private
	 * String addressDepartment; private String phoneDepartment; private String
	 * position; private String rank; private String postionEng; private String
	 * civilServants; private String levelRank; private String quantum; private
	 * String toNation; private String transitNation; private String purpose;
	 * private String typeExpense; private Date estimateFrom; private String
	 * estimateTo; private String addressLine5; private Date dateDecision;
	 * 
	 * private String nameProvider; private Date datePassport; private Date
	 * dateCountry; private String purposeKind; private String reasonKind;
	 * private String styleDob; private Boolean fatherLost; private Boolean
	 * motherLost; private String sfDob; private String smDob; private String
	 * ssDob;
	 */

}
