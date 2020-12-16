package com.nec.asia.nic.comp.trans.dao.impl;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.nec.asia.nic.comp.trans.dao.NicTransactionReprintDao;
import com.nec.asia.nic.comp.trans.domain.NicTransactionReprint;
import com.nec.asia.nic.comp.trans.domain.NicTransactionReprintId;
import com.nec.asia.nic.framework.dao.DaoException;
import com.nec.asia.nic.framework.dao.impl.GenericHibernateDao;

/**
 * @author khang
 * @Company: NEC Asia Pacific Ltd
 * @Since: 31-May-2016
 */

/*
 * Modification History:
 */
@Repository("transactionReprintDao")
public class NicTransactionReprintDaoImpl extends GenericHibernateDao<NicTransactionReprint, NicTransactionReprintId> implements NicTransactionReprintDao {

	@Override
	public List<NicTransactionReprint> findByTransactionId(String transactionId) throws DaoException {
		DetachedCriteria detachedCriteria = DetachedCriteria.forClass(getPersistentClass());
		if (StringUtils.isNotBlank(transactionId)) {
			detachedCriteria.add(Restrictions.eq("id.transactionId", transactionId));
			
			List<NicTransactionReprint> resultList = this.findAll(detachedCriteria);
			return resultList;
		}
		return null;
	}
	
	@Override
	public NicTransactionReprint findByRefArn(String refArn) throws DaoException {
		NicTransactionReprint nicTransactionReprint = null;
		DetachedCriteria detachedCriteria = DetachedCriteria.forClass(getPersistentClass());
		if (StringUtils.isNotBlank(refArn)) {
			detachedCriteria.add(Restrictions.eq("id.refArn", refArn));
			
			List<NicTransactionReprint> resultList = this.findAll(detachedCriteria);
			if (CollectionUtils.isNotEmpty(resultList)) {
				nicTransactionReprint = resultList.get(0);
			}
		}
		return nicTransactionReprint;
	}
	
	
	@Override
	public NicTransactionReprint getLatestReprintByTransactionId(String transactionId) throws DaoException {
		NicTransactionReprint nicTransactionReprint = null;
		DetachedCriteria detachedCriteria = DetachedCriteria.forClass(getPersistentClass());
		if (StringUtils.isNotBlank(transactionId)) {
			detachedCriteria.add(Restrictions.eq("id.transactionId", transactionId));
			Order order = Order.desc("reprintCount"); 
			detachedCriteria.addOrder(order);
			List<NicTransactionReprint> resultList = this.findAll(detachedCriteria);
			if (CollectionUtils.isNotEmpty(resultList)) {
				nicTransactionReprint = resultList.get(0);
			}
		}
		return nicTransactionReprint;
	}
}
