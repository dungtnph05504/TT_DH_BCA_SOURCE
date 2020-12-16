package service.transaction.model;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement(name = "PassportRestoreDetail")
@XmlType(name = "PassportRestoreDetail", propOrder = { "reason", "name",
		"gender", "dateOfBirth", "nationalityName", "passportNo",
		"dateOfIssue", "placeOfIssue", "dateOfExpiry", "status", "diplomaCode",
		"dateOfDiploma", "dateOfRegister", "officeName", "approverName", "note", "transactionId" })
public class PassportRestoreDetail {
	private String reason;
	private String name;
	private String gender;
	private String dateOfBirth;
	private String nationalityName;
	private String passportNo;
	private String dateOfIssue;
	private String placeOfIssue;
	private String dateOfExpiry;
	private String status;
	private String diplomaCode;
	private String dateOfDiploma;
	private String dateOfRegister;
	private String officeName;
	private String approverName;
	private String note;
	private String transactionId;

	public String getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}

	public String getDiplomaCode() {
		return diplomaCode;
	}

	public void setDiplomaCode(String diplomaCode) {
		this.diplomaCode = diplomaCode;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(String dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	public String getNationalityName() {
		return nationalityName;
	}

	public void setNationalityName(String nationalityName) {
		this.nationalityName = nationalityName;
	}

	public String getPassportNo() {
		return passportNo;
	}

	public void setPassportNo(String passportNo) {
		this.passportNo = passportNo;
	}

	public String getDateOfIssue() {
		return dateOfIssue;
	}

	public void setDateOfIssue(String dateOfIssue) {
		this.dateOfIssue = dateOfIssue;
	}

	public String getPlaceOfIssue() {
		return placeOfIssue;
	}

	public void setPlaceOfIssue(String placeOfIssue) {
		this.placeOfIssue = placeOfIssue;
	}

	public String getDateOfExpiry() {
		return dateOfExpiry;
	}

	public void setDateOfExpiry(String dateOfExpiry) {
		this.dateOfExpiry = dateOfExpiry;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getDateOfDiploma() {
		return dateOfDiploma;
	}

	public void setDateOfDiploma(String dateOfDiploma) {
		this.dateOfDiploma = dateOfDiploma;
	}

	public String getDateOfRegister() {
		return dateOfRegister;
	}

	public void setDateOfRegister(String dateOfRegister) {
		this.dateOfRegister = dateOfRegister;
	}

	public String getOfficeName() {
		return officeName;
	}

	public void setOfficeName(String officeName) {
		this.officeName = officeName;
	}

	public String getApproverName() {
		return approverName;
	}

	public void setApproverName(String approverName) {
		this.approverName = approverName;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

}
