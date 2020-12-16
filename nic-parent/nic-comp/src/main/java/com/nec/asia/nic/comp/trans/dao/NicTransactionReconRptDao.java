package com.nec.asia.nic.comp.trans.dao;

import java.util.List;

import com.nec.asia.nic.comp.trans.domain.NicTransactionReconRpt;
import com.nec.asia.nic.comp.trans.domain.NicTransactionReconRptId;
import com.nec.asia.nic.framework.dao.GenericDao;

/* 
 * Modification History:
 *  
 * 11 Dec 2013 (chris): init class. 
 * 15 May 2014 (chris): add method getNicTransactionsByDate()
 */
public interface NicTransactionReconRptDao extends GenericDao<NicTransactionReconRpt, NicTransactionReconRptId> {
	
	//default methods from super class
	//public NicTransactionReconRpt findById(NicTransactionReconRptId id);
	//public NicTransactionReconRptId save (NicTransactionReconRpt e);
	//public void saveOrUpdate(NicTransactionReconRpt e);
    //public NicTransactionReconRpt merge(NicTransactionReconRpt e);
	//public void delete(NicTransactionReconRpt e);
	//public void delete(NicTransactionReconRptId id);
	//public List<NicTransactionReconRpt> findAll ();
	
	//other methods
	public List<Object> getNicTransactionReconRptData(String regSiteCode, String issSiteCode, String applicationDate, String collectionDate);

	public List<NicTransactionReconRpt> findAllReconReport(String regSiteCode, String issSiteCode, String applicationDate, String collectionDate);

	public List<Object> getNicTransactionsByDate(String inputDate);
}
