package com.nec.asia.nic.comp.trans.dao.impl;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.LogicalExpression;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.nec.asia.nic.comp.trans.dao.ConfigurationWorkflowDao;
import com.nec.asia.nic.comp.trans.domain.BaseModelSingle;
import com.nec.asia.nic.comp.trans.domain.ConfigurationWorkflow;
import com.nec.asia.nic.framework.PaginatedResult;
import com.nec.asia.nic.framework.dao.impl.GenericHibernateDao;

@Repository
public class ConfigurationWorkflowDaoImpl extends GenericHibernateDao<ConfigurationWorkflow, Long> implements ConfigurationWorkflowDao{

	@Override
	public List<ConfigurationWorkflow> findSiteRepositoryByActive(
			String siteGroup, String type, Date todayTime, Integer me, Boolean stage) {
		try {
			DetachedCriteria detachedCriteria = DetachedCriteria.forClass(ConfigurationWorkflow.class);
			if(StringUtils.isNotEmpty(siteGroup)){
				if(me == 0){
					detachedCriteria.add(Restrictions.ne("siteIdTo", siteGroup));					
				}
				if(me == 1){
					detachedCriteria.add(Restrictions.eq("siteIdTo", siteGroup));		
				}
			}
			if(StringUtils.isNotEmpty(type)){
				detachedCriteria.add(Restrictions.eq("configType", type));
			}
			if(todayTime != null){
				detachedCriteria.add(Restrictions.le("dateTimeFrom", todayTime));
				detachedCriteria.add(Restrictions.ge("dateTimeTo", todayTime));
			}
			if(stage != null){
				detachedCriteria.add(Restrictions.eq("stage", stage));
			}
		 	List<ConfigurationWorkflow> configList = this.findAll(detachedCriteria);
		 	if(configList != null && configList.size() > 0)
		 		return configList;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public ConfigurationWorkflow findSiteRepositoryByType(String siteGroup,
			String siteCode, String type) {
		try {
			DetachedCriteria detachedCriteria = DetachedCriteria.forClass(ConfigurationWorkflow.class);
			if(StringUtils.isNotEmpty(siteGroup)){			
				detachedCriteria.add(Restrictions.eq("siteIdTo", siteGroup));		
			}
			if(StringUtils.isNotEmpty(type)){
				detachedCriteria.add(Restrictions.eq("configType", type));
			}
			if(StringUtils.isNotEmpty(siteCode)){
				detachedCriteria.add(Restrictions.eq("siteIdFrom", siteCode));
			}
		 	List<ConfigurationWorkflow> configList = this.findAll(detachedCriteria);
		 	if(configList != null && configList.size() > 0)
		 		return configList.get(0);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public BaseModelSingle<ConfigurationWorkflow> findSiteRepositoryBySite(String siteCode,
			Date todayTime, String type, Boolean stage) {
		try {
			DetachedCriteria detachedCriteria = DetachedCriteria.forClass(ConfigurationWorkflow.class);
			if(StringUtils.isNotEmpty(siteCode)){			
				detachedCriteria.add(Restrictions.eq("siteIdFrom", siteCode));		
			}
			if(StringUtils.isNotEmpty(type)){
				detachedCriteria.add(Restrictions.eq("configType", type));
			}
			if(todayTime != null){
				detachedCriteria.add(Restrictions.le("dateTimeFrom", todayTime));
				detachedCriteria.add(Restrictions.ge("dateTimeTo", todayTime));
			}
			if(stage != null){
				detachedCriteria.add(Restrictions.eq("stage", stage));
			}
		 	List<ConfigurationWorkflow> configList = this.findAll(detachedCriteria);
		 	if(configList != null && configList.size() > 0)
		 		return new BaseModelSingle<ConfigurationWorkflow>(configList.get(0), true, null);
		} catch (Exception e) {
			e.printStackTrace();
			//save Log
			return new BaseModelSingle<ConfigurationWorkflow>(null, false,  e.getMessage() + "\n [subtrack] \n" + e.getCause().getMessage() + "\n [subtrack] \n" + e.getCause().getCause().getMessage() + " - findSiteRepositoryBySite: " + siteCode + " - thất bại.");
		}
		return new BaseModelSingle<ConfigurationWorkflow>(null, true, null);
	}

	@Override
	public PaginatedResult<ConfigurationWorkflow> findPaginateBySearch(
			String configType, String stage, String siteFrom, Date todayTime, int pageNo, int pageSize) {
		try {
			DetachedCriteria detachedCriteria = DetachedCriteria.forClass(ConfigurationWorkflow.class);
			if(StringUtils.isNotEmpty(configType)){			
				detachedCriteria.add(Restrictions.eq("configType", configType));		
			}
			if(StringUtils.isNotEmpty(siteFrom)){
				detachedCriteria.add(Restrictions.eq("siteIdFrom", siteFrom));
			}
			if(todayTime != null && StringUtils.isNotEmpty(stage)){
				if(stage.equals("1")){
					Criterion and1 = Restrictions.le("dateTimeFrom", todayTime);
					Criterion and2 = Restrictions.ge("dateTimeTo", todayTime);
					LogicalExpression andExp = Restrictions.and(and1, and2);
					detachedCriteria.add(andExp);
				}else{
					Criterion or1 = Restrictions.gt("dateTimeFrom", todayTime);
					Criterion or2 = Restrictions.lt("dateTimeTo", todayTime);
					LogicalExpression orExp = Restrictions.or(or1, or2);
					detachedCriteria.add(orExp);
				}
			}
		 	PaginatedResult<ConfigurationWorkflow> configList = this.findAllForPagination(detachedCriteria, pageNo, pageSize);
		 	if(configList != null)
		 		return configList;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public ConfigurationWorkflow findConfigById(Long id, Boolean stage) {
		try {
			DetachedCriteria detachedCriteria = DetachedCriteria.forClass(ConfigurationWorkflow.class);
			if(id != null){			
				detachedCriteria.add(Restrictions.eq("id", id));		
			}
			if(stage != null){
				detachedCriteria.add(Restrictions.eq("stage", stage));
			}
		 	List<ConfigurationWorkflow> configList = this.findAll(detachedCriteria);
		 	if(configList != null && configList.size() > 0)
		 		return configList.get(0);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/*Bỏ hàm này*/
	@Override
	public List<ConfigurationWorkflow> findSiteRepositoryByNoActive(
			String siteGroup, String type, Date todayTime) {
		try {
			DetachedCriteria detachedCriteria = DetachedCriteria.forClass(ConfigurationWorkflow.class);
			
			Criterion cri1 = Restrictions.ne("siteIdTo", siteGroup);									
			Criterion or1 = Restrictions.gt("dateTimeFrom", todayTime);
			Criterion or2 = Restrictions.lt("dateTimeTo", todayTime);
			
			Criterion cri2 = Restrictions.eq("stage", false);

			detachedCriteria.add(Restrictions.or(Restrictions.or(Restrictions.or(or1, or2), cri1), cri2));
			
			if(StringUtils.isNotEmpty(type)){
				detachedCriteria.add(Restrictions.eq("configType", type));
			}
			
			
		 	List<ConfigurationWorkflow> configList = this.findAll(detachedCriteria);
		 	if(configList != null && configList.size() > 0)
		 		return configList;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}
