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
import com.nec.asia.nic.comp.trans.dao.FlightDao;
import com.nec.asia.nic.comp.trans.dao.FlightRouterDao;
import com.nec.asia.nic.comp.trans.dao.FreeVisaDao;
import com.nec.asia.nic.comp.trans.dao.NicTransactionDao;
import com.nec.asia.nic.comp.trans.domain.BorderGate;
import com.nec.asia.nic.comp.trans.domain.EppAirline;
import com.nec.asia.nic.comp.trans.domain.EppAirport;
import com.nec.asia.nic.comp.trans.domain.EppArchiveCode;
import com.nec.asia.nic.comp.trans.domain.EppFlight;
import com.nec.asia.nic.comp.trans.domain.EppFlightRouter;
import com.nec.asia.nic.comp.trans.domain.EppFreeVisa;
import com.nec.asia.nic.comp.trans.domain.NicDocumentData;
import com.nec.asia.nic.comp.trans.domain.NicTransaction;
import com.nec.asia.nic.framework.PaginatedResult;
import com.nec.asia.nic.framework.dao.impl.GenericHibernateDao;

@Repository("freeVisaDao")
public class FreeVisaDaoImpl extends
	GenericHibernateDao<EppFreeVisa, Long> implements
	FreeVisaDao{

	@Override
	public EppFreeVisa findByCtryCode_Type_PPType(String countryCode, String ppType, String type) {
		try {
			DetachedCriteria detachedCriteria = DetachedCriteria.forClass(getPersistentClass());			
			if(StringUtils.isNotEmpty(countryCode) && StringUtils.isNotEmpty(ppType) && StringUtils.isNotEmpty(type)){
				detachedCriteria.add(Restrictions.eq("countryCode", countryCode));
				detachedCriteria.add(Restrictions.eq("passportType", ppType));
				detachedCriteria.add(Restrictions.eq("type", type));
				List<EppFreeVisa> list = this.findAll(detachedCriteria);
				if(list != null && list.size() > 0)
					return list.get(0);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public PaginatedResult<EppFreeVisa> findPaginatedByVisa(Integer status,
			String country, int pageNo, int pageSize) {
		try {
			DetachedCriteria detachedCriteria = DetachedCriteria.forClass(getPersistentClass());			
			if(StringUtils.isNotEmpty(country)){
				detachedCriteria.add(Restrictions.eq("countryCode", country));				
			}
			if(status != -1){
				detachedCriteria.add(Restrictions.eq("status", status));				
			}
			PaginatedResult<EppFreeVisa> pr = this.findAllForPagination(detachedCriteria, pageNo, pageSize);
			return pr;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	} 
}
