package com.nec.asia.nic.statistic;

public class EppStatisticRptForm {

	private String fromDate;
	private String toDate;
	private int noOfDays;
	private boolean collectedType;
	private boolean pendingType;
	
	public EppStatisticRptForm(){}
	
	public String getFromDate() {
		return fromDate;
	}
	public void setFromDate(String fromDate) {
		this.fromDate = fromDate;
	}
	public String getToDate() {
		return toDate;
	}
	public void setToDate(String toDate) {
		this.toDate = toDate;
	}
	public int getNoOfDays() {
		return noOfDays;
	}
	public void setNoOfDays(int noOfDays) {
		this.noOfDays = noOfDays;
	}
	public boolean getCollectedType() {
		return collectedType;
	}
	public void setCollectedType(boolean collectedType) {
		this.collectedType = collectedType;
	}
	public boolean getPendingType() {
		return pendingType;
	}
	public void setPendingType(boolean pendingType) {
		this.pendingType = pendingType;
	}
	
}
