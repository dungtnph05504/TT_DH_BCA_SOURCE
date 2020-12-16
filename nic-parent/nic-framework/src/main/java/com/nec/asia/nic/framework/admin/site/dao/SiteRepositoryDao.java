package com.nec.asia.nic.framework.admin.site.dao;

import java.util.List;

import org.hibernate.criterion.Order;

import com.nec.asia.nic.framework.PaginatedResult;
import com.nec.asia.nic.framework.admin.site.domain.SiteRepository;
import com.nec.asia.nic.framework.dao.DaoException;
import com.nec.asia.nic.framework.dao.GenericDao;

/**
 * 
 * @author khang
 */

/*
 * Modification History:
 * 
 * 18 Jan 2016 (Tue) : Add functions:
 *  getAllSiteRepoByGroupId
 */
public interface SiteRepositoryDao extends GenericDao<SiteRepository, String> {

	List<SiteRepository> findByAll() throws DaoException;
    SiteRepository findBySiteId(String siteId) throws DaoException;
    List<SiteRepository> findByGroupId(String groupId) throws DaoException;
    List<SiteRepository> findByGroupId1(String groupId); 
    List<SiteRepository> findByAllGroup();
    List<SiteRepository> findById(String groupId, String siteId) throws DaoException;
    public PaginatedResult<SiteRepository> getAllSiteRepoByGroupId(String groupId,int pageNo, int pageSize, Order... orders) throws DaoException;
    public PaginatedResult<SiteRepository> search(String groupId, String likeName, int pageNo, int pageSize) throws DaoException;
    List<SiteRepository> findAllByActive(String active);
}
