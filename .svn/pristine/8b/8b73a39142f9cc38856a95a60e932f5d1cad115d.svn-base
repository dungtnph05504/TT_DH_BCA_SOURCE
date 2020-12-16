/**
 * 
 */
package com.nec.asia.nic.syncSinger.controller;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintStream;
import java.io.Serializable;
import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.TreeMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import ognl.InappropriateExpressionException;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.displaytag.tags.TableTagParameters;
import org.displaytag.util.ParamEncoder;
import org.hibernate.criterion.Order;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
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

import com.fasterxml.jackson.annotation.JsonProperty;
import com.nec.asia.nic.common.DmsUtility;
import com.nec.asia.nic.comp.job.dto.HitCandidateDTO;
import com.nec.asia.nic.comp.job.dto.SearchHitDto;
import com.nec.asia.nic.comp.listHandover.domain.NicListHandover;
import com.nec.asia.nic.comp.listHandover.domain.NicListHandoverDetail;
import com.nec.asia.nic.comp.listHandover.domain.NicListHandoverId;
import com.nec.asia.nic.comp.listHandover.domain.NicPersoLocations;
import com.nec.asia.nic.comp.listHandover.service.ListHandoverService;
import com.nec.asia.nic.comp.listHandover.service.PersoLocationsService;
import com.nec.asia.nic.comp.queuesJobSchedule.service.QueuesJobScheduleService;
import com.nec.asia.nic.comp.trans.dao.DocumentDataDao;
import com.nec.asia.nic.comp.trans.domain.AreaData;
import com.nec.asia.nic.comp.trans.domain.AttachmentDoc;
import com.nec.asia.nic.comp.trans.domain.BaseModelSingle;
import com.nec.asia.nic.comp.trans.domain.CountryData;
import com.nec.asia.nic.comp.trans.domain.DocAttachment;
import com.nec.asia.nic.comp.trans.domain.DocumentInfo;
import com.nec.asia.nic.comp.trans.domain.NicDocumentData;
import com.nec.asia.nic.comp.trans.domain.NicRegistrationData;
import com.nec.asia.nic.comp.trans.domain.NicSearchHitResult;
import com.nec.asia.nic.comp.trans.domain.NicSearchResult;
import com.nec.asia.nic.comp.trans.domain.NicTransaction;
import com.nec.asia.nic.comp.trans.domain.NicTransactionAttachment;
import com.nec.asia.nic.comp.trans.domain.NicTransactionLog;
import com.nec.asia.nic.comp.trans.domain.NicUploadJob;
import com.nec.asia.nic.comp.trans.domain.OfficeData;
import com.nec.asia.nic.comp.trans.domain.PassportInformation;
import com.nec.asia.nic.comp.trans.domain.PersonAttachment;
import com.nec.asia.nic.comp.trans.domain.PersonInformation;
import com.nec.asia.nic.comp.trans.domain.PlaceData;
import com.nec.asia.nic.comp.trans.domain.QueuesJobSchedule;
import com.nec.asia.nic.comp.trans.domain.type.DocumentStatus;
import com.nec.asia.nic.comp.trans.dto.AssignmentFilterAll;
import com.nec.asia.nic.comp.trans.dto.InvestigationHitData;
import com.nec.asia.nic.comp.trans.dto.NicSearchHitResultHitInfoItem;
import com.nec.asia.nic.comp.trans.dto.NicUploadJobDto;
import com.nec.asia.nic.comp.trans.dto.Passport;
import com.nec.asia.nic.comp.trans.exception.JobNoLongerAssignedToOfficerException;
import com.nec.asia.nic.comp.trans.service.DocumentDataService;
import com.nec.asia.nic.comp.trans.service.NicTransactionAttachmentService;
import com.nec.asia.nic.comp.trans.service.NicTransactionService;
import com.nec.asia.nic.comp.trans.service.NicUploadJobService;
import com.nec.asia.nic.comp.trans.service.exception.NicUploadJobServiceException;
import com.nec.asia.nic.comp.trans.service.exception.TransactionServiceException;
import com.nec.asia.nic.comp.trans.service.impl.NicSearchHitResultHitScorerFpImpl;
import com.nec.asia.nic.comp.trans.service.impl.NicSearchHitResultHitScorerTextualImpl;
import com.nec.asia.nic.enquiry.controller.FingerprintInfo;
import com.nec.asia.nic.framework.PaginatedResult;
import com.nec.asia.nic.framework.admin.code.domain.CodeValues;
import com.nec.asia.nic.framework.admin.code.domain.Codes;
import com.nec.asia.nic.framework.admin.code.domain.ParaSignerCompares;
import com.nec.asia.nic.framework.admin.code.domain.Parameters;
import com.nec.asia.nic.framework.admin.code.domain.ParametersId;
import com.nec.asia.nic.framework.admin.code.domain.PaymentDef;
import com.nec.asia.nic.framework.admin.code.domain.PaymentDefId;
import com.nec.asia.nic.framework.admin.code.service.CodesService;
import com.nec.asia.nic.framework.admin.code.service.ParametersService;
import com.nec.asia.nic.framework.admin.code.service.PaymentDefService;
import com.nec.asia.nic.framework.admin.rbac.domain.Users;
import com.nec.asia.nic.framework.admin.rbac.service.UserService;
import com.nec.asia.nic.framework.admin.site.dao.SiteRepositoryDao;
import com.nec.asia.nic.framework.admin.site.domain.SiteRepository;
import com.nec.asia.nic.framework.admin.site.service.SiteService;
import com.nec.asia.nic.framework.common.ImageUtil;
import com.nec.asia.nic.framework.controller.AbstractController;
import com.nec.asia.nic.framework.report.service.AuditAccessLogService;
import com.nec.asia.nic.investigation.AttachmentSource;
import com.nec.asia.nic.investigation.Base64Jp2HeaderHelper;
import com.nec.asia.nic.investigation.controller.AttachmentEntry;
import com.nec.asia.nic.investigation.controller.InvestigationAssignmentData;
import com.nec.asia.nic.investigation.controller.InvestigationController;
import com.nec.asia.nic.investigation.controller.InvestigationData;
import com.nec.asia.nic.investigation.controller.InvestigationHit;
import com.nec.asia.nic.investigation.controller.InvestigationController.DataJson;
import com.nec.asia.nic.investigation.controller.InvestigationController.RequestDocument;
import com.nec.asia.nic.investigation.exception.InvalidParameterException;
import com.nec.asia.nic.util.BaseListResponse;
import com.nec.asia.nic.util.Constants;
import com.nec.asia.nic.util.PrintLocation;
import com.nec.asia.nic.util.RegistrationConstants;
import com.nec.asia.nic.util.RequestBase;
import com.nec.asia.nic.util.pagingUtil;
import com.nec.asia.nic.utils.DateUtil;
import com.nec.asia.nic.utils.HelperClass;
import com.nec.asia.nic.web.session.UserSession;

/**
 * @author sailaja_chinapothula
 * @Company: NEC Asia Pacific Ltd
 * @Since: Jun 27, 2013
 */

/*
 * Modification History:
 * 
 * 30 Dec 2013 (chris lai) : update with audit record
 */

@Controller
@RequestMapping(value = "/syncSinger")
public class SyncSingerController extends AbstractController {

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
	private PaymentDefService paymentDefService;

	@Autowired
	private NicTransactionService nicTransactionService;

	@Autowired
	private NicUploadJobService uploadJobService;

	@Autowired
	private PersoLocationsService persoLocationsService;

	@Autowired
	// @Qualifier(value="codesService")
	private CodesService codesService;

	// 30 Dec 2013 (chris lai) : update with audit record
	@Autowired
	private AuditAccessLogService auditAccessLogService;

	@Autowired
	private SiteRepositoryDao siteRepositoryDao;
	
	@Autowired
	private NicTransactionAttachmentService nicTransactionAttachmentService;

	@Autowired
	private SiteService eppSiteService;
	
	@Autowired
	private DocumentDataDao documentDataDao = null;
	
	@Autowired
	private DocumentDataService documentDataService;
	
	@Autowired
	private ListHandoverService listHandoverService;
	
	@Autowired
	private QueuesJobScheduleService queuesJobScheduleService;
	
	@Autowired
	@Qualifier("userService")
	private UserService userService;
	
	private static String DECENDING_ORDER = "2";
	
	private static final String PARA_TX_ATTCH_J2K_CONVERT_AT_SERVER_SCOPE = "SYSTEM";
	private static final String PARA_TX_ATTCH_J2K_CONVERT_AT_SERVER_VALUE = "TX_ATTCH_J2K_CONVERT_AT_SERVER";
	
	private static final String PARA_TRANSACTION_ATTACHMENT_SOURCE_SCOPE = "SYSTEM";
	private static final String PARA_TRANSACTION_ATTACHMENT_SOURCE_VALUE = "TRANSACTION_ATTACHMENT_SOURCE";
	
	private static final String PARA_TRANSACTION_ATTACHM_IGNORE_J2K_SCOPE = "SYSTEM";
	private static final String PARA_TRANSACTION_ATTACHM_IGNORE_J2K_VALUE = "TRANSACT2_ATTACHM_IGNORE_J2K";
	
	private static final String PARA_SCOPE_SYSTEM = "SYSTEM";
	private static final String PARA_NAME_SYSTEM_DMS_URL = "DMS_URL";

	private static final Logger logger = LoggerFactory
			.getLogger(SyncSingerController.class);

