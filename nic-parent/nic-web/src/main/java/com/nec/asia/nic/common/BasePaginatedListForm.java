package com.nec.asia.nic.common;

import java.io.Serializable;

/**

 * @author Ambrocio,Paulo Johannes D.
 * @Company: NEC ASIA PACIFIC PTE
 * @since : Jul 12, 2013
 * <p>
 *	Base Model for paginated forms
 * </p>
 * 
 *
 */
public class BasePaginatedListForm implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -5467186533178483081L;
	private int pageNo = 1;
	private int pageSize = 10;
    private int startIndex = 0;

    public BasePaginatedListForm() {

	}

	/**
	 * @return the pageNo
	 */
	public int getPageNo() {
		return pageNo;
	}

	/**
	 * @param pageNo the pageNo to set
	 */
	public void setPageNo(int pageNo) {
		this.pageNo = pageNo;
	}

	/**
	 * @return the pageSize
	 */
	public int getPageSize() {
		return pageSize;
	}

	/**
	 * @param pageSize the pageSize to set
	 */
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	/**
	 * @return the startIndex
	 */
	public int getStartIndex() {
		return startIndex;
	}

	/**
	 * @param startIndex the startIndex to set
	 */
	public void setStartIndex(int startIndex) {
		this.startIndex = startIndex;
	}

    
    
}
