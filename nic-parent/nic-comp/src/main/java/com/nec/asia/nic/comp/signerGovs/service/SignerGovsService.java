package com.nec.asia.nic.comp.signerGovs.service;

import java.util.List;

import com.nec.asia.nic.comp.listHandover.domain.NicListHandover;
import com.nec.asia.nic.comp.signerGovs.domain.SignerGovs;
import com.nec.asia.nic.framework.PaginatedResult;
import com.nec.asia.nic.framework.service.BusinessService;


/**
 * 
 * @author chris_wong
 *
 */
/* 
 * Modification History:
 *  
 * 18 Aug 2013 (chris): add method to retrieve payment def for replacement transaction.
 * 26 Sep 2013 (chris): add BASIC_FEE_FOR_SC for senior citizen fix rate.
 */

public interface SignerGovsService extends BusinessService<SignerGovs, Long>{
	
	public PaginatedResult<SignerGovs> findAllSignerGovs(String search, int pageNo, int pageSize) throws Exception;

	public boolean createSignerGovs(SignerGovs signerGov);
	
	public boolean editSignerGovs(SignerGovs signerGov);
	
	public SignerGovs findSignerByCode(String code);
	
	public List<SignerGovs> findListSignerByCode(String code);
	
}
