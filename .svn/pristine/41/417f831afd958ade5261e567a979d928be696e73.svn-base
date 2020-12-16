package com.nec.asia.nic.comp.trans.dao.impl;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.nec.asia.nic.comp.trans.dao.FeeRecieptPaymentDao;
import com.nec.asia.nic.comp.trans.dao.RecieptManagerDao;
import com.nec.asia.nic.comp.trans.domain.BaseModelList;
import com.nec.asia.nic.comp.trans.domain.BaseModelSingle;
import com.nec.asia.nic.comp.trans.domain.EppRecieptManager;
import com.nec.asia.nic.comp.trans.domain.FeeRecieptPayment;
import com.nec.asia.nic.comp.ws.log.domain.CreateMessageException;
import com.nec.asia.nic.framework.dao.impl.GenericHibernateDao;

@Repository("feeRecieptPaymentDao")
public class FeeRecieptPaymentDaoImpl extends GenericHibernateDao<FeeRecieptPayment, Long> implements FeeRecieptPaymentDao{

	@Override
	public BaseModelList<FeeRecieptPayment> findAllFeeRecieptPayment(String recieptNo) {
		try {
			List<FeeRecieptPayment> list = null;
			DetachedCriteria criteria = DetachedCriteria.forClass(FeeRecieptPayment.class);
			if(StringUtils.isNotEmpty(recieptNo)){
				criteria.add(Restrictions.eq("recieptNo", recieptNo));
				list = this.findAll(criteria);
			}
			return new BaseModelList<FeeRecieptPayment>(list, true, null);
		} catch (Exception e) {
			return new BaseModelList<FeeRecieptPayment>(null, false, CreateMessageException.createMessageException(e) + " - findAllFeeRecieptPayment - " + recieptNo + " - thất bại.");
		}
	}

	@Override
	public BaseModelSingle<Boolean> saveOrUpdateNew(FeeRecieptPayment e) {
		try {
			this.saveOrUpdate(e);
			return new BaseModelSingle<Boolean>(true, true, null);
		} catch (Exception ex) {
			ex.printStackTrace();
			return new BaseModelSingle<Boolean>(false, false,  CreateMessageException.createMessageException(ex) + " - saveOrUpdateNew:" + e.getRecieptNo() + " - thất bại.");
		}
	}

	@Override
	public BaseModelSingle<Boolean> deleteObject(List<FeeRecieptPayment> list) {
		try {
			for(FeeRecieptPayment e : list)
				this.delete(e);
			return new BaseModelSingle<Boolean>(true, true, null);
		} catch (Exception ex) {
			ex.printStackTrace();
			return new BaseModelSingle<Boolean>(false, false,  CreateMessageException.createMessageException(ex) + " - deleteObject - thất bại.");
		}
	}
}
