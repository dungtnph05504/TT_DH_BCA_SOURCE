package com.nec.asia.nic.investigation.dto;

public class SiteConfig {
	private String siteId;
	private Integer stage;
	
	public SiteConfig() {
		super();
	}
	public SiteConfig(String siteId, Integer stage) {
		super();
		this.siteId = siteId;
		this.stage = stage;
	}
	public String getSiteId() {
		return siteId;
	}
	public void setSiteId(String siteId) {
		this.siteId = siteId;
	}
	public Integer getStage() {
		return stage;
	}
	public void setStage(Integer stage) {
		this.stage = stage;
	}
	
	
}
