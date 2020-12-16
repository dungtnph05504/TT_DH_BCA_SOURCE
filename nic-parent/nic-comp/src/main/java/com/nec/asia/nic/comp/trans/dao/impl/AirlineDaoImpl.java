package com.nec.asia.nic.comp.trans.dao.impl;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.nec.asia.nic.comp.trans.dao.AirlineDao;
import com.nec.asia.nic.comp.trans.domain.EppAirline;
import com.nec.asia.nic.comp.trans.dto.AirlineDto;
import com.nec.asia.nic.framework.PaginatedResult;
import com.nec.asia.nic.framework.dao.impl.GenericHibernateDao;

@Repository("airlineDao")
public class AirlineDaoImpl extends
	GenericHibernateDao<EppAirline, Long> implements
	AirlineDao{

	@Override
	public EppAirline findByCode(String code) {
		try {
			DetachedCriteria detachedCriteria = DetachedCriteria.forClass(getPersistentClass());			
			if(StringUtils.isNotEmpty(code)){
				detachedCriteria.add(Restrictions.eq("code", code));
				List<EppAirline> list = this.findAll(detachedCriteria);
				if(list != null && list.size() > 0)
					return list.get(0);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public PaginatedResult<EppAirline> findPaginateBySearch(String code,
			String nationality, int pageNo, int pageSize) {
		try {
			DetachedCriteria criteria = DetachedCriteria.forClass(EppAirline.class);
			if(StringUtils.isNotEmpty(code)){
				criteria.add(Restrictions.eq("code", code));
			}
			if(StringUtils.isNotEmpty(nationality)){
				criteria.add(Restrictions.eq("countryCode", nationality));
			}
			PaginatedResult<EppAirline> result = this.findAllForPagination(criteria, pageNo, pageSize);
			return result;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	} 
}
