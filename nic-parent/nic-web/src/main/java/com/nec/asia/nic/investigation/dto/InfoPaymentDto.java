package com.nec.asia.nic.investigation.dto;

public class InfoPaymentDto {
	private String maPhuPhi;
	private String tenPhuPhi;
	private String soTien;
	private String trangThai;
	private String photoStr;
	private String esColectionDate;
	private int soLuong; 
	private String tongTien;
	
	private String maPhiChinh;
	private String tenPhiChinh;
	private String tienPhiChinh;
	
	
	public InfoPaymentDto() {
		super();
	}
	public InfoPaymentDto(String maPhuPhi, String tenPhuPhi, String soTien,
			String trangThai) {
		super();
		this.maPhuPhi = maPhuPhi;
		this.tenPhuPhi = tenPhuPhi;
		this.soTien = soTien;
		this.trangThai = trangThai;
	}
	
	
	
	public String getMaPhiChinh() {
		return maPhiChinh;
	}
	public void setMaPhiChinh(String maPhiChinh) {
		this.maPhiChinh = maPhiChinh;
	}
	public String getTenPhiChinh() {
		return tenPhiChinh;
	}
	public void setTenPhiChinh(String tenPhiChinh) {
		this.tenPhiChinh = tenPhiChinh;
	}
	public String getTienPhiChinh() {
		return tienPhiChinh;
	}
	public void setTienPhiChinh(String tienPhiChinh) {
		this.tienPhiChinh = tienPhiChinh;
	}
	public String getTongTien() {
		return tongTien;
	}
	public void setTongTien(String tongTien) {
		this.tongTien = tongTien;
	}
	public int getSoLuong() {
		return soLuong;
	}
	public void setSoLuong(int soLuong) {
		this.soLuong = soLuong;
	}
	public String getPhotoStr() {
		return photoStr;
	}
	public void setPhotoStr(String photoStr) {
		this.photoStr = photoStr;
	}
	public String getEsColectionDate() {
		return esColectionDate;
	}
	public void setEsColectionDate(String esColectionDate) {
		this.esColectionDate = esColectionDate;
	}
	public String getMaPhuPhi() {
		return maPhuPhi;
	}
	public void setMaPhuPhi(String maPhuPhi) {
		this.maPhuPhi = maPhuPhi;
	}
	public String getTenPhuPhi() {
		return tenPhuPhi;
	}
	public void setTenPhuPhi(String tenPhuPhi) {
		this.tenPhuPhi = tenPhuPhi;
	}
	public String getSoTien() {
		return soTien;
	}
	public void setSoTien(String soTien) {
		this.soTien = soTien;
	}
	public String getTrangThai() {
		return trangThai;
	}
	public void setTrangThai(String trangThai) {
		this.trangThai = trangThai;
	}
	
	
}
