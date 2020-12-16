/**
 * 
 */
package com.nec.asia.nic.comp.trans.service.impl;


import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nec.asia.idserver.agent.payload.model.result.IdserverMatchResult;
import com.nec.asia.idserver.agent.payload.model.result.IdserverScore;
import com.nec.asia.idserver.agent.payload.model.result.IdserverSubject;
import com.nec.asia.nic.comp.trans.dao.NicSearchHitResultDao;
import com.nec.asia.nic.comp.trans.dao.NicSearchResultDao;
import com.nec.asia.nic.comp.trans.domain.BufEppPersonInvestigation;
import com.nec.asia.nic.comp.trans.domain.NicSearchHitResult;
import com.nec.asia.nic.comp.trans.domain.NicSearchResult;
import com.nec.asia.nic.comp.trans.domain.NicTransactionPayment;
import com.nec.asia.nic.comp.trans.dto.SearchResultInA08Dto;
import com.nec.asia.nic.comp.trans.service.InquirySearchResultService;
import com.nec.asia.nic.comp.trans.service.exception.InquirySearchServiceException;
import com.nec.asia.nic.framework.dao.DaoException;
import com.nec.asia.nic.framework.service.impl.DefaultBusinessServiceImpl;

/**
 * @author setia_budiyono
 *
 */
/* 
 * Modification History:
 * 05 Jan 2016 (chris) - to add type of search
 * 04 Feb 2016 (setia) - to fix the logic to insert a new record of search hit result
 */
