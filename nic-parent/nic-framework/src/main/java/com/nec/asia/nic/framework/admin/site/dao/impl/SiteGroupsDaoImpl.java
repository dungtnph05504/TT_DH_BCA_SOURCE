package com.nec.asia.nic.framework.admin.site.dao.impl;

import java.util.List;
import java.util.Map;

import javax.persistence.Query;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Session;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.nec.asia.nic.framework.PaginatedResult;
import com.nec.asia.nic.framework.admin.site.dao.SiteGroupsDao;
import com.nec.asia.nic.framework.admin.site.domain.SiteGroups;
import com.nec.asia.nic.framework.dao.DaoException;
import com.nec.asia.nic.framework.dao.impl.GenericHibernateDao;

/**
 * 
 * @author khang
 */

/*
 * Modification History:
 * 
 * 15 Jan 2016 (Tue) : Add functions:
 *  getAllSiteGroups
 *  getSiteGroupsById
 */
@Repository("siteGroupsDao")
public class SiteGroupsDaoImpl extends GenericHibernateDao<SiteGroups, String> implements SiteGroupsDao {

	private static final Log _logger = LogFactory.getLog(SiteGroupsDaoImpl.class);

	@Override
	public PaginatedResult<SiteGroups> getAllSiteGroups(int pageNo, int pageSize) {
		// TODO Auto-generated method stub
		DetachedCriteria criteria = DetachedCriteria.forClass(SiteGroups.class);
		PaginatedResult<SiteGroups> paginatedResult = findAllForPagination(criteria, pageNo, pageSize);
		return paginatedResult;

	}

	@Override
	public SiteGroups getSiteGroupsById(String groupId) {
		// TODO Auto-generated method stub
		try {
			DetachedCriteria criteria = DetachedCriteria.forClass(SiteGroups.class);
			criteria.add(Restrictions.like("groupId", groupId));
			List<SiteGroups> list = (List<SiteGroups>) getHibernateTemplate().findByCriteria(criteria);
			if (list != null && list.size() > 0) {
				return list.get(0);
			}
		} catch (Throwable e) {
			_logger.debug("EXCEPTION OCCURRED IN RETRIEVAL SITE GROUPS >>> ", e);
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public SiteGroups getOnlyListByLevel(String level) {
		try {
			DetachedCriteria criteria = DetachedCriteria.forClass(SiteGroups.class);
			criteria.add(Restrictions.eq("levelNic", level));
			List<SiteGroups> list = (List<SiteGroups>) getHibernateTemplate().findByCriteria(criteria);
			if (list != null && list.size() > 0) {
				return list.get(0);
			}
		} catch (Throwable e) {
			_logger.debug("EXCEPTION OCCURRED IN RETRIEVAL SITE GROUPS >>> ", e);
		}
		return null;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<SiteGroups> getListGroupByLevel(String level) {
		try {
			DetachedCriteria criteria = DetachedCriteria.forClass(SiteGroups.class);
			criteria.add(Restrictions.eq("levelNic", level));
			List<SiteGroups> list = (List<SiteGroups>) getHibernateTemplate().findByCriteria(criteria);
			if (list != null && list.size() > 0) {
				return list;
			}
		} catch (Throwable e) {
			_logger.debug("EXCEPTION OCCURRED IN RETRIEVAL SITE GROUPS >>> ", e);
		}
		return null;
	}

	@Override
	public List<SiteGroups> findAllSiteGroups() {
		try {
			DetachedCriteria criteria = DetachedCriteria.forClass(SiteGroups.class);
			return this.findAll(criteria);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public SiteGroups findSiteGroupsByGroupId(String groupId)
			throws DaoException {
		try {
			
			DetachedCriteria detachedCriteria = DetachedCriteria.forClass(SiteGroups.class);
			if(StringUtils.isNotEmpty(groupId)){
				detachedCriteria.add(Restrictions.eq("groupId", groupId));
			}
			List<SiteGroups> list = this.findAll(detachedCriteria);
			if(list != null && list.size() > 0)
				return list.get(0);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
		}
		return null;
	}

	@Override
	public Map<String, String> findSiteGroupsByQuery(String groupId)
			throws DaoException {
		try {
			StringBuffer str = new StringBuffer("");
			Query query = (Query) this.getSession().createSQLQuery(str.toString()).setParameter(0, groupId);
			
		} catch (Exception e) {
			// TODO: handle exception
		}
		return null;
	}

	@Override
	public List<SiteGroups> findAllSiteGroubs() {
		try {
			return this.findAll();
		} catch (Exception e) {
			// TODO: handle exception
			logger.error(e.getMessage());
			throw e;
		}
	}

}
