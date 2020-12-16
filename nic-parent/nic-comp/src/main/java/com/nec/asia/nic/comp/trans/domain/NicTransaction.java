package com.nec.asia.nic.comp.trans.domain;
// Generated Jan 20, 2016 11:25:39 AM by Hibernate Tools 4.3.1.Final

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import com.nec.asia.nic.comp.listHandover.domain.NicListHandover;

/**
 * NicTransaction generated by hbm2java
 */
public class NicTransaction implements java.io.Serializable {
    /**
     * 
     */
    private static final long serialVersionUID = 4030959704968010599L;
    //CORE TRANSACTION
//    public static final String TRANSACTION_TYPE_REGISTRATION        = "REG";
//    public static final String TRANSACTION_TYPE_REPLACEMENT         = "REP";
//    public static final String TRANSACTION_TYPE_PARTICULAR_UPDATE   = "PAR_UPD";
//    public static final String TRANSACTION_TYPE_CONVERSION          = "CON";
//    //UPDATE OF TRANSACTION
//    public static final String TRANSACTION_TYPE_CARD_ISSUANCE       = "CAR_ISS";
//    public static final String TRANSACTION_TYPE_CARD_SUSPENSION     = "CAR_SUS";
//    public static final String TRANSACTION_TYPE_CARD_REACTIVATION   = "CAR_REA";
//    public static final String TRANSACTION_TYPE_CARD_TERMINATION    = "CAR_TER";
//    
//    public static final String TRANSACTION_SUBTYPE_RE_REG           = "RE-REG";
//    //Particular Update
//    public static final String TRANSACTION_SUBTYPE_UPDATE_FINGERPRINT   = "PAR_UPD_FP";
//    public static final String TRANSACTION_SUBTYPE_UPDATE_ADDRESS       = "PAR_UPD_ADDR";
    
    public static final String TRANSACTION_STATUS_RIC_ISSUED = "RIC_ISSUED";
    public static final String TRANSACTION_STATUS_RIC_DEACTIVATED = "RIC_DEACTIVATED";
    
    public static final String NEW = "NEW";
    public static final String RENEW_BY_LOST = "RENEW_BY_LOST";
    public static final String RENEW_BY_DAMAGE = "RENEW_BY_DAMAGE";
    public static final String RENEW_BY_EXPIRE = "RENEW_BY_EXPIRE";
    public static final String RENEW_BY_FULL = "RENEW_BY_FULL";
    public static final String RENEW_BY_ALMEXP = "RENEW_BY_ALMEXP";
    public static final String UPDATE = "UPDATE";
    public static final String CHILD = "CHILD";
    public static final String OTHER = "OTHER";

	public static final String TRANSACTION_STATUS_INVESTIGATION_PROCESSING = "INVESTIGATION_PROCESSING";
	public static final String TRANSACTION_STATUS_RIC_UPLOADED = "RIC_UPLOADED";

	public static final String TRANSACTION_STATUS_CREATED_C = "CREATED_C";

	public static final String TRANSACTION_STATUS_INVESTIGATION_SAVED = "INVESTIGATION_SAVED";
    
	private String transactionId;
	private String personCode;
	private String applnRefNo;
	private String nin;
	private Date dateOfApplication;
	private Date estDateOfCollection;
	private String passportType;
	private Integer validityPeriod;
	private String issuingAuthority;
	private Integer priority;
	private String regSiteCode;
	private String issSiteCode;
	private String nicSiteCode;
	private String transactionType;
	private String transactionStatus;
	private String prevPassportNo;
	private Date prevDateOfIssue;
	private String registrationNo;
	private String recieptNo;
	private Boolean isEpassport;
	private Boolean isPostOffice;
//	private String receiver;
	private String note;
	private String infoPerson;
	private String packageId;
	private String createBy;
	private String createWkstnId;
	private Date createDatetime;
	private String updateBy;
	private String updateWkstnId;
	private Date updateDatetime;
	private String prevDateOfExpr;
	private String appointmentPlace;
	private String transactionSubType;
	private String applicant;
	private String registrationType;
	private Long paBlacklistId;
	private String paInqBllUser;
	private String paArchiveCode;
	private Boolean paSearchBio;
	private String description;
	private NicRegistrationData nicRegistrationData;
	private NicTransactionPayment nicTransactionPayment;
	private Set<NicWorkflowJob> nicWorkflowJobs = new HashSet<NicWorkflowJob>(0);
	private Set<NicTransactionAttachment> nicTransactionAttachments = new HashSet<NicTransactionAttachment>(0);
	private Set<NicSearchResult> nicSearchResults = new HashSet<NicSearchResult>(0);
	
