/**
 * 
 */
package com.nec.asia.nic.framework.admin.rbac.dao;

import java.util.List;

import org.hibernate.criterion.Order;

import com.nec.asia.nic.framework.PaginatedResult;
import com.nec.asia.nic.framework.admin.rbac.domain.Users;
import com.nec.asia.nic.framework.dao.GenericDao;

/**
 * @author sailaja_chinapothula
 *
 */
public interface UsersDao extends GenericDao<Users, String> {
	
	public void updateUserInfo(Users users) throws Exception;
	//default methods from super class
	//public Users findById(String userId);
	//public Long save (Users e);
	//public void saveOrUpdate(Users e);
    //public Users merge(Users e);
	//public void delete(Users e);
	//public void delete(Users userId);
	//public List<Users> findAll ();

	public PaginatedResult<Users> findAllForPagination(Users user, int pageNo,	 int pageSize, Order order);
	public PaginatedResult<Users> findAllForPagination(Users user, String nameUser, String loginUser, String siteCode, Boolean flag, int pageNo, int pageSize, Order order);
	public List<Users> findByRoles(String roleId);
	public List<Users> getListUserBySiteGroupAndRole(String siteGroup, String role);
	public List<Users> getListUserBySiteCode(String siteCode);
	public List<Users> findBySiteCode(String siteId);
}