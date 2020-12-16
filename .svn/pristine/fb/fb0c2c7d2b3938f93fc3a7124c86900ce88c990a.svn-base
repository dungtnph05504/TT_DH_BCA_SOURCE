/**
 * 
 */
package com.nec.asia.nic.framework.admin.rbac.dao;

import java.util.List;

import org.hibernate.criterion.Order;

import com.nec.asia.nic.framework.PaginatedResult;
import com.nec.asia.nic.framework.admin.rbac.domain.Roles;
import com.nec.asia.nic.framework.admin.rbac.domain.Workstations;
import com.nec.asia.nic.framework.dao.GenericDao;

/**
 * @author sailaja_chinapothula
 *
 */
public interface RolesDao extends GenericDao<Roles, String> {
	
	//default methods from super class
	//public Roles findById(String roleId);
	//public Long save (Roles e);
	//public void saveOrUpdate(Roles e);
    //public Roles merge(Roles e);
	//public void delete(Roles e);
	//public void delete(Roles funtnId);
	//public List<Roles> findAll ();
	public List<Roles> findBySystemId(String systemId);

	public PaginatedResult<Roles> getPageByRoleId(String roleId,
			int currentPage, int pageSize, Order hibernateOrder);
}