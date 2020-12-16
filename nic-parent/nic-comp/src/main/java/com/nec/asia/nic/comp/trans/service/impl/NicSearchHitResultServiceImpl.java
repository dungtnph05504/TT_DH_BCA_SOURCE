/**
 * 
 */
package com.nec.asia.nic.comp.trans.service.impl;

import java.util.List;
import java.util.StringTokenizer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nec.asia.nic.comp.job.dto.HitCandidateDTO;
import com.nec.asia.nic.comp.trans.dao.NicSearchHitResultDao;
import com.nec.asia.nic.comp.trans.domain.NicSearchHitResult;
import com.nec.asia.nic.comp.trans.dto.NicHitTransaction;
import com.nec.asia.nic.comp.trans.dto.NicUploadJobDto;
import com.nec.asia.nic.comp.trans.service.NicSearchHitResultService;
import com.nec.asia.nic.comp.ws.log.domain.CreateMessageException;
import com.nec.asia.nic.framework.service.impl.DefaultBusinessServiceImpl;

/**
 * @author sailaja_chinapothula
 * @Company: NEC Asia Pacific Ltd
 * @Since: Jul 15, 2013
 */
@Service("nicSearchHitResultService")
public class NicSearchHitResultServiceImpl extends DefaultBusinessServiceImpl<NicSearchHitResult, Long, NicSearchHitResultDao>
implements NicSearchHitResultService {
	
	@Autowired
	private NicSearchHitResultDao nicSearchHitResultDao = null;

	/* (non-Javadoc)
	 * @see com.nec.asia.nic.comp.trans.service.NicSearchHitResultService#findHitPositions(java.lang.String)
	 */
	@Override
	public List<NicSearchHitResult> findHitPositions(String hitTransactionId, Long searchResultId)
			throws Exception {
		List<NicSearchHitResult> result=null;
		try{
			 result = nicSearchHitResultDao.findHitPositions(hitTransactionId, searchResultId);
		}catch(Exception ex){
			logger.error("Error occurred while getting the Match Score for Hit Candidate. Exception: "+ex.getMessage());
			throw new Exception(ex);
		}
		return result;
	}
	
	@Override
	public List<NicSearchHitResult> findHitPositionsHit(String hitTransactionId, Long hitResultId)
			throws Exception {
		List<NicSearchHitResult> result=null;
		try{
			 result = nicSearchHitResultDao.findHitPositionsHit(hitTransactionId, hitResultId);
		}catch(Exception ex){
			logger.error("Error occurred while getting the Match Score for Hit Candidate. Exception: "+ex.getMessage());
			throw new Exception(ex);
		}
		return result;
	}
	
	@Override
	public List<String> findListHitSpecial(String transactionId, String typeHit, int pageNumber, int pageSize){
	
		List<String> result = null;
		try {
			result = nicSearchHitResultDao.findListHitSpecial(transactionId, typeHit, pageNumber, pageSize);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
		}
		
		return result;
	}
	
	@Override
	public List<NicHitTransaction> findHitBySource(String tranid, String source, String regSite, long searchId){
	
		List<NicHitTransaction> result = null;
		try {
			result = nicSearchHitResultDao.findHitBySource(tranid, source, regSite, searchId);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
		}
		
		return result;
	}

	@Override
	public NicSearchHitResult findSearchHitResultByTranIdAndSearchId(
			String tranId, Long searchId) throws Exception {
		NicSearchHitResult nicHitResult = null;
		try {
			List<NicSearchHitResult> list = this.dao.findHitResultsByTranIdAndSearchId(tranId, searchId);
			if (list != null && list.size() > 0) {
				nicHitResult = list.get(0);
			}
		} catch (Exception e) {
			throw new Exception(CreateMessageException.createMessageException(e) + " - findSearchHitResultByTranIdAndSearchId thất bại.");
		}
		return nicHitResult;
	}
	
	/* helper method */
	/*private void setHitFingersNScore(String result, NicSearchHitResult dto) {
		String hitFingers = "";
		double matchingScore = 0;
		
		StringTokenizer token = new StringTokenizer(result,",");
		while (token.hasMoreTokens()) {
			StringTokenizer tokenScore = new StringTokenizer(token.nextToken(), "=");
			while(tokenScore.hasMoreTokens()) {
				String fingerPosition = tokenScore.nextToken();
				float score = Float.parseFloat(tokenScore.nextToken());
				hitFingers += fingerPosition;
				matchingScore += score;
			}
		}
		if (matchingScore>=9999) {
			matchingScore=9999;
		}
		//dto.setHitFingers(hitFingers);
		//dto.setMatchingScore(""+matchingScore);
	}
	*/
}
