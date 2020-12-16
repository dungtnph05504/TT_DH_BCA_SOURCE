package com.nec.asia.nic.comp.trans.domain;

import java.util.Date;

import com.nec.asia.nic.utils.DateUtil;

/**
 * NicCardReconRptId 
 */

/* 
 * Modification History:
 * 
 * 05 Dec 2013 (chris): Initial Class
 */
@Deprecated
public class NicCardReconRptId implements java.io.Serializable {

	private static final long serialVersionUID = 6259107133754174158L;

	public static final String DATE_FORMAT_YYYYMMDD = DateUtil.FORMAT_YYYYMMDD;
	
	private String siteCode;
	private String reportCreateDate;
	
	public NicCardReconRptId() {
	}

	public NicCardReconRptId(String siteCode, String reportCreateDate) {
		this.siteCode = siteCode;
		this.reportCreateDate = reportCreateDate;
	}
	
	public NicCardReconRptId(String siteCode, Date reportCreateDate) {
		this.siteCode = siteCode;
		this.reportCreateDate = DateUtil.parseDate(reportCreateDate, DATE_FORMAT_YYYYMMDD);
	}

	public String getSiteCode() {
		return siteCode;
	}

	public void setSiteCode(String siteCode) {
		this.siteCode = siteCode;
	}

	public String getReportCreateDate() {
		return reportCreateDate;
	}

	public void setReportCreateDate(String reportCreateDate) {
		this.reportCreateDate = reportCreateDate;
	}

}
