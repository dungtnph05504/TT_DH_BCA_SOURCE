/**
 * 
 */
package com.nec.asia.nic.framework.report.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Example;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.nec.asia.nic.framework.dao.impl.GenericHibernateDao;
import com.nec.asia.nic.framework.report.dao.ReportParameterDao;
import com.nec.asia.nic.framework.report.domain.ReportParameterId;
import com.nec.asia.nic.framework.report.domain.Report;
import com.nec.asia.nic.framework.report.domain.ReportParameter;

/**
 * @author prasad_karnati
 *
 */

@Repository("reportParameterDao")
public class ReportParameterDaoImpl extends GenericHibernateDao<ReportParameter, ReportParameterId>  implements ReportParameterDao {
	
	public List<ReportParameter> getReportParameters(String reportId, String paraName) {
		List<ReportParameter> entities = null;
		DetachedCriteria criteria = DetachedCriteria.forClass(ReportParameter.class);
		criteria.add(Restrictions.eq("id.report.reportId", reportId));
		if (StringUtils.isNotBlank(paraName)) 
			criteria.add(Restrictions.eq("id.paraName", paraName));
		criteria.addOrder(Order.asc("priority"));
		criteria.add(Restrictions.or(Restrictions.isNull("deleteFlag"), Restrictions.ne("deleteFlag", "Y")));
		entities = super.findAll(criteria);
		return entities;
	}

	public String saveReportParameter(ReportParameter parameter) {
		String status = "";
		try {
			this.saveOrUpdate(parameter);
			status = "success";
		} catch (HibernateException exp) {
			status = "failed";
		} finally {
			logger.debug("[saveReportParameter] : {} ", status);
		}
		return status;
	}

	public String deleteReportParameter(ReportParameter parameter) throws Exception {
		String status="";
		try{
			ReportParameter entity = super.findById(parameter.getId());
			if (entity != null) {
				entity.setDeleteBy(parameter.getDeleteBy());
				entity.setDeleteDate(parameter.getDeleteDate());
				entity.setDeleteWkstnId(parameter.getDeleteWkstnId());
				entity.setDeleteFlag("Y");
				
				logger.trace("[deleteReportParameter] before delete, softDelete : {} ", isSupportSoftDelete);
				if (isSupportSoftDelete) {
					super.saveOrUpdate(entity);
					logger.trace("[deleteReportParameter] softDelete : {} ", isSupportSoftDelete);
				} else {
					//NOTE: it is required to cleanUp the reference java object, otherwise cannot flush.
					boolean cleanUp = false;
					if (entity.getId().getReport()!=null && CollectionUtils.isNotEmpty(entity.getId().getReport().getReportParameters())) {
						cleanUp = entity.getId().getReport().getReportParameters().remove(entity);
					}
					logger.trace("[deleteReportParameter] cleanup: {}, before physical delete", cleanUp);
					//super.getHibernateTemplate().delete(entity);
					super.delete(entity);
					logger.trace("[deleteReportParameter] physical deleted.");
				}
			}
			status="success";
		} catch(HibernateException exp) {
			logger.error("[deleteReportParameter] error.", exp);
			status="failed";
		} finally {
			logger.debug("[deleteReportParameter] : {} ", status);
		}
		return status;
	}
}
