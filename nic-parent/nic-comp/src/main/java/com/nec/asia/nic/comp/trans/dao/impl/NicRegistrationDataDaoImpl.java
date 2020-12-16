package com.nec.asia.nic.comp.trans.dao.impl;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;


import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.FetchMode;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Conjunction;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Disjunction;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Repository;

import com.nec.asia.nic.comp.trans.dao.NicRegistrationDataDao;
import com.nec.asia.nic.comp.trans.domain.BaseModelSingle;
import com.nec.asia.nic.comp.trans.domain.NicRegistrationData;
import com.nec.asia.nic.comp.trans.domain.NicTransaction;
import com.nec.asia.nic.comp.trans.dto.RICTxnRptDto;
import com.nec.asia.nic.comp.trans.dto.RICTxnUpldRptDto;
import com.nec.asia.nic.comp.ws.log.domain.CreateMessageException;
import com.nec.asia.nic.framework.PaginatedResult;
import com.nec.asia.nic.framework.dao.impl.GenericHibernateDao;
import com.nec.asia.nic.utils.HelperClass;

/* 
 * Modification History:
 *  
 * 
 * 08 Jan 2014 (Sailaja): Added findAllForPaginationByFilterOnReprint method for Re-print
 * 16 jan 2014 (priya): Added getRicUnCollectedCardStatusRptRecordList and changes for Ric Site code
 * 24 Jan 2014 (eunike): Changed query on getRicUnCollectedCardStatusRptRecordList (card status is null and dispatch id is not null)
 * 26 Jan 2014 (eunike): Changed query on getRicUnCollectedCardStatusRptRecordList (incl estcoldate)
 * 29 Jan 2014 (eunike): Changed query on getRicUnCollectedCardStatusRptRecordList (txn status=ric_card_received instead of card_status n dispatch_id) 
 * 10 Feb 2014 (priya): added getCardDeliveryStatusRptRecordList,getRicExprsPrintRptRecordList
 * 19 Mar 2014 (Swapna): Added findAllTransIdForPagination method to load only transaction ids, nins without loading remaining fields info.
 */
