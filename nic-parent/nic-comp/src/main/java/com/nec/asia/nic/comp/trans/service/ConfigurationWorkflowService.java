package com.nec.asia.nic.comp.trans.service;

import java.util.Date;
import java.util.List;

import com.nec.asia.nic.comp.trans.domain.BaseModelSingle;
import com.nec.asia.nic.comp.trans.domain.ConfigurationWorkflow;
import com.nec.asia.nic.comp.trans.dto.NicUploadJobDto;
import com.nec.asia.nic.framework.PaginatedResult;
import com.nec.asia.nic.framework.service.BusinessService;

public interface ConfigurationWorkflowService extends BusinessService<ConfigurationWorkflow, Long>{
	List<ConfigurationWorkflow> findSiteRepositoryByActive(String siteGroup, String type, Date todayTime, Integer me, Boolean stage);
	ConfigurationWorkflow findSiteRepositoryByType(String siteGroup, String siteCode, String type);
	
	BaseModelSingle<ConfigurationWorkflow> findSiteRepositoryBySite(String siteCode, Date todayTime, String type, Boolean stage);
	
	PaginatedResult<NicUploadJobDto> findPaginateBySearch(String configType, String stage, String siteFrom, Date todayTime, int pageNo, int pageSize);

	Boolean saveOrUpdateConfig(ConfigurationWorkflow cf);
	public ConfigurationWorkflow findConfigById(Long id, Boolean stage);
}
