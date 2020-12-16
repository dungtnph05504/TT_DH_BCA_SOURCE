package com.nec.asia.nic.framework.job.scheduler.impl;

import static org.quartz.JobBuilder.newJob;
import static org.quartz.SimpleScheduleBuilder.simpleSchedule;
import static org.quartz.TriggerBuilder.newTrigger;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.StringTokenizer;

import org.apache.commons.collections.CollectionUtils;
import org.apache.log4j.Logger;
import org.quartz.CronScheduleBuilder;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SimpleTrigger;
import org.quartz.Trigger;
import org.quartz.TriggerKey;
import org.quartz.simpl.SimpleJobFactory;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;

import com.nec.asia.nic.framework.PageRequest;
import com.nec.asia.nic.framework.PaginatedResult;
import com.nec.asia.nic.framework.admin.job.domain.Job;
import com.nec.asia.nic.framework.admin.job.domain.JobExecutionDetails;
import com.nec.asia.nic.framework.admin.job.domain.JobExecutionHistory;
import com.nec.asia.nic.framework.admin.job.domain.JobExecutionHistoryLatest;
import com.nec.asia.nic.framework.admin.job.domain.JobSchedule;
import com.nec.asia.nic.framework.dao.DaoException;
import com.nec.asia.nic.framework.job.scheduler.JobScheduleJobListener;
import com.nec.asia.nic.framework.job.scheduler.JobScheduleService;
import com.nec.asia.nic.framework.job.scheduler.JobScheduleTriggerListener;
import com.nec.asia.nic.framework.job.scheduler.dataAccess.JobScheduleDao;
import com.nec.asia.nic.framework.job.scheduler.dataAccess.type.ActionOnEvent;
import com.nec.asia.nic.framework.job.scheduler.dataAccess.type.Frequency;
import com.nec.asia.nic.framework.job.scheduler.dataAccess.type.JobStatus;
import com.nec.asia.nic.framework.job.scheduler.exception.CreateJobException;
import com.nec.asia.nic.framework.job.scheduler.exception.CreateJobExecutionException;
import com.nec.asia.nic.framework.job.scheduler.exception.CreateScheduleException;
import com.nec.asia.nic.framework.job.scheduler.exception.JobScheduleException;
import com.nec.asia.nic.framework.job.scheduler.exception.UpdateJobException;
import com.nec.asia.nic.utils.DateUtil;
import com.nec.asia.nic.utils.StringUtil;


/**
 * The Class JobScheduleServiceImpl.
 *
 * @author boon_wui
 * @since 14 April 208
 * @version 1.0
 */
public class JobScheduleServiceImpl implements JobScheduleService {

	/** The _logger. */
	private static Logger _logger = Logger.getLogger(JobScheduleServiceImpl.class);

	/** The dao. */
	//@Autowired
	JobScheduleDao jobScheduleDao = null;
	
	   /** The scheduler. */
    private Scheduler scheduler = null;
    
    /** The scheduler factory. */
    private SchedulerFactoryBean schedulerFactory = null;
    
	/* (non-Javadoc)
	 * @see com.nec.asia.nic.framework.job.scheduler.JobScheduleService#startScheduler()
	 */
	public void startScheduler() throws JobScheduleException {
	    
	    _logger.debug("JobScheduleServiceImpl(): startScheduler - Start");
	       try {
	            JobScheduleTriggerListener listener = new JobScheduleTriggerListener();
	            
	            scheduler.getListenerManager().addTriggerListener(listener);
	            //scheduler.addTriggerListener(listener);
	            
	            scheduler.start();
	        }
	        catch (SchedulerException e) {
	            throw new JobScheduleException("Fail to start scheduler.");
	        }
	}

	/* (non-Javadoc)
	 * @see com.nec.asia.nic.framework.job.scheduler.JobScheduleService#standbyScheduler()
	 */
	public void standbyScheduler() throws JobScheduleException {
	       _logger.debug("JobScheduleQuartzImpl(): standbyScheduler - Start");

	        try {
	            scheduler.standby();
	        }
	        catch (SchedulerException e) {
	            throw new JobScheduleException("Fail to standby scheduler.");
	        }
	}

	/**
	 * @param jobScheduleDao the jobScheduleDao to set
	 */
	public void setJobScheduleDao(JobScheduleDao jobScheduleDao) {
		this.jobScheduleDao = jobScheduleDao;
	}

	/**
	 * To retrieve Job record by job ID.
	 *
	 * @param jobId String
	 * @return Job
	 * @throws JobScheduleException the job schedule exception
	 */
	public Job getJob(String jobId) throws JobScheduleException {
		try {
			Job job = this.jobScheduleDao.getJob(jobId);

			return job;
		}
		catch (DaoException de) {
			throw new JobScheduleException("Error while getting job by job id.", de);
		}
	}
	
	/**
	 * To retrieve Job record list by job name.
	 *
	 * @param jobName the job name
	 * @return List
	 * @throws JobScheduleException the job schedule exception
	 */
	public List<Job> getJobByName(String jobName) throws JobScheduleException {
		try{

			List<Job> jobs = this.jobScheduleDao.getJobByName(jobName);
			return jobs;		
		
		}
		catch(DaoException de) {
			throw new JobScheduleException("Error while getting jobs by name.", de);
		}
	}

	/**
	 * To retrieve Job schedule record list by job id.
	 *
	 * @param jobId String
	 * @return List
	 * @throws JobScheduleException the job schedule exception
	 */
	public List<JobSchedule> getJobScheduleByJobId(String jobId) throws JobScheduleException {
		try{
			
			List<JobSchedule> jobScheduleList = this.jobScheduleDao.getJobScheduleByJobId(jobId);
			return jobScheduleList;
		
		}
		catch(DaoException de) {
			throw new JobScheduleException("Error while getting job schedule list by job id.", de);
		}
	}

	/**
	 * To retrieve Job schedule record by primary keys.
	 *
	 * @param jobId String
	 * @param scheduleName String
	 * @return JobSchedule
	 * @throws JobScheduleException the job schedule exception
	 */
	public JobSchedule getJobScheduleByPrimaryKey(String jobId, String scheduleName) throws JobScheduleException {
		try{

			_logger.debug("JobScheduleServiceImpl(): getJobScheduleByPrimaryKey() - Start");
			
			JobSchedule jobSchedule = this.jobScheduleDao.getJobScheduleByPrimaryKey(jobId, scheduleName);
			return jobSchedule;		
		
		}
		catch(DaoException de) {
			throw new JobScheduleException("Error while getting job schedule list by job id.", de);
		}
	}
	
	/**
	 * To retrieve a list of active job records.
	 *
	 * @return List
	 * @throws JobScheduleException the job schedule exception
	 */
	public List<Job> getActiveJobs() throws JobScheduleException {
		try{

			List<Job> jobs = this.jobScheduleDao.getActiveJobs();
			return jobs;

		}
		catch(DaoException de){
			throw new JobScheduleException("Error while getting all active jobs.", de);
		}
	}

