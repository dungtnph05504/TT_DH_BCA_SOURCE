package com.nec.asia.nic.reprint;

import java.io.IOException;
import java.sql.Blob;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.ModelAndView;

import com.nec.asia.nic.common.dto.NicDboToDtoUtil;
import com.nec.asia.nic.common.dto.RicNewRegistrationDTO;
import com.nec.asia.nic.common.dto.RicTransactionLogDTO;
import com.nec.asia.nic.comp.job.dto.HitCandidateDTO;
import com.nec.asia.nic.comp.job.dto.LogInfoDTO;
import com.nec.asia.nic.comp.trans.domain.NicRegistrationData;
import com.nec.asia.nic.comp.trans.domain.NicTransaction;
import com.nec.asia.nic.comp.trans.domain.NicTransactionAttachment;
import com.nec.asia.nic.comp.trans.domain.NicTransactionLog;
import com.nec.asia.nic.comp.trans.dto.NicRegistrationDataDto;
import com.nec.asia.nic.comp.trans.service.NicRegistrationDataService;
import com.nec.asia.nic.comp.trans.service.NicTransactionAttachmentService;
import com.nec.asia.nic.comp.trans.service.NicTransactionService;
import com.nec.asia.nic.comp.trans.service.NicUploadJobService;
import com.nec.asia.nic.comp.trans.service.exception.NicUploadJobServiceException;
import com.nec.asia.nic.framework.admin.code.domain.CodeValues;
import com.nec.asia.nic.framework.admin.code.service.CodesService;
import com.nec.asia.nic.framework.PageRequest;
import com.nec.asia.nic.framework.PaginatedResult;
import com.nec.asia.nic.framework.controller.AbstractController;
import com.nec.asia.nic.framework.exception.JaxbXmlConvertorException;
import com.nec.asia.nic.reprint.dto.TransactionDocumentDto;
import com.nec.asia.nic.util.Constants;
import com.nec.asia.nic.util.JobXmlConvertor;
import com.nec.asia.nic.util.RegistrationConstants;
import com.nec.asia.nic.util.TransLogInfoXmlConvertor;
import com.nec.asia.nic.utils.DateUtil;
import com.nec.asia.nic.utils.StringUtil;
import com.nec.asia.nic.web.session.UserSession;

/**
 * @author franco_conte
 * @Company: NEC Asia Pacific Ltd
 * @Since: Sep 2, 2013
 */

@Controller
@RequestMapping(value = "/rePrintController")
public class ReprintController extends AbstractController {

	private static final Logger logger = LoggerFactory.getLogger(ReprintController.class);
	
	public static TransLogInfoXmlConvertor logUtil = new TransLogInfoXmlConvertor();

	private static final String SITE_CODE = "SITE_CODE";

	private static final String RECORD_STATUS = "RECORD_STATUS";

	private static final String GENDER = "GENDER";

	private static final String TRANSACTION_STATUS = "TRANSACTION_STATUS";

	private Map<String, String> siteCodeValues;

	@Autowired
	private NicUploadJobService uploadJobService;

	@Autowired
	private NicTransactionService nicTransactionService;
	
	@Autowired
	private NicRegistrationDataService nicRegistrationDataService;

	@Autowired
	private NicTransactionAttachmentService nicTransactionDocumentService;

	@Autowired
	private CodesService codesService;

	private Map<String, String> allCodeValues;
	

	@RequestMapping(value = "/init")
	public String txnEnquiryInit(WebRequest request, ModelMap model) {
		logger.info("NIC Enquiry Page");
		try {
			Map<String, String> siteCodeList = uploadJobService.getCodeValues(SITE_CODE);
			Map<String, String> transStatusList = uploadJobService.getCodeValues(TRANSACTION_STATUS);
			Map<String, String> genderList = uploadJobService.getCodeValues(GENDER);
			model.addAttribute("genderList", getCodeValueList(GENDER));
			model.addAttribute("siteCodeList", getCodeValueList(SITE_CODE));
			model.addAttribute("transStatusList", getCodeValueList(TRANSACTION_STATUS));
			//model.addAttribute("transactionEnquiryForm",transactionEnquiryForm);
			model.addAttribute("nicRegData", new NicRegistrationDataDto());
			model.addAttribute("fnSelected", Constants.HEADING_NIC_REPRINT);
			return "show.transactionEnquiry";

		} catch (Exception e) {

			e.printStackTrace();
			return null;
		}

	}

