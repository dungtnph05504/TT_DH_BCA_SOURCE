package com.nec.asia.nic.comp.trans.domain;

import java.io.Serializable;
import java.util.Date;

public class ConfigurationWorkflow implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Long id;
	private String siteIdFrom;
	private String siteIdTo;
	private String configType;
	private Date dateTimeFrom;
	private Date dateTimeTo;
	private String createBy;
	private Date createDate;
	private Boolean stage;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getSiteIdFrom() {
		return siteIdFrom;
	}
	public void setSiteIdFrom(String siteIdFrom) {
		this.siteIdFrom = siteIdFrom;
	}
	public String getSiteIdTo() {
		return siteIdTo;
	}
	public void setSiteIdTo(String siteIdTo) {
		this.siteIdTo = siteIdTo;
	}
	public String getConfigType() {
		return configType;
	}
	public void setConfigType(String configType) {
		this.configType = configType;
	}
	public Date getDateTimeFrom() {
		return dateTimeFrom;
	}
	public void setDateTimeFrom(Date dateTimeFrom) {
		this.dateTimeFrom = dateTimeFrom;
	}
	public Date getDateTimeTo() {
		return dateTimeTo;
	}
	public void setDateTimeTo(Date dateTimeTo) {
		this.dateTimeTo = dateTimeTo;
	}
	public String getCreateBy() {
		return createBy;
	}
	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	public Boolean getStage() {
		return stage;
	}
	public void setStage(Boolean stage) {
		this.stage = stage;
	}
	
	

}
