/**
 * 
 */
package com.nec.asia.nic.listHandover.controller;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.InetAddress;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import ognl.InappropriateExpressionException;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.displaytag.tags.TableTagParameters;
import org.displaytag.util.ParamEncoder;
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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.ModelAndView;

import com.nec.asia.nic.comp.listHandover.domain.NicListHandover;
import com.nec.asia.nic.comp.listHandover.domain.NicListHandoverDetail;
import com.nec.asia.nic.comp.listHandover.service.ListHandoverService;
import com.nec.asia.nic.comp.trans.dao.DocumentDataDao;
import com.nec.asia.nic.comp.trans.domain.EppListHandoverDetail;
import com.nec.asia.nic.comp.trans.domain.NicTransaction;
import com.nec.asia.nic.comp.trans.domain.NicUploadJob;
import com.nec.asia.nic.comp.trans.dto.AssignmentFilterAll;
import com.nec.asia.nic.comp.trans.dto.InvestigationHitData;
import com.nec.asia.nic.comp.trans.service.EppListHandoverDetailService;
import com.nec.asia.nic.comp.trans.service.NicTransactionService;
import com.nec.asia.nic.comp.trans.service.NicUploadJobService;
import com.nec.asia.nic.framework.PaginatedResult;
import com.nec.asia.nic.framework.admin.code.domain.CodeValues;
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
import com.nec.asia.nic.framework.controller.AbstractController;
import com.nec.asia.nic.framework.report.service.AuditAccessLogService;
import com.nec.asia.nic.investigation.controller.InvestigationAssignmentData;
import com.nec.asia.nic.util.Constants;
import com.nec.asia.nic.util.pagingUtil;
import com.nec.asia.nic.utils.DateUtil;
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
@RequestMapping(value = "/listHandover")
public class ListHandoverController extends AbstractController {

	@Autowired
	private ParametersService parametersService;
	
	@Autowired
	private ListHandoverService listHandoverService;

	@Autowired
	private PaymentDefService paymentDefService;

	@Autowired
	private NicTransactionService nicTransactionService;
	
	@Autowired
	private NicUploadJobService uploadJobService;
	
	@Autowired
	private DocumentDataDao documentDataDao;
	
	@Autowired
	//@Qualifier(value="codesService")
	private CodesService codesService;
	
	//30 Dec 2013 (chris lai) : update with audit record
	@Autowired
	private AuditAccessLogService auditAccessLogService;
	
	@Autowired
	private EppListHandoverDetailService eppListHandoverDetailService;
	
	@Autowired
	@Qualifier("userService")
	private UserService userService;
	
	private static final Logger logger = LoggerFactory.getLogger(ListHandoverController.class);

	@ModelAttribute("userAssignment")
	public Map<String, String> userAssignment() {
		List<Users> codeList = userService.findAll();
		Map<String, String> userAssignment = new LinkedHashMap<String, String>();
		for (Users ricCode : codeList) {
			userAssignment.put(ricCode.getUserId(), ricCode.getUserId());
		}
		return userAssignment;
	}
	
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
		pageNo = !StringUtils.isEmpty(request.getParameter("pageNo")) ? Integer.parseInt(request.getParameter("pageNo")) : 1;
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
			Date creDate = null;
			if (!StringUtils.isEmpty(investigationAssignmentData.getCreateDate())){				
				String pattern = "dd-MMM-yyyy";
			    SimpleDateFormat format = new SimpleDateFormat(pattern);
			    creDate = format.parse(investigationAssignmentData.getCreateDate());
			}


			pr = listHandoverService.findAllHandoverList(pageNo, pageSize,
					new AssignmentFilterAll(creDate, investigationAssignmentData.getPackageCode(), investigationAssignmentData.getCreateBy(), investigationAssignmentData.getTypeList()));

			List<NicListHandover> list = new ArrayList<NicListHandover>();

			if (pr != null) {
				list = pr.getRows();
				if (StringUtils.isNotBlank(investigationAssignmentData
						.getSearchTransactionId()) && list.size() > 0) {
					return new ModelAndView(
							"forward:/servlet/listHandover/startDetailHandover/" + list.get(0).getId().getPackageId());
				}
				else
				{
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
							Constants.HEADING_NIC_LIST_HANDOVER);
					ModelAndView modelAndView = new ModelAndView(
							"listHandover.list", searchResultMap);
					investigationAssignmentData.cleanUpForNextPage();
					modelAndView.addObject("formData", investigationAssignmentData);
					return modelAndView;
				}
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
		
