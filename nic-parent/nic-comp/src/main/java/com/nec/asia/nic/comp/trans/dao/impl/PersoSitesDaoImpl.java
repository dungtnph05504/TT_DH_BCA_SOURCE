package com.nec.asia.nic.comp.trans.dao.impl;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.nec.asia.nic.comp.trans.dao.ArchiveCodeDao;
import com.nec.asia.nic.comp.trans.dao.BorderGateDao;
import com.nec.asia.nic.comp.trans.dao.PersoSitesDao;
import com.nec.asia.nic.comp.trans.domain.BorderGate;
import com.nec.asia.nic.comp.trans.domain.EppArchiveCode;
import com.nec.asia.nic.comp.trans.domain.NicDocumentData;
import com.nec.asia.nic.comp.trans.domain.PersoSites;
import com.nec.asia.nic.framework.PaginatedResult;
import com.nec.asia.nic.framework.dao.impl.GenericHibernateDao;

@Repository("persoSitesDao")
public class PersoSitesDaoImpl extends GenericHibernateDao<PersoSites, Long> implements PersoSitesDao{

	@Override
	public List<PersoSites> findAllPersoSites(Long idPerso) {
		try {
			List<PersoSites> list = null;
			DetachedCriteria criteria = DetachedCriteria.forClass(PersoSites.class);
			if(idPerso != null){
				criteria.add(Restrictions.eq("persoId", idPerso));
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
	public PersoSites findPersoSitesById(Long idPerso, String siteId){
		try {
			List<PersoSites> list = null;
			DetachedCriteria criteria = DetachedCriteria.forClass(PersoSites.class);
			if(idPerso != null){
				criteria.add(Restrictions.eq("persoId", idPerso));
			}
			if(StringUtils.isNotEmpty(siteId)){
				criteria.add(Restrictions.eq("siteId", siteId));
			}
			
			list = this.findAll(criteria);
			
			if(list != null && list.size() > 0){
				PersoSites arcCode = list.get(0);
				return arcCode;
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
		}
		
		return null;
	}
	
	@Override
	public PersoSites findPersoSitesByCode(String siteId){
		try {
			List<PersoSites> list = null;
			DetachedCriteria criteria = DetachedCriteria.forClass(PersoSites.class);
			if(StringUtils.isNotEmpty(siteId)){
				criteria.add(Restrictions.eq("siteId", siteId));
			}
			
			list = this.findAll(criteria);
			
			if(list != null && list.size() > 0){
				PersoSites arcCode = list.get(0);
				return arcCode;
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
		}
		
		return null;
	}

	@Override
	public PaginatedResult<PersoSites> findPagiPersoSites(Long idPerso, int pageNo, int pageSize) {
		try {
			PaginatedResult<PersoSites> list = null;
			DetachedCriteria criteria = DetachedCriteria.forClass(PersoSites.class);
			if(idPerso != null){
				criteria.add(Restrictions.eq("persoId", idPerso));
			}
			
			list = this.findAllForPagination(criteria, pageNo, pageSize);
			return list;
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
		}
		return null;
	}

	@Override
	public PersoSites findPersoSitesById(Long id) {
		try {
			List<PersoSites> list = null;
			DetachedCriteria criteria = DetachedCriteria.forClass(PersoSites.class);
			if(id != null){
				criteria.add(Restrictions.eq("id", id));
			}
			
			list = this.findAll(criteria);
			
			if(list != null && list.size() > 0){
				PersoSites arcCode = list.get(0);
				return arcCode;
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
		}
		
		return null;
	}
}
