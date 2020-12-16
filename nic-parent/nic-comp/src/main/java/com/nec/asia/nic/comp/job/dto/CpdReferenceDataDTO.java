package com.nec.asia.nic.comp.job.dto;

import java.io.Serializable;
import java.util.Date;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import com.nec.asia.nic.dx.utils.DateAdapter;

/**
 * @author johnpaul_millan
 * @Company : NEC ASIA PACIFIC PTE LTD
 * @Since : May 30, 2013
 * <p>DTO for CpdReferenceData for verification job</p>
 */

@XmlRootElement
public class CpdReferenceDataDTO implements Serializable{

	private static final long serialVersionUID = 1L;
	
    protected String nin;
    protected String surname;
    protected String firstName;
    protected String surnameAtBirth;
    protected String sex;
//    @XmlElement(type = String.class)
//    @XmlJavaTypeAdapter(DateAdapter.class)
//    @XmlSchemaType(name = "dateTime")
    protected Date birthDate;
    protected String birthDateApprox;
    protected String flatNoApartmentName;
    protected String streetName;
    protected String locality;
    protected String townVillage;
    protected String district;
    protected String postalCode;
    protected String prefferedMailingAddress;
    protected String overseasAddress;
    protected String overseasCountry;
    protected byte[] photo;
    protected byte[] thumbprint;
    protected byte[] signature;
    protected String maritalStatus;
    protected String spouseNin;
    protected String spouseSurname;
    protected String spouseFirstName;
    protected String fatherNin;
    protected String fatherFullname;
    protected String motherNin;
    protected String motherFullname;
	
	private String cpdXml;
	public String getCpdXml() {
		return cpdXml;
	}
	public void setCpdXml(String cpdXml) {
		this.cpdXml = cpdXml;
	}
	public String getNin() {
		return nin;
	}
	public void setNin(String nin) {
		this.nin = nin;
	}
	public String getSurname() {
		return surname;
	}
	public void setSurname(String surname) {
		this.surname = surname;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getSurnameAtBirth() {
		return surnameAtBirth;
	}
	public void setSurnameAtBirth(String surnameAtBirth) {
		this.surnameAtBirth = surnameAtBirth;
	}
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	public Date getBirthDate() {
		return birthDate;
	}
	public void setBirthDate(Date birthDate) {
		this.birthDate = birthDate;
	}
	public String getBirthDateApprox() {
		return birthDateApprox;
	}
	public void setBirthDateApprox(String birthDateApprox) {
		this.birthDateApprox = birthDateApprox;
	}
	public String getFlatNoApartmentName() {
		return flatNoApartmentName;
	}
	public void setFlatNoApartmentName(String flatNoApartmentName) {
		this.flatNoApartmentName = flatNoApartmentName;
	}
	public String getStreetName() {
		return streetName;
	}
	public void setStreetName(String streetName) {
		this.streetName = streetName;
	}
	public String getLocality() {
		return locality;
	}
	public void setLocality(String locality) {
		this.locality = locality;
	}
	public String getTownVillage() {
		return townVillage;
	}
	public void setTownVillage(String townVillage) {
		this.townVillage = townVillage;
	}
	public String getDistrict() {
		return district;
	}
	public void setDistrict(String district) {
		this.district = district;
	}
	public String getPostalCode() {
		return postalCode;
	}
	public void setPostalCode(String postalCode) {
		this.postalCode = postalCode;
	}
	public String getPrefferedMailingAddress() {
		return prefferedMailingAddress;
	}
	public void setPrefferedMailingAddress(String prefferedMailingAddress) {
		this.prefferedMailingAddress = prefferedMailingAddress;
	}
	public String getOverseasAddress() {
		return overseasAddress;
	}
	public void setOverseasAddress(String overseasAddress) {
		this.overseasAddress = overseasAddress;
	}
	public String getOverseasCountry() {
		return overseasCountry;
	}
	public void setOverseasCountry(String overseasCountry) {
		this.overseasCountry = overseasCountry;
	}
	public byte[] getPhoto() {
		return photo;
	}
	public void setPhoto(byte[] photo) {
		this.photo = photo;
	}
	public byte[] getThumbprint() {
		return thumbprint;
	}
	public void setThumbprint(byte[] thumbprint) {
		this.thumbprint = thumbprint;
	}
	public byte[] getSignature() {
		return signature;
	}
	public void setSignature(byte[] signature) {
		this.signature = signature;
	}
	public String getMaritalStatus() {
		return maritalStatus;
	}
	public void setMaritalStatus(String maritalStatus) {
		this.maritalStatus = maritalStatus;
	}
	public String getSpouseNin() {
		return spouseNin;
	}
	public void setSpouseNin(String spouseNin) {
		this.spouseNin = spouseNin;
	}
	public String getSpouseSurname() {
		return spouseSurname;
	}
	public void setSpouseSurname(String spouseSurname) {
		this.spouseSurname = spouseSurname;
	}
	public String getSpouseFirstName() {
		return spouseFirstName;
	}
	public void setSpouseFirstName(String spouseFirstName) {
		this.spouseFirstName = spouseFirstName;
	}
	public String getFatherNin() {
		return fatherNin;
	}
	public void setFatherNin(String fatherNin) {
		this.fatherNin = fatherNin;
	}
	public String getFatherFullname() {
		return fatherFullname;
	}
	public void setFatherFullname(String fatherFullname) {
		this.fatherFullname = fatherFullname;
	}
	public String getMotherNin() {
		return motherNin;
	}
	public void setMotherNin(String motherNin) {
		this.motherNin = motherNin;
	}
	public String getMotherFullname() {
		return motherFullname;
	}
	public void setMotherFullname(String motherFullname) {
		this.motherFullname = motherFullname;
	}
}