	/**
	 * To retrieve a list of all job records.
	 *
	 * @return List
	 * @throws JobScheduleException the job schedule exception
	 */
	public List<Job> getAllJobs() throws JobScheduleException {
		try{

			List<Job> jobs=this.jobScheduleDao.getAllJobs();
			return jobs;
		
		}
		catch(DaoException de){
			throw new JobScheduleException("Error while getting all jobs.", de);
		}
	}
	
	/**
	 * To retrieve a list of all job records by systemId
	 *
	 * @return List
	 * @throws JobScheduleException the job schedule exception
	 */
	public List getAllJobsBySystemId(String systemId) throws JobScheduleException {
		try{

			List<Job> jobs=this.jobScheduleDao.getAllJobsBySystemId(systemId);
			return jobs;
		
		}
		catch(DaoException de){
			throw new JobScheduleException("Error while getting all jobs with systemId: " + systemId, de);
		}
	}
	
	/**
	 * To delete a job record.
	 *
	 * @param job Job
	 * @throws CreateScheduleException the create schedule exception
	 */
	public void deleteJob(Job job) throws CreateScheduleException {

		_logger.debug("JobScheduleServiceImpl(): deleteJob(Job job) - Start");

		if (job!=null) {
			this.deleteJob(job.getJobId());
		}
	}

	/**
	 * To delete a job record by job ID.
	 *
	 * @param jobId String
	 * @throws CreateScheduleException the create schedule exception
	 */
	public void deleteJob(String jobId) throws CreateScheduleException {

		_logger.debug("JobScheduleServiceImpl(): deleteJob(String jobId) - Start");
		
        Job job = null;
        
        try {
            if (jobId != null) {
                
                job = this.jobScheduleDao.getJob(jobId);

                if (job != null) {
                    this.jobScheduleDao.deleteJob(job);
                    //[04 Mar 2016][Tue] - Edit
                    //this.deleteJobScheduler(job.getJobName(), job.getJobId());
                }
            }
        }
        catch(DaoException de) {
            throw new CreateScheduleException("Job not found in database.");
        }
	}

    /**
     * Delete Job schedule with job id and schedule name as parameters.
     *
     * @param scheduleName String
     * @param jobId String
     * @throws CreateScheduleException the create schedule exception
     */
    public void deleteJobSchedule(String scheduleName, String jobId) throws CreateScheduleException {

        JobSchedule jobSchedule = null;
        
        try {
            if (jobId != null && scheduleName != null) {
                
                jobSchedule = this.jobScheduleDao.getJobScheduleByPrimaryKey(jobId, scheduleName);

                if (jobSchedule != null) {
                    this.jobScheduleDao.deleteJobSchedule(jobSchedule);
                    this.deleteTrigger(scheduleName, jobId);
                }
            }
        }
        catch(DaoException de) {
            throw new CreateScheduleException("Job not found in database.");
        }
    }

    /**
     * Delete Quartz Scheduler trigger record with job name and job group as parameters.
     *
     * @param scheduleName the schedule name
     * @param jobId the job id
     * @throws CreateScheduleException the create schedule exception
     */
    private void deleteTrigger(String scheduleName, String jobId) throws CreateScheduleException {
        try {
            scheduler.unscheduleJob(TriggerKey.triggerKey(scheduleName, jobId));
        }
        catch (Exception e) {
            throw new CreateScheduleException(e);
        }
    }

    /**
     * Delete Quartz Scheduler record with job id and job group as parameters.
     *
     * @param jobId String
     * @throws CreateScheduleException the create schedule exception
     */
    private void deleteJobScheduler(String jobName, String jobId) throws CreateScheduleException {
        try {
            scheduler.deleteJob(JobKey.jobKey(jobName, jobId));
        }
        catch (Exception e) {
            throw new CreateScheduleException(e);
        }
    }
	/**
	 * To create a job record.
	 *
	 * @param job Job
	 * @throws CreateJobException the create job exception
	 * @throws SchedulerException 
	 */
	public void createJob(Job job) throws CreateJobException, SchedulerException {			
		this.createJob(job, null);
	}

	/**
	 * To create a job record with groupName.
	 *
	 * @param job Job
	 * @param groupName the group name
	 * @throws CreateJobException the create job exception
	 * @throws SchedulerException 
	 */
	public void createJob(Job job, String groupName) throws CreateJobException, SchedulerException {
	    try {
            this.jobScheduleDao.createJob(job);
            
            JobScheduleJobListener jobListener = new JobScheduleJobListener();
            JobDetail jobDetail = newJob(job.getJobImplClass())
                    .withIdentity(job.getJobName(), job.getJobId())
                    .withDescription(job.getJobDescription())
                    .storeDurably(true)
                    .requestRecovery(false)
                    .usingJobData(this.getJobDataMap(job.getJobProperties()))
                    .build();
            
            scheduler.getListenerManager().addJobListener(jobListener);
            scheduler.addJob(jobDetail, true);
        }
        catch (DaoException de) {
            throw new CreateJobException("Failed to create job details in the database.");
        }
        catch (Exception e) {
            throw new CreateJobException(e);
        }		
	}

	/**
	 * To update a job record.
	 *
	 * @param job Job
	 * @throws UpdateJobException the update job exception
	 * @throws SchedulerException 
	 */
	public void updateJob(Job job) throws UpdateJobException, SchedulerException {
	    try {
            this.jobScheduleDao.updateJob(job);
        }
        catch (DaoException de)
        {
            de.printStackTrace();
            throw new UpdateJobException("Failed to update job details in the database.");
        }
        
        JobScheduleJobListener jobListener = new JobScheduleJobListener();
        
        JobDetail jobDetail = newJob(job.getJobImplClass())
                .withIdentity(job.getJobName(), job.getJobId())
                .withDescription(job.getJobDescription())
                .storeDurably(true)
                .requestRecovery(false)
                .usingJobData(this.getJobDataMap(job.getJobProperties()))
                .build();
        
        scheduler.getListenerManager().addJobListener(jobListener);

        try {

            scheduler.addJob(jobDetail, true); //add job again
        }
        catch (Exception e) {
            e.printStackTrace();
            throw new UpdateJobException(e);
        }
	}

