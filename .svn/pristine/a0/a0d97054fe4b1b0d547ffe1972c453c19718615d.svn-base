package com.nec.asia.nic.investigation.controller;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Serializable;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Random;
import java.util.TimeZone;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRExporter;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.JRPdfExporter;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.bouncycastle.pqc.math.linearalgebra.PolynomialRingGF2;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.displaytag.tags.TableTagParameters;
import org.displaytag.util.ParamEncoder;
import org.hibernate.mapping.Array;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.ui.jasperreports.JasperReportsUtils;
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

import ar.com.fdvs.dj.core.DynamicJasperHelper;
import ar.com.fdvs.dj.core.layout.ClassicLayoutManager;
import ar.com.fdvs.dj.domain.DynamicReport;
import ar.com.fdvs.dj.domain.builders.FastReportBuilder;
import bsh.StringUtil;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.nec.asia.nic.admin.domain.User;
import com.nec.asia.nic.common.dto.RicRegistrationDTO;
import com.nec.asia.nic.comp.job.dto.SearchHitDto;
import com.nec.asia.nic.comp.listHandover.domain.NicListHandover;
import com.nec.asia.nic.comp.listHandover.domain.NicListHandoverDetail;
import com.nec.asia.nic.comp.listHandover.domain.NicListHandoverId;
import com.nec.asia.nic.comp.listHandover.domain.NicPersoLocations;
import com.nec.asia.nic.comp.listHandover.service.ListHandoverService;
import com.nec.asia.nic.comp.listHandover.service.PersoLocationsService;
import com.nec.asia.nic.comp.trans.domain.DocumentInfo;
import com.nec.asia.nic.comp.trans.domain.EppArchiveCode;
import com.nec.asia.nic.comp.trans.domain.NicRegistrationData;
import com.nec.asia.nic.comp.trans.domain.NicRejectionData;
import com.nec.asia.nic.comp.trans.domain.NicTransaction;
import com.nec.asia.nic.comp.trans.domain.NicTransactionAttachment;
import com.nec.asia.nic.comp.trans.domain.NicTransactionLog;
import com.nec.asia.nic.comp.trans.domain.NicTransactionPackage;
import com.nec.asia.nic.comp.trans.domain.NicTransactionPaymentDetail;
import com.nec.asia.nic.comp.trans.domain.NicUploadJob;
import com.nec.asia.nic.comp.trans.domain.NicWorkflowJob;
import com.nec.asia.nic.comp.trans.domain.NicWorkflowProcess;
import com.nec.asia.nic.comp.trans.dto.AssignmentFilter;
import com.nec.asia.nic.comp.trans.dto.AssignmentFilterAll;
import com.nec.asia.nic.comp.trans.dto.FacialImgDto;
import com.nec.asia.nic.comp.trans.dto.InvestigationHitData;
import com.nec.asia.nic.comp.trans.dto.NicUploadJobDto;
import com.nec.asia.nic.comp.trans.exception.JobNoLongerAssignedToOfficerException;
import com.nec.asia.nic.comp.trans.service.ArchiveCodeService;
import com.nec.asia.nic.comp.trans.service.NicRegistrationDataService;
import com.nec.asia.nic.comp.trans.service.NicTransactionAttachmentService;
import com.nec.asia.nic.comp.trans.service.NicTransactionPackageService;
import com.nec.asia.nic.comp.trans.service.NicTransactionPaymentDetaiService;
import com.nec.asia.nic.comp.trans.service.NicTransactionService;
import com.nec.asia.nic.comp.trans.service.NicUploadJobService;
import com.nec.asia.nic.comp.trans.service.TransactionLogService;
import com.nec.asia.nic.comp.trans.service.exception.NicUploadJobServiceException;
import com.nec.asia.nic.comp.workflowProcess.service.WorkflowProcessService;
import com.nec.asia.nic.framework.PaginatedResult;
import com.nec.asia.nic.framework.admin.code.domain.CodeValues;
import com.nec.asia.nic.framework.admin.code.domain.Codes;
import com.nec.asia.nic.framework.admin.code.domain.Parameters;
import com.nec.asia.nic.framework.admin.code.domain.ParametersId;
import com.nec.asia.nic.framework.admin.code.domain.PaymentDef;
import com.nec.asia.nic.framework.admin.code.service.CodesService;
import com.nec.asia.nic.framework.admin.code.service.ParametersService;
import com.nec.asia.nic.framework.admin.code.service.PaymentDefService;
import com.nec.asia.nic.framework.admin.rbac.domain.Users;
import com.nec.asia.nic.framework.admin.rbac.service.RoleService;
import com.nec.asia.nic.framework.admin.rbac.service.UserService;
import com.nec.asia.nic.framework.admin.site.dao.SiteRepositoryDao;
import com.nec.asia.nic.framework.admin.site.domain.SiteGroups;
import com.nec.asia.nic.framework.admin.site.domain.SiteRepository;
import com.nec.asia.nic.framework.admin.site.service.SiteService;
import com.nec.asia.nic.framework.controller.AbstractController;
import com.nec.asia.nic.framework.dao.DaoException;
import com.nec.asia.nic.framework.security.service.ADUser;
import com.nec.asia.nic.investigation.controller.InvestigationController.DataJson;
import com.nec.asia.nic.investigation.controller.InvestigationController.RequestDocument;
import com.nec.asia.nic.investigation.dto.InfoPaymentDto;
import com.nec.asia.nic.investigation.dto.InfoPerson;
import com.nec.asia.nic.investigation.dto.InfoPersons;
import com.nec.asia.nic.investigation.dto.InvestigationOffer;
import com.nec.asia.nic.util.BaseListResponse;
import com.nec.asia.nic.util.Constants;
import com.nec.asia.nic.util.PrintLocation;
import com.nec.asia.nic.util.RegistrationConstants;
import com.nec.asia.nic.util.RequestBase;
import com.nec.asia.nic.util.RequestVerifyInfo;
import com.nec.asia.nic.util.TransLogInfoXmlConvertor;
import com.nec.asia.nic.util.pagingUtil;
import com.nec.asia.nic.utils.HelperClass;
import com.nec.asia.nic.web.session.UserSession;

@Controller
@RequestMapping(value = "/investigationConfirm")
public class InvestigationConfirmController extends InvestigationController {

	private static final Logger logger = LoggerFactory
			.getLogger(InvestigationConfirmController.class);

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
	private PersoLocationsService persoLocationsService;

	@Autowired
	private SiteRepositoryDao siteRepositoryDao;
	
	
	@Autowired
	private NicTransactionPackageService transactionPackageService;

	@Autowired
	private WorkflowProcessService workflowProcessService;

	@Autowired
	@Qualifier("userService")
	private UserService userService;
	
	@Autowired
	private RoleService roleService;
	
	@Autowired
	private SiteService siteService;
	
	@Autowired
	private PaymentDefService paymentDefService;
	
	@Autowired
	private NicTransactionPaymentDetaiService paymentDetailService;
	
	@Autowired
	private ArchiveCodeService archiveCodeService;
	
	@Autowired
	private NicRegistrationDataService regDataService;
	
	@Autowired
	private TransactionLogService nicTransactionLogService;
	//@Autowired
	//private SiteRe

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

	@RequestMapping(value = "/investigationConfirmList")
	public ModelAndView getInvestigationConfirmList(
			@ModelAttribute(value = "formData") InvestigationAssignmentData investigationAssignmentData,
			WebRequest request, HttpServletRequest httpRequest, ModelMap model) {
		logger.info("getInvestigationConfirmList");

		int pageNo = 1;

		String tableId = "row";
		String pageNumber = request.getParameter((new ParamEncoder(tableId)
				.encodeParameterName(TableTagParameters.PARAMETER_PAGE)));
		if (StringUtils.isNotBlank(pageNumber)
				&& StringUtils.isNumeric(pageNumber)) {
			pageNo = Integer.parseInt(pageNumber);
		}
		pageNo = !StringUtils.isEmpty(request.getParameter("pageNo")) ? Integer.parseInt(request.getParameter("pageNo")) : 1;
		return this.getInvestigationConfirmList(investigationAssignmentData,
				request, httpRequest, model, pageNo, null, null);
	}
	
	@RequestMapping(value = "/JobInvestigationConfirmAgain")
	public ModelAndView JobInvestigationConfirmAgain(
			@ModelAttribute(value = "formData") InvestigationAssignmentData investigationAssignmentData,
			WebRequest request, HttpServletRequest httpRequest, ModelMap model) {
		logger.info("getInvestigationConfirmList");

		int pageNo = 1;

		String tableId = "row";
		String pageNumber = request.getParameter((new ParamEncoder(tableId)
				.encodeParameterName(TableTagParameters.PARAMETER_PAGE)));
		if (StringUtils.isNotBlank(pageNumber)
				&& StringUtils.isNumeric(pageNumber)) {
			pageNo = Integer.parseInt(pageNumber);
		}
		pageNo = !StringUtils.isEmpty(request.getParameter("pageNo")) ? Integer.parseInt(request.getParameter("pageNo")) : 1;
		return this.getInvestigationConfirmAgain(investigationAssignmentData,
				request, httpRequest, model, pageNo, null, null);
	}
	
	@RequestMapping(value = "/noteApprove")
	public ModelAndView noteApprove(
			@ModelAttribute(value = "formData") InvestigationAssignmentData investigationAssignmentData,
			WebRequest request, HttpServletRequest httpRequest, ModelMap model) {
		logger.info("getNoteApprove");

		int pageNo = 1;

		String tableId = "row";
		String pageNumber = request.getParameter((new ParamEncoder(tableId)
				.encodeParameterName(TableTagParameters.PARAMETER_PAGE)));
		if (StringUtils.isNotBlank(pageNumber)
				&& StringUtils.isNumeric(pageNumber)) {
			pageNo = Integer.parseInt(pageNumber);
		}
		pageNo = !StringUtils.isEmpty(request.getParameter("pageNo")) ? Integer.parseInt(request.getParameter("pageNo")) : 1;
		return this.getNoteApprove(investigationAssignmentData,
				request, httpRequest, model, pageNo, null, null);
	}
	
	@RequestMapping(value = "/JobInvestigationApprove")
	public ModelAndView JobInvestigationApprove(
			@ModelAttribute(value = "formData") InvestigationAssignmentData investigationAssignmentData,
			WebRequest request, HttpServletRequest httpRequest, ModelMap model) {
		logger.info("getInvestigationConfirmList");

		int pageNo = 1;

		String tableId = "row";
		String pageNumber = request.getParameter((new ParamEncoder(tableId)
				.encodeParameterName(TableTagParameters.PARAMETER_PAGE)));
		if (StringUtils.isNotBlank(pageNumber)
				&& StringUtils.isNumeric(pageNumber)) {
			pageNo = Integer.parseInt(pageNumber);
		}
		pageNo = !StringUtils.isEmpty(request.getParameter("pageNo")) ? Integer.parseInt(request.getParameter("pageNo")) : 1;
		return this.JobInvestigationApprove(investigationAssignmentData,
				request, httpRequest, model, pageNo, null, null);
	}
	
	@ResponseBody
	@RequestMapping(value="/searchInvesB")
	public List<NicUploadJobDto> searchListA(@RequestParam String packageNumber, @RequestParam String createDate, @RequestParam String endDate, @RequestParam String styleList,
			WebRequest request, HttpServletRequest httpRequest, ModelMap model) throws NicUploadJobServiceException, ParseException{
		HttpSession session = httpRequest.getSession();
		UserSession userSession = (UserSession) session.getAttribute("userSession");
//		int pageSize = Constants.DEFAULT_PAGE_MAX_SIZE;
//		Date creDate = null;
//		if (!StringUtils.isEmpty(createDate)){				
//			String pattern = "dd-MMM-yyyy";
//		    SimpleDateFormat format = new SimpleDateFormat(pattern);
//		    creDate = format.parse(createDate);
//		}
//		Date eDate = null;
//		if (!StringUtils.isEmpty(endDate)){				
//			String pattern = "dd-MMM-yyyy";
//		    SimpleDateFormat format = new SimpleDateFormat(pattern);
//		    eDate = format.parse(endDate);
//		}
		String creDate1 = null;
		String endDate1 = null;
		if (!StringUtils.isEmpty(createDate)){
			creDate1 = com.nec.asia.nic.comp.trans.utils.HelperClass.convertMonthNumberToMonthWord1(createDate);
		}
		if (!StringUtils.isEmpty(endDate)){
			endDate1 = com.nec.asia.nic.comp.trans.utils.HelperClass.convertMonthNumberToMonthWord1(endDate);
		}
		Date creDate = !StringUtils.isEmpty(creDate1) ? com.nec.asia.nic.comp.trans.utils.HelperClass.convertStringToDate1(creDate1) : Calendar.getInstance().getTime();
		Date eDate = !StringUtils.isEmpty(endDate1) ? com.nec.asia.nic.comp.trans.utils.HelperClass.convertStringToDate1(endDate1) : Calendar.getInstance().getTime();
		Calendar eCal = Calendar.getInstance();
		eCal.setTime(eDate);
		eCal.add(Calendar.DATE, 1);
		Date eDateFinish = eCal.getTime();

		List<NicUploadJobDto> pr = null;
		if(styleList.equals("0")){
			pr = uploadJobService.findAllForInvestigationPagination2(
					new String[] { NicUploadJob.RECORD_STATUS_COMPLETED }, userSession.getUserId(), null,
					new AssignmentFilterAll(creDate, eDateFinish, styleList, packageNumber));			
		}else {
			pr = uploadJobService.findListHandoverListB(8, null, userSession.getUserId() ,new AssignmentFilterAll(creDate, eDateFinish, "0", packageNumber));
		}
//		NicUploadJobDto[] ds1 = new NicUploadJobDto[pr.size()];
//		if(pr != null){
//			for(int i = 0; i < pr.size(); i++){
//				ds1[i] = pr.get(i);
//			}
//		}
		return pr;
	}
	
	@ResponseBody
	@RequestMapping(value="/searchUpdate")
	public List<NicUploadJobDto> searchUpdate(@RequestParam String packageNumber, @RequestParam String createDate, @RequestParam String endDate,
			WebRequest request, HttpServletRequest httpRequest, ModelMap model) throws Exception{
		HttpSession session = httpRequest.getSession();
		UserSession userSession = (UserSession) session.getAttribute("userSession");
		String creDate1 = null;
		String endDate1 = null;
		if (!StringUtils.isEmpty(createDate)){
			creDate1 = com.nec.asia.nic.comp.trans.utils.HelperClass.convertMonthNumberToMonthWord1(createDate);
		}
		if (!StringUtils.isEmpty(endDate)){
			endDate1 = com.nec.asia.nic.comp.trans.utils.HelperClass.convertMonthNumberToMonthWord1(endDate);
		}
		Date creDate = !StringUtils.isEmpty(creDate1) ? com.nec.asia.nic.comp.trans.utils.HelperClass.convertStringToDate1(creDate1) : Calendar.getInstance().getTime();
		Date eDate = !StringUtils.isEmpty(endDate1) ? com.nec.asia.nic.comp.trans.utils.HelperClass.convertStringToDate1(endDate1) : Calendar.getInstance().getTime();
		Calendar eCal = Calendar.getInstance();
		eCal.setTime(eDate);
		eCal.add(Calendar.DATE, 1);
		Date eDateFinish = eCal.getTime();

		List<NicUploadJobDto> pr = null;

		pr = nicTransactionService.searchUpdateB(packageNumber, creDate, eDateFinish);			
		
//		NicUploadJobDto[] ds1 = new NicUploadJobDto[pr.size()];
//		if(pr != null){
//			for(int i = 0; i < pr.size(); i++){
//				ds1[i] = pr.get(i);
//			}
//		}
		return pr;
	}
	
	@ResponseBody
	@RequestMapping(value="/distroyListB")
	public String distroyListB(@RequestParam String handoverId, @RequestParam String danhSachB, WebRequest request, HttpServletRequest httpRequest, ModelMap model){
		HttpSession session = httpRequest.getSession();
		UserSession userSession = (UserSession) session.getAttribute("userSession");
		String[] arr = danhSachB.split(",");
		for(int i = 0; i < arr.length; i++){		
			NicTransaction txn = nicTransactionService.findById(arr[i]);
			//txn.setNoteHandoverB(null);
			//txn.setLeaderOfficerId(null);
			nicTransactionService.saveOrUpdate(txn);		
		}
		NicListHandover handover = listHandoverService.findByPackageId(new NicListHandoverId(handoverId, NicListHandover.TYPE_LIST_B)).getModel();
		if(handover != null){
			handover.setUpdateBy(userSession.getUserId());
			handover.setUpdateDate(Calendar.getInstance().getTime());
			handover.setHandoverStage(false);
//			handover.setArchiveCode(null);
			listHandoverService.createRecordHandover(handover);		
			//return handover.getPackageId();
		}
		return "";
	}
	
