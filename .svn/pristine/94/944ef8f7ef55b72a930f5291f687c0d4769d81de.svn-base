package com.nec.asia.nic.comp.listHandover.domain;
// Generated Jan 20, 2016 11:25:39 AM by Hibernate Tools 4.3.1.Final

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity 
@Table(name = "EPP_CENTRAL.EPP_PERSO_LOCATIONS")
public class NicPersoLocations implements java.io.Serializable {
    
	private static final long serialVersionUID = 15103120310231L;

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
	
	public NicPersoLocations() {
	}

	public NicPersoLocations(Long id) {
		this.id = id;
	}
	
	public NicPersoLocations(LocationForm loca) {
		this.code = loca.getCode();
		this.name = loca.getName();
		this.address = loca.getAddress();
		this.description = loca.getAddress();
		this.status = loca.getStatus();
		this.idPerso = loca.getIdPerso();
	}

	public NicPersoLocations(Long id, String code, String name, String address,
			String description, int status, Long createBy, Date createDate, Long lastModifiedBy, Date lastModifiedDate,Long idPerso) {

		this.id = id;
		this.code = code;
		this.name = name;
		this.address = address;
		this.description = description;
		this.status = status;
		this.createBy = createBy;
		this.createDate = createDate;
		this.lastModifiedBy = lastModifiedBy;
		this.lastModifiedDate = lastModifiedDate;
		this.idPerso = idPerso;
	}

	public Long getIdPerso() {
		return idPerso;
	}

	public void setIdPerso(Long idPerso) {
		this.idPerso = idPerso;
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

	
}
