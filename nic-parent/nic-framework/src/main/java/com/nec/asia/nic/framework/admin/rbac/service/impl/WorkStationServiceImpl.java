/**
 * 
 */
package com.nec.asia.nic.framework.admin.rbac.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.criterion.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nec.asia.nic.framework.PaginatedResult;
import com.nec.asia.nic.framework.admin.code.dao.CodesDao;
import com.nec.asia.nic.framework.admin.rbac.dao.WorkstationsDao;
import com.nec.asia.nic.framework.admin.rbac.domain.Workstations;
import com.nec.asia.nic.framework.admin.rbac.service.WorkStationService;
import com.nec.asia.nic.framework.service.impl.DefaultBusinessServiceImpl;

/**
 * @author chris - Workstation Service 
 */
@Service("workstationsService")
public class WorkStationServiceImpl extends
		DefaultBusinessServiceImpl<Workstations, String, WorkstationsDao> implements WorkStationService {

	/** must define the non-argument constructor because we use CGLib proxy */
	public WorkStationServiceImpl() {
		super();
	}

	@Autowired
	public WorkStationServiceImpl(WorkstationsDao dao) {
		this.dao = dao;
	}
	
	@Override
	public PaginatedResult<Workstations> findAllForPagination(Workstations workstation, int pageNo, int pageSize, Order order){
		return this.dao.findAllForPagination(workstation, pageNo, pageSize, order);
	}
	
	@Override
	public List<Workstations> findBySiteCode(String siteCode){
		List<Workstations> result = new ArrayList<Workstations>();
		result = this.dao.findBySiteCode(siteCode);
		return result;
	}
}
