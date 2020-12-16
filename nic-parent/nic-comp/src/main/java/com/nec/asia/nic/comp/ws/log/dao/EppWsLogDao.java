package com.nec.asia.nic.comp.ws.log.dao;

import java.util.List;

import com.nec.asia.nic.comp.trans.domain.BaseModelSingle;
import com.nec.asia.nic.comp.ws.log.domain.EppWsLog;
import com.nec.asia.nic.framework.PaginatedResult;
import com.nec.asia.nic.framework.dao.GenericDao;

public interface EppWsLogDao extends GenericDao<EppWsLog, Long>{
	public List<EppWsLog> getListWsLogByKeyId(String keyId); 
	public EppWsLog getWsLogById(long id); 
	public List<EppWsLog> getWsLogAll();
	public BaseModelSingle<Boolean> insertDataTable(EppWsLog eppWsLog);
	public PaginatedResult<EppWsLog> findAllForPagination(int PageNo,int PageSize);
	public PaginatedResult<EppWsLog> findAllForPagination(String keyId,String urlRequest,String logWsDateFrom,String logWsDateTo,String type,int pageNo,int pageSize);
}
