package com.nec.asia.nic.framework.report.domain;

import java.util.Date;

// Generated Jun 14, 2013 12:38:31 PM by Hibernate Tools 3.4.0.CR1

/**
 * VNicTransaction generated by hbm2java
 */
public class VNicTransaction implements java.io.Serializable {

	private String transactionId;
	private String nin;
	private String applnRefNo;
	private Date dateOfApplication;
	private String firstnameFull;
	private String surnameFull;
	private String surnameBirthFull;
	private Date dateOfBirth;
	private String transactionType;
	private String transactionStatus;
	private String gender;
	private String regOffId;
	private Date transactionDate;
	private String servedBy;
	private String approvedBy;
	private Date approveDate;

	public VNicTransaction() {
	}

	public VNicTransaction(String transactionId, String nin) {
		this.transactionId = transactionId;
		this.nin = nin;
	}

	public VNicTransaction(String transactionId, String nin,
			String applnRefNo, Date dateOfApplication, String firstnameFull,
			String surnameFull, String surnameBirthFull, Date dateOfBirth,
			String transactionType, String transactionStatus, String gender,
			String regOffId, Date transactionDate, String servedBy,
			String approvedBy, Date approveDate) {
		this.transactionId = transactionId;
		this.nin = nin;
		this.applnRefNo = applnRefNo;
		this.dateOfApplication = dateOfApplication;
		this.firstnameFull = firstnameFull;
		this.surnameFull = surnameFull;
		this.surnameBirthFull = surnameBirthFull;
		this.dateOfBirth = dateOfBirth;
		this.transactionType = transactionType;
		this.transactionStatus = transactionStatus;
		this.gender = gender;
		this.regOffId = regOffId;
		this.transactionDate = transactionDate;
		this.servedBy = servedBy;
		this.approvedBy = approvedBy;
		this.approveDate = approveDate;
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

	public String getApplnRefNo() {
		return this.applnRefNo;
	}

	public void setApplnRefNo(String applnRefNo) {
		this.applnRefNo = applnRefNo;
	}

	public Date getDateOfApplication() {
		return this.dateOfApplication;
	}

	public void setDateOfApplication(Date dateOfApplication) {
		this.dateOfApplication = dateOfApplication;
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

	public String getTransactionType() {
		return this.transactionType;
	}

	public void setTransactionType(String transactionType) {
		this.transactionType = transactionType;
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

	public String getRegOffId() {
		return this.regOffId;
	}

	public void setRegOffId(String regOffId) {
		this.regOffId = regOffId;
	}

	public Date getTransactionDate() {
		return this.transactionDate;
	}

	public void setTransactionDate(Date transactionDate) {
		this.transactionDate = transactionDate;
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

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof VNicTransaction))
			return false;
		VNicTransaction castOther = (VNicTransaction) other;

		return ((this.getTransactionId() == castOther.getTransactionId()) || (this
				.getTransactionId() != null
				&& castOther.getTransactionId() != null && this
				.getTransactionId().equals(castOther.getTransactionId())))
				&& ((this.getNin() == castOther.getNin()) || (this.getNin() != null
						&& castOther.getNin() != null && this.getNin().equals(
						castOther.getNin())))
				&& ((this.getApplnRefNo() == castOther.getApplnRefNo()) || (this
						.getApplnRefNo() != null
						&& castOther.getApplnRefNo() != null && this
						.getApplnRefNo().equals(castOther.getApplnRefNo())))
				&& ((this.getDateOfApplication() == castOther
						.getDateOfApplication()) || (this
						.getDateOfApplication() != null
						&& castOther.getDateOfApplication() != null && this
						.getDateOfApplication().equals(
								castOther.getDateOfApplication())))
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
				&& ((this.getTransactionType() == castOther
						.getTransactionType()) || (this.getTransactionType() != null
						&& castOther.getTransactionType() != null && this
						.getTransactionType().equals(
								castOther.getTransactionType())))
				&& ((this.getTransactionStatus() == castOther
						.getTransactionStatus()) || (this
						.getTransactionStatus() != null
						&& castOther.getTransactionStatus() != null && this
						.getTransactionStatus().equals(
								castOther.getTransactionStatus())))
				&& ((this.getGender() == castOther.getGender()) || (this
						.getGender() != null && castOther.getGender() != null && this
						.getGender().equals(castOther.getGender())))
				&& ((this.getRegOffId() == castOther.getRegOffId()) || (this
						.getRegOffId() != null
						&& castOther.getRegOffId() != null && this
						.getRegOffId().equals(castOther.getRegOffId())))
				&& ((this.getTransactionDate() == castOther
						.getTransactionDate()) || (this.getTransactionDate() != null
						&& castOther.getTransactionDate() != null && this
						.getTransactionDate().equals(
								castOther.getTransactionDate())))
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
						.getApproveDate().equals(castOther.getApproveDate())));
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
				+ (getApplnRefNo() == null ? 0 : this.getApplnRefNo()
						.hashCode());
		result = 37
				* result
				+ (getDateOfApplication() == null ? 0 : this
						.getDateOfApplication().hashCode());
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
				+ (getTransactionType() == null ? 0 : this.getTransactionType()
						.hashCode());
		result = 37
				* result
				+ (getTransactionStatus() == null ? 0 : this
						.getTransactionStatus().hashCode());
		result = 37 * result
				+ (getGender() == null ? 0 : this.getGender().hashCode());
		result = 37 * result
				+ (getRegOffId() == null ? 0 : this.getRegOffId().hashCode());
		result = 37
				* result
				+ (getTransactionDate() == null ? 0 : this.getTransactionDate()
						.hashCode());
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
		return result;
	}

}
