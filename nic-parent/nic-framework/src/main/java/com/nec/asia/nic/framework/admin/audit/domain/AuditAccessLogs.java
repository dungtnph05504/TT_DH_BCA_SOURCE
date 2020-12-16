package com.nec.asia.nic.framework.admin.audit.domain;

import java.util.Date;

/* 
 * Modification History:
 * 
 * 22 Dec 2015 (chris): Code Refactoring.
 */
public class AuditAccessLogs implements java.io.Serializable {

	private static final long serialVersionUID = -4794968410838254917L;
	
	private long accessLogId;
	private String userId;
	private String wkstnId;
	private String functionName;
	private String errorFlag;
	private Date auditDate;
	private Long timeTaken;
	private String serverId;
	private String sessionId;
	private String paramValues;
	private String accessLogData;
	private String archiveFlag;
	private String archiveBy;
	private Date archiveDate;

	public AuditAccessLogs() {
	}

	public AuditAccessLogs(long accessLogId, String userId,
			String wkstnId, String functionName, Date auditDate) {
		this.accessLogId = accessLogId;
		this.userId = userId;
		this.wkstnId = wkstnId;
		this.functionName = functionName;
		this.auditDate = auditDate;
	}

	public AuditAccessLogs(long accessLogId, String userId,
			String wkstnId, String functionName, String errorFlag,
			Date auditDate, Long timeTaken, String serverId, String sessionId,
			String paramValues, String accessLogData, String archiveFlag,
			String archiveBy, Date archiveDate) {
		this.accessLogId = accessLogId;
		this.userId = userId;
		this.wkstnId = wkstnId;
		this.functionName = functionName;
		this.errorFlag = errorFlag;
		this.auditDate = auditDate;
		this.timeTaken = timeTaken;
		this.serverId = serverId;
		this.sessionId = sessionId;
		this.paramValues = paramValues;
		this.accessLogData = accessLogData;
		this.archiveFlag = archiveFlag;
		this.archiveBy = archiveBy;
		this.archiveDate = archiveDate;
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

	public Date getAuditDate() {
		return this.auditDate;
	}

	public void setAuditDate(Date auditDate) {
		this.auditDate = auditDate;
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

	public Date getArchiveDate() {
		return this.archiveDate;
	}

	public void setArchiveDate(Date archiveDate) {
		this.archiveDate = archiveDate;
	}

}
