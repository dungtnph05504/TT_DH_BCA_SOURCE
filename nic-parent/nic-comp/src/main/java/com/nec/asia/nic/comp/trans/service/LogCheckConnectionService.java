package com.nec.asia.nic.comp.trans.service;

import java.util.List;

import com.nec.asia.nic.comp.trans.domain.BaseModelSingle;
import com.nec.asia.nic.comp.trans.domain.LogCheckConnection;
import com.nec.asia.nic.framework.PaginatedResult;
import com.nec.asia.nic.framework.service.BusinessService;

public interface LogCheckConnectionService extends
		BusinessService<LogCheckConnection, Long> {

	public BaseModelSingle<Boolean> saveOrUpdateLogCheckConnection(LogCheckConnection logCheckCon);
	public BaseModelSingle<LogCheckConnection> findLogBySiteCode(String siteCode);
	public PaginatedResult<LogCheckConnection> findAllForPagination(int PageNo, int PageSize);
	public List<LogCheckConnection> findAllLog();
	public List<LogCheckConnection> findAllAndOrder(String order);
}
