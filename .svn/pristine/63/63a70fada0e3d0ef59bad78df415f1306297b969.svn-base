package com.nec.asia.nic.comp.trans.service;

import java.util.List;

import com.nec.asia.nic.comp.trans.domain.BaseModelSingle;
import com.nec.asia.nic.comp.trans.domain.BorderGate;
import com.nec.asia.nic.comp.trans.domain.EppArchiveCode;

public interface ArchiveCodeService {
	
	public List<EppArchiveCode> findAllEppArchiveCode(String type, int year, String code, int stt);
	
	public BaseModelSingle<Boolean> saveOrUpdateData(EppArchiveCode detail);
	
	public int amountArchiveCode(String type, int year, String code, int stt);
	
	public BaseModelSingle<EppArchiveCode> findArchiveCodeByAll(String docType, int nYear, String officeCode, int incNo);
}
