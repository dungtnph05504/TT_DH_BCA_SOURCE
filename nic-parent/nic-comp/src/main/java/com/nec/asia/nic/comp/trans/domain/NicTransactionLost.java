package com.nec.asia.nic.comp.trans.domain;

import java.io.Serializable;
import java.util.Date;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement(name = "NicTransactionLost")
@XmlType(name = "NicTransactionLost", propOrder = { "id", "createdBy",
		"createdDate", "updatedBy", "updatedDate", "officeCode",
		"dateOfRegister", "diplomaCode", "dateOfDiploma",
		"officeOfDiplomaCode", "reason", "approverName", "approverPosition",
		"approverLevel", "note", "transactionId", "passportNo", "personCode",
		"isSendNotification", "isCancelPassportStatus", "docType", "name",
		"searchName", "gender", "dateOfBirth", "placeOfBirthCode",
		"nationalityCode", "dateOfDocIssue", "dateOfDocExpiry",
		"rstApprovalDate", "rstApproverName", "rstApproverPosition",
		"rstApproverLevel", "rstDiplomaCode", "rstDiplomaDate", "rstNote",
		"printer", "sendDiplomaDate", "sendDiplomaCode", "sendSignName",
		"sendSignPosition", "sendSignLevel", "sendOfficeCode", "sendIssue",
		"sendDear", "placeOfIssueCode", "isCancelPhysical", "archiveCode" })
