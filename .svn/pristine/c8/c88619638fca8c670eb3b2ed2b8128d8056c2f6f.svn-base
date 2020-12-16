package com.nec.asia.nic.comp.trans.service;

import java.util.Date;
import java.util.List;

import com.nec.asia.nic.comp.trans.domain.BaseModelList;
import com.nec.asia.nic.comp.trans.domain.BaseModelSingle;
import com.nec.asia.nic.comp.trans.domain.NicTransactionLog;
import com.nec.asia.nic.comp.trans.service.exception.TransactionServiceException;
import com.nec.asia.nic.framework.PageRequest;
import com.nec.asia.nic.framework.PaginatedResult;
import com.nec.asia.nic.framework.service.BusinessService;

/**
 * This interface to manage all transaction log.
 * 
 * @author setia_budiyono
 *
 */

/*
 * Modification History:
 * 
 * 22 Oct 2013 (chris) : Added saveTransactionLog
 * 14 Nov 2013 (chris) : Added siteCode
 * 23 May 2014 (chris) : Add method saveTransactionLogWithNin()
 * 09 May 2016 (khang) : add method addTransactionLog
 */
public interface TransactionLogService extends BusinessService<NicTransactionLog, Long>{
	
	public static final String PARA_SCOPE_SYSTEM = "SYSTEM"; 
	public static final String PARA_NAME_SYSTEM_SITE_CODE = "SYSTEM_SITE_CODE"; 
	
	public BaseModelSingle<Long> saveTransactionLog(String transactionId, String transactionStage, String transactionStatus, String officerId, String wkstnId, String siteCode, Date startTime, Date endTime, String logInfo, String logData);

	public BaseModelSingle<Long> saveTransactionLogWithNin(String transactionId, String transactionStage, String transactionStatus,	String officerId, String wkstnId, String siteCode, Date startTime, Date endTime, String logInfo, String logData, String nin);

	public int addTransactionLog(List<NicTransactionLog> transactionLogList) throws Exception;

	public List<NicTransactionLog> findForListLog(NicTransactionLog nicTransactionLog) throws TransactionServiceException;
	
	public PaginatedResult<NicTransactionLog> findAllForPagination(PageRequest pageRequest, NicTransactionLog nicTransactionLog) throws TransactionServiceException;
	
	public BaseModelSingle<Void> saveOrUpdateTranLog(NicTransactionLog tranLog);
	
	public BaseModelSingle<Boolean> saveTransactionLog(NicTransactionLog nicTransactionLog);
}
