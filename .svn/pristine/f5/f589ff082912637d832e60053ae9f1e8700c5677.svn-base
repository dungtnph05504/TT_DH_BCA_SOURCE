/**
 * 
 */
package com.nec.asia.nic.admin.job.controller;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.ModelAndView;

import com.nec.asia.nic.comp.trans.domain.NicTransaction;
import com.nec.asia.nic.comp.trans.service.NicTransactionService;
import com.nec.asia.nic.comp.trans.service.impl.NicTransactionServiceImpl;
import com.nec.asia.nic.dx.trans.MainTransaction;
import com.nec.asia.nic.dx.trans.RegistrationData;
import com.nec.asia.nic.framework.PageRequest;
import com.nec.asia.nic.framework.PaginatedResult;
import com.nec.asia.nic.framework.admin.code.service.ParametersService;
import com.nec.asia.nic.framework.admin.job.domain.Job;
import com.nec.asia.nic.framework.admin.job.domain.JobSchedule;
import com.nec.asia.nic.framework.admin.job.domain.JobScheduleId;
import com.nec.asia.nic.framework.admin.job.dto.JobDto;
import com.nec.asia.nic.framework.admin.job.dto.JobScheduleDto;
import com.nec.asia.nic.framework.job.scheduler.JobScheduleService;
import com.nec.asia.nic.framework.job.scheduler.dataAccess.type.ActionOnEvent;
import com.nec.asia.nic.framework.job.scheduler.dataAccess.type.Frequency;
import com.nec.asia.nic.framework.job.scheduler.exception.JobScheduleException;
import com.nec.asia.nic.framework.report.service.AuditAccessLogService;
import com.nec.asia.nic.util.Constants;
import com.nec.asia.nic.util.MiscXmlConvertor;
import com.nec.asia.nic.util.pagingUtil;
import com.nec.asia.nic.utils.DateUtil;
import com.nec.asia.nic.web.session.UserSession;

import org.apache.commons.fileupload.*;
import org.apache.commons.fileupload.disk.*; 
import org.apache.commons.fileupload.servlet.*;

/**
 * @author Tue
 *
 */

@Controller
@RequestMapping(value = "/batchJobMgmt")
public class EppJobScheAdminController {
	
	private static final Logger logger = LoggerFactory.getLogger(EppJobScheAdminController.class);
	private final static String MODE_EDIT = "EDIT";
	private final static String MODE_ADD = "ADD";
	private final static String MODE_AMEND = "AMEND";
	private final static String MODE_VIEW = "VIEW";
	
	@Autowired(required=true)
	private JobScheduleService jobScheduleService;
	
	@Autowired
	private NicTransactionService nicTransactionService = null;
	
	//30 Dec 2013 (chris lai) : update with audit record
	@Autowired
	private AuditAccessLogService auditAccessLogService;
	
	@Autowired
	private ParametersService parametersService;
	
	@RequestMapping(value="/view")
	@ExceptionHandler
	public ModelAndView view(HttpServletRequest request, ModelMap modelMap) {
		logger.info("Entry at view request ");
		PaginatedResult<Job> list = new PaginatedResult<Job>();		
			PageRequest pageRequest = this.generateReqPage(request);
			try {
				list = jobScheduleService.findAllJobForPagination(pageRequest);
			} catch (JobScheduleException e) {				
				e.printStackTrace();
				list = new PaginatedResult<>(0, 1, new ArrayList<Job>());
			}
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
		//modelMap.addAttribute("jobList", list.getRows());
		modelMap.addAttribute("fnSelected", Constants.HEADING_NIC_BATCHJOBS);
		return new ModelAndView("batchJobMgmt.jobView");
	}
	
	@ResponseBody
	@RequestMapping(value="/getJobs")
	@ExceptionHandler
	public PaginatedResult<Job> getJobs(HttpServletRequest request) {
		logger.info("Entry at getJobs request ");
		
		PaginatedResult<Job> list = new PaginatedResult<Job>();
		try {
			PageRequest pageRequest = this.generateReqPage(request);
			pageRequest.setSortname("createdate");
			list = jobScheduleService.findAllJobForPagination(pageRequest);
		} catch (JobScheduleException ex) {
			logMessage(ex);
		}
		
		return list;
	}
	
	@RequestMapping(value="/forwardReq" , method = RequestMethod.GET)
	@ExceptionHandler
	public ModelAndView frwReqtoSave(ModelMap model) throws Exception{	
		logger.info("Entry at frwReqtoSave request ");
		
		model.addAttribute("fnSelected", Constants.HEADING_NIC_BATCHJOBS);
		return new ModelAndView ("batchJobMgmt.jobSave","eppBatchJobForm", new JobDto());
		
	}
	
