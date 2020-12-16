package com.nec.asia.nic.listlogcheckconnection.controller;

public class Site {
	private String siteName;
	private String siteCode;
	
	public Site(String siteName, String siteCode) {
		super();
		this.siteName = siteName;
		this.siteCode = siteCode;
	}
	public String getSiteCode() {
		return siteCode;
	}
	public void setSiteCode(String siteCode) {
		this.siteCode = siteCode;
	}
	public Site() {
	}
	public String getSiteName() {
		return siteName;
	}
	public void setSiteName(String siteName) {
		this.siteName = siteName;
	}
	
}
