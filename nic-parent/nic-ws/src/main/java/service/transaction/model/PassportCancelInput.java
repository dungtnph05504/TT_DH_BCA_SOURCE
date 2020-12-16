package service.transaction.model;
/*
 * Đầu vào của service Hủy hộ chiếu.
 */
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement(name = "PassportCancelInput")
@XmlType(name = "PassportCancelInput", propOrder = { "createBy", "reason",
		"officeCode", "dateOfRegister", "diplomaCode", "dateOfDiploma",
		"officeOfDiploma", "signName", "signPosition", "signLevel",
		"approverName", "approverPosition",
		"approverLevel", "note", "dear", "about", "cancelDocuments", 
		"isSendNotification", "isCancelPassportStatus", "isCancelPhysical",
		"name", "gender", "dateOfBirth", "passportNo", "dateOfDocIssue", "dateOfDocExpiry", 
		"transactionId", "placeOfIssueCode", "regSiteCode", "phoiSerialNo", "archiveCode", "nationalityCode", "placeOfBirthCode"})
public class PassportCancelInput {
	private String createBy;
	private String reason;
	private String officeCode;
	private String dateOfRegister;
	private String diplomaCode;
	private String dateOfDiploma;
	private String officeOfDiploma;
	private String signName;
	private String signPosition;
	private String signLevel;
	private String approverName;
	private String approverPosition;
	private String approverLevel;
	private String note;
	private String isSendNotification;
	private String isCancelPassportStatus;
	private String dear;
	private String about;
	private String isCancelPhysical;
	private List<PassportCancelDetail> cancelDocuments;
	private String name;
	private String gender;
	private String dateOfBirth;
	private String passportNo;
	private String dateOfDocIssue;
	private String dateOfDocExpiry;
	private String transactionId;
	private String placeOfIssueCode;
	private String regSiteCode;
	private String phoiSerialNo;
	private String archiveCode;
	private String nationalityCode;
	private String placeOfBirthCode;
	
	public String getPlaceOfIssueCode() {
		return placeOfIssueCode;
	}
	public void setPlaceOfIssueCode(String placeOfIssueCode) {
		this.placeOfIssueCode = placeOfIssueCode;
	}
	public String getPlaceOfBirthCode() {
		return placeOfBirthCode;
	}
	public void setPlaceOfBirthCode(String placeOfBirthCode) {
		this.placeOfBirthCode = placeOfBirthCode;
	}
	public String getArchiveCode() {
		return archiveCode;
	}
	public void setArchiveCode(String archiveCode) {
		this.archiveCode = archiveCode;
	}
	public String getNationalityCode() {
		return nationalityCode;
	}
	public void setNationalityCode(String nationalityCode) {
		this.nationalityCode = nationalityCode;
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
	public String getPassportNo() {
		return passportNo;
	}
	public void setPassportNo(String passportNo) {
		this.passportNo = passportNo;
	}
	public String getDateOfDocIssue() {
		return dateOfDocIssue;
	}
	public void setDateOfDocIssue(String dateOfDocIssue) {
		this.dateOfDocIssue = dateOfDocIssue;
	}
	public String getDateOfDocExpiry() {
		return dateOfDocExpiry;
	}
	public void setDateOfDocExpiry(String dateOfDocExpiry) {
		this.dateOfDocExpiry = dateOfDocExpiry;
	}
	public String getTransactionId() {
		return transactionId;
	}
	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}
	public String getRegSiteCode() {
		return regSiteCode;
	}
	public void setRegSiteCode(String regSiteCode) {
		this.regSiteCode = regSiteCode;
	}
	public String getPhoiSerialNo() {
		return phoiSerialNo;
	}
	public void setPhoiSerialNo(String phoiSerialNo) {
		this.phoiSerialNo = phoiSerialNo;
	}
	public String getIsCancelPhysical() {
		return isCancelPhysical;
	}
	public void setIsCancelPhysical(String isCancelPhysical) {
		this.isCancelPhysical = isCancelPhysical;
	}
	public String getDear() {
		return dear;
	}
	public void setDear(String dear) {
		this.dear = dear;
	}
	public String getAbout() {
		return about;
	}
	public void setAbout(String about) {
		this.about = about;
	}
	public String getIsSendNotification() {
		return isSendNotification;
	}
	public void setIsSendNotification(String isSendNotification) {
		this.isSendNotification = isSendNotification;
	}
	public String getIsCancelPassportStatus() {
		return isCancelPassportStatus;
	}
	public void setIsCancelPassportStatus(String isCancelPassportStatus) {
		this.isCancelPassportStatus = isCancelPassportStatus;
	}
	public String getCreateBy() {
		return createBy;
	}
	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}
	public String getReason() {
		return reason;
	}
	public void setReason(String reason) {
		this.reason = reason;
	}
	public String getOfficeCode() {
		return officeCode;
	}
	public void setOfficeCode(String officeCode) {
		this.officeCode = officeCode;
	}
	public String getDateOfRegister() {
		return dateOfRegister;
	}
	public void setDateOfRegister(String dateOfRegister) {
		this.dateOfRegister = dateOfRegister;
	}
	public String getDiplomaCode() {
		return diplomaCode;
	}
	public void setDiplomaCode(String diplomaCode) {
		this.diplomaCode = diplomaCode;
	}
	public String getDateOfDiploma() {
		return dateOfDiploma;
	}
	public void setDateOfDiploma(String dateOfDiploma) {
		this.dateOfDiploma = dateOfDiploma;
	}
	public String getOfficeOfDiploma() {
		return officeOfDiploma;
	}
	public void setOfficeOfDiploma(String officeOfDiploma) {
		this.officeOfDiploma = officeOfDiploma;
	}
	public String getSignName() {
		return signName;
	}
	public void setSignName(String signName) {
		this.signName = signName;
	}
	public String getSignPosition() {
		return signPosition;
	}
	public void setSignPosition(String signPosition) {
		this.signPosition = signPosition;
	}
	public String getSignLevel() {
		return signLevel;
	}
	public void setSignLevel(String signLevel) {
		this.signLevel = signLevel;
	}
	public String getApproverName() {
		return approverName;
	}
	public void setApproverName(String approverName) {
		this.approverName = approverName;
	}
	public String getApproverPosition() {
		return approverPosition;
	}
	public void setApproverPosition(String approverPosition) {
		this.approverPosition = approverPosition;
	}
	public String getApproverLevel() {
		return approverLevel;
	}
	public void setApproverLevel(String approverLevel) {
		this.approverLevel = approverLevel;
	}
	public String getNote() {
		return note;
	}
	public void setNote(String note) {
		this.note = note;
	}
	public List<PassportCancelDetail> getCancelDocuments() {
		return cancelDocuments;
	}
	public void setCancelDocuments(List<PassportCancelDetail> cancelDocuments) {
		this.cancelDocuments = cancelDocuments;
	}

}
