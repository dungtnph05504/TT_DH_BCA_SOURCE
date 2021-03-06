package com.nec.asia.nic.comp.trans.dao.impl;

import java.net.InetAddress;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.ObjectUtils;
import org.apache.commons.lang.StringUtils;
import org.hibernate.Hibernate;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Conjunction;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Disjunction;
import org.hibernate.criterion.Example;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.sql.JoinType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.lowagie.text.pdf.AsianFontMapper;
import com.nec.asia.nic.comp.job.dto.LogInfoDTO;
import com.nec.asia.nic.comp.job.utils.NicCommandUtil;
import com.nec.asia.nic.comp.trans.dao.NicTransactionLogDao;
import com.nec.asia.nic.comp.trans.dao.NicUploadJobDao;
import com.nec.asia.nic.comp.trans.domain.BaseModelList;
import com.nec.asia.nic.comp.trans.domain.BaseModelSingle;
import com.nec.asia.nic.comp.trans.domain.NicJobCpdCheckResult;
import com.nec.asia.nic.comp.trans.domain.NicSearchHitResult;
import com.nec.asia.nic.comp.trans.domain.NicSearchResult;
import com.nec.asia.nic.comp.trans.domain.NicTransaction;
import com.nec.asia.nic.comp.trans.domain.NicTransactionLog;
import com.nec.asia.nic.comp.trans.domain.NicUploadJob;
import com.nec.asia.nic.comp.trans.dto.AssignmentFilter;
import com.nec.asia.nic.comp.trans.dto.AssignmentFilterAll;
import com.nec.asia.nic.comp.trans.dto.CountPassport;
import com.nec.asia.nic.comp.trans.dto.NicUploadJobDto;
import com.nec.asia.nic.comp.trans.service.NicTransactionService;
import com.nec.asia.nic.comp.trans.service.exception.NicUploadJobServiceException;
import com.nec.asia.nic.comp.trans.utils.RegistrationConstants;
import com.nec.asia.nic.comp.ws.log.domain.CreateMessageException;
import com.nec.asia.nic.framework.PaginatedResult;
import com.nec.asia.nic.framework.admin.code.dao.ParametersDao;
import com.nec.asia.nic.framework.admin.code.domain.Codes;
import com.nec.asia.nic.framework.admin.code.domain.Parameters;
import com.nec.asia.nic.framework.admin.code.domain.ParametersId;
import com.nec.asia.nic.framework.admin.code.service.CodesService;
import com.nec.asia.nic.framework.dao.impl.GenericHibernateDao;
import com.nec.asia.nic.framework.exception.JaxbXmlConvertorException;
import com.nec.asia.nic.util.TransLogInfoXmlConvertor;
import com.nec.asia.nic.utils.DateUtil;

/*
 * Modification History:
 * 
 * 27 Sept 2013 (Peddi Swapna): Added getPendingInvestigationsCount method to get the pending investigations count.
 * 01 Oct 2013 (Sailaja): Added findRejectedJobsForSupervisor method to get the rejected jobs list.
 * 04 Oct 2013 (Sailaja) : Added assignNewRejectesdJob method to get the new rejected jobs list.
 * 11-OCT-2013 (jp) - temp avoid CAR_ISS
 * 17 Oct 2013 (Sailaja) : Modified getPendingInvestigationsCount method to filter based on the officerId and Investigation Status.
 * 29 Oct 2013 (chris) : Added create job.
 * 21 Nov 2013 (jp) : changed getTransactionJobsInQueue to fetch Issuance transactions and log4j fix in logging
 * 26 Nov 2013 (Sailaja) : Added cancelTransaction method to update the investigation status
 *  07 Jan 2013 (chris) : Added constants RECORD_STATUS_CANCELLED.
 *  08 Jan 2014 (Sailaja) : Added updatePersoRegisterStatusOnReprint method for reprint
 *  13 Feb 2014 (chris) : to sort the investigation job list
 *  15 Apr 2014 (Peddi Swapna) : Added getUploadJobId method to get the job id.
 *  17 Mar 2014 (jp) : changed getTransactionJobsInQueue to sort by job id to process old cases first
 *  28 Mar 2014 (jp) : add getTxnWithMaxReprocessCount()
 *  06 May 2014 (jp): added getCarTxnBeforeFeb7 to sync card status to cpd for transactions before 07FEB
 *  08 May 2014 (Peddi Swapna) : Allowing the user to see the error transactions based on the search criteria.
 *  03 Jun 2014 (jp) : interval seconds for retry on reprocess changed from 100 to 200
 *  30 Jun 2014 (jp) : changed logic for afis check interval 
 *  29 Feb 2016 (tue) : Added findAllForPagination.
 *  01 Apr 2016 (chris) : add parameter to config interval, rety count.
 *  13 Apr 2016 (Khang) : Add parameter to filter process jobs by investigation status
 */
