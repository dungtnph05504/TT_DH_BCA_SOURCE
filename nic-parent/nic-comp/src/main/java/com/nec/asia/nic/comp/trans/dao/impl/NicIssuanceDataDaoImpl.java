package com.nec.asia.nic.comp.trans.dao.impl;

import java.net.InetAddress;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.FetchMode;
import org.hibernate.HibernateException;
import org.hibernate.Transaction;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Property;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.nec.asia.nic.comp.job.dto.LogInfoDTO;
import com.nec.asia.nic.comp.trans.dao.NicIssuanceDataDao;
import com.nec.asia.nic.comp.trans.domain.NicIssuanceData;
import com.nec.asia.nic.comp.trans.domain.NicIssuanceDataId;
import com.nec.asia.nic.comp.trans.domain.NicRegistrationData;
import com.nec.asia.nic.comp.trans.domain.NicTransaction;
import com.nec.asia.nic.comp.trans.domain.NicTransactionLog;
import com.nec.asia.nic.comp.trans.domain.VNicTxnLog;
import com.nec.asia.nic.comp.trans.dto.CardStatusRptDTO;
import com.nec.asia.nic.comp.trans.dto.RICBatchCardInfoDto;
import com.nec.asia.nic.comp.trans.dto.RICTxnRptDto;
import com.nec.asia.nic.framework.PaginatedResult;
import com.nec.asia.nic.framework.dao.DaoException;
import com.nec.asia.nic.framework.dao.impl.GenericHibernateDao;
import com.nec.asia.nic.util.TransLogInfoXmlConvertor;
import com.nec.asia.nic.utils.DateUtil;

//defined in spring-servies-card.xml
@Repository("issuanceDataDao")

/* 
 * Modification History:
 *  
 * 06 Sep 2013 (chris): modify to pass workstationId and officerId.
 * 09 Sep 2013 (chris): add method findByTransacionId
 *  1 Nov 2013 (Swapna):added getLatestNicIssuanceData method.
 * 13 Nov 2013 (chris): add method findByPersoRefId
 * 15 Nov 2013 (chris): add method findByNin
 * 03 Dec 2013 (chris): set updateDate in updateStatusByTransactionId
 * 10 Dec 2013 (Peddi Swapna): Added new methods to support the NIC Statistics modules during the code merge. 
 * 08 jan 2014 (priya): add method getRicBatchCardInfoRptRecordList(),getCardCollectedStatusRptRecordList(),getCardRejectedRptRecordList().
 * 06 Feb 2014 (chris): add condition to prevent status override by earlier update.
 * 10 Feb 2014 (priya): add method getRicLostNfoundRptRecordList,getCardDeActRptRecordList,getCardReActRptRecordList,getCardExpiredStatusRptRecordList.
 * 21 Mar 2014 (chris): fix bugs in findAllForPagination for wrong property used (eg issSiteCode).
 * 06 Jun 2013 (chris): rename findByTransacionId to findByTransactionId
 */
