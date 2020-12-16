package com.nec.asia.nic.comp.trans.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.nec.asia.nic.comp.trans.dao.LogCheckConnectionDao;
import com.nec.asia.nic.comp.trans.domain.BaseModelSingle;
import com.nec.asia.nic.comp.trans.domain.LogCheckConnection;
import com.nec.asia.nic.comp.trans.domain.NicDocumentData;
import com.nec.asia.nic.comp.ws.log.domain.CreateMessageException;
import com.nec.asia.nic.framework.PaginatedResult;
import com.nec.asia.nic.framework.dao.impl.GenericHibernateDao;

@Repository
public class LogCheckConnectionDaoImpl extends
		GenericHibernateDao<LogCheckConnection, Long> implements
		LogCheckConnectionDao {

	@Override
	public BaseModelSingle<Boolean> saveOrUpdateLogCheckConnection(
			LogCheckConnection logCheckCon) {
		try {
			this.saveOrUpdate(logCheckCon);
			return new BaseModelSingle<Boolean>(true, true, null);
		} catch (Exception e) {
			return new BaseModelSingle<Boolean>(false, false, CreateMessageException.createMessageException(e) + " - saveOrUpdateLogCheckConnection - thất bại");
		}
	}

	@Override
	public BaseModelSingle<LogCheckConnection> findLogBySiteCode(String siteCode) {
		LogCheckConnection logCheck = null;
		try {
			DetachedCriteria detachedCriteria = DetachedCriteria.forClass(getPersistentClass());
		        if (StringUtils.isNotEmpty(siteCode)) {
		            detachedCriteria.add(Restrictions.eq("siteCode", siteCode));
		            List<LogCheckConnection> listLog = this.findAll(detachedCriteria);
		            if (CollectionUtils.isNotEmpty(listLog)) {
		                logCheck = listLog.get(0);
		            }
		        }
	        return new BaseModelSingle<LogCheckConnection>(logCheck, true, null);
		} catch (Exception e) {
			return new BaseModelSingle<LogCheckConnection>(null, false, CreateMessageException.createMessageException(e) + " - findLogBySiteCode - " + siteCode + " - thất bại.");
		}
	}

	@Override
	public PaginatedResult<LogCheckConnection> findAllForPagination(int PageNo,
			int PageSize) {
		try {
			DetachedCriteria criteria = DetachedCriteria
					.forClass(getPersistentClass());
			Order orders = Order.desc("lastConnectedDatetime");
			return this.findAllForPagination(criteria, PageNo, PageSize, orders);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public List<LogCheckConnection> findAllLog() {
		List<LogCheckConnection> listLog = new ArrayList<LogCheckConnection>();
		try {
			listLog = this.findAll();
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		return listLog;
	}

	@Override
	public List<LogCheckConnection> findAllAndOrder(String order){
		try {
			DetachedCriteria criteria = DetachedCriteria
					.forClass(getPersistentClass());
			Order orders = Order.desc(order);
			return this.findAllOrder(criteria, orders);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}
