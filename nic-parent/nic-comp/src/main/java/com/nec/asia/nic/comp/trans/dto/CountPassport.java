package com.nec.asia.nic.comp.trans.dto;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
public class CountPassport {

	private String regSite;
	private Integer total;
	
	public String getRegSite() {
		return regSite;
	}
	public void setRegSite(String regSite) {
		this.regSite = regSite;
	}
	public Integer getTotal() {
		return total;
	}
	public void setTotal(Integer total) {
		this.total = total;
	}
	
	
}
