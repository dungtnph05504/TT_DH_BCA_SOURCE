package com.nec.asia.nic.framework.admin.site.service;

import java.util.List;

import com.nec.asia.nic.framework.PaginatedResult;
import com.nec.asia.nic.framework.admin.site.domain.SiteGroups;
import com.nec.asia.nic.framework.admin.site.domain.SiteRepository;
import com.nec.asia.nic.framework.dao.DaoException;
import com.nec.asia.nic.framework.service.BusinessService;

/**
 * @author Tue
 *
 */
public interface SiteService extends BusinessService<SiteGroups, String> {
	
	public PaginatedResult<SiteGroups> getAllSiteGroups(int pageNo,int pageSize) throws DaoException;
	public PaginatedResult<SiteRepository> getAllSiteRepoByGroupId(String groupId,int pageNo,int pageSize) throws DaoException;
	
	public String updateSiteGroup(SiteGroups siteGroup) throws DaoException;
	public String delSiteGroup(String groupId) throws DaoException;
	
	public SiteRepository getSiteRepoById(String siteId) throws DaoException;
	public String updateSiteRepository(SiteRepository siteRepository) throws DaoException;
	public String delSiteRepository(String siteId) throws DaoException;
	
	public SiteGroups getSiteGroupById(String groupId) throws DaoException;
	public SiteGroups findSiteGroupsByGroupId(String groupId);
	
	public List<SiteRepository> findAllByGroupId(String groupId) throws DaoException;
	
	public List<SiteRepository> findAllSite() throws DaoException;
	
	public PaginatedResult<SiteRepository> search(String groupId, String likeName, int pageNo, int pageSize) throws DaoException;

	public String getSiteRepoAuthority(String siteId, String defaultValOnExceptionOrNull) throws DaoException;
	
	public SiteGroups getOnlyListByLevel(String level);
	public List<SiteGroups> getListGroupByLevel(String level);
	public List<SiteRepository> findAllByGroupId1(String groupId);
	public List<SiteRepository> findByAllGroup();
	
	public List<SiteRepository> findAllByActive(String active);
	
	public List<SiteGroups> findAllSiteGroubs();
}
