package com.nec.asia.nic.comp.trans.dao.impl;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.nec.asia.nic.comp.trans.dao.DetailRecieptDao;
import com.nec.asia.nic.comp.trans.dao.RecieptManagerDao;
import com.nec.asia.nic.comp.trans.domain.EppDetailReciept;
import com.nec.asia.nic.comp.trans.domain.EppRecieptManager;
import com.nec.asia.nic.framework.dao.impl.GenericHibernateDao;

@Repository("detailRecieptDao")
public class DetailRecieptDaoImpl extends GenericHibernateDao<EppDetailReciept, Long> implements DetailRecieptDao{

	@Override
	public List<EppDetailReciept> findAllEppDetailReciept(String recieptNo) {
		try {
			List<EppDetailReciept> list = null;
			DetachedCriteria criteria = DetachedCriteria.forClass(EppDetailReciept.class);
			if(StringUtils.isNotEmpty(recieptNo)){
				criteria.add(Restrictions.eq("recieptNo", recieptNo));
			}
			list = this.findAll(criteria);
			return list;
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
		}
		return null;
	}

	@Override
	public EppDetailReciept findDetailReciept(String recieptNo,
			String transactionId) throws Exception {
		try {
			List<EppDetailReciept> list = null;
			DetachedCriteria criteria = DetachedCriteria.forClass(EppDetailReciept.class);
			if(StringUtils.isNotEmpty(recieptNo)){
				criteria.add(Restrictions.eq("recieptNo", recieptNo));
			}
			if(StringUtils.isNotEmpty(transactionId)){
				criteria.add(Restrictions.eq("transactionId", transactionId));
			}
			list = this.findAll(criteria);
			if(list != null && list.size() > 0)
				return list.get(0);
		} catch (Throwable e) {
			e.printStackTrace();
			throw new Exception(e);
		}
		return null;
	}

}
