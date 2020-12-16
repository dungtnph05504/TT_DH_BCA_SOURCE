package com.nec.asia.nic.comp.trans.dto;

import java.util.Date;

public class AssignmentFilterAll {

	private String transactionId;
	private String transactionType;
	private Integer priority;
	private Date createDate;
	private Date endDate;
	private Date issueDate;
	private String regSiteCode;
	private String passportType;
	private String typeInvestigation;
	private String packageCode;
	private String[] tranArray;
	private String createBy;
	private String typeList;
	private String validateInfoBca;

	public AssignmentFilterAll() {
		super();
	}

	public AssignmentFilterAll(String transactionId,Integer priority,String transactionType, Date createDate,
			Date issueDate, String regSiteCode, String passportType, String typeInvestigation) {
		super();
		this.transactionId = transactionId;
		this.priority = priority;
		this.transactionType = transactionType;
		this.createDate = createDate;
		this.issueDate = issueDate;
		this.regSiteCode = regSiteCode;
		this.passportType = passportType;
		this.typeInvestigation = typeInvestigation;
	}
	
	
	
	
	
	public AssignmentFilterAll(String transactionId,Integer priority, String transactionType,
			 Date createDate, Date issueDate,
			String regSiteCode, String passportType, String typeInvestigation,
			String packageCode) {
		super();
		this.transactionId = transactionId;
		this.priority = priority;
		this.transactionType = transactionType;
		this.createDate = createDate;
		this.issueDate = issueDate;
		this.regSiteCode = regSiteCode;
		this.passportType = passportType;
		this.typeInvestigation = typeInvestigation;
		this.packageCode = packageCode;
	}
	
	public AssignmentFilterAll( String typeInvestigation,
			String packageCode) {
		super();
		this.typeInvestigation = typeInvestigation;
		this.packageCode = packageCode;
	}
	
	
	public AssignmentFilterAll(String transactionId,Integer priority, String transactionType,
			 Date createDate, Date issueDate,
			String regSiteCode, String passportType, String typeInvestigation,
			String packageCode, String[] arr, String validateInfoBca) {
		super();
		this.transactionId = transactionId;
		this.priority = priority;
		this.transactionType = transactionType;
		this.createDate = createDate;
		this.issueDate = issueDate;
		this.regSiteCode = regSiteCode;
		this.passportType = passportType;
		this.typeInvestigation = typeInvestigation;
		this.packageCode = packageCode;
		this.tranArray = arr;
		this.validateInfoBca = validateInfoBca;
	}
	

	public AssignmentFilterAll(Date createDate, String regSiteCode,
			String packageCode) {
		super();
		this.createDate = createDate;
		this.regSiteCode = regSiteCode;
		this.packageCode = packageCode;
	}
	
	

	
	
	
	public AssignmentFilterAll(Date createDate, Date endDate,
			String typeInvestigation, String packageCode) {
		super();
		this.createDate = createDate;
		this.endDate = endDate;
		this.typeInvestigation = typeInvestigation;
		this.packageCode = packageCode;
	}

	public AssignmentFilterAll(Date createDate, String packageCode,
			String createBy, String typeList) {
		super();
		this.createDate = createDate;
		this.packageCode = packageCode;
		this.createBy = createBy;
		this.typeList = typeList;
	}

	
	
	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public String getValidateInfoBca() {
		return validateInfoBca;
	}

	public void setValidateInfoBca(String validateInfoBca) {
		this.validateInfoBca = validateInfoBca;
	}

	public String getTypeList() {
		return typeList;
	}

	public void setTypeList(String typeList) {
		this.typeList = typeList;
	}

	public String getCreateBy() {
		return createBy;
	}

	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}

	public String[] getTranArray() {
		return tranArray;
	}

	public void setTranArray(String[] tranArray) {
		this.tranArray = tranArray;
	}

	public String getPackageCode() {
		return packageCode;
	}

	public void setPackageCode(String packageCode) {
		this.packageCode = packageCode;
	}

	public String getTypeInvestigation() {
		return typeInvestigation;
	}

	public void setTypeInvestigation(String typeInvestigation) {
		this.typeInvestigation = typeInvestigation;
	}

	public Integer getPriority() {
		return priority;
	}

	public void setPriority(Integer priority) {
		this.priority = priority;
	}

	public String getTransactionType() {
		return transactionType;
	}

	public void setTransactionType(String transactionType) {
		this.transactionType = transactionType;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public Date getIssueDate() {
		return issueDate;
	}

	public void setIssueDate(Date issueDate) {
		this.issueDate = issueDate;
	}

	public String getRegSiteCode() {
		return regSiteCode;
	}

	public void setRegSiteCode(String regSiteCode) {
		this.regSiteCode = regSiteCode;
	}

	public String getPassportType() {
		return passportType;
	}

	public void setPassportType(String passportType) {
		this.passportType = passportType;
	}

	public String getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}
}
