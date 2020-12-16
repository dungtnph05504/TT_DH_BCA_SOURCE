package com.nec.asia.nic.comp.workflowProcess.dao.impl;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.exolab.castor.types.DateTime;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.nec.asia.nic.comp.listHandover.dao.ListHandoverDao;
import com.nec.asia.nic.comp.listHandover.domain.NicListHandover;
import com.nec.asia.nic.comp.trans.domain.NicWorkflowProcess;
import com.nec.asia.nic.comp.trans.dto.AssignmentFilterAll;
import com.nec.asia.nic.comp.workflowProcess.dao.WorkflowProcessDao;
import com.nec.asia.nic.framework.PaginatedResult;
import com.nec.asia.nic.framework.admin.code.dao.PaymentDefDao;
import com.nec.asia.nic.framework.admin.code.domain.PaymentDef;
import com.nec.asia.nic.framework.admin.code.domain.PaymentDefId;
import com.nec.asia.nic.framework.dao.impl.GenericHibernateDao;

/*
 * Modification History:
 * 27 Jun 2017 (chris): add delete method for physical delete
 */
@Repository("workflowProcessDao")
public class WorkflowProcessDaoImpl extends
		GenericHibernateDao<NicWorkflowProcess, Long> implements WorkflowProcessDao {

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
	public PaginatedResult<NicWorkflowProcess> findAllWorkflowProcess(int PageNo,
			int PageSize, AssignmentFilterAll assignmentFilter) throws Exception {
		
		 DetachedCriteria criteria =
		 DetachedCriteria.forClass(getPersistentClass());
		 
		 if (assignmentFilter != null) {
				/*
				if (StringUtils.isNotBlank(assignmentFilter.getTransactionType())) {
					criteria.add(Restrictions.eq("id",
							assignmentFilter.getTransactionType()));
				}*/
		 }
		 
		 Order orders = Order.asc("createDate");
		// getHibernateTemplate().findByCriteria(criteria);
		 
		return this.findAllForPagination(criteria, PageNo, PageSize, orders);
	}
	
	@Override
	public NicWorkflowProcess findWorkflowProcessById(Long Id) throws Exception {
		/*
		 * DetachedCriteria criteria =
		 * DetachedCriteria.forClass(PaymentDef.class);
		 * criteria.addOrder(Order.asc("id.transactionType"));
		 * criteria.addOrder(Order.asc("id.transactionSubtype"));
		 * List<PaymentDef> PaymentList = (List<PaymentDef>)
		 * getHibernateTemplate().findByCriteria(criteria);
		 */
		NicWorkflowProcess result = null;// findAllPaymentMatrixList(1,
														// 10);
		return result;
	}

	public boolean createWorkflowProcess(NicWorkflowProcess workflowProcess)
			throws Exception {
		boolean status = false;
		Date date = new Date();
		try {
			//NicListHandover entity = findById(listHandover.getId());
			super.saveOrUpdate(workflowProcess);
			status = true;

		} catch (Exception exp) {
		} finally {
			logger.debug("[createWorkflowProcess]status : {} ", status);
		}
		return status;
	}
	
	public boolean editWorkflowProcess(NicWorkflowProcess workflowProcess)
			throws Exception {
		boolean status = false;
		Date date = new Date();
		workflowProcess.setUpdateDate(date);
		try {
			// NicListHandover entity = findById(listHandover.getId());
			super.saveOrUpdate(workflowProcess);
			status = true;

		} catch (Exception exp) {
		} finally {
			logger.debug("[editWorkflowProcess]status : {} ", status);
		}
		return status;
	}
	
	@Override
	public List<NicWorkflowProcess> findWorkflowProcessByCriteria(String workflowS) throws Exception {
		/*
		 * DetachedCriteria criteria =
		 * DetachedCriteria.forClass(PaymentDef.class);
		 * criteria.addOrder(Order.asc("id.transactionType"));
		 * criteria.addOrder(Order.asc("id.transactionSubtype"));
		 * List<PaymentDef> PaymentList = (List<PaymentDef>)
		 * getHibernateTemplate().findByCriteria(criteria);
		 */
	
		DetachedCriteria criteria = DetachedCriteria
				.forClass(getPersistentClass());

		if (StringUtils.isNotEmpty(workflowS)) {
			criteria.add(Restrictions.eq("workflowStart", workflowS));
		}
		logger.info("Get data WorkflowProcess DAO Impl");
		List<NicWorkflowProcess> lst = this.findAll(criteria);
		
		logger.info("Get data WorkflowProcess DAO Impl: lst = " + lst.size() );
		if(lst != null && lst.size() > 0){
			return lst;
		}
		return null;
	}
	
	@Override
	public List<NicWorkflowProcess> findWorkflowProcessByCriteriaEnd(String workflowE) throws Exception {
		/*
		 * DetachedCriteria criteria =
		 * DetachedCriteria.forClass(PaymentDef.class);
		 * criteria.addOrder(Order.asc("id.transactionType"));
		 * criteria.addOrder(Order.asc("id.transactionSubtype"));
		 * List<PaymentDef> PaymentList = (List<PaymentDef>)
		 * getHibernateTemplate().findByCriteria(criteria);
		 */
	
		DetachedCriteria criteria = DetachedCriteria
				.forClass(getPersistentClass());

		if (StringUtils.isNotEmpty(workflowE)) {
			criteria.add(Restrictions.eq("workflowEnd", workflowE));
		}
		logger.info("Get data WorkflowProcess DAO Impl");
		List<NicWorkflowProcess> lst = this.findAll(criteria);
		
		logger.info("Get data WorkflowProcess DAO Impl: lst = " + lst.size() );
		if(lst != null && lst.size() > 0){
			return lst;
		}
		return null;
	}
	
	@Override
	public NicWorkflowProcess findWorkflowProcessByCriteria(String workflowS, String result) throws Exception {
		/*
		 * DetachedCriteria criteria =
		 * DetachedCriteria.forClass(PaymentDef.class);
		 * criteria.addOrder(Order.asc("id.transactionType"));
		 * criteria.addOrder(Order.asc("id.transactionSubtype"));
		 * List<PaymentDef> PaymentList = (List<PaymentDef>)
		 * getHibernateTemplate().findByCriteria(criteria);
		 */
		NicWorkflowProcess res = null;// findAllPaymentMatrixList(1,
														// 10);
		return res;
	}
}
