/**
 * 
 */
package com.nec.asia.nic.framework.report.dto;

import java.util.Date;

/**
 * @author Prasad_Karnati
 *
 */
public class AuditAccessLogDto implements java.io.Serializable{
	private long accessLogId;
	private String userId;
	private String wkstnId;
	private String functionName;
	private String errorFlag;
	private String auditDate;
	private Long timeTaken;
	private String serverId;
	private String sessionId;
	private String paramValues;
	private String accessLogData;
	private String archiveFlag;
	private String archiveBy;
	private String archiveDate;
	/**
	 * @return the accessLogId
	 */
	public long getAccessLogId() {
		return accessLogId;
	}
	/**
	 * @param accessLogId the accessLogId to set
	 */
	public void setAccessLogId(long accessLogId) {
		this.accessLogId = accessLogId;
	}
	/**
	 * @return the userId
	 */
	public String getUserId() {
		return userId;
	}
	/**
	 * @param userId the userId to set
	 */
	public void setUserId(String userId) {
		this.userId = userId;
	}
	/**
	 * @return the wkstnId
	 */
	public String getWkstnId() {
		return wkstnId;
	}
	/**
	 * @param wkstnId the wkstnId to set
	 */
	public void setWkstnId(String wkstnId) {
		this.wkstnId = wkstnId;
	}
	/**
	 * @return the functionName
	 */
	public String getFunctionName() {
		return functionName;
	}
	/**
	 * @param functionName the functionName to set
	 */
	public void setFunctionName(String functionName) {
		this.functionName = functionName;
	}
	/**
	 * @return the errorFlag
	 */
	public String getErrorFlag() {
		return errorFlag;
	}
	/**
	 * @param errorFlag the errorFlag to set
	 */
	public void setErrorFlag(String errorFlag) {
		this.errorFlag = errorFlag;
	}
	/**
	 * @return the auditDate
	 */
	public String getAuditDate() {
		return auditDate;
	}
	/**
	 * @param auditDate the auditDate to set
	 */
	public void setAuditDate(String auditDate) {
		this.auditDate = auditDate;
	}
	/**
	 * @return the timeTaken
	 */
	public Long getTimeTaken() {
		return timeTaken;
	}
	/**
	 * @param timeTaken the timeTaken to set
	 */
	public void setTimeTaken(Long timeTaken) {
		this.timeTaken = timeTaken;
	}
	/**
	 * @return the serverId
	 */
	public String getServerId() {
		return serverId;
	}
	/**
	 * @param serverId the serverId to set
	 */
	public void setServerId(String serverId) {
		this.serverId = serverId;
	}
	/**
	 * @return the sessionId
	 */
	public String getSessionId() {
		return sessionId;
	}
	/**
	 * @param sessionId the sessionId to set
	 */
	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}
	/**
	 * @return the paramValues
	 */
	public String getParamValues() {
		return paramValues;
	}
	/**
	 * @param paramValues the paramValues to set
	 */
	public void setParamValues(String paramValues) {
		this.paramValues = paramValues;
	}
	/**
	 * @return the accessLogData
	 */
	public String getAccessLogData() {
		return accessLogData;
	}
	/**
	 * @param accessLogData the accessLogData to set
	 */
	public void setAccessLogData(String accessLogData) {
		this.accessLogData = accessLogData;
	}
	/**
	 * @return the archiveFlag
	 */
	public String getArchiveFlag() {
		return archiveFlag;
	}
	/**
	 * @param archiveFlag the archiveFlag to set
	 */
	public void setArchiveFlag(String archiveFlag) {
		this.archiveFlag = archiveFlag;
	}
	/**
	 * @return the archiveBy
	 */
	public String getArchiveBy() {
		return archiveBy;
	}
	/**
	 * @param archiveBy the archiveBy to set
	 */
	public void setArchiveBy(String archiveBy) {
		this.archiveBy = archiveBy;
	}
	/**
	 * @return the archiveDate
	 */
	public String getArchiveDate() {
		return archiveDate;
	}
	/**
	 * @param archiveDate the archiveDate to set
	 */
	public void setArchiveDate(String archiveDate) {
		this.archiveDate = archiveDate;
	}
}