@Repository("registrationDataDao")
public class NicRegistrationDataDaoImpl extends
GenericHibernateDao<NicRegistrationData, String> implements NicRegistrationDataDao{

	
	
	/*public NicRegistrationData findById(String transactionId){
		//transactionId = "RICHQ-2013-000001";
		System.out.println("Transaction Id"+transactionId);
        System.out.println("Inside Dao Impl=========>>>>>>Display Photo");
        NicRegistrationData nicRegistrationData =new NicRegistrationData();
        NicTransaction nicTransaction = new NicTransaction();
        nicTransaction.getNin();
        nicRegistrationData.getFirstnameFull();
        nicRegistrationData.getSurnameBirthFull();
        nicRegistrationData.getGender();
        nicRegistrationData.getDateOfBirth();
		   DetachedCriteria subquery = DetachedCriteria.forClass(NicTransaction.class, "a");
		   subquery.createAlias("a.transactionId", "b2");
		   subquery.setProjection(Projections.property("b2.transactionId"));
		  
		  DetachedCriteria criteria1 = DetachedCriteria.forClass(NicRegistrationData.class, "b1");
		  criteria1.add(Subqueries.propertyIn("b1.transactionId", subquery));
		  
		  DetachedCriteria criteria = DetachedCriteria.forClass(NicTransaction.class);
	      criteria.setFetchMode("transactionId", FetchMode.JOIN);
	      criteria.createAlias("transactionId", "t");
	      
	      criteria.add(Restrictions.eq("t.transactionId", transactionId));
		   return (NicRegistrationData) this.getHibernateTemplate().findByCriteria(criteria);
        DetachedCriteria criteria = DetachedCriteria.forClass(NicRegistrationData.class);
        criteria.add(Restrictions.eq("transactionId", transactionId));
        criteria.setProjection(Projections.property("nin"));
        criteria.setProjection(Projections.property("firstnameFull"));
        criteria.setProjection(Projections.property("surnameBirthFull"));
        criteria.setProjection(Projections.property("gender"));
        criteria.setProjection(Projections.property("dateOfBirth"));
       // criteria.setProjection(Projections.property(""));
		   return (NicRegistrationData) this.getHibernateTemplate().findByCriteria(criteria);
	}
*/


	
	
	@Override
	public NicTransaction getTransactionRegistrationData(NicTransaction transObj){
		
		DetachedCriteria detachedCriteria = DetachedCriteria.forClass(getPersistentClass());
		
		if (StringUtils.isNotBlank(transObj.getTransactionId())) {
			detachedCriteria.add(Restrictions.eq("transactionId", transObj.getTransactionId()))
			.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		}
		
		List<NicRegistrationData> resultReg = this.findAll(detachedCriteria);
		
		if(resultReg.size() > 0){
			transObj.setNicRegistrationData(resultReg.get(0));
		}
		return transObj;
	}
	
	public Long getNicIdByTransactionId(String transactionId) {
		Long nicId = null;
		
		String hql = "select a.nicId from NicRegistrationData a where a.transactionId = ? ";
		Object[] parameters = { transactionId } ;
		List<String> resultList = (List<String>) this.getHibernateTemplate().find(hql, parameters);
		if (CollectionUtils.isNotEmpty(resultList)) {
			nicId = Long.parseLong(resultList.get(0));
		}
		return nicId;
	}

	@Override
	public PaginatedResult<NicRegistrationData> findAllForPagination(NicRegistrationData registrationData, int pageNo, int pageSize) {
//		Session session = null;
//		try {
//			session = this.openSession();
//			FullTextSession fullTextSession = Search.getFullTextSession(session);
//
//			QueryBuilder queryBuilder = fullTextSession.getSearchFactory().buildQueryBuilder().forEntity(NicRegistrationData.class).get();
//			org.apache.lucene.search.Query query = null;
//			if (StringUtils.isNotBlank(registrationData.getFirstnameFull())) {
//				query = queryBuilder.keyword().onFields("firstnameFull").matching(registrationData.getFirstnameFull()).createQuery();
//			}
//			
//			if (StringUtils.isNotBlank(registrationData.getSurnameFull())) {
//				query = queryBuilder.keyword().onFields("surnameFull").matching(registrationData.getSurnameFull()).createQuery();
//			}
//			if (StringUtils.isNotBlank(registrationData.getMiddlenameFull())) {
//				query = queryBuilder.keyword().onFields("middlenameFull").matching(registrationData.getMiddlenameFull()).createQuery();
//			}
//
//			org.hibernate.search.FullTextQuery fullTextQuery = fullTextSession.createFullTextQuery(query, NicRegistrationData.class);
//
//			int total = fullTextQuery.getResultSize();
//
//			int maxNoPage = 1;
//			if (total > pageSize) {
//				maxNoPage = (int) Math.ceil(((double) total / pageSize));
//			}
//			int firstResults = (pageNo - 1) < 0 ? 0 : (pageNo - 1) * pageSize;
//			int maxResults = pageSize;
//			if (pageNo == maxNoPage) {
//				maxResults = total % pageSize;
//				if (maxResults == 0) {
//					maxResults = pageSize;
//				}
//			}
//
//			fullTextQuery.setFirstResult(firstResults);
//			fullTextQuery.setMaxResults(maxResults);
//			List<NicRegistrationData> registrationDataList = fullTextQuery.list();
//
//			fullTextSession.close();
//
//			return new PaginatedResult<NicRegistrationData>(total, pageNo, registrationDataList);
//
//		} catch (Exception e) {
//			if (session.isConnected())
//				session.close();
//			e.printStackTrace();
//		}
		return null;
	}

	
	@Override
	public PaginatedResult<NicRegistrationData> findAllForPaginationByFilter(
			NicRegistrationData nicRegistration, int pageNo, int pageSize, boolean ignoreCase,
			boolean enableLike, Order order) {
		DetachedCriteria rCriteria = DetachedCriteria.forClass(NicRegistrationData.class);
		if(StringUtils.isNotEmpty(nicRegistration.getTransactionId())){
			rCriteria.add(Restrictions.eq("transactionId", nicRegistration.getTransactionId()));
		}
		if (StringUtils.isNotEmpty(nicRegistration.getSearchName())) {
			rCriteria.add(Restrictions.eq("searchName", nicRegistration.getSearchName()));
		}

//		if (StringUtils.isNotEmpty(nicRegistration.getSurnameFull())) {
//			if (nicRegistration.getSurnameFull().contains("*")) {
//				rCriteria.add(Restrictions.ilike("surnameFull", nicRegistration.getSurnameFull().replace("*", "").trim(), MatchMode.ANYWHERE));
//			} else {
//				rCriteria.add(Restrictions.eq("surnameFull", nicRegistration.getSurnameFull()));
//			}
//		}
//
//		if (StringUtils.isNotEmpty(nicRegistration.getFirstnameFull())) {
//			if (nicRegistration.getFirstnameFull().contains("*")) {
//				rCriteria.add(Restrictions.ilike("firstnameFull", nicRegistration.getFirstnameFull().replace("*", "").trim(), MatchMode.ANYWHERE));
//			} else {
//				rCriteria.add(Restrictions.eq("firstnameFull", nicRegistration.getFirstnameFull()));
//			}
//		}
//
//		if (StringUtils.isNotEmpty(nicRegistration.getMiddlenameFull())) {
//			if (nicRegistration.getMiddlenameFull().contains("*")) {
//				rCriteria.add(Restrictions.ilike("middlenameFull", nicRegistration.getMiddlenameFull().replace("*", "").trim(), MatchMode.ANYWHERE));
//			} else {
//				rCriteria.add(Restrictions.eq("middlenameFull", nicRegistration.getMiddlenameFull()));
//			}
//		}

		if (StringUtils.isNotEmpty(nicRegistration.getGender())) {
			rCriteria.add(Restrictions.eq("gender", nicRegistration.getGender()));
		}
		if (nicRegistration.getDateOfBirth() != null) {
			rCriteria.add(Restrictions.eq("dateOfBirth", nicRegistration.getDateOfBirth()));
		}
		return findAllForPagination(rCriteria, pageNo, pageSize, order);
	}


	@Override
	public PaginatedResult<NicRegistrationData> findAllForPaginationByFilter(NicRegistrationData nicRegistration, 
			Date dateFrom, Date dateTo, int pageNo, int pageSize, boolean ignoreCase, boolean enableLike, Order order) {
		DetachedCriteria rCriteria = DetachedCriteria.forClass(NicRegistrationData.class);
//		if(StringUtils.isNotEmpty(nicRegistration.getNin())){
//			rCriteria.add(Restrictions.like("nin", nicRegistration.getNin(), MatchMode.ANYWHERE).ignoreCase());
//		}
		//if(StringUtils.isNotEmpty(nicRegistration.getTransactionId())){
		//	rCriteria.add(Restrictions.ilike("transactionId", nicRegistration.getTransactionId().toLowerCase()));
		//}
		if(StringUtils.isNotEmpty(nicRegistration.getSurnameFull())){
			rCriteria.add(Restrictions.like("surnameFull", nicRegistration.getSurnameFull(), MatchMode.ANYWHERE).ignoreCase());
		}
//		if(StringUtils.isNotEmpty(nicRegistration.getSurnameBirthFull())){
//			rCriteria.add(Restrictions.like("surnameBirthFull", nicRegistration.getSurnameBirthFull(), MatchMode.ANYWHERE).ignoreCase());
//		}
//		if(StringUtils.isNotEmpty(nicRegistration.getOptionSurname())){
//			rCriteria.add(Restrictions.like("optionSurname", nicRegistration.getOptionSurname(), MatchMode.ANYWHERE).ignoreCase());
//		}
		if(StringUtils.isNotEmpty(nicRegistration.getGender())){
			rCriteria.add(Restrictions.eq("gender", nicRegistration.getGender()));
		}
		if(nicRegistration.getDateOfBirth() != null){
			rCriteria.add(Restrictions.eq("dateOfBirth", nicRegistration.getDateOfBirth()));
		}
		
//		NicTransaction nicTransaction = nicRegistration.getNicTransaction();
//		if(nicTransaction != null || dateFrom != null || dateTo != null){
//			rCriteria.createAlias("nicTransaction", "transaction");
//			if(nicTransaction != null) {
//				if(StringUtils.isNotEmpty(nicTransaction.getNin())){
//					rCriteria.add(Restrictions.eq("transaction.nin", nicTransaction.getNin()));
//				}
//				if(StringUtils.isNotEmpty(nicTransaction.getRegSiteCode())){
//					rCriteria.add(Restrictions.eq("transaction.regSiteCode", nicTransaction.getRegSiteCode()));
//				}
//				if(StringUtils.isNotEmpty(nicTransaction.getTransactionStatus())){
//					rCriteria.add(Restrictions.eq("transaction.transactionStatus", nicTransaction.getTransactionStatus()));
//				}
//			}
//			
//			if(dateTo != null){
//				dateTo = DateUtil.getEndOfDay(dateTo);
//			}
//			
//			if(dateFrom != null && dateTo != null){
//				rCriteria.add(Restrictions.between("transaction.dateOfApplication", dateFrom, dateTo));
//			} else if(dateFrom != null) {
//				rCriteria.add(Restrictions.gt("transaction.dateOfApplication", dateFrom));
//			} else if(dateTo != null) {
//				rCriteria.add(Restrictions.lt("transaction.dateOfApplication", dateTo));
//			}
//		}

		return findAllForPagination(rCriteria, pageNo, pageSize, order);
	}

	@Override
	public PaginatedResult<NicRegistrationData> findAllForPagination(int currentPage, int pageSize, Order hibernateOrder, String siteCode, String officerId, Date fromDate, Date toDate) throws Exception {
//		
		try{
//			if(StringUtils.isNotEmpty(officerId)){
//				rCriteria = DetachedCriteria.forClass(NicRegistrationData.class);
//				rCriteria.add(Restrictions.ilike("regOfficerId", officerId, MatchMode.ANYWHERE));
//			}
//			if(fromDate!=null && toDate!=null){
//				if(rCriteria == null) DetachedCriteria.forClass(NicRegistrationData.class);
//				rCriteria.add(Restrictions.between("createDate", fromDate,toDate ));
//			}
//			else if(fromDate!=null){
//				if(rCriteria == null) DetachedCriteria.forClass(NicRegistrationData.class);
//				rCriteria.add(Restrictions.ge("createDate", fromDate));
//			}
//			else if(toDate!=null){
//				if(rCriteria == null) DetachedCriteria.forClass(NicRegistrationData.class);
//				rCriteria.add(Restrictions.le("createDate", toDate));
//			}
//			
			
		
		DetachedCriteria tCriteria = DetachedCriteria.forClass(NicRegistrationData.class);
		
		tCriteria.createAlias("nicTransaction", "nicTransaction");
		
		if(StringUtils.isNotBlank(siteCode)){
			tCriteria.add(Restrictions.like("nicTransaction.regSiteCode",siteCode, MatchMode.ANYWHERE).ignoreCase());
		}
		
		if(officerId != null || fromDate != null || toDate != null ){
		
			
			
			if(StringUtils.isNotEmpty(officerId)){
				tCriteria.add(Restrictions.like("regOfficerId", officerId, MatchMode.ANYWHERE).ignoreCase());
			}
			
			if(fromDate!=null && toDate!=null){
				tCriteria.add(Restrictions.between("createDate", fromDate,toDate ));
			}
			else if(fromDate!=null){
				tCriteria.add(Restrictions.ge("createDate", fromDate));
			}
			else if(toDate!=null){
				tCriteria.add(Restrictions.le("createDate", toDate));
			}
		}
			
			return findAllForPagination(tCriteria, currentPage, pageSize, hibernateOrder);
		}catch(Exception ex){
			logger.error("Error occurred while getting the search result list. Exception:"+ex.getMessage());
			throw new Exception("Error occurred while getting the search result list. Exception:"+ex.getMessage());
		}
		
	}
	
	@Override
	public PaginatedResult<NicRegistrationData> getRicTxnRptRecordList(
			RICTxnRptDto ricTxnRptcriteria,int page, int pageSize) {  
		DetachedCriteria criteria = DetachedCriteria
				.forClass(NicRegistrationData.class);
		criteria.setFetchMode("nicTransaction", FetchMode.JOIN);
		criteria.createAlias("nicTransaction", "t");
		if (ricTxnRptcriteria.getTxnStartDate() != null) { 
			criteria.add(Restrictions.ge("t.dateOfApplication",ricTxnRptcriteria.getTxnStartDate()));
		}
		if (ricTxnRptcriteria.getTxnEndDate() != null) {
		 
			criteria.add(Restrictions.le("t.dateOfApplication", ricTxnRptcriteria.getTxnEndDate()));
		}
		if (ricTxnRptcriteria.getTxnType() != null && !"ALL".equals(ricTxnRptcriteria.getTxnType())) { 
			criteria.add(Restrictions.eq("t.transactionType", ricTxnRptcriteria.getTxnType()));
		}
		if (ricTxnRptcriteria.getTxnSubType() != null && !"ALL".equals(ricTxnRptcriteria.getTxnSubType())) { 
			criteria.add(Restrictions.eq("t.transactionSubtype", ricTxnRptcriteria.getTxnSubType()));
		}
		if (ricTxnRptcriteria.getTxnStatus() != null && !"ALL".equals(ricTxnRptcriteria.getTxnStatus())) { 
			criteria.add(Restrictions.eq("t.transactionStatus", ricTxnRptcriteria.getTxnStatus()));
		}  
		if (ricTxnRptcriteria.getRicOffice() != null
				&& "ALL".equals(ricTxnRptcriteria.getRicOffice())) {
			criteria.add(Restrictions.like("t.regSiteCode", "RIC",
					MatchMode.START).ignoreCase());
		} else if (ricTxnRptcriteria.getRicOffice() != null
				&& !"ALL".equals(ricTxnRptcriteria.getRicOffice())) {
			criteria.add(Restrictions.eq("t.regSiteCode",
					ricTxnRptcriteria.getRicOffice()));
		}
		if (ricTxnRptcriteria.getEstColStartDate() != null) { 
			criteria.add(Restrictions.ge("estCollectionDate",ricTxnRptcriteria.getEstColStartDate()));
		}
		if (ricTxnRptcriteria.getEstColEndDate() != null) { 
			criteria.add(Restrictions.le("estCollectionDate", ricTxnRptcriteria.getEstColEndDate()));
		}
		if(ricTxnRptcriteria.getGender()!=null && !"ALL".equals(ricTxnRptcriteria.getGender())){
			criteria.add(Restrictions.eq("gender", ricTxnRptcriteria.getGender()));
		}
		Order orders = Order.asc("t.applnRefNo"); 
		PaginatedResult<NicRegistrationData> list = this.findAllForPagination(criteria, page, pageSize, orders);  
		return list; 
	}

	@Override
	public List getAllRegistrations(Date fromDate, Date toDate, String[] siteCodes) throws Exception {
		try{
		DetachedCriteria tCriteria = DetachedCriteria.forClass(NicRegistrationData.class);
		
		tCriteria.createAlias("nicTransaction", "nicTransaction");
		
		if(siteCodes!=null && siteCodes.length>0){
			tCriteria.add(Restrictions.in("nicTransaction.regSiteCode",siteCodes));
		}
		
		if(fromDate != null || toDate != null ){
			
			if(fromDate!=null && toDate!=null){
				tCriteria.add(Restrictions.between("nicTransaction.dateOfApplication", fromDate,toDate ));
			}
			else if(fromDate!=null){
				tCriteria.add(Restrictions.ge("nicTransaction.dateOfApplication", fromDate));
			}
			else if(toDate!=null){
				tCriteria.add(Restrictions.le("nicTransaction.dateOfApplication", toDate));
			}
		}
		
		tCriteria.setProjection(Projections.projectionList()
			    .add(Projections.property("fpQuality"), "fpQuality"))
				.setResultTransformer(Transformers.aliasToBean(NicRegistrationData.class));
			
			return findAll(tCriteria);
		}catch(Exception ex){
			logger.error("Error occurred while getting the search result list. Exception:"+ex.getMessage());
			return null;
		}
	}

	@Override
	public List<NicRegistrationData> getRegistrations(List<String> transIds) throws Exception {
		try{
			DetachedCriteria tCriteria = DetachedCriteria.forClass(NicRegistrationData.class);
			
//			tCriteria.add(Restrictions.in("transactionId", transIds));
			Conjunction conjunction=Restrictions.conjunction();
			if(transIds.size()>0){
				Disjunction disjunctionIdListCount = Restrictions.disjunction();
				
				for(int i=0;i<(((transIds.size()/1000)+1)); i++){
					Criterion list=null;
					if(transIds.size()>((i+1)*1000)){
						list = Restrictions.in("transactionId",Arrays.copyOfRange(transIds.toArray(), (i*1000), ((i+1)*1000)));
					}else{
						list = Restrictions.in("transactionId",Arrays.copyOfRange(transIds.toArray(), (i*1000), transIds.size()));
					}
					
					disjunctionIdListCount.add(list);
				}
				
				conjunction.add(disjunctionIdListCount);
			}
			tCriteria.add(conjunction);
			return findAll(tCriteria);
			
		}catch(Exception ex){
			logger.error("Error occurred while getting the search result list. Exception:"+ex.getMessage());
			throw new Exception("Error occurred while getting the search result list. Exception:"+ex.getMessage());
		}
	}

	@Override
	public PaginatedResult<NicRegistrationData> getRicTxnUploadRptRecordList(
			RICTxnUpldRptDto ricTxnUpldRptcriteria, int page, int pageSize) {
		DetachedCriteria criteria = DetachedCriteria
				.forClass(NicRegistrationData.class);
		criteria.setFetchMode("nicTransaction", FetchMode.JOIN);
		criteria.createAlias("nicTransaction", "t");
		if (ricTxnUpldRptcriteria.getTxnStartDate() != null) { 
			criteria.add(Restrictions.ge("t.dateOfApplication",ricTxnUpldRptcriteria.getTxnStartDate()));
		}
		if (ricTxnUpldRptcriteria.getTxnEndDate() != null) {
		 
			criteria.add(Restrictions.le("t.dateOfApplication", ricTxnUpldRptcriteria.getTxnEndDate()));
		}
		if (ricTxnUpldRptcriteria.getTxnType() != null && !"ALL".equals(ricTxnUpldRptcriteria.getTxnType())) { 
			criteria.add(Restrictions.eq("t.transactionType", ricTxnUpldRptcriteria.getTxnType()));
		}
		if (ricTxnUpldRptcriteria.getTxnSubType() != null && !"ALL".equals(ricTxnUpldRptcriteria.getTxnSubType())) { 
			criteria.add(Restrictions.eq("t.transactionSubtype", ricTxnUpldRptcriteria.getTxnSubType()));
		} 
		  
		if (ricTxnUpldRptcriteria.getRicOffice() != null
				&& "ALL".equals(ricTxnUpldRptcriteria.getRicOffice())) {
			criteria.add(Restrictions.like("t.regSiteCode", "RIC",
					MatchMode.START).ignoreCase());
		} else if (ricTxnUpldRptcriteria.getRicOffice() != null
				&& !"ALL".equals(ricTxnUpldRptcriteria.getRicOffice())) {
			criteria.add(Restrictions.eq("t.regSiteCode",
					ricTxnUpldRptcriteria.getRicOffice()));
		}
		criteria.add(Restrictions.eq("uploadFlag", Boolean.TRUE));
		Order orders = Order.asc("t.applnRefNo"); 
		PaginatedResult<NicRegistrationData> list = this.findAllForPagination(criteria, page, pageSize, orders);  
		//System.out.println("Result in Dao >>"+list.getTotal());
		return list; 
	}

	@Override
	public List getRegistrationsInfoBySite(Date fromDate,	Date toDate, String siteCode) throws Exception {
		Session session  = null;
		try{
			session  = getHibernateTemplate().getSessionFactory().openSession();
			
			Criteria tCriteria = session.createCriteria(NicRegistrationData.class);
			tCriteria.createAlias("nicTransaction", "nicTransaction");
			
			if(StringUtils.isNotBlank(siteCode)){
				tCriteria.add(Restrictions.eq("nicTransaction.regSiteCode",siteCode).ignoreCase());
			}
			
			if(fromDate != null || toDate != null ){
				
				if(fromDate!=null && toDate!=null){
					tCriteria.add(Restrictions.between("nicTransaction.dateOfApplication", fromDate,toDate ));
				}
				else if(fromDate!=null){
					tCriteria.add(Restrictions.ge("nicTransaction.dateOfApplication", fromDate));
				}
				else if(toDate!=null){
					tCriteria.add(Restrictions.le("nicTransaction.dateOfApplication", toDate));
				}
			}
			
			tCriteria.setProjection(Projections.projectionList()
				    .add(Projections.property("dateOfBirth"), "dateOfBirth")
				    .add(Projections.property("fpQuality"), "fpQuality"))
					.setResultTransformer(Transformers.aliasToBean(NicRegistrationData.class));
			
				return tCriteria.list();
			}catch(Exception ex){
				logger.error("Error occurred while getting the search result list. Exception:"+ex.getMessage());
				throw new Exception("Error occurred while getting the search result list. Exception:"+ex.getMessage());
			}finally{
				session.close();
		}
	}

	@Override
	public List<NicRegistrationData> getFPEncodeValues(Date fromDate, Date toDate, String[] sites) throws Exception {
		Session session  = null;
		try{
			session  = getHibernateTemplate().getSessionFactory().openSession();
			
			Criteria tCriteria = session.createCriteria(NicRegistrationData.class);
			tCriteria.createAlias("nicTransaction", "nicTransaction");
			
			if(fromDate != null || toDate != null ){
				
				if(fromDate!=null && toDate!=null){
					tCriteria.add(Restrictions.between("nicTransaction.dateOfApplication", fromDate,toDate ));
				}
				else if(fromDate!=null){
					tCriteria.add(Restrictions.ge("nicTransaction.dateOfApplication", fromDate));
				}
				else if(toDate!=null){
					tCriteria.add(Restrictions.le("nicTransaction.dateOfApplication", toDate));
				}
			}
			
			tCriteria.add(Restrictions.in("nicTransaction.regSiteCode", sites));
			
			tCriteria.setProjection(Projections.projectionList()
				    .add(Projections.property("fpEncode"), "fpEncode")
				    .add(Projections.property("fpQuality"), "fpQuality")
				    .add(Projections.property("transactionId"), "transactionId"))
					.setResultTransformer(Transformers.aliasToBean(NicRegistrationData.class));
			
				return tCriteria.list();
			}catch(Exception ex){
				logger.error("Error occurred while getting the fingerprint encode values. Exception:"+ex.getMessage());
				throw new Exception("Error occurred while getting the fingerprint encode values. Exception:"+ex.getMessage());
			}finally{
				session.close();
		}
	}
	
	@Override
	public PaginatedResult<NicRegistrationData> findAllForPaginationByFilterOnReprint(
			NicRegistrationData nicRegistrationData, Date from, Date to,
			int pageNo, int pageSize, boolean ignoreCase, boolean enableLike,
			Order order) {
		DetachedCriteria rCriteria = DetachedCriteria.forClass(NicRegistrationData.class);
//		if(StringUtils.isNotEmpty(nicRegistrationData.getNin())){
//			rCriteria.add(Restrictions.like("nin", nicRegistrationData.getNin(), MatchMode.ANYWHERE).ignoreCase());
//		}
		if(StringUtils.isNotEmpty(nicRegistrationData.getTransactionId())){
			rCriteria.add(Restrictions.ilike("transactionId", nicRegistrationData.getTransactionId().toLowerCase()));
		}
		return findAllForPagination(rCriteria, pageNo, pageSize, order);
	}


	@Override
	public List<Object[]> getTotalCardsIssuedBySite(Date fromDate, Date toDate, String[] sites) throws Exception {
		Session session  = null;
		try{
			session  = getHibernateTemplate().getSessionFactory().openSession();//this.getSession();//getHibernateTemplate().getSessionFactory().openSession();
			Query query = session.getNamedQuery("report.getTotalCardsIssuedBySite");
			query.setDate("fromDate", fromDate);
			query.setDate("toDate", toDate);
			query.setParameterList("sites", sites);
			
			List<Object[]> results = query.list();

			return results;
			}catch(Exception ex){
				logger.error("Error occurred while getting the total cards issued by site ino: Exception"+ex.getMessage());
				throw new Exception("Error occurred while getting the total cards issued by site ino: Exception"+ex.getMessage());
			}finally{
				session.close();
		}
	}

	@Override
	public PaginatedResult<NicRegistrationData> getRicUnCollectedCardStatusRptRecordList(
			RICTxnRptDto unColCardStatuSrchCriteria, int page, int pageSize) throws Exception{
		
		DetachedCriteria regDataCriteria = DetachedCriteria.forClass(NicRegistrationData.class);
		regDataCriteria.setFetchMode("nicTransaction", FetchMode.JOIN);
		regDataCriteria.createAlias("nicTransaction", "t");
		
		regDataCriteria.add(Restrictions.isNotNull("estCollectionDate"));
		regDataCriteria.add(Restrictions.eq("t.transactionStatus", "RIC_CARD_RECEIVED"));
		if (unColCardStatuSrchCriteria.getTxnStartDate() != null) { 
			regDataCriteria.add(Restrictions.ge("t.dateOfApplication",unColCardStatuSrchCriteria.getTxnStartDate()));
		}
		if (unColCardStatuSrchCriteria.getTxnEndDate() != null) {
			regDataCriteria.add(Restrictions.le("t.dateOfApplication", unColCardStatuSrchCriteria.getTxnEndDate()));
		} 
		if (unColCardStatuSrchCriteria.getRicOffice()==null) {
			regDataCriteria.add(Restrictions.like("t.regSiteCode", "RIC",MatchMode.START).ignoreCase());
		}else{
			regDataCriteria.add(Restrictions.le("t.regSiteCode", unColCardStatuSrchCriteria.getRicOffice()));	
		}
		
		if (unColCardStatuSrchCriteria.getNoOfDays() != null) {
			regDataCriteria.add(Restrictions.sqlRestriction("TRUNC(SYSDATE)-TRUNC(EST_COLLECTION_DATE) >'"+unColCardStatuSrchCriteria.getNoOfDays()+"'"));
		}
		if (unColCardStatuSrchCriteria.getEstColStartDate() != null) { 
			regDataCriteria.add(Restrictions.ge("estCollectionDate",unColCardStatuSrchCriteria.getEstColStartDate()));
		}
		if (unColCardStatuSrchCriteria.getEstColEndDate() != null) {
			regDataCriteria.add(Restrictions.le("estCollectionDate", unColCardStatuSrchCriteria.getEstColEndDate()));
		} 
		
		
		
		Order orders = Order.asc("t.issSiteCode");
		PaginatedResult<NicRegistrationData> list = this.findAllForPagination(regDataCriteria, page, pageSize, orders, Order.desc("estCollectionDate"));  
		System.out.println("Result in Dao >>"+list.getTotal());
		return list;
	}

	@Override
	public PaginatedResult<NicRegistrationData> getRicExprsPrintRptRecordList(
			RICTxnRptDto exprsPrintRptSrchCriteria, int page, int pageSize) {  
		PaginatedResult<NicRegistrationData> list = null;
		DetachedCriteria criteria = DetachedCriteria
				.forClass(NicRegistrationData.class);
		criteria.setFetchMode("nicTransaction", FetchMode.JOIN);
		criteria.createAlias("nicTransaction", "t");
		if (exprsPrintRptSrchCriteria.getTxnStartDate() != null) { 
			criteria.add(Restrictions.ge("t.dateOfApplication",exprsPrintRptSrchCriteria.getTxnStartDate()));
		}
		if (exprsPrintRptSrchCriteria.getTxnEndDate() != null) {
		 
			criteria.add(Restrictions.le("t.dateOfApplication", exprsPrintRptSrchCriteria.getTxnEndDate()));
		}
		if (exprsPrintRptSrchCriteria.getTxnType() != null && !"ALL".equals(exprsPrintRptSrchCriteria.getTxnType())) { 
			criteria.add(Restrictions.eq("t.transactionType", exprsPrintRptSrchCriteria.getTxnType()));
		}
		if (exprsPrintRptSrchCriteria.getTxnSubType() != null && !"ALL".equals(exprsPrintRptSrchCriteria.getTxnSubType())) { 
			criteria.add(Restrictions.eq("t.transactionSubtype", exprsPrintRptSrchCriteria.getTxnSubType()));
		}
		if (exprsPrintRptSrchCriteria.getTxnStatus() != null && !"ALL".equals(exprsPrintRptSrchCriteria.getTxnStatus())) { 
			criteria.add(Restrictions.eq("t.transactionStatus", exprsPrintRptSrchCriteria.getTxnStatus()));
		}  
		if (exprsPrintRptSrchCriteria.getRicOffice() != null
				&& "ALL".equals(exprsPrintRptSrchCriteria.getRicOffice())) {
			criteria.add(Restrictions.like("t.regSiteCode", "RIC",
					MatchMode.START).ignoreCase());
		} else if (exprsPrintRptSrchCriteria.getRicOffice() != null
				&& !"ALL".equals(exprsPrintRptSrchCriteria.getRicOffice())) {
			criteria.add(Restrictions.eq("t.regSiteCode",
					exprsPrintRptSrchCriteria.getRicOffice()));
		}
		if (exprsPrintRptSrchCriteria.getEstColStartDate() != null) { 
			criteria.add(Restrictions.ge("estCollectionDate",exprsPrintRptSrchCriteria.getEstColStartDate()));
		}
		if (exprsPrintRptSrchCriteria.getEstColEndDate() != null) { 
			criteria.add(Restrictions.le("estCollectionDate", exprsPrintRptSrchCriteria.getEstColEndDate()));
		}
		if(exprsPrintRptSrchCriteria.getGender()!=null && !"ALL".equals(exprsPrintRptSrchCriteria.getGender())){
			criteria.add(Restrictions.eq("gender", exprsPrintRptSrchCriteria.getGender()));
		}
		criteria.add(Restrictions.eq("expressFlag", Boolean.TRUE)); 
		Order orders = Order.asc("t.applnRefNo");  
		list = this.findAllForPagination(criteria, page, pageSize, orders);  
		return list; 
	}

	@Override
	public PaginatedResult<NicRegistrationData> getCardDeliveryStatusRptRecordList(
			RICTxnRptDto cardDelStatuSrchCriteria, int page, int pageSize)throws Exception {
		PaginatedResult<NicRegistrationData> list = null;
		DetachedCriteria criteria = DetachedCriteria
				.forClass(NicRegistrationData.class);
		criteria.setFetchMode("nicTransaction", FetchMode.JOIN);
		criteria.createAlias("nicTransaction", "t"); 
		if (cardDelStatuSrchCriteria.getEstColStartDate() != null) {
			criteria.add(Restrictions.ge("estCollectionDate", cardDelStatuSrchCriteria.getEstColStartDate()));
		}
		if (cardDelStatuSrchCriteria.getEstColEndDate() != null) {
			criteria.add(Restrictions.le("estCollectionDate", cardDelStatuSrchCriteria.getEstColEndDate()));
		}
		if (cardDelStatuSrchCriteria.getRicOffice() != null
				&& "ALL".equals(cardDelStatuSrchCriteria.getRicOffice())) {
			criteria.add(Restrictions.like("t.regSiteCode", "RIC",
					MatchMode.START).ignoreCase());
		} else if (cardDelStatuSrchCriteria.getRicOffice() != null
				&& !"ALL".equals(cardDelStatuSrchCriteria.getRicOffice())) {
			criteria.add(Restrictions.eq("t.regSiteCode",
					cardDelStatuSrchCriteria.getRicOffice()));
		}
		criteria.add(Restrictions.isNotNull("estCollectionDate")); 
		criteria.add(Restrictions.ne("t.transactionType", "PAR_UPD"));
		criteria.add(Restrictions.ne("t.transactionType", "CON"));
		/*criteria.add(Restrictions.eq("t.transactionStatus", "NIC_TX_COMPLETD"));
		criteria.add(Restrictions.eq("t.transactionStatus", "PERSO_PERSONALIZED"));
		criteria.add(Restrictions.eq("t.transactionStatus", "NIC_SCREENING"));*/
		criteria.add(Restrictions.in("t.transactionStatus", Arrays.asList("NIC_TX_COMPLETD","PERSO_PERSONALIZED","RIC_UPLOADED","NIC_SCREENING")));
		/*criteria.add(Restrictions.and(Restrictions.like("t.transactionStatus", "NIC_TX_COMPLETD",MatchMode.START),
				(Restrictions.like("t.transactionStatus", "RIC_UPLOAD",MatchMode.START))));
		criteria.add(Restrictions.and(Restrictions.like("t.transactionStatus", "PERSO_PERSONALIZED",MatchMode.START),
				(Restrictions.like("t.transactionStatus", "NIC_SCREENING",MatchMode.START))));*/
		list = this.findAllForPagination(criteria, page, pageSize, Order.asc("t.applnRefNo"));  
		logger.debug("result in DAO >> "+list.getTotal());
		return list;
	}

	@Override
	public PaginatedResult<NicRegistrationData> findAllTransIdForPagination(int currentPage, int pageSize, Order hibernateOrder, String[] siteCodes, String officerId, Date fromDate, Date toDate) throws Exception {
		Session session  = null;
		try{
			session  = getHibernateTemplate().getSessionFactory().openSession();
			
			Criteria c = session.createCriteria(NicRegistrationData.class);
			
			
			c.createAlias("nicTransaction", "nicTransaction");
		
			if(siteCodes!=null){
				c.add(Restrictions.in("nicTransaction.regSiteCode",siteCodes));
			}
		
			if(officerId != null || fromDate != null || toDate != null ){
		
			
			
			if(StringUtils.isNotEmpty(officerId)){
				c.add(Restrictions.like("regOfficerId", officerId, MatchMode.ANYWHERE).ignoreCase());
				}
				
				if(fromDate!=null && toDate!=null){
					c.add(Restrictions.between("createDate", fromDate,toDate ));
				}
				else if(fromDate!=null){
					c.add(Restrictions.ge("createDate", fromDate));
				}
				else if(toDate!=null){
					c.add(Restrictions.le("createDate", toDate));
				}
			}
				
			int total = ((Long) (c.setProjection(Projections.rowCount()).uniqueResult())).intValue();
						int maxNoPage = 1;
						if (total>pageSize) {
							maxNoPage = (int) Math.ceil(((double)total / pageSize));
						}
						int firstResults = (currentPage - 1)<0?0:(currentPage - 1) * pageSize;
						int maxResults = pageSize;
						if (currentPage == maxNoPage) {
							maxResults = total % pageSize;
							//Swapna:(30082013): Fixed pagination issue.
							if(maxResults==0){
								maxResults= pageSize;
							}
						}
						c.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);    
						c.setFirstResult(firstResults);    
						c.setMaxResults(maxResults); 
						
						c.addOrder(hibernateOrder);
						
						c.setProjection(Projections.projectionList()
							    .add(Projections.property("transactionId"), "transactionId")
							    .add(Projections.property("nin"), "nin"))
								.setResultTransformer(Transformers.aliasToBean(NicRegistrationData.class));
						
						List<NicRegistrationData> res = c.list();
						return new PaginatedResult<NicRegistrationData>(total, currentPage, res);
			
		}catch(Exception ex){
			logger.error("Error occurred while getting the search result list. Exception:"+ex.getMessage());
			throw new Exception("Error occurred while getting the search result list. Exception:"+ex.getMessage());
		}finally{
			session.close();
		}
	}

	public List<NicRegistrationData> findAllByFields (Map<String,Object> fields) throws Exception {
		
		Session session  =null;
		
		try{
			
			session=getHibernateTemplate().getSessionFactory().openSession(); 
		
		Criteria c = session.createCriteria(getPersistentClass());
		
		if (fields!=null && fields.size() > 0) {
			for (String field : fields.keySet()) {
				c.add(Restrictions.eq(field, fields.get(field)));
			}
		
			return	c.list();
		}
		
		return null;
				
		
/*		String hql = "select a.nicId from NicRegistrationData a where a.transactionId = ? ";
		
		Object[] parameters = { transactionId } ;
		List<String> resultList = (List<String>) this.getHibernateTemplate().find(hql, parameters);
		
		
		if (CollectionUtils.isNotEmpty(resultList)) {
			nicId = Long.parseLong(resultList.get(0));
		}
*/		
		//return null;
        
		}catch (Exception e){
			e.printStackTrace();
			throw e;
		}finally{
			session.close();
		}
	}
	
	public boolean isLookoutData (String firstName, String lastName, String middleName) throws Exception {
		boolean lookoutFlag = false;
		Session session = null;
		try {
			session = getHibernateTemplate().getSessionFactory().openSession();
			Query query = session.getNamedQuery("epp.getLookOutList");
			query.setString("firstName", firstName);
			query.setString("lastName", lastName);
			query.setString("middleName", middleName);

			
			List<Object> results = query.list();
			if (results!=null && results.size()>0 &&
					results.get(0)!=null && results.get(0) instanceof java.lang.String) {
				lookoutFlag = true;
			}
		} finally {
			session.close();
		}
		return lookoutFlag;

	}

	@Override
	public NicRegistrationData getNicDataById(String transactionId) {
		DetachedCriteria detachedCriteria = DetachedCriteria.forClass(getPersistentClass());
		
		if (StringUtils.isNotBlank(transactionId)) {
			detachedCriteria.add(Restrictions.eq("transactionId", transactionId))
			.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		}
		
		List<NicRegistrationData> resultReg = this.findAll(detachedCriteria);
		
		if(resultReg != null && resultReg.size() > 0){
			return resultReg.get(0);
		}
		return null;
	}

	@Override
	public BaseModelSingle<String> saveNicRegistrationData(
			NicRegistrationData nicRegData) {
		try {
			String id = this.save(nicRegData);
			return new BaseModelSingle<String>(id, true, null);
		} catch (Exception e) {
			return new BaseModelSingle<String>(null, false, CreateMessageException.createMessageException(e) + " - saveNicRegistrationData - " + nicRegData.getTransactionId() + " - thất bại.");
		}
	}

	@Override
	public BaseModelSingle<Void> saveOrUpdateRegisData(
			NicRegistrationData nicRegData) {
		try {
			this.saveOrUpdate(nicRegData);
			return new BaseModelSingle<Void>(null, true, null);
		} catch (Exception e) {
			return new BaseModelSingle<Void>(null, false, CreateMessageException.createMessageException(e) + " - saveOrUpdateRegisData - " + nicRegData.getTransactionId() + " - thất bại.");
		}
	}

	@Override
	public BaseModelSingle<NicRegistrationData> findRegistDataById(
			String transactionId) {
		
		try {
			NicRegistrationData nicRegistrationData = this.findById(transactionId);
			return new BaseModelSingle<NicRegistrationData>(nicRegistrationData, true, null);
		} catch (Exception e) {
			return new BaseModelSingle<NicRegistrationData>(null, false, CreateMessageException.createMessageException(e) + " - findRegistDataById - " + transactionId + " - thất bại.");
		}
	}

	@Override
	public List<NicRegistrationData> findRegByPersonInfo(String name,
			String gender, Date date) {
		List<NicRegistrationData> listRs = null;
		try {
			DetachedCriteria detachedCriteria = DetachedCriteria.forClass(getPersistentClass());
			
			if (StringUtils.isNotBlank(name)) {
				detachedCriteria.add(Restrictions.eq("searchName", HelperClass.removeAccent(name).toUpperCase().trim()));
			}
			if (StringUtils.isNotBlank(gender)) {
				detachedCriteria.add(Restrictions.eq("gender", gender));
			}
			if (date != null) {
				detachedCriteria.add(Restrictions.eq("dateOfBirth", date));
			}
			
			listRs = this.findAll(detachedCriteria);
		} catch (Exception e) {
			// TODO: handle exception
		}
		return listRs;
	}

	
}
