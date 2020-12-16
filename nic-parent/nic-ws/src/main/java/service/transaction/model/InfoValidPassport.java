package service.transaction.model;

public class InfoValidPassport {

	private String base64;
	private String passportNo;
	private String dateOfDocIssue;
	private String dateOfDocExpiry;
	private String placeOfDocIssue;
	private String name;
	private String gender;
	private String dateOfBirth;
	private String placeOfBirthCode;
	private String placeOfDocIssueCode;
	private String nationalityCode;
	private String nin;
	private String transactionId;
	
	public String getPlaceOfDocIssueCode() {
		return placeOfDocIssueCode;
	}
	public void setPlaceOfDocIssueCode(String placeOfDocIssueCode) {
		this.placeOfDocIssueCode = placeOfDocIssueCode;
	}
	public String getNationalityCode() {
		return nationalityCode;
	}
	public void setNationalityCode(String nationalityCode) {
		this.nationalityCode = nationalityCode;
	}
	public String getPlaceOfBirthCode() {
		return placeOfBirthCode;
	}
	public void setPlaceOfBirthCode(String placeOfBirthCode) {
		this.placeOfBirthCode = placeOfBirthCode;
	}
	public String getTransactionId() {
		return transactionId;
	}
	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}
	public String getBase64() {
		return base64;
	}
	public void setBase64(String base64) {
		this.base64 = base64;
	}
	public String getPassportNo() {
		return passportNo;
	}
	public void setPassportNo(String passportNo) {
		this.passportNo = passportNo;
	}
	public String getDateOfDocIssue() {
		return dateOfDocIssue;
	}
	public void setDateOfDocIssue(String dateOfDocIssue) {
		this.dateOfDocIssue = dateOfDocIssue;
	}
	public String getDateOfDocExpiry() {
		return dateOfDocExpiry;
	}
	public void setDateOfDocExpiry(String dateOfDocExpiry) {
		this.dateOfDocExpiry = dateOfDocExpiry;
	}
	public String getPlaceOfDocIssue() {
		return placeOfDocIssue;
	}
	public void setPlaceOfDocIssue(String placeOfDocIssue) {
		this.placeOfDocIssue = placeOfDocIssue;
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
	public String getNin() {
		return nin;
	}
	public void setNin(String nin) {
		this.nin = nin;
	}
	
}
