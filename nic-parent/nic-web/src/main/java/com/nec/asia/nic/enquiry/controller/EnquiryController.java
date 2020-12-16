/**
 * 
 */
package com.nec.asia.nic.enquiry.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.displaytag.tags.TableTagParameters;
import org.displaytag.util.ParamEncoder;
import org.hibernate.criterion.Order;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.ModelAndView;

import com.nec.asia.nic.common.DmsUtility;
import com.nec.asia.nic.common.dto.RicRegistrationDocumentDTO;
import com.nec.asia.nic.comp.job.dto.CpdReferenceDataDTO;
import com.nec.asia.nic.comp.job.dto.HitCandidateDTO;
import com.nec.asia.nic.comp.trans.domain.BaseModelList;
import com.nec.asia.nic.comp.trans.domain.NicDocumentData;
import com.nec.asia.nic.comp.trans.domain.NicJobCpdCheckResult;
import com.nec.asia.nic.comp.trans.domain.NicRegistrationData;
import com.nec.asia.nic.comp.trans.domain.NicTransaction;
import com.nec.asia.nic.comp.trans.domain.NicTransactionAttachment;
import com.nec.asia.nic.comp.trans.domain.NicTransactionLog;
import com.nec.asia.nic.comp.trans.domain.NicUploadJob;
import com.nec.asia.nic.comp.trans.dto.NicEnquiryForm;
import com.nec.asia.nic.comp.trans.dto.NicRegistrationDataDto;
import com.nec.asia.nic.comp.trans.dto.NicUploadJobDto;
import com.nec.asia.nic.comp.trans.service.DocumentDataService;
import com.nec.asia.nic.comp.trans.service.NicTransactionAttachmentService;
import com.nec.asia.nic.comp.trans.service.NicTransactionService;
import com.nec.asia.nic.comp.trans.service.NicUploadJobService;
import com.nec.asia.nic.comp.trans.service.TransactionLogService;
import com.nec.asia.nic.comp.trans.service.exception.InquirySearchServiceException;
import com.nec.asia.nic.comp.trans.service.exception.NicUploadJobServiceException;
import com.nec.asia.nic.dx.dto.DataExchangeDTO;
import com.nec.asia.nic.dx.dto.FpVerificationDTO;
import com.nec.asia.nic.framework.PageRequest;
import com.nec.asia.nic.framework.PaginatedResult;
import com.nec.asia.nic.framework.admin.code.domain.CodeValues;
import com.nec.asia.nic.framework.admin.code.domain.Parameters;
import com.nec.asia.nic.framework.admin.code.domain.ParametersId;
import com.nec.asia.nic.framework.admin.code.service.CodesService;
import com.nec.asia.nic.framework.admin.code.service.ParametersService;
import com.nec.asia.nic.framework.admin.site.domain.SiteRepository;
import com.nec.asia.nic.framework.admin.site.service.SiteService;
import com.nec.asia.nic.framework.common.ImageUtil;
import com.nec.asia.nic.framework.common.LabelValueBean;
import com.nec.asia.nic.framework.controller.AbstractController;
import com.nec.asia.nic.framework.dao.DaoException;
import com.nec.asia.nic.investigation.AttachmentSource;
import com.nec.asia.nic.investigation.exception.InvalidParameterException;
import com.nec.asia.nic.util.CitizenRefXmlConvertor;
import com.nec.asia.nic.util.CodeValueDescComparator;
import com.nec.asia.nic.util.Constants;
import com.nec.asia.nic.util.LogDataXmlConvertor;
import com.nec.asia.nic.util.SiteRepositoryComparator;
import com.nec.asia.nic.util.pagingUtil;
import com.nec.asia.nic.utils.BaseDTOMapper;
import com.nec.asia.nic.utils.DateUtil;
import com.nec.asia.nic.utils.HelperClass;
import com.nec.asia.nic.web.session.UserSession;

/**
 * @author sailaja_chinapothula
 * @Company: NEC Asia Pacific Ltd
 * @Since: Jun 18, 2013
 */

/*
 * Modification History:
 * 20 Aug 2014 (chris): change "TRANSACTION_TYPE" to "JOB_TYPE" for CODE_ID.
 * 20 Aug 2014 (chris): added JobUploadTime in nicUploadJobInfo
 * 11 Jul 2017 (chris): to show fingerprint as signature based on transaction data.
 */

@Controller
@RequestMapping(value = "/nicEnquiry")
public class EnquiryController extends AbstractController {

    private static final Logger logger = LoggerFactory.getLogger(EnquiryController.class);

    public static CitizenRefXmlConvertor util = new CitizenRefXmlConvertor();

    @Autowired
    private NicUploadJobService uploadJobService;

    @Autowired
    private CodesService codesService;

    @Autowired
    private TransactionLogService transactionLogService;

    @Autowired
    private NicTransactionAttachmentService nicTransactionDocumentService;

    @Autowired
    NicTransactionService nicTransactionService;

    @Autowired
    private ParametersService parametersService;
    
    @Autowired
    private DocumentDataService documentDataService;

	@Autowired
	private SiteService siteService;
	
    public static LogDataXmlConvertor issuanceUtil = new LogDataXmlConvertor();

    private static String ASCENDING_ORDER="1";
	private static String DESCENDING_ORDER="2";

	private static final String PARA_SCOPE_SYSTEM = "SYSTEM";
	private static final String PARA_NAME_SYSTEM_DMS_URL = "DMS_URL";

	private static final String PARA_TRANSACTION_ATTACHMENT_SOURCE_SCOPE = "SYSTEM";
	private static final String PARA_TRANSACTION_ATTACHMENT_SOURCE_VALUE = "TRANSACTION_ATTACHMENT_SOURCE";
	
    @RequestMapping(value = "/jobQueue")
    @ExceptionHandler
    public ModelAndView nicEnquiryJobList(WebRequest request, HttpServletRequest httpServletRequest, ModelMap model) throws Exception {
        logger.info("NIC Enquiry Page");
        try {
            //int pageNo = 1;
            //int pageSize = Constants.PAGE_SIZE_DEFAULT;//20;
            // startIndex = 0;
            String viewFPFalg = "Y";
            UserSession userSession = (UserSession) httpServletRequest.getSession().getAttribute("userSession");
            // long jobId = 12;
            //ModelAndView mav = new ModelAndView("nic-enquiry-jobqueue");
            NicEnquiryForm nicEnqForm = new NicEnquiryForm();

//            nicEnqForm.setSiteCodeCodeList(getCodeValueList("SITE_CODE"));
            List<SiteRepository> siteList = getSiteList();
            nicEnqForm.setRegSiteList(siteList);
            nicEnqForm.setIssSiteList(siteList);
            nicEnqForm.setTxnStatusCodeList(getCodeValueList("TRANSACTION_STATUS"));
            nicEnqForm.setJobTypeCodeList(getCodeValueList("JOB_TYPE"));
            nicEnqForm.setRecordStatusCodeList(getCodeValueList("INVESTIGATION_STATUS"));
            nicEnqForm.setJobStateCodeList(getCodeValueList("JOB_STAGE"));
            nicEnqForm.setInvestigationTypeList(this.getInvestigationTypeList());
            if (!userSession.getFunctions().contains("VIEW_FP")) {
                viewFPFalg = "N";
            }

            //			SortedMap<String, String> jobTypeList = getCodeValues("JOB_TYPE");
            //			nicEnqForm.setJobTypeList(jobTypeList);
            //			SortedMap<String, String> jobStateList = getCodeValues("JOB_STAGE");
            //			nicEnqForm.setJobStateList(jobStateList);
            //			SortedMap<String, String> recordStatusList = getCodeValues("INVESTIGATION_STATUS");
            //			nicEnqForm.setRecordStatusList(recordStatusList);
            //			SortedMap<String, String> siteCodeList = getCodeValues("SITE_CODE");
            //			nicEnqForm.setSiteCodeList(siteCodeList);
            //			SortedMap<String, String> transactionTypeList = getCodeValues("TRANSACTION_TYPE");
            //			nicEnqForm.setTransactionTypeList(transactionTypeList);
            model.addAttribute("fnSelected", Constants.HEADING_NIC_JOB_ENQUIRY);
            //			logger.info("The Code Value job Type List is-------->>>>>>>"
            //					+ jobTypeList.size());
            //			logger.info("The Code Value job current state is ===============>>>>>>>"
            //					+ jobStateList.size());
            //			logger.info("The Code Value record status is =================>>>>>>>>>"
            //					+ recordStatusList.size());
            // logger.info("The Code Value List is-------->>>>>>>"+codeList.get(0).getCodeValueDesc());
            ModelAndView mav = new ModelAndView("nic-enquiry-jobqueue");
            mav.addObject("nicEnqForm", nicEnqForm);
            mav.addObject("viewFPFalg", viewFPFalg);
            //model.addAttribute("jobList", new ArrayList<NicUploadJobInfo>());
            //phúc edit           
            model.addAttribute("pageSize", 10);
            model.addAttribute("pageNo", 1);
            model.addAttribute("totalPage", 1);
            model.addAttribute("startIndex", 0);
            model.addAttribute("totalRecord", 0);
            model.addAttribute("endIndex", 0);
            model.addAttribute("jobList", new ArrayList<NicUploadJobInfo>());
            //end
            return mav;

        } catch (Exception e) {

            e.printStackTrace();
            return null;
        }

    }

    private List<LabelValueBean> getInvestigationTypeList() throws NicUploadJobServiceException {
        List<LabelValueBean> list = new ArrayList<LabelValueBean>();

        //[15 Feb 2016][Tue] - Edit
        //LabelValueBean cpd = new LabelValueBean("CPD", "CPD");
        //LabelValueBean afis = new LabelValueBean("AFIS", "AFIS");

        //list.add(cpd);
        //list.add(afis);
        
        List<CodeValues> values = getCodeValueList("INVESTIGATION_TYPE");
        for(CodeValues value : values) {
        	list.add(new LabelValueBean(value.getCodeValueDesc(), value.getId().getCodeValue()));
        }

        return list;
    }

    private Map<String, String> getCodeValues(String codeId) throws NicUploadJobServiceException {
        return uploadJobService.getCodeValues(codeId);
    }

    private List<CodeValues> getCodeValueList(String codeId) throws NicUploadJobServiceException {
        List<CodeValues> codeValueList = codesService.findAllByCodeId(codeId);
        if (codeValueList != null) {
            Collections.sort(codeValueList, new CodeValueDescComparator());
        }

        return codeValueList;
    }

    private List<SiteRepository> getSiteList() throws InquirySearchServiceException {
        List<SiteRepository> siteList = null;
		try {
			siteList = siteService.findAllSite();
		} catch (DaoException e) {
			throw new InquirySearchServiceException(e);
		}
        if (siteList != null) {
            Collections.sort(siteList, new SiteRepositoryComparator());
        }

        return siteList;
    }
    
