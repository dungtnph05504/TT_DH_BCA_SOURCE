package service.transaction.model;
//chi tiết hồ sơ khớp cá nhân
public class TransactionDetailToHanding {
	private String transactionId;
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
	private String matchType;
	private String matchedPs;
	private String passportStatus;
	
	public String getPassportStatus() {
		return passportStatus;
	}
	public void setPassportStatus(String passportStatus) {
		this.passportStatus = passportStatus;
	}
	public String getTransactionId() {
		return transactionId;
	}
	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
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
	public String getMatchType() {
		return matchType;
	}
	public void setMatchType(String matchType) {
		this.matchType = matchType;
	}
	public String getMatchedPs() {
		return matchedPs;
	}
	public void setMatchedPs(String matchedPs) {
		this.matchedPs = matchedPs;
	}
}
