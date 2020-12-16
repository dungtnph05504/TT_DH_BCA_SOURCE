package com.nec.asia.nic.investigation.dto;

import java.util.ArrayList;
import java.util.List;

import com.nec.asia.nic.comp.trans.domain.NicDocumentData;

public class InlichsuDto {
	
	private String hoten;
	private String ngaythangnam;
	private String gioitinh;
	private String ngaysinh;
	private String noisinh;
	private String cmnd;
	private String diachi;
	private String nghenghiep;
	private String ghichu;
	private List<NicDocumentData> docData = new ArrayList<NicDocumentData>();

	public String getHoten() {
		return hoten;
	}

	public void setHoten(String hoten) {
		this.hoten = hoten;
	}

	public String getNgaythangnam() {
		return ngaythangnam;
	}

	public void setNgaythangnam(String ngaythangnam) {
		this.ngaythangnam = ngaythangnam;
	}

	public String getGioitinh() {
		return gioitinh;
	}

	public void setGioitinh(String gioitinh) {
		this.gioitinh = gioitinh;
	}

	public String getNgaysinh() {
		return ngaysinh;
	}

	public void setNgaysinh(String ngaysinh) {
		this.ngaysinh = ngaysinh;
	}

	public String getNoisinh() {
		return noisinh;
	}

	public void setNoisinh(String noisinh) {
		this.noisinh = noisinh;
	}

	public String getCmnd() {
		return cmnd;
	}

	public void setCmnd(String cmnd) {
		this.cmnd = cmnd;
	}

	public String getDiachi() {
		return diachi;
	}

	public void setDiachi(String diachi) {
		this.diachi = diachi;
	}

	public String getNghenghiep() {
		return nghenghiep;
	}

	public void setNghenghiep(String nghenghiep) {
		this.nghenghiep = nghenghiep;
	}

	public String getGhichu() {
		return ghichu;
	}

	public void setGhichu(String ghichu) {
		this.ghichu = ghichu;
	}

	public List<NicDocumentData> getDocData() {
		return docData;
	}

	public void setDocData(List<NicDocumentData> docData) {
		this.docData = docData;
	}
	
	
}
