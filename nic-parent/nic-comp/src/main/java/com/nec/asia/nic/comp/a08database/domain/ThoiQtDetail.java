package com.nec.asia.nic.comp.a08database.domain;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement(name = "ThoiQtDetail")
@XmlType(name = "ThoiQtDetail", propOrder = { "maCaNhan", "name", "dateOfBirth", 
		"gender", "placeOfBirth", "addressOfBirth", "hoSoId", "ethnic", "code",
		"archiveCode", "documentNo", "documentDate", "sentOfficeName",
		"documentCount", "investigateBy", "stateDutyFfl", "exportReason",
		"exportDate", "residentAddress", "aproverName", "aproverPstn",
		"aproverContent", "createdBy", "createdDate", "blInvestBy",
		"blInvestDate", "fatherName", "fatherCountry", "fatherOccupation",
		"fatherAddress", "motherName", "motherCountry", "motherOccupation",
		"motherAddress", "countryHome", "currResidentAddress", "occupation",
		"status", "investigateDocNo", "investigateDate", "responseDocNo",
		"responseDate", "localResponseDocNo", "localResponseDate",
		"viceMinisterDocNo", "viceMinisterDate", "superAproverContent", "notes", "approveDate"
})
public class ThoiQtDetail {
	private String maCaNhan;
	private String name;
	private String dateOfBirth;
	private String gender;
	private String placeOfBirth;
	private String addressOfBirth;
	private Double hoSoId;
	private String ethnic;
	private String code;
	private String archiveCode;
	private String documentNo;
	private String documentDate;
	private String sentOfficeName;
	private String documentCount;
	private String investigateBy;
	private String stateDutyFfl;
	private String exportReason;
	private String exportDate;
	private String residentAddress;
	private String aproverName;
	private String aproverPstn;
	private String aproverContent;
	private String createdBy;
	private String createdDate;
	private String blInvestBy;
	private String blInvestDate;
	private String fatherName;
	private String fatherCountry;
	private String fatherOccupation;
	private String fatherAddress;
	private String motherName;
	private String motherCountry;
	private String motherOccupation;
	private String motherAddress;
	private String countryHome;
	private String currResidentAddress;
	private String occupation;
	private String status;
	private String investigateDocNo;
	private String investigateDate;
	private String responseDocNo;
	private String responseDate;
	private String localResponseDocNo;
	private String localResponseDate;
	private String viceMinisterDocNo;
	private String viceMinisterDate;
	private String superAproverContent;
	private String notes;

	private String approveDate;
	
	public String getApproveDate() {
		return approveDate;
	}

