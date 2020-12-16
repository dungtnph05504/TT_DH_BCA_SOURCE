/**
 * 
 */
package com.nec.asia.nic.framework.admin.rbac.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.nec.asia.nic.framework.admin.rbac.dao.FunctionsDao;
import com.nec.asia.nic.framework.admin.rbac.domain.Functions;
import com.nec.asia.nic.framework.admin.rbac.domain.Roles;
import com.nec.asia.nic.framework.dao.impl.GenericHibernateDao;

/**
 * @author sailaja_chinapothula
 *
 */
@Repository("functionDao")
public class FunctionDaoImpl extends GenericHibernateDao<Functions, String> implements FunctionsDao {
	
	@Override
	public List<Functions> findBySystemId(String systemId){
		List<Functions> result = new ArrayList<Functions>();
		DetachedCriteria tCriteria = DetachedCriteria.forClass(Functions.class);
		if (StringUtils.isNotEmpty(systemId)) {
			tCriteria.add(Restrictions.like("systemId", systemId));
			result = (List<Functions>) getHibernateTemplate().findByCriteria(tCriteria);
		}
		
		return result;
	}
}
