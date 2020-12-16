package com.nec.asia.nic.comp.trans.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nec.asia.nic.comp.trans.dao.ConfigurationApiDao;
import com.nec.asia.nic.comp.trans.domain.ConfigurationApi;
import com.nec.asia.nic.comp.trans.domain.ConfigurationWorkflow;
import com.nec.asia.nic.comp.trans.dto.ConfigurationApiDto;
import com.nec.asia.nic.comp.trans.service.ConfigurationApiService;
import com.nec.asia.nic.framework.PaginatedResult;
import com.nec.asia.nic.framework.service.impl.DefaultBusinessServiceImpl;

@Service
public class ConfigurationApiServiceImpl extends DefaultBusinessServiceImpl<ConfigurationApi, Long, ConfigurationApiDao> implements ConfigurationApiService{

	@Autowired
	ConfigurationApiDao dao;
	
	@Override
	public PaginatedResult<ConfigurationApiDto> findListApiBySearch(String type,
			String name, Boolean stage, int pageNo, int pageSize) {
		try {
			Integer intType = null;
			if(StringUtils.isNotEmpty(type)){
				intType = Integer.parseInt(type);
			}
			PaginatedResult<ConfigurationApi> result = this.dao.findListApiBySearch(intType, name, stage, pageNo, pageSize);
			if(result != null){
				List<ConfigurationApiDto> list = new ArrayList<ConfigurationApiDto>();
				int i = (pageNo - 1) * pageSize;
				for(ConfigurationApi api : result.getRows()){
					ConfigurationApiDto dto = new ConfigurationApiDto();
					i++;
					dto.setId(api.getId());
					dto.setUriApi(api.getUriApi());
					dto.setNameApi(api.getNameApi());
					dto.setDescription(api.getDescription());
					dto.setStt(i);
					dto.setCloseAll(api.getCloseAll() ? "Y" : "N");
					dto.setCloseMember(api.getCloseMember());
					dto.setType(api.getType());
					list.add(dto);
				}
				PaginatedResult<ConfigurationApiDto> pr = new PaginatedResult<>(result.getTotal(), pageNo, list);
				return pr;
			}
		} catch (Throwable e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public ConfigurationApi findConfigApiById(Long id, Boolean stage) {
		return this.dao.findConfigApiById(id, stage);
	}

	@Override
	public Boolean saveOrUpdateConfig(ConfigurationApi cf) {
		try {
			dao.saveOrUpdate(cf);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public ConfigurationApi findConfigApiByName(String name, Boolean stage) {
		// TODO Auto-generated method stub
		return this.dao.findConfigApiByName(name, stage);
	}

	@Override
	public ConfigurationApi findConfigApiByUrl(String url, String name) {
		// TODO Auto-generated method stub
		return this.dao.findConfigApiByName(url, name);
	}

}
