	/**
 * 
 */
package com.nec.asia.nic.admin.job.controller;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.StringTokenizer;

import javax.servlet.http.HttpServletRequest;

import oracle.net.aso.l;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.nec.asia.nic.framework.PageRequest;
import com.nec.asia.nic.framework.PaginatedResult;
import com.nec.asia.nic.framework.admin.code.domain.Parameters;
import com.nec.asia.nic.framework.admin.code.domain.ParametersId;
import com.nec.asia.nic.framework.admin.code.service.ParametersService;
import com.nec.asia.nic.framework.admin.job.domain.Job;
import com.nec.asia.nic.framework.admin.job.domain.JobExecutionDetails;
import com.nec.asia.nic.framework.admin.job.domain.JobExecutionHistory;
import com.nec.asia.nic.framework.admin.job.domain.JobExecutionHistoryLatest;
import com.nec.asia.nic.framework.admin.job.dto.JobMonitorDto;
import com.nec.asia.nic.framework.common.LabelValueBean;
import com.nec.asia.nic.framework.job.scheduler.JobScheduleService;
import com.nec.asia.nic.framework.job.scheduler.exception.JobScheduleException;
import com.nec.asia.nic.framework.report.service.AuditAccessLogService;
import com.nec.asia.nic.util.Constants;
import com.nec.asia.nic.util.pagingUtil;
import com.nec.asia.nic.utils.DateUtil;

/**
 * @author Tue
 *
 */

@Controller
@RequestMapping(value = "/batchJobMnt")
public class EppJobScheMonitorController {
	
	private static final Logger logger = LoggerFactory.getLogger(EppJobScheMonitorController.class);
	
	@Autowired(required=true)
	private JobScheduleService jobScheduleService;
	
	@Autowired
	private AuditAccessLogService auditAccessLogService;
	
	@Autowired
	private ParametersService parametersService;
	
	private void logMessage(Exception ex) {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		PrintStream ps = new PrintStream(baos);
		ex.printStackTrace(ps);
		logger.error(ex.getMessage() + " : \n" + new String(baos.toByteArray()));
	}
	
	@RequestMapping(value="/view")
	@ExceptionHandler
	public ModelAndView view(HttpServletRequest request, ModelMap modelMap) {
		logger.debug("Method View is starting...");
		
		long startTimeMs = System.currentTimeMillis();
		Throwable throwable = null;
		
		try {
			ArrayList<String> autoRefreshList = this.getAutoRefreshList();
			request.setAttribute("autoRefreshList", autoRefreshList);
		} catch (Exception ex) {
			logMessage(ex);
			throwable = ex;
		} finally {
			try {
				String functionName = "view";
				Object[] args = null;
				long timeMs = System.currentTimeMillis() - startTimeMs;
				//auditAccessLogService.saveAuditAccessLogForWeb(request, functionName, args, throwable, timeMs);
			} catch (Exception exp) {
				logMessage(exp);
			}
		}
		//Phúc edit
		PaginatedResult<JobExecutionHistoryLatest> list = new PaginatedResult<JobExecutionHistoryLatest>();
		try {
			PageRequest pageRequest = this.generateReqPage(request);
			pageRequest.setMaxRecords(Constants.PAGE_SIZE_DEFAULT);
			pageRequest.setSortname("createdate");
			pageRequest.setSortorder("desc");
			list = jobScheduleService.findAllJobExecLatestForPagination(pageRequest);
			List<JobExecutionHistoryLatest> listExecJob = list.getRows();
			this.sortList(listExecJob, pageRequest.getSortorder(), pageRequest.getSortorder());
			//modelMap.addAttribute("jobList", listExecJob);
			//phúc edit
			int firstResults = (pageRequest.getPageNo() - 1) < 0 ? 0 : (pageRequest.getPageNo() - 1) * pageRequest.getMaxRecords();
			modelMap.addAttribute("pageSize", pageRequest.getMaxRecords());
			modelMap.addAttribute("pageNo", pageRequest.getPageNo());
			modelMap.addAttribute("totalPage", pagingUtil.totalPage(list.getTotal(), pageRequest.getMaxRecords()));
			modelMap.addAttribute("startIndex", list.getTotal() != 0 ? firstResults + 1 : 0);
			modelMap.addAttribute("totalRecord", list.getTotal());
			modelMap.addAttribute("endIndex", firstResults + list.getRowSize());
			modelMap.addAttribute("jobList", list.getRows());
			//end
		} catch (JobScheduleException ex) {
			
		}
		//end
		
		modelMap.addAttribute("fnSelected", Constants.HEADING_NIC_BATCHJOBS_APP);
		return new ModelAndView("batchJobMnt.jobView");
	}
	
