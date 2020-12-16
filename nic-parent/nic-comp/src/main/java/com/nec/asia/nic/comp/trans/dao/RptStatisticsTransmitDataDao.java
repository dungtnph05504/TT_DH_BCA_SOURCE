package com.nec.asia.nic.comp.trans.dao;

import java.util.Date;
import java.util.List;

import com.nec.asia.nic.comp.trans.domain.RptStatisticsTransmitData;
import com.nec.asia.nic.framework.PaginatedResult;
import com.nec.asia.nic.framework.dao.GenericDao;

public interface RptStatisticsTransmitDataDao extends
		GenericDao<RptStatisticsTransmitData, Long> {

	public List<RptStatisticsTransmitData> findByCondition(String type,
			String site, String rptDate);

	public Boolean saveOrUpdateData(RptStatisticsTransmitData detail);

	public List<RptStatisticsTransmitData> findSingerByCondition(String type,
			String site, Date rptDate);

	public RptStatisticsTransmitData findSingerByConditions(String type,
			String site, Date rptDate);

	public List<RptStatisticsTransmitData> findBySiteCode(String site)
			throws Exception;

	public RptStatisticsTransmitData findByType(String type);

	public List<RptStatisticsTransmitData> findAllAndOrder(String order);

	public PaginatedResult<RptStatisticsTransmitData> findAllForPagination(
			int PageNo, int PageSize);

	public PaginatedResult<RptStatisticsTransmitData> findAllForPagination(
			String timeFrom, String timeTo, String type, int pageNo,
			int pageSize);
	
	public List<String> getAllType();

	public List<RptStatisticsTransmitData> findAllByCondition(String type,
			String printSiteCode, Date fDate, Date tDate) throws Exception;

}
