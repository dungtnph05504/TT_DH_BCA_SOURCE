package com.nec.asia.nic.listLogWs.controller;

import java.util.Date;

public class ListLogWsInfo {
	private Long id;
	private String keyId;
	private String type;
	private String urlRequest;
	private String dataRequest;
	private String dataResponse;
	private String messageErrorLog;
	private Date createdDate;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getKeyId() {
		return keyId;
	}
	public void setKeyId(String keyId) {
		this.keyId = keyId;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getUrlRequest() {
		return urlRequest;
	}
	public void setUrlRequest(String urlRequest) {
		this.urlRequest = urlRequest;
	}
	public String getDataRequest() {
		return dataRequest;
	}
	public void setDataRequest(String dataRequest) {
		this.dataRequest = dataRequest;
	}
	public String getDataResponse() {
		return dataResponse;
	}
	public void setDataResponse(String dataResponse) {
		this.dataResponse = dataResponse;
	}
	public String getMessageErrorLog() {
		return messageErrorLog;
	}
	public void setMessageErrorLog(String messageErrorLog) {
		this.messageErrorLog = messageErrorLog;
	}
	public Date getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}
	
}