	@ResponseBody
	@RequestMapping(value="/searchInvesBAgain")
	public List<NicUploadJobDto> searchInvesBAgain(@RequestParam String packageNumber, @RequestParam String createDate, @RequestParam String endDate, @RequestParam String styleList,
			WebRequest request, HttpServletRequest httpRequest, ModelMap model) throws NicUploadJobServiceException, ParseException{
		HttpSession session = httpRequest.getSession();
		UserSession userSession = (UserSession) session.getAttribute("userSession");
//		int pageSize = Constants.DEFAULT_PAGE_MAX_SIZE;
//		Date creDate = null;
//		if (!StringUtils.isEmpty(createDate)){				
//			String pattern = "dd-MMM-yyyy";
//		    SimpleDateFormat format = new SimpleDateFormat(pattern);
//		    creDate = format.parse(createDate);
//		}
//		Date eDate = null;
//		if (!StringUtils.isEmpty(endDate)){				
//			String pattern = "dd-MMM-yyyy";
//		    SimpleDateFormat format = new SimpleDateFormat(pattern);
//		    eDate = format.parse(endDate);
//		}
		String creDate1 = null;
		String endDate1 = null;
		if (!StringUtils.isEmpty(createDate)){
			creDate1 = com.nec.asia.nic.comp.trans.utils.HelperClass.convertMonthNumberToMonthWord1(createDate);
		}
		if (!StringUtils.isEmpty(endDate)){
			endDate1 = com.nec.asia.nic.comp.trans.utils.HelperClass.convertMonthNumberToMonthWord1(endDate);
		}
		Date creDate = !StringUtils.isEmpty(creDate1) ? com.nec.asia.nic.comp.trans.utils.HelperClass.convertStringToDate1(creDate1) : Calendar.getInstance().getTime();
		Date eDate = !StringUtils.isEmpty(endDate1) ? com.nec.asia.nic.comp.trans.utils.HelperClass.convertStringToDate1(endDate1) : Calendar.getInstance().getTime();
		Calendar eCal = Calendar.getInstance();
		eCal.setTime(eDate);
		eCal.add(Calendar.DATE, 1);
		Date eDateFinish = eCal.getTime();
		List<NicUploadJobDto> pr = null;
		pr = uploadJobService.findListHandoverListB(8, null, userSession.getUserId() ,new AssignmentFilterAll(creDate, eDateFinish, "0", packageNumber));
		//NicUploadJobDto[] ds1 = new NicUploadJobDto[pr.size()];
//		if(pr != null){
//			for(int i = 0; i < pr.size(); i++){
//				ds1[i] = pr.get(i);
//			}
//		}
		return pr;
	}
	
