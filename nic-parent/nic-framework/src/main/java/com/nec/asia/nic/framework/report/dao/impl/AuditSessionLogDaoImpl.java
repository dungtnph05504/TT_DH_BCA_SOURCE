/**
 * 
 */
package com.nec.asia.nic.framework.report.dao.impl;

import java.util.Date;

import org.apache.commons.lang.StringUtils;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.nec.asia.nic.framework.PaginatedResult;
import com.nec.asia.nic.framework.admin.audit.domain.AuditSessionLogs;
import com.nec.asia.nic.framework.dao.impl.GenericHibernateDao;
import com.nec.asia.nic.framework.report.dao.AuditSessionLogDao;
import com.nec.asia.nic.utils.DateUtil;

/* 
 * Modification History:
 * 
 * 24 Sep 2013 (chris): init
 */

@Repository("auditSessionLogDao")
public class AuditSessionLogDaoImpl extends
		GenericHibernateDao<AuditSessionLogs, Long> implements
		AuditSessionLogDao {

	@Override
	public PaginatedResult<AuditSessionLogs> getUserSessionLogList(
			String userId, String wkstnId, String logindateFrom,
			String loginDateTo, String logoutDateFrom, String logoutDateTo,
			int pageSize, Order hibernateOrder, int currentPage) {
		try {
			DetachedCriteria dCriteria = DetachedCriteria.forClass(AuditSessionLogs.class);
			
			Date logInDateFrom = DateUtil.strToDate(logindateFrom, DateUtil.FORMAT_DDdashMMMdashYYYY);
			Date logInDateTo = DateUtil.strToDate(loginDateTo, DateUtil.FORMAT_DDdashMMMdashYYYY);
			Date logOutDateFrom = DateUtil.strToDate(logoutDateFrom, DateUtil.FORMAT_DDdashMMMdashYYYY);
			Date logOutDateTo = DateUtil.strToDate(logoutDateTo, DateUtil.FORMAT_DDdashMMMdashYYYY);
			
			if (StringUtils.isNotBlank(userId)) {
				dCriteria.add(Restrictions.like("userId", userId, MatchMode.ANYWHERE).ignoreCase());
			}
			if (StringUtils.isNotBlank(wkstnId)) {
				dCriteria.add(Restrictions.like("wkstnId", wkstnId,	MatchMode.ANYWHERE).ignoreCase());
			}
			
			if (logInDateFrom != null && logInDateTo != null) {
				dCriteria.add(Restrictions.between("loginDate", logInDateFrom,	logInDateTo));
			} else if (logInDateFrom != null) {
				dCriteria.add(Restrictions.ge("loginDate", logInDateFrom));
			} else if (logInDateTo != null) {
				dCriteria.add(Restrictions.le("loginDate", logInDateTo));
			}
			
			if (logOutDateFrom != null && logOutDateTo != null) {
				dCriteria.add(Restrictions.between("logoutDate", logOutDateFrom, logOutDateTo));
			} else if (logOutDateFrom != null) {
				dCriteria.add(Restrictions.ge("logoutDate", logOutDateFrom));
			} else if (logOutDateTo != null) {
				dCriteria.add(Restrictions.le("logoutDate", logOutDateTo));
			}
			return findAllForPagination(dCriteria, currentPage, pageSize, hibernateOrder);
		} catch (Exception ex) {
			logger.error("Error occurred while getting the user session enquiry search result list. Exception:"	+ ex.getMessage());
			return null;
		}
	}

}
