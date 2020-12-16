package com.nec.asia.nic.comp.signerGovs.dao;

import java.util.List;

import com.nec.asia.nic.comp.signerGovs.domain.SignerGovs;
import com.nec.asia.nic.framework.PaginatedResult;
import com.nec.asia.nic.framework.dao.GenericDao;

public interface SignerGovsDao extends GenericDao<SignerGovs, Long> {
	//default methods from super class
	//public PaymentDef findById(PaymentDefId id);
	//public Long save (PaymentDef e);
	//public void saveOrUpdate(PaymentDef e);
    //public PaymentDef merge(PaymentDef e);
	//public void delete(PaymentDef e);
	//public void delete(PaymentDefId id);
	//public List<PaymentDef> findAll ();

	public PaginatedResult<SignerGovs> listSignerGovsAllBySearch(String codeSigner,int PageNo, int PageSize) throws Exception;
	
	public boolean createSignerGovs(SignerGovs signerGov) throws Exception;
	
	public boolean editSignerGovs(SignerGovs signerGov) throws Exception;
	
	public SignerGovs findByCode(String code) throws Exception;
	
	public List<SignerGovs> findListByCode(String code) throws Exception;
}
