package com.nec.asia.nic.comp.trans.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nec.asia.nic.comp.trans.dao.ArchiveCodeDao;
import com.nec.asia.nic.comp.trans.dao.BorderGateDao;
import com.nec.asia.nic.comp.trans.domain.BaseModelSingle;
import com.nec.asia.nic.comp.trans.domain.BorderGate;
import com.nec.asia.nic.comp.trans.domain.EppArchiveCode;
import com.nec.asia.nic.comp.trans.service.ArchiveCodeService;
import com.nec.asia.nic.comp.trans.service.BorderGateService;
import com.nec.asia.nic.comp.ws.log.domain.CreateMessageException;

@Service
public class ArchiveCodeServiceImpl implements ArchiveCodeService {

	@Autowired
	private ArchiveCodeDao dao;
	
	@Override
	public List<EppArchiveCode> findAllEppArchiveCode(String type, int year, String code, int stt) {
		try {
			return dao.findAllEppArchiveCode(type, year, code, stt);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
		}
		return null;
	}
	
	@Override
	public BaseModelSingle<Boolean> saveOrUpdateData(EppArchiveCode detail){
		try {
			return dao.saveOrUpdateData(detail);
		} catch (Exception e) {
			e.printStackTrace();
			return new BaseModelSingle<Boolean>(false, false, CreateMessageException.createMessageException(e) + " - saveOrUpdateData - thất bại");
		}
	}
	
	@Override
	public int amountArchiveCode(String type, int year, String code, int stt){
		try {
			return dao.amountArchiveCode(type, year, code, stt);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
		}
		return -1;
	}

	@Override
	public BaseModelSingle<EppArchiveCode> findArchiveCodeByAll(String docType,
			int nYear, String officeCode, int incNo) {
		try {
			return this.dao.findArchiveCodeByAll(docType, nYear, officeCode, incNo);
		} catch (Exception e) {
			return new BaseModelSingle<EppArchiveCode>(null, false, CreateMessageException.createMessageException(e) + " - findArchiveCodeByAll - thất bại.");
		}
	}
}
