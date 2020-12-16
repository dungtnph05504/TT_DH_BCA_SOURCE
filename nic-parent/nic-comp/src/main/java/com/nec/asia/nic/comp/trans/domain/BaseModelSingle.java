package com.nec.asia.nic.comp.trans.domain;

public class BaseModelSingle <T>{
	private T model;
	private boolean isError;
	private String message;
	public T getModel() {
		return model;
	}
	public void setModel(T model) {
		this.model = model;
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
	public BaseModelSingle(T model, boolean isError, String message) {
		this.model = model;
		this.isError = isError;
		this.message = message;
	}
	public BaseModelSingle() {
	}	
}