	/**
	 * Delete Quartz Scheduler record with job name and job group as parameters.
	 *
	 * @param jobName String
	 * @param jobGroup String
	 * @throws CreateScheduleException the create schedule exception
	 */
	public void deleteJob(String jobName, String jobGroup) throws CreateScheduleException {
	}
	
	
	/**
	 * To schedule a job record in NSYS_SCHEDULE table.
	 *
	 * @param job Job
	 * @param schedule JobSchedule
	 * @param action String
	 * @throws CreateScheduleException the create schedule exception
	 */
	public void scheduleJob(Job job, JobSchedule schedule, String action) throws CreateScheduleException {
	    if (schedule != null && job != null) {    

            //make start date earlier by one day (Specific)
            Date startDateOneDayEarlier = DateUtil.addDays(schedule.getStartDate(), -1);

            //make end date later by one day (Specific)
            Date endDateOneDayLater = DateUtil.addDays(schedule.getStartDate(), 1);
            
            // Determine the cronExpression to schedule the job based on the frequency
            if (Frequency.Daily.equals(schedule.getFrequency())) {
                if (schedule.getStartHour() < 0 || schedule.getStartHour() > 24) {
                    throw new CreateScheduleException("Invalid Hour parameter. Hour must be between 0 and 24.");
                }
        
                if (schedule.getStartMin() < 0 || schedule.getStartMin() > 60) {
                    throw new CreateScheduleException("Invalid Minute parameter. Minute must be between 0 and 60.");
                }
                
                String cronExpression = "";

                cronExpression = cronExpression.concat("0 ");
                cronExpression = cronExpression.concat(String.valueOf(schedule.getStartMin()) + " ");
                cronExpression = cronExpression.concat(String.valueOf(schedule.getStartHour()) + " ");
                cronExpression = cronExpression.concat("? * *");

                if ("ADD".equalsIgnoreCase(action) && schedule.isActive()) {
                    this.scheduleCronJob(schedule.getId().getScheduleName(), job.getJobName(), job.getJobId(),
                            schedule.getStartDate(), schedule.getEndDate(), cronExpression,
                            schedule.getActionOnFailure());         
                }
                else if ("AMEND".equalsIgnoreCase(action) && schedule.isActive()) {
                    this.rescheduleCronJob(schedule.getId().getScheduleName(), job.getJobName(), job.getJobId(),
                            schedule.getStartDate(), schedule.getEndDate(), cronExpression,
                            schedule.getActionOnFailure());         
                }
            }
            else if (Frequency.Weekly.equals(schedule.getFrequency())) {

                if (schedule.getStartHour() < 0 || schedule.getStartHour() > 24) {
                    throw new CreateScheduleException("Invalid Hour parameter. Hour must be between 0 and 24.");
                }
        
                if (schedule.getStartMin() < 0 || schedule.getStartMin() > 60) {
                    throw new CreateScheduleException("Invalid Minute parameter. Minute must be between 0 and 60.");
                }

                String cronExpression = "";

                cronExpression = cronExpression.concat("0 ");
                cronExpression = cronExpression.concat(String.valueOf(schedule.getStartMin()) + " ");
                cronExpression = cronExpression.concat(String.valueOf(schedule.getStartHour()) + " ");
                cronExpression = cronExpression.concat("? * ");

                String dayOfWeek = "";
                
                for (int i = 0; i < schedule.getDayofWeek().length; i++) {
                    _logger.info("JobScheduleServiceQuartzImpl(): schedule.getDayofWeek()[] - " + schedule.getDayofWeek()[i]);
                    dayOfWeek = dayOfWeek.concat(schedule.getDayofWeek()[i] + ",");
                }

                _logger.info("JobScheduleServiceQuartzImpl(): dayOfWeek - " + dayOfWeek.substring(0, dayOfWeek.length() - 1));
                
                cronExpression = cronExpression.concat(dayOfWeek.substring(0, dayOfWeek.length() - 1));
                
                if ("ADD".equalsIgnoreCase(action) && schedule.isActive()) {

                    _logger.info("JobScheduleServiceQuartzImpl(): Weekly - ADD");
                    
                    this.scheduleCronJob(schedule.getId().getScheduleName(), job.getJobName(), job.getJobId(),
                            schedule.getStartDate(), schedule.getEndDate(), cronExpression,
                            schedule.getActionOnFailure());         
                }
                else if ("AMEND".equalsIgnoreCase(action) && schedule.isActive()) {

                    _logger.info("JobScheduleServiceQuartzImpl(): Weekly - AMEND");

                    this.rescheduleCronJob(schedule.getId().getScheduleName(), job.getJobName(), job.getJobId(),
                            schedule.getStartDate(), schedule.getEndDate(), cronExpression,
                            schedule.getActionOnFailure());         
                }
            }
            else if (Frequency.Monthly.equals(schedule.getFrequency())) {

                if (schedule.getStartHour() < 0 || schedule.getStartHour() > 24) {
                    throw new CreateScheduleException("Invalid Hour parameter. Hour must be between 0 and 24.");
                }
        
                if (schedule.getStartMin() < 0 || schedule.getStartMin() > 60) {
                    throw new CreateScheduleException("Invalid Minute parameter. Minute must be between 0 and 60.");
                }

                String cronExpression = "";

                cronExpression = cronExpression.concat("0 ");
                cronExpression = cronExpression.concat(String.valueOf(schedule.getStartMin()) + " ");
                cronExpression = cronExpression.concat(String.valueOf(schedule.getStartHour()) + " ");

                if ("LAST".equalsIgnoreCase(schedule.getDayOfMonth())) {
                    cronExpression = cronExpression.concat("L ");
                }
                else {
                    cronExpression = cronExpression.concat(schedule.getDayOfMonth() + " ");
                }
                
                cronExpression = cronExpression.concat("* ?");

                if ("ADD".equalsIgnoreCase(action) && schedule.isActive()) {
                    this.scheduleCronJob(schedule.getId().getScheduleName(), job.getJobName(), job.getJobId(),
                            schedule.getStartDate(), schedule.getEndDate(), cronExpression,
                            schedule.getActionOnFailure());         
                }
                else if ("AMEND".equalsIgnoreCase(action) && schedule.isActive()) {
                    this.rescheduleCronJob(schedule.getId().getScheduleName(), job.getJobName(), job.getJobId(),
                            schedule.getStartDate(), schedule.getEndDate(), cronExpression,
                            schedule.getActionOnFailure());         
                }
            }
            else if (Frequency.Yearly.equals(schedule.getFrequency())) {

                if (schedule.getStartHour() < 0 || schedule.getStartHour() > 24) {
                    throw new CreateScheduleException("Invalid Hour parameter. Hour must be between 0 and 24.");
                }
        
                if (schedule.getStartMin() < 0 || schedule.getStartMin() > 60) {
                    throw new CreateScheduleException("Invalid Minute parameter. Minute must be between 0 and 60.");
                }

                String cronExpression = "";

                cronExpression = cronExpression.concat("0 ");
                cronExpression = cronExpression.concat(String.valueOf(schedule.getStartMin()) + " ");
                cronExpression = cronExpression.concat(String.valueOf(schedule.getStartHour()) + " ");
                cronExpression = cronExpression.concat(schedule.getYearlyDayOfMonth() + " "); //day of month
                cronExpression = cronExpression.concat(schedule.getYearlyMonth() + " "); //month
                cronExpression = cronExpression.concat("? *");

                if ("ADD".equalsIgnoreCase(action) && schedule.isActive()) {
                    this.scheduleCronJob(schedule.getId().getScheduleName(), job.getJobName(), job.getJobId(),
                            schedule.getStartDate(), schedule.getEndDate(), cronExpression,
                            schedule.getActionOnFailure());         
                }
                else if ("AMEND".equalsIgnoreCase(action) && schedule.isActive()) {
                    this.rescheduleCronJob(schedule.getId().getScheduleName(), job.getJobName(), job.getJobId(),
                            schedule.getStartDate(), schedule.getEndDate(), cronExpression,
                            schedule.getActionOnFailure());         
                }
            }
            else if (Frequency.Specific.equals(schedule.getFrequency())) {          

                if (schedule.getStartHour() < 0 || schedule.getStartHour() > 24) {
                    throw new CreateScheduleException("Invalid Hour parameter. Hour must be between 0 and 24.");
                }
        
                if (schedule.getStartMin() < 0 || schedule.getStartMin() > 60) {
                    throw new CreateScheduleException("Invalid Minute parameter. Minute must be between 0 and 60.");
                }

                String startDate = DateUtil.parseDate(schedule.getStartDate(), DateUtil.FORMAT_YYYYMMDD);
                
                String cronExpression = "";

                cronExpression = cronExpression.concat("0 ");
                cronExpression = cronExpression.concat(String.valueOf(schedule.getStartMin()) + " ");
                cronExpression = cronExpression.concat(String.valueOf(schedule.getStartHour()) + " ");
                cronExpression = cronExpression.concat(startDate.substring(6, 8) + " "); //day of month
                cronExpression = cronExpression.concat(startDate.substring(4, 6) + " "); //month
                cronExpression = cronExpression.concat("? ");
                cronExpression = cronExpression.concat(startDate.substring(0, 4));
            
                if ("ADD".equalsIgnoreCase(action) && schedule.isActive()) {
                    this.scheduleCronJob(schedule.getId().getScheduleName(), job.getJobName(), job.getJobId(),
                            startDateOneDayEarlier, endDateOneDayLater, cronExpression, 
                            schedule.getActionOnFailure());         
                }
                else if ("AMEND".equalsIgnoreCase(action) && schedule.isActive()) {
                    this.rescheduleCronJob(schedule.getId().getScheduleName(), job.getJobName(), job.getJobId(),
                            startDateOneDayEarlier, endDateOneDayLater, cronExpression,
                            schedule.getActionOnFailure());         
                }
            }
            else if (Frequency.RepeatAtInterval.equals(schedule.getFrequency())) {
                if ("ADD".equalsIgnoreCase(action) && schedule.isActive()) {
                    this.scheduleSimpleJob(schedule.getId().getScheduleName(), job.getJobName(), job.getJobId(),
                            schedule.getStartDate(), schedule.getEndDate(), schedule.getRepeatCount(), 
                            schedule.getRepeatIntervalSecond(), schedule.getActionOnFailure());
                }
                else if ("AMEND".equalsIgnoreCase(action) && schedule.isActive()) {
                    this.rescheduleSimpleJob(schedule.getId().getScheduleName(), job.getJobName(), job.getJobId(),
                            schedule.getStartDate(), schedule.getEndDate(), schedule.getRepeatCount(), 
                            schedule.getRepeatIntervalSecond(), schedule.getActionOnFailure());
                }
            }
            else if (Frequency.RepeatInDefinitely.equals(schedule.getFrequency())) {            
                if ("ADD".equalsIgnoreCase(action) && schedule.isActive()) {
                    this.scheduleSimpleJob(schedule.getId().getScheduleName(), job.getJobName(), job.getJobId(),
                            schedule.getStartDate(), schedule.getEndDate(), 
                            SimpleTrigger.REPEAT_INDEFINITELY, schedule.getRepeatIntervalSecond(),
                            schedule.getActionOnFailure());
                }
                else if ("AMEND".equalsIgnoreCase(action) && schedule.isActive()) {
                    this.rescheduleSimpleJob(schedule.getId().getScheduleName(), job.getJobName(), job.getJobId(),
                            schedule.getStartDate(), schedule.getEndDate(), 
                            SimpleTrigger.REPEAT_INDEFINITELY, schedule.getRepeatIntervalSecond(),
                            schedule.getActionOnFailure());
                }
            }
//          else if (Frequency.RepeatBetweenTime.equals(schedule.getFrequency())) {
//                if (schedule.getStartHour() < 0 || schedule.getStartHour() > 24) {
//                    throw new CreateScheduleException("Invalid Hour parameter. Start Hour must be between 0 and 24.");
//                }
//        
//                if (schedule.getEndHour() < 0 || schedule.getEndHour() > 24) {
//                    throw new CreateScheduleException("Invalid Hour parameter. End Hour must be between 0 and 24.");
//                }
//
//                if (schedule.getStartHour() > schedule.getEndHour()) {
//                    throw new CreateScheduleException("Invalid Hour parameter. Start Hour should be same or less than End Hour.");
//                }
//
//                String startDate = DateUtil.parseDate(schedule.getStartDate(), DateUtil.FORMAT_YYYYMMDD);
//                
//                String cornHourExpression=String.valueOf(schedule.getStartHour());
//                
//                String cronExpression = "";
//                long hours = schedule.getRepeatIntervalSecond()/(60*60);
//                long minutes = (schedule.getRepeatIntervalSecond() - (hours * 60))/60;
//                long seconds = schedule.getRepeatIntervalSecond()-(hours * 60)-(minutes * 60);
//                
//                cronExpression = cronExpression.concat(String.valueOf(seconds)+" ");
//                cronExpression = cronExpression.concat(String.valueOf(minutes) + " ");
//                cronExpression = cronExpression.concat(String.valueOf(hours) + " ");
//                cronExpression = cronExpression.concat(startDate.substring(6, 8) + " "); //day of month
//                cronExpression = cronExpression.concat(startDate.substring(4, 6) + " "); //month
//                cronExpression = cronExpression.concat("? ");
//                cronExpression = cronExpression.concat(startDate.substring(0, 4));
//               
//                
//                if ("ADD".equalsIgnoreCase(action) && schedule.isActive()) {
//                    this.scheduleCronJob(schedule.getId().getScheduleName(), job.getJobName(), job.getJobId(),
//                            startDateOneDayEarlier, endDateOneDayLater, cronExpression, 
//                            schedule.getActionOnFailure());         
//                }
//                else if ("AMEND".equalsIgnoreCase(action) && schedule.isActive()) {
//                    this.rescheduleCronJob(schedule.getId().getScheduleName(), job.getJobName(), job.getJobId(),
//                            startDateOneDayEarlier, endDateOneDayLater, cronExpression,
//                            schedule.getActionOnFailure());         
//                }
//          }
            /*JobScheduleId scheduleId = new JobScheduleId();
            scheduleId.setJobId(job.getJobId());
            scheduleId.setScheduleName(job.getJobName());
            schedule.setId(scheduleId);*/

            try {
                if ("ADD".equalsIgnoreCase(action)) {
                    this.jobScheduleDao.createJobSchedule(schedule);
                }
                else if ("AMEND".equalsIgnoreCase(action)) {
                    this.jobScheduleDao.updateJobSchedule(schedule);
                }
            }
            catch(DaoException de) {
                throw new CreateScheduleException(de);
            }           
        }
	}

