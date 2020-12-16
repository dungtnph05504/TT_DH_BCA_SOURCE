package com.nec.asia.nic.comp.trans.dto;

import java.sql.Date;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement(name="EppPersonDto")
@XmlAccessorType(XmlAccessType.FIELD)
public class EppPersonDto {
	@XmlElement(name = "id") 
	private String id;
	@XmlElement(name = "name")
	private String name;
	@XmlElement(name = "searchName")
	private String searchName;
	@XmlElement(name = "gender")
	private String gender;
	@XmlElement(name = "dateOfBirth")
	private String dateOfBirth;
	@XmlElement(name = "placeOfBirthId")
	private String placeOfBirthId;
	@XmlElement(name = "nationalityId")
	private String nationalityId;
	@XmlElement(name = "type_")
	private String type_;
	@XmlElement(name = "picture")
	private byte[] picture;
	@XmlElement(name = "pictureStr")
	private String pictureStr;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
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
	public String getPlaceOfBirthId() {
		return placeOfBirthId;
	}
	public void setPlaceOfBirthId(String placeOfBirthId) {
		this.placeOfBirthId = placeOfBirthId;
	}
	public String getNationalityId() {
		return nationalityId;
	}
	public void setNationalityId(String nationalityId) {
		this.nationalityId = nationalityId;
	}
	public String getType_() {
		return type_;
	}
	public void setType_(String type_) {
		this.type_ = type_;
	}
	public byte[] getPicture() {
		return picture;
	}
	public void setPicture(byte[] picture) {
		this.picture = picture;
	}
	public String getPictureStr() {
		return pictureStr;
	}
	public void setPictureStr(String pictureStr) {
		this.pictureStr = pictureStr;
	}
	
}
