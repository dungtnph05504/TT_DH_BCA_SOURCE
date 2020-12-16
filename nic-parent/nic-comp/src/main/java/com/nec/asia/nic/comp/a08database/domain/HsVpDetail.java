package com.nec.asia.nic.comp.a08database.domain;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement(name = "HsVpDetail")
@XmlType(name = "HsVpDetail", propOrder = { "soDangKy", "donViLapId",
		"chiTietDonViLap", "nguoiDuyet", "chucVuNguoiDuyet",
		"capBacNguoiDuyet", "canBoQlhs", "ngayPhatHien", "ngayDangKy",
		"maNoiXayRa", "noiXayRa", "tenVuViec", "nguoiToChucThietHai",
		"ngayChuyenCqDieuTra", "coQuanDieuTra", "ngayKetThuc", "maLyDoKetThuc",
		"lyDoKetThuc", "soLuu", "ngayNopLuu", "ghiChu" })
public class HsVpDetail {
	private String soDangKy;
	private Double donViLapId;
	private String chiTietDonViLap;
	private String nguoiDuyet;
	private String chucVuNguoiDuyet;
	private String capBacNguoiDuyet;
	private String canBoQlhs;
	private String ngayPhatHien;
	private String ngayDangKy;
	private String maNoiXayRa;
	private String noiXayRa;
	private String tenVuViec;
	private String nguoiToChucThietHai;
	private String ngayChuyenCqDieuTra;
	private String coQuanDieuTra;
	private String ngayKetThuc;
	private String maLyDoKetThuc;
	private String lyDoKetThuc;
	private String soLuu;
	private String ngayNopLuu;
	private String ghiChu;
	public String getSoDangKy() {
		return soDangKy;
	}
	public void setSoDangKy(String soDangKy) {
		this.soDangKy = soDangKy;
	}
	public Double getDonViLapId() {
		return donViLapId;
	}
	public void setDonViLapId(Double donViLapId) {
		this.donViLapId = donViLapId;
	}
	public String getChiTietDonViLap() {
		return chiTietDonViLap;
	}
	public void setChiTietDonViLap(String chiTietDonViLap) {
		this.chiTietDonViLap = chiTietDonViLap;
	}
	public String getNguoiDuyet() {
		return nguoiDuyet;
	}
	public void setNguoiDuyet(String nguoiDuyet) {
		this.nguoiDuyet = nguoiDuyet;
	}
	public String getChucVuNguoiDuyet() {
		return chucVuNguoiDuyet;
	}
	public void setChucVuNguoiDuyet(String chucVuNguoiDuyet) {
		this.chucVuNguoiDuyet = chucVuNguoiDuyet;
	}
	public String getCapBacNguoiDuyet() {
		return capBacNguoiDuyet;
	}
	public void setCapBacNguoiDuyet(String capBacNguoiDuyet) {
		this.capBacNguoiDuyet = capBacNguoiDuyet;
	}
	public String getCanBoQlhs() {
		return canBoQlhs;
	}
	public void setCanBoQlhs(String canBoQlhs) {
		this.canBoQlhs = canBoQlhs;
	}
	public String getNgayPhatHien() {
		return ngayPhatHien;
	}
	public void setNgayPhatHien(String ngayPhatHien) {
		this.ngayPhatHien = ngayPhatHien;
	}
	public String getNgayDangKy() {
		return ngayDangKy;
	}
	public void setNgayDangKy(String ngayDangKy) {
		this.ngayDangKy = ngayDangKy;
	}
	public String getMaNoiXayRa() {
		return maNoiXayRa;
	}
	public void setMaNoiXayRa(String maNoiXayRa) {
		this.maNoiXayRa = maNoiXayRa;
	}
	public String getNoiXayRa() {
		return noiXayRa;
	}
	public void setNoiXayRa(String noiXayRa) {
		this.noiXayRa = noiXayRa;
	}
	public String getTenVuViec() {
		return tenVuViec;
	}
	public void setTenVuViec(String tenVuViec) {
		this.tenVuViec = tenVuViec;
	}
	public String getNguoiToChucThietHai() {
		return nguoiToChucThietHai;
	}
	public void setNguoiToChucThietHai(String nguoiToChucThietHai) {
		this.nguoiToChucThietHai = nguoiToChucThietHai;
	}
	public String getNgayChuyenCqDieuTra() {
		return ngayChuyenCqDieuTra;
	}
	public void setNgayChuyenCqDieuTra(String ngayChuyenCqDieuTra) {
		this.ngayChuyenCqDieuTra = ngayChuyenCqDieuTra;
	}
	public String getCoQuanDieuTra() {
		return coQuanDieuTra;
	}
	public void setCoQuanDieuTra(String coQuanDieuTra) {
		this.coQuanDieuTra = coQuanDieuTra;
	}
	public String getNgayKetThuc() {
		return ngayKetThuc;
	}
	public void setNgayKetThuc(String ngayKetThuc) {
		this.ngayKetThuc = ngayKetThuc;
	}
	public String getMaLyDoKetThuc() {
		return maLyDoKetThuc;
	}
	public void setMaLyDoKetThuc(String maLyDoKetThuc) {
		this.maLyDoKetThuc = maLyDoKetThuc;
	}
	public String getLyDoKetThuc() {
		return lyDoKetThuc;
	}
	public void setLyDoKetThuc(String lyDoKetThuc) {
		this.lyDoKetThuc = lyDoKetThuc;
	}
	public String getSoLuu() {
		return soLuu;
	}
	public void setSoLuu(String soLuu) {
		this.soLuu = soLuu;
	}
	public String getNgayNopLuu() {
		return ngayNopLuu;
	}
	public void setNgayNopLuu(String ngayNopLuu) {
		this.ngayNopLuu = ngayNopLuu;
	}
	public String getGhiChu() {
		return ghiChu;
	}
	public void setGhiChu(String ghiChu) {
		this.ghiChu = ghiChu;
	}
	
}
