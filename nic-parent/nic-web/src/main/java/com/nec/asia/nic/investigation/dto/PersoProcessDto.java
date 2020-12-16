package com.nec.asia.nic.investigation.dto;

import java.util.Map;

public class PersoProcessDto {
	private String siteGroups;
	private Map<String, Integer> duLieuCTH;

	public PersoProcessDto() {
		super();
	}

	public PersoProcessDto(String siteGroups, Map<String, Integer> duLieuCTH) {
		super();
		this.siteGroups = siteGroups;
		this.duLieuCTH = duLieuCTH;
	}

	public String getSiteGroups() {
		return siteGroups;
	}

	public void setSiteGroups(String siteGroups) {
		this.siteGroups = siteGroups;
	}

	public Map<String, Integer> getDuLieuCTH() {
		return duLieuCTH;
	}

	public void setDuLieuCTH(Map<String, Integer> duLieuCTH) {
		this.duLieuCTH = duLieuCTH;
	}
	
	
}
