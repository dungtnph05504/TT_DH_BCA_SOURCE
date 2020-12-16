package com.nec.asia.nic.comp.trans.domain;

import java.io.Serializable;
import java.util.Date;

public class SynQueueJobXnc implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Date createdTs;
	private Long id;
	private String objectId;
	private String objectType;
	private String siteCode;
	private String status;
	private Integer syncRetry;
	private Date syncTs;
	private Long idImmi;
	private String flightType;
	private String passportType;
	
	public SynQueueJobXnc() {
		super();
	}

	public Date getCreatedTs() {
		return createdTs;
	}

	public void setCreatedTs(Date createdTs) {
		this.createdTs = createdTs;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getObjectId() {
		return objectId;
	}

	public void setObjectId(String objectId) {
		this.objectId = objectId;
	}

	public String getObjectType() {
		return objectType;
	}

	public void setObjectType(String objectType) {
		this.objectType = objectType;
	}

	

	public String getSiteCode() {
		return siteCode;
	}

	public void setSiteCode(String siteCode) {
		this.siteCode = siteCode;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Integer getSyncRetry() {
		return syncRetry;
	}

	public void setSyncRetry(Integer syncRetry) {
		this.syncRetry = syncRetry;
	}

	public Date getSyncTs() {
		return syncTs;
	}

	public void setSyncTs(Date syncTs) {
		this.syncTs = syncTs;
	}

	public Long getIdImmi() {
		return idImmi;
	}

	public void setIdImmi(Long idImmi) {
		this.idImmi = idImmi;
	}

	public String getFlightType() {
		return flightType;
	}

	public void setFlightType(String flightType) {
		this.flightType = flightType;
	}

	public String getPassportType() {
		return passportType;
	}

	public void setPassportType(String passportType) {
		this.passportType = passportType;
	}
	
	
	
	

}
