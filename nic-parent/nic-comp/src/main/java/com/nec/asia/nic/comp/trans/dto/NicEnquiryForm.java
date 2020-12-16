/**
 * 
 */
package com.nec.asia.nic.comp.trans.dto;

import java.util.List;
import java.util.Map;

import com.nec.asia.nic.framework.admin.code.domain.CodeValues;
import com.nec.asia.nic.framework.admin.site.domain.SiteRepository;
import com.nec.asia.nic.framework.common.LabelValueBean;
import com.nec.asia.nic.comp.job.dto.HitCandidateDTO;
import com.nec.asia.nic.comp.trans.domain.NicRegistrationData;
import com.nec.asia.nic.comp.trans.domain.NicTransaction;
import com.nec.asia.nic.comp.trans.domain.NicUploadJob;

/**
 * @author sailaja_chinapothula
 * @Company: NEC Asia Pacific Ltd
 * @Since: Jun 19, 2013
 */
/* 
 * Modification History:
 * 
 * 23 Sep 2013 (chris): remove duplicated line to fix compile error.
 * 
 * 07 Nov 2013 (Peddi Swapna): added investigationType, investigationTypeList attributes.
 * 10 Dec 2013 (Peddi Swapna): Added new methods to support the NIC Statistics modules during the code merge. 
 * 08 May 2014 (Peddi Swapna): Added showErrTrans, to allow the user to see the error transactions.
 */
public class NicEnquiryForm {
	
	private String jobType;
	private String investigationType;
	private String jobState;
	private String recordStatus;
	private Map<String, String> jobTypeList;
	private Map<String, String> jobStateList;
	private Map<String, String> recordStatusList;
	private Map<String, String> siteCodeList;
	private Map<String, String> transactionTypeList;
	private String owner;
	private Long jobId;
	private String nin;
	private String transactionId;
	private String siteCode;
	private String regSiteCode;
	private String issSiteCode;
	private String transactionStatus;
	private String selSiteCode;
	private String transactionType;
	private String dateOfBirth;
	private String passportNo;
	private String firstName;
	private String lastName;
	private String middleName;
	private String receiptNo;
	private String gender;
	private List<CodeValues> siteCodeCodeList;
	private List<CodeValues> txnStatusCodeList;
	private List<CodeValues> jobTypeCodeList;
	private List<CodeValues> recordStatusCodeList;
	private List<CodeValues> jobStateCodeList;
	private List<CodeValues> genderCodeList;
	private List<LabelValueBean> investigationTypeList;
	private List<SiteRepository> regSiteList;
	private List<SiteRepository> issSiteList;
	private NicUploadJob   nicUploadJob;
	private NicTransaction  nicTransaction;
	private NicRegistrationData  registrationData;
	private HitCandidateDTO cpdData;
	private String dateFrom;
	private String dateTo;
	
	private String userId; //Added by Sailaja for Audit Enquiry
	private String wkstnId; //Added by Sailaja for Audit Enquiry
	private String functionName;//Added by Sailaja for Audit Enquiry
	
	private String auditDateFrom;//Added by Sailaja for Audit Enquiry
	private String auditDateTo;//Added by Sailaja for Audit Enquiry
	private String errorFlag;//Added by Sailaja for Audit Enquiry
	
	private String rptFormate;//added by Prasad for reports
	
	 
	private String officerId;
	
	private String photoOption;
	
	private String pageNum;
	
	private int pageSize;
	
	private String fromQualityScore;
	private String toQualityScore;
	private String fromVerificationScore;
	private String toVerificationScore;
	private String fpOption;
	
	private String  transIds;
	
	private String selectedMonth;
	
	private String selectedDate;
	
	private String dataSyncId;
	
	private boolean showErrTrans;
	private String remarks;
	private String cancelPptFlag;
	