	@RequestMapping(value="/createJob" )
	@ExceptionHandler
	public ModelAndView saveJob( HttpServletRequest httpRequest, @ModelAttribute("eppBatchJobForm") JobDto eppBatchJobForm ,BindingResult result, ModelMap model ){
		logger.info("Entry at saveJob request ");

		long startTimeMs = System.currentTimeMillis();
		Throwable throwable = null;
		
		HttpSession session = httpRequest.getSession();
		UserSession userSession = (UserSession) session.getAttribute("userSession");
		List<String> messages = new LinkedList<String>();
		List<String> error = new ArrayList<String>();
		try {
			Job job = new Job();
			job.setJobId(eppBatchJobForm.getJobId().toString());
			job.setJobName(eppBatchJobForm.getJobName().toUpperCase().trim());
			job.setJobClass(eppBatchJobForm.getJobClass().trim());
			job.setJobImplClass(Class.forName(eppBatchJobForm.getJobClass().trim()));
			job.setJobDescription(eppBatchJobForm.getJobDesc().toUpperCase().trim());
			job.setJobProperties(eppBatchJobForm.getJobProperties());
			job.setCreateby(userSession.getUserName());
			job.setCreatedate(DateUtil.getSystemTime());
			
			jobScheduleService.createJob(job);
			
			messages.add("Tạo thành công batchJob : " + eppBatchJobForm.getJobId() + ".");
			httpRequest.setAttribute("messages", messages);
		} catch (Exception ex) {
			error.add("Xảy ra lỗi trong khi tạo Công việc, lý do:" + ex.getMessage());
			httpRequest.setAttribute("Errors", error);
			logMessage(ex);
			throwable = ex;
		} finally {
			try {
				String functionName = "Thêm mới công việc (Job)";
				Object[] args = { eppBatchJobForm };
				long timeMs = System.currentTimeMillis() - startTimeMs;
				auditAccessLogService.saveAuditAccessLogForWeb(httpRequest, functionName, args, throwable, timeMs);
			} catch (Exception exp) {
				logMessage(exp);
			}
		}

		logger.info("Exit at createBatchJob request ");

		if(error != null && error.size() > 0) {
			model.addAttribute("fnSelected", Constants.HEADING_NIC_BATCHJOBS);
			return new ModelAndView("batchJobMgmt.jobSave", "eppBatchJobForm", eppBatchJobForm);
		}
		return view(httpRequest, model);
	}
	
	@RequestMapping(value="/editJob/{jobId}")
	@ExceptionHandler
	public ModelAndView editJob(HttpServletRequest httpRequest, @PathVariable String jobId, ModelMap model) throws JobScheduleException {
		logger.info("Entry at editBatchJob request ");

		long startTimeMs = System.currentTimeMillis();
		Throwable throwable = null;
		
		JobDto eppBatchJobForm = new JobDto();
		try {
			Job eppJob = jobScheduleService.getJob(jobId);
			if (eppJob != null) {
				eppBatchJobForm.setMode(MODE_EDIT);
				eppBatchJobForm.setJobId(eppJob.getJobId());
				eppBatchJobForm.setJobName(eppJob.getJobName());
				eppBatchJobForm.setJobDesc(eppJob.getJobDescription().toUpperCase());
				eppBatchJobForm.setJobClass(eppJob.getJobClass());
				eppBatchJobForm.setJobProperties(eppJob.getJobProperties());
			}
		} catch (Exception ex) {
			List<String> error = new ArrayList<String>();
			error.add("Xảy ra lỗi trong khi chỉnh sửa công việc, lý do:" + ex.getMessage());
			httpRequest.setAttribute("Errors", error);
			logMessage(ex);
			throwable = ex;
		} finally {
			try {
				String functionName = "Cập nhật công việc (Job)";
				Object[] args = { eppBatchJobForm };
				long timeMs = System.currentTimeMillis() - startTimeMs;
				auditAccessLogService.saveAuditAccessLogForWeb(httpRequest, functionName, args, throwable, timeMs);
			} catch (Exception exp) {
				logMessage(exp);
			}
		}

		logger.info("Exit at editBatchJob request ");

		model.addAttribute("fnSelected", Constants.HEADING_NIC_BATCHJOBS);

		return new ModelAndView("batchJobMgmt.jobSave", "eppBatchJobForm", eppBatchJobForm);
	}
	
	@RequestMapping(value="/updateJob")
	@ExceptionHandler
	public ModelAndView updateJob(HttpServletRequest httpRequest, @ModelAttribute("eppBatchJobForm") JobDto eppBatchJobForm, ModelMap model) throws Exception{
		logger.info("Entry at updateJob request ");

		long startTimeMs = System.currentTimeMillis();
		Throwable throwable = null;
		
		boolean status_flg = true;
		HttpSession session = httpRequest.getSession();
		UserSession userSession = (UserSession) session.getAttribute("userSession");
		try {
			Job jobEntity = jobScheduleService.getJob(eppBatchJobForm.getJobId());
			jobEntity.setJobName(eppBatchJobForm.getJobName());
			jobEntity.setJobDescription(eppBatchJobForm.getJobDesc());
			jobEntity.setJobClass(eppBatchJobForm.getJobClass().trim());
			jobEntity.setJobImplClass(Class.forName(eppBatchJobForm.getJobClass().trim()));
			jobEntity.setJobProperties(eppBatchJobForm.getJobProperties().toUpperCase().trim());
			jobEntity.setUpdateby(userSession.getUserId());
			jobEntity.setUpdatedate(DateUtil.getSystemTimestamp());
			
			jobScheduleService.updateJob(jobEntity);
			
			List<String> messages  = new ArrayList<String>();
			messages.add("Sửa thành công batchJob: " + eppBatchJobForm.getJobId() + ".");
			httpRequest.setAttribute("messages", messages);
			
		} catch(Exception ex) {
			status_flg = false;
			List<String> error = new LinkedList<String>();
			error.add("Sửa không thành công batchJob : " + eppBatchJobForm.getJobId());
			httpRequest.setAttribute("Errors", error);
			logMessage(ex);
			throwable = ex;
		} finally {
			try {
				String functionName = "Cập nhật công việc (Job)";
				Object[] args = { eppBatchJobForm };
				long timeMs = System.currentTimeMillis() - startTimeMs;
				auditAccessLogService.saveAuditAccessLogForWeb(httpRequest, functionName,args,throwable, timeMs);
			} catch(Exception exp) {
				logMessage(exp);
			}
		}
		
	    logger.info("Exit at updateBatchJob request ");
	    if(status_flg) {
	    	return view(httpRequest, model);
	    } else {
	    	model.addAttribute("fnSelected", Constants.HEADING_NIC_BATCHJOBS);
	    	eppBatchJobForm.setMode(MODE_EDIT);
	    	return new ModelAndView("batchJobMgmt.jobSave", "eppBatchJobForm", eppBatchJobForm);
	    }
	    
	}
	
