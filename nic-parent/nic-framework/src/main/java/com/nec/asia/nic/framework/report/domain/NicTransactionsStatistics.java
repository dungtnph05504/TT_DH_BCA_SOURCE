package com.nec.asia.nic.framework.report.domain;

// Generated Sep 24, 2013 8:10:14 PM by Hibernate Tools 3.4.0.CR1

import java.lang.Long;
import java.util.Date;

/**
 * NicTransactionsStatistics generated by hbm2java
 */
public class NicTransactionsStatistics implements java.io.Serializable {

	
	private String regSiteCode;
	private Long numOfTxnRecieved;
	private Long numOfApproveTxn;
	private Long numOfRejectTxn;
	private Long numOfPendingInvestTxn;
	private Long numOfPendingProcessTxn;
	private Long numOfErrorProcessTxn;
	private Long numOfTotalTxns;
	private Date createDate;
	private Date updateDate;	
	private Date dateOfApplication;
	private String issSiteCode;
	private String transactionType;
	private String transactionSubtype;
	
	public NicTransactionsStatistics() {
	}

	

	public NicTransactionsStatistics(Date dateOfApplication,
			String issSiteCode, String transactionType,
			String transactionSubtype,
			String regSiteCode, Long numOfTxnRecieved,
			Long numOfApproveTxn, Long numOfRejectTxn,
			Long numOfPendingInvestTxn,
			Long numOfPendingProcessTxn, Long numOfErrorProcessTxn,
			Long numOfTotalTxns, Date createDate, Date updateDate) {
		this.dateOfApplication = dateOfApplication;
		this.issSiteCode = issSiteCode;
		this.transactionType = transactionType;
		this.transactionSubtype = transactionSubtype;		
		this.regSiteCode = regSiteCode;
		this.numOfTxnRecieved = numOfTxnRecieved;
		this.numOfApproveTxn = numOfApproveTxn;
		this.numOfRejectTxn = numOfRejectTxn;
		this.numOfPendingInvestTxn = numOfPendingInvestTxn;
		this.numOfPendingProcessTxn = numOfPendingProcessTxn;
		this.numOfErrorProcessTxn = numOfErrorProcessTxn;
		this.numOfTotalTxns = numOfTotalTxns;
		this.createDate = createDate;
		this.updateDate = updateDate;
	}

	

	public String getRegSiteCode() {
		return this.regSiteCode;
	}

	public void setRegSiteCode(String regSiteCode) {
		this.regSiteCode = regSiteCode;
	}

	public Long getNumOfTxnRecieved() {
		return this.numOfTxnRecieved;
	}

	public void setNumOfTxnRecieved(Long numOfTxnRecieved) {
		this.numOfTxnRecieved = numOfTxnRecieved;
	}

	public Long getNumOfApproveTxn() {
		return this.numOfApproveTxn;
	}

	public void setNumOfApproveTxn(Long numOfApproveTxn) {
		this.numOfApproveTxn = numOfApproveTxn;
	}

	public Long getNumOfRejectTxn() {
		return this.numOfRejectTxn;
	}

	public void setNumOfRejectTxn(Long numOfRejectTxn) {
		this.numOfRejectTxn = numOfRejectTxn;
	}

	public Long getNumOfPendingInvestTxn() {
		return this.numOfPendingInvestTxn;
	}

	public void setNumOfPendingInvestTxn(Long numOfPendingInvestTxn) {
		this.numOfPendingInvestTxn = numOfPendingInvestTxn;
	}

	public Long getNumOfPendingProcessTxn() {
		return this.numOfPendingProcessTxn;
	}

	public void setNumOfPendingProcessTxn(Long numOfPendingProcessTxn) {
		this.numOfPendingProcessTxn = numOfPendingProcessTxn;
	}

	public Long getNumOfErrorProcessTxn() {
		return this.numOfErrorProcessTxn;
	}

	public void setNumOfErrorProcessTxn(Long numOfErrorProcessTxn) {
		this.numOfErrorProcessTxn = numOfErrorProcessTxn;
	}

	public Long getNumOfTotalTxns() {
		return this.numOfTotalTxns;
	}

	public void setNumOfTotalTxns(Long numOfTotalTxns) {
		this.numOfTotalTxns = numOfTotalTxns;
	}

	public Date getCreateDate() {
		return this.createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public Date getUpdateDate() {
		return this.updateDate;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

	public Date getDateOfApplication() {
		return dateOfApplication;
	}

	public void setDateOfApplication(Date dateOfApplication) {
		this.dateOfApplication = dateOfApplication;
	}

	public String getIssSiteCode() {
		return issSiteCode;
	}

	public void setIssSiteCode(String issSiteCode) {
		this.issSiteCode = issSiteCode;
	}

	public String getTransactionType() {
		return transactionType;
	}

	public void setTransactionType(String transactionType) {
		this.transactionType = transactionType;
	}

	public String getTransactionSubtype() {
		return transactionSubtype;
	}

	public void setTransactionSubtype(String transactionSubtype) {
		this.transactionSubtype = transactionSubtype;
	}
	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof NicTransactionsStatistics))
			return false;
		NicTransactionsStatistics castOther = (NicTransactionsStatistics) other;

		return ((this.getDateOfApplication() == castOther
				.getDateOfApplication()) || (this.getDateOfApplication() != null
				&& castOther.getDateOfApplication() != null && this
				.getDateOfApplication()
				.equals(castOther.getDateOfApplication())))
				&& ((this.getIssSiteCode() == castOther.getIssSiteCode()) || (this
						.getIssSiteCode() != null
						&& castOther.getIssSiteCode() != null && this
						.getIssSiteCode().equals(castOther.getIssSiteCode())))
				&& ((this.getTransactionType() == castOther
						.getTransactionType()) || (this.getTransactionType() != null
						&& castOther.getTransactionType() != null && this
						.getTransactionType().equals(
								castOther.getTransactionType())))
				&& ((this.getTransactionSubtype() == castOther
						.getTransactionSubtype()) || (this
						.getTransactionSubtype() != null
						&& castOther.getTransactionSubtype() != null && this
						.getTransactionSubtype().equals(
								castOther.getTransactionSubtype())));
	}

	public int hashCode() {
		int result = 17;

		result = 37
				* result
				+ (getDateOfApplication() == null ? 0 : this
						.getDateOfApplication().hashCode());
		result = 37
				* result
				+ (getIssSiteCode() == null ? 0 : this.getIssSiteCode()
						.hashCode());
		result = 37
				* result
				+ (getTransactionType() == null ? 0 : this.getTransactionType()
						.hashCode());
		result = 37
				* result
				+ (getTransactionSubtype() == null ? 0 : this
						.getTransactionSubtype().hashCode());
		return result;
	}


}
