package com.nec.asia.nic.comp.perso.domain;

import java.util.Date;

/**
 * @author khang
 */
/*
 * Modification History:
 * 
 * 18 Jan 2016 (khang): Created
 */
public class PersoStatus implements java.io.Serializable {

	/**
     * The serialVersionUID
     */
    private static final long serialVersionUID = 1L;
    
    private long statusId;
	private String transactionId;
	private String applnRefNo;
	private String passportNo;
	private String chipSerialNo;
	private String serialNo;
	private String transactionStatus;
	private Date statusDate;
	private String printingSite;
	private Date dateOfIssue;
	private Date dateOfExpiry;
	private String errorCode;
	private String errorMessage;
	private Date createDatetime;
	private String sourceLocation;

	public PersoStatus() {
	}

	public PersoStatus(long statusId, String transactionId, Date statusDate) {
		this.statusId = statusId;
		this.transactionId = transactionId;
		this.statusDate = statusDate;
	}

	public PersoStatus(long statusId, String transactionId, String applnRefNo,
			String passportNo, String chipSerialNo, String serialNo,
			String transactionStatus, Date statusDate, String printingSite,
			Date dateOfIssue, Date dateOfExpiry, String errorCode,
			String errorMessage, Date createDatetime, String sourceLocation) {
		this.statusId = statusId;
		this.transactionId = transactionId;
		this.applnRefNo = applnRefNo;
		this.passportNo = passportNo;
		this.chipSerialNo = chipSerialNo;
		this.serialNo = serialNo;
		this.transactionStatus = transactionStatus;
		this.statusDate = statusDate;
		this.printingSite = printingSite;
		this.dateOfIssue = dateOfIssue;
		this.dateOfExpiry = dateOfExpiry;
		this.errorCode = errorCode;
		this.errorMessage = errorMessage;
		this.createDatetime = createDatetime;
		this.sourceLocation = sourceLocation;
	}

	public String getSerialNo() {
		return serialNo;
	}

	public void setSerialNo(String serialNo) {
		this.serialNo = serialNo;
	}

	public long getStatusId() {
		return this.statusId;
	}

	public void setStatusId(long statusId) {
		this.statusId = statusId;
	}

	public String getTransactionId() {
		return this.transactionId;
	}

	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}

	public String getApplnRefNo() {
		return this.applnRefNo;
	}

	public void setApplnRefNo(String applnRefNo) {
		this.applnRefNo = applnRefNo;
	}

	public String getPassportNo() {
		return this.passportNo;
	}

	public void setPassportNo(String passportNo) {
		this.passportNo = passportNo;
	}

	public String getChipSerialNo() {
		return this.chipSerialNo;
	}

	public void setChipSerialNo(String chipSerialNo) {
		this.chipSerialNo = chipSerialNo;
	}

	public String getTransactionStatus() {
		return this.transactionStatus;
	}

	public void setTransactionStatus(String transactionStatus) {
		this.transactionStatus = transactionStatus;
	}

	public Date getStatusDate() {
		return this.statusDate;
	}

	public void setStatusDate(Date statusDate) {
		this.statusDate = statusDate;
	}

	public String getPrintingSite() {
		return this.printingSite;
	}

	public void setPrintingSite(String printingSite) {
		this.printingSite = printingSite;
	}

	public Date getDateOfIssue() {
		return this.dateOfIssue;
	}

	public void setDateOfIssue(Date dateOfIssue) {
		this.dateOfIssue = dateOfIssue;
	}

	public Date getDateOfExpiry() {
		return this.dateOfExpiry;
	}

	public void setDateOfExpiry(Date dateOfExpiry) {
		this.dateOfExpiry = dateOfExpiry;
	}

	public String getErrorCode() {
		return this.errorCode;
	}

	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

	public String getErrorMessage() {
		return this.errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	public Date getCreateDatetime() {
		return this.createDatetime;
	}

	public void setCreateDatetime(Date createDatetime) {
		this.createDatetime = createDatetime;
	}

	public String getSourceLocation() {
		return this.sourceLocation;
	}

	public void setSourceLocation(String sourceLocation) {
		this.sourceLocation = sourceLocation;
	}

}
