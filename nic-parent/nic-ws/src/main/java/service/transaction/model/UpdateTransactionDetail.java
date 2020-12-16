package service.transaction.model;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement(name = "UpdateTransactionDetail")
@XmlType(name = "UpdateTransactionDetail", propOrder = { "transactionId", "nin",
		"dateOfApplication", "estDateOfCollection", "passportType", "priority",
		"regSiteCode", "issSiteCode", "transactionType", "transactionStatus",
		"checksum", "isPostOffice", "recieptNo", "registrationNo",
		"passportStyle", "regisData", "families", "documents",
		"prevPassportNo", "placeIssuance", "prevDateOfIssue", "idQueue",
		"prevDateOfExpr", "appointmentPlace", "applicant", "registrationType",
		"paBlacklistId", "paInqBllUser", "paArchiveCode", "paSearchBio",
		"description", "paJoinPersonDate", "paSearchObjDate", "paSaveDocDate",
		"officerCodeUpdate", "note" })
public class UpdateTransactionDetail {
	private String transactionId;
	private String nin;
	private String dateOfApplication;
	private String estDateOfCollection;
	private String passportType;
	private Integer priority;
	private String regSiteCode;
	private String issSiteCode;
	private String transactionType;
	private String transactionStatus;
	private String checksum;
	private String isPostOffice;
	private String recieptNo;
	private String registrationNo;
	private String passportStyle;
	private String prevPassportNo;
	private String placeIssuance;
	private String prevDateOfIssue;
	private Long idQueue;
	private RegistrationData regisData;
	private List<PersonFamily> families;
	private List<TransactionDocument> documents;

	private String prevDateOfExpr;
	private String appointmentPlace;
	private String applicant;
	private String registrationType;
	private Long paBlacklistId;
	private String paInqBllUser;
	private String paArchiveCode;
	private String paSearchBio;

	private String description;
	private String paJoinPersonDate;
	private String paSearchObjDate;
	private String paSaveDocDate;
	private String officerCodeUpdate;
	private String note; //chi tiết nội dung đề nghị nếu có
	

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public String getOfficerCodeUpdate() {
		return officerCodeUpdate;
	}

	public void setOfficerCodeUpdate(String officerCodeUpdate) {
		this.officerCodeUpdate = officerCodeUpdate;
	}

