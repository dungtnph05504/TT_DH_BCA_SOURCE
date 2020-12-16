package com.nec.asia.nic.investigation.controller;

public class CancelDocumentData {

	String uploadJobId;
	String transactionId;
	String passportNo;

	public String getUploadJobId() {
		return uploadJobId;
	}

	public void setUploadJobId(String uploadJobId) {
		this.uploadJobId = uploadJobId;
	}

	public String getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}

	public String getPassportNo() {
		return passportNo;
	}

	public void setPassportNo(String passportNo) {
		this.passportNo = passportNo;
	}

}
