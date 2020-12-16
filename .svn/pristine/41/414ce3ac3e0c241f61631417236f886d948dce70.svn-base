package com.nec.asia.nic.comp.trans.dao.impl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.nec.asia.nic.comp.trans.dao.NicFPDataDao;
import com.nec.asia.nic.comp.trans.domain.BaseModelSingle;
import com.nec.asia.nic.comp.trans.domain.NicFPData;
import com.nec.asia.nic.comp.ws.log.domain.CreateMessageException;
import com.nec.asia.nic.framework.PaginatedResult;
import com.nec.asia.nic.framework.dao.impl.GenericHibernateDao;


@Repository("fpDataDao")
public class NicFPDataDaoImpl extends
GenericHibernateDao<NicFPData, String> implements NicFPDataDao{

	@Override
	public PaginatedResult<NicFPData> findAllForPagination(int currentPage,	int pageSize, String[] siteCodes, String officerId, Date fromDate,	Date toDate, Long[] fpPos, Long fromQuaScore, Long toQuaScore, Long fromVerficationScore, Long toVerficationScore) throws Exception {
		try{
		DetachedCriteria tCriteria = DetachedCriteria.forClass(NicFPData.class);
		
			if(officerId != null || fromDate != null || toDate != null ){
		
			
			
			if(siteCodes!=null){
				tCriteria.add(Restrictions.in("siteCode", siteCodes));
			}
			
			if(StringUtils.isNotEmpty(officerId)){
				tCriteria.add(Restrictions.like("createBy", officerId, MatchMode.ANYWHERE).ignoreCase());
			}
			
			if(fromDate!=null && toDate!=null){
				tCriteria.add(Restrictions.between("dateOfApplication", fromDate,toDate ));
			}
			else if(fromDate!=null){
				tCriteria.add(Restrictions.ge("dateOfApplication", fromDate));
			}
			else if(toDate!=null){
				tCriteria.add(Restrictions.le("dateOfApplication", toDate));
			}
			
			tCriteria.add(Restrictions.in("id.fpPosition", fpPos));
			
			if(fromQuaScore!=null && toQuaScore!=null){
				tCriteria.add(Restrictions.between("quality", fromQuaScore,toQuaScore ));
			}
			
			if(fromVerficationScore!=null && toVerficationScore!=null){
				tCriteria.add(Restrictions.between("verificationScore", fromVerficationScore,toVerficationScore ));
			}
		}
			return findAllForPagination(tCriteria, currentPage, pageSize);	
		}catch(Exception ex){
			logger.error("Error occurred while getting the NIC FP Data. Exception:"+ex.getMessage());
			throw new Exception("Error occurred while getting the NIC FP Data. Exception:"+ex.getMessage());
		}
	}

//	@Transactional(rollbackFor = java.lang.Throwable.class, propagation = Propagation.SUPPORTS)
//    @SuppressWarnings("unchecked")
	public List<Object[]> getFPQByFingerReportData(String[] siteCodes, Date fromDate, Date toDate) throws Exception {
		Session session  = null;
		try{
			session  = getHibernateTemplate().getSessionFactory().openSession();//this.getSession();//getHibernateTemplate().getSessionFactory().openSession();
			Query query = session.getNamedQuery("report.getFPByFingerReportData");
			query.setDate("fromDate", fromDate);
			query.setDate("toDate", toDate);
			query.setParameterList("siteCode", siteCodes);
			
			List<Object[]> results = query.list();

			return results;
			}catch(Exception ex){
				logger.error("Error occurred while getting the FPQ By Finger Report Data: Exception"+ex.getMessage());
				return null;
			}finally{
				session.close();
			}
	}

//	@Transactional(rollbackFor = java.lang.Throwable.class, propagation = Propagation.SUPPORTS)
//    @SuppressWarnings("unchecked")
	public List<Object[]> getFPQPerByFingerReportData(String[] siteCodes, Date fromDate, Date toDate) throws Exception {
		Session session  = null;
		try{
			session  = getHibernateTemplate().getSessionFactory().openSession();//this.getSession();//getHibernateTemplate().getSessionFactory().openSession();
			Query query = session.getNamedQuery("report.getFPQPerByFingerReportData");
			query.setDate("fromDate", fromDate);
			query.setDate("toDate", toDate);
			query.setParameterList("siteCode", siteCodes);
			
			List<Object[]> results = query.list();

			return results;
			}catch(Exception ex){
				logger.error("Error occurred while getting the FPQ By Finger Report Data: Exception"+ex.getMessage());
				return null;
			}finally{
				session.close();
			}
	}

	@Override
	public List<Object[]> getFPQByPopulationReportData(String siteCode, Date fromDate, Date toDate) throws Exception {
		Session session  = null;
		try{
			session  = getHibernateTemplate().getSessionFactory().openSession();//this.getSession();//getHibernateTemplate().getSessionFactory().openSession();
			Query query = session.getNamedQuery("report.getFPQByPopulationReportData");
			query.setDate("fromDate", fromDate);
			query.setDate("toDate", toDate);
			query.setParameter("siteCode", siteCode);
			
			List<Object[]> results = query.list();

			return results;
			}catch(Exception ex){
				logger.error("Error occurred while getting the FPQ By Finger Report Data: Exception"+ex.getMessage());
				return null;
			}finally{
				session.close();
			}
	}

	@Override
	public List<Object[]> getVerifyScoreSummaryCounts(Date fromDate, Date toDate, String[] sites) throws Exception {
		Session session  = null;
		try{
			session  = getHibernateTemplate().getSessionFactory().openSession();//this.getSession();//getHibernateTemplate().getSessionFactory().openSession();
			Query query = session.getNamedQuery("report.getVerifyScoreSummaryCounts");
			query.setDate("fromDate", fromDate);
			query.setDate("toDate", toDate);
			query.setParameterList("sites", sites);
			
			List<Object[]> results = query.list();

			return results;
			}catch(Exception ex){
				logger.error("Error occurred while getting the verification summary counts: Exception"+ex.getMessage());
				throw new Exception("Error occurred while getting the verification summary counts: Exception"+ex.getMessage());
			}finally{
				session.close();
		}
	}

	@Override
	public PaginatedResult<String> getTransIdsWithBelowVerifyScore( Date fromDate, Date toDate, int verifyScore, int fromRow, int toRow, String[] sites) throws Exception {
		Session session  = null;
		try{
			PaginatedResult<String> result = new PaginatedResult<String>();
			session  = getHibernateTemplate().getSessionFactory().openSession();//this.getSession();//getHibernateTemplate().getSessionFactory().openSession();
			Query query = session.getNamedQuery("report.getTransIdsWithBelowVerifyScoreList");
			query.setDate("fromDate", fromDate);
			query.setDate("toDate", toDate);
			query.setInteger("verifyScr", verifyScore);
			query.setInteger("fromRow", fromRow);
			query.setInteger("toRow", toRow);
			query.setParameterList("sites", sites);
			List<String> results = query.list();
			
			Query countQuery = session.getNamedQuery("report.getTransIdsWithBelowVerifyScoreCount");
			countQuery.setDate("fromDate", fromDate);
			countQuery.setDate("toDate", toDate);
			countQuery.setInteger("verifyScr", verifyScore);
			countQuery.setParameterList("sites", sites);
			
			int total = ((BigDecimal) (countQuery.uniqueResult())).intValue(); 
			
			result.setRows(results);
			result.setTotal(total);

			return result;
			}catch(Exception ex){
				logger.error("Error occurred while getting the verification summary counts: Exception"+ex.getMessage());
				throw new Exception("Error occurred while getting the verification summary counts: Exception"+ex.getMessage());
			}finally{
				session.close();
		}
	}

	@Override
	public PaginatedResult<String> getTransIdsWithAboveVerifyScore(Date fromDate, Date toDate, int verifyScore, int fromRow, int toRow, String[] sites) throws Exception {
		Session session  = null;
		try{
			PaginatedResult<String> result = new PaginatedResult<String>();
			session  = getHibernateTemplate().getSessionFactory().openSession();//this.getSession();//getHibernateTemplate().getSessionFactory().openSession();
			Query query = session.getNamedQuery("report.getTransIdsWithAboveVerifyScoreList");
			query.setDate("fromDate", fromDate);
			query.setDate("toDate", toDate);
			query.setInteger("verifyScr", verifyScore);
			query.setInteger("fromRow", fromRow);
			query.setInteger("toRow", toRow);
			query.setParameterList("sites", sites);
			List<String> results = query.list();
			
			Query countQuery = session.getNamedQuery("report.getTransIdsWithAboveVerifyScoreCount");
			countQuery.setDate("fromDate", fromDate);
			countQuery.setDate("toDate", toDate);
			countQuery.setInteger("verifyScr", verifyScore);
			countQuery.setParameterList("sites", sites);
			
			int total = ((BigDecimal) (countQuery.uniqueResult())).intValue(); 
			
			result.setRows(results);
			result.setTotal(total);

			return result;
			}catch(Exception ex){
				logger.error("Error occurred while getting the verification summary counts: Exception"+ex.getMessage());
				throw new Exception("Error occurred while getting the verification summary counts: Exception"+ex.getMessage());
			}finally{
				session.close();
		}
	}
	
	@Override
	public List<Object[]> getAvgFPScores(Date fromDate, Date toDate, String[] sites) throws Exception {
		Session session  = null;
		try{
			session  = getHibernateTemplate().getSessionFactory().openSession();//this.getSession();//getHibernateTemplate().getSessionFactory().openSession();
			Query query = session.getNamedQuery("report.getAvgFPScores");
			query.setDate("fromDate", fromDate);
			query.setDate("toDate", toDate);
			query.setParameterList("sites", sites);
			
			List<Object[]> results = query.list();

			return results;
			}catch(Exception ex){
				logger.error("Error occurred while getting the average fp score counts: Exception"+ex.getMessage());
				throw new Exception("Error occurred while getting the average fp score counts: Exception"+ex.getMessage());
			}finally{
				session.close();
		}
	}

	@Override
	public List<Object[]> getVerifyFailureRateRptData(Date fromDate, Date toDate, String[] sites) throws Exception {
		Session session  = null;
		try{
			session  = getHibernateTemplate().getSessionFactory().openSession();//this.getSession();//getHibernateTemplate().getSessionFactory().openSession();
			Query query = session.getNamedQuery("report.getverifyFailureRateRptData");
			query.setDate("fromDate", fromDate);
			query.setDate("toDate", toDate);
			query.setParameterList("sites", sites);
			
			List<Object[]> results = query.list();

			return results;
			}catch(Exception ex){
				logger.error("Error occurred while getting the verify failure rate report data: Exception"+ex.getMessage());
				throw new Exception("Error occurred while getting the verify failure rate report data: Exception"+ex.getMessage());
			}finally{
				session.close();
		}
	}

	@Override
	public List<Object[]> getLowVerifyCountByFP(Date fromDate, Date toDate, String[] sites) throws Exception {
		Session session  = null;
		try{
			session  = getHibernateTemplate().getSessionFactory().openSession();//this.getSession();//getHibernateTemplate().getSessionFactory().openSession();
			Query query = session.getNamedQuery("report.getLowVerifyCountByFP");
			query.setDate("fromDate", fromDate);
			query.setDate("toDate", toDate);
			query.setParameterList("sites", sites);
			
			List<Object[]> results = query.list();

			return results;
			}catch(Exception ex){
				logger.error("Error occurred while getting the low verify finger count: Exception"+ex.getMessage());
				return null;
			}finally{
				session.close();
		}
	}

	@Override
	public List<Object[]> getLowQualityCountByFP(Date fromDate, Date toDate, String[] sites) throws Exception {
		Session session  = null;
		try{
			session  = getHibernateTemplate().getSessionFactory().openSession();//this.getSession();//getHibernateTemplate().getSessionFactory().openSession();
			Query query = session.getNamedQuery("report.getLowQualityCountByFP");
			query.setDate("fromDate", fromDate);
			query.setDate("toDate", toDate);
			query.setParameterList("sites", sites);
			
			List<Object[]> results = query.list();

			return results;
			}catch(Exception ex){
				logger.error("Error occurred while getting the low quality finger count: Exception"+ex.getMessage());
				throw new Exception("Error occurred while getting the low quality finger count: Exception"+ex.getMessage());
			}finally{
				session.close();
		}
	}

	@Override
	public List<Object[]> getTotalCasesVerifiableInfo(Date fromDate, Date toDate, String[] sites) throws Exception {
		Session session  = null;
		try{
			session  = getHibernateTemplate().getSessionFactory().openSession();//this.getSession();//getHibernateTemplate().getSessionFactory().openSession();
			Query query = session.getNamedQuery("report.getTotalCasesVerifiableInfo");
			query.setDate("fromDate", fromDate);
			query.setDate("toDate", toDate);
			query.setParameterList("sites", sites);
			
			List<Object[]> results = query.list();

			return results;
			}catch(Exception ex){
				logger.error("Error occurred while getting the total cases verifiable count info: Exception"+ex.getMessage());
				throw new Exception("Error occurred while getting the total cases verifiable count info: Exception"+ex.getMessage());
			}finally{
				session.close();
		}
	}

	@Override
	public List<String> getAllTransIdsWithAboveVerifyScore(Date fromDate, Date toDate, int verifyScore, String[] sites) throws Exception {
		Session session  = null;
		try{
			PaginatedResult<String> result = new PaginatedResult<String>();
			session  = getHibernateTemplate().getSessionFactory().openSession();//this.getSession();//getHibernateTemplate().getSessionFactory().openSession();
			Query query = session.getNamedQuery("report.getAllTransIdsWithAboveVerifyScore");
			query.setDate("fromDate", fromDate);
			query.setDate("toDate", toDate);
			query.setInteger("verifyScr", verifyScore);
			query.setParameterList("sites", sites);
			List<String> results = query.list();

			return results;
			}catch(Exception ex){
				logger.error("Error occurred while getting the verification summary transaction ids: Exception"+ex.getMessage());
				return null;
			}finally{
				session.close();
		}
	}

	@Override
	public List<String> getAllTransIdsWithBelowVerifyScore(Date fromDate, Date toDate, int verifyScore, String[] sites) throws Exception {
		Session session  = null;
		try{
			PaginatedResult<String> result = new PaginatedResult<String>();
			session  = getHibernateTemplate().getSessionFactory().openSession();//this.getSession();//getHibernateTemplate().getSessionFactory().openSession();
			Query query = session.getNamedQuery("report.getAllTransIdsWithBelowVerifyScore");
			query.setDate("fromDate", fromDate);
			query.setDate("toDate", toDate);
			query.setInteger("verifyScr", verifyScore);
			query.setParameterList("sites", sites);
			List<String> results = query.list();

			return results;
			}catch(Exception ex){
				logger.error("Error occurred while getting the verification summary transaction ids: Exception"+ex.getMessage());
				return null;
			}finally{
				session.close();
		}
	}

	@Override
	public PaginatedResult<String> getTransIdsWithBelowQuaScore(Date fromDate, Date toDate, int quality, int fromRow, int toRow, String[] sites) throws Exception {
		Session session  = null;
		try{
			PaginatedResult<String> result = new PaginatedResult<String>();
			session  = getHibernateTemplate().getSessionFactory().openSession();//this.getSession();//getHibernateTemplate().getSessionFactory().openSession();
			Query query = session.getNamedQuery("report.getTransIdsWithBelowQuaScoreList");
			query.setDate("fromDate", fromDate);
			query.setDate("toDate", toDate);
			query.setInteger("quality", quality);
			query.setInteger("fromRow", fromRow);
			query.setInteger("toRow", toRow);
			query.setParameterList("sites", sites);
			List<String> results = query.list();
			
			Query countQuery = session.getNamedQuery("report.getTransIdsWithBelowQuaScoreCount");
			countQuery.setDate("fromDate", fromDate);
			countQuery.setDate("toDate", toDate);
			countQuery.setInteger("quality", quality);
			countQuery.setParameterList("sites", sites);
			
			int total = ((BigDecimal) (countQuery.uniqueResult())).intValue(); 
			
			result.setRows(results);
			result.setTotal(total);

			return result;
			}catch(Exception ex){
				logger.error("Error occurred while getting the quality score summary counts: Exception"+ex.getMessage());
				throw new Exception("Error occurred while getting the quality score summary counts: Exception"+ex.getMessage());
			}finally{
				session.close();
		}
	}

	@Override
	public PaginatedResult<String> getTransIdsWithAboveQualityScore(Date fromDate, Date toDate, int quality, int fromRow, int toRow, String[] sites) throws Exception {
		Session session  = null;
		try{
			PaginatedResult<String> result = new PaginatedResult<String>();
			session  = getHibernateTemplate().getSessionFactory().openSession();//this.getSession();//getHibernateTemplate().getSessionFactory().openSession();
			Query query = session.getNamedQuery("report.getTransIdsWithAboveQuaScoreList");
			query.setDate("fromDate", fromDate);
			query.setDate("toDate", toDate);
			query.setInteger("quality", quality);
			query.setInteger("fromRow", fromRow);
			query.setInteger("toRow", toRow);
			query.setParameterList("sites", sites);
			List<String> results = query.list();
			
			Query countQuery = session.getNamedQuery("report.getTransIdsWithAboveQuaScoreCount");
			countQuery.setDate("fromDate", fromDate);
			countQuery.setDate("toDate", toDate);
			countQuery.setInteger("quality", quality);
			countQuery.setParameterList("sites", sites);
			
			int total = ((BigDecimal) (countQuery.uniqueResult())).intValue(); 
			
			result.setRows(results);
			result.setTotal(total);

			return result;
			}catch(Exception ex){
				logger.error("Error occurred while getting the quality score summary counts: Exception"+ex.getMessage());
				throw new Exception("Error occurred while getting the quality score summary counts: Exception"+ex.getMessage());
			}finally{
				session.close();
		}
	}

	@Override
	public List<String> getAllTransIdsWithAboveQualityScore(Date fromDate, Date toDate, int quality, String[] sites) throws Exception {
		Session session  = null;
		try{
			PaginatedResult<String> result = new PaginatedResult<String>();
			session  = getHibernateTemplate().getSessionFactory().openSession();//this.getSession();//getHibernateTemplate().getSessionFactory().openSession();
			Query query = session.getNamedQuery("report.getAllTransIdsWithAboveQuaScore");
			query.setDate("fromDate", fromDate);
			query.setDate("toDate", toDate);
			query.setInteger("quality", quality);
			query.setParameterList("sites", sites);
			List<String> results = query.list();

			return results;
			}catch(Exception ex){
				logger.error("Error occurred while getting the Quality summary transaction ids: Exception"+ex.getMessage());
				return null;
			}finally{
				session.close();
		}
	}

	@Override
	public List<String> getAllTransIdsWithBelowQualityScore(Date fromDate, Date toDate, int quality, String[] sites) throws Exception {
		Session session  = null;
		try{
			PaginatedResult<String> result = new PaginatedResult<String>();
			session  = getHibernateTemplate().getSessionFactory().openSession();//this.getSession();//getHibernateTemplate().getSessionFactory().openSession();
			Query query = session.getNamedQuery("report.getAllTransIdsWithBelowQuaScore");
			query.setDate("fromDate", fromDate);
			query.setDate("toDate", toDate);
			query.setInteger("quality", quality);
			query.setParameterList("sites", sites);
			List<String> results = query.list();

			return results;
			}catch(Exception ex){
				logger.error("Error occurred while getting the Quality summary transaction ids: Exception"+ex.getMessage());
				return null;
			}finally{
				session.close();
		}
	}

	@Override
	public BaseModelSingle<Boolean> saveNicFPData(NicFPData fpData) {
		try {
			this.save(fpData);
			return new BaseModelSingle<Boolean>(true, true, null);
		} catch (Exception e) {
			return new BaseModelSingle<Boolean>(null, false, CreateMessageException.createMessageException(e) + " - saveNicFPData - " + fpData.getDocId() + " - thất bại.");
		}
	}
}
