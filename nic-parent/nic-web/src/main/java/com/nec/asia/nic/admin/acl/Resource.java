package com.nec.asia.nic.admin.acl;

import java.io.Serializable;

/**
 * Demo resource bean
 * 
 * @author bright_zheng
 *
 */
public class Resource implements Serializable {
	private static final long serialVersionUID = -3102625249112330755L;
	
	private long id;
	private String code;
	private String name;
	private String uri;
	
	public Resource(){}
	
	public Resource(long id, String code, String name, String uri){
		this.id = id;
		this.code = code;
		this.name = name;
		this.uri = uri;
	}
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getCode() {
		return this.code;
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
	public String getUri() {
		return uri;
	}
	public void setUri(String uri) {
		this.uri = uri;
	}
	
}
