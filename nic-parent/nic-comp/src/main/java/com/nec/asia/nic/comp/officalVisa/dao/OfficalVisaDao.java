package com.nec.asia.nic.comp.officalVisa.dao;

import java.util.List;

import com.nec.asia.nic.comp.signerGovs.domain.SignerGovs;
import com.nec.asia.nic.comp.trans.domain.NicDecisionManager;
import com.nec.asia.nic.comp.trans.domain.NicOfficalVisa;
import com.nec.asia.nic.framework.PaginatedResult;
import com.nec.asia.nic.framework.dao.GenericDao;

public interface OfficalVisaDao extends GenericDao<NicOfficalVisa, Long> {
	//default methods from super class
	//public PaymentDef findById(PaymentDefId id);
	//public Long save (PaymentDef e);
	//public void saveOrUpdate(PaymentDef e);
    //public PaymentDef merge(PaymentDef e);
	//public void delete(PaymentDef e);
	//public void delete(PaymentDefId id);
	//public List<PaymentDef> findAll ();

	public PaginatedResult<NicOfficalVisa> listOfficalVisaAllBySearch(String countryCode,int PageNo, int PageSize) throws Exception;
	
	public boolean createOfficalVisa(NicOfficalVisa officalVisa) throws Exception;
	
	public boolean editOfficalVisa(NicOfficalVisa officalVisaedit) throws Exception;
	
	public NicOfficalVisa findByOfficalVisa(String countryCode, String passpor) throws Exception;
}
