package com.nec.asia.nic.framework.admin.site.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.nec.asia.nic.framework.PaginatedResult;
import com.nec.asia.nic.framework.admin.site.dao.SiteRepositoryDao;
import com.nec.asia.nic.framework.admin.site.domain.SiteRepository;
import com.nec.asia.nic.framework.dao.DaoException;
import com.nec.asia.nic.framework.dao.impl.GenericHibernateDao;

/**
 * @author khang
 */
/*
 * Modification History:
 * 
 * 18 Jan 2016 (Tue) : Add functions:
 *  getAllSiteRepoByGroupId
 */
@Repository("siteRepositoryDao")
public class SiteRepositoryDaoImpl extends GenericHibernateDao<SiteRepository, String> implements SiteRepositoryDao {

    private static final Log _logger = LogFactory.getLog(SiteRepositoryDaoImpl.class);

    @Override
    public List<SiteRepository> findByAll() throws DaoException {

        List<SiteRepository> siteList = null;
        DetachedCriteria detachedCriteria = DetachedCriteria.forClass(getPersistentClass());
        siteList = this.findAll(detachedCriteria);
        return siteList;
    }
    
    @Override
    public SiteRepository findBySiteId(String siteId) throws DaoException {

        if (StringUtils.isBlank(siteId)) {
            throw new DaoException("SiteId cannot be empty.");
        }
        List<SiteRepository> siteList = null;
        SiteRepository siteRepository = null;
        DetachedCriteria detachedCriteria = DetachedCriteria.forClass(getPersistentClass());
        if (StringUtils.isNotEmpty(siteId)) {
            detachedCriteria.add(Restrictions.eq("siteId", siteId));

            siteList = this.findAll(detachedCriteria);
            if (CollectionUtils.isNotEmpty(siteList)) {
                siteRepository = siteList.get(0);
            }

        }
        logger.info("[findBySiteId][{}] size:{}", new Object[] {
            siteId, siteList.size()
        });

        return siteRepository;
    }

    @Override
    public List<SiteRepository> findByGroupId(String groupId) throws DaoException {

        if (StringUtils.isBlank(groupId)) {
            throw new DaoException("SiteId cannot be empty.");
        }
        List<SiteRepository> siteList = new ArrayList<SiteRepository>();

        DetachedCriteria detachedCriteria = DetachedCriteria.forClass(getPersistentClass());
        if (StringUtils.isNotEmpty(groupId)) {
            detachedCriteria.add(Restrictions.eq("groupId", groupId));

            List<SiteRepository> resultList = this.findAll(detachedCriteria);
            siteList.addAll(resultList);
        }
        logger.info("[findByGroupId][{}] size:{}", new Object[] {
            groupId, siteList.size()
        });
        return siteList;
    }

    @Override
    public List<SiteRepository> findById(String groupId, String siteId) throws DaoException {

        if (StringUtils.isBlank(groupId) && StringUtils.isBlank(siteId)) {
            throw new DaoException("GroupId and SiteId cannot be empty.");
        }
        List<SiteRepository> siteList = new ArrayList<SiteRepository>();

        DetachedCriteria detachedCriteria = DetachedCriteria.forClass(getPersistentClass());
        if (StringUtils.isNotBlank(groupId)) {
            detachedCriteria.add(Restrictions.eq("groupId", groupId));
        }

        if (StringUtils.isNotBlank(siteId)) {
            detachedCriteria.add(Restrictions.eq("siteId", siteId));
        }

        List<SiteRepository> resultList = this.findAll(detachedCriteria);
        siteList.addAll(resultList);
        logger.info("[findByGroupId][{}] size:{}", new Object[] {
            groupId, siteList.size()
        });
        return siteList;
    }
    
	@Override
	public PaginatedResult<SiteRepository> getAllSiteRepoByGroupId(String groupId, int pageNo, int pageSize, Order... orders) throws DaoException {
		// TODO Auto-generated method stub
		DetachedCriteria criteria = DetachedCriteria.forClass(SiteRepository.class);
		criteria.add(Restrictions.eq("siteGroups.groupId", groupId));
		criteria.addOrder(Order.asc("siteId"));
		
		return findAllForPagination(criteria, pageNo, pageSize,orders);
	}

	@Override
	public PaginatedResult<SiteRepository> search(String groupId, String likeName, int pageNo, int pageSize) throws DaoException {
		// TODO Auto-generated method stub
		DetachedCriteria criteria = DetachedCriteria.forClass(SiteRepository.class);
		criteria.add(Restrictions.eq("siteGroups.groupId", groupId));
		String[] site = likeName.split("=");
		if (site[0].equals("siteId")) {
			criteria.add(Restrictions.eq("siteId", site[1]));
		}else{
			if(StringUtils.isNotEmpty(likeName) && !likeName.equals("*")) {
				criteria.add(Restrictions.ilike("siteName", likeName, MatchMode.ANYWHERE));
			}
		}
		
		criteria.addOrder(Order.asc("siteId"));
		try {
			return findAllForPagination(criteria, pageNo, pageSize);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new PaginatedResult<SiteRepository>(0, 0, new ArrayList<SiteRepository>());
	}

	@Override
	public List<SiteRepository> findByGroupId1(String groupId) {
		try {
			DetachedCriteria criteria = DetachedCriteria.forClass(SiteRepository.class);
			criteria.add(Restrictions.eq("siteGroups.groupId", groupId));
			criteria.addOrder(Order.asc("siteId"));
			@SuppressWarnings("unchecked")
			List<SiteRepository> list = (List<SiteRepository>) getHibernateTemplate().findByCriteria(criteria);
			if(list != null && list.size() > 0){
				return list;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public List<SiteRepository> findByAllGroup() {
		try {
			DetachedCriteria criteria = DetachedCriteria.forClass(SiteRepository.class);
			criteria.addOrder(Order.asc("siteId"));
			@SuppressWarnings("unchecked")
			List<SiteRepository> list = (List<SiteRepository>) getHibernateTemplate().findByCriteria(criteria);
			if(list != null && list.size() > 0){
				return list;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@Override
	public List<SiteRepository> findAllByActive(String active) {
		try {
			DetachedCriteria criteria = DetachedCriteria.forClass(SiteRepository.class);
			if(StringUtils.isNotEmpty(active)){
				criteria.add(Restrictions.eq("activeIndicator", active));
			}
			criteria.addOrder(Order.asc("siteId"));
			@SuppressWarnings("unchecked")
			List<SiteRepository> list = (List<SiteRepository>) getHibernateTemplate().findByCriteria(criteria);
			if(list != null && list.size() > 0){
				return list;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}
