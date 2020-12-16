package com.nec.asia.nic.framework.job.scheduler.dataAccess.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.springframework.stereotype.Repository;

import com.nec.asia.nic.framework.PageRequest;
import com.nec.asia.nic.framework.PaginatedResult;
import com.nec.asia.nic.framework.admin.audit.domain.AuditSessionLogs;
import com.nec.asia.nic.framework.admin.job.domain.Job;
import com.nec.asia.nic.framework.admin.job.domain.JobExecutionDetails;
import com.nec.asia.nic.framework.admin.job.domain.JobExecutionHistory;
import com.nec.asia.nic.framework.admin.job.domain.JobExecutionHistoryLatest;
import com.nec.asia.nic.framework.admin.job.domain.JobSchedule;
import com.nec.asia.nic.framework.dao.DaoException;
import com.nec.asia.nic.framework.dao.impl.GenericHibernateDao;
import com.nec.asia.nic.framework.hibernate.AbstractHibernateDao;
import com.nec.asia.nic.framework.job.scheduler.dataAccess.JobScheduleDao;
import com.nec.asia.nic.utils.ExceptionMessageFormatter;


/**
 * Job Schedule Hibernate DAO Implementation.
 * 
 * @author boon_wui
 * @since 14 April 208
 * @version 1.0
 *
 */
/*
 * Modification History:
 * 
 * 04 Nov 2011 (Subash Chandra): Changes done to avoid performance issue while deleting job history table.
 * 18 Nov 2011 (Alvin Chua): Change LogId from String to Long to avoid type cast exception.
 */
@Repository("jobScheduleDao")
public class JobScheduleHibernateImpl extends GenericHibernateDao<JobSchedule, String> implements JobScheduleDao {

    /** The _logger. */
    public final Logger _logger =  LoggerFactory.getLogger(this.getClass());
	/* (non-Javadoc)
	 * @see com.nec.asia.nic.framework.job.scheduler.dataAccess.JobScheduleDao#getJob(java.lang.String)
	 */
	public Job getJob(String jobId) throws DaoException {
		//Job job = (Job)this.currentSession().get(Job.class, jobId);
		Job job = (Job) this.getHibernateTemplate().get(Job.class, jobId);
		return job;
	}

	/* (non-Javadoc)
	 * @see com.nec.asia.nic.framework.job.scheduler.dataAccess.JobScheduleDao#getJobByName(java.lang.String)
	 */
	@SuppressWarnings("unchecked")
	public List<Job> getJobByName(String jobName) throws DaoException {
		try {
			DetachedCriteria dCriteria = DetachedCriteria.forClass(Job.class);
			if (StringUtils.isNotBlank(jobName)) {
				dCriteria.add(Restrictions.like("jobName", jobName, MatchMode.ANYWHERE).ignoreCase());
			}
			dCriteria.addOrder(Order.desc("jobId"));
			List<Job> results = (List<Job>) this.getHibernateTemplate().findByCriteria(dCriteria);
			
//			List<Job> results = (List<Job>)this.currentSession().createCriteria(Job.class)
//				.add(Restrictions.eq("jobName", jobName))
//				.addOrder(Order.desc("jobId")).list();		
			return results;
		} 
		catch (HibernateException e) {
			throw new DaoException(ExceptionMessageFormatter.format(e));
		}		
	}

	/* (non-Javadoc)
	 * @see com.nec.asia.nic.framework.job.scheduler.dataAccess.JobScheduleDao#getJobScheduleByJobId(java.lang.String)
	 */
	@SuppressWarnings("unchecked")
	public List<JobSchedule> getJobScheduleByJobId(String jobId) throws DaoException {

		try {
			DetachedCriteria dCriteria = DetachedCriteria.forClass(JobSchedule.class);
			dCriteria.add(Restrictions.eq("id.jobId", jobId));
			dCriteria.addOrder(Order.desc("id.jobId"));
			List<JobSchedule> results = (List<JobSchedule>) this.getHibernateTemplate().findByCriteria(dCriteria);
			
//			List<JobSchedule> results = (List<JobSchedule>)this.currentSession().createCriteria(JobSchedule.class)
//				.add(Restrictions.eq("id.jobId", jobId))
//				.addOrder(Order.desc("id.jobId")).list();
			return results;
		} 
		catch (HibernateException e) {
			throw new DaoException(ExceptionMessageFormatter.format(e));
		}		
	}

