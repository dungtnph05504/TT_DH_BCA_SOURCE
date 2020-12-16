package com.nec.asia.nic.framework.admin.audit.impl;

import java.text.MessageFormat;

import org.aspectj.lang.ProceedingJoinPoint;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.nec.asia.nic.framework.admin.audit.AuditService;
import com.nec.asia.nic.framework.admin.audit.Auditable.Severity;

/**
 * Default implementation of business action audit.
 * 
 * @author bright_zheng
 *
 */
public abstract class AbstractAuditService implements AuditService{
	private static Logger logger = LoggerFactory.getLogger(AbstractAuditService.class);
	protected static final String DEFAULT_SECCESS_CODE = "000";
	protected static final String DEFAULT_FAILURE_CODE = "999";
	
	/**
	 * Audit Message Template. Example:
	 * 
		severity	//0
		module		//1
		subject		//2		
		what		//3
		who			//4
		when		//5
		where		//6		
		code		//7
		message		//8		
		elapsedTime	//9 
	 */
	private String messageTemplate;
	
	public String getMessageTemplate() {
		return messageTemplate;
	}

	public void setMessageTemplate(String messageTemplate) {
		this.messageTemplate = messageTemplate;
	}

	public void doAudit(ProceedingJoinPoint pjp, 
			Severity severity, 
			Throwable throwable, 
			long timeMs){
		//extract audit info from parameters
		AuditBean auditBean = this.extractToAuditBean(pjp.getArgs());
		
		//set the following properties by default
		auditBean.setElapsedTime(timeMs);
		auditBean.setSeverity(severity);
		if(throwable==null){
			auditBean.setCode(DEFAULT_SECCESS_CODE);
		}else{
			auditBean.setCode(DEFAULT_FAILURE_CODE);
			auditBean.setMessage(throwable.getMessage());
		}
		this.store(auditBean);
	}
	
	/**
	 * We should extract what we want from parameters.
	 * Must be implemented in subclass.
	 * 
	 * @param args
	 * @return
	 */
	abstract protected AuditBean extractToAuditBean(Object[] args);
	
	/**
	 * this method must be override by subclass
	 * for audit info persistence
	 * 
	 * as an abstract behavior, here we just log it to file only
	 * the subclass should invoke super.store(auditbean) first
	 * and then implement the real store logic. 
	 * 
	private String code; 		//flag of the action
	private String message;		//exception message if any	
	private long elapsedTime;
	 * 
	 * @param auditBean
	 */
	protected void store(AuditBean auditBean){
		//log only by default
		if(logger.isInfoEnabled()){
			String message = MessageFormat.format(getMessageTemplate(), 
				new Object[]{
					auditBean.getSeverity(),	//0

					auditBean.getModule(),		//1
					auditBean.getSubject(),		//2
					
					auditBean.getWhat(),		//3
					auditBean.getWho(),			//4
					auditBean.getWhen(),		//5
					auditBean.getWhere(),		//6
					
					auditBean.getCode(),		//7
					auditBean.getMessage(),		//8
					
					auditBean.getElapsedTime()	//9
				   });
			auditBean.setMessage(message);
			logger.info("==>Audit Message: {}", message);
		}
	}
}
