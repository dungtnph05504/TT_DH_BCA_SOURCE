package com.nec.asia.nic.utils;

import java.io.*;

/**
 * general data transfer object for exchanging real pojo between client and server via HTTP 
 * 
 * @author bright_zheng
 *
 */
public class DTO<T> implements Serializable {
	private static final long serialVersionUID = -7150578931576750838L;
	private String className;
	private T object;
	
	public DTO(String className, T object){
		this.className = className;
		this.object = object;
	}

	public T getObject() {
		return object;
	}

	public void setObject(T object) {
		this.object = object;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public String getClassName() {
		return className;
	}
}