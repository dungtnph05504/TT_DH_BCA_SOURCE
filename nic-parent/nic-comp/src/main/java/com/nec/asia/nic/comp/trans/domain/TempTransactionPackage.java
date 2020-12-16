package com.nec.asia.nic.comp.trans.domain;

import java.io.Serializable;

public class TempTransactionPackage implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Long id;
	private String packageId;
	private String transactionId;
	private Integer typeList;
	private String offerStage;
	private String offerNote;
	private String approveStage;
	private String approveNote;
	
	public TempTransactionPackage() {
		super();
	}

	public TempTransactionPackage(Long id, String packageId,
			String transactionId, Integer typeList, String offerStage,
			String offerNote, String approveStage, String approveNote) {
		super();
		this.id = id;
		this.packageId = packageId;
		this.transactionId = transactionId;
		this.typeList = typeList;
		this.offerStage = offerStage;
		this.offerNote = offerNote;
		this.approveStage = approveStage;
		this.approveNote = approveNote;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getPackageId() {
		return packageId;
	}

	public void setPackageId(String packageId) {
		this.packageId = packageId;
	}

	public String getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}

	public Integer getTypeList() {
		return typeList;
	}

	public void setTypeList(Integer typeList) {
		this.typeList = typeList;
	}

	public String getOfferStage() {
		return offerStage;
	}

	public void setOfferStage(String offerStage) {
		this.offerStage = offerStage;
	}

	public String getOfferNote() {
		return offerNote;
	}

	public void setOfferNote(String offerNote) {
		this.offerNote = offerNote;
	}

	public String getApproveStage() {
		return approveStage;
	}

	public void setApproveStage(String approveStage) {
		this.approveStage = approveStage;
	}

	public String getApproveNote() {
		return approveNote;
	}

	public void setApproveNote(String approveNote) {
		this.approveNote = approveNote;
	}
	
	
	
	
}
