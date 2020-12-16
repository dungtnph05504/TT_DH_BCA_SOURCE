package com.nec.asia.nic.comp.trans.dao.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Query;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import bsh.StringUtil;

import com.nec.asia.nic.comp.trans.dao.EppDocumentReturnedDao;
import com.nec.asia.nic.comp.trans.domain.EppDocumentReturned;
import com.nec.asia.nic.comp.trans.dto.StatisticalPassportProvinceReturnedDto;
import com.nec.asia.nic.comp.ws.log.domain.EppWsLog;
import com.nec.asia.nic.framework.PaginatedResult;
import com.nec.asia.nic.framework.dao.impl.GenericHibernateDao;
import com.nec.asia.nic.framework.service.impl.DefaultBusinessServiceImpl;
import com.nec.asia.nic.utils.DateUtil;

@Repository
public class EppDocumentReturnedDaoImpl extends
		GenericHibernateDao<EppDocumentReturned, Long> implements
		EppDocumentReturnedDao {
	private SimpleDateFormat dateFormat = new SimpleDateFormat(
			DateUtil.FORMAT_YYYYMMDD);
	@Override
	public boolean insertDataTable(EppDocumentReturned epp) {
		try {
			this.saveOrUpdate(epp);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public List<EppDocumentReturned> getByPassportNo(String passportNo) {
		try {
			DetachedCriteria dCriteria = DetachedCriteria
					.forClass(EppDocumentReturned.class);
			dCriteria.add(Restrictions.eq("passportNo", passportNo));
			dCriteria.addOrder(Order.desc("createDate"));
			return findAll(dCriteria);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public PaginatedResult<StatisticalPassportProvinceReturnedDto> allStorageReceipt(
			String fromDate, String toDate, String officeId, int pageNo,
			int pageSize) {
		List<StatisticalPassportProvinceReturnedDto> listDto = new ArrayList<StatisticalPassportProvinceReturnedDto>();
		try {
			StringBuffer sql = new StringBuffer(
					"select DISTINCT reg.SURNAME_FULL, reg.MIDDLENAME_FULL,reg.GENDER,reg.FIRSTNAME_FULL,reg.DATE_OF_BIRTH,");
			StringBuffer sql2 = new StringBuffer(
					"from EPP_CENTRAL.EPP_REGISTRATION_DATA reg,EPP_CENTRAL.EPP_TRANSACTION txn,EPP_CENTRAL.EPP_DOCUMENT_DATA doc");
			StringBuffer sql3 = new StringBuffer(
					"where docRtn.PASSPORT_NO = doc.PASSPORT_NO");
			String str1 = "docRtn.PASSPORT_NO,docRtn.RECEIVED_DATE,docRtn.RETURN_DATE,docRtn.STORAGE_NO ";
			String str2 = ",EPP_CENTRAL.EPP_DOCUMENT_RETURNED docRtn ";
			String str3 = " and doc.TRANSACTION_ID = txn.TRANSACTION_ID and txn.TRANSACTION_ID = reg.TRANSACTION_ID";
			sql.append(str1).append(sql2).append(str2).append(sql3)
					.append(str3);
			if (StringUtils.isNotEmpty(fromDate)) {
				sql.append(" and TRUNC(TO_DATE(docRtn.RECEIVED_DATE ,'DD/MM/YY')) >= TRUNC(TO_DATE('"
						+ fromDate + "','DD/MM/YY'))");
				sql.append(" and TRUNC(TO_DATE(docRtn.RECEIVED_DATE ,'DD/MM/YY')) <= TRUNC(TO_DATE('"
						+ toDate + "','DD/MM/YY'))");
			}
			if (StringUtils.isNotEmpty(officeId)) {
				sql.append(" and docRtn.OFFICE_CODE='" + officeId.trim() + "'");
			}
			Query query = this.getSession().createSQLQuery(sql.toString());
			int count = query.list().size();
			int rowCount = 0;
			if (count != 0) {
				rowCount = count;
			}
			int firstResults = 0;
			if (pageNo == 0) {
				firstResults = 1;
			} else {
				firstResults = (pageNo - 1) * pageSize;
			}
			// int firstResults = (page - 1) < 0 ? 0 : (page - 1) * pageSize;
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
			Iterator itr = list.iterator();
			// int i = (pageNo - 1) * pageSize;
			SimpleDateFormat dateFormat = new SimpleDateFormat(
					DateUtil.FORMAT_YYYYMMDD);
			if (list != null && list.size() > 0 && !list.isEmpty()) {
				while (itr.hasNext()) {
					Object[] record = (Object[]) itr.next();
					StatisticalPassportProvinceReturnedDto dto = new StatisticalPassportProvinceReturnedDto();
					// i=i+1;
					dto.setDateOfBirth(record[4].toString());
					dto.setGender(record[2].toString());
					dto.setNumberSave(record[8].toString());
					dto.setName(record[0].toString() + " "
							+ record[1].toString() + " " + record[3].toString());
					dto.setPassportNo(record[5].toString());
					dto.setReceivedDate(record[6].toString());
					if (record[7] != null) {
						dto.setReturnDate(record[7].toString());
					} else {
						dto.setReturnDate(null);
					}
					dto.setTotalRecord(rowCount);
					listDto.add(dto);
				}
				PaginatedResult<StatisticalPassportProvinceReturnedDto> result = new PaginatedResult<StatisticalPassportProvinceReturnedDto>(
						rowCount, pageNo, listDto);
				return result;
			}
			// DetachedCriteria criteria = DetachedCriteria
			// .forClass(getPersistentClass());
			// Order orders = Order.desc("receivedDate");
			// Date auditDateFrom = DateUtil.strToDate(fromDate,
			// DateUtil.FORMAT_DDMMYYYY);
			// Date auditDateTo = DateUtil.strToDate(toDate,
			// DateUtil.FORMAT_DDMMYYYY);
			// if (auditDateFrom != null && auditDateTo != null) {
			// criteria.add(Restrictions.between("receivedDate", auditDateFrom,
			// auditDateTo));
			// } else if(auditDateFrom!=null){
			// criteria.add(Restrictions.ge("receivedDate", auditDateFrom));
			// } else if(auditDateTo!=null){
			// criteria.add(Restrictions.le("receivedDate", auditDateTo));
			// }
			// return this.findAllForPagination(criteria, pageNo, pageSize,
			// orders);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return new PaginatedResult<>(0, pageNo, listDto);
	}

	@Override
	public PaginatedResult<StatisticalPassportProvinceReturnedDto> allStorageReceived(
			String fromDate, String toDate, String officeId, int pageNo,
			int pageSize) {
		List<StatisticalPassportProvinceReturnedDto> listDto = new ArrayList<StatisticalPassportProvinceReturnedDto>();
		try {
			StringBuffer sql = new StringBuffer(
					"select DISTINCT reg.SURNAME_FULL, reg.MIDDLENAME_FULL,reg.GENDER,reg.FIRSTNAME_FULL,reg.DATE_OF_BIRTH,");
			StringBuffer sql2 = new StringBuffer(
					"from EPP_CENTRAL.EPP_REGISTRATION_DATA reg,EPP_CENTRAL.EPP_TRANSACTION txn,EPP_CENTRAL.EPP_DOCUMENT_DATA doc");
			StringBuffer sql3 = new StringBuffer(
					"where docRtn.PASSPORT_NO = doc.PASSPORT_NO");
			String str1 = "docRtn.PASSPORT_NO,docRtn.RECEIVED_DATE,docRtn.RETURN_DATE,docRtn.STORAGE_NO ";
			String str2 = ",EPP_CENTRAL.EPP_DOCUMENT_RETURNED docRtn";
			String str3 = " and doc.PASSPORT_NO = txn.PASSPORT_NO and txn.TRANSACTION_ID = reg.TRANSACTION_ID";
			sql.append(str1).append(sql2).append(str2).append(sql3)
					.append(str3);
			if (StringUtils.isNotEmpty(fromDate)) {
				sql.append(" and TRUNC(TO_DATE(docRtn.RETURN_DATE ,'DD/MM/YY')) >= TRUNC(TO_DATE('"
						+ fromDate + "','DD/MM/YY'))");
				sql.append(" and TRUNC(TO_DATE(docRtn.RETURN_DATE ,'DD/MM/YY')) >= TRUNC(TO_DATE('"
						+ toDate + "','DD/MM/YY'))");
			}
			if (StringUtils.isNotEmpty(officeId)) {
				sql.append("and docRtn.OFFICE_CODE='" + officeId.trim() + "'");
			}
			Query query = this.getSession().createSQLQuery(sql.toString());
			int count = query.list().size();
			int rowCount = 0;
			if (count != 0) {
				rowCount = count;
			}
			int firstResults = 0;
			if (pageNo == 0) {
				firstResults = 1;
			} else {
				firstResults = (pageNo - 1) * pageSize;
			}
			// int firstResults = (page - 1) < 0 ? 0 : (page - 1) * pageSize;
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
			Iterator itr = list.iterator();
			// int i = (pageNo - 1) * pageSize;
			if (list != null && list.size() > 0 && !list.isEmpty()) {
				while (itr.hasNext()) {
					Object[] record = (Object[]) itr.next();
					StatisticalPassportProvinceReturnedDto dto = new StatisticalPassportProvinceReturnedDto();
					// i=i+1;
					dto.setDateOfBirth(record[4] != null ? record[4].toString()
							: "");
					dto.setGender(record[2] != null ? record[2].toString() : "");
					dto.setNumberSave(record[8] != null ? record[8].toString()
							: "");
					dto.setName(record[0].toString() + " "
							+ record[1].toString() + " " + record[3].toString());
					dto.setPassportNo(record[5] != null ? record[5].toString()
							: "");
					dto.setReceivedDate(record[6] != null ? record[6]
							.toString() : "");
					if (record[7] != null) {
						dto.setReturnDate(record[7].toString());
					} else {
						dto.setReturnDate(null);
					}
					dto.setTotalRecord(rowCount);
					listDto.add(dto);
				}
				PaginatedResult<StatisticalPassportProvinceReturnedDto> result = new PaginatedResult<StatisticalPassportProvinceReturnedDto>(
						rowCount, pageNo, listDto);
				return result;
			}
			// DetachedCriteria criteria = DetachedCriteria
			// .forClass(getPersistentClass());
			// Order orders = Order.desc("receivedDate");
			// Date auditDateFrom = DateUtil.strToDate(fromDate,
			// DateUtil.FORMAT_DDMMYYYY);
			// Date auditDateTo = DateUtil.strToDate(toDate,
			// DateUtil.FORMAT_DDMMYYYY);
			// if (auditDateFrom != null && auditDateTo != null) {
			// criteria.add(Restrictions.between("receivedDate", auditDateFrom,
			// auditDateTo));
			// } else if(auditDateFrom!=null){
			// criteria.add(Restrictions.ge("receivedDate", auditDateFrom));
			// } else if(auditDateTo!=null){
			// criteria.add(Restrictions.le("receivedDate", auditDateTo));
			// }
			// return this.findAllForPagination(criteria, pageNo, pageSize,
			// orders);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return new PaginatedResult<>(0, pageNo, listDto);
	}

	@Override
	public List<StatisticalPassportProvinceReturnedDto> getAllStorageReceived(
			String fromDate, String toDate, String officeId) throws Exception {
		List<StatisticalPassportProvinceReturnedDto> listDto = null;
		try {
			StringBuffer sql = new StringBuffer(
					" select DISTINCT reg.SURNAME_FULL, reg.MIDDLENAME_FULL, reg.GENDER, reg.FIRSTNAME_FULL, reg.DATE_OF_BIRTH, ");
			StringBuffer sql2 = new StringBuffer(
					" from EPP_CENTRAL.EPP_REGISTRATION_DATA reg, EPP_CENTRAL.EPP_TRANSACTION txn, EPP_CENTRAL.EPP_DOCUMENT_DATA doc ");
			StringBuffer sql3 = new StringBuffer(
					" where docRtn.PASSPORT_NO = doc.PASSPORT_NO ");
			String str1 = " docRtn.PASSPORT_NO,docRtn.RECEIVED_DATE,docRtn.RETURN_DATE,docRtn.STORAGE_NO ";
			String str2 = ", EPP_CENTRAL.EPP_DOCUMENT_RETURNED docRtn ";
			String str3 = " and doc.TRANSACTION_ID = txn.TRANSACTION_ID and txn.TRANSACTION_ID = reg.TRANSACTION_ID ";
			sql.append(str1).append(sql2).append(str2).append(sql3)
					.append(str3);
			if (StringUtils.isNotEmpty(fromDate)) {
				sql.append(" and TRUNC(TO_DATE(docRtn.RETURN_DATE ,'DD/MM/YY')) >= TRUNC(TO_DATE('"
						+ fromDate + "','DD/MM/YY')) ");
				sql.append(" and TRUNC(TO_DATE(docRtn.RETURN_DATE ,'DD/MM/YY')) >= TRUNC(TO_DATE('"
						+ toDate + "','DD/MM/YY')) ");
			}
			if (StringUtils.isNotEmpty(officeId)) {
				sql.append(" and docRtn.OFFICE_CODE='" + officeId.trim() + "' ");
			}
			Query query = this.getSession().createSQLQuery(sql.toString());
			List list = query.list();
			Iterator itr = list.iterator();
			if (list != null && list.size() > 0 && !list.isEmpty()) {
				listDto = new ArrayList<StatisticalPassportProvinceReturnedDto>();
				while (itr.hasNext()) {
					Object[] record = (Object[]) itr.next();
					StatisticalPassportProvinceReturnedDto dto = new StatisticalPassportProvinceReturnedDto();
					if (record[4] != null) {
						Date dateOfBirth = DateUtil.strToDate(record[4].toString(), DateUtil.FORMAT_TIMESTAMP);
						dto.setDateOfBirth(dateFormat.format(dateOfBirth));
					}
					dto.setGender(record[2] != null ? record[2].toString() : null);
					dto.setNumberSave(record[8] != null ? record[8].toString()
							: null);
					String name = "";
					if (record[0] != null) {
						name = record[0].toString();
					}
					if (record[1] != null) {
						name = name + " " + record[1].toString();
					}
					if (record[3] != null) {
						name = name + " " + record[3].toString();
					}
					dto.setName(name);
					dto.setPassportNo(record[5] != null ? record[5].toString()
							: null);
					if (record[6] != null) {
						Date dateRec = DateUtil.strToDate(record[6].toString(), DateUtil.FORMAT_TIMESTAMP);
						dto.setReceivedDate(dateFormat.format(dateRec));
					}
					if (record[7] != null) {
						Date dateRet = DateUtil.strToDate(record[7].toString(), DateUtil.FORMAT_TIMESTAMP);
						dto.setReturnDate(dateFormat.format(dateRet));
					}
					listDto.add(dto);
				}
			}
		} catch (Exception e) {
			throw e;
		}
		return listDto;
	}

	@Override
	public List<StatisticalPassportProvinceReturnedDto> getAllStorageReceipt(
			String fromDate, String toDate, String officeId) throws Exception {
		List<StatisticalPassportProvinceReturnedDto> listDto = null;
		try {
			StringBuffer sql = new StringBuffer(
					"select DISTINCT reg.SURNAME_FULL, reg.MIDDLENAME_FULL,reg.GENDER,reg.FIRSTNAME_FULL,reg.DATE_OF_BIRTH,");
			StringBuffer sql2 = new StringBuffer(
					"from EPP_CENTRAL.EPP_REGISTRATION_DATA reg,EPP_CENTRAL.EPP_TRANSACTION txn,EPP_CENTRAL.EPP_DOCUMENT_DATA doc");
			StringBuffer sql3 = new StringBuffer(
					"where docRtn.PASSPORT_NO = doc.PASSPORT_NO");
			String str1 = "docRtn.PASSPORT_NO,docRtn.RECEIVED_DATE,docRtn.RETURN_DATE,docRtn.STORAGE_NO ";
			String str2 = ",EPP_CENTRAL.EPP_DOCUMENT_RETURNED docRtn ";
			String str3 = " and doc.TRANSACTION_ID = txn.TRANSACTION_ID and txn.TRANSACTION_ID = reg.TRANSACTION_ID";
			sql.append(str1).append(sql2).append(str2).append(sql3)
					.append(str3);
			if (StringUtils.isNotEmpty(fromDate)) {
				sql.append(" and TRUNC(TO_DATE(docRtn.RECEIVED_DATE ,'DD/MM/YY')) >= TRUNC(TO_DATE('"
						+ fromDate + "','DD/MM/YY'))");
				sql.append(" and TRUNC(TO_DATE(docRtn.RECEIVED_DATE ,'DD/MM/YY')) <= TRUNC(TO_DATE('"
						+ toDate + "','DD/MM/YY'))");
			}
			if (StringUtils.isNotEmpty(officeId)) {
				sql.append(" and docRtn.OFFICE_CODE='" + officeId.trim() + "'");
			}
			Query query = this.getSession().createSQLQuery(sql.toString());
			List list = query.list();
			Iterator itr = list.iterator();
			if (list != null && list.size() > 0 && !list.isEmpty()) {
				listDto = new ArrayList<StatisticalPassportProvinceReturnedDto>();
				while (itr.hasNext()) {
					Object[] record = (Object[]) itr.next();
					StatisticalPassportProvinceReturnedDto dto = new StatisticalPassportProvinceReturnedDto();
					if (record[4] != null) {
						Date dateOfBirth = DateUtil.strToDate(record[4].toString(), DateUtil.FORMAT_TIMESTAMP);
						dto.setDateOfBirth(dateFormat.format(dateOfBirth));
					}
					if (record[2] != null) {
						dto.setGender(record[2].toString());
					}
					if (record[8] != null) {
						dto.setNumberSave(record[8].toString());
					}
					String name = "";
					if (record[0] != null) {
						name = record[0].toString();
					}
					if (record[1] != null) {
						name = name + " " + record[1].toString();
					}
					if (record[3] != null) {
						name = name + " " + record[3].toString();
					}
					dto.setName(name);
					if (record[5] != null) {
						dto.setPassportNo(record[5].toString());
					}
					if (record[6] != null) {
						Date dateRec = DateUtil.strToDate(record[6].toString(), DateUtil.FORMAT_TIMESTAMP);
						dto.setReceivedDate(dateFormat.format(dateRec));
					}
					if (record[7] != null) {
						Date dateRet = DateUtil.strToDate(record[7].toString(), DateUtil.FORMAT_TIMESTAMP);
						dto.setReturnDate(dateFormat.format(dateRet));
					}
					listDto.add(dto);
				}
			}
		} catch (Exception e) {
			throw e;
		}
		return listDto;
	}

}
