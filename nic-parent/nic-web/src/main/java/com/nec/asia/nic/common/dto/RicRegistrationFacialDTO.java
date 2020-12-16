package com.nec.asia.nic.common.dto;

import java.io.Serializable;

public class RicRegistrationFacialDTO implements Serializable {


	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private boolean indicator;
	private String face;
	private String status;
	
	public String getFace() {
		return face;
	}
	public void setFace(String face) {
		this.face = face;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public boolean isIndicator() {
		return indicator;
	}
	public void setIndicator(boolean indicator) {
		this.indicator = indicator;
	}
	
	
	
	
	
}