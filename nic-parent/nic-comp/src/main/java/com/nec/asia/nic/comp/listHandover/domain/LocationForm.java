package com.nec.asia.nic.comp.listHandover.domain;

import java.util.Date;

public class LocationForm {
	private Long id;
	private String code;
	private String name;
	private String address;
	private String description;
	private int status;
	private String issuePlace;
	private Date createDate;
	private Date lastModifiedDate;
	private Long lastModifiedBy;
	private Long createBy;
	private Long idPerso;
	private String stage;
	
	
	public LocationForm() {
		super();
	}
	
	public LocationForm(NicPersoLocations nic) {
		this.id = nic.getId();
		this.code = nic.getCode();
		this.name = nic.getName();
		this.address = nic.getAddress();
		this.description = nic.getDescription();
		this.status = nic.getStatus();
		this.idPerso = nic.getIdPerso();
	}
	
	public LocationForm(Long id, String code, String name, String address,
			String description, int status, String issuePlace, Date createDate,
			Date lastModifiedDate, Long lastModifiedBy, Long createBy,
			Long idPerso) {
		super();
		this.id = id;
		this.code = code;
		this.name = name;
		this.address = address;
		this.description = description;
		this.status = status;
		this.issuePlace = issuePlace;
		this.createDate = createDate;
		this.lastModifiedDate = lastModifiedDate;
		this.lastModifiedBy = lastModifiedBy;
		this.createBy = createBy;
		this.idPerso = idPerso;
	}

	

	public String getStage() {
		return stage;
	}

	public void setStage(String stage) {
		this.stage = stage;
	}

	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public String getIssuePlace() {
		return issuePlace;
	}
	public void setIssuePlace(String issuePlace) {
		this.issuePlace = issuePlace;
	}
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	public Date getLastModifiedDate() {
		return lastModifiedDate;
	}
	public void setLastModifiedDate(Date lastModifiedDate) {
		this.lastModifiedDate = lastModifiedDate;
	}
	public Long getLastModifiedBy() {
		return lastModifiedBy;
	}
	public void setLastModifiedBy(Long lastModifiedBy) {
		this.lastModifiedBy = lastModifiedBy;
	}
	public Long getCreateBy() {
		return createBy;
	}
	public void setCreateBy(Long createBy) {
		this.createBy = createBy;
	}
	public Long getIdPerso() {
		return idPerso;
	}
	public void setIdPerso(Long idPerso) {
		this.idPerso = idPerso;
	}
	
	
}
