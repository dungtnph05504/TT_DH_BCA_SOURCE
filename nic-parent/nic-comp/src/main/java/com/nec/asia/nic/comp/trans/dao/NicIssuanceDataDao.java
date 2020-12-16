package com.nec.asia.nic.comp.trans.dao;

import java.util.Date;
import java.util.List;

import org.hibernate.criterion.Order;

import com.nec.asia.nic.comp.trans.domain.NicIssuanceData;
import com.nec.asia.nic.comp.trans.domain.NicIssuanceDataId;
import com.nec.asia.nic.comp.trans.domain.NicTransaction;
import com.nec.asia.nic.comp.trans.dto.CardStatusRptDTO;
import com.nec.asia.nic.comp.trans.dto.RICBatchCardInfoDto;
import com.nec.asia.nic.comp.trans.dto.RICTxnRptDto;
import com.nec.asia.nic.framework.PaginatedResult;
import com.nec.asia.nic.framework.dao.DaoException;
import com.nec.asia.nic.framework.dao.GenericDao;

/**
 * 
 * @author setia_budiyono
 *
 */
/* 
 * Modification History:
 *  
 * 29 Aug 2013 (chris): modify updateTransactionByPackageId(), to save dispatch id in issuance data.
 * 09 Sep 2013 (chris): add method findByTransacionId
 *  1 Nov 2013 (Swapna):added getLatestNicIssuanceData method.
 * 13 Nov 2013 (chris): add method findByPersoRefId
 * 15 Nov 2013 (chris): add method findByNin
 *10 Dec 2013 (Peddi Swapna): Added new methods to support the NIC Statistics modules during the code merge.
 * 16 Jan 2014 (priya): add method getRicBatchCardInfoRptRecordList(),getCardCollectedStatusRptRecordList(),getCardRejectedRptRecordList.
 * 10 Feb 2014 (priya): add method getCardExpiredStatusRptRecordList(),getCardReActRptRecordList(),getCardDeActRptRecordList(),getRicLostNfoundRptRecordList.
 * 06 Jun 2013 (chris): rename findByTransacionId to findByTransactionId
 */
public interface NicIssuanceDataDao extends GenericDao<NicIssuanceData, NicIssuanceDataId> {
	
		
	public List<NicIssuanceData> findByPeriod (String siteCode, Date start, Date end) throws DaoException;
	
	public List<NicIssuanceData> findDataByCCn(String ccn) throws DaoException;
	
	public List<NicIssuanceData> findByPackageId (String packageId) throws DaoException;
	
	public List<NicIssuanceData> findByTransactionId (String transactionId) throws DaoException;//09 Oct 2013
	
	public void updateStatusByPackageId (List<String> packageIdList, String status) throws DaoException;
	
	public NicTransaction getTransactionIssuanceDatas(NicTransaction transObj);

	public List<String> findByCCn(String ccn);
	
    public void updateStatusByTransactionId(String transactionId, String status, String officerId, String workstationId) throws DaoException; 
    
	public void updateTransactionByPackageId(String dispatchId, List<String> packageIdList, String status, String officerId, String workstationId) throws DaoException;

	public void updateTransactionByCcn(String ccn, String transactionStatus, String officerId, String workstationId) throws DaoException;

	public PaginatedResult<NicIssuanceData> findAllForPaginationByFilter(NicIssuanceData nicIssuanceData, int pageNo, int pageSize, boolean ignoreCase, boolean enableLike, Order order) throws DaoException;
	
	/**
	 * @param hitTransactionId
	 * @return
	 */
	public NicIssuanceData getLatestNicIssuanceData(String hitTransactionId) throws DaoException;

	public List<NicIssuanceData> findByPersoRefId(String persoRefId) throws DaoException;
	
	public List<NicIssuanceData> findByNin(String nin) throws DaoException;

/**
	 * @param transactionId
	 * @param status
	 * @throws DaoException
	 */
	void updateStatusByTransactionId(String transactionId, String status)
			throws DaoException;

	public PaginatedResult<NicIssuanceData> findAllForPagination(NicIssuanceData issuanceData, Date regDatefrom, Date regDateto, Date collectDatefrom, Date collectDateto, int pageNo, int pageSize, boolean ignoreCase, boolean enableLike, Order order) throws DaoException;

	public List<RICBatchCardInfoDto> getRicBatchCardInfoRptRecordList(RICBatchCardInfoDto ricBatchCardInfoRptCriteria) throws DaoException;
	public List<CardStatusRptDTO> getCardCollectedStatusRptRecordList(CardStatusRptDTO cardColSrchCriteria) throws DaoException;
	public List<CardStatusRptDTO> getCardRejectedRptRecordList(CardStatusRptDTO cardColSrchCriteria) throws DaoException;
	public List<CardStatusRptDTO> getCardExpiredStatusRptRecordList(CardStatusRptDTO cardExpiredSrchCriteria) throws DaoException;
	public List<CardStatusRptDTO> getCardReActRptRecordList(CardStatusRptDTO cardReActSrchCriteria) throws DaoException;
	public List<CardStatusRptDTO> getCardDeActRptRecordList(CardStatusRptDTO cardDeActSrchCriteria) throws DaoException;
	public List<RICTxnRptDto> getRicLostNfoundRptRecordList(RICTxnRptDto lostNfoundSrchCriteria) throws DaoException;
	
}
