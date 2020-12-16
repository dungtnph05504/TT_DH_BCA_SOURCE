package com.nec.asia.nic.framework.admin.audit.impl;

import org.aspectj.lang.ProceedingJoinPoint;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.nec.asia.nic.framework.admin.audit.AuditService;
import com.nec.asia.nic.framework.admin.audit.Auditable;
import com.nec.asia.nic.framework.admin.audit.Auditable.Severity;
import com.nec.asia.nic.utils.ApplicationContextManager;

/**
 * Audit adapter between AOP aspects and audit service implementation
 * 
 * @author bright_zheng
 *
 */
public class AuditServiceAdapter {
	private static final Logger logger = LoggerFactory.getLogger(AuditServiceAdapter.class);
	
	private static final String DEFAULT_CONTROLLER_AUDIT_SERVICE = "defaultControllerAuditService";
	private static final String DEFAULT_SERVICE_AUDIT_SERVICE = "defaultServiceAuditService";
	
	public Object executeControllerLayerAudit(ProceedingJoinPoint pjp, Auditable auditable) throws Throwable {
		return this.executeAudit(pjp, auditable, DEFAULT_CONTROLLER_AUDIT_SERVICE);
	}
	
	public Object executeServiceLayerAudit(ProceedingJoinPoint pjp, Auditable auditable) throws Throwable {
		return this.executeAudit(pjp, auditable, DEFAULT_SERVICE_AUDIT_SERVICE);
	}
	
	private Object executeAudit(ProceedingJoinPoint pjp, Auditable auditable, String defaultService) throws Throwable {
		logger.info("executeAudit({}, {}, {})", new Object[]{pjp, auditable, defaultService} );
		long start = System.currentTimeMillis();
		Throwable throwable=null;
		try {
			return pjp.proceed();
		} catch (Throwable th) {
			throwable = th;
			throw th;
		} finally{
			long timeMs = System.currentTimeMillis() - start;
			try{
				Severity severity = auditable.severity();
				String implClass = auditable.implClass();				
				
				//if we specify our specific implementation class
				if (implClass==null || implClass.length()==0){
					implClass = defaultService;
				}
				AuditService service = (AuditService) ApplicationContextManager.getBean(implClass);
				service.doAudit(pjp, severity, throwable, timeMs);
			}catch(Exception e){
				logger.error("Error in executeAudit.", e);
			}
		}
	}
}
