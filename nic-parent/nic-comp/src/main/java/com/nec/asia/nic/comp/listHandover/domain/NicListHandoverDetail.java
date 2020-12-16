package com.nec.asia.nic.comp.listHandover.domain;
// Generated Jan 20, 2016 11:25:39 AM by Hibernate Tools 4.3.1.Final

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.Table;


public class NicListHandoverDetail implements java.io.Serializable {
    
	private String packageId;
	private String transactionId;
	private long jobId;
	private String typeListName;
	private String createBy;
	private Date createDate;
	private Date updateDate;
	private String updateBy;
	private String description;
	private String typeList;
	
	public NicListHandoverDetail() {
	}

	public NicListHandoverDetail(String packageId) {
		this.packageId = packageId;
	}

	
	public NicListHandoverDetail(String packageId, String transactionId,
			long jobId, String typeListName, String createBy, Date createDate,
			Date updateDate, String updateBy, String description,
			String typeList) {
		super();
		this.packageId = packageId;
		this.transactionId = transactionId;
		this.jobId = jobId;
		this.typeListName = typeListName;
		this.createBy = createBy;
		this.createDate = createDate;
		this.updateDate = updateDate;
		this.updateBy = updateBy;
		this.description = description;
		this.typeList = typeList;
	}

	public NicListHandoverDetail(String packageId, long jobId, String transactionId, String typeListName,
			String createBy, Date createDate, Date updateDate, String updateBy, String description) {

		this.packageId = packageId;
		this.jobId = jobId;
		this.transactionId = transactionId;
		this.typeListName = typeListName;
		this.updateBy = updateBy;
		this.createBy = createBy;
		this.createDate = createDate;
		this.updateDate = updateDate;
		this.description = description;
	}

	public String getPackageId() {
		return packageId;
	}

	public void setPackageId(String packageId) {
		this.packageId = packageId;
	}

	public long getJobId() {
		return jobId;
	}

	public void setJobId(long jobId) {
		this.jobId = jobId;
	}
	
	public String getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}

	public String getTypeListName() {
		return typeListName;
	}

	public void setTypeListName(String typeListName) {
		this.typeListName = typeListName;
	}

	public String getCreateBy() {
		return createBy;
	}

	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public Date getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

	public String getUpdateBy() {
		return updateBy;
	}

	public void setUpdateBy(String updateBy) {
		this.updateBy = updateBy;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getTypeList() {
		return typeList;
	}

	public void setTypeList(String typeList) {
		this.typeList = typeList;
	}

	
}
