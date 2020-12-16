package com.nec.asia.nic.comp.trans.dao;

import java.util.List;

import com.nec.asia.nic.comp.trans.domain.BaseModelList;
import com.nec.asia.nic.comp.trans.domain.BaseModelSingle;
import com.nec.asia.nic.comp.trans.domain.BorderGate;
import com.nec.asia.nic.comp.trans.domain.EppArchiveCode;
import com.nec.asia.nic.comp.trans.domain.EppPerson;
import com.nec.asia.nic.comp.trans.domain.EppPersonFamily;
import com.nec.asia.nic.framework.dao.GenericDao;

public interface EppPersonFamilyDao extends GenericDao<EppPersonFamily, Long>{
	
	public List<EppPersonFamily> findAllEppPersonFamily(String searchName);
	public BaseModelList<EppPersonFamily> findAllEppPersonFamily1(Long personId);
	
	public BaseModelSingle<Boolean> saveOrUpdateData(EppPersonFamily detail);

	
}
