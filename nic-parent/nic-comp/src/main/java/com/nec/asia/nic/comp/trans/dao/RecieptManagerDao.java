package com.nec.asia.nic.comp.trans.dao;

import java.util.List;

import com.nec.asia.nic.comp.trans.domain.BaseModelList;
import com.nec.asia.nic.comp.trans.domain.BaseModelSingle;
import com.nec.asia.nic.comp.trans.domain.BorderGate;
import com.nec.asia.nic.comp.trans.domain.EppRecieptManager;
import com.nec.asia.nic.framework.dao.GenericDao;

public interface RecieptManagerDao extends GenericDao<EppRecieptManager, Long>{
	BaseModelList<EppRecieptManager> findAllEppRecieptManager(String recieptNo);
	public BaseModelSingle<Boolean> saveOrUpdateNew(EppRecieptManager e);
}
