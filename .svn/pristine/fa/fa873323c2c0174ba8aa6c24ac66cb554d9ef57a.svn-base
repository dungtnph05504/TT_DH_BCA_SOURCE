package com.nec.asia.nic.comp.trans.dao.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Conjunction;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.nec.asia.nic.comp.job.dto.LogInfoDTO;
import com.nec.asia.nic.comp.trans.dao.NicTransactionLogDao;
import com.nec.asia.nic.comp.trans.domain.BaseModelList;
import com.nec.asia.nic.comp.trans.domain.BaseModelSingle;
import com.nec.asia.nic.comp.trans.domain.NicRegistrationData;
import com.nec.asia.nic.comp.trans.domain.NicTransaction;
import com.nec.asia.nic.comp.trans.domain.NicTransactionLog;
import com.nec.asia.nic.comp.trans.dto.ExceptionHandlngRptDto;
import com.nec.asia.nic.comp.trans.service.exception.NicUploadJobServiceException;
import com.nec.asia.nic.comp.ws.log.domain.CreateMessageException;
import com.nec.asia.nic.framework.PaginatedResult;
import com.nec.asia.nic.framework.dao.impl.GenericHibernateDao;
import com.nec.asia.nic.util.TransLogInfoXmlConvertor;

/*
 * Modification History
 * 
 * 30 Sept 2013 :(Sailaja): Added the editRemarksData method to edit the remarks.
 * 06 Dec 2013 : (Sailaja): Added the findAllLogInfo to display in Perso tab.
 * 10 Dec 2013 : (Sailaja): Added the persoEditRemarksData method to show additional Information
 * 17 Dec 2013 : (chris) : Temporary fix for null pointer.
 * 06 Jan 2014 : (Sailaja) : Added findAllForPagination method to show fps issuance
 * 07 Jan 2013 : (chris) : rename function - editRemarksData() to findRemarksByTxnIdNStatus()
 * 10 Feb 2014 : (chris) : added getExceptionHandlingRptRecordList
 * 09 Apr 2014 : (chris) : update function - findAllForPagination()
 * 21 May 2014 : (Peddi Swapna): Added getLatestErrorMessage method.
 * 09 Apr 2014 : (chris) : update function - findAllForPagination() to return only if logData is not empty and contain FPData
 * 09 May 2016 : (khang) : add method addTransactionLog(List<NicTransactionLog>)
 */

