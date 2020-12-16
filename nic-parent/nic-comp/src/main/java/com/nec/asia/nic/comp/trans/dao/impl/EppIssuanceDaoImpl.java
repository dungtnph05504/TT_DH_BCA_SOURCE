package com.nec.asia.nic.comp.trans.dao.impl;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.nec.asia.nic.comp.trans.dao.EppIssuanceDao;
import com.nec.asia.nic.comp.trans.domain.EppIssuance;
import com.nec.asia.nic.framework.dao.impl.GenericHibernateDao;

@Repository
public class EppIssuanceDaoImpl extends GenericHibernateDao<EppIssuance, Long>
		implements EppIssuanceDao {

	@Override
	public void saveOrUpdateEppIssuance(EppIssuance eppIssuance) throws Exception {
		try {
			this.saveOrUpdate(eppIssuance);
		} catch (Exception e) {
			throw e;
		}
	}

	@Override
	public List<EppIssuance> findAllByTranId(String transactionId)
			throws Exception {
		List<EppIssuance> list = null;
		try {
			DetachedCriteria creCriteria = DetachedCriteria.forClass(getPersistentClass());
			creCriteria.addOrder(Order.desc("id"));
			creCriteria.add(Restrictions.eq("documentCode", transactionId));
			list = this.findAll(creCriteria);
		} catch (Exception e) {
			throw e;
		}
		return list;
	}

	@Override
	public Long saveEppIssuance(EppIssuance eppIssuance) throws Exception {
		try {
			return save(eppIssuance);
		} catch (Exception e) {
			throw e;
		}
	}

}
