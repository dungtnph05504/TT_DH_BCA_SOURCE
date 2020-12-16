package com.nec.asia.nic.framework.admin.site.dao;

import java.util.List;
import java.util.Map;

import com.nec.asia.nic.framework.PaginatedResult;
import com.nec.asia.nic.framework.admin.site.domain.SiteGroups;
import com.nec.asia.nic.framework.dao.DaoException;
import com.nec.asia.nic.framework.dao.GenericDao;

/**
 * @author khang
 */

/*
 * Modification History:
 * 
 * 15 Jan 2016 (Tue) : Add functions:
 *  getAllSiteGroups
 *  getSiteGroupsById
 */
public interface SiteGroupsDao extends GenericDao<SiteGroups, String> {
	
	public PaginatedResult<SiteGroups> getAllSiteGroups(int pageNo, int pageSize) throws DaoException;
	public SiteGroups getSiteGroupsById(String groupId) throws DaoException;
	public SiteGroups findSiteGroupsByGroupId(String groupId) throws DaoException;
	public SiteGroups getOnlyListByLevel(String level);
	public List<SiteGroups> findAllSiteGroups();
	public List<SiteGroups> getListGroupByLevel(String level);
	public Map<String, String> findSiteGroupsByQuery(String groupId) throws DaoException;
	public List<SiteGroups> findAllSiteGroubs();
}
