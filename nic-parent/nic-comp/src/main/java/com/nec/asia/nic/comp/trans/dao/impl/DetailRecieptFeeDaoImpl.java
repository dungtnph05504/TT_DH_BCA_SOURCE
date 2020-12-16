package com.nec.asia.nic.comp.trans.dao.impl;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.nec.asia.nic.comp.trans.dao.DetailRecieptFeeDao;
import com.nec.asia.nic.comp.trans.dao.RecieptManagerDao;
import com.nec.asia.nic.comp.trans.domain.BaseModelList;
import com.nec.asia.nic.comp.trans.domain.BaseModelSingle;
import com.nec.asia.nic.comp.trans.domain.DetailRecieptFee;
import com.nec.asia.nic.comp.trans.domain.EppRecieptManager;
import com.nec.asia.nic.framework.dao.impl.GenericHibernateDao;

@Repository("detailRecieptFeeDao")
public class DetailRecieptFeeDaoImpl extends GenericHibernateDao<DetailRecieptFee, Long> implements DetailRecieptFeeDao{

	@Override
	public BaseModelList<DetailRecieptFee> findAllDetailRecieptFee(String recieptNo) {
		try {
			List<DetailRecieptFee> list = null;
			DetachedCriteria criteria = DetachedCriteria.forClass(DetailRecieptFee.class);
			if(StringUtils.isNotEmpty(recieptNo)){
				criteria.add(Restrictions.eq("recieptNo", recieptNo));
				list = this.findAll(criteria);
			}
			return new BaseModelList<DetailRecieptFee>(list, true, null);
		} catch (Exception e) {
			e.printStackTrace();
			return new BaseModelList<DetailRecieptFee>(null, false,  e.getMessage() + "\n [subtrack] \n" + e.getCause().getMessage() + "\n [subtrack] \n" + e.getCause().getCause().getMessage() + " - findAllDetailRecieptFee - " + recieptNo + " - thất bại.");
		}
		
	}

	@Override
	public BaseModelSingle<DetailRecieptFee> findDetailRecieptFee(String recieptNo, String code,
			String number) throws Exception {
		try {
			List<DetailRecieptFee> list = null;
			DetachedCriteria criteria = DetachedCriteria.forClass(DetailRecieptFee.class);
			if(StringUtils.isNotEmpty(recieptNo)){
				criteria.add(Restrictions.eq("recieptNo", recieptNo));
			}
			if(StringUtils.isNotEmpty(code)){
				criteria.add(Restrictions.eq("codeBill", code));
			}
			if(StringUtils.isNotEmpty(number)){
				criteria.add(Restrictions.eq("numberBill", number));
			}
			list = this.findAll(criteria);
			if(list != null && list.size() > 0)
				return new BaseModelSingle<DetailRecieptFee>(list.get(0), true, null);
		} catch (Throwable e) {
			e.printStackTrace();
			return new BaseModelSingle<DetailRecieptFee>(null, false,  e.getMessage() + "\n [subtrack] \n" + e.getCause().getMessage() + "\n [subtrack] \n" + e.getCause().getCause().getMessage() + " - findDetailRecieptFee - " + recieptNo + " - thất bại.");
		}
		return new BaseModelSingle<DetailRecieptFee>(null, true, null);
	}

	@Override
	public BaseModelSingle<Boolean> saveOrUpdateNew(DetailRecieptFee e) {
		try {
			this.saveOrUpdate(e);
			return new BaseModelSingle<Boolean>(true, true, null);
		} catch (Throwable ex) {
			ex.printStackTrace();
			return new BaseModelSingle<Boolean>(false, false,  ex.getMessage() + "\n [subtrack] \n" + ex.getCause().getMessage() + "\n [subtrack] \n" + ex.getCause().getCause().getMessage() + " - saveOrUpdateNew: " + e.getNumberBill() + " - thất bại.");
		}
	}

}