public class NicIssuanceDataDaoImpl extends
		GenericHibernateDao<NicIssuanceData, NicIssuanceDataId> implements
		NicIssuanceDataDao {
	public final Logger logger = LoggerFactory.getLogger(this.getClass());

	public static TransLogInfoXmlConvertor util = new TransLogInfoXmlConvertor();

	@Override
	public List<NicIssuanceData> findByPeriod(String siteCode, Date start,
			Date end) throws DaoException {
		List<NicIssuanceData> resultList = null;
		try {
			DetachedCriteria criteria = DetachedCriteria.forClass(
					NicIssuanceData.class, "issuanceData");
			DetachedCriteria transaction = criteria
					.createCriteria("nicTransaction");
			transaction.add(Restrictions.eq("issSiteCode", siteCode));
			transaction.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);

			if (start != null && end != null)
				criteria.add(Restrictions.between("packageDate", start, end));

			criteria.add(Restrictions.isNotNull("packageId"));
			criteria.add(Restrictions.eq("receivedCardFlag", false));

			resultList = (List<NicIssuanceData>) this.getHibernateTemplate().findByCriteria(criteria);
			if (CollectionUtils.isNotEmpty(resultList)) {
				getHibernateTemplate().initialize(resultList);
			}
			return resultList;
		} catch (HibernateException ex) {
			throw new DaoException(ex);
		}
	}

	public List<String> findByCCn(String ccn) {

		// DetachedCriteria detachedCriteria =
		// DetachedCriteria.forClass(NicIssuanceData.class);
		DetachedCriteria detachedCriteria = DetachedCriteria
				.forClass(getPersistentClass());

		if (StringUtils.isNotBlank(ccn)) {
			detachedCriteria.add(Restrictions.eq("ccn", ccn))
					.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		}

		// List<NicIssuanceData> resultIssUance =
		// detachedCriteria.getExecutableCriteria(getSession()).list();
		List<NicIssuanceData> resultIssUance = (List<NicIssuanceData>) this.getHibernateTemplate()
				.findByCriteria(detachedCriteria);
		List<String> transIdList = new ArrayList<String>();

		for (NicIssuanceData issuanceObj : resultIssUance) {
			transIdList.add(issuanceObj.getId().getTransactionId());
		}

		return transIdList;
	}

	@Override
	public NicTransaction getTransactionIssuanceDatas(NicTransaction transObj) {

		DetachedCriteria detachedCriteria = DetachedCriteria
				.forClass(getPersistentClass());

		if (StringUtils.isNotBlank(transObj.getTransactionId())) {
			detachedCriteria.add(
					Restrictions.eq("id.transactionId",
							transObj.getTransactionId())).setResultTransformer(
					Criteria.DISTINCT_ROOT_ENTITY);
		}

		List<NicIssuanceData> resultIssUance = this.findAll(detachedCriteria);

		Set<NicIssuanceData> setIssuance = new HashSet<NicIssuanceData>(
				resultIssUance);
//		transObj.setNicIssuanceDatas(setIssuance);

		return transObj;
	}

	@Override
	public List<NicIssuanceData> findDataByCCn(String ccn) throws DaoException {
		DetachedCriteria detachedCriteria = DetachedCriteria
				.forClass(NicIssuanceData.class);
		List<NicIssuanceData> resultList = null;
		if (StringUtils.isNotBlank(ccn)) {
			detachedCriteria.add(Restrictions.eq("ccn", ccn))
					.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
			resultList = (List<NicIssuanceData>) this.getHibernateTemplate().findByCriteria(
					detachedCriteria);// detachedCriteria.getExecutableCriteria(this.getSession()).list();
		}
		return resultList;

	}

	@SuppressWarnings("unchecked")
	@Override
	public List<NicIssuanceData> findByPackageId(String packageId)
			throws DaoException {
		List<NicIssuanceData> resultList = null;
		DetachedCriteria detachedCriteria = DetachedCriteria
				.forClass(NicIssuanceData.class);
		if (StringUtils.isNotBlank(packageId)) {
			// detachedCriteria.add(Restrictions.eq("packageId", packageId))
			// .setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
			// resultList =
			// detachedCriteria.getExecutableCriteria(this.getSession()).list();
			detachedCriteria.add(Restrictions.eq("packageId", packageId));
			resultList = (List<NicIssuanceData>) this.getHibernateTemplate().findByCriteria(
					detachedCriteria);
		}
		return resultList;

	}

	// 09 Oct 2013 (chris) add method findByTransactionId - start
	public List<NicIssuanceData> findByTransactionId(String transactionId)
			throws DaoException {
		List<NicIssuanceData> resultList = null;
		DetachedCriteria detachedCriteria = DetachedCriteria
				.forClass(NicIssuanceData.class);
		if (StringUtils.isNotBlank(transactionId)) {
			detachedCriteria.add(
					Restrictions.eq("id.transactionId", transactionId))
					.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
			resultList = detachedCriteria.getExecutableCriteria(
					this.getSession()).list();
		}
		return resultList;
	}

	public void saveOrUpdate(NicIssuanceData issData) {
		String wkstnId = "SYSTEM";
		try {
			java.net.InetAddress localMachine = InetAddress.getLocalHost();
			wkstnId = localMachine.getHostName().toUpperCase();
		} catch (Exception ex) {
		}
		if (issData.getUpdateWkstnId() == null)
			issData.setUpdateWkstnId(wkstnId);
		super.saveOrUpdate(issData);
	}

	// 09 Oct 2013 (chris) add method findByTransactionId - end

	@Override
	public void updateStatusByPackageId(List<String> packageIdList,
			String status) throws DaoException {
		String hql = "update from NicIssuanceData set cardStatus = :status  where packageId = :packageId";
		Transaction transaction = this.getSession().getTransaction();
		try {

			for (String packageId : packageIdList) {
				this.getSession().createQuery(hql)
						.setString("packageId", packageId)
						.setString("status", status).executeUpdate();
			}
		} catch (HibernateException ex) {
			// transaction.rollback();
			throw new DaoException(ex);
		}
	}

	@Override
	public void updateStatusByTransactionId(String transactionId,
			String status, String officerId, String workstationId)
			throws DaoException {
		String hql = "update from NicTransaction set "
				+ "transactionStatus = :status, updateBy = :officerId, updateWkstnId = :workstationId, updateDate = sysdate "
				+ "where transactionId = :transactionId";
		Transaction transaction = this.getSession().getTransaction();
		try {
			this.getSession().createQuery(hql)
					.setString("transactionId", transactionId)
					.setString("status", status)
					.setString("officerId", officerId)
					.setString("workstationId", workstationId).executeUpdate();
		} catch (HibernateException ex) {
			// transaction.rollback();
			throw new DaoException(ex);
		}
	}

	public void updateTransactionByPackageId(String dispatchId,
			List<String> packageIdList, String status, String officerId,
			String workstationId) throws DaoException {
		StringBuffer sql = new StringBuffer("UPDATE NICDB.NIC_TRANSACTION SET TRANSACTION_STATUS = :status ");
		sql.append("WHERE TRANSACTION_ID IN (SELECT TRANSACTION_ID FROM NICDB.NIC_ISSUANCE_DATA WHERE PACKAGE_ID IN (:packageIdList)) ");
		sql.append("AND TRANSACTION_STATUS in ('NIC_PERSO_DATA_PREPARED','NIC_PERSO_REQUEST_SUBMITTED','NIC_TX_COMPLETD','PERSO_ERROR','PERSO_PERSONALIZED') ");
		Transaction transaction = this.getSession().getTransaction();
		try {
			int txnCount = this.getSession().createSQLQuery(sql.toString())
					.setString("status", status)
					.setParameterList("packageIdList", packageIdList)
					.executeUpdate();
			logger.info(
					"[updateTransactionByPackageId] packageIdList:{}, num of record[NICDB.NIC_TRANSACTION] updated: {}",
					packageIdList, txnCount);

			StringBuffer updateIssDataSql = new StringBuffer(
					"UPDATE NICDB.NIC_ISSUANCE_DATA ");
			updateIssDataSql
					.append("SET DISPATCH_ID = :dispatchId, UPDATE_BY = :officerId, UPDATE_WKSTN_ID = :workstationId, UPDATE_DATE = sysdate ");
			updateIssDataSql.append("WHERE PACKAGE_ID IN (:packageIdList)");
			int issDataCount = this.getSession()
					.createSQLQuery(updateIssDataSql.toString())
					.setString("dispatchId", dispatchId)
					.setString("officerId", officerId)
					.setString("workstationId", workstationId)
					.setParameterList("packageIdList", packageIdList)
					.executeUpdate();
			logger.info(
					"[updateTransactionByPackageId] dispatchId:{}, num of record[NICDB.NIC_ISSUANCE_DATA] updated: {}",
					dispatchId, issDataCount);
		} catch (HibernateException ex) {

			throw new DaoException(ex);
		}
	}

	public void updateTransactionByCcn(String ccn, String transactionStatus,
			String officerId, String workstationId) throws DaoException {
		StringBuffer sql = new StringBuffer(
				"UPDATE NICDB.NIC_TRANSACTION SET TRANSACTION_STATUS = :status ");
		sql.append(", UPDATE_BY = :officerId, UPDATE_WKSTN_ID = :workstationId, UPDATE_DATE = sysdate ");
		sql.append("WHERE TRANSACTION_ID IN (SELECT TRANSACTION_ID FROM NICDB.NIC_ISSUANCE_DATA WHERE CCN = :ccn)");
		Transaction transaction = this.getSession().getTransaction();
		try {
			logger.debug("[updateTransactionByCcn], ccn={}, status={}", ccn,
					transactionStatus);
			int txnCount = this.getSession().createSQLQuery(sql.toString())
					.setString("status", transactionStatus)
					.setString("ccn", ccn).setString("officerId", officerId)
					.setString("workstationId", workstationId).executeUpdate();
			logger.info(
					"[updateTransactionByCcn] ccn:{}, num of record[NICDB.NIC_TRANSACTION] updated: {}",
					ccn, txnCount);
		} catch (HibernateException ex) {

			throw new DaoException(ex);
		}
	}

	@Override
	public PaginatedResult<NicIssuanceData> findAllForPaginationByFilter(
			NicIssuanceData nicIssuanceData, int pageNo, int pageSize,
			boolean ignoreCase, boolean enableLike, Order order)
			throws DaoException {
		DetachedCriteria criteria = DetachedCriteria
				.forClass(NicIssuanceData.class);

		if (StringUtils.isNotEmpty(nicIssuanceData.getNin())) {
			criteria.add(Restrictions.ilike("nin", nicIssuanceData.getNin()
					.toLowerCase()));
		}
		if (StringUtils.isNotEmpty(nicIssuanceData.getCcn())) {
			criteria.add(Restrictions.ilike("ccn", nicIssuanceData.getCcn()
					.toLowerCase()));
		}

		return findAllForPagination(criteria, pageNo, pageSize, order);
	}

	@Override
	public NicIssuanceData getLatestNicIssuanceData(String transactionId)
			throws DaoException {
		NicIssuanceData nicIssuanceData = null;
		List<NicIssuanceData> resultList = null;
		try {
			DetachedCriteria criteria = DetachedCriteria.forClass(NicIssuanceData.class);

			if (StringUtils.isNotBlank(transactionId)) {
				criteria.add(Restrictions.eq("id.transactionId", transactionId));
			}
			criteria.addOrder(Order.desc("createDate"));
			resultList = (List<NicIssuanceData>) this.getHibernateTemplate().findByCriteria(criteria);// (List<NicIssuanceData>)criteria.getExecutableCriteria(this.getSession()).list();

			if (CollectionUtils.isNotEmpty(resultList)) {
				nicIssuanceData = resultList.get(0);
				if (StringUtils.isBlank(nicIssuanceData.getCcn())) {
					for (NicIssuanceData tempIssData: resultList) {
						if (StringUtils.isNotBlank(tempIssData.getCcn())) {
							nicIssuanceData = tempIssData;
							break;
						}
					}
				}
			}
		} catch (HibernateException ex) {
			throw new DaoException("Error occurred while getting the latest Issuance Data. Exception: "	+ ex);
		}
		return nicIssuanceData;

	}

	@SuppressWarnings("unchecked")
	@Override
	public List<NicIssuanceData> findByPersoRefId(String persoRefId)
			throws DaoException {
		List<NicIssuanceData> resultList = null;
		try {
			DetachedCriteria criteria = DetachedCriteria
					.forClass(NicIssuanceData.class);
			criteria.add(Restrictions.eq("id.persoRefId", persoRefId));
			resultList = (List<NicIssuanceData>) this.getHibernateTemplate().findByCriteria(criteria);
		} catch (HibernateException ex) {
			throw new DaoException(
					"Error occurred while getting the Issuance Data by Perso Ref Id. Exception: "
							+ ex);
		}
		return resultList;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<NicIssuanceData> findByNin(String nin) throws DaoException {
		List<NicIssuanceData> resultList = null;
		try {
			DetachedCriteria criteria = DetachedCriteria
					.forClass(NicIssuanceData.class);
			criteria.add(Restrictions.eq("nin", nin));
			resultList = (List<NicIssuanceData>) this.getHibernateTemplate().findByCriteria(criteria);
		} catch (HibernateException ex) {
			throw new DaoException(
					"Error occurred while getting the Issuance Data by Nin. Exception: "
							+ ex);
		}
		return resultList;
	}

	@Override
	public void updateStatusByTransactionId(String transactionId, String status)
			throws DaoException {
		String hql = "update from NicTransaction set transactionStatus = :status where transactionId = :transactionId";
		Transaction transaction = this.getSession().getTransaction();
		try {
			this.getSession().createQuery(hql)
					.setString("transactionId", transactionId)
					.setString("status", status).executeUpdate();
		} catch (HibernateException ex) {
			// transaction.rollback();
			throw new DaoException(ex);
		}
	}

	@Override
	public PaginatedResult<NicIssuanceData> findAllForPagination(
			NicIssuanceData issuanceData, Date regDatefrom, Date regDateTo,
			Date collectDatefrom, Date collectDateto, int pageNo, int pageSize,
			boolean ignoreCase, boolean enableLike, Order order)
			throws DaoException {

		DetachedCriteria criteria = DetachedCriteria
				.forClass(NicIssuanceData.class);

		if (StringUtils.isNotEmpty(issuanceData.getNin())) {
			criteria.add(Restrictions.like("nin", issuanceData.getNin(),
					MatchMode.ANYWHERE).ignoreCase());
		}
		if (StringUtils.isNotEmpty(issuanceData.getCcn())) {
			criteria.add(Restrictions.eq("ccn", issuanceData.getCcn()));
		}
		if (StringUtils.isNotEmpty(issuanceData.getCardStatus())) {
			criteria.add(Restrictions.eq("cardStatus",
					issuanceData.getCardStatus()));
		}
		if (StringUtils.isNotEmpty(issuanceData.getPackageId())) {
			criteria.add(Restrictions.eq("packageId",
					issuanceData.getPackageId()));
		}

		if (collectDateto != null) {
			collectDateto = DateUtil.getEndOfDay(collectDateto);
		}
		
		//21 Mar 2014 (chris) change to include null collectionDate -- start
		if (collectDatefrom != null && collectDateto != null) {
			criteria.add(	Restrictions.or(Restrictions.isNull("collectionDate"), 
					Restrictions.between("collectionDate",	collectDatefrom, collectDateto)));
		} else if (collectDatefrom != null) {
			criteria.add(	Restrictions.or(Restrictions.isNull("collectionDate"), 
					Restrictions.gt("collectionDate", collectDatefrom)));
		} else if (collectDateto != null) {
			criteria.add(	Restrictions.or(Restrictions.isNull("collectionDate"), 
					Restrictions.lt("collectionDate", collectDateto)));
		}
		//21 Mar 2014 (chris) change to include null collectionDate -- end

		NicTransaction nicTransaction = issuanceData.getNicTransaction();
		if (nicTransaction != null || regDatefrom != null || regDateTo != null) {
			criteria.createAlias("nicTransaction", "nicTransaction");
			//21 Mar 2014 (chris) change issSiteCode to nicTransaction.issSiteCode
			if (nicTransaction != null
					&& StringUtils.isNotEmpty(nicTransaction.getIssSiteCode())) {
				criteria.add(Restrictions.eq("nicTransaction.issSiteCode",
						nicTransaction.getIssSiteCode()));
			}

			if (nicTransaction != null
					&& StringUtils.isNotEmpty(nicTransaction.getRegSiteCode())) {
				criteria.add(Restrictions.eq("nicTransaction.regSiteCode",
						nicTransaction.getRegSiteCode()));
			}

			if (regDateTo != null) {
				regDateTo = DateUtil.getEndOfDay(regDateTo);
			}

			if (regDatefrom != null && regDateTo != null) {
				criteria.add(Restrictions.between(
						"nicTransaction.dateOfApplication", regDatefrom,
						regDateTo));
			} else if (regDatefrom != null) {
				criteria.add(Restrictions.gt(
						"nicTransaction.dateOfApplication", regDatefrom));
			} else if (regDateTo != null) {
				criteria.add(Restrictions.lt(
						"nicTransaction.dateOfApplication", regDateTo));
			}

		}

		return findAllForPagination(criteria, pageNo, pageSize, order);
	}

	@Override
	public List<RICBatchCardInfoDto> getRicBatchCardInfoRptRecordList(
			RICBatchCardInfoDto ricBatchCardInfoRptCriteria)
			throws DaoException {
		System.out.println("Entering DAO Result >>");
		List<RICBatchCardInfoDto> records = new ArrayList<RICBatchCardInfoDto>();
		Format formatter = new SimpleDateFormat("dd-MMM-yyyy");
		DetachedCriteria criteria = DetachedCriteria
				.forClass(NicIssuanceData.class);
		criteria.setFetchMode("nicTransaction", FetchMode.JOIN);
		criteria.createAlias("nicTransaction", "t");
		if (ricBatchCardInfoRptCriteria.getCrdPkgStartDate() != null) {
			criteria.add(Restrictions.ge("packageDate",
					ricBatchCardInfoRptCriteria.getCrdPkgStartDate()));
		}
		if (ricBatchCardInfoRptCriteria.getCrdPkgEndDate() != null) {
			criteria.add(Restrictions.le("packageDate",
					ricBatchCardInfoRptCriteria.getCrdPkgEndDate()));
		}
		if (ricBatchCardInfoRptCriteria.getCrdIssueStartDate() != null) {
			criteria.add(Restrictions.ge("dateOfIssue",
					ricBatchCardInfoRptCriteria.getCrdIssueStartDate()));
		}
		if (ricBatchCardInfoRptCriteria.getCrdIssueEndDate() != null) {
			criteria.add(Restrictions.le("dateOfIssue",
					ricBatchCardInfoRptCriteria.getCrdIssueEndDate()));
		}
		// Need to Include after the Date of Expiry column
		/*
		 * if (ricBatchCardInfoRptCriteria.getCrdExpiryStartDate() != null) {
		 * criteria
		 * .add(Restrictions.ge("dateOfExpiry",ricBatchCardInfoRptCriteria
		 * .getCrdExpiryStartDate())); } if
		 * (ricBatchCardInfoRptCriteria.getCrdExpiryEndDate() != null) {
		 * criteria
		 * .add(Restrictions.le("dateOfExpiry",ricBatchCardInfoRptCriteria
		 * .getCrdExpiryEndDate())); }
		 */
		if (ricBatchCardInfoRptCriteria.getCardStatus() != null) {
			criteria.add(Restrictions.eq("cardStatus",
					ricBatchCardInfoRptCriteria.getCardStatus()));
		}
		if (ricBatchCardInfoRptCriteria.getTransStartDate() != null) {
			criteria.add(Restrictions.ge("t.dateOfApplication",
					ricBatchCardInfoRptCriteria.getTransStartDate()));
		}
		if (ricBatchCardInfoRptCriteria.getTransEndDate() != null) {
			criteria.add(Restrictions.le("t.dateOfApplication",
					ricBatchCardInfoRptCriteria.getTransEndDate()));
		}
		if (ricBatchCardInfoRptCriteria.getTxnType() != null
				&& !"ALL".equals(ricBatchCardInfoRptCriteria.getTxnType())) {
			criteria.add(Restrictions.eq("t.transactionType",
					ricBatchCardInfoRptCriteria.getTxnType()));
		}
		if (ricBatchCardInfoRptCriteria.getTxnSubType() != null
				&& !"ALL".equals(ricBatchCardInfoRptCriteria.getTxnSubType())) {
			criteria.add(Restrictions.eq("t.transactionSubtype",
					ricBatchCardInfoRptCriteria.getTxnSubType()));
		}
		if (ricBatchCardInfoRptCriteria.getRicOffice() != null
				&& "ALL".equals(ricBatchCardInfoRptCriteria.getRicOffice())) {
			criteria.add(Restrictions.like("t.regSiteCode", "RIC",
					MatchMode.START).ignoreCase());
		} else if (ricBatchCardInfoRptCriteria.getRicOffice() != null
				&& !"ALL".equals(ricBatchCardInfoRptCriteria.getRicOffice())) {
			criteria.add(Restrictions.eq("t.regSiteCode",
					ricBatchCardInfoRptCriteria.getRicOffice()));
		}
		criteria.addOrder(Order.asc("t.applnRefNo"));
		List<NicIssuanceData> list = this.findAll(criteria);
		System.out.println("DAO Result >>" + list.size());
		if (list != null && list.size() > 0) {
			for (NicIssuanceData data : list) {
				RICBatchCardInfoDto record = new RICBatchCardInfoDto();
				record.setBatchId(data.getDispatchId());
				record.setNin(data.getNin());
				record.setTxnNo(data.getId().getTransactionId());
				record.setCcNo(data.getCcn());
				if (data.getDateOfIssue() != null) {
					record.setCardIssueDate(formatter.format(data
							.getDateOfIssue()));
				}
				record.setCardStatus(data.getCardStatus());
				record.setCardExpiryDate("");// Date of Expiry need to set
				if (data.getNicTransaction().getDateOfApplication() != null) {
					record.setApplicationDate(formatter.format(data
							.getNicTransaction().getDateOfApplication()));
				}
				if (data.getPackageDate() != null) {
					record.setPackageDate(formatter.format(data
							.getPackageDate()));
				}
				record.setPkgSeqNo(data.getPackageSequence());
				if (data.getId().getTransactionId() != null) {
					DetachedCriteria criteria1 = DetachedCriteria
							.forClass(NicRegistrationData.class);
					criteria1.add(Restrictions.eq("transactionId", data.getId()
							.getTransactionId()));
					List<NicRegistrationData> regData = (List<NicRegistrationData>) this
							.getHibernateTemplate().findByCriteria(criteria1);
					if (regData != null && regData.size() > 0) {
						record.setFirstName(regData.get(0).getFirstnameFull());
						record.setSurName(regData.get(0).getSurnameFull());
						record.setGender(regData.get(0).getGender());

					}
				}
				records.add(record);
			}
		}
		return records;
	}

	@Override
	public List<CardStatusRptDTO> getCardCollectedStatusRptRecordList(
			CardStatusRptDTO cardColSrchCriteria) {
		System.out.println("Entering DAO Result >>");
		List<CardStatusRptDTO> records = new ArrayList<CardStatusRptDTO>();
		Format formatter = new SimpleDateFormat("dd-MMM-yyyy");
		DetachedCriteria criteria = DetachedCriteria
				.forClass(NicIssuanceData.class);
		criteria.setFetchMode("nicTransaction", FetchMode.JOIN);
		criteria.createAlias("nicTransaction", "t");
		if (cardColSrchCriteria.getCrdCollStartDt() != null) {
			criteria.add(Restrictions.ge("collectionDate",
					cardColSrchCriteria.getCrdCollStartDt()));
		}
		if (cardColSrchCriteria.getCrdCollEndDt() != null) {
			criteria.add(Restrictions.le("collectionDate",
					cardColSrchCriteria.getCrdCollEndDt()));
		}
		if (cardColSrchCriteria.getCardStatus() != null) {
			criteria.add(Restrictions.eq("cardStatus",
					cardColSrchCriteria.getCardStatus()));
		}
		if (cardColSrchCriteria.getIssuedBy() != null) {
			criteria.add(Restrictions.eq("issuanceOfficerId",
					cardColSrchCriteria.getIssuedBy()));
		}
		if (cardColSrchCriteria.getRicOffice() != null
				&& "ALL".equals(cardColSrchCriteria.getRicOffice())) {
			criteria.add(Restrictions.like("t.regSiteCode", "RIC",
					MatchMode.START).ignoreCase());
		} else if (cardColSrchCriteria.getRicOffice() != null
				&& !"ALL".equals(cardColSrchCriteria.getRicOffice())) {
			criteria.add(Restrictions.eq("t.regSiteCode",
					cardColSrchCriteria.getRicOffice()));
		}
		if (cardColSrchCriteria.getPkgId() != null) {
			criteria.add(Restrictions.eq("dispatchId",
					cardColSrchCriteria.getPkgId()));
		}
		criteria.addOrder(Order.asc("t.applnRefNo"));
		List<NicIssuanceData> list = this.findAll(criteria);
		System.out.println("DAO Result >>" + list.size());
		if (list != null && list.size() > 0) {
			for (NicIssuanceData data : list) {
				CardStatusRptDTO record = new CardStatusRptDTO();
				record.setTransactionNo(data.getNicTransaction()
						.getApplnRefNo());
				record.setNin(data.getNin());
				record.setCcnNo(data.getCcn());
				record.setCardIssuDt(formatter.format(data.getDateOfIssue()));
				record.setCardStatus(data.getCardStatus());
				record.setPkgSeqNo(data.getPackageSequence());
				record.setPkgId(data.getPackageId());
				record.setIssuedBy(data.getIssuanceOfficerId());
				if (data.getId().getTransactionId() != null) {
					DetachedCriteria criteria1 = DetachedCriteria
							.forClass(NicRegistrationData.class);
					criteria1.add(Restrictions.eq("transactionId", data.getId()
							.getTransactionId()));
					List<NicRegistrationData> regData = (List<NicRegistrationData>) this.getHibernateTemplate().findByCriteria(criteria1);
					if (regData != null && regData.size() > 0) {
						record.setFirstName(regData.get(0).getFirstnameFull());
						record.setSurName(regData.get(0).getSurnameFull());
//						record.setSurNameAtBirth(regData.get(0)
//								.getSurnameBirthFull());
					}
				}
				records.add(record);
			}
		}
		System.out.println("records Result >>" + records.size());
		return records;
	}

	@Override
	public List<CardStatusRptDTO> getCardRejectedRptRecordList(
			CardStatusRptDTO cardColSrchCriteria) throws DaoException {
		System.out.println("Entering DAO Result >>");
		LogInfoDTO logDTO = null;
		List<CardStatusRptDTO> records = new ArrayList<CardStatusRptDTO>();
		DetachedCriteria criteria = DetachedCriteria
				.forClass(NicIssuanceData.class);
		criteria.setFetchMode("nicTransaction", FetchMode.JOIN);
		criteria.createAlias("nicTransaction", "t");
		// IN RIC checked with Rejection Date in Issuance Table
		/*
		 * if (cardColSrchCriteria.getCrdCollStartDt() != null) {
		 * criteria.add(Restrictions.ge("collectionDate",
		 * cardColSrchCriteria.getCrdCollStartDt())); } if
		 * (cardColSrchCriteria.getCrdCollEndDt() != null) {
		 * criteria.add(Restrictions.le("collectionDate",
		 * cardColSrchCriteria.getCrdCollEndDt())); }
		 */
		if (cardColSrchCriteria.getRicOffice() != null
				&& "ALL".equals(cardColSrchCriteria.getRicOffice())) {
			criteria.add(Restrictions.like("t.issSiteCode", "RIC",
					MatchMode.START).ignoreCase());
		} else if (cardColSrchCriteria.getRicOffice() != null
				&& !"ALL".equals(cardColSrchCriteria.getRicOffice())) {
			criteria.add(Restrictions.eq("t.issSiteCode",
					cardColSrchCriteria.getRicOffice()));
		}
		criteria.add(Restrictions.eq("t.transactionStatus", "RIC_CARD_REJECTED"));
		List<NicIssuanceData> list = this.findAll(criteria);
		System.out.println("DAO Result >>" + list.size());
		if (list != null && list.size() > 0) {
			for (NicIssuanceData issuData : list) {
				CardStatusRptDTO record = new CardStatusRptDTO();
				record.setTransactionNo(issuData.getId().getTransactionId());
				record.setNin(issuData.getNin());
				record.setCcnNo(issuData.getCcn());
				record.setCardRejectDt("");// Need toSet the Rejection Date
				record.setIssuedBy(issuData.getIssuanceOfficerId());
				record.setPkgSeqNo(issuData.getPackageSequence());
				record.setPkgId(issuData.getDispatchId());
				DetachedCriteria criteria1 = DetachedCriteria
						.forClass(NicTransactionLog.class);
				criteria1.add(Restrictions.eq("refId", issuData.getId()
						.getTransactionId()));
				criteria.setProjection(Projections.projectionList()
						.add(Projections.groupProperty("refId"))
						.add(Projections.max("logId")));
				criteria1.add(Restrictions.isNotNull("logInfo"));
				criteria1
						.add(Restrictions
								.sqlRestriction("extractvalue(sys.xmltype(log_info), '//logInfoDTO/Reason/text()') is not null"));
				criteria1.add(Restrictions.ne("siteCode", "NICDC"));
				if (cardColSrchCriteria.getRejectionReason() != null
						&& !"ALL".equalsIgnoreCase(cardColSrchCriteria
								.getRejectionReason())) {
					criteria1
							.add(Restrictions
									.sqlRestriction("extractvalue(sys.xmltype(log_info), '//logInfoDTO/Reason/text()') ='"
											+ cardColSrchCriteria
													.getRejectionReason() + "'"));

				}
				List<NicTransactionLog> logInfoData = (List<NicTransactionLog>) this
						.getHibernateTemplate().findByCriteria(criteria1);
				if (logInfoData != null && logInfoData.size() > 0) {
					String logInfo = logInfoData.get(0).getLogInfo();
					try {
						if (logInfo != null) {
							logDTO = (LogInfoDTO) util.unmarshal(logInfo);
							record.setRemarks(logDTO.getRemark());
							record.setRejectionReason(logDTO.getReason());
						} else {
							record.setRemarks("");
							record.setRejectionReason("");
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
					DetachedCriteria criteria2 = DetachedCriteria
							.forClass(NicRegistrationData.class);
					criteria2.add(Restrictions.eq("transactionId", issuData
							.getId().getTransactionId()));
					List<NicRegistrationData> regData = (List<NicRegistrationData>) this
							.getHibernateTemplate().findByCriteria(criteria2);
					if (regData != null && regData.size() > 0) {
						record.setFirstName(regData.get(0).getFirstnameFull());
						record.setSurName(regData.get(0).getSurnameFull());
//						record.setSurNameAtBirth(regData.get(0)
//								.getSurnameBirthFull());
					}
					records.add(record);
				}
			}
		}
		return records;
	}

	@Override
	public List<CardStatusRptDTO> getCardExpiredStatusRptRecordList(
			CardStatusRptDTO cardExpiredSrchCriteria) throws DaoException {
		System.out.println("Entering DAO Result >>");
		Format formatter = new SimpleDateFormat("dd-MMM-yyyy");
		List<CardStatusRptDTO> records = new ArrayList<CardStatusRptDTO>();
		DetachedCriteria criteria = DetachedCriteria
				.forClass(NicIssuanceData.class);
		criteria.setFetchMode("nicTransaction", FetchMode.JOIN);
		criteria.createAlias("nicTransaction", "t");
		// Need to Do for Date_of_Expiry Column
		/*
		 * if (cardExpiredSrchCriteria.getCardCollStartDt() != null) {
		 * 
		 * } if (cardExpiredSrchCriteria.getCardCollEndDt() != null) {
		 * 
		 * }
		 */
		if (cardExpiredSrchCriteria.getTranStartDt() != null) {
			criteria.add(Restrictions.ge("t.dateOfApplication",
					cardExpiredSrchCriteria.getTranStartDt()));
		}
		if (cardExpiredSrchCriteria.getTranEndDt() != null) {
			criteria.add(Restrictions.le("t.dateOfApplication",
					cardExpiredSrchCriteria.getTranEndDt()));
		}
		if (cardExpiredSrchCriteria.getRicOffice() != null
				&& "ALL".equals(cardExpiredSrchCriteria.getRicOffice())) {
			criteria.add(Restrictions.like("t.regSiteCode", "RIC",
					MatchMode.START).ignoreCase());
		} else if (cardExpiredSrchCriteria.getRicOffice() != null
				&& !"ALL".equals(cardExpiredSrchCriteria.getRicOffice())) {
			criteria.add(Restrictions.eq("t.regSiteCode",
					cardExpiredSrchCriteria.getRicOffice()));
		}
		criteria.addOrder(Order.asc("t.applnRefNo"));
		List<NicIssuanceData> list = this.findAll(criteria);
		System.out.println("DAO Result >>" + list.size());
		if (list != null && list.size() > 0) {
			for (NicIssuanceData data : list) {
				CardStatusRptDTO record = new CardStatusRptDTO();
				record.setTransactionNo(data.getId().getTransactionId());
				record.setNin(data.getNin());
				record.setCcnNo(data.getCcn());
				record.setTxnDt(formatter.format(data.getNicTransaction()
						.getDateOfApplication()));
				record.setExpiryDt(""); // need to set Expiry Date
				record.setPkgSeqNo(data.getPackageSequence());
				record.setPkgId(data.getPackageId());
				if (data.getId().getTransactionId() != null) {
					DetachedCriteria criteria2 = DetachedCriteria
							.forClass(NicRegistrationData.class);
					criteria2.add(Restrictions.eq("transactionId", data.getId()
							.getTransactionId()));
					List<NicRegistrationData> regData = (List<NicRegistrationData>) this
							.getHibernateTemplate().findByCriteria(criteria2);
					if (regData != null && regData.size() > 0) {
						record.setFirstName(regData.get(0).getFirstnameFull());
						record.setSurName(regData.get(0).getSurnameFull());
//						record.setSurNameAtBirth(regData.get(0)
//								.getSurnameBirthFull());
					}
				}
				records.add(record);
			}
		}
		return records;
	}

	@Override
	public List<CardStatusRptDTO> getCardReActRptRecordList(
			CardStatusRptDTO cardReActSrchCriteria) throws DaoException {
		System.out.println("Entering DAO Result >>");
		Format formatter = new SimpleDateFormat("dd-MMM-yyyy");
		LogInfoDTO logDTO = null;
		List<CardStatusRptDTO> records = new ArrayList<CardStatusRptDTO>();
		DetachedCriteria criteria = DetachedCriteria
				.forClass(VNicTxnLog.class);
		/*criteria.setFetchMode("nicTransaction", FetchMode.JOIN);
		criteria.createAlias("nicTransaction", "t");
		if (cardReActSrchCriteria.getCrdCollStartDt() != null) {
			criteria.add(Restrictions.ge("collectionDate",
					cardReActSrchCriteria.getCrdCollStartDt()));
		}
		if (cardReActSrchCriteria.getCrdCollEndDt() != null) {
			criteria.add(Restrictions.le("collectionDate",
					cardReActSrchCriteria.getCrdCollEndDt()));
		}
		if (cardReActSrchCriteria.getRicOffice() != null
				&& "ALL".equals(cardReActSrchCriteria.getRicOffice())) {
			criteria.add(Restrictions.like("t.regSiteCode", "RIC",
					MatchMode.START));
		} else if (cardReActSrchCriteria.getRicOffice() != null
				&& !"ALL".equals(cardReActSrchCriteria.getRicOffice())) {
			criteria.add(Restrictions.eq("t.regSiteCode",
					cardReActSrchCriteria.getRicOffice()));
		}*/
		//criteria.setFetchMode("nicTransaction", FetchMode.JOIN);
		//criteria.createAlias("nicTransaction", "t");
		if (cardReActSrchCriteria.getCrdCollStartDt() != null) {
			criteria.add(Restrictions.ge("txnUpdateDate",
					cardReActSrchCriteria.getCrdCollStartDt()));
		}
		if (cardReActSrchCriteria.getCrdCollEndDt() != null) {
			criteria.add(Restrictions.le("txnUpdateDate",
					cardReActSrchCriteria.getCrdCollEndDt()));
		}
		if (cardReActSrchCriteria.getRicOffice() != null
				&& "ALL".equals(cardReActSrchCriteria.getRicOffice())) {
			criteria.add(Restrictions.like("regSiteCode", "RIC",
					MatchMode.START));
		} else if (cardReActSrchCriteria.getRicOffice() != null
				&& !"ALL".equals(cardReActSrchCriteria.getRicOffice())) {
			criteria.add(Restrictions.eq("regSiteCode",
					cardReActSrchCriteria.getRicOffice()));
		}
		criteria.add(Restrictions.eq("transactionStatus",
				"RIC_CARD_REACTIVATED"));
		criteria.setProjection(Projections.projectionList()
				.add(Projections.groupProperty("transactionId"))
				.add(Projections.max("logId")));
		List<NicIssuanceData> list = this.findAll(criteria);
		System.out.println("DAO Result >>" + list.size());
		if (list != null && list.size() > 0) {
			for (NicIssuanceData data : list) {
				CardStatusRptDTO record = new CardStatusRptDTO();
				record.setTransactionNo(data.getId().getTransactionId());
				record.setNin(data.getNin());
				record.setCcnNo(data.getCcn());
				record.setReDeActivationDate(formatter.format(data
						.getUpdateDate()));
				record.setCardStatus(data.getCardStatus());
				DetachedCriteria criteria1 = DetachedCriteria
						.forClass(NicTransactionLog.class);
				criteria1.add(Restrictions.eq("refId", data.getId()
						.getTransactionId()));
				criteria.setProjection(Projections.projectionList()
						.add(Projections.groupProperty("refId"))
						.add(Projections.max("logId")));
				criteria1.add(Restrictions.isNotNull("logInfo"));
				criteria1
						.add(Restrictions
								.sqlRestriction("extractvalue(sys.xmltype(log_info), '//logInfoDTO/Reason/text()') is not null"));
				criteria1.add(Restrictions.ne("siteCode", "NICDC")); 
				List<NicTransactionLog> logInfoData = (List<NicTransactionLog>) this
						.getHibernateTemplate().findByCriteria(criteria1);
				if (logInfoData != null && logInfoData.size() > 0) {
					String logInfo = logInfoData.get(0).getLogInfo();
					try {
						if (logInfo != null) {
							logDTO = (LogInfoDTO) util.unmarshal(logInfo);
							record.setRemarks(logDTO.getRemark());
							record.setRejectionReason(logDTO.getReason());
						} else {
							record.setRemarks("");
							record.setRejectionReason("");
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
				record.setIssuedBy(data.getIssuanceOfficerId());
				if (data.getId().getTransactionId() != null) {
					DetachedCriteria criteria2 = DetachedCriteria
							.forClass(NicRegistrationData.class);
					criteria1.add(Restrictions.eq("transactionId", data.getId()
							.getTransactionId()));
					@SuppressWarnings("unchecked")
					List<NicRegistrationData> regData = (List<NicRegistrationData>) this
							.getHibernateTemplate().findByCriteria(criteria2);
					if (regData != null && regData.size() > 0) {
						record.setFirstName(regData.get(0).getFirstnameFull());
						record.setSurName(regData.get(0).getSurnameFull());
//						record.setSurNameAtBirth(regData.get(0)
//								.getSurnameBirthFull());
					}
				}
				records.add(record);
			}

		}
		return records;
	}

	@Override
	public List<CardStatusRptDTO> getCardDeActRptRecordList(
			CardStatusRptDTO cardDeActSrchCriteria) throws DaoException {
		System.out.println("Entering DAO Result >>");
		LogInfoDTO logDTO = null;
		Format formatter = new SimpleDateFormat("dd-MMM-yyyy");
		List<CardStatusRptDTO> records = new ArrayList<CardStatusRptDTO>();
		DetachedCriteria criteria = DetachedCriteria
				.forClass(VNicTxnLog.class);
		/*criteria.setFetchMode("nicTransaction", FetchMode.JOIN);
		criteria.createAlias("nicTransaction", "t");*/
		if (cardDeActSrchCriteria.getCrdCollStartDt() != null) {
			criteria.add(Restrictions.ge("txnUpdateDate",
					cardDeActSrchCriteria.getCrdCollStartDt()));
		}
		if (cardDeActSrchCriteria.getCrdCollEndDt() != null) {
			criteria.add(Restrictions.le("txnUpdateDate",
					cardDeActSrchCriteria.getCrdCollEndDt()));
		}
		if (cardDeActSrchCriteria.getRicOffice() != null
				&& "ALL".equals(cardDeActSrchCriteria.getRicOffice())) {
			criteria.add(Restrictions.like("regSiteCode", "RIC",
					MatchMode.START));
		} else if (cardDeActSrchCriteria.getRicOffice() != null
				&& !"ALL".equals(cardDeActSrchCriteria.getRicOffice())) {
			criteria.add(Restrictions.eq("regSiteCode",
					cardDeActSrchCriteria.getRicOffice()));
		}
		criteria.add(Restrictions.eq("transactionStatus",
				"RIC_CARD_DEACTIVATED"));
		criteria.setProjection(Projections.projectionList()
				.add(Projections.groupProperty("transactionId"))
				.add(Projections.max("logId")));
		List<NicIssuanceData> list = this.findAll(criteria);
		System.out.println("DAO Result >>" + list.size());
		if (list != null && list.size() > 0) {
			System.out.println("After DAO Query");
			for (NicIssuanceData data : list) {
				System.out.println("Get Registration Data Details"+data.getCcn());
				CardStatusRptDTO record = new CardStatusRptDTO();
				record.setTransactionNo(data.getId().getTransactionId());
				System.out.println("Get Registration Data Details"+data.getId().getTransactionId());
				record.setNin(data.getNin());
				record.setCcnNo(data.getCcn());
				record.setReDeActivationDate(formatter.format(data
						.getUpdateDate()));
				record.setCardStatus(data.getCardStatus());
				DetachedCriteria criteria1 = DetachedCriteria
						.forClass(VNicTxnLog.class);
				criteria1.add(Restrictions.eq("transactionId", data.getId()
						.getTransactionId()));
				criteria.setProjection(Projections.projectionList()
						.add(Projections.groupProperty("transactionId"))
						.add(Projections.max("logId")));
//				criteria1.add(Restrictions.isNotNull("logInfo"));
//				criteria1
//						.add(Restrictions
//								.sqlRestriction("extractvalue(sys.xmltype(log_info), '//logInfoDTO/Reason/text()') is not null"));
				//criteria1.add(Restrictions.ne("siteCode", "NICDC")); 
				List<NicTransactionLog> logInfoData = (List<NicTransactionLog>) this
						.getHibernateTemplate().findByCriteria(criteria1);
				if (logInfoData != null && logInfoData.size() > 0) {
					String logInfo = logInfoData.get(0).getLogInfo();
					try {
						if (logInfo != null) {
							logDTO = (LogInfoDTO) util.unmarshal(logInfo);
							record.setRemarks(logDTO.getRemark());
							record.setRejectionReason(logDTO.getReason());
						} else {
							record.setRemarks("");
							record.setRejectionReason("");
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
				}else {
					record.setRemarks("");
					record.setRejectionReason("");
				}
				record.setIssuedBy(data.getIssuanceOfficerId());
				if (data.getId().getTransactionId() != null) {
					System.out.println("Get Registration Data Details");
					DetachedCriteria criteria2 = DetachedCriteria
							.forClass(NicRegistrationData.class);
					criteria1.add(Restrictions.eq("transactionId", data.getId()
							.getTransactionId())); 
					List<NicRegistrationData> regData = (List<NicRegistrationData>) this.getHibernateTemplate().findByCriteria(criteria2);
					if (regData != null && regData.size() > 0) {
						record.setFirstName(regData.get(0).getFirstnameFull());
						record.setSurName(regData.get(0).getSurnameFull());
//						record.setSurNameAtBirth(regData.get(0)
//								.getSurnameBirthFull());
					}
				}
				records.add(record);
			} 
		}
		return records;
	}
	
	@Override
	public List<RICTxnRptDto> getRicLostNfoundRptRecordList(
			RICTxnRptDto lostNfoundSrchCriteria)
			throws DaoException {
		DetachedCriteria criteria = DetachedCriteria.forClass(NicIssuanceData.class);
		Format formatter = new SimpleDateFormat("dd-MMM-yyyy");
		List<RICTxnRptDto> records = new ArrayList<RICTxnRptDto>();
		criteria.setFetchMode("nicTransaction", FetchMode.JOIN);
		criteria.createAlias("nicTransaction", "t");
		
		if (lostNfoundSrchCriteria.getTxnStartDate() != null) { 
			criteria.add(Restrictions.ge("t.dateOfApplication",lostNfoundSrchCriteria.getTxnStartDate()));
		}
		if (lostNfoundSrchCriteria.getTxnEndDate() != null) {
			criteria.add(Restrictions.le("t.dateOfApplication", lostNfoundSrchCriteria.getTxnEndDate()));
		} 
		if (lostNfoundSrchCriteria.getTxnType() != null && !"ALL".equals(lostNfoundSrchCriteria.getTxnType())) { 
			criteria.add(Restrictions.eq("transactionType", lostNfoundSrchCriteria.getTxnType()));
		}else{
			Criterion cr1=Restrictions.eq("t.transactionType", "RIC_CARD_LOST");
			Criterion cr2=Restrictions.eq("t.transactionType", "RIC_CARD_FOUND"); 
			criteria.add(Restrictions.or(cr1, cr2));
		}
		if (lostNfoundSrchCriteria.getRicOffice() != null && "ALL".equals(lostNfoundSrchCriteria.getRicOffice())) {
			 criteria.add(Restrictions.like("t.regSiteCode", "RIC", MatchMode.START).ignoreCase()); 
		} else if (lostNfoundSrchCriteria.getRicOffice() != null && !"ALL".equals(lostNfoundSrchCriteria.getRicOffice())) {
			criteria.add(Restrictions.eq("t.regSiteCode", lostNfoundSrchCriteria.getRicOffice()));
		}
		if (lostNfoundSrchCriteria.getNoOfDays() != null && !"ALL".equals(lostNfoundSrchCriteria.getNoOfDays())) {
			criteria.add(Restrictions.sqlRestriction("TRUNC(SYSDATE)-TRUNC(CARD_STATUS_CHANGE_TIME) >'"+lostNfoundSrchCriteria.getNoOfDays()+"'"));
		} 
		criteria.add(Restrictions.isNotNull("cardStatus"));  
		List<NicIssuanceData> list = this.findAll(criteria);
		System.out.println("DAO Result >>" + list.size());
		if (list != null && list.size() > 0) {
			System.out.println("After DAO Query");
			for (NicIssuanceData data : list) {
				RICTxnRptDto record = new RICTxnRptDto();	
				record.setApplicationDate(formatter.format(data.getNicTransaction().getDateOfApplication()));
				record.setTxnType(data.getNicTransaction().getTransactionType());
//				record.setTxnSubType(data.getNicTransaction().getTransactionSubtype());
//				record.setNin(data.getNicTransaction().getNin());
				record.setTxnStatus(data.getNicTransaction().getTransactionStatus());
				record.setCardStatus(data.getCardStatus()); 
				record.setTransactionNo(data.getNicTransaction().getTransactionId()); 
				record.setCardStatusChngTime(data.getCardStatusChangeTime());
				records.add(record);
			}
		}
		return records;
	}
}
