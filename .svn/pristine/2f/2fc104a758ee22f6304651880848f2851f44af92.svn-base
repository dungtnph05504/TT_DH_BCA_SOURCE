package com.nec.asia.nic.comp.trans.dao.impl;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.nec.asia.nic.comp.trans.dao.RecieptManagerDao;
import com.nec.asia.nic.comp.trans.domain.BaseModelList;
import com.nec.asia.nic.comp.trans.domain.BaseModelSingle;
import com.nec.asia.nic.comp.trans.domain.EppRecieptManager;
import com.nec.asia.nic.comp.ws.log.domain.CreateMessageException;
import com.nec.asia.nic.framework.dao.impl.GenericHibernateDao;

@Repository("recieptManagerDao")
public class RecieptManagerDaoImpl extends GenericHibernateDao<EppRecieptManager, Long> implements RecieptManagerDao{

	@Override
	public BaseModelList<EppRecieptManager> findAllEppRecieptManager(String recieptNo) {
		try {
			List<EppRecieptManager> list = null;
			DetachedCriteria criteria = DetachedCriteria.forClass(EppRecieptManager.class);
			if(StringUtils.isNotEmpty(recieptNo)){
				criteria.add(Restrictions.eq("recieptNo", recieptNo));
				list = this.findAll(criteria);
			}
			return new BaseModelList<EppRecieptManager>(list, true, null);
		} catch (Exception e) {
			e.printStackTrace();
			return new BaseModelList<EppRecieptManager>(null, false,  CreateMessageException.createMessageException(e) + " - findAllEppRecieptManager - " + recieptNo + " - thất bại.");
		}
	}

	@Override
	public BaseModelSingle<Boolean> saveOrUpdateNew(EppRecieptManager e) {
		try {
			this.saveOrUpdate(e);
			return new BaseModelSingle<Boolean>(true, true, null);
		} catch (Throwable ex) {
			ex.printStackTrace();
			return new BaseModelSingle<Boolean>(false, false,  CreateMessageException.createMessageException(new Exception(ex)) + " - saveOrUpdateNew: EppRecieptManager: recieptNo = " + e.getRecieptNo() + " - thất bại.");
		}
	}

}