@Repository("transactionLogDao")
public class NicTransactionLogDaoImpl extends
		GenericHibernateDao<NicTransactionLog, Long> implements
		NicTransactionLogDao {
	
	public static TransLogInfoXmlConvertor util = new TransLogInfoXmlConvertor();

	@Override
	public BaseModelList<NicTransactionLog> findAllByRefIdAndTransStateList(
			String refId, List<String> transactionStates) {
		try {
			List<NicTransactionLog> nicTransactionLogList = new ArrayList<NicTransactionLog>();
			DetachedCriteria detachedCriteria = DetachedCriteria.forClass(getPersistentClass());
			if (StringUtils.isNotBlank(refId)) {
				detachedCriteria.add(Restrictions.eq("refId", refId));
			}
			if (CollectionUtils.isNotEmpty(transactionStates)) {
				detachedCriteria.add(Restrictions.in("transactionStage", transactionStates));
			}
			detachedCriteria.addOrder(Order.desc("logId"));
			List<NicTransactionLog> resultList = this.findAll(detachedCriteria);
			nicTransactionLogList.addAll(resultList);
			return new BaseModelList<NicTransactionLog>(nicTransactionLogList, true, null);
		} catch (Exception e) {
			return new BaseModelList<NicTransactionLog>(null, false, e.getMessage() + "\n [subtrack] \n" + e.getCause().getMessage() + "\n [subtrack] \n" + e.getCause().getCause().getMessage() + " - findAllByRefIdAndTransStateList - " + refId + " - thất bại.");
		}
		
	}

	/**
	 * @deprecated
	 */
	@Override
	public String updateRemarks(String refId, String remarksData) {
		logger.info("Inside Dao================>>>>>>>>>>>>>>>");
		String status = "";
		DetachedCriteria criteria = DetachedCriteria.forClass(NicTransactionLog.class);
		criteria.add(Restrictions.eq("refId", refId));
		criteria.addOrder(Order.desc("logId"));
		List<NicTransactionLog> list = (List<NicTransactionLog>) getHibernateTemplate().findByCriteria(criteria);
		try {
			NicTransactionLog updateObj = list.get(0);
			updateObj.setLogData(remarksData);
			saveOrUpdate(updateObj);
			status = "success";
//		   for(NicTransactionLog updateObj : list) {
//		   updateObj.setLogData(remarksData);
//		   saveOrUpdate(updateObj);
//		   status = "success";
//			}	
				//create a new method
				/*NicTransactionLog nicTransactionLog = new NicTransactionLog();
				nicTransactionLog.setRefId(refId);
				nicTransactionLog.setLogCreateTime(new Date());
				nicTransactionLog.setLogData(remarksData);*/
				//nicTransactionLog.set
				
			} catch (Exception e) {
				status = "error";
				e.printStackTrace();
			}
			return status;	
	}
	
	@Override
	public NicTransactionLog getNicTransactionLog(String refId, String transactionStage,String transactionStatus, String userId, String wkstnId) throws NicUploadJobServiceException {
		logger.info("Inside Dao============>>>>>> get the list");
		return null;
	}


	@Override
	public void insertRejectReasonRemarks(long rejectJobId,
			String rejectedReason, String rejectedRemarks, String userId,
			String wkstnId, String transactionId) throws Exception {
		NicTransactionLog nicTransactionLog = new NicTransactionLog();
		LogInfoDTO logInfoDTO = new LogInfoDTO();
		//String remarksData = (String) util.marshal(rejectedReason,rejectedRemarks);
		logInfoDTO.setReason(rejectedReason);
		logInfoDTO.setRemark(rejectedRemarks);
		String logInfoXml  = util.marshal(logInfoDTO);
		nicTransactionLog.setRefId(transactionId);
		nicTransactionLog.setLogCreateTime(new Date());
		nicTransactionLog.setTransactionStage(NicTransactionLog.TRANSACTION_STAGE_INVESTIGATION);//"INVESTIGATION");
		nicTransactionLog.setTransactionStatus(NicTransactionLog.TRANSACTION_STATUS_NIC_REJECTED);//"NIC_REJECTED");
		nicTransactionLog.setWkstnId(wkstnId);
		nicTransactionLog.setOfficerId(userId);
		nicTransactionLog.setSiteCode("NICDC");
		nicTransactionLog.setLogInfo(logInfoXml);
	    save(nicTransactionLog);
	}


	
	@Override
	public String findRemarksByTxnIdNStatus(String refId, String transactionStage, String transactionStatus) throws Exception {
		logger.debug("Inside the Dao====================findRemarksByTxnIdNStatus");
		LogInfoDTO logInfoDTO = null;
		
		DetachedCriteria criteria = DetachedCriteria.forClass(NicTransactionLog.class);
		criteria.add(Restrictions.eq("refId", refId));
		criteria.add(Restrictions.eq("transactionStage",transactionStage));
		criteria.add(Restrictions.eq("transactionStatus",transactionStatus));

		List<NicTransactionLog> logData = findAll(criteria);
		if(logData!=null && logData.size()>0){
			String logInfoXml = logData.get(0).getLogInfo();
			if (StringUtils.isNotBlank(logInfoXml)) {
				logInfoDTO = (LogInfoDTO) util.unmarshal(logInfoXml);
				String remarksData = logInfoDTO.getRemark();
				return remarksData;
			}
		} 
		return null;
	}


	@Override
	public List<NicTransactionLog> findAllLogInfo(String refId,
			String transactionStage, String transactionStatus) throws Exception {
		logger.debug("Inside the Dao====================findAllLogInfo");
		List<NicTransactionLog> nicTransactionLogList = new ArrayList<NicTransactionLog>();
		DetachedCriteria criteria = DetachedCriteria.forClass(NicTransactionLog.class);
		criteria.add(Restrictions.eq("refId", refId));
		criteria.add(Restrictions.eq("transactionStage", transactionStage));
		criteria.add(Restrictions.eq("transactionStatus", transactionStatus));
		criteria.addOrder(Order.desc("logCreateTime"));
		List<NicTransactionLog> resultList = this.findAll(criteria);
		nicTransactionLogList.addAll(resultList);
		return nicTransactionLogList;
	}


	@Override
	public String findAllPersoRemarks(String transactionId,
			String transactionStage, String transactionStatus) throws Exception {
		logger.debug("Indside the Dao ==========Additional Perso Information=============");
		LogInfoDTO logInfoDTO = null;
		//NicTransactionLog nicTransactionLog = new NicTransactionLog();
		DetachedCriteria criteria = DetachedCriteria.forClass(NicTransactionLog.class);
		criteria.add(Restrictions.eq("refId", transactionId));
		criteria.add(Restrictions.eq("transactionStage",transactionStage));
		criteria.add(Restrictions.eq("transactionStatus",transactionStatus));
		// criteria.setProjection(Projections.property("logData"));
		List<NicTransactionLog> logData = findAll(criteria);
		if(logData!=null && logData.size()>0){
			String persoRemarks = logData.get(0).getLogInfo();
			if (StringUtils.isNotBlank(persoRemarks)) {
				logInfoDTO = (LogInfoDTO) util.unmarshal(persoRemarks);
				String persoRemarksData = logInfoDTO.getRemark();
				return persoRemarksData;
			}
		}
		return null;
	}

	@Override
	public PaginatedResult<NicTransactionLog> findAllForPagination(int currentPage,
			int pageSize, String siteCode, String officerId, String transactionStage, String transactionStatus) throws Exception {
		try {
			DetachedCriteria criteria = DetachedCriteria.forClass(NicTransactionLog.class);
			if (StringUtils.isNotEmpty(transactionStage)) {
				criteria.add(Restrictions.eq("transactionStage", transactionStage));
				//fix for issuance FP not shown
				if (StringUtils.equals(transactionStage, NicTransactionLog.TRANSACTION_STAGE_RIC_ISSUANCE)) {
					criteria.add(Restrictions.like("logData", "FingerprintData", MatchMode.ANYWHERE));
				}
			}
			if (StringUtils.isNotEmpty(transactionStatus))
				criteria.add(Restrictions.eq("transactionStatus", transactionStatus));
			if (StringUtils.isNotEmpty(siteCode)) {
				criteria.add(Restrictions.like("siteCode", siteCode, MatchMode.ANYWHERE).ignoreCase());
			}
			if (StringUtils.isNotEmpty(officerId)) {
				criteria.add(Restrictions.like("officerId", officerId, MatchMode.ANYWHERE).ignoreCase());
			}

			return findAllForPagination(criteria, currentPage, pageSize);
		} catch (Exception ex) {
			logger.error("Error occurred while getting the NIC FP Issuance Data. Exception:" + ex.getMessage());
			return null;
		}
	}
	
	@Override
	public List<NicTransactionLog> findAllByNinWithExcludedTransactions(
			String nin, List<String> excludedTransactionIdList) {
		List<NicTransactionLog> nicTransactionLogList = new ArrayList<NicTransactionLog>();
		DetachedCriteria detachedCriteria = DetachedCriteria.forClass(getPersistentClass());
		if (StringUtils.isNotBlank(nin)) {
			detachedCriteria.add(Restrictions.eq("nin", nin));
		}
		if (CollectionUtils.isNotEmpty(excludedTransactionIdList)) {
			//detachedCriteria.add(Restrictions.not(Restrictions.in("refId", excludedTransactionIdList)));
			
			Conjunction conjunction=Restrictions.conjunction();
			if(excludedTransactionIdList.size()>0){
				Conjunction disjunctionIdListCount = Restrictions.conjunction();
				
				for(int i=0;i<(((excludedTransactionIdList.size()/1000)+1)); i++){
					Criterion list=null;
					if(excludedTransactionIdList.size()>((i+1)*1000)){
						list = Restrictions.not(Restrictions.in("refId",Arrays.copyOfRange(excludedTransactionIdList.toArray(), (i*1000), ((i+1)*1000))));
					}else{
						list = Restrictions.not(Restrictions.in("refId",Arrays.copyOfRange(excludedTransactionIdList.toArray(), (i*1000), excludedTransactionIdList.size())));
					}
					
					disjunctionIdListCount.add(list);
				}
				

				conjunction.add(disjunctionIdListCount);
			}
			detachedCriteria.add(conjunction);
			
		}
		List<NicTransactionLog> resultList = this.findAll(detachedCriteria);
		nicTransactionLogList.addAll(resultList);
		return nicTransactionLogList;
	}

	@Override
	public PaginatedResult<NicTransactionLog> getExceptionHandlingRptRecordList(
			ExceptionHandlngRptDto excepHandlingRptSrchCriteria, int pageNo,
			int pageSize) throws Exception { 
		LogInfoDTO logDTO = null; 
		DetachedCriteria criteria = DetachedCriteria.forClass(NicTransactionLog.class); 
		if (excepHandlingRptSrchCriteria.getFwdStartDt() != null) {
			criteria.add(Restrictions.ge("startTime", excepHandlingRptSrchCriteria.getFwdStartDt()));
		}
		if (excepHandlingRptSrchCriteria.getFwdEndDt() != null) {
			criteria.add(Restrictions.le("startTime", excepHandlingRptSrchCriteria.getFwdEndDt()));
		}
		if (excepHandlingRptSrchCriteria.getResStartDt() != null) {
			criteria.add(Restrictions.ge("endTime", excepHandlingRptSrchCriteria.getResStartDt()));
		}
		if (excepHandlingRptSrchCriteria.getResEndDt() != null) {
			criteria.add(Restrictions.le("endTime", excepHandlingRptSrchCriteria.getResEndDt()));
		}
		if (excepHandlingRptSrchCriteria.getRicOffice() != null
				&& "ALL".equals(excepHandlingRptSrchCriteria.getRicOffice())) {
			criteria.add(Restrictions.like("siteCode", "RIC",
					MatchMode.START).ignoreCase());
		} else if (excepHandlingRptSrchCriteria.getRicOffice() != null
				&& !"ALL".equals(excepHandlingRptSrchCriteria.getRicOffice())) {
			criteria.add(Restrictions.eq("siteCode",
					excepHandlingRptSrchCriteria.getRicOffice()));
		} 
		if(excepHandlingRptSrchCriteria.getReason()!=null && !"ALL".equals(excepHandlingRptSrchCriteria.getReason())){
			criteria.add(Restrictions
					.sqlRestriction("extractvalue(sys.xmltype(log_info), '//logInfoDTO/Reason/text()') ='"+excepHandlingRptSrchCriteria.getReason()+"'"));
		}
		criteria.setProjection(Projections.projectionList()
				.add(Projections.groupProperty("refId"))
				.add(Projections.max("logId")));
		criteria.add(Restrictions
				.sqlRestriction("extractvalue(sys.xmltype(log_info), '//logInfoDTO/Reason/text()') is not null")); 
		criteria.add(Restrictions.isNotNull("logInfo")); 
		criteria.add(Restrictions.eq("transactionStage", "EXC_HANDLING"));
		PaginatedResult<NicTransactionLog>  logInfoData =    this.findAllForPagination(criteria, pageNo, pageSize, Order.asc("refId"));  
		return  logInfoData; 
	}

	@Override
	public NicTransactionLog getLatestErrorMessage(String transactionId) throws Exception {
		try {
			DetachedCriteria criteria = DetachedCriteria.forClass(NicTransactionLog.class);
			if (StringUtils.isNotEmpty(transactionId))
				criteria.add(Restrictions.eq("refId", transactionId));
			
			criteria.add(Restrictions.like("transactionStatus", "%_ERROR"));
			
			criteria.addOrder(Order.desc("logCreateTime"));
			
			List<NicTransactionLog> logData = findAll(criteria);
			
			if (CollectionUtils.isNotEmpty(logData)) {
				return logData.get(0);
			}else{
				return null;
			}
			
		} catch (Exception ex) {
			logger.error("Error occurred while getting the NIC FP Issuance Data. Exception:" + ex.getMessage());
			return null;
		}
	}
	
	@Override
    public NicTransactionLog getLatestTransactionLog(String transactionId) throws Exception {
        try {
            DetachedCriteria criteria = DetachedCriteria.forClass(NicTransactionLog.class);
            if (StringUtils.isNotEmpty(transactionId))
                criteria.add(Restrictions.eq("refId", transactionId));
            criteria.addOrder(Order.desc("logCreateTime"));
            
            List<NicTransactionLog> logData = findAll(criteria);
            
            if (CollectionUtils.isNotEmpty(logData)) {
                return logData.get(0);
            }else{
                return null;
            }
            
        } catch (Exception ex) {
            logger.error("Error occurred while getting the Epp Transaction Log. Exception:" + ex.getMessage());
            return null;
        }
    }
	
	@Override
	public NicTransactionLog getLatestTransactionLog(String transactionId, String transactionStage) throws Exception {
	    try {
	        DetachedCriteria criteria = DetachedCriteria.forClass(NicTransactionLog.class);
	        if (StringUtils.isNotEmpty(transactionId))
	            criteria.add(Restrictions.eq("refId", transactionId));
	        if (StringUtils.isNotBlank(transactionStage)) 
	            criteria.add(Restrictions.eq("transactionStage", transactionStage));
	        
	        criteria.addOrder(Order.desc("startTime"));
	    
	        List<NicTransactionLog> logData = findAll(criteria);
	        
	        if (CollectionUtils.isNotEmpty(logData)) {
	            return logData.get(0);
	        }else{
	            return null;
	        }
	        
	    } catch (Exception ex) {
	        logger.error("Error occurred while getting the Epp Transaction Log. Exception:" + ex.getMessage());
	        return null;
	    }
	}
	
	@Override
	public NicTransactionLog getLatestTransactionLog(String transactionId, String transactionStage, String transactionStatus) throws Exception {
	    try {
	        DetachedCriteria criteria = DetachedCriteria.forClass(NicTransactionLog.class);
	        if (StringUtils.isNotEmpty(transactionId))
	            criteria.add(Restrictions.eq("refId", transactionId));
	        if (StringUtils.isNotBlank(transactionStage)) 
	            criteria.add(Restrictions.eq("transactionStage", transactionStage));
	        if (StringUtils.isNotBlank(transactionStatus))
	        	criteria.add(Restrictions.eq("transactionStatus", transactionStatus));
	        
	        criteria.addOrder(Order.desc("logCreateTime"));
	    
	        List<NicTransactionLog> logData = findAll(criteria);
	        
	        if (CollectionUtils.isNotEmpty(logData)) {
	            return logData.get(0);
	        }else{
	            return null;
	        }
	        
	    } catch (Exception ex) {
	        logger.error("Error occurred while getting the Epp Transaction Log. Exception:" + ex.getMessage());
	        return null;
	    }
	}

	@Override
	public int addTransactionLog(List<NicTransactionLog> transactionLogList) throws Exception {
		Session session = null;
		int count = 0;
		try {
			session = this.openSession();
			Transaction tx = session.beginTransaction();
			for (NicTransactionLog transactionLog : transactionLogList) {
				session.save(transactionLog);
				if (++count % 50 == 0) {
					session.flush();
					session.clear();
				}
			}
			tx.commit();
		} catch (HibernateException ex) {			
			throw new Exception(ex);
		} finally {
			session.close();
		}
		return count;
	}
	
	@Override
	public PaginatedResult<NicTransactionLog> findAllForPagination(NicTransactionLog nicTransactionLog, int pageNo, int pageSize, boolean ignoreCase, boolean enableLike, Order order) {
		DetachedCriteria tCriteria = DetachedCriteria.forClass(NicTransactionLog.class);
		if (nicTransactionLog != null && StringUtils.isNotEmpty(nicTransactionLog.getRefId())) {
			tCriteria.add(Restrictions.eq("refId", nicTransactionLog.getRefId()));
		}

		return findAllForPagination(tCriteria, pageNo, pageSize, order);
	}

	@Override
	public List<NicTransactionLog> findAllLogByStatus(String refId,
			String transactionStatus) throws Exception {
		logger.debug("Inside the Dao====================findAllLogInfo");
		List<NicTransactionLog> nicTransactionLogList = new ArrayList<NicTransactionLog>();
		DetachedCriteria criteria = DetachedCriteria.forClass(NicTransactionLog.class);
		criteria.add(Restrictions.eq("refId", refId));
		criteria.add(Restrictions.eq("transactionStatus", transactionStatus));
		criteria.addOrder(Order.desc("logCreateTime"));
		List<NicTransactionLog> resultList = this.findAll(criteria);
		nicTransactionLogList.addAll(resultList);
		return nicTransactionLogList;
	}

	@Override
	public List<NicTransactionLog> findForListLog(
			NicTransactionLog nicTransactionLog, int pageNo, int pageSize,
			boolean ignoreCase, boolean enableLike, Order order) {
		try {
			DetachedCriteria tCriteria = DetachedCriteria.forClass(NicTransactionLog.class);
			if (nicTransactionLog != null && StringUtils.isNotEmpty(nicTransactionLog.getRefId())) {
				tCriteria.add(Restrictions.eq("refId", nicTransactionLog.getRefId()));
			}
			tCriteria.addOrder(order);
			return findAll(tCriteria);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public BaseModelSingle<Boolean> saveTransactionLog(
			NicTransactionLog nicTransactionLog) {
		try {
			this.save(nicTransactionLog);
			return new BaseModelSingle<Boolean>(true, true, null);
		} catch (Exception e) {
			return new BaseModelSingle<Boolean>(false, false, CreateMessageException.createMessageException(e) + " - saveTransactionLog - thất bại.");
		}
	}
}


