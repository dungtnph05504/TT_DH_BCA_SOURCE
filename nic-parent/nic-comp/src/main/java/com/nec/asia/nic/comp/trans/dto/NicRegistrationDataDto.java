/**
 * 
 */
package com.nec.asia.nic.comp.trans.dto;

import java.util.Date;

import com.nec.asia.nic.comp.trans.domain.NicRegistrationData;

/**
 * @author prasad_karnati
 *
 */

/* 
 * Modification History:
 * 
 * 11 Oct 2013 (Peddi Swapna): Modified NicRegistrationDataDto constructor to set the correct value for father's surname.
 * 10 Dec 2013 (Peddi Swapna): Added new methods to support the NIC Statistics modules during the code merge. 
 */
public class NicRegistrationDataDto {
	
	private String transactionId;
	private String nin;
	private String surnameFull;	
	private String firstnameFull;	
	private String surnameBirthFull;	
	private Long nicId;
	private Long nicUploadJobId;
	
	private Boolean regCompleteFlag;
	private String regOfficerId;
	private Date regCompleteTime;
	private Boolean verCompleteFlag;
	private String verOfficerId;
	private Date verCompleteTime;
	private Boolean payCompleteFlag;
	private String payOfficerId;
	private Date payCompleteTime;
	private Boolean regTransactionCompleteFlag;
	private Boolean uploadFlag;
	private Date uploadTime;
	private Boolean nicJobCompleteFlag;
	private Boolean fullAmputatedFlag;
	private Boolean partialAmputatedFlag;
	private String supervisorDecision;
	private String createBy;
	private String createWkstnId;
	private Date createDate;
	private String updateBy;
	private String updateWkstnId;
	private Date updateDate;
	private String surnameLine1;
	private String surnameLine2;
	private Boolean surnameLenExceedFlag;
	private String firstnameLine1;
	private String firstnameLine2;
	private Boolean firstnameLenExceedFlag;
	private String surnameBirthLine1;
	private String surnameBirthLine2;
	private Boolean surnameBirthLenExceedFlag;
	private String optionSurname; //refer to code table
	
	private Date dateOfBirth;
	private String dateOfBirthDisp;
	private String approxDob;
	private String printDob;
	private String gender;
	private String maritalStatus;	
	private Boolean maritalNapoleanCodeFlag;
	private String addressLine1;
	private String addressLine2;
	private String addressLine3;
	private String addressLine4;
	private String addressLine5;
	private String addressLine6;
	private String preferredMailingAddress;
    private String overseasAddress;
	private String overseasCountry;
	private String fatherNin;
	private Boolean addressUpdatedFlag;
	private String addressUpdatedOfficerId;
	private String addressUpdatedWkstnId;
	private Date addressUpdatedTime;
	private String fatherName;
	private String fatherSurname;	
	private String motherName;
	private String motherSurname;
	private String motherNin;
	private String spouseName;
	private String spouseSurname;
	private String spouseNin;
	private Long verDefaultFp;
	private String fpBypassDecision;
	private String fpBypassOfficerId;
	private Date fpBypassTime;
	private Integer totalFp;
	private String fpIndicator;
	private String fpQuality;
	private String fpEncode;
	private String fpVerifyScore;
	private String signatureIndicator;
	private String signatureFp;
	private String facialIndicator;
	private Boolean seniorCitizenFlag;
	//private Boolean priority;
	private Boolean fpVerifyFlag;
	private Boolean cardVerifyFlag;
	private Boolean expressFlag;
	private Boolean conversionFlag;
	private String email;
	private String contactNo;
	
	private NicTransactionDto nicTransaction;
	
	private String cpdNin;
	private String cpdSurnameFull;	
	private String cpdFirstnameFull;	
	private String cpdSurnameBirthFull;	
	private Date   cpdDateOfBirth;
	private String cpdGender;
	private String cpdMaritalStatus;	
	private String cpdAddressLine1;
	private String cpdAddressLine2;
	private String cpdAddressLine3;
	private String capdAddressLine4;
	private String cpdAddressLine5;
	private String cpdAddressLine6;
	private String cpdPreferredMailingAddress;
	private String cpdFatherName;
	private String cpdFatherSurname;	
	private String cpdMotherName;
	private String cpdMotherSurname;
	
	private String dateFrom;
	/**
	 * @return the transactionId
	 */
	public String getTransactionId() {
		return transactionId;
	}


