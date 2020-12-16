package service.transaction.model;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement(name="InfoPassportC")
@XmlType(name="InfoPassportC", propOrder={"chipSerialNo", "passportNo", "passportType", "dateOfIssue", "dateOfExpiry", "icaoLine1", "icaoLine2", "signerName", "signerPosition", "description", "status"
		, "placeOfIssueId", "placeOfIssueName", "personId", "fpEncode", "isEpassport"})
public class InfoPassportC {
	private String chipSerialNo;
	private String passportNo;
	private String passportType;
	private String dateOfIssue;
	private String dateOfExpiry;
	private String icaoLine1;
	private String icaoLine2;
	private String signerName;
	private String signerPosition;
	private String description;
	private String status;
	private String placeOfIssueId;
	private String placeOfIssueName;
	private String personId;
	private String fpEncode;
	private Boolean isEpassport;
	
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
	public String getIcaoLine1() {
		return icaoLine1;
	}
	public void setIcaoLine1(String icaoLine1) {
		this.icaoLine1 = icaoLine1;
	}
	public String getIcaoLine2() {
		return icaoLine2;
	}
	public void setIcaoLine2(String icaoLine2) {
		this.icaoLine2 = icaoLine2;
	}
	public String getSignerName() {
		return signerName;
	}
	public void setSignerName(String signerName) {
		this.signerName = signerName;
	}
	public String getSignerPosition() {
		return signerPosition;
	}
	public void setSignerPosition(String signerPosition) {
		this.signerPosition = signerPosition;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getPlaceOfIssueId() {
		return placeOfIssueId;
	}
	public void setPlaceOfIssueId(String placeOfIssueId) {
		this.placeOfIssueId = placeOfIssueId;
	}
	public String getPlaceOfIssueName() {
		return placeOfIssueName;
	}
	public void setPlaceOfIssueName(String placeOfIssueName) {
		this.placeOfIssueName = placeOfIssueName;
	}
	public String getPersonId() {
		return personId;
	}
	public void setPersonId(String personId) {
		this.personId = personId;
	}
	public String getFpEncode() {
		return fpEncode;
	}
	public void setFpEncode(String fpEncode) {
		this.fpEncode = fpEncode;
	}
	public Boolean getIsEpassport() {
		return isEpassport;
	}
	public void setIsEpassport(Boolean isEpassport) {
		this.isEpassport = isEpassport;
	}
	public String getPassportNo() {
		return passportNo;
	}
	public void setPassportNo(String passportNo) {
		this.passportNo = passportNo;
	}
	public String getPassportType() {
		return passportType;
	}
	public void setPassportType(String passportType) {
		this.passportType = passportType;
	}
	
	
}