	@RequestMapping(value="/deleteJob")
	@ExceptionHandler
	public ModelAndView deleteJob(HttpServletRequest request, ModelMap modelMap) throws Exception {
		logger.info("Entry at deleteJob request ");
		
		long startTimeMs = System.currentTimeMillis();
		Throwable throwable = null;
		
		List<String> messages = new LinkedList<String>();
		boolean success_flg = true;
		Job eppJob = null;
		String jobId = request.getParameter("jobId");
		try{
			eppJob= jobScheduleService.getJob(jobId);
			if(eppJob!=null) {
				// Check schedule exist
				List<JobSchedule> scheduleList = jobScheduleService.getJobScheduleByJobId(jobId);
				if(CollectionUtils.isEmpty(scheduleList)) {
					jobScheduleService.deleteJob(eppJob);
					messages.add("Xóa thành công batchJob: " + jobId + ".");
					request.setAttribute("messages", messages);
				} else {
					success_flg = false;
					request.setAttribute("Errors", "Xóa không thành công batchJob :"+jobId+" như công việc này có "+scheduleList.size()+" schedule(s).");
				}
		 	}
		} catch (Exception ex) {
			List<String> error = new LinkedList<String>();
			error.add("Lỗi khi xóa batch job : " + jobId);
			request.setAttribute("Errors", error);
			logMessage(ex);
			throwable = ex;
		} finally {
			try {
				String functionName = "Xóa công việc (Job)";
				Object[] args = { eppJob };
				long timeMs = System.currentTimeMillis() - startTimeMs;
				auditAccessLogService.saveAuditAccessLogForWeb(request, functionName,args,throwable, timeMs);
			} catch(Exception exp){
				logMessage(exp);
			}
		}
		
		if(success_flg) {
			return view(request, modelMap);
		} else {
			return editJob(request, jobId, modelMap);
		}
	}
	
	@RequestMapping(value="/viewJob/{jobId}")
	@ExceptionHandler
	public ModelAndView viewJob( @ModelAttribute("eppBatchJobForm") JobDto batchJobForm, HttpServletRequest request, @PathVariable String jobId, ModelMap model) throws JobScheduleException {
		logger.info("Entry at viewJob request ");
		
		long startTimeMs = System.currentTimeMillis();
		Throwable throwable = null;
		
		String prevMode = batchJobForm.getMode();
		String mode = "";
		if(StringUtils.isEmpty(prevMode) || (StringUtils.isNotEmpty(prevMode) && !prevMode.equals(MODE_EDIT))) {
			mode = MODE_VIEW;
		} else {
			mode = prevMode;
			
		}
		request.setAttribute("prevMode", mode);
		JobDto eppBatchJobForm = new JobDto();
		eppBatchJobForm.setMode(mode);
		try {
			Job eppJob = jobScheduleService.getJob(jobId);
			
			eppBatchJobForm.setJobId(eppJob.getJobId());
			eppBatchJobForm.setJobName(eppJob.getJobName());
			eppBatchJobForm.setJobDesc(eppJob.getJobDescription());
			eppBatchJobForm.setJobClass(eppJob.getJobClass());
			eppBatchJobForm.setJobProperties(eppJob.getJobProperties());
			
		} catch (JobScheduleException ex) {
			logMessage(ex);
			throwable = ex;
		} finally {
			try {
				String functionName = "Hiển thị công việc (Job)";
				Object[] args = { eppBatchJobForm };
				long timeMs = System.currentTimeMillis() - startTimeMs;
				auditAccessLogService.saveAuditAccessLogForWeb(request, functionName,args,throwable, timeMs);
			} catch(Exception exp){
				logMessage(exp);
			}
		}
		//phúc thêm 30/5/2019
		PaginatedResult<JobSchedule> list = new PaginatedResult<JobSchedule>();
		PageRequest pageRequest = this.generateReqPage(request);
		try {
			pageRequest.setSortname("startDate");
			list = jobScheduleService.findJobScheForPaginationByJobId(jobId, pageRequest);
		} catch (JobScheduleException ex) {
			ex.printStackTrace();
		} 
		//phúc edit
		int firstResults = (pageRequest.getPageNo() - 1) < 0 ? 0 : (pageRequest.getPageNo() - 1) * pageRequest.getMaxRecords();
		model.addAttribute("pageSize", pageRequest.getMaxRecords());
		model.addAttribute("pageNo", pageRequest.getPageNo());
		model.addAttribute("totalPage", pagingUtil.totalPage(list.getTotal(), pageRequest.getMaxRecords()));
		model.addAttribute("startIndex", list.getTotal() != 0 ? firstResults + 1 : 0);
		model.addAttribute("totalRecord", list.getTotal());
		model.addAttribute("endIndex", firstResults + list.getRowSize());
		model.addAttribute("jobList", list.getRows());
		//end
		model.addAttribute("idJob", jobId);
		model.addAttribute("fnSelected", Constants.HEADING_NIC_BATCHJOB_SCHE);
		//model.addAttribute("jobList", list.getRows());
		return new ModelAndView("display.scheView", "eppBatchJobForm", eppBatchJobForm);
	}
	
