package com.nec.asia.nic.comp.decisionManager.dao;

import java.util.List;

import com.nec.asia.nic.comp.signerGovs.domain.SignerGovs;
import com.nec.asia.nic.comp.trans.domain.NicDecisionManager;
import com.nec.asia.nic.comp.trans.dto.AssignmentFilterAll;
import com.nec.asia.nic.framework.PaginatedResult;
import com.nec.asia.nic.framework.dao.GenericDao;

public interface DecisionManagerDao extends GenericDao<NicDecisionManager, Long> {
	//default methods from super class
	//public PaymentDef findById(PaymentDefId id);
	//public Long save (PaymentDef e);
	//public void saveOrUpdate(PaymentDef e);
    //public PaymentDef merge(PaymentDef e);
	//public void delete(PaymentDef e);
	//public void delete(PaymentDefId id);
	//public List<PaymentDef> findAll ();

	public PaginatedResult<NicDecisionManager> listDecisionManagerAllBySearch(String decisionManager,int PageNo, int PageSize, AssignmentFilterAll assignmentFilter) throws Exception;
	
	public boolean createDecisionManager(NicDecisionManager decisionManager) throws Exception;
	
	public boolean editDecisionManager(NicDecisionManager decisionManageredit) throws Exception;
	
	public NicDecisionManager findByDecisionNumber(String decisionNumber) throws Exception;
}
