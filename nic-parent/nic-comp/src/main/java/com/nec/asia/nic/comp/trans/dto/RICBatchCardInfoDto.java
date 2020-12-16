package com.nec.asia.nic.comp.trans.dto;


import java.util.ArrayList;
import java.util.Date;

public class RICBatchCardInfoDto {
	private String batchId;
	private String txnNo;
	private String ccNo;
	private String txnType;
	private String txnSubType;
	private String cardStatus;
	private String cardIssueDate;
	private String gender;
	private String rptFrmt;
	private String nin;
	private String firstName;
	private String surName;
	private String rptFormat;
	private String regOfficerId;
	private String serialNo;
	private String ricOffice;
	private String batchDate;
	private String batchEndDate;
	private String cardExpiryDate;
	private String cardDispatchDate;
	private String batchDownloadedDate;
	private String startDate;
	private String cardIssueEndDate;
	private String cardExpiryEndDate;
	private String cardDispatchEndDate;
	private String cardBatchEndDate;
	private ArrayList<String> selColumns;
	private String txnStartDate;
	private String txnEndDate;
	private String packageDate;
	private String applicationDate;
	private String cardPkgStartDate;
	private String cardPkgEndDate;
	private String pkgSeqNo; 
	private Date transStartDate;
	private Date transEndDate;
	private Date crdPkgStartDate;
	private Date crdPkgEndDate;
	private Date crdIssueStartDate;
	private Date crdIssueEndDate;
	private Date crdExpiryStartDate;
	private Date crdExpiryEndDate;
	
	public String getBatchId() {
		return batchId;
	}

	public void setBatchId(String batchId) {
		this.batchId = batchId;
	}

	public String getTxnNo() {
		return txnNo;
	}

	public void setTxnNo(String txnNo) {
		this.txnNo = txnNo;
	}

	public String getCcNo() {
		return ccNo;
	}

	public void setCcNo(String ccNo) {
		this.ccNo = ccNo;
	}

	public String getCardStatus() {
		return cardStatus;
	}

	public void setCardStatus(String cardStatus) {
		this.cardStatus = cardStatus;
	}

	public String getCardIssueDate() {
		return cardIssueDate;
	}

