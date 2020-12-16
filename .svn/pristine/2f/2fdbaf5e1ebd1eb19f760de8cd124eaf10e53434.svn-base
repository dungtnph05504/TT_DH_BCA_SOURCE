package com.nec.asia.nic.comp.ws.log.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nec.asia.nic.comp.trans.domain.BaseModelSingle;
import com.nec.asia.nic.comp.ws.log.dao.EppWsLogDao;
import com.nec.asia.nic.comp.ws.log.domain.CreateMessageException;
import com.nec.asia.nic.comp.ws.log.domain.EppWsLog;
import com.nec.asia.nic.comp.ws.log.service.EppWsLogService;
import com.nec.asia.nic.framework.PaginatedResult;
import com.nec.asia.nic.framework.service.impl.DefaultBusinessServiceImpl;

@Service("eppWsLogService")
public class EppWsLogServiceImpl extends DefaultBusinessServiceImpl<EppWsLog, Long, EppWsLogDao> implements EppWsLogService{

	@Autowired
	private EppWsLogDao dao;
	
	@Override
	public BaseModelSingle<Boolean> inserDataTable(EppWsLog eppWsLog) {
		try {
			return this.dao.insertDataTable(eppWsLog);
		} catch (Exception e) {
			e.printStackTrace();
			return new BaseModelSingle<Boolean>(false, false,  CreateMessageException.createMessageException(e));
		}
	}
	@Override
	public EppWsLog getWsLogById(long id) {
		try {
			EppWsLog listWsLog = this.dao.getWsLogById(id);
			return listWsLog;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new EppWsLog();
	}
	@Override
	public List<EppWsLog> getWsLogByKeyId(String keyId) {
		try {
			List<EppWsLog> listWsLog = this.dao.getListWsLogByKeyId(keyId);
			return listWsLog;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ArrayList<EppWsLog>();
	}
	@Override
	public List<EppWsLog> getWsLogAll(){
		try {
			List<EppWsLog> listWsLog=this.dao.getWsLogAll();
			return listWsLog;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return new ArrayList<EppWsLog>();
	}
    @Override
    public PaginatedResult<EppWsLog> findAllForPagination(int PageNo,int PageSize){
    	try {
    		PaginatedResult<EppWsLog> pag=this.dao.findAllForPagination(PageNo, PageSize);
    		return pag;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
    	return new PaginatedResult<EppWsLog>();
    	    }
    @Override
    public PaginatedResult<EppWsLog> findAllForPagination(String keyId,String urlRequest,String logWsDateFrom,String logWsDateTo,String type,int pageNo,int pageSize){
    	try {
    		PaginatedResult<EppWsLog> pag=this.dao.findAllForPagination(keyId,urlRequest,logWsDateFrom,logWsDateTo,type,pageNo,pageSize);
    		return pag;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
    	return new PaginatedResult<EppWsLog>();
    	    }
}