	public String getOwner() {
		return owner;
	}
	public void setOwner(String owner) {
		this.owner = owner;
	}
	public Long getJobId() {
		return jobId;
	}
	public void setJobId(Long jobId) {
		this.jobId = jobId;
	}
	public String getNin() {
		return nin;
	}
	public void setNin(String nin) {
		this.nin = nin;
	}
	public String getTransactionId() {
		return transactionId;
	}
	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}
	/**
	 * @return the jobTypeList
	 */
	public Map<String, String>  getJobTypeList() {
		return jobTypeList;
	}
	/**
	 * @param jobTypeList the jobTypeList to set
	 */
	public void setJobTypeList(Map<String, String>  jobTypeList) {
		this.jobTypeList = jobTypeList;
	}
	/**
	 * @return the jobStateList
	 */
	public Map<String, String>  getJobStateList() {
		return jobStateList;
	}
	/**
	 * @param jobStateList the jobStateList to set
	 */
	public void setJobStateList(Map<String, String>  jobStateList) {
		this.jobStateList = jobStateList;
	}
	/**
	 * @return the recordStatusList
	 */
	public Map<String, String>  getRecordStatusList() {
		return recordStatusList;
	}
	/**
	 * @param recordStatusList the recordStatusList to set
	 */
	public void setRecordStatusList(Map<String, String>  recordStatusList) {
		this.recordStatusList = recordStatusList;
	}
	public Map<String, String> getSiteCodeList() {
		return siteCodeList;
	}
	public void setSiteCodeList(Map<String, String> siteCodeList) {
		this.siteCodeList = siteCodeList;
	}
	public Map<String, String> getTransactionTypeList() {
		return transactionTypeList;
	}
	public void setTransactionTypeList(Map<String, String> transactionTypeList) {
		this.transactionTypeList = transactionTypeList;
	}
	/**
	 * @return the jobType
	 */
	public String getJobType() {
		return jobType;
	}
	/**
	 * @param jobType the jobType to set
	 */
	public void setJobType(String jobType) {
		this.jobType = jobType;
	}
	/**
	 * @return the jobState
	 */
	public String getJobState() {
		return jobState;
	}
	/**
	 * @param jobState the jobState to set
	 */
	public void setJobState(String jobState) {
		this.jobState = jobState;
	}
	/**
	 * @return the recordStatus
	 */
	public String getRecordStatus() {
		return recordStatus;
	}
	/**
	 * @param recordStatus2 the recordStatus to set
	 */
	public void setRecordStatus(String recordStatus2) {
		this.recordStatus = recordStatus2;
	}
	public String getTransactionType() {
		return transactionType;
	}
	public void setTransactionType(String transactionType) {
		this.transactionType = transactionType;
	}
	public List<CodeValues> getJobTypeCodeList() {
		return jobTypeCodeList;
	}
	public void setJobTypeCodeList(List<CodeValues> jobTypeCodeList) {
		this.jobTypeCodeList = jobTypeCodeList;
	}
	public List<CodeValues> getSiteCodeCodeList() {
		return siteCodeCodeList;
	}
	public void setSiteCodeCodeList(List<CodeValues> siteCodeCodeList) {
		this.siteCodeCodeList = siteCodeCodeList;
	}
	public List<CodeValues> getRecordStatusCodeList() {
		return recordStatusCodeList;
	}
	public void setRecordStatusCodeList(List<CodeValues> recordStatusCodeList) {
		this.recordStatusCodeList = recordStatusCodeList;
	}
	public List<CodeValues> getJobStateCodeList() {
		return jobStateCodeList;
	}
	public void setJobStateCodeList(List<CodeValues> jobStateCodeList) {
		this.jobStateCodeList = jobStateCodeList;
	}
	public List<CodeValues> getTxnStatusCodeList() {
		return txnStatusCodeList;
	}
	public void setTxnStatusCodeList(List<CodeValues> txnStatusCodeList) {
		this.txnStatusCodeList = txnStatusCodeList;
	}
	/**
	 * @return the nicUploadJob
	 */
	public NicUploadJob getNicUploadJob() {
		return nicUploadJob;
	}
	/**
	 * @param nicUploadJob the nicUploadJob to set
	 */
	public void setNicUploadJob(NicUploadJob nicUploadJob) {
		this.nicUploadJob = nicUploadJob;
	}
	/**
	 * @return the nicTransaction
	 */
	public NicTransaction getNicTransaction() {
		return nicTransaction;
	}
	/**
	 * @param nicTransaction the nicTransaction to set
	 */
	public void setNicTransaction(NicTransaction nicTransaction) {
		this.nicTransaction = nicTransaction;
	}

	/**
	 * @return the cpdData
	 */
	public HitCandidateDTO getCpdData() {
		return cpdData;
	}
	/**
	 * @param cpdData the cpdData to set
	 */
	public void setCpdData(HitCandidateDTO cpdData) {
		this.cpdData = cpdData;
	}
	/**
	 * @return the investigationType
	 */
	public String getInvestigationType() {
		return investigationType;
	}
	/**
	 * @param investigationType the investigationType to set
	 */
	public void setInvestigationType(String investigationType) {
		this.investigationType = investigationType;
	}
	/**
	 * @return the investigationTypeList
	 */
	public List<LabelValueBean> getInvestigationTypeList() {
		return investigationTypeList;
	}
	/**
	 * @param investigationTypeList the investigationTypeList to set
	 */
	public void setInvestigationTypeList(List<LabelValueBean> investigationTypeList) {
		this.investigationTypeList = investigationTypeList;
	}
	
		public String getDateFrom() {
		return dateFrom;
	}
	public void setDateFrom(String dateFrom) {
		this.dateFrom = dateFrom;
	}
	public String getDateTo() {
		return dateTo;
	}
	public void setDateTo(String dateTo) {
		this.dateTo = dateTo;
	}
	/**
	 * @return the photoOption
	 */
	public String getPhotoOption() {
		return photoOption;
	}
	/**
	 * @param photoOption the photoOption to set
	 */
	public void setPhotoOption(String photoOption) {
		this.photoOption = photoOption;
	}
	/**
	 * @return the officerId
	 */
	public String getOfficerId() {
		return officerId;
	}
	/**
	 * @param officerId the officerId to set
	 */
	public void setOfficerId(String officerId) {
		this.officerId = officerId;
	}
	/**
	 * @return the pageSize
	 */
	public int getPageSize() {
		return pageSize;
	}
	/**
	 * @param pageSize the pageSize to set
	 */
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
	
	public String getPageNum() {
		return pageNum;
	}
	public void setPageNum(String pageNum) {
		this.pageNum = pageNum;
	}
	/**
	 * @return the fpOption
	 */
	public String getFpOption() {
		return fpOption;
	}
	/**
	 * @param fpOption the fpOption to set
	 */
	public void setFpOption(String fpOption) {
		this.fpOption = fpOption;
	}
	/**
	 * @return the fromQualityScore
	 */
	public String getFromQualityScore() {
		return fromQualityScore;
	}
	/**
	 * @param fromQualityScore the fromQualityScore to set
	 */
	public void setFromQualityScore(String fromQualityScore) {
		this.fromQualityScore = fromQualityScore;
	}
	/**
	 * @return the toQualityScore
	 */
	public String getToQualityScore() {
		return toQualityScore;
	}
	/**
	 * @param toQualityScore the toQualityScore to set
	 */
	public void setToQualityScore(String toQualityScore) {
		this.toQualityScore = toQualityScore;
	}
	/**
	 * @return the fromVerificationScore
	 */
	public String getFromVerificationScore() {
		return fromVerificationScore;
	}
	/**
	 * @param fromVerificationScore the fromVerificationScore to set
	 */
	public void setFromVerificationScore(String fromVerificationScore) {
		this.fromVerificationScore = fromVerificationScore;
	}
	/**
	 * @return the toVerificationScore
	 */
	public String getToVerificationScore() {
		return toVerificationScore;
	}
	/**
	 * @param toVerificationScore the toVerificationScore to set
	 */
	public void setToVerificationScore(String toVerificationScore) {
		this.toVerificationScore = toVerificationScore;
	}
	
	
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getWkstnId() {
		return wkstnId;
	}
	public void setWkstnId(String wkstnId) {
		this.wkstnId = wkstnId;
	}
	public String getFunctionName() {
		return functionName;
	}
	public void setFunctionName(String functionName) {
		this.functionName = functionName;
	}
	
	public String getAuditDateFrom() {
		return auditDateFrom;
	}
	public void setAuditDateFrom(String auditDateFrom) {
		this.auditDateFrom = auditDateFrom;
	}
	public String getAuditDateTo() {
		return auditDateTo;
	}
	public void setAuditDateTo(String auditDateTo) {
		this.auditDateTo = auditDateTo;
	}
	public String getErrorFlag() {
		return errorFlag;
	}
	public void setErrorFlag(String errorFlag) {
		this.errorFlag = errorFlag;
	}
	/**
	 * @return the selSiteCode
	 */
	public String getSelSiteCode() {
		return selSiteCode;
	}
	/**
	 * @param selSiteCode the selSiteCode to set
	 */
	public void setSelSiteCode(String selSiteCode) {
		this.selSiteCode = selSiteCode;
	}
	/**
	 * @return the transIds
	 */
	public String getTransIds() {
		return transIds;
	}
	/**
	 * @param transIds the transIds to set
	 */
	public void setTransIds(String transIds) {
		this.transIds = transIds;
	}
	/**
	 * @return the selectedMonth
	 */
	public String getSelectedMonth() {
		return selectedMonth;
	}
	/**
	 * @param selectedMonth the selectedMonth to set
	 */
	public void setSelectedMonth(String selectedMonth) {
		this.selectedMonth = selectedMonth;
	}
	/**
	 * @return the selectedDate
	 */
	public String getSelectedDate() {
		return selectedDate;
	}
	/**
	 * @param selectedDate the selectedDate to set
	 */
	public void setSelectedDate(String selectedDate) {
		this.selectedDate = selectedDate;
	}
	/**
	 * @return the dataSyncId
	 */
	public String getDataSyncId() {
		return dataSyncId;
	}
	/**
	 * @param dataSyncId the dataSyncId to set
	 */
	public void setDataSyncId(String dataSyncId) {
		this.dataSyncId = dataSyncId;
	}
	
	/**
	 * @return the showErrTrans
	 */
	public boolean isShowErrTrans() {
		return showErrTrans;
	}
	
	/**
	 * @param showErrTrans the showErrTrans to set
	 */
	public void setShowErrTrans(boolean showErrTrans) {
		this.showErrTrans = showErrTrans;
	}

	
	/**
	 * @return the rptFormate
	 */
	public String getRptFormate() {
		return rptFormate;
	}
	/**
	 * @param rptFormate the rptFormate to set
	 */
	public void setRptFormate(String rptFormate) {
		this.rptFormate = rptFormate;
	}
	public List<SiteRepository> getRegSiteList() {
		return regSiteList;
	}
	public void setRegSiteList(List<SiteRepository> regSiteList) {
		this.regSiteList = regSiteList;
	}
	public List<SiteRepository> getIssSiteList() {
		return issSiteList;
	}
	public void setIssSiteList(List<SiteRepository> issSiteList) {
		this.issSiteList = issSiteList;
	}
	public String getRegSiteCode() {
		return regSiteCode;
	}
	public void setRegSiteCode(String regSiteCode) {
		this.regSiteCode = regSiteCode;
	}
	public String getIssSiteCode() {
		return issSiteCode;
	}
	public void setIssSiteCode(String issSiteCode) {
		this.issSiteCode = issSiteCode;
	}
	public String getTransactionStatus() {
		return transactionStatus;
	}
	public void setTransactionStatus(String transactionStatus) {
		this.transactionStatus = transactionStatus;
	}
	public String getSiteCode() {
		return siteCode;
	}
	public void setSiteCode(String siteCode) {
		this.siteCode = siteCode;
	}
	public List<CodeValues> getGenderCodeList() {
		return genderCodeList;
	}
	public void setGenderCodeList(List<CodeValues> genderCodeList) {
		this.genderCodeList = genderCodeList;
	}
	public NicRegistrationData getRegistrationData() {
		return registrationData;
	}
	public void setRegistrationData(NicRegistrationData registrationData) {
		this.registrationData = registrationData;
	}
	public String getDateOfBirth() {
		return dateOfBirth;
	}
	public void setDateOfBirth(String dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}
	public String getPassportNo() {
		return passportNo;
	}
	public void setPassportNo(String passportNo) {
		this.passportNo = passportNo;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getMiddleName() {
		return middleName;
	}
	public void setMiddleName(String middleName) {
		this.middleName = middleName;
	}
	public String getReceiptNo() {
		return receiptNo;
	}
	public void setReceiptNo(String receiptNo) {
		this.receiptNo = receiptNo;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	public String getCancelPptFlag() {
		return cancelPptFlag;
	}
	public void setCancelPptFlag(String cancelPptFlag) {
		this.cancelPptFlag = cancelPptFlag;
	}

}