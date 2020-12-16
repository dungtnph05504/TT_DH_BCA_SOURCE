package com.nec.asia.nic.comp.bufPersonInvestigation.service;

import java.util.List;

import com.nec.asia.nic.comp.trans.domain.BufEppPersonInvestigation;
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

public interface BufPersonInvestigationService extends BusinessService<BufEppPersonInvestigation, Long>{
	
	public BufEppPersonInvestigation findByTranId(String tranId) throws Exception;
	
	public List<BufEppPersonInvestigation> findListByTranId(String tranId) throws Exception;
	
	public Boolean updateAndCreate(BufEppPersonInvestigation obj) throws Exception;
	
	public List<BufEppPersonInvestigation> findListByTranMasterId(String tranId) throws Exception;
	
	public BufEppPersonInvestigation findBufByTranMasterIdAndPersonCode(String tranMasterId, String personCode, String tranId, String maCaNhan) throws Exception;
	
	public Boolean deleteBufPersonByTranMasterId(String tranMasterId) throws Exception;
}
