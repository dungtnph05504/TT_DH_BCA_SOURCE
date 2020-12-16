package com.nec.asia.nic.comp.trans.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;

import com.nec.asia.nic.comp.job.dto.InfoFamillyDto;
import com.nec.asia.nic.comp.trans.domain.NicRejectionData;
import com.nec.asia.nic.comp.trans.domain.NicSearchResult;
import com.nec.asia.nic.comp.trans.domain.NicTransaction;
import com.nec.asia.nic.comp.trans.domain.NicUploadJob;
/*
 * Modification History:
 * 
 * 16 Oct 2013 (Sailaja) : Added Date of Application to display in the Investigation job list.
 */
import com.nec.asia.nic.utils.DateUtil;

/**
 * @author sailaja_chinapothula
 *
 */
public class NicPaymentJsonDto implements Serializable {  
	
	private String transactionId;
	private String paymentId;
	private Double feeAmount;
	private Integer noOfTimeLost;
	private Boolean reduceRateFlag;
	private Double reduceRateAmount;
	private Double paymentAmount;
	private String collectionOfficerId;
	private Date paymentDatetime;
	private Boolean waiverFlag;
	private String waiverReason;
	private String waiverOfficerId;
	private Double cashReceived;
	private Double balance;
	private String paymentStatus;
	private String receiptId;
	private String createBy;
	private String createWkstnId;
	private Date createDatetime;
	private String updateBy;
	private String updateWkstnId;
	private Date updateDatetime;
	public String getTransactionId() {
		return transactionId;
	}
	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}
	public String getPaymentId() {
		return paymentId;
	}
	public void setPaymentId(String paymentId) {
		this.paymentId = paymentId;
	}
	public Double getFeeAmount() {
		return feeAmount;
	}
	public void setFeeAmount(Double feeAmount) {
		this.feeAmount = feeAmount;
	}
	public Integer getNoOfTimeLost() {
		return noOfTimeLost;
	}
	public void setNoOfTimeLost(Integer noOfTimeLost) {
		this.noOfTimeLost = noOfTimeLost;
	}
	public Boolean getReduceRateFlag() {
		return reduceRateFlag;
	}
	public void setReduceRateFlag(Boolean reduceRateFlag) {
		this.reduceRateFlag = reduceRateFlag;
	}
	public Double getReduceRateAmount() {
		return reduceRateAmount;
	}
	public void setReduceRateAmount(Double reduceRateAmount) {
		this.reduceRateAmount = reduceRateAmount;
	}
	public Double getPaymentAmount() {
		return paymentAmount;
	}
	public void setPaymentAmount(Double paymentAmount) {
		this.paymentAmount = paymentAmount;
	}
	public String getCollectionOfficerId() {
		return collectionOfficerId;
	}
	public void setCollectionOfficerId(String collectionOfficerId) {
		this.collectionOfficerId = collectionOfficerId;
	}
	public Date getPaymentDatetime() {
		return paymentDatetime;
	}
	public void setPaymentDatetime(Date paymentDatetime) {
		this.paymentDatetime = paymentDatetime;
	}
	public Boolean getWaiverFlag() {
		return waiverFlag;
	}
	public void setWaiverFlag(Boolean waiverFlag) {
		this.waiverFlag = waiverFlag;
	}
	public String getWaiverReason() {
		return waiverReason;
	}
	public void setWaiverReason(String waiverReason) {
		this.waiverReason = waiverReason;
	}
	public String getWaiverOfficerId() {
		return waiverOfficerId;
	}
	public void setWaiverOfficerId(String waiverOfficerId) {
		this.waiverOfficerId = waiverOfficerId;
	}
	public Double getCashReceived() {
		return cashReceived;
	}
	public void setCashReceived(Double cashReceived) {
		this.cashReceived = cashReceived;
	}
	public Double getBalance() {
		return balance;
	}
	public void setBalance(Double balance) {
		this.balance = balance;
	}
	public String getPaymentStatus() {
		return paymentStatus;
	}
	public void setPaymentStatus(String paymentStatus) {
		this.paymentStatus = paymentStatus;
	}
	public String getReceiptId() {
		return receiptId;
	}
	public void setReceiptId(String receiptId) {
		this.receiptId = receiptId;
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
	public Date getCreateDatetime() {
		return createDatetime;
	}
	public void setCreateDatetime(Date createDatetime) {
		this.createDatetime = createDatetime;
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
	public Date getUpdateDatetime() {
		return updateDatetime;
	}
	public void setUpdateDatetime(Date updateDatetime) {
		this.updateDatetime = updateDatetime;
	}
	
	
}
