package com.nec.asia.nic.comp.officalVisa.service;

import java.util.List;

import com.nec.asia.nic.comp.listHandover.domain.NicListHandover;
import com.nec.asia.nic.comp.signerGovs.domain.SignerGovs;
import com.nec.asia.nic.comp.trans.domain.NicDecisionManager;
import com.nec.asia.nic.comp.trans.domain.NicOfficalVisa;
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

public interface OfficalVisaService extends BusinessService<NicOfficalVisa, Long>{
	
	public PaginatedResult<NicOfficalVisa> findAllOfficalVisa(String search, int pageNo, int pageSize) throws Exception;

	public boolean createOfficalVisa(NicOfficalVisa officalVisa);
	
	public boolean editOfficalVisa(NicOfficalVisa officalVisaedit);
	
	public NicOfficalVisa findOfficalVisaByCode(String countryCode, String passportType);
}
