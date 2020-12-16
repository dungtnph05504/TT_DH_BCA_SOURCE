/**
 * 
 */
package com.nec.asia.nic.framework.admin.rbac.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.nec.asia.nic.framework.PaginatedResult;
import com.nec.asia.nic.framework.admin.rbac.dao.RolesDao;
import com.nec.asia.nic.framework.admin.rbac.domain.Roles;
import com.nec.asia.nic.framework.admin.rbac.domain.Workstations;
import com.nec.asia.nic.framework.dao.impl.GenericHibernateDao;

/**
 * @author sailaja_chinapothula
 *
 */
@Repository("rolesDao")
public class RolesDaoImpl extends GenericHibernateDao<Roles, String> implements RolesDao {
	
	@Override
	public List<Roles> findBySystemId(String systemId){
		List<Roles> result = new ArrayList<Roles>();
		DetachedCriteria tCriteria = DetachedCriteria.forClass(Roles.class);
		if (StringUtils.isNotEmpty(systemId)) {
			tCriteria.add(Restrictions.like("systemId", systemId));
			result = (List<Roles>) getHibernateTemplate().findByCriteria(tCriteria);
		}
		
		return result;
	}

	@Override
	public PaginatedResult<Roles> getPageByRoleId(String roleId,
			int currentPage, int pageSize, Order hibernateOrder) {
		try {
			DetachedCriteria tCriteria = DetachedCriteria.forClass(getPersistentClass());
			tCriteria.add(Restrictions.eq("roleId", roleId));
			tCriteria.addOrder(hibernateOrder);
			return this.findAllForPagination(tCriteria, currentPage, pageSize);
		} catch (Exception e) {
			throw e;
		}
	}
}
