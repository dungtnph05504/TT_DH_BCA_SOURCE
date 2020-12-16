package com.nec.asia.nic.comp.trans.dao.impl;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.nec.asia.nic.comp.trans.dao.CardStockTrackingLogDao;
import com.nec.asia.nic.comp.trans.domain.CardStockTrackingLog;
import com.nec.asia.nic.framework.dao.impl.GenericHibernateDao;

/* 
 * Modification History:
 *  
 * 07 Jan 2014 (chris): init class. 
 * 11 Feb 2014 (chris): add order by logId.
 */

@Repository("cardStockTrackingLogDao")
public class CardStockTrackingLogDaoImpl 
		extends GenericHibernateDao<CardStockTrackingLog, Long> 
		implements CardStockTrackingLogDao{
	
	public List<CardStockTrackingLog> findAllByCcn(String ccn) {
		List<CardStockTrackingLog> cardStockTrackingLogList = null;
		
		DetachedCriteria detachedCriteria = DetachedCriteria.forClass(getPersistentClass());
		if (StringUtils.isNotEmpty(ccn)) {
			detachedCriteria.add(Restrictions.eq("ccn", ccn));
			detachedCriteria.addOrder(Order.asc("logId"));
			cardStockTrackingLogList = this.findAll(detachedCriteria);
		}
		return cardStockTrackingLogList;
	}
}
