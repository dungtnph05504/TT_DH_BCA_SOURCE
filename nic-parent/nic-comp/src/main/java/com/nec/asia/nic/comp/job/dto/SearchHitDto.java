package com.nec.asia.nic.comp.job.dto;

import java.io.Serializable;

public class SearchHitDto implements Serializable {

	private static final long serialVersionUID = 1L;

	private String hitTransactionId;
	private Long searchResultId;
	private String searchResult_typeSearch;
	private String searchHitResult_hitInfo;

	public SearchHitDto(String hitTransactionId, Long searchResultId, String searchResult_typeSearch,
			String searchHitResult_hitInfo) {
		super();
		this.hitTransactionId = hitTransactionId;
		this.searchResultId = searchResultId;
		this.searchResult_typeSearch = searchResult_typeSearch;
		this.searchHitResult_hitInfo = searchHitResult_hitInfo;
	}

	public SearchHitDto() {
		super();
		// TODO Auto-generated constructor stub
	}

	public String getHitTransactionId() {
		return hitTransactionId;
	}

	public void setHitTransactionId(String hitTransactionId) {
		this.hitTransactionId = hitTransactionId;
	}

	public Long getSearchResultId() {
		return searchResultId;
	}

	public void setSearchResultId(Long searchResultId) {
		this.searchResultId = searchResultId;
	}

	public String getSearchResult_typeSearch() {
		return searchResult_typeSearch;
	}

	public void setSearchResult_typeSearch(String searchResult_typeSearch) {
		this.searchResult_typeSearch = searchResult_typeSearch;
	}

	public String getSearchHitResult_hitInfo() {
		return searchHitResult_hitInfo;
	}

	public void setSearchHitResult_hitInfo(String searchHitResult_hitInfo) {
		this.searchHitResult_hitInfo = searchHitResult_hitInfo;
	}

	public String toString() {
		return "[" + "[hitTransactionId[" + hitTransactionId + "]]" + "[searchResultId[" + searchResultId.longValue()
				+ "]]" + "[searchResult_typeSearch[" + searchResult_typeSearch + "]]" + "]";
	}
}
