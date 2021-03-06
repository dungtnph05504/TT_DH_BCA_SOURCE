package com.nec.asia.nic.comp.listHandover.domain;

// Generated Jan 20, 2016 11:25:39 AM by Hibernate Tools 4.3.1.Final

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import com.nec.asia.nic.comp.trans.domain.NicTransaction;

public class NicListHandover implements java.io.Serializable {

	private static final long serialVersionUID = 15103120310231L;
	public static final String TYPE_LIST_A = "A";
	public static final String TYPE_LIST_B = "B";
	public static final String TYPE_LIST_C = "C";

	private NicListHandoverId id;

	private String packageName;
	private String siteCode;
	private int handoverStatus;
	private Boolean handoverStage;
	private int countTransaction;
	private String processUsers;
	private String proposalUser;
	private Date proposalDate;

	private String approveUser;
	private Date approveDate;
	// private String archiveCode;
	private String createBy;
	private Date createDate;
	private Date updateDate;
	private String updateBy;

	private String proposalName;
	private String approveName;
	private String approvePosition;
	private String creatorName;
	private String updateByName;
	private Date dateOfDelivery;
	private String placeOfDelivery;

	/*
	 * Thông tin hủy danh sách B
	 */
	private String cancelReason;
	private String cancelBy;
	private String cancelByName;
	private Date cancelDatetime;

	public NicListHandover(NicListHandoverId id, String packageName,
			String siteCode, int handoverStatus, Boolean handoverStage,
			int countTransaction, String processUsers, String proposalUser,
			Date proposalDate, String approveUser, Date approveDate,
			String createBy, Date createDate, Date updateDate, String updateBy,
			String proposalName, String approveName, String approvePosition,
			String creatorName, String updateByName, Date dateOfDelivery,
			String placeOfDelivery, String cancelReason, String cancelBy,
			String cancelByName, Date cancelDatetime) {
		super();
		this.id = id;
		this.packageName = packageName;
		this.siteCode = siteCode;
		this.handoverStatus = handoverStatus;
		this.handoverStage = handoverStage;
		this.countTransaction = countTransaction;
		this.processUsers = processUsers;
		this.proposalUser = proposalUser;
		this.proposalDate = proposalDate;
		this.approveUser = approveUser;
		this.approveDate = approveDate;
		this.createBy = createBy;
		this.createDate = createDate;
		this.updateDate = updateDate;
		this.updateBy = updateBy;
		this.proposalName = proposalName;
		this.approveName = approveName;
		this.approvePosition = approvePosition;
		this.creatorName = creatorName;
		this.updateByName = updateByName;
		this.dateOfDelivery = dateOfDelivery;
		this.placeOfDelivery = placeOfDelivery;
		this.cancelReason = cancelReason;
		this.cancelBy = cancelBy;
		this.cancelByName = cancelByName;
		this.cancelDatetime = cancelDatetime;
	}


	 private Set<NicTransaction> transactions = new HashSet<NicTransaction>(0);
	 
	 private int status;
	 
	 

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public Set<NicTransaction> getTransactions() {
		return transactions;
	}

	public void setTransactions(Set<NicTransaction> transactions) {
		this.transactions = transactions;
	}



	public String getCancelReason() {
		return cancelReason;
	}

	public void setCancelReason(String cancelReason) {
		this.cancelReason = cancelReason;
	}

	public String getCancelBy() {
		return cancelBy;
	}

	public void setCancelBy(String cancelBy) {
		this.cancelBy = cancelBy;
	}

	public String getCancelByName() {
		return cancelByName;
	}

	public void setCancelByName(String cancelByName) {
		this.cancelByName = cancelByName;
	}

	public Date getCancelDatetime() {
		return cancelDatetime;
	}

	public void setCancelDatetime(Date cancelDatetime) {
		this.cancelDatetime = cancelDatetime;
	}

	public NicListHandover() {

	}

