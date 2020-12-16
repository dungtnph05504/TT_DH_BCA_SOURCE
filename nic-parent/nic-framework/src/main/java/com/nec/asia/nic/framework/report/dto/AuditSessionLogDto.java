/**
 * 
 */
package com.nec.asia.nic.framework.report.dto;

import java.util.Date;

/**
 * @author Prasad_Karnati
 *
 */
public class AuditSessionLogDto implements java.io.Serializable {
	private long sessionLogId;
	private String sessionId;
	private String userId;
	private String wkstnId;
	private String loginDate;
	private String logoutDate;
	private String timeTaken;
	private String archiveFlag;
	private String archiveBy;
	private String archiveDate;
	/**
	 * @return the sessionLogId
	 */
	public long getSessionLogId() {
		return sessionLogId;
	}
	/**
	 * @param sessionLogId the sessionLogId to set
	 */
	public void setSessionLogId(long sessionLogId) {
		this.sessionLogId = sessionLogId;
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
	 * @return the loginDate
	 */
	public String getLoginDate() {
		return loginDate;
	}
	/**
	 * @param loginDate the loginDate to set
	 */
	public void setLoginDate(String loginDate) {
		this.loginDate = loginDate;
	}
	/**
	 * @return the logoutDate
	 */
	public String getLogoutDate() {
		return logoutDate;
	}
	/**
	 * @param logoutDate the logoutDate to set
	 */
	public void setLogoutDate(String logoutDate) {
		this.logoutDate = logoutDate;
	}
	/**
	 * @return the timeTaken
	 */
	public String getTimeTaken() {
		return timeTaken;
	}
	/**
	 * @param timeTaken the timeTaken to set
	 */
	public void setTimeTaken(String timeTaken) {
		this.timeTaken = timeTaken;
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
