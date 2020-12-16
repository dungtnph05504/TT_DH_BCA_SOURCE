package com.nec.asia.nic.comp.trans.domain;
// Generated Jan 20, 2016 11:25:39 AM by Hibernate Tools 4.3.1.Final

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * NicTransaction generated by hbm2java
 */
public class DetailRecieptFee implements java.io.Serializable {
    /**
     * 
     */
    private static final long serialVersionUID = 4030959704968010599L; 
    
    private long id;
	private String recieptNo;
	private String codeBill;
	private String numberBill;
	private Double price;
	private String priceFlag;
	private Date createDate;
	private String createBy;
	private Date modifyDate;
	private String modifyBy;
	private String description;
	private String reason;
	private String cashierName;
	private Date dateOfReceipt;
	private String createByName;
	private String customerName;
	
	public DetailRecieptFee() {
	}

	public DetailRecieptFee(long id, String recieptNo, String codeBill,
			String numberBill, Double price, String priceFlag,
			Date createDate, String createBy, Date modifyDate, String modifyBy,
			String description, String reason, String cashierName,
			Date dateOfReceipt, String createByName, String customerName) {
		super();
		this.id = id;
		this.recieptNo = recieptNo;
		this.codeBill = codeBill;
		this.numberBill = numberBill;
		this.price = price;
		this.priceFlag = priceFlag;
		this.createDate = createDate;
		this.createBy = createBy;
		this.modifyDate = modifyDate;
		this.modifyBy = modifyBy;
		this.description = description;
		this.reason = reason;
		this.cashierName = cashierName;
		this.dateOfReceipt = dateOfReceipt;
		this.createByName = createByName;
		this.customerName = customerName;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getRecieptNo() {
		return recieptNo;
	}

	public void setRecieptNo(String recieptNo) {
		this.recieptNo = recieptNo;
	}

	public String getCodeBill() {
		return codeBill;
	}

	public void setCodeBill(String codeBill) {
		this.codeBill = codeBill;
	}

	public String getNumberBill() {
		return numberBill;
	}

	public void setNumberBill(String numberBill) {
		this.numberBill = numberBill;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public String getPriceFlag() {
		return priceFlag;
	}

	public void setPriceFlag(String priceFlag) {
		this.priceFlag = priceFlag;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public String getCreateBy() {
		return createBy;
	}

	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}

	public Date getModifyDate() {
		return modifyDate;
	}

	public void setModifyDate(Date modifyDate) {
		this.modifyDate = modifyDate;
	}

	public String getModifyBy() {
		return modifyBy;
	}

	public void setModifyBy(String modifyBy) {
		this.modifyBy = modifyBy;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}
	public String getCashierName() {
		return cashierName;
	}

	public void setCashierName(String cashierName) {
		this.cashierName = cashierName;
	}

	public Date getDateOfReceipt() {
		return dateOfReceipt;
	}

	public void setDateOfReceipt(Date dateOfReceipt) {
		this.dateOfReceipt = dateOfReceipt;
	}

	public String getCreateByName() {
		return createByName;
	}

	public void setCreateByName(String createByName) {
		this.createByName = createByName;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	
	
}
