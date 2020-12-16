package com.nec.asia.nic.comp.trans.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nec.asia.nic.comp.trans.dao.NicMainDao;
import com.nec.asia.nic.comp.trans.dao.NicTransactionDao;
import com.nec.asia.nic.comp.trans.domain.NicTransaction;
import com.nec.asia.nic.comp.trans.service.NicStatisticsService;
import com.nec.asia.nic.framework.PageRequest;
import com.nec.asia.nic.framework.PaginatedResult;
import com.nec.asia.nic.framework.service.impl.DefaultBusinessServiceImpl;

/*
 * Modification History:
 * 
 * 11 May 2016 (tue): add methods:  getCollectedTransByEstDate, getPendingTransByEstDate, getAllTransByEstDate, findAllByStatusAndEstDate
 * 
*/

@Service("nicStatisticsService")
public class NicStatisticsServiceImpl
		extends
		DefaultBusinessServiceImpl<NicTransaction, String, NicTransactionDao>
		implements NicStatisticsService {
	
	
	@Autowired
	private NicMainDao nicMainDao = null;
	
	/** must define the non-argument constructor because we use CGLib proxy */
	public NicStatisticsServiceImpl() {
	}

	@Autowired
	public NicStatisticsServiceImpl(NicTransactionDao dao) {
		this.dao = dao;
	}

	@Override
	public List<Object[]> getNicTransactionStatsBySite(
			String regSiteCode) {
		return dao.getNicTransactionStatsBySite(regSiteCode);
	}

	@Override
	public List<Object[]> getNicTransactionStatsBySiteAndDate(
			String regSiteCode, Date dateFrom, Date dateTo) {
		return dao.getNicTransactionStatsBySiteAndDate(regSiteCode, dateFrom, dateTo);
	}

	@Override
	public List<Object[]> getNicTransactionStatusStats(
			String regSiteCode, Date dateFrom, Date dateTo) {
		return dao.getNicTransactionStatusStats(regSiteCode, dateFrom, dateTo);
	}

	@Override
	public List<Object[]> getNicTransactionStatusStatsByDate(
			String regSiteCode, Date dateFrom, Date dateTo) {
		return dao.getNicTransactionStatusStatsByDate(regSiteCode, dateFrom, dateTo);
	}

	@Override
	public List<Object[]> getNicMainStatsByStatus(
			String cardStatus) {
		return dao.getNicMainStatsByStatus(cardStatus);
	}

	@Override
	public List<Object[]> getTxnAgeTypeGenderSiteStats(Date dateFrom,
			Date dateTo) {
		return dao.getTxnAgeTypeGenderSiteStats(dateFrom, dateTo);
	}

	@Override
	public List<Object[]> getTxnDaySiteTotal(String transctionStatus,
			String transactionType, String transactionSubtype, Date dateFrom,
			Date dateTo) {
		return dao.getTxnDaySiteTotal(transctionStatus, transactionType, transactionSubtype, dateFrom, dateTo);
	}
	
	@Override
	public List<Object[]> getCollectedTransByEstDate(String fromDate, int noOfDays) {
		// TODO Auto-generated method stub
		return dao.getCollectedTransByEstDate(fromDate, noOfDays);
	}

	@Override
	public List<Object[]> getPendingTransByEstDate(String fromDate, int noOfDays) {
		// TODO Auto-generated method stub
		return dao.getPendingTransByEstDate(fromDate, noOfDays);
	}

	@Override
	public List<Object[]> getAllTransByEstDate(String fromDate, int noOfDays) {
		// TODO Auto-generated method stub
		return dao.getAllTransByEstDate(fromDate, noOfDays);
	}

	@Override
	public PaginatedResult<NicTransaction> findAllByStatusAndEstDate(String[] status, String estDate, String transType, PageRequest pageRequest) {
		// TODO Auto-generated method stub
		return dao.getAllByStatusAndEstDate(status, estDate, transType, pageRequest);
	}

	@Override
	public List<NicTransaction> getAllByStatusAndEstDate(String[] status, String estDate, String transType) {
		// TODO Auto-generated method stub
		return dao.getAllByStatusAndEstDate(status, estDate, transType);
	}

}