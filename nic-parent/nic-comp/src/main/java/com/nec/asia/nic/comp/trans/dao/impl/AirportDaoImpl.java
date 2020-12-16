package com.nec.asia.nic.comp.trans.dao.impl;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.nec.asia.nic.comp.trans.dao.AirlineDao;
import com.nec.asia.nic.comp.trans.dao.AirportDao;
import com.nec.asia.nic.comp.trans.dao.ArchiveCodeDao;
import com.nec.asia.nic.comp.trans.dao.BorderGateDao;
import com.nec.asia.nic.comp.trans.dao.NicTransactionDao;
import com.nec.asia.nic.comp.trans.domain.BorderGate;
import com.nec.asia.nic.comp.trans.domain.EppAirline;
import com.nec.asia.nic.comp.trans.domain.EppAirport;
import com.nec.asia.nic.comp.trans.domain.EppArchiveCode;
import com.nec.asia.nic.comp.trans.domain.NicDocumentData;
import com.nec.asia.nic.comp.trans.domain.NicTransaction;
import com.nec.asia.nic.framework.PaginatedResult;
import com.nec.asia.nic.framework.dao.impl.GenericHibernateDao;

@Repository("airportDao")
public class AirportDaoImpl extends
	GenericHibernateDao<EppAirport, Long> implements
	AirportDao{

	@Override
	public EppAirport findByCode(String code) {
		try {
			DetachedCriteria detachedCriteria = DetachedCriteria.forClass(getPersistentClass());			
			if(StringUtils.isNotEmpty(code)){
				detachedCriteria.add(Restrictions.eq("code", code));
				List<EppAirport> list = this.findAll(detachedCriteria);
				if(list != null && list.size() > 0)
					return list.get(0);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@Override
	public PaginatedResult<EppAirport> findPaginateBySearch(String code,
			String nationality, int pageNo, int pageSize) {
		try {
			DetachedCriteria criteria = DetachedCriteria.forClass(EppAirport.class);
			if(StringUtils.isNotEmpty(code)){
				criteria.add(Restrictions.eq("code", code));
			}
			if(StringUtils.isNotEmpty(nationality)){
				criteria.add(Restrictions.eq("countryCode", nationality));
			}
			PaginatedResult<EppAirport> result = this.findAllForPagination(criteria, pageNo, pageSize);
			return result;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	} 
}