	@ResponseBody
	@RequestMapping(value = "/search", method = RequestMethod.POST)
	public PaginatedResult<NicRegistrationDataDto> rePrintSearchTransactionList(WebRequest request, 
			@ModelAttribute(value = "nicRegData") NicRegistrationDataDto nicRegData) {
		PaginatedResult<NicRegistrationDataDto> pr = null;
		try {

			String pages = request.getParameter("page");
			String sortname = request.getParameter("sortname");
			String sortorder = request.getParameter("sortorder");
			String rp = request.getParameter("rp");
			String recordPerPage = request.getParameter("rp");

			if (!StringUtils.isNotBlank(recordPerPage)) {
				recordPerPage = "50";
			}

			int pageSize = 20;
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
			pr = nicRegistrationDataService.findAllForPaginationOnReprint(pageRequest, nicRegData);

			return pr;
		} catch (Exception e) {

			e.printStackTrace();
			return null;
		}
	}

	@ResponseBody
	@RequestMapping(value = "/searchPhotos", method = RequestMethod.POST)
	public ModelAndView txnPhotoSearch(WebRequest request, ModelMap model, 
			@ModelAttribute(value = "nicRegData") NicRegistrationDataDto nicRegData) {
		List<NicTransactionAttachment> pr = null;
		try {

			String pages = request.getParameter("page");
			String sortname = request.getParameter("sortname");
			String sortorder = request.getParameter("sortorder");
			String rp = request.getParameter("rp");
			String recordPerPage = request.getParameter("rp");

			if (!StringUtils.isNotBlank(recordPerPage)) {
				recordPerPage = "50";
			}

			int pageSize = 20;
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
			try {
				pageRequest.setPageNo(Integer.parseInt(pageNumber));
			} catch (Exception e) {
				pageRequest.setPageNo(1);
			}

			List<TransactionDocumentDto> docDtos = new ArrayList<TransactionDocumentDto>();
			List<Object[]> photos = nicTransactionService.getNicTransactionAttachmentByDocType(nicRegData);
			int i = 1;
			if(photos != null) {
				for(Object[] pho : photos) {
					TransactionDocumentDto doc = new TransactionDocumentDto();
					doc.setTransactionId((String)pho[0]);
					doc.setNin((String)pho[1]);
					Blob p = (Blob)pho[2];
					if(p == null) continue;
					doc.setDocData(p.getBytes(1, (int)p.length()));
					String decodedDocString = Base64.encodeBase64String(doc.getDocData());
					doc.setDecDocData(decodedDocString);
					docDtos.add(doc);
					logger.info("{}", i);
					i++;
				}
			}
			
			return new ModelAndView("show.transactionEnquiryPhotos", "photolist", docDtos);
		} catch (Exception e) {

			e.printStackTrace();
			return null;
		}
	}
	
