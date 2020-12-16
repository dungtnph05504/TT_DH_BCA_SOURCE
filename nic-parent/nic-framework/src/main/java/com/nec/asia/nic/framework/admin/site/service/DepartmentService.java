package com.nec.asia.nic.framework.admin.site.service;

import java.util.List;

import com.nec.asia.nic.framework.admin.site.domain.Department;
import com.nec.asia.nic.framework.service.BusinessService;

public interface DepartmentService extends BusinessService<Department, String>{
	Boolean saveOrUpdateDepartment(Department department);
	List<Department> findBySiteId(String siteId);
	List<Department> findAllDepartment();
	
}
