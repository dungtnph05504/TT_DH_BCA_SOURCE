package service.transaction.model;
/*
 * Kết quả trả về của service tìm kiếm danh sách hồ sơ hộ chiếu để hủy.
 */
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement(name = "PassportLostOutput")
@XmlType(name = "PassportLostOutput", propOrder = {"name",
		"gender", "dateOfBirth", "nationality", "passportNo", 
		"dateOfIssue", "dateOfExpiry", "placeOfBirth", "placeOfBirthCode", "nationalityCode", "placeOfIssue", "placeOfIssueCode",
		"officeName", "transactionId"})
public class PassportLostOutput {
	private String name;
	private String gender;
	private String dateOfBirth;
	private String nationality;
	private String passportNo;
	private String dateOfIssue;
	private String dateOfExpiry;
	private String officeName;
	private String transactionId;
	private String placeOfBirth;
	private String placeOfBirthCode;
	private String nationalityCode;
	private String placeOfIssue;
	private String placeOfIssueCode;
	
	public String getPlaceOfBirth() {
		return placeOfBirth;
	}
	public void setPlaceOfBirth(String placeOfBirth) {
		this.placeOfBirth = placeOfBirth;
	}
	public String getPlaceOfBirthCode() {
		return placeOfBirthCode;
	}
	public void setPlaceOfBirthCode(String placeOfBirthCode) {
		this.placeOfBirthCode = placeOfBirthCode;
	}
	public String getNationalityCode() {
		return nationalityCode;
	}
	public void setNationalityCode(String nationalityCode) {
		this.nationalityCode = nationalityCode;
	}
	public String getPlaceOfIssue() {
		return placeOfIssue;
	}
	public void setPlaceOfIssue(String placeOfIssue) {
		this.placeOfIssue = placeOfIssue;
	}
	public String getPlaceOfIssueCode() {
		return placeOfIssueCode;
	}
	public void setPlaceOfIssueCode(String placeOfIssueCode) {
		this.placeOfIssueCode = placeOfIssueCode;
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
	public String getNationality() {
		return nationality;
	}
	public void setNationality(String nationality) {
		this.nationality = nationality;
	}
	public String getPassportNo() {
		return passportNo;
	}
	public void setPassportNo(String passportNo) {
		this.passportNo = passportNo;
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
	public String getOfficeName() {
		return officeName;
	}
	public void setOfficeName(String officeName) {
		this.officeName = officeName;
	}
}
