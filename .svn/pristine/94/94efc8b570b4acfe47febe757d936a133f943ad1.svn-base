package com.nec.asia.nic.comp.trans.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.nec.asia.nic.comp.trans.dao.EppPersonDao;
import com.nec.asia.nic.comp.trans.dao.EppPersonFamilyDao;
import com.nec.asia.nic.comp.trans.domain.BaseModelList;
import com.nec.asia.nic.comp.trans.domain.BaseModelSingle;
import com.nec.asia.nic.comp.trans.domain.EppPerson;
import com.nec.asia.nic.comp.trans.domain.EppPersonFamily;
import com.nec.asia.nic.comp.ws.log.domain.CreateMessageException;
import com.nec.asia.nic.framework.dao.impl.GenericHibernateDao;

@Repository("eppPersonFamilyDao")
public class EppPersonFamilyDaoImpl extends GenericHibernateDao<EppPersonFamily, Long> implements EppPersonFamilyDao{

	@Override
	public List<EppPersonFamily> findAllEppPersonFamily(String searchName) {
		try {
			List<EppPersonFamily> list = null;
			DetachedCriteria criteria = DetachedCriteria.forClass(EppPerson.class);
			if(StringUtils.isNotEmpty(searchName)){
				criteria.add(Restrictions.eq("name", searchName));
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
	public BaseModelSingle<Boolean> saveOrUpdateData(EppPersonFamily detail){
		try {
			if(detail != null){
				this.saveOrUpdate(detail);
				return new BaseModelSingle<Boolean>(true, true, null);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return new BaseModelSingle<Boolean>(false, false,  CreateMessageException.createMessageException(e) + " - saveOrUpdateData: " + detail.getName() + " - thất bại.");
		}
		return new BaseModelSingle<Boolean>(false, true, null);
	}

	@Override
	public BaseModelList<EppPersonFamily> findAllEppPersonFamily1(Long personId) {
		try {
			List<EppPersonFamily> list = null;
			DetachedCriteria criteria = DetachedCriteria.forClass(EppPersonFamily.class);
			if(personId != null){
				criteria.add(Restrictions.eq("subjectPerson", personId));
				list = this.findAll(criteria);
			}
			return new BaseModelList<EppPersonFamily>(list, true, null);
		} catch (Exception e) {
			e.printStackTrace();
			return new BaseModelList<EppPersonFamily>(null, false,  CreateMessageException.createMessageException(e) + " - findAllEppPersonFamily1 - " + personId + " - thất bại.");	
		}
		
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