@Service("inquirySearchResultService")
public class InquirySearchResultServiceImpl extends
		DefaultBusinessServiceImpl<NicSearchResult, Long, NicSearchResultDao> implements InquirySearchResultService {
	
	@Autowired
	private NicSearchHitResultDao searchHitResultDao;
	
	public InquirySearchResultServiceImpl () {}
	
	@Autowired
	public InquirySearchResultServiceImpl (NicSearchResultDao dao) {
		this.dao = dao; 
	}
	
	public long saveSearchResult(String typeOfSearch, IdserverMatchResult matchResult, Long workflowJobId, String transactionId, String afisKeyNo) throws InquirySearchServiceException {
		if (matchResult == null)
			throw new InquirySearchServiceException ("MatchResult cannot be empty");
		
		NicSearchResult searchResult = new NicSearchResult();
		searchResult.setWorkflowJobId(workflowJobId);
		searchResult.setTransactionId(transactionId);
		searchResult.setAfisKeyNo(afisKeyNo);
		searchResult.setTypeSearch(typeOfSearch);//(SCREENING_FP_1_TO_N);
		searchResult.setHasHit(matchResult.isHasHit());
		searchResult.setCreateDatetime(new Date());

		List<NicSearchHitResult> hitList = new ArrayList<NicSearchHitResult> ();
		if (CollectionUtils.isNotEmpty(matchResult.getSubjectList())) {
			NicSearchHitResult hitResult = null;
			for (IdserverSubject subject : matchResult.getSubjectList()) {
				hitResult = new NicSearchHitResult();
				hitResult.setTransactionIdHit(subject.getRegistrationId());
				hitResult.setAfisKeyNoHit(subject.getPersonIdentifier());
				hitResult.setDataSource(subject.getDataSource());
				hitResult.setHitDecision(subject.getHitDecision());
				hitResult.setSearchResultId(searchResult.getSearchResultId());
				hitResult.setCreateDatetime(new Date());
				String hitInfo = "";
				for (IdserverScore score : subject.getScoreList()) {
					if (StringUtils.isNotBlank(hitInfo)) {
						hitInfo += ",";
					}
					hitInfo += score.getFingerPosition() + "=" + score.getScore();
				}
				
				hitResult.setHitInfo(hitInfo);
				hitList.add(hitResult);
			}
		}
		
		searchResult.getHitList().addAll(hitList);
		return this.saveSearchResult(searchResult, searchResult.getHitList());
	}
	
	 public long saveSearchResult(NicSearchResult searchResult, Collection<NicSearchHitResult> searchHitResultList) throws InquirySearchServiceException  {
			if (searchResult == null)
				throw new InquirySearchServiceException ("NicSearchResult cannot be empty");
			
			searchResult.setCreateDatetime(new Date());
			searchResult.setHitList(null);
			long searchResultId = this.dao.save(searchResult);
			if (CollectionUtils.isNotEmpty(searchHitResultList)) {
				for (NicSearchHitResult hit : searchHitResultList) {
					hit.setSearchResultId(searchResultId);
					this.searchHitResultDao.save(hit);
				}
				logger.info("SAVE_SEARCH_HIT_LIST: " + searchResult.getTransactionId());
			}
			logger.info("SAVE_SEARCH_HIT: " + searchResult.getTransactionId());
			return searchResultId;
	 }

	
	
	public IdserverMatchResult findSearchResultById(long searchResultId) throws InquirySearchServiceException {
		NicSearchResult searchResult = this.dao.findById(searchResultId);
		IdserverMatchResult matchResult = new IdserverMatchResult();
		
		if (searchResult == null)
			throw new InquirySearchServiceException ("Invalid search result id. No data found.");

		matchResult.setHasHit(searchResult.getHasHit());
		List<IdserverSubject> subjectList = new ArrayList<IdserverSubject> ();

		try {
			List<NicSearchHitResult> resultList = this.searchHitResultDao.findHitResultBySearchId(searchResult.getSearchResultId());
			
			if (CollectionUtils.isNotEmpty(resultList)) {
				IdserverSubject subject = null;
				for (NicSearchHitResult hitResult : resultList) {
					subject = new IdserverSubject();
					subject.setDataSource(hitResult.getDataSource());
					subject.setHitDecision(hitResult.getHitDecision());
//					subject.setPersonIdentifier(hitResult.getNicIdHit());
					subject.setRegistrationId(hitResult.getTransactionIdHit());
					
					//construct score
//					StringTokenizer token = new StringTokenizer(hitResult.getHitPositions(),",");
//					List<IdserverScore> scoreList = new ArrayList<IdserverScore> ();
//					while (token.hasMoreTokens()) {
//						StringTokenizer tokenScore = new StringTokenizer(token.nextToken(), "=");
//						IdserverScore score = null;
//						while(tokenScore.hasMoreTokens()) {
//							score = new IdserverScore();
//							score.setFingerPosition(new Short(tokenScore.nextToken()));
//							score.setScore(new Float(tokenScore.nextToken()));
//							scoreList.add(score);
//						}
//					}
//					
//					subject.setScoreList(scoreList);
					subjectList.add(subject);
				}
				matchResult.setSubjectList(subjectList);
			}
			
			return matchResult;
		}catch (DaoException ex) {
			throw new InquirySearchServiceException (ex);
		}
	}

	
	@Override
	public Boolean removeNicSearchResultById(NicSearchResult request){
		try {
			dao.delete(request);
			return true;
		}
		catch (Exception ex) {
			
		}
		
		return false;
	}
	
	@Override
	public Boolean removeNicSearchHitResultById(NicSearchHitResult request){
		try {
			 this.searchHitResultDao.delete(request);
			return true;
		}
		catch (Exception ex) {
			
		}
		
		return false;
	}

	@Override
	public NicSearchResult findLatestResultByJobId(Long jobId, String typeOfSearch) throws InquirySearchServiceException {
		NicSearchResult searchResult = dao.findLatestResultByJobId(jobId, typeOfSearch);
		logger.debug("findLatestResultByJobId({}) : result!=null ? {}", jobId, searchResult!=null);
		return searchResult;
	}
	
	@Override
	public List<NicSearchHitResult> findListHitResultByJobId(Long searchResultId) throws InquirySearchServiceException {
		List<NicSearchHitResult> searchResult = null;
		try {
			searchResult = this.searchHitResultDao.findHitResultBySearchId(searchResultId);
		} catch (DaoException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		logger.debug("findLatestResultByJobId({}) : result!=null ? {}", searchResultId, searchResult!=null);
		return searchResult;
	}
	
	@Override
	public List<BufEppPersonInvestigation> getResultA08(SearchResultInA08Dto request) throws InquirySearchServiceException {
		try{
			return this.searchHitResultDao.getResultA08(request);
		}catch(Exception e){
			logger.error("GET DATA A08 FAIL: " + e.getMessage());
			return new ArrayList<BufEppPersonInvestigation>();
		}
	}

	@Override
	public List<BufEppPersonInvestigation> getCheckResultA08(
			SearchResultInA08Dto request) throws InquirySearchServiceException {
		try{
			return this.searchHitResultDao.getResultA08Check(request);
		}catch(Exception e){
			logger.error("GET DATA A08 FAIL: " + e.getMessage());
			return new ArrayList<BufEppPersonInvestigation>();
		}
	}
}
