/**
 * 
 */
package com.nec.asia.nic.framework.admin.rbac.dao;

import java.util.List;

import org.hibernate.criterion.Order;

import com.nec.asia.nic.framework.PaginatedResult;
import com.nec.asia.nic.framework.admin.rbac.domain.Workstations;
import com.nec.asia.nic.framework.dao.GenericDao;

/**
 * @author sailaja_chinapothula
 *
 */
public interface WorkstationsDao extends GenericDao<Workstations, String> {

	public PaginatedResult<Workstations> findAllForPagination(Workstations workstation, int pageNo, int pageSize, Order order);
	
	//default methods from super class
	//public Workstations findById(String wkstnId);
	//public Long save (Workstations e);
	//public void saveOrUpdate(Workstations e);
    //public Workstations merge(Workstations e);
	//public void delete(Workstations e);
	//public void delete(Workstations wkstnId);
	//public List<Workstations> findAll ();
	
	public List<Workstations> findBySiteCode(String siteCode);
}