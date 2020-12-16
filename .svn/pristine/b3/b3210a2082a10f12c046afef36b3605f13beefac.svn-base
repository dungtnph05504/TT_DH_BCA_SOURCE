package com.nec.asia.nic.framework.report.dao.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.rmi.CORBA.UtilDelegate;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.nec.asia.nic.framework.PaginatedResult;
import com.nec.asia.nic.framework.dao.impl.GenericHibernateDao;
import com.nec.asia.nic.framework.report.dao.ReportTemplateDao;
import com.nec.asia.nic.framework.report.domain.ReportTemplateId;
import com.nec.asia.nic.framework.report.domain.Report;
import com.nec.asia.nic.framework.report.domain.ReportTemplate;
import com.nec.asia.nic.framework.report.form.NicFlexiReport;
import com.nec.asia.nic.utils.DateUtil;



@Repository("reportTemplateDao")
public class ReportTemplateDaoImpl extends GenericHibernateDao<ReportTemplate, ReportTemplateId> implements ReportTemplateDao {
	
	public String uploadFile(ReportTemplate reportTemplate)
			throws Exception {
		String flag;
		try {
			this.saveOrUpdate(reportTemplate);
			flag = "success";
		} catch (Exception e) {
			flag = "fail";
			logger.error("fail to save ReportTemplate.", e);
		}
		return flag;
	}
	
	public ReportTemplate getReportTemplateById(String reportId, String reportTemplateFileName)
			throws Exception {
		
		ReportTemplateId reportTemplateId = new ReportTemplateId();
		if(StringUtils.isNotBlank(reportId)){
			reportTemplateId.setReportId(reportId.trim());
		}
		if(StringUtils.isNotBlank(reportTemplateFileName)){
			reportTemplateId.setTemplateFileName(reportTemplateFileName.trim());
		}

		return super.findById(reportTemplateId);
	}

	public String deleteTemplate(ReportTemplate templateObj) throws Exception {
		String status="";
		try{
			ReportTemplate entity = super.findById(templateObj.getId());
			if (entity != null) {
				entity.setDeleteBy(templateObj.getDeleteBy());
				entity.setDeleteDate(templateObj.getDeleteDate());
				entity.setDeleteWkstnId(templateObj.getDeleteWkstnId());
				entity.setDeleteFlag("Y");

				if (isSupportSoftDelete) {
					super.saveOrUpdate(entity);
					logger.trace("[deleteReportTemplate] softDelete : {} ", isSupportSoftDelete);
				} else {
					super.delete(entity);
					logger.trace("[deleteReportTemplate] physical deleted.");
				}
			}
			status="success";
		} catch(Exception exp) {
			status="fail";
			logger.error("fail to delete ReportTemplate.", exp);
		} finally {
			logger.debug("[deleteReportTemplate] : {} ", status);
		}
		return status;
	}
	
	public String deleteTemplates(String reportId, String deleteBy, Date deleteDate, String deleteWkstnId) throws Exception {
		String status="";
		try{
			if (isSupportSoftDelete) {
				int count = 0;
				List<ReportTemplate> templates = this.getReportTemplateByReportId(reportId);
				for (ReportTemplate entity : templates) {
					entity.setDeleteBy(deleteBy);
					entity.setDeleteDate(deleteDate);
					entity.setDeleteWkstnId(deleteWkstnId);
					entity.setDeleteFlag("Y");
					super.saveOrUpdate(entity);
					count++;
				}
				logger.trace("[deleteTemplates] softDelete={}: {} ", isSupportSoftDelete, count);
			} else {
				String queryString = "delete from "+this.getPersistentClass().getSimpleName()+" where id.reportId=?";
				int count = this.getHibernateTemplate().bulkUpdate(queryString, reportId);
				logger.trace("[deleteTemplates] physical deleted: {} ", count);
			}

			status = "success";
		} catch(Exception exp) {
			status = "fail";
			logger.error("fail to delete ReportTemplates.", exp);
		} finally {
			logger.debug("[deleteTemplates] : {} ", status);
		}
		return status;
	}

	/**
	 * Delete entity.
	 * 
	 * @param entity
	 *            the entity
	 */
	public void deleteEntity(Object entity) {
		this.getHibernateTemplate().delete(entity);
	}

	public List<NicFlexiReport> getReportTemplateDetails(
			int pageNo, int pageSize, String reportId, String templateName)
			throws Exception {
		List<NicFlexiReport> result =	new ArrayList<NicFlexiReport>();
		NicFlexiReport flexyReport;

		DetachedCriteria criteria = DetachedCriteria.forClass(ReportTemplate.class);
		criteria.add(Restrictions.eq("id.reportId", reportId));
		criteria.add(Restrictions.eq("deleteFlag", "N"));
		List<ReportTemplate> list = this.findAll(criteria);
		if(list !=null&& list.size()>0){
			for(ReportTemplate reportTemplate : list){
				flexyReport = new NicFlexiReport();
				flexyReport.setReportId(reportTemplate.getId().getReportId());
				flexyReport.setTemplateFileName(reportTemplate.getId().getTemplateFileName());
				flexyReport.setMainReportFlg(reportTemplate.getMainReportFlg());
				result.add(flexyReport);
			}	
		}

		return result;
	}
	
	@Override
	public List<ReportTemplate> getReportTemplateByReportId(String reportId) throws Exception {
		DetachedCriteria criteria = DetachedCriteria.forClass(ReportTemplate.class);
		criteria.add(Restrictions.eq("id.reportId", reportId));
		criteria.add(Restrictions.eq("deleteFlag", "N"));
		return this.findAll(criteria);
	}
}
