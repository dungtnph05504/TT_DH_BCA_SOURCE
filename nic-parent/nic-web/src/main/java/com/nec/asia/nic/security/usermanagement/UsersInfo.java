package com.nec.asia.nic.security.usermanagement;

import java.util.Date;

/**
 * UserInfo
 */
public class UsersInfo{

	private String userId;
	private Boolean sysAdminFlag;
	private String siteCode;
	private Date userStartDate;
	private Date userEndDate;
	private Boolean activeIndicator;
	private String systemId;
	private String createBy;
	private String createWkstnId;
	private Date createDate;
	private String updateBy;
	private String updateWkstnId;
	private Date updateDate;
	private String deleteBy;
	private String deleteWkstnId;
	private Date deleteDate;
	private Boolean deleteFlag;
	private String firstName;
	private String userName;
	private String fmtCreateDate;
	private String fmtUpdateDate;
	private String siteGroupCode;
	
	
	
	public String getSiteGroupCode() {
		return siteGroupCode;
	}

	public void setSiteGroupCode(String siteGroupCode) {
		this.siteGroupCode = siteGroupCode;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	
	
	

	public String getUserId() {
		return this.userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public Boolean getSysAdminFlag() {
		return this.sysAdminFlag;
	}

	public void setSysAdminFlag(Boolean sysAdminFlag) {
		this.sysAdminFlag = sysAdminFlag;
	}

	public String getSiteCode() {
		return this.siteCode;
	}

	public void setSiteCode(String siteCode) {
		this.siteCode = siteCode;
	}

	public Date getUserStartDate() {
		return this.userStartDate;
	}

	public void setUserStartDate(Date userStartDate) {
		this.userStartDate = userStartDate;
	}

	public Date getUserEndDate() {
		return this.userEndDate;
	}

	public void setUserEndDate(Date userEndDate) {
		this.userEndDate = userEndDate;
	}

	public Boolean getActiveIndicator() {
		return this.activeIndicator;
	}

	public void setActiveIndicator(Boolean activeIndicator) {
		this.activeIndicator = activeIndicator;
	}

	public String getSystemId() {
		return this.systemId;
	}

	public void setSystemId(String systemId) {
		this.systemId = systemId;
	}

	public String getCreateBy() {
		return this.createBy;
	}

	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}

	public String getCreateWkstnId() {
		return this.createWkstnId;
	}

	public void setCreateWkstnId(String createWkstnId) {
		this.createWkstnId = createWkstnId;
	}

	public Date getCreateDate() {
		return this.createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public String getUpdateBy() {
		return this.updateBy;
	}

	public void setUpdateBy(String updateBy) {
		this.updateBy = updateBy;
	}

	public String getUpdateWkstnId() {
		return this.updateWkstnId;
	}

	public void setUpdateWkstnId(String updateWkstnId) {
		this.updateWkstnId = updateWkstnId;
	}

	public Date getUpdateDate() {
		return this.updateDate;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

	public String getDeleteBy() {
		return this.deleteBy;
	}

	public void setDeleteBy(String deleteBy) {
		this.deleteBy = deleteBy;
	}

	public String getDeleteWkstnId() {
		return this.deleteWkstnId;
	}

	public void setDeleteWkstnId(String deleteWkstnId) {
		this.deleteWkstnId = deleteWkstnId;
	}

	public Date getDeleteDate() {
		return this.deleteDate;
	}

	public void setDeleteDate(Date deleteDate) {
		this.deleteDate = deleteDate;
	}

	public Boolean getDeleteFlag() {
		return this.deleteFlag;
	}

	public void setDeleteFlag(Boolean deleteFlag) {
		this.deleteFlag = deleteFlag;
	}

	public String getFmtCreateDate() {
		return fmtCreateDate;
	}

	public void setFmtCreateDate(String fmtCreateDate) {
		this.fmtCreateDate = fmtCreateDate;
	}

	public String getFmtUpdateDate() {
		return fmtUpdateDate;
	}

	public void setFmtUpdateDate(String fmtUpdateDate) {
		this.fmtUpdateDate = fmtUpdateDate;
	}
	
}
