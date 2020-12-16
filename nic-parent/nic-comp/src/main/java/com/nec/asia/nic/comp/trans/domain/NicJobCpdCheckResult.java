package com.nec.asia.nic.comp.trans.domain;

// Generated May 16, 2013 7:03:37 PM by Hibernate Tools 3.4.0.CR1

import java.sql.Blob;
import java.util.Date;

/**
 * NicJobCpdCheckResult generated by hbm2java
 */
@Deprecated
public class NicJobCpdCheckResult implements java.io.Serializable {

	private long uploadJobId;
	private NicUploadJob nicUploadJob;
	private String nin;
	private String cpdXml;
	private String cpdCheckResult;
	private Date cpdCheckTime;
	private String decision;
	private String decisionOfficerId;
	private Date decisionTime;
	private String createBy;
	private String createWkstnId;
	private Date createDate;
	private String updateBy;
	private String updateWkstnId;
	private Date updateDate;

	public NicJobCpdCheckResult() {
	}

	public NicJobCpdCheckResult(NicUploadJob nicUploadJob) {
		this.nicUploadJob = nicUploadJob;
	}

	public NicJobCpdCheckResult(NicUploadJob nicUploadJob, String nin,
			String cpdXml, String cpdCheckResult, Date cpdCheckTime,
			String decision, String decisionOfficerId, Date decisionTime,
			String createBy, String createWkstnId, Date createDate,
			String updateBy, String updateWkstnId, Date updateDate) {
		this.nicUploadJob = nicUploadJob;
		this.nin = nin;
		this.cpdXml = cpdXml;
		this.cpdCheckResult = cpdCheckResult;
		this.cpdCheckTime = cpdCheckTime;
		this.decision = decision;
		this.decisionOfficerId = decisionOfficerId;
		this.decisionTime = decisionTime;
		this.createBy = createBy;
		this.createWkstnId = createWkstnId;
		this.createDate = createDate;
		this.updateBy = updateBy;
		this.updateWkstnId = updateWkstnId;
		this.updateDate = updateDate;
	}

	public long getUploadJobId() {
		return this.uploadJobId;
	}

	public void setUploadJobId(long uploadJobId) {
		this.uploadJobId = uploadJobId;
	}

	public NicUploadJob getNicUploadJob() {
		return this.nicUploadJob;
	}

	public void setNicUploadJob(NicUploadJob nicUploadJob) {
		this.nicUploadJob = nicUploadJob;
	}

	public String getNin() {
		return this.nin;
	}

	public void setNin(String nin) {
		this.nin = nin;
	}

	public String getCpdXml() {
		return this.cpdXml;
	}

	public void setCpdXml(String cpdXml) {
		this.cpdXml = cpdXml;
	}

	public String getCpdCheckResult() {
		return this.cpdCheckResult;
	}

	public void setCpdCheckResult(String cpdCheckResult) {
		this.cpdCheckResult = cpdCheckResult;
	}

	public Date getCpdCheckTime() {
		return this.cpdCheckTime;
	}

	public void setCpdCheckTime(Date cpdCheckTime) {
		this.cpdCheckTime = cpdCheckTime;
	}

	public String getDecision() {
		return this.decision;
	}

	public void setDecision(String decision) {
		this.decision = decision;
	}

	public String getDecisionOfficerId() {
		return this.decisionOfficerId;
	}

	public void setDecisionOfficerId(String decisionOfficerId) {
		this.decisionOfficerId = decisionOfficerId;
	}

	public Date getDecisionTime() {
		return this.decisionTime;
	}

	public void setDecisionTime(Date decisionTime) {
		this.decisionTime = decisionTime;
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

}