	@RequestMapping(value = "/viewDetails/{transId}", method = RequestMethod.GET)
	public ModelAndView viewTransactionDetails(
			@PathVariable String transId,
			WebRequest request,
			ModelMap model,
			HttpServletResponse response) throws Exception {
		boolean flag=false;
		NicTransaction nic = nicTransactionService.findById(transId, true, true, true, true);
		NicDboToDtoUtil nicDboToDtoUtil = new NicDboToDtoUtil();
		RicNewRegistrationDTO transDetail = nicDboToDtoUtil.getDto(nic);

		transDetail.setTransactionId(nic.getTransactionId());

		transDetail.setTransactionTypeDesc(getCodeValueDesc("TRANSACTION_TYPE", nic.getTransactionType()));
//		transDetail.setTransactionSubTypeDesc(getCodeValueDesc("TRANSACTION_SUBTYPE", nic.getTransactionSubtype()));
		transDetail.setTransactionStatusDesc(getCodeValueDesc("TRANSACTION_STATUS", nic.getTransactionStatus()));
		transDetail.setIssueSite(getCodeValueDesc("SITE_CODE", nic.getIssSiteCode()));
		transDetail.setRegSiteCode(getCodeValueDesc("SITE_CODE", nic.getRegSiteCode()));

		transDetail.getPerson().setGenderDesc(getCodeValueDesc("GENDER", transDetail.getPerson().getGender()));
		transDetail.getPerson().setMaritalStatusDesc(getCodeValueDesc("MARITAL_STATUS", transDetail.getPerson().getMaritalStatus()));

		transDetail.setTownDisp(transDetail.getTown() + " " + getCodeValueDesc("TOWN_VILLAGE", transDetail.getTown()));
		transDetail.setDistrictDisp(transDetail.getDistrict() + " " + getCodeValueDesc("DISTRICT", transDetail.getDistrict()));
		
		transDetail.setTown(getCodeValueDesc("TOWN_VILLAGE", transDetail.getTown()));
		transDetail.setOverseasCountry(getCodeValueDesc("COUNTRY", transDetail.getOverseasCountry()));

		List<NicTransactionLog> txnLogs = nicTransactionService.findAllTransactionLogsByRefId(nic.getTransactionId());
		List<RicTransactionLogDTO> logDtoList = nicDboToDtoUtil.getTransactionLogs(txnLogs);
		setDescription(logDtoList);
		transDetail.setTransactionLogList(logDtoList);
		
		List<RicTransactionLogDTO> ricLogDtoList = nicDboToDtoUtil.getRicTransactionLogs(txnLogs);
		setDescription(ricLogDtoList);
		transDetail.setRicTransactionLogList(ricLogDtoList);

		/*Set<NicIssuanceData> nicIssuance = nic.getNicIssuanceDatas(); 
		transDetail.setCardStatus(transorCardDetail.getCardStatus());
		transDetail.setCcn(transorCardDetail.getCcNo());
		String trStatus = getCodeDesc(transDetail.getTransactionStatus(),
				RegistrationConstants.CODE_ID_TRANSACTION_STATUS)
				.getDescription();
		transDetail.setTransactionStatusDesc(trStatus);*/

		transDetail.setComingFromRegistration(false);
		transDetail.setComingFromVerification(false);
		transDetail.setComingFromTransQuery(true);
		
		ModelAndView mav = new ModelAndView("show.transactionEnquiryDetail",
				"newRegistration", transDetail);
		mav.addObject("fnSelected","VIEW_TXN : View Application Transaction");
		//Changes if Collection slip is null when retrieved from NIC 20/8/2013 Start
		mav.addObject("viewColSlipFlag",flag);
		//Changes if Collection slip is null when retrieved from NIC 20/8/2013 End
		return mav;
	}
	
	private void setDescription(List<RicTransactionLogDTO> logDtoList) {
		for(RicTransactionLogDTO logDto: logDtoList){
			String txnStage =  logDto.getTransactionStage();
			if(txnStage != null){
				if(txnStage.equalsIgnoreCase(RegistrationConstants.TRANSACTION_STAGE_EXCEPTION_HANDLING)){
					logDto.setReason(populateReasonsDisplay(RegistrationConstants.EXCPTION_HANDLING_REASON_CODE, logDto.getReason()));
				}else if(txnStage.equalsIgnoreCase(RegistrationConstants.TRANSACTION_STAGE_PENDING_SUPERVISOR)){
					logDto.setReason(populateReasonsDisplay(RegistrationConstants.SUPERVISOR_REASON_CODE, logDto.getReason()));
				}else if(txnStage.equalsIgnoreCase(RegistrationConstants.TRANSACTION_STAGE_CANCEL)){
					logDto.setReason(getCodeValueDesc("REASON_CANCEL", logDto.getReason())) ;
				} 
			}
			logDto.setRicSitecode(getCodeValueDesc("SITE_CODE", logDto.getRicSitecode()));
			//logDto.setTransactionStage(getCodeDesc(RegistrationConstants.CODE_ID_TRANSACTION_STAGE, logDto.getTransactionStage()));
			//logDto.setUsername(formUserName(logDto.getUserId()));
		}
		
	}

