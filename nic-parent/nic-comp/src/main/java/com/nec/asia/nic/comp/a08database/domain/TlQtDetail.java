package com.nec.asia.nic.comp.a08database.domain;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement(name = "TlQtDetail")
@XmlType(name = "TlQtDetail", propOrder = { "maCaNhan", "name", "dateOfBirth", 
		"gender", "placeOfBirth", "addressOfBirth", "hoSoId", "ethnic", "receiptNo",
		"archiveCode", "documentNo", "documentDate", "sentOfficeName",
		"passportNo", "ppDateOfIssue", "ppDateOfExpiry", "ppPlaceOfIssue",
		"countryName", "restTmpCountryName", "residentAddressBfExp",
		"occupationBfExp", "occupationCurrent", "workAddress",
		"vnNatGiveupDate", "documentResult", "vnNatGiveupReason",
		"exportReason", "exportDate", "comebackReason", "vnNameBfExp",
		"vnNameRequest", "vnChgNameReason", "family" })
public class TlQtDetail {
	private String maCaNhan;
	private String name;
	private String dateOfBirth;
	private String gender;
	private String placeOfBirth;
	private String addressOfBirth;
	private Double hoSoId;
	private String ethnic;
	private String receiptNo;
	private String archiveCode;
	private String documentNo;
	private String documentDate;
	private String sentOfficeName;
	private String passportNo;
	private String ppDateOfIssue;
	private String ppDateOfExpiry;
	private String ppPlaceOfIssue;
	private String countryName;
	private String restTmpCountryName;
	private String residentAddressBfExp;
	private String occupationBfExp;
	private String occupationCurrent;
	private String workAddress;
	private String vnNatGiveupDate;
	private String documentResult;
	private String vnNatGiveupReason;
	private String exportReason;
	private String exportDate;
	private String comebackReason;
	private String vnNameBfExp;
	private String vnNameRequest;
	private String vnChgNameReason;
	private List<PersonFamily> family;

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

	public Double getHoSoId() {
		return hoSoId;
	}

	public void setHoSoId(Double hoSoId) {
		this.hoSoId = hoSoId;
	}

	public String getEthnic() {
		return ethnic;
	}

	public void setEthnic(String ethnic) {
		this.ethnic = ethnic;
	}

	public String getMaCaNhan() {
		return maCaNhan;
	}

	public void setMaCaNhan(String maCaNhan) {
		this.maCaNhan = maCaNhan;
	}

	public String getReceiptNo() {
		return receiptNo;
	}

	public void setReceiptNo(String receiptNo) {
		this.receiptNo = receiptNo;
	}

	public String getArchiveCode() {
		return archiveCode;
	}

	public void setArchiveCode(String archiveCode) {
		this.archiveCode = archiveCode;
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

	public String getPpPlaceOfIssue() {
		return ppPlaceOfIssue;
	}

	public void setPpPlaceOfIssue(String ppPlaceOfIssue) {
		this.ppPlaceOfIssue = ppPlaceOfIssue;
	}

	public String getCountryName() {
		return countryName;
	}

	public void setCountryName(String countryName) {
		this.countryName = countryName;
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

	public String getOccupationBfExp() {
		return occupationBfExp;
	}

	public void setOccupationBfExp(String occupationBfExp) {
		this.occupationBfExp = occupationBfExp;
	}

	public String getOccupationCurrent() {
		return occupationCurrent;
	}

	public void setOccupationCurrent(String occupationCurrent) {
		this.occupationCurrent = occupationCurrent;
	}

	public String getWorkAddress() {
		return workAddress;
	}

	public void setWorkAddress(String workAddress) {
		this.workAddress = workAddress;
	}

	public String getVnNatGiveupDate() {
		return vnNatGiveupDate;
	}

	public void setVnNatGiveupDate(String vnNatGiveupDate) {
		this.vnNatGiveupDate = vnNatGiveupDate;
	}

	public String getDocumentResult() {
		return documentResult;
	}

	public void setDocumentResult(String documentResult) {
		this.documentResult = documentResult;
	}

	public String getVnNatGiveupReason() {
		return vnNatGiveupReason;
	}

	public void setVnNatGiveupReason(String vnNatGiveupReason) {
		this.vnNatGiveupReason = vnNatGiveupReason;
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

	public String getComebackReason() {
		return comebackReason;
	}

	public void setComebackReason(String comebackReason) {
		this.comebackReason = comebackReason;
	}

	public String getVnNameBfExp() {
		return vnNameBfExp;
	}

	public void setVnNameBfExp(String vnNameBfExp) {
		this.vnNameBfExp = vnNameBfExp;
	}

	public String getVnNameRequest() {
		return vnNameRequest;
	}

	public void setVnNameRequest(String vnNameRequest) {
		this.vnNameRequest = vnNameRequest;
	}

	public String getVnChgNameReason() {
		return vnChgNameReason;
	}

	public void setVnChgNameReason(String vnChgNameReason) {
		this.vnChgNameReason = vnChgNameReason;
	}

	public TlQtDetail(String maCaNhan, String receiptNo, String archiveCode,
			String documentNo, String documentDate, String sentOfficeName,
			String passportNo, String ppDateOfIssue, String ppDateOfExpiry,
			String ppPlaceOfIssue, String countryName,
			String restTmpCountryName, String residentAddressBfExp,
			String occupationBfExp, String occupationCurrent,
			String workAddress, String vnNatGiveupDate, String documentResult,
			String vnNatGiveupReason, String exportReason, String exportDate,
			String comebackReason, String vnNameBfExp, String vnNameRequest,
			String vnChgNameReason) {
		this.maCaNhan = maCaNhan;
		this.receiptNo = receiptNo;
		this.archiveCode = archiveCode;
		this.documentNo = documentNo;
		this.documentDate = documentDate;
		this.sentOfficeName = sentOfficeName;
		this.passportNo = passportNo;
		this.ppDateOfIssue = ppDateOfIssue;
		this.ppDateOfExpiry = ppDateOfExpiry;
		this.ppPlaceOfIssue = ppPlaceOfIssue;
		this.countryName = countryName;
		this.restTmpCountryName = restTmpCountryName;
		this.residentAddressBfExp = residentAddressBfExp;
		this.occupationBfExp = occupationBfExp;
		this.occupationCurrent = occupationCurrent;
		this.workAddress = workAddress;
		this.vnNatGiveupDate = vnNatGiveupDate;
		this.documentResult = documentResult;
		this.vnNatGiveupReason = vnNatGiveupReason;
		this.exportReason = exportReason;
		this.exportDate = exportDate;
		this.comebackReason = comebackReason;
		this.vnNameBfExp = vnNameBfExp;
		this.vnNameRequest = vnNameRequest;
		this.vnChgNameReason = vnChgNameReason;
	}

	public TlQtDetail() {
	}

}
