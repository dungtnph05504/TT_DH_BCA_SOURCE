package service.transaction.model;
//chi tiết cá nhân khớp cá nhân.
public class OrgPersonDetailToHanding {
	/*""NAME"":    
        ""GENDER"":   		// Giới tính
        ""DATE_OF_BIRTH"":   // Ngày sinh
        ""PLACE_OF_BIRTH"":     		 // Nơi sinh
        ""ID_NUMBER"":            // Số cmnd
        ""DATE_OF_ISSUE"":       // ngày cấp 	
        ""PLACE_OF_ID_ISSUE"":     // nơi cấp cmnd
		""RECEIPT_NO"":       // số biên nhận
        ""RESIDENT_ADDRESS"":  // Địa chỉ thường trú
		""PASSPORT_NO"":       // Số hộ chiếu
		""DATE_OF_ISSUE"":       // ngày cấp
		""DATE_OF_EXPIRY"":       // hạn hộ chiếu
		""PLACE_OF_ISSUE_ID"":   // nơi cấp hộ chiếu
		""STATUS"": 			// tình trạng hộ chiếu
		""PERSON_CODE"": 		// mã cá nhân
		""ORG_PERSON"": 		//mã cá nhân gốc
*/
	private String name;
	private String gender;
	private String dateOfBirth;
	private String placeOfBirth;
	private String idNumber;
	private String dateOfIdIssue;
	private String placeOfIdIssue;
	private String receiptNo;
	private String residentAddress;
	private String passportNo;
	private String dateOfPPIssue;
	private String dateOfPPExpiry;
	private String placeOfPPIssue;
	private String status;
	private String personCode;
	private String orgPerson;
	
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
	public String getDateOfPPIssue() {
		return dateOfPPIssue;
	}
	public void setDateOfPPIssue(String dateOfPPIssue) {
		this.dateOfPPIssue = dateOfPPIssue;
	}
	public String getDateOfPPExpiry() {
		return dateOfPPExpiry;
	}
	public void setDateOfPPExpiry(String dateOfPPExpiry) {
		this.dateOfPPExpiry = dateOfPPExpiry;
	}
	public String getPlaceOfPPIssue() {
		return placeOfPPIssue;
	}
	public void setPlaceOfPPIssue(String placeOfPPIssue) {
		this.placeOfPPIssue = placeOfPPIssue;
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
	public String getIdNumber() {
		return idNumber;
	}
	public void setIdNumber(String idNumber) {
		this.idNumber = idNumber;
	}
	public String getReceiptNo() {
		return receiptNo;
	}
	public void setReceiptNo(String receiptNo) {
		this.receiptNo = receiptNo;
	}
	public String getResidentAddress() {
		return residentAddress;
	}
	public void setResidentAddress(String residentAddress) {
		this.residentAddress = residentAddress;
	}
	public String getPassportNo() {
		return passportNo;
	}
	public void setPassportNo(String passportNo) {
		this.passportNo = passportNo;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getPersonCode() {
		return personCode;
	}
	public void setPersonCode(String personCode) {
		this.personCode = personCode;
	}
	public String getOrgPerson() {
		return orgPerson;
	}
	public void setOrgPerson(String orgPerson) {
		this.orgPerson = orgPerson;
	}
	
}
