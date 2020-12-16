package com.nec.asia.nic.comp.queuesJobSchedule.dao.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang.StringUtils;
import org.exolab.castor.types.DateTime;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.nec.asia.nic.comp.decisionManager.dao.BusinessListDao;
import com.nec.asia.nic.comp.decisionManager.dao.DecisionManagerDao;
import com.nec.asia.nic.comp.listHandover.dao.ListHandoverDao;
import com.nec.asia.nic.comp.listHandover.domain.NicListHandover;
import com.nec.asia.nic.comp.queuesJobSchedule.dao.LogJobScheduleDao;
import com.nec.asia.nic.comp.signerGovs.dao.SignerGovsDao;
import com.nec.asia.nic.comp.signerGovs.domain.SignerGovs;
import com.nec.asia.nic.comp.trans.domain.LogJobSchedule;
import com.nec.asia.nic.comp.trans.domain.NicBusinessList;
import com.nec.asia.nic.comp.trans.domain.NicDecisionManager;
import com.nec.asia.nic.comp.trans.domain.NicUploadJob;
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
@Repository("logJobScheduleDao")
public class LogJobScheduleDaoImpl extends GenericHibernateDao<LogJobSchedule, Long>
		implements LogJobScheduleDao {

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
	public PaginatedResult<LogJobSchedule> listLogJobScheduleAllBySearch(Long recordId, String code,
			int PageNo, int PageSize) throws Exception {
		DetachedCriteria criteria = DetachedCriteria
				.forClass(getPersistentClass());
		Order orders = Order.asc("id");
		// getHibernateTemplate().findByCriteria(criteria);
		if(recordId != null && recordId > 0)
			criteria.add(Restrictions.eq("recordId", recordId));
		if(org.apache.commons.lang.StringUtils.isNotEmpty(code))
			criteria.add(Restrictions.eq("code", code));
		
		return this.findAllForPagination(criteria, PageNo, PageSize, orders);
	}

	public boolean createLogJobSchedule(LogJobSchedule logJobSchedule) throws Exception {
		boolean status = false;
		Date date = new Date();
		logJobSchedule.setCreateDate(date);
		try {
			// NicListHandover entity = findById(listHandover.getId());
			super.saveOrUpdate(logJobSchedule);
			status = true;

		} catch (Exception exp) {
		} finally {
			logger.debug("[createBusinessList]status : {} ", status);
		}
		return status;
	}


	public List<LogJobSchedule> findByCode(String code) {

		DetachedCriteria criteria = DetachedCriteria.forClass(getPersistentClass());
		if(org.apache.commons.lang.StringUtils.isNotEmpty(code))
			criteria.add(Restrictions.eq("code", code));
		else
			return null;
		
		List<LogJobSchedule> logJobSchedule = new ArrayList<LogJobSchedule>();
		logJobSchedule = this.findAll(criteria);
		if(logJobSchedule != null && logJobSchedule.size() > 0)
			return logJobSchedule;
		else
			return null;
	}
	
	public List<LogJobSchedule> findByRecordId(Long recordId) {

		DetachedCriteria criteria = DetachedCriteria.forClass(getPersistentClass());
		if(recordId != null && recordId > 0)
			criteria.add(Restrictions.eq("recordId", recordId));
		else
			return null;
		
		List<LogJobSchedule> logJobSchedule = new ArrayList<LogJobSchedule>();
		logJobSchedule = this.findAll(criteria);
		if(logJobSchedule != null && logJobSchedule.size() > 0)
			return logJobSchedule;
		else
			return null;
	}
	
	public PaginatedResult<LogJobSchedule> findListByCriteria(int pageNo, int pageSize, AssignmentFilterAll assignmentFilter)
	{
		DetachedCriteria dCriteria = DetachedCriteria
				.forClass(getPersistentClass());
		//dCriteria.createAlias("nicTransaction", "nicTransaction");
		Order orders = null;

		if (assignmentFilter != null) {
			if (StringUtils.isNotBlank(assignmentFilter.getTransactionId())) {
				dCriteria.add(Restrictions.eq("code",
						assignmentFilter.getTransactionId()));
			}
			if (StringUtils.isNotBlank(assignmentFilter.getRegSiteCode())) {
				dCriteria.add(Restrictions.eq("typeLogJob",
						assignmentFilter.getRegSiteCode()));
			}
			
			/*if (assignmentFilter.getCreateDate() != null) {
				dCriteria.add(Restrictions.ge("createDate",
						assignmentFilter.getCreateDate()));

				Date maxDate = new Date(assignmentFilter.getCreateDate()
						.getTime() + TimeUnit.DAYS.toMillis(1));
				dCriteria.add(Restrictions.lt("createDate",
						maxDate));
			}*/
			if (assignmentFilter.getCreateDate() != null) {
				dCriteria.add(Restrictions.gt("createDate",
						assignmentFilter.getCreateDate()));
			}
			if (assignmentFilter.getIssueDate() != null) {
				dCriteria.add(Restrictions.lt("createDate",
						assignmentFilter.getIssueDate()));
			}
			
			if (StringUtils.isNotBlank(assignmentFilter.getPassportType())) {
				dCriteria.add(Restrictions.eq("status",
						assignmentFilter.getPassportType()));
			}

			if (StringUtils.isNotBlank(assignmentFilter.getTransactionType())) {
				dCriteria.add(Restrictions.eq("typeTransaction",
						assignmentFilter.getTransactionType()));
			}

		}
		orders = Order.desc("createDate");
		if (orders == null) {
			return this.findAllForPagination(dCriteria, pageNo, pageSize);
		} else {
			return this.findAllForPagination(dCriteria, pageNo, pageSize,
					orders);
		}
	}

	@Override
	public PaginatedResult<LogJobSchedule> findByCode1(String code, int pageNo,
			int pageSize) throws Exception {
		DetachedCriteria criteria = DetachedCriteria.forClass(getPersistentClass());
		if(org.apache.commons.lang.StringUtils.isNotEmpty(code))
			criteria.add(Restrictions.eq("code", code));
		else
			return null;
		
		//List<LogJobSchedule> logJobSchedule = new ArrayList<LogJobSchedule>();
		return  this.findAllForPagination(criteria, pageNo, pageSize);
	}
}
