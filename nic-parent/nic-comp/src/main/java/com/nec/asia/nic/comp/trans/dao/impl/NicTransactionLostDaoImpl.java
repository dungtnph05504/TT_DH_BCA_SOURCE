package com.nec.asia.nic.comp.trans.dao.impl;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.nec.asia.nic.comp.trans.dao.NicTransactionLostDao;
import com.nec.asia.nic.comp.trans.domain.BaseModelSingle;
import com.nec.asia.nic.comp.trans.domain.NicTransactionLost;
import com.nec.asia.nic.comp.ws.log.domain.CreateMessageException;
import com.nec.asia.nic.framework.dao.impl.GenericHibernateDao;
import com.nec.asia.nic.utils.DateUtil;
import com.nec.asia.nic.utils.HelperClass;

@Repository
public class NicTransactionLostDaoImpl extends
		GenericHibernateDao<NicTransactionLost, Long> implements
		NicTransactionLostDao {

	@Override
	public List<NicTransactionLost> findTransactionLost(Date date) {
		// Session session = null;
		// List<NicTransactionLost> result = null;
		// try {
		// session = this.openSession();
		// Query query = session.getNamedQuery("getLostDocumentData");
		// query.setDate("dateC", date);
		//
		// List<Object[]> list = query.list();
		// if (CollectionUtils.isNotEmpty(list)) {
		// result = new ArrayList<NicTransactionLost>();
		// NicTransactionLost countP = new NicTransactionLost();
		// for (int i = 0; i < list.size(); i++) {
		// Object[] obj = (Object[]) list.get(i);
		// countP.setTransactionId((String) obj[0]);
		// countP.setNin((String) obj[1]);
		// countP.setName((String) obj[2]);
		// countP.setDob((Date) obj[3]);
		// countP.setPassportNo((String) obj[4]);
		// countP.setDateIssue((Date) obj[5]);
		// countP.setDateExrity((Date) obj[6]);
		// countP.setPlaceIssue((String) obj[7]);
		// countP.setCreateBy((String) obj[8]);
		// countP.setCreateDate((Date) obj[9]);
		// countP.setReason((String) obj[10]);
		// countP.setReasonNote((String) obj[11]);
		// countP.setProcessStatus((String) obj[12]);
		// result.add(countP);
		// }
		// return result;
		// }
		// } catch (Exception e) {
		// e.printStackTrace();
		// System.out.println(e.getMessage());
		// }
		// finally {
		// session.close();
		// }
		return null;
	}

	@Override
	public BaseModelSingle<NicTransactionLost> findTranLostByConditions(
			String transactionId, String passportNo, String personCode) {
		try {
			NicTransactionLost nicTransactionLost = null;
			List<NicTransactionLost> list = null;
			DetachedCriteria criteria = DetachedCriteria
					.forClass(getPersistentClass());
			criteria.addOrder(Order.desc("id"));
			if (StringUtils.isNotBlank(transactionId)
					|| StringUtils.isNotBlank(passportNo)) {
				if (StringUtils.isNotBlank(transactionId)) {
					criteria.add(Restrictions.eq("transactionId", transactionId));
				}
				if (StringUtils.isNotBlank(passportNo)) {
					criteria.add(Restrictions.eq("passportNo", passportNo));
				}
				
				if (StringUtils.isNotBlank(personCode)) {
					criteria.add(Restrictions.eq("personCode", personCode));
				}
				list = this.findAll(criteria);
				if (list != null && list.size() > 0)
					nicTransactionLost = list.get(0);
			}
			return new BaseModelSingle<NicTransactionLost>(nicTransactionLost,
					true, null);
		} catch (Exception e) {
			return new BaseModelSingle<NicTransactionLost>(null, false,
					CreateMessageException.createMessageException(e)
							+ " - findTranLostByConditions - thất bại.");
		}
	}

	@Override
	public BaseModelSingle<Void> saveOrUpdateLost(NicTransactionLost lost)
			throws Exception {
		try {
			this.saveOrUpdate(lost);
			return new BaseModelSingle<Void>(null, true, null);
		} catch (Exception e) {
			return new BaseModelSingle<Void>(null, false,
					CreateMessageException.createMessageException(e)
							+ " - saveOrUpdateLost - thất bại.");
		}
	}

	@Override
	public List<NicTransactionLost> findPassportCancelled(String passportNo,
			String reason, String name, String gender, String dateOfBirth) {
		List<NicTransactionLost> listTranLost = null;
		try {
			DetachedCriteria criteria = DetachedCriteria
					.forClass(getPersistentClass());
			criteria.add(Restrictions.isNull("rstApprovalDate"));
			criteria.add(Restrictions.eq("passportNo", passportNo));
			if (StringUtils.isNotBlank(reason)) {
				criteria.add(Restrictions.eq("reason", reason));
			}
			if (StringUtils.isNotBlank(name)) {
				criteria.add(Restrictions.eq("searchName", HelperClass.removeAccent(name.trim()).toUpperCase()));
			}
			if (StringUtils.isNotBlank(gender)) {
				criteria.add(Restrictions.eq("gender", gender));
			}
			if (StringUtils.isNotBlank(dateOfBirth)) {
				criteria.add(Restrictions.eq("dateOfBirth", dateOfBirth));
			}
			listTranLost = this.findAll(criteria);
		} catch (Exception e) {
			throw e;
		}
		return listTranLost;
	}

	@Override
	public List<NicTransactionLost> findDocCancelToPrint(String createdBy, Boolean printType,
			String diplomaCode, Date dateOfBirth, Date fromDate, Date toDate,
			String gender, String passportNo, String name) throws Exception {
		List<NicTransactionLost> list = null;
		try {
			DetachedCriteria criteria = DetachedCriteria
					.forClass(getPersistentClass());
			criteria.addOrder(Order.asc("id"));
			if (StringUtils.isNotBlank(createdBy)) {
				criteria.add(Restrictions.eq("createdBy", createdBy));
			}
			criteria.add(Restrictions.eq("printer", printType));
			criteria.addOrder(Order.desc("createdDate"));
			if (StringUtils.isNotBlank(diplomaCode)) {
				criteria.add(Restrictions.eq("sendDiplomaCode", diplomaCode));
			}
			if (StringUtils.isNotBlank(gender)) {
				criteria.add(Restrictions.eq("gender", gender));
			}
			if (StringUtils.isNotBlank(name)) {
				criteria.add(Restrictions.eq("searchName", HelperClass.removeAccent(name.trim()).toUpperCase()));
			}
			if (StringUtils.isNotBlank(passportNo)) {
				criteria.add(Restrictions.eq("passportNo", passportNo));
			}
			if (dateOfBirth != null) {
				String date = DateUtil.parseDate(dateOfBirth, DateUtil.FORMAT_YYYYMMDD);
				criteria.add(Restrictions.eq("dateOfBirth", date));
			}
			if (fromDate != null && toDate != null) {
				criteria.add(Restrictions.between("createdDate", fromDate, toDate));
			}else if (fromDate != null) {
				criteria.add(Restrictions.ge("createdDate", fromDate));
			}else if (toDate != null) {
				criteria.add(Restrictions.le("createdDate", toDate));
			}
			list = this.findAll(criteria);
		} catch (Exception e) {
			throw e;
		}
		return list;
	}

	@Override
	public List<NicTransactionLost> findDocRestoreToPrint(String diplomaCode,
			String dateOfBirth, Date fromDate, Date toDate, String gender,
			String passportNo, String name) throws Exception {
		List<NicTransactionLost> list = null;
		try {
			if (StringUtils.isBlank(diplomaCode)
					&& StringUtils.isBlank(dateOfBirth)
					&& fromDate == null
					&& toDate == null
					&& StringUtils.isBlank(gender)
					&& StringUtils.isBlank(passportNo)
					&& StringUtils.isBlank(name)) {
				return list;
			}
			DetachedCriteria criteria = DetachedCriteria
					.forClass(getPersistentClass());
			criteria.addOrder(Order.desc("rstApprovalDate"));
			if (StringUtils.isNotBlank(diplomaCode)) {
				criteria.add(Restrictions.eq("rstDiplomaCode", diplomaCode));
			}
			if (StringUtils.isNotBlank(gender)) {
				criteria.add(Restrictions.eq("gender", gender));
			}
			if (StringUtils.isNotBlank(name)) {
				criteria.add(Restrictions.eq("searchName", HelperClass.removeAccent(name.trim()).toUpperCase()));
			}
			if (StringUtils.isNotBlank(passportNo)) {
				criteria.add(Restrictions.eq("passportNo", passportNo));
			}
			if (StringUtils.isNotBlank(dateOfBirth)) {
				criteria.add(Restrictions.eq("dateOfBirth", dateOfBirth));
			}
			if (fromDate != null && toDate != null) {
				criteria.add(Restrictions.between("rstApprovalDate", fromDate, toDate));
			}else if (fromDate != null) {
				criteria.add(Restrictions.ge("rstApprovalDate", fromDate));
			}else if (toDate != null) {
				criteria.add(Restrictions.le("rstApprovalDate", toDate));
			}
			list = this.findAll(criteria);
		} catch (Exception e) {
			throw e;
		}
		return list;
	}

	@Override
	public List<NicTransactionLost> findDocCanceled(String diplomaCode,
			String dateOfBirth, Date fromDate, Date toDate, String gender,
			String passportNo, String name, String reason,
			String nationalityCode) throws Exception {
		List<NicTransactionLost> list = null;
		try {
			if (StringUtils.isBlank(diplomaCode)
					&& StringUtils.isBlank(dateOfBirth)
					&& fromDate == null
					&& toDate == null
					&& StringUtils.isBlank(gender)
					&& StringUtils.isBlank(passportNo)
					&& StringUtils.isBlank(name)
					&& StringUtils.isBlank(reason)
					&& StringUtils.isBlank(nationalityCode)) {
				return list;
			}
			DetachedCriteria criteria = DetachedCriteria
					.forClass(getPersistentClass());
			criteria.addOrder(Order.desc("createdDate"));
			if (StringUtils.isNotBlank(reason)) {
				criteria.add(Restrictions.eq("reason", reason));
			}
			if (StringUtils.isNotBlank(nationalityCode)) {
				criteria.add(Restrictions.eq("nationalityCode", nationalityCode));
			}
			if (StringUtils.isNotBlank(diplomaCode)) {
				criteria.add(Restrictions.eq("diplomaCode", diplomaCode));
			}
			if (StringUtils.isNotBlank(gender)) {
				criteria.add(Restrictions.eq("gender", gender));
			}
			if (StringUtils.isNotBlank(name)) {
				criteria.add(Restrictions.eq("searchName", HelperClass.removeAccent(name.trim()).toUpperCase()));
			}
			if (StringUtils.isNotBlank(passportNo)) {
				criteria.add(Restrictions.eq("passportNo", passportNo));
			}
			if (StringUtils.isNotBlank(dateOfBirth)) {
				criteria.add(Restrictions.eq("dateOfBirth", dateOfBirth));
			}
			if (fromDate != null) {
				criteria.add(Restrictions.ge("createdDate", fromDate));
			} 
			if (toDate != null) {
				criteria.add(Restrictions.le("createdDate", toDate));
			}
			list = this.findAll(criteria);
		} catch (Exception e) {
			throw e;
		}
		return list;
	}

	@Override
	public Long saveTranLost(NicTransactionLost nicTranLost) throws Exception {
		try {
			return this.save(nicTranLost);
		} catch (Exception e) {
			throw e;
		}
	}

	@Override
	public NicTransactionLost findDocCanByPassportNo(String passportNo)
			throws Exception {
		try {
			NicTransactionLost nicTransactionLost = null;
			List<NicTransactionLost> list = null;
			DetachedCriteria criteria = DetachedCriteria
					.forClass(getPersistentClass());
			criteria.addOrder(Order.desc("id"));
			if (StringUtils.isNotBlank(passportNo)) {
				criteria.add(Restrictions.eq("passportNo", passportNo));
				list = this.findAll(criteria);
				if (list != null && list.size() > 0)
					nicTransactionLost = list.get(0);
			}
			return nicTransactionLost;
		} catch (Exception e) {
			throw e;
		}
	}
	
	@Override
	public List<NicTransactionLost> findTranLost(Date fromDate, Date toDate, String reason, String officeCode)
			throws Exception {
		try {
			List<NicTransactionLost> list = null;
			DetachedCriteria criteria = DetachedCriteria.forClass(getPersistentClass());
			if (StringUtils.isNotBlank(reason)) {
				criteria.add(Restrictions.eq("reason", reason));
			}
			if (StringUtils.isNotBlank(officeCode)) {
				criteria.add(Restrictions.eq("officeCode", officeCode));
			}
			if (fromDate != null) {
				criteria.add(Restrictions.ge("createdDate", fromDate));
			} 
			if (toDate != null) {
				criteria.add(Restrictions.le("createdDate", toDate));
			}
			list = this.findAll(criteria);
			return list;
		} catch (Exception e) {
			throw e;
		}
	}
}