    /**
     * To schedule a job using cron expression. This allows the job to be
     * scheduled to run on a fixed period. (Private Method)
     * 
     * A cron expression is a string comprised of 6 or 7 fields separated by
     * white space. Fields can contain any of the allowed values, along with
     * various combinations of the allowed special characters for that field.
     * The fields are as follows:
     * 
     * Field Name Mandatory? Allowed Values Allowed Special Characters
     * Seconds YES 0-59 , - * /
     * Minutes YES 0-59 , - * /
     * Hours YES 0-23 , - * /
     * Day of month YES 1-31 , - * ? / L W C
     * Month YES 1-12 or JAN-DEC , - * /
     * Day of week YES 1-7 or SUN-SAT , - * ? / L C #
     * Year NO empty, 1970-2099 , - * /
     * 
     * See details on Cron Expression from Open Symphony at
     * http://www.opensymphony.com/quartz/wikidocs/CronTriggers%20Tutorial.html
     *
     * @param scheduleName String
     * @param jobName String
     * @param jobId String
     * @param startDate Date
     * @param endDate Date
     * @param cronExpression the cron expression
     * @param actionOnFailure the action on failure
     * @throws CreateScheduleException the create schedule exception
     */
    private void scheduleCronJob(String scheduleName,
                                String jobName,
                                String jobId,
                                Date startDate,
                                Date endDate,
                                String cronExpression,
                                ActionOnEvent actionOnFailure) throws CreateScheduleException {

        try {
            
            JobDetail job = scheduler.getJobDetail(JobKey.jobKey(jobName, jobId));
            
            if (job == null) {
                throw new CreateScheduleException("Job not found.");
            }
            
            if (startDate == null) {
                startDate = new Date();
            }
            
            Trigger trg = newTrigger()
                    .withIdentity(scheduleName, jobId)
                    .startAt(startDate)
                    .endAt(endDate)
                    .withSchedule(CronScheduleBuilder.cronSchedule(cronExpression))
                    .forJob(job)
                    .build();
            
            scheduler.scheduleJob(trg);
            
            /*CronTrigger cronTrigger = new CronTrigger(scheduleName, jobGroupName);
            cronTrigger.setJobName(jobName);

            cronTrigger.setStartTime(startDate);
            cronTrigger.setEndTime(endDate);

            CronExpression cexp = new CronExpression(cronExpression);
            
            //Assign the CronExpression to CronTrigger
            cronTrigger.setCronExpression(cexp);
            cronTrigger.setJobGroup(jobGroupName);
            
            //Trigger Listener - Start
            cronTrigger.addTriggerListener(JobScheduleTriggerListener.triggerName);
            //Trigger Listener - End
            */
        }
        catch (Exception se) {
            throw new CreateScheduleException(se);
        }
    }

