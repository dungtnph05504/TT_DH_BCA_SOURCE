package com.nec.asia.nic.framework.admin.code.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.nec.asia.nic.framework.PaginatedResult;
import com.nec.asia.nic.framework.admin.code.dao.PaymentDefDao;
import com.nec.asia.nic.framework.admin.code.domain.PaymentDef;
import com.nec.asia.nic.framework.admin.code.domain.PaymentDefId;
import com.nec.asia.nic.framework.dao.impl.GenericHibernateDao;

/*
 * Modification History:
 * 27 Jun 2017 (chris): add delete method for physical delete
 */
@Repository("paymentDefDao")
public class PaymentDefDaoImpl extends
		GenericHibernateDao<PaymentDef, PaymentDefId> implements
		PaymentDefDao {

	@SuppressWarnings("unchecked")
	public List<PaymentDef> findPaymentDefByTransType(String transactionType, String transactionSubtype) {
		DetachedCriteria criteria = DetachedCriteria.forClass(PaymentDef.class);
		criteria.add(Restrictions.eq("id.transactionType", transactionType));
		criteria.add(Restrictions.eq("id.transactionSubtype", transactionSubtype));
		
		return (List<PaymentDef>) getHibernateTemplate().findByCriteria(criteria);
	}
	
	@Override
	public PaginatedResult<PaymentDef> findAllPaymentMatrixList(int PageNo, int PageSize)
			throws Exception {
		DetachedCriteria criteria = DetachedCriteria.forClass(PaymentDef.class);
		 criteria.addOrder(Order.asc("id.transactionType"));
		 criteria.addOrder(Order.asc("id.transactionSubtype"));
		 List<PaymentDef> PaymentList = (List<PaymentDef>) getHibernateTemplate().findByCriteria(criteria);
		 PaginatedResult<PaymentDef> result = findAllPaymentMatrixList(1, 10);
		 return result;
	}
	
	public String deletePaymentMatrix(PaymentDef paymentDef) throws Exception{
		String status = "";
		try {
			PaymentDef entity = findById(paymentDef.getId());
			if (entity!=null) {
				entity.setDeleteBy(paymentDef.getDeleteBy());
				entity.setDeleteWkstnId(paymentDef.getDeleteWkstnId());
				entity.setDeleteDateTime(paymentDef.getDeleteDateTime());
				entity.setDeleteFlag(paymentDef.getDeleteFlag());
				
				if (isSupportSoftDelete) {
					super.saveOrUpdate(entity);
					logger.trace("[deletePaymentMatrix] softDelete : {} ", isSupportSoftDelete);
				} else {
					super.delete(entity);
					logger.trace("[deletePaymentMatrix] physical deleted.");
				}
				status = "Successfully deleted payment definition ";
			} else {
				status = "payment definition doesn't exists";
			}
		} catch (Exception exp) {
			status = "Fail to delete payment definition ";
		} finally {
			logger.debug("[deleteReport] : {} ", status);
		}
		return status;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<PaymentDef> findPaymentDefByTransType(String transactionType,
			String transactionSubtype, String stylePay) {
		try {
			DetachedCriteria criteria = DetachedCriteria.forClass(PaymentDef.class);
			if(StringUtils.isNotEmpty(transactionType)){
				criteria.add(Restrictions.eq("id.transactionType", transactionType));				
			}
			if(StringUtils.isNotEmpty(transactionSubtype)){
				criteria.add(Restrictions.eq("id.transactionSubtype", transactionSubtype));				
			}
			if(StringUtils.isNotEmpty(stylePay)){
				criteria.add(Restrictions.eq("typePayment", stylePay));				
			}
			return (List<PaymentDef>) getHibernateTemplate().findByCriteria(criteria);
		} catch (Exception e) {
			// TODO: handle exception
		}
		return new ArrayList<PaymentDef>();
	}

	@Override
	public PaymentDef findOnlyPaymentDefByTransType(String transactionType,
			String transactionSubtype) {
		try {
			DetachedCriteria criteria = DetachedCriteria.forClass(PaymentDef.class);
			if(StringUtils.isNotEmpty(transactionType)){
				criteria.add(Restrictions.eq("id.transactionType", transactionType));				
			}
			if(StringUtils.isNotEmpty(transactionSubtype)){
				criteria.add(Restrictions.eq("id.transactionSubtype", transactionSubtype));				
			}			
			List<PaymentDef> list = (List<PaymentDef>) getHibernateTemplate().findByCriteria(criteria);
			if(list != null && list.size() > 0)
				return list.get(0);
		} catch (Exception e) {
			// TODO: handle exception
		}
		return new PaymentDef();
	}
}