	// to retrieve separately
	private Set<NicDocumentData> nicDocumentDatas = new HashSet<NicDocumentData>(0);
	private Set<NicListHandover> listHandovers = new HashSet<NicListHandover>(0);

	private String dateOfBirthDesc;
	private String createDesc;
	private String religionDesc;
	private String nationDesc;
	private String dateNinDesc;
	private String datePassportDesc;
	private String spouseDobDesc;
	private String motherDobDesc;
	private String fatherDobDesc;
	private String dateDecisionDesc;
	private String estimateFromDesc;
	private String dateCountryDesc;
	
	private Date paJoinPersonDate;
	private Date paSearchObjDate;
	private Date paSaveDocDate;
	private String archiveCode;
	private String matchedTypePerson;
	
	/*private Long printPerso;
	private Integer syncPassport;
	private String leaderOfficerId;
	private String noteHandoverB;
	private String tranType;
	private Boolean passportStyle;
	private String dateOfBirthDesc;
	private String createDesc;
	private String religionDesc;
	private String nationDesc;
	private String dateNinDesc;
	private String datePassportDesc;
	private String spouseDobDesc;
	private String motherDobDesc;
	private String fatherDobDesc;
	private String dateDecisionDesc;
	private String estimateFromDesc;
	private String dateCountryDesc;*/
	
	public NicTransaction() {
	}

	public NicTransaction(String transactionId) {
		this.transactionId = transactionId;
	}

	

