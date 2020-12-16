package com.nec.asia.nic.comp.trans.domain;

import java.io.Serializable;
import java.util.Date;

public class EppFlightRouter implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Long id;
	private String createBy;
	private String lastModifiedBy;
	private Date createDate;
	private Date lastModifiedDate;
	private String flightRouterCode;
	private String fromPlaceCode;
	private String toPlaceCode;
	private String name;
	private String note;
	private Date closedTime;
	
	public EppFlightRouter(){
		
	}
	
	public EppFlightRouter(Long id, String createBy, String lastModifiedBy,
			Date createDate, Date lastModifiedDate, String flightRouterCode,
			String fromPlaceCode, String toPlaceCode, String name, String note,
			Date closedTime) {
		super();
		this.id = id;
		this.createBy = createBy;
		this.lastModifiedBy = lastModifiedBy;
		this.createDate = createDate;
		this.lastModifiedDate = lastModifiedDate;
		this.flightRouterCode = flightRouterCode;
		this.fromPlaceCode = fromPlaceCode;
		this.toPlaceCode = toPlaceCode;
		this.name = name;
		this.note = note;
		this.closedTime = closedTime;
	}
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getCreateBy() {
		return createBy;
	}
	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}
	public String getLastModifiedBy() {
		return lastModifiedBy;
	}
	public void setLastModifiedBy(String lastModifiedBy) {
		this.lastModifiedBy = lastModifiedBy;
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
	public String getFlightRouterCode() {
		return flightRouterCode;
	}
	public void setFlightRouterCode(String flightRouterCode) {
		this.flightRouterCode = flightRouterCode;
	}
	public String getFromPlaceCode() {
		return fromPlaceCode;
	}
	public void setFromPlaceCode(String fromPlaceCode) {
		this.fromPlaceCode = fromPlaceCode;
	}
	public String getToPlaceCode() {
		return toPlaceCode;
	}
	public void setToPlaceCode(String toPlaceCode) {
		this.toPlaceCode = toPlaceCode;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getNote() {
		return note;
	}
	public void setNote(String note) {
		this.note = note;
	}
	public Date getClosedTime() {
		return closedTime;
	}
	public void setClosedTime(Date closedTime) {
		this.closedTime = closedTime;
	}
	
	
}
