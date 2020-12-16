package com.nec.asia.nic.listLogWs.controller;

import java.util.List;

import com.nec.asia.nic.comp.ws.log.domain.EppWsLog;

public class ListLogWsFrm {
	// public List<ListLogWsInfo> epplist;
	// public
	private String keyId;
	private String urlRequest;
	private String type;
	private String logWsDateFrom;
	private String logWsDateTo;

	public ListLogWsFrm() {
		super();
	}

	public String getKeyId() {
		return keyId;
	}

	public void setKeyId(String keyId) {
		this.keyId = keyId;
	}

	public String getUrlRequest() {
		return urlRequest;
	}

	public void setUrlRequest(String urlRequest) {
		this.urlRequest = urlRequest;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getLogWsDateFrom() {
		return logWsDateFrom;
	}

	public void setLogWsDateFrom(String logWsDateFrom) {
		this.logWsDateFrom = logWsDateFrom;
	}

	public String getLogWsDateTo() {
		return logWsDateTo;
	}

	public void setLogWsDateTo(String logWsDateTo) {
		this.logWsDateTo = logWsDateTo;
	}

	// private
}
