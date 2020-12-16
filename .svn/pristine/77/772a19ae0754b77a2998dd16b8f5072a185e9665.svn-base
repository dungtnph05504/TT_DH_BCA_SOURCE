/**
 * 
 */
package com.nec.asia.nic.framework.report.form;



import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Set;

import com.nec.asia.nic.framework.report.domain.Report;
import com.nec.asia.nic.framework.report.domain.ReportParameter;
import com.nec.asia.nic.framework.report.domain.NicReportCodeTable;

import org.springframework.web.multipart.commons.CommonsMultipartFile;


/**
 * @author prasad_karnati
 *
 */

/*
 * Modification History:
 * 
 * 22 Jun 2017 (chris) : re-factor for report management
 */
public class NicReportForm implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1810408236693576994L;
	private List<NicReportCodeTable> nicReportCodeTables;	
	private List<Report> nicReportNames;
	private String description;
	private String reportId;
	private String reportName;
	private String codePriority;
	private String htmlFormat;
	private String reportCategory;
	private String serverFlag;
	private String reportPriority;
	private CommonsMultipartFile fileData;
	
	private String parameterName;
	private String paramDesc;
	private String paramType;
	private String paramPriority;
	private String paramMandatory;
	private String parameterAlias;
	
	
	private String  reportParameterName;
	private String  reportParameterDesc;
	private String  reportParameterType;
	private String  reportParameterPriority;
	private String  reportParameterMandatory;
	private String  reportParameterAlias;	
	private String deleteCollection;
	
	

	private List<ReportParameter> parameterList;	
	private String categoryLoad;	
	private String mode;
	private String status;
	private String message;
	private String reportCategoryDesc;
	
	//class re-factor (moved from CodeValueForm)
	private String reportFileName;
	private String rptFrmt;
	
	

	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * @return the micReportCodeTables
	 */
	public List<NicReportCodeTable> getNicReportCodeTables() {
		return nicReportCodeTables;
	}

	/**
	 * @param nicReportCodeTables the micReportCodeTables to set
	 */
	public void setNicReportCodeTables(List<NicReportCodeTable> nicReportCodeTables) {
		this.nicReportCodeTables = nicReportCodeTables;
	}

	/**
	 * @return the reportId
	 */
	public String getReportId() {
		return reportId;
	}

	/**
	 * @param reportId the reportId to set
	 */
	public void setReportId(String reportId) {
		this.reportId = reportId;
	}

	/**
	 * @return the reportName
	 */
	public String getReportName() {
		return reportName;
	}

	/**
	 * @param reportName the reportName to set
	 */
	public void setReportName(String reportName) {
		this.reportName = reportName;
	}

	/**
	 * @return the codePriority
	 */
	public String getCodePriority() {
		return codePriority;
	}

	/**
	 * @param codePriority the codePriority to set
	 */
	public void setCodePriority(String codePriority) {
		this.codePriority = codePriority;
	}

	/**
	 * @return the htmlFormat
	 */
	public String getHtmlFormat() {
		return htmlFormat;
	}

	/**
	 * @param htmlFormat the htmlFormat to set
	 */
	public void setHtmlFormat(String htmlFormat) {
		this.htmlFormat = htmlFormat;
	}

	public String getReportCategory() {
		return reportCategory;
	}

	public void setReportCategory(String reportCategory) {
		this.reportCategory = reportCategory;
	}

	/**
	 * @return the micReportNames
	 */
	public List<Report> getMicReportNames() {
		return nicReportNames;
	}

	/**
	 * @param nicReportNames the micReportNames to set
	 */
	public void setMicReportNames(List<Report> nicReportNames) {
		this.nicReportNames = nicReportNames;
	}

	/**
	 * @return the fileData
	 */
	public CommonsMultipartFile getFileData() {
		return fileData;
	}

	/**
	 * @param fileData the fileData to set
	 */
	public void setFileData(CommonsMultipartFile fileData) {
		this.fileData = fileData;
	}

	/**
	 * @return the serverFlag
	 */
	public String getServerFlag() {
		return serverFlag;
	}

	/**
	 * @param serverFlag the serverFlag to set
	 */
	public void setServerFlag(String serverFlag) {
		this.serverFlag = serverFlag;
	}

	public String getParameterName() {
		return parameterName;
	}

	public void setParameterName(String parameterName) {
		this.parameterName = parameterName;
	}

	public String getParamDesc() {
		return paramDesc;
	}

	public void setParamDesc(String paramDesc) {
		this.paramDesc = paramDesc;
	}

	public String getParamType() {
		return paramType;
	}

	public void setParamType(String paramType) {
		this.paramType = paramType;
	}

	public String getParamPriority() {
		return paramPriority;
	}

	public void setParamPriority(String paramPriority) {
		this.paramPriority = paramPriority;
	}

	public String getParamMandatory() {
		return paramMandatory;
	}

	public void setParamMandatory(String paramMandatory) {
		this.paramMandatory = paramMandatory;
	}

	public String getParameterAlias() {
		return parameterAlias;
	}

	public void setParameterAlias(String parameterAlias) {
		this.parameterAlias = parameterAlias;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	/**
	 * @return the reportParameterName
	 */
	public String getReportParameterName() {
		return reportParameterName;
	}

	/**
	 * @param reportParameterName the reportParameterName to set
	 */
	public void setReportParameterName(String reportParameterName) {
		this.reportParameterName = reportParameterName;
	}

	/**
	 * @return the reportParameterDesc
	 */
	public String getReportParameterDesc() {
		return reportParameterDesc;
	}

	/**
	 * @param reportParameterDesc the reportParameterDesc to set
	 */
	public void setReportParameterDesc(String reportParameterDesc) {
		this.reportParameterDesc = reportParameterDesc;
	}

	/**
	 * @return the reportParameterType
	 */
	public String getReportParameterType() {
		return reportParameterType;
	}

	/**
	 * @param reportParameterType the reportParameterType to set
	 */
	public void setReportParameterType(String reportParameterType) {
		this.reportParameterType = reportParameterType;
	}

	/**
	 * @return the reportParameterPriority
	 */
	public String getReportParameterPriority() {
		return reportParameterPriority;
	}

	/**
	 * @param reportParameterPriority the reportParameterPriority to set
	 */
	public void setReportParameterPriority(String reportParameterPriority) {
		this.reportParameterPriority = reportParameterPriority;
	}

	/**
	 * @return the reportParameterMandatory
	 */
	public String getReportParameterMandatory() {
		return reportParameterMandatory;
	}

	/**
	 * @param reportParameterMandatory the reportParameterMandatory to set
	 */
	public void setReportParameterMandatory(String reportParameterMandatory) {
		this.reportParameterMandatory = reportParameterMandatory;
	}

	/**
	 * @return the reportParameterAlias
	 */
	public String getReportParameterAlias() {
		return reportParameterAlias;
	}

	/**
	 * @param reportParameterAlias the reportParameterAlias to set
	 */
	public void setReportParameterAlias(String reportParameterAlias) {
		this.reportParameterAlias = reportParameterAlias;
	}

	/**
	 * @return the parameterList
	 */
	public List<ReportParameter> getParameterList() {
		return parameterList;
	}

	/**
	 * @param parameterList the parameterList to set
	 */
	public void setParameterList(Set<ReportParameter> parameterList) {
		this.parameterList = new ArrayList<ReportParameter>(parameterList);	
		Comparator<ReportParameter> rptParaComparator = new Comparator<ReportParameter>() {
			@Override
			public int compare(ReportParameter o1, ReportParameter o2) {
				// return a negative integer, zero, or a positive integer as the first argument is less than, equal to, or greater than the second.
				if (o1!=null && o1.getPriority()!=null && o2!=null && o1.getPriority()!=null) {
					int p1 = o1.getPriority().intValue();
					int p2 = o2.getPriority().intValue();
					if (p1 < p2) {
						return -1;
					} else if (p1 > p2) {
						return 1;
					} else {
						return 0;
					}
				} 
				//if priority is null then move to last order.
				if (o1==null || o1.getPriority()==null) {
					return 1;
				}
				if (o2==null || o2.getPriority()==null) {
					return -1;
				}
				return 0;
			}
		};
		//System.out.println("before sort, top#1="+(this.parameterList!=null && this.parameterList.size()>0?this.parameterList.get(0).getPriority():""));
		Collections.sort(this.parameterList, rptParaComparator);
		//System.out.println(" after sort, top#1="+(this.parameterList!=null && this.parameterList.size()>0?this.parameterList.get(0).getPriority():""));
	}

	/**
	 * @return the reportPriority
	 */
	public String getReportPriority() {
		return reportPriority;
	}

	/**
	 * @param reportPriority the reportPriority to set
	 */
	public void setReportPriority(String reportPriority) {
		this.reportPriority = reportPriority;
	}

	/**
	 * @return the categoryLoad
	 */
	public String getCategoryLoad() {
		return categoryLoad;
	}

	/**
	 * @param categoryLoad the categoryLoad to set
	 */
	public void setCategoryLoad(String categoryLoad) {
		this.categoryLoad = categoryLoad;
	}

	public String getMode() {
		return mode;
	}

	public void setMode(String mode) {
		this.mode = mode;
	}

	public String getDeleteCollection() {
		return deleteCollection;
	}

	public void setDeleteCollection(String deleteCollection) {
		this.deleteCollection = deleteCollection;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	/**
	 * @return the reportCategoryDesc
	 */
	public String getReportCategoryDesc() {
		return reportCategoryDesc;
	}

	/**
	 * @param reportCategoryDesc the reportCategoryDesc to set
	 */
	public void setReportCategoryDesc(String reportCategoryDesc) {
		this.reportCategoryDesc = reportCategoryDesc;
	}
	
	//class re-factor (moved from CodeValueForm)
	public String getReportFileName() {
		return reportFileName;
	}
	public void setReportFileName(String reportFileName) {
		this.reportFileName = reportFileName;
	}
	
	public String getRptFrmt() {
		return rptFrmt;
	}
	public void setRptFrmt(String rptFrmt) {
		this.rptFrmt = rptFrmt;
	}
}
