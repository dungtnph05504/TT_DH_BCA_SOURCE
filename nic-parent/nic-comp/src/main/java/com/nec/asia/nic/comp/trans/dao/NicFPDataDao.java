package com.nec.asia.nic.comp.trans.dao;

import java.util.Date;
import java.util.List;

import com.nec.asia.nic.comp.trans.domain.BaseModelSingle;
import com.nec.asia.nic.comp.trans.domain.NicFPData;
import com.nec.asia.nic.framework.PaginatedResult;
import com.nec.asia.nic.framework.dao.GenericDao;

public interface NicFPDataDao extends GenericDao<NicFPData, String> {

	public PaginatedResult<NicFPData> findAllForPagination(int currentPage, int pageSize, String[] siteCodes, String officerId, Date fromDate, Date toDate, Long[] fpPos, Long fromQuaScore, Long toQuaScore, Long fromVerficationScore, Long toVerficationScore) throws Exception;

	public List<Object[]> getFPQByFingerReportData(String[] siteCodes, Date fromDate, Date toDate) throws Exception;

	public List<Object[]> getFPQPerByFingerReportData(String[] siteCodes,Date fromDate, Date toDate) throws Exception;

	public List<Object[]> getFPQByPopulationReportData(String siteCode, Date fromDate, Date toDate) throws Exception;

	public List<Object[]> getVerifyScoreSummaryCounts(Date fromDate, Date toDate, String[] sites)  throws Exception;

	public PaginatedResult<String> getTransIdsWithBelowVerifyScore(Date fromDate, Date toDate, int verifyScore, int fromRow, int toRow, String[] sites) throws Exception;

	public PaginatedResult<String> getTransIdsWithAboveVerifyScore(Date fromDate, Date toDate, int verifyScore, int fromRow, int toRow, String[] sites) throws Exception;
	
	public List<Object[]> getAvgFPScores(Date fromDate, Date toDate, String[] sites)  throws Exception;

	public List<Object[]> getVerifyFailureRateRptData(Date fromDate, Date toDate, String[] sites) throws Exception;

	public List<Object[]> getLowVerifyCountByFP(Date fromDate, Date toDate, String[] sites) throws Exception;

	public List<Object[]> getLowQualityCountByFP(Date fromDate, Date toDate, String[] sites) throws Exception;

	public List<Object[]> getTotalCasesVerifiableInfo(Date fromDate, Date toDate, String[] sites) throws Exception;

	public List<String> getAllTransIdsWithAboveVerifyScore(Date fromDate, Date toDate, int verifyScore, String[] sites) throws Exception;

	public List<String> getAllTransIdsWithBelowVerifyScore(Date fromDate, Date toDate, int verifyScore, String[] sites) throws Exception;

	public PaginatedResult<String> getTransIdsWithBelowQuaScore(Date fromDate,Date toDate, int quality, int fromRow, int toRow, String[] sites) throws Exception;

	public PaginatedResult<String> getTransIdsWithAboveQualityScore(Date fromDate, Date toDate, int quality, int fromRow, int toRow, String[] sites) throws Exception;

	public List<String> getAllTransIdsWithAboveQualityScore(Date fromDate, Date toDate, int quality, String[] sites) throws Exception;

	public List<String> getAllTransIdsWithBelowQualityScore(Date fromDate, Date toDate, int quality, String[] sites) throws Exception;

	public BaseModelSingle<Boolean> saveNicFPData(NicFPData fpData);
	
}
