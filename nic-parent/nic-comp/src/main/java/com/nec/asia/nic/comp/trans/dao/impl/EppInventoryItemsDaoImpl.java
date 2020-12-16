package com.nec.asia.nic.comp.trans.dao.impl;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.nec.asia.nic.comp.trans.dao.EppInventoryItemsDao;
import com.nec.asia.nic.comp.trans.domain.EppInventoryItems;
import com.nec.asia.nic.framework.dao.impl.GenericHibernateDao;


@Repository
public class EppInventoryItemsDaoImpl extends GenericHibernateDao<EppInventoryItems, Long> implements EppInventoryItemsDao{

	@SuppressWarnings("unchecked")
	@Override
	public List<EppInventoryItems> findInventoryItems(Integer inventoryId, Integer maxTotal) {
		Session session = null;
		try {
			session = this.openSession();
			Criteria criteria = session.createCriteria(EppInventoryItems.class);
			if(inventoryId != null){
				criteria.add(Restrictions.eq("inventoryId", inventoryId));
			}
			if(maxTotal != null){
				criteria.setFirstResult(0);
				criteria.setMaxResults(maxTotal);
			}
			List<EppInventoryItems> list = criteria.list();
			if(list != null && list.size() > 0)
				return list;
		} catch (Throwable e) {
			e.printStackTrace();
		} finally {
			session.close();
		}
		return null;
	}

	@Override
	public EppInventoryItems findInventoryItemsById(Long itemId,
			Integer category) {
		Session session = null;
		try {
			session = this.openSession();
			Criteria criteria = session.createCriteria(EppInventoryItems.class);
			if(itemId != null){
				criteria.add(Restrictions.eq("id", itemId));
			}
			if(category != null){
				criteria.add(Restrictions.eq("categoryId", category));
			}
			@SuppressWarnings("unchecked")
			List<EppInventoryItems> list = criteria.list();
			if(list != null && list.size() > 0)
				return list.get(0);
		} catch (Throwable e) {
			e.printStackTrace();
		} finally {
			session.close();
		}
		return null;
	}

}
