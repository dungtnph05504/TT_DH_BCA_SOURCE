package com.nec.asia.nic.comp.trans.dao;

import java.util.List;

import com.nec.asia.nic.comp.trans.domain.EppIssuance;
import com.nec.asia.nic.framework.dao.GenericDao;

public interface EppIssuanceDao extends GenericDao<EppIssuance, Long> {
	public void saveOrUpdateEppIssuance(EppIssuance eppIssuance) throws Exception;

	public List<EppIssuance> findAllByTranId(String transactionId)throws Exception;

	public Long saveEppIssuance(EppIssuance eppIssuance)throws Exception;
}
