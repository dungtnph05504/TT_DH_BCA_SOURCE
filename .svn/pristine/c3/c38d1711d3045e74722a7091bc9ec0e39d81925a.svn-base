/**
 * 
 */
package com.nec.asia.nic.comp.trans.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nec.asia.nic.comp.trans.dao.NicSearchResultDao;
import com.nec.asia.nic.comp.trans.domain.NicSearchResult;
import com.nec.asia.nic.comp.trans.service.NicSearchResultService;
import com.nec.asia.nic.comp.ws.log.domain.CreateMessageException;
import com.nec.asia.nic.framework.service.impl.DefaultBusinessServiceImpl;

/**
 * @author sailaja_chinapothula
 * @Company: NEC Asia Pacific Ltd
 * @Since: Jul 15, 2013
 */
@Service("nicSearchResultService")
public class NicSearchResultServiceImpl extends DefaultBusinessServiceImpl<NicSearchResult, Long, NicSearchResultDao>
implements NicSearchResultService {
	
	@Autowired
	private NicSearchResultDao nicSearchResultDao = null;

	@Autowired
	public NicSearchResultServiceImpl(NicSearchResultDao dao){
		this.dao = dao;
	}
	
	/* (non-Javadoc)
	 * @see com.nec.asia.nic.comp.trans.service.NicSearchHitResultService#findHitPositions(java.lang.String)
	 */
	@Override
	public List<NicSearchResult> findAllByJobId(Long jobId)
			throws Exception {
		List<NicSearchResult> result=null;
		try{
			 result = dao.findAllByJobId(jobId);
		}catch(Exception ex){
			logger.error("Error occurred while getting the list NicSearchResult for . Exception: "+ex.getMessage());
			throw new Exception(ex);
		}
		return result;
	}
	
	@Override
	public NicSearchResult findLatestResultByJobId(Long jobId, String typeOfSearch)
			throws Exception {
		NicSearchResult result=null;
		try{
			 result = dao.findLatestResultByJobId(jobId,typeOfSearch);
		}catch(Exception ex){
			logger.error("Error occurred while getting the Only one NicSearchResult for . Exception: "+ex.getMessage());
			throw new Exception(ex);
		}
		return result;
	}

	@Override
	public NicSearchResult findSearchResultByTranIdAndTypeSearch(String transactionId,
			String typeOfSearch) throws Exception {
		NicSearchResult nicSearchResult = null;
		try {
			List<NicSearchResult> list = this.dao.findSearchResultsByTranIdAndTypeSearch(transactionId, typeOfSearch);
			if (list != null && list.size() > 0) {
				nicSearchResult = list.get(0);
			}
		} catch (Exception e) {
			throw new Exception(CreateMessageException.createMessageException(e) + " - findSearchResultByTranIdAndTypeSearch thất bại.");
		}
		return nicSearchResult;
	}
	
}
