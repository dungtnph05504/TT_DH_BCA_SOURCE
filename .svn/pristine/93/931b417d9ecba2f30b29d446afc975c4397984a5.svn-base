package com.nec.asia.nic.comp.trans.dao.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.nec.asia.nic.comp.trans.dao.DocumentDataDao;
import com.nec.asia.nic.comp.trans.domain.BaseModelList;
import com.nec.asia.nic.comp.trans.domain.BaseModelSingle;
import com.nec.asia.nic.comp.trans.domain.NicDocumentData;
import com.nec.asia.nic.comp.trans.domain.NicDocumentDataId;
import com.nec.asia.nic.comp.trans.domain.NicRegistrationData;
import com.nec.asia.nic.comp.trans.domain.NicTransaction;
import com.nec.asia.nic.comp.trans.dto.RetrieveDocumentDataResponse;
import com.nec.asia.nic.comp.ws.log.domain.CreateMessageException;
import com.nec.asia.nic.framework.PaginatedResult;
import com.nec.asia.nic.framework.dao.DaoException;
import com.nec.asia.nic.framework.dao.impl.GenericHibernateDao;
import com.nec.asia.nic.utils.DateUtil;
import com.nec.asia.nic.utils.ExceptionMessageFormatter;

/**
 * 
 * @author chris_wong
 *
 */
/*
 * Modification History:
 * 
 * 04 Jan 2016 (chris): init dao impl 09 May 2016 (khang): update used of
 * openSession in updatePackageDispatchInfoByPassportNoList 20 May 2016
 * (khang/chris): fix for countExistingPassportByPassportNoList
 */
