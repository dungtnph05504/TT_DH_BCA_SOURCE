package com.nec.asia.nic.comp.trans.dto;

public class SearchResultInA08Dto {

	private String pHoTen;
	private String pNgaySinh;
	private String pKieuNS;
	private String pQTich;
	private String pGTinh;
	private String pSoHC;
	private String pSoCMND;
	private String pMaCaNhan;
	
	public String getpHoTen() {
		return pHoTen;
	}
	public void setpHoTen(String pHoTen) {
		this.pHoTen = pHoTen;
	}
	public String getpNgaySinh() {
		return pNgaySinh;
	}
	public void setpNgaySinh(String pNgaySinh) {
		this.pNgaySinh = pNgaySinh;
	}
	public String getpKieuNS() {
		return pKieuNS;
	}
	public void setpKieuNS(String pKieuNS) {
		this.pKieuNS = pKieuNS;
	}
	public String getpQTich() {
		return pQTich;
	}
	public void setpQTich(String pQTich) {
		this.pQTich = pQTich;
	}
	public String getpGTinh() {
		return pGTinh;
	}
	public void setpGTinh(String pGTinh) {
		this.pGTinh = pGTinh;
	}
	public String getpSoHC() {
		return pSoHC;
	}
	public void setpSoHC(String pSoHC) {
		this.pSoHC = pSoHC;
	}
	public String getpSoCMND() {
		return pSoCMND;
	}
	public void setpSoCMND(String pSoCMND) {
		this.pSoCMND = pSoCMND;
	}
	public String getpMaCaNhan() {
		return pMaCaNhan;
	}
	public void setpMaCaNhan(String pMaCaNhan) {
		this.pMaCaNhan = pMaCaNhan;
	}
	@Override
	public String toString() {
		return "SearchResultInA08Dto [HoTen=" + pHoTen + ", NgaySinh="
				+ pNgaySinh + ", KieuNS=" + pKieuNS + ", QTich=" + pQTich
				+ ", GTinh=" + pGTinh + ", SoHC=" + pSoHC + ", SoCMND="
				+ pSoCMND + ", MaCaNhan=" + pMaCaNhan + "]";
	}
}
