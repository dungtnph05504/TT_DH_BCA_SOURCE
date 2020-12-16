package com.nec.asia.nic.comp.trans.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nec.asia.nic.comp.trans.dao.LogCheckConnectionDao;
import com.nec.asia.nic.comp.trans.domain.BaseModelSingle;
import com.nec.asia.nic.comp.trans.domain.LogCheckConnection;
import com.nec.asia.nic.comp.trans.service.LogCheckConnectionService;
import com.nec.asia.nic.comp.ws.log.domain.CreateMessageException;
import com.nec.asia.nic.framework.PaginatedResult;
import com.nec.asia.nic.framework.service.impl.DefaultBusinessServiceImpl;

@Service("logCheckConnectionService")
public class LogCheckConnectionServiceImpl
		extends
		DefaultBusinessServiceImpl<LogCheckConnection, Long, LogCheckConnectionDao>
		implements LogCheckConnectionService {

	@Autowired
	private LogCheckConnectionDao dao;
	
	@Override
	public BaseModelSingle<Boolean> saveOrUpdateLogCheckConnection(
			LogCheckConnection logCheckCon) {
		try {
			return this.dao.saveOrUpdateLogCheckConnection(logCheckCon);
		} catch (Exception e) {
			return new BaseModelSingle<Boolean>(false, false, CreateMessageException.createMessageException(e) + " - saveOrUpdateLogCheckConnection - thất bại");
		}
	}

	@Override
	public BaseModelSingle<LogCheckConnection> findLogBySiteCode(String siteCode) {
		// TODO Auto-generated method stub
		try {
			return this.dao.findLogBySiteCode(siteCode);
		} catch (Exception e) {
			return new BaseModelSingle<LogCheckConnection>(null, false, CreateMessageException.createMessageException(e) + " - findLogBySiteCode - " + siteCode + " - thất bại.");
		}
	}

	@Override
	public PaginatedResult<LogCheckConnection> findAllForPagination(int PageNo,
			int PageSize) {
		try {
    		PaginatedResult<LogCheckConnection> pag = this.dao.findAllForPagination(PageNo, PageSize);
    		return pag;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
    	return new PaginatedResult<LogCheckConnection>();
	}

	@Override
	public List<LogCheckConnection> findAllLog() {
		try {
			return this.dao.findAllLog();
		} catch (Exception e) {
			// TODO: handle exception
		}
		return new ArrayList<LogCheckConnection>();
	}

	@Override
	public List<LogCheckConnection> findAllAndOrder(String order){
		try {
			return this.dao.findAllAndOrder(order);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
		}
		return null;
	}

}
