package com.nec.asia.nic.framework.admin.site.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nec.asia.nic.framework.admin.site.dao.DepartmentDao;
import com.nec.asia.nic.framework.admin.site.domain.Department;
import com.nec.asia.nic.framework.admin.site.service.DepartmentService;
import com.nec.asia.nic.framework.service.impl.DefaultBusinessServiceImpl;


@Service
public class DepartmentServiceImpl extends DefaultBusinessServiceImpl<Department, String, DepartmentDao> implements DepartmentService{

	@Autowired
	DepartmentDao dao;
	
	@Override
	public Boolean saveOrUpdateDepartment(Department department) {
		try {
			dao.saveOrUpdate(department);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public List<Department> findBySiteId(String siteId) {
		// TODO Auto-generated method stub
		return dao.findBySiteId(siteId);
	}

	@Override
	public List<Department> findAllDepartment() {
		return dao.findAllDepartment();
	}

}
