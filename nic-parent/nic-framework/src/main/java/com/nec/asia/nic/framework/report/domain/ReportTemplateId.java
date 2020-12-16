package com.nec.asia.nic.framework.report.domain;

import com.nec.asia.nic.framework.report.domain.Report;

// Generated May 16, 2013 6:46:21 PM by Hibernate Tools 3.4.0.CR1

public class ReportTemplateId implements java.io.Serializable {

	private String reportId;
	private String templateFileName;

	public ReportTemplateId() {
	}

	public ReportTemplateId(String reportId, String templateFileName) {
		this.reportId = reportId;
		this.templateFileName = templateFileName;
	}

	public String getTemplateFileName() {
		return this.templateFileName;
	}

	public void setTemplateFileName(String templateFileName) {
		this.templateFileName = templateFileName;
	}

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof ReportTemplateId))
			return false;
		ReportTemplateId castOther = (ReportTemplateId) other;

		return ((this.getReportId() == castOther.getReportId()) || (this
				.getReportId() != null && castOther.getReportId() != null && this
				.getReportId().equals(castOther.getReportId())))
				&& ((this.getTemplateFileName() == castOther
						.getTemplateFileName()) || (this.getTemplateFileName() != null
						&& castOther.getTemplateFileName() != null && this
						.getTemplateFileName().equals(
								castOther.getTemplateFileName())));
	}

	public int hashCode() {
		int result = 17;

		result = 37
				* result
				+ (getReportId() == null ? 0 : this.getReportId()
						.hashCode());
		result = 37
				* result
				+ (getTemplateFileName() == null ? 0 : this
						.getTemplateFileName().hashCode());
		return result;
	}

	/**
	 * @return the reportId
	 */
	public String getReportId() {
		return reportId;
	}

	/**
	 * @param reportId the reportId to set
	 */
	public void setReportId(String reportId) {
		this.reportId = reportId;
	}

}
