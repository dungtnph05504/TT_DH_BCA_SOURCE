/**
 * 
 */
package com.nec.asia.nic.common.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.validation.constraints.Pattern;

import org.apache.commons.collections.FactoryUtils;
import org.apache.commons.collections.list.LazyList;

/**
 * @author aparna_sharma
 *
 */

public class RicNewRegistrationDTO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String transactionTypeDesc;
	private String transactionSubTypeDesc;
	private String transactionStageDesc;
	private String transactionStatusDesc;
	private String transactionId;
	private String transactionType;
	private String transactionSubType;
	private String trnasactionStartDate;
	private String transactionStatus;
	private String nin;
	private NinDetailsDTO person;
	private boolean addressChangeFlag;
	private String oldAddress1;
	private String oldAddress2;
	private String oldAddress3;
	private String oldAddress4;
	private String oldAddress5;
	private String oldAddress6;
	private String oldOverseasAddress;
	private String oldOverseasAddressCountry;
	private String oldPreferredMailingAddress;
	private String locality;
	private String town;
	private String district;
	private String townDisp;
	private String districtDisp;
	private String overseasCountry;
	private List<RicRegistrationDocumentDTO> documentList;
	private RicRegistrationFingerPrintDTO fingerPrint01;
	private RicRegistrationFingerPrintDTO fingerPrint02;
	private RicRegistrationFingerPrintDTO fingerPrint03;
	private RicRegistrationFingerPrintDTO fingerPrint04;
	private RicRegistrationFingerPrintDTO fingerPrint05;
	private RicRegistrationFingerPrintDTO fingerPrint06;
	private RicRegistrationFingerPrintDTO fingerPrint07;
	private RicRegistrationFingerPrintDTO fingerPrint08;
	private RicRegistrationFingerPrintDTO fingerPrint09;
	private RicRegistrationFingerPrintDTO fingerPrint10;
	private RicRegistrationFingerPrintDTO verifyFingerPrint01;
	private RicRegistrationFingerPrintDTO verifyFingerPrint02;
	private RicRegistrationFingerPrintDTO verifyFingerPrint03;
	private RicRegistrationFingerPrintDTO verifyFingerPrint04;
	private RicRegistrationFingerPrintDTO verifyFingerPrint05;
	private RicRegistrationFingerPrintDTO verifyFingerPrint06;
	private RicRegistrationFingerPrintDTO verifyFingerPrint07;
	private RicRegistrationFingerPrintDTO verifyFingerPrint08;
	private RicRegistrationFingerPrintDTO verifyFingerPrint09;
	private RicRegistrationFingerPrintDTO verifyFingerPrint10;
	private RicRegistrationFacialDTO encodedFace;
	private RicRegistrationFacialDTO enrolledFace;
	private RicRegistrationFacialDTO printedFace;
	private RicRegistrationFacialDTO thumbNailFace;
	private RicRegistrationFacialDTO thumbNailEncodeFace;
	private RicRegistrationFacialDTO thumbNailPrintedFace;
	private RicRegistrationFacialDTO smallPortraitCliFace;
	private String facialOriginalImage;
	private List<RicTransactionLogDTO> transactionLogList;
	private List<RicTransactionLogDTO> ricTransactionLogList;
	private String updatedBy;
	private String updateTime;
	private RicTransactionLogDTO  transLog; 	
	private RicTransactionLogDTO  newRegistrationLog; 
	private String transactionStage;
	private String fpEncode;
	private String fpVerifyScore;
	@Pattern(regexp="^$|^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$",message="Not A Valid Email")
	private String email;
	@Pattern(regexp="^$|\\d{3}-\\d{7,16}",message="Phone Number must be in the form XXX-XXXXXXX")
	private String phone;
	private String issueSite;
	private String sign;
	private String signFp;
	private boolean useFpForSignYN;
	//for supervisor
	private String decisionType;
	private String supervisorDecision;
	private String supervisorRemarks;
	private String fpIndicator;
	private String fpQuality;
	private boolean comingFromRegistration;
	private boolean comingFromVerification;
	private RicRegistrationDocumentDTO userDeclarationDoc;
	private String supervisorCancelRemarks;
	
	private String counterId;
	private String queueNumber;
	
	//for update particulars
	private String defaultFp;
	private boolean fpVerified;
	private boolean cardVerified;
	private String nicFace;
	private String nicSignature;
	private String nicFpEncode;
	private Map<String,String> defaultFpMap;
	private boolean comingFromTransQuery;
	
	private String minutiaHeader;
	private String signingUrl;
	private String fpQualityGood;
	private String fpQualityAcceptable;
	private int fpVerifyMatchScore;
	private Map<String,String> fpQualityGoodThreshold;
	private Map<String,String> fpQualityAcceptableThreshold;
	
	private PaymentDTO payment;
	private String oldSurname;
	private String regSiteCode; 
	private String ccn;
	private String cardStatus;
	

