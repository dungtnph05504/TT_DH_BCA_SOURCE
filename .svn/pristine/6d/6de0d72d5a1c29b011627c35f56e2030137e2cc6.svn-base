package com.nec.asia.nic.comp.ws.log.service;

import java.util.List;

import com.nec.asia.nic.comp.trans.domain.BaseModelSingle;
import com.nec.asia.nic.comp.ws.log.domain.EppWsLog;
import com.nec.asia.nic.framework.PaginatedResult;
import com.nec.asia.nic.framework.service.BusinessService;

public interface EppWsLogService extends BusinessService<EppWsLog, Long>{
	public BaseModelSingle<Boolean> inserDataTable(EppWsLog eppWsLog);
	public List<EppWsLog> getWsLogByKeyId(String keyId);
	public EppWsLog getWsLogById(long id);
	public List<EppWsLog> getWsLogAll();
	public PaginatedResult<EppWsLog> findAllForPagination(int PageNo,int PageSize);
	public PaginatedResult<EppWsLog> findAllForPagination(String keyId,String urlRequest,String logWsDateFrom,String logWsDateTo,String type,int pageNo,int pageSize);
}
