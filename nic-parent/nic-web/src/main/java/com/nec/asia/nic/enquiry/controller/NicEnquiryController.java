/**
 * 
 */
package com.nec.asia.nic.enquiry.controller;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.ModelAndView;

import com.nec.asia.nic.common.dto.NicDboToDtoUtil;
import com.nec.asia.nic.common.dto.RicNewRegistrationDTO;
import com.nec.asia.nic.common.dto.RicTransactionLogDTO;
import com.nec.asia.nic.comp.trans.domain.NicMain;
import com.nec.asia.nic.comp.trans.domain.NicTransaction;
import com.nec.asia.nic.comp.trans.domain.NicTransactionAttachment;
import com.nec.asia.nic.comp.trans.domain.NicTransactionLog;
import com.nec.asia.nic.comp.trans.service.NicTransactionAttachmentService;
import com.nec.asia.nic.comp.trans.service.NicTransactionService;
import com.nec.asia.nic.framework.admin.code.domain.CodeValues;
import com.nec.asia.nic.framework.admin.code.service.CodesService;
import com.nec.asia.nic.framework.PageRequest;
import com.nec.asia.nic.framework.PaginatedResult;
import com.nec.asia.nic.framework.controller.AbstractController;
import com.nec.asia.nic.util.RegistrationConstants;
import com.nec.asia.nic.utils.StringUtil;

/**
 * @author franco_conte
 * @Company: NEC Asia Pacific Ltd
 * @Since: Sep 2, 2013
 */

@Controller
@RequestMapping(value = "/nicMainEnquiry")
public class NicEnquiryController extends AbstractController {

	private static final Logger logger = LoggerFactory
			.getLogger(NicEnquiryController.class);

	private static final String SITE_CODE = "SITE_CODE";

	private static final String RECORD_STATUS = "RECORD_STATUS";

	private static final String GENDER = "GENDER";

	@Autowired
	private NicTransactionService nicTransactionService;
	
	@Autowired
	private NicTransactionAttachmentService nicTransactionDocumentService;

	@Autowired
	private CodesService codesService;
	

	@RequestMapping(value = "/init")
	@ExceptionHandler
	public String txnEnquiryInit(WebRequest request, ModelMap model) throws Exception {
		logger.info("NIC Enquiry Page");
		try {
			int pageNo = 1;
			int pageSize = 20;
			int startIndex = 0;

			//TransactionEnquiryForm transactionEnquiryForm = new TransactionEnquiryForm();

			Map<String, String> siteCodeList = codesService.getCodeValues(SITE_CODE);
			
			Map<String, String> recordStatusList = codesService.getCodeValues(RECORD_STATUS);

			Map<String, String> genderList = codesService.getCodeValues(GENDER);

			model.addAttribute("nicMain", new NicMain());
			model.addAttribute("genderList", genderList);
			model.addAttribute("siteCodeList", siteCodeList);
			model.addAttribute("recordStatusList", recordStatusList);
			
			return "show.nicEnquiry";

		} catch (Exception e) {

			e.printStackTrace();
			return null;
		}

	}

