package com.nec.asia.nic.util;

public class RequestBase {

	private String Keyword;
	private Integer PageSize;
	private Integer PageIndex;
	private Integer Status; // 0(khóa) hoặc 1(hoạt động), -1 (toàn bộ)

	public RequestBase() {

	}

	public RequestBase(String keyword, Integer pageSize, Integer pageIndex,
			Integer status) {
		this.Keyword = keyword;
		this.PageSize = pageSize;
		this.PageIndex = pageIndex;
		this.Status = status;
	}

	public String getKeyword() {
		return this.Keyword;
	}

	public void setKeyword(String keyword) {
		this.Keyword = keyword;
	}

	public Integer getPageSize() {
		return this.PageSize;
	}

	public void setPageSize(Integer pageSize) {
		this.PageSize = pageSize;
	}

	public Integer getPageIndex() {
		return this.PageIndex;
	}

	public void setPageIndex(Integer pageIndex) {
		this.PageIndex = pageIndex;
	}

	public Integer getStatus() {
		return this.Status;
	}

	public void setStatus(Integer status) {
		this.Status = status;
	}
}
