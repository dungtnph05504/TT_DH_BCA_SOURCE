/**
 * 
 */
package com.nec.asia.nic.comp.trans.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nec.asia.nic.comp.trans.dao.NicFPDataDao;
import com.nec.asia.nic.comp.trans.domain.BaseModelSingle;
import com.nec.asia.nic.comp.trans.domain.NicFPData;
import com.nec.asia.nic.comp.trans.service.NicFPDataService;
import com.nec.asia.nic.framework.PaginatedResult;
import com.nec.asia.nic.framework.service.impl.DefaultBusinessServiceImpl;

/**
 * @author Peddi Swapna
 * @Company: NEC Asia Pacific Ltd
 * @Since: Nov 25, 2013
 */
@Service("nicFPDataService")
public class NicFPDataServiceImpl extends DefaultBusinessServiceImpl<NicFPData, String, NicFPDataDao>
implements NicFPDataService {

	/** must define the non-argument constructor because we use CGLib proxy */
	public NicFPDataServiceImpl(){
		
	}
	
	@Autowired
	public NicFPDataServiceImpl(NicFPDataDao dao){
		this.dao = dao;
	}

	@Override
	public PaginatedResult<NicFPData> findAllForPagination(int currentPage, int pageSize, String[] siteCodes, String officerId, Date fromDate,	Date toDate, Long[] fpPos, Long fromQuaScore, Long toQuaScore, Long fromVerficationScore, Long toVerficationScore) throws Exception {
		return dao.findAllForPagination(currentPage, pageSize,siteCodes,officerId,fromDate,toDate,fpPos, fromQuaScore, toQuaScore, fromVerficationScore, toVerficationScore);
	}

	@Override
	public List<Object[]> getFPQByFingerReportData(String[] siteCodes, Date fromDate, Date toDate) throws Exception {
		return dao.getFPQByFingerReportData(siteCodes,fromDate,toDate);
	}

	@Override
	public List<Object[]> getFPQPerByFingerReportData(String[] siteCodes, Date fromDate, Date toDate) throws Exception {
		return dao.getFPQPerByFingerReportData(siteCodes,fromDate,toDate);
	}

	@Override
	public List<Object[]> getFPQByPopulationReportData(String siteCode, Date fromDate, Date toDate) throws Exception {
		return dao.getFPQByPopulationReportData(siteCode,fromDate,toDate);
	}

	@Override
	public List<Object[]> getVerifyScoreSummaryCounts(Date fromDate, Date toDate, String[] sites) throws Exception {
		return dao.getVerifyScoreSummaryCounts(fromDate,toDate, sites);
	}

	@Override
	public PaginatedResult<String> getTransIdsWithBelowVerifyScore(Date fromDate, Date toDate, int verifyScore, int fromRow, int toRow, String[] sites) throws Exception {
		return dao.getTransIdsWithBelowVerifyScore(fromDate,toDate, verifyScore, fromRow, toRow, sites);
	}

	@Override
	public PaginatedResult<String> getTransIdsWithAboveVerifyScore( Date fromDate, Date toDate, int verifyScore, int fromRow, int toRow, String[] sites) throws Exception {
		return dao.getTransIdsWithAboveVerifyScore(fromDate,toDate, verifyScore, fromRow, toRow, sites);
	}

	@Override
	public List<Object[]> getAvgFPScores(Date fromDate, Date toDate, String[] sites) throws Exception {
		return dao.getAvgFPScores(fromDate,toDate, sites);
	}

	@Override
	public List<Object[]> getVerifyFailureRateRptData(Date fromDate, Date toDate, String[] sites) throws Exception {
		return dao.getVerifyFailureRateRptData(fromDate,toDate, sites);
	}

	@Override
	public List<Object[]> getLowVerifyCountByFP(Date fromDate, Date toDate, String[] sites) throws Exception {
		return dao.getLowVerifyCountByFP(fromDate,toDate, sites);
	}

	@Override
	public List<Object[]> getLowQualityCountByFP(Date fromDate, Date toDate, String[] sites) throws Exception {
		return dao.getLowQualityCountByFP(fromDate,toDate, sites);
	}

	@Override
	public List<Object[]> getTotalCasesVerifiableInfo(Date fromDate, Date toDate, String[] sites) throws Exception {
		return dao.getTotalCasesVerifiableInfo(fromDate,toDate, sites);
	}

	@Override
	public List<String> getAllTransIdsWithAboveVerifyScore(Date fromDate, Date toDate, int verifyScore, String[] sites) throws Exception {
		return dao.getAllTransIdsWithAboveVerifyScore(fromDate,toDate, verifyScore, sites);
	}

	@Override
	public List<String> getAllTransIdsWithBelowVerifyScore(Date fromDate, Date toDate, int verifyScore, String[] sites) throws Exception {
		return dao.getAllTransIdsWithBelowVerifyScore(fromDate,toDate, verifyScore, sites);
	}

	@Override
	public PaginatedResult<String> getTransIdsWithBelowQuaScore(Date fromDate, Date toDate, int quality, int fromRow, int toRow, String[] sites) throws Exception {
		return dao.getTransIdsWithBelowQuaScore(fromDate,toDate, quality, fromRow, toRow, sites);
	}

	@Override
	public PaginatedResult<String> getTransIdsWithAboveQualityScore(Date fromDate, Date toDate, int quality, int fromRow, int toRow,  String[] sites) throws Exception {
		return dao.getTransIdsWithAboveQualityScore(fromDate,toDate, quality, fromRow, toRow, sites);
	}

	@Override
	public List<String> getAllTransIdsWithAboveQualityScore(Date fromDate, Date toDate, int quality, String[] sites) throws Exception {
		return dao.getAllTransIdsWithAboveQualityScore(fromDate,toDate, quality, sites);
	}

	@Override
	public List<String> getAllTransIdsWithBelowQualityScore(Date fromDate, Date toDate, int quality, String[] sites) throws Exception {
		return dao.getAllTransIdsWithBelowQualityScore(fromDate,toDate, quality, sites);
	}

	@Override
	public BaseModelSingle<Boolean> saveNicFPData(NicFPData fpData)
			throws Exception {
		try {
			return this.dao.saveNicFPData(fpData);
		} catch (Exception e) {
			return new BaseModelSingle<Boolean>(null, false, e.getMessage() + "\n [subtrack] \n" + e.getCause().getMessage() + "\n [subtrack] \n" + e.getCause().getCause().getMessage() + " - saveNicFPData - " + fpData.getDocId() + " - thất bại.");
		}
	}

}
