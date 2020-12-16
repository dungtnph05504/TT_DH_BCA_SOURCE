package com.nec.asia.nic.comp.trans.dao;

import java.util.List;

import com.nec.asia.nic.comp.trans.domain.NicSearchResult;
import com.nec.asia.nic.framework.dao.GenericDao;



/**
 * @author setia_budiyono
 *
 */
public interface NicSearchResultDao extends GenericDao<NicSearchResult, Long> {
	
	public List<NicSearchResult> findAllByJobId(Long jobId);

	public NicSearchResult findLatestResultByJobId(Long jobId, String typeOfSearch);
	
	public List<NicSearchResult> findSearchResultsByTranIdAndTypeSearch(String transactionId, String typeOfSearch) throws Exception;
}
