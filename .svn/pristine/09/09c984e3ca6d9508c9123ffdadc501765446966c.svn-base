package service.transaction.model;

import java.util.Date;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import org.codehaus.jackson.map.annotate.JsonSerialize;

import com.nec.asia.nic.util.DateHandler;

@XmlRootElement(name="TransactionLost")
@XmlType(name="TransactionLost", propOrder={"transactionId", "name", "nin", "dob", "passportNo", "dateOfIssue", "dateOfExpiry", "placeIssue", "reason", "note", "siteCode", "pob"})
public class TransactionLost {
	
	private String transactionId;
	private String name;
	private String nin;
	@JsonSerialize(using=DateHandler.class)
	private Date dob;
	private String passportNo;
	@JsonSerialize(using=DateHandler.class)
	private Date dateOfIssue;
	@JsonSerialize(using=DateHandler.class)
	private Date dateOfExpiry;
	private String placeIssue;
	private String reason;
	private String note;
	private String siteCode;
	private String pob;
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
	public String getNin() {
		return nin;
	}
	public void setNin(String nin) {
		this.nin = nin;
	}
	public Date getDob() {
		return dob;
	}
	public void setDob(Date dob) {
		this.dob = dob;
	}
	public String getPassportNo() {
		return passportNo;
	}
	public void setPassportNo(String passportNo) {
		this.passportNo = passportNo;
	}
	public Date getDateOfIssue() {
		return dateOfIssue;
	}
	public void setDateOfIssue(Date dateOfIssue) {
		this.dateOfIssue = dateOfIssue;
	}
	public Date getDateOfExpiry() {
		return dateOfExpiry;
	}
	public void setDateOfExpiry(Date dateOfExpiry) {
		this.dateOfExpiry = dateOfExpiry;
	}
	public String getPlaceIssue() {
		return placeIssue;
	}
	public void setPlaceIssue(String placeIssue) {
		this.placeIssue = placeIssue;
	}
	public String getReason() {
		return reason;
	}
	public void setReason(String reason) {
		this.reason = reason;
	}
	public String getNote() {
		return note;
	}
	public void setNote(String note) {
		this.note = note;
	}
	public String getSiteCode() {
		return siteCode;
	}
	public void setSiteCode(String siteCode) {
		this.siteCode = siteCode;
	}
	public String getPob() {
		return pob;
	}
	public void setPob(String pob) {
		this.pob = pob;
	}
	
	
}
