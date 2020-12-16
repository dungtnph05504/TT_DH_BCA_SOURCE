package com.nec.asia.nic.comp.trans.dao.impl;

import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.nec.asia.nic.comp.trans.dao.NicTransactionAttachmentDao;
import com.nec.asia.nic.comp.trans.domain.BaseModelList;
import com.nec.asia.nic.comp.trans.domain.BaseModelSingle;
import com.nec.asia.nic.comp.trans.domain.NicTransaction;
import com.nec.asia.nic.comp.trans.domain.NicTransactionAttachment;
import com.nec.asia.nic.comp.ws.log.domain.CreateMessageException;
import com.nec.asia.nic.framework.dao.impl.GenericHibernateDao;
/*
 * Modification History:
 * 
 * 19 Jan 2016 (chris): update condition for dao.findNicTransactionAttachments(transactionId, docType, serialNo)
 * 
 */
@Repository("transactionDocumentDao")
public class NicTransactionAttachmentDaoImpl extends
		GenericHibernateDao<NicTransactionAttachment, Long> implements NicTransactionAttachmentDao{
	
	@Override
	public NicTransaction getTransactiondocuments(NicTransaction transObj) {
		DetachedCriteria detachedCriteria = DetachedCriteria.forClass(getPersistentClass());
		
		if (StringUtils.isNotBlank(transObj.getTransactionId())) {
			detachedCriteria.add(Restrictions.eq("transactionId", transObj.getTransactionId()))
			.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		}
		
		List<NicTransactionAttachment> resultList = this.findAll(detachedCriteria);
		
		Set<NicTransactionAttachment> setTransDocs = new HashSet<NicTransactionAttachment>(resultList);
		transObj.setNicTransactionAttachments(setTransDocs);
		return transObj;
	}
	@Override
	public BaseModelList<NicTransactionAttachment> findNicTransactionAttachments(String transactionId, String docType, String serialNo) {
		try {
			DetachedCriteria detachedCriteria = DetachedCriteria.forClass(getPersistentClass());
			
			if (StringUtils.isNotBlank(transactionId)) {
				detachedCriteria.add(Restrictions.eq("transactionId",transactionId));
			}
			if (StringUtils.isNotBlank(docType)) {
				detachedCriteria.add(Restrictions.eq("docType",docType));
			}
			if (StringUtils.isNotBlank(serialNo)) {
				detachedCriteria.add(Restrictions.eq("serialNo",serialNo));
			}
			
			List<NicTransactionAttachment> resultList = this.findAll(detachedCriteria);
			return new BaseModelList<NicTransactionAttachment>(resultList, true, null);
		} catch (Exception e) {
			return new BaseModelList<NicTransactionAttachment>(null, false, CreateMessageException.createMessageException(e) + " - findNicTransactionAttachments - thất bại.");
		}
	}

	@Override
	public List<Object[]> getNicTransactionAttachmentByDocType(String docType,
			String regSiteCode, String transactionId, String surnameFull, String status,
			String surnameAtBirth, String otherName, String gender, Date dateOfBirth) {
		List<Object[]> resultList = null;
		Session session  = null;
		try {
			session  = getHibernateTemplate().getSessionFactory().openSession();
			Query q = session.getNamedQuery("getNicTransactionAttachmentByDocType");
			q.setParameter("docType", docType);
			q.setParameter("regSiteCode", regSiteCode);
			q.setParameter("transactionId", transactionId);
			q.setParameter("surnameFull", surnameFull);
			q.setParameter("status", status);
			//q.setParameter("surnameAtBirth", surnameAtBirth);
			//q.setParameter("otherName", otherName);
			q.setParameter("gender", gender);
			q.setDate("dateOfBirth", dateOfBirth);
			resultList = q.list();
		} catch (HibernateException e) {
			e.printStackTrace();
		}finally{
			session.close();
		}
		return resultList;
	}

	@Override
	public List<NicTransactionAttachment> findNicTransactionAttachments(String transactionId, String[] docTypes, String[] serialNums) throws Exception {
//		Session session = null;
		try {
//			session = getHibernateTemplate().getSessionFactory().openSession();
//			Criteria tCriteria = session.createCriteria(NicTransactionAttachment.class);
//			tCriteria.add(Restrictions.eq("transactionId", transactionId));
//			tCriteria.add(Restrictions.in("docType", docTypes));
//			tCriteria.add(Restrictions.in("serialNo", serialNums));
//
//			return tCriteria.list();

			List<String> docTypeList = Arrays.asList(docTypes);
			List<String> serialNumList = serialNums == null ? null : Arrays.asList(serialNums);

			DetachedCriteria criteria = DetachedCriteria.forClass(getPersistentClass());
			criteria.add(Restrictions.eq("transactionId", transactionId));
			criteria.add(Restrictions.in("docType", docTypeList));
			if (CollectionUtils.isNotEmpty(serialNumList)) {
				criteria.add(Restrictions.in("serialNo", serialNumList));
			}
			criteria.addOrder(Order.asc("docType")).addOrder(Order.desc("serialNo"));
			if (StringUtils.isNotBlank(transactionId)) {
				return this.findAll(criteria);
			}

		} catch (Exception ex) {
			logger.error("Error occurred while getting the transaction documents. Exception: " + ex.getMessage());
			throw new Exception("Error occurred while getting the transaction documents. Exception: " + ex.getMessage());
		} finally {
//			session.close();
		}
		return null;
	}
	
	@Override
	public BaseModelList<NicTransactionAttachment> findNicTransactionAttachmentsOutTypes(String transactionId, String[] docTypes) throws Exception {
//		Session session = null;
		try {
//			session = getHibernateTemplate().getSessionFactory().openSession();
//			Criteria tCriteria = session.createCriteria(NicTransactionAttachment.class);
//			tCriteria.add(Restrictions.eq("transactionId", transactionId));
//			tCriteria.add(Restrictions.in("docType", docTypes));
//			tCriteria.add(Restrictions.in("serialNo", serialNums));
//
//			return tCriteria.list();

			List<String> docTypeList = Arrays.asList(docTypes);

			DetachedCriteria criteria = DetachedCriteria.forClass(getPersistentClass());
			criteria.add(Restrictions.eq("transactionId", transactionId));
			criteria.add(Restrictions.not(Restrictions.in("docType", docTypeList)));
			criteria.addOrder(Order.asc("docType")).addOrder(Order.desc("serialNo"));
			return new BaseModelList<NicTransactionAttachment>(this.findAll(criteria), true, null);
		} catch (Exception ex) {
			logger.error("Error occurred while getting the transaction documents. Exception: " + ex.getMessage());
			return new BaseModelList<NicTransactionAttachment>(null, false, CreateMessageException.createMessageException(ex) + " - findNicTransactionAttachmentsOutTypes - " + transactionId + " - thất bại.");
		} finally {
//			session.close();
		}
	}
	
	@Override
	public BaseModelList<NicTransactionAttachment> findNicTransactionAttachmentsInTypes(String transactionId, String[] docTypes) throws Exception {
//		Session session = null;
		try {
//			session = getHibernateTemplate().getSessionFactory().openSession();
//			Criteria tCriteria = session.createCriteria(NicTransactionAttachment.class);
//			tCriteria.add(Restrictions.eq("transactionId", transactionId));
//			tCriteria.add(Restrictions.in("docType", docTypes));
//			tCriteria.add(Restrictions.in("serialNo", serialNums));
//
//			return tCriteria.list();

			List<String> docTypeList = Arrays.asList(docTypes);

			DetachedCriteria criteria = DetachedCriteria.forClass(getPersistentClass());
			criteria.add(Restrictions.eq("transactionId", transactionId));
			criteria.add(Restrictions.in("docType", docTypeList));
			criteria.addOrder(Order.asc("docType")).addOrder(Order.desc("serialNo"));
			return new BaseModelList<NicTransactionAttachment>(this.findAll(criteria), true, null);

		} catch (Exception ex) {
			logger.error("Error occurred while getting the transaction documents. Exception: " + ex.getMessage());
			return new BaseModelList<NicTransactionAttachment>(null, false, CreateMessageException.createMessageException(ex) + " - findNicTransactionAttachmentsInTypes - " + transactionId + " - thất bại.");
		} finally {
//			session.close();
		}
	}

	@Override
	public List<NicTransactionAttachment> getTransactionProofDocuments(String txnId, String[] excludedDocTypes) throws Exception {
		//Session session = null;
		try {
			//session = getHibernateTemplate().getSessionFactory().openSession();
			//Criteria tCriteria = session.createCriteria(NicTransactionAttachment.class);
			//tCriteria.add(Restrictions.eq("transactionId", txnId));
			//tCriteria.add(Restrictions.not(Restrictions.in("docType", docTypes)));
			//return tCriteria.list();
			
			DetachedCriteria criteria = DetachedCriteria.forClass(getPersistentClass());
			criteria.add(Restrictions.eq("transactionId", txnId));
	
			criteria.add(Restrictions.like("docType", "SC_%"));//TRUNG THÊM ĐIỀU KIỆN
			criteria.add(Restrictions.not(Restrictions.like("docType","SC_USER_DECL")));//TRUNG THÊM ĐIỀU KIỆN
			criteria.add(Restrictions.not(Restrictions.in("docType", excludedDocTypes)));
			criteria.addOrder(Order.asc("docType"));
			criteria.addOrder(Order.asc("serialNo"));
			return this.findAll(criteria);
		} catch (Exception ex) {
			logger.error("Error occurred while getting the transaction documents. Exception: " + ex.getMessage());
			throw new Exception("Error occurred while getting the transaction documents. Exception: " + ex.getMessage());
		} finally {
			//session.close();
		}
	}

	@Override
	public List<NicTransactionAttachment> findFacialTEById(
			String transactionId, String docType, String remark) {
		DetachedCriteria detachedCriteria = DetachedCriteria.forClass(getPersistentClass());
		
		if (StringUtils.isNotBlank(transactionId)) {
			detachedCriteria.add(Restrictions.eq("transactionId",transactionId));
		}
		if (StringUtils.isNotBlank(docType)) {
			detachedCriteria.add(Restrictions.eq("docType",docType));
		}
		if (StringUtils.isNotBlank(remark)) {
			detachedCriteria.add(Restrictions.eq("remarks",remark));
		}
		
		List<NicTransactionAttachment> resultList = this.findAll(detachedCriteria);
		return resultList;
	}

	@Override
	public BaseModelSingle<Long> saveTranAttachment(
			NicTransactionAttachment tranAttachment) {
		try {
			Long id  = this.save(tranAttachment);
			return new BaseModelSingle<Long>(id, true, null);
		} catch (Exception e) {
			return new BaseModelSingle<Long>(null, false, CreateMessageException.createMessageException(e) + " - saveTranPayment - " + tranAttachment.getTransactionId() + " - thất bại.");
		}
	}

	@Override
	public BaseModelSingle<Void> deleteTranAttachment(
			NicTransactionAttachment attach) {
		try {
			this.delete(attach);
			return new BaseModelSingle<Void>(null, true, null);
		} catch (Exception e) {
			return new BaseModelSingle<Void>(null, false, CreateMessageException.createMessageException(e)+ " - deleteTranAttachment - " + attach.getTransactionId() + " - thất bại.");
		}
	}
	@Override
	public List<NicTransactionAttachment> findAttachmentsByTranId(
			String transactionId) throws Exception {
		List<NicTransactionAttachment> listTranAttach = null;
		try {
			DetachedCriteria detachedCriteria = DetachedCriteria.forClass(getPersistentClass());
			
			if (StringUtils.isNotBlank(transactionId)) {
				detachedCriteria.add(Restrictions.eq("transactionId",transactionId));
			}
			listTranAttach = this.findAll(detachedCriteria);
		} catch (Exception e) {
			throw e;
		}
		return listTranAttach;
	}
	@Override
	public List<NicTransactionAttachment> findNicTransactionAttachments(
			String transactionId, String docType, String[] serialNo) {
		List<NicTransactionAttachment> listTranAttach = null;
		try {
			DetachedCriteria detachedCriteria = DetachedCriteria.forClass(getPersistentClass());
			
			if (StringUtils.isNotBlank(transactionId)) {
				detachedCriteria.add(Restrictions.eq("transactionId",transactionId));
			}
			if (StringUtils.isNotBlank(docType)) {
				detachedCriteria.add(Restrictions.eq("docType",docType));
			}
			if (serialNo.length > 0) {
				detachedCriteria.add(Restrictions.in("serialNo",serialNo));
			}
			listTranAttach = this.findAll(detachedCriteria);
		} catch (Exception e) {
			throw e;
		}
		return listTranAttach;
	}
	
}
