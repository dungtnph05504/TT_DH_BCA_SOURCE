package com.nec.asia.nic.comp.trans.dto;

import java.util.List;

import com.nec.asia.nic.comp.trans.domain.EppPersonFamily;

public class InforPassportDto {
   //transaction
	private HoSoDto hoso;
	
	private ListHandoverDto handoverA;
	private ListHandoverDto handoverB;
	private ListHandoverDto handoverC;
	private List<PersonFamiliDto> nhanthan;
	
	
	
	public ListHandoverDto getHandoverA() {
		return handoverA;
	}
	public void setHandoverA(ListHandoverDto handoverA) {
		this.handoverA = handoverA;
	}
	public ListHandoverDto getHandoverB() {
		return handoverB;
	}
	public void setHandoverB(ListHandoverDto handoverB) {
		this.handoverB = handoverB;
	}
	public ListHandoverDto getHandoverC() {
		return handoverC;
	}
	public void setHandoverC(ListHandoverDto handoverC) {
		this.handoverC = handoverC;
	}
	public HoSoDto getHoso() {
		return hoso;
	}
	public void setHoso(HoSoDto hoso) {
		this.hoso = hoso;
	}
	public List<PersonFamiliDto> getNhanthan() {
		return nhanthan;
	}
	public void setNhanthan(List<PersonFamiliDto> nhanthan) {
		this.nhanthan = nhanthan;
	}
	
	
}
