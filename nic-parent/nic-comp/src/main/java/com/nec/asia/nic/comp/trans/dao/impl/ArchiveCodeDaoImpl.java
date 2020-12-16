package com.nec.asia.nic.comp.trans.dao.impl;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.nec.asia.nic.comp.trans.dao.ArchiveCodeDao;
import com.nec.asia.nic.comp.trans.dao.BorderGateDao;
import com.nec.asia.nic.comp.trans.domain.BaseModelSingle;
import com.nec.asia.nic.comp.trans.domain.BorderGate;
import com.nec.asia.nic.comp.trans.domain.EppArchiveCode;
import com.nec.asia.nic.comp.trans.domain.NicDocumentData;
import com.nec.asia.nic.comp.ws.log.domain.CreateMessageException;
import com.nec.asia.nic.framework.dao.impl.GenericHibernateDao;

@Repository
public class ArchiveCodeDaoImpl extends GenericHibernateDao<EppArchiveCode, Long> implements ArchiveCodeDao{

	@Override
	public List<EppArchiveCode> findAllEppArchiveCode(String type, int year, String code, int stt) {
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
			return list;
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
		}
		return null;
	}

	@Override
	public BaseModelSingle<Boolean> saveOrUpdateData(EppArchiveCode detail){
		try {
			if(detail != null){
				this.saveOrUpdate(detail);
				return new BaseModelSingle<Boolean>(true, true, null);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return new BaseModelSingle<Boolean>(false, false, CreateMessageException.createMessageException(e) + " - saveOrUpdateData - thất bại.");
		}
		
		return new BaseModelSingle<Boolean>(false, true, null);
	}
	
	@Override
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
	}

	@Override
	public BaseModelSingle<EppArchiveCode> findArchiveCodeByAll(String docType,
			int nYear, String officeCode, int incNo) {
		try {
			List<EppArchiveCode> list = null;
			DetachedCriteria criteria = DetachedCriteria.forClass(EppArchiveCode.class);
			if(StringUtils.isNotEmpty(officeCode)){
				criteria.add(Restrictions.eq("officeCode", officeCode));
			}
			if(StringUtils.isNotEmpty(docType)){
				criteria.add(Restrictions.eq("docType", docType));
			}
			if(nYear > 0){
				criteria.add(Restrictions.eq("nYear", nYear));
			}
			if(incNo > 0){
				criteria.add(Restrictions.eq("incNo", incNo));
			}
			list = this.findAll(criteria);
			
			if(list != null && list.size() > 0){
				EppArchiveCode arcCode = list.get(0);
				return new BaseModelSingle<EppArchiveCode>(arcCode, true, null);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return new BaseModelSingle<EppArchiveCode>(null, false, CreateMessageException.createMessageException(e) + " - findArchiveCodeByAll - thất bại.");
		}
		return new BaseModelSingle<EppArchiveCode>(null, true, null);
	}
}
