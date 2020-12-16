package com.nec.asia.nic.comp.trans.dao.impl;

import java.math.BigDecimal;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.FetchMode;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.nec.asia.nic.comp.trans.dao.NicTransactionPaymentDao;
import com.nec.asia.nic.comp.trans.domain.BaseModelSingle;
import com.nec.asia.nic.comp.trans.domain.NicRegistrationData;
import com.nec.asia.nic.comp.trans.domain.NicTransaction;
import com.nec.asia.nic.comp.trans.domain.NicTransactionPayment;
import com.nec.asia.nic.comp.trans.dto.RICPymtRptDto;
import com.nec.asia.nic.comp.trans.dto.RICWaiverRptDto;
import com.nec.asia.nic.comp.ws.log.domain.CreateMessageException;
import com.nec.asia.nic.framework.PaginatedResult;
import com.nec.asia.nic.framework.dao.impl.GenericHibernateDao;

/*
 * 16 jan 2014 (priya): added ricSiteCheck for getRicPymtRptRecordList,getRicWaiverRptRecordList
 * 10 feb 2014 (priya): changes made in getRicWaiverRptRecordList() 
 */

@Repository("transactionPaymentDao")
public class NicTransactionPaymentDaoImpl extends
		GenericHibernateDao<NicTransactionPayment, String> implements
		NicTransactionPaymentDao {

	@Override
	public NicTransaction getTransactionPayment(NicTransaction transObj) {

		DetachedCriteria detachedCriteria = DetachedCriteria.forClass(getPersistentClass());

		if (StringUtils.isNotBlank(transObj.getTransactionId())) {
			detachedCriteria.add(Restrictions.eq("transactionId", transObj.getTransactionId()))
					.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		}

		List<NicTransactionPayment> resultReg = this.findAll(detachedCriteria);

		if (resultReg.size() > 0) {
			transObj.setNicTransactionPayment(resultReg.get(0));
		}
		return transObj;
	}

	@Override
	public PaginatedResult<RICPymtRptDto> getRicPymtRptRecordList(
			RICPymtRptDto ricPymtRptDto, int pageNo, int pageSize) {
		Format formatter = new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss");
		List<RICPymtRptDto> records = new ArrayList<RICPymtRptDto>();  
		DetachedCriteria criteria = DetachedCriteria
				.forClass(NicTransactionPayment.class);
		criteria.setFetchMode("nicTransaction", FetchMode.JOIN);
		criteria.createAlias("nicTransaction", "t");
		if (ricPymtRptDto.getTxnEndDate() != null) {
			criteria.add(Restrictions.le("paymentTime",
					ricPymtRptDto.getTxnEndDate()));
		}  
		if (ricPymtRptDto.getTxnStartDate() != null) {
			criteria.add(Restrictions.ge("paymentTime",
					ricPymtRptDto.getTxnStartDate()));
		} 
		if (StringUtils.isNotEmpty(ricPymtRptDto.getCollectedBy())) {
			criteria.add(Restrictions.eq("collectionOfficerId",
					ricPymtRptDto.getCollectedBy()));
		}
		if (ricPymtRptDto.getRicOffice() != null
				&& "ALL".equals(ricPymtRptDto.getRicOffice())) {
			criteria.add(Restrictions.like("t.regSiteCode", "RIC",
					MatchMode.START).ignoreCase());
		} else if (ricPymtRptDto.getRicOffice() != null
				&& !"ALL".equals(ricPymtRptDto.getRicOffice())) {
			criteria.add(Restrictions.eq("t.regSiteCode",
					ricPymtRptDto.getRicOffice()));
		} 
		Order orders = Order.asc("t.applnRefNo");
		List<NicTransactionPayment> list = this.findAll(criteria);
		logger.info("DAO Result >>" + list.size());
		if (list != null && list.size() > 0) {
			for (NicTransactionPayment pymnt : list) {
				RICPymtRptDto dto = new RICPymtRptDto();
				dto.setSerialNo(Integer.toString(0));
				dto.setCollectedBy(pymnt.getCollectionOfficerId());
//				dto.setNin(pymnt.getNicTransaction().getNin());
				dto.setPymtNo(pymnt.getPaymentId());
				dto.setTxnNo(pymnt.getTransactionId());
				dto.setTxnType(pymnt.getNicTransaction().getTransactionType());
//				dto.setTxnSubType(pymnt.getNicTransaction()
//						.getTransactionSubtype());
				dto.setPymtAmt(pymnt.getPaymentAmount().toString());
				dto.setPymtStatus(pymnt.getPaymentStatus());
				dto.setPymtDate(formatter.format(pymnt.getPaymentDatetime()));
				DetachedCriteria criteria1 = DetachedCriteria
						.forClass(NicRegistrationData.class);
				criteria1.add(Restrictions.eq("transactionId", pymnt.getTransactionId()));
				logger.info("ricPymtRptDto.getCitizenType() >>"	+ ricPymtRptDto.getCitizenType());
				if (StringUtils.isNotEmpty(ricPymtRptDto.getCitizenType())
						&& ricPymtRptDto.getCitizenType() != "ALL") {
					if(ricPymtRptDto.getCitizenType().equalsIgnoreCase("Y"))
						criteria1.add(Restrictions.eq("seniorCitizenFlag", Boolean.TRUE));
					else{
						criteria1.add(Restrictions.eq("seniorCitizenFlag", Boolean.FALSE));
					}
				}
				@SuppressWarnings("unchecked")
				List<NicRegistrationData>  regData =    (List<NicRegistrationData>) this.getHibernateTemplate().findByCriteria(criteria1); 
				if (regData != null && regData.size()>0) { 
					dto.setFirstName(regData.get(0).getFirstnameFull());
					dto.setSurName(regData.get(0).getSurnameFull());
//					dto.setSurNameBirth(regData.get(0).getSurnameBirthFull());
//					dto.setCitizenType(regData.get(0).getSeniorCitizenFlag() ? "Yes"
//							: "NO");
					records.add(dto); 
				} 
			}
		}   
		PaginatedResult<RICPymtRptDto> result=null; 
		  result = new PaginatedResult<RICPymtRptDto>(
					records.size(), pageNo, records);
			logger.info("result in dao >>" + result.getTotal()); 
		return result;
	}

	@Override
	public PaginatedResult<RICWaiverRptDto> getRicWaiverRptRecordList(
			RICWaiverRptDto ricWaiverRptDto, int pageNo, int pageSize) {
		Format formatter = new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss");
		List<RICWaiverRptDto> records = new ArrayList<RICWaiverRptDto>();  
		DetachedCriteria criteria = DetachedCriteria
				.forClass(NicTransactionPayment.class);
		criteria.setFetchMode("nicTransaction", FetchMode.JOIN);
		criteria.createAlias("nicTransaction", "t");
		if (ricWaiverRptDto.getTxnStartDate() != null) {
			criteria.add(Restrictions.ge("updateDate",
					ricWaiverRptDto.getTxnStartDate()));
		} 
		if (ricWaiverRptDto.getTxnEndDate() != null) {
			criteria.add(Restrictions.le("updateDate",
					ricWaiverRptDto.getTxnEndDate()));
		}   
		if (StringUtils.isNotEmpty(ricWaiverRptDto.getServedBy())) {
			criteria.add(Restrictions.ilike("waiverOfficerId",
					ricWaiverRptDto.getServedBy()));
		} 
		if (ricWaiverRptDto.getRicOffice() != null
				&& "ALL".equals(ricWaiverRptDto.getRicOffice())) {
			criteria.add(Restrictions.like("t.regSiteCode", "RIC",
					MatchMode.START).ignoreCase());
		} else if (ricWaiverRptDto.getRicOffice() != null
				&& !"ALL".equals(ricWaiverRptDto.getRicOffice())) {
			criteria.add(Restrictions.eq("t.regSiteCode",
					ricWaiverRptDto.getRicOffice()));
		}
		if (ricWaiverRptDto.getTxnType() != null && !"ALL".equals(ricWaiverRptDto.getTxnType())) { 
			criteria.add(Restrictions.eq("t.transactionType", ricWaiverRptDto.getTxnType()));
		}
		if (ricWaiverRptDto.getTxnSubType() != null && !"ALL".equals(ricWaiverRptDto.getTxnSubType())) { 
			criteria.add(Restrictions.eq("t.transactionSubtype", ricWaiverRptDto.getTxnSubType()));
		}
		criteria.add(Restrictions.eq("paymentStatus",
				"WAIVED"));
		criteria.addOrder( Order.asc("t.applnRefNo")); 
		List<NicTransactionPayment> list = this.findAll(criteria);
		logger.info("DAO Result >>" + list.size());
		if (list != null && list.size() > 0) {
			for (NicTransactionPayment pymnt : list) {
				RICWaiverRptDto dto = new RICWaiverRptDto(); 
				dto.setSerialNo(Integer.toString(0));
				dto.setWaiverNo(pymnt.getPaymentId());
				dto.setTxnNo(pymnt.getTransactionId());
//				dto.setNin(pymnt.getNicTransaction().getNin());
				dto.setTxnType(pymnt.getNicTransaction().getTransactionType());
//				dto.setTxnSubType(pymnt.getNicTransaction()
//						.getTransactionSubtype());
				
				if(pymnt.getReduceRateAmount()!=null && pymnt.getFeeAmount()!=null){
			    	try{
				    	BigDecimal reduceRateAmt = new BigDecimal(pymnt.getReduceRateAmount().toString());
				    	BigDecimal feeAmt = new BigDecimal(pymnt.getFeeAmount().toString());
				    	BigDecimal waiverAmt = feeAmt.subtract(reduceRateAmt);
				    	dto.setWaiverAmt(waiverAmt.toString());
			    	}catch(Exception e){
			    		e.printStackTrace();
			    	}
			    }  
				if(pymnt.getPaymentDatetime()!=null){
					dto.setWaiverDate(formatter.format(pymnt.getPaymentDatetime()));
				} 
				dto.setWaiverReason(pymnt.getWaiverReason());
				dto.setWaiverOfficer(pymnt.getWaiverOfficerId());
				dto.setServedBy(pymnt.getCollectionOfficerId());
				DetachedCriteria criteria1 = DetachedCriteria
						.forClass(NicRegistrationData.class);
				criteria1.add(Restrictions.eq("transactionId",
						pymnt.getTransactionId()));
				logger.info("ricPymtRptDto.getCitizenType() >>"
						+ ricWaiverRptDto.getCitizenType());
				if (StringUtils.isNotEmpty(ricWaiverRptDto.getCitizenType())
						&& !"ALL".equals(ricWaiverRptDto.getCitizenType())) {
					if(ricWaiverRptDto.getCitizenType().equalsIgnoreCase("Y"))
						criteria1.add(Restrictions.eq("seniorCitizenFlag",
								Boolean.TRUE));
					else{
						criteria1.add(Restrictions.eq("seniorCitizenFlag",
								Boolean.FALSE));
					}
				}
				@SuppressWarnings("unchecked")
				List<NicRegistrationData>  regData =    (List<NicRegistrationData>) this.getHibernateTemplate().findByCriteria(criteria1); 
				if (regData != null && regData.size()>0) { 
					dto.setFirstName(regData.get(0).getFirstnameFull());
					dto.setSurName(regData.get(0).getSurnameFull());
//					dto.setSurNameBirth(regData.get(0).getSurnameBirthFull());
//					dto.setCitizenType(regData.get(0).getSeniorCitizenFlag() ? "Yes"
//							: "NO");
					records.add(dto); 
				} 
			}
		}   
		PaginatedResult<RICWaiverRptDto> result=null; 
		  result = new PaginatedResult<RICWaiverRptDto>(
					records.size(), pageNo, records);
			logger.info("result in dao >>" + result.getTotal());
		return result;
	}

	@Override
	public BaseModelSingle<NicTransactionPayment> findGetPaymentByTransaction(
			String transId) {
		try {
			DetachedCriteria detachedCriteria = DetachedCriteria.forClass(getPersistentClass());			
			if(StringUtils.isNotEmpty(transId)){
				detachedCriteria.add(Restrictions.eq("transactionId", transId));
				List<NicTransactionPayment> list = this.findAll(detachedCriteria);
				if(list != null && list.size() > 0)
					return new BaseModelSingle<NicTransactionPayment>(list.get(0), true, null);
			}
			return new BaseModelSingle<NicTransactionPayment>(null, true, null);
		} catch (Exception e) {
			e.printStackTrace();
			return new BaseModelSingle<NicTransactionPayment>(null, false, CreateMessageException.createMessageException(e) + " - findGetPaymentByTransaction: transactionId: " + transId + " - thất bại.");
		}
	}

	@Override
	public BaseModelSingle<String> saveTranPayment(
			NicTransactionPayment nicTranPay) {
		try {
			String id  = this.save(nicTranPay);
			return new BaseModelSingle<String>(id, true, null);
		} catch (Exception e) {
			return new BaseModelSingle<String>(null, false, CreateMessageException.createMessageException(e) + " - saveTranPayment - " + nicTranPay.getPaymentId() + " - thất bại.");
		}
	}

	@Override
	public void saveOrUpdatePaymentAmount(NicTransactionPayment payments)
			throws Exception {
		try {
			this.saveOrUpdate(payments);
		} catch (Exception e) {
			throw new Exception(CreateMessageException.createMessageException(e) + " - saveOrUpdatePaymentAmount thất bại.");
		}
	} 

}
