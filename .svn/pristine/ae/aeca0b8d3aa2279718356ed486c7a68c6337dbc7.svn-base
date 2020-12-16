package com.nec.asia.nic.investigation.dto;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.nec.asia.nic.comp.immihistory.domain.ImmiHistory;
import com.nec.asia.nic.comp.trans.domain.NicTransaction;

public class InvestigationTargetDto {

	//Lịch sử xuất nhập cảnh
	public List<ImmiHistory> listImmi = new ArrayList<ImmiHistory>();
		
	//Danh sách hộ chiếu mất/hủy
	public List<NicTransaction> listLost = new ArrayList<NicTransaction>();

	public InvestigationTargetDto(){
		
	}
	
	public InvestigationTargetDto(List<ImmiHistory> listImmi,
			List<NicTransaction> listLost) {
		super();
		this.listImmi = listImmi;
		this.listLost = listLost;
	}

	public List<ImmiHistory> getListImmi() {
		return listImmi;
	}

	public void setListImmi(List<ImmiHistory> listImmi) {
		this.listImmi = listImmi;
	}

	public List<NicTransaction> getListLost() {
		return listLost;
	}

	public void setListLost(List<NicTransaction> listLost) {
		this.listLost = listLost;
	}
	
	
}
