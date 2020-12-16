package com.nec.asia.nic.investigation.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.nec.asia.nic.investigation.dto.CentralDto;
import com.nec.asia.nic.investigation.dto.TransmissionStatistics;

public class InvestigationAssignmentData {

	private static final Logger logger = LoggerFactory.getLogger(InvestigationAssignmentData.class);

	public static final String CONST__assignmentStatus__ALL = "ALL";
	public static final String CONST__assignmentStatus__ASSIGNED = "ASSIGNED";
	public static final String CONST__assignmentStatus__UNASSIGNED = "UNASSIGNED";

	private String searchTransactionId;
	private String assignmentStatus;
	private String[] selectedJobIds;
	private String[] processJobIds;
	private String assignToId;
	private String currentlyAssignedToUserId;
	private String unassignAllForUserUserId;

	private String transactionType;
	private String createDate;
	private String issueDate;
	private String passportType;
	private Integer priority;
	private String regSiteCode;
	private String typeInvestigation; 
	//Phúc thêm mã phiếu 6/5/2019
	private String[] packageId;
	private String packageCode;
	private String listCode;
	private String listName;
	private String typeList;
	private String createBy;
	private String endDate;
	
	private String validateInfoBca;
	private String jobId;
	private String packageNumber;
	private List<CentralDto> data; 
	private String[] loadJobIds;
	private String stageLoad;
	private String[] saveFullName;
	private String name;
	private String passportNo;
	private String nin;
	private String gender;
	private String dateOfBirth;
	private String dob;
	private String pob;
	private String passPortStage;
	private String reason;
	private String reasonNote;
	private String startYear;
	private String endYear;
	private String visaNo;
	private String url;
	private String nameApi;
	
	private String nameNationVie;
	private String passPortType;
	private int yearIssue;
	private String nation;
	private String description;
	private boolean status;
	private String imgPre;
	private String imgPreUV;
	private String imgFace;
	private TransmissionStatistics transmissionData;
	private int stt;

	//dũng thêm tham số form bordergate
	private String codeBordergate;
	
	
	private String officeCode;
	private String dateOfRegister;
	
	private String quantityList;
	private String dateList;
	
	
	
	

	public String getDateList() {
		return dateList;
	}

	public void setDateList(String dateList) {
		this.dateList = dateList;
	}




	public String getQuantityList() {
		return quantityList;
	}

	public void setQuantityList(String quantityList) {
		this.quantityList = quantityList;
	}

	public String getOfficeCode() {
		return officeCode;
	}

	public void setOfficeCode(String officeCode) {
		this.officeCode = officeCode;
	}

	public String getDateOfRegister() {
		return dateOfRegister;
	}

	public void setDateOfRegister(String dateOfRegister) {
		this.dateOfRegister = dateOfRegister;
	}

	public String getCodeBordergate() {
		return codeBordergate;
	}

	public void setCodeBordergate(String codeBordergate) {
		this.codeBordergate = codeBordergate;
	}

	private String pageNo;
	
	public String getPageNo() {
		return pageNo;
	}

	public void setPageNo(String pageNo) {
		this.pageNo = pageNo;
	}
	
	public int getStt() {
		return stt;
	}

	public void setStt(int stt) {
		this.stt = stt;
	}

	public String getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(String dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	public TransmissionStatistics getTransmissionData() {
		return transmissionData;
	}

	public void setTransmissionData(TransmissionStatistics transmissionData) {
		this.transmissionData = transmissionData;
	}

	public boolean isStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}

	public String getNation() {
		return nation;
	}

