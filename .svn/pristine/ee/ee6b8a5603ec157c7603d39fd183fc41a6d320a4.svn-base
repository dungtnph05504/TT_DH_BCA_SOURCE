package com.nec.asia.nic.framework.admin.rbac.service;

import java.util.List;

import org.hibernate.criterion.Order;

import com.nec.asia.nic.framework.PaginatedResult;
import com.nec.asia.nic.framework.admin.rbac.domain.Workstations;
import com.nec.asia.nic.framework.service.BusinessService;

public interface WorkStationService extends
		BusinessService<Workstations, String> {

	public PaginatedResult<Workstations> findAllForPagination(Workstations workstation, int pageNo, int pageSize, Order order);
	
	public List<Workstations> findBySiteCode(String siteCode);

}
