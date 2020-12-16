package service.transaction.model;

import java.util.Date;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import com.nec.asia.nic.comp.trans.domain.EppPerson;
import com.nec.asia.nic.comp.trans.domain.EppPersonAttchmnt;
import com.nec.asia.nic.comp.trans.domain.EppPersonFamily;


@XmlRootElement(name="InfoPerson")
@XmlType(name="InfoPerson", propOrder={"personCode","orgPerson","refId","name","otherName","searchName","gender","dateOfBirth","placeOfBirthCode","placeOfBirthName",
		"idNumber","dateOfIdIssue","placeOfIdIssueName","ethnic","religion","ethnicCode","religionCode","nationalityName","nationalityCode","fatherName","fatherSearchName","motherName",
		"motherSearchName","countryOfBirth","createdBy","createTs","updatedBy","updateTs","isChecked","description","srcOffice","srcDoc","idQueue","photo"})
public class InfoPerson {
	
    private String personCode;
    private String orgPerson;
    private String refId;
    private String name;
	private String otherName;
	private String searchName;
	private String gender;
	private String dateOfBirth;
	private String placeOfBirthCode; 
	private String placeOfBirthName; 
	private String idNumber; 
	private String dateOfIdIssue;
	private String placeOfIdIssueName;  
	private String ethnic; 
	private String religion;
	private String ethnicCode;
	private String religionCode;
	private String nationalityName; 
	private String nationalityCode;
	private String fatherName;
	private String fatherSearchName;
	private String motherName; 
	private String motherSearchName;
	private String countryOfBirth;
	private String createdBy;
	private Date createTs;
	private String updatedBy;
	private Date updateTs;
	private Boolean isChecked;
	private String description;
	private String srcOffice; 
	private String srcDoc;
	private Long idQueue;
	private String photo;

	
	public InfoPerson() {
		super();
	}


	public InfoPerson(EppPerson epp) {
		this.personCode = epp.getPersonCode();
		this.orgPerson = epp.getOrgPerson();
		this.refId = epp.getRefId();
		this.name = epp.getName();
		this.otherName = epp.getOtherName();
		this.searchName = epp.getSearchName();
		this.gender = epp.getGender();
		this.dateOfBirth = epp.getDateOfBirth();
		this.placeOfBirthCode = epp.getPlaceOfBirthCode();
		this.placeOfBirthName = epp.getPlaceOfBirthName();
		this.idNumber = epp.getIdNumber();
		this.dateOfIdIssue = epp.getDateOfIdIssue();
		this.placeOfIdIssueName = epp.getPlaceOfIdIssueName();
		this.ethnic = epp.getEthnic();
		this.religion = epp.getReligion();
		this.ethnicCode = epp.getEthnicCode();
		this.religionCode = epp.getReligionCode();
		this.nationalityName = epp.getNationalityName();
		this.nationalityCode = epp.getNationalityCode();
		this.fatherName = epp.getFatherName();
		this.fatherSearchName = epp.getFatherSearchName();
		this.motherName = epp.getMotherName();
		this.motherSearchName = epp.getMotherSearchName();
		this.countryOfBirth = epp.getCountryOfBirth();
		this.createdBy = epp.getCreatedBy();
		this.createTs = epp.getCreateTs();
		this.updatedBy = epp.getUpdatedBy();
		this.updateTs = epp.getUpdateTs();
		this.isChecked = epp.getIsChecked();
		this.description = epp.getDescription();
		this.srcOffice = epp.getSrcOffice();
		this.srcDoc = epp.getSrcDoc();
	}

	
	
	public Long getIdQueue() {
		return idQueue;
	}


	public void setIdQueue(Long idQueue) {
		this.idQueue = idQueue;
	}


	public String getPersonCode() {
		return personCode;
	}



	public void setPersonCode(String personCode) {
		this.personCode = personCode;
	}



	public String getOrgPerson() {
		return orgPerson;
	}



