package com.nec.asia.nic.comp.listHandover.dao.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.exolab.castor.types.DateTime;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.lowagie.text.pdf.AsianFontMapper;
import com.nec.asia.nic.comp.listHandover.dao.ListHandoverDao;
import com.nec.asia.nic.comp.listHandover.domain.NicListHandover;
import com.nec.asia.nic.comp.listHandover.domain.NicListHandoverId;
import com.nec.asia.nic.comp.trans.domain.BaseModelList;
import com.nec.asia.nic.comp.trans.domain.BaseModelSingle;
import com.nec.asia.nic.comp.trans.domain.EppListHandoverDetail;
import com.nec.asia.nic.comp.trans.domain.NicTransactionPackage;
import com.nec.asia.nic.comp.trans.dto.AssignmentFilterAll;
import com.nec.asia.nic.comp.trans.dto.CountPassport;
import com.nec.asia.nic.comp.ws.log.domain.CreateMessageException;
import com.nec.asia.nic.framework.PaginatedResult;
import com.nec.asia.nic.framework.admin.code.dao.PaymentDefDao;
import com.nec.asia.nic.framework.admin.code.domain.Parameters;
import com.nec.asia.nic.framework.admin.code.domain.ParametersId;
import com.nec.asia.nic.framework.admin.code.domain.PaymentDef;
import com.nec.asia.nic.framework.admin.code.domain.PaymentDefId;
import com.nec.asia.nic.framework.dao.impl.GenericHibernateDao;
import com.nec.asia.nic.utils.Constants;

/*
 * Modification History:
 * 27 Jun 2017 (chris): add delete method for physical delete
 */
