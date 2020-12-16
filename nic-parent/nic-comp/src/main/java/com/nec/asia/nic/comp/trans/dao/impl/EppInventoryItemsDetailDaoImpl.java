package com.nec.asia.nic.comp.trans.dao.impl;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.nec.asia.nic.comp.trans.dao.EppInventoryItemsDetailDao;
import com.nec.asia.nic.comp.trans.domain.EppInventoryItemsDetail;
import com.nec.asia.nic.framework.dao.impl.GenericHibernateDao;

@Repository("eppInventoryItemsDetailDao")
public class EppInventoryItemsDetailDaoImpl extends GenericHibernateDao<EppInventoryItemsDetail, Long> implements EppInventoryItemsDetailDao{

	public EppInventoryItemsDetail findByCondition(String docChar, String docNum){
		//EppInvestoryItemsDetail result = new EppInvestoryItemsDetail();
		try {
			DetachedCriteria criteria = DetachedCriteria
					.forClass(EppInventoryItemsDetail.class);
			if(StringUtils.isNotEmpty(docChar) && StringUtils.isNotEmpty(docNum)){
				criteria.add(Restrictions.eq("docChars", docChar));
				criteria.add(Restrictions.eq("docNum", docNum));
				@SuppressWarnings("unchecked")
				List<EppInventoryItemsDetail> lst =  (List<EppInventoryItemsDetail>) this.getHibernateTemplate().findByCriteria(criteria);
				if(lst != null && lst.size() > 0){
					return lst.get(0);
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<EppInventoryItemsDetail> findInventoryItemsDetail(
			Long[] inventoryItemsId, Integer maxTotal, String syncStatus,
			String status) {
		Session session = null;
		try {
			session = this.openSession();
			Criteria criteria = session.createCriteria(EppInventoryItemsDetail.class);
			if(inventoryItemsId != null){
				criteria.add(Restrictions.in("inventoryItemsId", inventoryItemsId));
			}
			if(maxTotal != null){
				criteria.setFirstResult(0);
				criteria.setMaxResults(maxTotal);
			}
			if(StringUtils.isNotEmpty(syncStatus)){
				criteria.add(Restrictions.eq("syncStatus", syncStatus));
			}
			if(StringUtils.isNotEmpty(status)){
				criteria.add(Restrictions.eq("status", status));
			}
			List<EppInventoryItemsDetail> list = criteria.list();
			if(list != null && list.size() > 0)
				return list;
		} catch (Throwable e) {
			e.printStackTrace();
		} finally {
			session.close();
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public EppInventoryItemsDetail findByConditions(String chipNo,
			String docChar, String docNum) {
		try {
			DetachedCriteria criteria = DetachedCriteria.forClass(EppInventoryItemsDetail.class);
			if(StringUtils.isNotEmpty(chipNo)){
				criteria.add(Restrictions.eq("chipSeriesNo", chipNo));
			}
			if(StringUtils.isNotEmpty(docChar)){
				criteria.add(Restrictions.eq("docChars", docChar));
			}
			if(StringUtils.isNotEmpty(docNum)){
				criteria.add(Restrictions.eq("docNum", docNum));
			}
			
			List<EppInventoryItemsDetail> lst =  (List<EppInventoryItemsDetail>) this.getHibernateTemplate().findByCriteria(criteria);
			if(lst != null && lst.size() > 0){
				return lst.get(0);
			}
			
		} catch (Exception e) {
			// TODO: handle exception
		}
		return null;
	}
}
