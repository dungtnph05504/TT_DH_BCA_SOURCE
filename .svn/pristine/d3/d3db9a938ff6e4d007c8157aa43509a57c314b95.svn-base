package com.nec.asia.nic.framework.job.scheduler;

import java.util.Date;
import java.util.List;

import org.hibernate.criterion.Order;
import org.quartz.SchedulerException;

import com.nec.asia.nic.framework.PageRequest;
import com.nec.asia.nic.framework.PaginatedResult;
import com.nec.asia.nic.framework.admin.job.domain.Job;
import com.nec.asia.nic.framework.admin.job.domain.JobExecutionDetails;
import com.nec.asia.nic.framework.admin.job.domain.JobExecutionHistory;
import com.nec.asia.nic.framework.admin.job.domain.JobExecutionHistoryLatest;
import com.nec.asia.nic.framework.admin.job.domain.JobSchedule;
import com.nec.asia.nic.framework.dao.DaoException;
import com.nec.asia.nic.framework.job.scheduler.dataAccess.type.JobStatus;
import com.nec.asia.nic.framework.job.scheduler.exception.CreateJobException;
import com.nec.asia.nic.framework.job.scheduler.exception.CreateJobExecutionException;
import com.nec.asia.nic.framework.job.scheduler.exception.CreateScheduleException;
import com.nec.asia.nic.framework.job.scheduler.exception.JobScheduleException;
import com.nec.asia.nic.framework.job.scheduler.exception.UpdateJobException;

/**
 * Job Management and Execution Service Interface.
 *
 * @author alvin_chua
 */
public interface JobScheduleService {
	
	/** The Constant EVENT_TYPE_INFO. */
	public final static String EVENT_TYPE_INFO  = "I";
	
	/** The Constant EVENT_TYPE_WARN. */
	public final static String EVENT_TYPE_WARN  = "W";
	
	/** The Constant EVENT_TYPE_ERROR. */
	public final static String EVENT_TYPE_ERROR = "E";
	
	/**
	 * Creates the job.
	 *
	 * @param job the job
	 * @throws CreateJobException the create job exception
	 * @throws SchedulerException 
	 */
	public void createJob(Job job) throws CreateJobException, SchedulerException;			
	
	/**
	 * Creates the job.
	 *
	 * @param job the job
	 * @param groupName the group name
	 * @throws CreateJobException the create job exception
	 * @throws SchedulerException 
	 */
	public void createJob(Job job, String groupName) throws CreateJobException, SchedulerException;
	
	/**
	 * Update job.
	 *
	 * @param job the job
	 * @throws UpdateJobException the update job exception
	 * @throws SchedulerException 
	 */
	public void updateJob(Job job) throws UpdateJobException, SchedulerException;

	/**
	 * Delete job.
	 *
	 * @param jobId the job id
	 * @throws CreateScheduleException the create schedule exception
	 */
	public void deleteJob(String jobId) throws CreateScheduleException;
	
	/**
	 * Delete job.
	 *
	 * @param job the job
	 * @throws CreateScheduleException the create schedule exception
	 */
	public void deleteJob(Job job) throws CreateScheduleException;
	
	/**
	 * Delete job.
	 *
	 * @param jobName the job name
	 * @param jobGroup the job group
	 * @throws CreateScheduleException the create schedule exception
	 */
	public void deleteJob(String jobName, String jobGroup) throws CreateScheduleException;
	
	/**
	 * Gets the job.
	 *
	 * @param jobId the job id
	 * @return the job
	 * @throws JobScheduleException the job schedule exception
	 */
	public Job getJob(String jobId) throws JobScheduleException;
	
	/**
	 * Gets the active jobs.
	 *
	 * @return the active jobs
	 * @throws JobScheduleException the job schedule exception
	 */
	public List getActiveJobs() throws JobScheduleException;
	
	/**
	 * Gets the all jobs.
	 *
	 * @return the all jobs
	 * @throws JobScheduleException the job schedule exception
	 */
	public List getAllJobs() throws JobScheduleException;
	
	/**
	 * Gets the all jobs by systemId
	 *
	 * @return the all jobs
	 * @throws JobScheduleException the job schedule exception
	 */
	public List getAllJobsBySystemId(String systemId) throws JobScheduleException;
	
	/**
	 * Gets the job by name.
	 *
	 * @param jobName the job name
	 * @return the job by name
	 * @throws JobScheduleException the job schedule exception
	 */
	public List getJobByName(String jobName) throws JobScheduleException;

	/**
	 * Gets the job schedule by job id.
	 *
	 * @param jobId the job id
	 * @return the job schedule by job id
	 * @throws JobScheduleException the job schedule exception
	 */
	public List getJobScheduleByJobId(String jobId) throws JobScheduleException;
	
	/**
	 * Gets the job schedule by primary key.
	 *
	 * @param jobId the job id
	 * @param scheduleName the schedule name
	 * @return the job schedule by primary key
	 * @throws JobScheduleException the job schedule exception
	 */
	public JobSchedule getJobScheduleByPrimaryKey(String jobId, String scheduleName) throws JobScheduleException;
	
	/**
	 * Delete job schedule.
	 *
	 * @param scheduleName the schedule name
	 * @param jobId the job id
	 * @throws CreateScheduleException the create schedule exception
	 */
	public void deleteJobSchedule(String scheduleName, String jobId) throws CreateScheduleException;
	
