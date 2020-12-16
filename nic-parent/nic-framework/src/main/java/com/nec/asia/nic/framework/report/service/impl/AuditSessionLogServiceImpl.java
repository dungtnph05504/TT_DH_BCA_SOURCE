/**
 * 
 */
package com.nec.asia.nic.framework.report.service.impl;

import org.hibernate.criterion.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nec.asia.nic.framework.PaginatedResult;
import com.nec.asia.nic.framework.admin.audit.domain.AuditSessionLogs;
import com.nec.asia.nic.framework.admin.site.dao.SiteGroupsDao;
import com.nec.asia.nic.framework.report.dao.AuditSessionLogDao;
import com.nec.asia.nic.framework.report.service.AuditSessionLogService;
import com.nec.asia.nic.framework.service.impl.DefaultBusinessServiceImpl;

/*
 * Modification History:
 * 12 Dec 2013 (Sailaja): getUserSessionLogList method
 */
@Service("auditSessionLogService")
public class AuditSessionLogServiceImpl extends DefaultBusinessServiceImpl<AuditSessionLogs, Long, AuditSessionLogDao> implements AuditSessionLogService {

    /** must define the non-argument constructor because we use CGLib proxy */
    public AuditSessionLogServiceImpl() {
    }

    @Autowired
    public AuditSessionLogServiceImpl(AuditSessionLogDao dao) {
        this.dao = dao;
    }

    @Override
    public PaginatedResult<AuditSessionLogs> getUserSessionLogList(
        String userId, String wkstnId, String logindateFrom, String loginDateTo, String logoutDateFrom, String logoutDateTo, int pageSize, Order hibernateOrder, int currentPage) throws Exception {
        try {
            return dao.getUserSessionLogList(userId, wkstnId, logindateFrom, loginDateTo, logoutDateFrom, logoutDateTo, pageSize, hibernateOrder, currentPage);
        } catch (Exception ex) {
        	logger.error("Exception in AuditSessionLogService", ex);
        }
        return null;

    }

}