    @RequestMapping(value = "/search")
    @ExceptionHandler
    //public String index(WebRequest request,String jobType, String userId, String jobCurrentState, String status, long uploadJobId,String transactionId) {
    public ModelAndView nicEnquiryJobSearchList(
        WebRequest request, @RequestParam("owner") String owner, @RequestParam("transactionId") String txnId, @RequestParam("jobId") Long jobId, @RequestParam("nin") String nin,
        @RequestParam("jobType") String jobType, @RequestParam("jobState") String jobState, @RequestParam("recordStatus") String recordStatus, @RequestParam("txnStatus") String txnStatus, ModelMap model) throws Exception {
        logger.info("NIC Enquiry JobQueue Page");
        logger.info("The upload job Id========================>>>>>>>>>>> " + jobId);
        logger.info("The owner is ============================>>>>>>>>>>" + owner);
        logger.info("The nin is========================>>>>>>>>>>> " + nin);
        logger.info("The transaction Id========================>>>>>>>>>>> " + txnId);
        logger.info("The job Type is===========================>>>>>>>>>>> " + jobType);
        logger.info("The job current state is==================>>>>>>>>>>>>" + jobState);
        logger.info("The record status is======================>>>>>>>>>>>>" + recordStatus);
        logger.info("The transaction status is======================>>>>>>>>>>>>" + recordStatus);
        PaginatedResult<NicUploadJob> pr = null;
        try {
            NicEnquiryForm nicEnqForm = new NicEnquiryForm();
            nicEnqForm.setJobType(jobType);
            nicEnqForm.setJobState(jobState);
            nicEnqForm.setRecordStatus(recordStatus);
            nicEnqForm.setJobId(jobId);
            nicEnqForm.setNin(nin);
            nicEnqForm.setOwner(owner);
            nicEnqForm.setTransactionId(txnId);
            String[] investStatus = recordStatus.split(",");

            Map<String, String> jobTypeList = uploadJobService.getCodeValues("JOB_TYPE");
            nicEnqForm.setJobTypeList(jobTypeList);
            Map<String, String> jobStateList = uploadJobService.getCodeValues("JOB_STAGE");
            nicEnqForm.setJobStateList(jobStateList);
            Map<String, String> recordStatusList = uploadJobService.getCodeValues("INVESTIGATION_STATUS");
            nicEnqForm.setRecordStatusList(recordStatusList);

            //NicUploadJob nicUploadJob = null;
            int pageNo = 1;
            int pageSize = Constants.PAGE_SIZE_DEFAULT; //200000
            int startIndex = 0;
            // String nin = "K000000000004K";
            //long jobId = 12;
            //String txnId = "RICHQ-2013-001065";
            logger.info("The transaction Id is " + txnId);
            String pageNumber = request.getParameter((new ParamEncoder("row").encodeParameterName(TableTagParameters.PARAMETER_PAGE)));
            if (StringUtils.isNotBlank(pageNumber)) {
                startIndex = (Integer.parseInt(pageNumber) - 1) * pageSize;
            } else {
                startIndex = 10;
            }
            PageRequest pageRequest = new PageRequest();
            pageRequest.setCalculateRecordCount(true);
            pageRequest.setFirstRowIndex(startIndex);
            pageRequest.setMaxRecords(pageSize);

            pr = uploadJobService.getJobList(jobType, jobId, jobState, investStatus, txnId, nin, owner, pageNo, pageSize);
            List<NicUploadJobInfo> jobList = new ArrayList<NicUploadJobInfo>();
            if (pr != null) {
                //		    	jobList = pr.getRows();
                for (NicUploadJob nicUploadJob : pr.getRows()) {
                    NicUploadJobInfo nicUploadJobInfo = new NicUploadJobInfo();

                    PropertyUtils.copyProperties(nicUploadJobInfo, nicUploadJob);

                    String jobTypeDesc = null;
                    if (nicUploadJobInfo != null && StringUtils.isNotBlank(nicUploadJobInfo.getJobType())) {
                        //CodeValues codeValue = codesService.getCodeValueByIdName("TRANSACTION_TYPE", nicUploadJobInfo.getJobType());
                        CodeValues codeValue = codesService.getCodeValueByIdName("JOB_TYPE", nicUploadJobInfo.getJobType()); //2014 Aug 20

                        if (codeValue != null) {
                            jobTypeDesc = codeValue.getCodeValueDesc();
                        } else {
                            jobTypeDesc = nicUploadJobInfo.getJobType();
                        }
                    }

                    nicUploadJobInfo.setJobType(jobTypeDesc);

                    String status = null;
                    if (nicUploadJobInfo != null && StringUtils.isNotBlank(nicUploadJobInfo.getInvestigationStatus())) {
                        CodeValues codeValue = codesService.getCodeValueByIdName("INVESTIGATION_STATUS", nicUploadJobInfo.getInvestigationStatus());

                        if (codeValue != null) {
                            status = codeValue.getCodeValueDesc();
                        } else {
                            status = nicUploadJobInfo.getInvestigationStatus();
                        }
                    }

                    nicUploadJobInfo.setInvestigationStatus(status);

                    jobList.add(nicUploadJobInfo);
                }

                Map<String, Object> searchResultMap = new HashMap<String, Object>();
                searchResultMap.put("jobList", jobList);
                searchResultMap.put("totalRecords", pr.getTotal());
                searchResultMap.put("pageSize", pageSize);
                searchResultMap.put("isSearch", "Y");
                model.addAttribute("jobList", jobList);
                model.addAttribute("nicEnqForm", nicEnqForm);
                ModelAndView mav = new ModelAndView("nic-enquiry-jobqueue", searchResultMap);
                mav.addObject("nicEnqForm", nicEnqForm);
                model.addAttribute("fnSelected", Constants.HEADING_NIC_JOB_ENQUIRY);
                //return new ModelAndView("nic-enquiry-jobqueue",searchResultMap);
                return mav;
            } else {
                return new ModelAndView("nic-enquiry-jobqueue", null);
            }
        } catch (Exception e) {

            e.printStackTrace();
            return null;
        }
    }

