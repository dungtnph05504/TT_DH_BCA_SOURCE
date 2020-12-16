package com.nec.asia.nic.comp.trans.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;

import com.nec.asia.nic.comp.job.dto.InfoFamillyDto;
import com.nec.asia.nic.comp.trans.domain.NicRejectionData;
import com.nec.asia.nic.comp.trans.domain.NicSearchResult;
import com.nec.asia.nic.comp.trans.domain.NicTransaction;
import com.nec.asia.nic.comp.trans.domain.NicUploadJob;
/*
 * Modification History:
 * 
 * 16 Oct 2013 (Sailaja) : Added Date of Application to display in the Investigation job list.
 */
import com.nec.asia.nic.utils.DateUtil;

/**
 * @author sailaja_chinapothula
 *
 */
public class NicUploadJobJsonDto implements Serializable {  
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Long workflowJobId;
	private String transactionId;
	private String jobType;
	private Integer jobPriority;
	private Date jobCreatedTime;
	private String jobCurrentStage;
	private String jobCurrentStatus;
	private Date jobStartTime;
	private Date jobEndTime;
	private String cpdCheckStatus;
	private String afisRegisterStatus;
	private String afisCheckStatus;
	private String afisVerifyStatus;
	private String investigationType;
	private String investigationStatus;
	private String investigationOfficerId;
	private String afisUpdateStatus;
	private String persoRegisterStatus;
	private Boolean jobCompletedFlag;
	private Boolean jobErrorFlag;
	private String createBy;
	private String createWkstnId;
	private Date createDatetime;
	private String updateBy;
	private String updateWkstnId;
	private Date updateDatetime;
	private Integer jobReprocessCount;

	private Date estDateOfCollection;
	private Integer priority;
	private String jobApproveStatus;
	///16-04 TRUNG them luu tam so ho chiếu nhập tay
	private String tempPassportNo;
	///11-12 Luu ma goi tin tu A08
	private String receiptNo;
	///12-12 Check trang thai xac thuc thông tin tu bo cong an
	private Integer validateInfoBca;
	
	//phúc thêm thông tin phiếu bàn giao
	private String packageId;
	private Date datePackage;
	private String numberTran;
	private String ricName;
	
	private String originalyId;
	private Date dateApprovePerson;
	private Date dateApproveInvestigation;
	private Date dateApproveNin;
	
	
	private Long originalyBlacklistId;
	private Long originalyPersonId;
	private String approveFlag;
	private String noteApprovePerson;
	private String noteApproveObj;
	private String noteApproveNin;
	private Date editTimeExpiry;
	
	
	
