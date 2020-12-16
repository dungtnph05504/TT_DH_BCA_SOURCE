package com.nec.asia.nic.comp.immihistory.model;

import java.util.Date;






import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import org.codehaus.jackson.map.annotate.JsonSerialize;

import com.nec.asia.nic.comp.immihistory.domain.ImmiHistoryChilden;
import com.nec.asia.nic.util.DateHandler;

@XmlRootElement(name="ImmihistoryChildren")
@XmlType(name="ImmihistoryChildren", propOrder={"createdBy", "lastModifiedBy", "createdTime", "lastModifiedTime", "fullName", "dateOfBirth", "gender", "familyrelationshipCode", "placeOfBirthCode", "address"})
public class ImmihistoryChildren {
	
 
    
    private String createdBy;
   
    private String lastModifiedBy;
    @JsonSerialize(using=DateHandler.class)
    private Date createdTime;
    @JsonSerialize(using=DateHandler.class)
    private Date lastModifiedTime;
   
    private String fullName;
    @JsonSerialize(using=DateHandler.class)
    private Date dateOfBirth;
    
    private String gender;
   
    private String familyrelationshipCode;
    
    private String placeOfBirthCode;
    
    private String address;
    
    

	public ImmihistoryChildren() {
		super();
	}
	
	public ImmihistoryChildren(ImmiHistoryChilden chil) {
		
		this.createdBy = chil.getCreatedBy();
		this.lastModifiedBy = chil.getLastModifiedBy();
		this.createdTime = chil.getCreatedTime();
		this.lastModifiedTime = chil.getLastModifiedTime();		
		this.fullName = chil.getFullName();
		this.dateOfBirth = chil.getDateOfBirth();
		this.gender = chil.getGender();
		this.familyrelationshipCode = chil.getFamilyrelationshipCode();
		this.placeOfBirthCode = chil.getPlaceOfBirthCode();
		this.address = chil.getAddress();
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

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	

	public Date getCreatedTime() {
		return createdTime;
	}

	public void setCreatedTime(Date createdTime) {
		this.createdTime = createdTime;
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
    
    
}