    @RequestMapping(value = "/searchJobs", method = RequestMethod.POST)
    public ModelAndView nicSearchJobs(WebRequest request, ModelMap model, @ModelAttribute(value = "nicEnqForm") NicEnquiryForm nicEnquiryForm) throws Exception {

        try {

            String pages = !StringUtils.isEmpty(request.getParameter("pageNo")) ? request.getParameter("pageNo") : "1";
            String sortname = request.getParameter("sortname") != null ? request.getParameter("sortname") : "workflowJobId";
            String sortorder = request.getParameter("sortorder") != null ? request.getParameter("sortorder") : "asc";
            String rp = request.getParameter("rp");
            String recordPerPage = request.getParameter("rp");

            if (!StringUtils.isNotBlank(recordPerPage)) {
                recordPerPage = "50";
            }

            int pageSize = Constants.PAGE_SIZE_DEFAULT;
            if (rp == null || "NaN".equals(rp)) {
                //pageSize = Integer.parseInt(configurationService.getUserAdminConsoleJobQueueMaxRecordsPerPage(getUserSession(request).getUserId()).getSettings());
            } else {
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

            NicUploadJob nicUploadJob = new NicUploadJob();
            /*
             * if(nicEnquiryForm.getJobId() != null){
             * nicUploadJob.setUploadJobId(nicEnquiryForm.getJobId());
             * NicUploadJob job = uploadJobService.getJobEnqueryDetails(nicEnquiryForm.getJobId());
             * if(job != null) {
             * nicEnquiryForm.setTransactionId(job.getTransactionId());
             * //job.setNicRegistrationDatas(null);
             * //job.setNicJobAfisCheckResult(null);
             * //job.setNicJobCompletedFlag(null);
             * //job.setNicJobCpdCheckResult(null);
             * } else {
             * nicEnquiryForm.setTransactionId("null");
             * }
             * }
             */

            /*
             * if(nicEnquiryForm.getJobId() != null){
             * nicUploadJob.setUploadJobId(nicEnquiryForm.getJobId());
             * }
             */
            nicUploadJob.setTransactionId(nicEnquiryForm.getTransactionId());
            nicUploadJob.setJobType(nicEnquiryForm.getJobType());
            //nicUploadJob.setInvestigationStatus(nicEnquiryForm.getRecordStatus());
            nicUploadJob.setJobCurrentStage(nicEnquiryForm.getJobState());
            nicUploadJob.setInvestigationType(nicEnquiryForm.getInvestigationType());

            //[16 Feb 2016][Tue] - Add
            /*if (!StringUtils.isEmpty(nicEnquiryForm.getSiteCode())) {
                Set<NicRegistrationData> nicRegistrationDatas = new HashSet<NicRegistrationData>();
                NicRegistrationData nicRegData = new NicRegistrationData();
                NicTransaction nicTransaction = new NicTransaction();
                nicTransaction.setRegSiteCode(nicEnquiryForm.getSiteCode());
                //nicRegData.setTransactionId(nicTransaction.getTransactionId());
                //nicRegistrationDatas.add(nicRegData);
                //nicUploadJob.setNicRegistrationDatas(nicRegistrationDatas);
            }*/

            String[] investStatus = null;
            if (StringUtils.isNotBlank(nicEnquiryForm.getRecordStatus())) {
                investStatus = nicEnquiryForm.getRecordStatus().split(",");
            }
            NicTransaction nicTransaction = new NicTransaction();
            
            
            if (StringUtils.isNotBlank(nicEnquiryForm.getTransactionStatus())) {
            	nicTransaction.setTransactionStatus(nicEnquiryForm.getTransactionStatus());
            }
            
            if (StringUtils.isNotBlank(nicEnquiryForm.getIssSiteCode())) {
            	nicTransaction.setIssSiteCode(nicEnquiryForm.getIssSiteCode());
            }
            
            if (StringUtils.isNotBlank(nicEnquiryForm.getRegSiteCode())) {
            	nicTransaction.setRegSiteCode(nicEnquiryForm.getRegSiteCode());
            }

            Date dateFrom = DateUtil.strToDate(nicEnquiryForm.getDateFrom(), "dd-MMM-yyyy");
			Date dateTo = DateUtil.strToDate(nicEnquiryForm.getDateTo(), "dd-MMM-yyyy");
			
            PaginatedResult<NicUploadJob> pr = uploadJobService.findAllForPagination(pageRequest, nicUploadJob, nicTransaction,investStatus, nicEnquiryForm.getJobId(), nicEnquiryForm.isShowErrTrans(), dateFrom, dateTo);
            List<NicUploadJobInfo> jobList = new ArrayList<NicUploadJobInfo>();
            for (NicUploadJob job : pr.getRows()) {
                NicUploadJobInfo nicUploadJobInfo = new NicUploadJobInfo();

                PropertyUtils.copyProperties(nicUploadJobInfo, job);

                String jobTypeDesc = null;
                if (nicUploadJobInfo != null && StringUtils.isNotBlank(nicUploadJobInfo.getJobType())) {
                    //CodeValues codeValue = codesService.getCodeValueByIdName("TRANSACTION_TYPE", nicUploadJobInfo.getJobType());
                    CodeValues codeValue = codesService.getCodeValueByIdName("JOB_TYPE", nicUploadJobInfo.getJobType()); //2014 Aug 20

                    if (codeValue != null) {
                        jobTypeDesc = codeValue.getCodeValueDesc();
                    } else {
                        jobTypeDesc = nicUploadJobInfo.getJobType();
                    }
                }

                nicUploadJobInfo.setJobType(jobTypeDesc);

                String status = null;
                if (nicUploadJobInfo != null && StringUtils.isNotBlank(nicUploadJobInfo.getInvestigationStatus())) {
                    CodeValues codeValue = codesService.getCodeValueByIdName("INVESTIGATION_STATUS", nicUploadJobInfo.getInvestigationStatus());

                    if (codeValue != null) {
                        status = codeValue.getCodeValueDesc();
                    } else {
                        status = nicUploadJobInfo.getInvestigationStatus();
                    }
                }

                nicUploadJobInfo.setInvestigationStatus(status);

                //[16 Feb 2016][Tue] - Edit
                /*String jobCurrentState = null;
                if (nicUploadJobInfo != null && StringUtils.isNotBlank(nicUploadJobInfo.getJobCurrentState())) {
                    CodeValues codeValue = codesService.getCodeValueByIdName("JOB_STAGE", nicUploadJobInfo.getJobCurrentState());

                    if (codeValue != null) {
                        jobCurrentState = codeValue.getCodeValueDesc();
                    } else {
                        //jobCurrentState = nicUploadJobInfo.setJobCurrentState();
                    }
                }*/
                String jobCurrentState = null;
                if (nicUploadJobInfo != null) {
                    CodeValues codeValue = codesService.getCodeValueByIdName("JOB_STAGE", nicUploadJobInfo.getJobCurrentState());
                    if (codeValue == null) {
                    	codeValue = codesService.getCodeValueByIdName("JOB_STAGE", job.getJobCurrentStage());
                    }
                    
                    if( codeValue != null) {
                    	jobCurrentState = codeValue.getCodeValueDesc();
                    }
                }

                nicUploadJobInfo.setJobCurrentState(jobCurrentState);
                // End
                
				if (StringUtils.isNotBlank(job.getTransactionId())) {
					NicTransaction txn = nicTransactionService.findById(job.getTransactionId()); //uploadJobService.getNicTransaction(job.getTransactionId());
					if (txn != null) {
						String regSiteDesc = null;
						if (StringUtils.isNotBlank(txn.getRegSiteCode())) {
							SiteRepository regSite = siteService.getSiteRepoById(txn.getRegSiteCode());
							if (regSite != null) {
								regSiteDesc = regSite.getSiteName();
							} else {
								regSiteDesc = txn.getRegSiteCode();
							}
						}

						nicUploadJobInfo.setRegSiteDesc(regSiteDesc);

						String transactionStatus = null;
	                    if (StringUtils.isNotBlank(txn.getTransactionStatus())) {
	                        CodeValues codeValue = codesService.getCodeValueByIdName("TRANSACTION_STATUS", txn.getTransactionStatus());
	                        if (codeValue != null) {
	                            transactionStatus = codeValue.getCodeValueDesc();
	                        } else {
	                            transactionStatus = txn.getTransactionStatus();
	                        }
	                    }
	                    
	                    nicUploadJobInfo.setTransactionStatus(transactionStatus);
					}
				}


                nicUploadJobInfo.setJobTxnId(nicUploadJobInfo.getUploadJobId() + "," + nicUploadJobInfo.getTransactionId());

             // Get Investigation type desc
                String investigationType = null;
                if (nicUploadJobInfo != null && StringUtils.isNotBlank(nicUploadJobInfo.getInvestigationType())) {
                    CodeValues codeValue = codesService.getCodeValueByIdName("INVESTIGATION_TYPE", nicUploadJobInfo.getInvestigationType());

                    if (codeValue != null) {
                    	investigationType = codeValue.getCodeValueDesc();
                    } 
                }

                nicUploadJobInfo.setInvestigationType(investigationType);
                
                if (job.getCreateDatetime() != null) {
                	nicUploadJobInfo.setCreateDateTime(DateUtil.parseDate(job.getCreateDatetime(), DateUtil.FORMAT_DD_MM_YYYY_HH_MM_SS));
                }
                jobList.add(nicUploadJobInfo);
            }
            //phúc edit
            int firstResults = (pageRequest.getPageNo() - 1) < 0 ? 0 : (pageRequest.getPageNo() - 1) * pageSize;
            model.addAttribute("pageSize", pageSize);
            model.addAttribute("pageNo", pageRequest.getPageNo());
            model.addAttribute("totalPage", pagingUtil.totalPage(pr.getTotal(), pageSize));
            model.addAttribute("startIndex", pr.getTotal() != 0 ? firstResults + 1 : 0);
            model.addAttribute("totalRecord", pr.getTotal());
            model.addAttribute("endIndex", firstResults + pr.getRowSize());
            model.addAttribute("jobList", jobList);
            //end
        } catch (Exception e) {

            e.printStackTrace();
            return new ModelAndView("nic-enquiry-jobqueue", null);
        }
        ModelAndView mav = new ModelAndView("nic-enquiry-jobqueue");
        mav.addObject("nicEnqForm", nicEnquiryForm);
        return mav;
    }

    @ResponseBody
    @RequestMapping(value = "/jobDetails/{jobId}", method = RequestMethod.GET)
    @ExceptionHandler
    public NicUploadJobDto nicEnquiryJobDetailsList(WebRequest request, @PathVariable String jobId, ModelMap model) throws Exception {
        logger.info("Nic Enquiry Job Details List for dialog popup");

        NicEnquiryForm nicForm = new NicEnquiryForm();

        NicUploadJob nicUploadJob = uploadJobService.findById(Long.valueOf(jobId));
        NicUploadJobDto uploadDto = new NicUploadJobDto(nicUploadJob);

        model.addAttribute("fnSelected", Constants.HEADING_NIC_JOB_ENQUIRY);
        return uploadDto;

    }

    @ResponseBody
    @RequestMapping(value = "/jobEnqDgData/{jobId}", method = RequestMethod.GET)
    @ExceptionHandler
    public NicRegistrationDataDto getDGDForJobEnquery(WebRequest request, @PathVariable String jobId, ModelMap model) throws Exception {

        HitCandidateDTO candidateCPDData = new HitCandidateDTO();
        NicUploadJob nicUploadJob = uploadJobService.findById(Long.valueOf(jobId));
        String transactionId = nicUploadJob.getTransactionId();// this one is null

        NicRegistrationData nicRegistrationData = uploadJobService.getNicRegistrationData(transactionId);
        NicRegistrationDataDto ricAndCpdData = new NicRegistrationDataDto(nicRegistrationData);
        NicJobCpdCheckResult cpdData = uploadJobService.findCpdData(Long.valueOf(jobId));
        String dataFromCpd = "";
        if (cpdData != null) {
            dataFromCpd = cpdData.getCpdXml();
        }
        CpdReferenceDataDTO convertedCpdData = new CpdReferenceDataDTO();
        if (!dataFromCpd.equals("")) {
            convertedCpdData = (CpdReferenceDataDTO) util.unmarshal(dataFromCpd);
        }
        if (convertedCpdData != null) {
            BaseDTOMapper.copyProperties(candidateCPDData, convertedCpdData);
        }
        ricAndCpdData.setCpdFirstnameFull(candidateCPDData.getFirstName());
        ricAndCpdData.setCpdSurnameFull(candidateCPDData.getSurname());
        ricAndCpdData.setCpdSurnameBirthFull(candidateCPDData.getSurnameAtBirth());
        ricAndCpdData.setCpdGender(candidateCPDData.getSex());
        ricAndCpdData.setCpdMaritalStatus(candidateCPDData.getMaritalStatus());
        ricAndCpdData.setCapdAddressLine4(candidateCPDData.getTownVillage());
        ricAndCpdData.setCpdDateOfBirth(candidateCPDData.getBirthDate());
        ricAndCpdData.setCpdFatherName(candidateCPDData.getFatherFirstName());
        ricAndCpdData.setCpdFatherSurname(candidateCPDData.getFatherSurname());
        ricAndCpdData.setCpdMotherName(candidateCPDData.getMotherFirstName());
        ricAndCpdData.setCpdMotherSurname(candidateCPDData.getMotherSurname());
        ricAndCpdData.setCpdNin(candidateCPDData.getNin());
        model.addAttribute("fnSelected", Constants.HEADING_NIC_JOB_ENQUIRY);
        return ricAndCpdData;
    }

    @ResponseBody
    @RequestMapping(value = "/jobEnqHitList/{jobId}", method = RequestMethod.GET)
    @ExceptionHandler
    public ModelAndView jobEnqHitList(WebRequest request, @PathVariable String jobId, ModelMap model) throws Exception {
        List<HitCandidateDTO> hitCandidatesList = new ArrayList<HitCandidateDTO>();
        try {
            if (StringUtils.isNumeric(jobId)) {
                hitCandidatesList = uploadJobService.getAfisHitCandidateListByJobId(new Long(jobId));
            }

        } catch (Exception ex) {
            logger.info("Error occurred while getting the job enquiry hit list for Job:" + jobId + ", Reason: " + ex.getMessage());
        }

        return new ModelAndView("nic-enquiry-hitList", "hitCandidatesList", hitCandidatesList);
    }

    @ResponseBody
    @RequestMapping(value = "/jobEnqTrans/{txnId}", method = RequestMethod.GET)
    @ExceptionHandler
    public ModelAndView jobEnqTrans(WebRequest request, @PathVariable String txnId, ModelMap model) throws Exception {
		NicTransactionInfo nicTransactionInfo = null;
		try {
			NicTransaction nicTransaction = uploadJobService.getNicTransaction(txnId);
			if (nicTransaction != null) {
				nicTransactionInfo = getTransactionInfo(nicTransaction);
			}
		} catch (Exception ex) {
			logger.info("Error occurred while getting the transaction info for Transaction:" + txnId + ", Reason: " + ex.getMessage());
		}
        return new ModelAndView("nic-enquiry-transInfo", "nicTransaction", nicTransactionInfo);
    }

    @ResponseBody
    @RequestMapping(value = "/jobEnqHistoryEvents/{txnId}", method = RequestMethod.GET)
    @ExceptionHandler
    public ModelAndView jobEnqHistoryEvents(WebRequest request, @PathVariable String txnId, ModelMap model) throws Exception {
        List<NicTransactionLogInfo> nicTransactionLogInfos = new ArrayList<NicTransactionLogInfo>();
        try {
        	BaseModelList<NicTransactionLog> baseFindAllTranLog = uploadJobService.getNicTransactionLogs(txnId);
            List<NicTransactionLog> nicTransactionLogs = baseFindAllTranLog.getListModel();
            if (nicTransactionLogs != null) {
                for (NicTransactionLog log : nicTransactionLogs) {

                    NicTransactionLogInfo nicTransactionLogInfo = new NicTransactionLogInfo();

                    PropertyUtils.copyProperties(nicTransactionLogInfo, log);

                    String transactionStatus = null;
                    if (log != null && StringUtils.isNotBlank(log.getTransactionStatus())) {
                        CodeValues codeValue = codesService.getCodeValueByIdName("TRANSACTION_STATUS", log.getTransactionStatus());

                        if (codeValue != null) {
                            transactionStatus = codeValue.getCodeValueDesc();
                        } else {
                            transactionStatus = log.getTransactionStatus();
                        }
                    }

                    nicTransactionLogInfo.setTransactionStatus(transactionStatus);

                    String regSiteCode = null;
                    if (log != null && StringUtils.isNotBlank(log.getSiteCode())) {
                    	SiteRepository regSite = siteService.getSiteRepoById(log.getSiteCode());
                    	if (regSite != null) {
                    		regSiteCode = regSite.getSiteName();
                    	} else {
                    		regSiteCode = log.getSiteCode();
                    	}
                    }

                    nicTransactionLogInfo.setSiteCode(regSiteCode);

                    // [22 Feb 2016][Tue] - Add
                    // Set transaction stage
                    String transactionStage = null;
                    if (log != null && StringUtils.isNotBlank(log.getTransactionStage())) {
                        CodeValues codeValue = codesService.getCodeValueByIdName("TRANSACTION_STAGE", log.getTransactionStage());

                        if(codeValue == null) {
                        	codeValue = codesService.getCodeValueByIdName("JOB_STAGE", log.getTransactionStage());
                        }
                        if (codeValue != null) {
                        	transactionStage = codeValue.getCodeValueDesc();
                        } else {
                        	transactionStage = log.getTransactionStage();
                        }
                    }
                    nicTransactionLogInfo.setTransactionStage(transactionStage);
                    if(nicTransactionLogInfo.getLogCreateTime() != null){
                    	nicTransactionLogInfo.setLogCreateTimeDesc(DateUtil.parseDate(log.getLogCreateTime(), "dd/MM/yyyy HH:mm:ss"));
                    } 
                    nicTransactionLogInfos.add(nicTransactionLogInfo);
                }
            }
        } catch (Exception ex) {
            logger.info("Error occurred while getting the job enquiry transaction history for Transaction:" + txnId + ", Reason: " + ex.getMessage());
        }

        return new ModelAndView("nic-enquiry-history-events", "nicTransactionLogs", nicTransactionLogInfos);
    }

    @ResponseBody
    @RequestMapping(value = "/jobEnqDetailsInit", method = RequestMethod.GET)
    @ExceptionHandler
    public ModelAndView jobEnqDetailsInit(WebRequest request, ModelMap model) throws Exception {
        
    	return new ModelAndView("nic-enquiry-deatils-init");
    }

    @ResponseBody
    @RequestMapping(value = "/jobEnqJobDetails/{jobId}", method = RequestMethod.GET)
    @ExceptionHandler
    public ModelAndView jobEnqJobDetails(WebRequest request, @PathVariable String jobId, ModelMap model) throws Exception {
        NicUploadJobInfo nicUploadJobInfo = null;
        boolean showErrorBtn = false;
        boolean showRetryBtn = false;
        try {
            if (StringUtils.isNumeric(jobId)) {
                NicUploadJob nicUploadJob = uploadJobService.getJobEnqueryDetails(new Long(jobId));
                if (nicUploadJob != null) {
                	logger.info("jobId:" + jobId + ", CpdCheckStatus: " + nicUploadJob.getCpdCheckStatus()+", AfisCheckStatus: "+nicUploadJob.getAfisCheckStatus()+", AfisRegisterStatus: "+nicUploadJob.getAfisRegisterStatus()+", AfisCheckStatus: "+nicUploadJob.getAfisCheckStatus()+", AfisVerifyStatus: "+nicUploadJob.getAfisVerifyStatus()+", PersoRegisterStatus: "+nicUploadJob.getPersoRegisterStatus());
                    nicUploadJobInfo = new NicUploadJobInfo();

                    PropertyUtils.copyProperties(nicUploadJobInfo, nicUploadJob);
                    //nicUploadJobInfo.setNicJobErrorFlag(nicUploadJob.getJobErrorFlag());
                    nicUploadJobInfo.setNicJobCompletedFlag(nicUploadJob.getJobCompletedFlag());
                    //logger.info("jobId:" + jobId + ", nicUploadJob.getJobErrorFlag(): " + nicUploadJob.getJobErrorFlag()+", nicUploadJob.getJobReprocessCount(): "+nicUploadJob.getJobReprocessCount());
                    logger.info("jobId:" + jobId + ", nicUploadJobInfo.getNicJobErrorFlag(): " + nicUploadJobInfo.getNicJobErrorFlag()+", nicUploadJobInfo.getJobReprocessCount(): "+nicUploadJobInfo.getJobReprocessCount());
                    
                    if (nicUploadJob.getJobStartTime() != null) {
                        nicUploadJobInfo.setJobStartDateTime(DateUtil.parseDate(nicUploadJob.getJobStartTime(), "dd/MM/yyyy hh:mm:ss"));
                    }

                    if (nicUploadJob.getJobEndTime() != null) {
                        nicUploadJobInfo.setJobEndDateTime(DateUtil.parseDate(nicUploadJob.getJobEndTime(), "dd/MM/yyyy hh:mm:ss"));
                    }

                    if (nicUploadJob.getCreateDatetime() != null) {
                        nicUploadJobInfo.setCreateDateTime(DateUtil.parseDate(nicUploadJob.getCreateDatetime(), "dd/MM/yyyy hh:mm:ss"));
                    }

                    if (nicUploadJob.getUpdateDatetime() != null) {
                        nicUploadJobInfo.setUpdateDateTime(DateUtil.parseDate(nicUploadJob.getUpdateDatetime(), "dd/MM/yyyy hh:mm:ss"));
                    }

                    if (nicUploadJob.getJobCreatedTime() != null) {
                        nicUploadJobInfo.setJobUploadDateTime(DateUtil.parseDate(nicUploadJob.getJobCreatedTime(), "dd/MM/yyyy hh:mm:ss"));
                    }

                    String persoRegisterStatus = null;
                    if (nicUploadJobInfo != null && StringUtils.isNotBlank(nicUploadJobInfo.getPersoRegisterStatus())) {
                        CodeValues codeValue = codesService.getCodeValueByIdName("INVESTIGATION_STATUS", nicUploadJobInfo.getPersoRegisterStatus());

                        if (codeValue != null) {
                            persoRegisterStatus = codeValue.getCodeValueDesc();
                        } else {
                            persoRegisterStatus = nicUploadJobInfo.getPersoRegisterStatus();
                        }
                    }

                    nicUploadJobInfo.setPersoRegisterStatus(persoRegisterStatus);

                    String cpdCheckStatus = null;
                    if (nicUploadJobInfo != null && StringUtils.isNotBlank(nicUploadJobInfo.getCpdCheckStatus())) {
                        CodeValues codeValue = codesService.getCodeValueByIdName("INVESTIGATION_STATUS", nicUploadJobInfo.getCpdCheckStatus());

                        if (codeValue != null) {
                            cpdCheckStatus = codeValue.getCodeValueDesc();
                        } else {
                            cpdCheckStatus = nicUploadJobInfo.getCpdCheckStatus();
                        }
                    }

                    nicUploadJobInfo.setCpdCheckStatus(cpdCheckStatus);

                    String afisCheckStatus = null;
                    if (nicUploadJobInfo != null && StringUtils.isNotBlank(nicUploadJobInfo.getAfisCheckStatus())) {
                        CodeValues codeValue = codesService.getCodeValueByIdName("INVESTIGATION_STATUS", nicUploadJobInfo.getAfisCheckStatus());

                        if (codeValue != null) {
                            afisCheckStatus = codeValue.getCodeValueDesc();
                        } else {
                            afisCheckStatus = nicUploadJobInfo.getAfisCheckStatus();
                        }
                    }

                    nicUploadJobInfo.setAfisCheckStatus(afisCheckStatus);

                    String jobTypeDesc = null;
                    if (nicUploadJobInfo != null && StringUtils.isNotBlank(nicUploadJobInfo.getJobType())) {
                        //CodeValues codeValue = codesService.getCodeValueByIdName("TRANSACTION_TYPE", nicUploadJobInfo.getJobType());
                        CodeValues codeValue = codesService.getCodeValueByIdName("JOB_TYPE", nicUploadJobInfo.getJobType()); //2014 Aug 20 

                        if (codeValue != null) {
                            jobTypeDesc = codeValue.getCodeValueDesc();
                        } else {
                            jobTypeDesc = nicUploadJobInfo.getJobType();
                        }
                    }

                    nicUploadJobInfo.setJobType(jobTypeDesc);

                    String status = null;
                    if (nicUploadJobInfo != null && StringUtils.isNotBlank(nicUploadJobInfo.getInvestigationStatus())) {
                        CodeValues codeValue = codesService.getCodeValueByIdName("INVESTIGATION_STATUS", nicUploadJobInfo.getInvestigationStatus());

                        if (codeValue != null) {
                            status = codeValue.getCodeValueDesc();
                        } else {
                            status = nicUploadJobInfo.getInvestigationStatus();
                        }
                    }

                    nicUploadJobInfo.setInvestigationStatus(status);

                    String currentStage = null;
                    if (nicUploadJobInfo != null) {
                        CodeValues codeValue = codesService.getCodeValueByIdName("JOB_STAGE", nicUploadJobInfo.getJobCurrentState());
                        if (codeValue == null) {
                        	codeValue = codesService.getCodeValueByIdName("JOB_STAGE", nicUploadJob.getJobCurrentStage());
                        }
                        
                        if( codeValue != null) {
                        	currentStage = codeValue.getCodeValueDesc();
                        }
                    }

                    nicUploadJobInfo.setJobCurrentState(currentStage);

                    String afisRegStatus = null;
                    if (nicUploadJobInfo != null && StringUtils.isNotBlank(nicUploadJobInfo.getAfisRegisterStatus())) {
                        CodeValues codeValue = codesService.getCodeValueByIdName("INVESTIGATION_STATUS", nicUploadJobInfo.getAfisRegisterStatus());

                        if (codeValue != null) {
                            afisRegStatus = codeValue.getCodeValueDesc();
                        } else {
                            afisRegStatus = nicUploadJobInfo.getAfisRegisterStatus();
                        }
                    }

                    nicUploadJobInfo.setAfisRegisterStatus(afisRegStatus);

                    String afisVerifyStatus = null;
                    if (nicUploadJobInfo != null && StringUtils.isNotBlank(nicUploadJobInfo.getAfisVerifyStatus())) {
                        CodeValues codeValue = codesService.getCodeValueByIdName("INVESTIGATION_STATUS", nicUploadJobInfo.getAfisVerifyStatus());

                        if (codeValue != null) {
                            afisVerifyStatus = codeValue.getCodeValueDesc();
                        } else {
                            afisVerifyStatus = nicUploadJobInfo.getAfisVerifyStatus();
                        }
                    }

                    nicUploadJobInfo.setAfisVerifyStatus(afisVerifyStatus);

                    String syncTxCpdStatus = null;
                    if (nicUploadJobInfo != null && StringUtils.isNotBlank(nicUploadJobInfo.getSyncTxCpdStatus())) {
                        CodeValues codeValue = codesService.getCodeValueByIdName("INVESTIGATION_STATUS", nicUploadJobInfo.getSyncTxCpdStatus());

                        if (codeValue != null) {
                            syncTxCpdStatus = codeValue.getCodeValueDesc();
                        } else {
                            syncTxCpdStatus = nicUploadJobInfo.getSyncTxCpdStatus();
                        }
                    }

                    nicUploadJobInfo.setSyncTxCpdStatus(syncTxCpdStatus);

                    String syncCardCpdStatus = null;
                    if (nicUploadJobInfo != null && StringUtils.isNotBlank(nicUploadJobInfo.getSyncCardCpdStatus())) {
                        CodeValues codeValue = codesService.getCodeValueByIdName("INVESTIGATION_STATUS", nicUploadJobInfo.getSyncCardCpdStatus());

                        if (codeValue != null) {
                            syncCardCpdStatus = codeValue.getCodeValueDesc();
                        } else {
                            syncCardCpdStatus = nicUploadJobInfo.getSyncCardCpdStatus();
                        }
                    }

                    nicUploadJobInfo.setSyncCardCpdStatus(syncCardCpdStatus);

                    String dataPreparationStatus = null;
                    if (nicUploadJobInfo != null && StringUtils.isNotBlank(nicUploadJobInfo.getDataPreparationStatus())) {
                        CodeValues codeValue = codesService.getCodeValueByIdName("INVESTIGATION_STATUS", nicUploadJobInfo.getDataPreparationStatus());

                        if (codeValue != null) {
                            dataPreparationStatus = codeValue.getCodeValueDesc();
                        } else {
                            dataPreparationStatus = nicUploadJobInfo.getDataPreparationStatus();
                        }
                    }

                    nicUploadJobInfo.setDataPreparationStatus(dataPreparationStatus);

                    String syncInventoryStatus = null;
                    if (nicUploadJobInfo != null && StringUtils.isNotBlank(nicUploadJobInfo.getSyncInventoryStatus())) {
                        CodeValues codeValue = codesService.getCodeValueByIdName("INVESTIGATION_STATUS", nicUploadJobInfo.getSyncInventoryStatus());

                        if (codeValue != null) {
                            syncInventoryStatus = codeValue.getCodeValueDesc();
                        } else {
                            syncInventoryStatus = nicUploadJobInfo.getSyncInventoryStatus();
                        }
                    }

                    nicUploadJobInfo.setSyncInventoryStatus(syncInventoryStatus);

                    String jobCurrentStatus = null;
                    if (nicUploadJobInfo != null && StringUtils.isNotBlank(nicUploadJobInfo.getJobCurrentStatus())) {
                        CodeValues codeValue = codesService.getCodeValueByIdName("JOB_STATUS", nicUploadJobInfo.getJobCurrentStatus());

                        if (codeValue != null) {
                            jobCurrentStatus = codeValue.getCodeValueDesc();
                        } else {
                            jobCurrentStatus = nicUploadJobInfo.getJobCurrentStatus();
                        }
                    }

                    nicUploadJobInfo.setJobCurrentStatus(jobCurrentStatus);
                    //2014 Aug 20 - simplify logic
                    if (nicUploadJobInfo.getNicJobErrorFlag()) {

                        NicTransactionLog nicTransactionLog = uploadJobService.getLatestErrorMessage(nicUploadJobInfo.getTransactionId());
                        if (nicTransactionLog != null) {
                            nicUploadJobInfo.setErrorDesc(nicTransactionLog.getLogData());
                        }
                        
                        if (nicUploadJobInfo.getJobReprocessCount()!=null && nicUploadJobInfo.getJobReprocessCount().intValue() >= 3) {
                            showRetryBtn = true;
                        }

                        if (StringUtils.contains(nicUploadJobInfo.getJobCurrentStatus(), "_ERROR")) {
                            showErrorBtn = true;
                        }
                    }
                }
            }
        } catch (Exception ex) {
            logger.error("Error occurred while getting the job deatils for Transaction:" + jobId + ", Reason: " + ex.getMessage(), ex);
        }

        ModelAndView mav = new ModelAndView("nic-enquiry-jobInfo");
        mav.addObject("nicUploadJob", nicUploadJobInfo);
        mav.addObject("showErrorBtn", showErrorBtn);
        mav.addObject("showRetryBtn", showRetryBtn);

        return mav;
    }

    @ResponseBody
    @RequestMapping(value = "/jobEnqDemographicDetails/{jobId}", method = RequestMethod.GET)
    @ExceptionHandler
    public ModelAndView jobEnqDemographicDetails(WebRequest request, @PathVariable String jobId, ModelMap model) throws Exception {
        NicRegistrationDataDto ricAndCpdData = null;
        ModelAndView mav = new ModelAndView("nic-enquiry-demographicInfo");
        try {
            HitCandidateDTO candidateCPDData = new HitCandidateDTO();
            NicUploadJob nicUploadJob = uploadJobService.findById(Long.valueOf(jobId));
            String ricDOB = null;
            String cpdDOB = null;
            String townVillageDesc = "";
            String districtDesc = "";

            String cpdTownVillageDesc = "";
            String cpdDistrictDesc = "";
            //nicForm.setNicUploadJob(nicUploadJob);		

            // 2) Get The Photo of the main candidate of the current job by
            // Transaction Id

            String transactionId = nicUploadJob.getTransactionId();// this one is null

            NicRegistrationData nicRegistrationData = uploadJobService.getNicRegistrationData(transactionId);
            ricAndCpdData = new NicRegistrationDataDto(nicRegistrationData);

            if (ricAndCpdData != null) {

                if (ricAndCpdData.getAddressLine4() != null && StringUtils.isNotBlank(ricAndCpdData.getAddressLine4())) {
                    CodeValues codeValue = codesService.getCodeValueByIdName("TOWN_VILLAGE", ricAndCpdData.getAddressLine4());
                    if (codeValue != null) {
                        townVillageDesc = codeValue.getCodeValueDesc();
                    } else {
                        townVillageDesc = ricAndCpdData.getAddressLine4();
                    }
                }
                String district = ricAndCpdData.getAddressLine5();
                if (district != null && StringUtils.isNotBlank(district)) {
                    CodeValues codeValue = codesService.getCodeValueByIdName("DISTRICT", district);

                    if (codeValue != null) {
                        districtDesc = codeValue.getCodeValueDesc();
                    } else {
                        districtDesc = district;
                    }
                }

                if (ricAndCpdData.getCapdAddressLine4() != null && StringUtils.isNotBlank(ricAndCpdData.getCapdAddressLine4())) {
                    CodeValues codeValue = codesService.getCodeValueByIdName("TOWN_VILLAGE", ricAndCpdData.getCapdAddressLine4());
                    if (codeValue != null) {
                        cpdTownVillageDesc = codeValue.getCodeValueDesc();
                    } else {
                        cpdTownVillageDesc = ricAndCpdData.getCapdAddressLine4();
                    }
                }
                String cpdDistrict = ricAndCpdData.getCpdAddressLine5();
                if (cpdDistrict != null && StringUtils.isNotBlank(cpdDistrict)) {
                    CodeValues codeValue = codesService.getCodeValueByIdName("DISTRICT", cpdDistrict);

                    if (codeValue != null) {
                        cpdDistrictDesc = codeValue.getCodeValueDesc();
                    } else {
                        cpdDistrictDesc = cpdDistrict;
                    }
                }

                if (StringUtils.isNotBlank(ricAndCpdData.getGender())) {
                    if (ricAndCpdData.getGender().equalsIgnoreCase("M")) {
                        ricAndCpdData.setGender("Male");
                    } else if (ricAndCpdData.getGender().equalsIgnoreCase("F")) {
                        ricAndCpdData.setGender("Female");
                    } else if (ricAndCpdData.getGender().equalsIgnoreCase("X")) {
                        ricAndCpdData.setGender("Unknown");
                    }
                }

                if (StringUtils.isNotBlank(ricAndCpdData.getMaritalStatus())) {
                    if (ricAndCpdData.getMaritalStatus().equalsIgnoreCase("M")) {
                        ricAndCpdData.setMaritalStatus("Married");
                    } else if (ricAndCpdData.getMaritalStatus().equalsIgnoreCase("S")) {
                        ricAndCpdData.setMaritalStatus("Single");
                    } else if (ricAndCpdData.getMaritalStatus().equalsIgnoreCase("O")) {
                        ricAndCpdData.setMaritalStatus("Others");
                    }
                }

                if (ricAndCpdData.getDateOfBirth() != null) {
                    ricDOB = DateUtil.parseDate(ricAndCpdData.getDateOfBirth(), "dd/MM/yyyy");
                }
            }
            NicJobCpdCheckResult cpdData = uploadJobService.findCpdData(Long.valueOf(jobId));
            String dataFromCpd = "";
            if (cpdData != null) {
                dataFromCpd = cpdData.getCpdXml();
            }
            CpdReferenceDataDTO convertedCpdData = new CpdReferenceDataDTO();
            if (!dataFromCpd.equals("")) {
                convertedCpdData = (CpdReferenceDataDTO) util.unmarshal(dataFromCpd);
            }
            if (convertedCpdData != null) {
                BaseDTOMapper.copyProperties(candidateCPDData, convertedCpdData);

                ricAndCpdData.setCpdFirstnameFull(candidateCPDData.getFirstName());
                ricAndCpdData.setCpdSurnameFull(candidateCPDData.getSurname());
                ricAndCpdData.setCpdSurnameBirthFull(candidateCPDData.getSurnameAtBirth());
                //			ricAndCpdData.setCpdGender(candidateCPDData.getSex());

                if (candidateCPDData != null) {
                    if (StringUtils.isNotBlank(candidateCPDData.getSex())) {
                        if (candidateCPDData.getSex().equalsIgnoreCase("M")) {
                            ricAndCpdData.setCpdGender("Male");
                        } else if (candidateCPDData.getSex().equalsIgnoreCase("F")) {
                            ricAndCpdData.setCpdGender("Female");
                        } else if (candidateCPDData.getSex().equalsIgnoreCase("X")) {
                            ricAndCpdData.setCpdGender("Unknown");
                        }
                    }
                }

                if (StringUtils.isNotBlank(candidateCPDData.getMaritalStatus())) {
                    if (candidateCPDData.getMaritalStatus().equalsIgnoreCase("M")) {
                        ricAndCpdData.setCpdMaritalStatus("Married");
                    } else if (candidateCPDData.getMaritalStatus().equalsIgnoreCase("S")) {
                        ricAndCpdData.setCpdMaritalStatus("Single");
                    } else if (candidateCPDData.getMaritalStatus().equalsIgnoreCase("O")) {
                        ricAndCpdData.setCpdMaritalStatus("Others");
                    }
                }

                if (candidateCPDData.getBirthDate() != null) {
                    cpdDOB = DateUtil.parseDate(candidateCPDData.getBirthDate(), "dd/MM/yyyy");
                }

                //			ricAndCpdData.setCpdMaritalStatus(candidateCPDData.getMaritalStatus());
                ricAndCpdData.setCapdAddressLine4(candidateCPDData.getTownVillage());
                ricAndCpdData.setCpdDateOfBirth(candidateCPDData.getBirthDate());
                ricAndCpdData.setCpdFatherName(candidateCPDData.getFatherFirstName());
                ricAndCpdData.setCpdFatherSurname(candidateCPDData.getFatherSurname());
                ricAndCpdData.setCpdMotherName(candidateCPDData.getMotherFirstName());
                ricAndCpdData.setCpdMotherSurname(candidateCPDData.getMotherSurname());
                ricAndCpdData.setCpdNin(candidateCPDData.getNin());
            }
            model.addAttribute("fnSelected", Constants.HEADING_NIC_JOB_ENQUIRY);
            mav.addObject("ricAndCpdData", ricAndCpdData);
            mav.addObject("ricDOB", ricDOB);
            mav.addObject("cpdDOB", cpdDOB);

            mav.addObject("townVillageDesc", townVillageDesc);
            mav.addObject("districtDesc", districtDesc);
            mav.addObject("cpdTownVillageDesc", cpdTownVillageDesc);
            mav.addObject("cpdDistrictDesc", cpdDistrictDesc);

        } catch (Exception ex) {
            logger.info("Error occurred while getting the demographic info for :" + jobId + ", Reason: " + ex.getMessage());
        }

        return mav;
    }

    @ResponseBody
    @RequestMapping(value = "/getPersonSummary/{txnId}", method = RequestMethod.GET)
    @ExceptionHandler
    public ModelAndView getPersonSummary(WebRequest request, @PathVariable String txnId, ModelMap model) throws Exception {
        NicRegistrationData nicRegistrationData = null;
        String photoStr = null;
        String signatureStr = null;
        String signatureFlag = "N";
        String fullAmpFlag = "N";
        String approxDOBFlag = "N";
        String dob = "";
        String townVillageDesc = "";
        String districtDesc = "";
        //[19Feb2016][Tue] - Add
        String passportNo = "";
        String passportStatus = "";
        List<NicDocumentData> docList = null;
        try {
            nicRegistrationData = uploadJobService.getNicRegistrationData(txnId);
            List<NicTransactionAttachment> photoList =
                nicTransactionDocumentService.findNicTransactionAttachments(txnId, NicTransactionAttachment.DOC_TYPE_PHOTO_CAPTURE, NicTransactionAttachment.DEFAULT_SERIAL_NO).getListModel();
            //			if(!nicRegistrationData.getFullAmputatedFlag()){
            //				if(StringUtils.isNotBlank(nicRegistrationData.getSignatureIndicator()) && nicRegistrationData.getSignatureIndicator().equalsIgnoreCase("S")){
            List<NicTransactionAttachment> signatureList =
                nicTransactionDocumentService.findNicTransactionAttachments(txnId, NicTransactionAttachment.DOC_TYPE_SIGNATURE, NicTransactionAttachment.DEFAULT_SERIAL_NO).getListModel();
            if (CollectionUtils.isNotEmpty(signatureList)) {
                byte[] signBinary = signatureList.get(0).getDocData();
                signatureStr = Base64.encodeBase64String(signBinary);
                signatureFlag = "S";
            }
			else if(CollectionUtils.isEmpty(signatureList)){ //Thumbprint as Signature 'T'
	    		List<NicTransactionAttachment> fpSignList = nicTransactionDocumentService.findNicTransactionAttachments(txnId, NicTransactionAttachment.DOC_TYPE_SIGN_FP, NicTransactionAttachment.DEFAULT_SERIAL_NO).getListModel();
				if (CollectionUtils.isNotEmpty(fpSignList)) {
					byte[] signBinary = fpSignList.get(0).getDocData();
					signatureStr = Base64.encodeBase64String(signBinary);
					signatureFlag = "T";
				}
	    	}
            //else {
            //    fullAmpFlag = "Y";
            //}
            
            if (StringUtils.isBlank(nicRegistrationData.getFpQuality())) {
            	fullAmpFlag = "Y";
            	logger.info("[{}] FpQuality is empty, fullAmpFlag: {}", txnId, fullAmpFlag);
            }

            if (CollectionUtils.isNotEmpty(photoList) && photoList.size() > 0) {
                photoStr = Base64.encodeBase64String(photoList.get(0).getDocData());
            }
            
            if (nicRegistrationData.getDateOfBirth() != null) {
                dob = DateUtil.parseDate(nicRegistrationData.getDateOfBirth(), "dd/MM/yyyy");

            } else {
                approxDOBFlag = "Y";
                dob = nicRegistrationData.getApproxDob();
            }

            if (nicRegistrationData.getAddressLine4() != null && StringUtils.isNotBlank(nicRegistrationData.getAddressLine4())) {
                CodeValues codeValue = codesService.getCodeValueByIdName("TOWN_VILLAGE", nicRegistrationData.getAddressLine4());
                if (codeValue != null) {
                    townVillageDesc = codeValue.getCodeValueDesc();
                } else {
                    townVillageDesc = nicRegistrationData.getAddressLine4();
                }
            }
            String fullName = "";
            if(!StringUtils.isEmpty(nicRegistrationData.getSurnameFull())){
            	fullName += nicRegistrationData.getSurnameFull();
            }
            if(!StringUtils.isEmpty(nicRegistrationData.getMiddlenameFull())){
            	fullName += " " + nicRegistrationData.getMiddlenameFull();
            }
            if(!StringUtils.isEmpty(nicRegistrationData.getFirstnameFull())){
            	fullName += " " + nicRegistrationData.getFirstnameFull();
            }
            nicRegistrationData.setSurnameFull(fullName);
            //			String district = nicRegistrationData.getAddressLine5();
            //			if(district !=null && StringUtils.isNotBlank(district)){
            //				CodeValues codeValue = codesService.getCodeValueByIdName("DISTRICT", district);
            //				
            //				if(codeValue!=null){
            //					districtDesc = codeValue.getCodeValueDesc();
            //				}else{
            //					districtDesc = district;
            //				}
            //			}

            //[19Feb2016][Tue] - Add
            docList = (List<NicDocumentData>) documentDataService.findByTransactionId(txnId);
            if(CollectionUtils.isNotEmpty(docList) && null != docList.get(0).getId()) {
            	passportNo = docList.get(0).getId().getPassportNo();
            	if (StringUtils.isNotBlank(docList.get(0).getStatus())) {
            		CodeValues codeValue = codesService.getCodeValueByIdName("TRANSACTION_STATUS", docList.get(0).getStatus());
                    if (codeValue != null) {
                    	passportStatus = codeValue.getCodeValueDesc();
                    } else {
                    	passportStatus = docList.get(0).getStatus();
                    }
            	}
            }
            
        } catch (Exception ex) {
            logger.info("Error occurred while getting the person summary information for Transaction:" + txnId + ", Reason: " + ex.getMessage());
        }

        ModelAndView mav = new ModelAndView("nic-enquiry-person-summary");

        mav.addObject("photoStr", photoStr);
        mav.addObject("signatureStr", signatureStr);
        mav.addObject("fullAmpFlag", fullAmpFlag);
        mav.addObject("signatureFlag", signatureFlag);
        mav.addObject("approxDOBFlag", approxDOBFlag);
        mav.addObject("townVillageDesc", townVillageDesc);
        mav.addObject("districtDesc", districtDesc);
        mav.addObject("dob", dob);
        mav.addObject("nicRegistrationData", nicRegistrationData);
        mav.addObject("passportNo", passportNo);
        mav.addObject("passportStatus", passportStatus);
        
        return mav;

    }

    @ResponseBody
    @RequestMapping(value = "/getTransDocuments/{txnId}", method = RequestMethod.GET)
    @ExceptionHandler
    public ModelAndView getTransDocuments(WebRequest request, @PathVariable String txnId, ModelMap model, HttpServletRequest httpServletRequest) throws Exception {
        List<NicTransactionAttachment> nicTransactionDocuments = null;
        List<RicRegistrationDocumentDTO> proofDocList = new ArrayList<RicRegistrationDocumentDTO>();
        //[20 Feb 2016][Tue] - Add
        int pageSize = Constants.PAGE_SIZE_DEFAULT;
		int currentPage = 1;
		String tableId = "proofDocList";
        String sortedElement = "serialNo";
		String order = EnquiryController.DESCENDING_ORDER;
		ParametersId id = new ParametersId();
		id.setParaName(Parameters.MAX_RECORDS_PER_PAGE);
		id.setParaScope("SYSTEM");		
        try {
        	
			Order hibernateOrder = Order.desc(sortedElement);
			if (order.equalsIgnoreCase(EnquiryController.ASCENDING_ORDER)) {
				hibernateOrder = Order.asc(sortedElement);
			}
			
			try {
				if (null != request
						.getParameter((new ParamEncoder(tableId).encodeParameterName(TableTagParameters.PARAMETER_PAGE)))) {
					currentPage = Integer.parseInt(request.getParameter(
							(new ParamEncoder(tableId).encodeParameterName(TableTagParameters.PARAMETER_PAGE))));
				}
				// End
			} catch (Exception ex) {
				throw ex;
			}
			
			Parameters parameter = parametersService.findById(id);
			if (parameter != null) {
				String pageSizeDb = parameter.getParaShortValue();

				if (StringUtils.isNotBlank(pageSizeDb) && StringUtils.isNumeric(pageSizeDb)) {
					pageSize = Integer.parseInt(pageSizeDb);
				}
			}
		
            httpServletRequest.setAttribute("txnId", txnId);
            ImageUtil imageUtil = new ImageUtil();

			String[] excludedDocTypes = { NicTransactionAttachment.DOC_TYPE_FINGERPRINT,
					NicTransactionAttachment.DOC_TYPE_MINUTIA, NicTransactionAttachment.DOC_TYPE_MINUTIA_CBEFF,
					NicTransactionAttachment.DOC_TYPE_ENCODING, NicTransactionAttachment.DOC_TYPE_PERSO, 
					NicTransactionAttachment.DOC_TYPE_SIGNATURE, NicTransactionAttachment.DOC_TYPE_SIGN_FP, 
					NicTransactionAttachment.DOC_TYPE_PHOTO_CAPTURE, NicTransactionAttachment.DOC_TYPE_THUMBNAIL_CAPTURE, 
					NicTransactionAttachment.DOC_TYPE_PHOTO_GREY, NicTransactionAttachment.DOC_TYPE_THUMBNAIL_GREY, 
					NicTransactionAttachment.DOC_TYPE_PHOTO_CHIP, NicTransactionAttachment.DOC_TYPE_THUMBNAIL_CHIP, 
					NicTransactionAttachment.DOC_TYPE_PHOTO_CLI, 
					NicTransactionAttachment.DOC_TYPE_TPL };

            // [20 Feb 2016][Tue] - Add
           /* PaginatedResult<NicTransactionAttachment> transAttList = nicTransactionDocumentService.findAllForPagination(currentPage, pageSize, hibernateOrder);
            if (null != transAttList) {
                for (NicTransactionAttachment nicTransProofDocument : transAttList.getRows()) {
                    RicRegistrationDocumentDTO proofDoc = new RicRegistrationDocumentDTO();
                    String decodedDocString = Base64.encodeBase64String(nicTransProofDocument.getDocData());
                    String imageType = imageUtil.checkImageType(nicTransProofDocument.getDocData());
                    String docType = nicTransProofDocument.getDocType();
                    if (StringUtils.isNotBlank(imageType) && imageType.equalsIgnoreCase(ImageUtil.IMAGE_JPG)) {
                        proofDoc.setDocumentName("D" + nicTransProofDocument.getTransactionDocId());
                        proofDoc.setType("JPEG");
                    } else {
                        proofDoc.setDocumentName(docType);
                        proofDoc.setType("PDF");
                    }
                    proofDoc.setDocumentId(String.valueOf(nicTransProofDocument.getTransactionDocId()));
                    proofDoc.setTransactionNo(nicTransProofDocument.getTransactionId());
                    String docTypeDesc = null;
                    //Document Description from Code Table
                    if (docType != null && StringUtils.isNotBlank(docType)) {
                        CodeValues codeValue = codesService.getCodeValueByIdName("DOC_TYPE", docType);
                        if (codeValue != null) {
                            docTypeDesc = codeValue.getCodeValueDesc();
                        } else {
                            docTypeDesc = docType;
                        }
                    }
                    proofDoc.setPurpose(docTypeDesc);
                    List<String> documentList = new ArrayList<String>();
                    documentList.add(decodedDocString);
                    proofDoc.setDocument(documentList);
                    proofDocList.add(proofDoc);
                }
            }*/
            
            nicTransactionDocuments = nicTransactionDocumentService.getTransactionProofDocuments(txnId, excludedDocTypes);            
            if (CollectionUtils.isNotEmpty(nicTransactionDocuments)) {
                for (NicTransactionAttachment nicTransProofDocument : nicTransactionDocuments) {
                    RicRegistrationDocumentDTO proofDoc = new RicRegistrationDocumentDTO();
                    String decodedDocString = Base64.encodeBase64String(nicTransProofDocument.getDocData());
                    String imageType = imageUtil.checkImageType(nicTransProofDocument.getDocData());
                    String docType = nicTransProofDocument.getDocType();

                    if (StringUtils.isNotBlank(imageType) && imageType.equalsIgnoreCase(ImageUtil.IMAGE_JPG)) {
                        proofDoc.setDocumentName("D" + nicTransProofDocument.getTransactionDocId());
                        proofDoc.setType("JPEG");
                    } else {
                        proofDoc.setDocumentName(docType);
                        proofDoc.setType("PDF");
                    }

                    proofDoc.setDocumentId(String.valueOf(nicTransProofDocument.getTransactionDocId()));
                    proofDoc.setTransactionNo(nicTransProofDocument.getTransactionId());
                    proofDoc.setSerialNumber(nicTransProofDocument.getSerialNo());
                    
                    String docTypeDesc = null;
                    //Document Description from Code Table
                    if (docType != null && StringUtils.isNotBlank(docType)) {
                        CodeValues codeValue = codesService.getCodeValueByIdName("DOC_TYPE", docType);
                        if (codeValue != null) {
                            docTypeDesc = codeValue.getCodeValueDesc();
                        } else {
                            docTypeDesc = docType;
                        }
                    }
                    // proofDoc.setDocumentName(docType);

                    proofDoc.setPurpose(docTypeDesc);

					proofDoc.setDmsLink(this.getDmsLink(nicTransProofDocument.getTransactionDocId(), nicTransProofDocument.getTransactionId(), nicTransProofDocument.getDocType()));

                    List<String> documentList = new ArrayList<String>();
                    documentList.add(decodedDocString);
                    proofDoc.setDocument(documentList);
                    proofDocList.add(proofDoc);
                }
            }
        } catch (Exception ex) {
            logger.info("Error occurred while getting the transaction documents for Transaction:" + txnId + ", Reason: " + ex.getMessage());
        }
        httpServletRequest.setAttribute("pageSize", pageSize);
        httpServletRequest.setAttribute("proofDocList", proofDocList);
        return new ModelAndView("nic-enquiry-trans-docs");

    }

	private String getDmsLink(Long transactionDocId, String transactionId, String docType) throws Exception {

		String attachmentSource = null;
		try {
			Parameters parameter = this.parametersService.getParamDetails(
					EnquiryController.PARA_TRANSACTION_ATTACHMENT_SOURCE_SCOPE,
					EnquiryController.PARA_TRANSACTION_ATTACHMENT_SOURCE_VALUE);
			if (parameter != null && StringUtils.isNotBlank(parameter.getParaShortValue())) {
				attachmentSource = parameter.getParaShortValue();
			}
		} catch (Exception e) {
		}

		if (attachmentSource == null) {
			InvalidParameterException exception = new InvalidParameterException(
					"Parameter TRANSACTION_ATTACHMENT_SOURCE is required.");
			exception.printStackTrace();
			throw exception;
		}

		if (!(attachmentSource.equalsIgnoreCase(AttachmentSource.DMS))) {
			return null;
		}

		String urlPrefix = null;
		try {
			Parameters parameter = this.parametersService.getParamDetails(
					EnquiryController.PARA_SCOPE_SYSTEM,
					EnquiryController.PARA_NAME_SYSTEM_DMS_URL);
			if (parameter != null && parameter.getParaLobValue() != null) {
				urlPrefix = parameter.getParaLobValue();
			}
		} catch (Exception e) {
		}

		if (StringUtils.isBlank(urlPrefix)) {
			InvalidParameterException exception = new InvalidParameterException("Parameter DMS_URL is required.");
			exception.printStackTrace();
			throw exception;
		}

		return urlPrefix + DmsUtility.DMS__URL_DELIMITER_1 + transactionDocId + DmsUtility.DMS__URL_DELIMITER_2
				+ transactionId + DmsUtility.DMS__URL_DELIMITER_3 + docType;
	}

    @ResponseBody
    @RequestMapping(value = "/showJPEGProofDoc/{transId}/{docType}")
    public void showJPEGProofDoc(
        @PathVariable String transId, @PathVariable String docType, WebRequest request, ModelMap model, RicRegistrationDocumentDTO ricRegistrationDocumentDTO, HttpServletResponse response)
            throws Exception {
        ServletOutputStream ouputStream = null;
        try {
            byte[] doc = null;
            ouputStream = response.getOutputStream();
            List<NicTransactionAttachment> nicTransactionDocuments = nicTransactionDocumentService.findNicTransactionAttachments(transId, docType, null).getListModel();

            if (CollectionUtils.isNotEmpty(nicTransactionDocuments) && nicTransactionDocuments.size() > 0) {
                doc = nicTransactionDocuments.get(0).getDocData();

                response.setContentType("image/jpg");
                ouputStream.write(doc);
            } else {
                ouputStream.print("Không tìm thấy Tài liệu để tải xuống.");
                return;
            }
        } catch (Exception e) {
            ouputStream.print("Xảy ra lỗi trong khi tải xuống tài liệu.");
        } finally {
            if (ouputStream != null) {
                try {
                    ouputStream.close();
                } catch (IOException e) {
                    throw new Exception(e);
                }
            }
        }

    }

    @ResponseBody
    @RequestMapping(value = "/showPDFProofDoc/{transId}/{docType}")
    public void showPDFProofDoc(
        @PathVariable String transId, @PathVariable String docType, WebRequest request, ModelMap model, RicRegistrationDocumentDTO ricRegistrationDocumentDTO, HttpServletResponse response)
            throws Exception {
        ServletOutputStream ouputStream = null;
        try {
            byte[] doc = null;
            ouputStream = response.getOutputStream();
            List<NicTransactionAttachment> nicTransactionDocuments = nicTransactionDocumentService.findNicTransactionAttachments(transId, docType, null).getListModel();

            if (CollectionUtils.isNotEmpty(nicTransactionDocuments) && nicTransactionDocuments.size() > 0) {
                doc = nicTransactionDocuments.get(0).getDocData();

                response.setContentType("application/pdf");
                ouputStream.write(doc);
            } else {
                ouputStream.print("Không tìm thấy Tài liệu để tải xuống.");
                return;
            }
        } catch (Exception e) {
            ouputStream.print("Xảy ra lỗi trong khi tải xuống tài liệu.");
        } finally {
            if (ouputStream != null) {
                try {
                    ouputStream.close();
                } catch (IOException e) {
                    throw new Exception(e);
                }
            }
        }
    }

    @ResponseBody
    @RequestMapping(value = "/photosFullView", method = RequestMethod.GET)
    @ExceptionHandler
    public ModelAndView photosFullView(HttpServletRequest request, ModelMap model) throws Exception {
    	try {
            PhotoEnquiryInfo photoEnquiryInfo = new PhotoEnquiryInfo();
            /*String[] docTypes = {
                NicTransactionAttachment.DOC_TYPE_PHOTO_CAPTURE, NicTransactionAttachment.DOC_TYPE_PHOTO_GREY, NicTransactionAttachment.DOC_TYPE_PHOTO_CHIP, NicTransactionAttachment.DOC_TYPE_PHOTO_CLI
            };*/
            String[] docTypes = {
                    NicTransactionAttachment.DOC_TYPE_PHOTO_CAPTURE, NicTransactionAttachment.DOC_TYPE_SIGNATURE };
            String[] serialNums = {
                NicTransactionAttachment.DEFAULT_SERIAL_NO
            };
            String transactionId = request.getParameter("transactionId");
            //String nin = request.getParameter("nin");
            String passportNo = request.getParameter("passportNo");
            
            photoEnquiryInfo.setTransactionId(transactionId);
            //[19Feb2016][Tue] - Edit
            //photoEnquiryInfo.setNin(nin);
            photoEnquiryInfo.setPassportNo(passportNo);

            List<NicTransactionAttachment> nicTransactionDocuments = nicTransactionDocumentService.getNicTransactionAttachments(transactionId, docTypes, serialNums);

            /*if (CollectionUtils.isNotEmpty(nicTransactionDocuments)) {
                for (NicTransactionAttachment nicTransactionDocument : nicTransactionDocuments) {
                    if (nicTransactionDocument != null && StringUtils.isNotBlank(nicTransactionDocument.getDocType())
                        && nicTransactionDocument.getDocType().equalsIgnoreCase(NicTransactionAttachment.DOC_TYPE_PHOTO_CAPTURE)) {
                        photoEnquiryInfo.setCapturePhotoStr(Base64.encodeBase64String(nicTransactionDocument.getDocData()));
                    } else if (nicTransactionDocument != null && StringUtils.isNotBlank(nicTransactionDocument.getDocType())
                        && nicTransactionDocument.getDocType().equalsIgnoreCase(NicTransactionAttachment.DOC_TYPE_PHOTO_GREY)) {
                        photoEnquiryInfo.setGreyPhotoStr(Base64.encodeBase64String(nicTransactionDocument.getDocData()));
                    } else if (nicTransactionDocument != null && StringUtils.isNotBlank(nicTransactionDocument.getDocType())
                        && nicTransactionDocument.getDocType().equalsIgnoreCase(NicTransactionAttachment.DOC_TYPE_PHOTO_CHIP)) {
                        photoEnquiryInfo.setChipPhotoStr(Base64.encodeBase64String(nicTransactionDocument.getDocData()));
                    } else if (nicTransactionDocument != null && StringUtils.isNotBlank(nicTransactionDocument.getDocType())
                        && nicTransactionDocument.getDocType().equalsIgnoreCase(NicTransactionAttachment.DOC_TYPE_PHOTO_CLI)) {
                        photoEnquiryInfo.setCliPhotoStr(Base64.encodeBase64String(nicTransactionDocument.getDocData()));
                    }
                }
            }*/
            
            if (CollectionUtils.isNotEmpty(nicTransactionDocuments)) {
                for (NicTransactionAttachment nicTransactionDocument : nicTransactionDocuments) {
                    if (nicTransactionDocument != null && StringUtils.isNotBlank(nicTransactionDocument.getDocType())
                        && nicTransactionDocument.getDocType().equalsIgnoreCase(NicTransactionAttachment.DOC_TYPE_PHOTO_CAPTURE)) {
                        photoEnquiryInfo.setCapturePhotoStr(Base64.encodeBase64String(nicTransactionDocument.getDocData()));
                    } else if (nicTransactionDocument != null && StringUtils.isNotBlank(nicTransactionDocument.getDocType())
                        && nicTransactionDocument.getDocType().equalsIgnoreCase(NicTransactionAttachment.DOC_TYPE_SIGNATURE)) {
                        photoEnquiryInfo.setSignatureStr(Base64.encodeBase64String(nicTransactionDocument.getDocData()));
                    } 
                }
            }

            request.setAttribute("photoEnquiryInfo", photoEnquiryInfo);
            request.setAttribute("passportNo", passportNo);
            return new ModelAndView("photo-full-view");
        } catch (Exception ex) {
            ModelAndView modelAndView = new ModelAndView();
            modelAndView.setViewName("errorWithoutMenu");
            modelAndView.addObject("exception", ex);
            modelAndView.addObject("message", ex.getLocalizedMessage());
            modelAndView.addObject("messageStack", ex.getMessage());
            return modelAndView;
        }
    }
    
    public String convertImageFormatWsqToJpg1(byte[] imageBase64String) {
		String convertedBase64String = "";
		try {
			convertedBase64String = Base64.encodeBase64String(HelperClass.ConvertWSQToJPG(imageBase64String));
			// System.out.println("The image binary after base64 conversion===="+convertedBase64String);
		} catch (Exception ex) {
			ex.printStackTrace();
			System.out.println("Đã xảy ra lỗi khi thực hiện" + ex.getMessage());
		}
		return convertedBase64String;
	}

    @ResponseBody
    @RequestMapping(value = "/fpInfo", method = RequestMethod.GET)
    @ExceptionHandler
    public ModelAndView fpInfo(HttpServletRequest request, ModelMap model) throws Exception {
        try {

            UserSession userSession = (UserSession) request.getSession().getAttribute("userSession");
            String transactionId = request.getParameter("transactionId");
            //[19Feb2016][Tue] - Add
            //String nin = request.getParameter("nin");
            String passportNo = request.getParameter("passportNo");
            
            String fpIndicator = request.getParameter("fpIndicator");
            String fpQuality = request.getParameter("fpQuality");
            String fpEncode = request.getParameter("fpEncode");
            String fpVerifyScore = request.getParameter("fpVerifyScore");
            String fpVerifyMatchScore = "";
            String viewFPFalg = "Y";
            String[] goodFpQuas = null;
            String[] fpIndicators = fpIndicator.split(",");
            String[] fpQualitys = fpQuality.split(",");
            String[] fpEncodes = fpEncode.split(",");
            String[] fpVerifyScores = fpVerifyScore.split(",");
            String defGoodFpQuas = "1-65,2-65,3-55,4-40,5-30,6-65,7-65,8-55,9-40,10-30";
            String defAcceptFpQuas = "1-40,2-40,3-40,4-25,5-15,6-40,7-40,8-40,9-25,10-15";
            String[] accFpQuas = null;
            ParametersId goodFpQuaParamId = new ParametersId();
            goodFpQuaParamId.setParaName(Parameters.FP_QUALITY_GOOD);
            goodFpQuaParamId.setParaScope(Parameters.SCOPE_SYSTEM);

            Parameters goodFpQuaParam = parametersService.findById(goodFpQuaParamId);

            if (goodFpQuaParam != null) {
                goodFpQuas = goodFpQuaParam.getParaShortValue().split(",");
            } else {
                goodFpQuas = defGoodFpQuas.split(",");
            }

            ParametersId accFpQuaParamId = new ParametersId();
            accFpQuaParamId.setParaName(Parameters.FP_QUALITY_ACCEPTABLE);
            accFpQuaParamId.setParaScope(Parameters.SCOPE_SYSTEM);

            Parameters accFpQuaParam = parametersService.findById(accFpQuaParamId);

            if (accFpQuaParam != null) {
                accFpQuas = accFpQuaParam.getParaShortValue().split(",");
            } else {
                accFpQuas = defAcceptFpQuas.split(",");
            }

            ParametersId paramId = new ParametersId();
            paramId.setParaName(Parameters.FP_VERIFY_MATCH_SCORE);
            paramId.setParaScope(Parameters.SCOPE_SYSTEM);

            Parameters param = parametersService.findById(paramId);
            if (param != null) {
                fpVerifyMatchScore = param.getParaShortValue();
            }

            List<Integer> fpEncodesArr = new ArrayList<Integer>();
            for (String fpEndVal : fpEncodes) {
            	Pattern pattern = Pattern.compile("\\d*"); 
                Matcher matcher = pattern.matcher(fpEndVal); 
            	if(!StringUtils.isEmpty(fpEndVal) && matcher.matches()){
            		fpEncodesArr.add(Integer.parseInt(fpEndVal));            		
            	}
            }
            Map<Integer, FingerprintInfo> fpMap = new HashMap<Integer, FingerprintInfo>();

            if (userSession.getFunctions().contains("VIEW_FP")) {

                List<NicTransactionAttachment> fps = nicTransactionDocumentService.findNicTransactionAttachments(transactionId, NicTransactionAttachment.DOC_TYPE_FINGERPRINT, null).getListModel();
                if (CollectionUtils.isNotEmpty(fps)) {
                    for (NicTransactionAttachment fp : fps) {
                        Integer fpPos = Integer.parseInt(fp.getSerialNo());

                        FingerprintInfo info = new FingerprintInfo();
                        info.setFpImage(Base64.encodeBase64String(fp.getDocData()));
                        info.setFpQuaScr(Integer.parseInt(fpQualitys[fpPos - 1].split("-")[1]));
                        //[19Feb2016][Tue] - Add
                        //info.setFpVerifyScr(Integer.parseInt(fpVerifyScores[fpPos - 1].split("-")[1]));
                        info.setFpIndicator(fpIndicators[fpPos - 1].split("-")[1]);
                        info.setGoodFPQuaScr(Integer.parseInt(goodFpQuas[fpPos - 1].split("-")[1]));
                        info.setAccetableFPQuaScr(Integer.parseInt(accFpQuas[fpPos - 1].split("-")[1]));
                        info.setBaseImageConvert(convertImageFormatWsqToJpg1(fp.getDocData()));	
                        if (fpEncodesArr.contains(fpPos)) {
                            info.setEncodeFlag(true);
                        }
                        fpMap.put(fpPos, info);
                    }
                }

                for (String fpIndi : fpIndicators) {
                    Integer fpPos = Integer.parseInt(fpIndi.split("-")[0]);
                    if (!fpMap.containsKey(fpPos)) {

                        FingerprintInfo info = new FingerprintInfo();
                        info.setFpImage(null);
                        info.setFpQuaScr(Integer.parseInt(fpQualitys[fpPos - 1].split("-")[1]));
                        //[19Feb2016][Tue] - Add
                        //info.setFpVerifyScr(Integer.parseInt(fpVerifyScores[fpPos - 1].split("-")[1]));
                        info.setFpIndicator(fpIndicators[fpPos - 1].split("-")[1]);
                        info.setGoodFPQuaScr(Integer.parseInt(goodFpQuas[fpPos - 1].split("-")[1]));
                        info.setAccetableFPQuaScr(Integer.parseInt(accFpQuas[fpPos - 1].split("-")[1]));

                        if (fpEncodesArr.contains(fpPos)) {
                            info.setEncodeFlag(true);
                        }
                        fpMap.put(fpPos, info);
                    }
                }

            } else {

                viewFPFalg = "N";

                List<NicTransactionAttachment> fps = nicTransactionDocumentService.findNicTransactionAttachments(transactionId, NicTransactionAttachment.DOC_TYPE_FINGERPRINT, null).getListModel();
                if (CollectionUtils.isNotEmpty(fps)) {
                    for (NicTransactionAttachment fp : fps) {
                        Integer fpPos = Integer.parseInt(fp.getSerialNo());

                        FingerprintInfo info = new FingerprintInfo();
                        info.setFpImage(Base64.encodeBase64String(fp.getDocData()));
                        info.setFpQuaScr(Integer.parseInt(fpQualitys[fpPos - 1].split("-")[1]));
                        //[19Feb2016][Tue] - Add
                        //info.setFpVerifyScr(Integer.parseInt(fpVerifyScores[fpPos - 1].split("-")[1]));
                        info.setFpIndicator(fpIndicators[fpPos - 1].split("-")[1]);
                        info.setBaseImageConvert(convertImageFormatWsqToJpg1(fp.getDocData()));	
                        if (fpEncodesArr.contains(fpPos)) {
                            info.setEncodeFlag(true);
                        }
                        fpMap.put(fpPos, info);
                    }
                }

                for (String fpIndi : fpIndicators) {
                    Integer fpPos = Integer.parseInt(fpIndi.split("-")[0]);
                    if (!fpMap.containsKey(fpPos)) {
                        FingerprintInfo info = new FingerprintInfo();
                        info.setFpImage(null);
                        info.setFpQuaScr(Integer.parseInt(fpQualitys[fpPos - 1].split("-")[1]));
                        //[19Feb2016][Tue] - Add
                        //info.setFpVerifyScr(Integer.parseInt(fpVerifyScores[fpPos - 1].split("-")[1]));
                        info.setFpIndicator(fpIndicators[fpPos - 1].split("-")[1]);

                        if (fpEncodesArr.contains(fpPos)) {
                            info.setEncodeFlag(true);
                        }
                        fpMap.put(fpPos, info);
                    }
                }
            }

            ModelAndView mav = new ModelAndView("fp-info");
            for(Map.Entry<Integer, FingerprintInfo> finger : fpMap.entrySet()){
            	if(StringUtils.isEmpty(finger.getValue().getBaseImageConvert())){
            		finger.getValue().setBaseImageConvert("N");
            	}
            }
            mav.addObject("fpMap", fpMap);
            //[19Feb2016][Tue] - Add
            //mav.addObject("fpVerifyMatchScore", Integer.parseInt(fpVerifyMatchScore));
            mav.addObject("fpVerifyMatchScore", 1);
            mav.addObject("viewFPFalg", viewFPFalg);

            return mav;
        } catch (Exception ex) {
            ModelAndView modelAndView = new ModelAndView();
            modelAndView.setViewName("errorWithoutMenu");
            modelAndView.addObject("exception", ex);
            modelAndView.addObject("message", ex.getLocalizedMessage());
            modelAndView.addObject("messageStack", ex.getMessage());
            return modelAndView;
        }
    }

    @ResponseBody
    @RequestMapping(value = "/getIssuanceData", method = RequestMethod.GET)
    @ExceptionHandler
    public ModelAndView getIssuanceData(HttpServletRequest request, ModelMap model) throws Exception {
        DataExchangeDTO convertedIssuanceData = null;
        List<FPEnquiryInfo> fpEnquiryInfoList = new ArrayList<FPEnquiryInfo>();
        String viewFPFalg = "Y";
        try {
            UserSession userSession = (UserSession) request.getSession().getAttribute("userSession");
            if (!userSession.getFunctions().contains("VIEW_FP")) {
                viewFPFalg = "N";
            }
            String logId = request.getParameter("logId");

            NicTransactionLog nicTransactionLog = transactionLogService.findById(new Long(logId));
            if (nicTransactionLog.getLogData() != null) {
                convertedIssuanceData = (DataExchangeDTO) issuanceUtil.unmarshal(nicTransactionLog.getLogData());

                if (convertedIssuanceData != null) {
                    if (convertedIssuanceData.getIssuanceDetails() != null) {
                        if (convertedIssuanceData.getIssuanceDetails().getFpVerifications() != null) {
                            List<FpVerificationDTO> fpDataList = convertedIssuanceData.getIssuanceDetails().getFpVerifications().getFpVerificationList();
                            if (CollectionUtils.isNotEmpty(fpDataList)) {
                                for (FpVerificationDTO fpVerificationDTO : fpDataList) {
                                    FPEnquiryInfo fpEnquiryInfo = new FPEnquiryInfo();
                                    if (fpVerificationDTO.getFingerprintData() != null) {
                                        fpEnquiryInfo.setFpDataStr(Base64.encodeBase64String(fpVerificationDTO.getFingerprintData()));
                                    }
                                    fpEnquiryInfo.setQualityScore(fpVerificationDTO.getFpQuality());
                                    fpEnquiryInfo.setFpPosition(fpVerificationDTO.getFingerprintPosition());
                                    fpEnquiryInfo.setFpSource(fpVerificationDTO.getFpSource());
                                    fpEnquiryInfo.setFpVerifyFlag(fpVerificationDTO.getFpVerifyFlag());
                                    fpEnquiryInfo.setMatchScore(fpVerificationDTO.getMatchScore());

                                    fpEnquiryInfoList.add(fpEnquiryInfo);
                                }
                            }
                        }
                    }
                }
            }

            ModelAndView mav = new ModelAndView("issuance-details");
            mav.addObject("fpEnquiryInfoList", fpEnquiryInfoList);
            mav.addObject("convertedIssuanceData", convertedIssuanceData);
            mav.addObject("fpSize", fpEnquiryInfoList.size());
            mav.addObject("viewFPFalg", viewFPFalg);

            return mav;
        } catch (Exception ex) {
            ModelAndView modelAndView = new ModelAndView();
            modelAndView.setViewName("errorWithoutMenu");
            modelAndView.addObject("exception", ex);
            modelAndView.addObject("message", ex.getLocalizedMessage());
            modelAndView.addObject("messageStack", ex.getMessage());
            return modelAndView;
        }
    }

    @ResponseBody
    @RequestMapping(value = "/retry/{jobId}", method = RequestMethod.GET)
    @ExceptionHandler
    public String retry(HttpServletRequest request, @PathVariable String jobId, ModelMap model) throws Exception {
        try {
            logger.info("Retrying the job with Job Id:" + jobId);
            HttpSession session = request.getSession();
            UserSession userSession = (UserSession) session.getAttribute("userSession");
            if (StringUtils.isNotBlank(jobId)) {
                uploadJobService.retryTransaction(new Long(jobId), userSession.getUserId(), userSession.getWorkstationId());
                return "success";
            } else {
                logger.info("Job Id is null");
                return "fail";
            }

        } catch (Exception ex) {
            logger.error("Error occurred while Retrying the Job. Exception" + ex.getMessage());

            return "fail";
        }
    }
    public NicTransactionInfo getTransactionInfo(NicTransaction nicTransaction) throws Exception {
		NicTransactionInfo nicTransactionInfo = null;
		try {
				nicTransactionInfo = new NicTransactionInfo();

				PropertyUtils.copyProperties(nicTransactionInfo, nicTransaction);

				if (nicTransaction.getDateOfApplication() != null) {
					nicTransactionInfo.setDatTimeOfApplication(DateUtil.parseDate(nicTransaction.getDateOfApplication(), "dd/MM/yyyy hh:mm:ss"));
				}

				if (nicTransaction.getEstDateOfCollection() != null) {
					nicTransactionInfo.setEstCollectionDate(DateUtil.parseDate(nicTransaction.getEstDateOfCollection(), "dd/MM/yyyy"));
				}

				if (nicTransaction.getCreateDatetime() != null) {
					nicTransactionInfo.setCreateDateTime(DateUtil.parseDate(nicTransaction.getCreateDatetime(), "dd/MM/yyyy hh:mm:ss"));
				}

				if (nicTransaction.getUpdateDatetime() != null) {
					nicTransactionInfo.setUpdateDateTime(DateUtil.parseDate(nicTransaction.getUpdateDatetime(), "dd/MM/yyyy hh:mm:ss"));
				}

				if (nicTransaction.getPrevDateOfIssue() != null) {
					nicTransactionInfo.setPrevIssueDate(DateUtil.parseDate(nicTransaction.getPrevDateOfIssue(), "dd/MM/yyyy"));
				}

				String regSiteCode = null;
				if (StringUtils.isNotBlank(nicTransactionInfo.getRegSiteCode())) {
					SiteRepository regSite = siteService.getSiteRepoById(nicTransactionInfo.getRegSiteCode());
					if (regSite != null) {
						regSiteCode = regSite.getSiteName();
					} else {
						regSiteCode = nicTransactionInfo.getRegSiteCode();
					}
				}

				nicTransactionInfo.setRegSiteCode(regSiteCode);

				String issSiteCode = null;
				if (StringUtils.isNotBlank(nicTransactionInfo.getIssSiteCode())) {
					SiteRepository regSite = siteService.getSiteRepoById(nicTransactionInfo.getIssSiteCode());
					if (regSite != null) {
						issSiteCode = regSite.getSiteName();
					} else {
						issSiteCode = nicTransactionInfo.getRegSiteCode();
					}
				}

				nicTransactionInfo.setIssSiteCode(issSiteCode);

				String transactionStatus = null;
				if (nicTransactionInfo != null && StringUtils.isNotBlank(nicTransactionInfo.getTransactionStatus())) {
					CodeValues codeValue = codesService.getCodeValueByIdName("TRANSACTION_STATUS", nicTransactionInfo.getTransactionStatus());

					if (codeValue != null) {
						transactionStatus = codeValue.getCodeValueDesc();
					} else {
						transactionStatus = nicTransactionInfo.getTransactionStatus();
					}
				}

				nicTransactionInfo.setTransactionStatus(transactionStatus);

				String transactionSubType = null;
				if (nicTransactionInfo != null && StringUtils.isNotBlank(nicTransactionInfo.getTransactionSubtype())) {
					CodeValues codeValue = codesService.getCodeValueByIdName("TRANSACTION_SUBTYPE", nicTransactionInfo.getTransactionSubtype());

					if (codeValue != null) {
						transactionSubType = codeValue.getCodeValueDesc();
					} else {
						transactionSubType = nicTransactionInfo.getTransactionSubtype();
					}
				}

				nicTransactionInfo.setTransactionSubtype(transactionSubType);

				String transactionType = null;
				if (nicTransactionInfo != null && StringUtils.isNotBlank(nicTransactionInfo.getTransactionType())) {
					CodeValues codeValue = codesService.getCodeValueByIdName("TRANSACTION_TYPE", nicTransactionInfo.getTransactionType());

					transactionType = nicTransactionInfo.getTransactionType();
					if (codeValue != null) {
						transactionType = codeValue.getCodeValueDesc();
					} else {
						codeValue = codesService.getCodeValueByIdName("JOB_TYPE", nicTransactionInfo.getTransactionType());
						if (codeValue != null) {
							transactionType = codeValue.getCodeValueDesc();
						}
					}
				}

				nicTransactionInfo.setTransactionType(transactionType);

				String passportType = null;
				if (nicTransactionInfo != null && StringUtils.isNotBlank(nicTransactionInfo.getPassportType())) {
					CodeValues codeValue = codesService.getCodeValueByIdName("PASSPORT_TYPE", nicTransactionInfo.getPassportType());

					if (codeValue != null) {
						passportType = codeValue.getCodeValueDesc();
					} else {
						passportType = nicTransactionInfo.getPassportType();
					}
				}

				nicTransactionInfo.setPassportType(passportType);

				// Get name
				NicRegistrationData registrationData = nicTransaction.getNicRegistrationData();
				if (registrationData != null) {
					if (StringUtils.isNotBlank(registrationData.getSurnameFull())) {
						nicTransactionInfo.setLastName(registrationData.getSurnameFull());
					}
					
					if (StringUtils.isNotBlank(registrationData.getFirstnameFull())) {
						nicTransactionInfo.setFirstName(registrationData.getFirstnameFull());
					}
					
					if (StringUtils.isNotBlank(registrationData.getMiddlenameFull())) {
						nicTransactionInfo.setMiddleName(registrationData.getMiddlenameFull());
					}
				}
		} catch (Exception ex) {
			logger.info("Error occurred while getting the job enquiry transaction info for Transaction:" + nicTransaction.getTransactionId() + ", Reason: " + ex.getMessage());
		}

		return nicTransactionInfo;
	}
}
