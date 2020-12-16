package com.nec.asia.nic.common.dto;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Calendar;


public class RicTransactionLogDTO implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int parentLogId;
	private String transactionId;
	private String transactionStage;
	private String transactionStatus;
	private String counterId;
	private String ricSitecode;
	private String updateTime;
	private String remarks;
	private String userId;
	private String username;
	private String reason;
	private int logId;
	private String transactionDate;
	private String firstname;
	private String surname;
	private String surnameBirth;
	private String transactionType;
	private String transactionSubType;
	
	private String nin;
	
	public RicTransactionLogDTO(){}
	
	/*public RicTransactionLogDTO(RicTransactionLog orm){ 
		try {
			this.transactionId=(orm.getTransaction()!=null && orm.getTransaction().getTransactionId()!=null)?orm.getTransaction().getTransactionId():orm.getTransactionId();		
			this.transactionStage = orm.getTransactionStage(); 
			this.counterId=(orm.getCounterId()!=null)?orm.getCounterId():""; 
			this.ricSitecode=orm.getRicSitecode(); 
			this.updateTime=getCalendarWithTimeValueAsString(orm.getStartTime());
			this.remarks=(orm.getRemarks()!=null)?orm.getRemarks():""; 
			this.userId=orm.getUserId();
			this.reason=(orm.getReason()!=null)?orm.getReason():"";  
			this.logId=(int) orm.getLogId();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}*/
	
	
	public int getParentLogId() {
		return parentLogId;
	}

	public void setParentLogId(int parentLogId) {
		this.parentLogId = parentLogId;
	}

	public String getTransactionStage() {
		return transactionStage;
	}
	public void setTransactionStage(String transactionStage) {
		this.transactionStage = transactionStage;
	}
	public String getCounterId() {
		return counterId;
	}
	public void setCounterId(String counterId) {
		this.counterId = counterId;
	}
 
	 
	public String getRicSitecode() {
		return ricSitecode;
	}

	public void setRicSitecode(String ricSitecode) {
		this.ricSitecode = ricSitecode;
	}

	public String getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
	}

	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public String getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}
	
	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}
	 
	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	} 

	public String getCalendarWithTimeValueAsString(Calendar date) {
		if (date == null) {
			return "";
		}
		String output = null;
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yyyy HH:mm");
			Calendar cal = (Calendar) date;
			sdf.setCalendar(cal);
			output = sdf.format(cal.getTime());
			System.out
					.println("The date output is ::::::::::::::::::############"
							+ output);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return output;
	}

	public int getLogId() {
		return logId;
	}

	public void setLogId(int logId) {
		this.logId = logId;
	}

	public String getTransactionDate() {
		return transactionDate;
	}

	public void setTransactionDate(String transactionDate) {
		this.transactionDate = transactionDate;
	}

	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public String getSurnameBirth() {
		return surnameBirth;
	}

	public void setSurnameBirth(String surnameBirth) {
		this.surnameBirth = surnameBirth;
	}

	public String getTransactionType() {
		return transactionType;
	}

	public void setTransactionType(String transactionType) {
		this.transactionType = transactionType;
	}

	public String getTransactionSubType() {
		return transactionSubType;
	}

	public void setTransactionSubType(String transactionSubType) {
		this.transactionSubType = transactionSubType;
	}

	public String getNin() {
		return nin;
	}

	public void setNin(String nin) {
		this.nin = nin;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getTransactionStatus() {
		return transactionStatus;
	}

	public void setTransactionStatus(String transactionStatus) {
		this.transactionStatus = transactionStatus;
	}  
}