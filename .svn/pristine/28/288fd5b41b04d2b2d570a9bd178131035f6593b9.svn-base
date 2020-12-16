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
import com.nec.asia.nic.comp.trans.dao.NicTransactionDao;
import com.nec.asia.nic.comp.trans.domain.BorderGate;
import com.nec.asia.nic.comp.trans.domain.EppAirline;
import com.nec.asia.nic.comp.trans.domain.EppAirport;
import com.nec.asia.nic.comp.trans.domain.EppArchiveCode;
import com.nec.asia.nic.comp.trans.domain.EppFlight;
import com.nec.asia.nic.comp.trans.domain.EppFlightRouter;
import com.nec.asia.nic.comp.trans.domain.NicDocumentData;
import com.nec.asia.nic.comp.trans.domain.NicTransaction;
import com.nec.asia.nic.framework.PaginatedResult;
import com.nec.asia.nic.framework.dao.impl.GenericHibernateDao;

@Repository("flightDao")
public class FlightDaoImpl extends
	GenericHibernateDao<EppFlight, Long> implements
	FlightDao{

	@Override
	public EppFlight findByFlightno_Type(String flightNo, String type) {
		try {
			DetachedCriteria detachedCriteria = DetachedCriteria.forClass(getPersistentClass());			
			if(StringUtils.isNotEmpty(flightNo) && StringUtils.isNotEmpty(type)){
				detachedCriteria.add(Restrictions.eq("flightNo", flightNo));
				detachedCriteria.add(Restrictions.eq("type", type));
				List<EppFlight> list = this.findAll(detachedCriteria);
				if(list != null && list.size() > 0)
					return list.get(0);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public PaginatedResult<EppFlight> findPaginateBySearch(
			String flightNo, String flightRouter, String airlineNo, int pageNo,
			int pageSize) {
		try {
			DetachedCriteria detachedCriteria = DetachedCriteria.forClass(getPersistentClass());			
			if(StringUtils.isNotEmpty(flightNo)){
				detachedCriteria.add(Restrictions.eq("flightNo", flightNo));
			}
			if(StringUtils.isNotEmpty(flightRouter)){
				detachedCriteria.add(Restrictions.eq("flightRouterCode", flightRouter));
			}
			if(StringUtils.isNotEmpty(airlineNo)){
				detachedCriteria.add(Restrictions.eq("airlineCode", airlineNo));
			}
			PaginatedResult<EppFlight> pr = this.findAllForPagination(detachedCriteria, pageNo, pageSize);
			return pr;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public List<EppFlight> findByFlightRouter(String routerCode) {
		try {
			DetachedCriteria detachedCriteria = DetachedCriteria.forClass(getPersistentClass());			
			if(StringUtils.isNotEmpty(routerCode)){
				detachedCriteria.add(Restrictions.eq("flightRouterCode", routerCode));
			}
			List<EppFlight> list = this.findAll(detachedCriteria);
			if(list != null && list.size() > 0)
				return list;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	} 
}
