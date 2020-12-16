package com.nec.asia.nic.comp.officalNation.dao.impl;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.exolab.castor.types.DateTime;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.nec.asia.nic.comp.decisionManager.dao.DecisionManagerDao;
import com.nec.asia.nic.comp.listHandover.dao.ListHandoverDao;
import com.nec.asia.nic.comp.listHandover.domain.NicListHandover;
import com.nec.asia.nic.comp.officalNation.dao.OfficalNationDao;
import com.nec.asia.nic.comp.signerGovs.dao.SignerGovsDao;
import com.nec.asia.nic.comp.signerGovs.domain.SignerGovs;
import com.nec.asia.nic.comp.trans.domain.NicDecisionManager;
import com.nec.asia.nic.comp.trans.domain.NicOfficalNation;
import com.nec.asia.nic.comp.trans.dto.AssignmentFilterAll;
import com.nec.asia.nic.framework.PaginatedResult;
import com.nec.asia.nic.framework.admin.code.dao.PaymentDefDao;
import com.nec.asia.nic.framework.admin.code.domain.PaymentDef;
import com.nec.asia.nic.framework.admin.code.domain.PaymentDefId;
import com.nec.asia.nic.framework.dao.impl.GenericHibernateDao;

/*
 * Modification History:
 * 27 Jun 2017 (chris): add delete method for physical delete
 */
@Repository("officalNationDao")
public class OfficalNationDaoImpl extends GenericHibernateDao<NicOfficalNation, Long>
		implements OfficalNationDao {

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
	public PaginatedResult<NicOfficalNation> listOfficalNationAllBySearch(String search,
			int PageNo, int PageSize, AssignmentFilterAll assignmentFilter) throws Exception {
		DetachedCriteria criteria = DetachedCriteria
				.forClass(getPersistentClass());
		Order orders = Order.asc("id");
		// getHibernateTemplate().findByCriteria(criteria);
		if(StringUtils.isNotEmpty(assignmentFilter.getTransactionId())){
			criteria.add(Restrictions.eq("officalNationNo",
					assignmentFilter.getTransactionId()));
		}
		
		if(StringUtils.isNotEmpty(assignmentFilter.getTransactionType())){
			criteria.add(Restrictions.eq("status",
					assignmentFilter.getTransactionType()));
		}

		return this.findAllForPagination(criteria, PageNo, PageSize, orders);
	}

	public boolean createOfficalNation(NicOfficalNation officalNation) throws Exception {
		boolean status = false;
		Date date = new Date();
		officalNation.setCreateDate(date);
		try {
			// NicListHandover entity = findById(listHandover.getId());
			super.saveOrUpdate(officalNation);
			status = true;

		} catch (Exception exp) {
			logger.debug("[createOfficalNation]ex : {} ", exp);
		} finally {
			logger.debug("[createOfficalNation]status : {} ", status);
		}
		return status;
	}

	public boolean editOfficalNation(NicOfficalNation officalNationedit) throws Exception {
		boolean status = false;
		Date date = new Date();
		officalNationedit.setModifyDate(date);
		try {
			// NicListHandover entity = findById(listHandover.getId());
			super.saveOrUpdate(officalNationedit);
			status = true;

		} catch (Exception exp) {
		} finally {
			logger.debug("[editOfficalNationr]status : {} ", status);
		}
		return status;
	}

	public NicOfficalNation findByOfficalNationNo(String officalNationNo) {

		DetachedCriteria criteria = DetachedCriteria.forClass(getPersistentClass());
		criteria.add(Restrictions.eq("officalNationNo", officalNationNo));
		List<NicOfficalNation> officalNation = this.findAll(criteria);
		if(!officalNation.isEmpty()){
			return officalNation.get(0);
		}
		return null;
	}
}
