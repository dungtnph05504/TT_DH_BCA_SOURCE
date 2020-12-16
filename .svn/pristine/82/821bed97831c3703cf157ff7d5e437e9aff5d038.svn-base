package com.nec.asia.nic.comp.trans.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nec.asia.nic.comp.trans.dao.RptStatisticsTransmitDataDao;
import com.nec.asia.nic.comp.trans.domain.RptStatisticsTransmitData;
import com.nec.asia.nic.comp.trans.service.RptStatisticsTransmitDataService;
import com.nec.asia.nic.framework.PaginatedResult;
import com.nec.asia.nic.framework.service.impl.DefaultBusinessServiceImpl;
import com.nec.asia.nic.utils.DateUtil;

/**
 * @author JohnPaul_Millan
 * @Company : NEC ASIA PACIFIC PTE LTD
 * @Since : Mar 4, 2014
 * 
 */
 
 
/*
 * Modification History:
 * 
 * 13 Mar 2014 (chris): add the non-argument constructor
 * 22 Sep 2014 (chris): add method findAfisRefIdByTransactionId()
 * 11 Jan 2016 (chris): add method getNextAfisKeyNo()
 */
 
@Service("rptStatisticsTransmitDataSerivce")
public class RptStatisticsTransmitDataSerivceImpl 
					extends DefaultBusinessServiceImpl<RptStatisticsTransmitData, Long, RptStatisticsTransmitDataDao>
					implements RptStatisticsTransmitDataService {
	
	public final Logger logger = LoggerFactory.getLogger(this.getClass());

	public RptStatisticsTransmitDataSerivceImpl(){
		
	}

	@Autowired
	public RptStatisticsTransmitDataSerivceImpl(RptStatisticsTransmitDataDao dao) {
		this.dao = dao;
	}
	
	@Override
	public List<RptStatisticsTransmitData> findByCondition(String type, String site, String rptDate) {
		try {
			List<RptStatisticsTransmitData> list = this.dao.findByCondition(type, site, rptDate);
			if(list != null && list.size() > 0){
				return list;
			}
		}catch (Exception ex) {
			ex.printStackTrace();
		}
		return null;
	}

	@Override
	public Boolean saveOrUpdateData(RptStatisticsTransmitData detail){
		
		try {
			return dao.saveOrUpdateData(detail);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
		}
		
		return false;
	}
	
	@Override
	public List<RptStatisticsTransmitData> findSingerByCondition (String type, String site, Date rptDate){
		try {
			List<RptStatisticsTransmitData> model = this.dao.findSingerByCondition(type, site, rptDate);
			return model;
		}catch (Exception ex) {
			ex.printStackTrace();
		}
		return null;
	}
	
	@Override
	public List<RptStatisticsTransmitData> findBySiteCode(String site){
		try {
			List<RptStatisticsTransmitData> list = this.dao.findBySiteCode(site);
			if(list != null && list.size() > 0){
				return list;
			}
		}catch (Exception ex) {
			ex.printStackTrace();
		}
		return null;
	}

	@Override
	public RptStatisticsTransmitData findByType(String type) {
		try {
			return this.dao.findByType(type);
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		return null;
	}

	@Override
	public RptStatisticsTransmitData findSingerByConditions(String type,
			String site, Date rptDate) {
		try {
			RptStatisticsTransmitData model = this.dao.findSingerByConditions(type, site, rptDate);
			return model;
		}catch (Exception ex) {
			ex.printStackTrace();
		}
		return null;
	}

	@Override
	public List<RptStatisticsTransmitData> findAllAndOrder(String order) {
		try {
			return this.dao.findAllAndOrder(order);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
		}
		return new ArrayList<RptStatisticsTransmitData>();
	}

	@Override
	public PaginatedResult<RptStatisticsTransmitData> findAllForPagination(
			int PageNo, int PageSize) {
		try {
			return this.dao.findAllForPagination(PageNo, PageSize);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
		}
		return new PaginatedResult<RptStatisticsTransmitData>();
	}

	@Override
	public PaginatedResult<RptStatisticsTransmitData> findAllForPagination(
			String timeFrom, String timeTo, String type, int pageNo,
			int pageSize) {
		try {
			return this.dao.findAllForPagination(timeFrom, timeTo, type, pageNo, pageSize);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public List<String> getAllType() {
		try {
			return this.dao.getAllType();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ArrayList<String>();
	}

	@Override
	public List<RptStatisticsTransmitData> findAllByCondition(String type,
			String printSiteCode, String fromDate, String toDate) {
		try {
			Date fDate = null;
			Date tDate = null;
			if (StringUtils.isNotBlank(fromDate)) {
				fDate = DateUtil.strToDate(fromDate, DateUtil.FORMAT_YYYYMMDD);
			}
			if (StringUtils.isNotBlank(toDate)) {
				tDate = new Date(DateUtil.strToDate(toDate, DateUtil.FORMAT_YYYYMMDD).getTime() + 86400000L);
			}
			return this.dao.findAllByCondition(type, printSiteCode, fDate, tDate);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}