	@ResponseBody
	@RequestMapping(value="/getSchedule/{jobId}")
	@ExceptionHandler
	public PaginatedResult<JobSchedule> getJobSchedule(HttpServletRequest request, @PathVariable String jobId) throws JobScheduleException {
		logger.info("Entry at getJobSche request ");
		
		PaginatedResult<JobSchedule> list = new PaginatedResult<JobSchedule>();
		try {
			PageRequest pageRequest = this.generateReqPage(request);
			list = jobScheduleService.findJobScheForPaginationByJobId(jobId, pageRequest);
		} catch (JobScheduleException ex) {
			logMessage(ex);
		} 
		
		return list;
	}
	
	@RequestMapping(value="/addSchedule" )
	@ExceptionHandler
	public ModelAndView addSchedule(@ModelAttribute("eppBatchJobForm") JobDto batchJobForm, HttpServletRequest request, ModelMap modelMap) throws Exception {
		logger.info("Entry at addSchedule request ");
		
		long startTimeMs = System.currentTimeMillis();
		Throwable throwable = null;
		JobScheduleDto eppJobScheduleForm = new JobScheduleDto();
		try {
			eppJobScheduleForm.init();
			eppJobScheduleForm.setMode(MODE_ADD);
			eppJobScheduleForm.setPrevMode(batchJobForm.getMode());
			eppJobScheduleForm.setJobId(request.getParameter("jobId"));
			eppJobScheduleForm.setJobName(request.getParameter("jobName"));
			eppJobScheduleForm.setJobClass(request.getParameter("jobClass"));
		} catch (JobScheduleException ex) {
			logMessage(ex);
			throwable = ex;
		} finally {
			try {
				String functionName = "Thêm mới lịch trình";
				Object[] args = { eppJobScheduleForm };
				long timeMs = System.currentTimeMillis() - startTimeMs;
				auditAccessLogService.saveAuditAccessLogForWeb(request, functionName,args,throwable, timeMs);
			} catch(Exception exp){
				logMessage(exp);
			}
		}
		modelMap.addAttribute("fnSelected", Constants.HEADING_NIC_BATCHJOB_SCHE);
		return new ModelAndView ("display.scheSave","eppJobScheduleForm",eppJobScheduleForm);
	}
	
	@ResponseBody
	@RequestMapping(value="/delSchedule/{jobId}/{scheduleName}" )
	@ExceptionHandler
	public String delSchedule(HttpServletRequest request, @PathVariable String jobId, @PathVariable String scheduleName, ModelMap modelMap) throws Exception {
		logger.info("Entry at delSchedule request ");
		
		try {
			jobScheduleService.deleteJobSchedule(scheduleName, jobId);
			return "success";
		} catch (Exception ex) {
			logMessage(ex);
			return "fail";
		} 
	}
	
	@RequestMapping(value="/editSchedule/{jobId}/{scheduleName}" )
	@ExceptionHandler
	public ModelAndView editSchedule(@ModelAttribute("eppBatchJobForm") JobDto batchJobForm, HttpServletRequest request, @PathVariable String jobId, @PathVariable String scheduleName, ModelMap modelMap) throws Exception {
		logger.info("Entry at editSchedule request ");
		
		long startTimeMs = System.currentTimeMillis();
		Throwable throwable = null;
		
		JobScheduleDto eppJobScheduleForm = new JobScheduleDto();
		try {
			// Get job infor
			Job eppJob = jobScheduleService.getJob(jobId);
			// Get job schedule info
			JobSchedule jobSchedule = jobScheduleService.getJobScheduleByPrimaryKey(jobId, scheduleName);
			eppJobScheduleForm = parseScheduleForm(jobSchedule);
			eppJobScheduleForm.setJobId(jobId);
			eppJobScheduleForm.setJobName(eppJob.getJobName());
			eppJobScheduleForm.setJobClass(eppJob.getJobClass());
			eppJobScheduleForm.setMode(MODE_AMEND);
			eppJobScheduleForm.setPrevMode(batchJobForm.getMode());
		} catch (Exception ex) {
			logMessage(ex);
			throwable = ex;
		} finally {
			try {
				String functionName = "Cập nhật lịch trình";
				Object[] args = { eppJobScheduleForm };
				long timeMs = System.currentTimeMillis() - startTimeMs;
				auditAccessLogService.saveAuditAccessLogForWeb(request, functionName,args,throwable, timeMs);
			} catch(Exception exp){
				logMessage(exp);
			}
		}
		
		modelMap.addAttribute("fnSelected", Constants.HEADING_NIC_BATCHJOB_SCHE);
		return new ModelAndView ("display.scheSave","eppJobScheduleForm",eppJobScheduleForm);
	}
	
