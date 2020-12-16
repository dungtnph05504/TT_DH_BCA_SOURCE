package com.nec.asia.nic.comp.trans.dao.impl;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.nec.asia.nic.comp.trans.dao.ConfigurationApiDao;
import com.nec.asia.nic.comp.trans.domain.ConfigurationApi;
import com.nec.asia.nic.framework.PaginatedResult;
import com.nec.asia.nic.framework.dao.impl.GenericHibernateDao;


@Repository
public class ConfigurationApiDaoImpl extends GenericHibernateDao<ConfigurationApi, Long> implements ConfigurationApiDao{

	@Override
	public PaginatedResult<ConfigurationApi> findListApiBySearch(Integer type,
			String name, Boolean stage, int pageNo, int pageSize) {
		try {
			DetachedCriteria criteria = DetachedCriteria.forClass(ConfigurationApi.class);
			if(type != null){
				criteria.add(Restrictions.eq("type", type));
			}
			if(StringUtils.isNotEmpty(name)){
				criteria.add(Restrictions.eq("nameApi", name));
			}
			if(stage != null){
				criteria.add(Restrictions.eq("closeAll", stage));
			}
			PaginatedResult<ConfigurationApi> result = this.findAllForPagination(criteria, pageNo, pageSize);
			return result;
		} catch (Throwable e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public ConfigurationApi findConfigApiById(Long id, Boolean stage) {
		try {
			DetachedCriteria criteria = DetachedCriteria.forClass(ConfigurationApi.class);
			if(id != null){
				criteria.add(Restrictions.eq("id", id));
			}
			if(stage != null){
				criteria.add(Restrictions.eq("closeAll", stage));
			}
			List<ConfigurationApi> result = this.findAll(criteria);
			if(result != null && result.size() > 0)
				return result.get(0);
		} catch (Throwable e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public ConfigurationApi findConfigApiByName(String name, Boolean stage) {
		try {
			DetachedCriteria criteria = DetachedCriteria.forClass(ConfigurationApi.class);
			if(StringUtils.isNotEmpty(name)){
				criteria.add(Restrictions.eq("nameApi", name));
			}
			if(stage != null){
				criteria.add(Restrictions.eq("closeAll", stage));
			}
			List<ConfigurationApi> result = this.findAll(criteria);
			if(result != null && result.size() > 0)
				return result.get(0);
		} catch (Throwable e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public ConfigurationApi findConfigApiByName(String url, String name) {
		try {
			DetachedCriteria criteria = DetachedCriteria.forClass(ConfigurationApi.class);
			if(StringUtils.isNotEmpty(name)){
				criteria.add(Restrictions.eq("nameApi", name));
			}
			if(StringUtils.isNotEmpty(url)){
				criteria.add(Restrictions.eq("uriApi", url));
			}
			List<ConfigurationApi> result = this.findAll(criteria);
			if(result != null && result.size() > 0)
				return result.get(0);
		} catch (Throwable e) {
			e.printStackTrace();
		}
		return null;
	}

}