	/**
	 * Schedule job.
	 *
	 * @param job the job
	 * @param schedule the schedule
	 * @param action the action
	 * @throws CreateScheduleException the create schedule exception
	 */
	public void scheduleJob(Job job, JobSchedule schedule, String action) throws CreateScheduleException;

	/**
	 * Start scheduler.
	 *
	 * @throws JobScheduleException the job schedule exception
	 */
	public void startScheduler() throws JobScheduleException;
	
	/**
	 * Standby scheduler.
	 *
	 * @throws JobScheduleException the job schedule exception
	 */
	public void standbyScheduler() throws JobScheduleException;
	
	/**
	 * Enable trigger.
	 *
	 * @param scheduleName the schedule name
	 * @param jobId the job id
	 * @throws JobScheduleException the job schedule exception
	 */
	public void enableTrigger(String scheduleName, String jobId) throws JobScheduleException;
	
	/**
	 * Disable trigger.
	 *
	 * @param scheduleName the schedule name
	 * @param jobId the job id
	 * @throws JobScheduleException the job schedule exception
	 */
	public void disableTrigger(String scheduleName, String jobId) throws JobScheduleException;
	
	/**
	 * Creates the job execution history.
	 *
	 * @param history the history
	 * @throws CreateJobExecutionException the create job execution exception
	 */
	public void createJobExecutionHistory(JobExecutionHistory history) throws CreateJobExecutionException;
	
	/**
	 * Creates the job execution history.
	 *
	 * @param history the history
	 * @param details the details
	 * @throws CreateJobExecutionException the create job execution exception
	 */
	public void createJobExecutionHistory(JobExecutionHistory history, List<JobExecutionDetails> details) throws CreateJobExecutionException;
	
	/**
	 * Gets the job execution history latest.
	 *
	 * @return the job execution history latest
	 * @throws JobScheduleException the job schedule exception
	 */
	public List<JobExecutionHistoryLatest> getJobExecutionHistoryLatest() throws JobScheduleException;
	
	/**
	 * Gets the latest job execution history by systemId
	 *  
	 * @param systemId
	 * @return
	 * @throws JobScheduleException
	 */
	public List<JobExecutionHistoryLatest> getJobExecutionHistoryLatest(String systemId) throws JobScheduleException;

	/**
	 * Gets the job execution history by primary key.
	 *
	 * @param logId the log id
	 * @return the job execution history by primary key
	 * @throws JobScheduleException the job schedule exception
	 */
	public JobExecutionHistory getJobExecutionHistoryByPrimaryKey(Long logId) throws JobScheduleException;
	
	/**
	 * Gets the execution details.
	 *
	 * @param logId the log id
	 * @return the execution details
	 * @throws JobScheduleException the job schedule exception
	 */
	public List<JobExecutionDetails> getExecutionDetails(Long logId) throws JobScheduleException;
	
	/**
	 * Gets the execution details.
	 *
	 * @param logId the log id
	 * @param detailType the detail type
	 * @return the execution details
	 * @throws JobScheduleException the job schedule exception
	 */
	public List<JobExecutionDetails> getExecutionDetails(Long logId, String detailType) throws JobScheduleException;
	
	/**
	 * Update job status.
	 *
	 * @param jobId the job id
	 * @param status the status
	 * @throws JobScheduleException the job schedule exception
	 */
	public void updateJobStatus(String jobId, JobStatus status) throws JobScheduleException;
	
	/**
	 * Sets the delayed simple trigger.
	 *
	 * @param scheduleName the schedule name
	 * @param jobName the job name
	 * @param jobId the job id
	 * @param delayTime the delay time
	 * @throws JobScheduleException the job schedule exception
	 */
	public void setDelayedSimpleTrigger(String scheduleName, String jobName, String jobId, long delayTime) throws JobScheduleException;
	
	/**
	 * Deletes job execution history along with execution details.
	 *
	 * @param retentionDays the retention days
	 * @throws JobScheduleException the job schedule exception
	 */
	public void deleteJobExecutionHistoryForHousekeeping(Integer retentionDays) throws JobScheduleException;
	
	/**
	 * Delete job execution history and detail by job id.
	 *
	 * @param logId the log id
	 * @throws JobScheduleException the job schedule exception
	 */
	public void deleteLogById(Long logId) throws JobScheduleException;
	
	/**
	 * Deletes job execution history along with execution details by job id.
	 *
	 * @param jobId the job id
	 * @throws JobScheduleException the job schedule exception
	 */
	public void deleteLogHistory(String jobId) throws JobScheduleException;
		
	/**
	 * Update the job execution status.
	 * 
	 * @param jobId
	 * @param executedDateTime
	 * @param nextExecutionDate
	 * @param status
	 * @throws JobScheduleException
	 */
	public void updateJobExecutionStatus(String jobId, Date executedDateTime, Date nextExecutionDate, JobStatus status) throws JobScheduleException;	

	public PaginatedResult<Job> findAllJobForPagination(PageRequest pageRequest) throws JobScheduleException;
	public PaginatedResult<JobExecutionHistory> findJobExecForPaginationByJobId(String jobId, PageRequest pageRequest) throws JobScheduleException;
	public PaginatedResult<JobSchedule> findJobScheForPaginationByJobId(String jobId, PageRequest pageRequest) throws JobScheduleException;
	public PaginatedResult<JobExecutionDetails> findJobExecDetailsForPaginationByLogId(Long logId, String filterName, PageRequest pageRequest) throws JobScheduleException;
	public PaginatedResult<JobExecutionHistoryLatest> findAllJobExecLatestForPagination(PageRequest pageRequest) throws JobScheduleException;
}