	@ResponseBody
	@RequestMapping(value="/searchApproveNic")
	public List<NicUploadJobDto> searchApprove(@RequestParam String packageNumber, @RequestParam String createDate, @RequestParam String endDate, @RequestParam String styleList,
			WebRequest request, HttpServletRequest httpRequest, ModelMap model) throws Exception{
		HttpSession session = httpRequest.getSession();
		UserSession userSession = (UserSession) session.getAttribute("userSession");
//		int pageSize = Constants.DEFAULT_PAGE_MAX_SIZE;
//		Date creDate = null;
//		if (!StringUtils.isEmpty(createDate)){				
//			String pattern = "dd-MMM-yyyy";
//		    SimpleDateFormat format = new SimpleDateFormat(pattern);
//		    creDate = format.parse(createDate);
//		}
//		Date eDate = null;
//		if (!StringUtils.isEmpty(endDate)){				
//			String pattern = "dd-MMM-yyyy";
//		    SimpleDateFormat format = new SimpleDateFormat(pattern);
//		    eDate = format.parse(endDate);
//		}
		String creDate1 = null;
		String endDate1 = null;
		if (!StringUtils.isEmpty(createDate)){
			creDate1 = com.nec.asia.nic.comp.trans.utils.HelperClass.convertMonthNumberToMonthWord1(createDate);
		}
		if (!StringUtils.isEmpty(endDate)){
			endDate1 = com.nec.asia.nic.comp.trans.utils.HelperClass.convertMonthNumberToMonthWord1(endDate);
		}
		Date creDate = !StringUtils.isEmpty(creDate1) ? com.nec.asia.nic.comp.trans.utils.HelperClass.convertStringToDate1(creDate1) : Calendar.getInstance().getTime();
		Date eDate = !StringUtils.isEmpty(endDate1) ? com.nec.asia.nic.comp.trans.utils.HelperClass.convertStringToDate1(endDate1) : Calendar.getInstance().getTime();
		Calendar eCal = Calendar.getInstance();
		eCal.setTime(eDate);
		eCal.add(Calendar.DATE, 1);
		Date eDateFinish = eCal.getTime();
		List<NicUploadJobDto> pr = new ArrayList<NicUploadJobDto>();
		if(styleList.equals("0")){
			pr = uploadJobService.findListHandoverListB(8, userSession.getUserId() , null, new AssignmentFilterAll(creDate, eDateFinish, styleList, packageNumber));			
		}else if(styleList.equals("1")){
			pr = uploadJobService.findListHandoverListBApprove(8, userSession.getUserId() , null, new AssignmentFilterAll(creDate, eDateFinish, styleList, packageNumber));
		}else{
			List<NicUploadJobDto> pr1 = uploadJobService.findListHandoverListB(8, userSession.getUserId() , null, new AssignmentFilterAll(creDate, eDateFinish, "0", packageNumber));			
			List<NicUploadJobDto> pr2 = uploadJobService.findListHandoverListBApprove(8, userSession.getUserId() , null, new AssignmentFilterAll(creDate, eDateFinish, "1", packageNumber));
			pr.addAll(pr1);
			pr.addAll(pr2);
		}
		//NicUploadJobDto[] ds1 = new NicUploadJobDto[pr.size()];
//		if(pr != null){
//			for(int i = 0; i < pr.size(); i++){
//				ds1[i] = pr.get(i);
//			}
//		}
		return pr;
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
			dto.setEsColectionDate(nicTran.getEstDateOfCollection() != null ? HelperClass.convertDateToString2(nicTran.getEstDateOfCollection()) : "");
		}
		List<NicUploadJob> list = uploadJobService.findAllByTransactionId(tranactionId);
		if(CollectionUtils.isNotEmpty(list)){
			//dto.setNoteApprove(list.get(0).getNoteApprove());
			dto.setNoteApprovePerson(list.get(0).getNoteApprovePerson() != null ? list.get(0).getNoteApprovePerson() : "");
			dto.setNoteApproveObj(list.get(0).getNoteApproveObj() != null ? list.get(0).getNoteApproveObj() : "");
			dto.setNoteApproveNin(list.get(0).getNoteApproveNin() != null ? list.get(0).getNoteApproveNin() : "");
		}
		dto.setCount(1);		
		if(StringUtils.isNotEmpty(nicTran.getInfoPerson())){
			JAXBContext jaxbContext = JAXBContext.newInstance(InfoPersons.class);
			Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
			StringReader reader = new StringReader(nicTran.getInfoPerson());
			InfoPersons persons = (InfoPersons) unmarshaller.unmarshal(reader);
			List<InfoPerson> listPerson = null;
			if(persons != null){
				listPerson = persons.getInfoPersons();
				if(listPerson != null && listPerson.size() > 0){
					dto.setCount(listPerson.size() + 1);
				}
			}			
		}
		return dto;
	}
	
	@ResponseBody
	@RequestMapping(value="/getApprovePayment/{tranactionId}")
	public List<InfoPaymentDto> getApprovePayment(@PathVariable String tranactionId,
			WebRequest request, HttpServletRequest httpRequest, ModelMap model) throws NicUploadJobServiceException {
		List<InfoPaymentDto> listPayment = new ArrayList<InfoPaymentDto>();		
		//Lấy thông tin thanh toán
		Double tongTien = 0.0;
		try {
			NicTransaction nicTransaction = nicTransactionService.findById(tranactionId);		 	
		 	List<NicTransactionPaymentDetail> listDetail = paymentDetailService.findListDetailPaymentByTransactionId(nicTransaction.getNicTransactionPayment().getPaymentId(), null, null, true);
		 	for(NicTransactionPaymentDetail def : listDetail) {
		 		InfoPaymentDto dto = new InfoPaymentDto();
		 		dto.setMaPhuPhi(def.getSubTypePayment());
		 		dto.setTenPhuPhi(codesService.getCodeValueDescByIdName(def.getTypePayment(), def.getSubTypePayment(), ""));
		 		dto.setSoLuong(1);
		 		dto.setSoTien(this.formatTienTeVN(def.getPaymentAmount()));
		 		tongTien += def.getPaymentAmount();
		 		listPayment.add(dto);
		 	}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		//lấy ảnh mặt
		String photoStr = "";
		List<NicTransactionAttachment> photoList = nicTransactionAttachmentService.findNicTransactionAttachments(
				tranactionId, NicTransactionAttachment.DOC_TYPE_PHOTO_CAPTURE, NicTransactionAttachment.DEFAULT_SERIAL_NO).getListModel();				
		if (CollectionUtils.isNotEmpty(photoList) && photoList.size() > 0) {
			String images = Base64.encodeBase64String(photoList.get(0).getDocData());
			photoStr = "<img src=\'data:image/jpg;base64,"+ images +"' class=\"img-border doGetAJpgSafeVersion\" height=\"180\" width=\"135\" />";
		}else{
			photoStr = "<img class=\"img-border\" height=\"180\" width=\"135\" src=\"/eppcentral/resources/images/No_Image.jpg\">";
		}
		if(listPayment.size() == 0){
			listPayment.add(new InfoPaymentDto());
		}
		listPayment.get(0).setPhotoStr(photoStr);
		listPayment.get(0).setTongTien(this.formatTienTeVN(tongTien));
		NicTransaction nicTran = nicTransactionService.findById(tranactionId);
		if(nicTran != null){
			listPayment.get(0).setEsColectionDate(nicTran.getEstDateOfCollection() != null ? HelperClass.convertDateToString2(nicTran.getEstDateOfCollection()) : "");
		}
		return listPayment;
	}
	
	@ResponseBody
	@RequestMapping(value="/getInsertRec/{listA}/{kyDuyetA}/{code}/{stt}/{stage}/{place}")
	public ModelAndView getInsertReciept(@PathVariable String listA, @RequestBody String yourArray, @PathVariable String kyDuyetA, 
			@PathVariable String code, @PathVariable Integer stt, @PathVariable String stage, @PathVariable String place,
			WebRequest request, HttpServletRequest httpRequest, ModelMap model) throws Exception {
		JSONObject onb = new JSONObject(yourArray);
		
		Boolean testD = false;
		Boolean testC = false;
		Boolean testK = false;
		String handoverId = "";
		ModelAndView mav = new ModelAndView("investigation-offer");
		//Lấy user
		HttpSession session = httpRequest.getSession();
		UserSession userSession = (UserSession) session.getAttribute("userSession");
		Users users = userService.findById(userSession.getUserId());
		//
		List<InvestigationOffer> listOffer = new ArrayList<InvestigationOffer>();
		if(!StringUtils.isEmpty(listA) && listA.contains(",")){
			InvestigationOffer offerRoot = new InvestigationOffer();
			String[] arrayA = listA.split(",");
			String matailieu = code + stt;
			if(stage.equals("Y")){
				handoverId = createListBWithHandover(users, listA, userSession, matailieu, place, onb, kyDuyetA, "CREATED_B");
			}
			for(int i = 0; i < arrayA.length; i++){
				//tạo danh sách B
				String stageB = (onb.has("key_" + arrayA[i]) && !onb.isNull("key_" + arrayA[i])) ? onb.getString("key_" + arrayA[i]) : "";
				if(stageB.equals("D")){
					testD = true;
				}
				if(stageB.equals("C")){
					testC = true;
				}
				if(stageB.equals("K")){
					testK = true;
				}
				if(stage.equals("Y")){
					saveTranPackage(handoverId, arrayA[i], stageB, onb);
				}
			}
			int count = 0;
			int countPerson = 0;
			for(int i = 0; i < arrayA.length; i++){
				//tạo danh sách B
				String stageB = (onb.has("key_" + arrayA[i]) && !onb.isNull("key_" + arrayA[i])) ? onb.getString("key_" + arrayA[i]) : "";
				if(testD){
					if(!stageB.equals("D"))
						continue;
				}else if(testC){
					if(!stageB.equals("C"))
						continue;
				}else if(testK){
					if(!stageB.equals("K"))
						continue;
				}else if(!testD && !testC && !testK){
					continue;
				}
				count++;
				NicTransaction nicTransaction = nicTransactionService.findById(arrayA[i]);
				//nicTransaction.setLeaderOfficerId(kyDuyetA);
				//nicTransaction.setNoteHandoverB((onb.has(arrayA[i]) && !onb.isNull(arrayA[i])) ? onb.getString(arrayA[i]) : "");
				//createListB(nicTransaction, handoverId, arrayA[i] , stageB);
				nicTransactionService.saveOrUpdate(nicTransaction);
				//end
				List<NicUploadJob> nicJob = uploadJobService.findAllByTransactionId(arrayA[i]);
				if(nicJob != null && nicJob.size() > 0){
					NicRegistrationData regData = nicJob.get(0).getNicTransaction().getNicRegistrationData();
					InvestigationOffer offer = new InvestigationOffer();
					offer.setStt(count);
					offer.setSoHoSo(nicJob.get(0).getTransactionId());
					offer.setSoDanhSach(nicJob.get(0).getNicTransaction().getPackageId());
					offer.setHoTen(HelperClass.createFullName(regData.getSurnameFull(), regData.getMiddlenameFull(), regData.getFirstnameFull()));
					String dateDob = regData.getDateOfBirth() != null ? HelperClass.convertDateToString2(regData.getDateOfBirth()) : "";
					offer.setNgaySinh(HelperClass.loadDateOfBirth(dateDob, regData.getDefDateOfBirth()));
					//offer.setNgaySinh(regData.getDateOfBirth() != null ? HelperClass.convertDateToString2(regData.getDateOfBirth()) : "");
					offer.setSoCMND(nicJob.get(0).getNicTransaction().getNin());
					if(!StringUtils.isEmpty(regData.getGender())){
						offer.setGioiTinh(regData.getGender().equals("M") ? "Nam" : "Nữ");
					}else{
						offer.setGioiTinh("Không");
					}
					//offer.setNoiSinh(regData.getPlaceOfBirth());
					String noiSinh = codesService.getCodeValueDescByIdName("DISTRICT", regData.getPlaceOfBirth(), "");
					if(StringUtils.isNotEmpty(noiSinh)){
						offer.setNoiSinh(noiSinh);								
					}else{
						offer.setNoiSinh(regData.getPlaceOfBirth());	
					}
					String diaChi = regData.getAddressLine1();
					String tinhTP = codesService.getCodeValueDescByIdName(Constants.CODE_DISTRICT_ID, regData.getAddressLine5(), "");
					String quanHuyen = codesService.getCodeValueDescByIdName(Constants.CODE_TOWN_VILLAGE_ID, regData.getAddressLine4(), "");
					if(StringUtils.isNotEmpty(quanHuyen)){
						diaChi += ", " + quanHuyen;
					}
					if(StringUtils.isNotEmpty(tinhTP)){
						diaChi += ", " + tinhTP;						
					}
					offer.setSoBn(nicTransaction.getRecieptNo());
					offer.setDiaChi(diaChi);
					offer.setSoHC(nicTransaction.getPrevPassportNo() != null ? nicTransaction.getPrevPassportNo() : "");
					//offer.setNoiDungDeXuat("");
					offer.setGhiChu("");
					offerRoot.setNgayHenTra(HelperClass.convertDateToString2(nicTransaction.getEstDateOfCollection()));
					//Ảnh mặt
					List<NicTransactionAttachment> photoList = nicTransactionAttachmentService.findNicTransactionAttachments(
							arrayA[i], NicTransactionAttachment.DOC_TYPE_PHOTO_CAPTURE, NicTransactionAttachment.DEFAULT_SERIAL_NO).getListModel();				
					if (CollectionUtils.isNotEmpty(photoList) && photoList.size() > 0) {
						String images = Base64.encodeBase64String(photoList.get(0).getDocData());						
						offer.setAnhMat(images);
					}else{						
						offer.setAnhMat("");
					}
					//offer.setNoiDungDeXuat(nicTransaction.getNoteHandoverB());
					listOffer.add(offer);
					offerRoot.setNoiTra(place);
					Users usersLD = null; //userService.findByUserId(nicJob.get(0).getNicTransaction().getLeaderOfficerId()); chua lay duoc du lieu tu Handover
					if(usersLD != null){
						offerRoot.setLanhDao(usersLD.getUserName());
						offerRoot.setChucVu(usersLD.getPosition());
						SiteRepository sr = siteRepositoryDao.findBySiteId(usersLD.getSiteCode());
						offerRoot.setNoiXL(sr.getSiteName());
						
					}
					//offerRoot.setLanhDao(usersLD.getUserName());
					//offerRoot.setChucVu(usersLD.getPosition());
					if(testD){
						offerRoot.setSoDeXuat(handoverId);						
					}
					//SiteRepository sr = siteRepositoryDao.findBySiteId(usersLD.getSiteCode());
					//offerRoot.setNoiXL(sr.getSiteName());
				}
				if(StringUtils.isNotEmpty(nicTransaction.getInfoPerson())){
					JAXBContext jaxbContext = JAXBContext.newInstance(InfoPersons.class);
					Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
					StringReader reader = new StringReader(nicTransaction.getInfoPerson());
					InfoPersons persons = (InfoPersons) unmarshaller.unmarshal(reader);
					List<InfoPerson> listPerson = null;
					if(persons != null){
						listPerson = persons.getInfoPersons();
						if(listPerson != null && listPerson.size() > 0){
							countPerson += listPerson.size();
						}
					}								
				}
			}
			offerRoot.setKhuVuc(users.getSiteGroupCode());
			String[] ngayIn = HelperClass.convertDateToString2(Calendar.getInstance().getTime()).split("/");
			offerRoot.setNgayHienTai(ngayIn[0]);
			offerRoot.setThangHienTai(ngayIn[1]);
			offerRoot.setNamHienTai(ngayIn[2]);
			Users usersCB = userService.findByUserId(userSession.getUserId());
			if(usersCB != null)
				offerRoot.setCanBo(usersCB.getUserName());
			offerRoot.setSoLuongHS(count);
			offerRoot.setSoNguoi(count + countPerson);
			if(testD){
				mav = new ModelAndView("investigation-offer");
			}else if(testC){
				mav = new ModelAndView("investigation-offer-c");
			}else if(testK){
				mav = new ModelAndView("investigation-offer-k");
			}
			mav.addObject("entityOffer", offerRoot);
			mav.addObject("listOffer", listOffer);
		}
		
		return mav;
	}
	
	public void saveTranPackage(String handoverId, String arrayA, String stageB, JSONObject onb){
		if(StringUtils.isEmpty(stageB) || stageB.equals("0")){
			return;
		}
		String note = (onb.has(arrayA) && !onb.isNull(arrayA)) ? onb.getString(arrayA) : "";
		NicTransactionPackage tranPack = new NicTransactionPackage();
		tranPack.setPackageId(handoverId);
		tranPack.setTransactionId(arrayA);
		tranPack.setTypeList(8);
		tranPack.setStage(stageB);
		tranPack.setNoteApprove(note);
		transactionPackageService.insertDataTable(tranPack);
	}
	
	
	
	@ResponseBody
	@RequestMapping(value="/getUpdateRec/{listA}/{kyDuyetA}/{code}/{stt}/{stage}/{place}")
	public ModelAndView getUpdateReciept(@PathVariable String listA, @RequestBody String yourArray, @PathVariable String kyDuyetA, @PathVariable String stage,
			@PathVariable String code, @PathVariable Integer stt, @PathVariable String place,
			WebRequest request, HttpServletRequest httpRequest, ModelMap model) throws Exception {
		Boolean testD = false;
		Boolean testC = false;
		Boolean testK = false;
		ModelAndView mav = new ModelAndView("investigation-offer");
		//Lấy user
		HttpSession session = httpRequest.getSession();
		UserSession userSession = (UserSession) session.getAttribute("userSession");
		Users users = userService.findById(userSession.getUserId());
		//
		List<InvestigationOffer> listOffer = new ArrayList<InvestigationOffer>();
		if(!StringUtils.isEmpty(listA) && listA.contains(",")){
			InvestigationOffer offerRoot = new InvestigationOffer();
			JSONObject onb = new JSONObject(yourArray);
			String[] arrayA = listA.split(",");
			String matailieu = code + stt;
			String handoverId = (onb.has("codeListB") && !onb.isNull("codeListB")) ? onb.getString("codeListB") : "";
			String handoverIdNew = "";
			if(stage.equals("Y")){
				updateListBWithHandover(handoverId, userSession, arrayA, onb);	
				handoverIdNew = createListBWithHandover(users, listA, userSession, matailieu, place, onb, kyDuyetA, "CREATED_AGAIN_B");
			}
			for(int i = 0; i < arrayA.length; i++){
				//tạo danh sách B
				String stageB = (onb.has("key_" + arrayA[i]) && !onb.isNull("key_" + arrayA[i])) ? onb.getString("key_" + arrayA[i]) : "";
				if(stageB.equals("D")){
					testD = true;
				}
				if(stageB.equals("C")){
					testC = true;
				}
				if(stageB.equals("K")){
					testK = true;
				}
				if(stage.equals("Y")){
					saveTranPackage(handoverIdNew, arrayA[i], stageB, onb);
				}
			}
			int count = 0;
			int countPerson = 0;
			for(int i = 0; i < arrayA.length; i++){
				String stageB = (onb.has("key_" + arrayA[i]) && !onb.isNull("key_" + arrayA[i])) ? onb.getString("key_" + arrayA[i]) : "";
				if(testD){
					if(!stageB.equals("D"))
						continue;
				}else if(testC){
					if(!stageB.equals("C"))
						continue;
				}else if(testK){
					if(!stageB.equals("K"))
						continue;
				}else if(!testD && !testC && !testK){
					continue;
				}
				count++;
				NicTransaction nicTransaction = nicTransactionService.findById(arrayA[i]);			
				//tạo danh sách B
				//nicTransaction.setLeaderOfficerId(kyDuyetA);
				//nicTransaction.setNoteHandoverB((onb.has(arrayA[i]) && !onb.isNull(arrayA[i])) ? onb.getString(arrayA[i]) : "");
				nicTransactionService.saveOrUpdate(nicTransaction);
				//updateListB(handoverId, stageB, nicTransaction,arrayA[i])			
				List<NicUploadJob> nicJob = uploadJobService.findAllByTransactionId(arrayA[i]);
				if(nicJob != null && nicJob.size() > 0){
					NicRegistrationData regData = nicJob.get(0).getNicTransaction().getNicRegistrationData();
					InvestigationOffer offer = new InvestigationOffer();
					offer.setStt(count);
					offer.setSoHoSo(nicJob.get(0).getTransactionId());
					offer.setSoDanhSach(nicJob.get(0).getNicTransaction().getPackageId());
					offer.setHoTen(HelperClass.createFullName(regData.getSurnameFull(), regData.getMiddlenameFull(), regData.getFirstnameFull()));
					String dateDob = regData.getDateOfBirth() != null ? HelperClass.convertDateToString2(regData.getDateOfBirth()) : "";
					offer.setNgaySinh(HelperClass.loadDateOfBirth(dateDob, regData.getDefDateOfBirth()));
					//offer.setNgaySinh(regData.getDateOfBirth() != null ? HelperClass.convertDateToString2(regData.getDateOfBirth()) : "");
					offer.setSoCMND(nicJob.get(0).getNicTransaction().getNin());
					if(!StringUtils.isEmpty(regData.getGender())){
						offer.setGioiTinh(regData.getGender().equals("M") ? "Nam" : "Nữ");
					}else{
						offer.setGioiTinh("Không");
					}
					//offer.setNoiSinh(regData.getPlaceOfBirth());
					String noiSinh = codesService.getCodeValueDescByIdName("DISTRICT", regData.getPlaceOfBirth(), "");
					if(StringUtils.isNotEmpty(noiSinh)){
						offer.setNoiSinh(noiSinh);								
					}else{
						offer.setNoiSinh(regData.getPlaceOfBirth());	
					}
					String diaChi = regData.getAddressLine1();
					String tinhTP = codesService.getCodeValueDescByIdName(Constants.CODE_DISTRICT_ID, regData.getAddressLine5(), "");
					String quanHuyen = codesService.getCodeValueDescByIdName(Constants.CODE_TOWN_VILLAGE_ID, regData.getAddressLine4(), "");
					if(StringUtils.isNotEmpty(quanHuyen)){
						diaChi += ", " + quanHuyen;
					}
					if(StringUtils.isNotEmpty(tinhTP)){
						diaChi += ", " + tinhTP;						
					}
					offer.setSoBn(nicTransaction.getRecieptNo());
					offer.setDiaChi(diaChi);
					//offer.setSoHC("");
					offer.setSoHC(nicTransaction.getPrevPassportNo() != null ? nicTransaction.getPrevPassportNo() : "");
					//offer.setNoiDungDeXuat("");
					offer.setGhiChu("");
					offerRoot.setNgayHenTra(HelperClass.convertDateToString2(nicTransaction.getEstDateOfCollection()));
					//Ảnh mặt
					List<NicTransactionAttachment> photoList = nicTransactionAttachmentService.findNicTransactionAttachments(
							arrayA[i], NicTransactionAttachment.DOC_TYPE_PHOTO_CAPTURE, NicTransactionAttachment.DEFAULT_SERIAL_NO).getListModel();				
					if (CollectionUtils.isNotEmpty(photoList) && photoList.size() > 0) {
						String images = Base64.encodeBase64String(photoList.get(0).getDocData());						
						offer.setAnhMat(images);
					}else{						
						offer.setAnhMat("");
					}
					//offer.setNoiDungDeXuat(nicTransaction.getNoteHandoverB());
					listOffer.add(offer);
					offerRoot.setNoiTra(place);
					Users usersLD = null;//userService.findByUserId(nicJob.get(0).getNicTransaction().getLeaderOfficerId());
					if(usersLD != null){
						offerRoot.setLanhDao(usersLD.getUserName());
						offerRoot.setChucVu(usersLD.getPosition());
						SiteRepository sr = siteRepositoryDao.findBySiteId(usersLD.getSiteCode());
						offerRoot.setNoiXL(sr.getSiteName());
						
					}
					if(testD){
						offerRoot.setSoDeXuat(handoverIdNew);						
					}
				}
				if(StringUtils.isNotEmpty(nicTransaction.getInfoPerson())){
					JAXBContext jaxbContext = JAXBContext.newInstance(InfoPersons.class);
					Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
					StringReader reader = new StringReader(nicTransaction.getInfoPerson());
					InfoPersons persons = (InfoPersons) unmarshaller.unmarshal(reader);
					List<InfoPerson> listPerson = null;
					if(persons != null){
						listPerson = persons.getInfoPersons();
						if(listPerson != null && listPerson.size() > 0){
							countPerson += listPerson.size();
						}
					}								
				}
			}
			offerRoot.setKhuVuc(users.getSiteGroupCode());
			String[] ngayIn = HelperClass.convertDateToString2(Calendar.getInstance().getTime()).split("/");
			offerRoot.setNgayHienTai(ngayIn[0]);
			offerRoot.setThangHienTai(ngayIn[1]);
			offerRoot.setNamHienTai(ngayIn[2]);
			Users usersCB = userService.findByUserId(userSession.getUserId());
			offerRoot.setCanBo(usersCB.getUserName());
			//offerRoot.setCanBo(userSession.getUserName());
			offerRoot.setSoLuongHS(count);
			offerRoot.setSoNguoi(count + countPerson);
			if(testD){
				mav = new ModelAndView("investigation-offer");
			}else if(testC){
				mav = new ModelAndView("investigation-offer-c");
			}else if(testK){
				mav = new ModelAndView("investigation-offer-k");
			}
			mav.addObject("entityOffer", offerRoot);
			mav.addObject("listOffer", listOffer);
		}
		
		return mav;
	}
	
	@ResponseBody
	@RequestMapping(value="/getApproveLeader/{listB}/{code}/{stt}")
	public String getApproveLeader(@PathVariable String listB, @RequestBody String yourArray,
			@PathVariable String code, @PathVariable Integer stt,
			WebRequest request, HttpServletRequest httpRequest, ModelMap model) throws Throwable {	
		try {
			//Lấy user
			HttpSession session = httpRequest.getSession();
			UserSession userSession = (UserSession) session.getAttribute("userSession");
			Users users = userService.findById(userSession.getUserId());
			//Users users = userService.findById(userSession.getUserId());
			//		
			if(!StringUtils.isEmpty(listB) && listB.contains(",")){			
				JSONObject onb = new JSONObject(yourArray);				
				String[] arrayA = listB.split(",");
				String handoverId = (onb.has("codeListB") && !onb.isNull("codeListB")) ? onb.getString("codeListB") : "";
				approveListBWithHandover(handoverId, userSession);
				for(int i = 0; i < arrayA.length; i++){
					//tạo danh sách B
					NicTransaction nicTransaction = nicTransactionService.findById(arrayA[i]);
					String noteB = (onb.has(arrayA[i]) && !onb.isNull(arrayA[i])) ? onb.getString(arrayA[i]) : "";
					//nicTransaction.setNoteHandoverB(noteB);				
					String stageB = (onb.has("key_" + arrayA[i]) && !onb.isNull("key_" + arrayA[i])) ? onb.getString("key_" + arrayA[i]) : null;
					
					approveListB(handoverId, stageB, nicTransaction, userSession, noteB, users.getSiteCode());
					//end
				}
				
				if(StringUtils.isNotEmpty(code) && stt > 0){
					String type = code.substring(0, 2);
					String year_  = code.substring(2, 4);
					Integer year = Integer.parseInt(year_);
					String codeA = code.substring(4,7);
					//Kiểm tra số tài liệu có trong csdl chưa
					List<EppArchiveCode> update = archiveCodeService.findAllEppArchiveCode(type, year, codeA, stt);
					if(update != null && update.size() > 0){
						EppArchiveCode update_ = update.get(0);
						int oldAmount = update_.getCount();
						update_.setCount(oldAmount + arrayA.length); 
						Boolean result = archiveCodeService.saveOrUpdateData(update_).getModel();
						logger.info("==== result update archiveCode: " + code + "- stt: " + stt + "- result: " + result);
					}
					else
					{
						EppArchiveCode create = new EppArchiveCode();
						create.setClosed("N");
						create.setCount(arrayA.length);
						create.setDocType(type);
						create.setIncNo(stt);
						create.setnYear(year);
						create.setOfficeCode(codeA);
						Boolean result = archiveCodeService.saveOrUpdateData(create).getModel();
						logger.info("==== result create archiveCode: " + code + "- stt: " + stt + "- result: " + result);
					}
				}
			}
			return "Y";
			
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
		}
		return "N";
	}
	
	
	void createListB(NicTransaction nicTransaction, String handoverId, String arrayA, String stageB) throws Exception{
		//tạo danh sách B
		nicTransactionService.saveOrUpdate(nicTransaction);
		NicTransactionPackage tranPack = new NicTransactionPackage();
		tranPack.setPackageId(handoverId);
		tranPack.setTransactionId(arrayA);
		tranPack.setTypeList(8);
		tranPack.setStage(stageB);
		transactionPackageService.insertDataTable(tranPack);
	}
	
	
	void updateListB(String handoverId, String stageB, NicTransaction nicTransaction, String arrayB) throws Exception{
		// update danh sách B
		nicTransactionService.saveOrUpdate(nicTransaction);
		// NicTransactionPackage tranPack =
		// transactionPackageService.getListPackageByPackageIdAndTranID(handoverId,
		// nicTransaction.getTransactionId());
		// tranPack.setStage(stageB);
		// transactionPackageService.insertDataTable(tranPack);
		NicTransactionPackage tranPack = new NicTransactionPackage();
		tranPack.setPackageId(handoverId);
		tranPack.setTransactionId(arrayB);
		tranPack.setTypeList(8);
		tranPack.setStage(stageB);
		transactionPackageService.insertDataTable(tranPack);
	}
	
	void approveListB(String handoverId, String stageB, NicTransaction nicTransaction, UserSession userSession, String noteB, String siteCode) throws Throwable{
		//update danh sách B
		Date startTime = new Date();
		if(stageB.equals("K")){
			//đề xuất từ chối
			List<NicUploadJob> jobB = uploadJobService.findAllByTransactionId(nicTransaction.getTransactionId());
			if(jobB != null && jobB.size() > 0){
				jobB.get(0).setInvestigationStatus(NicUploadJob.RECORD_STATUS_REJECTED);
				uploadJobService.saveOrUpdate(jobB.get(0));
				//this.reject_Approve(nicTransaction.getNoteHandoverB(), jobB.get(0).getWorkflowJobId() , userSession);
			}
		} else if(stageB.equals("C")){
			//đề xuất bổ sung
			List<NicUploadJob> jobB = uploadJobService.findAllByTransactionId(nicTransaction.getTransactionId());
			if(jobB != null && jobB.size() > 0){
				jobB.get(0).setInvestigationStatus(NicUploadJob.RECORD_STATUS_IN_PROGRESS);
				//jobB.get(0).setApproveFlag(null);
				//nicTransaction.setLeaderOfficerId(null);
				//nicTransaction.setNoteHandoverB(null);
				uploadJobService.saveOrUpdate(jobB.get(0));
				Date endTime = new Date();
				this.saveTransactionLog(nicTransaction.getTransactionId(), "HANDOVER", "APPROVE_C", startTime, endTime, "", "", null, siteCode, userSession.getSystemId(), userSession.getUserId());
			}
		} else {
			//đề xuất duyệt
			List<NicUploadJob> jobB = uploadJobService.findAllByTransactionId(nicTransaction.getTransactionId());
			if(jobB != null && jobB.size() > 0){
				jobB.get(0).setInvestigationStatus(NicUploadJob.RECORD_STATUS_APPROVE_PERSO);	
				jobB.get(0).setJobApproveStatus("1");
				uploadJobService.saveOrUpdate(jobB.get(0));
				Date endTime = new Date();
				this.saveTransactionLog(nicTransaction.getTransactionId(), "HANDOVER", "APPROVE_D", startTime, endTime, "", "", null, siteCode, userSession.getSystemId(), userSession.getUserId());
			}
		}
		nicTransactionService.saveOrUpdate(nicTransaction);
		NicTransactionPackage tranPack = transactionPackageService.getListPackageByPackageIdAndTranID(handoverId, nicTransaction.getTransactionId());
		tranPack.setStage(stageB);
		tranPack.setNoteApprove(noteB);
		transactionPackageService.insertDataTable(tranPack);
	}
	
	String createListBWithHandover(Users users, String listA, UserSession userSession, String codestt, String place, JSONObject onb, String kyDuyetA, String jobStatus) {
		String[] arr = listA.split(",");
		NicTransaction txn = nicTransactionService.findById(arr[0]);
		String site = users.getSiteCode();
		String sitePA = txn.getIssSiteCode();
		String handoverId = sitePA.substring(sitePA.length() - 2, sitePA.length());
		String countHandover = listHandoverService.getNUpdateCodeValueFromCodeId(Constants.PARA_NAME_HANDOVER);
		handoverId += countHandover;
		String year = HelperClass.convertDateToString2(Calendar.getInstance().getTime()).split("/")[2];
		handoverId += "/" + year.substring(year.length() - 2);
		try {
			SiteRepository sr = siteRepositoryDao.findBySiteId(site);
			if(sr != null){
				if(sr.getSiteGroups().getGroupId().equals("MN")){
					handoverId += "/P5"; 
				}else if(sr.getSiteGroups().getGroupId().equals("MB") || sr.getSiteGroups().getGroupId().equals("MT")){
					handoverId += "/P3"; 
				}
			}
		} catch (DaoException e) {
			e.printStackTrace();
		}
		String transId = "";
		int n = 0;
		for(int i = 0; i < arr.length; i++){
			String stageB = (onb.has("key_" + arr[i]) && !onb.isNull("key_" + arr[i])) ? onb.getString("key_" + arr[i]) : "";
			if(StringUtils.isEmpty(stageB) || stageB.equals("0"))
				continue;
			transId += arr[i] + ",";
			n++;
		}
		NicListHandover handover = new NicListHandover();
		handover.setId(new NicListHandoverId(handoverId, NicListHandover.TYPE_LIST_B));
		handover.setPackageName(Constants.PACKAGE_NAME_CODE);
		//handover.setTransactionId(transId);
		handover.setCreateBy(userSession.getUserId());
		handover.setCreateDate(Calendar.getInstance().getTime());
		//handover.setUserLeaderProcess(userSession.getUserId());
//		handover.setArchiveCode(codestt);
		handover.setHandoverStage(true);
		//handover.setResultPlace(place);
		handover.setCountTransaction(n);
		handover.setProcessUsers(kyDuyetA);
		listHandoverService.saveOrUpdate(handover);
		
		Date startTime = new Date();
		//Tạo log khi thêm mới Danh sách B
		for(int i = 0; i < arr.length; i++){
			this.saveTransactionLog(arr[i], "HANDOVER", jobStatus, startTime, new Date(), null, null, null, site, userSession.getWorkstationId(), userSession.getUserId());
		}
		
		return handoverId;
	}
	
	String updateListBWithHandover(String handoverID, UserSession userSession, String[] arr, JSONObject onb) {
		for(int i = 0; i < arr.length; i++){
			String stageB = (onb.has("key_" + arr[i]) && !onb.isNull("key_" + arr[i])) ? onb.getString("key_" + arr[i]) : "";			
			if(StringUtils.isEmpty(stageB) || stageB.equals("0")){
				NicTransaction txn = nicTransactionService.findById(arr[i]);
				//txn.setNoteHandoverB(null);
				//txn.setLeaderOfficerId(null);
				//txn.setOfferB(null);
				nicTransactionService.saveOrUpdate(txn);
			}
		}
		NicListHandover handover = listHandoverService.findById(handoverID);
		if(handover != null){
			handover.setUpdateBy(userSession.getUserId());
			handover.setUpdateDate(Calendar.getInstance().getTime());
			handover.setHandoverStage(false);
//			handover.setArchiveCode(null);
			listHandoverService.saveOrUpdate(handover);			
			return handover.getId().getPackageId();
		}
		return "";
	}
	
	String approveListBWithHandover(String handoverID, UserSession userSession) {
		NicListHandover handover = listHandoverService.findById(handoverID);
		if(handover != null){
			handover.setUpdateBy(userSession.getUserId());
			handover.setUpdateDate(Calendar.getInstance().getTime());
			handover.setHandoverStatus(1);
			listHandoverService.saveOrUpdate(handover);			
			return handover.getId().getPackageId();
		}
		return "";
	}
	
	@ResponseBody
	@RequestMapping(value="/detailTranB")
	public NicUploadJobDto detailTranB(@RequestParam String transactionId,
			WebRequest request, HttpServletRequest httpRequest, ModelMap model) throws Exception {
		NicUploadJobDto dto = new NicUploadJobDto();
		NicRegistrationData regData = regDataService.findById(transactionId);
		if(regData != null){
			dto.setTransactionId(transactionId);
			dto.setFullName(regData.getSurnameLine1());
			dto.setDob(HelperClass.convertDateToString2(regData.getDateOfBirth()));
			String address1 = regData.getAddressLine1();
			String ht = codesService.getCodeValueDescByIdName(RegistrationConstants.CODE_TOWN, regData.getAddressLine4(), "");
			String tp = codesService.getCodeValueDescByIdName(RegistrationConstants.CODE_DISTRICT, regData.getAddressLine5(), "");
			if(StringUtils.isNotEmpty(ht)){
				address1 += ", " + ht;
			}
			if(StringUtils.isNotEmpty(tp)){
				address1 += ", " + tp;
			}
			dto.setAddress1(address1);
		}
		NicTransaction txn = nicTransactionService.findById(transactionId);
		if(txn != null){
			dto.setNin(txn.getNin());
		}
		List<NicTransactionAttachment> listAtc = nicTransactionAttachmentService.getNicTransactionAttachments(transactionId, new String[] {"PH_CAP", "PH_CHIP"}, null);
		List<FacialImgDto> imgDtos = new ArrayList<FacialImgDto>();
		if(CollectionUtils.isNotEmpty(listAtc)){
			for(NicTransactionAttachment att : listAtc){
				FacialImgDto img = new FacialImgDto();
				img.setType(att.getDocType());
				img.setSerialNo(att.getSerialNo());
				img.setBase64(Base64.encodeBase64String(att.getDocData()));
				imgDtos.add(img);
			}
		}
		//List<NicTransactionAttachment> listFp = nicTransactionAttachmentService.getNicTransactionAttachments(transactionId, new String[] {"FP"}, null);
		List<FacialImgDto> fpDtos = new ArrayList<FacialImgDto>();
		
		for(int i = 1; i <= 10; i++){
			List<NicTransactionAttachment> fp = nicTransactionAttachmentService.findNicTransactionAttachments(transactionId, "FP", (i == 10 ? String.valueOf(i) : ("0" + i))).getListModel();
			if(CollectionUtils.isNotEmpty(fp)){
				FacialImgDto img = new FacialImgDto();
				img.setType(fp.get(0).getDocType());
				img.setSerialNo(fp.get(0).getSerialNo());
				img.setBase64(convertImageFormatWsqToJpg1(fp.get(0).getDocData()));
				fpDtos.add(img);
			}
		}
	
		dto.setListFinger(fpDtos);
		dto.setListImg(imgDtos);
		return dto;
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
	
	@ResponseBody
	@RequestMapping(value="/detailInvesB")
	public List<NicUploadJobDto> getInvestigationConfirmList(@RequestParam String packageNo, @RequestParam String styleB,
			WebRequest request, HttpServletRequest httpRequest, ModelMap model) throws NicUploadJobServiceException, JAXBException {
//		HttpSession session = httpRequest.getSession();
//		UserSession userSession = (UserSession) session.getAttribute("userSession");
//		int pageSize = Constants.DEFAULT_PAGE_MAX_SIZE;
//		Date creDate = null;
//		if (!StringUtils.isEmpty(createDate)){				
//			String pattern = "dd-MMM-yyyy";
//		    SimpleDateFormat format = new SimpleDateFormat(pattern);
//		    creDate = format.parse(createDate);
//		}
//		Date eDate = null;
//		if (!StringUtils.isEmpty(endDate)){				
//			String pattern = "dd-MMM-yyyy";
//		    SimpleDateFormat format = new SimpleDateFormat(pattern);
//		    eDate = format.parse(endDate);
//		}
		String creDate1 = null;
		String endDate1 = null;
//		if (!StringUtils.isEmpty(createDate)){
//			creDate1 = com.nec.asia.nic.comp.trans.utils.HelperClass.convertMonthNumberToMonthWord1(createDate);
//		}
//		if (!StringUtils.isEmpty(endDate)){
//			endDate1 = com.nec.asia.nic.comp.trans.utils.HelperClass.convertMonthNumberToMonthWord1(endDate);
//		}
		//model.addAttribute("maDanhSach", idPackage);
		Date creDate = !StringUtils.isEmpty(creDate1) ? com.nec.asia.nic.comp.trans.utils.HelperClass.convertStringToDate1(creDate1) : null;
		Date eDate = !StringUtils.isEmpty(endDate1) ? com.nec.asia.nic.comp.trans.utils.HelperClass.convertStringToDate1(endDate1) : null;
		List<NicUploadJobDto> pr = null;
		if(styleB.equals("0")){
			pr = uploadJobService.findAllForInvestigationPagination3(
					new String[] { NicUploadJob.RECORD_STATUS_COMPLETED }, null, null,
					new AssignmentFilterAll(creDate, eDate, null, packageNo));
			
		}else{
			pr = uploadJobService.findInvestigationJobListB(packageNo, null);
		}
//		pr.get(0).setPackageId(idPackage);
//		NicUploadJobDto[] ds1 = new NicUploadJobDto[pr.size()];
		if(pr != null && pr.size() > 0){
			for(NicUploadJobDto dto : pr){
				List<NicTransactionAttachment> photoList = nicTransactionAttachmentService.findNicTransactionAttachments(
						dto.getTransactionId(), NicTransactionAttachment.DOC_TYPE_PHOTO_CAPTURE, NicTransactionAttachment.DEFAULT_SERIAL_NO).getListModel();				
				if (CollectionUtils.isNotEmpty(photoList) && photoList.size() > 0) {
					String images = Base64.encodeBase64String(photoList.get(0).getDocData());
					dto.setPhotoStr("<img src=\'data:image/jpg;base64,"+ images +"' class=\"img-border doGetAJpgSafeVersion\" height=\"180\" width=\"135\" />");
				}else{
					//dto.setPhotoStr("<img src=\"<c:url value='/resources/images/No_Image.jpg' />\" class=\"img-border\" height=\"320\" width=\"240\" title=\"Hit Candidate\" />");
					dto.setPhotoStr("<img class=\"img-border\" height=\"180\" width=\"135\" src=\"/eppcentral/resources/images/No_Image.jpg\">");
				}				
			}
			pr.get(0).setCount(1);
			NicTransaction txn = nicTransactionService.findById(pr.get(0).getTransactionId());
			if(StringUtils.isNotEmpty(txn.getInfoPerson())){
				JAXBContext jaxbContext = JAXBContext.newInstance(InfoPersons.class);
				Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
				StringReader reader = new StringReader(txn.getInfoPerson());
				InfoPersons persons = (InfoPersons) unmarshaller.unmarshal(reader);
				List<InfoPerson> listPerson = null;
				if(persons != null){
					listPerson = persons.getInfoPersons();
					if(listPerson != null && listPerson.size() > 0){
						pr.get(0).setCount(listPerson.size() + 1);
					}
				}			
			}

//			for(int i = 0; i < pr.size(); i++){
//				ds1[i] = pr.get(i);
//			}
			
		}
		//pr.get(0).setCount(pr.size());
		return pr;
	}
	
	@ResponseBody
	@RequestMapping(value="/detailInvesBAgain")
	public List<NicUploadJobDto> detailInvesBAgain(@RequestParam String packageNo,
			WebRequest request, HttpServletRequest httpRequest, ModelMap model) throws NicUploadJobServiceException, JAXBException {

		//String creDate1 = null;
		//String endDate1 = null;
		//Date creDate = !StringUtils.isEmpty(creDate1) ? com.nec.asia.nic.comp.trans.utils.HelperClass.convertStringToDate1(creDate1) : null;
		//Date eDate = !StringUtils.isEmpty(endDate1) ? com.nec.asia.nic.comp.trans.utils.HelperClass.convertStringToDate1(endDate1) : null;
		List<NicUploadJobDto> pr = null;
		pr = uploadJobService.findInvestigationJobListB(packageNo, null);
		//NicUploadJobDto[] ds1 = new NicUploadJobDto[pr.size()];
		if(pr != null && pr.size() > 0){
			for(NicUploadJobDto dto : pr){
				List<NicTransactionAttachment> photoList = nicTransactionAttachmentService.findNicTransactionAttachments(
						dto.getTransactionId(), NicTransactionAttachment.DOC_TYPE_PHOTO_CAPTURE, NicTransactionAttachment.DEFAULT_SERIAL_NO).getListModel();				
				if (CollectionUtils.isNotEmpty(photoList) && photoList.size() > 0) {
					String images = Base64.encodeBase64String(photoList.get(0).getDocData());
					dto.setPhotoStr("<img src=\'data:image/jpg;base64,"+ images +"' class=\"img-border doGetAJpgSafeVersion\" height=\"180\" width=\"135\" />");
				}else{
					dto.setPhotoStr("<img class=\"img-border\" height=\"180\" width=\"135\" src=\"/eppcentral/resources/images/No_Image.jpg\">");
				}				
			}
//			for(int i = 0; i < pr.size(); i++){
//				ds1[i] = pr.get(i);
//			}
			pr.get(0).setCount(1);
			NicTransaction txn = nicTransactionService.findById(pr.get(0).getTransactionId());
			if(StringUtils.isNotEmpty(txn.getInfoPerson())){
				JAXBContext jaxbContext = JAXBContext.newInstance(InfoPersons.class);
				Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
				StringReader reader = new StringReader(txn.getInfoPerson());
				InfoPersons persons = (InfoPersons) unmarshaller.unmarshal(reader);
				List<InfoPerson> listPerson = null;
				if(persons != null){
					listPerson = persons.getInfoPersons();
					if(listPerson != null && listPerson.size() > 0){
						pr.get(0).setCount(listPerson.size() + 1);
					}
				}			
			}
		}
		//pr.get(0).setCount(pr.size());
		return pr;
	}
	
	
	public ModelAndView getNoteApprove(
			@ModelAttribute(value = "formData") InvestigationAssignmentData investigationAssignmentData,
			WebRequest request, HttpServletRequest httpRequest, ModelMap model,
			int pageNo, List<String> listOfMessages, List<String> listOfErrors) {
		logger.info("getInvestigationConfirmList pageNo");
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
		Map<String, String> userKhuVuc = new LinkedHashMap<String, String>();
		Map<String, String> ricKhuVuc = new LinkedHashMap<String, String>();
		String archiveCode = "";
		investigationAssignmentData.cleanUpStrings();
		//Phúc lấy danh sách lãnh đạo ký theo khu vực + quyền
//		Users user = userService.findById(userSession.getUserId());
//		if(user != null){
//			String khuVuc = user.getSiteGroupCode();
//			SiteRepository siR = new SiteRepository();
//			try {
//				siR = siteRepositoryDao.findBySiteId(user.getSiteCode());
//			} catch (DaoException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//			if(StringUtils.isNotEmpty(siR.getSiteId())){
//				archiveCode = siR.getSiteGroups().getArchiveCode();
//			}
//			
//			List<Users> dsUser = userService.getListUserBySiteGroupAndRole(khuVuc, Constants.ROLE_ID_NIC_LANHDAOPHEDUYET);
//			Collections.sort(dsUser, new Comparator<Users>() {			
//				@Override
//				public int compare(Users o1, Users o2) {
//					// TODO Auto-generated method stub
//					return o1.getUserName().compareTo(o2.getUserName());
//				}
//
//			});
//			if(dsUser != null && dsUser.size() > 0){
//				for(Users users : dsUser){
//					userKhuVuc.put(users.getUserId(), users.getUserName());
//				}
//			}
//			//Phúc lấy danh sách trung tâm ric theo khu vực
//			List<SiteRepository> dsRepository = siteService.findAllByGroupId1(khuVuc);
//			if(dsRepository != null && dsRepository.size() > 0){
//				for(SiteRepository site : dsRepository){
//					ricKhuVuc.put(site.getSiteId(), site.getSiteName());
//				}
//			}
//		}
		
		PaginatedResult<NicUploadJobDto> pr = null;
		try {
//			int pageSize = Constants.PAGE_SIZE_DEFAULT;
//
//			Parameters parameter = parametersService.findById(new ParametersId(
//					Parameters.SCOPE_SYSTEM, Parameters.MAX_RECORDS_PER_PAGE));
//			if (parameter != null) {
//				String pageSizeDb = parameter.getParaShortValue();
//
//				if (StringUtils.isNotBlank(pageSizeDb)
//						&& StringUtils.isNumeric(pageSizeDb)) {
//					pageSize = Integer.parseInt(pageSizeDb);
//				}
//			}
			//pageSize = Constants.DEFAULT_PAGE_MAX_SIZE;

//			pr = uploadJobService.findAllForInvestigationPagination(
//							new String[] { NicUploadJob.RECORD_STATUS_COMPLETED },
//							userSession.getUserId(),
//							pageNo,
//							pageSize,
//							investigationAssignmentData.assignedOnly(),
//							new AssignmentFilterAll(investigationAssignmentData
//									.getSearchTransactionId(),
//									investigationAssignmentData.getPriority(),
//									investigationAssignmentData
//											.getTransactionType(), null, null,
//									investigationAssignmentData
//											.getRegSiteCode(),
//									investigationAssignmentData
//											.getPassportType(),
//									investigationAssignmentData
//											.getTypeInvestigation(), investigationAssignmentData.getPackageCode()));
			pr = new PaginatedResult<>(0, 1, new ArrayList<NicUploadJobDto>());
			List<NicUploadJobDto> list = new ArrayList<NicUploadJobDto>();

			if (pr != null) {
				list = pr.getRows();
				if (list != null) {
//					for (NicUploadJobDto record : list) {
//						String transactionId = record.getTransactionId();
//						if (transactionId != null) {
//							NicTransaction nicTransaction = nicTransactionService
//									.findById(transactionId);
//							if (nicTransaction != null) {
//								record.setPeriodValidate(nicTransaction.getValidityPeriod());
//								record.setDateOfApplication(nicTransaction
//										.getDateOfApplication());
//								record.setEstDateOfCollection(nicTransaction
//										.getEstDateOfCollection());
//								record.setFullName(nicTransaction
//										.getNicRegistrationData()
//										.getSurnameLine1());
//								record.setPassportType(nicTransaction
//										.getPassportType());
//								record.setRegSiteCode(nicTransaction
//										.getRegSiteCode());
//								{
//									try {
//										CodeValues codeValue = codesService.getCodeValueByIdName(Codes.PRIORITY,nicTransaction.getPriority().toString());
//										if (codeValue != null
//												&& codeValue.getCodeValueDesc() != null) {
//											record.setPriorityString(codeValue
//													.getCodeValueDesc());
//										} else {
//											record.setPriorityString(nicTransaction
//													.getPriority() == null ? null
//													: nicTransaction
//															.getPriority()
//															.toString());
//										}
//									} catch (Exception e) {
//										record.setPriorityString(nicTransaction
//												.getPriority() == null ? null
//												: nicTransaction.getPriority()
//														.toString());
//									}
//								}
//							}
//						}
//					}
				} else {
					request.setAttribute("jobDetailsErrorMsg", "Không tìm thấy dữ liệu",
							0);
				}
				
				String matailieu = "";
				//Lấy mã tài liệu theo cú pháp
				if(StringUtils.isNotEmpty(archiveCode)){
					Date date = new Date(); // your date
					// Choose time zone in which you want to interpret your Date
					Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("Europe/Paris"));
					cal.setTime(date);
					String year = ("" + cal.get(Calendar.YEAR)).substring(Math.max(("" + cal.get(Calendar.YEAR)).length() - 2, 0));
					
					matailieu = "HC" + year + archiveCode;
				}
				model.addAttribute("matailieu", matailieu);
				
				Map searchResultMap = new HashMap();
				searchResultMap.put("totalRecords", pr.getTotal());
				//phúc edit			

				model.addAttribute("chiTietDS", pr.getRows());
				model.addAttribute("dsHoSo", pr.getRows());
				model.addAttribute("usersKhuVuc", userKhuVuc);
				model.addAttribute("siteKhuVuc", ricKhuVuc);
				//end	

				model.addAttribute("fnSelected",
						Constants.HEADING_NIC_INVESTIGATION_CONFIRM);
				ModelAndView modelAndView = new ModelAndView("investigation.note.approve",searchResultMap);
				investigationAssignmentData.cleanUpForNextPage();
				modelAndView.addObject("formData", investigationAssignmentData);
				return modelAndView;
			} 
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public ModelAndView getInvestigationConfirmList(
			@ModelAttribute(value = "formData") InvestigationAssignmentData investigationAssignmentData,
			WebRequest request, HttpServletRequest httpRequest, ModelMap model,
			int pageNo, List<String> listOfMessages, List<String> listOfErrors) {
		logger.info("getInvestigationConfirmList pageNo");
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
		Map<String, String> userKhuVuc = new LinkedHashMap<String, String>();
		Map<String, String> ricKhuVuc = new LinkedHashMap<String, String>();
		String archiveCode = "";
		investigationAssignmentData.cleanUpStrings();
		//Phúc lấy danh sách lãnh đạo ký theo khu vực + quyền
		Users user = userService.findById(userSession.getUserId());
		if(user != null){
			String khuVuc = user.getSiteGroupCode();
			SiteRepository siR = new SiteRepository();
			try {
				siR = siteRepositoryDao.findBySiteId(user.getSiteCode());
			} catch (DaoException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if(StringUtils.isNotEmpty(siR.getSiteId())){
				archiveCode = siR.getSiteGroups().getArchiveCode();
			}
			
			List<Users> dsUser = userService.getListUserBySiteGroupAndRole(khuVuc, Constants.ROLE_ID_NIC_LANHDAOPHEDUYET);
			Collections.sort(dsUser, new Comparator<Users>() {			
				@Override
				public int compare(Users o1, Users o2) {
					// TODO Auto-generated method stub
					return o1.getUserName().compareTo(o2.getUserName());
				}

			});
			if(dsUser != null && dsUser.size() > 0){
				for(Users users : dsUser){
					userKhuVuc.put(users.getUserId(), users.getUserName());
				}
			}
			//Phúc lấy danh sách trung tâm ric theo khu vực
			List<SiteRepository> dsRepository = siteService.findAllByGroupId1(khuVuc);
			if(dsRepository != null && dsRepository.size() > 0){
				for(SiteRepository site : dsRepository){
					ricKhuVuc.put(site.getSiteId(), site.getSiteName());
				}
			}
		}
		
		PaginatedResult<NicUploadJobDto> pr = null;
		try {
			int pageSize = Constants.PAGE_SIZE_DEFAULT;

			Parameters parameter = parametersService.findById(new ParametersId(
					Parameters.SCOPE_SYSTEM, Parameters.MAX_RECORDS_PER_PAGE));
			if (parameter != null) {
				String pageSizeDb = parameter.getParaShortValue();

				if (StringUtils.isNotBlank(pageSizeDb)
						&& StringUtils.isNumeric(pageSizeDb)) {
					pageSize = Integer.parseInt(pageSizeDb);
				}
			}
			//pageSize = Constants.DEFAULT_PAGE_MAX_SIZE;

//			pr = uploadJobService.findAllForInvestigationPagination(
//							new String[] { NicUploadJob.RECORD_STATUS_COMPLETED },
//							userSession.getUserId(),
//							pageNo,
//							pageSize,
//							investigationAssignmentData.assignedOnly(),
//							new AssignmentFilterAll(investigationAssignmentData
//									.getSearchTransactionId(),
//									investigationAssignmentData.getPriority(),
//									investigationAssignmentData
//											.getTransactionType(), null, null,
//									investigationAssignmentData
//											.getRegSiteCode(),
//									investigationAssignmentData
//											.getPassportType(),
//									investigationAssignmentData
//											.getTypeInvestigation(), investigationAssignmentData.getPackageCode()));
			pr = new PaginatedResult<>(0, 1, new ArrayList<NicUploadJobDto>());
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
								record.setPeriodValidate(nicTransaction.getValidityPeriod());
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
										CodeValues codeValue = codesService.getCodeValueByIdName(Codes.PRIORITY,nicTransaction.getPriority().toString());
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
				
				String matailieu = "";
				//Lấy mã tài liệu theo cú pháp
				if(StringUtils.isNotEmpty(archiveCode)){
					Date date = new Date(); // your date
					// Choose time zone in which you want to interpret your Date
					Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("Europe/Paris"));
					cal.setTime(date);
					String year = ("" + cal.get(Calendar.YEAR)).substring(Math.max(("" + cal.get(Calendar.YEAR)).length() - 2, 0));
					
					matailieu = "HC" + year + archiveCode;
				}
				model.addAttribute("matailieu", matailieu);
				
				Map searchResultMap = new HashMap();
				searchResultMap.put("totalRecords", pr.getTotal());
				//phúc edit			

				model.addAttribute("chiTietDS", pr.getRows());
				model.addAttribute("dsHoSo", pr.getRows());
				model.addAttribute("usersKhuVuc", userKhuVuc);
				model.addAttribute("siteKhuVuc", ricKhuVuc);
				//end	

				model.addAttribute("fnSelected",
						Constants.HEADING_NIC_INVESTIGATION_CONFIRM);
				ModelAndView modelAndView = new ModelAndView("investigation.investigationConfirm.investigationConfirmList",searchResultMap);
				investigationAssignmentData.cleanUpForNextPage();
				modelAndView.addObject("formData", investigationAssignmentData);
				return modelAndView;
			} else {
				model.addAttribute("fnSelected",
						Constants.HEADING_NIC_INVESTIGATION_CONFIRM);
				ModelAndView modelAndView = new ModelAndView("investigation.investigationConfirm.investigationConfirmList", null);
				investigationAssignmentData.cleanUpForNextPage();
				modelAndView.addObject("formData", investigationAssignmentData);
				return modelAndView;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	
	public ModelAndView JobInvestigationApprove(
			@ModelAttribute(value = "formData") InvestigationAssignmentData investigationAssignmentData,
			WebRequest request, HttpServletRequest httpRequest, ModelMap model,
			int pageNo, List<String> listOfMessages, List<String> listOfErrors) {
		logger.info("getInvestigationConfirmList pageNo");
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
		Map<String, String> userKhuVuc = new LinkedHashMap<String, String>();
		Map<String, String> ricKhuVuc = new LinkedHashMap<String, String>();
		investigationAssignmentData.cleanUpStrings();
		//Phúc lấy danh sách lãnh đạo ký theo khu vực + quyền
		Users user = userService.findById(userSession.getUserId());
		if(user != null){
			String khuVuc = user.getSiteGroupCode();
			List<Users> dsUser = userService.getListUserBySiteGroupAndRole(khuVuc, Constants.ROLE_ID_NIC_LANHDAOPHEDUYET);
			Collections.sort(dsUser, new Comparator<Users>() {			
				@Override
				public int compare(Users o1, Users o2) {
					// TODO Auto-generated method stub
					return o1.getUserName().compareTo(o2.getUserName());
				}

			});
			if(dsUser != null && dsUser.size() > 0){
				for(Users users : dsUser){
					userKhuVuc.put(users.getUserId(), users.getUserName());
				}
			}
			//Phúc lấy danh sách trung tâm ric theo khu vực
			List<SiteRepository> dsRepository = siteService.findAllByGroupId1(khuVuc);
			if(dsRepository != null && dsRepository.size() > 0){
				for(SiteRepository site : dsRepository){
					ricKhuVuc.put(site.getSiteId(), site.getSiteName());					
				}
			}
		}
		
		PaginatedResult<NicUploadJobDto> pr = null;
		try {
			int pageSize = Constants.PAGE_SIZE_DEFAULT;

			Parameters parameter = parametersService.findById(new ParametersId(
					Parameters.SCOPE_SYSTEM, Parameters.MAX_RECORDS_PER_PAGE));
			if (parameter != null) {
				String pageSizeDb = parameter.getParaShortValue();

				if (StringUtils.isNotBlank(pageSizeDb)
						&& StringUtils.isNumeric(pageSizeDb)) {
					pageSize = Integer.parseInt(pageSizeDb);
				}
			}
			//pageSize = Constants.DEFAULT_PAGE_MAX_SIZE;

//			pr = uploadJobService.findAllForInvestigationPagination(
//							new String[] { NicUploadJob.RECORD_STATUS_COMPLETED },
//							userSession.getUserId(),
//							pageNo,
//							pageSize,
//							investigationAssignmentData.assignedOnly(),
//							new AssignmentFilterAll(investigationAssignmentData
//									.getSearchTransactionId(),
//									investigationAssignmentData.getPriority(),
//									investigationAssignmentData
//											.getTransactionType(), null, null,
//									investigationAssignmentData
//											.getRegSiteCode(),
//									investigationAssignmentData
//											.getPassportType(),
//									investigationAssignmentData
//											.getTypeInvestigation(), investigationAssignmentData.getPackageCode()));
			pr = new PaginatedResult<>(0, 1, new ArrayList<NicUploadJobDto>());
			List<NicUploadJobDto> list = new ArrayList<NicUploadJobDto>();

			if (pr != null) {
				list = pr.getRows();
				if (list != null) {
//					for (NicUploadJobDto record : list) {
//						String transactionId = record.getTransactionId();
//						if (transactionId != null) {
//							NicTransaction nicTransaction = nicTransactionService
//									.findById(transactionId);
//							if (nicTransaction != null) {
//								record.setPeriodValidate(nicTransaction.getValidityPeriod());
//								record.setDateOfApplication(nicTransaction
//										.getDateOfApplication());
//								record.setEstDateOfCollection(nicTransaction
//										.getEstDateOfCollection());
//								record.setFullName(nicTransaction
//										.getNicRegistrationData()
//										.getSurnameLine1());
//								record.setPassportType(nicTransaction
//										.getPassportType());
//								record.setRegSiteCode(nicTransaction
//										.getRegSiteCode());
//								{
//									try {
//										CodeValues codeValue = codesService.getCodeValueByIdName(Codes.PRIORITY,nicTransaction.getPriority().toString());
//										if (codeValue != null
//												&& codeValue.getCodeValueDesc() != null) {
//											record.setPriorityString(codeValue
//													.getCodeValueDesc());
//										} else {
//											record.setPriorityString(nicTransaction
//													.getPriority() == null ? null
//													: nicTransaction
//															.getPriority()
//															.toString());
//										}
//									} catch (Exception e) {
//										record.setPriorityString(nicTransaction
//												.getPriority() == null ? null
//												: nicTransaction.getPriority()
//														.toString());
//									}
//								}
//							}
//						}
//
//					}
				} else {
					request.setAttribute("jobDetailsErrorMsg", "Không tìm thấy dữ liệu",
							0);
				}
				
				Map searchResultMap = new HashMap();
				searchResultMap.put("totalRecords", pr.getTotal());
				//phúc edit			

				model.addAttribute("chiTietDS", pr.getRows());
				model.addAttribute("dsHoSo", pr.getRows());
				model.addAttribute("usersKhuVuc", userKhuVuc);
				model.addAttribute("siteKhuVuc", ricKhuVuc);
				//end	

				model.addAttribute("fnSelected",
						Constants.HEADING_NIC_INVESTIGATION_CONFIRM);
				ModelAndView modelAndView = new ModelAndView("investigation.cfapprove",searchResultMap);
				investigationAssignmentData.cleanUpForNextPage();
				modelAndView.addObject("formData", investigationAssignmentData);
				return modelAndView;
			} else {
				model.addAttribute("fnSelected",
						Constants.HEADING_NIC_INVESTIGATION_CONFIRM);
				ModelAndView modelAndView = new ModelAndView("investigation.cfapprove", null);
				investigationAssignmentData.cleanUpForNextPage();
				modelAndView.addObject("formData", investigationAssignmentData);
				return modelAndView;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	
	public ModelAndView getInvestigationConfirmAgain(
			@ModelAttribute(value = "formData") InvestigationAssignmentData investigationAssignmentData,
			WebRequest request, HttpServletRequest httpRequest, ModelMap model,
			int pageNo, List<String> listOfMessages, List<String> listOfErrors) {
		logger.info("getInvestigationConfirmList pageNo");
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
		Map<String, String> userKhuVuc = new HashMap<>();
		Map<String, String> ricKhuVuc = new HashMap<String, String>();
		investigationAssignmentData.cleanUpStrings();
		//Phúc lấy danh sách lãnh đạo ký theo khu vực + quyền
		Users user = userService.findById(userSession.getUserId());
		String archiveCode = "";
		if(user != null){
			String khuVuc = user.getSiteGroupCode();
			List<Users> dsUser = userService.getListUserBySiteGroupAndRole(khuVuc, Constants.ROLE_ID_NIC_LANHDAOPHEDUYET);
			if(dsUser != null && dsUser.size() > 0){
				for(Users users : dsUser){
					userKhuVuc.put(users.getUserId(), users.getUserName());
				}
			}
			SiteRepository siR = new SiteRepository();
			try {
				siR = siteRepositoryDao.findBySiteId(user.getSiteCode());
			} catch (DaoException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if(StringUtils.isNotEmpty(siR.getSiteId())){
				archiveCode = siR.getSiteGroups().getArchiveCode();
			}
			//Phúc lấy danh sách trung tâm ric theo khu vực
			List<SiteRepository> dsRepository = siteService.findAllByGroupId1(khuVuc);
			if(dsRepository != null && dsRepository.size() > 0){
				for(SiteRepository site : dsRepository){
					ricKhuVuc.put(site.getSiteId(), site.getSiteName());					
				}
			}
		}
		
		PaginatedResult<NicUploadJobDto> pr = null;
		try {
			int pageSize = Constants.PAGE_SIZE_DEFAULT;

			Parameters parameter = parametersService.findById(new ParametersId(
					Parameters.SCOPE_SYSTEM, Parameters.MAX_RECORDS_PER_PAGE));
			if (parameter != null) {
				String pageSizeDb = parameter.getParaShortValue();

				if (StringUtils.isNotBlank(pageSizeDb)
						&& StringUtils.isNumeric(pageSizeDb)) {
					pageSize = Integer.parseInt(pageSizeDb);
				}
			}
			//pageSize = Constants.DEFAULT_PAGE_MAX_SIZE;

//			pr = uploadJobService.findAllForInvestigationPagination(
//							new String[] { NicUploadJob.RECORD_STATUS_COMPLETED },
//							userSession.getUserId(),
//							pageNo,
//							pageSize,
//							investigationAssignmentData.assignedOnly(),
//							new AssignmentFilterAll(investigationAssignmentData
//									.getSearchTransactionId(),
//									investigationAssignmentData.getPriority(),
//									investigationAssignmentData
//											.getTransactionType(), null, null,
//									investigationAssignmentData
//											.getRegSiteCode(),
//									investigationAssignmentData
//											.getPassportType(),
//									investigationAssignmentData
//											.getTypeInvestigation(), investigationAssignmentData.getPackageCode()));
			pr = new PaginatedResult<>(0, 1, new ArrayList<NicUploadJobDto>());
			List<NicUploadJobDto> list = new ArrayList<NicUploadJobDto>();

			if (pr != null) {
				list = pr.getRows();
				if (list != null) {
//					for (NicUploadJobDto record : list) {
//						String transactionId = record.getTransactionId();
//						if (transactionId != null) {
//							NicTransaction nicTransaction = nicTransactionService
//									.findById(transactionId);
//							if (nicTransaction != null) {
//								record.setPeriodValidate(nicTransaction.getValidityPeriod());
//								record.setDateOfApplication(nicTransaction
//										.getDateOfApplication());
//								record.setEstDateOfCollection(nicTransaction
//										.getEstDateOfCollection());
//								record.setFullName(nicTransaction
//										.getNicRegistrationData()
//										.getSurnameLine1());
//								record.setPassportType(nicTransaction
//										.getPassportType());
//								record.setRegSiteCode(nicTransaction
//										.getRegSiteCode());
//								{
//									try {
//										CodeValues codeValue = codesService.getCodeValueByIdName(Codes.PRIORITY,nicTransaction.getPriority().toString());
//										if (codeValue != null
//												&& codeValue.getCodeValueDesc() != null) {
//											record.setPriorityString(codeValue
//													.getCodeValueDesc());
//										} else {
//											record.setPriorityString(nicTransaction
//													.getPriority() == null ? null
//													: nicTransaction
//															.getPriority()
//															.toString());
//										}
//									} catch (Exception e) {
//										record.setPriorityString(nicTransaction
//												.getPriority() == null ? null
//												: nicTransaction.getPriority()
//														.toString());
//									}
//								}
//							}
//						}
//
//					}
				} else {
					request.setAttribute("jobDetailsErrorMsg", "Không tìm thấy dữ liệu",
							0);
				}
				
				String matailieu = "";
				//Lấy mã tài liệu theo cú pháp
				if(StringUtils.isNotEmpty(archiveCode)){
					Date date = new Date(); // your date
					// Choose time zone in which you want to interpret your Date
					Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("Europe/Paris"));
					cal.setTime(date);
					String year = ("" + cal.get(Calendar.YEAR)).substring(Math.max(("" + cal.get(Calendar.YEAR)).length() - 2, 0));
					
					matailieu = "HC" + year + archiveCode;
				}
				model.addAttribute("matailieu", matailieu);
				
				
				Map searchResultMap = new HashMap();
				searchResultMap.put("totalRecords", pr.getTotal());
				//phúc edit			

				model.addAttribute("chiTietDS", pr.getRows());
				model.addAttribute("dsHoSo", pr.getRows());
				model.addAttribute("usersKhuVuc", userKhuVuc);
				model.addAttribute("siteKhuVuc", ricKhuVuc);
				//end	

				model.addAttribute("fnSelected",
						Constants.HEADING_NIC_INVESTIGATION_CONFIRM);
				ModelAndView modelAndView = new ModelAndView("investigation.invesagain",searchResultMap);
				investigationAssignmentData.cleanUpForNextPage();
				modelAndView.addObject("formData", investigationAssignmentData);
				return modelAndView;
			} else {
				model.addAttribute("fnSelected",
						Constants.HEADING_NIC_INVESTIGATION_CONFIRM);
				ModelAndView modelAndView = new ModelAndView("investigation.invesagain", null);
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
	public ModelAndView startinvestigationConfirmcompare(
			@PathVariable long jobId, HttpServletRequest httpRequest,
			Model model) throws Throwable {
		logger.info("NIC Start Investigation Confirm compare");

		return this.startinvestigationConfirmcompare(jobId, httpRequest, model,
				null);
	}
	
	@ResponseBody
	@RequestMapping(value = { "/getPositionSign/{signId}" })
	public NicUploadJobDto getPositionSign(
			@PathVariable String signId, HttpServletRequest httpRequest,
			Model model) throws Throwable {
		NicUploadJobDto dto = new NicUploadJobDto();
		Users users = userService.findByUserId(signId);
		if(users != null)
			dto.setRicName(users.getPosition());
		return dto;
	}
	
	@ResponseBody
	@RequestMapping(value = { "/getCountArchiveCode/{code}/{stt}" })
	public Integer getCountArchiveCode(
			@PathVariable String code, @PathVariable Integer stt, HttpServletRequest httpRequest,
			Model model) throws Throwable {
		try{
			if(StringUtils.isNotEmpty(code) && stt > 0)
			{
				String type = code.substring(0, 2);
				String year_  = code.substring(2, 4);
				Integer year = Integer.parseInt(year_);
				String codeA = code.substring(4);
				Integer count = archiveCodeService.amountArchiveCode(type, year, codeA, stt);
				return count;
			}
		}
		catch (Exception e) {
			// TODO: handle exception
			return -1;
		}
		return 0;
	}

	public ModelAndView startinvestigationConfirmcompare(long jobId,
			HttpServletRequest httpRequest, Model model,
			List<String> listOfMessages) throws Throwable {
		logger.info("NIC Start Investigation Confirm compare, listOfMessages");
		String periodValid = "";
		ModelAndView modelAndView = new ModelAndView(
				"investigation.investigationConfirm.startInvestigationConfirm.compare");

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
		if(nicTransaction.getNicRegistrationData().getDateOfBirth() != null){
			String dob = HelperClass.convertDateToString1(nicTransaction.getNicRegistrationData().getDateOfBirth());
			nicTransaction.setDateOfBirthDesc(dob);
					//nicTransaction.getNicRegistrationData().setDateOfBirth(HelperClass.convertStringToDate1(dob));			
		}
		if(nicTransaction.getNicRegistrationData().getCreateDatetime() != null){
			String crd = HelperClass.convertDateToString1(nicTransaction.getNicRegistrationData().getCreateDatetime());
			nicTransaction.setCreateDesc(crd);
					//nicTransaction.getNicRegistrationData().setCreateDatetime(HelperClass.convertStringToDate1(crd));			
		}
		if(nicTransaction.getNicRegistrationData().getReligion() != null){
			String religion = codesService.getCodeValueDescByIdName(RegistrationConstants.CODE_RELIGION_CODE_ID, nicTransaction.getNicRegistrationData().getReligion(), "");
			nicTransaction.setReligionDesc(religion);
		}
		if(nicTransaction.getNicRegistrationData().getNation()!= null){
			String nation = codesService.getCodeValueDescByIdName(RegistrationConstants.CODE_NATION_CODE_ID, nicTransaction.getNicRegistrationData().getNation(), "");
			nicTransaction.setNationDesc(nation);
		}
		modelAndView.addObject("photoStr", photoStr);
		modelAndView.addObject("nicData", nicTransaction);
		// end
		modelAndView.addObject("transID", mainTransactionId);
		modelAndView.addObject("jobsID", jobId);
		NicTransaction nic = nicTransactionService
				.getNicTransactionById(mainTransactionId).getModel();
		if (nic != null) {
			periodValid = nic.getValidityPeriod().toString();
		}
		modelAndView.addObject("periodValid", periodValid);
		List<String> messages = new ArrayList<String>();

		if (listOfMessages != null) {
			messages.addAll(listOfMessages);
		}

		if (CollectionUtils.isNotEmpty(messages)) {
			httpRequest.setAttribute("messages", messages);
		}
		
		//Kiểm tra có được gửi VERIFY không
		Boolean checkVer = true;
		try{
			NicWorkflowProcess verification = workflowProcessService.findWorkflowProcessByCriteria(NicWorkflowProcess.VERIFICATION).get(0);
			if (verification.getStatus() == NicWorkflowProcess.WORKFLOW_STATUS_OFF)
				checkVer = false;
		}
		catch(Exception e){}
		
		modelAndView.addObject("checkVer", checkVer);
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

		return this.getInvestigationConfirmList(investigationAssignmentData,
				request, httpRequest, model, 1, null, null);
	}

	// /Xử lý từ chối bản ghi
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

			messages.add("Đã từ chối bản ghi thành công.");
		} catch (JobNoLongerAssignedToOfficerException e) {
			logger.info("rejection failed");
			messages.add("Từ chố bản ghi thất bại. Công việc hiện tại không được phân công cho User này");
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

		httpRequest.setAttribute("messages", messages);
		return new ModelAndView(
				"forward:/servlet/investigationConfirm/investigationConfirmList");
	}
	
	// /Xử lý từ chối bản ghi
		
	public void reject_Approve(String remarks, Long jobId, UserSession userSession) throws Throwable {

		logger.info("reject_Approve");

		try {

			/*uploadJobService.rejectJob(
					remarks,
					jobId,
					userSession.getUserName(), userSession.getWorkstationId(),
					NicRejectionData.rejectReason_investigation,
					NicTransactionLog.TRANSACTION_STAGE_INVESTIGATION,
					NicTransactionLog.TRANSACTION_STATUS_NIC_REJECTED);*/
			
			uploadJobService.rejectJob(
					remarks,
					jobId,
					userSession.getUserName(), userSession.getWorkstationId(),
					NicRejectionData.rejectReason_investigation,
					"HANDOVER",
					"APPROVE_K");

			
		} catch (JobNoLongerAssignedToOfficerException e) {
			logger.info("rejection failed");
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@ResponseBody
	@RequestMapping(value="/detailInvesApprove")
	public List<NicUploadJobDto> detailInvesApprove(@RequestParam String packageNo,
			WebRequest request, HttpServletRequest httpRequest, ModelMap model) throws Exception {

		//String creDate1 = null;
		//String endDate1 = null;
		//Date creDate = !StringUtils.isEmpty(creDate1) ? com.nec.asia.nic.comp.trans.utils.HelperClass.convertStringToDate1(creDate1) : null;
		//Date eDate = !StringUtils.isEmpty(endDate1) ? com.nec.asia.nic.comp.trans.utils.HelperClass.convertStringToDate1(endDate1) : null;
		List<NicUploadJobDto> pr = null;
		pr = uploadJobService.findInvestigationJobApprove(packageNo, null);
		//NicUploadJobDto[] ds1 = new NicUploadJobDto[pr.size()];
		if(pr != null && pr.size() > 0){
			for(NicUploadJobDto dto : pr){
				List<NicTransactionAttachment> photoList = nicTransactionAttachmentService.findNicTransactionAttachments(
						dto.getTransactionId(), NicTransactionAttachment.DOC_TYPE_PHOTO_CAPTURE, NicTransactionAttachment.DEFAULT_SERIAL_NO).getListModel();				
				if (CollectionUtils.isNotEmpty(photoList) && photoList.size() > 0) {
					String images = Base64.encodeBase64String(photoList.get(0).getDocData());
					dto.setPhotoStr("<img src=\'data:image/jpg;base64,"+ images +"' class=\"img-border doGetAJpgSafeVersion\" height=\"180\" width=\"135\" />");
				}else{
					dto.setPhotoStr("<img class=\"img-border\" height=\"180\" width=\"135\" src=\"/eppcentral/resources/images/No_Image.jpg\">");
				}				
			}
//			for(int i = 0; i < pr.size(); i++){
//				ds1[i] = pr.get(i);
//			}
			
		}
		pr.get(0).setCount(pr.size());
		return pr;
	}

	// /Xử lý chấp nhận duyệt bản ghi
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

			uploadJobService.approveJobStatus_Confirmed(
					Long.valueOf(investigationHitData.getUploadJobId()),
					userSession.getUserName(), userSession.getWorkstationId(),
					NicTransactionLog.TRANSACTION_STAGE_INVESTIGATION,
					NicTransactionLog.TRANSACTION_STATUS_NIC_CONFIRMED,
					NicUploadJob.RECORD_STATUS_CONFIRMED);

			messages.add("Duyệt bản ghi thành công.");
		} catch (JobNoLongerAssignedToOfficerException e) {
			messages.add("Duyệt bản ghi thất bại. Công việc hiện tại không được phân công cho User này");
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

		httpRequest.setAttribute("messages", messages);
		return new ModelAndView(
				"forward:/servlet/investigationConfirm/investigationConfirmList");
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
			
	/*		do {

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

			} while (tokenA98 == "");
*/
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
				/* TODO: XỬ LÝ GỌI API CHUYỂN DỮ LIỆU SANG BỘ CÔNG AN */
				
				NicUploadJob nicUp = new NicUploadJob();
				nicUp = uploadJobService.findById(Long.valueOf(investigationHitData.getUploadJobId()));
				if(nicUp != null){
					//Cập nhật trạng thái đã gửi dữ liệu xác thực từ BCA
					nicUp.setValidateInfoBca(0);
					uploadJobService.saveOrUpdate(nicUp);
				}
				
				messages.add("Đã gửi bản ghi sang bộ công an kiểm duyệt thông tin.");
			}
			
		
		} catch (JobNoLongerAssignedToOfficerException e) {
			messages.add("Gửi thông tin sang bộ công an thất bại. Công việc hiện tại không được phân công cho User này");
		} catch (Exception e) {
			e.printStackTrace();
		}

		httpRequest.setAttribute("messages", messages);
		return new ModelAndView(
				"forward:/servlet/investigationConfirm/investigationConfirmList");
	}
	
	@ResponseBody
	@RequestMapping(value="/getPaymentSign/{transactionId}")
	public ModelAndView getPaymentSign(@PathVariable String transactionId,
			WebRequest request, HttpServletRequest httpRequest, ModelMap model) {
			ModelAndView mav = new ModelAndView("investigation-pay");
			List<InfoPaymentDto> listPayment = new ArrayList<InfoPaymentDto>();	
			//InfoPaymentDto phiChinh = new InfoPaymentDto();
			try {
				NicTransaction nicTransaction = nicTransactionService.findById(transactionId);
			 	//Lấy phí chính
				
				//-------------
				List<PaymentDef> listPP = paymentDefService.findPaymentDefByTransType(null, null, PaymentDef.TYPE_ID_PAYMENT_PP);
				//List<PaymentDef> listPC = paymentDefService.findPaymentDefByTransType(null, null, PaymentDef.TYPE_ID_PAYMENT_PC);
				List<NicTransactionPaymentDetail> listDetail = paymentDetailService.findListDetailPaymentByTransactionId(nicTransaction.getNicTransactionPayment().getPaymentId(), null, null, null);
			 	for(PaymentDef def : listPP) {
			 		InfoPaymentDto dto = new InfoPaymentDto();
			 		dto.setMaPhuPhi(def.getTransactionSubtype());
			 		dto.setTenPhuPhi(codesService.getCodeValueDescByIdName(def.getTransactionType(), def.getTransactionSubtype(), ""));
			 		dto.setSoTien(this.formatTienTeVN(def.getFeeAmount()));
			 		dto.setTrangThai("0");
			 		if(listDetail != null && listDetail.size() > 0){
			 			for(NicTransactionPaymentDetail detail : listDetail){
			 				if(def.getTransactionSubtype().equals(detail.getSubTypePayment()) && detail.isStatusFee() == true){
			 					dto.setTrangThai("1");
			 					break;
			 				}
			 			}
			 		}
			 		listPayment.add(dto);
			 	}
//			 	for(NicTransactionPaymentDetail detail : listDetail){
//			 		for(PaymentDef pc : listPC){
//			 			if(detail.getTypePayment().equals(pc.getTransactionType()) && detail.getSubTypePayment().equals(pc.getTransactionSubtype())){
//			 				phiChinh.setMaPhiChinh(detail.getSubTypePayment());
//			 				phiChinh.setTenPhiChinh("");
//			 				phiChinh.setTienPhiChinh(this.formatTienTeVN(detail.getPaymentAmount()));
//			 				break;
//			 			}
//			 		}
//			 	}
			 	//model.addAttribute("dsMaPC", listPC);	
			} catch (Exception e) {
				e.printStackTrace();
			}
		//model.addAttribute("pc_HienTai", phiChinh);	
		model.addAttribute("tranID", transactionId);	
		mav.addObject("PhiBoSung", listPayment);
		return mav;
	}
	
	public String formatTienTeVN(Object object){
		String amoutTo = "";
		try {
			Locale localeVN = new Locale("vi", "VN");
			NumberFormat currencyVN = NumberFormat.getCurrencyInstance(localeVN);
			amoutTo = currencyVN.format(object);
			if(amoutTo.contains("đ")){
				amoutTo = amoutTo.replace("đ", "VNĐ");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return amoutTo;
	}
	
	@ResponseBody
	@RequestMapping(value="/savePaymentById/{transactionId}")
	public String savePaymentById(@PathVariable String transactionId, @RequestBody String payArray,
			WebRequest request, HttpServletRequest httpRequest, ModelMap model) {		
		HttpSession session = httpRequest.getSession();
		UserSession userSession = (UserSession) session.getAttribute("userSession");
		JSONObject onb = new JSONObject(payArray);
		try {
			NicTransaction nicTransaction = nicTransactionService.findById(transactionId);
		 	List<PaymentDef> listPP = paymentDefService.findPaymentDefByTransType(null, null, PaymentDef.TYPE_ID_PAYMENT_PP);
		 	//List<NicTransactionPaymentDetail> listDetail = paymentDetailService.findListDetailPaymentByTransactionId(transactionId, null, null, null);
		 	for(PaymentDef def : listPP) {
		 		String subtype = (onb.has(def.getTransactionSubtype()) && !onb.isNull(def.getTransactionSubtype())) ? onb.getString(def.getTransactionSubtype()) : "";
		 		if(nicTransaction != null && nicTransaction.getNicTransactionPayment() != null){
		 			NicTransactionPaymentDetail detail = new NicTransactionPaymentDetail();
		 			detail.setPaymentId(nicTransaction.getNicTransactionPayment().getPaymentId());
		 			detail.setTypePayment(def.getTransactionType());
		 			detail.setSubTypePayment(def.getTransactionSubtype());
		 			detail.setStatusFee(subtype.equals("T") ? true : false);
		 			detail.setPaymentAmount(def.getFeeAmount());
		 			detail.setCreateDate(Calendar.getInstance().getTime());
		 			detail.setCreateBy(userSession.getUserId());
		 			detail.setNote(NicTransactionPaymentDetail.NODE_PAYMENT_DESC);
		 			paymentDetailService.saveOrUpdatePaymentDetail(detail);
		 		}
		 	}
//		 	String maPC = (onb.has("JsonPC")) && !onb.isNull("JsonPC") ? onb.getString("JsonPC") : "";
//		 	String tienThayDoi = (onb.has("JsonSum")) && !onb.isNull("JsonSum") ? onb.getString("JsonSum") : "";
//		 	String maBanDau = (onb.has("JsoBanDau")) && !onb.isNull("JsoBanDau") ? onb.getString("JsoBanDau") : "";
//		 	List<NicTransactionPaymentDetail> listDetail = paymentDetailService.findListDetailPaymentByTransactionId(nicTransaction.getNicTransactionPayment().getPaymentId(), null, maBanDau, null);
//		 	if(StringUtils.isNotEmpty(maPC) && listDetail.size() > 0){
//		 		listDetail.get(0).setSubTypePayment(maPC);
//		 		listDetail.get(0).setPaymentAmount(Double.parseDouble(tienThayDoi));
//		 		listDetail.get(0).setUpdateDate(Calendar.getInstance().getTime());
//		 		listDetail.get(0).setUpdateBy(userSession.getUserId());
//		 		listDetail.get(0).setNote(NicTransactionPaymentDetail.NODE_PAYMENT_DESC_PC);
//		 		paymentDetailService.saveOrUpdatePaymentDetail(listDetail.get(0));
//		 	}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "string";
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

	/*// /====DÙNG TẠM CONTROLLER ĐỂ KHỞI TẠO DANH SÁCH BÀN GIAO
	@RequestMapping(value = "/listHandover")
	public ModelAndView getHandoverList(
			@ModelAttribute(value = "formData") InvestigationAssignmentData investigationAssignmentData,
			WebRequest request, HttpServletRequest httpRequest, ModelMap model) {
		logger.info("getHandoverList");

		int pageNo = 1;

		String tableId = "row";
		String pageNumber = request.getParameter((new ParamEncoder(tableId)
				.encodeParameterName(TableTagParameters.PARAMETER_PAGE)));
		if (StringUtils.isNotBlank(pageNumber)
				&& StringUtils.isNumeric(pageNumber)) {
			pageNo = Integer.parseInt(pageNumber);
		}

		return this.getHandoverList(investigationAssignmentData, request,
				httpRequest, model, pageNo, null, null);
	}

	public ModelAndView getHandoverList(
			@ModelAttribute(value = "formData") InvestigationAssignmentData investigationAssignmentData,
			WebRequest request, HttpServletRequest httpRequest, ModelMap model,
			int pageNo, List<String> listOfMessages, List<String> listOfErrors) {
		logger.info("getHandoverList pageNo");
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

		PaginatedResult<NicListHandover> pr = null;
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

			pr = listHandoverService.findAllHandoverList(pageNo, pageSize);

			List<NicListHandover> list = new ArrayList<NicListHandover>();

			if (pr != null) {
				list = pr.getRows();
				Map searchResultMap = new HashMap();
				searchResultMap.put("totalRecords", pr.getTotal());
				searchResultMap.put("pageSize", pageSize);
				model.addAttribute("jobList", list);
				model.addAttribute("fnSelected",
						Constants.HEADING_NIC_LIST_HANDOVER);
				ModelAndView modelAndView = new ModelAndView(
						"listHandover.list", searchResultMap);
				investigationAssignmentData.cleanUpForNextPage();
				modelAndView.addObject("formData", investigationAssignmentData);
				return modelAndView;
			} else {
				model.addAttribute("fnSelected",
						Constants.HEADING_NIC_LIST_HANDOVER);
				ModelAndView modelAndView = new ModelAndView(
						"listHandover.list", null);
				investigationAssignmentData.cleanUpForNextPage();
				modelAndView.addObject("formData", investigationAssignmentData);
				return modelAndView;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@RequestMapping(value = { "/startDetailHandover/{packId}" })
	public ModelAndView startDetailHandovercompare(@PathVariable String packId,
			HttpServletRequest httpRequest, Model model) throws Throwable {
		logger.info("NIC Start DetailHandover compare");

		return this
				.startDetailHandovercompare(packId, httpRequest, model, null);
	}

	public ModelAndView startDetailHandovercompare(String packId,
			HttpServletRequest httpRequest, Model model,
			List<String> listOfMessages) throws Throwable {
		logger.info("NIC Start DetailHandover compare, listOfMessages");

		ModelAndView modelAndView = new ModelAndView("listHandover.compare");
		List<NicListHandoverDetail> list = new ArrayList<NicListHandoverDetail>();
		this.initializeModelAndViewForms(modelAndView);

		NicListHandover jobDetails = listHandoverService.findById(packId);
		String mainTransactionId = jobDetails.getTransactionId();
		if (!StringUtils.isBlank(jobDetails.getTransactionId())) {
			String[] arrayTrans = jobDetails.getTransactionId().split(",");
			if (arrayTrans.length > 0) {
				for (String t : arrayTrans) {
					NicTransaction nicTrans = nicTransactionService.findById(t);
					if (nicTrans != null) {
						NicListHandoverDetail detail = new NicListHandoverDetail();
						detail.setCreateDate(nicTrans.getNicRegistrationData()
								.getCreateDatetime());
						detail.setCreateBy(nicTrans.getNicRegistrationData()
								.getCreateBy());
						long jobId = 0;
						List<NicUploadJob> lstUpload = uploadJobService
								.findAllByTransactionId(nicTrans
										.getTransactionId());
						if (!lstUpload.isEmpty()) {
							for (NicUploadJob n : lstUpload) {
								jobId = n.getWorkflowJobId();
							}
						}
						detail.setJobId(jobId);
						detail.setPackageId(packId);
						detail.setTransactionId(nicTrans.getTransactionId());
						detail.setTypeListName(jobDetails.getTypeListName());
						list.add(detail);
					}
				}
			}
		}
		model.addAttribute("jobList", list);
		List<String> messages = new ArrayList<String>();

		if (listOfMessages != null) {
			messages.addAll(listOfMessages);
		}

		if (CollectionUtils.isNotEmpty(messages)) {
			httpRequest.setAttribute("messages", messages);
		}

		return modelAndView;
	}*/

	// Xử lý tạo danh sách bàn giao cho Xac minh so bo sang BCA / Lannh dao
	@RequestMapping(value = { "/createHandover" })
	public ModelAndView createHandover(
			@ModelAttribute(value = "formData") InvestigationAssignmentData investigationHitData,
			BindingResult result, WebRequest request,
			HttpServletRequest httpRequest, Model model,
			@RequestParam("selectedJobIds") String[] checkboxvalues)
			throws Throwable {
		//Lưu danh sách transactionId phù hợp
        List<String> listTranId = new ArrayList<String>();
		List<String> messages = new LinkedList<String>();
		//messages.add("Có lỗi khi tạo danh sách bàn giao.");
		logger.info("createHandover");
		String mess = "";
		try {
			HttpSession session = httpRequest.getSession();
			Date date = new Date();
			UserSession userSession = (UserSession) session
					.getAttribute("userSession");
			String listIdTrans = "";
			if (checkboxvalues != null) {
				for (String st : checkboxvalues) {
					listTranId.add(st);
					List<NicUploadJob> lstUpload = uploadJobService
							.findAllByTransactionId(st);
					if (investigationHitData.getAssignToId().equals("1")) {

						// Chuyển trạng thái bản ghi sang trạng thái chờ thông
						// tin từ BCA
						uploadJobService
								.approveJobStatus_Confirmed(
										Long.valueOf(lstUpload.get(0)
												.getWorkflowJobId()),
										userSession.getUserName(),
										userSession.getWorkstationId(),
										NicTransactionLog.TRANSACTION_STAGE_INVESTIGATION,
										NicTransactionLog.TRANSACTION_STATUS_NIC_PENING_BCA,
										NicUploadJob.RECORD_STATUS_PENDING_BCA);
						mess = "BCA";
					} else {

						// Chuyển danh sách sang cho lãnh đạo
						uploadJobService
								.approveJobStatus_Confirmed(
										Long.valueOf(lstUpload.get(0)
												.getWorkflowJobId()),
										userSession.getUserName(),
										userSession.getWorkstationId(),
										NicTransactionLog.TRANSACTION_STAGE_INVESTIGATION,
										NicTransactionLog.TRANSACTION_STATUS_NIC_CONFIRMED,
										NicUploadJob.RECORD_STATUS_CONFIRMED);
						mess = "Xét duyệt";
					}

					// Lưu id sang bảng danh sách bàn giao
					listIdTrans += st + ",";
				}

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
//					handover.setId(new NicListHandoverId(setPackageId(packId), typeList));
					//handover.setTransactionId(listIdTrans);
					handover.setPackageName(investigationHitData
							.getUnassignAllForUserUserId());
					/*handover.setTypeList(Integer.parseInt(investigationHitData
							.getAssignToId()));*/
					//handover.setDescription("");
					listHandoverService.createRecordHandover(handover);
					// Thêm dữ liệu danh sách vào bảng trung gian phúc thêm 10/5
					for(String tranId : listTranId){
						NicTransactionPackage tranPackage = new NicTransactionPackage();
						tranPackage.setPackageId(packId);
						tranPackage.setTransactionId(tranId);
						transactionPackageService.insertDataTable(tranPackage);
					}
					ModelAndView modelAndView = new ModelAndView(
					"investigation.investigationConfirm.success");
					model.addAttribute("code", packId);
					return modelAndView;
				}
			} else {
				messages.add("Chưa có bản ghi nào được chọn.");
				httpRequest.setAttribute("messages", messages);
				return new ModelAndView(
						"forward:/servlet/investigationConfirm/investigationConfirmList");
			}
			messages.add("Tạo danh sách bàn giao sang " + mess +" thành công.");
		} catch (JobNoLongerAssignedToOfficerException e) {
			messages.add("Tạo danh sách bàn giao sang " + mess +" thất bại. Công việc hiện tại không được phân công cho User này");
		} catch (Exception e) {
			e.printStackTrace();
		}

		investigationHitData = new InvestigationAssignmentData();
		httpRequest.setAttribute("messages", messages);
		return new ModelAndView(
				"forward:/servlet/investigationConfirm/investigationConfirmList");
	}

	/*// /====DÙNG TẠM CONTROLLER ĐỂ KHỞI TẠO DANH SÁCH ĐIỂM IN
	@RequestMapping(value = "/persoLocations")
	public ModelAndView getPersoLocations(
			@ModelAttribute(value = "formData") InvestigationAssignmentData investigationAssignmentData,
			WebRequest request, HttpServletRequest httpRequest, ModelMap model) {
		logger.info("getPersoLocations");

		int pageNo = 1;

		RequestBase rq = new RequestBase();
		rq.setPageSize(20);
		rq.setPageIndex(1);
		rq.setStatus(-1);
		try {
			List<PrintLocation> list_ = GetDataAllPrintLocation(rq);
			List<NicPersoLocations> listPerso = persoLocationsService.findAll();
			if (list_ != null) {
				if (listPerso == null) {
					for (PrintLocation pr : list_) {
						NicPersoLocations nicPerso_ = new NicPersoLocations();
						nicPerso_.setAddress(pr.getAddress());
						nicPerso_.setLastModifiedBy(pr.getLastModifiedBy());
						nicPerso_.setLastModifiedDate(pr.getLastModifiedDate());
						nicPerso_.setCreateDate(pr.getCreatedDate());
						nicPerso_.setCreateBy(pr.getCreatedBy());
						nicPerso_.setName(pr.getName());
						nicPerso_.setCode(pr.getCode());
						nicPerso_.setIdPerso(pr.getID());
						nicPerso_.setDescription(pr.getDescription());
						nicPerso_.setStatus(pr.getStatus());

						persoLocationsService
								.createRecordPersoLocations(nicPerso_);
					}
				} else {
					for (PrintLocation pr : list_) {
						Boolean check_ = false;
						for (NicPersoLocations pl : listPerso) {
							if (pl.getIdPerso() == pr.getID()) {
								pl.setAddress(pr.getAddress());
								pl.setLastModifiedBy(pr.getLastModifiedBy());
								pl.setLastModifiedDate(pr.getLastModifiedDate());
								pl.setCreateDate(pr.getCreatedDate());
								pl.setCreateBy(pr.getCreatedBy());
								pl.setName(pr.getName());
								pl.setCode(pr.getCode());
								pl.setIdPerso(pr.getID());
								pl.setDescription(pr.getDescription());
								pl.setStatus(pr.getStatus());

								persoLocationsService
										.createRecordPersoLocations(pl);
								check_ = true;
								break;
							}
						}
						if (!check_) {
							NicPersoLocations nicPerso_ = new NicPersoLocations();
							nicPerso_.setAddress(pr.getAddress());
							nicPerso_.setLastModifiedBy(pr.getLastModifiedBy());
							nicPerso_.setLastModifiedDate(pr
									.getLastModifiedDate());
							nicPerso_.setCreateDate(pr.getCreatedDate());
							nicPerso_.setCreateBy(pr.getCreatedBy());
							nicPerso_.setName(pr.getName());
							nicPerso_.setCode(pr.getCode());
							nicPerso_.setIdPerso(pr.getID());
							nicPerso_.setDescription(pr.getDescription());
							nicPerso_.setStatus(pr.getStatus());
							persoLocationsService
									.createRecordPersoLocations(nicPerso_);
						}
					}
				}
			}

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String tableId = "row";
		String pageNumber = request.getParameter((new ParamEncoder(tableId)
				.encodeParameterName(TableTagParameters.PARAMETER_PAGE)));
		if (StringUtils.isNotBlank(pageNumber)
				&& StringUtils.isNumeric(pageNumber)) {
			pageNo = Integer.parseInt(pageNumber);
		}

		return this.getPersoLocations(investigationAssignmentData, request,
				httpRequest, model, pageNo, null, null);
	}

	public ModelAndView getPersoLocations(
			@ModelAttribute(value = "formData") InvestigationAssignmentData investigationAssignmentData,
			WebRequest request, HttpServletRequest httpRequest, ModelMap model,
			int pageNo, List<String> listOfMessages, List<String> listOfErrors) {
		logger.info("getPersoLocations pageNo");
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

		PaginatedResult<NicPersoLocations> pr = null;
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

			pr = persoLocationsService.findAllPersoLocations(pageNo, pageSize);

			List<NicPersoLocations> list = new ArrayList<NicPersoLocations>();

			if (pr != null) {
				list = pr.getRows();
				Map searchResultMap = new HashMap();
				searchResultMap.put("totalRecords", pr.getTotal());
				searchResultMap.put("pageSize", pageSize);
				model.addAttribute("jobList", list);
				model.addAttribute("fnSelected",
						Constants.HEADING_NIC_PERSO_LOCATIONS);
				ModelAndView modelAndView = new ModelAndView(
						"persoLocations.list", searchResultMap);
				investigationAssignmentData.cleanUpForNextPage();
				modelAndView.addObject("formData", investigationAssignmentData);
				return modelAndView;
			} else {
				model.addAttribute("fnSelected",
						Constants.HEADING_NIC_PERSO_LOCATIONS);
				ModelAndView modelAndView = new ModelAndView(
						"persoLocations.list", null);
				investigationAssignmentData.cleanUpForNextPage();
				modelAndView.addObject("formData", investigationAssignmentData);
				return modelAndView;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@RequestMapping(value = { "/startDetailPersoLocations/{id}" })
	public ModelAndView startDetailPersoLocations(
			@ModelAttribute(value = "formData") InvestigationHitData investigationHitData,
			@PathVariable long id, HttpServletRequest httpRequest, Model model)
			throws Throwable {
		logger.info("NIC Start DetailHandover compare");

		return this.startDetailPersoLocations(id, httpRequest, model, null);
	}

	public ModelAndView startDetailPersoLocations(long id,
			HttpServletRequest httpRequest, Model model,
			List<String> listOfMessages) throws Throwable {
		logger.info("NIC Start DetailPersoLocations compare, listOfMessages");

		ModelAndView modelAndView = new ModelAndView("persoLocations.compare");
		this.initializeModelAndViewForms(modelAndView);

		List<SiteRepository> listSR = siteRepositoryDao.findAll();
		NicPersoLocations jobDetails = persoLocationsService.findById(id);
		Map<String, Boolean> listSite = new LinkedHashMap<String, Boolean>();

		if (listSR != null) {
			boolean contains = false;
			if (jobDetails != null) {
				String sites = jobDetails.getIssuePlace();
				if (!StringUtils.isEmpty(sites)) {

					String[] arraySite = sites.split(",");
					for (SiteRepository a : listSR) {
						contains = java.util.Arrays.asList(arraySite).contains(
								a.getSiteId());
						listSite.put(a.getSiteId(), contains);
					}
				} else {
					for (SiteRepository a : listSR) {
						listSite.put(a.getSiteId(), contains);
					}
				}
			} else {
				for (SiteRepository a : listSR) {
					listSite.put(a.getSiteId(), contains);
				}
			}
		}
		model.addAttribute("persoLocation", jobDetails.getName());
		model.addAttribute("jobList", listSite);
		model.addAttribute("idhidden", jobDetails.getId());
		List<String> messages = new ArrayList<String>();

		if (listOfMessages != null) {
			messages.addAll(listOfMessages);
		}

		if (CollectionUtils.isNotEmpty(messages)) {
			httpRequest.setAttribute("messages", messages);
		}

		return modelAndView;
	}

	@RequestMapping(value = { "/updatePersoLocations" })
	public ModelAndView updatePersoLocations(
			@ModelAttribute(value = "formData") InvestigationHitData investigationHitData,
			BindingResult result, WebRequest request,
			HttpServletRequest httpRequest, Model model,
			@RequestParam("chkSms") String[] checkboxvalues) throws Throwable {
		logger.info("updatePersoLocations");

		List<String> messages = new LinkedList<String>();
		try {
			HttpSession session = httpRequest.getSession();
			Date date = new Date();
			UserSession userSession = (UserSession) session
					.getAttribute("userSession");
			String listIdTrans = "";
			NicPersoLocations nicPerso = persoLocationsService.findById(Long
					.valueOf(investigationHitData.getUploadJobId()));
			if (checkboxvalues != null) {
				for (String st : checkboxvalues) {
					listIdTrans += st + ",";
					if (nicPerso != null) {
						nicPerso.setIssuePlace(listIdTrans);
						persoLocationsService.saveOrUpdate(nicPerso);
					}
				}
			} else {
				messages.add("Chưa có bản ghi nào được chọn.");
				httpRequest.setAttribute("messages", messages);
				return new ModelAndView(
						"forward:/servlet/investigationConfirm/persoLocations");
			}
			messages.add("Cập nhật điểm in thành công.");
		} catch (JobNoLongerAssignedToOfficerException e) {
			messages.add("Cập nhật điểm in không thành công. Công việc hiện tại không được phân công cho User này");
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

		investigationHitData = new InvestigationHitData();
		httpRequest.setAttribute("messages", messages);
		return new ModelAndView(
				"forward:/servlet/investigationConfirm/persoLocations");
	}

	// Gọi API từ PERSO lấy dữ liệu các điểm in
	public List<PrintLocation> GetDataAllPrintLocation(RequestBase urlParameters)
			throws JsonParseException, JsonMappingException, IOException {
		List<PrintLocation> listResult = new LinkedList<PrintLocation>();
		String urlP_ = "Keyword="
				+ (!StringUtils.isEmpty(urlParameters.getKeyword()) ? urlParameters
						.getKeyword() : "") + "&PageSize="
				+ urlParameters.getPageSize() + "&PageIndex="
				+ urlParameters.getPageIndex() + "&Status="
				+ urlParameters.getStatus();

		byte[] postData = urlP_.getBytes(StandardCharsets.UTF_8);
		int postDataLength = postData.length;
		URL url = new URL(
				"http://192.168.1.16:1988/master/getAllPrintLocationByParam");
		HttpURLConnection connection = (HttpURLConnection) url.openConnection();
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
			JSONObject onb = new JSONObject(st);
			BaseListResponse responseB = new BaseListResponse();
			responseB.setIsSuccess(onb.getBoolean("IsSuccess"));
			if (responseB.getIsSuccess()) {
				JSONArray arrayResp = onb.getJSONArray("Data");
				for (int i = 0; i < arrayResp.length(); i++) {

					JSONObject jk = new JSONObject();
					jk = arrayResp.getJSONObject(i);
					PrintLocation pl_ = new PrintLocation();
					pl_.setID(jk.getLong("ID"));

					DateFormat formatter = new SimpleDateFormat(
							"yyyy-MM-dd'T'HH:mm:ss.SSS");
					Date datec = null;
					try {
						datec = formatter.parse(jk.getString("CreatedDate"));
					} catch (JSONException | ParseException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					pl_.setCreatedDate(datec);

					DateFormat formatterm = new SimpleDateFormat(
							"yyyy-MM-dd'T'HH:mm:ss.SSS");
					Date datem = null;
					try {
						datem = formatterm.parse(jk
								.getString("LastModifiedDate"));
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (ParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					pl_.setLastModifiedDate(datem);
					pl_.setCreatedBy(jk.getLong("CreatedBy"));
					pl_.setLastModifiedBy(jk.getLong("LastModifiedBy"));
					pl_.setName(jk.getString("Name"));
					pl_.setCode(jk.getString("Code"));
					pl_.setAddress(jk.getString("Address"));
					pl_.setDescription(jk.getString("Description"));
					pl_.setStatus(jk.getInt("Status"));

					if (pl_ != null) {
						listResult.add(pl_);
					}
				}
				responseB.setData(listResult);
			}

		} else {
			// /Trả về log mã lỗi và thông tin lỗi

		}
		return listResult;
	}*/

	// /======DANH SÁCH IN THÀNH CÔNG========
	@RequestMapping(value = "/applyFilterSuccessPerso")
	public ModelAndView applyFilterSuccessPerso(
			@ModelAttribute(value = "formData") InvestigationAssignmentData investigationAssignmentData,
			WebRequest request, HttpServletRequest httpRequest, ModelMap model) {
		logger.info("applyFilter");

		return this.getSuccessPersoList(investigationAssignmentData,
				request, httpRequest, model, 1, null, null);
	}
	
	@RequestMapping(value = "/successPersoList")
	public ModelAndView getSuccessPersoList(
			@ModelAttribute(value = "formData") InvestigationAssignmentData investigationAssignmentData,
			WebRequest request, HttpServletRequest httpRequest, ModelMap model) {
		logger.info("getSuccessPersoList");

		int pageNo = 1;

		String tableId = "row";
		String pageNumber = request.getParameter((new ParamEncoder(tableId)
				.encodeParameterName(TableTagParameters.PARAMETER_PAGE)));
		if (StringUtils.isNotBlank(pageNumber)
				&& StringUtils.isNumeric(pageNumber)) {
			pageNo = Integer.parseInt(pageNumber);
		}
		pageNo = !StringUtils.isEmpty(request.getParameter("pageNo")) ? Integer.parseInt(request.getParameter("pageNo")) : 1;
		return this.getSuccessPersoList(investigationAssignmentData, request,
				httpRequest, model, pageNo, null, null);
	}

	public ModelAndView getSuccessPersoList(
			@ModelAttribute(value = "formData") InvestigationAssignmentData investigationAssignmentData,
			WebRequest request, HttpServletRequest httpRequest, ModelMap model,
			int pageNo, List<String> listOfMessages, List<String> listOfErrors) {
		logger.info("getSuccessPersoList pageNo");
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
			int pageSize = 10;

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

			pr = uploadJobService
					.findAllForInvestigationPagination(
							new String[] { NicUploadJob.RECORD_STATUS_APPROVE_PERSO },
							null,
							pageNo,
							pageSize,
							//investigationAssignmentData.assignedOnly(),
							null,
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
											.getTypeInvestigation()));

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
						Constants.HEADING_NIC_APPROVE_PERSO);
				ModelAndView modelAndView = new ModelAndView(
						"successPerso.list",
						searchResultMap);
				investigationAssignmentData.cleanUpForNextPage();
				modelAndView.addObject("formData", investigationAssignmentData);
				return modelAndView;
			} else {
				model.addAttribute("fnSelected",
						Constants.HEADING_NIC_APPROVE_PERSO);
				ModelAndView modelAndView = new ModelAndView(
						"successPerso.list",
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

	/*// /======DANH SÁCH CHỜ CÂP SỐ HỘ CHIẾU========
	@RequestMapping(value = "/pendingPassportNoList")
	public ModelAndView getPendingPassportNoList(
			@ModelAttribute(value = "formData") InvestigationAssignmentData investigationAssignmentData,
			WebRequest request, HttpServletRequest httpRequest, ModelMap model) {
		logger.info("getSuccessPersoList");

		int pageNo = 1;

		String tableId = "row";
		String pageNumber = request.getParameter((new ParamEncoder(tableId)
				.encodeParameterName(TableTagParameters.PARAMETER_PAGE)));
		if (StringUtils.isNotBlank(pageNumber)
				&& StringUtils.isNumeric(pageNumber)) {
			pageNo = Integer.parseInt(pageNumber);
		}

		return this.getPendingPassportNoList(investigationAssignmentData,
				request, httpRequest, model, pageNo, null, null);
	}

	public ModelAndView getPendingPassportNoList(
			@ModelAttribute(value = "formData") InvestigationAssignmentData investigationAssignmentData,
			WebRequest request, HttpServletRequest httpRequest, ModelMap model,
			int pageNo, List<String> listOfMessages, List<String> listOfErrors) {
		logger.info("getPendingPassportNoList pageNo");
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

			pr = uploadJobService
					.findPendingPassportNo(
							new String[] { NicUploadJob.RECORD_STATUS_APPROVE_PERSO },
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
											.getTypeInvestigation()));

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
						Constants.HEADING_NIC_PENDING_PASSPORTNO);
				ModelAndView modelAndView = new ModelAndView(
						"pendingPassportNo.list", searchResultMap);
				investigationAssignmentData.cleanUpForNextPage();
				modelAndView.addObject("formData", investigationAssignmentData);
				return modelAndView;
			} else {
				model.addAttribute("fnSelected",
						Constants.HEADING_NIC_PENDING_PASSPORTNO);
				ModelAndView modelAndView = new ModelAndView(
						"pendingPassportNo.list", null);
				investigationAssignmentData.cleanUpForNextPage();
				modelAndView.addObject("formData", investigationAssignmentData);
				return modelAndView;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	@RequestMapping(value = "/applyFilterPendingPassportNo")
	public ModelAndView applyFilterPendingPassportNo(
			@ModelAttribute(value = "formData") InvestigationAssignmentData investigationAssignmentData,
			WebRequest request, HttpServletRequest httpRequest, ModelMap model) {
		logger.info("applyFilter");

		return this.getPendingPassportNoList(investigationAssignmentData,
				request, httpRequest, model, 1, null, null);
	}
	
	public class ResponseReport{
		
		private String sobiennhanF;
		private String nguoinopF;
		private String namsinhF;
		private String cmndF;
		private String hentraF;
		
		public ResponseReport(){
			
		}
		
		public ResponseReport(String sobiennhanF, String nguoinopF, String namsinhF, String cmndF, String hentraF){
			this.sobiennhanF = sobiennhanF;
			this.nguoinopF = nguoinopF;
			this.namsinhF = namsinhF;
			this.cmndF = cmndF;
			this.hentraF = hentraF;
		}
		
		public String getSobiennhanF(){
			return this.sobiennhanF;
		}
		public void setSobiennhanF(String sobiennhanF){
			this.sobiennhanF = sobiennhanF;
		}
		
		public String getNguoinopF(){
			return this.nguoinopF;
		}
		public void setNguoinopF(String nguoinopF){
			this.nguoinopF = nguoinopF;
		}
		
		public String getNamsinhF(){
			return this.namsinhF;
		}
		public void setNamsinhF(String namsinhF){
			this.namsinhF = namsinhF;
		}
		
		public String getCmndF(){
			return this.cmndF;
		}
		public void setCmndF(String cmndF){
			this.cmndF = cmndF;
		}
		
		public String getHentraF(){
			return this.hentraF;
		}
		public void setHentraF(String hentraF){
			this.hentraF = hentraF;
		}
	}
	
	@ResponseBody
	@RequestMapping(value = "/showPdfResult")
	public void printPDF(WebRequest request, Model model, @RequestParam("packID") String packId , HttpServletResponse response) {
		try {
			String pachId_ = packId.split(",")[0];
			NicListHandover jobDetails = listHandoverService.findById(pachId_);
			if(jobDetails != null){
				Map<String, Object> parameterMap = new HashMap<String, Object>();
				parameterMap.put("titleP", "DANH SÁCH " + jobDetails.getTypeListName().toUpperCase());
				String dateP_ = "Ngày " + jobDetails.getCreateDate().getDate() + " tháng " + jobDetails.getCreateDate().getMonth() + " năm " + (jobDetails.getCreateDate().getYear() + 1900);
				parameterMap.put("dateP", dateP_);
				parameterMap.put("codeHandoverP", "Số: " + jobDetails.getPackageId());
				
				String[] arrayTrans = jobDetails.getTransactionId().split(",");
				parameterMap.put("totalP", String.valueOf(arrayTrans.length));
				List<ResponseReport> list = new ArrayList<ResponseReport>();
				if(arrayTrans.length > 0){
					
					for (String t : arrayTrans) {
						NicTransaction nicTrans = nicTransactionService.findById(t);
						if (nicTrans != null) {
							ResponseReport detail = new ResponseReport();
							detail.setSobiennhanF(t);
							detail.setNamsinhF(new SimpleDateFormat("MM-dd-yyyy").format(nicTrans.getNicRegistrationData()
									.getDateOfBirth()));
							detail.setCmndF(nicTrans.getNin());
							detail.setNguoinopF(jobDetails.getCreateBy());
							detail.setHentraF(new SimpleDateFormat("MM-dd-yyyy").format(nicTrans.getEstDateOfCollection()));
							list.add(detail);
						}
					}
					
					FastReportBuilder drb = new FastReportBuilder();
					drb.addColumn("Số biên nhận", "sobiennhanF", String.class.getName(), 30);
					drb.addColumn("Người nộp", "nguoinopF", String.class.getName(), 30);
					drb.addColumn("Năm sinh", "namsinhF", String.class.getName(), 30);
					drb.addColumn("CMND/CCCD", "cmndF", String.class.getName(), 30);
					drb.addColumn("Hẹn trả", "hentraF", String.class.getName(), 30);
					for (String entry : ricPymtRptDto.getSelColumns()) {
						drb.addColumn(entry, entry, String.class.getName(), 30);
					}
					drb.setTemplateFile("resources/report/templates/list_base.jrxml");
					drb.setUseFullPageWidth(true);
					DynamicReport dr = drb.build();
					//PaginatedResult<RICPymtRptDto> result = paymentService.getRicPymtRptRecordList(ricPymtRptDto, 1, 3, true);
					JRDataSource dataSource = new JRBeanCollectionDataSource(list);
					ByteArrayOutputStream os = new ByteArrayOutputStream();
					try {
						InputStream reportStream = this.getClass().getResourceAsStream("/report/templates/list_base.jrxml");
						JasperReport jasperReport = JasperCompileManager.compileReport(reportStream);
						JasperReportsUtils.renderAsPdf(jasperReport, parameterMap, dataSource, os);
						response.setContentType("application/pdf");
						response.getOutputStream().write(os.toByteArray());
					} catch (JRException e) {
						e.printStackTrace();
					} catch (Exception e) {
						e.printStackTrace();
					}
					
					 * InputStream reportStream = this.getClass().getResourceAsStream( "/RIC_ExceptionHandling_Report.jrxml").; JasperReport jasperReport = JasperCompileManager .compileReport(reportStream); 
					JasperPrint jasperPrint = DynamicJasperHelper.generateJasperPrint(dr, new ClassicLayoutManager(), dataSource, parameterMap);
					System.out.println("jasperPrint >>" + jasperPrint.getName());
					JRExporter exporter = new JRPdfExporter();
					ByteArrayOutputStream os = new ByteArrayOutputStream();
					exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
					exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, os);
					
					 * FileOutputStream fileOuputStream=null; fileOuputStream = new FileOutputStream("c:\\pdf\\report1.pdf"); 
					// fileOuputStream.write(os.toByteArray());
					response.setContentType("application/pdf");
					response.getOutputStream().write(os.toByteArray());
					// fileOuputStream.close();
					
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}*/
	
	private void saveTransactionLog(String transactionId, String transactionStage,String transactionStatus, Date startTime, Date endTime, String logInfo, String logData, Integer retryCount, String siteCode, String WkstnId, String OfficerId) {
		NicTransactionLog transactionLog = new NicTransactionLog();
		transactionLog.setRefId(transactionId);
		transactionLog.setLogCreateTime(new Date());
		transactionLog.setTransactionStage(transactionStage);//TRANSACTION_STATE_PERSONALIZATION);
		transactionLog.setTransactionStatus(transactionStatus);
		transactionLog.setSiteCode(siteCode); //get from 'CURRENT_SITE_CODE'
		transactionLog.setStartTime(startTime);
		transactionLog.setEndTime(endTime);
		transactionLog.setLogInfo(logInfo);
		transactionLog.setLogData(logData);
		//8-OCT-2013 (jp) - added hostname and system
		transactionLog.setWkstnId(WkstnId);
		transactionLog.setOfficerId(OfficerId);
		transactionLog.setRetryCount(retryCount);
		synchronized (NicTransactionLog.class) {
			nicTransactionLogService.save(transactionLog);
		}
	}
}
