package com.nec.asia.nic.comp.perso.domain;

import java.util.Date;

/**
 * @author khang
 */
/*
 * Modification History:
 */
public class PersoDispatchInfo implements java.io.Serializable {

	private long dispatchInfoId;
	private String batchNumber;
	private String documentType;
	private long priority;
	private long batchSize;
	private String collectionSite;
	private Date collectionDate;
	private String applnRefNo;
	private String passportNo;
	private String sourceLocation;

	public PersoDispatchInfo() {
	}

	public PersoDispatchInfo(long dispatchInfoId, String batchNumber, long priority, long batchSize,
			String collectionSite) {
		this.dispatchInfoId = dispatchInfoId;
		this.batchNumber = batchNumber;
		this.priority = priority;
		this.batchSize = batchSize;
		this.collectionSite = collectionSite;
	}

	public PersoDispatchInfo(long dispatchInfoId, String batchNumber, String documentType, long priority,
			long batchSize, String collectionSite, Date collectionDate, String applnRefNo, String passportNo,
			String sourceLocation) {
		this.dispatchInfoId = dispatchInfoId;
		this.batchNumber = batchNumber;
		this.documentType = documentType;
		this.priority = priority;
		this.batchSize = batchSize;
		this.collectionSite = collectionSite;
		this.collectionDate = collectionDate;
		this.applnRefNo = applnRefNo;
		this.passportNo = passportNo;
		this.sourceLocation = sourceLocation;
	}

	public long getDispatchInfoId() {
		return this.dispatchInfoId;
	}

	public void setDispatchInfoId(long dispatchInfoId) {
		this.dispatchInfoId = dispatchInfoId;
	}

	public String getBatchNumber() {
		return this.batchNumber;
	}

	public void setBatchNumber(String batchNumber) {
		this.batchNumber = batchNumber;
	}

	public String getDocumentType() {
		return this.documentType;
	}

	public void setDocumentType(String documentType) {
		this.documentType = documentType;
	}

	public long getPriority() {
		return this.priority;
	}

	public void setPriority(long priority) {
		this.priority = priority;
	}

	public long getBatchSize() {
		return this.batchSize;
	}

	public void setBatchSize(long batchSize) {
		this.batchSize = batchSize;
	}

	public String getCollectionSite() {
		return this.collectionSite;
	}

	public void setCollectionSite(String collectionSite) {
		this.collectionSite = collectionSite;
	}

	public Date getCollectionDate() {
		return this.collectionDate;
	}

	public void setCollectionDate(Date collectionDate) {
		this.collectionDate = collectionDate;
	}

	public String getApplnRefNo() {
		return this.applnRefNo;
	}

	public void setApplnRefNo(String applnRefNo) {
		this.applnRefNo = applnRefNo;
	}

	public String getPassportNo() {
		return this.passportNo;
	}

	public void setPassportNo(String passportNo) {
		this.passportNo = passportNo;
	}

	public String getSourceLocation() {
		return this.sourceLocation;
	}

	public void setSourceLocation(String sourceLocation) {
		this.sourceLocation = sourceLocation;
	}

}
