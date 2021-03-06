package com.nec.asia.nic.comp.trans.domain;


import java.util.Date;

// Generated Aug 11, 2014 12:01:57 AM by Hibernate Tools 3.4.0.CR1

/**
 * VNicTxnLog generated by hbm2java
 */
public class VNicTxnLog implements java.io.Serializable {

	
	private Long   logId;
	private String transactionId;
	private String nin;
	private String firstnameFull;
	private String surnameFull;
	private String surnameBirthFull;
	private Date dateOfBirth;
	private String transactionSubtype;
	private String transactionStatus;
	private String gender;
	private String regSiteCode;
	private Date dateOfApplication;
	private String servedBy;
	private String approvedBy;
	private Date approveDate;
	private String issSiteCode;
	private String cardStatus;
	private String ccn;	
	private String officerId;
	private Date txnUpdateDate;
	private String logData;
	private String logInfo;

	public VNicTxnLog() {
	}

	public VNicTxnLog(String transactionId) {
		this.transactionId = transactionId;
		
	}

	public VNicTxnLog(Long logId ,String transactionId, String nin, String firstnameFull,
			String surnameFull, String surnameBirthFull, Date dateOfBirth,
			String transactionSubtype, String transactionStatus, String gender,
			String regSiteCode, Date dateOfApplication, String servedBy,
			String approvedBy, Date approveDate, String issSiteCode,
			String cardStatus,String ccn, String officerId, Date txnUpdateDate,
			String logData, String logInfo) {
		this.logId = logId;
		this.transactionId = transactionId;
		this.nin = nin;
		this.firstnameFull = firstnameFull;
		this.surnameFull = surnameFull;
		this.surnameBirthFull = surnameBirthFull;
		this.dateOfBirth = dateOfBirth;
		this.transactionSubtype = transactionSubtype;
		this.transactionStatus = transactionStatus;
		this.gender = gender;
		this.regSiteCode = regSiteCode;
		this.dateOfApplication = dateOfApplication;
		this.servedBy = servedBy;
		this.approvedBy = approvedBy;
		this.approveDate = approveDate;
		this.issSiteCode = issSiteCode;
		this.cardStatus = cardStatus;
		this.ccn=ccn;
		this.officerId = officerId;
		this.txnUpdateDate = txnUpdateDate;
		this.logData = logData;
		this.logInfo = logInfo;
	}
	public Long getLogId() {
		return this.logId;
	}

