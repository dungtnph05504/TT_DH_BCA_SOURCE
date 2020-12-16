package com.nec.asia.nic.investigation.controller;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.io.StringReader;
import java.text.DateFormat;
import java.text.Normalizer;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.swing.text.html.StyleSheet.ListPainter;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.record.formula.functions.Hlookup;
import org.displaytag.tags.TableTagParameters;
import org.displaytag.util.ParamEncoder;
import org.hibernate.criterion.Order;
import org.hibernate.metamodel.relational.Identifier;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.ModelAndView;

import com.nec.asia.idserver.spec.itf.cmd.action.jobpackage.GetTransactionDataAction;
import com.nec.asia.nic.common.DmsUtility;
import com.nec.asia.nic.common.dto.NinDetailsDTO;
import com.nec.asia.nic.common.dto.RicRegistrationDocumentDTO;
import com.nec.asia.nic.comp.bufPersonInvestigation.service.BufPersonInvestigationService;
import com.nec.asia.nic.comp.immihistory.domain.ImmiHistory;
import com.nec.asia.nic.comp.job.command.NicCommandExecutor;
import com.nec.asia.nic.comp.job.command.NicSubmitPersoCommand;
import com.nec.asia.nic.comp.listHandover.domain.NicListHandover;
import com.nec.asia.nic.comp.listHandover.domain.NicListHandoverId;
import com.nec.asia.nic.comp.listHandover.service.ListHandoverService;
import com.nec.asia.nic.comp.listHandover.service.PersoLocationsService;
import com.nec.asia.nic.comp.signerGovs.service.SignerGovsService;
import com.nec.asia.nic.comp.trans.dao.DocumentDataDao;
import com.nec.asia.nic.comp.trans.dao.NicSearchResultDao;
import com.nec.asia.nic.comp.trans.domain.BufEppPersonInvestigation;
import com.nec.asia.nic.comp.trans.domain.EppBlacklist;
import com.nec.asia.nic.comp.trans.domain.EppBlacklistAttachment;
import com.nec.asia.nic.comp.trans.domain.EppIdentification;
import com.nec.asia.nic.comp.trans.domain.EppIdentificationAttachmnt;
import com.nec.asia.nic.comp.trans.domain.EppPerson;
import com.nec.asia.nic.comp.trans.domain.EppPersonAttchmnt;
import com.nec.asia.nic.comp.trans.domain.EppPersonFamily;
import com.nec.asia.nic.comp.trans.domain.NicAfisData;
import com.nec.asia.nic.comp.trans.domain.NicDocumentData;
import com.nec.asia.nic.comp.trans.domain.NicRegistrationData;
import com.nec.asia.nic.comp.trans.domain.NicSearchHitResult;
import com.nec.asia.nic.comp.trans.domain.NicSearchResult;
import com.nec.asia.nic.comp.trans.domain.NicTransaction;
import com.nec.asia.nic.comp.trans.domain.NicTransactionAttachment;
import com.nec.asia.nic.comp.trans.domain.NicTransactionLog;
import com.nec.asia.nic.comp.trans.domain.NicTransactionLost;
import com.nec.asia.nic.comp.trans.domain.NicUploadJob;
import com.nec.asia.nic.comp.trans.dto.AssignmentFilterAll;
import com.nec.asia.nic.comp.trans.dto.EppBufFamilyDto;
import com.nec.asia.nic.comp.trans.dto.EppBufInfoDocDto;
import com.nec.asia.nic.comp.trans.dto.InfoPersonDto;
import com.nec.asia.nic.comp.trans.dto.NicUploadJobDto;
import com.nec.asia.nic.comp.trans.service.DocumentDataService;
import com.nec.asia.nic.comp.trans.service.EppPersonService;
import com.nec.asia.nic.comp.trans.service.NicAfisDataService;
import com.nec.asia.nic.comp.trans.service.NicBlacklistService;
import com.nec.asia.nic.comp.trans.service.NicIdentificationService;
import com.nec.asia.nic.comp.trans.service.NicImmiHistoryService;
import com.nec.asia.nic.comp.trans.service.NicRegistrationDataService;
import com.nec.asia.nic.comp.trans.service.NicSearchHitResultService;
import com.nec.asia.nic.comp.trans.service.NicTransactionAttachmentService;
import com.nec.asia.nic.comp.trans.service.NicTransactionLostService;
import com.nec.asia.nic.comp.trans.service.NicTransactionService;
import com.nec.asia.nic.comp.trans.service.NicUploadJobService;
import com.nec.asia.nic.comp.trans.service.exception.DocumentDataServiceException;
import com.nec.asia.nic.comp.trans.service.exception.NicAfisDataServiceException;
import com.nec.asia.nic.comp.trans.service.exception.NicUploadJobServiceException;
import com.nec.asia.nic.comp.trans.service.exception.TransactionServiceException;
import com.nec.asia.nic.comp.workflowProcess.service.WorkflowProcessService;
import com.nec.asia.nic.dx.trans.BufEppPersonDoc;
import com.nec.asia.nic.enquiry.controller.FingerprintInfo;
import com.nec.asia.nic.framework.PaginatedResult;
import com.nec.asia.nic.framework.admin.code.domain.CodeValues;
import com.nec.asia.nic.framework.admin.code.domain.Parameters;
import com.nec.asia.nic.framework.admin.code.domain.ParametersId;
import com.nec.asia.nic.framework.admin.code.domain.PaymentDef;
import com.nec.asia.nic.framework.admin.code.service.CodesService;
import com.nec.asia.nic.framework.admin.code.service.ParametersService;
import com.nec.asia.nic.framework.admin.code.service.PaymentDefService;
import com.nec.asia.nic.framework.admin.rbac.domain.Users;
import com.nec.asia.nic.framework.admin.rbac.service.UserService;
import com.nec.asia.nic.framework.admin.site.domain.SiteGroups;
import com.nec.asia.nic.framework.admin.site.domain.SiteRepository;
import com.nec.asia.nic.framework.admin.site.service.SiteService;
import com.nec.asia.nic.framework.common.ImageUtil;
import com.nec.asia.nic.framework.dao.DaoException;
import com.nec.asia.nic.investigation.dto.ConfirmLostDto;
import com.nec.asia.nic.investigation.dto.HistoryNinDto;
import com.nec.asia.nic.investigation.dto.InfoPaymentDto;
import com.nec.asia.nic.investigation.dto.InfoPerson;
import com.nec.asia.nic.investigation.dto.InfoPersons;
import com.nec.asia.nic.investigation.dto.InvestigationListInfoTargetDto;
import com.nec.asia.nic.investigation.dto.InvestigationMasterDto;
import com.nec.asia.nic.investigation.dto.InvestigationOffer;
import com.nec.asia.nic.investigation.AttachmentSource;
import com.nec.asia.nic.investigation.dto.InvestigationMatchingDto;
import com.nec.asia.nic.investigation.dto.InvestigationTargetDto;
import com.nec.asia.nic.investigation.exception.InvalidParameterException;
import com.nec.asia.nic.util.CitizenRefXmlConvertor;
import com.nec.asia.nic.util.Constants;
import com.nec.asia.nic.util.JobXmlConvertor;
import com.nec.asia.nic.util.RegistrationConstants;
import com.nec.asia.nic.util.TransLogInfoXmlConvertor;
import com.nec.asia.nic.util.pagingUtil;
import com.nec.asia.nic.utils.HelperClass;
import com.nec.asia.nic.web.session.UserSession;


@Controller
@RequestMapping(value="/investionProcess")
public class InvestigationProcessController {
	private static final Logger logger = LoggerFactory
			.getLogger(InvestigationController.class);

	private static final String PARA_TRANSACTION_ATTACHM_IGNORE_J2K_SCOPE = "SYSTEM";
	private static final String PARA_TRANSACTION_ATTACHM_IGNORE_J2K_VALUE = "TRANSACT2_ATTACHM_IGNORE_J2K";

	private static final String PARA_TX_ATTCH_J2K_CONVERT_AT_SERVER_SCOPE = "SYSTEM";
	private static final String PARA_TX_ATTCH_J2K_CONVERT_AT_SERVER_VALUE = "TX_ATTCH_J2K_CONVERT_AT_SERVER";

	public static final String DATA_DELIMITER = "XXXXX";
	public static final String DATA_DELIMITER_LEFT = "[";
	public static final String DATA_DELIMITER_RIGHT = "]";

	private static final String PARA_SCOPE_SYSTEM = "SYSTEM";
	private static final String PARA_NAME_SYSTEM_DMS_URL = "DMS_URL";

	private static final String PARA_TRANSACTION_ATTACHMENT_SOURCE_SCOPE = "SYSTEM";
	private static final String PARA_TRANSACTION_ATTACHMENT_SOURCE_VALUE = "TRANSACTION_ATTACHMENT_SOURCE";

	public static JobXmlConvertor jobUtil = new JobXmlConvertor();

	public static CitizenRefXmlConvertor util = new CitizenRefXmlConvertor();

	public static TransLogInfoXmlConvertor logUtil = new TransLogInfoXmlConvertor();

	private static String ASSENDING_ORDER = "1";

	private static String DECENDING_ORDER = "2";

	public static final String STATUS_COMPLETED = "02";

	// /TRUNG THÊM GỌI API TECA
	public static String tokenA98 = "";
	public static String typeTokenA98 = "Bearer";
	public static Integer expireTokenA98 = 0;

	public static String tokenBNG = "";
	public static String typeTokenBNG = "Bearer";
	public static Integer expireTokenBNG = 0;
	// /
	@Autowired
	private ParametersService parametersService;

	@Autowired
	private NicUploadJobService uploadJobService;

	@Autowired
	private CodesService codesService;

	@Autowired
	private NicTransactionAttachmentService nicTransactionAttachmentService;

	@Autowired
	private DocumentDataDao documentDataDao = null;

	@Autowired
	private NicTransactionService nicTransactionService;

	@Autowired
	private SiteService eppSiteService;

	@Autowired
	private DocumentDataService documentDataService;

	@Autowired
	private SignerGovsService signerGovsService;

	@Autowired
	private ListHandoverService listHandoverService;
	
	@Autowired
	private SiteService siteService;
	
	@Autowired
	private NicRegistrationDataService regDataService;

	@Autowired
	private WorkflowProcessService workflowProcessService; 

	@Autowired
	private PersoLocationsService persoLocationsService;
	
	@Autowired
	private NicAfisDataService  nicAfisDataService;
	
	@Autowired
	private NicSearchResultDao nicSearchResultDao;
	
	@Autowired
	private NicBlacklistService nicBlacklistService;
	
	@Autowired
	private NicIdentificationService nicIdentificationService;
	
	@Autowired
	private NicSearchHitResultService nicSearchHitResultService; 
	
	@Autowired
	private NicImmiHistoryService nicImmiHistoryService; 
	
	
	@Autowired
	private NicBlacklistService blacklistService;
	
	@Autowired
	private PaymentDefService paymentDefService;
	
	@Autowired
	private EppPersonService eppPersonService;
	
	@Autowired
	private NicTransactionLostService lostService;
	
	@Autowired
	private BufPersonInvestigationService bufPersonService;

	
	private NicSubmitPersoCommand nicSubmitPersoCommand;
	private NicCommandExecutor<NicUploadJob> baseExecutor;
	private ApplicationContext appContext;

	@Autowired
	@Qualifier("userService")
	private UserService userService;

	@ModelAttribute("userAssignment")
	public Map<String, String> userAssignment() {
		List<Users> codeList = userService.findAll();
		Map<String, String> userAssignment = new LinkedHashMap<String, String>();
		for (Users ricCode : codeList) {
			userAssignment.put(ricCode.getUserId(), ricCode.getUserName());
		}
		return userAssignment;
	}

	@ModelAttribute("transactionType")
	public Map<String, String> transactionType() {
		List<CodeValues> codeList = codesService.getAllCodeValues(
				"TRANSACTION_TYPE", new String[] { "NEW", "RENEWAL", "LOST",
						"REG", "REP" });
		Map<String, String> transactionType = new LinkedHashMap<String, String>();
		transactionType.put("", "--Chọn--");
		for (CodeValues ricCode : codeList) {
			transactionType.put(ricCode.getId().getCodeValue(),
					ricCode.getCodeValueDesc());
		}
		return transactionType;
	}

	@ModelAttribute("listSiteRepository")
	public Map<String, String> listSiteRepository() throws DaoException {
		Map<String, String> list = new HashMap<String, String>();
		list.put("", "--Chọn--");
		SiteGroups sg = siteService.getOnlyListByLevel("1");
		if(sg != null){
			List<SiteRepository> listSite = siteService.findAllByGroupId1(sg.getGroupId());	
			for(SiteRepository sr : listSite){
				list.put(sr.getSiteId(), sr.getSiteName());
			}
		}
		return list;
	}
	
	@ModelAttribute("passportType")
	public Map<String, String> passportType() {
		List<CodeValues> codeList = codesService.getAllCodeValues(
				"PASSPORT_TYPE", new String[] { "P", "PD", "PO", "PE" });
		Map<String, String> passportType = new LinkedHashMap<String, String>();
		passportType.put("", "--Chọn--");
		for (CodeValues ricCode : codeList) {
			passportType.put(ricCode.getId().getCodeValue(),
					ricCode.getCodeValueDesc());
		}
		return passportType;
	}

	@ModelAttribute("priorityCode")
	public Map<String, String> priorityCode() {
		List<CodeValues> codeList = codesService.getAllCodeValues("PRIORITY",
				new String[] { "1", "2", "3" });
		Map<String, String> priorityCode = new LinkedHashMap<String, String>();
		priorityCode.put("", "--Chọn--");
		for (CodeValues ricCode : codeList) {
			priorityCode.put(ricCode.getId().getCodeValue(),
					ricCode.getCodeValueDesc());
		}
		return priorityCode;
	}
	
	private void logMessage(Exception ex) {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		PrintStream ps = new PrintStream(baos);
		ex.printStackTrace(ps);
		logger.error(ex.getMessage() + " : \n" + new String(baos.toByteArray()));
	}

	
	@RequestMapping(value = "/showInvestigation")
	public ModelAndView getInvestionJob(
			@ModelAttribute(value = "formData") InvestigationAssignmentData investigationAssignmentData,
			WebRequest request, HttpServletRequest httpRequest, ModelMap model) {
		logger.info("NIC Investigation");
		PaginatedResult<NicUploadJobDto> pr = null;
		//PaginatedResult<NicUploadJobDto> rejectedList = null;
		try {
			int pageNo = !StringUtils.isEmpty(request.getParameter("pageNo")) ? Integer.parseInt(request.getParameter("pageNo")) : 1;
			int pageSize = 20;
			//int startIndex = 0;
			HttpSession session = httpRequest.getSession();
			UserSession userSession = (UserSession) session
					.getAttribute("userSession");
			logger.info("The Officer Id or User Id is ===============>>>>>>>>>>"
					+ userSession.getUserName());

			// Modified for pagination
			ParametersId id = new ParametersId();
			id.setParaName(Parameters.MAX_RECORDS_PER_PAGE);
			id.setParaScope(Parameters.SCOPE_SYSTEM);
			String tableId = "row";
			String sortedElement = "codeId";
			String order = InvestigationProcessController.DECENDING_ORDER;

			try {
				String sort = request
						.getParameter(new ParamEncoder(tableId)
								.encodeParameterName(TableTagParameters.PARAMETER_SORT));
				if (StringUtils.isNotBlank(sort))
					sortedElement = sort;
			} catch (Exception ex) {
				logMessage(ex);
			}

			try {
				String requestOrder = request
						.getParameter(new ParamEncoder(tableId)
								.encodeParameterName(TableTagParameters.PARAMETER_ORDER));
				if (StringUtils.isNotBlank(requestOrder))
					order = requestOrder;
			} catch (Exception ex) {
				logMessage(ex);
			}

			Order hibernateOrder = Order.desc(sortedElement);
			if (order.equalsIgnoreCase(InvestigationProcessController.DECENDING_ORDER)) {
				hibernateOrder = Order.asc(sortedElement);
			}

			Parameters parameter = parametersService.findById(id);

			if (parameter != null) {
				String pageSizeDb = parameter.getParaShortValue();

				if (StringUtils.isNotBlank(pageSizeDb)
						&& StringUtils.isNumeric(pageSizeDb)) {
					pageSize = Integer.parseInt(pageSizeDb);
				}
			}
			pageSize = Constants.PAGE_SIZE_DEFAULT;
//			String pageNumber = request.getParameter((new ParamEncoder(tableId)
//					.encodeParameterName(TableTagParameters.PARAMETER_PAGE)));
//
//			if (StringUtils.isNotBlank(pageNumber)
//					&& StringUtils.isNumeric(pageNumber)) {
//				pageNo = Integer.parseInt(pageNumber);
//			}
			httpRequest.setAttribute("pendingCount",
					uploadJobService.getPendingInvestigationsCount());

			if (investigationAssignmentData == null) {
				investigationAssignmentData = new InvestigationAssignmentData();
			}
			
			if(StringUtils.isEmpty(investigationAssignmentData.getTypeList())){
				investigationAssignmentData.setTypeList("2");
			}

			investigationAssignmentData.cleanUpStrings();
			// pr =
			// uploadJobService.findAllForInvestigationPagination(userSession.getUserName(),
			// pageNo, pageSize);
			
			
			if(investigationAssignmentData.getTypeList().equals("3")){
				pr = uploadJobService
						.findAllForInvestigationPagination3(
								new String[] { NicUploadJob.RECORD_STATUS_APPROVED },
								null, userSession.getUserId(),
								pageNo,
								pageSize,
								investigationAssignmentData.assignedOnly(),
								new AssignmentFilterAll(investigationAssignmentData.getTypeList(), investigationAssignmentData.getPackageCode()));
			}else if(investigationAssignmentData.getTypeList().equals("0")){
				pr = uploadJobService
						.findAllForInvestigationPagination3(
								new String[] { NicUploadJob.RECORD_STATUS_APPROVED, NicUploadJob.RECORD_STATUS_IN_PROGRESS},
								null, userSession.getUserId(),
								pageNo,
								pageSize,
								investigationAssignmentData.assignedOnly(),
								new AssignmentFilterAll(investigationAssignmentData.getTypeList(), investigationAssignmentData.getPackageCode()));
			}else{
				pr = uploadJobService
						.findAllForInvestigationPagination1(
								new String[] { NicUploadJob.RECORD_STATUS_IN_PROGRESS },
								null, userSession.getUserId(),
								pageNo,
								pageSize,
								investigationAssignmentData.assignedOnly(),
								new AssignmentFilterAll(investigationAssignmentData.getTypeList(), investigationAssignmentData.getPackageCode()));
			}
			
			List<NicUploadJobDto> list = new ArrayList<NicUploadJobDto>();
			if (pr != null) {
				list = pr.getRows();
				model.addAttribute("total", pr.getTotal());
				if (list != null) {
					for (NicUploadJobDto record : list) {
						if(StringUtils.isEmpty(record.getInvestigationOfficerId())){
							record.setFlagOfficerId("0");
						}else {
							if(record.getInvestigationOfficerId().equals(userSession.getUserId())){
								record.setFlagOfficerId("0");
							}else{
								record.setFlagOfficerId("1");								
							}
						}
						if(investigationAssignmentData.getTypeList().equals("3")){
							record.setFlagOfficerId("1");		
						}
						String transactionId = record.getTransactionId();
						if (transactionId != null) {
							NicTransaction nicTransaction = nicTransactionService
									.findById(transactionId);
							if (nicTransaction != null) {
								record.setDateOfApplication(nicTransaction
										.getDateOfApplication());
								record.setEstDateOfCollection(nicTransaction
										.getEstDateOfCollection());
								record.setPassportType(nicTransaction
										.getPassportType());
								record.setRegSiteCode(nicTransaction
										.getRegSiteCode());
								record.setPackageId(nicTransaction.getPackageId());
								record.setNin(nicTransaction.getNin());
								// record.setPriority(nicTransaction.getPriority());
//								{
//									try {
//										//CodeValues codeValue = codesService.getCodeValueByIdName(Codes.PRIORITY,nicTransaction.getPriority().toString());
//										String priority = codesService.getCodeValueDescByIdName(RegistrationConstants.CODE_PRIORITY_CODE_ID, nicTransaction.getPriority().toString(), "");
//										if (priority != null) {
//											record.setPriorityString(priority);
//										} else {
//											record.setPriorityString("");
//										}
//									} catch (Exception e) {
//										record.setPriorityString(nicTransaction
//												.getPriority() == null ? null
//												: nicTransaction.getPriority()
//														.toString());
//									}
//								}
								NicRegistrationData reg = nicTransaction.getNicRegistrationData();
								if(reg != null){
									record.setFullName(HelperClass.createFullName(reg.getSurnameFull(), reg.getMiddlenameFull(), reg.getFirstnameFull()));
									String dateDob = HelperClass.convertDateToString2(reg.getDateOfBirth());
									record.setDob(com.nec.asia.nic.utils.HelperClass.loadDateOfBirth(dateDob, reg.getDefDateOfBirth()));
									//record.setDob(HelperClass.convertDateToString2(reg.getDateOfBirth()));
									if(!StringUtils.isEmpty(reg.getGender())){
										record.setGender(reg.getGender().equals("M") ? "Nam" : "Nữ");
									}
									String noiSinh = codesService.getCodeValueDescByIdName("DISTRICT", reg.getPlaceOfBirth(), "");
									if(StringUtils.isNotEmpty(noiSinh)){
										record.setPlaceOfBirth(noiSinh);								
									}else{
										record.setPlaceOfBirth(reg.getPlaceOfBirth());	
									}
									//record.setPlaceOfBirth(reg.getPlaceOfBirth());
								}
							}
							
						}
					}
				} else {
					request.setAttribute("jobDetailsErrorMsg", "Không tìm thấy dữ liệu",
							0);
				}
				Map searchResultMap = new HashMap();
				searchResultMap.put("totalRecords", pr.getTotal());
				//searchResultMap.put("pageSize", pageSize);

				//model.addAttribute("jobList", list);
				//phúc edit
				int firstResults = (pageNo - 1) < 0 ? 0 : (pageNo - 1) * pageSize;
				model.addAttribute("pageSize", pageSize);
				model.addAttribute("pageNo", pageNo);
				model.addAttribute("totalPage", pagingUtil.totalPage(pr.getTotal(), pageSize));
				model.addAttribute("startIndex", pr.getTotal() != 0 ? firstResults + 1 : 0);
				model.addAttribute("totalRecord", pr.getTotal());
				model.addAttribute("endIndex", firstResults + pr.getRowSize());
				model.addAttribute("dsXuLyA", list);
				//end
				model.addAttribute("fnSelected",
						Constants.HEADING_NIC_INVESTIGATION);
				return new ModelAndView("investigation.investigation",
						searchResultMap);
			} else {
				model.addAttribute("fnSelected",
						Constants.HEADING_NIC_INVESTIGATION);
				return new ModelAndView("investigation.investigation", null);
			}
			// }
		} catch (Exception e) {

			e.printStackTrace();
			return null;
		}

	}
	
	@ResponseBody
	@RequestMapping(value="/showMessage")
	public String showMessage(@RequestParam String arrJob,
			WebRequest request, HttpServletRequest httpRequest, ModelMap model) {
		String[] arrStage = arrJob.split(",");
		boolean chkShow = true;
		for(int i = 0; i < arrStage.length; i++){
			if(!arrStage[i].equals("1")){
				chkShow = false;
				break;
			}
		}
		if(chkShow)
			return "Y";
		return "N";
	}
	
	@ResponseBody
	@RequestMapping(value="/testOfficeId")
	public String testOfficeId(@RequestParam String arrJob,
			WebRequest request, HttpServletRequest httpRequest, ModelMap model) {
		HttpSession session = httpRequest.getSession();
		UserSession userSession = (UserSession) session.getAttribute("userSession");
		String[] arrJobId = arrJob.split(",");
		Long[] arr = new Long[arrJobId.length]; 
		for(int i = 0; i < arrJobId.length; i++){
			arr[i] = Long.valueOf(arrJobId[i]);
		}
		String check = uploadJobService.testInvestigationProcessByJobId(arr, userSession.getUserId(), new AssignmentFilterAll());
		if(!check.equals(""))
			return check;
		return "";
	}
	
	@RequestMapping(value = "/invesProcess/{stage}")
	public ModelAndView getInvestionProcess(
			@ModelAttribute(value = "formData") InvestigationAssignmentData investigationAssignmentData, @PathVariable String stage,
			WebRequest request, HttpServletRequest httpRequest, ModelMap model) {
		logger.info("NIC Investigation");
		List<NicUploadJobDto> pr = null;
		//PaginatedResult<NicUploadJobDto> rejectedList = null;
		try {
			//int pageNo = !StringUtils.isEmpty(request.getParameter("pageNo")) ? Integer.parseInt(request.getParameter("pageNo")) : 1;
			//int pageSize = Constants.PAGE_SIZE_DEFAULT;
			//int startIndex = 0;
			HttpSession session = httpRequest.getSession();
			UserSession userSession = (UserSession) session
					.getAttribute("userSession");
			logger.info("The Officer Id or User Id is ===============>>>>>>>>>>"
					+ userSession.getUserName());

			// Modified for pagination
			ParametersId id = new ParametersId();
			id.setParaName(Parameters.MAX_RECORDS_PER_PAGE);
			id.setParaScope(Parameters.SCOPE_SYSTEM);
			String tableId = "row";
			String sortedElement = "codeId";
			String order = InvestigationProcessController.DECENDING_ORDER;
			try {
				String sort = request
						.getParameter(new ParamEncoder(tableId)
								.encodeParameterName(TableTagParameters.PARAMETER_SORT));
				if (StringUtils.isNotBlank(sort))
					sortedElement = sort;
			} catch (Exception ex) {
				logMessage(ex);
			}

			try {
				String requestOrder = request
						.getParameter(new ParamEncoder(tableId)
								.encodeParameterName(TableTagParameters.PARAMETER_ORDER));
				if (StringUtils.isNotBlank(requestOrder))
					order = requestOrder;
			} catch (Exception ex) {
				logMessage(ex);
			}

			Order hibernateOrder = Order.desc(sortedElement);
			if (order.equalsIgnoreCase(InvestigationProcessController.DECENDING_ORDER)) {
				hibernateOrder = Order.asc(sortedElement);
			}

//			Parameters parameter = parametersService.findById(id);
//
//			if (parameter != null) {
//				String pageSizeDb = parameter.getParaShortValue();
//
//				if (StringUtils.isNotBlank(pageSizeDb)
//						&& StringUtils.isNumeric(pageSizeDb)) {
//					pageSize = Integer.parseInt(pageSizeDb);
//				}
//			}
			//pageSize = Constants.PAGE_SIZE_DEFAULT;
			httpRequest.setAttribute("pendingCount",
					uploadJobService.getPendingInvestigationsCount());

			if (investigationAssignmentData == null) {
				investigationAssignmentData = new InvestigationAssignmentData();
			}

			investigationAssignmentData.cleanUpStrings();
			Long[] listJobId = new Long[investigationAssignmentData.getSelectedJobIds().length];
			
			if(investigationAssignmentData.getSelectedJobIds() != null && investigationAssignmentData.getSelectedJobIds().length > 0){
				for(int i = 0; i < investigationAssignmentData.getSelectedJobIds().length; i++){
					//Mở lại rồi nhé
					uploadJobService.updateOfficerIdByJobId(investigationAssignmentData.getSelectedJobIds()[i], userSession.getUserId());					
					listJobId[i] = Long.parseLong(investigationAssignmentData.getSelectedJobIds()[i]);
					NicUploadJob jobNic = uploadJobService.findById(Long.parseLong(investigationAssignmentData.getSelectedJobIds()[i]));
					NicListHandover handover = listHandoverService.findHandoverByTransactionId(jobNic.getTransactionId(), 8, 1, true);
					if(jobNic.getInvestigationStatus().equals("01") && handover != null){// && jobNic.getApproveFlag() == null
						//jobNic.setApproveFlag("Y");
						uploadJobService.saveOrUpdate(jobNic);					
					}
				}
			}
			pr = uploadJobService.findInvestigationProcessByJobId(listJobId, null, new AssignmentFilterAll());
			NicUploadJob jobFocus = null;
			if(StringUtils.isNotEmpty(investigationAssignmentData.getJobId())){
				jobFocus = uploadJobService.findById(Long.parseLong(investigationAssignmentData.getJobId()));
			}
			String photoStr = "";
			String soHoSo = "";
			if(pr == null || pr.size() == 0){
				pr = new ArrayList<NicUploadJobDto>();
			}else{
				if(jobFocus != null){
					List<NicTransactionAttachment> photoList = nicTransactionAttachmentService.findNicTransactionAttachments(
							jobFocus.getTransactionId(), NicTransactionAttachment.DOC_TYPE_PHOTO_CAPTURE, NicTransactionAttachment.DEFAULT_SERIAL_NO).getListModel();				
					if (CollectionUtils.isNotEmpty(photoList) && photoList.size() > 0) {
						//String images = Base64.encodeBase64String(photoList.get(0).getDocData());
						photoStr = Base64.encodeBase64String(photoList.get(0).getDocData());
						//dto.setPhotoStr(images);
					}
					soHoSo = jobFocus.getTransactionId();
					//model.addAttribute("noteAprove", jobFocus.getNoteApprove() != null ? jobFocus.getNoteApprove() : "");
					model.addAttribute("noteAprovePerson", jobFocus.getNoteApprovePerson() != null ? jobFocus.getNoteApprovePerson() : "");
					model.addAttribute("noteAproveObj", jobFocus.getNoteApproveObj() != null ? jobFocus.getNoteApproveObj() : "");
					model.addAttribute("noteAproveNin", jobFocus.getNoteApproveNin() != null ? jobFocus.getNoteApproveNin() : "");
				}else{
					List<NicTransactionAttachment> photoList = nicTransactionAttachmentService.findNicTransactionAttachments(
							pr.get(0).getTransactionId(), NicTransactionAttachment.DOC_TYPE_PHOTO_CAPTURE, NicTransactionAttachment.DEFAULT_SERIAL_NO).getListModel();				
					if (CollectionUtils.isNotEmpty(photoList) && photoList.size() > 0) {
						//String images = Base64.encodeBase64String(photoList.get(0).getDocData());
						photoStr = Base64.encodeBase64String(photoList.get(0).getDocData());
						//dto.setPhotoStr(images);
					}
					soHoSo = pr.get(0).getTransactionId();
					//model.addAttribute("noteAprove", pr.get(0).getNoteApprove());
					model.addAttribute("noteAprovePerson", pr.get(0).getNoteApprovePerson() != null ? pr.get(0).getNoteApprovePerson() : "");
					model.addAttribute("noteAproveObj", pr.get(0).getNoteApproveObj() != null ? pr.get(0).getNoteApproveObj() : "");
					model.addAttribute("noteAproveNin", pr.get(0).getNoteApproveNin() != null ? pr.get(0).getNoteApproveNin() : "");
				}
			}
			
			NicTransaction txn = nicTransactionService.findById(soHoSo);
			String photoTreEm = "";
			List<InfoPerson> listFormat = new ArrayList<InfoPerson>();
			if(StringUtils.isNotEmpty(txn.getInfoPerson())){
				JAXBContext jaxbContext = JAXBContext.newInstance(InfoPersons.class);
				Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
				StringReader reader = new StringReader(txn.getInfoPerson());
				InfoPersons persons = (InfoPersons) unmarshaller.unmarshal(reader);
				List<InfoPerson> listPerson = null;
				String personId = "";
				if(persons != null){
					listPerson = persons.getInfoPersons();
					if(listPerson != null && listPerson.size() > 0){
						personId = listPerson.get(0).getId();
					}
				}
				listFormat = this.formatDatatoVN(listPerson);
				if(StringUtils.isNotEmpty(personId)){
					List<NicTransactionAttachment> photoList = nicTransactionAttachmentService.findFacialTEById (
							soHoSo, NicTransactionAttachment.DOC_TYPE_ENROLLED_SUB, personId);				
					if (CollectionUtils.isNotEmpty(photoList) && photoList.size() > 0) {
						String images = Base64.encodeBase64String(photoList.get(0).getDocData());
						photoTreEm = images;						
					}
					
				}				
			}
			//investigationAssignmentData.setAssignmentStatus(stage);
			model.addAttribute("photoTreEm", photoTreEm);
			model.addAttribute("dsTreEm", listFormat);
			model.addAttribute("slTreEm", listFormat.size() > 0 ? "Y" : "N");
			model.addAttribute("stage", stage);
			model.addAttribute("chiTietDS", pr);
			model.addAttribute("photoStr", photoStr);
			model.addAttribute("soHoSo", soHoSo);
			//model.addAttribute("dsTreEm", new ArrayList<NicUploadJobDto>());
			ModelAndView modelAndView = new ModelAndView("investigation.investigation.process");
			modelAndView.addObject("formData", investigationAssignmentData);
			return modelAndView;
		} catch (Exception e) {

			e.printStackTrace();
			System.out.println(e.getMessage());
		}

		return null;
	}
	
