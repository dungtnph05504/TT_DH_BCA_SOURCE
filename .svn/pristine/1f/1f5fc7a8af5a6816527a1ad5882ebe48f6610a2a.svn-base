package com.nec.asia.nic.comp.decisionManager.service;

import java.util.List;

import com.nec.asia.nic.comp.listHandover.domain.NicListHandover;
import com.nec.asia.nic.comp.signerGovs.domain.SignerGovs;
import com.nec.asia.nic.comp.trans.domain.NicBusinessList;
import com.nec.asia.nic.comp.trans.domain.NicDecisionManager;
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

public interface BusinessListService extends BusinessService<NicBusinessList, Long>{
	
	public PaginatedResult<NicBusinessList> listBusinessListAllBySearch(String decisionNumber,int PageNo, int PageSize)throws Exception;
	
	public boolean createBusinessList(NicBusinessList businessList);
	
	public boolean editBusinessList(NicBusinessList businessListedit);
	
	public List<NicBusinessList> findListByDecisionNumber(String decisionNumber);
	
	public PaginatedResult<NicBusinessList> findListByDecisionNumber1(String decisionNumber, int pageNo, int pageSize);
	
	public NicBusinessList findBySerial (int serial, String decisionNumber);
}