	/* (non-Javadoc)
	 * @see com.nec.asia.nic.framework.job.scheduler.dataAccess.JobScheduleDao#getJobScheduleByPrimaryKey(java.lang.String, java.lang.String)
	 */
	public JobSchedule getJobScheduleByPrimaryKey(String jobId, String scheduleName) throws DaoException {

		try {
			DetachedCriteria dCriteria = DetachedCriteria.forClass(JobSchedule.class);
			dCriteria.add(Restrictions.eq("id.jobId", jobId));
			dCriteria.add(Restrictions.eq("id.scheduleName", scheduleName));
			List<JobSchedule> results = (List<JobSchedule>) this.getHibernateTemplate().findByCriteria(dCriteria);
			return (CollectionUtils.isEmpty(results) ? null : results.get(0));
//		      Object jobSchedule = this.currentSession().createCriteria(JobSchedule.class)
//              .add(Restrictions.eq("id.jobId", jobId))
//              .add(Restrictions.eq("id.scheduleName", scheduleName))
//              .uniqueResult();
//		      return (JobSchedule) jobSchedule;
		} 
		catch (HibernateException e) {
			throw new DaoException(ExceptionMessageFormatter.format(e));
		}		
	}
	
	/* (non-Javadoc)
	 * @see com.nec.asia.nic.framework.job.scheduler.dataAccess.JobScheduleDao#getActiveJobs()
	 */
	@SuppressWarnings("unchecked")
	public List<Job> getActiveJobs() throws DaoException {
		try {
			DetachedCriteria dCriteria = DetachedCriteria.forClass(Job.class);
			dCriteria.add(Restrictions.eq("active", true));
			dCriteria.addOrder(Order.desc("jobId"));
			List<Job> results = (List<Job>) this.getHibernateTemplate().findByCriteria(dCriteria);
			return results;
			
//			List<Job> results = (List<Job>)this.currentSession().createCriteria(Job.class)
//				.add(Restrictions.eq("active", true))
//				.addOrder(Order.desc("jobId")).list();		
//			return results;
		} 
		catch (HibernateException e) {
			throw new DaoException(ExceptionMessageFormatter.format(e));
		}		
	}

	/* (non-Javadoc)
	 * @see com.nec.asia.nic.framework.job.scheduler.dataAccess.JobScheduleDao#getAllJobs()
	 */
	@SuppressWarnings("unchecked")
	public List<Job> getAllJobs() throws DaoException {
		try {
			DetachedCriteria dCriteria = DetachedCriteria.forClass(Job.class);
			dCriteria.addOrder(Order.desc("jobId"));
			List<Job> results = (List<Job>) this.getHibernateTemplate().findByCriteria(dCriteria);
			return results;
			
//			List<Job> results = (List<Job>)this.currentSession().createCriteria(Job.class)
//				.addOrder(Order.desc("jobId")).list();		
//			return results;
		} 
		catch (HibernateException e) {
			throw new DaoException(ExceptionMessageFormatter.format(e));
		}		
	}

	/* (non-Javadoc)
	 * @see com.nec.asia.nic.framework.job.scheduler.dataAccess.JobScheduleDao#getExecutionDetails(java.lang.Long)
	 */
	@SuppressWarnings("unchecked")
	public List<JobExecutionDetails> getExecutionDetails(Long logId) throws DaoException {
		try {
			DetachedCriteria dCriteria = DetachedCriteria.forClass(JobExecutionDetails.class);
			dCriteria.add(Restrictions.eq("logId", logId));
			dCriteria.addOrder(Order.asc("detailId"));
			List<JobExecutionDetails> results = (List<JobExecutionDetails>) this.getHibernateTemplate().findByCriteria(dCriteria);
			return results;
			
//			List<JobExecutionDetails> results = (List<JobExecutionDetails>)this.currentSession().createCriteria(JobExecutionDetails.class)
//				.add(Restrictions.eq("logId", logId))
//				.addOrder(Order.asc("detailId")).list();		
//			return results;
		} 
		catch (HibernateException e) {
			throw new DaoException(ExceptionMessageFormatter.format(e));
		}		
	}
	
