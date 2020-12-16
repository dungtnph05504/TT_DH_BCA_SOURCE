package com.nec.asia.nic.framework.report.dao;


import org.hibernate.criterion.Order;

import com.nec.asia.nic.framework.PaginatedResult;
import com.nec.asia.nic.framework.admin.audit.domain.AuditAccessLogs;
import com.nec.asia.nic.framework.dao.GenericDao;

/**
 * @author chris
 *
 */

/* 
 * Modification History:
 * 
 * 24 Sep 2013 (chris): init
 */
public interface AuditAccessLogDao extends
		GenericDao<AuditAccessLogs, Long> {

	PaginatedResult<AuditAccessLogs> getAuditAccessLogList(String userId,String wkstnId,String functionName,
			String dateFrom,String dateTo,String status,int pageSize, Order order, int currentPage);
	
	//default methods from super class
	//public AuditAccessLog findById(Long id);
	//public Long save (AuditAccessLog e);
	//public void saveOrUpdate(AuditAccessLog e);
    //public AuditAccessLog merge(AuditAccessLog e);
	//public void delete(AuditAccessLog e);
	//public void delete(Long id);
	//public List<AuditAccessLog> findAll ();
}
