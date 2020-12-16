package com.nec.asia.nic.comp.officalNation.dao;

import java.util.List;

import com.nec.asia.nic.comp.signerGovs.domain.SignerGovs;
import com.nec.asia.nic.comp.trans.domain.NicDecisionManager;
import com.nec.asia.nic.comp.trans.domain.NicOfficalNation;
import com.nec.asia.nic.comp.trans.dto.AssignmentFilterAll;
import com.nec.asia.nic.framework.PaginatedResult;
import com.nec.asia.nic.framework.dao.GenericDao;

public interface OfficalNationDao extends GenericDao<NicOfficalNation, Long> {
	//default methods from super class
	//public PaymentDef findById(PaymentDefId id);
	//public Long save (PaymentDef e);
	//public void saveOrUpdate(PaymentDef e);
    //public PaymentDef merge(PaymentDef e);
	//public void delete(PaymentDef e);
	//public void delete(PaymentDefId id);
	//public List<PaymentDef> findAll ();

	public PaginatedResult<NicOfficalNation> listOfficalNationAllBySearch(String officalNationNo,int PageNo, int PageSize, AssignmentFilterAll assignmentFilter) throws Exception;
	
	public boolean createOfficalNation(NicOfficalNation officalNation) throws Exception;
	
	public boolean editOfficalNation(NicOfficalNation officalNationedit) throws Exception;
	
	public NicOfficalNation findByOfficalNationNo(String OfficalNationNo) throws Exception;
}
