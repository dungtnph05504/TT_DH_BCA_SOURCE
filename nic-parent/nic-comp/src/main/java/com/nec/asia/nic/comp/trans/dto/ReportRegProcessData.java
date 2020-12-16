package com.nec.asia.nic.comp.trans.dto;

public class ReportRegProcessData {
	private int stt;
	private String transactionId;
	private String passportNo;
	private String name;
	private String dateOfBirth;
	private String gender;
	private String nin;
	private String transactionStatus;
	private String documentStatus;
	private String regSiteName;
	private String regDate;
	private String printSiteName;
	private String printDate;
	private String approveStage;
	
	public String getApproveStage() {
		return approveStage;
	}
	public void setApproveStage(String approveStage) {
		this.approveStage = approveStage;
	}
	public String getPassportNo() {
		return passportNo;
	}
	public void setPassportNo(String passportNo) {
		this.passportNo = passportNo;
	}
	public String getDocumentStatus() {
		return documentStatus;
	}
	public void setDocumentStatus(String documentStatus) {
		this.documentStatus = documentStatus;
	}
	public String getPrintSiteName() {
		return printSiteName;
	}
	public void setPrintSiteName(String printSiteName) {
		this.printSiteName = printSiteName;
	}
	public String getPrintDate() {
		return printDate;
	}
	public void setPrintDate(String printDate) {
		this.printDate = printDate;
	}
	public int getStt() {
		return stt;
	}
	public void setStt(int stt) {
		this.stt = stt;
	}
	public String getTransactionId() {
		return transactionId;
	}
	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDateOfBirth() {
		return dateOfBirth;
	}
	public void setDateOfBirth(String dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public String getNin() {
		return nin;
	}
	public void setNin(String nin) {
		this.nin = nin;
	}
	public String getTransactionStatus() {
		return transactionStatus;
	}
	public void setTransactionStatus(String transactionStatus) {
		this.transactionStatus = transactionStatus;
	}
	public String getRegSiteName() {
		return regSiteName;
	}
	public void setRegSiteName(String regSiteName) {
		this.regSiteName = regSiteName;
	}
	public String getRegDate() {
		return regDate;
	}
	public void setRegDate(String regDate) {
		this.regDate = regDate;
	}
}