	/* (non-Javadoc)
	 * @see com.nec.asia.nic.framework.job.scheduler.dataAccess.JobScheduleDao#getExecutionDetails(java.lang.Long, java.lang.String)
	 */
	@SuppressWarnings("unchecked")
	public List<JobExecutionDetails> getExecutionDetails(Long logId, String detailType) throws DaoException {
		try {
			DetachedCriteria dCriteria = DetachedCriteria.forClass(JobExecutionDetails.class);
			dCriteria.add(Restrictions.eq("logId", logId));
			dCriteria.add(Restrictions.eq("detailType", detailType));
			dCriteria.addOrder(Order.asc("detailId"));
			List<JobExecutionDetails> results = (List<JobExecutionDetails>) this.getHibernateTemplate().findByCriteria(dCriteria);
			return results;
			
//			List<JobExecutionDetails> results = (List<JobExecutionDetails>)this.currentSession().createCriteria(JobExecutionDetails.class)
//				.add(Restrictions.eq("logId", logId))
//				.add(Restrictions.eq("detailType", detailType))
//				.addOrder(Order.asc("detailId")).list();		
//			return results;
		} 
		catch (HibernateException e) {
			throw new DaoException(ExceptionMessageFormatter.format(e));
		}		
	}
	
	/* (non-Javadoc)
	 * @see com.nec.asia.nic.framework.job.scheduler.dataAccess.JobScheduleDao#createJob(com.nec.asia.nic.framework.job.scheduler.dataAccess.Job)
	 */
	public void createJob(Job job) throws DaoException {
		try {			
			this.getHibernateTemplate().save(job);
		}
		catch (HibernateException e) {
			throw new DaoException(ExceptionMessageFormatter.format(e));
		}		
	}
	
	/* (non-Javadoc)
	 * @see com.nec.asia.nic.framework.job.scheduler.dataAccess.JobScheduleDao#updateJob(com.nec.asia.nic.framework.job.scheduler.dataAccess.Job)
	 */
	public void updateJob(Job job) throws DaoException {
		try {
		    this.getHibernateTemplate().update(job);
		}
		catch (HibernateException e) {
			throw new DaoException(ExceptionMessageFormatter.format(e));
		}				
	}
	
	/* (non-Javadoc)
	 * @see com.nec.asia.nic.framework.job.scheduler.dataAccess.JobScheduleDao#deleteJob(com.nec.asia.nic.framework.job.scheduler.dataAccess.Job)
	 */
	public void deleteJob(Job job) throws DaoException {
		try {
			this.getHibernateTemplate().delete(job);
		}
		catch (HibernateException e) {
			throw new DaoException(ExceptionMessageFormatter.format(e));
		}						
	}

	/* (non-Javadoc)
	 * @see com.nec.asia.nic.framework.job.scheduler.dataAccess.JobScheduleDao#createJobSchedule(com.nec.asia.nic.framework.job.scheduler.dataAccess.JobSchedule)
	 */
	public void createJobSchedule(JobSchedule schedule) throws DaoException {
		try {
		    this.getHibernateTemplate().save(schedule);
		}
		catch (HibernateException e) {
			throw new DaoException(ExceptionMessageFormatter.format(e));
		}		
	}
	
	/* (non-Javadoc)
	 * @see com.nec.asia.nic.framework.job.scheduler.dataAccess.JobScheduleDao#updateJobSchedule(com.nec.asia.nic.framework.job.scheduler.dataAccess.JobSchedule)
	 */
	public void updateJobSchedule(JobSchedule schedule) throws DaoException {
		try {
		    this.getHibernateTemplate().update(schedule);
		}
		catch (HibernateException e) {
			throw new DaoException(ExceptionMessageFormatter.format(e));
		}				
	}
	
