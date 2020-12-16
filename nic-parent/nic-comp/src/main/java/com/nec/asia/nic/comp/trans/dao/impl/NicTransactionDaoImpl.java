package com.nec.asia.nic.comp.trans.dao.impl;

import java.math.BigDecimal;
import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.TimeUnit;

import oracle.jdbc.OracleTypes;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.hibernate.HibernateException;
import org.hibernate.LockMode;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.criterion.Conjunction;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Disjunction;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.internal.SessionImpl;
import org.hibernate.type.StringType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.nec.asia.nic.comp.job.dto.LogInfoDTO;
import com.nec.asia.nic.comp.trans.dao.NicRegistrationDataDao;
import com.nec.asia.nic.comp.trans.dao.NicTransactionDao;
import com.nec.asia.nic.comp.trans.domain.BaseModelList;
import com.nec.asia.nic.comp.trans.domain.BaseModelSingle;
import com.nec.asia.nic.comp.trans.domain.NicRegistrationData;
import com.nec.asia.nic.comp.trans.domain.NicTransaction;
import com.nec.asia.nic.comp.trans.domain.NicTransactionLog;
import com.nec.asia.nic.comp.trans.domain.type.TransactionStatus;
import com.nec.asia.nic.comp.trans.dto.CountPassport;
import com.nec.asia.nic.comp.trans.dto.Datadto;
import com.nec.asia.nic.comp.trans.dto.NicUploadJobDto;
import com.nec.asia.nic.comp.trans.dto.RICTxnRptDto;
import com.nec.asia.nic.comp.trans.dto.ReportRegProcessData;
import com.nec.asia.nic.comp.trans.utils.MSReportDefinition;
import com.nec.asia.nic.comp.trans.utils.MultiSeriesReport;
import com.nec.asia.nic.comp.trans.utils.ReportParameter;
import com.nec.asia.nic.comp.ws.log.domain.CreateMessageException;
import com.nec.asia.nic.framework.PageRequest;
import com.nec.asia.nic.framework.PaginatedResult;
import com.nec.asia.nic.framework.dao.DaoException;
import com.nec.asia.nic.framework.dao.impl.GenericHibernateDao;
import com.nec.asia.nic.util.TransLogInfoXmlConvertor;
import com.nec.asia.nic.utils.DateUtil;
import com.nec.asia.nic.utils.HelperClass;

/* 
 * Modification History:
 *  
 * 05 Sep 2013 (chris): change field name sex to gender.
 * 17 Dec 2013 (chris): to add validation in findAllByTransIdList()
 * 20 Dec 2013 (chris): to limit by 500 records per query for findAllByTransIdList. 
 * 20 Dec 2013 (chris): add method getTransactionStatusByIdForUpdate(). 
 * 06 Jan 2013 (chris): fix lock error if transaction is null.
 * 16 jan 2014 (priya):added exception and ricste check for getRicRejTxnRptRecordList 
 * 10 feb 2014 (priya):  changes made in getRicRejTxnRptRecordList method
 * 13 Jan 2014 (chris): fix null for updateStatusByTransactionId().
 * 06 Jun 2014 (chris): remove limit by 500 records per query for findAllByTransIdList.
 * 09 May 2016 (khang): add method updateStatusByTxnIdList
 * 11 May 2016 (tue): add methods:  getCollectedTransByEstDate, getPendingTransByEstDate, getAllTransByEstDate, getAllByStatusAndEstDate
 * 
 */

