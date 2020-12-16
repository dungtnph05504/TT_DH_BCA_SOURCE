package com.nec.asia.nic.comp.trans.dao;

import java.util.List;

import com.nec.asia.nic.comp.trans.domain.BorderGate;
import com.nec.asia.nic.comp.trans.domain.EppDetailReciept;
import com.nec.asia.nic.comp.trans.domain.EppRecieptManager;
import com.nec.asia.nic.framework.dao.GenericDao;

public interface DetailRecieptDao extends GenericDao<EppDetailReciept, Long>{
	List<EppDetailReciept> findAllEppDetailReciept(String recieptNo);
	EppDetailReciept findDetailReciept(String recieptNo, String transactionId) throws Exception;
}