	public void setApproveDate(String approveDate) {
		this.approveDate = approveDate;
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

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
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

	public String getDocumentCount() {
		return documentCount;
	}

	public void setDocumentCount(String documentCount) {
		this.documentCount = documentCount;
	}

	public String getInvestigateBy() {
		return investigateBy;
	}

	public void setInvestigateBy(String investigateBy) {
		this.investigateBy = investigateBy;
	}

	public String getStateDutyFfl() {
		return stateDutyFfl;
	}

	public void setStateDutyFfl(String stateDutyFfl) {
		this.stateDutyFfl = stateDutyFfl;
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

	public String getResidentAddress() {
		return residentAddress;
	}

	public void setResidentAddress(String residentAddress) {
		this.residentAddress = residentAddress;
	}

	public String getAproverName() {
		return aproverName;
	}

	public void setAproverName(String aproverName) {
		this.aproverName = aproverName;
	}

	public String getAproverPstn() {
		return aproverPstn;
	}

	public void setAproverPstn(String aproverPstn) {
		this.aproverPstn = aproverPstn;
	}

	public String getAproverContent() {
		return aproverContent;
	}

	public void setAproverContent(String aproverContent) {
		this.aproverContent = aproverContent;
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

	public String getFatherName() {
		return fatherName;
	}

	public void setFatherName(String fatherName) {
		this.fatherName = fatherName;
	}

	public String getFatherCountry() {
		return fatherCountry;
	}

	public void setFatherCountry(String fatherCountry) {
		this.fatherCountry = fatherCountry;
	}

	public String getFatherOccupation() {
		return fatherOccupation;
	}

	public void setFatherOccupation(String fatherOccupation) {
		this.fatherOccupation = fatherOccupation;
	}

	public String getFatherAddress() {
		return fatherAddress;
	}

	public void setFatherAddress(String fatherAddress) {
		this.fatherAddress = fatherAddress;
	}

	public String getMotherName() {
		return motherName;
	}

	public void setMotherName(String motherName) {
		this.motherName = motherName;
	}

	public String getMotherCountry() {
		return motherCountry;
	}

	public void setMotherCountry(String motherCountry) {
		this.motherCountry = motherCountry;
	}

	public String getMotherOccupation() {
		return motherOccupation;
	}

	public void setMotherOccupation(String motherOccupation) {
		this.motherOccupation = motherOccupation;
	}

	public String getMotherAddress() {
		return motherAddress;
	}

	public void setMotherAddress(String motherAddress) {
		this.motherAddress = motherAddress;
	}

	public String getCountryHome() {
		return countryHome;
	}

	public void setCountryHome(String countryHome) {
		this.countryHome = countryHome;
	}

	public String getCurrResidentAddress() {
		return currResidentAddress;
	}

	public void setCurrResidentAddress(String currResidentAddress) {
		this.currResidentAddress = currResidentAddress;
	}

	public String getOccupation() {
		return occupation;
	}

	public void setOccupation(String occupation) {
		this.occupation = occupation;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getInvestigateDocNo() {
		return investigateDocNo;
	}

	public void setInvestigateDocNo(String investigateDocNo) {
		this.investigateDocNo = investigateDocNo;
	}

	public String getInvestigateDate() {
		return investigateDate;
	}

	public void setInvestigateDate(String investigateDate) {
		this.investigateDate = investigateDate;
	}

	public String getResponseDocNo() {
		return responseDocNo;
	}

	public void setResponseDocNo(String responseDocNo) {
		this.responseDocNo = responseDocNo;
	}

	public String getResponseDate() {
		return responseDate;
	}

	public void setResponseDate(String responseDate) {
		this.responseDate = responseDate;
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

	public String getViceMinisterDocNo() {
		return viceMinisterDocNo;
	}

	public void setViceMinisterDocNo(String viceMinisterDocNo) {
		this.viceMinisterDocNo = viceMinisterDocNo;
	}

	public String getViceMinisterDate() {
		return viceMinisterDate;
	}

	public void setViceMinisterDate(String viceMinisterDate) {
		this.viceMinisterDate = viceMinisterDate;
	}

	public String getSuperAproverContent() {
		return superAproverContent;
	}

	public void setSuperAproverContent(String superAproverContent) {
		this.superAproverContent = superAproverContent;
	}

	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

	public ThoiQtDetail(String maCaNhan, String code, String archiveCode,
			String documentNo, String documentDate, String sentOfficeName,
			String documentCount, String investigateBy, String stateDutyFfl,
			String exportReason, String exportDate, String residentAddress,
			String aproverName, String aproverPstn, String aproverContent,
			String createdBy, String createdDate, String blInvestBy,
			String blInvestDate, String fatherName, String fatherCountry,
			String fatherOccupation, String fatherAddress, String motherName,
			String motherCountry, String motherOccupation,
			String motherAddress, String countryHome,
			String currResidentAddress, String occupation, String status,
			String investigateDocNo, String investigateDate,
			String responseDocNo, String responseDate,
			String localResponseDocNo, String localResponseDate,
			String viceMinisterDocNo, String viceMinisterDate,
			String superAproverContent, String notes) {
		this.maCaNhan = maCaNhan;
		this.code = code;
		this.archiveCode = archiveCode;
		this.documentNo = documentNo;
		this.documentDate = documentDate;
		this.sentOfficeName = sentOfficeName;
		this.documentCount = documentCount;
		this.investigateBy = investigateBy;
		this.stateDutyFfl = stateDutyFfl;
		this.exportReason = exportReason;
		this.exportDate = exportDate;
		this.residentAddress = residentAddress;
		this.aproverName = aproverName;
		this.aproverPstn = aproverPstn;
		this.aproverContent = aproverContent;
		this.createdBy = createdBy;
		this.createdDate = createdDate;
		this.blInvestBy = blInvestBy;
		this.blInvestDate = blInvestDate;
		this.fatherName = fatherName;
		this.fatherCountry = fatherCountry;
		this.fatherOccupation = fatherOccupation;
		this.fatherAddress = fatherAddress;
		this.motherName = motherName;
		this.motherCountry = motherCountry;
		this.motherOccupation = motherOccupation;
		this.motherAddress = motherAddress;
		this.countryHome = countryHome;
		this.currResidentAddress = currResidentAddress;
		this.occupation = occupation;
		this.status = status;
		this.investigateDocNo = investigateDocNo;
		this.investigateDate = investigateDate;
		this.responseDocNo = responseDocNo;
		this.responseDate = responseDate;
		this.localResponseDocNo = localResponseDocNo;
		this.localResponseDate = localResponseDate;
		this.viceMinisterDocNo = viceMinisterDocNo;
		this.viceMinisterDate = viceMinisterDate;
		this.superAproverContent = superAproverContent;
		this.notes = notes;
	}

	public ThoiQtDetail() {
	}

}
