package com.nec.asia.nic.comp.immihistory.domain;
// Generated Jan 20, 2016 11:25:39 AM by Hibernate Tools 4.3.1.Final

import java.util.Date;

import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlAccessType;

import com.nec.asia.nic.comp.immihistory.model.Immihistory;

/**
 * NicDecisionManager generated by hbm2java
 */
@XmlRootElement(name = "ImmiHistory")
@XmlAccessorType(XmlAccessType.FIELD)
public class ImmiHistory implements java.io.Serializable {
    /**
     * 
     */
    private static final long serialVersionUID = 4030959704968010599L; 
    
    @XmlElement(name = "id") 
    private Long id;
    @XmlElement(name = "createdBy") 
    private String createdBy;
    @XmlElement(name = "lastModifiedBy") 
    private String lastModifiedBy;
    @XmlElement(name = "createdTime") 
    private Date createdTime;
    @XmlElement(name="lastModifiedTime")
    private Date lastModifiedTime;
    @XmlElement(name="immigrationTime")
    private Date immigrationTime;
    @XmlElement(name="transactionId")
    private String transactionId;
    @XmlElement(name="immiType")
    private String immiType;
    @XmlElement(name="workstationCode")
    private Integer workstationCode;
    @XmlElement(name="borderGateCode")
    private String borderGateCode;
    @XmlElement(name="firstName")
    private String firstName;
    @XmlElement(name="middleName")
    private String middleName;
    @XmlElement(name="lastName")
    private String lastName;
    @XmlElement(name="fullName")
    private String fullName;
    @XmlElement(name="fullNameWithout")
    private String fullNameWithout;
    @XmlElement(name="placeOfBirthCode")
    private String placeOfBirthCode;
    @XmlElement(name="identityCardNo")
    private String identityCardNo;
    @XmlElement(name="dateOfBirth")
    private Date dateOfBirth;
    @XmlElement(name="defDateOfBirth")
    private String defDateOfBirth;
    @XmlElement(name="gender")
    private String gender;
    @XmlElement(name="countryCode")
    private String countryCode;
    @XmlElement(name="passportNo")
    private String passportNo;
    @XmlElement(name="passportType")
    private String passportType;
    @XmlElement(name="passportIssuePlaceCode")
    private String passportIssuePlaceCode;
    @XmlElement(name="passportExpiredDate")
    private Date passportExpiredDate;
    @XmlElement(name="icaoLine")
    private String icaoLine;
    @XmlElement(name="personId")
    private String personId;
    @XmlElement(name="personType")
    private String personType;
    @XmlElement(name="caSerialNumber")
    private String caSerialNumber;
    @XmlElement(name="caSignedDate")
    private Date caSignedDate;
    @XmlElement(name="caValidFromDate")
    private Date caValidFromDate;
    @XmlElement(name="caValidToDate")
    private Date caValidToDate;
    @XmlElement(name="visaNo")
    private String visaNo;
    @XmlElement(name="visaTypeCode")
    private String visaTypeCode;
    @XmlElement(name="visaValue")
    private String visaValue;
    @XmlElement(name="visaSymbolCode")
    private String visaSymbolCode;
    @XmlElement(name="visaIssuePlaceCode")
    private String visaIssuePlaceCode;
    @XmlElement(name="visaIssueDate")
    private Date visaIssueDate;
    @XmlElement(name="freeVisaId")
    private Integer freeVisaId;
    @XmlElement(name="residenceUntilDate")
    private Date residenceUntilDate;
    @XmlElement(name="flightNo")
    private String flightNo;
    @XmlElement(name="purposeCode")
    private String purposeCode;
    @XmlElement(name="purposeName")
    private String purposeName;
    @XmlElement(name="preprocessSkey")
    private Long preprocessSkey;
    @XmlElement(name="gateNote")
    private String gateNote;
    @XmlElement(name="checkCaResult")
    private String checkCaResult;
    @XmlElement(name="checkBlackListResult")
    private String checkBlackListResult;
    @XmlElement(name="checkBlackListIdStr")
    private String checkBlackListIdStr;
    @XmlElement(name="checkDocumentResult")
    private String checkDocumentResult;
    @XmlElement(name="systemCheckResult")
    private Integer systemCheckResult;
    @XmlElement(name="supervisorFullname")
    private String supervisorFullname;
    @XmlElement(name="supervisorResult")
    private Integer supervisorResult;
    @XmlElement(name="supervisorNote")
    private String supervisorNote;
    @XmlElement(name="adminFullname")
    private String adminFullname;
    @XmlElement(name="adminResult")
    private Integer adminResult;
    @XmlElement(name="adminNote")
    private String adminNote;
    @XmlElement(name="deleteFlag")
    private String deleteFlag;
    
    
    private int stt;
    private String crDate;
    
