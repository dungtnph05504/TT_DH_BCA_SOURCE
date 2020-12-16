package service.perso.model;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement(name = "PassportByPassportNo")
@XmlType ( name = "PassportByPassportNo",  propOrder = { "chipseriNo","dateOfIssue", "dateOfExpiry", "name", "gender", "dateOfBirth", 
		"placeOfBirth","nin","archiveCode", "passportNo", "receiptNo", "phoiSerialNo"} )
public class PassportByPassportNo {
    private String chipseriNo;
    private String dateOfIssue;
    private String dateOfExpiry;
    private String  name;
    private String gender;
    private String dateOfBirth;
    private String placeOfBirth;
    private String nin;
    private String archiveCode;
    private String passportNo;
    private String receiptNo;
    private String phoiSerialNo;
    
	public String getPhoiSerialNo() {
		return phoiSerialNo;
	}
	public void setPhoiSerialNo(String phoiSerialNo) {
		this.phoiSerialNo = phoiSerialNo;
	}
	public String getChipseriNo() {
		return chipseriNo;
	}
	public void setChipseriNo(String chipseriNo) {
		this.chipseriNo = chipseriNo;
	}
	public String getDateOfIssue() {
		return dateOfIssue;
	}
	public void setDateOfIssue(String dateOfIssue) {
		this.dateOfIssue = dateOfIssue;
	}
	public String getDateOfExpiry() {
		return dateOfExpiry;
	}
	public void setDateOfExpiry(String dateOfExpiry) {
		this.dateOfExpiry = dateOfExpiry;
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
	public String getNin() {
		return nin;
	}
	public void setNin(String nin) {
		this.nin = nin;
	}
	public String getArchiveCode() {
		return archiveCode;
	}
	public void setArchiveCode(String archiveCode) {
		this.archiveCode = archiveCode;
	}
	public String getPassportNo() {
		return passportNo;
	}
	public void setPassportNo(String passportNo) {
		this.passportNo = passportNo;
	}
	public String getReceiptNo() {
		return receiptNo;
	}
	public void setReceiptNo(String receiptNo) {
		this.receiptNo = receiptNo;
	}
    
    
}
