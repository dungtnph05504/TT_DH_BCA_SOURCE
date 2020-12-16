package com.nec.asia.nic.statistic;

public class EppStatisticTransInfo {
	private String statDate;
	private String pendingAfis;
	private String pendingInvestigation;
	private String pendingVerified;
	private String pendingPerso;
	private String pendingQc;
	private String pendingDispatch;
	private String pendingReceive;
	private String pendingIssue;
	private String numPassportCollected;
	
	public EppStatisticTransInfo(){}
	public EppStatisticTransInfo(String statDate, String pendingAfis, String pendingInvestigation, String pendingVerified,
			String pendingPerso, String pendingQc, String pendingDispatch, String pendingReceive, String pendingIssue,
			String numPassportCollected) {
		super();
		this.statDate = statDate;
		this.pendingAfis = pendingAfis;
		this.pendingInvestigation = pendingInvestigation;
		this.pendingVerified = pendingVerified;
		this.pendingPerso = pendingPerso;
		this.pendingQc = pendingQc;
		this.pendingDispatch = pendingDispatch;
		this.pendingReceive = pendingReceive;
		this.pendingIssue = pendingIssue;
		this.numPassportCollected = numPassportCollected;
	}
	public String getStatDate() {
		return statDate;
	}
	public void setStatDate(String statDate) {
		this.statDate = statDate;
	}
	public String getPendingAfis() {
		return pendingAfis;
	}
	public void setPendingAfis(String pendingAfis) {
		this.pendingAfis = pendingAfis;
	}
	public String getPendingInvestigation() {
		return pendingInvestigation;
	}
	public void setPendingInvestigation(String pendingInvestigation) {
		this.pendingInvestigation = pendingInvestigation;
	}
	public String getPendingVerified() {
		return pendingVerified;
	}
	public void setPendingVerified(String pendingVerified) {
		this.pendingVerified = pendingVerified;
	}
	public String getPendingPerso() {
		return pendingPerso;
	}
	public void setPendingPerso(String pendingPerso) {
		this.pendingPerso = pendingPerso;
	}
	public String getPendingQc() {
		return pendingQc;
	}
	public void setPendingQc(String pendingQc) {
		this.pendingQc = pendingQc;
	}
	public String getPendingDispatch() {
		return pendingDispatch;
	}
	public void setPendingDispatch(String pendingDispatch) {
		this.pendingDispatch = pendingDispatch;
	}
	public String getPendingReceive() {
		return pendingReceive;
	}
	public void setPendingReceive(String pendingReceive) {
		this.pendingReceive = pendingReceive;
	}
	public String getPendingIssue() {
		return pendingIssue;
	}
	public void setPendingIssue(String pendingIssue) {
		this.pendingIssue = pendingIssue;
	}
	public String getNumPassportCollected() {
		return numPassportCollected;
	}
	public void setNumPassportCollected(String numPassportCollected) {
		this.numPassportCollected = numPassportCollected;
	}
	
		
}