	@RequestMapping(value="/saveSchedule" )
	@ExceptionHandler
	public ModelAndView saveSchedule(@ModelAttribute("eppJobScheduleForm") JobScheduleDto eppJobScheduleForm, HttpServletRequest request, ModelMap modelMap) throws Exception {
		logger.info("Entry at saveSchedule request ");

		long startTimeMs = System.currentTimeMillis();
		Throwable throwable = null;

		try {
			Job job = jobScheduleService.getJob(eppJobScheduleForm.getJobId());

			JobSchedule schedule = new JobSchedule();

			Date startDate = DateUtil.strToDate(eppJobScheduleForm.getDurationStartDate(), DateUtil.FORMAT_MM_DD_YYYY);
			Date endDate = DateUtil.strToDate(eppJobScheduleForm.getDurationEndDate(), DateUtil.FORMAT_MM_DD_YYYY);

			schedule.setStartDate(startDate);
			schedule.setEndDate(endDate);
			JobScheduleId jobScheduleIdObj = new JobScheduleId(eppJobScheduleForm.getJobId(), eppJobScheduleForm.getScheduleName().toUpperCase().trim());
			schedule.setId(jobScheduleIdObj);

			// add action type for success
			if (ActionOnEvent.None.getKey().equals(eppJobScheduleForm.getActionOnSuccessType())) {
				schedule.setActionOnSuccess(ActionOnEvent.None);
			} else if (ActionOnEvent.NotifyEmail.getKey().equals(eppJobScheduleForm.getActionOnSuccessType())) {
				schedule.setActionOnSuccess(ActionOnEvent.NotifyEmail);
			} else if (ActionOnEvent.Rerun.getKey().equals(eppJobScheduleForm.getActionOnSuccessType())) {
				schedule.setActionOnSuccess(ActionOnEvent.Rerun);
			} else if (ActionOnEvent.RerunNotifyEmail.getKey().equals(eppJobScheduleForm.getActionOnSuccessType())) {
				schedule.setActionOnSuccess(ActionOnEvent.RerunNotifyEmail);
			}

			// add action type for failure
			if (ActionOnEvent.None.getKey().equals(eppJobScheduleForm.getActionOnFailureType())) {
				schedule.setActionOnFailure(ActionOnEvent.None);
			} else if (ActionOnEvent.NotifyEmail.getKey().equals(eppJobScheduleForm.getActionOnFailureType())) {
				schedule.setActionOnFailure(ActionOnEvent.NotifyEmail);
			} else if (ActionOnEvent.Rerun.getKey().equals(eppJobScheduleForm.getActionOnFailureType())) {
				schedule.setActionOnFailure(ActionOnEvent.Rerun);
			} else if (ActionOnEvent.RerunNotifyEmail.getKey().equals(eppJobScheduleForm.getActionOnFailureType())) {
				schedule.setActionOnFailure(ActionOnEvent.RerunNotifyEmail);
			}

			schedule.setEmailAddress(eppJobScheduleForm.getEmailAddress());

			if (Frequency.Daily.getKey().equals(eppJobScheduleForm.getFreqOccurType())
					|| Frequency.Weekly.getKey().equals(eppJobScheduleForm.getFreqOccurType())
					|| Frequency.Monthly.getKey().equals(eppJobScheduleForm.getFreqOccurType())
					|| Frequency.Yearly.getKey().equals(eppJobScheduleForm.getFreqOccurType())
					|| Frequency.Specific.getKey().equals(eppJobScheduleForm.getFreqOccurType())) {

				if ("PM".equals(eppJobScheduleForm.getFreqDailyTriggerTimeFormat())
						&& Integer.parseInt(eppJobScheduleForm.getFreqDailyTriggerTimeHr()) != 12) {
					schedule.setStartHour(Integer.parseInt(eppJobScheduleForm.getFreqDailyTriggerTimeHr()) + 12);
				} else if ("AM".equals(eppJobScheduleForm.getFreqDailyTriggerTimeFormat())
						&& Integer.parseInt(eppJobScheduleForm.getFreqDailyTriggerTimeHr()) == 12) {
					schedule.setStartHour(Integer.parseInt(eppJobScheduleForm.getFreqDailyTriggerTimeHr()) - 12);
				} else {
					schedule.setStartHour(Integer.parseInt(eppJobScheduleForm.getFreqDailyTriggerTimeHr()));
				}
				schedule.setStartMin(Integer.parseInt(eppJobScheduleForm.getFreqDailyTriggerTimeMin()));
			}

			if (Frequency.Daily.getKey().equals(eppJobScheduleForm.getFreqOccurType())) {
				schedule.setFrequency(Frequency.Daily);
			} else if (Frequency.Weekly.getKey().equals(eppJobScheduleForm.getFreqOccurType())) {
				schedule.setFrequency(Frequency.Weekly);
				schedule.setDayofWeek(eppJobScheduleForm.getFreqWeeklyDayOfWeek());

				String dayOfWeek = "";
				for (int i = 0; i < eppJobScheduleForm.getFreqWeeklyDayOfWeek().length; i++) {
					if (StringUtils.isEmpty(dayOfWeek)) {
						dayOfWeek = dayOfWeek.concat(eppJobScheduleForm.getFreqWeeklyDayOfWeek()[i]);
					} else {
						dayOfWeek = dayOfWeek.concat(',' + eppJobScheduleForm.getFreqWeeklyDayOfWeek()[i]);
					}
				}

				schedule.setWeekDay(dayOfWeek);
			} else if (Frequency.Monthly.getKey().equals(eppJobScheduleForm.getFreqOccurType())) {
				schedule.setFrequency(Frequency.Monthly);
				schedule.setDayOfMonth(eppJobScheduleForm.getFreqMonthlyDayOfMonth());
			} else if (Frequency.Yearly.getKey().equals(eppJobScheduleForm.getFreqOccurType())) {
				schedule.setFrequency(Frequency.Yearly);
				schedule.setYearlyMonth(eppJobScheduleForm.getFreqYearlyMonth());
				schedule.setYearlyDayOfMonth(eppJobScheduleForm.getFreqYearlyDayOfMonth());
			} else if (Frequency.RepeatAtInterval.getKey().equals(eppJobScheduleForm.getFreqOccurType())) {
				schedule.setFrequency(Frequency.RepeatAtInterval);
				schedule.setRepeatCount(Integer.parseInt(eppJobScheduleForm.getFreqRepeatIntervalCount()));
				schedule.setRepeatIntervalSecond(Integer.parseInt(eppJobScheduleForm.getFreqRepeatIntervalSecond()));

				// set action to None for both Success and Failure
				schedule.setActionOnSuccess(ActionOnEvent.None);
				schedule.setActionOnFailure(ActionOnEvent.None);

			} else if (Frequency.RepeatInDefinitely.getKey().equals(eppJobScheduleForm.getFreqOccurType())) {
				schedule.setFrequency(Frequency.RepeatInDefinitely);
				schedule.setRepeatIntervalSecond(Integer.parseInt(eppJobScheduleForm.getFreqRepeatIntervalSecond()));

				// set action to None for both Success and Failure
				schedule.setActionOnSuccess(ActionOnEvent.None);
				schedule.setActionOnFailure(ActionOnEvent.None);
			} else if (Frequency.Specific.getKey().equals(eppJobScheduleForm.getFreqOccurType())) {
				schedule.setFrequency(Frequency.Specific);
			}

			// add enabled disabled schedule
			schedule.setActive(eppJobScheduleForm.getScheduleEnabled());

			// create schedule description
			String scheduleDescription = createScheduleDescription(schedule);
			schedule.setScheduleDescription(scheduleDescription);

			if (MODE_EDIT.equalsIgnoreCase(eppJobScheduleForm.getMode())
					|| MODE_AMEND.equalsIgnoreCase(eppJobScheduleForm.getMode())) {
				jobScheduleService.scheduleJob(job, schedule, MODE_AMEND);
			} else {
				jobScheduleService.scheduleJob(job, schedule, MODE_ADD);
			}
			// if enabled checkbox is not checked, disable it
			if (!eppJobScheduleForm.getScheduleEnabled()) {
				jobScheduleService.disableTrigger(eppJobScheduleForm.getScheduleName(), eppJobScheduleForm.getJobId());
			} else {
				jobScheduleService.enableTrigger(eppJobScheduleForm.getScheduleName(), eppJobScheduleForm.getJobId());
			}

		} catch (Exception ex) {
			logMessage(ex);
			throwable = ex;
		} finally {
			try {
				String functionName = "Cập nhật lịch trình";//"saveSchedule";
				Object[] args = { eppJobScheduleForm };
				long timeMs = System.currentTimeMillis() - startTimeMs;
				auditAccessLogService.saveAuditAccessLogForWeb(request, functionName, args, throwable, timeMs);
			} catch (Exception exp) {
				logMessage(exp);
			}
		}

		JobDto eppBatchJobForm = new JobDto();
		eppBatchJobForm.setMode(eppJobScheduleForm.getPrevMode());
		
		return viewJob(eppBatchJobForm, request, eppJobScheduleForm.getJobId(), modelMap);
	}
	