	public String getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}

	public String getNin() {
		return nin;
	}

	public void setNin(String nin) {
		this.nin = nin;
	}

	public String getDateOfApplication() {
		return dateOfApplication;
	}

	public void setDateOfApplication(String dateOfApplication) {
		this.dateOfApplication = dateOfApplication;
	}

	public String getEstDateOfCollection() {
		return estDateOfCollection;
	}

	public void setEstDateOfCollection(String estDateOfCollection) {
		this.estDateOfCollection = estDateOfCollection;
	}

	public void setPrevDateOfIssue(String prevDateOfIssue) {
		this.prevDateOfIssue = prevDateOfIssue;
	}

	public String getPassportType() {
		return passportType;
	}

	public void setPassportType(String passportType) {
		this.passportType = passportType;
	}

	public Integer getPriority() {
		return priority;
	}

	public void setPriority(Integer priority) {
		this.priority = priority;
	}

	public String getRegSiteCode() {
		return regSiteCode;
	}

	public void setRegSiteCode(String regSiteCode) {
		this.regSiteCode = regSiteCode;
	}

	public String getIssSiteCode() {
		return issSiteCode;
	}

	public void setIssSiteCode(String issSiteCode) {
		this.issSiteCode = issSiteCode;
	}

	public String getTransactionType() {
		return transactionType;
	}

	public void setTransactionType(String transactionType) {
		this.transactionType = transactionType;
	}

	public String getTransactionStatus() {
		return transactionStatus;
	}

	public void setTransactionStatus(String transactionStatus) {
		this.transactionStatus = transactionStatus;
	}

	public String getChecksum() {
		return checksum;
	}

	public void setChecksum(String checksum) {
		this.checksum = checksum;
	}

	public String getIsPostOffice() {
		return isPostOffice;
	}

	public void setIsPostOffice(String isPostOffice) {
		this.isPostOffice = isPostOffice;
	}

	public String getRecieptNo() {
		return recieptNo;
	}

	public void setRecieptNo(String recieptNo) {
		this.recieptNo = recieptNo;
	}

	public String getRegistrationNo() {
		return registrationNo;
	}

	public void setRegistrationNo(String registrationNo) {
		this.registrationNo = registrationNo;
	}

	public String getPassportStyle() {
		return passportStyle;
	}

	public void setPassportStyle(String passportStyle) {
		this.passportStyle = passportStyle;
	}

	public RegistrationData getRegisData() {
		return regisData;
	}

	public void setRegisData(RegistrationData regisData) {
		this.regisData = regisData;
	}

	public List<PersonFamily> getFamilies() {
		return families;
	}

	public void setFamilies(List<PersonFamily> families) {
		this.families = families;
	}

	public List<TransactionDocument> getDocuments() {
		return documents;
	}

	public void setDocuments(List<TransactionDocument> documents) {
		this.documents = documents;
	}

	public String getPrevPassportNo() {
		return prevPassportNo;
	}

	public void setPrevPassportNo(String prevPassportNo) {
		this.prevPassportNo = prevPassportNo;
	}

	public String getPrevDateOfIssue() {
		return prevDateOfIssue;
	}

	public Long getIdQueue() {
		return idQueue;
	}

	public void setIdQueue(Long idQueue) {
		this.idQueue = idQueue;
	}

	public String getPlaceIssuance() {
		return placeIssuance;
	}

	public void setPlaceIssuance(String placeIssuance) {
		this.placeIssuance = placeIssuance;
	}

	public String getPrevDateOfExpr() {
		return prevDateOfExpr;
	}

	public void setPrevDateOfExpr(String prevDateOfExpr) {
		this.prevDateOfExpr = prevDateOfExpr;
	}

	public String getAppointmentPlace() {
		return appointmentPlace;
	}

	public void setAppointmentPlace(String appointmentPlace) {
		this.appointmentPlace = appointmentPlace;
	}

	public String getApplicant() {
		return applicant;
	}

	public void setApplicant(String applicant) {
		this.applicant = applicant;
	}

	public String getRegistrationType() {
		return registrationType;
	}

	public void setRegistrationType(String registrationType) {
		this.registrationType = registrationType;
	}

	public Long getPaBlacklistId() {
		return paBlacklistId;
	}

	public void setPaBlacklistId(Long paBlacklistId) {
		this.paBlacklistId = paBlacklistId;
	}

	public String getPaInqBllUser() {
		return paInqBllUser;
	}

	public void setPaInqBllUser(String paInqBllUser) {
		this.paInqBllUser = paInqBllUser;
	}

	public String getPaArchiveCode() {
		return paArchiveCode;
	}

	public void setPaArchiveCode(String paArchiveCode) {
		this.paArchiveCode = paArchiveCode;
	}

	public String getPaSearchBio() {
		return paSearchBio;
	}

	public void setPaSearchBio(String paSearchBio) {
		this.paSearchBio = paSearchBio;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getPaJoinPersonDate() {
		return paJoinPersonDate;
	}

	public void setPaJoinPersonDate(String paJoinPersonDate) {
		this.paJoinPersonDate = paJoinPersonDate;
	}

	public String getPaSearchObjDate() {
		return paSearchObjDate;
	}

	public void setPaSearchObjDate(String paSearchObjDate) {
		this.paSearchObjDate = paSearchObjDate;
	}

	public String getPaSaveDocDate() {
		return paSaveDocDate;
	}

	public void setPaSaveDocDate(String paSaveDocDate) {
		this.paSaveDocDate = paSaveDocDate;
	}
}