	public void setOrgPerson(String orgPerson) {
		this.orgPerson = orgPerson;
	}



	public String getRefId() {
		return refId;
	}



	public void setRefId(String refId) {
		this.refId = refId;
	}



	public String getName() {
		return name;
	}



	public void setName(String name) {
		this.name = name;
	}



	public String getOtherName() {
		return otherName;
	}



	public void setOtherName(String otherName) {
		this.otherName = otherName;
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



	public String getPlaceOfBirthName() {
		return placeOfBirthName;
	}



	public void setPlaceOfBirthName(String placeOfBirthName) {
		this.placeOfBirthName = placeOfBirthName;
	}



	public String getIdNumber() {
		return idNumber;
	}



	public void setIdNumber(String idNumber) {
		this.idNumber = idNumber;
	}



	public String getDateOfIdIssue() {
		return dateOfIdIssue;
	}



	public void setDateOfIdIssue(String dateOfIdIssue) {
		this.dateOfIdIssue = dateOfIdIssue;
	}



	public String getPlaceOfIdIssueName() {
		return placeOfIdIssueName;
	}



	public void setPlaceOfIdIssueName(String placeOfIdIssueName) {
		this.placeOfIdIssueName = placeOfIdIssueName;
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



	public String getEthnicCode() {
		return ethnicCode;
	}



	public void setEthnicCode(String ethnicCode) {
		this.ethnicCode = ethnicCode;
	}



	public String getReligionCode() {
		return religionCode;
	}



	public void setReligionCode(String religionCode) {
		this.religionCode = religionCode;
	}



	public String getNationalityName() {
		return nationalityName;
	}



	public void setNationalityName(String nationalityName) {
		this.nationalityName = nationalityName;
	}



	public String getNationalityCode() {
		return nationalityCode;
	}



	public void setNationalityCode(String nationalityCode) {
		this.nationalityCode = nationalityCode;
	}



	public String getFatherName() {
		return fatherName;
	}



	public void setFatherName(String fatherName) {
		this.fatherName = fatherName;
	}



	public String getFatherSearchName() {
		return fatherSearchName;
	}



	public void setFatherSearchName(String fatherSearchName) {
		this.fatherSearchName = fatherSearchName;
	}



	public String getMotherName() {
		return motherName;
	}



	public void setMotherName(String motherName) {
		this.motherName = motherName;
	}



	public String getMotherSearchName() {
		return motherSearchName;
	}



	public void setMotherSearchName(String motherSearchName) {
		this.motherSearchName = motherSearchName;
	}



	public String getCountryOfBirth() {
		return countryOfBirth;
	}



	public void setCountryOfBirth(String countryOfBirth) {
		this.countryOfBirth = countryOfBirth;
	}



	public String getCreatedBy() {
		return createdBy;
	}



	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}



	public Date getCreateTs() {
		return createTs;
	}



	public void setCreateTs(Date createTs) {
		this.createTs = createTs;
	}



	public String getUpdatedBy() {
		return updatedBy;
	}



	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}



	public Date getUpdateTs() {
		return updateTs;
	}



	public void setUpdateTs(Date updateTs) {
		this.updateTs = updateTs;
	}



	public Boolean getIsChecked() {
		return isChecked;
	}



	public void setIsChecked(Boolean isChecked) {
		this.isChecked = isChecked;
	}



	public String getDescription() {
		return description;
	}



	public void setDescription(String description) {
		this.description = description;
	}



	public String getSrcOffice() {
		return srcOffice;
	}



	public void setSrcOffice(String srcOffice) {
		this.srcOffice = srcOffice;
	}



	public String getSrcDoc() {
		return srcDoc;
	}



	public void setSrcDoc(String srcDoc) {
		this.srcDoc = srcDoc;
	}


	public String getPhoto() {
		return photo;
	}


	public void setPhoto(String photo) {
		this.photo = photo;
	}

	
}
