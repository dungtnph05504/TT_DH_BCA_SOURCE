package com.nec.asia.nic.comp.a08database.domain;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement(name = "NhanTlDetail")
@XmlType(name = "NhanTlDetail", propOrder = { "maCaNhan", "name", "dateOfBirth",
		"gender", "placeOfBirth", "addressOfBirth", "expatriateCode",
		"archiveCode", "ethnic", "religion", "hoSoId", "cmBackAddress", "exportReason",
		"residentAddressBfExp", "documentType", "documentNo", "documentDoi",
		"passportNo", "documentCode", "documentDate", "sentOfficeName",
		"expatriateCountry", "ovrseaDocList", "aprvrDocList", "cmBackPreList",
		"cmBackReaList", "cmBackDate", "blInvestBy", "blInvestDate",
		"createdBy", "createdDate", "processStep", "bussDeptCmmt", "notes",
		"verifiedResult", "aprvrCompliment", "verifiedDocNo", "verifiedDocDate",
		"localResponseDocNo", "localResponseDate", "dsqDocCode", "dsqDocDate", 
		"thongHanhNo", "thongHanhDate", "thongHanhNoiCap", "family"})
public class NhanTlDetail {
	private String maCaNhan;
	private String name;
	private String dateOfBirth;
	private String gender;
	private String placeOfBirth;
	private String addressOfBirth;
	private Double hoSoId;
	private String expatriateCode;
	private String archiveCode;
	private String ethnic;
	private String religion;
	private String cmBackAddress;
	private String exportReason;
	private String residentAddressBfExp;
	private String documentType;
	private String documentNo;
	private String documentDoi;
	private String passportNo;
	private String documentCode;
	private String documentDate;
	private String sentOfficeName;
	private String expatriateCountry;
	private String ovrseaDocList;
	private String aprvrDocList;
	private String cmBackPreList;
	private String cmBackReaList;
	private String cmBackDate;
	private String blInvestBy;
	private String blInvestDate;
	private String createdBy;
	private String createdDate;
	private String processStep;
	private String bussDeptCmmt;
	private String notes;
	private String verifiedResult;
	private String aprvrCompliment;

	private String verifiedDocNo;
	private String verifiedDocDate;
	private String localResponseDocNo;
	private String localResponseDate;
	private String dsqDocCode;
	private String dsqDocDate;
	private String thongHanhNo;
	private String thongHanhDate;
	private String thongHanhNoiCap;
	private List<PersonFamily> family;
	
	public List<PersonFamily> getFamily() {
		return family;
	}

	public void setFamily(List<PersonFamily> family) {
		this.family = family;
	}

	public String getVerifiedDocNo() {
		return verifiedDocNo;
	}

	public void setVerifiedDocNo(String verifiedDocNo) {
		this.verifiedDocNo = verifiedDocNo;
	}

	public String getVerifiedDocDate() {
		return verifiedDocDate;
	}

	public void setVerifiedDocDate(String verifiedDocDate) {
		this.verifiedDocDate = verifiedDocDate;
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

	public String getThongHanhNo() {
		return thongHanhNo;
	}

	public void setThongHanhNo(String thongHanhNo) {
		this.thongHanhNo = thongHanhNo;
	}

	public String getThongHanhDate() {
		return thongHanhDate;
	}

	public void setThongHanhDate(String thongHanhDate) {
		this.thongHanhDate = thongHanhDate;
	}

	public String getThongHanhNoiCap() {
		return thongHanhNoiCap;
	}

	public void setThongHanhNoiCap(String thongHanhNoiCap) {
		this.thongHanhNoiCap = thongHanhNoiCap;
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

	public String getCmBackPreList() {
		return cmBackPreList;
	}

	public void setCmBackPreList(String cmBackPreList) {
		this.cmBackPreList = cmBackPreList;
	}

	public String getMaCaNhan() {
		return maCaNhan;
	}

	public void setMaCaNhan(String maCaNhan) {
		this.maCaNhan = maCaNhan;
	}

	public String getExpatriateCode() {
		return expatriateCode;
	}

	public void setExpatriateCode(String expatriateCode) {
		this.expatriateCode = expatriateCode;
	}

	public String getArchiveCode() {
		return archiveCode;
	}

	public void setArchiveCode(String archiveCode) {
		this.archiveCode = archiveCode;
	}

	public String getEthnic() {
		return ethnic;
	}

	public void setEthnic(String ethnic) {
		this.ethnic = ethnic;
	}

	public String getReligion() {
		return religion;
	}

	public void setReligion(String religion) {
		this.religion = religion;
	}

	public String getCmBackAddress() {
		return cmBackAddress;
	}

	public void setCmBackAddress(String cmBackAddress) {
		this.cmBackAddress = cmBackAddress;
	}

	public String getExportReason() {
		return exportReason;
	}

	public void setExportReason(String exportReason) {
		this.exportReason = exportReason;
	}

	public String getResidentAddressBfExp() {
		return residentAddressBfExp;
	}

	public void setResidentAddressBfExp(String residentAddressBfExp) {
		this.residentAddressBfExp = residentAddressBfExp;
	}

	public String getDocumentType() {
		return documentType;
	}

	public void setDocumentType(String documentType) {
		this.documentType = documentType;
	}

	public String getDocumentNo() {
		return documentNo;
	}

	public void setDocumentNo(String documentNo) {
		this.documentNo = documentNo;
	}

	public String getDocumentDoi() {
		return documentDoi;
	}

	public void setDocumentDoi(String documentDoi) {
		this.documentDoi = documentDoi;
	}

	public String getPassportNo() {
		return passportNo;
	}

	public void setPassportNo(String passportNo) {
		this.passportNo = passportNo;
	}

	public String getDocumentCode() {
		return documentCode;
	}

	public void setDocumentCode(String documentCode) {
		this.documentCode = documentCode;
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

	public String getExpatriateCountry() {
		return expatriateCountry;
	}

	public void setExpatriateCountry(String expatriateCountry) {
		this.expatriateCountry = expatriateCountry;
	}

	public String getOvrseaDocList() {
		return ovrseaDocList;
	}

	public void setOvrseaDocList(String ovrseaDocList) {
		this.ovrseaDocList = ovrseaDocList;
	}

	public String getAprvrDocList() {
		return aprvrDocList;
	}

	public void setAprvrDocList(String aprvrDocList) {
		this.aprvrDocList = aprvrDocList;
	}

	public String getCmBackReaList() {
		return cmBackReaList;
	}

	public void setCmBackReaList(String cmBackReaList) {
		this.cmBackReaList = cmBackReaList;
	}

	public String getCmBackDate() {
		return cmBackDate;
	}

	public void setCmBackDate(String cmBackDate) {
		this.cmBackDate = cmBackDate;
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

	public String getProcessStep() {
		return processStep;
	}

	public void setProcessStep(String processStep) {
		this.processStep = processStep;
	}

	public String getBussDeptCmmt() {
		return bussDeptCmmt;
	}

	public void setBussDeptCmmt(String bussDeptCmmt) {
		this.bussDeptCmmt = bussDeptCmmt;
	}

	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

	public String getVerifiedResult() {
		return verifiedResult;
	}

	public void setVerifiedResult(String verifiedResult) {
		this.verifiedResult = verifiedResult;
	}

	public String getAprvrCompliment() {
		return aprvrCompliment;
	}

	public void setAprvrCompliment(String aprvrCompliment) {
		this.aprvrCompliment = aprvrCompliment;
	}

	public NhanTlDetail() {
	}

}
