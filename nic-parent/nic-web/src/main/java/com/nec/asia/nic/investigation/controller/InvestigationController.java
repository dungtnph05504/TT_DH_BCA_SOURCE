package com.nec.asia.nic.investigation.controller;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintStream;
import java.io.Serializable;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
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
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.transaction.Transaction;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.builder.ReflectionToStringBuilder;
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
import org.springframework.context.ApplicationContext;
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
import com.nec.asia.nic.common.dto.RicRegistrationDocumentDTO;
import com.nec.asia.nic.comp.job.command.NicCommandExecutor;
import com.nec.asia.nic.comp.job.command.NicSubmitPersoCommand;
import com.nec.asia.nic.comp.job.dto.HitCandidateDTO;
import com.nec.asia.nic.comp.job.dto.SearchHitDto;
import com.nec.asia.nic.comp.job.utils.ApplicationContextProvider;
import com.nec.asia.nic.comp.listHandover.domain.NicListHandover;
import com.nec.asia.nic.comp.listHandover.domain.NicPersoLocations;
import com.nec.asia.nic.comp.listHandover.service.ListHandoverService;
import com.nec.asia.nic.comp.listHandover.service.PersoLocationsService;
import com.nec.asia.nic.comp.signerGovs.domain.SignerGovs;
import com.nec.asia.nic.comp.signerGovs.service.SignerGovsService;
import com.nec.asia.nic.comp.trans.dao.DocumentDataDao;
import com.nec.asia.nic.comp.trans.domain.AreaData;
import com.nec.asia.nic.comp.trans.domain.AttachmentDoc;
import com.nec.asia.nic.comp.trans.domain.CountryData;
import com.nec.asia.nic.comp.trans.domain.DocAttachment;
import com.nec.asia.nic.comp.trans.domain.DocumentInfo;
import com.nec.asia.nic.comp.trans.domain.NicDocumentData;
import com.nec.asia.nic.comp.trans.domain.NicRegistrationData;
import com.nec.asia.nic.comp.trans.domain.NicRejectionData;
import com.nec.asia.nic.comp.trans.domain.NicSearchHitResult;
import com.nec.asia.nic.comp.trans.domain.NicSearchResult;
import com.nec.asia.nic.comp.trans.domain.NicTransaction;
import com.nec.asia.nic.comp.trans.domain.NicTransactionAttachment;
import com.nec.asia.nic.comp.trans.domain.NicTransactionLog;
import com.nec.asia.nic.comp.trans.domain.NicUploadJob;
import com.nec.asia.nic.comp.trans.domain.NicWorkflowJob;
import com.nec.asia.nic.comp.trans.domain.NicWorkflowProcess;
import com.nec.asia.nic.comp.trans.domain.OfficeData;
import com.nec.asia.nic.comp.trans.domain.PassportInformation;
import com.nec.asia.nic.comp.trans.domain.PersonAttachment;
import com.nec.asia.nic.comp.trans.domain.PersonInformation;
import com.nec.asia.nic.comp.trans.domain.PlaceData;
import com.nec.asia.nic.comp.trans.domain.RequestCancel;
import com.nec.asia.nic.comp.trans.domain.type.DocumentStatus;
import com.nec.asia.nic.comp.trans.domain.type.TransactionStatus;
import com.nec.asia.nic.comp.trans.dto.AssignmentFilter;
import com.nec.asia.nic.comp.trans.dto.AssignmentFilterAll;
import com.nec.asia.nic.comp.trans.dto.InvestigationHitData;
import com.nec.asia.nic.comp.trans.dto.NicEnquiryForm;
import com.nec.asia.nic.comp.trans.dto.NicSearchHitResultHitInfoItem;
import com.nec.asia.nic.comp.trans.dto.NicUploadJobDto;
import com.nec.asia.nic.comp.trans.dto.Passport;
import com.nec.asia.nic.comp.trans.exception.JobNoLongerAssignedToOfficerException;
import com.nec.asia.nic.comp.trans.service.DocumentDataService;
import com.nec.asia.nic.comp.trans.service.NicRegistrationDataService;
import com.nec.asia.nic.comp.trans.service.NicTransactionAttachmentService;
import com.nec.asia.nic.comp.trans.service.NicTransactionService;
import com.nec.asia.nic.comp.trans.service.NicUploadJobService;
import com.nec.asia.nic.comp.trans.service.exception.NicUploadJobServiceException;
import com.nec.asia.nic.comp.trans.service.exception.TransactionServiceException;
import com.nec.asia.nic.comp.trans.service.impl.NicSearchHitResultHitScorerFpImpl;
import com.nec.asia.nic.comp.trans.service.impl.NicSearchHitResultHitScorerTextualImpl;
import com.nec.asia.nic.comp.workflowProcess.service.WorkflowProcessService;
// import com.nec.asia.nic.dx.trans.CitizenRef;
import com.nec.asia.nic.enquiry.controller.FingerprintInfo;
import com.nec.asia.nic.enquiry.controller.TransactionEnquiryController;
import com.nec.asia.nic.framework.PaginatedResult;
import com.nec.asia.nic.framework.admin.code.domain.CodeValues;
import com.nec.asia.nic.framework.admin.code.domain.Codes;
import com.nec.asia.nic.framework.admin.code.domain.Parameters;
import com.nec.asia.nic.framework.admin.code.domain.ParametersId;
import com.nec.asia.nic.framework.admin.code.service.CodesService;
import com.nec.asia.nic.framework.admin.code.service.ParametersService;
import com.nec.asia.nic.framework.admin.code.service.PaymentDefService;
import com.nec.asia.nic.framework.admin.rbac.domain.Users;
import com.nec.asia.nic.framework.admin.rbac.service.UserService;
import com.nec.asia.nic.framework.admin.site.domain.SiteGroups;
import com.nec.asia.nic.framework.admin.site.domain.SiteRepository;
import com.nec.asia.nic.framework.admin.site.service.SiteService;
import com.nec.asia.nic.framework.common.ImageUtil;
import com.nec.asia.nic.framework.controller.AbstractController;
import com.nec.asia.nic.framework.dao.DaoException;
import com.nec.asia.nic.investigation.AttachmentSource;
import com.nec.asia.nic.investigation.Base64Jp2HeaderHelper;
import com.nec.asia.nic.investigation.exception.InvalidParameterException;
import com.nec.asia.nic.util.BaseListResponse;
import com.nec.asia.nic.util.BlackListApi;
import com.nec.asia.nic.util.CitizenRefXmlConvertor;
import com.nec.asia.nic.util.CodeValueDescComparator;
import com.nec.asia.nic.util.Constants;
import com.nec.asia.nic.util.FileAttachmentApi;
import com.nec.asia.nic.util.IdListApi;
import com.nec.asia.nic.util.JobXmlConvertor;
import com.nec.asia.nic.util.PrintLocation;
import com.nec.asia.nic.util.RegistrationConstants;
import com.nec.asia.nic.util.RequestBase;
import com.nec.asia.nic.util.RequestVerifyInfo;
import com.nec.asia.nic.util.TransLogInfoXmlConvertor;
import com.nec.asia.nic.util.pagingUtil;
import com.nec.asia.nic.utils.DateUtil;
import com.nec.asia.nic.utils.HelperClass;
import com.nec.asia.nic.web.session.UserSession;

@Controller
@RequestMapping(value = "/investigation")
public class InvestigationController extends AbstractController {

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
	private NicUploadJobService uploadJobSerivce;

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