	public void setCardIssueDate(String cardIssueDate) {
		this.cardIssueDate = cardIssueDate;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getRptFrmt() {
		return rptFrmt;
	}

	public void setRptFrmt(String rptFrmt) {
		this.rptFrmt = rptFrmt;
	}

	public String getNin() {
		return nin;
	}

	public void setNin(String nin) {
		this.nin = nin;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getSurName() {
		return surName;
	}

	public void setSurName(String surName) {
		this.surName = surName;
	}

	public String getRptFormat() {
		return rptFormat;
	}

	public void setRptFormat(String rptFormat) {
		this.rptFormat = rptFormat;
	}

	public String getRegOfficerId() {
		return regOfficerId;
	}

	public void setRegOfficerId(String regOfficerId) {
		this.regOfficerId = regOfficerId;
	}

	public String getSerialNo() {
		return serialNo;
	}

	public void setSerialNo(String serialNo) {
		this.serialNo = serialNo;
	}

	public ArrayList<String> getSelColumns() {
		return selColumns;
	}

	public void setSelColumns(ArrayList<String> selColumns) {
		this.selColumns = selColumns;
	}

	public String getBatchDate() {
		return batchDate;
	}

	public String getCardExpiryDate() {
		return cardExpiryDate;
	}

	public void setCardExpiryDate(String cardExpiryDate) {
		this.cardExpiryDate = cardExpiryDate;
	}

	public String getCardDispatchDate() {
		return cardDispatchDate;
	}

	public void setCardDispatchDate(String cardDispatchDate) {
		this.cardDispatchDate = cardDispatchDate;
	}

	public void setBatchDate(String batchDate) {
		this.batchDate = batchDate;
	}

	public String getRicOffice() {
		return ricOffice;
	}

	public void setRicOffice(String ricOffice) {
		this.ricOffice = ricOffice;
	}

	public String getBatchDownloadedDate() {
		return batchDownloadedDate;
	}

	public void setBatchDownloadedDate(String batchDownloadedDate) {
		this.batchDownloadedDate = batchDownloadedDate;
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getBatchEndDate() {
		return batchEndDate;
	}

	public void setBatchEndDate(String batchEndDate) {
		this.batchEndDate = batchEndDate;
	}

	public String getCardIssueEndDate() {
		return cardIssueEndDate;
	}

	public void setCardIssueEndDate(String cardIssueEndDate) {
		this.cardIssueEndDate = cardIssueEndDate;
	}

	public String getCardExpiryEndDate() {
		return cardExpiryEndDate;
	}

	public void setCardExpiryEndDate(String cardExpiryEndDate) {
		this.cardExpiryEndDate = cardExpiryEndDate;
	}

	public String getTxnType() {
		return txnType;
	}

	public void setTxnType(String txnType) {
		this.txnType = txnType;
	}

	public String getCardDispatchEndDate() {
		return cardDispatchEndDate;
	}

	public void setCardDispatchEndDate(String cardDispatchEndDate) {
		this.cardDispatchEndDate = cardDispatchEndDate;
	}

	public String getCardBatchEndDate() {
		return cardBatchEndDate;
	}

	public void setCardBatchEndDate(String cardBatchEndDate) {
		this.cardBatchEndDate = cardBatchEndDate;
	}

	public String getTxnSubType() {
		return txnSubType;
	}

	public void setTxnSubType(String txnSubType) {
		this.txnSubType = txnSubType;
	}

	public String getTxnStartDate() {
		return txnStartDate;
	}

	public void setTxnStartDate(String txnStartDate) {
		this.txnStartDate = txnStartDate;
	}

	public String getTxnEndDate() {
		return txnEndDate;
	}

	public void setTxnEndDate(String txnEndDate) {
		this.txnEndDate = txnEndDate;
	}

	public String getPackageDate() {
		return packageDate;
	}

	public void setPackageDate(String packageDate) {
		this.packageDate = packageDate;
	}

	public String getApplicationDate() {
		return applicationDate;
	}

	public void setApplicationDate(String applicationDate) {
		this.applicationDate = applicationDate;
	}

	public String getCardPkgStartDate() {
		return cardPkgStartDate;
	}

	public void setCardPkgStartDate(String cardPkgStartDate) {
		this.cardPkgStartDate = cardPkgStartDate;
	}

	public String getCardPkgEndDate() {
		return cardPkgEndDate;
	}

	public void setCardPkgEndDate(String cardPkgEndDate) {
		this.cardPkgEndDate = cardPkgEndDate;
	}

	public String getPkgSeqNo() {
		return pkgSeqNo;
	}

	public void setPkgSeqNo(String pkgSeqNo) {
		this.pkgSeqNo = pkgSeqNo;
	}

	public Date getTransStartDate() {
		return transStartDate;
	}

	public void setTransStartDate(Date transStartDate) {
		this.transStartDate = transStartDate;
	}

	public Date getTransEndDate() {
		return transEndDate;
	}

	public void setTransEndDate(Date transEndDate) {
		this.transEndDate = transEndDate;
	}

	public Date getCrdPkgStartDate() {
		return crdPkgStartDate;
	}

	public void setCrdPkgStartDate(Date crdPkgStartDate) {
		this.crdPkgStartDate = crdPkgStartDate;
	}

	public Date getCrdPkgEndDate() {
		return crdPkgEndDate;
	}

	public void setCrdPkgEndDate(Date crdPkgEndDate) {
		this.crdPkgEndDate = crdPkgEndDate;
	}

	public Date getCrdIssueStartDate() {
		return crdIssueStartDate;
	}

	public void setCrdIssueStartDate(Date crdIssueStartDate) {
		this.crdIssueStartDate = crdIssueStartDate;
	}

	public Date getCrdIssueEndDate() {
		return crdIssueEndDate;
	}

	public void setCrdIssueEndDate(Date crdIssueEndDate) {
		this.crdIssueEndDate = crdIssueEndDate;
	}

	public Date getCrdExpiryStartDate() {
		return crdExpiryStartDate;
	}

	public void setCrdExpiryStartDate(Date crdExpiryStartDate) {
		this.crdExpiryStartDate = crdExpiryStartDate;
	}

	public Date getCrdExpiryEndDate() {
		return crdExpiryEndDate;
	}

	public void setCrdExpiryEndDate(Date crdExpiryEndDate) {
		this.crdExpiryEndDate = crdExpiryEndDate;
	}
	
	
}