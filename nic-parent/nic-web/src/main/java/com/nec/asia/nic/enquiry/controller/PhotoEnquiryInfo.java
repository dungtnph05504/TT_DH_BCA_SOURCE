package com.nec.asia.nic.enquiry.controller;

public class PhotoEnquiryInfo{
	
	private String transactionId;
	private String nin;
//	private String siteCode;
//	private String officerId;
//	private String applicationDate;
//	
	private String capturePhotoStr;
	private String greyPhotoStr;
	private String chipPhotoStr;
	private String cliPhotoStr;
	private String passportNo; // Added by Tue : 19Feb2016
	private String signatureStr;// Added by Tue : 24Feb2016

	
/**
	 * @return the signatureStr
	 */
	public String getSignatureStr() {
		return signatureStr;
	}
	/**
	 * @param signatureStr the signatureStr to set
	 */
	public void setSignatureStr(String signatureStr) {
		this.signatureStr = signatureStr;
	}
/**
	 * @return the passportNo
	 */
	public String getPassportNo() {
		return passportNo;
	}
	/**
	 * @param passportNo the passportNo to set
	 */
	public void setPassportNo(String passportNo) {
		this.passportNo = passportNo;
	}
	//	public String getOfficerId() {
//		return officerId;
//	}
//
//	public void setOfficerId(String officerId) {
//		this.officerId = officerId;
//	}
//
	public String getTransactionId() {
		return this.transactionId;
	}
//
	public String getNin() {
		return nin;
	}

	public void setNin(String nin) {
		this.nin = nin;
	}
//
//	public String getSiteCode() {
//		return siteCode;
//	}
//
//	public void setSiteCode(String siteCode) {
//		this.siteCode = siteCode;
//	}
//
//	public String getApplicationDate() {
//		return applicationDate;
//	}
//
//	public void setApplicationDate(String applicationDate) {
//		this.applicationDate = applicationDate;
//	}

	public String getCapturePhotoStr() {
		return capturePhotoStr;
	}

	public void setCapturePhotoStr(String capturePhotoStr) {
		this.capturePhotoStr = capturePhotoStr;
	}

	public String getGreyPhotoStr() {
		return greyPhotoStr;
	}

	public void setGreyPhotoStr(String greyPhotoStr) {
		this.greyPhotoStr = greyPhotoStr;
	}

	public String getChipPhotoStr() {
		return chipPhotoStr;
	}

	public void setChipPhotoStr(String chipPhotoStr) {
		this.chipPhotoStr = chipPhotoStr;
	}

	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}
	/**
	 * @return the cliPhotoStr
	 */
	public String getCliPhotoStr() {
		return cliPhotoStr;
	}
	/**
	 * @param cliPhotoStr the cliPhotoStr to set
	 */
	public void setCliPhotoStr(String cliPhotoStr) {
		this.cliPhotoStr = cliPhotoStr;
	}
}
