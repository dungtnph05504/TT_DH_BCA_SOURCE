package com.nec.asia.nic.comp.a08database.domain;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement(name = "VkHhDetail")
@XmlType(name = "VkHhDetail", propOrder = { "maCaNhan", "documentNo",
		"documentDate", "sentOfficeName", "hoSoId", "receiptNo", "religion",
		"occupation", "workAddress", "restTmpCountryName",
		"residentAddressBfExp", "ioDocType", "ioDocDateOfIssue",
		"ioDocDateOfExpiry", "ioDocPlaceOfIssue", "vnWorkShortDesc",
		"houseOccpFile", "comebackReason", "description", "sponsorName",
		"sponsorRelationship", "sponsorGender", "sponsorDob",
		"sponsorIdNumber", "sponsorResPlace", "sponsorResAddress",
		"blInvestBy", "blInvestDate", "aproverName", "archiveCode",
		"createdDate", "exportReason", "exportDate", "exportType",
		"sponsorIdDate", "sponsorIdPlace", "sponsorOccupation",
		"sponsorWorkAdress", "status", "aproverDate", "sponsorDate",
		"proposalBy", "proposalDate", "family", "ioDocCode" })
public class VkHhDetail {
	private String maCaNhan;
	private String documentNo;
	private String documentDate;
	private String sentOfficeName;
	private Double hoSoId;
	private String receiptNo;
	private String religion;
	private String occupation;
	private String workAddress;
	private String restTmpCountryName;
	private String residentAddressBfExp;
	private String ioDocCode;
	private String ioDocType;
	private String ioDocDateOfIssue;
	private String ioDocDateOfExpiry;
	private String ioDocPlaceOfIssue;
	private String vnWorkShortDesc;
	private String houseOccpFile;
	private String comebackReason;
	private String description;
	private String sponsorName;
	private String sponsorRelationship;
	private String sponsorGender;
	private String sponsorDob;
	private String sponsorIdNumber;
	private String sponsorResPlace;
	private String sponsorResAddress;
	private String blInvestBy;
	private String blInvestDate;
	private String aproverName;
	private String archiveCode;
	private String createdDate;
	private String exportReason;
	private String exportDate;
	private String exportType;
	private String sponsorIdDate;
	private String sponsorIdPlace;
	private String sponsorOccupation;
	private String sponsorWorkAdress;
	private String status;
	private String aproverDate;
	private String sponsorDate;
	private String proposalBy;
	private String proposalDate;
	
	public String getIoDocCode() {
		return ioDocCode;
	}

	public void setIoDocCode(String ioDocCode) {
		this.ioDocCode = ioDocCode;
	}

	private List<PersonFamily> family;
	
	public List<PersonFamily> getFamily() {
		return family;
	}

	public void setFamily(List<PersonFamily> family) {
		this.family = family;
	}

	public String getSponsorDate() {
		return sponsorDate;
	}

