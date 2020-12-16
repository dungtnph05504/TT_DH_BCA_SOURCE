package com.nec.asia.nic.framework.admin.site.dao;

import java.util.List;

import com.nec.asia.nic.framework.admin.site.domain.Department;
import com.nec.asia.nic.framework.dao.GenericDao;

public interface DepartmentDao extends GenericDao<Department, String>{
	List<Department> findBySiteId(String siteId);

	List<Department> findAllDepartment();
}
