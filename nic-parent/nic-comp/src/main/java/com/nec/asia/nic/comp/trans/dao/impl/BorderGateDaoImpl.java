package com.nec.asia.nic.comp.trans.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.nec.asia.nic.comp.trans.dao.BorderGateDao;
import com.nec.asia.nic.comp.trans.domain.BorderGate;
import com.nec.asia.nic.comp.trans.domain.EppAirline;
import com.nec.asia.nic.comp.trans.domain.NicDocumentData;
import com.nec.asia.nic.comp.trans.dto.AirlineDto;
import com.nec.asia.nic.comp.trans.utils.HelperClass;
import com.nec.asia.nic.comp.trans.utils.RegistrationConstants;
import com.nec.asia.nic.framework.PaginatedResult;
import com.nec.asia.nic.framework.dao.impl.GenericHibernateDao;

@Repository
public class BorderGateDaoImpl extends GenericHibernateDao<BorderGate, Long> implements BorderGateDao{

	@Override
	public List<BorderGate> findAllBorderGate(String code) {
		try {
			List<BorderGate> list = null;
			DetachedCriteria criteria = DetachedCriteria.forClass(BorderGate.class);
			if(StringUtils.isNotEmpty(code)){
				criteria.add(Restrictions.eq("code", code));
			}
			list = this.findAll(criteria);
			return list;
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
		}
		return null;
	}

	@Override
	public List<BorderGate> findBorderGateBySync(String syncData) {
		try {
			DetachedCriteria detachedCriteria = DetachedCriteria.forClass(BorderGate.class);
			if(StringUtils.isNotEmpty(syncData)){
				detachedCriteria.add(Restrictions.eq("syncDataFlag", syncData));
			}
			List<BorderGate> list = this.findAll(detachedCriteria);
			if(list != null && list.size() > 0)
				return list;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public List<BorderGate> findAllBorderGate() {
		try {
			List<BorderGate> list = null;
			DetachedCriteria criteria = DetachedCriteria.forClass(BorderGate.class);
			list = this.findAll(criteria);
			return list;
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
		}
		return null;
	}
	
	@Override
	public PaginatedResult<BorderGate> findAllBorderGate(String code, int pageNo, int pageSize) {
		try {
			DetachedCriteria criteria = DetachedCriteria.forClass(BorderGate.class);
			if(StringUtils.isNotEmpty(code)){
				criteria.add(Restrictions.eq("code", code));
			}

			PaginatedResult<BorderGate> result = this.findAllForPagination(criteria, pageNo, pageSize);
			return result;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	} 
	


}
