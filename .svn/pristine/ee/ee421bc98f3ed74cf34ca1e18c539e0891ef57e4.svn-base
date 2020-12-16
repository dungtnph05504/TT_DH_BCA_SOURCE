package com.nec.asia.nic.comp.trans.service;

import java.util.Date;
import java.util.List;

import com.nec.asia.nic.comp.trans.domain.NicTransaction;
import com.nec.asia.nic.framework.PageRequest;
import com.nec.asia.nic.framework.PaginatedResult;
import com.nec.asia.nic.framework.service.BusinessService;

/**
 * 
 * @author franco_conte
 *
 */

/*
 * Modification History:
 * 
 * 11 May 2016 (tue): add methods:  getCollectedTransByEstDate, getPendingTransByEstDate, getAllTransByEstDate, findAllByStatusAndEstDate
 * 
*/

public interface NicStatisticsService extends BusinessService<NicTransaction, String>{
	

	public List<Object[]> getNicTransactionStatsBySite(String regSiteCode);

	public List<Object[]> getNicTransactionStatsBySiteAndDate(String regSiteCode, Date dateFrom, Date dateTo );
	
	public List<Object[]> getNicTransactionStatusStats(String regSiteCode, Date dateFrom, Date dateTo);
	
	public List<Object[]> getNicTransactionStatusStatsByDate(String regSiteCode, Date dateFrom, Date dateTo);

	public List<Object[]> getNicMainStatsByStatus(String cardStatus);

	public List<Object[]> getTxnAgeTypeGenderSiteStats(Date dateFrom, Date dateTo);

	public List<Object[]> getTxnDaySiteTotal(String transctionStatus, String transactionType, String transactionSubtype, Date dateFrom, Date dateTo);

	public List<Object[]> getCollectedTransByEstDate(String fromDate, int noOfDays);
	public List<Object[]> getPendingTransByEstDate(String fromDate, int noOfDays);
	public List<Object[]> getAllTransByEstDate(String fromDate, int noOfDays);
	public PaginatedResult<NicTransaction> findAllByStatusAndEstDate(String[] status, String estDate, String transType, PageRequest pageRequest);
	public List<NicTransaction> getAllByStatusAndEstDate(String[] status, String estDate, String transType);
	
}