	public ImmiHistory() {
		super();
	}

	public ImmiHistory(Immihistory immi){
		//this.id = immi.getId();
		this.createdBy = immi.getCreatedBy();
		this.lastModifiedBy = immi.getLastModifiedBy();
		this.createdTime = immi.getCreatedTime();
		this.lastModifiedTime = immi.getLastModifiedTime();
//		if(StringUtils.isNotEmpty(immi.getImmigrationTime())){
//			try {
//				this.immigrationTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(immi.getImmigrationTime());				
//			} catch (Exception e) {}
//		}
		this.immigrationTime = immi.getImmigrationTime();
		this.transactionId = immi.getTransactionId();
		this.immiType = immi.getImmiType();
		this.workstationCode = immi.getWorkstationCode();
		this.borderGateCode = immi.getBorderGateCode();
		this.firstName = immi.getFirstName();
		this.middleName = immi.getMiddleName();
		this.lastName = immi.getLastName();
		this.fullName = immi.getFullName();
		this.fullNameWithout = immi.getFullNameWithout();
		this.placeOfBirthCode = immi.getPlaceOfBirthCode();
		this.identityCardNo = immi.getIdentityCardNo();
		this.dateOfBirth = immi.getDateOfBirth();
		this.defDateOfBirth = immi.getDefDateOfBirth();
		this.gender = immi.getGender();
		this.countryCode = immi.getCountryCode();
		this.passportNo = immi.getPassportNo();
		this.passportType = immi.getPassportType();
		this.passportIssuePlaceCode = immi.getPassportIssuePlaceCode();
		this.passportExpiredDate = immi.getPassportExpiredDate();
		this.icaoLine = immi.getIcaoLine();
		this.personId = immi.getPersonId();
		this.personType = immi.getPersonType();
		this.caSerialNumber = immi.getCaSerialNumber();
		this.caSignedDate = immi.getCaSignedDate();
		this.caValidFromDate = immi.getCaValidFromDate();
		this.caValidToDate = immi.getCaValidToDate();
		this.visaNo = immi.getVisaNo();
		this.visaTypeCode = immi.getVisaTypeCode();
		this.visaValue = immi.getVisaValue();
		this.visaSymbolCode = immi.getVisaSymbolCode();
		this.visaIssuePlaceCode = immi.getVisaIssuePlaceCode();
		this.visaIssueDate = immi.getVisaIssueDate();
		this.freeVisaId = immi.getFreeVisaId();
		this.residenceUntilDate = immi.getResidenceUntilDate();
		this.flightNo = immi.getFlightNo();
		this.purposeCode = immi.getPurposeCode();
		this.purposeName = immi.getPurposeName();
		this.preprocessSkey = immi.getPreprocessSkey();
		this.gateNote = immi.getGateNote();
		this.checkCaResult = immi.getCheckCaResult();
		this.checkBlackListResult = immi.getCheckBlackListResult();
		this.checkBlackListIdStr = immi.getCheckBlackListIdStr();
		this.checkDocumentResult = immi.getCheckDocumentResult();
		this.systemCheckResult = immi.getSystemCheckResult();
		this.supervisorFullname = immi.getSupervisorFullname();
		this.supervisorResult = immi.getSupervisorResult();
		this.supervisorNote = immi.getSupervisorNote();
		this.adminFullname = immi.getAdminFullname();
		this.adminResult = immi.getAdminResult();
		this.adminNote = immi.getAdminNote();
		this.deleteFlag = immi.getDeleteFlag();
	}