	/* (non-Javadoc)
	 * @see com.nec.asia.nic.framework.job.scheduler.dataAccess.JobScheduleDao#deleteJobSchedule(com.nec.asia.nic.framework.job.scheduler.dataAccess.JobSchedule)
	 */
	public void deleteJobSchedule(JobSchedule schedule) throws DaoException {
		try {
		    this.getHibernateTemplate().delete(schedule);
		}
		catch (HibernateException e) {
			throw new DaoException(ExceptionMessageFormatter.format(e));
		}						
	}

	/* (non-Javadoc)
	 * @see com.nec.asia.nic.framework.job.scheduler.dataAccess.JobScheduleDao#createJobExecutionHistory(com.nec.asia.nic.framework.job.scheduler.dataAccess.JobExecutionHistory)
	 */
	public void createJobExecutionHistory(JobExecutionHistory history) throws DaoException {
		try {
		    this.getHibernateTemplate().save(history);
		}
		catch (HibernateException e) {
			throw new DaoException(ExceptionMessageFormatter.format(e));
		}		
	}

	/* (non-Javadoc)
	 * @see com.nec.asia.nic.framework.job.scheduler.dataAccess.JobScheduleDao#createJobExecutionDetail(com.nec.asia.nic.framework.job.scheduler.dataAccess.JobExecutionDetail)
	 */
	public void createJobExecutionDetail(JobExecutionDetails detail) throws DaoException {
		try {
		    this.getHibernateTemplate().save(detail);
		}
		catch (HibernateException e) {
			throw new DaoException(ExceptionMessageFormatter.format(e));
		}		
	}

