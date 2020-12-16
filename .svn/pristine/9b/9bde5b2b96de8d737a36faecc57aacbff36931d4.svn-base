package com.nec.asia.nic.framework.report.domain;

import com.nec.asia.nic.framework.report.domain.Report;

// Generated May 16, 2013 6:46:21 PM by Hibernate Tools 3.4.0.CR1

/*
 * Modification History:
 * 23-Jun-2017 (chris): update equals and hashCode methods.
 */
public class ReportParameterId implements java.io.Serializable {

	private static final long serialVersionUID = -4250456256277451795L;
	
	private Report report;
	private String paraName;

	public ReportParameterId() {
	}

	public ReportParameterId(Report report, String paraName) {
		this.report = report;
		this.paraName = paraName;
	}

	public Report getReport() {
		return this.report;
	}

	public void setReport(Report report) {
		this.report = report;
	}

	public String getParaName() {
		return this.paraName;
	}

	public void setParaName(String paraName) {
		this.paraName = paraName;
	}

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof ReportParameterId))
			return false;
		ReportParameterId castOther = (ReportParameterId) other;
		
		boolean sameReportObject = this.getReport() == castOther.getReport();
		boolean equalReportObject = this.getReport() != null && castOther.getReport() != null && this.getReport().equals(castOther.getReport());
		boolean equalReportId = this.getReport() != null && castOther.getReport() != null && this.getReport().getReportId()!=null && this.getReport().getReportId().equals(castOther.getReport().getReportId());
		boolean sameParaName = this.getParaName() == castOther.getParaName();
		boolean equalParaName = this.getParaName() != null && castOther.getParaName() != null && this.getParaName().equals(castOther.getParaName());
		
		return (sameReportObject || equalReportObject || equalReportId) && (sameParaName || equalParaName);
	}

	public int hashCode() {
		int result = 17;
		//result = 37	* result + (getReport() == null ? 0 : this.getReport().hashCode());
		result = 37	* result + (getReport() == null || getReport().getReportId()==null ? 0 : this.getReport().getReportId().hashCode());
		result = 37 * result + (getParaName() == null ? 0 : this.getParaName().hashCode());
		return result;
	}

}
