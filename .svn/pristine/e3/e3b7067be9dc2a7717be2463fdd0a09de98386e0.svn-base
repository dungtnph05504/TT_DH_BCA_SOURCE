package com.nec.asia.nic.comp.trans.dao;

import java.util.List;

import com.nec.asia.nic.comp.trans.domain.BaseModelSingle;
import com.nec.asia.nic.comp.trans.domain.NicDocumentHistory;
import com.nec.asia.nic.framework.dao.GenericDao;



/**
 * @author khang
 *
 */
/* 
 * Modification History:
 *  
 * 09 May 2016 (khang): add method addDocumentHistory
 */
public interface DocumentHistoryDao extends GenericDao<NicDocumentHistory, Long> {
	
	public List<NicDocumentHistory> findAllByPassportNo(String passportNo);

	public int addDocumentHistory(List<NicDocumentHistory> documentHistoryList) throws Exception;
	
	public BaseModelSingle<Boolean> saveDocumentHistory(NicDocumentHistory nicDocumentHistory);
}
