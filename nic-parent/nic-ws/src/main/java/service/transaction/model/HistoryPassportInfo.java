package service.transaction.model;

import java.util.Date;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import org.codehaus.jackson.map.annotate.JsonSerialize;

import com.nec.asia.nic.util.DateHandler;



@XmlRootElement(name="HistoryPassportInfo")
@XmlType(name="HistoryPassportInfo", propOrder={"packageId", "transactionId", "typeTransaction", "regSiteCode", "status", "passportNo", "issueDate", "expiryDate", "issueSiteCode", "receiptNo",
		"passportStage", "offerNote", "approveNote", "approveName", "approvePosition", "releaseDate", "receiveDate"})
public class HistoryPassportInfo {
	
	private String packageId;
	private String transactionId;
	private String typeTransaction;
	private String regSiteCode;
	private String status;
	private String passportNo;
	private String issueDate;

	private String expiryDate;
	
	private String receiveDate;
	private String issueSiteCode;
	private String receiptNo;
	private String passportStage;
	private String offerNote;
	private String approveNote;
	private String approveName;
	private String approvePosition;

	private String releaseDate;
	
	public String getPackageId() {
		return packageId;
	}
	public void setPackageId(String packageId) {
		this.packageId = packageId;
	}
	public String getTransactionId() {
		return transactionId;
	}
	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}
	public String getTypeTransaction() {
		return typeTransaction;
	}
	public void setTypeTransaction(String typeTransaction) {
		this.typeTransaction = typeTransaction;
	}
	public String getRegSiteCode() {
		return regSiteCode;
	}
	public void setRegSiteCode(String regSiteCode) {
		this.regSiteCode = regSiteCode;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getPassportNo() {
		return passportNo;
	}
	public void setPassportNo(String passportNo) {
		this.passportNo = passportNo;
	}
	
	public String getIssueSiteCode() {
		return issueSiteCode;
	}
	public void setIssueSiteCode(String issueSiteCode) {
		this.issueSiteCode = issueSiteCode;
	}
	public String getReceiptNo() {
		return receiptNo;
	}
	public void setReceiptNo(String receiptNo) {
		this.receiptNo = receiptNo;
	}
	public String getPassportStage() {
		return passportStage;
	}
	public void setPassportStage(String passportStage) {
		this.passportStage = passportStage;
	}
	public String getOfferNote() {
		return offerNote;
	}
	public void setOfferNote(String offerNote) {
		this.offerNote = offerNote;
	}
	public String getApproveNote() {
		return approveNote;
	}
	public void setApproveNote(String approveNote) {
		this.approveNote = approveNote;
	}
	public String getApproveName() {
		return approveName;
	}
	public void setApproveName(String approveName) {
		this.approveName = approveName;
	}
	public String getApprovePosition() {
		return approvePosition;
	}
	public void setApprovePosition(String approvePosition) {
		this.approvePosition = approvePosition;
	}
	public String getIssueDate() {
		return issueDate;
	}
	public void setIssueDate(String issueDate) {
		this.issueDate = issueDate;
	}
	public String getExpiryDate() {
		return expiryDate;
	}
	public void setExpiryDate(String expiryDate) {
		this.expiryDate = expiryDate;
	}
	public String getReceiveDate() {
		return receiveDate;
	}
	public void setReceiveDate(String receiveDate) {
		this.receiveDate = receiveDate;
	}
	public String getReleaseDate() {
		return releaseDate;
	}
	public void setReleaseDate(String releaseDate) {
		this.releaseDate = releaseDate;
	}
	
	
	
	
}