@Repository("transactionDao")
public class NicTransactionDaoImpl extends
		GenericHibernateDao<NicTransaction, String> implements
		NicTransactionDao {

	@Autowired
	NicRegistrationDataDao registrationDataDao;

	private static final String TRANSACTION_STATUS_APPROVE = TransactionStatus.Verified
			.getKey();// "NIC_APPROVED";
	private static final String TRANSACTION_STATUS_REJECT = TransactionStatus.Rejected
			.getKey();// "NIC_REJECTED";
	private static final String TRANSACTION_STATUS_SUSPEND = TransactionStatus.Suspended
			.getKey();// "NIC_SUSPEND";
	private static final int MAX_RECORDS = 500;

	public static TransLogInfoXmlConvertor util = new TransLogInfoXmlConvertor();

	@Override
	public List<NicTransaction> findAllByTransIdList(List<String> transIds) {
		List<NicTransaction> nicTransactionDBOList = new ArrayList<NicTransaction>();

		DetachedCriteria detachedCriteria = DetachedCriteria
				.forClass(getPersistentClass());
		if (CollectionUtils.isNotEmpty(transIds)) {
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

			List<NicTransaction> resultTransactions = this
					.findAll(detachedCriteria);
			nicTransactionDBOList.addAll(resultTransactions);
		}
		logger.info("[findAllByTransIdList][{}] size:{}", new Object[] {
				transIds, nicTransactionDBOList.size() });
		return nicTransactionDBOList;
	}

	public List<NicTransaction> findAllByNin(String nin) {
		List<NicTransaction> nicTransactionDBOList = new ArrayList<NicTransaction>();
		DetachedCriteria detachedCriteria = DetachedCriteria
				.forClass(getPersistentClass());
		if (StringUtils.isNotEmpty(nin)) {
			detachedCriteria.add(Restrictions.eq("nin", nin));
			List<NicTransaction> resultTransactions = this
					.findAll(detachedCriteria);
			nicTransactionDBOList.addAll(resultTransactions);
		}
		logger.info("[findAllByNin][{}] size:{}", new Object[] { nin,
				nicTransactionDBOList.size() });
		return nicTransactionDBOList;
	}

	/* Created by Sailaja */
	@Override
	public List<NicTransaction> findAllByTransTypeList(String txnType) {
		List<NicTransaction> nicTransactionDBOList = new ArrayList<NicTransaction>();
		DetachedCriteria detachedCriteria = DetachedCriteria
				.forClass(getPersistentClass());
		if (StringUtils.isNotEmpty(txnType)) {
			detachedCriteria.add(Restrictions.eq("transactionType", txnType));
			List<NicTransaction> resultTransactions = this
					.findAll(detachedCriteria);
			nicTransactionDBOList.addAll(resultTransactions);
		}
		logger.info("[findAllByTransTypeList][{}] size:{}", new Object[] {
				txnType, nicTransactionDBOList.size() });
		return nicTransactionDBOList;
	}

	public List<NicTransaction> findAllByFields(String surname,
			String firstName, String middleName, String sex, String dob) {
		List<NicTransaction> nicTransactionDBOList = new ArrayList<NicTransaction>();
		List<Object> paraList = new ArrayList<Object>();
		String hql = "";
		// "from NicTransaction a ";
		// "where a.transactionId in (select b.transactionId from NicRegistrationData b "
		// +
		// "where nin like ? and surnameFull like ? and firstnameFull like ? and surnameBirthFull like ? and sex like ?) ";
		String hql2 = "";

		if (StringUtils.isNotBlank(surname)) {
			if (StringUtils.isNotBlank(hql2))
				hql2 += " and";
			hql2 += " upper(surnameFull) like ?";
			paraList.add(surname.toUpperCase());
		}
		if (StringUtils.isNotBlank(firstName)) {
			if (StringUtils.isNotBlank(hql2))
				hql2 += " and";
			hql2 += " upper(firstnameFull) like ?";
			paraList.add(firstName.toUpperCase());
		}
		if (StringUtils.isNotBlank(middleName)) {
			if (StringUtils.isNotBlank(hql2))
				hql2 += " and";
			hql2 += " upper(middlenameFull) like ?";
			paraList.add(middleName.toUpperCase());
		}
		if (StringUtils.isNotBlank(sex)) {
			if (StringUtils.isNotBlank(hql2))
				hql2 += " and";
			hql2 += " upper(gender) like ?";
			paraList.add(sex.toUpperCase());
		}
		if (StringUtils.isNotBlank(dob)) {
			if (StringUtils.isNotBlank(hql2))
				hql2 += " and";
			hql2 += " to_char(dateOfBirth, 'dd-mm-yyyy') = ?";
			paraList.add(dob);
		}

		if (StringUtils.isNotBlank(hql2)) {
			hql = "from NicTransaction a where a.transactionId in "
					+ "(select b.transactionId from NicRegistrationData b where "
					+ hql2 + ") ";
			logger.info("[NicTransactionDao.findAllByFields] hql: {}", hql);
		}
		Object[] parameters = paraList.toArray();
		List<NicTransaction> resultTransactions = (List<NicTransaction>) this
				.getHibernateTemplate().find(hql, parameters);
		if (resultTransactions != null) {
			nicTransactionDBOList.addAll(resultTransactions);
		}
		logger.info("[findAllByFields][{}] size:{}", new Object[] { hql2,
				nicTransactionDBOList.size() });
		return nicTransactionDBOList;
	}

	public String getTransactionStatusById(String transactionId) {
		String transactionStatus = null;

		String hql = "select a.transactionStatus from NicTransaction a where a.transactionId = ? ";
		Object[] parameters = { transactionId };
		List<String> transactionStatusList = (List<String>) this
				.getHibernateTemplate().find(hql, parameters);
		if (CollectionUtils.isNotEmpty(transactionStatusList)) {
			transactionStatus = transactionStatusList.get(0);
		}
		return transactionStatus;
	}

	public String getTransactionStatusByIdForUpdate(String transactionId) {
		String transactionStatus = null;

		NicTransaction nicTransactionDBO = this.findById(transactionId);
		if (nicTransactionDBO != null) {
			this.getHibernateTemplate().lock(nicTransactionDBO,
					LockMode.PESSIMISTIC_WRITE); // LockMode.UPGRADE);
			transactionStatus = nicTransactionDBO.getTransactionStatus();
		}
		return transactionStatus;
	}

	@Override
	public void updateStatusByTransactionId(String transactionId,
			String status, String officerId, String workstationId) {
		NicTransaction nicTransactionDBO = this.findById(transactionId);
		if (nicTransactionDBO != null) {
			nicTransactionDBO.setTransactionStatus(status);
			nicTransactionDBO.setUpdateBy(officerId);
			nicTransactionDBO.setUpdateWkstnId(workstationId);
			nicTransactionDBO.setUpdateDatetime(new Date());
			this.saveOrUpdate(nicTransactionDBO);
		}
	}

	@Override
	public void updateOnApprove(String transactionId, String userId,
			String wkstnId) {
		logger.info(
				"Inside Dao Impl========>>>>>Approve Card transactionId-{}, userId-{}, wkstnId-{}",
				new Object[] { transactionId, userId, wkstnId });

		DetachedCriteria criteria = DetachedCriteria
				.forClass(getPersistentClass());
		criteria.add(Restrictions.eq("transactionId", transactionId));
		List<NicTransaction> list = (List<NicTransaction>) getHibernateTemplate()
				.findByCriteria(criteria);
		try {
			if (list != null) {
				for (NicTransaction updateObj : list) {
					updateObj.setTransactionStatus(TRANSACTION_STATUS_APPROVE);
					updateObj.setUpdateBy(userId);
					updateObj.setUpdateWkstnId(wkstnId);
					updateObj.setUpdateDatetime(new Date());
					saveOrUpdate(updateObj);
				}
			}
		} catch (Exception e) {
			logger.error(
					"[updateOnApprove] transactionId-{}, userId-{}, wkstnId-{}",
					new Object[] { transactionId, userId, wkstnId }, e);
		}
	}

	@Override
	public void updateOnReject(String transactionId, String userId,
			String wkstnId) {
		logger.info(
				"Inside Dao Impl========>>>>>Reject Card transactionId-{}, userId-{}, wkstnId-{}",
				new Object[] { transactionId, userId, wkstnId });
		DetachedCriteria criteria = DetachedCriteria
				.forClass(getPersistentClass());
		criteria.add(Restrictions.eq("transactionId", transactionId));
		List<NicTransaction> list = (List<NicTransaction>) getHibernateTemplate()
				.findByCriteria(criteria);
		try {
			if (list != null) {
				for (NicTransaction updateObj : list) {
					updateObj.setTransactionStatus(TRANSACTION_STATUS_REJECT);
					updateObj.setUpdateBy(userId);
					updateObj.setUpdateWkstnId(wkstnId);
					updateObj.setUpdateDatetime(new Date());
					saveOrUpdate(updateObj);
				}
			}
		} catch (Exception e) {
			logger.error(
					"[updateOnReject] transactionId-{}, userId-{}, wkstnId-{}",
					new Object[] { transactionId, userId, wkstnId }, e);
		}

	}

	@Override
	public PaginatedResult<Object[]> findAllBySearchParam(
			PageRequest pageRequest, String siteCode, String tranasctionId,
			String surname, String maidenName, String otherName, String gender,
			String dateOfBirth, String status) {
		int count = 0;
		String sortname = pageRequest.getSortname();
		String sortorder = pageRequest.getSortorder();
		int pageNo = (pageRequest.getFirstRowIndex() / pageRequest
				.getMaxRecords()) + 1;
		String orderByPhrase = "";
		if (sortname != null && "".equals(sortname) == false) {
			orderByPhrase = " ORDER BY " + sortname + " " + sortorder;
		} else {
			orderByPhrase = " ORDER BY T.updateDate DESC";
		}

		Date dob = DateUtil.strToDate(dateOfBirth, "dd-MMM-yyyy");
		dateOfBirth = DateUtil.parseDate(dob, DateUtil.FORMAT_DDMMYYYY);

		String select = "SELECT T.transactionId ";
		String from = " FROM NicTransaction T, NicRegistrationData R ";
		String where = " WHERE 1=1 AND R.transactionId = T.transactionId ";
		if (pageRequest.isCalculateRecordCount()) {
			String countSql = select
					+ from
					+ where
					+ " AND (:siteCode IS NULL OR T.regSiteCode = :siteCode)"
					+ " AND (:tranasctionId IS NULL OR T.transactionId = :tranasctionId)"
					+ " AND (:status IS NULL OR T.transactionStatus = :status)"
					+ " AND (:surname IS NULL OR R.surnameFull = :surname)"
					+ " AND (:maidenName IS NULL OR  R.surnameBirthFull = :maidenName)"
					+ " AND (:otherName IS NULL OR R.optionSurname = :otherName)"
					+ " AND (:gender IS NULL OR R.gender = :gender)"
					+ " AND (:dateOfBirth IS NULL OR to_char(R.dateOfBirth, 'ddmmyyyy') = :dateOfBirth)"
					+ "";

			List<String> params = new ArrayList<String>();
			params.add("siteCode");
			params.add("tranasctionId");
			params.add("status");
			params.add("surname");
			params.add("maidenName");
			params.add("otherName");
			params.add("gender");
			params.add("dateOfBirth");

			List<String> values = new ArrayList<String>();
			values.add(siteCode);
			values.add(tranasctionId);
			values.add(status);
			values.add(surname);
			values.add(maidenName);
			values.add(otherName);
			values.add(gender);
			values.add(dateOfBirth);

			count = this
					.getHibernateTemplate()
					.findByNamedParam(countSql, params.toArray(new String[8]),
							values.toArray()).size();
		}

		List<Object[]> result = new ArrayList<Object[]>();
		if (count > 0) {

			select = " SELECT T.transactionId, T.queueNumber, T.applnRefNo, T.nin, T.dateOfApplication, T.regSiteCode, T.issSiteCode, "
					+ "T.transactionType, T.transactionSubtype, T.transactionStatus, T.createBy, T.createWkstnId, T.createDate, "
					+ "T.updateBy, T.updateWkstnId, T.updateDate, T.dateOfIssue, "
					+ "R.firstnameFull, R.surnameFull, R.surnameBirthFull, R.optionSurname, R.gender, R.dateOfBirth ";

			String sql = select
					+ from
					+ where
					+ " AND (:siteCode IS NULL OR T.regSiteCode = :siteCode)"
					+ " AND (:tranasctionId IS NULL OR T.transactionId = :tranasctionId)"
					+ " AND (:status IS NULL OR T.transactionStatus = :status)"
					+ " AND (:surname IS NULL OR R.surnameFull = :surname)"
					+ " AND (:maidenName IS NULL OR  R.surnameBirthFull = :maidenName)"
					+ " AND (:otherName IS NULL OR R.optionSurname = :otherName)"
					+ " AND (:gender IS NULL OR R.gender = :gender)"
					+ " AND (:dateOfBirth IS NULL OR to_char(R.dateOfBirth, 'ddmmyyyy') = :dateOfBirth)"
					+ " AND rownum between :firstRow and :lastRow "
					+ orderByPhrase;

			/*
			 * SQLQuery sqlQuery = this.getSession().createSQLQuery(sql);
			 * sqlQuery.setParameter("siteCode", siteCode);
			 * sqlQuery.setParameter("tranasctionId", tranasctionId);
			 * sqlQuery.setParameter("status", status);
			 * sqlQuery.setParameter("surname", surname);
			 * sqlQuery.setParameter("maidenName", maidenName);
			 * sqlQuery.setParameter("otherName", otherName);
			 * sqlQuery.setParameter("gender", gender);
			 * sqlQuery.setParameter("dateOfBirth", dateOfBirth);
			 * sqlQuery.setParameter("firstRow", pageRequest.getFirstRowIndex()
			 * + 1); sqlQuery.setParameter("lastRow",
			 * (pageRequest.getFirstRowIndex() + pageRequest .getMaxRecords()));
			 * result = sqlQuery.list();
			 */

			List<String> params = new ArrayList<String>();
			params.add("siteCode");
			params.add("tranasctionId");
			params.add("status");
			params.add("surname");
			params.add("maidenName");
			params.add("otherName");
			params.add("gender");
			params.add("dateOfBirth");
			params.add("firstRow");
			params.add("lastRow");

			List<Object> values = new ArrayList<Object>();
			values.add(siteCode);
			values.add(tranasctionId);
			values.add(status);
			values.add(surname);
			values.add(maidenName);
			values.add(otherName);
			values.add(gender);
			values.add(dateOfBirth);
			values.add(Long.valueOf(pageRequest.getFirstRowIndex() + 1));
			values.add(Long.valueOf(pageRequest.getFirstRowIndex()
					+ pageRequest.getMaxRecords()));

			result = (List<Object[]>) this.getHibernateTemplate()
					.findByNamedParam(sql, params.toArray(new String[10]),
							values.toArray());

		}

		return new PaginatedResult<Object[]>(count, pageNo, result);
	}

	@Override
	public PaginatedResult<NicTransaction> findAllForPaginationByFilter(
			NicTransaction nicTransaction, int pageNo, int pageSize,
			boolean ignoreCase, boolean enableLike, Order order) {
		DetachedCriteria rCriteria = null;
		NicRegistrationData nicRegistration = nicTransaction
				.getNicRegistrationData();
		if (nicRegistration != null) {
			if (StringUtils.isNotEmpty(nicRegistration.getSurnameFull())) {
				rCriteria = DetachedCriteria
						.forClass(NicRegistrationData.class);
				rCriteria.add(Restrictions.ilike("surname", nicRegistration
						.getSurnameFull().toLowerCase()));
			}
			// if
			// (StringUtils.isNotEmpty(nicRegistration.getSurnameBirthFull())) {
			// if (rCriteria == null)
			// DetachedCriteria.forClass(NicRegistrationData.class);
			// rCriteria.add(Restrictions.ilike("surnameBirth",
			// nicRegistration.getSurnameBirthFull().toLowerCase()));
			// }
			// if (StringUtils.isNotEmpty(nicRegistration.getOptionSurname())) {
			// if (rCriteria == null)
			// DetachedCriteria.forClass(NicRegistrationData.class);
			// rCriteria.add(Restrictions.ilike("optionSurname",
			// nicRegistration.getOptionSurname().toLowerCase()));
			// }
			if (StringUtils.isNotEmpty(nicRegistration.getGender())) {
				if (rCriteria == null)
					DetachedCriteria.forClass(NicRegistrationData.class);
				rCriteria.add(Restrictions.ilike("gender", nicRegistration
						.getGender().toLowerCase()));
			}
			if (nicRegistration.getDateOfBirth() != null) {
				if (rCriteria == null)
					DetachedCriteria.forClass(NicRegistrationData.class);
				rCriteria.add(Restrictions.eq("dateOfBirth",
						nicRegistration.getDateOfBirth()));
			}

		}

		DetachedCriteria tCriteria = DetachedCriteria
				.forClass(NicTransaction.class);

		// if (StringUtils.isNotEmpty(nicTransaction.getNin())) {
		// tCriteria.add(Restrictions.ilike("nin", nicTransaction.getNin()
		// .toLowerCase()));
		// }
		if (StringUtils.isNotEmpty(nicTransaction.getTransactionStatus())) {
			tCriteria.add(Restrictions.ilike("transactionStatus",
					nicTransaction.getTransactionStatus().toLowerCase()));
		}

		if (rCriteria != null) {
			// tCriteria.add(rCriteria);
		}

		return findAllForPagination(tCriteria, pageNo, pageSize, order);
	}

	@Override
	public List<RICTxnRptDto> getRicRejTxnRptRecordList(
			RICTxnRptDto ricRejTxnRptCriteria) throws DaoException {
		logger.info("Entering into Dao");
		LogInfoDTO logDTO = null;
		Format formatter = new SimpleDateFormat("dd-MMM-yyyy");
		List<RICTxnRptDto> records = new ArrayList<RICTxnRptDto>();
		DetachedCriteria criteria = DetachedCriteria
				.forClass(NicTransaction.class);
		criteria.add(Restrictions.eq("transactionStatus", "NIC_REJECTED"));
		if (ricRejTxnRptCriteria.getTxnRejStartDate() != null) {
			criteria.add(Restrictions.ge("updateDate",
					ricRejTxnRptCriteria.getTxnRejStartDate()));
		}
		if (ricRejTxnRptCriteria.getTxnRejEndDate() != null) {
			criteria.add(Restrictions.le("updateDate",
					ricRejTxnRptCriteria.getTxnRejEndDate()));
		}
		if (ricRejTxnRptCriteria.getTxnStartDate() != null) {
			criteria.add(Restrictions.ge("dateOfApplication",
					ricRejTxnRptCriteria.getTxnStartDate()));
		}
		if (ricRejTxnRptCriteria.getTxnEndDate() != null) {
			criteria.add(Restrictions.le("dateOfApplication",
					ricRejTxnRptCriteria.getTxnEndDate()));
		}
		if (ricRejTxnRptCriteria.getTxnType() != null
				&& !"ALL".equals(ricRejTxnRptCriteria.getTxnType())) {
			criteria.add(Restrictions.eq("transactionType",
					ricRejTxnRptCriteria.getTxnType()));
		}
		if (ricRejTxnRptCriteria.getTxnSubType() != null
				&& !"ALL".equals(ricRejTxnRptCriteria.getTxnSubType())) {
			criteria.add(Restrictions.eq("transactionSubtype",
					ricRejTxnRptCriteria.getTxnSubType()));
		}
		if (ricRejTxnRptCriteria.getRicOffice() != null
				&& "ALL".equals(ricRejTxnRptCriteria.getRicOffice())) {
			criteria.add(Restrictions.like("regSiteCode", "RIC",
					MatchMode.START).ignoreCase());
		} else if (ricRejTxnRptCriteria.getRicOffice() != null
				&& !"ALL".equals(ricRejTxnRptCriteria.getRicOffice())) {
			criteria.add(Restrictions.eq("regSiteCode",
					ricRejTxnRptCriteria.getRicOffice()));
		}
		criteria.addOrder(Order.asc("applnRefNo"));
		List<NicTransaction> list = this.findAll(criteria);
		logger.info("DAO Result >>" + list.size());
		if (list != null && list.size() > 0) {
			for (NicTransaction trans : list) {
				RICTxnRptDto dto = new RICTxnRptDto();
				dto.setTransactionNo(trans.getApplnRefNo());
				// dto.setNin(trans.getNin());
				// dto.setTxnSubType(trans.getTransactionSubtype());
				dto.setTxnType(trans.getTransactionType());
				dto.setTxnServedBy(trans.getUpdateBy());
				dto.setApplicationDate(formatter.format(trans
						.getDateOfApplication()));
				if (dto.getLastModifiedDate() != null) {
					dto.setLastModifiedDate(formatter.format(trans
							.getUpdateDatetime()));
				}
				DetachedCriteria criteria1 = DetachedCriteria
						.forClass(NicRegistrationData.class);
				criteria1.add(Restrictions.eq("transactionId",
						trans.getApplnRefNo()));
				List<NicRegistrationData> regData = (List<NicRegistrationData>) this
						.getHibernateTemplate().findByCriteria(criteria1);
				if (regData != null && regData.size() > 0) {
					dto.setFirstName(regData.get(0).getFirstnameFull());
					dto.setSurName(regData.get(0).getSurnameFull());
					// dto.setSurNameBirth(regData.get(0).getSurnameBirthFull());
				}
				DetachedCriteria criteria2 = DetachedCriteria
						.forClass(NicTransactionLog.class);
				criteria2.add(Restrictions.eq("refId", trans.getApplnRefNo()));
				criteria.setProjection(Projections.projectionList()
						.add(Projections.groupProperty("refId"))
						.add(Projections.max("logId")));
				criteria2.add(Restrictions.eq("transactionStatus",
						trans.getTransactionStatus()));
				logger.info("Rejection reason >>"
						+ ricRejTxnRptCriteria.getReason());
				if (ricRejTxnRptCriteria.getReason() != null
						&& !"ALL".equals(ricRejTxnRptCriteria.getReason())) {
					criteria2
							.add(Restrictions
									.sqlRestriction("extractvalue(sys.xmltype(log_info), '//logInfoDTO/Reason/text()') ='"
											+ ricRejTxnRptCriteria.getReason()
											+ "'"));
				}
				criteria2
						.add(Restrictions
								.sqlRestriction("extractvalue(sys.xmltype(log_info), '//logInfoDTO/Reason/text()') is not null"));
				criteria2.add(Restrictions.isNotNull("transactionStatus"));
				criteria2.add(Restrictions.isNotNull("logInfo"));
				List<NicTransactionLog> logInfoData = (List<NicTransactionLog>) this
						.getHibernateTemplate().findByCriteria(criteria2);
				if (logInfoData != null && logInfoData.size() > 0) {
					String logInfo = logInfoData.get(0).getLogInfo();
					try {
						if (logInfo != null) {
							logDTO = (LogInfoDTO) util.unmarshal(logInfo);
							dto.setRemarks(logDTO.getRemark());
							dto.setReason(logDTO.getReason());
						} else {
							dto.setRemarks("");
							dto.setReason("");
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
					records.add(dto);
				}
			}
		}
		logger.info("Registration Data >>" + records.size());
		return records;
	}

	@Override
	public List<Object[]> getNicTransactionStatsBySite(String regSiteCode) {
		List<Object[]> resultList = null;
		Session session = null;
		try {
			session = getHibernateTemplate().getSessionFactory().openSession();
			Query q = session.getNamedQuery("getNicTransactionStatsBySite");
			q.setString("regSiteCode", regSiteCode);
			resultList = q.list();
		} catch (HibernateException e) {
			e.printStackTrace();
		} finally {
			session.close();
		}
		return resultList;
	}

	@Override
	public List<Object[]> getNicTransactionStatsBySiteAndDate(
			String regSiteCode, Date dateFrom, Date dateTo) {
		List<Object[]> resultList = null;
		Session session = null;
		try {
			if (dateTo != null) {
				dateTo = DateUtil.getEndOfDay(dateTo);
			}
			session = getHibernateTemplate().getSessionFactory().openSession();
			Query q = session
					.getNamedQuery("getNicTransactionStatsBySiteAndDate");
			q.setString("regSiteCode", regSiteCode);
			q.setDate("dateFrom", dateFrom);
			q.setDate("dateTo", dateTo);
			resultList = q.list();
		} catch (HibernateException e) {
			e.printStackTrace();
		} finally {
			session.close();
		}
		return resultList;
	}

	@Override
	public List<Object[]> getNicTransactionStatusStats(String regSiteCode,
			Date dateFrom, Date dateTo) {
		List<Object[]> resultList = null;
		Session session = null;
		try {
			if (dateTo != null) {
				dateTo = DateUtil.getEndOfDay(dateTo);
			}

			session = getHibernateTemplate().getSessionFactory().openSession();
			Query q = session.getNamedQuery("getNicTransactionStatusStats");
			q.setString("regSiteCode", regSiteCode);
			q.setDate("dateFrom", dateFrom);
			q.setDate("dateTo", dateTo);
			resultList = q.list();
		} catch (HibernateException e) {
			e.printStackTrace();
		} finally {
			session.close();
		}
		return resultList;
	}

	@Override
	public List<Object[]> getNicTransactionStatusStatsByDate(
			String regSiteCode, Date dateFrom, Date dateTo) {
		List<Object[]> resultList = null;
		Session session = null;
		try {
			if (dateTo != null) {
				dateTo = DateUtil.getEndOfDay(dateTo);
			}
			session = getHibernateTemplate().getSessionFactory().openSession();
			Query q = session
					.getNamedQuery("getNicTransactionStatusStatsByDate");
			q.setString("regSiteCode", regSiteCode);
			q.setDate("dateFrom", dateFrom);
			q.setDate("dateTo", dateTo);
			resultList = q.list();
		} catch (HibernateException e) {
			e.printStackTrace();
		} finally {
			session.close();
		}
		return resultList;
	}

	@Override
	public List<Object[]> getNicMainStatsByStatus(String cardStatus) {
		List<Object[]> resultList = null;
		Session session = null;
		try {
			session = getHibernateTemplate().getSessionFactory().openSession();
			Query q = session.getNamedQuery("getNicMainStatsByStatus");
			q.setParameter("cardStatus", cardStatus);
			resultList = q.list();
		} catch (HibernateException e) {
			e.printStackTrace();
		} finally {
			session.close();
		}
		return resultList;
	}

	@Override
	public List<Object[]> getTxnAgeTypeGenderSiteStats(Date dateFrom,
			Date dateTo) {
		List<Object[]> resultList = null;
		Session session = null;
		try {
			if (dateTo != null) {
				dateTo = DateUtil.getEndOfDay(dateTo);
			}

			session = getHibernateTemplate().getSessionFactory().openSession();
			Query q = session
					.getNamedQuery("getNicTransactionStatsByAgeTypeGenderSite");
			q.setDate("dateFrom", dateFrom);
			q.setDate("dateTo", dateTo);
			resultList = q.list();
		} catch (HibernateException e) {
			e.printStackTrace();
		} finally {
			session.close();
		}
		return resultList;
	}

	@Override
	public List<Object[]> getTxnDaySiteTotal(String transctionStatus,
			String transactionType, String transactionSubtype, Date dateFrom,
			Date dateTo) {
		List<Object[]> resultList = null;
		Session session = null;
		try {
			if (dateTo != null) {
				dateTo = DateUtil.getEndOfDay(dateTo);
			}

			session = getHibernateTemplate().getSessionFactory().openSession();
			Query q = session.getNamedQuery("txnDaySiteTotal");
			q.setParameter("transctionStatus", transctionStatus);
			q.setParameter("transactionType", transactionType);
			q.setParameter("transactionSubtype", transactionSubtype);
			q.setDate("dateFrom", dateFrom);
			q.setDate("dateTo", dateTo);
			resultList = q.list();
		} catch (HibernateException e) {
			e.printStackTrace();
		} finally {
			session.close();
		}
		return resultList;
	}

	@Override
	public List getCardConvChartData(Date fromDate, Date toDate, String[] sites)
			throws Exception {
		Session session = null;
		try {
			session = getHibernateTemplate().getSessionFactory().openSession();
			Query query = session.getNamedQuery("report.getCardConvChartData");
			query.setParameter("fromDate", fromDate);
			query.setParameter("toDate", toDate);
			query.setParameterList("sites", sites);

			List results = query.list();

			return results;
		} catch (Exception ex) {
			logger.error("Error occurred while getting the card conversion chart data: Exception"
					+ ex.getMessage());
			throw new Exception(
					"Error occurred while getting the card conversion chart data: Exception"
							+ ex.getMessage());
		} finally {
			session.close();
		}
	}

	@Override
	public List<Object[]> getTotTransVolBySiteReportInfo(Date fromDate,
			Date toDate, String[] sites) throws Exception {
		Session session = null;
		try {
			session = getHibernateTemplate().getSessionFactory().openSession();
			Query query = session
					.getNamedQuery("report.getTotTransVolBySiteInfo");
			query.setDate("fromDate", fromDate);
			query.setDate("toDate", toDate);
			query.setParameterList("sites", sites);

			List results = query.list();

			return results;
		} catch (Exception ex) {
			logger.error("Error occurred while getting the total transaction volume by site report data: Exception"
					+ ex.getMessage());
			throw new Exception(
					"Error occurred while getting the total transaction volume by site report data: Exception"
							+ ex.getMessage());
		} finally {
			session.close();
		}
	}

	/**
	 * Execute chart query.
	 *
	 * @param chartDef
	 *            the chart def
	 * @param parameters
	 *            the parameters
	 * @return the list
	 * @throws DaoException
	 *             the dao exception
	 */
	private List<Object[]> executeChartQuery(MSReportDefinition chartDef,
			HashMap<String, Object> parameters) throws DaoException {
		long startTime = System.currentTimeMillis();
		Session session = null;
		try {
			// Session session = getSession();
			session = getHibernateTemplate().getSessionFactory().openSession();
			SQLQuery query = session.createSQLQuery(chartDef.getReportQuery());
			for (String columnName : chartDef.getReportColumns().split(",")) {
				query.addScalar(columnName, StringType.INSTANCE);
			}

			for (ReportParameter paramDef : chartDef.getReportParameterList()) {
				Object value = parameters.get(paramDef.getName());
				if (value == null) {
					if (paramDef.getParamType().isAssignableFrom(Date.class)) {
						query.setDate(paramDef.getName(), (Date) value);
						logger.trace("In setParameter: Null : "
								+ paramDef.getName());
					} else {
						query.setParameter(paramDef.getName(), null);
						logger.trace("In setParameter: Null : "
								+ paramDef.getName());
					}
				} else {
					if (paramDef.getParamType().isAssignableFrom(Date.class)
							&& value instanceof Date) {
						// Mahesh 20100715:
						// Something fishy about setTimestamp with
						// java.util.Date parameter,
						// so manually converting from date to timestamp,
						// otherwise query is hanging
						// query.setTimestamp(paramDef.getName(), (Date)value);

						// value =
						// Timestamp.valueOf(DateUtil.parseDate((Date)value,
						// DateUtil.FORMAT_TIMESTAMP));
						query.setDate(paramDef.getName(), (Date) value);

						logger.trace("In setParameter: Timestamp : "
								+ paramDef.getName() + ": " + value);
					} else if (paramDef.getParamType().isPrimitive()
							&& value instanceof String) {
						if (paramDef.getParamType().isAssignableFrom(
								Short.class)) {
							query.setShort(paramDef.getName(),
									Short.valueOf((String) value));
						} else if (paramDef.getParamType().isAssignableFrom(
								Integer.class)) {
							query.setInteger(paramDef.getName(),
									Integer.valueOf((String) value));
						} else if (paramDef.getParamType().isAssignableFrom(
								Long.class)) {
							query.setLong(paramDef.getName(),
									Long.valueOf((String) value));
						} else if (paramDef.getParamType().isAssignableFrom(
								Float.class)) {
							query.setFloat(paramDef.getName(),
									Float.valueOf((String) value));
						} else if (paramDef.getParamType().isAssignableFrom(
								Double.class)) {
							query.setDouble(paramDef.getName(),
									Double.valueOf((String) value));
						} else if (paramDef.getParamType().isAssignableFrom(
								Boolean.class)) {
							query.setBoolean(paramDef.getName(),
									Boolean.valueOf((String) value));
							logger.trace("In setParameter: Boolean : "
									+ paramDef.getName() + ": " + value);
						} else {
							throw new DaoException("Parameter type: "
									+ paramDef.getParamType().getName()
									+ " is not supported");
						}
					} else if (paramDef.getParamType().isAssignableFrom(
							Date.class)
							&& value instanceof String) {
						Date date = DateUtil.strToDate((String) value,
								DateUtil.FORMAT_DDMMYYYYHHMMSS);
						query.setTimestamp(paramDef.getName(), date);
						logger.trace("In setParameter: Date : "
								+ paramDef.getName() + ": " + value);
					} else if (paramDef.getParamType().isAssignableFrom(
							String.class)
							&& value instanceof String) {
						query.setString(paramDef.getName(), (String) value);
						logger.trace("In setParameter: String : "
								+ paramDef.getName() + ": " + value);
					} else if (paramDef.getParamType().isAssignableFrom(
							value.getClass())) {
						query.setParameter(paramDef.getName(), value);
						logger.trace("In setParameter: same type "
								+ paramDef.getName() + ": " + value);
					} else {
						query.setParameter(paramDef.getName(), value);
						logger.trace("In setParameter: unknown type "
								+ paramDef.getName() + ": " + value);
					}
				}
			}
			return query.list();
		} catch (HibernateException ex) {
			throw new DaoException(ex);
		} finally {
			logger.trace("In finally of executeReportQuery: Caption: , TotalTimeMilli: "
					+ (System.currentTimeMillis() - startTime));
			session.close();
		}

	}

	@Override
	public void loadMSLineReportData(MultiSeriesReport report,
			MSReportDefinition msReportDef, HashMap<String, Object> parameters)
			throws Exception {
		try {
			List<Object[]> resultList = executeChartQuery(msReportDef,
					parameters);
			for (Object[] record : resultList) {
				if (msReportDef.getIsSingleLineSeriesResult()) {
					report.addSeriesData((String) record[0],
							Arrays.copyOfRange(record, 1, record.length));
				} else {
					if (record.length == 3) {
						report.addData((String) record[0], (String) record[1],
								(String) record[2], null);
					} else if (record.length == 4) {
						report.addData((String) record[0], (String) record[1],
								(String) record[2], (String) record[3], null);
					}
				}
			}
		} catch (HibernateException ex) {
			throw new DaoException(ex);
		}
	}

	@Override
	public List<Object[]> getCardBypassApprovedBySiteInfo(Date fromDate,
			Date toDate, String[] sites) throws Exception {
		Session session = null;
		try {
			session = getHibernateTemplate().getSessionFactory().openSession();
			Query query = session
					.getNamedQuery("report.getCardBypassApprovedBySiteInfo");
			query.setDate("fromDate", fromDate);
			query.setDate("toDate", toDate);
			query.setParameterList("sites", sites);

			List results = query.list();

			return results;
		} catch (Exception ex) {
			logger.error("Error occurred while getting the card Bypass Approved By Site Report Details: Exception"
					+ ex.getMessage());
			throw new Exception(
					"Error occurred while getting the card Bypass Approved By Site Report Details: Exception"
							+ ex.getMessage());
		} finally {
			session.close();
		}
	}

	@Override
	public PaginatedResult<String> getCardBypassApprovedTransIds(Date fromDate,
			Date toDate, String[] siteCodes, int fromRow, int toRow)
			throws Exception {
		Session session = null;
		try {
			PaginatedResult<String> result = new PaginatedResult<String>();
			session = getHibernateTemplate().getSessionFactory().openSession();// this.getSession();//getHibernateTemplate().getSessionFactory().openSession();
			Query query = session
					.getNamedQuery("report.getCardBypassApprovedTransIds");
			query.setDate("fromDate", fromDate);
			query.setDate("toDate", toDate);
			query.setParameterList("sites", siteCodes);
			query.setInteger("fromRow", fromRow);
			query.setInteger("toRow", toRow);
			List<String> results = query.list();

			Query countQuery = session
					.getNamedQuery("report.getCardBypassApprovedTransIdsCount");
			countQuery.setDate("fromDate", fromDate);
			countQuery.setDate("toDate", toDate);
			countQuery.setParameterList("sites", siteCodes);

			int total = ((BigDecimal) (countQuery.uniqueResult())).intValue();

			result.setRows(results);
			result.setTotal(total);

			return result;
		} catch (Exception ex) {
			logger.error("Error occurred while getting the card bypass approved transaction ids: Exception"
					+ ex.getMessage());
			throw new Exception(
					"Error occurred while getting the card bypass approved transaction ids: Exception"
							+ ex.getMessage());
		} finally {
			session.close();
		}
	}

	@Override
	public List<String> getAllCardBypassApprovedTransIds(Date fromDate,
			Date toDate, String[] sites) throws Exception {
		Session session = null;
		try {
			session = getHibernateTemplate().getSessionFactory().openSession();// this.getSession();//getHibernateTemplate().getSessionFactory().openSession();
			Query query = session
					.getNamedQuery("report.getAllCardBypassApprovedTransIds");
			query.setDate("fromDate", fromDate);
			query.setDate("toDate", toDate);
			query.setParameterList("sites", sites);
			List<String> results = query.list();

			return results;
		} catch (Exception ex) {
			logger.error("Error occurred while getting the card bypass approved transaction ids: Exception"
					+ ex.getMessage());
			throw new Exception(
					"Error occurred while getting the card bypass approved transaction ids: Exception"
							+ ex.getMessage());
		} finally {
			session.close();
		}
	}

	@Override
	public void updateOnSuspend(String transactionId, String userId,
			String wkstnId) {
		logger.info(
				"Inside Dao Impl========>>>>>Approve Card transactionId-{}, userId-{}, wkstnId-{}",
				new Object[] { transactionId, userId, wkstnId });

		DetachedCriteria criteria = DetachedCriteria
				.forClass(getPersistentClass());
		criteria.add(Restrictions.eq("transactionId", transactionId));
		List<NicTransaction> list = (List<NicTransaction>) getHibernateTemplate()
				.findByCriteria(criteria);
		try {
			if (list != null) {
				for (NicTransaction updateObj : list) {
					updateObj.setTransactionStatus(TRANSACTION_STATUS_SUSPEND);
					updateObj.setUpdateBy(userId);
					updateObj.setUpdateWkstnId(wkstnId);
					updateObj.setUpdateDatetime(new Date());
					saveOrUpdate(updateObj);
				}
			}
		} catch (Exception e) {
			logger.error(
					"[updateOn  TRANSACTION_STATUS_SUSPEND] transactionId-{}, userId-{}, wkstnId-{}",
					new Object[] { transactionId, userId, wkstnId }, e);
		}
	}

	@Override
	public BaseModelSingle<NicTransaction> getNicTransactionById(
			String transactionId) {
		NicTransaction nicTransaction = null;

		DetachedCriteria detachedCriteria = DetachedCriteria
				.forClass(getPersistentClass());
		// detachedCriteria.getExecutableCriteria(this.hibernateTemplate.getSessionFactory().openSession());
		if (StringUtils.isNotEmpty(transactionId)) {
			detachedCriteria.add(Restrictions
					.eq("transactionId", transactionId));
			try {
				List<NicTransaction> nicTransactionList = this
						.findAll(detachedCriteria);
				if (CollectionUtils.isNotEmpty(nicTransactionList)) {
					nicTransaction = nicTransactionList.get(0);
				}
			} catch (Exception e) {
				return new BaseModelSingle<NicTransaction>(null, false,
						CreateMessageException.createMessageException(e)
								+ " - getNicTransactionById - " + transactionId
								+ " - thất bại.");
			}
		}
		return new BaseModelSingle<NicTransaction>(nicTransaction, true, null);
	}

	@Override
	public NicTransaction getNicTransactionByPrevPPno(String prevPassport,
			String typePassport, String nin) {
		NicTransaction nicTransaction = null;

		DetachedCriteria detachedCriteria = DetachedCriteria
				.forClass(getPersistentClass());
		// detachedCriteria.getExecutableCriteria(this.hibernateTemplate.getSessionFactory().openSession());
		if (StringUtils.isNotEmpty(prevPassport)) {
			detachedCriteria.add(Restrictions
					.eq("prevPassportNo", prevPassport));
		}
		if (StringUtils.isNotEmpty(typePassport)) {
			detachedCriteria.add(Restrictions.eq("passportType", typePassport));
		}
		if (StringUtils.isNotEmpty(nin)) {
			detachedCriteria.add(Restrictions.eq("nin", nin));
		}
		List<NicTransaction> nicTransactionList = this
				.findAll(detachedCriteria);
		if (CollectionUtils.isNotEmpty(nicTransactionList)) {
			nicTransaction = nicTransactionList.get(0);
		}
		return nicTransaction;
	}

	@Override
	public PaginatedResult<NicTransaction> findAllForPagination(
			NicTransaction nicTransaction, int pageNo, int pageSize,
			boolean ignoreCase, boolean enableLike, Order order) {
		DetachedCriteria tCriteria = DetachedCriteria
				.forClass(NicTransaction.class);

		if (StringUtils.isNotEmpty(nicTransaction.getTransactionId())) {
			tCriteria.add(Restrictions.ilike("transactionId", nicTransaction
					.getTransactionId().toLowerCase()));
		}

		if (StringUtils.isNotEmpty(nicTransaction.getApplnRefNo())) {
			tCriteria.add(Restrictions.ilike("applnRefNo", nicTransaction
					.getApplnRefNo().toLowerCase()));
		}

		NicRegistrationData nicRegistration = nicTransaction
				.getNicRegistrationData();
		if (nicRegistration != null) {
			tCriteria.createAlias("nicRegistrationData", "nicRegistrationData");
			if (StringUtils.isNotEmpty(nicRegistration.getSurnameFull())) {
				tCriteria.add(Restrictions.ilike(
						"nicRegistrationData.surnameFull", nicRegistration
								.getSurnameFull().toLowerCase()));
			}
			if (StringUtils.isNotEmpty(nicRegistration.getFirstnameFull())) {
				tCriteria.add(Restrictions.ilike(
						"nicRegistrationData.firstnameFull", nicRegistration
								.getFirstnameFull().toLowerCase()));
			}
			if (StringUtils.isNotEmpty(nicRegistration.getMiddlenameFull())) {
				tCriteria.add(Restrictions.ilike(
						"nicRegistrationData.middlenameFull", nicRegistration
								.getMiddlenameFull().toLowerCase()));
			}

			if (StringUtils.isNotEmpty(nicRegistration.getGender())) {
				tCriteria.add(Restrictions.eq("nicRegistrationData.gender",
						nicRegistration.getGender()));
			}
			if (nicRegistration.getDateOfBirth() != null) {
				tCriteria.add(Restrictions.eq(
						"nicRegistrationData.dateOfBirth",
						nicRegistration.getDateOfBirth()));
			}
		}

		return findAllForPagination(tCriteria, pageNo, pageSize, order);
	}

	@Override
	public int updateStatusByTxnIdList(List<String> txnIdList, String status,
			String officerId, String workstationId) throws Exception {
		Session session = null;
		int updateCount = 0;
		try {
			session = this.openSession();
			Query query = session
					.getNamedQuery("updateStatusByTransactionIdList");
			query.setString("status", status);
			query.setString("officerId", officerId);
			query.setString("workstationId", workstationId);
			query.setParameterList("txnIdList", txnIdList);
			updateCount = query.executeUpdate();
			logger.info(
					"[updateTransactionStatus] update result returned for status: {}, updateCount: {}",
					status, updateCount);
		} catch (HibernateException ex) {
			throw new Exception(ex);
		} finally {
			session.close();
		}
		return updateCount;
	}

	@Override
	public List<NicTransaction> findByApplnRefId(String applnRefNo)
			throws DaoException {
		try {
			DetachedCriteria detachedCriteria = DetachedCriteria
					.forClass(getPersistentClass());
			if (StringUtils.isNotEmpty(applnRefNo)) {
				detachedCriteria.add(Restrictions.eq("applnRefNo", applnRefNo));
				List<NicTransaction> nicTransactionList = this
						.findAll(detachedCriteria);
				return nicTransactionList;
			}
		} catch (Exception e) {
			throw new DaoException(e);
		}
		return null;
	}

	@Override
	public List<Object[]> getCollectedTransByEstDate(String fromDate, int noDay) {

		Session session = null;
		try {
			session = getHibernateTemplate().getSessionFactory().openSession();
			Query query = session.getNamedQuery("getCollectedTransByDate");
			query.setString("fromDate", fromDate);
			query.setInteger("noOfDays", noDay);
			List<Object[]> resultList = null;
			resultList = query.list();

			return resultList;
		} catch (Exception ex) {
			logger.error("Error occurred while getting the card Bypass Approved By Site Report Details: Exception"
					+ ex.getMessage());
		} finally {
			session.close();
		}
		return null;
	}

	@Override
	public List<Object[]> getPendingTransByEstDate(String fromDate, int noDay) {
		Session session = null;
		try {
			session = getHibernateTemplate().getSessionFactory().openSession();
			Query query = session.getNamedQuery("getPendingTransByDate");
			query.setString("fromDate", fromDate);
			query.setInteger("noOfDays", noDay);
			List<Object[]> resultList = null;
			resultList = query.list();

			return resultList;
		} catch (Exception ex) {
			logger.error("Error occurred while getting the card Bypass Approved By Site Report Details: Exception"
					+ ex.getMessage());
		} finally {
			session.close();
		}
		return null;
	}

	@Override
	public List<Object[]> getAllTransByEstDate(String fromDate, int noDay) {
		Session session = null;
		try {
			session = getHibernateTemplate().getSessionFactory().openSession();
			Query query = session.getNamedQuery("getAllTransByDate");
			query.setString("fromDate", fromDate);
			query.setInteger("noOfDays", noDay);
			List<Object[]> resultList = null;
			resultList = query.list();

			return resultList;
		} catch (Exception ex) {
			System.out
					.println("Error occurred while getting the card Bypass Approved By Site Report Details: Exception"
							+ ex.getMessage());
		} finally {
			session.close();
		}
		return null;
	}

	@Override
	public PaginatedResult<NicTransaction> getAllByStatusAndEstDate(
			String[] status, String estDate, String transType,
			PageRequest pageRequest) {
		// TODO Auto-generated method stub
		DetachedCriteria criteria = DetachedCriteria
				.forClass(NicTransaction.class);
		Order order = null;
		if (pageRequest != null && pageRequest.getSortorder().equals("desc")) {
			order = Order.desc(pageRequest.getSortname());
		} else {
			order = Order.asc(pageRequest.getSortname());
		}

		if (status != null && StringUtils.isNotBlank(transType)) {
			if (transType.equals("PEN")) {
				criteria.add(Restrictions.in("transactionStatus", status));
			} else {
				criteria.add(Restrictions.not(Restrictions.in(
						"transactionStatus", status)));
			}

		}

		if (estDate != null) {
			criteria.add(Restrictions.lt("estDateOfCollection",
					DateUtil.adjustDays(new Date(estDate), 1)));
			criteria.add(Restrictions.gt("estDateOfCollection",
					DateUtil.adjustDays(new Date(estDate), -1)));
		}

		criteria.add(Restrictions.not(Restrictions.in("createBy", new String[] {
				"SYSTEM", "TEST" })));

		return findAllForPagination(criteria, pageRequest.getPageNo(),
				pageRequest.getMaxRecords(), order);
	}

	@Override
	public List<NicTransaction> getAllByStatusAndEstDate(String[] status,
			String estDate, String transType) {
		// TODO Auto-generated method stub
		try {
			DetachedCriteria criteria = DetachedCriteria
					.forClass(getPersistentClass());
			if (status != null && StringUtils.isNotBlank(transType)) {
				if (transType.equals("PEN")) {
					criteria.add(Restrictions.in("transactionStatus", status));
				} else {
					criteria.add(Restrictions.not(Restrictions.in(
							"transactionStatus", status)));
				}

			}

			if (estDate != null) {
				criteria.add(Restrictions.lt("estDateOfCollection",
						DateUtil.adjustDays(new Date(estDate), 1)));
				criteria.add(Restrictions.gt("estDateOfCollection",
						DateUtil.adjustDays(new Date(estDate), -1)));
			}

			criteria.add(Restrictions.not(Restrictions.in("createBy",
					new String[] { "SYSTEM", "TEST" })));

			List<NicTransaction> nicTransactionList = this.findAll(criteria);

			return nicTransactionList;
		} catch (Exception e) {
			try {
				throw new DaoException(e);
			} catch (DaoException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		return null;
	}

	/* [09-Mar] Trung thêm thay đổi số năm hết hạn hộ chiếu */
	@Override
	public void updateValidateTime(String transactionId, String userId,
			String wkstnId, Integer year) {
		logger.info(
				"Inside Dao Impl========>>>>>Change Year-ValidatityPeriod transactionId-{}, userId-{}, wkstnId-{}",
				new Object[] { transactionId, userId, wkstnId });

		DetachedCriteria criteria = DetachedCriteria
				.forClass(getPersistentClass());
		criteria.add(Restrictions.eq("transactionId", transactionId));
		List<NicTransaction> list = (List<NicTransaction>) getHibernateTemplate()
				.findByCriteria(criteria);
		try {
			if (list != null) {
				for (NicTransaction updateObj : list) {
					updateObj.setValidityPeriod(year);
					updateObj.setUpdateBy(userId);
					updateObj.setUpdateWkstnId(wkstnId);
					updateObj.setUpdateDatetime(new Date());
					saveOrUpdate(updateObj);
				}
			}
		} catch (Exception e) {
			logger.error(
					"[updateValidateTime] transactionId-{}, userId-{}, wkstnId-{}, year-{}",
					new Object[] { transactionId, userId, wkstnId, year }, e);
		}
	}

	@Override
	public List<NicTransaction> getListTransactionByPackage(String packageId) {
		List<NicTransaction> nicTransactionDBOList = new ArrayList<NicTransaction>();
		DetachedCriteria detachedCriteria = DetachedCriteria
				.forClass(getPersistentClass());
		if (StringUtils.isNotEmpty(packageId)) {
			detachedCriteria.add(Restrictions.eq("packageID", packageId));
			List<NicTransaction> resultTransactions = this
					.findAll(detachedCriteria);
			nicTransactionDBOList.addAll(resultTransactions);
		}
		logger.info("[findAllByPackageId][{}] size:{}", new Object[] {
				packageId, nicTransactionDBOList.size() });
		return nicTransactionDBOList;
	}

	@Override
	public List<NicTransaction> getListTransactionBySiteCode(String siteCode,
			Date date) {
		List<NicTransaction> nicTransactionDBOList = new ArrayList<NicTransaction>();
		DetachedCriteria detachedCriteria = DetachedCriteria
				.forClass(getPersistentClass());
		if (StringUtils.isNotEmpty(siteCode)) {
			detachedCriteria.add(Restrictions.eq("regSiteCode", siteCode));
			if (date != null)
				detachedCriteria.add(Restrictions.ge("updateDatetime",
						new Date(date.getTime() - TimeUnit.DAYS.toMillis(1))));
			List<NicTransaction> resultTransactions = this
					.findAll(detachedCriteria);
			nicTransactionDBOList.addAll(resultTransactions);
		}
		logger.info("[findAllByPackageId][{}] size:{}", new Object[] {
				siteCode, nicTransactionDBOList.size() });
		return nicTransactionDBOList;
	}

	@Override
	public List<CountPassport> issuePassportList() throws Exception {
		Session session = null;
		List<CountPassport> lst = null;
		try {
			session = this.openSession();
			Query query = session.getNamedQuery("issuePassportList");
			List<Object[]> list = query.list();
			if (CollectionUtils.isNotEmpty(list)) {
				lst = new ArrayList<CountPassport>();
				for (int i = 0; i < list.size(); i++) {
					CountPassport countP = new CountPassport();
					Object[] obj = (Object[]) list.get(i);
					countP.setRegSite((String) obj[0]);
					countP.setTotal((Integer) obj[1]);
					lst.add(countP);
				}
			}
			// logger.info("[updateTransactionStatus] update result returned for status: {}, updateCount: {}",
			// status, updateCount);
		} catch (HibernateException ex) {
			throw new Exception(ex);
		} finally {
			session.close();
		}
		return lst;
	}

	@Override
	public List<CountPassport> noneIssuePassportList() throws Exception {
		Session session = null;
		List<CountPassport> lst = null;
		try {
			session = this.openSession();
			Query query = session.getNamedQuery("noneIssuePassportList");
			List<Object[]> list = query.list();
			if (CollectionUtils.isNotEmpty(list)) {
				lst = new ArrayList<CountPassport>();
				for (int i = 0; i < list.size(); i++) {
					CountPassport countP = new CountPassport();
					Object[] obj = (Object[]) list.get(i);
					countP.setRegSite((String) obj[0]);
					countP.setTotal((Integer) obj[1]);
					lst.add(countP);
				}
			}
			// logger.info("[updateTransactionStatus] update result returned for status: {}, updateCount: {}",
			// status, updateCount);
		} catch (HibernateException ex) {
			throw new Exception(ex);
		} finally {
			session.close();
		}
		return lst;
	}

	@Override
	public List<CountPassport> printedPassport() throws Exception {
		Session session = null;
		List<CountPassport> lst = null;
		try {
			session = this.openSession();
			Query query = session.getNamedQuery("printedPassport");
			List<Object[]> list = query.list();
			if (CollectionUtils.isNotEmpty(list)) {
				lst = new ArrayList<CountPassport>();
				for (int i = 0; i < list.size(); i++) {
					CountPassport countP = new CountPassport();
					Object[] obj = (Object[]) list.get(i);
					countP.setRegSite((String) obj[0]);
					countP.setTotal((Integer) obj[1]);
					lst.add(countP);
				}
			}
			// logger.info("[updateTransactionStatus] update result returned for status: {}, updateCount: {}",
			// status, updateCount);
		} catch (HibernateException ex) {
			throw new Exception(ex);
		} finally {
			session.close();
		}
		return lst;
	}

	@Override
	public List<CountPassport> nonePrintPassport() throws Exception {
		Session session = null;
		List<CountPassport> lst = null;
		try {
			session = this.openSession();
			Query query = session.getNamedQuery("nonePrintPassport");
			List<Object[]> list = query.list();
			if (CollectionUtils.isNotEmpty(list)) {
				lst = new ArrayList<CountPassport>();
				for (int i = 0; i < list.size(); i++) {
					CountPassport countP = new CountPassport();
					Object[] obj = (Object[]) list.get(i);
					countP.setRegSite((String) obj[0]);
					countP.setTotal((Integer) obj[1]);
					lst.add(countP);
				}
			}
			// logger.info("[updateTransactionStatus] update result returned for status: {}, updateCount: {}",
			// status, updateCount);
		} catch (HibernateException ex) {
			throw new Exception(ex);
		} finally {
			session.close();
		}
		return lst;
	}

	@Override
	public long getRowCountallPassportList(String handoverId, String regSite,
			String fromDate, String toDate) throws Exception {
		Session session = null;
		try {
			session = this.openSession();
			Query query = session.getNamedQuery("allPassportList");
			query.setString("packageID", handoverId);
			query.setString("regSCode", regSite);
			query.setString("fromDate", fromDate);
			query.setString("toDate", toDate);
			List<Object[]> list = query.list();
			if (CollectionUtils.isNotEmpty(list)) {
				return list.size();
			}
		} catch (HibernateException ex) {
			throw new Exception(ex);
		} finally {
			session.close();
		}
		return 0;
	}

	@Override
	public List<NicUploadJobDto> allPassportList(String handoverId,
			String regSite, String fromDate, String toDate, int pageNumber,
			int pageSize) throws Exception {
		Session session = null;
		List<NicUploadJobDto> lst = null;
		try {
			session = this.openSession();
			Query query = session.getNamedQuery("allPassportList");
			int firstResult = (pageNumber - 1) * pageSize;
			int maxResults = pageSize;
			query.setFirstResult(firstResult);
			query.setMaxResults(maxResults);
			query.setString("packageID", handoverId);
			query.setString("regSCode", regSite);
			query.setString("fromDate", fromDate);
			query.setString("toDate", toDate);
			List<Object[]> list = query.list();
			if (CollectionUtils.isNotEmpty(list)) {
				lst = new ArrayList<NicUploadJobDto>();
				for (int i = 0; i < list.size(); i++) {
					NicUploadJobDto countP = new NicUploadJobDto();
					Object[] obj = (Object[]) list.get(i);
					countP.setPackageId((String) obj[0]);
					countP.setCreateDate((Date) obj[1]);
					countP.setCreateBy((String) obj[2]);
					countP.setRegSiteCode((String) obj[3]);
					countP.setCount((Integer) obj[4]);
					if (pageNumber == 1) {
						countP.setStt(i + 1);
					} else {
						countP.setStt(((pageNumber - 1) * pageSize) + i + 1);
					}
					lst.add(countP);
				}
			}
			// logger.info("[updateTransactionStatus] update result returned for status: {}, updateCount: {}",
			// status, updateCount);
		} catch (HibernateException ex) {
			throw new Exception(ex);
		} finally {
			session.close();
		}
		return lst;
	}

	@Override
	public long getRowCountallPersoList(String handoverId, Integer persoId,
			String fromDate, String toDate) throws Exception {
		Session session = null;
		try {
			session = this.openSession();
			Query query = session.getNamedQuery("allPersoList");
			query.setString("packageID", handoverId);
			if (persoId != null)
				query.setLong("printPerso", Long.valueOf((Integer) persoId));
			else
				query.setLong("printPerso", 0);
			query.setString("fromDate", fromDate);
			query.setString("toDate", toDate);
			List<Object[]> list = query.list();
			if (CollectionUtils.isNotEmpty(list)) {
				return list.size();
			}
		} catch (HibernateException ex) {
			throw new Exception(ex);
		} finally {
			session.close();
		}
		return 0;
	}

	@Override
	public List<NicUploadJobDto> allPersoList(String handoverId,
			Integer persoId, String fromDate, String toDate, int pageNumber,
			int pageSize) throws Exception {
		Session session = null;
		List<NicUploadJobDto> lst = null;
		try {
			session = this.openSession();
			Query query = session.getNamedQuery("allPersoList");
			int firstResult = (pageNumber - 1) * pageSize;
			int maxResults = pageSize;
			query.setFirstResult(firstResult);
			query.setMaxResults(maxResults);
			query.setString("packageID", handoverId);
			if (persoId != null)
				query.setInteger("printPerso", persoId);
			else
				query.setInteger("printPerso", 0);
			query.setString("fromDate", fromDate);
			query.setString("toDate", toDate);
			List<Object[]> list = query.list();
			if (CollectionUtils.isNotEmpty(list)) {
				lst = new ArrayList<NicUploadJobDto>();
				for (int i = 0; i < list.size(); i++) {
					NicUploadJobDto countP = new NicUploadJobDto();
					Object[] obj = (Object[]) list.get(i);
					countP.setListName((String) obj[0]);
					countP.setCreateDate((Date) obj[1]);
					countP.setListCode((String) obj[2]);
					countP.setPackageId((String) obj[3]);
					if (pageNumber == 1) {
						countP.setStt(i + 1);
					} else {
						countP.setStt(((pageNumber - 1) * pageSize) + i + 1);
					}
					lst.add(countP);
				}
			}
			// logger.info("[updateTransactionStatus] update result returned for status: {}, updateCount: {}",
			// status, updateCount);
		} catch (HibernateException ex) {
			throw new Exception(ex);
		} finally {
			session.close();
		}
		return lst;
	}

	@Override
	public List<NicUploadJobDto> newTransactionProcess() throws Exception {
		Session session = null;
		List<NicUploadJobDto> lst = null;
		try {
			session = this.openSession();
			Query query = session.getNamedQuery("newTransactionProcessCXL");
			List<Object[]> list = query.list();
			if (CollectionUtils.isNotEmpty(list)) {
				lst = new ArrayList<NicUploadJobDto>();
				for (int i = 0; i < list.size(); i++) {
					NicUploadJobDto countP = new NicUploadJobDto();
					Object[] obj = (Object[]) list.get(i);
					countP.setFullName((String) obj[0]);
					countP.setRegSiteCode((String) obj[1]);
					countP.setTransactionId((String) obj[2]);
					lst.add(countP);
				}
			}
			// logger.info("[updateTransactionStatus] update result returned for status: {}, updateCount: {}",
			// status, updateCount);
		} catch (HibernateException ex) {
			throw new Exception(ex);
		} finally {
			session.close();
		}
		return lst;
	}

	@Override
	public List<NicUploadJobDto> newTransactionProcessDDXL() throws Exception {
		Session session = null;
		List<NicUploadJobDto> lst = null;
		try {
			session = this.openSession();
			Query query = session.getNamedQuery("newTransactionProcessDDXL");
			List<Object[]> list = query.list();
			if (CollectionUtils.isNotEmpty(list)) {
				lst = new ArrayList<NicUploadJobDto>();
				for (int i = 0; i < list.size(); i++) {
					NicUploadJobDto countP = new NicUploadJobDto();
					Object[] obj = (Object[]) list.get(i);
					countP.setFullName((String) obj[0]);
					countP.setRegSiteCode((String) obj[1]);
					countP.setTransactionId((String) obj[2]);
					lst.add(countP);
				}
			}
			// logger.info("[updateTransactionStatus] update result returned for status: {}, updateCount: {}",
			// status, updateCount);
		} catch (HibernateException ex) {
			throw new Exception(ex);
		} finally {
			session.close();
		}
		return lst;
	}

	@Override
	public List<CountPassport> totalHosoTrongNgay() throws Exception {
		Session session = null;
		List<CountPassport> lst = null;
		try {
			session = this.openSession();
			Query query = session.getNamedQuery("totalHosoTrongNgay");
			List<Object[]> list = query.list();
			if (CollectionUtils.isNotEmpty(list)) {
				lst = new ArrayList<CountPassport>();
				for (int i = 0; i < list.size(); i++) {
					CountPassport countP = new CountPassport();
					Object[] obj = (Object[]) list.get(i);
					countP.setRegSite((String) obj[0]);
					countP.setTotal((Integer) obj[1]);
					lst.add(countP);
				}
			}
			// logger.info("[updateTransactionStatus] update result returned for status: {}, updateCount: {}",
			// status, updateCount);
		} catch (HibernateException ex) {
			throw new Exception(ex);
		} finally {
			session.close();
		}
		return lst;
	}

	@Override
	public List<CountPassport> sohschuaxl() throws Exception {
		Session session = null;
		List<CountPassport> lst = null;
		try {
			session = this.openSession();
			Query query = session.getNamedQuery("sohschuaxl");
			List<Object[]> list = query.list();
			if (CollectionUtils.isNotEmpty(list)) {
				lst = new ArrayList<CountPassport>();
				for (int i = 0; i < list.size(); i++) {
					CountPassport countP = new CountPassport();
					Object[] obj = (Object[]) list.get(i);
					countP.setRegSite((String) obj[0]);
					countP.setTotal((Integer) obj[1]);
					lst.add(countP);
				}
			}
			// logger.info("[updateTransactionStatus] update result returned for status: {}, updateCount: {}",
			// status, updateCount);
		} catch (HibernateException ex) {
			throw new Exception(ex);
		} finally {
			session.close();
		}
		return lst;
	}

	@Override
	public List<CountPassport> sohsdaxl() throws Exception {
		Session session = null;
		List<CountPassport> lst = null;
		try {
			session = this.openSession();
			Query query = session.getNamedQuery("sohsdaxl");
			List<Object[]> list = query.list();
			if (CollectionUtils.isNotEmpty(list)) {
				lst = new ArrayList<CountPassport>();
				for (int i = 0; i < list.size(); i++) {
					CountPassport countP = new CountPassport();
					Object[] obj = (Object[]) list.get(i);
					countP.setRegSite((String) obj[0]);
					countP.setTotal((Integer) obj[1]);
					lst.add(countP);
				}
			}
			// logger.info("[updateTransactionStatus] update result returned for status: {}, updateCount: {}",
			// status, updateCount);
		} catch (HibernateException ex) {
			throw new Exception(ex);
		} finally {
			session.close();
		}
		return lst;
	}

	@Override
	public List<CountPassport> slhosoDuyetCap() throws Exception {
		Session session = null;
		List<CountPassport> lst = null;
		try {
			session = this.openSession();
			Query query = session.getNamedQuery("slhosoDuyetCap");
			List<Object[]> list = query.list();
			if (CollectionUtils.isNotEmpty(list)) {
				lst = new ArrayList<CountPassport>();
				for (int i = 0; i < list.size(); i++) {
					CountPassport countP = new CountPassport();
					Object[] obj = (Object[]) list.get(i);
					countP.setRegSite((String) obj[0]);
					countP.setTotal((Integer) obj[1]);
					lst.add(countP);
				}
			}
			// logger.info("[updateTransactionStatus] update result returned for status: {}, updateCount: {}",
			// status, updateCount);
		} catch (HibernateException ex) {
			throw new Exception(ex);
		} finally {
			session.close();
		}
		return lst;
	}

	@Override
	public List<CountPassport> slhosoTuChoi() throws Exception {
		Session session = null;
		List<CountPassport> lst = null;
		try {
			session = this.openSession();
			Query query = session.getNamedQuery("slhosoTuChoi");
			List<Object[]> list = query.list();
			if (CollectionUtils.isNotEmpty(list)) {
				lst = new ArrayList<CountPassport>();
				for (int i = 0; i < list.size(); i++) {
					CountPassport countP = new CountPassport();
					Object[] obj = (Object[]) list.get(i);
					countP.setRegSite((String) obj[0]);
					countP.setTotal((Integer) obj[1]);
					lst.add(countP);
				}
			}
			// logger.info("[updateTransactionStatus] update result returned for status: {}, updateCount: {}",
			// status, updateCount);
		} catch (HibernateException ex) {
			throw new Exception(ex);
		} finally {
			session.close();
		}
		return lst;
	}

	@Override
	public List<CountPassport> slhosoSapHetHan() throws Exception {
		Session session = null;
		List<CountPassport> lst = null;
		try {
			session = this.openSession();
			Query query = session.getNamedQuery("slhosoSapHetHan");
			List<Object[]> list = query.list();
			if (CollectionUtils.isNotEmpty(list)) {
				lst = new ArrayList<CountPassport>();
				for (int i = 0; i < list.size(); i++) {
					CountPassport countP = new CountPassport();
					Object[] obj = (Object[]) list.get(i);
					countP.setRegSite((String) obj[0]);
					countP.setTotal((Integer) obj[1]);
					lst.add(countP);
				}
			}
			// logger.info("[updateTransactionStatus] update result returned for status: {}, updateCount: {}",
			// status, updateCount);
		} catch (HibernateException ex) {
			throw new Exception(ex);
		} finally {
			session.close();
		}
		return lst;
	}

	@Override
	public List<CountPassport> slhosoQuaHan() throws Exception {
		Session session = null;
		List<CountPassport> lst = null;
		try {
			session = this.openSession();
			Query query = session.getNamedQuery("slhosoQuaHan");
			List<Object[]> list = query.list();
			if (CollectionUtils.isNotEmpty(list)) {
				lst = new ArrayList<CountPassport>();
				for (int i = 0; i < list.size(); i++) {
					CountPassport countP = new CountPassport();
					Object[] obj = (Object[]) list.get(i);
					countP.setRegSite((String) obj[0]);
					countP.setTotal((Integer) obj[1]);
					lst.add(countP);
				}
			}
			// logger.info("[updateTransactionStatus] update result returned for status: {}, updateCount: {}",
			// status, updateCount);
		} catch (HibernateException ex) {
			throw new Exception(ex);
		} finally {
			session.close();
		}
		return lst;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public PaginatedResult<NicUploadJobDto> allListBProcessPerso(
			String packageId, String dateApprove, String siteCode, int pageNo,
			int pageSize) {
		List<NicUploadJobDto> listDto = new ArrayList<NicUploadJobDto>();
		try {
			StringBuffer sql = new StringBuffer(
					"SELECT DISTINCT EPP_CENTRAL.EPP_LIST_HANDOVER.PACKAGE_ID, EPP_CENTRAL.EPP_LIST_HANDOVER.PROPOSAL_DATE, EPP_CENTRAL.EPP_LIST_HANDOVER.APPROVE_DATE, EPP_CENTRAL.EPP_LIST_HANDOVER.LEADER_ID FROM EPP_CENTRAL.EPP_LIST_HANDOVER JOIN EPP_CENTRAL.EPP_TRANSACTION_PACKAGE ON EPP_CENTRAL.EPP_LIST_HANDOVER.PACKAGE_ID = EPP_CENTRAL.EPP_TRANSACTION_PACKAGE.PACKAGE_ID JOIN EPP_CENTRAL.EPP_TRANSACTION ON EPP_CENTRAL.EPP_TRANSACTION.TRANSACTION_ID = EPP_CENTRAL.EPP_TRANSACTION_PACKAGE.TRANSACTION_ID WHERE EPP_CENTRAL.EPP_LIST_HANDOVER.TYPE_LIST = 8 and EPP_CENTRAL.EPP_LIST_HANDOVER.STATUS_HANDOVER = 1 and EPP_CENTRAL.EPP_TRANSACTION_PACKAGE.STAGE = 'D'");
			if (StringUtils.isNotEmpty(packageId)) {
				sql.append(" and EPP_CENTRAL.EPP_LIST_HANDOVER.PACKAGE_ID  ='"
						+ packageId + "'");
			}
			if (StringUtils.isNotEmpty(dateApprove)) {
				sql.append(" and TRUNC(TO_DATE(EPP_CENTRAL.EPP_LIST_HANDOVER.APPROVE_DATE ,'DD/MM/YY')) >= TRUNC(TO_DATE('"
						+ dateApprove + "','DD/MM/YY'))");
				sql.append(" and TRUNC(TO_DATE(EPP_CENTRAL.EPP_LIST_HANDOVER.APPROVE_DATE ,'DD/MM/YY')) <= TRUNC(TO_DATE('"
						+ dateApprove + "','DD/MM/YY'))");
			}
			if (StringUtils.isNotEmpty(siteCode)) {
				sql.append(" and EPP_CENTRAL.EPP_TRANSACTION.REG_SITE_CODE  ='"
						+ siteCode + "'");
			}

			// sql.append(" order by txn.DATE_OF_APPLICATION desc");
			Query query = this.getSession().createSQLQuery(sql.toString());
			int count = query.list().size();
			int rowCount = 0;
			if (true) {
				if (count != 0) {
					rowCount = count;
				}
				query.setFirstResult((pageNo - 1) * pageSize);
				query.setMaxResults(pageSize);
			}
			List list = query.list();
			System.out.println("List Size >>" + list.size());
			Iterator itr = list.iterator();
			int i = (pageNo - 1) * pageSize;
			if (list != null && !list.isEmpty() && list.size() > 0) {
				while (itr.hasNext()) {
					Object[] record = (Object[]) itr.next();
					NicUploadJobDto ricLocal = new NicUploadJobDto();
					i = i + 1;
					ricLocal.setStt(i);
					ricLocal.setPackageId(record[0] != null ? record[0]
							.toString() : "");
					ricLocal.setDateIssuce(record[1] != null ? record[1]
							.toString() : "");
					ricLocal.setDateEprity(record[2] != null ? record[2]
							.toString() : "");
					ricLocal.setLeaderId(record[3] != null ? record[3]
							.toString() : "");
					if (StringUtils.isNotEmpty(ricLocal.getDateIssuce())) {
						Date date1 = HelperClass.convertStringToDate3(ricLocal
								.getDateIssuce());
						ricLocal.setDateIssuce(HelperClass
								.convertDateToString2(date1));
					}
					if (StringUtils.isNotEmpty(ricLocal.getDateEprity())) {
						Date date1 = HelperClass.convertStringToDate3(ricLocal
								.getDateEprity());
						ricLocal.setDateEprity(HelperClass
								.convertDateToString2(date1));
					}
					listDto.add(ricLocal);
				}
				PaginatedResult<NicUploadJobDto> result = new PaginatedResult<NicUploadJobDto>(
						rowCount, pageNo, listDto);
				return result;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public PaginatedResult<Datadto> allListBProcessPerso(String name,
			String dateOfBirth, String idNumber, String gender,
			String listCode, String passportNo, String passportType,
			String receipNo, int page, int pageSize) {
		List<Datadto> listDto = new ArrayList<Datadto>();
		Session s = null;
		try {
			page += 1;
			s = getHibernateTemplate().getSessionFactory().openSession();
			StringBuffer sql = null;
			boolean check = false;
			int rowCount = 0;
			if (StringUtils.isBlank(listCode)
					&& StringUtils.isBlank(passportNo)
					&& StringUtils.isBlank(passportType)
					&& StringUtils.isBlank(receipNo)
					&& StringUtils.isBlank(idNumber)) {
				sql = new StringBuffer(
						"SELECT * FROM (SELECT r.SURNAME_FULL, r.MIDDLENAME_FULL, r.FIRSTNAME_FULL, r.PLACE_OF_BIRTH, r.GENDER, r.DATE_OF_BIRTH,r.TRANSACTION_ID "
								+ ", txn.NIN, doc.PASSPORT_NO , r.r__, r.DEF_DATE_OF_BIRTH FROM "
								+ " (SELECT reg.SURNAME_FULL, reg.MIDDLENAME_FULL, reg.FIRSTNAME_FULL, reg.PLACE_OF_BIRTH, reg.GENDER, reg.DATE_OF_BIRTH,reg.TRANSACTION_ID"
								+ ", row_number() over(order by reg.FIRSTNAME_FULL asc nulls last) r__, reg.DEF_DATE_OF_BIRTH ");
				StringBuffer sqlCount = new StringBuffer(
						"SELECT COUNT(*) AS rowcount ");
				String sql2 = "FROM EPP_CENTRAL.EPP_REGISTRATION_DATA reg "
						+ "WHERE reg.SEARCH_NAME = '"
						+ HelperClass.removeAccent(name.trim()).toUpperCase()
						+ "' ";

				if (StringUtils.isNotEmpty(dateOfBirth)) {
					if (dateOfBirth.length() == 8) {
						String date = dateOfBirth.substring(6, 8) + "/"
								+ dateOfBirth.substring(4, 6) + "/"
								+ dateOfBirth.substring(2, 4);
						sql2 += " and TO_DATE(reg.DATE_OF_BIRTH ,'DD/MM/YY') = TO_DATE('"
								+ date + "','DD/MM/YY')";
					} else if (dateOfBirth.length() == 6) {
						String date = "01/" + dateOfBirth.substring(4, 6) + "/"
								+ dateOfBirth.substring(2, 4);
						sql2 += " and TO_DATE(reg.DATE_OF_BIRTH ,'DD/MM/YY') >= TO_DATE('"
								+ date
								+ "','DD/MM/YY')"
								+ " and TO_DATE(reg.DATE_OF_BIRTH ,'DD/MM/YY') <= LAST_DAY( TO_DATE('"
								+ date + "','DD/MM/YY'))";
					} else if (dateOfBirth.length() == 4) {
						String dateBegin = "01/01/"
								+ dateOfBirth.substring(2, 4);
						String dateEnd = "31/12/" + dateOfBirth.substring(2, 4);
						sql2 += " and TO_DATE(reg.DATE_OF_BIRTH ,'DD/MM/YY') >= TO_DATE('"
								+ dateBegin
								+ "','DD/MM/YY')"
								+ " and TO_DATE(reg.DATE_OF_BIRTH ,'DD/MM/YY') <= TO_DATE('"
								+ dateEnd + "','DD/MM/YY')";
					}
				}

				if (gender != null && !gender.trim().equals("")) {
					sql2 += " and reg.GENDER='" + gender.trim() + "'";
				}
				sql.append(sql2);
				sql.append(") r "
						+ " LEFT JOIN EPP_CENTRAL.EPP_TRANSACTION txn ON txn.TRANSACTION_ID = r.TRANSACTION_ID  "
						+ " LEFT JOIN EPP_CENTRAL.EPP_DOCUMENT_DATA doc ON doc.TRANSACTION_ID = r.TRANSACTION_ID  "
						+ " ORDER BY r__ ) WHERE r__ >= (((" + page + " -1) * "
						+ pageSize + ") + 1) and r__ < ((" + page + " * "
						+ pageSize + ") + 1 )");

				sqlCount.append(sql2);
				Query queryC = s.createSQLQuery(sqlCount.toString());
				System.out.println(sqlCount.toString());
				BigDecimal o = (BigDecimal) queryC.uniqueResult();
				rowCount = o.intValue();
			} else {
				sql = new StringBuffer(
						"SELECT * FROM ( select reg.SURNAME_FULL, reg.MIDDLENAME_FULL, reg.FIRSTNAME_FULL, reg.PLACE_OF_BIRTH,"
								+ "reg.GENDER, reg.DATE_OF_BIRTH,reg.TRANSACTION_ID, txn.NIN, doc.PASSPORT_NO, "
								+ "row_number() over(order by reg.TRANSACTION_ID asc nulls last) r__, reg.DEF_DATE_OF_BIRTH, COUNT(reg.TRANSACTION_ID) OVER () RESULT_COUNT "
								+ " from EPP_CENTRAL.EPP_TRANSACTION txn INNER JOIN EPP_CENTRAL.EPP_REGISTRATION_DATA reg ON reg.TRANSACTION_ID = txn.TRANSACTION_ID "
								+ " LEFT JOIN EPP_CENTRAL.EPP_DOCUMENT_DATA doc ON doc.TRANSACTION_ID = txn.TRANSACTION_ID ");
				StringBuffer sql3 = new StringBuffer(" where 1=1");
				String str3 = " ";
				String str4 = " LEFT JOIN EPP_CENTRAL.EPP_LIST_HANDOVER_DETAIL han ON han.transaction_id =  txn.TRANSACTION_ID ";
				String str5 = " ORDER BY r__ ) WHERE r__ >= (((" + page
						+ " -1) * " + pageSize + ") + 1) and r__ < ((" + page
						+ " * " + pageSize + ") + 1 )";

				if (listCode != null && !listCode.trim().equals("")) {
					sql.append(str4)
							.append(sql3)
							.append(str3)
							.append(" and han.PACKAGE_ID='" + listCode.trim()
									+ "' ");
				} else {
					sql.append(sql3).append(str3).append("");
				}
				if (StringUtils.isNotEmpty(dateOfBirth)) {
					if (dateOfBirth.length() == 8) {
						String date = dateOfBirth.substring(6, 8) + "/"
								+ dateOfBirth.substring(4, 6) + "/"
								+ dateOfBirth.substring(2, 4);
						sql.append(" and TO_DATE(reg.DATE_OF_BIRTH ,'DD/MM/YY') = TO_DATE('"
								+ date + "','DD/MM/YY')");
					} else if (dateOfBirth.length() == 6) {
						String date = "01/" + dateOfBirth.substring(4, 6) + "/"
								+ dateOfBirth.substring(2, 4);
						sql.append(" and TO_DATE(reg.DATE_OF_BIRTH ,'DD/MM/YY') >= TO_DATE('"
								+ date
								+ "','DD/MM/YY')"
								+ " and TO_DATE(reg.DATE_OF_BIRTH ,'DD/MM/YY') <= LAST_DAY( TO_DATE('"
								+ date + "','DD/MM/YY'))");
					} else if (dateOfBirth.length() == 4) {
						String dateBegin = "01/01/"
								+ dateOfBirth.substring(2, 4);
						String dateEnd = "31/12/" + dateOfBirth.substring(2, 4);
						sql.append(" and TO_DATE(reg.DATE_OF_BIRTH ,'DD/MM/YY') >= TO_DATE('"
								+ dateBegin
								+ "','DD/MM/YY')"
								+ " and TO_DATE(reg.DATE_OF_BIRTH ,'DD/MM/YY') <= TO_DATE('"
								+ dateEnd + "','DD/MM/YY')");
					}
				} else {
					sql.append("");
				}
				if (name != null && !name.trim().equals("")) {
					sql.append(" and (reg.SEARCH_NAME = '"
							+ HelperClass.removeAccent(name.trim())
									.toUpperCase() + "')");
				} else {
					sql.append("");
				}
				if (passportNo != null && !passportNo.trim().equals("")) {
					sql.append(" and doc.PASSPORT_NO ='" + passportNo.trim()
							+ "'");
				} else {
					sql.append("");
				}
				if (receipNo != null && !receipNo.trim().equals("")) {
					sql.append(" and txn.RECEIPT_NO ='" + receipNo.trim() + "'");
				} else {
					sql.append("");
				}
				if (passportType != null && !passportType.trim().equals("")) {
					sql.append(" and txn.PASSPORT_TYPE ='"
							+ passportType.trim() + "'");
				} else {
					sql.append("");
				}
				if (idNumber != null && !idNumber.trim().equals("")) {
					sql.append(" and txn.NIN ='" + idNumber.trim() + "'");
				} else {
					sql.append("");
				}
				if (gender != null && !gender.trim().equals("")) {
					sql.append(" and reg.GENDER='" + gender.trim() + "'");
				}
				sql.append(str5);
				check = true;
			}

			Query query = s.createSQLQuery(sql.toString());
			logger.info("SQL: =>>> " + sql.toString());

			List list = query.list();
			Iterator itr = list.iterator();
			if (list != null && !list.isEmpty() && list.size() > 0) {
				while (itr.hasNext()) {
					Object[] record = (Object[]) itr.next();
					Datadto dt = new Datadto();
					// i = i + 1;
					SimpleDateFormat dateFomat = new SimpleDateFormat(
							DateUtil.FORMAT_YYYYMMDD);
					if (record[5] != null) {
						String date = dateFomat.format(record[5]);
						if (record[10] != null) {
							String defDOB = record[10].toString();
							if (defDOB.equals("M")) {
								date = date.substring(0, 6);
							} else if (defDOB.equals("Y")) {
								date = date.substring(0, 4);
							}
						}
						dt.setDateOfBirth(date);
					} else {
						dt.setDateOfBirth("");
					}
					dt.setGender(record[4] != null ? record[4].toString() : "");
					dt.setName(HelperClass.createFullName(
							record[0] != null ? record[0].toString() : "",
							record[1] != null ? record[1].toString() : "",
							record[2] != null ? record[2].toString() : ""));
					dt.setPlaceOfBirth(record[3] != null ? record[3].toString()
							: "");
					dt.setTranId(record[6] != null ? record[6].toString() : "");

					if (record[7] != null) {
						dt.setNin(record[7].toString());
					}
					dt.setPassportNo(record[8] != null ? record[8].toString()
							: "");
					if (check) {
						rowCount = Integer
								.parseInt(record[11] != null ? record[11]
										.toString() : "0");
					}
					dt.setTotalRow(rowCount);
					listDto.add(dt);
				}
				PaginatedResult<Datadto> result = new PaginatedResult<Datadto>(
						rowCount, page, listDto);
				return result;
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			s.close();
		}
		return new PaginatedResult<>(0, page, listDto);
	}

	@SuppressWarnings("rawtypes")
	@Override
	public PaginatedResult<NicUploadJobDto> allPassportStorage(
			String passportType, String passportNo, String status,
			String startDate, String endDate, String fullName, int pageNo,
			int pageSize) throws Exception {
		List<NicUploadJobDto> listDto = new ArrayList<NicUploadJobDto>();
		SimpleDateFormat dateFomat = new SimpleDateFormat(
				DateUtil.FORMAT_DD_MM_YYYY);
		try {
			Boolean check = false;
			StringBuffer sql = new StringBuffer(
					"select wj.TEMP_PASSPORT_NO, txn.IS_EPASSPORT, txn.PASSPORT_TYPE, txn.REG_SITE_CODE, reg.SURNAME_FULL, reg.MIDDLENAME_FULL, reg.FIRSTNAME_FULL, wj.INVESTIGATION_STATUS, txn.DATE_OF_APPLICATION, txn.TRANSACTION_ID, txn.NIN, wj.PERSO_REGISTER_STATUS ");
			StringBuffer sql2 = new StringBuffer(
					" from EPP_CENTRAL.EPP_TRANSACTION txn, EPP_CENTRAL.EPP_REGISTRATION_DATA reg, EPP_CENTRAL.EPP_WORKFLOW_JOB wj");
			StringBuffer sql3 = new StringBuffer(
					" where txn.TRANSACTION_ID = reg.TRANSACTION_ID and txn.TRANSACTION_ID = wj.TRANSACTION_ID");
			String str1 = " , doc.DATE_OF_ISSUE, doc.DATE_OF_EXPIRY, doc.STATUS, doc.PASSPORT_NO, doc.PACKAGE_ID";
			String str2 = " , EPP_CENTRAL.EPP_DOCUMENT_DATA doc";
			String str3 = " and txn.TRANSACTION_ID = doc.TRANSACTION_ID";
			// if(StringUtils.isNotEmpty(status) && !status.equals("5")){
			// check = true;
			// sql.append(str1).append(sql2).append(str2).append(sql3).append(str3);
			// }else{
			sql.append(str1).append(sql2).append(str2).append(sql3)
					.append(str3);
			// }
			// if(check){
			if (StringUtils.isNotEmpty(startDate)) {
				sql.append(" and TRUNC(TO_DATE(doc.DATE_OF_ISSUE ,'DD/MM/YY')) >= TRUNC(TO_DATE('"
						+ startDate + "','DD/MM/YY'))");
				sql.append(" and TRUNC(TO_DATE(doc.DATE_OF_ISSUE ,'DD/MM/YY')) <= TRUNC(TO_DATE('"
						+ startDate + "','DD/MM/YY'))");
			}
			if (StringUtils.isNotEmpty(endDate)) {
				sql.append(" and TRUNC(TO_DATE(doc.DATE_OF_EXPIRY ,'DD/MM/YY')) >= TRUNC(TO_DATE('"
						+ endDate + "','DD/MM/YY'))");
				sql.append(" and TRUNC(TO_DATE(doc.DATE_OF_EXPIRY ,'DD/MM/YY')) <= TRUNC(TO_DATE('"
						+ endDate + "','DD/MM/YY'))");
			}
			if (passportNo != null && !passportNo.trim().equals("")) {
				sql.append(" and doc.PASSPORT_NO ='" + passportNo.trim() + "'");
			}
			// }
			if (StringUtils.isNotEmpty(passportType)) {
				sql.append(" and txn.PASSPORT_TYPE ='" + passportType + "'");
			}
			if (fullName != null && !fullName.trim().equals("")) {
				sql.append(" and reg.SEARCH_NAME ='"
						+ HelperClass.removeAccent(fullName.trim())
								.toUpperCase() + "'");
			}
			if (StringUtils.isNotEmpty(status)) {
				int tag = Integer.parseInt(status);
				switch (tag) {
				case 1:
					sql.append(" and doc.STATUS  ='PERSONALIZED' && doc.PACKAGE_ID is not null");
					break;
				case 2:
					sql.append(" and doc.STATUS  ='NONE'");
					break;
				case 3:
					sql.append(" and doc.STATUS  ='NONE'");
					break;
				case 4:
					String date1 = HelperClass
							.getCalendarWithoutTimeValueAsString1(Calendar
									.getInstance());
					sql.append(" and TRUNC(TO_DATE(doc.DATE_OF_EXPIRY ,'DD/MM/YY')) <= TRUNC(TO_DATE('"
							+ date1 + "','DD/MM/YY'))");
					break;
				case 5:
					sql.append(" and wj.TEMP_PASSPORT_NO is not null and (wj.PERSO_REGISTER_STATUS is null || wj.PERSO_REGISTER_STATUS == '09') and wj.INVESTIGATION_STATUS = '40'");
					break;
				case 6:
					sql.append(" and doc.STATUS  ='ISSUANCE'");
					break;
				case 7:
					sql.append(" and doc.STATUS  ='NONE'");
					break;
				case 8:
					sql.append(" and doc.STATUS  ='PERSONALIZED' && doc.PACKAGE_ID is null && (wj.PERSO_REGISTER_STATUS is null || wj.PERSO_REGISTER_STATUS == '09')");
					break;
				case 9:
					sql.append(" and doc.PACKAGE_ID is null and wj.PERSO_REGISTER_STATUS = '02' and doc.STATUS ='PERSONALIZED'");
					break;

				default:
					break;
				}
				/*
				 * if(!status.equals("Y") && !status.equals("N")){
				 * sql.append(" and doc.STATUS  ='" + status + "'"); }else
				 * if(status.equals("Y")){ String date1 =
				 * HelperClass.getCalendarWithoutTimeValueAsString1
				 * (Calendar.getInstance()); sql.append(
				 * " and TRUNC(TO_DATE(doc.DATE_OF_EXPIRY ,'DD/MM/YY')) >= TRUNC(TO_DATE('"
				 * + date1 +"','DD/MM/YY'))"); }else if(status.equals("N")){
				 * String date1 =
				 * HelperClass.getCalendarWithoutTimeValueAsString1
				 * (Calendar.getInstance()); sql.append(
				 * " and TRUNC(TO_DATE(doc.DATE_OF_EXPIRY ,'DD/MM/YY')) <= TRUNC(TO_DATE('"
				 * + date1 +"','DD/MM/YY'))"); }
				 */
			}
			// sql.append(" and wj.TEMP_PASSPORT_NO is not null");
			Query query = this.getSession().createSQLQuery(sql.toString());
			int count = query.list().size();
			int rowCount = 0;
			if (count != 0) {
				rowCount = count;
			}
			int firstResults = (pageNo - 1) < 0 ? 0 : (pageNo - 1) * pageSize;
			int maxNoPage = 1;
			if (count > pageSize) {
				maxNoPage = (int) Math.ceil(((double) count / pageSize));
			}
			int maxResults = pageSize;
			if (pageNo == maxNoPage) {
				maxResults = count % pageSize;
				if (maxResults == 0) {
					maxResults = pageSize;
				}
			}
			query.setFirstResult(firstResults);
			query.setMaxResults(maxResults);
			List list = query.list();
			System.out.println("List Size >>" + list.size());
			Iterator itr = list.iterator();
			int i = (pageNo - 1) * pageSize;
			if (list != null && !list.isEmpty() && list.size() > 0) {
				while (itr.hasNext()) {
					Object[] record = (Object[]) itr.next();
					NicUploadJobDto ricLocal = new NicUploadJobDto();
					i = i + 1;
					ricLocal.setStt(i);
					ricLocal.setTempPassportNo(record[0] != null ? record[0]
							.toString() : "");
					ricLocal.setPassportStyle(record[1] != null ? record[1]
							.toString() : "");
					ricLocal.setPassportType(record[2] != null ? record[2]
							.toString() : "");
					// ricLocal.setDateIssuce(record[3] != null ?
					// record[3].toString() : "");
					// ricLocal.setDateEprity(record[4] != null ?
					// record[4].toString() : "");
					ricLocal.setNicSiteCode(record[3] != null ? record[3]
							.toString() : "");
					ricLocal.setSubName(record[4] != null ? record[4]
							.toString() : "");
					ricLocal.setMidName(record[5] != null ? record[5]
							.toString() : "");
					ricLocal.setFirstName(record[6] != null ? record[6]
							.toString() : "");
					// ricLocal.setStatus(record[7] != null ?
					// record[7].toString() : "");
					ricLocal.setInvestigationStatus(record[7] != null ? record[7]
							.toString() : "");
					ricLocal.setEsColectionDate(record[8] != null ? dateFomat
							.format(record[8]) : "");
					ricLocal.setTransactionId(record[9] != null ? record[9]
							.toString() : "");
					ricLocal.setNin(record[10] != null ? record[10].toString()
							: "");
					ricLocal.setPersonRegStatus(record[11] != null ? record[11]
							.toString() : "");
					ricLocal.setDateIssuce(record[12] != null ? dateFomat
							.format(record[12]) : "");
					ricLocal.setDateEprity(record[13] != null ? dateFomat
							.format(record[13]) : "");
					ricLocal.setStatus(record[14] != null ? record[14]
							.toString() : "");
					ricLocal.setPassportNo(record[15] != null ? record[15]
							.toString() : "");
					ricLocal.setDocPackage(record[16] != null ? record[16]
							.toString() : "");
					listDto.add(ricLocal);
				}
				PaginatedResult<NicUploadJobDto> result = new PaginatedResult<NicUploadJobDto>(
						rowCount, pageNo, listDto);
				return result;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new PaginatedResult<>(0, pageNo, listDto);
	}

	@SuppressWarnings("rawtypes")
	@Override
	public PaginatedResult<NicUploadJobDto> allTransactionStorage(
			String transactionId, String recieptNo, String passportNo,
			String nin, String fullName, String passportStyle, String ppStage,
			String typeInves, int pageNo, int pageSize) throws Exception {
		List<NicUploadJobDto> listDto = new ArrayList<NicUploadJobDto>();
		try {
			Boolean check = false;
			StringBuffer sql = new StringBuffer(
					"select txn.TRANSACTION_ID, txn.RECEIPT_NO, txn.NIN, reg.SURNAME_FULL, reg.MIDDLENAME_FULL, reg.FIRSTNAME_FULL, reg.CONTACT_NO, txn.PASSPORT_STYLE, txn.PASSPORT_TYPE, txn.ISS_SITE_CODE, txn.DATE_OF_APPLICATION, wj.PERSO_REGISTER_STATUS, wj.INVESTIGATION_STATUS, txn.PREV_PASSPORT_NO, wj.INVESTIGATION_OFFICER_ID, wj.DATE_APPROVE_INVESTIGATION, wj.DATE_APPROVE_NIN, wj.DATE_APPROVE_PERSON, txn.LEADER_OFFICER_ID, wj.APPROVE_FLAG ");
			StringBuffer str1 = new StringBuffer(
					" from EPP_CENTRAL.EPP_TRANSACTION txn, EPP_CENTRAL.EPP_REGISTRATION_DATA reg, EPP_CENTRAL.EPP_WORKFLOW_JOB wj");
			StringBuffer str2 = new StringBuffer(
					" where txn.TRANSACTION_ID = reg.TRANSACTION_ID and txn.TRANSACTION_ID = wj.TRANSACTION_ID");
			String plus1 = ", doc.PASSPORT_NO, doc.STATUS";
			String plus2 = ", EPP_CENTRAL.EPP_DOCUMENT_DATA doc";
			String plus3 = " and txn.TRANSACTION_ID = doc.TRANSACTION_ID";
			if (StringUtils.isNotEmpty(passportNo)
					|| StringUtils.isNotEmpty(ppStage)
					|| (StringUtils.isNotEmpty(typeInves) && (typeInves
							.equals("3") || typeInves.equals("1")))) {
				check = true;
				sql.append(plus1).append(str1).append(plus2).append(str2)
						.append(plus3);
			} else {
				sql.append(str1).append(str2);
			}
			if (transactionId != null && !transactionId.trim().equals("")) {
				sql.append(" and txn.TRANSACTION_ID  ='" + transactionId.trim()
						+ "'");
			}
			if (recieptNo != null && !recieptNo.trim().equals("")) {
				sql.append(" and txn.RECEIPT_NO  ='" + recieptNo.trim() + "'");
			}
			if (passportNo != null && !passportNo.trim().equals("")) {
				sql.append(" and doc.PASSPORT_NO  ='" + passportNo.trim() + "'");
			}
			if (nin != null && !nin.trim().equals("")) {
				sql.append(" and txn.NIN  ='" + nin.trim() + "'");
			}
			if (fullName != null && !fullName.trim().equals("")) {
				sql.append(" and upper(reg.SURNAME_LINE1)  = '"
						+ fullName.trim().toUpperCase() + "'");
			}
			if (StringUtils.isNotEmpty(passportStyle)) {
				sql.append(" and txn.PASSPORT_STYLE  ='" + passportStyle + "'");
			}
			/*
			 * if (StringUtils.isNotEmpty(ppStage)) { if(!ppStage.equals("Y") &&
			 * !ppStage.equals("N")){ sql.append(" and doc.STATUS  ='" + ppStage
			 * + "'"); }else if(ppStage.equals("Y")){ String date1 =
			 * HelperClass.
			 * getCalendarWithoutTimeValueAsString1(Calendar.getInstance());
			 * sql.append(
			 * " and TRUNC(TO_DATE(doc.DATE_OF_EXPIRY ,'DD/MM/YY')) >= TRUNC(TO_DATE('"
			 * + date1 +"','DD/MM/YY'))"); }else if(ppStage.equals("N")){ String
			 * date1 =
			 * HelperClass.getCalendarWithoutTimeValueAsString1(Calendar.
			 * getInstance()); sql.append(
			 * " and TRUNC(TO_DATE(doc.DATE_OF_EXPIRY ,'DD/MM/YY')) <= TRUNC(TO_DATE('"
			 * + date1 +"','DD/MM/YY'))"); } }
			 */
			if (StringUtils.isNotEmpty(ppStage)) {
				int tag = Integer.parseInt(ppStage);
				switch (tag) {
				case 1:
					sql.append(" and doc.STATUS  ='PERSONALIZED' && doc.PACKAGE_ID is not null");
					break;
				case 2:
					sql.append(" and doc.STATUS  ='NONE'");
					break;
				case 3:
					sql.append(" and doc.STATUS  ='NONE'");
					break;
				case 4:
					String date1 = HelperClass
							.getCalendarWithoutTimeValueAsString1(Calendar
									.getInstance());
					sql.append(" and TRUNC(TO_DATE(doc.DATE_OF_EXPIRY ,'DD/MM/YY')) <= TRUNC(TO_DATE('"
							+ date1 + "','DD/MM/YY'))");
					break;
				case 5:
					sql.append(" and wj.TEMP_PASSPORT_NO is not null and (wj.PERSO_REGISTER_STATUS is null || wj.PERSO_REGISTER_STATUS == '09') and wj.INVESTIGATION_STATUS = '40'");
					break;
				case 6:
					sql.append(" and doc.STATUS  ='ISSUANCE'");
					break;
				case 7:
					sql.append(" and doc.STATUS  ='NONE'");
					break;
				case 8:
					sql.append(" and doc.STATUS  ='PERSONALIZED' && doc.PACKAGE_ID is null && (wj.PERSO_REGISTER_STATUS is null || wj.PERSO_REGISTER_STATUS == '09')");
					break;
				case 9:
					sql.append(" and doc.PACKAGE_ID is null and wj.PERSO_REGISTER_STATUS = '02' and doc.STATUS ='PERSONALIZED'");
					break;

				default:
					break;
				}
			}
			if (StringUtils.isNotEmpty(typeInves)) {
				switch (Integer.parseInt(typeInves)) {
				case 1:
					sql.append(" and wj.PERSO_REGISTER_STATUS  ='02' and doc.STATUS == 'PERSONALIZED' and doc.PACKAGE_ID is null");
					break;
				case 2:
					sql.append(" and (wj.INVESTIGATION_STATUS  ='00' or wj.INVESTIGATION_STATUS is null)");
					break;
				case 3:
					sql.append(" and doc.STATUS = 'ISSUANCE'");
					break;
				case 4:
					sql.append(" and wj.INVESTIGATION_STATUS  ='01' and wj.INVESTIGATION_OFFICER_ID is null");
					break;
				case 5:
					sql.append(" and wj.INVESTIGATION_STATUS  ='04'");
					break;
				case 6:
					sql.append(" and wj.INVESTIGATION_STATUS  ='40' and wj.PERSO_REGISTER_STATUS is null");
					break;
				case 7:
					sql.append(" and wj.INVESTIGATION_STATUS  ='02' and txn.LEADER_OFFICER_ID is not null");
					break;
				case 8:
					sql.append(" and wj.INVESTIGATION_STATUS  ='01' and wj.INVESTIGATION_OFFICER_ID is not null and wj.APPROVE_FLAG is null");
					break;
				case 9:
					sql.append(" and wj.INVESTIGATION_STATUS  ='02' and txn.LEADER_OFFICER_ID is null");
					break;
				case 10:
					sql.append(" and wj.INVESTIGATION_STATUS  ='01' and wj.INVESTIGATION_OFFICER_ID is not null and wj.APPROVE_FLAG is not null");
					break;

				default:
					break;
				}
			}
			// sql.append(" order by txn.DATE_OF_APPLICATION desc");
			Query query = this.getSession().createSQLQuery(sql.toString());
			int count = query.list().size();
			int rowCount = 0;
			if (true) {
				// count = query.list().size();
				// rowCount = 0;
				if (count != 0) {
					rowCount = count;
				}
				query.setFirstResult((pageNo - 1) * pageSize);
				query.setMaxResults(pageSize);
			}
			List list = query.list();
			System.out.println("List Size >>" + list.size());
			Iterator itr = list.iterator();
			int i = (pageNo - 1) * pageSize;
			if (list != null && !list.isEmpty() && list.size() > 0) {
				while (itr.hasNext()) {
					Object[] record = (Object[]) itr.next();
					NicUploadJobDto ricLocal = new NicUploadJobDto();
					i = i + 1;
					ricLocal.setStt(i);
					ricLocal.setTransactionId(record[0] != null ? record[0]
							.toString() : "");
					ricLocal.setRecieptNo(record[1] != null ? record[1]
							.toString() : "");
					// ricLocal.setPassportNo(record[2] != null ?
					// record[2].toString() : "");
					ricLocal.setSubName(record[3] != null ? record[3]
							.toString() : "");
					ricLocal.setMidName(record[4] != null ? record[4]
							.toString() : "");
					ricLocal.setFirstName(record[5] != null ? record[5]
							.toString() : "");
					ricLocal.setPhone(record[6] != null ? record[6].toString()
							: "");
					ricLocal.setPassportStyle(record[7] != null ? record[7]
							.toString() : "");
					ricLocal.setPassportType(record[8] != null ? record[8]
							.toString() : "");
					ricLocal.setNicSiteCode(record[9] != null ? record[9]
							.toString() : "");
					ricLocal.setEsColectionDate(record[10] != null ? record[10]
							.toString() : "");
					ricLocal.setPersoCheckStatus(record[11] != null ? record[11]
							.toString() : "");
					ricLocal.setInvestigationStatus(record[12] != null ? record[12]
							.toString() : "");
					// ricLocal.setStatus(record[13] != null ?
					// record[13].toString() : "");
					ricLocal.setInvestigationOfficerId(record[14] != null ? record[14]
							.toString() : "");
					ricLocal.setDateApproveInvestigation(record[15] != null ? record[15]
							.toString() : "");
					ricLocal.setDateApproveNin(record[16] != null ? record[16]
							.toString() : "");
					ricLocal.setDateApprovePerson(record[17] != null ? record[17]
							.toString() : "");
					ricLocal.setLeaderId(record[18] != null ? record[18]
							.toString() : "");
					ricLocal.setPriorityString(record[19] != null ? record[19]
							.toString() : "");
					if (check) {
						ricLocal.setPassportNo(record[20] != null ? record[20]
								.toString() : "");
						ricLocal.setStatus(record[21] != null ? record[21]
								.toString() : "");
					}
					listDto.add(ricLocal);
				}
				PaginatedResult<NicUploadJobDto> result = new PaginatedResult<NicUploadJobDto>(
						rowCount, pageNo, listDto);
				return result;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new PaginatedResult<NicUploadJobDto>(0, pageNo, listDto);
	}

	@Override
	public long getTracuuhosohcCount(NicUploadJobDto model) throws Exception {
		Session session = null;
		try {
			session = this.openSession();
			Query query = session.getNamedQuery("tracuuhosohc");
			query.setString("fullnameS", model.getFullName());
			query.setString("genderS", model.getGender());
			query.setString("dobS", model.getDob());
			query.setString("pobS", model.getPlaceOfBirth());
			query.setString("ninS", model.getNin());
			query.setString("passportNoS", model.getPassportNo());
			query.setString("receiptNoS", model.getRecieptNo());
			query.setString("packageIdS", model.getPackageId());
			if (model.getTypeApprove() != null)
				query.setLong("typeListS", Long.valueOf(model.getTypeApprove()));
			else
				query.setLong("typeListS", -1);
			List<Object[]> list = query.list();
			if (CollectionUtils.isNotEmpty(list)) {
				return list.size();
			}
		} catch (HibernateException ex) {
			throw new Exception(ex);
		} finally {
			session.close();
		}
		return 0;
	}

	@Override
	public List<NicUploadJobDto> allTracuuhosohc(NicUploadJobDto model,
			int pageNumber, int pageSize) throws Exception {
		Session session = null;
		List<NicUploadJobDto> lst = null;
		try {
			session = this.openSession();
			Query query = session.getNamedQuery("tracuuhosohc");
			int firstResult = (pageNumber - 1) * pageSize;
			int maxResults = pageSize;
			query.setFirstResult(firstResult);
			query.setMaxResults(maxResults);
			query.setString("fullnameS", model.getFullName());
			query.setString("genderS", model.getGender());
			query.setString("dobS", model.getDob());
			query.setString("pobS", model.getPlaceOfBirth());
			query.setString("ninS", model.getNin());
			query.setString("passportNoS", model.getPassportNo());
			query.setString("receiptNoS", model.getRecieptNo());
			query.setString("packageIdS", model.getPackageId());
			if (model.getTypeApprove() != null)
				query.setLong("typeListS", Long.valueOf(model.getTypeApprove()));
			else
				query.setLong("typeListS", -1);
			List<Object[]> list = query.list();
			if (CollectionUtils.isNotEmpty(list)) {
				lst = new ArrayList<NicUploadJobDto>();
				for (int i = 0; i < list.size(); i++) {
					NicUploadJobDto countP = new NicUploadJobDto();
					Object[] obj = (Object[]) list.get(i);
					countP.setTransactionId((String) obj[0]);
					countP.setFullName((String) obj[1]);
					countP.setGender((String) obj[2]);
					countP.setDatePackage((Date) obj[3]);
					countP.setPlaceOfBirth((String) obj[4]);
					countP.setNin((String) obj[5]);
					countP.setPassportNo((String) obj[6]);

					if (pageNumber == 1) {
						countP.setStt(i + 1);
					} else {
						countP.setStt(((pageNumber - 1) * pageSize) + i + 1);
					}
					lst.add(countP);
				}
			}
			// logger.info("[updateTransactionStatus] update result returned for status: {}, updateCount: {}",
			// status, updateCount);
		} catch (HibernateException ex) {
			throw new Exception(ex);
		} finally {
			session.close();
		}
		return lst;
	}

	@Override
	public List<NicUploadJobDto> listPackageIDPerso(String code, String packId)
			throws Exception {
		Session session = null;
		List<NicUploadJobDto> lst = null;
		try {
			session = this.openSession();
			Query query = session.getNamedQuery("listPackageIDPerso");
			query.setString("code", code);

			List<String> list = query.list();
			if (CollectionUtils.isNotEmpty(list)) {
				lst = new ArrayList<NicUploadJobDto>();
				for (int i = 0; i < list.size(); i++) {
					NicUploadJobDto countP = new NicUploadJobDto();
					String obj = list.get(i);
					countP.setPackageId((String) obj);
					lst.add(countP);
				}
			}
			// logger.info("[updateTransactionStatus] update result returned for status: {}, updateCount: {}",
			// status, updateCount);
		} catch (HibernateException ex) {
			throw new Exception(ex);
		} finally {
			session.close();
		}
		return lst;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public PaginatedResult<NicUploadJobDto> allPassportDestroy(
			String passportNo, String[] status, String toDay, int pageNo,
			int pageSize) throws Exception {
		List<NicUploadJobDto> listDto = new ArrayList<NicUploadJobDto>();
		try {
			StringBuffer sql = new StringBuffer(
					"select reg.SURNAME_FULL, reg.MIDDLENAME_FULL, reg.FIRSTNAME_FULL, reg.DATE_OF_BIRTH, reg.GENDER, doc.PASSPORT_NO, txn.NIN, reg.PLACE_OF_BIRTH, reg.STYLE_DOB from EPP_CENTRAL.EPP_REGISTRATION_DATA reg, EPP_CENTRAL.EPP_DOCUMENT_DATA doc, EPP_CENTRAL.EPP_TRANSACTION txn where txn.TRANSACTION_ID = reg.TRANSACTION_ID and txn.TRANSACTION_ID = doc.TRANSACTION_ID");
			if (StringUtils.isNotEmpty(passportNo)) {
				sql.append(" and doc.PASSPORT_NO  ='" + passportNo + "'");
			}

			if (status != null) {
				sql.append(" and doc.STATUS IN (");
				for (int i = 0; i < status.length; i++) {
					sql.append("'" + status[i] + "'");
					if (i < status.length - 1)
						sql.append(",");
				}
				sql.append(")");
			}
			if (StringUtils.isNotEmpty(toDay)) {
				// sql.append(" and TRUNC(TO_DATE(doc.DATE_OF_EXPIRY ,'DD/MM/YY')) >= TRUNC(TO_DATE('"+
				// toDay +"','DD/MM/YY'))");
			}
			sql.append(" order by txn.DATE_OF_APPLICATION desc");
			Query query = this.getSession().createSQLQuery(sql.toString());
			int count = query.list().size();
			int rowCount = 0;
			if (true) {
				if (count != 0) {
					rowCount = count;
				}
				query.setFirstResult((pageNo - 1) * pageSize);
				query.setMaxResults(pageSize);
			}
			List list = query.list();
			System.out.println("List Size >>" + list.size());
			Iterator itr = list.iterator();
			int i = (pageNo - 1) * pageSize;
			if (list != null && !list.isEmpty() && list.size() > 0) {
				while (itr.hasNext()) {
					Object[] record = (Object[]) itr.next();
					NicUploadJobDto ricLocal = new NicUploadJobDto();
					i = i + 1;
					ricLocal.setStt(i);
					ricLocal.setSubName(record[0] != null ? record[0]
							.toString() : "");
					ricLocal.setMidName(record[1] != null ? record[1]
							.toString() : "");
					ricLocal.setFirstName(record[2] != null ? record[2]
							.toString() : "");
					ricLocal.setDob(record[3] != null ? record[3].toString()
							: "");
					ricLocal.setGender(record[4] != null ? record[4].toString()
							: "");
					ricLocal.setPassportNo(record[5] != null ? record[5]
							.toString() : "");
					ricLocal.setNin(record[6] != null ? record[6].toString()
							: "");
					ricLocal.setPlaceOfBirth(record[7] != null ? record[7]
							.toString() : "");
					ricLocal.setKindDob(record[8] != null ? record[8]
							.toString() : "");
					listDto.add(ricLocal);
				}
				PaginatedResult<NicUploadJobDto> result = new PaginatedResult<NicUploadJobDto>(
						rowCount, pageNo, listDto);
				return result;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public List<NicUploadJobDto> searchUpdateB(String packageId,
			String createDate, String endDate) throws Exception {
		List<NicUploadJobDto> listDto = new ArrayList<NicUploadJobDto>();
		try {
			StringBuffer sql = new StringBuffer(
					"select txn.TRANSACTION_ID, reg.SURNAME_LINE1 from EPP_CENTRAL.EPP_LIST_HANDOVER han, EPP_CENTRAL.EPP_TRANSACTION_PACKAGE txk, EPP_CENTRAL.EPP_TRANSACTION txn, EPP_CENTRAL.EPP_REGISTRATION_DATA reg where han.PACKAGE_ID = txk.PACKAGE_ID and txk.TRANSACTION_ID = txn.TRANSACTION_ID and reg.TRANSACTION_ID = txn.TRANSACTION_ID and han.TYPE_LIST = 8 and han.STATUS_HANDOVER = 1 and txk.STAGE = 'C' and han.HANDOVER_STAGE = 'Y'");
			if (StringUtils.isNotEmpty(packageId)) {
				sql.append(" and han.PACKAGE_ID  ='" + packageId + "'");
			}
			if (StringUtils.isNotEmpty(createDate)) {
				sql.append(" and TRUNC(TO_DATE(han.CREATE_DATE ,'DD/MM/YY')) >= TRUNC(TO_DATE('"
						+ createDate + "','DD/MM/YY'))");

			}
			if (StringUtils.isNotEmpty(endDate)) {
				sql.append(" and TRUNC(TO_DATE(han.CREATE_DATE ,'DD/MM/YY')) <= TRUNC(TO_DATE('"
						+ endDate + "','DD/MM/YY'))");
			}

			sql.append(" order by txn.DATE_OF_APPLICATION desc");
			Query query = this.getSession().createSQLQuery(sql.toString());
			List list = query.list();
			System.out.println("List Size >>" + list.size());
			Iterator itr = list.iterator();
			int i = 0;
			if (list != null && !list.isEmpty() && list.size() > 0) {
				while (itr.hasNext()) {
					Object[] record = (Object[]) itr.next();
					String id = record[0] != null ? record[0].toString() : "";
					if (!checkTrung(listDto, id)) {
						continue;
					}
					NicUploadJobDto ricLocal = new NicUploadJobDto();
					i = i + 1;
					ricLocal.setStt(i);
					ricLocal.setTransactionId(record[0] != null ? record[0]
							.toString() : "");
					ricLocal.setFullName(record[1] != null ? record[1]
							.toString() : "");
					listDto.add(ricLocal);
				}
				return listDto;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public Boolean checkTrung(List<NicUploadJobDto> list, String transactionId) {
		for (NicUploadJobDto dto : list) {
			if (dto.getTransactionId().equals(transactionId))
				return false;
		}
		return true;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<NicUploadJobDto> listPackageIDPerso(String code)
			throws Exception {
		Session session = null;
		List<NicUploadJobDto> lst = null;
		try {
			session = this.openSession();
			Query query = session.getNamedQuery("packageIDPerso");
			query.setString("code", code);

			List<String> list = query.list();
			if (CollectionUtils.isNotEmpty(list)) {
				lst = new ArrayList<NicUploadJobDto>();
				for (int i = 0; i < list.size(); i++) {
					NicUploadJobDto countP = new NicUploadJobDto();
					String obj = list.get(i);
					countP.setPackageId((String) obj);
					lst.add(countP);
				}
			}
		} catch (HibernateException ex) {
			throw new Exception(ex);
		} finally {
			session.close();
		}
		return lst;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public BaseModelList<NicUploadJobDto> listPackageIDConfig(String packageId,
			String todayTime) throws Exception {
		Session session = null;
		List<NicUploadJobDto> lst = null;
		try {
			session = this.openSession();
			Query query = session.getNamedQuery("packageIDConfig");
			query.setString("todayTime", todayTime);
			query.setString("handoverId", packageId);
			List<Object> list = query.list();
			Iterator itr = list.iterator();
			if (CollectionUtils.isNotEmpty(list)) {
				lst = new ArrayList<NicUploadJobDto>();
				while (itr.hasNext()) {
					NicUploadJobDto countP = new NicUploadJobDto();
					Object[] record = (Object[]) itr.next();
					countP.setPackageId(record[0].toString());
					countP.setRegSiteCode(record[1].toString());
					lst.add(countP);
				}
			}
		} catch (HibernateException ex) {
			return new BaseModelList<NicUploadJobDto>(null, false,
					CreateMessageException.createMessageException(ex)
							+ " - listPackageIDConfig - " + packageId
							+ " - thất bại.");
		} finally {
			session.close();
		}
		return new BaseModelList<NicUploadJobDto>(lst, true, null);
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public List<NicUploadJobDto> listPackageIDDefault(String packageId,
			String type) throws Exception {
		Session session = null;
		List<NicUploadJobDto> lst = null;
		try {
			session = this.openSession();
			Query query = session.getNamedQuery("packageIDDefault");
			query.setString("handoverId", packageId);
			query.setString("type", type);
			List<Object> list = query.list();
			Iterator itr = list.iterator();
			if (CollectionUtils.isNotEmpty(list)) {
				lst = new ArrayList<NicUploadJobDto>();
				while (itr.hasNext()) {
					NicUploadJobDto countP = new NicUploadJobDto();
					Object[] record = (Object[]) itr.next();
					countP.setPackageId(record[0].toString());
					countP.setRegSiteCode(record[1].toString());
					lst.add(countP);
				}
			}
		} catch (HibernateException ex) {
			throw new Exception(ex);
		} finally {
			session.close();
		}
		return lst;
	}

	@Override
	public String getSitePerso(String transactionId) throws Exception {
		Session session = null;
		String code = "";
		try {
			session = this.openSession();
			Query query = session.getNamedQuery("sitePerso");
			query.setString("tranId", transactionId);

			@SuppressWarnings("unchecked")
			List<String> list = query.list();
			if (CollectionUtils.isNotEmpty(list)) {
				for (int i = 0; i < list.size(); i++) {
					code = list.get(i);
				}
			}
		} catch (HibernateException ex) {
			throw new Exception(ex);
		} finally {
			session.close();
		}
		return code;
	}

	@Override
	public BaseModelSingle<NicTransaction> findNicTransactionById(
			String transactionId) throws Exception {
		try {
			NicTransaction nicTran = this.findById(transactionId);
			if (nicTran != null) {
				return new BaseModelSingle<NicTransaction>(nicTran, true, null);
			}
		} catch (Exception e) {
			return new BaseModelSingle<NicTransaction>(null, false,
					CreateMessageException.createMessageException(e)
							+ " - findListHandoverByPackageId: "
							+ transactionId + " - thất bại.");
		}
		return new BaseModelSingle<NicTransaction>(null, true, null);
	}

	@Override
	public BaseModelSingle<Void> saveOrUpdateTransaction(NicTransaction tran) {
		try {
			this.saveOrUpdate(tran);
			return new BaseModelSingle<Void>(null, true, null);
		} catch (Exception e) {
			e.printStackTrace();
			return new BaseModelSingle<Void>(null, false,
					CreateMessageException.createMessageException(e)
							+ " - updateOrSaveTransaction - "
							+ tran.getTransactionId() + " - thất bại.");
		}
	}

	@Override
	public BaseModelSingle<String> saveTransaction(NicTransaction tran) {
		try {
			String id = this.save(tran);
			return new BaseModelSingle<String>(id, true, null);
		} catch (Exception e) {
			e.printStackTrace();
			return new BaseModelSingle<String>(null, false,
					CreateMessageException.createMessageException(e)
							+ " - updateOrSaveTransaction - "
							+ tran.getTransactionId() + " - thất bại.");

		}
	}

	@Override
	public List<NicTransaction> findTranByPersonCode(String personCode)
			throws Exception {
		List<NicTransaction> list = null;
		try {
			DetachedCriteria criteria = DetachedCriteria
					.forClass(getPersistentClass());
			criteria.addOrder(Order.desc("createDatetime"));
			if (StringUtils.isNotBlank(personCode)) {
				criteria.add(Restrictions.eq("personCode", personCode));
				list = this.findAll(criteria);
			}
		} catch (Exception e) {
			throw e;
		}
		return list;
	}

	@Override
	public List<NicTransaction> findTranByPersonCode(String[] listPersonCode)
			throws Exception {
		List<NicTransaction> list = null;
		try {
			DetachedCriteria criteria = DetachedCriteria
					.forClass(getPersistentClass());
			if (listPersonCode.length > 0) {
				criteria.add(Restrictions.in("personCode", listPersonCode));
				list = this.findAll(criteria);
			}
		} catch (Exception e) {
			throw e;
		}
		return list;
	}

	@Override
	public List<NicTransaction> findTranByArchiveCode(String archiveCode)
			throws Exception {
		List<NicTransaction> list = null;
		try {
			DetachedCriteria criteria = DetachedCriteria
					.forClass(getPersistentClass());
			if (StringUtils.isNotBlank(archiveCode)) {
				criteria.add(Restrictions.eq("archiveCode", archiveCode));
				list = this.findAll(criteria);
			}
		} catch (Exception e) {
			throw e;
		}
		return list;
	}

	@Override
	public List<NicTransaction> findTranByNinAndTypePassport(String nin,
			String passportType) throws Exception {
		List<NicTransaction> list = null;
		try {
			DetachedCriteria criteria = DetachedCriteria
					.forClass(getPersistentClass());
			criteria.add(Restrictions.eq("nin", nin));
			criteria.add(Restrictions.eq("passportType", passportType));
			list = this.findAll(criteria);
		} catch (Exception e) {
			throw e;
		}
		return list;
	}

	@Override
	public List<NicTransaction> findAllByListTranId(String[] listTranId) {
		List<NicTransaction> listRs = null;
		try {
			DetachedCriteria criteria = DetachedCriteria
					.forClass(getPersistentClass());
			criteria.add(Restrictions.in("transactionId", listTranId));
			listRs = this.findAll(criteria);
		} catch (Exception e) {
			throw e;
		}
		return listRs;
	}

	@Override
	public PaginatedResult<NicUploadJobDto> getResultPassportInStore(
			String passportType, String passportNo, String status,
			String startDate, String endDate, String fullName, int pageNo,
			int pageSize) {
		List<NicUploadJobDto> listDto = null;
		// List<EppPerson> list = null;
		int total = 0;
		SessionImpl session = (SessionImpl) this.openSession();
		SimpleDateFormat dateFomat = new SimpleDateFormat(
				DateUtil.FORMAT_DD_MM_YYYY);
		try {
			int tag = 0;
			if (StringUtils.isNotBlank(status))
				tag = Integer.parseInt(status);
			CallableStatement callableStatement = session
					.connection()
					.prepareCall(
							"{call Epp_central.GetResultPassport(?, ?, ?, ?, ?, ?, ?, ?, ?)}");
			callableStatement.setInt(1, pageSize);
			callableStatement.setInt(2, pageNo);
			callableStatement.setString(3, startDate);
			callableStatement.setString(4, endDate);
			callableStatement.setString(5, passportNo);
			callableStatement.setString(6, passportType);
			callableStatement.setString(7,
					HelperClass.removeAccent(fullName.trim()).toUpperCase());
			callableStatement.setInt(8, tag);
			callableStatement.registerOutParameter(9, OracleTypes.CURSOR);
			callableStatement.execute();
			ResultSet resultSet = (ResultSet) callableStatement.getObject(9);
			if (resultSet != null) {
				listDto = new ArrayList<NicUploadJobDto>();
				while (resultSet.next()) {
					NicUploadJobDto person = new NicUploadJobDto();
					String tranIdStr = resultSet.getString("TRANID");
					String isppeStr = resultSet.getString("ISPPE");
					String pptypeStr = resultSet.getString("PPTYPE");
					String regcodeStr = resultSet.getString("REGCODE");
					String surnameStr = resultSet.getString("SURNAME");
					String middlenameStr = resultSet.getString("MIDDLENAME");
					String firstnameStr = resultSet.getString("FIRSTNAME");
					String investatusStr = resultSet.getString("INVESTATUS");
					Date doaStr = resultSet.getDate("DOA");
					String ninStr = resultSet.getString("NIN");
					String persoregstatusStr = resultSet
							.getString("PERSOREGSTATUS");
					Date doiStr = resultSet.getDate("DOI");
					Date doeStr = resultSet.getDate("DOE");
					String statusStr = resultSet.getString("STATUS");
					String packidStr = resultSet.getString("PACKID");
					String ppnoStr = resultSet.getString("PPNO");
					// Long uploadJobId = resultSet.getLong("JOBID");
					person.setDateIssuance(doeStr);
					person.setTempPassportNo(ppnoStr);
					person.setPassportStyle(isppeStr);
					person.setPassportType(pptypeStr);
					person.setNicSiteCode(regcodeStr);
					person.setSubName(surnameStr);
					person.setMidName(middlenameStr);
					person.setFirstName(firstnameStr);
					person.setInvestigationStatus(investatusStr);
					person.setEsColectionDate((doaStr != null) ? dateFomat
							.format(doaStr) : "");
					person.setNin(ninStr);
					person.setPersonRegStatus(persoregstatusStr);
					person.setDateIssuce((doiStr != null) ? dateFomat
							.format(doiStr) : "");
					person.setDateEprity((doeStr != null) ? dateFomat
							.format(doeStr) : "");
					person.setStatus(statusStr);
					person.setPassportNo(ppnoStr);
					person.setDocPackage(packidStr);
					person.setTransactionId(tranIdStr);
					listDto.add(person);
				}
			}

			CallableStatement callableStatementCount = session
					.connection()
					.prepareCall(
							"{call Epp_central.GetResultPassport_Count(?, ?, ?, ?, ?, ?, ?, ?, ?)}");
			callableStatementCount.setInt(1, pageSize);
			callableStatementCount.setInt(2, pageNo);
			callableStatementCount.setString(3, startDate);
			callableStatementCount.setString(4, endDate);
			callableStatementCount.setString(5, passportNo);
			callableStatementCount.setString(6, passportType);
			callableStatementCount.setString(7,
					HelperClass.removeAccent(fullName.trim()).toUpperCase());
			callableStatementCount.setInt(8, tag);
			callableStatementCount.registerOutParameter(9, OracleTypes.CURSOR);
			callableStatementCount.execute();
			ResultSet resultSet1 = (ResultSet) callableStatementCount
					.getObject(9);
			if (resultSet1 != null) {
				while (resultSet1.next()) {
					total = resultSet1.getInt("TOTAL");
				}
			}

			PaginatedResult<NicUploadJobDto> result = new PaginatedResult<NicUploadJobDto>(
					total, pageNo, listDto);

			return result;
			// return list;
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(e.getMessage());

		} finally {
			session.close();
		}

		return null;
	}

	@Override
	public PaginatedResult<NicUploadJobDto> getResultTransactionInStore(
			String transactionId, String recieptNo, String passportNo,
			String nin, String fullName, String passportStyle, String ppStage,
			String typeInves, int pageNo, int pageSize) {
		List<NicUploadJobDto> listDto = null;
		// List<EppPerson> list = null;
		int total = 0;
		SessionImpl session = (SessionImpl) this.openSession();
		SimpleDateFormat dateFomat = new SimpleDateFormat(
				DateUtil.FORMAT_DD_MM_YYYY);
		try {
			int tag = 0;
			int tag1 = 0;
			if (StringUtils.isNotBlank(ppStage))
				tag = Integer.parseInt(ppStage);
			if (StringUtils.isNotBlank(typeInves))
				tag1 = Integer.parseInt(typeInves);
			CallableStatement callableStatement = session
					.connection()
					.prepareCall(
							"{call Epp_central.GETRESULTTRANSACTION(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)}");
			callableStatement.setInt(1, pageSize);
			callableStatement.setInt(2, pageNo);
			callableStatement.setString(3, transactionId);
			callableStatement.setString(4, recieptNo);
			callableStatement.setString(5, passportNo);
			callableStatement.setString(6, nin);
			callableStatement.setString(7, passportStyle);
			callableStatement.setInt(8, tag);
			callableStatement.setInt(9, tag1);
			String data = "";
			if (fullName != null) {
				data = fullName;
			}
			callableStatement.setString(10,
					HelperClass.removeAccent(fullName).trim().toUpperCase());
			callableStatement.registerOutParameter(11, OracleTypes.CURSOR);
			callableStatement.execute();
			ResultSet resultSet = (ResultSet) callableStatement.getObject(11);
			if (resultSet != null) {
				listDto = new ArrayList<NicUploadJobDto>();
				while (resultSet.next()) {
					NicUploadJobDto person = new NicUploadJobDto();
					String tranIdStr = resultSet.getString("TRANID");
					String repnoStr = resultSet.getString("repno");
					String ninStr = resultSet.getString("nin");
					String surnameStr = resultSet.getString("SURNAME");
					String middlenameStr = resultSet.getString("MIDDLENAME");
					String firstnameStr = resultSet.getString("FIRSTNAME");
					String contactStr = resultSet.getString("contact");
					String ppstyleStr = resultSet.getString("ppstyle");
					String pptypeStr = resultSet.getString("pptype");
					String isitecodeStr = resultSet.getString("isitecode");
					Date doaStr = resultSet.getDate("DOA");
					String persoregstatusStr = resultSet
							.getString("PERSOREGSTATUS");
					String investatusStr = resultSet.getString("INVESTATUS");
					String prevppnoStr = resultSet.getString("prevppno");
					String inveofficerStr = resultSet.getString("inveofficer");
					Date dainveStr = resultSet.getDate("dainve");
					Date daninStr = resultSet.getDate("danin");
					Date dapersonStr = resultSet.getDate("daperson");
					// String leaderStr = resultSet.getString("leader");
					String ppnoStr = resultSet.getString("PPNO");
					String statusStr = resultSet.getString("STATUS");
					String tranStatus = resultSet.getString("transtatus");
					Date doeDate = resultSet.getDate("DOE");
					// Long uploadJobId = resultSet.getLong("JOBID");
					person.setTranStatus(tranStatus);
					person.setTempPassportNo(ppnoStr);
					person.setPassportType(pptypeStr);
					person.setSubName(surnameStr);
					person.setMidName(middlenameStr);
					person.setFirstName(firstnameStr);
					person.setInvestigationStatus(investatusStr);
					person.setDateIssuance((doeDate != null) ? doeDate : null);
					person.setEsColectionDate((doaStr != null) ? dateFomat
							.format(doaStr) : "");
					person.setNin(ninStr);
					person.setPersonRegStatus(persoregstatusStr);
					person.setPersoCheckStatus(statusStr);
					person.setPassportNo(ppnoStr);
					person.setTransactionId(tranIdStr);
					person.setRecieptNo(repnoStr);
					person.setPhone(contactStr);
					person.setPassportStyle(ppstyleStr);
					person.setNicSiteCode(isitecodeStr);
					// person.setLeaderId(leaderStr);
					person.setInvestigationOfficerId(inveofficerStr);
					person.setDateApproveInvestigation((dainveStr != null) ? dateFomat
							.format(dainveStr) : "");
					person.setDateApprovePerson((dapersonStr != null) ? dateFomat
							.format(dapersonStr) : "");
					person.setDateApproveNin((daninStr != null) ? dateFomat
							.format(daninStr) : "");
					person.setPriorityString(prevppnoStr);
					listDto.add(person);
				}
			}

			CallableStatement callableStatementCount = session
					.connection()
					.prepareCall(
							"{call Epp_central.GETRESULTTRANSACTION_Count(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)}");
			callableStatementCount.setInt(1, pageSize);
			callableStatementCount.setInt(2, pageNo);
			callableStatementCount.setString(3, transactionId);
			callableStatementCount.setString(4, recieptNo);
			callableStatementCount.setString(5, passportNo);
			callableStatementCount.setString(6, nin);
			callableStatementCount.setString(7, passportStyle);
			callableStatementCount.setInt(8, tag);
			callableStatementCount.setInt(9, tag1);
			if (fullName != null) {
				data = fullName;
			}
			callableStatementCount.setString(10,
					HelperClass.removeAccent(fullName).trim().toUpperCase());
			callableStatementCount.registerOutParameter(11, OracleTypes.CURSOR);
			callableStatementCount.execute();
			ResultSet resultSet1 = (ResultSet) callableStatementCount
					.getObject(11);
			if (resultSet1 != null) {
				while (resultSet1.next()) {
					total = resultSet1.getInt("TOTAL");
				}
			}

			PaginatedResult<NicUploadJobDto> result = new PaginatedResult<NicUploadJobDto>(
					total, pageNo, listDto);

			return result;
			// return list;
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(e.getMessage());

		} finally {
			session.close();
		}

		return null;
	}

	@Override
	public PaginatedResult<NicTransaction> getPageByRegDateOrRegSite(
			Date fDate, Date tDate, String regSiteCode, int pageNo, int pageSize)
			throws Exception {
		PaginatedResult<NicTransaction> pagRs = null;
		try {
			DetachedCriteria criteria = DetachedCriteria
					.forClass(getPersistentClass());
			if (StringUtils.isNotBlank(regSiteCode) || fDate != null
					|| tDate != null) {
				if (StringUtils.isNotBlank(regSiteCode)) {
					criteria.add(Restrictions.eq("regSiteCode", regSiteCode));
				}
				if (fDate != null && tDate != null) {
					criteria.add(Restrictions.between("dateOfApplication",
							fDate, tDate));
				} else if (fDate != null) {
					criteria.add(Restrictions.ge("dateOfApplication", fDate));
				} else if (tDate != null) {
					criteria.add(Restrictions.le("dateOfApplication", tDate));
				}
			}
			criteria.addOrder(Order.asc("dateOfApplication"));
			pagRs = this.findAllForPagination(criteria, pageNo, pageSize);
		} catch (Exception e) {
			throw e;
		}
		return pagRs;
	}

	@Override
	public List<NicTransaction> findByCoditions(Date fDate, Date tDate,
			String regSiteCode) throws Exception {
		List<NicTransaction> list = null;
		try {
			DetachedCriteria criteria = DetachedCriteria
					.forClass(getPersistentClass());
			if (StringUtils.isNotBlank(regSiteCode) || fDate != null
					|| tDate != null) {
				if (StringUtils.isNotBlank(regSiteCode)) {
					criteria.add(Restrictions.eq("regSiteCode", regSiteCode));
				}
				if (fDate != null && tDate != null) {
					criteria.add(Restrictions.between("dateOfApplication",
							fDate, tDate));
				} else if (fDate != null) {
					criteria.add(Restrictions.ge("dateOfApplication", fDate));
				} else if (tDate != null) {
					criteria.add(Restrictions.le("dateOfApplication", tDate));
				}
			}
			criteria.addOrder(Order.asc("dateOfApplication"));
			list = this.findAll(criteria);
		} catch (Exception e) {
			throw e;
		}
		return list;
	}

	@Override
	public List<NicTransaction> getByRegDateOrRegSite(Date fDate, Date tDate,
			String regSiteCode) throws Exception {
		List<NicTransaction> listTran = null;
		try {
			DetachedCriteria criteria = DetachedCriteria
					.forClass(getPersistentClass());
			if (StringUtils.isNotBlank(regSiteCode) || fDate != null
					|| tDate != null) {
				if (StringUtils.isNotBlank(regSiteCode)) {
					criteria.add(Restrictions.eq("regSiteCode", regSiteCode));
				}
				if (fDate != null && tDate != null) {
					criteria.add(Restrictions.between("dateOfApplication",
							fDate, tDate));
				} else if (fDate != null) {
					criteria.add(Restrictions.ge("dateOfApplication", fDate));
				} else if (tDate != null) {
					criteria.add(Restrictions.le("dateOfApplication", tDate));
				}
			}
			criteria.addOrder(Order.asc("dateOfApplication"));
			listTran = this.findAll(criteria);
		} catch (Exception e) {
			throw e;
		}
		return listTran;
	}

	@Override
	public PaginatedResult<ReportRegProcessData> getPageByCodition(
			String fromDate, String toDate, String regSiteCode,
			String printSiteCode, int pageNo, int pageSize) throws Exception {
		List<ReportRegProcessData> listDto = new ArrayList<ReportRegProcessData>();
		Session s = null;
		try {
			s = getHibernateTemplate().getSessionFactory().openSession();
			StringBuffer sql = null;
			int rowCount = 0;
			sql = new StringBuffer(
					"SELECT * FROM ( select reg.SURNAME_FULL, reg.MIDDLENAME_FULL, reg.FIRSTNAME_FULL, reg.GENDER, "
					+ "reg.DATE_OF_BIRTH, reg.TRANSACTION_ID, txn.NIN, doc.PASSPORT_NO, doc.STATUS, txn.REG_SITE_CODE, "
					+ "doc.PRINTING_SITE, doc.DATE_OF_ISSUE, row_number() over(order by doc.DATE_OF_ISSUE asc nulls last) r__, "
					+ "reg.DEF_DATE_OF_BIRTH, txn.DATE_OF_APPLICATION, COUNT(reg.TRANSACTION_ID) OVER () RESULT_COUNT  from EPP_CENTRAL.EPP_TRANSACTION txn "
					+ "INNER JOIN EPP_CENTRAL.EPP_REGISTRATION_DATA reg ON reg.TRANSACTION_ID = txn.TRANSACTION_ID  "
					+ "LEFT JOIN EPP_CENTRAL.EPP_DOCUMENT_DATA doc ON doc.TRANSACTION_ID = txn.TRANSACTION_ID "
					+ "and (doc.STATUS IN ('PACKED', 'PERSONALIZED', 'ISSUANCE', 'CANCELLED', 'NONE')) where 1=1 ");
			if (StringUtils.isNotBlank(fromDate)) {
				sql.append(" and TO_DATE(doc.DATE_OF_ISSUE ,'DD/MM/YY') >= TO_DATE('" + fromDate.substring(8, 10) + "/" + fromDate.substring(5, 7) + "/" + fromDate.substring(2, 4) + "','DD/MM/YY') ");
			}
			if (StringUtils.isNotBlank(toDate)) {
				sql.append(" and TO_DATE(doc.DATE_OF_ISSUE ,'DD/MM/YY') <= TO_DATE('" + toDate.substring(8, 10) + "/" + toDate.substring(5, 7) + "/" + toDate.substring(2, 4) + "','DD/MM/YY') ");
			}
			if (StringUtils.isNotBlank(regSiteCode)) {
				sql.append(" and txn.reg_site_code = '" + regSiteCode + "' ");
			}
			if (StringUtils.isNotBlank(printSiteCode)) {
				sql.append(" and doc.printing_site = '" + printSiteCode + "' ");
			}
			sql.append("  ORDER BY r__ ) WHERE r__ >= (((" + pageNo
						+ " -1) * " + pageSize + ") + 1) and r__ < ((" + pageNo
						+ " * " + pageSize + ") + 1 ) ");
			Query query = s.createSQLQuery(sql.toString());
			logger.info("SQL: =>>> " + sql.toString());

			List list = query.list();
			Iterator itr = list.iterator();
			if (list != null && !list.isEmpty() && list.size() > 0) {
				while (itr.hasNext()) {
					Object[] record = (Object[]) itr.next();
					ReportRegProcessData dt = new ReportRegProcessData();
					// i = i + 1;
					SimpleDateFormat dateFomat = new SimpleDateFormat(
							DateUtil.FORMAT_DDdashMMdashYYYY);
					if (record[4] != null) {
						String date = dateFomat.format(record[4]);
						if (record[13] != null) {
							String defDOB = record[13].toString();
							if (defDOB.equals("M")) {
								date = date.substring(0, 6);
							} else if (defDOB.equals("Y")) {
								date = date.substring(0, 4);
							}
						}
						dt.setDateOfBirth(date);
					} else {
						dt.setDateOfBirth("");
					}
					dt.setGender(record[3] != null ? record[3].toString() : "");
					dt.setName(HelperClass.createFullName(
							record[0] != null ? record[0].toString() : "",
							record[1] != null ? record[1].toString() : "",
							record[2] != null ? record[2].toString() : ""));
					
					dt.setTransactionId(record[5] != null ? record[5].toString() : "");

					if (record[6] != null) {
						dt.setNin(record[6].toString());
					}
					dt.setPassportNo(record[7] != null ? record[7].toString()
							: "");
					rowCount = Integer.parseInt(record[15] != null ? record[15]
							.toString() : "0");
					dt.setDocumentStatus(record[8] != null ? record[8].toString() : "");
					dt.setRegSiteName(record[9] != null ? record[9].toString() : "");
					dt.setPrintSiteName(record[10] != null ? record[10].toString() : "");
					dt.setRegDate(record[14] != null ? dateFomat.format(record[14]) : "");
					dt.setPrintDate(record[11] != null ? dateFomat.format(record[11]) : "");
					listDto.add(dt);
				}
				PaginatedResult<ReportRegProcessData> result = new PaginatedResult<ReportRegProcessData>(
						rowCount, pageNo, listDto);
				return result;
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			s.close();
		}
		return new PaginatedResult<>(0, pageNo, listDto);
	}

	@Override
	public List<ReportRegProcessData> getAllByCodition(String fromDate,
			String toDate, String regSiteCode, String printSiteCode)
			throws Exception {
		List<ReportRegProcessData> listDto = new ArrayList<ReportRegProcessData>();
		Session s = null;
		try {
			s = getHibernateTemplate().getSessionFactory().openSession();
			StringBuffer sql = null;
			sql = new StringBuffer(
					"SELECT reg.SURNAME_FULL, reg.MIDDLENAME_FULL, reg.FIRSTNAME_FULL, reg.GENDER, reg.DATE_OF_BIRTH, "
					+ "reg.TRANSACTION_ID, txn.NIN, doc.PASSPORT_NO, doc.STATUS, txn.REG_SITE_CODE, doc.PRINTING_SITE, "
					+ "doc.DATE_OF_ISSUE, reg.DEF_DATE_OF_BIRTH, txn.DATE_OF_APPLICATION from EPP_CENTRAL.EPP_TRANSACTION txn "
					+ "INNER JOIN EPP_CENTRAL.EPP_REGISTRATION_DATA reg ON reg.TRANSACTION_ID = txn.TRANSACTION_ID  "
					+ "LEFT JOIN EPP_CENTRAL.EPP_DOCUMENT_DATA doc ON doc.TRANSACTION_ID = txn.TRANSACTION_ID "
					+ "and (doc.STATUS IN ('PACKED', 'PERSONALIZED', 'ISSUANCE', 'CANCELLED', 'NONE')) where 1=1 ");
			if (StringUtils.isNotBlank(fromDate)) {
				sql.append(" and TO_DATE(doc.DATE_OF_ISSUE ,'DD/MM/YY') >= TO_DATE('" + fromDate.substring(8, 10) + "/" + fromDate.substring(5, 7) + "/" + fromDate.substring(2, 4) + "','DD/MM/YY') ");
			}
			if (StringUtils.isNotBlank(toDate)) {
				sql.append(" and TO_DATE(doc.DATE_OF_ISSUE ,'DD/MM/YY') <= TO_DATE('" + toDate.substring(8, 10) + "/" + toDate.substring(5, 7) + "/" + toDate.substring(2, 4) + "','DD/MM/YY') ");
			}
			if (StringUtils.isNotBlank(regSiteCode)) {
				sql.append(" and txn.reg_site_code = '" + regSiteCode + "' ");
			}
			if (StringUtils.isNotBlank(printSiteCode)) {
				sql.append(" and doc.printing_site = '" + printSiteCode + "' ");
			}
			sql.append("  ORDER BY doc.DATE_OF_ISSUE asc nulls last");
			Query query = s.createSQLQuery(sql.toString());
			logger.info("SQL: =>>> " + sql.toString());

			List list = query.list();
			Iterator itr = list.iterator();
			if (list != null && !list.isEmpty() && list.size() > 0) {
				while (itr.hasNext()) {
					Object[] record = (Object[]) itr.next();
					ReportRegProcessData dt = new ReportRegProcessData();
					// i = i + 1;
					SimpleDateFormat dateFomat = new SimpleDateFormat(
							DateUtil.FORMAT_DDdashMMdashYYYY);
					if (record[4] != null) {
						String date = dateFomat.format(record[4]);
						if (record[12] != null) {
							String defDOB = record[12].toString();
							if (defDOB.equals("M")) {
								date = date.substring(0, 6);
							} else if (defDOB.equals("Y")) {
								date = date.substring(0, 4);
							}
						}
						dt.setDateOfBirth(date);
					} else {
						dt.setDateOfBirth("");
					}
					dt.setGender(record[3] != null ? record[3].toString() : "");
					dt.setName(HelperClass.createFullName(
							record[0] != null ? record[0].toString() : "",
							record[1] != null ? record[1].toString() : "",
							record[2] != null ? record[2].toString() : ""));
					
					dt.setTransactionId(record[5] != null ? record[5].toString() : "");

					if (record[6] != null) {
						dt.setNin(record[6].toString());
					}
					dt.setPassportNo(record[7] != null ? record[7].toString()
							: "");
					dt.setDocumentStatus(record[8] != null ? record[8].toString() : "");
					dt.setRegSiteName(record[9] != null ? record[9].toString() : "");
					dt.setPrintSiteName(record[10] != null ? record[10].toString() : "");
					dt.setRegDate(record[13] != null ? dateFomat.format(record[13]) : "");
					dt.setPrintDate(record[11] != null ? dateFomat.format(record[11]) : "");
					listDto.add(dt);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			s.close();
		}
		return listDto;
	}

	@Override
	public List<Integer> getCountTransmissFromA08() throws Exception {
		List<Integer> list = null;
		Session s = null;
		try {
			s = getHibernateTemplate().getSessionFactory().openSession();
			StringBuffer sql = new StringBuffer("select * from SYS.vw_a08_to_ttll");
			Query query = s.createSQLQuery(sql.toString());
			List<Object> listRs = query.list();
			Iterator itr = listRs.iterator();
			if (CollectionUtils.isNotEmpty(listRs)) {
				list = new ArrayList<Integer>();
				while (itr.hasNext()) {
					Object[] record = (Object[]) itr.next();
					for (int i = 0; i < record.length; i++) {
						BigDecimal b = (BigDecimal)(record[i] != null ? record[i] : 0);
						list.add(b.intValueExact());
					}
				}
			}
		} catch (Exception e) {
			throw e;
		}finally{
			if (s != null) {
				s.close();
			}
		}
		return list;
	}

}
