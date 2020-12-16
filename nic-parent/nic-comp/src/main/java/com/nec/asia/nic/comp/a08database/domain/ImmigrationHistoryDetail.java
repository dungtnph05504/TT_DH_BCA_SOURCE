package com.nec.asia.nic.comp.a08database.domain;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement(name = "ImmigrationHistoryDetail")
@XmlType(name = "ImmigrationHistoryDetail", propOrder = { "immiId", "immiType",
		"fullName", "countryCode", "countryName", "gender",
		"passportExpiredDate", "passportNo", "passportType", "dateOfBirth",
		"visano", "visaType", "flightNo", "flightPath", "flightFrom",
		"flightTo", "reason", "temporaryplace", "borderGate",
		"immigrationDate", "ioGateNo", "nationality", "orgNationality",
		"vnResAddress", "frResAddress", "occupation", "passportPoi",
		"numberOfChild", "visaSymbol", "visaDate", "officeToVisit", "note",
		"gateuserName", "supervisorName", "photo", "placeOfBirth", "visaPlaceOfIssue", "immiNo", "isChecked", "expectedExport", "childs"
})
public class ImmigrationHistoryDetail {

	private Long immiId;
	private String immiType;
	private String fullName;
	private String countryCode;
	private String countryName;
	private String gender;
	private String passportExpiredDate;
	private String passportNo;
	private String passportType;
	private String dateOfBirth;
	private String visano;
	private String visaType;
	private String flightNo;
	private String flightPath;
	private String flightFrom;
	private String flightTo;
	private String reason;
	private String temporaryplace;
	private String borderGate;
	private String immigrationDate;
	private String ioGateNo;
	private String nationality;
	private String orgNationality;
	private String vnResAddress;
	private String frResAddress;
	private String occupation;
	private String passportPoi;
	private int numberOfChild;
	private String visaSymbol;
	private String visaDate;
	private String officeToVisit;
	private String note;
	private String gateuserName;
	private String supervisorName;
	private String photo;
	private String placeOfBirth;
	private String visaPlaceOfIssue;
	private String immiNo;
	private String isChecked;
	private String expectedExport;
	private List<PersonFamily> childs;
	
	public List<PersonFamily> getChilds() {
		return childs;
	}

	public void setChilds(List<PersonFamily> childs) {
		this.childs = childs;
	}

	public String getPlaceOfBirth() {
		return placeOfBirth;
	}

	public void setPlaceOfBirth(String placeOfBirth) {
		this.placeOfBirth = placeOfBirth;
	}

	public String getVisaPlaceOfIssue() {
		return visaPlaceOfIssue;
	}

	public void setVisaPlaceOfIssue(String visaPlaceOfIssue) {
		this.visaPlaceOfIssue = visaPlaceOfIssue;
	}

	public String getImmiNo() {
		return immiNo;
	}

	public void setImmiNo(String immiNo) {
		this.immiNo = immiNo;
	}

	public String getIsChecked() {
		return isChecked;
	}

	public void setIsChecked(String isChecked) {
		this.isChecked = isChecked;
	}

	public String getExpectedExport() {
		return expectedExport;
	}

	public void setExpectedExport(String expectedExport) {
		this.expectedExport = expectedExport;
	}

	public Long getImmiId() {
		return immiId;
	}

	public void setImmiId(Long immiId) {
		this.immiId = immiId;
	}

	public String getImmiType() {
		return immiType;
	}

