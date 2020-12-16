package com.nec.asia.nic.framework.job.scheduler.dataAccess;

import java.util.List;

import org.hibernate.criterion.Order;

import com.nec.asia.nic.framework.PageRequest;
import com.nec.asia.nic.framework.PaginatedResult;
import com.nec.asia.nic.framework.admin.job.domain.Job;
import com.nec.asia.nic.framework.admin.job.domain.JobExecutionDetails;
import com.nec.asia.nic.framework.admin.job.domain.JobExecutionHistory;
import com.nec.asia.nic.framework.admin.job.domain.JobExecutionHistoryLatest;
import com.nec.asia.nic.framework.admin.job.domain.JobSchedule;
import com.nec.asia.nic.framework.dao.DaoException;
import com.nec.asia.nic.framework.dao.GenericDao;

/**
 * Job Scheduler Data Access Object Interface.
 * 
 * @author boon_wui
 * @since 14 April 208
 * @version 1.0
 *
 */

/*
 * Modification History:
 * 18 Nov 2011 (Alvin Chua): Change LogId from String to Long to avoid type cast exception. 
 */
public interface JobScheduleDao extends GenericDao<JobSchedule, String> {
	
	/**
	 * Gets the job.
	 *
	 * @param jobId the job id
	 * @return the job
	 * @throws DaoException the dao exception
	 */
	public Job getJob(String jobId) throws DaoException;
	
	/**
	 * Gets the job by name.
	 *
	 * @param jobName the job name
	 * @return the job by name
	 * @throws DaoException the dao exception
	 */
	public List<Job> getJobByName(String jobName) throws DaoException;
	
	/**
	 * Gets the job schedule by job id.
	 *
	 * @param jobId the job id
	 * @return the job schedule by job id
	 * @throws DaoException the dao exception
	 */
	public List<JobSchedule> getJobScheduleByJobId(String jobId) throws DaoException;
	
	/**
	 * Gets the job schedule by primary key.
	 *
	 * @param jobId the job id
	 * @param scheduleName the schedule name
	 * @return the job schedule by primary key
	 * @throws DaoException the dao exception
	 */
	public JobSchedule getJobScheduleByPrimaryKey(String jobId, String scheduleName) throws DaoException;
	
	/**
	 * Gets the active jobs.
	 *
	 * @return the active jobs
	 * @throws DaoException the dao exception
	 */
	public List<Job> getActiveJobs() throws DaoException;	
	
	/**
	 * Gets the all jobs.
	 *
	 * @return the all jobs
	 * @throws DaoException the dao exception
	 */
	public List<Job> getAllJobs() throws DaoException;	
	
	/**
	 * Creates the job.
	 *
	 * @param job the job
	 * @throws DaoException the dao exception
	 */
	public void createJob(Job job) throws DaoException;
	
	/**
	 * Update job.
	 *
	 * @param job the job
	 * @throws DaoException the dao exception
	 */
	public void updateJob(Job job) throws DaoException;
	
	/**
	 * Delete job.
	 *
	 * @param job the job
	 * @throws DaoException the dao exception
	 */
	public void deleteJob(Job job) throws DaoException;
	
	/**
	 * Creates the job schedule.
	 *
	 * @param jobSchedule the job schedule
	 * @throws DaoException the dao exception
	 */
	public void createJobSchedule(JobSchedule jobSchedule) throws DaoException;
	
	/**
	 * Update job schedule.
	 *
	 * @param jobSchedule the job schedule
	 * @throws DaoException the dao exception
	 */
	public void updateJobSchedule(JobSchedule jobSchedule) throws DaoException;
	
	/**
	 * Delete job schedule.
	 *
	 * @param jobSchedule the job schedule
	 * @throws DaoException the dao exception
	 */
	public void deleteJobSchedule(JobSchedule jobSchedule) throws DaoException;	
	
	/**
	 * Creates the job execution history.
	 *
	 * @param history the history
	 * @throws DaoException the dao exception
	 */
	public void createJobExecutionHistory(JobExecutionHistory history) throws DaoException;
	
	/**
	 * Gets the job execution history latest.
	 *
	 * @return the job execution history latest
	 * @throws DaoException the dao exception
	 */
	public List getJobExecutionHistoryLatest() throws DaoException;

	/**
	 * 
	 * @param systemId
	 * @return
	 * @throws DaoException
	 */
	public List getJobExecutionHistoryLatest(String systemId) throws DaoException;
	
	/**
	 * Gets the job execution history by primary key.
	 *
	 * @param logId the log id
	 * @return the job execution history by primary key
	 * @throws DaoException the dao exception
	 */
	public JobExecutionHistory getJobExecutionHistoryByPrimaryKey(Long logId) throws DaoException;
	
	/**
	 * Delete job execution history.
	 *
	 * @param jobExecutionHistory the job execution history
	 * @throws DaoException the dao exception
	 */
	public void deleteJobExecutionHistory(JobExecutionHistory jobExecutionHistory) throws DaoException;
	
	/**
	 * Creates the job execution detail.
	 *
	 * @param detail the detail
	 * @throws DaoException the dao exception
	 */
	public void createJobExecutionDetail(JobExecutionDetails detail) throws DaoException;
	
	/**
	 * Gets the execution details.
	 *
	 * @param logId the log id
	 * @return the execution details
	 * @throws DaoException the dao exception
	 */
	public List<JobExecutionDetails> getExecutionDetails(Long logId) throws DaoException;
	
	/**
	 * Gets the execution details.
	 *
	 * @param logId the log id
	 * @param detailType the detail type
	 * @return the execution details
	 * @throws DaoException the dao exception
	 */
	public List<JobExecutionDetails> getExecutionDetails(Long logId, String detailType) throws DaoException;
	
//	/**
//	 * Delete job execution history for housekeeping.
//	 *
//	 * @param retentionDays the retention days
//	 * @throws DaoException the dao exception
//	 */
//	public void deleteJobExecutionHistoryForHousekeeping(Integer retentionDays) throws DaoException;
	
	/**
	 * Delete job execution history.
	 *
	 * @param jobId the job id
	 * @throws DaoException the dao exception
	 */
	public void deleteJobExecutionHistory(String jobId) throws DaoException;

	/**
	 * Gets all job by system id
	 * 
	 * @param systemId
	 * @return
	 * @throws DaoException
	 */
	public List<Job> getAllJobsBySystemId(String systemId) throws DaoException;
	
	public PaginatedResult<Job> findAllJobs(PageRequest pageRequest) throws DaoException;
	public PaginatedResult<JobExecutionHistory> findJobExecHistoryByJobId(String jobId, PageRequest pageRequest) throws DaoException;
	public PaginatedResult<JobSchedule> findJobScheByJobId(String jobId, PageRequest pageRequest) throws DaoException;
	public PaginatedResult<JobExecutionDetails> findJobExecDetailsByLogId(Long logId, String filterName, PageRequest pageRequest) throws DaoException;
	public PaginatedResult<JobExecutionHistoryLatest> findAllJobExecLatest(PageRequest pageRequest) throws DaoException;
}