	/**
	 * Create schedule description (private method)
	 * 
	 * @param schedule JobSchedule
	 * @return String
	 */
	private String createScheduleDescription(JobSchedule schedule) {
		// variables to form the description sentence - Start
		String desc = "";
		String yearlyDay = "";
		String yearlyYear = "";
		String hour = String.valueOf(schedule.getStartHour());
		if (hour.length() == 1) {
			hour = '0' + hour;
		}

		String min = String.valueOf(schedule.getStartMin());
		if (min.length() == 1) {
			min = '0' + min;
		}
		if (schedule.getStartDate() != null) {
			yearlyDay = DateUtil.parseDate(schedule.getStartDate(), DateUtil.FORMAT_MM_DD_YYYY).substring(6, 8);
		}
		String yearlyMonth = "";
		if (schedule.getStartDate() != null) {
			yearlyYear = DateUtil.parseDate(schedule.getStartDate(), DateUtil.FORMAT_MM_DD_YYYY).substring(0, 4);
		}
		if (schedule.getStartDate() != null) {
			if ("01".equals(DateUtil.parseDate(schedule.getStartDate(), DateUtil.FORMAT_MM_DD_YYYY).substring(4, 6))) {
				yearlyMonth = "January";
			} else if ("02"
					.equals(DateUtil.parseDate(schedule.getStartDate(), DateUtil.FORMAT_MM_DD_YYYY).substring(4, 6))) {
				yearlyMonth = "February";
			} else if ("03"
					.equals(DateUtil.parseDate(schedule.getStartDate(), DateUtil.FORMAT_MM_DD_YYYY).substring(4, 6))) {
				yearlyMonth = "March";
			} else if ("04"
					.equals(DateUtil.parseDate(schedule.getStartDate(), DateUtil.FORMAT_MM_DD_YYYY).substring(4, 6))) {
				yearlyMonth = "April";
			} else if ("05"
					.equals(DateUtil.parseDate(schedule.getStartDate(), DateUtil.FORMAT_MM_DD_YYYY).substring(4, 6))) {
				yearlyMonth = "May";
			} else if ("06"
					.equals(DateUtil.parseDate(schedule.getStartDate(), DateUtil.FORMAT_MM_DD_YYYY).substring(4, 6))) {
				yearlyMonth = "June";
			} else if ("07"
					.equals(DateUtil.parseDate(schedule.getStartDate(), DateUtil.FORMAT_MM_DD_YYYY).substring(4, 6))) {
				yearlyMonth = "July";
			} else if ("08"
					.equals(DateUtil.parseDate(schedule.getStartDate(), DateUtil.FORMAT_MM_DD_YYYY).substring(4, 6))) {
				yearlyMonth = "August";
			} else if ("09"
					.equals(DateUtil.parseDate(schedule.getStartDate(), DateUtil.FORMAT_MM_DD_YYYY).substring(4, 6))) {
				yearlyMonth = "September";
			} else if ("10"
					.equals(DateUtil.parseDate(schedule.getStartDate(), DateUtil.FORMAT_MM_DD_YYYY).substring(4, 6))) {
				yearlyMonth = "October";
			} else if ("11"
					.equals(DateUtil.parseDate(schedule.getStartDate(), DateUtil.FORMAT_MM_DD_YYYY).substring(4, 6))) {
				yearlyMonth = "November";
			} else if ("12"
					.equals(DateUtil.parseDate(schedule.getStartDate(), DateUtil.FORMAT_MM_DD_YYYY).substring(4, 6))) {
				yearlyMonth = "December";
			}
		}		
		
		if (schedule.getFrequency().equals(Frequency.Daily)) {

			desc = desc.concat("Daily on ");
			desc = desc.concat(hour + ':' + min);

			if (schedule.getStartDate() != null) {

				String startDateString = DateUtil.parseDate(schedule.getStartDate(), DateUtil.FORMAT_MM_DD_YYYY);
				desc = desc.concat(", starting from " + startDateString + ' ');

				if (schedule.getEndDate() != null) {
					String endDateString = DateUtil.parseDate(schedule.getEndDate(), DateUtil.FORMAT_MM_DD_YYYY);
					desc = desc.concat("to " + endDateString);
				}
			}
		} else if (schedule.getFrequency().equals(Frequency.Weekly)) {

			desc = desc.concat("Weekly on ");
			desc = desc.concat(String.valueOf(hour + ':' + min) + ", every ");
			
			if (schedule.getStartDate() != null) {

				String startDateString = DateUtil.parseDate(schedule.getStartDate(), DateUtil.FORMAT_MM_DD_YYYY);
				desc = desc.concat(", starting from " + startDateString + ' ');

				if (schedule.getEndDate() != null) {
					String endDateString = DateUtil.parseDate(schedule.getEndDate(), DateUtil.FORMAT_MM_DD_YYYY);
					desc = desc.concat("to " + endDateString + ' ');
				}
			}
		} else if (schedule.getFrequency().equals(Frequency.Monthly)) {

			desc = desc.concat("Monthly on ");
			desc = desc.concat(String.valueOf(hour + ':' + min) + ", every ");

			if ("LAST".equalsIgnoreCase(schedule.getDayOfMonth())) {
				desc = desc.concat("last day");
			} else {
				desc = desc.concat(schedule.getDayOfMonth());
			}

			desc = desc.concat(" of the month");

			if (schedule.getStartDate() != null) {
				String startDateString = DateUtil.parseDate(schedule.getStartDate(), DateUtil.FORMAT_MM_DD_YYYY);
				desc = desc.concat(", starting from " + startDateString + ' ');

				if (schedule.getEndDate() != null) {
					String endDateString = DateUtil.parseDate(schedule.getEndDate(), DateUtil.FORMAT_MM_DD_YYYY);
					desc = desc.concat("to " + endDateString);
				}
			}
		} else if (schedule.getFrequency().equals(Frequency.Yearly)) {
			desc = desc.concat("Yearly on ");
			desc = desc.concat(String.valueOf(hour + ':' + min) + ", every ");
			desc = desc.concat(yearlyDay);
			desc = desc.concat(" of ");
			desc = desc.concat(yearlyMonth);

			if (schedule.getStartDate() != null) {
				String startDateString = DateUtil.parseDate(schedule.getStartDate(), DateUtil.FORMAT_MM_DD_YYYY);
				desc = desc.concat(", starting from " + startDateString + ' ');

				if (schedule.getEndDate() != null) {
					String endDateString = DateUtil.parseDate(schedule.getEndDate(), DateUtil.FORMAT_MM_DD_YYYY);
					desc = desc.concat("to " + endDateString);
				}
			}
		} else if (schedule.getFrequency().equals(Frequency.Specific)) {
			desc = desc.concat("Specifically on ");
			desc = desc.concat(String.valueOf(hour + ':' + min) + ", ");
			desc = desc.concat(yearlyDay);
			desc = desc.concat(" of ");
			desc = desc.concat(yearlyMonth + ' ');
			desc = desc.concat(yearlyYear);
		} else if (schedule.getFrequency().equals(Frequency.RepeatAtInterval)) {
			desc = desc.concat("Repeat on a specific count of ");
			desc = desc.concat(String.valueOf(schedule.getRepeatCount()));
			desc = desc.concat(" counts at ");
			desc = desc.concat(String.valueOf(schedule.getRepeatIntervalSecond()));
			desc = desc.concat(" second interval");
		} else if (schedule.getFrequency().equals(Frequency.RepeatInDefinitely)) {
			desc = desc.concat("Repeat indefinitely at ");
			desc = desc.concat(String.valueOf(schedule.getRepeatIntervalSecond()));
			desc = desc.concat(" second interval");
		}
		// variables to form the description sentence - End
		
		return desc.toUpperCase().trim();
	}
	
