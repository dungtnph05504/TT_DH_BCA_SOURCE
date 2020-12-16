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
import com.nec.asia.nic.framework.admin.audit.domain.AuditAccessLogs;
import com.nec.asia.nic.framework.dao.impl.GenericHibernateDao;
import com.nec.asia.nic.framework.report.dao.AuditAccessLogDao;
import com.nec.asia.nic.utils.DateUtil;

/**
 * @author chris
 *
 */

/* 
 * Modification History:
 * 
 * 24 Sep 2013 (chris): init
 */
@Repository("auditAccessLogDao")
public class AuditAccessLogDaoImpl extends
		GenericHibernateDao<AuditAccessLogs, Long> implements
		AuditAccessLogDao {

	@Override
	public PaginatedResult<AuditAccessLogs> getAuditAccessLogList(
			String userId, String wkstnId, String functionName, String dateFrom, String dateTo, String status, int pageSize, Order hibernateOrder, int currentPage) {
		try {
			DetachedCriteria dCriteria = DetachedCriteria.forClass(AuditAccessLogs.class);
			Date auditDateFrom = DateUtil.strToDate(dateFrom, DateUtil.FORMAT_DDdashMMMdashYYYY);
			Date auditDateTo = DateUtil.strToDate(dateTo, DateUtil.FORMAT_DDdashMMMdashYYYY);
			
			if (StringUtils.isNotBlank(userId)) {
				dCriteria.add(Restrictions.like("userId", userId, MatchMode.ANYWHERE).ignoreCase());
			}
			if(StringUtils.isNotBlank(wkstnId)){
			    dCriteria.add(Restrictions.like("wkstnId", wkstnId, MatchMode.ANYWHERE).ignoreCase());
			}
			if(StringUtils.isNotBlank(functionName)){
				dCriteria.add(Restrictions.like("functionName", functionName, MatchMode.ANYWHERE).ignoreCase());
			}
			if (StringUtils.isNotBlank(status)) {
				dCriteria.add(Restrictions.eq("errorFlag", status).ignoreCase());
			}
			   
			if (auditDateFrom != null && auditDateTo != null) {
				dCriteria.add(Restrictions.between("auditDate", auditDateFrom, auditDateTo));
			} else if(auditDateFrom!=null){
				dCriteria.add(Restrictions.ge("auditDate", auditDateFrom));
			} else if(auditDateTo!=null){
				dCriteria.add(Restrictions.le("auditDate", auditDateTo));
			}
			
			return findAllForPagination(dCriteria, currentPage, pageSize, hibernateOrder);
		} catch(Exception ex) {
			logger.error("Error occurred while getting the audit enquiry search result list. Exception:"+ex.getMessage());
			return null;
		}
	}


}
