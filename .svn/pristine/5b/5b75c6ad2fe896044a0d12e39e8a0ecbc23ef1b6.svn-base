package com.nec.asia.nic.framework;

import java.io.Serializable;
import java.util.List;

/**
 * A wrapper class for jQuery Flexigrid plugin component.
 * 
 * The format must be like this:
 * <code>
 * 		{"total":2,"page":1,"rows":[
 * 			{"personTitle":"Mr","partyName":"Test8325","personDateOfBirth":"1970-07-12"},
 * 			{"personTitle":"Ms","partyName":"Ms Susan Jones","personDateOfBirth":"1955-11-27"}
 * 		]}
 * </code>
 * 
 * @author bright_zheng
 *
 * @param <T>: the generic type of the specific domain
 */
public class PaginatedResult<T> implements Serializable {
	private static final long serialVersionUID = -538629307783721872L;

	public PaginatedResult(int total, int page, List<T> rows){
		this.total = total;	//total records		
		this.page = page;	//current page no.
		this.rows = rows;	//the objects of current page
	}
	public PaginatedResult(){
		
	}
	private int total;
	private int page;
	private List<T> rows;


	public int getTotal() {
		return total;
	}
	public void setTotal(int total) {
		this.total = total;
	}
	public int getPage() {
		return page;
	}
	public void setPage(int page) {
		this.page = page;
	}
	public List<T> getRows() {
		return rows;
	}
	public void setRows(List<T> rows) {
		this.rows = rows;
	}
	
	public int getRowSize(){
		return this.rows.size();
	}
}
