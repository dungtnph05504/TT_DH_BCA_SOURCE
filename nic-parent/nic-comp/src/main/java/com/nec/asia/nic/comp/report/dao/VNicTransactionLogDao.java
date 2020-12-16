/**
 * 
 */
package com.nec.asia.nic.comp.report.dao;

import java.util.List;


import com.nec.asia.nic.comp.trans.domain.VNicTxnLog;
import com.nec.asia.nic.comp.trans.dto.CardStatusRptDTO;
import com.nec.asia.nic.framework.dao.DaoException;
import com.nec.asia.nic.framework.dao.GenericDao;

/**
 * @author Prasad_Karnati
 *
 */
public interface VNicTransactionLogDao extends GenericDao<VNicTxnLog,String>{
	
	public List<CardStatusRptDTO> getCardReActRptRecordList(CardStatusRptDTO cardReActSrchCriteria) throws DaoException;
	public List<CardStatusRptDTO> getCardDeActRptRecordList(CardStatusRptDTO cardDeActSrchCriteria) throws DaoException;
	

}