	private JobScheduleDto parseScheduleForm(JobSchedule jobSchedule) throws Exception {
		JobScheduleDto eppJobScheduleForm = new JobScheduleDto();
		eppJobScheduleForm.init();
		eppJobScheduleForm.setScheduleName(jobSchedule.getId().getScheduleName());
		eppJobScheduleForm.setScheduleEnabled(jobSchedule.isActive());
		eppJobScheduleForm.setFreqOccurType(jobSchedule.getFrequency().getKey());

		eppJobScheduleForm.setActionOnSuccessType(jobSchedule.getActionOnSuccess().getKey());
		eppJobScheduleForm.setActionOnFailureType(jobSchedule.getActionOnFailure().getKey());
		eppJobScheduleForm.setEmailAddress(jobSchedule.getEmailAddress());
		if (jobSchedule.getStartHour() > 12) {
			eppJobScheduleForm.setFreqDailyTriggerTimeHr((jobSchedule.getStartHour() - 12) + "");
			eppJobScheduleForm.setFreqDailyTriggerTimeFormat("PM");
		} else {
			eppJobScheduleForm.setFreqDailyTriggerTimeHr(jobSchedule.getStartHour() + "");
			eppJobScheduleForm.setFreqDailyTriggerTimeFormat("AM");
		}
		eppJobScheduleForm.setFreqDailyTriggerTimeMin(jobSchedule.getStartMin() + "");

		if (StringUtils.isNotEmpty(jobSchedule.getWeekDay())) {
			String[] weekDays = jobSchedule.getWeekDay().split(",");
			eppJobScheduleForm.setFreqWeeklyDayOfWeek(weekDays);
		}
		eppJobScheduleForm.setFreqMonthlyDayOfMonth(jobSchedule.getDayOfMonth());
		eppJobScheduleForm.setFreqYearlyMonth(jobSchedule.getYearlyMonth());
		eppJobScheduleForm.setFreqYearlyDayOfMonth(jobSchedule.getYearlyDayOfMonth());
		eppJobScheduleForm.setFreqRepeatIntervalCount(jobSchedule.getRepeatCount() + "");
		eppJobScheduleForm.setFreqRepeatIntervalSecond(jobSchedule.getRepeatIntervalSecond() + "");
		eppJobScheduleForm.setDurationStartDate(DateUtil.parseDate(jobSchedule.getStartDate(), DateUtil.FORMAT_MM_DD_YYYY));
		eppJobScheduleForm.setDurationEndDate(DateUtil.parseDate(jobSchedule.getEndDate(), DateUtil.FORMAT_MM_DD_YYYY));
		if (null != jobSchedule.getEndDate()
				|| (null != jobSchedule.getStartDate() && null != jobSchedule.getEndDate())) {
			eppJobScheduleForm.setDurationEndDateEnabled("Yes");
		} else {
			eppJobScheduleForm.setDurationEndDateEnabled("No");
		}
		return eppJobScheduleForm;
	}
	
