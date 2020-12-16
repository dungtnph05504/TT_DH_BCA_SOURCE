package com.nec.asia.nic.investigation.dto;

public class ListApiDto {
	
	private int stt;
	private String description;
	private Boolean isOpen;
	private String paraName;
	
	public ListApiDto() {
		super();
	}

	public ListApiDto(int stt, String description, Boolean isOpen, String paraName) {
		super();
		this.stt = stt;
		this.description = description;
		this.isOpen = isOpen;
		this.paraName = paraName;
	}

	public int getStt() {
		return stt;
	}

	public void setStt(int stt) {
		this.stt = stt;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Boolean getIsOpen() {
		return isOpen;
	}

	public void setIsOpen(Boolean isOpen) {
		this.isOpen = isOpen;
	}
	
	public String getParaName() {
		return paraName;
	}

	public void setParaName(String paraName) {
		this.paraName = paraName;
	}
	
}