	@ResponseBody
	@RequestMapping(value="/getJobList")
	@ExceptionHandler
	public PaginatedResult<JobExecutionHistoryLatest> getJobList(HttpServletRequest request) {
		logger.debug("Method getJobList is starting...");
		
		PaginatedResult<JobExecutionHistoryLatest> list = new PaginatedResult<JobExecutionHistoryLatest>();
		try {
			PageRequest pageRequest = this.generateReqPage(request);
			list = jobScheduleService.findAllJobExecLatestForPagination(pageRequest);
			List<JobExecutionHistoryLatest> listExecJob = list.getRows();
			this.sortList(listExecJob, pageRequest.getSortorder(), pageRequest.getSortorder());
			list.setRows(listExecJob);
		} catch (JobScheduleException ex) {
			logMessage(ex);
		}
		
		return list;
	}
	

	@RequestMapping(value="/scheDetail/{jobId}")
	@ExceptionHandler
	public ModelAndView scheduleDetail(HttpServletRequest request, @PathVariable String jobId, ModelMap modelMap) {
		logger.debug("Method scheduleDetail is starting...");
		
		long startTimeMs = System.currentTimeMillis();
		Throwable throwable = null;
		/**/
		JobMonitorDto eppJobMonitorForm = new JobMonitorDto();
		try {
			Job job = jobScheduleService.getJob(jobId.trim());
			eppJobMonitorForm.setJobId(job.getJobId());
			eppJobMonitorForm.setJobName(job.getJobName());
		} catch (JobScheduleException ex) {
			logMessage(ex);
			throwable = ex;
		} finally {
			try {
				String functionName = "Xem chi tiết lịch trình";
				Object[] args = {eppJobMonitorForm};
				long timeMs = System.currentTimeMillis() - startTimeMs;
				auditAccessLogService.saveAuditAccessLogForWeb(request, functionName, args, throwable, timeMs);
			} catch (Exception exp) {
				logMessage(exp);
			}
		}
		PaginatedResult<JobExecutionHistory> list = new PaginatedResult<JobExecutionHistory>();
		try {
			 PageRequest pageRequest = this.generateReqPage(request);
			 int startIndex = 0;
	         String pageNumber = String.valueOf(pageRequest.getPageNo());
	         //pageRequest.setPageNo(Integer.parseInt(pageNumber));
	         if (StringUtils.isNotBlank(pageNumber)) {
	             startIndex = (Integer.parseInt(pageNumber) - 1) * pageRequest.getMaxRecords();
	         }
	         pageRequest.setFirstRowIndex(startIndex);
			 pageRequest.setSortname("executionDate");
			 pageRequest.setSortorder("desc");
			 list = jobScheduleService.findJobExecForPaginationByJobId(jobId, pageRequest);
			 modelMap.addAttribute("pageNo", pageRequest.getPageNo());
			 modelMap.addAttribute("totalPage", pagingUtil.totalPage(list.getTotal(), pageRequest.getMaxRecords()));
			 modelMap.addAttribute("jobList", list.getRows());
			 modelMap.addAttribute("startIndex", startIndex + 1);
			 modelMap.addAttribute("totalRecord", list.getTotal());
			 modelMap.addAttribute("endIndex", startIndex + list.getRowSize());
		} catch (JobScheduleException ex) {
			
		}
		
		modelMap.addAttribute("fnSelected", Constants.HEADING_NIC_BATCHJOBS_APP);
		return new ModelAndView("batchJobMnt.scheDetail", "eppJobMonitorForm", eppJobMonitorForm);
	}
	
	
	
	@ResponseBody
	@RequestMapping(value="/getScheDetail")
	@ExceptionHandler
	public PaginatedResult<JobExecutionHistory> getScheDetail(HttpServletRequest request, @ModelAttribute("eppJobMonitorForm") JobMonitorDto eppJobMonitorForm) {
		logger.debug("Method getJobList is starting...");
		
		PaginatedResult<JobExecutionHistory> list = new PaginatedResult<JobExecutionHistory>();
		try {
			 PageRequest pageRequest = this.generateReqPage(request);
			list = jobScheduleService.findJobExecForPaginationByJobId(eppJobMonitorForm.getJobId(), pageRequest);
		} catch (JobScheduleException ex) {
			logMessage(ex);
		}
		
		return list;
	}
	
