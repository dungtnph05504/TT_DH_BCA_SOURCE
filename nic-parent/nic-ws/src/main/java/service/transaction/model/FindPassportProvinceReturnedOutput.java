package service.transaction.model;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement(name="FindPassportProvinceReturnedOutput")
@XmlType(name="FindPassportProvinceReturnedOutput", propOrder={"idEppDocumentReturnedId","passportNo", 
		"chipSerialNo", "dateOfIssue", "dateOfExpiry", "printName", 
		"archiveCode", "receiptNo", "name", "gender", "dateOfBirth",
		"placeOfBirth", "nin", "dateOfIdIssue", "placeOfIdIssue",
		"residentAddress","receiptReturn","storageNo","officeName","receiptDate"
		,"receiptName","handoverName","handoverDate", "phoiSerialNo"})
public class FindPassportProvinceReturnedOutput {
	 private long idEppDocumentReturnedId;
     private String passportNo;
     private String chipSerialNo;
     private String dateOfIssue;
     private String dateOfExpiry;
     private String printName;
     private String archiveCode;
     private String receiptNo;
     private String name;
     private String gender;
     private String dateOfBirth;
     private String placeOfBirth;
     private String nin;
     private String dateOfIdIssue;
     private String placeOfIdIssue;
     private String residentAddress;
     private String receiptReturn;
     private String storageNo;
     private String officeName;
     private String receiptDate;
     private String receiptName;
     private String handoverName;
     private String handoverDate;
     private String phoiSerialNo;
     
     
	public String getPhoiSerialNo() {
		return phoiSerialNo;
	}
	public void setPhoiSerialNo(String phoiSerialNo) {
		this.phoiSerialNo = phoiSerialNo;
	}
	public long getIdEppDocumentReturnedId() {
		return idEppDocumentReturnedId;
	}
	public void setIdEppDocumentReturnedId(long idEppDocumentReturnedId) {
		this.idEppDocumentReturnedId = idEppDocumentReturnedId;
	}
	public String getPassportNo() {
		return passportNo;
	}
	public void setPassportNo(String passportNo) {
		this.passportNo = passportNo;
	}
	public String getChipSerialNo() {
		return chipSerialNo;
	}
	public void setChipSerialNo(String chipSerialNo) {
		this.chipSerialNo = chipSerialNo;
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
	public String getPrintName() {
		return printName;
	}
	public void setPrintName(String printName) {
		this.printName = printName;
	}
	public String getArchiveCode() {
		return archiveCode;
	}
	public void setArchiveCode(String archiveCode) {
		this.archiveCode = archiveCode;
	}
	public String getReceiptNo() {
		return receiptNo;
	}
	public void setReceiptNo(String receiptNo) {
		this.receiptNo = receiptNo;
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
	public String getDateOfIdIssue() {
		return dateOfIdIssue;
	}
	public void setDateOfIdIssue(String dateOfIdIssue) {
		this.dateOfIdIssue = dateOfIdIssue;
	}
	public String getPlaceOfIdIssue() {
		return placeOfIdIssue;
	}
	public void setPlaceOfIdIssue(String placeOfIdIssue) {
		this.placeOfIdIssue = placeOfIdIssue;
	}
	public String getResidentAddress() {
		return residentAddress;
	}
	public void setResidentAddress(String residentAddress) {
		this.residentAddress = residentAddress;
	}
	public String getReceiptReturn() {
		return receiptReturn;
	}
	public void setReceiptReturn(String receiptReturn) {
		this.receiptReturn = receiptReturn;
	}
	public String getStorageNo() {
		return storageNo;
	}
	public void setStorageNo(String storageNo) {
		this.storageNo = storageNo;
	}
	public String getOfficeName() {
		return officeName;
	}
	public void setOfficeName(String officeName) {
		this.officeName = officeName;
	}
	public String getReceiptDate() {
		return receiptDate;
	}
	public void setReceiptDate(String receiptDate) {
		this.receiptDate = receiptDate;
	}
	public String getReceiptName() {
		return receiptName;
	}
	public void setReceiptName(String receiptName) {
		this.receiptName = receiptName;
	}
	public String getHandoverName() {
		return handoverName;
	}
	public void setHandoverName(String handoverName) {
		this.handoverName = handoverName;
	}
	public String getHandoverDate() {
		return handoverDate;
	}
	public void setHandoverDate(String handoverDate) {
		this.handoverDate = handoverDate;
	}
     
}
