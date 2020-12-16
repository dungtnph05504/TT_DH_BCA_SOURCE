package com.nec.asia.nic.framework.report.dao;

import org.hibernate.criterion.Order;

import com.nec.asia.nic.framework.PaginatedResult;
import com.nec.asia.nic.framework.admin.audit.domain.AuditSessionLogs;
import com.nec.asia.nic.framework.dao.GenericDao;

/* 
 * Modification History:
 * 
 * 12 Dec 2013 (Sailaja): getUserSessionLogList method
 */
public interface AuditSessionLogDao extends
		GenericDao<AuditSessionLogs, Long> {

	PaginatedResult<AuditSessionLogs> getUserSessionLogList(
			String userId, String wkstnId, String logindateFrom,
			String loginDateTo, String logoutDateFrom, String logoutDateTo,
			int pageSize, Order hibernateOrder, int currentPage);

}