	public Date getEditTimeExpiry() {
		return editTimeExpiry;
	}
	public void setEditTimeExpiry(Date editTimeExpiry) {
		this.editTimeExpiry = editTimeExpiry;
	}
	public Long getWorkflowJobId() {
		return workflowJobId;
	}
	public void setWorkflowJobId(Long workflowJobId) {
		this.workflowJobId = workflowJobId;
	}
	public String getTransactionId() {
		return transactionId;
	}
	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}
	public String getJobType() {
		return jobType;
	}
	public void setJobType(String jobType) {
		this.jobType = jobType;
	}
	public Integer getJobPriority() {
		return jobPriority;
	}
	public void setJobPriority(Integer jobPriority) {
		this.jobPriority = jobPriority;
	}
	public Date getJobCreatedTime() {
		return jobCreatedTime;
	}
	public void setJobCreatedTime(Date jobCreatedTime) {
		this.jobCreatedTime = jobCreatedTime;
	}
	public String getJobCurrentStage() {
		return jobCurrentStage;
	}
	public void setJobCurrentStage(String jobCurrentStage) {
		this.jobCurrentStage = jobCurrentStage;
	}
	public String getJobCurrentStatus() {
		return jobCurrentStatus;
	}
	public void setJobCurrentStatus(String jobCurrentStatus) {
		this.jobCurrentStatus = jobCurrentStatus;
	}
	public Date getJobStartTime() {
		return jobStartTime;
	}
	public void setJobStartTime(Date jobStartTime) {
		this.jobStartTime = jobStartTime;
	}
	public Date getJobEndTime() {
		return jobEndTime;
	}
	public void setJobEndTime(Date jobEndTime) {
		this.jobEndTime = jobEndTime;
	}
	public String getCpdCheckStatus() {
		return cpdCheckStatus;
	}
	public void setCpdCheckStatus(String cpdCheckStatus) {
		this.cpdCheckStatus = cpdCheckStatus;
	}
	public String getAfisRegisterStatus() {
		return afisRegisterStatus;
	}
	public void setAfisRegisterStatus(String afisRegisterStatus) {
		this.afisRegisterStatus = afisRegisterStatus;
	}
	public String getAfisCheckStatus() {
		return afisCheckStatus;
	}
	public void setAfisCheckStatus(String afisCheckStatus) {
		this.afisCheckStatus = afisCheckStatus;
	}
	public String getAfisVerifyStatus() {
		return afisVerifyStatus;
	}
	public void setAfisVerifyStatus(String afisVerifyStatus) {
		this.afisVerifyStatus = afisVerifyStatus;
	}
	public String getInvestigationType() {
		return investigationType;
	}
	public void setInvestigationType(String investigationType) {
		this.investigationType = investigationType;
	}
	public String getInvestigationStatus() {
		return investigationStatus;
	}
	public void setInvestigationStatus(String investigationStatus) {
		this.investigationStatus = investigationStatus;
	}
	public String getInvestigationOfficerId() {
		return investigationOfficerId;
	}
	public void setInvestigationOfficerId(String investigationOfficerId) {
		this.investigationOfficerId = investigationOfficerId;
	}
	public String getAfisUpdateStatus() {
		return afisUpdateStatus;
	}
	public void setAfisUpdateStatus(String afisUpdateStatus) {
		this.afisUpdateStatus = afisUpdateStatus;
	}
	public String getPersoRegisterStatus() {
		return persoRegisterStatus;
	}
	public void setPersoRegisterStatus(String persoRegisterStatus) {
		this.persoRegisterStatus = persoRegisterStatus;
	}
	public Boolean getJobCompletedFlag() {
		return jobCompletedFlag;
	}
	public void setJobCompletedFlag(Boolean jobCompletedFlag) {
		this.jobCompletedFlag = jobCompletedFlag;
	}
	public Boolean getJobErrorFlag() {
		return jobErrorFlag;
	}
	public void setJobErrorFlag(Boolean jobErrorFlag) {
		this.jobErrorFlag = jobErrorFlag;
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
	public Integer getJobReprocessCount() {
		return jobReprocessCount;
	}
	public void setJobReprocessCount(Integer jobReprocessCount) {
		this.jobReprocessCount = jobReprocessCount;
	}
	public Date getEstDateOfCollection() {
		return estDateOfCollection;
	}
	public void setEstDateOfCollection(Date estDateOfCollection) {
		this.estDateOfCollection = estDateOfCollection;
	}
	public Integer getPriority() {
		return priority;
	}
	public void setPriority(Integer priority) {
		this.priority = priority;
	}
	public String getJobApproveStatus() {
		return jobApproveStatus;
	}
	public void setJobApproveStatus(String jobApproveStatus) {
		this.jobApproveStatus = jobApproveStatus;
	}
	public String getTempPassportNo() {
		return tempPassportNo;
	}
	public void setTempPassportNo(String tempPassportNo) {
		this.tempPassportNo = tempPassportNo;
	}
	public String getReceiptNo() {
		return receiptNo;
	}
	public void setReceiptNo(String receiptNo) {
		this.receiptNo = receiptNo;
	}
	public Integer getValidateInfoBca() {
		return validateInfoBca;
	}
	public void setValidateInfoBca(Integer validateInfoBca) {
		this.validateInfoBca = validateInfoBca;
	}
	public String getPackageId() {
		return packageId;
	}
	public void setPackageId(String packageId) {
		this.packageId = packageId;
	}
	public Date getDatePackage() {
		return datePackage;
	}
	public void setDatePackage(Date datePackage) {
		this.datePackage = datePackage;
	}
	public String getNumberTran() {
		return numberTran;
	}
	public void setNumberTran(String numberTran) {
		this.numberTran = numberTran;
	}
	public String getRicName() {
		return ricName;
	}
	public void setRicName(String ricName) {
		this.ricName = ricName;
	}
	public String getOriginalyId() {
		return originalyId;
	}
	public void setOriginalyId(String originalyId) {
		this.originalyId = originalyId;
	}
	public Date getDateApprovePerson() {
		return dateApprovePerson;
	}
	public void setDateApprovePerson(Date dateApprovePerson) {
		this.dateApprovePerson = dateApprovePerson;
	}
	public Date getDateApproveInvestigation() {
		return dateApproveInvestigation;
	}
	public void setDateApproveInvestigation(Date dateApproveInvestigation) {
		this.dateApproveInvestigation = dateApproveInvestigation;
	}
	public Date getDateApproveNin() {
		return dateApproveNin;
	}
	public void setDateApproveNin(Date dateApproveNin) {
		this.dateApproveNin = dateApproveNin;
	}
	public Long getOriginalyBlacklistId() {
		return originalyBlacklistId;
	}
	public void setOriginalyBlacklistId(Long originalyBlacklistId) {
		this.originalyBlacklistId = originalyBlacklistId;
	}
	public Long getOriginalyPersonId() {
		return originalyPersonId;
	}
	public void setOriginalyPersonId(Long originalyPersonId) {
		this.originalyPersonId = originalyPersonId;
	}
	public String getApproveFlag() {
		return approveFlag;
	}
	public void setApproveFlag(String approveFlag) {
		this.approveFlag = approveFlag;
	}
	public String getNoteApprovePerson() {
		return noteApprovePerson;
	}
	public void setNoteApprovePerson(String noteApprovePerson) {
		this.noteApprovePerson = noteApprovePerson;
	}
	public String getNoteApproveObj() {
		return noteApproveObj;
	}
	public void setNoteApproveObj(String noteApproveObj) {
		this.noteApproveObj = noteApproveObj;
	}
	public String getNoteApproveNin() {
		return noteApproveNin;
	}
	public void setNoteApproveNin(String noteApproveNin) {
		this.noteApproveNin = noteApproveNin;
	}
}
