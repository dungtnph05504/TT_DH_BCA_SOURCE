package com.nec.asia.nic.util;

import java.util.ArrayList;
import java.util.List;


public class BaseListResponse {

	public Integer TotalItems;
	public Integer Total;
	public Integer PageIndex;
	public Integer PageSize;
	public Integer PageCount;
	public Boolean IsSuccess;
	public Integer ResultCode;
	public String Message;
	public List<PropertyError> PropertyErrors = new ArrayList<PropertyError>();
	public List<PrintLocation> Data  = new ArrayList<PrintLocation>();

	public BaseListResponse() {

	}

	public BaseListResponse(Integer TotalItems, Integer Total,
			Integer PageIndex, Integer PageSize, Integer PageCount,
			Boolean IsSuccess, Integer ResultCode, String Message,
			List<PropertyError> PropertyErrors, List<PrintLocation> Data) {
		this.TotalItems = TotalItems;
		this.Total = Total;
		this.PageIndex = PageIndex;
		this.PageSize = PageSize;
		this.PageCount = PageCount;
		this.IsSuccess = IsSuccess;
		this.ResultCode = ResultCode;
		this.Message = Message;
		this.PropertyErrors = PropertyErrors;
		this.Data = Data;
	}

	public Integer getTotalItems() {
		return this.TotalItems;
	}

	public void setTotalItems(Integer TotalItems) {
		this.TotalItems = TotalItems;
	}

	public Integer getTotal() {
		return this.Total;
	}

	public void setTotal(Integer Total) {
		this.Total = Total;
	}

	public Integer getPageIndex() {
		return this.PageIndex;
	}

	public void setPageIndex(Integer PageIndex) {
		this.PageIndex = PageIndex;
	}

	public Integer getPageSize() {
		return this.PageSize;
	}

	public void setPageSize(Integer PageSize) {
		this.PageSize = PageSize;
	}

	public Integer getPageCount() {
		return this.PageCount;
	}

	public void setPageCount(Integer PageCount) {
		this.PageCount = PageCount;
	}

	public Boolean getIsSuccess() {
		return this.IsSuccess;
	}

	public void setIsSuccess(Boolean IsSuccess) {
		this.IsSuccess = IsSuccess;
	}

	public Integer getResultCode() {
		return this.ResultCode;
	}

	public void setResultCode(Integer ResultCode) {
		this.ResultCode = ResultCode;
	}

	public String getMessage() {
		return this.Message;
	}

	public void setMessage(String Message) {
		this.Message = Message;
	}

	public List<PropertyError> getPropertyErrors() {
		return this.PropertyErrors;
	}

	public void setPropertyErrors(List<PropertyError> PropertyErrors) {
		this.PropertyErrors = PropertyErrors;
	}

	public List<PrintLocation> getData() {
		return this.Data;
	}

	public void setData(List<PrintLocation> Data) {
		this.Data = Data;
	}
}
