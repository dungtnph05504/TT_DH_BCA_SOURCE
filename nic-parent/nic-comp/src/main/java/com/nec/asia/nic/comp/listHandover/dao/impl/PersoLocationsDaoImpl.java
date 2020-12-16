package com.nec.asia.nic.comp.listHandover.dao.impl;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.exolab.castor.types.DateTime;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.nec.asia.nic.comp.listHandover.dao.ListHandoverDao;
import com.nec.asia.nic.comp.listHandover.dao.PersoLocationsDao;
import com.nec.asia.nic.comp.listHandover.domain.NicListHandover;
import com.nec.asia.nic.comp.listHandover.domain.NicPersoLocations;
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
@Repository("persoLocationsDao")
public class PersoLocationsDaoImpl extends
		GenericHibernateDao<NicPersoLocations, Long> implements PersoLocationsDao {

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
	public PaginatedResult<NicPersoLocations> findAllPersoLocations(int PageNo,
			int PageSize, AssignmentFilterAll assignmentFilter) throws Exception {
		
		 DetachedCriteria criteria =
		 DetachedCriteria.forClass(getPersistentClass());
		 
		 if (assignmentFilter != null) {
				if (StringUtils.isNotBlank(assignmentFilter.getTransactionId())) {
					criteria.add(Restrictions.eq("code",
							assignmentFilter.getTransactionId()));
				}
		 }
		 
		 Order orders = Order.asc("id");
		// getHibernateTemplate().findByCriteria(criteria);
		 
		return this.findAllForPagination(criteria, PageNo, PageSize, orders);
	}
	
	@Override
	public List<NicPersoLocations> findPersoLocationsById(long id) throws Exception {
		/*
		 * DetachedCriteria criteria =
		 * DetachedCriteria.forClass(PaymentDef.class);
		 * criteria.addOrder(Order.asc("id.transactionType"));
		 * criteria.addOrder(Order.asc("id.transactionSubtype"));
		 * List<PaymentDef> PaymentList = (List<PaymentDef>)
		 * getHibernateTemplate().findByCriteria(criteria);
		 */
		DetachedCriteria criteria = DetachedCriteria.forClass(NicPersoLocations.class);
		criteria.add(Restrictions.eq("idPerso", id));
		return (List<NicPersoLocations>) getHibernateTemplate().findByCriteria(
				criteria);
	}

	public boolean createPersoLocations(NicPersoLocations persoLocation)
			throws Exception {
		boolean status = false;
		Date date = new Date();
		try {
			//NicListHandover entity = findById(listHandover.getId());
			super.saveOrUpdate(persoLocation);
			status = true;
		} catch (Exception exp) {
			exp.printStackTrace();
			logger.error(exp.getMessage());
		} finally {
			logger.debug("[createPersoLocation - status : {} ", status);
		}
		return status;
	}

	@Override
	public NicPersoLocations findPersoByCode(String code, Long persoId) {
		try {
			DetachedCriteria detachedCriteria = DetachedCriteria.forClass(NicPersoLocations.class);
			if(StringUtils.isNotEmpty(code)){
				detachedCriteria.add(Restrictions.eq("code", code));				
			}
			if(persoId != null){
				detachedCriteria.add(Restrictions.eq("idPerso", persoId));			
			}
			List<NicPersoLocations> list = this.findAll(detachedCriteria);
			if(list != null && list.size() > 0)
				return list.get(0);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public NicPersoLocations findPersoByCodeAnd(String code, Long persoId) {
		try {
			DetachedCriteria detachedCriteria = DetachedCriteria.forClass(NicPersoLocations.class);
			if(StringUtils.isNotEmpty(code) && persoId != null){
				Criterion cr1 = Restrictions.eq("code", code);
				Criterion cr2 = Restrictions.eq("idPerso", persoId);
				detachedCriteria.add(Restrictions.or(cr1, cr2));
			}
			List<NicPersoLocations> list = this.findAll(detachedCriteria);
			if(list != null && list.size() > 0)
				return list.get(0);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public List<NicPersoLocations> findPersoByStatus(Integer status, String code) {
		try {
			DetachedCriteria detachedCriteria = DetachedCriteria.forClass(NicPersoLocations.class);
			if(StringUtils.isNotEmpty(code)){
				detachedCriteria.add(Restrictions.eq("code", code));				
			}
			if(status != null){
				detachedCriteria.add(Restrictions.eq("status", status));			
			}
			List<NicPersoLocations> list = this.findAll(detachedCriteria);
			if(list != null && list.size() > 0)
				return list;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
