package com.nec.asia.nic.framework.admin.job.domain;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.nec.asia.nic.framework.dataAccess.Dbo;
import com.nec.asia.nic.framework.job.scheduler.dataAccess.type.JobStatus;


/**
 * The Class Job.
 *
 * @author boon_wui
 * @since 14 April 208
 * @version 1.0
 */

/*
 * Modification History:
 * 12 August 2013 (Peddi Swapna): Added secured attribute .
 */
public class Job extends Dbo {
	
	/**
     * serialVersionUID
     */
    private static final long serialVersionUID = 1L;

    /** The job id. */
	private String jobId = "";

	/** The job name. */
	private String jobName = "";
	
	/** The job description. */
	private String jobDescription = "";
	
	/** The active. */
	private boolean active;

	/** The job class. */
	private String jobClass;
	
	/** The job properties. */
	private String jobProperties;
	
	/** The job status. */
	private JobStatus jobStatus = JobStatus.Waiting;

	/** The job impl class. */
	private Class jobImplClass;
	
	/** The schedule. */
	@JsonManagedReference
	private Set<JobSchedule> jobSchedule = new HashSet<JobSchedule>();
	
	/** The createdate. */
	private Date createdate;
	
	/** The updatedate. */
	private Date updatedate;

	/** The last executed datetime. */
	private Date lastExecutedDate;

	/** The next scheduled execution datetime. */
	private Date nextExecutionDate;
	
	/** The createby. */
	private String createby;
	
	/** The updateby. */
	private String updateby;
	
	/** The secured. */
	private boolean secured;
	
	/** The system Id. */
	private String systemId;
	
	/** The next planned date. */
	private Date nextPlannedDate;
	/**
	 * Instantiates a new job.
	 */
	public Job() {
		super();
	}

	/**
	 * Instantiates a new job.
	 *
	 * @param jobName the job name
	 */
	public Job(String jobName) {
		super();
		this.jobName = jobName;
	}

	/**
	 * Instantiates a new job.
	 *
	 * @param jobName the job name
	 * @param jobDescription the job description
	 */
	public Job(String jobName, String jobDescription) {
		super();
		this.jobName = jobName;
		this.jobDescription = jobDescription;
	}

	/**
	 * Gets the job name.
	 *
	 * @return the job name
	 */
	public String getJobName() {
		return jobName;
	}

	/**
	 * Sets the job name.
	 *
	 * @param jobName the new job name
	 */
	public void setJobName(String jobName) {
		this.jobName = jobName;
	}

	/**
	 * Gets the job description.
	 *
	 * @return the job description
	 */
	public String getJobDescription() {
		return jobDescription;
	}

	/**
	 * Sets the job description.
	 *
	 * @param jobDescription the new job description
	 */
	public void setJobDescription(String jobDescription) {
		this.jobDescription = jobDescription;
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
	 * Checks if is active.
	 *
	 * @return true, if is active
	 */
	public boolean isActive() {
		return active;
	}

	/**
	 * Sets the active.
	 *
	 * @param active the new active
	 */
	public void setActive(boolean active) {
		this.active = active;
	}

	/**
	 * Gets the job impl class.
	 *
	 * @return the job impl class
	 */
	public Class getJobImplClass() {
		return jobImplClass;
	}

	/**
	 * Sets the job impl class.
	 *
	 * @param jobImplClass the new job impl class
	 */
	public void setJobImplClass(Class jobImplClass) {
		this.jobImplClass = jobImplClass;
	}

	/**
	 * Gets the createdate.
	 *
	 * @return the createdate
	 */
	public Date getCreatedate() {
		return createdate;
	}

	/**
	 * Sets the createdate.
	 *
	 * @param createdate the new createdate
	 */
	public void setCreatedate(Date createdate) {
		this.createdate = createdate;
	}

	/**
	 * Gets the updatedate.
	 *
	 * @return the updatedate
	 */
	public Date getUpdatedate() {
		return updatedate;
	}

	/**
	 * Sets the updatedate.
	 *
	 * @param updatedate the new updatedate
	 */
	public void setUpdatedate(Date updatedate) {
		this.updatedate = updatedate;
	}

	/**
	 * Gets the createby.
	 *
	 * @return the createby
	 */
	public String getCreateby() {
		return createby;
	}

	/**
	 * Sets the createby.
	 *
	 * @param createby the new createby
	 */
	public void setCreateby(String createby) {
		this.createby = createby;
	}

	/**
	 * Gets the updateby.
	 *
	 * @return the updateby
	 */
	public String getUpdateby() {
		return updateby;
	}

	/**
	 * Sets the updateby.
	 *
	 * @param updateby the new updateby
	 */
	public void setUpdateby(String updateby) {
		this.updateby = updateby;
	}

	/**
	 * Gets the job class.
	 *
	 * @return the job class
	 */
	public String getJobClass() {
		return jobClass;
	}

	/**
	 * Sets the job class.
	 *
	 * @param jobClass the new job class
	 */
	public void setJobClass(String jobClass) {
		this.jobClass = jobClass;
	}

	/**
	 * Gets the job properties.
	 *
	 * @return the job properties
	 */
	public String getJobProperties() {
		return jobProperties;
	}

	/**
	 * Sets the job properties.
	 *
	 * @param jobProperties the new job properties
	 */
	public void setJobProperties(String jobProperties) {
		this.jobProperties = jobProperties;
	}

    /**
     * @return the jobSchedule
     */
    public Set<JobSchedule> getJobSchedule() {
        return jobSchedule;
    }

    /**
     * @param jobSchedule the jobSchedule to set
     */
    public void setJobSchedule(Set<JobSchedule> jobSchedule) {
        this.jobSchedule = jobSchedule;
    }
	/**
	 * Gets the job status.
	 *
	 * @return the job status
	 */
	public JobStatus getJobStatus() {
		return jobStatus;
	}

	/**
	 * Sets the job status.
	 *
	 * @param jobStatus the new job status
	 */
	public void setJobStatus(JobStatus jobStatus) {
		this.jobStatus = jobStatus;
	}

	public boolean getSecured() {
		return secured;
	}

	public void setSecured(boolean secured) {
		this.secured = secured;
	}

	/**
	 * @return the lastExecutedDate
	 */
	public Date getLastExecutedDate() {
		return lastExecutedDate;
	}

	/**
	 * @param lastExecutedDate the lastExecutedDate to set
	 */
	public void setLastExecutedDate(Date lastExecutedDate) {
		this.lastExecutedDate = lastExecutedDate;
	}

	/**
	 * @return the nextExecutionDate
	 */
	public Date getNextExecutionDate() {
		return nextExecutionDate;
	}

	/**
	 * @param nextExecutionDate the nextExecutionDate to set
	 */
	public void setNextExecutionDate(Date nextExecutionDate) {
		this.nextExecutionDate = nextExecutionDate;
	}

	/**
	 * @return the systemId
	 */
	public String getSystemId() {
		return systemId;
	}

	/**
	 * @param systemId the systemId to set
	 */
	public void setSystemId(String systemId) {
		this.systemId = systemId;
	}

    /**
     * @return the nextPlannedDate
     */
    public Date getNextPlannedDate() {
        return nextPlannedDate;
    }

    /**
     * @param nextPlannedDate the nextPlannedDate to set
     */
    public void setNextPlannedDate(Date nextPlannedDate) {
        this.nextPlannedDate = nextPlannedDate;
    }
}
