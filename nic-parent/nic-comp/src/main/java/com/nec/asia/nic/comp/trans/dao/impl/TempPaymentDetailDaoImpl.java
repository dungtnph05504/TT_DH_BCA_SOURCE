package com.nec.asia.nic.comp.trans.dao.impl;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.nec.asia.nic.comp.trans.dao.TempPaymentDetailDao;
import com.nec.asia.nic.comp.trans.domain.TempPaymentDetail;
import com.nec.asia.nic.framework.dao.impl.GenericHibernateDao;


@Repository
public class TempPaymentDetailDaoImpl extends GenericHibernateDao<TempPaymentDetail, Long> implements TempPaymentDetailDao{

	@Override
	public List<TempPaymentDetail> findTempByTransactionId(String transactionId) {
		try {
			DetachedCriteria detachedCriteria = DetachedCriteria.forClass(TempPaymentDetail.class);
			if(StringUtils.isNotEmpty(transactionId)){
				detachedCriteria.add(Restrictions.eq("transactionId", transactionId));
			}
			List<TempPaymentDetail> list = this.findAll(detachedCriteria);
			if(list != null && list.size() > 0)
				return list;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public TempPaymentDetail findTempByTransactionIdOrType(
			String transactionId, String subType) {
		try {
			DetachedCriteria detachedCriteria = DetachedCriteria.forClass(TempPaymentDetail.class);
			if(StringUtils.isNotEmpty(transactionId)){
				detachedCriteria.add(Restrictions.eq("transactionId", transactionId));
			}
			if(StringUtils.isNotEmpty(subType)){
				detachedCriteria.add(Restrictions.eq("subtypePayment", subType));
			}
			List<TempPaymentDetail> list = this.findAll(detachedCriteria);
			if(list != null && list.size() > 0)
				return list.get(0);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}
