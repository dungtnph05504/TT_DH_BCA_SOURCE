package com.nec.asia.nic.framework.admin.site.dao.impl;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.nec.asia.nic.framework.admin.site.dao.DepartmentDao;
import com.nec.asia.nic.framework.admin.site.domain.Department;
import com.nec.asia.nic.framework.dao.impl.GenericHibernateDao;


@Repository
public class DepartmentDaoImpl extends GenericHibernateDao<Department, String> implements DepartmentDao {

	@Override
	public List<Department> findBySiteId(String siteId) {
		try {
			DetachedCriteria criteria = DetachedCriteria.forClass(Department.class);
			if(StringUtils.isNotEmpty(siteId)){
				criteria.add(Restrictions.eq("siteRepository.siteId", siteId));
			}
			criteria.add(Restrictions.eq("active", "Y"));
			List<Department> list = this.findAll(criteria);
			if(list != null && list.size() > 0)
				return list;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public List<Department> findAllDepartment() {
		List<Department> list = null;
		try{
			list = this.findAll();
		}catch(Exception e){
			logger.error("findAllDepartment ERROR");
		}
		return list;
	}

}
