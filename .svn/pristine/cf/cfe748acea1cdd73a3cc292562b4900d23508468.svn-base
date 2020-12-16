package com.nec.asia.nic.framework.admin.audit.impl;

import java.io.Serializable;
import java.util.Map;

import com.nec.asia.nic.framework.admin.audit.Auditable.Severity;

/**
 * basic bean including all we want to perform auditing
 * please treat it as an abstract info specification
 * 
 * @author bright_zheng
 *
 */
public class AuditBean implements Serializable {
	private static final long serialVersionUID = -8550207958613567850L;
	
	private String module;		//e.g. Registration
	private String subject;		//e.g. NIN
	private Severity severity;	//e.g. Normal
	private String who;			//The current user
	private String what;		//e.g. Registration Save & maybe more detail info
	private String when;		//The current timestamp
	private String where;		//The workstation Id
	
	private String code; 		//flag of the action
	private String message;		//exception message if any
	
	/** in ms */
	private long elapsedTime;
	
	/** need more info? use it then */
	private Map<String,?> extendInfo;

	public String getModule() {
		return module;
	}
	public void setModule(String module) {
		this.module = module;
	}
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public String getWho() {
		return who;
	}
	public void setWho(String who) {
		this.who = who;
	}
	public String getWhat() {
		return what;
	}
	public void setWhat(String what) {
		this.what = what;
	}
	public String getWhen() {
		return when;
	}
	public void setWhen(String when) {
		this.when = when;
	}
	public String getWhere() {
		return where;
	}
	public void setWhere(String where) {
		this.where = where;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	/**
	 * in milliseconds
	 * @param elapsedTime
	 */
	public long getElapsedTime() {
		return elapsedTime;
	}
	/**
	 * in seconds
	 * @param elapsedTime
	 */
	public void setElapsedTime(long elapsedTime) {
		this.elapsedTime = elapsedTime;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public void setSeverity(Severity severity) {
		this.severity = severity;
	}
	public Severity getSeverity() {
		return severity;
	}	
	public Map<String, ?> getExtendInfo() {
		return extendInfo;
	}
	public void setExtendInfo(Map<String, ?> extendInfo) {
		this.extendInfo = extendInfo;
	}
}
