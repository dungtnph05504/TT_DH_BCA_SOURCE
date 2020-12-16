package com.nec.asia.nic.comp.bufPersonInvestigation.dao;

import java.util.List;

import com.nec.asia.nic.comp.trans.domain.BufEppPersonInvestigation;
import com.nec.asia.nic.framework.dao.GenericDao;

public interface BufPersonInvestigationDao extends GenericDao<BufEppPersonInvestigation, Long> {
	//default methods from super class
	//public PaymentDef findById(PaymentDefId id);
	//public Long save (PaymentDef e);
	//public void saveOrUpdate(PaymentDef e);
    //public PaymentDef merge(PaymentDef e);
	//public void delete(PaymentDef e);
	//public void delete(PaymentDefId id);
	//public List<PaymentDef> findAll ();

	public BufEppPersonInvestigation findByTranId(String tranId) throws Exception;
	
	public List<BufEppPersonInvestigation> findListByTranId(String tranId) throws Exception;
	
	public Boolean updateAndCreate(BufEppPersonInvestigation obj)throws Exception;

	public List<BufEppPersonInvestigation> findListByTranMasterId(String tranId) throws Exception;
	
	public List<BufEppPersonInvestigation> findListByTranMasterIdAndPersonCode(String tranMasterId, String personCode, String tranId, String maCaNhan) throws Exception;

	public Boolean deleteBufPersonByTranMasterId(String tranMasterId) throws Exception;
	
}
