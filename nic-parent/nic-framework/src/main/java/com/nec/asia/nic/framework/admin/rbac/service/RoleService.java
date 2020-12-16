/**
 * 
 */
package com.nec.asia.nic.framework.admin.rbac.service;

import java.util.List;

import org.hibernate.criterion.Order;

import com.nec.asia.nic.framework.PaginatedResult;
import com.nec.asia.nic.framework.admin.rbac.domain.Roles;
import com.nec.asia.nic.framework.service.BusinessService;

public interface RoleService extends BusinessService<Roles, String> {

	public List<Roles> findBySystemId(String systemId);

	public PaginatedResult<Roles> getPageByRoleId(String roleId,
			int currentPage, int pageSize, Order hibernateOrder);
}
