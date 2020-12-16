package com.nec.asia.nic.framework.admin.job.domain;

import java.util.Date;

import com.nec.asia.nic.framework.dataAccess.Dbo;


/**
 * The Class JobExecutionHistory.
 *
 * @author boon_wui
 * @since 14 April 208
 * @version 1.0
 */
 
/*
 * Modification History:
 * 18 Nov 2011 (Alvin Chua): Change LogId from String to Long to avoid type cast exception. 
 */
public class JobExecutionHistory extends Dbo {

	/**
     * serialVersionUID
     */
    private static final long serialVersionUID = 1L;

    /** The log id. */
	private Long logId;

	/** The job id. */
	private String jobId;

	/** The execution date. */
	private Date executionDate;

	/** The complete date. */
	private Date completeDate;

	/** The status. */
	private int status = 0;

	/** The message. */
	private String message;

	/** The detail. */
	private String detail;
	
	/** The system id. */
	//private String systemId;
	
	/** The job execution details. */
	//private JobExecutionDetails jobExecutionDetails;

	/**
	 * Gets the log id.
	 *
	 * @return the log id
	 */
	public Long getLogId() {
		return logId;
	}

	/**
	 * Sets the log id.
	 *
	 * @param logId the new log id
	 */
	public void setLogId(Long logId) {
		this.logId = logId;
	}

	/**
	 * Gets the job id.
	 *
	 * @return the job id
	 */
	public String getJobId() {
		return jobId;
	}

	/**
	 * Sets the job id.
	 *
	 * @param jobId the new job id
	 */
	public void setJobId(String jobId) {
		this.jobId = jobId;
	}

	/**
	 * Gets the execution date.
	 *
	 * @return the execution date
	 */
	public Date getExecutionDate() {
		return executionDate;
	}

	/**
	 * Sets the execution date.
	 *
	 * @param executionDate the new execution date
	 */
	public void setExecutionDate(Date executionDate) {
		this.executionDate = executionDate;
	}

	/**
	 * Gets the status.
	 *
	 * @return the status
	 */
	public int getStatus() {
		return status;
	}

	/**
	 * Sets the status.
	 *
	 * @param status the new status
	 */
	public void setStatus(int status) {
		this.status = status;
	}

	/**
	 * Gets the message.
	 *
	 * @return the message
	 */
	public String getMessage() {
		return message;
	}

	/**
	 * Sets the message.
	 *
	 * @param message the new message
	 */
	public void setMessage(String message) {
		this.message = message;
	}

	/**
	 * Gets the detail.
	 *
	 * @return the detail
	 */
	public String getDetail() {
		return detail;
	}

	/**
	 * Sets the detail.
	 *
	 * @param detail the new detail
	 */
	public void setDetail(String detail) {
		this.detail = detail;
	}

	/**
	 * Gets the complete date.
	 *
	 * @return the complete date
	 */
	public Date getCompleteDate() {
		return completeDate;
	}

	/**
	 * Sets the complete date.
	 *
	 * @param completeDate the new complete date
	 */
	public void setCompleteDate(Date completeDate) {
		this.completeDate = completeDate;
	}

	/**
	 * @return the systemId
	 *//*
	public String getSystemId() {
		return systemId;
	}

	*//**
	 * @param systemId the systemId to set
	 *//*
	public void setSystemId(String systemId) {
		this.systemId = systemId;
	}
*/
    /**
     * @return the jobExecutionDetails
     *//*
    public JobExecutionDetails getJobExecutionDetails() {
        return jobExecutionDetails;
    }

    *//**
     * @param jobExecutionDetails the jobExecutionDetails to set
     *//*
    public void setJobExecutionDetails(JobExecutionDetails jobExecutionDetails) {
        this.jobExecutionDetails = jobExecutionDetails;
    }*/
}
