package com.nec.asia.nic.investigation.dto;

import java.util.Date;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement(name="Transaction")
@XmlType(name="Transaction", propOrder={"transactionId", "nin", "dateOfApplication", "estDateOfCollection", "passportType", "priority", "regSiteCode", "issSiteCode",
"transactionType", "transactionStatus", "checksum", "isPostOffice", "recieptNo", "registrationNo", "passportStyle", "regisData", "families", "documents", "prevPassportNo", "prevDateOfIssue"})
public class Transaction {
	
	private String transactionId;
	private String nin;
	private Date dateOfApplication;
	private Date estDateOfCollection;
	private String passportType;
	private Integer priority;
	private String regSiteCode;
	private String issSiteCode;
	private String transactionType;
	private String transactionStatus;
	private String checksum;
	private String isPostOffice;
	private String recieptNo;
	private String registrationNo;
	private String passportStyle;
	private String prevPassportNo;
	private Date prevDateOfIssue;
	private RegistrationData regisData;
	private List<PersonFamily> families;
	private List<TransactionDocument> documents;
	
	
	public String getTransactionId() {
		return transactionId;
	}
	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}
	public String getNin() {
		return nin;
	}
	public void setNin(String nin) {
		this.nin = nin;
	}
	public Date getDateOfApplication() {
		return dateOfApplication;
	}
	public void setDateOfApplication(Date dateOfApplication) {
		this.dateOfApplication = dateOfApplication;
	}
	public Date getEstDateOfCollection() {
		return estDateOfCollection;
	}
	public void setEstDateOfCollection(Date estDateOfCollection) {
		this.estDateOfCollection = estDateOfCollection;
	}
	public String getPassportType() {
		return passportType;
	}
	public void setPassportType(String passportType) {
		this.passportType = passportType;
	}
	public Integer getPriority() {
		return priority;
	}
	public void setPriority(Integer priority) {
		this.priority = priority;
	}
	public String getRegSiteCode() {
		return regSiteCode;
	}
	public void setRegSiteCode(String regSiteCode) {
		this.regSiteCode = regSiteCode;
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
	public String getTransactionStatus() {
		return transactionStatus;
	}
	public void setTransactionStatus(String transactionStatus) {
		this.transactionStatus = transactionStatus;
	}
	public String getChecksum() {
		return checksum;
	}
	public void setChecksum(String checksum) {
		this.checksum = checksum;
	}
	public String getIsPostOffice() {
		return isPostOffice;
	}
	public void setIsPostOffice(String isPostOffice) {
		this.isPostOffice = isPostOffice;
	}
	public String getRecieptNo() {
		return recieptNo;
	}
	public void setRecieptNo(String recieptNo) {
		this.recieptNo = recieptNo;
	}
	public String getRegistrationNo() {
		return registrationNo;
	}
	public void setRegistrationNo(String registrationNo) {
		this.registrationNo = registrationNo;
	}
	public String getPassportStyle() {
		return passportStyle;
	}
	public void setPassportStyle(String passportStyle) {
		this.passportStyle = passportStyle;
	}
	public RegistrationData getRegisData() {
		return regisData;
	}
	public void setRegisData(RegistrationData regisData) {
		this.regisData = regisData;
	}
	public List<PersonFamily> getFamilies() {
		return families;
	}
	public void setFamilies(List<PersonFamily> families) {
		this.families = families;
	}
	public List<TransactionDocument> getDocuments() {
		return documents;
	}
	public void setDocuments(List<TransactionDocument> documents) {
		this.documents = documents;
	}
	public String getPrevPassportNo() {
		return prevPassportNo;
	}
	public void setPrevPassportNo(String prevPassportNo) {
		this.prevPassportNo = prevPassportNo;
	}
	public Date getPrevDateOfIssue() {
		return prevDateOfIssue;
	}
	public void setPrevDateOfIssue(Date prevDateOfIssue) {
		this.prevDateOfIssue = prevDateOfIssue;
	}
	
	
}
