package com.nec.asia.nic.comp.trans.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.nec.asia.nic.comp.trans.dao.NicSearchResultDao;
import com.nec.asia.nic.comp.trans.domain.NicSearchResult;
import com.nec.asia.nic.framework.dao.impl.GenericHibernateDao;

/**
 * @author setia_budiyono
 *
 */
@Repository("searchResultDao")
public class NicSearchResultDaoImpl extends
		GenericHibernateDao<NicSearchResult, Long> implements NicSearchResultDao {
	
	public List<NicSearchResult> findAllByJobId(Long jobId) {
		List<NicSearchResult> searchResultList = new ArrayList<NicSearchResult>();
		
		DetachedCriteria detachedCriteria = DetachedCriteria.forClass(getPersistentClass());
		if (jobId!=null) {
			detachedCriteria.add(Restrictions.eq("workflowJobId", jobId));
			List<NicSearchResult> resultList = this.findAll(detachedCriteria);
			searchResultList.addAll(resultList);
		}
		return searchResultList;
	}

	@Override
	public NicSearchResult findLatestResultByJobId(Long jobId, String typeOfSearch) {
		NicSearchResult searchResult = null;
		
		DetachedCriteria detachedCriteria = DetachedCriteria.forClass(getPersistentClass());
		if (jobId!=null) {
			detachedCriteria.add(Restrictions.eq("workflowJobId", jobId));
			if (StringUtils.isNotBlank(typeOfSearch)) {
				detachedCriteria.add(Restrictions.eq("typeSearch", typeOfSearch));
			}
			detachedCriteria.addOrder(Order.desc("searchResultId"));
			List<NicSearchResult> resultList = this.findAll(detachedCriteria);
			if (CollectionUtils.isNotEmpty(resultList)) {
				searchResult = resultList.get(0);
			}
		}
		return searchResult;
	}

	@Override
	public List<NicSearchResult> findSearchResultsByTranIdAndTypeSearch(String transactionId,
			String typeOfSearch) throws Exception {
		List<NicSearchResult> list = null;
		try {
			DetachedCriteria detachedCriteria = DetachedCriteria.forClass(getPersistentClass());
			if (StringUtils.isNotBlank(typeOfSearch)) {
				detachedCriteria.add(Restrictions.eq("typeSearch", typeOfSearch));
			}
			if (StringUtils.isNotBlank(transactionId)) {
				detachedCriteria.add(Restrictions.eq("transactionId", transactionId));
			}
			list = this.findAll(detachedCriteria);
		} catch (Exception e) {
			throw e;
		}
		return list;
	}
	
}
