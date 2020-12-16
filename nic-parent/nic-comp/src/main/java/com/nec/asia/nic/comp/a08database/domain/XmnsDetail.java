package com.nec.asia.nic.comp.a08database.domain;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement(name = "XmnsDetail")
@XmlType(name = "XmnsDetail", propOrder = { "personCode", "receiptNo",
		"ioDocCode", "ioDocDateOfIssue", "ioDocPlaceOfIssue",
		"residentPlaceName", "residentAddress", "residentCountry",
		"exportReason", "passportReqReason", "description", "documentResult",
		"blInvestBy", "blInvestDate", "modifyInfoBy", "modifyInfoDate",
		"requestType", "documentType", "vrfDocumentNo", "vrfDocumentDate",
		"cancelPpListCode", "verifiedResult", "aListCode", "aListDate",
		"apprvListCode", "apprvListDate",
		"documentStatus", "dsqDocCode", "dsqDocDate", "a27DocCode",
		"approveBy", "approveDate", "name", "dob", "gender", "placeOfBirth",
		"addressOfBirth", "ethnic", "receiptDate", "createdBy", "createdDate",
		"documentNo", "documentDate", "sentOfficeName", "docNo",
		"restTmpCountryName", "localResponseDocNo", "localResponseDate",
		"rootPersonMapDate", "updatedBy", "updatedDate", "family", "hoSoId", "ioDocType" })
public class XmnsDetail {
	private String personCode;
	private String receiptNo;
	private String ioDocCode;
	private String ioDocDateOfIssue;
	private String ioDocPlaceOfIssue;
	private String residentPlaceName;
	private String residentAddress;
	private String residentCountry;
	private String exportReason;
	private String passportReqReason;
	private String description;
	private String documentResult;
	private String blInvestBy;
	private String blInvestDate;
	private String modifyInfoBy;
	private String modifyInfoDate;
	private String requestType;
	private String documentType;
	private String vrfDocumentNo;
	private String vrfDocumentDate;
	private String cancelPpListCode;
	private String verifiedResult;
	private String aListCode;
	private String aListDate;
	private String apprvListCode;
	private String apprvListDate;
	private String documentStatus;
	private String dsqDocCode;
	private String dsqDocDate;
	private String a27DocCode;
	private String approveBy;
	private String approveDate;

	private String name;
	private String dob;
	private String gender;
	private String placeOfBirth;
	private String addressOfBirth;
	private String ethnic;
	private String receiptDate;
	private String createdBy;
	private String createdDate;
	private String documentNo;
	private String documentDate;
	private String sentOfficeName;
	private String docNo;
	private String restTmpCountryName;
	private String localResponseDocNo;
	private String localResponseDate;
	private String rootPersonMapDate;
	private String updatedBy;
	private String updatedDate;
	private Double hoSoId;
	private String ioDocType;
	private List<PersonFamily> family;
	

	public String getIoDocType() {
		return ioDocType;
	}

	public void setIoDocType(String ioDocType) {
		this.ioDocType = ioDocType;
	}

	public Double getHoSoId() {
		return hoSoId;
	}

	public void setHoSoId(Double hoSoId) {
		this.hoSoId = hoSoId;
	}

	public List<PersonFamily> getFamily() {
		return family;
	}