	/* (non-Javadoc)
	 * @see com.nec.asia.nic.framework.job.scheduler.dataAccess.JobScheduleDao#getJobExecutionHistoryLatest()
	 */
	public List getJobExecutionHistoryLatest() throws DaoException {
		try {

			//List results = this.currentSession().getNamedQuery("latest.job.execution.history").list();
			List results = this.getHibernateTemplate().findByNamedQuery("latest.job.execution.history");
			return results;
		} 
		catch (HibernateException e) {
			throw new DaoException(ExceptionMessageFormatter.format(e));
		}		
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.nec.asia.baf.comp.admin.scheduler.dataAccess.JobScheduleDao#getJobExecutionHistoryLatest(java.lang.String)
	 */
	public List getJobExecutionHistoryLatest(String systemId) throws DaoException {
		try {

			//List results = this.currentSession().getNamedQuery("latest.job.execution.history.systemid").setString("systemId", systemId).list();
			List results = this.getHibernateTemplate().findByNamedQueryAndNamedParam("latest.job.execution.history.systemid", "systemId", systemId);
			return results;
		} 
		catch (HibernateException e) {
			throw new DaoException(ExceptionMessageFormatter.format(e));
		}		
	}

	
	/**
	 * Gets the job execution history.
	 *
	 * @param jobId the job id
	 * @return the job execution history
	 * @throws DaoException the dao exception
	 */
	@SuppressWarnings("unchecked")
	public List<JobExecutionHistory> getJobExecutionHistory(String jobId) throws DaoException {
		try {
			DetachedCriteria dCriteria = DetachedCriteria.forClass(JobExecutionHistory.class);
			dCriteria.add(Restrictions.eq("jobId", jobId));
			dCriteria.add(Restrictions.sqlRestriction("rownum <= 100"));
			dCriteria.addOrder(Order.desc("executionDate"));
			List<JobExecutionHistory> results = (List<JobExecutionHistory>) this.getHibernateTemplate().findByCriteria(dCriteria);
			return results;
			
//			List<JobExecutionHistory> results = (List<JobExecutionHistory>)this.currentSession().createCriteria(JobExecutionHistory.class)
//				.add(Restrictions.eq("jobId", jobId))
//				.setMaxResults(100)
//				.addOrder(Order.desc("executionDate")).list();
//
//			return results;
		} 
		catch (HibernateException e) {
			throw new DaoException(ExceptionMessageFormatter.format(e));
		}		
	}

	/* (non-Javadoc)
	 * @see com.nec.asia.nic.framework.job.scheduler.dataAccess.JobScheduleDao#getJobExecutionHistoryByPrimaryKey(java.lang.Long)
	 */
	public JobExecutionHistory getJobExecutionHistoryByPrimaryKey(Long logId) throws DaoException {
		
		try {
			DetachedCriteria dCriteria = DetachedCriteria.forClass(JobExecutionHistory.class);
			dCriteria.add(Restrictions.eq("logId", logId));
			dCriteria.add(Restrictions.sqlRestriction("rownum <= 1"));
			dCriteria.addOrder(Order.desc("executionDate"));
			List<JobExecutionHistory> results = (List<JobExecutionHistory>) this.getHibernateTemplate().findByCriteria(dCriteria);
			return (CollectionUtils.isEmpty(results) ? null : results.get(0));
			
//			JobExecutionHistory hist = (JobExecutionHistory) this.currentSession().get(JobExecutionHistory.class, logId);
//			
//			return hist;
		} 
		catch (HibernateException e) {
			throw new DaoException(ExceptionMessageFormatter.format(e));
		}		
	}
	
	
	/* (non-Javadoc)
	 * @see com.nec.asia.nic.framework.job.scheduler.dataAccess.JobScheduleDao#deleteJobExecutionHistory(com.nec.asia.nic.framework.job.scheduler.dataAccess.JobExecutionHistory)
	 */
	public void deleteJobExecutionHistory(JobExecutionHistory jobExecutionHistory) throws DaoException {
		try {
		    this.getHibernateTemplate().delete(jobExecutionHistory);
		} 
		catch (HibernateException e) {
			throw new DaoException(ExceptionMessageFormatter.format(e));
		}		
	}
	
	/* (non-Javadoc)
	 * @see com.nec.asia.nic.framework.job.scheduler.dataAccess.JobScheduleDao#deleteJobExecutionHistory(java.lang.String)
	 */
	public void deleteJobExecutionHistory(String jobId) throws DaoException {
//		int count = this.currentSession().createQuery("delete from JobExecutionHistory where jobId = (:jobId)")
//			.setParameter("jobId", jobId)
//			.executeUpdate();
		
		try {
		    this.getHibernateTemplate().bulkUpdate("delete from JobExecutionHistory where jobId = ? ", jobId);
		} 
		catch (HibernateException e) {
			throw new DaoException(ExceptionMessageFormatter.format(e));
		}	
	}
	
	
//	/* (non-Javadoc)
//	 * @see com.nec.asia.nic.framework.job.scheduler.dataAccess.JobScheduleDao#deleteJobExecutionHistoryForHousekeeping(java.lang.Integer)
//	 */
//	public void deleteJobExecutionHistoryForHousekeeping(Integer retentionDays) throws DaoException {
//	    String jobExecutionDetailSql = "DELETE FROM NSYS_JOB_EXECUTION_DETAILS WHERE LOG_ID IN (SELECT LOG_ID FROM NSYS_JOB_EXECUTION_HISTORY WHERE COMPLETE_DATE < ?)";
//	    String jobExecutionHistorySql = "DELETE FROM NSYS_JOB_EXECUTION_HISTORY WHERE COMPLETE_DATE < ?";
//        try {
//            Calendar calendar = Calendar.getInstance();
//            calendar.setTime(new Date());
//            calendar.set(Calendar.DAY_OF_YEAR, calendar.get(Calendar.DAY_OF_YEAR) - retentionDays);
//            calendar.set(Calendar.HOUR_OF_DAY,0);
//            calendar.set(Calendar.MINUTE,0);
//            calendar.set(Calendar.SECOND,0);
//            
//            _logger.info("In deleteJobExecutionHistoryForHousekeeping: retentionDate: "+calendar.getTime());
//            Session session = this.currentSession();
//            
//            int count = session.createSQLQuery(jobExecutionDetailSql)
//                            .setParameter(0, calendar.getTime())
//                            .executeUpdate();
//            _logger.info("In deleteJobExecutionHistoryForHousekeeping: execution detail deleted count: "+count);
//            
//            count = session.createSQLQuery(jobExecutionHistorySql)
//                            .setParameter(0, calendar.getTime())
//                            .executeUpdate();
//
//            _logger.info("In deleteJobExecutionHistoryForHousekeeping: execution history deleted count: "+count);
//            
//        } 
//        catch (HibernateException e) {
//            throw new DaoException(ExceptionMessageFormatter.format(e));
//        }       
//	}

	/*
	 * (non-Javadoc)
	 * @see com.nec.asia.baf.comp.admin.scheduler.dataAccess.JobScheduleDao#getAllJobsBySystemId(java.lang.String)
	 */
	public List<Job> getAllJobsBySystemId(String systemId) throws DaoException {
		try {
			DetachedCriteria dCriteria = DetachedCriteria.forClass(Job.class);
			dCriteria.add(Restrictions.eq("systemId", systemId));
			dCriteria.addOrder(Order.desc("jobId"));
			List<Job> results = (List<Job>) this.getHibernateTemplate().findByCriteria(dCriteria);
			return results;
			
//			List<Job> results = (List<Job>)this.currentSession().createCriteria(Job.class)
//				.add(Restrictions.eq("systemId", systemId))
//				.addOrder(Order.desc("jobId")).list();
//			return results;
		} 
		catch (HibernateException e) {
			throw new DaoException(ExceptionMessageFormatter.format(e));
		}
	}

	/**
	 * Gets the job execution history.
	 *
	 * @param jobId the job id
	 * @return the job execution history
	 * @throws DaoException the dao exception
	 */
	public PaginatedResult<JobExecutionHistory> findJobExecHistoryByJobId(String jobId, PageRequest pageRequest)
			throws DaoException {
		try {

			DetachedCriteria detachedCriteria = DetachedCriteria.forClass(JobExecutionHistory.class);
			detachedCriteria.add(Restrictions.eq("jobId", jobId));
			Order order = null;
			if (pageRequest != null && pageRequest.getSortorder().equals("desc")) {
				order = Order.desc(pageRequest.getSortname());
			} else {
				order = Order.asc(pageRequest.getSortname());
			}
			detachedCriteria.addOrder(order);

			//Session session = this.currentSession();
			Session session = this.getSession();
			Criteria c = detachedCriteria.getExecutableCriteria(session);

			int total = ((Long) (c.setProjection(Projections.rowCount()).uniqueResult())).intValue();
			int maxNoPage = 1;
			if (total > pageRequest.getMaxRecords()) {
				maxNoPage = (int) Math.ceil(((double) total / pageRequest.getMaxRecords()));
			}
			int firstResults = pageRequest.getFirstRowIndex();
			int maxResults = pageRequest.getMaxRecords();
			if (pageRequest.getPageNo() == maxNoPage) {
				maxResults = total % pageRequest.getMaxRecords();
				if (maxResults == 0) {
					maxResults = pageRequest.getMaxRecords();
				}
			}
			c.setProjection(null);
			c.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
			c.setFirstResult(firstResults);
			c.setMaxResults(maxResults);
			List<JobExecutionHistory> res = c.list();
			if (res != null)
				for (JobExecutionHistory entity : res) {
					this.getHibernateTemplate().evict(entity);
				}
			_logger.debug("total: {}, pageNo: {}, lastPage: {}, firstResults: {}, maxResults: {}, result size: {}",
					new Object[] { total, pageRequest.getPageNo(), maxNoPage, firstResults, maxResults, res.size() });
			return new PaginatedResult<JobExecutionHistory>(total, pageRequest.getPageNo(), res);

		} catch (HibernateException e) {
			throw new DaoException(ExceptionMessageFormatter.format(e));
		}
	}

	@Override
	public PaginatedResult<Job> findAllJobs(PageRequest pageRequest) throws DaoException {
		Order order = null;
		try {
			if(pageRequest != null && pageRequest.getSortorder().equals("desc")) {
				order = Order.desc(pageRequest.getSortname());
			} else {
				order = Order.asc(pageRequest.getSortname());
			}
			DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Job.class);
			//Session session = this.currentSession();
			Session session = this.getSession();
			Criteria c = detachedCriteria.getExecutableCriteria(session);
			
			int total = ((Long) (c.setProjection(Projections.rowCount()).uniqueResult())).intValue();
			int maxNoPage = 1;
			if (total > pageRequest.getMaxRecords()) {
				maxNoPage = (int) Math.ceil(((double) total / pageRequest.getMaxRecords()));
			}
			int firstResults = pageRequest.getFirstRowIndex();
			int maxResults = pageRequest.getMaxRecords();
			if (pageRequest.getPageNo() == maxNoPage) {
				maxResults = total % pageRequest.getMaxRecords();
				if (maxResults == 0) {
					maxResults = pageRequest.getMaxRecords();
				}
			}
			c.setProjection(null);
			c.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
			c.setFirstResult(firstResults);
			c.setMaxResults(maxResults);
			List<Job> res = c.list();
			if (res != null)
				for (Job entity : res) {
					this.getHibernateTemplate().evict(entity);
				}
			logger.debug("total: {}, pageNo: {}, lastPage: {}, firstResults: {}, maxResults: {}, result size: {}");
			return new PaginatedResult<Job>(total, pageRequest.getPageNo(), res);
			
		} catch (HibernateException e) {
			throw new DaoException(ExceptionMessageFormatter.format(e));
		}
	}