	@ResponseBody
	@RequestMapping(value="/delScheLog/{logId}" )
	@ExceptionHandler
	public String delScheduleLog(HttpServletRequest request, @PathVariable String logId) throws Exception {
		logger.debug("Method delScheduleLog is starting...");
		
		try {
			jobScheduleService.deleteLogById(Long.parseLong(logId));
			return "success";
		} catch (Exception ex) {
			logMessage(ex);
			return "fail";
		} 
	}
	
	@RequestMapping(value="/delAllLog/{jobId}" )
	@ExceptionHandler
	public ModelAndView delAllScheduleLog(HttpServletRequest request, @PathVariable String jobId, ModelMap modelMap) throws Exception {
		logger.debug("Method delAllScheduleLog is starting...");
		
		long startTimeMs = System.currentTimeMillis();
		Throwable throwable = null;
		try {
			jobScheduleService.deleteLogHistory(jobId);
		} catch (JobScheduleException ex) {
			logMessage(ex);
			throwable = ex;
		} finally {
			try {
				String functionName = "Xóa tất lịch sử log";
				Object[] args = {jobId};
				long timeMs = System.currentTimeMillis() - startTimeMs;
				auditAccessLogService.saveAuditAccessLogForWeb(request, functionName, args, throwable, timeMs);
			} catch (Exception exp) {
				logMessage(exp);
			}
		}
		
		return view(request, modelMap);
	}

	@RequestMapping(value="/viewScheLog/{logId}" )
	@ExceptionHandler
	public ModelAndView viewScheduleLog(@ModelAttribute("eppJobMonitorForm") JobMonitorDto eppJobMonitorForm, HttpServletRequest request, @PathVariable String logId, ModelMap modelMap) throws Exception {
		logger.debug("Method viewScheduleLog is starting...");
		
		long startTimeMs = System.currentTimeMillis();
		Throwable throwable = null;
		JobExecutionHistory hist = null;
		try {
			hist = jobScheduleService.getJobExecutionHistoryByPrimaryKey(Long.valueOf(logId));
			if(hist != null && StringUtils.isNotBlank(hist.getDetail())) {
				eppJobMonitorForm.setDetail(hist.getDetail());
			} else {
				eppJobMonitorForm.setDetail("");
			}
			
			Job job = jobScheduleService.getJob(hist.getJobId());
			eppJobMonitorForm.setJobId(job.getJobId());
			eppJobMonitorForm.setJobName(job.getJobName());
			eppJobMonitorForm.setExecutionDate(DateUtil.parseDate(hist.getExecutionDate(), DateUtil.FORMAT_DD_MM_YYYY_HH_MM_SS));
			eppJobMonitorForm.setCompleteDate(DateUtil.parseDate(hist.getCompleteDate(), DateUtil.FORMAT_DD_MM_YYYY_HH_MM_SS));
			eppJobMonitorForm.setStatus(hist.getStatus());
			eppJobMonitorForm.setMessage(hist.getMessage());
			request.setAttribute("filterType", this.getFilterByType());
		} catch (Exception ex) {
			logMessage(ex);
			throwable = ex;
		} finally {
			try {
				String functionName = "Hiển thị log Schedule";
				Object[] args = {hist};
				long timeMs = System.currentTimeMillis() - startTimeMs;
				auditAccessLogService.saveAuditAccessLogForWeb(request, functionName, args, throwable, timeMs);
			} catch (Exception exp) {
				logMessage(exp);
			}
		}
		modelMap.addAttribute("fnSelected", Constants.HEADING_NIC_BATCHJOBS_APP);
		return new ModelAndView("batchJobMnt.scheLog", "eppJobMonitorForm", eppJobMonitorForm);
	}
	
	@ResponseBody
	@RequestMapping(value="/getScheLog" )
	@ExceptionHandler
	public PaginatedResult<JobExecutionDetails> getScheduleLog(@ModelAttribute("eppJobMonitorForm") JobMonitorDto eppJobMonitorForm, HttpServletRequest request) throws Exception {
		logger.debug("Method getScheduleLog is starting...");
		
		PaginatedResult<JobExecutionDetails> list = null;
		try {
			PageRequest pageRequest = this.generateReqPage(request);
			list = jobScheduleService.findJobExecDetailsForPaginationByLogId(eppJobMonitorForm.getLogId(), eppJobMonitorForm.getFilterByName(), pageRequest);
		} catch (Exception ex) {
			logMessage(ex);
		}
		
		return list;
	}
	