	public NicTransaction(String transactionId, String personCode,
			String applnRefNo, String nin, Date dateOfApplication,
			Date estDateOfCollection, String passportType,
			Integer validityPeriod, String issuingAuthority, Integer priority,
			String regSiteCode, String issSiteCode, String nicSiteCode,
			String transactionType, String transactionStatus,
			String prevPassportNo, Date prevDateOfIssue, String registrationNo,
			String recieptNo, Boolean isEpassport, Boolean isPostOffice,
			String note, String infoPerson, String packageId, String createBy,
			String createWkstnId, Date createDatetime, String updateBy,
			String updateWkstnId, Date updateDatetime, String prevDateOfExpr,
			String appointmentPlace, String transactionSubType,
			String applicant, String registrationType, Long paBlacklistId,
			String paInqBllUser, String paArchiveCode, Boolean paSearchBio,
			String description, NicRegistrationData nicRegistrationData,
			NicTransactionPayment nicTransactionPayment,
			Set<NicWorkflowJob> nicWorkflowJobs,
			Set<NicTransactionAttachment> nicTransactionAttachments,
			Set<NicSearchResult> nicSearchResults,
			Set<NicDocumentData> nicDocumentDatas,
			Set<NicListHandover> listHandovers, String dateOfBirthDesc,
			String createDesc, String religionDesc, String nationDesc,
			String dateNinDesc, String datePassportDesc, String spouseDobDesc,
			String motherDobDesc, String fatherDobDesc,
			String dateDecisionDesc, String estimateFromDesc,
			String dateCountryDesc, Date paJoinPersonDate,
			Date paSearchObjDate, Date paSaveDocDate, String archiveCode,
			String matchedTypePerson) {
		super();
		this.transactionId = transactionId;
		this.personCode = personCode;
		this.applnRefNo = applnRefNo;
		this.nin = nin;
		this.dateOfApplication = dateOfApplication;
		this.estDateOfCollection = estDateOfCollection;
		this.passportType = passportType;
		this.validityPeriod = validityPeriod;
		this.issuingAuthority = issuingAuthority;
		this.priority = priority;
		this.regSiteCode = regSiteCode;
		this.issSiteCode = issSiteCode;
		this.nicSiteCode = nicSiteCode;
		this.transactionType = transactionType;
		this.transactionStatus = transactionStatus;
		this.prevPassportNo = prevPassportNo;
		this.prevDateOfIssue = prevDateOfIssue;
		this.registrationNo = registrationNo;
		this.recieptNo = recieptNo;
		this.isEpassport = isEpassport;
		this.isPostOffice = isPostOffice;
		this.note = note;
		this.infoPerson = infoPerson;
		this.packageId = packageId;
		this.createBy = createBy;
		this.createWkstnId = createWkstnId;
		this.createDatetime = createDatetime;
		this.updateBy = updateBy;
		this.updateWkstnId = updateWkstnId;
		this.updateDatetime = updateDatetime;
		this.prevDateOfExpr = prevDateOfExpr;
		this.appointmentPlace = appointmentPlace;
		this.transactionSubType = transactionSubType;
		this.applicant = applicant;
		this.registrationType = registrationType;
		this.paBlacklistId = paBlacklistId;
		this.paInqBllUser = paInqBllUser;
		this.paArchiveCode = paArchiveCode;
		this.paSearchBio = paSearchBio;
		this.description = description;
		this.nicRegistrationData = nicRegistrationData;
		this.nicTransactionPayment = nicTransactionPayment;
		this.nicWorkflowJobs = nicWorkflowJobs;
		this.nicTransactionAttachments = nicTransactionAttachments;
		this.nicSearchResults = nicSearchResults;
		this.nicDocumentDatas = nicDocumentDatas;
		this.listHandovers = listHandovers;
		this.dateOfBirthDesc = dateOfBirthDesc;
		this.createDesc = createDesc;
		this.religionDesc = religionDesc;
		this.nationDesc = nationDesc;
		this.dateNinDesc = dateNinDesc;
		this.datePassportDesc = datePassportDesc;
		this.spouseDobDesc = spouseDobDesc;
		this.motherDobDesc = motherDobDesc;
		this.fatherDobDesc = fatherDobDesc;
		this.dateDecisionDesc = dateDecisionDesc;
		this.estimateFromDesc = estimateFromDesc;
		this.dateCountryDesc = dateCountryDesc;
		this.paJoinPersonDate = paJoinPersonDate;
		this.paSearchObjDate = paSearchObjDate;
		this.paSaveDocDate = paSaveDocDate;
		this.archiveCode = archiveCode;
		this.matchedTypePerson = matchedTypePerson;
	}

	public String getMatchedTypePerson() {
		return matchedTypePerson;
	}

	public void setMatchedTypePerson(String matchedTypePerson) {
		this.matchedTypePerson = matchedTypePerson;
	}

	public String getArchiveCode() {
		return archiveCode;
	}

	public void setArchiveCode(String archiveCode) {
		this.archiveCode = archiveCode;
	}

	public Date getPaJoinPersonDate() {
		return paJoinPersonDate;
	}

	public void setPaJoinPersonDate(Date paJoinPersonDate) {
		this.paJoinPersonDate = paJoinPersonDate;
	}

	public Date getPaSearchObjDate() {
		return paSearchObjDate;
	}

	public void setPaSearchObjDate(Date paSearchObjDate) {
		this.paSearchObjDate = paSearchObjDate;
	}

	public Date getPaSaveDocDate() {
		return paSaveDocDate;
	}

	public void setPaSaveDocDate(Date paSaveDocDate) {
		this.paSaveDocDate = paSaveDocDate;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}

	public String getPersonCode() {
		return personCode;
	}

	public void setPersonCode(String personCode) {
		this.personCode = personCode;
	}

	public String getApplnRefNo() {
		return applnRefNo;
	}

	public void setApplnRefNo(String applnRefNo) {
		this.applnRefNo = applnRefNo;
	}

