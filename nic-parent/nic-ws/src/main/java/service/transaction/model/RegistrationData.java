package service.transaction.model;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement(name="RegistrationData")
@XmlType(name="RegistrationData", propOrder={"residentPlaceId", "residentAreaId", "residentAddress", "tmpPlaceId"
, "tmpAreaId", "tmpAddress", "totalFp", "contactNo", "fullName", "searchName", "nationality", "dateOfBirth", "placeOfBirth", "gender", "religion", "nation", 
"addressNin", "dateNin", "styleDob", "surName", "midName", "firstName", "job", "addressCompany", "personCode", 
"numDecision", "nameDepartment", "position","ownerType", "dayDecision", "createByName", "createBy"})
public class RegistrationData {
	private String residentPlaceId;
	private String residentAreaId;
	private String residentAddress;
	private String tmpPlaceId;
	private String tmpAreaId;
	private String tmpAddress;
	private Integer totalFp;
	private String contactNo;
	private String fullName;
	private String searchName;
	private String nationality;
	private String dateOfBirth;
	private String placeOfBirth;
	private String gender;
	private String religion;
	private String nation;
	private String addressNin;
	private String dateNin;
	private String styleDob;
	private String surName;
	private String midName;
	private String firstName;
	private String job;
	private String addressCompany;
	private String personCode;
	private String numDecision;
	private String nameDepartment;
	private String position;
	private String ownerType;
	private String dayDecision;
	private String createByName;
	private String createBy;
	
	public String getCreateBy() {
		return createBy;
	}
	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}
	public String getCreateByName() {
		return createByName;
	}
	public void setCreateByName(String createByName) {
		this.createByName = createByName;
	}
	public String getJob() {
		return job;
	}
	public void setJob(String job) {
		this.job = job;
	}
	public String getAddressCompany() {
		return addressCompany;
	}
	public void setAddressCompany(String addressCompany) {
		this.addressCompany = addressCompany;
	}
	public String getSurName() {
		return surName;
	}
	public void setSurName(String surName) {
		this.surName = surName;
	}
	public String getMidName() {
		return midName;
	}
	public void setMidName(String midName) {
		this.midName = midName;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getResidentPlaceId() {
		return residentPlaceId;
	}
	public void setResidentPlaceId(String residentPlaceId) {
		this.residentPlaceId = residentPlaceId;
	}
	public String getResidentAreaId() {
		return residentAreaId;
	}
	public void setResidentAreaId(String residentAreaId) {
		this.residentAreaId = residentAreaId;
	}
	public String getResidentAddress() {
		return residentAddress;
	}
	public void setResidentAddress(String residentAddress) {
		this.residentAddress = residentAddress;
	}
	public String getTmpPlaceId() {
		return tmpPlaceId;
	}
	public void setTmpPlaceId(String tmpPlaceId) {
		this.tmpPlaceId = tmpPlaceId;
	}
	public String getTmpAreaId() {
		return tmpAreaId;
	}
	public void setTmpAreaId(String tmpAreaId) {
		this.tmpAreaId = tmpAreaId;
	}
	public String getTmpAddress() {
		return tmpAddress;
	}
	public void setTmpAddress(String tmpAddress) {
		this.tmpAddress = tmpAddress;
	}
	public Integer getTotalFp() {
		return totalFp;
	}
	public void setTotalFp(Integer totalFp) {
		this.totalFp = totalFp;
	}
	public String getContactNo() {
		return contactNo;
	}
	public void setContactNo(String contactNo) {
		this.contactNo = contactNo;
	}
	public String getFullName() {
		return fullName;
	}
	public void setFullName(String fullName) {
		this.fullName = fullName;
	}
	public String getSearchName() {
		return searchName;
	}
	public void setSearchName(String searchName) {
		this.searchName = searchName;
	}
	public String getNationality() {
		return nationality;
	}
	public void setNationality(String nationality) {
		this.nationality = nationality;
	}
	
	public String getPlaceOfBirth() {
		return placeOfBirth;
	}
	public void setPlaceOfBirth(String placeOfBirth) {
		this.placeOfBirth = placeOfBirth;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public String getReligion() {
		return religion;
	}
	public void setReligion(String religion) {
		this.religion = religion;
	}
	public String getNation() {
		return nation;
	}
	public void setNation(String nation) {
		this.nation = nation;
	}
	public String getAddressNin() {
		return addressNin;
	}
	public void setAddressNin(String addressNin) {
		this.addressNin = addressNin;
	}
	
	public String getDateOfBirth() {
		return dateOfBirth;
	}
	public void setDateOfBirth(String dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}
	public String getDateNin() {
		return dateNin;
	}
	public void setDateNin(String dateNin) {
		this.dateNin = dateNin;
	}
	public String getStyleDob() {
		return styleDob;
	}
	public void setStyleDob(String styleDob) {
		this.styleDob = styleDob;
	}
	public String getPersonCode() {
		return personCode;
	}
	public void setPersonCode(String personCode) {
		this.personCode = personCode;
	}
	public String getNumDecision() {
		return numDecision;
	}
	public void setNumDecision(String numDecision) {
		this.numDecision = numDecision;
	}
	public String getNameDepartment() {
		return nameDepartment;
	}
	public void setNameDepartment(String nameDepartment) {
		this.nameDepartment = nameDepartment;
	}
	public String getPosition() {
		return position;
	}
	public void setPosition(String position) {
		this.position = position;
	}
	public String getOwnerType() {
		return ownerType;
	}
	public void setOwnerType(String ownerType) {
		this.ownerType = ownerType;
	}
	public String getDayDecision() {
		return dayDecision;
	}
	public void setDayDecision(String dayDecision) {
		this.dayDecision = dayDecision;
	}
	
}
