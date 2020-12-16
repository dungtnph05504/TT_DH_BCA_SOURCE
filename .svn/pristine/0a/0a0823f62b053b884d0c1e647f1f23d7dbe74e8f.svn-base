/**
 * 
 */
package com.nec.asia.nic.framework.admin.rbac.service.impl;

import java.util.List;

import org.hibernate.criterion.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nec.asia.nic.framework.PaginatedResult;
import com.nec.asia.nic.framework.admin.rbac.dao.RolesDao;
import com.nec.asia.nic.framework.admin.rbac.domain.Roles;
import com.nec.asia.nic.framework.admin.rbac.service.RoleService;
import com.nec.asia.nic.framework.service.impl.DefaultBusinessServiceImpl;

/**
 * @author chris - Role Service 
 */
 @Service("roleService")
public class RoleServiceImpl extends
		DefaultBusinessServiceImpl<Roles, String, RolesDao> implements RoleService {

	public RoleServiceImpl() {
		super();
	}

	@Autowired
	public RoleServiceImpl(RolesDao dao) {
		this.dao = dao;
	}
	
	@Override
	public List<Roles> findBySystemId(String systemId){
		return this.dao.findBySystemId(systemId);
	}

	@Override
	public PaginatedResult<Roles> getPageByRoleId(String roleId,
			int currentPage, int pageSize, Order hibernateOrder) {
		try {
			return this.dao.getPageByRoleId(roleId, currentPage, pageSize, hibernateOrder);
		} catch (Exception e) {
			e.printStackTrace();
			return new PaginatedResult<>();
		}
	}
}
