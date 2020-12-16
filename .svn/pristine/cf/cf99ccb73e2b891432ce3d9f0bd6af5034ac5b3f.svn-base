package com.nec.asia.nic.comp.officalVisa.dao.impl;

import java.util.Date;
import java.util.List;

import org.exolab.castor.types.DateTime;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.nec.asia.nic.comp.decisionManager.dao.DecisionManagerDao;
import com.nec.asia.nic.comp.listHandover.dao.ListHandoverDao;
import com.nec.asia.nic.comp.listHandover.domain.NicListHandover;
import com.nec.asia.nic.comp.officalVisa.dao.OfficalVisaDao;
import com.nec.asia.nic.comp.signerGovs.dao.SignerGovsDao;
import com.nec.asia.nic.comp.signerGovs.domain.SignerGovs;
import com.nec.asia.nic.comp.trans.domain.NicDecisionManager;
import com.nec.asia.nic.comp.trans.domain.NicOfficalVisa;
import com.nec.asia.nic.framework.PaginatedResult;
import com.nec.asia.nic.framework.admin.code.dao.PaymentDefDao;
import com.nec.asia.nic.framework.admin.code.domain.PaymentDef;
import com.nec.asia.nic.framework.admin.code.domain.PaymentDefId;
import com.nec.asia.nic.framework.dao.impl.GenericHibernateDao;

/*
 * Modification History:
 * 27 Jun 2017 (chris): add delete method for physical delete
 */
@Repository("officalVisaDao")
public class OfficalVisaDaoImpl extends GenericHibernateDao<NicOfficalVisa, Long>
		implements OfficalVisaDao {

	/*
	 * @SuppressWarnings("unchecked") public List<PaymentDef>
	 * findPaymentDefByTransType(String transactionType, String
	 * transactionSubtype) { DetachedCriteria criteria =
	 * DetachedCriteria.forClass(PaymentDef.class);
	 * criteria.add(Restrictions.eq("id.transactionType", transactionType));
	 * criteria.add(Restrictions.eq("id.transactionSubtype",
	 * transactionSubtype));
	 * 
	 * return (List<PaymentDef>)
	 * getHibernateTemplate().findByCriteria(criteria); }
	 */

	@Override
	public PaginatedResult<NicOfficalVisa> listOfficalVisaAllBySearch(String search,
			int PageNo, int PageSize) throws Exception {
		DetachedCriteria criteria = DetachedCriteria
				.forClass(getPersistentClass());
		Order orders = Order.asc("id");
		// getHibernateTemplate().findByCriteria(criteria);

		return this.findAllForPagination(criteria, PageNo, PageSize, orders);
	}

	public boolean createOfficalVisa(NicOfficalVisa officalVisa) throws Exception {
		boolean status = false;
		Date date = new Date();
		officalVisa.setCreateDate(date);
		try {
			// NicListHandover entity = findById(listHandover.getId());
			super.saveOrUpdate(officalVisa);
			status = true;

		} catch (Exception exp) {
			logger.debug("[createOfficalVisa]ex : {} ", exp);
		} finally {
			logger.debug("[createOfficalVisa]status : {} ", status);
		}
		return status;
	}

	public boolean editOfficalVisa(NicOfficalVisa officalVisaedit) throws Exception {
		boolean status = false;
		Date date = new Date();
		officalVisaedit.setLastModifiedTime(date);
		try {
			// NicListHandover entity = findById(listHandover.getId());
			super.saveOrUpdate(officalVisaedit);
			status = true;

		} catch (Exception exp) {
		} finally {
			logger.debug("[editOfficalVisa]status : {} ", status);
		}
		return status;
	}

	public NicOfficalVisa findByOfficalVisa(String countryCode, String passportType) {

		DetachedCriteria criteria = DetachedCriteria.forClass(getPersistentClass());
		criteria.add(Restrictions.eq("countryCode", countryCode));
		criteria.add(Restrictions.eq("passportType", passportType));
		List<NicOfficalVisa> officalVisa = this.findAll(criteria);
		if(!officalVisa.isEmpty()){
			return officalVisa.get(0);
		}
		return null;
	}
}
