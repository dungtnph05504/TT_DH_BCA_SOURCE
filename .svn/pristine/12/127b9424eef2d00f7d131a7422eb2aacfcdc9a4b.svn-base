package com.nec.asia.nic.comp.trans.service;

import java.util.List;

import com.nec.asia.nic.comp.trans.domain.NicSearchResult;
import com.nec.asia.nic.framework.service.BusinessService;



/**
 * @author setia_budiyono
 *
 */
public interface NicSearchResultService extends BusinessService<NicSearchResult, Long> {
	
	public List<NicSearchResult> findAllByJobId(Long jobId) throws Exception;

	public NicSearchResult findLatestResultByJobId(Long jobId, String typeOfSearch) throws Exception;
	
	public NicSearchResult findSearchResultByTranIdAndTypeSearch(String transactionId, String typeOfSearch) throws Exception;
}
