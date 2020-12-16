package com.nec.asia.nic.framework.report.dao.impl;

import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.springframework.stereotype.Repository;

import com.nec.asia.nic.framework.admin.audit.domain.AuditSessionLogs;
import com.nec.asia.nic.framework.admin.site.domain.SiteGroups;
import com.nec.asia.nic.framework.dao.DaoException;
import com.nec.asia.nic.framework.dao.impl.GenericHibernateDao;
import com.nec.asia.nic.framework.hibernate.AbstractHibernateDao;
import com.nec.asia.nic.framework.report.dao.GenericReportDao;
import com.nec.asia.nic.utils.ExceptionMessageFormatter;

/* 
 * Modification History:
 * 
 * 19 Apr 2017 (chris): refactor class
 */

@Repository("genericReportDao")
public class GenericReportDaoImpl extends GenericHibernateDao<Object, Integer> implements
		GenericReportDao {
	@Override
	public List getAllEntity(Class entityClass, String propertyName) throws DaoException {
		try {
			DetachedCriteria criteria = DetachedCriteria.forClass(entityClass);
			criteria.addOrder(Order.asc(propertyName));
			return getHibernateTemplate().findByCriteria(criteria); 				 
		} catch (HibernateException e) {
			throw new DaoException(ExceptionMessageFormatter.format(e));
		}
	}
}