	List<InfoPerson> formatDatatoVN(List<InfoPerson> list){
		if(list != null && list.size() > 0){
			for(InfoPerson info : list){
				info.setGender(info.getGender().equals("M") ? "Nam" : "Nữ");
				info.setDateOfBirth(HelperClass.convertStringToStringTk(info.getDateOfBirth(), 0));
//				String pob = codesService.getCodeValueDescByIdName(RegistrationConstants.CODE_ID_BirthPlace, info.getPlaceOfBirthId(), "");
//				if(StringUtils.isNotEmpty(pob)){
//					info.setPlaceOfBirthId(pob);
//				}
				String noiSinh = codesService.getCodeValueDescByIdName("DISTRICT", info.getPlaceOfBirthId(), "");
				if(StringUtils.isNotEmpty(noiSinh)){
					info.setPlaceOfBirthId(noiSinh);								
				}else{
					info.setPlaceOfBirthId(info.getPlaceOfBirthId());	
				}
			}
		}
		return list;
	}
	
	//Khớp dữ liệu cá nhân
		public Map<Integer, Integer> MatchingPersonalScore(Long jobId, String tranHitId){
			Map<Integer, Integer> fmapScore = new HashMap<Integer, Integer>();
			try {
				Long idBMS = null;
				
				List<NicSearchResult> searchResults = nicSearchResultDao.findAllByJobId(jobId);
				if(searchResults != null && searchResults.size() > 0){
					for(NicSearchResult sR_ : searchResults){						
						if(sR_.getTypeSearch().equals(NicSearchResult.TYPE_SEARCH__FP_1_N) && sR_.getHasHit() != null && sR_.getHasHit()){
							idBMS = sR_.getSearchResultId();
						}
					}
				}
				
				///Dữ liệu kiểm tra với BMS
				if(idBMS != null){
					//Lấy dữ liệu trùng trong bảng EPP_SEARCH_HIT_RESULT
					List<NicSearchHitResult> listHitBMS = nicSearchHitResultService.findHitPositions(null, idBMS);
					if(listHitBMS != null && listHitBMS.size() > 0){
						for(NicSearchHitResult sHR : listHitBMS){
							if(sHR.getDataSource().equals(NicSearchHitResult.hitInfo_DataSource_BMS)){
								//Lấy Id của đối tượng trùng
								String transactionId_BMS = sHR.getTransactionIdHit();
								if(transactionId_BMS.equals(tranHitId)){	
									int score = 0;										
									if(StringUtils.isNotEmpty(sHR.getHitInfo())){
										String[] listHit = sHR.getHitInfo().split(","); //Tách chuỗi theo dấu ","
										if(listHit.length > 0){
											for(int i = 0; i < listHit.length; i++){
											   if(Integer.parseInt((listHit[i].split("=")[1].split("\\.")[0])) > score)
												   score = Integer.parseInt((listHit[i].split("=")[1].split("\\.")[0]));
											   	   fmapScore.put(Integer.parseInt(listHit[i].split("=")[0]) , score);
											}
										}
									}
									break;
								}
							}
						}
					}
					else
					{
						logger.info("Not found list hit CPD by idCPD: " + idBMS);
					}
				}
				
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		 
			return fmapScore;
		}
	
	//Khớp dữ liệu cá nhân
	public InvestigationMatchingDto MatchingPersonal(Long jobId){
		InvestigationMatchingDto outputR = new InvestigationMatchingDto();
		try {
			
			//Danh sách BMS / CPD nghi trùng
			Long idCPD = null;
			Long idBMS = null;
			
			String statusTransaction = "Đã phát hành";
			String statusDocumentData = "Đang hoạt động";
			String passportNo = "";
			String transactionIdMaster = "";
			Boolean statusDocumentDataLock = false;

			List<String> listTranCPD = new ArrayList<String>();
			List<String> listTranBMS = new ArrayList<String>();
			
			List<NicSearchResult> searchResults = nicSearchResultDao.findAllByJobId(jobId);
			if(searchResults != null && searchResults.size() > 0){
				for(NicSearchResult sR_ : searchResults){
					transactionIdMaster =sR_.getTransactionId();
					if(sR_.getTypeSearch().equals(NicSearchResult.TYPE_SEARCH__CPD) && sR_.getHasHit()){
						idCPD = sR_.getSearchResultId();
					}
					else if(sR_.getTypeSearch().equals(NicSearchResult.TYPE_SEARCH__FP_1_N) && sR_.getHasHit() != null && sR_.getHasHit()){
						idBMS = sR_.getSearchResultId();
					}
				}
			}
			
			InvestigationMasterDto outPutM = new InvestigationMasterDto();
			
			//Lấy dữ liệu chủ hồ sơ
			NicRegistrationData nicRegisM = regDataService.getNicDataById(transactionIdMaster);
			NicTransaction nicTranM = nicTransactionService.getNicTransactionById(transactionIdMaster).getModel();
			outPutM.setFullName(nicRegisM.getSurnameLine1());
			//outPut.setGender(nicRegisM.getGender());
			if(nicRegisM.getGender() != null){
				outPutM.setGender(nicRegisM.getGender().equals("M") ? "Nam" : "Nữ");
			}else {
				outPutM.setGender("Không");
			}
			String dobM = "";
			if(nicRegisM.getDateOfBirth() != null){
				String pattern = "dd/MM/yyyy";
				DateFormat df = new SimpleDateFormat(pattern);
				dobM = df.format(nicRegisM.getDateOfBirth());
			}
			outPutM.setDob(com.nec.asia.nic.utils.HelperClass.loadDateOfBirth(dobM, nicRegisM.getDefDateOfBirth()));
			//outPutM.setDob(dobM);
			outPutM.setNin(nicTranM.getNin());
			//Lấy địa chỉ đầy đủ
			String address = nicRegisM.getAddressLine1();
			String px = codesService.getCodeValueDescByIdName(RegistrationConstants.CODE_TOWN, nicRegisM.getAddressLine4(), "");
			String tp = codesService.getCodeValueDescByIdName(RegistrationConstants.CODE_DISTRICT, nicRegisM.getAddressLine5(), "");
			if(StringUtils.isNotEmpty(px)){
				address += ", " + px;
			}
			if(StringUtils.isNotEmpty(tp)){
				address += ", " + tp;
			}
			outPutM.setAddress(address);
			outPutM.setReligion(StringUtils.isNotEmpty(nicRegisM.getReligion()) ? codesService.getCodeValueDescByIdName(Constants.CODE_RELIGION_ID, nicRegisM.getReligion(), "") : "");
			outPutM.setNation(StringUtils.isNotEmpty(nicRegisM.getNation()) ? codesService.getCodeValueDescByIdName("CODE_NATION" ,nicRegisM.getNation(), "") : "");
			//Xử lý ảnh đối tượng 
			List<NicTransactionAttachment> photoListM = nicTransactionAttachmentService
					.findNicTransactionAttachments(
							transactionIdMaster,
							NicTransactionAttachment.DOC_TYPE_PHOTO_CAPTURE,
							NicTransactionAttachment.DEFAULT_SERIAL_NO).getListModel();
			if (CollectionUtils.isNotEmpty(photoListM)
					&& photoListM.size() > 0) {
				String photoStr = Base64.encodeBase64String(photoListM.get(0)
						.getDocData());
				outPutM.setPhoto(photoStr);
			}
			
			outputR.setMasterData(outPutM);
			
			///Dữ liệu kiểm tra với CPD
			if(idCPD != null){
				
				//Lấy dữ liệu trùng trong bảng EPP_SEARCH_HIT_RESULT
				List<NicSearchHitResult> listHitCPD = nicSearchHitResultService.findHitPositions(null, idCPD);
				if(listHitCPD != null && listHitCPD.size() > 0){
					for(NicSearchHitResult sHR : listHitCPD){
						InvestigationListInfoTargetDto outPut = new InvestigationListInfoTargetDto();
						
						if(sHR.getDataSource().equals(NicSearchHitResult.hitInfo_DataSource_CPD)){
							//Lấy Id của đối tượng trùng
							String transactionId_CPD = sHR.getTransactionIdHit();
							
							if(StringUtils.isNotEmpty(transactionId_CPD)){
								//Lấy dữ liệu thông tin hồ sơ của transaction
								NicTransaction nicTran = nicTransactionService.getNicTransactionById(transactionId_CPD).getModel();
								if(nicTran != null){
									//Kiểm tra trạng thái của hồ sơ: Phúc đóng yêu cầu lấy số hộ chiếu cả phát hành + chưa phát hành
									/*if(!nicTran.getTransactionStatus().equals(NicTransaction.TRANSACTION_STATUS_RIC_ISSUED)){//TH chưa phát hành
										statusTransaction = "Đang xử lý - Giai đoạn: " + nicTran.getTransactionStatus();
									}*/
									if(false){}
									else{
										//Thông tin hộ chiếu
										Collection<NicDocumentData> nicDocs = documentDataService.findByTransactionId(transactionId_CPD).getModel();
										if(nicDocs != null && nicDocs.size() > 0){
											List<NicDocumentData> nicDocs_ = new ArrayList<NicDocumentData>(nicDocs);
											NicDocumentData nicDoc = nicDocs_.get(0);
											
											if(nicDoc.getStatus().equals(NicDocumentData.DOCUMENT_DATA_STATUS_NONE)){
												statusDocumentData = "Đã khóa";
												statusDocumentDataLock = true;
												passportNo = nicDoc.getId().getPassportNo();
											}
											else if(nicDoc.getStatus().equals(NicDocumentData.DOCUMENT_DATA_STATUS_PERSONALIZED))
											{
												statusDocumentData = "Cá thể hóa";
											}
											
											outPut.setPackageId_O(nicTran.getPackageId());
											outPut.setTransactionId_O(transactionId_CPD);
											outPut.setTypeTransaction_O(nicTran.getTransactionType());
											outPut.setReg_O(nicTran.getRegSiteCode());
											outPut.setStatus_O(statusDocumentData);
											outPut.setPassportNo_O(nicDoc.getId().getPassportNo());
											
											String issuePP = "";
											if(nicDoc.getDateOfIssue() != null){
												String pattern = "dd/MM/yyyy";
												DateFormat df = new SimpleDateFormat(pattern);
												issuePP = df.format(nicDoc.getDateOfIssue());
											}
											outPut.setIssueDatePassport_O(issuePP);
											
											String receivePP = "";
											if(nicDoc.getReceiveDatetime() != null){
												String pattern = "dd/MM/yyyy";
												DateFormat df = new SimpleDateFormat(pattern);
												receivePP = df.format(nicDoc.getReceiveDatetime());
											}
											outPut.setPayDatePassport_O(receivePP);
											outPut.setPayPlacePassport_O(nicTran.getIssSiteCode());
											outPut.setPersonRecieve_O("");
											outPut.setRemark_O("");
										}
										else{
											logger.info("Not found information Document data in database TransactionId: " + transactionId_CPD);
										}
									}
									
									//Thông tin cá nhân
									NicRegistrationData nicRegis = regDataService.getNicDataById(transactionId_CPD);
									if(nicRegis != null){
										outPut.setFullName_O(nicRegis.getSurnameLine1());
										//outPut.setGender_O(nicRegis.getGender());
										if(nicRegis.getGender() != null){
											outPut.setGender_O(nicRegis.getGender().equals("M") ? "Nam" : "Nữ");
										}else {
											outPut.setGender_O("Không");
										}
//										String dob = "";
//										if(nicRegis.getDateOfBirth() != null){
//											String pattern = "dd/MM/yyyy";
//											DateFormat df = new SimpleDateFormat(pattern);
//											dob = df.format(nicRegis.getDateOfBirth());
//										}
//										outPut.setDob_O(dob);
										String date = nicRegis.getDateOfBirth() != null ? HelperClass.convertDateToString2(nicRegis.getDateOfBirth()) : "";
										outPut.setDob_O(HelperClass.loadDateOfBirth(date, nicRegis.getDefDateOfBirth()));
										outPut.setNin_O(nicTran.getNin());
										//outPut.setPob_O(nicRegis.getPlaceOfBirth());
										String noiSinh = codesService.getCodeValueDescByIdName("DISTRICT", nicRegis.getPlaceOfBirth(), "");
										if(StringUtils.isNotEmpty(noiSinh)){
											outPut.setPob_O(noiSinh);								
										}else{
											outPut.setPob_O(nicRegis.getPlaceOfBirth());	
										}
										outPut.setReligion_O(StringUtils.isNotEmpty(nicRegis.getReligion()) ? codesService.getCodeValueDescByIdName(Constants.CODE_RELIGION_ID, nicRegis.getReligion(), "") : "");
										outPut.setNation_O(StringUtils.isNotEmpty(nicRegis.getNation()) ? codesService.getCodeValueDescByIdName("CODE_NATION" ,nicRegis.getNation(), "") : "");
										outPut.setPhone_O(nicRegis.getContactNo());
										outPut.setTransactionId_O(transactionId_CPD);
										//Xử lý ảnh đối tượng 
										List<NicTransactionAttachment> photoList = nicTransactionAttachmentService
												.findNicTransactionAttachments(
														transactionId_CPD,
														NicTransactionAttachment.DOC_TYPE_PHOTO_CAPTURE,
														NicTransactionAttachment.DEFAULT_SERIAL_NO).getListModel();
										if (CollectionUtils.isNotEmpty(photoList)
												&& photoList.size() > 0) {
											String photoStr = Base64.encodeBase64String(photoList.get(0)
													.getDocData());
											outPut.setPhoto_O(photoStr);
										}
										
										//Thông tin người thân
										outPut.setFullNameFather_O(nicRegis.getFatherFullname());
										String fatherDob = "";
										if(nicRegis.getFatherDateOfBirth() != null){
											String pattern = "dd/MM/yyyy";
											DateFormat df = new SimpleDateFormat(pattern);
											fatherDob = df.format(nicRegis.getFatherDateOfBirth());
										}
										outPut.setDobFather_O(fatherDob);
										
										outPut.setFullNameMother_O(nicRegis.getMotherFullname());
										String motherDob = "";
										if(nicRegis.getMotherDateOfBirth() != null){
											String pattern = "dd/MM/yyyy";
											DateFormat df = new SimpleDateFormat(pattern);
											motherDob = df.format(nicRegis.getMotherDateOfBirth());
										}
										outPut.setDobMother_O(motherDob);
										
										String spouserDob = "";
										if(nicRegis.getSpouseDateOfBirth()!= null){
											String pattern = "dd/MM/yyyy";
											DateFormat df = new SimpleDateFormat(pattern);
											spouserDob = df.format(nicRegis.getSpouseDateOfBirth());
										}
										outPut.setDobSpouser_O(spouserDob);
										
									}
									else
									{
										logger.info("Not found information Registration data in database TransactionId: " + transactionId_CPD);
									}
									
									//Kiểm tra danh sách giấy tờ mất/hủy
									//Tìm kiếm trong danh sách TRANSACTION với kiểu LOS (chỉ tìm với hộ chiếu khóa RIC_DEACTIVATED)
									/*List<NicTransaction> listLost = new ArrayList<NicTransaction>();
									if(statusDocumentDataLock && nicTran.getTransactionStatus().equals(NicTransaction.TRANSACTION_STATUS_RIC_DEACTIVATED)){
										//Kiểm tra qua số hộ chiếu
										NicTransaction nicTranLost_PPno = nicTransactionService.getNicTransactionByPrevPPno(passportNo, "", "");
										if(nicTranLost_PPno != null)
											listLost.add(nicTranLost_PPno);
										
										//Kiểm tra qua CMND / CCCD (tìm bản ghi đầu tiên)
										NicTransaction nicTranLost_nin = nicTransactionService.getNicTransactionByPrevPPno("", "", nicTran.getNin());
										if(nicTranLost_nin != null)
											listLost.add(nicTranLost_nin);
									}
									if(listLost != null && listLost.size() > 0)
										outputR.setListLost(listLost);
									
									//Kiểm tra đối với dữ liệu lịch sử XNC của đối tượng
									List<ImmiHistory> listImmi = new ArrayList<ImmiHistory>();
									listImmi = nicImmiHistoryService.findByNinPPno(nicTran.getNin(), passportNo);
									if(listImmi != null && listImmi.size() > 0)
										outputR.setListImmi(listImmi);
									*/
									
									listTranCPD.add(transactionId_CPD);
									outputR.getListTargetData().add(outPut);
								}
								else
								{
									logger.info("Not found information Transaction in database TransactionId: " + transactionId_CPD);
								}
							}
								
						}
					}
				}
				else
				{
					logger.info("Not found list hit CPD by idCPD: " + idCPD);
				}
				
			}
			
			///Dữ liệu kiểm tra với BMS
			if(idBMS != null){
				//Lấy dữ liệu trùng trong bảng EPP_SEARCH_HIT_RESULT
				List<NicSearchHitResult> listHitBMS = nicSearchHitResultService.findHitPositions(null, idBMS);
				if(listHitBMS != null && listHitBMS.size() > 0){
					for(NicSearchHitResult sHR : listHitBMS){
						if(sHR.getDataSource().equals(NicSearchHitResult.hitInfo_DataSource_BMS)){
							//Lấy Id của đối tượng trùng
							String transactionId_BMS = sHR.getTransactionIdHit();
							if(StringUtils.isNotEmpty(transactionId_BMS)){
								//Kiểm tra trong ListCheckCPD có trùng transactionID không. Nếu có thì chỉ thêm dữ liệu BMS vào vị trí đối tượng
								if(listTranCPD.contains(transactionId_BMS) && outputR.getListTargetData() != null && outputR.getListTargetData().size() > 0){
									InvestigationListInfoTargetDto updateObj = outputR.getListTargetData().get(listTranCPD.indexOf(transactionId_BMS));
									updateObj.setScoreBMSall_O(sHR.getHitInfo());
									int score = 0;
									//Tính số điểm trung bình cho vân tay
									//Kiểm tra chuỗi dữ liệu
									if(StringUtils.isNotEmpty(sHR.getHitInfo())){
										String[] listHit = sHR.getHitInfo().split(","); //Tách chuỗi theo dấu ","
										if(listHit.length > 0){
											for(int i = 0; i < listHit.length; i++){
											   if(Integer.parseInt((listHit[i].split("=")[1].split("\\.")[0])) > score)
												   score = Integer.parseInt((listHit[i].split("=")[1].split("\\.")[0]));
											}
										}
									}
									Double scoreBms = (double) score;
									updateObj.setScoreBMS_O(scoreBms /100);
									
									if(score == 0){
										updateObj.setScoreBms_O("");
									}else{
										updateObj.setScoreBms_O((scoreBms /100) + "");
									}
									
									//Xóa dữ liệu cũ trong 2 list
									listTranCPD.remove(transactionId_BMS);
									outputR.getListTargetData().remove(listTranCPD.indexOf(transactionId_BMS));
									
									//Cập nhật dữ liệu mới
									listTranCPD.add(transactionId_BMS);
									outputR.getListTargetData().add(updateObj);
								}
								else{
									InvestigationListInfoTargetDto outPut = new InvestigationListInfoTargetDto();
									
									outPut.setScoreBMSall_O(sHR.getHitInfo());
									int score = 0;
									//Tính số điểm trung bình cho vân tay
									//Kiểm tra chuỗi dữ liệu
									if(StringUtils.isNotEmpty(sHR.getHitInfo())){
										String[] listHit = sHR.getHitInfo().split(","); //Tách chuỗi theo dấu ","
										if(listHit.length > 0){
											for(int i = 0; i < listHit.length; i++){
											   if(Integer.parseInt((listHit[i].split("=")[1].split("\\.")[0])) > score)
												   score = Integer.parseInt((listHit[i].split("=")[1].split("\\.")[0]));
											}
										}
									}
									Double scoreBms = (double) score;
									outPut.setScoreBMS_O(scoreBms /100);
									if(score == 0){
										outPut.setScoreBms_O("");
									}else{
										outPut.setScoreBms_O((scoreBms /100) + "");
									}
									
									//Lấy dữ liệu thông tin hồ sơ của transaction
									NicTransaction nicTran = nicTransactionService.getNicTransactionById(transactionId_BMS).getModel();
									if(nicTran != null){
										//Kiểm tra trạng thái của hồ sơ: Lấy cả hộ chiếu cuẩn bi phát hành
										/*if(!nicTran.getTransactionStatus().equals(NicTransaction.TRANSACTION_STATUS_RIC_ISSUED)){//TH chưa phát hành
											statusTransaction = "Đang xử lý - Giai đoạn: " + nicTran.getTransactionStatus();
										}*/
										if(false){}
										else{
											//Thông tin hộ chiếu
											Collection<NicDocumentData> nicDocs = documentDataService.findByTransactionId(transactionId_BMS).getModel();
											if(nicDocs != null && nicDocs.size() > 0){
												List<NicDocumentData> nicDocs_ = new ArrayList<NicDocumentData>(nicDocs);
												NicDocumentData nicDoc = nicDocs_.get(0);
												
												if(nicDoc.getStatus().equals(NicDocumentData.DOCUMENT_DATA_STATUS_NONE)){
													statusDocumentData = "Đã khóa";
													statusDocumentDataLock = true;
													passportNo = nicDoc.getId().getPassportNo();
												}
												else if(nicDoc.getStatus().equals(NicDocumentData.DOCUMENT_DATA_STATUS_PERSONALIZED))
												{
													statusDocumentData = "Cá thể hóa";
												}
												
												outPut.setPackageId_O(nicTran.getPackageId());
												outPut.setTransactionId_O(transactionId_BMS);
												outPut.setTypeTransaction_O(nicTran.getTransactionType());
												outPut.setReg_O(nicTran.getRegSiteCode());
												outPut.setStatus_O(statusDocumentData);
												outPut.setPassportNo_O(nicDoc.getId().getPassportNo());
												
												String issuePP = "";
												if(nicDoc.getDateOfIssue() != null){
													String pattern = "dd/MM/yyyy";
													DateFormat df = new SimpleDateFormat(pattern);
													issuePP = df.format(nicDoc.getDateOfIssue());
												}
												outPut.setIssueDatePassport_O(issuePP);
												
												String receivePP = "";
												if(nicDoc.getReceiveDatetime() != null){
													String pattern = "dd/MM/yyyy";
													DateFormat df = new SimpleDateFormat(pattern);
													receivePP = df.format(nicDoc.getReceiveDatetime());
												}
												outPut.setPayDatePassport_O(receivePP);
												outPut.setPayPlacePassport_O(nicTran.getIssSiteCode());
												outPut.setPersonRecieve_O("");
												outPut.setRemark_O("");
											}
											else{
												logger.info("Not found information Document data in database TransactionId: " + transactionId_BMS);
											}
										}
										
										//Thông tin cá nhân
										NicRegistrationData nicRegis = regDataService.getNicDataById(transactionId_BMS);
										if(nicRegis != null){
											outPut.setFullName_O(nicRegis.getSurnameLine1());
											//outPut.setGender_O(nicRegis.getGender());
											if(nicRegis.getGender() != null){
												outPut.setGender_O(nicRegis.getGender().equals("M") ? "Nam" : "Nữ");
											}else {
												outPut.setGender_O("Không");
											}
											String dob = "";
											if(nicRegis.getDateOfBirth() != null){
												String pattern = "dd/MM/yyyy";
												DateFormat df = new SimpleDateFormat(pattern);
												dob = df.format(nicRegis.getDateOfBirth());
											}
											//outPut.setDob_O(dob);
											String date = nicRegis.getDateOfBirth() != null ? HelperClass.convertDateToString2(nicRegis.getDateOfBirth()) : "";
											outPut.setDob_O(HelperClass.loadDateOfBirth(date, nicRegis.getDefDateOfBirth()));
											outPut.setNin_O(nicTran.getNin());
											//outPut.setPob_O(nicRegis.getPlaceOfBirth());
											String noiSinh = codesService.getCodeValueDescByIdName("DISTRICT", nicRegis.getPlaceOfBirth(), "");
											if(StringUtils.isNotEmpty(noiSinh)){
												outPut.setPob_O(noiSinh);								
											}else{
												outPut.setPob_O(nicRegis.getPlaceOfBirth());	
											}
											outPut.setReligion_O(StringUtils.isNotEmpty(nicRegis.getReligion()) ? codesService.getCodeValueDescByIdName(Constants.CODE_RELIGION_ID, nicRegis.getReligion(), "") : "");
											outPut.setNation_O(StringUtils.isNotEmpty(nicRegis.getNation()) ? codesService.getCodeValueDescByIdName("CODE_NATION" ,nicRegis.getNation(), "") : "");
											outPut.setPhone_O(nicRegis.getContactNo());
											
											//Xử lý ảnh đối tượng 
											List<NicTransactionAttachment> photoList = nicTransactionAttachmentService
													.findNicTransactionAttachments(
															transactionId_BMS,
															NicTransactionAttachment.DOC_TYPE_PHOTO_CAPTURE,
															NicTransactionAttachment.DEFAULT_SERIAL_NO).getListModel();
											if (CollectionUtils.isNotEmpty(photoList)
													&& photoList.size() > 0) {
												String photoStr = Base64.encodeBase64String(photoList.get(0)
														.getDocData());
												outPut.setPhoto_O(photoStr);
											}
											
											//Thông tin người thân
											outPut.setFullNameFather_O(nicRegis.getFatherFullname());
											String fatherDob = "";
											if(nicRegis.getFatherDateOfBirth() != null){
												String pattern = "dd/MM/yyyy";
												DateFormat df = new SimpleDateFormat(pattern);
												fatherDob = df.format(nicRegis.getFatherDateOfBirth());
											}
											outPut.setDobFather_O(fatherDob);
											
											outPut.setFullNameMother_O(nicRegis.getMotherFullname());
											String motherDob = "";
											if(nicRegis.getMotherDateOfBirth() != null){
												String pattern = "dd/MM/yyyy";
												DateFormat df = new SimpleDateFormat(pattern);
												motherDob = df.format(nicRegis.getMotherDateOfBirth());
											}
											outPut.setDobMother_O(motherDob);
											
											String spouserDob = "";
											if(nicRegis.getSpouseDateOfBirth()!= null){
												String pattern = "dd/MM/yyyy";
												DateFormat df = new SimpleDateFormat(pattern);
												spouserDob = df.format(nicRegis.getSpouseDateOfBirth());
											}
											outPut.setDobSpouser_O(spouserDob);
											outPut.setTransactionId_O(transactionId_BMS);
										}
										else
										{
											logger.info("Not found information Registration data in database TransactionId: " + transactionId_BMS);
										}
										
										//Kiểm tra danh sách giấy tờ mất/hủy
										//Tìm kiếm trong danh sách TRANSACTION với kiểu LOS (chỉ tìm với hộ chiếu khóa RIC_DEACTIVATED)
										/*List<NicTransaction> listLost = new ArrayList<NicTransaction>();
										if(statusDocumentDataLock && nicTran.getTransactionStatus().equals(NicTransaction.TRANSACTION_STATUS_RIC_DEACTIVATED)){
											//Kiểm tra qua số hộ chiếu
											NicTransaction nicTranLost_PPno = nicTransactionService.getNicTransactionByPrevPPno(passportNo, "", "");
											if(nicTranLost_PPno != null)
												listLost.add(nicTranLost_PPno);
											
											//Kiểm tra qua CMND / CCCD (tìm bản ghi đầu tiên)
											NicTransaction nicTranLost_nin = nicTransactionService.getNicTransactionByPrevPPno("", "", nicTran.getNin());
											if(nicTranLost_nin != null)
												listLost.add(nicTranLost_nin);
										}
										if(listLost != null && listLost.size() > 0)
											outputR.setListLost(listLost);
										
										//Kiểm tra đối với dữ liệu lịch sử XNC của đối tượng
										List<ImmiHistory> listImmi = new ArrayList<ImmiHistory>();
										listImmi = nicImmiHistoryService.findByNinPPno(nicTran.getNin(), passportNo);
										if(listImmi != null && listImmi.size() > 0)
											outputR.setListImmi(listImmi);
										*/
										listTranBMS.add(transactionId_BMS);
										outputR.getListTargetData().add(outPut);
									}
									else
									{
										logger.info("Not found information Transaction in database TransactionId: " + transactionId_BMS);
									}
								}	
							}
						}
					}
				}
				else
				{
					logger.info("Not found list hit CPD by idCPD: " + idCPD);
				}
			}
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	 
		return outputR;
	}
	
	public InvestigationTargetDto ListHistoryByTarget(String tranId){
		InvestigationTargetDto result = new InvestigationTargetDto();
		
		try {
			result.setListImmi(new ArrayList<ImmiHistory>());
			result.setListLost(new ArrayList<NicTransaction>());
			
			String passportNo = "";
			Boolean statusDocumentDataLock = false; 
			NicTransaction nicTran = nicTransactionService.getNicTransactionById(tranId).getModel();
			if(nicTran.getTransactionStatus().equals(NicTransaction.TRANSACTION_STATUS_RIC_ISSUED)){
				Collection<NicDocumentData> nicDocs = documentDataService.findByTransactionId(tranId).getModel();
				if(nicDocs != null && nicDocs.size() > 0){
					List<NicDocumentData> nicDocs_ = new ArrayList<NicDocumentData>(nicDocs);
					NicDocumentData nicDoc = nicDocs_.get(0);
					statusDocumentDataLock = true;
					passportNo = nicDoc.getId().getPassportNo();
				}
			}
			//Kiểm tra danh sách giấy tờ mất/hủy
			//Tìm kiếm trong danh sách TRANSACTION với kiểu LOS (chỉ tìm với hộ chiếu khóa RIC_DEACTIVATED)
			List<NicTransaction> listLost = new ArrayList<NicTransaction>();
			List<NicUploadJob> listLostU = new ArrayList<NicUploadJob>();
			if(statusDocumentDataLock && nicTran.getTransactionStatus().equals(NicTransaction.TRANSACTION_STATUS_RIC_DEACTIVATED)){
			
				//Kiểm tra qua số hộ chiếu
				NicUploadJob nicTranLost_PPno;
				nicTranLost_PPno = uploadJobService.getNicTransactionByPrevPPno(passportNo, "", "");
				
				if(nicTranLost_PPno != null)
					listLostU.add(nicTranLost_PPno);
				
				//Kiểm tra qua CMND / CCCD (tìm bản ghi đầu tiên)
				NicUploadJob nicTranLost_nin = uploadJobService.getNicTransactionByPrevPPno("", "", nicTran.getNin());
				if(nicTranLost_nin != null)
					listLostU.add(nicTranLost_nin);
			}
			
			if(listLost != null && listLost.size() > 0)
			{
				for(NicUploadJob obj: listLostU){
					NicTransaction nicTran_ = nicTransactionService.getNicTransactionById(obj.getTransactionId()).getModel();
					if(nicTran_ != null){
						listLost.add(nicTran_);
					}
				}
			}
			
			result.setListLost(listLost);
				
			//Kiểm tra đối với dữ liệu lịch sử XNC của đối tượng
			List<ImmiHistory> listImmi = new ArrayList<ImmiHistory>();
			listImmi = nicImmiHistoryService.findByNinPPno(nicTran.getNin(), passportNo);
			if(listImmi != null && listImmi.size() > 0)
				result.setListImmi(listImmi);
		}
		catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}
	
	public Boolean MatchingDataRequest(Long tranMatchingId, String tagetMatchingId, int type, String note, Long idBlacklist, String wsktid, String userid, String fullName){
		
		Boolean result = false;
		if(tranMatchingId <= 0){
			logger.info("Null tranMatchingId");
		}
		else{
			try {
				NicUploadJob nicUpTaget = new NicUploadJob();
				try {
					if(StringUtils.isNotEmpty(tagetMatchingId)){
						//nicUpTaget = uploadJobService.findAllByTransactionId(tagetMatchingId).get(0);
					}
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				//result = uploadJobService.updateMatchingData(tranMatchingId, tagetMatchingId, type, note, idBlacklist, fullName);
				NicUploadJob nicUp = uploadJobService.findById(tranMatchingId);
				String typeKhop = "PERSON (Cá nhân)";
				String actionKhop = "Không khớp";
				if(type == 1 && StringUtils.isNotEmpty(tagetMatchingId)){
					actionKhop = "Khớp";
					
					//Kiểm tra dữ liệu PERSON
					NicRegistrationData regis = regDataService.getNicDataById(nicUp.getTransactionId());
					NicRegistrationData regisTaget = regDataService.getNicDataById(tagetMatchingId);
					Long idPerson = 0L;
					if(regis != null && regisTaget != null){
						String fullname = removeAccent(regis.getSurnameLine1().toUpperCase());
						String fullnameTaget =  removeAccent(regisTaget.getSurnameLine1().toUpperCase());
						String gender = regis.getGender().equals("M") ? "MALE" : "FEMALE";
						String genderTaget = regisTaget.getGender().equals("M") ? "MALE" : "FEMALE";
						String pattern = "dd-MMM-yyyy";
						DateFormat df = new SimpleDateFormat(pattern);      
						// Using DateFormat format method we can create a string 
						// representation of a date with the defined format.
						String dob = regis.getPrintDob();
						String pob = regis.getPlaceOfBirth();
						String dobTaget = regisTaget.getPrintDob();
						String pobTaget = regisTaget.getPlaceOfBirth();
						
							//Lấy Person theo Id
							EppPerson person = null;
							if(StringUtils.isNotEmpty(nicUpTaget.getNicTransaction().getPersonCode())){
								person = new EppPerson();
								//person = eppPersonService.getPersonById(nicUpTaget.getOriginalyPersonId());								
							}
							if(person == null){
								if(StringUtils.isEmpty(nicUp.getNicTransaction().getPersonCode())){
									//Thêm PERSON mới
									EppPerson newPer = new EppPerson();
									newPer.setDateOfBirth(dob);
									newPer.setCreateTs(new Date());
									String issueDateNin = "";
									if(regis.getDateNin() != null)
										issueDateNin= df.format(regis.getDateNin());
									newPer.setEthnic(regis.getNation());
									newPer.setGender(gender);
									newPer.setIdNumber(nicUp.getNicTransaction().getNin());
									newPer.setName(regis.getSurnameLine1());
									newPer.setReligion(regis.getReligion());
									/*Đóng do thay đổi bảng person
									 * newPer.setDateOfIdIssue(HelperClass.convertMonthWordToMonthNumber1(issueDateNin));
									newPer.setNationalityId(regis.getNationality());
									newPer.setOccupation(regis.getJob());
									newPer.setOfficeInfo(regis.getAddressCompany());
									newPer.setPlaceOfBirthId(pob);
									newPer.setResidentAddress(regis.getAddressLine1());
									newPer.setResidentAreaId(regis.getAddressLine5());
									newPer.setResidentPlaceId(regis.getAddressLine4());
									newPer.setTmpAddress(regis.getAddressTempStreet());
									newPer.setTmpPlaceId(regis.getAddressTempDistrict());
									newPer.setTmpAreaId(regis.getAddressTempCity());
									newPer.setFpFlag("N");*/
									newPer.setSearchName(removeAccent(regis.getSurnameLine1().toUpperCase()));
									Collection<NicDocumentData> nicDocs;
									try {
										nicDocs = documentDataService.findByTransactionId(tagetMatchingId).getModel();
										if(nicDocs != null && nicDocs.size() > 0){
											List<NicDocumentData> nicDocs_ = new ArrayList<NicDocumentData>(nicDocs);
											NicDocumentData nicDoc = nicDocs_.get(0);
											//newPer.setPassportNo(nicDoc.getId().getPassportNo());
										}
										List<NicTransactionAttachment> docFp = nicTransactionAttachmentService.getNicTransactionAttachments(
														tagetMatchingId, new String[] {NicTransactionAttachment.DOC_TYPE_FINGERPRINT}, null);
										if(docFp != null && docFp.size() > 0){
											//newPer.setFpFlag("Y");
										}
											
									} catch (Exception e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
									}
									eppPersonService.saveOrUpdateData(newPer);
									
									List<EppPerson> lstP = eppPersonService.findAllEppPerson2(fullname, gender, dob, pob);
									if(lstP != null && lstP.size() > 0){
										idPerson = lstP.get(0).getPersonId();
										
										//Cập nhật lại GlobalID
										EppPerson pGlobal = lstP.get(0);
										//pGlobal.setGlobalId(idPerson);
										eppPersonService.saveOrUpdateData(pGlobal);
									}
									
									try {
										
										List<NicTransactionAttachment> docList = nicTransactionAttachmentService
												.getNicTransactionAttachments(
														tagetMatchingId,
														new String[] { NicTransactionAttachment.DOC_TYPE_PHOTO_CAPTURE, 
															NicTransactionAttachment.DOC_TYPE_FINGERPRINT, 
															NicTransactionAttachment.KEY_SC_DSIGNER,
															NicTransactionAttachment.KEY_SC_NBERNIN,
															NicTransactionAttachment.KEY_SC_PASPORT},
														null);
										if(docList != null && docList.size() > 0){
											for(NicTransactionAttachment doc : docList){
												EppPersonAttchmnt attchmnt = new EppPersonAttchmnt();
												attchmnt.setFileName(doc.getDocType());
												attchmnt.setPersonId(idPerson);
												attchmnt.setSerialNo(Integer.parseInt(doc.getSerialNo()));
												attchmnt.setType_(doc.getDocType());
												attchmnt.setBase64(Base64.encodeBase64String(doc.getDocData()));
												eppPersonService.saveOrUpdateDataAttchmnt(attchmnt);
											}
										}
										
										//Thong tin nguoi than
										
										
									} catch (Exception e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
									}
								}
							}
							else{
								
								if(!fullnameTaget.equals(fullname) || !genderTaget.equals(gender)
										|| !dobTaget.equals(dob) || !pobTaget.equals(pob)){
									if(StringUtils.isEmpty(nicUp.getNicTransaction().getPersonCode())){
										//Thêm PERSON mới
										EppPerson newPer = new EppPerson();
										newPer.setDateOfBirth(dob);
										newPer.setCreateTs(new Date());
										String issueDateNin = "";
										if(regis.getDateNin() != null)
											issueDateNin= df.format(regis.getDateNin());
										newPer.setEthnic(regis.getNation());
										newPer.setGender(gender);
										newPer.setIdNumber(nicUp.getNicTransaction().getNin());
										newPer.setName(regis.getSurnameLine1());
										newPer.setReligion(regis.getReligion());
										/*newPer.setDateOfIdIssue(HelperClass.convertMonthWordToMonthNumber1(issueDateNin));
										newPer.setNationalityId(regis.getNationality());
										newPer.setOccupation(regis.getJob());
										newPer.setOfficeInfo(regis.getAddressCompany());
										newPer.setPlaceOfBirthId(pob);
										newPer.setResidentAddress(regis.getAddressLine1());
										newPer.setResidentAreaId(regis.getAddressLine5());
										newPer.setResidentPlaceId(regis.getAddressLine4());
										newPer.setTmpAddress(regis.getAddressTempStreet());
										newPer.setTmpPlaceId(regis.getAddressTempDistrict());
										newPer.setTmpAreaId(regis.getAddressTempCity());
										newPer.setOriginId(nicUpTaget.getOriginalyPersonId());
										newPer.setFpFlag("N");*/
										newPer.setSearchName(removeAccent(regis.getSurnameLine1().toUpperCase()));
										Collection<NicDocumentData> nicDocs;
										try {
											nicDocs = documentDataService.findByTransactionId(tagetMatchingId).getModel();
											if(nicDocs != null && nicDocs.size() > 0){
												List<NicDocumentData> nicDocs_ = new ArrayList<NicDocumentData>(nicDocs);
												NicDocumentData nicDoc = nicDocs_.get(0);
												//newPer.setPassportNo(nicDoc.getId().getPassportNo());
											}
											List<NicTransactionAttachment> docFp = nicTransactionAttachmentService.getNicTransactionAttachments(
													tagetMatchingId, new String[] {NicTransactionAttachment.DOC_TYPE_FINGERPRINT}, null);
											if(docFp != null && docFp.size() > 0){
												//newPer.setFpFlag("Y");
											}
										} catch (Exception e) {
											// TODO Auto-generated catch block
											e.printStackTrace();
										}
										eppPersonService.saveOrUpdateData(newPer);
										
										List<EppPerson> lstP = eppPersonService.findAllEppPerson2(fullname, gender, dob, pob);
										if(lstP != null && lstP.size() > 0){
											idPerson = lstP.get(0).getPersonId();
											
											//Cập nhật lại GlobalID
											EppPerson pGlobal = lstP.get(0);
											//pGlobal.setGlobalId(idPerson);
											eppPersonService.saveOrUpdateData(pGlobal);
										}
										
										try {
											
											List<NicTransactionAttachment> docList = nicTransactionAttachmentService
													.getNicTransactionAttachments(
															tagetMatchingId,
															new String[] { NicTransactionAttachment.DOC_TYPE_PHOTO_CAPTURE, 
																NicTransactionAttachment.DOC_TYPE_FINGERPRINT, 
																NicTransactionAttachment.KEY_SC_DSIGNER,
																NicTransactionAttachment.KEY_SC_NBERNIN,
																NicTransactionAttachment.KEY_SC_PASPORT},
															null);
											if(docList != null && docList.size() > 0){
												for(NicTransactionAttachment doc : docList){
													EppPersonAttchmnt attchmnt = new EppPersonAttchmnt();
													attchmnt.setFileName(doc.getDocType());
													attchmnt.setPersonId(idPerson);
													attchmnt.setSerialNo(Integer.parseInt(doc.getSerialNo()));
													attchmnt.setType_(doc.getDocType());
													attchmnt.setBase64(Base64.encodeBase64String(doc.getDocData()));
													eppPersonService.saveOrUpdateDataAttchmnt(attchmnt);
												}
											}
											
											//Thong tin nguoi than
											
											
										} catch (Exception e) {
											// TODO Auto-generated catch block
											e.printStackTrace();
										}
									}
									else
									{
										//idPerson = nicUpTaget.getOriginalyPersonId();
									}
								}
								else
								{
									//idPerson = nicUpTaget.getOriginalyPersonId();
								}
							}
						
						nicUp.getNicTransaction().setPersonCode(null);
						uploadJobService.saveOrUpdate(nicUp);
					}
				}
				else if(type == 1 && StringUtils.isEmpty(tagetMatchingId)){
					if(StringUtils.isEmpty(nicUp.getNicTransaction().getPersonCode())){
						//Kiểm tra dữ liệu PERSON
						NicRegistrationData regis = regDataService.getNicDataById(nicUp.getTransactionId());
						Long idPerson = 0L;
						if(regis != null){
							String fullname = removeAccent(regis.getSurnameLine1().toUpperCase());
							String gender = regis.getGender().equals("M") ? "MALE" : "FEMALE";
							String pattern = "dd-MMM-yyyy";
							DateFormat df = new SimpleDateFormat(pattern);      
							// Using DateFormat format method we can create a string 
							// representation of a date with the defined format.
							String dob = regis.getPrintDob();
							String pob = regis.getPlaceOfBirth();
							//Thêm PERSON mới
							EppPerson newPer = new EppPerson();
							newPer.setDateOfBirth(dob);
							newPer.setCreateTs(new Date());
							String issueDateNin = "";
							if(regis.getDateNin() != null)
								issueDateNin= df.format(regis.getDateNin());
							newPer.setEthnic(regis.getNation());
							newPer.setGender(gender);
							newPer.setIdNumber(nicUp.getNicTransaction().getNin());
							newPer.setName(regis.getSurnameLine1());
							newPer.setReligion(regis.getReligion());
							/*Đóng do thay đổi bảng person
							 * newPer.setDateOfIdIssue(HelperClass.convertMonthWordToMonthNumber1(issueDateNin));
							newPer.setNationalityId(regis.getNationality());
							newPer.setOccupation(regis.getJob());
							newPer.setOfficeInfo(regis.getAddressCompany());
							newPer.setPlaceOfBirthId(pob);
							newPer.setResidentAddress(regis.getAddressLine1());
							newPer.setResidentAreaId(regis.getAddressLine5());
							newPer.setResidentPlaceId(regis.getAddressLine4());
							newPer.setTmpAddress(regis.getAddressTempStreet());
							newPer.setTmpPlaceId(regis.getAddressTempDistrict());
							newPer.setTmpAreaId(regis.getAddressTempCity());
							newPer.setFpFlag("N");*/
							newPer.setSearchName(fullname);
							newPer.setSearchName(removeAccent(regis.getSurnameLine1().toUpperCase()));
							Collection<NicDocumentData> nicDocs;
							try {
								nicDocs = documentDataService.findByTransactionId(nicUp.getTransactionId()).getModel();
								if(nicDocs != null && nicDocs.size() > 0){
									List<NicDocumentData> nicDocs_ = new ArrayList<NicDocumentData>(nicDocs);
									NicDocumentData nicDoc = nicDocs_.get(0);
									//newPer.setPassportNo(nicDoc.getId().getPassportNo());
								}
								List<NicTransactionAttachment> docFp = nicTransactionAttachmentService.getNicTransactionAttachments(
										tagetMatchingId, new String[] {NicTransactionAttachment.DOC_TYPE_FINGERPRINT}, null);
								if(docFp != null && docFp.size() > 0){
									//newPer.setFpFlag("Y");
								}
							} catch (Exception e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							eppPersonService.saveOrUpdateData(newPer);
							
							List<EppPerson> lstP = eppPersonService.findAllEppPerson2(fullname, gender, dob, pob);
							if(lstP != null && lstP.size() > 0){
								idPerson = lstP.get(0).getPersonId();
								
								//Cập nhật lại GlobalID
								EppPerson pGlobal = lstP.get(0);
								//pGlobal.setGlobalId(idPerson);
								eppPersonService.saveOrUpdateData(pGlobal);
							}
							try {
								
								List<NicTransactionAttachment> docList = nicTransactionAttachmentService
										.getNicTransactionAttachments(
												nicUp.getTransactionId(),
												new String[] { NicTransactionAttachment.DOC_TYPE_PHOTO_CAPTURE, 
													NicTransactionAttachment.DOC_TYPE_FINGERPRINT, 
													NicTransactionAttachment.KEY_SC_DSIGNER,
													NicTransactionAttachment.KEY_SC_NBERNIN,
													NicTransactionAttachment.KEY_SC_PASPORT},
												null);
								if(docList != null && docList.size() > 0){
									for(NicTransactionAttachment doc : docList){
										EppPersonAttchmnt attchmnt = new EppPersonAttchmnt();
										attchmnt.setFileName(doc.getDocType());
										attchmnt.setPersonId(idPerson);
										attchmnt.setSerialNo(Integer.parseInt(doc.getSerialNo()));
										attchmnt.setType_(doc.getDocType());
										attchmnt.setBase64(Base64.encodeBase64String(doc.getDocData()));
										eppPersonService.saveOrUpdateDataAttchmnt(attchmnt);
									}
								}
								
								//Thong tin nguoi than
								
								
							} catch (Exception e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							
							nicUp.getNicTransaction().setPersonCode(null);
							uploadJobService.saveOrUpdate(nicUp);
						}
					}
				}
				else if(type == 2){
					typeKhop = "INVESTIGATION (Điều tra)";
					if(idBlacklist != null && idBlacklist > 0){
						actionKhop = "Trùng thông tin";
					}
					else{ 
						actionKhop = "Không trùng thông tin";
					}
				}
				else if(type == 3){
					typeKhop = "PID (CMND/CCCD)";
					actionKhop = "Đã tra cứu";
				}
				result = uploadJobService.updateMatchingData(tranMatchingId, tagetMatchingId, type, note, idBlacklist, fullName);
				try {
					//Lưu log Khớp dữ liệu
					NicTransactionLog nicTransactionLog = null;
					nicTransactionLog = new NicTransactionLog();
					nicTransactionLog.setRefId(nicUp.getTransactionId());
					nicTransactionLog.setTransactionStage(typeKhop);
					nicTransactionLog.setTransactionStatus(actionKhop);
					nicTransactionLog.setWkstnId(wsktid);
					nicTransactionLog.setOfficerId(userid);
					nicTransactionLog.setLogCreateTime(new Date());
					uploadJobService.saveNicTransactionLog(nicTransactionLog);
					
				} catch (NicUploadJobServiceException e) {
					e.printStackTrace();
				} catch (Exception e) {
					e.printStackTrace();
					throw new NicUploadJobServiceException(e);
				}
				
					
				if(result){
					/*//Kiểm tra trạng thái 3 trường dữ liệu khớp có null không 
					NicUploadJob nicU = uploadJobService.findById(tranMatchingId);
					if(nicU != null){
						if(nicU.getDateApproveInvestigation() != null && nicU.getDateApproveNin() != null && nicU.getDateApprovePerson() != null && nicU.getInvestigationStatus().equals(NicUploadJob.RECORD_STATUS_IN_PROGRESS)){
							uploadJobService.approveJobStatus_Confirmed(
									Long.valueOf(tranMatchingId),
									"SYSTEM", "SYSTEM-MACHINE",
									NicTransactionLog.TRANSACTION_STAGE_INVESTIGATION,
									NicTransactionLog.TRANSACTION_STATUS_NIC_CONFIRMED,
									NicUploadJob.RECORD_STATUS_APPROVED);
						}
					}*/
				}
			} catch (NicUploadJobServiceException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return result;
	}
	
	public static String removeAccent(String s) {
		  
		  String temp = Normalizer.normalize(s, Normalizer.Form.NFD);
		  Pattern pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
		  return pattern.matcher(temp).replaceAll("");
	}
	

	public List<EppBlacklist> ListMatchingEppBlacklist(long jobId){
		List<EppBlacklist> resultQ = new ArrayList<EppBlacklist>();
		try {
			
			String nin = "";
			String fullname = "";
			String dob = "";
			
			if (jobId > 0) {
				// Tìm bản ghi theo jobId
				NicUploadJob nicUploadJob = uploadJobService.findById(jobId);
				if (nicUploadJob != null) {
					//Lấy dữ liệu CMND/CCCD
					NicTransaction nicTran = nicTransactionService
							.getNicTransactionById(nicUploadJob
									.getTransactionId()).getModel();
					
					if(nicTran != null){
						nin = nicTran.getNin();
					}
					else{
						logger.info("not found nicTran");
					}
					
					//Lấy dữ liệu tên và ngày sinh
					NicRegistrationData nicReg = regDataService.getNicDataById(nicUploadJob.getTransactionId());
					if(nicReg != null){
						fullname = nicReg.getSurnameLine1();
						if(nicReg.getDateOfBirth() != null){
							String pattern = "dd/MM/yyyy";
							DateFormat df = new SimpleDateFormat(pattern);
							dob = df.format(nicReg.getDateOfBirth());
						}
					}
					else{
						logger.info("not found nicReg");
					}
					
					//Kiểm tra dữ liệu danh sách đen
					if(StringUtils.isNotEmpty(nin)){
						List<EppBlacklist> ds = nicBlacklistService.findByNin(nin);
						if(ds != null && ds.size() > 0){
							for(EppBlacklist eb : ds){
								resultQ.add(eb);
							}
						}
					}
					
					if(StringUtils.isNotEmpty(fullname) && StringUtils.isNotEmpty(dob)){
						List<EppBlacklist> ds = nicBlacklistService.findByInfoPerson(fullname, dob);
						if(ds != null && ds.size() > 0){
							for(EppBlacklist eb : ds){
								resultQ.add(eb);
							}
						}
					}
				} else {
					logger.info("not found nicUploadJob");
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return resultQ;
	}
	
	
	@ResponseBody
	@RequestMapping(value="/showHistoryNin/{jobId}")
	public ModelAndView showHistoryNin(@PathVariable String jobId,
			WebRequest request, HttpServletRequest httpRequest, ModelMap model) {
			ModelAndView mav = new ModelAndView("investigation-nin");
			List<HistoryNinDto> list = new ArrayList<HistoryNinDto>();
			try {
				List<EppIdentification> listEntity = ListMatchingEppIdentification(Long.parseLong(jobId));
				if(CollectionUtils.isNotEmpty(listEntity)){
					int i = 0;
					for(EppIdentification epp : listEntity){
						i++;
						HistoryNinDto dto = new HistoryNinDto();
						dto.setStt(i);
						dto.setHoTen(epp.getHoTen());
						dto.setGioiTinh(epp.getMaGioiTinh());
						dto.setCmnd(epp.getIdNumber());
						dto.setNgaySinh(epp.getNgaySinh());
						dto.setNgayCap(epp.getNgayCapCu());
						list.add(dto);
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			mav.addObject("jobList", list);
		return mav;
	}
	
	public List<EppIdentification> ListMatchingEppIdentification(long jobId){
		List<EppIdentification> resultQ = new ArrayList<EppIdentification>();
		try {
			
			String nin = "";
			
			if (jobId > 0) {
				// Tìm bản ghi theo jobId
				NicUploadJob nicUploadJob = uploadJobService.findById(jobId);
				if (nicUploadJob != null) {
					//Lấy dữ liệu CMND/CCCD
					NicTransaction nicTran = nicTransactionService
							.getNicTransactionById(nicUploadJob
									.getTransactionId()).getModel();
					
					if(nicTran != null){
						nin = nicTran.getNin();
					}
					else{
						logger.info("not found nicTran");
					}
					
					
					//Kiểm tra dữ liệu danh sách CMND/CCCD
					if(StringUtils.isNotEmpty(nin)){
						List<EppIdentification> ds = nicIdentificationService.findByNin(nin);
						if(ds != null && ds.size() > 0){
							for(EppIdentification eb : ds){
								resultQ.add(eb);
							}
						}
					}
				
				} else {
					logger.info("not found nicUploadJob");
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return resultQ;
	}
	
	@RequestMapping(value = "/invesProcessAgain")
	public ModelAndView invesProcessAgainJob(
			@ModelAttribute(value = "formData") InvestigationAssignmentData investigationAssignmentData,
			WebRequest request, HttpServletRequest httpRequest, ModelMap model) {
		logger.info("NIC Investigation");
		PaginatedResult<NicUploadJobDto> pr = null;
		//PaginatedResult<NicUploadJobDto> rejectedList = null;
		try {
			int pageNo = !StringUtils.isEmpty(request.getParameter("pageNo")) ? Integer.parseInt(request.getParameter("pageNo")) : 1;
			int pageSize = 20;
			//int startIndex = 0;
			HttpSession session = httpRequest.getSession();
			UserSession userSession = (UserSession) session
					.getAttribute("userSession");
			logger.info("The Officer Id or User Id is ===============>>>>>>>>>>"
					+ userSession.getUserName());

			// Modified for pagination
			ParametersId id = new ParametersId();
			id.setParaName(Parameters.MAX_RECORDS_PER_PAGE);
			id.setParaScope(Parameters.SCOPE_SYSTEM);
			String tableId = "row";
			String sortedElement = "codeId";
			String order = InvestigationProcessController.DECENDING_ORDER;

			try {
				String sort = request
						.getParameter(new ParamEncoder(tableId)
								.encodeParameterName(TableTagParameters.PARAMETER_SORT));
				if (StringUtils.isNotBlank(sort))
					sortedElement = sort;
			} catch (Exception ex) {
				logMessage(ex);
			}

			try {
				String requestOrder = request
						.getParameter(new ParamEncoder(tableId)
								.encodeParameterName(TableTagParameters.PARAMETER_ORDER));
				if (StringUtils.isNotBlank(requestOrder))
					order = requestOrder;
			} catch (Exception ex) {
				logMessage(ex);
			}

			Order hibernateOrder = Order.desc(sortedElement);
			if (order.equalsIgnoreCase(InvestigationProcessController.DECENDING_ORDER)) {
				hibernateOrder = Order.asc(sortedElement);
			}

			Parameters parameter = parametersService.findById(id);

			if (parameter != null) {
				String pageSizeDb = parameter.getParaShortValue();

				if (StringUtils.isNotBlank(pageSizeDb)
						&& StringUtils.isNumeric(pageSizeDb)) {
					pageSize = Integer.parseInt(pageSizeDb);
				}
			}
			pageSize = Constants.PAGE_SIZE_DEFAULT;
//			String pageNumber = request.getParameter((new ParamEncoder(tableId)
//					.encodeParameterName(TableTagParameters.PARAMETER_PAGE)));
//
//			if (StringUtils.isNotBlank(pageNumber)
//					&& StringUtils.isNumeric(pageNumber)) {
//				pageNo = Integer.parseInt(pageNumber);
//			}
			httpRequest.setAttribute("pendingCount",
					uploadJobService.getPendingInvestigationsCount());

			if (investigationAssignmentData == null) {
				investigationAssignmentData = new InvestigationAssignmentData();
			}

			investigationAssignmentData.cleanUpStrings();
			investigationAssignmentData.setSelectedJobIds(null);
			investigationAssignmentData.setTypeList("2");
//			//update lại officerID
//			if(investigationAssignmentData.getSelectedJobIds() != null && investigationAssignmentData.getSelectedJobIds().length > 0){
//				for(int i = 0; i < investigationAssignmentData.getSelectedJobIds().length; i++){
//					uploadJobService.updateOfficerIdByJobId(investigationAssignmentData.getSelectedJobIds()[i], null);										
//				}
//			}
			
			pr = uploadJobService
					.findAllForInvestigationPagination1(
							new String[] { NicUploadJob.RECORD_STATUS_IN_PROGRESS },
							null, userSession.getUserId(),
							pageNo,
							pageSize,
							investigationAssignmentData.assignedOnly(),
							new AssignmentFilterAll(investigationAssignmentData
									.getSearchTransactionId(),
									investigationAssignmentData.getPriority(),
									investigationAssignmentData
											.getTransactionType(), null, null,
									investigationAssignmentData
											.getRegSiteCode(),
									investigationAssignmentData
											.getPassportType(),
									investigationAssignmentData
											.getTypeList(), investigationAssignmentData.getPackageCode()));
			List<NicUploadJobDto> list = new ArrayList<NicUploadJobDto>();
			if (pr != null) {
				list = pr.getRows();
				model.addAttribute("total", pr.getTotal());
				if (list != null) {
					for (NicUploadJobDto record : list) {
						if(StringUtils.isEmpty(record.getInvestigationOfficerId())){
							record.setFlagOfficerId("0");
						}else {
							if(record.getInvestigationOfficerId().equals(userSession.getUserId())){
								record.setFlagOfficerId("0");
							}else{
								record.setFlagOfficerId("1");								
							}
						}
						String transactionId = record.getTransactionId();
						if (transactionId != null) {
							NicTransaction nicTransaction = nicTransactionService
									.findById(transactionId);
							if (nicTransaction != null) {
								record.setDateOfApplication(nicTransaction
										.getDateOfApplication());
								record.setEstDateOfCollection(nicTransaction
										.getEstDateOfCollection());
								record.setPassportType(nicTransaction
										.getPassportType());
								record.setRegSiteCode(nicTransaction
										.getRegSiteCode());
								record.setPackageId(nicTransaction.getPackageId());
								record.setNin(nicTransaction.getNin());
								// record.setPriority(nicTransaction.getPriority());
//								{
//									try {
//										//CodeValues codeValue = codesService.getCodeValueByIdName(Codes.PRIORITY,nicTransaction.getPriority().toString());
//										String priority = codesService.getCodeValueDescByIdName(RegistrationConstants.CODE_PRIORITY_CODE_ID, nicTransaction.getPriority().toString(), "");
//										if (priority != null) {
//											record.setPriorityString(priority);
//										} else {
//											record.setPriorityString("");
//										}
//									} catch (Exception e) {
//										record.setPriorityString(nicTransaction
//												.getPriority() == null ? null
//												: nicTransaction.getPriority()
//														.toString());
//									}
//								}
								NicRegistrationData reg = nicTransaction.getNicRegistrationData();
								if(reg != null){
									record.setFullName(HelperClass.createFullName(reg.getSurnameFull(), reg.getMiddlenameFull(), reg.getFirstnameFull()));
									String dateDob = HelperClass.convertDateToString2(reg.getDateOfBirth());
									record.setDob(HelperClass.loadDateOfBirth(dateDob, reg.getDefDateOfBirth()));
									//record.setDob(HelperClass.convertDateToString2(reg.getDateOfBirth()));
									if(!StringUtils.isEmpty(reg.getGender())){
										record.setGender(reg.getGender().equals("M") ? "Nam" : "Nữ");
									}
									//record.setPlaceOfBirth(reg.getPlaceOfBirth());
									String noiSinh = codesService.getCodeValueDescByIdName("DISTRICT", reg.getPlaceOfBirth(), "");
									if(StringUtils.isNotEmpty(noiSinh)){
										record.setPlaceOfBirth(noiSinh);								
									}else{
										record.setPlaceOfBirth(reg.getPlaceOfBirth());	
									}
								}
							}
							
						}
					}
				} else {
					request.setAttribute("jobDetailsErrorMsg", "Không tìm thấy dữ liệu",
							0);
				}
				Map searchResultMap = new HashMap();
				searchResultMap.put("totalRecords", pr.getTotal());
				//searchResultMap.put("pageSize", pageSize);

				//model.addAttribute("jobList", list);
				//phúc edit
				int firstResults = (pageNo - 1) < 0 ? 0 : (pageNo - 1) * pageSize;
				model.addAttribute("pageSize", pageSize);
				model.addAttribute("pageNo", pageNo);
				model.addAttribute("totalPage", pagingUtil.totalPage(pr.getTotal(), pageSize));
				model.addAttribute("startIndex", pr.getTotal() != 0 ? firstResults + 1 : 0);
				model.addAttribute("totalRecord", pr.getTotal());
				model.addAttribute("endIndex", firstResults + pr.getRowSize());
				model.addAttribute("dsXuLyA", list);
				//end
				model.addAttribute("fnSelected",
						Constants.HEADING_NIC_INVESTIGATION);
				return new ModelAndView("investigation.investigation",
						searchResultMap);
			} else {
				model.addAttribute("fnSelected",
						Constants.HEADING_NIC_INVESTIGATION);
				return new ModelAndView("investigation.investigation", null);
			}
			// }
		} catch (Exception e) {

			e.printStackTrace();
			return null;
		}

	}
	
	@ResponseBody
	@RequestMapping(value="/getFacialById/{tranactionId}")
	public NicUploadJobDto getFacialByTransactionId(@PathVariable String tranactionId,
			WebRequest request, HttpServletRequest httpRequest, ModelMap model) throws Exception {
		NicUploadJobDto dto = new NicUploadJobDto();		
		String photoStr = "";
		List<NicTransactionAttachment> photoList = nicTransactionAttachmentService.findNicTransactionAttachments(
				tranactionId, NicTransactionAttachment.DOC_TYPE_PHOTO_CAPTURE, NicTransactionAttachment.DEFAULT_SERIAL_NO).getListModel();				
		if (CollectionUtils.isNotEmpty(photoList) && photoList.size() > 0) {
			String images = Base64.encodeBase64String(photoList.get(0).getDocData());
			photoStr = "<img src=\'data:image/jpg;base64,"+ images +"' class=\"img-border doGetAJpgSafeVersion\" height=\"180\" width=\"135\" />";
		}else{
			photoStr = "<img class=\"img-border\" height=\"180\" width=\"135\" src=\"/eppcentral/resources/images/No_Image.jpg\">";
			//photoStr = "<img src=\"<c:url value='/resources/images/No_Image.jpg' />\" class=\"img-border\" height=\"320\" width=\"240\" title=\"Hit Candidate\" />";
		}
		dto.setPhotoStr(photoStr);
		NicTransaction nicTran = nicTransactionService.findById(tranactionId);
		if(nicTran != null){
			dto.setTransactionId(tranactionId);
		}
		List<NicUploadJob> listJob =  uploadJobService.findAllByTransactionId(tranactionId);
		if(listJob != null && listJob.size() > 0){
			//dto.setNoteApprove(listJob.get(0).getNoteApprove() != null ? listJob.get(0).getNoteApprove() : "");
			dto.setNoteApprovePerson(listJob.get(0).getNoteApprovePerson() != null ? listJob.get(0).getNoteApprovePerson() : "");
			dto.setNoteApproveObj(listJob.get(0).getNoteApproveObj() != null ? listJob.get(0).getNoteApproveObj() : "");
			dto.setNoteApproveNin(listJob.get(0).getNoteApproveNin() != null ? listJob.get(0).getNoteApproveNin() : "");
		}
		String photoTreEm = "<img class=\"img-border\" height=\"180\" width=\"135\" src=\"/ric-web/resources/images/No_Image.jpg\">";
		List<InfoPerson> listFormat = new ArrayList<InfoPerson>();
		if(StringUtils.isNotEmpty(nicTran.getInfoPerson())){
			JAXBContext jaxbContext = JAXBContext.newInstance(InfoPersons.class);
			Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
			StringReader reader = new StringReader(nicTran.getInfoPerson());
			InfoPersons persons = (InfoPersons) unmarshaller.unmarshal(reader);
			List<InfoPerson> listPerson = null;
			String personId = "";
			if(persons != null){
				listPerson = persons.getInfoPersons();
				if(listPerson != null && listPerson.size() > 0){
					personId = listPerson.get(0).getId();
				}
			}
			listFormat = this.formatDatatoVN(listPerson);
			if(StringUtils.isNotEmpty(personId)){
				List<NicTransactionAttachment> photoListSub = nicTransactionAttachmentService.findFacialTEById (
						tranactionId, NicTransactionAttachment.DOC_TYPE_ENROLLED_SUB, personId);	
				if (CollectionUtils.isNotEmpty(photoListSub) && photoListSub.size() > 0) {
					String images = Base64.encodeBase64String(photoListSub.get(0).getDocData());
					photoTreEm = "<img src=\'data:image/jpg;base64,"+ images +"' class=\"img-border doGetAJpgSafeVersion\" height=\"180\" width=\"135\" />";														
				}
			}				
		}
		List<InfoPersonDto> list = new ArrayList<InfoPersonDto>();
		for(InfoPerson info : listFormat){
			InfoPersonDto dto2 = new InfoPersonDto();
			dto2.setId(info.getId());
			dto2.setName(info.getName());
			dto2.setSearchName(info.getSearchName());
			dto2.setGender(info.getGender());
			dto2.setNationalityId(info.getNationalityId());
			dto2.setDateOfBirth(info.getDateOfBirth());
			dto2.setPlaceOfBirthId(info.getPlaceOfBirthId());
			dto2.setType_(info.getType_());
			list.add(dto2);
		}
		dto.setListInfo(list);
		dto.setPhotoTreEm(photoTreEm);
		return dto;
	}
	
	@RequestMapping(value = "/checkInfoByJob")
	public ModelAndView checkInfoByJobID(
			@ModelAttribute(value = "formData") InvestigationAssignmentData investigationAssignmentData,
			WebRequest request, HttpServletRequest httpRequest, ModelMap model) {
		logger.info("NIC Investigation");
		PaginatedResult<NicUploadJobDto> pr = null;
		//PaginatedResult<NicUploadJobDto> rejectedList = null;
		try {
			//int pageNo = !StringUtils.isEmpty(request.getParameter("pageNo")) ? Integer.parseInt(request.getParameter("pageNo")) : 1;
			// pageSize = 20;
			//int startIndex = 0;
			HttpSession session = httpRequest.getSession();
			UserSession userSession = (UserSession) session
					.getAttribute("userSession");
			logger.info("The Officer Id or User Id is ===============>>>>>>>>>>"
					+ userSession.getUserName());

			// Modified for pagination
			ParametersId id = new ParametersId();
			id.setParaName(Parameters.MAX_RECORDS_PER_PAGE);
			id.setParaScope(Parameters.SCOPE_SYSTEM);
			String tableId = "row";
			String sortedElement = "codeId";
			String order = InvestigationProcessController.DECENDING_ORDER;

			try {
				String sort = request
						.getParameter(new ParamEncoder(tableId)
								.encodeParameterName(TableTagParameters.PARAMETER_SORT));
				if (StringUtils.isNotBlank(sort))
					sortedElement = sort;
			} catch (Exception ex) {
				logMessage(ex);
			}

			try {
				String requestOrder = request
						.getParameter(new ParamEncoder(tableId)
								.encodeParameterName(TableTagParameters.PARAMETER_ORDER));
				if (StringUtils.isNotBlank(requestOrder))
					order = requestOrder;
			} catch (Exception ex) {
				logMessage(ex);
			}

			Order hibernateOrder = Order.desc(sortedElement);
			if (order.equalsIgnoreCase(InvestigationProcessController.DECENDING_ORDER)) {
				hibernateOrder = Order.asc(sortedElement);
			}

			//Parameters parameter = parametersService.findById(id);

//			if (parameter != null) {
//				String pageSizeDb = parameter.getParaShortValue();
//
//				if (StringUtils.isNotBlank(pageSizeDb)
//						&& StringUtils.isNumeric(pageSizeDb)) {
//					pageSize = Integer.parseInt(pageSizeDb);
//				}
//			}
//			pageSize = Constants.PAGE_SIZE_DEFAULT;
//			String pageNumber = request.getParameter((new ParamEncoder(tableId)
//					.encodeParameterName(TableTagParameters.PARAMETER_PAGE)));
//
//			if (StringUtils.isNotBlank(pageNumber)
//					&& StringUtils.isNumeric(pageNumber)) {
//				pageNo = Integer.parseInt(pageNumber);
//			}
			httpRequest.setAttribute("pendingCount",
					uploadJobService.getPendingInvestigationsCount());

			if (investigationAssignmentData == null) {
				investigationAssignmentData = new InvestigationAssignmentData();
			}
			
			investigationAssignmentData.cleanUpStrings();
			List<String> listJobId1 = new ArrayList<String>(); //Lưu thông tin jobId để load lại khi hoàn thành khớp hs
			List<String> loadJob = new ArrayList<String>();
			for(int i = 0; i < investigationAssignmentData.getSelectedJobIds().length; i++){
				listJobId1.add(investigationAssignmentData.getSelectedJobIds()[i]);	
				if(!investigationAssignmentData.getSelectedJobIds()[i].equals(investigationAssignmentData.getProcessJobIds()[0])){
					loadJob.add(investigationAssignmentData.getSelectedJobIds()[i]);
				}
			}
			model.addAttribute("loadJob", loadJob);
			model.addAttribute("dsJob", listJobId1);
			model.addAttribute("jobCompare", investigationAssignmentData.getProcessJobIds()[0]);
			model.addAttribute("count", listJobId1.size());
			model.addAttribute("stt", 1);
			//lấy thông tin trùng----------------------------------------------
			InvestigationMatchingDto listTrung = MatchingPersonal(Long.parseLong(investigationAssignmentData.getProcessJobIds()[0]));
			/*---------------------------------------------------------------*/
			
			//Ảnh vân tay chủ thể-------------------------------------------------------
			//Map<Integer, FingerprintInfo> fpMap = null;
			//Map<Integer, FingerprintInfo> fpMapDoiChieu = null;
			String viewFPFalg = "Y";
			NicUploadJob jobInfo = uploadJobService.findById(Long.parseLong(investigationAssignmentData.getProcessJobIds()[0]));
			//model.addAttribute("noteB", jobInfo.getNoteApprove());
			model.addAttribute("noteP", jobInfo.getNoteApprovePerson());
			model.addAttribute("noteO", jobInfo.getNoteApproveObj());
			model.addAttribute("noteN", jobInfo.getNoteApproveNin());
			model.addAttribute("idMaster", jobInfo.getTransactionId());
			NicTransaction nicTransaction = jobInfo.getNicTransaction();
		 	//fpMap = getVanTayTheoMaHoSo(nicTransaction, userSession);
		 	InvestigationMatchingDto dto = null;
		 	//Trường hợp không có hồ sơ trùng
		 	if(nicTransaction != null && nicTransaction.getNicRegistrationData() != null){
		 		dto = new InvestigationMatchingDto();
		 		dto.getMasterData().setFullName(HelperClass.createFullName(nicTransaction.getNicRegistrationData().getSurnameFull(), nicTransaction.getNicRegistrationData().getMiddlenameFull(), nicTransaction.getNicRegistrationData().getFirstnameFull()));
		 		String dateDob = HelperClass.convertDateToString2(nicTransaction.getNicRegistrationData().getDateOfBirth());
		 		dto.getMasterData().setDob(HelperClass.loadDateOfBirth(dateDob, nicTransaction.getNicRegistrationData().getDefDateOfBirth()));
		 		//dto.getMasterData().setDob(nicTransaction.getNicRegistrationData().getDateOfBirth() != null ? HelperClass.convertDateToString2(nicTransaction.getNicRegistrationData().getDateOfBirth()) : "");
		 		dto.getMasterData().setNin(nicTransaction.getNin());
		 		List<NicTransactionAttachment> photoList = nicTransactionAttachmentService.findNicTransactionAttachments(
						nicTransaction.getTransactionId(), NicTransactionAttachment.DOC_TYPE_PHOTO_CAPTURE, NicTransactionAttachment.DEFAULT_SERIAL_NO).getListModel();				
				if (CollectionUtils.isNotEmpty(photoList) && photoList.size() > 0) {
					String images = Base64.encodeBase64String(photoList.get(0).getDocData());
					dto.getMasterData().setPhoto(images);
				}else{
					dto.getMasterData().setPhoto("");
				}
				if(nicTransaction.getNicRegistrationData().getGender() != null){
					dto.getMasterData().setGender(nicTransaction.getNicRegistrationData().getGender().equals("M") ? "Nam" : "Nữ");
				}else {
					dto.getMasterData().setGender("Không");
				}
				dto.getMasterData().setAddress(nicTransaction.getNicRegistrationData().getAddressLine1());
				dto.getMasterData().setNation(StringUtils.isNotEmpty(nicTransaction.getNicRegistrationData().getNation()) ? codesService.getCodeValueDescByIdName("CODE_NATION" ,nicTransaction.getNicRegistrationData().getNation(), "") : "");
				dto.getMasterData().setReligion(StringUtils.isNotEmpty(nicTransaction.getNicRegistrationData().getReligion()) ? codesService.getCodeValueDescByIdName(Constants.CODE_RELIGION_ID, nicTransaction.getNicRegistrationData().getReligion(), "") : "");
		 		//dto = getInfoTransactionData(nicTransaction.getTransactionId());
		 	}
		 	InvestigationListInfoTargetDto trung1 = null;
		 	//List<ImmiHistory> listHistory = null;
		 	if(listTrung.getListTargetData() != null && listTrung.getListTargetData().size() > 0){
		 		List<InvestigationListInfoTargetDto> listSort = new ArrayList<InvestigationListInfoTargetDto>();
		 		for(InvestigationListInfoTargetDto inv : listTrung.getListTargetData()){
		 			if(StringUtils.isNotEmpty(inv.getScoreBms_O())){
		 				listSort.add(inv);
		 			}
		 		}
		 		for(InvestigationListInfoTargetDto inv : listTrung.getListTargetData()){
		 			if(StringUtils.isEmpty(inv.getScoreBms_O())){
		 				listSort.add(inv);
		 			}
		 		}
		 		listTrung.setListTargetData(listSort);
		 		NicTransaction nicTran = nicTransactionService.findById(listTrung.getListTargetData().get(0).getTransactionId_O());
		 		if(nicTran != null){
		 			//fpMapDoiChieu = getVanTayTheoMaHoSo(nicTran, userSession);
		 			trung1 = getInfoTransactionData(nicTran.getTransactionId());
		 			trung1.setPhoto_O(trung1.getImage_O());
		 		}
		 		//listHistory = ListHistoryByTarget(listTrung.get(0).getTransactionId_O()).getListImmi();
		 	}else{
		 		model.addAttribute("khongTrung", "Y");
		 	}
//		 	else{
//		 		listHistory = new ArrayList<ImmiHistory>();
//		 	}
			if(investigationAssignmentData.getStageLoad().equals("Y")){
				if(jobInfo.getDateApprovePerson() != null){
					model.addAttribute("action", "N");
				}else{
					model.addAttribute("action", "Y");
				}
			}else{
				model.addAttribute("action", "Y");
			}
			List<NicUploadJobDto> list = new ArrayList<NicUploadJobDto>();
			pr = new PaginatedResult<>(0, 1, list);
			if (pr != null) {
				model.addAttribute("stage", investigationAssignmentData.getStageLoad());
				model.addAttribute("chuHS", listTrung.getMasterData());
				model.addAttribute("HSTrung1", trung1 != null ? trung1 : new InvestigationListInfoTargetDto());
				model.addAttribute("fnSelected", Constants.HEADING_NIC_INVESTIGATION);
				ModelAndView mav = new ModelAndView("investigation.compare.info");
//				mav.addObject("fpMap", fpMap);
//				mav.addObject("fpMapDoiChieu", fpMapDoiChieu);
				mav.addObject("viewFPFalg", viewFPFalg);
				mav.addObject("dsBiTrung", listTrung.getListTargetData());
				model.addAttribute("showBody", listTrung.getListTargetData().size() > 0 ? "Y" : "N");
//				mav.addObject("HoSoXNC1", listHistory.size() > 0 ? listHistory.get(0) : new ImmiHistory());
//				mav.addObject("dsXNC", listHistory);
				return mav;
			}
		} catch (Exception e) {

			e.printStackTrace();
		}
		return new ModelAndView("investigation.compare.info");

	}
	
	public String convertImageFormatWsqToJpg1(byte[] imageBase64String) {
		String convertedBase64String = "";
		try {
			convertedBase64String = Base64.encodeBase64String(HelperClass.ConvertWSQToJPG(imageBase64String));
			// System.out.println("The image binary after base64 conversion===="+convertedBase64String);
		} catch (Exception ex) {
			System.out.println("Error occured while converting the wsq image to jpeg" + ex.getMessage());
		}
		return convertedBase64String;
	}
	
	public Map<Integer, FingerprintInfo> getVanTayTheoMaHoSo(NicTransaction nicTransaction, UserSession userSession){
		Map<Integer, FingerprintInfo> fpMap = new HashMap<Integer, FingerprintInfo>();
		String viewFPFalg = "Y";
		//NicUploadJob jobInfo = uploadJobService.findById(Long.parseLong(investigationAssignmentData.getProcessJobIds()[0]));
		//NicTransaction nicTransaction = jobInfo.getNicTransaction();
		try {
			String transactionId = nicTransaction.getTransactionId();
			// [19Feb2016][Tue] - Add
			// String nin = request.getParameter("nin");				

			String fpIndicator = nicTransaction.getNicRegistrationData()
					.getFpIndicator();
			String fpQuality = nicTransaction.getNicRegistrationData()
					.getFpQuality();
			String fpEncode = "";
			if (!StringUtils.isEmpty(nicTransaction.getNicRegistrationData()
					.getFpEncode()))
				fpEncode = nicTransaction.getNicRegistrationData()
						.getFpEncode();
			String fpVerifyScore = "";
			String fpVerifyMatchScore = "";
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

			Parameters goodFpQuaParam = parametersService
					.findById(goodFpQuaParamId);

			if (goodFpQuaParam != null) {
				goodFpQuas = goodFpQuaParam.getParaShortValue().split(",");
			} else {
				goodFpQuas = defGoodFpQuas.split(",");
			}

			ParametersId accFpQuaParamId = new ParametersId();
			accFpQuaParamId.setParaName(Parameters.FP_QUALITY_ACCEPTABLE);
			accFpQuaParamId.setParaScope(Parameters.SCOPE_SYSTEM);

			Parameters accFpQuaParam = parametersService
					.findById(accFpQuaParamId);

			if (accFpQuaParam != null) {
				accFpQuas = accFpQuaParam.getParaShortValue().split(",");
			} else {
				accFpQuas = defAcceptFpQuas.split(",");
			}

			ParametersId paramId = new ParametersId();
			paramId.setParaName(Parameters.FP_VERIFY_MATCH_SCORE);
			paramId.setParaScope(Parameters.SCOPE_SYSTEM);

			Parameters parameter = parametersService.findById(paramId);
			if (parameter != null) {
				fpVerifyMatchScore = parameter.getParaShortValue();
			}

			List<Integer> fpEncodesArr = new ArrayList<Integer>();
			for (String fpEndVal : fpEncodes) {
				if (!StringUtils.isEmpty(fpEndVal))
					fpEncodesArr.add(Integer.parseInt(fpEndVal));
			}

			if (userSession.getFunctions().contains("VIEW_FP")) {

				List<NicTransactionAttachment> fps = nicTransactionAttachmentService.findNicTransactionAttachments(transactionId,
								NicTransactionAttachment.DOC_TYPE_FINGERPRINT,
								null).getListModel();
				if (CollectionUtils.isNotEmpty(fps)) {
					for (NicTransactionAttachment fp : fps) {
						Integer fpPos = Integer.parseInt(fp.getSerialNo());

						FingerprintInfo info = new FingerprintInfo();
						info.setFpImage(Base64.encodeBase64String(fp
								.getDocData()));
						info.setFpQuaScr(Integer.parseInt(fpQualitys[fpPos - 1]
								.split("-")[1]));						
						info.setFpIndicator(fpIndicators[fpPos - 1].split("-")[1]);
						info.setGoodFPQuaScr(Integer
								.parseInt(goodFpQuas[fpPos - 1].split("-")[1]));
						info.setAccetableFPQuaScr(Integer
								.parseInt(accFpQuas[fpPos - 1].split("-")[1]));

						if (fpEncodesArr.contains(fpPos)) {
							info.setEncodeFlag(true);
						}

						info.setBaseImageConvert(convertImageFormatWsqToJpg1(fp.getDocData()));

						fpMap.put(fpPos, info);
					}

				}

				for (String fpIndi : fpIndicators) {
					Integer fpPos = Integer.parseInt(fpIndi.split("-")[0]);
					if (!fpMap.containsKey(fpPos)) {

						FingerprintInfo info = new FingerprintInfo();
						info.setFpImage(null);
						info.setFpQuaScr(Integer.parseInt(fpQualitys[fpPos - 1]
								.split("-")[1]));
						
						info.setFpIndicator(fpIndicators[fpPos - 1].split("-")[1]);
						info.setGoodFPQuaScr(Integer
								.parseInt(goodFpQuas[fpPos - 1].split("-")[1]));
						info.setAccetableFPQuaScr(Integer
								.parseInt(accFpQuas[fpPos - 1].split("-")[1]));

						if (fpEncodesArr.contains(fpPos)) {
							info.setEncodeFlag(true);
						}
						fpMap.put(fpPos, info);
					}
				}

			} else {

				viewFPFalg = "N";

				List<NicTransactionAttachment> fps = nicTransactionAttachmentService
						.findNicTransactionAttachments(transactionId,
								NicTransactionAttachment.DOC_TYPE_FINGERPRINT,
								null).getListModel();
				if (CollectionUtils.isNotEmpty(fps)) {
					for (NicTransactionAttachment fp : fps) {
						Integer fpPos = Integer.parseInt(fp.getSerialNo());

						FingerprintInfo info = new FingerprintInfo();
						info.setFpImage(Base64.encodeBase64String(fp
								.getDocData()));
						info.setFpQuaScr(Integer.parseInt(fpQualitys[fpPos - 1]
								.split("-")[1]));
						// [19Feb2016][Tue] - Add
						// info.setFpVerifyScr(Integer.parseInt(fpVerifyScores[fpPos
						// - 1].split("-")[1]));
						info.setFpIndicator(fpIndicators[fpPos - 1].split("-")[1]);

						if (fpEncodesArr.contains(fpPos)) {
							info.setEncodeFlag(true);
						}

						info.setBaseImageConvert(convertImageFormatWsqToJpg1(fp.getDocData()));
						fpMap.put(fpPos, info);
					}
				}

				for (String fpIndi : fpIndicators) {
					Integer fpPos = Integer.parseInt(fpIndi.split("-")[0]);
					if (!fpMap.containsKey(fpPos)) {
						FingerprintInfo info = new FingerprintInfo();
						info.setFpImage(null);
						info.setFpQuaScr(Integer.parseInt(fpQualitys[fpPos - 1]
								.split("-")[1]));
						// [19Feb2016][Tue] - Add
						// info.setFpVerifyScr(Integer.parseInt(fpVerifyScores[fpPos
						// - 1].split("-")[1]));
						info.setFpIndicator(fpIndicators[fpPos - 1].split("-")[1]);

						if (fpEncodesArr.contains(fpPos)) {
							info.setEncodeFlag(true);
						}
						fpMap.put(fpPos, info);
					}
				}
			}

		} catch (Exception e) {

		}
		for(Map.Entry<Integer, FingerprintInfo> map : fpMap.entrySet()){
			if(map.getValue().getBaseImageConvert() == null){
				map.getValue().setBaseImageConvert("N");
			}
		}
		return fpMap;
	}
	
	@ResponseBody
	@RequestMapping(value="/getInfoTransaction/{transactionId}")
	public ModelAndView getInfoTransaction(@PathVariable String transactionId,
			WebRequest request, HttpServletRequest httpRequest, ModelMap model) {
			ModelAndView mav = new ModelAndView("investigation-family");
			InvestigationListInfoTargetDto dto = new InvestigationListInfoTargetDto();
			try {
				NicTransaction nicTransaction = uploadJobService.getNicTransaction(transactionId);
				NicRegistrationData nicData = uploadJobService.getNicRegistrationData(transactionId);
				if(nicTransaction != null){
					dto.setNin_O(nicTransaction.getNin());
				}
				if(nicData !=  null){
					dto.setFullName_O(HelperClass.createFullName(nicData.getSurnameFull(), nicData.getMiddlenameFull(), nicData.getFirstnameFull()));
					dto.setReligion_O(StringUtils.isNotEmpty(nicData.getReligion()) ? codesService.getCodeValueDescByIdName(Constants.CODE_RELIGION_ID, nicData.getReligion(), "") : "");
					//dto.setAddress(nicData.getAddressLine1());
					if(nicData.getGender() != null){
						dto.setGender_O(nicData.getGender().equals("M") ? "Nam" : "Nữ");
					}else{
						dto.setGender_O("");
					}
					dto.setDateNin_O(nicData.getDateNin() != null ?HelperClass.convertDateToString2(nicData.getDateNin()) : "");
					dto.setNation_O(StringUtils.isNotEmpty(nicData.getNation()) ? codesService.getCodeValueDescByIdName("CODE_NATION" ,nicData.getNation(), "") : "");
					String date = nicData.getDateOfBirth()!= null ? HelperClass.convertDateToString2(nicData.getDateOfBirth()) : "";
					dto.setDob_O(HelperClass.loadDateOfBirth(date, nicData.getDefDateOfBirth()));
					//dto.setDob_O(nicData.getDateOfBirth()!= null ? HelperClass.convertDateToString2(nicData.getDateOfBirth()) : "");
					dto.setFullNameFather_O(nicData.getFatherFullname());
					dto.setFullNameMother_O(nicData.getMotherFullname());
					String datef = nicData.getFatherDateOfBirth() != null ? HelperClass.convertDateToString2(nicData.getFatherDateOfBirth()) : "";
					dto.setDobFather_O(HelperClass.loadDateOfBirth(datef, nicData.getFatherDefDateOfBirth()));
					//dto.setDobFather_O(nicData.getFatherDob() != null ? HelperClass.convertDateToString2(nicData.getFatherDob()) : "");
					String datem = nicData.getMotherDateOfBirth() != null ? HelperClass.convertDateToString2(nicData.getMotherDateOfBirth()) : "";
					dto.setDobMother_O(HelperClass.loadDateOfBirth(datem, nicData.getMotherDefDateOfBirth()));
					//dto.setDobMother_O(nicData.getMotherDob() != null ? HelperClass.convertDateToString2(nicData.getMotherDob()) : "");
					dto.setAddressNin_O(StringUtils.isNotEmpty(nicData.getAddressNin()) ? codesService.getCodeValueDescByIdName("CODE_IDPlace" ,nicData.getAddressNin(), "") : "");
					dto.setFullNameSpouser_O(nicData.getSpouseFullname());
					String dates = nicData.getSpouseDateOfBirth() != null ? HelperClass.convertDateToString2(nicData.getSpouseDateOfBirth()) : "";
					dto.setDobSpouser_O(HelperClass.loadDateOfBirth(dates, nicData.getSpouseDefDateOfBirth()));
					//dto.setDobSpouser_O(nicData.getSpouseDob() != null ? HelperClass.convertDateToString2(nicData.getSpouseDob()) : "");
					dto.setPhone_O(nicData.getContactNo() != null ? nicData.getContactNo() : "");
					String address = nicData.getAddressLine1();
					String px = codesService.getCodeValueDescByIdName(RegistrationConstants.CODE_TOWN, nicData.getAddressLine4(), "");
					String tp = codesService.getCodeValueDescByIdName(RegistrationConstants.CODE_DISTRICT, nicData.getAddressLine5(), "");
					if(StringUtils.isNotEmpty(px)){
						address += ", " + px;
					}
					if(StringUtils.isNotEmpty(tp)){
						address += ", " + tp;
					}
					dto.setAddress_O(address);
				}
				BufEppPersonInvestigation buf = bufPersonService.findByTranId(transactionId);
//				if(buf != null){
//					if(StringUtils.isNotEmpty(buf.getDataFamily())){
//						JAXBContext jaxbContext = JAXBContext.newInstance(EppBufFamilyDto.class);
//						Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
//						StringReader reader = new StringReader(buf.getDataFamily());
//						EppBufFamilyDto faDto = (EppBufFamilyDto) unmarshaller.unmarshal(reader);
//						if(faDto != null){
//							dto.setFullNameFather_O(StringUtils.isNotEmpty(faDto.getFatherSurname()) ? faDto.getFatherSurname() : "");
////							String fatherDob = "";
////							if(faDto.getFatherDob() != null){
////								String pattern = "dd/MM/yyyy";
////								DateFormat df = new SimpleDateFormat(pattern);
////								fatherDob = df.format(faDto.getFatherDob());
////							}
////							dto.setDobFather_O(fatherDob);
//							
//							dto.setFullNameMother_O(StringUtils.isNotEmpty(faDto.getMotherSurname()) ? faDto.getMotherSurname() : "");
////							String motherDob = "";
////							if(faDto.getMotherDob() != null){
////								String pattern = "dd/MM/yyyy";
////								DateFormat df = new SimpleDateFormat(pattern);
////								motherDob = df.format(faDto.getMotherDob());
////							}
////							dto.setDobMother_O(motherDob);
//							
//							dto.setFullNameSpouser_O(StringUtils.isNotEmpty(faDto.getSpouseSurname()) ? faDto.getSpouseSurname() : "");
////							String spouserDob = "";
////							if(faDto.getSpouseDob()!= null){
////								String pattern = "dd/MM/yyyy";
////								DateFormat df = new SimpleDateFormat(pattern);
////								spouserDob = df.format(faDto.getSpouseDob());
////							}
////							dto.setDobSpouser_O(spouserDob);
//							dto.setDsSon_O(faDto.getEpp_person());
//						}
//					}
//					if(StringUtils.isNotEmpty(buf.getDataInfoDocument())){
//						JAXBContext jaxbContext = JAXBContext.newInstance(EppBufInfoDocDto.class);
//						Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
//						StringReader reader = new StringReader(buf.getDataInfoDocument());
//						EppBufInfoDocDto dataDto = (EppBufInfoDocDto) unmarshaller.unmarshal(reader);
//						if(dataDto != null){
//							dto.setTransactionId_O(dataDto.getTransactionId());
//							dto.setPackageId_O(dataDto.getRecieptNo());
//							dto.setTypeTransaction_O(dataDto.getTypeTransaction());
//							dto.setReg_O(dataDto.getRegSiteCode());
//							dto.setStatus_O(dataDto.getStatus());
//							dto.setNote_O(dataDto.getNote());
//							dto.setOfficerMasterApprove_O(dataDto.getNameApprover());
//							dto.setPosition_O(dataDto.getLevelApprover());
//							dto.setPassportNo_O(dataDto.getPassportNo());
//							dto.setIssueDatePassport_O(HelperClass.convertDateToString2(dataDto.getIssuePassportDate()));
//							dto.setPayDatePassport_O(HelperClass.convertDateToString2(dataDto.getReleaseDate()));
//							dto.setPayPlacePassport_O(dataDto.getPlaceIssue());
//							dto.setPersonRecieve_O(dataDto.getReceiver());
//							dto.setRemark_O(dataDto.getRemarkApprove());
//						}
//					}
//				}
			} catch (Exception e) {
				// TODO: handle exception
			}
			mav.addObject("HSTrung1", dto);
			mav.addObject("chuHS", dto);
		return mav;
	}
	
	@ResponseBody
	@RequestMapping(value="/getInfoXNCTransaction/{transactionId}")
	public ModelAndView getInfoXNCTransaction(@PathVariable String transactionId,
			WebRequest request, HttpServletRequest httpRequest, ModelMap model) {
			ModelAndView mav = new ModelAndView("investigation-immig");
			List<ImmiHistory> listHistory = null;
			String images = "";
			try {
			 	listHistory = ListHistoryByTarget(transactionId).getListImmi();
			 	if(listHistory == null)
			 		listHistory = new ArrayList<ImmiHistory>();
			 	List<NicTransactionAttachment> photoList = nicTransactionAttachmentService.findNicTransactionAttachments(
						transactionId, NicTransactionAttachment.DOC_TYPE_PHOTO_CAPTURE, NicTransactionAttachment.DEFAULT_SERIAL_NO).getListModel();				
				if (CollectionUtils.isNotEmpty(photoList) && photoList.size() > 0) {
					images = Base64.encodeBase64String(photoList.get(0).getDocData());					
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			NicRegistrationData reg = regDataService.findById(transactionId);
			
			ImmiHistory immi = new ImmiHistory();
			/*Tạm đóng do thay đổi lại trường trong xnc*/
			/*immi.setFullName(HelperClass.createFullName(reg.getSurnameFull(), reg.getMiddlenameFull(), reg.getFirstnameFull()));
			immi.setFmDob(HelperClass.convertDateToString2(reg.getDateOfBirth()));
			immi.setGender(reg.getGender().equals("M") ? "Nam" : "Nữ");
			
			String noiSinh = codesService.getCodeValueDescByIdName("DISTRICT", reg.getPlaceOfBirth(), "");
			if(StringUtils.isNotEmpty(noiSinh)){
				immi.setPob(noiSinh);								
			}else{
				immi.setPob(reg.getPlaceOfBirth());	
			}*/
			mav.addObject("rootXNC", immi);
			model.addAttribute("tranId", transactionId);
			mav.addObject("HoSoXNC1", listHistory.size() > 0 ? listHistory.get(0) : new ImmiHistory());
			mav.addObject("listXNC", listHistory);
			model.addAttribute("stageImmi", listHistory.size() > 0 ? "Y" : "N");
			mav.addObject("photoXNC", images);
		return mav;
	}
	
	
	@ResponseBody
	@RequestMapping(value="/getInfoMathuyTransaction/{transactionId}")
	public ModelAndView getInfoMathuyTransaction(@PathVariable String transactionId,
			WebRequest request, HttpServletRequest httpRequest, ModelMap model) {
			ModelAndView mav = new ModelAndView("investigation-lost");
			List<NicTransaction> listHistory = new ArrayList<NicTransaction>();
			String images = "";
			try {
			 	listHistory = ListHistoryByTarget(transactionId).getListLost();
			 	if(listHistory == null || listHistory.size() <= 0)
			 		listHistory = new ArrayList<NicTransaction>();
			 	List<NicTransactionAttachment> photoList = nicTransactionAttachmentService.findNicTransactionAttachments(
						transactionId, NicTransactionAttachment.DOC_TYPE_PHOTO_CAPTURE, NicTransactionAttachment.DEFAULT_SERIAL_NO).getListModel();				
				if (CollectionUtils.isNotEmpty(photoList) && photoList.size() > 0) {
					images = Base64.encodeBase64String(photoList.get(0).getDocData());					
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			mav.addObject("HoSoXNC1", listHistory.size() > 0 ? listHistory.get(0).getNicRegistrationData() : new NicRegistrationData());
			mav.addObject("listXNC", listHistory);
			mav.addObject("photoXNC", images);
		return mav;
	}
	
	@ResponseBody
	@RequestMapping(value="/getLichSuCPTransaction/{transactionId}")
	public ModelAndView getLichSuCPTransaction(@PathVariable String transactionId,
			WebRequest request, HttpServletRequest httpRequest, ModelMap model) {
			ModelAndView mav = new ModelAndView("investigation-his");
			InvestigationListInfoTargetDto dto = null;
			try {
				dto = getInfoTransactionData(transactionId);
			} catch (Exception e) {
				e.printStackTrace();
			}
			mav.addObject("HSTrung1", dto);
			mav.addObject("dsThanNhan", dto.getInfoFa());
		return mav;
	}
	
	@ResponseBody
	@RequestMapping(value="/getStayTransaction/{transactionId}")
	public ModelAndView getStayTransaction(@PathVariable String transactionId,
			WebRequest request, HttpServletRequest httpRequest, ModelMap model) {
		ModelAndView mav = new ModelAndView("investigation-stay");
		return mav;
	}
	
	@ResponseBody
	@RequestMapping(value="/getFacial/{transactionId}")
	public String getFacialId(@PathVariable String transactionId,
			WebRequest request, HttpServletRequest httpRequest, ModelMap model) {
		List<NicTransactionAttachment> photoList = nicTransactionAttachmentService.findNicTransactionAttachments(
				transactionId, NicTransactionAttachment.DOC_TYPE_PHOTO_CAPTURE, NicTransactionAttachment.DEFAULT_SERIAL_NO).getListModel();				
		if (CollectionUtils.isNotEmpty(photoList) && photoList.size() > 0) {
			String images = Base64.encodeBase64String(photoList.get(0).getDocData());
			return "<img src=\'data:image/jpg;base64,"+ images +"' class=\"img-border doGetAJpgSafeVersion\" height=\"180\" width=\"135\" />";			
		}else{
			return "<img class=\"img-border\" height=\"180\" width=\"135\" src=\"/eppcentral/resources/images/No_Image.jpg\">";			
		}
	}
	
	
	@ResponseBody
	@RequestMapping(value="/getFacial1/{transactionId}")
	public String getFacial1Id(@PathVariable String transactionId,
			WebRequest request, HttpServletRequest httpRequest, ModelMap model) {
		List<NicTransactionAttachment> photoList = nicTransactionAttachmentService.findNicTransactionAttachments(
				transactionId, NicTransactionAttachment.DOC_TYPE_PHOTO_CAPTURE, NicTransactionAttachment.DEFAULT_SERIAL_NO).getListModel();				
		if (CollectionUtils.isNotEmpty(photoList) && photoList.size() > 0) {
			String images = Base64.encodeBase64String(photoList.get(0).getDocData());
			return "<img src=\'data:image/jpg;base64,"+ images +"' class=\"img-border doGetAJpgSafeVersion\" height=\"170\" width=\"125\" />";			
		}else{
			return "<img class=\"img-border\" height=\"170\" width=\"125\" src=\"/eppcentral/resources/images/No_Image.jpg\">";			
		}
	}
	
	@ResponseBody
	@RequestMapping(value="/getGoTransaction/{transactionId}")
	public ModelAndView getGoTransaction(@PathVariable String transactionId,
			WebRequest request, HttpServletRequest httpRequest, ModelMap model) {
		ModelAndView mav = new ModelAndView("investigation-go");
		return mav;
	}
	
	@ResponseBody
	@RequestMapping(value="/showDocument/{transactionId}")
	public String showDocument(@PathVariable String transactionId,
			WebRequest request, HttpServletRequest httpRequest, ModelMap model) {
		String photoDoc = "";
		List<NicTransactionAttachment> nicTransactionDocuments = null;
		try {

			nicTransactionDocuments = nicTransactionAttachmentService.getNicTransactionAttachments(transactionId, new String[]{NicTransactionAttachment.KEY_SC_DSIGNER, NicTransactionAttachment.KEY_SCAN_DOCUMENT}, new String[]{NicTransactionAttachment.DEFAULT_SERIAL_NO, NicTransactionAttachment.DEFAULT_SERIAL_NO_A});
			if (CollectionUtils.isNotEmpty(nicTransactionDocuments)) {
				for (NicTransactionAttachment nicTransProofDocument : nicTransactionDocuments) {
					
					String photoStr = Base64.encodeBase64String(nicTransProofDocument.getDocData());
					photoDoc += "<img src=\'data:image/jpg;base64,"+ photoStr +"' class=\"img-border doGetAJpgSafeVersion\" />";
				}
			}
		} catch (Exception ex) {
			logger.info("Error occurred while getting the transaction documents for Transaction:"
					+ transactionId + ", Reason: " + ex.getMessage());
		}
		return photoDoc;
	}
	
	
	@ResponseBody
	@RequestMapping(value="/getDocument/{transactionId}")
	public String getDocument(@PathVariable String transactionId,
			WebRequest request, HttpServletRequest httpRequest, ModelMap model) {
		//ModelAndView mav = new ModelAndView("investigation-doc");
		String photoStr = "";
		// /Xử lý tài liệu
				//ist<RicRegistrationDocumentDTO> proofDocList = new ArrayList<RicRegistrationDocumentDTO>();
				List<NicTransactionAttachment> nicTransactionDocuments = null;
				try {
					httpRequest.setAttribute("txnId", transactionId);
					//ImageUtil imageUtil = new ImageUtil();

					String[] excludedDocTypes = {
							NicTransactionAttachment.KEY_SC_DSIGNER,
							NicTransactionAttachment.KEY_SC_NBERNIN,
							NicTransactionAttachment.KEY_SC_PASPORT,
							NicTransactionAttachment.KEY_SCAN_DOCUMENT };

					nicTransactionDocuments = nicTransactionAttachmentService.getNicTransactionAttachments(transactionId, excludedDocTypes, null);
					if (CollectionUtils.isNotEmpty(nicTransactionDocuments)) {
						for (NicTransactionAttachment nicTransProofDocument : nicTransactionDocuments) {
							//RicRegistrationDocumentDTO proofDoc = new RicRegistrationDocumentDTO();
							String decodedDocString = Base64.encodeBase64String(nicTransProofDocument.getDocData());
							
							photoStr += "<img src=\'data:image/jpg;base64,"+ decodedDocString +"' class=\"img-border doGetAJpgSafeVersion\" />";
						}
					}
				} catch (Exception ex) {
					logger.info("Error occurred while getting the transaction documents for Transaction:"
							+ transactionId + ", Reason: " + ex.getMessage());
				}
				//httpRequest.setAttribute("proofDocList", proofDocList);
				return photoStr;
	}
	
	private String getDmsLink(Long transactionDocId, String transactionId,
			String docType) throws Exception {

		String attachmentSource = null;
		try {
			Parameters parameter = this.parametersService.getParamDetails(
					"SYSTEM", "TRANSACTION_ATTACHMENT_SOURCE");
			if (parameter != null
					&& StringUtils.isNotBlank(parameter.getParaShortValue())) {
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
					"SYSTEM", "TRANSACTION_ATTACHMENT_SOURCE");
			if (parameter != null && parameter.getParaLobValue() != null) {
				urlPrefix = parameter.getParaLobValue();
			}
		} catch (Exception e) {
		}

		if (StringUtils.isBlank(urlPrefix)) {
			InvalidParameterException exception = new InvalidParameterException(
					"Parameter DMS_URL is required.");
			exception.printStackTrace();
			throw exception;
		}

		return urlPrefix + DmsUtility.DMS__URL_DELIMITER_1 + transactionDocId
				+ DmsUtility.DMS__URL_DELIMITER_2 + transactionId
				+ DmsUtility.DMS__URL_DELIMITER_3 + docType;
	}
	
	@ResponseBody
	@RequestMapping(value="/getFingerPrint/{jobId}/{transactionId}")
	public ModelAndView getFingerPrint(@PathVariable String jobId, @PathVariable String transactionId, 
			WebRequest request, HttpServletRequest httpRequest, ModelMap model) {
		
		HttpSession session = httpRequest.getSession();
		UserSession userSession = (UserSession) session.getAttribute("userSession");
		ModelAndView mav = new ModelAndView("investigation-finger");
		NicUploadJob rootJob = uploadJobService.findById(Long.parseLong(jobId));
		Map<Integer, FingerprintInfo> fpMap = null;
		Map<Integer, FingerprintInfo> fpMapDoiChieu = null;
		Map<Integer, Integer> fpMapScore = null;
		String viewFPFalg = "Y";
		NicTransaction rootTransaction = null;
		if(rootJob != null && rootJob.getNicTransaction() != null){
			rootTransaction = rootJob.getNicTransaction();
			fpMap = getVanTayTheoMaHoSo(rootTransaction, userSession);
			for(int i = 1; i <= 10; i++){
				FingerprintInfo fgi = fpMap.get(i);
				if(fgi == null){
					FingerprintInfo info = new FingerprintInfo();
					info.setBaseImageConvert("N");
					mav.addObject(("fpMap" + i), info);					
				}else{
					mav.addObject(("fpMap" + i), fgi);	
				}
			}
		}
		fpMapScore = MatchingPersonalScore(Long.parseLong(jobId), transactionId);
		for(int i = 1; i <= 10; i++){
			Integer score = fpMapScore.get(i);
			if(score == null){
				mav.addObject(("fpMapScore" + i), "");				
			}else{
				Double score1 = Double.parseDouble(score + "");
				mav.addObject(("fpMapScore" + i), score1/100);			
			}
		}
		NicTransaction compareTran = nicTransactionService.findById(transactionId);
		
		if(compareTran != null){
			fpMapDoiChieu = getVanTayTheoMaHoSo(compareTran, userSession);
			for(int i = 1; i <= 10; i++){
				FingerprintInfo fgi = fpMapDoiChieu.get(i);
				if(fgi == null){
					FingerprintInfo info = new FingerprintInfo();
					info.setBaseImageConvert("N");
					mav.addObject(("fpMapDoiChieu" + i), info);					
				}else{
					mav.addObject(("fpMapDoiChieu" + i), fgi);	
				}
			}
		}
		//mav.addObject("fpMap", fpMap);
		//mav.addObject("fpMapDoiChieu", fpMapDoiChieu);
		mav.addObject("viewFPFalg", viewFPFalg);
		return mav;
	}
	
	public InvestigationListInfoTargetDto getInfoTransactionData(String transactionId){
		InvestigationListInfoTargetDto dto = new InvestigationListInfoTargetDto();
			try {
				NicTransaction nicTransaction = uploadJobService.getNicTransaction(transactionId);
				NicRegistrationData nicData = uploadJobService.getNicRegistrationData(transactionId);
				List<NicUploadJob> nicJob = uploadJobService.findAllByTransactionId(transactionId);
				if(nicJob != null){
					if(nicJob.get(0).getInvestigationOfficerId() != null){
						Users users = userService.findByUserId(nicJob.get(0).getInvestigationOfficerId());
						if(users != null)
							dto.setByA_O(users.getUserName());						
					}
				}
				if(nicTransaction != null){
					dto.setNin_O(nicTransaction.getNin());
					dto.setDanhSachA_O(nicTransaction.getPackageId());
					NicListHandover handover = listHandoverService.findByPackageId(new NicListHandoverId(nicTransaction.getPackageId(), null)).getModel();
					if(handover != null){
						dto.setDateA_O(HelperClass.convertDateToString2(handover.getCreateDate()));
					}
					//dto.setByA_O(nicTransaction.getRegSiteCode());
					dto.setPassportNo_O(nicTransaction.getPrevPassportNo());
				}
				if(nicData !=  null){
					dto.setFullName_O(HelperClass.createFullName(nicData.getSurnameFull(), nicData.getMiddlenameFull(), nicData.getFirstnameFull()));
					dto.setReligion_O(StringUtils.isNotEmpty(nicData.getReligion()) ? codesService.getCodeValueDescByIdName(Constants.CODE_RELIGION_ID, nicData.getReligion(), "") : "");
					//dto.setAddress(nicData.getAddressLine1());
					String address = nicData.getAddressLine1();
					String px = codesService.getCodeValueDescByIdName(RegistrationConstants.CODE_TOWN, nicData.getAddressLine4(), "");
					String tp = codesService.getCodeValueDescByIdName(RegistrationConstants.CODE_DISTRICT, nicData.getAddressLine5(), "");
					if(StringUtils.isNotEmpty(px)){
						address += ", " + px;
					}
					if(StringUtils.isNotEmpty(tp)){
						address += ", " + tp;
					}
					dto.setAddress_O(address);
					///dto.setAddress_O(nicData.getAddressLine1());
					if(nicData.getGender() != null){
						dto.setGender_O(nicData.getGender().equals("M") ? "Nam" : "Nữ");
					}else{
						dto.setGender_O("");
					}
					dto.setDateNin_O(nicData.getDateNin() != null ?HelperClass.convertDateToString2(nicData.getDateNin()) : "");
					dto.setNation_O(StringUtils.isNotEmpty(nicData.getNation()) ? codesService.getCodeValueDescByIdName("CODE_NATION" ,nicData.getNation(), "") : "");
					String date = nicData.getDateOfBirth() != null ? HelperClass.convertDateToString2(nicData.getDateOfBirth()) : "";
					dto.setFullNameFather_O(nicData.getFatherFullname());
					dto.setFullNameMother_O(nicData.getMotherFullname());
					String datef = nicData.getFatherDateOfBirth() != null ? HelperClass.convertDateToString2(nicData.getFatherDateOfBirth()) : "";
					dto.setDobFather_O(HelperClass.loadDateOfBirth(datef, nicData.getFatherDefDateOfBirth()));
					//dto.setDobFather_O(nicData.getFatherDob() != null ? HelperClass.convertDateToString2(nicData.getFatherDob()) : "");
					String datem = nicData.getMotherDateOfBirth() != null ? HelperClass.convertDateToString2(nicData.getMotherDateOfBirth()) : "";
					dto.setDobMother_O(HelperClass.loadDateOfBirth(datem, nicData.getMotherDefDateOfBirth()));
					//dto.setDobMother_O(nicData.getMotherDob() != null ? HelperClass.convertDateToString2(nicData.getMotherDob()) : "");
					dto.setAddressNin_O(StringUtils.isNotEmpty(nicData.getAddressNin()) ? codesService.getCodeValueDescByIdName("CODE_IDPlace" ,nicData.getAddressNin(), "") : "");
					dto.setFullNameSpouser_O(nicData.getSpouseFullname());
					String dates = nicData.getSpouseDateOfBirth() != null ? HelperClass.convertDateToString2(nicData.getSpouseDateOfBirth()) : "";
					dto.setDobSpouser_O(HelperClass.loadDateOfBirth(dates, nicData.getSpouseDefDateOfBirth()));
					dto.setPhone_O(nicData.getContactNo() != null ? nicData.getContactNo() : "");
					//dto.setPob_O(nicData.getPlaceOfBirth() != null ? nicData.getPlaceOfBirth() : "");
					String noiSinh = codesService.getCodeValueDescByIdName("DISTRICT", nicData.getPlaceOfBirth(), "");
					if(StringUtils.isNotEmpty(noiSinh)){
						dto.setPob_O(noiSinh);								
					}else{
						dto.setPob_O(nicData.getPlaceOfBirth());	
					}
					dto.setJob_O(nicData.getJob() != null ? nicData.getJob() : "");
					dto.setInfoFa(dto.getInfoFa());
					dto.setNationality_O(StringUtils.isNotEmpty(nicData.getNationality()) ? codesService.getCodeValueDescByIdName("NATIONALITY" ,nicData.getNationality(), "") : "");
				}
				List<NicTransactionAttachment> photoList = nicTransactionAttachmentService.findNicTransactionAttachments(
						transactionId, NicTransactionAttachment.DOC_TYPE_PHOTO_CAPTURE, NicTransactionAttachment.DEFAULT_SERIAL_NO).getListModel();				
				if (CollectionUtils.isNotEmpty(photoList) && photoList.size() > 0) {
					String images = Base64.encodeBase64String(photoList.get(0).getDocData());
					dto.setPhoto_O("<img src=\'data:image/jpg;base64,"+ images +"' class=\"img-border doGetAJpgSafeVersion\" height=\"180\" width=\"135\" />");
					dto.setImage_O(images);
				}else{
					dto.setPhoto_O("<img class=\"img-border\" height=\"180\" width=\"135\" src=\"/eppcentral/resources/images/No_Image.jpg\">");
					dto.setImage_O("");
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		return dto;
	}
	
	@ResponseBody
	@RequestMapping(value="/getDetailXNCTransaction/{transactionId}/{id}")
	public ImmiHistory getDetailXNCTransaction(@PathVariable String transactionId, @PathVariable String id, 
			WebRequest request, HttpServletRequest httpRequest, ModelMap model) {
			List<ImmiHistory> listHistory = null;
			ImmiHistory immi = null;
			try {
			 	listHistory = ListHistoryByTarget(transactionId).getListImmi();
			 	if(CollectionUtils.isNotEmpty(listHistory) && listHistory.size() > 0){
			 		for(ImmiHistory imi : listHistory){
			 			if(String.valueOf(imi.getId()).equals(id)){
			 				immi = imi;
			 				break;
			 			}
			 		}
			 	}else{
			 		immi = new ImmiHistory();
			 	}
			 	
			} catch (Exception e) {
				e.printStackTrace();
			}		
		return immi;
	}
	
	
	@RequestMapping(value = "/searchObjectJob")
	public ModelAndView searchObjectJob(
			@ModelAttribute(value = "formData") InvestigationAssignmentData investigationAssignmentData,
			WebRequest request, HttpServletRequest httpRequest, ModelMap model) {
		logger.info("NIC Investigation");
		PaginatedResult<NicUploadJobDto> pr = null;
		//PaginatedResult<NicUploadJobDto> rejectedList = null;
		try {
			//int pageNo = !StringUtils.isEmpty(request.getParameter("pageNo")) ? Integer.parseInt(request.getParameter("pageNo")) : 1;
			// pageSize = 20;
			//int startIndex = 0;
			HttpSession session = httpRequest.getSession();
			UserSession userSession = (UserSession) session
					.getAttribute("userSession");
			logger.info("The Officer Id or User Id is ===============>>>>>>>>>>"
					+ userSession.getUserName());

			// Modified for pagination
			ParametersId id = new ParametersId();
			id.setParaName(Parameters.MAX_RECORDS_PER_PAGE);
			id.setParaScope(Parameters.SCOPE_SYSTEM);
			String tableId = "row";
			String sortedElement = "codeId";
			String order = InvestigationProcessController.DECENDING_ORDER;

			try {
				String sort = request
						.getParameter(new ParamEncoder(tableId)
								.encodeParameterName(TableTagParameters.PARAMETER_SORT));
				if (StringUtils.isNotBlank(sort))
					sortedElement = sort;
			} catch (Exception ex) {
				logMessage(ex);
			}

			try {
				String requestOrder = request
						.getParameter(new ParamEncoder(tableId)
								.encodeParameterName(TableTagParameters.PARAMETER_ORDER));
				if (StringUtils.isNotBlank(requestOrder))
					order = requestOrder;
			} catch (Exception ex) {
				logMessage(ex);
			}

			Order hibernateOrder = Order.desc(sortedElement);
			if (order.equalsIgnoreCase(InvestigationProcessController.DECENDING_ORDER)) {
				hibernateOrder = Order.asc(sortedElement);
			}

			//Parameters parameter = parametersService.findById(id);

//			if (parameter != null) {
//				String pageSizeDb = parameter.getParaShortValue();
//
//				if (StringUtils.isNotBlank(pageSizeDb)
//						&& StringUtils.isNumeric(pageSizeDb)) {
//					pageSize = Integer.parseInt(pageSizeDb);
//				}
//			}
//			pageSize = Constants.PAGE_SIZE_DEFAULT;
//			String pageNumber = request.getParameter((new ParamEncoder(tableId)
//					.encodeParameterName(TableTagParameters.PARAMETER_PAGE)));
//
//			if (StringUtils.isNotBlank(pageNumber)
//					&& StringUtils.isNumeric(pageNumber)) {
//				pageNo = Integer.parseInt(pageNumber);
//			}
			httpRequest.setAttribute("pendingCount",
					uploadJobService.getPendingInvestigationsCount());

			if (investigationAssignmentData == null) {
				investigationAssignmentData = new InvestigationAssignmentData();
			}

			investigationAssignmentData.cleanUpStrings();
			List<String> loadJob = new ArrayList<String>();
			List<String> listJobId1 = new ArrayList<String>(); //Lưu thông tin jobId để load lại khi hoàn thành khớp hs
			for(int i = 0; i < investigationAssignmentData.getSelectedJobIds().length; i++){
				listJobId1.add(investigationAssignmentData.getSelectedJobIds()[i]);		
				if(!investigationAssignmentData.getSelectedJobIds()[i].equals(investigationAssignmentData.getProcessJobIds()[0])){
					loadJob.add(investigationAssignmentData.getSelectedJobIds()[i]);
				}
			}
			NicUploadJob jobLoad = uploadJobService.findById(Long.parseLong(investigationAssignmentData.getProcessJobIds()[0]));
     		List<EppBlacklist> listBlack = ListMatchingEppBlacklist(Long.parseLong(investigationAssignmentData.getProcessJobIds()[0]));	
     		InvestigationListInfoTargetDto dto = null;
     		if(jobLoad != null){
     			//model.addAttribute("noteB", jobLoad.getNoteApprove());
     			model.addAttribute("noteAprovePerson", jobLoad.getNoteApprovePerson() != null ? jobLoad.getNoteApprovePerson() : "");
				model.addAttribute("noteAproveObj", jobLoad.getNoteApproveObj() != null ? jobLoad.getNoteApproveObj() : "");
				model.addAttribute("noteAproveNin", jobLoad.getNoteApproveNin() != null ? jobLoad.getNoteApproveNin() : "");
				model.addAttribute("idMaster", jobLoad.getTransactionId());
				dto = getInfoTransactionData(jobLoad.getTransactionId());
			}
     		if(listBlack == null || listBlack.size() <= 0){    			
     			listBlack = new ArrayList<EppBlacklist>();
     			model.addAttribute("khongTrung", "Y");
     		}
     		else{
     			
     			Long idBb = listBlack.get(0).getId();
     			//Lẩy ảnh đối tượng bị trùng
     			List<EppBlacklistAttachment> listBB_Attach = blacklistService.findAttachmentById(idBb);
     			if(listBB_Attach != null && listBB_Attach.size() > 0){
     				for(EppBlacklistAttachment obj :listBB_Attach){
     					if(obj.getType_().equals("PHOTO")){
     						model.addAttribute("photoTrung", obj.getBase64());
     						break;
     					}
     				}
     			}
     		}
     		if(investigationAssignmentData.getStageLoad().equals("Y")){
				if(jobLoad.getDateApproveInvestigation() != null){
					model.addAttribute("action", "N");
				}else{
					model.addAttribute("action", "Y");
				}
			}else{
				model.addAttribute("action", "Y");
			}
     		model.addAttribute("count", listJobId1.size());
			model.addAttribute("stt", 1);
     		model.addAttribute("stage", investigationAssignmentData.getStageLoad());
     		model.addAttribute("loadJob", loadJob);
     		model.addAttribute("jobCompare", investigationAssignmentData.getProcessJobIds()[0]);
     		model.addAttribute("dsJob", listJobId1);
     		model.addAttribute("rootEntity", dto);
     		model.addAttribute("listBlack", listBlack);
     		model.addAttribute("showBody", listBlack.size() > 0 ? "Y" : "N");
     		model.addAttribute("fakeEntity", listBlack.size() > 0 ? listBlack.get(0) : new EppBlacklist());
			model.addAttribute("fnSelected", Constants.HEADING_NIC_INVESTIGATION);
			ModelAndView mav = new ModelAndView("investigation.search.obj");				
			return mav;
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ModelAndView("investigation.search.obj");

	}
	
	@RequestMapping(value = "/searchNinJob")
	public ModelAndView searchNinJob(
			@ModelAttribute(value = "formData") InvestigationAssignmentData investigationAssignmentData,
			WebRequest request, HttpServletRequest httpRequest, ModelMap model) {
		logger.info("NIC Investigation");
		PaginatedResult<NicUploadJobDto> pr = null;
		//PaginatedResult<NicUploadJobDto> rejectedList = null;
		try {
			//int pageNo = !StringUtils.isEmpty(request.getParameter("pageNo")) ? Integer.parseInt(request.getParameter("pageNo")) : 1;
			// pageSize = 20;
			//int startIndex = 0;
			HttpSession session = httpRequest.getSession();
			UserSession userSession = (UserSession) session
					.getAttribute("userSession");
			logger.info("The Officer Id or User Id is ===============>>>>>>>>>>"
					+ userSession.getUserName());

			// Modified for pagination
			ParametersId id = new ParametersId();
			id.setParaName(Parameters.MAX_RECORDS_PER_PAGE);
			id.setParaScope(Parameters.SCOPE_SYSTEM);
			String tableId = "row";
			String sortedElement = "codeId";
			String order = InvestigationProcessController.DECENDING_ORDER;

			try {
				String sort = request
						.getParameter(new ParamEncoder(tableId)
								.encodeParameterName(TableTagParameters.PARAMETER_SORT));
				if (StringUtils.isNotBlank(sort))
					sortedElement = sort;
			} catch (Exception ex) {
				logMessage(ex);
			}

			try {
				String requestOrder = request
						.getParameter(new ParamEncoder(tableId)
								.encodeParameterName(TableTagParameters.PARAMETER_ORDER));
				if (StringUtils.isNotBlank(requestOrder))
					order = requestOrder;
			} catch (Exception ex) {
				logMessage(ex);
			}

			Order hibernateOrder = Order.desc(sortedElement);
			if (order.equalsIgnoreCase(InvestigationProcessController.DECENDING_ORDER)) {
				hibernateOrder = Order.asc(sortedElement);
			}

			//Parameters parameter = parametersService.findById(id);

//			if (parameter != null) {
//				String pageSizeDb = parameter.getParaShortValue();
//
//				if (StringUtils.isNotBlank(pageSizeDb)
//						&& StringUtils.isNumeric(pageSizeDb)) {
//					pageSize = Integer.parseInt(pageSizeDb);
//				}
//			}
//			pageSize = Constants.PAGE_SIZE_DEFAULT;
//			String pageNumber = request.getParameter((new ParamEncoder(tableId)
//					.encodeParameterName(TableTagParameters.PARAMETER_PAGE)));
//
//			if (StringUtils.isNotBlank(pageNumber)
//					&& StringUtils.isNumeric(pageNumber)) {
//				pageNo = Integer.parseInt(pageNumber);
//			}
			httpRequest.setAttribute("pendingCount",
					uploadJobService.getPendingInvestigationsCount());

			if (investigationAssignmentData == null) {
				investigationAssignmentData = new InvestigationAssignmentData();
			}

			investigationAssignmentData.cleanUpStrings();
			List<String> listJobId1 = new ArrayList<String>(); //Lưu thông tin jobId để load lại khi hoàn thành khớp hs
			List<String> loadJob = new ArrayList<String>(); 
			for(int i = 0; i < investigationAssignmentData.getSelectedJobIds().length; i++){
				listJobId1.add(investigationAssignmentData.getSelectedJobIds()[i]);		
				if(!investigationAssignmentData.getSelectedJobIds()[i].equals(investigationAssignmentData.getProcessJobIds()[0])){
					loadJob.add(investigationAssignmentData.getSelectedJobIds()[i]);
				}
			}
			List<EppIdentification> listEntity = ListMatchingEppIdentification(Long.parseLong(investigationAssignmentData.getProcessJobIds()[0]));
			EppIdentification entity = null;
 			if(listEntity != null && listEntity.size() > 0){
				entity = listEntity.get(0);
				Long idI = listEntity.get(0).getId();
     			//Lẩy ảnh đối tượng bị trùng
     			List<EppIdentificationAttachmnt> listI_Attach = nicIdentificationService.findAttachmentById(idI);
     			if(listI_Attach != null && listI_Attach.size() > 0){
     				for(EppIdentificationAttachmnt obj :listI_Attach){
     					if(obj.getName().equals("PHOTO")){
     						model.addAttribute("photoTrung", obj.getBase64());
     						break;
     					}
     				}
     			}
			}else{
				entity = new EppIdentification();
				model.addAttribute("khongTrung", "Y");
			}
 			NicUploadJob jobById = uploadJobService.findById(Long.parseLong(investigationAssignmentData.getProcessJobIds()[0]));
 			InvestigationListInfoTargetDto matching = null;
 			if(jobById != null){
 				//model.addAttribute("noteB", jobById.getNoteApprove());
 				model.addAttribute("noteAprovePerson", jobById.getNoteApprovePerson() != null ? jobById.getNoteApprovePerson() : "");
				model.addAttribute("noteAproveObj", jobById.getNoteApproveObj() != null ? jobById.getNoteApproveObj() : "");
				model.addAttribute("noteAproveNin", jobById.getNoteApproveNin() != null ? jobById.getNoteApproveNin() : "");
				model.addAttribute("idMaster", jobById.getTransactionId());
				matching = getInfoTransactionData(jobById.getTransactionId());
 				if(matching == null)
 					matching = new InvestigationListInfoTargetDto();
 			}
 			if(investigationAssignmentData.getStageLoad().equals("Y")){
				if(jobById.getDateApproveNin() != null){
					model.addAttribute("action", "N");
				}else{
					model.addAttribute("action", "Y");
					MatchingDataRequest(Long.parseLong(investigationAssignmentData.getProcessJobIds()[0]), null, 3, null, null, userSession.getWorkstationId(),userSession.getUserName(), "");				
				}
			}else{
				model.addAttribute("action", "Y");
				MatchingDataRequest(Long.parseLong(investigationAssignmentData.getProcessJobIds()[0]), null, 3, null, null, userSession.getWorkstationId(),userSession.getUserName(), "");				
			}
 			model.addAttribute("count", listJobId1.size());
			model.addAttribute("stt", 1);
 			model.addAttribute("stage", investigationAssignmentData.getStageLoad());
 			model.addAttribute("loadJob", loadJob);
 			model.addAttribute("jobCompare", investigationAssignmentData.getProcessJobIds()[0]);	
			model.addAttribute("dsJob", listJobId1);
			model.addAttribute("fnSelected", Constants.HEADING_NIC_INVESTIGATION);
			ModelAndView mav = new ModelAndView("investigation.search.nin");	
			mav.addObject("entity_o", entity);
			mav.addObject("countTransaction", listEntity.size());
			mav.addObject("rostEntity", matching);
			return mav;
			
		} catch (Exception e) {

			e.printStackTrace();
		}
		return new ModelAndView("investigation.search.nin");

	}
	
	@RequestMapping(value = "/nextCheckNin")
	public ModelAndView nextCheckNin(
			@ModelAttribute(value = "formData") InvestigationAssignmentData investigationAssignmentData,
			WebRequest request, HttpServletRequest httpRequest, ModelMap model) {
		logger.info("NIC Investigation");
		PaginatedResult<NicUploadJobDto> pr = null;
		//PaginatedResult<NicUploadJobDto> rejectedList = null;
		try {
			//int pageNo = !StringUtils.isEmpty(request.getParameter("pageNo")) ? Integer.parseInt(request.getParameter("pageNo")) : 1;
			// pageSize = 20;
			//int startIndex = 0;
			HttpSession session = httpRequest.getSession();
			UserSession userSession = (UserSession) session
					.getAttribute("userSession");
			logger.info("The Officer Id or User Id is ===============>>>>>>>>>>"
					+ userSession.getUserName());

			// Modified for pagination
			ParametersId id = new ParametersId();
			id.setParaName(Parameters.MAX_RECORDS_PER_PAGE);
			id.setParaScope(Parameters.SCOPE_SYSTEM);
			String tableId = "row";
			String sortedElement = "codeId";
			String order = InvestigationProcessController.DECENDING_ORDER;

			try {
				String sort = request
						.getParameter(new ParamEncoder(tableId)
								.encodeParameterName(TableTagParameters.PARAMETER_SORT));
				if (StringUtils.isNotBlank(sort))
					sortedElement = sort;
			} catch (Exception ex) {
				logMessage(ex);
			}

			try {
				String requestOrder = request
						.getParameter(new ParamEncoder(tableId)
								.encodeParameterName(TableTagParameters.PARAMETER_ORDER));
				if (StringUtils.isNotBlank(requestOrder))
					order = requestOrder;
			} catch (Exception ex) {
				logMessage(ex);
			}

			Order hibernateOrder = Order.desc(sortedElement);
			if (order.equalsIgnoreCase(InvestigationProcessController.DECENDING_ORDER)) {
				hibernateOrder = Order.asc(sortedElement);
			}

			//Parameters parameter = parametersService.findById(id);

//			if (parameter != null) {
//				String pageSizeDb = parameter.getParaShortValue();
//
//				if (StringUtils.isNotBlank(pageSizeDb)
//						&& StringUtils.isNumeric(pageSizeDb)) {
//					pageSize = Integer.parseInt(pageSizeDb);
//				}
//			}
//			pageSize = Constants.PAGE_SIZE_DEFAULT;
//			String pageNumber = request.getParameter((new ParamEncoder(tableId)
//					.encodeParameterName(TableTagParameters.PARAMETER_PAGE)));
//
//			if (StringUtils.isNotBlank(pageNumber)
//					&& StringUtils.isNumeric(pageNumber)) {
//				pageNo = Integer.parseInt(pageNumber);
//			}
			httpRequest.setAttribute("pendingCount",
					uploadJobService.getPendingInvestigationsCount());

			if (investigationAssignmentData == null) {
				investigationAssignmentData = new InvestigationAssignmentData();
			}
			String jobId= "";
			investigationAssignmentData.cleanUpStrings();
			List<String> listJobId1 = new ArrayList<String>(); //Lưu thông tin jobId để load lại khi hoàn thành khớp hs
			//List<String> loadJob = new ArrayList<String>(); 
			for(int i = 0; i < investigationAssignmentData.getSelectedJobIds().length; i++){
				listJobId1.add(investigationAssignmentData.getSelectedJobIds()[i]);		
//				if(!investigationAssignmentData.getSelectedJobIds()[i].equals(investigationAssignmentData.getProcessJobIds()[0])){
//					loadJob.add(investigationAssignmentData.getSelectedJobIds()[i]);
//				}
			}
			if(investigationAssignmentData.getLoadJobIds() == null){
				Long[] listJobId = new Long[investigationAssignmentData.getSelectedJobIds().length];
				
				if(investigationAssignmentData.getSelectedJobIds() != null && investigationAssignmentData.getSelectedJobIds().length > 0){
					for(int i = 0; i < investigationAssignmentData.getSelectedJobIds().length; i++){
						//Mở lại rồi nhé
						uploadJobService.updateOfficerIdByJobId(investigationAssignmentData.getSelectedJobIds()[i], userSession.getUserId());					
						listJobId[i] = Long.parseLong(investigationAssignmentData.getSelectedJobIds()[i]);
						
					}
				}
				List<NicUploadJobDto> pr1 = uploadJobService.findInvestigationProcessByJobId(listJobId, null, new AssignmentFilterAll());
				NicUploadJob jobFocus = null;
				if(StringUtils.isNotEmpty(investigationAssignmentData.getJobId())){
					jobFocus = uploadJobService.findById(Long.parseLong(investigationAssignmentData.getJobId()));
				}
				String photoStr = "";
				String soHoSo = "";
				if(pr1 == null || pr1.size() == 0){
					pr1 = new ArrayList<NicUploadJobDto>();
				}else{
					if(jobFocus != null){
						List<NicTransactionAttachment> photoList = nicTransactionAttachmentService.findNicTransactionAttachments(
								jobFocus.getTransactionId(), NicTransactionAttachment.DOC_TYPE_PHOTO_CAPTURE, NicTransactionAttachment.DEFAULT_SERIAL_NO).getListModel();				
						if (CollectionUtils.isNotEmpty(photoList) && photoList.size() > 0) {
							//String images = Base64.encodeBase64String(photoList.get(0).getDocData());
							photoStr = Base64.encodeBase64String(photoList.get(0).getDocData());
							//dto.setPhotoStr(images);
						}
						soHoSo = jobFocus.getTransactionId();
						//model.addAttribute("noteAprove", jobFocus.getNoteApprove() != null ? jobFocus.getNoteApprove() : "");
						model.addAttribute("noteAprovePerson", jobFocus.getNoteApprovePerson() != null ? jobFocus.getNoteApprovePerson() : "");
						model.addAttribute("noteAproveObj", jobFocus.getNoteApproveObj() != null ? jobFocus.getNoteApproveObj() : "");
						model.addAttribute("noteAproveNin", jobFocus.getNoteApproveNin() != null ? jobFocus.getNoteApproveNin() : "");
					}else{
						List<NicTransactionAttachment> photoList = nicTransactionAttachmentService.findNicTransactionAttachments(
								pr1.get(0).getTransactionId(), NicTransactionAttachment.DOC_TYPE_PHOTO_CAPTURE, NicTransactionAttachment.DEFAULT_SERIAL_NO).getListModel();				
						if (CollectionUtils.isNotEmpty(photoList) && photoList.size() > 0) {
							//String images = Base64.encodeBase64String(photoList.get(0).getDocData());
							photoStr = Base64.encodeBase64String(photoList.get(0).getDocData());
							//dto.setPhotoStr(images);
						}
						soHoSo = pr1.get(0).getTransactionId();
						//model.addAttribute("noteAprove", pr1.get(0).getNoteApprove());
						model.addAttribute("noteAprovePerson", pr1.get(0).getNoteApprovePerson() != null ? pr1.get(0).getNoteApprovePerson() : "");
						model.addAttribute("noteAproveObj", pr1.get(0).getNoteApproveObj() != null ? pr1.get(0).getNoteApproveObj() : "");
						model.addAttribute("noteAproveNin", pr1.get(0).getNoteApproveNin() != null ? pr1.get(0).getNoteApproveNin() : "");
					}
				}
				NicTransaction txn = nicTransactionService.findById(soHoSo);
				String photoTreEm = "";
				List<InfoPerson> listFormat = new ArrayList<InfoPerson>();
				if(StringUtils.isNotEmpty(txn.getInfoPerson())){
					JAXBContext jaxbContext = JAXBContext.newInstance(InfoPersons.class);
					Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
					StringReader reader = new StringReader(txn.getInfoPerson());
					InfoPersons persons = (InfoPersons) unmarshaller.unmarshal(reader);
					List<InfoPerson> listPerson = null;
					String personId = "";
					if(persons != null){
						listPerson = persons.getInfoPersons();
						if(listPerson != null && listPerson.size() > 0){
							personId = listPerson.get(0).getId();
						}
					}
					listFormat = this.formatDatatoVN(listPerson);
					if(StringUtils.isNotEmpty(personId)){
						List<NicTransactionAttachment> photoList = nicTransactionAttachmentService.findFacialTEById (
								soHoSo, NicTransactionAttachment.DOC_TYPE_ENROLLED_SUB, personId);				
						if (CollectionUtils.isNotEmpty(photoList) && photoList.size() > 0) {
							String images = Base64.encodeBase64String(photoList.get(0).getDocData());
							photoTreEm = images;						
						}
						
					}				
				}
				model.addAttribute("photoTreEm", photoTreEm);
				model.addAttribute("dsTreEm", listFormat);
				model.addAttribute("slTreEm", listFormat.size() > 0 ? "Y" : "N");
				model.addAttribute("chiTietDS", pr1);
				model.addAttribute("photoStr", photoStr);
				model.addAttribute("soHoSo", soHoSo);
				//model.addAttribute("dsTreEm", new ArrayList<NicUploadJobDto>());
				ModelAndView modelAndView = new ModelAndView("investigation.investigation.process");
				modelAndView.addObject("formData", investigationAssignmentData);
				return modelAndView;
			}
			List<String> loadJob = new ArrayList<String>();
			for(int i = 0; i < investigationAssignmentData.getLoadJobIds().length; i++){
				if(!investigationAssignmentData.getLoadJobIds()[i].equals(investigationAssignmentData.getJobId())){
					jobId = investigationAssignmentData.getLoadJobIds()[i];
					break;
				}
			}
			for(int i = 0; i < investigationAssignmentData.getLoadJobIds().length; i++){
				if(!investigationAssignmentData.getLoadJobIds()[i].equals(jobId)){
					loadJob.add(investigationAssignmentData.getLoadJobIds()[i]);
				}
			}
			List<EppIdentification> listEntity = ListMatchingEppIdentification(Long.parseLong(jobId));
			EppIdentification entity = null;
 			if(listEntity != null && listEntity.size() > 0){
				entity = listEntity.get(0);
				Long idI = listEntity.get(0).getId();
     			//Lẩy ảnh đối tượng bị trùng
     			List<EppIdentificationAttachmnt> listI_Attach = nicIdentificationService.findAttachmentById(idI);
     			if(listI_Attach != null && listI_Attach.size() > 0){
     				for(EppIdentificationAttachmnt obj :listI_Attach){
     					if(obj.getName().equals("PHOTO")){
     						model.addAttribute("photoTrung", obj.getBase64());
     						break;
     					}
     				}
     			}
			}else{
				entity = new EppIdentification();
				model.addAttribute("khongTrung", "Y");
			}
 			NicUploadJob jobById = uploadJobService.findById(Long.parseLong(jobId));
 			InvestigationListInfoTargetDto matching = null;
 			if(jobById != null){
 				model.addAttribute("idMaster", jobById.getTransactionId());
 				matching = getInfoTransactionData(jobById.getTransactionId());
 				if(matching == null)
 					matching = new InvestigationListInfoTargetDto();
 			}
 			if(investigationAssignmentData.getStageLoad().equals("Y")){
				if(jobById.getDateApproveNin() != null){
					model.addAttribute("action", "N");
				}else{
					model.addAttribute("action", "Y");
					MatchingDataRequest(Long.parseLong(jobId), null, 3, null, null, userSession.getWorkstationId(),userSession.getUserName(), "");				
				}
			}else{
				MatchingDataRequest(Long.parseLong(jobId), null, 3, null, null, userSession.getWorkstationId(),userSession.getUserName(), "");				
				model.addAttribute("action", "Y");
			}
 			model.addAttribute("count", listJobId1.size());
			model.addAttribute("stt", listJobId1.size() - loadJob.size());
			model.addAttribute("stage", investigationAssignmentData.getStageLoad());
 			model.addAttribute("loadJob", loadJob);
 			model.addAttribute("jobCompare", jobId);	
			model.addAttribute("dsJob", listJobId1);
			model.addAttribute("fnSelected", Constants.HEADING_NIC_INVESTIGATION);
			ModelAndView mav = new ModelAndView("investigation.search.nin");	
			mav.addObject("entity_o", entity);
			mav.addObject("countTransaction", listEntity.size());
			mav.addObject("rostEntity", matching);
			return mav;				
			
		} catch (Exception e) {

			e.printStackTrace();
		}
		return new ModelAndView("investigation.search.nin");

	}
	
	@RequestMapping(value = "/saveCompareJob/{kind}")
	public ModelAndView saveCompareJob(
			@ModelAttribute(value = "formData") InvestigationAssignmentData investigationAssignmentData, @PathVariable String kind,
			WebRequest request, HttpServletRequest httpRequest, ModelMap model) {
		logger.info("NIC Investigation");
		logger.info("NIC Investigation");
		List<NicUploadJobDto> pr = null;
		try {		
			HttpSession session = httpRequest.getSession();
			UserSession userSession = (UserSession) session
					.getAttribute("userSession");
			logger.info("The Officer Id or User Id is ===============>>>>>>>>>>"
					+ userSession.getUserName());

			// Modified for pagination
			ParametersId id = new ParametersId();
			id.setParaName(Parameters.MAX_RECORDS_PER_PAGE);
			id.setParaScope(Parameters.SCOPE_SYSTEM);
			String tableId = "row";
			String sortedElement = "codeId";
			String order = InvestigationProcessController.DECENDING_ORDER;

			try {
				String sort = request
						.getParameter(new ParamEncoder(tableId)
								.encodeParameterName(TableTagParameters.PARAMETER_SORT));
				if (StringUtils.isNotBlank(sort))
					sortedElement = sort;
			} catch (Exception ex) {
				logMessage(ex);
			}

			try {
				String requestOrder = request
						.getParameter(new ParamEncoder(tableId)
								.encodeParameterName(TableTagParameters.PARAMETER_ORDER));
				if (StringUtils.isNotBlank(requestOrder))
					order = requestOrder;
			} catch (Exception ex) {
				logMessage(ex);
			}

			Order hibernateOrder = Order.desc(sortedElement);
			if (order.equalsIgnoreCase(InvestigationProcessController.DECENDING_ORDER)) {
				hibernateOrder = Order.asc(sortedElement);
			}
				
			httpRequest.setAttribute("pendingCount",
					uploadJobService.getPendingInvestigationsCount());

			if (investigationAssignmentData == null) {
				investigationAssignmentData = new InvestigationAssignmentData();
			}

			investigationAssignmentData.cleanUpStrings();
			boolean kQua = false;
			/*Khớp */
			String fullName = "";
			if(investigationAssignmentData.getSaveFullName() != null && investigationAssignmentData.getSaveFullName().length > 0){
				fullName = investigationAssignmentData.getSaveFullName()[0];
			}
			
			if(kind.equals("Y")){
				kQua = MatchingDataRequest(Long.parseLong(investigationAssignmentData.getJobId()), investigationAssignmentData.getProcessJobIds()[0], 1, investigationAssignmentData.getListName(), null, userSession.getWorkstationId(),userSession.getUserName(), fullName);				
			}
			
			if(kind.equals("N")){
				kQua = MatchingDataRequest(Long.parseLong(investigationAssignmentData.getJobId()), null, 1, investigationAssignmentData.getListName(), null, userSession.getWorkstationId(),userSession.getUserName(), fullName);				
			}
			if(!kQua){
				ModelAndView view = new ModelAndView("investigation.compare.info");
				view.addObject("loiKhop", "Yêu cầu thực hiện không thành công.");
				return view;
			}
			//end
			if(investigationAssignmentData.getLoadJobIds() == null){
				Long[] listJobId = new Long[investigationAssignmentData.getSelectedJobIds().length];
				
				if(investigationAssignmentData.getSelectedJobIds() != null && investigationAssignmentData.getSelectedJobIds().length > 0){
					for(int i = 0; i < investigationAssignmentData.getSelectedJobIds().length; i++){
						//Tạm đóng nhớ mở lại
						//uploadJobService.updateOfficerIdByJobId(investigationAssignmentData.getSelectedJobIds()[i], userSession.getUserId());					
						listJobId[i] = Long.parseLong(investigationAssignmentData.getSelectedJobIds()[i]);
						
					}
				}
				pr = uploadJobService.findInvestigationProcessByJobId(listJobId, null, new AssignmentFilterAll());
				NicUploadJob jobFocus = uploadJobService.findById(Long.parseLong(investigationAssignmentData.getJobId()));
				String photoStr = "";
				String soHoSo = "";
				if(pr == null || pr.size() == 0){
					pr = new ArrayList<NicUploadJobDto>();
				}else{
					//for(NicUploadJobDto dto : pr){
						List<NicTransactionAttachment> photoList = nicTransactionAttachmentService.findNicTransactionAttachments(
								pr.get(0).getTransactionId(), NicTransactionAttachment.DOC_TYPE_PHOTO_CAPTURE, NicTransactionAttachment.DEFAULT_SERIAL_NO).getListModel();				
						if (CollectionUtils.isNotEmpty(photoList) && photoList.size() > 0) {
							//String images = Base64.encodeBase64String(photoList.get(0).getDocData());
							photoStr = Base64.encodeBase64String(photoList.get(0).getDocData());
							//dto.setPhotoStr(images);
						}
						//else{
							//dto.setPhotoStr("");
						//}
						soHoSo = jobFocus.getTransactionId();
						model.addAttribute("noteAprovePerson", jobFocus.getNoteApprovePerson() != null ? jobFocus.getNoteApprovePerson() : "");
						model.addAttribute("noteAproveObj", jobFocus.getNoteApproveObj() != null ? jobFocus.getNoteApproveObj() : "");
						model.addAttribute("noteAproveNin", jobFocus.getNoteApproveNin() != null ? jobFocus.getNoteApproveNin() : "");
						//model.addAttribute("noteAprove", jobFocus.getNoteApprove() != null ? jobFocus.getNoteApprove() : "");
						//break;
					//}
				}
				NicTransaction txn = nicTransactionService.findById(soHoSo);
				String photoTreEm = "";
				List<InfoPerson> listFormat = new ArrayList<InfoPerson>();
				if(StringUtils.isNotEmpty(txn.getInfoPerson())){
					JAXBContext jaxbContext = JAXBContext.newInstance(InfoPersons.class);
					Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
					StringReader reader = new StringReader(txn.getInfoPerson());
					InfoPersons persons = (InfoPersons) unmarshaller.unmarshal(reader);
					List<InfoPerson> listPerson = null;
					String personId = "";
					if(persons != null){
						listPerson = persons.getInfoPersons();
						if(listPerson != null && listPerson.size() > 0){
							personId = listPerson.get(0).getId();
						}
					}
					listFormat = this.formatDatatoVN(listPerson);
					if(StringUtils.isNotEmpty(personId)){
						List<NicTransactionAttachment> photoList = nicTransactionAttachmentService.findFacialTEById (
								soHoSo, NicTransactionAttachment.DOC_TYPE_ENROLLED_SUB, personId);				
						if (CollectionUtils.isNotEmpty(photoList) && photoList.size() > 0) {
							String images = Base64.encodeBase64String(photoList.get(0).getDocData());
							photoTreEm = images;						
						}
						
					}				
				}
				model.addAttribute("photoTreEm", photoTreEm);
				model.addAttribute("dsTreEm", listFormat);
				model.addAttribute("slTreEm", listFormat.size() > 0 ? "Y" : "N");
				model.addAttribute("chiTietDS", pr);
				model.addAttribute("photoStr", photoStr);
				model.addAttribute("soHoSo", soHoSo);
				//model.addAttribute("dsTreEm", new ArrayList<NicUploadJobDto>());
				ModelAndView modelAndView = new ModelAndView("investigation.investigation.process");
				modelAndView.addObject("formData", investigationAssignmentData);
				return modelAndView;
			}
			
			return nextTransaction(investigationAssignmentData, model);
		} catch (Exception e) {

			e.printStackTrace();
			System.out.println(e.getMessage());
		}

		return null;
	}
	
	
	@ResponseBody
	@RequestMapping(value="/testLostId")
	public String testLostId(@RequestParam String transactionId, ModelMap model, HttpServletRequest request){
		try {
			NicDocumentData nicData = documentDataService.getDocumentDataById(transactionId).getModel();
			if(nicData != null && nicData.getStatus().equals(RegistrationConstants.TRANSACTION_STAGE_ISSUANCE)){
				return "Y";
			}			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "N";
	}
	
//	@ResponseBody
//	@RequestMapping(value="/successPassport")
//	public String successPassport(@RequestBody String yourArray, ModelMap model, HttpServletRequest request){
//		JSONObject onb = new JSONObject(yourArray);
//		HttpSession session = request.getSession();
//		UserSession userSession = (UserSession) session.getAttribute("userSession");
//		try {
//			String tranId = (onb.has("tranid") && !onb.isNull("tranid")) ? onb.getString("tranid") : "";
//			String name = (onb.has("name") && !onb.isNull("name")) ? onb.getString("name") : "";
//			String dob = (onb.has("dob") && !onb.isNull("dob")) ? onb.getString("dob") : "";
//			String nin = (onb.has("nin") && !onb.isNull("nin")) ? onb.getString("nin") : "";
//			String ppno = (onb.has("ppno") && !onb.isNull("ppno")) ? onb.getString("ppno") : "";
//			String datepp = (onb.has("datepp") && !onb.isNull("datepp")) ? onb.getString("datepp") : "";
//			String datepp1 = (onb.has("datepp1") && !onb.isNull("datepp1")) ? onb.getString("datepp1") : "";
//			String placepp = (onb.has("placepp") && !onb.isNull("placepp")) ? onb.getString("placepp") : "";
//			//String datelost = (onb.has("datelost") && !onb.isNull("datelost")) ? onb.getString("datelost") : "";			
//			NicDocumentData nicData = documentDataService.getDocumentDataById(tranId).getModel();
//			if(nicData != null){
//				nicData.setStatus("NONE");
//				nicData.setActiveFlag(false);
//				nicData.setUpdateDatetime(Calendar.getInstance().getTime());
//				nicData.setUpdateBy(userSession.getUserId());
//				documentDataService.saveOrUpdate(nicData);
//			}
//			//Thêm bản ghi vào db
//			NicTransactionLost lost = new NicTransactionLost();
//			lost.setTransactionId(tranId);
//			lost.setName(name);
//			lost.setNin(nin);
//			lost.setDob(HelperClass.convertStringToDate2(dob));
//			lost.setPassportNo(ppno);
//			lost.setDateIssue(HelperClass.convertStringToDate2(datepp));
//			lost.setDateExrity(HelperClass.convertStringToDate2(datepp1));
//			lost.setPlaceIssue(placepp);
//			lost.setCreateDate(Calendar.getInstance().getTime());
//			lost.setCreateBy(userSession.getUserId());
//			lostService.saveOrUpdateLost(lost);
//			return "Y";
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		return "N";
//	}
	
	@ResponseBody
	@RequestMapping(value="/distroyTransaction")
	public ModelAndView distroyTransaction(@RequestParam String transactionId,
			WebRequest request, HttpServletRequest httpRequest, ModelMap model) {
			ModelAndView mav = new ModelAndView("comfirm-lost");
			NicDocumentData nicData = documentDataService.getDocumentDataById(transactionId).getModel();
			if(nicData != null && nicData.getStatus().equals(RegistrationConstants.TRANSACTION_STAGE_ISSUANCE)){				
				ConfirmLostDto dto = new ConfirmLostDto();
				NicRegistrationData regData = regDataService.findById(transactionId);
				NicTransaction txn = nicTransactionService.findById(transactionId);
				if(regData != null){
					dto.setHoTen(HelperClass.createFullName(regData.getSurnameFull(), regData.getMiddlenameFull(), regData.getFirstnameFull()));
					String date = HelperClass.convertDateToString2(regData.getDateOfBirth());
					dto.setNgaySinh(HelperClass.loadDateOfBirth(date, regData.getDefDateOfBirth()));
					
				}
				if(txn != null){
					dto.setCmnd(txn.getNin());						
				}
				dto.setNgayCapHC(HelperClass.convertDateToString2(nicData.getDateOfIssue()));
				dto.setSoHC(nicData.getId().getPassportNo());
				dto.setHanHC(HelperClass.convertDateToString2(nicData.getDateOfExpiry()));
				List<NicTransactionAttachment> photoList = nicTransactionAttachmentService.findNicTransactionAttachments(
						transactionId, NicTransactionAttachment.DOC_TYPE_PHOTO_CAPTURE, NicTransactionAttachment.DEFAULT_SERIAL_NO).getListModel();				
				if (CollectionUtils.isNotEmpty(photoList) && photoList.size() > 0) {
					String images = Base64.encodeBase64String(photoList.get(0).getDocData());
					dto.setAnhMat(images);
				}else{
					dto.setAnhMat("");
				}
				dto.setSoLuongHS(1);
				mav.addObject("root", dto);
			}
		return mav;
	}
	
	@RequestMapping(value = "/saveEqualJobObject/{kind}")
	public ModelAndView saveEqualJob(
			@ModelAttribute(value = "formData") InvestigationAssignmentData investigationAssignmentData, @PathVariable String kind,
			WebRequest request, HttpServletRequest httpRequest, ModelMap model) {
		logger.info("NIC Investigation");
		logger.info("NIC Investigation");
		List<NicUploadJobDto> pr = null;
		try {		
			HttpSession session = httpRequest.getSession();
			UserSession userSession = (UserSession) session
					.getAttribute("userSession");
			logger.info("The Officer Id or User Id is ===============>>>>>>>>>>"
					+ userSession.getUserName());

			// Modified for pagination
			ParametersId id = new ParametersId();
			id.setParaName(Parameters.MAX_RECORDS_PER_PAGE);
			id.setParaScope(Parameters.SCOPE_SYSTEM);
			String tableId = "row";
			String sortedElement = "codeId";
			String order = InvestigationProcessController.DECENDING_ORDER;

			try {
				String sort = request
						.getParameter(new ParamEncoder(tableId)
								.encodeParameterName(TableTagParameters.PARAMETER_SORT));
				if (StringUtils.isNotBlank(sort))
					sortedElement = sort;
			} catch (Exception ex) {
				logMessage(ex);
			}

			try {
				String requestOrder = request
						.getParameter(new ParamEncoder(tableId)
								.encodeParameterName(TableTagParameters.PARAMETER_ORDER));
				if (StringUtils.isNotBlank(requestOrder))
					order = requestOrder;
			} catch (Exception ex) {
				logMessage(ex);
			}

			Order hibernateOrder = Order.desc(sortedElement);
			if (order.equalsIgnoreCase(InvestigationProcessController.DECENDING_ORDER)) {
				hibernateOrder = Order.asc(sortedElement);
			}
				
			httpRequest.setAttribute("pendingCount",
					uploadJobService.getPendingInvestigationsCount());

			if (investigationAssignmentData == null) {
				investigationAssignmentData = new InvestigationAssignmentData();
			}

			investigationAssignmentData.cleanUpStrings();
			boolean kQua = false;
			/*Khớp */
			String fullName = "";
			if(investigationAssignmentData.getSaveFullName() != null && investigationAssignmentData.getSaveFullName().length > 0){
				fullName = investigationAssignmentData.getSaveFullName()[0];
			}
			if(kind.equals("Y")){
				kQua = MatchingDataRequest(Long.parseLong(investigationAssignmentData.getJobId()), null, 2, investigationAssignmentData.getListName(), Long.parseLong(investigationAssignmentData.getProcessJobIds()[0]), userSession.getWorkstationId(),userSession.getUserName(), fullName);				
			}
			
			if(kind.equals("N")){
				kQua = MatchingDataRequest(Long.parseLong(investigationAssignmentData.getJobId()), null, 2, investigationAssignmentData.getListName(), null, userSession.getWorkstationId(),userSession.getUserName(), fullName);				
			}
			if(!kQua){
				ModelAndView view = new ModelAndView("investigation.compare.info");
				view.addObject("loiKhop", "Yêu cầu thực hiện không thành công.");
				return view;
			}
			//end
			if(investigationAssignmentData.getLoadJobIds() == null){
				Long[] listJobId = new Long[investigationAssignmentData.getSelectedJobIds().length];
				
				if(investigationAssignmentData.getSelectedJobIds() != null && investigationAssignmentData.getSelectedJobIds().length > 0){
					for(int i = 0; i < investigationAssignmentData.getSelectedJobIds().length; i++){
						//Tạm đóng nhớ mở lại
						//uploadJobService.updateOfficerIdByJobId(investigationAssignmentData.getSelectedJobIds()[i], userSession.getUserId());					
						listJobId[i] = Long.parseLong(investigationAssignmentData.getSelectedJobIds()[i]);
						
					}
				}
				pr = uploadJobService.findInvestigationProcessByJobId(listJobId, null, new AssignmentFilterAll());
				NicUploadJob jobFocus = uploadJobService.findById(Long.parseLong(investigationAssignmentData.getJobId()));
				String photoStr = "";
				String soHoSo = "";
				if(pr == null || pr.size() == 0){
					pr = new ArrayList<NicUploadJobDto>();
				}else{
					//for(NicUploadJobDto dto : pr){
						List<NicTransactionAttachment> photoList = nicTransactionAttachmentService.findNicTransactionAttachments(
								jobFocus.getTransactionId(), NicTransactionAttachment.DOC_TYPE_PHOTO_CAPTURE, NicTransactionAttachment.DEFAULT_SERIAL_NO).getListModel();				
						if (CollectionUtils.isNotEmpty(photoList) && photoList.size() > 0) {
							//String images = Base64.encodeBase64String(photoList.get(0).getDocData());
							photoStr = Base64.encodeBase64String(photoList.get(0).getDocData());
							//dto.setPhotoStr(images);
						}
						//else{
							//dto.setPhotoStr("");
						//}
						soHoSo = jobFocus.getTransactionId();
						model.addAttribute("noteAprovePerson", jobFocus.getNoteApprovePerson() != null ? jobFocus.getNoteApprovePerson() : "");
						model.addAttribute("noteAproveObj", jobFocus.getNoteApproveObj() != null ? jobFocus.getNoteApproveObj() : "");
						model.addAttribute("noteAproveNin", jobFocus.getNoteApproveNin() != null ? jobFocus.getNoteApproveNin() : "");
						//model.addAttribute("noteAprove", jobFocus.getNoteApprove() != null ? jobFocus.getNoteApprove() : "");
						//break;
					//}
				}
				NicTransaction txn = nicTransactionService.findById(soHoSo);
				String photoTreEm = "";
				List<InfoPerson> listFormat = new ArrayList<InfoPerson>();
				if(StringUtils.isNotEmpty(txn.getInfoPerson())){
					JAXBContext jaxbContext = JAXBContext.newInstance(InfoPersons.class);
					Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
					StringReader reader = new StringReader(txn.getInfoPerson());
					InfoPersons persons = (InfoPersons) unmarshaller.unmarshal(reader);
					List<InfoPerson> listPerson = null;
					String personId = "";
					if(persons != null){
						listPerson = persons.getInfoPersons();
						if(listPerson != null && listPerson.size() > 0){
							personId = listPerson.get(0).getId();
						}
					}
					listFormat = this.formatDatatoVN(listPerson);
					if(StringUtils.isNotEmpty(personId)){
						List<NicTransactionAttachment> photoList = nicTransactionAttachmentService.findFacialTEById (
								soHoSo, NicTransactionAttachment.DOC_TYPE_ENROLLED_SUB, personId);				
						if (CollectionUtils.isNotEmpty(photoList) && photoList.size() > 0) {
							String images = Base64.encodeBase64String(photoList.get(0).getDocData());
							photoTreEm = images;						
						}
						
					}				
				}
				model.addAttribute("photoTreEm", photoTreEm);
				model.addAttribute("dsTreEm", listFormat);
				model.addAttribute("slTreEm", listFormat.size() > 0 ? "Y" : "N");
				model.addAttribute("chiTietDS", pr);
				model.addAttribute("photoStr", photoStr);
				model.addAttribute("soHoSo", soHoSo);
				//model.addAttribute("dsTreEm", new ArrayList<NicUploadJobDto>());
				ModelAndView modelAndView = new ModelAndView("investigation.investigation.process");
				modelAndView.addObject("formData", investigationAssignmentData);
				return modelAndView;
			}
			
			return nextObject(investigationAssignmentData, model);
		} catch (Exception e) {

			e.printStackTrace();
			System.out.println(e.getMessage());
		}

		return null;
	}
	
	@ResponseBody
	@RequestMapping(value="/getInfoTransactionByNin/{blackId}")
	public EppBlacklist getInfoTransactionByNin(@PathVariable String blackId,
			WebRequest request, HttpServletRequest httpRequest, ModelMap model) {
		EppBlacklist blacklist = null;
		try {
			blacklist = blacklistService.findById(Long.parseLong(blackId));
			if(blacklist == null)
				blacklist = new EppBlacklist();
			else{
				Long idBb = blacklist.getId();
     			//Lẩy ảnh đối tượng bị trùng
     			List<EppBlacklistAttachment> listBB_Attach = blacklistService.findAttachmentById(idBb);
     			if(listBB_Attach != null && listBB_Attach.size() > 0){
     				for(EppBlacklistAttachment obj :listBB_Attach){
     					if(obj.getType_().equals("PHOTO")){
     						blacklist.setPhoto(obj.getBase64());
     						break;
     					}
     				}
     			}
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		return blacklist;
	}
	
	@RequestMapping(value = "/saveProcess")
	public ModelAndView saveProcess(@ModelAttribute(value = "formData") InvestigationAssignmentData investigationAssignmentData,
			WebRequest request, HttpServletRequest httpRequest, ModelMap model){
		try {
			HttpSession session = httpRequest.getSession();
			UserSession userSession = (UserSession) session
					.getAttribute("userSession");
			List<Long> listJob = new ArrayList<Long>();
			for(int i = 0; i < investigationAssignmentData.getSelectedJobIds().length; i++){
				//Kiểm tra trạng thái 3 trường dữ liệu khớp có null không 
				NicUploadJob nicU = uploadJobService.findById(Long.valueOf(investigationAssignmentData.getSelectedJobIds()[i]));
				if(nicU != null){
					if(nicU.getDateApproveInvestigation() != null && nicU.getDateApproveNin() != null && nicU.getDateApprovePerson() != null && nicU.getInvestigationStatus().equals(NicUploadJob.RECORD_STATUS_IN_PROGRESS)){
						uploadJobService.approveJobStatus_Confirmed(
								Long.valueOf(investigationAssignmentData.getSelectedJobIds()[i]),
								"SYSTEM", "SYSTEM-MACHINE",
								NicTransactionLog.TRANSACTION_STAGE_INVESTIGATION,
								NicTransactionLog.TRANSACTION_STATUS_NIC_CONFIRMED,
								NicUploadJob.RECORD_STATUS_APPROVED);
					}else{
						listJob.add(Long.valueOf(investigationAssignmentData.getSelectedJobIds()[i]));
					}
				}			
			}
			if(listJob.size() > 0){
				Long[] arrJob = new Long[listJob.size()];
				for(int i = 0; i < listJob.size(); i++){
					arrJob[i] = listJob.get(i);
				}
				List<NicUploadJobDto> pr = uploadJobService.findInvestigationProcessByJobId(arrJob, null, new AssignmentFilterAll());
				NicUploadJob jobFocus = null;
				if(StringUtils.isNotEmpty(investigationAssignmentData.getJobId())){
					jobFocus = uploadJobService.findById(Long.parseLong(investigationAssignmentData.getJobId()));
				}
				String photoStr = "";
				String soHoSo = "";
				if(pr == null || pr.size() == 0){
					pr = new ArrayList<NicUploadJobDto>();
				}else{
					if(jobFocus != null){
						List<NicTransactionAttachment> photoList = nicTransactionAttachmentService.findNicTransactionAttachments(
								jobFocus.getTransactionId(), NicTransactionAttachment.DOC_TYPE_PHOTO_CAPTURE, NicTransactionAttachment.DEFAULT_SERIAL_NO).getListModel();				
						if (CollectionUtils.isNotEmpty(photoList) && photoList.size() > 0) {
							//String images = Base64.encodeBase64String(photoList.get(0).getDocData());
							photoStr = Base64.encodeBase64String(photoList.get(0).getDocData());
							//dto.setPhotoStr(images);
						}
						soHoSo = jobFocus.getTransactionId();
						//model.addAttribute("noteAprove", jobFocus.getNoteApprove() != null ? jobFocus.getNoteApprove() : "");
						model.addAttribute("noteAprovePerson", jobFocus.getNoteApprovePerson() != null ? jobFocus.getNoteApprovePerson() : "");
						model.addAttribute("noteAproveObj", jobFocus.getNoteApproveObj() != null ? jobFocus.getNoteApproveObj() : "");
						model.addAttribute("noteAproveNin", jobFocus.getNoteApproveNin() != null ? jobFocus.getNoteApproveNin() : "");
					}else{
						List<NicTransactionAttachment> photoList = nicTransactionAttachmentService.findNicTransactionAttachments(
								pr.get(0).getTransactionId(), NicTransactionAttachment.DOC_TYPE_PHOTO_CAPTURE, NicTransactionAttachment.DEFAULT_SERIAL_NO).getListModel();				
						if (CollectionUtils.isNotEmpty(photoList) && photoList.size() > 0) {
							//String images = Base64.encodeBase64String(photoList.get(0).getDocData());
							photoStr = Base64.encodeBase64String(photoList.get(0).getDocData());
							//dto.setPhotoStr(images);
						}
						soHoSo = pr.get(0).getTransactionId();
						//model.addAttribute("noteAprove", pr.get(0).getNoteApprove());
						model.addAttribute("noteAprovePerson", pr.get(0).getNoteApprovePerson() != null ? pr.get(0).getNoteApprovePerson() : "");
						model.addAttribute("noteAproveObj", pr.get(0).getNoteApproveObj() != null ? pr.get(0).getNoteApproveObj() : "");
						model.addAttribute("noteAproveNin", pr.get(0).getNoteApproveNin() != null ? pr.get(0).getNoteApproveNin() : "");
					}
				}
				NicTransaction txn = nicTransactionService.findById(soHoSo);
				String photoTreEm = "";
				List<InfoPerson> listFormat = new ArrayList<InfoPerson>();
				if(StringUtils.isNotEmpty(txn.getInfoPerson())){
					JAXBContext jaxbContext = JAXBContext.newInstance(InfoPersons.class);
					Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
					StringReader reader = new StringReader(txn.getInfoPerson());
					InfoPersons persons = (InfoPersons) unmarshaller.unmarshal(reader);
					List<InfoPerson> listPerson = null;
					String personId = "";
					if(persons != null){
						listPerson = persons.getInfoPersons();
						if(listPerson != null && listPerson.size() > 0){
							personId = listPerson.get(0).getId();
						}
					}
					listFormat = this.formatDatatoVN(listPerson);
					if(StringUtils.isNotEmpty(personId)){
						List<NicTransactionAttachment> photoList = nicTransactionAttachmentService.findFacialTEById (
								soHoSo, NicTransactionAttachment.DOC_TYPE_ENROLLED_SUB, personId);				
						if (CollectionUtils.isNotEmpty(photoList) && photoList.size() > 0) {
							String images = Base64.encodeBase64String(photoList.get(0).getDocData());
							photoTreEm = images;						
						}
						
					}				
				}
				model.addAttribute("photoTreEm", photoTreEm);
				model.addAttribute("dsTreEm", listFormat);
				model.addAttribute("slTreEm", listFormat.size() > 0 ? "Y" : "N");
				//investigationAssignmentData.setAssignmentStatus(stage);
				model.addAttribute("stage", investigationAssignmentData.getStageLoad());
				model.addAttribute("chiTietDS", pr);
				model.addAttribute("photoStr", photoStr);
				model.addAttribute("soHoSo", soHoSo);
				//model.addAttribute("dsTreEm", new ArrayList<NicUploadJobDto>());
				ModelAndView modelAndView = new ModelAndView("investigation.investigation.process");
				modelAndView.addObject("formData", investigationAssignmentData);
				return modelAndView;				
			}
			
			PaginatedResult<NicUploadJobDto> pr = null;
			int pageNo = !StringUtils.isEmpty(request.getParameter("pageNo")) ? Integer.parseInt(request.getParameter("pageNo")) : 1;
			int pageSize = Constants.PAGE_SIZE_DEFAULT;
			

			investigationAssignmentData.cleanUpStrings();
			
			//update lại officerID
//			if(investigationAssignmentData.getSelectedJobIds() != null && investigationAssignmentData.getSelectedJobIds().length > 0){
//				for(int i = 0; i < investigationAssignmentData.getSelectedJobIds().length; i++){
//					uploadJobService.updateOfficerIdByJobId(investigationAssignmentData.getSelectedJobIds()[i], null);										
//				}
//			}
			if(StringUtils.isEmpty(investigationAssignmentData.getTypeList())){
				investigationAssignmentData.setTypeList("2");
			}
			pr = uploadJobService
					.findAllForInvestigationPagination1(
							new String[] { NicUploadJob.RECORD_STATUS_IN_PROGRESS },
							null, userSession.getUserId() ,
							pageNo,
							pageSize,
							investigationAssignmentData.assignedOnly(),
							new AssignmentFilterAll(investigationAssignmentData
									.getSearchTransactionId(),
									investigationAssignmentData.getPriority(),
									investigationAssignmentData
											.getTransactionType(), null, null,
									investigationAssignmentData
											.getRegSiteCode(),
									investigationAssignmentData
											.getPassportType(),
									investigationAssignmentData
											.getTypeList(), investigationAssignmentData.getPackageCode()));
			List<NicUploadJobDto> list = new ArrayList<NicUploadJobDto>();
			if (pr != null) {
				list = pr.getRows();
				model.addAttribute("total", pr.getTotal());
				if (list != null) {
					for (NicUploadJobDto record : list) {
						if(StringUtils.isEmpty(record.getInvestigationOfficerId())){
							record.setFlagOfficerId("0");
						}else {
							if(record.getInvestigationOfficerId().equals(userSession.getUserId())){
								record.setFlagOfficerId("0");
							}else{
								record.setFlagOfficerId("1");								
							}
						}
						String transactionId = record.getTransactionId();
						if (transactionId != null) {
							NicTransaction nicTransaction = nicTransactionService.findById(transactionId);
							if (nicTransaction != null) {
//								record.setDateOfApplication(nicTransaction
//										.getDateOfApplication());
//								record.setEstDateOfCollection(nicTransaction
//										.getEstDateOfCollection());
								record.setPassportType(nicTransaction
										.getPassportType());
								record.setRegSiteCode(nicTransaction.getRegSiteCode());
								record.setPackageId(nicTransaction.getPackageId());
								record.setNin(nicTransaction.getNin());
								// record.setPriority(nicTransaction.getPriority());
								{
//									try {
//										//CodeValues codeValue = codesService.getCodeValueByIdName(Codes.PRIORITY,nicTransaction.getPriority().toString());
//										String priority = codesService.getCodeValueDescByIdName(RegistrationConstants.CODE_PRIORITY_CODE_ID, nicTransaction.getPriority().toString(), "");
//										if (priority != null) {
//											record.setPriorityString(priority);
//										} else {
//											record.setPriorityString("");
//										}
//									} catch (Exception e) {
//										record.setPriorityString(nicTransaction
//												.getPriority() == null ? null
//												: nicTransaction.getPriority()
//														.toString());
//									}
								}
								NicRegistrationData reg = regDataService.findById(transactionId);
								if(reg != null){
									record.setFullName(HelperClass.createFullName(reg.getSurnameFull(), reg.getMiddlenameFull(), reg.getFirstnameFull()));
									record.setDob(HelperClass.convertDateToString2(reg.getDateOfBirth()));
									if(!StringUtils.isEmpty(reg.getGender())){
										record.setGender(reg.getGender().equals("M") ? "Nam" : "Nữ");
									}
									record.setPlaceOfBirth(reg.getPlaceOfBirth());
								}
							}
							
						}
					}
				} else {
					request.setAttribute("jobDetailsErrorMsg", "Không tìm thấy dữ liệu",
							0);
				}
				Map searchResultMap = new HashMap();
				searchResultMap.put("totalRecords", pr.getTotal());
				//searchResultMap.put("pageSize", pageSize);

				//model.addAttribute("jobList", list);
				//phúc edit
				int firstResults = (pageNo - 1) < 0 ? 0 : (pageNo - 1) * pageSize;
				model.addAttribute("pageSize", pageSize);
				model.addAttribute("pageNo", pageNo);
				model.addAttribute("totalPage", pagingUtil.totalPage(pr.getTotal(), pageSize));
				model.addAttribute("startIndex", pr.getTotal() != 0 ? firstResults + 1 : 0);
				model.addAttribute("totalRecord", pr.getTotal());
				model.addAttribute("endIndex", firstResults + pr.getRowSize());
				model.addAttribute("dsXuLyA", list);
				//end
//				model.addAttribute("fnSelected",
//						Constants.HEADING_NIC_INVESTIGATION);
				return new ModelAndView("investigation.investigation",
						searchResultMap);
			} else {
//				model.addAttribute("fnSelected",
//						Constants.HEADING_NIC_INVESTIGATION);
				return new ModelAndView("investigation.investigation", null);
			}
			// }
		} catch (Exception e) {

			e.printStackTrace();
			return null;
		}
	}
	
	@RequestMapping(value = "/nextInfoByJobID")
	public ModelAndView nextInfoByJobID(
			@ModelAttribute(value = "formData") InvestigationAssignmentData investigationAssignmentData,
			WebRequest request, HttpServletRequest httpRequest, ModelMap model) {
		
		logger.info("NIC Investigation");
		PaginatedResult<NicUploadJobDto> pr = null;
		//PaginatedResult<NicUploadJobDto> rejectedList = null;
		try {
			//int pageNo = !StringUtils.isEmpty(request.getParameter("pageNo")) ? Integer.parseInt(request.getParameter("pageNo")) : 1;
			// pageSize = 20;
			//int startIndex = 0;
			HttpSession session = httpRequest.getSession();
			UserSession userSession = (UserSession) session
					.getAttribute("userSession");
			logger.info("The Officer Id or User Id is ===============>>>>>>>>>>"
					+ userSession.getUserName());

			// Modified for pagination
			ParametersId id = new ParametersId();
			id.setParaName(Parameters.MAX_RECORDS_PER_PAGE);
			id.setParaScope(Parameters.SCOPE_SYSTEM);
			String tableId = "row";
			String sortedElement = "codeId";
			String order = InvestigationProcessController.DECENDING_ORDER;

			try {
				String sort = request
						.getParameter(new ParamEncoder(tableId)
								.encodeParameterName(TableTagParameters.PARAMETER_SORT));
				if (StringUtils.isNotBlank(sort))
					sortedElement = sort;
			} catch (Exception ex) {
				logMessage(ex);
			}

			try {
				String requestOrder = request
						.getParameter(new ParamEncoder(tableId)
								.encodeParameterName(TableTagParameters.PARAMETER_ORDER));
				if (StringUtils.isNotBlank(requestOrder))
					order = requestOrder;
			} catch (Exception ex) {
				logMessage(ex);
			}

			Order hibernateOrder = Order.desc(sortedElement);
			if (order.equalsIgnoreCase(InvestigationProcessController.DECENDING_ORDER)) {
				hibernateOrder = Order.asc(sortedElement);
			}

			//Parameters parameter = parametersService.findById(id);

//			if (parameter != null) {
//				String pageSizeDb = parameter.getParaShortValue();
//
//				if (StringUtils.isNotBlank(pageSizeDb)
//						&& StringUtils.isNumeric(pageSizeDb)) {
//					pageSize = Integer.parseInt(pageSizeDb);
//				}
//			}
//			pageSize = Constants.PAGE_SIZE_DEFAULT;
//			String pageNumber = request.getParameter((new ParamEncoder(tableId)
//					.encodeParameterName(TableTagParameters.PARAMETER_PAGE)));
//
//			if (StringUtils.isNotBlank(pageNumber)
//					&& StringUtils.isNumeric(pageNumber)) {
//				pageNo = Integer.parseInt(pageNumber);
//			}
			httpRequest.setAttribute("pendingCount",
					uploadJobService.getPendingInvestigationsCount());

			if (investigationAssignmentData == null) {
				investigationAssignmentData = new InvestigationAssignmentData();
			}
			String jobId= "";
			investigationAssignmentData.cleanUpStrings();
			List<String> listJobId1 = new ArrayList<String>(); //Lưu thông tin jobId để load lại khi hoàn thành khớp hs
			//List<String> loadJob = new ArrayList<String>();
			for(int i = 0; i < investigationAssignmentData.getSelectedJobIds().length; i++){
				listJobId1.add(investigationAssignmentData.getSelectedJobIds()[i]);	
				/*if(!investigationAssignmentData.getSelectedJobIds()[i].equals(investigationAssignmentData.getProcessJobIds()[0])){
					loadJob.add(investigationAssignmentData.getSelectedJobIds()[i]);
				}*/
			}
			if(investigationAssignmentData.getLoadJobIds() == null){
				Long[] listJobId = new Long[investigationAssignmentData.getSelectedJobIds().length];
				
				if(investigationAssignmentData.getSelectedJobIds() != null && investigationAssignmentData.getSelectedJobIds().length > 0){
					for(int i = 0; i < investigationAssignmentData.getSelectedJobIds().length; i++){
						//Mở lại rồi nhé
						uploadJobService.updateOfficerIdByJobId(investigationAssignmentData.getSelectedJobIds()[i], userSession.getUserId());					
						listJobId[i] = Long.parseLong(investigationAssignmentData.getSelectedJobIds()[i]);
						
					}
				}
				List<NicUploadJobDto> pr1 = uploadJobService.findInvestigationProcessByJobId(listJobId, null, new AssignmentFilterAll());
				NicUploadJob jobFocus = null;
				if(StringUtils.isNotEmpty(investigationAssignmentData.getJobId())){
					jobFocus = uploadJobService.findById(Long.parseLong(investigationAssignmentData.getJobId()));
				}
				String photoStr = "";
				String soHoSo = "";
				if(pr1 == null || pr1.size() == 0){
					pr1 = new ArrayList<NicUploadJobDto>();
				}else{
					if(jobFocus != null){
						List<NicTransactionAttachment> photoList = nicTransactionAttachmentService.findNicTransactionAttachments(
								jobFocus.getTransactionId(), NicTransactionAttachment.DOC_TYPE_PHOTO_CAPTURE, NicTransactionAttachment.DEFAULT_SERIAL_NO).getListModel();				
						if (CollectionUtils.isNotEmpty(photoList) && photoList.size() > 0) {
							//String images = Base64.encodeBase64String(photoList.get(0).getDocData());
							photoStr = Base64.encodeBase64String(photoList.get(0).getDocData());
							//dto.setPhotoStr(images);
						}
						soHoSo = jobFocus.getTransactionId();
						//model.addAttribute("noteAprove", jobFocus.getNoteApprove() != null ? jobFocus.getNoteApprove() : "");
						model.addAttribute("noteAprovePerson", jobFocus.getNoteApprovePerson() != null ? jobFocus.getNoteApprovePerson() : "");
						model.addAttribute("noteAproveObj", jobFocus.getNoteApproveObj() != null ? jobFocus.getNoteApproveObj() : "");
						model.addAttribute("noteAproveNin", jobFocus.getNoteApproveNin() != null ? jobFocus.getNoteApproveNin() : "");
					}else{
						List<NicTransactionAttachment> photoList = nicTransactionAttachmentService.findNicTransactionAttachments(
								pr1.get(0).getTransactionId(), NicTransactionAttachment.DOC_TYPE_PHOTO_CAPTURE, NicTransactionAttachment.DEFAULT_SERIAL_NO).getListModel();				
						if (CollectionUtils.isNotEmpty(photoList) && photoList.size() > 0) {
							//String images = Base64.encodeBase64String(photoList.get(0).getDocData());
							photoStr = Base64.encodeBase64String(photoList.get(0).getDocData());
							//dto.setPhotoStr(images);
						}
						soHoSo = pr1.get(0).getTransactionId();
						//model.addAttribute("noteAprove", pr1.get(0).getNoteApprove());
						model.addAttribute("noteAprovePerson", pr1.get(0).getNoteApprovePerson() != null ? pr1.get(0).getNoteApprovePerson() : "");
						model.addAttribute("noteAproveObj", pr1.get(0).getNoteApproveObj() != null ? pr1.get(0).getNoteApproveObj() : "");
						model.addAttribute("noteAproveNin", pr1.get(0).getNoteApproveNin() != null ? pr1.get(0).getNoteApproveNin() : "");
					}
				}
				NicTransaction txn = nicTransactionService.findById(soHoSo);
				String photoTreEm = "";
				List<InfoPerson> listFormat = new ArrayList<InfoPerson>();
				if(StringUtils.isNotEmpty(txn.getInfoPerson())){
					JAXBContext jaxbContext = JAXBContext.newInstance(InfoPersons.class);
					Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
					StringReader reader = new StringReader(txn.getInfoPerson());
					InfoPersons persons = (InfoPersons) unmarshaller.unmarshal(reader);
					List<InfoPerson> listPerson = null;
					String personId = "";
					if(persons != null){
						listPerson = persons.getInfoPersons();
						if(listPerson != null && listPerson.size() > 0){
							personId = listPerson.get(0).getId();
						}
					}
					listFormat = this.formatDatatoVN(listPerson);
					if(StringUtils.isNotEmpty(personId)){
						List<NicTransactionAttachment> photoList = nicTransactionAttachmentService.findFacialTEById (
								soHoSo, NicTransactionAttachment.DOC_TYPE_ENROLLED_SUB, personId);				
						if (CollectionUtils.isNotEmpty(photoList) && photoList.size() > 0) {
							String images = Base64.encodeBase64String(photoList.get(0).getDocData());
							photoTreEm = images;						
						}
						
					}				
				}
				model.addAttribute("photoTreEm", photoTreEm);
				model.addAttribute("dsTreEm", listFormat);
				model.addAttribute("slTreEm", listFormat.size() > 0 ? "Y" : "N");
				model.addAttribute("chiTietDS", pr1);
				model.addAttribute("photoStr", photoStr);
				model.addAttribute("soHoSo", soHoSo);
				//model.addAttribute("dsTreEm", new ArrayList<NicUploadJobDto>());
				ModelAndView modelAndView = new ModelAndView("investigation.investigation.process");
				modelAndView.addObject("formData", investigationAssignmentData);
				return modelAndView;
			}
			List<String> loadJob = new ArrayList<String>();
			for(int i = 0; i < investigationAssignmentData.getLoadJobIds().length; i++){
				if(!investigationAssignmentData.getLoadJobIds()[i].equals(investigationAssignmentData.getJobId())){
					jobId = investigationAssignmentData.getLoadJobIds()[i];
					break;
				}
			}
			for(int i = 0; i < investigationAssignmentData.getLoadJobIds().length; i++){
				if(!investigationAssignmentData.getLoadJobIds()[i].equals(jobId)){
					loadJob.add(investigationAssignmentData.getLoadJobIds()[i]);
				}
			}
			
			model.addAttribute("loadJob", loadJob);
			model.addAttribute("jobCompare", jobId);
			model.addAttribute("dsJob", listJobId1);
			model.addAttribute("count", listJobId1.size());
			model.addAttribute("stt", listJobId1.size() - loadJob.size());
			//model.addAttribute("jobCompare", investigationAssignmentData.getProcessJobIds()[0]);
			//lấy thông tin trùng----------------------------------------------
			InvestigationMatchingDto listTrung = MatchingPersonal(Long.parseLong(jobId));
			/*---------------------------------------------------------------*/
			
			//Ảnh vân tay chủ thể-------------------------------------------------------
			//Map<Integer, FingerprintInfo> fpMap = null;
			//Map<Integer, FingerprintInfo> fpMapDoiChieu = null;
			String viewFPFalg = "Y";
			NicUploadJob jobInfo = uploadJobService.findById(Long.parseLong(jobId));
			NicTransaction nicTransaction = jobInfo.getNicTransaction();
			//model.addAttribute("noteB", jobInfo.getNoteApprove());
			model.addAttribute("noteP", jobInfo.getNoteApprovePerson());
			model.addAttribute("noteO", jobInfo.getNoteApproveObj());
			model.addAttribute("noteN", jobInfo.getNoteApproveNin());
			model.addAttribute("idMaster", jobInfo.getTransactionId());
		 	//fpMap = getVanTayTheoMaHoSo(nicTransaction, userSession);
		 	InvestigationMatchingDto dto = null;
		 	//Trường hợp không có hồ sơ trùng
		 	if(nicTransaction != null && nicTransaction.getNicRegistrationData() != null){
		 		dto = new InvestigationMatchingDto();
		 		dto.getMasterData().setFullName(HelperClass.createFullName(nicTransaction.getNicRegistrationData().getSurnameFull(), nicTransaction.getNicRegistrationData().getMiddlenameFull(), nicTransaction.getNicRegistrationData().getFirstnameFull()));
		 		dto.getMasterData().setDob(nicTransaction.getNicRegistrationData().getDateOfBirth() != null ? HelperClass.convertDateToString2(nicTransaction.getNicRegistrationData().getDateOfBirth()) : "");
		 		dto.getMasterData().setNin(nicTransaction.getNin());
		 		List<NicTransactionAttachment> photoList = nicTransactionAttachmentService.findNicTransactionAttachments(
						nicTransaction.getTransactionId(), NicTransactionAttachment.DOC_TYPE_PHOTO_CAPTURE, NicTransactionAttachment.DEFAULT_SERIAL_NO).getListModel();				
				if (CollectionUtils.isNotEmpty(photoList) && photoList.size() > 0) {
					String images = Base64.encodeBase64String(photoList.get(0).getDocData());
					dto.getMasterData().setPhoto(images);
				}else{
					dto.getMasterData().setPhoto("");
				}
				if(nicTransaction.getNicRegistrationData().getGender() != null){
					dto.getMasterData().setGender(nicTransaction.getNicRegistrationData().getGender().equals("M") ? "Nam" : "Nữ");
				}else {
					dto.getMasterData().setGender("Không");
				}
				dto.getMasterData().setAddress(nicTransaction.getNicRegistrationData().getAddressLine1());
				dto.getMasterData().setNation(StringUtils.isNotEmpty(nicTransaction.getNicRegistrationData().getNation()) ? codesService.getCodeValueDescByIdName("CODE_NATION" ,nicTransaction.getNicRegistrationData().getNation(), "") : "");
				dto.getMasterData().setReligion(StringUtils.isNotEmpty(nicTransaction.getNicRegistrationData().getReligion()) ? codesService.getCodeValueDescByIdName(Constants.CODE_RELIGION_ID, nicTransaction.getNicRegistrationData().getReligion(), "") : "");
		 		//dto = getInfoTransactionData(nicTransaction.getTransactionId());
		 	}
		 	InvestigationListInfoTargetDto trung1 = null;
		 	//List<ImmiHistory> listHistory = null;
		 	if(listTrung.getListTargetData() != null && listTrung.getListTargetData().size() > 0){
		 		List<InvestigationListInfoTargetDto> listSort = new ArrayList<InvestigationListInfoTargetDto>();
		 		for(InvestigationListInfoTargetDto inv : listTrung.getListTargetData()){
		 			if(StringUtils.isNotEmpty(inv.getScoreBms_O())){
		 				listSort.add(inv);
		 			}
		 		}
		 		for(InvestigationListInfoTargetDto inv : listTrung.getListTargetData()){
		 			if(StringUtils.isEmpty(inv.getScoreBms_O())){
		 				listSort.add(inv);
		 			}
		 		}
		 		listTrung.setListTargetData(listSort);
		 		NicTransaction nicTran = nicTransactionService.findById(listTrung.getListTargetData().get(0).getTransactionId_O());
		 		if(nicTran != null){
		 			//fpMapDoiChieu = getVanTayTheoMaHoSo(nicTran, userSession);
		 			trung1 = getInfoTransactionData(nicTran.getTransactionId());
		 			trung1.setPhoto_O(trung1.getImage_O());
		 		}
		 		//listHistory = ListHistoryByTarget(listTrung.get(0).getTransactionId_O()).getListImmi();
		 	}else{
		 		model.addAttribute("khongTrung", "Y");
		 	}
//		 	else{
//		 		listHistory = new ArrayList<ImmiHistory>();
//		 	}
		 	if(investigationAssignmentData.getStageLoad().equals("Y")){
				if(jobInfo.getDateApprovePerson() != null){
					model.addAttribute("action", "N");
				}else{
					model.addAttribute("action", "Y");
				}
			}else{
				model.addAttribute("action", "Y");
			}
			List<NicUploadJobDto> list = new ArrayList<NicUploadJobDto>();
			pr = new PaginatedResult<>(0, 1, list);
			if (pr != null) {
				model.addAttribute("stage", investigationAssignmentData.getStageLoad());
				model.addAttribute("chuHS", listTrung.getMasterData());
				model.addAttribute("HSTrung1", trung1 != null ? trung1 : new InvestigationListInfoTargetDto());
				model.addAttribute("fnSelected", Constants.HEADING_NIC_INVESTIGATION);
				ModelAndView mav = new ModelAndView("investigation.compare.info");
//				mav.addObject("fpMap", fpMap);
//				mav.addObject("fpMapDoiChieu", fpMapDoiChieu);
				mav.addObject("viewFPFalg", viewFPFalg);
				mav.addObject("dsBiTrung", listTrung.getListTargetData());
				model.addAttribute("showBody", listTrung.getListTargetData().size() > 0 ? "Y" : "N");
//				mav.addObject("HoSoXNC1", listHistory.size() > 0 ? listHistory.get(0) : new ImmiHistory());
//				mav.addObject("dsXNC", listHistory);
				return mav;
			}
		} catch (Exception e) {

			e.printStackTrace();
		}
		return new ModelAndView("investigation.compare.info");
	}
	
	@ResponseBody
	@RequestMapping(value="/getFacialSubById")
	public String getFacialSubById(@RequestParam String transId, @RequestParam String personId,
			WebRequest request, HttpServletRequest httpRequest, ModelMap model) throws Exception {
		String photoTreEm = "<img class=\"img-border\" height=\"180\" width=\"135\" src=\"/ric-web/resources/images/No_Image.jpg\">";
		List<NicTransactionAttachment> photoList = nicTransactionAttachmentService.findFacialTEById (
				transId, NicTransactionAttachment.DOC_TYPE_ENROLLED_SUB, personId);	
		if (CollectionUtils.isNotEmpty(photoList) && photoList.size() > 0) {
			String images = Base64.encodeBase64String(photoList.get(0).getDocData());
			photoTreEm = "<img src=\'data:image/jpg;base64,"+ images +"' class=\"img-border doGetAJpgSafeVersion\" height=\"180\" width=\"135\" />";														
		}
		return photoTreEm;
	}
	
	public ModelAndView nextObject(InvestigationAssignmentData investigationAssignmentData, ModelMap model){
		String jobId = "";
		investigationAssignmentData.cleanUpStrings();
		List<String> loadJob = new ArrayList<String>();
		List<String> listJobId1 = new ArrayList<String>(); //Lưu thông tin jobId để load lại khi hoàn thành khớp hs
		for(int i = 0; i < investigationAssignmentData.getSelectedJobIds().length; i++){
			listJobId1.add(investigationAssignmentData.getSelectedJobIds()[i]);		
			/*if(!investigationAssignmentData.getSelectedJobIds()[i].equals(investigationAssignmentData.getProcessJobIds()[0])){
				loadJob.add(investigationAssignmentData.getSelectedJobIds()[i]);
			}*/
		}
		
		for(int i = 0; i < investigationAssignmentData.getLoadJobIds().length; i++){
			if(!investigationAssignmentData.getLoadJobIds()[i].equals(investigationAssignmentData.getJobId())){
				jobId = investigationAssignmentData.getLoadJobIds()[i];
				break;
			}
		}
		for(int i = 0; i < investigationAssignmentData.getLoadJobIds().length; i++){
			if(!investigationAssignmentData.getLoadJobIds()[i].equals(jobId)){
				loadJob.add(investigationAssignmentData.getLoadJobIds()[i]);
			}
		}
		
		NicUploadJob jobLoad = uploadJobService.findById(Long.parseLong(jobId));
 		List<EppBlacklist> listBlack = ListMatchingEppBlacklist(Long.parseLong(jobId));	
 		InvestigationListInfoTargetDto dto = null;
 		if(jobLoad != null){
			dto = getInfoTransactionData(jobLoad.getTransactionId());
			//model.addAttribute("noteB", jobLoad.getNoteApprove());
			model.addAttribute("noteAprovePerson", jobLoad.getNoteApprovePerson() != null ? jobLoad.getNoteApprovePerson() : "");
			model.addAttribute("noteAproveObj", jobLoad.getNoteApproveObj() != null ? jobLoad.getNoteApproveObj() : "");
			model.addAttribute("noteAproveNin", jobLoad.getNoteApproveNin() != null ? jobLoad.getNoteApproveNin() : "");
			model.addAttribute("idMaster", jobLoad.getTransactionId());
 		}
 		if(listBlack == null || listBlack.size() <= 0){    			
 			listBlack = new ArrayList<EppBlacklist>();
 			model.addAttribute("khongTrung", "Y");
 		}
 		else{
 			
 			Long idBb = listBlack.get(0).getId();
 			//Lẩy ảnh đối tượng bị trùng
 			List<EppBlacklistAttachment> listBB_Attach = blacklistService.findAttachmentById(idBb);
 			if(listBB_Attach != null && listBB_Attach.size() > 0){
 				for(EppBlacklistAttachment obj :listBB_Attach){
 					if(obj.getType_().equals("PHOTO")){
 						model.addAttribute("photoTrung", obj.getBase64());
 						break;
 					}
 				}
 			}
 		}
 		if(investigationAssignmentData.getStageLoad().equals("Y")){
			if(jobLoad.getDateApproveInvestigation() != null){
				model.addAttribute("action", "N");
			}else{
				model.addAttribute("action", "Y");
			}
		}else{
			model.addAttribute("action", "Y");
		}
 		model.addAttribute("count", listJobId1.size());
		model.addAttribute("stt", listJobId1.size() - loadJob.size());
 		model.addAttribute("stage", investigationAssignmentData.getStageLoad());
 		model.addAttribute("loadJob", loadJob);
 		model.addAttribute("jobCompare", jobId);
 		model.addAttribute("dsJob", listJobId1);
 		model.addAttribute("rootEntity", dto);
 		model.addAttribute("listBlack", listBlack);
 		model.addAttribute("showBody", listBlack.size() > 0 ? "Y" : "N");
 		model.addAttribute("fakeEntity", listBlack.size() > 0 ? listBlack.get(0) : new EppBlacklist());
		model.addAttribute("fnSelected", Constants.HEADING_NIC_INVESTIGATION);
		ModelAndView mav = new ModelAndView("investigation.search.obj");				
		return mav;
	}
	
	public ModelAndView nextTransaction(InvestigationAssignmentData investigationAssignmentData, ModelMap model){
		try{
			PaginatedResult<NicUploadJobDto> pr = null;
			String jodId = "";
			
			investigationAssignmentData.cleanUpStrings();
			List<String> listJobId1 = new ArrayList<String>(); //Lưu thông tin jobId để load lại khi hoàn thành khớp hs
			List<String> loadJob = new ArrayList<String>();
			for(int i = 0; i < investigationAssignmentData.getSelectedJobIds().length; i++){
				listJobId1.add(investigationAssignmentData.getSelectedJobIds()[i]);	
			}
			
			for(int i = 0; i < investigationAssignmentData.getLoadJobIds().length; i++){
				if(!investigationAssignmentData.getLoadJobIds()[i].equals(investigationAssignmentData.getJobId())){
					jodId = investigationAssignmentData.getLoadJobIds()[i];
					break;
				}
			}
			for(int i = 0; i < investigationAssignmentData.getLoadJobIds().length; i++){
				if(!investigationAssignmentData.getLoadJobIds()[i].equals(jodId)){
					loadJob.add(investigationAssignmentData.getLoadJobIds()[i]);
				}
			}
			model.addAttribute("loadJob", loadJob);
			model.addAttribute("dsJob", listJobId1);
			model.addAttribute("jobCompare", jodId);
			model.addAttribute("count", listJobId1.size());
			model.addAttribute("stt", listJobId1.size() - loadJob.size());
			//lấy thông tin trùng----------------------------------------------
			InvestigationMatchingDto listTrung = MatchingPersonal(Long.parseLong(jodId));
			/*---------------------------------------------------------------*/
			
			//Ảnh vân tay chủ thể-------------------------------------------------------
			//Map<Integer, FingerprintInfo> fpMap = null;
			//Map<Integer, FingerprintInfo> fpMapDoiChieu = null;
			String viewFPFalg = "Y";
			NicUploadJob jobInfo = uploadJobService.findById(Long.parseLong(jodId));
			NicTransaction nicTransaction = jobInfo.getNicTransaction();
			//model.addAttribute("noteB", jobInfo.getNoteApprove());
			model.addAttribute("noteP", jobInfo.getNoteApprovePerson());
			model.addAttribute("noteO", jobInfo.getNoteApproveObj());
			model.addAttribute("noteN", jobInfo.getNoteApproveNin());
			model.addAttribute("idMaster", jobInfo.getTransactionId());
		 	//fpMap = getVanTayTheoMaHoSo(nicTransaction, userSession);
		 	InvestigationMatchingDto dto = null;
		 	//Trường hợp không có hồ sơ trùng
		 	if(nicTransaction != null && nicTransaction.getNicRegistrationData() != null){
		 		dto = new InvestigationMatchingDto();
		 		dto.getMasterData().setFullName(HelperClass.createFullName(nicTransaction.getNicRegistrationData().getSurnameFull(), nicTransaction.getNicRegistrationData().getMiddlenameFull(), nicTransaction.getNicRegistrationData().getFirstnameFull()));
		 		dto.getMasterData().setDob(nicTransaction.getNicRegistrationData().getDateOfBirth() != null ? HelperClass.convertDateToString2(nicTransaction.getNicRegistrationData().getDateOfBirth()) : "");
		 		dto.getMasterData().setNin(nicTransaction.getNin());
		 		List<NicTransactionAttachment> photoList = nicTransactionAttachmentService.findNicTransactionAttachments(
						nicTransaction.getTransactionId(), NicTransactionAttachment.DOC_TYPE_PHOTO_CAPTURE, NicTransactionAttachment.DEFAULT_SERIAL_NO).getListModel();				
				if (CollectionUtils.isNotEmpty(photoList) && photoList.size() > 0) {
					String images = Base64.encodeBase64String(photoList.get(0).getDocData());
					dto.getMasterData().setPhoto(images);
				}else{
					dto.getMasterData().setPhoto("");
				}
				if(nicTransaction.getNicRegistrationData().getGender() != null){
					dto.getMasterData().setGender(nicTransaction.getNicRegistrationData().getGender().equals("M") ? "Nam" : "Nữ");
				}else {
					dto.getMasterData().setGender("Không");
				}
				dto.getMasterData().setAddress(nicTransaction.getNicRegistrationData().getAddressLine1());
				dto.getMasterData().setNation(StringUtils.isNotEmpty(nicTransaction.getNicRegistrationData().getNation()) ? codesService.getCodeValueDescByIdName("CODE_NATION" ,nicTransaction.getNicRegistrationData().getNation(), "") : "");
				dto.getMasterData().setReligion(StringUtils.isNotEmpty(nicTransaction.getNicRegistrationData().getReligion()) ? codesService.getCodeValueDescByIdName(Constants.CODE_RELIGION_ID, nicTransaction.getNicRegistrationData().getReligion(), "") : "");
		 		//dto = getInfoTransactionData(nicTransaction.getTransactionId());
		 	}
		 	InvestigationListInfoTargetDto trung1 = null;
		 	//List<ImmiHistory> listHistory = null;
		 	if(listTrung.getListTargetData() != null && listTrung.getListTargetData().size() > 0){
		 		List<InvestigationListInfoTargetDto> listSort = new ArrayList<InvestigationListInfoTargetDto>();
		 		for(InvestigationListInfoTargetDto inv : listTrung.getListTargetData()){
		 			if(StringUtils.isNotEmpty(inv.getScoreBms_O())){
		 				listSort.add(inv);
		 			}
		 		}
		 		for(InvestigationListInfoTargetDto inv : listTrung.getListTargetData()){
		 			if(StringUtils.isEmpty(inv.getScoreBms_O())){
		 				listSort.add(inv);
		 			}
		 		}
		 		listTrung.setListTargetData(listSort);
		 		NicTransaction nicTran = nicTransactionService.findById(listTrung.getListTargetData().get(0).getTransactionId_O());
		 		if(nicTran != null){
		 			//fpMapDoiChieu = getVanTayTheoMaHoSo(nicTran, userSession);
		 			trung1 = getInfoTransactionData(nicTran.getTransactionId());
		 			trung1.setPhoto_O(trung1.getImage_O());
		 		}
		 		//listHistory = ListHistoryByTarget(listTrung.get(0).getTransactionId_O()).getListImmi();
		 	}else{
		 		model.addAttribute("khongTrung", "Y");
		 	}
//		 	else{
//		 		listHistory = new ArrayList<ImmiHistory>();
//		 	}
		 	if(investigationAssignmentData.getStageLoad().equals("Y")){
				if(jobInfo.getDateApprovePerson() != null){
					model.addAttribute("action", "N");
				}else{
					model.addAttribute("action", "Y");
				}
			}else{
				model.addAttribute("action", "Y");
			}
			List<NicUploadJobDto> list = new ArrayList<NicUploadJobDto>();
			pr = new PaginatedResult<>(0, 1, list);
			if (pr != null) {
				//model.addAttribute("stage", investigationAssignmentData.getAssignmentStatus());
				model.addAttribute("stage", investigationAssignmentData.getStageLoad());
				model.addAttribute("chuHS", listTrung.getMasterData());
				model.addAttribute("HSTrung1", trung1 != null ? trung1 : new InvestigationListInfoTargetDto());
				model.addAttribute("fnSelected", Constants.HEADING_NIC_INVESTIGATION);
				ModelAndView mav = new ModelAndView("investigation.compare.info");
//				mav.addObject("fpMap", fpMap);
//				mav.addObject("fpMapDoiChieu", fpMapDoiChieu);
				mav.addObject("viewFPFalg", viewFPFalg);
				mav.addObject("dsBiTrung", listTrung.getListTargetData());
				model.addAttribute("showBody", listTrung.getListTargetData().size() > 0 ? "Y" : "N");
//				mav.addObject("HoSoXNC1", listHistory.size() > 0 ? listHistory.get(0) : new ImmiHistory());
//				mav.addObject("dsXNC", listHistory);
				return mav;
			}
		}catch(Exception ex){
			
		}
		return new ModelAndView("investigation.compare.info");
	}
//	@RequestMapping(value = "/getInfoObjectById/{transactionId}")
//	public InvestigationMatchingDto getInfoObjectByTransaction(@PathVariable String transactionId,
//			WebRequest request, HttpServletRequest httpRequest, ModelMap model){
//		return null;
//	}
	
}
