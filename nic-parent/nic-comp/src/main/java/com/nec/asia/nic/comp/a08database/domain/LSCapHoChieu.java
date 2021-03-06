package com.nec.asia.nic.comp.a08database.domain;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement(name = "LSCapHoChieu")
@XmlType(name = "LSCapHoChieu", propOrder = { "apxPersonCode", "apxOrgPerson",
		"transactionId", "receiptNo", "phoneNo", "residentPlaceName",
		"residentAreaName", "residentAddress", "tmpPlaceName", "tmpAreaName",
		"tmpAddress", "occupation", "officeInfo", "isEpassport", "priority",
		"prevPassportNo", "prevDateOfIssue", "type", "description",
		"officeName", "status", "passportNo", "ppDateOfIssue",
		"ppDateOfExpiry", "ppType", "ppSerialNo", "ppIcaoLine1", "ppIcaoLine2",
		"ppPlaceOfIssue", "ppStatus", "proposalBy", "proposalDate",
		"proposalType", "proposalContent", "proposalAprvrName",
		"proposalAprvrDate", "proposalAprvrContent", "proposalAprvrPstn",
		"deliveryNote", "dateOfDelivery", "deliveryOfficeName", "archiveCode",
		"applicant", "recipient", "cListCode", "cListCrtBy", "cListCrtDate",
		"blInvestBy", "blInvestDate", "createdBy", "createdDate", "documentNo",
		"documentDate", "sentOfficeName", "sentOfficeAddr", "fullName",
		"family", "photo", "dateOfBirth", "gender", "pid", "placeOfIssuePid",
		"bListCode", "bListCrtDate", "bListCrtBy", "note",
		"listAttachments", "aListCode", "aListCrtDate", "aListCrtBy",
		"packNo", "pageNo", "receiveBy", "issueDate", "issueBy", "printDate",
		"placeOfBirth", "addressOfBirth", "ethnic", "hoSoId", "buocXl", "cancelBy",
		"cancelDate", "cancelReason", "dateOfIssuePid", "cancelType", "printName"})
public class LSCapHoChieu {

	private String apxPersonCode;
	private String apxOrgPerson;
	private String transactionId;
	private String receiptNo;
	private String phoneNo;
	private String residentPlaceName;
	private String residentAreaName;
	private String residentAddress;
	private String tmpPlaceName;
	private String tmpAreaName;
	private String tmpAddress;
	private String occupation;
	private String officeInfo;
	private String isEpassport;
	private String priority;
	private String prevPassportNo;
	private String type;
	private String description;
	private String officeName;
	private String status;
	private String passportNo;
	private String ppDateOfIssue;
	private String ppDateOfExpiry;
	private String ppType;
	private String ppSerialNo;
	private String ppIcaoLine1;
	private String ppIcaoLine2;
	private String ppPlaceOfIssue;
	private String ppStatus;
	private String proposalBy;
	private String proposalDate;
	private String proposalType;
	private String proposalContent;
	private String proposalAprvrName;
	private String proposalAprvrDate;
	private String proposalAprvrContent;
	private String proposalAprvrPstn;
	private String deliveryNote;
	private String dateOfDelivery;
	private String deliveryOfficeName;
	private String archiveCode;
	private String applicant;
	private String recipient;
	private String cListCode;
	private String cListCrtBy;
	private String cListCrtDate;
	private String blInvestBy;
	private String blInvestDate;
	private String createdBy;
	private String createdDate;
	private String documentNo;
	private String documentDate;
	private String sentOfficeName;
	private String sentOfficeAddr;
	private String fullName;
	private List<PersonFamily> family;
	private String photo;
	private String dateOfBirth;
	private String gender;
	private String pid;
	private String placeOfIssuePid;
	private String bListCode;
	private String bListCrtDate;
	private String bListCrtBy;
	private String note;
	private List<DecAttachment> listAttachments;
	private String aListCode;
	private String aListCrtDate;
	private String aListCrtBy;
	private String packNo;
	private Long pageNo;
	private String receiveBy;
	private String issueDate;
	private String issueBy;
	private String printDate;
	private String placeOfBirth;
	private String addressOfBirth;
	private String ethnic;
	private String prevDateOfIssue;
	private Double hoSoId;
	private String buocXl;
	private String cancelBy;
	private String cancelDate;
	private String cancelReason;
	private String cancelType;
	private String dateOfIssuePid;
	private String printName;
	
