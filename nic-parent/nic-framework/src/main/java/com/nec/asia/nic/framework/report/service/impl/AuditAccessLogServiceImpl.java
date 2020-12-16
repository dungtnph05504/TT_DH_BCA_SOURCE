/**
 * 
 */
package com.nec.asia.nic.framework.report.service.impl;

import java.net.InetAddress;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.hibernate.criterion.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.nec.asia.nic.framework.PaginatedResult;
import com.nec.asia.nic.framework.admin.audit.domain.AuditAccessLogs;
import com.nec.asia.nic.framework.report.dao.AuditAccessLogDao;
import com.nec.asia.nic.framework.report.service.AuditAccessLogService;
import com.nec.asia.nic.framework.service.impl.DefaultBusinessServiceImpl;
import com.nec.asia.nic.utils.ExceptionMessageFormatter;
import com.nec.asia.nic.utils.XmlMessageUtil;
import com.nec.asia.nic.web.session.UserSession;

/**
 * @author chris
 */
/* 
 * Modification History:
 * 
 * 24 Sep 2013 (chris): init
 * 11 Dec 2013 (Sailaja): getAuditAccessLogList
 * 19 Jun 2014 (chris): add method saveAuditAccessLogForWS()
 * 22 Jul 2014 (chris): add @Transactional for method saveAuditAccessLogForWS() 
 */