	@Override
	public PaginatedResult<JobSchedule> findJobScheByJobId(String jobId, PageRequest pageRequest) throws DaoException {
		
		try {
			DetachedCriteria detachedCriteria = DetachedCriteria.forClass(JobSchedule.class);
			detachedCriteria.add(Restrictions.eq("id.jobId", jobId));
			Order order = null;
			if (pageRequest != null && pageRequest.getSortorder().equals("desc")) {
				order = Order.desc(pageRequest.getSortname());
			} else {
				order = Order.asc(pageRequest.getSortname());
			}
			detachedCriteria.addOrder(order);

			//Session session = this.currentSession();
			Session session = this.getSession();
			Criteria c = detachedCriteria.getExecutableCriteria(session);

			int total = ((Long) (c.setProjection(Projections.rowCount()).uniqueResult())).intValue();
			int maxNoPage = 1;
			if (total > pageRequest.getMaxRecords()) {
				maxNoPage = (int) Math.ceil(((double) total / pageRequest.getMaxRecords()));
			}
			int firstResults = pageRequest.getFirstRowIndex();
			int maxResults = pageRequest.getMaxRecords();
			if (pageRequest.getPageNo() == maxNoPage) {
				maxResults = total % pageRequest.getMaxRecords();
				if (maxResults == 0) {
					maxResults = pageRequest.getMaxRecords();
				}
			}
			c.setProjection(null);
			c.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
			c.setFirstResult(firstResults);
			c.setMaxResults(maxResults);
			List<JobSchedule> res = c.list();
			if (res != null)
				for (JobSchedule entity : res) {
					this.getHibernateTemplate().evict(entity);
				}
			_logger.debug("total: {}, pageNo: {}, lastPage: {}, firstResults: {}, maxResults: {}, result size: {}",
					new Object[] { total, pageRequest.getPageNo(), maxNoPage, firstResults, maxResults, res.size() });
			return new PaginatedResult<JobSchedule>(total, pageRequest.getPageNo(), res);

		} catch (HibernateException e) {
			throw new DaoException(ExceptionMessageFormatter.format(e));
		}
	}

