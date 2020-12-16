package com.nec.asia.nic.comp.ws.log.dao.impl;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.nec.asia.nic.comp.trans.domain.BaseModelSingle;
import com.nec.asia.nic.comp.ws.log.dao.EppWsLogDao;
import com.nec.asia.nic.comp.ws.log.domain.CreateMessageException;
import com.nec.asia.nic.comp.ws.log.domain.EppWsLog;
import com.nec.asia.nic.framework.PaginatedResult;
import com.nec.asia.nic.framework.dao.impl.GenericHibernateDao;
import com.nec.asia.nic.utils.DateUtil;

@Repository
public class EppWsLogDaoImpl extends GenericHibernateDao<EppWsLog, Long> implements EppWsLogDao{

	@Override
	public List<EppWsLog> getListWsLogByKeyId(String keyId) {
		try {
			DetachedCriteria dCriteria = DetachedCriteria.forClass(getPersistentClass());
			dCriteria.add(Restrictions.eq("keyId", keyId));
			return this.findAll(dCriteria);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	@Override
	public EppWsLog getWsLogById(long id) {
		try {
			DetachedCriteria dCriteria = DetachedCriteria.forClass(getPersistentClass());
			dCriteria.add(Restrictions.eq("id", id));
			 List<EppWsLog> list=this.findAll(dCriteria);
			 if(list !=null && list.size()>0) return list.get(0);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
    @Override
    public List<EppWsLog> getWsLogAll(){
    	try {
    		DetachedCriteria dCriteria = DetachedCriteria.forClass(getPersistentClass());
    		return this.findAll(dCriteria);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
    	return null;
    }
	@Override
	public BaseModelSingle<Boolean> insertDataTable(EppWsLog eppWsLog) {
		try {
			super.saveOrUpdate(eppWsLog);
			return new BaseModelSingle<Boolean>(true, true, null);
		} catch (Exception e) {
			
			e.printStackTrace();
			return new BaseModelSingle<Boolean>(false, false,  CreateMessageException.createMessageException(e));
		}
	}
	@Override
	public PaginatedResult<EppWsLog> findAllForPagination(int PageNo,int PageSize){
		try {
			DetachedCriteria criteria = DetachedCriteria
					.forClass(getPersistentClass());
			Order orders = Order.desc("createdDate");
			return this.findAllForPagination(criteria, PageNo, PageSize, orders);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return null;
	}
	@Override 
	public PaginatedResult<EppWsLog> findAllForPagination(String keyId,String urlRequest,String logWsDateFrom,String logWsDateTo,String type,int pageNo,int pageSize){
		try {
			DetachedCriteria criteria = DetachedCriteria
					.forClass(getPersistentClass());
			Order orders = Order.desc("createdDate");
			Date auditDateFrom = DateUtil.strToDate(logWsDateFrom, DateUtil.FORMAT_YYYYMMDD);
			Date auditDateTo = DateUtil.strToDate(logWsDateTo, DateUtil.FORMAT_YYYYMMDD);
			
			if (StringUtils.isNotBlank(keyId)) {
				criteria.add(Restrictions.like("keyId", keyId, MatchMode.ANYWHERE).ignoreCase());
			}
			if(StringUtils.isNotBlank(urlRequest)){
				criteria.add(Restrictions.like("urlRequest", urlRequest, MatchMode.ANYWHERE).ignoreCase());
			}
			if(StringUtils.isNotBlank(type)){
				criteria.add(Restrictions.like("type", type, MatchMode.ANYWHERE).ignoreCase());
			}
//			if (StringUtils.isNotBlank(status)) {
//				dCriteria.add(Restrictions.eq("errorFlag", status).ignoreCase());
//			}
			   
			if (auditDateFrom != null && auditDateTo != null) {
				criteria.add(Restrictions.between("createdDate", auditDateFrom, auditDateTo));
			} else if(auditDateFrom!=null){
				criteria.add(Restrictions.ge("createdDate", auditDateFrom));
			} else if(auditDateTo!=null){
				criteria.add(Restrictions.le("createdDate", auditDateTo));
			}
			return this.findAllForPagination(criteria, pageNo, pageSize, orders);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return null;
	}
}