public class NicTransactionLost implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Long id;
	private String createdBy;
	private Date createdDate;
	private String updatedBy;
	private Date updatedDate;
	private String officeCode;
	private Date dateOfRegister;
	private String diplomaCode;
	private Date dateOfDiploma;
	private String officeOfDiplomaCode;
	private String reason;
	private String approverName;
	private String approverPosition;
	private String approverLevel;
	private String note;
	private String transactionId;
	private String passportNo;
	private String personCode;
	private Boolean isSendNotification;
	private Boolean isCancelPassportStatus;
	private String docType;
	private String name;
	private String searchName;
	private String gender;
	private String dateOfBirth;
	private String placeOfBirthCode;
	private String nationalityCode;
	private Date dateOfDocIssue;
	private Date dateOfDocExpiry;
	private String placeOfIssueCode;
	private Date rstApprovalDate;
	private String rstApproverName;
	private String rstApproverPosition;
	private String rstApproverLevel;
	private String rstDiplomaCode;
	private Date rstDiplomaDate;
	private String rstNote;
	private Boolean printer;
	private Date sendDiplomaDate;
	private String sendDiplomaCode;
	private String sendSignName;
	private String sendSignPosition;
	private String sendSignLevel;
	private String sendOfficeCode;
	private String sendIssue;
	private String sendDear;
	private Boolean isCancelPhysical;
	private String archiveCode;

	public String getArchiveCode() {
		return archiveCode;
	}

	public void setArchiveCode(String archiveCode) {
		this.archiveCode = archiveCode;
	}

	public Boolean getIsCancelPhysical() {
		return isCancelPhysical;
	}

	public void setIsCancelPhysical(Boolean isCancelPhysical) {
		this.isCancelPhysical = isCancelPhysical;
	}

	public String getPlaceOfIssueCode() {
		return placeOfIssueCode;
	}

	public void setPlaceOfIssueCode(String placeOfIssueCode) {
		this.placeOfIssueCode = placeOfIssueCode;
	}

	public Boolean getPrinter() {
		return printer;
	}

	public void setPrinter(Boolean printer) {
		this.printer = printer;
	}

	public Date getSendDiplomaDate() {
		return sendDiplomaDate;
	}

	public void setSendDiplomaDate(Date sendDiplomaDate) {
		this.sendDiplomaDate = sendDiplomaDate;
	}

	public String getSendDiplomaCode() {
		return sendDiplomaCode;
	}

	public void setSendDiplomaCode(String sendDiplomaCode) {
		this.sendDiplomaCode = sendDiplomaCode;
	}

	public String getSendSignName() {
		return sendSignName;
	}

	public void setSendSignName(String sendSignName) {
		this.sendSignName = sendSignName;
	}

	public String getSendSignPosition() {
		return sendSignPosition;
	}

	public void setSendSignPosition(String sendSignPosition) {
		this.sendSignPosition = sendSignPosition;
	}

	public String getSendSignLevel() {
		return sendSignLevel;
	}

	public void setSendSignLevel(String sendSignLevel) {
		this.sendSignLevel = sendSignLevel;
	}

	public String getSendOfficeCode() {
		return sendOfficeCode;
	}

	public void setSendOfficeCode(String sendOfficeCode) {
		this.sendOfficeCode = sendOfficeCode;
	}

	public String getSendIssue() {
		return sendIssue;
	}

	public void setSendIssue(String sendIssue) {
		this.sendIssue = sendIssue;
	}

	public String getSendDear() {
		return sendDear;
	}

	public void setSendDear(String sendDear) {
		this.sendDear = sendDear;
	}

	public String getDocType() {
		return docType;
	}

	public void setDocType(String docType) {
		this.docType = docType;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSearchName() {
		return searchName;
	}

	public void setSearchName(String searchName) {
		this.searchName = searchName;
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

	public String getPlaceOfBirthCode() {
		return placeOfBirthCode;
	}

	public void setPlaceOfBirthCode(String placeOfBirthCode) {
		this.placeOfBirthCode = placeOfBirthCode;
	}

	public String getNationalityCode() {
		return nationalityCode;
	}

	public void setNationalityCode(String nationalityCode) {
		this.nationalityCode = nationalityCode;
	}

	public Date getDateOfDocIssue() {
		return dateOfDocIssue;
	}

	public void setDateOfDocIssue(Date dateOfDocIssue) {
		this.dateOfDocIssue = dateOfDocIssue;
	}

	public Date getDateOfDocExpiry() {
		return dateOfDocExpiry;
	}

	public void setDateOfDocExpiry(Date dateOfDocExpiry) {
		this.dateOfDocExpiry = dateOfDocExpiry;
	}

	public Date getRstApprovalDate() {
		return rstApprovalDate;
	}

	public void setRstApprovalDate(Date rstApprovalDate) {
		this.rstApprovalDate = rstApprovalDate;
	}

	public String getRstApproverName() {
		return rstApproverName;
	}

	public void setRstApproverName(String rstApproverName) {
		this.rstApproverName = rstApproverName;
	}

	public String getRstApproverPosition() {
		return rstApproverPosition;
	}

	public void setRstApproverPosition(String rstApproverPosition) {
		this.rstApproverPosition = rstApproverPosition;
	}

	public String getRstApproverLevel() {
		return rstApproverLevel;
	}

	public void setRstApproverLevel(String rstApproverLevel) {
		this.rstApproverLevel = rstApproverLevel;
	}

	public String getRstDiplomaCode() {
		return rstDiplomaCode;
	}

	public void setRstDiplomaCode(String rstDiplomaCode) {
		this.rstDiplomaCode = rstDiplomaCode;
	}

	public Date getRstDiplomaDate() {
		return rstDiplomaDate;
	}

	public void setRstDiplomaDate(Date rstDiplomaDate) {
		this.rstDiplomaDate = rstDiplomaDate;
	}

	public String getRstNote() {
		return rstNote;
	}

	public void setRstNote(String rstNote) {
		this.rstNote = rstNote;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public String getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}

	public Date getUpdatedDate() {
		return updatedDate;
	}

	public void setUpdatedDate(Date updatedDate) {
		this.updatedDate = updatedDate;
	}

	public String getOfficeCode() {
		return officeCode;
	}

	public void setOfficeCode(String officeCode) {
		this.officeCode = officeCode;
	}

	public Date getDateOfRegister() {
		return dateOfRegister;
	}

	public void setDateOfRegister(Date dateOfRegister) {
		this.dateOfRegister = dateOfRegister;
	}

	public String getDiplomaCode() {
		return diplomaCode;
	}

	public void setDiplomaCode(String diplomaCode) {
		this.diplomaCode = diplomaCode;
	}

	public Date getDateOfDiploma() {
		return dateOfDiploma;
	}

	public void setDateOfDiploma(Date dateOfDiploma) {
		this.dateOfDiploma = dateOfDiploma;
	}

	public String getOfficeOfDiplomaCode() {
		return officeOfDiplomaCode;
	}

	public void setOfficeOfDiplomaCode(String officeOfDiplomaCode) {
		this.officeOfDiplomaCode = officeOfDiplomaCode;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
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

	public String getPersonCode() {
		return personCode;
	}

	public void setPersonCode(String personCode) {
		this.personCode = personCode;
	}

	public Boolean getIsSendNotification() {
		return isSendNotification;
	}

	public void setIsSendNotification(Boolean isSendNotification) {
		this.isSendNotification = isSendNotification;
	}

	public Boolean getIsCancelPassportStatus() {
		return isCancelPassportStatus;
	}

	public void setIsCancelPassportStatus(Boolean isCancelPassportStatus) {
		this.isCancelPassportStatus = isCancelPassportStatus;
	}

}
