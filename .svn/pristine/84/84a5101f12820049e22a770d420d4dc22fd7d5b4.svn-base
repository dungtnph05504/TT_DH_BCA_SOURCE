package com.nec.asia.nic.comp.immihistory.model;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import org.codehaus.jackson.map.annotate.JsonSerialize;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.nec.asia.nic.comp.immihistory.domain.ImmiHistory;
import com.nec.asia.nic.util.DateHandler;


@XmlRootElement(name="Immihistory")
@XmlType(name="Immihistory", propOrder =  {"createdBy", "lastModifiedBy", "createdTime", "lastModifiedTime", "immigrationTime", "transactionId", "immiType", "workstationCode", "borderGateCode", "firstName", "middleName", "lastName"
	,"fullName", "fullNameWithout", "placeOfBirthCode", "identityCardNo", "dateOfBirth", "defDateOfBirth", "gender", "countryCode", "passportNo", "passportType", "passportIssuePlaceCode", "passportExpiredDate", "icaoLine"
	,"personId", "personType", "caSerialNumber", "caSignedDate", "caValidFromDate", "caValidToDate", "visaNo", "visaTypeCode", "visaValue", "visaSymbolCode", "visaIssuePlaceCode", "visaIssueDate", "freeVisaId", "residenceUntilDate", "flightNo"
	,"purposeCode", "purposeName", "preprocessSkey", "gateNote", "checkCaResult", "checkBlackListResult", "checkBlackListIdStr", "checkDocumentResult", "systemCheckResult", "supervisorFullname", "supervisorResult", "supervisorNote", "adminFullname", "adminResult", "adminNote"
	,"deleteFlag", "idQueue", "syncType", "childrens", "images"})
public class Immihistory {
	
    
    private String createdBy;
   
    private String lastModifiedBy;
    @JsonSerialize(using=DateHandler.class)
    private Date createdTime;
    @JsonSerialize(using=DateHandler.class)
    private Date lastModifiedTime;    
    @JsonSerialize(using=DateHandler.class)
    private Date immigrationTime;
   
    private String transactionId;
    
    private String immiType;
    
    private Integer workstationCode;
    
    private String borderGateCode;
   
    private String firstName;
    
    private String middleName;
   
    private String lastName;
    
    private String fullName;
  
    private String fullNameWithout;
    
    private String placeOfBirthCode;
    
    private String identityCardNo;
    @JsonSerialize(using=DateHandler.class)
    private Date dateOfBirth;
    
    private String defDateOfBirth;
   
    private String gender;
   
    private String countryCode;
   
    private String passportNo;
    
    private String passportType;
   
    private String passportIssuePlaceCode;
    @JsonSerialize(using=DateHandler.class)
    private Date passportExpiredDate;
    
    private String icaoLine;
    
    private String personId;
    
    private String personType;
    
    private String caSerialNumber;
    @JsonSerialize(using=DateHandler.class)
    private Date caSignedDate;
    @JsonSerialize(using=DateHandler.class)
    private Date caValidFromDate;
    @JsonSerialize(using=DateHandler.class)
    private Date caValidToDate;
    
    private String visaNo;
    
    private String visaTypeCode;
   
    private String visaValue;
    
    private String visaSymbolCode;
    
    private String visaIssuePlaceCode;
    @JsonSerialize(using=DateHandler.class)
    private Date visaIssueDate;
   
    private Integer freeVisaId;
    @JsonSerialize(using=DateHandler.class)
    private Date residenceUntilDate;
   
    private String flightNo;
   
    private String purposeCode;
    
    private String purposeName;
    
    private Long preprocessSkey;
    
    private String gateNote;
   
    private String checkCaResult;
    
    private String checkBlackListResult;
    
    private String checkBlackListIdStr;
    
    private String checkDocumentResult;
   
    private Integer systemCheckResult;
   
    private String supervisorFullname;
   
    private Integer supervisorResult;
    
    private String supervisorNote;
   
    private String adminFullname;
    
    private Integer adminResult;
    
    private String adminNote;
   
    private String deleteFlag;
    
    private Long idQueue;
    
    private String syncType;
    
    private List<ImmihistoryChildren> childrens;
    
    private List<ImmihistoryImage> images;
    
    
    
    
    

	public Immihistory() {
		super();
	}
	
	public Immihistory(ImmiHistory immi) {
		
		this.createdBy = immi.getCreatedBy();
		this.lastModifiedBy = immi.getLastModifiedBy();
		this.createdTime = immi.getCreatedTime();
		this.lastModifiedTime = immi.getLastModifiedTime();
//		if(immi.getImmigrationTime() != null){
//			DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//			this.immigrationTime = df.format(immi.getImmigrationTime());
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

	
	
	public String getSyncType() {
		return syncType;
	}

	public void setSyncType(String syncType) {
		this.syncType = syncType;
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

	public List<ImmihistoryChildren> getChildrens() {
		return childrens;
	}

	public void setChildrens(List<ImmihistoryChildren> childrens) {
		this.childrens = childrens;
	}

	public List<ImmihistoryImage> getImages() {
		return images;
	}

	public void setImages(List<ImmihistoryImage> images) {
		this.images = images;
	}

	public Long getIdQueue() {
		return idQueue;
	}

	public void setIdQueue(Long idQueue) {
		this.idQueue = idQueue;
	}

	public Integer getAdminResult() {
		return adminResult;
	}

	public void setAdminResult(Integer adminResult) {
		this.adminResult = adminResult;
	}
    
    
}
