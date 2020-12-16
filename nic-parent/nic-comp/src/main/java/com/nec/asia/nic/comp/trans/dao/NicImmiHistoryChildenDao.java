package com.nec.asia.nic.comp.trans.dao;

import java.util.List;

import com.nec.asia.nic.comp.immihistory.domain.ImmiHistory;
import com.nec.asia.nic.comp.immihistory.domain.ImmiHistoryChilden;
import com.nec.asia.nic.comp.trans.domain.EppBlacklist;
import com.nec.asia.nic.comp.trans.dto.CountPassport;
import com.nec.asia.nic.comp.trans.dto.NicUploadJobDto;
import com.nec.asia.nic.framework.dao.GenericDao;

/* 
 * Modification History:
 *  
 * 11 Dec 2013 (chris): init class. 
 */
public interface NicImmiHistoryChildenDao extends GenericDao<ImmiHistoryChilden, Long> {
	
	public List<ImmiHistoryChilden> findByImmiId (Long id);
	
	
}
