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
import com.nec.asia.nic.comp.trans.dao.PurposeDao;
import com.nec.asia.nic.comp.trans.domain.BorderGate;
import com.nec.asia.nic.comp.trans.domain.EppAirline;
import com.nec.asia.nic.comp.trans.domain.EppAirport;
import com.nec.asia.nic.comp.trans.domain.EppArchiveCode;
import com.nec.asia.nic.comp.trans.domain.EppFlight;
import com.nec.asia.nic.comp.trans.domain.EppFlightRouter;
import com.nec.asia.nic.comp.trans.domain.EppFreeVisa;
import com.nec.asia.nic.comp.trans.domain.EppPurpose;
import com.nec.asia.nic.comp.trans.domain.NicDocumentData;
import com.nec.asia.nic.comp.trans.domain.NicTransaction;
import com.nec.asia.nic.framework.PaginatedResult;
import com.nec.asia.nic.framework.dao.impl.GenericHibernateDao;

@Repository("purposeDao")
public class PurposeDaoImpl extends
	GenericHibernateDao<EppPurpose, Long> implements
	PurposeDao{

	@Override
	public EppPurpose findByCode(String code) {
		try {
			DetachedCriteria detachedCriteria = DetachedCriteria.forClass(getPersistentClass());			
			if(StringUtils.isNotEmpty(code)){
				detachedCriteria.add(Restrictions.eq("purposeCode", code));
				List<EppPurpose> list = this.findAll(detachedCriteria);
				if(list != null && list.size() > 0)
					return list.get(0);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public PaginatedResult<EppPurpose> findPaginateByPurpose(String code,
			Integer status, int pageNo, int pageSize) {
		try {
			DetachedCriteria detachedCriteria = DetachedCriteria.forClass(getPersistentClass());			
			if(StringUtils.isNotEmpty(code)){
				detachedCriteria.add(Restrictions.eq("purposeCode", code));
			}
			if(status != -1){
				detachedCriteria.add(Restrictions.eq("status", status));
			}
			PaginatedResult<EppPurpose> pr = this.findAllForPagination(detachedCriteria, pageNo, pageSize);
			return pr;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	} 
}