	public void setNation(String nation) {
		this.nation = nation;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getImgPre() {
		return imgPre;
	}

	public void setImgPre(String imgPre) {
		this.imgPre = imgPre;
	}

	public String getImgPreUV() {
		return imgPreUV;
	}

	public void setImgPreUV(String imgPreUV) {
		this.imgPreUV = imgPreUV;
	}

	public String getImgFace() {
		return imgFace;
	}

	public void setImgFace(String imgFace) {
		this.imgFace = imgFace;
	}

	public String getNameNationVie() {
		return nameNationVie;
	}

	public void setNameNationVie(String nameNationVie) {
		this.nameNationVie = nameNationVie;
	}

	public String getPassPortType() {
		return passPortType;
	}

	public void setPassPortType(String passPortType) {
		this.passPortType = passPortType;
	}

	public int getYearIssue() {
		return yearIssue;
	}

	public void setYearIssue(int yearIssue) {
		this.yearIssue = yearIssue;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getNameApi() {
		return nameApi;
	}

	public void setNameApi(String nameApi) {
		this.nameApi = nameApi;
	}

	public String getStartYear() {
		return startYear;
	}

	public void setStartYear(String startYear) {
		this.startYear = startYear;
	}

	public String getEndYear() {
		return endYear;
	}

	public void setEndYear(String endYear) {
		this.endYear = endYear;
	}

	public String getVisaNo() {
		return visaNo;
	}

	public void setVisaNo(String visaNo) {
		this.visaNo = visaNo;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public String getReasonNote() {
		return reasonNote;
	}

	public void setReasonNote(String reasonNote) {
		this.reasonNote = reasonNote;
	}

	public String getPassPortStage() {
		return passPortStage;
	}

	public void setPassPortStage(String passPortStage) {
		this.passPortStage = passPortStage;
	}

	public String getNin() {
		return nin;
	}

	public void setNin(String nin) {
		this.nin = nin;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getDob() {
		return dob;
	}

	public void setDob(String dob) {
		this.dob = dob;
	}

	public String getPob() {
		return pob;
	}

	public void setPob(String pob) {
		this.pob = pob;
	}

	public static Logger getLogger() {
		return logger;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPassportNo() {
		return passportNo;
	}

	public void setPassportNo(String passportNo) {
		this.passportNo = passportNo;
	}

	public String[] getSaveFullName() {
		return saveFullName;
	}

	public void setSaveFullName(String[] saveFullName) {
		this.saveFullName = saveFullName;
	}

	public String getStageLoad() {
		return stageLoad;
	}

	public void setStageLoad(String stageLoad) {
		this.stageLoad = stageLoad;
	}

	public String[] getLoadJobIds() {
		return loadJobIds;
	}

	public void setLoadJobIds(String[] loadJobIds) {
		this.loadJobIds = loadJobIds;
	}

	public List<CentralDto> getData() {
		return data;
	}

	public void setData(List<CentralDto> data) {
		this.data = data;
	}

	public String[] getProcessJobIds() {
		return processJobIds;
	}

	public void setProcessJobIds(String[] processJobIds) {
		this.processJobIds = processJobIds;
	}

	public String getPackageNumber() {
		return packageNumber;
	}

	public void setPackageNumber(String packageNumber) {
		this.packageNumber = packageNumber;
	}

	public String getJobId() {
		return jobId;
	}

	public void setJobId(String jobId) {
		this.jobId = jobId;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public String getValidateInfoBca() {
		return validateInfoBca;
	}

	public void setValidateInfoBca(String validateInfoBca) {
		this.validateInfoBca = validateInfoBca;
	}

	public String getCreateBy() {
		return createBy;
	}

	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}

	public String getTypeList() {
		return typeList;
	}

	public void setTypeList(String typeList) {
		this.typeList = typeList;
	}

	public String getListName() {
		return listName;
	}

	public void setListName(String listName) {
		this.listName = listName;
	}

	public String getListCode() {
		return listCode;
	}

	public void setListCode(String listCode) {
		this.listCode = listCode;
	}

	public String getPackageCode() {
		return packageCode;
	}

	public void setPackageCode(String packageCode) {
		this.packageCode = packageCode;
	}

	public String[] getPackageId() {
		return packageId;
	}

	public void setPackageId(String[] packageId) {
		this.packageId = packageId;
	}

	public InvestigationAssignmentData() {
		super();
		this.initialize();
	}

	public Boolean assignedOnly() {

		if (this.getAssignmentStatus() != null
				&& this.getAssignmentStatus().equals(InvestigationAssignmentData.CONST__assignmentStatus__ASSIGNED)) {
			return true;
		}

		if (this.getAssignmentStatus() != null
				&& this.getAssignmentStatus().equals(InvestigationAssignmentData.CONST__assignmentStatus__UNASSIGNED)) {
			return false;
		}

		return null;
	}

	private void initialize() {
		this.assignToId = "";
		this.searchTransactionId = "";
		this.assignmentStatus = InvestigationAssignmentData.CONST__assignmentStatus__ASSIGNED;
		this.initializeSelectedJobIds();
		this.currentlyAssignedToUserId = "";
		this.unassignAllForUserUserId = "";
		
		this.transactionType = "";
		this.createDate= null;
		this.issueDate= null;
		this.passportType = "";
		this.priority = null;
		this.regSiteCode = "";
		this.typeInvestigation = "";
	}

	public void initializeSelectedJobIds() {
		this.selectedJobIds = new String[] {};
	}

	private String capitalizeIfNotEmpty(String original) {

		if (original != null) {
			return original.trim().toUpperCase();
		} else {
			return original;
		}
	}
	
	private Date capitalizeIfNotEmptyDate(Date original) {

			return original;
	}

	public void cleanUpStrings() {

		this.currentlyAssignedToUserId = this.capitalizeIfNotEmpty(this.currentlyAssignedToUserId);
		this.searchTransactionId = this.capitalizeIfNotEmpty(this.searchTransactionId);
		this.assignToId = this.capitalizeIfNotEmpty(this.assignToId);
		this.unassignAllForUserUserId = this.capitalizeIfNotEmpty(this.unassignAllForUserUserId);
		
		this.transactionType = this.capitalizeIfNotEmpty(this.transactionType);
		this.passportType = this.capitalizeIfNotEmpty(this.passportType);
		this.regSiteCode = this.capitalizeIfNotEmpty(this.regSiteCode);
		this.typeInvestigation = this.capitalizeIfNotEmpty(this.typeInvestigation);
		this.createDate= this.capitalizeIfNotEmpty(this.createDate);
		this.issueDate= this.capitalizeIfNotEmpty(this.issueDate);
		this.endDate= this.capitalizeIfNotEmpty(this.endDate);
	}

	public void cleanUpForNextPage() {

		this.cleanUpStrings();

		this.initializeSelectedJobIds();
	}

	public List<Long> getSelectedJobIds_long() {

		List<Long> jobIds = new ArrayList<Long>();

		try {
			for (int i = 0; i < this.getSelectedJobIds().length; i++) {
				jobIds.add(Long.parseLong(this.getSelectedJobIds()[i]));
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.info("problem getting job id list");
			return new ArrayList<Long>();
		}

		return jobIds;
	}

	public String getSearchTransactionId() {
		return searchTransactionId;
	}

	public void setSearchTransactionId(String searchTransactionId) {
		this.searchTransactionId = searchTransactionId;
	}

	public String getAssignmentStatus() {
		return assignmentStatus;
	}

	public void setAssignmentStatus(String assignmentStatus) {
		this.assignmentStatus = assignmentStatus;
	}

	public String getAssignToId() {
		return assignToId;
	}

	public void setAssignToId(String assignToId) {
		this.assignToId = assignToId;
	}

	public String[] getSelectedJobIds() {
		return selectedJobIds;
	}

	public void setSelectedJobIds(String[] selectedJobIds) {
		this.selectedJobIds = selectedJobIds;
	}

	public String getTypeInvestigation() {
		return typeInvestigation;
	}

	public void setTypeInvestigation(String typeInvestigation) {
		this.typeInvestigation = typeInvestigation;
	}
	
	public String getCurrentlyAssignedToUserId() {
		return currentlyAssignedToUserId;
	}

	public void setCurrentlyAssignedToUserId(String currentlyAssignedToUserId) {
		this.currentlyAssignedToUserId = currentlyAssignedToUserId;
	}

	public String getUnassignAllForUserUserId() {
		return unassignAllForUserUserId;
	}

	public void setUnassignAllForUserUserId(String unassignAllForUserUserId) {
		this.unassignAllForUserUserId = unassignAllForUserUserId;
	}
	
	public String getTransactionType() {
		return transactionType;
	}

	public void setTransactionType(String transactionType) {
		this.transactionType = transactionType;
	}

	public String getCreateDate() {
		return createDate;
	}

	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}

	public String getIssueDate() {
		return issueDate;
	}

	public void setIssueDate(String issueDate) {
		this.issueDate = issueDate;
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

}
