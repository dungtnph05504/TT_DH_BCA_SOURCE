package com.nec.asia.nic.comp.trans.domain;

import java.util.List;

public class BaseModelList<T>{
	private List<T> listModel;
	private boolean isError;
	private String message;
	public List<T> getListModel() {
		return listModel;
	}
	public void setListModel(List<T> listModel) {
		this.listModel = listModel;
	}
	public boolean isError() {
		return isError;
	}
	public void setError(boolean isError) {
		this.isError = isError;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public BaseModelList(List<T> listModel, boolean isError, String message) {
		this.listModel = listModel;
		this.isError = isError;
		this.message = message;
	}
	public BaseModelList() {
	}
	
	
}
