package com.nec.asia.nic.reprint.dto;

import java.util.Date;

public class TransactionDocumentDto {

	private long transactionDocId;
	private String transactionId;
	
	private String docType;

	private String serialNo;
	private byte[] docData;
	private String status;
	private String purpose;
	private String createBy;
	private String createWkstnId;
	private Date createDate;
	private String updateBy;
	private String updateWkstnId;
	private Date updateDate;
	
	private String nin;
	private String decDocData;
	
	public long getTransactionDocId() {
		return transactionDocId;
	}
	public void setTransactionDocId(long transactionDocId) {
		this.transactionDocId = transactionDocId;
	}
	public String getTransactionId() {
		return transactionId;
	}
	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}
	public String getDocType() {
		return docType;
	}
	public void setDocType(String docType) {
		this.docType = docType;
	}
	public String getSerialNo() {
		return serialNo;
	}
	public void setSerialNo(String serialNo) {
		this.serialNo = serialNo;
	}
	public byte[] getDocData() {
		return docData;
	}
	public void setDocData(byte[] docData) {
		this.docData = docData;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getPurpose() {
		return purpose;
	}
	public void setPurpose(String purpose) {
		this.purpose = purpose;
	}
	public String getCreateBy() {
		return createBy;
	}
	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}
	public String getCreateWkstnId() {
		return createWkstnId;
	}
	public void setCreateWkstnId(String createWkstnId) {
		this.createWkstnId = createWkstnId;
	}
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	public String getUpdateBy() {
		return updateBy;
	}
	public void setUpdateBy(String updateBy) {
		this.updateBy = updateBy;
	}
	public String getUpdateWkstnId() {
		return updateWkstnId;
	}
	public void setUpdateWkstnId(String updateWkstnId) {
		this.updateWkstnId = updateWkstnId;
	}
	public Date getUpdateDate() {
		return updateDate;
	}
	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}
	public String getNin() {
		return nin;
	}
	public void setNin(String nin) {
		this.nin = nin;
	}
	public String getDecDocData() {
		return decDocData;
	}
	public void setDecDocData(String decDocData) {
		this.decDocData = decDocData;
	}

}
