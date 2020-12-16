/**
 * 
 */
package com.nec.asia.nic.framework.report.dao.impl;


import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.hibernate.Criteria;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.nec.asia.nic.framework.PaginatedResult;
import com.nec.asia.nic.framework.dao.impl.GenericHibernateDao;
import com.nec.asia.nic.framework.report.dao.ReportDao;
import com.nec.asia.nic.framework.report.domain.Report;

/**
 * @author Prasad_Karnati
 *
 */
@Repository("reportDao")
public class ReportDaoImpl extends GenericHibernateDao<Report,String>  implements  ReportDao {
	
	public List<Report> getReportsByCategory(String categoryType) throws Exception {
		List<Report> entities = null;
		try {
			DetachedCriteria criteria = DetachedCriteria.forClass(Report.class);
			criteria.add(Restrictions.eq("reportCategory", categoryType));
			criteria.add(Restrictions.or(Restrictions.isNull("deleteFlag"), Restrictions.ne("deleteFlag", "Y")));
			criteria.addOrder(Order.asc("reportPriority"));
			
			entities = super.findAll(criteria);
			
	        logger.info("[getReportsByCategory][{}] size:{}", new Object[] { categoryType, entities!=null?entities.size():0 });
		}
		catch (Exception e) {
			logger.error("Exception in ReportDao", e);
		}

		return entities;
	}

	public List<Report> getReportDetailById(String reportId) throws Exception {

		List<Report> entities = null;
		try {
			DetachedCriteria criteria = DetachedCriteria.forClass(Report.class);
			criteria.add(Restrictions.eq("reportId", reportId));
			//criteria.setFetchMode("reportParameters", FetchMode.SELECT); //default lazy "false"
			entities = super.findAll(criteria);
		}
		catch (Exception e) {
			logger.error("Exception in ReportDao", e);
		}
		return entities;
	}

	public Report findReportById(String reportId) throws Exception {
		Report reportObj = null;
		try {
			reportObj = super.findById(reportId);
		} catch (Exception e) {
			logger.error("Exception in ReportDao", e);
		}
		return reportObj;
	}
	
	public String saveReportDefinition(Report report) throws Exception {
		String status = "";
		logger.debug("Enter into saveReportDefinition");
		try {
			save(report);
			status = "success";
		 }catch (Exception e) {
			status = "failed";
			logger.error("Exception in ReportDao", e);
		}
		logger.debug("Exit from saveReportDefinition ");
		return status;
	}
	
	public String updateReportDefinition(Report report) throws Exception {
		String status = "";
		try {
			if (report != null) {
				this.saveOrUpdate(report);
				status = "success";
			}
		} catch (Exception e) {
			status = "failed";
			logger.error("Exception in ReportDao", e);
		}
		logger.debug("Exit from updateReportDefinition ");
		return status;
	}
	
	public String deleteReport(Report reportDBO) throws Exception{
		
		String status = "";
		try {
			Report entity = findReportById(reportDBO.getReportId());
			if (entity!=null) {
				entity.setDeleteBy(reportDBO.getDeleteBy());
				entity.setDeleteWkstnId(reportDBO.getDeleteWkstnId());
				entity.setDeleteDate(reportDBO.getDeleteDate());
				entity.setDeleteFlag(reportDBO.getDeleteFlag());
				
				if (isSupportSoftDelete) {
					super.saveOrUpdate(entity);
					logger.trace("[deleteReport] softDelete : {} ", isSupportSoftDelete);
				} else {
					//NOTE: it is required to cleanUp the reference java object, otherwise cannot flush.
					boolean cleanUp = false;
					if (CollectionUtils.isNotEmpty(entity.getReportParameters())) {
						this.getHibernateTemplate().deleteAll(entity.getReportParameters());
						cleanUp = true;
					}
					logger.trace("[deleteReport] cleanup parameters: {}, before physical delete", cleanUp);
					super.delete(entity);
					logger.trace("[deleteReport] physical deleted.");
				}
				status = "success";
			} else {
				status = "report definition doesn't exists";
			}
			
		} catch (Exception exp) {
			status = "failed";
			logger.error("Exception in ReportDao", exp);
		} finally {
			logger.debug("[deleteReport] : {} ", status);
		}
		return status;
	}

	@SuppressWarnings("unchecked")
	@Override
	public PaginatedResult<Report> getReportsByCategory1(String categoryType, int pageNo,
			int pageSize) throws Exception {
		//List<Report> entities = null;
		try {
			DetachedCriteria criteria = DetachedCriteria.forClass(Report.class);
			criteria.add(Restrictions.eq("reportCategory", categoryType));
			criteria.add(Restrictions.or(Restrictions.isNull("deleteFlag"), Restrictions.ne("deleteFlag", "Y")));
			criteria.addOrder(Order.asc("reportPriority"));
			
			//entities = super.findAll(criteria);
			
	        //logger.info("[getReportsByCategory][{}] size:{}", new Object[] { categoryType, entities!=null?entities.size():0 });
			return this.findAllForPagination(criteria, pageNo, pageSize);
		}
		catch (Exception e) {
			logger.error("Exception in ReportDao", e);
			return new PaginatedResult<>(0, 1, new ArrayList<Report>());
		}

	}

//	public void deleteEntity(Object entity) {
//		this.getHibernateTemplate().saveOrUpdate(entity);
//	}
}
