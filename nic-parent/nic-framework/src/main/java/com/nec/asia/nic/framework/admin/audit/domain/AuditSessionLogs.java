package com.nec.asia.nic.framework.admin.audit.domain;

import java.util.Date;

/* 
 * Modification History:
 * 
 * 22 Dec 2015 (chris): Code Refactoring.
 */
public class AuditSessionLogs implements java.io.Serializable {

	private static final long serialVersionUID = -4090092264842792839L;
	
	private long sessionLogId;
	private String sessionId;
	private String userId;
	private String wkstnId;
	private Date loginDate;
	private Date logoutDate;
	private Long timeTaken;
	private String archiveFlag;
	private String archiveBy;
	private Date archiveDate;

	public AuditSessionLogs() {
	}

	public AuditSessionLogs(long sessionLogId) {
		this.sessionLogId = sessionLogId;
	}

	public AuditSessionLogs(long sessionLogId, String sessionId,
			String userId, String wkstnId, Date loginDate, Date logoutDate,
			Long timeTaken, String archiveFlag, String archiveBy,
			Date archiveDate) {
		this.sessionLogId = sessionLogId;
		this.sessionId = sessionId;
		this.userId = userId;
		this.wkstnId = wkstnId;
		this.loginDate = loginDate;
		this.logoutDate = logoutDate;
		this.timeTaken = timeTaken;
		this.archiveFlag = archiveFlag;
		this.archiveBy = archiveBy;
		this.archiveDate = archiveDate;
	}

	public long getSessionLogId() {
		return this.sessionLogId;
	}

	public void setSessionLogId(long sessionLogId) {
		this.sessionLogId = sessionLogId;
	}

	public String getSessionId() {
		return this.sessionId;
	}

	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
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

	public Date getLoginDate() {
		return this.loginDate;
	}

	public void setLoginDate(Date loginDate) {
		this.loginDate = loginDate;
	}

	public Date getLogoutDate() {
		return this.logoutDate;
	}

	public void setLogoutDate(Date logoutDate) {
		this.logoutDate = logoutDate;
	}

	public Long getTimeTaken() {
		return this.timeTaken;
	}

	public void setTimeTaken(Long timeTaken) {
		this.timeTaken = timeTaken;
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
