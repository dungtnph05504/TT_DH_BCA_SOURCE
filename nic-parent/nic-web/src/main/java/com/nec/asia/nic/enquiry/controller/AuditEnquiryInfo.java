package com.nec.asia.nic.enquiry.controller;

// Generated Jun 17, 2013 1:58:48 PM by Hibernate Tools 3.4.0.CR1

import java.math.BigDecimal;

import java.util.Date;

public class AuditEnquiryInfo implements java.io.Serializable {

	private long accessLogId;
	private String userId;
	private String wkstnId;
	private String functionName;
	private String errorFlag;
	private String auditDateStr;
	private Long timeTaken;
	private String serverId;
	private String sessionId;
	private String paramValues;
	private String accessLogData;
	private String archiveFlag;
	private String archiveBy;
	//private String archiveDate;

	private String loginDateFrom;//Added by Sailaja for Audit Enquiry
	private String loginDateTo;//Added by Sailaja for Audit Enquiry
	private String logoutDateFrom;//Added by Sailaja for Audit Enquiry
	private String logoutDateTo;//Added by Sailaja for Audit Enquiry
	
	private String fixBootstrap;
    
	
	
	public String getFixBootstrap() {
		return fixBootstrap;
	}

	public void setFixBootstrap(String fixBootstrap) {
		this.fixBootstrap = fixBootstrap;
	}

	public String getLoginDateFrom() {
		return loginDateFrom;
	}

	public void setLoginDateFrom(String loginDateFrom) {
		this.loginDateFrom = loginDateFrom;
	}

	public String getLoginDateTo() {
		return loginDateTo;
	}

	public void setLoginDateTo(String loginDateTo) {
		this.loginDateTo = loginDateTo;
	}

	public String getLogoutDateFrom() {
		return logoutDateFrom;
	}

	public void setLogoutDateFrom(String logoutDateFrom) {
		this.logoutDateFrom = logoutDateFrom;
	}

	public String getLogoutDateTo() {
		return logoutDateTo;
	}

	public void setLogoutDateTo(String logoutDateTo) {
		this.logoutDateTo = logoutDateTo;
	}

	public long getAccessLogId() {
		return this.accessLogId;
	}

	public void setAccessLogId(long accessLogId) {
		this.accessLogId = accessLogId;
	}

	public String getUserId() {
		return this.userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getWkstnId() {
		return this.wkstnId;
	}

	public void setWkstnId(String wkstnId) {
		this.wkstnId = wkstnId;
	}

	public String getFunctionName() {
		return this.functionName;
	}

	public void setFunctionName(String functionName) {
		this.functionName = functionName;
	}

	public String getErrorFlag() {
		return this.errorFlag;
	}

	public void setErrorFlag(String errorFlag) {
		this.errorFlag = errorFlag;
	}


	public String getAuditDateStr() {
		return auditDateStr;
	}

	public void setAuditDateStr(String auditDateStr) {
		this.auditDateStr = auditDateStr;
	}

	public Long getTimeTaken() {
		return this.timeTaken;
	}

	public void setTimeTaken(Long timeTaken) {
		this.timeTaken = timeTaken;
	}

	public String getServerId() {
		return this.serverId;
	}

	public void setServerId(String serverId) {
		this.serverId = serverId;
	}

	public String getSessionId() {
		return this.sessionId;
	}

	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}

	public String getParamValues() {
		return this.paramValues;
	}

	public void setParamValues(String paramValues) {
		this.paramValues = paramValues;
	}

	public String getAccessLogData() {
		return this.accessLogData;
	}

	public void setAccessLogData(String accessLogData) {
		this.accessLogData = accessLogData;
	}

	public String getArchiveFlag() {
		return this.archiveFlag;
	}

	public void setArchiveFlag(String archiveFlag) {
		this.archiveFlag = archiveFlag;
	}

	public String getArchiveBy() {
		return this.archiveBy;
	}

	public void setArchiveBy(String archiveBy) {
		this.archiveBy = archiveBy;
	}

	/*public String getArchiveDate() {
		return this.archiveDate;
	}

	public void setArchiveDate(String archiveDate) {
		this.archiveDate = archiveDate;
	}*/
	
}
