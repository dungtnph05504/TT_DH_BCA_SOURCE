package com.nec.asia.nic.investigation.controller;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.displaytag.tags.TableTagParameters;
import org.displaytag.util.ParamEncoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.ModelAndView;

import bsh.StringUtil;

import com.nec.asia.nic.admin.domain.User;
import com.nec.asia.nic.common.dto.RicRegistrationDTO;
import com.nec.asia.nic.comp.job.dto.SearchHitDto;
import com.nec.asia.nic.comp.listHandover.domain.NicListHandover;
import com.nec.asia.nic.comp.listHandover.domain.NicListHandoverDetail;
import com.nec.asia.nic.comp.listHandover.domain.NicListHandoverId;
import com.nec.asia.nic.comp.listHandover.service.ListHandoverService;
import com.nec.asia.nic.comp.queuesJobSchedule.service.QueuesJobScheduleService;
import com.nec.asia.nic.comp.trans.domain.AreaData;
import com.nec.asia.nic.comp.trans.domain.CountryData;
import com.nec.asia.nic.comp.trans.domain.NicDocumentData;
import com.nec.asia.nic.comp.trans.domain.NicRegistrationData;
import com.nec.asia.nic.comp.trans.domain.NicRejectionData;
import com.nec.asia.nic.comp.trans.domain.NicTransaction;
import com.nec.asia.nic.comp.trans.domain.NicTransactionAttachment;
import com.nec.asia.nic.comp.trans.domain.NicTransactionLog;
import com.nec.asia.nic.comp.trans.domain.NicUploadJob;
import com.nec.asia.nic.comp.trans.domain.NicWorkflowJob;
import com.nec.asia.nic.comp.trans.domain.OfficeData;
import com.nec.asia.nic.comp.trans.domain.PlaceData;
import com.nec.asia.nic.comp.trans.domain.QueuesJobSchedule;
import com.nec.asia.nic.comp.trans.domain.RequestCancel;
import com.nec.asia.nic.comp.trans.dto.AssignmentFilter;
import com.nec.asia.nic.comp.trans.dto.AssignmentFilterAll;
import com.nec.asia.nic.comp.trans.dto.InvestigationHitData;
import com.nec.asia.nic.comp.trans.dto.NicUploadJobDto;
import com.nec.asia.nic.comp.trans.exception.JobNoLongerAssignedToOfficerException;
import com.nec.asia.nic.comp.trans.service.DocumentDataService;
import com.nec.asia.nic.comp.trans.service.NicRegistrationDataService;
import com.nec.asia.nic.comp.trans.service.NicTransactionAttachmentService;
import com.nec.asia.nic.comp.trans.service.NicTransactionService;
import com.nec.asia.nic.comp.trans.service.NicUploadJobService;
import com.nec.asia.nic.framework.PaginatedResult;
import com.nec.asia.nic.framework.admin.code.domain.CodeValues;
import com.nec.asia.nic.framework.admin.code.domain.Codes;
import com.nec.asia.nic.framework.admin.code.domain.Parameters;
import com.nec.asia.nic.framework.admin.code.domain.ParametersId;
import com.nec.asia.nic.framework.admin.code.service.CodesService;
import com.nec.asia.nic.framework.admin.code.service.ParametersService;
import com.nec.asia.nic.framework.admin.rbac.domain.Users;
import com.nec.asia.nic.framework.admin.rbac.service.UserService;
import com.nec.asia.nic.framework.admin.site.domain.SiteGroups;
import com.nec.asia.nic.framework.admin.site.domain.SiteRepository;
import com.nec.asia.nic.framework.admin.site.service.SiteService;
import com.nec.asia.nic.framework.controller.AbstractController;
import com.nec.asia.nic.framework.dao.DaoException;
import com.nec.asia.nic.framework.security.service.ADUser;
import com.nec.asia.nic.investigation.controller.InvestigationController.RequestDocument;
import com.nec.asia.nic.util.Constants;
import com.nec.asia.nic.util.RegistrationConstants;
import com.nec.asia.nic.util.TransLogInfoXmlConvertor;
import com.nec.asia.nic.util.pagingUtil;
import com.nec.asia.nic.utils.HelperClass;
import com.nec.asia.nic.web.session.UserSession;

