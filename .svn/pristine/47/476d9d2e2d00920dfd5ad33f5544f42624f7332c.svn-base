package com.nec.asia.nic.comp.trans.service;

import java.util.Collection;
import java.util.List;

import com.nec.asia.idserver.agent.payload.model.result.IdserverMatchResult;
import com.nec.asia.nic.comp.trans.domain.BufEppPersonInvestigation;
import com.nec.asia.nic.comp.trans.domain.NicSearchHitResult;
import com.nec.asia.nic.comp.trans.domain.NicSearchResult;
import com.nec.asia.nic.comp.trans.dto.SearchResultInA08Dto;
import com.nec.asia.nic.comp.trans.service.exception.InquirySearchServiceException;
import com.nec.asia.nic.framework.service.BusinessService;

/**
 * @author setia_budiyono
 *
 */
/* 
 * Modification History:
 * 20 Jan 2016 (chris) - add findLatestResultByJobId
 * 04 Feb 2016 (setia) - add a new method to save searchResult and searchHitResult
 */
public interface InquirySearchResultService extends BusinessService<NicSearchResult, Long> {
	
	public final static String SCREENING_FP_1_TO_N = "FP 1:N";
	public final static String VERIFICATION_FP_1_TO_1 = "FP 1:1";
	
	public long saveSearchResult(String typeOfSearch, IdserverMatchResult matchResult, Long workflowJobId, String transactionId, String afisKeyNo) throws InquirySearchServiceException ;
	
	public IdserverMatchResult findSearchResultById(long searchResultId) throws InquirySearchServiceException;

	public NicSearchResult findLatestResultByJobId(Long jobId, String typeOfSearch) throws InquirySearchServiceException ;
	
	public long saveSearchResult(NicSearchResult searchResult, Collection<NicSearchHitResult> searchHitResult) throws InquirySearchServiceException ;
	
	public Boolean removeNicSearchResultById(NicSearchResult request);
	
	public Boolean removeNicSearchHitResultById(NicSearchHitResult request);
	
	public List<NicSearchHitResult> findListHitResultByJobId(Long searchResultId) throws InquirySearchServiceException;
	
	public  List<BufEppPersonInvestigation>  getResultA08(SearchResultInA08Dto request) throws InquirySearchServiceException;

	public List<BufEppPersonInvestigation> getCheckResultA08(
			SearchResultInA08Dto request)throws InquirySearchServiceException;
}