@Repository("listHandoverDao")
public class ListHandoverDaoImpl extends
		GenericHibernateDao<NicListHandover, String> implements ListHandoverDao {

	/*
	 * @SuppressWarnings("unchecked") public List<PaymentDef>
	 * findPaymentDefByTransType(String transactionType, String
	 * transactionSubtype) { DetachedCriteria criteria =
	 * DetachedCriteria.forClass(PaymentDef.class);
	 * criteria.add(Restrictions.eq("id.transactionType", transactionType));
	 * criteria.add(Restrictions.eq("id.transactionSubtype",
	 * transactionSubtype));
	 * 
	 * return (List<PaymentDef>)
	 * getHibernateTemplate().findByCriteria(criteria); }
	 */

	@Override
	public PaginatedResult<NicListHandover> findAllListHandover(int PageNo,
			int PageSize, AssignmentFilterAll assignmentFilter)
			throws Exception {

		DetachedCriteria criteria = DetachedCriteria
				.forClass(getPersistentClass());

		if (assignmentFilter != null) {
			// if (StringUtils.isNotBlank(assignmentFilter.getTypeList())) {
			// criteria.add(Restrictions.eq("typeList",
			// Integer.parseInt(assignmentFilter.getTypeList())));
			// }

			if (StringUtils.isNotBlank(assignmentFilter.getPackageCode())) {
				criteria.add(Restrictions.eq("id.packageId",
						assignmentFilter.getPackageCode()));
			}
			if (StringUtils.isNotBlank(assignmentFilter.getCreateBy())) {
				criteria.add(Restrictions.eq("createBy",
						assignmentFilter.getCreateBy()));
			}
			if (assignmentFilter.getCreateDate() != null) {
				criteria.add(Restrictions.ge("createDate",
						assignmentFilter.getCreateDate()));

				Date maxDate = new Date(assignmentFilter.getCreateDate()
						.getTime() + TimeUnit.DAYS.toMillis(1));
				criteria.add(Restrictions.lt("createDate", maxDate));
			}
		}

		Order orders = Order.desc("createDate");
		// getHibernateTemplate().findByCriteria(criteria);

		return this.findAllForPagination(criteria, PageNo, PageSize, orders);
	}

	@Override
	public BaseModelList<NicListHandover> findListHandoverByPackageId(
			NicListHandoverId id, AssignmentFilterAll assignmentFilter)
			throws Exception {
		DetachedCriteria criteria = DetachedCriteria
				.forClass(getPersistentClass());
		criteria.add(Restrictions.eq("id", id));
		if (assignmentFilter != null) {
			if (assignmentFilter.getCreateDate() != null) {
				criteria.add(Restrictions.ge("createDate",
						assignmentFilter.getCreateDate()));

				Date maxDate = new Date(assignmentFilter.getCreateDate()
						.getTime() + TimeUnit.DAYS.toMillis(1));
				criteria.add(Restrictions.lt("createDate", maxDate));
			}
		}
		try {
			return new BaseModelList<NicListHandover>(this.findAll(criteria),
					true, null);
		} catch (Exception e) {
			return new BaseModelList<NicListHandover>(null, false,
					CreateMessageException.createMessageException(e)
							+ " - findListHandoverByPackageId: "
							+ id.getPackageId() + " - thất bại.");
		}
	}

	public boolean createHandoverList(NicListHandover listHandover)
			throws Exception {
		boolean status = false;
		Date date = new Date();
		try {
			// NicListHandover entity = findById(listHandover.getId());
			super.saveOrUpdate(listHandover);
			status = true;

		} catch (Exception exp) {
		} finally {
			logger.debug("[createHandoverList]status : {} ", status);
		}
		return status;
	}

	@Override
	public List<NicListHandover> findListHandoverByPackageIdOrType(
			String packageId, int type, AssignmentFilterAll assignmentFilter)
			throws Exception {
		DetachedCriteria criteria = DetachedCriteria
				.forClass(getPersistentClass());
		criteria.add(Restrictions.eq("packageId", packageId));
		// if (StringUtils.isBlank(type)) {
		// criteria.add(Restrictions.eq("typeList", type));
		// }
		if (assignmentFilter != null) {
			if (assignmentFilter.getCreateDate() != null) {
				criteria.add(Restrictions.ge("createDate",
						assignmentFilter.getCreateDate()));

				Date maxDate = new Date(assignmentFilter.getCreateDate()
						.getTime() + TimeUnit.DAYS.toMillis(1));
				criteria.add(Restrictions.lt("createDate", maxDate));
			}
		}
		try {
			return this.findAll(criteria);
		} catch (Exception e) {
		}
		return new ArrayList<NicListHandover>();
	}

	@Override
	public List<NicListHandover> findListHandoverByCriteria(String packageId,
			String type, Integer status) throws Exception {
		DetachedCriteria criteria = DetachedCriteria
				.forClass(getPersistentClass());
		if (StringUtils.isNotEmpty(packageId))
			criteria.add(Restrictions.eq("id.packageId", packageId));
		if (type != null)
			criteria.add(Restrictions.eq("id.typeList", type));
		if (status != null)
			criteria.add(Restrictions.eq("status", status));
		try {
			return this.findAll(criteria);
		} catch (Exception e) {
		}
		return new ArrayList<NicListHandover>();
	}

	@Override
	public BaseModelSingle<NicListHandover> findHandoverByCriteria(
			String packageId, String type, Integer status) throws Exception {
		DetachedCriteria criteria = DetachedCriteria
				.forClass(getPersistentClass());
		if (StringUtils.isNotEmpty(packageId))
			criteria.add(Restrictions.eq("packageId", packageId));
		// if(type != null)
		// criteria.add(Restrictions.eq("typeList", type));
		if (status != null)
			criteria.add(Restrictions.eq("status", status));

		try {
			NicListHandover nicListHandover = this.findAll(criteria).get(0);
			return new BaseModelSingle<NicListHandover>(nicListHandover, true,
					null);
		} catch (Exception e) {
			return new BaseModelSingle<NicListHandover>(null, false,
					CreateMessageException.createMessageException(e)
							+ " - findHandoverByCriteria - " + packageId
							+ " - thất bại.");
		}

	}

	@Override
	public NicListHandover findHandoverByCriteriaSync(String packageId,
			String type, Integer status) throws Exception {
		DetachedCriteria criteria = DetachedCriteria
				.forClass(getPersistentClass());
		if (StringUtils.isNotEmpty(packageId))
			criteria.add(Restrictions.eq("id.packageId", packageId));
		if (type != null)
			criteria.add(Restrictions.eq("id.typeList", type));
		if (status != null)
			criteria.add(Restrictions.eq("status", status));

		criteria.add(Restrictions.or(Restrictions.eq("isSyncPerso", false),
				Restrictions.isNull("isSyncPerso")));

		try {
			return this.findAll(criteria).get(0);
		} catch (Exception e) {
		}

		return null;
	}

	@Override
	public PaginatedResult<NicListHandover> findListHandoverNoAssign(
			String[] arraySite, String userAssign, String siteCode,
			int typeList, int pageNo, int pageSize, Date createDate,
			Date endDate) {
		DetachedCriteria criteria = DetachedCriteria
				.forClass(getPersistentClass());
		// criteria.createAlias("transactions", "nicTransaction");
		// criteria.createAlias("nicTransaction.nicWorkflowJobs", "jobs");
		// criteria.add(Restrictions.isNotNull("jobs.investigationStatus"));
		if (StringUtils.isEmpty(userAssign)) {
			criteria.add(Restrictions.isNull("usersProcess"));
		} else {
			if (userAssign.equals("0")) {
				criteria.add(Restrictions.isNull("usersProcess"));
			} else {
				criteria.add(Restrictions.isNotNull("usersProcess"));
			}
		}
		if (!StringUtils.isEmpty(siteCode)) {
			criteria.add(Restrictions.eq("siteCode", siteCode));
		} else {
			if (arraySite != null && arraySite.length > 0) {
				criteria.add(Restrictions.in("siteCode", arraySite));
			}
		}

		if (createDate != null) {
			criteria.add(Restrictions.ge("createDate", createDate));
		}
		if (endDate != null) {
			criteria.add(Restrictions.le("createDate", endDate));
		}
		// criteria.add(Restrictions.eq("typeList", typeList));
		try {
			return this.findAllForPagination(criteria, pageNo, pageSize);
		} catch (Exception e) {
		}
		return new PaginatedResult<>(0, 1, new ArrayList<NicListHandover>());
	}

	@Override
	@Transactional(readOnly = false)
	public void listHandoverUpdateAssignJob(String packageId, int typeList,
			String siteCode, String[] listAssign, String userId) {
		String dsAssign = "";
		for (int i = 0; i < listAssign.length; i++) {
			dsAssign += listAssign[i] + ",";
		}
		try {
			List<NicListHandover> handover = findListHandoverByOrther(
					packageId, typeList);
			if (handover != null && handover.size() > 0) {
				if (StringUtils.isEmpty(handover.get(0).getProcessUsers())) {
					handover.get(0).setProcessUsers(dsAssign);
				} else {
					handover.get(0).setProcessUsers(
							handover.get(0).getProcessUsers() + dsAssign);
				}
				handover.get(0).setUpdateDate(new Date());
				handover.get(0).setUpdateBy(userId);
				super.saveOrUpdate(handover.get(0));
			}
		} catch (Exception e) {
			// TODO: handle exception
		}

	}

	@Override
	public List<NicListHandover> findListHandoverByOrther(String packageId,
			int typeList) {
		try {
			DetachedCriteria criteria = DetachedCriteria
					.forClass(getPersistentClass());
			Criterion check1 = Restrictions.eq("packageId", packageId);
			// Criterion check2 = Restrictions.eq("typeList", typeList);
			criteria.add(Restrictions.and(check1));
			return this.findAll(criteria);
		} catch (Exception e) {
			// TODO: handle exception
		}
		return null;
	}
	@Override
	public List<NicListHandover> findListHandoverByOrther1(String packageId,
			String typeList) {
	    try {
	    	DetachedCriteria criteria = DetachedCriteria.forClass(getPersistentClass());
	    	Criterion check1 = Restrictions.eq("id.packageId", packageId);
	    	Criterion check2 = Restrictions.eq("id.typeList", typeList);
	    	criteria.addOrder(Order.desc("createDate"));
	    	criteria.add(Restrictions.and(check1, check2));
	    	return this.findAll(criteria);
		} catch (Exception e) {
			// TODO: handle exception
		}
	    return null;
	}
	@Override
	public String getNUpdateCodeValueFromCodeId(String codeId) {
		ParametersId paramsId = new ParametersId();
		paramsId.setParaScope(Constants.PARA_SCOPE_SYSTEM);
		paramsId.setParaName(codeId);
		Parameters param = getHibernateTemplate().load(Parameters.class,
				paramsId);
		System.out
				.println(" SEQUENCE OBTAINED >> " + param.getParaShortValue());
		String value = param.getParaShortValue();
		try {
			int i = Integer.parseInt(value);
			value = String.valueOf(i);
			// priya start changes 3/1/2014
			if (codeId.equalsIgnoreCase(Constants.PARA_LIST_HANDOVER)) {
				// priya end changes 3/1/2014
				if (value.length() == 1) {
					value = String.format("%s" + value, "000000");
				} else if (value.length() == 2) {
					value = String.format("%s" + value, "00000");
				} else if (value.length() == 3) {
					value = String.format("%s" + value, "0000");
				} else if (value.length() == 4) {
					value = String.format("%s" + value, "000");
				} else if (value.length() == 5) {
					value = String.format("%s" + value, "00");
				} else if (value.length() == 6) {
					value = String.format("%s" + value, "0");
				}
			}
			// APARNA CHANGES START 06/02/2014
			if (codeId.equalsIgnoreCase(Constants.PARA_PACKAGE_NEXT)) {
				/*
				 * if (value.length() == 1) { value = String.format("%s" +
				 * value, "0"); }
				 * 
				 * //reset count in case it reaches 100 if (value.length() == 3)
				 * { value = "01"; i = 0; }
				 */

				// GITA CHANGES START 17/04/2014
				if (value.length() == 1) {
					value = String.format("%s" + value, "000");
				} else if (value.length() == 2) {
					value = String.format("%s" + value, "00");
				}

				else if (value.length() == 3) {
					value = String.format("%s" + value, "0");
				}

				// reset count in case it reaches 10000
				else if (value.length() == 5) {
					value = "0001";
					i = 0;
				}
				// GITA CHANGES END 17/04/2014

			}
			// APARNA CHANGES END 06/02/2014
			i++;
			param.setParaShortValue(String.valueOf(i));
			getHibernateTemplate().update(param);
			System.out.println("new value SEQ >> " + i);
			return value;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public List<NicListHandover> findListHandoverListB(int typeList,
			String leaderId, String asignId,
			AssignmentFilterAll assignmentFilter) {
		try {
			DetachedCriteria criteria = DetachedCriteria
					.forClass(getPersistentClass());
			// criteria.add(Restrictions.eq("typeList", typeList));
			if (StringUtils.isNotEmpty(leaderId)) {
				criteria.createAlias("transactions", "transaction");
				criteria.add(Restrictions.eq("transaction.leaderOfficerId",
						leaderId));
			}
			if (StringUtils.isNotEmpty(asignId)) {
				criteria.add(Restrictions.eq("userLeaderProcess", asignId));
			}
			criteria.add(Restrictions.eq("handoverStage", true));
			if (assignmentFilter != null) {

				if (StringUtils.isNotBlank(assignmentFilter.getPackageCode())) {
					criteria.add(Restrictions.eq("packageId",
							assignmentFilter.getPackageCode()));
				}
				if (assignmentFilter.getCreateDate() != null
						&& assignmentFilter.getEndDate() != null) {
					criteria.add(Restrictions.ge("createDate",
							assignmentFilter.getCreateDate()));
					criteria.add(Restrictions.le("createDate",
							assignmentFilter.getEndDate()));
				}
				if (StringUtils.isNotBlank(assignmentFilter
						.getTypeInvestigation())) {
					criteria.add(Restrictions.eq("status", Integer
							.parseInt(assignmentFilter.getTypeInvestigation())));
				}
			}
			return this.findAll(criteria);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
		}
		return new ArrayList<NicListHandover>();
	}

	@Override
	public NicListHandover findHandoverByTransactionId(String transactionId) {
		try {
			DetachedCriteria dCriteria = DetachedCriteria
					.forClass(getPersistentClass());
			dCriteria.createAlias("transactions", "transactions");
			dCriteria.add(Restrictions.eq("transactions.transactionId",
					transactionId));
			List<NicListHandover> list = this.findAll(dCriteria);
			if (list != null && list.size() > 0)
				return list.get(0);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
		}
		return null;
	}

	@Override
	public NicListHandover findHandoverByTransactionId(String transactionId,
			String type, Integer status, Boolean stage) {
		try {
			DetachedCriteria dCriteria = DetachedCriteria
					.forClass(getPersistentClass());
			dCriteria.createAlias("transactions", "transactions");
			dCriteria.add(Restrictions.eq("transactions.transactionId",
					transactionId));
			// dCriteria.add(Restrictions.eq("typeList", type));
			if (status != null) {
				dCriteria.add(Restrictions.eq("status", status));
			}
			if (stage != null) {
				dCriteria.add(Restrictions.eq("handoverStage", stage));
			}
			List<NicListHandover> list = this.findAll(dCriteria);
			if (list != null && list.size() > 0)
				return list.get(0);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
		}
		return null;
	}

	@Override
	public NicListHandover findHandoverByTransactionId1(String transactionId,
			int type, Integer[] status, Boolean stage) {
		try {
			DetachedCriteria dCriteria = DetachedCriteria
					.forClass(getPersistentClass());
			dCriteria.createAlias("transactions", "transactions");
			dCriteria.add(Restrictions.eq("transactions.transactionId",
					transactionId));
			// dCriteria.add(Restrictions.eq("typeList", type));
			if (status != null) {
				dCriteria.add(Restrictions.in("status", status));
			}
			if (stage != null) {
				dCriteria.add(Restrictions.eq("handoverStage", stage));
			}
			List<NicListHandover> list = this.findAll(dCriteria);
			if (list != null && list.size() > 0)
				return list.get(0);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
		}
		return null;
	}

	@Override
	public List<NicListHandover> findListHandoverListBApprove(int typeList,
			String leaderId, String asignId,
			AssignmentFilterAll assignmentFilter) {
		try {
			DetachedCriteria criteria = DetachedCriteria
					.forClass(getPersistentClass());
			// criteria.add(Restrictions.eq("typeList", typeList));
			if (StringUtils.isNotEmpty(leaderId)) {
				// criteria.createAlias("transactions", "transaction");
				criteria.add(Restrictions.eq("leaderId", leaderId));
			}
			if (StringUtils.isNotEmpty(asignId)) {
				criteria.add(Restrictions.eq("userLeaderProcess", asignId));
			}
			criteria.add(Restrictions.eq("handoverStage", true));
			// criteria.add(Restrictions.eq("", value));
			if (assignmentFilter != null) {

				if (StringUtils.isNotBlank(assignmentFilter.getPackageCode())) {
					criteria.add(Restrictions.eq("packageId",
							assignmentFilter.getPackageCode()));
				}
				if (assignmentFilter.getCreateDate() != null
						&& assignmentFilter.getEndDate() != null) {
					criteria.add(Restrictions.ge("createDate",
							assignmentFilter.getCreateDate()));
					criteria.add(Restrictions.le("createDate",
							assignmentFilter.getEndDate()));
				}
				if (StringUtils.isNotBlank(assignmentFilter
						.getTypeInvestigation())) {
					criteria.add(Restrictions.eq("status", Integer
							.parseInt(assignmentFilter.getTypeInvestigation())));
				}
			}
			return this.findAll(criteria);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
		}
		return new ArrayList<NicListHandover>();
	}

	@Override
	public BaseModelList<NicListHandover> findAllHandoverByTransactionId(
			String transactionId, String type, Integer status, Boolean stage) {
		try {
			DetachedCriteria dCriteria = DetachedCriteria
					.forClass(getPersistentClass());
			dCriteria.createAlias("transactions", "transactions");
			dCriteria.add(Restrictions.eq("transactions.transactionId",
					transactionId));
			 dCriteria.add(Restrictions.eq("id.typeList", type));
			if (status != null) {
				dCriteria.add(Restrictions.eq("status", status));
			}
			if (stage != null) {
				dCriteria.add(Restrictions.eq("handoverStage", stage));
			}
			List<NicListHandover> list = null;
			if (StringUtils.isNotBlank(transactionId)) {
				list = this.findAll(dCriteria);
			}
			return new BaseModelList<NicListHandover>(list, true, null);
		} catch (Exception e) {
			e.printStackTrace();
			return new BaseModelList<NicListHandover>(null, false,
					CreateMessageException.createMessageException(e)
							+ " - findAllHandoverByTransactionId - "
							+ transactionId + " - thất bại.");
		}
	}

	@Override
	public List<CountPassport> countHandoverAProcess(String datefrom)
			throws Exception {
		Session session = null;
		List<CountPassport> lst = null;
		try {
			session = this.openSession();
			Query query = session.getNamedQuery("countHandoverAProcess");
			query.setString("datefrom", datefrom);
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
	public List<CountPassport> countHandoverA(String datefrom) throws Exception {
		Session session = null;
		List<CountPassport> lst = null;
		try {
			session = this.openSession();
			Query query = session.getNamedQuery("countHandoverA");
			query.setString("datefrom", datefrom);
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
	public List<String> listTransactionA(String pack) throws Exception {
		Session session = null;
		List<String> lst = null;
		try {
			session = this.openSession();
			Query query = session.getNamedQuery("listTransactionA");
			query.setString("pack", pack);
			List<String> list = query.list();
			if (CollectionUtils.isNotEmpty(list)) {
				lst = new ArrayList<String>();
				for (int i = 0; i < list.size(); i++) {
					CountPassport countP = new CountPassport();
					String obj = list.get(i);
					lst.add(obj);
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
	public BaseModelSingle<Void> saveHandover(NicListHandover handover) {
		try {
			this.save(handover);
			return new BaseModelSingle<Void>(null, true, null);
		} catch (Throwable e) {
			e.printStackTrace();
			return new BaseModelSingle<Void>(null, false,
					CreateMessageException
							.createMessageException(new Exception(e))
							+ " - saveHandover: packageId = "
							+ handover.getId().getPackageId() + " - thất bại.");
		}
	}

	@Override
	public BaseModelSingle<Boolean> saveOrUpdateHandover(
			NicListHandover hanCheck) {
		try {
			this.saveOrUpdate(hanCheck);
			return new BaseModelSingle<Boolean>(true, true, null);
		} catch (Exception e) {
			return new BaseModelSingle<Boolean>(null, false,
					CreateMessageException
							.createMessageException(new Exception(e))
							+ " - saveOrUpdateHandover: packageId = "
							+ hanCheck.getId().getPackageId() + " - thất bại.");
		}
	}

}
