package com.nec.asia.nic.comp.queuesJobSchedule.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.nec.asia.nic.comp.queuesJobSchedule.dao.QueuesJobScheduleDao;
import com.nec.asia.nic.comp.trans.domain.QueuesJobSchedule;
import com.nec.asia.nic.comp.trans.dto.AssignmentFilterAll;
import com.nec.asia.nic.framework.PaginatedResult;
import com.nec.asia.nic.framework.dao.impl.GenericHibernateDao;
import com.nec.asia.nic.utils.DateUtil;

/*
 * Modification History:
 * 27 Jun 2017 (chris): add delete method for physical delete
 */
@Repository("queuesJobScheduleDao")
public class QueuesJobScheduleDaoImpl extends GenericHibernateDao<QueuesJobSchedule, Long>
		implements QueuesJobScheduleDao {

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
	public PaginatedResult<QueuesJobSchedule> listQueuesJobScheduleAllBySearch(Long recordId, String code,
			int PageNo, int PageSize, AssignmentFilterAll assignmentFilter) throws Exception {
		DetachedCriteria criteria = DetachedCriteria
				.forClass(getPersistentClass());
		Order orders = Order.asc("id");
		// getHibernateTemplate().findByCriteria(criteria);
		if(assignmentFilter != null){
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
		}
		
		if(recordId != null && recordId > 0)
			criteria.add(Restrictions.eq("recordId", recordId));
		if(org.apache.commons.lang.StringUtils.isNotEmpty(code))
			criteria.add(Restrictions.eq("code", code));
		
		return this.findAllForPagination(criteria, PageNo, PageSize, orders);
	}

	public boolean createQueuesJobSchedule(QueuesJobSchedule queuesJobSchedule) throws Exception {
		boolean status = false;
		try {
			// NicListHandover entity = findById(listHandover.getId());
			super.saveOrUpdate(queuesJobSchedule);
			status = true;

		} catch (Exception exp) {
			logger.debug("[createQueuesJobSchedule]ex : {} ", exp);
		} finally {
			logger.debug("[createQueuesJobSchedule]status : {} ", status);
		}
		return status;
	}

	public boolean deleteQueuesJobSchedule(QueuesJobSchedule queuesJobSchedule) throws Exception {
		boolean status = false;
		try {
			// NicListHandover entity = findById(listHandover.getId());
			super.delete(queuesJobSchedule);
			status = true;

		} catch (Exception exp) {
			logger.debug("[deleteQueuesJobSchedule]ex : {} ", exp);
		} finally {
			logger.debug("[deleteQueuesJobSchedule]status : {} ", status);
		}
		return status;
	}

	public QueuesJobSchedule findByCode(String code) {

		DetachedCriteria criteria = DetachedCriteria.forClass(getPersistentClass());
		if(org.apache.commons.lang.StringUtils.isNotEmpty(code))
			criteria.add(Restrictions.eq("code", code));
		else
			return null;
		
		List<QueuesJobSchedule> logJobSchedule = new ArrayList<QueuesJobSchedule>();
		logJobSchedule = this.findAll(criteria);
		if(logJobSchedule != null && logJobSchedule.size() > 0)
			return logJobSchedule.get(0);
		else
			return null;
	}
	
	public QueuesJobSchedule findByRecordId(Long recordId) {

		DetachedCriteria criteria = DetachedCriteria.forClass(getPersistentClass());
		if(recordId != null && recordId > 0)
			criteria.add(Restrictions.eq("recordId", recordId));
		else
			return null;
		
		List<QueuesJobSchedule> logJobSchedule = new ArrayList<QueuesJobSchedule>();
		logJobSchedule = this.findAll(criteria);
		if(logJobSchedule != null && logJobSchedule.size() > 0)
			return logJobSchedule.get(0);
		else
			return null;
	}
	
	@Override
	public List<QueuesJobSchedule> getListInQueuesByType() throws Exception {
		DetachedCriteria criteria = DetachedCriteria
				.forClass(getPersistentClass());
		Order orders = Order.asc("id");
		
		Criterion creite_NEW = Restrictions.eq("status", QueuesJobSchedule.STATUS_JOB_NEW);
		Criterion creite_ERROR = Restrictions.eq("status", QueuesJobSchedule.STATUS_JOB_ERROR);
		Criterion isLds = Restrictions.ne("typeTransaction", QueuesJobSchedule.TYPE_TRANSACTION_LDS);
		
		criteria.add(Restrictions.and(Restrictions.or(creite_NEW, creite_ERROR), isLds));
		
		return this.findAll(criteria);
	}
	
	@Override
	public List<QueuesJobSchedule> getListInQueuesForLds() throws Exception {
		DetachedCriteria criteria = DetachedCriteria
				.forClass(getPersistentClass());
		Order orders = Order.asc("id");
		
		Criterion creite_NEW = Restrictions.eq("status", QueuesJobSchedule.STATUS_JOB_NEW);
		Criterion creite_ERROR = Restrictions.eq("status", QueuesJobSchedule.STATUS_JOB_ERROR);
		Criterion isLds = Restrictions.eq("typeTransaction", QueuesJobSchedule.TYPE_TRANSACTION_LDS);
		
		criteria.add(Restrictions.and(Restrictions.or(creite_NEW, creite_ERROR), isLds));
		
		return this.findAll(criteria);
	}
}
