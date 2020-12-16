package com.nec.asia.nic.comp.officalNation.service;

import java.util.List;

import com.nec.asia.nic.comp.listHandover.domain.NicListHandover;
import com.nec.asia.nic.comp.signerGovs.domain.SignerGovs;
import com.nec.asia.nic.comp.trans.domain.NicDecisionManager;
import com.nec.asia.nic.comp.trans.domain.NicOfficalNation;
import com.nec.asia.nic.comp.trans.dto.AssignmentFilterAll;
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

public interface OfficalNationService extends BusinessService<NicOfficalNation, Long>{
	
	public PaginatedResult<NicOfficalNation> findAllOfficalNation(String search, int pageNo, int pageSize, AssignmentFilterAll assignmentFilter) throws Exception;

	public boolean createOfficalNation(NicOfficalNation officalNation);
	
	public boolean editOfficalNation(NicOfficalNation officalNationedit);
	
	public NicOfficalNation findOfficalNationByCode(String officalNationNo);
}
