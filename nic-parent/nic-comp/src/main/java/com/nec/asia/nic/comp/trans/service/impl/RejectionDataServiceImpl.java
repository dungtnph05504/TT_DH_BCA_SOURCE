/**
 * 
 */
package com.nec.asia.nic.comp.trans.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nec.asia.nic.comp.trans.dao.NicRejectionDataDao;
import com.nec.asia.nic.comp.trans.domain.NicRejectionData;
import com.nec.asia.nic.comp.trans.service.RejectionDataService;
import com.nec.asia.nic.comp.trans.service.exception.RejectionDataServiceException;
import com.nec.asia.nic.framework.dao.DaoException;
import com.nec.asia.nic.framework.service.impl.DefaultBusinessServiceImpl;

/**
 * 
 * @author chris_wong
 *
 */
/*
 * Modification History:
 * 
 * 23 May 2017 (chris): init service implementations
 */

@Service("rejectionDataService")
public class RejectionDataServiceImpl extends
		DefaultBusinessServiceImpl<NicRejectionData, Long, NicRejectionDataDao> implements RejectionDataService {
	
	/** must define the non-argument constructor because we use CGLib proxy */
	public RejectionDataServiceImpl() {
	}

	@Autowired
	public RejectionDataServiceImpl(NicRejectionDataDao dao) {
		this.dao = dao;
	}
	
	public List<NicRejectionData> findAllRejectionDataForSync(String siteCode) throws RejectionDataServiceException {
		try {
			return this.dao.findAllRejectionDataForSync(siteCode);
		} catch (DaoException e) {
			throw new RejectionDataServiceException(e);
		}
	}
	
	public void updateSyncStatus(List<String> transactionIdList) throws RejectionDataServiceException {
		try {
			this.dao.updateSyncStatus(transactionIdList);
		} catch (DaoException e) {
			throw new RejectionDataServiceException(e);
		}
	}
}