	private PageRequest generateReqPage(HttpServletRequest request) {
		 String pages = !StringUtils.isEmpty(request.getParameter("pageNo")) ? request.getParameter("pageNo") : "1";
         String sortname = request.getParameter("sortname");
         String sortorder = request.getParameter("sortorder");
         String rp = request.getParameter("rp");

         int pageSize = Constants.PAGE_SIZE_DEFAULT;
         if ( StringUtils.isNotEmpty(rp) && !"NaN".equals(rp)) {
             pageSize = Integer.parseInt(rp);
         }

         int startIndex = 0;
         String pageNumber = pages;
         if (StringUtils.isNotBlank(pageNumber)) {
             startIndex = (Integer.parseInt(pageNumber) - 1) * pageSize;
         }
         
         PageRequest pageRequest = new PageRequest();
         pageRequest.setCalculateRecordCount(true);
         pageRequest.setFirstRowIndex(startIndex);
         pageRequest.setMaxRecords(pageSize);
         pageRequest.setSortname(sortname);
         pageRequest.setSortorder(sortorder);
         pageRequest.setPageNo(Integer.parseInt(pageNumber));
		
		return pageRequest;
	}
	
	public ArrayList<String> getAutoRefreshList() {

		ArrayList<String> list = new ArrayList<String>();
		try {
			ParametersId id = new ParametersId();
			id.setParaName("REFRESH_INTERVAL_LIST");
			id.setParaScope(Parameters.SCOPE_SYSTEM);
			
			Parameters eppParameters = parametersService.findById(id);
			if (eppParameters != null) {
				String autoRefresh = eppParameters.getParaShortValue();
				if (autoRefresh != null) {
					StringTokenizer tokens = new StringTokenizer(autoRefresh, ",");
					while (tokens.hasMoreTokens()) {
						list.add(tokens.nextElement().toString().trim());
					}
				}
			}
			if (CollectionUtils.isEmpty(list)) {
				list.add("30");
			}
			
		} catch (Exception e) {
			logger.error("Error occured while getting the Auto Refresh List"+ e.getMessage());
		}
		
		return list;
	}
	
	public List<LabelValueBean> getFilterByType() {
		return new ArrayList <LabelValueBean> () {{ 
			this.add(new LabelValueBean("All",""));
			this.add(new LabelValueBean("Error","E"));
			this.add(new LabelValueBean("Warning","W"));
			this.add(new LabelValueBean("Information","I"));
		}};
	}
	
	private void sortList(List<JobExecutionHistoryLatest> list, final String propertyName, final String order) {

	    if (list.size() > 0) {
	        Collections.sort(list, new Comparator<JobExecutionHistoryLatest>() {
	            public int compare(final JobExecutionHistoryLatest object1, final JobExecutionHistoryLatest object2) {
	                int result = 0;
	                if(order.equals("asc")) {
	                	if(propertyName.equalsIgnoreCase("jobId")) {
	                		result = object1.getJobId().compareToIgnoreCase(object2.getJobId());
	                	} else if(propertyName.equalsIgnoreCase("jobName")) {
	                		result = object1.getJobName().compareToIgnoreCase(object2.getJobName());
	                	} else if(propertyName.equalsIgnoreCase("message")) {
	                		result = object1.getMessage().compareToIgnoreCase(object2.getMessage());
	                	} else if(propertyName.equalsIgnoreCase("executionDate")) {
	                		result = object1.getExecutionDate().compareTo(object2.getExecutionDate());
	                	}
	                } else {
	                	if(propertyName.equalsIgnoreCase("jobId")) {
	                		result = object2.getJobId().compareToIgnoreCase(object1.getJobId());
	                	} else if(propertyName.equalsIgnoreCase("jobName")) {
	                		result = object2.getJobName().compareToIgnoreCase(object1.getJobName());
	                	} else if(propertyName.equalsIgnoreCase("message")) {
	                		result = object2.getMessage().compareToIgnoreCase(object1.getMessage());
	                	} else if(propertyName.equalsIgnoreCase("executionDate")) {
	                		result = object2.getExecutionDate().compareTo(object1.getExecutionDate());
	                	}
	                }
	                return result;
	            }
	        });
	    }
	}
	
}