	@Override
	public PaginatedResult<JobExecutionDetails> findJobExecDetailsByLogId(Long logId, String filterName, PageRequest pageRequest)
			throws DaoException {
		
		try {
			DetachedCriteria detachedCriteria = DetachedCriteria.forClass(JobExecutionDetails.class);
			detachedCriteria.add(Restrictions.eq("logId", logId));
			if(StringUtils.isNotEmpty(filterName)) {
				detachedCriteria.add(Restrictions.eq("detailType", filterName));
			}

			Order order = null;
			if (pageRequest != null && pageRequest.getSortorder().equals("desc")) {
				order = Order.desc(pageRequest.getSortname());
			} else {
				order = Order.asc(pageRequest.getSortname());
			}
			detachedCriteria.addOrder(order);

			//Session session = this.currentSession();
			Session session = this.getSession();
			Criteria c = detachedCriteria.getExecutableCriteria(session);

			int total = ((Long) (c.setProjection(Projections.rowCount()).uniqueResult())).intValue();
			int maxNoPage = 1;
			if (total > pageRequest.getMaxRecords()) {
				maxNoPage = (int) Math.ceil(((double) total / pageRequest.getMaxRecords()));
			}
			int firstResults = pageRequest.getFirstRowIndex();
			int maxResults = pageRequest.getMaxRecords();
			if (pageRequest.getPageNo() == maxNoPage) {
				maxResults = total % pageRequest.getMaxRecords();
				if (maxResults == 0) {
					maxResults = pageRequest.getMaxRecords();
				}
			}
			c.setProjection(null);
			c.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
			c.setFirstResult(firstResults);
			c.setMaxResults(maxResults);
			List<JobExecutionDetails> res = c.list();
			if (res != null) {
				for (JobExecutionDetails entity : res) {
					this.getHibernateTemplate().evict(entity);
				}
			}
			_logger.debug("total: {}, pageNo: {}, lastPage: {}, firstResults: {}, maxResults: {}, result size: {}",
					new Object[] { total, pageRequest.getPageNo(), maxNoPage, firstResults, maxResults, res.size() });
			return new PaginatedResult<JobExecutionDetails>(total, pageRequest.getPageNo(), res);

		} catch (HibernateException e) {
			throw new DaoException(ExceptionMessageFormatter.format(e));
		}
	}
	