//EUNIKE START 20130719
	private int noCardLost;
	private boolean seniorCitizenFlag;
	//EUNIKE END

	
	public String getTransactionId() {
		return transactionId;
	}
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public RicNewRegistrationDTO() {
		documentList= LazyList.decorate(new ArrayList(),  
			       FactoryUtils.instantiateFactory(RicRegistrationDocumentDTO.class));  
	}
	
	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}
	public String getTransactionType() {
		return transactionType;
	}
	public void setTransactionType(String transactionType) {
		this.transactionType = transactionType;
	}
	public String getTransactionSubType() {
		return transactionSubType;
	}
	public void setTransactionSubType(String transactionSubType) {
		this.transactionSubType = transactionSubType;
	}
	public String getTrnasactionStartDate() {
		return trnasactionStartDate;
	}
	public void setTrnasactionStartDate(String trnasactionStartDate) {
		this.trnasactionStartDate = trnasactionStartDate;
	}
	public String getTransactionStatus() {
		return transactionStatus;
	}
	public void setTransactionStatus(String transactionStatus) {
		this.transactionStatus = transactionStatus;
	}
	public String getNin() {
		return nin;
	}
	public void setNin(String nin) {
		this.nin = nin;
	}
	public NinDetailsDTO getPerson() {
		return person;
	}
	public void setPerson(NinDetailsDTO person) {
		this.person = person;
	}
	public boolean isAddressChangeFlag() {
		return addressChangeFlag;
	}
	public void setAddressChangeFlag(boolean addressChangeFlag) {
		this.addressChangeFlag = addressChangeFlag;
	}
	public String getOldAddress1() {
		return oldAddress1;
	}
	public void setOldAddress1(String oldAddress1) {
		this.oldAddress1 = oldAddress1;
	}
	public String getOldAddress2() {
		return oldAddress2;
	}
	public void setOldAddress2(String oldAddress2) {
		this.oldAddress2 = oldAddress2;
	}
	public String getOldAddress3() {
		return oldAddress3;
	}
	public void setOldAddress3(String oldAddress3) {
		this.oldAddress3 = oldAddress3;
	}
	public String getOldAddress4() {
		return oldAddress4;
	}
	public void setOldAddress4(String oldAddress4) {
		this.oldAddress4 = oldAddress4;
	}
	public List<RicRegistrationDocumentDTO> getDocumentList() {
		return documentList;
	}
	public void setDocumentList(List<RicRegistrationDocumentDTO> documentList) {
		this.documentList = documentList;
	}
	public RicRegistrationFingerPrintDTO getFingerPrint01() {
		return fingerPrint01;
	}
	public void setFingerPrint01(RicRegistrationFingerPrintDTO fingerPrint01) {
		this.fingerPrint01 = fingerPrint01;
	}
	public RicRegistrationFingerPrintDTO getFingerPrint02() {
		return fingerPrint02;
	}
	public void setFingerPrint02(RicRegistrationFingerPrintDTO fingerPrint02) {
		this.fingerPrint02 = fingerPrint02;
	}
	public RicRegistrationFingerPrintDTO getFingerPrint03() {
		return fingerPrint03;
	}
	public void setFingerPrint03(RicRegistrationFingerPrintDTO fingerPrint03) {
		this.fingerPrint03 = fingerPrint03;
	}
	public RicRegistrationFingerPrintDTO getFingerPrint04() {
		return fingerPrint04;
	}
	public void setFingerPrint04(RicRegistrationFingerPrintDTO fingerPrint04) {
		this.fingerPrint04 = fingerPrint04;
	}
	public RicRegistrationFingerPrintDTO getFingerPrint05() {
		return fingerPrint05;
	}
	public void setFingerPrint05(RicRegistrationFingerPrintDTO fingerPrint05) {
		this.fingerPrint05 = fingerPrint05;
	}
	public RicRegistrationFingerPrintDTO getFingerPrint06() {
		return fingerPrint06;
	}
	public void setFingerPrint06(RicRegistrationFingerPrintDTO fingerPrint06) {
		this.fingerPrint06 = fingerPrint06;
	}
	public RicRegistrationFingerPrintDTO getFingerPrint07() {
		return fingerPrint07;
	}
	public void setFingerPrint07(RicRegistrationFingerPrintDTO fingerPrint07) {
		this.fingerPrint07 = fingerPrint07;
	}
	public RicRegistrationFingerPrintDTO getFingerPrint08() {
		return fingerPrint08;
	}
	public void setFingerPrint08(RicRegistrationFingerPrintDTO fingerPrint08) {
		this.fingerPrint08 = fingerPrint08;
	}
	public RicRegistrationFingerPrintDTO getFingerPrint09() {
		return fingerPrint09;
	}
	public void setFingerPrint09(RicRegistrationFingerPrintDTO fingerPrint09) {
		this.fingerPrint09 = fingerPrint09;
	}
	public RicRegistrationFingerPrintDTO getFingerPrint10() {
		return fingerPrint10;
	}
	public void setFingerPrint10(RicRegistrationFingerPrintDTO fingerPrint10) {
		this.fingerPrint10 = fingerPrint10;
	}
	
	public List<RicTransactionLogDTO> getTransactionLogList() {
		return transactionLogList;
	}
	public void setTransactionLogList(List<RicTransactionLogDTO> transactionLogList) {
		this.transactionLogList = transactionLogList;
	}
	public String getUpdatedBy() {
		return updatedBy;
	}
	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}
	public String getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
	}
	public RicRegistrationFacialDTO getEncodedFace() {
		return encodedFace;
	}
	public void setEncodedFace(RicRegistrationFacialDTO encodedFace) {
		this.encodedFace = encodedFace;
	}
	public RicRegistrationFacialDTO getEnrolledFace() {
		return enrolledFace;
	}
	public void setEnrolledFace(RicRegistrationFacialDTO enrolledFace) {
		this.enrolledFace = enrolledFace;
	}
	public RicRegistrationFacialDTO getPrintedFace() {
		return printedFace;
	}
	public void setPrintedFace(RicRegistrationFacialDTO printedFace) {
		this.printedFace = printedFace;
	}

	public RicRegistrationFacialDTO getThumbNailFace() {
		return thumbNailFace;
	}
	public void setThumbNailFace(RicRegistrationFacialDTO thumbNailFace) {
		this.thumbNailFace = thumbNailFace;
	}
	
	public RicTransactionLogDTO getNewRegistrationLog() {
		return newRegistrationLog;
	}
	public void setNewRegistrationLog(RicTransactionLogDTO newRegistrationLog) {
		this.newRegistrationLog = newRegistrationLog;
	}
	
	public RicTransactionLogDTO getTransLog() {
		return transLog;
	}
	public void setTransLog(RicTransactionLogDTO transLog) {
		this.transLog = transLog;
	}
	 
	public RicRegistrationFacialDTO getThumbNailEncodeFace() {
		return thumbNailEncodeFace;
	}
	public void setThumbNailEncodeFace(RicRegistrationFacialDTO thumbNailEncodeFace) {
		this.thumbNailEncodeFace = thumbNailEncodeFace;
	}
	public RicRegistrationFacialDTO getThumbNailPrintedFace() {
		return thumbNailPrintedFace;
	}
	public void setThumbNailPrintedFace(
			RicRegistrationFacialDTO thumbNailPrintedFace) {
		this.thumbNailPrintedFace = thumbNailPrintedFace;
	}
	public String getTransactionStage() {
		return transactionStage;
	}
	public void setTransactionStage(String transactionStage) {
		this.transactionStage = transactionStage;
	}
	public String getFpEncode() {
		return fpEncode;
	}
	public void setFpEncode(String fpEncode) {
		this.fpEncode = fpEncode;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getIssueSite() {
		return issueSite;
	}
	public void setIssueSite(String issueSite) {
		this.issueSite = issueSite;
	}
	public String getSign() {
		return sign;
	}
	public void setSign(String sign) {
		this.sign = sign;
	}
	public String getSignFp() {
		return signFp;
	}
	public void setSignFp(String signFp) {
		this.signFp = signFp;
	}	
	public String getSupervisorDecision() {
		return supervisorDecision;
	}
	public void setSupervisorDecision(String supervisorDecision) {
		this.supervisorDecision = supervisorDecision;
	}
	public String getSupervisorRemarks() {
		return supervisorRemarks;
	}
	public void setSupervisorRemarks(String supervisorRemarks) {
		this.supervisorRemarks = supervisorRemarks;
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
	public String getDecisionType() {
		return decisionType;
	}
	public void setDecisionType(String decisionType) {
		this.decisionType = decisionType;
	}
	public boolean isUseFpForSignYN() {
		return useFpForSignYN;
	}
	public void setUseFpForSignYN(boolean useFpForSignYN) {
		this.useFpForSignYN = useFpForSignYN;
	}
	public boolean isComingFromRegistration() {
		return comingFromRegistration;
	}
	public void setComingFromRegistration(boolean comingFromRegistration) {
		this.comingFromRegistration = comingFromRegistration;
	}
	public boolean isComingFromVerification() {
		return comingFromVerification;
	}
	public void setComingFromVerification(boolean comingFromVerification) {
		this.comingFromVerification = comingFromVerification;
	}
	public RicRegistrationFingerPrintDTO getVerifyFingerPrint01() {
		return verifyFingerPrint01;
	}
	public void setVerifyFingerPrint01(
			RicRegistrationFingerPrintDTO verifyFingerPrint01) {
		this.verifyFingerPrint01 = verifyFingerPrint01;
	}
	public RicRegistrationFingerPrintDTO getVerifyFingerPrint02() {
		return verifyFingerPrint02;
	}
	public void setVerifyFingerPrint02(
			RicRegistrationFingerPrintDTO verifyFingerPrint02) {
		this.verifyFingerPrint02 = verifyFingerPrint02;
	}
	public RicRegistrationFingerPrintDTO getVerifyFingerPrint03() {
		return verifyFingerPrint03;
	}
	public void setVerifyFingerPrint03(
			RicRegistrationFingerPrintDTO verifyFingerPrint03) {
		this.verifyFingerPrint03 = verifyFingerPrint03;
	}
	public RicRegistrationFingerPrintDTO getVerifyFingerPrint04() {
		return verifyFingerPrint04;
	}
	public void setVerifyFingerPrint04(
			RicRegistrationFingerPrintDTO verifyFingerPrint04) {
		this.verifyFingerPrint04 = verifyFingerPrint04;
	}
	public RicRegistrationFingerPrintDTO getVerifyFingerPrint05() {
		return verifyFingerPrint05;
	}
	public void setVerifyFingerPrint05(
			RicRegistrationFingerPrintDTO verifyFingerPrint05) {
		this.verifyFingerPrint05 = verifyFingerPrint05;
	}
	public RicRegistrationFingerPrintDTO getVerifyFingerPrint06() {
		return verifyFingerPrint06;
	}
	public void setVerifyFingerPrint06(
			RicRegistrationFingerPrintDTO verifyFingerPrint06) {
		this.verifyFingerPrint06 = verifyFingerPrint06;
	}
	public RicRegistrationFingerPrintDTO getVerifyFingerPrint07() {
		return verifyFingerPrint07;
	}
	public void setVerifyFingerPrint07(
			RicRegistrationFingerPrintDTO verifyFingerPrint07) {
		this.verifyFingerPrint07 = verifyFingerPrint07;
	}
	public RicRegistrationFingerPrintDTO getVerifyFingerPrint08() {
		return verifyFingerPrint08;
	}
	public void setVerifyFingerPrint08(
			RicRegistrationFingerPrintDTO verifyFingerPrint08) {
		this.verifyFingerPrint08 = verifyFingerPrint08;
	}
	public RicRegistrationFingerPrintDTO getVerifyFingerPrint09() {
		return verifyFingerPrint09;
	}
	public void setVerifyFingerPrint09(
			RicRegistrationFingerPrintDTO verifyFingerPrint09) {
		this.verifyFingerPrint09 = verifyFingerPrint09;
	}
	public RicRegistrationFingerPrintDTO getVerifyFingerPrint10() {
		return verifyFingerPrint10;
	}
	public void setVerifyFingerPrint10(
			RicRegistrationFingerPrintDTO verifyFingerPrint10) {
		this.verifyFingerPrint10 = verifyFingerPrint10;
	}
	public RicRegistrationDocumentDTO getUserDeclarationDoc() {
		return userDeclarationDoc;
	}
	public void setUserDeclarationDoc(RicRegistrationDocumentDTO userDeclarationDoc) {
		this.userDeclarationDoc = userDeclarationDoc;
	}
	public String getTransactionTypeDesc() {
		return transactionTypeDesc;
	}
	public void setTransactionTypeDesc(String transactionTypeDesc) {
		this.transactionTypeDesc = transactionTypeDesc;
	}
	public String getTransactionSubTypeDesc() {
		return transactionSubTypeDesc;
	}
	public void setTransactionSubTypeDesc(String transactionSubTypeDesc) {
		this.transactionSubTypeDesc = transactionSubTypeDesc;
	}
	public String getTransactionStageDesc() {
		return transactionStageDesc;
	}
	public void setTransactionStageDesc(String transactionStageDesc) {
		this.transactionStageDesc = transactionStageDesc;
	}
	public String getTransactionStatusDesc() {
		return transactionStatusDesc;
	}
	public void setTransactionStatusDesc(String transactionStatusDesc) {
		this.transactionStatusDesc = transactionStatusDesc;
	}
	public String getSupervisorCancelRemarks() {
		return supervisorCancelRemarks;
	}
	public void setSupervisorCancelRemarks(String supervisorCancelRemarks) {
		this.supervisorCancelRemarks = supervisorCancelRemarks;
	}
	public String getLocality() {
		return locality;
	}
	public void setLocality(String locality) {
		this.locality = locality;
	}
	public String getDistrict() {
		return district;
	}
	public void setDistrict(String district) {
		this.district = district;
	}
	public String getCounterId() {
		return counterId;
	}
	public void setCounterId(String counterId) {
		this.counterId = counterId;
	}
	public String getQueueNumber() {
		return queueNumber;
	}
	public void setQueueNumber(String queueNumber) {
		this.queueNumber = queueNumber;
	}
	public String getDefaultFp() {
		return defaultFp;
	}
	public void setDefaultFp(String defaultFp) {
		this.defaultFp = defaultFp;
	}
	public boolean isFpVerified() {
		return fpVerified;
	}
	public void setFpVerified(boolean fpVerified) {
		this.fpVerified = fpVerified;
	}
	public boolean isCardVerified() {
		return cardVerified;
	}
	public void setCardVerified(boolean cardVerified) {
		this.cardVerified = cardVerified;
	}
	public String getNicFace() {
		return nicFace;
	}
	public void setNicFace(String nicFace) {
		this.nicFace = nicFace;
	}
	public String getNicSignature() {
		return nicSignature;
	}
	public void setNicSignature(String nicSignature) {
		this.nicSignature = nicSignature;
	}
	public String getNicFpEncode() {
		return nicFpEncode;
	}
	public void setNicFpEncode(String nicFpEncode) {
		this.nicFpEncode = nicFpEncode;
	}
	public RicRegistrationFacialDTO getSmallPortraitCliFace() {
		return smallPortraitCliFace;
	}
	public void setSmallPortraitCliFace(
			RicRegistrationFacialDTO smallPortraitCliFace) {
		this.smallPortraitCliFace = smallPortraitCliFace;
	}
	public Map<String, String> getDefaultFpMap() {
		return defaultFpMap;
	}
	public void setDefaultFpMap(Map<String, String> defaultFpMap) {
		this.defaultFpMap = defaultFpMap;
	}
	public PaymentDTO getPayment() {
		return payment;
	}
	public void setPayment(PaymentDTO payment) {
		this.payment = payment;
	}
	public String getMinutiaHeader() {
		return minutiaHeader;
	}
	public void setMinutiaHeader(String minutiaHeader) {
		this.minutiaHeader = minutiaHeader;
	}
	public String getSigningUrl() {
		return signingUrl;
	}
	public void setSigningUrl(String signingUrl) {
		this.signingUrl = signingUrl;
	}
	
	public boolean isComingFromTransQuery() {
		return comingFromTransQuery;
	}
	public void setComingFromTransQuery(boolean comingFromTransQuery) {
		this.comingFromTransQuery = comingFromTransQuery;
	}
	public String getOldSurname() {
		return oldSurname;
	}
	public void setOldSurname(String oldSurname) {
		this.oldSurname = oldSurname;
	}
	public String getRegSiteCode() {
		return regSiteCode;
	}
	public void setRegSiteCode(String regSiteCode) {
		this.regSiteCode = regSiteCode;
	}
	public String getTown() {
		return town;
	}
	public void setTown(String town) {
		this.town = town;
	}
	public String getOldAddress5() {
		return oldAddress5;
	}
	public void setOldAddress5(String oldAddress5) {
		this.oldAddress5 = oldAddress5;
	}
	public String getOldAddress6() {
		return oldAddress6;
	}
	public void setOldAddress6(String oldAddress6) {
		this.oldAddress6 = oldAddress6;
	}
	public String getFpQualityGood() {
		return fpQualityGood;
	}
	public void setFpQualityGood(String fpQualityGood) {
		this.fpQualityGood = fpQualityGood;
	}
	public String getFpQualityAcceptable() {
		return fpQualityAcceptable;
	}
	public void setFpQualityAcceptable(String fpQualityAcceptable) {
		this.fpQualityAcceptable = fpQualityAcceptable;
	}
	public int getFpVerifyMatchScore() {
		return fpVerifyMatchScore;
	}
	public void setFpVerifyMatchScore(int fpVerifyMatchScore) {
		this.fpVerifyMatchScore = fpVerifyMatchScore;
	}
	public String getCcn() {
		return ccn;
	}
	public void setCcn(String ccn) {
		this.ccn = ccn;
	}
	public String getCardStatus() {
		return cardStatus;
	}
	public void setCardStatus(String cardStatus) {
		this.cardStatus = cardStatus;
	}
	public String getFpVerifyScore() {
		return fpVerifyScore;
	}
	public void setFpVerifyScore(String fpVerifyScore) {
		this.fpVerifyScore = fpVerifyScore;
	}
//EUNIKE START 20130719
	public int getNoCardLost() {
		return noCardLost;
	}
	public void setNoCardLost(int noCardLost) {
		this.noCardLost = noCardLost;
	}
	public boolean isSeniorCitizenFlag() {
		return seniorCitizenFlag;
	}
	public void setSeniorCitizenFlag(boolean seniorCitizenFlag) {
		this.seniorCitizenFlag = seniorCitizenFlag;
	}
	//EUNIKE END
	public String getFacialOriginalImage() {
		return facialOriginalImage;
	}
	public void setFacialOriginalImage(String facialOriginalImage) {
		this.facialOriginalImage = facialOriginalImage;
	}
	public Map<String, String> getFpQualityGoodThreshold() {
		return fpQualityGoodThreshold;
	}
	public void setFpQualityGoodThreshold(Map<String, String> fpQualityGoodThreshold) {
		this.fpQualityGoodThreshold = fpQualityGoodThreshold;
	}
	public Map<String, String> getFpQualityAcceptableThreshold() {
		return fpQualityAcceptableThreshold;
	}
	public void setFpQualityAcceptableThreshold(
			Map<String, String> fpQualityAcceptableThreshold) {
		this.fpQualityAcceptableThreshold = fpQualityAcceptableThreshold;
	}
	public String getOverseasCountry() {
		return overseasCountry;
	}
	public void setOverseasCountry(String overseasCountry) {
		this.overseasCountry = overseasCountry;
	}
	public String getOldOverseasAddress() {
		return oldOverseasAddress;
	}
	public void setOldOverseasAddress(String oldOverseasAddress) {
		this.oldOverseasAddress = oldOverseasAddress;
	}
	public String getOldOverseasAddressCountry() {
		return oldOverseasAddressCountry;
	}
	public void setOldOverseasAddressCountry(String oldOverseasAddressCountry) {
		this.oldOverseasAddressCountry = oldOverseasAddressCountry;
	}
	public String getOldPreferredMailingAddress() {
		return oldPreferredMailingAddress;
	}
	public void setOldPreferredMailingAddress(String oldPreferredMailingAddress) {
		this.oldPreferredMailingAddress = oldPreferredMailingAddress;
	}
	public String getTownDisp() {
		return townDisp;
	}
	public void setTownDisp(String townDisp) {
		this.townDisp = townDisp;
	}
	public String getDistrictDisp() {
		return districtDisp;
	}
	public void setDistrictDisp(String districtDisp) {
		this.districtDisp = districtDisp;
	}
	public List<RicTransactionLogDTO> getRicTransactionLogList() {
		return ricTransactionLogList;
	}
	public void setRicTransactionLogList(List<RicTransactionLogDTO> ricTransactionLogList) {
		this.ricTransactionLogList = ricTransactionLogList;
	}

}
