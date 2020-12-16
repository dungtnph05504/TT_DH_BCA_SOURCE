package com.nec.asia.nic.framework;


/**
 * The Class PageRequest.
 *
 * @author Mahesh
 */
public class PageRequest {
	
	/** The first row index. */
	private int firstRowIndex;
	
	/** The max records. */
	private int maxRecords;
	
	/** The calculate record count. */
	private boolean calculateRecordCount=true;
	
	/** The sortname. */
	private String sortname;
	
	/** The sortorder. */
	private String sortorder;
	
	/** The pageNo. */
	private int pageNo;
	
	
	
	

	/**
	 * Gets the first row index.
	 *
	 * @return the first row index
	 */
	public int getFirstRowIndex() {
		return firstRowIndex;
	}
	
	/**
	 * Sets the first row index.
	 *
	 * @param firstRowIndex the new first row index
	 */
	public void setFirstRowIndex(int firstRowIndex) {
		this.firstRowIndex = firstRowIndex;
	}
	
	/**
	 * Gets the max records.
	 *
	 * @return the max records
	 */
	public int getMaxRecords() {
		return maxRecords;
	}
	
	/**
	 * Sets the max records.
	 *
	 * @param maxRecords the new max records
	 */
	public void setMaxRecords(int maxRecords) {
		this.maxRecords = maxRecords;
	}
	
	/**
	 * Checks if is calculate record count.
	 *
	 * @return true, if is calculate record count
	 */
	public boolean isCalculateRecordCount() {
		return calculateRecordCount;
	}
	
	/**
	 * Sets the calculate record count.
	 *
	 * @param calculateRecordCount the new calculate record count
	 */
	public void setCalculateRecordCount(boolean calculateRecordCount) {
		this.calculateRecordCount = calculateRecordCount;
	}
	
	/**
	 * Gets the sortname.
	 *
	 * @return the sortname
	 */
	public String getSortname() {
		return sortname;
	}
	
	/**
	 * Sets the sortname.
	 *
	 * @param sortname the new sortname
	 */
	public void setSortname(String sortname) {
		this.sortname = sortname;
	}
	
	/**
	 * Gets the sortorder.
	 *
	 * @return the sortorder
	 */
	public String getSortorder() {
		return sortorder;
	}
	
	/**
	 * Sets the sortorder.
	 *
	 * @param sortorder the new sortorder
	 */
	public void setSortorder(String sortorder) {
		this.sortorder = sortorder;
	}

	public int getPageNo() {
		return pageNo;
	}

	public void setPageNo(int pageNo) {
		this.pageNo = pageNo;
	}
	
	
}
