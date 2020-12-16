package com.nec.asia.nic.comp.trans.dao;

import java.sql.ResultSet;
import java.util.List;

import com.nec.asia.nic.comp.trans.domain.BufEppPersonInvestigation;
import com.nec.asia.nic.comp.trans.domain.NicSearchHitResult;
import com.nec.asia.nic.comp.trans.dto.NicHitTransaction;
import com.nec.asia.nic.comp.trans.dto.SearchResultInA08Dto;
import com.nec.asia.nic.framework.dao.DaoException;
import com.nec.asia.nic.framework.dao.GenericDao;

/*
 * Modification History:
 * 
 * 08 Oct 2013 (Sailaja): Modified updateDataOnTrueHit,updateDataOnFalseHit  methods to update the remarks.
 * 
 */

/**
 * @author setia_budiyono
 *
 */
public interface NicSearchHitResultDao extends GenericDao<NicSearchHitResult, Long> {
	public List<NicSearchHitResult> findHitResultBySearchId (Long searchResultId) throws DaoException;

	//added by Sailaja
	public List<NicSearchHitResult> findHitPositions(String hitTransactionId, Long searchResultId) throws DaoException;
	
	public List<NicSearchHitResult> findHitPositionsHit(String hitTransactionId, Long hitResultId) throws DaoException;
	
	/* To Update the NIC_SEARCH_HIT_RESULT table for true hit */
	public void updateDataOnTrueHit(String hitTransactionId, Long searchResultId, String userId, String remarks) throws DaoException;
	
	/* To Update the NIC_SEARCH_HIT_RESULT table for false hit */
	public void updateDataOnFalseHit(String hitTransactionId, Long searchResultId, String userId, String remarks) throws DaoException;
	
	public List<NicSearchHitResult> sortHitResultBySearchId (Long searchResultId) throws DaoException;
	
	/* Count the true hit, false hit */
	public List<String> getCount(Long searchResultId, String transactionId);

	public List<Boolean> getCount(String transactionId, Long searchResultId);
	 
	public void updateDataOnTrueHit(String hitTransactionId, List<Long> searchResultIds, String userId, String remarks) throws DaoException;
	 
	public void updateDataOnFalseHit(String hitTransactionId, List<Long> searchResultIds, String userId, String remarks) throws DaoException;
	
	public List<Long> getHitTransaction(String tranid, int pageNumber, int pageSize)  throws Exception;
	
	public List<String> findListHitSpecial(String transactionId, String typeHit, int pageNumber, int pageSize)
			throws Exception;
	
	public List<NicHitTransaction> findHitBySource(String tranid, String source, String regSite, long searchId) throws Exception;
	
	public List<BufEppPersonInvestigation> getResultA08(SearchResultInA08Dto request) throws Exception;
	
	public List<NicSearchHitResult> findHitResultsByTranIdAndSearchId(String tranId, Long searchId) throws Exception;

	public List<BufEppPersonInvestigation> getResultA08Check(
			SearchResultInA08Dto request)throws Exception;
}