	public String getNin() {
		return nin;
	}

	public void setNin(String nin) {
		this.nin = nin;
	}

	public Date getDateOfApplication() {
		return dateOfApplication;
	}

	public void setDateOfApplication(Date dateOfApplication) {
		this.dateOfApplication = dateOfApplication;
	}

	public Date getEstDateOfCollection() {
		return estDateOfCollection;
	}

	public void setEstDateOfCollection(Date estDateOfCollection) {
		this.estDateOfCollection = estDateOfCollection;
	}

	public String getPassportType() {
		return passportType;
	}

	public void setPassportType(String passportType) {
		this.passportType = passportType;
	}

	public Integer getValidityPeriod() {
		return validityPeriod;
	}

	public void setValidityPeriod(Integer validityPeriod) {
		this.validityPeriod = validityPeriod;
	}

	public String getIssuingAuthority() {
		return issuingAuthority;
	}

	public void setIssuingAuthority(String issuingAuthority) {
		this.issuingAuthority = issuingAuthority;
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

	public String getNicSiteCode() {
		return nicSiteCode;
	}

	public void setNicSiteCode(String nicSiteCode) {
		this.nicSiteCode = nicSiteCode;
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

	public String getPrevPassportNo() {
		return prevPassportNo;
	}

	public void setPrevPassportNo(String prevPassportNo) {
		this.prevPassportNo = prevPassportNo;
	}

	public Date getPrevDateOfIssue() {
		return prevDateOfIssue;
	}

	public void setPrevDateOfIssue(Date prevDateOfIssue) {
		this.prevDateOfIssue = prevDateOfIssue;
	}

	public String getRegistrationNo() {
		return registrationNo;
	}

	public void setRegistrationNo(String registrationNo) {
		this.registrationNo = registrationNo;
	}

	public String getRecieptNo() {
		return recieptNo;
	}

	public void setRecieptNo(String recieptNo) {
		this.recieptNo = recieptNo;
	}

	public Boolean getIsEpassport() {
		return isEpassport;
	}

	public void setIsEpassport(Boolean isEpassport) {
		this.isEpassport = isEpassport;
	}

	public Boolean getIsPostOffice() {
		return isPostOffice;
	}

	public void setIsPostOffice(Boolean isPostOffice) {
		this.isPostOffice = isPostOffice;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public String getInfoPerson() {
		return infoPerson;
	}

	public void setInfoPerson(String infoPerson) {
		this.infoPerson = infoPerson;
	}

	public String getPackageId() {
		return packageId;
	}

	public void setPackageId(String packageId) {
		this.packageId = packageId;
	}

	public String getCreateBy() {
		return createBy;
	}

	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}

	public String getCreateWkstnId() {
		return createWkstnId;
	}

	public void setCreateWkstnId(String createWkstnId) {
		this.createWkstnId = createWkstnId;
	}

	public Date getCreateDatetime() {
		return createDatetime;
	}

	public void setCreateDatetime(Date createDatetime) {
		this.createDatetime = createDatetime;
	}

	public String getUpdateBy() {
		return updateBy;
	}

	public void setUpdateBy(String updateBy) {
		this.updateBy = updateBy;
	}

	public String getUpdateWkstnId() {
		return updateWkstnId;
	}

	public void setUpdateWkstnId(String updateWkstnId) {
		this.updateWkstnId = updateWkstnId;
	}

	public Date getUpdateDatetime() {
		return updateDatetime;
	}

	public void setUpdateDatetime(Date updateDatetime) {
		this.updateDatetime = updateDatetime;
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

	public String getTransactionSubType() {
		return transactionSubType;
	}

	public void setTransactionSubType(String transactionSubType) {
		this.transactionSubType = transactionSubType;
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

	public Boolean getPaSearchBio() {
		return paSearchBio;
	}

	public void setPaSearchBio(Boolean paSearchBio) {
		this.paSearchBio = paSearchBio;
	}

	public NicRegistrationData getNicRegistrationData() {
		return nicRegistrationData;
	}

	public void setNicRegistrationData(NicRegistrationData nicRegistrationData) {
		this.nicRegistrationData = nicRegistrationData;
	}

	public NicTransactionPayment getNicTransactionPayment() {
		return nicTransactionPayment;
	}

	public void setNicTransactionPayment(NicTransactionPayment nicTransactionPayment) {
		this.nicTransactionPayment = nicTransactionPayment;
	}

	public Set<NicWorkflowJob> getNicWorkflowJobs() {
		return nicWorkflowJobs;
	}

	public void setNicWorkflowJobs(Set<NicWorkflowJob> nicWorkflowJobs) {
		this.nicWorkflowJobs = nicWorkflowJobs;
	}

	public Set<NicTransactionAttachment> getNicTransactionAttachments() {
		return nicTransactionAttachments;
	}

	public void setNicTransactionAttachments(
			Set<NicTransactionAttachment> nicTransactionAttachments) {
		this.nicTransactionAttachments = nicTransactionAttachments;
	}

	public Set<NicSearchResult> getNicSearchResults() {
		return nicSearchResults;
	}

	public void setNicSearchResults(Set<NicSearchResult> nicSearchResults) {
		this.nicSearchResults = nicSearchResults;
	}

	public Set<NicDocumentData> getNicDocumentDatas() {
		return nicDocumentDatas;
	}

	public void setNicDocumentDatas(Set<NicDocumentData> nicDocumentDatas) {
		this.nicDocumentDatas = nicDocumentDatas;
	}

	public Set<NicListHandover> getListHandovers() {
		return listHandovers;
	}

	public void setListHandovers(Set<NicListHandover> listHandovers) {
		this.listHandovers = listHandovers;
	}

	public String getDateOfBirthDesc() {
		return dateOfBirthDesc;
	}

	public void setDateOfBirthDesc(String dateOfBirthDesc) {
		this.dateOfBirthDesc = dateOfBirthDesc;
	}

	public String getCreateDesc() {
		return createDesc;
	}

	public void setCreateDesc(String createDesc) {
		this.createDesc = createDesc;
	}

	public String getReligionDesc() {
		return religionDesc;
	}

	public void setReligionDesc(String religionDesc) {
		this.religionDesc = religionDesc;
	}

	public String getNationDesc() {
		return nationDesc;
	}

	public void setNationDesc(String nationDesc) {
		this.nationDesc = nationDesc;
	}

	public String getDateNinDesc() {
		return dateNinDesc;
	}

	public void setDateNinDesc(String dateNinDesc) {
		this.dateNinDesc = dateNinDesc;
	}

	public String getDatePassportDesc() {
		return datePassportDesc;
	}

	public void setDatePassportDesc(String datePassportDesc) {
		this.datePassportDesc = datePassportDesc;
	}

	public String getSpouseDobDesc() {
		return spouseDobDesc;
	}

	public void setSpouseDobDesc(String spouseDobDesc) {
		this.spouseDobDesc = spouseDobDesc;
	}

	public String getMotherDobDesc() {
		return motherDobDesc;
	}

	public void setMotherDobDesc(String motherDobDesc) {
		this.motherDobDesc = motherDobDesc;
	}

	public String getFatherDobDesc() {
		return fatherDobDesc;
	}

	public void setFatherDobDesc(String fatherDobDesc) {
		this.fatherDobDesc = fatherDobDesc;
	}

	public String getDateDecisionDesc() {
		return dateDecisionDesc;
	}

	public void setDateDecisionDesc(String dateDecisionDesc) {
		this.dateDecisionDesc = dateDecisionDesc;
	}

	public String getEstimateFromDesc() {
		return estimateFromDesc;
	}

	public void setEstimateFromDesc(String estimateFromDesc) {
		this.estimateFromDesc = estimateFromDesc;
	}

	public String getDateCountryDesc() {
		return dateCountryDesc;
	}

	public void setDateCountryDesc(String dateCountryDesc) {
		this.dateCountryDesc = dateCountryDesc;
	}
}