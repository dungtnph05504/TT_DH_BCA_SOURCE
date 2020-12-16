package com.nec.asia.nic.comp.trans.service;

import com.nec.asia.nic.comp.trans.domain.ConfigurationApi;
import com.nec.asia.nic.comp.trans.domain.ConfigurationWorkflow;
import com.nec.asia.nic.comp.trans.dto.ConfigurationApiDto;
import com.nec.asia.nic.framework.PaginatedResult;
import com.nec.asia.nic.framework.service.BusinessService;

public interface ConfigurationApiService extends BusinessService<ConfigurationApi, Long>{
	PaginatedResult<ConfigurationApiDto> findListApiBySearch(String type, String name, Boolean stage, int pageNo, int pageSize);
	ConfigurationApi findConfigApiById(Long id, Boolean stage);
	Boolean saveOrUpdateConfig(ConfigurationApi cf);
	ConfigurationApi findConfigApiByName(String name, Boolean stage);
	ConfigurationApi findConfigApiByUrl(String url, String name);
}
