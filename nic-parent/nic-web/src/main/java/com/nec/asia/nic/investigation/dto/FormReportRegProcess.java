package com.nec.asia.nic.investigation.dto;

public class FormReportRegProcess {
	private String fromDate;
	private String toDate;
	private String regSiteCode;
	private String printSiteCode;
	
	public String getPrintSiteCode() {
		return printSiteCode;
	}
	public void setPrintSiteCode(String printSiteCode) {
		this.printSiteCode = printSiteCode;
	}
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
	public String getRegSiteCode() {
		return regSiteCode;
	}
	public void setRegSiteCode(String regSiteCode) {
		this.regSiteCode = regSiteCode;
	}
	@Override
	public String toString() {
		return "FormReportRegProcess [fromDate=" + fromDate + ", toDate="
				+ toDate + ", regSiteCode=" + regSiteCode + ", printSiteCode="
				+ printSiteCode + "]";
	}
}
