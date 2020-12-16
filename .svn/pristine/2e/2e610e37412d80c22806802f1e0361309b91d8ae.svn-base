package com.nec.asia.nic.comp.trans.dao.impl;

import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import oracle.jdbc.OracleTypes;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.hibernate.internal.SessionImpl;
import org.springframework.stereotype.Repository;

import com.nec.asia.nic.comp.trans.dao.EppPersonDao;
import com.nec.asia.nic.comp.trans.domain.BaseModelSingle;
import com.nec.asia.nic.comp.trans.domain.EppPerson;
import com.nec.asia.nic.comp.trans.dto.NicUploadJobDto;
import com.nec.asia.nic.comp.ws.log.domain.CreateMessageException;
import com.nec.asia.nic.framework.PaginatedResult;
import com.nec.asia.nic.framework.dao.impl.GenericHibernateDao;

@Repository("eppPersonDao")
public class EppPersonDaoImpl extends GenericHibernateDao<EppPerson, Long>
		implements EppPersonDao {

	@Override
	public List<EppPerson> findAllEppPerson(String searchName, String idNumber,
			String passportNo) {
		try {
			List<EppPerson> list = null;
			DetachedCriteria criteria = DetachedCriteria
					.forClass(EppPerson.class);
			if (StringUtils.isNotEmpty(searchName)) {
				criteria.add(Restrictions.eq("searchName", searchName));
			}
			if (StringUtils.isNotEmpty(idNumber)) {
				criteria.add(Restrictions.eq("idNumber", idNumber));
			}
			if (StringUtils.isNotEmpty(passportNo)) {
				// criteria.add(Restrictions.eq("passportNo", passportNo));
			}
			list = this.findAll(criteria);
			return list;
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
		}
		return null;
	}

	@Override
	public BaseModelSingle<EppPerson> findByStringCode(String code) {
		try {
			List<EppPerson> list = null;
			DetachedCriteria criteria = DetachedCriteria
					.forClass(EppPerson.class);
			if (StringUtils.isNotEmpty(code)) {
				criteria.add(Restrictions.eq("personCode", code));
				list = this.findAll(criteria);
			}
			if (list != null && list.size() > 0) {
				return new BaseModelSingle<EppPerson>(list.get(0), true, null);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return new BaseModelSingle<EppPerson>(null, false,
					CreateMessageException.createMessageException(e)
							+ " - findByStringCode - " + code + " - thất bại.");
		}
		return new BaseModelSingle<EppPerson>(null, true, null);
	}

	@Override
	public List<EppPerson> findAllEppPerson2(String searchName, String gender,
			String dateOfBirth, String placeOfBirthId) {
		try {
			List<EppPerson> list = null;
			DetachedCriteria criteria = DetachedCriteria
					.forClass(EppPerson.class);
			if (StringUtils.isNotEmpty(searchName)
					&& StringUtils.isNotEmpty(gender)
					&& StringUtils.isNotEmpty(dateOfBirth)
					&& StringUtils.isNotEmpty(placeOfBirthId)) {
				criteria.add(Restrictions.and(
						Restrictions.eq("searchName", searchName),
						Restrictions.eq("gender", gender),
						Restrictions.eq("dateOfBirth", dateOfBirth),
						Restrictions.eq("placeOfBirthCode", placeOfBirthId)));
				list = this.findAll(criteria);
			}
			return list;
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
		}
		return null;
	}

	@Override
	public BaseModelSingle<Boolean> saveOrUpdateData(EppPerson detail) {
		try {
			if (detail != null) {
				this.saveOrUpdate(detail);
				return new BaseModelSingle<Boolean>(true, true, null);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return new BaseModelSingle<Boolean>(false, false,
					CreateMessageException.createMessageException(e)
							+ " - saveOrUpdateData: " + detail.getPersonCode()
							+ " - thất bại.");
		}

		return new BaseModelSingle<Boolean>(false, true, null);
	}

	@Override
	public List<EppPerson> findByGlobalId(Long globalId) {
		try {
			List<EppPerson> list = null;
			DetachedCriteria criteria = DetachedCriteria
					.forClass(EppPerson.class);
			if (globalId != null) {
				criteria.add(Restrictions.eq("personId", globalId));
				list = this.findAll(criteria);
				return list;
			}
		} catch (Throwable e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
		}
		return null;
	}

	@Override
	public PaginatedResult<EppPerson> findAllEppPerson3(String searchName,
			String gender, String dateOfBirth, String placeOfBirthId,
			String nin, String type, int pageNo, int pageSize) {

		// List<EppPerson> list = null;
		try {
			DetachedCriteria criteria = DetachedCriteria
					.forClass(EppPerson.class);
			if (StringUtils.isNotEmpty(searchName)) {
				criteria.add(Restrictions.eq("searchName", searchName));
			}
			if (StringUtils.isNotEmpty(gender)) {
				criteria.add(Restrictions.eq("gender", gender));
			}
			if (StringUtils.isNotEmpty(dateOfBirth)) {
				criteria.add(Restrictions.eq("dateOfBirth", dateOfBirth));
			}
			if (StringUtils.isNotEmpty(placeOfBirthId)) {
				criteria.add(Restrictions
						.eq("placeOfBirthCode", placeOfBirthId));
			}
			if (StringUtils.isNotEmpty(nin)) {
				criteria.add(Restrictions.eq("idNumber", nin));
			}
			if (StringUtils.isNotEmpty(type)) {
				// criteria.add(Restrictions.eq("fpFlag", type));
			}
			PaginatedResult<EppPerson> result = this.findAllForPagination(
					criteria, pageNo, pageSize);
			// return list;
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
		}
		return null;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public PaginatedResult<NicUploadJobDto> findAllEppPerson4(
			String searchName, String gender, String dateOfBirth,
			String placeOfBirthId, String nin, String type, int pageNo,
			int pageSize) {
		List<NicUploadJobDto> listDto = new ArrayList<NicUploadJobDto>();
		try {
			StringBuffer sql = new StringBuffer(
					"select per.NAME , per.GENDER, per.DATE_OF_BIRTH, per.PLACE_OF_BIRTH_NAME , per.ID_NUMBER, per.PERSON_ID, wj.TRANSACTION_ID, per.DATE_OF_ID_ISSUE , per.PLACE_OF_BIRTH_NAME from EPP_CENTRAL.EPP_PERSON per, EPP_CENTRAL.EPP_WORKFLOW_JOB wj where per.PERSON_CODE = wj.ORIGINALY_PERSON_ID");
			if (searchName != null && !searchName.trim().equals("")) {
				sql.append(" and upper(per.NAME)  ='"
						+ searchName.trim().toUpperCase() + "'");
			}
			if (StringUtils.isNotEmpty(gender)) {
				sql.append(" and per.GENDER  ='" + gender + "'");
			}
			if (StringUtils.isNotEmpty(dateOfBirth)) {
				sql.append(" and per.DATE_OF_BIRTH  ='" + dateOfBirth + "'");
			}
			if (nin != null && !nin.trim().equals("")) {
				sql.append(" per.ID_NUMBER  ='" + nin.trim() + "'");
			}
			if (StringUtils.isNotEmpty(placeOfBirthId)) {
				sql.append(" and per.PLACE_OF_BIRTH_CODE ='" + placeOfBirthId
						+ "'");
			}
			if (StringUtils.isNotEmpty(type)) {
				// sql.append(" and per.FP_FLAG ='" + type + "'");
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
			if (list != null && !list.isEmpty() && list.size() > 0) {
				int i = (pageNo - 1) * pageSize;
				while (itr.hasNext()) {
					i++;
					Object[] record = (Object[]) itr.next();
					NicUploadJobDto ricLocal = new NicUploadJobDto();
					ricLocal.setStt(i);
					ricLocal.setFullName(record[0] != null ? record[0]
							.toString() : "");
					ricLocal.setGender(record[1] != null ? record[1].toString()
							: "");
					ricLocal.setDob(record[2] != null ? record[2].toString()
							: "");
					ricLocal.setPlaceOfBirth(record[3] != null ? record[3]
							.toString() : "");
					ricLocal.setNin(record[4] != null ? record[4].toString()
							: "");
					ricLocal.setUploadJobId(record[5] != null ? Long
							.parseLong(record[5].toString()) : null);
					ricLocal.setTransactionId(record[6] != null ? record[6]
							.toString() : "");
					ricLocal.setDateNin(record[7] != null ? record[7]
							.toString() : "");
					ricLocal.setAddressNin(record[8] != null ? record[8]
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
		return new PaginatedResult<NicUploadJobDto>(0, pageNo, listDto);
	}

	@Override
	public List<EppPerson> findAllByFields(Map<String, Object> fields)
			throws Exception {

		Session session = null;

		try {

			session = getHibernateTemplate().getSessionFactory().openSession();

			Criteria c = session.createCriteria(EppPerson.class);

			if (fields != null && fields.size() > 0) {
				for (String field : fields.keySet()) {
					c.add(Restrictions.eq(field, fields.get(field)));
				}

				return c.list();
			}

			return null;

			/*
			 * String hql =
			 * "select a.nicId from NicRegistrationData a where a.transactionId = ? "
			 * ;
			 * 
			 * Object[] parameters = { transactionId } ; List<String> resultList
			 * = (List<String>) this.getHibernateTemplate().find(hql,
			 * parameters);
			 * 
			 * 
			 * if (CollectionUtils.isNotEmpty(resultList)) { nicId =
			 * Long.parseLong(resultList.get(0)); }
			 */
			// return null;

		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			session.close();
		}
	}

	@Override
	public List<EppPerson> findAllByNin(String nin) {
		List<EppPerson> nicTransactionDBOList = new ArrayList<EppPerson>();
		DetachedCriteria detachedCriteria = DetachedCriteria
				.forClass(getPersistentClass());
		if (StringUtils.isNotEmpty(nin)) {
			detachedCriteria.add(Restrictions.eq("idNumber", nin));
			List<EppPerson> resultTransactions = this.findAll(detachedCriteria);
			nicTransactionDBOList.addAll(resultTransactions);
		}
		logger.info("[findAllByNin][{}] size:{}", new Object[] { nin,
				nicTransactionDBOList.size() });
		return nicTransactionDBOList;
	}

	@Override
	public List<EppPerson> findPersonByPersonCode(String personCode)
			throws Exception {
		List<EppPerson> list = null;
		try {
			DetachedCriteria criteria = DetachedCriteria
					.forClass(getPersistentClass());
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
	public List<EppPerson> findListPersonByOrgPersonCode(String[] orgPerson)
			throws Exception {
		List<EppPerson> list = null;
		try {
			DetachedCriteria criteria = DetachedCriteria
					.forClass(getPersistentClass());
			if (orgPerson.length > 0) {
				criteria.add(Restrictions.or(Restrictions.in("orgPerson", orgPerson), Restrictions.in("personCode", orgPerson)));
				list = this.findAll(criteria);
			}
		} catch (Exception e) {
			throw e;
		}
		return list;
	}

	@Override
	public PaginatedResult<NicUploadJobDto> getResultPersonInStore(String searchName,
			String gender, String dateOfBirth, String placeOfBirthId,
			String nin, String type, int pageNo, int pageSize) {
		List<NicUploadJobDto> listDto = null;
		// List<EppPerson> list = null;
		int total = 0;
		SessionImpl session = (SessionImpl) this.openSession();
		try {
			
			CallableStatement callableStatement = session
					.connection()
					.prepareCall(
							"{call Epp_central.GetResultPerson(?, ?, ?, ?, ?, ?, ?, ?)}");
			callableStatement.setInt(1, pageSize);
			callableStatement.setInt(2, pageNo);
			callableStatement.setString(3, searchName);
			callableStatement.setString(4, gender);
			callableStatement.setString(5, dateOfBirth);
			callableStatement.setString(6, nin);
			callableStatement.setString(7, placeOfBirthId);
			callableStatement.registerOutParameter(8, OracleTypes.CURSOR);
			callableStatement.execute();
			ResultSet resultSet = (ResultSet) callableStatement.getObject(8);
			if (resultSet.isBeforeFirst()) { 
				listDto = new ArrayList<NicUploadJobDto>();
				while (resultSet.next()) {
					NicUploadJobDto person = new NicUploadJobDto();
					String nameStr = resultSet.getString("NAME");
					String genderStr = resultSet.getString("GENDER");
					String dobStr = resultSet.getString("DOB");
					String idNumStr = resultSet.getString("IDNUM");
					Long personIdStr = resultSet.getLong("PERSONID");
					String ninStr = resultSet.getString("NIN");
					String tranIdStr = resultSet.getString("TRANID");
					String doiiStr = resultSet.getString("DOII");
					String pobStr = resultSet.getString("POBNAME");
					Long uploadJobId = resultSet.getLong("JOBID");
					person.setFullName(nameStr);
					person.setGender(genderStr);
					person.setDob(dobStr);
					person.setNin(StringUtils.isEmpty(idNumStr) ? ninStr : idNumStr);
					person.setPersonId(personIdStr);
					person.setDateIssuce(doiiStr);
					person.setTransactionId(tranIdStr);
					person.setPlaceOfBirth(pobStr);
					person.setUploadJobId(uploadJobId);
					listDto.add(person);
				}
			}
			
			CallableStatement callableStatementCount = session
					.connection()
					.prepareCall(
							"{call Epp_central.GetResultPerson_Count(?, ?, ?, ?, ?, ?, ?, ?)}");
			callableStatementCount.setInt(1, pageSize);
			callableStatementCount.setInt(2, pageNo);
			callableStatementCount.setString(3, searchName);
			callableStatementCount.setString(4, gender);
			callableStatementCount.setString(5, dateOfBirth);
			callableStatementCount.setString(6, nin);
			callableStatementCount.setString(7, placeOfBirthId);
			callableStatementCount.registerOutParameter(8, OracleTypes.CURSOR);
			callableStatementCount.execute();
			ResultSet resultSet1 = (ResultSet) callableStatementCount.getObject(8);
			if (resultSet1.isBeforeFirst()) { 
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
	public Long saveEppPerson(EppPerson epp) throws Exception {
		try {
			return this.save(epp);
		} catch (Exception e) {
			throw e;
		}
	}
}