	public String getPrintName() {
		return printName;
	}

	public void setPrintName(String printName) {
		this.printName = printName;
	}

	public String getCancelType() {
		return cancelType;
	}

	public void setCancelType(String cancelType) {
		this.cancelType = cancelType;
	}

	public String getDateOfIssuePid() {
		return dateOfIssuePid;
	}

	public void setDateOfIssuePid(String dateOfIssuePid) {
		this.dateOfIssuePid = dateOfIssuePid;
	}

	public String getCancelBy() {
		return cancelBy;
	}

	public void setCancelBy(String cancelBy) {
		this.cancelBy = cancelBy;
	}

	public String getCancelDate() {
		return cancelDate;
	}

	public void setCancelDate(String cancelDate) {
		this.cancelDate = cancelDate;
	}

	public String getCancelReason() {
		return cancelReason;
	}

	public void setCancelReason(String cancelReason) {
		this.cancelReason = cancelReason;
	}

	public String getBuocXl() {
		return buocXl;
	}

	public void setBuocXl(String buocXl) {
		this.buocXl = buocXl;
	}

	public Double getHoSoId() {
		return hoSoId;
	}

	public void setHoSoId(Double hoSoId) {
		this.hoSoId = hoSoId;
	}

	public String getPackNo() {
		return packNo;
	}

	public String getPlaceOfBirth() {
		return placeOfBirth;
	}

	public void setPlaceOfBirth(String placeOfBirth) {
		this.placeOfBirth = placeOfBirth;
	}

	public String getEthnic() {
		return ethnic;
	}

	public void setEthnic(String ethnic) {
		this.ethnic = ethnic;
	}

	public String getAddressOfBirth() {
		return addressOfBirth;
	}

	public void setAddressOfBirth(String addressOfBirth) {
		this.addressOfBirth = addressOfBirth;
	}

	public String getPrevDateOfIssue() {
		return prevDateOfIssue;
	}

	public void setPrevDateOfIssue(String prevDateOfIssue) {
		this.prevDateOfIssue = prevDateOfIssue;
	}

	public void setPackNo(String packNo) {
		this.packNo = packNo;
	}

	public Long getPageNo() {
		return pageNo;
	}

	public void setPageNo(Long pageNo) {
		this.pageNo = pageNo;
	}

	public String getReceiveBy() {
		return receiveBy;
	}

	public void setReceiveBy(String receiveBy) {
		this.receiveBy = receiveBy;
	}

	public String getIssueDate() {
		return issueDate;
	}

	public void setIssueDate(String issueDate) {
		this.issueDate = issueDate;
	}

	public String getIssueBy() {
		return issueBy;
	}

	public void setIssueBy(String issueBy) {
		this.issueBy = issueBy;
	}

	public String getPrintDate() {
		return printDate;
	}

