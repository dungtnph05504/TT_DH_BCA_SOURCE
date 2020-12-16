package com.nec.asia.nic.framework.admin.job.domain;

import com.nec.asia.nic.framework.dataAccess.Dbo;


/**
 * The Class JobExecutionDetail.
 *
 * @author boon_wui
 * @since 14 April 208
 * @version 1.0
 */
/*
 * Modification History:
 * 18 Nov 2011 (Alvin Chua): Change LogId from String to Long to avoid type cast exception. 
 */
public class JobExecutionDetails extends Dbo{
	
	/**
     * serialVersionUID
     */
    private static final long serialVersionUID = 1L;

    /** The detail id. */
	private Long detailId;
	
	/** The detail type. */
	private String detailType;
	
	/** The message. */
	private String message;
	
	/** The log id. */
	private Long logId;
	
	/** The ext ref id1. */
	private String extRefId1;
	
	/** The ext ref id2. */
	private String extRefId2;
	
	/** The message code. */
	private String messageCode;
	
	//private JobExecutionHistory jobExecutionHistory;
	/**
	 * Gets the detail id.
	 *
	 * @return the detail id
	 */
	public Long getDetailId() {
		return detailId;
	}
	
	/**
	 * Sets the detail id.
	 *
	 * @param detailId the new detail id
	 */
	public void setDetailId(Long detailId) {
		this.detailId = detailId;
	}
	
	/**
	 * Gets the detail type.
	 *
	 * @return the detail type
	 */
	public String getDetailType() {
		return detailType;
	}
	
	/**
	 * Sets the detail type.
	 *
	 * @param detailType the new detail type
	 */
	public void setDetailType(String detailType) {
		this.detailType = detailType;
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
	
    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    public String toString()
    {
    	return ""+this.detailId;
    }
	
	/**
	 * Gets the ext ref id1.
	 *
	 * @return the ext ref id1
	 */
	public String getExtRefId1() {
		return extRefId1;
	}
	
	/**
	 * Sets the ext ref id1.
	 *
	 * @param extRefId1 the new ext ref id1
	 */
	public void setExtRefId1(String extRefId1) {
		this.extRefId1 = extRefId1;
	}
	
	/**
	 * Gets the ext ref id2.
	 *
	 * @return the ext ref id2
	 */
	public String getExtRefId2() {
		return extRefId2;
	}
	
	/**
	 * Sets the ext ref id2.
	 *
	 * @param extRefId2 the new ext ref id2
	 */
	public void setExtRefId2(String extRefId2) {
		this.extRefId2 = extRefId2;
	}
	
	/**
	 * Gets the message code.
	 *
	 * @return the message code
	 */
	public String getMessageCode() {
		return messageCode;
	}
	
	/**
	 * Sets the message code.
	 *
	 * @param messageCode the new message code
	 */
	public void setMessageCode(String messageCode) {
		this.messageCode = messageCode;
	}

  /*  *//**
     * @return the jobExecutionHistory
     *//*
    public JobExecutionHistory getJobExecutionHistory() {
        return jobExecutionHistory;
    }

    *//**
     * @param jobExecutionHistory the jobExecutionHistory to set
     *//*
    public void setJobExecutionHistory(JobExecutionHistory jobExecutionHistory) {
        this.jobExecutionHistory = jobExecutionHistory;
    }*/
}