	public void setFamily(List<PersonFamily> family) {
		this.family = family;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDob() {
		return dob;
	}

	public void setDob(String dob) {
		this.dob = dob;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getPlaceOfBirth() {
		return placeOfBirth;
	}

	public void setPlaceOfBirth(String placeOfBirth) {
		this.placeOfBirth = placeOfBirth;
	}

	public String getAddressOfBirth() {
		return addressOfBirth;
	}

	public void setAddressOfBirth(String addressOfBirth) {
		this.addressOfBirth = addressOfBirth;
	}

	public String getEthnic() {
		return ethnic;
	}

	public void setEthnic(String ethnic) {
		this.ethnic = ethnic;
	}

	public String getReceiptDate() {
		return receiptDate;
	}

	public void setReceiptDate(String receiptDate) {
		this.receiptDate = receiptDate;
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

	public String getDocNo() {
		return docNo;
	}

	public void setDocNo(String docNo) {
		this.docNo = docNo;
	}

	public String getRestTmpCountryName() {
		return restTmpCountryName;
	}

	public void setRestTmpCountryName(String restTmpCountryName) {
		this.restTmpCountryName = restTmpCountryName;
	}

	public String getLocalResponseDocNo() {
		return localResponseDocNo;
	}

	public void setLocalResponseDocNo(String localResponseDocNo) {
		this.localResponseDocNo = localResponseDocNo;
	}

	public String getLocalResponseDate() {
		return localResponseDate;
	}

	public void setLocalResponseDate(String localResponseDate) {
		this.localResponseDate = localResponseDate;
	}

	public String getRootPersonMapDate() {
		return rootPersonMapDate;
	}

	public void setRootPersonMapDate(String rootPersonMapDate) {
		this.rootPersonMapDate = rootPersonMapDate;
	}

	public String getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}

	public String getUpdatedDate() {
		return updatedDate;
	}

	public void setUpdatedDate(String updatedDate) {
		this.updatedDate = updatedDate;
	}

	public String getPersonCode() {
		return personCode;
	}

	public void setPersonCode(String personCode) {
		this.personCode = personCode;
	}

	public String getReceiptNo() {
		return receiptNo;
	}

	public void setReceiptNo(String receiptNo) {
		this.receiptNo = receiptNo;
	}

	public String getIoDocCode() {
		return ioDocCode;
	}

	public void setIoDocCode(String ioDocCode) {
		this.ioDocCode = ioDocCode;
	}

	public String getIoDocDateOfIssue() {
		return ioDocDateOfIssue;
	}

	public void setIoDocDateOfIssue(String ioDocDateOfIssue) {
		this.ioDocDateOfIssue = ioDocDateOfIssue;
	}

	public String getIoDocPlaceOfIssue() {
		return ioDocPlaceOfIssue;
	}

	public void setIoDocPlaceOfIssue(String ioDocPlaceOfIssue) {
		this.ioDocPlaceOfIssue = ioDocPlaceOfIssue;
	}

	public String getResidentPlaceName() {
		return residentPlaceName;
	}

	public void setResidentPlaceName(String residentPlaceName) {
		this.residentPlaceName = residentPlaceName;
	}

	public String getResidentAddress() {
		return residentAddress;
	}

	public void setResidentAddress(String residentAddress) {
		this.residentAddress = residentAddress;
	}

	public String getResidentCountry() {
		return residentCountry;
	}

	public void setResidentCountry(String residentCountry) {
		this.residentCountry = residentCountry;
	}

	public String getExportReason() {
		return exportReason;
	}

	public void setExportReason(String exportReason) {
		this.exportReason = exportReason;
	}

	public String getPassportReqReason() {
		return passportReqReason;
	}

	public void setPassportReqReason(String passportReqReason) {
		this.passportReqReason = passportReqReason;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getDocumentResult() {
		return documentResult;
	}

	public void setDocumentResult(String documentResult) {
		this.documentResult = documentResult;
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

	public String getModifyInfoBy() {
		return modifyInfoBy;
	}

	public void setModifyInfoBy(String modifyInfoBy) {
		this.modifyInfoBy = modifyInfoBy;
	}

	public String getModifyInfoDate() {
		return modifyInfoDate;
	}

	public void setModifyInfoDate(String modifyInfoDate) {
		this.modifyInfoDate = modifyInfoDate;
	}

	public String getRequestType() {
		return requestType;
	}

	public void setRequestType(String requestType) {
		this.requestType = requestType;
	}

	public String getDocumentType() {
		return documentType;
	}

	public void setDocumentType(String documentType) {
		this.documentType = documentType;
	}

	public String getVrfDocumentNo() {
		return vrfDocumentNo;
	}

	public void setVrfDocumentNo(String vrfDocumentNo) {
		this.vrfDocumentNo = vrfDocumentNo;
	}

	public String getVrfDocumentDate() {
		return vrfDocumentDate;
	}

	public void setVrfDocumentDate(String vrfDocumentDate) {
		this.vrfDocumentDate = vrfDocumentDate;
	}

	public String getCancelPpListCode() {
		return cancelPpListCode;
	}

	public void setCancelPpListCode(String cancelPpListCode) {
		this.cancelPpListCode = cancelPpListCode;
	}

	public String getVerifiedResult() {
		return verifiedResult;
	}

	public void setVerifiedResult(String verifiedResult) {
		this.verifiedResult = verifiedResult;
	}

	public String getaListCode() {
		return aListCode;
	}

	public void setaListCode(String aListCode) {
		this.aListCode = aListCode;
	}

	public String getaListDate() {
		return aListDate;
	}

	public void setaListDate(String aListDate) {
		this.aListDate = aListDate;
	}

	public String getApprvListCode() {
		return apprvListCode;
	}

	public void setApprvListCode(String apprvListCode) {
		this.apprvListCode = apprvListCode;
	}

	public String getApprvListDate() {
		return apprvListDate;
	}

	public void setApprvListDate(String apprvListDate) {
		this.apprvListDate = apprvListDate;
	}

	public String getDocumentStatus() {
		return documentStatus;
	}

	public void setDocumentStatus(String documentStatus) {
		this.documentStatus = documentStatus;
	}

	public String getDsqDocCode() {
		return dsqDocCode;
	}

	public void setDsqDocCode(String dsqDocCode) {
		this.dsqDocCode = dsqDocCode;
	}

	public String getDsqDocDate() {
		return dsqDocDate;
	}

	public void setDsqDocDate(String dsqDocDate) {
		this.dsqDocDate = dsqDocDate;
	}

	public String getA27DocCode() {
		return a27DocCode;
	}

	public void setA27DocCode(String a27DocCode) {
		this.a27DocCode = a27DocCode;
	}

	public String getApproveBy() {
		return approveBy;
	}

	public void setApproveBy(String approveBy) {
		this.approveBy = approveBy;
	}

	public String getApproveDate() {
		return approveDate;
	}

	public void setApproveDate(String approveDate) {
		this.approveDate = approveDate;
	}

}
