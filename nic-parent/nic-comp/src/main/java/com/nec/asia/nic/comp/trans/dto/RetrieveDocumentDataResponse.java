package com.nec.asia.nic.comp.trans.dto;

import java.util.Date;

import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement(name = "RetrieveDocumentDataResponse")
@XmlType ( name = "RetrieveDocumentDataResponse",  propOrder = { "passportNo","fullName", "pid", "dob", "typeDob", "gender", "passportType", 
		"placeOfBirth", "expireDate", "printSite", "status", "flagActive", "icaoOne", "icaoTwo", "infoPerson", "userPrint", "printDate", "image","dateIssua","transactionID"} )
public class RetrieveDocumentDataResponse {
	private String passportNo;
    private String fullName;
    private String pid;
    private Date dob;
    private String typeDob;
    private String gender;
    private String placeOfBirth;
    private String passportType;
    
    private Date expireDate;
    private String printSite;
    private String status;
    private String flagActive;
    private String icaoOne;
    private String icaoTwo;
    private String infoPerson;
    private String userPrint;
    private Date printDate;
    private String image;
    private String transactionID;
    private Date dateIssua;
   
    public RetrieveDocumentDataResponse() {
    }

    

	public String getTransactionID() {
		return transactionID;
	}



	public void setTransactionID(String transactionID) {
		this.transactionID = transactionID;
	}



	public Date getDateIssua() {
		return dateIssua;
	}



	public void setDateIssua(Date dateIssua) {
		this.dateIssua = dateIssua;
	}



	public String getImage() {
		return image;
	}



	public void setImage(String image) {
		this.image = image;
	}



	public String getTypeDob() {
		return typeDob;
	}



	public void setTypeDob(String typeDob) {
		this.typeDob = typeDob;
	}



	public String getPrintSite() {
		return printSite;
	}



	public void setPrintSite(String printSite) {
		this.printSite = printSite;
	}



	public String getUserPrint() {
		return userPrint;
	}



	public void setUserPrint(String userPrint) {
		this.userPrint = userPrint;
	}



	public Date getPrintDate() {
		return printDate;
	}



	public void setPrintDate(Date printDate) {
		this.printDate = printDate;
	}



	public Date getExpireDate() {
		return expireDate;
	}



	public void setExpireDate(Date expireDate) {
		this.expireDate = expireDate;
	}



	public String getFlagActive() {
		return flagActive;
	}



	public void setFlagActive(String flagActive) {
		this.flagActive = flagActive;
	}



	public String getStatus() {
		return status;
	}



	public void setStatus(String status) {
		this.status = status;
	}



	public String getIcaoOne() {
		return icaoOne;
	}



	public void setIcaoOne(String icaoOne) {
		this.icaoOne = icaoOne;
	}



	public String getIcaoTwo() {
		return icaoTwo;
	}



	public void setIcaoTwo(String icaoTwo) {
		this.icaoTwo = icaoTwo;
	}



	public String getInfoPerson() {
		return infoPerson;
	}



	public void setInfoPerson(String infoPerson) {
		this.infoPerson = infoPerson;
	}



	public String getPassportNo() {
		return passportNo;
	}


	public void setPassportNo(String passportNo) {
		this.passportNo = passportNo;
	}


	public String getFullName() {
		return fullName;
	}


	public void setFullName(String fullName) {
		this.fullName = fullName;
	}


	public String getPid() {
		return pid;
	}


	public void setPid(String pid) {
		this.pid = pid;
	}


	public Date getDob() {
		return dob;
	}


	public void setDob(Date dob) {
		this.dob = dob;
	}


	public String getGender() {
		return gender;
	}


	public void setGender(String gender) {
		this.gender = gender;
	}


	public String getPlaceOfBirth() {
		return placeOfBirth;
	}


	public void setPlaceOfBirth(String placeOfBirth) {
		this.placeOfBirth = placeOfBirth;
	}


	public String getPassportType() {
		return passportType;
	}


	public void setPassportType(String passportType) {
		this.passportType = passportType;
	}
 
  
}
