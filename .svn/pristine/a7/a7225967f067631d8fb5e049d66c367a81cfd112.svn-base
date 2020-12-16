package com.nec.asia.nic.comp.trans.service.impl;

import java.net.InetAddress;
import java.util.Date;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.hibernate.criterion.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.nec.asia.nic.comp.trans.dao.NicTransactionLogDao;
import com.nec.asia.nic.comp.trans.domain.BaseModelList;
import com.nec.asia.nic.comp.trans.domain.BaseModelSingle;
import com.nec.asia.nic.comp.trans.domain.NicTransaction;
import com.nec.asia.nic.comp.trans.domain.NicTransactionLog;
import com.nec.asia.nic.comp.trans.service.TransactionLogService;
import com.nec.asia.nic.comp.trans.service.exception.TransactionServiceException;
import com.nec.asia.nic.comp.ws.log.domain.CreateMessageException;
import com.nec.asia.nic.framework.PageRequest;
import com.nec.asia.nic.framework.PaginatedResult;
import com.nec.asia.nic.framework.admin.code.dao.ParametersDao;
import com.nec.asia.nic.framework.admin.code.domain.Parameters;
import com.nec.asia.nic.framework.admin.code.domain.ParametersId;
import com.nec.asia.nic.framework.service.impl.DefaultBusinessServiceImpl;

/*
 * Modification History:
 * 
 * 28 Jan 2016 chris : set default retry count to 0
 * 09 May 2016 khang : add method addTransactionLog(List<NicTransactionLog>)
 * 24 May 2016 chris : overide method saveOrUpdate for auto retry.
 * 
 */

