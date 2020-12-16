package com.nec.asia.nic.comp.trans.dao.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Conjunction;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Disjunction;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.nec.asia.nic.comp.trans.dao.NicRejectionDataDao;
import com.nec.asia.nic.comp.trans.domain.NicDocumentData;
import com.nec.asia.nic.comp.trans.domain.NicRegistrationData;
import com.nec.asia.nic.comp.trans.domain.NicRejectionData;
import com.nec.asia.nic.framework.dao.DaoException;
import com.nec.asia.nic.framework.dao.impl.GenericHibernateDao;
import com.nec.asia.nic.utils.ExceptionMessageFormatter;

/**
 * @author sailaja_chinapothula
 * @Company: NEC Asia Pacific Ltd
 * @Since: Aug 7, 2013
 */
/* 
 * Modification History:
 *  
 * 09 Oct 2013 (chris): to handle empty transaction id list.
 * 02 Dec 2013 (Sailaja): Added cancelTransaction method to cancel the transaction.
 */
@Repository("nicRejectionDataDao")
public class NicRejectionDataDaoImpl extends
		GenericHibernateDao<NicRejectionData, Long> 
		implements NicRejectionDataDao {

	@Override
	public void updateRejectReasonRemarks(long rejectJobId,
			String rejectedReason, String rejectedRemarks, String userId,
			String wkstnId,String transactionId, String investigationType)
			throws Exception {

	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<NicRejectionData> findByPeriod(String siteCode, Date start,
			Date end) throws DaoException {
		List<NicRejectionData> resultList = null;
		if (StringUtils.isBlank(siteCode)) {
			throw new DaoException("site code cannot be empty.");
		}
		
		/*
		 * for hibernateTemplate.find() to work with parameters.
		 * 
		 * Object[] params = new Object[]{object1,object2};
		 * getHibernateTemplate().find("select r from Table r where r.field=? and r.field2=?",params);
		 */
		String hql = "from NicRejectionData a where exists (select 1 from NicTransaction b where b.transactionId=a.transactionId and b.issSiteCode = ?) ";
				//"and a.rejectDate >= ? and a.rejectDate <= ?";
			//"from NicRejectionData a where a.rejectDate >= :startDate and a.rejectDate <= :endDate and exists (select 1 from NicTransaction b where b.transactionId=a.transactionId and b.issSiteCode = :siteCode) ";
		try {
			List<Object> parameterList = new ArrayList<Object>();
			parameterList.add(siteCode);
			//to set dataDownloadedFlag
			//hql += " and a.dataDownloadedFlag = ?";
			//parameterList.add(false);
			if (start!=null) {
				hql += " and a.rejectDate >= ?";
				parameterList.add(start);
			}
			if (end!=null) {
				hql += " and a.rejectDate <= ?";
				parameterList.add(end);
			}
			Object[] parameters = parameterList.toArray(); //new Object[] {start, end, siteCode};
			resultList = //this.findAll();//this.findAllByHql(hql, parameters); 
				(List<NicRejectionData>) this.getHibernateTemplate().find(hql, parameters);
				//this.getSession().createQuery(hql)
				//	.setString("siteCode", siteCode).setDate("startDate", start).setDate("endDate", end).list();

		} catch (HibernateException ex) {
			throw new DaoException(ex);
		}
		return resultList;
	}
	
	public void updateDataReceivedByTransactionIdList(List<String> transactionIdList, String officerId, String wkstnId) throws DaoException {
		if (CollectionUtils.isEmpty(transactionIdList)) {
			//throw new DaoException("transaction id cannot be empty.");
			return; //09 Oct 2013 (chris): to handle empty transaction id list.
		}
		DetachedCriteria detachedCriteria = DetachedCriteria.forClass(getPersistentClass());
//		detachedCriteria.add(Restrictions.in("transactionId", transactionIdList));
		
		Conjunction conjunction=Restrictions.conjunction();
		if(transactionIdList.size()>0){
			Disjunction disjunctionIdListCount = Restrictions.disjunction();
			
			for(int i=0;i<(((transactionIdList.size()/1000)+1)); i++){
				Criterion list=null;
				if(transactionIdList.size()>((i+1)*1000)){
					list = Restrictions.in("transactionId",Arrays.copyOfRange(transactionIdList.toArray(), (i*1000), ((i+1)*1000)));
				}else{
					list = Restrictions.in("transactionId",Arrays.copyOfRange(transactionIdList.toArray(), (i*1000), transactionIdList.size()));
				}
				
				disjunctionIdListCount.add(list);
			}
			
			conjunction.add(disjunctionIdListCount);
		}
		detachedCriteria.add(conjunction);
		
		//detachedCriteria.add(Restrictions.eq("dataDownloadedFlag", false));
//		List<NicRejectionData> resultList = this.findAll(detachedCriteria);
//		for (NicRejectionData rejectionDataDBO: resultList) {
//			rejectionDataDBO.setDataDownloadedFlag(true);
//			rejectionDataDBO.setUpdateBy(officerId);
//			rejectionDataDBO.setUpdateWkstnId(wkstnId);
//			rejectionDataDBO.setUpdateDate(new Date());
//			this.saveOrUpdate(rejectionDataDBO);
//		}
	}
	
	public void cancelTransaction(Long jobId, String cancelReason,
			String userId, String wkstnId, String transactionId)throws DaoException{
//         NicRejectionData entityObj = findById( jobId);
//		if(entityObj!=null){
//			if(cancelReason!=null){
//			entityObj.setRejectReason(cancelReason);
//			entityObj.setRejectRemarks(cancelReason);
//			}
//			entityObj.setUpdateBy(userId);
//			entityObj.setUpdateWkstnId(wkstnId);
//			entityObj.setUpdateDate(new Date());
//			entityObj.setRejectionType("OTHERS");
//			saveOrUpdate(entityObj);
//		}else{
//			NicRejectionData nicRejectionData = new NicRejectionData();
//			nicRejectionData.setUploadJobId(jobId);
//			if(StringUtils.isNotBlank(transactionId)){
//			nicRejectionData.setTransactionId(transactionId);
//			}
//			nicRejectionData.setCreateBy(userId);
//			nicRejectionData.setCreateWkstnId(wkstnId);
//			nicRejectionData.setCreateDate(new Date());
//			nicRejectionData.setRejectDate(new Date());
//			if(cancelReason!=null){
//			nicRejectionData.setRejectReason(cancelReason);
//			nicRejectionData.setRejectRemarks(cancelReason);
//			}
//			nicRejectionData.setRejectOfficerId(userId);
//			nicRejectionData.setRejectWkstnId(wkstnId);
//			nicRejectionData.setRejectionType("OTHERS");
//			save(nicRejectionData);			
//	    }
	}

	@Override
	public NicRejectionData findRejectInfo(long jobId) throws DaoException {
		NicRejectionData nicRejectionData = new NicRejectionData();
		try{
			nicRejectionData =  this.findById(jobId);
		}catch (HibernateException ex) {
			throw new DaoException(ex);
		}
		return nicRejectionData;
		
	}
	
	public NicRejectionData findByTransactionId(String transactionId) throws DaoException {
		NicRejectionData nicRejectionDatDBO = null;
		DetachedCriteria detachedCriteria = DetachedCriteria.forClass(getPersistentClass());
		if (StringUtils.isNotBlank(transactionId)) {
			detachedCriteria.add(Restrictions.eq("transactionId", transactionId));
			
			List<NicRejectionData> resultList = this.findAll(detachedCriteria);
			if (CollectionUtils.isNotEmpty(resultList)) {
				nicRejectionDatDBO = resultList.get(0);
			}
		}
		return nicRejectionDatDBO;
	}
	
	@Override
	public List<NicRejectionData> findAllRejectionDataForSync(String siteCode) throws DaoException {
		try {
			List<NicRejectionData> results = null;
			results = (List<NicRejectionData>) this.getHibernateTemplate().findByNamedQuery("findUnSyncRejectionDataBySiteCode", siteCode);
			return results;
		} catch (Exception e) {
			throw new DaoException(ExceptionMessageFormatter.format(e));
		} 
	}
	
	@Override
	public void updateSyncStatus(List<String> transactionIdList) throws DaoException {
		Session session = null;
		int updateCount = 0;
		try {
			//this.getHibernateTemplate().bulkUpdate(queryString, values)
			session = this.openSession();
			Query query = session.getNamedQuery("updateRejectionSyncStatusByTransactionIdList");
			query.setParameterList("transactionIdList", transactionIdList);
			updateCount = query.executeUpdate();
			logger.info("[updateSyncStatus] update result returned for transactionIdList: {}, updateCount: {}", transactionIdList, updateCount);
		} catch (Exception e) {
			throw new DaoException(ExceptionMessageFormatter.format(e));
		} finally {
			session.close();
		}
	}
}
