package com.nec.asia.nic.framework.admin.code.dao.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.nec.asia.nic.framework.PaginatedResult;
import com.nec.asia.nic.framework.admin.code.dao.CodesDao;
import com.nec.asia.nic.framework.admin.code.domain.CodeValues;
import com.nec.asia.nic.framework.admin.code.domain.Codes;
import com.nec.asia.nic.framework.dao.impl.GenericHibernateDao;

@Repository("codesDao")
/* 
 * Modification History:
 * 
 * 20 Dec 2015 (chris): Code Refactoring.
 * 15 Mar 2016 (Khang): Sort Code Value Description before returning
 * 27 Jun 2017 (chris): add delete method for physical delete
 */
public class CodesDaoImpl extends GenericHibernateDao<Codes, String> implements CodesDao {

	/* get CodeValue Description for Report Generation - Code Table as Report Parameters. */
	public Map<String, String> getPramCodes(String codeName) {

		Map<String, String> entityList = new HashMap<String, String>();
		Codes code = this.findById(codeName);
		Set<com.nec.asia.nic.framework.admin.code.domain.CodeValues> codeValuesList = code.getCodeValues();
		for (com.nec.asia.nic.framework.admin.code.domain.CodeValues codeValueObj : codeValuesList) {
			entityList.put(codeValueObj.getCodeValueDesc(), codeValueObj.getCodeValueDesc());
		}

		return entityList;
	}

	/* Get List of CodeValues in a map form by using codeId. */
	@Override
	public Map<String, String> getCodeValuesByCodeID(String codeName) throws Exception {
		Map<String, String> codeValueDescMap = new HashMap<String, String>();
		Codes code = this.findById(codeName);
		Set<CodeValues> codeValuesSet = code.getCodeValues();

		for (CodeValues codeValue : codeValuesSet) {
			
			if(codeValue.getDeleteFlag()==null || codeValue.getDeleteFlag()==false)//Tú thêm đoạn code này
				codeValueDescMap.put(codeValue.getId().getCodeValue(), codeValue.getCodeValueDesc());
		}

		if (logger.isDebugEnabled()) {
			logger.debug(codeName + "===============================" + codeValueDescMap);
		}
		logger.info("[getCodeValuesByCodeID] : ["+codeName+"] size: "+codeValueDescMap.size());
		return codeValueDescMap;
	}

	public PaginatedResult<Codes> getAllCodes(int pageNo, int pageSize) {

		DetachedCriteria criteria = DetachedCriteria.forClass(Codes.class);
		Criterion deleteFlagNull = Restrictions.isNull("deleteFlag");
		Criterion deleteFlagN = Restrictions.eq("deleteFlag", false);
		criteria.add(Restrictions.or(deleteFlagNull, deleteFlagN));
		PaginatedResult<Codes> paginatedResult = findAllForPagination(criteria, pageNo, pageSize);

		return paginatedResult;
	}

	@Override
	public void delete(Codes entity) {
		try {
			logger.trace("delete code >> " + entity.getCodeId());
			if (isSupportSoftDelete) {
				Codes obj = this.findById(entity.getCodeId());
				if (obj!=null) {
					obj.setDeleteBy(entity.getDeleteBy());
					obj.setDeleteDate(entity.getDeleteDate());
					obj.setDeleteFlag(entity.getDeleteFlag());
					obj.setDeleteWkstnId(entity.getDeleteWkstnId());
					
					this.saveOrUpdate(obj);
					logger.debug("[delete][Codes] softDelete({}) : {} ", obj.getCodeId());
				}
			} else {
				super.delete(entity);
				logger.debug("[delete][Codes] physical deleted.");
			}
		} catch (Throwable t) {
			t.printStackTrace();
			logger.error("EXCEPTION OCCURRED IN Delete Codes >> " + t.getMessage());
		}
	}

	@Override
	public List<String> getAllCodeDescs() throws Exception {
		List<String> listType = new ArrayList<String>();
		Session session = null;
		try {
			session = getHibernateTemplate().getSessionFactory().openSession();
			StringBuffer sql = new StringBuffer("SELECT DISTINCT r.CODE_ID FROM EPP_CENTRAL_SYS.CODES r");
			Query query = session.createSQLQuery(sql.toString());
			listType = query.list();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return listType;
	}

	@Override
	public PaginatedResult<Codes> getCodesByCodeId(String codeId, int pageNo, int pageSize) {
		PaginatedResult<Codes> pagRs = null;
		try {
			DetachedCriteria criteria = DetachedCriteria.forClass(Codes.class);
			criteria.add(Restrictions.eq("codeId", codeId));
			pagRs = this.findAllForPagination(criteria, pageNo, pageSize, Order.desc("codeId"));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return pagRs;
	}

}