package com.nec.asia.nic.comp.trans.dto;

public class InfoStorageDto {
	private String tenPhi;
	private String soTien;
	private String moTa;
	
	public InfoStorageDto() {
		super();
	}

	public InfoStorageDto(String tenPhi, String soTien, String moTa) {
		super();
		this.tenPhi = tenPhi;
		this.soTien = soTien;
		this.moTa = moTa;
	}

	public String getTenPhi() {
		return tenPhi;
	}

	public void setTenPhi(String tenPhi) {
		this.tenPhi = tenPhi;
	}

	public String getSoTien() {
		return soTien;
	}

	public void setSoTien(String soTien) {
		this.soTien = soTien;
	}

	public String getMoTa() {
		return moTa;
	}

	public void setMoTa(String moTa) {
		this.moTa = moTa;
	}
	
	
	
	
}
