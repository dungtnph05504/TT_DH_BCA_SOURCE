/**
 * 
 */
package com.nec.asia.nic.comp.trans.service;

import java.util.List;

import com.nec.asia.nic.comp.trans.domain.NicRejectionData;
import com.nec.asia.nic.comp.trans.service.exception.RejectionDataServiceException;
import com.nec.asia.nic.framework.service.BusinessService;


/**
 * 
 * @author chris_wong
 *
 */
/* 
 * Modification History:
 *  
 * 23 May 2016 (chris): init interface
 */
public interface RejectionDataService extends BusinessService<NicRejectionData, Long>  {

	//default methods from super class
	//public NicRejectionData findById(NicRejectionDataId id);
	//public NicRejectionDataId save (NicRejectionData e);
	//public void saveOrUpdate(NicRejectionData e);
    //public NicRejectionData merge(NicRejectionData e);
	//public void delete(NicRejectionData e);
	//public void delete(NicRejectionDataId id);
	//public List<NicRejectionData> findAll();
	
	public List<NicRejectionData> findAllRejectionDataForSync(String siteCode) throws RejectionDataServiceException;
	
	public void updateSyncStatus(List<String> transactionIdList) throws RejectionDataServiceException;
}
