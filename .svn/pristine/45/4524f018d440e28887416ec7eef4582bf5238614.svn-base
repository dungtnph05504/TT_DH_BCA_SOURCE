package com.nec.asia.nic.comp.decisionManager.dao;

import java.util.List;

import com.nec.asia.nic.comp.signerGovs.domain.SignerGovs;
import com.nec.asia.nic.comp.trans.domain.NicBusinessList;
import com.nec.asia.nic.comp.trans.domain.NicDecisionManager;
import com.nec.asia.nic.framework.PaginatedResult;
import com.nec.asia.nic.framework.dao.GenericDao;

public interface BusinessListDao extends GenericDao<NicBusinessList, Long> {
	//default methods from super class
	//public PaymentDef findById(PaymentDefId id);
	//public Long save (PaymentDef e);
	//public void saveOrUpdate(PaymentDef e);
    //public PaymentDef merge(PaymentDef e);
	//public void delete(PaymentDef e);
	//public void delete(PaymentDefId id);
	//public List<PaymentDef> findAll ();

	public PaginatedResult<NicBusinessList> listBusinessListAllBySearch(String decisionNumber,int PageNo, int PageSize) throws Exception;
	
	public boolean createBusinessList(NicBusinessList businessList) throws Exception;
	
	public boolean editBusinessList(NicBusinessList businessListedit) throws Exception;
	
	public List<NicBusinessList> findListByDecisionNumber(String decisionNumber) throws Exception;
	public PaginatedResult<NicBusinessList> findListByDecisionNumber1(String decisionNumber, int pageNo, int pageSize) throws Exception;
	
	public NicBusinessList findBySerial (int serial, String decisionNumber) throws Exception;
}
