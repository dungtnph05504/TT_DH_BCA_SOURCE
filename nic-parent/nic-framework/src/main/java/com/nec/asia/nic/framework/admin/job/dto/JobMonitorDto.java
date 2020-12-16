package com.nec.asia.nic.framework.admin.job.dto;

/**
 * The Class EppJobMonitorForm.
 *
 * @author Tue
 * @since 08 Mar 2016
 * @version 1.0
 */
public class JobMonitorDto {

	private String jobId;
	private String jobName;
	private long logId;
	private String executionDate;
	private String completeDate;
	private int status;
	private String message;
	private String detail;
	private String filterByName;
	private String page;
	private String pageSize;
	
	

	public String getPage() {
		return page;
	}

	public String getPageSize() {
		return pageSize;
	}

	public String getJobId() {
		return jobId;
	}

	public void setJobId(String jobId) {
		this.jobId = jobId;
	}

	public String getJobName() {
		return jobName;
	}

	public void setJobName(String jobName) {
		this.jobName = jobName;
	}

	public long getLogId() {
		return logId;
	}

	public void setLogId(long logId) {
		this.logId = logId;
	}

	public String getExecutionDate() {
		return executionDate;
	}

	public void setExecutionDate(String executionDate) {
		this.executionDate = executionDate;
	}

	public String getCompleteDate() {
		return completeDate;
	}

	public void setCompleteDate(String completeDate) {
		this.completeDate = completeDate;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getDetail() {
		return detail;
	}

	public void setDetail(String detail) {
		this.detail = detail;
	}

	public String getFilterByName() {
		return filterByName;
	}

	public void setFilterByName(String filterByName) {
		this.filterByName = filterByName;
	}
}
