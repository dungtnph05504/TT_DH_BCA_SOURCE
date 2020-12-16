package com.nec.asia.nic.comp.trans.dao;

import java.util.List;

import com.nec.asia.nic.comp.trans.domain.CardStockTrackingLog;
import com.nec.asia.nic.framework.dao.GenericDao;

/* 
 * Modification History:
 *  
 * 07 Jan 2013 (chris): init class. 
 */
public interface CardStockTrackingLogDao extends GenericDao<CardStockTrackingLog, Long> {
	//default methods from super class
	//public CardStockTrackingLog findById(Long id);
	//public Long save (CardStockTrackingLog e);
	//public void saveOrUpdate(CardStockTrackingLog e);
    //public CardStockTrackingLog merge(CardStockTrackingLog e);
	//public void delete(CardStockTrackingLog e);
	//public void delete(Long id);
	//public List<CardStockTrackingLog> findAll ();
	
	public List<CardStockTrackingLog> findAllByCcn(String ccn);
}
