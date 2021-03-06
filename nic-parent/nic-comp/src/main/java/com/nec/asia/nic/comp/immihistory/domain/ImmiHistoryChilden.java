package com.nec.asia.nic.comp.immihistory.domain;
// Generated Jan 20, 2016 11:25:39 AM by Hibernate Tools 4.3.1.Final

import java.util.Date;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.nec.asia.nic.comp.immihistory.model.ImmihistoryChildren;

/**
 * NicDecisionManager generated by hbm2java
 */

@XmlRootElement(name = "ImmiHistoryChilden")
@XmlAccessorType(XmlAccessType.FIELD)
public class ImmiHistoryChilden implements java.io.Serializable {
    /**
     * 
     */
    private static final long serialVersionUID = 4030959704968010599L; 
    
    @XmlElement(name = "id") 
    private Long id;
    @XmlElement(name = "createdBy") 
    private String createdBy;
    @XmlElement(name = "createdBy") 
    private String lastModifiedBy;
    @XmlElement(name = "createdTime") 
    private Date createdTime;
    @XmlElement(name = "lastModifiedTime") 
    private Date lastModifiedTime;
    @XmlElement(name = "immihistoryId") 
    private Long immihistoryId;
    @XmlElement(name = "fullName") 
    private String fullName;
    @XmlElement(name = "dateOfBirth") 
    private Date dateOfBirth;
    @XmlElement(name = "gender") 
    private String gender;
    @XmlElement(name = "familyrelationshipCode") 
    private String familyrelationshipCode;
    @XmlElement(name="placeOfBirthCode")
    private String placeOfBirthCode;
    @XmlElement(name="address")
    private String address;
   
	public ImmiHistoryChilden() {
		super();
	}

	public ImmiHistoryChilden(Long id, String createdBy, String lastModifiedBy,
			Date createdTime, Date lastModifiedTime, Long immihistoryId,
			String fullName, Date dateOfBirth, String gender,
			String familyrelationshipCode) {
		super();
		this.createdBy = createdBy;
		this.lastModifiedBy = lastModifiedBy;
		this.createdTime = createdTime;
		this.lastModifiedTime = lastModifiedTime;
		this.immihistoryId = immihistoryId;
		this.fullName = fullName;
		this.dateOfBirth = dateOfBirth;
		this.gender = gender;
		this.familyrelationshipCode = familyrelationshipCode;
	}
	
	public ImmiHistoryChilden(ImmihistoryChildren children){
		
		this.createdBy = children.getCreatedBy();
		this.lastModifiedBy = children.getLastModifiedBy();
		//this.createdTime = children.getCreatedTime();
		this.lastModifiedTime = children.getLastModifiedTime();
		
		this.fullName = children.getFullName();
		this.dateOfBirth = children.getDateOfBirth();
		this.gender = children.getGender();
		this.familyrelationshipCode = children.getFamilyrelationshipCode();
		this.placeOfBirthCode = children.getPlaceOfBirthCode();
		this.address = children.getAddress();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public String getLastModifiedBy() {
		return lastModifiedBy;
	}

	public void setLastModifiedBy(String lastModifiedBy) {
		this.lastModifiedBy = lastModifiedBy;
	}

	public Date getLastModifiedTime() {
		return lastModifiedTime;
	}

	public void setLastModifiedTime(Date lastModifiedTime) {
		this.lastModifiedTime = lastModifiedTime;
	}

	public Date getCreatedTime() {
		return createdTime;
	}

	public void setCreatedTime(Date createdTime) {
		this.createdTime = createdTime;
	}


	public Long getImmihistoryId() {
		return immihistoryId;
	}

	public void setImmihistoryId(Long immihistoryId) {
		this.immihistoryId = immihistoryId;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public Date getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(Date dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getFamilyrelationshipCode() {
		return familyrelationshipCode;
	}

	public void setFamilyrelationshipCode(String familyrelationshipCode) {
		this.familyrelationshipCode = familyrelationshipCode;
	}

	public String getPlaceOfBirthCode() {
		return placeOfBirthCode;
	}

	public void setPlaceOfBirthCode(String placeOfBirthCode) {
		this.placeOfBirthCode = placeOfBirthCode;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}
	
	
	
}
