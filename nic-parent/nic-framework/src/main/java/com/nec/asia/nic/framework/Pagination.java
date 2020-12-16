package com.nec.asia.nic.framework;

import java.io.Serializable;

/**
 * page=2&rp=20&sortname=partyName&sortorder=asc&query=&qtype=personTitle
 * 
 * @author bright_zheng
 *
 */
public class Pagination implements Serializable {
	private static final long serialVersionUID = -4768781521506000835L;
	private int pageNo;
	private int pageSize;
	private String orderBy;
	private String orderType;
	
	public Pagination(){}
	
	public Pagination(String orderBy, String orderType){
		this.pageNo = 1;
		this.pageSize = 20;
		this.orderBy = orderBy;
		this.orderType = orderType;
	}
	
	public int getPageNo() {
		return pageNo;
	}
	public void setPageNo(int pageNo) {
		this.pageNo = pageNo;
	}
	public int getPageSize() {
		return pageSize;
	}
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
	public String getOrderBy() {
		return orderBy;
	}
	public void setOrderBy(String orderBy) {
		this.orderBy = orderBy;
	}
	public String getOrderType() {
		return orderType;
	}
	public void setOrderType(String orderType) {
		this.orderType = orderType;
	}
	
}
