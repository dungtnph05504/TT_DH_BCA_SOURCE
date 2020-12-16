package com.nec.asia.nic.comp.trans.dto;

import java.util.Date;

import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement(name = "PassportInfo")
@XmlType ( name = "PassportInfo",  propOrder = { "transactionId","fullName", "pid", "dob", "gender", "address", 
		"placeOfBirth","passportType","placeOfIssue", "placePersoId", "picture", 
		"countryCode", "nationality", "dateOfExpiry","dateOfIssue", 
		"stylePassport", "receiptNo", "epp_persons", "detailaddress", "estOfRecieve", "namePlaceOfIssue", "handoverA"} )
public class PassportInfo {
	private String transactionId;
    private String fullName;
    private String pid;
    private String dob;
    private String gender;
    private String address;
    private String placeOfBirth;
    private String passportType;
    private String placeOfIssue;
    private Long placePersoId;
    private byte[] picture;
    private String countryCode;
    private String nationality;
    private Date dateOfExpiry;
    private Date dateOfIssue;
    private Boolean stylePassport;
    private String receiptNo;
    //private EppPersonsDto epp_persons;
    
    private String detailaddress;
    private Date estOfRecieve;
    private String namePlaceOfIssue;
    private String handoverA;
    
    public PassportInfo() {
    }
 
    public void setTransactionId(String transactionId) {
    	this.transactionId = transactionId;
    }
 
    public String getTransactionId() {
        return transactionId;
    }
    
    public void setFullName(String fullName) {
    	this.fullName = fullName;
    }
 
    public String getFullName() {
        return fullName;
    }
 
    public void setPid(String pid) {
    	this.pid = pid;
    }
 
    public String getPid() {
        return pid;
    }
    public void setDob(String dob) {
    	this.dob = dob;
    }
 
    public String getDob() {
        return dob;
    }
    public void setGender(String gender) {
    	this.gender = gender;
    }
 
    public String getGender() {
        return gender;
    }
    
    public void setAddress(String address) {
    	this.address = address;
    }
 
    public String getAddress() {
        return address;
    }
    
    public void setPlaceOfBirth(String placeOfBirth) {
    	this.placeOfBirth = placeOfBirth;
    }
 
    public String getPlaceOfBirth() {
        return placeOfBirth;
    }
    
    public void setPassportType(String passportType) {
    	this.passportType = passportType;
    }

    public String getPassportType() {
        return passportType;
    }
    
    public void setPlaceOfIssue(String placeOfIssue) {
    	this.placeOfIssue = placeOfIssue;
    }
    
    public String getPlaceOfIssue() {
        return placeOfIssue;
    }
    
    public void setPlacePersoId(Long placePersoId) {
    	this.placePersoId = placePersoId;
    }
    
    public Long getPlacePersoId() {
        return placePersoId;
    }
    
    public void setPicture(byte[] picture) {
    	this.picture = picture;
    }
    
    public byte[] getPicture() {
        return picture;
    }
    
    public void setDateOfExpiry(Date dateOfExpiry) {
    	this.dateOfExpiry = dateOfExpiry;
    }
 
    public Date getDateOfExpiry() {
        return dateOfExpiry;
    }
    
    public void setDateOfIssue(Date dateOfIssue) {
    	this.dateOfIssue = dateOfIssue;
    }
 
    public Date getDateOfIssue() {
        return dateOfIssue;
    }
    
    public void setCountryCode(String countryCode) {
    	this.countryCode = countryCode;
    }
 
    public String getCountryCode() {
        return countryCode;
    }
    
    public void setNationality(String nationality) {
    	this.nationality = nationality;
    }
 
    public String getNationality() {
        return nationality;
    }

/*	public EppPersonsDto getEpp_persons() {
		return epp_persons;
	}

	public void setEpp_persons(EppPersonsDto epp_persons) {
		this.epp_persons = epp_persons;
	}
*/
	public Boolean getStylePassport() {
		return stylePassport;
	}

	public void setStylePassport(Boolean stylePassport) {
		this.stylePassport = stylePassport;
	}

	public String getReceiptNo() {
		return receiptNo;
	}

	public void setReceiptNo(String receiptNo) {
		this.receiptNo = receiptNo;
	}

	public String getDetailaddress() {
		return detailaddress;
	}

	public void setDetailaddress(String detailaddress) {
		this.detailaddress = detailaddress;
	}

	public Date getEstOfRecieve() {
		return estOfRecieve;
	}

	public void setEstOfRecieve(Date estOfRecieve) {
		this.estOfRecieve = estOfRecieve;
	}

	public String getHandoverA() {
		return handoverA;
	}

	public void setHandoverA(String handoverA) {
		this.handoverA = handoverA;
	}

	public String getNamePlaceOfIssue() {
		return namePlaceOfIssue;
	}

	public void setNamePlaceOfIssue(String namePlaceOfIssue) {
		this.namePlaceOfIssue = namePlaceOfIssue;
	}
}
