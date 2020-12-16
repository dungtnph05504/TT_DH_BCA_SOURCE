package com.nec.asia.nic.comp.trans.dao.impl;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.nec.asia.nic.comp.trans.dao.EppArchiveDao;
import com.nec.asia.nic.comp.trans.domain.EppArchive;
import com.nec.asia.nic.framework.dao.impl.GenericHibernateDao;

@Repository
public class EppArchiveDaoImpl extends GenericHibernateDao<EppArchive, Long> implements EppArchiveDao{

	@Override
	public Boolean deleteAllEppArchiveByArchiveCode(String archiveCode) {
		try {
			DetachedCriteria dCriteria = DetachedCriteria
					.forClass(getPersistentClass());
			dCriteria.add(Restrictions.eq("archiveCode", archiveCode));
			List<EppArchive> list = this.findAll(dCriteria);
			for (EppArchive eppArchive : list) {
				this.delete(eppArchive);
			}
			return true;
		} catch (Exception e) {
			throw e;
		}
	}

	@Override
	public Boolean saveListArchive(List<EppArchive> listArchive) {
		try {
			for (EppArchive eppArchive : listArchive) {
				this.save(eppArchive);
			}
		} catch (Exception e) {
			throw e;
		}
		return true;
	}

	@Override
	public List<EppArchive> findByTransactionId(String transactionId)
			throws Exception {
		List<EppArchive> list = null;
		try {
			DetachedCriteria dCriteria = DetachedCriteria
					.forClass(getPersistentClass());
			dCriteria.addOrder(Order.desc("createdTs"));
			dCriteria.add(Restrictions.eq("docCode", transactionId));
			list = this.findAll(dCriteria);
		} catch (Exception e) {
			throw e;
		}
		return list;
	}

	@Override
	public void saveOrUpdateArchive(EppArchive eppArchive) throws Exception {
		try {
			this.saveOrUpdate(eppArchive);
		} catch (Exception e) {
			throw e;
		}
	}

	@Override
	public EppArchive findByTranIdAndArchiveCode(String transactionId,
			String archiveCode) throws Exception {
		EppArchive eppArc = null;
		try {
			DetachedCriteria dCriteria = DetachedCriteria
					.forClass(getPersistentClass());
			dCriteria.add(Restrictions.eq("archiveCode", archiveCode));
			dCriteria.addOrder(Order.desc("createdTs"));
			dCriteria.add(Restrictions.eq("docCode", transactionId));
			List<EppArchive> list = this.findAll(dCriteria);
			if (list != null && list.size() > 0) {
				eppArc = list.get(0);
			}
		} catch (Exception e) {
			throw e;
		}
		return eppArc;
	}

}
