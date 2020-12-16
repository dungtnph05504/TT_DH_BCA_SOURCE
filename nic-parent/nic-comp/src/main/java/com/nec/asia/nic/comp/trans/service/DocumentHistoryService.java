/**
 * 
 */
package com.nec.asia.nic.comp.trans.service;

import java.util.List;

import com.nec.asia.nic.comp.trans.domain.BaseModelSingle;
import com.nec.asia.nic.comp.trans.domain.NicDocumentHistory;
import com.nec.asia.nic.framework.service.BusinessService;


/**
 * 
 * @author khang
 *
 */
/* 
 * Modification History:
 *  
 * 15 Jan 2016 (khang): init interface
 * 09 May 2016 (khang): add method addDocumentHistory(List<NicDocumentHistory>)
 */
public interface DocumentHistoryService extends BusinessService<NicDocumentHistory, Long>  {

	//default methods from super class
	//public NicDocumentData findById(NicDocumentDataId id);
	//public NicDocumentDataId save (NicDocumentData e);
	//public void saveOrUpdate(NicDocumentData e);
    //public NicDocumentData merge(NicDocumentData e);
	//public void delete(NicDocumentData e);
	//public void delete(NicDocumentDataId id);
	//public List<NicDocumentData> findAll ();
	

	public List<NicDocumentHistory> findAllByPassportNo(String passportNo);

	public int addDocumentHistory(List<NicDocumentHistory> documentHistoryList) throws Exception;
	
	public BaseModelSingle<Boolean> saveDocumentHistory(NicDocumentHistory nicDocumentHistory);
}
