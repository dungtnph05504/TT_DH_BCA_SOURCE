package com.nec.asia.nic.framework.admin.code.dao.impl;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.List;
import java.util.Properties;

import org.apache.commons.lang.StringUtils;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.nec.asia.nic.framework.PaginatedResult;
import com.nec.asia.nic.framework.admin.code.dao.ParametersDao;
import com.nec.asia.nic.framework.admin.code.domain.Parameters;
import com.nec.asia.nic.framework.admin.code.domain.ParametersId;
import com.nec.asia.nic.framework.dao.DaoException;
import com.nec.asia.nic.framework.dao.impl.GenericHibernateDao;

/*
 * Modification History:
 * 25 Jan 2016 (Tue): Added findAllActForPagination method to get all params have delete_flg = false.
 * 27 Jun 2017 (chris): add delete method for physical delete
 */
@Repository("parametersDao")
public class ParametersDaoImpl extends
		GenericHibernateDao<Parameters, ParametersId> implements
		ParametersDao {

	@Override
	public List<Parameters> findAllByScope(String paraScope) {
		DetachedCriteria criteria = DetachedCriteria.forClass(Parameters.class);
		criteria.add(Restrictions.eq("id.paraScope", paraScope));
		return (List<Parameters>) getHibernateTemplate().findByCriteria(criteria);
	}
	
	@Override
	public PaginatedResult<Parameters> findAllForPaginationByScope(String paraScope, int pageNo, int pageSize) {
		logger.debug("Inside Dao Impl ======>>>>>Param Scope: "+paraScope);
		DetachedCriteria criteria = DetachedCriteria.forClass(Parameters.class);
		criteria.add(Restrictions.eq("id.paraScope", paraScope));
		
		return this.findAllForPagination(criteria, pageNo, pageSize);
	}

//	@Override
//	public PaginatedResult<Parameters> findAllForPagination(String paramScope, int pageNo, int pageSize) {
//		logger.debug("Inside Dao Impl ======>>>>>Param Scope"+paramScope);
//		DetachedCriteria criteria = DetachedCriteria.forClass(Parameters.class);
//		criteria.add(Restrictions.eq("id.paraScope", paramScope));
//		
//		return this.findAllForPagination(criteria, pageNo, pageSize);
//	}
	
	@Override
	public void savePendingParameter(Parameters pending) {
		try {
			getHibernateTemplate().save(pending);
		} catch (Exception e1) {
			logger.error("EXCEPTION OCCURRED IN SAVE Parameter >> "	+ e1.getMessage());
		}
	}

	@Override
	public boolean updateParameter(Parameters param) {
		logger.debug("param.getId() >> " + param.getId().getParaName());
		try {
			Parameters obj = getHibernateTemplate().load(Parameters.class, param.getId());
			if (obj!=null && StringUtils.isNotBlank(obj.getSystemId())) {
				logger.debug("updating param >> " + obj.getId().getParaName());
				obj.getId().setParaName(param.getId().getParaName());
				obj.getId().setParaScope(param.getId().getParaScope());
				obj.setAdminAccessibleFlag(param.getAdminAccessibleFlag());
				obj.setCreateBy(param.getCreateBy());
				obj.setCreateDate(param.getCreateDate());
				obj.setCreateWkstnId(param.getCreateWkstnId());
				obj.setDeleteBy(param.getDeleteBy());
				obj.setDeleteDate(param.getDeleteDate());
				obj.setDeleteFlag(param.getDeleteFlag());
				obj.setDeleteWkstnId(param.getDeleteWkstnId());
				obj.setParaDesc(param.getParaDesc());
				obj.setParaLobValue(param.getParaLobValue());
				obj.setParaShortValue(param.getParaShortValue());
				obj.setParaType(param.getParaType());
				obj.setSystemId(param.getSystemId());
				obj.setUpdateBy(param.getUpdateBy());
				obj.setUpdateDate(param.getUpdateDate());
				obj.setUpdateWkstnId(param.getUpdateWkstnId());
				obj.setUserAccessibleFlag(param.getUserAccessibleFlag());
				getHibernateTemplate().update(obj);
				return true;
			}
		} catch (Throwable e1) {
			logger.error("EXCEPTION OCCURRED IN RETRIEVAL / UPDATE Parameter >> " + e1.getMessage());
		}
		return false;
	}
	
	public boolean deleteParameter(Parameters param) {
		try {
			logger.trace("delete param >> " + param.getId().getParaName());
			if (isSupportSoftDelete) {
				Parameters obj = this.findById(param.getId());
				if (obj!=null) {
					obj.setDeleteBy(param.getDeleteBy());
					obj.setDeleteDate(param.getDeleteDate());
					obj.setDeleteFlag(param.getDeleteFlag());
					obj.setDeleteWkstnId(param.getDeleteWkstnId());
					
					this.saveOrUpdate(obj);
					logger.debug("[deleteReportTemplate] softDelete({}) : {} ", obj.getId().getParaName());
				}
			} else {
				super.delete(param);
				logger.debug("[deleteParameter] physical deleted.");
			}
			return true;
		} catch (Throwable e1) {
			logger.error("EXCEPTION OCCURRED IN Delete Parameter >> " + e1.getMessage());
		}
		return false;
	}
	
	@Override
	public List<Parameters> findMatchingParameters(String paraName) {
		DetachedCriteria criteria = DetachedCriteria.forClass(Parameters.class);
		criteria.add(Restrictions.like("id.paraName", paraName+"%"));		
		return (List<Parameters>) this.findAll(criteria);
	}
	
	//APARNA CHANGES START 06/01/2014
	@Override
	public boolean resetTransactionIdSequence() {
		ParametersId id=new ParametersId("APPLICATION", "TRANS_ID_SEQ");
		try{
			Parameters param=getHibernateTemplate().get(Parameters.class, id);
			if(param!=null){
				param.setParaShortValue("1");
				getHibernateTemplate().update(param);
				return true;
			}
			return false;
		}catch(Exception e){
			logger.error("Error occurred while reset TransactionIdSequence. Exception: "+e.getMessage());
			return false;
		}
	}

	@Override
	public void updateAsSequenceResetDone(String paraScope, String paraName) {
		ParametersId id = new ParametersId();
		id.setParaScope(paraScope);
		id.setParaName(paraName);
		try{
			Parameters param =getHibernateTemplate().get(Parameters.class, id);
			if(param!=null){
				param.setParaShortValue("Y");
				getHibernateTemplate().update(param);
			}
		}catch(Exception e){
			logger.error("Error occurred while update reset sequence status. Exception: "+e.getMessage());
		}

	}

	@Override
	public boolean resetCollectionSlipSequence() {
		ParametersId id=new ParametersId("APPLICATION", "COLL_SLIP_ID_SEQ");
		try{
			Parameters param=getHibernateTemplate().get(Parameters.class, id);
			if(param!=null){
				param.setParaShortValue("1");
				getHibernateTemplate().update(param);
				return true;
			}
			return false;
		}catch(Exception e){
			logger.error("Error occurred while update reset col slip id sequence. Exception: "+e.getMessage());
			return false;
		}
	}

	//APARNA CHANGES END 06/01/2014

	@Override
    public Properties readProperties(Parameters parameters) throws Exception{
        Properties properties = null;
        if (parameters!=null && StringUtils.isNotEmpty(parameters.getParaLobValue())){
            properties = new Properties();
            try {
                properties.load(new ByteArrayInputStream(parameters.getParaLobValue().getBytes()));
            } catch (IOException e) {
                throw new Exception(e);
            }
        }
        return properties;
    }
	
	@Override
	public PaginatedResult<Parameters> findAllActForPagination(int pageNo, int pageSize, Order order) throws DaoException {
		DetachedCriteria criteria = DetachedCriteria.forClass(Parameters.class);
		criteria.add(Restrictions.or(Restrictions.ne("deleteFlag", true), Restrictions.isNull("deleteFlag")));
		criteria.addOrder(order);
		return this.findAllForPagination(criteria, pageNo, pageSize);
		
	}

	@Override
	public PaginatedResult<Parameters> getPageParamByParaName(String parameter,
			int pageNo, int pageSize, Order hibernateOrder) {
		try {
			DetachedCriteria criteria = DetachedCriteria.forClass(Parameters.class);
			criteria.add(Restrictions.eq("id.paraName", parameter));	
			criteria.addOrder(hibernateOrder);
			return this.findAllForPagination(criteria, pageNo, pageSize);
		} catch (Exception e) {
			throw e;
		}
	}
}
