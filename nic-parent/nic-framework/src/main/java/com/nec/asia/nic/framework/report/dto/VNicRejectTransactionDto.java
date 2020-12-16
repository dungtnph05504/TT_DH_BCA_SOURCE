/**
 * 
 */
package com.nec.asia.nic.framework.report.dto;

import java.util.Date;

/**
 * @author Prasad_Karnati
 *
 */
public class VNicRejectTransactionDto implements java.io.Serializable{
	private String transactionId;
	private String nin;
	private String applnRefNo;
	private String dateOfApplication;
	private String firstnameFull;
	private String surnameFull;
	private String surnameBirthFull;
	private String dateOfBirth;
	private String transactionType;
	private String transactionSubtype;
	private String gender;
	private String regOffId;
	private String transactionDate;
	private String servedBy;
	private String approvedBy;
	private String approveDate;
	private String rejectOfficerId;
	private String rejectRemarks;
	private String rejectReason;
	private String rejectDate;// added by prasad on 20/10/2014(User praveen asked to display reject_Date in the rejectiotxn report)
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
	 * @return the applnRefNo
	 */
	public String getApplnRefNo() {
		return applnRefNo;
	}
	/**
	 * @param applnRefNo the applnRefNo to set
	 */
	public void setApplnRefNo(String applnRefNo) {
		this.applnRefNo = applnRefNo;
	}
	/**
	 * @return the dateOfApplication
	 */
	public String getDateOfApplication() {
		return dateOfApplication;
	}
	/**
	 * @param dateOfApplication the dateOfApplication to set
	 */
	public void setDateOfApplication(String dateOfApplication) {
		this.dateOfApplication = dateOfApplication;
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
	public String getDateOfBirth() {
		return dateOfBirth;
	}
	/**
	 * @param dateOfBirth the dateOfBirth to set
	 */
	public void setDateOfBirth(String dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}
	/**
	 * @return the transactionType
	 */
	public String getTransactionType() {
		return transactionType;
	}
	/**
	 * @param transactionType the transactionType to set
	 */
	public void setTransactionType(String transactionType) {
		this.transactionType = transactionType;
	}
	/**
	 * @return the transactionSubtype
	 */
	public String getTransactionSubtype() {
		return transactionSubtype;
	}
	/**
	 * @param transactionSubtype the transactionSubtype to set
	 */
	public void setTransactionSubtype(String transactionSubtype) {
		this.transactionSubtype = transactionSubtype;
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
	 * @return the regOffId
	 */
	public String getRegOffId() {
		return regOffId;
	}
	/**
	 * @param regOffId the regOffId to set
	 */
	public void setRegOffId(String regOffId) {
		this.regOffId = regOffId;
	}
	/**
	 * @return the transactionDate
	 */
	public String getTransactionDate() {
		return transactionDate;
	}
	/**
	 * @param transactionDate the transactionDate to set
	 */
	public void setTransactionDate(String transactionDate) {
		this.transactionDate = transactionDate;
	}
	/**
	 * @return the servedBy
	 */
	public String getServedBy() {
		return servedBy;
	}
	/**
	 * @param servedBy the servedBy to set
	 */
	public void setServedBy(String servedBy) {
		this.servedBy = servedBy;
	}
	/**
	 * @return the approvedBy
	 */
	public String getApprovedBy() {
		return approvedBy;
	}
	/**
	 * @param approvedBy the approvedBy to set
	 */
	public void setApprovedBy(String approvedBy) {
		this.approvedBy = approvedBy;
	}
	/**
	 * @return the approveDate
	 */
	public String getApproveDate() {
		return approveDate;
	}
	/**
	 * @param approveDate the approveDate to set
	 */
	public void setApproveDate(String approveDate) {
		this.approveDate = approveDate;
	}
	/**
	 * @return the rejectOfficerId
	 */
	public String getRejectOfficerId() {
		return rejectOfficerId;
	}
	/**
	 * @param rejectOfficerId the rejectOfficerId to set
	 */
	public void setRejectOfficerId(String rejectOfficerId) {
		this.rejectOfficerId = rejectOfficerId;
	}
	/**
	 * @return the rejectRemarks
	 */
	public String getRejectRemarks() {
		return rejectRemarks;
	}
	/**
	 * @param rejectRemarks the rejectRemarks to set
	 */
	public void setRejectRemarks(String rejectRemarks) {
		this.rejectRemarks = rejectRemarks;
	}
	/**
	 * @return the rejectReason
	 */
	public String getRejectReason() {
		return rejectReason;
	}
	/**
	 * @param rejectReason the rejectReason to set
	 */
	public void setRejectReason(String rejectReason) {
		this.rejectReason = rejectReason;
	}
	
	/**
	 * @return the rejectDate
	 */
	public String getRejectDate() {
		return rejectDate;
	}

	/**
	 * @param rejectDate the rejectDate to set
	 */
	public void setRejectDate(String rejectDate) {
		this.rejectDate = rejectDate;
	}
}
