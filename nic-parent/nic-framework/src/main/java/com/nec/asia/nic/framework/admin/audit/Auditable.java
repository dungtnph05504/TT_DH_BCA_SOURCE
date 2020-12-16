package com.nec.asia.nic.framework.admin.audit;

import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotation for Auditing
 * 
 * We can extend the auditing behavior by specify:
 * 1. Severity, means the severity of auditing action
 * 2. ImplClass, means we can even specify the auditing implementation
 * 
 * @author bright_zheng
 *
 */
@Inherited
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface Auditable{
	public enum Severity { NORMAL, IMPORTANT, CRITICAL};
	//public enum AuditType { ACTION, SERVICE};

	/** The severity of the audit action, default is Severity.NORMAL */
	Severity severity() default Severity.NORMAL;
	
	/** The type of audit, default is AuditType.ACTION */
	//AuditType auditType() default AuditType.ACTION;
	
	/** The specific audit implementation class refer id from Spring, 
	 *  default is injected by configuration file  */
	String implClass() default "";
}
