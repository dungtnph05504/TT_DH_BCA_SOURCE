/**
 * 
 */
package com.nec.asia.nic.comp.trans.service;

import java.util.List;

import com.nec.asia.nic.comp.trans.domain.NicSearchHitResult;
import com.nec.asia.nic.comp.trans.dto.NicHitTransaction;
import com.nec.asia.nic.framework.dao.DaoException;
import com.nec.asia.nic.framework.service.BusinessService;

/**
 * @author sailaja_chinapothula
 * @Company: NEC Asia Pacific Ltd
 * @Since: Jul 15, 2013
 */
public interface NicSearchHitResultService extends BusinessService<NicSearchHitResult, Long>  {

	public List<NicSearchHitResult> findHitPositions(String hitTransactionId, Long searchResultId) throws Exception; 
	
	public List<NicSearchHitResult> findHitPositionsHit(String hitTransactionId, Long hitResultId) throws Exception;
	
	public List<String> findListHitSpecial(String transactionId, String typeHit, int pageNumber, int pageSize) throws Exception; 

	public List<NicHitTransaction> findHitBySource(String tranid, String source, String regSite, long searchId) throws Exception;
	
	public NicSearchHitResult findSearchHitResultByTranIdAndSearchId(String tranId, Long searchId) throws Exception;
}
