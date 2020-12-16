package com.nec.asia.nic.comp.trans.dao;

import java.util.Date;
import java.util.List;

import com.nec.asia.nic.comp.trans.domain.BaseModelSingle;
import com.nec.asia.nic.comp.trans.domain.ConfigurationWorkflow;
import com.nec.asia.nic.framework.PaginatedResult;
import com.nec.asia.nic.framework.dao.GenericDao;

public interface ConfigurationWorkflowDao extends GenericDao<ConfigurationWorkflow, Long>{
	List<ConfigurationWorkflow> findSiteRepositoryByActive(String siteGroup, String type, Date todayTime, Integer me, Boolean stage);
	List<ConfigurationWorkflow> findSiteRepositoryByNoActive(String siteGroup, String type, Date todayTime);//Bỏ hàm này
	ConfigurationWorkflow findSiteRepositoryByType(String siteGroup, String siteCode, String type);
	BaseModelSingle<ConfigurationWorkflow> findSiteRepositoryBySite(String siteCode, Date todayTime, String type, Boolean stage);
	ConfigurationWorkflow findConfigById(Long id, Boolean stage);
	PaginatedResult<ConfigurationWorkflow> findPaginateBySearch(String configType, String stage, String siteFrom, Date todayTime, int pageNo, int pageSize);
}