	@ResponseBody
	@RequestMapping(value = "/search", method = RequestMethod.POST)
	@ExceptionHandler
	public PaginatedResult<NicMain> nicEnquirySearch(WebRequest request, ModelMap model, NicMain nicMain) throws Exception {
		PaginatedResult<NicMain> pr = null;
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
				// pageSize =
				// Integer.parseInt(configurationService.getUserAdminConsoleJobQueueMaxRecordsPerPage(getUserSession(request).getUserId()).getSettings());
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
			pageRequest.setSortorder(sortorder);
			pageRequest.setSortname(sortname);
			pageRequest.setPageNo(Integer.parseInt(pageNumber));
		
			//pr = nicTransactionService.findAllForPagination(pageRequest, nicMain);

			return pr;
		} catch (Exception e) {

			e.printStackTrace();
			return null;
		}
	}

	@ResponseBody
	@RequestMapping(value = "/viewHistory/{nin}", method = RequestMethod.GET)
	@ExceptionHandler
	public ModelAndView viewHistory(
			@PathVariable String nin,
			WebRequest request,
			ModelMap model,
			HttpServletResponse response) throws Exception {
		//List<NicHistory> nicHistory = null; //nicTransactionService.getNicHistory(nin);
		List nicHistory = null; //nicTransactionService.getNicHistory(nin);
		return new ModelAndView("show.nicHistory", "nicHistory", nicHistory);
	}
	
	@RequestMapping(value = "/viewDetails/{transId}", method = RequestMethod.GET)
	@ExceptionHandler
	public ModelAndView viewDetails(
			@PathVariable String transId,
			WebRequest request,
			ModelMap model,
			HttpServletResponse response) throws Exception {
		boolean flag=false;
		
		NicTransaction nic = nicTransactionService.findById(transId, true, true, true, true);
		NicDboToDtoUtil nicDboToDtoUtil = new NicDboToDtoUtil();
		RicNewRegistrationDTO transDetail = nicDboToDtoUtil.getDto(nic);
		transDetail.setComingFromTransQuery(true);

		transDetail.setTransactionId(nic.getTransactionId());

		transDetail.setTransactionTypeDesc(getCodeValueDesc(RegistrationConstants.CODE_ID_TRANSACTION_TYPE, nic.getTransactionType()));
//		transDetail.setTransactionSubTypeDesc(getCodeValueDesc(RegistrationConstants.CODE_ID_TRANSACTION_SUBTYPE, nic.getTransactionSubtype()));
		transDetail.setTransactionStatusDesc(getCodeValueDesc(RegistrationConstants.CODE_ID_TRANSACTION_STATUS, nic.getTransactionStatus()));
		transDetail.setIssueSite(getCodeValueDesc(RegistrationConstants.CODE_ID_SITE_CODE, nic.getIssSiteCode()));
		transDetail.setRegSiteCode(getCodeValueDesc(RegistrationConstants.CODE_ID_SITE_CODE, nic.getRegSiteCode()));

//		transDetail.setTransactionStageDesc(getCodeValueDesc(nic.getTransactionStage(), RegistrationConstants.CODE_ID_TRANSACTION_STAGE));
		
		if(transDetail.getPerson() != null) {
			transDetail.getPerson().setGenderDesc(getCodeValueDesc(RegistrationConstants.CODE_ID_GENDER, transDetail.getPerson().getGender()));
			transDetail.getPerson().setMaritalStatusDesc(getCodeValueDesc(RegistrationConstants.CODE_ID_MARITAL_STATUS, transDetail.getPerson().getMaritalStatusDesc()));
		}

		transDetail.setTown(getCodeValueDesc(RegistrationConstants.CODE_ID_TOWN, transDetail.getTown()));
		transDetail.setOverseasCountry(getCodeValueDesc(RegistrationConstants.CODE_ID_COUNTRY, transDetail.getOverseasCountry()));
		
		List<NicTransactionLog> txnLogs = nicTransactionService.findAllTransactionLogsByRefId(nic.getTransactionId());
		List<RicTransactionLogDTO> logDtoList = nicDboToDtoUtil.getTransactionLogs(txnLogs);
		setDescription(logDtoList);
		transDetail.setTransactionLogList(logDtoList);
		
		/*Set<NicIssuanceData> nicIssuance = nic.getNicIssuanceDatas(); 
		transDetail.setCardStatus(transorCardDetail.getCardStatus());
		transDetail.setCcn(transorCardDetail.getCcNo());
		String trStatus = getCodeValueDesc(transDetail.getTransactionStatus(),
				RegistrationConstants.CODE_ID_TRANSACTION_STATUS)
				.getDescription();
		transDetail.setTransactionStatusDesc(trStatus);*/

		ModelAndView mav = new ModelAndView("show.nicEnquiryDetail",
				"newRegistration", transDetail);
		mav.addObject("fnSelected",
				"VIEW_TXN : View Application Transaction");
		mav.addObject("viewColSlipFlag",flag);

		return mav;
	}

	@ResponseBody
	@RequestMapping(value = "/userDeclarationPdf/{transId}")
	@ExceptionHandler
	public void printCollectionSlip(
			@PathVariable String transId,
			WebRequest request,
			ModelMap model,
			HttpServletResponse response) throws Exception{
		byte[] doc = null;
		
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
			// TODO Auto-generated catch block
			e.printStackTrace();
		}  
	}
	
	@ResponseBody
	@RequestMapping(value = "/colSlipPdf/{transId}")
	@ExceptionHandler
	public void colSlipPdf(
			@PathVariable String transId,
			WebRequest request,
			ModelMap model,
			HttpServletResponse response) throws Exception{
		byte[] doc = null;
		
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
			// TODO Auto-generated catch block
			e.printStackTrace();
		}  
	}
	
	private void setDescription(List<RicTransactionLogDTO> logDtoList) {
		for(RicTransactionLogDTO logDto: logDtoList){
			if(logDto.getTransactionStage().equalsIgnoreCase(RegistrationConstants.TRANSACTION_STAGE_EXCEPTION_HANDLING)){
				logDto.setReason(populateReasonsDisplay(RegistrationConstants.EXCPTION_HANDLING_REASON_CODE, logDto.getReason()));
			}else if(logDto.getTransactionStage().equalsIgnoreCase(RegistrationConstants.TRANSACTION_STAGE_PENDING_SUPERVISOR)){
				logDto.setReason(populateReasonsDisplay(RegistrationConstants.SUPERVISOR_REASON_CODE, logDto.getReason()));
			}else if(logDto.getTransactionStage().equalsIgnoreCase(RegistrationConstants.TRANSACTION_STAGE_CANCEL)){
				logDto.setReason(getCodeValueDesc(RegistrationConstants.CANCELLATION_REASON_CODE, logDto.getReason())) ;
			} 
			logDto.setRicSitecode(getCodeValueDesc(RegistrationConstants.RIC_SITE_CODE, logDto.getRicSitecode()));
			logDto.setTransactionStage(getCodeValueDesc(RegistrationConstants.CODE_ID_TRANSACTION_STAGE, logDto.getTransactionStage()));
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
	
	private String getCodeValueDesc(String codeId, String codeVal) {
		String desc = "UNKNOWN";
		try {
			CodeValues code = codesService.getCodeValueByIdName(codeId, codeVal);
			if(code != null) {
				desc = code.getCodeValueDesc();
			}
		} catch (Exception e) {
		}
		return desc;
	}


	
}
