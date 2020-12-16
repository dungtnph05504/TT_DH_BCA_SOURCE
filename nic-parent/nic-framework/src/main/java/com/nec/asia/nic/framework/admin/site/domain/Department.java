package com.nec.asia.nic.framework.admin.site.domain;

import java.io.Serializable;
import java.util.Date;

public class Department implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String codeDepartment;
	private String nameDepartment;
	private SiteRepository siteRepository;
	private String address;
	private String country;
	private String active;
	private Date createTs;
	private String createBy;
	
	
	
	public Department() {
		super();
	}

	

	public Department(String codeDepartment, String nameDepartment,
			SiteRepository siteRepository, String address, String country,
			String active, Date createTs, String createBy) {
		super();
		this.codeDepartment = codeDepartment;
		this.nameDepartment = nameDepartment;
		this.siteRepository = siteRepository;
		this.address = address;
		this.country = country;
		this.active = active;
		this.createTs = createTs;
		this.createBy = createBy;
	}



	public String getCodeDepartment() {
		return codeDepartment;
	}

	public void setCodeDepartment(String codeDepartment) {
		this.codeDepartment = codeDepartment;
	}

	public String getNameDepartment() {
		return nameDepartment;
	}

	public void setNameDepartment(String nameDepartment) {
		this.nameDepartment = nameDepartment;
	}

	

	public SiteRepository getSiteRepository() {
		return siteRepository;
	}



	public void setSiteRepository(SiteRepository siteRepository) {
		this.siteRepository = siteRepository;
	}



	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getActive() {
		return active;
	}

	public void setActive(String active) {
		this.active = active;
	}

	public Date getCreateTs() {
		return createTs;
	}

	public void setCreateTs(Date createTs) {
		this.createTs = createTs;
	}

	public String getCreateBy() {
		return createBy;
	}

	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}
	
	
	
}
