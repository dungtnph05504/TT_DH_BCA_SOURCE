package com.nec.asia.nic.comp.trans.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.StringTokenizer;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.hibernate.criterion.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import bsh.StringUtil;

import com.lowagie.text.pdf.AsianFontMapper;
import com.nec.asia.nic.comp.job.dto.HitCandidateDTO;
import com.nec.asia.nic.comp.job.dto.LogInfoDTO;
import com.nec.asia.nic.comp.job.dto.SearchHitDto;
import com.nec.asia.nic.comp.job.utils.NicCommandUtil;
import com.nec.asia.nic.comp.listHandover.dao.ListHandoverDao;
import com.nec.asia.nic.comp.listHandover.domain.NicListHandover;
import com.nec.asia.nic.comp.listHandover.domain.NicListHandoverId;
//import com.nec.asia.nic.comp.trans.dao.NicJobAfisCheckResultDao;
//import com.nec.asia.nic.comp.trans.dao.NicJobCpdCheckResultDao;
import com.nec.asia.nic.comp.trans.dao.NicRegistrationDataDao;
import com.nec.asia.nic.comp.trans.dao.NicRejectionDataDao;
import com.nec.asia.nic.comp.trans.dao.NicSearchHitResultDao;
import com.nec.asia.nic.comp.trans.dao.NicSearchResultDao;
import com.nec.asia.nic.comp.trans.dao.NicTransactionAttachmentDao;
import com.nec.asia.nic.comp.trans.dao.NicTransactionDao;
import com.nec.asia.nic.comp.trans.dao.NicTransactionLogDao;
import com.nec.asia.nic.comp.trans.dao.NicTransactionPackageDao;
import com.nec.asia.nic.comp.trans.dao.NicUploadJobDao;
import com.nec.asia.nic.comp.trans.domain.BaseModelList;
import com.nec.asia.nic.comp.trans.domain.BaseModelSingle;
import com.nec.asia.nic.comp.trans.domain.EppListHandoverDetail;
import com.nec.asia.nic.comp.trans.domain.NicDocumentData;
import com.nec.asia.nic.comp.trans.domain.NicJobCpdCheckResult;
import com.nec.asia.nic.comp.trans.domain.NicRegistrationData;
import com.nec.asia.nic.comp.trans.domain.NicRejectionData;
import com.nec.asia.nic.comp.trans.domain.NicSearchHitResult;
import com.nec.asia.nic.comp.trans.domain.NicSearchResult;
import com.nec.asia.nic.comp.trans.domain.NicTransaction;
import com.nec.asia.nic.comp.trans.domain.NicTransactionAttachment;
import com.nec.asia.nic.comp.trans.domain.NicTransactionLog;
import com.nec.asia.nic.comp.trans.domain.NicTransactionPackage;
import com.nec.asia.nic.comp.trans.domain.NicUploadJob;
import com.nec.asia.nic.comp.trans.domain.type.TransactionStatus;
import com.nec.asia.nic.comp.trans.dto.AssignmentFilter;
import com.nec.asia.nic.comp.trans.dto.AssignmentFilterAll;
import com.nec.asia.nic.comp.trans.dto.InvestigationHitData;
import com.nec.asia.nic.comp.trans.dto.NicEnquiryForm;
import com.nec.asia.nic.comp.trans.dto.NicHitTransaction;
import com.nec.asia.nic.comp.trans.dto.NicUploadJobDto;
import com.nec.asia.nic.comp.trans.dto.Passport;
import com.nec.asia.nic.comp.trans.exception.JobNoLongerAssignedToOfficerException;
import com.nec.asia.nic.comp.trans.service.DocumentDataService;
import com.nec.asia.nic.comp.trans.service.EppListHandoverDetailService;
import com.nec.asia.nic.comp.trans.service.NicSearchHitResultHitScorer;
import com.nec.asia.nic.comp.trans.service.NicTransactionService;
import com.nec.asia.nic.comp.trans.service.NicUploadJobService;
import com.nec.asia.nic.comp.trans.service.exception.NicUploadJobServiceException;
import com.nec.asia.nic.comp.trans.service.exception.TransactionServiceException;
import com.nec.asia.nic.comp.trans.utils.TransDTOMapper;
import com.nec.asia.nic.comp.ws.log.domain.CreateMessageException;
import com.nec.asia.nic.dx.trans.RejectionData;
import com.nec.asia.nic.framework.PageRequest;
import com.nec.asia.nic.framework.PaginatedResult;
import com.nec.asia.nic.framework.admin.code.dao.CodesDao;
import com.nec.asia.nic.framework.admin.code.dao.ParametersDao;
import com.nec.asia.nic.framework.admin.code.domain.CodeValues;
import com.nec.asia.nic.framework.admin.code.domain.Codes;
import com.nec.asia.nic.framework.admin.code.domain.Parameters;
import com.nec.asia.nic.framework.admin.code.domain.ParametersId;
import com.nec.asia.nic.framework.admin.code.service.CodesService;
import com.nec.asia.nic.framework.admin.rbac.dao.UsersDao;
import com.nec.asia.nic.framework.admin.rbac.domain.Roles;
import com.nec.asia.nic.framework.admin.rbac.domain.Users;
import com.nec.asia.nic.framework.admin.rbac.service.UserService;
import com.nec.asia.nic.framework.dao.DaoException;
import com.nec.asia.nic.framework.exception.JaxbXmlConvertorException;
import com.nec.asia.nic.framework.service.impl.DefaultBusinessServiceImpl;
import com.nec.asia.nic.util.TransLogInfoXmlConvertor;
import com.nec.asia.nic.utils.DateUtil;
import com.nec.asia.nic.utils.HelperClass;

/**
 * @author sailaja_chinapothula
 * 
 */
/*
 * Modification History:
 * 
 * 27 Sept 2013 (Peddi Swapna): Added getPendingInvestigationsCount method to get the pending investigations count.
 * 
 * 30 Sept 2013 (Sailaja): Added editRemarksData method to get edit the remarks.
 * 
 * 01 Oct 2013 (Sailaja) : Added findRejectedJobsForSupervisor method to get the rejected job list.
 * 
 * 01 Oct 2013 (Sailaja) : Added getUserRole method to get the role for the supervisor login.
 * 
 * 04 Oct 2013 (Sailaja) : Added assignNewRejectesdJobList to get the new assigned rejected jobs list.
 * 
 * 08 Oct 2013 (Sailaja) : Modified updateDataOnTrueHit, updateDataOnFalseHit to update the remarks upon true/false hit.
 * 
 * 09 Oct 2013 (Sailaja) : Modified sortAfisHitCandidateListByJobId method, added remarks to display in the hit records.
 * 
 * 23 Oct 2013 (chris) : Modified updateRejectionDataDownloadedStatus add parameters siteCode
 * 
 * 12 Nov 2013 (chris) : Added updateJobToResetIncompletedInvestigation, add siteCode in transaction Log
 * 
 * 26 Nov 2013 (Sailaja) : Added cancelTransaction method to cancel the transaction in investigation
 * 
 * 06 Dec 2013 (Sailaja) : Added findAllLogInfo method to display remarks in perso tab
 * 
 * 10 Dec 2013 (Sailaja) : Added editPersoRemarksData method to show Additional Perso Information 
 
 * 20 Dec 2013 (JP) : added setting of update details for update job table
 * 
 * 07 Jan 2013 (chris) : add transactional annotation for updateJobToResetIncompletedInvestigation().
 * 
 * 08 Jan 2014 (Sailaja) : Added updatePersoRegisterStatusOnReprint method for reprint
 * 
 * 15 Apr 2014 (Peddi Swapna) : Added getWorkflowJobId method to get the job id.
 * 
 * 03 Jan 2014 (jp) : added getJobsToReprocess 
 * 
 * 08 May 2014 (Peddi Swapna) : Allowing the user to see the error transactions based on the search criteria.
 * 
 * 22 May 2014 (jp) : added queTxnForReprocess to reset the Job End date of transaction by 2 days to queue it for reprocess job running after midnight
 * 
 * 21 May 2014 : (Peddi Swapna): Added getLatestErrorMessage method.
 * 
 * 11 Jun 2014 : (jp) :  added retryTransaction(long jobId, String officerId, String workStationId) for retry transaction
 * 
 * 20 Aug 2014 (chris): change "TRANSACTION_TYPE" to "JOB_TYPE" for CODE_ID.
 * 
 * 29 Feb 2016 (tue) : Added findAllForPagination.
 *
 * 01 Apr 2016 (chris) : add parameter to config interval, rety count.
 * 
 * 27 Apr 2016 (chris) : add code table FCM_REQUIRED_SUB_ITEM and route to FCM when required
 * 
 * 30 Jun 2017 (chris) : transaction enquiry, to remove self transaction id in the hit list.
 */
