/**
 * 
 */
package com.nec.asia.nic.framework.report.service;

import org.hibernate.criterion.Order;

import com.nec.asia.nic.framework.PaginatedResult;
import com.nec.asia.nic.framework.admin.audit.domain.AuditSessionLogs;
import com.nec.asia.nic.framework.service.BusinessService;

/* 
 * Modification History:
 * 
 * 12 Dec 2013 (Sailaja): getUserSessionLogList method
 */
public interface AuditSessionLogService extends
		BusinessService<AuditSessionLogs, Long> {

	PaginatedResult<AuditSessionLogs> getUserSessionLogList(
			String userId, String wkstnId, 
			String logindateFrom, String loginDateTo, String logoutDateFrom, String logoutDateTo,
			int pageSize, Order hibernateOrder, int currentPage) throws Exception;

}