	public NicListHandover(NicListHandoverId id, String packageName,
			String siteCode, int handoverStatus, Boolean handoverStage,
			int countTransaction, String processUsers, String proposalUser,
			Date proposalDate, String approveUser, Date approveDate,
			String createBy, Date createDate, Date updateDate, String updateBy,
			String proposalName, String approveName, String approvePosition,
			String creatorName, String updateByName, Date dateOfDelivery,
			String placeOfDelivery) {
		super();
		this.id = id;
		this.packageName = packageName;
		this.siteCode = siteCode;
		this.handoverStatus = handoverStatus;
		this.handoverStage = handoverStage;
		this.countTransaction = countTransaction;
		this.processUsers = processUsers;
		this.proposalUser = proposalUser;
		this.proposalDate = proposalDate;
		this.approveUser = approveUser;
		this.approveDate = approveDate;
		this.createBy = createBy;
		this.createDate = createDate;
		this.updateDate = updateDate;
		this.updateBy = updateBy;
		this.proposalName = proposalName;
		this.approveName = approveName;
		this.approvePosition = approvePosition;
		this.creatorName = creatorName;
		this.updateByName = updateByName;
		this.dateOfDelivery = dateOfDelivery;
		this.placeOfDelivery = placeOfDelivery;
	}

	public String getPackageName() {
		return packageName;
	}

	public void setPackageName(String packageName) {
		this.packageName = packageName;
	}

	public String getSiteCode() {
		return siteCode;
	}

	public void setSiteCode(String siteCode) {
		this.siteCode = siteCode;
	}

	public int getHandoverStatus() {
		return handoverStatus;
	}

	public void setHandoverStatus(int handoverStatus) {
		this.handoverStatus = handoverStatus;
	}

	public Boolean getHandoverStage() {
		return handoverStage;
	}

	public void setHandoverStage(Boolean handoverStage) {
		this.handoverStage = handoverStage;
	}

	public int getCountTransaction() {
		return countTransaction;
	}

	public void setCountTransaction(int countTransaction) {
		this.countTransaction = countTransaction;
	}

	public String getProcessUsers() {
		return processUsers;
	}

	public void setProcessUsers(String processUsers) {
		this.processUsers = processUsers;
	}

	public String getProposalUser() {
		return proposalUser;
	}

	public void setProposalUser(String proposalUser) {
		this.proposalUser = proposalUser;
	}

	public Date getProposalDate() {
		return proposalDate;
	}

	public void setProposalDate(Date proposalDate) {
		this.proposalDate = proposalDate;
	}

	public String getApproveUser() {
		return approveUser;
	}

	public void setApproveUser(String approveUser) {
		this.approveUser = approveUser;
	}

	public Date getApproveDate() {
		return approveDate;
	}

	public void setApproveDate(Date approveDate) {
		this.approveDate = approveDate;
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

	public String getProposalName() {
		return proposalName;
	}

	public void setProposalName(String proposalName) {
		this.proposalName = proposalName;
	}

	public String getApproveName() {
		return approveName;
	}

	public void setApproveName(String approveName) {
		this.approveName = approveName;
	}

	public String getApprovePosition() {
		return approvePosition;
	}

	public void setApprovePosition(String approvePosition) {
		this.approvePosition = approvePosition;
	}

	public String getCreatorName() {
		return creatorName;
	}

	public void setCreatorName(String creatorName) {
		this.creatorName = creatorName;
	}

	public String getUpdateByName() {
		return updateByName;
	}

	public void setUpdateByName(String updateByName) {
		this.updateByName = updateByName;
	}

	public Date getDateOfDelivery() {
		return dateOfDelivery;
	}

	public void setDateOfDelivery(Date dateOfDelivery) {
		this.dateOfDelivery = dateOfDelivery;
	}

	public String getPlaceOfDelivery() {
		return placeOfDelivery;
	}

	public void setPlaceOfDelivery(String placeOfDelivery) {
		this.placeOfDelivery = placeOfDelivery;
	}

	public NicListHandoverId getId() {
		return id;
	}

	public void setId(NicListHandoverId id) {
		this.id = id;
	}

}
