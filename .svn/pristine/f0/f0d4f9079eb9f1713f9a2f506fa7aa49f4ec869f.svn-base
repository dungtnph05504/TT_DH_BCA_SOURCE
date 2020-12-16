package com.nec.asia.nic.comp.trans.dao.impl;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.nec.asia.nic.comp.trans.dao.NicTransactionPaymentDetailDao;
import com.nec.asia.nic.comp.trans.domain.BaseModelList;
import com.nec.asia.nic.comp.trans.domain.BaseModelSingle;
import com.nec.asia.nic.comp.trans.domain.NicTransactionPaymentDetail;
import com.nec.asia.nic.comp.ws.log.domain.CreateMessageException;
import com.nec.asia.nic.framework.dao.impl.GenericHibernateDao;


@Repository("nicTransactionPaymentDetailDao")
public class NicTransactionPaymentDetailDaoImpl extends GenericHibernateDao<NicTransactionPaymentDetail, Long> implements NicTransactionPaymentDetailDao{

	@Override
	public BaseModelList<NicTransactionPaymentDetail> findListDetailPaymentByTransactionId(
			String paymentID, String type, String subType, Boolean status) {
		try {
			DetachedCriteria detachedCriteria = DetachedCriteria.forClass(getPersistentClass());
			if(StringUtils.isNotEmpty(paymentID)){
				detachedCriteria.add(Restrictions.eq("paymentId", paymentID));
			}
			if(StringUtils.isNotEmpty(type)){
				detachedCriteria.add(Restrictions.eq("typePayment", type));
			}
			if(StringUtils.isNotEmpty(subType)){
				detachedCriteria.add(Restrictions.eq("subTypePayment", subType));
			}
			if(status != null){
				detachedCriteria.add(Restrictions.eq("statusFee", status));
			}
			List<NicTransactionPaymentDetail> list = this.findAll(detachedCriteria);
			return new BaseModelList<NicTransactionPaymentDetail>(list, true, null);
			
		} catch (Exception e) {
			e.printStackTrace();
			return new BaseModelList<NicTransactionPaymentDetail>(null, false,  CreateMessageException.createMessageException(e) + " - findListDetailPaymentByTransactionId - " + paymentID + " - thất bại.");
		}
	}

	@Override
	public BaseModelSingle<NicTransactionPaymentDetail> findDetailPaymentByType(
			String paymentId, String type, String subType, Boolean status) {
		try {
			DetachedCriteria detachedCriteria = DetachedCriteria.forClass(getPersistentClass());			
			if(StringUtils.isNotEmpty(paymentId)){
				detachedCriteria.add(Restrictions.eq("paymentId", paymentId));
			}
			if(StringUtils.isNotEmpty(type)){
				detachedCriteria.add(Restrictions.eq("typePayment", type));
			}
			if(StringUtils.isNotEmpty(subType)){
				detachedCriteria.add(Restrictions.eq("subTypePayment", subType));
			}
			if(status != null){
				detachedCriteria.add(Restrictions.eq("statusFee", status));
			}
			List<NicTransactionPaymentDetail> list = this.findAll(detachedCriteria);
			if(list != null && list.size() > 0)
				return new BaseModelSingle<NicTransactionPaymentDetail>(list.get(0), true, null);
			return new BaseModelSingle<NicTransactionPaymentDetail>(null, true, null);
		} catch (Exception e) {
			e.printStackTrace();
			return new BaseModelSingle<NicTransactionPaymentDetail>(null, false,  CreateMessageException.createMessageException(e) + " - findDetailPaymentByType - thất bại.");
		}
	}

	@Override
	public BaseModelList<NicTransactionPaymentDetail> findListDetailPaymentList(String paymentId) {
		try {
			List<NicTransactionPaymentDetail> list = null;
			DetachedCriteria detachedCriteria = DetachedCriteria.forClass(getPersistentClass());			
			if(StringUtils.isNotEmpty(paymentId)){
				detachedCriteria.add(Restrictions.eq("paymentId", paymentId));
				list = this.findAll(detachedCriteria);
			}
			return new BaseModelList<NicTransactionPaymentDetail>(list, true, null);	
		} catch (Exception e) {
			e.printStackTrace();
			return new BaseModelList<NicTransactionPaymentDetail>(null, false,  CreateMessageException.createMessageException(e) + " - findListDetailPaymentList - " + paymentId + " - thất bại.");
		}
	}

	@Override
	public BaseModelList<NicTransactionPaymentDetail> findListDetailPaymentList(
			String paymentId, Boolean stage) {
		try {
			List<NicTransactionPaymentDetail> list = null;
			DetachedCriteria detachedCriteria = DetachedCriteria.forClass(getPersistentClass());	
			if (!(StringUtils.isEmpty(paymentId) && stage == null)) {
				if(StringUtils.isNotEmpty(paymentId)){
					detachedCriteria.add(Restrictions.eq("paymentId", paymentId));
				}
				if(stage != null){
					detachedCriteria.add(Restrictions.eq("statusFee", stage));
				}
				list = this.findAll(detachedCriteria);
			}
			return new BaseModelList<NicTransactionPaymentDetail>(list, true, null);
		} catch (Exception e) {
			e.printStackTrace();
			return new BaseModelList<NicTransactionPaymentDetail>(null, false,  CreateMessageException.createMessageException(e) + " - findListDetailPaymentList - " + paymentId + " - thất bại.");
		}
		
	}

	@Override
	public BaseModelSingle<Boolean> saveOrUpdatePaymentDetail(
			NicTransactionPaymentDetail detail) {
		try {
			BaseModelSingle<NicTransactionPaymentDetail> baseDetailPay = this.findDetailPaymentByType(detail.getPaymentId(), detail.getTypePayment(), detail.getSubTypePayment(), null);
			if (!baseDetailPay.isError() || baseDetailPay.getMessage() != null) {
				return new BaseModelSingle<Boolean>(false, baseDetailPay.isError(), baseDetailPay.getMessage());
			}
			NicTransactionPaymentDetail detailPay = baseDetailPay.getModel();
			if(detailPay != null){
				detailPay.setStatusFee(detail.isStatusFee());
				detailPay.setUpdateBy(detail.getCreateBy());
				detailPay.setUpdateDate(detail.getCreateDate());
				this.saveOrUpdate(detailPay);
			}else{
				this.saveOrUpdate(detail);
			}
			return new BaseModelSingle<Boolean>(true, true, null);
		} catch (Throwable e) {
			e.printStackTrace();
			return new BaseModelSingle<Boolean>(false, false,  CreateMessageException.createMessageException(new Exception(e)) + " - saveOrUpdatePaymentDetail - thất bại.");
		}
	}
}