    /**
     * To reschedule a job using cron expression. This allows the job to be
     * scheduled to run on a fixed period. (Private Method)
     *
     * @param scheduleName String
     * @param jobName String
     * @param jobId String
     * @param startDate Date
     * @param endDate Date
     * @param cronExpression the cron expression
     * @param actionOnFailure the action on failure
     * @throws CreateScheduleException the create schedule exception
     */
    private void rescheduleCronJob(String scheduleName, 
                                   String jobName, 
                                   String jobId,
                                   Date startDate,
                                   Date endDate,
                                   String cronExpression,
                                   ActionOnEvent actionOnFailure) throws CreateScheduleException {

        try {
            
            JobDetail job = scheduler.getJobDetail(JobKey.jobKey(jobName, jobId));
            
            if (job == null) {
                throw new CreateScheduleException("Job not found.");
            }
            
            if (startDate == null) {
                startDate = new Date();
            }
            
            Trigger trg = newTrigger()
                    .withIdentity(scheduleName, jobId)
                    .startAt(startDate)
                    .endAt(endDate)
                    .withSchedule(CronScheduleBuilder.cronSchedule(cronExpression))
                    .forJob(job)
                    .build();
            
            scheduler.unscheduleJob(TriggerKey.triggerKey(scheduleName, jobId));
            scheduler.scheduleJob(trg);
            
            /*JobDetail job = scheduler.getJobDetail(jobName, jobGroupName);

            if (job == null) {
                throw new CreateScheduleException("Job not found.");
            }

            CronTrigger cronTrigger = new CronTrigger(scheduleName, jobGroupName);
            cronTrigger.setJobName(jobName);
            cronTrigger.setJobGroup(jobGroupName);

            if (startDate == null) {
                startDate = new Date();
            }
            
            cronTrigger.setStartTime(startDate);
            cronTrigger.setEndTime(endDate);
            
            CronExpression cexp = new CronExpression(cronExpression);

            //Assign the CronExpression to CronTrigger
            cronTrigger.setCronExpression(cexp);

            //Trigger Listener - Start
            cronTrigger.addTriggerListener(JobScheduleTriggerListener.triggerName);
            //Trigger Listener - End
        
            //scheduler.rescheduleJob(scheduleName, null, cronTrigger);
            scheduler.unscheduleJob(scheduleName, jobGroupName);
            scheduler.scheduleJob(cronTrigger);*/
        }
        catch (Exception se) {
            throw new CreateScheduleException(se);
        }
    }

    /**
     * To schedule a simple job (Private Method).
     *
     * @param scheduleName String
     * @param jobName String
     * @param jobGroupName String
     * @param repeatCount int
     * @param repeatIntervalSecond long
     * @throws CreateScheduleException the create schedule exception
     */
    /*private void scheduleSimpleJob(String scheduleName,
                                  String jobName,
                                  String jobGroupName,
                                  int repeatCount,
                                  long repeatIntervalSecond) throws CreateScheduleException {

        this.scheduleSimpleJob(scheduleName, 
                                jobName, 
                                jobGroupName, 
                                new Date(), 
                                null, 
                                repeatCount, 
                                repeatIntervalSecond,
                                null);
    
    }*/

