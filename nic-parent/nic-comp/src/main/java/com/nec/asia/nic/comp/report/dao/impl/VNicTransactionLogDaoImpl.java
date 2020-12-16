/**
 * 
 */
package com.nec.asia.nic.comp.report.dao.impl;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.nec.asia.nic.comp.job.dto.LogInfoDTO;
import com.nec.asia.nic.comp.report.dao.VNicTransactionLogDao;
import com.nec.asia.nic.comp.trans.domain.NicIssuanceData;
import com.nec.asia.nic.comp.trans.domain.NicIssuanceDataId;
import com.nec.asia.nic.comp.trans.domain.VNicTxnLog;
import com.nec.asia.nic.comp.trans.dto.CardStatusRptDTO;
import com.nec.asia.nic.framework.PaginatedResult;
import com.nec.asia.nic.framework.dao.DaoException;
import com.nec.asia.nic.framework.dao.impl.GenericHibernateDao;
import com.nec.asia.nic.util.TransLogInfoXmlConvertor;

/**
 * @author Prasad_Karnati
 *
 */
@Repository("vNicTransactionLogDao")
public class VNicTransactionLogDaoImpl extends GenericHibernateDao<VNicTxnLog, String> implements VNicTransactionLogDao {
	public static TransLogInfoXmlConvertor utils = new TransLogInfoXmlConvertor();

