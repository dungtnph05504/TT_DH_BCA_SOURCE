package service.transaction.model;

/*
 * Thông tin chi tiết hộ chiếu cần hủy của service hủy hộ chiếu.
 */
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement(name = "PassportCancelDetail")
@XmlType(name = "PassportCancelDetail", propOrder = { "passportNo", "name",
		"gender", "dateOfBirth", "dateOfDocIssue", "dateOfDocExpiry",
		"transactionId", "placeOfIssue", "createBy", "regSiteCode", "phoiSerialNo", "placeOfBirth" })
public class PassportCancelDetail {
	private String name;
	private String gender;
	private String dateOfBirth;
	private String passportNo;
	private String dateOfDocIssue;
	private String dateOfDocExpiry;
	private String transactionId;
	private String placeOfIssue;
	private String createBy;
	private String regSiteCode;
	private String phoiSerialNo;
	private String placeOfBirth;
	

	public String getPlaceOfBirth() {
		return placeOfBirth;
	}

	public void setPlaceOfBirth(String placeOfBirth) {
		this.placeOfBirth = placeOfBirth;
	}

	public String getPhoiSerialNo() {
		return phoiSerialNo;
	}

	public void setPhoiSerialNo(String phoiSerialNo) {
		this.phoiSerialNo = phoiSerialNo;
	}

	public String getPlaceOfIssue() {
		return placeOfIssue;
	}

	public void setPlaceOfIssue(String placeOfIssue) {
		this.placeOfIssue = placeOfIssue;
	}

	public String getCreateBy() {
		return createBy;
	}

	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}

	public String getRegSiteCode() {
		return regSiteCode;
	}

	public void setRegSiteCode(String regSiteCode) {
		this.regSiteCode = regSiteCode;
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

}
