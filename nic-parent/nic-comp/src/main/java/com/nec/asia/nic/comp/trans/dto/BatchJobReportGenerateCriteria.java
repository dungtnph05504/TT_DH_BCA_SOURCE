package com.nec.asia.nic.comp.trans.dto;

import java.util.ArrayList;
import java.util.Date;

public class BatchJobReportGenerateCriteria {
	private ArrayList<String> selColumns;
	private String batchJobStartDate,batchJobEndDate,jobId,jobStatus,site,reportFormat;
	private Date startDate;
	private Date endDate;
	public String getBatchJobStartDate() {
		return batchJobStartDate;
	}

	public void setBatchJobStartDate(String batchJobStartDate) {
		this.batchJobStartDate = batchJobStartDate;
	}

	public String getBatchJobEndDate() {
		return batchJobEndDate;
	}

	public void setBatchJobEndDate(String batchJobEndDate) {
		this.batchJobEndDate = batchJobEndDate;
	}

	public String getJobId() {
		return jobId;
	}

	public void setJobId(String jobId) {
		this.jobId = jobId;
	}

	public String getJobStatus() {
		return jobStatus;
	}

	public void setJobStatus(String jobStatus) {
		this.jobStatus = jobStatus;
	}

	public String getSite() {
		return site;
	}

	public void setSite(String site) {
		this.site = site;
	}

	public String getReportFormat() {
		return reportFormat;
	}

	public void setReportFormat(String reportFormat) {
		this.reportFormat = reportFormat;
	}

	public ArrayList<String> getSelColumns() {
		return selColumns;
	}

	public void setSelColumns(ArrayList<String> selColumns) {
		this.selColumns = selColumns;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

}