	public List<CardStatusRptDTO> getCardReActRptRecordList(CardStatusRptDTO cardReActSrchCriteria) throws DaoException{
		System.out.println("Entering DAO Result >>");
		LogInfoDTO logDTO = null;
		Format formatter = new SimpleDateFormat("dd-MMM-yyyy");
		List<CardStatusRptDTO> records = new ArrayList<CardStatusRptDTO>();
		DetachedCriteria criteria = DetachedCriteria
				.forClass(VNicTxnLog.class);
		/*criteria.setFetchMode("nicTransaction", FetchMode.JOIN);
		criteria.createAlias("nicTransaction", "t");*/
		if (cardReActSrchCriteria.getCrdCollStartDt() != null) {
			criteria.add(Restrictions.ge("txnUpdateDate",
					cardReActSrchCriteria.getCrdCollStartDt()));
		}
		if (cardReActSrchCriteria.getCrdCollEndDt() != null) {
			criteria.add(Restrictions.le("txnUpdateDate",
					cardReActSrchCriteria.getCrdCollEndDt()));
		}
		if (cardReActSrchCriteria.getRicOffice() != null
				&& "ALL".equals(cardReActSrchCriteria.getRicOffice())) {
			criteria.add(Restrictions.like("regSiteCode", "RIC",
					MatchMode.START));
		} else if (cardReActSrchCriteria.getRicOffice() != null
				&& !"ALL".equals(cardReActSrchCriteria.getRicOffice())) {
			criteria.add(Restrictions.eq("regSiteCode",
					cardReActSrchCriteria.getRicOffice()));
		}
		criteria.add(Restrictions.eq("transactionStatus",
				"RIC_CARD_REACTIVATED"));
		//criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		/*criteria.setProjection(Projections.projectionList()
				.add(Projections.groupProperty("transactionId"))
				.add(Projections.max("logId")));*/
		List<VNicTxnLog> list = this.findAll(criteria);
		System.out.println("Size====>"+list.size());
		if (list != null && list.size() > 0) {
		for (VNicTxnLog data : list) {
				CardStatusRptDTO record = new CardStatusRptDTO();
				record.setNin(data.getNin());
				record.setTransactionNo(data.getTransactionId());
				record.setFirstName(data.getFirstnameFull());
				record.setSurName(data.getSurnameFull());
				record.setSurNameAtBirth(data.getSurnameBirthFull());
				record.setCardStatus(data.getCardStatus());
				record.setCcnNo(data.getCcn());
				record.setReDeActivationDate(formatter.format(data.getTxnUpdateDate()));
				record.setIssuedBy(data.getOfficerId());
				if (data.getLogInfo() != null && list.size() > 0) {
					String logInfo = data.getLogInfo();
					try {
						if (logInfo != null) {
							logDTO = (LogInfoDTO) utils.unmarshal(logInfo);
							record.setRemarks(logDTO.getRemark());
							record.setRejectionReason(logDTO.getReason());
						} else {
							record.setRemarks("");
							record.setRejectionReason("");
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
				}else {
					record.setRemarks("");
					record.setRejectionReason("");
				}
				records.add(record);
			}
		}
		//System.out.println("========================>"+list.size());
		
		return records;
	}
		
	
	
	public List<CardStatusRptDTO> getCardDeActRptRecordList(CardStatusRptDTO cardDeActSrchCriteria) throws DaoException{
	
		System.out.println("Entering DAO Result >>");
		LogInfoDTO logDTO = null;
		Format formatter = new SimpleDateFormat("dd-MMM-yyyy");
		List<CardStatusRptDTO> records = new ArrayList<CardStatusRptDTO>();
		DetachedCriteria criteria = DetachedCriteria
				.forClass(VNicTxnLog.class);
		/*criteria.setFetchMode("nicTransaction", FetchMode.JOIN);
		criteria.createAlias("nicTransaction", "t");*/
		if (cardDeActSrchCriteria.getCrdCollStartDt() != null) {
			criteria.add(Restrictions.ge("txnUpdateDate",
					cardDeActSrchCriteria.getCrdCollStartDt()));
		}
		if (cardDeActSrchCriteria.getCrdCollEndDt() != null) {
			criteria.add(Restrictions.le("txnUpdateDate",
					cardDeActSrchCriteria.getCrdCollEndDt()));
		}
		if (cardDeActSrchCriteria.getRicOffice() != null
				&& "ALL".equals(cardDeActSrchCriteria.getRicOffice())) {
			criteria.add(Restrictions.like("regSiteCode", "RIC",
					MatchMode.START));
		} else if (cardDeActSrchCriteria.getRicOffice() != null
				&& !"ALL".equals(cardDeActSrchCriteria.getRicOffice())) {
			criteria.add(Restrictions.eq("regSiteCode",
					cardDeActSrchCriteria.getRicOffice()));
		}
		criteria.add(Restrictions.eq("transactionStatus",
				"RIC_CARD_DEACTIVATED"));
		//criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		/*criteria.setProjection(Projections.projectionList()
				.add(Projections.groupProperty("transactionId"))
				.add(Projections.max("logId")));*/
		List<VNicTxnLog> list = this.findAll(criteria);
		System.out.println("Size ======>"+list.size());
		if (list != null && list.size() > 0) {
		for (VNicTxnLog data : list) {
				CardStatusRptDTO record = new CardStatusRptDTO();
				record.setNin(data.getNin());
				record.setTransactionNo(data.getTransactionId());
				record.setFirstName(data.getFirstnameFull());
				record.setSurName(data.getSurnameFull());
				record.setSurNameAtBirth(data.getSurnameBirthFull());
				record.setCardStatus(data.getCardStatus());
				record.setCcnNo(data.getCcn());
				record.setReDeActivationDate(formatter.format(data.getTxnUpdateDate()));
				record.setIssuedBy(data.getOfficerId());
				if (data.getLogInfo() != null && list.size() > 0) {
					String logInfo = data.getLogInfo();
					try {
						if (logInfo != null) {
							logDTO = (LogInfoDTO) utils.unmarshal(logInfo);
							record.setRemarks(logDTO.getRemark());
							record.setRejectionReason(logDTO.getReason());
						} else {
							record.setRemarks("");
							record.setRejectionReason("");
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
				}else {
					record.setRemarks("");
					record.setRejectionReason("");
				}
				records.add(record);
			}
		}
		//System.out.println("========================>"+list.size());
		
		return records;
	}

	

}