	/**
	 * @param transactionId the transactionId to set
	 */
	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}


	/**
	 * @return the nicId
	 */
	public Long getNicId() {
		return nicId;
	}


	/**
	 * @param nicId the nicId to set
	 */
	public void setNicId(Long nicId) {
		this.nicId = nicId;
	}


	/**
	 * @return the nicUploadJobId
	 */
	public Long getNicUploadJobId() {
		return nicUploadJobId;
	}


	/**
	 * @param nicUploadJobId the nicUploadJobId to set
	 */
	public void setNicUploadJobId(Long nicUploadJobId) {
		this.nicUploadJobId = nicUploadJobId;
	}


	/**
	 * @return the regCompleteFlag
	 */
	public Boolean getRegCompleteFlag() {
		return regCompleteFlag;
	}


	/**
	 * @param regCompleteFlag the regCompleteFlag to set
	 */
	public void setRegCompleteFlag(Boolean regCompleteFlag) {
		this.regCompleteFlag = regCompleteFlag;
	}


	/**
	 * @return the regOfficerId
	 */
	public String getRegOfficerId() {
		return regOfficerId;
	}


	/**
	 * @param regOfficerId the regOfficerId to set
	 */
	public void setRegOfficerId(String regOfficerId) {
		this.regOfficerId = regOfficerId;
	}


	/**
	 * @return the regCompleteTime
	 */
	public Date getRegCompleteTime() {
		return regCompleteTime;
	}


	/**
	 * @param regCompleteTime the regCompleteTime to set
	 */
	public void setRegCompleteTime(Date regCompleteTime) {
		this.regCompleteTime = regCompleteTime;
	}


	/**
	 * @return the verCompleteFlag
	 */
	public Boolean getVerCompleteFlag() {
		return verCompleteFlag;
	}


	/**
	 * @param verCompleteFlag the verCompleteFlag to set
	 */
	public void setVerCompleteFlag(Boolean verCompleteFlag) {
		this.verCompleteFlag = verCompleteFlag;
	}


	/**
	 * @return the verOfficerId
	 */
	public String getVerOfficerId() {
		return verOfficerId;
	}


	/**
	 * @param verOfficerId the verOfficerId to set
	 */
	public void setVerOfficerId(String verOfficerId) {
		this.verOfficerId = verOfficerId;
	}


	/**
	 * @return the verCompleteTime
	 */
	public Date getVerCompleteTime() {
		return verCompleteTime;
	}


	/**
	 * @param verCompleteTime the verCompleteTime to set
	 */
	public void setVerCompleteTime(Date verCompleteTime) {
		this.verCompleteTime = verCompleteTime;
	}


	/**
	 * @return the payCompleteFlag
	 */
	public Boolean getPayCompleteFlag() {
		return payCompleteFlag;
	}


	/**
	 * @param payCompleteFlag the payCompleteFlag to set
	 */
	public void setPayCompleteFlag(Boolean payCompleteFlag) {
		this.payCompleteFlag = payCompleteFlag;
	}


	/**
	 * @return the payOfficerId
	 */
	public String getPayOfficerId() {
		return payOfficerId;
	}


	/**
	 * @param payOfficerId the payOfficerId to set
	 */
	public void setPayOfficerId(String payOfficerId) {
		this.payOfficerId = payOfficerId;
	}


	/**
	 * @return the payCompleteTime
	 */
	public Date getPayCompleteTime() {
		return payCompleteTime;
	}


	/**
	 * @param payCompleteTime the payCompleteTime to set
	 */
	public void setPayCompleteTime(Date payCompleteTime) {
		this.payCompleteTime = payCompleteTime;
	}


	/**
	 * @return the regTransactionCompleteFlag
	 */
	public Boolean getRegTransactionCompleteFlag() {
		return regTransactionCompleteFlag;
	}


	/**
	 * @param regTransactionCompleteFlag the regTransactionCompleteFlag to set
	 */
	public void setRegTransactionCompleteFlag(Boolean regTransactionCompleteFlag) {
		this.regTransactionCompleteFlag = regTransactionCompleteFlag;
	}


	/**
	 * @return the uploadFlag
	 */
	public Boolean getUploadFlag() {
		return uploadFlag;
	}


	/**
	 * @param uploadFlag the uploadFlag to set
	 */
	public void setUploadFlag(Boolean uploadFlag) {
		this.uploadFlag = uploadFlag;
	}


	/**
	 * @return the uploadTime
	 */
	public Date getUploadTime() {
		return uploadTime;
	}


	/**
	 * @param uploadTime the uploadTime to set
	 */
	public void setUploadTime(Date uploadTime) {
		this.uploadTime = uploadTime;
	}


	/**
	 * @return the nicJobCompleteFlag
	 */
	public Boolean getNicJobCompleteFlag() {
		return nicJobCompleteFlag;
	}


	/**
	 * @param nicJobCompleteFlag the nicJobCompleteFlag to set
	 */
	public void setNicJobCompleteFlag(Boolean nicJobCompleteFlag) {
		this.nicJobCompleteFlag = nicJobCompleteFlag;
	}


	/**
	 * @return the fullAmputatedFlag
	 */
	public Boolean getFullAmputatedFlag() {
		return fullAmputatedFlag;
	}


	/**
	 * @param fullAmputatedFlag the fullAmputatedFlag to set
	 */
	public void setFullAmputatedFlag(Boolean fullAmputatedFlag) {
		this.fullAmputatedFlag = fullAmputatedFlag;
	}


	/**
	 * @return the partialAmputatedFlag
	 */
	public Boolean getPartialAmputatedFlag() {
		return partialAmputatedFlag;
	}


	/**
	 * @param partialAmputatedFlag the partialAmputatedFlag to set
	 */
	public void setPartialAmputatedFlag(Boolean partialAmputatedFlag) {
		this.partialAmputatedFlag = partialAmputatedFlag;
	}


	/**
	 * @return the supervisorDecision
	 */
	public String getSupervisorDecision() {
		return supervisorDecision;
	}


	/**
	 * @param supervisorDecision the supervisorDecision to set
	 */
	public void setSupervisorDecision(String supervisorDecision) {
		this.supervisorDecision = supervisorDecision;
	}


	/**
	 * @return the createBy
	 */
	public String getCreateBy() {
		return createBy;
	}


	/**
	 * @param createBy the createBy to set
	 */
	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}


	/**
	 * @return the createWkstnId
	 */
	public String getCreateWkstnId() {
		return createWkstnId;
	}


	/**
	 * @param createWkstnId the createWkstnId to set
	 */
	public void setCreateWkstnId(String createWkstnId) {
		this.createWkstnId = createWkstnId;
	}


	/**
	 * @return the createDate
	 */
	public Date getCreateDate() {
		return createDate;
	}


	/**
	 * @param createDate the createDate to set
	 */
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}


	/**
	 * @return the updateBy
	 */
	public String getUpdateBy() {
		return updateBy;
	}


	/**
	 * @param updateBy the updateBy to set
	 */
	public void setUpdateBy(String updateBy) {
		this.updateBy = updateBy;
	}


	/**
	 * @return the updateWkstnId
	 */
	public String getUpdateWkstnId() {
		return updateWkstnId;
	}


	/**
	 * @param updateWkstnId the updateWkstnId to set
	 */
	public void setUpdateWkstnId(String updateWkstnId) {
		this.updateWkstnId = updateWkstnId;
	}


	/**
	 * @return the updateDate
	 */
	public Date getUpdateDate() {
		return updateDate;
	}


	/**
	 * @param updateDate the updateDate to set
	 */
	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}


	/**
	 * @return the surnameLine1
	 */
	public String getSurnameLine1() {
		return surnameLine1;
	}


	/**
	 * @param surnameLine1 the surnameLine1 to set
	 */
	public void setSurnameLine1(String surnameLine1) {
		this.surnameLine1 = surnameLine1;
	}


	/**
	 * @return the surnameLine2
	 */
	public String getSurnameLine2() {
		return surnameLine2;
	}


	/**
	 * @param surnameLine2 the surnameLine2 to set
	 */
	public void setSurnameLine2(String surnameLine2) {
		this.surnameLine2 = surnameLine2;
	}


	/**
	 * @return the surnameLenExceedFlag
	 */
	public Boolean getSurnameLenExceedFlag() {
		return surnameLenExceedFlag;
	}


	/**
	 * @param surnameLenExceedFlag the surnameLenExceedFlag to set
	 */
	public void setSurnameLenExceedFlag(Boolean surnameLenExceedFlag) {
		this.surnameLenExceedFlag = surnameLenExceedFlag;
	}


	/**
	 * @return the firstnameLine1
	 */
	public String getFirstnameLine1() {
		return firstnameLine1;
	}


	/**
	 * @param firstnameLine1 the firstnameLine1 to set
	 */
	public void setFirstnameLine1(String firstnameLine1) {
		this.firstnameLine1 = firstnameLine1;
	}


	/**
	 * @return the firstnameLine2
	 */
	public String getFirstnameLine2() {
		return firstnameLine2;
	}


	/**
	 * @param firstnameLine2 the firstnameLine2 to set
	 */
	public void setFirstnameLine2(String firstnameLine2) {
		this.firstnameLine2 = firstnameLine2;
	}


	/**
	 * @return the firstnameLenExceedFlag
	 */
	public Boolean getFirstnameLenExceedFlag() {
		return firstnameLenExceedFlag;
	}


	/**
	 * @param firstnameLenExceedFlag the firstnameLenExceedFlag to set
	 */
	public void setFirstnameLenExceedFlag(Boolean firstnameLenExceedFlag) {
		this.firstnameLenExceedFlag = firstnameLenExceedFlag;
	}


	/**
	 * @return the surnameBirthLine1
	 */
	public String getSurnameBirthLine1() {
		return surnameBirthLine1;
	}


	/**
	 * @param surnameBirthLine1 the surnameBirthLine1 to set
	 */
	public void setSurnameBirthLine1(String surnameBirthLine1) {
		this.surnameBirthLine1 = surnameBirthLine1;
	}


	/**
	 * @return the surnameBirthLine2
	 */
	public String getSurnameBirthLine2() {
		return surnameBirthLine2;
	}


	/**
	 * @param surnameBirthLine2 the surnameBirthLine2 to set
	 */
	public void setSurnameBirthLine2(String surnameBirthLine2) {
		this.surnameBirthLine2 = surnameBirthLine2;
	}


	/**
	 * @return the surnameBirthLenExceedFlag
	 */
	public Boolean getSurnameBirthLenExceedFlag() {
		return surnameBirthLenExceedFlag;
	}


	/**
	 * @param surnameBirthLenExceedFlag the surnameBirthLenExceedFlag to set
	 */
	public void setSurnameBirthLenExceedFlag(Boolean surnameBirthLenExceedFlag) {
		this.surnameBirthLenExceedFlag = surnameBirthLenExceedFlag;
	}


	/**
	 * @return the optionSurname
	 */
	public String getOptionSurname() {
		return optionSurname;
	}


	/**
	 * @param optionSurname the optionSurname to set
	 */
	public void setOptionSurname(String optionSurname) {
		this.optionSurname = optionSurname;
	}


	/**
	 * @return the dateOfBirthDisp
	 */
	public String getDateOfBirthDisp() {
		return dateOfBirthDisp;
	}


	/**
	 * @param dateOfBirthDisp the dateOfBirthDisp to set
	 */
	public void setDateOfBirthDisp(String dateOfBirthDisp) {
		this.dateOfBirthDisp = dateOfBirthDisp;
	}


	/**
	 * @return the approxDob
	 */
	public String getApproxDob() {
		return approxDob;
	}


	/**
	 * @param approxDob the approxDob to set
	 */
	public void setApproxDob(String approxDob) {
		this.approxDob = approxDob;
	}


	/**
	 * @return the printDob
	 */
	public String getPrintDob() {
		return printDob;
	}


	/**
	 * @param printDob the printDob to set
	 */
	public void setPrintDob(String printDob) {
		this.printDob = printDob;
	}


	/**
	 * @return the maritalNapoleanCodeFlag
	 */
	public Boolean getMaritalNapoleanCodeFlag() {
		return maritalNapoleanCodeFlag;
	}


	/**
	 * @param maritalNapoleanCodeFlag the maritalNapoleanCodeFlag to set
	 */
	public void setMaritalNapoleanCodeFlag(Boolean maritalNapoleanCodeFlag) {
		this.maritalNapoleanCodeFlag = maritalNapoleanCodeFlag;
	}


	/**
	 * @return the overseasAddress
	 */
	public String getOverseasAddress() {
		return overseasAddress;
	}


	/**
	 * @param overseasAddress the overseasAddress to set
	 */
	public void setOverseasAddress(String overseasAddress) {
		this.overseasAddress = overseasAddress;
	}


	/**
	 * @return the overseasCountry
	 */
	public String getOverseasCountry() {
		return overseasCountry;
	}


	/**
	 * @param overseasCountry the overseasCountry to set
	 */
	public void setOverseasCountry(String overseasCountry) {
		this.overseasCountry = overseasCountry;
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
	 * @return the addressUpdatedFlag
	 */
	public Boolean getAddressUpdatedFlag() {
		return addressUpdatedFlag;
	}


	/**
	 * @param addressUpdatedFlag the addressUpdatedFlag to set
	 */
	public void setAddressUpdatedFlag(Boolean addressUpdatedFlag) {
		this.addressUpdatedFlag = addressUpdatedFlag;
	}


	/**
	 * @return the addressUpdatedOfficerId
	 */
	public String getAddressUpdatedOfficerId() {
		return addressUpdatedOfficerId;
	}


	/**
	 * @param addressUpdatedOfficerId the addressUpdatedOfficerId to set
	 */
	public void setAddressUpdatedOfficerId(String addressUpdatedOfficerId) {
		this.addressUpdatedOfficerId = addressUpdatedOfficerId;
	}


	/**
	 * @return the addressUpdatedWkstnId
	 */
	public String getAddressUpdatedWkstnId() {
		return addressUpdatedWkstnId;
	}


	/**
	 * @param addressUpdatedWkstnId the addressUpdatedWkstnId to set
	 */
	public void setAddressUpdatedWkstnId(String addressUpdatedWkstnId) {
		this.addressUpdatedWkstnId = addressUpdatedWkstnId;
	}


	/**
	 * @return the addressUpdatedTime
	 */
	public Date getAddressUpdatedTime() {
		return addressUpdatedTime;
	}


	/**
	 * @param addressUpdatedTime the addressUpdatedTime to set
	 */
	public void setAddressUpdatedTime(Date addressUpdatedTime) {
		this.addressUpdatedTime = addressUpdatedTime;
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


	/**
	 * @return the verDefaultFp
	 */
	public Long getVerDefaultFp() {
		return verDefaultFp;
	}


	/**
	 * @param verDefaultFp the verDefaultFp to set
	 */
	public void setVerDefaultFp(Long verDefaultFp) {
		this.verDefaultFp = verDefaultFp;
	}


	/**
	 * @return the fpBypassDecision
	 */
	public String getFpBypassDecision() {
		return fpBypassDecision;
	}


	/**
	 * @param fpBypassDecision the fpBypassDecision to set
	 */
	public void setFpBypassDecision(String fpBypassDecision) {
		this.fpBypassDecision = fpBypassDecision;
	}


	/**
	 * @return the fpBypassOfficerId
	 */
	public String getFpBypassOfficerId() {
		return fpBypassOfficerId;
	}


	/**
	 * @param fpBypassOfficerId the fpBypassOfficerId to set
	 */
	public void setFpBypassOfficerId(String fpBypassOfficerId) {
		this.fpBypassOfficerId = fpBypassOfficerId;
	}


	/**
	 * @return the fpBypassTime
	 */
	public Date getFpBypassTime() {
		return fpBypassTime;
	}


	/**
	 * @param fpBypassTime the fpBypassTime to set
	 */
	public void setFpBypassTime(Date fpBypassTime) {
		this.fpBypassTime = fpBypassTime;
	}


	/**
	 * @return the totalFp
	 */
	public Integer getTotalFp() {
		return totalFp;
	}


	/**
	 * @param totalFp the totalFp to set
	 */
	public void setTotalFp(Integer totalFp) {
		this.totalFp = totalFp;
	}


	/**
	 * @return the fpIndicator
	 */
	public String getFpIndicator() {
		return fpIndicator;
	}


	/**
	 * @param fpIndicator the fpIndicator to set
	 */
	public void setFpIndicator(String fpIndicator) {
		this.fpIndicator = fpIndicator;
	}


	/**
	 * @return the fpQuality
	 */
	public String getFpQuality() {
		return fpQuality;
	}


	/**
	 * @param fpQuality the fpQuality to set
	 */
	public void setFpQuality(String fpQuality) {
		this.fpQuality = fpQuality;
	}


	/**
	 * @return the fpEncode
	 */
	public String getFpEncode() {
		return fpEncode;
	}


	/**
	 * @param fpEncode the fpEncode to set
	 */
	public void setFpEncode(String fpEncode) {
		this.fpEncode = fpEncode;
	}


	/**
	 * @return the fpVerifyScore
	 */
	public String getFpVerifyScore() {
		return fpVerifyScore;
	}


	/**
	 * @param fpVerifyScore the fpVerifyScore to set
	 */
	public void setFpVerifyScore(String fpVerifyScore) {
		this.fpVerifyScore = fpVerifyScore;
	}


	/**
	 * @return the signatureIndicator
	 */
	public String getSignatureIndicator() {
		return signatureIndicator;
	}


	/**
	 * @param signatureIndicator the signatureIndicator to set
	 */
	public void setSignatureIndicator(String signatureIndicator) {
		this.signatureIndicator = signatureIndicator;
	}


	/**
	 * @return the signatureFp
	 */
	public String getSignatureFp() {
		return signatureFp;
	}


	/**
	 * @param signatureFp the signatureFp to set
	 */
	public void setSignatureFp(String signatureFp) {
		this.signatureFp = signatureFp;
	}


	/**
	 * @return the facialIndicator
	 */
	public String getFacialIndicator() {
		return facialIndicator;
	}


	/**
	 * @param facialIndicator the facialIndicator to set
	 */
	public void setFacialIndicator(String facialIndicator) {
		this.facialIndicator = facialIndicator;
	}


	/**
	 * @return the seniorCitizenFlag
	 */
	public Boolean getSeniorCitizenFlag() {
		return seniorCitizenFlag;
	}


	/**
	 * @param seniorCitizenFlag the seniorCitizenFlag to set
	 */
	public void setSeniorCitizenFlag(Boolean seniorCitizenFlag) {
		this.seniorCitizenFlag = seniorCitizenFlag;
	}


	/**
	 * @return the fpVerifyFlag
	 */
	public Boolean getFpVerifyFlag() {
		return fpVerifyFlag;
	}


	/**
	 * @param fpVerifyFlag the fpVerifyFlag to set
	 */
	public void setFpVerifyFlag(Boolean fpVerifyFlag) {
		this.fpVerifyFlag = fpVerifyFlag;
	}


	/**
	 * @return the cardVerifyFlag
	 */
	public Boolean getCardVerifyFlag() {
		return cardVerifyFlag;
	}


	/**
	 * @param cardVerifyFlag the cardVerifyFlag to set
	 */
	public void setCardVerifyFlag(Boolean cardVerifyFlag) {
		this.cardVerifyFlag = cardVerifyFlag;
	}


	/**
	 * @return the expressFlag
	 */
	public Boolean getExpressFlag() {
		return expressFlag;
	}


	/**
	 * @param expressFlag the expressFlag to set
	 */
	public void setExpressFlag(Boolean expressFlag) {
		this.expressFlag = expressFlag;
	}


	/**
	 * @return the conversionFlag
	 */
	public Boolean getConversionFlag() {
		return conversionFlag;
	}


	/**
	 * @param conversionFlag the conversionFlag to set
	 */
	public void setConversionFlag(Boolean conversionFlag) {
		this.conversionFlag = conversionFlag;
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
	 * @return the contactNo
	 */
	public String getContactNo() {
		return contactNo;
	}


	/**
	 * @param contactNo the contactNo to set
	 */
	public void setContactNo(String contactNo) {
		this.contactNo = contactNo;
	}


	/**
	 * @return the nicTransaction
	 */
	public NicTransactionDto getNicTransaction() {
		return nicTransaction;
	}


	/**
	 * @param nicTransaction the nicTransaction to set
	 */
	public void setNicTransaction(NicTransactionDto nicTransaction) {
		this.nicTransaction = nicTransaction;
	}


	/**
	 * @return the dateFrom
	 */
	public String getDateFrom() {
		return dateFrom;
	}


	/**
	 * @param dateFrom the dateFrom to set
	 */
	public void setDateFrom(String dateFrom) {
		this.dateFrom = dateFrom;
	}


	/**
	 * @return the dateTo
	 */
	public String getDateTo() {
		return dateTo;
	}


	/**
	 * @param dateTo the dateTo to set
	 */
	public void setDateTo(String dateTo) {
		this.dateTo = dateTo;
	}

	private String dateTo;
	
	
	public NicRegistrationDataDto(){}
	
	
	public NicRegistrationDataDto(NicRegistrationData regDto){
//		this . nin=regDto.getNin();
		this . surnameFull=regDto.getSurnameFull();
		this . firstnameFull=regDto.getFirstnameFull();
//		this . surnameBirthFull=regDto.getSurnameBirthFull();
		this.dateOfBirth=regDto.getDateOfBirth();
		this . gender=regDto.getGender();
		this . maritalStatus=regDto.getMaritalStatus();
		this . addressLine1=regDto.getAddressLine1();
		this . addressLine2=regDto.getAddressLine2();
		this . addressLine3=regDto.getAddressLine3();
		this . addressLine4=regDto.getAddressLine4();
//		this . addressLine5=regDto.getAddressLine5();
//		this . addressLine6=regDto.getAddressLine6();
//		this . preferredMailingAddress=regDto.getPreferredMailingAddress();
		this . fatherName=regDto.getFatherFullname();
		this . motherName=regDto.getMotherFullname();
		
	}

	/**
	 * @return the nin
	 */
	public String getNin() {
		return nin;
	}

	/**
	 * @param nin the nin to set
	 */
	public void setNin(String nin) {
		this.nin = nin;
	}

	/**
	 * @return the surnameFull
	 */
	public String getSurnameFull() {
		return surnameFull;
	}

	/**
	 * @param surnameFull the surnameFull to set
	 */
	public void setSurnameFull(String surnameFull) {
		this.surnameFull = surnameFull;
	}

	/**
	 * @return the firstnameFull
	 */
	public String getFirstnameFull() {
		return firstnameFull;
	}

	/**
	 * @param firstnameFull the firstnameFull to set
	 */
	public void setFirstnameFull(String firstnameFull) {
		this.firstnameFull = firstnameFull;
	}

	/**
	 * @return the surnameBirthFull
	 */
	public String getSurnameBirthFull() {
		return surnameBirthFull;
	}

	/**
	 * @param surnameBirthFull the surnameBirthFull to set
	 */
	public void setSurnameBirthFull(String surnameBirthFull) {
		this.surnameBirthFull = surnameBirthFull;
	}

	/**
	 * @return the dateOfBirth
	 */
	public Date getDateOfBirth() {
		return dateOfBirth;
	}

	/**
	 * @param dateOfBirth the dateOfBirth to set
	 */
	public void setDateOfBirth(Date dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	/**
	 * @return the gender
	 */
	public String getGender() {
		return gender;
	}

	/**
	 * @param gender the gender to set
	 */
	public void setGender(String gender) {
		this.gender = gender;
	}

	/**
	 * @return the maritalStatus
	 */
	public String getMaritalStatus() {
		return maritalStatus;
	}

	/**
	 * @param maritalStatus the maritalStatus to set
	 */
	public void setMaritalStatus(String maritalStatus) {
		this.maritalStatus = maritalStatus;
	}

	/**
	 * @return the addressLine1
	 */
	public String getAddressLine1() {
		return addressLine1;
	}

	/**
	 * @param addressLine1 the addressLine1 to set
	 */
	public void setAddressLine1(String addressLine1) {
		this.addressLine1 = addressLine1;
	}

	/**
	 * @return the addressLine2
	 */
	public String getAddressLine2() {
		return addressLine2;
	}

	/**
	 * @param addressLine2 the addressLine2 to set
	 */
	public void setAddressLine2(String addressLine2) {
		this.addressLine2 = addressLine2;
	}

	/**
	 * @return the addressLine3
	 */
	public String getAddressLine3() {
		return addressLine3;
	}

	/**
	 * @param addressLine3 the addressLine3 to set
	 */
	public void setAddressLine3(String addressLine3) {
		this.addressLine3 = addressLine3;
	}

	/**
	 * @return the addressLine4
	 */
	public String getAddressLine4() {
		return addressLine4;
	}

	/**
	 * @param addressLine4 the addressLine4 to set
	 */
	public void setAddressLine4(String addressLine4) {
		this.addressLine4 = addressLine4;
	}

	/**
	 * @return the addressLine5
	 */
	public String getAddressLine5() {
		return addressLine5;
	}

	/**
	 * @param addressLine5 the addressLine5 to set
	 */
	public void setAddressLine5(String addressLine5) {
		this.addressLine5 = addressLine5;
	}

	/**
	 * @return the addressLine6
	 */
	public String getAddressLine6() {
		return addressLine6;
	}

	/**
	 * @param addressLine6 the addressLine6 to set
	 */
	public void setAddressLine6(String addressLine6) {
		this.addressLine6 = addressLine6;
	}

	/**
	 * @return the preferredMailingAddress
	 */
	public String getPreferredMailingAddress() {
		return preferredMailingAddress;
	}

	/**
	 * @param preferredMailingAddress the preferredMailingAddress to set
	 */
	public void setPreferredMailingAddress(String preferredMailingAddress) {
		this.preferredMailingAddress = preferredMailingAddress;
	}

	/**
	 * @return the fatherName
	 */
	public String getFatherName() {
		return fatherName;
	}

	/**
	 * @param fatherName the fatherName to set
	 */
	public void setFatherName(String fatherName) {
		this.fatherName = fatherName;
	}

	/**
	 * @return the fatherSurname
	 */
	public String getFatherSurname() {
		return fatherSurname;
	}

	/**
	 * @param fatherSurname the fatherSurname to set
	 */
	public void setFatherSurname(String fatherSurname) {
		this.fatherSurname = fatherSurname;
	}

	/**
	 * @return the motherName
	 */
	public String getMotherName() {
		return motherName;
	}

	/**
	 * @param motherName the motherName to set
	 */
	public void setMotherName(String motherName) {
		this.motherName = motherName;
	}

	/**
	 * @return the motherSurname
	 */
	public String getMotherSurname() {
		return motherSurname;
	}

	/**
	 * @param motherSurname the motherSurname to set
	 */
	public void setMotherSurname(String motherSurname) {
		this.motherSurname = motherSurname;
	}

	/**
	 * @return the cpdNin
	 */
	public String getCpdNin() {
		return cpdNin;
	}

	/**
	 * @param cpdNin the cpdNin to set
	 */
	public void setCpdNin(String cpdNin) {
		this.cpdNin = cpdNin;
	}

	/**
	 * @return the cpdSurnameFull
	 */
	public String getCpdSurnameFull() {
		return cpdSurnameFull;
	}

	/**
	 * @param cpdSurnameFull the cpdSurnameFull to set
	 */
	public void setCpdSurnameFull(String cpdSurnameFull) {
		this.cpdSurnameFull = cpdSurnameFull;
	}

	/**
	 * @return the cpdFirstnameFull
	 */
	public String getCpdFirstnameFull() {
		return cpdFirstnameFull;
	}

	/**
	 * @param cpdFirstnameFull the cpdFirstnameFull to set
	 */
	public void setCpdFirstnameFull(String cpdFirstnameFull) {
		this.cpdFirstnameFull = cpdFirstnameFull;
	}

	/**
	 * @return the cpdSurnameBirthFull
	 */
	public String getCpdSurnameBirthFull() {
		return cpdSurnameBirthFull;
	}

	/**
	 * @param cpdSurnameBirthFull the cpdSurnameBirthFull to set
	 */
	public void setCpdSurnameBirthFull(String cpdSurnameBirthFull) {
		this.cpdSurnameBirthFull = cpdSurnameBirthFull;
	}

	/**
	 * @return the cpdDateOfBirth
	 */
	public Date getCpdDateOfBirth() {
		return cpdDateOfBirth;
	}

	/**
	 * @param cpdDateOfBirth the cpdDateOfBirth to set
	 */
	public void setCpdDateOfBirth(Date cpdDateOfBirth) {
		this.cpdDateOfBirth = cpdDateOfBirth;
	}

	/**
	 * @return the cpdGender
	 */
	public String getCpdGender() {
		return cpdGender;
	}

	/**
	 * @param cpdGender the cpdGender to set
	 */
	public void setCpdGender(String cpdGender) {
		this.cpdGender = cpdGender;
	}

	/**
	 * @return the cpdMaritalStatus
	 */
	public String getCpdMaritalStatus() {
		return cpdMaritalStatus;
	}

	/**
	 * @param cpdMaritalStatus the cpdMaritalStatus to set
	 */
	public void setCpdMaritalStatus(String cpdMaritalStatus) {
		this.cpdMaritalStatus = cpdMaritalStatus;
	}

	/**
	 * @return the cpdAddressLine1
	 */
	public String getCpdAddressLine1() {
		return cpdAddressLine1;
	}

	/**
	 * @param cpdAddressLine1 the cpdAddressLine1 to set
	 */
	public void setCpdAddressLine1(String cpdAddressLine1) {
		this.cpdAddressLine1 = cpdAddressLine1;
	}

	/**
	 * @return the cpdAddressLine2
	 */
	public String getCpdAddressLine2() {
		return cpdAddressLine2;
	}

	/**
	 * @param cpdAddressLine2 the cpdAddressLine2 to set
	 */
	public void setCpdAddressLine2(String cpdAddressLine2) {
		this.cpdAddressLine2 = cpdAddressLine2;
	}

	/**
	 * @return the cpdAddressLine3
	 */
	public String getCpdAddressLine3() {
		return cpdAddressLine3;
	}

	/**
	 * @param cpdAddressLine3 the cpdAddressLine3 to set
	 */
	public void setCpdAddressLine3(String cpdAddressLine3) {
		this.cpdAddressLine3 = cpdAddressLine3;
	}

	/**
	 * @return the capdAddressLine4
	 */
	public String getCapdAddressLine4() {
		return capdAddressLine4;
	}

	/**
	 * @param capdAddressLine4 the capdAddressLine4 to set
	 */
	public void setCapdAddressLine4(String capdAddressLine4) {
		this.capdAddressLine4 = capdAddressLine4;
	}

	/**
	 * @return the cpdAddressLine5
	 */
	public String getCpdAddressLine5() {
		return cpdAddressLine5;
	}

	/**
	 * @param cpdAddressLine5 the cpdAddressLine5 to set
	 */
	public void setCpdAddressLine5(String cpdAddressLine5) {
		this.cpdAddressLine5 = cpdAddressLine5;
	}

	/**
	 * @return the cpdAddressLine6
	 */
	public String getCpdAddressLine6() {
		return cpdAddressLine6;
	}

	/**
	 * @param cpdAddressLine6 the cpdAddressLine6 to set
	 */
	public void setCpdAddressLine6(String cpdAddressLine6) {
		this.cpdAddressLine6 = cpdAddressLine6;
	}

	/**
	 * @return the cpdPreferredMailingAddress
	 */
	public String getCpdPreferredMailingAddress() {
		return cpdPreferredMailingAddress;
	}

	/**
	 * @param cpdPreferredMailingAddress the cpdPreferredMailingAddress to set
	 */
	public void setCpdPreferredMailingAddress(String cpdPreferredMailingAddress) {
		this.cpdPreferredMailingAddress = cpdPreferredMailingAddress;
	}

	/**
	 * @return the cpdFatherName
	 */
	public String getCpdFatherName() {
		return cpdFatherName;
	}

	/**
	 * @param cpdFatherName the cpdFatherName to set
	 */
	public void setCpdFatherName(String cpdFatherName) {
		this.cpdFatherName = cpdFatherName;
	}

	/**
	 * @return the cpdFatherSurname
	 */
	public String getCpdFatherSurname() {
		return cpdFatherSurname;
	}

	/**
	 * @param cpdFatherSurname the cpdFatherSurname to set
	 */
	public void setCpdFatherSurname(String cpdFatherSurname) {
		this.cpdFatherSurname = cpdFatherSurname;
	}

	/**
	 * @return the cpdMotherName
	 */
	public String getCpdMotherName() {
		return cpdMotherName;
	}

	/**
	 * @param cpdMotherName the cpdMotherName to set
	 */
	public void setCpdMotherName(String cpdMotherName) {
		this.cpdMotherName = cpdMotherName;
	}

	/**
	 * @return the cpdMotherSurname
	 */
	public String getCpdMotherSurname() {
		return cpdMotherSurname;
	}

	/**
	 * @param cpdMotherSurname the cpdMotherSurname to set
	 */
	public void setCpdMotherSurname(String cpdMotherSurname) {
		this.cpdMotherSurname = cpdMotherSurname;
	}
	

}