@Service("uploadJobService")
public class NicUploadJobServiceImpl extends
		DefaultBusinessServiceImpl<NicUploadJob, Long, NicUploadJobDao>
		implements NicUploadJobService {
	
	@Autowired
	private TransDTOMapper mapper = null;
	@Autowired
	private NicTransactionDao transactionDao = null;
	@Autowired
	private NicRegistrationDataDao registrationDataDao = null;
	@Autowired
	private NicTransactionAttachmentDao transactionDocumentDao = null;
	//@Autowired
	//private NicJobCpdCheckResultDao nicJobCpdCheckResultDao = null;
	//@Autowired
	//private NicJobAfisCheckResultDao nicJobAfisCheckResultDao = null;

	@Autowired
	private NicSearchResultDao searchResultDao = null;
	
	@Autowired
	private NicSearchHitResultDao searchHitResultDao = null;

	@Autowired
	private NicRejectionDataDao nicRejectionDataDao = null;
	
	@Autowired
	private NicUploadJobDao uploadJobDao = null;
	
	@Autowired
	 private UsersDao usersDao = null;
	
	@Autowired
	private NicTransactionLogDao transactionLogDao = null;
	
	@Autowired
	private ParametersDao parametersDao;
	
	@Autowired
	NicCommandUtil nicCommandUtil = null;

	@Autowired
	private CodesService codesService;

	public static TransLogInfoXmlConvertor logUtil = new TransLogInfoXmlConvertor();

	@Autowired
	private NicTransactionService nicTransactionService;

	@Autowired
	private DocumentDataService documentDataService;
	
	
	@Autowired
	private NicTransactionPackageDao tranPackageDao;
	
	@Autowired
	private ListHandoverDao handoverDao;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private EppListHandoverDetailService eppListHandoverDetailService;
	
	/** must define the non-argument constructor because we use CGLib proxy */
	public NicUploadJobServiceImpl() {
	}

	@Autowired
	public NicUploadJobServiceImpl(NicUploadJobDao dao) {
		this.dao = dao;
	}

	@Autowired
	private CodesDao codesDao;
	
	@Autowired
	private CodesService codesSevice;

	@Override
	public PaginatedResult<NicUploadJobDto> findAllForInvestigationPagination(
			String officerId, int pageNo, int pageSize)
			throws NicUploadJobServiceException {
		return this.findAllForInvestigationPagination(
				  officerId,   pageNo,   pageSize, null);
	}

	@Override
	public PaginatedResult<NicUploadJobDto> findAllForInvestigationPagination(
			String officerId, int pageNo, int pageSize, Boolean assignedOnly)
			throws NicUploadJobServiceException { 
		return this.findAllForInvestigationPagination(
				  officerId,   pageNo,   pageSize, assignedOnly, null);
	}
	
	@Override
	public PaginatedResult<NicUploadJobDto> findAllForInvestigationPagination(
			String officerId, int pageNo, int pageSize, Boolean assignedOnly, AssignmentFilter assignmentFilter)
			throws NicUploadJobServiceException { 
		List<NicUploadJobDto> joblstDto = new ArrayList<NicUploadJobDto>();
		PaginatedResult<NicUploadJob> pr = null;
		PaginatedResult<NicUploadJobDto> pageResult = new PaginatedResult<NicUploadJobDto>();
		try {
			pr = dao.findInvestigationJobForPaginationByOfficerId(officerId,
					pageNo, pageSize,   assignedOnly, assignmentFilter);
			if (pr.getRows() != null) {
				List<NicUploadJob> jobList = pr.getRows();
				for (NicUploadJob record : jobList) {
					//logger.info("Upload Job Id=============>>>>>>>>>>>>>>>>>>>" + record.getWorkflowJobId());
					NicUploadJobDto dto = new NicUploadJobDto();
					dto.setUploadJobId(record.getWorkflowJobId());
					dto.setTransactionId(record.getTransactionId());
					
					CodeValues codeValue = codesSevice.getCodeValueByIdName("JOB_TYPE", record.getJobType());
					if(codeValue!=null){
						dto.setJobType(codeValue.getCodeValueDesc());
					}else{
						dto.setJobType(record.getJobType());
					}
					
					dto.setInvestigationType(record.getInvestigationType());
					dto.setJobCurrentState(record.getJobCurrentStage());
					dto.setInvestigationStatus(record.getInvestigationStatus());
					dto.setInvestigationOfficerId(record.getInvestigationOfficerId());
					dto.setJobUploadTime(record.getJobCreatedTime());
					
					joblstDto.add(dto);
				}
				pageResult.setRows(joblstDto);
				pageResult.setTotal((int) pr.getTotal());
				pageResult.setPage(pr.getPage());
			}
		} catch (Exception ex) {
			logger.error("Error occurred while getting the investigation job list. Exception: "
					+ ex.getMessage());
			throw new NicUploadJobServiceException(ex);
		}
		return pageResult;
	}

	//TRUNG EDIT
	@Override
	public PaginatedResult<NicUploadJobDto> findAllForAssignmentJobPagination(
			String officerId, int pageNo, int pageSize)
			throws NicUploadJobServiceException {
		return this.findAllForAssignmentJobPagination(
				  officerId,   pageNo,   pageSize, null);
	}

	@Override
	public PaginatedResult<NicUploadJobDto> findAllForAssignmentJobPagination(
			String officerId, int pageNo, int pageSize, Boolean assignedOnly)
			throws NicUploadJobServiceException { 
		return this.findAllForAssignmentJobPagination(
				  officerId,   pageNo,   pageSize, assignedOnly, null);
	}
	
	@Override
	public PaginatedResult<NicUploadJobDto> findAllForAssignmentJobPagination(
			String officerId, int pageNo, int pageSize, Boolean assignedOnly, AssignmentFilter assignmentFilter)
			throws NicUploadJobServiceException { 
		List<NicUploadJobDto> joblstDto = new ArrayList<NicUploadJobDto>();
		PaginatedResult<NicUploadJob> pr = null;
		PaginatedResult<NicUploadJobDto> pageResult = new PaginatedResult<NicUploadJobDto>();
		try {
			pr = dao.findAssignmentForPaginationByOfficerId(officerId,
					pageNo, pageSize,   assignedOnly, assignmentFilter);
			if (pr.getRows() != null) {
				List<NicUploadJob> jobList = pr.getRows();
				for (NicUploadJob record : jobList) {
					//logger.info("Upload Job Id=============>>>>>>>>>>>>>>>>>>>" + record.getWorkflowJobId());
					NicUploadJobDto dto = new NicUploadJobDto();
					dto.setUploadJobId(record.getWorkflowJobId());
					dto.setTransactionId(record.getTransactionId());
					
					CodeValues codeValue = codesSevice.getCodeValueByIdName("JOB_TYPE", record.getJobType());
					if(codeValue!=null){
						dto.setJobType(codeValue.getCodeValueDesc());
					}else{
						dto.setJobType(record.getJobType());
					}
					
					dto.setInvestigationType(record.getInvestigationType());
					dto.setJobCurrentState(record.getJobCurrentStage());
					dto.setInvestigationStatus(record.getInvestigationStatus());
					dto.setInvestigationOfficerId(record.getInvestigationOfficerId());
					dto.setJobUploadTime(record.getJobCreatedTime());
					
					joblstDto.add(dto);
				}
				pageResult.setRows(joblstDto);
				pageResult.setTotal((int) pr.getTotal());
				pageResult.setPage(pr.getPage());
			}
		} catch (Exception ex) {
			logger.error("Error occurred while getting the investigation job list. Exception: "
					+ ex.getMessage());
			throw new NicUploadJobServiceException(ex);
		}
		return pageResult;
	}
	
	@Override
	public PaginatedResult<NicUploadJobDto> findAllForAssignmentJobPagination(String[] recordStatuses, String officerId,
			int pageNo, int pageSize) throws NicUploadJobServiceException {
		return this.findAllForAssignmentJobPagination( recordStatuses,   officerId,
				  pageNo,   pageSize,null);
	}

	@Override
	public PaginatedResult<NicUploadJobDto> findAllForAssignmentJobPagination(String[] recordStatuses, String officerId,
			int pageNo, int pageSize, Boolean assignedOnly) throws NicUploadJobServiceException {
		return this.findAllForAssignmentJobPagination( recordStatuses,   officerId,
				  pageNo,   pageSize, assignedOnly, null); 
	}

	@Override
	public PaginatedResult<NicUploadJobDto> findAllForAssignmentJobPagination(String[] recordStatuses, String officerId,
			int pageNo, int pageSize, Boolean assignedOnly, AssignmentFilter assignmentFilter) throws NicUploadJobServiceException {
		List<NicUploadJobDto> joblstDto = new ArrayList<NicUploadJobDto>(); 
		PaginatedResult<NicUploadJob> pr = null;
		PaginatedResult<NicUploadJobDto> pageResult = new PaginatedResult<NicUploadJobDto>();
		try {
			// NicUploadJob nicUploadJob = new NicUploadJob();
			// nicUploadJob.setInvestigationOfficerId(userId);
			pr = dao.findAssignmentForPaginationByOfficerId(recordStatuses, officerId, pageNo, pageSize,   assignedOnly, assignmentFilter);
			// dao.findAllForPagination(nicUploadJob, pageNo,
			// pageSize);//dao.findAllInvestigationForPagination(nicUploadJob,
			// pageNo, pageSize);
			if (pr.getRows() != null) {
				List<NicUploadJob> jobList = pr.getRows();
				for (NicUploadJob record : jobList) {
					logger.info("Upload Job Id=============>>>>>>>>>>>>>>>>>>>" + record.getWorkflowJobId());
					NicUploadJobDto dto = new NicUploadJobDto();
					dto.setUploadJobId(record.getWorkflowJobId());
					dto.setTransactionId(record.getTransactionId());

					// CodeValues codeValue =
					// codesService.getCodeValueByIdName("TRANSACTION_TYPE",
					// record.getJobType());
					CodeValues codeValue = codesSevice.getCodeValueByIdName("JOB_TYPE", record.getJobType()); // 2014
																														// Aug
																														// 20

					if (codeValue != null) {
						dto.setJobType(codeValue.getCodeValueDesc());
					} else {
						dto.setJobType(record.getJobType());
					}

					dto.setInvestigationType(record.getInvestigationType());
					dto.setJobCurrentState(record.getJobCurrentStage());
					dto.setInvestigationStatus(record.getInvestigationStatus());
					dto.setInvestigationOfficerId(record.getInvestigationOfficerId());
					dto.setJobUploadTime(record.getJobCreatedTime());

					joblstDto.add(dto);
				}
				pageResult.setRows(joblstDto);
				pageResult.setTotal((int) pr.getTotal());
				pageResult.setPage(pr.getPage());
			}
		} catch (Exception ex) {
			logger.error("Error occurred while getting the investigation job list. Exception: " + ex.getMessage());
			throw new NicUploadJobServiceException(ex);
		}
		return pageResult;
	}
	//END
	
	@Override
	public PaginatedResult<NicUploadJobDto> findByTransactionId(String txnId, String owner)
			throws NicUploadJobServiceException {
		//PaginatedResult<NicUploadJob> result = null;
		//modified
		List<NicUploadJobDto> joblstDto = new ArrayList<NicUploadJobDto>();
		PaginatedResult<NicUploadJob> result = null;
		PaginatedResult<NicUploadJobDto> pageResult = new PaginatedResult<NicUploadJobDto>();
		new PaginatedResult<NicUploadJobDto>();
		try {
			result = dao.findByTransactionId(txnId, owner);
			//modified
			if (result.getRows() != null) {
				List<NicUploadJob> jobList = result.getRows();
				for (NicUploadJob record : jobList) {
					NicUploadJobDto dto = new NicUploadJobDto();
					dto.setUploadJobId(record.getWorkflowJobId());
					dto.setTransactionId(record.getTransactionId());
					
					//CodeValues codeValue = codesService.getCodeValueByIdName("TRANSACTION_TYPE", record.getJobType());
					CodeValues codeValue = codesSevice.getCodeValueByIdName("JOB_TYPE", record.getJobType()); //2014 Aug 20
					
					if(codeValue!=null){
						dto.setJobType(codeValue.getCodeValueDesc());
					}else{
						dto.setJobType(record.getJobType());
					}
					
					dto.setInvestigationType(record.getInvestigationType());
					dto.setJobCurrentState(record.getJobCurrentStage());
					dto.setInvestigationStatus(record.getInvestigationStatus());
					dto.setInvestigationOfficerId(record.getInvestigationOfficerId());
					//dto.setJobUploadTime(record.getJobUploadTime());
					
					joblstDto.add(dto);
				}
				pageResult.setRows(joblstDto);
				pageResult.setTotal((int) result.getTotal());
				pageResult.setPage(result.getPage());
				}
				
		} catch (Exception ex) {
			logger.error("Error occurred while getting the job search list. Exception: "
					+ ex.getMessage());
			throw new NicUploadJobServiceException(ex);
		}
		return pageResult;
	}
	
	@Override
	public PaginatedResult<NicUploadJobDto> findByTransactionIdApprove(String txnId, String owner)
			throws NicUploadJobServiceException {
		//PaginatedResult<NicUploadJob> result = null;
		//modified
		List<NicUploadJobDto> joblstDto = new ArrayList<NicUploadJobDto>();
		PaginatedResult<NicUploadJob> result = null;
		PaginatedResult<NicUploadJobDto> pageResult = new PaginatedResult<NicUploadJobDto>();
		new PaginatedResult<NicUploadJobDto>();
		try {
			result = dao.findByTransactionIdApprove(txnId, owner);
			//modified
			if (result.getRows() != null) {
				List<NicUploadJob> jobList = result.getRows();
				for (NicUploadJob record : jobList) {
					NicUploadJobDto dto = new NicUploadJobDto();
					dto.setUploadJobId(record.getWorkflowJobId());
					dto.setTransactionId(record.getTransactionId());
					
					//CodeValues codeValue = codesService.getCodeValueByIdName("TRANSACTION_TYPE", record.getJobType());
					CodeValues codeValue = codesSevice.getCodeValueByIdName("JOB_TYPE", record.getJobType()); //2014 Aug 20
					
					if(codeValue!=null){
						dto.setJobType(codeValue.getCodeValueDesc());
					}else{
						dto.setJobType(record.getJobType());
					}
					
					dto.setInvestigationType(record.getInvestigationType());
					dto.setJobCurrentState(record.getJobCurrentStage());
					dto.setInvestigationStatus(record.getInvestigationStatus());
					dto.setInvestigationOfficerId(record.getInvestigationOfficerId());
					//dto.setJobUploadTime(record.getJobUploadTime());
					
					joblstDto.add(dto);
				}
				pageResult.setRows(joblstDto);
				pageResult.setTotal((int) result.getTotal());
				pageResult.setPage(result.getPage());
				}
				
		} catch (Exception ex) {
			logger.error("Error occurred while getting the job search list. Exception: "
					+ ex.getMessage());
			throw new NicUploadJobServiceException(ex);
		}
		return pageResult;
	}
	
	@Override
	public PaginatedResult<NicUploadJob> findAllForPagination(
			PageRequest pageRequest, NicUploadJob nicUploadJob)
			throws NicUploadJobServiceException {
		try {
			int pageNo = pageRequest.getPageNo();
			int pageSize = pageRequest.getMaxRecords();
			boolean ignoreCase = false;
			boolean enableLike = false;
			Order order = null;
			if(StringUtils.equals(pageRequest.getSortorder(),"asc")){
				order = Order.asc(pageRequest.getSortname());
			} else {
				order = Order.desc(pageRequest.getSortname());
			}
			return dao.findAllForPagination(nicUploadJob, pageNo, pageSize, ignoreCase, enableLike, order);
		} catch (Exception e) {
			throw new NicUploadJobServiceException(e);
		}
		
	}		

	@Override
	@Transactional(rollbackFor = NicUploadJobServiceException.class, propagation = Propagation.REQUIRED)
	public void assignNewJobList(String userid, String officerUserId, String officerWorkstationId)
			throws NicUploadJobServiceException {
		try {
			NicUploadJob job = dao.assignNewJob(userid);
			this.logTransaction(job.getTransactionId(), NicTransactionLog.TRANSACTION_STAGE_INVESTIGATION_ASSIGN,
					this.codesService.getCodeValueDescByIdName(Codes.INVESTIGATION_STATUS, job.getInvestigationStatus(),
							job.getInvestigationStatus()),
					officerUserId, officerWorkstationId, NicTransactionLog.TRANSACTION_STAGE_INVESTIGATION_ASSIGN,
					job.getWorkflowJobId().toString());
		} catch (Exception ex) {
			logger.error("Error occurred while getting the updated officer id list. Exception: " + ex.getMessage(), ex);
			throw new NicUploadJobServiceException(ex);
		}
	}

	private void logTransaction(String referenceId, String stage, String status,String officerUserId, String officerWorkstationId, String reason, String remarks) {
		{
			NicTransactionLog nicTransactionLog = new NicTransactionLog();
			nicTransactionLog.setRefId(referenceId);
			nicTransactionLog.setTransactionStage(stage);
			nicTransactionLog.setTransactionStatus(status);
			nicTransactionLog.setOfficerId(officerUserId);
			nicTransactionLog.setWkstnId(officerWorkstationId);
			nicTransactionLog.setLogCreateTime(new Date());
			String remarksDataMarshal = null;
			{
				LogInfoDTO logInfoDto = new LogInfoDTO();
				logInfoDto.setReason(reason);
				logInfoDto.setRemark(remarks);
				try {
					remarksDataMarshal = logUtil.marshal(logInfoDto);
				} catch (JaxbXmlConvertorException e) {
					e.printStackTrace();
				}
			}
			nicTransactionLog.setLogInfo(remarksDataMarshal);
			this.transactionLogDao.save(nicTransactionLog);
		}
	}

	@Override
	@Transactional(rollbackFor= NicUploadJobServiceException.class ,propagation = Propagation.REQUIRED)
	public List<NicUploadJob> newInvestigationList(long obj)
			throws NicUploadJobServiceException {
		// List<NicUploadJobDto> joblstDto = new ArrayList<NicUploadJobDto>();
		List<NicUploadJob> result = null;
		try {
			result = dao.newInvestigation(obj);
		} catch (Exception ex) {
			logger.error("Error occurred while getting the updated officer id list. Exception: "+ ex.getMessage(), ex);
			throw new NicUploadJobServiceException(ex);
		}
		return result;
	}

	@Override
	@Transactional(rollbackFor= NicUploadJobServiceException.class ,propagation = Propagation.REQUIRED)
	public void approveJobId(long approveJobId, String userId, String wkstnId)
			throws NicUploadJobServiceException {
		try {
			dao.approveJobId(approveJobId, userId, wkstnId);
		} catch (Exception ex) {
			logger.error("Error occurred while approving the card. Exception: "	+ ex.getMessage(), ex);
			throw new NicUploadJobServiceException(ex);
		}

	}
	
	@Override
	@Transactional(rollbackFor= NicUploadJobServiceException.class ,propagation = Propagation.REQUIRED)
	public Boolean updateMatchingData(Long jobId, String tagetTransactionId, int type, String note, Long idBlacklist, String fullName)
			throws NicUploadJobServiceException {
		try {
			return dao.updateMatchingData(jobId, tagetTransactionId, type, note, idBlacklist, fullName);
		} catch (Exception ex) {
			logger.error("Error occurred while updateMatchingData. Exception: "	+ ex.getMessage(), ex);
			//throw new NicUploadJobServiceException(ex);
		}
		return false;
	}
	@Override
	@Transactional(rollbackFor= NicUploadJobServiceException.class ,propagation = Propagation.REQUIRED)
	public void rejectJobId(long rejectJobId, String userId, String wkstnId)
			throws NicUploadJobServiceException {
		try {
			dao.rejectJobId(rejectJobId, userId, wkstnId);
		} catch (Exception ex) {
			logger.error("Error occurred while rejecting the card. Exception: "+ ex.getMessage(), ex);
			throw new NicUploadJobServiceException(ex);
		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.nec.asia.nic.comp.trans.service.NicUploadJobService#
	 * findAllForInvestigationPagination(long, int, int)
	 */
	@Override
	public PaginatedResult<NicUploadJobDto> findAllForEnquiryPagination(
			String txnId, int pageNo, int pageSize)
			throws NicUploadJobServiceException {
		List<NicUploadJobDto> joblstDto = new ArrayList<NicUploadJobDto>();
		PaginatedResult<NicUploadJob> pr = null;
		PaginatedResult<NicUploadJobDto> pageResult = new PaginatedResult<NicUploadJobDto>();
		try {
			NicUploadJob nicUploadJob = new NicUploadJob();
			nicUploadJob.setTransactionId(txnId);
			logger.info("The transaction id is " + txnId);
			pr = dao.findAllForPagination(nicUploadJob, pageNo, pageSize);// dao.findAllInvestigationForPagination(nicUploadJob,
																			// pageNo,
																			// pageSize);
			if (pr.getRows() != null) {
				List<NicUploadJob> jobList = pr.getRows();
				for (NicUploadJob record : jobList) {
					logger.info("Upload Job Id=============>>>>>>>>>>>>>>>>>>>"
							+ record.getWorkflowJobId());
					NicUploadJobDto dto = new NicUploadJobDto();
					dto.setUploadJobId(record.getWorkflowJobId());
					dto.setTransactionId(record.getTransactionId());
					dto.setJobType(record.getJobType());
					dto.setInvestigationType(record.getInvestigationType());
					dto.setJobCurrentState(record.getJobCurrentStage());
					dto.setInvestigationStatus(record.getInvestigationStatus());
					dto.setInvestigationOfficerId(record
							.getInvestigationOfficerId());
					joblstDto.add(dto);
				}
				pageResult.setRows(joblstDto);
				pageResult.setTotal((int) pr.getTotal());
				pageResult.setPage(pr.getPage());
			}
		} catch (Exception ex) {
			logger.error("Error occurred while getting the investigation job list. Exception: "+ ex.getMessage(), ex);
			throw new NicUploadJobServiceException(ex);
		}
		return pageResult;
	}

	@Override
	public Map<String, String> getCodeValues(String codeName)
			throws NicUploadJobServiceException {
		Map<String, String> result = null;
		try {
			CodeValues codeValue = new CodeValues();
			new NicEnquiryForm();
			codeValue.getId();
			logger.info("Inside Service===>>>> Code Id "
					+ codeValue.getId());
			result = codesDao.getCodeValuesByCodeID(codeName);
		} catch (Exception ex) {
			logger.error("Error occurred while getting the job details list-- Start Investigation. Exception: "
					+ ex.getMessage());
			throw new NicUploadJobServiceException(ex);
		}
		return result;
	}

	@Override
	@Transactional(rollbackFor = java.lang.Throwable.class, propagation = Propagation.REQUIRED)
	public BaseModelSingle<NicUploadJob> updateJobState(long jobId, String state, int command)
			throws NicUploadJobServiceException {

		
		//NicCommandUtil nicCommandUtil = new NicCommandUtil();
		
		try {
			return this.dao.updateJobState(jobId, state, command);
		} catch (Exception e) {
			return new BaseModelSingle<NicUploadJob>(null, false, CreateMessageException.createMessageException(e) +  " - updateJobState - " + jobId + " - thất bại.");
		}

	}

	
	@Override
	@Transactional(rollbackFor = java.lang.Throwable.class, propagation = Propagation.REQUIRED)
	public BaseModelSingle<Void> updateJobStatus(long jobId, String status)
			throws NicUploadJobServiceException {
		try{
			return this.dao.updateJobStatus(jobId, status);
		}catch(Exception e){
			return new BaseModelSingle<Void>(null, false, CreateMessageException.createMessageException(e) + " - updateJobStatus - " + jobId + " - thất bại");
		}
	}

	@Override
	public BaseModelSingle<Boolean> saveOrUpdateService(NicUploadJob nicUp)
			throws NicUploadJobServiceException {
		try {
			this.dao.saveOrUpdateService(nicUp);
			return new BaseModelSingle<Boolean>(true, true, null);
		} catch (Exception e) {
//			logger.info("getTransactionJobsInQueue exception:"+e.getMessage());
			return new BaseModelSingle<Boolean>(false, false,  CreateMessageException.createMessageException(e) + " - saveOrUpdateService - NicUploadJob - thất bại." );
		}
	}
	
	@Override
	public List<NicUploadJob> getTransactionJobsInQueue()
			throws NicUploadJobServiceException {
		try {
			Map<String, String> configMap = this.getConfigMapFromParameters();
			return dao.getTransactionJobsInQueue(configMap);
		} catch (Exception e) {
			logger.info("getTransactionJobsInQueue exception:"+e.getMessage());
			throw new NicUploadJobServiceException(e);
		}
	}
	@Override
	public List<NicUploadJob> getJobsToReprocess()
			throws NicUploadJobServiceException {
		try {
			Map<String, String> configMap = this.getConfigMapFromParameters();
			return dao.getTxnWithMaxReprocessCount(configMap);
		} catch (Exception e) {
			throw new NicUploadJobServiceException(e);
		}
	}
	
	private Map<String, String> getConfigMapFromParameters() {
		Map<String, String> configMap = new HashMap<String, String>();
		List<Parameters> parameterList = parametersDao.findAllByScope("WORKFLOW");
		if (CollectionUtils.isNotEmpty(parameterList)) {
			for (Parameters parameter : parameterList) {
				if (parameter!=null && parameter.getId()!=null && parameter.getId().getParaName()!=null && parameter.getParaShortValue()!=null) {
					configMap.put(parameter.getId().getParaName(), parameter.getParaShortValue());
				}
			}
		}
		return configMap;
	}
	
	
//	@Override
//	@Transactional(rollbackFor = java.lang.Throwable.class, propagation = Propagation.REQUIRED)
//	public void reprocessPendingtransactions()
//			throws NicUploadJobServiceException {
//		
//		try {
//			
//			String updateBy = "SYSTEM_NIC_JOB";
//			String updateWkstnId = "SYSTEM_NIC_JOB";
//			
//				java.net.InetAddress localMachine = InetAddress.getLocalHost();
//				updateWkstnId = localMachine.getHostName();
//			
//			logger.info("inside reprocessPendingtransactions");
//			
//			List<NicUploadJob> listTxn =  dao.getTxnWithMaxReprocessCount();
//			
//			logger.info("number of transactions to be processed:"+listTxn.size());
//			
//			for (NicUploadJob uploadObj: listTxn){
//				
//				NicUploadJob trans = dao.findById(uploadObj.getWorkflowJobId());
//				trans.setJobReprocessCount(1);
//				trans.setUpdateBy(updateBy);
//				trans.setUpdateDate(new Date());
//				trans.setUpdateWkstnId(updateWkstnId);
//				
//				dao.saveOrUpdate(trans);
//				
//			}
//			
//			
//		} catch (Exception e) {
//			logger.info("Exception in reprocessPendingtransactions:"+e.getMessage());
//			e.printStackTrace();
//			throw new NicUploadJobServiceException(e);
//		}
//	}
	
	
	@Override
	@Transactional(rollbackFor = java.lang.Throwable.class, propagation = Propagation.REQUIRED)
	public NicUploadJob updateProcessNum(long uploadJobId) throws NicUploadJobServiceException {
		try {
			Map<String, String> configMap = this.getConfigMapFromParameters();
			
			int maxReprocessCount = 10; //last count for reprocess.
			String maxReprocessCountVal = configMap.get("MAX_REPROCESS_COUNT");
			if (StringUtils.isNumeric(maxReprocessCountVal)) {
				maxReprocessCount = Integer.parseInt(maxReprocessCountVal);
			}
			
			NicUploadJob nicObj = dao.findById(Long.valueOf(uploadJobId));
			logger.info("inside updateProcessNum");
			if(nicObj.getJobReprocessCount() == null){
				logger.info("update count is null. will be set to 0");
				nicObj.setJobReprocessCount(Integer.valueOf(0));
				nicObj.setUpdateDatetime(new Date());
				dao.saveOrUpdate(nicObj);
			}else if(nicObj.getJobReprocessCount() < maxReprocessCount){
				
				int appendedCount = nicObj.getJobReprocessCount()+1;
				logger.info("update count as count+1:"+appendedCount);
				nicObj.setJobReprocessCount(appendedCount);
				nicObj.setUpdateDatetime(new Date());
				dao.saveOrUpdate(nicObj);
			} else {
				logger.info("no updating needed. reached max number of retries");
			}

			return nicObj;
			
		} catch (Exception e) {
			throw new NicUploadJobServiceException(e);
		}
	}

	@Override
	@Transactional(rollbackFor = java.lang.Throwable.class, propagation = Propagation.REQUIRED)
	public void resetReprocessCount(long uploadJobId, long reprocessCount) throws NicUploadJobServiceException {
		try {
			
			
			NicUploadJob nicObj = dao.findById(Long.valueOf(uploadJobId));
			logger.info("inside resetReprocessCount");
			
			if(nicObj.getJobReprocessCount() !=null){
				nicObj.setJobReprocessCount((int) reprocessCount);
				dao.saveOrUpdate(nicObj);
			}else{
				logger.info("no need to reset, count is null");
			}
			
		} catch (Exception e) {
			logger.error("Exception in resetReprocessCount", e);
			throw new NicUploadJobServiceException(e);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.nec.asia.nic.comp.trans.service.NicUploadJobService#getJobList(java
	 * .lang.String, long, java.lang.String, java.lang.String, java.lang.String,
	 * java.lang.String, int, int)
	 */
	@Override
	public PaginatedResult<NicUploadJob> getJobList(String jobType, Long jobId,
			String jobState, String[] recordStatus, String txnId, String nin, String owner,
			int pageNo, int pageSize) throws NicUploadJobServiceException {
		PaginatedResult<NicUploadJob> result = null;
		try {
			result = dao.getJobList(jobType, jobId, jobState, recordStatus,
					txnId, nin, owner, pageNo, pageSize);
		} catch (Exception e) {
			throw new NicUploadJobServiceException(e);
		}
		return result;
	}

	public List<HitCandidateDTO> getAfisHitCandidateListByJobId(Long jobId)
			throws NicUploadJobServiceException {
		List<HitCandidateDTO> candidateList = null;
		try {
//			NicUploadJob nicUploadJob = this.findById(jobId);
//			if (nicUploadJob != null
//					&& nicUploadJob.getNicSearchResults() !=null) {
//				candidateList = new ArrayList<HitCandidateDTO>();
//				
//				for (NicSearchResult searchResult : nicUploadJob.getNicSearchResults()) {
			List<NicSearchResult> searchResultList = this.searchResultDao.findAllByJobId(jobId);
			if (searchResultList!=null) {
				candidateList = new ArrayList<HitCandidateDTO>();
				for (NicSearchResult searchResult : searchResultList) {
					if (searchResult != null && Boolean.TRUE.equals(searchResult.getHasHit())) {
						String currentTxnId = searchResult.getTransactionId();
						logger.info(" -----------------------------------JOB[{}], TXN[{}], RID[{}], Type of Search[{}].", new Object[] {jobId, currentTxnId, searchResult.getSearchResultId(), searchResult.getTypeSearch()});
						for (NicSearchHitResult hitResult : searchResult.getHitList()) {
							String transactionId = hitResult.getTransactionIdHit();
							//skip for display error or self hit records.
							if (StringUtils.equals(currentTxnId, transactionId))	continue;
							
							HitCandidateDTO dto = new HitCandidateDTO();
							dto.setNin(transactionId);
							dto.setTransactionId(transactionId);
							dto.setSearchResultId(searchResult.getSearchResultId());
							
							NicTransaction nicTransaction = transactionDao.findById(transactionId);
							NicRegistrationData nicRegistrationData = registrationDataDao.findById(transactionId);
							if(nicRegistrationData!=null){
								dto.setSurname(nicRegistrationData.getSurnameFull());
								dto.setFirstName(nicRegistrationData.getFirstnameFull());
							}
							if (nicTransaction!=null) {
								dto.setNin(nicTransaction.getNin());
							}
							logger.info("Inside Service===========>>>>>>Transaction Id Hit: "+ transactionId);
							String docType = NicTransactionAttachment.DOC_TYPE_THUMBNAIL_CAPTURE;
							String serialNo = NicTransactionAttachment.DEFAULT_SERIAL_NO;
							List<NicTransactionAttachment> nicTransDoc = transactionDocumentDao.findNicTransactionAttachments(transactionId, docType, serialNo).getListModel();
							// if thumbnails doesn't exists then get photo
							if (CollectionUtils.isEmpty(nicTransDoc)) {
								docType = NicTransactionAttachment.DOC_TYPE_PHOTO_CAPTURE;
								nicTransDoc = transactionDocumentDao.findNicTransactionAttachments(transactionId, docType, serialNo).getListModel();
							}
							if (CollectionUtils.isNotEmpty(nicTransDoc)) {
								byte[] photoBinary = nicTransDoc.get(0).getDocData();
								dto.setPhotoBinary(photoBinary);
							}
							this.setHitFingersNScore(hitResult.getHitInfo(), dto);
							String verifyHitDecision = "";
							if (hitResult.getVerifyDecision()!=null) {
								if (hitResult.getVerifyDecision().booleanValue()==true) {
									verifyHitDecision = "Y";
								} else {
									verifyHitDecision = "N";
								}
							}
							dto.setVerifyDecision(verifyHitDecision);
							candidateList.add(dto);
						}
					}
				}
			}
		} catch (Exception e) {
			throw new NicUploadJobServiceException(e);
		}
		return candidateList;
	}

	@Override
	public List<HitCandidateDTO> getAfisHitCandidateListByTxnId(String txnId) throws NicUploadJobServiceException {
		List<HitCandidateDTO> candidateList = null;
		try {
		
			List<NicUploadJob> nicUploadJobList = this.dao.findAllByTransactionId(txnId).getListModel();
			if (CollectionUtils.isNotEmpty(nicUploadJobList)) {
				for (NicUploadJob nicUploadJob : nicUploadJobList) {
					List<NicSearchResult> searchResultList = this.searchResultDao.findAllByJobId(nicUploadJob.getWorkflowJobId());
					if (CollectionUtils.isNotEmpty(searchResultList)) {
						candidateList = new ArrayList<HitCandidateDTO>();
						for (NicSearchResult searchResult : searchResultList) {
							if (searchResult != null && Boolean.TRUE.equals(searchResult.getHasHit())) {
								logger.info(" -----------------------------------JOB[{}], TXN[{}], RID[{}], Type of Search[{}].", new Object[] {searchResult.getWorkflowJobId(), txnId, searchResult.getSearchResultId(), searchResult.getTypeSearch()});
								for (NicSearchHitResult hitResult : searchResult.getHitList()) {
									String transactionId = hitResult.getTransactionIdHit();
									//skip for display error or self hit records.
									if (StringUtils.equals(txnId, transactionId))	continue;
									
									HitCandidateDTO dto = new HitCandidateDTO();
									dto.setNin(transactionId);
									dto.setTransactionId(transactionId);
									dto.setSearchResultId(searchResult.getSearchResultId());
									NicTransaction nicTransaction = transactionDao.findById(transactionId);
									NicRegistrationData nicRegistrationData = registrationDataDao.findById(transactionId);
									if (nicRegistrationData != null) {
										dto.setSurname(nicRegistrationData.getSurnameFull());
										dto.setFirstName(nicRegistrationData.getSurnameLine1());
									}
									if (nicTransaction!=null) {
										dto.setNin(nicTransaction.getNin());
									}
									logger.info("Inside Service===========>>>>>>Transaction Id Hit: " + transactionId);
									String docType = NicTransactionAttachment.DOC_TYPE_THUMBNAIL_CAPTURE;
									String serialNo = NicTransactionAttachment.DEFAULT_SERIAL_NO;
									List<NicTransactionAttachment> nicTransDoc = transactionDocumentDao.findNicTransactionAttachments(transactionId, docType, serialNo).getListModel();
									// if thumbnails doesn't exists then get photo
									if (CollectionUtils.isEmpty(nicTransDoc)) {
										docType = NicTransactionAttachment.DOC_TYPE_PHOTO_CAPTURE;
										nicTransDoc = transactionDocumentDao.findNicTransactionAttachments(transactionId, docType, serialNo).getListModel();
									}
									if (CollectionUtils.isNotEmpty(nicTransDoc)) {
										byte[] photoBinary = nicTransDoc.get(0).getDocData();
										dto.setPhotoBinary(photoBinary);
									}
									this.setHitFingersNScore(hitResult.getHitInfo(), dto);
									String verifyHitDecision = "";
									if (hitResult.getVerifyDecision()!=null) {
										if (hitResult.getVerifyDecision().booleanValue()==true) {
											verifyHitDecision = "Y";
										} else {
											verifyHitDecision = "N";
										}
									}
									dto.setVerifyDecision(verifyHitDecision);
									candidateList.add(dto);
								}
							}
						}
					}
				}
			}

		} catch (Exception e) {
			throw new NicUploadJobServiceException(e);
		}
		return candidateList;
	}

	/* helper method */
	private void setHitFingersNScore(String result, HitCandidateDTO dto) {
		String hitFingers = "";
		double matchingScore = 0;

		if (StringUtils.isBlank(result) || result.indexOf(",")==-1 || result.indexOf("=")==-1 ) {
			return;
		}
		
		StringTokenizer token = new StringTokenizer(result, ",");
		while (token.hasMoreTokens()) {
			StringTokenizer tokenScore = new StringTokenizer(token.nextToken(),
					"=");
			while (tokenScore.hasMoreTokens()) {
				String fingerPosition = tokenScore.nextToken();
				float score = Float.parseFloat(tokenScore.nextToken());
				hitFingers += fingerPosition;
				matchingScore += score;
			}
		}
		if (matchingScore >= 9999) {
			matchingScore = 9999;
		}
		dto.setHitFingers(hitFingers);
		dto.setMatchingScore("" + matchingScore);
	}
	
	/* Method to filter the false hit and true hit record */
	
	public List<HitCandidateDTO> sortAfisHitCandidateListByJobId(Long jobId)
			throws NicUploadJobServiceException {
		List<HitCandidateDTO> candidateList = this.getAfisHitCandidateListByJobId(jobId);
		//TODO sort by hitscore
		for (HitCandidateDTO hitCandidate : candidateList) {
			
		}
		return candidateList;
	}

	/**
	 * @deprecated
	 */
	@Override
	public NicJobCpdCheckResult findCpdData(long jobId) throws Exception {
		logger.info("Inside Service -- CPD Data ");
		NicJobCpdCheckResult cpdObj = new NicJobCpdCheckResult();
		try {
			//cpdObj = nicJobCpdCheckResultDao.findById(jobId);
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.error("Error occurred while getting the CPD Data -- Start Investigation. Exception: "
					+ ex.getMessage());

		}
		return cpdObj;
	}

	/**
	 * @deprecated
	 */
	@Override
	@Transactional(rollbackFor= NicUploadJobServiceException.class ,propagation = Propagation.REQUIRED)
	public void updateDecisionOnCpdReject(long rejectJobId, String userId, String wkstnId) throws NicUploadJobServiceException {
		try {
			//nicJobCpdCheckResultDao.updateDecisionCpdOnReject(rejectJobId, userId, wkstnId);
		} catch (Exception ex) {
			logger.error("Error occurred while rejecting the card. Exception: "
					+ ex.getMessage());
			throw new NicUploadJobServiceException(ex);
		}
		
	}

	/**
	 * @deprecated
	 */
	@Override
	@Transactional(rollbackFor= NicUploadJobServiceException.class ,propagation = Propagation.REQUIRED)
	public void updateDecisionOnAfisReject(long rejectJobId, String userId,
			String wkstnId) throws NicUploadJobServiceException {
		try {
			//nicJobAfisCheckResultDao.updateDecisionAfisOnReject(rejectJobId, userId, wkstnId);
		} catch (Exception ex) {
			logger.error("Error occurred while rejecting the card. Exception: "
					+ ex.getMessage());
			throw new NicUploadJobServiceException(ex);
		}
		
	}

	/**
	 * @deprecated
	 */
	@Override
	@Transactional(rollbackFor= NicUploadJobServiceException.class ,propagation = Propagation.REQUIRED)
	public void updateDecisionOnCpdApprove(long approveJobId, String userId,
			String wkstnId) throws NicUploadJobServiceException {
		try {
			//nicJobCpdCheckResultDao.updateDecisionCpdOnApprove(approveJobId, userId, wkstnId);
		} catch (Exception ex) {
			logger.error("Error occurred while rejecting the card. Exception: "
					+ ex.getMessage());
			throw new NicUploadJobServiceException(ex);
		}
		
	}

	/**
	 * @deprecated
	 */
	@Override
	@Transactional(rollbackFor= NicUploadJobServiceException.class ,propagation = Propagation.REQUIRED)
	public void updateDecisionOnAfisApprove(long approveJobId, String userId,
			String wkstnId) throws NicUploadJobServiceException {
		try {
			//nicJobAfisCheckResultDao.updateDecisionAfisOnApprove(approveJobId, userId, wkstnId);
		} catch (Exception ex) {
			logger.error("Error occurred while approving the card. Exception: "
					+ ex.getMessage());
			throw new NicUploadJobServiceException(ex);
		}
		
	}

	
	@Override
	@Transactional(rollbackFor= NicUploadJobServiceException.class ,propagation = Propagation.REQUIRED)
	public void updateRejectReasonRemarks(long rejectJobId,
			String rejectedReason, String rejectedRemarks, String userId,
			String wkstnId, String transactionId, String investigationType) throws NicUploadJobServiceException {
		logger.info("Inside the service layer==> NIC_REJECTION_DATA");
		try {
			nicRejectionDataDao.updateRejectReasonRemarks(rejectJobId, rejectedReason, rejectedRemarks, userId, wkstnId ,transactionId, investigationType);
		} catch (Exception ex) {
			logger.error("Error occurred while updating the NIC_REJECTION_DATA table. Exception: "
					+ ex.getMessage());
			throw new NicUploadJobServiceException(ex);
		}
	}

	
	@Override
	@Transactional(rollbackFor= NicUploadJobServiceException.class ,propagation = Propagation.REQUIRED)
	public void updateDataOnTrueHit(String hitTransactionId, Long searchResultId, String userId, String remarks) 
	throws NicUploadJobServiceException {
		logger.info("Inside service ===========>>>>>>>> True hit");
		try {
			searchHitResultDao.updateDataOnTrueHit(hitTransactionId, searchResultId, userId, remarks);
		} catch (Exception ex) {
			logger.error("Error occurred while updating the NIC_SEARCH_HIT_TABLE table. Exception: "
					+ ex.getMessage());
			throw new NicUploadJobServiceException(ex);
		}
	}

	
	@Override
	@Transactional(rollbackFor= NicUploadJobServiceException.class ,propagation = Propagation.REQUIRED)
	public void updateDataOnFalseHit(String hitTransactionId,
			Long searchResultId, String userId, String remarks)
			throws NicUploadJobServiceException {
		logger.info("Inside service ===========>>>>>>>> False hit");
		try {
			searchHitResultDao.updateDataOnFalseHit(hitTransactionId, searchResultId, userId, remarks);
		} catch (Exception ex) {
			logger.error("Error occurred while updating the NIC_SEARCH_HIT_TABLE table-- False Hit. Exception: "
					+ ex.getMessage());
			throw new NicUploadJobServiceException(ex);
		}
		
	}

	/**
	 * @deprecated
	 */
	@Override
	@Transactional(rollbackFor= NicUploadJobServiceException.class ,propagation = Propagation.REQUIRED)
	public String updateRemarks(String refId, String remarksData)
			throws NicUploadJobServiceException {
		logger.info("Inside service ============>>>>>>>>>>Update Remarks");
		String status = "";
		try{
			status = transactionLogDao.updateRemarks(refId, remarksData);
			
		}catch (Exception ex) {
			logger.error("Error occurred while updating the NIC_TRANSACTION_LOG TABLE table-- False Hit. Exception: "
					+ ex.getMessage());
			throw new NicUploadJobServiceException(ex);
		
	}
		return status;

}

	
	public List<RejectionData> getRejectionDataListBySiteCode(String siteCode, Date start, Date end) throws NicUploadJobServiceException {
		List<RejectionData> resultDTOList = new ArrayList<RejectionData>();
		try {
			List<NicRejectionData> resultList = this.nicRejectionDataDao.findByPeriod(siteCode, start, end);
			if (CollectionUtils.isNotEmpty(resultList)) {
				for (NicRejectionData dbo: resultList) {
					String transactionStatus = this.transactionDao.getTransactionStatusById(dbo.getTransactionId());
					RejectionData rejectionData = this.mapper.parseRejectionDataDTO(dbo, transactionStatus);
					//RejectionData rejectionData = new RejectionData();
					//rejectionData.setTransactionID(dbo.getTransactionId());
					rejectionData.setTransactionStatus(transactionStatus);
					//rejectionData.setRejectionInfo(new LogInfo());
					//rejectionData.getRejectionInfo().setReason(dbo.getRejectReason());
					//rejectionData.getRejectionInfo().setRemark(dbo.getRejectRemarks());
					resultDTOList.add(rejectionData);
				}
			}			
		} catch (DaoException e) {
			logger.error("Error occurred while getRejectionDataListBySiteCode. Exception: "
					+ e.getMessage());
			throw new NicUploadJobServiceException(e);
		}
		return resultDTOList;
	}

	@Transactional(rollbackFor= NicUploadJobServiceException.class ,propagation = Propagation.REQUIRED)
	public void updateRejectionDataDownloadedStatus(List<String> transactionIdList, String officerId, String wkstnId, String siteCode) throws NicUploadJobServiceException {
		try {
			//23 Oct 2013 (chris): validate IssSiteCode with transactionId - start
			boolean differentSiteCode = false;
			String transactionId = "";
			List<NicTransaction> nicTransactionDBOList = this.transactionDao.findAllByTransIdList(transactionIdList);
			if (CollectionUtils.isNotEmpty(nicTransactionDBOList)) {
				for (NicTransaction nicTransaction: nicTransactionDBOList) {
					if (!StringUtils.equalsIgnoreCase(siteCode, nicTransaction.getIssSiteCode())) {
						differentSiteCode = true;
						transactionId = nicTransaction.getTransactionId();
						logger.error("Transaction [{}] is belong to {} instead of {}, invalid update!!", new Object[]{ transactionId, nicTransaction.getIssSiteCode(), siteCode});
						break;
					}
				}	
			}
			if (differentSiteCode) {
				throw new NicUploadJobServiceException("Invalid update download status on transactionId:"+transactionId);
			}
			//23 Oct 2013 (chris): validate IssSiteCode with transactionId - end
			nicRejectionDataDao.updateDataReceivedByTransactionIdList(transactionIdList, officerId, wkstnId);
		} catch (Exception e) {
			logger.error("Error occurred while updating the NIC_REJECTION_DATA table. Exception: " + e.getMessage());
			throw new NicUploadJobServiceException(e);
		}
	}
	
	public NicTransactionLog getNicTransactionLog(String refId, String transactionStage, String transactionStatus) throws NicUploadJobServiceException{
		NicTransactionLog nicTransLog = null;
		try {
			NicTransactionLog nicTransactionLog = new NicTransactionLog();
			nicTransactionLog.setRefId(refId);
			nicTransactionLog.setTransactionStage(transactionStage);
			nicTransactionLog.setTransactionStatus(transactionStatus);
			
			List<NicTransactionLog> nicTransactionLogList = transactionLogDao.findAll(nicTransactionLog);
			if(CollectionUtils.isNotEmpty(nicTransactionLogList)){
				nicTransLog = nicTransactionLogList.get(0);
			}
		} catch (Exception e) {
			logger.error("Error occurred while getRejectionDataListBySiteCode. Exception: "
					+ e.getMessage());
			throw new NicUploadJobServiceException(e);
		}
		return nicTransLog;
	}

	/* (non-Javadoc)
	 * @see com.nec.asia.nic.comp.trans.service.NicUploadJobService#saveNicTransactionLog(com.nec.asia.nic.comp.trans.domain.NicTransactionLog)e
	 */
	@Override
	@Transactional(rollbackFor= NicUploadJobServiceException.class ,propagation = Propagation.REQUIRED)
	public void saveNicTransactionLog(NicTransactionLog nicTransactionLog)	throws NicUploadJobServiceException {
		try{
			if (StringUtils.isBlank(nicTransactionLog.getSiteCode())) {
				nicTransactionLog.setSiteCode(this.getCurrentSiteCodeFromParameter());
			}
			transactionLogDao.saveOrUpdate(nicTransactionLog);
		} catch(Exception ex){
			logger.error("Error occurred while saving the NIC transaction log. Exception:" +ex.getMessage(), ex);
			throw new NicUploadJobServiceException(ex);
		}
		
	}
	
	private String getCurrentSiteCodeFromParameter() {
		String currentSiteCode = null;
		Parameters parameter = parametersDao.findById(new ParametersId(PARA_SCOPE_SYSTEM, PARA_NAME_CURRENT_SITE_CODE));
		if (parameter!=null) {
			currentSiteCode = (String) parameter.getParaValue();
		} else {
			logger.warn("No matching Parameter for {} , {} ", PARA_SCOPE_SYSTEM, PARA_NAME_CURRENT_SITE_CODE);
		}
		return currentSiteCode;
	}
	
	public  NicUploadJob getJobEnqueryDetails(long jobId) {

		NicUploadJob uploadJob =this.dao.findById(jobId);
		if(uploadJob!=null){
			return uploadJob;
		}else{
			return null;	
		}
		
		
	}
	public NicTransaction getNicTransaction(String transactionId){
		NicTransaction nictxnObj =transactionDao.findById(transactionId);
		if(nictxnObj!=null){
			return nictxnObj;
		}else{
			return null;
		}
	
	}
	public NicRegistrationData getNicRegistrationData(String transactionId){
		NicRegistrationData nicRegObj =registrationDataDao.findById(transactionId);
		if(nicRegObj!=null){
			return nicRegObj;
		}else{
			return null;
		}
	
	}

	
	@Override
	@Transactional(rollbackFor= NicUploadJobServiceException.class ,propagation = Propagation.REQUIRED)
	public void insertRejectReasonRemarks(long rejectJobId,
			String rejectedReason, String rejectedRemarks, String userId,
			String wkstnId, String transactionId) throws Exception {
		try{
			transactionLogDao.insertRejectReasonRemarks(rejectJobId, 
					rejectedReason, rejectedRemarks, userId, wkstnId, transactionId);
			
		} catch(Exception e){
			logger.error("Error occurred while inserting the record in NIC transaction log. Exception:" +e.getMessage(), e);
			throw new NicUploadJobServiceException(e);
		}
		
	}
	
	@Override
	@Transactional(rollbackFor= java.lang.Throwable.class ,propagation = Propagation.REQUIRED)
	public void updateOnApprove(String transactionId, String userId,
			String wkstnId) {
		try{
			transactionDao.updateOnApprove(transactionId, userId, wkstnId);
		} catch(Exception e){
			e.printStackTrace();
			logger.error("Error occurred while updating the transaction Status in NIC_TRANSACTION: "+e.getMessage());
		}
	}

	
	@Override
	@Transactional(rollbackFor= java.lang.Throwable.class ,propagation = Propagation.REQUIRED)
	public void updateOnReject(String transactionId, String userId,
			String wkstnId) {
		try{
			transactionDao.updateOnReject(transactionId, userId, wkstnId);
		} catch(Exception e){
			e.printStackTrace();
			logger.error("Error occurred while updating the transaction Status in NIC_TRANSACTION: "+e.getMessage());
		}
		
	}

	
	@Override
	public List<String> getCount(Long searchResultId, String transactionId) {
		List<String> countTrueHit = null;
		try{
			countTrueHit = searchHitResultDao.getCount(searchResultId, transactionId);
		} catch(Exception e){
			logger.error("Error occured while getting the count of hit: "+e.getMessage(), e);
		}
		return countTrueHit;
	}

	@Override
	public List<Boolean> getCount(String transactionId, Long searchResultId ) {
		List<Boolean> countTrueHit = null;
		try {
			countTrueHit = searchHitResultDao.getCount( transactionId , searchResultId);
		} catch (Exception e) {
			logger.error("Error occured while getting the count of hit: " + e.getMessage(), e);
		}
		return countTrueHit;
	}

	@Override
	public BaseModelList<NicTransactionLog> getNicTransactionLogs(String refId) throws NicUploadJobServiceException {
		try{
			return transactionLogDao.findAllByRefIdAndTransStateList(refId,null);
		} catch(Exception e){
			e.printStackTrace();
			logger.error("Error occurred while getting the NIC transaction logs. Exception:" +e.getMessage());
//			throw new NicUploadJobServiceException("Error occurred while getting the NIC transaction logs. Exception:" +e.getMessage());
			return new BaseModelList<NicTransactionLog>(null, false, CreateMessageException.createMessageException(e) + " - getNicTransactionLogs - " + refId + " - thất bại.");
		}
	}

	/* (non-Javadoc)
	 * @see com.nec.asia.nic.comp.trans.service.NicUploadJobService#updateNicRegistrationData(com.nec.asia.nic.comp.trans.domain.NicRegistrationData)
	 */
	@Override
	@Transactional(rollbackFor= java.lang.Throwable.class ,propagation = Propagation.REQUIRED)
	public void updateNicRegistrationData(NicRegistrationData registrationData)	throws NicUploadJobServiceException {
		try{
			 registrationDataDao.saveOrUpdate(registrationData);
		} catch(Exception e){
			logger.error("Error occurred while updating the nic registration data. Exception:" +e.getMessage(), e);
			throw new NicUploadJobServiceException("Error occurred while updating the nic registration data. Exception:" +e.getMessage());
		}
	}

	@Override
	public long getPendingInvestigationsCount() throws NicUploadJobServiceException {
		try{
			return uploadJobDao.getPendingInvestigationsCount();
		} catch(Exception e){
			logger.error("Error occurred while getting the pending investigations count. Exception:" +e.getMessage(), e);
			throw new NicUploadJobServiceException("Error occurred while getting the pending investigations count. Exception:" +e.getMessage());
		}
	}

	@Override
	public long getSuspendedInvestigationsCount() throws NicUploadJobServiceException {
		try {
			return uploadJobDao.getSuspendedInvestigationsCount();
		} catch (Exception e) {
			logger.error("Error occurred while getting the Suspended investigations count. Exception:" + e.getMessage(),
					e);
			throw new NicUploadJobServiceException(
					"Error occurred while getting the Suspended investigations count. Exception:" + e.getMessage());
		}
	}

	
	@Override
	@Transactional(rollbackFor= java.lang.Throwable.class ,propagation = Propagation.REQUIRED)
	public String editRemarksData(String refId,
			String transactionStage, String transactionStatus)
			throws NicUploadJobServiceException {
		String remarks = "";
		try{
			//nicTransactionLog = transactionLogDao.editRemarksData(refId, transactionStage, transactionStatus);
			remarks = transactionLogDao.findRemarksByTxnIdNStatus(refId, transactionStage, transactionStatus);
		} catch(Exception e){
			//e.printStackTrace();
			logger.error("Error occurred while editing the remarks. Exception:" +e.getMessage(), e);
			throw new NicUploadJobServiceException("Error occurred while editing the remarks. Exception:" +e.getMessage());
		}
		return remarks;
	}
	
	@Override
	public PaginatedResult<NicUploadJobDto> findRejectedJobsForSupervisor(
			String officerId, int pageNo, int pageSize) throws NicUploadJobServiceException {
		List<NicUploadJobDto> joblstDto = new ArrayList<NicUploadJobDto>();
		PaginatedResult<NicUploadJob> pr = null;
		PaginatedResult<NicUploadJobDto> pageResult = new PaginatedResult<NicUploadJobDto>();
		try {
			pr = uploadJobDao.findRejectedJobsForSupervisor(officerId, pageNo, pageSize);
			if (pr.getRows() != null) {
				List<NicUploadJob> jobList = pr.getRows();
				for (NicUploadJob record : jobList) {
					logger.info("Upload Job Id=============>>>>>>>>>>>>>>>>>>>"
							+ record.getWorkflowJobId());
					NicUploadJobDto dto = new NicUploadJobDto();
					dto.setUploadJobId(record.getWorkflowJobId());
					dto.setTransactionId(record.getTransactionId());
					//CodeValues codeValue = codesService.getCodeValueByIdName("TRANSACTION_TYPE", record.getJobType());
					CodeValues codeValue = codesSevice.getCodeValueByIdName("JOB_TYPE", record.getJobType()); //2014 Aug 20
					if(codeValue!=null){
						dto.setJobType(codeValue.getCodeValueDesc());
					}else{
						dto.setJobType(record.getJobType());
					}
					dto.setInvestigationType(record.getInvestigationType());
					dto.setJobCurrentState(record.getJobCurrentStage());
					dto.setInvestigationStatus(record.getInvestigationStatus());
					dto.setInvestigationOfficerId(record.getInvestigationOfficerId());
					dto.setJobUploadTime(record.getJobCreatedTime());
					joblstDto.add(dto);
				}
				pageResult.setRows(joblstDto);
				pageResult.setTotal((int) pr.getTotal());
				pageResult.setPage(pr.getPage());
			}
		} catch (Exception ex) {
			logger.error("Error occurred while getting the investigation job list. Exception: "
					+ ex.getMessage());
			throw new NicUploadJobServiceException(ex);
		}
		return pageResult;
	}
	
	public String getUserRole(String usrId) {
		String roleName = null;
		Users userRole = usersDao.findById(usrId);
		Set<Roles> roleSet = userRole.getRoles();
		for (Roles role : roleSet) {
			if ((role.getRoleId()).equalsIgnoreCase("SUPERVISOR")) {
				roleName = role.getRoleId();
			}
		}
		return roleName;
	}

	
	@Override
	@Transactional(rollbackFor= NicUploadJobServiceException.class ,propagation = Propagation.REQUIRED)
	public void assignNewRejectedJobList(String userid)
			throws NicUploadJobServiceException {
		try {
			dao.assignNewRejectJob(userid);
		} catch (Exception ex) {
			logger.error("Error occurred while getting the assigning the rejected job list. Exception: "
					+ ex.getMessage());
			throw new NicUploadJobServiceException(ex);
		}
	}
	
	@Override
	@Transactional(rollbackFor= java.lang.Throwable.class ,propagation = Propagation.REQUIRED)
	public void updateJobToResetIncompletedInvestigation(String updateBy, String updateWkstnId) throws NicUploadJobServiceException {
		try {
			List<NicUploadJob> resultList = dao.findAllPendingInvestigationJob();
			for (NicUploadJob job : resultList) {
				logger.debug("[updateJobToResetIncompletedInvestigation] reset investigation for jobId:{}, transactionId:{}, io:{}, status:{} ", new Object[] {job.getWorkflowJobId(), job.getTransactionId(), job.getInvestigationOfficerId(), job.getInvestigationStatus()});
				job.setInvestigationOfficerId(null);
				job.setInvestigationStatus(NicUploadJob.RECORD_STATUS_INITIAL);
				job.setUpdateBy(updateBy);
				job.setUpdateWkstnId(updateWkstnId);
				job.setUpdateDatetime(new Date());
				dao.saveOrUpdate(job);
			}
		} catch (Exception ex) {
			throw new NicUploadJobServiceException(ex);
		}
	}

	@Override
	public void cancelTransaction(Long jobId, String userId,
			String wkstnId) throws NicUploadJobServiceException {
		
		try {
			dao.cancelTransaction(jobId, userId, wkstnId);
		} catch (Exception ex) {
			logger.error("Error occurred while rejecting the card. Exception: "+ ex.getMessage(), ex);
			throw new NicUploadJobServiceException(ex);
		}
	}

	@Override
	public void updateCancelTransaction(Long jobId, String cancelReason,
			String userId, String wkstnId,
			String transactionId)
			throws NicUploadJobServiceException {
		logger.info("Inside the service layer to cancel the transaction==> NIC_REJECTION_DATA");
		try {
			nicRejectionDataDao.cancelTransaction(jobId, cancelReason, userId, wkstnId ,transactionId);
		}catch (Exception ex) {
			logger.error("Error occurred while updating the NIC_REJECTION_DATA table. Exception: "
					+ ex.getMessage());
			throw new NicUploadJobServiceException(ex);
		}	
		
	}

	@Override
	public PaginatedResult<NicUploadJob> findAllForPagination(
			PageRequest pageRequest, NicUploadJob nicUploadJob,
			String dateFrom, String dateTo) throws NicUploadJobServiceException {
		try {
			int pageNo = pageRequest.getPageNo();
			int pageSize = pageRequest.getMaxRecords();
			boolean ignoreCase = false;
			boolean enableLike = false;
			Order order = null;
			if(StringUtils.equals(pageRequest.getSortorder(),"asc")){
				order = Order.asc(pageRequest.getSortname());
			} else {
				order = Order.desc(pageRequest.getSortname());
			}

			Date from = DateUtil.strToDate(dateFrom, DateUtil.FORMAT_DDdashMMMdashYYYY);
			Date to = DateUtil.strToDate(dateTo, DateUtil.FORMAT_DDdashMMMdashYYYY);
			
			return dao.findAllForPagination(nicUploadJob, from, to, pageNo, pageSize, ignoreCase, enableLike, order);
		} catch (Exception e) {
			throw new NicUploadJobServiceException(e);
		}
	}

	@Override
	public PaginatedResult<NicUploadJob> findAllForPagination(PageRequest pageRequest, NicUploadJob nicUploadJob, String[] investStatus,Long jobId, boolean isShowErrTrans) throws NicUploadJobServiceException {
		try {
			int pageNo = pageRequest.getPageNo();
			int pageSize = pageRequest.getMaxRecords();
			boolean ignoreCase = false;
			boolean enableLike = false;
			Order order = null;
			if(StringUtils.equals(pageRequest.getSortorder(),"asc")){
				order = Order.asc(pageRequest.getSortname());
			} else {
				order = Order.desc(pageRequest.getSortname());
			}
			return dao.findAllForPagination(nicUploadJob, investStatus, jobId, isShowErrTrans, pageNo, pageSize, ignoreCase, enableLike, order);
		} catch (Exception e) {
			throw new NicUploadJobServiceException(e);
		}
	}
	
	@Override
	public List<NicTransactionLog> findAllLogInfo(String refId,
			String transactionStage, String transactionStatus)
			throws NicUploadJobServiceException {
		List<NicTransactionLog> nicTransactionLogList = new ArrayList<NicTransactionLog>();
		try{
			nicTransactionLogList = transactionLogDao.findAllLogInfo(refId, transactionStage, transactionStatus);
		}catch (Exception ex) {
			logger.error("Error occurred while getting the remarks list for Perso. Exception: "
					+ ex.getMessage());
			throw new NicUploadJobServiceException(ex);
		}	
		return nicTransactionLogList;
	}

	@Override
	public String findAllPersoRemarks(String transactionId,
			String transactionStage, String transactionStatus)
			throws NicUploadJobServiceException {
		String nicTransactionLog = "";
		try{
			nicTransactionLog = transactionLogDao.findAllPersoRemarks(transactionId, transactionStage, transactionStatus);
		} catch(Exception e){
			e.printStackTrace();
			logger.error("Error occurred while editing the perso remarks for Additional information. Exception:" +e.getMessage());
			throw new NicUploadJobServiceException("Error occurred while editing the perso remarks for Additional information. Exception:" +e.getMessage());
		}
		return nicTransactionLog;
	}

	@Override
	public NicRejectionData findRejectInfo(long jobId)
			throws NicUploadJobServiceException {
		NicRejectionData nicRejectionData = new NicRejectionData();
		logger.info("Inside the service layer to display the rejected remarks info==> NIC_REJECTION_DATA");
		try {
			nicRejectionData = nicRejectionDataDao.findRejectInfo(jobId);
		}catch (Exception ex) {
			logger.error("Error occurred while getting the rejected info for supervisor module NIC_REJECTION_DATA table. Exception: "
					+ ex.getMessage());
			throw new NicUploadJobServiceException(ex);
		}
		return nicRejectionData;
	}

	@Override
	public String updatePersoRegisterStatusOnReprint(String transactionId,
			String transactionType, String userId, String wkstnId) throws NicUploadJobServiceException {
		logger.info("Inside the service layer to update perso register status upon reprint");
		String status="";
		try {
			status = dao.updatePersoRegisterStatusOnReprint(transactionId, transactionType, userId, wkstnId);
			
		} catch (Exception ex) {
			logger.error("Error occurred while update perso register status upon reprint. Exception: "+ ex.getMessage(), ex);
			throw new NicUploadJobServiceException(ex);
		}
		return status;
		
	}

	@Override
	public NicTransactionLog getLatestErrorMessage(String transactionId) throws NicUploadJobServiceException {
		try {
			return transactionLogDao.getLatestErrorMessage(transactionId);
			
		} catch (Exception ex) {
			logger.error("Error occurred while getting the transaction latest error message. Exception: "+ ex.getMessage(), ex);
			throw new NicUploadJobServiceException(ex);
		}
	}	
	
	@Override
	public void queTxnForReprocess(long jobId) throws NicUploadJobServiceException{
		
//		String result = "failed";
		NicUploadJob nicObj = new NicUploadJob();
		//NicCommandUtil nicCommandUtil = new NicCommandUtil();
		int maxReprocessCount = 3;
		
		try{
			
			nicObj = dao.findById(jobId);
			
			if (nicObj == null){
				throw new NicUploadJobServiceException("Job Id ["+jobId+"] not found.");
			}else if(nicObj.getJobReprocessCount() < maxReprocessCount){
				throw new NicUploadJobServiceException("Job Id ["+jobId+"] is still in progress.");
			}else{
				
				nicObj.setUpdateBy("SYSTEM_NIC");
				nicObj.setUpdateDatetime(new Date());
				
				nicObj.setUpdateWkstnId(nicCommandUtil.getHostName());
				
				Calendar cal = Calendar.getInstance();
				cal.setTime(nicObj.getJobEndTime());
				
				logger.info("date before changing:"+cal.getTime());
				
				cal.add(Calendar.DATE,-2);
				
				logger.info("date after changing:"+cal.getTime());
				
				nicObj.setJobEndTime(cal.getTime());
				
				
				dao.saveOrUpdate(nicObj);
			}
			
			
		}catch(Exception e){
			logger.error("Exception encountered:"+e.getMessage());
			throw new NicUploadJobServiceException(e);
		}
		
	}
	
	@Override
	public void retryTransaction(long jobId, String officerId, String workStationId) throws NicUploadJobServiceException{
		logger.info("start of retryTransaction(), jobId:"+jobId);
		NicUploadJob nicObj = new NicUploadJob();
		//NicCommandUtil nicCommandUtil = new NicCommandUtil();
		int maxReprocessCount = 3;
		String transactionStage = "JOB_RETRY";
		String logInfo = "";
		String logData = null;
		
	try{	
		
		nicObj = dao.findById(jobId);
		
		if (nicObj == null){
			logger.info("retryTransaction() nicObj is null, job id cannot be found.");
			throw new NicUploadJobServiceException("Job Id ["+jobId+"] not found.");
		}else if(nicObj.getJobReprocessCount() < maxReprocessCount){
			logger.info("retryTransaction() nicObj cant be processed since current reprocess count is only:"+nicObj.getJobReprocessCount());
			throw new NicUploadJobServiceException("Job Id ["+jobId+"] is still in progress.");
		}else{
			
			logger.info("retryTransaction() transaction id:"+nicObj.getTransactionId());
			
			nicObj.setUpdateBy("SYSTEM_NIC");
			nicObj.setUpdateDatetime(new Date());
			nicObj.setUpdateWkstnId(nicCommandUtil.getHostName());
			logInfo = "<logInfoDTO><Reason>Job Retry</Reason><Remark>Job Reprocess count changed from "+nicObj.getJobReprocessCount()+" to 0</Remark></logInfoDTO>";
			
			logger.info("retryTransaction() before creating translog");
			
			NicTransactionLog transactionLog = new NicTransactionLog();
			transactionLog.setRefId(nicObj.getTransactionId());
			transactionLog.setLogCreateTime(new Date());
			transactionLog.setTransactionStage(transactionStage);
			transactionLog.setTransactionStatus(nicObj.getJobCurrentStatus());
			transactionLog.setSiteCode(nicCommandUtil.getSystemSiteCodeFromParameter()); //get from 'CURRENT_SITE_CODE'
			transactionLog.setStartTime(new Date());
			transactionLog.setEndTime(new Date());
			transactionLog.setLogInfo(logInfo);
			transactionLog.setLogData(logData);
			transactionLog.setWkstnId(workStationId);
			transactionLog.setOfficerId(officerId);

			transactionLogDao.saveOrUpdate(transactionLog);
			logger.info("retryTransaction() after creating translog");
			
			logger.info("retryTransaction() before setting reprocess count to zero");
			nicObj.setJobReprocessCount(0);
			//2014 Aug 20 - start
			nicObj.setUpdateBy(officerId);
			nicObj.setUpdateWkstnId(workStationId);
			nicObj.setUpdateDatetime(new Date());
			//2014 Aug 20 - end
			dao.saveOrUpdate(nicObj);
			logger.info("retryTransaction() after setting reprocess count to zero");
		}
		
	}catch(Exception e){
		logger.error("Exception encountered:"+e.getMessage());
		throw new NicUploadJobServiceException(e);
	}
	}
	
	@Override
	public Long getUploadJobId(String transId, String[] jobTypes) throws NicUploadJobServiceException {
		try{	
			return this.dao.getUploadJobId(transId, jobTypes);
		}catch(Exception e){
			logger.error("Exception encountered:"+e.getMessage());
			throw new NicUploadJobServiceException(e);
		}
	}

	@Override
	public PaginatedResult<NicUploadJobDto> findAllForInvestigationPagination(String[] recordStatuses, String officerId,
			int pageNo, int pageSize) throws NicUploadJobServiceException {
		return this.findAllForInvestigationPagination( recordStatuses,   officerId,
				  pageNo,   pageSize,null);
	}

	@Override
	public PaginatedResult<NicUploadJobDto> findAllForInvestigationPagination(String[] recordStatuses, String officerId,
			int pageNo, int pageSize, Boolean assignedOnly) throws NicUploadJobServiceException {
		return this.findAllForInvestigationPagination( recordStatuses,   officerId,
				  pageNo,   pageSize, assignedOnly, null); 
	}
	
	@Override
	public PaginatedResult<NicUploadJobDto> findAllForInvestigationPagination(String[] recordStatuses, String officerId,
			int pageNo, int pageSize, Boolean assignedOnly, AssignmentFilterAll assignmentFilter) throws NicUploadJobServiceException {
		List<NicUploadJobDto> joblstDto = new ArrayList<NicUploadJobDto>(); 
		PaginatedResult<NicUploadJob> pr = null;
		PaginatedResult<NicUploadJob> prDSQ = null;
		PaginatedResult<NicUploadJobDto> pageResult = new PaginatedResult<NicUploadJobDto>();
		try {
			// NicUploadJob nicUploadJob = new NicUploadJob();
			// nicUploadJob.setInvestigationOfficerId(userId);
			pr = dao.findInvestigationJobForPaginationByOfficerId(recordStatuses, officerId, pageNo, pageSize,   assignedOnly, assignmentFilter);
			
			// dao.findAllForPagination(nicUploadJob, pageNo,
			// pageSize);//dao.findAllInvestigationForPagination(nicUploadJob,
			// pageNo, pageSize);
			if (pr.getRows() != null) {
			
				List<NicUploadJob> jobList = pr.getRows();
			
				for (NicUploadJob record : jobList) {
					
					logger.info("Upload Job Id=============>>>>>>>>>>>>>>>>>>>" + record.getWorkflowJobId());
					NicUploadJobDto dto = new NicUploadJobDto();
					dto.setUploadJobId(record.getWorkflowJobId());
					dto.setTransactionId(record.getTransactionId());

					// CodeValues codeValue =
					// codesService.getCodeValueByIdName("TRANSACTION_TYPE",
					// record.getJobType());
					CodeValues codeValue = codesSevice.getCodeValueByIdName("JOB_TYPE", record.getJobType());

					if (codeValue != null) {
						dto.setJobType(codeValue.getCodeValueDesc());
					} else {
						dto.setJobType(record.getJobType());
					}
					List<NicTransactionLog> log_ = transactionLogDao.findAllByRefIdAndTransStateList(record.getTransactionId(),null).getListModel();
					String logStr = "";
					if(log_ != null){
						for(NicTransactionLog l : log_){
							if(!StringUtils.isEmpty(l.getLogInfo()))
								logStr += l.getLogInfo().replaceAll("\\<.*?>","") + " | ";
								//replaceAll("\\<.*?>","") 
						}
					}
					
					dto.setInvestigationType(record.getInvestigationType());
					dto.setJobCurrentState(record.getJobCurrentStage());
					dto.setInvestigationStatus(record.getInvestigationStatus());
					dto.setInvestigationOfficerId(record.getInvestigationOfficerId());
					dto.setJobUploadTime(record.getJobCreatedTime());
					if(record.getValidateInfoBca() != null){
						if(record.getValidateInfoBca() == 1)
							dto.setValidateInfoBca_des("Đã gửi");
						else if(record.getValidateInfoBca() == 2)
							dto.setValidateInfoBca_des("Đã có kết quả từ BCA");
						else {
							dto.setValidateInfoBca_des("Không");
						}
							
					}
					
					dto.setPassportNo(record.getTempPassportNo());
					dto.setPersoCheckStatus(record.getPersoRegisterStatus());
					dto.setRecieptNo(record.getReceiptNo());
					
					NicDocumentData nicData = documentDataService.findByDocNumber(record.getTempPassportNo()).getModel();
					if(nicData != null){
						/*Kiểm tra cho phép đồng bộ sang NIC Cục lãnh sự */
						if(record.getPersoRegisterStatus() != null && record.getPersoRegisterStatus().equals(NicUploadJob.RECORD_STATUS_APPROVED) 
								&&nicData.getStatus().equals("ISSUANCE")
								&& nicData.getIssuedFlag() && nicData.getActiveFlag())
						{
							dto.setSyncNic(true);
						}
						else
							dto.setSyncNic(false);
						
						dto.setPackageId(nicData.getPackageId());
					}
					else
						dto.setSyncNic(false);
					/*
					if(record.getPriority() == 0)
						dto.setPriorityString("Thông thường");*/
					NicTransaction nicTran = nicTransactionService.getNicTransactionById(record.getTransactionId()).getModel();
					//if(record.getJobType().equals("LOS"))
						//logStr = nicTran.getNicRegistrationData().getReasonKind();
					dto.setNoteInvestigation(logStr);
					if(nicTran!= null){
						dto.setNicSiteCode(nicTran.getNicSiteCode());
					}
					joblstDto.add(dto);
				}
				pageResult.setRows(joblstDto);
				pageResult.setTotal((int) pr.getTotal());
				pageResult.setPage(pr.getPage());
			}
		} catch (Exception ex) {
			logger.error("Error occurred while getting the investigation job list. Exception: " + ex.getMessage());
			throw new NicUploadJobServiceException(ex);
		}
		return pageResult;
	}
	
	@Override
	public PaginatedResult<NicUploadJobDto> findAllForInvestigationPaginationAll(String[] recordStatuses, String officerId,
			int pageNo, int pageSize, Boolean assignedOnly, AssignmentFilterAll assignmentFilter) throws NicUploadJobServiceException {
		List<NicUploadJobDto> joblstDto = new ArrayList<NicUploadJobDto>(); 
		PaginatedResult<NicUploadJob> pr = null;
		PaginatedResult<NicUploadJob> prDSQ = null;
		PaginatedResult<NicUploadJobDto> pageResult = new PaginatedResult<NicUploadJobDto>();
		try {
			// NicUploadJob nicUploadJob = new NicUploadJob();
			// nicUploadJob.setInvestigationOfficerId(userId);
			pr = dao.findInvestigationJobForPaginationByOfficerIdAll(recordStatuses, officerId, pageNo, pageSize,   assignedOnly, assignmentFilter);
			
			// dao.findAllForPagination(nicUploadJob, pageNo,
			// pageSize);//dao.findAllInvestigationForPagination(nicUploadJob,
			// pageNo, pageSize);
			if (pr.getRows() != null) {
			
				List<NicUploadJob> jobList = pr.getRows();
			
				for (NicUploadJob record : jobList) {
					
					logger.info("Upload Job Id=============>>>>>>>>>>>>>>>>>>>" + record.getWorkflowJobId());
					NicUploadJobDto dto = new NicUploadJobDto();
					dto.setUploadJobId(record.getWorkflowJobId());
					dto.setTransactionId(record.getTransactionId());

					// CodeValues codeValue =
					// codesService.getCodeValueByIdName("TRANSACTION_TYPE",
					// record.getJobType());
					CodeValues codeValue = codesSevice.getCodeValueByIdName("JOB_TYPE", record.getJobType()); // 2014
																														// Aug
																														// 20

					if (codeValue != null) {
						dto.setJobType(codeValue.getCodeValueDesc());
					} else {
						dto.setJobType(record.getJobType());
					}
					List<NicTransactionLog> log_ = transactionLogDao.findAllByRefIdAndTransStateList(record.getTransactionId(),null).getListModel();
					String logStr = "";
					if(log_ != null){
						for(NicTransactionLog l : log_){
							if(!StringUtils.isEmpty(l.getLogInfo()))
								logStr += l.getLogInfo().replaceAll("\\<.*?>","") + " | ";
								//replaceAll("\\<.*?>","") 
						}
					}
					dto.setNoteInvestigation(logStr);
					dto.setInvestigationType(record.getInvestigationType());
					dto.setJobCurrentState(record.getJobCurrentStage());
					dto.setInvestigationStatus(record.getInvestigationStatus());
					dto.setInvestigationOfficerId(record.getInvestigationOfficerId());
					dto.setJobUploadTime(record.getJobCreatedTime());
					if(record.getValidateInfoBca() != null){
						if(record.getValidateInfoBca() == 1)
							dto.setValidateInfoBca_des("Đã gửi");
						else if(record.getValidateInfoBca() == 2)
							dto.setValidateInfoBca_des("Đã có kết quả từ BCA");
						else {
							dto.setValidateInfoBca_des("Không");
						}
							
					}
					
					dto.setPassportNo(record.getTempPassportNo());
					dto.setPersoCheckStatus(record.getPersoRegisterStatus());
					dto.setRecieptNo(record.getReceiptNo());
					
					NicDocumentData nicData = documentDataService.findByDocNumber(record.getTempPassportNo()).getModel();
					if(nicData != null){
						/*Kiểm tra cho phép đồng bộ sang NIC Cục lãnh sự */
						if(record.getPersoRegisterStatus() != null && record.getPersoRegisterStatus().equals(NicUploadJob.RECORD_STATUS_APPROVED) 
								&&nicData.getStatus().equals("ISSUANCE")
								&& nicData.getIssuedFlag() && nicData.getActiveFlag())
						{
							dto.setSyncNic(true);
						}
						else
							dto.setSyncNic(false);
						
						dto.setPackageId(nicData.getPackageId());
					}
					else
						dto.setSyncNic(false);
					/*
					if(record.getPriority() == 0)
						dto.setPriorityString("Thông thường");*/
					NicTransaction nicTran = nicTransactionService.getNicTransactionById(record.getTransactionId()).getModel();
					if(nicTran!= null){
						dto.setNicSiteCode(nicTran.getNicSiteCode());
					}
					joblstDto.add(dto);
				}
				pageResult.setRows(joblstDto);
				pageResult.setTotal((int) pr.getTotal());
				pageResult.setPage(pr.getPage());
			}
		} catch (Exception ex) {
			logger.error("Error occurred while getting the investigation job list. Exception: " + ex.getMessage());
			throw new NicUploadJobServiceException(ex);
		}
		return pageResult;
	}
	
	@Override
	public PaginatedResult<NicUploadJobDto> findAllForInvestigationPaginationDSQ(String[] recordStatuses, String officerId,
			int pageNo, int pageSize, Boolean assignedOnly, AssignmentFilterAll assignmentFilter) throws NicUploadJobServiceException {
		List<NicUploadJobDto> joblstDto = new ArrayList<NicUploadJobDto>(); 
		PaginatedResult<NicUploadJob> pr = null;
		PaginatedResult<NicUploadJobDto> pageResult = new PaginatedResult<NicUploadJobDto>();
		try {
			// NicUploadJob nicUploadJob = new NicUploadJob();
			// nicUploadJob.setInvestigationOfficerId(userId);
			pr = dao.findInvestigationJobForPaginationByOfficerId_DSQ(recordStatuses, officerId, pageNo, pageSize,   assignedOnly, assignmentFilter);
			// dao.findAllForPagination(nicUploadJob, pageNo,
			// pageSize);//dao.findAllInvestigationForPagination(nicUploadJob,
			// pageNo, pageSize);
			if (pr.getRows() != null) {
			
				List<NicUploadJob> jobList = pr.getRows();
			
				for (NicUploadJob record : jobList) {
					
					logger.info("Upload Job Id=============>>>>>>>>>>>>>>>>>>>" + record.getWorkflowJobId());
					NicUploadJobDto dto = new NicUploadJobDto();
					dto.setUploadJobId(record.getWorkflowJobId());
					dto.setTransactionId(record.getTransactionId());

					// CodeValues codeValue =
					// codesService.getCodeValueByIdName("TRANSACTION_TYPE",
					// record.getJobType());
					CodeValues codeValue = codesSevice.getCodeValueByIdName("JOB_TYPE", record.getJobType()); // 2014
																														// Aug
																														// 20

					if (codeValue != null) {
						dto.setJobType(codeValue.getCodeValueDesc());
					} else {
						dto.setJobType(record.getJobType());
					}
					List<NicTransactionLog> log_ = transactionLogDao.findAllByRefIdAndTransStateList(record.getTransactionId(),null).getListModel();
					String logStr = "";
					if(log_ != null){
						for(NicTransactionLog l : log_){
							if(!StringUtils.isEmpty(l.getLogInfo()))
								logStr += l.getLogInfo().replaceAll("\\<.*?>","") + " | ";
								//replaceAll("\\<.*?>","") 
						}
					}
					dto.setNoteInvestigation(logStr);
					dto.setInvestigationType(record.getInvestigationType());
					dto.setJobCurrentState(record.getJobCurrentStage());
					dto.setInvestigationStatus(record.getInvestigationStatus());
					dto.setInvestigationOfficerId(record.getInvestigationOfficerId());
					dto.setJobUploadTime(record.getJobCreatedTime());
					if(record.getValidateInfoBca() != null){
						if(record.getValidateInfoBca() == 1 && !record.getInvestigationStatus().equals(NicUploadJob.RECORD_STATUS_CONFIRMED_DSQ))
							dto.setValidateInfoBca_des("Đã gửi");
						else if(record.getValidateInfoBca() == 2)
							dto.setValidateInfoBca_des("Đã có kết quả từ BCA");
						else {
							if(record.getInvestigationStatus().equals(NicUploadJob.RECORD_STATUS_CONFIRMED_DSQ)){
								dto.setValidateInfoBca_des("Đã có kết quả từ CLS");
							}
							else
							{
								dto.setValidateInfoBca_des("Không");
							}
						}
					}
					else
						dto.setValidateInfoBca_des("Không");
					
					dto.setPassportNo(record.getTempPassportNo());
					dto.setPersoCheckStatus(record.getPersoRegisterStatus());
					dto.setRecieptNo(record.getReceiptNo());
					
					NicDocumentData nicData = documentDataService.findByDocNumber(record.getTempPassportNo()).getModel();
					if(nicData != null){
						/*Kiểm tra cho phép đồng bộ sang NIC Cục lãnh sự */
						if(record.getPersoRegisterStatus() != null && record.getPersoRegisterStatus().equals(NicUploadJob.RECORD_STATUS_APPROVED) 
								&&nicData.getStatus().equals("ISSUANCE")
								&& nicData.getIssuedFlag() && nicData.getActiveFlag())
						{
							dto.setSyncNic(true);
						}
						else
							dto.setSyncNic(false);
						
						dto.setPackageId(nicData.getPackageId());
					}
					else
						dto.setSyncNic(false);
					/*
					if(record.getPriority() == 0)
						dto.setPriorityString("Thông thường");*/
					NicTransaction nicTran = nicTransactionService.getNicTransactionById(record.getTransactionId()).getModel();
					if(nicTran!= null){
						dto.setNicSiteCode(nicTran.getNicSiteCode());
					}
//					if(StringUtils.isNotEmpty(assignmentFilter.getValidateInfoBca()) && assignmentFilter.getValidateInfoBca().equals("3")){
//						if(nicTransactionService.searchTransactionByLog(dto.getTransactionId(), "NIC_CONFIRMED_CONSULATE")){
//							joblstDto.add(dto);
//						}
//					}else{						
						joblstDto.add(dto);
//					}
				}
				pageResult.setRows(joblstDto);
				pageResult.setTotal((int) pr.getTotal());
				//pageResult.setTotal(joblstDto.size());
				pageResult.setPage(pr.getPage());
			}
		} catch (Exception ex) {
			logger.error("Error occurred while getting the investigation job list. Exception: " + ex.getMessage());
			throw new NicUploadJobServiceException(ex);
		}
		return pageResult;
	}
	
	@Override
	public PaginatedResult<NicUploadJobDto> findPendingPassportNo(String[] recordStatuses, String officerId,
			int pageNo, int pageSize, Boolean assignedOnly, AssignmentFilterAll assignmentFilter) throws NicUploadJobServiceException {
		List<NicUploadJobDto> joblstDto = new ArrayList<NicUploadJobDto>(); 
		PaginatedResult<NicUploadJob> pr = null;
		PaginatedResult<NicUploadJobDto> pageResult = new PaginatedResult<NicUploadJobDto>();
		try {
			// NicUploadJob nicUploadJob = new NicUploadJob();
			// nicUploadJob.setInvestigationOfficerId(userId);
			pr = dao.findPendingPassportNo(recordStatuses, officerId, pageNo, pageSize,   assignedOnly, assignmentFilter);
			// dao.findAllForPagination(nicUploadJob, pageNo,
			// pageSize);//dao.findAllInvestigationForPagination(nicUploadJob,
			// pageNo, pageSize);
			if (pr.getRows() != null) {
				List<NicUploadJob> jobList = pr.getRows();
				for (NicUploadJob record : jobList) {
					logger.info("Upload Job Id=============>>>>>>>>>>>>>>>>>>>" + record.getWorkflowJobId());
					NicUploadJobDto dto = new NicUploadJobDto();
					dto.setUploadJobId(record.getWorkflowJobId());
					dto.setTransactionId(record.getTransactionId());

					// CodeValues codeValue =
					// codesService.getCodeValueByIdName("TRANSACTION_TYPE",
					// record.getJobType());
					CodeValues codeValue = codesSevice.getCodeValueByIdName("JOB_TYPE", record.getJobType()); // 2014
				
					if (codeValue != null) {
						dto.setJobType(codeValue.getCodeValueDesc());
					} else {
						dto.setJobType(record.getJobType());
					}
					List<NicTransactionLog> log_ = transactionLogDao.findAllByRefIdAndTransStateList(record.getTransactionId(),null).getListModel();
					String logStr = "";
					if(log_ != null){
						for(NicTransactionLog l : log_){
							if(!StringUtils.isEmpty(l.getLogInfo()))
								logStr += l.getLogInfo().replaceAll("\\<.*?>","") + " | ";
								//replaceAll("\\<.*?>","") 
						}
					}
					dto.setNoteInvestigation(logStr);
					dto.setInvestigationType(record.getInvestigationType());
					dto.setJobCurrentState(record.getJobCurrentStage());
					dto.setInvestigationStatus(record.getInvestigationStatus());
					dto.setInvestigationOfficerId(record.getInvestigationOfficerId());
					dto.setJobUploadTime(record.getJobCreatedTime());

					joblstDto.add(dto);
				}
				pageResult.setRows(joblstDto);
				pageResult.setTotal((int) pr.getTotal());
				pageResult.setPage(pr.getPage());
			}
		} catch (Exception ex) {
			logger.error("Error occurred while getting the investigation job list. Exception: " + ex.getMessage());
			throw new NicUploadJobServiceException(ex);
		}
		return pageResult;
	}
	
	@Override
	public PaginatedResult<NicUploadJobDto> findAllPersoStatus(String[] recordStatuses, String officerId,
			int pageNo, int pageSize, Boolean assignedOnly, AssignmentFilterAll assignmentFilter) throws NicUploadJobServiceException {
		List<NicUploadJobDto> joblstDto = new ArrayList<NicUploadJobDto>(); 
		PaginatedResult<NicUploadJob> pr = null;
		PaginatedResult<NicUploadJobDto> pageResult = new PaginatedResult<NicUploadJobDto>();
		try {
			// NicUploadJob nicUploadJob = new NicUploadJob();
			// nicUploadJob.setInvestigationOfficerId(userId);
			pr = dao.findInvestigationJobForPaginationByOfficerId(recordStatuses, officerId, pageNo, pageSize,   assignedOnly, assignmentFilter);
			// dao.findAllForPagination(nicUploadJob, pageNo,
			// pageSize);//dao.findAllInvestigationForPagination(nicUploadJob,
			// pageNo, pageSize);
			if (pr.getRows() != null) {
				List<NicUploadJob> jobList = pr.getRows();
				for (NicUploadJob record : jobList) {
					logger.info("Upload Job Id=============>>>>>>>>>>>>>>>>>>>" + record.getWorkflowJobId());
					NicUploadJobDto dto = new NicUploadJobDto();
					dto.setUploadJobId(record.getWorkflowJobId());
					dto.setTransactionId(record.getTransactionId());

					// CodeValues codeValue =
					// codesService.getCodeValueByIdName("TRANSACTION_TYPE",
					// record.getJobType());
					CodeValues codeValue = codesSevice.getCodeValueByIdName("JOB_TYPE", record.getJobType()); // 2014
																														// Aug
																														// 20

					if (codeValue != null) {
						dto.setJobType(codeValue.getCodeValueDesc());
					} else {
						dto.setJobType(record.getJobType());
					}
					List<NicTransactionLog> log_ = transactionLogDao.findAllByRefIdAndTransStateList(record.getTransactionId(),null).getListModel();
					String logStr = "";
					if(log_ != null){
						for(NicTransactionLog l : log_){
							if(!StringUtils.isEmpty(l.getLogInfo()))
								logStr += l.getLogInfo().replaceAll("\\<.*?>","") + " | ";
								//replaceAll("\\<.*?>","") 
						}
					}
					dto.setNoteInvestigation(logStr);
					dto.setInvestigationType(record.getInvestigationType());
					dto.setJobCurrentState(record.getJobCurrentStage());
					dto.setInvestigationStatus(record.getInvestigationStatus());
					dto.setInvestigationOfficerId(record.getInvestigationOfficerId());
					dto.setJobUploadTime(record.getJobCreatedTime());

					joblstDto.add(dto);
				}
				pageResult.setRows(joblstDto);
				pageResult.setTotal((int) pr.getTotal());
				pageResult.setPage(pr.getPage());
			}
		} catch (Exception ex) {
			logger.error("Error occurred while getting the investigation job list. Exception: " + ex.getMessage());
			throw new NicUploadJobServiceException(ex);
		}
		return pageResult;
	}

//	@Override
//	@Transactional(rollbackFor = NicUploadJobServiceException.class, propagation = Propagation.REQUIRED)
//	public void updateHitDecision(List<InvestigationHitData> hits, String userName, String workstationId)
//			throws NicUploadJobServiceException, JobNoLongerAssignedToOfficerException {
//		 
//		NicUploadJob nicUploadJob = this.findById(Long.valueOf(hits.get(0).getUploadJobId()));
//		if (nicUploadJob != null) {
//			if (nicUploadJob.getInvestigationOfficerId() == null
//					|| nicUploadJob.getInvestigationOfficerId().compareToIgnoreCase(userName) != 0) {
//				throw new JobNoLongerAssignedToOfficerException();
//			}
//		}else{
//			throw new NicUploadJobServiceException("unexpected  ");
//		}
//
//		List<NicSearchResult> results = this.getAllSearchResult(Long.valueOf(hits.get(0).getUploadJobId()));
//		this.processDuplicateDecision( hits, userName);
//
//		/* approve/reject */
//		int numberOfTrueHits = 0;
//		for (InvestigationHitData hit : hits) {
//			if (hit.getDuplicateDecision().equalsIgnoreCase("Y")) {
//				numberOfTrueHits++;
//			}
//		}
//		
//		
//		if (numberOfTrueHits != 0 || this.has_noHit_entry(results)) {
//			this.suspendJob("Set To Suspended By System", Long.valueOf(hits.get(0).getUploadJobId()), userName,
//					workstationId);
//		} else {
//			this.approveJob("Auto Approved By System", Long.valueOf(hits.get(0).getUploadJobId()), userName,
//					workstationId);
//		}
//	}

//	private boolean has_noHit_entry(List<NicSearchResult> results) {
//
//		if (CollectionUtils.isEmpty(results)) {
//			return false;
//		}
//
//		for (NicSearchResult result : results) {
//			if (Collections.disjoint(Arrays.asList(new String[] { result.getTypeSearch() }),
//					NicSearchResult.TYPE_SEARCH__THAT_ARE_MAIN_CANDIDATE_BASED)) {
//			} else {
//				return true;
//			}
//		}
//		
//		return false;
//	}

//		for (NicSearchResult result : results) {
//			if (Collections.disjoint(Arrays.asList(new String[] { result.getTypeSearch() }),
//					NicSearchResult.TYPE_SEARCH__THAT_ARE_MAIN_CANDIDATE_BASED)) {
//			} else {
//				//[cw] 2016 Apr 26 check if the hit item need to route to FCM 
//				boolean requiredFCM = false;
//				if (CollectionUtils.isNotEmpty(result.getHitList())) {
//					for (NicSearchHitResult searchHitResult : result.getHitList()) {
//						if (StringUtils.isNotBlank(searchHitResult.getHitInfo())) {
//							String groupEntry = searchHitResult.getHitInfo();
//							logger.info("[{}] [{}] =========== Hit Info >>>>>>>> {} ", new Object[] { result.getTransactionId(), result.getTypeSearch(), groupEntry});
//							if (groupEntry.indexOf(NicSearchHitResult.hitInfo__SUB_ITEM__GROUP__DELIMITER) != -1) {
//								//Get List Code required to forward to Fraud Case Management 
//								List<CodeValues> codeValueList = codesService.findAllByCodeId(Codes.FCM_REQUIRED_SUB_ITEM);
//								String groupEntryDataGroup = StringUtils.substringAfter(groupEntry, NicSearchHitResult.hitInfo__SUB_ITEM__GROUP__DELIMITER);
//								
//								if (StringUtils.isNotBlank(groupEntryDataGroup)) {
//									String[] itemsLevel2 = groupEntryDataGroup.split(NicSearchHitResult.hitInfo__SUB_ITEM__GROUP_ITEM__DELIMITER);
//									
//									if (itemsLevel2!=null && itemsLevel2.length>0) {
//										loop: for (String groupEntryDataItem : itemsLevel2) {
//											for (CodeValues codeValue: codeValueList) {
//												//if item found in code list then set to route to FCM.
//												if (StringUtils.equals(groupEntryDataItem, codeValue.getId().getCodeValue())) {
//													requiredFCM = true;
//													logger.info("[{}] [{}] =========== Required Investigation item >>>>>>>> {} ", new Object[] { result.getTransactionId(), result.getTypeSearch(), groupEntryDataItem});
//													break loop;
//												}
//											}
//										}
//									}
//								}
//							}
//						}
//					}
//				}
//				if (requiredFCM) {
//					return true;
//				}
//				//[cw] 2016 Apr 26 check if the hit item need to route to FCM - end
//			}
//		}
//		
//		return false;
//	}
	

	@Transactional(rollbackFor = NicUploadJobServiceException.class, propagation = Propagation.REQUIRED)
	@Override
	public void approveJob(String remarks, long jobId, String officerUsername, String officerWorkstation)
			throws NicUploadJobServiceException {
		this.approveJob( remarks,  jobId,  officerUsername,  officerWorkstation,NicTransactionLog.TRANSACTION_STAGE_INVESTIGATION,NicTransactionLog.TRANSACTION_STATUS_NIC_APPROVED);
	}

	@Transactional(rollbackFor = NicUploadJobServiceException.class, propagation = Propagation.REQUIRED)
	@Override
	public void approveJob(String remarks, long jobId, String officerUsername, String officerWorkstation,String transactionStage,String transactionStatus)
			throws NicUploadJobServiceException, JobNoLongerAssignedToOfficerException {

		{
			LogInfoDTO logInfoDTO = new LogInfoDTO();
			logger.info("The job Id===========>>>>>>>>" + jobId);
			logger.info("The remarks ===============>>>>>>>>>>>>" + remarks); 
			NicUploadJob nicUploadJob = this.findById(jobId);
			if (nicUploadJob != null) {
				
				if (nicUploadJob.getInvestigationOfficerId() == null
						|| nicUploadJob.getInvestigationOfficerId().compareToIgnoreCase(officerUsername) != 0 && !nicUploadJob.getJobType().equals("LOS") && !officerUsername.equals("SYSTEM")) {
					throw new JobNoLongerAssignedToOfficerException();
				}
				
				String refId = nicUploadJob.getTransactionId();
				try {
					NicTransactionLog nicTransactionLog = this.getNicTransactionLog(refId, transactionStage,
							transactionStatus);
					if (nicTransactionLog != null) {
						if (remarks != null) {
							logInfoDTO.setRemark(remarks);
							nicTransactionLog.setLogInfo(logUtil.marshal(logInfoDTO));
						}
						this.saveNicTransactionLog(nicTransactionLog);
					} else {
						nicTransactionLog = new NicTransactionLog();
						nicTransactionLog.setRefId(refId);
						nicTransactionLog.setTransactionStage(transactionStage);
						nicTransactionLog.setTransactionStatus(transactionStatus);
						nicTransactionLog.setWkstnId(officerWorkstation);
						nicTransactionLog.setOfficerId(officerUsername);
						nicTransactionLog.setLogCreateTime(new Date());
						if (remarks != null) {
							logInfoDTO.setRemark(remarks);
							nicTransactionLog.setLogInfo(logUtil.marshal(logInfoDTO));
						}
						this.saveNicTransactionLog(nicTransactionLog);
					}
				} catch (NicUploadJobServiceException e) {
					e.printStackTrace();
				} catch (JaxbXmlConvertorException e) {
					e.printStackTrace();
					throw new NicUploadJobServiceException(e);
				}
			}
		}

		NicUploadJob transId = this.findById(jobId);
		if (transId != null) {
			String transactionId = transId.getTransactionId();
			this.updateOnApprove(transactionId, officerUsername, officerWorkstation);
		}

		this.approveJobId(jobId, officerUsername, officerWorkstation);
	}

	@Transactional(rollbackFor = NicUploadJobServiceException.class, propagation = Propagation.REQUIRED)
	@Override
	public void suspendJob(String remarks, long jobId, String officerUsername, String officerWorkstation, String transactionStage, String transactionStatus)
			throws NicUploadJobServiceException, JobNoLongerAssignedToOfficerException {

		{
			LogInfoDTO logInfoDTO = new LogInfoDTO();
			logger.info("The job Id===========>>>>>>>>" + jobId);
			logger.info("The remarks ===============>>>>>>>>>>>>" + remarks); 
			NicUploadJob nicUploadJob = this.findById(jobId);
			if (nicUploadJob != null) {
				  
				if (nicUploadJob.getInvestigationOfficerId() == null
						|| nicUploadJob.getInvestigationOfficerId().compareToIgnoreCase(officerUsername) != 0) {
					throw new JobNoLongerAssignedToOfficerException();
				} 
				  
				String refId = nicUploadJob.getTransactionId();
				try {
					NicTransactionLog nicTransactionLog = this.getNicTransactionLog(refId, transactionStage,
							transactionStatus);
					if (nicTransactionLog != null) {
						if (remarks != null) {
							logInfoDTO.setRemark(remarks);
							nicTransactionLog.setLogInfo(logUtil.marshal(logInfoDTO));
						}
						this.saveNicTransactionLog(nicTransactionLog);
					} else {
						nicTransactionLog = new NicTransactionLog();
						nicTransactionLog.setRefId(refId);
						nicTransactionLog.setTransactionStage(transactionStage);
						nicTransactionLog.setTransactionStatus(transactionStatus);
						nicTransactionLog.setWkstnId(officerWorkstation);
						nicTransactionLog.setOfficerId(officerUsername);
						nicTransactionLog.setLogCreateTime(new Date());
						if (remarks != null) {
							logInfoDTO.setRemark(remarks);
							nicTransactionLog.setLogInfo(logUtil.marshal(logInfoDTO));
						}
						this.saveNicTransactionLog(nicTransactionLog);
					}
				} catch (NicUploadJobServiceException e) {
					e.printStackTrace();
				} catch (JaxbXmlConvertorException e) {
					e.printStackTrace();
					throw new NicUploadJobServiceException(e);
				}
			}
		}

		NicUploadJob transId = this.findById(jobId);
		if (transId != null) {
			String transactionId = transId.getTransactionId();
			this.updateOnSuspend(transactionId, officerUsername, officerWorkstation);
		}

		this.suspendJobId(jobId, officerUsername, officerWorkstation);
	}

	@Transactional(rollbackFor = NicUploadJobServiceException.class, propagation = Propagation.REQUIRED)
	@Override
	public void rejectJob(String remarks, long jobId, String officerUsername, String officerWorkstation,
			String rejectedReason,
			String transactionStage, String transactionStatus) throws NicUploadJobServiceException, JobNoLongerAssignedToOfficerException { 

		{
			LogInfoDTO logInfoDTO = new LogInfoDTO();
			logger.info("The job Id===========>>>>>>>>" + jobId);
			logger.info("The remarks ===============>>>>>>>>>>>>" + remarks);
			NicUploadJob nicUploadJob = this.findById(jobId);
			if (nicUploadJob != null) {
				 
				if (nicUploadJob.getInvestigationOfficerId() == null
						|| nicUploadJob.getInvestigationOfficerId().compareToIgnoreCase(officerUsername) != 0) {
					throw new JobNoLongerAssignedToOfficerException();
				}
				 
				String refId = nicUploadJob.getTransactionId();
				try {
					NicTransactionLog nicTransactionLog = this.getNicTransactionLog(refId, transactionStage,
							transactionStatus);
					if (nicTransactionLog != null) {
						if (remarks != null) {
							logInfoDTO.setRemark(remarks);
							nicTransactionLog.setLogInfo(logUtil.marshal(logInfoDTO));
						}
						this.saveNicTransactionLog(nicTransactionLog);
					} else {
						nicTransactionLog = new NicTransactionLog();
						nicTransactionLog.setRefId(refId);
						nicTransactionLog.setTransactionStage(transactionStage);
						nicTransactionLog.setTransactionStatus(transactionStatus);
						nicTransactionLog.setWkstnId(officerWorkstation);
						nicTransactionLog.setOfficerId(officerUsername);
						nicTransactionLog.setLogCreateTime(new Date());
						if (remarks != null) {
							logInfoDTO.setRemark(remarks);
							nicTransactionLog.setLogInfo(logUtil.marshal(logInfoDTO));
						}
						this.saveNicTransactionLog(nicTransactionLog);
					}
				} catch (NicUploadJobServiceException e) {
					e.printStackTrace();
				} catch (JaxbXmlConvertorException e) {
					e.printStackTrace();
					throw new NicUploadJobServiceException(e);
				}
			}
		}

		NicUploadJob job = null;
		job = this.findById(jobId);
		if (job != null) {
			String transactionIdReject = job.getTransactionId();
			this.updateOnReject(transactionIdReject, officerUsername, officerWorkstation);
		}
		
		this.rejectJobId(jobId, officerUsername, officerWorkstation);

		if (job != null) {
			try {
				this.recordRejection(jobId, rejectedReason, remarks, officerUsername, job.getTransactionId());
			} catch (Exception e) {
				throw new NicUploadJobServiceException(e);
			}
		}
	}
	
	@Transactional(rollbackFor = NicUploadJobServiceException.class, propagation = Propagation.REQUIRED)
	@Override
	public void confirmDSQ(String remarks, long jobId, String officerUsername, String officerWorkstation,
			String rejectedReason,
			String transactionStage, String transactionStatus) throws NicUploadJobServiceException, JobNoLongerAssignedToOfficerException { 

		{
			LogInfoDTO logInfoDTO = new LogInfoDTO();
			logger.info("The job Id===========>>>>>>>>" + jobId);
			logger.info("The remarks ===============>>>>>>>>>>>>" + remarks);
			NicUploadJob nicUploadJob = this.findById(jobId);
			if (nicUploadJob != null) {
				 
				if (nicUploadJob.getInvestigationOfficerId() == null
						|| nicUploadJob.getInvestigationOfficerId().compareToIgnoreCase(officerUsername) != 0) {
					throw new JobNoLongerAssignedToOfficerException();
				}
				 
				String refId = nicUploadJob.getTransactionId();
				try {
					NicTransactionLog nicTransactionLog = this.getNicTransactionLog(refId, transactionStage,
							transactionStatus);
					if (nicTransactionLog != null) {
						if (remarks != null) {
							logInfoDTO.setRemark(remarks);
							nicTransactionLog.setLogInfo(logUtil.marshal(logInfoDTO));
						}
						this.saveNicTransactionLog(nicTransactionLog);
					} else {
						nicTransactionLog = new NicTransactionLog();
						nicTransactionLog.setRefId(refId);
						nicTransactionLog.setTransactionStage(transactionStage);
						nicTransactionLog.setTransactionStatus(transactionStatus);
						nicTransactionLog.setWkstnId(officerWorkstation);
						nicTransactionLog.setOfficerId(officerUsername);
						nicTransactionLog.setLogCreateTime(new Date());
						if (remarks != null) {
							logInfoDTO.setRemark(remarks);
							nicTransactionLog.setLogInfo(logUtil.marshal(logInfoDTO));
						}
						this.saveNicTransactionLog(nicTransactionLog);
					}
				} catch (NicUploadJobServiceException e) {
					e.printStackTrace();
				} catch (JaxbXmlConvertorException e) {
					e.printStackTrace();
					throw new NicUploadJobServiceException(e);
				}
			}
		}
	}

	@Override
	@Transactional(rollbackFor = NicUploadJobServiceException.class, propagation = Propagation.REQUIRED)
	public void recordRejection(long jobId, String rejectedReason, String remarks, String officerUsername,
			String transactionId) throws NicUploadJobServiceException {
		logger.info("recordRejection");

		try {
			this.nicRejectionDataDao.saveOrUpdate(new NicRejectionData(jobId, transactionId, officerUsername,
					new Date(), rejectedReason, remarks));
		} catch (Exception ex) {
			logger.error("Error occurred while recordRejection. Exception: " + ex.getMessage());
			throw new NicUploadJobServiceException(ex);
		}
	}

	@Override
	@Transactional(rollbackFor = java.lang.Throwable.class, propagation = Propagation.REQUIRED)
	public void updateOnSuspend(String transactionId, String userId, String wkstnId) {
		try {
			transactionDao.updateOnSuspend(transactionId, userId, wkstnId);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Error occurred while updating the transaction Status in NIC_TRANSACTION: " + e.getMessage());
		}
	}

	@Override
	@Transactional(rollbackFor = NicUploadJobServiceException.class, propagation = Propagation.REQUIRED)
	public void suspendJobId(long jobId, String userId, String wkstnId) throws NicUploadJobServiceException {
		try {
			dao.suspendJobId(jobId, userId, wkstnId);
		} catch (Exception ex) {
			logger.error("Error occurred while suspending the card. Exception: " + ex.getMessage(), ex);
			throw new NicUploadJobServiceException(ex);
		}

	}

	@Override
	@Transactional(rollbackFor = NicUploadJobServiceException.class, propagation = Propagation.REQUIRED)
	public void assignNewSuspendedJobList(String userid, String officerUserId, String officerWorkstationId)
			throws NicUploadJobServiceException {
		try {
			NicUploadJob job = dao.assignNewSuspendedJob(userid);
			this.logTransaction(job.getTransactionId(),
					NicTransactionLog.TRANSACTION_STAGE_FRAUD_CASE_MANAGEMENT_ASSIGN,  
					this.codesSevice.
					getCodeValueDescByIdName(Codes.INVESTIGATION_STATUS, job.getInvestigationStatus(), job.getInvestigationStatus()) ,
					officerUserId, officerWorkstationId,
					NicTransactionLog.TRANSACTION_STAGE_FRAUD_CASE_MANAGEMENT_ASSIGN,
					job.getWorkflowJobId().toString());
		} catch (Exception ex) {
			logger.error("Error occurred while getting the updated officer id list. Exception: " + ex.getMessage(), ex);
			throw new NicUploadJobServiceException(ex);
		}
	}

	@Override 
	public List<String> getAllSearchTypes(long jobId, String mainTransactionId, String hitTransactionId)
			throws NicUploadJobServiceException {
		try {
			return dao.getAllSearchTypes(jobId, mainTransactionId, hitTransactionId);
		} catch (Exception ex) {
			logger.error("Error occurred. Exception: " + ex.getMessage(), ex);
			throw new NicUploadJobServiceException(ex);
		}
	}

//	@Override 
//	public List<NicSearchHitResult> getAllSearchHitResult(String hitTransactionId) throws NicUploadJobServiceException {
//		try {
//			return dao.getAllSearchHitResult(hitTransactionId);
//		} catch (Exception ex) {
//			logger.error("Error occurred. Exception: " + ex.getMessage(), ex);
//			throw new NicUploadJobServiceException(ex);
//		}
//	}

//	@Override 
//	public List<NicSearchResult> getAllSearchResult(long jobId, String mainTransactionId, List<Long> searchResultIds)
//			throws NicUploadJobServiceException {
//		try {
//			return dao.getAllSearchResult(jobId, mainTransactionId, searchResultIds);
//		} catch (Exception ex) {
//			logger.error("Error occurred. Exception: " + ex.getMessage(), ex);
//			throw new NicUploadJobServiceException(ex);
//		}
//	}
 
	@Override 
	public List<SearchHitDto> getAllHitsForJobId(Long jobId)
			throws NicUploadJobServiceException { 
		 
		return this.getAllHitsForJobId(jobId, null, null,null);
	}
	 
	@Override 
	public List<SearchHitDto> getAllHitsForJobId(Long jobId, Boolean hitFlag, Boolean hitFlag_assumeSelfTransactionIdAsTrueHit, String mainTransactionId)
			throws NicUploadJobServiceException {
	    List<SearchHitDto> allHits = new ArrayList<SearchHitDto>( );
	    
		//get all saerch results
		List<NicSearchResult> results=this.getAllSearchResult__1_1_Y__1_N_N__excluded(jobId ); 
		
		// 29-Jan-2016 (Khang): Check null for results
		if (CollectionUtils.isNotEmpty(results)) {
    		//get all hit results for all search results
    		// List<NicSearchHitResult>  hits=this.filterBasedOn_hitFlag(this.getAllSearchHitResult(results ), hitFlag); 
    		List<NicSearchHitResult>  searchHitResultList = new ArrayList<NicSearchHitResult>(); 
    		List<String>  searchResult_typeSearchs = new ArrayList<String>(); 
            for (NicSearchResult result : results) {
                if (CollectionUtils.isNotEmpty(result.getHitList())) { 
                	for(NicSearchHitResult aHitResult  :result.getHitList()){
                         searchHitResultList.add (aHitResult);
                         searchResult_typeSearchs.add (result.getTypeSearch());
                	}  
                }
            }
            
            if (CollectionUtils.isNotEmpty(searchHitResultList)) {
                this.filterBasedOn_hitFlag(searchHitResultList,searchResult_typeSearchs, hitFlag,   hitFlag_assumeSelfTransactionIdAsTrueHit,   mainTransactionId); 
                
                //return
        		 
        		for(int i=0;i<searchHitResultList.size();i++){
        			allHits.add(new SearchHitDto(searchHitResultList.get(i).getTransactionIdHit(),searchHitResultList.get(i).getSearchResultId(),searchResult_typeSearchs.get(i),searchHitResultList.get(i).getHitInfo())); 
        		} 
        		
            }
    	} 
		return allHits;
	}
	 
	private void filterBasedOn_hitFlag(List<NicSearchHitResult> hits, List<String> typeSearchs, Boolean hitFlag,
			Boolean hitFlag_assumeSelfTransactionIdAsTrueHit, String mainTransactionId) {

		if (hits == null) {
			return;
		}

		if (hits.size() == 0) {
			return;
		}

		if (hitFlag == null) {
			return;
		}

		for (int i = hits.size() - 1; i >= 0; i--) {
			Boolean verifyDecision = hits.get(i).getVerifyDecision();
			String transactionIdHit = hits.get(i).getTransactionIdHit();
			if (hitFlag) {
				if ((verifyDecision != null && verifyDecision)) {
				} else if ((hitFlag_assumeSelfTransactionIdAsTrueHit != null && hitFlag_assumeSelfTransactionIdAsTrueHit
						&& mainTransactionId.equals(transactionIdHit))) {
				} else {
					hits.remove(i);
					typeSearchs.remove(i);
				}
			}
			if (!hitFlag) {
				if (!(verifyDecision == null || !verifyDecision
						|| (hitFlag_assumeSelfTransactionIdAsTrueHit != null
								&& !hitFlag_assumeSelfTransactionIdAsTrueHit
								&& mainTransactionId.equals(transactionIdHit)))) {
					hits.remove(i);
					typeSearchs.remove(i);
				}
			}
		}
	}
	 
	@Override
	public List<SearchHitDto> getAllHitsForJobId_1PerHitTransactionId_anySearchResultId(Long jobId)
			throws NicUploadJobServiceException {

		return this.getAllHitsForJobId_1PerHitTransactionId_anySearchResultId(jobId, null, null,null); 
	}
	 
	@Override
	public List<SearchHitDto> getAllHitsForJobId_1PerHitTransactionId_anySearchResultId(Long jobId, Boolean hitFlag)
			throws NicUploadJobServiceException {

		return this.getAllHitsForJobId_1PerHitTransactionId_anySearchResultId(jobId, hitFlag, null,null); 
	}
 
	@Override
	public List<SearchHitDto> getAllHitsForJobId_1PerHitTransactionId_anySearchResultId(Long jobId, Boolean hitFlag, Boolean hitFlag_assumeSelfTransactionIdAsTrueHit, String mainTransactionId)
			throws NicUploadJobServiceException {

		return this.removeDupSearchResultId(this.sortAccordingToTransactionIdAndSearchResultId(this.getAllHitsForJobId(jobId, hitFlag, hitFlag_assumeSelfTransactionIdAsTrueHit,   mainTransactionId))); 
	}

	private List<SearchHitDto> removeDupSearchResultId(List<SearchHitDto> hits) {

		// remove uncomparables
		for (int i = hits.size() - 1; i >= 0; i--) {
			if (hits.get(i).getHitTransactionId() == null || hits.get(i).getHitTransactionId().trim().length() == 0
					|| hits.get(i).getSearchResultId() == null) {
				hits.remove(i);
			}
		}

		// sort
		if (hits.size() > 1) {
			for (int i = hits.size() - 2; i >= 0; i--) {
				if (hits.get(i).getHitTransactionId().compareTo(hits.get(i + 1).getHitTransactionId()) == 0) {
					hits.remove(i + 1);
				}
			}
		}

		return hits;
	}

	private List<SearchHitDto> sortAccordingToTransactionIdAndSearchResultId(List<SearchHitDto> hits)
			  {

		// remove uncomparables
		for (int i = hits.size() - 1; i >= 0; i--) {
			if (hits.get(i).getHitTransactionId() == null || hits.get(i).getHitTransactionId().trim().length() == 0
					|| hits.get(i).getSearchResultId() == null) {
				hits.remove(i);
			}
		}

		// sort
		if (hits.size() >= 2) {
			boolean switchHappened = true;
			while (switchHappened) {
				switchHappened = false;
				for (int i = 0; i <= hits.size() - 2; i++) {
					SearchHitDto item0 = hits.get(i);
					SearchHitDto item1 = hits.get(i + 1);
					if (item0.getHitTransactionId().compareTo(item1.getHitTransactionId()) < 0) {
						continue;
					}
					if (item0.getHitTransactionId().compareTo(item1.getHitTransactionId()) > 0) {
						hits.set(i, item1);
						hits.set(i + 1, item0);
						switchHappened = true;
						continue;
					}
					if (item0.getSearchResultId().longValue() > item1.getSearchResultId().longValue()) {
						hits.set(i, item1);
						hits.set(i + 1, item0);
						switchHappened = true;
						continue;
					}
				}
			}
		}

		return hits;
	} 

	@Override
	public List<NicSearchResult> getAllSearchResult(Long jobId) throws NicUploadJobServiceException {
		try {
			return dao.getAllSearchResult(jobId);
		} catch (Exception ex) {
			logger.error("Error occurred. Exception: " + ex.getMessage(), ex);
			throw new NicUploadJobServiceException(ex);
		}
	}

	@Override
	public List<NicSearchHitResult> getAllSearchHitResult(List<NicSearchResult> results)
			throws NicUploadJobServiceException {
		try {
			return dao.getAllSearchHitResult(results);
		} catch (Exception ex) {
			logger.error("Error occurred. Exception: " + ex.getMessage(), ex);
			throw new NicUploadJobServiceException(ex);
		}
	}
	
	@Override
	@Transactional(rollbackFor = NicUploadJobServiceException.class, propagation = Propagation.REQUIRED)
	public void updateDataOnTrueHit(String hitTransactionId, List<Long> searchResultIds, String userId, String remarks)
			throws NicUploadJobServiceException {
		try {
			searchHitResultDao.updateDataOnTrueHit(hitTransactionId, searchResultIds, userId, remarks);
		} catch (Exception ex) {
			logger.error("Error occurred while updating the NIC_SEARCH_HIT_TABLE table. Exception: " + ex.getMessage());
			throw new NicUploadJobServiceException(ex);
		}
	}

	@Override
	@Transactional(rollbackFor = NicUploadJobServiceException.class, propagation = Propagation.REQUIRED)
	public void updateDataOnFalseHit(String hitTransactionId, List<Long> searchResultIds, String userId, String remarks)
			throws NicUploadJobServiceException {
		try {
			searchHitResultDao.updateDataOnFalseHit(hitTransactionId, searchResultIds, userId, remarks);
		} catch (Exception ex) {
			logger.error("Error occurred while updating the NIC_SEARCH_HIT_TABLE table-- False Hit. Exception: "
					+ ex.getMessage());
			throw new NicUploadJobServiceException(ex);
		}
	}

	@Override
	public PaginatedResult<NicUploadJobDto> findTransactions(String txnId, String owner, List<String> statuses)
			throws NicUploadJobServiceException {
		//PaginatedResult<NicUploadJob> result = null;
		//modified
		List<NicUploadJobDto> joblstDto = new ArrayList<NicUploadJobDto>();
		PaginatedResult<NicUploadJob> result = null;
		PaginatedResult<NicUploadJobDto> pageResult = new PaginatedResult<NicUploadJobDto>();
		new PaginatedResult<NicUploadJobDto>();
		try {
			result = dao.findTransactions(txnId, owner,  statuses);
			//modified
			if (result.getRows() != null) {
				List<NicUploadJob> jobList = result.getRows();
				for (NicUploadJob record : jobList) {
					NicUploadJobDto dto = new NicUploadJobDto();
					dto.setUploadJobId(record.getWorkflowJobId());
					dto.setTransactionId(record.getTransactionId());
					
					//CodeValues codeValue = codesService.getCodeValueByIdName("TRANSACTION_TYPE", record.getJobType());
					CodeValues codeValue = codesSevice.getCodeValueByIdName("JOB_TYPE", record.getJobType()); //2014 Aug 20
					
					if(codeValue!=null){
						dto.setJobType(codeValue.getCodeValueDesc());
					}else{
						dto.setJobType(record.getJobType());
					}
					
					dto.setInvestigationType(record.getInvestigationType());
					dto.setJobCurrentState(record.getJobCurrentStage());
					dto.setInvestigationStatus(record.getInvestigationStatus());
					dto.setInvestigationOfficerId(record.getInvestigationOfficerId());
					//dto.setJobUploadTime(record.getJobUploadTime());
					
					joblstDto.add(dto);
				}
				pageResult.setRows(joblstDto);
				pageResult.setTotal((int) result.getTotal());
				pageResult.setPage(result.getPage());
				}
				
		} catch (Exception ex) {
			logger.error("Error occurred while getting the job search list. Exception: "
					+ ex.getMessage());
			throw new NicUploadJobServiceException(ex);
		}
		return pageResult;
	}

	@Override
	public List<NicSearchHitResult> getAllSearchHitResult(Long jobId, String hitTransactionId, List<String> searchTypes,NicSearchHitResultHitScorer scorer)
			throws NicUploadJobServiceException {
		try {
			return this.orderFromHighestMatchToLowest(dao.getAllSearchHitResult(jobId, hitTransactionId, searchTypes),scorer);
		} catch (Exception ex) {
			logger.error("Error occurred. Exception: " + ex.getMessage(), ex);
			throw new NicUploadJobServiceException(ex);
		}
	}

	private List<NicSearchHitResult> orderFromHighestMatchToLowest(List<NicSearchHitResult> hits,
			NicSearchHitResultHitScorer scorer) {

		if (scorer == null) {
			return hits;
		}

		if (hits == null || hits.size() <= 1) {
			return hits;
		}

		boolean switchDone = true;
		while (switchDone) {
			switchDone = false;
			for (int i = hits.size() - 2; i >= 0; i--) {
				NicSearchHitResult item0 = hits.get(i);
				NicSearchHitResult item1 = hits.get(i + 1);
				if (scorer.getScore(item0) < scorer.getScore(item1)) {
					hits.set(i, item1);
					hits.set(i + 1, item0);
					switchDone = true;
				}
			}
		}

		return hits;
	}
	 

	@Override
	public List<NicSearchResult> getAllSearchResult(Long jobId, List<String> searchTypes)
			throws NicUploadJobServiceException {
		try {
			return dao.getAllSearchResult(jobId, searchTypes);
		} catch (Exception ex) {
			logger.error("Error occurred. Exception: " + ex.getMessage(), ex);
			throw new NicUploadJobServiceException(ex);
		}
	}

	@Override
	@Transactional(rollbackFor = NicUploadJobServiceException.class, propagation = Propagation.REQUIRED)
	public void unassignInvestigationJob(long jobId, String officerUserId, String officerWorkstationId) throws NicUploadJobServiceException {

		try {
			dao.unassignInvestigationJob(jobId,   officerUserId,   officerWorkstationId);
		} catch (Exception ex) {
			logger.error("Error occurred. Exception: " + ex.getMessage(), ex);
			throw new NicUploadJobServiceException(ex);
		}
	}

	@Override
	@Transactional(rollbackFor = NicUploadJobServiceException.class, propagation = Propagation.REQUIRED)
	public void unassignInvestigationAllJobsForUser(String userId, String officerUserId, String officerWorkstationId) throws NicUploadJobServiceException {

		try {
			dao.unassignInvestigationAllJobsForUser(userId,   officerUserId,   officerWorkstationId);
		} catch (Exception ex) {
			logger.error("Error occurred. Exception: " + ex.getMessage(), ex);
			throw new NicUploadJobServiceException(ex);
		}
	}

	@Override
	@Transactional(rollbackFor = NicUploadJobServiceException.class, propagation = Propagation.REQUIRED)
	public void unassignSuspendedInvestigationJob(long jobId, String officerUserId, String officerWorkstationId) throws NicUploadJobServiceException {

		try {
			dao.unassignSuspendedInvestigationJob(jobId,   officerUserId,   officerWorkstationId);
		} catch (Exception ex) {
			logger.error("Error occurred. Exception: " + ex.getMessage(), ex);
			throw new NicUploadJobServiceException(ex);
		}
	}

	@Override
	@Transactional(rollbackFor = NicUploadJobServiceException.class, propagation = Propagation.REQUIRED)
	public void unassignSuspendedInvestigationAllJobsForUser(String userId, String officerUserId, String officerWorkstationId) throws NicUploadJobServiceException {

		try {
			dao.unassignSuspendedInvestigationAllJobsForUser(userId,   officerUserId,   officerWorkstationId);
		} catch (Exception ex) {
			logger.error("Error occurred. Exception: " + ex.getMessage(), ex);
			throw new NicUploadJobServiceException(ex);
		}
	}

	@Override
	public PaginatedResult<NicUploadJob> findAllForPagination(PageRequest pageRequest, NicUploadJob nicUploadJob,
			String[] investStatus, Long jobId, String siteCode, boolean isShowErrTrans)
			throws NicUploadJobServiceException {
		try {
			int pageNo = pageRequest.getPageNo();
			int pageSize = pageRequest.getMaxRecords();
			boolean ignoreCase = false;
			boolean enableLike = false;
			Order order = null;
			if(StringUtils.equals(pageRequest.getSortorder(),"asc")){
				order = Order.asc(pageRequest.getSortname());
			} else {
				order = Order.desc(pageRequest.getSortname());
			}
			return dao.findAllForPagination(nicUploadJob, investStatus, jobId, siteCode, isShowErrTrans, pageNo, pageSize, ignoreCase, enableLike, order);
		} catch (Exception e) {
			throw new NicUploadJobServiceException(e);
		}
	}
	
	@Override
	public PaginatedResult<NicUploadJob> findAllForPagination(PageRequest pageRequest, NicUploadJob nicUploadJob, NicTransaction nicTransaction,
			String[] investStatus, Long jobId, boolean isShowErrTrans, Date dateFrom, Date dateTo)
			throws NicUploadJobServiceException {
		try {
			int pageNo = pageRequest.getPageNo();
			int pageSize = pageRequest.getMaxRecords();
			boolean ignoreCase = false;
			boolean enableLike = false;
			Order order = null;
			if(StringUtils.equals(pageRequest.getSortorder(),"asc")){
				order = Order.asc(pageRequest.getSortname());
			} else {
				order = Order.desc(pageRequest.getSortname());
			}
			return dao.findAllForPagination(nicUploadJob, nicTransaction, investStatus, jobId, isShowErrTrans, dateFrom, dateTo, pageNo, pageSize, ignoreCase, enableLike, order);
		} catch (Exception e) {
			throw new NicUploadJobServiceException(e);
		}
	}
 
	 private List<NicSearchResult> getAllSearchResult__1_1_Y__1_N_N__excluded(Long jobId) throws NicUploadJobServiceException {
		try {
			return dao.getAllSearchResult__1_1_Y__1_N_N__excluded(jobId); 
		} catch (Exception ex) {
			logger.error("Error occurred. Exception: " + ex.getMessage(), ex);
			throw new NicUploadJobServiceException(ex);
		}
	 }
	 
	 public void assigmentDemo(List<Long> jobIds, String assignToId, String officerUserId,
				String officerWorkstationId){
		 try {
				//dao.unassignInvestigationJobs(jobIds, officerUserId, officerWorkstationId); 31/01/2018 - TRUNG TẠM ĐÓNG ĐỂ BẢN GHI KO DÍNH INVES DUYỆT ĐƯỢC
				dao.assignInvestigationJobs(jobIds, assignToId, officerUserId, officerWorkstationId);
			} catch (Exception ex) {
				logger.error("Error occurred. Exception: " + ex.getMessage(), ex);
			}
	 }

	@Override
	@Transactional(rollbackFor = NicUploadJobServiceException.class, propagation = Propagation.REQUIRED)
	public void unassignInvestigationJobs(List<Long> jobIds, String officerUserId, String officerWorkstationId)
			throws NicUploadJobServiceException {

		try { 
			dao.unassignInvestigationJobs(jobIds, officerUserId, officerWorkstationId); 
		} catch (Exception ex) {
			logger.error("Error occurred. Exception: " + ex.getMessage(), ex);
			throw new NicUploadJobServiceException(ex);
		}
	}

	@Override
	@Transactional(rollbackFor = NicUploadJobServiceException.class, propagation = Propagation.REQUIRED)
	public void assignInvestigationJobs(List<Long> jobIds, String assignToId, String officerUserId,
			String officerWorkstationId) throws NicUploadJobServiceException {

		try {
			//dao.unassignInvestigationJobs(jobIds, officerUserId, officerWorkstationId); 31/01/2018 - TRUNG TẠM ĐÓNG ĐỂ BẢN GHI KO DÍNH INVES DUYỆT ĐƯỢC
			dao.assignInvestigationJobs(jobIds, assignToId, officerUserId, officerWorkstationId);
		} catch (Exception ex) {
			logger.error("Error occurred. Exception: " + ex.getMessage(), ex);
			throw new NicUploadJobServiceException(ex);
		}
	}
	
	@Override
	@Transactional(rollbackFor = NicUploadJobServiceException.class, propagation = Propagation.REQUIRED)
	public void assignInvestigationOnlyJob(long jobIds, String assignToId, String officerUserId,
			String officerWorkstationId) throws NicUploadJobServiceException {

		try {
			//dao.unassignInvestigationJobs(jobIds, officerUserId, officerWorkstationId); 31/01/2018 - TRUNG TẠM ĐÓNG ĐỂ BẢN GHI KO DÍNH INVES DUYỆT ĐƯỢC
			dao.assignInvestigationOnlyJob(jobIds, assignToId, officerUserId, officerWorkstationId);
		} catch (Exception ex) {
			logger.error("Error occurred. Exception: " + ex.getMessage(), ex);
			throw new NicUploadJobServiceException(ex);
		}
	}

	@Override
	@Transactional(rollbackFor = NicUploadJobServiceException.class, propagation = Propagation.REQUIRED)
	public void unassignSuspendedInvestigationJobs(List<Long> jobIds, String officerUserId, String officerWorkstationId)
			throws NicUploadJobServiceException {

		try { 
			dao.unassignSuspendedInvestigationJobs(jobIds, officerUserId, officerWorkstationId); 
		} catch (Exception ex) {
			logger.error("Error occurred. Exception: " + ex.getMessage(), ex);
			throw new NicUploadJobServiceException(ex);
		}
	}

	@Override
	@Transactional(rollbackFor = NicUploadJobServiceException.class, propagation = Propagation.REQUIRED)
	public void assignSuspendedInvestigationJobs(List<Long> jobIds, String assignToId, String officerUserId,
			String officerWorkstationId) throws NicUploadJobServiceException {

		try {
			dao.unassignSuspendedInvestigationJobs(jobIds, officerUserId, officerWorkstationId);
			dao.assignSuspendedInvestigationJobs(jobIds, assignToId, officerUserId, officerWorkstationId);
		} catch (Exception ex) {
			logger.error("Error occurred. Exception: " + ex.getMessage(), ex);
			throw new NicUploadJobServiceException(ex);
		}
	}

	@Override
	@Transactional(rollbackFor = NicUploadJobServiceException.class, propagation = Propagation.REQUIRED)
	public void rejectApplication(List<InvestigationHitData> hits, String remarks, String userName,
			String workstationId, String transactionStage, String transactionStatus) throws NicUploadJobServiceException, JobNoLongerAssignedToOfficerException {

		/* is this job still assigned to me */
		NicUploadJob nicUploadJob = this.findById(Long.valueOf(hits.get(0).getUploadJobId()));
		if (nicUploadJob != null) {
			if (nicUploadJob.getInvestigationOfficerId() == null
					|| nicUploadJob.getInvestigationOfficerId().compareToIgnoreCase(userName) != 0) {
				throw new JobNoLongerAssignedToOfficerException();
			}
		} else {
			throw new NicUploadJobServiceException("unexpected  ");
		}
 
		this.processDuplicateDecision( hits, userName);

		/* reject */
		this.rejectJob(remarks, Long.valueOf(hits.get(0).getUploadJobId()), userName, workstationId,
				NicRejectionData.rejectReason_investigation,   transactionStage,   transactionStatus);
	}

	@Override
	@Transactional(rollbackFor = NicUploadJobServiceException.class, propagation = Propagation.REQUIRED)
	public void suspendApplication(List<InvestigationHitData> hits, String remarks, String userName,
			String workstationId,
			String transactionStage, String transactionStatus) throws NicUploadJobServiceException, JobNoLongerAssignedToOfficerException {

		/* is this job still assigned to me */
		NicUploadJob nicUploadJob = this.findById(Long.valueOf(hits.get(0).getUploadJobId()));
		if (nicUploadJob != null) {
			if (nicUploadJob.getInvestigationOfficerId() == null
					|| nicUploadJob.getInvestigationOfficerId().compareToIgnoreCase(userName) != 0) {
				throw new JobNoLongerAssignedToOfficerException();
			}
		} else {
			throw new NicUploadJobServiceException("unexpected  ");
		}
 
		this.processDuplicateDecision( hits, userName);
 
		this.suspendJob(remarks, Long.valueOf(hits.get(0).getUploadJobId()), userName, workstationId ,
				  transactionStage,   transactionStatus);
	}

	@Override
	@Transactional(rollbackFor = NicUploadJobServiceException.class, propagation = Propagation.REQUIRED)
	public void approveApplication(List<InvestigationHitData> hits, String remarks, String userName,
			String workstationId, String transactionStage, String transactionStatus)
					throws NicUploadJobServiceException, JobNoLongerAssignedToOfficerException {

		/* is this job still assigned to me */
		NicUploadJob nicUploadJob = this.findById(Long.valueOf(hits.get(0).getUploadJobId()));
		if (nicUploadJob != null) {
			if (nicUploadJob.getInvestigationOfficerId() == null
					|| nicUploadJob.getInvestigationOfficerId().compareToIgnoreCase(userName) != 0) {
				throw new JobNoLongerAssignedToOfficerException();
			}
		} else {
			throw new NicUploadJobServiceException("unexpected  ");
		}

		this.processDuplicateDecision(hits, userName);

		this.approveJob(remarks, Long.valueOf(hits.get(0).getUploadJobId()), userName, workstationId, transactionStage,
				transactionStatus);

		try {
			List<Passport> items = hits.get(0).getPassportsToCancel_asList();
			for (Passport item : items) {
				this.nicTransactionService.updateDocByStatus(item.getTransactionId(),
						item.getPassportNo(), TransactionStatus.Cancelled,   userName,
						  workstationId, transactionStage);
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new NicUploadJobServiceException("calling nicTransactionService.updateDocByStatus");
		}
	}

	private void processDuplicateDecision(List<InvestigationHitData> hits, String userName)
			throws NicUploadJobServiceException {

		List<NicSearchResult> results = this.getAllSearchResult(Long.valueOf(hits.get(0).getUploadJobId()));
		List<Long> searchResultIds = new ArrayList<Long>();
		for (NicSearchResult result : results) {
			searchResultIds.add(result.getSearchResultId());
		}
		for (InvestigationHitData hit : hits) {
			if (hit.getDuplicateDecision().equalsIgnoreCase("Y")) {
				this.updateDataOnTrueHit(hit.getHitTransactionId(), searchResultIds, userName, hit.getRemarks());
			} else {
				this.updateDataOnFalseHit(hit.getHitTransactionId(), searchResultIds, userName, hit.getRemarks());
			}
		}
	}
	
	@Override
	public List<NicUploadJob> findAllByTransactionId(String txnId) throws Exception {
		return this.dao.findAllByTransactionId(txnId).getListModel();
	}
	
	@Override
	public List<NicUploadJob> findAllByPersonID(long id) throws Exception {
		return this.dao.findAllByPersonID(id);
	}
	
	@Override
	public List<NicUploadJob> findAllByPersonCode(String code) throws Exception {
		return this.dao.findAllByPersonCode(code);
	}
	
	/*tunt*/
	public PaginatedResult<NicUploadJobDto> findAllForApprovePagination(String officerId,
			int pageNo, int pageSize, Boolean assignedOnly, AssignmentFilter assignmentFilter) throws NicUploadJobServiceException {
		List<NicUploadJobDto> joblstDto = new ArrayList<NicUploadJobDto>(); 
		PaginatedResult<NicUploadJob> pr = null;
		PaginatedResult<NicUploadJobDto> pageResult = new PaginatedResult<NicUploadJobDto>();
		try {
			// NicUploadJob nicUploadJob = new NicUploadJob();
			// nicUploadJob.setInvestigationOfficerId(userId);
			pr = dao.findApproveJobForPaginationByOfficerId(officerId, pageNo, pageSize,   assignedOnly, assignmentFilter);
			// dao.findAllForPagination(nicUploadJob, pageNo,
			// pageSize);//dao.findAllInvestigationForPagination(nicUploadJob,
			// pageNo, pageSize);
			if (pr.getRows() != null) {
				List<NicUploadJob> jobList = pr.getRows();
				for (NicUploadJob record : jobList) {
					logger.info("Upload Job Id=============>>>>>>>>>>>>>>>>>>>" + record.getWorkflowJobId());
					NicUploadJobDto dto = new NicUploadJobDto();
					dto.setUploadJobId(record.getWorkflowJobId());
					dto.setTransactionId(record.getTransactionId());

					// CodeValues codeValue =
					// codesService.getCodeValueByIdName("TRANSACTION_TYPE",
					// record.getJobType());
					CodeValues codeValue = codesSevice.getCodeValueByIdName("JOB_TYPE", record.getJobType()); // 2014

					if (codeValue != null) {
						dto.setJobType(codeValue.getCodeValueDesc());
					} else {
						dto.setJobType(record.getJobType());
					}
					dto.setJobApproveStatus(record.getJobApproveStatus());
					dto.setInvestigationType(record.getInvestigationType());
					dto.setJobCurrentState(record.getJobCurrentStage());
					dto.setInvestigationStatus(record.getInvestigationStatus());
					dto.setInvestigationOfficerId(record.getInvestigationOfficerId());
					dto.setJobUploadTime(record.getJobCreatedTime());

					joblstDto.add(dto);
				}
				pageResult.setRows(joblstDto);
				pageResult.setTotal((int) pr.getTotal());
				pageResult.setPage(pr.getPage());
			}
		} catch (Exception ex) {
			logger.error("Error occurred while getting the investigation job list. Exception: " + ex.getMessage());
			throw new NicUploadJobServiceException(ex);
		}
		return pageResult;
	}
	
	public PaginatedResult<NicUploadJobDto> findAllForApprovePaginationALL(String officerId,
			int pageNo, int pageSize, Boolean assignedOnly, AssignmentFilter assignmentFilter) throws NicUploadJobServiceException {
		List<NicUploadJobDto> joblstDto = new ArrayList<NicUploadJobDto>(); 
		PaginatedResult<NicUploadJob> pr = null;
		PaginatedResult<NicUploadJobDto> pageResult = new PaginatedResult<NicUploadJobDto>();
		try {
			// NicUploadJob nicUploadJob = new NicUploadJob();
			// nicUploadJob.setInvestigationOfficerId(userId);
			pr = dao.findApproveJobForPaginationByOfficerIdALL(officerId, pageNo, pageSize,   assignedOnly, assignmentFilter);
			// dao.findAllForPagination(nicUploadJob, pageNo,
			// pageSize);//dao.findAllInvestigationForPagination(nicUploadJob,
			// pageNo, pageSize);
			if (pr.getRows() != null) {
				List<NicUploadJob> jobList = pr.getRows();
				for (NicUploadJob record : jobList) {
//					logger.info("Upload Job Id=============>>>>>>>>>>>>>>>>>>>" + record.getWorkflowJobId());
					NicUploadJobDto dto = new NicUploadJobDto();
					dto.setUploadJobId(record.getWorkflowJobId());
					dto.setTransactionId(record.getTransactionId());

					// CodeValues codeValue =
					// codesService.getCodeValueByIdName("TRANSACTION_TYPE",
					// record.getJobType());
					CodeValues codeValue = codesSevice.getCodeValueByIdName("JOB_TYPE", record.getJobType()); // 2014

					if (codeValue != null) {
						dto.setJobType(codeValue.getCodeValueDesc());
					} else {
						dto.setJobType(record.getJobType());
					}
					dto.setJobApproveStatus(record.getJobApproveStatus());
					dto.setInvestigationType(record.getInvestigationType());
					dto.setJobCurrentState(record.getJobCurrentStage());
					dto.setInvestigationStatus(record.getInvestigationStatus());
					dto.setInvestigationOfficerId(record.getInvestigationOfficerId());
					dto.setJobUploadTime(record.getJobCreatedTime());

					joblstDto.add(dto);
				}
				pageResult.setRows(joblstDto);
				pageResult.setTotal((int) pr.getTotal());
				pageResult.setPage(pr.getPage());
			}
		} catch (Exception ex) {
			logger.error("Error occurred while getting the investigation job list. Exception: " + ex.getMessage());
			throw new NicUploadJobServiceException(ex);
		}
		return pageResult;
	}
	
	@Override
	public void approveJobStatus(long jobId, String officerUsername, String officerWorkstation)
			throws NicUploadJobServiceException {
		this.approveJobStatus(jobId,  officerUsername,  officerWorkstation,NicTransactionLog.TRANSACTION_STAGE_INVESTIGATION,NicTransactionLog.TRANSACTION_STATUS_NIC_APPROVED);
	}
	
	@Transactional(rollbackFor = NicUploadJobServiceException.class, propagation = Propagation.REQUIRED)
	@Override
	public void approveJobStatus( long jobId, String officerUsername, String officerWorkstation,String transactionStage,String transactionStatus)
			throws NicUploadJobServiceException, JobNoLongerAssignedToOfficerException {

		
			//LogInfoDTO logInfoDTO = new LogInfoDTO();
			logger.info("The job Id===========>>>>>>>>" + jobId);
		
			/*NicUploadJob nicUploadJob = this.findById(jobId);
			if (nicUploadJob != null) {
				
				/*if (nicUploadJob.getInvestigationOfficerId() == null
						|| nicUploadJob.getInvestigationOfficerId().compareToIgnoreCase(officerUsername) != 0) {
					throw new JobNoLongerAssignedToOfficerException();
				}
				
				String refId = nicUploadJob.getTransactionId();
				try {
					NicTransactionLog nicTransactionLog = this.getNicTransactionLog(refId, transactionStage,
							transactionStatus);
					if (nicTransactionLog != null) {
						
						this.saveNicTransactionLog(nicTransactionLog);
					} else {
						nicTransactionLog = new NicTransactionLog();
						nicTransactionLog.setRefId(refId);
						nicTransactionLog.setTransactionStage(transactionStage);
						nicTransactionLog.setTransactionStatus(transactionStatus);
						nicTransactionLog.setWkstnId(officerWorkstation);
						nicTransactionLog.setOfficerId(officerUsername);
						nicTransactionLog.setLogCreateTime(new Date());
						
						this.saveNicTransactionLog(nicTransactionLog);
					}
				} catch (NicUploadJobServiceException e) {
					e.printStackTrace();
				} catch (Exception e) {
					e.printStackTrace();
					throw new NicUploadJobServiceException(e);
				}
			}
		}*/

		//NicUploadJob transId = this.findById(jobId);*/
		/*(if (transId != null) {
			String transactionId = transId.getTransactionId();
			this.updateOnApprove(transactionId, officerUsername, officerWorkstation);
		}*/
		logger.error("tunt: approveJobId--------");
		this.approveJobIdStatus(jobId, officerUsername, officerWorkstation);
	}
	
	@Override
	@Transactional(rollbackFor= NicUploadJobServiceException.class ,propagation = Propagation.REQUIRED)
	public void approveJobIdStatus(long approveJobId, String userId, String wkstnId)
			throws NicUploadJobServiceException {
		try {
			dao.approveJobIdStatus(approveJobId, userId, wkstnId);
		} catch (Exception ex) {
			logger.error("Error occurred while approving the card. Exception: "	+ ex.getMessage(), ex);
			throw new NicUploadJobServiceException(ex);
		}

	}
	
	@Transactional(rollbackFor = NicUploadJobServiceException.class, propagation = Propagation.REQUIRED)
	@Override
	public void approveJobStatus_Confirmed(long approveJobId, String userId, String wkstnId ,String transactionStage,String transactionStatus, String investStatus)
			throws NicUploadJobServiceException {
		try {
			NicUploadJob nicUploadJob = this.findById(approveJobId);
			if (nicUploadJob != null) {
				
				if ((nicUploadJob.getInvestigationOfficerId() == null
						|| nicUploadJob.getInvestigationOfficerId().compareToIgnoreCase(userId) != 0) && !nicUploadJob.getJobType().equals("LOS") && !userId.equals("SYSTEM")) {
					throw new JobNoLongerAssignedToOfficerException();
				}
				
				String refId = nicUploadJob.getTransactionId();
				try {
					NicTransactionLog nicTransactionLog = this.getNicTransactionLog(refId, transactionStage,
							transactionStatus);
					if (nicTransactionLog != null) {
						
						this.saveNicTransactionLog(nicTransactionLog);
					} else {
						nicTransactionLog = new NicTransactionLog();
						nicTransactionLog.setRefId(refId);
						nicTransactionLog.setTransactionStage(transactionStage);
						nicTransactionLog.setTransactionStatus(transactionStatus);
						nicTransactionLog.setWkstnId(wkstnId);
						nicTransactionLog.setOfficerId(userId);
						nicTransactionLog.setLogCreateTime(new Date());
						
						this.saveNicTransactionLog(nicTransactionLog);
					}
				} catch (NicUploadJobServiceException e) {
					e.printStackTrace();
				} catch (Exception e) {
					e.printStackTrace();
					throw new NicUploadJobServiceException(e);
				}
			}
			dao.approveJob_Confirmed(approveJobId, userId, wkstnId, investStatus);
			
		} catch (Exception ex) {
			logger.error("Error occurred while approving the card. Exception: "	+ ex.getMessage(), ex);
			throw new NicUploadJobServiceException(ex);
		}

	}
	
	@Override
	public String getUploadJobByTransactionId(String transId) throws NicUploadJobServiceException {
		try{	
			return this.dao.getUploadJobByTransactionId(transId);
		}catch(Throwable e){
			logger.error("Exception encountered:"+e.getMessage());
			throw new NicUploadJobServiceException(e);
		}
	}
	
	@Override
	public void getSaveTempPassportNo(String transId, String passportNo) throws NicUploadJobServiceException {
		try{	
			this.dao.getSaveTempPassportNo(transId,passportNo);
		}catch(Exception e){
			logger.error("Exception encountered:"+e.getMessage());
			throw new NicUploadJobServiceException(e);
		}
	}
	
	@Override
	public NicUploadJob getUploadJobByTransactionIdJob(String transId) throws NicUploadJobServiceException {
		try{	
			return this.dao.getUploadJobByTransactionIdJob(transId);
		}catch(Exception e){
			logger.error("Exception encountered:"+e.getMessage());
			throw new NicUploadJobServiceException(e);
		}
	}

	 @Override
	public NicUploadJob getNicTransactionByPrevPPno(String prevPassport,
			String typePassport, String nin) throws NicUploadJobServiceException {
		try {
			if (StringUtils.isBlank(prevPassport)
					&& StringUtils.isBlank(typePassport)
					&& StringUtils.isBlank(nin)) {
				throw new TransactionServiceException(
						"One in all cannot be empty.");
			}
			NicUploadJob nicU = dao.getNicTransactionByPrevPPno(
					prevPassport, typePassport, nin);
			return nicU;
		} catch (Exception e) {
			throw new NicUploadJobServiceException(e);
		}
	}
	
	/*Trung*/
	@Override
	public PaginatedResult<NicUploadJobDto> findByTransactionIdPerso(String txnId, String owner)
			throws NicUploadJobServiceException {
		//PaginatedResult<NicUploadJob> result = null;
		//modified
		List<NicUploadJobDto> joblstDto = new ArrayList<NicUploadJobDto>();
		PaginatedResult<NicUploadJob> result = null;
		PaginatedResult<NicUploadJobDto> pageResult = new PaginatedResult<NicUploadJobDto>();
		new PaginatedResult<NicUploadJobDto>();
		try {
			result = dao.findByTransactionIdPerso(txnId, owner);
			//modified
			if (result.getRows() != null) {
				List<NicUploadJob> jobList = result.getRows();
				for (NicUploadJob record : jobList) {
					NicUploadJobDto dto = new NicUploadJobDto();
					dto.setUploadJobId(record.getWorkflowJobId());
					dto.setTransactionId(record.getTransactionId());
					
					//CodeValues codeValue = codesService.getCodeValueByIdName("TRANSACTION_TYPE", record.getJobType());
					CodeValues codeValue = codesSevice.getCodeValueByIdName("JOB_TYPE", record.getJobType()); //2014 Aug 20
					
					if(codeValue!=null){
						dto.setJobType(codeValue.getCodeValueDesc());
					}else{
						dto.setJobType(record.getJobType());
					}
					
					dto.setInvestigationType(record.getInvestigationType());
					dto.setJobCurrentState(record.getJobCurrentStage());
					dto.setInvestigationStatus(record.getInvestigationStatus());
					dto.setInvestigationOfficerId(record.getInvestigationOfficerId());
					//dto.setJobUploadTime(record.getJobUploadTime());
					
					joblstDto.add(dto);
				}
				pageResult.setRows(joblstDto);
				pageResult.setTotal((int) result.getTotal());
				pageResult.setPage(result.getPage());
				}
				
		} catch (Exception ex) {
			logger.error("Error occurred while getting the job search list. Exception: "
					+ ex.getMessage());
			throw new NicUploadJobServiceException(ex);
		}
		return pageResult;
	}
	
	public PaginatedResult<NicUploadJobDto> findPersoList(String officerId,
			int pageNo, int pageSize, Boolean assignedOnly, AssignmentFilter assignmentFilter) throws NicUploadJobServiceException {
		List<NicUploadJobDto> joblstDto = new ArrayList<NicUploadJobDto>(); 
		PaginatedResult<NicUploadJob> pr = null;
		PaginatedResult<NicUploadJobDto> pageResult = new PaginatedResult<NicUploadJobDto>();
		try {
			// NicUploadJob nicUploadJob = new NicUploadJob();
			// nicUploadJob.setInvestigationOfficerId(userId);
			pr = dao.findPersoList(officerId, pageNo, pageSize,   assignedOnly, assignmentFilter);
			// dao.findAllForPagination(nicUploadJob, pageNo,
			// pageSize);//dao.findAllInvestigationForPagination(nicUploadJob,
			// pageNo, pageSize);
			if (pr.getRows() != null) {
				List<NicUploadJob> jobList = pr.getRows();
				for (NicUploadJob record : jobList) {
					logger.info("Upload Job Id=============>>>>>>>>>>>>>>>>>>>" + record.getWorkflowJobId());
					NicUploadJobDto dto = new NicUploadJobDto();
					dto.setUploadJobId(record.getWorkflowJobId());
					dto.setTransactionId(record.getTransactionId());

					// CodeValues codeValue =
					// codesService.getCodeValueByIdName("TRANSACTION_TYPE",
					// record.getJobType());
					CodeValues codeValue = codesSevice.getCodeValueByIdName("JOB_TYPE", record.getJobType()); // 2014

					if (codeValue != null) {
						dto.setJobType(codeValue.getCodeValueDesc());
					} else {
						dto.setJobType(record.getJobType());
					}

					dto.setInvestigationType(record.getInvestigationType());
					dto.setJobCurrentState(record.getJobCurrentStage());
					dto.setInvestigationStatus(record.getInvestigationStatus());
					dto.setInvestigationOfficerId(record.getInvestigationOfficerId());
					dto.setJobUploadTime(record.getJobCreatedTime());

					joblstDto.add(dto);
				}
				pageResult.setRows(joblstDto);
				pageResult.setTotal((int) pr.getTotal());
				pageResult.setPage(pr.getPage());
			}
		} catch (Exception ex) {
			logger.error("Error occurred while getting the investigation job list. Exception: " + ex.getMessage());
			throw new NicUploadJobServiceException(ex);
		}
		return pageResult;
	}
	
	@Override
	public PaginatedResult<NicUploadJobDto> findByTransactionIdSync(String txnId, String owner)
			throws NicUploadJobServiceException {
		//PaginatedResult<NicUploadJob> result = null;
		//modified
		List<NicUploadJobDto> joblstDto = new ArrayList<NicUploadJobDto>();
		PaginatedResult<NicUploadJob> result = null;
		PaginatedResult<NicUploadJobDto> pageResult = new PaginatedResult<NicUploadJobDto>();
		new PaginatedResult<NicUploadJobDto>();
		try {
			result = dao.findByTransactionIdSync(txnId, owner);
			//modified
			if (result.getRows() != null) {
				List<NicUploadJob> jobList = result.getRows();
				for (NicUploadJob record : jobList) {
					NicUploadJobDto dto = new NicUploadJobDto();
					dto.setUploadJobId(record.getWorkflowJobId());
					dto.setTransactionId(record.getTransactionId());
					
					//CodeValues codeValue = codesService.getCodeValueByIdName("TRANSACTION_TYPE", record.getJobType());
					CodeValues codeValue = codesSevice.getCodeValueByIdName("JOB_TYPE", record.getJobType()); //2014 Aug 20
					
					if(codeValue!=null){
						dto.setJobType(codeValue.getCodeValueDesc());
					}else{
						dto.setJobType(record.getJobType());
					}
					
					dto.setInvestigationType(record.getInvestigationType());
					dto.setJobCurrentState(record.getJobCurrentStage());
					dto.setInvestigationStatus(record.getInvestigationStatus());
					dto.setInvestigationOfficerId(record.getInvestigationOfficerId());
					//dto.setJobUploadTime(record.getJobUploadTime());
					
					joblstDto.add(dto);
				}
				pageResult.setRows(joblstDto);
				pageResult.setTotal((int) result.getTotal());
				pageResult.setPage(result.getPage());
				}
				
		} catch (Exception ex) {
			logger.error("Error occurred while getting the job search list. Exception: "
					+ ex.getMessage());
			throw new NicUploadJobServiceException(ex);
		}
		return pageResult;
	}
	
	public PaginatedResult<NicUploadJobDto> findSyncSingerList(String officerId,
			int pageNo, int pageSize, Boolean assignedOnly, AssignmentFilterAll assignmentFilter) throws NicUploadJobServiceException {
		List<NicUploadJobDto> joblstDto = new ArrayList<NicUploadJobDto>(); 
		PaginatedResult<NicUploadJob> pr = null;
		PaginatedResult<NicUploadJobDto> pageResult = new PaginatedResult<NicUploadJobDto>();
		try {
			// NicUploadJob nicUploadJob = new NicUploadJob();
			// nicUploadJob.setInvestigationOfficerId(userId);
			pr = dao.findSyncSingerList(officerId, pageNo, pageSize,   assignedOnly, assignmentFilter);
			// dao.findAllForPagination(nicUploadJob, pageNo,
			// pageSize);//dao.findAllInvestigationForPagination(nicUploadJob,
			// pageNo, pageSize);
			if (pr.getRows() != null) {
				List<NicUploadJob> jobList = pr.getRows();
				for (NicUploadJob record : jobList) {
					logger.info("Upload Job Id=============>>>>>>>>>>>>>>>>>>>" + record.getWorkflowJobId());
					NicUploadJobDto dto = new NicUploadJobDto();
					dto.setUploadJobId(record.getWorkflowJobId());
					dto.setTransactionId(record.getTransactionId());

					// CodeValues codeValue =
					// codesService.getCodeValueByIdName("TRANSACTION_TYPE",
					// record.getJobType());
					CodeValues codeValue = codesSevice.getCodeValueByIdName("JOB_TYPE", record.getJobType()); // 2014

					if (codeValue != null) {
						dto.setJobType(codeValue.getCodeValueDesc());
					} else {
						dto.setJobType(record.getJobType());
					}

					dto.setInvestigationType(record.getInvestigationType());
					dto.setJobCurrentState(record.getJobCurrentStage());
					dto.setInvestigationStatus(record.getInvestigationStatus());
					dto.setInvestigationOfficerId(record.getInvestigationOfficerId());
					dto.setJobUploadTime(record.getJobCreatedTime());
					dto.setPassportNo(record.getTempPassportNo());
					
					joblstDto.add(dto);
				}
				pageResult.setRows(joblstDto);
				pageResult.setTotal((int) pr.getTotal());
				pageResult.setPage(pr.getPage());
			}
		} catch (Exception ex) {
			logger.error("Error occurred while getting the investigation job list. Exception: " + ex.getMessage());
			throw new NicUploadJobServiceException(ex);
		}
		return pageResult;
	}
	
	//TRUNG THÊM CHO PHẦN PHÂN CÔNG
	@Override
	public PaginatedResult<NicUploadJobDto> findAllForInvestigationPaginationByNullOfficer(String[] recordStatuses, String officerId,
			int pageNo, int pageSize, Boolean assignedOnly, AssignmentFilterAll assignmentFilter) throws NicUploadJobServiceException {
		List<NicUploadJobDto> joblstDto = new ArrayList<NicUploadJobDto>(); 
		PaginatedResult<NicUploadJob> pr = null;
		PaginatedResult<NicUploadJobDto> pageResult = new PaginatedResult<NicUploadJobDto>();
		try {
			// NicUploadJob nicUploadJob = new NicUploadJob();
			// nicUploadJob.setInvestigationOfficerId(userId);
			pr = dao.findInvestigationJobForPaginationNullOfficer(recordStatuses, officerId, pageNo, pageSize, assignedOnly, assignmentFilter);
			// dao.findAllForPagination(nicUploadJob, pageNo,
			// pageSize);//dao.findAllInvestigationForPagination(nicUploadJob,
			// pageNo, pageSize);
			if (pr.getRows() != null) {
				List<NicUploadJob> jobList = pr.getRows();
				for (NicUploadJob record : jobList) {
					logger.info("Upload Job Id=============>>>>>>>>>>>>>>>>>>>" + record.getWorkflowJobId());
					NicUploadJobDto dto = new NicUploadJobDto();
					dto.setUploadJobId(record.getWorkflowJobId());
					dto.setTransactionId(record.getTransactionId());
					dto.setPassportType(record.getNicTransaction().getPassportType());
					CodeValues codeValue = codesSevice.getCodeValueByIdName("JOB_TYPE", record.getJobType()); // 2014
																														// Aug
																														// 20

					if (codeValue != null) {
						dto.setJobType(codeValue.getCodeValueDesc());
					} else {
						dto.setJobType(record.getJobType());
					}

					dto.setInvestigationType(record.getInvestigationType());
					dto.setJobCurrentState(record.getJobCurrentStage());
					dto.setInvestigationStatus(record.getInvestigationStatus());
					dto.setInvestigationOfficerId(record.getInvestigationOfficerId());
					dto.setJobUploadTime(record.getJobCreatedTime());

					joblstDto.add(dto);
				}
				pageResult.setRows(joblstDto);
				pageResult.setTotal((int) pr.getTotal());
				pageResult.setPage(pr.getPage());
			}
		} catch (Exception ex) {
			logger.error("Error occurred while getting the investigation job list. Exception: " + ex.getMessage());
			throw new NicUploadJobServiceException(ex);
		}
		return pageResult;
	}

	@SuppressWarnings("unused")
	@Override
	public PaginatedResult<NicUploadJobDto> findAllForInvestigationPaginationByPackageID(String[] status,
			String packageId, int pageNo, int pageSize) {
		List<NicUploadJobDto> joblstDto = new ArrayList<NicUploadJobDto>(); 
		List<NicUploadJobDto> joblstPage = new ArrayList<NicUploadJobDto>(); 
		try {
			List<NicUploadJob> list = new ArrayList<NicUploadJob>();
			List<NicTransaction> listTras = transactionDao.getListTransactionByPackage(packageId);
			for(NicTransaction nic : listTras){
				List<NicUploadJob> listJob = this.dao.findAllByTransactionIdOrStatus(status,nic.getTransactionId());
				for(NicUploadJob job : listJob){
					list.add(job);
				}
			}
			if(list != null && list.size() > 0){
				for (NicUploadJob record : list) {
					logger.info("Upload Job Id=============>>>>>>>>>>>>>>>>>>>" + record.getWorkflowJobId());
					NicUploadJobDto dto = new NicUploadJobDto();
					dto.setUploadJobId(record.getWorkflowJobId());
					dto.setTransactionId(record.getTransactionId());
					dto.setPassportType(record.getNicTransaction().getPassportType());
					CodeValues codeValue = codesSevice.getCodeValueByIdName("JOB_TYPE", record.getJobType()); // 2014
																														// Aug
																														// 20

					if (codeValue != null) {
						dto.setJobType(codeValue.getCodeValueDesc());
					} else {
						dto.setJobType(record.getJobType());
					}

					dto.setInvestigationType(record.getInvestigationType());
					dto.setJobCurrentState(record.getJobCurrentStage());
					dto.setInvestigationStatus(record.getInvestigationStatus());
					dto.setInvestigationOfficerId(record.getInvestigationOfficerId());
					dto.setJobUploadTime(record.getJobCreatedTime());

					joblstDto.add(dto);
				}
				int totalItems = joblstDto.size() - (pageNo - 1) * pageSize;
				if(totalItems > pageSize){
					totalItems = pageSize;
				}
				for(int j = (pageNo - 1) * pageSize; j < totalItems + (pageNo - 1) * pageSize; j++){
					joblstPage.add(joblstDto.get(j));
				}
				return new PaginatedResult<>(joblstDto.size(), pageNo, joblstPage);
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
		}
		return new PaginatedResult<>(joblstDto.size(), pageNo, joblstPage);
	}

	@Override
	public PaginatedResult<NicUploadJobDto> findAllForInvestigationPaginationByNullOfficerPackage(String[] recordStatuses, String officerId, int pageNo,int pageSize, Boolean assignedOnly,AssignmentFilterAll assignmentFilter)
			throws NicUploadJobServiceException {
		List<NicUploadJobDto> joblstDto = new ArrayList<NicUploadJobDto>(); 
		PaginatedResult<NicUploadJob> pr = null;
		PaginatedResult<NicUploadJobDto> pageResult = new PaginatedResult<NicUploadJobDto>();
		try {			
			pr = dao.findInvestigationJobForPaginationNullOfficerPackage(recordStatuses, officerId, pageNo, pageSize, assignedOnly, assignmentFilter);			
			if (pr.getRows() != null) {
				List<NicUploadJob> jobList = pr.getRows();
				for (NicUploadJob record : jobList) {
					List<EppListHandoverDetail> listD = eppListHandoverDetailService.getPackageNameByTransactionId(record.getTransactionId()).getListModel();
					logger.info("Upload Job Id=============>>>>>>>>>>>>>>>>>>>" + record.getWorkflowJobId());
					if(listD != null && listD.size() > 0){		
						List<NicListHandover> listH = handoverDao.findListHandoverByPackageId(new NicListHandoverId(listD.get(0).getPackageId(), null), null).getListModel();
						NicUploadJobDto dto = new NicUploadJobDto();
						dto.setPackageId(listD.get(0).getPackageId());
						dto.setNumberTran("" + listD.size());
						dto.setRicName(record.getNicTransaction().getIssSiteCode());
						dto.setDatePackage(listH.get(0).getCreateDate());					
						joblstDto.add(dto);
					}
				}
				pageResult.setRows(joblstDto);
				pageResult.setTotal((int) pr.getTotal());
				pageResult.setPage(pr.getPage());
			}
		} catch (Exception ex) {
			logger.error("Error occurred while getting the investigation job list. Exception: " + ex.getMessage());
			throw new NicUploadJobServiceException(ex);
		}
		return pageResult;
	}

	@Override
	public PaginatedResult<NicUploadJobDto> findAllForInvestigationPagination2(
			String[] recordStatuses, String officerId, int pageNo,
			int pageSize, Boolean assignedOnly,
			AssignmentFilterAll assignmentFilter)
			throws NicUploadJobServiceException {
		List<NicUploadJobDto> joblstDto = new ArrayList<NicUploadJobDto>(); 
		List<NicUploadJobDto> joblstPage = new ArrayList<NicUploadJobDto>(); 
		List<NicUploadJob> pr = null;
		PaginatedResult<NicUploadJobDto> pageResult = new PaginatedResult<NicUploadJobDto>();
		try {

			pr = dao.findInvestigationJobForPagination(recordStatuses, officerId, assignedOnly, assignmentFilter);
			if (pr != null && pr.size() > 0) {
				for (NicUploadJob record : pr) {
					List<NicTransactionPackage> list = tranPackageDao.getListPackageByTransactionId(record.getTransactionId());	
					
					for(NicTransactionPackage ptran : list){
						List<NicListHandover> listHan = handoverDao.findListHandoverByPackageIdOrType(ptran.getPackageId(), 2, assignmentFilter);
						if(listHan != null && listHan.size() > 0){
							NicUploadJobDto dto = new NicUploadJobDto();
							dto.setListCode(listHan.get(0).getId().getPackageId());
							dto.setListName(listHan.get(0).getPackageName());
							dto.setNumberTran("1");
							dto.setCreateDate(listHan.get(0).getCreateDate());
							if(checkEqualPackage(joblstDto, ptran.getPackageId())){
								joblstDto.add(dto);								
							}
						}
					}
				}
//				if(assignmentFilter != null && !StringUtils.isEmpty(assignmentFilter.getPackageCode())){
//					list = tranPackageDao.getListPackageByPackageId(assignmentFilter.getPackageCode());
//				}
				int totalItems = joblstDto.size() - (pageNo - 1) * pageSize; // 12 - 2
				if(totalItems > pageSize){
					totalItems = pageSize;
				}
				for(int j = (pageNo - 1) * pageSize; j < totalItems + (pageNo - 1) * pageSize; j++){
					joblstPage.add(joblstDto.get(j));
				}
				pageResult.setRows(joblstPage);
				pageResult.setTotal(joblstDto.size());
				pageResult.setPage(pageNo);
			}
		} catch (Exception ex) {
			logger.error("Error occurred while getting the investigation job list. Exception: " + ex.getMessage());
			throw new NicUploadJobServiceException(ex);
		}
		return pageResult;
	}
	
	public boolean checkEqualPackage(List<NicUploadJobDto> list, String id){
		boolean check = true;
		for(NicUploadJobDto dto : list){
			if(dto.getListCode().equals(id)){
				check = false;
				int number = Integer.parseInt(dto.getNumberTran());
				number++;
				dto.setNumberTran(String.valueOf(number));
				break;
			}
		}
		return check;
	}

	@Override
	public PaginatedResult<NicUploadJobDto> findAllForInvestigationPagination3(
			String[] recordStatuses, String officerId, int stage, int pageNo,
			int pageSize, Boolean assignedOnly,
			AssignmentFilterAll assignmentFilter)
			throws NicUploadJobServiceException {
		List<NicUploadJobDto> joblstDto = new ArrayList<NicUploadJobDto>(); 
		List<NicUploadJobDto> joblstPage = new ArrayList<NicUploadJobDto>(); 
		List<NicUploadJob> pr = null;
		PaginatedResult<NicUploadJobDto> pageResult = new PaginatedResult<NicUploadJobDto>();
		try {

			pr = dao.findInvestigationJobForPagination(recordStatuses, officerId, assignedOnly, assignmentFilter);
			if (pr != null && pr.size() > 0) {
				for (NicUploadJob record : pr) {
					if(record.getValidateInfoBca() != null && record.getValidateInfoBca() == stage){
						List<NicTransactionPackage> list = tranPackageDao.getListPackageByTransactionId(record.getTransactionId());	
						
						for(NicTransactionPackage ptran : list){
							List<NicListHandover> listHan = handoverDao.findListHandoverByPackageId(new NicListHandoverId(ptran.getPackageId(), null), assignmentFilter).getListModel();
							if(listHan != null && listHan.size() > 0){
								NicUploadJobDto dto = new NicUploadJobDto();
								dto.setListCode(listHan.get(0).getId().getPackageId());
								dto.setListName(listHan.get(0).getPackageName());
								dto.setNumberTran("1");
								dto.setCreateDate(listHan.get(0).getCreateDate());
								dto.setCreateBy(listHan.get(0).getCreateBy());
								if(checkEqualPackage(joblstDto, ptran.getPackageId())){
									joblstDto.add(dto);								
								}
							}
						}						
					}
				}
				if(!StringUtils.isEmpty(assignmentFilter.getPackageCode())){
					for(int i = 0; i < joblstDto.size(); i++){
						if(!joblstDto.get(i).getListCode().equals(assignmentFilter.getPackageCode())){
							joblstDto.remove(i);
							i--;
						}
					}
				}
				int totalItems = joblstDto.size() - (pageNo - 1) * pageSize; // 12 - 2
				if(totalItems > pageSize){
					totalItems = pageSize;
				}
				for(int j = (pageNo - 1) * pageSize; j < totalItems + (pageNo - 1) * pageSize; j++){
					joblstPage.add(joblstDto.get(j));
				}
				pageResult.setRows(joblstPage);
				pageResult.setTotal(joblstPage.size());
				pageResult.setPage(pageNo);
			}
		} catch (Exception ex) {
			logger.error("Error occurred while getting the investigation job list. Exception: " + ex.getMessage());
			throw new NicUploadJobServiceException(ex);
		}
		return pageResult;
	}
	
	@Override
	@Transactional(rollbackFor = NicUploadJobServiceException.class, propagation = Propagation.REQUIRED)
	public void assignInvestigationJobs1(List<Long> jobIds, String[] assignToId, String officerUserId,
			String officerWorkstationId) throws NicUploadJobServiceException {

		try {
			//dao.unassignInvestigationJobs(jobIds, officerUserId, officerWorkstationId); 31/01/2018 - TRUNG TẠM ĐÓNG ĐỂ BẢN GHI KO DÍNH INVES DUYỆT ĐƯỢC
			dao.assignInvestigationJobs1(jobIds, assignToId, officerUserId, officerWorkstationId);
		} catch (Exception ex) {
			logger.error("Error occurred. Exception: " + ex.getMessage(), ex);
			throw new NicUploadJobServiceException(ex);
		}
	}

	@Override
	public void assignInvestigationJobsUpdate(List<Long> jobIds,
			String[] assignToId, String officerUserId,
			String officerWorkstationId) throws NicUploadJobServiceException {
		try {
			//dao.unassignInvestigationJobs(jobIds, officerUserId, officerWorkstationId); 31/01/2018 - TRUNG TẠM ĐÓNG ĐỂ BẢN GHI KO DÍNH INVES DUYỆT ĐƯỢC
			dao.assignInvestigationJobsUpdate(jobIds, assignToId, officerUserId, officerWorkstationId);
		} catch (Exception ex) {
			logger.error("Error occurred. Exception: " + ex.getMessage(), ex);
			throw new NicUploadJobServiceException(ex);
		}
	}

	@Override
	public PaginatedResult<NicUploadJobDto> findAllForInvestigationPagination1(
			String[] recordStatuses, String officerId, String leaderId,int pageNo,
			int pageSize, Boolean assignedOnly,
			AssignmentFilterAll assignmentFilter)
			throws NicUploadJobServiceException {
		List<NicUploadJobDto> joblstDto = new ArrayList<NicUploadJobDto>(); 
		List<NicUploadJobDto> dtoListPage = new ArrayList<NicUploadJobDto>(); 
		List<NicUploadJob> pr = null;
		//PaginatedResult<NicUploadJob> prDSQ = null;
		PaginatedResult<NicUploadJobDto> pageResult = new PaginatedResult<NicUploadJobDto>();
		try {
			// NicUploadJob nicUploadJob = new NicUploadJob();
			// nicUploadJob.setInvestigationOfficerId(userId);
			pr = dao.findInvestigationJobForPaginationByOfficerId2(recordStatuses, officerId, leaderId, assignedOnly, assignmentFilter);
			
			// dao.findAllForPagination(nicUploadJob, pageNo,
			// pageSize);//dao.findAllInvestigationForPagination(nicUploadJob,
			// pageNo, pageSize);
			if (pr != null) {
			
				//List<NicUploadJob> jobList = pr.getRows();
			
				for (NicUploadJob record : pr) {
					
//					NicListHandover handover = handoverDao.findById(record.getNicTransaction().getPackageID());
//					if(handover != null && StringUtils.isNotEmpty(handover.getUsersProcess())){
//						if(!handover.getUsersProcess().contains((leaderId + ","))){
//							continue;
//						}
//					}
					
					logger.info("Upload Job Id=============>>>>>>>>>>>>>>>>>>>" + record.getWorkflowJobId());
					NicUploadJobDto dto = new NicUploadJobDto();
					dto.setUploadJobId(record.getWorkflowJobId());
					dto.setTransactionId(record.getTransactionId());

					// CodeValues codeValue =
					// codesService.getCodeValueByIdName("TRANSACTION_TYPE",
					// record.getJobType());
					CodeValues codeValue = codesSevice.getCodeValueByIdName("JOB_TYPE", record.getJobType());

					if (codeValue != null) {
						dto.setJobType(codeValue.getCodeValueDesc());
					} else {
						dto.setJobType(record.getJobType());
					}
//					List<NicTransactionLog> log_ = transactionLogDao.findAllByRefIdAndTransStateList(record.getTransactionId(),null);
//					String logStr = "";
//					if(log_ != null){
//						for(NicTransactionLog l : log_){
//							if(!StringUtils.isEmpty(l.getLogInfo()))
//								logStr += l.getLogInfo().replaceAll("\\<.*?>","") + " | ";
//								//replaceAll("\\<.*?>","") 
//						}
//					}
//					if(StringUtils.isEmpty(record.getInvestigationOfficerId())){
//						dto.setFlagOfficerId("0");
//					}else {
//						dto.setFlagOfficerId("1");
//					}
					
					int search = 0;
					if(assignmentFilter != null && assignmentFilter.getTypeInvestigation() != null){
						search = Integer.parseInt(assignmentFilter.getTypeInvestigation());
						
					}
					//RicTransaction txn = ricTransactionService.findById(record.getTransactionId());
					NicListHandover handover = null;//handoverDao.findHandoverByTransactionId(record.getTransactionId(), 8, 1, true);
					if(record.getInvestigationStatus().equals("02")){
						dto.setStageList("Đã xử lý");
						dto.setPriority(3);
					}else if(record.getInvestigationStatus().equals("01") && record.getInvestigationOfficerId() == null && handover == null){
						dto.setStageList("Chưa xử lý");
						dto.setPriority(1);
					}else if((record.getInvestigationStatus().equals("01") && record.getInvestigationOfficerId() != null && handover == null) || record.getJobApproveStatus() != null){
						dto.setStageList("Đang xử lý");
						dto.setPriority(2);
					}else if(record.getInvestigationStatus().equals("01") && handover != null && record.getJobApproveStatus() == null){
						dto.setStageList("Đợi bổ sung");
						dto.setPriority(4);
					}
//					
					if(search == 1 && (dto.getPriority() == 2 || dto.getPriority() == 3 || dto.getPriority() == 4)){
						continue;
					}else if(search == 2 && (dto.getPriority() == 1 || dto.getPriority() == 3 || dto.getPriority() == 4)){
						continue;
					}else if(search == 3 && (dto.getPriority() == 2 || dto.getPriority() == 1 || dto.getPriority() == 4)){
						continue;
					}else if(search == 4 && (dto.getPriority() == 2 || dto.getPriority() == 3 || dto.getPriority() == 1)){
						continue;
					}
					
					dto.setInvestigationType(record.getInvestigationType());
					dto.setJobCurrentState(record.getJobCurrentStage());
					dto.setInvestigationStatus(record.getInvestigationStatus());
					if(StringUtils.isNotEmpty(record.getInvestigationOfficerId())){
						Users users = userService.findByUserId(record.getInvestigationOfficerId());
						if(users != null){
							dto.setLeaderId(users.getUserName());
						}						
					}
					dto.setInvestigationOfficerId(record.getInvestigationOfficerId());
					dto.setJobUploadTime(record.getJobCreatedTime());
					if(record.getValidateInfoBca() != null){
						if(record.getValidateInfoBca() == 1)
							dto.setValidateInfoBca_des("Đã gửi");
						else if(record.getValidateInfoBca() == 2)
							dto.setValidateInfoBca_des("Đã có kết quả từ BCA");
						else {
							dto.setValidateInfoBca_des("Không");
						}
							
					}
					
					dto.setPassportNo(record.getTempPassportNo());
					dto.setPersoCheckStatus(record.getPersoRegisterStatus());
					dto.setRecieptNo(record.getReceiptNo());
					
					NicDocumentData nicData = documentDataService.findByDocNumber(record.getTempPassportNo()).getModel();
					if(nicData != null){
						/*Kiểm tra cho phép đồng bộ sang NIC Cục lãnh sự */
						if(record.getPersoRegisterStatus() != null && record.getPersoRegisterStatus().equals(NicUploadJob.RECORD_STATUS_APPROVED) 
								&&nicData.getStatus().equals("ISSUANCE")
								&& nicData.getIssuedFlag() && nicData.getActiveFlag())
						{
							dto.setSyncNic(true);
						}
						else
							dto.setSyncNic(false);
						
						dto.setPackageId(nicData.getPackageId());
					}
					else
						dto.setSyncNic(false);
					/*
					if(record.getPriority() == 0)
						dto.setPriorityString("Thông thường");*/
//					NicTransaction nicTran = nicTransactionService.getNicTransactionById(record.getTransactionId());
//					if(record.getJobType().equals("LOS"))
//						logStr = nicTran.getNicRegistrationData().getReasonKind();
//					dto.setNoteInvestigation(logStr);
//					if(nicTran!= null){
//						dto.setNicSiteCode(nicTran.getNicSiteCode());
//					}
					joblstDto.add(dto);
				}
				int totalItems = joblstDto.size() - (pageNo - 1) * pageSize; // 12 - 2
				if(totalItems > pageSize){
					totalItems = pageSize;
				}
				for(int j = (pageNo - 1) * pageSize; j < totalItems + (pageNo - 1) * pageSize; j++){
					dtoListPage.add(joblstDto.get(j));
				}
				pageResult.setRows(dtoListPage);
				pageResult.setTotal(joblstDto.size());
				pageResult.setPage(pageNo);
			}
		} catch (Exception ex) {
			logger.error("Error occurred while getting the investigation job list. Exception: " + ex.getMessage());
			throw new NicUploadJobServiceException(ex);
		}
		return pageResult;
	}

	public int countTranInPackage(Set<NicUploadJob> list, String packageId){
		int i = 0;
		for(NicUploadJob job : list){
/*			if(job.getNicTransaction().getPackageID().equals(packageId) && job.getNicTransaction().getLeaderOfficerId() == null){
				i++;
			}*/
		}
		return i;
	}
	
	@Override
	public List<NicUploadJobDto> findAllForInvestigationPagination2(
			String[] recordStatuses,String officerId, Boolean assignedOnly,AssignmentFilterAll assignmentFilter)
			throws NicUploadJobServiceException {
		List<NicUploadJobDto> joblstDto = new ArrayList<NicUploadJobDto>(); 
		List<NicUploadJob> pr = null;
		//PaginatedResult<NicUploadJob> prDSQ = null;
		//PaginatedResult<NicUploadJobDto> pageResult = new PaginatedResult<NicUploadJobDto>();
		try {
			// NicUploadJob nicUploadJob = new NicUploadJob();
			// nicUploadJob.setInvestigationOfficerId(userId);
			pr = dao.findInvestigationJobForPaginationByOfficerId2(recordStatuses, officerId, assignedOnly,assignmentFilter);
			
			// dao.findAllForPagination(nicUploadJob, pageNo,
			// pageSize);//dao.findAllInvestigationForPagination(nicUploadJob,
			// pageNo, pageSize);
			if (pr != null) {
			
				List<NicUploadJob> jobList = pr;
				Set<String> listPackage = new HashSet<String>();
				Map<String, String> officeProcess = new HashMap<String, String>();
				Set<NicUploadJob> setList = new HashSet<NicUploadJob>();
				for (NicUploadJob record : jobList) {
					setList.add(record);
				}
				//Map<String, Integer> countPack = new HashMap<String, Integer>();
				for (NicUploadJob record : setList) {
					
					logger.info("Upload Job Id=============>>>>>>>>>>>>>>>>>>>" + record.getWorkflowJobId());
					//NicUploadJobDto dto = new NicUploadJobDto();
					//dto.setUploadJobId(record.getWorkflowJobId());
					//dto.setTransactionId(record.getTransactionId());
					listPackage.add(record.getNicTransaction().getPackageId());
					if(StringUtils.isNotEmpty(record.getInvestigationOfficerId())){
						Users users = userService.findByUserId(record.getInvestigationOfficerId());
						if(users != null)
							officeProcess.put(record.getNicTransaction().getPackageId(), users.getUserName());
						
					}
					//dto.setPackageId(record.getNicTransaction().getPackageID());
					//dto.setDatePackage(record.getNicTransaction().getListHandovers().);
					// CodeValues codeValue =
					// codesService.getCodeValueByIdName("TRANSACTION_TYPE",
					// record.getJobType());
//					CodeValues codeValue = codesSevice.getCodeValueByIdName("JOB_TYPE", record.getJobType());
//
//					if (codeValue != null) {
//						dto.setJobType(codeValue.getCodeValueDesc());
//					} else {
//						dto.setJobType(record.getJobType());
//					}
//					List<NicTransactionLog> log_ = transactionLogDao.findAllByRefIdAndTransStateList(record.getTransactionId(),null);
//					String logStr = "";
//					if(log_ != null){
//						for(NicTransactionLog l : log_){
//							if(!StringUtils.isEmpty(l.getLogInfo()))
//								logStr += l.getLogInfo().replaceAll("\\<.*?>","") + " | ";
//								//replaceAll("\\<.*?>","") 
//						}
//					}
//					
//					dto.setInvestigationType(record.getInvestigationType());
//					dto.setJobCurrentState(record.getJobCurrentStage());
//					dto.setInvestigationStatus(record.getInvestigationStatus());
//					dto.setInvestigationOfficerId(record.getInvestigationOfficerId());
//					dto.setJobUploadTime(record.getJobCreatedTime());
//					if(record.getValidateInfoBca() != null){
//						if(record.getValidateInfoBca() == 1)
//							dto.setValidateInfoBca_des("Đã gửi");
//						else if(record.getValidateInfoBca() == 2)
//							dto.setValidateInfoBca_des("Đã có kết quả từ BCA");
//						else {
//							dto.setValidateInfoBca_des("Không");
//						}
//							
//					}
//					
//					dto.setPassportNo(record.getTempPassportNo());
//					dto.setPersoCheckStatus(record.getPersoRegisterStatus());
//					dto.setRecieptNo(record.getReceiptNo());
//					
//					NicDocumentData nicData = documentDataService.findByDocNumber(record.getTempPassportNo());
//					if(nicData != null){
//						/*Kiểm tra cho phép đồng bộ sang NIC Cục lãnh sự */
//						if(record.getPersoRegisterStatus() != null && record.getPersoRegisterStatus().equals(NicUploadJob.RECORD_STATUS_APPROVED) 
//								&&nicData.getStatus().equals("ISSUANCE")
//								&& nicData.getIssuedFlag() && nicData.getActiveFlag())
//						{
//							dto.setSyncNic(true);
//						}
//						else
//							dto.setSyncNic(false);
//						
//						dto.setPackageId(nicData.getPackageId());
//					}
//					else
//						dto.setSyncNic(false);
//					/*
//					if(record.getPriority() == 0)
//						dto.setPriorityString("Thông thường");*/
//					NicTransaction nicTran = nicTransactionService.getNicTransactionById(record.getTransactionId());
//					if(record.getJobType().equals("LOS"))
//						logStr = nicTran.getNicRegistrationData().getReasonKind();
//					dto.setNoteInvestigation(logStr);
//					if(nicTran!= null){
//						dto.setNicSiteCode(nicTran.getNicSiteCode());
//					}
					//joblstDto.add(dto);
				}
				for(String packageId : listPackage){
					NicListHandover han = handoverDao.findById(packageId);
					//int i = this.dao.countInvestigationJobListB(packageId, recordStatuses);
					if(han != null){
						NicUploadJobDto dto = new NicUploadJobDto();
						dto.setPackageId(han.getId().getPackageId());
						dto.setPackageDate(HelperClass.convertDateToString(han.getCreateDate()));
						dto.setRicName(officeProcess.get(han.getId().getPackageId()));		
						dto.setNumberTran(this.countTranInPackage(setList, packageId) + "");
						joblstDto.add(dto);
					}
				}
				//pageResult.setRows(joblstDto);
				//pageResult.setTotal((int) pr.getTotal());
				//pageResult.setPage(pr.getPage());
			}
		} catch (Exception ex) {
			logger.error("Error occurred while getting the investigation job list. Exception: " + ex.getMessage());
			//throw new NicUploadJobServiceException(ex);
			ex.printStackTrace();
		}
		return joblstDto;
	}

	@Override
	public List<NicUploadJobDto> findAllForInvestigationPagination3(
			String[] recordStatuses, String officerId, Boolean assignedOnly,
			AssignmentFilterAll assignmentFilter)
			throws NicUploadJobServiceException {
		List<NicUploadJobDto> joblstDto = new ArrayList<NicUploadJobDto>(); 
		List<NicUploadJob> pr = null;
		try {
			pr = dao.findInvestigationJobForPaginationByOfficerId2(recordStatuses, officerId, assignedOnly, assignmentFilter);
			if (pr != null) {
			
				List<NicUploadJob> jobList = pr;
				for (NicUploadJob record : jobList) {
					
					logger.info("Upload Job Id=============>>>>>>>>>>>>>>>>>>>" + record.getWorkflowJobId());
					NicUploadJobDto dto = new NicUploadJobDto();
					dto.setUploadJobId(record.getWorkflowJobId());
					dto.setTransactionId(record.getTransactionId());					
					dto.setPackageId(record.getNicTransaction().getPackageId());
					
					dto.setPassportNo(record.getTempPassportNo());
					NicTransaction nicTran = nicTransactionService.getNicTransactionById(record.getTransactionId()).getModel();
					if(nicTran!= null && nicTran.getNicRegistrationData() != null){
						dto.setNicSiteCode(nicTran.getNicSiteCode());
						dto.setFullName(HelperClass.createFullName(nicTran.getNicRegistrationData().getSurnameFull(), nicTran.getNicRegistrationData().getMiddlenameFull(), nicTran.getNicRegistrationData().getFirstnameFull()));
						if(!StringUtils.isEmpty(nicTran.getNicRegistrationData().getGender())){
							dto.setGender(nicTran.getNicRegistrationData().getGender().equals("M") ? "Nam" : "Nữ");
						}else{
							dto.setGender("Không");
						}
						String dateDob = HelperClass.convertDateToString(nicTran.getNicRegistrationData().getDateOfBirth());
						dto.setDob(com.nec.asia.nic.utils.HelperClass.loadDateOfBirth(dateDob, nicTran.getNicRegistrationData().getDefDateOfBirth()));
						//dto.setDob(nicTran.getNicRegistrationData().getDateOfBirth() != null ? HelperClass.convertDateToString(nicTran.getNicRegistrationData().getDateOfBirth()) : "");
						dto.setNin(nicTran.getNin());
						//dto.setPlaceOfBirth(nicTran.getNicRegistrationData().getPlaceOfBirth());
						String noiSinh = codesService.getCodeValueDescByIdName("DISTRICT", nicTran.getNicRegistrationData().getPlaceOfBirth(), "");
						if(StringUtils.isNotEmpty(noiSinh)){
							dto.setPlaceOfBirth(noiSinh);								
						}else{
							dto.setPlaceOfBirth(nicTran.getNicRegistrationData().getPlaceOfBirth());	
						}
						dto.setRegSiteCode(nicTran.getRegSiteCode());
						dto.setEsColectionDate(nicTran.getEstDateOfCollection() != null ? HelperClass.convertDateToString(nicTran.getEstDateOfCollection()) : "");
						dto.setLeaderNote(""); //nicTran.getNoteHandoverB());
						//dto.setNoteApprove(record.getNoteApprove());
						dto.setNoteApprovePerson(record.getNoteApprovePerson() != null ? record.getNoteApprovePerson() : "");
						dto.setNoteApproveObj(record.getNoteApproveObj() != null ? record.getNoteApproveObj() : "");
						dto.setNoteApproveNin(record.getNoteApproveNin() != null ? record.getNoteApproveNin() : "");
					}
					joblstDto.add(dto);
				}			
			}
		} catch (Exception ex) {
			logger.error("Error occurred while getting the investigation job list. Exception: " + ex.getMessage());
			throw new NicUploadJobServiceException(ex);
		}
		return joblstDto;
	}

	@Override
	public void updateOfficerIdByJobId(String jobId, String officer) {
		try {
			NicUploadJob job = dao.findById(Long.parseLong(jobId));
			if(job != null){
				job.setInvestigationOfficerId(officer);
				job.setUpdateDatetime(new Date());
				dao.saveOrUpdate(job);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	@Override
	public List<NicUploadJobDto> findInvestigationProcessByJobId(
			Long[] JobList, String officerId,
			AssignmentFilterAll assignmentFilter) {
		List<NicUploadJobDto> joblstDto = new ArrayList<NicUploadJobDto>(); 
		List<NicUploadJob> pr = null;	
		try {
			pr = dao.findInvestigationProcessByJobId(JobList, officerId, assignmentFilter);
			if (pr != null && pr.size() > 0) {
				List<NicUploadJob> jobList = pr;
				for (NicUploadJob record : jobList) {
					logger.info("Upload Job Id=============>>>>>>>>>>>>>>>>>>>" + record.getWorkflowJobId());
					NicUploadJobDto dto = new NicUploadJobDto();
					dto.setUploadJobId(record.getWorkflowJobId());
					dto.setTransactionId(record.getTransactionId());
//					dto.setPassportType(record.getNicTransaction().getPassportType());
//					CodeValues codeValue = codesSevice.getCodeValueByIdName("JOB_TYPE", record.getJobType()); // 2014
//																														// Aug
//																														// 20
//
//					if (codeValue != null) {
//						dto.setJobType(codeValue.getCodeValueDesc());
//					} else {
//						dto.setJobType(record.getJobType());
//					}
//
//					dto.setInvestigationType(record.getInvestigationType());
//					dto.setJobCurrentState(record.getJobCurrentStage());
//					dto.setInvestigationStatus(record.getInvestigationStatus());
					dto.setInvestigationOfficerId(record.getInvestigationOfficerId());
//					dto.setJobUploadTime(record.getJobCreatedTime());
					NicTransaction tran = record.getNicTransaction();
					if(tran != null){
						dto.setNin(tran.getNin());
						NicRegistrationData regData = tran.getNicRegistrationData();
						if(regData != null){
							dto.setFullName(HelperClass.createFullName(regData.getSurnameFull(), regData.getMiddlenameFull(), regData.getFirstnameFull()));
							dto.setGender("Không");
							if(regData.getGender() != null){
								dto.setGender(regData.getGender().equals("M") ? "Nam" : "Nữ");
							}
							String dateDob = regData.getDateOfBirth() != null ? HelperClass.convertDateToString(regData.getDateOfBirth()) : "";
							dto.setDob(com.nec.asia.nic.utils.HelperClass.loadDateOfBirth(dateDob, regData.getDefDateOfBirth()));
							//dto.setPlaceOfBirth(regData.getPlaceOfBirth());
							String noiSinh = codesService.getCodeValueDescByIdName("DISTRICT", regData.getPlaceOfBirth(), "");
							if(StringUtils.isNotEmpty(noiSinh)){
								dto.setPlaceOfBirth(noiSinh);								
							}else{
								dto.setPlaceOfBirth(regData.getPlaceOfBirth());	
							}
						}
					}
					dto.setDateApprovePerson(record.getDateApprovePerson() != null ? HelperClass.convertDateToString(record.getDateApprovePerson()) : "");
					dto.setDateApproveInvestigation(record.getDateApproveInvestigation() != null ? HelperClass.convertDateToString(record.getDateApproveInvestigation()) : "");
					dto.setDateApproveNin(record.getDateApproveNin() != null ? HelperClass.convertDateToString(record.getDateApproveNin()) : "");
					//dto.setNoteApprove(record.getNoteApprove() != null ? record.getNoteApprove() : "");
					dto.setNoteApprovePerson(record.getNoteApprovePerson() != null ? record.getNoteApprovePerson() : "");
					dto.setNoteApproveObj(record.getNoteApproveObj() != null ? record.getNoteApproveObj() : "");
					dto.setNoteApproveNin(record.getNoteApproveNin() != null ? record.getNoteApproveNin() : "");
					joblstDto.add(dto);
				}				
			}
		} catch (Exception ex) {
			logger.error("Error occurred while getting the investigation job list. Exception: " + ex.getMessage());
			//throw new NicUploadJobServiceException(ex);
		}
		return joblstDto;
	}

	@Override
	public List<NicUploadJobDto> findListHandoverListB(int styleList, String leaderId, String asignId, 
			AssignmentFilterAll assignmentFilter)
			throws NicUploadJobServiceException {
		List<NicUploadJobDto> joblstDto = new ArrayList<NicUploadJobDto>(); 
		List<NicListHandover> pr = null;
		//PaginatedResult<NicUploadJob> prDSQ = null;
		//PaginatedResult<NicUploadJobDto> pageResult = new PaginatedResult<NicUploadJobDto>();
		try {
			// NicUploadJob nicUploadJob = new NicUploadJob();
			// nicUploadJob.setInvestigationOfficerId(userId);
			pr = handoverDao.findListHandoverListB(styleList, leaderId, asignId, assignmentFilter);
			/*Xóa hồ sơ bị trùng*/
			Set<NicListHandover> setPr = new HashSet<NicListHandover>();
			for(NicListHandover handover : pr){
				setPr.add(handover);
			}
			/*end*/
			// dao.findAllForPagination(nicUploadJob, pageNo,
			// pageSize);//dao.findAllInvestigationForPagination(nicUploadJob,
			// pageNo, pageSize);
			if (pr != null) {
			
				Set<NicListHandover> jobList = setPr;
				//Set<String> listPackage = new HashSet<String>();
				for (NicListHandover record : jobList) {					
					logger.info("Handover dao=============>>>>>>>>>>>>>>>>>>>" + record.getId().getPackageId());
					NicUploadJobDto dto = new NicUploadJobDto();
					dto.setPackageId(record.getId().getPackageId());
					dto.setPackageDate(HelperClass.convertDateToString(record.getCreateDate()));
					if(StringUtils.isNotEmpty(record.getProcessUsers())){
						Users users = userService.findByUserId(record.getProcessUsers());
						if(users != null)
							dto.setRicName(users.getUserName());
						
					}
					dto.setNumberTran(record.getCountTransaction() + "");
					joblstDto.add(dto);					
				}


			}
		} catch (Exception ex) {
			logger.error("Error occurred while getting the investigation job list. Exception: " + ex.getMessage());
			throw new NicUploadJobServiceException(ex);
		}
		return joblstDto;
	}

	@Override
	public List<NicUploadJobDto> findInvestigationJobListB(String packageId,
			AssignmentFilterAll assignmentFilter) {
		List<NicUploadJobDto> joblstDto = new ArrayList<NicUploadJobDto>(); 
		List<NicUploadJob> pr = null;
		try {
			pr = dao.findInvestigationJobListB(packageId, assignmentFilter);
			if (pr != null) {
			
				List<NicUploadJob> jobList = pr;
				for (NicUploadJob record : jobList) {
					
					logger.info("Upload Job Id=============>>>>>>>>>>>>>>>>>>>" + record.getWorkflowJobId());
					NicUploadJobDto dto = new NicUploadJobDto();
					dto.setUploadJobId(record.getWorkflowJobId());
					dto.setTransactionId(record.getTransactionId());					
					dto.setPackageId(record.getNicTransaction().getPackageId());
					if(StringUtils.isNotEmpty(record.getNicTransaction().getPackageId())){
						List<NicListHandover> lh = handoverDao.findListHandoverByPackageId(new NicListHandoverId(packageId, null), null).getListModel();
						if(lh != null && lh.size() > 0){
							NicListHandover lh_ = lh.get(0);
//							dto.setArchiveCode(lh_.getArchiveCode());
//							String sstt = lh_.getArchiveCode().substring(7);
//							int stt = Integer.parseInt(sstt);
//							dto.setArchiveCodeStt(stt);
						}
					}
					dto.setPassportNo(record.getTempPassportNo());
					NicTransaction nicTran = nicTransactionService.getNicTransactionById(record.getTransactionId()).getModel();
					if(nicTran!= null && nicTran.getNicRegistrationData() != null){
						dto.setNicSiteCode(nicTran.getNicSiteCode());
						dto.setFullName(HelperClass.createFullName(nicTran.getNicRegistrationData().getSurnameFull(), nicTran.getNicRegistrationData().getMiddlenameFull(), nicTran.getNicRegistrationData().getFirstnameFull()));
						if(!StringUtils.isEmpty(nicTran.getNicRegistrationData().getGender())){
							dto.setGender(nicTran.getNicRegistrationData().getGender().equals("M") ? "Nam" : "Nữ");
						}else{
							dto.setGender("Không");
						}
						String dateDob = HelperClass.convertDateToString(nicTran.getNicRegistrationData().getDateOfBirth());
						dto.setDob(com.nec.asia.nic.utils.HelperClass.loadDateOfBirth(dateDob, nicTran.getNicRegistrationData().getDefDateOfBirth()));
						//dto.setDob(nicTran.getNicRegistrationData().getDateOfBirth() != null ? HelperClass.convertDateToString(nicTran.getNicRegistrationData().getDateOfBirth()) : "");
						dto.setNin(nicTran.getNin());
						//dto.setPlaceOfBirth(nicTran.getNicRegistrationData().getPlaceOfBirth());
						String noiSinh = codesService.getCodeValueDescByIdName("DISTRICT", nicTran.getNicRegistrationData().getPlaceOfBirth(), "");
						if(StringUtils.isNotEmpty(noiSinh)){
							dto.setPlaceOfBirth(noiSinh);								
						}else{
							dto.setPlaceOfBirth(nicTran.getNicRegistrationData().getPlaceOfBirth());	
						}
						dto.setRegSiteCode(nicTran.getRegSiteCode());
						dto.setEsColectionDate(nicTran.getEstDateOfCollection() != null ? HelperClass.convertDateToString(nicTran.getEstDateOfCollection()) : "");
						/*dto.setLeaderId(nicTran.getLeaderOfficerId());
						if(StringUtils.isNotEmpty(nicTran.getLeaderOfficerId())){
							Users users = userService.findByUserId(nicTran.getLeaderOfficerId());
							if(users != null)
								dto.setPosition(users.getPosition());
						}*/
						//dto.setLeaderNote(nicTran.getNoteHandoverB());
						//dto.setNoteApprove(record.getNoteApprove());
						dto.setNoteApprovePerson(record.getNoteApprovePerson() != null ? record.getNoteApprovePerson() : "");
						dto.setNoteApproveObj(record.getNoteApproveObj() != null ? record.getNoteApproveObj() : "");
						dto.setNoteApproveNin(record.getNoteApproveNin() != null ? record.getNoteApproveNin() : "");
						NicTransactionPackage tranPack = tranPackageDao.getListPackageByPackageIdAndTranID(packageId, nicTran.getTransactionId());
						if(tranPack != null){
							dto.setStageList(tranPack.getStage());
							dto.setLeaderNote(tranPack.getNoteApprove());							
						}
					}
					joblstDto.add(dto);
				}			
			}
		} catch (Exception ex) {
			logger.error("Error occurred while getting the investigation job list. Exception: " + ex.getMessage());
			//throw new NicUploadJobServiceException(ex);
		}
		return joblstDto;
	}

	@Override
	public BaseModelSingle<NicUploadJob> getUploadJobByTransactionIdSinger(Long jobId,
			String transId) {
		try {
			return dao.getUploadJobByTransactionIdSinger(jobId, transId);
		} catch (Exception e) {
			e.printStackTrace();
			return new BaseModelSingle<NicUploadJob>(null, false,  CreateMessageException.createMessageException(e) + " - getUploadJobByTransactionIdSinger - " + transId + " - thất bại.");
		}
	}

	@Override
	public List<NicUploadJob> getListByStatusAndYear(String[] recordStatuses,
			int year, int type) {
		List<NicUploadJob> listAll = new ArrayList<NicUploadJob>();
		try {
			List<NicUploadJob> list = dao.getListByStatusAndYear(recordStatuses, Calendar.getInstance());
			if(list != null && list.size() > 0){
				if(type == 0){
					//int year = calendar.get(Calendar.YEAR) - 1;
					for(NicUploadJob job : list){
						if((year - 1) == HelperClass.getYearInDate(job.getCreateDatetime())){
							listAll.add(job);
						}
					}
				}else{
					//int year = calendar.get(Calendar.YEAR);
					for(NicUploadJob job : list){
						if(year == HelperClass.getYearInDate(job.getCreateDatetime())){
							listAll.add(job);
						}
					}
				}
				return listAll;
			}
			
		} catch (Exception e) {
			// TODO: handle exception
		}
		return null;
	}

	@Override
	public PaginatedResult<NicUploadJobDto> findAllForInvestigationPagination3(
			String[] recordStatuses, String officerId, String leaderId,
			int pageNo, int pageSize, Boolean assignedOnly,
			AssignmentFilterAll assignmentFilter)
			throws NicUploadJobServiceException {
		List<NicUploadJobDto> joblstDto = new ArrayList<NicUploadJobDto>(); 
		PaginatedResult<NicUploadJob> pr = null;		
		PaginatedResult<NicUploadJobDto> pageResult = new PaginatedResult<NicUploadJobDto>();
		try {			
			pr = dao.findInvestigationJobForPaginationByOfficerId1(recordStatuses, officerId, leaderId,pageNo, pageSize, assignedOnly, assignmentFilter);
						
			if (pr != null) {		
				List<NicUploadJob> list = pr.getRows();
				for (NicUploadJob record : list) {			
					
					logger.info("Upload Job Id=============>>>>>>>>>>>>>>>>>>>" + record.getWorkflowJobId());
					NicUploadJobDto dto = new NicUploadJobDto();
					dto.setUploadJobId(record.getWorkflowJobId());
					dto.setTransactionId(record.getTransactionId());
					
					CodeValues codeValue = codesSevice.getCodeValueByIdName("JOB_TYPE", record.getJobType());

					if (codeValue != null) {
						dto.setJobType(codeValue.getCodeValueDesc());
					} else {
						dto.setJobType(record.getJobType());
					}										
					dto.setStageList("Đã xử lý");
					dto.setPriority(3);										
					dto.setInvestigationType(record.getInvestigationType());
					dto.setJobCurrentState(record.getJobCurrentStage());
					dto.setInvestigationStatus(record.getInvestigationStatus());
					if(StringUtils.isNotEmpty(record.getInvestigationOfficerId())){
						Users users = userService.findByUserId(record.getInvestigationOfficerId());
						if(users != null){
							dto.setLeaderId(users.getUserName());
						}						
					}
					dto.setInvestigationOfficerId(record.getInvestigationOfficerId());
					dto.setJobUploadTime(record.getJobCreatedTime());
//					if(record.getValidateInfoBca() != null){
//						if(record.getValidateInfoBca() == 1)
//							dto.setValidateInfoBca_des("Đã gửi");
//						else if(record.getValidateInfoBca() == 2)
//							dto.setValidateInfoBca_des("Đã có kết quả từ BCA");
//						else {
//							dto.setValidateInfoBca_des("Không");
//						}
//							
//					}
					if(!record.getInvestigationStatus().equals("02")){
						NicListHandover handover = handoverDao.findHandoverByTransactionId(record.getTransactionId(), NicListHandover.TYPE_LIST_B, 1, true);
						if(record.getInvestigationStatus().equals("02")){
							dto.setStageList("Đã xử lý");
							//dto.setPriority(3);
						}else if(record.getInvestigationStatus().equals("01") && record.getInvestigationOfficerId() == null && handover == null){
							dto.setStageList("Chưa xử lý");
							//dto.setPriority(1);
						}else if((record.getInvestigationStatus().equals("01") && record.getInvestigationOfficerId() != null && handover == null)){ //|| record.getApproveFlag() != null){
							dto.setStageList("Đang xử lý");
							//dto.setPriority(2);
						}else if(record.getInvestigationStatus().equals("01") && handover != null){ //&& record.getApproveFlag() == null){
							dto.setStageList("Đợi bổ sung");
							//dto.setPriority(4);
						}						
					}
					
					dto.setPassportNo(record.getTempPassportNo());
					dto.setPersoCheckStatus(record.getPersoRegisterStatus());
					dto.setRecieptNo(record.getReceiptNo());
					
//					NicDocumentData nicData = documentDataService.findByDocNumber(record.getTempPassportNo());
//					if(nicData != null){
//						/*Kiểm tra cho phép đồng bộ sang NIC Cục lãnh sự */
//						if(record.getPersoRegisterStatus() != null && record.getPersoRegisterStatus().equals(NicUploadJob.RECORD_STATUS_APPROVED) 
//								&&nicData.getStatus().equals("ISSUANCE")
//								&& nicData.getIssuedFlag() && nicData.getActiveFlag())
//						{
//							dto.setSyncNic(true);
//						}
//						else
//							dto.setSyncNic(false);
//						
//						dto.setPackageId(nicData.getPackageId());
//					}
//					else
//						dto.setSyncNic(false);
					/*
					if(record.getPriority() == 0)
						dto.setPriorityString("Thông thường");*/
//					NicTransaction nicTran = nicTransactionService.getNicTransactionById(record.getTransactionId());
//					if(record.getJobType().equals("LOS"))
//						logStr = nicTran.getNicRegistrationData().getReasonKind();
//					dto.setNoteInvestigation(logStr);
//					if(nicTran!= null){
//						dto.setNicSiteCode(nicTran.getNicSiteCode());
//					}
					joblstDto.add(dto);
				}
				pageResult.setRows(joblstDto);
				pageResult.setTotal(pr.getTotal());
				pageResult.setPage(pageNo);
			}
		} catch (Exception ex) {
			logger.error("Error occurred while getting the investigation job list. Exception: " + ex.getMessage());
			throw new NicUploadJobServiceException(ex);
		}
		return pageResult;
	}

	@Override
	public boolean updateNicUploadJob(NicUploadJob job) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public String testInvestigationProcessByJobId(Long[] JobList,
			String officerId, AssignmentFilterAll assignmentFilter) {
		List<NicUploadJob> pr = null;	
		try {
			pr = dao.findInvestigationProcessByJobId(JobList, null, assignmentFilter);
			if (pr != null && pr.size() > 0) {
				List<NicUploadJob> jobList = pr;
				for (NicUploadJob record : jobList) {
					if(record.getInvestigationOfficerId() != null && !record.getInvestigationOfficerId().equals(officerId)){
						return record.getTransactionId();
					}
				}				
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.error("Error occurred while getting the investigation job list. Exception: " + ex.getMessage());
		}
		return "";
	}

	@Override
	public List<NicUploadJobDto> findInvestigationJobApprove(String packageId,
			AssignmentFilterAll assignmentFilter) {
		List<NicUploadJobDto> joblstDto = new ArrayList<NicUploadJobDto>(); 
		List<NicUploadJob> pr = null;
		try {
			pr = dao.findInvestigationJobApprove(packageId, assignmentFilter);
			if (pr != null) {
			
				List<NicUploadJob> jobList = pr;
				for (NicUploadJob record : jobList) {
					
					logger.info("Upload Job Id=============>>>>>>>>>>>>>>>>>>>" + record.getWorkflowJobId());
					NicUploadJobDto dto = new NicUploadJobDto();
					dto.setUploadJobId(record.getWorkflowJobId());
					dto.setTransactionId(record.getTransactionId());					
					dto.setPackageId(record.getNicTransaction().getPackageId());
					if(StringUtils.isNotEmpty(record.getNicTransaction().getPackageId())){
						List<NicListHandover> lh = handoverDao.findListHandoverByPackageId(new NicListHandoverId(packageId, null), null).getListModel();
						if(lh != null && lh.size() > 0){
							NicListHandover lh_ = lh.get(0);
//							dto.setArchiveCode(lh_.getArchiveCode());
//							String sstt = lh_.getArchiveCode().substring(7);
//							int stt = Integer.parseInt(sstt);
//							dto.setArchiveCodeStt(stt);
							dto.setHandoverStatus(lh.get(0).getHandoverStatus() == 0 ? "K" : "C");
						}
					}
					dto.setPassportNo(record.getTempPassportNo());
					NicTransaction nicTran = nicTransactionService.getNicTransactionById(record.getTransactionId()).getModel();
					if(nicTran!= null && nicTran.getNicRegistrationData() != null){
						dto.setNicSiteCode(nicTran.getNicSiteCode());
						dto.setFullName(HelperClass.createFullName(nicTran.getNicRegistrationData().getSurnameFull(), nicTran.getNicRegistrationData().getMiddlenameFull(), nicTran.getNicRegistrationData().getFirstnameFull()));
						if(!StringUtils.isEmpty(nicTran.getNicRegistrationData().getGender())){
							dto.setGender(nicTran.getNicRegistrationData().getGender().equals("M") ? "Nam" : "Nữ");
						}else{
							dto.setGender("Không");
						}
						String dateDob = HelperClass.convertDateToString(nicTran.getNicRegistrationData().getDateOfBirth());
						dto.setDob(com.nec.asia.nic.utils.HelperClass.loadDateOfBirth(dateDob, nicTran.getNicRegistrationData().getDefDateOfBirth()));
						//dto.setDob(nicTran.getNicRegistrationData().getDateOfBirth() != null ? HelperClass.convertDateToString(nicTran.getNicRegistrationData().getDateOfBirth()) : "");
						dto.setNin(nicTran.getNin());
						dto.setPlaceOfBirth(nicTran.getNicRegistrationData().getPlaceOfBirth());
						dto.setRegSiteCode(nicTran.getRegSiteCode());
						dto.setEsColectionDate(nicTran.getEstDateOfCollection() != null ? HelperClass.convertDateToString(nicTran.getEstDateOfCollection()) : "");
						/*dto.setLeaderId(nicTran.getLeaderOfficerId());
						if(StringUtils.isNotEmpty(nicTran.getLeaderOfficerId())){
							Users users = userService.findByUserId(nicTran.getLeaderOfficerId());
							if(users != null)
								dto.setPosition(users.getPosition());
						}*/
						//dto.setLeaderNote(nicTran.getNoteHandoverB());
						//dto.setNoteApprove(record.getNoteApprove());
						dto.setNoteApprovePerson(record.getNoteApprovePerson() != null ? record.getNoteApprovePerson() : "");
						dto.setNoteApproveObj(record.getNoteApproveObj() != null ? record.getNoteApproveObj() : "");
						dto.setNoteApproveNin(record.getNoteApproveNin() != null ? record.getNoteApproveNin() : "");
						NicTransactionPackage tranPack = tranPackageDao.getListPackageByPackageIdAndTranID(packageId, nicTran.getTransactionId());
						if(tranPack != null){
							dto.setStageList(tranPack.getStage());
							dto.setLeaderNote(tranPack.getNoteApprove());							
						}
					}
					joblstDto.add(dto);
				}			
			}
		} catch (Exception ex) {
			logger.error("Error occurred while getting the investigation job list. Exception: " + ex.getMessage());
			//throw new NicUploadJobServiceException(ex);
		}
		return joblstDto;
	}

	@Override
	public List<NicUploadJobDto> findListHandoverListBApprove(int styleList,
			String leaderId, String asignId,
			AssignmentFilterAll assignmentFilter) {
		List<NicUploadJobDto> joblstDto = new ArrayList<NicUploadJobDto>(); 
		List<NicListHandover> pr = null;
		//PaginatedResult<NicUploadJob> prDSQ = null;
		//PaginatedResult<NicUploadJobDto> pageResult = new PaginatedResult<NicUploadJobDto>();
		try {
			// NicUploadJob nicUploadJob = new NicUploadJob();
			// nicUploadJob.setInvestigationOfficerId(userId);
			pr = handoverDao.findListHandoverListBApprove(styleList, leaderId, asignId, assignmentFilter);
			/*Xóa hồ sơ bị trùng*/
			Set<NicListHandover> setPr = new HashSet<NicListHandover>();
			for(NicListHandover handover : pr){
				setPr.add(handover);
			}
			/*end*/
			// dao.findAllForPagination(nicUploadJob, pageNo,
			// pageSize);//dao.findAllInvestigationForPagination(nicUploadJob,
			// pageNo, pageSize);
			if (pr != null) {
			
				Set<NicListHandover> jobList = setPr;
				//Set<String> listPackage = new HashSet<String>();
				for (NicListHandover record : jobList) {					
					logger.info("Handover dao=============>>>>>>>>>>>>>>>>>>>" + record.getId().getPackageId());
					NicUploadJobDto dto = new NicUploadJobDto();
					dto.setPackageId(record.getId().getPackageId());
					dto.setPackageDate(HelperClass.convertDateToString(record.getCreateDate()));
					if(StringUtils.isNotEmpty(record.getProcessUsers())){
						Users users = userService.findByUserId(record.getProcessUsers());
						if(users != null)
							dto.setRicName(users.getUserName());
						
					}
					dto.setNumberTran(record.getCountTransaction() + "");
					joblstDto.add(dto);					
				}


			}
		} catch (Exception ex) {
			logger.error("Error occurred while getting the investigation job list. Exception: " + ex.getMessage());
			//throw new NicUploadJobServiceException(ex);
		}
		return joblstDto;
	}

	@Override
	public BaseModelSingle<NicUploadJob> findUploadJobByTransId(String txnId) throws Exception {
		try {
			BaseModelList<NicUploadJob> list = dao.findAllByTransactionId(txnId);
			if(list.getListModel() != null && list.getListModel().size() > 0)
				return  new BaseModelSingle<NicUploadJob>(list.getListModel().get(0), true, null);
		} catch (Exception e) {
			return new BaseModelSingle<NicUploadJob>(null, false, CreateMessageException.createMessageException(e) + " - findUploadJobByTransId - " + txnId + " - thất bại.");
		}
		return new BaseModelSingle<NicUploadJob>(null, true, null);
	}

	@Override
	public BaseModelSingle<NicUploadJob> findNicUpByReceiptNo(String packageId, String receiptNo, String transactionId) {
		try {
			return dao.findNicUpByReceiptNo(packageId, receiptNo, transactionId);
		} catch (Exception e) {
			return new BaseModelSingle<NicUploadJob>(null, false, CreateMessageException.createMessageException(e) + " - findNicUpByReceiptNo - " + receiptNo + " - thất bại.");
		}
	}

	@Override
	public PaginatedResult<NicUploadJob> getPageJobsInQueue(String[] listTranId, int pageNo, int pageSize) throws NicUploadJobServiceException {
		try {
			Map<String, String> configMap = this.getConfigMapFromParameters();
			return this.dao.getPageJobsInQueue(listTranId, pageNo, pageSize, configMap);
		} catch (Exception e) {
			throw new NicUploadJobServiceException(e);
		}
	}
}
