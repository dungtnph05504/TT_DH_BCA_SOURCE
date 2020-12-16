package com.nec.asia.nic.comp.trans.domain;

import java.util.Date;

/* 
 * Modification History:
 * 
 * 07 Jan 2014 (chris): init class
 * 10 Feb 2014 (chris): change to CARD_ISSUED, add OUT_PACKAGE_ID, OUT_PACKAGE_SEQUENCE.
 * 06 Jun 2014 (chris): add constant CARD_RETURN.
 */
@Deprecated
public class CardStockTrackingLog implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	
	//STOCK TYPE
	public final static String STOCK_TYPE_IN 			= "IN";	
	public final static String STOCK_TYPE_OUT	 		= "OUT";
	
	//TRANSACTION STAGE
	public final static String TRANSACTION_STAGE_CARD_RECEPTION	= "CARD_RECEPTION";
	public final static String TRANSACTION_STAGE_CARD_ISSUED 	= "CARD_ISSUED";
	public final static String TRANSACTION_STAGE_CARD_FOUND 	= "CARD_FOUND";
	public final static String TRANSACTION_STAGE_CARD_TRANSFER 	= "CARD_TRANSFER";
	public final static String TRANSACTION_STAGE_CARD_RETURN	= "CARD_RETURN";

	private long logId;
	private String ccn;
	private String nin;
	private String siteCode;
	private String toSiteCode;
	private String stockType;
	private String transactionStage;
	private String packageId;
	private String remarks;
	private String createBy;
	private String createWkstnId;
	private Date   createDate;
	private String outPackageId;
	private String outPackageSequence;

	public CardStockTrackingLog() {
	}

	public long getLogId() {
		return logId;
	}

	public void setLogId(long logId) {
		this.logId = logId;
	}

	public String getCcn() {
		return ccn;
	}

	public void setCcn(String ccn) {
		this.ccn = ccn;
	}

	public String getNin() {
		return nin;
	}

	public void setNin(String nin) {
		this.nin = nin;
	}

	public String getSiteCode() {
		return siteCode;
	}

	public void setSiteCode(String siteCode) {
		this.siteCode = siteCode;
	}

	public String getToSiteCode() {
		return toSiteCode;
	}

	public void setToSiteCode(String toSiteCode) {
		this.toSiteCode = toSiteCode;
	}

	public String getStockType() {
		return stockType;
	}

	public void setStockType(String stockType) {
		this.stockType = stockType;
	}

	public String getTransactionStage() {
		return transactionStage;
	}

	public void setTransactionStage(String transactionStage) {
		this.transactionStage = transactionStage;
	}

	public String getPackageId() {
		return packageId;
	}

	public void setPackageId(String packageId) {
		this.packageId = packageId;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
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

	public String getOutPackageId() {
		return outPackageId;
	}

	public void setOutPackageId(String outPackageId) {
		this.outPackageId = outPackageId;
	}

	public String getOutPackageSequence() {
		return outPackageSequence;
	}

	public void setOutPackageSequence(String outPackageSequence) {
		this.outPackageSequence = outPackageSequence;
	}

}