	public ImmiHistory(Long id, String createdBy, String lastModifiedBy,
			Date createdTime, Date lastModifiedTime, Date immigrationTime,
			String transactionId, String immiType, Integer workstationCode,
			String borderGateCode, String firstName, String middleName,
			String lastName, String fullName, String fullNameWithout,
			String placeOfBirthCode, String identityCardNo, Date dateOfBirth,
			String defDateOfBirth, String gender, String countryCode,
			String passportNo, String passportType,
			String passportIssuePlaceCode, Date passportExpiredDate,
			String icaoLine, String personId, String personType,
			String caSerialNumber, Date caSignedDate, Date caValidFromDate,
			Date caValidToDate, String visaNo, String visaTypeCode,
			String visaValue, String visaSymbolCode, String visaIssuePlaceCode,
			Date visaIssueDate, Integer freeVisaId, Date residenceUntilDate,
			String flightNo, String purposeCode, String purposeName,
			Long preprocessSkey, String gateNote, String checkCaResult,
			String checkBlackListResult, String checkBlackListIdStr,
			String checkDocumentResult, Integer systemCheckResult,
			String supervisorFullname, Integer supervisorResult,
			String supervisorNote, String adminFullname, Integer adminResult, String adminNote,
			String deleteFlag) {
		super();
		this.id = id;
		this.createdBy = createdBy;
		this.lastModifiedBy = lastModifiedBy;
		this.createdTime = createdTime;
		this.lastModifiedTime = lastModifiedTime;
		this.immigrationTime = immigrationTime;
		this.transactionId = transactionId;
		this.immiType = immiType;
		this.workstationCode = workstationCode;
		this.borderGateCode = borderGateCode;
		this.firstName = firstName;
		this.middleName = middleName;
		this.lastName = lastName;
		this.fullName = fullName;
		this.fullNameWithout = fullNameWithout;
		this.placeOfBirthCode = placeOfBirthCode;
		this.identityCardNo = identityCardNo;
		this.dateOfBirth = dateOfBirth;
		this.defDateOfBirth = defDateOfBirth;
		this.gender = gender;
		this.countryCode = countryCode;
		this.passportNo = passportNo;
		this.passportType = passportType;
		this.passportIssuePlaceCode = passportIssuePlaceCode;
		this.passportExpiredDate = passportExpiredDate;
		this.icaoLine = icaoLine;
		this.personId = personId;
		this.personType = personType;
		this.caSerialNumber = caSerialNumber;
		this.caSignedDate = caSignedDate;
		this.caValidFromDate = caValidFromDate;
		this.caValidToDate = caValidToDate;
		this.visaNo = visaNo;
		this.visaTypeCode = visaTypeCode;
		this.visaValue = visaValue;
		this.visaSymbolCode = visaSymbolCode;
		this.visaIssuePlaceCode = visaIssuePlaceCode;
		this.visaIssueDate = visaIssueDate;
		this.freeVisaId = freeVisaId;
		this.residenceUntilDate = residenceUntilDate;
		this.flightNo = flightNo;
		this.purposeCode = purposeCode;
		this.purposeName = purposeName;
		this.preprocessSkey = preprocessSkey;
		this.gateNote = gateNote;
		this.checkCaResult = checkCaResult;
		this.checkBlackListResult = checkBlackListResult;
		this.checkBlackListIdStr = checkBlackListIdStr;
		this.checkDocumentResult = checkDocumentResult;
		this.systemCheckResult = systemCheckResult;
		this.supervisorFullname = supervisorFullname;
		this.supervisorResult = supervisorResult;
		this.supervisorNote = supervisorNote;
		this.adminFullname = adminFullname;
		this.adminResult = adminResult;
		this.adminNote = adminNote;
		this.deleteFlag = deleteFlag;
	}

	

	public int getStt() {
		return stt;
	}