@Repository("documentDataDao")
public class DocumentDataDaoImpl extends
		GenericHibernateDao<NicDocumentData, NicDocumentDataId> implements
		DocumentDataDao {

	@Override
	public BaseModelList<NicDocumentData> findAllByTransactionId(
			String transactionId) {
		List<NicDocumentData> documentDataDBOList = new ArrayList<NicDocumentData>();

		DetachedCriteria detachedCriteria = DetachedCriteria
				.forClass(getPersistentClass());
		if (StringUtils.isNotEmpty(transactionId)) {
			detachedCriteria.add(Restrictions.eq("id.transactionId",
					transactionId));
			detachedCriteria.addOrder(Order.desc("createDatetime"));
			try {
				List<NicDocumentData> resultList = this
						.findAll(detachedCriteria);
				if (resultList != null && resultList.size() > 0) {
					documentDataDBOList.addAll(resultList);
				}
				return new BaseModelList<NicDocumentData>(documentDataDBOList,
						true, null);
			} catch (Exception e) {
				return new BaseModelList<NicDocumentData>(null, false,
						CreateMessageException.createMessageException(e)
								+ " - findAllByTransactionId - "
								+ transactionId + " - thất bại.");
			}
		}
		// logger.info("[findAllByTransactionId][{}] size:{}", new Object[] {
		// transactionId, documentDataDBOList.size()});
		return new BaseModelList<NicDocumentData>(documentDataDBOList, true,
				null);
	}

	@Override
	public BaseModelSingle<NicDocumentData> findByDocNumber(String passportNo) {
		NicDocumentData documentDataDBO = null;

		DetachedCriteria detachedCriteria = DetachedCriteria
				.forClass(getPersistentClass());
		if (StringUtils.isNotEmpty(passportNo)) {
			detachedCriteria.add(Restrictions.eq("id.passportNo", passportNo));

			try {
				List<NicDocumentData> resultList = this
						.findAll(detachedCriteria);
				if (CollectionUtils.isNotEmpty(resultList)) {
					documentDataDBO = resultList.get(0);
				}
			} catch (Exception e) {
				// TODO: handle exception
				return new BaseModelSingle<NicDocumentData>(null, false,
						CreateMessageException.createMessageException(e)
								+ " - findByDocNumber - " + passportNo
								+ " - thất bại.");
			}
		}
		// logger.info("[findByDocNumber][{}] exists:{}", new Object[]
		// {passportNo, documentDataDBO!=null});
		return new BaseModelSingle<NicDocumentData>(documentDataDBO, true, null);
	}

	@Override
	public List<NicDocumentData> findSyncByDay(Date date) {
		List<NicDocumentData> documentDataDBO = null;

		DetachedCriteria detachedCriteria = DetachedCriteria
				.forClass(getPersistentClass());
		if (date != null) {
			detachedCriteria.add(Restrictions.ge("issueDatetime",
					new Date(date.getTime() - TimeUnit.DAYS.toMillis(1))));
			detachedCriteria.add(Restrictions.eq("status",
					NicDocumentData.DOCUMENT_DATA_STATUS_ISSUANCE));
			List<NicDocumentData> resultList = this.findAll(detachedCriteria);
			if (CollectionUtils.isNotEmpty(resultList)) {
				documentDataDBO = resultList;
			}
		}
		logger.info("[findSyncByDay][{}] exists:{}", new Object[] { date,
				documentDataDBO != null });
		return documentDataDBO;
	}

	@Override
	public List<NicDocumentData> findAllByTransactionId(String transactionId,
			List<String> statusesToInclude) {

		try {
			DetachedCriteria detachedCriteria = DetachedCriteria
					.forClass(getPersistentClass());
			detachedCriteria.add(Restrictions.eq("id.transactionId",
					transactionId));
			detachedCriteria.addOrder(Order.desc("createDatetime"));
			if (CollectionUtils.isNotEmpty(statusesToInclude)) {
				detachedCriteria.add(Restrictions.in("status",
						statusesToInclude));
			}
			return this.findAll(detachedCriteria);
		} catch (Exception e) {
			throw e;
		}
	}

	@Override
	public List<NicDocumentData> findAllByTransactionId_notInStatuses(
			String transactionId, List<String> statusesToExclude) {

		try {
			DetachedCriteria detachedCriteria = DetachedCriteria
					.forClass(getPersistentClass());
			detachedCriteria.add(Restrictions.eq("id.transactionId",
					transactionId));
			if (CollectionUtils.isNotEmpty(statusesToExclude)) {
				detachedCriteria.add(Restrictions.not(Restrictions.in("status",
						statusesToExclude)));
			}
			return this.findAll(detachedCriteria);
		} catch (Exception e) {
			return null;
		}
	}

	@Override
	public List<NicDocumentData> findAllDispatchedDocuments(Integer maxResult)
			throws DaoException {
		Session session = null;

		try {
			session = this.openSession();
			List<NicDocumentData> results = null;
			Criteria criteria = session.createCriteria(NicDocumentData.class);
			criteria.add(Restrictions.isNotNull("dispatchId"));
			criteria.add(Restrictions.or(Restrictions.isNull("syncStatus"),
					Restrictions.eq("syncStatus", ""),
					Restrictions.eq("syncStatus", "N")));
			criteria.setMaxResults(maxResult);
			criteria.addOrder(Order.asc("dispatchDatetime"));
			results = criteria.list();
			return results;
		} catch (Exception e) {
			throw new DaoException(ExceptionMessageFormatter.format(e));
		} finally {
			session.close();
		}
	}

	@Override
	public List<NicDocumentData> findAllSyncErrorDocuments(Integer maxResult)
			throws DaoException {
		Session session = null;

		try {
			session = this.openSession();
			List<NicDocumentData> results = null;
			Criteria criteria = session.createCriteria(NicDocumentData.class);
			criteria.add(Restrictions.isNotNull("dispatchId"));
			criteria.add(Restrictions.eq("syncStatus", "E"));
			criteria.setMaxResults(maxResult);
			criteria.addOrder(Order.asc("lastSyncDatetime"));
			results = criteria.list();
			return results;
		} catch (Exception e) {
			throw new DaoException(ExceptionMessageFormatter.format(e));
		} finally {
			session.close();
		}
	}

	@Override
	public int updatePackageDispatchInfoByPassportNoList(
			List<String> passportNoList, String packageId,
			Date packageDateTime, String dispatchId, Date dispatchDateTime,
			String status, Date statusUpdateTime, String officerId,
			String workstationId) throws DaoException {
		Session session = null;
		int updateCount = 0;
		try {
			session = this.openSession();
			Query query = session.getNamedQuery("updatePackageDispatchInfo");
			query.setString("packageId", packageId);
			query.setDate("packageDateTime", packageDateTime);
			query.setString("dispatchId", dispatchId);
			query.setDate("dispatchDateTime", dispatchDateTime);
			query.setString("status", status);
			query.setDate("statusUpdateTime", statusUpdateTime);
			query.setString("officerId", officerId);
			query.setString("workstationId", workstationId);
			query.setParameterList("passportNoList", passportNoList);
			updateCount = query.executeUpdate();
			logger.info(
					"[updatePackageDispatchInfo] update result returned for packageId: {}, updateCount: {}",
					packageId, updateCount);
		} catch (HibernateException ex) {
			throw new DaoException(ex);
		} finally {
			session.close();
		}
		return updateCount;
	}

	@Override
	public int countExistingPassportByPassportNoList(List<String> passportNoList)
			throws DaoException {
		Session session = null;
		int rowCount = 0;
		try {
			session = this.openSession();
			Query query = session
					.getNamedQuery("countExistingPassportByPassportNoList");
			query.setParameterList("passportNoList", passportNoList);
			rowCount = ((java.math.BigDecimal) query.uniqueResult()).intValue();// query.executeUpdate();
			logger.info("[countExistingPassport] uniqueResult Count: {} ",
					rowCount);
		} catch (HibernateException ex) {
			throw new DaoException(ex);
		} finally {
			session.close();
		}
		return rowCount;
	}

	@Override
	public List<NicDocumentData> findAllByDocNumber(String passportNo) {
		try {
			DetachedCriteria detachedCriteria = DetachedCriteria
					.forClass(getPersistentClass());
			detachedCriteria.add(Restrictions.eq("id.passportNo", passportNo));
			return this.findAll(detachedCriteria);
		} catch (Exception e) {
			throw e;
		}
	}

	@Override
	public PaginatedResult<NicDocumentData> findAllForPagination(
			NicDocumentData nicDocumentData, int pageNo, int pageSize) {
		DetachedCriteria tCriteria = DetachedCriteria
				.forClass(NicDocumentData.class);

		if (nicDocumentData != null && nicDocumentData.getId() != null) {

			if (StringUtils.isNotEmpty(nicDocumentData.getId().getPassportNo())) {
				tCriteria.add(Restrictions.eq("id.passportNo", nicDocumentData
						.getId().getPassportNo()));
			}

			if (StringUtils.isNotEmpty(nicDocumentData.getId()
					.getTransactionId())) {
				tCriteria.add(Restrictions.eq("id.transactionId",
						nicDocumentData.getId().getTransactionId()));
			}
		}

		Order order = Order.asc("updateDatetime");
		return findAllForPagination(tCriteria, pageNo, pageSize, order);
	}

	@Override
	public void cancelPassport(String transactionId, String passportNo)
			throws DaoException {
		Session session = this.openSession();
		Query query = session.getNamedQuery("callCancelPassport")
				.setParameter("passportNo", passportNo)
				.setParameter("transactionId", transactionId);
		query.executeUpdate();
	}

	public List<NicDocumentData> findAllDocumentsForSync(String siteCode)
			throws DaoException {

		try {
			List<NicDocumentData> results = null;
			DetachedCriteria criteria = DetachedCriteria
					.forClass(NicDocumentData.class);
			// criteria.add(Restrictions.eq("issSiteCode", siteCode));
			criteria.add(Restrictions.isNotNull("packageId"));
			criteria.add(Restrictions.isNotNull("dispatchId"));
			criteria.add(Restrictions.or(Restrictions.isNull("syncStatus"),
					Restrictions.eq("syncStatus", ""),
					Restrictions.eq("syncStatus", "N")));
			criteria.addOrder(Order.asc("packageId"));
			results = this.findAll(criteria);
			;
			return results;
		} catch (Exception e) {
			throw new DaoException(ExceptionMessageFormatter.format(e));
		}
	}

	@Override
	public void updateSyncStatus(List<String> passportNoList, String officerId,
			String workstationId) throws DaoException {
		Session session = null;
		int updateCount = 0;
		try {
			// this.getHibernateTemplate().bulkUpdate(queryString, values)
			session = this.openSession();
			Query query = session
					.getNamedQuery("updateSyncStatusByPassportNoList");

			query.setString("officerId", officerId);
			query.setString("workstationId", workstationId);
			// query.setDate("statusUpdateTime", statusUpdateTime);
			query.setParameterList("passportNoList", passportNoList);
			updateCount = query.executeUpdate();
			logger.info(
					"[updatePackageDispatchInfo] update result returned for passportNoList: {}, updateCount: {}",
					passportNoList, updateCount);
		} catch (Exception e) {
			throw new DaoException(ExceptionMessageFormatter.format(e));
		} finally {
			session.close();
		}
	}

	public String getNextPassportNo() {
		String passportNo = null;
		Session session = null;
		try {
			session = getHibernateTemplate().getSessionFactory().openSession();
			Query query = session.getNamedQuery("generateTrialPassportNo");
			String results = (String) query.uniqueResult();
			if (results != null) {
				passportNo = results;
			} else {
				logger.warn(
						"[getNextPassportNo] unexpected result returned result: {}",
						results);
			}
		} finally {
			session.close();
		}
		return passportNo;
	}

	/* [09-Mar] Trung lấy bản ghi Document Data theo transactionId */
	@Override
	public BaseModelSingle<NicDocumentData> getNicDocumentDataById(
			String transactionId) {
		NicDocumentData nicTransaction = null;
		try {
			DetachedCriteria detachedCriteria = DetachedCriteria
					.forClass(getPersistentClass());
			// detachedCriteria.getExecutableCriteria(this.hibernateTemplate.getSessionFactory().openSession());
			detachedCriteria.addOrder(Order.desc("createDatetime"));
			if (StringUtils.isNotEmpty(transactionId)) {
				detachedCriteria.add(Restrictions.eq("id.transactionId",
						transactionId));
				List<NicDocumentData> nicTransactionList = this
						.findAll(detachedCriteria);
				if (CollectionUtils.isNotEmpty(nicTransactionList)) {
					nicTransaction = nicTransactionList.get(0);
				}

			}
			return new BaseModelSingle<NicDocumentData>(nicTransaction, true,
					null);
		} catch (Exception e) {
			return new BaseModelSingle<NicDocumentData>(null, false,
					CreateMessageException.createMessageException(e)
							+ " - getNicDocumentDataById - " + transactionId
							+ " - thất bại.");
		}
	}

	/* [09-Mar] Trung thêm thay đổi số hộ chiếu */
	@Override
	public void updatePassportNo(String transactionId, String userId,
			String wkstnId, String passportNo) {
		logger.info(
				"Inside Dao Impl========>>>>>Update PassportNo transactionId-{}, userId-{}, wkstnId-{}",
				new Object[] { transactionId, userId, wkstnId });

		DetachedCriteria criteria = DetachedCriteria
				.forClass(getPersistentClass());
		criteria.add(Restrictions.eq("id.transactionId", transactionId));
		List<NicDocumentData> list = (List<NicDocumentData>) getHibernateTemplate()
				.findByCriteria(criteria);
		NicDocumentData data = new NicDocumentData();
		try {
			if (list != null) {
				for (NicDocumentData updateObj : list) {
					// NicDocumentDataId id = new NicDocumentDataId();
					// id.setPassportNo(passportNo);
					updateObj.getId().setPassportNo(passportNo);
					updateObj.setUpdateBy(userId);
					updateObj.setUpdateWkstnId(wkstnId);
					updateObj.setUpdateDatetime(new Date());
					saveOrUpdate(updateObj);
				}
			}
		} catch (Exception e) {
			logger.error(
					"[updatePassportNo] transactionId-{}, userId-{}, wkstnId-{}, passportNo-{}",
					new Object[] { transactionId, userId, wkstnId, passportNo },
					e);
		}
	}

	@Override
	public List<NicDocumentData> findAllBySiteCodeAndStage(String site,
			String status, int year, int check) {
		try {
			List<NicDocumentData> results = null;
			DetachedCriteria criteria = DetachedCriteria
					.forClass(NicDocumentData.class);
			// criteria.add(Restrictions.eq("issSiteCode", siteCode));
			if (StringUtils.isNotEmpty(site)) {
				criteria.add(Restrictions
						.like("id.transactionId", (site + "%")));
			}
			if (StringUtils.isNotEmpty(status)) {
				criteria.add(Restrictions.eq("status", status));
			}
			criteria.addOrder(Order.asc("packageId"));
			results = this.findAll(criteria);
			;
			return results;
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
		}
		return null;
	}

	@Override
	public List<RetrieveDocumentDataResponse> getlistDocumentData(
			String siteCode, String passportNo, Date issueDatePassport,
			String typePassport, Date expireDatePassport, String handoverB) {
		Session session = null;
		List<RetrieveDocumentDataResponse> lst = null;
		try {
			session = this.openSession();
			Query query = session.getNamedQuery("listDocumentData");
			query.setString("siteCode", siteCode);
			query.setString("passportNo", passportNo);
			query.setDate("dateIssue", issueDatePassport);
			query.setString("style", typePassport);
			query.setDate("dateExpire", expireDatePassport);
			query.setString("packId", handoverB);

			List<Object[]> list = query.list();
			if (CollectionUtils.isNotEmpty(list)) {
				lst = new ArrayList<RetrieveDocumentDataResponse>();
				for (int i = 0; i < list.size(); i++) {
					RetrieveDocumentDataResponse countP = new RetrieveDocumentDataResponse();
					Object[] obj = (Object[]) list.get(i);
					countP.setPassportNo((String) obj[0]);
					countP.setFullName((String) obj[1]);
					countP.setGender((String) obj[2]);
					countP.setDob((Date) obj[3]);
					countP.setPlaceOfBirth((String) obj[4]);
					countP.setPassportType((String) obj[5]);
					countP.setPid((String) obj[6]);
					countP.setTypeDob((String) obj[7]);
					countP.setTransactionID((String) obj[8]);
					countP.setDateIssua((Date) obj[9]);
					countP.setExpireDate((Date) obj[10]);
					countP.setPrintSite((String) obj[11]);
					countP.setStatus((String) obj[12]);
					countP.setFlagActive((String) obj[13]);
					countP.setInfoPerson((String) obj[14]);
					countP.setIcaoOne((String) obj[15]);
					countP.setIcaoTwo((String) obj[16]);
					lst.add(countP);
				}
				return lst;
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
		} finally {
			session.close();
		}
		return null;
	}

	public RetrieveDocumentDataResponse detailDocumentData(String passportNo) {
		Session session = null;
		try {
			session = this.openSession();
			Query query = session.getNamedQuery("detailDocumentData");
			query.setString("passportNo", passportNo);

			List<Object[]> list = query.list();
			if (CollectionUtils.isNotEmpty(list)) {
				RetrieveDocumentDataResponse countP = new RetrieveDocumentDataResponse();
				for (int i = 0; i < list.size(); i++) {
					Object[] obj = (Object[]) list.get(i);
					countP.setExpireDate((Date) obj[0]);
					countP.setPrintSite((String) obj[1]);
					countP.setStatus((String) obj[2]);
					countP.setFlagActive((String) obj[3]);
					try {
						countP.setInfoPerson(((String) obj[4])
								.replace("\n", ""));
					} catch (Exception e) {
					}
					countP.setIcaoOne((String) obj[5]);
					countP.setIcaoTwo((String) obj[6]);
					try {
						String convertImg = Base64
								.encodeBase64String((byte[]) obj[7]);
						countP.setImage(convertImg.replace("\r\n", ""));
					} catch (Exception e) {

					}

				}
				return countP;
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
		} finally {
			session.close();
		}
		return null;
	}

	@Override
	public BaseModelSingle<Void> saveOrUpdateDocumentData(NicDocumentData data) {
		try {
			this.saveOrUpdate(data);
			return new BaseModelSingle<Void>(null, true, null);
		} catch (Exception e) {
			return new BaseModelSingle<Void>(null, false,
					CreateMessageException.createMessageException(e)
							+ " - saveOrUpdateDocumentData - thất bại");
		}
	}

	@Override
	public BaseModelList<NicDocumentData> findAllPassportByCondition(
			String passportNo, String archiveCode) {
		try {
			List<NicDocumentData> results = null;
			DetachedCriteria criteria = DetachedCriteria
					.forClass(NicDocumentData.class);
			if (StringUtils.isNotBlank(passportNo)) {
				criteria.add(Restrictions.eq("id.passportNo", passportNo));
			}
			results = this.findAll(criteria);
			return new BaseModelList<NicDocumentData>(results, true, null);
		} catch (Exception e) {
			return new BaseModelList<NicDocumentData>(null, false,
					CreateMessageException.createMessageException(e)
							+ " - findAllPassportByCondition - " + passportNo
							+ " - thất bại.");
		}
	}

	@Override
	public BaseModelSingle<NicDocumentData> findPassportByConditions(
			String passportNo, String name, String gender, String dateOfBirth,
			String dateOfDocIssue, String dateOfDocExpiry) {
		try {
			NicDocumentData nicDocData = null;
			DetachedCriteria criteria = DetachedCriteria
					.forClass(NicDocumentData.class);
			if (StringUtils.isNotBlank(passportNo)) {
				criteria.add(Restrictions.eq("id.passportNo", passportNo));
			}
			if (StringUtils.isNotBlank(dateOfDocIssue)) {
				if (dateOfDocIssue.length() == 8) {
					Date date = DateUtil.strToDate(dateOfDocIssue,
							DateUtil.FORMAT_YYYYMMDD);
					criteria.add(Restrictions.eq("dateOfIssue", date));
				}
			}
			if (StringUtils.isNotBlank(dateOfDocExpiry)) {
				if (dateOfDocExpiry.length() == 8) {
					Date date = DateUtil.strToDate(dateOfDocExpiry,
							DateUtil.FORMAT_YYYYMMDD);
					criteria.add(Restrictions.eq("dateOfExpiry", date));
				}
			}
			List<NicDocumentData> list = this.findAll(criteria);
			if (list != null && list.size() > 0) {
				for (NicDocumentData nicDocumentData : list) {
					NicTransaction nicTran = nicDocumentData
							.getNicTransaction();
					boolean check = true;
					if (nicTran != null) {

						NicRegistrationData nicRegisData = nicTran
								.getNicRegistrationData();
						if (nicRegisData != null) {
							if (StringUtils.isNotBlank(gender)) {
								if (!nicRegisData.getGender().equals(gender)) {
									check = false;
								}
							}
							if (StringUtils.isNotBlank(dateOfBirth)) {
								Date date = DateUtil.strToDate(dateOfBirth,
										DateUtil.FORMAT_YYYYMMDD);
								if (nicRegisData.getDateOfBirth().getTime() != date
										.getTime()) {
									check = false;
								}
							}
							if (!(nicRegisData.getSearchName())
									.contains(com.nec.asia.nic.utils.HelperClass
											.removeAccent(name).toUpperCase())) {
								check = false;
							}
						}
						if (check) {
							nicDocData = nicDocumentData;
							break;
						}
					}
				}
			}
			return new BaseModelSingle<NicDocumentData>(nicDocData, true, null);
		} catch (Exception e) {
			return new BaseModelSingle<NicDocumentData>(null, false,
					CreateMessageException.createMessageException(e)
							+ " - findPassportByConditions - thất bại.");
		}
	}

	@Override
	public List<NicDocumentData> findPassportToRestore(String passportNo,
			String reason, String name, String gender, String dateOfBirth) {
		List<NicDocumentData> listDocData = null;
		try {
			DetachedCriteria criteria = DetachedCriteria
					.forClass(getPersistentClass());
			if (StringUtils.isNotBlank(passportNo)) {
				criteria.add(Restrictions.eq("id.passportNo", passportNo));
			}
			if (StringUtils.isNotBlank(reason)) {
				criteria.add(Restrictions.eq("cancelReason", reason));
			}
			List<NicDocumentData> list = this.findAll(criteria);
			if (list != null && list.size() > 0) {
				listDocData = new ArrayList<NicDocumentData>();
				for (NicDocumentData nicDocumentData : list) {
					NicTransaction nicTran = nicDocumentData
							.getNicTransaction();
					boolean check = true;
					if (nicTran != null) {
						NicRegistrationData nicRegisData = nicTran
								.getNicRegistrationData();

						if (nicRegisData != null) {
							if (StringUtils.isNotBlank(gender)) {
								if (!nicRegisData.getGender().equals(gender)) {
									check = false;
								}
							}
							if (StringUtils.isNotBlank(dateOfBirth)) {
								Date date = DateUtil.strToDate(dateOfBirth,
										DateUtil.FORMAT_YYYYMMDD);
								if (nicRegisData.getDateOfBirth().getTime() != date
										.getTime()) {
									check = false;
								}
							}

							if (!(nicRegisData.getSearchName())
									.contains(com.nec.asia.nic.utils.HelperClass
											.removeAccent(name).toUpperCase())) {
								check = false;
							}
						}
						if (check) {
							listDocData.add(nicDocumentData);
						}
					}
				}
			}
		} catch (Exception e) {
			throw e;
		}
		return listDocData;
	}

	@Override
	public List<NicDocumentData> findValidPassportById(String passportNo) {
		List<NicDocumentData> list = null;
		try {
			DetachedCriteria criteria = DetachedCriteria
					.forClass(getPersistentClass());
			if (StringUtils.isNotBlank(passportNo)) {
				criteria.add(Restrictions.eq("id.passportNo", passportNo));
				criteria.add(Restrictions.in("status", new String[] {
						NicDocumentData.DOCUMENT_DATA_STATUS_ISSUANCE,
						NicDocumentData.DOCUMENT_DATA_STATUS_PERSONALIZED,
						NicDocumentData.DOCUMENT_DATA_STATUS_PACKED,
						NicDocumentData.DOCUMENT_DATA_STATUS_INIT }));
				criteria.add(Restrictions.ge("dateOfExpiry", new Date()));
				list = this.findAll(criteria);
			}
		} catch (Exception e) {
			throw e;
		}
		return list;
	}

	@Override
	public List<NicDocumentData> findValidDocByListTranId(String[] listTranId) {
		List<NicDocumentData> list = null;
		try {
			DetachedCriteria criteria = DetachedCriteria
					.forClass(getPersistentClass());
			if (listTranId.length > 0) {
				criteria.add(Restrictions.in("id.transactionId", listTranId));
				criteria.add(Restrictions.and(
						Restrictions.not(Restrictions.eq("status",
								NicDocumentData.DOCUMENT_DATA_STATUS_CANCELLED)),
						Restrictions.not(Restrictions
								.eq("cancelType",
										NicDocumentData.DOCUMENT_DATA_CANCELLED_TYPE_PRINT_FAIL))));
				criteria.addOrder(Order.desc("createDatetime"));
				list = this.findAll(criteria);
			}
		} catch (Exception e) {
			throw e;
		}
		return list;
	}

	@Override
	public List<NicDocumentData> findDocDataToDel(
			NicDocumentDataId nicDocumentDataId, String phoiSerialNo) {
		List<NicDocumentData> list = null;
		try {
			DetachedCriteria criteria = DetachedCriteria
					.forClass(getPersistentClass());
			criteria.add(Restrictions.eq("id", nicDocumentDataId));
			criteria.add(Restrictions.eq("serialNo", phoiSerialNo));
			list = this.findAll(criteria);
		} catch (Exception e) {
			throw e;
		}
		return list;
	}

	@Override
	public void deletePassport(NicDocumentData nicDocData) {
		try {
			this.delete(nicDocData);
		} catch (Exception e) {
			throw e;
		}
		
	}

	@Override
	public List<NicDocumentData> findDocByListPassportNo(String[] listPassportNo) {
		List<NicDocumentData> list = null;
		try {
			DetachedCriteria criteria = DetachedCriteria
					.forClass(getPersistentClass());
			criteria.add(Restrictions.in("id.passportNo", listPassportNo));
			list = this.findAll(criteria);
		} catch (Exception e) {
			throw e;
		}
		return list;
	}

	@Override
	public List<NicDocumentData> findAllDocByListTranId(String[] listTranId) {
		List<NicDocumentData> list = null;
		try {
			DetachedCriteria criteria = DetachedCriteria
					.forClass(getPersistentClass());
			if (listTranId.length > 0) {
				criteria.add(Restrictions.in("id.transactionId", listTranId));
				criteria.addOrder(Order.desc("createDatetime"));
				list = this.findAll(criteria);
			}
		} catch (Exception e) {
			throw e;
		}
		return list;
	}
}
