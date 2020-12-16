/**
 * 
 */
package com.nec.asia.nic.framework.admin.rbac.dao.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;













import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.nec.asia.nic.framework.PaginatedResult;
import com.nec.asia.nic.framework.admin.rbac.dao.UsersDao;
import com.nec.asia.nic.framework.admin.rbac.domain.Users;
import com.nec.asia.nic.framework.dao.impl.GenericHibernateDao;
import com.nec.asia.nic.utils.Constants;

/**
 * @author sailaja_chinapothula
 *
 */
@Repository("usersDao")
public class UsersDaoImpl extends GenericHibernateDao<Users, String> implements UsersDao {
	
	public void updateUserInfo(Users user) throws Exception{
		this.getSession().update(user);
	}
	
	@Override
	public PaginatedResult<Users> findAllForPagination(Users user, int pageNo, int pageSize, Order order) {
		DetachedCriteria tCriteria = DetachedCriteria.forClass(Users.class);
		
		if (StringUtils.isNotEmpty(user.getUserId())) {
			tCriteria.add(Restrictions.ilike("userId", user.getUserId().toLowerCase(), MatchMode.ANYWHERE));
		}

		return findAllForPagination(tCriteria, pageNo, pageSize, order);
	}


	@Override
	public List<Users> findByRoles(String roleId) {
		List<Users> listUsers = new ArrayList<Users>();
		Session session = null;
		try {
			session = this.openSession();
			StringBuffer sql = new StringBuffer("select u.USER_ID from USERS u, USERS_ROLES ur where u.USER_ID = ur.USER_ID and ur.ROLE_ID = ?");
			Query query = session.createSQLQuery(sql.toString()).setParameter(0, roleId);
			List list = query.list();
			Iterator itr = list.iterator();
			if(list != null && list.size() > 0){
				while (itr.hasNext()) {
					Object record = (Object) itr.next();
					Users user = new Users();
					user.setUserId(record != null ? record.toString() : "");
					listUsers.add(user);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			session.close();
		}
		return listUsers;
	}
	
	@Override
	public List<Users> findBySiteCode(String siteId) {
		List<Users> listUsers = new ArrayList<Users>();
		Session session = null;
		try {
			session = this.openSession();
			StringBuffer sql = new StringBuffer("select USER_ID from USERS where SITE_CODE = ?");
			Query query = session.createSQLQuery(sql.toString()).setParameter(0, siteId);
			List list = query.list();
			Iterator itr = list.iterator();
			if(list != null && list.size() > 0){
				while (itr.hasNext()) {
					Object record = (Object) itr.next();
					Users user = new Users();
					user.setUserId(record != null ? record.toString() : "");
					listUsers.add(user);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			session.close();
		}
		return listUsers;
	}

	@Override
	public List<Users> getListUserBySiteGroupAndRole(String siteGroup,
			String role) {
		try {
			DetachedCriteria criteria = DetachedCriteria.forClass(Users.class);
			Criterion task1 = Restrictions.eq("siteGroupCode", siteGroup);
			criteria.createAlias("roles", "Role");
			Criterion task2 = Restrictions.eq("Role.roleId", Constants.ROLE_ID_NIC_LANHDAOPHEDUYET);
			criteria.add(Restrictions.and(task1, task2));
			return this.findAll(criteria);			
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	@Override
	public List<Users> getListUserBySiteCode(String siteCode) {
		try {
			DetachedCriteria criteria = DetachedCriteria.forClass(Users.class);
			Criterion task1 = Restrictions.eq("siteCode", siteCode);
			criteria.add(task1);
			return this.findAll(criteria);			
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public PaginatedResult<Users> findAllForPagination(Users user,
			String nameUser, String loginUser, String siteCode, Boolean flag,
			int pageNo, int pageSize, Order order) {
		try {
			DetachedCriteria tCriteria = DetachedCriteria.forClass(Users.class);
			
			if (StringUtils.isNotEmpty(nameUser)) {
				tCriteria.add(Restrictions.eq("userName", nameUser));
			}
			if (StringUtils.isNotEmpty(loginUser)) {
				tCriteria.add(Restrictions.eq("userId", loginUser));
			}
			if (StringUtils.isNotEmpty(siteCode)) {
				tCriteria.add(Restrictions.eq("siteCode", siteCode));
			}
			if (flag != null) {
				tCriteria.add(Restrictions.eq("deleteFlag", flag));
			}
			return findAllForPagination(tCriteria, pageNo, pageSize, order);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