	public void setStt(int stt) {
		this.stt = stt;
	}

	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}


	public String getCreatedBy() {
		return createdBy;
	}


	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}


	public String getLastModifiedBy() {
		return lastModifiedBy;
	}


	public void setLastModifiedBy(String lastModifiedBy) {
		this.lastModifiedBy = lastModifiedBy;
	}


	public Date getCreatedTime() {
		return createdTime;
	}


	public void setCreatedTime(Date createdTime) {
		this.createdTime = createdTime;
	}


	public Date getLastModifiedTime() {
		return lastModifiedTime;
	}


	public void setLastModifiedTime(Date lastModifiedTime) {
		this.lastModifiedTime = lastModifiedTime;
	}
	
	

	public String getCrDate() {
		return crDate;
	}

	public void setCrDate(String crDate) {
		this.crDate = crDate;
	}

	public Date getImmigrationTime() {
		return immigrationTime;
	}


	public void setImmigrationTime(Date immigrationTime) {
		this.immigrationTime = immigrationTime;
	}


	public String getTransactionId() {
		return transactionId;
	}


	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}


	public String getImmiType() {
		return immiType;
	}


	public void setImmiType(String immiType) {
		this.immiType = immiType;
	}


	public Integer getWorkstationCode() {
		return workstationCode;
	}


	public void setWorkstationCode(Integer workstationCode) {
		this.workstationCode = workstationCode;
	}


	public String getBorderGateCode() {
		return borderGateCode;
	}


	public void setBorderGateCode(String borderGateCode) {
		this.borderGateCode = borderGateCode;
	}


	public String getFirstName() {
		return firstName;
	}


	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}


	public String getMiddleName() {
		return middleName;
	}


	public void setMiddleName(String middleName) {
		this.middleName = middleName;
	}


	public String getLastName() {
		return lastName;
	}


	public void setLastName(String lastName) {
		this.lastName = lastName;
	}


	public String getFullName() {
		return fullName;
	}


	public void setFullName(String fullName) {
		this.fullName = fullName;
	}


	public String getFullNameWithout() {
		return fullNameWithout;
	}


	public void setFullNameWithout(String fullNameWithout) {
		this.fullNameWithout = fullNameWithout;
	}


	public String getPlaceOfBirthCode() {
		return placeOfBirthCode;
	}


	public void setPlaceOfBirthCode(String placeOfBirthCode) {
		this.placeOfBirthCode = placeOfBirthCode;
	}


	public String getIdentityCardNo() {
		return identityCardNo;
	}


	public void setIdentityCardNo(String identityCardNo) {
		this.identityCardNo = identityCardNo;
	}


	public Date getDateOfBirth() {
		return dateOfBirth;
	}


	public void setDateOfBirth(Date dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}


	public String getDefDateOfBirth() {
		return defDateOfBirth;
	}


	public void setDefDateOfBirth(String defDateOfBirth) {
		this.defDateOfBirth = defDateOfBirth;
	}


	public String getGender() {
		return gender;
	}


	public void setGender(String gender) {
		this.gender = gender;
	}


	public String getCountryCode() {
		return countryCode;
	}


	public void setCountryCode(String countryCode) {
		this.countryCode = countryCode;
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


	public String getPassportIssuePlaceCode() {
		return passportIssuePlaceCode;
	}


	public void setPassportIssuePlaceCode(String passportIssuePlaceCode) {
		this.passportIssuePlaceCode = passportIssuePlaceCode;
	}


	public Date getPassportExpiredDate() {
		return passportExpiredDate;
	}


	public void setPassportExpiredDate(Date passportExpiredDate) {
		this.passportExpiredDate = passportExpiredDate;
	}


	public String getIcaoLine() {
		return icaoLine;
	}


	public void setIcaoLine(String icaoLine) {
		this.icaoLine = icaoLine;
	}


	


	public String getPersonId() {
		return personId;
	}

	public void setPersonId(String personId) {
		this.personId = personId;
	}

	public String getPersonType() {
		return personType;
	}


	public void setPersonType(String personType) {
		this.personType = personType;
	}


	public String getCaSerialNumber() {
		return caSerialNumber;
	}


	public void setCaSerialNumber(String caSerialNumber) {
		this.caSerialNumber = caSerialNumber;
	}


	public Date getCaSignedDate() {
		return caSignedDate;
	}


	public void setCaSignedDate(Date caSignedDate) {
		this.caSignedDate = caSignedDate;
	}


	public Date getCaValidFromDate() {
		return caValidFromDate;
	}


	public void setCaValidFromDate(Date caValidFromDate) {
		this.caValidFromDate = caValidFromDate;
	}


	public Date getCaValidToDate() {
		return caValidToDate;
	}


	public void setCaValidToDate(Date caValidToDate) {
		this.caValidToDate = caValidToDate;
	}


	public String getVisaNo() {
		return visaNo;
	}


	public void setVisaNo(String visaNo) {
		this.visaNo = visaNo;
	}


	public String getVisaTypeCode() {
		return visaTypeCode;
	}


	public void setVisaTypeCode(String visaTypeCode) {
		this.visaTypeCode = visaTypeCode;
	}


	public String getVisaValue() {
		return visaValue;
	}


	public void setVisaValue(String visaValue) {
		this.visaValue = visaValue;
	}


	public String getVisaSymbolCode() {
		return visaSymbolCode;
	}


	public void setVisaSymbolCode(String visaSymbolCode) {
		this.visaSymbolCode = visaSymbolCode;
	}


	public String getVisaIssuePlaceCode() {
		return visaIssuePlaceCode;
	}


	public void setVisaIssuePlaceCode(String visaIssuePlaceCode) {
		this.visaIssuePlaceCode = visaIssuePlaceCode;
	}


	public Date getVisaIssueDate() {
		return visaIssueDate;
	}


	public void setVisaIssueDate(Date visaIssueDate) {
		this.visaIssueDate = visaIssueDate;
	}


	public Integer getFreeVisaId() {
		return freeVisaId;
	}


	public void setFreeVisaId(Integer freeVisaId) {
		this.freeVisaId = freeVisaId;
	}


	public Date getResidenceUntilDate() {
		return residenceUntilDate;
	}


	public void setResidenceUntilDate(Date residenceUntilDate) {
		this.residenceUntilDate = residenceUntilDate;
	}


	public String getFlightNo() {
		return flightNo;
	}


	public void setFlightNo(String flightNo) {
		this.flightNo = flightNo;
	}


	public String getPurposeCode() {
		return purposeCode;
	}


	public void setPurposeCode(String purposeCode) {
		this.purposeCode = purposeCode;
	}


	public String getPurposeName() {
		return purposeName;
	}


	public void setPurposeName(String purposeName) {
		this.purposeName = purposeName;
	}


	

	public Long getPreprocessSkey() {
		return preprocessSkey;
	}

	public void setPreprocessSkey(Long preprocessSkey) {
		this.preprocessSkey = preprocessSkey;
	}

	public String getGateNote() {
		return gateNote;
	}


	public void setGateNote(String gateNote) {
		this.gateNote = gateNote;
	}


	public String getCheckCaResult() {
		return checkCaResult;
	}


	public void setCheckCaResult(String checkCaResult) {
		this.checkCaResult = checkCaResult;
	}


	public String getCheckBlackListResult() {
		return checkBlackListResult;
	}


	public void setCheckBlackListResult(String checkBlackListResult) {
		this.checkBlackListResult = checkBlackListResult;
	}


	public String getCheckBlackListIdStr() {
		return checkBlackListIdStr;
	}


	public void setCheckBlackListIdStr(String checkBlackListIdStr) {
		this.checkBlackListIdStr = checkBlackListIdStr;
	}


	public String getCheckDocumentResult() {
		return checkDocumentResult;
	}


	public void setCheckDocumentResult(String checkDocumentResult) {
		this.checkDocumentResult = checkDocumentResult;
	}


	public Integer getSystemCheckResult() {
		return systemCheckResult;
	}


	public void setSystemCheckResult(Integer systemCheckResult) {
		this.systemCheckResult = systemCheckResult;
	}


	public String getSupervisorFullname() {
		return supervisorFullname;
	}


	public void setSupervisorFullname(String supervisorFullname) {
		this.supervisorFullname = supervisorFullname;
	}


	public Integer getSupervisorResult() {
		return supervisorResult;
	}


	public void setSupervisorResult(Integer supervisorResult) {
		this.supervisorResult = supervisorResult;
	}


	public String getSupervisorNote() {
		return supervisorNote;
	}


	public void setSupervisorNote(String supervisorNote) {
		this.supervisorNote = supervisorNote;
	}


	public String getAdminFullname() {
		return adminFullname;
	}


	public void setAdminFullname(String adminFullname) {
		this.adminFullname = adminFullname;
	}


	public String getAdminNote() {
		return adminNote;
	}


	public void setAdminNote(String adminNote) {
		this.adminNote = adminNote;
	}


	public String getDeleteFlag() {
		return deleteFlag;
	}


	public void setDeleteFlag(String deleteFlag) {
		this.deleteFlag = deleteFlag;
	}

	public Integer getAdminResult() {
		return adminResult;
	}

	public void setAdminResult(Integer adminResult) {
		this.adminResult = adminResult;
	}

	
	
}