	@ModelAttribute("userAssignment")
	public Map<String, String> userAssignment() {
		List<Users> codeList = userService.findAll();
		Map<String, String> userAssignment = new LinkedHashMap<String, String>();
		for (Users ricCode : codeList) {
			userAssignment.put(ricCode.getUserId(), ricCode.getUserId());
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

	// /TRUNG THÊM ĐỒNG BỘ DỮ LIỆU SANG A72
	// TODO: TRUNG THÊM SEARCH CHO PHẦN ĐỒNG BỘ DỮ LIỆU SANG A72
	@RequestMapping(value = "/searchSyncTransactionId", method = RequestMethod.POST)
	public ModelAndView searchJobSyncSingerList(
			@ModelAttribute(value = "formData") InvestigationAssignmentData investigationAssignmentData,
			WebRequest request,
			HttpServletRequest httpRequest, ModelMap model)
			throws Throwable {
		logger.info("applyFilter");

		return this.getSyncSingerJob(investigationAssignmentData,
				request, httpRequest, model, 1);

	}
	
	@RequestMapping(value = "/getSyncSingerJob")
	public ModelAndView getSyncSingerJob(
			@ModelAttribute(value = "formData") InvestigationAssignmentData investigationAssignmentData,
			WebRequest request, HttpServletRequest httpRequest, ModelMap model) {
		logger.info("getSyncSingerJob");

		int pageNo = 1;

		String tableId = "row";
		String pageNumber = request.getParameter((new ParamEncoder(tableId)
				.encodeParameterName(TableTagParameters.PARAMETER_PAGE)));
		if (StringUtils.isNotBlank(pageNumber)
				&& StringUtils.isNumeric(pageNumber)) {
			pageNo = Integer.parseInt(pageNumber);
		}
		pageNo = !StringUtils.isEmpty(request.getParameter("pageNo")) ? Integer.parseInt(request.getParameter("pageNo")) : 1;
		return this.getSyncSingerJob(investigationAssignmentData,
				request, httpRequest, model, pageNo);
	}

	public ModelAndView getSyncSingerJob(
			@ModelAttribute(value = "formData") InvestigationAssignmentData investigationAssignmentData,
			WebRequest request,
			HttpServletRequest httpRequest, ModelMap model, int pageNo) {
		logger.info("NIC Perso Manager approve ==== ");
		PaginatedResult<NicUploadJobDto> pr = null;
		try {
			int pageSize = 20;
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
			String order = SyncSingerController.DECENDING_ORDER;

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
			if (order.equalsIgnoreCase(SyncSingerController.DECENDING_ORDER)) {
				hibernateOrder = Order.asc(sortedElement);
			}
			
			if (investigationAssignmentData == null) {
				investigationAssignmentData = new InvestigationAssignmentData();
			}

			investigationAssignmentData.cleanUpStrings();

			Parameters parameter = parametersService.findById(id);

			if (parameter != null) {
				String pageSizeDb = parameter.getParaShortValue();

				if (StringUtils.isNotBlank(pageSizeDb)
						&& StringUtils.isNumeric(pageSizeDb)) {
					pageSize = Integer.parseInt(pageSizeDb);
				}
			}
			pageSize = Constants.PAGE_SIZE_DEFAULT;
			String pageNumber = request.getParameter((new ParamEncoder(tableId)
					.encodeParameterName(TableTagParameters.PARAMETER_PAGE)));

			if (StringUtils.isNotBlank(pageNumber)
					&& StringUtils.isNumeric(pageNumber)) {
				pageNo = Integer.parseInt(pageNumber);
			}
			httpRequest.setAttribute("pendingCount",
					uploadJobService.getPendingInvestigationsCount());
			// pr =
			// uploadJobService.findAllForInvestigationPagination(userSession.getUserName(),
			// pageNo, pageSize);
			pr = uploadJobService.findSyncSingerList(userSession.getUserName(),
					pageNo, pageSize, null, new AssignmentFilterAll(investigationAssignmentData
							.getSearchTransactionId(),
							investigationAssignmentData.getPriority(),
							investigationAssignmentData
									.getTransactionType(), null, null, "","",""));
			List<NicUploadJobDto> list = new ArrayList<NicUploadJobDto>();
			if (pr != null) {
				list = pr.getRows();
				if (list != null) {
					for (NicUploadJobDto record : list) {
						String transactionId = record.getTransactionId();
						if (transactionId != null) {
							NicTransaction nicTransaction = nicTransactionService
									.findById(transactionId);
							if (nicTransaction != null) {
								record.setDateOfApplication(nicTransaction
										.getDateOfApplication());
								record.setFullName(nicTransaction
										.getNicRegistrationData()
										.getSurnameLine1());
								record.setEstDateOfCollection(nicTransaction
										.getEstDateOfCollection());
								// record.setPriority(nicTransaction.getPriority());
								{
									try {
										CodeValues codeValue = codesService
												.getCodeValueByIdName(
														Codes.PRIORITY,
														nicTransaction
																.getPriority()
																.toString());
										if (codeValue != null
												&& codeValue.getCodeValueDesc() != null) {
											record.setPriorityString(codeValue
													.getCodeValueDesc());
										} else {
											record.setPriorityString(nicTransaction
													.getPriority() == null ? null
													: nicTransaction
															.getPriority()
															.toString());
										}
									} catch (Exception e) {
										record.setPriorityString(nicTransaction
												.getPriority() == null ? null
												: nicTransaction.getPriority()
														.toString());
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
				model.addAttribute("jobList", list);
				//end

				model.addAttribute("fnSelected",
						Constants.HEADING_NIC_SYNC);
				ModelAndView modelAndView = new ModelAndView(
						"syncSinger.syncSingermain",
						searchResultMap);
				investigationAssignmentData.cleanUpForNextPage();
				modelAndView.addObject("formData", investigationAssignmentData);
				return modelAndView;
			} else {
				model.addAttribute("fnSelected",
						Constants.HEADING_NIC_SYNC);
				ModelAndView modelAndView = new ModelAndView(
						"syncSinger.syncSingermain",
						null);
				investigationAssignmentData.cleanUpForNextPage();
				modelAndView.addObject("formData", investigationAssignmentData);
				return modelAndView;
			}
			// }
		} catch (Exception e) {
			logger.info(e.getMessage());
			e.printStackTrace();
			return null;
			// return new ModelAndView("investigation.investigation", null);
		}
	}

	@RequestMapping(value = { "/syncSingerDetailId/{jobId}" })
	public ModelAndView syncSingerDetailId(@PathVariable long jobId,
			HttpServletRequest httpRequest, Model model) throws Throwable {
		logger.info("NIC Start startApproveStatus compare");

		return this.syncSingerDetailId(jobId, httpRequest, model, null);
	}

	public ModelAndView syncSingerDetailId(long jobId,
			HttpServletRequest httpRequest, Model model,
			List<String> listOfMessages) throws Throwable {
		logger.info("NIC Start Investigation compare, listOfMessages");

		this.prepareModelStuff(model);

		ModelAndView modelAndView = new ModelAndView(
				"syncSinger.syncSinger.compare");

		NicUploadJob jobDetails = uploadJobService.findById(jobId);
		NicTransaction nicTransaction = nicTransactionService
				.findById(jobDetails.getTransactionId());

		String mainTransactionId = jobDetails.getTransactionId();

		this.initializeModelAndViewForms(modelAndView);

		this.initializeModelAndViewCommonPageDetails(modelAndView, jobDetails,
				mainTransactionId);

		List<SearchHitDto> hits = this.getAllHits(jobId, mainTransactionId);

		List<InvestigationHit> invHits_all = this.getAllHitsForModelAndView(
				httpRequest, modelAndView, jobDetails, mainTransactionId, hits);
		logger.info("invHits_all.size():" + invHits_all.size());

		List<InvestigationHit> invHits_error = this
				.getAllHits_error(invHits_all);
		this.processMainCandidateInvestigationInformation(invHits_all,
				invHits_error);

		List<InvestigationHit> invHits = this.getAllHits_nonError(invHits_all);

		// trung show img
		String photoStr = null;
		List<NicTransactionAttachment> photoList = nicTransactionAttachmentService
				.findNicTransactionAttachments(jobDetails.getTransactionId(),
						NicTransactionAttachment.DOC_TYPE_PHOTO_CAPTURE,
						NicTransactionAttachment.DEFAULT_SERIAL_NO).getListModel();
		if (CollectionUtils.isNotEmpty(photoList) && photoList.size() > 0) {
			photoStr = Base64.encodeBase64String(photoList.get(0).getDocData());
		}
		modelAndView.addObject("photoStr", photoStr);
		// end show img
		logger.info("invHits.size():" + invHits.size());
		logger.info("invHits_error.size():" + invHits_error.size());

		List<InvestigationHit> invHits_displayThis = null;
		if (invHits.size() > 0) {
			invHits_displayThis = invHits;
			modelAndView.addObject("inv_noHit", new Boolean(true));
		} else {
			invHits_displayThis = invHits_error;
			modelAndView.addObject("inv_noHit", new Boolean(true));
		}

		modelAndView.addObject("nicData", nicTransaction);
		modelAndView.addObject("invHits", invHits_displayThis);
		modelAndView.addObject("invHitsSize", invHits_displayThis.size());
		logger.info("computed:  invHits.size():" + invHits_displayThis.size());

		List<String> messages = new ArrayList<String>();

		if (listOfMessages != null) {
			messages.addAll(listOfMessages);
		}

		if (CollectionUtils.isNotEmpty(messages)) {
			httpRequest.setAttribute("messages", messages);
		}

		return modelAndView;
	}

	public class DataJson implements Serializable {

		public DataJson() {
		}

		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		@JsonProperty("passportNo")
		private String passportNo;
		@JsonProperty("passportYear")
		private String passportYear;
		@JsonProperty("transactionId")
		private String transactionId;

		public String getPassportNo() {
			return passportNo;
		}

		public void setPassportNo(String passportNo) {
			this.passportNo = passportNo;
		}

		public String getPassportYear() {
			return passportYear;
		}

		public void setPassportYear(String passportYear) {
			this.passportYear = passportYear;
		}

		public String getTransactionId() {
			return transactionId;
		}

		public void setTransactionId(String transactionId) {
			this.transactionId = transactionId;
		}

	}
	
	public class RequestDocument implements Serializable {

		public RequestDocument() {
		}

		@JsonProperty("document")
		private DocumentInfo document;

		public DocumentInfo getDocument() {
			return document;
		}

		public void setDocument(DocumentInfo document) {
			this.document = document;
		}
	}
	
	@RequestMapping(value = "/apiCheck", method = RequestMethod.POST)
	@ExceptionHandler
	@ResponseBody
	public String apiCheckTeca(
			@org.springframework.web.bind.annotation.RequestBody String response,
			HttpServletRequest httpRequest) throws JsonParseException,
			JsonMappingException, IOException {

		String resultMess = "Đã có lỗi xảy ra";
		List<String> messages = new LinkedList<String>();
		messages.add("Tạo danh sách bàn giao gửi sang Bộ công an để báo mất / hỏng thất bại.");
		try {

			DataJson jsonData = new DataJson();
			try {
				if (response.split("&")[0].split("=")[1] != null)
					jsonData.setTransactionId(response.split("&")[0].split("=")[1]);
			} catch (Exception ex) {
				httpRequest.setAttribute("messages", messages);
				return "Lỗi dữ liệu đầu vào...";
			}
			do {

				if (tokenA98 == "" || tokenA98 == null) {
					Map<String, String> resultToken = new HashMap<String, String>();
					try {
						resultToken = GetTokenAPI("a98", "a98");
					} catch (JsonParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (JsonMappingException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					if (resultToken != null) {
						tokenA98 = resultToken.get("access_token");
						/*
						 * expireTokenA98 = Integer.parseInt(resultToken
						 * .get("expires_in")); typeTokenA98 =
						 * resultToken.get("token_type");
						 */
					}
				}
				/*
				 * if (expireTokenA98 == 0 && tokenA98 != "") {
				 * ReAccessToken(tokenA98); }
				 */

			} while (tokenA98 == "");

			do {
				if (tokenBNG == "" || tokenBNG == null) {
					Map<String, String> resultToken = new HashMap<String, String>();
					try {
						resultToken = GetTokenAPI("bng", "bng");
					} catch (JsonParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (JsonMappingException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					if (resultToken != null) {
						tokenBNG = resultToken.get("access_token");
						/*
						 * expireTokenBNG = Integer.parseInt(resultToken
						 * .get("expires_in")); typeTokenBNG =
						 * resultToken.get("token_type");
						 */
					}
				}
				/*
				 * if (expireTokenBNG == 0 && tokenBNG != "") {
				 * ReAccessToken(tokenBNG); }
				 */

			} while (tokenBNG == "");

			String urlCountry = "epp$Country";
			String urlPlace = "epp$Place";
			String urlArea = "epp$Area";
			String urlOffice = "epp$Office";

			// /Lấy dữ liệu danh mục quốc gia
			List<CountryData> listCountry = GetDataUtilAPICountry(urlCountry,
					tokenA98, typeTokenA98);
			// /Lấy dữ liệu danh mục quốc gia
			List<PlaceData> listPlace = GetDataUtilAPIPlace(urlPlace, tokenA98,
					typeTokenA98);
			// /Lấy dữ liệu danh mục khu vực
			List<AreaData> listArea = GetDataUtilAPIArea(urlArea, tokenA98,
					typeTokenA98);
			// /Lấy dữ liệu danh mục cơ quan
			List<OfficeData> listOffice = GetDataUtilAPIOffice(urlOffice,
					tokenA98, typeTokenA98);

			NicTransaction nicTransaciton = nicTransactionService
					.getNicTransactionById(jsonData.getTransactionId()).getModel();
			Collection<NicDocumentData> collectionNicDocumentData = documentDataService
					.findByTransactionId(jsonData.getTransactionId()).getModel();
			NicDocumentData nicDocumentData = new NicDocumentData();
			for (NicDocumentData item : collectionNicDocumentData) {
				nicDocumentData = item;
			}
			NicRegistrationData nicRegistrationData = nicTransaciton
					.getNicRegistrationData();
			String[] typeDocAttach = {
					NicTransactionAttachment.DOC_TYPE_PHOTO_CAPTURE,
					NicTransactionAttachment.DOC_TYPE_FINGERPRINT,
			// NicTransactionAttachment.DOC_TYPE_PERSO,
			// NicTransactionAttachment.DOC_TYPE_PHOTO_CHIP,
			// NicTransactionAttachment.DOC_TYPE_PHOTO_CLI,
			// NicTransactionAttachment.DOC_TYPE_PHOTO_GREY,
			// NicTransactionAttachment.DOC_TYPE_REF_COL_SLIP,
			// NicTransactionAttachment.DOC_TYPE_THUMBNAIL_CAPTURE,
			// NicTransactionAttachment.DOC_TYPE_THUMBNAIL_CHIP,
			// NicTransactionAttachment.DOC_TYPE_THUMBNAIL_GREY,
			// NicTransactionAttachment.DOC_TYPE_SIGN_FP
			};
			List<NicTransactionAttachment> nicTransacitonAttachment = nicTransactionAttachmentService
					.getNicTransactionAttachments(jsonData.getTransactionId(),
							typeDocAttach, null);

			DocumentInfo modelDocument = new DocumentInfo();

			modelDocument.setCode(jsonData.getTransactionId());
			modelDocument.setRegistrationNo(jsonData.getTransactionId());
			String dateOfDelivery = nicTransaciton.getEstDateOfCollection()
					.toString().split(" ")[0];
			modelDocument.setDateOfDelivery(dateOfDelivery);
			String passportNo_ = nicDocumentData.getId().getPassportNo();
			// /Lay thong tin ho chieu ============
			PassportInformation ppTemp = new PassportInformation();

			String passportType = "";
			String ppTypeTmp = nicTransaciton.getPassportType().trim();
			if (ppTypeTmp.equals("P")) {
				passportType = "REGULAR";
			} else if (ppTypeTmp.equals("PO")) {
				passportType = "OFFICIAL";
			} else if (ppTypeTmp.equals("PD")) {
				passportType = "DIPLOMATIC";
			} else {
				passportType = "OTHER";
			}
			ppTemp.setPassportNo(passportNo_);
			ppTemp.setType(passportType);
			ppTemp.setIsPassport("Y");
			ppTemp.setFpEnCode(nicTransaciton.getNicRegistrationData()
					.getFpEncode());
			ppTemp.setChipSerialNo(nicDocumentData.getChipSerialNo());
			String doe_ = nicDocumentData.getDateOfExpiry().toString()
					.split(" ")[0];
			
			doe_ = doe_.split("-")[0] + "-" + doe_.split("-")[1] + "-"
					+ doe_.split("-")[2];
			ppTemp.setDateOfExpiry(doe_);
			String doi_ = nicDocumentData.getDateOfIssue().toString()
					.split(" ")[0];
			doi_ = doi_.split("-")[0] + "-" + doi_.split("-")[1] + "-"
					+ doi_.split("-")[2];
			ppTemp.setDateOfIssue(doi_);
			ppTemp.setSignerName(nicDocumentData.getCreateBy());
			ppTemp.setSignerPosition("Can bo xu ly");// Để tạm thời

			String oTemp_ = "BNG";
			String oTempName_ = "Bộ ngoại giao";
			String oTempSite_ = "MB";// Mặc định là từ Bộ ngoại giao do csdl
										// chưa đầy đủ
	/*		if (listOffice != null) {
				for (OfficeData item : listOffice) {
					if (item.getCode().contains(oTemp_)) {
						oTempName_ = item.getName();
						oTempSite_ = item.getSite();
						break;
					}
				}
			}*/
			PlaceData placeTmp = new PlaceData();
			placeTmp.setId("1");
			placeTmp.setCode("HN");
			placeTmp.setName("Hà Nội");
			AreaData areaTmp = new AreaData();
			areaTmp.setId("1");
			areaTmp.setCode("");
			areaTmp.setName("Q. Ba Đình");
			OfficeData officeTmp = new OfficeData();
			officeTmp.setActive(false);
			officeTmp.setName(oTempName_);
			officeTmp.setCode(oTemp_);
			officeTmp.setSite(oTempSite_);
			ppTemp.setPlaceOfIssue(officeTmp);
			ppTemp.setStatus("ACTIVATED");
			modelDocument.setPassport(ppTemp);

			// //===========================

			// /Lay thong tin cong dan ==============
			PersonInformation personTmp = new PersonInformation();
			String fullName_ = nicRegistrationData.getSurnameFull() + " "
					+ nicRegistrationData.getMiddlenameFull() + " "
					+ nicRegistrationData.getFirstnameFull();

			personTmp.setName(fullName_);
			String gender_ = "UNKNOWN";
			String genderTmp_ = nicRegistrationData.getGender();
			if (genderTmp_.equals("M")) {
				gender_ = "MALE";
			} else if (genderTmp_.equals("F")) {
				gender_ = "FEMALE";
			}
			personTmp.setGender(gender_);
			String dob_ = nicRegistrationData.getDateOfBirth().toString()
					.split(" ")[0];
			dob_ = dob_.split("-")[0] + "-" + dob_.split("-")[1] + "-"
					+ dob_.split("-")[2];
			personTmp.setDateOfBirth(dob_);
			String idPlaceOfBirth_ = "202";// /Mặc định là Hà Nội do chưa đủ dữ
											// liệu
			String oTemppob_ = "BNG";
			String oTempNamepob_ = "Bộ ngoại giao";
			String oTempSitepob_ = "MB";// Mặc định là từ Bộ ngoại giao do csdl
										// chưa đầy đủ
			if (listPlace != null) {
				for (PlaceData item : listPlace) {
					if (item.getCode().contains(
							nicRegistrationData.getPlaceOfBirth())) {
						break;
					}
				}
			}
			
			CountryData coutryTmp = new CountryData();
			coutryTmp.setId("542");
			coutryTmp.setName("Vietnam");
			personTmp.setNationality(coutryTmp);
			personTmp.setResidentAddress(nicTransaciton
					.getNicRegistrationData().getAddressLine1());
			PlaceData placeTmp_ = new PlaceData();
			placeTmp_.setActive(false);
			placeTmp_.setName(oTempName_);
			placeTmp_.setCode(oTemp_);
			ppTemp.setPlaceOfIssue(officeTmp);
			personTmp.setPlaceOfBirth(placeTmp);
			personTmp.setIdNumber(nicTransaciton.getNin());
			personTmp.setResidentArea(areaTmp);
			personTmp.setResidentPlace(placeTmp);
			personTmp.setPlaceOfIdIssue(officeTmp);
			modelDocument.setPerson(personTmp);
			// //====================================

			// /Thong tin tai lieu di kem (anh mat va van tay)========
			DocAttachment docAtt = new DocAttachment();
			PersonAttachment perAtt = new PersonAttachment();
			List<AttachmentDoc> listAttachment = new LinkedList<AttachmentDoc>();
			if (nicTransacitonAttachment != null) {
				for (NicTransactionAttachment item : nicTransacitonAttachment) {
					AttachmentDoc attachmentDoc = new AttachmentDoc();
					String typeDoc_ = "OTHER"; // / Tạm thời các tài liệu gửi
												// kèm để chung là OTHER
					Integer serialNo = 0;
					if (item.getDocType().contains("FP")) {
						typeDoc_ = "FP_WSQ";//FINGERPRINT
					} else if (item.getDocType().contains("SCANDOCUMENT")) {
						typeDoc_ = "SCAN_DOCUMENT";
					} else if (item.getDocType().contains("SCANDOCUMENT")) {
						typeDoc_ = "PERSO";
					} else if (item.getDocType().contains("PH_CAP")
							|| item.getDocType().contains("PH_CHIP")
							|| item.getDocType().contains("PH_CLI")
							|| item.getDocType().contains("PH_GREY")
							|| item.getDocType().contains("TH_CAP")
							|| item.getDocType().contains("TH_CHIP")
							|| item.getDocType().contains("TH_GREY")) {
						typeDoc_ = "PH";//PHOTO
					}

					/*attachmentDoc.setFileName(item.getDocType() + "_"
							+ item.getSerialNo());*/
					byte[] encodeByte = Base64.encodeBase64(item.getDocData());
					String encoded = new String(encodeByte, "US-ASCII");
					attachmentDoc.setBase64(encoded);
					serialNo = Integer.parseInt(item.getSerialNo());
					attachmentDoc.setSerialNo(serialNo);
					attachmentDoc.setType(typeDoc_);
					listAttachment.add(attachmentDoc);
					
				}
			}
			docAtt.setDocattachments(listAttachment);
			perAtt.setPerattachments(listAttachment);
			modelDocument.setPerattachment(perAtt);
			modelDocument.setDocattachment(docAtt);
			// //===================================

			if (nicRegistrationData.getContactNo() != ""
					&& nicRegistrationData.getContactNo() != null) {
				modelDocument.setPhoneNo(nicRegistrationData.getContactNo());
			} else {
				modelDocument.setPhoneNo("0");
			}

			modelDocument.setIsEpassport("Y");// Mặc định đồng bộ dữ liệu mới từ NIC
			String priority_ = "NORMAL";
			if (nicTransaciton.getPriority() != null) {
				if (nicTransaciton.getPriority() == 1) {
					priority_ = "HIGH";
				} else if (nicTransaciton.getPriority() == 2) {
					priority_ = "HIGHEST";
				}
			}

			modelDocument.setPriority(priority_);
			String typeTransaction_ = "NEW";
			if (nicTransaciton.getTransactionType().contains("REP")) {
				typeTransaction_ = "RENEW_BY_LOST";
			} else if (nicTransaciton.getTransactionType() != "REP"
					&& nicTransaciton.getTransactionType() != "REG") {
				typeTransaction_ = "OTHER";
			}
			modelDocument.setType(typeTransaction_);

			OfficeData officeTmp_ = new OfficeData();
			officeTmp_.setCode("BNG");
			officeTmp_.setName("Bộ ngoại giao");
			modelDocument.setOffice(officeTmp_);
			modelDocument.setStatus(DocumentInfo.ISSUING);
			// /TEST API ĐỒNG BỘ DỮ LIỆU HC
			RequestDocument requestDocument = new RequestDocument();
			requestDocument.setDocument(modelDocument);
			String jsonModel = ConvertDataJson(modelDocument);
			String requestUrl = "http://222.252.27.89:8044/app/rest/v2/services/epp_DocumentService/syncWithoutProcessing";
			Boolean resultSync = sendPostRequestSync(requestUrl, jsonModel,
					tokenBNG, typeTokenBNG);

			if (!resultSync) {
				//messages.add("Tạo danh sách bàn giao gửi sang Bộ công an để báo mất / hỏng thành công.");
				resultMess = "Gui du lieu sang Bo cong an thanh cong!";
				try{
					
					//Cập nhật trạng thái đồng bộ dữ liệu
					///nicTransaciton.setSyncPassport(1);
					nicTransactionService.saveOrUpdateTransaction(nicTransaciton);
				}catch(Exception ex){
					
				}
			}
		} catch (Exception ex) {
			resultMess += ". [[Error]]: " + ex.toString();
		}
		return resultMess;
	}
	
	private String ConvertDataJson(DocumentInfo data)
			throws JsonGenerationException, JsonMappingException, IOException {
		ObjectMapper mapper = new ObjectMapper();
		String json = mapper.writeValueAsString(data);
		return json;
	}
	
	// /Đồng bộ dữ liệu hộ chiếu (Dữ liệu đơn)
		public Boolean sendPostRequestSync(String requestUrl, String data,
				String token, String typeToken) {
			StringBuffer jsonString = new StringBuffer();
			try {
				URL url = new URL(requestUrl);
				HttpURLConnection connection = (HttpURLConnection) url
						.openConnection();

				connection.setDoInput(true);
				connection.setDoOutput(true);
				connection.setRequestMethod("POST");
				connection.setRequestProperty("Authorization", typeToken.trim()
						+ " " + token.trim());
				connection.setRequestProperty("Content-Type",
						"application/json; charset=UTF-8");
				OutputStreamWriter writer = new OutputStreamWriter(
						connection.getOutputStream(), "UTF-8");
				writer.write(data);
				writer.close();

				int statusCode = connection.getResponseCode();
				if (statusCode == 200 || statusCode == 201) {

					InputStream content = connection.getInputStream();
					InputStreamReader isr = new InputStreamReader(content);

					int numCharsRead;
					char[] charArray = new char[1024];
					StringBuffer sb = new StringBuffer();
					while ((numCharsRead = isr.read(charArray)) > 0) {
						sb.append(charArray, 0, numCharsRead);
					}
					String st = sb.toString();
					return true;
					/*
					 * BufferedReader br = new BufferedReader(new InputStreamReader(
					 * connection.getInputStream())); String line; while ((line =
					 * br.readLine()) != null) { jsonString.append(line); }
					 * br.close(); connection.disconnect();
					 */
				}
			} catch (Exception e) {
				
			}
			return false;
		}
	
	@ExceptionHandler
	public Map<String, String> GetTokenAPI(String username, String password)
			throws JsonParseException, JsonMappingException, IOException {
		Map<String, String> accessToken = new HashMap<String, String>();

		String urlParameters = "username=" + username + "&password=" + password;
		/*
		 * String encoding = Base64.encodeBase64String(("epp:epp12#")
		 * .getBytes("UTF-8"));
		 */

		byte[] postData = urlParameters.getBytes(StandardCharsets.UTF_8);
		int postDataLength = postData.length;
		URL url = new URL("http://222.252.27.89:8044/app/rest/v2/oauth/token?"
				+ urlParameters);
		HttpURLConnection connection = (HttpURLConnection) url.openConnection();
		/*
		 * connection.setRequestMethod("POST");
		 * connection.setRequestProperty("Authorization", "Bearer " +
		 * encoding.trim()); connection.setRequestProperty("Content-Type",
		 * "application/x-www-form-urlencoded"); connection.connect();
		 */

		connection.setDoOutput(true);
		connection.setInstanceFollowRedirects(false);
		connection.setRequestMethod("POST");
		connection.setRequestProperty("Content-Type",
				"application/x-www-form-urlencoded");

		connection.setRequestProperty("Content-Length",
				Integer.toString(postDataLength));
		connection.setUseCaches(false);
		try (DataOutputStream wr = new DataOutputStream(
				connection.getOutputStream())) {
			wr.write(postData);
		}

		int statusCode = connection.getResponseCode();
		if (statusCode == 200) {
			InputStream content = connection.getInputStream();
			InputStreamReader isr = new InputStreamReader(content);

			int numCharsRead;
			char[] charArray = new char[1024];
			StringBuffer sb = new StringBuffer();
			while ((numCharsRead = isr.read(charArray)) > 0) {
				sb.append(charArray, 0, numCharsRead);
			}
			String st = sb.toString();
			JSONObject myResponse = new JSONObject(st);
			JSONObject arrayResp = myResponse.getJSONObject("data");
			accessToken.put("access_token", arrayResp.getString("token"));
			/*
			 * for (int i = 0; i < arrayResp.length(); i++) { JSONObject jk =
			 * new JSONObject(); jk = arrayResp.getJSONObject(i);
			 * accessToken.put("access_token", jk.getString("token")); }
			 */

			/*
			 * Integer expires_in = myResponse.getInt("expires_in");
			 * accessToken.put("expires_in", expires_in.toString());
			 * accessToken.put("token_type",
			 * myResponse.getString("token_type")); accessToken.put("scope",
			 * myResponse.getString("scope"));
			 */
		}

		return accessToken;
	}
	
	public List<CountryData> GetDataUtilAPICountry(String urlParameters,
			String token, String typeToken) throws JsonParseException,
			JsonMappingException, IOException {
		List<CountryData> listResult = new LinkedList<CountryData>();

		URL url = new URL("http://222.252.27.89:8044/app/rest/v2/entities/"
				+ urlParameters);
		HttpURLConnection connection = (HttpURLConnection) url.openConnection();
		connection.setRequestMethod("GET");
		connection.setRequestProperty("Authorization", typeToken.trim() + " "
				+ token.trim());
		connection.setRequestProperty("Content-Type", "application/json");
		connection.connect();

		int statusCode = connection.getResponseCode();
		if (statusCode == 200) {
			InputStream content = connection.getInputStream();
			Charset charset = Charset.forName("UTF8");
			InputStreamReader isr = new InputStreamReader(content, charset);

			int numCharsRead;
			char[] charArray = new char[1024];
			StringBuffer sb = new StringBuffer();
			while ((numCharsRead = isr.read(charArray)) > 0) {
				sb.append(charArray, 0, numCharsRead);
			}
			String st = sb.toString();
			JSONArray arrayResp = new JSONArray(st);

			for (int i = 0; i < arrayResp.length(); i++) {

				JSONObject myResponse = new JSONObject();
				myResponse = arrayResp.getJSONObject(i);

				CountryData map = new CountryData();

				map.setName(getMessageFromServerString(myResponse, "name"));
				Integer numCode = getMessageFromServerInt(myResponse, "numCode");
				map.setNumCode(numCode.toString());
				map.setAlpha2Code(getMessageFromServerString(myResponse,
						"alpha2Code"));
				map.setAlpha3Code(getMessageFromServerString(myResponse,
						"alpha3Code"));
				map.setNationality(getMessageFromServerString(myResponse,
						"nationality"));

				if (map != null) {
					listResult.add(map);
				}
			}
		} else {
			// /Trả về log mã lỗi và thông tin lỗi

		}
		return listResult;
	}

	public List<PlaceData> GetDataUtilAPIPlace(String urlParameters,
			String token, String typeToken) throws JsonParseException,
			JsonMappingException, IOException {
		List<PlaceData> listResult = new LinkedList<PlaceData>();

		URL url = new URL("http://222.252.27.89:8044/app/rest/v2/entities/"
				+ urlParameters);
		HttpURLConnection connection = (HttpURLConnection) url.openConnection();
		connection.setRequestMethod("GET");
		connection.setRequestProperty("Authorization", typeToken.trim() + " "
				+ token.trim());
		connection.setRequestProperty("Content-Type", "application/json");
		connection.connect();

		int statusCode = connection.getResponseCode();
		if (statusCode == 200) {
			InputStream content = connection.getInputStream();
			Charset charset = Charset.forName("UTF8");
			InputStreamReader isr = new InputStreamReader(content, charset);

			int numCharsRead;
			char[] charArray = new char[1024];
			StringBuffer sb = new StringBuffer();
			while ((numCharsRead = isr.read(charArray)) > 0) {
				sb.append(charArray, 0, numCharsRead);
			}
			String st = sb.toString();
			JSONArray arrayResp = new JSONArray(st);

			for (int i = 0; i < arrayResp.length(); i++) {

				JSONObject myResponse = new JSONObject();
				myResponse = arrayResp.getJSONObject(i);

				PlaceData map = new PlaceData();

				map.setName(getMessageFromServerString(myResponse, "name"));
				map.setCode(getMessageFromServerString(myResponse, "code"));
				map.setActive(getMessageFromServerBoolean(myResponse, "active"));

				if (map != null) {
					listResult.add(map);
				}
			}
		} else {
			// /Trả về log mã lỗi và thông tin lỗi

		}
		return listResult;
	}

	public List<AreaData> GetDataUtilAPIArea(String urlParameters,
			String token, String typeToken) throws JsonParseException,
			JsonMappingException, IOException {
		List<AreaData> listResult = new LinkedList<AreaData>();

		URL url = new URL("http://222.252.27.89:8044/app/rest/v2/entities/"
				+ urlParameters);
		HttpURLConnection connection = (HttpURLConnection) url.openConnection();
		connection.setRequestMethod("GET");
		connection.setRequestProperty("Authorization", typeToken.trim() + " "
				+ token.trim());
		connection.setRequestProperty("Content-Type", "application/json");
		connection.connect();

		int statusCode = connection.getResponseCode();
		if (statusCode == 200) {
			InputStream content = connection.getInputStream();
			Charset charset = Charset.forName("UTF8");
			InputStreamReader isr = new InputStreamReader(content, charset);

			int numCharsRead;
			char[] charArray = new char[1024];
			StringBuffer sb = new StringBuffer();
			while ((numCharsRead = isr.read(charArray)) > 0) {
				sb.append(charArray, 0, numCharsRead);
			}
			String st = sb.toString();
			JSONArray arrayResp = new JSONArray(st);

			for (int i = 0; i < arrayResp.length(); i++) {

				JSONObject myResponse = new JSONObject();
				myResponse = arrayResp.getJSONObject(i);

				AreaData map = new AreaData();

				map.setName(getMessageFromServerString(myResponse, "name"));
				map.setActive(getMessageFromServerBoolean(myResponse, "active"));

				if (map != null) {
					listResult.add(map);
				}
			}
		} else {
			// /Trả về log mã lỗi và thông tin lỗi

		}
		return listResult;
	}

	public List<OfficeData> GetDataUtilAPIOffice(String urlParameters,
			String token, String typeToken) throws JsonParseException,
			JsonMappingException, IOException {
		List<OfficeData> listResult = new LinkedList<OfficeData>();

		URL url = new URL("http://222.252.27.89:8044/app/rest/v2/entities/"
				+ urlParameters);
		HttpURLConnection connection = (HttpURLConnection) url.openConnection();
		connection.setRequestMethod("GET");
		connection.setRequestProperty("Authorization", typeToken.trim() + " "
				+ token.trim());
		connection.setRequestProperty("Content-Type", "application/json");
		connection.connect();

		int statusCode = connection.getResponseCode();
		if (statusCode == 200) {
			InputStream content = connection.getInputStream();
			Charset charset = Charset.forName("UTF8");
			InputStreamReader isr = new InputStreamReader(content, charset);

			int numCharsRead;
			char[] charArray = new char[1024];
			StringBuffer sb = new StringBuffer();
			while ((numCharsRead = isr.read(charArray)) > 0) {
				sb.append(charArray, 0, numCharsRead);
			}
			String st = sb.toString();
			JSONArray arrayResp = new JSONArray(st);

			for (int i = 0; i < arrayResp.length(); i++) {

				JSONObject myResponse = new JSONObject();
				myResponse = arrayResp.getJSONObject(i);

				OfficeData map = new OfficeData();

				map.setName(getMessageFromServerString(myResponse, "name"));
				map.setCode(getMessageFromServerString(myResponse, "code"));
				map.setActive(getMessageFromServerBoolean(myResponse, "active"));
				map.setAddress(getMessageFromServerString(myResponse, "address"));

				if (map != null) {
					listResult.add(map);
				}
			}
		} else {
			// /Trả về log mã lỗi và thông tin lỗi

		}
		return listResult;
	}
	// / END

	private void prepareModelStuff(Model model) {
		model.addAttribute("fnSelected", Constants.HEADING_NIC_INVESTIGATION);
	}
	
	public void initializeModelAndViewForms(ModelAndView mav) {
		mav.addObject("investigationHitData", new InvestigationHitData());
	}
	
	private void initializeModelAndViewCommonPageDetails(
			ModelAndView modelAndView, NicUploadJob jobDetails,
			String mainTransactionId) throws NicUploadJobServiceException {
		// formatdate
		String jobUploadTime = null;

		// date of application
		NicTransaction nicTransaction = nicTransactionService
				.findById(jobDetails.getTransactionId());
		if (nicTransaction != null) {
			jobUploadTime = DateUtil.parseDate(
					nicTransaction.getDateOfApplication(), "dd-MMM-yyyy");
		}
		modelAndView.addObject("jobUploadTime", jobUploadTime);

		String jobType = null;
		if (jobDetails != null
				&& StringUtils.isNotBlank(jobDetails.getJobType())) {
			CodeValues codeValue = codesService.getCodeValueByIdName(
					Codes.JOB_TYPE, jobDetails.getJobType());

			if (codeValue != null) {
				jobType = codeValue.getCodeValueDesc();
			} else {
				jobType = jobDetails.getJobType();
			}
		}
		modelAndView.addObject("jobType", jobType);
		modelAndView.addObject("jobDetails", jobDetails);
	}
	
	public List<SearchHitDto> getAllHits(long jobId, String mainTransactionId)
			throws NicUploadJobServiceException {

		// List<HitCandidateDTO> allHitsForJobId =
		// uploadJobService.getAfisHitCandidateListByJobId(jobId);
		return uploadJobService
				.getAllHitsForJobId_1PerHitTransactionId_anySearchResultId(jobId);
	}
	
	public List<InvestigationHit> getAllHitsForModelAndView(
			HttpServletRequest httpRequest, ModelAndView modelAndView,
			NicUploadJob jobDetails, String mainTransactionId,
			List<SearchHitDto> hits) {

		List<InvestigationHit> invHits = new ArrayList<InvestigationHit>();

		try {
			InvestigationData investigationData = this.getInvestigationData();
			InvestigationHit invHit_saveTime_mainCandidateInfo = null;

			for (SearchHitDto hit : hits) {
				try {
					InvestigationHit invHit = new InvestigationHit();
					{
						if (invHit_saveTime_mainCandidateInfo == null) {
							this.put1HitIntoModelAndView_main(httpRequest,
									modelAndView, jobDetails,
									mainTransactionId, investigationData,
									invHit);
							{
								invHit_saveTime_mainCandidateInfo = new InvestigationHit();
								BeanUtils.copyProperties(invHit,
										invHit_saveTime_mainCandidateInfo);
							}
						} else {
							BeanUtils.copyProperties(
									invHit_saveTime_mainCandidateInfo, invHit);
						}
					}

					{
						this.put1HitIntoModelAndView_hit(modelAndView,
								jobDetails, mainTransactionId,
								hit.getSearchResultId(),
								hit.getHitTransactionId(), investigationData,
								invHit, hit.getSearchResult_typeSearch(),
								hit.getSearchHitResult_hitInfo());
					}

					invHits.add(invHit);
				} catch (Exception e) {
					e.printStackTrace();
					continue;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return invHits;
	}
	
	private void put1HitIntoModelAndView_main(HttpServletRequest httpRequest,
			ModelAndView modelAndView, NicUploadJob jobDetails,
			String mainTransactionId, InvestigationData investigationData,
			InvestigationHit invHit) throws Exception,
			TransactionServiceException {

		/*
		 * MAIN - START
		 */
		{
			String mainCandidatePhoto = null;
			String mainCandidateSignature = null;

			// Main Candidate Photo, Signature and Finger prints.
			List<NicTransactionAttachment> mainCanPh = nicTransactionAttachmentService
					.getNicTransactionAttachments(
							mainTransactionId,
							new String[] { NicTransactionAttachment.DOC_TYPE_PHOTO_CAPTURE },
							new String[] { NicTransactionAttachment.DEFAULT_SERIAL_NO });
			// Main Candidate Photo
			if (CollectionUtils.isNotEmpty(mainCanPh)) {
				byte[] photoBinary = this.handleJp2(mainCanPh.get(0)
						.getDocData());
				mainCandidatePhoto = new Base64Jp2HeaderHelper()
						.getBase64WithJp2Header(photoBinary);
			}

			// Main Candidate Finger prints
			Map<String, byte[]> mainFpMap = new HashMap<String, byte[]>();
			Map<String, String> mainFpIndicatorMap = new HashMap<String, String>();

			List<NicTransactionAttachment> mainCanFp = nicTransactionAttachmentService
					.getNicTransactionAttachments(
							mainTransactionId,
							new String[] { NicTransactionAttachment.DOC_TYPE_FINGERPRINT },
							null);

			if (CollectionUtils.isNotEmpty(mainCanFp)) {
				for (NicTransactionAttachment record : mainCanFp) {
					mainFpMap.put(this.fixSerialNo(record.getSerialNo()),
							record.getDocData());
				}
			}

			if (CollectionUtils.isNotEmpty(mainCanFp)) {
				for (NicTransactionAttachment record : mainCanFp) {
					mainFpMap.put(record.getSerialNo(), record.getDocData());
				}
			}

			// Main Candidate Details
			NicTransaction mainCandidateListNin = nicTransactionService
					.findById(mainTransactionId);
			if (mainCandidateListNin != null) {

				invHit.addObject("mainCandidatePreviousPassportNumber",
						mainCandidateListNin.getPrevPassportNo());
				invHit.addObject("mainCandidatePreviousPassportIssueDate",
						DateUtil.parseDate(
								mainCandidateListNin.getPrevDateOfIssue(),
								"dd-MMM-yyyy"));

				// mainCandidateListNin.getNin();
				mainCandidateListNin = nicTransactionService
						.getTransactionRegistrationData(mainCandidateListNin);

				NicRegistrationData nicRegistrationData = mainCandidateListNin
						.getNicRegistrationData();

				invHit.addObject("mainCandidateDateOfApplication", DateUtil
						.parseDate(mainCandidateListNin.getDateOfApplication(),
								"dd-MMM-yyyy"));
				invHit.addObject("mainCandidateIssuingAuthority",
						this.eppSiteService.getSiteRepoAuthority(
								mainCandidateListNin.getIssuingAuthority(),
								mainCandidateListNin.getIssuingAuthority()));

				invHit.addObject("mainCandidateReleaseDate", DateUtil
						.parseDate(
								mainCandidateListNin.getEstDateOfCollection(),
								"dd-MMM-yyyy"));
				this.processOneReferringToCodeTable(mainCandidateListNin
						.getPriority().toString(), Codes.PRIORITY,
						"mainCandidatePriority", invHit);

				this.processOneReferringToCodeTable(
						mainCandidateListNin.getTransactionType(),
						Codes.TRANSACTION_TYPE, "mainCandidateTransactionType",
						invHit);
				this.processOneReferringToCodeTable(
						mainCandidateListNin.getTransactionStatus(),
						Codes.TRANSACTION_STATUS,
						"mainCandidateApplicationPassportStatus", invHit);
				this.processOneReferringToCodeTable(
						nicRegistrationData.getNationality(),
						Codes.NATIONALITY, "mainCandidateNationality", invHit);

				this.processMainCandidateAttachments(httpRequest,
						mainTransactionId, invHit);

				// Main Candidate FP Quality
				Map<Integer, FingerprintInfo> mainFpQua = new TreeMap<Integer, FingerprintInfo>();
				String mainFpQuality = mainCandidateListNin
						.getNicRegistrationData().getFpQuality();
				if (mainFpQuality != null) {
					String mainFpQualityArry[] = mainFpQuality.split(",");
					if (mainFpQualityArry != null) {
						for (int i = 0; i < mainFpQualityArry.length; ++i) {
							FingerprintInfo info = new FingerprintInfo();
							String matchFpArry[] = mainFpQualityArry[i]
									.split("-");

							info.setFpQuaScr(Integer.valueOf(matchFpArry[1]));
							info.setGoodFPQuaScr(investigationData.goodFpQuasMap
									.get(Integer.valueOf(matchFpArry[0])));
							info.setAccetableFPQuaScr(investigationData.acceptFpQuasMap
									.get(Integer.valueOf(matchFpArry[0])));

							mainFpQua
									.put(Integer.valueOf(matchFpArry[0]), info);
							logger.info("The main candidate fp Quality "
									+ mainFpQua);
						}
					}
				}

				// Main Candidate FP Verify
				Map<Integer, Integer> mainFpVeri = new TreeMap<Integer, Integer>();

				// Main Candidate FP Encode
				Map<Integer, Double> mainFpEnc = new TreeMap<Integer, Double>();
				String mainFpEncode = mainCandidateListNin
						.getNicRegistrationData().getFpEncode();
				if (mainFpEncode != null) {
					String mainFpEncodeArry[] = mainFpEncode.split(",");
					if (mainFpEncodeArry != null) {
						for (int i = 0; i < mainFpEncodeArry.length; ++i) {
							mainFpEnc.put(Integer.valueOf(mainFpEncodeArry[i]),
									Double.valueOf(mainFpEncodeArry[i]));
							logger.info("The main candidate fp encode "
									+ mainFpEnc);
						}
					}
				}

				if (nicRegistrationData != null) {

					invHit.addObject("mainCandidateAlsoKnownAs",
							nicRegistrationData.getAliasName());
					invHit.addObject("mainCandidateLimitation",
							nicRegistrationData.getPrintRemarks1());
					invHit.addObject("mainCandidatePosition",
							nicRegistrationData.getOccupationDesc());

					if (StringUtils.isNotBlank(nicRegistrationData
							.getFpIndicator())) {
						String[] fpIndicatorMain = nicRegistrationData
								.getFpIndicator().split(",");
						if (fpIndicatorMain.length > 0) {
							for (String mainInd : fpIndicatorMain) {
								mainFpIndicatorMap.put(mainInd.split("-")[0],
										mainInd.split("-")[1]);
							}
						}
					}

					List<NicTransactionAttachment> mainCanSig = nicTransactionAttachmentService
							.getNicTransactionAttachments(
									mainTransactionId,
									new String[] { NicTransactionAttachment.DOC_TYPE_SIGNATURE },
									new String[] { NicTransactionAttachment.DEFAULT_SERIAL_NO });
					// Main Candidate Signature
					if (CollectionUtils.isNotEmpty(mainCanSig)) {
						byte[] signBinary = this.handleJp2(mainCanSig.get(0)
								.getDocData());
						mainCandidateSignature = new Base64Jp2HeaderHelper()
								.getBase64WithJp2Header(signBinary);
						// mainCandidateSignature = signBinary;
					}

					String mainCandidateFN = nicRegistrationData
							.getFirstnameFull();
					String mainCandidateFNShort = null;
					if (StringUtils.isNotBlank(mainCandidateFN)) {
						if (mainCandidateFN.length() > 15) {
							mainCandidateFNShort = mainCandidateFN.substring(0,
									14) + " ...";
						} else {
							mainCandidateFNShort = mainCandidateFN;
						}
					}

					String mainCandidateSN = nicRegistrationData
							.getSurnameFull();
					String mainCandidateSNShort = null;
					if (StringUtils.isNotBlank(mainCandidateSN)) {
						if (mainCandidateSN.length() > 15) {
							mainCandidateSNShort = mainCandidateSN.substring(0,
									14) + " ...";
						} else {
							mainCandidateSNShort = mainCandidateSN;
						}
					}

					String mainCandidateMN = nicRegistrationData
							.getMiddlenameFull();
					String mainCandidateMNShort = null;
					if (StringUtils.isNotBlank(mainCandidateMN)) {
						if (mainCandidateMN.length() > 15) {
							mainCandidateMNShort = mainCandidateMN.substring(0,
									14) + " ...";
						} else {
							mainCandidateMNShort = mainCandidateMN;
						}
					}

					Date mainCandidateDOB = nicRegistrationData
							.getDateOfBirth();
					String mainCandDOB = null;
					if (mainCandidateDOB != null) {
						mainCandDOB = DateUtil.parseDate(mainCandidateDOB,
								"dd-MMM-yyyy");
					}
					String mainCandidateFlatNo = nicRegistrationData
							.getAddressLine1();
					String mainCandiadteStreetName = nicRegistrationData
							.getAddressLine2();
					String mainCandidateLocality = nicRegistrationData
							.getAddressLine3();

				/*	String mainCandidateFathersN = nicRegistrationData
							.getFatherFirstname();
					String mainCandidateFathersSN = nicRegistrationData
							.getFatherSurname();
					String mainCandidateFathersMN = nicRegistrationData
							.getFatherMiddlename();
					String mainCandidateMothersN = nicRegistrationData
							.getMotherFirstname();
					String mainCandidateMothersSN = nicRegistrationData
							.getMotherSurname();
					String mainCandidateMothersMN = nicRegistrationData
							.getMotherMiddlename();
					String mainCandidateSpouseFN = nicRegistrationData
							.getSpouseFirstname();
					String mainCandidateSpouseSN = nicRegistrationData
							.getSpouseSurname();
					String mainCandidateSpouseMN = nicRegistrationData
							.getSpouseMiddlename();*/

					invHit.addObject("mainCandidatePlaceOfBirth",
							nicRegistrationData.getPlaceOfBirth());
					invHit.addObject("mainCandidateFN", mainCandidateFN);
					invHit.addObject("mainCandidateFNShort",
							mainCandidateFNShort);
					invHit.addObject("mainCandidateSN", mainCandidateSN);
					invHit.addObject("mainCandidateMN", mainCandidateMN);
					invHit.addObject("mainCandidateSNShort",
							mainCandidateSNShort);
					invHit.addObject("mainCandidateMNShort",
							mainCandidateMNShort);
					this.processOneReferringToCodeTable(
							nicRegistrationData.getGender(), Codes.GENDER,
							"mainCandidateGender", invHit);
					invHit.addObject("mainCandidateDOB", mainCandDOB);
					invHit.addObject("mainCandidateFlatNo", mainCandidateFlatNo);
					invHit.addObject("mainCandiadteStreetName",
							mainCandiadteStreetName);
					invHit.addObject("mainCandidateLocality",
							mainCandidateLocality);
					this.processOneReferringToCodeTable(
							nicRegistrationData.getMaritalStatus(),
							Codes.MARITAL_STATUS, "mainCandidateMaritalStatus",
							invHit);
					/*invHit.addObject("mainCandidateFathersN",
							mainCandidateFathersN);
					invHit.addObject("mainCandidateFathersSN",
							mainCandidateFathersSN);
					invHit.addObject("mainCandidateFathersMN",
							mainCandidateFathersMN);
					invHit.addObject("mainCandidateMothersN",
							mainCandidateMothersN);
					invHit.addObject("mainCandidateMothersSN",
							mainCandidateMothersSN);
					invHit.addObject("mainCandidateMothersMN",
							mainCandidateMothersMN);
					invHit.addObject("mainCandidateSpouseFN",
							mainCandidateSpouseFN);
					invHit.addObject("mainCandidateSpouseSN",
							mainCandidateSpouseSN);
					invHit.addObject("mainCandidateSpouseMN",
							mainCandidateSpouseMN);*/
					invHit.addObject("mainCandidateFpQuality", mainFpQua);
					invHit.addObject("mainCandidateFpEncode", mainFpEnc);
					invHit.addObject("mainCandidateFpVerify", mainFpVeri);

				}
			}

			invHit.addObject("mainCandidatePhoto", mainCandidatePhoto);
			invHit.addObject("mainCandidateSignature", mainCandidateSignature);
			invHit.addObject("mainFpMap", mainFpMap);
			invHit.addObject("mainFpIndicatorMap", mainFpIndicatorMap);
		}
		/*
		 * 1111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111
		 * MAIN - END
		 * 1111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111
		 */

	}
	
	public List<InvestigationHit> getAllHits_error(
			List<InvestigationHit> invHits_all) {

		List<InvestigationHit> hits = new ArrayList<InvestigationHit>();

		if (CollectionUtils.isEmpty(invHits_all)) {
			return hits;
		}

		for (InvestigationHit hit : invHits_all) {
			if (!(Collections.disjoint(Arrays
					.asList(new String[] { ((String) hit
							.getHitCandidate_searchResult_typeSearch()) }),
					NicSearchResult.TYPE_SEARCH__THAT_ARE_MAIN_CANDIDATE_BASED))) {
				hits.add(hit);
			}
		}

		return hits;
	}
	
	public void processMainCandidateInvestigationInformation(
			List<InvestigationHit> invHits, List<InvestigationHit> invHits_error)
			throws Exception {

		if (CollectionUtils.isEmpty(invHits)) {
			return;
		}

		if (CollectionUtils.isEmpty(invHits_error)) {
			return;
		}

		List<NicSearchHitResultHitInfoItem> mainCandidateInvestigationInformation = this
				.getMainCandidateInvestigationInformation(invHits_error);
		for (InvestigationHit hit : invHits) {
			hit.setMainCandidateInvestigationInformation(mainCandidateInvestigationInformation);
		}
	}
	
	private List<NicSearchHitResultHitInfoItem> getMainCandidateInvestigationInformation(
			List<InvestigationHit> invHits_error) throws Exception {

		if (CollectionUtils.isEmpty(invHits_error)) {
			return null;
		}

		String hitInfo = (String) (invHits_error.get(0)
				.getHitCandidate_searchHitResult_hitInfo());

		if (StringUtils.isBlank(hitInfo)) {
			return null;
		}

		String itemsLevel1[] = hitInfo
				.split(NicSearchHitResult.hitInfo__ITEM__DELIMITER);
		List<NicSearchHitResultHitInfoItem> itemsForDisplay = new ArrayList<NicSearchHitResultHitInfoItem>();

		if (itemsLevel1 != null && itemsLevel1.length > 0) {
			for (String groupEntry : itemsLevel1) {
				String groupEntryTitle = StringUtils.substringBefore(
						groupEntry,
						NicSearchHitResult.hitInfo__SUB_ITEM__GROUP__DELIMITER);

				itemsForDisplay.add(new NicSearchHitResultHitInfoItem(
						this.codesService.getCodeValueDescByIdName(
								Codes.SRCH_HIT_RESULT__ITEM, groupEntryTitle,
								groupEntryTitle), this
								.getTextualHitInfoGroupItems(groupEntry)));
			}
		}

		return itemsForDisplay;
	}
	
	private List<NicSearchHitResultHitInfoItem> getTextualHitInfoGroupItems(
			String groupEntry) throws Exception {

		List<NicSearchHitResultHitInfoItem> subItemsForDisplay = new ArrayList<NicSearchHitResultHitInfoItem>();

		if (groupEntry
				.indexOf(NicSearchHitResult.hitInfo__SUB_ITEM__GROUP__DELIMITER) == -1) {
			logger.info("[NIC Investigation -1[" + subItemsForDisplay.size()
					+ "]]");
			return subItemsForDisplay;
		}

		String groupEntryDataGroup = StringUtils.substringAfter(groupEntry,
				NicSearchHitResult.hitInfo__SUB_ITEM__GROUP__DELIMITER);

		String itemsLevel2[] = groupEntryDataGroup
				.split(NicSearchHitResult.hitInfo__SUB_ITEM__GROUP_ITEM__DELIMITER);

		if (itemsLevel2 == null || itemsLevel2.length == 0) {
			logger.info("[NIC Investigation itemsLevel2["
					+ subItemsForDisplay.size() + "]]");
			return subItemsForDisplay;
		}

		for (String groupEntryDataItem : itemsLevel2) {
			subItemsForDisplay.add(new NicSearchHitResultHitInfoItem(
					this.codesService.getCodeValueDescByIdName(
							Codes.SRCH_HIT_RESULT__ITEM_SUB_ITEM,
							groupEntryDataItem, groupEntryDataItem), null));
		}

		logger.info("[NIC Investigation[" + subItemsForDisplay.size() + "]]");
		return subItemsForDisplay;
	}
	
	public List<InvestigationHit> getAllHits_nonError(
			List<InvestigationHit> invHits_all) {

		List<InvestigationHit> hits = new ArrayList<InvestigationHit>();

		if (CollectionUtils.isEmpty(invHits_all)) {
			return hits;
		}

		for (InvestigationHit hit : invHits_all) {
			if (Collections.disjoint(Arrays.asList(new String[] { ((String) hit
					.getHitCandidate_searchResult_typeSearch()) }),
					NicSearchResult.TYPE_SEARCH__THAT_ARE_MAIN_CANDIDATE_BASED)) {
				hits.add(hit);
			}
		}

		return hits;
	}
	
	private InvestigationData getInvestigationData() throws Exception {

		InvestigationData investigationData = new InvestigationData();

		// Parameters value for FP Verify Score
		ParametersId paramId = new ParametersId();
		paramId.setParaName(Parameters.FP_VERIFY_MATCH_SCORE);
		paramId.setParaScope(Parameters.SCOPE_SYSTEM);

		String[] goodFpQuas = null;
		String[] accFpQuas = null;

		String defGoodFpQuas = "1-65,2-65,3-55,4-40,5-30,6-65,7-65,8-55,9-40,10-30";
		String defAcceptFpQuas = "1-40,2-40,3-40,4-25,5-15,6-40,7-40,8-40,9-25,10-15";

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

		Parameters accFpQuaParam = parametersService.findById(accFpQuaParamId);

		if (accFpQuaParam != null) {
			accFpQuas = accFpQuaParam.getParaShortValue().split(",");
		} else {
			accFpQuas = defAcceptFpQuas.split(",");
		}

		for (String goodFpQua : goodFpQuas) {
			String[] fpQuaVals = goodFpQua.split("-");

			Integer fpPos = Integer.parseInt(fpQuaVals[0]);
			Integer fpQua = Integer.parseInt(fpQuaVals[1]);

			investigationData.goodFpQuasMap.put(fpPos, fpQua);

		}

		for (String accptFpQua : accFpQuas) {
			String[] fpQuaVals = accptFpQua.split("-");

			Integer fpPos = Integer.parseInt(fpQuaVals[0]);
			Integer fpQua = Integer.parseInt(fpQuaVals[1]);

			investigationData.acceptFpQuasMap.put(fpPos, fpQua);

		}

		return investigationData;
	}
	
	private void put1HitIntoModelAndView_hit(ModelAndView modelAndView,
			NicUploadJob jobDetails, String mainTransactionId,
			Long searchResultId, String hitTransactionId,
			InvestigationData investigationData, InvestigationHit invHit,
			String searchResult_typeSearch, String searchHitResult_hitInfo)
			throws Exception, TransactionServiceException {

		/*
		 * 2222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222
		 * HIT - START
		 * 2222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222
		 */
		{
			invHit.addObject("hitCandidate_searchResult_typeSearch",
					searchResult_typeSearch);
			invHit.addObject("hitCandidate_searchHitResult_hitInfo",
					searchHitResult_hitInfo);

			// Hit Candidate Photo, Signature and Finger prints.
			String hitCandidatePhoto = null;
			String hitCandidateSignature = null;

			List<NicTransactionAttachment> hitCanPh = nicTransactionAttachmentService
					.getNicTransactionAttachments(
							hitTransactionId,
							new String[] { NicTransactionAttachment.DOC_TYPE_PHOTO_CAPTURE },
							new String[] { NicTransactionAttachment.DEFAULT_SERIAL_NO });
			// Hit Candidate Photo
			if (CollectionUtils.isNotEmpty(hitCanPh)) {
				byte[] photoBinary = this.handleJp2(hitCanPh.get(0)
						.getDocData());
				hitCandidatePhoto = new Base64Jp2HeaderHelper()
						.getBase64WithJp2Header(photoBinary);
			}

			Map<String, String> hitFpIndicatorMap = new HashMap<String, String>();
			Map<String, byte[]> hitFpMap = new HashMap<String, byte[]>();

			List<NicTransactionAttachment> hitCanFp = nicTransactionAttachmentService
					.getNicTransactionAttachments(
							hitTransactionId,
							new String[] { NicTransactionAttachment.DOC_TYPE_FINGERPRINT },
							null);
			// Hit Candidate Finger prints
			if (CollectionUtils.isNotEmpty(hitCanFp)) {
				for (NicTransactionAttachment record : hitCanFp) {
					hitFpMap.put(this.fixSerialNo(record.getSerialNo()),
							record.getDocData());
				}
			}

			// get list of search types
			Set<String> uniqueList_searchTypesForThisTransactionIdHit = new HashSet<String>();
			uniqueList_searchTypesForThisTransactionIdHit
					.addAll(this.uploadJobService.getAllSearchTypes(
							jobDetails.getWorkflowJobId(), mainTransactionId,
							hitTransactionId));

			// To display the percentage match score
			this.processFpMatchInfo(hitTransactionId, invHit,
					uniqueList_searchTypesForThisTransactionIdHit,
					jobDetails.getWorkflowJobId());

			this.processHitCandidateHitCategories(invHit,
					uniqueList_searchTypesForThisTransactionIdHit);

			this.processTextualMatchInfo(hitTransactionId, invHit,
					uniqueList_searchTypesForThisTransactionIdHit,
					jobDetails.getWorkflowJobId());

			// Hit Candidate Details
			NicTransaction hitCandidateListNin = nicTransactionService
					.findById(hitTransactionId);
			if (hitCandidateListNin != null) {
				hitCandidateListNin = nicTransactionService
						.getTransactionRegistrationData(hitCandidateListNin);

				NicRegistrationData hitNicRegistrationData = hitCandidateListNin
						.getNicRegistrationData();

				invHit.addObject("hitCandidateDateOfApplication", DateUtil
						.parseDate(hitCandidateListNin.getDateOfApplication(),
								"dd-MMM-yyyy"));
				invHit.addObject("hitCandidateIssuingAuthority",
						this.eppSiteService.getSiteRepoAuthority(
								hitCandidateListNin.getIssuingAuthority(),
								hitCandidateListNin.getIssuingAuthority()));

				invHit.addObject("hitCandidateReleaseDate", DateUtil.parseDate(
						hitCandidateListNin.getEstDateOfCollection(),
						"dd-MMM-yyyy"));
				this.processOneReferringToCodeTable(hitCandidateListNin
						.getPriority().toString(), Codes.PRIORITY,
						"hitCandidatePriority", invHit);

				this.processOneReferringToCodeTable(
						hitCandidateListNin.getTransactionType(),
						Codes.TRANSACTION_TYPE, "hitCandidateTransactionType",
						invHit);
				this.processOneReferringToCodeTable(
						hitCandidateListNin.getTransactionStatus(),
						Codes.TRANSACTION_STATUS,
						"hitCandidateApplicationPassportStatus", invHit);
				this.processOneReferringToCodeTable(
						hitNicRegistrationData.getNationality(),
						Codes.NATIONALITY, "hitCandidateNationality", invHit);

				this.processHitDocuments(hitTransactionId, invHit,
						hitCandidateListNin.getPassportType());
				this.processHitPassports(hitTransactionId, invHit,
						hitCandidateListNin.getPassportType());

				// Hit Candidate FP Quality
				Map<Integer, FingerprintInfo> hitFpQua = new TreeMap<Integer, FingerprintInfo>();
				String hitFpQuality = hitCandidateListNin
						.getNicRegistrationData().getFpQuality();
				if (hitFpQuality != null) {
					String hitFpQualityArry[] = hitFpQuality.split(",");
					if (hitFpQualityArry != null) {
						for (int i = 0; i < hitFpQualityArry.length; ++i) {
							String hitFpArry[] = hitFpQualityArry[i].split("-");

							FingerprintInfo info = new FingerprintInfo();

							info.setFpQuaScr(Integer.valueOf(hitFpArry[1]));
							info.setGoodFPQuaScr(investigationData.goodFpQuasMap
									.get(Integer.valueOf(hitFpArry[0])));
							info.setAccetableFPQuaScr(investigationData.acceptFpQuasMap
									.get(Integer.valueOf(hitFpArry[0])));

							hitFpQua.put(Integer.valueOf(hitFpArry[0]), info);

							logger.info("The hit candidate fp Quality "
									+ hitFpQua);
						}
					}
					logger.info("The fp Quality is =================>>>>>>>>>>>>>"
							+ hitCandidateListNin.getNicRegistrationData()
									.getFpQuality());
				}

				// Hit Candidate FP Encode
				Map<Integer, Double> hitFpEnc = new TreeMap<Integer, Double>();
				String hitFpEncode = hitCandidateListNin
						.getNicRegistrationData().getFpEncode();
				if (hitFpEncode != null) {
					String hitFpEncodeArry[] = hitFpEncode.split(",");
					if (hitFpEncodeArry != null) {
						for (int i = 0; i < hitFpEncodeArry.length; ++i) {
							hitFpEnc.put(Integer.valueOf(hitFpEncodeArry[i]),
									Double.valueOf(hitFpEncodeArry[i]));
							logger.info("The hit candidate fp encode "
									+ hitFpEnc);
						}
					}
				}

				if (hitNicRegistrationData != null) {

					invHit.addObject("hitCandidateAlsoKnownAs",
							hitNicRegistrationData.getAliasName());
					invHit.addObject("hitCandidateLimitation",
							hitNicRegistrationData.getPrintRemarks1());
					invHit.addObject("hitCandidatePosition",
							hitNicRegistrationData.getOccupationDesc());

					if (StringUtils.isNotBlank(hitNicRegistrationData
							.getFpIndicator())) {
						String[] fpIndicatorHit = hitNicRegistrationData
								.getFpIndicator().split(",");
						if (fpIndicatorHit.length > 0) {
							for (String hitInd : fpIndicatorHit) {
								try {
									hitFpIndicatorMap.put(hitInd.split("-")[0],
											hitInd.split("-")[1]);
								} catch (Exception e) {
									// e.printStackTrace();
								}
							}

						}
					}

					List<NicTransactionAttachment> hitCanSig = nicTransactionAttachmentService
							.getNicTransactionAttachments(
									hitTransactionId,
									new String[] { NicTransactionAttachment.DOC_TYPE_SIGNATURE },
									new String[] { NicTransactionAttachment.DEFAULT_SERIAL_NO });
					// Hit Candidate Signature
					if (CollectionUtils.isNotEmpty(hitCanSig)) {
						byte[] signBinary = this.handleJp2(hitCanSig.get(0)
								.getDocData());
						hitCandidateSignature = new Base64Jp2HeaderHelper()
								.getBase64WithJp2Header(signBinary);
						// hitCandidateSignature = signBinary;
					}

					String hitCandidateFN = hitNicRegistrationData
							.getFirstnameFull();
					String hitCandidateFNShort = null;
					if (hitCandidateFN != null) {

						if (StringUtils.isNotBlank(hitCandidateFN)) {
							if (hitCandidateFN.length() > 15) {
								hitCandidateFNShort = hitCandidateFN.substring(
										0, 14) + " ...";
							} else {
								hitCandidateFNShort = hitCandidateFN;
							}
						}
					}
					String hitCandidateSNShort = null;
					String hitCandidateSN = hitNicRegistrationData
							.getSurnameFull();
					if (hitCandidateSN != null) {
						if (StringUtils.isNotBlank(hitCandidateSN)) {
							if (hitCandidateSN.length() > 15) {
								hitCandidateSNShort = hitCandidateSN.substring(
										0, 14) + " ...";
							} else {
								hitCandidateSNShort = hitCandidateSN;
							}
						}
					}

					String hitCandidateMN = hitNicRegistrationData
							.getMiddlenameFull();
					String hitCandidateMNShort = null;
					if (StringUtils.isNotBlank(hitCandidateMN)) {
						if (hitCandidateMN.length() > 15) {
							hitCandidateMNShort = hitCandidateMN.substring(0,
									14) + " ...";
						} else {
							hitCandidateMNShort = hitCandidateMN;
						}
					}

					Date hitCandidateDOB = hitNicRegistrationData
							.getDateOfBirth();

					String hitCandDOB = null;
					if (hitCandidateDOB != null) {
						hitCandDOB = DateUtil.parseDate(hitCandidateDOB,
								"dd-MMM-yyyy");
					}

					invHit.addObject("hitCandidatePlaceOfBirth",
							hitNicRegistrationData.getPlaceOfBirth());
					String hitCandidateFlatNo = hitNicRegistrationData
							.getAddressLine1();
					String hitCandidateStreetName = hitNicRegistrationData
							.getAddressLine2();
					String hitCandidateLocality = hitNicRegistrationData
							.getAddressLine3();
					/*String hitCandidateFathersN = hitNicRegistrationData
							.getFatherFirstname();
					String hitCandidateFathersSN = hitNicRegistrationData
							.getFatherSurname();
					String hitCandidateFathersMN = hitNicRegistrationData
							.getFatherMiddlename();
					String hitCandidateMothersN = hitNicRegistrationData
							.getMotherFirstname();
					String hitCandidateMothersSN = hitNicRegistrationData
							.getMotherSurname();
					String hitCandidateMothersMN = hitNicRegistrationData
							.getMotherMiddlename();
					String hitCandidateSpouseFN = hitNicRegistrationData
							.getSpouseFirstname();
					String hitCandidateSpouseSN = hitNicRegistrationData
							.getSpouseSurname();
					String hitCandidateSpouseMN = hitNicRegistrationData
							.getSpouseMiddlename();*/
					invHit.addObject("hitCandidateListTransId",
							hitCandidateListNin.getTransactionId());
					invHit.addObject("hitCandidatePhoto", hitCandidatePhoto);
					invHit.addObject("hitCandidateSignature",
							hitCandidateSignature);
					invHit.addObject("hitCandidateFN", hitCandidateFN);
					invHit.addObject("hitCandidateFNShort", hitCandidateFNShort);
					invHit.addObject("hitCandidateSN", hitCandidateSN);
					invHit.addObject("hitCandidateSNShort", hitCandidateSNShort);
					invHit.addObject("hitCandidateMN", hitCandidateMN);
					invHit.addObject("hitCandidateMNShort", hitCandidateMNShort);
					this.processOneReferringToCodeTable(
							hitNicRegistrationData.getGender(), Codes.GENDER,
							"hitCandidateGender", invHit);
					invHit.addObject("hitCandidateDOB", hitCandDOB);
					invHit.addObject("hitCandidateFlatNo", hitCandidateFlatNo);
					invHit.addObject("hitCandidateStreetName",
							hitCandidateStreetName);
					invHit.addObject("hitCandidateLocality",
							hitCandidateLocality);
					this.processOneReferringToCodeTable(
							hitNicRegistrationData.getMaritalStatus(),
							Codes.MARITAL_STATUS, "hitCandidateMaritalStatus",
							invHit);
					/*invHit.addObject("hitCandidateFathersN",
							hitCandidateFathersN);
					invHit.addObject("hitCandidateFathersSN",
							hitCandidateFathersSN);
					invHit.addObject("hitCandidateFathersMN",
							hitCandidateFathersMN);
					invHit.addObject("hitCandidateMothersN",
							hitCandidateMothersN);
					invHit.addObject("hitCandidateMothersSN",
							hitCandidateMothersSN);
					invHit.addObject("hitCandidateMothersMN",
							hitCandidateMothersMN);
					invHit.addObject("hitCandidateSpouseFN",
							hitCandidateSpouseFN);
					invHit.addObject("hitCandidateSpouseSN",
							hitCandidateSpouseSN);
					invHit.addObject("hitCandidateSpouseMN",
							hitCandidateSpouseMN);*/
					invHit.addObject("hitCandidateFpQuality", hitFpQua);
					invHit.addObject("hitCandidateFpEncode", hitFpEnc);
				}
			}
			invHit.addObject("hitFpMap", hitFpMap);
			invHit.addObject("hitFpIndicatorMap", hitFpIndicatorMap);

			invHit.addObject("hitTransactionId", hitTransactionId);
			invHit.addObject("searchResultId", searchResultId);
		}
		/*
		 * 2222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222
		 * HIT - END
		 * 2222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222
		 */
	}
	
	private byte[] handleJp2(byte[] image) {

		if (image == null) {
			return image;
		}

		// if (image != null) {
		// try {
		// return FileUtils.readFileToByteArray(new
		// File("C:/EPP-DEV/angeles/fileToConvert.xxx"));
		// } catch (Exception e) {
		// return null;
		// }
		// }

		boolean ignoreJ2k = false;
		{
			try {
				Parameters parameter = this.parametersService
						.getParamDetails(
								SyncSingerController.PARA_TRANSACTION_ATTACHM_IGNORE_J2K_SCOPE,
								SyncSingerController.PARA_TRANSACTION_ATTACHM_IGNORE_J2K_VALUE);
				if (parameter != null) {
					ignoreJ2k = Boolean.valueOf(parameter.getParaShortValue());
				}
			} catch (Exception e) {
			}
		}

		// get type
		String imageType = null;
		{
			try {
				imageType = new ImageUtil().checkImageType(image);
			} catch (Exception e) {
				return null;
			}
		}

		if (StringUtils.isBlank(imageType)) {
			return null;
		}

		if (imageType.equalsIgnoreCase(ImageUtil.IMAGE_J2K)) {
			if (ignoreJ2k) {
				return null;
			} else {
				boolean j2kConvertAtServer = false;
				{
					try {
						Parameters parameter = this.parametersService
								.getParamDetails(
										SyncSingerController.PARA_TX_ATTCH_J2K_CONVERT_AT_SERVER_SCOPE,
										SyncSingerController.PARA_TX_ATTCH_J2K_CONVERT_AT_SERVER_VALUE);
						if (parameter != null) {
							j2kConvertAtServer = Boolean.valueOf(parameter
									.getParaShortValue());
						}
					} catch (Exception e) {
					}
				}

				if (j2kConvertAtServer) {
					try {
//						return SpidHelper.getInstance("Y").convertImageFormat(
//								image, SpidHelper.IMAGE_J2K,
//								SpidHelper.IMAGE_JPG);
						return null;
					} catch (Exception e) {
						return null;
					}
				} else {
					return image;
				}
			}
		}

		return image;
	}
	
	private String fixSerialNo(String serialNo) {

		if (serialNo == null) {
			throw new RuntimeException("serialNo==null");
		}

		serialNo = serialNo.trim();

		if (serialNo.length() == 0) {
			throw new RuntimeException("serialNo.length()==0");
		}

		if (serialNo.length() == 1) {
			return "0" + serialNo;
		}

		return serialNo;

	}
	
	private void processOneReferringToCodeTable(String key,
			String codeTableKey, String modelItemName,
			InvestigationHit investigationHit) {
		if (key == null) {
			investigationHit.addObject(modelItemName, "");
		} else {
			CodeValues codeValue = codesService.getCodeValueByIdName(
					codeTableKey, key);
			if (codeValue != null) {
				investigationHit.addObject(modelItemName,
						codeValue.getCodeValueDesc());
			} else {
				investigationHit.addObject(modelItemName, key);
			}
		}
	}
	
	private void processMainCandidateAttachments(
			HttpServletRequest httpRequest, String mainTransactionId,
			InvestigationHit invHit) throws InvalidParameterException {

		String attachmentSource = null;
		try {
			Parameters parameter = this.parametersService
					.getParamDetails(
							SyncSingerController.PARA_TRANSACTION_ATTACHMENT_SOURCE_SCOPE,
							SyncSingerController.PARA_TRANSACTION_ATTACHMENT_SOURCE_VALUE);
			if (parameter != null
					&& StringUtils.isNotBlank(parameter.getParaShortValue())) {
				attachmentSource = parameter.getParaShortValue();
			}
		} catch (Exception e) {
		}

		if (attachmentSource == null) {
			throw new InvalidParameterException(
					"Parameter TRANSACTION_ATTACHMENT_SOURCE is required.");
		}

		if (attachmentSource.equalsIgnoreCase(AttachmentSource.DMS)) {
			this.processMainCandidateAttachments_thru_DMS(mainTransactionId,
					invHit);
			return;
		}

		if (attachmentSource.equalsIgnoreCase(AttachmentSource.INTERNAL)) {
			this.processMainCandidateAttachments_thru_INTERNAL(httpRequest,
					mainTransactionId, invHit);
			return;
		}

		throw new InvalidParameterException(
				"Parameter TRANSACTION_ATTACHMENT_SOURCE is required.");
	}
	
	private void processMainCandidateAttachments_thru_DMS(
			String mainTransactionId, InvestigationHit invHit)
			throws InvalidParameterException {

		String urlPrefix = null;
		try {
			Parameters parameter = this.parametersService.getParamDetails(
					SyncSingerController.PARA_SCOPE_SYSTEM,
					SyncSingerController.PARA_NAME_SYSTEM_DMS_URL);
			if (parameter != null && parameter.getParaLobValue() != null) {
				urlPrefix = parameter.getParaLobValue();
			}
		} catch (Exception e) {
		}

		if (StringUtils.isBlank(urlPrefix)) {
			throw new InvalidParameterException(
					"Parameter DMS_URL is required.");
		}

		List<AttachmentEntry> attachmentEntries = new ArrayList<AttachmentEntry>();

		// GET ALL ATTACHMENTS
		List<NicTransactionAttachment> nicTransactionDocuments = null;
		{
			String[] excludedDocTypes = {
					NicTransactionAttachment.DOC_TYPE_FINGERPRINT,
					NicTransactionAttachment.DOC_TYPE_MINUTIA,
					NicTransactionAttachment.DOC_TYPE_MINUTIA_CBEFF,
					NicTransactionAttachment.DOC_TYPE_ENCODING,
					NicTransactionAttachment.DOC_TYPE_PERSO,
					NicTransactionAttachment.DOC_TYPE_SIGNATURE,
					NicTransactionAttachment.DOC_TYPE_SIGN_FP,
					NicTransactionAttachment.DOC_TYPE_PHOTO_CAPTURE,
					NicTransactionAttachment.DOC_TYPE_THUMBNAIL_CAPTURE,
					NicTransactionAttachment.DOC_TYPE_PHOTO_GREY,
					NicTransactionAttachment.DOC_TYPE_THUMBNAIL_GREY,
					NicTransactionAttachment.DOC_TYPE_PHOTO_CHIP,
					NicTransactionAttachment.DOC_TYPE_THUMBNAIL_CHIP,
					NicTransactionAttachment.DOC_TYPE_PHOTO_CLI,
					NicTransactionAttachment.DOC_TYPE_TPL };
			try {
				nicTransactionDocuments = this.nicTransactionAttachmentService
						.getTransactionProofDocuments(mainTransactionId,
								excludedDocTypes);
			} catch (Exception e) {
				nicTransactionDocuments = null;
			}
		}

		// LOOP THROUGH ALL
		{
			if (nicTransactionDocuments != null
					&& nicTransactionDocuments.size() > 0) {
				for (NicTransactionAttachment transactionAttachment : nicTransactionDocuments) {
					this.processMainCandidateAttachments_1attachment_thru_DMS(
							mainTransactionId, urlPrefix, attachmentEntries,
							transactionAttachment);
				}
			}
		}

		invHit.addObject("mainCandidateAttachments", attachmentEntries);
		invHit.addObject("mainCandidateAttachmentSize",
				attachmentEntries.size());
	}
	
	private void processMainCandidateAttachments_1attachment_thru_DMS(
			String mainTransactionId, String urlPrefix,
			List<AttachmentEntry> attachmentEntries,
			NicTransactionAttachment attachment) {

		String description = this.codesService.getCodeValueDescByIdName(
				Codes.DOC_TYPE, attachment.getDocType(),
				attachment.getDocType());

		// for (NicTransactionAttachment attachment :
		// this.getAttachments_nullSafe(mainTransactionId,
		// transactionAttachment_docType)) {
		attachmentEntries.add(new AttachmentEntry(description,
				(urlPrefix != null ? urlPrefix
						+ DmsUtility.DMS__URL_DELIMITER_1
						+ attachment.getTransactionDocId()
						+ DmsUtility.DMS__URL_DELIMITER_2
						+ attachment.getTransactionId()
						+ DmsUtility.DMS__URL_DELIMITER_3
						+ attachment.getDocType() : null)));
		// }
	}
	
	private void processFpMatchInfo(String hitTransactionId,
			InvestigationHit invHit,
			Set<String> searchTypesForThisTransactionIdHit, Long workflowId)
			throws Exception {

		if (Collections.disjoint(searchTypesForThisTransactionIdHit,
				NicSearchResult.TYPE_SEARCH__THAT_ARE_FINGER_BASED)) {
			invHit.addObject("investigationForm", null);
			return;
		}

		try {
			NicSearchHitResult nicSearchHitResult = null; /*= this.uploadJobService
					.getAllSearchHitResult(workflowId, hitTransactionId,
							NicSearchResult.TYPE_SEARCH__THAT_ARE_FINGER_BASED,
							new NicSearchHitResultHitScorerFpImpl()).get(0);*/

			Map<Integer, Double> hitMatchs = new TreeMap<Integer, Double>();

			HitCandidateDTO hitCandidateDTO = new HitCandidateDTO();

			String hitPositionArry[] = nicSearchHitResult.getHitInfo().split(
					",");
			if (hitPositionArry != null) {
				for (int i = 0; i < hitPositionArry.length; ++i) {
					String matchArry[] = hitPositionArry[i].split("=");
					hitMatchs.put(Integer.valueOf(matchArry[0]),
							Double.valueOf(matchArry[1]) / 100);
					logger.info("The hit match score " + hitMatchs);
				}
			}

			if (!hitMatchs.containsKey(1)) {
				hitMatchs.put(Integer.valueOf(1), 0.1);
			}
			if (!hitMatchs.containsKey(2)) {
				hitMatchs.put(Integer.valueOf(2), 0.1);
			}
			if (!hitMatchs.containsKey(3)) {
				hitMatchs.put(Integer.valueOf(3), 0.1);
			}
			if (!hitMatchs.containsKey(4)) {
				hitMatchs.put(Integer.valueOf(4), 0.1);
			}
			if (!hitMatchs.containsKey(5)) {
				hitMatchs.put(Integer.valueOf(5), 0.1);
			}
			if (!hitMatchs.containsKey(6)) {
				hitMatchs.put(Integer.valueOf(6), 0.1);
			}
			if (!hitMatchs.containsKey(7)) {
				hitMatchs.put(Integer.valueOf(7), 0.1);
			}
			if (!hitMatchs.containsKey(8)) {
				hitMatchs.put(Integer.valueOf(8), 0.1);
			}
			if (!hitMatchs.containsKey(9)) {
				hitMatchs.put(Integer.valueOf(9), 0.1);
			}
			if (!hitMatchs.containsKey(10)) {
				hitMatchs.put(Integer.valueOf(10), 0.1);
			}
			hitCandidateDTO.setMatchScore(hitMatchs);
			invHit.addObject("investigationForm", hitCandidateDTO);
		} catch (Exception e) {
			invHit.addObject("investigationForm", null);
		}
	}
	
	private void processHitCandidateHitCategories(InvestigationHit invHit,
			Set<String> uniqueList_searchTypesForThisTransactionIdHit) {

		Set<String> uniqueDescriptionsList = new HashSet<String>();
		for (String searchTypeCode : uniqueList_searchTypesForThisTransactionIdHit) {
			String desc = this.codesService.getCodeValueDescByIdName(
					Codes.SEARCH_TYPE, searchTypeCode, "");
			if (desc.trim().length() > 0) {
				uniqueDescriptionsList.add(desc);
			}
		}

		List<String> sortedDescs = new ArrayList<String>();
		sortedDescs.addAll(uniqueDescriptionsList);
		Collections.sort(sortedDescs);

		String hitCandidatehitCategories = "";
		for (String aDesc : sortedDescs) {
			if (hitCandidatehitCategories.length() > 0) {
				hitCandidatehitCategories = hitCandidatehitCategories + ", ";
			}
			hitCandidatehitCategories = hitCandidatehitCategories + aDesc;
		}

		invHit.addObject("hitCandidatehitCategories", hitCandidatehitCategories);
	}
	
	private void processTextualMatchInfo(String hitTransactionId,
			InvestigationHit invHit,
			Set<String> searchTypesForThisTransactionIdHit, Long workflowId) {

		if (Collections.disjoint(searchTypesForThisTransactionIdHit,
				NicSearchResult.TYPE_SEARCH__THAT_ARE_TEXT_BASED)) {
			invHit.addObject("hitCandidateHitInfo", null);
			return;
		}

		try {
			NicSearchHitResult nicSearchHitResult = null;/*this.uploadJobService
					.getAllSearchHitResult(workflowId, hitTransactionId,
							NicSearchResult.TYPE_SEARCH__THAT_ARE_TEXT_BASED,
							new NicSearchHitResultHitScorerTextualImpl())
					.get(0)*/;

			String itemsLevel1[] = nicSearchHitResult.getHitInfo().split(
					NicSearchHitResult.hitInfo__ITEM__DELIMITER);
			List<NicSearchHitResultHitInfoItem> itemsForDisplay = new ArrayList<NicSearchHitResultHitInfoItem>();

			if (itemsLevel1 != null && itemsLevel1.length > 0) {
				for (String groupEntry : itemsLevel1) {
					String groupEntryTitle = StringUtils
							.substringBefore(
									groupEntry,
									NicSearchHitResult.hitInfo__SUB_ITEM__GROUP__DELIMITER);

					itemsForDisplay.add(new NicSearchHitResultHitInfoItem(
							this.codesService.getCodeValueDescByIdName(
									Codes.SRCH_HIT_RESULT__ITEM,
									groupEntryTitle, groupEntryTitle), this
									.getTextualHitInfoGroupItems(groupEntry)));
				}
			}

			invHit.addObject("hitCandidateHitInfo", itemsForDisplay);
		} catch (Exception e) {
			invHit.addObject("hitCandidateHitInfo", null);
		}

	}
	
	private void processHitDocuments(String hitTransactionId,
			InvestigationHit invHit, String passportType) {

		List<NicDocumentData> docs = null;
		docs = this.documentDataDao.findAllByTransactionId(hitTransactionId,
				Arrays.asList(DocumentStatus.Active.getKey()));
		if (CollectionUtils.isEmpty(docs)) {
			docs = this.documentDataDao.findAllByTransactionId_notInStatuses(
					hitTransactionId, Arrays.asList(
							DocumentStatus.Active.getKey(),
							DocumentStatus.Cancelled.getKey()));
		}
		if (CollectionUtils.isEmpty(docs)) {
			docs = this.documentDataDao.findAllByTransactionId(
					hitTransactionId,
					Arrays.asList(DocumentStatus.Cancelled.getKey()));
		}

		if (docs == null || docs.size() == 0 || docs.get(0) == null) {
			invHit.addObject("hitCandidateDocumentPassportNumber", "");
			invHit.addObject("hitCandidateDocumentPassportIssuedDate", "");
			invHit.addObject("hitCandidateDocumentPassportExpirationDate", "");
			invHit.addObject("hitCandidateDocumentPassportStatus", "");
			invHit.addObject("hitCandidateDocumentPassportType", "");
		} else {
			invHit.addObject("hitCandidateDocumentPassportNumber", docs.get(0)
					.getId().getPassportNo());
			invHit.addObject("hitCandidateDocumentPassportIssuedDate", DateUtil
					.parseDate(docs.get(0).getDateOfIssue(), "dd-MMM-yyyy"));
			invHit.addObject("hitCandidateDocumentPassportExpirationDate",
					DateUtil.parseDate(docs.get(0).getDateOfExpiry(),
							"dd-MMM-yyyy"));
			this.processHitCandidateDocumentPassportStatus(invHit, docs);
			invHit.addObject("hitCandidateDocumentPassportType",
					this.codesService.getCodeValueDescByIdName(
							Codes.PASSPORT_TYPE, passportType, passportType));
		}
	}

	private void processHitPassports(String hitTransactionId,
			InvestigationHit invHit, String passportType) {

		List<NicDocumentData> docs = null;
		docs = this.documentDataDao.findAllByTransactionId(hitTransactionId,
				Arrays.asList(DocumentStatus.Active.getKey()));

		if (docs == null || docs.size() == 0 || docs.get(0) == null) {
			invHit.addObject("hitCandidatePassports", null);
		} else {
			List<Passport> hitCandidatePassports = new ArrayList<Passport>();
			for (NicDocumentData doc : docs) {
				Passport aPassport = new Passport();
				aPassport.setDateOfExpiry(doc.getDateOfExpiry());
				aPassport.setDateOfIssue(doc.getDateOfIssue());
				aPassport.setPassportNo(doc.getId().getPassportNo());
				aPassport.setPassportType(passportType);
				aPassport.setPassportType_forDisplay(this.codesService
						.getCodeValueDescByIdName(Codes.PASSPORT_TYPE,
								passportType, passportType));
				aPassport.setStatus(doc.getStatus());
				aPassport.setStatus_forDisplay(this
						.getPassportStatus_forDisplay(doc.getStatus()));
				aPassport.setTransactionId(doc.getId().getTransactionId());
				hitCandidatePassports.add(aPassport);
			}
			invHit.addObject("hitCandidatePassports", hitCandidatePassports);
		}
	}
	
	private void processMainCandidateAttachments_thru_INTERNAL(
			HttpServletRequest httpRequest, String mainTransactionId,
			InvestigationHit invHit) {

		boolean ignoreJ2k = false;
		{
			try {
				Parameters parameter = this.parametersService
						.getParamDetails(
								SyncSingerController.PARA_TRANSACTION_ATTACHM_IGNORE_J2K_SCOPE,
								SyncSingerController.PARA_TRANSACTION_ATTACHM_IGNORE_J2K_VALUE);
				if (parameter != null) {
					ignoreJ2k = Boolean.valueOf(parameter.getParaShortValue());
				}
			} catch (Exception e) {
			}
		}

		String urlPrefix = AttachmentEntry.INTERNAL_URL_PATH;

		List<AttachmentEntry> attachmentEntries = new ArrayList<AttachmentEntry>();

		// GET ALL ATTACHMENTS
		List<NicTransactionAttachment> nicTransactionDocuments = null;
		{
			String[] excludedDocTypes = {
					NicTransactionAttachment.DOC_TYPE_FINGERPRINT,
					NicTransactionAttachment.DOC_TYPE_MINUTIA,
					NicTransactionAttachment.DOC_TYPE_MINUTIA_CBEFF,
					NicTransactionAttachment.DOC_TYPE_ENCODING,
					NicTransactionAttachment.DOC_TYPE_PERSO,
					NicTransactionAttachment.DOC_TYPE_SIGNATURE,
					NicTransactionAttachment.DOC_TYPE_SIGN_FP,
					NicTransactionAttachment.DOC_TYPE_PHOTO_CAPTURE,
					NicTransactionAttachment.DOC_TYPE_THUMBNAIL_CAPTURE,
					NicTransactionAttachment.DOC_TYPE_PHOTO_GREY,
					NicTransactionAttachment.DOC_TYPE_THUMBNAIL_GREY,
					NicTransactionAttachment.DOC_TYPE_PHOTO_CHIP,
					NicTransactionAttachment.DOC_TYPE_THUMBNAIL_CHIP,
					NicTransactionAttachment.DOC_TYPE_PHOTO_CLI,
					NicTransactionAttachment.DOC_TYPE_TPL };
			try {
				nicTransactionDocuments = this.nicTransactionAttachmentService
						.getTransactionProofDocuments(mainTransactionId,
								excludedDocTypes);
			} catch (Exception e) {
				nicTransactionDocuments = null;
			}
		}

		// LOOP THROUGH ALL
		{
			if (nicTransactionDocuments != null
					&& nicTransactionDocuments.size() > 0) {
				for (NicTransactionAttachment transactionAttachment : nicTransactionDocuments) {
					this.processMainCandidateAttachments_1attachment_thru_INTERNAL(
							httpRequest, mainTransactionId, urlPrefix,
							attachmentEntries, ignoreJ2k, transactionAttachment);
				}
			}
		}

		invHit.addObject("mainCandidateAttachments", attachmentEntries);
		invHit.addObject("mainCandidateAttachmentSize",
				attachmentEntries.size());
	}
	
	private void processMainCandidateAttachments_1attachment_thru_INTERNAL(
			HttpServletRequest httpRequest, String mainTransactionId,
			String urlPrefix, List<AttachmentEntry> attachmentEntries,
			boolean ignoreJ2k, NicTransactionAttachment attachment) {

		String description = this.codesService.getCodeValueDescByIdName(
				Codes.DOC_TYPE, attachment.getDocType(),
				attachment.getDocType());

		// for (NicTransactionAttachment attachment :
		// this.getAttachments_nullSafe(mainTransactionId,
		// transactionAttachment_docType)) {

		if (attachment.getDocData() != null) {

			{
				String imageType = null;
				{
					try {
						imageType = new ImageUtil().checkImageType(attachment
								.getDocData());
					} catch (Exception e) {
						return;
					}
				}

				if (ignoreJ2k
						&& (StringUtils.isNotBlank(imageType) && imageType
								.equalsIgnoreCase(ImageUtil.IMAGE_J2K))) {
					return;
				}
			}

			try {
				attachmentEntries.add(new AttachmentEntry(description,
						httpRequest.getContextPath() + urlPrefix
								+ AttachmentEntry.INTERNAL__URL_DELIMITER
								+ attachment.getTransactionDocId()));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		// }
	}
	
	private void processHitCandidateDocumentPassportStatus(
			InvestigationHit invHit, List<NicDocumentData> docs) {
		String statusDescription = null;
		try {
			statusDescription = DocumentStatus.getInstance(
					docs.get(0).getStatus()).getName();
		} catch (Exception e) {
			statusDescription = docs.get(0).getStatus();
		}
		invHit.addObject("hitCandidateDocumentPassportStatus",
				statusDescription);
	}
	
	private String getPassportStatus_forDisplay(String status) {
		try {
			return DocumentStatus.getInstance(status).getName();
		} catch (Exception e) {
			return status;
		}
	}
	
	public String getMessageFromServerString(JSONObject response, String key) {
		return ((response.has(key) && !response.isNull(key))) ? response
				.getString(key) : null;
	}

	public Integer getMessageFromServerInt(JSONObject response, String key) {
		return ((response.has(key) && !response.isNull(key))) ? response
				.getInt(key) : null;
	}

	public Long getMessageFromServerLong(JSONObject response, String key) {
		return ((response.has(key) && !response.isNull(key))) ? response
				.getLong(key) : null;
	}

	public Boolean getMessageFromServerBoolean(JSONObject response, String key) {
		return ((response.has(key) && !response.isNull(key))) ? response
				.getBoolean(key) : null;
	}
	
	// Xử lý tạo danh sách bàn giao đồng bộ sang A72
	@RequestMapping(value = { "/createHandoverSyncA72" })
	public ModelAndView createHandoverSyncA72(
			@ModelAttribute(value = "formData") InvestigationAssignmentData investigationHitData,
			BindingResult result, WebRequest request,
			HttpServletRequest httpRequest, Model model,
			@RequestParam("selectedJobIds") String[] checkboxvalues)
			throws Throwable {
		logger.info("createHandoverSyncA72");

		List<String> messages = new LinkedList<String>();
		try {
			HttpSession session = httpRequest.getSession();
			Date date = new Date();
			UserSession userSession = (UserSession) session
					.getAttribute("userSession");
			String listIdTrans = "";
			if (checkboxvalues != null) {
				for (String st : checkboxvalues) {
					NicTransaction nicTransaciton = nicTransactionService
							.getNicTransactionById(st).getModel();
					List<NicUploadJob> lstUpload = uploadJobService
							.findAllByTransactionId(st);

					try {
						// Cập nhật trạng thái đồng bộ dữ liệu
						//nicTransaciton.setSyncPassport(1);
						nicTransactionService.saveOrUpdate(nicTransaciton);
					} catch (Exception ex) {

					}

					NicUploadJob nicU = new NicUploadJob();
					nicU = lstUpload.get(0);
					// Chuyển vào hàng đợi để gửi sang BNG chạy JOb
					QueuesJobSchedule obj = new QueuesJobSchedule();
					obj.setCode(nicU.getTransactionId());
					if (nicU.getNicTransaction() != null) {
						obj.setPassportType(nicU.getNicTransaction()
								.getPassportType());
						if (nicU.getNicTransaction().getNicRegistrationData() != null) {
							obj.setName(nicU.getNicTransaction()
									.getNicRegistrationData().getSurnameLine1());
						}
					}
					obj.setTypeTransaction(QueuesJobSchedule.TYPE_TRANSACTION_SYNC);
					obj.setTypeLogJob(nicU.getJobType());
					obj.setStatus(QueuesJobSchedule.STATUS_JOB_NEW);
					obj.setDescription("Đang xử lý gửi dữ liệu đồng bộ Hộ chiếu sang Bộ công an..");
					queuesJobScheduleService.createQueuesJobSchedule(obj);

					// Lưu id sang bảng danh sách bàn giao
					listIdTrans += st + ",";
				}

				if (!StringUtils.isEmpty(listIdTrans)) {
					String packId = "";
					Random ran = new Random();
					while (StringUtils.isBlank(packId)) {
						String p = "RC-CQDD-" + ran.nextInt(99999999);
						List<NicListHandover> check = listHandoverService.findAllByPackageId(new NicListHandoverId(p, null)).getListModel();
						if (check == null || check.size() <= 0) {
							packId = p;
						}
					}

					NicListHandover handover = new NicListHandover();
					handover.setCreateBy(userSession.getUserId());
					handover.setCreateDate(date);
					// 10/06/2020 thieu typeList, khong khoi tao NicListHandoverId
//					handover.setPackageId(packId);
					//handover.setTransactionId(listIdTrans);
					handover.setPackageName(investigationHitData
							.getUnassignAllForUserUserId());
					//handover.setTypeList(6);// Dành cho đồng bộ hộ chiếu sang bộ
											// công an
					//handover.setDescription("");
					listHandoverService.createRecordHandover(handover);
					messages.add("Tạo danh sách bàn giao đồng bộ hộ chiếu sang Bộ công an thành công.");
					ModelAndView modelAndView = new ModelAndView(
							"syncSinger.success");
					model.addAttribute("code", packId);
					return modelAndView;
				}
			} else {
				messages.add("Chưa có bản ghi nào được chọn.");
				httpRequest.setAttribute("messages", messages);
				return new ModelAndView(
						"forward:/servlet/syncSinger/getSyncSingerJob");
			}

		} catch (JobNoLongerAssignedToOfficerException e) {
			messages.add("Tạo danh sách bàn giao đồng bộ hộ chiếu sang Bộ công an thất bại. Công việc hiện tại không được phân công cho User này");
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

		investigationHitData = new InvestigationAssignmentData();
		httpRequest.setAttribute("messages", messages);
		return new ModelAndView("forward:/servlet/syncSinger/getSyncSingerJob");
	}
		
}
