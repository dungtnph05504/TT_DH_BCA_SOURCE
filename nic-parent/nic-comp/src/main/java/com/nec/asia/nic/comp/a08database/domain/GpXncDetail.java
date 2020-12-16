package com.nec.asia.nic.comp.a08database.domain;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement(name = "GpXncDetail")
@XmlType(name = "GpXncDetail", propOrder = { "key", "hoSoId", "receiptNo",
		"documentCode", "orgNationality", "residenceCard", "residenceCardDoi",
		"residenceCardPoi", "createdBy", "createdDate", "purpose",
		"phoneNumber", "blInvestBy", "blInvestDate", "licenseNo",
		"licenseDate", "licenseApprvrName", "licenseApprvrPstn", "licensePoi",
		"licenseExpire", "cListCode", "cListCreatedDate", "cApprvrDate",
		"cApprvrName", "cancelBy", "cancelDate", "cancelReason" })
public class GpXncDetail {

	private String key;
	private String receiptNo;
	private String documentCode;
	private String orgNationality;
	private String residenceCard;
	private String residenceCardDoi;
	private String residenceCardPoi;
	private String createdBy;
	private String createdDate;
	private String purpose;
	private String phoneNumber;
	private String blInvestBy;
	private String blInvestDate;
	private String licenseNo;
	private String licenseDate;
	private String licenseApprvrName;
	private String licenseApprvrPstn;
	private String licensePoi;
	private String licenseExpire;
	private String cListCode;
	private String cListCreatedDate;
	private String cApprvrDate;
	private String cApprvrName;
	private String cancelBy;
	private String cancelDate;
	private String cancelReason;
	
	private Double hoSoId;
	
	public Double getHoSoId() {
		return hoSoId;
	}
	public void setHoSoId(Double hoSoId) {
		this.hoSoId = hoSoId;
	}
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	public String getReceiptNo() {
		return receiptNo;
	}
	public void setReceiptNo(String receiptNo) {
		this.receiptNo = receiptNo;
	}
	public String getDocumentCode() {
		return documentCode;
	}
	public void setDocumentCode(String documentCode) {
		this.documentCode = documentCode;
	}
	public String getOrgNationality() {
		return orgNationality;
	}
	public void setOrgNationality(String orgNationality) {
		this.orgNationality = orgNationality;
	}
	public String getResidenceCard() {
		return residenceCard;
	}
	public void setResidenceCard(String residenceCard) {
		this.residenceCard = residenceCard;
	}
	public String getResidenceCardDoi() {
		return residenceCardDoi;
	}
	public void setResidenceCardDoi(String residenceCardDoi) {
		this.residenceCardDoi = residenceCardDoi;
	}
	public String getResidenceCardPoi() {
		return residenceCardPoi;
	}
	public void setResidenceCardPoi(String residenceCardPoi) {
		this.residenceCardPoi = residenceCardPoi;
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
	public String getPurpose() {
		return purpose;
	}
	public void setPurpose(String purpose) {
		this.purpose = purpose;
	}
	public String getPhoneNumber() {
		return phoneNumber;
	}
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
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
	public String getLicenseNo() {
		return licenseNo;
	}
	public void setLicenseNo(String licenseNo) {
		this.licenseNo = licenseNo;
	}
	public String getLicenseDate() {
		return licenseDate;
	}
	public void setLicenseDate(String licenseDate) {
		this.licenseDate = licenseDate;
	}
	public String getLicenseApprvrName() {
		return licenseApprvrName;
	}
	public void setLicenseApprvrName(String licenseApprvrName) {
		this.licenseApprvrName = licenseApprvrName;
	}
	public String getLicenseApprvrPstn() {
		return licenseApprvrPstn;
	}
	public void setLicenseApprvrPstn(String licenseApprvrPstn) {
		this.licenseApprvrPstn = licenseApprvrPstn;
	}
	public String getLicensePoi() {
		return licensePoi;
	}
	public void setLicensePoi(String licensePoi) {
		this.licensePoi = licensePoi;
	}
	public String getLicenseExpire() {
		return licenseExpire;
	}
	public void setLicenseExpire(String licenseExpire) {
		this.licenseExpire = licenseExpire;
	}
	public String getcListCode() {
		return cListCode;
	}
	public void setcListCode(String cListCode) {
		this.cListCode = cListCode;
	}
	public String getcListCreatedDate() {
		return cListCreatedDate;
	}
	public void setcListCreatedDate(String cListCreatedDate) {
		this.cListCreatedDate = cListCreatedDate;
	}
	public String getcApprvrDate() {
		return cApprvrDate;
	}
	public void setcApprvrDate(String cApprvrDate) {
		this.cApprvrDate = cApprvrDate;
	}
	public String getcApprvrName() {
		return cApprvrName;
	}
	public void setcApprvrName(String cApprvrName) {
		this.cApprvrName = cApprvrName;
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

}