@Controller
@RequestMapping(value = "/investigationLost")
public class InvestigationLostController extends InvestigationController {

	private static final Logger logger = LoggerFactory
			.getLogger(InvestigationLostController.class);

	public static TransLogInfoXmlConvertor logUtil = new TransLogInfoXmlConvertor();

	public static final String STATUS_COMPLETED = "02";

	@Autowired
	private ParametersService parametersService;

	@Autowired
	private NicUploadJobService uploadJobService;

	@Autowired
	private CodesService codesService;

	@Autowired
	private NicTransactionService nicTransactionService;

	@Autowired
	private NicTransactionAttachmentService nicTransactionAttachmentService;

	@Autowired
	private ListHandoverService listHandoverService;

	@Autowired
	private DocumentDataService documentDataService;
	
	@Autowired
	private SiteService siteService;
	
	@Autowired
	private QueuesJobScheduleService queuesJobScheduleService;

	@Autowired 
	@Qualifier("userService")
	private UserService userService;

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

	@ModelAttribute("investigationTypeCode")
	public Map<String, String> investigationTypeCode() {
		// List<CodeValues> codeList =
		// codesService.getAllCodeValues("SEARCH_TYPE", new String[] {"1", "2",
		// "3"});
		Map<String, String> investigationType = new LinkedHashMap<String, String>();
		investigationType.put("", "Không dính điều tra");
		investigationType.put("AFIS 1:N", "AFIS 1:N");
		investigationType.put("CPD", "CPD");
		investigationType.put("AFIS 1:1 % CPD", "CPD,AFIS 1:N");
		return investigationType;
	}

	@RequestMapping(value = "/investigationLostList")
	public ModelAndView getInvestigationLostList(
			@ModelAttribute(value = "formData") InvestigationAssignmentData investigationAssignmentData,
			WebRequest request, HttpServletRequest httpRequest, ModelMap model) {
		logger.info("getInvestigationLostList");

		int pageNo = 1;

		String tableId = "row";
		String pageNumber = request.getParameter((new ParamEncoder(tableId)
				.encodeParameterName(TableTagParameters.PARAMETER_PAGE)));
		if (StringUtils.isNotBlank(pageNumber)
				&& StringUtils.isNumeric(pageNumber)) {
			pageNo = Integer.parseInt(pageNumber);
		}
		pageNo = !StringUtils.isEmpty(request.getParameter("pageNo")) ? Integer.parseInt(request.getParameter("pageNo")) : 1;
		return this.getInvestigationLostList(investigationAssignmentData,
				request, httpRequest, model, pageNo, null, null);
	}

