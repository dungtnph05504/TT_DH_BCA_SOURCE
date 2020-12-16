package service.investigation.model;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import com.nec.asia.nic.comp.a08database.domain.PassportStatus;

@XmlRootElement(name = "DataPersonBuff")
@XmlType(name = "DataPersonBuff", propOrder = { "apxPersonCcode", "maCaNhan",
		"name", "otherName", "gender", "dateOfBirth", "placeOfBirthName",
		"idNumber", "ethNic", "religion", "searchName", "nationalityName",
		"residentPlaceName", "residentAddress", "tempAddress", "occupation",
		"officeInfo", "fatherName", "fatherNationality", "fatherOccupation",
		"motherName", "motherNationality", "motherOccupation", "passportNo",
		"matchPoint", "searchTs", "src", "transactionId",
		"transactionMasterId", "dataType", "orgPerson", "idQueue",
		"listPassportStatus" })
public class DataPersonBuff {
	private String apxPersonCcode;
	private String maCaNhan;
	private String name;
	private String otherName;
	private String gender;
	private String dateOfBirth;
	private String placeOfBirthName;
	private String idNumber;
	private String ethNic;
	private String religion;
	private String searchName;
	private String nationalityName;
	private String residentPlaceName;
	private String residentAddress;
	private String tempAddress;
	private String occupation;
	private String officeInfo;
	private String fatherName;
	private String fatherNationality;
	private String fatherOccupation;
	private String motherName;
	private String motherNationality;
	private String motherOccupation;
	private String passportNo;
	private Double matchPoint;
	private String searchTs;
	private String src;
	private String transactionId;
	private String transactionMasterId;
	private String dataType;
	private String orgPerson;
	private Long idQueue;
	private List<PassportStatus> listPassportStatus;

	public List<PassportStatus> getListPassportStatus() {
		return listPassportStatus;
	}

	public void setListPassportStatus(List<PassportStatus> listPassportStatus) {
		this.listPassportStatus = listPassportStatus;
	}

	public Long getIdQueue() {
		return idQueue;
	}

	public void setIdQueue(Long idQueue) {
		this.idQueue = idQueue;
	}

	public String getOrgPerson() {
		return orgPerson;
	}

	public void setOrgPerson(String orgPerson) {
		this.orgPerson = orgPerson;
	}

	public String getDataType() {
		return dataType;
	}

	public void setDataType(String dataType) {
		this.dataType = dataType;
	}

	public String getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}

	public String getTransactionMasterId() {
		return transactionMasterId;
	}

	public void setTransactionMasterId(String transactionMasterId) {
		this.transactionMasterId = transactionMasterId;
	}

	public String getApxPersonCcode() {
		return apxPersonCcode;
	}

	public void setApxPersonCcode(String apxPersonCcode) {
		this.apxPersonCcode = apxPersonCcode;
	}

	public String getMaCaNhan() {
		return maCaNhan;
	}

	public void setMaCaNhan(String maCaNhan) {
		this.maCaNhan = maCaNhan;
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

	public String getEthNic() {
		return ethNic;
	}

	public void setEthNic(String ethNic) {
		this.ethNic = ethNic;
	}

	public String getReligion() {
		return religion;
	}

	public void setReligion(String religion) {
		this.religion = religion;
	}

	public String getSearchName() {
		return searchName;
	}

	public void setSearchName(String searchName) {
		this.searchName = searchName;
	}

	public String getNationalityName() {
		return nationalityName;
	}

	public void setNationalityName(String nationalityName) {
		this.nationalityName = nationalityName;
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

	public String getTempAddress() {
		return tempAddress;
	}

	public void setTempAddress(String tempAddress) {
		this.tempAddress = tempAddress;
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

	public String getFatherName() {
		return fatherName;
	}

	public void setFatherName(String fatherName) {
		this.fatherName = fatherName;
	}

	public String getFatherNationality() {
		return fatherNationality;
	}

	public void setFatherNationality(String fatherNationality) {
		this.fatherNationality = fatherNationality;
	}

	public String getFatherOccupation() {
		return fatherOccupation;
	}

	public void setFatherOccupation(String fatherOccupation) {
		this.fatherOccupation = fatherOccupation;
	}

	public String getMotherName() {
		return motherName;
	}

	public void setMotherName(String motherName) {
		this.motherName = motherName;
	}

	public String getMotherNationality() {
		return motherNationality;
	}

	public void setMotherNationality(String motherNationality) {
		this.motherNationality = motherNationality;
	}

	public String getMotherOccupation() {
		return motherOccupation;
	}

	public void setMotherOccupation(String motherOccupation) {
		this.motherOccupation = motherOccupation;
	}

	public String getPassportNo() {
		return passportNo;
	}

	public void setPassportNo(String passportNo) {
		this.passportNo = passportNo;
	}

	public Double getMatchPoint() {
		return matchPoint;
	}

	public void setMatchPoint(Double matchPoint) {
		this.matchPoint = matchPoint;
	}

	public String getSearchTs() {
		return searchTs;
	}

	public void setSearchTs(String searchTs) {
		this.searchTs = searchTs;
	}

	public String getSrc() {
		return src;
	}

	public void setSrc(String src) {
		this.src = src;
	}
}