@Service("auditAccessLogService")
@Transactional
public class AuditAccessLogServiceImpl
		extends
		DefaultBusinessServiceImpl<AuditAccessLogs, Long, AuditAccessLogDao>
		implements AuditAccessLogService {

	/** must define the non-argument constructor because we use CGLib proxy */
	public AuditAccessLogServiceImpl() {
	}
	
	@Autowired
	public AuditAccessLogServiceImpl(AuditAccessLogDao dao){
		this.dao = dao;
	}

	public void saveAuditAccessLogForWeb(HttpServletRequest httpRequest, String functionName, Object args[], Throwable throwable, long timeMs) {
		String serverId = "NIC"; 
		String userId = "ADMIN";
		String wkstnId = "SYSTEM";
		String sessionId = "";

		//get server Id
		try {
			java.net.InetAddress localMachine = InetAddress.getLocalHost();
			serverId = localMachine.getHostName().toUpperCase();
		} catch (Exception e) {
		}
		//get userId, workstationId, sessionId
		try {
			if (httpRequest!=null) {
				HttpSession session = httpRequest.getSession();
				sessionId = session.getId();
				UserSession userSession = (UserSession) session.getAttribute("userSession");
				userId = userSession.getUserName();
				wkstnId = userSession.getWorkstationId();
			}
		} catch (Exception e) {
		}
		String errorFlag = throwable==null? "N" : "Y";
		String exceptionMsg = throwable==null?"":StringUtils.substring(ExceptionMessageFormatter.format(throwable), 0, 3999);
		String paramValues = StringUtils.substring(ReflectionToStringBuilder.toString(args,ToStringStyle.DEFAULT_STYLE), 0, 3999);
		
		AuditAccessLogs accessLogDBO = new AuditAccessLogs();
		accessLogDBO.setAuditDate(new Date());
		accessLogDBO.setFunctionName(functionName);	
		
		accessLogDBO.setErrorFlag(errorFlag);	
		accessLogDBO.setParamValues(paramValues);
		accessLogDBO.setAccessLogData(exceptionMsg);
		accessLogDBO.setTimeTaken(timeMs);
		
		accessLogDBO.setServerId(serverId);
		accessLogDBO.setSessionId(sessionId);
		accessLogDBO.setUserId(userId);
		accessLogDBO.setWkstnId(wkstnId);
		try {
			this.save(accessLogDBO);
		} catch (Exception e) {
			logger.error("Fail to save access log: {}", e.getMessage());
		}
	}
	
	public void saveAuditAccessLog(HttpServletRequest httpRequest, String functionName, String obj, Throwable throwable, long timeMs) {
		String serverId = "NIC"; 
		String userId = "ADMIN";
		String wkstnId = "SYSTEM";
		String sessionId = "";

		//get server Id
		try {
			java.net.InetAddress localMachine = InetAddress.getLocalHost();
			serverId = localMachine.getHostName().toUpperCase();
		} catch (Exception e) {
		}
		//get userId, workstationId, sessionId
		try {
			if (httpRequest!=null) {
				HttpSession session = httpRequest.getSession();
				sessionId = session.getId();
				UserSession userSession = (UserSession) session.getAttribute("userSession");
				userId = userSession.getUserName();
				wkstnId = userSession.getWorkstationId();
			}
		} catch (Exception e) {
		}
		String errorFlag = throwable==null? "N" : "Y";
		String exceptionMsg = throwable==null?"":StringUtils.substring(ExceptionMessageFormatter.format(throwable), 0, 3999);
		String paramValues = obj;		
		AuditAccessLogs accessLogDBO = new AuditAccessLogs();
		accessLogDBO.setAuditDate(new Date());
		accessLogDBO.setFunctionName(functionName);	
		
		accessLogDBO.setErrorFlag(errorFlag);	
		accessLogDBO.setParamValues(paramValues);
		accessLogDBO.setAccessLogData(exceptionMsg);
		accessLogDBO.setTimeTaken(timeMs);
		
		accessLogDBO.setServerId(serverId);
		accessLogDBO.setSessionId(sessionId);
		accessLogDBO.setUserId(userId);
		accessLogDBO.setWkstnId(wkstnId);
		try {
			this.save(accessLogDBO);
		} catch (Exception e) {
			logger.error("Fail to save access log: {}", e.getMessage());
		}
	}

	@Override
	public PaginatedResult<AuditAccessLogs> getAuditAccessLogList(
			String userId, String wkstnId, String functionName,
			String dateFrom, String dateTo, String status, int pageSize,
			Order order, int currentPage) throws Exception {
		try {
			return dao.getAuditAccessLogList(userId, wkstnId, functionName,
					dateFrom, dateTo, status, pageSize, order, currentPage);
		} catch (Exception ex) {
			logger.error("Error in getAuditAccessLogList: {}", ex.getMessage());
		}
		return null;
	}

	@Override
	public void saveAuditAccessLogForWS(String serviceName, String functionName, String userId, String wkstnId, Object[] args, Object[] response, Throwable throwable, long timeMs) {
		boolean success = throwable==null? true : false;
		this.saveAuditAccessLogForWS(serviceName, functionName, userId, wkstnId, args, response, throwable, timeMs, success);
	}
	
	@Override
	public void saveAuditAccessLogForWS(String serviceName, String functionName, String userId, String wkstnId, Object[] args, Object[] response, Throwable throwable, long timeMs, boolean success) {
		String serverId = "NIC"; 
		String sessionId = "";

		String paramValues = "";
		String responses = "";
		//get server Id
		try {
			java.net.InetAddress localMachine = InetAddress.getLocalHost();
			serverId = localMachine.getHostName().toUpperCase();
		} catch (Exception e) {
		}
		//sessionId
		if (serviceName!=null) {
			sessionId = serviceName+"-"+System.currentTimeMillis();
		}

		String errorFlag = success? "N" : "Y";
		//if success, then ignore the throwable object
		if (throwable!=null && StringUtils.equals("N", errorFlag)) {
			errorFlag = "Y";
		}
		if (args!=null && args.length == 1) {
			paramValues = XmlMessageUtil.parseObjectToXml(args[0]);
		} else if (args!=null && args.length > 1) {
			paramValues = XmlMessageUtil.parseObjectToXml(args);
		}
		
		AuditAccessLogs accessLogDBO = new AuditAccessLogs();
		accessLogDBO.setAuditDate(new Date());
		accessLogDBO.setFunctionName(functionName);	
		
		accessLogDBO.setErrorFlag(errorFlag);	
		accessLogDBO.setParamValues(paramValues);
		if (throwable==null) {
			if (response!=null && response.length == 1) {
				responses = XmlMessageUtil.parseObjectToXml(response[0]);
			} else if (response!=null && response.length > 1) {
				responses = XmlMessageUtil.parseObjectToXml(response);
			}
			accessLogDBO.setAccessLogData(responses);
		} else {
			String exceptionMsg = StringUtils.substring(ExceptionMessageFormatter.format(throwable), 0, 3999);
			accessLogDBO.setAccessLogData(exceptionMsg);
		}
		accessLogDBO.setTimeTaken(timeMs);
		
		accessLogDBO.setServerId(serverId);
		accessLogDBO.setSessionId(sessionId);
		accessLogDBO.setUserId(userId);
		accessLogDBO.setWkstnId(wkstnId);
		try {
			this.save(accessLogDBO);
			logger.info("save access log completed: {}, {}", accessLogDBO.getAccessLogId(), accessLogDBO.getFunctionName());
		} catch (Exception e) {
			logger.error("Fail to save access log: {}", e.getMessage());
		}
	}
}
