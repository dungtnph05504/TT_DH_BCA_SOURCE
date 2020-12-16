package com.nec.asia.nic.comp.a08database.domain;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement(name = "DoiTuong")
@XmlType(name = "DoiTuong", propOrder = { "key", "name", "nickName",
		"dateOfBirth", "gender", "ethNic", "religion", "originNationalityId",
		"currentNationalityId", "placeOfBirthName", "address", "note",
		"ppNumber", "idNumber", "type", "expire", "registeredNo", "originNationalityName",
		"currentNationalityName"
})
public class DoiTuong {
	private Double key;
	private String name;
	private String nickName;
	private String dateOfBirth;
	private String gender;
	private String ethNic;
	private String religion;
	private Long originNationalityId;
	private Long currentNationalityId;
	private String originNationalityName;
	private String currentNationalityName;
	private String placeOfBirthName;
	private String address;
	private String note;
	private String ppNumber;
	private String idNumber;
	private String type;
	private String expire;
	private String registeredNo;

	public String getOriginNationalityName() {
		return originNationalityName;
	}

	public void setOriginNationalityName(String originNationalityName) {
		this.originNationalityName = originNationalityName;
	}

	public String getCurrentNationalityName() {
		return currentNationalityName;
	}

	public void setCurrentNationalityName(String currentNationalityName) {
		this.currentNationalityName = currentNationalityName;
	}

	public Double getKey() {
		return key;
	}

	public void setKey(Double key) {
		this.key = key;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
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

	public Long getOriginNationalityId() {
		return originNationalityId;
	}

	public void setOriginNationalityId(Long originNationalityId) {
		this.originNationalityId = originNationalityId;
	}

	public Long getCurrentNationalityId() {
		return currentNationalityId;
	}

	public void setCurrentNationalityId(Long currentNationalityId) {
		this.currentNationalityId = currentNationalityId;
	}

	public String getPlaceOfBirthName() {
		return placeOfBirthName;
	}

	public void setPlaceOfBirthName(String placeOfBirthName) {
		this.placeOfBirthName = placeOfBirthName;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public String getPpNumber() {
		return ppNumber;
	}

	public void setPpNumber(String ppNumber) {
		this.ppNumber = ppNumber;
	}

	public String getIdNumber() {
		return idNumber;
	}

	public void setIdNumber(String idNumber) {
		this.idNumber = idNumber;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getExpire() {
		return expire;
	}

	public void setExpire(String expire) {
		this.expire = expire;
	}

	public String getRegisteredNo() {
		return registeredNo;
	}

	public void setRegisteredNo(String registeredNo) {
		this.registeredNo = registeredNo;
	}

}