	private void logMessage(Exception ex) {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		PrintStream ps = new PrintStream(baos);
		ex.printStackTrace(ps);
		logger.error(ex.getMessage() + " : \n" + new String(baos.toByteArray()));
	}
	
	private PageRequest generateReqPage(HttpServletRequest request) {
		int page = !StringUtils.isEmpty(request.getParameter("pageNo")) ? Integer.parseInt(request.getParameter("pageNo")) : 1;
        String sortname = request.getParameter("sortname");
        String sortorder = request.getParameter("sortorder") != null ? request.getParameter("sortorder") : "desc";
        String rp = request.getParameter("rp");

        int pageSize = Constants.PAGE_SIZE_DEFAULT;
        if ( StringUtils.isNotEmpty(rp) && !"NaN".equals(rp)) {
            pageSize = Integer.parseInt(rp);
        }

        int startIndex = 0;
//        String pageNumber = pages;
//        if (StringUtils.isNotBlank(pageNumber)) {
//            startIndex = (Integer.parseInt(pageNumber) - 1) * pageSize;
//        }
        //Phúc edit
        //pageSize = Constants.DEFAULT_PAGE_MAX_SIZE;
        //end
        PageRequest pageRequest = new PageRequest();
        pageRequest.setCalculateRecordCount(true);
        pageRequest.setFirstRowIndex(startIndex);
        pageRequest.setMaxRecords(pageSize);
        pageRequest.setSortname(sortname);
        pageRequest.setSortorder(sortorder);
        pageRequest.setPageNo(page);
		
		return pageRequest;
	}


	@RequestMapping(value = "/importTransactionsGet",method=RequestMethod.GET)
	@ResponseBody
	@ExceptionHandler
	public ModelAndView signerGovsCreate(WebRequest request,HttpServletRequest httpRequest, ModelMap model)
			throws Exception {
		
		return new ModelAndView("syns.importTransactions");
	}

	@RequestMapping(value = "/importTransactions",method=RequestMethod.POST)
	@ResponseBody
	@ExceptionHandler
	public String signerGovsCreate(WebRequest request,
			HttpServletRequest httpRequest, ModelMap model,
			
			@RequestParam(value = "xml") String xml)
			throws Exception {
		try {
			
			/*MainTransaction main=new MainTransaction();
			main.setApplnRefNo("ApplnRefN");
			main.setChecksum("CHECKSUM");
			main.setIssSiteCode("SITECODE001");
			RegistrationData regisData=new RegistrationData();
			regisData.setAddressCompany("CONG TY");
			regisData.setAddressNin("09089709");
			main.setRegistrationData(regisData);
			
			String xml1= MiscXmlConvertor.parseObjectToXml(main);
			
			*/
			
			Object obj = MiscXmlConvertor.parseXmlToObject(xml);
			MainTransaction transaction = (MainTransaction)obj;
			
			//NicTransactionServiceImpl nicTransactionService = new NicTransactionServiceImpl();
			
			nicTransactionService.saveMainTransaction(transaction);
			
		} catch (Exception exp) {
			exp.printStackTrace();
			
			return "0";
			

			// return mav;
		} finally {
			// 30 Dec 2013 (chris lai) : update with audit record
			try {
				
			
			} catch (Exception exp) {
			}
		}

		return "1";
	}
}
