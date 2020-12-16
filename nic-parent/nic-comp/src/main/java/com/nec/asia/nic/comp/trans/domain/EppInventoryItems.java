package com.nec.asia.nic.comp.trans.domain;
// Generated Jan 20, 2016 11:25:39 AM by Hibernate Tools 4.3.1.Final

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * RptStatisticsTransmitData generated by hbm2java
 */
public class EppInventoryItems implements java.io.Serializable {
    /**
     * 
     */

    private static final long serialVersionUID = 4030959704968010599L; 
    
    private Long id;
	private Date deleteTs;
	private String deletedBy;
	private Date updateTs;
	private String updatedBy;
	private Date createTs;
	private String createdBy;
	private Integer inventoryId;
	private String handoverNo;
	private String recieptDate;
	private String recieptName;
	private String status;
	private String batchNo;
	private Integer categoryId;
	private String handoverName;
	
	public EppInventoryItems() {
	}
	
	

	public EppInventoryItems(Long id, Date createTs, String createdBy,
			Integer inventoryId, String handoverNo, String recieptDate,
			String recieptName, String status, String batchNo,
			Integer categoryId, String handoverName) {
		super();
		this.id = id;
		this.createTs = createTs;
		this.createdBy = createdBy;
		this.inventoryId = inventoryId;
		this.handoverNo = handoverNo;
		this.recieptDate = recieptDate;
		this.recieptName = recieptName;
		this.status = status;
		this.batchNo = batchNo;
		this.categoryId = categoryId;
		this.handoverName = handoverName;
	}



	public String getHandoverName() {
		return handoverName;
	}

	public void setHandoverName(String handoverName) {
		this.handoverName = handoverName;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDeletedBy() {
		return deletedBy;
	}

	public void setDeletedBy(String deletedBy) {
		this.deletedBy = deletedBy;
	}

	public String getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}


	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}


	public String getHandoverNo() {
		return handoverNo;
	}

	public void setHandoverNo(String handoverNo) {
		this.handoverNo = handoverNo;
	}

	public String getRecieptDate() {
		return recieptDate;
	}

	public void setRecieptDate(String recieptDate) {
		this.recieptDate = recieptDate;
	}

	public String getRecieptName() {
		return recieptName;
	}

	public void setRecieptName(String recieptName) {
		this.recieptName = recieptName;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getBatchNo() {
		return batchNo;
	}

	public void setBatchNo(String batchNo) {
		this.batchNo = batchNo;
	}

	public Date getDeleteTs() {
		return deleteTs;
	}

	public void setDeleteTs(Date deleteTs) {
		this.deleteTs = deleteTs;
	}

	public Date getUpdateTs() {
		return updateTs;
	}

	public void setUpdateTs(Date updateTs) {
		this.updateTs = updateTs;
	}

	public Date getCreateTs() {
		return createTs;
	}

	public void setCreateTs(Date createTs) {
		this.createTs = createTs;
	}

	public Integer getInventoryId() {
		return inventoryId;
	}

	public void setInventoryId(Integer inventoryId) {
		this.inventoryId = inventoryId;
	}

	public Integer getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(Integer categoryId) {
		this.categoryId = categoryId;
	}

	

	
}