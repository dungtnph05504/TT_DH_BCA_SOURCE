package com.nec.asia.nic.comp.trans.dao.impl;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.nec.asia.nic.comp.trans.dao.SynQueueJobXncDao;
import com.nec.asia.nic.comp.trans.domain.SynQueueJobXnc;
import com.nec.asia.nic.framework.dao.impl.GenericHibernateDao;


@Repository
public class SynQueueJobXncDaoImpl extends GenericHibernateDao<SynQueueJobXnc, Long> implements SynQueueJobXncDao{

	@Override
	public SynQueueJobXnc findQueueXncByInfo(Long id, String status) throws Exception {
		try {
			DetachedCriteria criteria = DetachedCriteria.forClass(SynQueueJobXnc.class);
			if(id != null){
				criteria.add(Restrictions.eq("id", id));
			}
			if(StringUtils.isNotEmpty(status)){
				criteria.add(Restrictions.eq("status", status));
			}
			List<SynQueueJobXnc> list = this.findAll(criteria);
			if(list != null && list.size() > 0)
				return list.get(0);
		} catch (Throwable e) {
			throw new Exception(e);
		}
		return null;
	}

	@Override
	public List<SynQueueJobXnc> findQueueXncBySite(String site, String status,
			String objType[], int maxTotal) throws Exception {
		try {
			DetachedCriteria criteria = DetachedCriteria.forClass(SynQueueJobXnc.class);
			if(StringUtils.isNotEmpty(site)){
				criteria.add(Restrictions.eq("siteCode", site));
			}
			if(StringUtils.isNotEmpty(status)){
				criteria.add(Restrictions.eq("status", status));
			}
			if(objType != null){
				criteria.add(Restrictions.in("objectType", objType));
			}
			List<SynQueueJobXnc> list = this.findAll(criteria);
			if(list != null && list.size() > 0)
				return list;
		} catch (Throwable e) {
			throw new Exception(e);
		}
		return null;
	}

	@Override
	public SynQueueJobXnc findQueueXncByStatus(String site, String status,
			String objType) throws Exception {
		try {
			DetachedCriteria criteria = DetachedCriteria.forClass(SynQueueJobXnc.class);
			if(StringUtils.isNotEmpty(site)){
				criteria.add(Restrictions.eq("siteCode", site));
			}
			if(StringUtils.isNotEmpty(status)){
				criteria.add(Restrictions.eq("status", status));
			}
			if(StringUtils.isNotEmpty(objType)){
				criteria.add(Restrictions.eq("objectType", objType));
			}
			List<SynQueueJobXnc> list = this.findAll(criteria);
			if(list != null && list.size() > 0)
				return list.get(0);			
		} catch (Throwable e) {
			e.printStackTrace();
			throw new Exception(e);
		}
		return null;
	}

}