	private String populateReasonsDisplay(String codeId, String reason){
		String reasonDesc = "";
		if(!StringUtil.isEmpty(reason) && !StringUtil.isEmpty(codeId)){
			String[] reasonArr = reason.split(",");
			for(int i=0; i< reasonArr.length; i++){
				if(i >0){
					reasonDesc += ", " + getCodeValueDesc(codeId, reasonArr[i]); 
				}else{
					reasonDesc = getCodeValueDesc(codeId, reasonArr[i]);
				}
			}
		}
		return reasonDesc;
	}
	
	@ResponseBody
	@RequestMapping(value = "/userDeclarationPdf/{transId}")
	public void printCollectionSlip(
			@PathVariable String transId,
			WebRequest request,
			ModelMap model,
			HttpServletResponse response) {
		byte[] doc = new byte[]{};
		
		List<NicTransactionAttachment> mainCanSig = nicTransactionDocumentService
				.findNicTransactionAttachments(transId,
						RegistrationConstants.DOC_TYPE_USER_DECL,
						NicTransactionAttachment.DEFAULT_SERIAL_NO).getListModel();

		if (CollectionUtils.isNotEmpty(mainCanSig)) {
			doc = mainCanSig.get(0).getDocData();
			//byte[] signBinary = mainCanSig.get(0).getDocData();
			//mainCandidateSignature = Base64.encodeBase64String(signBinary);
		}
		
		response.setContentType("application/pdf");
		try {
			response.getOutputStream().write(doc);
		} catch (IOException e) {
			e.printStackTrace();
		}  
	}
	
	@ResponseBody
	@RequestMapping(value = "/colSlipPdf/{transId}")
	public void colSlipPdf(
			@PathVariable String transId,
			WebRequest request,
			ModelMap model,
			HttpServletResponse response) {
		byte[] doc = new byte[]{};
		
		List<NicTransactionAttachment> mainCanSig = nicTransactionDocumentService
				.findNicTransactionAttachments(transId,
						RegistrationConstants.DOC_TYPE_COL_SLIP,
						NicTransactionAttachment.DEFAULT_SERIAL_NO).getListModel();

		if (CollectionUtils.isNotEmpty(mainCanSig)) {
			doc = mainCanSig.get(0).getDocData();
			//byte[] signBinary = mainCanSig.get(0).getDocData();
			//mainCandidateSignature = Base64.encodeBase64String(signBinary);
		}
		
		response.setContentType("application/pdf");
		try {
			response.getOutputStream().write(doc);
		} catch (IOException e) {
			e.printStackTrace();
		}  
	}

	private String getCodeValueDesc(String codeId, String codeVal) {
		String desc = "";
		try {
			if(codeId != null && codeVal != null) {
				CodeValues code = codesService.getCodeValueByIdName(codeId, codeVal);
				if(code != null) {
					desc = code.getCodeValueDesc();
				}
			}
		} catch (Exception e) {
		}
		return desc;
	}

	private String getDateDisp(Date date) {
		return DateUtil.parseDate(date, DateUtil.FORMAT_DDdashMMMdashYYYY);
	}

	/*private String getCodeDesc(String codeVal) {
		if(StringUtils.isEmpty(codeVal)) return "";
		return getAllCodeValues().get(codeVal);
	}*/
	