	@RequestMapping(value = "/formatWsqToJpg", headers = "Accept=application/json", method = RequestMethod.POST)
	public @ResponseBody String convertImageFormatWsqToJpg(@org.springframework.web.bind.annotation.RequestBody String imageBase64String) {
		String convertedBase64String = "";
		try {
			byte[] imageBinary = Base64.decodeBase64(imageBase64String);
			System.out.println("The image binary before conversion===="
					+ imageBinary);
			byte[] convertedBinary = null; 
					//SpidHelper.convertImageFormat(imageBinary,
					//SpidHelper.IMAGE_WSQ, SpidHelper.IMAGE_JPG);
			System.out.println("The image binary after conversion===="
					+ convertedBinary);
			convertedBase64String = Base64.encodeBase64String(convertedBinary);
			// System.out.println("The image binary after base64 conversion===="+convertedBase64String);
		} catch (Exception ex) {
			System.out
					.println("Error occured while converting the wsq image to jpeg"
							+ ex.getMessage());
		}
		return convertedBase64String;
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
	
		//Phúc thêm hàm sửa hiển thị hồ sơ AFIS
		public void funtionFixDisplayAFIS(InvestigationHit hit, String mainTransactionId){
			String fullNameM = "";
			if(hit.getMainCandidateSN() != null && !hit.getMainCandidateSN().equals("")) fullNameM += hit.getMainCandidateSN();
			if(hit.getMainCandidateMN() != null && !hit.getMainCandidateMN().equals("")) fullNameM += " " + hit.getMainCandidateMN(); 
			if(hit.getMainCandidateFN() != null && !hit.getMainCandidateFN().equals("")) fullNameM += " " + hit.getMainCandidateFN();
			hit.setMainCandidateFuN(fullNameM);
			String fullNameH = "";
			if(hit.getHitCandidateSN() != null && !hit.getHitCandidateSN().equals("")) fullNameH += hit.getHitCandidateSN();
			if(hit.getHitCandidateMN() != null && !hit.getHitCandidateMN().equals("")) fullNameH += " " + hit.getHitCandidateMN(); 
			if(hit.getHitCandidateFN() != null && !hit.getHitCandidateFN().equals("")) fullNameH += " " + hit.getHitCandidateFN();
			hit.setHitCandidateFuN(fullNameH);
			if(hit.getHitCandidateListTransId() != null){
				NicRegistrationData regData = regDataService.getNicDataById(hit.getHitCandidateListTransId().toString());	
				//hit.setHitOverseasAddressCountry(regData.getOverseasCountry() != null ? regData.getOverseasCountry() : "");
				//hit.setHitAddress5(regData.getAddressLine5() != null ? regData.getAddressLine5() : "");
				//hit.setHitAddress4(regData.getAddressLine4() != null ? regData.getAddressLine4() : "");
				if(regData != null){
					hit.setHitOverseasAddressCountry(codesService.getCodeValueDescByIdName(RegistrationConstants.NATIONALITY_CODE_ID, regData.getOverseasCountry() != null ? regData.getOverseasCountry() : "", ""));
					hit.setHitAddress5(codesService.getCodeValueDescByIdName(RegistrationConstants.CODE_ID_DISTRICT, regData.getAddressLine5() != null ? regData.getAddressLine5() : "", ""));
					hit.setHitAddress4(codesService.getCodeValueDescByIdName(RegistrationConstants.CODE_ID_TOWN, regData.getAddressLine4() != null ? regData.getAddressLine4() : "", ""));						
				}
			}
			NicRegistrationData regDataM = regDataService.getNicDataById(mainTransactionId);	
			//hit.setMainOverseasAddressCountry(regDataM.getOverseasCountry() != null ? regDataM.getOverseasCountry() : "");
			//hit.setMainAddress5(regDataM.getAddressLine5() != null ? regDataM.getAddressLine5() : "");
			//hit.setMainAddress4(regDataM.getAddressLine4() != null ? regDataM.getAddressLine4() : "");
			if(regDataM != null){
				hit.setMainOverseasAddressCountry(codesService.getCodeValueDescByIdName(RegistrationConstants.NATIONALITY_CODE_ID, regDataM.getOverseasCountry() != null ? regDataM.getOverseasCountry() : "", ""));
				hit.setMainAddress5(codesService.getCodeValueDescByIdName(RegistrationConstants.CODE_ID_DISTRICT, regDataM.getAddressLine5() != null ? regDataM.getAddressLine5() : "", ""));
				hit.setMainAddress4(codesService.getCodeValueDescByIdName(RegistrationConstants.CODE_ID_TOWN, regDataM.getAddressLine4() != null ? regDataM.getAddressLine4() : "", ""));					
			}
			hit.setMainCandidateDateOfApplication(hit.getMainCandidateDateOfApplication() != null ? HelperClass.convertMonthWordToMonthNumber(hit.getMainCandidateDateOfApplication().toString()) : "");
			hit.setHitCandidateDateOfApplication(hit.getHitCandidateDateOfApplication() != null ? HelperClass.convertMonthWordToMonthNumber(hit.getHitCandidateDateOfApplication().toString()) : "");
			hit.setMainCandidateDOB(hit.getMainCandidateDOB() != null ? HelperClass.convertMonthWordToMonthNumber(hit.getMainCandidateDOB().toString()) : "");
			hit.setHitCandidateDOB(hit.getHitCandidateDOB() != null ? HelperClass.convertMonthWordToMonthNumber(hit.getHitCandidateDOB().toString()) : "");
			hit.setMainCandidatePreviousPassportIssueDate(hit.getMainCandidatePreviousPassportIssueDate() != null ? HelperClass.convertMonthWordToMonthNumber(hit.getMainCandidatePreviousPassportIssueDate().toString()) : "");
			hit.setHitCandidateDocumentPassportIssuedDate(hit.getHitCandidateDocumentPassportIssuedDate() != null ? HelperClass.convertMonthWordToMonthNumber(hit.getHitCandidateDocumentPassportIssuedDate().toString()) : "");
			hit.setHitCandidateDocumentPassportExpirationDate(hit.getHitCandidateDocumentPassportExpirationDate() != null ? HelperClass.convertMonthWordToMonthNumber(hit.getHitCandidateDocumentPassportExpirationDate().toString()) : "");
			hit.setMainCandidateReleaseDate(hit.getMainCandidateReleaseDate() != null ? HelperClass.convertMonthWordToMonthNumber(hit.getMainCandidateReleaseDate().toString()) : "");
			hit.setHitCandidateReleaseDate(hit.getHitCandidateReleaseDate() != null ? HelperClass.convertMonthWordToMonthNumber(hit.getHitCandidateReleaseDate().toString()) : "");
		}

	@RequestMapping(value = { "/startInvestigationCompare/{jobId}" })
	public ModelAndView startinvestigationcompare(@PathVariable long jobId,
			HttpServletRequest httpRequest, Model model) throws Throwable {
		logger.info("NIC Start Investigation compare");

		return this.startinvestigationcompare(jobId, httpRequest, model, null);
	}

	public ModelAndView startinvestigationcompare(long jobId,
			HttpServletRequest httpRequest, Model model,
			List<String> listOfMessages) throws Throwable {
		logger.info("NIC Start Investigation compare, listOfMessages");

		this.prepareModelStuff(model);

		ModelAndView modelAndView = new ModelAndView(
				"investigation.startinvestigation.compare");

		NicUploadJob jobDetails = uploadJobService.findById(jobId);

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

		logger.info("invHits.size():" + invHits.size());
		logger.info("invHits_error.size():" + invHits_error.size());
		List<InvestigationHit> invHits_displayThis = null;
		if (invHits.size() > 0) {
			//Phúc fix tên đầy đủ
			for(InvestigationHit hit : invHits){
				funtionFixDisplayAFIS(hit, mainTransactionId);
			}
			invHits_displayThis = invHits;
			modelAndView.addObject("inv_noHit", new Boolean(false));
		} else {
			//Phúc fix tên đầy đủ
			for(InvestigationHit hit : invHits_error){
				funtionFixDisplayAFIS(hit, mainTransactionId);
			}
			invHits_displayThis = invHits_error;
			modelAndView.addObject("inv_noHit", new Boolean(true));
		}

		// 05-02-2018: trung show img và thông tin cơ bản
		String photoStr = null;
		NicTransaction nicTransaction = null;

		if (invHits.size() <= 0 && invHits_error.size() <= 0
				&& invHits_all.size() <= 0) {// trung thêm điều kiện khi show
												// thông tin bản ghi ko dính
												// investigation
			modelAndView.addObject("inv_none", new Boolean(true));
			nicTransaction = nicTransactionService.findById(jobDetails
					.getTransactionId());// TRung thêm
			List<NicTransactionAttachment> photoList = nicTransactionAttachmentService
					.findNicTransactionAttachments(
							jobDetails.getTransactionId(),
							NicTransactionAttachment.DOC_TYPE_PHOTO_CAPTURE,
							NicTransactionAttachment.DEFAULT_SERIAL_NO).getListModel();
			if (CollectionUtils.isNotEmpty(photoList) && photoList.size() > 0) {
				photoStr = Base64.encodeBase64String(photoList.get(0)
						.getDocData());
			}
			//phúc edit lại thông tin
			if(nicTransaction.getNicRegistrationData() != null && nicTransaction.getNicRegistrationData().getCreateDatetime() != null){
				String dateDesc = HelperClass.convertDateToString1(nicTransaction.getNicRegistrationData().getCreateDatetime());
				nicTransaction.setCreateDesc(dateDesc);
			}
//			if(nicTransaction != null && nicTransaction.getNicRegistrationData() != null){
//				String address5 = codesService.getCodeValueDescByIdName(RegistrationConstants.CODE_ID_DISTRICT, nicTransaction.getNicRegistrationData().getAddressLine5() != null ? nicTransaction.getNicRegistrationData().getAddressLine5() : "", "");
//				if(!StringUtils.isEmpty(address5)){
//					nicTransaction.getNicRegistrationData().setAddressLine5(address5);
//				}else{
//					nicTransaction.getNicRegistrationData().setAddressLine5(codesService.getCodeValueDescByIdName(RegistrationConstants.NATIONALITY_CODE_ID, nicTransaction.getNicRegistrationData().getOverseasCountry() != null ? nicTransaction.getNicRegistrationData().getOverseasCountry() : "", ""));
//				}
//				String dddress4 = codesService.getCodeValueDescByIdName(RegistrationConstants.CODE_ID_TOWN, nicTransaction.getNicRegistrationData().getAddressLine4() != null ? nicTransaction.getNicRegistrationData().getAddressLine4() : "", "");	
//				if(!StringUtils.isEmpty(dddress4)){
//					nicTransaction.getNicRegistrationData().setAddressLine4(dddress4);
//				}else{
//					nicTransaction.getNicRegistrationData().setAddressLine4(codesService.getCodeValueDescByIdName(RegistrationConstants.NATIONALITY_CODE_ID, nicTransaction.getNicRegistrationData().getOverseasCountry() != null ? nicTransaction.getNicRegistrationData().getOverseasCountry() : "", ""));
//				}
//			}
		} else {
			modelAndView.addObject("inv_none", new Boolean(false));
		}
		modelAndView.addObject("photoStr", photoStr);
		modelAndView.addObject("nicData", nicTransaction);
		// end
		modelAndView.addObject("transID", mainTransactionId);
		modelAndView.addObject("jobsID", jobId);
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

					String mainCandidateFathersN = nicRegistrationData
							.getFatherFullname();
					/*String mainCandidateFathersSN = nicRegistrationData
							.getFatherSurname();
					String mainCandidateFathersMN = nicRegistrationData
							.getFatherMiddlename();*/
					String mainCandidateMothersN = nicRegistrationData
							.getMotherFullname();
					/*String mainCandidateMothersSN = nicRegistrationData
							.getMotherSurname();
					String mainCandidateMothersMN = nicRegistrationData
							.getMotherMiddlename();*/
					String mainCandidateSpouseFN = nicRegistrationData
							.getSpouseFullname();
					/*String mainCandidateSpouseSN = nicRegistrationData
							.getSpouseSurname();
					String mainCandidateSpouseMN = nicRegistrationData
							.getSpouseMiddlename()*/;

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
					invHit.addObject("mainCandidateFathersN",
							mainCandidateFathersN);
					/*invHit.addObject("mainCandidateFathersSN",
							mainCandidateFathersSN);
					invHit.addObject("mainCandidateFathersMN",
							mainCandidateFathersMN);*/
					invHit.addObject("mainCandidateMothersN",
							mainCandidateMothersN);
					/*invHit.addObject("mainCandidateMothersSN",
							mainCandidateMothersSN);
					invHit.addObject("mainCandidateMothersMN",
							mainCandidateMothersMN);*/
					invHit.addObject("mainCandidateSpouseFN",
							mainCandidateSpouseFN);
					/*invHit.addObject("mainCandidateSpouseSN",
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

	private void processMainCandidateAttachments(
			HttpServletRequest httpRequest, String mainTransactionId,
			InvestigationHit invHit) throws InvalidParameterException {

		String attachmentSource = null;
		try {
			Parameters parameter = this.parametersService
					.getParamDetails(
							InvestigationController.PARA_TRANSACTION_ATTACHMENT_SOURCE_SCOPE,
							InvestigationController.PARA_TRANSACTION_ATTACHMENT_SOURCE_VALUE);
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

	private void processMainCandidateAttachments_thru_INTERNAL(
			HttpServletRequest httpRequest, String mainTransactionId,
			InvestigationHit invHit) {

		boolean ignoreJ2k = false;
		{
			try {
				Parameters parameter = this.parametersService
						.getParamDetails(
								InvestigationController.PARA_TRANSACTION_ATTACHM_IGNORE_J2K_SCOPE,
								InvestigationController.PARA_TRANSACTION_ATTACHM_IGNORE_J2K_VALUE);
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

	private void processMainCandidateAttachments_thru_DMS(
			String mainTransactionId, InvestigationHit invHit)
			throws InvalidParameterException {

		String urlPrefix = null;
		try {
			Parameters parameter = this.parametersService.getParamDetails(
					InvestigationController.PARA_SCOPE_SYSTEM,
					InvestigationController.PARA_NAME_SYSTEM_DMS_URL);
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
					String hitCandidateFathersN = hitNicRegistrationData
							.getFatherFullname();
					/*String hitCandidateFathersSN = hitNicRegistrationData
							.getFatherSurname();
					String hitCandidateFathersMN = hitNicRegistrationData
							.getFatherMiddlename();*/
					String hitCandidateMothersN = hitNicRegistrationData
							.getMotherFullname();
					/*String hitCandidateMothersSN = hitNicRegistrationData
							.getMotherSurname();
					String hitCandidateMothersMN = hitNicRegistrationData
							.getMotherMiddlename();*/
					String hitCandidateSpouseFN = hitNicRegistrationData
							.getSpouseFullname();
					/*String hitCandidateSpouseSN = hitNicRegistrationData
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
					invHit.addObject("hitCandidateFathersN",
							hitCandidateFathersN);
					/*invHit.addObject("hitCandidateFathersSN",
							hitCandidateFathersSN);
					invHit.addObject("hitCandidateFathersMN",
							hitCandidateFathersMN);*/
					invHit.addObject("hitCandidateMothersN",
							hitCandidateMothersN);
					/*invHit.addObject("hitCandidateMothersSN",
							hitCandidateMothersSN);
					invHit.addObject("hitCandidateMothersMN",
							hitCandidateMothersMN);*/
					invHit.addObject("hitCandidateSpouseFN",
							hitCandidateSpouseFN);
					/*invHit.addObject("hitCandidateSpouseSN",
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
								InvestigationController.PARA_TRANSACTION_ATTACHM_IGNORE_J2K_SCOPE,
								InvestigationController.PARA_TRANSACTION_ATTACHM_IGNORE_J2K_VALUE);
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
										InvestigationController.PARA_TX_ATTCH_J2K_CONVERT_AT_SERVER_SCOPE,
										InvestigationController.PARA_TX_ATTCH_J2K_CONVERT_AT_SERVER_VALUE);
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

	private String getPassportStatus_forDisplay(String status) {
		try {
			return DocumentStatus.getInstance(status).getName();
		} catch (Exception e) {
			return status;
		}
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
			NicSearchHitResult nicSearchHitResult = null; /*this.uploadJobService
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

	private void processTextualMatchInfo(String hitTransactionId,
			InvestigationHit invHit,
			Set<String> searchTypesForThisTransactionIdHit, Long workflowId) {

		if (Collections.disjoint(searchTypesForThisTransactionIdHit,
				NicSearchResult.TYPE_SEARCH__THAT_ARE_TEXT_BASED)) {
			invHit.addObject("hitCandidateHitInfo", null);
			return;
		}

		try {
			NicSearchHitResult nicSearchHitResult = null; /*this.uploadJobService
					.getAllSearchHitResult(workflowId, hitTransactionId,
							NicSearchResult.TYPE_SEARCH__THAT_ARE_TEXT_BASED,
							new NicSearchHitResultHitScorerTextualImpl())
					.get(0);*/

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

	private void processOneReferringToCodeTable(String key,
			String codeTableKey, String modelItemName, ModelAndView modelAndView) {
		if (key == null) {
			modelAndView.addObject(modelItemName, "");
		} else {
			CodeValues codeValue = codesService.getCodeValueByIdName(
					codeTableKey, key);
			if (codeValue != null) {
				modelAndView.addObject(modelItemName,
						codeValue.getCodeValueDesc());
			} else {
				modelAndView.addObject(modelItemName, key);
			}
		}
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

	public List<SearchHitDto> getAllHits(long jobId, String mainTransactionId)
			throws NicUploadJobServiceException {

		// List<HitCandidateDTO> allHitsForJobId =
		// uploadJobService.getAfisHitCandidateListByJobId(jobId);
		return uploadJobService
				.getAllHitsForJobId_1PerHitTransactionId_anySearchResultId(jobId);
	}

	private Long getSearchResultId(NicUploadJob jobDetails) {

		if (jobDetails != null) {
			if (jobDetails.getNicSearchResults() != null) {
				Long searchResultId = null;
				// get the last search result.
				for (NicSearchResult sr : jobDetails.getNicSearchResults()) {
					if (searchResultId != null) {
						if (searchResultId.longValue() < sr.getSearchResultId()
								.longValue())
							searchResultId = sr.getSearchResultId();
					} else {
						searchResultId = sr.getSearchResultId();
					}
				}
				logger.info("searchResultId:" + searchResultId);
				return searchResultId;
			} else {
				logger.info("searchResultId:" + null);
				return null;
			}
		} else {
			logger.info("searchResultId:" + null);
			return null;
		}
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

	public void initializeModelAndViewForms(ModelAndView mav) {
		mav.addObject("investigationHitData", new InvestigationHitData());
	}

	private void prepareModelStuff(Model model) {
		model.addAttribute("fnSelected", Constants.HEADING_NIC_INVESTIGATION);
	}

	@RequestMapping(value = { "/reject" })
	public ModelAndView reject(
			@ModelAttribute(value = "investigationHitData") InvestigationHitData investigationHitData,
			BindingResult result, WebRequest request,
			HttpServletRequest httpRequest, Model model) throws Throwable {
		logger.info("reject");

		this.dumpHitData(investigationHitData);

		List<String> messages = new LinkedList<String>();
		try {
			List<InvestigationHitData> hits = this
					.get_submitHitDecision_hits(investigationHitData);

			HttpSession session = httpRequest.getSession();
			UserSession userSession = (UserSession) session
					.getAttribute("userSession");

			uploadJobService.rejectApplication(hits,
					investigationHitData.getJobRejectRemarks(),
					userSession.getUserName(), userSession.getWorkstationId(),
					NicTransactionLog.TRANSACTION_STAGE_INVESTIGATION,
					NicTransactionLog.TRANSACTION_STATUS_NIC_REJECTED);

			messages.add("Bản ghi đã được từ chối thành công.");
		} catch (JobNoLongerAssignedToOfficerException e) {
			messages.add("Bản ghi từ chối không thành công. Bản ghi không được phân công cho User này.");
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return new ModelAndView(
				"forward:/servlet/investigation/investigation");
		/*return this.continueAndGetNextRecord(httpRequest, model, messages);*/
	}

	@RequestMapping(value = { "/reject_noHit" })
	public ModelAndView reject_noHit(
			@ModelAttribute(value = "investigationHitData") InvestigationHitData investigationHitData,
			BindingResult result, WebRequest request,
			HttpServletRequest httpRequest, Model model) throws Throwable {
		logger.info("reject_noHit");

		List<String> messages = new LinkedList<String>();
		try {
			HttpSession session = httpRequest.getSession();
			UserSession userSession = (UserSession) session
					.getAttribute("userSession");

			uploadJobService.rejectJob(
					investigationHitData.getJobRejectRemarks(),
					Long.valueOf(investigationHitData.getUploadJobId()),
					userSession.getUserName(), userSession.getWorkstationId(),
					NicRejectionData.rejectReason_investigation,
					NicTransactionLog.TRANSACTION_STAGE_INVESTIGATION,
					NicTransactionLog.TRANSACTION_STATUS_NIC_REJECTED);

			messages.add("Từ chối bản ghi thành công.");
		} catch (JobNoLongerAssignedToOfficerException e) {
			logger.info("rejection failed");
			messages.add("Từ chối bản ghi thất bại. Bản ghi không được phân công cho User này.");
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return new ModelAndView(
				"forward:/servlet/investigation/investigation");
		/*return this.continueAndGetNextRecord(httpRequest, model, messages);*/
	}

	@RequestMapping(value = { "/approve" })
	public ModelAndView approve(
			@ModelAttribute(value = "investigationHitData") InvestigationHitData investigationHitData,
			BindingResult result, WebRequest request,
			HttpServletRequest httpRequest, Model model) throws Throwable {
		logger.info("approve");

		this.dumpHitData(investigationHitData);

		List<String> messages = new LinkedList<String>();
		try {
			List<InvestigationHitData> hits = this
					.get_submitHitDecision_hits(investigationHitData);

			HttpSession session = httpRequest.getSession();
			UserSession userSession = (UserSession) session
					.getAttribute("userSession");

			uploadJobService.approveApplication(hits,
					investigationHitData.getJobApproveRemarks(),
					userSession.getUserName(), userSession.getWorkstationId(),
					NicTransactionLog.TRANSACTION_STAGE_INVESTIGATION,
					NicTransactionLog.TRANSACTION_STATUS_NIC_APPROVED);

			if (investigationHitData.getPassportsToCancel_asList().size() > 0) {
				messages.add("Phê duyệt bản ghi thành công.");
				//messages.add("The approval of the application, and cancellation of the selected passport/s was successful.");
			} else {
				messages.add("Phê duyệt bản ghi thành công.");
			}
			//Kiểm tra bảng WorkflowProcess
			NicWorkflowProcess objW = null;
			Boolean checkW = true;
			try {
				logger.info("Get data WorkflowProcess");
				List<NicWorkflowProcess> lstW = workflowProcessService.findWorkflowProcessByCriteria(NicWorkflowProcess.INVESTIGATION);
				if(lstW != null && lstW.size() > 0){
					objW = new NicWorkflowProcess();
					objW = lstW.get(0);
					
						NicUploadJob obj = uploadJobService.findById(Long.valueOf(hits.get(0).getUploadJobId()));
						//Kiểm tra bước kế tiếp sau khi phân công
						switch (objW.getWorkflowEnd()) {
						case NicWorkflowProcess.PERSO:
							try {
								nicTransactionService.updateOnApprove(obj.getTransactionId(), "SYSTEM", "BAF-NIC-DEV");
								//Xử lý gán điểm in cho bản ghi
								NicTransaction nicTrans = nicTransactionService.findById(obj.getTransactionId());
								List<NicPersoLocations> listPerso = persoLocationsService.findAll();
								/*if(listPerso != null && listPerso.size() > 0){
									for(NicPersoLocations l : listPerso){
										if(!StringUtils.isEmpty(l.getIssuePlace())){
											String[] arraySite = l.getIssuePlace().split(",");
											boolean contains = java.util.Arrays.asList(arraySite).contains(nicTrans.getIssSiteCode());
											if(contains){
												nicTrans.setPrintPerso(l.getIdPerso());
												break;
											}
											else
											{
												nicTrans.setPrintPerso((long) 1);
											}
										}
									}
								}
								else
								{
									nicTrans.setPrintPerso((long) 1);
								}*/
								
								//Xử lý duyệt sang IN
								uploadJobService
										.approveJobStatus_Confirmed(
												Long.valueOf(obj.getWorkflowJobId()),
												"SYSTEM",
												"BAF-NIC-DEV",
												NicTransactionLog.TRANSACTION_STAGE_INVESTIGATION,
												NicTransactionLog.TRANSACTION_STATUS_NIC_CONFIRM_BOSS,
												NicUploadJob.RECORD_STATUS_APPROVE_PERSO);
								
								nicTransactionService.saveOrUpdate(nicTrans);
								
								logger.info("process send to step PERSO");
							} catch (NicUploadJobServiceException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							} catch (JobNoLongerAssignedToOfficerException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							break;
						case NicWorkflowProcess.LEADERS_APPROVAL:
							uploadJobService.approveJobStatus_Confirmed(
									Long.valueOf(obj.getWorkflowJobId()),
									"SYSTEM",
									"BAF-NIC-DEV",
									NicTransactionLog.TRANSACTION_STAGE_INVESTIGATION,
									NicTransactionLog.TRANSACTION_STATUS_NIC_CONFIRMED,
									NicUploadJob.RECORD_STATUS_CONFIRMED);
							break;
						default:
							logger.info("Not found case. Record will process default");
							break;
					}
				}
				else
				{
					logger.info("Get data WorkflowProcess: NULL. Record will process default");
				}
			}
			catch (Exception e1) {
				// TODO Auto-generated catch block
				logger.error("Error when process in WorkflowProcess. Record will process default");
				e1.printStackTrace();
			}
		} catch (JobNoLongerAssignedToOfficerException e) {
			messages.add("Phê duyêt không thành công. Do bản ghi không được phân quyền cho User này.");
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return new ModelAndView(
				"forward:/servlet/investigation/investigation");
		/*return this.continueAndGetNextRecord(httpRequest, model, messages);*/
	}

	@RequestMapping(value = { "/approve_noHit" })
	public ModelAndView approve_noHit(
			@ModelAttribute(value = "investigationHitData") InvestigationHitData investigationHitData,
			BindingResult result, WebRequest request,
			HttpServletRequest httpRequest, Model model) throws Throwable {
		logger.info("approve_noHit");

		List<String> messages = new LinkedList<String>();
		try {
			HttpSession session = httpRequest.getSession();
			UserSession userSession = (UserSession) session
					.getAttribute("userSession");

			uploadJobService.approveJob(
					investigationHitData.getJobApproveRemarks(),
					Long.valueOf(investigationHitData.getUploadJobId()),
					userSession.getUserName(), userSession.getWorkstationId(),
					NicTransactionLog.TRANSACTION_STAGE_INVESTIGATION,
					NicTransactionLog.TRANSACTION_STATUS_NIC_APPROVED);

			messages.add("Phê duyệt bản ghi thành công.");
			//Kiểm tra bảng WorkflowProcess
			NicWorkflowProcess objW = null;
			Boolean checkW = true;
			try {
				logger.info("Get data WorkflowProcess");
				List<NicWorkflowProcess> lstW = workflowProcessService.findWorkflowProcessByCriteria(NicWorkflowProcess.INVESTIGATION);
				if(lstW != null && lstW.size() > 0){
					objW = new NicWorkflowProcess();
					objW = lstW.get(0);
					
						NicUploadJob obj = uploadJobService.findById(Long.valueOf(investigationHitData.getUploadJobId()));
						//Kiểm tra bước kế tiếp sau khi phân công
						switch (objW.getWorkflowEnd()) {
						case NicWorkflowProcess.PERSO:
							try {
								nicTransactionService.updateOnApprove(obj.getTransactionId(), "SYSTEM", "BAF-NIC-DEV");
								//Xử lý gán điểm in cho bản ghi
								NicTransaction nicTrans = nicTransactionService.findById(obj.getTransactionId());
								List<NicPersoLocations> listPerso = persoLocationsService.findAll();
								/*if(listPerso != null && listPerso.size() > 0){
									for(NicPersoLocations l : listPerso){
										if(!StringUtils.isEmpty(l.getIssuePlace())){
											String[] arraySite = l.getIssuePlace().split(",");
											boolean contains = java.util.Arrays.asList(arraySite).contains(nicTrans.getIssSiteCode());
											if(contains){
												nicTrans.setPrintPerso(l.getIdPerso());
												break;
											}
											else
											{
												nicTrans.setPrintPerso((long) 1);
											}
										}
									}
								}
								else
								{
									nicTrans.setPrintPerso((long) 1);
								}*/
								
								//Xử lý duyệt sang IN
								uploadJobService
										.approveJobStatus_Confirmed(
												Long.valueOf(obj.getWorkflowJobId()),
												"SYSTEM",
												"BAF-NIC-DEV",
												NicTransactionLog.TRANSACTION_STAGE_INVESTIGATION,
												NicTransactionLog.TRANSACTION_STATUS_NIC_CONFIRM_BOSS,
												NicUploadJob.RECORD_STATUS_APPROVE_PERSO);
								
								nicTransactionService.saveOrUpdate(nicTrans);
								
								logger.info("process send to step PERSO");
							} catch (NicUploadJobServiceException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							} catch (JobNoLongerAssignedToOfficerException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							break;
						case NicWorkflowProcess.LEADERS_APPROVAL:
							uploadJobService.approveJobStatus_Confirmed(
									Long.valueOf(obj.getWorkflowJobId()),
									"SYSTEM",
									"BAF-NIC-DEV",
									NicTransactionLog.TRANSACTION_STAGE_INVESTIGATION,
									NicTransactionLog.TRANSACTION_STATUS_NIC_CONFIRMED,
									NicUploadJob.RECORD_STATUS_CONFIRMED);
							break;
						default:
							logger.info("Not found case. Record will process default");
							break;
					}
				}
				else
				{
					logger.info("Get data WorkflowProcess: NULL. Record will process default");
				}
			}
			catch (Exception e1) {
				// TODO Auto-generated catch block
				logger.error("Error when process in WorkflowProcess. Record will process default");
				e1.printStackTrace();
			}
			
		} catch (JobNoLongerAssignedToOfficerException e) {
			messages.add("Phê duyêt không thành công. Do bản ghi không được phân quyền cho User này.");
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return new ModelAndView(
				"forward:/servlet/investigation/investigation");
		/*return this.continueAndGetNextRecord(httpRequest, model, messages);*/
	}

	@RequestMapping(value = { "/suspend" })
	public ModelAndView suspend(
			@ModelAttribute(value = "investigationHitData") InvestigationHitData investigationHitData,
			BindingResult result, WebRequest request,
			HttpServletRequest httpRequest, Model model) throws Throwable {
		logger.info("suspend");

		this.dumpHitData(investigationHitData);

		List<String> messages = new LinkedList<String>();
		try {
			List<InvestigationHitData> hits = this
					.get_submitHitDecision_hits(investigationHitData);

			HttpSession session = httpRequest.getSession();
			UserSession userSession = (UserSession) session
					.getAttribute("userSession");

			uploadJobService.suspendApplication(hits,
					investigationHitData.getJobSuspendRemarks(),
					userSession.getUserName(), userSession.getWorkstationId(),
					NicTransactionLog.TRANSACTION_STAGE_INVESTIGATION,
					NicTransactionLog.TRANSACTION_STATUS_NIC_SUSPENDED);

			messages.add("Tạm ngừng bản ghi thành công.");
		} catch (JobNoLongerAssignedToOfficerException e) {
			messages.add("Tạm ngừng bản ghi không thành công. Do bản ghi không được phân quyền cho User này.");
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

		return this.continueAndGetNextRecord(httpRequest, model, messages);
	}

	@RequestMapping(value = { "/suspend_noHit" })
	public ModelAndView suspend_noHit(
			@ModelAttribute(value = "investigationHitData") InvestigationHitData investigationHitData,
			BindingResult result, WebRequest request,
			HttpServletRequest httpRequest, Model model) throws Throwable {
		logger.info("suspend_noHit");

		List<String> messages = new LinkedList<String>();
		try {
			HttpSession session = httpRequest.getSession();
			UserSession userSession = (UserSession) session
					.getAttribute("userSession");

			uploadJobService.suspendJob(
					investigationHitData.getJobSuspendRemarks(),
					Long.valueOf(investigationHitData.getUploadJobId()),
					userSession.getUserName(), userSession.getWorkstationId(),
					NicTransactionLog.TRANSACTION_STAGE_INVESTIGATION,
					NicTransactionLog.TRANSACTION_STATUS_NIC_SUSPENDED);

			messages.add("Tạm ngừng bản ghi thành công.");
		} catch (JobNoLongerAssignedToOfficerException e) {
			messages.add("Tạm ngừng bản ghi không thành công. Do bản ghi không được phân quyền cho User này.");
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

		return this.continueAndGetNextRecord(httpRequest, model, messages);
	}

	public void dumpHitData(InvestigationHitData investigationHitData) {
		logger.info("getDuplicateDecision                      ["
				+ investigationHitData.getDuplicateDecision() + "]");
		logger.info("getHitTransactionId                       ["
				+ investigationHitData.getHitTransactionId() + "]");
		logger.info("getRemarks                                ["
				+ investigationHitData.getRemarks() + "]");
		logger.info("getSearchResultId                         ["
				+ investigationHitData.getSearchResultId() + "]");
		logger.info("getUploadJobId                            ["
				+ investigationHitData.getUploadJobId() + "]");
		logger.info("getJobApproveRemarks                      ["
				+ investigationHitData.getJobApproveRemarks() + "]");
		logger.info("getJobRejectRemarks                       ["
				+ investigationHitData.getJobRejectRemarks() + "]");
		logger.info("getJobSuspendRemarks                      ["
				+ investigationHitData.getJobSuspendRemarks() + "]");
		logger.info("getTransactionIdAndPassportNumbersToCancel["
				+ ReflectionToStringBuilder.toString(investigationHitData
						.getTransactionIdAndPassportNumbersToCancel()) + "]");
	}

	private ModelAndView continueAndGetNextRecord(
			HttpServletRequest httpRequest, Model model, List<String> messages)
			throws Throwable {
		HttpSession session = httpRequest.getSession();
		String userName = ((UserSession) session.getAttribute("userSession"))
				.getUserName();
		String userWorkstation = ((UserSession) session
				.getAttribute("userSession")).getWorkstationId();

		// any avialable assigned to open?
		{
			Long availableAssignedToDisplay = null;
			{
				try {
					PaginatedResult<NicUploadJobDto> pr = uploadJobService
							.findAllForInvestigationPagination(userName, 1, 1);
					if (pr != null) {
						List<NicUploadJobDto> list = pr.getRows();
						if (list != null) {
							availableAssignedToDisplay = list.get(0)
									.getUploadJobId();
						}
					}
				} catch (Exception e) {
					// e.printStackTrace();
				}
			}

			// yes
			// dispaly it with proper message
			if (availableAssignedToDisplay != null) {
				List<String> listOfMessages = new ArrayList<String>();
				if (messages != null) {
					listOfMessages.addAll(messages);
				}
				listOfMessages
						.add("Bản ghi tiếp theo để điều tra được hiển thị dưới đây.");
				return this.startinvestigationcompare(
						availableAssignedToDisplay, httpRequest, model,
						listOfMessages);
			}
		}

		// here:no available assigned to open
		{
			Long newlyAssignedToDisplay = null;
			try {
				this.assignNewInvestigation(userName, userName, userWorkstation);
				/*newlyAssignedToDisplay = (this.uploadJobService
						.findAllForInvestigationPagination(userName, 1, 1))
						.getRows().get(0).getUploadJobId();*/
			} catch (Exception ex) {
			}

			// any available unassigned?
			// yes
			// assign
			// display it with proper message
			if (newlyAssignedToDisplay != null) {
				List<String> listOfMessages = new ArrayList<String>();
				if (messages != null) {
					listOfMessages.addAll(messages);
				}
				listOfMessages
						.add("Bản ghi tiếp theo để điều tra được hiển thị dưới đây.");
				return this.startinvestigationcompare(newlyAssignedToDisplay,
						httpRequest, model, listOfMessages);
			}
			// no
			// message that there is no avaialble next
			{
				List<String> listOfMessages = new ArrayList<String>();
				if (messages != null) {
					listOfMessages.addAll(messages);
				}
				listOfMessages
						.add("Không có hồ sơ điều tra mới được tìm thấy để điều tra.");
				httpRequest.setAttribute("messages", listOfMessages);
				return new ModelAndView(
						"forward:/servlet/investigation/investigation");
			}
		}
	}

	public List<InvestigationHitData> get_submitHitDecision_hits(
			InvestigationHitData investigationHitData) {

		List<InvestigationHitData> items = new ArrayList<InvestigationHitData>();

		String[] uploadJobIds = investigationHitData.getUploadJobId().split(
				InvestigationController.DATA_DELIMITER);
		String[] hitTransactionIds = investigationHitData.getHitTransactionId()
				.split(InvestigationController.DATA_DELIMITER);
		String[] searchResultIds = investigationHitData.getSearchResultId()
				.split(InvestigationController.DATA_DELIMITER);
		String[] remarkss = investigationHitData.getRemarks().split(
				InvestigationController.DATA_DELIMITER);
		String[] duplicateDecisions = investigationHitData
				.getDuplicateDecision().split(
						InvestigationController.DATA_DELIMITER);

		for (int i = 0; i < uploadJobIds.length; i++) {
			// System.out.println("xxx"+i+"ccc"+remarkss[i]+"ccc");
			items.add(new InvestigationHitData(uploadJobIds[i],
					hitTransactionIds[i], searchResultIds[i], (remarkss[i]
							.length() == 2) ? "" : remarkss[i].substring(1,
							remarkss[i].length() - 2 + 1),
					duplicateDecisions[i], investigationHitData
							.getJobApproveRemarks(), investigationHitData
							.getJobRejectRemarks(), investigationHitData
							.getJobSuspendRemarks(), investigationHitData
							.getTransactionIdAndPassportNumbersToCancel()));
		}

		return items;
	}

	public void approveJob(String approveRemarks, long approveJobId,
			String investigationType, String officerUsername,
			String officerWorkstation) throws Throwable {

		this.uploadJobService.approveJob(approveRemarks, approveJobId,
				officerUsername, officerWorkstation);
	}

	@RequestMapping(value = "/investigation")
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
			String order = InvestigationController.DECENDING_ORDER;

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
			if (order.equalsIgnoreCase(InvestigationController.DECENDING_ORDER)) {
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
			// pr =
			// uploadJobService.findAllForInvestigationPagination(userSession.getUserName(),
			// pageNo, pageSize);
			pr = uploadJobService
					.findAllForInvestigationPagination1(
							new String[] { NicUploadJob.RECORD_STATUS_IN_PROGRESS },
							userSession.getUserName(), null,
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
								record.setPassportType(nicTransaction
										.getPassportType());
								record.setRegSiteCode(nicTransaction
										.getRegSiteCode());
								record.setPackageId(nicTransaction.getPackageId());
								record.setNin(nicTransaction.getNin());
								// record.setPriority(nicTransaction.getPriority());
								{
									try {
										//CodeValues codeValue = codesService.getCodeValueByIdName(Codes.PRIORITY,nicTransaction.getPriority().toString());
										String priority = codesService.getCodeValueDescByIdName(RegistrationConstants.CODE_PRIORITY_CODE_ID, nicTransaction.getPriority().toString(), "");
										if (priority != null) {
											record.setPriorityString(priority);
										} else {
											record.setPriorityString("");
										}
									} catch (Exception e) {
										record.setPriorityString(nicTransaction
												.getPriority() == null ? null
												: nicTransaction.getPriority()
														.toString());
									}
								}
								NicRegistrationData reg = nicTransaction.getNicRegistrationData();
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

	@RequestMapping(value = "/search", method = RequestMethod.POST)
	public ModelAndView searchJobList(WebRequest request,
			HttpServletRequest httpRequest,
			@RequestParam("search_data") String txnId, Model model)
			throws Throwable {
		logger.info("Retrieve Transaction Id =========================>> "
				+ txnId);
		httpRequest.setAttribute("pendingCount",
				uploadJobService.getPendingInvestigationsCount());
		PaginatedResult<NicUploadJobDto> pr = null;
		try {
			// NicUploadJob nicUploadJob = null;
			int pageNo = 1;
			// int pageSize = 10;
			// [22/08/2013]: Swapna Modified
			int pageSize = 10;
			int startIndex = 0;

			// Sailaja Modified for pagination
			ParametersId id = new ParametersId();
			id.setParaName(Parameters.MAX_RECORDS_PER_PAGE);
			id.setParaScope(Parameters.SCOPE_SYSTEM);
			String tableId = "row";
			String sortedElement = "codeId";
			String order = InvestigationController.DECENDING_ORDER;

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
			if (order.equalsIgnoreCase(InvestigationController.DECENDING_ORDER)) {
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
			String pageNumber = request.getParameter((new ParamEncoder(tableId)
					.encodeParameterName(TableTagParameters.PARAMETER_PAGE)));

			if (StringUtils.isNotBlank(pageNumber)
					&& StringUtils.isNumeric(pageNumber)) {
				pageNo = Integer.parseInt(pageNumber);
			}
			if (StringUtils.isNotBlank(pageNumber)) {
				startIndex = (Integer.parseInt(pageNumber) - 1) * pageSize;
			} else {
				startIndex = 10;
			}
			HttpSession session = httpRequest.getSession();
			UserSession userSession = (UserSession) session
					.getAttribute("userSession");
			pr = uploadJobService.findByTransactionId(txnId,
					userSession.getUserName());
			List<NicUploadJobDto> list = new ArrayList<NicUploadJobDto>();
			if (pr != null) {
				list = pr.getRows();
				if (list != null) {
					for (NicUploadJobDto record : list) {
						String transactionId = record.getTransactionId();
						NicTransaction nicTransaction = nicTransactionService
								.findById(transactionId);
						record.setDateOfApplication(nicTransaction
								.getDateOfApplication());
						record.setEstDateOfCollection(nicTransaction
								.getEstDateOfCollection());
						// record.setPriority(nicTransaction.getPriority());
						{
							try {
								CodeValues codeValue = codesService
										.getCodeValueByIdName(Codes.PRIORITY,
												nicTransaction.getPriority()
														.toString());
								if (codeValue != null
										&& codeValue.getCodeValueDesc() != null) {
									record.setPriorityString(codeValue
											.getCodeValueDesc());
								} else {
									record.setPriorityString(nicTransaction
											.getPriority() == null ? null
											: nicTransaction.getPriority()
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
				} else {
					request.setAttribute("jobDetailsErrorMsg", "No Data found",
							0);
				}
				model.addAttribute("nicUploadJobDto", new NicUploadJobDto());
				Map searchResultMap = new HashMap();
				searchResultMap.put("jobList", list);
				searchResultMap.put("totalRecords", pr.getTotal());
				searchResultMap.put("pageSize", pageSize);
				searchResultMap.put("txnId", txnId);
				model.addAttribute("jobList", list);
				prepareModelStuff(model);
				return new ModelAndView("investigation.investigation",
						searchResultMap);
			} else {
				return new ModelAndView("investigation.investigation", null);
			}
		} catch (Exception e) {

			e.printStackTrace();
			return null;
		}

	}

	@RequestMapping(value = { "/newInvestigation" })
	public ModelAndView NewInvestigation(HttpServletRequest request, Model model)
			throws Throwable {
		return this.NewInvestigation(request, model, null);
	}

	public ModelAndView NewInvestigation(HttpServletRequest request,
			Model model, List<String> messageItems) throws Throwable {
		List<String> messages = new LinkedList<String>();

		if (messageItems != null) {
			messages.addAll(messageItems);
		}

		HttpSession session = request.getSession();
		UserSession userSession = (UserSession) session
				.getAttribute("userSession");
		try {
			this.assignNewInvestigation(userSession.getUserName(),
					userSession.getUserName(), userSession.getWorkstationId());
			messages.add("Hồ sơ điều tra được gán thành công cho người dùng: "
					+ userSession.getUserName());
		} catch (Exception ex) {
			messages.add("Không có hồ sơ điều tra mới được tìm thấy để chỉ định.");
		}
		request.setAttribute("messages", messages);
		return new ModelAndView("forward:/servlet/investigation/investigation");
	}

	private void assignNewInvestigation(String user, String officerUserId,
			String officerWorkstationId) throws NoNewRecordException {
		try {
			uploadJobService.assignNewJobList(user.trim(), officerUserId,
					officerWorkstationId);
		} catch (Exception ex) {
			throw new NoNewRecordException();
		}
	}

	private void logMessage(Exception ex) {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		PrintStream ps = new PrintStream(baos);
		ex.printStackTrace(ps);
		logger.error(ex.getMessage() + " : \n" + new String(baos.toByteArray()));
	}

	private List<CodeValues> getCodeValueList(String codeId)
			throws NicUploadJobServiceException {
		List<CodeValues> codeValueList = codesService.findAllByCodeId(codeId);
		if (codeValueList != null) {
			Collections.sort(codeValueList, new CodeValueDescComparator());
		}

		return codeValueList;
	}

	// TUNT---------------

	// TODO: TRUNG THÊM SEARCH CHO PHẦN APPROVE JON
	@RequestMapping(value = "/searchApprove", method = RequestMethod.POST)
	public ModelAndView searchJobApproveList(WebRequest request,
			HttpServletRequest httpRequest,
			@RequestParam("search_data") String txnId, Model model)
			throws Throwable {
		logger.info("Retrieve Transaction Id =========================>> "
				+ txnId);
		httpRequest.setAttribute("pendingCount",
				uploadJobService.getPendingInvestigationsCount());
		PaginatedResult<NicUploadJobDto> pr = null;
		try {
			// NicUploadJob nicUploadJob = null;
			int pageNo = 1;
			// int pageSize = 10;
			// [22/08/2013]: Swapna Modified
			int pageSize = 10;
			int startIndex = 0;

			// Sailaja Modified for pagination
			ParametersId id = new ParametersId();
			id.setParaName(Parameters.MAX_RECORDS_PER_PAGE);
			id.setParaScope(Parameters.SCOPE_SYSTEM);
			String tableId = "row";
			String sortedElement = "codeId";
			String order = InvestigationController.DECENDING_ORDER;

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
			if (order.equalsIgnoreCase(InvestigationController.DECENDING_ORDER)) {
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
			String pageNumber = request.getParameter((new ParamEncoder(tableId)
					.encodeParameterName(TableTagParameters.PARAMETER_PAGE)));

			if (StringUtils.isNotBlank(pageNumber)
					&& StringUtils.isNumeric(pageNumber)) {
				pageNo = Integer.parseInt(pageNumber);
			}
			if (StringUtils.isNotBlank(pageNumber)) {
				startIndex = (Integer.parseInt(pageNumber) - 1) * pageSize;
			} else {
				startIndex = 10;
			}
			HttpSession session = httpRequest.getSession();
			UserSession userSession = (UserSession) session
					.getAttribute("userSession");
			pr = uploadJobService.findByTransactionIdApprove(txnId,
					userSession.getUserName());
			List<NicUploadJobDto> list = new ArrayList<NicUploadJobDto>();
			if (pr != null) {
				list = pr.getRows();
				if (list != null) {
					for (NicUploadJobDto record : list) {
						String transactionId = record.getTransactionId();
						NicTransaction nicTransaction = nicTransactionService
								.findById(transactionId);
						record.setDateOfApplication(nicTransaction
								.getDateOfApplication());
						record.setEstDateOfCollection(nicTransaction
								.getEstDateOfCollection());
						// record.setPriority(nicTransaction.getPriority());
						{
							try {
								CodeValues codeValue = codesService
										.getCodeValueByIdName(Codes.PRIORITY,
												nicTransaction.getPriority()
														.toString());
								if (codeValue != null
										&& codeValue.getCodeValueDesc() != null) {
									record.setPriorityString(codeValue
											.getCodeValueDesc());
								} else {
									record.setPriorityString(nicTransaction
											.getPriority() == null ? null
											: nicTransaction.getPriority()
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
				} else {
					request.setAttribute("jobDetailsErrorMsg", "No Data found",
							0);
				}
				model.addAttribute("nicUploadJobDto", new NicUploadJobDto());
				Map searchResultMap = new HashMap();
				searchResultMap.put("jobList", list);
				searchResultMap.put("totalRecords", pr.getTotal());
				searchResultMap.put("pageSize", pageSize);
				searchResultMap.put("txnId", txnId);
				model.addAttribute("jobList", list);
				prepareModelStuff(model);
				return new ModelAndView("investigation.approveStatus",
						searchResultMap);
			} else {
				return new ModelAndView("investigation.approveStatus", null);
			}
		} catch (Exception e) {

			e.printStackTrace();
			return null;
		}

	}

	@RequestMapping(value = "/getApproveJob")
	public ModelAndView getApproveJob(WebRequest request,
			HttpServletRequest httpRequest, ModelMap model) {
		logger.info("NIC Approve");
		PaginatedResult<NicUploadJobDto> pr = null;
		try {
			int pageNo = 1;
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
			String order = InvestigationController.DECENDING_ORDER;

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
			if (order.equalsIgnoreCase(InvestigationController.DECENDING_ORDER)) {
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
			pr = uploadJobService.findAllForApprovePagination(
					userSession.getUserName(), pageNo, pageSize, null, null);
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
					request.setAttribute("jobDetailsErrorMsg", "No Data found",
							0);
				}
				Map searchResultMap = new HashMap();
				searchResultMap.put("totalRecords", pr.getTotal());
				searchResultMap.put("pageSize", pageSize);

				model.addAttribute("jobList", list);

				model.addAttribute("fnSelected",
						Constants.HEADING_NIC_INVESTIGATION);
				return new ModelAndView("investigation.approveStatus",
						searchResultMap);
			} else {
				model.addAttribute("fnSelected",
						Constants.HEADING_NIC_INVESTIGATION);
				return new ModelAndView("investigation.approveStatus", null);
			}
			// }
		} catch (Exception e) {
			logger.info(e.getMessage());
			e.printStackTrace();
			return null;
			// return new ModelAndView("investigation.investigation", null);
		}
	}

	@RequestMapping(value = { "/startApproveStatus/{jobId}" })
	public ModelAndView startApproveStatus(@PathVariable long jobId,
			HttpServletRequest httpRequest, Model model) throws Throwable {
		logger.info("NIC Start startApproveStatus compare");

		return this.startApproveStatus(jobId, httpRequest, model, null);
	}

	public ModelAndView startApproveStatus(long jobId,
			HttpServletRequest httpRequest, Model model,
			List<String> listOfMessages) throws Throwable {
		logger.info("NIC Start Investigation compare, listOfMessages");

		this.prepareModelStuff(model);

		ModelAndView modelAndView = new ModelAndView(
				"investigation.startApproveStatus.compare");

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

		//Kiểm tra có được gửi VERIFY không
		Boolean checkVer = true;
		try {
			NicWorkflowProcess verification = workflowProcessService
					.findWorkflowProcessByCriteria(
							NicWorkflowProcess.VERIFICATION).get(0);
			if (verification.getStatus() == NicWorkflowProcess.WORKFLOW_STATUS_OFF)
				checkVer = false;
		} catch (Exception e) {
		}

		modelAndView.addObject("checkVer", checkVer);
				
		return modelAndView;
	}

	// /Phê duyệt bản ghi trong danh sách chờ phê duyệt
	@RequestMapping(value = { "/approveStatus" })
	public ModelAndView approveStatus(
			@ModelAttribute(value = "investigationHitData") InvestigationHitData investigationHitData,
			BindingResult result, WebRequest request,
			HttpServletRequest httpRequest, Model model) throws Throwable {
		logger.info("Start Approve record continuos submit-Perso step");

		List<String> messages = new LinkedList<String>();
		try {
			HttpSession session = httpRequest.getSession();
			UserSession userSession = (UserSession) session
					.getAttribute("userSession");

			uploadJobService.approveJobStatus(
					Long.valueOf(investigationHitData.getUploadJobId()),
					userSession.getUserName(), userSession.getWorkstationId(),
					NicTransactionLog.TRANSACTION_STAGE_INVESTIGATION,
					NicTransactionLog.TRANSACTION_STATUS_NIC_APPROVED);

			messages.add("Phê duyệt bản ghi thành công");
		} catch (JobNoLongerAssignedToOfficerException e) {
			messages.add("Đã có lỗi xảy ra. Vui lòng thử lại");
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		// return this.continueAndGetNextRecord(httpRequest, model, messages);
		return new ModelAndView("investigation.approveStatus", null);
	}

	// TRUNG approve Assignment
	@RequestMapping(value = "/getApproveAssignment")
	public ModelAndView getApproveAssignmentList(
			@ModelAttribute(value = "formData") InvestigationAssignmentData investigationAssignmentData,
			WebRequest request, HttpServletRequest httpRequest, ModelMap model) {
		logger.info("getInvestigationAssignmentList");

		int pageNo = 1;

		String tableId = "row";
		String pageNumber = request.getParameter((new ParamEncoder(tableId)
				.encodeParameterName(TableTagParameters.PARAMETER_PAGE)));
		if (StringUtils.isNotBlank(pageNumber)
				&& StringUtils.isNumeric(pageNumber)) {
			pageNo = Integer.parseInt(pageNumber);
		}

		return this.getApproveAssignment(investigationAssignmentData, request,
				httpRequest, model, pageNo, null, null);
	}

	public ModelAndView getApproveAssignment(
			@ModelAttribute(value = "formData") InvestigationAssignmentData investigationAssignmentData,
			WebRequest request, HttpServletRequest httpRequest, ModelMap model,
			int pageNo, List<String> listOfMessages, List<String> listOfErrors) {
		logger.info("getInvestigationAssignmentList pageNo");
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

			// pr = uploadJobService.findAllForInvestigationPagination(
			// investigationAssignmentData.getCurrentlyAssignedToUserId(),
			// pageNo, pageSize,
			// investigationAssignmentData.assignedOnly(),
			// new
			// AssignmentFilter(investigationAssignmentData.getSearchTransactionId()));
			pr = uploadJobService.findAllForAssignmentJobPagination(
					new String[] { NicUploadJob.RECORD_STATUS_INITIAL,
							NicUploadJob.RECORD_STATUS_IN_PROGRESS },
					investigationAssignmentData.getCurrentlyAssignedToUserId(),
					pageNo,
					pageSize,
					investigationAssignmentData.assignedOnly(),
					new AssignmentFilter(investigationAssignmentData
							.getSearchTransactionId()));

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
					request.setAttribute("jobDetailsErrorMsg", "No Data found",
							0);
				}
				Map searchResultMap = new HashMap();
				searchResultMap.put("totalRecords", pr.getTotal());
				searchResultMap.put("pageSize", pageSize);

				model.addAttribute("jobList", list);

				model.addAttribute("fnSelected",
						Constants.HEADING_NIC_INVESTIGATION_ASSIGNMENT);
				ModelAndView modelAndView = new ModelAndView(
						"investigation.approveAssignmentList", searchResultMap);
				investigationAssignmentData.cleanUpForNextPage();
				modelAndView.addObject("formData", investigationAssignmentData);
				return modelAndView;
			} else {
				model.addAttribute("fnSelected",
						Constants.HEADING_NIC_INVESTIGATION_ASSIGNMENT);
				ModelAndView modelAndView = new ModelAndView(
						"investigation.approveAssignmentList", null);
				investigationAssignmentData.cleanUpForNextPage();
				modelAndView.addObject("formData", investigationAssignmentData);
				return modelAndView;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
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

	@RequestMapping(value = "/passportNoEdit", method = RequestMethod.POST)
	@ExceptionHandler
	@ResponseBody
	public boolean saveDataPassport(
			@org.springframework.web.bind.annotation.RequestBody String response,
			HttpServletRequest httpRequest) throws JsonParseException,
			JsonMappingException, IOException {
		boolean result = false;
		try {

			DataJson jsonData = new DataJson();

			try {
				if (response.split("&")[0].split("=")[1] != null)
					jsonData.setPassportNo(response.split("&")[0].split("=")[1]);
			} catch (Exception ex) {
			}
			try {
				if (response.split("&")[1].split("=")[1] != null)
					jsonData.setPassportYear(response.split("&")[1].split("=")[1]);
			} catch (Exception ex) {
			}
			jsonData.setTransactionId(response.split("&")[2].split("=")[1]);

			// DataJson jsonData = new DataJson();
			String ppNo = jsonData.getPassportNo();
			String ppYear = jsonData.getPassportYear();
			String transactionId = jsonData.getTransactionId();
			HttpSession session = httpRequest.getSession();
			UserSession userSession = (UserSession) session
					.getAttribute("userSession");

			if (ppNo != null && ppNo != "") {
				uploadJobService.getSaveTempPassportNo(transactionId, ppNo);
				/*
				 * result = documentDataService.updatePassportNo(transactionId,
				 * userSession.getUserName(), userSession.getWorkstationId(),
				 * ppNo);
				 */
			}
			if (ppYear != null && ppYear != "") {
				int year = Integer.parseInt(ppYear);
				result = nicTransactionService.updateValidateTime(
						transactionId, userSession.getUserName(),
						userSession.getWorkstationId(), year);
			}

		} catch (Exception ex) {
		}
		return result;
	}

	@RequestMapping(value = { "/checkSignDetail/{transactonId}" })
	public ModelAndView checkSignDetail(@PathVariable String transactonId,
			HttpServletRequest httpRequest, Model model) throws Throwable {
		logger.info("NIC Start checkSignDetail compare");

		return this.checkSignDetail(transactonId, httpRequest, model, null);
	}

	public ModelAndView checkSignDetail(String transactionId,
			HttpServletRequest httpRequest, Model model,
			List<String> listOfMessages) throws Throwable {
		logger.info("NIC Start Check Sign compare, listOfMessages");

		this.prepareModelStuff(model);

		ModelAndView modelAndView = new ModelAndView(
				"investigation.checkSign.compare");

		NicTransaction nicTransaction = nicTransactionService
				.findById(transactionId);

		String mainTransactionId = transactionId;

		this.initializeModelAndViewForms(modelAndView);

		// trung show img CHU KY
		String photoStr = null;
		List<NicTransactionAttachment> photoList = nicTransactionAttachmentService
				.findNicTransactionAttachments(transactionId, "SIGN_DOC",
						NicTransactionAttachment.DEFAULT_SERIAL_NO).getListModel();
		if (CollectionUtils.isNotEmpty(photoList) && photoList.size() > 0) {
			photoStr = Base64.encodeBase64String(photoList.get(0).getDocData());
		}
		modelAndView.addObject("photoStr", photoStr);
		// end show img CHU KY

		modelAndView.addObject("nicData", nicTransaction);

		List<String> messages = new ArrayList<String>();

		if (listOfMessages != null) {
			messages.addAll(listOfMessages);
		}

		if (CollectionUtils.isNotEmpty(messages)) {
			httpRequest.setAttribute("messages", messages);
		}

		return modelAndView;
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
			String requestUrl = "http://192.168.1.15:8044/app/rest/v2/services/epp_DocumentService/syncWithoutProcessing";
			Boolean resultSync = sendPostRequestSync(requestUrl, jsonModel,
					tokenBNG, typeTokenBNG);

			if (resultSync) {
				//messages.add("Tạo danh sách bàn giao gửi sang Bộ công an để báo mất / hỏng thành công.");
				resultMess = "Gửi dữ liệu sang Bộ công an thành công";
				try{
					
					//Cập nhật trạng thái đồng bộ dữ liệu
					//nicTransaciton.setSyncPassport(1);
					nicTransactionService.saveOrUpdate(nicTransaciton);
				}catch(Exception ex){
					
				}
			}
		} catch (Exception ex) {
			resultMess += ". [[Error]]: " + ex.toString();
		}
		return resultMess;
	}

	private void SyncNewDataA72(String transactionId) {

		try {
			do {

				if (tokenA98 == "") {
					Map<String, String> resultToken = GetTokenAPI("a98", "a98");
					if (resultToken != null) {
						tokenA98 = resultToken.get("access_token");
						expireTokenA98 = Integer.parseInt(resultToken
								.get("expires_in"));
						typeTokenA98 = resultToken.get("token_type");
					}
				}
				if (expireTokenA98 == 0 && tokenA98 != "") {
					ReAccessToken(tokenA98);
				}

			} while (tokenA98 == "" || expireTokenA98 == 0);

			do {
				if (tokenBNG == "") {
					Map<String, String> resultToken = GetTokenAPI("bng", "bng");
					if (resultToken != null) {
						tokenBNG = resultToken.get("access_token");
						expireTokenBNG = Integer.parseInt(resultToken
								.get("expires_in"));
						typeTokenBNG = resultToken.get("token_type");
					}
				}
				if (expireTokenBNG == 0 && tokenBNG != "") {
					ReAccessToken(tokenBNG);
				}

			} while (tokenBNG == "" || expireTokenBNG == 0);

			String urlCountry = "epp$Country";
			String urlPlace = "epp$Place";
			String urlArea = "epp$Area";
			String urlOffice = "epp$Office";

			// /Lấy dữ liệu danh mục tỉnh/thành
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
					.getNicTransactionById(transactionId).getModel();
			Collection<NicDocumentData> collectionNicDocumentData = documentDataService
					.findByTransactionId(transactionId).getModel();
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
					.getNicTransactionAttachments(transactionId, typeDocAttach,
							null);

			DocumentInfo modelDocument = new DocumentInfo();

			modelDocument.setCode(transactionId);
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
			String doe_ = nicDocumentData.getDateOfExpiry().toString()
					.split(" ")[0];
			//ppTemp.setDateOfExpiry(nicDocumentData.getDateOfExpiry());
			String doi_ = nicDocumentData.getDateOfIssue().toString()
					.split(" ")[0];
			//ppTemp.setDateOfIssue(nicDocumentData.getDateOfIssue());
			ppTemp.setSignerName(nicDocumentData.getCreateBy());
			ppTemp.setSignerPosition("Can bo xu ly");// Để tạm thời

			String placeOfIssue_ = "56003"; // Mặc định là từ Bộ ngoại giao do
											// csdl chưa đầy đủ
			if (listOffice != null) {
				for (OfficeData item : listOffice) {
					if (item.getName().contains(
							nicTransaciton.getIssuingAuthority())) {
						break;
					}
				}
			}
			OfficeData placeTmp = new OfficeData();
			ppTemp.setPlaceOfIssue(placeTmp);
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
			dob_ = dob_.split("-")[2] + "/" + dob_.split("-")[1] + "/"
					+ dob_.split("-")[0];
			personTmp.setDateOfBirth(dob_);
			String idPlaceOfBirth_ = "202";// /Mặc định là Hà Nội do chưa đủ dữ
											// liệu
			if (listPlace != null) {
				for (PlaceData item : listPlace) {
					if (item.getName().contains(
							nicRegistrationData.getPlaceOfBirth())) {
						break;
					}
				}
			}
			PlaceData placeTmp_ = new PlaceData();
			personTmp.setPlaceOfBirth(placeTmp_);
			personTmp.setIdNumber(nicTransaciton.getNin());
			;
			modelDocument.setPerson(personTmp);
			// //====================================

			// /Thong tin tai lieu di kem (anh mat va van tay)========
			List<AttachmentDoc> listAttachment = new LinkedList<AttachmentDoc>();
			if (nicTransacitonAttachment != null) {
				for (NicTransactionAttachment item : nicTransacitonAttachment) {
					AttachmentDoc attachmentDoc = new AttachmentDoc();
					String typeDoc_ = "OTHER"; // / Tạm thời các tài liệu gửi
												// kèm để chung là OTHER
					Integer serialNo = 0;
					if (item.getDocType().contains("FP")) {
						typeDoc_ = "FINGERPRINT";
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
						typeDoc_ = "PHOTO";
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

			// //===================================

			if (nicRegistrationData.getContactNo() != ""
					&& nicRegistrationData.getContactNo() != null) {
				modelDocument.setPhoneNo(nicRegistrationData.getContactNo());
			} else {
				modelDocument.setPhoneNo("0");
			}

			String idResidentPlace_ = "202";// /Mặc định là Hà Nội do chưa đủ dữ
											// liệu

			if (listPlace != null) {
				for (PlaceData item : listPlace) {
					if (item.getName().contains(
							nicRegistrationData.getAddressLine4())) {
						break;
					}
				}
			}
			String idResidentArea_ = "202";// /Mặc định là Hà Nội do chưa đủ dữ
											// liệu

			// if(listArea != null){
			// for(AreaData item : listArea)
			// {
			// if(item.getName().contains(nicRegistrationData.getAddressLine5())
			// ||
			// item.getCode().contains(nicRegistrationData.getAddressLine5())){
			// idResidentArea_ = item.getId();
			// break;
			// }
			// }
			// }
			PlaceData residentPlacetmp = new PlaceData();
			AreaData residentAreatmp = new AreaData();
			residentAreatmp.setId(idResidentArea_);
			modelDocument.setIsEpassport("Y");// Mặc định đồng bộ dữ liệu mới
												// từ NIC
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
			String office_ = "56003"; // Mặc định là từ Bộ ngoại giao do csdl
										// chưa đầy đủ
			if (listOffice != null) {
				for (OfficeData item : listOffice) {
					if (item.getName().contains(
							nicTransaciton.getIssuingAuthority())) {
						break;
					}
				}
			}
			OfficeData officeTmp_ = new OfficeData();
			modelDocument.setOffice(officeTmp_);
			
			// /TEST API ĐỒNG BỘ DỮ LIỆU HC
			RequestDocument requestDocument = new RequestDocument();
			requestDocument.setDocument(modelDocument);
			String jsonModel = ConvertDataJson(modelDocument);
			String requestUrl = "http://192.168.1.15:8044/app/rest/v2/services/epp_DocumentService/syncWithoutProcessing";
			Boolean resultSync = sendPostRequestSync(requestUrl, jsonModel,
					tokenBNG, typeTokenBNG);
			/*
			 * ///TEST API ĐĂNG KÝ HỦY HỘ CHIẾU RequestCancel rqCancel_ = new
			 * RequestCancel(); rqCancel_.setCode(jsonData.getTransactionId());
			 * rqCancel_.setDocType("PASSPORT");
			 * rqCancel_.setDocCode(passportNo_); rqCancel_.setReason("LOST");
			 * String oTemp_ = "56003"; // Mặc định là từ Bộ ngoại giao do csdl
			 * chưa đầy đủ if(listOffice != null){ for(OfficeData item :
			 * listOffice) {
			 * if(item.getName().contains(nicTransaciton.getIssuingAuthority
			 * ())){ oTemp_ = item.getId(); break; } } } OfficeData officeTemp_
			 * = new OfficeData(); officeTemp_.setId(oTemp_);
			 * rqCancel_.setOffice(officeTemp_);
			 * 
			 * DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd"); Date
			 * issueTmp = nicDocumentData.getDateOfIssue(); String split =
			 * nicDocumentData.getDateOfIssue().toString().split(" ")[0];
			 * rqCancel_.setDateOfRegister(split);
			 * 
			 * String jsonModelCancel = ConvertDataJsonCancel(rqCancel_); String
			 * requestUrlCancel =
			 * "http://27.72.57.171:8080/app/rest/v2/entities/epp$CancelDocument"
			 * ; String resultCancel = sendPostRequestCancel(requestUrlCancel,
			 * jsonModelCancel, tokenBNG, typeTokenBNG);
			 */
		} catch (Exception ex) {
			String text = ex.toString();
		}
		
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
		URL url = new URL("http://192.168.1.15:8044/app/rest/v2/oauth/token?"
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

	public Boolean ReAccessToken(String oldToken) throws JsonParseException,
			JsonMappingException, IOException {
		String urlParameters = "token=Bearer+" + oldToken
				+ "&token_type=access_token";
		String encoding = Base64.encodeBase64String(("epp:epp12#")
				.getBytes("UTF-8"));

		URL url = new URL("http://192.168.1.15:8044/app/rest/v2/oauth/revoke?"
				+ urlParameters);
		HttpURLConnection connection = (HttpURLConnection) url.openConnection();
		connection.setRequestMethod("POST");
		connection.setRequestProperty("Authorization",
				"Bearer " + encoding.trim());
		connection.setRequestProperty("Content-Type",
				"application/x-www-form-urlencoded");
		connection.connect();

		int statusCode = connection.getResponseCode();
		if (statusCode == 200) {
			return true;
		}
		return false;
	}

	public List<CountryData> GetDataUtilAPICountry(String urlParameters,
			String token, String typeToken) throws JsonParseException,
			JsonMappingException, IOException {
		List<CountryData> listResult = new LinkedList<CountryData>();

		URL url = new URL("http://192.168.1.15:8044/app/rest/v2/entities/"
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

		URL url = new URL("http://192.168.1.15:8044/app/rest/v2/entities/"
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

		URL url = new URL("http://192.168.1.15:8044/app/rest/v2/entities/"
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

		URL url = new URL("http://192.168.1.15:8044/app/rest/v2/entities/"
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

	public String sendPostAuthentication(String requestUrl, String data,
			String token, String typeToken) {
		String result = "";
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
				Charset charset = Charset.forName("UTF8");
				InputStreamReader isr = new InputStreamReader(content, charset);

				int numCharsRead;
				char[] charArray = new char[1024];
				StringBuffer sb = new StringBuffer();
				while ((numCharsRead = isr.read(charArray)) > 0) {
					sb.append(charArray, 0, numCharsRead);
				}
				result = sb.toString();
				/*
				 * accessToken.put("access_token",
				 * arrayResp.getString("token"));
				 */
				/*
				 * BufferedReader br = new BufferedReader(new InputStreamReader(
				 * connection.getInputStream())); String line; while ((line =
				 * br.readLine()) != null) { jsonString.append(line); }
				 * br.close(); connection.disconnect();
				 */
			}
		} catch (Exception e) {
			return "441";
		}
		return result;
	}

	// /Đăng ký giấy tờ hủy (Dữ liệu đơn)
	public static String sendPostRequestCancel(String requestUrl, String data,
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
				BufferedReader br = new BufferedReader(new InputStreamReader(
						connection.getInputStream()));
				String line;
				while ((line = br.readLine()) != null) {
					jsonString.append(line);
				}
				br.close();
				connection.disconnect();
				return "200";
			}
		} catch (Exception e) {
			// throw new RuntimeException(e.getMessage());
			return "404";
		}
		return jsonString.toString();
	}

	private String ConvertDataJson(DocumentInfo data)
			throws JsonGenerationException, JsonMappingException, IOException {
		ObjectMapper mapper = new ObjectMapper();
		String json = mapper.writeValueAsString(data);
		return json;
	}

	private String ConvertDataJsonCancel(RequestCancel data)
			throws JsonGenerationException, JsonMappingException, IOException {
		ObjectMapper mapper = new ObjectMapper();
		String json = mapper.writeValueAsString(data);
		return json;
	}

	// /TRUNG THÊM DUYỆT IN PERSO

	// TODO: TRUNG THÊM SEARCH CHO PHẦN DUYỆT IN PERSO
	@RequestMapping(value = "/searchPersoTransactionId", method = RequestMethod.POST)
	public ModelAndView searchJobPersoList(WebRequest request,
			HttpServletRequest httpRequest,
			@RequestParam("search_data") String txnId, Model model)
			throws Throwable {
		logger.info("Retrieve Transaction Id =========================>> "
				+ txnId);
		httpRequest.setAttribute("pendingCount",
				uploadJobService.getPendingInvestigationsCount());
		PaginatedResult<NicUploadJobDto> pr = null;
		try {
			// NicUploadJob nicUploadJob = null;
			int pageNo = 1;
			// int pageSize = 10;
			// [22/08/2013]: Swapna Modified
			int pageSize = 10;
			int startIndex = 0;

			// Sailaja Modified for pagination
			ParametersId id = new ParametersId();
			id.setParaName(Parameters.MAX_RECORDS_PER_PAGE);
			id.setParaScope(Parameters.SCOPE_SYSTEM);
			String tableId = "row";
			String sortedElement = "codeId";
			String order = InvestigationController.DECENDING_ORDER;

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
			if (order.equalsIgnoreCase(InvestigationController.DECENDING_ORDER)) {
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
			String pageNumber = request.getParameter((new ParamEncoder(tableId)
					.encodeParameterName(TableTagParameters.PARAMETER_PAGE)));

			if (StringUtils.isNotBlank(pageNumber)
					&& StringUtils.isNumeric(pageNumber)) {
				pageNo = Integer.parseInt(pageNumber);
			}
			if (StringUtils.isNotBlank(pageNumber)) {
				startIndex = (Integer.parseInt(pageNumber) - 1) * pageSize;
			} else {
				startIndex = 10;
			}
			HttpSession session = httpRequest.getSession();
			UserSession userSession = (UserSession) session
					.getAttribute("userSession");
			pr = uploadJobService.findByTransactionIdPerso(txnId,
					userSession.getUserName());
			List<NicUploadJobDto> list = new ArrayList<NicUploadJobDto>();
			if (pr != null) {
				list = pr.getRows();
				if (list != null) {
					for (NicUploadJobDto record : list) {
						String transactionId = record.getTransactionId();
						NicTransaction nicTransaction = nicTransactionService
								.findById(transactionId);
						record.setDateOfApplication(nicTransaction
								.getDateOfApplication());
						record.setEstDateOfCollection(nicTransaction
								.getEstDateOfCollection());
						// record.setPriority(nicTransaction.getPriority());
						{
							try {
								CodeValues codeValue = codesService
										.getCodeValueByIdName(Codes.PRIORITY,
												nicTransaction.getPriority()
														.toString());
								if (codeValue != null
										&& codeValue.getCodeValueDesc() != null) {
									record.setPriorityString(codeValue
											.getCodeValueDesc());
								} else {
									record.setPriorityString(nicTransaction
											.getPriority() == null ? null
											: nicTransaction.getPriority()
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
				} else {
					request.setAttribute("jobDetailsErrorMsg", "Không tìm thấy dữ liệu",
							0);
				}
				model.addAttribute("nicUploadJobDto", new NicUploadJobDto());
				Map searchResultMap = new HashMap();
				searchResultMap.put("jobList", list);
				searchResultMap.put("totalRecords", pr.getTotal());
				searchResultMap.put("pageSize", pageSize);
				searchResultMap.put("txnId", txnId);
				model.addAttribute("jobList", list);
				prepareModelStuff(model);
				return new ModelAndView("persoManager.persomain",
						searchResultMap);
			} else {
				return new ModelAndView("persoManager.persomain", null);
			}
		} catch (Exception e) {

			e.printStackTrace();
			return null;
		}

	}

	@RequestMapping(value = "/getPersoJob")
	public ModelAndView getPersoJob(
			@ModelAttribute(value = "formData") InvestigationAssignmentData investigationAssignmentData,
			WebRequest request, HttpServletRequest httpRequest, ModelMap model) {
		logger.info("NIC Perso Manager approve ==== ");
		PaginatedResult<NicUploadJobDto> pr = null;
		try {
			int pageNo = 1;
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
			String order = InvestigationController.DECENDING_ORDER;

			try {
				String sort = request
						.getParameter(new ParamEncoder(tableId)
								.encodeParameterName(TableTagParameters.PARAMETER_SORT));
				if (StringUtils.isNotBlank(sort))
					sortedElement = sort;
			} catch (Exception ex) {
				logMessage(ex);
			}

			if (investigationAssignmentData == null) {
				investigationAssignmentData = new InvestigationAssignmentData();
			}

			investigationAssignmentData.cleanUpStrings();

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
			if (order.equalsIgnoreCase(InvestigationController.DECENDING_ORDER)) {
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
			pr = uploadJobService.findPersoList(userSession.getUserName(),
					pageNo, pageSize, null, null);
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
				searchResultMap.put("pageSize", pageSize);

				model.addAttribute("jobList", list);

				model.addAttribute("fnSelected",
						Constants.HEADING_NIC_APPROVE_PERSO);
				return new ModelAndView("persoManager.persolist",
						searchResultMap);
			} else {
				model.addAttribute("fnSelected",
						Constants.HEADING_NIC_APPROVE_PERSO);
				return new ModelAndView("persoManager.persolist", null);
			}
			// }
		} catch (Exception e) {
			logger.info(e.getMessage());
			e.printStackTrace();
			return null;
			// return new ModelAndView("investigation.investigation", null);
		}
	}

	@RequestMapping(value = { "/persoDetailId/{jobId}" })
	public ModelAndView persoDetailId(@PathVariable long jobId,
			HttpServletRequest httpRequest, Model model) throws Throwable {
		logger.info("NIC Start startApproveStatus compare");

		return this.persoDetailId(jobId, httpRequest, model, null);
	}

	public ModelAndView persoDetailId(long jobId,
			HttpServletRequest httpRequest, Model model,
			List<String> listOfMessages) throws Throwable {
		logger.info("NIC Start PERSO compare, listOfMessages");
		String periodValid = "";
		this.prepareModelStuff(model);

		ModelAndView modelAndView = new ModelAndView(
				"persoManager.perso.compare");

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

		modelAndView.addObject("transID", mainTransactionId);
		modelAndView.addObject("jobsID", jobId);

		NicTransaction nic = nicTransactionService
				.getNicTransactionById(mainTransactionId).getModel();
		if (nic != null) {
			periodValid = nic.getValidityPeriod().toString();
		}
		modelAndView.addObject("periodValid", periodValid);
		return modelAndView;
	}

	// /Phê duyệt bản ghi trong danh sách chờ duyệt in
	@RequestMapping(value = { "/persoSubmit" })
	public ModelAndView persoSubmit(
			@ModelAttribute(value = "investigationHitData") InvestigationHitData investigationHitData,
			BindingResult result, WebRequest request,
			HttpServletRequest httpRequest, Model model) throws Throwable {
		logger.info("Perso Submit Process ====");

		List<String> messages = new LinkedList<String>();
		try {
			HttpSession session = httpRequest.getSession();
			UserSession userSession = (UserSession) session
					.getAttribute("userSession");

			/*
			 * NicUploadJob obj = uploadJobSerivce
			 * .getUploadJobByTransactionIdJob(investigationHitData
			 * .getUploadJobId()); if (obj != null) {
			 */
			uploadJobService.approveJobStatus_Confirmed(
					Long.valueOf(investigationHitData.getUploadJobId()),
					userSession.getUserName(), userSession.getWorkstationId(),
					NicTransactionLog.TRANSACTION_STAGE_PERSO_REGISTER,
					NicTransactionLog.TRANSACTION_STATUS_NIC_PENDING_PERSO,
					NicUploadJob.RECORD_STATUS_APPROVE_PERSO);
			// Xu ly perso /// TẠM ĐÓNG KHI GỌI SERVICE TỪ HỆ THỐNG PERSO THỰC
			// HIỆN HÀNH ĐỘNG NÀY
			/*
			 * logger.info("-------------- PersoThread_TRUNG: " +
			 * Thread.currentThread().getName()); appContext =
			 * ApplicationContextProvider.getApplicationContext(); baseExecutor
			 * = (NicCommandExecutor) appContext .getBean("baseExecutorPerso");
			 * baseExecutor.doCommand(obj);
			 * logger.info("-------------- PersoThread_TRUNG: " +
			 * Thread.currentThread().getName() + " End.");
			 */
			// Dong bo du lieu sau khi perso
			/*
			 * NicUploadJob objEnd = uploadJobSerivce
			 * .getUploadJobByTransactionIdJob(investigationHitData
			 * .getUploadJobId()); if (objEnd != null) { if
			 * (!objEnd.getPersoRegisterStatus().equals(
			 * NicUploadJob.RECORD_STATUS_COMPLETED)) {
			 * SyncNewDataA72(obj.getTransactionId()); } } else {
			 * logger.info("Loi khi Perso -- [[" + obj.getTransactionId() +
			 * "]]"); }
			 */
			/* } */
			messages.add("Đã duyệt bản ghi đợi chuẩn bị in.");
		} catch (JobNoLongerAssignedToOfficerException e) {
			messages.add("Duyệt thất bại. Công việc hiện tại không phần quyền xử lý cho User này.");
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

		httpRequest.setAttribute("messages", messages);
		return new ModelAndView("forward:/servlet/investigation/getPersoJob");
	}

	@RequestMapping(value = { "/reject_noHit_Perso" })
	public ModelAndView reject_noHit_Perso(
			@ModelAttribute(value = "investigationHitData") InvestigationHitData investigationHitData,
			BindingResult result, WebRequest request,
			HttpServletRequest httpRequest, Model model) throws Throwable {
		logger.info("reject_noHit_Perso");

		List<String> messages = new LinkedList<String>();
		try {
			HttpSession session = httpRequest.getSession();
			UserSession userSession = (UserSession) session
					.getAttribute("userSession");

			uploadJobService.rejectJob(
					investigationHitData.getJobRejectRemarks(),
					Long.valueOf(investigationHitData.getUploadJobId()),
					userSession.getUserName(), userSession.getWorkstationId(),
					NicRejectionData.rejectReason_investigation,
					NicTransactionLog.TRANSACTION_STAGE_INVESTIGATION,
					NicTransactionLog.TRANSACTION_STATUS_NIC_REJECTED);

			messages.add("Từ chối bản ghi thành công.");
		} catch (JobNoLongerAssignedToOfficerException e) {
			logger.info("rejection failed");
			messages.add("Từ chối bản ghi thất bại. Bản ghi không được phân công cho User này.");
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

		httpRequest.setAttribute("messages", messages);
		return new ModelAndView("forward:/servlet/investigation/getPersoJob");
	}

	// / END

	// /TRUNG THÊM ĐỒNG BỘ DỮ LIỆU SANG A72
	// TODO: TRUNG THÊM SEARCH CHO PHẦN ĐỒNG BỘ DỮ LIỆU SANG A72
	@RequestMapping(value = "/searchSyncTransactionId", method = RequestMethod.POST)
	public ModelAndView searchJobSyncSingerList(WebRequest request,
			HttpServletRequest httpRequest,
			@RequestParam("search_data") String txnId, Model model)
			throws Throwable {
		logger.info("Retrieve Transaction Id =========================>> "
				+ txnId);
		httpRequest.setAttribute("pendingCount",
				uploadJobService.getPendingInvestigationsCount());
		PaginatedResult<NicUploadJobDto> pr = null;
		try {
			// NicUploadJob nicUploadJob = null;
			int pageNo = 1;
			// int pageSize = 10;
			// [22/08/2013]: Swapna Modified
			int pageSize = 10;
			int startIndex = 0;

			// Sailaja Modified for pagination
			ParametersId id = new ParametersId();
			id.setParaName(Parameters.MAX_RECORDS_PER_PAGE);
			id.setParaScope(Parameters.SCOPE_SYSTEM);
			String tableId = "row";
			String sortedElement = "codeId";
			String order = InvestigationController.DECENDING_ORDER;

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
			if (order.equalsIgnoreCase(InvestigationController.DECENDING_ORDER)) {
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
			String pageNumber = request.getParameter((new ParamEncoder(tableId)
					.encodeParameterName(TableTagParameters.PARAMETER_PAGE)));

			if (StringUtils.isNotBlank(pageNumber)
					&& StringUtils.isNumeric(pageNumber)) {
				pageNo = Integer.parseInt(pageNumber);
			}
			if (StringUtils.isNotBlank(pageNumber)) {
				startIndex = (Integer.parseInt(pageNumber) - 1) * pageSize;
			} else {
				startIndex = 10;
			}
			HttpSession session = httpRequest.getSession();
			UserSession userSession = (UserSession) session
					.getAttribute("userSession");
			pr = uploadJobService.findByTransactionIdSync(txnId,
					userSession.getUserName());
			List<NicUploadJobDto> list = new ArrayList<NicUploadJobDto>();
			if (pr != null) {
				list = pr.getRows();
				if (list != null) {
					for (NicUploadJobDto record : list) {
						String transactionId = record.getTransactionId();
						NicTransaction nicTransaction = nicTransactionService
								.findById(transactionId);
						record.setDateOfApplication(nicTransaction
								.getDateOfApplication());
						record.setEstDateOfCollection(nicTransaction
								.getEstDateOfCollection());
						// record.setPriority(nicTransaction.getPriority());
						{
							try {
								CodeValues codeValue = codesService
										.getCodeValueByIdName(Codes.PRIORITY,
												nicTransaction.getPriority()
														.toString());
								if (codeValue != null
										&& codeValue.getCodeValueDesc() != null) {
									record.setPriorityString(codeValue
											.getCodeValueDesc());
								} else {
									record.setPriorityString(nicTransaction
											.getPriority() == null ? null
											: nicTransaction.getPriority()
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
				} else {
					request.setAttribute("jobDetailsErrorMsg", "No Data found",
							0);
				}
				model.addAttribute("nicUploadJobDto", new NicUploadJobDto());
				Map searchResultMap = new HashMap();
				searchResultMap.put("jobList", list);
				searchResultMap.put("totalRecords", pr.getTotal());
				searchResultMap.put("pageSize", pageSize);
				searchResultMap.put("txnId", txnId);
				model.addAttribute("jobList", list);
				prepareModelStuff(model);
				return new ModelAndView("syncSinger.syncSingermain",
						searchResultMap);
			} else {
				return new ModelAndView("syncSinger.syncSingermain", null);
			}
		} catch (Exception e) {

			e.printStackTrace();
			return null;
		}

	}

	@RequestMapping(value = "/getSyncSingerJob")
	public ModelAndView getSyncSingerJob(WebRequest request,
			HttpServletRequest httpRequest, ModelMap model) {
		logger.info("NIC Perso Manager approve ==== ");
		PaginatedResult<NicUploadJobDto> pr = null;
		try {
			int pageNo = 1;
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
			String order = InvestigationController.DECENDING_ORDER;

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
			if (order.equalsIgnoreCase(InvestigationController.DECENDING_ORDER)) {
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
					pageNo, pageSize, null, null);
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
				searchResultMap.put("pageSize", pageSize);

				model.addAttribute("jobList", list);

				model.addAttribute("fnSelected",
						Constants.HEADING_NIC_INVESTIGATION);
				return new ModelAndView("syncSinger.syncSingermain",
						searchResultMap);
			} else {
				model.addAttribute("fnSelected",
						Constants.HEADING_NIC_INVESTIGATION);
				return new ModelAndView("syncSinger.syncSingermain", null);
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
		NicTransaction nicTransaction = nicTransactionService.findById(jobDetails.getTransactionId());
		if(nicTransaction != null){
			//Phúc chỉnh format ngày, dân tộc...
			if (nicTransaction.getNicRegistrationData().getDateOfBirth() != null) {
				String dob = HelperClass.convertDateToString1(nicTransaction
						.getNicRegistrationData().getDateOfBirth());
				if(dob.contains(" ")){
					nicTransaction.setDateOfBirthDesc(dob.split(" ")[0]);			
				}else{
					nicTransaction.setDateOfBirthDesc(dob);				
				}
				// nicTransaction.getNicRegistrationData().setDateOfBirth(HelperClass.convertStringToDate1(dob));
			}
			if (nicTransaction.getNicRegistrationData().getCreateDatetime() != null) {
				String crd = HelperClass.convertDateToString1(nicTransaction
						.getNicRegistrationData().getCreateDatetime());
				nicTransaction.setCreateDesc(crd);
				// nicTransaction.getNicRegistrationData().setCreateDatetime(HelperClass.convertStringToDate1(crd));
			}
			if (nicTransaction.getNicRegistrationData().getReligion() != null) {
				String religion = codesService.getCodeValueDescByIdName(
						RegistrationConstants.CODE_RELIGION_CODE_ID, nicTransaction
								.getNicRegistrationData().getReligion(), "");
				nicTransaction.getNicRegistrationData().setReligion(religion);
			}
			if (nicTransaction.getNicRegistrationData().getNation() != null) {
				String nation = codesService.getCodeValueDescByIdName(
						RegistrationConstants.CODE_NATION_CODE_ID, nicTransaction
								.getNicRegistrationData().getNation(), "");
				nicTransaction.getNicRegistrationData().setNation(nation);
			}
		}
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

	// / END

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

	// /METHOD DANH CHO CAC FORM DUNG CHUNG
	public void InitMainInfo(WebRequest request, String txnId, long jobId,
			ModelMap model, HttpServletRequest httpRequest, ModelAndView mav) {
		NicTransaction nicTransaction = new NicTransaction();
		String photoStr = null;
		try {
			if (StringUtils.isNotEmpty(txnId)) {
				nicTransaction = nicTransactionService.findById(txnId);

				//CodeValues codeValuePOB = codesService.getCodeValueByIdName(nicTransaction.getNicRegistrationData().getPlaceOfBirth(), "CODE_BirthPlace"); // / Lấy dữ liệu CV nơi sinh
				//CodeValues codeValuePOI = codesService.getCodeValueByIdName(nicTransaction.getNicRegistrationData().getAddressNin(),"CODE_IDPlace"); // / Lấy dữ liệu CV nơi cấp
				if(nicTransaction.getNicRegistrationData().getAddressNin() != null){
					String addNin = codesService.getCodeValueDescByIdName(RegistrationConstants.CODE_IDPlace_CODE_ID, nicTransaction.getNicRegistrationData().getAddressNin(), "");													
					nicTransaction.getNicRegistrationData().setAddressNin(addNin);
				}
				//CodeValues codeValueMS = codesService.getCodeValueByIdName(nicTransaction.getNicRegistrationData().getMaritalStatus(), "MARITAL_STATUS"); // / Lấy dữ liệu CV tình trạng hôn nhân
				if(nicTransaction.getNicRegistrationData().getMaritalStatus() != null){
					String marital = codesService.getCodeValueDescByIdName(RegistrationConstants.MARITAL_STATUS_CODE_ID, nicTransaction.getNicRegistrationData().getMaritalStatus(), "");													
					nicTransaction.getNicRegistrationData().setMaritalStatus(marital);
				}
				//CodeValues codeValueN = codesService.getCodeValueByIdName(nicTransaction.getNicRegistrationData().getNation(),"CODE_NATION"); // / Lấy dữ liệu CV dân tộc
				if(nicTransaction.getNicRegistrationData().getNation() != null){
					String nation = codesService.getCodeValueDescByIdName(RegistrationConstants.CODE_NATION_CODE_ID, nicTransaction.getNicRegistrationData().getNation(), "");													
					nicTransaction.getNicRegistrationData().setNation(nation);
				}
				//CodeValues codeValueR = codesService.getCodeValueByIdName(nicTransaction.getNicRegistrationData().getReligion(),"CODE_RELIGION"); // / Lấy dữ liệu CV tôn giáo
				if(nicTransaction.getNicRegistrationData().getReligion() != null){
					String religion = codesService.getCodeValueDescByIdName(RegistrationConstants.CODE_RELIGION_CODE_ID, nicTransaction.getNicRegistrationData().getReligion(), "");													
					nicTransaction.getNicRegistrationData().setReligion(religion);
				}
//				CodeValues codeValueJ = codesService.getCodeValueByIdName(nicTransaction.getNicRegistrationData().getJob(),"CODE_JOB"); // / Lấy dữ liệu CV công việc
				if(nicTransaction.getNicRegistrationData().getJob() != null){
					String job = codesService.getCodeValueDescByIdName(RegistrationConstants.CODE_JOB_CODE_ID, nicTransaction.getNicRegistrationData().getJob(), "");													
					nicTransaction.getNicRegistrationData().setJob(job);
				}
				if(nicTransaction.getNicRegistrationData().getNationality() != null){
					String country = codesService.getCodeValueDescByIdName(RegistrationConstants.NATIONALITY_CODE_ID, nicTransaction.getNicRegistrationData().getNationality(), "");													
					nicTransaction.getNicRegistrationData().setNationality(country);
				}
				if(nicTransaction.getNicRegistrationData().getCreateDatetime() != null){
					String crd = HelperClass.convertDateToString1(nicTransaction.getNicRegistrationData().getCreateDatetime());
					nicTransaction.setCreateDesc(crd);		
				}
				if(nicTransaction.getNicRegistrationData().getFatherDateOfBirth() != null){
					String dobF = HelperClass.convertDateToString1(nicTransaction.getNicRegistrationData().getFatherDateOfBirth());
					nicTransaction.setFatherDobDesc(dobF);		
				}
				if(nicTransaction.getNicRegistrationData().getMotherDateOfBirth()!= null){
					String dobM = HelperClass.convertDateToString1(nicTransaction.getNicRegistrationData().getMotherDateOfBirth());
					nicTransaction.setMotherDobDesc(dobM);		
				}
				if(nicTransaction.getNicRegistrationData().getSpouseDateOfBirth() != null){
					String dobS = HelperClass.convertDateToString1(nicTransaction.getNicRegistrationData().getSpouseDateOfBirth());
					nicTransaction.setSpouseDobDesc(dobS);		
				}
				if(nicTransaction != null && nicTransaction.getNicRegistrationData() != null){
					String address5 = codesService.getCodeValueDescByIdName(RegistrationConstants.CODE_ID_DISTRICT, nicTransaction.getNicRegistrationData().getAddressLine5() != null ? nicTransaction.getNicRegistrationData().getAddressLine5() : "", "");
					if(!StringUtils.isEmpty(address5)){
						nicTransaction.getNicRegistrationData().setAddressLine5(address5);
					}else{
						nicTransaction.getNicRegistrationData().setAddressLine5(codesService.getCodeValueDescByIdName(RegistrationConstants.NATIONALITY_CODE_ID, nicTransaction.getNicRegistrationData().getOverseasCountry() != null ? nicTransaction.getNicRegistrationData().getOverseasCountry() : "", ""));
					}
					String dddress4 = codesService.getCodeValueDescByIdName(RegistrationConstants.CODE_ID_TOWN, nicTransaction.getNicRegistrationData().getAddressLine4() != null ? nicTransaction.getNicRegistrationData().getAddressLine4() : "", "");	
					if(!StringUtils.isEmpty(dddress4)){
						nicTransaction.getNicRegistrationData().setAddressLine4(dddress4);
					}else{
						nicTransaction.getNicRegistrationData().setAddressLine4(codesService.getCodeValueDescByIdName(RegistrationConstants.NATIONALITY_CODE_ID, nicTransaction.getNicRegistrationData().getOverseasCountry() != null ? nicTransaction.getNicRegistrationData().getOverseasCountry() : "", ""));
					}
				}
				//				if (codeValuePOB != null) {
//					nicTransaction.getNicRegistrationData().setPlaceOfBirth(
//							codeValuePOB.getCodeValueDesc());
//				}
//				if (codeValuePOI != null) {
//					nicTransaction.getNicRegistrationData().setAddressNin(
//							codeValuePOI.getCodeValueDesc());
//				}
//				if (codeValueMS != null) {
//					nicTransaction.getNicRegistrationData().setMaritalStatus(
//							codeValueMS.getCodeValueDesc());
//				}
//				if (codeValueN != null) {
//					nicTransaction.getNicRegistrationData().setNation(
//							codeValueN.getCodeValueDesc());
//				}
//				if (codeValueR != null) {
//					nicTransaction.getNicRegistrationData().setReligion(
//							codeValueR.getCodeValueDesc());
//				}
//				if (codeValueJ != null) {
//					nicTransaction.getNicRegistrationData().setJob(
//							codeValueJ.getCodeValueDesc());
//				}
				//format lại dữ liệu...
				if(nicTransaction.getNicRegistrationData().getDateNin() != null){
					nicTransaction.setDateNinDesc(HelperClass.convertDateToString1(nicTransaction.getNicRegistrationData().getDateNin()));
				}

				// /Lấy dữ liệu CV cho bộ ngoại giao - công vụ
				if (nicTransaction.getPassportType() != "P") {
//					CodeValues codeValueSD = codesService.getCodeValueByIdName(nicTransaction.getNicRegistrationData().getSignerDecision(), "CODE_IDGovernment"); // / Lấy dữ liệu CV cơ quan chủ quản
//					CodeValues codeValueTP = codesService.getCodeValueByIdName(nicTransaction.getNicRegistrationData().getPurpose(), "CODE_TripPurpose"); // / Lấy dữ liệu CV mục đích chuyến đi
//					CodeValues codeValueTC = codesService.getCodeValueByIdName(nicTransaction.getNicRegistrationData().getTypeExpense(), "CODE_TripCost"); // / Lấy dữ liệu CV chi phí chuyến đi
					/*
					if(nicTransaction.getNicRegistrationData().getDateDecision() != null){
						String dateDecision = HelperClass.convertDateToString1(nicTransaction.getNicRegistrationData().getDateDecision());
						nicTransaction.setDateDecisionDesc(dateDecision);
					}
					if(nicTransaction.getNicRegistrationData().getGovDecision() != null){
						String decisionGov = codesService.getCodeValueDescByIdName(RegistrationConstants.CODE_IDGovernment_CODE_ID, nicTransaction.getNicRegistrationData().getGovDecision(), "");													
						nicTransaction.getNicRegistrationData().setGovDecision(decisionGov);		
					}
					if(nicTransaction.getNicRegistrationData().getSignerDecision() != null){
						String decisionSigner = codesService.getCodeValueDescByIdName(RegistrationConstants.CODE_SIGNER_GOV_CODE_ID, nicTransaction.getNicRegistrationData().getSignerDecision(), "");													
						nicTransaction.getNicRegistrationData().setSignerDecision(decisionSigner);		
					}
					if(nicTransaction.getNicRegistrationData().getPurpose() != null){
						String purpose = codesService.getCodeValueDescByIdName(RegistrationConstants.CODE_TripPurpose_CODE_ID, nicTransaction.getNicRegistrationData().getPurpose(), "");													
						nicTransaction.getNicRegistrationData().setPurpose(purpose);		
					}
					if(nicTransaction.getNicRegistrationData().getTypeExpense() != null){
						String typeEx = codesService.getCodeValueDescByIdName(RegistrationConstants.CODE_TripCost_CODE_ID, nicTransaction.getNicRegistrationData().getTypeExpense(), "");													
						nicTransaction.getNicRegistrationData().setTypeExpense(typeEx);		
					}
					if(nicTransaction.getNicRegistrationData().getEstimateFrom() != null){
						nicTransaction.setEstimateFromDesc(HelperClass.convertDateToString1(nicTransaction.getNicRegistrationData().getEstimateFrom()));
					}*/
//					if (codeValueSD != null) {
//						nicTransaction.getNicRegistrationData()
//								.setSignerDecision(
//										codeValueSD.getCodeValueDesc());
//					}
//					if (codeValueTP != null) {
//						nicTransaction.getNicRegistrationData().setPurpose(
//								codeValueTP.getCodeValueDesc());
//					}
//					if (codeValueTC != null) {
//						nicTransaction.getNicRegistrationData().setTypeExpense(
//								codeValueTC.getCodeValueDesc());
//					}
				}

				// Hiển thị ảnh cá nhân
				List<NicTransactionAttachment> photoList = nicTransactionAttachmentService
						.findNicTransactionAttachments(
								txnId,
								NicTransactionAttachment.DOC_TYPE_PHOTO_CAPTURE,
								NicTransactionAttachment.DEFAULT_SERIAL_NO).getListModel();
				if (CollectionUtils.isNotEmpty(photoList)
						&& photoList.size() > 0) {
					photoStr = Base64.encodeBase64String(photoList.get(0)
							.getDocData());
				}

				NicUploadJob jobDetails = uploadJobService.findById(jobId);
				// Xử lý điều tra nghi vấn

				String mainTransactionId = jobDetails.getTransactionId();

				this.initializeModelAndViewForms(mav);

				this.initializeModelAndViewCommonPageDetails(mav, jobDetails,
						mainTransactionId);

				List<SearchHitDto> hits = this.getAllHits(jobId,
						mainTransactionId);

				List<InvestigationHit> invHits_all = this
						.getAllHitsForModelAndView(httpRequest, mav,
								jobDetails, mainTransactionId, hits);
				logger.info("invHits_all.size():" + invHits_all.size());

				List<InvestigationHit> invHits_error = this
						.getAllHits_error(invHits_all);
				this.processMainCandidateInvestigationInformation(invHits_all,
						invHits_error);

				List<InvestigationHit> invHits = this
						.getAllHits_nonError(invHits_all);

				logger.info("invHits.size():" + invHits.size());
				logger.info("invHits_error.size():" + invHits_error.size());

				List<InvestigationHit> invHits_displayThis = null;
				if (invHits.size() > 0) {
					//Phúc edit
					for(InvestigationHit hit : invHits){
						funtionFixDisplayAFIS(hit, mainTransactionId);						
					}
					invHits_displayThis = invHits;
					mav.addObject("inv_noHit", new Boolean(false));
				} else {
					for(InvestigationHit hit : invHits_error){
						funtionFixDisplayAFIS(hit, mainTransactionId);						
					}
					invHits_displayThis = invHits_error;
					mav.addObject("inv_noHit", new Boolean(true));
				}
				if (invHits.size() <= 0 && invHits_error.size() <= 0
						&& invHits_all.size() <= 0) {
				} else {
					mav.addObject("inv_none", new Boolean(false));
				}
				/* mav.addObject("jobDetails", jobDetails); */
				mav.addObject("invHits", invHits_displayThis);
				mav.addObject("invHitsSize", invHits_displayThis.size());
				logger.info("computed:  invHits.size():"
						+ invHits_displayThis.size());
			}
		} catch (Exception ex) {
			logger.error(
					"Error occurred while getting the status for Transaction:"
							+ txnId + ", Reason: " + ex.getMessage(), ex);
		}

		// /Xử lý tài liệu
		List<RicRegistrationDocumentDTO> proofDocList = new ArrayList<RicRegistrationDocumentDTO>();
		List<NicTransactionAttachment> nicTransactionDocuments = null;
		try {
			httpRequest.setAttribute("txnId", txnId);
			ImageUtil imageUtil = new ImageUtil();

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

			nicTransactionDocuments = nicTransactionAttachmentService
					.getTransactionProofDocuments(txnId, excludedDocTypes);
			if (CollectionUtils.isNotEmpty(nicTransactionDocuments)) {
				for (NicTransactionAttachment nicTransProofDocument : nicTransactionDocuments) {
					RicRegistrationDocumentDTO proofDoc = new RicRegistrationDocumentDTO();
					String decodedDocString = Base64
							.encodeBase64String(nicTransProofDocument
									.getDocData());
					String imageType = imageUtil
							.checkImageType(nicTransProofDocument.getDocData());
					String docType = nicTransProofDocument.getDocType();

					if (StringUtils.isNotBlank(imageType)
							&& imageType.equalsIgnoreCase(ImageUtil.IMAGE_JPG)) {
						proofDoc.setDocumentName("D"
								+ nicTransProofDocument.getTransactionDocId());
						proofDoc.setType("JPEG");
					} else {
						proofDoc.setDocumentName(docType);
						proofDoc.setType("PDF");
					}

					proofDoc.setDocumentId(String.valueOf(nicTransProofDocument
							.getTransactionDocId()));
					proofDoc.setTransactionNo(nicTransProofDocument
							.getTransactionId());
					proofDoc.setSerialNumber(nicTransProofDocument
							.getSerialNo());

					String docTypeDesc = null;
					// Document Description from Code Table
					if (docType != null && StringUtils.isNotBlank(docType)) {
						CodeValues codeValue = codesService
								.getCodeValueByIdName("DOC_TYPE", docType);
						if (codeValue != null) {
							docTypeDesc = codeValue.getCodeValueDesc();
						} else {
							docTypeDesc = docType;
						}
					}
					// proofDoc.setDocumentName(docType);

					proofDoc.setPurpose(docTypeDesc);

					proofDoc.setDmsLink(this.getDmsLink(
							nicTransProofDocument.getTransactionDocId(),
							nicTransProofDocument.getTransactionId(),
							nicTransProofDocument.getDocType()));

					List<String> documentList = new ArrayList<String>();
					documentList.add(decodedDocString);
					proofDoc.setDocument(documentList);
					proofDocList.add(proofDoc);
				}
			}
		} catch (Exception ex) {
			logger.info("Error occurred while getting the transaction documents for Transaction:"
					+ txnId + ", Reason: " + ex.getMessage());
		}

		httpRequest.setAttribute("proofDocList", proofDocList);

		mav.addObject("nicData", nicTransaction);
		mav.addObject("photoStr", photoStr);

		// trung show img CHU KY
		String photoStrSigner = null;
		List<NicTransactionAttachment> photoList = nicTransactionAttachmentService
				.findNicTransactionAttachments(txnId, "SC_DECSION",
						NicTransactionAttachment.DEFAULT_SERIAL_NO).getListModel();
		if (CollectionUtils.isNotEmpty(photoList) && photoList.size() > 0) {
			photoStrSigner = Base64.encodeBase64String(photoList.get(0)
					.getDocData());
		}
		httpRequest.setAttribute("photoStrSigner", photoStrSigner);
		mav.addObject("numCompare", nicTransaction.getNicRegistrationData()
				.getNumDecision());

		/*SignerGovs signerAp = new SignerGovs();
		signerAp = signerGovsService.findSignerByCode(nicTransaction
				.getNicRegistrationData().getSignerDecision());

		if (signerAp != null) {
			mav.addObject("signerCompare", signerAp.getNameSigner());
		} else {
			mav.addObject("signerCompare", nicTransaction
					.getNicRegistrationData().getSignerDecision());
		}
		CodeValues codeGOv = codesService.getCodeValueByIdName(
				"CODE_IDGovernment", nicTransaction.getNicRegistrationData().getGovDecision());
		if (codeGOv != null)
			mav.addObject("govCompareDb", codeGOv.getCodeValueDesc());
		else
			mav.addObject("govCompare", nicTransaction.getNicRegistrationData()
					.getGovDecision());*/

		// end show img CHU KY

		// Show img Tai lieu so sanh
		String photoCompare = null;
		/*SignerGovs signer = new SignerGovs();
		signer = signerGovsService.findSignerByCode(nicTransaction.getNicRegistrationData().getSignerDecision());
		if (signer != null) {
			if (signer.getDocData() != null) {
				photoCompare = new String(signer.getDocData());
			}
			
			mav.addObject("signerCompareDb", signer.getNameSigner());
//			CodeValues codeGOv_pare = codesService.getCodeValueByIdName("CODE_IDGovernment", signer.getCodeGovernment());
			String codeGOv_pare = codesService.getCodeValueDescByIdName(RegistrationConstants.CODE_IDGovernment_CODE_ID, signer.getCodeGovernment(), "");
			if (codeGOv_pare != null)
				mav.addObject("govCompareDb", codeGOv_pare);
			mav.addObject(
					"statusSign",
					(signer.getActive().equals("Y") ? "Hoạt động" : "Tạm ngừng"));
		}
		else
		{
			mav.addObject("signerCompareDb", "");
			mav.addObject("govCompareDb", "");
		}*/
		httpRequest.setAttribute("photoCompare", photoCompare);

		// end Show img Tai lieu so sanh

		// /Ảnh vân tay
		Map<Integer, FingerprintInfo> fpMap = new HashMap<Integer, FingerprintInfo>();
		String viewFPFalg = "Y";

		try {

			UserSession userSession = (UserSession) httpRequest.getSession()
					.getAttribute("userSession");
			String transactionId = txnId;
			// [19Feb2016][Tue] - Add
			// String nin = request.getParameter("nin");
			String passportNo = "";

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
						// [19Feb2016][Tue] - Add
						// info.setFpVerifyScr(Integer.parseInt(fpVerifyScores[fpPos
						// - 1].split("-")[1]));
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
						// [19Feb2016][Tue] - Add
						// info.setFpVerifyScr(Integer.parseInt(fpVerifyScores[fpPos
						// - 1].split("-")[1]));
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
		mav.addObject("fpMap", fpMap);
		// [19Feb2016][Tue] - Add
		// mav.addObject("fpVerifyMatchScore",
		// Integer.parseInt(fpVerifyMatchScore));
		mav.addObject("fpVerifyMatchScore", 1);
		mav.addObject("viewFPFalg", viewFPFalg);

		// //end anh van tay
		mav.addObject("checkBCA_result", false);

		try {
			List<BlackListApi> modelBl = new ArrayList<BlackListApi>();
			List<IdListApi> modelId = new ArrayList<IdListApi>();
			ObjectMapper mapper = new ObjectMapper();
			String jsonModel = "";

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

			// /Xu ly ket qua tu BCA
			com.nec.asia.nic.util.RequestVerifyInfo requestDocument = new com.nec.asia.nic.util.RequestVerifyInfo();
			requestDocument.setIdNumber(nicTransaction.getNin());
			requestDocument.setName(nicTransaction.getNicRegistrationData()
					.getSurnameLine1());
			requestDocument.setDateOfBirth(new SimpleDateFormat("dd-MM-yyyy")
					.format(nicTransaction.getNicRegistrationData()
							.getDateOfBirth()));
			requestDocument.setPlaceOfBirth(nicTransaction
					.getNicRegistrationData().getPlaceOfBirth());
			requestDocument.setPassportNo(nicTransaction.getPrevPassportNo());

			// Đánh dấu dữ liệu trả về từ BCA - false: chưa có hoặc lấy lỗi /
			// true:
			// là đã có và lấy đc về

			jsonModel = mapper.writeValueAsString(requestDocument);
			String requestUrl = "http://192.168.1.15:8044/app/rest/v2/queries/epp$Investigate/findBlackList";
			String resultSync = sendPostAuthentication(requestUrl, jsonModel,
					tokenBNG, "Bearer");

			if (!resultSync.equals("441") && !StringUtils.isEmpty(resultSync)) {
				JSONObject myResponse = new JSONObject(resultSync);
				if (!myResponse.toString().equals("{}")) {
					JSONObject data_ = myResponse.getJSONObject("data");
					// Lấy dữ liệu danh sách đen
					JSONObject bls_ = data_.getJSONObject("bl_lists");
					JSONArray arrayResp = bls_.getJSONArray("bl_list");
					for (int i = 0; i < arrayResp.length(); i++) {
						JSONObject bl_ = arrayResp.getJSONObject(i);
						BlackListApi bl = new BlackListApi();
						bl.setID(getMessageFromServerLong(bl_, "ID"));
						bl.setNAME(getMessageFromServerString(bl_, "NAME"));
						bl.setSEARCH_NAME(getMessageFromServerString(bl_,
								"SEARCH_NAME"));
						bl.setNICK_NAME(getMessageFromServerString(bl_,
								"NICK_NAME"));
						bl.setDATE_OF_BIRTH(getMessageFromServerString(bl_,
								"DATE_OF_BIRTH"));
						bl.setGENDER(getMessageFromServerString(bl_, "GENDER"));
						bl.setETHNIC(getMessageFromServerString(bl_, "ETHNIC"));
						bl.setRELIGION(getMessageFromServerString(bl_,
								"RELIGION"));
						bl.setORIGIN_NATIONALITY_ID(getMessageFromServerInt(
								bl_, "ORIGIN_NATIONALITY_ID"));
						bl.setCURRENT_NATIONALITY_ID(getMessageFromServerInt(
								bl_, "CURRENT_NATIONALITY_ID"));
						bl.setPLACE_OF_BIRTH_ID(getMessageFromServerInt(bl_,
								"PLACE_OF_BIRTH_ID"));
						bl.setID_NUMBER(getMessageFromServerString(bl_,
								"ID_NUMBER"));
						bl.setADDRESS(getMessageFromServerString(bl_, "ADDRESS"));
						bl.setNOTE(getMessageFromServerString(bl_, "NOTE"));
						bl.setTYPE_(getMessageFromServerString(bl_, "TYPE_"));

						List<FileAttachmentApi> modelAttachment = new ArrayList<FileAttachmentApi>();
						try {
							JSONObject attachs_ = bl_
									.getJSONObject("bl_attachments");
							JSONObject attach_ = attachs_
									.getJSONObject("bl_attachment");

							FileAttachmentApi f_at = new FileAttachmentApi();
							f_at.setID(getMessageFromServerLong(attach_, "ID"));
							f_at.setBLACKLIST_ID(getMessageFromServerLong(
									attach_, "BLACKLIST_ID"));
							f_at.setTYPE_(getMessageFromServerString(attach_,
									"TYPE_"));
							f_at.setFILE_NAME(getMessageFromServerString(
									attach_, "FILE_NAME"));
							f_at.setBASE64(getMessageFromServerString(attach_,
									"BASE64"));

							modelAttachment.add(f_at);
						} catch (Exception e) {
						}

						bl.setFileAttachments(modelAttachment);
						modelBl.add(bl);
					}

					// Lấy dữ liệu nhân thân
					JSONObject idl_ = data_.getJSONObject("id_lists");
					JSONArray arrayId = idl_.getJSONArray("id_list");
					for (int i = 0; i < arrayId.length(); i++) {
						JSONObject id_ = arrayId.getJSONObject(i);
						IdListApi id = new IdListApi();
						id.setID(getMessageFromServerLong(id_, "ID"));
						id.setNAME(getMessageFromServerString(id_, "NAME"));
						id.setSEARCH_NAME(getMessageFromServerString(id_,
								"SEARCH_NAME"));
						id.setDATE_OF_BIRTH(getMessageFromServerString(id_,
								"DATE_OF_BIRTH"));
						id.setGENDER(getMessageFromServerString(id_, "GENDER"));
						id.setPLACE_OF_BIRTH(getMessageFromServerString(id_,
								"PLACE_OF_BIRTH"));
						id.setID_NUMBER(getMessageFromServerString(id_,
								"ID_NUMBER"));
						id.setDATE_OF_ISSUE(getMessageFromServerString(id_,
								"DATE_OF_ISSUE"));
						id.setFATHER_NAME(getMessageFromServerString(id_,
								"FATHER_NAME"));
						id.setMOTHER_NAME(getMessageFromServerString(id_,
								"MOTHER_NAME"));
						id.setSTATUS(getMessageFromServerString(id_, "STATUS"));

						List<FileAttachmentApi> modelAttachment = new ArrayList<FileAttachmentApi>();
						try {
							JSONObject attachs_ = id_
									.getJSONObject("id_attachments");
							JSONObject attach_ = attachs_
									.getJSONObject("id_attachment");

							FileAttachmentApi f_at = new FileAttachmentApi();
							f_at.setIDENTIFICATION_ID(getMessageFromServerLong(
									attach_, "IDENTIFICATION_ID"));
							f_at.setFILE_NAME(getMessageFromServerString(
									attach_, "NAME"));
							f_at.setBASE64(getMessageFromServerString(attach_,
									"BASE64"));
							modelAttachment.add(f_at);
						} catch (Exception e) {
						}

						id.setFileAttachments(modelAttachment);
						modelId.add(id);
					}
				}

				mav.addObject("checkBCA_result", true);
				mav.addObject("modelBl", modelBl);
				mav.addObject("modelId", modelId);
			}
		} catch (JsonGenerationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonMappingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e){
			
		}
		

	}

	//Xử lý tạo danh sách bàn giao
	@RequestMapping(value = { "/createHandover" })
	public ModelAndView createHandover(
			@ModelAttribute(value = "formData") InvestigationAssignmentData investigationHitData,
			BindingResult result, WebRequest request,
			HttpServletRequest httpRequest, Model model,
			@RequestParam("selectedJobIds") String[] checkboxvalues)
			throws Throwable {
		logger.info("createHandover");

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

					uploadJobService
							.approveJobStatus_Confirmed(
									Long.valueOf(lstUpload.get(0)
											.getWorkflowJobId()),
									userSession.getUserName(),
									userSession.getWorkstationId(),
									NicTransactionLog.TRANSACTION_STAGE_PERSO_REGISTER,
									NicTransactionLog.TRANSACTION_STATUS_NIC_PENDING_PERSO,
									NicUploadJob.RECORD_STATUS_APPROVE_PERSO);
					// Lưu id sang bảng danh sách bàn giao
					listIdTrans += st + ",";
				}

				if (!StringUtils.isEmpty(listIdTrans)) {
					NicListHandover handover = new NicListHandover();
					handover.setCreateBy(userSession.getUserId());
					handover.setCreateDate(date);
					//10/06/2020 thieu typeList, khong khoi tao NicListHandoverId.
//					handover.setPackageId(investigationHitData
//							.getCurrentlyAssignedToUserId());
					//handover.setTransactionId(listIdTrans);
					handover.setPackageName(investigationHitData
							.getUnassignAllForUserUserId());
					//handover.setTypeList(3);// Dành cho Duyệt In
					//handover.setDescription("");
					listHandoverService.createRecordHandover(handover);

				}
			} else {
				messages.add("Chưa có bản ghi nào được chọn.");
				httpRequest.setAttribute("messages", messages);
				return new ModelAndView(
						"forward:/servlet/investigation/getPersoJob");
			}
			messages.add("Tạo danh sách bàn giao duyệt In thành công.");
		} catch (JobNoLongerAssignedToOfficerException e) {
			messages.add("Tạo danh sách bàn giao duyệt In thất bại. Công việc hiện tại không được phân công cho User này");
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

		investigationHitData = new InvestigationAssignmentData();
		httpRequest.setAttribute("messages", messages);
		return new ModelAndView("forward:/servlet/investigation/getPersoJob");
	}
	
	
	///Xử lý gửi yêu cầu sang bộ công an duyệt thông tin
		@RequestMapping(value = { "/pending_bca" })
		public ModelAndView pending_bca(
				@ModelAttribute(value = "investigationHitData") InvestigationHitData investigationHitData,
				BindingResult result, WebRequest request,
				HttpServletRequest httpRequest, Model model) throws Throwable {
			logger.info("pending_bca");

			List<String> messages = new LinkedList<String>();
			try {
				HttpSession session = httpRequest.getSession();
				UserSession userSession = (UserSession) session
						.getAttribute("userSession");
				String tranId = "";
				NicUploadJob nicUpload = uploadJobService.findById(Long.valueOf(investigationHitData.getUploadJobId()));
				tranId = nicUpload.getTransactionId();
				NicTransaction nicTran = nicTransactionService.findById(tranId);
				
				/*do {

					if (tokenA98 == "" || tokenA98 == null) {
						Map<String, String> resultToken = GetTokenAPI("a98", "a98");
						if (resultToken != null) {
							tokenA98 = resultToken.get("access_token");
							expireTokenA98 = Integer.parseInt(resultToken
									.get("expires_in"));
							typeTokenA98 = resultToken.get("token_type");
						}
					}
					if (expireTokenA98 == 0 && tokenA98 != "") {
						ReAccessToken(tokenA98);
					}

				} while (tokenA98 == "");*/

				do {
					if (tokenBNG == ""|| tokenBNG == null) {
						Map<String, String> resultToken = GetTokenAPI("bng", "bng");
						if (resultToken != null) {
							tokenBNG = resultToken.get("access_token");
							/*expireTokenBNG = Integer.parseInt(resultToken
									.get("expires_in"));
							typeTokenBNG = resultToken.get("token_type");*/
						}
					}
					/*if (expireTokenBNG == 0 && tokenBNG != "") {
						ReAccessToken(tokenBNG);
					}*/

				} while (tokenBNG == "");
				
				RequestVerifyInfo requestDocument = new RequestVerifyInfo();
				requestDocument.setIdNumber(nicTran.getNin());
				requestDocument.setName(nicTran.getNicRegistrationData().getSurnameLine1());
				requestDocument.setDateOfBirth(new SimpleDateFormat("dd-MM-yyyy").format(nicTran.getNicRegistrationData().getDateOfBirth()));
				requestDocument.setPlaceOfBirth(nicTran.getNicRegistrationData().getPlaceOfBirth());
				requestDocument.setPassportNo(nicTran.getPrevPassportNo());
				
				ObjectMapper mapper = new ObjectMapper();
				String jsonModel = mapper.writeValueAsString(requestDocument);
				String requestUrl = "http://192.168.1.15:8044/app/rest/v2/queries/epp$Investigate/findBlackList";
				String resultSync = sendPostAuthentication(requestUrl, jsonModel,
									tokenBNG, "Bearer");
				if(resultSync.equals("441")){
					messages.add("Lỗi khi gửi thông tin sang Bộ công an - API");
				}
				else{
					
					/*JSONObject myResponse = new JSONObject(st);
					JSONObject bls_ = myResponse.getJSONObject("bl_lists");
					JSONArray arrayResp = bls_.getJSONArray("bl_list");
					for (int i = 0; i < arrayResp.length(); i++) {
						JSONObject bl_ = arrayResp.getJSONObject(i);
						bl_.ge
					}
					*/
					uploadJobService.approveJobStatus_Confirmed(
							Long.valueOf(investigationHitData.getUploadJobId()),
							userSession.getUserName(), userSession.getWorkstationId(),
							NicTransactionLog.TRANSACTION_STAGE_INVESTIGATION,
							NicTransactionLog.TRANSACTION_STATUS_NIC_PENING_BCA,
							NicUploadJob.RECORD_STATUS_PENDING_BCA);
					
					NicUploadJob nicUp = new NicUploadJob();
					nicUp = uploadJobService.findById(Long.valueOf(investigationHitData.getUploadJobId()));
					if(nicUp != null){
						//Cập nhật trạng thái đã gửi dữ liệu xác thực từ BCA
						nicUp.setValidateInfoBca(0);
						uploadJobService.saveOrUpdate(nicUp);
					}
					/* TODO: XỬ LÝ GỌI API CHUYỂN DỮ LIỆU SANG BỘ CÔNG AN */
					messages.add("Đã gửi bản ghi sang bộ công an kiểm duyệt thông tin.");
					
					
				}
				
			
			} catch (JobNoLongerAssignedToOfficerException e) {
				messages.add("Gửi thông tin sang bộ công an thất bại. Công việc hiện tại không được phân công cho User này");
			} catch (Exception e) {
				e.printStackTrace();
			}

			httpRequest.setAttribute("messages", messages);
			return new ModelAndView(
					"forward:/servlet/investigation/investigation");
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

}