	public ModelAndView getInvestigationLostList(
			@ModelAttribute(value = "formData") InvestigationAssignmentData investigationAssignmentData,
			WebRequest request, HttpServletRequest httpRequest, ModelMap model,
			int pageNo, List<String> listOfMessages, List<String> listOfErrors) {
		logger.info("getInvestigationLostList pageNo");
		HttpSession session = httpRequest.getSession();
		UserSession userSession = (UserSession) session
				.getAttribute("userSession");
		logger.info("The Officer Id or User Id is ===============>>>>>>>>>>"
				+ userSession.getUserName());

		{
			List<String> messages = new ArrayList<String>();

			if (listOfMessages != null) {
				messages.addAll(listOfMessages);
			}

			if (CollectionUtils.isNotEmpty(messages)) {
				httpRequest.setAttribute("messages", messages);
			}
		}

		{
			List<String> Errors = new ArrayList<String>();

			if (listOfMessages != null) {
				Errors.addAll(listOfErrors);
			}

			if (CollectionUtils.isNotEmpty(Errors)) {
				httpRequest.setAttribute("Errors", Errors);
			}
		}

		if (investigationAssignmentData == null) {
			investigationAssignmentData = new InvestigationAssignmentData();
		}

		investigationAssignmentData.cleanUpStrings();

		PaginatedResult<NicUploadJobDto> pr = null;
		try {
			int pageSize = 20;

			Parameters parameter = parametersService.findById(new ParametersId(
					Parameters.SCOPE_SYSTEM, Parameters.MAX_RECORDS_PER_PAGE));
			if (parameter != null) {
				String pageSizeDb = parameter.getParaShortValue();

				if (StringUtils.isNotBlank(pageSizeDb)
						&& StringUtils.isNumeric(pageSizeDb)) {
					pageSize = Integer.parseInt(pageSizeDb);
				}
			}
			pageSize = Constants.PAGE_SIZE_DEFAULT;
			pr = uploadJobService.findAllForInvestigationPagination(
							new String[] { "LOS" },
							userSession.getUserId(),
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
											.getTypeInvestigation(), investigationAssignmentData.getPackageCode()));

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
								record.setEstDateOfCollection(nicTransaction
										.getEstDateOfCollection());
								record.setFullName(nicTransaction
										.getNicRegistrationData()
										.getSurnameLine1());
								record.setPassportType(nicTransaction
										.getPassportType());
								record.setRegSiteCode(nicTransaction
										.getRegSiteCode());
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
										if(nicTransaction.getPriority() == 0)
											record.setPriorityString("Thông thường");
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

				model.addAttribute("fnSelected", Constants.HEADING_NIC_LOST_DAMAGE);
				ModelAndView modelAndView = new ModelAndView(
						"investigation.investigationLost.investigationLostList",
						searchResultMap);
				investigationAssignmentData.cleanUpForNextPage();
				modelAndView.addObject("formData", investigationAssignmentData);
				return modelAndView;
			} else {
				model.addAttribute("fnSelected",
						Constants.HEADING_NIC_LOST_DAMAGE);
				ModelAndView modelAndView = new ModelAndView(
						"investigation.investigationLost.investigationLostList",
						null);
				investigationAssignmentData.cleanUpForNextPage();
				modelAndView.addObject("formData", investigationAssignmentData);
				return modelAndView;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@RequestMapping(value = { "/startDetailInvestigation/{jobId}" })
	public ModelAndView startinvestigationLostcompare(@PathVariable long jobId,
			HttpServletRequest httpRequest, Model model) throws Throwable {
		logger.info("NIC Start Investigation Lost compare");

		return this.startinvestigationLostcompare(jobId, httpRequest, model,
				null);
	}

	public ModelAndView startinvestigationLostcompare(long jobId,
			HttpServletRequest httpRequest, Model model,
			List<String> listOfMessages) throws Throwable {
		logger.info("NIC Start Investigation Lost compare, listOfMessages");

		ModelAndView modelAndView = new ModelAndView(
				"investigation.investigationLost.startInvestigationLost.compare");

		this.initializeModelAndViewForms(modelAndView);

		NicUploadJob jobDetails = uploadJobService.findById(jobId);
		String mainTransactionId = jobDetails.getTransactionId();

		// 05-02-2018: trung show img và thông tin cơ bản
		String photoStr = null;
		NicTransaction nicTransaction = null;

		nicTransaction = nicTransactionService.findById(jobDetails
				.getTransactionId());// TRung thêm
		List<NicTransactionAttachment> photoList = nicTransactionAttachmentService
				.findNicTransactionAttachments(jobDetails.getTransactionId(),
						NicTransactionAttachment.DOC_TYPE_PHOTO_CAPTURE,
						NicTransactionAttachment.DEFAULT_SERIAL_NO).getListModel();
		if (CollectionUtils.isNotEmpty(photoList) && photoList.size() > 0) {
			photoStr = Base64.encodeBase64String(photoList.get(0).getDocData());
		}
		//Phúc chỉnh format ngày, dân tộc...
		if (nicTransaction.getNicRegistrationData().getDateOfBirth() != null) {
			String dob = HelperClass.convertDateToString1(nicTransaction
					.getNicRegistrationData().getDateOfBirth());
			nicTransaction.setDateOfBirthDesc(dob);
			// nicTransaction.getNicRegistrationData().setDateOfBirth(HelperClass.convertStringToDate1(dob));
		}
		if (nicTransaction.getNicRegistrationData().getCreateDatetime() != null) {
			String crd = HelperClass.convertDateToString1(nicTransaction
					.getNicRegistrationData().getCreateDatetime());
			nicTransaction.setCreateDesc(crd);
			// nicTransaction.getNicRegistrationData().setCreateDatetime(HelperClass.convertStringToDate1(crd));
		}
		/*if (nicTransaction.getNicRegistrationData().getDatePassport() != null) {
			String dpp = HelperClass.convertDateToString1(nicTransaction
					.getNicRegistrationData().getDatePassport());
			nicTransaction.setDatePassportDesc(dpp);
			// nicTransaction.getNicRegistrationData().setCreateDatetime(HelperClass.convertStringToDate1(crd));
		}
		if (nicTransaction.getNicRegistrationData().getDateCountry() != null) {
			String dcty = HelperClass.convertDateToString1(nicTransaction
					.getNicRegistrationData().getDateCountry());
			nicTransaction.setDateCountryDesc(dcty);
			// nicTransaction.getNicRegistrationData().setCreateDatetime(HelperClass.convertStringToDate1(crd));
		}*/
		if (nicTransaction.getNicRegistrationData().getReligion() != null) {
			String religion = codesService.getCodeValueDescByIdName(
					RegistrationConstants.CODE_RELIGION_CODE_ID, nicTransaction
							.getNicRegistrationData().getReligion(), "");
			nicTransaction.setReligionDesc(religion);
		}
		if (nicTransaction.getNicRegistrationData().getNation() != null) {
			String nation = codesService.getCodeValueDescByIdName(
					RegistrationConstants.CODE_NATION_CODE_ID, nicTransaction
							.getNicRegistrationData().getNation(), "");
			nicTransaction.setNationDesc(nation);
		}
		modelAndView.addObject("photoStr", photoStr);
		modelAndView.addObject("nicData", nicTransaction);
		// end
		modelAndView.addObject("transID", mainTransactionId);
		modelAndView.addObject("jobsID", jobId);
		List<String> messages = new ArrayList<String>();

		if (listOfMessages != null) {
			messages.addAll(listOfMessages);
		}

		if (CollectionUtils.isNotEmpty(messages)) {
			httpRequest.setAttribute("messages", messages);
		}

		return modelAndView;
	}

	// /Lấy thông tin chi tiết của giao dịch (bao gồm cả dữ liệu đã kiểm tra CPD
	// và AFIS)
	@ResponseBody
	@RequestMapping(value = "/txnDetailTrans/{txnId}/{jobId}", method = RequestMethod.GET)
	@ExceptionHandler
	public ModelAndView detailTransactionMain(WebRequest request,
			@PathVariable String txnId, @PathVariable long jobId,
			ModelMap model, HttpServletRequest httpRequest) throws Exception {
		ModelAndView mav = new ModelAndView("nic-investigation-details");
		this.InitMainInfo(request, txnId, jobId, model, httpRequest, mav);
		return mav;
	}

	@RequestMapping(value = "/applyFilter")
	public ModelAndView applyFilter(
			@ModelAttribute(value = "formData") InvestigationAssignmentData investigationAssignmentData,
			WebRequest request, HttpServletRequest httpRequest, ModelMap model) {
		logger.info("applyFilter");

		return this.getInvestigationLostList(investigationAssignmentData,
				request, httpRequest, model, 1, null, null);
	}

	// Xử lý tạo danh sách bàn giao
	@RequestMapping(value = { "/createHandover" })
	public ModelAndView createHandover(
			@ModelAttribute(value = "formData") InvestigationAssignmentData investigationHitData,
			BindingResult result, WebRequest request,
			HttpServletRequest httpRequest, Model model,
			@RequestParam("selectedJobIds") String[] checkboxvalues)
			throws Throwable {
		logger.info("createHandover");
		Boolean res_api = false;

		List<String> messages = new LinkedList<String>();
		try {
			HttpSession session = httpRequest.getSession();
			Date date = new Date();
			UserSession userSession = (UserSession) session
					.getAttribute("userSession");
			String listIdTrans = "";
			if (checkboxvalues != null) {
				for (String st : checkboxvalues) {
					List<NicUploadJob> lstUpload = uploadJobService
							.findAllByTransactionId(st);
					/*NicTransaction nicTran = nicTransactionService.findById(st);
					Collection<NicDocumentData> collectionNicDocumentData = documentDataService
							.findByTransactionId(st);
					NicDocumentData nicDocumentData = new NicDocumentData();
					for (NicDocumentData item : collectionNicDocumentData) {
						nicDocumentData = item;
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
								
								 * expireTokenA98 = Integer.parseInt(resultToken
								 * .get("expires_in")); typeTokenA98 =
								 * resultToken.get("token_type");
								 
							}
						}
						
						 * if (expireTokenA98 == 0 && tokenA98 != "") {
						 * ReAccessToken(tokenA98); }
						 

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
								
								 * expireTokenBNG = Integer.parseInt(resultToken
								 * .get("expires_in")); typeTokenBNG =
								 * resultToken.get("token_type");
								 
							}
						}
						
						 * if (expireTokenBNG == 0 && tokenBNG != "") {
						 * ReAccessToken(tokenBNG); }
						 

					} while (tokenBNG == "");

					// /TEST API ĐĂNG KÝ HỦY HỘ CHIẾU
					String urlCountry = "epp$Country";
					String urlPlace = "epp$Place";
					String urlArea = "epp$Area";
					String urlOffice = "epp$Office";
					// /Lấy dữ liệu danh mục quốc gia
					List<CountryData> listCountry = GetDataUtilAPICountry(
							urlCountry, tokenA98, typeTokenA98);
					// /Lấy dữ liệu danh mục quốc gia
					List<PlaceData> listPlace = GetDataUtilAPIPlace(urlPlace,
							tokenA98, typeTokenA98);
					// /Lấy dữ liệu danh mục khu vực
					List<AreaData> listArea = GetDataUtilAPIArea(urlArea,
							tokenA98, typeTokenA98);
					// /Lấy dữ liệu danh mục cơ quan
					List<OfficeData> listOffice = GetDataUtilAPIOffice(
							urlOffice, tokenA98, typeTokenA98);

					RequestCancel rqCancel_ = new RequestCancel();
					rqCancel_.setCode(st);
					rqCancel_.setDocType("PASSPORT");
					rqCancel_.setDocCode(nicTran.getPrevPassportNo());
					rqCancel_.setReason("LOST");
					String oTemp_ = "BNG"; String oTempName_ = "Bộ ngoại giao";String oTempSite_ = "MB";// Mặc định là từ Bộ ngoại giao do csdlchưa đầy đủ
					OfficeData officeTemp_ = new OfficeData();
					if (listOffice != null) {
						for (OfficeData item : listOffice) {
							if (item.getCode().contains(oTemp_)) {
								oTempName_ = item.getName();
								oTempSite_ = item.getSite();
								break;
							}
						}
					}
					officeTemp_.setActive(false);
					officeTemp_.setName(oTempName_);
					officeTemp_.setCode(oTemp_);
					officeTemp_.setSite(oTempSite_);
					rqCancel_.setOffice(officeTemp_);
					String split = "";
					DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
					if(nicDocumentData != null){
						Date issueTmp = nicDocumentData.getDateOfIssue();
						if(issueTmp != null){
							split = issueTmp.toString()
									.split(" ")[0];
							rqCancel_.setDateOfRegister(split);
						}
					}
					if(StringUtils.isEmpty(split))
					{
						Date now = new Date();
						split = formatter.format(now);
						rqCancel_.setDateOfRegister(split);
					}
					
					String jsonModelCancel = ConvertDataJsonCancel(rqCancel_);
					String requestUrlCancel = "http://222.252.27.89:8044/app/rest/v2/entities/epp$CancelDocument";
					String resultCancel = sendPostRequestCancel(
							requestUrlCancel, jsonModelCancel, tokenBNG,
							typeTokenBNG);
					if(resultCancel.equals("200"))
						res_api = true;
					*/
					res_api = true;
					if (res_api) {
						// Chuyển trạng thái bản ghi sang trạng thái đã chuyển
						uploadJobService
								.approveJobStatus_Confirmed(
										Long.valueOf(lstUpload.get(0)
												.getWorkflowJobId()),
										userSession.getUserName(),
										userSession.getWorkstationId(),
										NicTransactionLog.TRANSACTION_STAGE_LOST,
										NicTransactionLog.TRANSACTION_STATUS_NIC_FLAG_BCA_LOST_DAMAGE,
										NicUploadJob.RECORD_STATUS_COMPLETED);

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
						obj.setDescription("Đang xử lý gửi dữ liệu đồng bộ HC mất/hỏng sang Bộ công an..");
						queuesJobScheduleService.createQueuesJobSchedule(obj);
						
						// Lưu id sang bảng danh sách bàn giao
						listIdTrans += st + ",";
					}
					res_api = true;
				}
				if (res_api) {
					if (!StringUtils.isEmpty(listIdTrans)) {
						String packId = "";
						Random ran = new Random();
						while(StringUtils.isBlank(packId)){
							String p = "RC-CQDD-" + ran.nextInt(99999999); 
							List<NicListHandover> check = listHandoverService.findAllByPackageId(new NicListHandoverId(p, null)).getListModel();
							if(check == null || check.size() <= 0){
								packId = p;
							}
						}
						
						NicListHandover handover = new NicListHandover();
						handover.setCreateBy(userSession.getUserId());
						handover.setCreateDate(date);
						// 10/06/2020 thieu typeList, khong khoi tao NicListHandoverId
//						handover.setPackageId(packId);
						//handover.setTransactionId(listIdTrans);
						handover.setPackageName(investigationHitData
								.getUnassignAllForUserUserId());
						//handover.setTypeList(4);
						//handover.setDescription("");
						listHandoverService.createRecordHandover(handover);
						messages.add("Tạo danh sách bàn giao gửi sang Bộ công an để báo mất / hỏng thành công.");
						ModelAndView modelV = new ModelAndView("investigation.investigationLost.success");
						model.addAttribute("code", packId);
						return modelV;
					}
				}
				else
				{
					messages.add("Chưa tạo được dữ liệu gửi sang Bộ công an");
				}
			} else {
				messages.add("Chưa có bản ghi nào được chọn.");
				httpRequest.setAttribute("messages", messages);
				return new ModelAndView(
						"forward:/servlet/investigationLost/investigationLostList");
			}
		} catch (JobNoLongerAssignedToOfficerException e) {
			messages.add("Tạo danh sách bàn giao gửi sang Bộ công an để báo mất / hỏng thất bại. Công việc hiện tại không được phân công cho User này");
		} catch (Exception e) {
			e.printStackTrace();
		}

		investigationHitData = new InvestigationAssignmentData();
		httpRequest.setAttribute("messages", messages);
		return new ModelAndView(
				"forward:/servlet/investigationLost/investigationLostList");
	}

	private String ConvertDataJsonCancel(RequestCancel data)
			throws JsonGenerationException, JsonMappingException, IOException {
		ObjectMapper mapper = new ObjectMapper();
		String json = mapper.writeValueAsString(data);
		return json;
	}
}
