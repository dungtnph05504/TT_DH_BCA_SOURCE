package com.nec.asia.nic.comp.trans.dao.impl;

import java.sql.Blob;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import jcifs.util.Base64;
import oracle.jdbc.internal.OracleTypes;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Projection;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.nec.asia.nic.comp.a08database.connect.A08DatabaseConnection;
import com.nec.asia.nic.comp.a08database.domain.LSCapHoChieu;
import com.nec.asia.nic.comp.trans.dao.NicSearchHitResultDao;
import com.nec.asia.nic.comp.trans.domain.BufEppPersonInvestigation;
import com.nec.asia.nic.comp.trans.domain.NicSearchHitResult;
import com.nec.asia.nic.comp.trans.dto.NicHitTransaction;
import com.nec.asia.nic.comp.trans.dto.NicUploadJobDto;
import com.nec.asia.nic.comp.trans.dto.SearchResultInA08Dto;
import com.nec.asia.nic.framework.dao.DaoException;
import com.nec.asia.nic.framework.dao.impl.GenericHibernateDao;
import com.nec.asia.nic.utils.DateUtil;



/**
 * @author setia_budiyono
 *
 */
@Repository("searchHitResultDao")
public class NicSearchHitResultDaoImpl extends
		GenericHibernateDao<NicSearchHitResult, Long> implements NicSearchHitResultDao {

	@Override
	public List<NicSearchHitResult> findHitResultBySearchId (Long searchResultId) throws DaoException {
		List <NicSearchHitResult> resultList = null;
		DetachedCriteria searchResult = DetachedCriteria.forClass(getPersistentClass());
		//DetachedCriteria searchResult = criteria.createCriteria("searchResult");
		searchResult.add(Restrictions.eq("searchResultId", searchResultId));
		searchResult.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		
		resultList = this.findAll(searchResult); 
		return resultList;
		
	}
	
	@Override
	public List<NicSearchHitResult> sortHitResultBySearchId (Long searchResultId) throws DaoException {
		List <NicSearchHitResult> resultList = null;
		DetachedCriteria criteria = DetachedCriteria.forClass(NicSearchHitResult.class);
		Criterion crit1= Restrictions.eq("verifyDecision", "Y"); //(04/09/2013):Sailaja - modified to get the hit records.
		Criterion crit2= Restrictions.isNull("verifyDecision");	
		criteria.add(Restrictions.or(crit1, crit2));
		criteria.add(Restrictions.eq("searchResultId", searchResultId));
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		resultList = this.findAll(criteria); 
		return resultList;
		
	}

	
	@Override
	public List<NicSearchHitResult> findHitPositions(String hitTransactionId, Long searchResultId)
			throws DaoException {
		DetachedCriteria criteria = DetachedCriteria.forClass(NicSearchHitResult.class);
		
		if(StringUtils.isNotEmpty(hitTransactionId))
			criteria.add(Restrictions.eq("transactionIdHit", hitTransactionId));
		
		if(searchResultId != null)
		   criteria.add(Restrictions.eq("searchResultId", searchResultId));
		   return (List<NicSearchHitResult>) this.getHibernateTemplate().findByCriteria(criteria);
		
	}
	
	@Override
	public List<NicHitTransaction> findHitBySource(String tranid, String source, String regSite, long searchId)
			throws Exception {
		Session session = null;
		List<NicHitTransaction> lst = null;
		try {
			session = this.openSession();
			Query query = null;
			if(source.equals("BMS"))
				query = session.getNamedQuery("getHitTransactionByDataSourceBMS");
			else
				query = session.getNamedQuery("getHitTransactionByDataSource");
			
	        query.setString("tranid", tranid);
			query.setString("source", source);
			query.setString("regSite", regSite);
			query.setLong("searchId", searchId);
			List<Object[]> list = query.list();
			if (CollectionUtils.isNotEmpty(list)) {
				lst = new ArrayList<NicHitTransaction>();
				for (int i = 0; i < list.size(); i++) {
					NicHitTransaction countP = new NicHitTransaction();
					 Object[] obj = (Object[]) list.get(i);
					 countP.setTranid((String) obj[0]);
					 countP.setPersonId((Long) obj[1]);
					 countP.setHitId((Long) obj[2]);
					 lst.add(countP);
				}
			}
			//logger.info("[updateTransactionStatus] update result returned for status: {}, updateCount: {}", status, updateCount);
		} catch (HibernateException ex) {
			throw new Exception(ex);
		}finally {
			session.close();
		}
		return lst;
	}
	
	@Override
	public List<String> findListHitSpecial(String transactionId, String typeHit, int pageNumber, int pageSize)
			throws Exception {
		Session session = null;
		List<String> lst = null;
		try {
			session = this.openSession();
			Query query = session.getNamedQuery("blacklistHit");
			int firstResult = (pageNumber - 1) * pageSize;
	        int maxResults = pageSize;
	        query.setFirstResult(firstResult);
	        query.setMaxResults(maxResults);
	        query.setString("tranId", transactionId);
			query.setString("type", typeHit);
			List<String> list = query.list();
			if (CollectionUtils.isNotEmpty(list)) {
				return list;
			}
			//logger.info("[updateTransactionStatus] update result returned for status: {}, updateCount: {}", status, updateCount);
		} catch (HibernateException ex) {
			throw new Exception(ex);
		}finally {
			session.close();
		}
		return lst;
	}
	
	@Override
	public List<NicSearchHitResult> findHitPositionsHit(String hitTransactionId, Long hitResultId)
			throws DaoException {
		DetachedCriteria criteria = DetachedCriteria.forClass(NicSearchHitResult.class);
		
		if(StringUtils.isNotEmpty(hitTransactionId))
			criteria.add(Restrictions.eq("transactionIdHit", hitTransactionId));
		
		if(hitResultId != null)
		   criteria.add(Restrictions.eq("hitResultId", hitResultId));
		   return (List<NicSearchHitResult>) this.getHibernateTemplate().findByCriteria(criteria);
		
	}

	
	@Override
	public void updateDataOnTrueHit(String hitTransactionId, Long searchResultId, String userId, String remarks)
			throws DaoException {
		logger.info("Inside Dao =========True Hit>>>>>>>>>>>>");
		DetachedCriteria criteria = DetachedCriteria.forClass(NicSearchHitResult.class);
		criteria.add(Restrictions.eq("transactionIdHit", hitTransactionId));
		criteria.add(Restrictions.eq("searchResultId", searchResultId));
		List<NicSearchHitResult> list = (List<NicSearchHitResult>) getHibernateTemplate().findByCriteria(criteria);
		try {
			for (NicSearchHitResult updateObj : list) {
				updateObj.setVerifyDecision(true);
				updateObj.setVerifyDecisionBy(userId);
				updateObj.setVerifyDecisionDatetime(new Date());
				updateObj.setVerifyDecisionRemarks(remarks);
				saveOrUpdate(updateObj);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void updateDataOnFalseHit(String hitTransactionId, Long searchResultId, String userId, String remarks)
			throws DaoException {
		logger.info("Inside Dao ============= False Hit>>>>>>>>>>>");
		DetachedCriteria criteria = DetachedCriteria.forClass(NicSearchHitResult.class);
		criteria.add(Restrictions.eq("transactionIdHit", hitTransactionId));
		criteria.add(Restrictions.eq("searchResultId", searchResultId));
		List<NicSearchHitResult> list = (List<NicSearchHitResult>) getHibernateTemplate().findByCriteria(criteria);
		try {
			for (NicSearchHitResult updateObj : list) {
				updateObj.setVerifyDecision(false);
				updateObj.setVerifyDecisionBy(userId);
				updateObj.setVerifyDecisionDatetime(new Date());
				if (remarks != null) {
					updateObj.setVerifyDecisionRemarks(remarks);
				}
				saveOrUpdate(updateObj);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	
	@Override
	public List<String> getCount(Long searchResultId, String transactionId) {
		//System.out.println("Inside Dao ============= False Hit>>>>>>>>>>>");
		DetachedCriteria criteria = DetachedCriteria.forClass(NicSearchHitResult.class);
		criteria.add(Restrictions.eq("searchResultId", searchResultId));
		criteria.add(Restrictions.ne("transactionIdHit", transactionId));
		criteria.setProjection(Projections.property("verifyDecision"));
		List<String> verifyDecision = (List<String>) getHibernateTemplate().findByCriteria(criteria);
		return verifyDecision;
	}
	
	@Override
	public List<Boolean> getCount(String transactionId  ,Long searchResultId ) { 
		DetachedCriteria criteria = DetachedCriteria.forClass(NicSearchHitResult.class);
		criteria.add(Restrictions.eq("searchResultId", searchResultId));
		criteria.add(Restrictions.ne("transactionIdHit", transactionId));
		criteria.setProjection(Projections.property("verifyDecision"));
		return (List<Boolean>) getHibernateTemplate().findByCriteria(criteria); 
	}
	
	@Override
	public void updateDataOnTrueHit(String hitTransactionId, List<Long> searchResultIds, String userId, String remarks)
			throws DaoException { 
		DetachedCriteria criteria = DetachedCriteria.forClass(NicSearchHitResult.class);
		criteria.add(Restrictions.eq("transactionIdHit", hitTransactionId));
		criteria.add(Restrictions.in("searchResultId", searchResultIds));
		List<NicSearchHitResult> list = (List<NicSearchHitResult>) getHibernateTemplate().findByCriteria(criteria);
		try {
			for (NicSearchHitResult updateObj : list) {
				updateObj.setVerifyDecision(true);
				updateObj.setVerifyDecisionBy(userId);
				updateObj.setVerifyDecisionDatetime(new Date());
				updateObj.setVerifyDecisionRemarks(remarks);
				saveOrUpdate(updateObj);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void updateDataOnFalseHit(String hitTransactionId, List<Long> searchResultIds, String userId, String remarks)
			throws DaoException { 
		DetachedCriteria criteria = DetachedCriteria.forClass(NicSearchHitResult.class);
		criteria.add(Restrictions.eq("transactionIdHit", hitTransactionId));
		criteria.add(Restrictions.in("searchResultId", searchResultIds));
		List<NicSearchHitResult> list = (List<NicSearchHitResult>) getHibernateTemplate().findByCriteria(criteria);
		try {
			for (NicSearchHitResult updateObj : list) {
				updateObj.setVerifyDecision(false);
				updateObj.setVerifyDecisionBy(userId);
				updateObj.setVerifyDecisionDatetime(new Date());
				if (remarks != null) {
					updateObj.setVerifyDecisionRemarks(remarks);
				}
				saveOrUpdate(updateObj);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public List<Long> getHitTransaction(String tranid, int pageNumber, int pageSize) throws Exception {
		Session session = null;
		List<NicUploadJobDto> lst = null;
		try {
			session = this.openSession();
			Query query = session.getNamedQuery("getHitTransaction");
			int firstResult = (pageNumber - 1) * pageSize;
	        int maxResults = pageSize;
	        query.setFirstResult(firstResult);
	        query.setMaxResults(maxResults);
	        query.setString("tranId", tranid);
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
					 
					 if(pageNumber == 1){
						 countP.setStt(i + 1);
					 }
					 else{
						 countP.setStt(((pageNumber - 1) * pageSize) + i + 1);
					 }
					 lst.add(countP);
				}
			}
			//logger.info("[updateTransactionStatus] update result returned for status: {}, updateCount: {}", status, updateCount);
		} catch (HibernateException ex) {
			throw new Exception(ex);
		}finally {
			session.close();
		}
		return null;
	}
	
	@Override
	public List<BufEppPersonInvestigation> getResultA08(SearchResultInA08Dto request) throws Exception{
		Connection conn = null;
		List<BufEppPersonInvestigation> resultBuf = null;
		try {
			conn = A08DatabaseConnection.connectA08Database();
			conn.setAutoCommit(false);
			//conn = A08DatabaseConnection.connectDatabaseTest();
			if (conn != null) {
				logger.info("INPUT_A08: " + request.toString());
				CallableStatement command = conn.prepareCall(A08DatabaseConnection.GET_SO_SANH_CA_NHAN);
				command.registerOutParameter(1, OracleTypes.CURSOR);
				command.setString(2, request.getpHoTen());
				command.setString(3, request.getpNgaySinh());
				command.setString(4, request.getpKieuNS());
				command.setString(5, request.getpQTich());
				command.setString(6, request.getpGTinh());
				command.setString(7, request.getpSoHC());
				command.setString(8, request.getpSoCMND());
				command.setString(9, request.getpMaCaNhan());
				command.execute();
				ResultSet result = (ResultSet) command.getObject(1);
				if(result != null){
					resultBuf = new ArrayList<BufEppPersonInvestigation>();
					while(result.next()){
						BufEppPersonInvestigation buff =new BufEppPersonInvestigation();
						buff.setType("CPD");
						buff.setKey(result.getLong("KEY_"));
						buff.setDataType(result.getString("LOAIDULIEU"));
						buff.setMaCaNhan(result.getString("MACANHAN"));
						buff.setName(result.getString("NAME"));
						buff.setOtherName(result.getString("OTHER_NAME"));
						buff.setGender(result.getString("GENDER"));
						buff.setDateOfBirth(result.getString("DATE_OF_BIRTH"));
						buff.setPlaceOfBirthName(result.getString("PLACE_OF_BIRTH_NAME"));
						buff.setIdNumber(result.getString("ID_NUMBER"));
						buff.setDateOfIdIssue(result.getString("DATE_OF_ID_ISSUE"));
						buff.setPlaceOfIdIssueName(result.getString("PLACE_OF_ID_ISSUE_NAME"));
						buff.setEthnic(result.getString("ETHNIC"));
						buff.setReligion(result.getString("RELIGION"));
						buff.setSearchName(result.getString("SEARCH_NAME"));
						buff.setNationalityName(result.getString("NATIONALITY_NAME"));
						buff.setResidentPlaceName(result.getString("RESIDENT_PLACE_NAME"));
						buff.setResidentAddress(result.getString("RESIDENT_ADDRESS"));
						buff.setTmpAddress(result.getString("TMP_ADDRESS"));
						buff.setOccupation(result.getString("OCCUPATION"));
						buff.setOfficeInfo(result.getString("OFFICE_INFO"));
						buff.setFatherName(result.getString("FATHER_NAME"));
						buff.setFatherNationality(result.getString("FATHER_NATIONALITY"));
						buff.setFatherOccupation(result.getString("FATHER_OCCUPATION"));
						buff.setMotherName(result.getString("MOTHER_NAME"));
						buff.setMotherNationality(result.getString("MOTHER_NATIONALITY"));
						buff.setMotherOccupation(result.getString("MOTHER_OCCUPATION"));
						buff.setOrgPerson(buff.getMaCaNhan());
						buff.setPassportNo(result.getString("LIST_OF_PASSPORT_NO"));
						/*if (buff.getDataType().contains("HC")) {
							buff.setPassportNo(getPassportNo(conn, buff.getMaCaNhan()));
						}*/
						try {
							Blob blob = result.getBlob("PHOTO");
							byte[] photo = blob
									.getBytes(1, (int) blob.length());
							buff.setPhoto(photo);
						} catch (SQLException e) {
							logger.error("get PHOTO error: " + e.getMessage());
						}
						
						buff.setSrc("A08");
						buff.setCreateBy("SYSTEM");
						buff.setCreateDatetime(new Date());
						resultBuf.add(buff);
					}
				}
			}
		} catch (Exception e) {
			logger.error("GET DATA A08 FAIL: " + e.getMessage());
		}finally{
			if (conn != null) {
				conn.close();
			}
		}
		
		return resultBuf;
	}

	@Override
	public List<NicSearchHitResult> findHitResultsByTranIdAndSearchId(
			String tranId, Long searchId) throws Exception {
		List<NicSearchHitResult> list = null;
		try {
			DetachedCriteria criteria = DetachedCriteria.forClass(getPersistentClass());
			if (StringUtils.isNotBlank(tranId)) {
				criteria.add(Restrictions.eq("transactionIdHit", tranId));
			}
			if (StringUtils.isNotBlank(searchId.toString())) {
				criteria.add(Restrictions.eq("searchResultId", searchId));
			}
			list = this.findAll(criteria);
		} catch (Exception e) {
			throw e;
		}
		return list;
	}
	
	private String getPassportNo (Connection conn, String personCode){
		String passportNo = "";
		try {
			CallableStatement command = conn
					.prepareCall(A08DatabaseConnection.FUNCTION_GET_HC_DETAILS);
			command.registerOutParameter(1, -10);
			command.setString(2, personCode);
			command.execute();
			ResultSet result = (ResultSet) command.getObject(1);
			if (result != null) {
				while (result.next()) {
					String ppStatus = result.getString("PP_STATUS");
					Date dateOfEx = null;
					Date now = new Date();
					if (result.getString("PP_DATE_OF_EXPIRY") != null) {
						try {
							dateOfEx = DateUtil.strToDate(result.getString("PP_DATE_OF_EXPIRY"), DateUtil.FORMAT_YYYYMMDD);
						} catch (Exception e) {
						}
					}
					if ("1".equals(ppStatus) && dateOfEx != null && dateOfEx.getTime() >= now.getTime()) {
						if (passportNo == "") {
							passportNo = result.getString("PASSPORT_NO");
						}else{
							passportNo += "," + result.getString("PASSPORT_NO");
						}
					}
				}
			}
		} catch (Exception e) {
		}
		return passportNo;
	}

	@Override
	public List<BufEppPersonInvestigation> getResultA08Check(
			SearchResultInA08Dto request) throws Exception {
		Connection conn = null;
		List<BufEppPersonInvestigation> resultBuf = null;
		try {
			conn = A08DatabaseConnection.connectA08Database();
			conn.setAutoCommit(false);
			//conn = A08DatabaseConnection.connectDatabaseTest();
			if (conn != null) {
				logger.info("INPUT_A08: " + request.toString());
				CallableStatement command = conn.prepareCall(A08DatabaseConnection.GET_SO_SANH_CA_NHAN);
				command.registerOutParameter(1, OracleTypes.CURSOR);
				command.setString(2, request.getpHoTen());
				command.setString(3, request.getpNgaySinh());
				command.setString(4, request.getpKieuNS());
				command.setString(5, request.getpQTich());
				command.setString(6, request.getpGTinh());
				command.setString(7, request.getpSoHC());
				command.setString(8, request.getpSoCMND());
				command.setString(9, request.getpMaCaNhan());
				command.execute();
				ResultSet result = (ResultSet) command.getObject(1);
				if(result != null){
					resultBuf = new ArrayList<BufEppPersonInvestigation>();
					while(result.next()){
						BufEppPersonInvestigation buff =new BufEppPersonInvestigation();
						buff.setType("CPD");
						buff.setKey(result.getLong("KEY_"));
						buff.setDataType(result.getString("LOAIDULIEU"));
						buff.setMaCaNhan(result.getString("MACANHAN"));
						buff.setName(result.getString("NAME"));
						buff.setOtherName(result.getString("OTHER_NAME"));
						buff.setGender(result.getString("GENDER"));
						buff.setDateOfBirth(result.getString("DATE_OF_BIRTH"));
						buff.setPlaceOfBirthName(result.getString("PLACE_OF_BIRTH_NAME"));
						buff.setIdNumber(result.getString("ID_NUMBER"));
						buff.setDateOfIdIssue(result.getString("DATE_OF_ID_ISSUE"));
						buff.setPlaceOfIdIssueName(result.getString("PLACE_OF_ID_ISSUE_NAME"));
						buff.setEthnic(result.getString("ETHNIC"));
						buff.setReligion(result.getString("RELIGION"));
						buff.setSearchName(result.getString("SEARCH_NAME"));
						buff.setNationalityName(result.getString("NATIONALITY_NAME"));
						buff.setResidentPlaceName(result.getString("RESIDENT_PLACE_NAME"));
						buff.setResidentAddress(result.getString("RESIDENT_ADDRESS"));
						buff.setTmpAddress(result.getString("TMP_ADDRESS"));
						buff.setOccupation(result.getString("OCCUPATION"));
						buff.setOfficeInfo(result.getString("OFFICE_INFO"));
						buff.setFatherName(result.getString("FATHER_NAME"));
						buff.setFatherNationality(result.getString("FATHER_NATIONALITY"));
						buff.setFatherOccupation(result.getString("FATHER_OCCUPATION"));
						buff.setMotherName(result.getString("MOTHER_NAME"));
						buff.setMotherNationality(result.getString("MOTHER_NATIONALITY"));
						buff.setMotherOccupation(result.getString("MOTHER_OCCUPATION"));
						buff.setOrgPerson(buff.getMaCaNhan());
						buff.setPassportNo(result.getString("LIST_OF_PASSPORT_NO"));
						/*if (buff.getDataType().contains("HC")) {
							buff.setPassportNo(getPassportNo(conn, buff.getMaCaNhan()));
						}*/
//						try {
//							Blob blob = result.getBlob("PHOTO");
//							byte[] photo = blob
//									.getBytes(1, (int) blob.length());
//							buff.setPhoto(photo);
//						} catch (SQLException e) {
//							logger.error("get PHOTO error: " + e.getMessage());
//						}
						
						buff.setSrc("A08");
						buff.setCreateBy("SYSTEM");
						buff.setCreateDatetime(new Date());
						resultBuf.add(buff);
					}
				}
			}
		} catch (Exception e) {
			logger.error("GET DATA A08 FAIL: " + e.getMessage());
		}finally{
			if (conn != null) {
				conn.close();
			}
		}
		
		return resultBuf;
	}
}
