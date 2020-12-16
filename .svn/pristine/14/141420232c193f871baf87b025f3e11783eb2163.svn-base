/**
 * 
 */
package com.nec.asia.nic.framework.admin.rbac.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.nec.asia.nic.framework.PaginatedResult;
import com.nec.asia.nic.framework.admin.rbac.dao.WorkstationsDao;
import com.nec.asia.nic.framework.admin.rbac.domain.Users;
import com.nec.asia.nic.framework.admin.rbac.domain.Workstations;
import com.nec.asia.nic.framework.admin.site.domain.SiteGroups;
import com.nec.asia.nic.framework.dao.impl.GenericHibernateDao;

/**
 * @author sailaja_chinapothula
 *
 */
@Repository("workstationsDao")
public class WorkstationsDaoImpl extends GenericHibernateDao<Workstations, String> implements WorkstationsDao {
	@Override
	public PaginatedResult<Workstations> findAllForPagination(Workstations workstation, int pageNo, int pageSize, Order order) {
		DetachedCriteria tCriteria = DetachedCriteria.forClass(Workstations.class);
		
		if (StringUtils.isNotEmpty(workstation.getWkstnId())) {
			tCriteria.add(Restrictions.ilike("wkstnId", workstation.getWkstnId().toLowerCase(), MatchMode.ANYWHERE));
		}

		return findAllForPagination(tCriteria, pageNo, pageSize, order);
	}
	
	@Override
	public List<Workstations> findBySiteCode(String siteCode){
		List<Workstations> lstWskt = new ArrayList<Workstations>();
		DetachedCriteria tCriteria = DetachedCriteria.forClass(Workstations.class);
		if (StringUtils.isNotEmpty(siteCode)) {
			tCriteria.add(Restrictions.like("siteCode", siteCode));
			lstWskt = (List<Workstations>) getHibernateTemplate().findByCriteria(tCriteria);
		}
		return lstWskt;
	}
}
