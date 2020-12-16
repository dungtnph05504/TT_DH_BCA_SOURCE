package com.nec.asia.nic.comp.decisionManager.dao.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.exolab.castor.types.DateTime;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.nec.asia.nic.comp.decisionManager.dao.BusinessListDao;
import com.nec.asia.nic.comp.decisionManager.dao.DecisionManagerDao;
import com.nec.asia.nic.comp.listHandover.dao.ListHandoverDao;
import com.nec.asia.nic.comp.listHandover.domain.NicListHandover;
import com.nec.asia.nic.comp.signerGovs.dao.SignerGovsDao;
import com.nec.asia.nic.comp.signerGovs.domain.SignerGovs;
import com.nec.asia.nic.comp.trans.domain.NicBusinessList;
import com.nec.asia.nic.comp.trans.domain.NicDecisionManager;
import com.nec.asia.nic.framework.PaginatedResult;
import com.nec.asia.nic.framework.admin.code.dao.PaymentDefDao;
import com.nec.asia.nic.framework.admin.code.domain.PaymentDef;
import com.nec.asia.nic.framework.admin.code.domain.PaymentDefId;
import com.nec.asia.nic.framework.dao.impl.GenericHibernateDao;

/*
 * Modification History:
 * 27 Jun 2017 (chris): add delete method for physical delete
 */
@Repository("businessListDao")
public class BusinessListDaoImpl extends GenericHibernateDao<NicBusinessList, Long>
		implements BusinessListDao {

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
	public PaginatedResult<NicBusinessList> listBusinessListAllBySearch(String search,
			int PageNo, int PageSize) throws Exception {
		DetachedCriteria criteria = DetachedCriteria
				.forClass(getPersistentClass());
		Order orders = Order.asc("id");
		// getHibernateTemplate().findByCriteria(criteria);

		return this.findAllForPagination(criteria, PageNo, PageSize, orders);
	}

	public boolean createBusinessList(NicBusinessList businessList) throws Exception {
		boolean status = false;
		Date date = new Date();
		businessList.setCreateDate(date);
		try {
			// NicListHandover entity = findById(listHandover.getId());
			super.saveOrUpdate(businessList);
			status = true;

		} catch (Exception exp) {
		} finally {
			logger.debug("[createBusinessList]status : {} ", status);
		}
		return status;
	}

	public boolean editBusinessList(NicBusinessList businessListedit) throws Exception {
		boolean status = false;
		Date date = new Date();
		businessListedit.setModifyDate(date);
		try {
			// NicListHandover entity = findById(listHandover.getId());
			super.saveOrUpdate(businessListedit);
			status = true;

		} catch (Exception exp) {
		} finally {
			logger.debug("[editBusinessList]status : {} ", status);
		}
		return status;
	}

	public List<NicBusinessList> findListByDecisionNumber(String decisionNumber) {

		DetachedCriteria criteria = DetachedCriteria.forClass(getPersistentClass());
		criteria.add(Restrictions.eq("decisionNumber", decisionNumber));
		List<NicBusinessList> businessList = new ArrayList<NicBusinessList>();
		businessList = this.findAll(criteria);
		if(businessList != null)
			return businessList;
		else
			return null;
	}
	
	public NicBusinessList findBySerial(int serial, String decisionNumber) {

		DetachedCriteria criteria = DetachedCriteria.forClass(getPersistentClass());
		criteria.add(Restrictions.eq("serial", serial));
		criteria.add(Restrictions.eq("decisionNumber", decisionNumber));
		List<NicBusinessList> businessList = this.findAll(criteria);
		if(businessList != null && businessList.size() > 0)
			return businessList.get(0);
		else
			return null;
	}

	@Override
	public PaginatedResult<NicBusinessList> findListByDecisionNumber1(
			String decisionNumber, int pageNo, int pageSize) throws Exception {
		DetachedCriteria criteria = DetachedCriteria.forClass(getPersistentClass());
		criteria.add(Restrictions.eq("decisionNumber", decisionNumber));
		List<NicBusinessList> businessList = new ArrayList<NicBusinessList>();
		//businessList = this.findAll(criteria);
		try {
			return this.findAllForPagination(criteria, pageNo, pageSize);
		} catch (Exception e) {
			e.printStackTrace();
			return new PaginatedResult<>(0, 1, businessList);
		}
	}
}
