package com.nec.asia.nic.comp.signerGovs.dao.impl;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.exolab.castor.types.DateTime;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.nec.asia.nic.comp.listHandover.dao.ListHandoverDao;
import com.nec.asia.nic.comp.listHandover.domain.NicListHandover;
import com.nec.asia.nic.comp.signerGovs.dao.SignerGovsDao;
import com.nec.asia.nic.comp.signerGovs.domain.SignerGovs;
import com.nec.asia.nic.framework.PaginatedResult;
import com.nec.asia.nic.framework.admin.code.dao.PaymentDefDao;
import com.nec.asia.nic.framework.admin.code.domain.PaymentDef;
import com.nec.asia.nic.framework.admin.code.domain.PaymentDefId;
import com.nec.asia.nic.framework.dao.impl.GenericHibernateDao;

/*
 * Modification History:
 * 27 Jun 2017 (chris): add delete method for physical delete
 */
@Repository("signerGovsDao")
public class SignerGovsDaoImpl extends GenericHibernateDao<SignerGovs, Long>
		implements SignerGovsDao {

	/*
	 * @SuppressWarnings("unchecked") public List<PaymentDef>
	 * findPaymentDefByTransType(String transactionType, String
	 * transactionSubtype) { DetachedCriteria criteria =
	 * DetachedCriteria.forClass(PaymentDef.class);
	 * criteria.add(Restrictions.eq("id.transactionType", transactionType));
	 * criteria.add(Restrictions.eq("id.transactionSubtype",
	 * transactionSubtype));
	 * 
	 * return (List<PaymentDef>)
	 * getHibernateTemplate().findByCriteria(criteria); }
	 */

	@Override
	public PaginatedResult<SignerGovs> listSignerGovsAllBySearch(String search,
			int PageNo, int PageSize) throws Exception {
		DetachedCriteria criteria = DetachedCriteria
				.forClass(getPersistentClass());
		Order orders = Order.asc("id");
		// getHibernateTemplate().findByCriteria(criteria);

		return this.findAllForPagination(criteria, PageNo, PageSize, orders);
	}

	public boolean createSignerGovs(SignerGovs signerGov) throws Exception {
		boolean status = false;
		Date date = new Date();
		signerGov.setCreateDate(date);
		try {
			// NicListHandover entity = findById(listHandover.getId());
			super.saveOrUpdate(signerGov);
			status = true;

		} catch (Exception exp) {
		} finally {
			logger.debug("[createSignerGov]status : {} ", status);
		}
		return status;
	}

	public boolean editSignerGovs(SignerGovs signerGov) throws Exception {
		boolean status = false;
		Date date = new Date();
		signerGov.setUpdateDate(date);
		try {
			// NicListHandover entity = findById(listHandover.getId());
			super.saveOrUpdate(signerGov);
			status = true;

		} catch (Exception exp) {
		} finally {
			logger.debug("[editSignerGov]status : {} ", status);
		}
		return status;
	}

	public SignerGovs findByCode(String code) {

		DetachedCriteria criteria = DetachedCriteria.forClass(getPersistentClass());
		criteria.add(Restrictions.eq("codeSigner", code));
		List<SignerGovs> signers = this.findAll(criteria);
		if(!signers.isEmpty()){
			return signers.get(0);
		}
		return null;
	}
	
	public List<SignerGovs> findListByCode(String code) {

		DetachedCriteria criteria = DetachedCriteria.forClass(getPersistentClass());
		if(StringUtils.isNotEmpty(code))
			criteria.add(Restrictions.eq("codeGovernment", code));
		
		List<SignerGovs> signers = this.findAll(criteria);
		if(!signers.isEmpty()){
			return signers;
		}
		return null;
	}
}