    /**
     * To schedule a simple job with start date and end date (Private Method).
     *
     * @param scheduleName String
     * @param jobName String
     * @param jobId String
     * @param startDate Date
     * @param endDate Date
     * @param repeatCount int
     * @param repeatIntervalSecond long
     * @param actionOnFailure the action on failure
     * @throws CreateScheduleException the create schedule exception
     */
    private void scheduleSimpleJob(String scheduleName,
                                  String jobName,
                                  String jobId,
                                  Date startDate,
                                  Date endDate,
                                  int repeatCount,
                                  long repeatIntervalSecond,
                                  ActionOnEvent actionOnFailure) throws CreateScheduleException {

        try {
            JobDetail job = scheduler.getJobDetail(JobKey.jobKey(jobName, jobId));
            
            if (job == null) {
                throw new CreateScheduleException("Job not found with jobId: " + jobId + ", jobName: " + jobName);
            }
            
            if (startDate == null) {
                startDate = new Date();
            }
            
            Trigger trg = newTrigger()
                    .withIdentity(scheduleName, jobId)
                    .startAt(startDate)
                    .endAt(endDate)
                    .withSchedule(simpleSchedule()
                            .withIntervalInMilliseconds(repeatIntervalSecond * 1000L)
                            .withRepeatCount(repeatCount))
                    .forJob(job)
                    .build();
            
            
            scheduler.scheduleJob(trg);
            /*JobDetail job = scheduler.getJobDetail(jobName, jobGroupName);
            
            if (job == null) {
                throw new CreateScheduleException("Job not found.");
            }

            SimpleTrigger trigger = new SimpleTrigger(scheduleName, jobGroupName, startDate, endDate, repeatCount, repeatIntervalSecond * 1000L);

            trigger.setJobName(jobName);
            trigger.setJobGroup(jobGroupName);

            //Trigger Listener - Start
            trigger.addTriggerListener(JobScheduleTriggerListener.triggerName);
            //Trigger Listener - End
             
            scheduler.scheduleJob(trigger);*/
        }
        catch (Exception se) {
            throw new CreateScheduleException(se);
        }
    }

    /**
     * To reschedule a simple job with start date and end date (Private Method).
     *
     * @param scheduleName String
     * @param jobName String
     * @param jobId String
     * @param startDate Date
     * @param endDate Date
     * @param repeatCount int
     * @param repeatIntervalSecond long
     * @param actionOnFailure the action on failure
     * @throws CreateScheduleException the create schedule exception
     */
    private void rescheduleSimpleJob(String scheduleName,
                                     String jobName,
                                     String jobId,
                                     Date startDate,
                                     Date endDate,
                                     int repeatCount,
                                     long repeatIntervalSecond,
                                     ActionOnEvent actionOnFailure) throws CreateScheduleException {
        try {
            JobDetail job = scheduler.getJobDetail(JobKey.jobKey(jobName, jobId));
            
            if (job == null) {
                throw new CreateScheduleException("Job not found with jobId: " + jobId + ", jobName: " + jobName);
            }
            
            if (startDate == null) {
                startDate = new Date();
            }
            
            Trigger trg = newTrigger()
                    .withIdentity(scheduleName, jobId)
                    .startAt(startDate)
                    .endAt(endDate)
                    .withSchedule(simpleSchedule()
                            .withIntervalInMilliseconds(repeatIntervalSecond * 1000L)
                            .withRepeatCount(repeatCount))
                    .forJob(job)
                    .build();
            
            scheduler.unscheduleJob(TriggerKey.triggerKey(scheduleName, jobId));
            scheduler.scheduleJob(trg);
            
            /*JobDetail job = scheduler.getJobDetail(jobName, jobGroupName);

            if (job == null) {
                throw new CreateScheduleException("Job not found.");
            }

            SimpleTrigger trigger = new SimpleTrigger(scheduleName, jobGroupName, startDate, endDate, repeatCount, repeatIntervalSecond * 1000L);

            trigger.setJobName(jobName);
            trigger.setJobGroup(jobGroupName);

            //Trigger Listener - Start
            trigger.addTriggerListener(JobScheduleTriggerListener.triggerName);
            //Trigger Listener - End
            
            scheduler.unscheduleJob(scheduleName, jobGroupName);
            scheduler.scheduleJob(trigger);*/
        }
        catch (Exception se) {
            throw new CreateScheduleException(se);
        }
    }

	/**
	 * To enable trigger.
	 *
	 * @param scheduleName String
	 * @param jobId String
	 * @throws JobScheduleException the job schedule exception
	 */
	public void enableTrigger(String scheduleName, String jobId) throws JobScheduleException {
	       try {
	            scheduler.resumeTrigger(TriggerKey.triggerKey(scheduleName, jobId));
	        }
	        catch (SchedulerException e) {
	            throw new JobScheduleException("Fail to enable trigger.");
	        }
	}

	/**
	 * To disable trigger.
	 *
	 * @param scheduleName String
	 * @param jobId String
	 * @throws JobScheduleException the job schedule exception
	 */
	public void disableTrigger(String scheduleName, String jobId) throws JobScheduleException {
	       try {
	            scheduler.unscheduleJob(TriggerKey.triggerKey(scheduleName, jobId));
	        }
	        catch (SchedulerException e) {
	            throw new JobScheduleException("Fail to disable trigger.");
	        }
	}

	/**
	 * To create new record for job execution history.
	 *
	 * @param history JobExecutionHistory
	 * @throws CreateJobExecutionException the create job execution exception
	 */
	public void createJobExecutionHistory(JobExecutionHistory history) throws CreateJobExecutionException {
		try {
			this.jobScheduleDao.createJobExecutionHistory(history);
		}
		catch(DaoException de) {
			throw new CreateJobExecutionException("Failed to create job execution history in the database.");
		}		
	}

	/**
	 * To create new record for job execution history with details.
	 *
	 * @param history JobExecutionHistory
	 * @param details the details
	 * @throws CreateJobExecutionException the create job execution exception
	 */
	public void createJobExecutionHistory(JobExecutionHistory history, List<JobExecutionDetails> details) throws CreateJobExecutionException {
		try {
			this.jobScheduleDao.createJobExecutionHistory(history);
			if (details!=null){
				for (int i=0;i<details.size();i++){
					JobExecutionDetails detail=details.get(i);
					detail.setLogId(history.getLogId());
					this.jobScheduleDao.createJobExecutionDetail(detail);
				}
			}
		}
		catch(DaoException de) {
			throw new CreateJobExecutionException("Failed to create job execution history in the database.");
		}		
	}