	@Override
	public PaginatedResult<JobExecutionHistoryLatest> findAllJobExecLatest(PageRequest pageRequest)
			throws DaoException {
		
		try {
			//Session session = this.currentSession();
			Session session = this.getSession();
			Query qr =  session.getNamedQuery("latest.job.execution.history");
			
			int total = qr.list().size();
			int maxNoPage = 1;
			if (total > pageRequest.getMaxRecords()) {
				maxNoPage = (int) Math.ceil(((double) total / pageRequest.getMaxRecords()));
			}
			int firstResults = pageRequest.getFirstRowIndex();
			int maxResults = pageRequest.getMaxRecords();
			if (pageRequest.getPageNo() == maxNoPage) {
				maxResults = total % pageRequest.getMaxRecords();
				if (maxResults == 0) {
					maxResults = pageRequest.getMaxRecords();
				}
			}
		
			qr.setFirstResult(firstResults);
			qr.setMaxResults(maxResults);
			List<Object[]> jobDetailSchLatestList = qr.list();
			List<JobExecutionHistoryLatest> jobExecutionHistoryLatest = new ArrayList<JobExecutionHistoryLatest>();
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
			
			
			_logger.debug("total: {}, pageNo: {}, lastPage: {}, firstResults: {}, maxResults: {}, result size: {}",
					new Object[] { total, pageRequest.getPageNo(), maxNoPage, firstResults, maxResults, jobExecutionHistoryLatest.size() });
			return new PaginatedResult<JobExecutionHistoryLatest>(total, pageRequest.getPageNo(), jobExecutionHistoryLatest);

		} catch (HibernateException e) {
			throw new DaoException(ExceptionMessageFormatter.format(e));
		}
	}
	
}