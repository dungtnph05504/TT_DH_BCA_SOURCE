package com.nec.asia.nic.comp.trans.dao;

import java.util.List;

import org.hibernate.criterion.Order;

import com.nec.asia.nic.comp.trans.domain.BaseModelList;
import com.nec.asia.nic.comp.trans.domain.BaseModelSingle;
import com.nec.asia.nic.comp.trans.domain.NicTransactionLog;
import com.nec.asia.nic.comp.trans.dto.ExceptionHandlngRptDto;
import com.nec.asia.nic.framework.PaginatedResult;
import com.nec.asia.nic.framework.dao.GenericDao;

/*
 * Modification History
 * 
 * 30 Sept 2013 :(Sailaja): Added the editRemarksData method to edit the remarks.
 * 06 Dec 2013 : (Sailaja): Added the findAllLogInfo to display in Perso tab.
 * 10 Dec 2013 : (Sailaja): Added the findAllPersoRemarks method to show additional Information
 * 07 Jan 2013 : (chris) : rename function - editRemarksData() to findRemarksByTxnIdNStatus()
 * 10 Feb 2014 : (priys) : Added method getExceptionHandlingRptRecordList
 * 21 May 2014 : (Peddi Swapna): Added getLatestErrorMessage method.
 * 09 May 2016 : (khang) : add method addTransactionLog(List<NicTransactionLog>)
 */

public interface NicTransactionLogDao extends GenericDao<NicTransactionLog, Long> {
	
	//default methods from super class
	//public NicTransactionLog findById(Long transactionLogId);
	//public Long save (NicTransactionLog e);
	//public void saveOrUpdate(NicTransactionLog e);
    //public NicTransactionLog merge(NicTransactionLog e);
	//public void delete(NicTransactionLog e);
	//public void delete(Long transactionLogId);
	//public List<NicTransactionLog> findAll ();
	
	public BaseModelList<NicTransactionLog> findAllByRefIdAndTransStateList(String refId, List<String> transactionStates);
	
	/* To update the remarks */
	public String updateRemarks(String refId, String remarksData);
	
	public NicTransactionLog getNicTransactionLog(String refId, String transactionStage,String transactionStatus, String userId, String wkstnId) throws Exception;
	
	/* To Reject the card -- Insert NIC_TRANSACTION_LOG table */
	public void insertRejectReasonRemarks(long rejectJobId, String rejectedReason, String rejectedRemarks, 
			String userId, String wkstnId,String transactionId) throws Exception;

	/* To edit the remarks in the remarks textarea */
	//public String editRemarksData(String refId, String transactionStage,String transactionStatus) throws Exception;
	public String findRemarksByTxnIdNStatus(String refId, String transactionStage,String transactionStatus) throws Exception;
	
	public List<NicTransactionLog> findAllLogInfo(String refId, String transactionStage,String transactionStatus) throws Exception;

	/* To show Additional Perso Information */
	public String findAllPersoRemarks(String transactionId,
			String transactionStage, String transactionStatus) throws Exception;
    
	/* To show fps issuance enquiry */
	public PaginatedResult<NicTransactionLog> findAllForPagination(int currentPage,
			int pageSize, String siteCode, String officerId, String transactionStage, String transactionStatus) throws Exception;

	public List<NicTransactionLog> findAllByNinWithExcludedTransactions(
			String nin, List<String> excludedTransactionIdList);
	public PaginatedResult<NicTransactionLog> getExceptionHandlingRptRecordList(ExceptionHandlngRptDto excepHandlingRptSrchCriteria,int pageNo, int pageSize)throws Exception;

	public NicTransactionLog getLatestErrorMessage(String transactionId) throws Exception;

	public NicTransactionLog getLatestTransactionLog(String transactionId) throws Exception;

    public NicTransactionLog getLatestTransactionLog(String transactionId, String transactionStage) throws Exception;
    
    public NicTransactionLog getLatestTransactionLog(String transactionId, String transactionStage, String transactionStatus) throws Exception;

	public int addTransactionLog(List<NicTransactionLog> transactionLogList) throws Exception;

	public PaginatedResult<NicTransactionLog> findAllForPagination(NicTransactionLog nicTransactionLog, int pageNo, int pageSize, boolean ignoreCase, boolean enableLike, Order order);
	public List<NicTransactionLog> findAllLogByStatus(String refId,String transactionStatus) throws Exception;
	public List<NicTransactionLog> findForListLog(NicTransactionLog nicTransactionLog, int pageNo, int pageSize, boolean ignoreCase, boolean enableLike, Order order);
	public BaseModelSingle<Boolean> saveTransactionLog(NicTransactionLog nicTransactionLog);
}
