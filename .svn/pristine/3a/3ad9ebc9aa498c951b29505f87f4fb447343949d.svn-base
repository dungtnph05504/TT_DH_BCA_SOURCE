package com.nec.asia.nic.comp.trans.dao;

import java.util.List;

import com.nec.asia.nic.comp.trans.domain.EppBlacklist;
import com.nec.asia.nic.comp.trans.domain.EppIdentification;
import com.nec.asia.nic.framework.dao.GenericDao;

/* 
 * Modification History:
 *  
 * 11 Dec 2013 (chris): init class. 
 */
public interface NicIdentificationDao extends GenericDao<EppIdentification, Long> {
	
	public List<EppIdentification> findByNin (String nin);

	public void cleanOldData(List<EppIdentification> hitByCriterion) throws Exception;

	public void saveIdentification(EppIdentification eppIdentification)throws Exception;

	public List<EppIdentification> findByTranId(String transactionId)throws Exception;
	
}
