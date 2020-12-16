package service.perso.model;

import java.util.Date;

import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement(name = "RetrieveDocumentDataResponse")
@XmlType ( name = "RetrieveDocumentDataResponse",  propOrder = { "passportNo", "fullName", "pid", "dob", "gender", "passportType", 
		"placeOfBirth", "expireDate", "status", "icaoOne", "icaoTwo", "infoPerson", "userPrint", "printDate"} )
public class RetrieveDocumentDataResponse {
	private String passportNo;
    private String fullName;
    private String pid;
    private String dob;
    private String gender;
    private String placeOfBirth;
    private String passportType;
    
    private Date expireDate;
    private String status;
    private String icaoOne;
    private String icaoTwo;
    private String infoPerson;
    private String userPrint;
    private Date printDate;
   
    public RetrieveDocumentDataResponse() {
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


	public String getDob() {
		return dob;
	}


	public void setDob(String dob) {
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
