package com.nec.asia.nic.comp.trans.dao;

import java.util.List;

import com.nec.asia.nic.comp.trans.domain.NicCardReconRpt;
import com.nec.asia.nic.comp.trans.domain.NicCardReconRptId;
import com.nec.asia.nic.framework.dao.GenericDao;

/* 
 * Modification History:
 *  
 * 11 Dec 2013 (chris): init class. 
 */
public interface NicCardReconRptDao extends GenericDao<NicCardReconRpt, NicCardReconRptId> {
	
	//default methods from super class
	//public NicCardReconRpt findById(NicCardReconRptId id);
	//public NicCardReconRptId save (NicCardReconRpt e);
	//public void saveOrUpdate(NicCardReconRpt e);
    //public NicCardReconRpt merge(NicCardReconRpt e);
	//public void delete(NicCardReconRpt e);
	//public void delete(NicCardReconRptId id);
	//public List<NicCardReconRpt> findAll ();
	
	//other methods
	public List<NicCardReconRpt> findAllReconReport(String siteCode, String reportCreateDate);
}
