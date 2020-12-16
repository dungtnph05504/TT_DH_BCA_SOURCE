package com.nec.asia.nic.comp.trans.dao;

import java.util.Date;
import java.util.List;

import com.nec.asia.nic.comp.trans.domain.NicRejectionData;
import com.nec.asia.nic.framework.dao.DaoException;
import com.nec.asia.nic.framework.dao.DefaultDao;
import com.nec.asia.nic.framework.dao.GenericDao;

/**
 * @author sailaja_chinapothula
 * @Company: NEC Asia Pacific Ltd
 * @Since: Aug 7, 2013
 */

/* 
 * Modification History:
 *  
 * 12 Aug 2013 (chris): add method findByPeriod. 
 * 26 Aug 2013 (chris): add method updateDataReceivedByTransactionIdList
 * 02 Dec 2013 (Sailaja): Added cancelTransaction method to cancel the transaction.
 * 18 Dec 2013 (chris): add method findByTransactionId
 */


public interface NicRejectionDataDao 
	//extends DefaultDao<NicRejectionData, String>
	extends GenericDao<NicRejectionData, Long> 
{
	//default methods from super class
	//public NicRejectionData findById(String transactionId);
	//public String save (NicRejectionData e);
	//public void saveOrUpdate(NicRejectionData e);
    //public NicRejectionData merge(NicRejectionData e);
	//public void delete(NicRejectionData e);
	//public void delete(String transactionId);
	//public List<NicRejectionData> findAll ();
	
	/* To Reject the card -- Update NIC_REJECTION_DATA table */
	public void updateRejectReasonRemarks(long rejectJobId, String rejectedReason, String rejectedRemarks, String userId, String wkstnId,String transactionId, String investigationType) throws Exception;
	
	public List<NicRejectionData> findByPeriod(String siteCode, Date start, Date end) throws DaoException;
	
	public void updateDataReceivedByTransactionIdList(List<String> transactionIdList, String officerId, String wkstnId) throws DaoException;

	/* Cancel Transaction */
	public void cancelTransaction(Long jobId, String cancelReason,
			String userId, String wkstnId, String transactionId)throws DaoException;
    
	/* To display the rejected info for supervisor module */
	public NicRejectionData findRejectInfo(long jobId) throws DaoException;
	
	public NicRejectionData findByTransactionId(String transactionId) throws DaoException;
	
	public List<NicRejectionData> findAllRejectionDataForSync(String siteCode) throws DaoException;
	public void updateSyncStatus(List<String> transactionIdList) throws DaoException;
}