	public void setImmiType(String immiType) {
		this.immiType = immiType;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public String getCountryCode() {
		return countryCode;
	}

	public void setCountryCode(String countryCode) {
		this.countryCode = countryCode;
	}

	public String getCountryName() {
		return countryName;
	}

	public void setCountryName(String countryName) {
		this.countryName = countryName;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getPassportExpiredDate() {
		return passportExpiredDate;
	}

	public void setPassportExpiredDate(String passportExpiredDate) {
		this.passportExpiredDate = passportExpiredDate;
	}

	public String getPassportNo() {
		return passportNo;
	}

	public void setPassportNo(String passportNo) {
		this.passportNo = passportNo;
	}

	public String getPassportType() {
		return passportType;
	}

	public void setPassportType(String passportType) {
		this.passportType = passportType;
	}

	public String getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(String dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	public String getVisano() {
		return visano;
	}

	public void setVisano(String visano) {
		this.visano = visano;
	}

	public String getVisaType() {
		return visaType;
	}

	public void setVisaType(String visaType) {
		this.visaType = visaType;
	}

	public String getFlightNo() {
		return flightNo;
	}

	public void setFlightNo(String flightNo) {
		this.flightNo = flightNo;
	}

	public String getFlightPath() {
		return flightPath;
	}

	public void setFlightPath(String flightPath) {
		this.flightPath = flightPath;
	}

	public String getFlightFrom() {
		return flightFrom;
	}

	public void setFlightFrom(String flightFrom) {
		this.flightFrom = flightFrom;
	}

	public String getFlightTo() {
		return flightTo;
	}

	public void setFlightTo(String flightTo) {
		this.flightTo = flightTo;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public String getTemporaryplace() {
		return temporaryplace;
	}

	public void setTemporaryplace(String temporaryplace) {
		this.temporaryplace = temporaryplace;
	}

	public String getBorderGate() {
		return borderGate;
	}

	public void setBorderGate(String borderGate) {
		this.borderGate = borderGate;
	}

	public String getImmigrationDate() {
		return immigrationDate;
	}

	public void setImmigrationDate(String immigrationDate) {
		this.immigrationDate = immigrationDate;
	}

	public String getIoGateNo() {
		return ioGateNo;
	}

	public void setIoGateNo(String ioGateNo) {
		this.ioGateNo = ioGateNo;
	}

	public String getNationality() {
		return nationality;
	}

	public void setNationality(String nationality) {
		this.nationality = nationality;
	}

	public String getOrgNationality() {
		return orgNationality;
	}

	public void setOrgNationality(String orgNationality) {
		this.orgNationality = orgNationality;
	}

	public String getVnResAddress() {
		return vnResAddress;
	}

	public void setVnResAddress(String vnResAddress) {
		this.vnResAddress = vnResAddress;
	}

	public String getFrResAddress() {
		return frResAddress;
	}

	public void setFrResAddress(String frResAddress) {
		this.frResAddress = frResAddress;
	}

	public String getOccupation() {
		return occupation;
	}

	public void setOccupation(String occupation) {
		this.occupation = occupation;
	}

	public String getPassportPoi() {
		return passportPoi;
	}

	public void setPassportPoi(String passportPoi) {
		this.passportPoi = passportPoi;
	}

	public int getNumberOfChild() {
		return numberOfChild;
	}

	public void setNumberOfChild(int numberOfChild) {
		this.numberOfChild = numberOfChild;
	}

	public String getVisaSymbol() {
		return visaSymbol;
	}

	public void setVisaSymbol(String visaSymbol) {
		this.visaSymbol = visaSymbol;
	}

	public String getVisaDate() {
		return visaDate;
	}

	public void setVisaDate(String visaDate) {
		this.visaDate = visaDate;
	}

	public String getOfficeToVisit() {
		return officeToVisit;
	}

	public void setOfficeToVisit(String officeToVisit) {
		this.officeToVisit = officeToVisit;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public String getGateuserName() {
		return gateuserName;
	}

	public void setGateuserName(String gateuserName) {
		this.gateuserName = gateuserName;
	}

	public String getSupervisorName() {
		return supervisorName;
	}

	public void setSupervisorName(String supervisorName) {
		this.supervisorName = supervisorName;
	}

	public String getPhoto() {
		return photo;
	}

	public void setPhoto(String photo) {
		this.photo = photo;
	}
}
