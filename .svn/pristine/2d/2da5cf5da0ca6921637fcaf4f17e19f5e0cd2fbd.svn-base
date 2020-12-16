package com.nec.asia.nic.framework.admin.site.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nec.asia.nic.framework.PaginatedResult;
import com.nec.asia.nic.framework.admin.site.dao.SiteGroupsDao;
import com.nec.asia.nic.framework.admin.site.dao.SiteRepositoryDao;
import com.nec.asia.nic.framework.admin.site.domain.SiteGroups;
import com.nec.asia.nic.framework.admin.site.domain.SiteRepository;
import com.nec.asia.nic.framework.admin.site.service.SiteService;
import com.nec.asia.nic.framework.dao.DaoException;
import com.nec.asia.nic.framework.service.impl.DefaultBusinessServiceImpl;

@Service("siteService")
public class SiteServiceImpl extends
DefaultBusinessServiceImpl<SiteGroups, String, SiteGroupsDao> implements SiteService {
	
	@Autowired
	private SiteGroupsDao eppSiteGroupsDao;
	
	@Autowired
	private SiteRepositoryDao eppSiteRepositoryDao;

	@Autowired
	public SiteServiceImpl(SiteGroupsDao dao) {
		this.dao = dao;
	}
	
	@Override
	public PaginatedResult<SiteGroups> getAllSiteGroups(int pageNo, int pageSize) throws DaoException {
		// TODO Auto-generated method stub
		return eppSiteGroupsDao.getAllSiteGroups(pageNo, pageSize);
	}

	@Override
	public PaginatedResult<SiteRepository> getAllSiteRepoByGroupId(String groupId, int pageNo, int pageSize) throws DaoException {
		// TODO Auto-generated method stub
		return eppSiteRepositoryDao.getAllSiteRepoByGroupId(groupId, pageNo, pageSize);
	}

	@Override
	public String updateSiteGroup(SiteGroups siteGroup) throws DaoException {
		// TODO Auto-generated method stub
		try {
			eppSiteGroupsDao.saveOrUpdate(siteGroup);
			return "success";
		} catch (Exception e) {
			// TODO Auto-generated catch block
			return "fail";
		}
	}

	@Override
	public String delSiteGroup(String groupId) {
		// TODO Auto-generated method stub
		String status="";
		
		try {
			SiteGroups eppSiteGroupEntity = eppSiteGroupsDao.findById(groupId);
			eppSiteGroupsDao.delete(eppSiteGroupEntity);
			status="success";
		} catch(Exception exp) {
			status="fail";
		}
		
		return status;
	}

	@Override
	public SiteRepository getSiteRepoById(String siteId) {
		// TODO Auto-generated method stub
		try {
			return eppSiteRepositoryDao.findById(siteId);
		} catch (Exception e) {
			throw e;
		}
	}

	@Override
	public String updateSiteRepository(SiteRepository siteRepository) throws DaoException {
		// TODO Auto-generated method stub
		try {
			eppSiteRepositoryDao.saveOrUpdate(siteRepository);
			return "success";
		} catch (Exception e) {
			// TODO Auto-generated catch block
			return "fail";
		}
	}

	@Override
	public String delSiteRepository(String siteId) {
		// TODO Auto-generated method stub
		String status="";
		
		try{
			SiteRepository eppSiteRepoEntity = eppSiteRepositoryDao.findById(siteId);
			eppSiteRepositoryDao.delete(eppSiteRepoEntity);
		status="success";
		} catch(Exception exp) {
			status="fail";
		}
		
		return status;
	}

	@Override
	public List<SiteRepository> findAllByGroupId(String groupId) throws DaoException {
		// TODO Auto-generated method stub
		return eppSiteRepositoryDao.findByGroupId(groupId);
	}

	@Override
	public SiteGroups getSiteGroupById(String groupId) throws DaoException {
		// TODO Auto-generated method stub
		return eppSiteGroupsDao.getSiteGroupsById(groupId);
	}
	
	public PaginatedResult<SiteRepository> search(String groupId, String likeName, int pageNo, int pageSize) throws DaoException {
		// TODO Auto-generated method stub
		return eppSiteRepositoryDao.search(groupId, likeName, pageNo, pageSize);
	}

	@Override
	public String getSiteRepoAuthority(String siteId, String defaultValOnExceptionOrNull) throws DaoException {

		try {
			String value = this.getSiteRepoById(siteId).getAuthority();
			if (value == null) {
				return defaultValOnExceptionOrNull;
			}
			return value;
		} catch (Exception e) {
			return defaultValOnExceptionOrNull;
		}
	}
	
	@Override
	public List<SiteRepository> findAllSite() throws DaoException {
		return eppSiteRepositoryDao.findAll();
	}

	@Override
	public SiteGroups getOnlyListByLevel(String level) {
		try {
			SiteGroups site = eppSiteGroupsDao.getOnlyListByLevel(level);
			return site;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			return null;
		}
	}
	
	@Override
	public List<SiteGroups> getListGroupByLevel(String level) {
		try {
			List<SiteGroups> site = eppSiteGroupsDao.getListGroupByLevel(level);
			return site;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			return null;
		}
	}

	@Override
	public List<SiteRepository> findAllByGroupId1(String groupId) {
		// TODO Auto-generated method stub
		return eppSiteRepositoryDao.findByGroupId1(groupId);
	}

	@Override
	public List<SiteRepository> findByAllGroup() {
		try {
			List<SiteRepository> site = eppSiteRepositoryDao.findByAllGroup();
			return site;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			return null;
		}
	}

	@Override
	public List<SiteRepository> findAllByActive(String active) {
		try {
			List<SiteRepository> site = eppSiteRepositoryDao.findAllByActive(active);
			return site;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public SiteGroups findSiteGroupsByGroupId(String groupId) {
		// TODO Auto-generated method stub
		try {
			return this.dao.findSiteGroupsByGroupId(groupId);
			
		} catch (Exception e) {
			// TODO: handle exception
			logger.error(e.getMessage());
		}
		return null;
	}

	@Override
	public List<SiteGroups> findAllSiteGroubs() {
		try {
			return this.dao.findAllSiteGroubs();
		} catch (Exception e) {			// TODO: handle exception
			logger.error(e.getMessage());
			throw e;
		}
	}
 
}
