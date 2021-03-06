package com.nec.asia.nic.comp.a08database.domain;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement(name="PersonFamily")
@XmlType(name="PersonFamily", propOrder={"address", "id", "name", "gender", "dateOfBirth", "placeOfBirth", "relationship", "lost", "photo", "typeDob", "transactionId", "personCode"})
public class PersonFamily {
	private String name;
	private String gender;
	private String dateOfBirth;
	private String placeOfBirth;
	private String relationship;
	private String lost;
	private String photo;
	private String typeDob;
	private String transactionId;
	private String personCode;
	private String id;
	private String address;
	
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getPersonCode() {
		return personCode;
	}
	public void setPersonCode(String personCode) {
		this.personCode = personCode;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
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
	public String getPlaceOfBirth() {
		return placeOfBirth;
	}
	public void setPlaceOfBirth(String placeOfBirth) {
		this.placeOfBirth = placeOfBirth;
	}
	public String getRelationship() {
		return relationship;
	}
	public void setRelationship(String relationship) {
		this.relationship = relationship;
	}
	public String getLost() {
		return lost;
	}
	public void setLost(String lost) {
		this.lost = lost;
	}
	public String getPhoto() {
		return photo;
	}
	public void setPhoto(String photo) {
		this.photo = photo;
	}
	public String getTypeDob() {
		return typeDob;
	}
	public void setTypeDob(String typeDob) {
		this.typeDob = typeDob;
	}
	public String getTransactionId() {
		return transactionId;
	}
	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}
	
	
}