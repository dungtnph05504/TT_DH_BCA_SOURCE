package com.nec.asia.nic.comp.trans.dao.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.ResultTransformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.nec.asia.nic.comp.trans.dao.TemplatePassportDao;
import com.nec.asia.nic.comp.trans.domain.NicTransaction;
import com.nec.asia.nic.comp.trans.domain.TemplatePassport;
import com.nec.asia.nic.framework.PaginatedResult;
import com.nec.asia.nic.framework.dao.DaoException;
import com.nec.asia.nic.framework.dao.impl.GenericHibernateDao;

@Repository("templatePassportDao")
public class TemplatePassportImpl extends
		GenericHibernateDao<TemplatePassport, Long> implements
		TemplatePassportDao {

	@Override
	public PaginatedResult<TemplatePassport> findListPassport(String nation,
			String passportType, int yearIssue, int pageNo, int pageSize) {
		try {
			DetachedCriteria criteria = DetachedCriteria
					.forClass(TemplatePassport.class);
			if (StringUtils.isNotEmpty(nation)) {
				criteria.add(Restrictions.eq("nameNationVie", nation));
			}
			if (StringUtils.isNotEmpty(passportType)) {
				criteria.add(Restrictions.eq("passPortType", passportType));
			}
			if (yearIssue != 0) {
				criteria.add(Restrictions.eq("yearIssue", yearIssue));
			}
			
			PaginatedResult<TemplatePassport> list = this.findAllForPagination(
					criteria, pageNo, pageSize);
			return list;

		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}
	
	

	@Override
	public PaginatedResult<TemplatePassport> findAllListPassport(int pageNo,
			int pageSize) {
		try {

			PaginatedResult<TemplatePassport> list = this.findAllForPagination(
					pageNo, pageSize);
			return list;

		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public Integer saveNewPassport(TemplatePassport templatePassport) {
		try {
			this.saveOrUpdate(templatePassport);
			return 1;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}

	@Override
	public String getLastIdPassport() {
		Session session = null;
		String id = null;
		try {
			session = getHibernateTemplate().getSessionFactory().openSession();
			Query q = session.getNamedQuery("getLastIdPassport");
			List<String> resultList = q.list();
			id = resultList.get(0);
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			session.close();
		}
		return id;

	}
	
	@Override
	public List<String> getCodeNation() {
		Session session = null;
		List<String> resultList = null;
		try {
			session = this.openSession();
			session = getHibernateTemplate().getSessionFactory().openSession();
			Query q = session.getNamedQuery("getNationName");
			resultList = q.list();
		} catch (Exception e) {
			e.printStackTrace();
		} finally{
			session.close();
		}
		return resultList;
	}
	
	@Override
	public List<String> getCodeNationName(String codeValue) {
		Session session = null;
		List<String> resultList = null;
		try {
			session = this.openSession();
			session = getHibernateTemplate().getSessionFactory().openSession();
			Query q = session.getNamedQuery("getCodeNation");
			q.setString("namenation", codeValue);
			resultList = q.list();
		} catch (Exception e) {
			e.printStackTrace();
		} finally{
			session.close();
		}
		return resultList;
	}
	
	@Override
	public List<String> getNationName() {
		Session session = null;
		List<String> resultList = null;
		try {
			session = this.openSession();
			session = getHibernateTemplate().getSessionFactory().openSession();
			Query q = session.getNamedQuery("getNationName");
			resultList = q.list();
		} catch (Exception e) {
			e.printStackTrace();
		} finally{
			session.close();
		}
		return resultList;
	}

	@Override
	public List<TemplatePassport> findListByTemplatePassportId(
			String templatePassportId) {

		try {
			DetachedCriteria detachedCriteria = DetachedCriteria
					.forClass(getPersistentClass());
			if (StringUtils.isNotEmpty(templatePassportId)) {
				detachedCriteria.add(Restrictions.eq("id",
						Long.parseLong(templatePassportId)));
			}
			List<TemplatePassport> list = this.findAll(detachedCriteria);
			return list;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ArrayList<TemplatePassport>();
	}

	@Override
	public Integer updatePassport(TemplatePassport passport) {
		try {

			this.saveOrUpdate(passport);
			return 1;
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
	}



	

}
