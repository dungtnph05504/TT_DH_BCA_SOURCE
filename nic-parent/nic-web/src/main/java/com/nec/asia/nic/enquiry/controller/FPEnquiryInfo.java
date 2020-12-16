package com.nec.asia.nic.enquiry.controller;

public class FPEnquiryInfo{
	
	private String transactionId;
	private String nin;
	private String ccn; //Added by Sailaja for FP Issuance Enquiry
	private String cardStatus; //Added by Sailaja for FP Issuance Enquiry
	private String createBy; //Added by Sailaja for FP Issuance Enquiry
	private String createWkstnId; //Added by Sailaja for FP Issuance Enquiry
	private String issuanceBy; //Added by Sailaja for FP Issuance Enquiry
	private String issuanceDate;//Added by Sailaja for FP Issuance Enquiry
	private String issuanceWkstnId;//Added by Sailaja for FP Issuance Enquiry
	private String siteCode;
	private String officerId;
	private String fpPosition;
	private String fpIndicator;
	private String qualityScore;
	private String verificationScore;
	private String applicationDate;
	
	private String fpDataStr;
	
	private String fpSource;
	private String fpVerifyFlag;
	private String matchScore;
	

	public String getIssuanceBy() {
		return issuanceBy;
	}

	public void setIssuanceBy(String issuanceBy) {
		this.issuanceBy = issuanceBy;
	}

	public String getIssuanceDate() {
		return issuanceDate;
	}

	public void setIssuanceDate(String issuanceDate) {
		this.issuanceDate = issuanceDate;
	}

	public String getIssuanceWkstnId() {
		return issuanceWkstnId;
	}

	public void setIssuanceWkstnId(String issuanceWkstnId) {
		this.issuanceWkstnId = issuanceWkstnId;
	}

	public String getOfficerId() {
		return officerId;
	}

	public String getCcn() {
		return ccn;
	}

	public void setCcn(String ccn) {
		this.ccn = ccn;
	}

	public String getCardStatus() {
		return cardStatus;
	}

	public void setCardStatus(String cardStatus) {
		this.cardStatus = cardStatus;
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

	public void setOfficerId(String officerId) {
		this.officerId = officerId;
	}

	public String getTransactionId() {
		return this.transactionId;
	}

	public String getNin() {
		return nin;
	}

	public void setNin(String nin) {
		this.nin = nin;
	}

	public String getSiteCode() {
		return siteCode;
	}

	public void setSiteCode(String siteCode) {
		this.siteCode = siteCode;
	}

	public String getApplicationDate() {
		return applicationDate;
	}

	public void setApplicationDate(String applicationDate) {
		this.applicationDate = applicationDate;
	}

	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}

	/**
	 * @return the fpDataStr
	 */
	public String getFpDataStr() {
		return fpDataStr;
	}

	/**
	 * @param fpDataStr the fpDataStr to set
	 */
	public void setFpDataStr(String fpDataStr) {
		this.fpDataStr = fpDataStr;
	}

	/**
	 * @return the fpPosition
	 */
	public String getFpPosition() {
		return fpPosition;
	}

	/**
	 * @param fpPosition the fpPosition to set
	 */
	public void setFpPosition(String fpPosition) {
		this.fpPosition = fpPosition;
	}

	/**
	 * @return the qualityScore
	 */
	public String getQualityScore() {
		return qualityScore;
	}

	/**
	 * @param qualityScore the qualityScore to set
	 */
	public void setQualityScore(String qualityScore) {
		this.qualityScore = qualityScore;
	}

	/**
	 * @return the verificationScore
	 */
	public String getVerificationScore() {
		return verificationScore;
	}

	/**
	 * @param verificationScore the verificationScore to set
	 */
	public void setVerificationScore(String verificationScore) {
		this.verificationScore = verificationScore;
	}

	/**
	 * @return the fpIndicator
	 */
	public String getFpIndicator() {
		return fpIndicator;
	}

	/**
	 * @param fpIndicator the fpIndicator to set
	 */
	public void setFpIndicator(String fpIndicator) {
		this.fpIndicator = fpIndicator;
	}

	/**
	 * @return the fpSource
	 */
	public String getFpSource() {
		return fpSource;
	}

	/**
	 * @param fpSource the fpSource to set
	 */
	public void setFpSource(String fpSource) {
		this.fpSource = fpSource;
	}

	/**
	 * @return the fpVerifyFlag
	 */
	public String getFpVerifyFlag() {
		return fpVerifyFlag;
	}

	/**
	 * @param fpVerifyFlag the fpVerifyFlag to set
	 */
	public void setFpVerifyFlag(String fpVerifyFlag) {
		this.fpVerifyFlag = fpVerifyFlag;
	}

	/**
	 * @return the matchScore
	 */
	public String getMatchScore() {
		return matchScore;
	}

	/**
	 * @param matchScore the matchScore to set
	 */
	public void setMatchScore(String matchScore) {
		this.matchScore = matchScore;
	}
}
