package com.nec.asia.nic.comp.bufPersonInvestigation.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.nec.asia.nic.comp.bufPersonInvestigation.dao.BufPersonInvestigationDao;
import com.nec.asia.nic.comp.trans.domain.BufEppPersonInvestigation;
import com.nec.asia.nic.framework.dao.impl.GenericHibernateDao;

/*
 * Modification History:
 * 27 Jun 2017 (chris): add delete method for physical delete
 */
@Repository("bufPersonInvestigationDao")
public class BufPersonInvestigationDaoImpl extends
		GenericHibernateDao<BufEppPersonInvestigation, Long> implements
		BufPersonInvestigationDao {

	public BufEppPersonInvestigation findByTranId(String tranId) {

		BufEppPersonInvestigation nicBufEpp = null;

		try {
			DetachedCriteria detachedCriteria = DetachedCriteria
					.forClass(getPersistentClass());
			// detachedCriteria.getExecutableCriteria(this.hibernateTemplate.getSessionFactory().openSession());
			if (StringUtils.isNotEmpty(tranId)) {
				detachedCriteria.add(Restrictions.eq("transactionId", tranId));
				List<BufEppPersonInvestigation> nicBufEppList = this
						.findAll(detachedCriteria);
				if (CollectionUtils.isNotEmpty(nicBufEppList)) {
					nicBufEpp = nicBufEppList.get(0);
				}
			}
		} catch (Exception e) {
			throw e;
		}

		return nicBufEpp;
	}

	public List<BufEppPersonInvestigation> findListByTranId(String tranId) {

		try {
			DetachedCriteria criteria = DetachedCriteria
					.forClass(getPersistentClass());
			if (StringUtils.isNotEmpty(tranId))
				criteria.add(Restrictions.eq("transactionId", tranId));

			List<BufEppPersonInvestigation> signers = this.findAll(criteria);
			if (signers != null && signers.size() > 0) {
				return signers;
			}
		} catch (Exception e) {
			throw e;
		}

		return new ArrayList<BufEppPersonInvestigation>();
	}

	public List<BufEppPersonInvestigation> findListByMasterTranId(String tranId) {

		DetachedCriteria criteria = DetachedCriteria
				.forClass(getPersistentClass());
		if (StringUtils.isNotEmpty(tranId))
			criteria.add(Restrictions.eq("transactionMasterId", tranId));

		List<BufEppPersonInvestigation> signers = this.findAll(criteria);
		if (!signers.isEmpty()) {
			return signers;
		}
		return null;
	}

	@Override
	public Boolean updateAndCreate(BufEppPersonInvestigation obj) {

		try {
			super.saveOrUpdate(obj);
			return true;
		} catch (Exception ex) {

		}

		return false;
	}

	@Override
	public List<BufEppPersonInvestigation> findListByTranMasterId(String tranId)
			throws Exception {
		try {
			DetachedCriteria criteria = DetachedCriteria
					.forClass(getPersistentClass());
			if (StringUtils.isNotEmpty(tranId))
				criteria.add(Restrictions.eq("transactionMasterId", tranId));
			List<BufEppPersonInvestigation> signers = this.findAll(criteria);
			if (signers != null && signers.size() > 0) {
				return signers;
			}
		} catch (Exception e) {
			throw e;
		}

		return new ArrayList<BufEppPersonInvestigation>();
	}

	@Override
	public List<BufEppPersonInvestigation> findListByTranMasterIdAndPersonCode(
			String tranMasterId, String personCode, String tranId,
			String maCaNhan) throws Exception {
		try {
			DetachedCriteria criteria = DetachedCriteria
					.forClass(getPersistentClass());
			if (StringUtils.isNotBlank(tranMasterId))
				criteria.add(Restrictions.eq("transactionMasterId",
						tranMasterId));
			if (StringUtils.isNotBlank(tranId))
				criteria.add(Restrictions.eq("transactionId", tranId));
			if (StringUtils.isNotBlank(personCode))
				criteria.add(Restrictions.eq("personCode", personCode));
			if (StringUtils.isNotBlank(maCaNhan))
				criteria.add(Restrictions.eq("maCaNhan", maCaNhan));
			List<BufEppPersonInvestigation> signers = this.findAll(criteria);
			if (signers != null && signers.size() > 0) {
				return signers;
			}
		} catch (Exception e) {
			throw e;
		}

		return null;
	}

	@Override
	public Boolean deleteBufPersonByTranMasterId(String tranMasterId)
			throws Exception {
		Boolean check = false;
		try {
			DetachedCriteria criteria = DetachedCriteria
					.forClass(getPersistentClass());
			if (StringUtils.isNotBlank(tranMasterId))
				criteria.add(Restrictions.eq("transactionMasterId",
						tranMasterId));
			criteria.add(Restrictions.eq("type", "CPD"));
			List<BufEppPersonInvestigation> signers = this.findAll(criteria);
			if (signers != null && signers.size() > 0) {
				for (BufEppPersonInvestigation bufEppPersonInvestigation : signers) {
					this.delete(bufEppPersonInvestigation);
				}
			}
			check = true;
		} catch (Exception e) {
			throw e;
		}
		return check;
	}

}
