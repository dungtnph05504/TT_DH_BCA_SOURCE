package com.nec.asia.nic.comp.trans.dao;

import java.util.List;

import com.nec.asia.nic.comp.trans.domain.BorderGate;
import com.nec.asia.nic.comp.trans.domain.EppArchiveCode;
import com.nec.asia.nic.comp.trans.domain.EppPerson;
import com.nec.asia.nic.comp.trans.domain.EppPersonAttchmnt;
import com.nec.asia.nic.framework.dao.GenericDao;

public interface EppPersonAttchmntDao extends GenericDao<EppPersonAttchmnt, Long>{
	
	public List<EppPersonAttchmnt> findAllEppPersonAttchmnt(String[] type, String serialNo);
	
	public List<EppPersonAttchmnt> findAllEppPersonAttchmnt1(String[] type, Long personId);
	
	public Boolean saveOrUpdateData(EppPersonAttchmnt detail);
	
}
