package com.nec.asia.nic.comp.trans.dao.impl;

import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.nec.asia.nic.comp.trans.dao.EppPersonAttchmntDao;
import com.nec.asia.nic.comp.trans.dao.EppPersonDao;
import com.nec.asia.nic.comp.trans.dao.EppPersonFamilyDao;
import com.nec.asia.nic.comp.trans.domain.EppPerson;
import com.nec.asia.nic.comp.trans.domain.EppPersonAttchmnt;
import com.nec.asia.nic.comp.trans.domain.EppPersonFamily;
import com.nec.asia.nic.framework.dao.impl.GenericHibernateDao;

@Repository("eppPersonAttchmntDao")
public class EppPersonAttchmntDaoImpl extends GenericHibernateDao<EppPersonAttchmnt, Long> implements EppPersonAttchmntDao{

	@Override
	public List<EppPersonAttchmnt> findAllEppPersonAttchmnt(String[] type, String serialNo) {
		try {
			List<EppPersonAttchmnt> list = null;
			DetachedCriteria criteria = DetachedCriteria.forClass(EppPerson.class);
			if(type != null){
				List<String> docTypeList = Arrays.asList(type);
				criteria.add(Restrictions.in("type_", docTypeList));
			}
			if(StringUtils.isNotEmpty(serialNo)){
				criteria.add(Restrictions.eq("serialNo", serialNo));
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
	public Boolean saveOrUpdateData(EppPersonAttchmnt detail){
		try {
			if(detail != null){
				this.saveOrUpdate(detail);
				return true;
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
		}
		
		return false;
	}

	@Override
	public List<EppPersonAttchmnt> findAllEppPersonAttchmnt1(String[] type,
			Long personId) {
		try {
			List<EppPersonAttchmnt> list = null;
			DetachedCriteria criteria = DetachedCriteria.forClass(EppPersonAttchmnt.class);
			if (!(type == null && personId == null)) {
				if(type != null){
					List<String> docTypeList = Arrays.asList(type);
					criteria.add(Restrictions.in("type_", docTypeList));
				}
				if(personId != null){
					criteria.add(Restrictions.eq("personId", personId));
				}
				list = this.findAll(criteria);
			}
			return list;
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
		}
		return null;
	}
	
	/*@Override
	public int amountArchiveCode(String type, int year, String code, int stt){
		try {
			List<EppArchiveCode> list = null;
			DetachedCriteria criteria = DetachedCriteria.forClass(EppArchiveCode.class);
			if(StringUtils.isNotEmpty(code)){
				criteria.add(Restrictions.eq("officeCode", code));
			}
			if(StringUtils.isNotEmpty(type)){
				criteria.add(Restrictions.eq("docType", type));
			}
			if(year > 0){
				criteria.add(Restrictions.eq("nYear", year));
			}
			if(stt > 0){
				criteria.add(Restrictions.eq("incNo", stt));
			}
			list = this.findAll(criteria);
			
			if(list != null && list.size() > 0){
				EppArchiveCode arcCode = list.get(0);
				return arcCode.getCount();
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
		}
		
		return 0;
	}*/
}