		return this.startDetailHandovercompare(packId, httpRequest, model, null);
	}

	public ModelAndView startDetailHandovercompare(String packId,
			HttpServletRequest httpRequest, Model model,
			List<String> listOfMessages) throws Throwable {
		logger.info("NIC Start DetailHandover compare, listOfMessages");

		ModelAndView modelAndView = new ModelAndView("listHandover.compare");
		List<NicListHandoverDetail> list = new ArrayList<NicListHandoverDetail>();
		this.initializeModelAndViewForms(modelAndView);

		NicListHandover jobDetails = listHandoverService.findById(packId);
		List<EppListHandoverDetail> listD = eppListHandoverDetailService.getListPackageByPackageId(packId, NicListHandover.TYPE_LIST_C).getListModel();
		//String mainTransactionId = jobDetails.getTransactionId();
		if (listD != null && listD.size() > 0) {
			//String[] arrayTrans = jobDetails.getTransactionId().split(",");
			if (listD != null && listD.size() > 0) {
				for (EppListHandoverDetail t : listD) {
					NicTransaction nicTrans = nicTransactionService.findById(t.getTransactionId());
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
						//detail.setTypeListName(jobDetails.getTypeListName());
						list.add(detail);
					}
				}
			}
		}
		model.addAttribute("jobList", list);
		model.addAttribute("typeJob", jobDetails.getId().getTypeList());
		List<String> messages = new ArrayList<String>();

		if (listOfMessages != null) {
			messages.addAll(listOfMessages);
		}

		if (CollectionUtils.isNotEmpty(messages)) {
			httpRequest.setAttribute("messages", messages);
		}

		return modelAndView;
	}
	
	public void initializeModelAndViewForms(ModelAndView mav) {
		mav.addObject("investigationHitData", new InvestigationHitData());
	}

	@RequestMapping(value = "/applyFilter")
	public ModelAndView applyFilter(
			@ModelAttribute(value = "formData") InvestigationAssignmentData investigationAssignmentData,
			WebRequest request, HttpServletRequest httpRequest, ModelMap model) {
		logger.info("applyFilter");

		return this.getHandoverList(investigationAssignmentData,
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
	@RequestMapping(value = "/showPdfResult/{packID}")
	public void printPDF(WebRequest request, Model model, @PathVariable String packID , HttpServletResponse response) {
		try {
			String pachId_ = packID;
			if(pachId_.contains(",")){
				pachId_ = pachId_.split(",")[0];
			}
			NicListHandover jobDetails = listHandoverService.findById(pachId_);
			if(jobDetails != null){
				Map<String, Object> parameterMap = new HashMap<String, Object>();
				//parameterMap.put("titleP", "DANH SÁCH " + jobDetails.getTypeListName().toUpperCase());
				String dateP_ = "Ngày " + jobDetails.getCreateDate().getDate() + " tháng " +  (jobDetails.getCreateDate().getMonth() + 1) + " năm " + (jobDetails.getCreateDate().getYear() + 1900);
				parameterMap.put("dateP", dateP_);
				parameterMap.put("codeHandoverP", "Số: " + jobDetails.getId().getPackageId());
				
				//String[] arrayTrans = jobDetails.getTransactionId().split(",");
				/*parameterMap.put("totalP", String.valueOf(arrayTrans.length));
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
					
				}*/
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@ResponseBody
	@RequestMapping(value = "/showPdfResultSync/{packID}")
	public void printPDF_sync(WebRequest request, Model model,
			@PathVariable String packID, HttpServletResponse response) {
		try {
			String pachId_ = packID;
			if (pachId_.contains(",")) {
				pachId_ = pachId_.split(",")[0];
			}
			NicListHandover jobDetails = listHandoverService.findById(pachId_);
			if (jobDetails != null) {
				Map<String, Object> parameterMap = new HashMap<String, Object>();
				/*parameterMap.put("titleP", "DANH SÁCH "
						+ jobDetails.getTypeListName().toUpperCase());*/
				String dateP_ = "Ngày " + jobDetails.getCreateDate().getDate()
						+ " tháng "
						+ (jobDetails.getCreateDate().getMonth() + 1) + " năm "
						+ (jobDetails.getCreateDate().getYear() + 1900);
				parameterMap.put("dateP", dateP_);
				parameterMap.put("codeHandoverP",
						"Số: " + jobDetails.getId().getPackageId());

				String[] arrayTrans = null;//jobDetails.getTransactionId().split(",");
				parameterMap.put("totalP", String.valueOf(arrayTrans.length));
				List<ResponseReport> list = new ArrayList<ResponseReport>();
				if (arrayTrans.length > 0) {

					for (String t : arrayTrans) {
						NicTransaction nicTrans = nicTransactionService
								.findById(t);
						
						List<NicUploadJob> nicUploadJobs = uploadJobService
								.findAllByTransactionId(t);
						NicUploadJob nicUploadJob = nicUploadJobs.get(0);
							
						if (nicTrans != null) {
							ResponseReport detail = new ResponseReport();
							detail.setSobiennhanF(t);
							detail.setNamsinhF(new SimpleDateFormat(
									"dd-MM-yyyy").format(nicTrans
									.getNicRegistrationData().getDateOfBirth()));
							detail.setCmndF(nicTrans.getNin());
							detail.setNguoinopF(jobDetails.getCreateBy());
							//Lấy số hộ chiếu
							String ppo = nicTrans.getPrevPassportNo();
							if(!nicUploadJob.getJobType().equals("LOS")){
								ppo = documentDataDao.getNicDocumentDataById(t).getModel().getId().getPassportNo();
							}
							detail.setHentraF(ppo);
							list.add(detail);
						}
					}

					/*
					 * FastReportBuilder drb = new FastReportBuilder();
					 * drb.addColumn("Số biên nhận", "sobiennhanF",
					 * String.class.getName(), 30); drb.addColumn("Người nộp",
					 * "nguoinopF", String.class.getName(), 30);
					 * drb.addColumn("Năm sinh", "namsinhF",
					 * String.class.getName(), 30); drb.addColumn("CMND/CCCD",
					 * "cmndF", String.class.getName(), 30);
					 * drb.addColumn("Hẹn trả", "hentraF",
					 * String.class.getName(), 30); for (String entry :
					 * ricPymtRptDto.getSelColumns()) { drb.addColumn(entry,
					 * entry, String.class.getName(), 30); }
					 * drb.setTemplateFile(
					 * "resources/report/templates/list_base.jrxml");
					 * drb.setUseFullPageWidth(true); DynamicReport dr =
					 * drb.build();
					 */
					// PaginatedResult<RICPymtRptDto> result =
					// paymentService.getRicPymtRptRecordList(ricPymtRptDto, 1,
					// 3, true);
					JRDataSource dataSource = new JRBeanCollectionDataSource(
							list);
					ByteArrayOutputStream os = new ByteArrayOutputStream();
					try {
						InputStream reportStream = this.getClass()
								.getResourceAsStream(
										"/report/templates/list_base_sync.jrxml");
						JasperReport jasperReport = JasperCompileManager
								.compileReport(reportStream);
						JasperReportsUtils.renderAsPdf(jasperReport,
								parameterMap, dataSource, os);
						response.setContentType("application/pdf");
						response.getOutputStream().write(os.toByteArray());
					} catch (JRException e) {
						e.printStackTrace();
					} catch (Exception e) {
						e.printStackTrace();
					}
					/*
					 * InputStream reportStream =
					 * this.getClass().getResourceAsStream(
					 * "/RIC_ExceptionHandling_Report.jrxml").; JasperReport
					 * jasperReport = JasperCompileManager
					 * .compileReport(reportStream); JasperPrint jasperPrint =
					 * DynamicJasperHelper.generateJasperPrint(dr, new
					 * ClassicLayoutManager(), dataSource, parameterMap);
					 * System.out.println("jasperPrint >>" +
					 * jasperPrint.getName()); JRExporter exporter = new
					 * JRPdfExporter(); ByteArrayOutputStream os = new
					 * ByteArrayOutputStream();
					 * exporter.setParameter(JRExporterParameter.JASPER_PRINT,
					 * jasperPrint);
					 * exporter.setParameter(JRExporterParameter.OUTPUT_STREAM,
					 * os);
					 * 
					 * FileOutputStream fileOuputStream=null; fileOuputStream =
					 * new FileOutputStream("c:\\pdf\\report1.pdf"); //
					 * fileOuputStream.write(os.toByteArray());
					 * response.setContentType("application/pdf");
					 * response.getOutputStream().write(os.toByteArray()); //
					 * fileOuputStream.close();
					 */
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
