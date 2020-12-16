package com.nec.asia.nic.comp.trans.domain;

import java.io.Serializable;

import com.nec.asia.nic.comp.listHandover.domain.NicListHandover;

public class EppListHandoverDetail implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Long id;
	private String packageId;
	private NicListHandover packageHandover;
	private String transactionId;
	private String proposalStage;
	private String proposalNote;
	private String approveStage;
	private String approveNote;
	private String typeList;
	private String exchangeContent;
	public EppListHandoverDetail(){
		
	}
	
	public EppListHandoverDetail(Long id, String packageId,
			NicListHandover packageHandover, String transactionId,
			String proposalStage, String proposalNote, String approveStage,
			String approveNote, String typeList) {
		super();
		this.id = id;
		this.packageId = packageId;
		this.packageHandover = packageHandover;
		this.transactionId = transactionId;
		this.proposalStage = proposalStage;
		this.proposalNote = proposalNote;
		this.approveStage = approveStage;
		this.approveNote = approveNote;
		this.typeList = typeList;
	}

	public EppListHandoverDetail(Long id, String packageId,
			String transactionId, String proposalStage, String proposalNote,
			String approveStage, String approveNote,NicListHandover packageHandover) {
		super();
		this.id = id;
		this.packageId = packageId;
		this.transactionId = transactionId;
		this.proposalStage = proposalStage;
		this.proposalNote = proposalNote;
		this.approveStage = approveStage;
		this.approveNote = approveNote;
		this.packageHandover= packageHandover;
	}

	public EppListHandoverDetail(Long id, String packageId,
			NicListHandover packageHandover, String transactionId,
			String proposalStage, String proposalNote, String approveStage,
			String approveNote, String typeList, String exchangeContent) {
		super();
		this.id = id;
		this.packageId = packageId;
		this.packageHandover = packageHandover;
		this.transactionId = transactionId;
		this.proposalStage = proposalStage;
		this.proposalNote = proposalNote;
		this.approveStage = approveStage;
		this.approveNote = approveNote;
		this.typeList = typeList;
		this.exchangeContent = exchangeContent;
	}

	public String getExchangeContent() {
		return exchangeContent;
	}

	public void setExchangeContent(String exchangeContent) {
		this.exchangeContent = exchangeContent;
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

	public String getProposalStage() {
		return proposalStage;
	}

	public void setProposalStage(String proposalStage) {
		this.proposalStage = proposalStage;
	}

	public String getProposalNote() {
		return proposalNote;
	}

	public void setProposalNote(String proposalNote) {
		this.proposalNote = proposalNote;
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

	public NicListHandover getPackageHandover() {
		return packageHandover;
	}

	public void setPackageHandover(NicListHandover packageHandover) {
		this.packageHandover = packageHandover;
	}

	public String getTypeList() {
		return typeList;
	}

	public void setTypeList(String typeList) {
		this.typeList = typeList;
	}
}