	public void setSponsorDate(String sponsorDate) {
		this.sponsorDate = sponsorDate;
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

	public Double getHoSoId() {
		return hoSoId;
	}

	public void setHoSoId(Double hoSoId) {
		this.hoSoId = hoSoId;
	}

	public String getMaCaNhan() {
		return maCaNhan;
	}

	public void setMaCaNhan(String maCaNhan) {
		this.maCaNhan = maCaNhan;
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

	public String getReceiptNo() {
		return receiptNo;
	}

	public void setReceiptNo(String receiptNo) {
		this.receiptNo = receiptNo;
	}

	public String getReligion() {
		return religion;
	}

	public void setReligion(String religion) {
		this.religion = religion;
	}

	public String getOccupation() {
		return occupation;
	}

	public void setOccupation(String occupation) {
		this.occupation = occupation;
	}

	public String getWorkAddress() {
		return workAddress;
	}

	public void setWorkAddress(String workAddress) {
		this.workAddress = workAddress;
	}

	public String getRestTmpCountryName() {
		return restTmpCountryName;
	}

	public void setRestTmpCountryName(String restTmpCountryName) {
		this.restTmpCountryName = restTmpCountryName;
	}

	public String getResidentAddressBfExp() {
		return residentAddressBfExp;
	}

	public void setResidentAddressBfExp(String residentAddressBfExp) {
		this.residentAddressBfExp = residentAddressBfExp;
	}

	public String getIoDocType() {
		return ioDocType;
	}

	public void setIoDocType(String ioDocType) {
		this.ioDocType = ioDocType;
	}

	public String getIoDocDateOfIssue() {
		return ioDocDateOfIssue;
	}

	public void setIoDocDateOfIssue(String ioDocDateOfIssue) {
		this.ioDocDateOfIssue = ioDocDateOfIssue;
	}

	public String getIoDocDateOfExpiry() {
		return ioDocDateOfExpiry;
	}

	public void setIoDocDateOfExpiry(String ioDocDateOfExpiry) {
		this.ioDocDateOfExpiry = ioDocDateOfExpiry;
	}

	public String getIoDocPlaceOfIssue() {
		return ioDocPlaceOfIssue;
	}

	public void setIoDocPlaceOfIssue(String ioDocPlaceOfIssue) {
		this.ioDocPlaceOfIssue = ioDocPlaceOfIssue;
	}

	public String getVnWorkShortDesc() {
		return vnWorkShortDesc;
	}

	public void setVnWorkShortDesc(String vnWorkShortDesc) {
		this.vnWorkShortDesc = vnWorkShortDesc;
	}

	public String getHouseOccpFile() {
		return houseOccpFile;
	}

	public void setHouseOccpFile(String houseOccpFile) {
		this.houseOccpFile = houseOccpFile;
	}

	public String getComebackReason() {
		return comebackReason;
	}

	public void setComebackReason(String comebackReason) {
		this.comebackReason = comebackReason;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getSponsorName() {
		return sponsorName;
	}

	public void setSponsorName(String sponsorName) {
		this.sponsorName = sponsorName;
	}

	public String getSponsorRelationship() {
		return sponsorRelationship;
	}

	public void setSponsorRelationship(String sponsorRelationship) {
		this.sponsorRelationship = sponsorRelationship;
	}

	public String getSponsorGender() {
		return sponsorGender;
	}

	public void setSponsorGender(String sponsorGender) {
		this.sponsorGender = sponsorGender;
	}

	public String getSponsorDob() {
		return sponsorDob;
	}

	public void setSponsorDob(String sponsorDob) {
		this.sponsorDob = sponsorDob;
	}

	public String getSponsorIdNumber() {
		return sponsorIdNumber;
	}

	public void setSponsorIdNumber(String sponsorIdNumber) {
		this.sponsorIdNumber = sponsorIdNumber;
	}

	public String getSponsorResPlace() {
		return sponsorResPlace;
	}

	public void setSponsorResPlace(String sponsorResPlace) {
		this.sponsorResPlace = sponsorResPlace;
	}

	public String getSponsorResAddress() {
		return sponsorResAddress;
	}

	public void setSponsorResAddress(String sponsorResAddress) {
		this.sponsorResAddress = sponsorResAddress;
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

	public String getAproverName() {
		return aproverName;
	}

	public void setAproverName(String aproverName) {
		this.aproverName = aproverName;
	}

	public String getArchiveCode() {
		return archiveCode;
	}

	public void setArchiveCode(String archiveCode) {
		this.archiveCode = archiveCode;
	}

	public String getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(String createdDate) {
		this.createdDate = createdDate;
	}

	public String getExportReason() {
		return exportReason;
	}

	public void setExportReason(String exportReason) {
		this.exportReason = exportReason;
	}

	public String getExportDate() {
		return exportDate;
	}

	public void setExportDate(String exportDate) {
		this.exportDate = exportDate;
	}

	public String getExportType() {
		return exportType;
	}

	public void setExportType(String exportType) {
		this.exportType = exportType;
	}

	public String getSponsorIdDate() {
		return sponsorIdDate;
	}

	public void setSponsorIdDate(String sponsorIdDate) {
		this.sponsorIdDate = sponsorIdDate;
	}

	public String getSponsorIdPlace() {
		return sponsorIdPlace;
	}

	public void setSponsorIdPlace(String sponsorIdPlace) {
		this.sponsorIdPlace = sponsorIdPlace;
	}

	public String getSponsorOccupation() {
		return sponsorOccupation;
	}

	public void setSponsorOccupation(String sponsorOccupation) {
		this.sponsorOccupation = sponsorOccupation;
	}

	public String getSponsorWorkAdress() {
		return sponsorWorkAdress;
	}

	public void setSponsorWorkAdress(String sponsorWorkAdress) {
		this.sponsorWorkAdress = sponsorWorkAdress;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getAproverDate() {
		return aproverDate;
	}

	public void setAproverDate(String aproverDate) {
		this.aproverDate = aproverDate;
	}

	public VkHhDetail(String maCaNhan, String documentNo, String documentDate,
			String sentOfficeName, String receiptNo, String religion,
			String occupation, String workAddress, String restTmpCountryName,
			String residentAddressBfExp, String ioDocType,
			String ioDocDateOfIssue, String ioDocDateOfExpiry,
			String ioDocPlaceOfIssue, String vnWorkShortDesc,
			String houseOccpFile, String comebackReason, String description,
			String sponsorName, String sponsorRelationship,
			String sponsorGender, String sponsorDob, String sponsorIdNumber,
			String sponsorResPlace, String sponsorResAddress,
			String blInvestBy, String blInvestDate, String aproverName,
			String archiveCode, String createdDate, String exportReason,
			String exportDate, String exportType, String sponsorIdDate,
			String sponsorIdPlace, String sponsorOccupation,
			String sponsorWorkAdress, String status, String aproverDate) {
		this.maCaNhan = maCaNhan;
		this.documentNo = documentNo;
		this.documentDate = documentDate;
		this.sentOfficeName = sentOfficeName;
		this.receiptNo = receiptNo;
		this.religion = religion;
		this.occupation = occupation;
		this.workAddress = workAddress;
		this.restTmpCountryName = restTmpCountryName;
		this.residentAddressBfExp = residentAddressBfExp;
		this.ioDocType = ioDocType;
		this.ioDocDateOfIssue = ioDocDateOfIssue;
		this.ioDocDateOfExpiry = ioDocDateOfExpiry;
		this.ioDocPlaceOfIssue = ioDocPlaceOfIssue;
		this.vnWorkShortDesc = vnWorkShortDesc;
		this.houseOccpFile = houseOccpFile;
		this.comebackReason = comebackReason;
		this.description = description;
		this.sponsorName = sponsorName;
		this.sponsorRelationship = sponsorRelationship;
		this.sponsorGender = sponsorGender;
		this.sponsorDob = sponsorDob;
		this.sponsorIdNumber = sponsorIdNumber;
		this.sponsorResPlace = sponsorResPlace;
		this.sponsorResAddress = sponsorResAddress;
		this.blInvestBy = blInvestBy;
		this.blInvestDate = blInvestDate;
		this.aproverName = aproverName;
		this.archiveCode = archiveCode;
		this.createdDate = createdDate;
		this.exportReason = exportReason;
		this.exportDate = exportDate;
		this.exportType = exportType;
		this.sponsorIdDate = sponsorIdDate;
		this.sponsorIdPlace = sponsorIdPlace;
		this.sponsorOccupation = sponsorOccupation;
		this.sponsorWorkAdress = sponsorWorkAdress;
		this.status = status;
		this.aproverDate = aproverDate;
	}

	public VkHhDetail() {
	}

}