	public void setPrintDate(String printDate) {
		this.printDate = printDate;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public List<PersonFamily> getFamily() {
		return family;
	}

	public void setFamily(List<PersonFamily> family) {
		this.family = family;
	}

	public String getPhoto() {
		return photo;
	}

	public void setPhoto(String photo) {
		this.photo = photo;
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

	public String getPid() {
		return pid;
	}

	public void setPid(String pid) {
		this.pid = pid;
	}

	public String getPlaceOfIssuePid() {
		return placeOfIssuePid;
	}

	public void setPlaceOfIssuePid(String placeOfIssuePid) {
		this.placeOfIssuePid = placeOfIssuePid;
	}

	public String getbListCode() {
		return bListCode;
	}

	public void setbListCode(String bListCode) {
		this.bListCode = bListCode;
	}

	public String getbListCrtDate() {
		return bListCrtDate;
	}

	public void setbListCrtDate(String bListCrtDate) {
		this.bListCrtDate = bListCrtDate;
	}

	public String getbListCrtBy() {
		return bListCrtBy;
	}

	public void setbListCrtBy(String bListCrtBy) {
		this.bListCrtBy = bListCrtBy;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public List<DecAttachment> getListAttachments() {
		return listAttachments;
	}

	public void setListAttachments(List<DecAttachment> listAttachments) {
		this.listAttachments = listAttachments;
	}

	public String getaListCode() {
		return aListCode;
	}

	public void setaListCode(String aListCode) {
		this.aListCode = aListCode;
	}

	public String getaListCrtDate() {
		return aListCrtDate;
	}

	public void setaListCrtDate(String aListCrtDate) {
		this.aListCrtDate = aListCrtDate;
	}

	public String getaListCrtBy() {
		return aListCrtBy;
	}

	public void setaListCrtBy(String aListCrtBy) {
		this.aListCrtBy = aListCrtBy;
	}

	public String getApxPersonCode() {
		return apxPersonCode;
	}

	public void setApxPersonCode(String apxPersonCode) {
		this.apxPersonCode = apxPersonCode;
	}

	public String getApxOrgPerson() {
		return apxOrgPerson;
	}

	public void setApxOrgPerson(String apxOrgPerson) {
		this.apxOrgPerson = apxOrgPerson;
	}

	public String getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}

	public String getReceiptNo() {
		return receiptNo;
	}

	public void setReceiptNo(String receiptNo) {
		this.receiptNo = receiptNo;
	}

	public String getPhoneNo() {
		return phoneNo;
	}

	public void setPhoneNo(String phoneNo) {
		this.phoneNo = phoneNo;
	}

	public String getResidentPlaceName() {
		return residentPlaceName;
	}

	public void setResidentPlaceName(String residentPlaceName) {
		this.residentPlaceName = residentPlaceName;
	}

	public String getResidentAreaName() {
		return residentAreaName;
	}

	public void setResidentAreaName(String residentAreaName) {
		this.residentAreaName = residentAreaName;
	}

	public String getResidentAddress() {
		return residentAddress;
	}

	public void setResidentAddress(String residentAddress) {
		this.residentAddress = residentAddress;
	}

	public String getTmpPlaceName() {
		return tmpPlaceName;
	}

	public void setTmpPlaceName(String tmpPlaceName) {
		this.tmpPlaceName = tmpPlaceName;
	}

	public String getTmpAreaName() {
		return tmpAreaName;
	}

	public void setTmpAreaName(String tmpAreaName) {
		this.tmpAreaName = tmpAreaName;
	}

	public String getTmpAddress() {
		return tmpAddress;
	}

	public void setTmpAddress(String tmpAddress) {
		this.tmpAddress = tmpAddress;
	}

	public String getOccupation() {
		return occupation;
	}

	public void setOccupation(String occupation) {
		this.occupation = occupation;
	}

	public String getOfficeInfo() {
		return officeInfo;
	}

	public void setOfficeInfo(String officeInfo) {
		this.officeInfo = officeInfo;
	}

	public String getIsEpassport() {
		return isEpassport;
	}

	public void setIsEpassport(String isEpassport) {
		this.isEpassport = isEpassport;
	}

	public String getPriority() {
		return priority;
	}

	public void setPriority(String priority) {
		this.priority = priority;
	}

	public String getPrevPassportNo() {
		return prevPassportNo;
	}

	public void setPrevPassportNo(String prevPassportNo) {
		this.prevPassportNo = prevPassportNo;
	}
	

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getOfficeName() {
		return officeName;
	}

	public void setOfficeName(String officeName) {
		this.officeName = officeName;
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

	public String getPpDateOfIssue() {
		return ppDateOfIssue;
	}

	public void setPpDateOfIssue(String ppDateOfIssue) {
		this.ppDateOfIssue = ppDateOfIssue;
	}

	public String getPpDateOfExpiry() {
		return ppDateOfExpiry;
	}

	public void setPpDateOfExpiry(String ppDateOfExpiry) {
		this.ppDateOfExpiry = ppDateOfExpiry;
	}

	public String getPpType() {
		return ppType;
	}

	public void setPpType(String ppType) {
		this.ppType = ppType;
	}

	public String getPpSerialNo() {
		return ppSerialNo;
	}

	public void setPpSerialNo(String ppSerialNo) {
		this.ppSerialNo = ppSerialNo;
	}

	public String getPpIcaoLine1() {
		return ppIcaoLine1;
	}

	public void setPpIcaoLine1(String ppIcaoLine1) {
		this.ppIcaoLine1 = ppIcaoLine1;
	}

	public String getPpIcaoLine2() {
		return ppIcaoLine2;
	}

	public void setPpIcaoLine2(String ppIcaoLine2) {
		this.ppIcaoLine2 = ppIcaoLine2;
	}

	public String getPpPlaceOfIssue() {
		return ppPlaceOfIssue;
	}

	public void setPpPlaceOfIssue(String ppPlaceOfIssue) {
		this.ppPlaceOfIssue = ppPlaceOfIssue;
	}

	public String getPpStatus() {
		return ppStatus;
	}

	public void setPpStatus(String ppStatus) {
		this.ppStatus = ppStatus;
	}

	public String getProposalBy() {
		return proposalBy;
	}

	public void setProposalBy(String proposalBy) {
		this.proposalBy = proposalBy;
	}

	public String getProposalDate() {
		return proposalDate;
	}

	public void setProposalDate(String proposalDate) {
		this.proposalDate = proposalDate;
	}

	public String getProposalType() {
		return proposalType;
	}

	public void setProposalType(String proposalType) {
		this.proposalType = proposalType;
	}

	public String getProposalContent() {
		return proposalContent;
	}

	public void setProposalContent(String proposalContent) {
		this.proposalContent = proposalContent;
	}

	public String getProposalAprvrName() {
		return proposalAprvrName;
	}

	public void setProposalAprvrName(String proposalAprvrName) {
		this.proposalAprvrName = proposalAprvrName;
	}

	public String getProposalAprvrDate() {
		return proposalAprvrDate;
	}

	public void setProposalAprvrDate(String proposalAprvrDate) {
		this.proposalAprvrDate = proposalAprvrDate;
	}

	public String getProposalAprvrContent() {
		return proposalAprvrContent;
	}

	public void setProposalAprvrContent(String proposalAprvrContent) {
		this.proposalAprvrContent = proposalAprvrContent;
	}

	public String getProposalAprvrPstn() {
		return proposalAprvrPstn;
	}

	public void setProposalAprvrPstn(String proposalAprvrPstn) {
		this.proposalAprvrPstn = proposalAprvrPstn;
	}

	public String getDeliveryNote() {
		return deliveryNote;
	}

	public void setDeliveryNote(String deliveryNote) {
		this.deliveryNote = deliveryNote;
	}

	public String getDateOfDelivery() {
		return dateOfDelivery;
	}

	public void setDateOfDelivery(String dateOfDelivery) {
		this.dateOfDelivery = dateOfDelivery;
	}

	public String getDeliveryOfficeName() {
		return deliveryOfficeName;
	}

	public void setDeliveryOfficeName(String deliveryOfficeName) {
		this.deliveryOfficeName = deliveryOfficeName;
	}

	public String getArchiveCode() {
		return archiveCode;
	}

	public void setArchiveCode(String archiveCode) {
		this.archiveCode = archiveCode;
	}

	public String getApplicant() {
		return applicant;
	}

	public void setApplicant(String applicant) {
		this.applicant = applicant;
	}

	public String getRecipient() {
		return recipient;
	}

	public void setRecipient(String recipient) {
		this.recipient = recipient;
	}

	public String getcListCode() {
		return cListCode;
	}

	public void setcListCode(String cListCode) {
		this.cListCode = cListCode;
	}

	public String getcListCrtBy() {
		return cListCrtBy;
	}

	public void setcListCrtBy(String cListCrtBy) {
		this.cListCrtBy = cListCrtBy;
	}

	public String getcListCrtDate() {
		return cListCrtDate;
	}

	public void setcListCrtDate(String cListCrtDate) {
		this.cListCrtDate = cListCrtDate;
	}

	public String getBlInvestBy() {
		return blInvestBy;
	}

	public void setBlInvestBy(String blInvestBy) {
		this.blInvestBy = blInvestBy;
	}

	public String getBlInvestDate() {
		return blInvestDate;
	}

	public void setBlInvestDate(String blInvestDate) {
		this.blInvestDate = blInvestDate;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public String getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(String createdDate) {
		this.createdDate = createdDate;
	}

	public String getDocumentNo() {
		return documentNo;
	}

	public void setDocumentNo(String documentNo) {
		this.documentNo = documentNo;
	}

	public String getDocumentDate() {
		return documentDate;
	}

	public void setDocumentDate(String documentDate) {
		this.documentDate = documentDate;
	}

	public String getSentOfficeName() {
		return sentOfficeName;
	}

	public void setSentOfficeName(String sentOfficeName) {
		this.sentOfficeName = sentOfficeName;
	}

	public String getSentOfficeAddr() {
		return sentOfficeAddr;
	}

	public void setSentOfficeAddr(String sentOfficeAddr) {
		this.sentOfficeAddr = sentOfficeAddr;
	}
}
