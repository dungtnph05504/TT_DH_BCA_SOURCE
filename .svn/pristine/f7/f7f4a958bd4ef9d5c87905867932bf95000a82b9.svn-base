package com.nec.asia.nic.framework.admin.audit;

import org.aspectj.lang.ProceedingJoinPoint;

import com.nec.asia.nic.framework.admin.audit.Auditable.Severity;


/**
 * General audit interface.<br>
 * 
 * NRIC is required system keeps audit log on what action, is done by who, where, when, on an applicant (by nric)<br>
 * 
 * 1. What: The defined and auditable method means what business action the user performing<br>
 * 2. Who: Should be the current user - USERID.<br>
 * 3. Where: Means the workstation - WORKSTATIONID.<br>
 * 4. When: Just the system current date time - AUDITDATE.<br>
 * 5. Subject: The NRIC ID - NRICNO.<br>
 * 
 * 
 * Current Example:
 * AUDITDATE		USERID	WORKSTATIONID	REGTXNID	SUBTXNID	NRICNO	AUDITMSGID
 * 20100728183926	USER1	WS3001			LIN			USR			000
 * 20100728184011	USER1	WS3001			REG			001			S8439204I	000
 * 20100728184028	USER1	WS3001			REG			002			S8439204I	000
 * 20100728184304	USER1	WS3001			REG			004			S8439204I	ERR
 *  
 * @author bright_zheng
 *
 */
public interface AuditService {
	
	/**
	 * Audit service spec
	 * 
	 * @param pjp, ProceedingJoinPoint
	 * @param severity, Severity
	 * @param throwable, if exception occurred, it will not be null
	 * @param timeMs, MiliSecond of method executing
	 * 
	 * @throws Throwable
	 */
	public void doAudit(ProceedingJoinPoint pjp, 
			Severity severity, 
			Throwable throwable, 
			long timeMs) throws Throwable;
}
