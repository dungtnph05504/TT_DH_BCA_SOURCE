package com.nec.asia.nic.comp.trans.dao.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.nec.asia.nic.comp.trans.dao.RptStatisticsTransmitDataDao;
import com.nec.asia.nic.comp.trans.domain.RptStatisticsTransmitData;
import com.nec.asia.nic.framework.PaginatedResult;
import com.nec.asia.nic.framework.dao.impl.GenericHibernateDao;
import com.nec.asia.nic.utils.DateUtil;

@Repository("rptStatisticsTransmitDataDao")
public class RptStatisticsTransmitDataDaoImpl extends GenericHibernateDao<RptStatisticsTransmitData, Long> implements RptStatisticsTransmitDataDao{

	@Override
	public List<RptStatisticsTransmitData> findByCondition(String type, String site, String rptDate) {
		try {
			List<RptStatisticsTransmitData> list = null;
			DetachedCriteria criteria = DetachedCriteria.forClass(RptStatisticsTransmitData.class);
			if(StringUtils.isNotEmpty(type)){
				criteria.add(Restrictions.eq("type", type));
			}
			if(StringUtils.isNotEmpty(site)){
				criteria.add(Restrictions.eq("siteCode", site));
			}
			if(StringUtils.isNotEmpty(rptDate)){
				criteria.add(Restrictions.eq("rptDate", rptDate));
			}
			list = this.findAll(criteria);
			return list;
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
		}
		return null;
	}
	