	/**
	 * To retrieve latest job execution history group by Job ID.
	 *
	 * @return List
	 * @throws JobScheduleException the job schedule exception
	 */
	@SuppressWarnings("unchecked")
	public List<JobExecutionHistoryLatest> getJobExecutionHistoryLatest() throws JobScheduleException {
				
		try {
			List<JobExecutionHistoryLatest> jobExecutionHistoryLatest = new ArrayList<JobExecutionHistoryLatest> ();
			
			List<Object[]> jobDetailSchLatestList = this.jobScheduleDao.getJobExecutionHistoryLatest();
			
			if (CollectionUtils.isNotEmpty(jobDetailSchLatestList)) {
				for (int i = 0; i < jobDetailSchLatestList.size(); i++) {

					  JobExecutionHistoryLatest exeHistLatest = new JobExecutionHistoryLatest();
					
		              Object[] obj = (Object[]) jobDetailSchLatestList.get(i);

		              exeHistLatest.setLogId((Long) obj[0]);
		              exeHistLatest.setJobId((String) obj[1]);
		              exeHistLatest.setExecutionDate((Date) obj[2]);
		              exeHistLatest.setStatus((Integer) obj[3]);
		              exeHistLatest.setMessage((String) obj[4]);
		              exeHistLatest.setJobName((String) obj[5]);
		              exeHistLatest.setJobStatus((String) obj[6]);
		              exeHistLatest.setLastExecutionDate((Date) obj[7]);
		              exeHistLatest.setNextExecutionDate((Date) obj[8]);
		              		              
		              jobExecutionHistoryLatest.add(exeHistLatest);
				}				
			}
			
			return jobExecutionHistoryLatest;
		}
		catch(DaoException de) {
			throw new JobScheduleException("Failed to retrieve job execution history in the database.");
		}		
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.nec.asia.baf.comp.admin.scheduler.JobScheduleService#getJobExecutionHistoryLatest(java.lang.String)
	 */
	public List<JobExecutionHistoryLatest> getJobExecutionHistoryLatest(String systemId) throws JobScheduleException {
		
		try {
			List<JobExecutionHistoryLatest> jobExecutionHistoryLatest = new ArrayList<JobExecutionHistoryLatest> ();
			
			List<Object[]> jobDetailSchLatestList = this.jobScheduleDao.getJobExecutionHistoryLatest(systemId);
			
			if (CollectionUtils.isNotEmpty(jobDetailSchLatestList)) {
				for (int i = 0; i < jobDetailSchLatestList.size(); i++) {

					  JobExecutionHistoryLatest exeHistLatest = new JobExecutionHistoryLatest();
					
		              Object[] obj = (Object[]) jobDetailSchLatestList.get(i);

		              exeHistLatest.setLogId((Long) obj[0]);
		              exeHistLatest.setJobId((String) obj[1]);
		              exeHistLatest.setExecutionDate((Date) obj[2]);
		              exeHistLatest.setStatus((Integer) obj[3]);
		              exeHistLatest.setMessage((String) obj[4]);
		              exeHistLatest.setJobName((String) obj[5]);
		              exeHistLatest.setJobStatus((String) obj[6]);
		              exeHistLatest.setLastExecutionDate((Date) obj[7]);
		              exeHistLatest.setNextExecutionDate((Date) obj[8]);
		              		              
		              jobExecutionHistoryLatest.add(exeHistLatest);
				}				
			}
			
			return jobExecutionHistoryLatest;
		}
		catch(DaoException de) {
			throw new JobScheduleException("Failed to retrieve job execution history in the database.");
		}		
	}
	

	/**
	 * To retrieve job execution history by primary key.
	 *
	 * @param logId String
	 * @return the job execution history by primary key
	 * @throws JobScheduleException the job schedule exception
	 */
	public JobExecutionHistory getJobExecutionHistoryByPrimaryKey(Long logId) throws JobScheduleException {		
		try {
			JobExecutionHistory hist = this.jobScheduleDao.getJobExecutionHistoryByPrimaryKey(logId);
			return hist;
		}
		catch(DaoException de) {
			throw new JobScheduleException("Failed to retrieve job execution history by primary key in the database.");
		}		
	}
	
	/* (non-Javadoc)
	 * @see com.nec.asia.nic.framework.job.scheduler.JobScheduleService#deleteLogById(java.lang.Long)
	 */
	public void deleteLogById(Long logId) throws JobScheduleException {
		try {
			if (logId != null) {
				JobExecutionHistory jobExecutionHistory = this.jobScheduleDao.getJobExecutionHistoryByPrimaryKey(logId);
				if (jobExecutionHistory != null) {
					this.jobScheduleDao.deleteJobExecutionHistory(jobExecutionHistory);
				}
			}
		}
		catch(DaoException de) {
			throw new JobScheduleException("Job not found in database.");
		}
	}
	
	/* (non-Javadoc)
	 * @see com.nec.asia.nic.framework.job.scheduler.JobScheduleService#deleteLogHistory(java.lang.String)
	 */
	public void deleteLogHistory(String jobId) throws JobScheduleException {
		try {
			this.jobScheduleDao.deleteJobExecutionHistory(jobId);
		} catch(DaoException de) {
			throw new JobScheduleException("Job not found in database.");
		}
	}
	
	/* (non-Javadoc)
	 * @see com.nec.asia.nic.framework.job.scheduler.JobScheduleService#getExecutionDetails(java.lang.Long)
	 */
	public List<JobExecutionDetails> getExecutionDetails(Long logId) throws JobScheduleException{
		try {
			return jobScheduleDao.getExecutionDetails(logId);
		}
		catch(DaoException de) {
			de.printStackTrace();
			throw new JobScheduleException("Failed to retrieve job execution details.");
		}		
	}
	
	/* (non-Javadoc)
	 * @see com.nec.asia.nic.framework.job.scheduler.JobScheduleService#getExecutionDetails(java.lang.Long, java.lang.String)
	 */
	public List<JobExecutionDetails> getExecutionDetails(Long logId, String detailType) throws JobScheduleException{
		try {
			return jobScheduleDao.getExecutionDetails(logId, detailType);
		}
		catch(DaoException de) {
			de.printStackTrace();
			throw new JobScheduleException("Failed to retrieve job execution details.");
		}		
	}
	
	/**
	 * Set a delayed trigger if an exception happens to the job.
	 *
	 * @param scheduleName String
	 * @param jobName String
	 * @param jobId String
	 * @param delayTime long
	 * @throws JobScheduleException the job schedule exception
	 */
	public void setDelayedSimpleTrigger(String scheduleName, String jobName, String jobId, long delayTime) throws JobScheduleException {

        long startTime = System.currentTimeMillis() + delayTime;

        Date startDate = new Date(startTime);
        
        _logger.debug("JobScheduleServiceQuartzImpl(): setDelayedSimpleTrigger(): startDate - " + startDate);
        
        try {

            Trigger trg = newTrigger()
                    .withIdentity(scheduleName + "_DelayTrig_" + startDate, jobId)
                    .startAt(startDate)
                    .withSchedule(simpleSchedule()
                            .withIntervalInMilliseconds(0L)
                            .withRepeatCount(0))
                    .forJob(JobKey.jobKey(jobName, jobId))
                    .build();
            
            scheduler.scheduleJob(trg);
            
            /*SimpleTrigger trigger = new SimpleTrigger(scheduleName + "_DelayTrig_" + startDate,
                    jobId, 
                    startDate, 
                    null, 
                    0,
                    0L);

            trigger.setJobName(jobName);
            trigger.setJobGroup(jobId);

            //Trigger Listener - Start
            trigger.addTriggerListener(JobScheduleTriggerListener.triggerName);
            //Trigger Listener - End
             */
                        
        }
        catch (SchedulerException e) {
            throw new JobScheduleException("Fail to schedule delayed trigger.");
        }
	}

	/* (non-Javadoc)
	 * @see com.nec.asia.nic.framework.job.scheduler.JobScheduleService#updateJobStatus(java.lang.String, com.nec.asia.nic.framework.job.scheduler.dataAccess.type.JobStatus)
	 */
	public void updateJobStatus(String jobId, JobStatus status) throws JobScheduleException{
		try {
			Job job = this.jobScheduleDao.getJob(jobId);
			if (job!=null){
				job.setJobStatus(status);
				jobScheduleDao.updateJob(job);
			}else{
				throw new JobScheduleException("Job (" + jobId + ") does not exists.");
			}
		}catch(DaoException de) {
			de.printStackTrace();
			throw new JobScheduleException("Failed to update job status.");
		}	
	}

	public void updateJobExecutionStatus(String jobId, Date executedDateTime, Date nextExecutionDate, JobStatus status) throws JobScheduleException{
		try {
			Job job = this.jobScheduleDao.getJob(jobId);
			if (job!=null){
				job.setLastExecutedDate(executedDateTime);
				job.setNextExecutionDate(nextExecutionDate);
				job.setJobStatus(status);
				jobScheduleDao.updateJob(job);
			}else{
				throw new JobScheduleException("Job (" + jobId + ") does not exists.");
			}
		}catch(DaoException de) {
			de.printStackTrace();
			throw new JobScheduleException("Failed to update job status.");
		}	
	}
	
    /* (non-Javadoc)
     * @see com.nec.asia.nic.framework.job.scheduler.JobScheduleService#deleteJobExecutionHistoryForHousekeeping(java.lang.Integer)
     */
    public void deleteJobExecutionHistoryForHousekeeping(Integer retentionDays) throws JobScheduleException
    {
        _logger.trace(" In JobScheduleServiceImpl  :deleteJobExecutionHistoryForHousekeeping() - Start");
        throw new JobScheduleException("Operation not Supported!");
//        try {
//            jobScheduleDao.deleteJobExecutionHistoryForHousekeeping(retentionDays);
//        }catch(Exception ex) {
//            throw new JobScheduleException("Error while deleteJobExecutionHistoryForHousekeeping: "+ex.getMessage(), ex);
//        }   
    }
    
    
    /**
     * Gets the job data map.
     *
     * @param properties the properties
     * @return the job data map
     */
    private JobDataMap getJobDataMap (String properties) {
        JobDataMap result = new JobDataMap();
        if (properties!=null && !StringUtil.isEmpty(properties) ) {
            StringTokenizer token = new StringTokenizer(properties,"\n");
            _logger.debug("Tokenizing...");
            while (token.hasMoreTokens()) {
                StringTokenizer tokenTemp = new StringTokenizer((String)token.nextToken(),"=");
                if (tokenTemp.hasMoreTokens()){
                    String property = (String)tokenTemp.nextToken();
                    String value = "";
                    if (tokenTemp.hasMoreTokens()){
                        value=(String) tokenTemp.nextToken();
                        value = value.replaceAll("\n", "");
                        value = value.replaceAll("\r", "");
                    }
                    this._logger.debug("Property=" + property +"; value=" + value);
                    result.put(property, value);
                }
            }
        }
        return result;
    }
    

    /**
     * Sets the scheduler.
     *
     * @param scheduler the new scheduler
     */
    public void setScheduler(Scheduler scheduler) {
        this.scheduler = scheduler;
        
        try {
            scheduler.setJobFactory(new SimpleJobFactory()); // added to force spring 3.0 to work with Quartz 2.*
            
            JobScheduleJobListener jobListener = new JobScheduleJobListener();
            scheduler.getListenerManager().addJobListener(jobListener);
            
            JobScheduleTriggerListener listener = new JobScheduleTriggerListener();
            scheduler.getListenerManager().addTriggerListener(listener);
        } catch (SchedulerException e) {
            _logger.fatal("Fail to set scheduler job factor as SimpleJobFactory");
        }   
    }

    /**
     * Sets the scheduler factory.
     *
     * @param schedulerFactory the new scheduler factory
     * @throws SchedulerException 
     */
    public void setSchedulerFactory(SchedulerFactoryBean schedulerFactory) {
        this.schedulerFactory = schedulerFactory;
        if (this.schedulerFactory !=null){
           this.scheduler=schedulerFactory.getObject();
            
            try {
                scheduler.setJobFactory(new SimpleJobFactory()); // added to force spring 3.0 to work with Quartz 2.*
                
                JobScheduleJobListener jobListener = new JobScheduleJobListener();
                scheduler.getListenerManager().addJobListener(jobListener);
                
                JobScheduleTriggerListener listener = new JobScheduleTriggerListener();
                scheduler.getListenerManager().addTriggerListener(listener);
            } catch (SchedulerException e) {
                _logger.fatal("Fail to set scheduler job factor as SimpleJobFactory");
            }   
        }
    }
    
	@Override
	public PaginatedResult<Job> findAllJobForPagination(PageRequest pageRequest)
			throws JobScheduleException {
		try {
			return this.jobScheduleDao.findAllJobs(pageRequest);
		} catch (DaoException e) {
			e.printStackTrace();
			throw new JobScheduleException("Failed to get all job for pagination.");
		}
	}

	@Override
	public PaginatedResult<JobExecutionHistory> findJobExecForPaginationByJobId(String jobId, PageRequest pageRequest)
			throws JobScheduleException {
		try {
			return this.jobScheduleDao.findJobExecHistoryByJobId(jobId, pageRequest);
		} catch (DaoException e) {
			e.printStackTrace();
			throw new JobScheduleException("Failed to get job exec history for pagination.");
		}
	}

	@Override
	public PaginatedResult<JobSchedule> findJobScheForPaginationByJobId(String jobId, PageRequest pageRequest)
			throws JobScheduleException {
		try {
			return this.jobScheduleDao.findJobScheByJobId(jobId, pageRequest);
		} catch (DaoException e) {
			e.printStackTrace();
			throw new JobScheduleException("Failed to get job schedule for pagination.");
		}
	}

	@Override
	public PaginatedResult<JobExecutionDetails> findJobExecDetailsForPaginationByLogId(Long logId, String fileterName, 
			PageRequest pageRequest) throws JobScheduleException {
		try {
			return this.jobScheduleDao.findJobExecDetailsByLogId(logId, fileterName, pageRequest);
		} catch (DaoException e) {
			e.printStackTrace();
			throw new JobScheduleException("Failed to get job exec details for pagination.");
		}
	}
	
	@Override
	public PaginatedResult<JobExecutionHistoryLatest> findAllJobExecLatestForPagination(PageRequest pageRequest) throws JobScheduleException {
		try {
			return this.jobScheduleDao.findAllJobExecLatest(pageRequest);
		} catch (DaoException e) {
			e.printStackTrace();
			throw new JobScheduleException("Failed to get job exec latest for pagination.");
		}
	}
	
}