@Repository("uploadJobDao")
public class NicUploadJobDaoImpl extends
		GenericHibernateDao<NicUploadJob, Long> implements NicUploadJobDao {
	// chris fix start
	private static final String RECORD_STATUS_INITIAL = NicUploadJob.RECORD_STATUS_INITIAL; // "00"
	private static final String RECORD_STATUS_IN_PROGRESS = NicUploadJob.RECORD_STATUS_IN_PROGRESS; // "01";
	private static final String RECORD_STATUS_COMPLETED = NicUploadJob.RECORD_STATUS_COMPLETED; // "02";
	private static final String RECORD_STATUS_APPROVED = NicUploadJob.RECORD_STATUS_APPROVED; // RECORD_STATUS_COMPLETED;
	private static final String RECORD_STATUS_REJECTED = NicUploadJob.RECORD_STATUS_REJECTED; // "04";
	private static final String RECORD_STATUS_CANCELLED = NicUploadJob.RECORD_STATUS_CANCELLED; // "05";
																								// //07
																								// Jan
																								// 2014
	private static final String RECORD_STATUS_SUSPENDED = NicUploadJob.RECORD_STATUS_SUSPENDED; // "06";
																								// //JESUS:
																								// 2016
																								// 01
																								// 13
	private static final String SYSTEM_EPP = "SYSTEM_EPP";
	// chris fix end

	public static final String PARA_SCOPE_WORKFLOW = "WORKFLOW"; // 13 Apr 2016
																	// (khang)
	public static final String PARA_NAME_VALID_JOB_INV_STATUS = "VALID_JOB_INV_STATUS"; // 13
																						// Apr
																						// 2016
																						// (khang)

	@Autowired
	NicCommandUtil nicCommandUtil = null;

	@Autowired
	private NicTransactionLogDao transactionLogDao = null;

	@Autowired
	private CodesService codesService;

	@Autowired
	private NicTransactionService nicTransactionService;

	@Autowired
	private ParametersDao parametersDao;

	public static TransLogInfoXmlConvertor logUtil = new TransLogInfoXmlConvertor();

	@Override
	public PaginatedResult<NicUploadJob> findByTransactionId(String txnId,
			String owner) throws Exception {
		logger.debug("Inside Dao Impl============>>>>>>>Find the Job List by transaction Id "
				+ txnId);

		DetachedCriteria criteria = DetachedCriteria
				.forClass(NicUploadJob.class);

		criteria.add(Restrictions.eq("investigationOfficerId", owner));
		if (StringUtils.isNotBlank(txnId)) {
			criteria.add(Restrictions.eq("transactionId", txnId));
		}
		Criterion inInvestigationStatus = Restrictions.in(
				"investigationStatus", new String[] { RECORD_STATUS_INITIAL,
						RECORD_STATUS_IN_PROGRESS });

		criteria.add(inInvestigationStatus);

		PaginatedResult<NicUploadJob> result = findAllForPagination(criteria,
				1, 10);

		// NicUploadJob nicUploadJob = new NicUploadJob();
		// nicUploadJob.setTransactionId(txnId);
		// nicUploadJob.setInvestigationOfficerId(owner);
		//
		// nicUploadJob.setInvestigationStatus(RECORD_STATUS_IN_PROGRESS);
		//
		// PaginatedResult<NicUploadJob> result = findAllForPagination(
		// nicUploadJob, 1, 10);
		return result; // (List<NicUploadJob>) list;
	}

	// TRUNG THÊM SEARCH KẾT QUẢ BẢN GHI DANH SÁCH CHỜ PHÊ DUYỆT
	@Override
	public PaginatedResult<NicUploadJob> findByTransactionIdApprove(
			String txnId, String owner) throws Exception {
		logger.debug("Inside Dao Impl============>>>>>>>Find the Job List by transaction Id "
				+ txnId);

		DetachedCriteria criteria = DetachedCriteria
				.forClass(NicUploadJob.class);

		DateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
		Date data = formatter.parse("19-04-2018");

		criteria.add(Restrictions.eq("investigationOfficerId", owner));
		if (StringUtils.isNotBlank(txnId)) {
			criteria.add(Restrictions.eq("transactionId", txnId));
		}

		Criterion jobApproveStatus = Restrictions
				.and(Restrictions
						.and(Restrictions.isNull("jobApproveStatus"),
								Restrictions.or(
										Restrictions
												.eq("investigationStatus",
														NicUploadJob.RECORD_STATUS_COMPLETED),
										Restrictions.and(
												Restrictions
														.eq("cpdCheckStatus",
																NicUploadJob.RECORD_STATUS_COMPLETED),
												Restrictions
														.eq("afisRegisterStatus",
																NicUploadJob.RECORD_STATUS_COMPLETED),
												Restrictions
														.eq("afisCheckStatus",
																NicUploadJob.RECORD_STATUS_COMPLETED)))),
						Restrictions.and(Restrictions.isNull("tempPassportNo")));

		criteria.add(jobApproveStatus);

		PaginatedResult<NicUploadJob> result = findAllForPagination(criteria,
				1, 10);

		return result;
	}

	// END

	// TRUNG THÊM SEARCH KẾT QUẢ BẢN GHI DANH SÁCH DUYỆT IN
	@Override
	public PaginatedResult<NicUploadJob> findByTransactionIdPerso(String txnId,
			String owner) throws Exception {
		logger.debug("Inside Dao Impl============>>>>>>>Find the Job List by transaction Id "
				+ txnId);

		DetachedCriteria criteria = DetachedCriteria
				.forClass(NicUploadJob.class);

		DateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
		Date data = formatter.parse("19-04-2018");

		criteria.add(Restrictions.eq("investigationOfficerId", owner));
		if (StringUtils.isNotBlank(txnId)) {
			criteria.add(Restrictions.eq("transactionId", txnId));
		}

		Criterion jobApproveStatus = Restrictions.and(
				Restrictions.isNotNull("jobApproveStatus"),
				Restrictions.eq("jobApproveStatus", "1"),
				Restrictions.isNull("persoRegisterStatus"));

		criteria.add(jobApproveStatus);

		PaginatedResult<NicUploadJob> result = findAllForPagination(criteria,
				1, 10);

		return result;
	}

	// END

	// TRUNG THÊM SEARCH KẾT QUẢ BẢN GHI DANH SÁCH ĐỒNG BỘ A72
	@Override
	public PaginatedResult<NicUploadJob> findByTransactionIdSync(String txnId,
			String owner) throws Exception {
		logger.debug("Inside Dao Impl============>>>>>>>Find the Job List by transaction Id "
				+ txnId);

		DetachedCriteria criteria = DetachedCriteria
				.forClass(NicUploadJob.class);

		DateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
		Date data = formatter.parse("19-04-2018");

		criteria.add(Restrictions.eq("investigationOfficerId", owner));
		if (StringUtils.isNotBlank(txnId)) {
			criteria.add(Restrictions.eq("transactionId", txnId));
		}

		Criterion jobApproveStatus = Restrictions.and(Restrictions
				.isNotNull("jobApproveStatus"), Restrictions.eq(
				"jobApproveStatus", "1"), Restrictions
				.isNotNull("persoRegisterStatus"), Restrictions.eq(
				"persoRegisterStatus", NicUploadJob.RECORD_STATUS_COMPLETED));

		criteria.add(jobApproveStatus);

		PaginatedResult<NicUploadJob> result = findAllForPagination(criteria,
				1, 10);

		return result;
	}

	// END

	@SuppressWarnings("unchecked")
	@Override
	public BaseModelList<NicUploadJob> findAllByTransactionId(String txnId)
			throws Exception {
		try {
			// logger.debug("Inside Dao Impl============>>>>>>>Find the Job List by transaction Id "
			// + txnId);
			List<NicUploadJob> list = null;
			DetachedCriteria criteria = DetachedCriteria
					.forClass(NicUploadJob.class);
			criteria.addOrder(Order.desc("id"));
			if (StringUtils.isNotBlank(txnId)) {
				criteria.add(Restrictions.eq("transactionId", txnId));
				list = (List<NicUploadJob>) getHibernateTemplate()
						.findByCriteria(criteria);
			}
			return new BaseModelList<NicUploadJob>(list, true, null);
		} catch (Throwable e) {
			logger.error(e.getMessage());
			// throw new Exception("error get wfl: " + e.getMessage());
			return new BaseModelList<NicUploadJob>(null, false,
					CreateMessageException
							.createMessageException(new Exception(e))
							+ " - findAllByTransactionId: "
							+ txnId
							+ " - thất bại.");
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<NicUploadJob> findAllByPersonID(long id) throws Exception {
		try {
			logger.debug("Inside Dao Impl============>>>>>>>Find the Job List by person Id: "
					+ id);
			DetachedCriteria criteria = DetachedCriteria
					.forClass(NicUploadJob.class);
			criteria.add(Restrictions.eq("originalyPersonId", id));
			criteria.addOrder(Order.desc("workflowJobId"));
			return (List<NicUploadJob>) getHibernateTemplate().findByCriteria(
					criteria);
		} catch (Exception e) {
			logger.error(e.getMessage());
			return null;
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<NicUploadJob> findAllByPersonCode(String code) throws Exception {
		try {
			logger.debug("Inside Dao Impl============>>>>>>>Find the Job List by person Code: "
					+ code);
			DetachedCriteria criteria = DetachedCriteria
					.forClass(NicUploadJob.class);
			criteria.add(Restrictions.eq("originalyPersonId", code));
			criteria.addOrder(Order.desc("workflowJobId"));
			return (List<NicUploadJob>) getHibernateTemplate().findByCriteria(
					criteria);
		} catch (Exception e) {
			logger.error(e.getMessage());
			return null;
		}
	}

	@Override
	public NicUploadJob assignNewJob(String officerId) throws Exception {
		logger.debug("Inside Dao Impl==========>>>>>>Update Officer Id whose values are null"
				+ officerId);

		// search here with sort and 1 returned
		Long uploadJobId = null;
		try {
			{
				Session session = null;
				try {
					session = getHibernateTemplate().getSessionFactory()
							.openSession();
					Query query = session
							.getNamedQuery("epp.NicUploadJobDaoImpl.assignNewJobBasedOnStatus");
					query.setString("investigationStatus",
							RECORD_STATUS_INITIAL);

					List<Object> results = query.list();

					if (results == null || results.size() == 0) {
					} else {
						uploadJobId = (Long) results.get(0);
					}
				} finally {
					session.close();
				}
			}

			if (uploadJobId == null) {
				throw new Exception("No Job Found.");
			}

			NicUploadJob updateobject = this.findById(uploadJobId);

			updateobject.setInvestigationOfficerId(officerId);
			updateobject.setInvestigationStatus(RECORD_STATUS_IN_PROGRESS);
			updateobject
					.setJobCurrentStage(NicTransactionLog.TRANSACTION_STAGE_INVESTIGATION);
			updateobject.setUpdateBy(officerId);
			updateobject.setUpdateDatetime(new Date());
			updateobject.setUpdateWkstnId("SYSTEM");

			getHibernateTemplate().update(updateobject);

			return updateobject;
		} catch (Exception e) {
			logger.error("======== Exception while updating data ", e);
			throw e;
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<NicUploadJob> newInvestigation(long jobId) throws Exception {
		logger.debug("Inside Dao Impl==========>>>>>>>>New Investigation"
				+ jobId);
		DetachedCriteria criteria = DetachedCriteria
				.forClass(NicUploadJob.class);
		criteria.add(Restrictions.eq("workflowJobId", jobId));
		return (List<NicUploadJob>) this.getHibernateTemplate().findByCriteria(
				criteria);
	}

	@Override
	public void approveJobId(long approveJobId, String userId, String wkstnId)
			throws Exception {
		try {
			DetachedCriteria criteria = DetachedCriteria
					.forClass(NicUploadJob.class);
			criteria.add(Restrictions.eq("workflowJobId", approveJobId));
			List<NicUploadJob> list = (List<NicUploadJob>) getHibernateTemplate()
					.findByCriteria(criteria);
			if (CollectionUtils.isNotEmpty(list)) {
				NicUploadJob approveUpdateObj = list.get(0);
				approveUpdateObj.setInvestigationStatus(RECORD_STATUS_APPROVED);
				approveUpdateObj.setUpdateBy(userId);
				approveUpdateObj.setUpdateWkstnId(wkstnId);
				approveUpdateObj.setUpdateDatetime(new Date());
				getHibernateTemplate().update(approveUpdateObj);
			}
		} catch (Exception e) {
			logger.error("======== Exception while updating data ", e);
			// e.printStackTrace();
			throw e;
		}
	}

	@Override
	public void rejectJobId(long rejectJobId, String userId, String wkstnId)
			throws Exception {
		try {
			DetachedCriteria criteria = DetachedCriteria
					.forClass(NicUploadJob.class);
			criteria.add(Restrictions.eq("workflowJobId", rejectJobId));
			List<NicUploadJob> list = (List<NicUploadJob>) getHibernateTemplate()
					.findByCriteria(criteria);
			if (CollectionUtils.isNotEmpty(list)) {
				NicUploadJob rejectUpdateObj = list.get(0);
				rejectUpdateObj.setInvestigationStatus(RECORD_STATUS_REJECTED);
				rejectUpdateObj.setUpdateBy(userId);
				rejectUpdateObj.setUpdateWkstnId(wkstnId);
				rejectUpdateObj.setUpdateDatetime(new Date());
				getHibernateTemplate().update(rejectUpdateObj);
			}
		} catch (Exception e) {
			logger.error("======== Exception while updating data ", e);
			// e.printStackTrace();
			throw e;
		}
		// return rejectJobID;
	}

	@Override
	public PaginatedResult<NicUploadJob> getJobList(String jobType, Long jobId,
			String jobState, String[] recordStatus, String txnId, String nin,
			String owner, int pageNo, int pageSize) {
		logger.debug("Inside Dao Impl==========>>>>>>>NIC Enquiry");
		/*
		 * Query query = this.getSession().createQuery(
		 * "select transactionId from NicTransaction a where a.nin=(:nin)");
		 * query.setString("nin", nin);
		 */
		List<String> transIds = null;
		if (StringUtils.isNotBlank(nin)) {
			DetachedCriteria criteria = DetachedCriteria
					.forClass(NicTransaction.class);
			criteria.add(Restrictions.eq("nin", nin));
			criteria.setProjection(Projections.property("transactionId"));
			transIds = (List<String>) getHibernateTemplate().findByCriteria(
					criteria);
		}

		DetachedCriteria detachedCriteria = DetachedCriteria
				.forClass(NicUploadJob.class);

		if (StringUtils.isNotEmpty(jobType)) {
			detachedCriteria.add(Restrictions.eq("jobType", jobType));
		}

		if (StringUtils.isNotEmpty(owner)) {
			detachedCriteria.add(Restrictions.eq("investigationOfficerId",
					owner));
		}

		if (jobId != null) {
			detachedCriteria.add(Restrictions.eq("workflowJobId", jobId));
		}

		if (StringUtils.isNotBlank(jobState)) {
			detachedCriteria.add(Restrictions.eq("jobCurrentStage", jobState));
		}

		if (recordStatus != null && recordStatus.length > 0) {
			detachedCriteria.add(Restrictions.in("investigationStatus",
					recordStatus));
		}
		if (StringUtils.isNotBlank(txnId)) {
			// detachedCriteria.add(Restrictions.eq("transactionId", txnId));
			detachedCriteria.add(Restrictions.like("transactionId", txnId,
					MatchMode.ANYWHERE).ignoreCase());
		}

		if (CollectionUtils.isNotEmpty(transIds)) {
			// detachedCriteria.add(Restrictions.in("transactionId", transIds));
			Conjunction conjunction = Restrictions.conjunction();
			if (transIds.size() > 0) {
				Disjunction disjunctionIdListCount = Restrictions.disjunction();

				for (int i = 0; i < (((transIds.size() / 1000) + 1)); i++) {
					Criterion list = null;
					if (transIds.size() > ((i + 1) * 1000)) {
						list = Restrictions.in("transactionId", Arrays
								.copyOfRange(transIds.toArray(), (i * 1000),
										((i + 1) * 1000)));
					} else {
						list = Restrictions.in("transactionId", Arrays
								.copyOfRange(transIds.toArray(), (i * 1000),
										transIds.size()));
					}

					disjunctionIdListCount.add(list);
				}

				conjunction.add(disjunctionIdListCount);
			}
			detachedCriteria.add(conjunction);
		}
		// int total = ((Long)
		// detachedCriteria.setProjection(Projections.rowCount()).uniqueResult()).intValue();
		// int maxNoPage = 1;
		// if (total>pageSize) {
		// maxNoPage = (int) Math.ceil(((double)total / pageSize));
		// }
		// int firstResults = (pageNo - 1) * pageSize;
		// int maxResults = pageSize;
		// if (pageNo == maxNoPage) {
		// maxResults = total % pageSize;
		// }
		// c.setProjection(null);
		// c.setResultTransformer(Criteria.ROOT_ENTITY);
		// c.setFirstResult(firstResults);
		// c.setMaxResults(maxResults);
		PaginatedResult<NicUploadJob> list = this.findAllForPagination(
				detachedCriteria, pageNo, pageSize);
		return list;
		// return this.findAllJobsForPagination(detachedCriteria, pageNo,
		// pageSize, jobType, jobId, jobState, recordStatus, txnId, tranIds);
	}

	/*
	 * protected PaginatedResult<NicUploadJob> findAllJobsForPagination(final
	 * DetachedCriteria detachedCriteria, final int pageNo, final int
	 * pageSize,final String jobType, final Long jobId,final String
	 * jobState,final String recordStatus, final String txnId, final
	 * List<String> tranIds) {
	 * 
	 * return (PaginatedResult<NicUploadJob>) getHibernateTemplate().execute(
	 * new HibernateCallback<PaginatedResult<NicUploadJob>>() {
	 * 
	 * public PaginatedResult<NicUploadJob> doInHibernate(Session session)
	 * throws HibernateException, SQLException { Criteria c =
	 * detachedCriteria.getExecutableCriteria(session);
	 * 
	 * if(StringUtils.isNotEmpty(jobType)){ c.add(Restrictions.eq("jobType",
	 * jobType)); } if(jobId != null){ c.add(Restrictions.eq("workflowJobId",
	 * jobId)); } if(StringUtils.isNotEmpty(jobState)){
	 * c.add(Restrictions.eq("jobCurrentState", jobState)); }
	 * if(StringUtils.isNotEmpty(recordStatus)){
	 * c.add(Restrictions.eq("investigationStatus", recordStatus)); }
	 * if(StringUtils.isNotEmpty(txnId)){ c.add(Restrictions.eq("transactionId",
	 * txnId)); } if(CollectionUtils.isEmpty(tranIds)){
	 * c.add(Restrictions.in("transactionId", tranIds)); } int total = ((Long)
	 * c.setProjection(Projections.rowCount()).uniqueResult()).intValue(); int
	 * maxNoPage = 1; if (total>pageSize) { maxNoPage = (int)
	 * Math.ceil(((double)total / pageSize)); } int firstResults = (pageNo - 1)
	 * * pageSize; int maxResults = pageSize; if (pageNo == maxNoPage) {
	 * maxResults = total % pageSize; } c.setProjection(null);
	 * c.setResultTransformer(Criteria.ROOT_ENTITY);
	 * c.setFirstResult(firstResults); c.setMaxResults(maxResults);
	 * List<NicUploadJob> list = c.list();
	 * 
	 * System.out.println("============================>>>>>>>>>>>>>>>>>>>>>>>>>"
	 * +list.size());
	 * 
	 * logger.debug(
	 * "total: {}, pageNo: {}, lastPage: {}, firstResults: {}, maxResults: {}, result size: {}"
	 * , new Object[]{total, pageNo, maxNoPage, firstResults, maxResults,
	 * list.size()});
	 * 
	 * return new PaginatedResult<NicUploadJob>(total, pageNo, list); } }); }
	 */

	@Override
	public List<NicUploadJob> getTransactionJobsInQueue(
			Map<String, String> configMap) throws Exception {
		logger.info("inside getTransactionJobsInQueue -- ");
		DetachedCriteria criteria = DetachedCriteria
				.forClass(NicUploadJob.class);

		String jobInvStatus = configMap.get(PARA_NAME_VALID_JOB_INV_STATUS);
		String[] investigationStatus = jobInvStatus.split(",");
		if (investigationStatus == null) {
			investigationStatus = new String[] { "02,40" };
		}
		Criterion nullJobCurrentState = Restrictions.isNull("jobCurrentStage");
		Criterion blankJobCurrentState = Restrictions.eq("jobCurrentStage", "");

		Criterion jobApprove = Restrictions.eq("jobApproveStatus", "");
		Criterion nulljobApprove = Restrictions.isNull("jobApproveStatus");

		Criterion isjobApprove = Restrictions.eq("jobApproveStatus", "1");
		Criterion notnulljobApprove = Restrictions
				.isNotNull("jobApproveStatus");
		// Criterion inInvestigationStatus =
		// Restrictions.in("investigationStatus", new String[]
		// {"02","04","05"});
		Criterion inInvestigationStatus = Restrictions.in(
				"investigationStatus", investigationStatus);
		Criterion nullAfisDeleteStatus = Restrictions
				.isNull("afisUpdateStatus");
		Criterion investigationStatusNull = Restrictions
				.isNull("investigationStatus");
		// Criterion tempPPno = Restrictions.isNull("tempPassportNo");

		int reprocessMax = 3;
		String minReprocessCountVal = configMap.get("MIN_REPROCESS_COUNT");
		if (StringUtils.isNotBlank(minReprocessCountVal)) {
			reprocessMax = Integer.valueOf(minReprocessCountVal);
		}

		Criterion canReprocess = Restrictions.or(
				Restrictions.isNull("jobReprocessCount"),
				Restrictions.lt("jobReprocessCount", reprocessMax));

		Criterion hasError = this.getErrorConditionCriterion();
		Criterion hasNull = this.getErrorConditionCriterionNone();

		criteria.add(
		// jobApprove, //Thêm điều kiện để cho vào danh sách duyệt trước khi
		// PERSO
		Restrictions.or(Restrictions.or(
				Restrictions.or(nullJobCurrentState, blankJobCurrentState),
				investigationStatusNull), // , tempPPno
				Restrictions.and(inInvestigationStatus, nullAfisDeleteStatus,
						isjobApprove, notnulljobApprove), Restrictions.and(
						hasError, canReprocess)));

		criteria.addOrder(Order.desc("jobPriority")); // 2016 Jan 19
		criteria.addOrder(Order.asc("jobReprocessCount"));
		criteria.addOrder(Order.asc("workflowJobId"));

		// add new logic to get top 100 cases first
		int maxResult = 100;
		String maxJobqueueResultVal = configMap.get("MAX_JOBQUEUE_COUNT");
		if (StringUtils.isNotBlank(maxJobqueueResultVal)) {
			maxResult = Integer.valueOf(maxJobqueueResultVal);
		}
		this.getHibernateTemplate().setMaxResults(maxResult);

		List<NicUploadJob> result = (List<NicUploadJob>) this
				.getHibernateTemplate().findByCriteria(criteria);
		// logger.info("hibernate template:"+this.getHibernateTemplate());
		logger.info("result size:" + result.size());
		// ----------------------------------------------------------
		// add new logic to filter reprocess jobs which are not yet ready for
		// reprocess
		if (result != null && result.size() > 0) {
			result = cleanReprocsessTxn(result, configMap);
		} else {
			logger.info("empty results");
		}
		// end
		return result;
	}

	private Criterion getErrorConditionCriterion() {
		String errorCode = "09";
		Criterion cpdCheckError = Restrictions.eq("cpdCheckStatus", errorCode);
		Criterion afisRegisterError = Restrictions.eq("afisRegisterStatus",
				errorCode);
		Criterion afisCheckError = Restrictions
				.eq("afisCheckStatus", errorCode);
		Criterion afisVerifyError = Restrictions.eq("afisVerifyStatus",
				errorCode);
		Criterion persoRegisterError = Restrictions.eq("persoRegisterStatus",
				errorCode);
		Criterion hasError = Restrictions.or(cpdCheckError, Restrictions.or(
				afisRegisterError,
				Restrictions.or(afisCheckError,
						Restrictions.or(afisVerifyError, persoRegisterError))));
		return hasError;
	}

	private Criterion getErrorConditionCriterionNone() {
		String errorCode = "";
		Criterion cpdCheckError = Restrictions.eq("cpdCheckStatus", errorCode);
		Criterion cpdCheckErrorNull = Restrictions.isNull("cpdCheckStatus");
		Criterion afisRegisterError = Restrictions.eq("afisRegisterStatus",
				errorCode);
		Criterion afisRegisterErrorNull = Restrictions
				.isNull("afisRegisterStatus");

		Criterion afisCheckError = Restrictions
				.eq("afisCheckStatus", errorCode);
		Criterion afisCheckErrorNull = Restrictions.isNull("afisCheckStatus");

		Criterion hasError = Restrictions.or(
				Restrictions.or(cpdCheckError, cpdCheckErrorNull),
				Restrictions.or(afisRegisterError, afisRegisterErrorNull),
				Restrictions.or(afisCheckError, afisCheckErrorNull));
		return hasError;
	}

	// 2015 Dec 17 commented for to remove dataPreparationStatus,
	// syncTxCpdStatus, syncInventoryStatus, syncCardCpdStatus
	// @Override
	// public List<NicUploadJob> getTransactionJobsInQueue() throws Exception {
	// logger.info("inside getTransactionJobsInQueue -- ");
	// DetachedCriteria criteria =
	// DetachedCriteria.forClass(NicUploadJob.class);
	// Criterion nullJobCurrentState = Restrictions.isNull("jobCurrentStage");
	// Criterion blankJobCurrentState = Restrictions.eq("jobCurrentStage", "");
	// Criterion inInvestigationStatus = Restrictions.in("investigationStatus",
	// new String[] {"02","04","05"});
	// Criterion nullAfisDeleteStatus = Restrictions.isNull("afisDeleteStatus");
	//
	// //11-OCT-2013 [jp] - temp avoid CAR_ISS
	// //Criterion notCarIss = Restrictions.not(Restrictions.eq("jobType",
	// "CAR_ISS"));
	// Criterion notCarIss = Restrictions.and(
	// Restrictions.and(Restrictions.not(Restrictions.eq("jobType",
	// "CAR_ISS")),Restrictions.not(Restrictions.eq("jobType", "CAR_TER")))
	// ,
	// Restrictions.and(Restrictions.not(Restrictions.eq("jobType",
	// "CAR_REA")),Restrictions.not(Restrictions.eq("jobType", "CAR_SUP")))
	// );
	//
	// //check if reprocess count is less than the reprocess limit
	// int reprocessMax = 3;
	// Criterion canReprocess =
	// Restrictions.or(Restrictions.isNull("jobReprocessCount"),
	// Restrictions.lt("jobReprocessCount",reprocessMax));
	//
	//
	// //check if any status encountered null
	// String errorCode = "09";
	// Criterion hasError =
	// Restrictions.or(
	// Restrictions.or(
	// Restrictions.or(
	// Restrictions.or(Restrictions.eq("cpdCheckStatus", errorCode),
	// Restrictions.eq("afisRegisterStatus", errorCode)),
	// Restrictions.or(Restrictions.eq("afisCheckStatus", errorCode),
	// Restrictions.eq("dataPreparationStatus", errorCode))
	// ),
	// Restrictions.or(
	// Restrictions.or(Restrictions.eq("persoRegisterStatus", errorCode),
	// Restrictions.eq("syncTxCpdStatus", errorCode)),
	// Restrictions.or(Restrictions.eq("syncInventoryStatus", errorCode),
	// Restrictions.eq("syncCardCpdStatus", errorCode))
	// )
	// ),
	// Restrictions.eq("afisVerifyStatus", errorCode));
	//
	//
	//
	// //----------//
	// //building criteria
	//
	// criteria.add(
	//
	// //11-OCT-2013 [jp] - temp avoid CAR_ISS
	// //Restrictions.and(notCarIss,
	//
	// Restrictions.or(
	// Restrictions.or(
	// Restrictions.or(nullJobCurrentState, blankJobCurrentState),
	// Restrictions.and(inInvestigationStatus,nullAfisDeleteStatus)),
	// Restrictions.and(hasError,canReprocess)
	// )
	//
	// //)//
	// );
	//
	// //17 Mar 2014 (jp) : changed getTransactionJobsInQueue to sort by job id
	// to process old cases first
	// //criteria.addOrder(Order.asc("workflowJobId"));
	//
	// //26 Jun 2014 (jp) : change order to get unprocessed jobs first before
	// jobs to reprocess and also order by oldest transactions first
	// criteria.addOrder(Order.asc("jobReprocessCount"));
	// criteria.addOrder(Order.asc("workflowJobId"));
	//
	// List<NicUploadJob> result = new ArrayList<NicUploadJob>();
	//
	// //add new logic to get top 100 cases first
	// this.getHibernateTemplate().setMaxResults(100);
	//
	// result = this.getHibernateTemplate().findByCriteria(criteria);
	// logger.info("hibernate template:"+this.getHibernateTemplate());
	// logger.info("result size:"+result.size());
	// //----------------------------------------------------------
	// //add new logic to filter reprocess jobs which are not yet ready for
	// reprocess
	// if(result !=null && result.size() > 0){
	//
	// result = cleanReprocsessTxn(result);
	//
	// }else{
	//
	// logger.info("empty results");
	// }
	// //end
	//
	// return result;
	//
	// }

	// get all transactions with error and reprocess count = 3
	@Override
	public List<NicUploadJob> getTxnWithMaxReprocessCount(
			Map<String, String> configMap) throws Exception {

		int waitHours = 24;// mininum waiting hours for reprocess of failed txns
		int minReprocessCount = 3; // first count for reprocess.
		int maxReprocessCount = 10; // last count for reprocess.

		if (configMap != null) {
			String minHoursForReprocessVal = configMap
					.get("MIN_HOUR_FOR_REPROCESS");
			String minReprocessCountVal = configMap.get("MAX_RETRY_COUNT");
			String maxReprocessCountVal = configMap.get("MAX_REPROCESS_COUNT");

			if (StringUtils.isNumeric(minHoursForReprocessVal)) {
				waitHours = Integer.parseInt(minHoursForReprocessVal);
			}
			if (StringUtils.isNumeric(minReprocessCountVal)) {
				minReprocessCount = Integer.parseInt(minReprocessCountVal);
			}
			if (StringUtils.isNumeric(maxReprocessCountVal)) {
				maxReprocessCount = Integer.parseInt(maxReprocessCountVal);
			}
			logger.info(
					"configuration replaced with config Map, waitHours: {}, minReprocessCount:{}, maxReprocessCount: {}",
					new Object[] { waitHours, minReprocessCount,
							maxReprocessCount });
		} else {
			logger.info(
					"initial configuration,  waitHours: {}, minReprocessCount:{}, maxReprocessCount: {}",
					new Object[] { waitHours, minReprocessCount,
							maxReprocessCount });
		}

		List<NicUploadJob> result = new ArrayList<NicUploadJob>();

		Calendar cal = Calendar.getInstance();
		// cal.setTime(new Date());
		// cal.add(Calendar.DATE, -1);
		cal.add(Calendar.HOUR, -waitHours);
		Date dateBefore1Day = cal.getTime();

		logger.trace("dateBefore2Days: {} ", dateBefore1Day);

		DetachedCriteria criteria = DetachedCriteria
				.forClass(NicUploadJob.class);
		Criterion reachedMaxReprocessCount = Restrictions.between(
				"jobReprocessCount", minReprocessCount, maxReprocessCount);
		Criterion isLastExecutedNotToday = Restrictions.le("jobEndTime",
				dateBefore1Day);
		Criterion hasGivenState = Restrictions.in("jobCurrentStage",
				new String[] { "CPD_CHECK", "AFIS_REGISTER", "AFIS_CHECK",
						"AFIS_VERIFY", "PERSO_REGISTER", "SYNC_TX_CPD" });

		// check if any status encountered error
		Criterion hasError = this.getErrorConditionCriterion();

		criteria.add(Restrictions.and(
				Restrictions.and(reachedMaxReprocessCount, hasError),
				// hasGivenState)
				Restrictions.and(isLastExecutedNotToday, hasGivenState)));

		this.getHibernateTemplate().setMaxResults(500);
		result = (List<NicUploadJob>) this.getHibernateTemplate()
				.findByCriteria(criteria);

		if (result != null && result.size() > 0) {
			logger.info("getTxnWithMaxReprocessCount has " + result.size()
					+ " results");
		} else {
			logger.info("getTxnWithMaxReprocessCount has empty results");
		}

		return result;
	}

	// ***************************************************

	private List<NicUploadJob> cleanReprocsessTxn(List<NicUploadJob> jobList,
			Map<String, String> configMap) {
		Calendar jobTime;
		Calendar currentCal;
		int interval = 60;// waiting time in seconds for retry of failed txns
		int intervalAfis = 120;
		int maxRetryCount = 3;
		if (configMap != null) {
			String normalRetryIntervalVal = configMap
					.get("NORMAL_RETRY_INTERVAL");
			String afisRetryIntervalVal = configMap.get("AFIS_RETRY_INTERVAL");
			String maxRetryCountVal = configMap.get("MAX_RETRY_COUNT");

			if (StringUtils.isNumeric(normalRetryIntervalVal)) {
				interval = Integer.parseInt(normalRetryIntervalVal);
			}
			if (StringUtils.isNumeric(afisRetryIntervalVal)) {
				intervalAfis = Integer.parseInt(afisRetryIntervalVal);
			}
			if (StringUtils.isNumeric(maxRetryCountVal)) {
				maxRetryCount = Integer.parseInt(maxRetryCountVal);
			}
			logger.info(
					"configuration replaced with config Map, interval: {}, intervalAfis:{}, maxRetryCount: {}",
					new Object[] { interval, intervalAfis, maxRetryCount });
		} else {
			logger.info(
					"initial configuration, interval: {}, intervalAfis:{}, maxRetryCount: {}",
					new Object[] { interval, intervalAfis, maxRetryCount });
		}

		List<NicUploadJob> toRemove = new ArrayList<NicUploadJob>();

		for (NicUploadJob job : jobList) {
			logger.trace("job[{}] time:{}", job.getWorkflowJobId(),
					job.getJobEndTime());
			if (job.getJobEndTime() != null) {

				int jobReprocessCount = (Integer) ObjectUtils.defaultIfNull(
						job.getJobReprocessCount(), 1);

				if (StringUtils.isNotEmpty(job.getJobCurrentStage())
						&& StringUtils.equalsIgnoreCase(
								job.getJobCurrentStage(), "AFIS_CHECK")) {
					interval = intervalAfis;
				}

				logger.trace("interval to be used is:{}", interval);

				// check reprocess after reprocessCount * X amount of seconds
				int secondToWait = jobReprocessCount * interval;
				logger.trace("job REPROCESS COUNT * waitInterval:{}",
						secondToWait);

				jobTime = new GregorianCalendar();
				jobTime.setTime(job.getJobEndTime());
				jobTime.add(Calendar.SECOND, secondToWait);

				logger.trace("job[{}] time: {}", job.getTransactionId(),
						jobTime.getTime());

				currentCal = new GregorianCalendar();
				currentCal.setTime(new Date());

				boolean isJobToExeTimeAfterCurrentTime = jobTime.getTime()
						.after(currentCal.getTime());
				logger.trace(
						"job current time[{}] vs next reprocesstime[{}], compare result: {} ",
						new Object[] { currentCal.getTime(), jobTime.getTime(),
								isJobToExeTimeAfterCurrentTime });

				if (checkIfError(job) && jobReprocessCount < maxRetryCount
						&& isJobToExeTimeAfterCurrentTime) {
					logger.info("job is not yet ready for reprocess: [{}][{}]",
							job.getWorkflowJobId(), job.getTransactionId());
					toRemove.add(job);
				}
			}
		}
		jobList.removeAll(toRemove);

		return jobList;
	}

	// util to check if job has error
	private boolean checkIfError(NicUploadJob job) {
		boolean cpdCheckStatus = StringUtils.equals(job.getCpdCheckStatus(),
				"09");
		boolean afisRegisterStatus = StringUtils.equals(
				job.getAfisRegisterStatus(), "09");
		boolean afisCheckStatus = StringUtils.equals(job.getAfisCheckStatus(),
				"09");
		boolean dataPreparationStatus = false; // StringUtils.equals(job.getDataPreparationStatus(),
												// "09");
		boolean persoRegisterStatus = StringUtils.equals(
				job.getPersoRegisterStatus(), "09");
		boolean syncTxCpdStatus = false; // StringUtils.equals(job.getSyncTxCpdStatus(),
											// "09");
		boolean syncInventoryStatus = false; // StringUtils.equals(job.getSyncInventoryStatus(),
												// "09");
		boolean syncCardCpdStatus = false; // StringUtils.equals(job.getSyncCardCpdStatus(),
											// "09");
		boolean afisVerifyStatus = StringUtils.equals(
				job.getAfisVerifyStatus(), "09");

		if (cpdCheckStatus || afisRegisterStatus || afisCheckStatus
				|| dataPreparationStatus || persoRegisterStatus
				|| syncTxCpdStatus || syncInventoryStatus || syncCardCpdStatus
				|| afisVerifyStatus) {

			return true;

		} else {
			return false;
		}

	}

	public PaginatedResult<NicUploadJob> findInvestigationJobForPaginationByOfficerId(
			String officerId, int pageNo, int pageSize) {
		return this.findInvestigationJobForPaginationByOfficerId(officerId,
				pageNo, pageSize, null);
	}

	public PaginatedResult<NicUploadJob> findInvestigationJobForPaginationByOfficerId(
			String officerId, int pageNo, int pageSize, Boolean assignedOnly) {
		return this.findInvestigationJobForPaginationByOfficerId(officerId,
				pageNo, pageSize, assignedOnly, null);
	}

	/**
	 * To return the PaginatedResult<NicUploadJob> for those job waiting for
	 * investigation. It will filter by the officer Id passed in.
	 * 
	 * @param officerId
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	public PaginatedResult<NicUploadJob> findInvestigationJobForPaginationByOfficerId(
			String officerId, int pageNo, int pageSize, Boolean assignedOnly,
			AssignmentFilter assignmentFilter) {
		DetachedCriteria dCriteria = DetachedCriteria
				.forClass(getPersistentClass());
		Order orders = null;
		if (StringUtils.isNotBlank(officerId)) {
			dCriteria.add(Restrictions.eq("investigationOfficerId", officerId));
		}
		dCriteria.add(Restrictions.not(Restrictions.in("investigationStatus",
				new String[] { RECORD_STATUS_COMPLETED, RECORD_STATUS_REJECTED,
						RECORD_STATUS_CANCELLED, RECORD_STATUS_SUSPENDED })));
		if (assignedOnly != null) {
			if (assignedOnly) {
				dCriteria.add(Restrictions.isNotNull("investigationOfficerId"));
			} else {
				dCriteria.add(Restrictions.isNull("investigationOfficerId"));
			}
		}
		if (assignmentFilter != null) {
			if (StringUtils.isNotBlank(assignmentFilter.getTransactionId())) {
				dCriteria.add(Restrictions.eq("transactionId",
						assignmentFilter.getTransactionId()));
			}
		}
		orders = Order.asc("workflowJobId");
		if (orders == null) {
			return this.findAllForPagination(dCriteria, pageNo, pageSize);
		} else {
			return this.findAllForPagination(dCriteria, pageNo, pageSize,
					orders);
		}
	}

	@Override
	public void updateDecisionOnReject(long rejectJobId, String userId,
			String wkstnId) throws Exception {
		try {
			DetachedCriteria criteria = DetachedCriteria
					.forClass(NicJobCpdCheckResult.class);
			criteria.add(Restrictions.eq("workflowJobId", rejectJobId));
			List<NicJobCpdCheckResult> list = (List<NicJobCpdCheckResult>) getHibernateTemplate()
					.findByCriteria(criteria);
			if (CollectionUtils.isNotEmpty(list)) {
				NicJobCpdCheckResult rejectUpdateObj = list.get(0);
				rejectUpdateObj.setDecision("Y");
				rejectUpdateObj.setDecisionOfficerId(userId);
				rejectUpdateObj.setDecisionTime(new Date());
				rejectUpdateObj.setUpdateBy(userId);
				rejectUpdateObj.setUpdateWkstnId(wkstnId);
				rejectUpdateObj.setUpdateDate(new Date());
				getHibernateTemplate().update(rejectUpdateObj);
			}
		} catch (Exception e) {
			logger.error("Error occurred while updating the decision to approve, Exception: "
					+ e.getMessage());
			e.printStackTrace();
		}
		// return rejectJobID;
	}

	@Override
	public long getPendingInvestigationsCount() throws Exception {
		long pendingCount = 0;
		Session session = null;
		try {
			DetachedCriteria criteria = DetachedCriteria
					.forClass(NicUploadJob.class);
			criteria.add(Restrictions.isNull("investigationOfficerId"));
			criteria.add(Restrictions.eq("investigationStatus",
					NicUploadJob.RECORD_STATUS_INITIAL));

			// List<NicUploadJob> list = this.findAll(criteria);
			// if (CollectionUtils.isEmpty(list) || list.size() <= 0) {
			// //throw new Exception("No Investigation Job Found.");
			// logger.info("No Investigation Job Found.");
			// } else {
			// pendingCount = list.size();
			// }

			// 2016-Mar-19 (Khang): Use count method instead of list all then
			// get size
			session = this.openSession();
			Criteria c = criteria.getExecutableCriteria(session);
			c.setProjection(Projections.rowCount());
			Long count = (Long) c.uniqueResult();
			pendingCount = count.longValue();

		} catch (Exception e) {
			logger.error("Error occurred while getting the pending investigations count, Exception: "
					+ e.getMessage());
			e.printStackTrace();
		} finally {
			session.close();
		}
		return pendingCount;
	}

	@Override
	public long getSuspendedInvestigationsCount() throws Exception {
		long count = 0;
		Session session = null;
		try {
			DetachedCriteria criteria = DetachedCriteria
					.forClass(NicUploadJob.class);
			criteria.add(Restrictions.isNull("investigationOfficerId"));
			criteria.add(Restrictions.eq("investigationStatus",
					NicUploadJob.RECORD_STATUS_SUSPENDED));
			// List<NicUploadJob> list = this.findAll(criteria);
			// if (CollectionUtils.isEmpty(list) || list.size() <= 0) {
			// // throw new Exception("No Investigation Job Found.");
			// logger.info("No Investigation Job Found.");
			// } else {
			// count = list.size();
			// }

			// 2016-Mar-19 (Khang): Use count method instead of list all then
			// get size
			session = this.openSession();
			Criteria c = criteria.getExecutableCriteria(session);
			c.setProjection(Projections.rowCount());
			Long cnt = (Long) c.uniqueResult();
			count = cnt.longValue();

		} catch (Exception e) {
			logger.error("Error occurred while getting the Suspended investigations count, Exception: "
					+ e.getMessage());
			e.printStackTrace();
		} finally {
			session.close();
		}
		return count;
	}

	@Override
	public PaginatedResult<NicUploadJob> findRejectedJobsForSupervisor(
			String officerId, int pageNo, int pageSize) {
		DetachedCriteria detachedCriteria = DetachedCriteria
				.forClass(getPersistentClass());
		if (StringUtils.isNotBlank(officerId)) {
			detachedCriteria.add(Restrictions.eq("investigationOfficerId",
					officerId));
			detachedCriteria.add(Restrictions.eq("investigationStatus",
					RECORD_STATUS_REJECTED));
		}
		return this.findAllForPagination(detachedCriteria, pageNo, pageSize);
	}

	@Override
	public void assignNewRejectJob(String userid) throws Exception {
		try {
			DetachedCriteria criteria = DetachedCriteria
					.forClass(getPersistentClass());
			// criteria.add(Restrictions.isNull("approvalOfficerId"));
			// criteria.add(Restrictions.isNull("approvalStatus"));
			criteria.add(Restrictions.eq("investigationStatus",
					NicUploadJob.RECORD_STATUS_REJECTED));
			criteria.addOrder(Order.asc("createDate"));
			List<NicUploadJob> list = this.findAll(criteria);
			if (CollectionUtils.isEmpty(list) || list.size() <= 0) {
				throw new Exception("No Rejected Job Found.");
			}
			NicUploadJob updateobject = list.get(0);
			updateobject.setInvestigationOfficerId(userid);
			updateobject
					.setJobCurrentStage(NicTransactionLog.TRANSACTION_STAGE_INVESTIGATION);
			updateobject.setUpdateBy(userid);
			updateobject.setUpdateDatetime(new Date());
			updateobject.setUpdateWkstnId("SYSTEM");
			getHibernateTemplate().update(updateobject);
		} catch (Exception e) {
			logger.error("======== Exception while updating data ", e);
			throw e;
		}
	}

	@Override
	public Long createWorkflowJob(String transactionId, String jobType,
			Integer jobPriority) {
		Long nicUploadJobId = null;

		String officerId = SYSTEM_EPP;
		String wkstnId = SYSTEM_EPP;
		try {
			java.net.InetAddress localMachine = InetAddress.getLocalHost();
			wkstnId = localMachine.getHostName().toUpperCase();
		} catch (Exception e) {
		}

		NicUploadJob nicUploadJobDBO = new NicUploadJob();
		nicUploadJobDBO.setTransactionId(transactionId);
		nicUploadJobDBO.setJobType(jobType);
		nicUploadJobDBO.setJobPriority(jobPriority);
		nicUploadJobDBO.setJobCreatedTime(new Date());
		nicUploadJobDBO.setCreateBy(officerId);
		nicUploadJobDBO.setCreateWkstnId(wkstnId);
		nicUploadJobDBO.setCreateDatetime(new Date());

		// tunt - Gán trạng thái chờ InVestigation thủ công, ko check qua AFis
		// nữa (Mặc định là null sau đó job quyets bản ghi null và check Afis)
		// nicUploadJobDBO.setJobApproveStatus("0");
		// nicUploadJobDBO.setInvestigationStatus(NicUploadJob.RECORD_STATUS_IN_PROGRESS);
		nicUploadJobId = this.save(nicUploadJobDBO);
		logger.debug(
				"[createWorkflowJob] save NicUploadJob, transactionId:{}, type:{}, jobId:{} ",
				new Object[] { transactionId, jobType, nicUploadJobId });
		return nicUploadJobId;
	}

	@Override
	public List<NicUploadJob> findAllPendingInvestigationJob() {
		String[] statusesToReset = { RECORD_STATUS_INITIAL,
				RECORD_STATUS_IN_PROGRESS };
		DetachedCriteria detachedCriteria = DetachedCriteria
				.forClass(getPersistentClass());
		detachedCriteria.add(Restrictions.isNotNull("investigationOfficerId"));
		detachedCriteria.add(Restrictions.in("investigationStatus",
				statusesToReset));
		return this.findAll(detachedCriteria);
	}

	@Override
	public void cancelTransaction(Long jobId, String userId, String wkstnId) {
		try {
			DetachedCriteria criteria = DetachedCriteria
					.forClass(NicUploadJob.class);
			criteria.add(Restrictions.eq("workflowJobId", jobId));
			List<NicUploadJob> list = (List<NicUploadJob>) getHibernateTemplate()
					.findByCriteria(criteria);
			if (CollectionUtils.isNotEmpty(list)) {
				NicUploadJob rejectUpdateObj = list.get(0);
				rejectUpdateObj.setInvestigationStatus(RECORD_STATUS_CANCELLED);
				rejectUpdateObj.setUpdateBy(userId);
				rejectUpdateObj.setUpdateWkstnId(wkstnId);
				rejectUpdateObj.setUpdateDatetime(new Date());
				getHibernateTemplate().update(rejectUpdateObj);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public PaginatedResult<NicUploadJob> findAllForPagination(
			NicUploadJob nicUploadJob, Date dateFrom, Date dateTo, int pageNo,
			int pageSize, boolean ignoreCase, boolean enableLike, Order order)
			throws Exception {

		DetachedCriteria dCrit = DetachedCriteria.forClass(NicUploadJob.class);
		dCrit.add(Example.create(nicUploadJob));

		List<String> transIds = null;
		// TODO to implement
		// if (!CollectionUtils.isEmpty(nicUploadJob.getNicRegistrationDatas()))
		// {
		// NicRegistrationData regData = (NicRegistrationData)
		// nicUploadJob.getNicRegistrationDatas().iterator().next();
		// NicTransaction nicTransaction = regData.getNicTransaction();
		// if (nicTransaction != null) {
		// DetachedCriteria tCrit = DetachedCriteria
		// .forClass(NicTransaction.class);
		// if (StringUtils.isNotEmpty(nicTransaction.getNin())) {
		// // tCrit.add(Restrictions.eq("nin",
		// // nicTransaction.getNin()));
		// tCrit.add(Restrictions.like("nin", nicTransaction.getNin(),
		// MatchMode.ANYWHERE).ignoreCase());
		// }
		// if (StringUtils.isNotEmpty(nicTransaction.getRegSiteCode())) {
		// tCrit.add(Restrictions.eq("regSiteCode",
		// nicTransaction.getRegSiteCode()));
		// }
		// tCrit.setProjection(Projections.property("transactionId"));
		// transIds = getHibernateTemplate().findByCriteria(tCrit);
		//
		// if (CollectionUtils.isEmpty(transIds)) {
		// return new PaginatedResult<NicUploadJob>(0, 1,
		// new ArrayList<NicUploadJob>());
		// }
		// }
		// }

		if (CollectionUtils.isNotEmpty(transIds)) {
			// dCrit.add(Restrictions.in("transactionId", transIds));

			Conjunction conjunction = Restrictions.conjunction();
			if (transIds.size() > 0) {
				Disjunction disjunctionIdListCount = Restrictions.disjunction();

				for (int i = 0; i < (((transIds.size() / 1000) + 1)); i++) {
					Criterion list = null;
					if (transIds.size() > ((i + 1) * 1000)) {
						list = Restrictions.in("transactionId", Arrays
								.copyOfRange(transIds.toArray(), (i * 1000),
										((i + 1) * 1000)));
					} else {
						list = Restrictions.in("transactionId", Arrays
								.copyOfRange(transIds.toArray(), (i * 1000),
										transIds.size()));
					}

					disjunctionIdListCount.add(list);
				}

				conjunction.add(disjunctionIdListCount);
			}
			dCrit.add(conjunction);

		}

		if (dateTo != null) {
			dateTo = DateUtil.getEndOfDay(dateTo);
		}

		if (dateFrom != null && dateTo != null) {
			dCrit.add(Restrictions.between("jobUploadTime", dateFrom, dateTo));
		} else if (dateFrom != null) {
			dCrit.add(Restrictions.gt("jobUploadTime", dateFrom));
		} else if (dateTo != null) {
			dCrit.add(Restrictions.lt("jobUploadTime", dateTo));
		}

		return findAllForPagination(dCrit, pageNo, pageSize, order);
	}

	@Override
	public PaginatedResult<NicUploadJob> findAllForPagination(
			NicUploadJob nicUploadJob, String[] investStatus, Long jobId,
			boolean isShowErrTrans, int pageNo, int pageSize,
			boolean ignoreCase, boolean enableLike, Order order) {
		DetachedCriteria dCrit = DetachedCriteria.forClass(NicUploadJob.class);
		dCrit.add(Example.create(nicUploadJob));

		List<String> transIds = null;
		// TODO to implement
		// if (!CollectionUtils.isEmpty(nicUploadJob.getNicRegistrationDatas()))
		// {
		// NicRegistrationData regData = (NicRegistrationData)
		// nicUploadJob.getNicRegistrationDatas().iterator().next();
		// NicTransaction nicTransaction = regData.getNicTransaction();
		// if (nicTransaction != null) {
		// DetachedCriteria tCrit = DetachedCriteria
		// .forClass(NicTransaction.class);
		// if (StringUtils.isNotEmpty(nicTransaction.getRegSiteCode())) {
		// tCrit.add(Restrictions.eq("regSiteCode",
		// nicTransaction.getRegSiteCode()));
		// }
		// tCrit.setProjection(Projections.property("transactionId"));
		// transIds = getHibernateTemplate().findByCriteria(tCrit);
		//
		// if (CollectionUtils.isEmpty(transIds)) {
		// return new PaginatedResult<NicUploadJob>(0, 1,
		// new ArrayList<NicUploadJob>());
		// }
		// }
		// }

		if (jobId != null) {
			dCrit.add(Restrictions.eq("workflowJobId", jobId));
		}
		if (CollectionUtils.isNotEmpty(transIds)) {
			// dCrit.add(Restrictions.in("transactionId", transIds));

			Conjunction conjunction = Restrictions.conjunction();
			if (transIds.size() > 0) {
				Disjunction disjunctionIdListCount = Restrictions.disjunction();

				for (int i = 0; i < (((transIds.size() / 1000) + 1)); i++) {
					Criterion list = null;
					if (transIds.size() > ((i + 1) * 1000)) {
						list = Restrictions.in("transactionId", Arrays
								.copyOfRange(transIds.toArray(), (i * 1000),
										((i + 1) * 1000)));
					} else {
						list = Restrictions.in("transactionId", Arrays
								.copyOfRange(transIds.toArray(), (i * 1000),
										transIds.size()));
					}

					disjunctionIdListCount.add(list);
				}

				conjunction.add(disjunctionIdListCount);
			}
			dCrit.add(conjunction);
		}

		if (!isShowErrTrans) {
			if (investStatus != null && investStatus.length > 0) {
				dCrit.add(Restrictions.in("investigationStatus", investStatus));
			}
		} else {
			// String[] errorCodes
			// ={"SYNC_CARD_CPD_ERROR","AFIS_REGISTER_ERROR","SYNC_TX_CPD_ERROR","CPD_CHECK_ERROR","INVENTORY_SYNC_ERROR","AFIS_CHECK_ERROR"};
			List<String> persoErrorTransIds = null;

			DetachedCriteria tCrit = DetachedCriteria
					.forClass(NicTransaction.class);
			tCrit.add(Restrictions.eq("transactionStatus", "PERSO_ERROR"));
			tCrit.setProjection(Projections.property("transactionId"));
			persoErrorTransIds = (List<String>) getHibernateTemplate()
					.findByCriteria(tCrit);
			Criterion jobCurrentStateCriterion = Restrictions.like(
					"jobCurrentStatus", "%_ERROR"); // Restrictions.and(Restrictions.like("jobCurrentStatus",
													// "%_ERROR"),Restrictions.eq("nicJobErrorFlag",
													// true));
			Criterion personErrorCritetion = null;
			if (CollectionUtils.isNotEmpty(persoErrorTransIds)) {
				// personErrorCritetion = Restrictions.in("transactionId",
				// persoErrorTransIds);

				Conjunction conjunction = Restrictions.conjunction();
				if (persoErrorTransIds.size() > 0) {
					Disjunction disjunctionIdListCount = Restrictions
							.disjunction();

					for (int i = 0; i < (((persoErrorTransIds.size() / 1000) + 1)); i++) {
						Criterion list = null;
						if (persoErrorTransIds.size() > ((i + 1) * 1000)) {
							list = Restrictions.in("transactionId", Arrays
									.copyOfRange(persoErrorTransIds.toArray(),
											(i * 1000), ((i + 1) * 1000)));
						} else {
							list = Restrictions.in("transactionId", Arrays
									.copyOfRange(persoErrorTransIds.toArray(),
											(i * 1000),
											persoErrorTransIds.size()));
						}

						disjunctionIdListCount.add(list);
					}

					conjunction.add(disjunctionIdListCount);
				}
				personErrorCritetion = (conjunction);
			}

			if (personErrorCritetion != null) {
				dCrit.add(Restrictions.or(jobCurrentStateCriterion,
						personErrorCritetion));
			} else {
				dCrit.add(jobCurrentStateCriterion);
			}

		}
		return findAllForPagination(dCrit, pageNo, pageSize, order);
	}

	public PaginatedResult<NicUploadJob> findAllForPagination(
			NicUploadJob nicUploadJob, String[] investStatus, Long jobId,
			String siteCode, boolean isShowErrTrans, int pageNo, int pageSize,
			boolean ignoreCase, boolean enableLike, Order order) {
		DetachedCriteria dCrit = DetachedCriteria.forClass(NicUploadJob.class);
		dCrit.add(Example.create(nicUploadJob));

		if (jobId != null) {
			dCrit.add(Restrictions.eq("workflowJobId", jobId));
		}

		List<String> transIds = null;
		if (StringUtils.isNotBlank(siteCode)) {
			DetachedCriteria tCrit = DetachedCriteria
					.forClass(NicTransaction.class);
			tCrit.add(Restrictions.eq("regSiteCode", siteCode));
			tCrit.setProjection(Projections.property("transactionId"));
			transIds = (List<String>) getHibernateTemplate().findByCriteria(
					tCrit);
			if (CollectionUtils.isEmpty(transIds)) {
				return new PaginatedResult<NicUploadJob>(0, 1,
						new ArrayList<NicUploadJob>());
			}
		}

		if (CollectionUtils.isNotEmpty(transIds)) {
			// dCrit.add(Restrictions.in("transactionId", transIds));

			Conjunction conjunction = Restrictions.conjunction();
			if (transIds.size() > 0) {
				Disjunction disjunctionIdListCount = Restrictions.disjunction();

				for (int i = 0; i < (((transIds.size() / 1000) + 1)); i++) {
					Criterion list = null;
					if (transIds.size() > ((i + 1) * 1000)) {
						list = Restrictions.in("transactionId", Arrays
								.copyOfRange(transIds.toArray(), (i * 1000),
										((i + 1) * 1000)));
					} else {
						list = Restrictions.in("transactionId", Arrays
								.copyOfRange(transIds.toArray(), (i * 1000),
										transIds.size()));
					}

					disjunctionIdListCount.add(list);
				}

				conjunction.add(disjunctionIdListCount);
			}
			dCrit.add(conjunction);
		}

		if (!isShowErrTrans) {
			if (investStatus != null && investStatus.length > 0) {
				dCrit.add(Restrictions.in("investigationStatus", investStatus));
			}
		} else {
			// String[] errorCodes
			// ={"SYNC_CARD_CPD_ERROR","AFIS_REGISTER_ERROR","SYNC_TX_CPD_ERROR","CPD_CHECK_ERROR","INVENTORY_SYNC_ERROR","AFIS_CHECK_ERROR"};
			List<String> persoErrorTransIds = null;

			DetachedCriteria tCrit = DetachedCriteria
					.forClass(NicTransaction.class);
			tCrit.add(Restrictions.eq("transactionStatus", "PERSO_ERROR"));
			tCrit.setProjection(Projections.property("transactionId"));
			persoErrorTransIds = (List<String>) getHibernateTemplate()
					.findByCriteria(tCrit);
			Criterion jobCurrentStateCriterion = Restrictions.like(
					"jobCurrentStatus", "%_ERROR"); // Restrictions.and(Restrictions.like("jobCurrentStatus",
													// "%_ERROR"),Restrictions.eq("nicJobErrorFlag",
													// true));
			Criterion personErrorCritetion = null;
			if (CollectionUtils.isNotEmpty(persoErrorTransIds)) {
				// personErrorCritetion = Restrictions.in("transactionId",
				// persoErrorTransIds);

				Conjunction conjunction = Restrictions.conjunction();
				if (persoErrorTransIds.size() > 0) {
					Disjunction disjunctionIdListCount = Restrictions
							.disjunction();

					for (int i = 0; i < (((persoErrorTransIds.size() / 1000) + 1)); i++) {
						Criterion list = null;
						if (persoErrorTransIds.size() > ((i + 1) * 1000)) {
							list = Restrictions.in("transactionId", Arrays
									.copyOfRange(persoErrorTransIds.toArray(),
											(i * 1000), ((i + 1) * 1000)));
						} else {
							list = Restrictions.in("transactionId", Arrays
									.copyOfRange(persoErrorTransIds.toArray(),
											(i * 1000),
											persoErrorTransIds.size()));
						}

						disjunctionIdListCount.add(list);
					}

					conjunction.add(disjunctionIdListCount);
				}
				personErrorCritetion = (conjunction);
			}

			if (personErrorCritetion != null) {
				dCrit.add(Restrictions.or(jobCurrentStateCriterion,
						personErrorCritetion));
			} else {
				dCrit.add(jobCurrentStateCriterion);
			}

		}
		return findAllForPagination(dCrit, pageNo, pageSize, order);
	}

	@Override
	public String updatePersoRegisterStatusOnReprint(String transactionId,
			String transactionType, String userId, String wkstnId)
			throws Exception {
		String status = "";
		try {
			DetachedCriteria criteria = DetachedCriteria
					.forClass(NicUploadJob.class);
			criteria.add(Restrictions.eq("transactionId", transactionId));
			criteria.add(Restrictions.eq("jobType", transactionType));
			List<NicUploadJob> list = (List<NicUploadJob>) getHibernateTemplate()
					.findByCriteria(criteria);
			if (CollectionUtils.isNotEmpty(list)) {

				NicUploadJob updateObj = list.get(0);
				updateObj.setPersoRegisterStatus("00");
				updateObj.setUpdateBy(userId);
				updateObj.setUpdateWkstnId(wkstnId);
				updateObj.setUpdateDatetime(new Date());
				updateObj.setJobReprocessCount(null);
				getHibernateTemplate().update(updateObj);
				status = "success";
			}
			status = "fail";
		} catch (Exception e) {
			logger.error("Error occurred while updating the perso register status upon reprint, Exception: "
					+ e.getMessage());
			e.printStackTrace();
		}
		return status;
	}

	@Override
	public Long getUploadJobId(String transId, String[] jobTypes)
			throws Exception {
		DetachedCriteria detachedCriteria = DetachedCriteria
				.forClass(NicUploadJob.class);

		detachedCriteria.add(Restrictions.eq("transactionId", transId));
		detachedCriteria.add(Restrictions.in("jobType", jobTypes));

		List<NicUploadJob> list = (List<NicUploadJob>) getHibernateTemplate()
				.findByCriteria(detachedCriteria);

		if (CollectionUtils.isNotEmpty(list)) {
			return list.get(0).getWorkflowJobId();
		} else {
			throw new Exception("No Job Id found with Transaction Id: "
					+ transId + " and with job status: " + jobTypes);
		}
	}

	public PaginatedResult<NicUploadJob> findInvestigationJobForPaginationByOfficerId(
			String[] recordStatuses, String officerId, int pageNo, int pageSize) {
		return this.findInvestigationJobForPaginationByOfficerId(
				recordStatuses, officerId, pageNo, pageSize, null);
	}

	public PaginatedResult<NicUploadJob> findInvestigationJobForPaginationByOfficerId(
			String[] recordStatuses, String officerId, int pageNo,
			int pageSize, Boolean assignedOnly) {
		return this
				.findInvestigationJobForPaginationByOfficerId(recordStatuses,
						officerId, pageNo, pageSize, assignedOnly, null);
	}

	public PaginatedResult<NicUploadJob> findInvestigationJobForPaginationByOfficerId(
			String[] recordStatuses, String officerId, int pageNo,
			int pageSize, Boolean assignedOnly,
			AssignmentFilterAll assignmentFilter) {
		DetachedCriteria dCriteria = DetachedCriteria
				.forClass(getPersistentClass());
		dCriteria.createAlias("nicTransaction", "nicTransaction");
		Order orders = null;
		Boolean perso = false;
		Boolean StageJobType = false;
		dCriteria.add(Restrictions.isNull("nicTransaction.nicSiteCode"));
		if (recordStatuses != null && recordStatuses.length > 0) {
			for (String st : recordStatuses) {
				if (st.equals("LOS")) {
					StageJobType = true;
					break;
				}
				if (st.equals(NicUploadJob.RECORD_STATUS_APPROVE_PERSO)) {
					perso = true;
					break;
				}
			}
			if (!StageJobType)
				dCriteria.add(Restrictions.in("investigationStatus",
						recordStatuses));
			else
				dCriteria.add(Restrictions.in("jobType", recordStatuses));
		}

		if (StringUtils.isNotBlank(officerId) && !StageJobType) {
			dCriteria.add(Restrictions.eq("investigationOfficerId", officerId));
		}
		if (assignedOnly != null) {
			if (assignedOnly && !StageJobType) {
				dCriteria.add(Restrictions.isNotNull("investigationOfficerId"));
			} else {
				dCriteria.add(Restrictions.isNull("investigationOfficerId"));
			}
		}
		/*
		 * if (assignmentFilter != null) { if
		 * (StringUtils.isNotBlank(assignmentFilter.getTransactionId())) {
		 * dCriteria.add(Restrictions.eq("transactionId",
		 * assignmentFilter.getTransactionId())); } }
		 */

		if (assignmentFilter != null) {
			if (StringUtils.isNotBlank(assignmentFilter.getTransactionId())) {
				dCriteria.add(Restrictions.eq("transactionId",
						assignmentFilter.getTransactionId()));
			}
			if (assignmentFilter.getPriority() != null) {
				dCriteria.add(Restrictions.eq("nicTransaction.priority",
						assignmentFilter.getPriority()));
			}
			if (StringUtils.isNotBlank(assignmentFilter.getRegSiteCode())) {
				dCriteria.add(Restrictions.eq("nicTransaction.regSiteCode",
						assignmentFilter.getRegSiteCode()));
			}
			if (assignmentFilter.getCreateDate() != null) {
				dCriteria.add(Restrictions.ge("nicTransaction.createDatetime",
						assignmentFilter.getCreateDate()));

				Date maxDate = new Date(assignmentFilter.getCreateDate()
						.getTime() + TimeUnit.DAYS.toMillis(1));
				dCriteria.add(Restrictions.lt("nicTransaction.createDatetime",
						maxDate));
			}
			if (StringUtils.isNotBlank(assignmentFilter.getPassportType())) {
				dCriteria.add(Restrictions.eq("nicTransaction.passportType",
						assignmentFilter.getPassportType()));
			}

			if (StringUtils.isNotBlank(assignmentFilter.getTransactionType())) {
				dCriteria.add(Restrictions.eq("nicTransaction.transactionType",
						assignmentFilter.getTransactionType()));
			}

			if (StringUtils.isNotBlank(assignmentFilter.getPackageCode())) {
				dCriteria.add(Restrictions.eq("nicTransaction.packageID",
						assignmentFilter.getPackageCode()));
			}

			if (assignmentFilter.getIssueDate() != null) {
				dCriteria.add(Restrictions.eq(
						"nicTransaction.estDateOfCollection",
						assignmentFilter.getIssueDate()));
			}
			if ((StringUtils
					.isNotBlank(assignmentFilter.getTypeInvestigation()))) {
				if (!perso)
					dCriteria.add(Restrictions.eq("investigationType",
							assignmentFilter.getTypeInvestigation()));
				else {
					if (assignmentFilter.getTypeInvestigation().contains("0"))
						dCriteria.add(Restrictions.eq(
								"nicTransaction.syncPassport", 0));
					else if (assignmentFilter.getTypeInvestigation().contains(
							"1"))
						dCriteria.add(Restrictions.eq(
								"nicTransaction.syncPassport", 1));
					else if (assignmentFilter.getTypeInvestigation().contains(
							"2"))
						dCriteria.add(Restrictions
								.isNull("nicTransaction.syncPassport"));
				}
			}
			if (assignmentFilter.getTranArray() != null
					&& assignmentFilter.getTranArray().length > 0) {
				dCriteria.add(Restrictions.in("transactionId",
						assignmentFilter.getTranArray()));
			}
		}
		orders = Order.asc("workflowJobId");
		if (orders == null) {
			return this.findAllForPagination(dCriteria, pageNo, pageSize);
		} else {
			return this.findAllForPagination(dCriteria, pageNo, pageSize,
					orders);
		}
	}

	public PaginatedResult<NicUploadJob> findInvestigationJobForPaginationByOfficerIdAll(
			String[] recordStatuses, String officerId, int pageNo,
			int pageSize, Boolean assignedOnly,
			AssignmentFilterAll assignmentFilter) {
		DetachedCriteria dCriteria = DetachedCriteria
				.forClass(getPersistentClass());
		dCriteria.createAlias("nicTransaction", "nicTransaction");
		Order orders = null;

		Boolean StageJobType = false;
		if (recordStatuses != null && recordStatuses.length > 0) {
			for (String st : recordStatuses) {
				if (st.equals("LOS")) {
					StageJobType = true;
					break;
				}
			}
			if (!StageJobType)
				dCriteria.add(Restrictions.in("investigationStatus",
						recordStatuses));
			else
				dCriteria.add(Restrictions.in("jobType", recordStatuses));
		}

		if (StringUtils.isNotBlank(officerId) && !StageJobType) {
			dCriteria.add(Restrictions.eq("investigationOfficerId", officerId));
		}
		if (assignedOnly != null) {
			if (assignedOnly && !StageJobType) {
				dCriteria.add(Restrictions.isNotNull("investigationOfficerId"));
			} else {
				dCriteria.add(Restrictions.isNull("investigationOfficerId"));
			}
		}
		/*
		 * if (assignmentFilter != null) { if
		 * (StringUtils.isNotBlank(assignmentFilter.getTransactionId())) {
		 * dCriteria.add(Restrictions.eq("transactionId",
		 * assignmentFilter.getTransactionId())); } }
		 */

		if (assignmentFilter != null) {
			if (StringUtils.isNotBlank(assignmentFilter.getTransactionId())) {
				dCriteria.add(Restrictions.eq("transactionId",
						assignmentFilter.getTransactionId()));
			}
			if (assignmentFilter.getPriority() != null) {
				dCriteria.add(Restrictions.eq("nicTransaction.priority",
						assignmentFilter.getPriority()));
			}
			if (StringUtils.isNotBlank(assignmentFilter.getRegSiteCode())) {
				dCriteria.add(Restrictions.eq("nicTransaction.regSiteCode",
						assignmentFilter.getRegSiteCode()));
			}
			if (assignmentFilter.getCreateDate() != null) {
				dCriteria.add(Restrictions.ge("nicTransaction.createDatetime",
						assignmentFilter.getCreateDate()));

				Date maxDate = new Date(assignmentFilter.getCreateDate()
						.getTime() + TimeUnit.DAYS.toMillis(1));
				dCriteria.add(Restrictions.lt("nicTransaction.createDatetime",
						maxDate));
			}
			if (StringUtils.isNotBlank(assignmentFilter.getPassportType())) {
				dCriteria.add(Restrictions.eq("nicTransaction.passportType",
						assignmentFilter.getPassportType()));
			}

			if (StringUtils.isNotBlank(assignmentFilter.getTransactionType())) {
				dCriteria.add(Restrictions.eq("nicTransaction.transactionType",
						assignmentFilter.getTransactionType()));
			}

			if (assignmentFilter.getIssueDate() != null) {
				dCriteria.add(Restrictions.eq(
						"nicTransaction.estDateOfCollection",
						assignmentFilter.getIssueDate()));
			}

			if (StringUtils.isNotBlank(assignmentFilter.getPackageCode())) {
				dCriteria.add(Restrictions.eq("nicTransaction.packageID",
						assignmentFilter.getPackageCode()));
			}

			if ((StringUtils
					.isNotBlank(assignmentFilter.getTypeInvestigation()))) {
				dCriteria.add(Restrictions.eq("investigationType",
						assignmentFilter.getTypeInvestigation()));
			}
			if (assignmentFilter.getTranArray() != null
					&& assignmentFilter.getTranArray().length > 0) {
				dCriteria.add(Restrictions.in("transactionId",
						assignmentFilter.getTranArray()));
			}
			if (StringUtils.isNotBlank(assignmentFilter.getValidateInfoBca())) {
				if (assignmentFilter.getValidateInfoBca().equals("0")) {
					Criterion stage1 = Restrictions.eq("validateInfoBca", 0);
					Criterion stage2 = Restrictions.isNull("validateInfoBca");
					dCriteria.add(Restrictions.or(stage1, stage2));
					// Integer[] stage = { 0 , null };
					// dCriteria.add(Restrictions.in("validateInfoBca", stage));
					// dCriteria.add(Restrictions.eq("validateInfoBca",
					// Integer.parseInt(assignmentFilter.getValidateInfoBca())));
				} else {
					dCriteria.add(Restrictions.eq("validateInfoBca", Integer
							.parseInt(assignmentFilter.getValidateInfoBca())));
				}
			}
		}
		orders = Order.asc("workflowJobId");
		if (orders == null) {
			return this.findAllForPagination(dCriteria, pageNo, pageSize);
		} else {
			return this.findAllForPagination(dCriteria, pageNo, pageSize,
					orders);
		}
	}

	public PaginatedResult<NicUploadJob> findInvestigationJobForPaginationByOfficerId_DSQ(
			String[] recordStatuses, String officerId, int pageNo,
			int pageSize, Boolean assignedOnly,
			AssignmentFilterAll assignmentFilter) {
		DetachedCriteria dCriteria = DetachedCriteria
				.forClass(getPersistentClass());
		dCriteria.createAlias("nicTransaction", "nicTransaction");
		Order orders = null;

		Boolean StageJobType = false;
		dCriteria.add(Restrictions.isNotNull("nicTransaction.nicSiteCode"));
		/*
		 * if (recordStatuses != null && recordStatuses.length > 0) { for
		 * (String st : recordStatuses) { if (st.equals("LOS")) { StageJobType =
		 * true; break; } } if (!StageJobType)
		 * dCriteria.add(Restrictions.in("investigationStatus",
		 * recordStatuses)); else dCriteria.add(Restrictions.in("jobType",
		 * recordStatuses)); }
		 */

		if (StringUtils.isNotBlank(officerId) && !StageJobType) {
			dCriteria.add(Restrictions.eq("investigationOfficerId", officerId));
		}
		if (assignedOnly != null) {
			if (assignedOnly && !StageJobType) {
				dCriteria.add(Restrictions.isNotNull("investigationOfficerId"));
			} else {
				dCriteria.add(Restrictions.isNull("investigationOfficerId"));
			}
		}
		/*
		 * if (assignmentFilter != null) { if
		 * (StringUtils.isNotBlank(assignmentFilter.getTransactionId())) {
		 * dCriteria.add(Restrictions.eq("transactionId",
		 * assignmentFilter.getTransactionId())); } }
		 */

		if (assignmentFilter != null) {
			if (StringUtils.isNotBlank(assignmentFilter.getTransactionId())) {
				dCriteria.add(Restrictions.eq("transactionId",
						assignmentFilter.getTransactionId()));
			}

			if (assignmentFilter.getPriority() != null) {
				dCriteria.add(Restrictions.eq("nicTransaction.priority",
						assignmentFilter.getPriority()));
			}

			if (StringUtils.isNotBlank(assignmentFilter.getRegSiteCode())) {
				dCriteria.add(Restrictions.eq("nicTransaction.regSiteCode",
						assignmentFilter.getRegSiteCode()));
			}

			if (StringUtils.isNotBlank(assignmentFilter.getPassportType())) {
				dCriteria.add(Restrictions.eq("nicTransaction.passportType",
						assignmentFilter.getPassportType()));
			}

			if (StringUtils.isNotBlank(assignmentFilter.getTransactionType())) {
				dCriteria.add(Restrictions.eq("nicTransaction.transactionType",
						assignmentFilter.getTransactionType()));
			}

			if (assignmentFilter.getCreateDate() != null) {
				dCriteria.add(Restrictions.gt("createDatetime",
						assignmentFilter.getCreateDate()));
			}
			if (assignmentFilter.getIssueDate() != null) {
				dCriteria.add(Restrictions.lt("createDatetime",
						assignmentFilter.getIssueDate()));
			}

			if (StringUtils.isNotBlank(assignmentFilter.getValidateInfoBca())) {
				if (assignmentFilter.getValidateInfoBca().equals("0")) {
					// Integer[] stage = { 0 , null };
					// dCriteria.add(Restrictions.in("validateInfoBca", stage));
					Criterion stage1 = Restrictions.eq("validateInfoBca", 0);
					Criterion stage2 = Restrictions.isNull("validateInfoBca");
					dCriteria.add(Restrictions.or(stage1, stage2));
					// dCriteria.add(Restrictions.eq("validateInfoBca",
					// Integer.parseInt(assignmentFilter.getValidateInfoBca())));
				} else if (assignmentFilter.getValidateInfoBca().equals("1")
						|| assignmentFilter.getValidateInfoBca().equals("2")) {
					dCriteria.add(Restrictions.eq("validateInfoBca", Integer
							.parseInt(assignmentFilter.getValidateInfoBca())));
				}
			}

			if ((StringUtils
					.isNotBlank(assignmentFilter.getTypeInvestigation()))) {
				// Kiểm tra trạng thái bản ghi
				// Nếu là RNO thì là lấy số biên nhận
				if (assignmentFilter.getTypeInvestigation().equals("RNO")) {
					dCriteria.add(Restrictions.isNotNull("receiptNo"));
				}

				// Nếu là BCA02 thì là đã lấy kết quả từ BCA
				if (assignmentFilter.getTypeInvestigation().equals("BCA02")) {
					dCriteria.add(Restrictions.eq("validateInfoBca", 2));
				}

				// Nếu là PER thì là đã đóng gói
				if (assignmentFilter.getTypeInvestigation().equals("PER")) {
					dCriteria.add(Restrictions.eq("persoRegisterStatus", "02"));
				}

				// Nếu là ISS thì là đã phát hành
				if (assignmentFilter.getTypeInvestigation().equals("ISS")) {
					dCriteria.add(Restrictions.eq(
							"nicTransaction.transactionStatus", "RIC_ISSUED"));
				}

				// Nếu là SYNC thì là đã Đồng bộ sang BCA
				if (assignmentFilter.getTypeInvestigation().equals("SYNC")) {
					dCriteria.add(Restrictions.eq(
							"nicTransaction.syncPassport", 1));
				}
			}
		}
		orders = Order.desc("workflowJobId");
		if (orders == null) {
			return this.findAllForPagination(dCriteria, pageNo, pageSize);
		} else {
			return this.findAllForPagination(dCriteria, pageNo, pageSize,
					orders);
		}
	}

	public PaginatedResult<NicUploadJob> findPendingPassportNo(
			String[] recordStatuses, String officerId, int pageNo,
			int pageSize, Boolean assignedOnly,
			AssignmentFilterAll assignmentFilter) {
		DetachedCriteria dCriteria = DetachedCriteria
				.forClass(getPersistentClass());
		dCriteria.createAlias("nicTransaction", "nicTransaction");
		Order orders = null;

		Boolean StageJobType = false;
		if (recordStatuses != null && recordStatuses.length > 0) {
			for (String st : recordStatuses) {
				if (st.equals("LOS")) {
					StageJobType = true;
					break;
				}
			}
			if (!StageJobType)
				dCriteria.add(Restrictions.in("investigationStatus",
						recordStatuses));
			else
				dCriteria.add(Restrictions.in("jobType", recordStatuses));
		}

		if (StringUtils.isNotBlank(officerId) && !StageJobType) {
			dCriteria.add(Restrictions.eq("investigationOfficerId", officerId));
		}
		if (assignedOnly != null) {
			if (assignedOnly && !StageJobType) {
				dCriteria.add(Restrictions.isNotNull("investigationOfficerId"));
			} else {
				dCriteria.add(Restrictions.isNull("investigationOfficerId"));
			}
		}

		dCriteria.add(Restrictions.or(Restrictions.isNull("tempPassportNo"),
				Restrictions.eq("tempPassportNo", "")));
		/*
		 * if (assignmentFilter != null) { if
		 * (StringUtils.isNotBlank(assignmentFilter.getTransactionId())) {
		 * dCriteria.add(Restrictions.eq("transactionId",
		 * assignmentFilter.getTransactionId())); } }
		 */

		if (assignmentFilter != null) {
			if (StringUtils.isNotBlank(assignmentFilter.getTransactionId())) {
				dCriteria.add(Restrictions.eq("transactionId",
						assignmentFilter.getTransactionId()));
			}
			if (assignmentFilter.getPriority() != null) {
				dCriteria.add(Restrictions.eq("nicTransaction.priority",
						assignmentFilter.getPriority()));
			}
			if (StringUtils.isNotBlank(assignmentFilter.getRegSiteCode())) {
				dCriteria.add(Restrictions.eq("nicTransaction.regSiteCode",
						assignmentFilter.getRegSiteCode()));
			}
			if (assignmentFilter.getCreateDate() != null) {
				dCriteria.add(Restrictions.ge("nicTransaction.createDatetime",
						assignmentFilter.getCreateDate()));

				Date maxDate = new Date(assignmentFilter.getCreateDate()
						.getTime() + TimeUnit.DAYS.toMillis(1));
				dCriteria.add(Restrictions.lt("nicTransaction.createDatetime",
						maxDate));
			}
			if (StringUtils.isNotBlank(assignmentFilter.getPassportType())) {
				dCriteria.add(Restrictions.eq("nicTransaction.passportType",
						assignmentFilter.getPassportType()));
			}

			if (StringUtils.isNotBlank(assignmentFilter.getTransactionType())) {
				dCriteria.add(Restrictions.eq("nicTransaction.transactionType",
						assignmentFilter.getTransactionType()));
			}

			if (assignmentFilter.getIssueDate() != null) {
				dCriteria.add(Restrictions.eq(
						"nicTransaction.estDateOfCollection",
						assignmentFilter.getIssueDate()));
			}

			if ((StringUtils
					.isNotBlank(assignmentFilter.getTypeInvestigation()))) {
				dCriteria.add(Restrictions.eq("investigationType",
						assignmentFilter.getTypeInvestigation()));
			}
		}
		orders = Order.asc("workflowJobId");
		if (orders == null) {
			return this.findAllForPagination(dCriteria, pageNo, pageSize);
		} else {
			return this.findAllForPagination(dCriteria, pageNo, pageSize,
					orders);
		}
	}

	public PaginatedResult<NicUploadJob> findAllStatusPerso(
			String[] recordStatuses, String officerId, int pageNo,
			int pageSize, Boolean assignedOnly,
			AssignmentFilterAll assignmentFilter) {
		DetachedCriteria dCriteria = DetachedCriteria
				.forClass(getPersistentClass());
		dCriteria.createAlias("nicTransaction", "nicTransaction");
		Order orders = null;

		Boolean StageJobType = false;
		if (recordStatuses != null && recordStatuses.length > 0) {
			for (String st : recordStatuses) {
				if (st.equals("LOS")) {
					StageJobType = true;
					break;
				}
			}
			if (!StageJobType)
				dCriteria.add(Restrictions.in("persoRegisterStatus",
						recordStatuses));
			else
				dCriteria.add(Restrictions.in("jobType", recordStatuses));
		}

		if (StringUtils.isNotBlank(officerId) && !StageJobType) {
			dCriteria.add(Restrictions.eq("investigationOfficerId", officerId));
		}
		if (assignedOnly != null) {
			if (assignedOnly && !StageJobType) {
				dCriteria.add(Restrictions.isNotNull("investigationOfficerId"));
			} else {
				dCriteria.add(Restrictions.isNull("investigationOfficerId"));
			}
		}
		/*
		 * if (assignmentFilter != null) { if
		 * (StringUtils.isNotBlank(assignmentFilter.getTransactionId())) {
		 * dCriteria.add(Restrictions.eq("transactionId",
		 * assignmentFilter.getTransactionId())); } }
		 */

		if (assignmentFilter != null) {
			if (StringUtils.isNotBlank(assignmentFilter.getTransactionId())) {
				dCriteria.add(Restrictions.eq("transactionId",
						assignmentFilter.getTransactionId()));
			}
			if (assignmentFilter.getPriority() != null) {
				dCriteria.add(Restrictions.eq("nicTransaction.priority",
						assignmentFilter.getPriority()));
			}
			if (StringUtils.isNotBlank(assignmentFilter.getRegSiteCode())) {
				dCriteria.add(Restrictions.eq("nicTransaction.regSiteCode",
						assignmentFilter.getRegSiteCode()));
			}
			if (assignmentFilter.getCreateDate() != null) {
				dCriteria.add(Restrictions.ge("nicTransaction.createDatetime",
						assignmentFilter.getCreateDate()));

				Date maxDate = new Date(assignmentFilter.getCreateDate()
						.getTime() + TimeUnit.DAYS.toMillis(1));
				dCriteria.add(Restrictions.lt("nicTransaction.createDatetime",
						maxDate));
			}
			if (StringUtils.isNotBlank(assignmentFilter.getPassportType())) {
				dCriteria.add(Restrictions.eq("nicTransaction.passportType",
						assignmentFilter.getPassportType()));
			}

			if (StringUtils.isNotBlank(assignmentFilter.getTransactionType())) {
				dCriteria.add(Restrictions.eq("nicTransaction.transactionType",
						assignmentFilter.getTransactionType()));
			}

			if (assignmentFilter.getIssueDate() != null) {
				dCriteria.add(Restrictions.eq(
						"nicTransaction.estDateOfCollection",
						assignmentFilter.getIssueDate()));
			}

			if ((StringUtils
					.isNotBlank(assignmentFilter.getTypeInvestigation()))) {
				dCriteria.add(Restrictions.eq("investigationType",
						assignmentFilter.getTypeInvestigation()));
			}
		}
		orders = Order.asc("workflowJobId");

		if (orders == null) {
			return this.findAllForPagination(dCriteria, pageNo, pageSize);
		} else {
			return this.findAllForPagination(dCriteria, pageNo, pageSize,
					orders);
		}
	}

	// TRUNG THÊM DANH SÁCH BÀN GIAO ĐỂ PHÊ DUYỆT
	public PaginatedResult<NicUploadJob> findAssignmentForPaginationByOfficerId(
			String[] recordStatuses, String officerId, int pageNo, int pageSize) {
		return this.findAssignmentForPaginationByOfficerId(recordStatuses,
				officerId, pageNo, pageSize, null);
	}

	public PaginatedResult<NicUploadJob> findAssignmentForPaginationByOfficerId(
			String[] recordStatuses, String officerId, int pageNo,
			int pageSize, Boolean assignedOnly) {
		return this.findAssignmentForPaginationByOfficerId(recordStatuses,
				officerId, pageNo, pageSize, assignedOnly, null);
	}

	public PaginatedResult<NicUploadJob> findAssignmentForPaginationByOfficerId(
			String[] recordStatuses, String officerId, int pageNo,
			int pageSize, Boolean assignedOnly,
			AssignmentFilter assignmentFilter) {
		DetachedCriteria dCriteria = DetachedCriteria
				.forClass(getPersistentClass());
		Order orders = null;
		if (StringUtils.isNotBlank(officerId)) {
			dCriteria.add(Restrictions.eq("investigationOfficerId", officerId));
		}
		if (recordStatuses != null && recordStatuses.length > 0) {
			dCriteria.add(Restrictions
					.in("investigationStatus", recordStatuses));
		}
		if (assignedOnly != null) {
			if (assignedOnly) {
				dCriteria.add(Restrictions.isNotNull("investigationOfficerId"));
			} else {
				dCriteria.add(Restrictions.isNull("investigationOfficerId"));
			}
		}
		if (assignmentFilter != null) {
			if (StringUtils.isNotBlank(assignmentFilter.getTransactionId())) {
				dCriteria.add(Restrictions.eq("transactionId",
						assignmentFilter.getTransactionId()));
			}
		}
		dCriteria.add(Restrictions.eq("jobApproveStatus", null));
		orders = Order.asc("workflowJobId");
		if (orders == null) {
			return this.findAllForPagination(dCriteria, pageNo, pageSize);
		} else {
			return this.findAllForPagination(dCriteria, pageNo, pageSize,
					orders);
		}
	}

	public PaginatedResult<NicUploadJob> findAssignmentForPaginationByOfficerId(
			String officerId, int pageNo, int pageSize) {
		return this.findAssignmentForPaginationByOfficerId(officerId, pageNo,
				pageSize, null);
	}

	public PaginatedResult<NicUploadJob> findAssignmentForPaginationByOfficerId(
			String officerId, int pageNo, int pageSize, Boolean assignedOnly) {
		return this.findAssignmentForPaginationByOfficerId(officerId, pageNo,
				pageSize, assignedOnly, null);
	}

	/**
	 * To return the PaginatedResult<NicUploadJob> for those job waiting for
	 * investigation. It will filter by the officer Id passed in.
	 * 
	 * @param officerId
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	// DAnh sách tạo ở đây
	public PaginatedResult<NicUploadJob> findAssignmentForPaginationByOfficerId(
			String officerId, int pageNo, int pageSize, Boolean assignedOnly,
			AssignmentFilter assignmentFilter) {
		DetachedCriteria dCriteria = DetachedCriteria
				.forClass(getPersistentClass());
		Order orders = null;
		// if (StringUtils.isNotBlank(officerId)) {
		// dCriteria.add(Restrictions.eq("investigationOfficerId",
		// officerId));
		// }
		// dCriteria.add(Restrictions.not(Restrictions.in(
		// "investigationStatus", new String[] {
		// RECORD_STATUS_COMPLETED,
		// RECORD_STATUS_REJECTED,
		// RECORD_STATUS_CANCELLED,
		// RECORD_STATUS_SUSPENDED})));
		// if (assignedOnly != null) {
		// if (assignedOnly) {
		// dCriteria.add(Restrictions.isNotNull("investigationOfficerId"));
		// } else {
		// dCriteria.add(Restrictions.isNull("investigationOfficerId"));
		// }
		// }
		// if (assignmentFilter != null) {
		// if (StringUtils.isNotBlank(assignmentFilter.getTransactionId())) {
		// dCriteria.add(Restrictions.eq("transactionId",
		// assignmentFilter.getTransactionId()));
		// }
		// }
		dCriteria.add(Restrictions.eq("jobApproveStatus", null));
		orders = Order.asc("workflowJobId");
		if (orders == null) {
			return this.findAllForPagination(dCriteria, pageNo, pageSize);
		} else {
			return this.findAllForPagination(dCriteria, pageNo, pageSize,
					orders);
		}
	}

	// END
	@Override
	public void suspendJobId(long jobId, String userId, String wkstnId)
			throws Exception {
		try {
			DetachedCriteria criteria = DetachedCriteria
					.forClass(NicUploadJob.class);
			criteria.add(Restrictions.eq("workflowJobId", jobId));
			List<NicUploadJob> list = (List<NicUploadJob>) getHibernateTemplate()
					.findByCriteria(criteria);
			if (CollectionUtils.isNotEmpty(list)) {
				NicUploadJob updateObj = list.get(0);
				updateObj.setInvestigationStatus(RECORD_STATUS_SUSPENDED);
				updateObj.setUpdateBy(userId);
				updateObj.setUpdateWkstnId(wkstnId);
				updateObj.setUpdateDatetime(new Date());
				updateObj.setInvestigationOfficerId(null);
				getHibernateTemplate().update(updateObj);
			}
		} catch (Exception e) {
			logger.error("======== Exception while updating data ", e);
			// e.printStackTrace();
			throw e;
		}
	}

	// ----> END
	@Override
	public NicUploadJob assignNewSuspendedJob(String officerId)
			throws Exception {
		logger.debug("Inside Dao Impl==========>>>>>>Update Officer Id whose values are null"
				+ officerId);

		// search here with sort and 1 returned
		Long uploadJobId = null;
		try {
			{
				Session session = null;
				try {
					session = getHibernateTemplate().getSessionFactory()
							.openSession();
					Query query = session
							.getNamedQuery("epp.NicUploadJobDaoImpl.assignNewJobBasedOnStatus");
					query.setString("investigationStatus",
							RECORD_STATUS_SUSPENDED);

					List<Object> results = query.list();

					if (results == null || results.size() == 0) {
					} else {
						uploadJobId = (Long) results.get(0);
					}
				} finally {
					session.close();
				}
			}

			if (uploadJobId == null) {
				throw new Exception("No Job Found.");
			}

			NicUploadJob updateobject = this.findById(uploadJobId);

			updateobject.setInvestigationOfficerId(officerId);
			updateobject
					.setJobCurrentStage(NicTransactionLog.TRANSACTION_STAGE_FRAUD_CASE_MANAGEMENT);
			updateobject.setUpdateBy(officerId);
			updateobject.setUpdateDatetime(new Date());
			updateobject.setUpdateWkstnId("SYSTEM");

			getHibernateTemplate().update(updateobject);

			return updateobject;
		} catch (Exception e) {
			logger.error("======== Exception while updating data ", e);
			throw e;
		}
	}

	private List<NicUploadJob> sortByReleaseDatePriority(List<NicUploadJob> list) {

		if (CollectionUtils.isEmpty(list)) {
			return list;
		}

		for (NicUploadJob record : list) {
			String transactionId = record.getTransactionId();
			if (transactionId != null) {
				try {
					NicTransaction nicTransaction = this.nicTransactionService
							.findById(transactionId);
					if (nicTransaction != null) {
						record.getNicTransaction().setEstDateOfCollection(
								nicTransaction.getEstDateOfCollection());
						record.getNicTransaction().setPriority(
								nicTransaction.getPriority());
					}
				} catch (Exception e) {
				}
			}
		}

		boolean switchDone = true;
		while (switchDone) {
			switchDone = false;
			for (int i = 0; i <= list.size() - 2; i++) {
				NicUploadJob job1 = list.get(i);
				NicUploadJob job2 = list.get(i + 1);
				/*
				 * if (job1.eq_byReleaseDate(job2) && job1.eq_byPriority(job2))
				 * { } else if (job1.lt_byReleaseDate(job2) ||
				 * (job1.eq_byReleaseDate(job2) && job1 .gt_byPriority(job2))) {
				 * } else { list.set(i, job2); list.set(i + 1, job1); switchDone
				 * = true; }
				 */
			}
		}

		return list;
	}

	@Override
	public List<String> getAllSearchTypes(long jobId, String mainTransactionId,
			String hitTransactionId) throws Exception {

		// GET ALL SEARCH RESULTS USING jobId
		List<NicSearchResult> results = this
				.getAllSearchResult__1_1_Y__1_N_N__excluded(jobId);
		// FOR EACH SEARCH RESULT
		for (int i = results.size() - 1; i >= 0; i--) {
			// GET ALL SERACHHITRESULTS USING SEARCHRESULTID
			// List<NicSearchHitResult> hitResults =
			// this.getAllSearchHitResult(results.get(i).getSearchResultId());
			// IF DOES NOT INCLUDE HITTRANSID, REMOVE FROM LIST
			boolean inTheList = false;
			for (NicSearchHitResult hitResult : results.get(i).getHitList()) {
				if (hitResult.getTransactionIdHit().equals(hitTransactionId)) {
					inTheList = true;
					break;
				}
			}
			if (!inTheList) {
				results.remove(i);
			}
		}

		List<String> searchTypeCodes = new ArrayList<String>();
		for (NicSearchResult result : results) {
			searchTypeCodes.add(result.getTypeSearch());
		}

		return searchTypeCodes;
	}

	/*
	 * @Override public List<NicSearchHitResult> getAllSearchHitResult(long
	 * jobId, String mainTransactionId,String hitTransactionId) throws Exception
	 * { DetachedCriteria detachedCriteria =
	 * DetachedCriteria.forClass(NicSearchHitResult.class);
	 * 
	 * detachedCriteria.add(Restrictions.eq("searchResult.workflowJobId",
	 * jobId));
	 * detachedCriteria.add(Restrictions.eq("searchResult.transactionId",
	 * mainTransactionId));
	 * detachedCriteria.add(Restrictions.eq("transactionIdHit",
	 * hitTransactionId));
	 * 
	 * List<NicSearchHitResult> list = (List<NicSearchHitResult>)
	 * getHibernateTemplate() .findByCriteria(detachedCriteria);
	 * 
	 * if (CollectionUtils.isEmpty(list)) { return new
	 * ArrayList<NicSearchHitResult>(); }
	 * 
	 * return list; }
	 * 
	 * @Override public List<NicSearchResult> getAllSearchResult(long jobId,
	 * String mainTransactionId, List<Long> searchResultIds) throws Exception {
	 * DetachedCriteria detachedCriteria =
	 * DetachedCriteria.forClass(NicSearchResult.class);
	 * 
	 * detachedCriteria.add(Restrictions.in("searchResultId", searchResultIds));
	 * detachedCriteria.add(Restrictions.eq("workflowJobId", jobId));
	 * detachedCriteria.add(Restrictions.eq("transactionId",
	 * mainTransactionId));
	 * 
	 * List<NicSearchResult> list = (List<NicSearchResult>)
	 * getHibernateTemplate().findByCriteria(detachedCriteria);
	 * 
	 * if (CollectionUtils.isEmpty(list)) { return new
	 * ArrayList<NicSearchResult>(); }
	 * 
	 * return list; }
	 */

	@Override
	public List<NicSearchResult> getAllSearchResult(Long jobId)
			throws Exception {
		DetachedCriteria detachedCriteria = DetachedCriteria
				.forClass(NicSearchResult.class);

		detachedCriteria.add(Restrictions.eq("workflowJobId", jobId));

		List<NicSearchResult> list = (List<NicSearchResult>) getHibernateTemplate()
				.findByCriteria(detachedCriteria);

		if (CollectionUtils.isEmpty(list)) {
			return new ArrayList<NicSearchResult>();
		}

		return list;
	}

	@Override
	public List<NicSearchHitResult> getAllSearchHitResult(
			List<NicSearchResult> results) throws Exception {

		List<Long> searchResultIds = new ArrayList<Long>();
		for (NicSearchResult result : results) {
			searchResultIds.add(result.getSearchResultId());
		}

		DetachedCriteria detachedCriteria = DetachedCriteria
				.forClass(NicSearchHitResult.class);

		detachedCriteria
				.add(Restrictions.in("searchResultId", searchResultIds));

		List<NicSearchHitResult> list = (List<NicSearchHitResult>) getHibernateTemplate()
				.findByCriteria(detachedCriteria);

		if (CollectionUtils.isEmpty(list)) {
			return new ArrayList<NicSearchHitResult>();
		}

		return list;
	}

	@Override
	public List<NicSearchHitResult> getAllSearchHitResult(long searchResultId)
			throws Exception {
		DetachedCriteria detachedCriteria = DetachedCriteria
				.forClass(NicSearchHitResult.class);

		detachedCriteria.add(Restrictions.eq("searchResultId", searchResultId));

		List<NicSearchHitResult> list = (List<NicSearchHitResult>) getHibernateTemplate()
				.findByCriteria(detachedCriteria);

		if (CollectionUtils.isEmpty(list)) {
			return new ArrayList<NicSearchHitResult>();
		}

		return list;
	}

	@Override
	public List<NicSearchHitResult> getAllSearchHitResult(long searchResultId,
			String transactionIdHit) throws Exception {
		DetachedCriteria detachedCriteria = DetachedCriteria
				.forClass(NicSearchHitResult.class);

		detachedCriteria.add(Restrictions.eq("searchResultId", searchResultId));

		if (StringUtils.isNotBlank(transactionIdHit)) {
			detachedCriteria.add(Restrictions.eq("transactionIdHit",
					transactionIdHit));
		}

		List<NicSearchHitResult> list = (List<NicSearchHitResult>) getHibernateTemplate()
				.findByCriteria(detachedCriteria);

		if (CollectionUtils.isEmpty(list)) {
			return new ArrayList<NicSearchHitResult>();
		}

		return list;
	}

	@Override
	public PaginatedResult<NicUploadJob> findTransactions(String txnId,
			String owner, List<String> statuses) throws Exception {

		DetachedCriteria criteria = DetachedCriteria
				.forClass(NicUploadJob.class);

		if (StringUtils.isNotBlank(txnId)) {
			criteria.add(Restrictions.eq("transactionId", txnId));
		}
		if (StringUtils.isNotBlank(owner)) {
			criteria.add(Restrictions.eq("investigationOfficerId", owner));
		}
		if (CollectionUtils.isNotEmpty(statuses)) {
			criteria.add(Restrictions.in("investigationStatus", statuses));
		}

		PaginatedResult<NicUploadJob> result = findAllForPagination(criteria,
				1, 10);

		return result;
	}

	@Override
	public List<NicSearchHitResult> getAllSearchHitResult(Long jobId,
			String hitTransactionId, List<String> searchTypes) throws Exception {

		List<NicSearchHitResult> allHitResults = new ArrayList<NicSearchHitResult>();
		for (NicSearchResult result : this.getAllSearchResult(jobId,
				searchTypes)) {
			for (NicSearchHitResult hitResult : this
					.getAllSearchHitResult(result.getSearchResultId())) {
				if (hitResult.getTransactionIdHit().equals(hitTransactionId)) {
					allHitResults.add(hitResult);
				}
			}
		}

		return allHitResults;
	}

	@Override
	public List<NicSearchResult> getAllSearchResult(Long jobId,
			List<String> searchTypes) throws Exception {
		DetachedCriteria detachedCriteria = DetachedCriteria
				.forClass(NicSearchResult.class);

		detachedCriteria.add(Restrictions.eq("workflowJobId", jobId));
		detachedCriteria.add(Restrictions.in("typeSearch", searchTypes));

		List<NicSearchResult> list = (List<NicSearchResult>) getHibernateTemplate()
				.findByCriteria(detachedCriteria);

		if (CollectionUtils.isEmpty(list)) {
			return new ArrayList<NicSearchResult>();
		}

		return list;
	}

	private void logTransaction(String referenceId, String stage,
			String status, String officerUserId, String officerWorkstationId,
			String reason, String remarks) {
		{
			NicTransactionLog nicTransactionLog = new NicTransactionLog();
			nicTransactionLog.setRefId(referenceId);
			nicTransactionLog.setTransactionStage(stage);
			nicTransactionLog.setTransactionStatus(status);
			nicTransactionLog.setOfficerId(officerUserId);
			nicTransactionLog.setWkstnId(officerWorkstationId);
			nicTransactionLog.setLogCreateTime(new Date());
			String remarksDataMarshal = null;
			{
				LogInfoDTO logInfoDto = new LogInfoDTO();
				logInfoDto.setReason(reason);
				logInfoDto.setRemark(remarks);
				try {
					remarksDataMarshal = logUtil.marshal(logInfoDto);
				} catch (JaxbXmlConvertorException e) {
					e.printStackTrace();
				}
			}
			nicTransactionLog.setLogInfo(remarksDataMarshal);
			this.transactionLogDao.save(nicTransactionLog);
		}
	}

	@Override
	public void unassignInvestigationJob(long jobId, String officerUserId,
			String officerWorkstationId) throws Exception {
		try {
			DetachedCriteria criteria = DetachedCriteria
					.forClass(NicUploadJob.class);
			criteria.add(Restrictions.eq("workflowJobId", jobId));
			criteria.add(Restrictions.eq("investigationStatus",
					NicUploadJob.RECORD_STATUS_IN_PROGRESS));
			criteria.add(Restrictions.isNotNull("investigationOfficerId"));
			List<NicUploadJob> list = (List<NicUploadJob>) getHibernateTemplate()
					.findByCriteria(criteria);
			if (CollectionUtils.isNotEmpty(list)) {
				NicUploadJob updateObj = list.get(0);

				this.logTransaction(
						updateObj.getTransactionId(),
						NicTransactionLog.TRANSACTION_STAGE_INVESTIGATION_UNASSIGN,
						this.codesService.getCodeValueDescByIdName(
								Codes.INVESTIGATION_STATUS,
								updateObj.getInvestigationStatus(),
								updateObj.getInvestigationStatus()),
						officerUserId,
						officerWorkstationId,
						NicTransactionLog.TRANSACTION_STAGE_INVESTIGATION_UNASSIGN,
						updateObj.getWorkflowJobId().toString());

				updateObj
						.setInvestigationStatus(NicUploadJobDaoImpl.RECORD_STATUS_INITIAL);
				updateObj.setInvestigationOfficerId(null);
				updateObj.setUpdateBy(officerUserId);
				updateObj.setUpdateWkstnId(officerWorkstationId);
				updateObj.setUpdateDatetime(new Date());
				getHibernateTemplate().update(updateObj);
			}
		} catch (Exception e) {
			logger.error("======== Exception while updating data ", e);
			throw e;
		}
	}

	@Override
	public void unassignInvestigationAllJobsForUser(String userId,
			String officerUserId, String officerWorkstationId) throws Exception {
		try {
			DetachedCriteria criteria = DetachedCriteria
					.forClass(NicUploadJob.class);
			criteria.add(Restrictions.eq("investigationOfficerId", userId));
			criteria.add(Restrictions.eq("investigationStatus",
					NicUploadJobDaoImpl.RECORD_STATUS_IN_PROGRESS));
			List<NicUploadJob> list = (List<NicUploadJob>) getHibernateTemplate()
					.findByCriteria(criteria);
			if (CollectionUtils.isNotEmpty(list)) {
				for (NicUploadJob updateObj : list) {

					this.logTransaction(
							updateObj.getTransactionId(),
							NicTransactionLog.TRANSACTION_STAGE_INVESTIGATION_UNASSIGN,
							this.codesService.getCodeValueDescByIdName(
									Codes.INVESTIGATION_STATUS,
									updateObj.getInvestigationStatus(),
									updateObj.getInvestigationStatus()),
							officerUserId,
							officerWorkstationId,
							NicTransactionLog.TRANSACTION_STAGE_INVESTIGATION_UNASSIGN,
							updateObj.getWorkflowJobId().toString());

					updateObj.setInvestigationOfficerId(null);
					updateObj
							.setInvestigationStatus(NicUploadJobDaoImpl.RECORD_STATUS_INITIAL);
					updateObj.setUpdateBy(officerUserId);
					updateObj.setUpdateWkstnId(officerWorkstationId);
					updateObj.setUpdateDatetime(new Date());
					getHibernateTemplate().update(updateObj);
				}
			}
		} catch (Exception e) {
			logger.error("======== Exception while updating data ", e);
			throw e;
		}
	}

	@Override
	public void unassignSuspendedInvestigationJob(long jobId,
			String officerUserId, String officerWorkstationId) throws Exception {
		try {
			DetachedCriteria criteria = DetachedCriteria
					.forClass(NicUploadJob.class);
			criteria.add(Restrictions.eq("workflowJobId", jobId));
			criteria.add(Restrictions.eq("investigationStatus",
					NicUploadJob.RECORD_STATUS_SUSPENDED));
			criteria.add(Restrictions.isNotNull("investigationOfficerId"));
			List<NicUploadJob> list = (List<NicUploadJob>) getHibernateTemplate()
					.findByCriteria(criteria);
			if (CollectionUtils.isNotEmpty(list)) {
				NicUploadJob updateObj = list.get(0);

				this.logTransaction(
						updateObj.getTransactionId(),
						NicTransactionLog.TRANSACTION_STAGE_FRAUD_CASE_MANAGEMENT_UNASSIGN,
						this.codesService.getCodeValueDescByIdName(
								Codes.INVESTIGATION_STATUS,
								updateObj.getInvestigationStatus(),
								updateObj.getInvestigationStatus()),
						officerUserId,
						officerWorkstationId,
						NicTransactionLog.TRANSACTION_STAGE_FRAUD_CASE_MANAGEMENT_UNASSIGN,
						updateObj.getWorkflowJobId().toString());

				updateObj.setInvestigationOfficerId(null);
				updateObj
						.setJobCurrentStage(NicTransactionLog.TRANSACTION_STAGE_INVESTIGATION);
				updateObj.setUpdateBy(officerUserId);
				updateObj.setUpdateWkstnId(officerWorkstationId);
				updateObj.setUpdateDatetime(new Date());
				getHibernateTemplate().update(updateObj);
			}
		} catch (Exception e) {
			logger.error("======== Exception while updating data ", e);
			throw e;
		}
	}

	@Override
	public void unassignSuspendedInvestigationAllJobsForUser(String userId,
			String officerUserId, String officerWorkstationId) throws Exception {
		try {
			DetachedCriteria criteria = DetachedCriteria
					.forClass(NicUploadJob.class);
			criteria.add(Restrictions.eq("investigationOfficerId", userId));
			criteria.add(Restrictions.eq("investigationStatus",
					NicUploadJobDaoImpl.RECORD_STATUS_SUSPENDED));
			List<NicUploadJob> list = (List<NicUploadJob>) getHibernateTemplate()
					.findByCriteria(criteria);
			if (CollectionUtils.isNotEmpty(list)) {
				for (NicUploadJob updateObj : list) {

					this.logTransaction(
							updateObj.getTransactionId(),
							NicTransactionLog.TRANSACTION_STAGE_FRAUD_CASE_MANAGEMENT_UNASSIGN,
							this.codesService.getCodeValueDescByIdName(
									Codes.INVESTIGATION_STATUS,
									updateObj.getInvestigationStatus(),
									updateObj.getInvestigationStatus()),
							officerUserId,
							officerWorkstationId,
							NicTransactionLog.TRANSACTION_STAGE_FRAUD_CASE_MANAGEMENT_UNASSIGN,
							updateObj.getWorkflowJobId().toString());

					updateObj.setInvestigationOfficerId(null);
					updateObj
							.setJobCurrentStage(NicTransactionLog.TRANSACTION_STAGE_INVESTIGATION);
					updateObj.setUpdateBy(officerUserId);
					updateObj.setUpdateWkstnId(officerWorkstationId);
					updateObj.setUpdateDatetime(new Date());
					getHibernateTemplate().update(updateObj);
				}
			}
		} catch (Exception e) {
			logger.error("======== Exception while updating data ", e);
			throw e;
		}
	}

	/*
	 * @Override public PaginatedResult<NicUploadJob> findAllForPagination(
	 * NicUploadJob nicUploadJob, NicTransaction nicTransaction, String[]
	 * investStatus, Long jobId, boolean isShowErrTrans, Date dateFrom, Date
	 * dateTo, int pageNo, int pageSize, boolean ignoreCase, boolean enableLike,
	 * Order order) { DetachedCriteria dCrit =
	 * DetachedCriteria.forClass(NicUploadJob.class);
	 * dCrit.add(Example.create(nicUploadJob));
	 * 
	 * if (jobId != null) { dCrit.add(Restrictions.eq("workflowJobId", jobId));
	 * } List<String> transIds = null; if
	 * (StringUtils.isBlank(nicUploadJob.getTransactionId()) && jobId == null &&
	 * nicTransaction != null) {
	 * 
	 * DetachedCriteria tCrit = DetachedCriteria.forClass(NicTransaction.class);
	 * 
	 * if (StringUtils.isNotBlank(nicTransaction.getTransactionStatus())) {
	 * tCrit.add(Restrictions.eq("transactionStatus",
	 * nicTransaction.getTransactionStatus())); }
	 * 
	 * if (StringUtils.isNotBlank(nicTransaction.getRegSiteCode())) {
	 * tCrit.add(Restrictions.eq("regSiteCode",
	 * nicTransaction.getRegSiteCode())); }
	 * 
	 * if (StringUtils.isNotBlank(nicTransaction.getIssSiteCode())) {
	 * tCrit.add(Restrictions.eq("issSiteCode",
	 * nicTransaction.getIssSiteCode())); }
	 * 
	 * tCrit.setProjection(Projections.property("transactionId")); transIds =
	 * (List<String>) getHibernateTemplate().findByCriteria(tCrit); if
	 * (CollectionUtils.isEmpty(transIds)) { return new
	 * PaginatedResult<NicUploadJob>(0, 1, new ArrayList<NicUploadJob>()); } }
	 * 
	 * if (CollectionUtils.isNotEmpty(transIds)) { //
	 * dCrit.add(Restrictions.in("transactionId", transIds));
	 * 
	 * Conjunction conjunction = Restrictions.conjunction(); if (transIds.size()
	 * > 0) { Disjunction disjunctionIdListCount = Restrictions.disjunction();
	 * 
	 * for (int i = 0; i < (((transIds.size() / 1000) + 1)); i++) { Criterion
	 * list = null; if (transIds.size() > ((i + 1) * 1000)) { list =
	 * Restrictions.in("transactionId", Arrays.copyOfRange(transIds.toArray(),
	 * (i * 1000), ((i + 1) * 1000))); } else { list =
	 * Restrictions.in("transactionId", Arrays.copyOfRange(transIds.toArray(),
	 * (i * 1000), transIds.size())); }
	 * 
	 * disjunctionIdListCount.add(list); }
	 * 
	 * conjunction.add(disjunctionIdListCount); } dCrit.add(conjunction); }
	 * 
	 * if (!isShowErrTrans) { if (investStatus != null && investStatus.length >
	 * 0) { dCrit.add(Restrictions.in("investigationStatus", investStatus)); } }
	 * else { //String[] errorCodes
	 * ={"SYNC_CARD_CPD_ERROR","AFIS_REGISTER_ERROR"
	 * ,"SYNC_TX_CPD_ERROR","CPD_CHECK_ERROR"
	 * ,"INVENTORY_SYNC_ERROR","AFIS_CHECK_ERROR"}; List<String>
	 * persoErrorTransIds = null;
	 * 
	 * DetachedCriteria tCrit = DetachedCriteria.forClass(NicTransaction.class);
	 * tCrit.add(Restrictions.eq("transactionStatus", "PERSO_ERROR"));
	 * tCrit.setProjection(Projections.property("transactionId"));
	 * persoErrorTransIds = (List<String>)
	 * getHibernateTemplate().findByCriteria(tCrit); Criterion
	 * jobCurrentStateCriterion = Restrictions.like("jobCurrentStatus",
	 * "%_ERROR"); //Restrictions.and(Restrictions.like("jobCurrentStatus",
	 * "%_ERROR"),Restrictions.eq("nicJobErrorFlag", true)); Criterion
	 * personErrorCritetion = null; if
	 * (CollectionUtils.isNotEmpty(persoErrorTransIds)) { //
	 * personErrorCritetion = Restrictions.in("transactionId",
	 * persoErrorTransIds);
	 * 
	 * Conjunction conjunction = Restrictions.conjunction(); if
	 * (persoErrorTransIds.size() > 0) { Disjunction disjunctionIdListCount =
	 * Restrictions.disjunction();
	 * 
	 * for (int i = 0; i < (((persoErrorTransIds.size() / 1000) + 1)); i++) {
	 * Criterion list = null; if (persoErrorTransIds.size() > ((i + 1) * 1000))
	 * { list = Restrictions.in("transactionId",
	 * Arrays.copyOfRange(persoErrorTransIds.toArray(), (i * 1000), ((i + 1) *
	 * 1000))); } else { list = Restrictions.in("transactionId",
	 * Arrays.copyOfRange(persoErrorTransIds.toArray(), (i * 1000),
	 * persoErrorTransIds.size())); }
	 * 
	 * disjunctionIdListCount.add(list); }
	 * 
	 * conjunction.add(disjunctionIdListCount); } personErrorCritetion =
	 * (conjunction); }
	 * 
	 * if (personErrorCritetion != null) {
	 * dCrit.add(Restrictions.or(jobCurrentStateCriterion,
	 * personErrorCritetion)); } else { dCrit.add(jobCurrentStateCriterion); }
	 * 
	 * } return findAllForPagination(dCrit, pageNo, pageSize, order); }
	 */

	@Override
	public PaginatedResult<NicUploadJob> findAllForPagination(
			NicUploadJob nicUploadJob, NicTransaction nicTransaction,
			String[] investStatus, Long jobId, boolean isShowErrTrans,
			Date dateFrom, Date dateTo, int pageNo, int pageSize,
			boolean ignoreCase, boolean enableLike, Order order) {
		DetachedCriteria dCrit = DetachedCriteria.forClass(NicUploadJob.class);
		dCrit.createAlias("nicTransaction", "nicTransaction");

		dCrit.add(Example.create(nicUploadJob));

		if (jobId != null) {
			dCrit.add(Restrictions.eq("workflowJobId", jobId));
		}

		if (StringUtils.isNotBlank(nicTransaction.getTransactionStatus())) {
			dCrit.add(Restrictions.eq("nicTransaction.transactionStatus",
					nicTransaction.getTransactionStatus()));
		}

		if (StringUtils.isNotBlank(nicTransaction.getRegSiteCode())) {
			dCrit.add(Restrictions.eq("nicTransaction.regSiteCode",
					nicTransaction.getRegSiteCode()));
		}

		if (StringUtils.isNotBlank(nicTransaction.getIssSiteCode())) {
			dCrit.add(Restrictions.eq("nicTransaction.issSiteCode",
					nicTransaction.getIssSiteCode()));
		}

		if (dateFrom != null && dateTo != null) {
			dCrit.add(Restrictions.between("jobCreatedTime",
					DateUtil.getStartOfDay(dateFrom),
					DateUtil.getEndOfDay(dateTo)));
		} else if (dateFrom != null) {
			dCrit.add(Restrictions.gt("jobCreatedTime",
					DateUtil.getStartOfDay(dateFrom)));
		} else if (dateTo != null) {
			dCrit.add(Restrictions.lt("jobCreatedTime",
					DateUtil.getEndOfDay(dateTo)));
		}

		if (isShowErrTrans) {
			Criterion jobCurrentStateCriterion = Restrictions.like(
					"jobCurrentStatus", "%_ERROR");
			Criterion persoErrorCritetion = Restrictions.eq(
					"nicTransaction.transactionStatus", "PERSO_ERROR");
			dCrit.add(Restrictions.or(jobCurrentStateCriterion,
					persoErrorCritetion));
		} else {

			if (investStatus != null && investStatus.length > 0) {
				dCrit.add(Restrictions.in("investigationStatus", investStatus));
			}
		}
		return findAllForPagination(dCrit, pageNo, pageSize, order);
	}

	@Override
	public List<NicSearchResult> getAllSearchResult__1_1_Y__1_N_N__excluded(
			Long jobId) throws Exception {
		DetachedCriteria detachedCriteria = DetachedCriteria
				.forClass(NicSearchResult.class);

		detachedCriteria.add(Restrictions.eq("workflowJobId", jobId));

		List<NicSearchResult> list = (List<NicSearchResult>) getHibernateTemplate()
				.findByCriteria(detachedCriteria);

		if (list == null) {
			list = new ArrayList<NicSearchResult>();
		}

		if (CollectionUtils.isEmpty(list)) {
			return list;
		}

		this.removeNicSearchResult_typeSearchBlank(list);

		this.processAllNicSearchResult(list);

		return list;
	}

	public void processAllNicSearchResult(List<NicSearchResult> list) {
		for (int i = list.size() - 1; i >= 0; i--) {
			this.process_1NicSearchResult(list, i);
		}
	}

	public void process_1NicSearchResult(List<NicSearchResult> list, int i) {

		NicSearchResult nicSearchResult = list.get(i);

		String searchType = list.get(i).getTypeSearch();

		if (!(searchType.equalsIgnoreCase(NicSearchResult.TYPE_SEARCH__FP_1_1) || searchType
				.equalsIgnoreCase(NicSearchResult.TYPE_SEARCH__FP_1_N))) {
			return;
		}

		if (searchType.equalsIgnoreCase(NicSearchResult.TYPE_SEARCH__FP_1_1)) {
			this.process_TYPE_SEARCH__FP_1_1(list, i, nicSearchResult);
			return;
		}

		if (searchType.equalsIgnoreCase(NicSearchResult.TYPE_SEARCH__FP_1_N)) {
			this.process_TYPE_SEARCH__FP_1_N(list, i, nicSearchResult);
			return;
		}
	}

	public void removeNicSearchResult_typeSearchBlank(List<NicSearchResult> list) {
		for (int i = list.size() - 1; i >= 0; i--) {
			if (StringUtils.isBlank(list.get(i).getTypeSearch())) {
				list.remove(i);
			}
		}
	}

	public void process_TYPE_SEARCH__FP_1_N(List<NicSearchResult> list, int i,
			NicSearchResult nicSearchResult) {
		this.removeHitFromHitList(nicSearchResult, "N", true);
		this.removeSearchResultWithEmptyHitList(list, i, nicSearchResult);
	}

	public void process_TYPE_SEARCH__FP_1_1(List<NicSearchResult> list, int i,
			NicSearchResult nicSearchResult) {
		this.removeHitFromHitList(nicSearchResult, "Y", false);
		this.removeSearchResultWithEmptyHitList(list, i, nicSearchResult);
	}

	public void removeSearchResultWithEmptyHitList(List<NicSearchResult> list,
			int i, NicSearchResult nicSearchResult) {
		if (CollectionUtils.isEmpty(nicSearchResult.getHitList())) {
			list.remove(i);
		}
	}

	public void removeHitFromHitList(NicSearchResult nicSearchResult,
			String removeIfHitDecisionIsThisValue, boolean removeNullToo) {

		if (CollectionUtils.isEmpty(nicSearchResult.getHitList())) {
			return;
		}

		List<NicSearchHitResult> hitList = new ArrayList<NicSearchHitResult>();
		hitList.addAll(nicSearchResult.getHitList());

		this.doRemoveHitDecision(removeIfHitDecisionIsThisValue, removeNullToo,
				hitList);

		Set<NicSearchHitResult> hitListSet = new HashSet<NicSearchHitResult>();
		hitListSet.addAll(hitList);
		nicSearchResult.setHitList(hitListSet);
	}

	public void doRemoveHitDecision(String removeIfHitDecisionIsThisValue,
			boolean removeNullToo, List<NicSearchHitResult> hitList) {

		this.removeSearchHitResultNull(hitList);

		if (removeNullToo) {
			this.removeHitDecisionNull(hitList);
		}

		this.removeSearchHitResults(removeIfHitDecisionIsThisValue, hitList);
	}

	public void removeSearchHitResultNull(List<NicSearchHitResult> hitList) {

		for (int i = hitList.size() - 1; i >= 0; i--) {
			if (hitList.get(i) == null) {
				hitList.remove(i);
			}
		}
	}

	public void removeHitDecisionNull(List<NicSearchHitResult> hitList) {

		for (int i = hitList.size() - 1; i >= 0; i--) {
			NicSearchHitResult hit = hitList.get(i);
			if (hit.getHitDecision() == null) {
				hitList.remove(i);
			}
		}
	}

	public void removeSearchHitResults(String removeIfHitDecisionIsThisValue,
			List<NicSearchHitResult> hitList) {

		for (int i = hitList.size() - 1; i >= 0; i--) {
			NicSearchHitResult hit = hitList.get(i);
			if (!(hit.getHitDecision() ^ (removeIfHitDecisionIsThisValue
					.equals("Y")))) {
				hitList.remove(i);
			}
		}
	}

	@Override
	public void unassignInvestigationJobs(List<Long> jobIds,
			String officerUserId, String officerWorkstationId) throws Exception {

		if (CollectionUtils.isEmpty(jobIds)) {
			return;
		}

		try {
			DetachedCriteria criteria = DetachedCriteria
					.forClass(NicUploadJob.class);
			criteria.add(Restrictions.in("workflowJobId", jobIds));
			criteria.add(Restrictions.eq("investigationStatus",
					NicUploadJob.RECORD_STATUS_IN_PROGRESS));
			criteria.add(Restrictions.isNotNull("investigationOfficerId"));
			List<NicUploadJob> list = (List<NicUploadJob>) getHibernateTemplate()
					.findByCriteria(criteria);
			if (CollectionUtils.isNotEmpty(list)) {
				for (NicUploadJob updateObj : list) {
					this.logTransaction(
							updateObj.getTransactionId(),
							NicTransactionLog.TRANSACTION_STAGE_INVESTIGATION_UNASSIGN,
							this.codesService.getCodeValueDescByIdName(
									Codes.INVESTIGATION_STATUS,
									updateObj.getInvestigationStatus(),
									updateObj.getInvestigationStatus()),
							officerUserId,
							officerWorkstationId,
							NicTransactionLog.TRANSACTION_STAGE_INVESTIGATION_UNASSIGN,
							updateObj.getWorkflowJobId().toString());

					updateObj
							.setInvestigationStatus(NicUploadJobDaoImpl.RECORD_STATUS_INITIAL);
					updateObj.setInvestigationOfficerId(null);
					updateObj.setUpdateBy(officerUserId);
					updateObj.setUpdateWkstnId(officerWorkstationId);
					updateObj.setUpdateDatetime(new Date());
					getHibernateTemplate().update(updateObj);
				}
			}
		} catch (Exception e) {
			logger.error("======== Exception while updating data ", e);
			throw e;
		}
	}

	@Override
	public void assignInvestigationJobs(List<Long> jobIds, String assignToId,
			String officerUserId, String officerWorkstationId) throws Exception {

		if (CollectionUtils.isEmpty(jobIds)) {
			return;
		}

		try {
			DetachedCriteria criteria = DetachedCriteria
					.forClass(NicUploadJob.class);
			criteria.add(Restrictions.in("workflowJobId", jobIds));
			criteria.add(Restrictions.eq("investigationStatus",
					NicUploadJob.RECORD_STATUS_INITIAL));
			criteria.add(Restrictions.isNull("investigationOfficerId"));
			List<NicUploadJob> list = (List<NicUploadJob>) getHibernateTemplate()
					.findByCriteria(criteria);
			if (CollectionUtils.isNotEmpty(list)) {
				for (NicUploadJob updateObj : list) {
					this.logTransaction(
							updateObj.getTransactionId(),
							NicTransactionLog.TRANSACTION_STAGE_INVESTIGATION_ASSIGN,
							this.codesService.getCodeValueDescByIdName(
									Codes.INVESTIGATION_STATUS,
									updateObj.getInvestigationStatus(),
									updateObj.getInvestigationStatus()),
							officerUserId,
							officerWorkstationId,
							NicTransactionLog.TRANSACTION_STAGE_INVESTIGATION_ASSIGN,
							updateObj.getWorkflowJobId().toString());

					updateObj.setInvestigationOfficerId(assignToId);
					updateObj
							.setInvestigationStatus(NicUploadJob.RECORD_STATUS_IN_PROGRESS);
					updateObj
							.setJobCurrentStage(NicTransactionLog.TRANSACTION_STAGE_INVESTIGATION);
					updateObj.setUpdateBy(officerUserId);
					updateObj.setUpdateDatetime(new Date());
					updateObj.setUpdateWkstnId(officerWorkstationId);

					getHibernateTemplate().update(updateObj);
				}
			}
		} catch (Exception e) {
			logger.error("======== Exception while updating data ", e);
			throw e;
		}
	}

	@Override
	public void assignInvestigationOnlyJob(long jobIds, String assignToId,
			String officerUserId, String officerWorkstationId) throws Exception {
		logger.info("assign User Singer. jobIds: " + jobIds);
		if (jobIds < 0) {
			logger.info("null data jobIds");
			return;
		}

		try {
			DetachedCriteria criteria = DetachedCriteria
					.forClass(NicUploadJob.class);
			criteria.add(Restrictions.eq("workflowJobId", jobIds));
			/*
			 * if(!assignToId.equals("SYSTEM"))
			 * criteria.add(Restrictions.eq("investigationStatus",
			 * NicUploadJob.RECORD_STATUS_INITIAL));
			 */
			criteria.add(Restrictions.isNull("investigationOfficerId"));
			List<NicUploadJob> list = (List<NicUploadJob>) getHibernateTemplate()
					.findByCriteria(criteria);
			if (CollectionUtils.isNotEmpty(list)) {
				for (NicUploadJob updateObj : list) {
					this.logTransaction(
							updateObj.getTransactionId(),
							NicTransactionLog.TRANSACTION_STAGE_INVESTIGATION_ASSIGN_BY_PARAM,
							this.codesService.getCodeValueDescByIdName(
									Codes.INVESTIGATION_STATUS,
									updateObj.getInvestigationStatus(),
									updateObj.getInvestigationStatus()),
							officerUserId,
							officerWorkstationId,
							NicTransactionLog.TRANSACTION_STAGE_INVESTIGATION_ASSIGN_BY_PARAM,
							updateObj.getWorkflowJobId().toString());

					updateObj.setInvestigationOfficerId(assignToId);
					// updateObj.setInvestigationStatus(NicUploadJob.RECORD_STATUS_IN_PROGRESS);
					updateObj
							.setJobCurrentStage(NicTransactionLog.TRANSACTION_STAGE_INVESTIGATION);
					updateObj.setUpdateBy(officerUserId);
					updateObj.setUpdateDatetime(new Date());
					updateObj.setUpdateWkstnId(officerWorkstationId);

					getHibernateTemplate().update(updateObj);
				}
			} else {
				logger.info("null data list");
			}
		} catch (Exception e) {
			logger.error("======== Exception while updating data ", e);
			throw e;
		}
	}

	@Override
	public void unassignSuspendedInvestigationJobs(List<Long> jobIds,
			String officerUserId, String officerWorkstationId) throws Exception {

		if (CollectionUtils.isEmpty(jobIds)) {
			return;
		}

		try {
			DetachedCriteria criteria = DetachedCriteria
					.forClass(NicUploadJob.class);
			criteria.add(Restrictions.in("workflowJobId", jobIds));
			criteria.add(Restrictions.eq("investigationStatus",
					NicUploadJob.RECORD_STATUS_SUSPENDED));
			criteria.add(Restrictions.isNotNull("investigationOfficerId"));
			List<NicUploadJob> list = (List<NicUploadJob>) getHibernateTemplate()
					.findByCriteria(criteria);
			if (CollectionUtils.isNotEmpty(list)) {
				for (NicUploadJob updateObj : list) {
					this.logTransaction(
							updateObj.getTransactionId(),
							NicTransactionLog.TRANSACTION_STAGE_FRAUD_CASE_MANAGEMENT_UNASSIGN,
							this.codesService.getCodeValueDescByIdName(
									Codes.INVESTIGATION_STATUS,
									updateObj.getInvestigationStatus(),
									updateObj.getInvestigationStatus()),
							officerUserId,
							officerWorkstationId,
							NicTransactionLog.TRANSACTION_STAGE_FRAUD_CASE_MANAGEMENT_UNASSIGN,
							updateObj.getWorkflowJobId().toString());

					updateObj.setInvestigationOfficerId(null);
					updateObj
							.setJobCurrentStage(NicTransactionLog.TRANSACTION_STAGE_INVESTIGATION);
					updateObj.setUpdateBy(officerUserId);
					updateObj.setUpdateWkstnId(officerWorkstationId);
					updateObj.setUpdateDatetime(new Date());
					getHibernateTemplate().update(updateObj);
				}
			}
		} catch (Exception e) {
			logger.error("======== Exception while updating data ", e);
			throw e;
		}
	}

	@Override
	public void assignSuspendedInvestigationJobs(List<Long> jobIds,
			String assignToId, String officerUserId, String officerWorkstationId)
			throws Exception {

		if (CollectionUtils.isEmpty(jobIds)) {
			return;
		}

		try {
			DetachedCriteria criteria = DetachedCriteria
					.forClass(NicUploadJob.class);
			criteria.add(Restrictions.in("workflowJobId", jobIds));
			criteria.add(Restrictions.eq("investigationStatus",
					NicUploadJob.RECORD_STATUS_SUSPENDED));
			criteria.add(Restrictions.isNull("investigationOfficerId"));
			List<NicUploadJob> list = (List<NicUploadJob>) getHibernateTemplate()
					.findByCriteria(criteria);
			if (CollectionUtils.isNotEmpty(list)) {
				for (NicUploadJob updateObj : list) {

					this.logTransaction(
							updateObj.getTransactionId(),
							NicTransactionLog.TRANSACTION_STAGE_FRAUD_CASE_MANAGEMENT_ASSIGN,
							this.codesService.getCodeValueDescByIdName(
									Codes.INVESTIGATION_STATUS,
									updateObj.getInvestigationStatus(),
									updateObj.getInvestigationStatus()),
							officerUserId,
							officerWorkstationId,
							NicTransactionLog.TRANSACTION_STAGE_FRAUD_CASE_MANAGEMENT_ASSIGN,
							updateObj.getWorkflowJobId().toString());

					updateObj.setInvestigationOfficerId(assignToId);
					updateObj
							.setJobCurrentStage(NicTransactionLog.TRANSACTION_STAGE_FRAUD_CASE_MANAGEMENT);
					updateObj.setUpdateBy(officerUserId);
					updateObj.setUpdateDatetime(new Date());
					updateObj.setUpdateWkstnId(officerWorkstationId);
					getHibernateTemplate().update(updateObj);
				}
			}
		} catch (Exception e) {
			logger.error("======== Exception while updating data ", e);
			throw e;
		}
	}

	/* tunt */
	@Override
	public PaginatedResult<NicUploadJob> findApproveJobForPaginationByOfficerId(
			String officerId, int pageNo, int pageSize, Boolean assignedOnly,
			AssignmentFilter assignmentFilter) throws ParseException {
		DetachedCriteria dCriteria = DetachedCriteria
				.forClass(getPersistentClass());
		Order orders = null;
		try {
			// TRUNG THÊM TRẠNG THÁI CHECK INVESTIGATION VÀ ĐƯỢC DUYỆT MỚI VÀO
			// DANH SÁCH CHỜ PHÊ DUYỆT
			DateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
			Date data = formatter.parse("19-04-2018");
			Calendar cal = Calendar.getInstance();
			cal.setTime(data);
			dCriteria.add(Restrictions.and(Restrictions.and(Restrictions
					.isNull("jobApproveStatus"), Restrictions.or(Restrictions
					.eq("investigationStatus",
							NicUploadJob.RECORD_STATUS_COMPLETED), Restrictions
					.and(Restrictions.eq("cpdCheckStatus",
							NicUploadJob.RECORD_STATUS_COMPLETED), Restrictions
							.eq("afisRegisterStatus",
									NicUploadJob.RECORD_STATUS_COMPLETED),
							Restrictions.eq("afisCheckStatus",
									NicUploadJob.RECORD_STATUS_COMPLETED)))),
					Restrictions.and(Restrictions.isNull("tempPassportNo"))));

			if (assignmentFilter != null) {
				if (StringUtils.isNotBlank(assignmentFilter.getTransactionId())) {
					dCriteria.add(Restrictions.eq("transactionId",
							assignmentFilter.getTransactionId()));
				}
			}
			orders = Order.asc("workflowJobId");
			logger.info("dao ===============>>>>>>>>>>" + pageSize);

			if (orders == null) {
				return this.findAllForPagination(dCriteria, pageNo, pageSize);
			} else {
				return this.findAllForPagination(dCriteria, pageNo, pageSize,
						orders);
			}
		} catch (Exception e) {
			logger.error("======== Exception while updating data ", e);
			// e.printStackTrace();
			throw e;
		}
	}

	@Override
	public PaginatedResult<NicUploadJob> findApproveJobForPaginationByOfficerIdALL(
			String officerId, int pageNo, int pageSize, Boolean assignedOnly,
			AssignmentFilter assignmentFilter) throws ParseException {
		DetachedCriteria dCriteria = DetachedCriteria
				.forClass(getPersistentClass());
		Order orders = null;
		try {
			// TRUNG THÊM TRẠNG THÁI CHECK INVESTIGATION VÀ ĐƯỢC DUYỆT MỚI VÀO
			// DANH SÁCH CHỜ PHÊ DUYỆT
			// /LOẠI BỎ TRẠNG THÁI ĐÃ PERSO_REGISTER_STATUS đã có dữ liệu không
			// gọi lại danh sách nữa
			/*
			 * DateFormat formatter = new SimpleDateFormat("dd-MM-yyyy"); Date
			 * data = formatter.parse("19-04-2018"); Calendar cal =
			 * Calendar.getInstance(); cal.setTime(data);
			 */
			dCriteria.add(Restrictions.and(Restrictions.eq("jobApproveStatus",
					"1"), Restrictions.eq("investigationStatus",
					NicUploadJob.RECORD_STATUS_APPROVE_PERSO), Restrictions.or(
					Restrictions.isNull("persoRegisterStatus"), Restrictions
							.eq("persoRegisterStatus",
									NicUploadJob.RECORD_STATUS_ERROR)),
					Restrictions.isNull("tempPassportNo")));

			if (assignmentFilter != null) {
				if (StringUtils.isNotBlank(assignmentFilter.getTransactionId())) {
					dCriteria.add(Restrictions.eq("transactionId",
							assignmentFilter.getTransactionId()));
				}
			}
			orders = Order.asc("workflowJobId");
			// logger.info("dao ===============>>>>>>>>>>" + pageSize);

			if (orders == null) {
				return this.findAllForPagination(dCriteria, pageNo, pageSize);
			} else {
				return this.findAllForPagination(dCriteria, pageNo, pageSize,
						orders);
			}
		} catch (Exception e) {
			logger.error("======== Exception while updating data ", e);
			// e.printStackTrace();
			throw e;
		}
	}

	@Override
	public void approveJobIdStatus(long approveJobId, String userId,
			String wkstnId) throws Exception {
		try {
			DetachedCriteria criteria = DetachedCriteria
					.forClass(NicUploadJob.class);
			criteria.add(Restrictions.eq("workflowJobId", approveJobId));
			List<NicUploadJob> list = (List<NicUploadJob>) getHibernateTemplate()
					.findByCriteria(criteria);
			if (CollectionUtils.isNotEmpty(list)) {
				NicUploadJob approveUpdateObj = list.get(0);
				approveUpdateObj.setJobApproveStatus("1");
				// approveUpdateObj.setInvestigationStatus(RECORD_STATUS_INITIAL);//Trung
				// chỉnh lại sau khi duyệt check AFIS và CPD
				approveUpdateObj.setUpdateBy(userId);
				approveUpdateObj.setUpdateWkstnId(wkstnId);
				approveUpdateObj.setUpdateDatetime(new Date());
				getHibernateTemplate().update(approveUpdateObj);
			}
		} catch (Exception e) {
			logger.error("======== Exception while updating data ", e);
			// e.printStackTrace();
			throw e;
		}
	}

	@Override
	public void approveJob_Confirmed(long approveJobId, String userId,
			String wkstnId, String investStatus) throws Exception {
		try {
			DetachedCriteria criteria = DetachedCriteria
					.forClass(NicUploadJob.class);
			criteria.add(Restrictions.eq("workflowJobId", approveJobId));
			List<NicUploadJob> list = (List<NicUploadJob>) getHibernateTemplate()
					.findByCriteria(criteria);
			if (CollectionUtils.isNotEmpty(list)) {
				NicUploadJob approveUpdateObj = list.get(0);
				approveUpdateObj.setInvestigationStatus(investStatus);
				if (investStatus == NicUploadJob.RECORD_STATUS_APPROVE_PERSO)
					approveUpdateObj.setJobApproveStatus("1");
				approveUpdateObj.setUpdateBy(userId);
				approveUpdateObj.setUpdateWkstnId(wkstnId);
				approveUpdateObj.setUpdateDatetime(new Date());
				getHibernateTemplate().update(approveUpdateObj);
			}
		} catch (Exception e) {
			logger.error("======== Exception while updating data ", e);
			// e.printStackTrace();
			throw e;
		}
	}

	@Override
	public String getUploadJobByTransactionId(String transId) throws Exception {
		DetachedCriteria detachedCriteria = DetachedCriteria
				.forClass(NicUploadJob.class);
		detachedCriteria.add(Restrictions.eq("transactionId", transId));

		List<NicUploadJob> result = (List<NicUploadJob>) getHibernateTemplate()
				.findByCriteria(detachedCriteria);
		if (CollectionUtils.isNotEmpty(result)) {
			for (NicUploadJob updateObj : result) {
				if (updateObj != null && updateObj.getTempPassportNo() != "") {
					String tempPP = updateObj.getTempPassportNo();
					return tempPP;
				} else {
					throw new Exception("No Job Id found with Transaction Id: "
							+ transId);
				}
			}
		}
		return "";
	}

	// /Tìm bản ghi theo Id Job
	@Override
	public NicUploadJob getUploadJobByTransactionIdJob(String transId)
			throws Exception {
		DetachedCriteria detachedCriteria = DetachedCriteria
				.forClass(NicUploadJob.class);
		if (transId != "") {
			long workflowId = 0;
			workflowId = Long.parseLong(transId);
			if (workflowId > 0) {
				detachedCriteria.add(Restrictions.eq("workflowJobId",
						workflowId));
				List<NicUploadJob> result = (List<NicUploadJob>) getHibernateTemplate()
						.findByCriteria(detachedCriteria);
				if (CollectionUtils.isNotEmpty(result)) {
					for (NicUploadJob updateObj : result) {
						if (updateObj != null) {

							return updateObj;
						} else {
							throw new Exception(
									"No Job Id found with Transaction Id: "
											+ transId);
						}
					}
				}
			}
		}
		return null;
	}

	@Override
	public NicUploadJob getNicTransactionByPrevPPno(String prevPassport,
			String typePassport, String nin) {
		NicUploadJob nicTransaction = null;

		DetachedCriteria detachedCriteria = DetachedCriteria
				.forClass(getPersistentClass());
		detachedCriteria.createAlias("nicTransaction", "nicTransaction");
		// detachedCriteria.getExecutableCriteria(this.hibernateTemplate.getSessionFactory().openSession());
		if (StringUtils.isNotEmpty(prevPassport)) {
			detachedCriteria.add(Restrictions.eq(
					"nicTransaction.prevPassportNo", prevPassport));
		} else if (StringUtils.isNotEmpty(nin)) {
			detachedCriteria.add(Restrictions.eq("nicTransaction.nin", nin));
		}

		detachedCriteria.add(Restrictions.eq("jobType", "LOS"));

		List<NicUploadJob> nicTransactionList = this.findAll(detachedCriteria);
		if (CollectionUtils.isNotEmpty(nicTransactionList)) {
			nicTransaction = nicTransactionList.get(0);
		}
		return nicTransaction;
	}

	@Override
	public void getSaveTempPassportNo(String transactionId, String passportNo)
			throws Exception {
		try {
			DetachedCriteria criteria = DetachedCriteria
					.forClass(NicUploadJob.class);
			criteria.add(Restrictions.eq("transactionId", transactionId));
			List<NicUploadJob> list = (List<NicUploadJob>) getHibernateTemplate()
					.findByCriteria(criteria);
			if (CollectionUtils.isNotEmpty(list)) {
				NicUploadJob approveUpdateObj = list.get(0);
				approveUpdateObj.setTempPassportNo(passportNo);
				approveUpdateObj.setUpdateDatetime(new Date());
				getHibernateTemplate().update(approveUpdateObj);
			}
		} catch (Exception e) {
			logger.error("======== Exception while updating data ", e);
			// e.printStackTrace();
			throw e;
		}
	}

	// TRUNG DIEU KIEN DE LAY DANH SACH DUYET IN
	@Override
	public PaginatedResult<NicUploadJob> findPersoList(String officerId,
			int pageNo, int pageSize, Boolean assignedOnly,
			AssignmentFilter assignmentFilter) throws ParseException {
		DetachedCriteria dCriteria = DetachedCriteria
				.forClass(getPersistentClass());
		Order orders = null;

		// TRUNG THÊM TRẠNG THÁI CHECK INVESTIGATION VÀ ĐƯỢC DUYỆT MỚI VÀO DANH
		// SÁCH
		DateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
		Date data = formatter.parse("19-04-2018");

		dCriteria.add(Restrictions.and(Restrictions.isNull("jobApproveStatus"),
				Restrictions.isNull("persoRegisterStatus"), Restrictions.eq(
						"investigationStatus",
						NicUploadJob.RECORD_STATUS_APPROVE_BOSS)));

		if (assignmentFilter != null) {
			if (StringUtils.isNotBlank(assignmentFilter.getTransactionId())) {
				dCriteria.add(Restrictions.eq("transactionId",
						assignmentFilter.getTransactionId()));
			}
		}
		orders = Order.asc("workflowJobId");
		logger.info("dao ===============>>>>>>>>>>" + pageSize);

		if (orders == null) {
			return this.findAllForPagination(dCriteria, pageNo, pageSize);
		} else {
			return this.findAllForPagination(dCriteria, pageNo, pageSize,
					orders);
		}
	}

	// /END

	// [SYNC] DIEU KIEN DE LAY DANH SACH DONG BO DU LIEU SANG BO CONG AN
	@Override
	public PaginatedResult<NicUploadJob> findSyncSingerList(String officerId,
			int pageNo, int pageSize, Boolean assignedOnly,
			AssignmentFilterAll assignmentFilter) throws ParseException {
		DetachedCriteria dCriteria = DetachedCriteria
				.forClass(getPersistentClass());
		dCriteria.createAlias("nicTransaction", "nicTransaction");
		Order orders = null;

		// TRUNG THÊM TRẠNG THÁI CHECK INVESTIGATION VÀ ĐƯỢC DUYỆT MỚI VÀO DANH
		// SÁCH
		DateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
		Date data = formatter.parse("19-04-2018");

		dCriteria.add(Restrictions.and(Restrictions
				.isNotNull("persoRegisterStatus"), Restrictions.eq(
				"persoRegisterStatus", NicUploadJob.RECORD_STATUS_COMPLETED),
				Restrictions.eq("nicTransaction.transactionStatus",
						"RIC_ISSUED"), Restrictions.or(
						Restrictions.eq("nicTransaction.syncPassport", 0),
						Restrictions.isNull("nicTransaction.syncPassport"))));

		if (assignmentFilter != null) {
			if (StringUtils.isNotBlank(assignmentFilter.getTransactionId())) {
				dCriteria.add(Restrictions.eq("transactionId",
						assignmentFilter.getTransactionId()));
			}

			if (assignmentFilter.getPriority() != null) {
				dCriteria.add(Restrictions.eq("nicTransaction.priority",
						assignmentFilter.getPriority()));
			}

			if (StringUtils.isNotBlank(assignmentFilter.getTransactionType())) {
				dCriteria.add(Restrictions.eq("nicTransaction.transactionType",
						assignmentFilter.getTransactionType()));
			}
		}
		orders = Order.asc("workflowJobId");
		logger.info("dao ===============>>>>>>>>>>" + pageSize);

		if (orders == null) {
			return this.findAllForPagination(dCriteria, pageNo, pageSize);
		} else {
			return this.findAllForPagination(dCriteria, pageNo, pageSize,
					orders);
		}
	}

	// /END

	// TRUNG THÊM CHO PHẦN PHÂN CÔNG
	public PaginatedResult<NicUploadJob> findInvestigationJobForPaginationNullOfficer(
			String[] recordStatuses, String officerId, int pageNo,
			int pageSize, Boolean assignedOnly,
			AssignmentFilterAll assignmentFilter) {
		DetachedCriteria dCriteria = DetachedCriteria
				.forClass(getPersistentClass());
		dCriteria.createAlias("nicTransaction", "nicTransaction");
		Order orders = null;
		if (StringUtils.isNotBlank(officerId)) {
			dCriteria.add(Restrictions.eq("investigationOfficerId", officerId));
		} else {
			dCriteria.add(Restrictions.isNull("investigationOfficerId"));
		}
		if (recordStatuses != null && recordStatuses.length > 0) {
			dCriteria.add(Restrictions
					.in("investigationStatus", recordStatuses));
		}

		if (assignmentFilter != null) {
			if (StringUtils.isNotBlank(assignmentFilter.getTransactionId())) {
				dCriteria.add(Restrictions.eq("transactionId",
						assignmentFilter.getTransactionId()));
			}
			if (assignmentFilter.getPriority() != null) {
				dCriteria.add(Restrictions.eq("nicTransaction.priority",
						assignmentFilter.getPriority()));
			}
			if (StringUtils.isNotBlank(assignmentFilter.getRegSiteCode())) {
				dCriteria.add(Restrictions.eq("nicTransaction.regSiteCode",
						assignmentFilter.getRegSiteCode()));
			}
			if (assignmentFilter.getCreateDate() != null) {
				dCriteria.add(Restrictions.ge("nicTransaction.createDatetime",
						assignmentFilter.getCreateDate()));

				Date maxDate = new Date(assignmentFilter.getCreateDate()
						.getTime() + TimeUnit.DAYS.toMillis(1));
				dCriteria.add(Restrictions.lt("nicTransaction.createDatetime",
						maxDate));
			}
			if (StringUtils.isNotBlank(assignmentFilter.getPassportType())) {
				dCriteria.add(Restrictions.eq("nicTransaction.passportType",
						assignmentFilter.getPassportType()));
			}

			if (StringUtils.isNotBlank(assignmentFilter.getTransactionType())) {
				dCriteria.add(Restrictions.eq("nicTransaction.transactionType",
						assignmentFilter.getTransactionType()));
			}

			if (assignmentFilter.getIssueDate() != null) {
				dCriteria.add(Restrictions.eq(
						"nicTransaction.estDateOfCollection",
						assignmentFilter.getIssueDate()));
			}
			if (StringUtils.isNotBlank(assignmentFilter.getPackageCode())) {
				dCriteria.add(Restrictions.eq("nicTransaction.packageID",
						assignmentFilter.getPackageCode()));
			}
		}
		dCriteria.add(Restrictions.not(Restrictions.eq("jobCurrentStage",
				NicUploadJob.JOB_STATE_PERSO_REGISTER)));

		orders = Order.asc("workflowJobId");
		if (orders == null) {
			return this.findAllForPagination(dCriteria, pageNo, pageSize);
		} else {
			return this.findAllForPagination(dCriteria, pageNo, pageSize,
					orders);
		}
	}

	// Phúc thêm 4/5/2019
	public List<NicUploadJob> convertData(List<NicUploadJob> list) {
		List<NicUploadJob> listKey = new ArrayList<NicUploadJob>();
		for (NicUploadJob nicUploadJob : list) {
			if (!StringUtils.isEmpty(nicUploadJob.getNicTransaction()
					.getPackageId())) {
				listKey.add(nicUploadJob);
			}
		}
		return totalByKey(listKey);
	}

	public List<NicUploadJob> totalByKey(List<NicUploadJob> list) {
		List<NicUploadJob> listNic = new ArrayList<NicUploadJob>();
		Set<String> set = new HashSet<>();
		for (NicUploadJob job : list) {
			set.add(job.getNicTransaction().getPackageId());
		}

		for (String key : set) {
			NicUploadJob nic = new NicUploadJob();
			int dem = 0;
			for (NicUploadJob job1 : list) {
				if (key.equals(job1.getNicTransaction().getPackageId())) {
					dem++;
					nic.getNicTransaction().setIssSiteCode(
							job1.getNicTransaction().getIssSiteCode());
				}
			}
			/*
			 * nic.setPackageId(key); nic.setNumberTran(dem +"");
			 */
			listNic.add(nic);
		}
		return listNic;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<NicUploadJob> findAllByPackageId(String packageId)
			throws Exception {
		try {
			DetachedCriteria detachedCriteria = DetachedCriteria
					.forClass(NicUploadJob.class);
			if (packageId != "") {
				detachedCriteria.add(Restrictions.eq(
						"nicTransaction.packageID", packageId));
				List<NicUploadJob> result = (List<NicUploadJob>) getHibernateTemplate()
						.findByCriteria(detachedCriteria);
				if (CollectionUtils.isNotEmpty(result)) {
					return result;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
		}
		return null;
	}

	@Override
	public PaginatedResult<NicUploadJob> findInvestigationJobForPaginationNullOfficerPackage(
			String[] recordStatuses, String officerId, int pageNo,
			int pageSize, Boolean assignedOnly,
			AssignmentFilterAll assignmentFilter) {
		DetachedCriteria dCriteria = DetachedCriteria
				.forClass(getPersistentClass());
		dCriteria.createAlias("nicTransaction", "nicTransaction");
		Order orders = null;
		if (StringUtils.isNotBlank(officerId)) {
			dCriteria.add(Restrictions.eq("investigationOfficerId", officerId));
		} else {
			dCriteria.add(Restrictions.isNull("investigationOfficerId"));
		}
		if (recordStatuses != null && recordStatuses.length > 0) {
			dCriteria.add(Restrictions
					.in("investigationStatus", recordStatuses));
		}

		if (assignmentFilter != null) {
			if (StringUtils.isNotBlank(assignmentFilter.getPackageCode())) {
				dCriteria.add(Restrictions.eq("nicTransaction.packageID",
						assignmentFilter.getPackageCode()));
			}
			if (StringUtils.isNotBlank(assignmentFilter.getRegSiteCode())) {
				dCriteria.add(Restrictions.eq("nicTransaction.regSiteCode",
						assignmentFilter.getRegSiteCode()));
			}
			if (assignmentFilter.getCreateDate() != null) {
				// dCriteria.add(Restrictions.ge("nicTransaction.createDatetime",
				// assignmentFilter.getCreateDate()));
				//
				// Date maxDate = new Date(assignmentFilter.getCreateDate()
				// .getTime() + TimeUnit.DAYS.toMillis(1));
				// dCriteria.add(Restrictions.lt("nicTransaction.createDatetime",
				// maxDate));
			}

		}
		dCriteria.add(Restrictions.not(Restrictions.eq("jobCurrentStage",
				NicUploadJob.JOB_STATE_PERSO_REGISTER)));

		orders = Order.asc("workflowJobId");
		List<NicUploadJob> lisNic = new ArrayList<NicUploadJob>();
		List<NicUploadJob> lisNicPage = new ArrayList<NicUploadJob>();
		if (orders == null) {
			List<NicUploadJob> list = this.findAll(dCriteria);
			lisNic = convertData(list);
		} else {
			List<NicUploadJob> list = this.findAllOrder(dCriteria, orders);
			lisNic = convertData(list);
		}
		int totalItems = lisNic.size() - (pageNo - 1) * pageSize; // 12 - 2
		if (totalItems > pageSize) {
			totalItems = pageSize;
		}
		for (int j = (pageNo - 1) * pageSize; j < totalItems + (pageNo - 1)
				* pageSize; j++) {
			lisNicPage.add(lisNic.get(j));
		}
		return new PaginatedResult<>(lisNic.size(), pageNo, lisNicPage);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<NicUploadJob> findAllByTransactionIdOrStatus(String[] status,
			String txnId) throws Exception {
		try {
			logger.debug("Inside Dao Impl============>>>>>>>Find the Job List by transaction Id "
					+ txnId);
			DetachedCriteria criteria = DetachedCriteria
					.forClass(NicUploadJob.class);
			if (status != null && status.length > 0) {
				criteria.add(Restrictions.in("investigationStatus", status));
			}
			if (StringUtils.isNotBlank(txnId)) {
				criteria.add(Restrictions.eq("transactionId", txnId));
			}
			return (List<NicUploadJob>) getHibernateTemplate().findByCriteria(
					criteria);
		} catch (Exception e) {
			logger.error(e.getMessage());
			throw e;
		}
	}

	@Override
	public List<NicUploadJob> findInvestigationJobForPagination(
			String[] recordStatuses, String officerId, Boolean assignedOnly,
			AssignmentFilterAll assignmentFilter) {
		DetachedCriteria dCriteria = DetachedCriteria
				.forClass(getPersistentClass());
		dCriteria.createAlias("nicTransaction", "nicTransaction");
		Order orders = null;
		Boolean perso = false;
		Boolean StageJobType = false;
		dCriteria.add(Restrictions.isNull("nicTransaction.nicSiteCode"));
		if (recordStatuses != null && recordStatuses.length > 0) {
			for (String st : recordStatuses) {
				if (st.equals("LOS")) {
					StageJobType = true;
					break;
				}
				if (st.equals(NicUploadJob.RECORD_STATUS_APPROVE_PERSO)) {
					perso = true;
					break;
				}
			}
			if (!StageJobType)
				dCriteria.add(Restrictions.in("investigationStatus",
						recordStatuses));
			else
				dCriteria.add(Restrictions.in("jobType", recordStatuses));
		}

		if (StringUtils.isNotBlank(officerId) && !StageJobType) {
			dCriteria.add(Restrictions.eq("investigationOfficerId", officerId));
		}
		if (assignedOnly != null) {
			if (assignedOnly && !StageJobType) {
				dCriteria.add(Restrictions.isNotNull("investigationOfficerId"));
			} else {
				dCriteria.add(Restrictions.isNull("investigationOfficerId"));
			}
		}

		if (assignmentFilter != null) {
			if (StringUtils.isNotBlank(assignmentFilter.getRegSiteCode())) {
				dCriteria.add(Restrictions.eq("nicTransaction.regSiteCode",
						assignmentFilter.getRegSiteCode()));
			}
			// if (assignmentFilter.getCreateDate() != null) {
			// dCriteria.add(Restrictions.ge("nicTransaction.createDatetime",
			// assignmentFilter.getCreateDate()));
			//
			// Date maxDate = new Date(assignmentFilter.getCreateDate()
			// .getTime() + TimeUnit.DAYS.toMillis(1));
			// dCriteria.add(Restrictions.lt("nicTransaction.createDatetime",
			// maxDate));
			// }
			// if (StringUtils.isNotBlank(assignmentFilter.getPackageCode())) {
			// dCriteria.add(Restrictions.eq("nicTransaction.packageID",
			// assignmentFilter.getPackageCode()));
			// }
		}
		orders = Order.asc("workflowJobId");
		if (orders == null) {
			return this.findAll(dCriteria);
		} else {
			return this.findAllOrder(dCriteria, orders);
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public void assignInvestigationJobs1(List<Long> jobIds,
			String[] assignToId, String officerUserId,
			String officerWorkstationId) throws Exception {
		if (CollectionUtils.isEmpty(jobIds)) {
			return;
		}
		// String dsAssign = "";
		// for(int i = 0 ; i < assignToId.length; i++){
		// dsAssign += assignToId[i];
		// if(i != assignToId.length - 1)
		// dsAssign += ",";
		// }
		try {
			DetachedCriteria criteria = DetachedCriteria
					.forClass(NicUploadJob.class);
			criteria.add(Restrictions.in("workflowJobId", jobIds));
			criteria.add(Restrictions.eq("investigationStatus",
					NicUploadJob.RECORD_STATUS_INITIAL));
			criteria.add(Restrictions.isNull("investigationOfficerId"));
			List<NicUploadJob> list = (List<NicUploadJob>) getHibernateTemplate()
					.findByCriteria(criteria);
			if (CollectionUtils.isNotEmpty(list)) {
				for (NicUploadJob updateObj : list) {
					this.logTransaction(
							updateObj.getTransactionId(),
							NicTransactionLog.TRANSACTION_STAGE_INVESTIGATION_ASSIGN,
							this.codesService.getCodeValueDescByIdName(
									Codes.INVESTIGATION_STATUS,
									updateObj.getInvestigationStatus(),
									updateObj.getInvestigationStatus()),
							officerUserId,
							officerWorkstationId,
							NicTransactionLog.TRANSACTION_STAGE_INVESTIGATION_ASSIGN,
							updateObj.getWorkflowJobId().toString());

					// updateObj.setInvestigationOfficerId(dsAssign);
					// if(StringUtils.isEmpty(updateObj.getInvestigationOfficerId())){
					// updateObj.setInvestigationOfficerId(dsAssign);
					// }else{
					// updateObj.setInvestigationOfficerId(updateObj.getInvestigationOfficerId()
					// + "," + dsAssign);
					// }
					updateObj
							.setInvestigationStatus(NicUploadJob.RECORD_STATUS_IN_PROGRESS);
					updateObj
							.setJobCurrentStage(NicTransactionLog.TRANSACTION_STAGE_INVESTIGATION);
					updateObj.setUpdateBy(officerUserId);
					updateObj.setUpdateDatetime(new Date());
					updateObj.setUpdateWkstnId(officerWorkstationId);

					getHibernateTemplate().update(updateObj);
				}
			}
		} catch (Exception e) {
			logger.error("======== Exception while updating data ", e);
			throw e;
		}

	}

	@SuppressWarnings("unchecked")
	@Override
	public void assignInvestigationJobsUpdate(List<Long> jobIds,
			String[] assignToId, String officerUserId,
			String officerWorkstationId) throws Exception {
		if (CollectionUtils.isEmpty(jobIds)) {
			return;
		}
		String dsAssign = "";
		for (int i = 0; i < assignToId.length; i++) {
			dsAssign += assignToId[i];
			if (i != assignToId.length - 1)
				dsAssign += ",";
		}
		try {
			DetachedCriteria criteria = DetachedCriteria
					.forClass(NicUploadJob.class);
			criteria.add(Restrictions.in("workflowJobId", jobIds));
			// criteria.add(Restrictions.eq("investigationStatus",NicUploadJob.RECORD_STATUS_INITIAL));
			criteria.add(Restrictions.isNotNull("investigationOfficerId"));
			List<NicUploadJob> list = (List<NicUploadJob>) getHibernateTemplate()
					.findByCriteria(criteria);
			if (CollectionUtils.isNotEmpty(list)) {
				for (NicUploadJob updateObj : list) {
					/*
					 * this.logTransaction( updateObj.getTransactionId(),
					 * NicTransactionLog.TRANSACTION_STAGE_INVESTIGATION_ASSIGN,
					 * this.codesService.getCodeValueDescByIdName(
					 * Codes.INVESTIGATION_STATUS,
					 * updateObj.getInvestigationStatus(),
					 * updateObj.getInvestigationStatus()), officerUserId,
					 * officerWorkstationId,
					 * NicTransactionLog.TRANSACTION_STAGE_INVESTIGATION_ASSIGN,
					 * updateObj.getWorkflowJobId().toString());
					 */

					// updateObj.setInvestigationOfficerId(dsAssign);
					if (StringUtils.isEmpty(updateObj
							.getInvestigationOfficerId())) {
						updateObj.setInvestigationOfficerId(dsAssign);
					} else {
						updateObj.setInvestigationOfficerId(updateObj
								.getInvestigationOfficerId() + "," + dsAssign);
					}
					// updateObj.setInvestigationStatus(NicUploadJob.RECORD_STATUS_IN_PROGRESS);
					// updateObj.setJobCurrentStage(NicTransactionLog.TRANSACTION_STAGE_INVESTIGATION);
					updateObj.setUpdateBy(officerUserId);
					updateObj.setUpdateDatetime(new Date());
					updateObj.setUpdateWkstnId(officerWorkstationId);

					getHibernateTemplate().update(updateObj);
				}
			}
		} catch (Exception e) {
			logger.error("======== Exception while updating data ", e);
			throw e;
		}

	}

	@Override
	public PaginatedResult<NicUploadJob> findInvestigationJobForPaginationByOfficerId1(
			String[] recordStatuses, String officerId, String leaderId,
			int pageNo, int pageSize, Boolean assignedOnly,
			AssignmentFilterAll assignmentFilter) {
		DetachedCriteria dCriteria = DetachedCriteria
				.forClass(getPersistentClass());
		dCriteria.createAlias("nicTransaction", "nicTransaction");
		Order orders = null;
		Boolean perso = false;
		Boolean StageJobType = false;
		dCriteria.add(Restrictions.isNull("nicTransaction.nicSiteCode"));
		if (recordStatuses != null && recordStatuses.length > 0) {
			for (String st : recordStatuses) {
				if (st.equals("LOS")) {
					StageJobType = true;
					break;
				}
				if (st.equals(NicUploadJob.RECORD_STATUS_APPROVE_PERSO)) {
					perso = true;
					break;
				}
			}
			if (!StageJobType)
				dCriteria.add(Restrictions.in("investigationStatus",
						recordStatuses));
			else
				dCriteria.add(Restrictions.in("jobType", recordStatuses));
		}
		// Phúc edit
		dCriteria.createAlias("nicTransaction.listHandovers", "handover");
		if (StringUtils.isNotEmpty(officerId)) {
			dCriteria.add(Restrictions.isNotNull("handover.usersProcess"));
		}
		if (StringUtils.isNotEmpty(leaderId)) {
			dCriteria.add(Restrictions.like("handover.usersProcess", "%"
					+ leaderId + ",%"));
			// Criterion test1 = Restrictions.like("handover.usersProcess",
			// "%"+leaderId+",%");
			// Criterion test2 = Restrictions.like("handover.usersProcess",
			// "%"+leaderId+",");
			// Criterion test3 = Restrictions.like("handover.usersProcess",
			// leaderId+",%");
			// dCriteria.add(Restrictions.or(Restrictions.or(test1, test2),
			// test3));
			// dCriteria.add(Restrictions.disjunction().add(test1).add(test2).add(test3));
		}

		// Phúc đóng 7/6/2-19
		// if (StringUtils.isNotBlank(officerId) && !StageJobType) {
		// dCriteria.add(Restrictions.eq("investigationOfficerId", officerId));
		// }
		// if (assignedOnly != null) {
		// if (assignedOnly && !StageJobType) {
		// dCriteria.add(Restrictions.isNotNull("investigationOfficerId"));
		// } else {
		// dCriteria.add(Restrictions.isNull("investigationOfficerId"));
		// }
		// }
		// end
		/*
		 * if (assignmentFilter != null) { if
		 * (StringUtils.isNotBlank(assignmentFilter.getTransactionId())) {
		 * dCriteria.add(Restrictions.eq("transactionId",
		 * assignmentFilter.getTransactionId())); } }
		 */

		if (assignmentFilter != null) {
			if (StringUtils.isNotBlank(assignmentFilter.getTransactionId())) {
				dCriteria.add(Restrictions.eq("transactionId",
						assignmentFilter.getTransactionId()));
			}
			if (assignmentFilter.getPriority() != null) {
				dCriteria.add(Restrictions.eq("nicTransaction.priority",
						assignmentFilter.getPriority()));
			}
			if (StringUtils.isNotBlank(assignmentFilter.getRegSiteCode())) {
				dCriteria.add(Restrictions.eq("nicTransaction.regSiteCode",
						assignmentFilter.getRegSiteCode()));
			}
			if (assignmentFilter.getCreateDate() != null) {
				dCriteria.add(Restrictions.ge("nicTransaction.createDatetime",
						assignmentFilter.getCreateDate()));

				Date maxDate = new Date(assignmentFilter.getCreateDate()
						.getTime() + TimeUnit.DAYS.toMillis(1));
				dCriteria.add(Restrictions.lt("nicTransaction.createDatetime",
						maxDate));
			}
			if (StringUtils.isNotBlank(assignmentFilter.getPassportType())) {
				dCriteria.add(Restrictions.eq("nicTransaction.passportType",
						assignmentFilter.getPassportType()));
			}

			if (StringUtils.isNotBlank(assignmentFilter.getTransactionType())) {
				dCriteria.add(Restrictions.eq("nicTransaction.transactionType",
						assignmentFilter.getTransactionType()));
			}

			if (StringUtils.isNotBlank(assignmentFilter.getPackageCode())) {
				dCriteria.add(Restrictions.eq("nicTransaction.packageID",
						assignmentFilter.getPackageCode()));
			}

			if (assignmentFilter.getIssueDate() != null) {
				dCriteria.add(Restrictions.eq(
						"nicTransaction.estDateOfCollection",
						assignmentFilter.getIssueDate()));
			}
			// if
			// ((StringUtils.isNotBlank(assignmentFilter.getTypeInvestigation())))
			// {
			// if(!perso)
			// dCriteria.add(Restrictions.eq("investigationType",assignmentFilter.getTypeInvestigation()));
			// else
			// {
			// if(assignmentFilter.getTypeInvestigation().contains("0"))
			// dCriteria.add(Restrictions.eq("nicTransaction.syncPassport",0));
			// else if(assignmentFilter.getTypeInvestigation().contains("1"))
			// dCriteria.add(Restrictions.eq("nicTransaction.syncPassport",1));
			// else if(assignmentFilter.getTypeInvestigation().contains("2"))
			// dCriteria.add(Restrictions.isNull("nicTransaction.syncPassport"));
			// }
			// }
			if (assignmentFilter.getTranArray() != null
					&& assignmentFilter.getTranArray().length > 0) {
				dCriteria.add(Restrictions.in("transactionId",
						assignmentFilter.getTranArray()));
			}

		}
		orders = Order.desc("createDatetime");
		if (orders == null) {
			return this.findAllForPagination(dCriteria, pageNo, pageSize);
		} else {
			return this.findAllForPagination(dCriteria, pageNo, pageSize,
					orders);
		}
	}

	@Override
	public List<NicUploadJob> findInvestigationJobForPaginationByOfficerId2(
			String[] recordStatuses, String officerId, Boolean assignedOnly,
			AssignmentFilterAll assignmentFilter) {
		DetachedCriteria dCriteria = DetachedCriteria
				.forClass(getPersistentClass());
		dCriteria.createAlias("nicTransaction", "nicTransaction");
		Order orders = null;
		Boolean perso = false;
		Boolean StageJobType = false;
		dCriteria.add(Restrictions.isNull("nicTransaction.nicSiteCode"));
		if (recordStatuses != null && recordStatuses.length > 0) {
			for (String st : recordStatuses) {
				if (st.equals("LOS")) {
					StageJobType = true;
					break;
				}
				if (st.equals(NicUploadJob.RECORD_STATUS_APPROVE_PERSO)) {
					perso = true;
					break;
				}
			}
			if (!StageJobType)
				dCriteria.add(Restrictions.in("investigationStatus",
						recordStatuses));
			else
				dCriteria.add(Restrictions.in("jobType", recordStatuses));
		}
		if (StringUtils.isNotBlank(officerId) && !StageJobType) {
			dCriteria.add(Restrictions.eq("investigationOfficerId", officerId));
		}
		if (assignedOnly != null) {
			if (assignedOnly && !StageJobType) {
				dCriteria.add(Restrictions.isNotNull("investigationOfficerId"));
			} else {
				dCriteria.add(Restrictions.isNull("investigationOfficerId"));
			}
		}

		dCriteria.add(Restrictions.isNull("nicTransaction.leaderOfficerId"));
		if (assignmentFilter != null) {

			if (StringUtils.isNotBlank(assignmentFilter.getPackageCode())) {
				dCriteria.add(Restrictions.eq("nicTransaction.packageID",
						assignmentFilter.getPackageCode()));
			}

			if (assignmentFilter.getIssueDate() != null) {
				dCriteria.add(Restrictions.eq(
						"nicTransaction.estDateOfCollection",
						assignmentFilter.getIssueDate()));
			}

			if (assignmentFilter.getCreateDate() != null
					&& assignmentFilter.getEndDate() != null) {
				dCriteria.createAlias("nicTransaction.listHandovers",
						"handoverName");
				dCriteria.add(Restrictions.ge("handoverName.createDate",
						assignmentFilter.getCreateDate()));
				dCriteria.add(Restrictions.le("handoverName.createDate",
						assignmentFilter.getEndDate()));
			}

		}
		orders = Order.desc("createDatetime");
		return this.findAll(dCriteria);
	}

	@Override
	public List<NicUploadJob> findInvestigationProcessByJobId(Long[] JobList,
			String officerId, AssignmentFilterAll assignmentFilter) {
		DetachedCriteria dCriteria = DetachedCriteria
				.forClass(getPersistentClass());
		dCriteria.createAlias("nicTransaction", "nicTransaction");
		// Order orders = null;
		// Boolean perso = false;
		// Boolean StageJobType = false;

		if (!StringUtils.isEmpty(officerId)) {
			dCriteria.add(Restrictions.eq("investigationOfficerId", officerId));
		}
		if (JobList != null && JobList.length > 0) {
			dCriteria.add(Restrictions.in("workflowJobId", JobList));
		}
		if (assignmentFilter != null) {
			if (StringUtils.isNotBlank(assignmentFilter.getPackageCode())) {
				dCriteria.add(Restrictions.eq("nicTransaction.packageID",
						assignmentFilter.getPackageCode()));
			}
			if (StringUtils.isNotBlank(assignmentFilter.getRegSiteCode())) {
				dCriteria.add(Restrictions.eq("nicTransaction.regSiteCode",
						assignmentFilter.getRegSiteCode()));
			}
			if (StringUtils.isNotBlank(assignmentFilter.getTransactionId())) {
				dCriteria.add(Restrictions.eq("transactionId",
						assignmentFilter.getTransactionId()));
			}
		}
		// orders = Order.desc("createDatetime");
		return this.findAll(dCriteria);
	}

	@SuppressWarnings("unchecked")
	@Override
	public Boolean updateMatchingData(Long jobId, String tagetTransactionId,
			int type, String note, Long idBlacklist, String fullName)
			throws Exception {
		try {
			DetachedCriteria criteria = DetachedCriteria
					.forClass(NicUploadJob.class);
			criteria.add(Restrictions.eq("workflowJobId", jobId));
			List<NicUploadJob> list = (List<NicUploadJob>) getHibernateTemplate()
					.findByCriteria(criteria);
			if (CollectionUtils.isNotEmpty(list)) {
				NicUploadJob UpdateObj = list.get(0);
				String startNote = "";
				if (type == 1) {
					if (StringUtils.isNotEmpty(tagetTransactionId)) {
						UpdateObj.getNicTransaction().setPersonCode(
								tagetTransactionId);
						startNote = RegistrationConstants.DETAULT_START_KHOP_CA_NHAN;
					} else {
						startNote = RegistrationConstants.DETAULT_START_KHOP_CA_NHAN_N;
					}
					if (StringUtils.isNotEmpty(fullName)
							&& StringUtils.isNotEmpty(tagetTransactionId)) {
						startNote += " - " + fullName;
					}
					if (StringUtils.isNotEmpty(note)) {
						startNote += " - " + note;
					}
					UpdateObj.setDateApprovePerson(new Date());
					UpdateObj.setNoteApprovePerson(startNote);
				} else if (type == 2) {
					if (idBlacklist != null) {
						UpdateObj.setOriginalyBlacklistId(idBlacklist);
						startNote = RegistrationConstants.DETAULT_START_TRACUU_DT;
					} else {
						startNote = RegistrationConstants.DETAULT_START_TRACUU_DT_N;
					}
					if (StringUtils.isNotEmpty(fullName) && idBlacklist != null) {
						startNote += " - " + fullName;
					}
					if (StringUtils.isNotEmpty(note)) {
						startNote += " - " + note;
					}
					UpdateObj.setDateApproveInvestigation(new Date());
					UpdateObj.setNoteApproveObj(startNote);
				} else if (type == 3) {
					UpdateObj.setDateApproveNin(new Date());
					startNote = RegistrationConstants.DETAULT_START_TRACUU_CMND;
					if (StringUtils.isNotEmpty(note)) {
						startNote += " - " + note;
						UpdateObj.setNoteApproveNin(startNote);
					}
				}
				getHibernateTemplate().update(UpdateObj);
				return true;
			}
		} catch (Exception e) {
			logger.error("======== Exception while updateMatchingData ", e);
			// e.printStackTrace();
			throw e;
		}
		// return rejectJobID;
		return false;
	}

	@Override
	public List<NicUploadJob> findInvestigationJobListB(String packageId,
			AssignmentFilterAll assignmentFilter) {
		try {
			DetachedCriteria dCriteria = DetachedCriteria
					.forClass(getPersistentClass());
			dCriteria.createAlias("nicTransaction", "nicTransaction");

			dCriteria.add(Restrictions
					.isNotNull("nicTransaction.leaderOfficerId"));
			if (!StringUtils.isEmpty(packageId)) {
				dCriteria.createAlias("nicTransaction.listHandovers",
						"handoverName");
				dCriteria.add(Restrictions.eq("handoverName.packageId",
						packageId));
			}
			if (assignmentFilter != null) {

				// if
				// (StringUtils.isNotBlank(assignmentFilter.getPackageCode())) {
				// dCriteria.add(Restrictions.eq("nicTransaction.packageID",
				// assignmentFilter.getPackageCode()));
				// }
				//
				// if (assignmentFilter.getIssueDate() != null) {
				// dCriteria.add(Restrictions.eq("nicTransaction.estDateOfCollection",
				// assignmentFilter.getIssueDate()));
				// }

			}
			return this.findAll(dCriteria);

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
		}
		return null;
	}

	@Override
	public BaseModelSingle<NicUploadJob> getUploadJobByTransactionIdSinger(
			Long jobId, String transId) {
		try {
			DetachedCriteria dCriteria = DetachedCriteria
					.forClass(getPersistentClass());
			dCriteria.createAlias("nicTransaction", "nicTransaction");

			if (jobId != null) {
				dCriteria.add(Restrictions.eq("workflowJobId", jobId));
			}

			if (!StringUtils.isEmpty(transId)) {
				dCriteria.add(Restrictions.eq("nicTransaction.transactionId",
						transId));
			}

			List<NicUploadJob> list = this.findAll(dCriteria);
			if (list != null && list.size() > 0)
				return new BaseModelSingle<NicUploadJob>(list.get(0), true,
						null);

		} catch (Exception e) {
			e.printStackTrace();
			return new BaseModelSingle<NicUploadJob>(null, false,
					CreateMessageException.createMessageException(e)
							+ " - getUploadJobByTransactionIdSinger - "
							+ transId + " - thất bại.");
		}
		return new BaseModelSingle<NicUploadJob>(null, true, null);
	}

	@Override
	public List<NicUploadJob> getListByStatusAndYear(String[] recordStatuses,
			Calendar calendar) {
		try {
			DetachedCriteria dCriteria = DetachedCriteria
					.forClass(getPersistentClass());
			dCriteria.createAlias("nicTransaction", "nicTransaction");

			if (recordStatuses != null) {
				dCriteria.add(Restrictions.in("investigationStatus",
						recordStatuses));
			}
			List<NicUploadJob> list = this.findAll(dCriteria);
			if (list != null && list.size() > 0)
				return list;

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
		}
		return null;
	}

	@Override
	public List<NicUploadJob> findInvestigationJobForPaginationByOfficerId2(
			String[] recordStatuses, String officerId, String leaderId,
			Boolean assignedOnly, AssignmentFilterAll assignmentFilter) {
		DetachedCriteria dCriteria = DetachedCriteria
				.forClass(getPersistentClass());
		dCriteria.createAlias("nicTransaction", "nicTransaction");
		Order orders = null;
		Boolean perso = false;
		Boolean StageJobType = false;
		dCriteria.add(Restrictions.isNull("nicTransaction.nicSiteCode"));
		if (recordStatuses != null && recordStatuses.length > 0) {
			for (String st : recordStatuses) {
				if (st.equals("LOS")) {
					StageJobType = true;
					break;
				}
				if (st.equals(NicUploadJob.RECORD_STATUS_APPROVE_PERSO)) {
					perso = true;
					break;
				}
			}
			if (!StageJobType)
				dCriteria.add(Restrictions.in("investigationStatus",
						recordStatuses));
			else
				dCriteria.add(Restrictions.in("jobType", recordStatuses));
		}
		// Phúc edit
		dCriteria.createAlias("nicTransaction.listHandovers", "handover");
		if (StringUtils.isNotEmpty(officerId)) {
			dCriteria.add(Restrictions.isNotNull("handover.usersProcess"));
		}
		if (StringUtils.isNotEmpty(leaderId)) {
			dCriteria.add(Restrictions.like("handover.usersProcess", "%"
					+ leaderId + ",%"));
			// Criterion test1 = Restrictions.like("handover.usersProcess",
			// "%"+leaderId+",%");
			// Criterion test2 = Restrictions.like("handover.usersProcess",
			// "%"+leaderId+",");
			// Criterion test3 = Restrictions.like("handover.usersProcess",
			// leaderId+",%");
			// dCriteria.add(Restrictions.or(Restrictions.or(test1, test2),
			// test3));
			// dCriteria.add(Restrictions.disjunction().add(test1).add(test2).add(test3));
		}

		// Phúc đóng 7/6/2-19
		// if (StringUtils.isNotBlank(officerId) && !StageJobType) {
		// dCriteria.add(Restrictions.eq("investigationOfficerId", officerId));
		// }
		// if (assignedOnly != null) {
		// if (assignedOnly && !StageJobType) {
		// dCriteria.add(Restrictions.isNotNull("investigationOfficerId"));
		// } else {
		// dCriteria.add(Restrictions.isNull("investigationOfficerId"));
		// }
		// }
		// end
		/*
		 * if (assignmentFilter != null) { if
		 * (StringUtils.isNotBlank(assignmentFilter.getTransactionId())) {
		 * dCriteria.add(Restrictions.eq("transactionId",
		 * assignmentFilter.getTransactionId())); } }
		 */

		if (assignmentFilter != null) {
			// if (StringUtils.isNotBlank(assignmentFilter.getTransactionId()))
			// {
			// dCriteria.add(Restrictions.eq("transactionId",
			// assignmentFilter.getTransactionId()));
			// }
			// if (assignmentFilter.getPriority() != null) {
			// dCriteria.add(Restrictions.eq("nicTransaction.priority",
			// assignmentFilter.getPriority()));
			// }
			// if (StringUtils.isNotBlank(assignmentFilter.getRegSiteCode())) {
			// dCriteria.add(Restrictions.eq("nicTransaction.regSiteCode",
			// assignmentFilter.getRegSiteCode()));
			// }
			// if (assignmentFilter.getCreateDate() != null) {
			// dCriteria.add(Restrictions.ge("nicTransaction.createDatetime",
			// assignmentFilter.getCreateDate()));
			//
			// Date maxDate = new Date(assignmentFilter.getCreateDate()
			// .getTime() + TimeUnit.DAYS.toMillis(1));
			// dCriteria.add(Restrictions.lt("nicTransaction.createDatetime",
			// maxDate));
			// }
			// if (StringUtils.isNotBlank(assignmentFilter.getPassportType())) {
			// dCriteria.add(Restrictions.eq("nicTransaction.passportType",
			// assignmentFilter.getPassportType()));
			// }
			//
			// if
			// (StringUtils.isNotBlank(assignmentFilter.getTransactionType())) {
			// dCriteria.add(Restrictions.eq("nicTransaction.transactionType",
			// assignmentFilter.getTransactionType()));
			// }

			if (StringUtils.isNotBlank(assignmentFilter.getPackageCode())) {
				dCriteria.add(Restrictions.eq("nicTransaction.packageID",
						assignmentFilter.getPackageCode()));
			}

			// if (assignmentFilter.getIssueDate() != null) {
			// dCriteria.add(Restrictions.eq(
			// "nicTransaction.estDateOfCollection",
			// assignmentFilter.getIssueDate()));
			// }
			// if
			// ((StringUtils.isNotBlank(assignmentFilter.getTypeInvestigation())))
			// {
			// if(!perso)
			// dCriteria.add(Restrictions.eq("investigationType",assignmentFilter.getTypeInvestigation()));
			// else
			// {
			// if(assignmentFilter.getTypeInvestigation().contains("0"))
			// dCriteria.add(Restrictions.eq("nicTransaction.syncPassport",0));
			// else if(assignmentFilter.getTypeInvestigation().contains("1"))
			// dCriteria.add(Restrictions.eq("nicTransaction.syncPassport",1));
			// else if(assignmentFilter.getTypeInvestigation().contains("2"))
			// dCriteria.add(Restrictions.isNull("nicTransaction.syncPassport"));
			// }
			// }
			// if(assignmentFilter.getTranArray() != null &&
			// assignmentFilter.getTranArray().length > 0){
			// dCriteria.add(Restrictions.in("transactionId",
			// assignmentFilter.getTranArray()));
			// }

		}
		orders = Order.desc("createDatetime");
		if (orders == null) {
			return this.findAll(dCriteria);
		} else {
			return this.findAllOrder(dCriteria, orders);
		}
	}

	@Override
	public int countInvestigationJobListB(String packageId,
			String[] recordStatuses) {
		try {
			DetachedCriteria dCriteria = DetachedCriteria
					.forClass(getPersistentClass());
			dCriteria.createAlias("nicTransaction", "nicTransaction");
			// if (recordStatuses != null && recordStatuses.length > 0) {
			Criterion c1 = Restrictions.in("investigationStatus",
					recordStatuses);
			// dCriteria.add(Restrictions.in("investigationStatus",
			// recordStatuses));
			// }
			// if (StringUtils.isNotBlank(packageId)) {
			// dCriteria.add(Restrictions.eq("nicTransaction.packageID",
			// packageId));
			Criterion c2 = Restrictions.eq("nicTransaction.packageID",
					packageId);
			// }

			dCriteria.add(Restrictions.and(c1, c2));

			List<NicUploadJob> list = this.findAll(dCriteria);
			if (list != null)
				return list.size();

		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}

	@Override
	public List<NicUploadJob> findInvestigationJobApprove(String packageId,
			AssignmentFilterAll assignmentFilter) {
		try {
			DetachedCriteria dCriteria = DetachedCriteria
					.forClass(getPersistentClass());
			dCriteria.createAlias("nicTransaction", "nicTransaction");

			// dCriteria.add(Restrictions.isNotNull("nicTransaction.leaderOfficerId"));
			if (!StringUtils.isEmpty(packageId)) {
				dCriteria.createAlias("nicTransaction.listHandovers",
						"handoverName");
				dCriteria.add(Restrictions.eq("handoverName.packageId",
						packageId));
			}
			if (assignmentFilter != null) {

				// if
				// (StringUtils.isNotBlank(assignmentFilter.getPackageCode())) {
				// dCriteria.add(Restrictions.eq("nicTransaction.packageID",
				// assignmentFilter.getPackageCode()));
				// }
				//
				// if (assignmentFilter.getIssueDate() != null) {
				// dCriteria.add(Restrictions.eq("nicTransaction.estDateOfCollection",
				// assignmentFilter.getIssueDate()));
				// }

			}
			return this.findAll(dCriteria);

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
		}
		return null;
	}

	@Override
	public BaseModelSingle<Long> createWorkflowJobCheck(String transactionId,
			String jobType, Integer jobPriority, Integer countCall,
			String personCode) {
		Long nicUploadJobId = null;

		String officerId = SYSTEM_EPP;
		String wkstnId = SYSTEM_EPP;
		try {
			java.net.InetAddress localMachine = InetAddress.getLocalHost();
			wkstnId = localMachine.getHostName().toUpperCase();
		} catch (Exception e) {
			return new BaseModelSingle<Long>(null, false,
					CreateMessageException.createMessageException(e)
							+ " - createWorkflowJobCheck - thất bại.");
		}

		NicUploadJob nicUploadJobDBO = new NicUploadJob();
		nicUploadJobDBO.setTransactionId(transactionId);
		if (jobType.contains("RENEW"))
			nicUploadJobDBO.setJobType("REP");
		else
			nicUploadJobDBO.setJobType("REG");
		// nicUploadJobDBO.setJobType(jobType);
		nicUploadJobDBO.setJobPriority(jobPriority);
		nicUploadJobDBO.setJobCreatedTime(new Date());
		nicUploadJobDBO.setCreateBy(officerId);
		nicUploadJobDBO.setCreateWkstnId(wkstnId);
		nicUploadJobDBO.setCreateDatetime(new Date());
		nicUploadJobDBO.setWorkflowJobRunAgainCount(countCall);// Số lần check
																// cpd_afis
		nicUploadJobDBO.setJobReprocessCount(0); //
		// nicUploadJobDBO.getNicTransaction().setPersonCode(personCode);
		try {
			Parameters afisCheck = this.parametersDao
					.findById(new ParametersId("WORKFLOW", "AFIS_ENABLE"));
			if (afisCheck != null
					&& afisCheck.getParaShortValue().equals("false")) {
				nicUploadJobDBO.setAfisCheckStatus("-1");
				nicUploadJobDBO.setAfisRegisterStatus("-1");
				nicUploadJobDBO.setAfisVerifyStatus("-1");
			}
		} catch (Exception e) {
			logger.error("Get paramester fail.");
		}
		try {
			nicUploadJobId = this.save(nicUploadJobDBO);
		} catch (Exception e) {
			return new BaseModelSingle<Long>(
					null,
					false,
					CreateMessageException.createMessageException(e)
							+ " - createWorkflowJobCheck - save UploadJob - thất bại.");
		}

		// logger.debug("[createWorkflowJob] save NicUploadJob, transactionId:{}, type:{}, jobId:{} ",
		// new Object[] { transactionId, jobType, nicUploadJobId });
		return new BaseModelSingle<Long>(nicUploadJobId, true, null);
	}

	@Override
	public BaseModelSingle<NicUploadJob> findNicUpByReceiptNo(String packageId,
			String receiptNo, String transactionId) {
		try {
			DetachedCriteria dCriteria = DetachedCriteria
					.forClass(getPersistentClass());
			dCriteria.createAlias("nicTransaction", "nicTransaction");
			if (!(StringUtils.isBlank(packageId)
					&& StringUtils.isBlank(receiptNo) && StringUtils
						.isBlank(transactionId))) {
				if (!StringUtils.isEmpty(packageId)) {
					dCriteria.createAlias("nicTransaction.listHandovers",
							"handoverName");
					dCriteria.add(Restrictions.eq("handoverName.id.packageId",
							packageId));
				}
				if (StringUtils.isNotEmpty(receiptNo)) {
					dCriteria.add(Restrictions.eq("nicTransaction.recieptNo",
							receiptNo));
				}
				if (StringUtils.isNotEmpty(transactionId)) {
					dCriteria.add(Restrictions.eq("transactionId",
							transactionId));
				}
				List<NicUploadJob> list = this.findAll(dCriteria);
				if (list != null && list.size() > 0)
					return new BaseModelSingle<NicUploadJob>(list.get(0), true,
							null);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return new BaseModelSingle<NicUploadJob>(null, false,
					CreateMessageException.createMessageException(e)
							+ " - findNicUpByReceiptNo - " + receiptNo
							+ " - thất bại.");
		}
		return new BaseModelSingle<NicUploadJob>(null, true, null);
	}

	@Override
	public BaseModelSingle<NicUploadJob> updateJobState(long jobId,
			String state, int command) {
		NicUploadJob nicObj = new NicUploadJob();
		try {

			nicObj = this.findById(jobId);

			switch (command) {

			case 1: {// JOB
				nicObj.setJobCurrentStage(state);
				break;
			}
			case 2: {// CPD
				nicObj.setCpdCheckStatus(state);
				break;
			}

			case 3: {// AFIS register
				nicObj.setAfisRegisterStatus(state);
				break;
			}
			case 4: {// AFIS SCREEN
				nicObj.setAfisCheckStatus(state);
				break;
			}
			case 5: {// PERSO_PREPARE
				// nicObj.setDataPreparationStatus(state);
				break;
			}
			case 6: {// PERSO_SUBMIT
				nicObj.setPersoRegisterStatus(state);
				break;
			}
			case 7: {// SYNC_TX_CPD
				// nicObj.setSyncTxCpdStatus(state);
				break;
			}
			case 8: {// SYNC_INVENTORY
				// nicObj.setSyncInventoryStatus(state);
				break;
			}
			case 9: {// INVESTIGATION
				nicObj.setInvestigationStatus(state);
				if (StringUtils.isNotBlank(state)) {
					String investigationType = "";
					if (StringUtils.equals("03", nicObj.getCpdCheckStatus())) {
						investigationType += "CPD";
					}
					if (StringUtils.equals("03", nicObj.getAfisCheckStatus())) {
						if (StringUtils.isNotBlank(investigationType))
							investigationType += ",";
						investigationType += "AFIS 1:N";
					}
					if (StringUtils.equals("03", nicObj.getAfisVerifyStatus())) {
						if (StringUtils.isNotBlank(investigationType))
							investigationType += ",";
						investigationType += "AFIS 1:1";
					}
					if (StringUtils.equals("03",
							nicObj.getBlacklistCheckStatus())) {
						if (StringUtils.isNotBlank(investigationType))
							investigationType += ",";
						investigationType += "BL";
					}
					if (StringUtils.equals("03",
							nicObj.getIdentificationCheckStatus())) {
						if (StringUtils.isNotBlank(investigationType))
							investigationType += ",";
						investigationType += "ID";
					}
					nicObj.setInvestigationType(investigationType);
				}
				// if(StringUtils.equals(nicObj.getJobCurrentStage(),
				// "CPD_CHECK")){
				// nicObj.setInvestigationType("CPD");
				// } else if (StringUtils.equals(nicObj.getJobCurrentStage(),
				// "AFIS_REGISTER")
				// || StringUtils.equals(nicObj.getJobCurrentStage(),
				// "AFIS_CHECK")
				// || StringUtils.equals(nicObj.getJobCurrentStage(),
				// "AFIS_VERIFY")) {
				// nicObj.setInvestigationType("AFIS");
				// } else {
				// nicObj.setInvestigationType("");
				// }
				break;
			}
			case 10: {// DELETION
				nicObj.setAfisUpdateStatus(state);
				break;
			}
			case 11: {// SYNC_CARD_CPD
				// nicObj.setSyncCardCpdStatus(state);
				break;
			}
			case 12: {// AFIS_VERIFY
				nicObj.setAfisVerifyStatus(state);
				break;
			}
			case 44: {// BLACKLIST
				nicObj.setBlacklistCheckStatus(state);
				break;
			}
			case 45: {// IDENTIFICATION
				nicObj.setIdentificationCheckStatus(state);
				break;
			}
			// case 13:{//CARD_DEACTIVATE
			// nicObj.setCardDeactivateStatus(state);
			// break;
			// }
			}

			nicObj.setUpdateBy(nicCommandUtil.getSystemName()); // need to move
																// to spring
																// config
			nicObj.setUpdateDatetime(new Date());
			// update of end time
			nicObj.setJobEndTime(new Date());
			nicObj.setUpdateWkstnId(nicCommandUtil.getHostName());
			this.saveOrUpdate(nicObj);
			// log("result t : "+nicObj+"\n"+ReflectionToStringBuilder.toString(nicObj,
			// ToStringStyle.MULTI_LINE_STYLE));

		} catch (Exception e) {
			return new BaseModelSingle<NicUploadJob>(nicObj, false,
					CreateMessageException.createMessageException(e)
							+ " - updateJobState - " + jobId + " - thất bại.");
		}

		return new BaseModelSingle<NicUploadJob>(nicObj, true, null);
	}

	@Override
	public BaseModelSingle<Void> updateJobStatus(long jobId, String status) {
		NicUploadJob nicObj = new NicUploadJob();

		try {
			nicObj = this.findById(jobId);
			nicObj.setJobCurrentStatus(status);
			this.saveOrUpdate(nicObj);
			return new BaseModelSingle<Void>(null, true, null);
		} catch (Exception e) {
			return new BaseModelSingle<Void>(null, false,
					CreateMessageException.createMessageException(e)
							+ " - updateJobStatus - " + jobId + " - thất bại");
		}
	}

	@Override
	public BaseModelSingle<Void> saveOrUpdateService(NicUploadJob nicUp) {
		try {
			this.saveOrUpdate(nicUp);
			return new BaseModelSingle<Void>(null, true, null);
		} catch (Exception e) {
			logger.info("getTransactionJobsInQueue exception:" + e.getMessage());
			return new BaseModelSingle<Void>(
					null,
					false,
					CreateMessageException.createMessageException(e)
							+ " - saveOrUpdateService - NicUploadJob - thất bại.");
		}
	}

	@Override
	public Long createWorkflowJobNoRun(String transactionId, String jobType,
			Integer jobPriority, int countCall, String personCode)
			throws Exception {
		Long nicUploadJobId = null;

		String officerId = SYSTEM_EPP;
		String wkstnId = SYSTEM_EPP;
		try {
			java.net.InetAddress localMachine = InetAddress.getLocalHost();
			wkstnId = localMachine.getHostName().toUpperCase();
		} catch (Exception e) {
			throw new Exception(
					CreateMessageException.createMessageException(e)
							+ " - createWorkflowJobCheck - thất bại.");
		}

		NicUploadJob nicUploadJobDBO = new NicUploadJob();
		nicUploadJobDBO.setTransactionId(transactionId);
		if (jobType.contains("RENEW"))
			nicUploadJobDBO.setJobType("REP");
		else
			nicUploadJobDBO.setJobType("REG");
		// nicUploadJobDBO.setJobType(jobType);
		nicUploadJobDBO.setJobPriority(jobPriority);
		nicUploadJobDBO.setJobCreatedTime(new Date());
		nicUploadJobDBO.setCreateBy(officerId);
		nicUploadJobDBO.setCreateWkstnId(wkstnId);
		nicUploadJobDBO.setCreateDatetime(new Date());
		nicUploadJobDBO.setWorkflowJobRunAgainCount(countCall);// Số lần check
																// cpd_afis
		nicUploadJobDBO.setJobReprocessCount(0); //
		nicUploadJobDBO.setAfisCheckStatus("-1");
		nicUploadJobDBO.setAfisRegisterStatus("-1");
		nicUploadJobDBO.setAfisVerifyStatus("-1");
		
		nicUploadJobDBO.setJobCurrentStage(NicUploadJob.JOB_STATE_PERSO_REGISTER);
		nicUploadJobDBO.setJobCurrentStatus(NicUploadJob.JOB_STATUS_PERSO_PRINTED);
		nicUploadJobDBO.setJobStartTime(new Date());
		nicUploadJobDBO.setJobEndTime(new Date());
		nicUploadJobDBO.setInvestigationStatus(NicUploadJob.RECORD_STATUS_APPROVE_PERSO);
		
		try {
			nicUploadJobId = this.save(nicUploadJobDBO);
		} catch (Exception e) {
			throw new Exception(
					CreateMessageException.createMessageException(e)
							+ " - createWorkflowJobCheck - save UploadJob - thất bại.");
		}

		// logger.debug("[createWorkflowJob] save NicUploadJob, transactionId:{}, type:{}, jobId:{} ",
		// new Object[] { transactionId, jobType, nicUploadJobId });
		return nicUploadJobId;
	}

	@Override
	public PaginatedResult<NicUploadJob> getPageJobsInQueue(
			String[] listTranId, int pageNo, int pageSize, Map<String, String> configMap) {
		DetachedCriteria criteria = DetachedCriteria
				.forClass(NicUploadJob.class);
		String jobInvStatus = configMap.get(PARA_NAME_VALID_JOB_INV_STATUS);
		String[] investigationStatus = jobInvStatus.split(",");
		if (investigationStatus == null) {
			investigationStatus = new String[] { "02,40" };
		}
		Criterion nullJobCurrentState = Restrictions.isNull("jobCurrentStage");
		Criterion blankJobCurrentState = Restrictions.eq("jobCurrentStage", "");

		Criterion isjobApprove = Restrictions.eq("jobApproveStatus", "1");
		Criterion notnulljobApprove = Restrictions
				.isNotNull("jobApproveStatus");
		Criterion inInvestigationStatus = Restrictions.in(
				"investigationStatus", investigationStatus);
		Criterion nullAfisDeleteStatus = Restrictions
				.isNull("afisUpdateStatus");
		Criterion investigationStatusNull = Restrictions
				.isNull("investigationStatus");

		int reprocessMax = 3;
		String minReprocessCountVal = configMap.get("MIN_REPROCESS_COUNT");
		if (StringUtils.isNotBlank(minReprocessCountVal)) {
			reprocessMax = Integer.valueOf(minReprocessCountVal);
		}

		Criterion canReprocess = Restrictions.or(
				Restrictions.isNull("jobReprocessCount"),
				Restrictions.lt("jobReprocessCount", reprocessMax));

		Criterion hasError = this.getErrorConditionCriterion();

		criteria.add(
		Restrictions.or(Restrictions.or(
				Restrictions.or(nullJobCurrentState, blankJobCurrentState),
				investigationStatusNull), // , tempPPno
				Restrictions.and(inInvestigationStatus, nullAfisDeleteStatus,
						isjobApprove, notnulljobApprove), Restrictions.and(
						hasError, canReprocess)));

		criteria.addOrder(Order.desc("jobPriority")); // 2016 Jan 19
		criteria.addOrder(Order.asc("jobReprocessCount"));
		criteria.addOrder(Order.asc("workflowJobId"));
		if (listTranId.length > 1) {
			criteria.add(Restrictions.in("transactionId", listTranId));
		}
		PaginatedResult<NicUploadJob> result = this.findAllForPagination(criteria, pageNo, pageSize);
		if (result != null && result.getRows().size() > 0) {
//			result.setRows(cleanReprocsessTxn(result.getRows(), configMap));
		} else {
			logger.info("empty results");
		}
		return result;
	}

}
