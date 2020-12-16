package com.nec.asia.nic.comp.trans.dto;

public class AssignmentFilter {

	private String transactionId;
	private String invCode;
	private String batchNo;
	private String status;
	private String invItems;
	private String chipNo;
	private String handoverNo;

	public AssignmentFilter() {
		super();
	}

	public AssignmentFilter(String transactionId, String invCode,
			String batchNo, String status, String invItems, String chipNo, String handoverNo) {
		super();
		this.transactionId = transactionId;
		this.invCode = invCode;
		this.batchNo = batchNo;
		this.status = status;
		this.invItems = invItems;
		this.chipNo = chipNo;
		this.handoverNo = handoverNo;
	}

	
	
	public AssignmentFilter(String transactionId) {
		super();
		this.transactionId = transactionId;
	}
	
	

	public String getHandoverNo() {
		return handoverNo;
	}

	public void setHandoverNo(String handoverNo) {
		this.handoverNo = handoverNo;
	}

	public String getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}

	public String getInvCode() {
		return invCode;
	}

	public void setInvCode(String invCode) {
		this.invCode = invCode;
	}

	public String getBatchNo() {
		return batchNo;
	}

	public void setBatchNo(String batchNo) {
		this.batchNo = batchNo;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getInvItems() {
		return invItems;
	}

	public void setInvItems(String invItems) {
		this.invItems = invItems;
	}

	public String getChipNo() {
		return chipNo;
	}

	public void setChipNo(String chipNo) {
		this.chipNo = chipNo;
	}
	
	
}
