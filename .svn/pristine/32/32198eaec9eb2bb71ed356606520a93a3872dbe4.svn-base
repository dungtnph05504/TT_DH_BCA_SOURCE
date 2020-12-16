package com.nec.asia.nic.framework.admin.code.dao.impl;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Property;
import org.hibernate.criterion.Restrictions;

import org.springframework.stereotype.Repository;

import com.nec.asia.nic.framework.PaginatedResult;
import com.nec.asia.nic.framework.admin.code.dao.ProofDocumentDefDao;
import com.nec.asia.nic.framework.admin.code.domain.CodeValues;
import com.nec.asia.nic.framework.admin.code.domain.ProofDocumentDef;
import com.nec.asia.nic.framework.admin.code.domain.ProofDocumentDefId;
import com.nec.asia.nic.framework.dao.impl.GenericHibernateDao;

/* 
 * Modification History:
 * 
 * 04 Sep 2013 (chris): to filter the deleted records.
 * 
 * 2 Oct 2013 (Peddi Swapna): Modified getAllForPagination method to implement the external sorting.
 * 
 * 17 Oct 2013 (chris): to fix error when delete_flag change to string type. 
 * 
 */
@Deprecated 
/**
 * @deprecated this class is not supported by this version 
 */
@Repository("proofDocumentDefDao")
public class ProofDocumentDefDaoImpl extends
		GenericHibernateDao<ProofDocumentDef, ProofDocumentDefId> implements
		ProofDocumentDefDao {

	@SuppressWarnings("unchecked")
	@Override
	public List<ProofDocumentDef> findProofDocumentDefByTransType(
			String transactionType, String transactionSubtype) {
		DetachedCriteria criteria = DetachedCriteria.forClass(ProofDocumentDef.class);
		criteria.add(Restrictions.eq("id.transactionType", transactionType));
		criteria.add(Restrictions.eq("id.transactionSubtype", transactionSubtype));
		criteria.add(Restrictions.or(Restrictions.isNull("deleteFlag"), Restrictions.not(Restrictions.eq("deleteFlag", "Y"))));

		return  (List<ProofDocumentDef>) getHibernateTemplate().findByCriteria(criteria);
	}

	public PaginatedResult<ProofDocumentDef> getAllForPagination(int pageNo,int pageSize,Order... order){
		DetachedCriteria detachedCriteria = DetachedCriteria.forClass(ProofDocumentDef.class);		
		detachedCriteria.add(Restrictions.or(Restrictions.isNull("deleteFlag"), Restrictions.not(Restrictions.eq("deleteFlag", "Y"))));

		PaginatedResult<ProofDocumentDef> paginatedResults =findAllForPagination(detachedCriteria, pageNo, pageSize, order);		
		return paginatedResults;
	}
	
	public List<ProofDocumentDef>  findAllDistinctProofDocuments(){
		DetachedCriteria detachedCriteria = DetachedCriteria.forClass(ProofDocumentDef.class);
		detachedCriteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		List<ProofDocumentDef> distinctList = findAll(detachedCriteria);
		
		return distinctList;
	}

}
