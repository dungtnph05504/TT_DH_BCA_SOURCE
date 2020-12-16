package com.nec.asia.nic.comp.trans.dao;

import java.util.List;

import com.nec.asia.nic.comp.trans.domain.NicAfisData;
import com.nec.asia.nic.framework.dao.DaoException;
import com.nec.asia.nic.framework.dao.GenericDao;

/* 
 * Modification History:
 *  
 * 22 Sep 2014 (chris): add method findReferenceAfisId
 */
public interface NicAfisDataDao extends GenericDao<NicAfisData, String>{

	
	public String findReferenceAfisId (String transactionId);
	
	public String getNextAfisKeyNo(String prefix);
	
	public List<String> findOtherAfisHistoryByTransIdList(List<String> tranIdList) throws DaoException;
}
