package com.nec.asia.nic.comp.trans.dao.impl;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.nec.asia.nic.comp.trans.dao.NicIdentificationDao;
import com.nec.asia.nic.comp.trans.domain.EppIdentification;
import com.nec.asia.nic.comp.ws.log.domain.CreateMessageException;
import com.nec.asia.nic.framework.dao.impl.GenericHibernateDao;

/* 
 * Modification History:
 *  
 * 11 Dec 2013 (chris): init class. 
 */
@Repository("nicIdentificationDao")
public class NicIdentificationDaoImpl extends
		GenericHibernateDao<EppIdentification, Long> implements
		NicIdentificationDao {

	@Transactional(rollbackFor = java.lang.Throwable.class, propagation = Propagation.SUPPORTS)
	@Override
	public List<EppIdentification> findByNin(String nin) {
		DetachedCriteria detachedCriteria = DetachedCriteria
				.forClass(getPersistentClass());
		if (StringUtils.isNotBlank(nin)) {
			detachedCriteria.add(Restrictions.eq("idNumber", nin));
		}
		List<EppIdentification> resultList = this.findAll(detachedCriteria);
		return resultList;
	}

	@Override
	public void cleanOldData(List<EppIdentification> hitByCriterion)
			throws Exception {
		try {
			for (EppIdentification eppIdentification : hitByCriterion) {
				this.delete(eppIdentification);
			}
		} catch (Exception e) {
			throw new Exception(
					CreateMessageException.createMessageException(e)
							+ " - cleanOldData thất bại.");
		}
	}

	@Override
	public void saveIdentification(EppIdentification eppIdentification)
			throws Exception {
		try {
			this.save(eppIdentification);
		} catch (Exception e) {
			throw new Exception(
					CreateMessageException.createMessageException(e)
							+ " - saveIdentification thất bại.");
		}
	}

	@Override
	public List<EppIdentification> findByTranId(String transactionId)
			throws Exception {
		List<EppIdentification> list = null;
		try {
			DetachedCriteria detachedCriteria = DetachedCriteria
					.forClass(getPersistentClass());
			if (StringUtils.isNotBlank(transactionId)) {
				detachedCriteria.add(Restrictions.eq("transactionId", transactionId));
			}
			list = this.findAll(detachedCriteria);
		} catch (Exception e) {
			throw new Exception(
					CreateMessageException.createMessageException(e)
							+ " - findByTranId thất bại.");
		}
		return list;
	}
}