@Service("nicTransactionLogService")
public class TransactionLogServiceImpl extends
		DefaultBusinessServiceImpl<NicTransactionLog, Long, NicTransactionLogDao>
		implements TransactionLogService {
	
	@Autowired
	private ParametersDao parametersDao;
	
	@Autowired
	public TransactionLogServiceImpl (NicTransactionLogDao dao) {
		this.dao = dao;
	}
	
	public Long save(NicTransactionLog log) {
		Long logId = null;
		try {
			NicTransactionLog dbo = dao.getLatestTransactionLog(log.getRefId(), log.getTransactionStage(), log.getTransactionStatus());
			//if (dbo==null)
			//	dbo = dao.getLatestTransactionLog(log.getRefId(), log.getTransactionStage());
			if (dbo!=null) {
				dbo.setRefId(log.getRefId());
				dbo.setLogCreateTime(log.getLogCreateTime());
				dbo.setTransactionStage(log.getTransactionStage());
				dbo.setTransactionStatus(log.getTransactionStatus());
				dbo.setSiteCode(log.getSiteCode());
				dbo.setStartTime(log.getStartTime());
				dbo.setEndTime(log.getEndTime());
				dbo.setLogInfo(log.getLogInfo());
				dbo.setLogData(log.getLogData());
				dbo.setWkstnId(log.getWkstnId());
				dbo.setOfficerId(log.getOfficerId());
				if (log.getRetryCount()!=null) {
					dbo.setRetryCount(log.getRetryCount());
				} else if (dbo.getRetryCount()!=null) {
					Integer retryCount = dbo.getRetryCount();
					retryCount = retryCount + 1;
					dbo.setRetryCount(retryCount);
				} else {
					dbo.setRetryCount(0);
				}
				logId = dbo.getLogId();
				super.saveOrUpdate(dbo);
			} else {
				if (log.getRetryCount()==null) {
					log.setRetryCount(0);
				} 
				logId = super.save(log);
			}
		} catch (Exception ex) {
			logger.warn("Error in save(NicTransactionLog log), {} ", ex.getMessage());
		}
		return logId;
	}
	
	public void saveOrUpdate(NicTransactionLog log) {
		try {
			this.save(log);
		} catch (Throwable t) {
			logger.info("exception in insert transaction log [{}][{}][{}]: {} ", new Object[] {log.getRefId(), log.getTransactionStage(), log.getTransactionStatus(), t.getMessage()});	
			boolean success = false;
			int count = 0;
			do {
				try {
					count ++;
					Thread.sleep(count * 1000L);
					logger.info("retry#{} for insert transaction log [{}][{}][{}] ", new Object[] {count, log.getRefId(), log.getTransactionStage(), log.getTransactionStatus()});	
					this.save(log);
					success = true;
				} catch (Throwable t2) {
					logger.info("exception in insert transaction log [{}][{}][{}]: {} ", new Object[] {count, log.getRefId(), log.getTransactionStage(), log.getTransactionStatus(), t.getMessage()});	
				}
			} while (!success && count <= 3);
		}
	}
	
	
	@Override
	@Transactional(rollbackFor = java.lang.Throwable.class, propagation = Propagation.REQUIRES_NEW)
	public BaseModelSingle<Long> saveTransactionLog(String transactionId, String transactionStage, String transactionStatus, String officerId, String wkstnId, String siteCode, Date startTime, Date endTime, String logInfo, String logData) {
		try {
			return this.saveTransactionLogWithNin(transactionId, transactionStage, transactionStatus, officerId, wkstnId, siteCode, startTime, endTime, logInfo, logData, null);			
		} catch (Exception e) {
			return new BaseModelSingle<Long>(null, false, CreateMessageException.createMessageException(e) + " - saveTransactionLogWithNin - " + transactionId + " - thất bại.");
		}
	}
	
	@Override
	@Transactional(rollbackFor = java.lang.Throwable.class, propagation = Propagation.REQUIRES_NEW)
	public BaseModelSingle<Long> saveTransactionLogWithNin(String transactionId, String transactionStage, String transactionStatus, String officerId, String wkstnId, String siteCode, Date startTime, Date endTime, String logInfo, String logData, String nin) {
		Long logId = null;
		try {
			if (StringUtils.isBlank(officerId)) {
				officerId = "SYSTEM";
			}
			if (StringUtils.isBlank(wkstnId)) {
				wkstnId = "SYSTEM";
				try {
					java.net.InetAddress localMachine = InetAddress.getLocalHost();
					wkstnId = localMachine.getHostName().toUpperCase();
				} catch (Exception e) {}
			}
			if (StringUtils.isBlank(siteCode)) {
				siteCode = this.getCurrentSiteCodeFromParameter();
			}
		} catch (Exception e) {			
		}
		NicTransactionLog transactionLog = new NicTransactionLog();
		transactionLog.setRefId(transactionId);
		transactionLog.setLogCreateTime(new Date());
		transactionLog.setTransactionStage(transactionStage);
		transactionLog.setTransactionStatus(transactionStatus);
		transactionLog.setSiteCode(siteCode);
		transactionLog.setStartTime(startTime);
		transactionLog.setEndTime(endTime);
		transactionLog.setLogInfo(logInfo);
		transactionLog.setLogData(logData);

		transactionLog.setOfficerId(officerId);
		transactionLog.setWkstnId(wkstnId);
//		transactionLog.setNin(nin);
		synchronized (NicTransactionLog.class) {
			//logId = dao.save(transactionLog);
			try {
				logId = this.save(transactionLog);
				if (logId != null) {
					return new BaseModelSingle<Long>(logId, true, null);
				}
				return new BaseModelSingle<Long>(null, true, null);
			} catch (Exception e) {
				return new BaseModelSingle<Long>(null, false, CreateMessageException.createMessageException(e) + " - saveTransactionLogWithNin - " + transactionId + " - thất bại.");
			}
		}
	}
	
	private String getCurrentSiteCodeFromParameter() {
		String currentSiteCode = "001";
		Parameters parameter = parametersDao.findById(new ParametersId(PARA_SCOPE_SYSTEM, PARA_NAME_SYSTEM_SITE_CODE));
		if (parameter!=null) {
			currentSiteCode = (String) parameter.getParaValue();
		} else {
			logger.warn("No matching Parameter for {} , {} ", PARA_SCOPE_SYSTEM, PARA_NAME_SYSTEM_SITE_CODE);
		}
		return currentSiteCode;
	}
	
	@Override
	public int addTransactionLog(List<NicTransactionLog> transactionLogList) throws Exception {
        int count = 0;
    	try {
    		count = this.dao.addTransactionLog(transactionLogList);
		} catch (Exception e) {
			throw new Exception(e);
		}
		return count;
    }

	@Override
	public PaginatedResult<NicTransactionLog> findAllForPagination(PageRequest pageRequest, NicTransactionLog nicTransactionLog) throws TransactionServiceException {
		PaginatedResult<NicTransactionLog> pr = null;
		try {
			int pageNo = pageRequest.getPageNo();
			int pageSize = pageRequest.getMaxRecords();
			boolean ignoreCase = true;
			boolean enableLike = true;
			Order order = null;
			if (StringUtils.equals(pageRequest.getSortorder(), "asc")) {
				order = Order.asc(pageRequest.getSortname());
			} else {
				order = Order.desc(pageRequest.getSortname());
			}
			pr = dao.findAllForPagination(nicTransactionLog, pageNo, pageSize, ignoreCase, enableLike, order);
		} catch (Exception e) {
			throw new TransactionServiceException(e);
		}

		return pr;
	}

	@Override
	public List<NicTransactionLog> findForListLog(NicTransactionLog nicTransactionLog)
			throws TransactionServiceException {
		List<NicTransactionLog> pr = null;
		try {
			int pageNo = 1;
			int pageSize = 1;
			boolean ignoreCase = true;
			boolean enableLike = true;
			Order order = Order.desc("logId");
			//if (StringUtils.equals(pageRequest.getSortorder(), "asc")) {
				//order = Order.asc(pageRequest.getSortname());
			//} else {
				//order = Order.desc(pageRequest.getSortname());
			//}
			pr = dao.findForListLog(nicTransactionLog, pageNo, pageSize, ignoreCase, enableLike, order);
		} catch (Exception e) {
			throw new TransactionServiceException(e);
		}

		return pr;
	}

	@Override
	public BaseModelSingle<Void> saveOrUpdateTranLog(NicTransactionLog tranLog) {
		try {
			this.dao.saveOrUpdate(tranLog);
			return new BaseModelSingle<Void>(null, true, null);
		} catch (Exception e) {
			return new BaseModelSingle<Void>(null, false, CreateMessageException.createMessageException(e) + " - saveOrUpdateTranLog - " + tranLog.getRefId() + " - thất bại.");
		}
	}

	@Override
	public BaseModelSingle<Boolean> saveTransactionLog(
			NicTransactionLog nicTransactionLog) {
		try {
			return this.dao.saveTransactionLog(nicTransactionLog);
		} catch (Exception e) {
			return new BaseModelSingle<Boolean>(false, false, CreateMessageException.createMessageException(e) + " - saveTransactionLog - thất bại.");
		}
	}
}