	public void setLogId(Long logId) {
		this.logId = logId;
	}
	public String getTransactionId() {
		return this.transactionId;
	}

	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}

	public String getNin() {
		return this.nin;
	}

	public void setNin(String nin) {
		this.nin = nin;
	}

	public String getFirstnameFull() {
		return this.firstnameFull;
	}

	public void setFirstnameFull(String firstnameFull) {
		this.firstnameFull = firstnameFull;
	}

	public String getSurnameFull() {
		return this.surnameFull;
	}

	public void setSurnameFull(String surnameFull) {
		this.surnameFull = surnameFull;
	}

	public String getSurnameBirthFull() {
		return this.surnameBirthFull;
	}

	public void setSurnameBirthFull(String surnameBirthFull) {
		this.surnameBirthFull = surnameBirthFull;
	}

	public Date getDateOfBirth() {
		return this.dateOfBirth;
	}

	public void setDateOfBirth(Date dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	public String getTransactionSubtype() {
		return this.transactionSubtype;
	}

	public void setTransactionSubtype(String transactionSubtype) {
		this.transactionSubtype = transactionSubtype;
	}

	public String getTransactionStatus() {
		return this.transactionStatus;
	}

	public void setTransactionStatus(String transactionStatus) {
		this.transactionStatus = transactionStatus;
	}

	public String getGender() {
		return this.gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getRegSiteCode() {
		return this.regSiteCode;
	}

	public void setRegSiteCode(String regSiteCode) {
		this.regSiteCode = regSiteCode;
	}

	public Date getDateOfApplication() {
		return this.dateOfApplication;
	}

	public void setDateOfApplication(Date dateOfApplication) {
		this.dateOfApplication = dateOfApplication;
	}

	public String getServedBy() {
		return this.servedBy;
	}

	public void setServedBy(String servedBy) {
		this.servedBy = servedBy;
	}

	public String getApprovedBy() {
		return this.approvedBy;
	}

	public void setApprovedBy(String approvedBy) {
		this.approvedBy = approvedBy;
	}

	public Date getApproveDate() {
		return this.approveDate;
	}

	public void setApproveDate(Date approveDate) {
		this.approveDate = approveDate;
	}

	public String getIssSiteCode() {
		return this.issSiteCode;
	}

	public void setIssSiteCode(String issSiteCode) {
		this.issSiteCode = issSiteCode;
	}

	public String getCardStatus() {
		return this.cardStatus;
	}

	public void setCardStatus(String cardStatus) {
		this.cardStatus = cardStatus;
	}

	public String getOfficerId() {
		return this.officerId;
	}

	public void setOfficerId(String officerId) {
		this.officerId = officerId;
	}

	public Date getTxnUpdateDate() {
		return this.txnUpdateDate;
	}

	public void setTxnUpdateDate(Date txnUpdateDate) {
		this.txnUpdateDate = txnUpdateDate;
	}

	public String getLogData() {
		return this.logData;
	}

	public void setLogData(String logData) {
		this.logData = logData;
	}

	public String getLogInfo() {
		return this.logInfo;
	}

	public void setLogInfo(String logInfo) {
		this.logInfo = logInfo;
	}

	public String getCcn() {
		return ccn;
	}

	public void setCcn(String ccn) {
		this.ccn = ccn;
	}

	/*public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof VNicTxnLog))
			return false;
		VNicTxnLog castOther = (VNicTxnLog) other;

		return ((this.getLogId() == castOther.getLogId()) || (this
						.getLogId() != null
						&& castOther.getLogId() != null && this
						.getLogId().equals(castOther.getLogId())))
				
				(this.getTransactionId() == castOther.getTransactionId()) || (this
				.getTransactionId() != null
				&& castOther.getTransactionId() != null && this
				.getTransactionId().equals(castOther.getTransactionId())))
				
				&& ((this.getNin() == castOther.getNin()) || (this.getNin() != null
						&& castOther.getNin() != null && this.getNin().equals(
						castOther.getNin())))
						
				&& ((this.getFirstnameFull() == castOther.getFirstnameFull()) || (this
						.getFirstnameFull() != null
						&& castOther.getFirstnameFull() != null && this
						.getFirstnameFull()
						.equals(castOther.getFirstnameFull())))
				&& ((this.getSurnameFull() == castOther.getSurnameFull()) || (this
						.getSurnameFull() != null
						&& castOther.getSurnameFull() != null && this
						.getSurnameFull().equals(castOther.getSurnameFull())))
				&& ((this.getSurnameBirthFull() == castOther
						.getSurnameBirthFull()) || (this.getSurnameBirthFull() != null
						&& castOther.getSurnameBirthFull() != null && this
						.getSurnameBirthFull().equals(
								castOther.getSurnameBirthFull())))
				&& ((this.getDateOfBirth() == castOther.getDateOfBirth()) || (this
						.getDateOfBirth() != null
						&& castOther.getDateOfBirth() != null && this
						.getDateOfBirth().equals(castOther.getDateOfBirth())))
				&& ((this.getTransactionSubtype() == castOther
						.getTransactionSubtype()) || (this
						.getTransactionSubtype() != null
						&& castOther.getTransactionSubtype() != null && this
						.getTransactionSubtype().equals(
								castOther.getTransactionSubtype())))
				&& ((this.getTransactionStatus() == castOther
						.getTransactionStatus()) || (this
						.getTransactionStatus() != null
						&& castOther.getTransactionStatus() != null && this
						.getTransactionStatus().equals(
								castOther.getTransactionStatus())))
				&& ((this.getGender() == castOther.getGender()) || (this
						.getGender() != null && castOther.getGender() != null && this
						.getGender().equals(castOther.getGender())))
				&& ((this.getRegSiteCode() == castOther.getRegSiteCode()) || (this
						.getRegSiteCode() != null
						&& castOther.getRegSiteCode() != null && this
						.getRegSiteCode().equals(castOther.getRegSiteCode())))
				&& ((this.getDateOfApplication() == castOther
						.getDateOfApplication()) || (this
						.getDateOfApplication() != null
						&& castOther.getDateOfApplication() != null && this
						.getDateOfApplication().equals(
								castOther.getDateOfApplication())))
				&& ((this.getServedBy() == castOther.getServedBy()) || (this
						.getServedBy() != null
						&& castOther.getServedBy() != null && this
						.getServedBy().equals(castOther.getServedBy())))
				&& ((this.getApprovedBy() == castOther.getApprovedBy()) || (this
						.getApprovedBy() != null
						&& castOther.getApprovedBy() != null && this
						.getApprovedBy().equals(castOther.getApprovedBy())))
				&& ((this.getApproveDate() == castOther.getApproveDate()) || (this
						.getApproveDate() != null
						&& castOther.getApproveDate() != null && this
						.getApproveDate().equals(castOther.getApproveDate())))
				&& ((this.getIssSiteCode() == castOther.getIssSiteCode()) || (this
						.getIssSiteCode() != null
						&& castOther.getIssSiteCode() != null && this
						.getIssSiteCode().equals(castOther.getIssSiteCode())))
				&& ((this.getCardStatus() == castOther.getCardStatus()) || (this
						.getCardStatus() != null
						&& castOther.getCardStatus() != null && this
						.getCardStatus().equals(castOther.getCardStatus())))
				&& ((this.getOfficerId() == castOther.getOfficerId()) || (this
						.getOfficerId() != null
						&& castOther.getOfficerId() != null && this
						.getOfficerId().equals(castOther.getOfficerId())))
				&& ((this.getTxnUpdateDate() == castOther.getTxnUpdateDate()) || (this
						.getTxnUpdateDate() != null
						&& castOther.getTxnUpdateDate() != null && this
						.getTxnUpdateDate()
						.equals(castOther.getTxnUpdateDate())))
				&& ((this.getLogData() == castOther.getLogData()) || (this
						.getLogData() != null && castOther.getLogData() != null && this
						.getLogData().equals(castOther.getLogData())))
				&& ((this.getLogInfo() == castOther.getLogInfo()) || (this
						.getLogInfo() != null && castOther.getLogInfo() != null && this
						.getLogInfo().equals(castOther.getLogInfo())));
	}

	public int hashCode() {
		int result = 17;

		result = 37
				* result
				+ (getTransactionId() == null ? 0 : this.getTransactionId()
						.hashCode());
		result = 37 * result
				+ (getNin() == null ? 0 : this.getNin().hashCode());
		result = 37
				* result
				+ (getFirstnameFull() == null ? 0 : this.getFirstnameFull()
						.hashCode());
		result = 37
				* result
				+ (getSurnameFull() == null ? 0 : this.getSurnameFull()
						.hashCode());
		result = 37
				* result
				+ (getSurnameBirthFull() == null ? 0 : this
						.getSurnameBirthFull().hashCode());
		result = 37
				* result
				+ (getDateOfBirth() == null ? 0 : this.getDateOfBirth()
						.hashCode());
		result = 37
				* result
				+ (getTransactionSubtype() == null ? 0 : this
						.getTransactionSubtype().hashCode());
		result = 37
				* result
				+ (getTransactionStatus() == null ? 0 : this
						.getTransactionStatus().hashCode());
		result = 37 * result
				+ (getGender() == null ? 0 : this.getGender().hashCode());
		result = 37
				* result
				+ (getRegSiteCode() == null ? 0 : this.getRegSiteCode()
						.hashCode());
		result = 37
				* result
				+ (getDateOfApplication() == null ? 0 : this
						.getDateOfApplication().hashCode());
		result = 37 * result
				+ (getServedBy() == null ? 0 : this.getServedBy().hashCode());
		result = 37
				* result
				+ (getApprovedBy() == null ? 0 : this.getApprovedBy()
						.hashCode());
		result = 37
				* result
				+ (getApproveDate() == null ? 0 : this.getApproveDate()
						.hashCode());
		result = 37
				* result
				+ (getIssSiteCode() == null ? 0 : this.getIssSiteCode()
						.hashCode());
		result = 37
				* result
				+ (getCardStatus() == null ? 0 : this.getCardStatus()
						.hashCode());
		result = 37 * result
				+ (getOfficerId() == null ? 0 : this.getOfficerId().hashCode());
		result = 37
				* result
				+ (getTxnUpdateDate() == null ? 0 : this.getTxnUpdateDate()
						.hashCode());
		result = 37 * result
				+ (getLogData() == null ? 0 : this.getLogData().hashCode());
		result = 37 * result
				+ (getLogInfo() == null ? 0 : this.getLogInfo().hashCode());
		return result;
	}
*/

}
