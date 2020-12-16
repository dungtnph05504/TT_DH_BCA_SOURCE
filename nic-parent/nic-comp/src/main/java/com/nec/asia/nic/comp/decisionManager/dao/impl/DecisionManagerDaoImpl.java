package com.nec.asia.nic.comp.decisionManager.dao.impl;

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
import com.nec.asia.nic.comp.signerGovs.dao.SignerGovsDao;
import com.nec.asia.nic.comp.signerGovs.domain.SignerGovs;
import com.nec.asia.nic.comp.trans.domain.NicDecisionManager;
import com.nec.asia.nic.comp.trans.dto.AssignmentFilterAll;
import com.nec.asia.nic.framework.PaginatedResult;
import com.nec.asia.nic.framework.admin.code.dao.PaymentDefDao;
import com.nec.asia.nic.framework.admin.code.domain.PaymentDef;
import com.nec.asia.nic.framework.admin.code.domain.PaymentDefId;
import com.nec.asia.nic.framework.dao.impl.GenericHibernateDao;
import com.nec.asia.nic.utils.DateUtil;

/*
 * Modification History:
 * 27 Jun 2017 (chris): add delete method for physical delete
 */
@Repository("decisionManagerDao")
public class DecisionManagerDaoImpl extends GenericHibernateDao<NicDecisionManager, Long>
		implements DecisionManagerDao {

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
	public PaginatedResult<NicDecisionManager> listDecisionManagerAllBySearch(String search,
			int PageNo, int PageSize, AssignmentFilterAll assignmentFilter) throws Exception {
		DetachedCriteria criteria = DetachedCriteria
				.forClass(getPersistentClass());
		Order orders = Order.asc("id");
		// getHibernateTemplate().findByCriteria(criteria);
		if (assignmentFilter.getCreateDate() != null && assignmentFilter.getIssueDate() != null) {
			criteria.add(Restrictions.between("createDate",
					DateUtil.getStartOfDay(assignmentFilter.getCreateDate()),
					DateUtil.getEndOfDay(assignmentFilter.getIssueDate())));
		} else if (assignmentFilter.getCreateDate() != null) {
			criteria.add(Restrictions.gt("createDate",
					DateUtil.getStartOfDay(assignmentFilter.getCreateDate())));
		} else if (assignmentFilter.getIssueDate() != null) {
			criteria.add(Restrictions.lt("createDate",
					DateUtil.getEndOfDay(assignmentFilter.getIssueDate())));
		}
		
		if (StringUtils.isNotBlank(assignmentFilter.getTransactionId()) && assignmentFilter.getTransactionId() != null) {
			criteria.add(Restrictions.eq("decisionNumber",
					assignmentFilter.getTransactionId()));
		}
		if (StringUtils.isNotBlank(assignmentFilter.getTransactionType()) && assignmentFilter.getTransactionType() != null) {
			criteria.add(Restrictions.eq("competentAuthorities",
					assignmentFilter.getTransactionType()));
		}
		if (StringUtils.isNotBlank(assignmentFilter.getRegSiteCode()) && assignmentFilter.getRegSiteCode() != null) {
			criteria.add(Restrictions.eq("signer",
					assignmentFilter.getRegSiteCode()));
		}
		
		return this.findAllForPagination(criteria, PageNo, PageSize, orders);
	}

	public boolean createDecisionManager(NicDecisionManager decisionManager) throws Exception {
		boolean status = false;
		Date date = new Date();
		decisionManager.setCreateDate(date);
		try {
			// NicListHandover entity = findById(listHandover.getId());
			super.saveOrUpdate(decisionManager);
			status = true;

		} catch (Exception exp) {
			logger.debug("[createDecisionManager]ex : {} ", exp);
		} finally {
			logger.debug("[createDecisionManager]status : {} ", status);
		}
		return status;
	}

	public boolean editDecisionManager(NicDecisionManager decisionManageredit) throws Exception {
		boolean status = false;
		Date date = new Date();
		decisionManageredit.setModifyDate(date);
		try {
			// NicListHandover entity = findById(listHandover.getId());
			super.saveOrUpdate(decisionManageredit);
			status = true;

		} catch (Exception exp) {
		} finally {
			logger.debug("[editDecisionManager]status : {} ", status);
		}
		return status;
	}

	public NicDecisionManager findByDecisionNumber(String decisionNumber) {

		DetachedCriteria criteria = DetachedCriteria.forClass(getPersistentClass());
		criteria.add(Restrictions.eq("decisionNumber", decisionNumber));
		List<NicDecisionManager> decisionManager = this.findAll(criteria);
		if(!decisionManager.isEmpty()){
			return decisionManager.get(0);
		}
		return null;
	}
}
