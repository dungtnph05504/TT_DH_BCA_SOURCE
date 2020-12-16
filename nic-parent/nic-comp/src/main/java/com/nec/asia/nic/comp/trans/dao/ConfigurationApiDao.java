package com.nec.asia.nic.comp.trans.dao;

import com.nec.asia.nic.comp.trans.domain.ConfigurationApi;
import com.nec.asia.nic.framework.PaginatedResult;
import com.nec.asia.nic.framework.dao.GenericDao;

public interface ConfigurationApiDao extends GenericDao<ConfigurationApi, Long>{
	PaginatedResult<ConfigurationApi> findListApiBySearch(Integer type, String name, Boolean stage, int pageNo, int pageSize);
	ConfigurationApi findConfigApiById(Long id, Boolean stage);
	ConfigurationApi findConfigApiByName(String name, Boolean stage);
	ConfigurationApi findConfigApiByName(String url, String name);
}