	private Map<String, String> getAllCodeValues() {
		if(this.allCodeValues == null){
			this.allCodeValues = codesService.getAllCodeValues(); 
		}
		return this.allCodeValues;
	}	
	
	private Map<String, String> getSiteCodeValues() {
		if(this.siteCodeValues == null){
			this.siteCodeValues = codesService.getCodeValues(SITE_CODE); 
		}
		return this.siteCodeValues;
	}	

	private List<CodeValues> getCodeValueList(String codeId) {
		return codesService.findAllByCodeId(codeId);
	}	
	
	
	@RequestMapping( value = "/reprintUpdate/{transIds}")
	public @ResponseBody String updateDataUponReprint(@PathVariable String transIds, HttpServletRequest httpRequest, Model model){
		logger.info("NIC Reprint=============== Dialog Open and update the tables upon submit");
		String status = "success";
		String transactionStage = "REPRINT";
		String transactionStatus = "APPROVE";
		String transactionId = "";
		String transactionType = "";
		String remarksData = "";
		LogInfoDTO logInfoDto = null;
		String remarksDataMarshal = "";
		if(transIds !=null){
			if(transIds.contains(",")){
				String mainTransIds [] =transIds.split(",");
				transactionId= mainTransIds [0];
				remarksData=mainTransIds [1];
			 }
			}
		logger.info("Transaction Id is ===================>>>>>>>>>>>"+transactionId);
		logger.info("Remarks Data ===================>>>>>>>>>>>"+remarksData);
		HttpSession session = httpRequest.getSession();
		UserSession userSession = (UserSession) session.getAttribute("userSession");
		String userId = userSession.getUserName();
		String wkstnId = userSession.getWorkstationId();
		NicTransaction nicTransaction = null;
		try {
			
			nicTransaction =  nicTransactionService.findById(transactionId);
			if(nicTransaction!=null){
				transactionType = nicTransaction.getTransactionType();
			}
			
			uploadJobService.updatePersoRegisterStatusOnReprint(transactionId, transactionType, userId, wkstnId);//Update NicUploadJob
			
			nicTransactionService.updateTransactionStatusOnReprint(transactionId, userId, wkstnId);//Update NicTransaction 
			
			NicTransactionLog nicTransactionLog = uploadJobService.getNicTransactionLog(transactionId, transactionStage, transactionStatus);
			if(nicTransactionLog!=null){
				logInfoDto = new LogInfoDTO();
				logInfoDto.setRemark(remarksData);
				try {
					remarksDataMarshal = logUtil.marshal(logInfoDto);
				} catch (JaxbXmlConvertorException e) {
					e.printStackTrace();
				}
				nicTransactionLog.setLogInfo(remarksDataMarshal);
				uploadJobService.saveNicTransactionLog(nicTransactionLog);
				}else{
				nicTransactionLog = new NicTransactionLog();
				nicTransactionLog.setRefId(transactionId);
				nicTransactionLog.setTransactionStage(transactionStage);
				nicTransactionLog.setTransactionStatus(transactionStatus);
				nicTransactionLog.setWkstnId(userSession.getWorkstationId());
				nicTransactionLog.setOfficerId(userSession.getUserName());
				nicTransactionLog.setLogCreateTime(new Date());
				//Marshal the remarks
				logInfoDto = new LogInfoDTO();
				logInfoDto.setRemark(remarksData);
				try {
					remarksDataMarshal = logUtil.marshal(logInfoDto);
				} catch (JaxbXmlConvertorException e) {
					e.printStackTrace();
				}
				nicTransactionLog.setLogInfo(remarksDataMarshal);
				uploadJobService.saveNicTransactionLog(nicTransactionLog);
			}
			status = "success";
		} catch (NicUploadJobServiceException e) {
			e.printStackTrace();
			status = "error";
		} catch (Exception e) {
			e.printStackTrace();
			status = "error";
		}
		return status;

	}
	
	
}