	@Override
	public Boolean saveOrUpdateData(RptStatisticsTransmitData detail){
		try {
			if(detail != null){
				this.saveOrUpdate(detail);
				return true;
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
		}
		
		return false;
	}

	@Override
	public List<RptStatisticsTransmitData> findSingerByCondition (String type, String site, Date rptDate) {
		List<RptStatisticsTransmitData> result = null;
		try {
			DetachedCriteria criteria = DetachedCriteria.forClass(RptStatisticsTransmitData.class);
			if(StringUtils.isNotEmpty(type)){
				criteria.add(Restrictions.eq("type", type));
			}
			String[] sites = new String[] { "AA", "BB", "CC" };
			if(site.equals("A")){
				criteria.add(Restrictions.in("siteCode", sites));
			}else if (site.equals("PA")){
				criteria.add(Restrictions.not(Restrictions.in("siteCode", sites)));
			}
			if(rptDate != null){
				criteria.add(Restrictions.eq("rptDate", rptDate));
			}
			result = this.findAll(criteria);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("DATE error : " +  rptDate);
		}
		return result;
	}

	@Override
	public List<RptStatisticsTransmitData> findBySiteCode(String site) throws Exception {
		Session session = null;
		List<RptStatisticsTransmitData> lst = null;
		try {
			session = this.openSession();
			Query query = session.getNamedQuery("truyvantruyendl");
			query.setString("site", site);
			List<Object[]> list = query.list();
			if (CollectionUtils.isNotEmpty(list)) {
				lst = new ArrayList<RptStatisticsTransmitData>();
				for (int i = 0; i < list.size(); i++) {
					RptStatisticsTransmitData countP = new RptStatisticsTransmitData();
					 Object[] obj = (Object[]) list.get(i);
					 countP.setSiteCode((String) obj[0]);
					 countP.setType((String) obj[1]);
					 countP.setTotal((Integer) obj[2]);
					 countP.setRptDate((Date) obj[3]);
					 countP.setId((Long) obj[4]);
					 lst.add(countP);
				}
			}
			//logger.info("[updateTransactionStatus] update result returned for status: {}, updateCount: {}", status, updateCount);
		} catch (HibernateException ex) {
			throw new Exception(ex);
		}finally {
			session.close();
		}
		return lst;
	}

	@Override
	public RptStatisticsTransmitData findByType(String type) {
		try {
			DetachedCriteria criteria = DetachedCriteria.forClass(RptStatisticsTransmitData.class);
			if(StringUtils.isNotEmpty(type)){
				criteria.add(Restrictions.eq("type", type));
			}
			List<RptStatisticsTransmitData> result = this.findAll(criteria);
			if(result != null && result.size() > 0){
				//return list;
				return result.get(0);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public RptStatisticsTransmitData findSingerByConditions(String type,
			String site, Date rptDate) {
		try {
			DetachedCriteria criteria = DetachedCriteria.forClass(RptStatisticsTransmitData.class);
			if(StringUtils.isNotEmpty(type)){
				criteria.add(Restrictions.eq("type", type));
			}
			if(StringUtils.isNotEmpty(site)){
				criteria.add(Restrictions.eq("siteCode", site));
			}
			if(rptDate != null){
				criteria.add(Restrictions.eq("rptDate", rptDate));
			}
			List<RptStatisticsTransmitData> result = this.findAll(criteria);
			if(result != null && result.size() > 0){
				//return list;
				return result.get(0);
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
		}
		return null;
	}

	@Override
	public List<RptStatisticsTransmitData> findAllAndOrder(String order) {
		try {
			DetachedCriteria criteria = DetachedCriteria.forClass(getPersistentClass());
			Order orders = Order.desc(order);
			return this.findAllOrder(criteria, orders);
		} catch (Exception e) {
			logger.error(e.getMessage());
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public PaginatedResult<RptStatisticsTransmitData> findAllForPagination(
			int PageNo, int PageSize) {
		try {
			DetachedCriteria criteria = DetachedCriteria
					.forClass(getPersistentClass());
			Order orders = Order.desc("updateDatetime");
			return this.findAllForPagination(criteria, PageNo, PageSize, orders);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public PaginatedResult<RptStatisticsTransmitData> findAllForPagination(
			String timeFrom, String timeTo, String type, int pageNo,
			int pageSize) {
		try {
			DetachedCriteria criteria = DetachedCriteria
					.forClass(getPersistentClass());
			Order orders = Order.desc("updateDatetime");
			Date dateFrom = DateUtil.strToDate(timeFrom, DateUtil.FORMAT_YYYYdashMMdashDD);
			Date dateTo = DateUtil.strToDate(timeTo, DateUtil.FORMAT_YYYYdashMMdashDD);
			if(StringUtils.isNotBlank(type)){
				criteria.add(Restrictions.eq("type", type));
			}
			if (dateFrom != null && dateTo != null) {
				criteria.add(Restrictions.between("rptDate", dateFrom, dateTo));
			} else if(dateFrom!=null){
				criteria.add(Restrictions.ge("rptDate", dateFrom));
			} else if(dateTo!=null){
				criteria.add(Restrictions.le("rptDate", dateTo));
			}
			return this.findAllForPagination(criteria, pageNo, pageSize, orders);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public List<String> getAllType() {
		List<String> listType = new ArrayList<String>();
		Session session = null;
		try {
			session = getHibernateTemplate().getSessionFactory().openSession();
			StringBuffer sql = new StringBuffer("SELECT DISTINCT r.TYPE FROM EPP_CENTRAL.RPT_STATISTICS_TRANSMIT_DATA r");
			Query query = session.createSQLQuery(sql.toString());
			listType = query.list();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return listType;
	}

	@Override
	public List<RptStatisticsTransmitData> findAllByCondition(String type,
			String printSiteCode, Date fDate, Date tDate) throws Exception {
		List<RptStatisticsTransmitData> list = null;
		try {
			DetachedCriteria criteria = DetachedCriteria
					.forClass(getPersistentClass());
			if (StringUtils.isNotBlank(type) || StringUtils.isNotBlank(printSiteCode) || fDate != null
					|| tDate != null) {
				if (StringUtils.isNotBlank(type)) {
					criteria.add(Restrictions.eq("type", type));
				}
				if (StringUtils.isNotBlank(printSiteCode)) {
					criteria.add(Restrictions.eq("siteCode", printSiteCode));
				}
				if (fDate != null && tDate != null) {
					criteria.add(Restrictions.between("rptDate",
							fDate, tDate));
				} else if (fDate != null) {
					criteria.add(Restrictions.ge("rptDate", fDate));
				} else if (tDate != null) {
					criteria.add(Restrictions.le("rptDate", tDate));
				}
			}
			criteria.addOrder(Order.asc("rptDate"));
			list = this.findAll(criteria);
		} catch (Exception e) {
			throw e;
		}
		return list;
	}
	
}
