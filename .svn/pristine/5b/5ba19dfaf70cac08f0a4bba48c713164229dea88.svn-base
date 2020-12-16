package com.nec.asia.nic.investigation.controller;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.Charset;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.XML;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
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

import com.nec.asia.nic.comp.immihistory.domain.ImmiHistory;
import com.nec.asia.nic.comp.immihistory.domain.ImmiHistoryChilden;
import com.nec.asia.nic.comp.immihistory.domain.ImmiHistoryImages;
import com.nec.asia.nic.comp.immihistory.domain.ImmiHistoryInfo;
import com.nec.asia.nic.comp.job.dto.InfoFamillyDto;
import com.nec.asia.nic.comp.listHandover.dao.ListHandoverDao;
import com.nec.asia.nic.comp.listHandover.domain.NicListHandover;
import com.nec.asia.nic.comp.listHandover.domain.NicPersoLocations;
import com.nec.asia.nic.comp.listHandover.service.ListHandoverService;
import com.nec.asia.nic.comp.listHandover.service.PersoLocationsService;
import com.nec.asia.nic.comp.trans.dao.NicImmiHistoryChildenDao;
import com.nec.asia.nic.comp.trans.dao.NicImmiHistoryImagesDao;
import com.nec.asia.nic.comp.trans.dao.TemplatePassportDao;
import com.nec.asia.nic.comp.trans.dao.TemplatePassportImgDao;
import com.nec.asia.nic.comp.trans.domain.BaseModelSingle;
import com.nec.asia.nic.comp.trans.domain.BorderGate;
import com.nec.asia.nic.comp.trans.domain.ConfigurationApi;
import com.nec.asia.nic.comp.trans.domain.ConfigurationWorkflow;
import com.nec.asia.nic.comp.trans.domain.EppInventory;
import com.nec.asia.nic.comp.trans.domain.LogCheckConnection;
import com.nec.asia.nic.comp.trans.domain.NicDocumentData;
import com.nec.asia.nic.comp.trans.domain.NicRegistrationData;
import com.nec.asia.nic.comp.trans.domain.NicTransaction;
import com.nec.asia.nic.comp.trans.domain.NicTransactionAttachment;
import com.nec.asia.nic.comp.trans.domain.NicTransactionPackage;
import com.nec.asia.nic.comp.trans.domain.NicUploadJob;
import com.nec.asia.nic.comp.trans.domain.RptStatisticsTransmitData;
import com.nec.asia.nic.comp.trans.domain.SyncQueueJob;
import com.nec.asia.nic.comp.trans.domain.TempSync;
import com.nec.asia.nic.comp.trans.domain.TemplatePassport;
import com.nec.asia.nic.comp.trans.domain.TemplatePassportImage;
import com.nec.asia.nic.comp.trans.dto.ReportRegProcessData;
import com.nec.asia.nic.comp.trans.dto.ConfigurationApiDto;
import com.nec.asia.nic.comp.trans.dto.CountPassport;
import com.nec.asia.nic.comp.trans.dto.CountTranStatus;
import com.nec.asia.nic.comp.trans.dto.ImmiHistoryDto;
import com.nec.asia.nic.comp.trans.dto.LogCheckConnDto;
import com.nec.asia.nic.comp.trans.dto.NicUploadJobDto;
import com.nec.asia.nic.comp.trans.dto.SyncQueueDto;
import com.nec.asia.nic.comp.trans.service.BorderGateService;
import com.nec.asia.nic.comp.trans.service.ConfigurationApiService;
import com.nec.asia.nic.comp.trans.service.ConfigurationWorkflowService;
import com.nec.asia.nic.comp.trans.service.DocumentDataService;
import com.nec.asia.nic.comp.trans.service.EppInventoryService;
import com.nec.asia.nic.comp.trans.service.LogCheckConnectionService;
import com.nec.asia.nic.comp.trans.service.NicImmiHistoryResultService;
import com.nec.asia.nic.comp.trans.service.NicImmiHistoryService;
import com.nec.asia.nic.comp.trans.service.NicRegistrationDataService;
import com.nec.asia.nic.comp.trans.service.NicTransactionAttachmentService;
import com.nec.asia.nic.comp.trans.service.NicTransactionPackageService;
import com.nec.asia.nic.comp.trans.service.NicTransactionService;
import com.nec.asia.nic.comp.trans.service.NicUploadJobService;
import com.nec.asia.nic.comp.trans.service.RptStatisticsTransmitDataService;
import com.nec.asia.nic.comp.trans.service.SyncQueueJobService;
import com.nec.asia.nic.comp.trans.service.exception.NicUploadJobServiceException;
import com.nec.asia.nic.framework.PaginatedResult;
import com.nec.asia.nic.framework.admin.code.domain.CodeValues;
import com.nec.asia.nic.framework.admin.code.domain.Parameters;
import com.nec.asia.nic.framework.admin.code.domain.ParametersId;
import com.nec.asia.nic.framework.admin.code.service.CodesService;
import com.nec.asia.nic.framework.admin.code.service.ParametersService;
import com.nec.asia.nic.framework.admin.rbac.domain.Users;
import com.nec.asia.nic.framework.admin.rbac.service.UserService;
import com.nec.asia.nic.framework.admin.site.domain.SiteGroups;
import com.nec.asia.nic.framework.admin.site.domain.SiteRepository;
import com.nec.asia.nic.framework.admin.site.service.SiteService;
import com.nec.asia.nic.framework.dao.DaoException;
import com.nec.asia.nic.framework.report.service.AuditAccessLogService;
import com.nec.asia.nic.investigation.dto.ChiTietHsoDto;
import com.nec.asia.nic.investigation.dto.DanhsachHsoDto;
import com.nec.asia.nic.investigation.dto.FormReportRegProcess;
import com.nec.asia.nic.investigation.dto.ImmiHistoryCountDto;
import com.nec.asia.nic.investigation.dto.InchitietDto;
import com.nec.asia.nic.investigation.dto.InfoPerson;
import com.nec.asia.nic.investigation.dto.InfoPersons;
import com.nec.asia.nic.investigation.dto.InlichsuDto;
import com.nec.asia.nic.investigation.dto.IntokhaiDto;
import com.nec.asia.nic.investigation.dto.ListApiDto;
import com.nec.asia.nic.investigation.dto.ListWorkflowDto;
import com.nec.asia.nic.investigation.dto.PersoProcessDto;
import com.nec.asia.nic.investigation.dto.ReportProcessDto;
import com.nec.asia.nic.investigation.dto.ReportProcessTranDto;
import com.nec.asia.nic.investigation.dto.RequestTestApiDto;
import com.nec.asia.nic.investigation.dto.RptDetailDto;
import com.nec.asia.nic.investigation.dto.RptStatisticsTransmitDataDto;
import com.nec.asia.nic.investigation.dto.SiteConfig;
import com.nec.asia.nic.investigation.dto.TranCuuHoSoDto;
import com.nec.asia.nic.investigation.dto.TransmissionStatistics;
import com.nec.asia.nic.investigation.dto.WorkflowDto;
import com.nec.asia.nic.investigation.dto.highchart.DataLabelsDto;
import com.nec.asia.nic.investigation.dto.highchart.DataPieDto;
import com.nec.asia.nic.investigation.dto.highchart.Highcharts1Dto;
import com.nec.asia.nic.investigation.dto.highchart.HighchartsDto;
import com.nec.asia.nic.investigation.dto.highchart.Series1Dto;
import com.nec.asia.nic.investigation.dto.highchart.SeriesDto;
import com.nec.asia.nic.investigation.dto.highchart.TitleDto;
import com.nec.asia.nic.investigation.dto.highchart.XAxisDto;
import com.nec.asia.nic.util.Constants;
import com.nec.asia.nic.util.RegistrationConstants;
import com.nec.asia.nic.util.pagingUtil;
import com.nec.asia.nic.utils.DateUtil;
import com.nec.asia.nic.utils.HelperClass;
import com.nec.asia.nic.web.session.UserSession;

@Controller
@RequestMapping(value = "/central")
public class CentralController {

	private static String ASSENDING_ORDER = "1";

	private static String DECENDING_ORDER = "2";

	private static String ACTIVE_SITE_Y = "Y";

	private static final String CODE_TTXL_MIENBAC = "AA";
	private static final String CODE_TTXL_MIENTRUNG = "BB";
	private static final String CODE_TTXL_MIENNAM = "CC";
	private static final String IMMI_TYPE_XC = "X";
	private static final String IMMI_TYPE_NC = "N";

	private HashMap<Character, String> codeVN;

	@Autowired
	private NicUploadJobService uploadJobService;

	@Autowired
	private SiteService siteService;

	@Autowired
	private DocumentDataService documentDataService;

	@Autowired
	private ParametersService parametersService;

	@Autowired
	private NicImmiHistoryService immiService;

	@Autowired
	private NicImmiHistoryResultService immiServiceResult;

	@Autowired
	private BorderGateService borderGateService;

	@Autowired
	private NicTransactionService nicTransactionService;

	@Autowired
	private NicUploadJobService nicUploadJobService;

	@Autowired
	private CodesService codesService;

	@Autowired
	private ListHandoverDao handoverDao;

	@Autowired
	private PersoLocationsService persoLocationService;

	@Autowired
	private NicTransactionPackageService nicTransactionPackageService;

	@Autowired
	private NicTransactionAttachmentService nicTransactionAttachmentService;

	@Autowired
	private UserService userService;

	@Autowired
	private RptStatisticsTransmitDataService rptStatisticsTransmitDataSerivce;

	@Autowired
	private EppInventoryService eppInventoryService;

	@Autowired
	private ListHandoverService handoverService;

	@Autowired
	private NicRegistrationDataService regDateService;

	@Autowired
	private ConfigurationWorkflowService configurationWorkflowService;

	@Autowired
	private ConfigurationApiService configurationApiService;

	@Autowired
	private SyncQueueJobService queueJobService;

	@Autowired
	private LogCheckConnectionService logCheckConnectionService;

	@Autowired
	private NicImmiHistoryChildenDao childenDao;

	@Autowired
	private NicImmiHistoryImagesDao ImmiHistoryImagesDao;

	@Autowired
	private AuditAccessLogService auditAccessLogService;
	
	private final SimpleDateFormat fm = new SimpleDateFormat(
			DateUtil.FORMAT_YYYYMMDD);

	@ModelAttribute("listSiteRepository")
	public Map<String, String> listSiteRepository() throws DaoException {
		Map<String, String> list = new HashMap<String, String>();
		list.put("", "Tất cả");
		SiteGroups sg = siteService.getOnlyListByLevel("1");
		if (sg != null) {
			List<SiteRepository> listSite = siteService.findAllByGroupId1(sg
					.getGroupId());
			for (SiteRepository sr : listSite) {
				list.put(sr.getSiteId(), sr.getSiteName());
			}
		}
		return list;
	}

	@ModelAttribute("siteList")
	public Map<String, String> siteList() throws DaoException {
		Map<String, String> list = new LinkedHashMap<String, String>();
		List<SiteRepository> listSite = siteService
				.findAllByActive(ACTIVE_SITE_Y);
		this.createCode();
		Collections.sort(listSite, new Comparator<SiteRepository>() {
			@Override
			public int compare(SiteRepository o1, SiteRepository o2) {
				// TODO Auto-generated method stub
				return generator(o1.getSiteName()).compareTo(
						generator(o2.getSiteName()));
			}

		});
		for (SiteRepository sr : listSite) {
			list.put(sr.getSiteId(), sr.getSiteName());
		}
		return list;
	}

	@ModelAttribute("groupList")
	public Map<String, String> groupList() throws DaoException {
		Map<String, String> list = new HashMap<String, String>();
		List<SiteGroups> listSiteG = siteService.findAll();
		for (SiteGroups sr : listSiteG) {
			list.put(sr.getGroupId().trim(), sr.getGroupName());
		}

		return list;
	}

	@ModelAttribute("persoList")
	public Map<String, String> persoList() throws DaoException {
		Map<String, String> list = new HashMap<String, String>();
		List<NicPersoLocations> persoList = persoLocationService.findAll();
		for (NicPersoLocations pr : persoList) {
			list.put(pr.getCode(), pr.getName());
		}

		return list;
	}

	@ModelAttribute("listSiteResXL")
	public Map<String, String> listSiteResXL() throws DaoException {
		Map<String, String> list = new HashMap<String, String>();
		list.put("", "Tất cả");
		List<SiteRepository> listSite = siteService.findAllSite();
		for (SiteRepository sr : listSite) {
			list.put(sr.getSiteId(), sr.getSiteName());
		}
		return list;
	}

	@ModelAttribute("listTTxuly")
	public Map<String, String> listTTxuly() throws DaoException {
		Map<String, String> list = new HashMap<String, String>();
		List<SiteGroups> listSiteG = siteService.getListGroupByLevel("1");
		for (SiteGroups sr : listSiteG) {
			if (sr.getGroupId().equals("MB")) {
				list.put(sr.getGroupId(), sr.getGroupName());
			}
		}

		return list;
	}

	@ModelAttribute("listTTcathe")
	public Map<Long, String> listTTcathe() throws DaoException {
		Map<Long, String> list = new HashMap<Long, String>();
		list.put(0l, "Tất cả");
		List<NicPersoLocations> listSite = persoLocationService.findAll();
		for (NicPersoLocations sr : listSite) {
			list.put(sr.getIdPerso(), sr.getName());
		}
		Map<Long, String> treeMap = new TreeMap<>(list);
		return treeMap;
	}

	@ModelAttribute("listNationality")
	public Map<String, String> listNationality() throws DaoException {
		Map<String, String> list = new HashMap<String, String>();
		list.put("", "Tất cả");
		List<CodeValues> listcode = codesService.findAllByCodeId("NATIONALITY");
		for (CodeValues sr : listcode) {
			list.put(sr.getId().getCodeValue(), sr.getCodeValueDesc());
		}
		Map<String, String> treeMap = new TreeMap<>(list);
		return treeMap;
	}

	@ModelAttribute("listCountry")
	public Map<String, String> listCountry() throws DaoException {
		Map<String, String> list = new HashMap<String, String>();
		list.put("", "Tất cả");
		List<CodeValues> listcode = codesService.findAllByCodeId("COUNTRY");
		for (CodeValues sr : listcode) {
			list.put(sr.getId().getCodeValue(), sr.getCodeValueDesc());
		}
		Map<String, String> treeMap = new TreeMap<>(list);
		return treeMap;
	}

	@ModelAttribute("listBorder")
	public Map<String, String> listBorder() throws DaoException {
		Map<String, String> list = new HashMap<String, String>();
		list.put("", "Tất cả");
		List<BorderGate> listBorder = borderGateService.findAllBorderGate("");
		for (BorderGate sr : listBorder) {
			list.put(sr.getCode(), sr.getName());
		}
		Map<String, String> treeMap = new TreeMap<>(list);
		return treeMap;
	}

	@RequestMapping(value = "/headQuarter")
	public ModelAndView headQuarter(
			@ModelAttribute(value = "formData") InvestigationAssignmentData investigationAssignmentData,
			WebRequest request, HttpServletRequest httpRequest, ModelMap model) {

		try {

			ModelAndView modelAndView = new ModelAndView(
					"central.trungtamdieuhanh.show");
			modelAndView.addObject("formData", investigationAssignmentData);
			return modelAndView;
		} catch (Exception e) {

			e.printStackTrace();
			System.out.println(e.getMessage());
		}

		return null;
	}

	@RequestMapping(value = "/personalized")
	public ModelAndView personalized(
			@ModelAttribute(value = "formData") InvestigationAssignmentData investigationAssignmentData,
			WebRequest request, HttpServletRequest httpRequest, ModelMap model) {

		try {

			ModelAndView modelAndView = new ModelAndView(
					"central.cathehoa.show");
			modelAndView.addObject("formData", investigationAssignmentData);
			return modelAndView;
		} catch (Exception e) {

			e.printStackTrace();
			System.out.println(e.getMessage());
		}

		return null;
	}

	@RequestMapping(value = "/danhsachxlcapphat")
	public ModelAndView danhsachxlCapPhat(
			@ModelAttribute(value = "formData") InvestigationAssignmentData investigationAssignmentData,
			WebRequest request, HttpServletRequest httpRequest, ModelMap model) {
		try {
			int pageNo = !StringUtils.isEmpty(request.getParameter("pageNo")) ? Integer
					.parseInt(request.getParameter("pageNo")) : 1;
			int pageSize = Constants.PAGE_SIZE_DEFAULT;
			httpRequest.setAttribute("pendingCount",
					uploadJobService.getPendingInvestigationsCount());

			if (investigationAssignmentData == null) {
				investigationAssignmentData = new InvestigationAssignmentData();
			} else {
				/*
				 * if(StringUtils.isNotEmpty(investigationAssignmentData.getCreateBy
				 * ())){ String sDate1=
				 * investigationAssignmentData.getCreateBy(); Date date1 = new
				 * SimpleDateFormat("dd/MM/yyyy").parse(sDate1);
				 * SimpleDateFormat formatter = new
				 * SimpleDateFormat("dd/MM/yy"); String strDate=
				 * formatter.format(date1);
				 * investigationAssignmentData.setCreateBy(strDate); }
				 * 
				 * if(StringUtils.isNotEmpty(investigationAssignmentData.getEndDate
				 * ())){ String sDate1=
				 * investigationAssignmentData.getEndDate(); Date date1 = new
				 * SimpleDateFormat("dd/MM/yyyy").parse(sDate1);
				 * SimpleDateFormat formatter = new
				 * SimpleDateFormat("dd/MM/yy"); String strDate=
				 * formatter.format(date1);
				 * investigationAssignmentData.setEndDate(strDate); }
				 */
			}
			/*
			 * if(StringUtils.isEmpty(investigationAssignmentData.getTypeList()))
			 * { investigationAssignmentData.setTypeList("2"); }
			 */

			investigationAssignmentData.cleanUpStrings();

			Integer total = (int) (long) nicTransactionService
					.getRowCountallPassportList(
							investigationAssignmentData.getPackageCode(),
							investigationAssignmentData.getRegSiteCode(),
							investigationAssignmentData.getCreateBy(),
							investigationAssignmentData.getEndDate());
			if (total > 0) {
				List<NicUploadJobDto> list = new ArrayList<NicUploadJobDto>();
				list = nicTransactionService.allPassportList(
						investigationAssignmentData.getPackageCode(),
						investigationAssignmentData.getRegSiteCode(),
						investigationAssignmentData.getCreateBy(),
						investigationAssignmentData.getEndDate(), pageNo,
						pageSize);
				if (list != null) {
					for (NicUploadJobDto item : list) {
						if (item.getCreateDate() != null)
							item.setPriorityDesc(new SimpleDateFormat(
									"dd/MM/yyyy").format(item.getCreateDate()));

						SiteRepository siteR = siteService.getSiteRepoById(item
								.getRegSiteCode());
						if (siteR != null) {
							item.setRegSiteCode(siteR.getSiteName());
						}
					}

					model.addAttribute("total", total);
					int firstResults = (pageNo - 1) < 0 ? 0 : (pageNo - 1)
							* pageSize;
					model.addAttribute("pageSize", pageSize);
					model.addAttribute("pageNo", pageNo);
					model.addAttribute("totalPage",
							pagingUtil.totalPage(total, pageSize));
					model.addAttribute("startIndex",
							total != 0 ? firstResults + 1 : 0);
					model.addAttribute("totalRecord", total);
					model.addAttribute("endIndex", firstResults + total);
					model.addAttribute("dsXuLyA", list);

					// end
					model.addAttribute("fnSelected",
							Constants.HEADING_NIC_INVESTIGATION);
					ModelAndView modelAndView = new ModelAndView(
							"center.processPackage.list", null);
					modelAndView.addObject("formData",
							investigationAssignmentData);
					return modelAndView;
				} else {
					model.addAttribute("fnSelected",
							Constants.HEADING_NIC_INVESTIGATION);
					return new ModelAndView("center.processPackage.list", null);
				}
			} else {
				model.addAttribute("fnSelected",
						Constants.HEADING_NIC_INVESTIGATION);
				return new ModelAndView("center.processPackage.list", null);
			}
			// }
		} catch (Exception e) {

			e.printStackTrace();
			return null;
		}

	}

	@RequestMapping(value = "/danhsachdphoiin")
	public ModelAndView danhsachdphoiin(
			@ModelAttribute(value = "formData") InvestigationAssignmentData investigationAssignmentData,
			WebRequest request, HttpServletRequest httpRequest, ModelMap model) {
		PaginatedResult<NicUploadJobDto> pr = null;
		try {
			int pageNo = !StringUtils.isEmpty(request.getParameter("pageNo")) ? Integer
					.parseInt(request.getParameter("pageNo")) : 1;
			int pageSize = Constants.PAGE_SIZE_DEFAULT;
			httpRequest.setAttribute("pendingCount",
					uploadJobService.getPendingInvestigationsCount());

			investigationAssignmentData.cleanUpStrings();
			/*
			 * Phúc tạm đóng 29/11/2019 thay đổi chức năng form Integer total =
			 * (int) (long)nicTransactionService.getRowCountallPersoList(
			 * investigationAssignmentData.getPackageCode(),
			 * investigationAssignmentData.getPriority(),
			 * investigationAssignmentData.getCreateBy(),
			 * investigationAssignmentData.getEndDate());
			 */
			int total = 1;
			if (total > 0) {
				List<NicUploadJobDto> list = new ArrayList<NicUploadJobDto>();
				pr = nicTransactionService.allListBProcessPerso(
						investigationAssignmentData.getPackageCode(),
						investigationAssignmentData.getCreateDate(),
						investigationAssignmentData.getRegSiteCode(), pageNo,
						pageSize);
				/*
				 * Phúc tạm đóng 29/11/2019 thay đổi chức năng form list =
				 * nicTransactionService.allPersoList(
				 * investigationAssignmentData.getPackageCode(),
				 * investigationAssignmentData.getPriority(),
				 * investigationAssignmentData.getCreateBy(),
				 * investigationAssignmentData.getEndDate(),pageNo, pageSize);
				 */
				if (pr != null) {
					list = pr.getRows();
				}
				if (list != null && list.size() > 0) {

					for (NicUploadJobDto item : list) {
						if (item.getLeaderId() != null) {
							Users users = userService.findByUserId(item
									.getLeaderId());
							if (users != null)
								item.setLeaderId(users.getUserName());
						}
					}

					model.addAttribute("total", pr.getTotal());
					int firstResults = (pageNo - 1) < 0 ? 0 : (pageNo - 1)
							* pageSize;
					model.addAttribute("pageSize", pageSize);
					model.addAttribute("pageNo", pageNo);
					model.addAttribute("totalPage",
							pagingUtil.totalPage(pr.getTotal(), pageSize));
					model.addAttribute("startIndex",
							pr.getTotal() != 0 ? firstResults + 1 : 0);
					model.addAttribute("totalRecord", pr.getTotal());
					model.addAttribute("endIndex",
							firstResults + pr.getRowSize());
					model.addAttribute("dsXuLyA", list);

					// end
					ModelAndView modelAndView = new ModelAndView(
							"center.packagePrint1.list", null);
					modelAndView.addObject("formData",
							investigationAssignmentData);
					return modelAndView;
				} else {
					return new ModelAndView("center.packagePrint1.list", null);
				}
			} else {
				return new ModelAndView("center.packagePrint1.list", null);
			}
			// }
		} catch (Exception e) {

			e.printStackTrace();
			return null;
		}

	}

	@RequestMapping(value = "/danhsachlsxnc")
	public ModelAndView danhsachlsxnc(
			@ModelAttribute(value = "formData") InvestigationHistoryData investigationAssignmentData,
			WebRequest request, HttpServletRequest httpRequest, ModelMap model) {
		try {

			int pageNo = !StringUtils.isEmpty(request.getParameter("pageNo")) ? Integer
					.parseInt(request.getParameter("pageNo")) : 1;
			int pageSize = Constants.PAGE_SIZE_DEFAULT;
			if (!StringUtils.isEmpty(investigationAssignmentData.getFullName())) {
				investigationAssignmentData
						.setFullName(investigationAssignmentData.getFullName()
								.toUpperCase());
			}

			if (!StringUtils.isEmpty(investigationAssignmentData
					.getSoPhieuXnc())) {

			} else if (!StringUtils.isEmpty(investigationAssignmentData
					.getPassportNo())) {

			} else if (!StringUtils.isEmpty(investigationAssignmentData
					.getVisaNo())) {

			} else {
				int dem = 13;
				if (StringUtils.isEmpty(investigationAssignmentData
						.getFullName()))
					dem--;
				if (StringUtils.isEmpty(investigationAssignmentData
						.getPassportNo()))
					dem--;
				if (StringUtils
						.isEmpty(investigationAssignmentData.getVisaNo()))
					dem--;
				if (StringUtils.isEmpty(investigationAssignmentData
						.getNationCode()))
					dem--;
				if (StringUtils.isEmpty(investigationAssignmentData
						.getDateOfBirth()))
					dem--;
				if (StringUtils
						.isEmpty(investigationAssignmentData.getGender()))
					dem--;
				if (StringUtils.isEmpty(investigationAssignmentData
						.getFromDate()))
					dem--;
				if (StringUtils.isEmpty(investigationAssignmentData
						.getEndDate()))
					dem--;
				if (StringUtils.isEmpty(investigationAssignmentData
						.getTypeXnc()))
					dem--;
				if (StringUtils.isEmpty(investigationAssignmentData.getCkXnc()))
					dem--;
				if (StringUtils.isEmpty(investigationAssignmentData
						.getChuyenBay()))
					dem--;
				if (StringUtils.isEmpty(investigationAssignmentData
						.getSoPhieuXnc()))
					dem--;
				if (StringUtils.isEmpty(investigationAssignmentData
						.getPurpose()))
					dem--;
				// if(StringUtils.isEmpty(investigationAssignmentData.getSapXep()))
				// dem--;
				if (dem < 2) {
					return new ModelAndView("center.lsxnc.list", null);
				}
			}
			PaginatedResult<ImmiHistoryInfo> pag = immiService
					.getListImihistory(investigationAssignmentData
							.getFullName().trim(), investigationAssignmentData
							.getPassportNo().trim(),
							investigationAssignmentData.getVisaNo().trim(),
							investigationAssignmentData.getNationCode(),
							investigationAssignmentData.getDateOfBirth(),
							investigationAssignmentData.getGender(),
							investigationAssignmentData.getFromDate(),
							investigationAssignmentData.getEndDate(),
							investigationAssignmentData.getTypeXnc(),
							investigationAssignmentData.getCkXnc(),
							investigationAssignmentData.getChuyenBay().trim(),
							investigationAssignmentData.getSoPhieuXnc().trim(),
							investigationAssignmentData.getPurpose().trim(),
							investigationAssignmentData.getSapXep(), pageNo,
							pageSize);
			if (pag != null) {
				List<ImmiHistoryInfo> list = pag.getRows();
				Integer total = pag.getTotal();// (int)
												// (long)immiService.listImmihistoryAllCount(nicUp);;
				if (total > 0) {
					// List<ImmiHistory> list = new ArrayList<ImmiHistory>();
					// list = immiService.listImmihistoryAll(nicUp, pageNo,
					// pageSize);
					if (list != null) {
						model.addAttribute("total", total);
						int firstResults = (pageNo - 1) < 0 ? 0 : (pageNo - 1)
								* pageSize;
						model.addAttribute("pageSize", pageSize);
						model.addAttribute("pageNo", pageNo);
						model.addAttribute("totalPage",
								pagingUtil.totalPage(total, pageSize));
						model.addAttribute("startIndex",
								total != 0 ? firstResults + 1 : 0);
						model.addAttribute("totalRecord", total);
						model.addAttribute("endIndex",
								firstResults + list.size());
						model.addAttribute("dsXuLyA", list);
						// model.addAttribute("log",log);
						// end
						ModelAndView modelAndView = new ModelAndView(
								"center.lsxnc.list", null);
						modelAndView.addObject("formData",
								investigationAssignmentData);
						return modelAndView;
					} else {
						return new ModelAndView("center.lsxnc.list", null);
					}
				} else {
					return new ModelAndView("center.lsxnc.list", null);
				}
			} else {
				return new ModelAndView("center.lsxnc.list", null);
			}

		} catch (Exception e) {

			e.printStackTrace();
			return null;
		}

	}

	@ResponseBody
	@RequestMapping(value = "/getChitietXNC/{id}", method = RequestMethod.GET)
	@ExceptionHandler
	public ImmiHistoryDataOuput getChitietXNC(WebRequest request,
			@PathVariable String id, ModelMap model) {
		ImmiHistoryDataOuput output = new ImmiHistoryDataOuput();
		DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		try {
			ImmiHistory im = immiService.getImmiHistoryById(Long.parseLong(id));
			if (im != null) {
				// if(im !=null){
				// if(im.getGender().equals("M")){
				// im.setGender("Nữ");
				// }else if(im.getGender().equals("F")){
				// im.setGender("Nam");
				// }else{
				// im.setGender("Khác");
				// }
				// if(im.getPersonType().equals("VN")){
				// im.setPersonType("Việt Nam");
				// }else if(im.getPersonType().equals("NN")){
				// im.setPersonType("Nước Ngoài");
				// }else{
				// im.setPersonType("Việt kiều");
				// }
				// if(im.getImmiType().equals("N")){
				// im.setImmiType("Nhập");
				// }else im.setImmiType("Xuất");
				// if(im.getPassportType().equals("P")){
				// im.setPassportType("Hộ chiếu phổ thông");
				// }else if(im.getPassportType().equals("PO")){
				// im.setPassportType("Hộ chiếu công vụ");
				// }
				// }
				output.setIm(im);
				if (im.getDateOfBirth() != null) {
					output.setDateOfBirth(dateFormat.format(im.getDateOfBirth()));
				}
				if (im.getCreatedTime() != null) {
					output.setCreateTime(dateFormat.format(im.getCreatedTime()));
				}
				if (im.getVisaIssueDate() != null) {
					output.setVisaIssueDate(dateFormat.format(im
							.getVisaIssueDate()));
				}
				if (im.getLastModifiedTime() != null) {
					output.setLastModifiedTime(dateFormat.format(im
							.getLastModifiedTime()));
				}
				if (im.getPassportExpiredDate() != null) {
					output.setPassportExpiredDate(dateFormat.format(im
							.getPassportExpiredDate()));
				}
				if (im.getResidenceUntilDate() != null) {
					output.setResidenceUntilDate(dateFormat.format(im
							.getResidenceUntilDate()));
				}

				if (im.getSystemCheckResult() == 0) {
					output.setAdminResult("Không qua");
				} else if (im.getSystemCheckResult() == 1) {
					output.setAdminResult("Cho qua");
				}
				CodeValues listcode = codesService.getCodeValueByIdName(
						"COUNTRY", im.getCountryCode());
				if (listcode != null) {
					im.setCountryCode(listcode.getCodeValueDesc());
				}
				listcode = codesService.getCodeValueByIdName("COUNTRY",
						im.getPlaceOfBirthCode());
				if (listcode != null) {
					im.setPlaceOfBirthCode(listcode.getCodeValueDesc());
				}
				listcode = codesService.getCodeValueByIdName("COUNTRY",
						im.getPassportIssuePlaceCode());
				if (listcode != null) {
					im.setPassportIssuePlaceCode(listcode.getCodeValueDesc());
				}
				List<BorderGate> listBorder = borderGateService
						.findAllBorderGate(im.getBorderGateCode());
				if (listBorder != null) {
					im.setBorderGateCode(listBorder.get(0).getName());
				}
				List<ImmiHistoryImages> images = ImmiHistoryImagesDao
						.findByImmiId(im.getId());
				if (images != null && images.size() > 0) {
					output.setData(Base64.encodeBase64String(images.get(0)
							.getData()));
				} else {
					output.setData(null);
				}
				return output;
			}

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return output;
	}

	@ResponseBody
	@RequestMapping(value = "/getChitietChildenXNC/{id}", method = RequestMethod.GET)
	@ExceptionHandler
	public List<ImmiHistoryChilden> txnEnquiryDetailInit(WebRequest request,
			@PathVariable String id, ModelMap model) throws Exception {

		// DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		try {
			List<ImmiHistoryChilden> list = childenDao.findByImmiId(Long
					.parseLong(id));
			if (list != null && list.size() > 0) {
				for (ImmiHistoryChilden im : list) {
					if (im.getFullName() == null)
						im.setFullName("");
					if (im.getAddress() == null)
						im.setAddress("");
					if (im.getFamilyrelationshipCode() == null)
						im.setFamilyrelationshipCode("");
					if (im.getPlaceOfBirthCode() == null)
						im.setPlaceOfBirthCode("");
					if (im.getGender() == null)
						im.setGender("");
				}
				return list;
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}

		// mav.addObject("showPrioritizeBtn", "");
		// mav.addObject("showReprintBtn", "");
		// mav.addObject("nicEnqDetailsForm", nicEnqForm);
		return null;
	}

	@RequestMapping(value = "/tracuuhosohochieu")
	public ModelAndView tracuuhosohochieu(
			@ModelAttribute(value = "formData") InvestigationAssignmentData investigationAssignmentData,
			WebRequest request, HttpServletRequest httpRequest, ModelMap model) {
		// logger.info("NIC Investigation");
		// PaginatedResult<NicUploadJobDto> pr = null;
		// PaginatedResult<NicUploadJobDto> rejectedList = null;
		try {
			int pageNo = !StringUtils.isEmpty(request.getParameter("pageNo")) ? Integer
					.parseInt(request.getParameter("pageNo")) : 1;
			// int pageSize = 20;
			// int startIndex = 0;
			// HttpSession session = httpRequest.getSession();
			// UserSession userSession = (UserSession)
			// session.getAttribute("userSession");
			// logger.info("The Officer Id or User Id is ===============>>>>>>>>>>"+
			// userSession.getUserName());

			// Modified for pagination
			// ParametersId id = new ParametersId();
			// id.setParaName(Parameters.MAX_RECORDS_PER_PAGE);
			// id.setParaScope(Parameters.SCOPE_SYSTEM);
			// String tableId = "row";
			// String sortedElement = "codeId";
			// String order = CentralController.DECENDING_ORDER;
			//
			// try {
			// String sort = request
			// .getParameter(new ParamEncoder(tableId)
			// .encodeParameterName(TableTagParameters.PARAMETER_SORT));
			// if (StringUtils.isNotBlank(sort))
			// sortedElement = sort;
			// } catch (Exception ex) {
			// //logMessage(ex);
			// }
			//
			// try {
			// String requestOrder = request
			// .getParameter(new ParamEncoder(tableId)
			// .encodeParameterName(TableTagParameters.PARAMETER_ORDER));
			// if (StringUtils.isNotBlank(requestOrder))
			// order = requestOrder;
			// } catch (Exception ex) {
			// //logMessage(ex);
			// }
			//
			// Order hibernateOrder = Order.desc(sortedElement);
			// if (order.equalsIgnoreCase(CentralController.DECENDING_ORDER)) {
			// hibernateOrder = Order.asc(sortedElement);
			// }
			//
			// Parameters parameter = parametersService.findById(id);
			//
			// if (parameter != null) {
			// String pageSizeDb = parameter.getParaShortValue();
			//
			// if (StringUtils.isNotBlank(pageSizeDb)
			// && StringUtils.isNumeric(pageSizeDb)) {
			// pageSize = Integer.parseInt(pageSizeDb);
			// }
			// }
			int pageSize = Constants.PAGE_SIZE_DEFAULT;
			httpRequest.setAttribute("pendingCount",
					uploadJobService.getPendingInvestigationsCount());

			NicUploadJobDto nicUp = new NicUploadJobDto();
			if (investigationAssignmentData == null) {
				investigationAssignmentData = new InvestigationAssignmentData();
			} else {
				nicUp.setFullName(investigationAssignmentData.getName());
				nicUp.setGender(investigationAssignmentData.getGender());
				nicUp.setDob(investigationAssignmentData.getDob());
				nicUp.setNin(investigationAssignmentData.getNin());
				nicUp.setRecieptNo(investigationAssignmentData
						.getPackageNumber());
				nicUp.setPackageId(investigationAssignmentData.getPackageCode());
				if (StringUtils.isNotEmpty(investigationAssignmentData
						.getTypeList())) {
					if (investigationAssignmentData.getTypeList().equals("A"))
						nicUp.setTypeApprove("7");
					else if (investigationAssignmentData.getTypeList().equals(
							"B"))
						nicUp.setTypeApprove("8");
					else
						nicUp.setTypeApprove(null); // Để tạm thời
				} else {
					nicUp.setTypeApprove(null);
				}
				nicUp.setPassportNo(investigationAssignmentData.getPassportNo());
				nicUp.setPassportType(investigationAssignmentData
						.getPassportType());
			}
			/*
			 * if(StringUtils.isEmpty(investigationAssignmentData.getTypeList()))
			 * { investigationAssignmentData.setTypeList("2"); }
			 */

			investigationAssignmentData.cleanUpStrings();
			Integer total = (int) (long) nicTransactionService
					.getTracuuhosohcCount(nicUp);
			if (total >= 0) {
				List<NicUploadJobDto> list = new ArrayList<NicUploadJobDto>();
				list = nicTransactionService.allTracuuhosohc(nicUp, pageNo,
						pageSize);
				if (list != null) {
					for (NicUploadJobDto immi : list) {
						if (immi.getGender().equals("M"))
							immi.setGender("Nam");
						else if (immi.getGender().equals("F"))
							immi.setGender("Nữ");
						else
							immi.setGender("Khác");

						if (immi.getDatePackage() != null)
							immi.setPackageDate(immi.getDatePackage()
									.toString());
					}
					// list = pr.getRows();
					model.addAttribute("total", total);
					// Map searchResultMap = new HashMap();
					// searchResultMap.put("totalRecords", total);
					int firstResults = (pageNo - 1) < 0 ? 0 : (pageNo - 1)
							* pageSize;
					model.addAttribute("pageSize", pageSize);
					model.addAttribute("pageNo", pageNo);
					model.addAttribute("totalPage",
							pagingUtil.totalPage(total, pageSize));
					model.addAttribute("startIndex",
							total != 0 ? firstResults + 1 : 0);
					model.addAttribute("totalRecord", total);
					model.addAttribute("endIndex", firstResults + total);
					model.addAttribute("dsXuLyA", list);

					// end
					model.addAttribute("fnSelected",
							Constants.HEADING_NIC_INVESTIGATION);
					ModelAndView modelAndView = new ModelAndView(
							"center.tracuuhshc.list", null);
					modelAndView.addObject("formData",
							investigationAssignmentData);
					return modelAndView;
				} else {
					model.addAttribute("fnSelected",
							Constants.HEADING_NIC_INVESTIGATION);
					return new ModelAndView("center.tracuuhshc.list", null);
				}
			} else {
				model.addAttribute("fnSelected",
						Constants.HEADING_NIC_INVESTIGATION);
				return new ModelAndView("center.tracuuhshc.list", null);
			}
			// }
		} catch (Exception e) {

			e.printStackTrace();
			return null;
		}

	}

	@RequestMapping(value = "/process")
	public ModelAndView process(
			@ModelAttribute(value = "formData") InvestigationAssignmentData investigationAssignmentData,
			WebRequest request, HttpServletRequest httpRequest, ModelMap model) {

		try {
			ModelAndView modelAndView = new ModelAndView(
					"central.xulyhoso.show");
			modelAndView.addObject("formData", investigationAssignmentData);
			return modelAndView;
		} catch (Exception e) {

			e.printStackTrace();
			System.out.println(e.getMessage());
		}

		return null;
	}

	@ResponseBody
	@RequestMapping(value = "/dulieuxulyhs", method = RequestMethod.GET)
	public Highcharts1Dto layThongTinXLHS() {
		Highcharts1Dto dto = new Highcharts1Dto();
		List<PersoProcessDto> list = xuLyHoSo();
		if (list != null) {
			List<String> categories = new ArrayList<String>();
			List<Integer> lsN = new ArrayList<Integer>();
			List<Integer> lsY = new ArrayList<Integer>();
			List<Integer> lsC = new ArrayList<Integer>();
			List<Integer> lsTb = new ArrayList<Integer>();
			int countN = 0;
			int countY = 0;
			int countC = 0;
			int countTb = 0;
			int totalN = 0;
			int totalY = 0;
			int totalC = 0;
			for (PersoProcessDto ps : list) {
				categories.add(ps.getSiteGroups());
				countN = ps.getDuLieuCTH().get("NN");
				totalN += countN;
				lsN.add(countN);
				countY = ps.getDuLieuCTH().get("NY");
				totalY += countY;
				lsY.add(countY);
				countC = ps.getDuLieuCTH().get("NC");
				totalC += countC;
				lsC.add(countC);
				countTb = (countN + countY + countC) / 3;
				lsTb.add(countTb);
			}
			Series1Dto ssN = new Series1Dto();
			ssN.setName("Không được cấp");
			ssN.setType("column");
			ssN.setColor("#90ed7d");
			ssN.setData(lsN);
			ssN.setStack("N");

			Series1Dto ssY = new Series1Dto();
			ssY.setName("Đã được cấp");
			ssY.setColor("#7cb5ec");
			ssY.setType("column");
			ssY.setData(lsY);
			ssY.setStack("Y");

			Series1Dto ssC = new Series1Dto();
			ssC.setName("Đang xử lý");
			ssC.setColor("#434348");
			ssC.setType("column");
			ssC.setData(lsC);
			ssC.setStack("C");

			Series1Dto seriesSp = new Series1Dto();
			seriesSp.setName("Trung bình");
			seriesSp.setType("spline");
			seriesSp.setData(lsTb);

			Series1Dto seriesPie = new Series1Dto();
			seriesPie.setName("Tổng số");
			seriesPie.setType("pie");
			List<DataPieDto> dtPie = new ArrayList<DataPieDto>();
			DataPieDto dtoN = new DataPieDto();
			dtoN.setName("Không được cấp");
			dtoN.setY(totalN);
			dtoN.setColor("#90ed7d");

			DataPieDto dtoY = new DataPieDto();
			dtoY.setName("Đã được cấp");
			dtoY.setY(totalY);
			dtoY.setColor("#7cb5ec");

			DataPieDto dtoC = new DataPieDto();
			dtoC.setName("Đang xử lý");
			dtoC.setY(totalC);
			dtoC.setColor("#434348");
			dtPie.add(dtoY);
			dtPie.add(dtoN);
			dtPie.add(dtoC);
			seriesPie.setDataPie(dtPie);
			dto.getSeries().add(ssY);
			dto.getSeries().add(ssN);
			dto.getSeries().add(ssC);
			dto.getSeries().add(seriesSp);
			dto.getSeries().add(seriesPie);

			XAxisDto axis = new XAxisDto();
			axis.setCategories(categories);
			dto.setxAxis(axis);

		}
		return dto;
	}

	public List<PersoProcessDto> xuLyHoSo() {
		List<PersoProcessDto> lsPs = new ArrayList<PersoProcessDto>();
		try {

			int yearN = Calendar.getInstance().get(Calendar.YEAR);
			int yearB = Calendar.getInstance().get(Calendar.YEAR) - 1;
			PersoProcessDto dtoN = new PersoProcessDto();
			dtoN.setSiteGroups(String.valueOf(yearN));
			PersoProcessDto dtoB = new PersoProcessDto();
			dtoB.setSiteGroups(String.valueOf(yearB));
			Map<String, Integer> mpN = new HashMap<String, Integer>();
			Map<String, Integer> mpB = new HashMap<String, Integer>();
			// Không được cấp
			List<NicUploadJob> yearNN = uploadJobService
					.getListByStatusAndYear(
							new String[] { NicUploadJob.RECORD_STATUS_REJECTED },
							Calendar.getInstance().get(Calendar.YEAR), 1);
			List<NicUploadJob> yearBN = uploadJobService
					.getListByStatusAndYear(
							new String[] { NicUploadJob.RECORD_STATUS_REJECTED },
							Calendar.getInstance().get(Calendar.YEAR), 0);
			int totalNN = yearNN != null ? yearNN.size() : 0;
			int totalBN = yearBN != null ? yearBN.size() : 0;
			mpN.put("NN", totalNN);
			mpB.put("NN", totalBN);
			// end
			// Đã được cấp
			List<NicUploadJob> yearNY = uploadJobService
					.getListByStatusAndYear(
							new String[] { NicUploadJob.RECORD_STATUS_APPROVE_PERSO },
							Calendar.getInstance().get(Calendar.YEAR), 1);
			List<NicUploadJob> yearBY = uploadJobService
					.getListByStatusAndYear(
							new String[] { NicUploadJob.RECORD_STATUS_APPROVE_PERSO },
							Calendar.getInstance().get(Calendar.YEAR), 0);
			int totalNY = yearNY != null ? yearNY.size() : 0;
			int totalBY = yearBY != null ? yearBY.size() : 0;
			mpN.put("NY", totalNY);
			mpB.put("NY", totalBY);
			// end
			// Đang xử lý
			List<NicUploadJob> yearNC = uploadJobService
					.getListByStatusAndYear(new String[] {
							NicUploadJob.RECORD_STATUS_INITIAL,
							NicUploadJob.RECORD_STATUS_IN_PROGRESS,
							NicUploadJob.RECORD_STATUS_COMPLETED }, Calendar
							.getInstance().get(Calendar.YEAR), 1);
			List<NicUploadJob> yearBC = uploadJobService
					.getListByStatusAndYear(new String[] {
							NicUploadJob.RECORD_STATUS_INITIAL,
							NicUploadJob.RECORD_STATUS_IN_PROGRESS,
							NicUploadJob.RECORD_STATUS_COMPLETED }, Calendar
							.getInstance().get(Calendar.YEAR), 0);
			int totalNC = yearNC != null ? yearNC.size() : 0;
			int totalBC = yearBC != null ? yearBC.size() : 0;
			mpN.put("NC", totalNC);
			mpB.put("NC", totalBC);
			dtoN.setDuLieuCTH(mpN);
			dtoB.setDuLieuCTH(mpB);
			lsPs.add(dtoN);
			lsPs.add(dtoB);
			// end
		} catch (Exception e) {
			e.printStackTrace();
		}
		return lsPs;
	}

	@ResponseBody
	@RequestMapping(value = "/loadProcess")
	public int[] loadProcess(ModelMap model, HttpServletRequest request) {
		int[] dataN = new int[2];
		// Không được cấp
		List<NicUploadJob> yearNowN = uploadJobService.getListByStatusAndYear(
				new String[] { NicUploadJob.RECORD_STATUS_REJECTED }, 2019, 1);
		List<NicUploadJob> yearBeforeN = uploadJobService
				.getListByStatusAndYear(
						new String[] { NicUploadJob.RECORD_STATUS_REJECTED },
						2019, 0);
		if (yearNowN != null) {
			dataN[0] = yearNowN.size();
		} else {
			dataN[0] = 0;
		}
		if (yearBeforeN != null) {
			dataN[1] = yearBeforeN.size();
		} else {
			dataN[1] = 0;
		}

		return dataN;
	}

	@RequestMapping(value = "/issuance")
	public ModelAndView issuance(
			@ModelAttribute(value = "formData") InvestigationAssignmentData investigationAssignmentData,
			WebRequest request, HttpServletRequest httpRequest, ModelMap model) {

		try {
			JSONObject obj = new JSONObject();
			List<SiteRepository> listSite = siteService.findByAllGroup();
			model.addAttribute("namHienTai",
					Calendar.getInstance().get(Calendar.YEAR));
			model.addAttribute("namTruocDo",
					Calendar.getInstance().get(Calendar.YEAR) - 1);
			String listSiteCode = "";
			String listSiteId = "";
			for (int i = 0; i < listSite.size(); i++) {
				int[] data = new int[2];
				listSiteCode += listSite.get(i).getSiteName();
				listSiteId += listSite.get(i).getSiteId();
				if (i != listSite.size() - 1) {
					listSiteCode += ",";
					listSiteId += ",";
				}
				List<NicDocumentData> dsNow = documentDataService
						.findAllBySiteCodeAndStage(listSite.get(i).getSiteId(),
								Constants.CODE_STATUS_ISUANCE, Calendar
										.getInstance().get(Calendar.YEAR), 1);
				List<NicDocumentData> dsBefore = documentDataService
						.findAllBySiteCodeAndStage(listSite.get(i).getSiteId(),
								Constants.CODE_STATUS_ISUANCE, Calendar
										.getInstance().get(Calendar.YEAR), 0);
				if (dsNow != null) {
					data[0] = dsNow.size();
				} else {
					data[0] = 0;
				}
				if (dsBefore != null) {
					data[1] = dsBefore.size();
				} else {
					data[1] = 0;
				}
				obj.put(listSite.get(i).getSiteId(), data);
				// Số lượng hộ chiếu chưa cấp
				List<NicDocumentData> countNow = documentDataService
						.findAllBySiteCodeAndStage(null,
								Constants.CODE_STATUS_PERSONALIZED, Calendar
										.getInstance().get(Calendar.YEAR), 1);
				List<NicDocumentData> countBefore = documentDataService
						.findAllBySiteCodeAndStage(null,
								Constants.CODE_STATUS_PERSONALIZED, Calendar
										.getInstance().get(Calendar.YEAR), 0);
				if (countNow != null) {
					model.addAttribute("countNow", countNow.size());
				} else {
					model.addAttribute("countNow", 0);
				}
				if (countBefore != null) {
					model.addAttribute("countBefore", countBefore.size());
				} else {
					model.addAttribute("countBefore", 0);
				}
			}
			// Lấy kết quả trong csdl để tạo biểu đồ
			model.addAttribute("arrSite", listSiteCode);
			model.addAttribute("arrSiteId", listSiteId);
			model.addAttribute("ttBieuDo", obj.toString());
			ModelAndView modelAndView = new ModelAndView(
					"central.capphathochieu.show");
			modelAndView.addObject("formData", investigationAssignmentData);
			return modelAndView;
		} catch (Exception e) {

			e.printStackTrace();
			System.out.println(e.getMessage());
		}

		return null;
	}

	@RequestMapping(value = "/searchxnc")
	public ModelAndView searchxnc(
			@ModelAttribute(value = "formData") InvestigationAssignmentData investigationAssignmentData,
			WebRequest request, HttpServletRequest httpRequest, ModelMap model) {

		try {
			JSONObject obj = new JSONObject();
			JSONObject objXNC = new JSONObject();
			List<BorderGate> listGate = borderGateService
					.findAllBorderGate(null);
			int[] dataXC = new int[listGate.size()];
			int[] dataNC = new int[listGate.size()];
			List<Double> tbGate = new ArrayList<Double>();
			List<Integer> totalXN = new ArrayList<Integer>();
			for (BorderGate gate : listGate) {
				obj.put(gate.getCode(), gate.getName());
			}
			int i = 0;
			Iterator<String> itr = obj.keys();
			int totalXC = 0;
			int totalNC = 0;
			while (itr.hasNext()) {
				String key = itr.next();
				/* Xuất cảnh */
				dataXC[i] = immiService.totalImmiByBorder(IMMI_TYPE_XC, key);
				/*
				 * List<ImmiHistory> listXC =
				 * immiService.findAllByTypeOrGate(IMMI_TYPE_XC, key); if(listXC
				 * != null){ dataXC[i] = listXC.size(); }else{ dataXC[i] = 0; }
				 */
				/* Nhập cảnh */
				dataNC[i] = immiService.totalImmiByBorder(IMMI_TYPE_NC, key);
				/*
				 * List<ImmiHistory> listNC =
				 * immiService.findAllByTypeOrGate(IMMI_TYPE_NC, key); if(listNC
				 * != null){ dataNC[i] = listNC.size(); }else{ dataNC[i] = 0; }
				 */
				Double tb = (double) ((dataXC[i] + dataNC[i]) / 2);
				totalXC += dataXC[i];
				totalNC += dataNC[i];
				tbGate.add(tb);
				i++;
			}
			totalXN.add(totalNC);
			totalXN.add(totalXC);
			objXNC.put("XC", dataXC);
			objXNC.put("NC", dataNC);
			ModelAndView modelAndView = new ModelAndView(
					"central.kiemsoatxnc.show");
			modelAndView.addObject("dsGate", obj.toString());
			modelAndView.addObject("tbGate", tbGate);
			modelAndView.addObject("totalXN", totalXN);
			modelAndView.addObject("dataGate", objXNC.toString());
			modelAndView.addObject("formData", investigationAssignmentData);
			return modelAndView;
		} catch (Exception e) {

			e.printStackTrace();
			System.out.println(e.getMessage());
		}

		return null;
	}

	@RequestMapping(value = "/statistical")
	public ModelAndView statistical(
			@ModelAttribute(value = "formData") InvestigationAssignmentData investigationAssignmentData,
			WebRequest request, HttpServletRequest httpRequest, ModelMap model) {

		try {

			ModelAndView modelAndView = new ModelAndView("central.thongke.show");
			modelAndView.addObject("formData", investigationAssignmentData);
			List<ReportProcessDto> report = ThongkeDulieuXuLy();
			if (report != null && report.size() > 0) {
				for (ReportProcessDto rp : report) {
					if (rp.getSiteGroup().equals(CODE_TTXL_MIENBAC)) {
						modelAndView.addObject("reportMB", rp);
					} else if (rp.getSiteGroup().equals(CODE_TTXL_MIENTRUNG)) {
						modelAndView.addObject("reportMT", rp);
					} else {
						modelAndView.addObject("reportMN", rp);
					}
				}

			} else {
				modelAndView.addObject("reportMB", null);
				modelAndView.addObject("reportMT", null);
				modelAndView.addObject("reportMN", null);
			}

			RptStatisticsTransmitDataDto rptStatic = DuLieuTruyenNhan();
			modelAndView.addObject("rptStatic", rptStatic);

			return modelAndView;
		} catch (Exception e) {

			e.printStackTrace();
			System.out.println(e.getMessage());
		}

		return null;
	}

	@RequestMapping(value = "/transmission")
	public ModelAndView transmission(
			@ModelAttribute(value = "formData") InvestigationAssignmentData investigationAssignmentData,
			WebRequest request, HttpServletRequest httpRequest, ModelMap model) {

		try {

			ModelAndView modelAndView = new ModelAndView(
					"central.thongKeTruyenNhan.show");
			modelAndView.addObject("formData", investigationAssignmentData);
			modelAndView.addObject("transmissPa", this.truyenNhanDuLieu("PA"));
			modelAndView.addObject("transmissA", this.truyenNhanDuLieu("A"));
			modelAndView.addObject("transmissIn", this.truyenNhanDuLieu("IN"));
			modelAndView
					.addObject("transmissA08", this.truyenNhanDuLieu("A08"));
			modelAndView.addObject("transmissFA08", this.receivedDataFromA08());
			RptStatisticsTransmitDataDto rptStatic = DuLieuTruyenNhan();
			modelAndView.addObject("rptStatic", rptStatic);

			return modelAndView;
		} catch (Exception e) {

			e.printStackTrace();
			System.out.println(e.getMessage());
		}

		return null;
	}

	private TransmissionStatistics receivedDataFromA08() {
		TransmissionStatistics transStatistics = null;
		List<Integer> counts = null;
		try {
			counts = nicTransactionService.getCountTransmissFromA08();
		} catch (Exception e) {
			e.printStackTrace();
		}
		transStatistics = new TransmissionStatistics();
		if (counts != null && counts.size() == 5) {
			transStatistics.setCountWaintTran(counts.get(1));
			transStatistics.setCountWaintPassport(counts.get(0));
			transStatistics.setCountReceiveTran(counts.get(3));
			transStatistics.setCountReceivePassport(counts.get(2));
			transStatistics.setCountError(counts.get(4));
		} else {
			transStatistics.setCountWaintTran(0);
			transStatistics.setCountWaintPassport(0);
			transStatistics.setCountReceiveTran(0);
			transStatistics.setCountReceivePassport(0);
			transStatistics.setCountError(0);
		}
		return transStatistics;
	}

	public List<ReportProcessDto> ThongkeDulieuXuLy() {
		List<ReportProcessDto> lstresult = new ArrayList<ReportProcessDto>();
		try {

			// ======= THEO DÕI XỬ LÝ HỒ SƠ
			List<SiteGroups> lstsiteG = siteService.getListGroupByLevel("1");
			int total = 0;
			int approved = 0;
			int reject = 0;
			int outOfDate = 0;
			int aboutToExpire = 0;
			String siteN = "";

			List<CountPassport> totalD = nicTransactionService
					.totalHosoTrongNgay();
			List<CountPassport> approvedD = nicTransactionService
					.slhosoDuyetCap();
			List<CountPassport> rejectD = nicTransactionService.slhosoTuChoi();
			List<CountPassport> aboutToExpireP = nicTransactionService
					.slhosoSapHetHan();
			List<CountPassport> outOfDateP = nicTransactionService
					.slhosoQuaHan();

			if (lstsiteG != null && lstsiteG.size() > 0) {
				// Lấy dữ liệu siteR theo siteGroupID
				for (SiteGroups site : lstsiteG) {
					ReportProcessDto report = new ReportProcessDto();
					if (!site.getGroupId().equals(CODE_TTXL_MIENNAM)) {
						report.setSiteGroup(site.getGroupId());
					} else {
						siteN = site.getGroupId();
					}
					ReportProcessTranDto dto = new ReportProcessTranDto();
					dto.setAmountTotal(0);
					dto.setAmountApprove(0);
					dto.setAmountReject(0);
					dto.setAmountAboutToExpire(0);
					dto.setAmountOutOfDate(0);
					if (totalD != null && totalD.size() > 0) {
						for (CountPassport cp : totalD) {
							if (cp.getRegSite().equals(site.getGroupId())) {
								if (!site.getGroupId()
										.equals(CODE_TTXL_MIENNAM)) {
									dto.setAmountTotal(cp.getTotal());
								} else {
									total = cp.getTotal();
								}
								break;
							}
						}
					}

					if (approvedD != null && approvedD.size() > 0) {
						for (CountPassport cp : approvedD) {
							if (cp.getRegSite().equals(site.getGroupId())) {
								if (!site.getGroupId()
										.equals(CODE_TTXL_MIENNAM)) {
									dto.setAmountApprove(cp.getTotal());
								} else {
									approved = cp.getTotal();
								}
								break;
							}
						}
					}

					if (rejectD != null && rejectD.size() > 0) {
						for (CountPassport cp : rejectD) {
							if (cp.getRegSite().equals(site.getGroupId())) {
								if (!site.getGroupId()
										.equals(CODE_TTXL_MIENNAM)) {
									dto.setAmountReject(cp.getTotal());
								} else {
									reject = cp.getTotal();
								}
								break;
							}
						}
					}

					if (aboutToExpireP != null && aboutToExpireP.size() > 0) {
						for (CountPassport cp : aboutToExpireP) {
							if (cp.getRegSite().equals(site.getGroupId())) {
								if (!site.getGroupId()
										.equals(CODE_TTXL_MIENNAM)) {
									dto.setAmountAboutToExpire(cp.getTotal());
								} else {
									aboutToExpire = cp.getTotal();
								}
								break;
							}
						}
					}

					if (outOfDateP != null && outOfDateP.size() > 0) {
						for (CountPassport cp : outOfDateP) {
							if (cp.getRegSite().equals(site.getGroupId())) {
								if (!site.getGroupId()
										.equals(CODE_TTXL_MIENNAM)) {
									dto.setAmountOutOfDate(cp.getTotal());
								} else {
									outOfDate = cp.getTotal();
								}
								break;
							}
						}
					}
					if (!site.getGroupId().equals(CODE_TTXL_MIENNAM)) {
						report.setDto(dto);
						lstresult.add(report);
					}
					/*
					 * List<SiteRepository> lstsiteR =
					 * siteService.findAllByGroupId1(site.getGroupId());
					 * if(lstsiteR != null && lstsiteR.size() > 0){
					 * for(SiteRepository sR : lstsiteR){ //Lấy dữ liệu NicTran
					 * theo siteR Date date = new Date(); List<NicTransaction>
					 * lstTranNow =
					 * nicTransactionService.getListTransactionBySiteCode
					 * (sR.getSiteId(), date); if(lstTranNow != null &&
					 * lstTranNow.size() > 0){ for(NicTransaction trans :
					 * lstTranNow) { NicUploadJob nicUpload =
					 * nicUploadJobService
					 * .getUploadJobByTransactionIdSinger(null
					 * ,trans.getTransactionId()); if(nicUpload != null){
					 * if(StringUtils.isNotEmpty(trans.getPackageID())){
					 * if(total == 0) total = 1; dto.setAmountTotal(total);
					 * total++; if(nicUpload.getInvestigationStatus() != null){
					 * if
					 * (nicUpload.getInvestigationStatus().equals(NicUploadJob.
					 * RECORD_STATUS_APPROVE_PERSO)){ if(approved == 0) approved
					 * = 1; dto.setAmountApprove(approved); approved++; } else
					 * if
					 * (nicUpload.getInvestigationStatus().equals(NicUploadJob
					 * .RECORD_STATUS_REJECTED)){ if(reject == 0) reject = 1;
					 * dto.setAmountReject(reject); reject++; } } } }
					 * if(nicUpload.getInvestigationStatus() != null){
					 * if(date.compareTo(trans.getDateOfApplication()) > 4 &&
					 * !nicUpload.getInvestigationStatus().equals(NicUploadJob.
					 * RECORD_STATUS_APPROVE_PERSO)){ if(outOfDate == 0)
					 * outOfDate = 1; dto.setAmountOutOfDate(outOfDate);
					 * outOfDate++; }
					 * 
					 * if(trans.getEstDateOfCollection().compareTo(date) > 0 &&
					 * !nicUpload.getInvestigationStatus().equals(NicUploadJob.
					 * RECORD_STATUS_APPROVE_PERSO)){ if(aboutToExpire == 0)
					 * aboutToExpire = 1;
					 * dto.setAmountAboutToExpire(aboutToExpire);
					 * aboutToExpire++; } } } report.setDto(dto); } } }
					 * lstresult.add(report);
					 */
				}

				ReportProcessDto report = new ReportProcessDto();
				report.setSiteGroup(siteN);
				ReportProcessTranDto dto = new ReportProcessTranDto();
				dto.setAmountTotal(total);
				dto.setAmountApprove(approved);
				dto.setAmountReject(reject);
				dto.setAmountAboutToExpire(aboutToExpire);
				dto.setAmountOutOfDate(outOfDate);
				report.setDto(dto);
				lstresult.add(report);
			}
		} catch (Exception ex) {

		}

		return lstresult;
	}

	public List<ReportProcessDto> ThongkeDulieuXuLyBieudo() {
		List<ReportProcessDto> lstresult = new ArrayList<ReportProcessDto>();
		try {

			// ======= THEO DÕI XỬ LÝ HỒ SƠ
			List<SiteGroups> lstsiteG = siteService.getListGroupByLevel("1");
			int total = 0;
			int approved = 0;
			String siteN = "";

			List<CountPassport> sohschuaxl = nicTransactionService.sohschuaxl();
			List<CountPassport> sohsdaxl = nicTransactionService.sohsdaxl();

			if (lstsiteG != null && lstsiteG.size() > 0) {
				// Lấy dữ liệu siteR theo siteGroupID
				for (SiteGroups site : lstsiteG) {
					ReportProcessDto report = new ReportProcessDto();
					if (!site.getGroupId().equals(CODE_TTXL_MIENNAM)) {
						report.setSiteGroup(site.getGroupId());
					} else {
						siteN = site.getGroupId();// =18A
					}
					ReportProcessTranDto dto = new ReportProcessTranDto();
					dto.setAmountTotal(0);
					dto.setAmountApprove(0);
					dto.setAmountReject(0);
					dto.setAmountAboutToExpire(0);
					dto.setAmountOutOfDate(0);
					if (sohschuaxl != null && sohschuaxl.size() > 0) {
						for (CountPassport cp : sohschuaxl) {
							if (cp.getRegSite().equals(site.getGroupId())) {
								if (!site.getGroupId()
										.equals(CODE_TTXL_MIENNAM)) {
									dto.setAmountTotal(cp.getTotal());
								} else {
									total = cp.getTotal();
								}
								break;
							}
						}
					}

					if (sohsdaxl != null && sohsdaxl.size() > 0) {
						for (CountPassport cp : sohsdaxl) {
							if (cp.getRegSite().equals(site.getGroupId())) {
								if (!site.getGroupId()
										.equals(CODE_TTXL_MIENNAM)) {
									dto.setAmountApprove(cp.getTotal());
								} else {
									approved = cp.getTotal();
								}
								break;
							}
						}
					}

					if (!site.getGroupId().equals(CODE_TTXL_MIENNAM)) {
						report.setDto(dto);
						lstresult.add(report);
					}
				}

				ReportProcessDto report = new ReportProcessDto();
				report.setSiteGroup(siteN);
				ReportProcessTranDto dto = new ReportProcessTranDto();
				dto.setAmountTotal(total);
				dto.setAmountApprove(approved);
				report.setDto(dto);
				lstresult.add(report);
			}
		} catch (Exception ex) {

		}

		return lstresult;
	}

	public List<PersoProcessDto> duLieuCaTheHoa() {
		List<PersoProcessDto> lsper = new ArrayList<PersoProcessDto>();
		try {
			List<CountPassport> chuaSd = eppInventoryService
					.findByStatus(EppInventory.CHUA_SU_DUNG);
			List<CountPassport> daSd = eppInventoryService
					.findByStatus(EppInventory.DA_IN);
			List<CountPassport> loi = eppInventoryService
					.findByStatus(EppInventory.PHOI_LOI);
			List<CountPassport> hong = eppInventoryService
					.findByStatus(EppInventory.IN_HONG);
			List<EppInventory> dsTTCaThe = eppInventoryService
					.findAllInvestory(null, null);
			if (dsTTCaThe != null) {

				for (EppInventory ep : dsTTCaThe) {
					PersoProcessDto dto = new PersoProcessDto();
					Integer countI = 0;
					Integer countD = 0;
					Integer countL = 0;
					dto.setSiteGroups(ep.getCode());
					if (chuaSd != null) {
						for (CountPassport cp : chuaSd) {
							if (ep.getCode().equals(cp.getRegSite())) {
								countI += cp.getTotal();
							}
						}
					}
					if (daSd != null) {
						for (CountPassport sd : daSd) {
							if (ep.getCode().equals(sd.getRegSite())) {
								countD += sd.getTotal();
							}
						}
					}
					if (loi != null) {
						for (CountPassport lh : loi) {
							if (ep.getCode().equals(lh.getRegSite())) {
								countL += lh.getTotal();
							}
						}
					}
					if (hong != null) {
						for (CountPassport lh : hong) {
							if (ep.getCode().equals(lh.getRegSite())) {
								countL += lh.getTotal();
							}
						}
					}
					Map<String, Integer> duLieuCTH = new HashMap<String, Integer>();
					duLieuCTH.put(EppInventory.CHUA_SU_DUNG, countI);
					duLieuCTH.put(EppInventory.DA_IN, countD);
					duLieuCTH.put(EppInventory.IN_HONG, countL);
					dto.setDuLieuCTH(duLieuCTH);
					lsper.add(dto);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return lsper;
	}

	public List<ReportProcessDto> DulieuPhoiIn() {

		List<ReportProcessDto> lstresult = new ArrayList<ReportProcessDto>();
		try {
			List<CountPassport> chuaSd = eppInventoryService
					.findByStatus(EppInventory.CHUA_SU_DUNG);
			List<CountPassport> daSd = eppInventoryService
					.findByStatus(EppInventory.DA_IN);
			List<CountPassport> loi = eppInventoryService
					.findByStatus(EppInventory.PHOI_LOI);
			List<CountPassport> hong = eppInventoryService
					.findByStatus(EppInventory.IN_HONG);

			ReportProcessDto dtoMN = new ReportProcessDto();
			ImmiHistoryCountDto immiDtoMN = new ImmiHistoryCountDto();
			immiDtoMN.setNhapCanh(0);
			immiDtoMN.setViphamNC(0);
			immiDtoMN.setXuatCanh(0);

			List<SiteGroups> lstsiteG = siteService.getListGroupByLevel("1");
			if (lstsiteG != null && lstsiteG.size() > 0) {
				for (SiteGroups bor : lstsiteG) {
					if (!bor.getGroupId().equals(CODE_TTXL_MIENTRUNG)) {
						ReportProcessDto dto = new ReportProcessDto();
						dto.setSiteGroup(bor.getGroupId());
						ImmiHistoryCountDto immiDto = new ImmiHistoryCountDto();
						immiDto.setNhapCanh(0);
						immiDto.setViphamNC(0);
						immiDto.setXuatCanh(0);
						if (chuaSd != null && chuaSd.size() > 0) {
							for (CountPassport cp : chuaSd) {
								if (cp.getRegSite().equals(bor.getGroupId())) {
									immiDto.setNhapCanh(cp.getTotal());
									break;
								}
							}
						}
						if (daSd != null && daSd.size() > 0) {
							for (CountPassport cp1 : daSd) {
								if (cp1.getRegSite().equals(bor.getGroupId())) {
									immiDto.setViphamNC(cp1.getTotal());
									break;
								}
							}
						}
						if (loi != null && loi.size() > 0) {
							for (CountPassport cp : loi) {
								if (cp.getRegSite().equals(bor.getGroupId())) {
									immiDto.setXuatCanh(cp.getTotal());
									break;
								}
							}
						}
						if (hong != null && hong.size() > 0) {
							for (CountPassport cp1 : hong) {
								if (cp1.getRegSite().equals(bor.getGroupId())) {
									int amout = immiDto.getXuatCanh();
									if (cp1.getTotal() != null) {
										immiDto.setXuatCanh(amout
												+ cp1.getTotal());
										break;
									}
								}
							}
						}

						dto.setImmi(immiDto);
						lstresult.add(dto);
					}
				}
			}
		} catch (Exception ex) {

		}

		return lstresult;
	}

	public List<ReportProcessDto> DulieuXuatNhapCanh() {

		List<ReportProcessDto> lstresult = new ArrayList<ReportProcessDto>();
		try {
			List<CountPassport> borderGate_NC = immiService.borderGateNC();
			List<CountPassport> borderGate_NCVP = immiService.borderGateNCVP();
			List<CountPassport> borderGate_XC = immiService.borderGateXC();
			List<CountPassport> borderGate_XCVP = immiService.borderGateXCVP();

			List<BorderGate> lstBorder = borderGateService
					.findAllBorderGate("");
			if (lstBorder != null && lstBorder.size() > 0) {
				for (BorderGate bor : lstBorder) {
					if (bor.getCode().equals("SNB")
							|| bor.getCode().equals("STS")
							|| bor.getCode().equals("SDN")
							|| bor.getCode().equals("SPQ")
							|| bor.getCode().equals("SCR")) {
						ReportProcessDto dto = new ReportProcessDto();
						dto.setSiteGroup(bor.getCode());
						ImmiHistoryCountDto immiDto = new ImmiHistoryCountDto();
						immiDto.setNhapCanh(0);
						immiDto.setViphamNC(0);
						immiDto.setXuatCanh(0);
						immiDto.setViphamXC(0);
						if (borderGate_NC != null && borderGate_NC.size() > 0) {
							for (CountPassport cp : borderGate_NC) {
								if (cp.getRegSite().equals(bor.getCode())) {
									immiDto.setNhapCanh(cp.getTotal());
									break;
								}
							}
						}
						if (borderGate_NCVP != null
								&& borderGate_NCVP.size() > 0) {
							for (CountPassport cp1 : borderGate_NCVP) {
								if (cp1.getRegSite().equals(bor.getCode())) {
									immiDto.setViphamNC(cp1.getTotal());
									break;
								}
							}
						}
						if (borderGate_XC != null && borderGate_XC.size() > 0) {
							for (CountPassport cp : borderGate_XC) {
								if (cp.getRegSite().equals(bor.getCode())) {
									immiDto.setXuatCanh(cp.getTotal());
									break;
								}
							}
						}
						if (borderGate_XCVP != null
								&& borderGate_XCVP.size() > 0) {
							for (CountPassport cp1 : borderGate_XCVP) {
								if (cp1.getRegSite().equals(bor.getCode())) {
									immiDto.setViphamXC(cp1.getTotal());
									break;
								}
							}
						}

						dto.setImmi(immiDto);
						lstresult.add(dto);
					}
				}
			}
		} catch (Exception ex) {

		}

		return lstresult;
	}

	public List<ReportProcessDto> DulieuTraHoChieu() {
		List<ReportProcessDto> lstresult = new ArrayList<ReportProcessDto>();
		try {
			List<CountPassport> countIssue = nicTransactionService
					.issuePassportList();
			List<CountPassport> countNotIssue = nicTransactionService
					.noneIssuePassportList();
			if (countIssue != null && countIssue.size() > 0) {
				for (CountPassport cp : countIssue) {
					ReportProcessDto dto = new ReportProcessDto();
					dto.setAmountIssueNot(0);
					dto.setSiteGroup(cp.getRegSite());
					dto.setAmountIssue(cp.getTotal());
					lstresult.add(dto);
				}
			}

			if (countNotIssue != null && countNotIssue.size() > 0) {
				Boolean check = true;
				for (CountPassport cp1 : countNotIssue) {
					for (ReportProcessDto re : lstresult) {
						if (re.getSiteGroup().equals(cp1.getRegSite())) {
							re.setAmountIssueNot(cp1.getTotal());
							check = false;
							break;
						}
					}
					if (check) {
						ReportProcessDto dtoN = new ReportProcessDto();
						dtoN.setAmountIssue(0);
						dtoN.setSiteGroup(cp1.getRegSite());
						dtoN.setAmountIssueNot(cp1.getTotal());
						lstresult.add(dtoN);
					}
				}
			}
			this.createCode();
			Collections.sort(lstresult, new Comparator<ReportProcessDto>() {
				@Override
				public int compare(ReportProcessDto o1, ReportProcessDto o2) {
					// TODO Auto-generated method stub
					return generator(o1.getSiteGroup()).compareTo(
							generator(o2.getSiteGroup()));
				}

			});

		} catch (Exception ex) {

		}
		return lstresult;
	}

	public RptStatisticsTransmitDataDto DuLieuTruyenNhan() {
		RptStatisticsTransmitDataDto res = new RptStatisticsTransmitDataDto();
		List<RptDetailDto> lstDetail = new ArrayList<RptDetailDto>();
		try {
			String[] lstBorder = { "NB", "DN", "TSN", "PQ", "CR", "BQP-MC" };
			String[] lstBorder1 = { "SNB", "SDN", "STS", "SPQ", "SCR", "BQP-MC" };
			for (int i = 0; lstBorder.length > i; i++) {
				RptDetailDto detail = new RptDetailDto();
				detail.setSiteCode(lstBorder[i]);
				detail.setGuidl_Nb(0);
				detail.setNhandl_Nb(0);
				detail.setGuihc_Bqp(0);
				detail.setGuimh_Bqp(0);
				List<RptStatisticsTransmitData> lstRpt = rptStatisticsTransmitDataSerivce
						.findBySiteCode(lstBorder1[i]);
				if (lstRpt != null && lstRpt.size() > 0) {
					for (RptStatisticsTransmitData rpt : lstRpt) {
						if (rpt.getType().equals(
								RptStatisticsTransmitData.GUI_DL_XNC)) {
							detail.setGuidl_Nb(rpt.getTotal());
						} else if (rpt.getType().equals(
								RptStatisticsTransmitData.NHAN_DL_XNC)) {
							detail.setNhandl_Nb(rpt.getTotal());
						} else if (rpt.getType().equals(
								RptStatisticsTransmitData.GUI_TT_HC)) {
							detail.setGuihc_Bqp(rpt.getTotal());
						} else if (rpt.getType().equals(
								RptStatisticsTransmitData.GUI_GT_MH)) {
							detail.setGuimh_Bqp(rpt.getTotal());
						}
					}
				}
				lstDetail.add(detail);
			}
			res.setLstRpt(lstDetail);
			List<RptStatisticsTransmitData> lstRptKQ = rptStatisticsTransmitDataSerivce
					.findBySiteCode("A08");
			if (lstRptKQ != null && lstRptKQ.size() > 0) {
				res.setTrakq(lstRptKQ.get(0).getTotal());
			} else {
				res.setTrakq(0);
			}

			List<RptStatisticsTransmitData> lstRptCA = rptStatisticsTransmitDataSerivce
					.findBySiteCode("BNG");
			if (lstRptCA != null && lstRptCA.size() > 0) {
				res.setKyca_bng(lstRptCA.get(0).getTotal());
			} else {
				res.setKyca_bng(0);
			}
		} catch (Exception ex) {

		}
		return res;
	}

	public List<ReportProcessDto> DulieuCathehoa() {
		List<ReportProcessDto> lstresult = new ArrayList<ReportProcessDto>();
		try {
			List<CountPassport> countIssue = nicTransactionService
					.printedPassport();
			List<CountPassport> countNotIssue = nicTransactionService
					.nonePrintPassport();
			List<SiteGroups> lstsiteG = siteService.getListGroupByLevel("1");

			ReportProcessDto dtoMN = new ReportProcessDto();
			for (SiteGroups site : lstsiteG) {
				ReportProcessDto dto = new ReportProcessDto();
				if (!site.getGroupId().equals(CODE_TTXL_MIENNAM)) {
					dto.setSiteGroup(site.getGroupId());
				} else {
					dtoMN.setSiteGroup(site.getGroupId());
				}

				if (countIssue != null && countIssue.size() > 0) {
					for (CountPassport cp : countIssue) {
						for (CountPassport cp1 : countNotIssue) {
							if (cp.getRegSite().equals(cp1.getRegSite())
									&& site.getGroupId().equals(
											cp1.getRegSite())) {
								if (!cp.getRegSite().equals(CODE_TTXL_MIENNAM)) {
									dto.setAmountIssue(cp.getTotal());
									dto.setAmountIssueNot(cp1.getTotal());
									lstresult.add(dto);
									break;
								} else {
									dtoMN.setAmountIssue(cp.getTotal());
									dtoMN.setAmountIssueNot(cp1.getTotal());
								}
							}
						}
					}
				}
			}
			lstresult.add(dtoMN);
		} catch (Exception ex) {

		}
		return lstresult;
	}

	@ResponseBody
	@RequestMapping(value = "/thongkeXLHS", method = RequestMethod.GET)
	public List<ReportProcessDto> thongkeXLHS() {
		List<ReportProcessDto> dulieuXL = ThongkeDulieuXuLy();

		return dulieuXL;
	}

	@ResponseBody
	@RequestMapping(value = "/dulieutn", method = RequestMethod.GET)
	public RptStatisticsTransmitDataDto dulieutn() {
		RptStatisticsTransmitDataDto dulieutn = DuLieuTruyenNhan();
		return dulieutn;
	}

	@ResponseBody
	@RequestMapping(value = "/bieuDoXuatNhapCanh", method = RequestMethod.GET)
	public HighchartsDto bieuDoXuatNhapCanh() {
		HighchartsDto hightchartDto = new HighchartsDto();
		List<ReportProcessDto> lst = DulieuXuatNhapCanh();
		if (lst != null && lst.size() > 0) {
			TitleDto title = new TitleDto();
			title.setText("");
			hightchartDto.setTitle(title);
			List<String> categories = new ArrayList<String>();
			List<Integer> nhapcanh = new ArrayList<Integer>();
			List<Integer> xuatcanh = new ArrayList<Integer>();
			List<Integer> vp_nhapcanh = new ArrayList<Integer>();
			List<Integer> vp_xuatcanh = new ArrayList<Integer>();
			DataLabelsDto dataLabel = new DataLabelsDto();
			dataLabel.setEnabled(true);

			for (ReportProcessDto dto : lst) {
				categories.add(dto.getSiteGroup());
				nhapcanh.add(dto.getImmi().getNhapCanh());
				xuatcanh.add(dto.getImmi().getXuatCanh());
				vp_nhapcanh.add(dto.getImmi().getViphamNC());
				vp_xuatcanh.add(dto.getImmi().getViphamXC());
			}

			SeriesDto series_vpxc = new SeriesDto();
			series_vpxc.setName("Vi phạm XC");
			series_vpxc.setColor("#f7a35c");
			series_vpxc.setStack("XC");
			series_vpxc.setData(vp_xuatcanh);
			series_vpxc.setDataLabels(dataLabel);

			SeriesDto series_xc = new SeriesDto();
			series_xc.setName("Xuất cảnh");
			series_xc.setColor("#90ed7d");
			series_xc.setStack("XC");
			series_xc.setData(xuatcanh);
			series_xc.setDataLabels(dataLabel);

			SeriesDto series_vpnc = new SeriesDto();
			series_vpnc.setName("Vi phạm NC");
			series_vpnc.setColor("#f7a35c");
			series_vpnc.setStack("NC");
			series_vpnc.setData(vp_nhapcanh);
			series_vpnc.setDataLabels(dataLabel);

			SeriesDto series_nc = new SeriesDto();
			series_nc.setName("Nhập cảnh");
			series_nc.setColor("#7cb5ec");
			series_nc.setStack("NC");
			series_nc.setData(nhapcanh);
			series_nc.setDataLabels(dataLabel);

			hightchartDto.getSeries().add(series_vpxc);
			hightchartDto.getSeries().add(series_xc);
			hightchartDto.getSeries().add(series_vpnc);
			hightchartDto.getSeries().add(series_nc);
			XAxisDto xAxis = new XAxisDto();
			xAxis.setCategories(categories);
			hightchartDto.setxAxis(xAxis);
		}
		return hightchartDto;
	}

	@ResponseBody
	@RequestMapping(value = "/bieuDoTraHoChieu", method = RequestMethod.GET)
	public HighchartsDto bieuDoTraHoChieu() {
		HighchartsDto hightchartDto = new HighchartsDto();
		List<ReportProcessDto> lst = DulieuTraHoChieu();
		if (lst != null && lst.size() > 0) {
			TitleDto title = new TitleDto();
			title.setText("");
			hightchartDto.setTitle(title);
			List<String> categories = new ArrayList<String>();
			List<Integer> chuatra = new ArrayList<Integer>();
			List<Integer> datra = new ArrayList<Integer>();

			DataLabelsDto dataLabel = new DataLabelsDto();
			dataLabel.setEnabled(true);

			for (ReportProcessDto dto : lst) {
				categories.add(dto.getSiteGroup());
				chuatra.add(dto.getAmountIssueNot());
				datra.add(dto.getAmountIssue());
			}

			SeriesDto series_ct = new SeriesDto();
			series_ct.setName("Chưa trả");
			series_ct.setColor("#7cb5ec");
			series_ct.setData(chuatra);
			series_ct.setDataLabels(dataLabel);

			SeriesDto series_dt = new SeriesDto();
			series_dt.setName("Đã trả");
			series_dt.setColor("#90ed7d");
			series_dt.setData(datra);
			series_dt.setDataLabels(dataLabel);

			hightchartDto.getSeries().add(series_ct);
			hightchartDto.getSeries().add(series_dt);
			XAxisDto xAxis = new XAxisDto();
			xAxis.setCategories(categories);
			hightchartDto.setxAxis(xAxis);
		}
		return hightchartDto;
	}

	@ResponseBody
	@RequestMapping(value = "/bieuDoCaTheHoa1", method = RequestMethod.GET)
	public HighchartsDto bieuDoCaTheHoa1() {

		HighchartsDto hightchartDto = new HighchartsDto();
		List<ReportProcessDto> lst = DulieuCathehoa();
		if (lst != null && lst.size() > 0) {
			TitleDto title = new TitleDto();
			title.setText("");
			hightchartDto.setTitle(title);
			List<String> categories = new ArrayList<String>();
			List<Integer> chuain = new ArrayList<Integer>();
			List<Integer> dain = new ArrayList<Integer>();

			DataLabelsDto dataLabel = new DataLabelsDto();
			dataLabel.setEnabled(true);

			for (ReportProcessDto dto : lst) {
				categories.add(dto.getSiteGroup());
				chuain.add(dto.getAmountIssueNot());
				dain.add(dto.getAmountIssue());
			}

			SeriesDto series_ct = new SeriesDto();
			series_ct.setName("Chưa in");
			series_ct.setColor("#7cb5ec");
			series_ct.setData(chuain);
			series_ct.setDataLabels(dataLabel);

			SeriesDto series_dt = new SeriesDto();
			series_dt.setName("Đã in");
			series_dt.setColor("#90ed7d");
			series_dt.setData(dain);
			series_dt.setDataLabels(dataLabel);

			hightchartDto.getSeries().add(series_ct);
			hightchartDto.getSeries().add(series_dt);
			XAxisDto xAxis = new XAxisDto();
			xAxis.setCategories(categories);
			hightchartDto.setxAxis(xAxis);
		}
		return hightchartDto;

	}

	@ResponseBody
	@RequestMapping(value = "/newTransactionProcess", method = RequestMethod.GET)
	public List<NicUploadJobDto> newTransactionProcess() {
		List<NicUploadJobDto> resultModel = new ArrayList<NicUploadJobDto>();
		try {
			List<NicUploadJobDto> resultCXL = nicTransactionService
					.newTransactionProcess();
			if (resultCXL != null && resultCXL.size() > 0) {
				for (NicUploadJobDto nic : resultCXL) {
					nic.setFullName(nic.getFullName().toUpperCase().trim());
					nic.setStageList("Chưa xử lý");
					resultModel.add(nic);
				}
			}

			List<NicUploadJobDto> resultDXL = nicTransactionService
					.newTransactionProcessDDXL();
			if (resultDXL != null && resultDXL.size() > 0) {
				for (NicUploadJobDto nic : resultDXL) {
					nic.setFullName(nic.getFullName().toUpperCase().trim());
					BaseModelSingle<NicUploadJob> baseGetUJ = uploadJobService
							.getUploadJobByTransactionIdSinger(null,
									nic.getTransactionId());
					NicUploadJob record = baseGetUJ.getModel();
					// if(record.getInvestigationStatus().equals("40")){
					// NicUploadJob record =
					// uploadJobService.getUploadJobByTransactionIdSinger(null,
					// nic.getTransactionId());
					// if(record.getInvestigationStatus().equals("40")){
					// BaseModelSingle<NicUploadJob> record =
					// uploadJobService.getUploadJobByTransactionIdSinger(null,
					// nic.getTransactionId());
					if (record.getInvestigationStatus().equals("40")) {
						nic.setStageList("Đã xử lý");
					} else {
						nic.setStageList("Đang xử lý");
					}
					resultModel.add(nic);
				}
			}
		} catch (Exception ex) {

		}
		return resultModel;
	}

	@ResponseBody
	@RequestMapping(value = "/logCheckConnection", method = RequestMethod.GET)
	public List<LogCheckConnDto> logCheckConnection() {
		List<LogCheckConnDto> resultModel = new ArrayList<LogCheckConnDto>();
		try {
			List<LogCheckConnection> result = logCheckConnectionService
					.findAllAndOrder("siteName");
			Date date = new Date();
			int timeout = 60;
			try {
				timeout = Integer.parseInt(parametersService.findById(
						new ParametersId("SYSTEM", "LAST_CONNECTION_TIMEOUT"))
						.getParaShortValue());
			} catch (Exception e) {
			}
			long check = date.getTime() - timeout * 1000;
			for (LogCheckConnection logCheckConnection : result) {
				if (logCheckConnection.getLastConnectedDatetime().getTime() <= check) {
					LogCheckConnDto logCheckConnDto = new LogCheckConnDto();
					logCheckConnDto.setSiteName(logCheckConnection
							.getSiteName());
					logCheckConnDto.setStage("Mất kết nối");
					resultModel.add(logCheckConnDto);
				}
			}
		} catch (Exception ex) {

		}
		return resultModel;
	}

	@ResponseBody
	@RequestMapping(value = "/newImmihistory", method = RequestMethod.GET)
	public List<NicUploadJobDto> newImmihistory() {
		List<NicUploadJobDto> result = new ArrayList<NicUploadJobDto>();
		try {
			result = immiService.newImmihistory();
			if (result != null && result.size() > 0) {
				for (NicUploadJobDto nic : result) {
					nic.setFullName(nic.getFullName().toUpperCase().trim());
					if (nic.getImmiType().equals("N")) {
						nic.setStageList("NC");
					} else {
						nic.setStageList("XC");
					}
				}
			}
		} catch (Exception ex) {

		}
		return result;
	}

	@ResponseBody
	@RequestMapping(value = "/bieuDoTheoDoiXLHS", method = RequestMethod.GET)
	public HighchartsDto bieuDoTheoDoiXLHS() {
		HighchartsDto hightchartDto = new HighchartsDto();
		List<ReportProcessDto> lst = ThongkeDulieuXuLyBieudo();
		if (lst != null && lst.size() > 0) {
			TitleDto title = new TitleDto();
			title.setText("");
			hightchartDto.setTitle(title);
			List<String> categories = new ArrayList<String>();
			List<Integer> datas_mb = new ArrayList<Integer>();
			List<Integer> datas_mt = new ArrayList<Integer>();
			List<Integer> datas_mn = new ArrayList<Integer>();

			int chuaxl_mb = 0;
			int cb_mb = 0;
			int dx_mb = 0;

			int chuaxl_mt = 0;
			int cb_mt = 0;
			int dx_mt = 0;

			int chuaxl_mn = 0;
			int cb_mn = 0;
			int dx_mn = 0;

			for (ReportProcessDto dto : lst) {
				if (dto.getSiteGroup() != null
						&& dto.getSiteGroup().equals(CODE_TTXL_MIENBAC)) {
					categories.add(dto.getSiteGroup());
					// categoriesB = dto.getSiteGroup();
					if (dto.getDto().getAmountTotal() > 4)
						cb_mb = dto.getDto().getAmountTotal();
					else
						chuaxl_mb = dto.getDto().getAmountTotal();

					dx_mb = dto.getDto().getAmountApprove();

				} else if (dto.getSiteGroup() != null
						&& dto.getSiteGroup().equals(CODE_TTXL_MIENTRUNG)) {
					// categoriesT = dto.getSiteGroup();
					if (dto.getDto().getAmountTotal() > 4)
						cb_mt = dto.getDto().getAmountTotal();
					else
						chuaxl_mt = dto.getDto().getAmountTotal();

					dx_mt = dto.getDto().getAmountApprove();
				} else {
					categories.add(dto.getSiteGroup());
					// categoriesN = dto.getSiteGroup();
					if (dto.getDto().getAmountTotal() > 4)
						cb_mn = dto.getDto().getAmountTotal();
					else
						chuaxl_mn = dto.getDto().getAmountTotal();

					dx_mn = dto.getDto().getAmountApprove();
				}
			}
			DataLabelsDto dataLabel = new DataLabelsDto();
			dataLabel.setEnabled(true);

			SeriesDto seriesCXL = new SeriesDto();
			seriesCXL.setName("Chưa XL");
			seriesCXL.setColor("#7cb5ec");
			datas_mb.add(chuaxl_mb);
			/* datas_mb.add(chuaxl_mt); */
			datas_mb.add(chuaxl_mn);
			seriesCXL.setData(datas_mb);
			seriesCXL.setDataLabels(dataLabel);

			SeriesDto seriesCB = new SeriesDto();
			seriesCB.setName("Chưa XL (Cảnh báo)");
			seriesCB.setColor("#e22222");
			datas_mt.add(cb_mb);
			/* datas_mt.add(cb_mt); */
			datas_mt.add(cb_mn);
			seriesCB.setData(datas_mt);
			seriesCB.setDataLabels(dataLabel);

			SeriesDto seriesDSD = new SeriesDto();
			seriesDSD.setName("Đã XL");
			seriesDSD.setColor("#90ed7d");
			datas_mn.add(dx_mb);
			/* datas_mn.add(dx_mt); */
			datas_mn.add(dx_mn);
			seriesDSD.setData(datas_mn);
			seriesDSD.setDataLabels(dataLabel);

			hightchartDto.getSeries().add(seriesCXL);
			hightchartDto.getSeries().add(seriesCB);
			hightchartDto.getSeries().add(seriesDSD);
			XAxisDto xAxis = new XAxisDto();
			xAxis.setCategories(categories);
			hightchartDto.setxAxis(xAxis);
		}

		return hightchartDto;
	}

	@ResponseBody
	@RequestMapping(value = "/bieuDoPhoiIn", method = RequestMethod.GET)
	public HighchartsDto bieuDoPhoiIn() {
		HighchartsDto hightchartDto = new HighchartsDto();
		List<ReportProcessDto> lst = DulieuPhoiIn();
		if (lst != null && lst.size() > 0) {
			TitleDto title = new TitleDto();
			title.setText("");
			hightchartDto.setTitle(title);
			List<String> categories = new ArrayList<String>();
			List<Integer> datas_mb = new ArrayList<Integer>();
			List<Integer> datas_mt = new ArrayList<Integer>();
			List<Integer> datas_mn = new ArrayList<Integer>();
			List<Integer> datas_mn1 = new ArrayList<Integer>();

			int chuaxl_mb = 0;
			int cb_mb = 0;
			int dx_mb = 0;
			int lh_mb = 0;

			int chuaxl_mt = 0;
			int cb_mt = 0;
			int dx_mt = 0;
			int lh_mt = 0;

			int chuaxl_mn = 0;
			int cb_mn = 0;
			int dx_mn = 0;
			int lh_mn = 0;
			for (ReportProcessDto dto : lst) {
				categories.add(dto.getSiteGroup());
				if (dto.getSiteGroup().equals(CODE_TTXL_MIENBAC)) {
					// categoriesB = dto.getSiteGroup();
					if (dto.getImmi().getNhapCanh() < 200)
						cb_mb = dto.getImmi().getNhapCanh();
					else
						chuaxl_mb = dto.getImmi().getNhapCanh();

					dx_mb = dto.getImmi().getViphamNC();
					lh_mb = dto.getImmi().getXuatCanh();
				} else if (dto.getSiteGroup().equals(CODE_TTXL_MIENTRUNG)) {
					// categoriesT = dto.getSiteGroup();
					if (dto.getImmi().getNhapCanh() < 200)
						cb_mt = dto.getImmi().getNhapCanh();
					else
						chuaxl_mt = dto.getImmi().getNhapCanh();

					dx_mt = dto.getImmi().getViphamNC();
					lh_mt = dto.getImmi().getXuatCanh();
				} else {
					// categoriesN = dto.getSiteGroup();
					// categoriesT = dto.getSiteGroup();
					if (dto.getImmi().getNhapCanh() < 200)
						cb_mn = dto.getImmi().getNhapCanh();
					else
						chuaxl_mn = dto.getImmi().getNhapCanh();

					dx_mn = dto.getImmi().getViphamNC();
					lh_mn = dto.getImmi().getXuatCanh();
				}
			}
			DataLabelsDto dataLabel = new DataLabelsDto();
			dataLabel.setEnabled(true);

			SeriesDto seriesCXL = new SeriesDto();
			seriesCXL.setName("Chưa SD");
			seriesCXL.setColor("#7cb5ec");
			datas_mb.add(chuaxl_mb);
			// datas_mb.add(chuaxl_mt);
			datas_mb.add(chuaxl_mn);
			seriesCXL.setData(datas_mb);
			seriesCXL.setDataLabels(dataLabel);

			SeriesDto seriesCB = new SeriesDto();
			seriesCB.setName("Chưa SD (CB)");
			seriesCB.setColor("#e22222");
			datas_mt.add(cb_mb);
			// datas_mt.add(cb_mt);
			datas_mt.add(cb_mn);
			seriesCB.setData(datas_mt);
			seriesCB.setDataLabels(dataLabel);

			SeriesDto seriesDSD = new SeriesDto();
			seriesDSD.setName("Đã SD");
			seriesDSD.setColor("#90ed7d");
			datas_mn.add(dx_mb);
			// datas_mn.add(dx_mt);
			datas_mn.add(dx_mn);
			seriesDSD.setData(datas_mn);
			seriesDSD.setDataLabels(dataLabel);

			SeriesDto seriesLH = new SeriesDto();
			seriesLH.setName("Lỗi, hỏng");
			seriesLH.setColor("#434348");
			datas_mn1.add(lh_mb);
			// datas_mn1.add(lh_mt);
			datas_mn1.add(lh_mn);
			seriesLH.setData(datas_mn1);
			seriesLH.setDataLabels(dataLabel);

			hightchartDto.getSeries().add(seriesCXL);
			hightchartDto.getSeries().add(seriesCB);
			hightchartDto.getSeries().add(seriesDSD);
			hightchartDto.getSeries().add(seriesLH);
			XAxisDto xAxis = new XAxisDto();
			xAxis.setCategories(categories);
			hightchartDto.setxAxis(xAxis);
		}

		return hightchartDto;
	}

	@ResponseBody
	@RequestMapping(value = "/bieuDoCaTheHoa", method = RequestMethod.GET)
	public Highcharts1Dto bieuDoCaTheHoa() {
		Highcharts1Dto hightchartDto = new Highcharts1Dto();
		List<PersoProcessDto> lst = duLieuCaTheHoa();
		if (lst != null && lst.size() > 0) {
			TitleDto title = new TitleDto();
			title.setText("");
			hightchartDto.setTitle(title);
			List<String> categories = new ArrayList<String>();
			List<Integer> countCSD = new ArrayList<Integer>();
			List<Integer> countDSD = new ArrayList<Integer>();
			List<Integer> countLH = new ArrayList<Integer>();
			List<Integer> countTB = new ArrayList<Integer>();
			Integer coutI = 0;
			Integer coutD = 0;
			Integer coutH = 0;
			for (PersoProcessDto dto : lst) {
				categories.add(dto.getSiteGroups());
				Integer cCSD = dto.getDuLieuCTH()
						.get(EppInventory.CHUA_SU_DUNG);
				coutI += cCSD;
				countCSD.add(cCSD);
				Integer cDSD = dto.getDuLieuCTH().get(EppInventory.DA_IN);
				coutD += cDSD;
				countDSD.add(cDSD);
				Integer cL_H = dto.getDuLieuCTH().get(EppInventory.IN_HONG);
				coutH += cL_H;
				countLH.add(cL_H);
				countTB.add((cCSD + cDSD + cL_H) / 3);
			}
			DataLabelsDto dataLabel = new DataLabelsDto();
			dataLabel.setEnabled(true);

			Series1Dto seriesCXL = new Series1Dto();
			seriesCXL.setName("Chưa sử dụng");
			seriesCXL.setType("column");
			seriesCXL.setColor("#7cb5ec");
			seriesCXL.setData(countCSD);
			seriesCXL.setStack(EppInventory.CHUA_SU_DUNG);
			seriesCXL.setDataLabels(dataLabel);

			Series1Dto seriesDSD = new Series1Dto();
			seriesDSD.setName("Đã sử dụng");
			seriesDSD.setColor("#90ed7d");
			seriesDSD.setType("column");
			seriesDSD.setData(countDSD);
			seriesDSD.setStack(EppInventory.DA_IN);
			seriesDSD.setDataLabels(dataLabel);

			Series1Dto seriesLH = new Series1Dto();
			seriesLH.setName("Hỏng/Lỗi");
			seriesLH.setColor("#434348");
			seriesLH.setType("column");
			seriesLH.setData(countLH);
			seriesLH.setStack(EppInventory.IN_HONG);
			seriesLH.setDataLabels(dataLabel);

			Series1Dto seriesSp = new Series1Dto();
			seriesSp.setName("Trung bình");
			seriesSp.setType("spline");
			seriesSp.setDataLabels(dataLabel);
			seriesSp.setData(countTB);

			Series1Dto seriesPie = new Series1Dto();
			seriesPie.setName("Tổng số");
			seriesPie.setType("pie");
			seriesPie.setDataLabels(dataLabel);
			List<DataPieDto> dtPie = new ArrayList<DataPieDto>();
			DataPieDto dtoI = new DataPieDto();
			dtoI.setName("Chưa sử dụng");
			dtoI.setY(coutI);
			dtoI.setColor("#7cb5ec");

			DataPieDto dtoD = new DataPieDto();
			dtoD.setName("Đã sử dụng");
			dtoD.setY(coutD);
			dtoD.setColor("#90ed7d");

			DataPieDto dtoH = new DataPieDto();
			dtoH.setName("Hỏng/Lỗi");
			dtoH.setY(coutH);
			dtoH.setColor("#434348");
			dtPie.add(dtoI);
			dtPie.add(dtoD);
			dtPie.add(dtoH);
			seriesPie.setDataPie(dtPie);

			hightchartDto.getSeries().add(seriesCXL);
			hightchartDto.getSeries().add(seriesDSD);
			hightchartDto.getSeries().add(seriesLH);
			hightchartDto.getSeries().add(seriesSp);
			hightchartDto.getSeries().add(seriesPie);
			XAxisDto xAxis = new XAxisDto();
			xAxis.setCategories(categories);
			hightchartDto.setxAxis(xAxis);
		}

		return hightchartDto;
	}

	@RequestMapping(value = "/ttluutru")
	public ModelAndView ttluutru(
			@ModelAttribute(value = "formData") InvestigationAssignmentData investigationAssignmentData,
			WebRequest request, HttpServletRequest httpRequest, ModelMap model) {

		return this.ttluutru1(investigationAssignmentData, request,
				httpRequest, model);
	}

	public ModelAndView ttluutru1(
			@ModelAttribute(value = "formData") InvestigationAssignmentData investigationAssignmentData,
			WebRequest request, HttpServletRequest httpRequest, ModelMap model) {

		try {
			// Lấy key MAIN
			Parameters paramMain = parametersService.getParamDetails(
					com.nec.asia.nic.utils.Constants.PARA_SCOPE_SYSTEM,
					"MAIN_CONNECTION_DATA_CENTER");
			Parameters paramSub = parametersService.getParamDetails(
					com.nec.asia.nic.utils.Constants.PARA_SCOPE_SYSTEM,
					"SUB_CONNECTION_DATA_CENTER");
			ModelAndView modelAndView = new ModelAndView(
					"central.ttluutru.show");

			if (paramMain != null) {
				Boolean valueM = false;
				if (StringUtils.isNotEmpty(paramMain.getParaShortValue())) {
					if (paramMain.getParaShortValue().equals("true")) {
						valueM = true;
					}
				}
				modelAndView.addObject("sttMain", "1");
				modelAndView.addObject("valueMain", valueM);
				modelAndView.addObject("nameMain", paramMain.getParaLobValue());
			}

			if (paramSub != null) {
				Boolean valueS = false;
				if (StringUtils.isNotEmpty(paramSub.getParaShortValue())) {
					if (paramSub.getParaShortValue().equals("true")) {
						valueS = true;
					}
				}
				modelAndView.addObject("sttSub", "2");
				modelAndView.addObject("valueSub", valueS);
				modelAndView.addObject("nameSub", paramSub.getParaLobValue());
			}

			modelAndView.addObject("formData", investigationAssignmentData);
			return modelAndView;
		} catch (Exception e) {

			e.printStackTrace();
			System.out.println(e.getMessage());
		}

		return null;
	}

	@ResponseBody
	@RequestMapping(value = "/turnttluutru")
	public ModelAndView turnttluutru(WebRequest request,
			HttpServletRequest httpRequest, ModelMap model) {

		try {
			Parameters paramMain = parametersService.getParamDetails(
					com.nec.asia.nic.utils.Constants.PARA_SCOPE_SYSTEM,
					"MAIN_CONNECTION_DATA_CENTER");
			Parameters paramSub = parametersService.getParamDetails(
					com.nec.asia.nic.utils.Constants.PARA_SCOPE_SYSTEM,
					"SUB_CONNECTION_DATA_CENTER");
			if (paramMain != null) {
				if (StringUtils.isNotEmpty(paramMain.getParaShortValue())) {
					if (paramMain.getParaShortValue().equals("true")) {
						paramMain.setParaShortValue("false");
						paramSub.setParaShortValue("true");
					} else {
						paramMain.setParaShortValue("true");
						paramSub.setParaShortValue("false");
					}
				}
			}
			parametersService.saveOrUpdate(paramSub);
			parametersService.saveOrUpdate(paramMain);
			model.addAttribute("thanhcong", "Thay đổi thành công");
		} catch (Exception ex) {

			model.addAttribute("loi", "Đã có lỗi xảy ra");
		}

		return this.ttluutru1(null, request, httpRequest, model);
	}

	@RequestMapping(value = "/ttdieuhanhbng")
	public ModelAndView ttdieuhanhbng(
			@ModelAttribute(value = "formData") InvestigationAssignmentData investigationAssignmentData,
			WebRequest request, HttpServletRequest httpRequest, ModelMap model) {

		return this.ttdieuhanhbng1(investigationAssignmentData, request,
				httpRequest, model);
	}

	public ModelAndView ttdieuhanhbng1(
			@ModelAttribute(value = "formData") InvestigationAssignmentData investigationAssignmentData,
			WebRequest request, HttpServletRequest httpRequest, ModelMap model) {

		try {
			// Lấy key MAIN
			Parameters paramMain = parametersService.getParamDetails("API",
					"WS_MAIN");
			List<Parameters> paramSub = parametersService
					.getParamDetailsList("API");
			ModelAndView modelAndView = new ModelAndView(
					"central.ttdieuhanhbng.show");

			if (paramMain != null) {
				Boolean valueM = false;
				if (StringUtils.isNotEmpty(paramMain.getParaShortValue())) {
					if (paramMain.getParaShortValue().equals("true")) {
						valueM = true;
					}
				}
				modelAndView.addObject("mainTypeAPI", paramMain.getParaDesc());
				modelAndView.addObject("mainUrlAPI",
						paramMain.getParaLobValue());
				modelAndView.addObject("mainStatusAPI", valueM);
			}

			List<ListApiDto> listApi = new ArrayList<ListApiDto>();
			if (paramSub != null && paramSub.size() > 0) {
				int stt = 1;
				for (Parameters para : paramSub) {
					if (!para.getId().getParaName().equals("WS_MAIN")) {
						ListApiDto dto = new ListApiDto();
						dto.setDescription(para.getParaDesc());
						Boolean valueS = false;
						if (StringUtils.isNotEmpty(para.getParaShortValue())) {
							if (para.getParaShortValue().equals("true")) {
								valueS = true;
							}
						}
						dto.setStt(stt++);
						dto.setIsOpen(valueS);
						dto.setParaName(para.getId().getParaName());
						listApi.add(dto);
					}
				}
			}
			modelAndView.addObject("dsApi", listApi);
			modelAndView.addObject("formData", investigationAssignmentData);
			return modelAndView;
		} catch (Exception e) {

			e.printStackTrace();
			System.out.println(e.getMessage());
		}

		return null;
	}

	@RequestMapping(value = "/detailAPI/{para}")
	public ModelAndView detailAPI(@PathVariable String para,
			WebRequest request, HttpServletRequest httpRequest, ModelMap model) {

		try {
			// Lấy key MAIN
			Parameters paramMain = parametersService.getParamDetails("API",
					"WS_MAIN");
			Parameters paramApi = parametersService
					.getParamDetails("API", para);
			String href = "http://" + paramMain.getParaLobValue()
					+ paramApi.getParaLobValue();
			ModelAndView modelAndView = new ModelAndView(
					"central.detailApi.show");

			String typeAPI = "POST";
			if (paramApi.getId().getParaName().equals("WS_LPPP_GET")) {
				typeAPI = "GET";
			}
			modelAndView.addObject("masterAPI", "API");
			modelAndView.addObject("hrefAPI", href);
			modelAndView.addObject("typeAPI", typeAPI);
			modelAndView.addObject("paraName", paramApi.getId().getParaName());
			return modelAndView;
		} catch (Exception e) {

			e.printStackTrace();
			System.out.println(e.getMessage());
		}

		return null;
	}

	@ResponseBody
	@RequestMapping(value = "/testDataApi", method = RequestMethod.POST)
	public String testDataApi(@RequestBody RequestTestApiDto data) {
		try {
			String href = data.getHref();
			if (data.getHref().contains("{")) {
				String[] parts = data.getHref().split("\\{");
				href = parts[0];
				if (href != null && href.length() > 0
						&& href.charAt(href.length() - 1) == '/') {
					href = href.substring(0, href.length() - 1);
				}
			}

			if (!data.getParam().equals("WS_USTATUS_LPPP_POST")) {
				URL url = new URL(href + data.getInput());
				HttpURLConnection connection = (HttpURLConnection) url
						.openConnection();
				connection.setRequestMethod(data.getTypeAPI());
				connection.setRequestProperty("Content-Type",
						"application/json");
				connection.connect();

				int statusCode = connection.getResponseCode();
				if (statusCode == 200) {
					InputStream content = connection.getInputStream();
					Charset charset = Charset.forName("UTF8");
					InputStreamReader isr = new InputStreamReader(content,
							charset);

					int numCharsRead;
					char[] charArray = new char[1024];
					StringBuffer sb = new StringBuffer();
					while ((numCharsRead = isr.read(charArray)) > 0) {
						sb.append(charArray, 0, numCharsRead);
					}
					return sb.toString();
				} else {
					// /Trả về log mã lỗi và thông tin lỗi

				}
			} else {
				/*
				 * ObjectMapper mapper = new ObjectMapper(); String dataP =
				 * mapper .writeValueAsString(data.getInput());
				 */
				URL url = new URL(href);
				HttpURLConnection connection = (HttpURLConnection) url
						.openConnection();

				connection.setDoInput(true);
				connection.setDoOutput(true);
				connection.setRequestMethod("POST");
				connection.setRequestProperty("Content-Type",
						"application/json; charset=UTF-8");
				OutputStreamWriter writer = new OutputStreamWriter(
						connection.getOutputStream(), "UTF-8");
				writer.write(data.getInput());
				writer.close();

				int statusCode = connection.getResponseCode();
				if (statusCode == 200 || statusCode == 201) {

					InputStream content = connection.getInputStream();
					Charset charset = Charset.forName("UTF8");
					InputStreamReader isr = new InputStreamReader(content,
							charset);

					int numCharsRead;
					char[] charArray = new char[1024];
					StringBuffer sb = new StringBuffer();
					while ((numCharsRead = isr.read(charArray)) > 0) {
						sb.append(charArray, 0, numCharsRead);
					}
					return sb.toString();

				}
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		return "";
	}

	@ResponseBody
	@RequestMapping(value = "/turnttdieuhanhbng/{para}")
	public ModelAndView turnttdieuhanhbng(@PathVariable String para,
			WebRequest request, HttpServletRequest httpRequest, ModelMap model) {

		try {
			Parameters paramMain = null;
			paramMain = parametersService.getParamDetails("API", para);
			if (paramMain != null) {
				if (StringUtils.isNotEmpty(paramMain.getParaShortValue())) {
					if (paramMain.getParaShortValue().equals("true")) {
						paramMain.setParaShortValue("false");
					} else {
						paramMain.setParaShortValue("true");
					}
				}
			}
			parametersService.saveOrUpdate(paramMain);
			model.addAttribute("thanhcong", "Thay đổi thành công");
		} catch (Exception ex) {

			model.addAttribute("loi", "Đã có lỗi xảy ra");
		}

		return this.ttdieuhanhbng(null, request, httpRequest, model);
	}

	@RequestMapping(value = "/ttca")
	public ModelAndView ttca(
			@ModelAttribute(value = "formData") InvestigationAssignmentData investigationAssignmentData,
			WebRequest request, HttpServletRequest httpRequest, ModelMap model) {

		ModelAndView modelAndView = new ModelAndView("central.ttca.show");
		modelAndView.addObject("formData", investigationAssignmentData);
		return modelAndView;
	}

	@RequestMapping(value = "/ttdancu")
	public ModelAndView ttdancu(
			@ModelAttribute(value = "formData") InvestigationAssignmentData investigationAssignmentData,
			WebRequest request, HttpServletRequest httpRequest, ModelMap model) {

		ModelAndView modelAndView = new ModelAndView("central.ttdancu.show");
		modelAndView.addObject("formData", investigationAssignmentData);
		return modelAndView;
	}

	@RequestMapping(value = "/ttchxnc")
	public ModelAndView ttchxnc(
			@ModelAttribute(value = "formData") InvestigationAssignmentData investigationAssignmentData,
			WebRequest request, HttpServletRequest httpRequest, ModelMap model) {

		ModelAndView modelAndView = new ModelAndView("central.ttchxnc.show");
		modelAndView.addObject("formData", investigationAssignmentData);
		return modelAndView;
	}

	@RequestMapping(value = "/ttcmnd")
	public ModelAndView ttcmnd(
			@ModelAttribute(value = "formData") InvestigationAssignmentData investigationAssignmentData,
			WebRequest request, HttpServletRequest httpRequest, ModelMap model) {

		ModelAndView modelAndView = new ModelAndView("central.cmnd.show");
		modelAndView.addObject("formData", investigationAssignmentData);
		return modelAndView;
	}

	@ResponseBody
	@RequestMapping(value = "/getDanhsachHs", method = RequestMethod.POST)
	public ModelAndView getDanhsachHs(@RequestBody String packID,
			WebRequest request, HttpServletRequest httpRequest, ModelMap model) {
		ModelAndView mav = new ModelAndView("central-danhsachHS");
		DanhsachHsoDto listDs = new DanhsachHsoDto();
		List<ChiTietHsoDto> lstChitiet = new ArrayList<ChiTietHsoDto>();
		String nameTTCTH = "";
		String nameTTXL = "";
		// InfoPaymentDto phiChinh = new InfoPaymentDto();
		try {
			List<NicTransactionPackage> lstPack = nicTransactionPackageService
					.getListPackageByPackageId(packID.replace("\"", ""));
			if (lstPack != null) {
				for (NicTransactionPackage pack : lstPack) {
					ChiTietHsoDto chiTietHsoDto = new ChiTietHsoDto();
					NicTransaction nicTran = nicTransactionService
							.getNicTransactionById(pack.getTransactionId())
							.getModel();
					// NicTransaction nicTran =
					// nicTransactionService.getNicTransactionById(pack.getTransactionId());
					//
					// BaseModelSingle<NicTransaction> nicTran =
					// nicTransactionService.getNicTransactionById(pack.getTransactionId());

					if (nicTran != null) {
						String strDate = "";
						if (nicTran.getNicRegistrationData().getDateOfBirth() != null) {
							SimpleDateFormat formatter = new SimpleDateFormat(
									"dd/MM/yyyy");
							strDate = formatter.format(nicTran
									.getNicRegistrationData().getDateOfBirth());
						}
						chiTietHsoDto.setDob(strDate);
						chiTietHsoDto.setFullName(nicTran
								.getNicRegistrationData().getSurnameLine1());
						String issuePlace = "";
						SiteRepository siteR = siteService
								.getSiteRepoById(nicTran.getIssSiteCode());
						if (siteR != null) {
							issuePlace = siteR.getSiteName();
						}
						chiTietHsoDto.setIssuePlace(issuePlace);
						chiTietHsoDto.setNin(nicTran.getNin());
						chiTietHsoDto.setPackageId(nicTran.getTransactionId());
						chiTietHsoDto.setPob(nicTran.getNicRegistrationData()
								.getPlaceOfBirth());
						if (nicTran.getIsEpassport() == null
								|| nicTran.getIsEpassport().equals("Y")) {
							chiTietHsoDto.setTypeP("Hộ chiếu điện tử");
						} else {
							chiTietHsoDto.setTypeP("Hộ chiếu thường");
						}
						lstChitiet.add(chiTietHsoDto);
						if (StringUtils.isBlank(nameTTXL)) {
							nameTTXL = siteR.getSiteGroups().getGroupName();
						}
						/*
						 * if(StringUtils.isBlank(nameTTCTH)){
						 * if(nicTran.getPrintPerso() != null){ nameTTCTH =
						 * persoLocationService
						 * .findAllById(nicTran.getPrintPerso
						 * ()).get(0).getName(); } else { nameTTCTH =
						 * persoLocationService
						 * .findAllById(1l).get(0).getName(); } }
						 */
					}
				}
				listDs.setChitiet(lstChitiet);
				listDs.setPackageId(packID.replace("\"", ""));
				listDs.setTtcathe(nameTTCTH);
				listDs.setTtxuly(nameTTXL);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		// model.addAttribute("pc_HienTai", phiChinh);
		model.addAttribute("tranID", packID);
		mav.addObject("danhsach", listDs);
		return mav;
	}

	@ResponseBody
	@RequestMapping(value = "/getTientrinhHs", method = RequestMethod.POST)
	public ModelAndView getTientrinhHs(@RequestBody String packID,
			WebRequest request, HttpServletRequest httpRequest, ModelMap model) {
		ModelAndView mav = new ModelAndView("central-tientrinhHS");
		ListWorkflowDto lstWF = new ListWorkflowDto();
		// InfoPaymentDto phiChinh = new InfoPaymentDto();
		try {
			String packID_ = packID.replace("\"", "");
			List<WorkflowDto> listWF_2 = new ArrayList<WorkflowDto>();
			List<WorkflowDto> listWF_3 = new ArrayList<WorkflowDto>();
			List<WorkflowDto> listWF_4 = new ArrayList<WorkflowDto>();
			int amount_1 = 0;
			int amount_2 = 0;
			int amount_3 = 0;
			int amount3_1 = 0;
			int amount3_2 = 0;
			int amount3_3 = 0;
			int amount4_1 = 0;
			int amount4_2 = 0;
			int amount4_3 = 0;

			String createDate3 = "";
			String handover3 = "";
			WorkflowDto workflowDSHS = new WorkflowDto();
			workflowDSHS.setAmount(0);
			// Tìm kiếm danh sách theo PackID
			NicListHandover handover = handoverDao.findById(packID_);
			if (handover != null) {
				if (handover.getId().getTypeList() == NicListHandover.TYPE_LIST_A) {
					workflowDSHS.setPackageId(packID_);
					workflowDSHS.setStep(1);
					workflowDSHS.setStatus(1);
					workflowDSHS.setIsPick(true);
					// nvp thêm loại danh sách 02/12/2019
					workflowDSHS.setTypePackage("Danh sách A: ");

					SimpleDateFormat formatter = new SimpleDateFormat(
							"dd/MM/yyyy");
					String strDate = formatter.format(handover.getCreateDate());
					String nameRSC = "";
					String nameGSC = "";
					workflowDSHS.setDate(strDate);
					List<NicTransactionPackage> lstPack = nicTransactionPackageService
							.getListPackageByPackageId(packID_);
					if (lstPack != null && lstPack.size() > 0) {
						workflowDSHS.setAmount(lstPack.size());
						NicTransaction nicTran = nicTransactionService
								.getNicTransactionById(
										lstPack.get(0).getTransactionId())
								.getModel();
						if (nicTran != null) {
							SiteRepository siteR = siteService
									.getSiteRepoById(nicTran.getRegSiteCode());
							if (siteR != null) {
								nameRSC = siteR.getSiteName();
								nameGSC = siteR.getSiteGroups().getGroupName();
							}
						}
						String packC = "";
						String dateC = "";
						// Xử lý các hồ sơ đóng gói
						for (NicTransactionPackage pack : lstPack) {
							NicListHandover hdv = handoverDao
									.findHandoverByTransactionId(
											pack.getTransactionId(),
											NicListHandover.TYPE_LIST_B, null,
											true);
							BaseModelSingle<NicUploadJob> baseGetUJ = nicUploadJobService
									.getUploadJobByTransactionIdSinger(null,
											pack.getTransactionId());
							NicUploadJob nU = baseGetUJ.getModel();
							if (hdv != null) {
								createDate3 = formatter.format(hdv
										.getCreateDate());
								handover3 = hdv.getId().getPackageId();
								List<NicTransactionPackage> lstP = nicTransactionPackageService
										.getListPackageByPackageId(hdv.getId()
												.getPackageId());
								if (lstP != null && lstP.size() > 0) {
									for (NicTransactionPackage p : lstP) {
										if (p.getStage().equals("D")
												&& p.getTransactionId()
														.equals(pack
																.getTransactionId())) {
											if (nU != null) {
												if (nU.getInvestigationStatus()
														.equals(NicUploadJob.RECORD_STATUS_APPROVE_PERSO)) {
													if (amount_3 == 0)
														amount_3 = 1;
													else
														amount_3 = amount_3 + 1;
													NicTransaction nT = nicTransactionService
															.getNicTransactionById(
																	p.getTransactionId())
															.getModel();
													NicDocumentData nD = documentDataService
															.getDocumentDataById(
																	p.getTransactionId())
															.getModel();
													if (nT != null
															&& nT.getTransactionStatus()
																	.equals(NicTransaction.TRANSACTION_STATUS_RIC_ISSUED)) {
														packC = nD
																.getPackageId();
														dateC = formatter
																.format(nD
																		.getCreateDatetime());
														if (amount3_3 == 0)
															amount3_3 = 1;
														else
															amount3_3 = amount3_3 + 1;

														if (nD.getActiveFlag() != null
																&& nD.getActiveFlag()
																		.equals("Y")) {
															if (amount4_2 == 0)
																amount4_2 = 1;
															else
																amount4_2 = amount4_2 + 1;
														} else if (nD
																.getRejectBy() != null) {
															if (amount4_3 == 0)
																amount4_3 = 1;
															else
																amount4_3 = amount4_3 + 1;
														} else {
															if (amount4_1 == 0)
																amount4_1 = 1;
															else
																amount4_1 = amount4_1 + 1;
														}
													} else if (nD != null
															&& StringUtils
																	.isNotEmpty(nD
																			.getPackageId())) {
														packC = nD
																.getPackageId();
														dateC = formatter
																.format(nD
																		.getCreateDatetime());
														if (amount3_2 == 0)
															amount3_2 = 1;
														else
															amount3_2 = amount3_2 + 1;
													} else {
														if (amount3_1 == 0)
															amount3_1 = 1;
														else
															amount3_1 = amount3_1 + 1;
													}
												} else if (nU
														.getInvestigationStatus()
														.equals(NicUploadJob.RECORD_STATUS_APPROVED)) {
													if (amount_2 == 0)
														amount_2 = 1;
													else
														amount_2 = amount_2 + 1;
												} else {
													if (amount_1 == 0)
														amount_1 = 1;
													else
														amount_1 = amount_1 + 1;
												}
											}
											break;
										}
									}
								}
							} else if (nU.getInvestigationStatus() != null
									&& nU.getInvestigationStatus()
											.equals(NicUploadJob.RECORD_STATUS_APPROVED)) {
								if (amount_2 == 0)
									amount_2 = 1;
								else
									amount_2 = amount_2 + 1;
							} else {
								if (amount_1 == 0)
									amount_1 = 1;
								else
									amount_1 = amount_1 + 1;
							}
						}
						if (amount_1 > 0) {
							WorkflowDto wf = new WorkflowDto();
							String st = formatter.format(handover
									.getCreateDate());
							wf.setAmount(amount_1);
							wf.setDate(st);
							wf.setIsPick(false);
							wf.setPackageId(packID_);
							wf.setTypePackage("Danh sách A: ");
							wf.setRegSiteCode(nameGSC);
							wf.setStatus(1);
							wf.setStep(2);
							listWF_2.add(wf);
						}

						if (amount_2 > 0) {
							WorkflowDto wf = new WorkflowDto();
							String st = formatter.format(handover
									.getCreateDate());
							wf.setAmount(amount_2);
							wf.setDate(st);
							wf.setIsPick(false);
							wf.setPackageId(handover.getId().getPackageId());
							wf.setTypePackage("Danh sách A: ");
							wf.setRegSiteCode(nameGSC);
							wf.setStatus(2);
							wf.setStep(2);
							listWF_2.add(wf);
						}

						if (amount_3 > 0) {
							WorkflowDto wf = new WorkflowDto();
							wf.setAmount(amount_3);
							wf.setDate(createDate3);
							wf.setIsPick(false);
							wf.setPackageId(handover3);
							wf.setRegSiteCode(nameGSC);
							wf.setStatus(3);
							wf.setStep(2);
							wf.setTypePackage("Danh sách B: ");
							listWF_2.add(wf);
						}

						if (amount3_1 > 0) {
							WorkflowDto wf = new WorkflowDto();
							wf.setAmount(amount3_1);
							wf.setIsPick(false);
							wf.setStatus(1);
							wf.setStep(3);
							listWF_3.add(wf);
						}

						if (amount3_2 > 0) {
							WorkflowDto wf = new WorkflowDto();
							wf.setAmount(amount3_2);
							wf.setIsPick(false);
							wf.setDate(dateC);
							wf.setPackageId(packC);
							wf.setTypePackage("Danh sách C: ");
							wf.setStatus(2);
							wf.setStep(3);
							listWF_3.add(wf);
						}

						if (amount3_3 > 0) {
							WorkflowDto wf = new WorkflowDto();
							wf.setAmount(amount3_3);
							wf.setIsPick(false);
							wf.setDate(dateC);
							wf.setPackageId(packC);
							wf.setTypePackage("Danh sách C: ");
							wf.setStatus(3);
							wf.setStep(3);
							listWF_3.add(wf);
						}

						if (amount4_1 > 0) {
							WorkflowDto wf = new WorkflowDto();
							wf.setAmount(amount4_1);
							wf.setIsPick(false);
							wf.setStatus(1);
							wf.setStep(4);
							listWF_4.add(wf);
						}

						if (amount4_2 > 0) {
							WorkflowDto wf = new WorkflowDto();
							wf.setAmount(amount4_2);
							wf.setIsPick(false);
							wf.setStatus(2);
							wf.setStep(4);
							listWF_4.add(wf);
						}

						if (amount4_3 > 0) {
							WorkflowDto wf = new WorkflowDto();
							wf.setAmount(amount4_3);
							wf.setIsPick(false);
							wf.setStatus(3);
							wf.setStep(4);
							listWF_4.add(wf);
						}

					}
					workflowDSHS.setRegSiteCode(nameRSC);
				} else if (handover.getId().getTypeList() == NicListHandover.TYPE_LIST_B) {
					List<NicTransactionPackage> lstPack = nicTransactionPackageService
							.getListPackageByPackageId(handover.getId()
									.getPackageId());
					String nameRSC = "";
					String nameGSC = "";
					if (lstPack != null && lstPack.size() > 0) {
						workflowDSHS.setAmount(lstPack.size());
						NicTransaction nicTran = nicTransactionService
								.getNicTransactionById(
										lstPack.get(0).getTransactionId())
								.getModel();
						if (nicTran != null) {
							SiteRepository siteR = siteService
									.getSiteRepoById(nicTran.getRegSiteCode());
							if (siteR != null) {
								nameRSC = siteR.getSiteName();
								nameGSC = siteR.getSiteGroups().getGroupName();
							}
						}
						// Lấy danh sách A của Transaction
						NicListHandover hdv = handoverDao
								.findHandoverByTransactionId(lstPack.get(0)
										.getTransactionId(),
										NicListHandover.TYPE_LIST_A, null, null);
						List<NicTransactionPackage> lstA = nicTransactionPackageService
								.getListPackageByPackageId(hdv.getId()
										.getPackageId());
						SimpleDateFormat formatter = new SimpleDateFormat(
								"dd/MM/yyyy");
						String strDate = formatter.format(hdv.getCreateDate());
						workflowDSHS.setPackageId(hdv.getId().getPackageId());
						workflowDSHS.setDate(strDate);
						workflowDSHS.setAmount(lstA.size());
						workflowDSHS.setStep(1);
						workflowDSHS.setStatus(1);
						workflowDSHS.setIsPick(false);
						workflowDSHS.setRegSiteCode(nameRSC);
						workflowDSHS.setTypePackage("Danh sách A: ");
					}
					SimpleDateFormat formatter = new SimpleDateFormat(
							"dd/MM/yyyy");
					if (handover.getHandoverStatus() == 0) {
						WorkflowDto wf = new WorkflowDto();
						String st = formatter.format(handover.getCreateDate());
						wf.setAmount(lstPack.size());
						wf.setDate(st);
						wf.setIsPick(true);
						wf.setPackageId(packID_);
						wf.setTypePackage("Danh sách B: ");
						wf.setRegSiteCode(nameGSC);
						wf.setStatus(2);
						wf.setStep(2);
						listWF_2.add(wf);
					} else {
						WorkflowDto wf = new WorkflowDto();
						String st = formatter.format(handover.getCreateDate());
						wf.setAmount(lstPack.size());
						wf.setDate(st);
						wf.setIsPick(true);
						wf.setPackageId(packID_);
						wf.setTypePackage("Danh sách B: ");
						wf.setStatus(3);
						wf.setStep(2);
						listWF_2.add(wf);

						String packC = "";
						String dateC = "";
						for (NicTransactionPackage p : lstPack) {
							BaseModelSingle<NicUploadJob> bGetUJ = nicUploadJobService
									.getUploadJobByTransactionIdSinger(null,
											p.getTransactionId());
							NicUploadJob nU = bGetUJ.getModel();
							if (nU != null) {
								if (nU.getInvestigationStatus()
										.equals(NicUploadJob.RECORD_STATUS_APPROVE_PERSO)) {
									if (amount_3 == 0)
										amount_3 = 1;
									else
										amount_3 = amount_3 + 1;

									NicTransaction nT = nicTransactionService
											.getNicTransactionById(
													p.getTransactionId())
											.getModel();
									NicDocumentData nD = documentDataService
											.getDocumentDataById(
													p.getTransactionId())
											.getModel();
									if (nT != null
											&& nT.getTransactionStatus()
													.equals(NicTransaction.TRANSACTION_STATUS_RIC_ISSUED)) {
										packC = nD.getPackageId();
										dateC = formatter.format(nD
												.getCreateDatetime());
										if (amount3_3 == 0)
											amount3_3 = 1;
										else
											amount3_3 = amount3_3 + 1;

										if (nD.getActiveFlag() != null
												&& nD.getActiveFlag().equals(
														"Y")) {
											if (amount4_2 == 0)
												amount4_2 = 1;
											else
												amount4_2 = amount4_2 + 1;
										} else if (nD.getRejectBy() != null) {
											if (amount4_3 == 0)
												amount4_3 = 1;
											else
												amount4_3 = amount4_3 + 1;
										} else {
											if (amount4_1 == 0)
												amount4_1 = 1;
											else
												amount4_1 = amount4_1 + 1;
										}
									} else if (nD != null
											&& StringUtils.isNotEmpty(nD
													.getPackageId())) {
										packC = nD.getPackageId();
										dateC = formatter.format(nD
												.getCreateDatetime());
										if (amount3_2 == 0)
											amount3_2 = 1;
										else
											amount3_2 = amount3_2 + 1;
									} else {
										if (amount3_1 == 0)
											amount3_1 = 1;
										else
											amount3_1 = amount3_1 + 1;
									}
								}
							}
						}

						if (amount3_1 > 0) {
							WorkflowDto wf1 = new WorkflowDto();
							wf1.setAmount(amount3_1);
							wf1.setIsPick(false);
							wf1.setDate(dateC);
							wf1.setPackageId(packC);
							wf1.setTypePackage("Danh sách C: ");
							wf1.setStatus(1);
							wf1.setStep(3);
							listWF_3.add(wf1);
						}

						if (amount3_2 > 0) {
							WorkflowDto wf1 = new WorkflowDto();
							wf1.setAmount(amount3_2);
							wf1.setIsPick(false);
							wf1.setDate(dateC);
							wf1.setPackageId(packC);
							wf1.setTypePackage("Danh sách C: ");
							wf1.setStatus(2);
							wf1.setStep(3);
							listWF_3.add(wf1);
						}

						if (amount3_3 > 0) {
							WorkflowDto wf1 = new WorkflowDto();
							wf1.setAmount(amount3_3);
							wf1.setIsPick(false);
							wf1.setDate(dateC);
							wf1.setPackageId(packC);
							wf1.setTypePackage("Danh sách C: ");
							wf1.setStatus(3);
							wf1.setStep(3);
							listWF_3.add(wf1);
						}

						if (amount4_1 > 0) {
							WorkflowDto wf1 = new WorkflowDto();
							wf1.setAmount(amount4_1);
							wf1.setIsPick(false);
							wf1.setStatus(1);
							wf1.setStep(4);
							listWF_4.add(wf1);
						}

						if (amount4_2 > 0) {
							WorkflowDto wf1 = new WorkflowDto();
							wf1.setAmount(amount4_2);
							wf1.setIsPick(false);
							wf1.setStatus(2);
							wf1.setStep(4);
							listWF_4.add(wf1);
						}

						if (amount4_3 > 0) {
							WorkflowDto wf1 = new WorkflowDto();
							wf1.setAmount(amount4_3);
							wf1.setIsPick(false);
							wf1.setStatus(3);
							wf1.setStep(4);
							listWF_4.add(wf1);
						}
					}
				}

				lstWF.setStep1(workflowDSHS);
				lstWF.setStep2(listWF_2);
				lstWF.setStep3(listWF_3);
				lstWF.setStep4(listWF_4);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		// model.addAttribute("pc_HienTai", phiChinh);
		mav.addObject("danhsach", lstWF);
		model.addAttribute("tranID", packID);
		return mav;
	}

	@RequestMapping(value = "/sodohethong")
	public ModelAndView sodohethong(WebRequest request,
			HttpServletRequest httpRequest, ModelMap model) {

		ModelAndView modelAndView = new ModelAndView("central.sodohethong.show");

		return modelAndView;
	}

	@ResponseBody
	@RequestMapping(value = "/chitiethoso/{txnId}")
	public TranCuuHoSoDto chitiethoso(@PathVariable String txnId) {
		TranCuuHoSoDto result = new TranCuuHoSoDto();
		result.setEndding(false);
		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
		try {
			NicTransaction nicTran = nicTransactionService
					.getNicTransactionById(txnId).getModel();
			if (nicTran != null) {
				result.setSoBienNhan(nicTran.getRecieptNo());
				List<NicTransactionPackage> lstPack = nicTransactionPackageService
						.getPackageNameByTransactionId(txnId);
				if (lstPack != null && lstPack.size() > 0) {
					for (NicTransactionPackage pack : lstPack) {
						NicListHandover handover = handoverDao
								.findHandoverByCriteria(pack.getPackageId(),
										null, null).getModel();
						if (handover != null) {
							String strDate = formatter.format(handover
									.getCreateDate());
							if (pack.getTypeList() == 7) {
								result.setSodanhsachA(pack.getPackageId());
								result.setNgaylapA(strDate);
								result.setNguoilapA(handover.getCreateBy());
							} else if (pack.getTypeList() == 8) {
								result.setSodanhsachB(pack.getPackageId());
								result.setNgaylapB(strDate);
								result.setNguoilapB(handover.getCreateBy());

								if (pack.getStage().equals("D")) {
									// result.setNguoiduyetDSB(nicTran.getLeaderOfficerId());
									if (handover.getUpdateDate() != null) {
										String strDateB = formatter
												.format(handover
														.getUpdateDate());
										result.setNgayduyetDSB(strDateB);
									}
									result.setYkienDSB(pack.getNoteApprove());
								}
							}
						}
					}
				}
				if (nicTran.getIsEpassport() != null
						&& nicTran.getIsEpassport().equals("Y"))
					result.setHochieudientu(true);
				else
					result.setHochieudientu(false);

				if (nicTran.getCreateDatetime() != null) {
					String ngayNhapmay = formatter.format(nicTran
							.getCreateDatetime());
					result.setNgaynhap(ngayNhapmay);
				}
			}

			NicDocumentData docData = documentDataService.getDocumentDataById(
					txnId).getModel();
			if (docData != null) {
				if (docData.getCreateDatetime() != null) {
					String strDateC = formatter.format(docData
							.getCreateDatetime());
					result.setNgaycapHc(strDateC);
					result.setNgaylapC(strDateC);
				}
				result.setSodsC(docData.getPackageId());
				result.setSohochieu(docData.getId().getPassportNo());
				if (docData.getDateOfExpiry() != null) {
					String hanHC = formatter.format(docData.getDateOfExpiry());
					result.setHanHc(hanHC);
				}
				result.setNguoinhan(nicTran.getAppointmentPlace());
			}
			BaseModelSingle<NicUploadJob> bGetUJ = nicUploadJobService
					.getUploadJobByTransactionIdSinger(null, txnId);
			NicUploadJob uploadJob = bGetUJ.getModel();
			if (uploadJob != null) {
				result.setNguoitra(uploadJob.getInvestigationOfficerId());
				if (uploadJob.getDateApprovePerson() != null) {
					String ngayTraDT = formatter.format(uploadJob
							.getDateApprovePerson());
					result.setNgaytradt(ngayTraDT);
				}
			}

			List<NicTransactionAttachment> nicAttch = nicTransactionAttachmentService
					.findNicTransactionAttachments(txnId, "PH_CAP", "01")
					.getListModel();
			if (nicAttch != null && nicAttch.size() > 0) {
				NicTransactionAttachment photo = nicAttch.get(0);
				result.setImage(Base64.encodeBase64String(photo.getDocData()));
			}

			result.setEndding(true);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return result;
	}

	@RequestMapping(value = "/inchitiet/{txnId}")
	public ModelAndView inchitiet(@PathVariable String txnId,
			WebRequest request, HttpServletRequest httpRequest, ModelMap model) {

		ModelAndView modelAndView = new ModelAndView("central-inchitiet");
		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
		InchitietDto tt = new InchitietDto();
		Calendar c = Calendar.getInstance();
		Date dateNow = new Date();
		c.setTime(dateNow);
		tt.setNgaythangnam("Ngày " + String.valueOf(c.get(Calendar.DATE))
				+ " tháng " + String.valueOf(c.get(Calendar.MONTH)) + " năm "
				+ String.valueOf(c.get(Calendar.YEAR)));

		if (StringUtils.isNotEmpty(txnId)) {
			BaseModelSingle<NicUploadJob> bGetUJ = nicUploadJobService
					.getUploadJobByTransactionIdSinger(null, txnId);
			NicUploadJob upJob = bGetUJ.getModel();
			if (upJob != null) {
				NicRegistrationData regData = upJob.getNicTransaction()
						.getNicRegistrationData();
				NicTransaction nicTran = upJob.getNicTransaction();
				tt.setHoten(regData.getSurnameLine1().toUpperCase());
				if (regData.getDateOfBirth() != null) {
					c.setTime(regData.getDateOfBirth());
					if (regData.getDefDateOfBirth() != null
							&& regData.getDefDateOfBirth().equals("Y")) {
						tt.setNgaysinh(String.valueOf(c.get(Calendar.YEAR)));
					} else if (regData.getDefDateOfBirth() != null
							&& regData.getDefDateOfBirth().equals("M")) {
						tt.setNgaysinh(String.valueOf(c.get(Calendar.MONTH))
								+ "/" + String.valueOf(c.get(Calendar.YEAR)));
					} else {
						String dob = formatter.format(regData.getDateOfBirth());
						tt.setNgaysinh(dob);
					}
				}
				if (regData.getGender().equals("F")) {
					tt.setGioitinh("Nữ");
				} else if (regData.getGender().equals("M")) {
					tt.setGioitinh("Nam");
				} else {
					tt.setGioitinh("Khác");
				}

				tt.setDantoc(regData.getNation());
				if (StringUtils.isNotEmpty(regData.getPlaceOfBirth())) {
					CodeValues codeV = codesService.getCodeValueByIdName(
							"CODE_BirthPlace", regData.getPlaceOfBirth());
					if (codeV != null)
						tt.setNoisinh(codeV.getCodeValueDesc());
					else
						tt.setNghenghiep(regData.getPlaceOfBirth());
				}
				tt.setCmnd(nicTran.getNin());
				if (regData.getDateNin() != null) {
					String pon = formatter.format(regData.getDateNin());
					tt.setNgaycap(pon);
				}
				tt.setDiachi(regData.getAddressLine1());
				// tt.setDiachitamtru(regData.getAddressTempStreet());
				if (StringUtils.isNotEmpty(regData.getJob())) {
					CodeValues codeV = codesService.getCodeValueByIdName(
							"CODE_JOB", regData.getJob());
					if (codeV != null)
						tt.setNghenghiep(codeV.getCodeValueDesc());
					else
						tt.setNghenghiep(regData.getJob());
				}
				// tt.setNoilamviec(regData.getAddressCompany());

				if (nicTran.getIsEpassport() == null
						|| !nicTran.getIsEpassport().equals("N"))
					tt.setLoaihs("Cấp HC điện tử");
				else
					tt.setLoaihs("HC thường");

				// Thông tin hộ chiếu
				NicDocumentData docData = documentDataService
						.getDocumentDataById(txnId).getModel();
				if (docData != null) {
					tt.setSohochieu(docData.getId().getPassportNo());
					if (docData.getIssueDatetime() != null) {
						String issue = formatter.format(docData
								.getIssueDatetime());
						tt.setNgaycaphc(issue);
					}
					if (docData.getDateOfExpiry() != null) {
						String expiry = formatter.format(docData
								.getDateOfExpiry());
						tt.setHanhc(expiry);
					}
					if (docData.getCancelDatetime() != null) {
						String cancel = formatter.format(docData
								.getCancelDatetime());
						tt.setNgayhuyhc(cancel);
					}
					if (StringUtils.isNotEmpty(docData.getCancelBy())) {
						Users user = userService.findByUserId(docData
								.getCancelBy());
						if (user != null) {
							tt.setNguoihuyhc(user.getUserName());
						} else
							tt.setNguoihuyhc(docData.getCancelBy());
					}
				}
				if (StringUtils.isNotEmpty(upJob.getInvestigationOfficerId())) {
					Users user = userService.findByUserId(upJob
							.getInvestigationOfficerId());
					if (user != null) {
						tt.setNguoitrahs(user.getUserName());
					}
					// else
					// tt.setNguoitrahs(nicTran.getLeaderOfficerId());

					if (upJob.getDateApproveInvestigation() != null) {
						String approve = formatter.format(upJob
								.getDateApproveInvestigation());
						tt.setNgaytrahs(approve);
					}
				}
				tt.setSohosoluu(nicTran.getTransactionId());
				NicListHandover handover = handoverDao
						.findHandoverByTransactionId(
								nicTran.getTransactionId(),
								NicListHandover.TYPE_LIST_B, 1, true);
				if (handover != null) {
					tt.setSodanhsachB(handover.getId().getPackageId());
					/*
					 * if(StringUtils.isNotEmpty(nicTran.getLeaderOfficerId())){
					 * Users user =
					 * userService.findByUserId(nicTran.getLeaderOfficerId());
					 * if(user != null){ tt.setNguoiduyet(user.getUserName()); }
					 * else tt.setNguoiduyet(nicTran.getLeaderOfficerId()); }
					 */

					if (handover.getUpdateDate() != null) {
						String update = formatter.format(handover
								.getUpdateDate());
						tt.setNgayduyet(update);
					}
				}
			}
		}

		model.addAttribute("tt", tt);
		return modelAndView;
	}

	@RequestMapping(value = "/inlichsu/{txnId}")
	public ModelAndView inlichsu(@PathVariable String txnId,
			WebRequest request, HttpServletRequest httpRequest, ModelMap model) {
		ModelAndView modelAndView = new ModelAndView("central-inlichsu");
		InlichsuDto tt = new InlichsuDto();
		try {
			SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
			Calendar c = Calendar.getInstance();
			Date dateNow = new Date();
			c.setTime(dateNow);
			tt.setNgaythangnam("Ngày " + String.valueOf(c.get(Calendar.DATE))
					+ " tháng " + String.valueOf(c.get(Calendar.MONTH))
					+ " năm " + String.valueOf(c.get(Calendar.YEAR)));

			if (StringUtils.isNotEmpty(txnId)) {
				BaseModelSingle<NicUploadJob> bGetUJ = nicUploadJobService
						.getUploadJobByTransactionIdSinger(null, txnId);
				NicUploadJob upJob = bGetUJ.getModel();

				if (upJob != null) {
					NicRegistrationData regData = upJob.getNicTransaction()
							.getNicRegistrationData();
					// Lấy thông tin hồ sơ
					tt.setHoten(regData.getSurnameLine1().toUpperCase());
					tt.setDiachi(regData.getAddressLine1());
					if (regData.getGender().equals("F"))
						tt.setGioitinh("Nữ");
					else if (regData.getGender().equals("M"))
						tt.setGioitinh("Nam");
					else
						tt.setGioitinh("Khác");

					if (regData.getDateOfBirth() != null) {
						String dob = formatter.format(regData.getDateOfBirth());
						tt.setNgaysinh(dob);
					}
					List<NicDocumentData> lstDoc = new ArrayList<NicDocumentData>();
					List<NicTransaction> lstNic = nicTransactionService
							.findAllByFields(upJob.getNicTransaction().getNin());
					if (lstNic != null && lstNic.size() > 0) {
						tt.setCmnd(upJob.getNicTransaction().getNin());
						int a = 1;
						for (NicTransaction nicTran : lstNic) {
							NicDocumentData doc = documentDataService
									.getDocumentDataById(
											nicTran.getTransactionId())
									.getModel();
							if (doc != null) {
								doc.setSyncStatus("" + a);
								if (doc.getStatus().equals("ISSUANCE")) {
									doc.setStatus("Khởi tạo");
								} else if (doc.getStatus().equals(
										"PERSONALIZED")) {
									doc.setStatus("Chưa trả");
								} else if (doc.getStatus().equals("NONE")) {
									doc.setStatus("Đã đóng");
								}
								if (doc.getIssueDatetime() != null) {
									String issue = formatter.format(doc
											.getIssueDatetime());
									doc.setDispatchId(issue);
								}
								if (doc.getDateOfExpiry() != null) {
									String expiry = formatter.format(doc
											.getDateOfExpiry());
									doc.setIssueBy(expiry);
								}

								lstDoc.add(doc);
							}
						}
					}

					if (lstDoc != null && lstDoc.size() > 0) {
						tt.setDocData(lstDoc);
					}
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		model.addAttribute("tt", tt);
		return modelAndView;
	}

	@RequestMapping(value = "/intokhai/{txnId}")
	public ModelAndView intokhai(@PathVariable String txnId,
			WebRequest request, HttpServletRequest httpRequest, ModelMap model) {

		ModelAndView modelAndView = new ModelAndView("central-intokhai");
		IntokhaiDto tt = new IntokhaiDto();
		try {
			SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
			Calendar c = Calendar.getInstance();
			Date dateNow = new Date();
			c.setTime(dateNow);
			// tt.setNgaythangnam("Ngày " + String.valueOf(c.get(Calendar.DATE))
			// + " tháng " + String.valueOf(c.get(Calendar.MONTH)) + " năm " +
			// String.valueOf(c.get(Calendar.YEAR)));

			if (StringUtils.isNotEmpty(txnId)) {
				BaseModelSingle<NicUploadJob> bGetUJ = nicUploadJobService
						.getUploadJobByTransactionIdSinger(null, txnId);
				NicUploadJob upJob = bGetUJ.getModel();

				if (upJob != null) {
					NicRegistrationData regData = upJob.getNicTransaction()
							.getNicRegistrationData();
					if (regData.getDateOfBirth() != null) {
						c.setTime(regData.getDateOfBirth());
						tt.setNgaysinh("Sinh ngày "
								+ String.valueOf(c.get(Calendar.DATE))
								+ " tháng "
								+ String.valueOf(c.get(Calendar.MONTH) + 1)
								+ " năm "
								+ String.valueOf(c.get(Calendar.YEAR)));
					}

					tt.setMahoso(txnId);
					tt.setHoten(regData.getSurnameLine1());
					tt.setNoisinh(regData.getAddressLine1());
					String cmnd = "";
					if (StringUtils.isNotEmpty(upJob.getNicTransaction()
							.getNin())) {
						String[] arr = upJob.getNicTransaction().getNin()
								.trim().split("");
						int length = 12;
						for (int i = 0; length > i; i++) {
							if (i > 0) {
								if (i < arr.length
										&& StringUtils.isNotEmpty(arr[i])) {
									cmnd += "<span class=\"epp-box\">" + arr[i]
											+ "</span>";
								} else
									cmnd += "<span class=\"epp-box\">&nbsp;&nbsp;</span>";
							}
						}
					}
					if (StringUtils.isNotEmpty(regData.getGender())
							&& regData.getGender().equals("M")) {
						tt.setGioitinhnam("x");
					} else if (StringUtils.isNotEmpty(regData.getGender())
							&& regData.getGender().equals("F")) {
						tt.setGioitinhnu("x");
					}
					tt.setCmnd(cmnd);
					if (regData.getDateNin() != null) {
						String ngaycap = formatter.format(regData.getDateNin());
						tt.setNgaycap(ngaycap);
					}
					tt.setNoicap(regData.getAddressNin());
					tt.setDantoc(regData.getNation());
					tt.setTongiao(regData.getReligion());
					tt.setPhoto(regData.getContactNo());
					tt.setDiachithuongtru(regData.getAddressLine1());
					// tt.setDiachithuongtru(regData.getAddressTempStreet());
					if (StringUtils.isNotEmpty(regData.getJob())) {
						CodeValues codeV = codesService.getCodeValueByIdName(
								"CODE_JOB", regData.getJob());
						if (codeV != null)
							tt.setNghenghiep(codeV.getCodeValueDesc());
						else
							tt.setNghenghiep(regData.getJob());
					}
					tt.setCha(regData.getFatherFullname());
					tt.setMe(regData.getMotherFullname());
					tt.setVochong(regData.getSpouseFullname());
					if (regData.getFatherDateOfBirth() != null) {
						String father = formatter.format(regData
								.getFatherDateOfBirth());
						tt.setNsinhcha(father);
					}
					if (regData.getMotherDateOfBirth() != null) {
						String mother = formatter.format(regData
								.getMotherDateOfBirth());
						tt.setNsinhme(mother);
					}
					if (regData.getSpouseDateOfBirth() != null) {
						String spouse = formatter.format(regData
								.getSpouseDateOfBirth());
						tt.setNsinhvochong(spouse);
					}
					tt.setSohclangannhat("");
					List<NicTransaction> lstNic = nicTransactionService
							.findAllByFields(upJob.getNicTransaction().getNin());
					if (lstNic != null && lstNic.size() > 0) {
						String tranBig = "";
						Date bigDate = null;
						for (NicTransaction nicTran : lstNic) {
							if (!nicTran.getTransactionId().equals(txnId)) {
								NicDocumentData doc = documentDataService
										.getDocumentDataById(
												nicTran.getTransactionId())
										.getModel();
								if (doc != null) {
									if (doc.getDateOfIssue() != null
											&& bigDate != null) {
										if (bigDate
												.before(doc.getDateOfIssue())) {
											bigDate = doc.getDateOfIssue();
											tranBig = doc.getId()
													.getTransactionId();
										}
									} else {
										tranBig = doc.getId()
												.getTransactionId();
										bigDate = doc.getDateOfIssue();
									}
								}
							}
						}

						if (StringUtils.isNotEmpty(tranBig)) {
							NicDocumentData nicDoc = documentDataService
									.getDocumentDataById(tranBig).getModel();
							if (nicDoc != null) {
								tt.setSohclangannhat(nicDoc.getId()
										.getPassportNo());
								if (nicDoc.getIssueDatetime() != null) {
									String issue = formatter.format(nicDoc
											.getIssueDatetime());
									tt.setCapngay(issue);
								}
							}
						}
					}
					tt.setNoidung("");
					/*
					 * if(regData.getTypeChild() != null &&
					 * regData.getTypeChild() > 0) tt.setTreemdikem("" +
					 * regData.getTypeChild() + " trẻ em đi kèm");
					 */

					List<NicTransactionAttachment> nicAttch = nicTransactionAttachmentService
							.findNicTransactionAttachments(txnId, "PH_CAP",
									"01").getListModel();
					if (nicAttch != null && nicAttch.size() > 0) {
						NicTransactionAttachment photo = nicAttch.get(0);
						tt.setPhoto("data:image/png;base64,"
								+ Base64.encodeBase64String(photo.getDocData()));
					} else {
						tt.setPhoto("/eppcentral/resources/images/No_Image.jpg");
					}
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		model.addAttribute("tt", tt);
		return modelAndView;
	}

	@ResponseBody
	@RequestMapping(value = "/loadSiteStream")
	public List<SiteConfig> loadSiteStream(@RequestParam String site,
			@RequestParam String type, HttpServletRequest httpRequest,
			WebRequest request, ModelMap model) {
		List<SiteConfig> list = new ArrayList<SiteConfig>();
		try {
			List<ConfigurationWorkflow> disableList = configurationWorkflowService
					.findSiteRepositoryByActive(site, type, new Date(), 0, true);
			if (disableList != null) {
				for (ConfigurationWorkflow cf : disableList) {
					SiteConfig config = new SiteConfig();
					config.setSiteId(cf.getSiteIdFrom());
					config.setStage(0);
					list.add(config);
				}
			}
			List<ConfigurationWorkflow> enableList = configurationWorkflowService
					.findSiteRepositoryByActive(site, type, new Date(), 1, true);
			if (enableList != null) {
				for (ConfigurationWorkflow cf : enableList) {
					SiteConfig config = new SiteConfig();
					config.setSiteId(cf.getSiteIdFrom());
					config.setStage(1);
					list.add(config);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	@ResponseBody
	@RequestMapping(value = "/saveSiteCheck")
	public Integer saveSiteCheck(@RequestParam Long idConfig,
			HttpServletRequest httpRequest, WebRequest request, ModelMap model) {

		try {
			String[] arrSite = httpRequest.getParameterValues("arrSite[]");
			String siteList = "";
			if (arrSite != null) {
				for (int i = 0; i < arrSite.length; i++) {
					siteList += arrSite[i] + ",";
				}
			}
			ConfigurationApi cf = configurationApiService.findConfigApiById(
					idConfig, null);
			if (cf != null) {
				cf.setCloseMember(siteList);
				configurationApiService.saveOrUpdateConfig(cf);
			}
			return 1;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}

	@ResponseBody
	@RequestMapping(value = "/getStatusCheck/{id}")
	public ModelAndView getStatusCheck(@PathVariable("id") Long id,
			HttpServletRequest httpRequest, WebRequest request, ModelMap model) {
		ModelAndView mav = new ModelAndView("check-statusq");
		String st = "";
		try {
			SyncQueueJob queue = queueJobService.findQueueByInfo(id, null);

			if (queue != null) {
				st = queue.getStatus();
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		model.addAttribute("st", st);
		return mav;
	}

	@ResponseBody
	@RequestMapping(value = "/saveStatusCheckQ/{idst}")
	public TempSync saveStatusCheckQ(@PathVariable("idst") Long id,
			@RequestParam("status") String status,
			@RequestParam("st") String st, HttpServletRequest httpRequest,
			WebRequest request, ModelMap model) {
		TempSync tmp = new TempSync();
		try {
			SyncQueueJob queue = queueJobService.findQueueByInfo(id, null);
			Date date = new Date();
			if (queue != null) {
				if (st.equals("NONE") && st.equals("PENDING")) {
					queue.setSyncRetry(0);
				}
				queue.setDateUpdateStatusHanding(date);
				queue.setStatus(status);
				queueJobService.saveOrUpdateQueue(queue);
				DateFormat dateFormat = new SimpleDateFormat(
						"yyyy-mm-dd hh:mm:ss.SSS");
				String strDate = dateFormat.format(date);
				tmp.setDate(strDate);
			}
			tmp.setCount(1);
			return tmp;
		} catch (Exception e) {
			e.printStackTrace();
		}
		tmp.setCount(0);
		return tmp;
	}

	@ResponseBody
	@RequestMapping(value = "/updateJobPriority", method = RequestMethod.POST)
	public Integer updateJobPriority(@RequestParam String jobId,
			@RequestParam int priority, HttpServletRequest httpRequest,
			WebRequest request, ModelMap model) {

		try {
			BaseModelSingle<NicUploadJob> job = nicUploadJobService
					.findUploadJobByTransId(jobId);
			if (job.isError() && job.getMessage() == null
					&& job.getModel() != null) {
				job.getModel().setJobPriority(priority);
				if (nicUploadJobService.saveOrUpdateService(job.getModel())
						.getModel()) {
					return 1;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}

	@ResponseBody
	@RequestMapping(value = "/saveSiteCheckQ")
	public Integer saveSiteCheckQ(@RequestParam Long idConfig,
			@RequestParam String site, HttpServletRequest httpRequest,
			WebRequest request, ModelMap model) {

		try {
			SyncQueueJob queue = queueJobService
					.findQueueByInfo(idConfig, null);
			if (queue != null) {
				queue.setReceiver(site);
				queueJobService.saveOrUpdateQueue(queue);
			}
			return 1;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}

	@ResponseBody
	@RequestMapping(value = "/saveSiteStream")
	public Integer saveSiteStream(@RequestParam String site,
			@RequestParam String type, @RequestParam String dateFrom,
			@RequestParam String dateTo, HttpServletRequest httpRequest,
			WebRequest request, ModelMap model) {
		HttpSession session = httpRequest.getSession();
		UserSession userSession = (UserSession) session
				.getAttribute("userSession");
		try {
			String[] arrSite = httpRequest.getParameterValues("arrSite[]");
			Date fromDate = HelperClass.convertStringToDate1(dateFrom + ":00");
			Date toDate = HelperClass.convertStringToDate1(dateTo + ":00");
			List<String> siteList = new ArrayList<String>();
			if (arrSite != null) {
				for (int i = 0; i < arrSite.length; i++) {
					ConfigurationWorkflow cf = configurationWorkflowService
							.findSiteRepositoryByType(site, arrSite[i], type);
					if (cf != null) {
						cf.setDateTimeFrom(fromDate);
						cf.setDateTimeTo(toDate);
						cf.setStage(true);
						Boolean result = configurationWorkflowService
								.saveOrUpdateConfig(cf);
						System.out
								.println("result save site stream: " + result);
					} else {
						ConfigurationWorkflow fw = new ConfigurationWorkflow();
						fw.setConfigType(type);
						fw.setDateTimeFrom(fromDate);
						fw.setDateTimeTo(toDate);
						fw.setSiteIdFrom(arrSite[i]);
						fw.setSiteIdTo(site);
						fw.setCreateBy(userSession.getUserId());
						fw.setCreateDate(new Date());
						fw.setStage(true);
						Boolean result = configurationWorkflowService
								.saveOrUpdateConfig(fw);
						System.out
								.println("result save site stream: " + result);
					}
					siteList.add(arrSite[i]);
				}
			}
			List<ConfigurationWorkflow> checkList = configurationWorkflowService
					.findSiteRepositoryByActive(site, type, null, 1, true);
			if (checkList != null) {
				for (ConfigurationWorkflow fw : checkList) {
					Boolean stage = false;
					for (String siteId : siteList) {
						if (fw.getSiteIdFrom().equals(siteId)) {
							stage = true;
							break;
						}
					}
					if (!stage) {
						fw.setStage(false);
						configurationWorkflowService.saveOrUpdateConfig(fw);
					}
				}
			}
			return 1;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}

	@RequestMapping(value = "/thongtinkhac/{txnId}")
	public ModelAndView thongtinkhac(@PathVariable String txnId,
			WebRequest request, HttpServletRequest httpRequest, ModelMap model) {

		ModelAndView modelAndView = new ModelAndView("central-thongtinkhac");
		List<InfoFamillyDto> tt = new ArrayList<InfoFamillyDto>();
		int stt = 0;
		if (StringUtils.isNotEmpty(txnId)) {
			BaseModelSingle<NicUploadJob> bGetUJ = nicUploadJobService
					.getUploadJobByTransactionIdSinger(null, txnId);
			NicUploadJob upJob = bGetUJ.getModel();
			if (upJob != null) {
				NicRegistrationData regData = upJob.getNicTransaction()
						.getNicRegistrationData();
				SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");

				if (StringUtils.isNotEmpty(regData.getFatherFullname())) {
					InfoFamillyDto father = new InfoFamillyDto();
					father.setStage_F("Cha");
					father.setFullName_F(regData.getFatherFullname());
					if (regData.getFatherDateOfBirth() != null) {
						String dob = formatter.format(regData
								.getFatherDateOfBirth());
						father.setDob_F(dob);
					}
					tt.add(father);
				}

				if (StringUtils.isNotEmpty(regData.getMotherFullname())) {
					InfoFamillyDto Mother = new InfoFamillyDto();
					Mother.setStage_F("Mẹ");
					Mother.setFullName_F(regData.getMotherFullname());
					if (regData.getMotherDateOfBirth() != null) {
						String dob = formatter.format(regData
								.getMotherDateOfBirth());
						Mother.setDob_F(dob);
					}
					tt.add(Mother);
				}

				if (StringUtils.isNotEmpty(regData.getSpouseFullname())) {
					InfoFamillyDto spouse = new InfoFamillyDto();
					spouse.setStage_F("Vợ/Chồng");
					spouse.setFullName_F(regData.getSpouseFullname());
					if (regData.getSpouseDateOfBirth() != null) {
						String dob = formatter.format(regData
								.getSpouseDateOfBirth());
						spouse.setDob_F(dob);
					}
					tt.add(spouse);
				}

				if (StringUtils.isNotEmpty(upJob.getNicTransaction()
						.getInfoPerson())) {
					String xmlPerson = upJob.getNicTransaction()
							.getInfoPerson();

					if (StringUtils.isNotEmpty(xmlPerson)) {
						JSONObject xmlJSONObj = XML.toJSONObject(xmlPerson);
						if (xmlJSONObj != null) {
							if (xmlJSONObj.has("EPP_PERSONS")
									&& !xmlJSONObj.isNull("EPP_PERSONS")) {
								JSONObject resp = xmlJSONObj
										.getJSONObject("EPP_PERSONS");
								if (resp.has("EPP_PERSON")
										&& !resp.isNull("EPP_PERSON")) {
									Object item = resp.get("EPP_PERSON");
									try {
										if (item instanceof JSONArray) {
											JSONArray arrayResp = (JSONArray) item;
											for (int i = 0; i < arrayResp
													.length(); i++) {
												JSONObject myResponse = new JSONObject();
												myResponse = arrayResp
														.getJSONObject(i);
												InfoFamillyDto spouse = new InfoFamillyDto();
												spouse.setStage_F("Con");
												if (myResponse.has("NAME")
														&& !myResponse
																.isNull("NAME")) {
													spouse.setFullName_F(myResponse
															.getString("NAME"));
												}
												if (myResponse
														.has("DATE_OF_BIRTH")
														&& !myResponse
																.isNull("DATE_OF_BIRTH")) {
													spouse.setDob_F(String.valueOf(myResponse
															.getInt("DATE_OF_BIRTH")));
												}
												tt.add(spouse);
											}
										} else {
											JSONObject myResponse = (JSONObject) item;
											InfoFamillyDto spouse = new InfoFamillyDto();
											spouse.setStage_F("Con");
											if (myResponse.has("NAME")
													&& !myResponse
															.isNull("NAME")) {
												spouse.setFullName_F(myResponse
														.getString("NAME"));
											}
											if (myResponse.has("DATE_OF_BIRTH")
													&& !myResponse
															.isNull("DATE_OF_BIRTH")) {
												spouse.setDob_F(String.valueOf(myResponse
														.getInt("DATE_OF_BIRTH")));
											}
											tt.add(spouse);
										}
									} catch (Exception e) {

									}
								}
							}
						}
					}
				}
			}
		}

		model.addAttribute("tt", tt);
		return modelAndView;
	}

	@RequestMapping(value = "/equalTransaction")
	public ModelAndView getInvestionJob(
			@ModelAttribute(value = "formData") InvestigationAssignmentData investigationAssignmentData,
			WebRequest request, HttpServletRequest httpRequest, ModelMap model) {
		// logger.info("NIC Investigation");
		PaginatedResult<NicUploadJobDto> pr = null;
		// PaginatedResult<NicUploadJobDto> rejectedList = null;
		try {
			// int pageNo = !StringUtils.isEmpty(request.getParameter("pageNo"))
			// ? Integer.parseInt(request.getParameter("pageNo")) : 1;
			// int pageSize = 20;
			// int startIndex = 0;
			HttpSession session = httpRequest.getSession();
			UserSession userSession = (UserSession) session
					.getAttribute("userSession");
			Map<String, String> userKhuVuc = new LinkedHashMap<String, String>();
			Users user = userService.findById(userSession.getUserId());
			if (user != null) {
				String khuVuc = user.getSiteGroupCode();
				List<Users> dsUser = userService.getListUserBySiteGroupAndRole(
						khuVuc, Constants.ROLE_ID_NIC_LANHDAOPHEDUYET);
				Collections.sort(dsUser, new Comparator<Users>() {
					@Override
					public int compare(Users o1, Users o2) {
						// TODO Auto-generated method stub
						return o1.getUserName().compareTo(o2.getUserName());
					}

				});
				if (dsUser != null && dsUser.size() > 0) {
					for (Users users : dsUser) {
						userKhuVuc.put(users.getUserId(), users.getUserName());
					}
				}
			}
			// List<CodeValues> list =
			// codesService.findAllByCodeId("NATIONALITY");
			// Map<String, String> danToc = new LinkedHashMap<String, String>();
			// if(list != null && list.size() > 0){
			// for(CodeValues code : list){
			// danToc.put(code.getId().getCodeValue(), code.getCodeValueDesc());
			// }
			// }
			httpRequest.setAttribute("pendingCount",
					uploadJobService.getPendingInvestigationsCount());

			if (investigationAssignmentData == null) {
				investigationAssignmentData = new InvestigationAssignmentData();
			}

			if (StringUtils.isEmpty(investigationAssignmentData.getTypeList())) {
				investigationAssignmentData.setTypeList("2");
			}

			investigationAssignmentData.cleanUpStrings();
			// pr =
			// uploadJobService.findAllForInvestigationPagination(userSession.getUserName(),
			// pageNo, pageSize);
			model.addAttribute("usersKhuVuc", userKhuVuc);
			// model.addAttribute("danToc", danToc);
			model.addAttribute("fnSelected",
					Constants.HEADING_NIC_INVESTIGATION);
			return new ModelAndView("central.tradoixm-bs.show");

		} catch (Exception e) {

			e.printStackTrace();
			return null;
		}

	}

	@RequestMapping(value = "/searchALLXNC")
	public ModelAndView searchALLXNC(
			@ModelAttribute(value = "formData") InvestigationAssignmentData investigationAssignmentData,
			WebRequest request, HttpServletRequest httpRequest, ModelMap model) {
		try {
			HttpSession session = httpRequest.getSession();
			UserSession userSession = (UserSession) session
					.getAttribute("userSession");
			Map<String, String> userKhuVuc = new LinkedHashMap<String, String>();
			Users user = userService.findById(userSession.getUserId());
			if (user != null) {
				String khuVuc = user.getSiteGroupCode();
				List<Users> dsUser = userService.getListUserBySiteGroupAndRole(
						khuVuc, Constants.ROLE_ID_NIC_LANHDAOPHEDUYET);
				Collections.sort(dsUser, new Comparator<Users>() {
					@Override
					public int compare(Users o1, Users o2) {
						// TODO Auto-generated method stub
						return o1.getUserName().compareTo(o2.getUserName());
					}

				});
				if (dsUser != null && dsUser.size() > 0) {
					for (Users users : dsUser) {
						userKhuVuc.put(users.getUserId(), users.getUserName());
					}
				}
			}
			List<CodeValues> list = codesService.findAllByCodeId("NATIONALITY");
			Map<String, String> danToc = new LinkedHashMap<String, String>();
			if (list != null && list.size() > 0) {
				for (CodeValues code : list) {
					danToc.put(code.getId().getCodeValue(),
							code.getCodeValueDesc());
				}
			}

			investigationAssignmentData.cleanUpStrings();
			model.addAttribute("pageSize", 10);
			model.addAttribute("pageNo", 1);
			model.addAttribute("totalPage", 1);
			model.addAttribute("startIndex", 0);
			model.addAttribute("totalRecord", 0);
			model.addAttribute("endIndex", 0);
			model.addAttribute("danToc", danToc);
			model.addAttribute("usersKhuVuc", userKhuVuc);
			model.addAttribute("fnSelected",
					Constants.HEADING_NIC_INVESTIGATION);
			return new ModelAndView("tracuu.tonghop.xnc");

		} catch (Exception e) {

			e.printStackTrace();
			return null;
		}

	}

	@RequestMapping(value = "/searchImmi")
	public ModelAndView searchImmi(
			@ModelAttribute(value = "formData") InvestigationAssignmentData inv,
			WebRequest request, HttpServletRequest httpRequest, ModelMap model) {
		PaginatedResult<ImmiHistoryDto> pr = null;
		List<ImmiHistoryDto> immiList = new ArrayList<ImmiHistoryDto>();
		try {
			int pageSize = Constants.DEFAULT_PAGE_MAX_SIZE;
			int pageNo = !StringUtils.isEmpty(request.getParameter("pageNo")) ? Integer
					.parseInt(request.getParameter("pageNo")) : 1;
			HttpSession session = httpRequest.getSession();
			UserSession userSession = (UserSession) session
					.getAttribute("userSession");
			Map<String, String> userKhuVuc = new LinkedHashMap<String, String>();
			Users user = userService.findById(userSession.getUserId());
			if (user != null) {
				String khuVuc = user.getSiteGroupCode();
				List<Users> dsUser = userService.getListUserBySiteGroupAndRole(
						khuVuc, Constants.ROLE_ID_NIC_LANHDAOPHEDUYET);
				Collections.sort(dsUser, new Comparator<Users>() {
					@Override
					public int compare(Users o1, Users o2) {
						// TODO Auto-generated method stub
						return o1.getUserName().compareTo(o2.getUserName());
					}

				});
				if (dsUser != null && dsUser.size() > 0) {
					for (Users users : dsUser) {
						userKhuVuc.put(users.getUserId(), users.getUserName());
					}
				}
			}
			List<CodeValues> list = codesService.findAllByCodeId("NATIONALITY");
			Map<String, String> danToc = new LinkedHashMap<String, String>();
			if (list != null && list.size() > 0) {
				for (CodeValues code : list) {
					danToc.put(code.getId().getCodeValue(),
							code.getCodeValueDesc());
				}
			}

			if (inv.getTypeList().equals("D")) {
				pr = immiService.getAllImmihistory(inv.getName(), inv.getDob(),
						inv.getGender(), inv.getPassportNo(), inv.getVisaNo(),
						inv.getNin(), inv.getPob(), pageNo, pageSize);
			} else {
				pr = immiService.getAllImmihistoryA(inv.getName(),
						Integer.parseInt(inv.getStartYear()),
						Integer.parseInt(inv.getEndYear()), inv.getGender(),
						inv.getPassportNo(), inv.getVisaNo(), inv.getNin(),
						inv.getPob(), pageNo, pageSize);
			}
			if (pr != null) {
				immiList = pr.getRows();
			} else {
				pr = new PaginatedResult<>(0, pageNo, immiList);
			}
			int firstResults = (pageNo - 1) < 0 ? 0 : (pageNo - 1) * pageSize;
			model.addAttribute("pageSize", pageSize);
			model.addAttribute("pageNo", pageNo);
			model.addAttribute("totalPage",
					pagingUtil.totalPage(pr.getTotal(), pageSize));
			model.addAttribute("startIndex",
					pr.getTotal() != 0 ? firstResults + 1 : 0);
			model.addAttribute("totalRecord", pr.getTotal());
			model.addAttribute("endIndex", firstResults + pr.getRowSize());
			model.addAttribute("dsXNC", immiList);
			model.addAttribute("danToc", danToc);
			model.addAttribute("usersKhuVuc", userKhuVuc);
			model.addAttribute("fnSelected",
					Constants.HEADING_NIC_INVESTIGATION);
			return new ModelAndView("tracuu.tonghop.xnc");

		} catch (Exception e) {

			e.printStackTrace();
		}
		return new ModelAndView("tracuu.tonghop.xnc");
	}

	@ResponseBody
	@RequestMapping(value = "/searchTraoDoiXM")
	public List<NicUploadJobDto> searchTraoDoiXM(
			@RequestParam String danhsachA, WebRequest request,
			HttpServletRequest servletRequest, ModelMap model) throws Exception {
		List<NicUploadJobDto> list = new ArrayList<NicUploadJobDto>();
		List<NicListHandover> listHan = handoverService
				.findListHandoverByCriteria(danhsachA,
						NicListHandover.TYPE_LIST_A, null);
		if (CollectionUtils.isNotEmpty(listHan)) {
			NicListHandover han = listHan.get(0);
			List<NicTransactionPackage> listPackage = nicTransactionPackageService
					.getListPackageByPackageId(han.getId().getPackageId());
			if (CollectionUtils.isNotEmpty(listPackage)) {
				for (NicTransactionPackage pk : listPackage) {
					NicTransactionPackage nk = nicTransactionPackageService
							.getListPackageByPackageIdAndStage(new String[] {
									"K", "C" }, pk.getTransactionId());
					if (nk != null) {
						NicUploadJobDto dto = new NicUploadJobDto();
						NicRegistrationData data = regDateService.findById(pk
								.getTransactionId());
						dto.setFullName(danhsachA
								+ " / "
								+ HelperClass.createFullName(
										data.getSurnameFull(),
										data.getMiddlenameFull(),
										data.getFirstnameFull()));
						dto.setTransactionId(pk.getTransactionId());
						list.add(dto);
					}

				}
			}
		}
		return list;
	}

	@RequestMapping(value = "/inXacMinhTraoDoi")
	public ModelAndView inXacMinhTraoDoi(@RequestParam String danhsach,
			WebRequest request, HttpServletRequest httpRequest, ModelMap model)
			throws DaoException {
		ModelAndView mav = new ModelAndView("in-traodoixm");
		List<NicUploadJobDto> list = new ArrayList<NicUploadJobDto>();
		String[] arr = danhsach.split(",");
		for (int i = 0; i < arr.length; i++) {
			NicUploadJobDto dto = new NicUploadJobDto();
			NicTransaction txn = nicTransactionService.findById(arr[i]);
			NicRegistrationData regData = regDateService.findById(arr[i]);
			if (regData != null) {
				dto.setFullName(HelperClass.createFullName(
						regData.getSurnameFull(), regData.getMiddlenameFull(),
						regData.getFirstnameFull()));
				dto.setGender(regData.getGender().equals("M") ? "Nam" : "Nữ");
				dto.setDob(HelperClass.loadDateOfBirth(HelperClass
						.convertDateToString2(regData.getDateOfBirth()),
						regData.getDefDateOfBirth()));
				String pob = codesService.getCodeValueDescByIdName("DISTRICT",
						regData.getPlaceOfBirth(), "");
				dto.setPlaceOfBirth(StringUtils.isNotEmpty(pob) ? pob
						: "&nbsp;");
				dto.setNation("&nbsp;");
				String nation = codesService.getCodeValueDescByIdName(
						"CODE_NATION", regData.getNation(), "");
				if (StringUtils.isNotEmpty(nation)) {
					dto.setNation(nation);
				}
				dto.setReligion("&nbsp;");
				String religion = codesService.getCodeValueDescByIdName(
						"CODE_RELIGION", regData.getReligion(), "");
				if (StringUtils.isNotEmpty(religion)) {
					dto.setReligion(religion);
				}
				dto.setJob("&nbsp;");
				String job = codesService.getCodeValueDescByIdName("JOB_TYPE",
						regData.getJob(), "");
				if (StringUtils.isNotEmpty(job)) {
					dto.setJob(job);
				}
				String address1 = regData.getAddressLine1();
				String ht = codesService.getCodeValueDescByIdName(
						RegistrationConstants.CODE_TOWN,
						regData.getAddressLine4(), "");
				String tp = codesService.getCodeValueDescByIdName(
						RegistrationConstants.CODE_DISTRICT,
						regData.getAddressLine5(), "");
				if (StringUtils.isNotEmpty(ht)) {
					address1 += ", " + ht;
				}
				if (StringUtils.isNotEmpty(tp)) {
					address1 += ", " + tp;
				}
				dto.setAddress1(address1);
				String address2 = ""; // regData.getAddressTempStreet();
				String ht2 = codesService.getCodeValueDescByIdName(
						RegistrationConstants.CODE_TOWN,
						regData.getAddressTempDistrict(), "");
				// String tp2 =
				// codesService.getCodeValueDescByIdName(RegistrationConstants.CODE_DISTRICT,
				// regData.getAddressTempCity(), "");
				if (StringUtils.isNotEmpty(ht2)) {
					address2 += ", " + ht2;
				}
				/*
				 * if(StringUtils.isNotEmpty(tp2)){ address2 += ", " + tp2; }
				 */
				dto.setAddress2(StringUtils.isNotEmpty(address2) ? address2
						: "&nbsp;");
				dto.setDateNin(HelperClass.convertDateToString2(regData
						.getDateNin()));
				String nationality = codesService.getCodeValueDescByIdName(
						RegistrationConstants.NATIONALITY_CODE_ID,
						regData.getNationality(), "");
				dto.setNationality(nationality);
			}
			if (txn != null) {
				dto.setNin(txn.getNin());
			}
			String day = HelperClass.convertDateToString2(Calendar
					.getInstance().getTime());
			String[] arrDay = day.split("/");
			dto.setNgay(arrDay[0]);
			dto.setThang(arrDay[1]);
			dto.setNam(arrDay[2]);

			List<NicTransactionAttachment> photoList = nicTransactionAttachmentService
					.findNicTransactionAttachments(arr[i],
							NicTransactionAttachment.DOC_TYPE_PHOTO_CAPTURE,
							NicTransactionAttachment.DEFAULT_SERIAL_NO)
					.getListModel();
			if (CollectionUtils.isNotEmpty(photoList) && photoList.size() > 0) {
				String images = Base64.encodeBase64String(photoList.get(0)
						.getDocData());
				dto.setPhotoStr(images);
			} else {
				dto.setPhotoStr("");
			}
			dto.setNoteInvestigation("&nbsp;");
			list.add(dto);
		}
		mav.addObject("dsDTO", list);
		return mav;
	}

	@RequestMapping(value = "/inDanhSachHS")
	public ModelAndView inDanhSachHS(@RequestParam String danhsach,
			@RequestParam String danhSachA, WebRequest request,
			HttpServletRequest httpRequest, ModelMap model)
			throws DaoException, JAXBException {
		ModelAndView mav = new ModelAndView("in-danhsach-hoso");
		List<NicUploadJobDto> list = new ArrayList<NicUploadJobDto>();
		String[] arr = danhsach.split(",");
		int count = arr.length;
		int countPerson = 0;
		for (int i = 0; i < arr.length; i++) {
			NicUploadJobDto dto = new NicUploadJobDto();
			dto.setStt(i + 1);
			NicTransaction txn = nicTransactionService.findById(arr[i]);
			NicRegistrationData regData = regDateService.findById(arr[i]);
			if (regData != null) {
				dto.setFullName(HelperClass.createFullName(
						regData.getSurnameFull(), regData.getMiddlenameFull(),
						regData.getFirstnameFull()));
				dto.setGender(regData.getGender().equals("M") ? "Nam" : "Nữ");
				dto.setDob(HelperClass.loadDateOfBirth(HelperClass
						.convertDateToString2(regData.getDateOfBirth()),
						regData.getDefDateOfBirth()));
				String pob = codesService.getCodeValueDescByIdName("DISTRICT",
						regData.getPlaceOfBirth(), "");
				dto.setPlaceOfBirth(pob);
				// String nation =
				// codesService.getCodeValueDescByIdName("CODE_NATION",
				// regData.getNation(), "");
				// if(StringUtils.isNotEmpty(nation)){
				// dto.setNation(nation);
				// }
				// String religion =
				// codesService.getCodeValueDescByIdName("CODE_RELIGION",
				// regData.getReligion(), "");
				// if(StringUtils.isNotEmpty(religion)){
				// dto.setReligion(religion);
				// }
				String address1 = regData.getAddressLine1();
				String ht = codesService.getCodeValueDescByIdName(
						RegistrationConstants.CODE_TOWN,
						regData.getAddressLine4(), "");
				String tp = codesService.getCodeValueDescByIdName(
						RegistrationConstants.CODE_DISTRICT,
						regData.getAddressLine5(), "");
				if (StringUtils.isNotEmpty(ht)) {
					address1 += ", " + ht;
				}
				if (StringUtils.isNotEmpty(tp)) {
					address1 += ", " + tp;
				}
				dto.setAddress1(address1);
				// String address2 = regData.getAddressTempStreet();
				// String ht2 =
				// codesService.getCodeValueDescByIdName(RegistrationConstants.CODE_TOWN,
				// regData.getAddressTempDistrict(), "");
				// String tp2 =
				// codesService.getCodeValueDescByIdName(RegistrationConstants.CODE_DISTRICT,
				// regData.getAddressTempCity(), "");
				// if(StringUtils.isNotEmpty(ht2)){
				// address2 += ", " + ht2;
				// }
				// if(StringUtils.isNotEmpty(tp2)){
				// address2 += ", " + tp2;
				// }
				// dto.setAddress2(address2);
				// dto.setDateNin(HelperClass.convertDateToString2(regData.getDateNin()));
				// String nationality =
				// codesService.getCodeValueDescByIdName(RegistrationConstants.NATIONALITY_CODE_ID,
				// regData.getNationality(), "");
				// dto.setNationality(nationality);
			}
			if (txn != null) {
				dto.setNin(txn.getNin());
				if (StringUtils.isNotEmpty(txn.getInfoPerson())) {
					JAXBContext jaxbContext = JAXBContext
							.newInstance(InfoPersons.class);
					Unmarshaller unmarshaller = jaxbContext
							.createUnmarshaller();
					StringReader reader = new StringReader(txn.getInfoPerson());
					InfoPersons persons = (InfoPersons) unmarshaller
							.unmarshal(reader);
					List<InfoPerson> listPerson = null;
					if (persons != null) {
						listPerson = persons.getInfoPersons();
						if (listPerson != null && listPerson.size() > 0) {
							countPerson += listPerson.size();
						}
					}
				}
				dto.setRecieptNo(txn.getRecieptNo());
			}
			String day = HelperClass.convertDateToString2(Calendar
					.getInstance().getTime());
			String[] arrDay = day.split("/");
			dto.setNgay(arrDay[0]);
			dto.setThang(arrDay[1]);
			dto.setNam(arrDay[2]);

			List<NicTransactionAttachment> photoList = nicTransactionAttachmentService
					.findNicTransactionAttachments(arr[i],
							NicTransactionAttachment.DOC_TYPE_PHOTO_CAPTURE,
							NicTransactionAttachment.DEFAULT_SERIAL_NO)
					.getListModel();
			if (CollectionUtils.isNotEmpty(photoList) && photoList.size() > 0) {
				String images = Base64.encodeBase64String(photoList.get(0)
						.getDocData());
				dto.setPhotoStr(images);
			} else {
				dto.setPhotoStr("");
			}
			dto.setNoA(danhSachA);
			list.add(dto);
		}
		list.get(0).setCount(count);
		list.get(0).setArchiveCodeStt(count + countPerson);
		mav.addObject("root", list.get(0));
		mav.addObject("dsDTO", list);
		return mav;
	}

	@RequestMapping(value = "/changProcessJob")
	public ModelAndView changProcessJob(WebRequest request,
			HttpServletRequest httpRequest, ModelMap model) {
		ModelAndView mav = new ModelAndView("central.dieuphoiluong.show");
		return mav;
	}

	@RequestMapping(value = "/createApi")
	public ModelAndView createApi(
			@ModelAttribute(value = "formData") InvestigationAssignmentData inv,
			WebRequest request, HttpServletRequest httpRequest, ModelMap model) {
		ModelAndView mav = new ModelAndView("save.quanly.api");
		return mav;
	}

	@RequestMapping("/saveApiForm")
	public ModelAndView saveApiForm(
			@ModelAttribute(value = "formData") InvestigationAssignmentData inv,
			WebRequest request, HttpServletRequest httpRequest, ModelMap model) {
		HttpSession session = httpRequest.getSession();
		UserSession userSession = (UserSession) session
				.getAttribute("userSession");
		ModelAndView mav = new ModelAndView("central.dieuphoiapi.show");
		int pageSize = Constants.DEFAULT_PAGE_MAX_SIZE;
		int pageNo = !StringUtils.isEmpty(request.getParameter("pageNo")) ? Integer
				.parseInt(request.getParameter("pageNo")) : 1;
		try {
			ConfigurationApi checkApi = configurationApiService
					.findConfigApiByUrl(inv.getUrl(), inv.getNameApi());
			if (checkApi != null) {
				model.addAttribute("result", 0);
			} else {
				ConfigurationApi cfg = new ConfigurationApi();
				cfg.setUriApi(inv.getUrl());
				cfg.setNameApi(inv.getNameApi());
				cfg.setType(Integer.parseInt(inv.getTypeList()));
				cfg.setDescription(inv.getListName());
				cfg.setCloseAll(inv.getTypeInvestigation().equals("N") ? false
						: true);
				if (inv.getLoadJobIds() != null) {
					String str = "";
					for (String site : inv.getLoadJobIds()) {
						str += site + ",";
					}
					cfg.setCloseMember(str);
				}
				cfg.setCreateDate(new Date());
				cfg.setCreateBy(userSession.getUserId());
				configurationApiService.saveOrUpdateConfig(cfg);
				model.addAttribute("result", 1);
			}
		} catch (Exception e) {
			model.addAttribute("result", -1);
		}

		List<ConfigurationApiDto> list = new ArrayList<ConfigurationApiDto>();
		Boolean stage = null;
		if (StringUtils.isNotEmpty(inv.getStageLoad())) {
			stage = inv.getStageLoad().equals("Y") ? true : false;
		}
		PaginatedResult<ConfigurationApiDto> pr = configurationApiService
				.findListApiBySearch(inv.getListCode(), inv.getName(), stage,
						pageNo, pageSize);
		if (pr != null) {
			list = pr.getRows();
		} else {
			pr = new PaginatedResult<>(0, pageNo, list);
		}
		int firstResults = (pageNo - 1) < 0 ? 0 : (pageNo - 1) * pageSize;
		model.addAttribute("pageSize", pageSize);
		model.addAttribute("pageNo", pageNo);
		model.addAttribute("totalPage",
				pagingUtil.totalPage(pr.getTotal(), pageSize));
		model.addAttribute("startIndex", pr.getTotal() != 0 ? firstResults + 1
				: 0);
		model.addAttribute("totalRecord", pr.getTotal());
		model.addAttribute("endIndex", firstResults + pr.getRowSize());
		model.addAttribute("dsApi", list);
		return mav;
	}

	@RequestMapping(value = "/showQueue")
	public ModelAndView showQueue(
			@ModelAttribute(value = "formData") InvestigationAssignmentData inv,
			@RequestParam(required = false) String receiver,
			@RequestParam(required = false) String objType,
			@RequestParam(required = false) String objStatus,
			WebRequest request, HttpServletRequest httpRequest, ModelMap model) {
		ModelAndView mav = new ModelAndView("queue.quanly.show");
		int pageSize = Constants.DEFAULT_PAGE_MAX_SIZE;
		int pageNo = !StringUtils.isEmpty(request.getParameter("pageNo")) ? Integer
				.parseInt(request.getParameter("pageNo")) : 1;
		List<SyncQueueDto> list = new ArrayList<SyncQueueDto>();
		if (StringUtils.isNotBlank(objStatus)) {
			inv = new InvestigationAssignmentData();
			inv.setTypeList(objType);
			inv.setStageLoad(objStatus);
			inv.setNation(receiver);
		}
		PaginatedResult<SyncQueueDto> pr = queueJobService
				.findListQueueBySearch(inv.getSearchTransactionId(),
						inv.getTypeList(), inv.getStageLoad(), inv.getNation(),
						pageNo, pageSize);
		if (pr != null) {
			list = pr.getRows();
		} else {
			pr = new PaginatedResult<SyncQueueDto>(0, pageNo, list);
		}
		int firstResults = (pageNo - 1) < 0 ? 0 : (pageNo - 1) * pageSize;
		model.addAttribute("formData", inv);
		model.addAttribute("pageSize", pageSize);
		model.addAttribute("pageNo", pageNo);
		model.addAttribute("totalPage",
				pagingUtil.totalPage(pr.getTotal(), pageSize));
		model.addAttribute("startIndex", pr.getTotal() != 0 ? firstResults + 1
				: 0);
		model.addAttribute("totalRecord", pr.getTotal());
		model.addAttribute("endIndex", firstResults + pr.getRowSize());
		model.addAttribute("dsQueue", list);
		return mav;
	}

	@RequestMapping(value = "/showJob")
	public ModelAndView showJob(
			@ModelAttribute(value = "formData") InvestigationAssignmentData inv,
			WebRequest request, HttpServletRequest httpRequest, ModelMap model) {
		ModelAndView mav = new ModelAndView("central.jobManager.show");
		int pageSize = Constants.DEFAULT_PAGE_MAX_SIZE;
		int pageNo = !StringUtils.isEmpty(request.getParameter("pageNo")) ? Integer
				.parseInt(request.getParameter("pageNo")) : 1;
		List<InvestigationAssignmentData> list = new ArrayList<InvestigationAssignmentData>();
		String listTranId = "";
		if (StringUtils.isNotBlank(inv.getJobId())
				|| StringUtils.isNotBlank(inv.getName())
				|| StringUtils.isNotBlank(inv.getDateOfBirth())
				|| StringUtils.isNotBlank(inv.getGender())
				|| StringUtils.isNotBlank(inv.getNin())) {
			if (StringUtils.isNotBlank(inv.getName())
					|| StringUtils.isNotBlank(inv.getDateOfBirth())
					|| StringUtils.isNotBlank(inv.getGender())) {
				List<NicRegistrationData> listReg = null;
				String[] dateOfBirth = inv.getDateOfBirth().split("-");
				String dob = dateOfBirth[0] + dateOfBirth[1] + dateOfBirth[2];
				try {
					listReg = regDateService.findRegByPersonInfo(inv.getName(),
							inv.getGender(), dob);
				} catch (Exception e) {
					e.printStackTrace();
				}
				if (listReg != null && listReg.size() > 0) {
					for (NicRegistrationData nicRegistrationData : listReg) {
						if (listTranId.length() < 1) {
							listTranId = nicRegistrationData.getTransactionId();
						} else {
							listTranId += ","
									+ nicRegistrationData.getTransactionId();
						}
					}
				}
			}
			if (StringUtils.isNotBlank(inv.getJobId())) {
				if (listTranId.length() < 1) {
					listTranId = inv.getJobId();
				} else {
					listTranId += "," + inv.getJobId();
				}
			}
			if (StringUtils.isNotBlank(inv.getNin())) {
				List<NicTransaction> listTran = null;
				try {
					listTran = nicTransactionService.findAllByFields(inv
							.getNin());
				} catch (Exception e) {
					e.printStackTrace();
				}
				if (listTran != null && listTran.size() > 0) {
					for (NicTransaction nicTransaction : listTran) {
						if (listTranId.length() < 1) {
							listTranId = nicTransaction.getTransactionId();
						} else {
							listTranId += ","
									+ nicTransaction.getTransactionId();
						}
					}
				}
			}
		}
		int firstResults = (pageNo - 1) < 0 ? 0 : (pageNo - 1) * pageSize;
		PaginatedResult<NicUploadJob> pr = null;
		try {
			pr = uploadJobService.getPageJobsInQueue(listTranId.split(","),
					pageNo, pageSize);
		} catch (NicUploadJobServiceException e) {
			e.printStackTrace();
		}
		if (pr != null) {
			if (pr.getRows() != null && pr.getRows().size() > 0) {
				int stt = firstResults + 1;
				for (NicUploadJob job : pr.getRows()) {
					InvestigationAssignmentData data = new InvestigationAssignmentData();
					NicTransaction tran = job.getNicTransaction();
					NicRegistrationData reg = tran.getNicRegistrationData();
					data.setJobId(tran.getTransactionId());
					data.setName(HelperClass.createFullName(
							reg.getSurnameFull(), reg.getMiddlenameFull(),
							reg.getFirstnameFull()));
					data.setGender(StringUtils.isNotBlank(reg.getGender()) ? (reg
							.getGender().equals("M") ? "Nam" : (reg.getGender()
							.equals("F") ? "Nữ" : "Không rõ")) : null);
					data.setDateOfBirth(reg.getDefDateOfBirth() != null ? DateUtil
							.parseDate(reg.getDateOfBirth(),
									DateUtil.FORMAT_DDdashMMdashYYYY) : "");
					data.setNin(tran.getNin());
					data.setPriority(job.getJobPriority());
					data.setStt(stt);
					list.add(data);
					stt++;
				}
			}
		} else {
			pr = new PaginatedResult<NicUploadJob>(0, pageNo,
					new ArrayList<NicUploadJob>());
		}

		// model.addAttribute("formData", inv);
		model.addAttribute("pageSize", pageSize);
		model.addAttribute("pageNo", pageNo);
		model.addAttribute("totalPage",
				pagingUtil.totalPage(pr.getTotal(), pageSize));
		model.addAttribute("startIndex", pr.getTotal() != 0 ? firstResults + 1
				: 0);
		model.addAttribute("totalRecord", pr.getTotal());
		model.addAttribute("endIndex", firstResults + pr.getRowSize());
		model.addAttribute("listJob", list);
		return mav;
	}

	@RequestMapping(value = "/changApiJob")
	public ModelAndView changApiJob(
			@ModelAttribute(value = "formData") InvestigationAssignmentData inv,
			WebRequest request, HttpServletRequest httpRequest, ModelMap model) {
		ModelAndView mav = new ModelAndView("central.dieuphoiapi.show");
		int pageSize = Constants.DEFAULT_PAGE_MAX_SIZE;
		int pageNo = !StringUtils.isEmpty(request.getParameter("pageNo")) ? Integer
				.parseInt(request.getParameter("pageNo")) : 1;
		List<ConfigurationApiDto> list = new ArrayList<ConfigurationApiDto>();
		Boolean stage = null;
		if (StringUtils.isNotEmpty(inv.getStageLoad())) {
			stage = inv.getStageLoad().equals("Y") ? true : false;
		}
		PaginatedResult<ConfigurationApiDto> pr = configurationApiService
				.findListApiBySearch(inv.getListCode(), inv.getName(), stage,
						pageNo, pageSize);
		if (pr != null) {
			list = pr.getRows();
		} else {
			pr = new PaginatedResult<>(0, pageNo, list);
		}
		int firstResults = (pageNo - 1) < 0 ? 0 : (pageNo - 1) * pageSize;
		model.addAttribute("pageSize", pageSize);
		model.addAttribute("pageNo", pageNo);
		model.addAttribute("totalPage",
				pagingUtil.totalPage(pr.getTotal(), pageSize));
		model.addAttribute("startIndex", pr.getTotal() != 0 ? firstResults + 1
				: 0);
		model.addAttribute("totalRecord", pr.getTotal());
		model.addAttribute("endIndex", firstResults + pr.getRowSize());
		model.addAttribute("dsApi", list);
		return mav;
	}

	@ResponseBody
	@RequestMapping(value = "/changeAllApi")
	public Integer changeAllApi(@RequestParam String idConfig,
			@RequestParam Integer stage, WebRequest request,
			HttpServletRequest httpRequest, ModelMap model) {
		try {
			Long id = Long.parseLong(idConfig);
			ConfigurationApi cf = configurationApiService.findConfigApiById(id,
					null);
			if (cf != null) {
				cf.setCloseAll(stage == 1 ? false : true);
				configurationApiService.saveOrUpdateConfig(cf);
			}
			return 1;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}

	@ResponseBody
	@RequestMapping(value = "/getSiteCheckApi/{idApi}/{type}")
	public ModelAndView getSiteCheckApi(@PathVariable Long idApi,
			@PathVariable Integer type, WebRequest request,
			HttpServletRequest httpRequest, ModelMap model) {
		ModelAndView mav = new ModelAndView("check-site");
		try {
			Map<String, String> list = new LinkedHashMap<String, String>();
			List<String> siteFocus = new ArrayList<String>();
			if (type == 1) {
				List<SiteRepository> listSite = siteService
						.findAllByActive(ACTIVE_SITE_Y);
				this.createCode();
				Collections.sort(listSite, new Comparator<SiteRepository>() {
					@Override
					public int compare(SiteRepository o1, SiteRepository o2) {
						// TODO Auto-generated method stub
						return generator(o1.getSiteName()).compareTo(
								generator(o2.getSiteName()));
					}

				});
				for (SiteRepository sr : listSite) {
					list.put(sr.getSiteId(), sr.getSiteName());
				}
			}
			if (type == 2) {
				List<NicPersoLocations> listSite = persoLocationService
						.findPersoByStatus(1, null);
				this.createCode();
				Collections.sort(listSite, new Comparator<NicPersoLocations>() {
					@Override
					public int compare(NicPersoLocations o1,
							NicPersoLocations o2) {
						// TODO Auto-generated method stub
						return generator(o1.getName()).compareTo(
								generator(o2.getName()));
					}

				});
				for (NicPersoLocations sr : listSite) {
					list.put(sr.getCode(), sr.getName());
				}
			}
			if (type == 3) {
				List<BorderGate> gateList = borderGateService
						.findBorderGateBySync(ACTIVE_SITE_Y);
				this.createCode();
				Collections.sort(gateList, new Comparator<BorderGate>() {
					@Override
					public int compare(BorderGate o1, BorderGate o2) {
						// TODO Auto-generated method stub
						return generator(o1.getName()).compareTo(
								generator(o2.getName()));
					}

				});
				for (BorderGate sr : gateList) {
					list.put(sr.getCode(), sr.getName());
				}
			}
			ConfigurationApi cfa = configurationApiService.findConfigApiById(
					idApi, null);
			if (cfa != null && StringUtils.isNotEmpty(cfa.getCloseMember())) {
				String[] arrSite = cfa.getCloseMember().split(",");
				for (int i = 0; i < arrSite.length; i++) {
					siteFocus.add(arrSite[i]);
				}
			}
			model.addAttribute("siteList", list);
			model.addAttribute("siteFocus", siteFocus);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return mav;
	}

	@ResponseBody
	@RequestMapping(value = "/getSiteCheck/{type}")
	public ModelAndView getSiteCheck(@PathVariable String type,
			WebRequest request, HttpServletRequest httpRequest, ModelMap model) {
		ModelAndView mav = new ModelAndView("check-siteq");
		try {
			Map<String, String> list = new LinkedHashMap<String, String>();
			if (type.equals("HS") || type.equals("DA") || type.equals("DC")
					|| type.equals("BF") || type.equals("PS")) {
				List<SiteRepository> listSite = siteService
						.findAllByActive(ACTIVE_SITE_Y);
				this.createCode();
				Collections.sort(listSite, new Comparator<SiteRepository>() {
					@Override
					public int compare(SiteRepository o1, SiteRepository o2) {
						// TODO Auto-generated method stub
						return generator(o1.getSiteName()).compareTo(
								generator(o2.getSiteName()));
					}

				});
				for (SiteRepository sr : listSite) {
					list.put(sr.getSiteId(), sr.getSiteName());
				}
			}
			if (type.equals("IN")) {
				List<NicPersoLocations> listSite = persoLocationService
						.findPersoByStatus(1, null);
				this.createCode();
				Collections.sort(listSite, new Comparator<NicPersoLocations>() {
					@Override
					public int compare(NicPersoLocations o1,
							NicPersoLocations o2) {
						// TODO Auto-generated method stub
						return generator(o1.getName()).compareTo(
								generator(o2.getName()));
					}

				});
				for (NicPersoLocations sr : listSite) {
					list.put(sr.getCode(), sr.getName());
				}
			}

			model.addAttribute("siteList", list);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return mav;
	}

	@ResponseBody
	@RequestMapping(value = "/updateSiteStream")
	public Integer updateSiteStream(@RequestParam String idConfig,
			@RequestParam Integer stage, WebRequest request,
			HttpServletRequest httpRequest, ModelMap model) {
		try {
			Long id = Long.parseLong(idConfig);
			ConfigurationWorkflow cf = configurationWorkflowService
					.findConfigById(id, null);
			if (cf != null) {
				cf.setStage(stage == 1 ? true : false);
				configurationWorkflowService.saveOrUpdateConfig(cf);
			}
			return 1;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}

	@RequestMapping(value = "/detailSiteStream")
	public ModelAndView detailSiteStream(
			@ModelAttribute(value = "formData") InvestigationAssignmentData inv,
			WebRequest request, HttpServletRequest httpRequest, ModelMap model) {
		ModelAndView mav = new ModelAndView("detail.dieuphoiluong.show");
		int pageSize = Constants.DEFAULT_PAGE_MAX_SIZE;
		int pageNo = !StringUtils.isEmpty(request.getParameter("pageNo")) ? Integer
				.parseInt(request.getParameter("pageNo")) : 1;
		List<NicUploadJobDto> list = new ArrayList<NicUploadJobDto>();
		PaginatedResult<NicUploadJobDto> pr = configurationWorkflowService
				.findPaginateBySearch(inv.getTransactionType(),
						inv.getStageLoad(), inv.getRegSiteCode(), new Date(),
						pageNo, pageSize);
		if (pr != null) {
			list = pr.getRows();
		} else {
			pr = new PaginatedResult<>(0, pageNo, list);
		}
		int firstResults = (pageNo - 1) < 0 ? 0 : (pageNo - 1) * pageSize;
		model.addAttribute("pageSize", pageSize);
		model.addAttribute("pageNo", pageNo);
		model.addAttribute("totalPage",
				pagingUtil.totalPage(pr.getTotal(), pageSize));
		model.addAttribute("startIndex", pr.getTotal() != 0 ? firstResults + 1
				: 0);
		model.addAttribute("totalRecord", pr.getTotal());
		model.addAttribute("endIndex", firstResults + pr.getRowSize());
		model.addAttribute("dsXuLyA", list);
		return mav;
	}

	@RequestMapping(value = "/inBoTucHS")
	public ModelAndView inBoTucHS(@RequestParam String danhsach,
			WebRequest request, HttpServletRequest httpRequest, ModelMap model)
			throws DaoException, JAXBException {
		ModelAndView mav = new ModelAndView("in-botuchoso");
		List<NicUploadJobDto> list = new ArrayList<NicUploadJobDto>();
		String[] arr = danhsach.split(",");
		for (int i = 0; i < arr.length; i++) {
			NicUploadJobDto dto = new NicUploadJobDto();
			NicRegistrationData regData = regDateService.findById(arr[i]);
			if (regData != null) {
				dto.setFullName(HelperClass.createFullName(
						regData.getSurnameFull(), regData.getMiddlenameFull(),
						regData.getFirstnameFull()));
				String address1 = regData.getAddressLine1();
				String ht = codesService.getCodeValueDescByIdName(
						RegistrationConstants.CODE_TOWN,
						regData.getAddressLine4(), "");
				String tp = codesService.getCodeValueDescByIdName(
						RegistrationConstants.CODE_DISTRICT,
						regData.getAddressLine5(), "");
				if (StringUtils.isNotEmpty(ht)) {
					address1 += ", " + ht;
				}
				if (StringUtils.isNotEmpty(tp)) {
					address1 += ", " + tp;
				}
				dto.setAddress1(address1);
			}
			String day = HelperClass.convertDateToString2(Calendar
					.getInstance().getTime());
			String[] arrDay = day.split("/");
			dto.setNgay(arrDay[0]);
			dto.setThang(arrDay[1]);
			dto.setNam(arrDay[2]);
			list.add(dto);
		}
		mav.addObject("dsDTO", list);
		return mav;
	}

	// templatePassport by Dung~98
	@Autowired
	private TemplatePassportImgDao templatePassportImgDao;

	@Autowired
	private TemplatePassportDao templatePassportDao;

	@RequestMapping(value = "/showTemplatePassport")
	public ModelAndView showTemplatePassport(
			@ModelAttribute(value = "formData") InvestigationAssignmentData inv,
			WebRequest request, HttpServletRequest httpRequest, ModelMap model)
			throws DaoException {
		ModelAndView mav = new ModelAndView("template.passport");
		int pageSize = Constants.DEFAULT_PAGE_MAX_SIZE;
		int pageNo = !StringUtils.isEmpty(request.getParameter("pageNo")) ? Integer
				.parseInt(request.getParameter("pageNo")) : 1;
		List<TemplatePassport> list = new ArrayList<TemplatePassport>();
		PaginatedResult<TemplatePassport> pr = templatePassportDao
				.findListPassport(inv.getNameNationVie(),
						inv.getPassPortType(), inv.getYearIssue(), pageNo,
						pageSize);

		if (pr != null) {
			list = pr.getRows();

		} else {
			pr = new PaginatedResult<TemplatePassport>(0, pageNo, list);
		}
		int firstResults = (pageNo - 1) < 0 ? 0 : (pageNo - 1) * pageSize;
		model.addAttribute("pageSize", pageSize);
		model.addAttribute("pageNo", pageNo);
		model.addAttribute("totalPage",
				pagingUtil.totalPage(pr.getTotal(), pageSize));
		model.addAttribute("startIndex", pr.getTotal() != 0 ? firstResults + 1
				: 0);
		model.addAttribute("totalRecord", pr.getTotal());
		model.addAttribute("endIndex", firstResults + pr.getRowSize());
		model.addAttribute("dsQueue", list);
		model.addAttribute("resultSeach", templatePassportDao.getNationName());
		return mav;
	}

	@RequestMapping(value = "/addPassportForm")
	public ModelAndView addPassportForm(
			@ModelAttribute(value = "formDataAdd") InvestigationAssignmentData inv,
			WebRequest request, HttpServletRequest httpRequest, ModelMap model) {
		ModelAndView mav = new ModelAndView("addPassportForm");
		model.addAttribute("nation", templatePassportDao.getCodeNation());
		return mav;
	}

	@ResponseBody
	@RequestMapping(value = "/getvalueNationName/{nation}")
	public String getvalueNationName(@PathVariable String nation) {
		String NameNation = templatePassportDao.getCodeNationName(nation)
				.get(0);
		return NameNation;
	}

	@RequestMapping(value = "/chitietTemplatePassport/{templatePassportId}")
	public ModelAndView chitietTemplatePassport(ModelMap model,
			@PathVariable String templatePassportId) throws DaoException {
		ModelAndView mav = new ModelAndView("chitietTemplatePassport");
		List<TemplatePassport> list = templatePassportDao
				.findListByTemplatePassportId(templatePassportId);
		model.addAttribute("queue", list.get(0));
		List<TemplatePassportImage> passportImages = new ArrayList<TemplatePassportImage>();
		String decodedDocstring = "";
		passportImages = templatePassportImgDao
				.findListTemplateIMGByTemplatePassportId(templatePassportId,
						"IMGPRE");
		if (passportImages.size() == 1) {
			decodedDocstring = Base64.encodeBase64String(passportImages.get(0)
					.getDocData());
		}
		model.addAttribute("imgpre", decodedDocstring);
		decodedDocstring = "";
		passportImages = templatePassportImgDao
				.findListTemplateIMGByTemplatePassportId(templatePassportId,
						"IMGPREUV");
		if (passportImages.size() == 1) {
			decodedDocstring = Base64.encodeBase64String(passportImages.get(0)
					.getDocData());
		}
		model.addAttribute("imgpreuv", decodedDocstring);
		decodedDocstring = "";
		passportImages = templatePassportImgDao
				.findListTemplateIMGByTemplatePassportId(templatePassportId,
						"IMGFACE");
		if (passportImages.size() == 1) {
			decodedDocstring = Base64.encodeBase64String(passportImages.get(0)
					.getDocData());
		}
		model.addAttribute("imgface", decodedDocstring);
		return mav;
	}

	@ResponseBody
	@RequestMapping(value = "/addPassport")
	public Integer addPassport(HttpServletRequest httpRequest,
			@RequestBody InvestigationAssignmentData obj) throws Exception {

		try {
			HttpSession session = httpRequest.getSession();
			UserSession userSession = (UserSession) session
					.getAttribute("userSession");
			TemplatePassport templatePassport = new TemplatePassport();
			templatePassport.setNation(obj.getNation());
			templatePassport.setNameNationVie(obj.getNameNationVie());
			templatePassport.setPassPortType(obj.getPassPortType());
			templatePassport.setYearIssue(obj.getYearIssue());
			templatePassport.setCreateDatetime(new Date());
			templatePassport.setCreateBy(userSession.getUserName());
			templatePassport.setModifyDateTime(new Date());
			templatePassport.setModifyBy(userSession.getUserName());
			templatePassport.setDescription(obj.getDescription());
			templatePassport.setStatus(obj.isStatus());
			if (templatePassportDao.saveNewPassport(templatePassport) == 1) {
				byte[] encoded = null;
				TemplatePassportImage passportImage = new TemplatePassportImage();
				if (!obj.getImgPre().equals("")) {
					encoded = Base64.decodeBase64(new String(obj.getImgPre()
							.substring(obj.getImgPre().indexOf(",") + 1))
							.getBytes("UTF-8"));
					passportImage.setDocData(encoded);
					passportImage.setDocType("IMGPRE");
					passportImage
							.setTemplatePassportId(Long
									.parseLong(templatePassportDao
											.getLastIdPassport()));
					templatePassportImgDao
							.saveNewImgTemplatePassport(passportImage);
				}
				if (!obj.getImgPreUV().equals("")) {
					encoded = Base64.decodeBase64(new String(obj.getImgPreUV()
							.substring(obj.getImgPreUV().indexOf(",") + 1))
							.getBytes("UTF-8"));
					passportImage.setDocData(encoded);
					passportImage.setDocType("IMGPREUV");
					passportImage
							.setTemplatePassportId(Long
									.parseLong(templatePassportDao
											.getLastIdPassport()));
					templatePassportImgDao
							.saveNewImgTemplatePassport(passportImage);
				}
				if (!obj.getImgFace().equals("")) {
					encoded = Base64.decodeBase64(new String(obj.getImgFace()
							.substring(obj.getImgFace().indexOf(",") + 1))
							.getBytes("UTF-8"));
					passportImage.setDocData(encoded);
					passportImage.setDocType("IMGFACE");
					passportImage
							.setTemplatePassportId(Long
									.parseLong(templatePassportDao
											.getLastIdPassport()));
					templatePassportImgDao
							.saveNewImgTemplatePassport(passportImage);
				}

				return 1;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}

	@RequestMapping(value = "/editTemplatePassportForm/{templatePassportId}")
	public ModelAndView editTemplatePassportForm(ModelMap model,
			@PathVariable String templatePassportId) throws DaoException {
		ModelAndView mav = new ModelAndView("editTemplatePassport");
		List<TemplatePassport> list = templatePassportDao
				.findListByTemplatePassportId(templatePassportId);
		model.addAttribute("queue", list.get(0));
		List<TemplatePassportImage> passportImages = new ArrayList<TemplatePassportImage>();
		String decodedDocstring = "";
		passportImages = templatePassportImgDao
				.findListTemplateIMGByTemplatePassportId(templatePassportId,
						"IMGPRE");
		if (passportImages.size() == 1) {
			decodedDocstring = Base64.encodeBase64String(passportImages.get(0)
					.getDocData());
		}
		model.addAttribute("imgpre", decodedDocstring);
		decodedDocstring = "";
		passportImages = templatePassportImgDao
				.findListTemplateIMGByTemplatePassportId(templatePassportId,
						"IMGPREUV");
		if (passportImages.size() == 1) {
			decodedDocstring = Base64.encodeBase64String(passportImages.get(0)
					.getDocData());
		}
		model.addAttribute("imgpreuv", decodedDocstring);
		decodedDocstring = "";
		passportImages = templatePassportImgDao
				.findListTemplateIMGByTemplatePassportId(templatePassportId,
						"IMGFACE");
		if (passportImages.size() == 1) {
			decodedDocstring = Base64.encodeBase64String(passportImages.get(0)
					.getDocData());
		}
		model.addAttribute("imgface", decodedDocstring);
		return mav;
	}

	@ResponseBody
	@RequestMapping(value = "/editTemplatePassport/{templatePassportId}")
	public Integer editTemplatePassport(HttpServletRequest httpRequest,
			@PathVariable String templatePassportId,
			@RequestBody InvestigationAssignmentData obj) {

		try {

			HttpSession session = httpRequest.getSession();
			UserSession userSession = (UserSession) session
					.getAttribute("userSession");
			List<TemplatePassport> listpassport = templatePassportDao
					.findListByTemplatePassportId(templatePassportId);

			listpassport.get(0).setModifyDateTime(new Date());
			listpassport.get(0).setModifyBy(userSession.getUserName());
			listpassport.get(0).setStatus(obj.isStatus());
			listpassport.get(0).setDescription(obj.getDescription());
			templatePassportDao.updatePassport(listpassport.get(0));

			byte[] decoded = null;
			if (!(obj.getImgPre() == null)) {
				decoded = Base64.decodeBase64(new String(obj.getImgPre()
						.substring(obj.getImgPre().indexOf(",") + 1))
						.getBytes("UTF-8"));
				templatePassportImgDao.updateTemplatePassportImg(decoded,
						templatePassportId, "IMGPRE");
			}
			if (!(obj.getImgPreUV() == null)) {
				decoded = Base64.decodeBase64(new String(obj.getImgPreUV()
						.substring(obj.getImgPreUV().indexOf(",") + 1))
						.getBytes("UTF-8"));
				templatePassportImgDao.updateTemplatePassportImg(decoded,
						templatePassportId, "IMGPREUV");
			}
			if (!(obj.getImgFace() == null)) {
				decoded = Base64.decodeBase64(new String(obj.getImgFace()
						.substring(obj.getImgFace().indexOf(",") + 1))
						.getBytes("UTF-8"));
				templatePassportImgDao.updateTemplatePassportImg(decoded,
						templatePassportId, "IMGFACE");
			}

			return 1;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}

	@RequestMapping(value = "/changeStatus/{templatePassportId}")
	public @ResponseBody Integer changeStatus(
			@PathVariable String templatePassportId,
			@RequestBody InvestigationAssignmentData obj) {
		TemplatePassport passport = templatePassportDao
				.findListByTemplatePassportId(templatePassportId).get(0);
		passport.setStatus(obj.isStatus());

		return templatePassportDao.updatePassport(passport);
	}

	// end Dung~98

	@ResponseBody
	@RequestMapping(value = "/truyenNhanDuLieu/{site}", method = RequestMethod.GET)
	public TransmissionStatistics truyenNhanDuLieu(@PathVariable String site) {
		TransmissionStatistics transStatistics = new TransmissionStatistics();
		try {
			String date = fm.format(new Date());
			Date now = fm.parse(date);
			if (StringUtils.isNotBlank(site)) {
				if (site.equals("FA08")) {
					return this.receivedDataFromA08();
				}
				List<RptStatisticsTransmitData> rptDatas = null;
				boolean check = false;
				if (site.equals("A")) {
					rptDatas = rptStatisticsTransmitDataSerivce
							.findSingerByCondition("uploadHandoverB", site, now);
					if (rptDatas != null && rptDatas.size() > 0) {
						for (RptStatisticsTransmitData rptData : rptDatas) {
							transStatistics
									.setCountReceiveHanB(transStatistics
											.getCountReceiveHanB()
											+ rptData.getTotal());
						}
					}
					transStatistics.setCountSendTran(queueJobService
							.countByConditions("HS", site,
									SyncQueueJob.STATUS_DONE, now));
					transStatistics.setCountSendHanA(queueJobService
							.countByConditions("DA", site,
									SyncQueueJob.STATUS_DONE, now));
					check = true;
				} else if (site.equals("PA")) {
					rptDatas = rptStatisticsTransmitDataSerivce
							.findSingerByCondition("uploadHandoverA", site, now);
					if (rptDatas != null && rptDatas.size() > 0) {
						for (RptStatisticsTransmitData rptData : rptDatas) {
							transStatistics
									.setCountReceiveHanA(transStatistics
											.getCountReceiveHanA()
											+ rptData.getTotal());
						}
					}
					transStatistics.setCountSendHanB(queueJobService
							.countByConditions("DB", site,
									SyncQueueJob.STATUS_DONE, now));
					transStatistics.setCountSendHanC(queueJobService
							.countByConditions("DC", site,
									SyncQueueJob.STATUS_DONE, now));
					check = true;
				} else if (site.equals("IN")) {
					rptDatas = rptStatisticsTransmitDataSerivce
							.findSingerByCondition("packLDS", site, now);
					if (rptDatas != null && rptDatas.size() > 0) {
						for (RptStatisticsTransmitData rptData : rptDatas) {
							transStatistics.setCountPack(transStatistics
									.getCountPack() + rptData.getTotal());
						}
					}
					rptDatas = rptStatisticsTransmitDataSerivce
							.findSingerByCondition("updateHandoverC", site, now);
					if (rptDatas != null && rptDatas.size() > 0) {
						for (RptStatisticsTransmitData rptData : rptDatas) {
							transStatistics
									.setCountReceiveHanC(transStatistics
											.getCountReceiveHanC()
											+ rptData.getTotal());
						}
					}
					transStatistics.setCountSendHanB(queueJobService
							.countByConditions("IN", site,
									SyncQueueJob.STATUS_DONE, now));
				} else if (site.equals("A08")) {
					transStatistics.setCountSendTran(queueJobService
							.countByConditions("HS_TO_A08", site,
									SyncQueueJob.STATUS_DONE, now));
					transStatistics.setCountSendHanB(queueJobService
							.countByConditions("DB_TO_A08", site,
									SyncQueueJob.STATUS_DONE, now));
					transStatistics.setCountSendPassport(queueJobService
							.countByConditions("PP_TO_A08", site,
									SyncQueueJob.STATUS_DONE, now));
					transStatistics.setCountSendHanC(queueJobService
							.countByConditions("DC", site,
									SyncQueueJob.STATUS_DONE, now));
					transStatistics.setCountSendLost(queueJobService
							.countByConditions("DOC_LOST", site,
									SyncQueueJob.STATUS_DONE, now));
					transStatistics.setCountSendArchive(queueJobService
							.countByConditions("ARCHIVE", site,
									SyncQueueJob.STATUS_DONE, now));
				}
				if (check) {
					rptDatas = rptStatisticsTransmitDataSerivce
							.findSingerByCondition("uploadTransaction", site,
									now);
					if (rptDatas != null && rptDatas.size() > 0) {
						for (RptStatisticsTransmitData rptData : rptDatas) {
							transStatistics
									.setCountReceiveTran(transStatistics
											.getCountReceiveTran()
											+ rptData.getTotal());
						}
					}
				}
				transStatistics.setCountWaitingToSend(queueJobService
						.countByConditions(null, site,
								SyncQueueJob.STATUS_PENDING, null));
				transStatistics.setCountSending(queueJobService
						.countByConditions(null, site,
								SyncQueueJob.STATUS_DOING, null));
				transStatistics.setCountError(queueJobService
						.countByConditions(null, site,
								SyncQueueJob.STATUS_NONE, null));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return transStatistics;
	}

	@RequestMapping(value = "/reportRegProcess")
	public ModelAndView reportRegProcess(
			@ModelAttribute(value = "formData") FormReportRegProcess inv,
			WebRequest request, HttpServletRequest httpRequest, ModelMap model) {
		ModelAndView mav = new ModelAndView("central.reportRegProcess.show");
		int pageSize = Constants.DEFAULT_PAGE_MAX_SIZE;
		int pageNo = !StringUtils.isEmpty(request.getParameter("pageNo")) ? Integer
				.parseInt(request.getParameter("pageNo")) : 1;
		List<ReportRegProcessData> list = new ArrayList<ReportRegProcessData>();
		String fromDate = "";
		String toDate = "";

		try {
			List<SiteRepository> listSite = siteService.findAllSite();
			if (StringUtils.isNotBlank(inv.getFromDate())
					|| StringUtils.isNotBlank(inv.getToDate())
					|| StringUtils.isNotBlank(inv.getRegSiteCode())) {
				if (StringUtils.isNotBlank(inv.getFromDate())) {
					String[] date = inv.getFromDate().split("-");
					fromDate = date[0] + date[1] + date[2];
				}
				if (StringUtils.isNotBlank(inv.getToDate())) {
					String[] date = inv.getToDate().split("-");
					toDate = date[0] + date[1] + date[2];
				}
				if (StringUtils.isNotBlank(inv.getRegSiteCode())) {
					model.addAttribute("regSiteCode", inv.getRegSiteCode());
				}
			} else {
				model.addAttribute("siteList", listSite);
				return mav;
			}
			int firstResults = (pageNo - 1) < 0 ? 0 : (pageNo - 1) * pageSize;
			PaginatedResult<NicTransaction> pr = nicTransactionService
					.getPageByRegDateOrRegSite(fromDate, toDate,
							inv.getRegSiteCode(), pageNo, pageSize);
			if (pr != null) {
				if (pr.getRows() != null && pr.getRows().size() > 0) {
					int stt = firstResults + 1;
					for (NicTransaction tran : pr.getRows()) {
						ReportRegProcessData data = new ReportRegProcessData();
						NicRegistrationData reg = tran.getNicRegistrationData();
						data.setTransactionId(tran.getTransactionId());
						data.setName(HelperClass.createFullName(
								reg.getSurnameFull(), reg.getMiddlenameFull(),
								reg.getFirstnameFull()));
						data.setGender(StringUtils.isNotBlank(reg.getGender()) ? (reg
								.getGender().equals("M") ? "Nam" : (reg
								.getGender().equals("F") ? "Nữ" : "Không rõ"))
								: null);
						data.setDateOfBirth(reg.getDefDateOfBirth() != null ? DateUtil
								.parseDate(reg.getDateOfBirth(),
										DateUtil.FORMAT_DDdashMMdashYYYY) : "");
						data.setNin(tran.getNin());
						data.setTransactionStatus(HelperClass
								.convertTransactionStatus(tran
										.getTransactionStatus()));
						if (StringUtils.isNotBlank(tran.getRegSiteCode())) {
							SiteRepository siteR = siteService
									.getSiteRepoById(tran.getRegSiteCode());
							if (siteR != null) {
								data.setRegSiteName(siteR.getSiteName());
							} else {
								data.setRegSiteName(tran.getRegSiteCode());
							}
						}
						data.setRegDate(tran.getDateOfApplication() != null ? DateUtil
								.parseDate(tran.getDateOfApplication(),
										DateUtil.FORMAT_DDdashMMdashYYYY) : "");
						data.setStt(stt);
						list.add(data);
						stt++;
					}
					List<CountTranStatus> listCount = nicTransactionService
							.countTranByStatusAndCoditions(fromDate, toDate,
									inv.getRegSiteCode());
					for (CountTranStatus countTranStatus : listCount) {
						countTranStatus.setStatus(HelperClass
								.convertTransactionStatus(countTranStatus
										.getTransactionStatus()));
					}
					model.addAttribute("listCount", listCount);
				}
			} else {
				pr = new PaginatedResult<NicTransaction>(0, pageNo,
						new ArrayList<NicTransaction>());
			}

			model.addAttribute("pageSize", pageSize);
			model.addAttribute("pageNo", pageNo);
			model.addAttribute("totalPage",
					pagingUtil.totalPage(pr.getTotal(), pageSize));
			model.addAttribute("startIndex",
					pr.getTotal() != 0 ? firstResults + 1 : 0);
			model.addAttribute("totalRecord", pr.getTotal());
			model.addAttribute("endIndex", firstResults + pr.getRowSize());
			model.addAttribute("listTransaction", list);
			model.addAttribute("siteList", listSite);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return mav;
	}

	@RequestMapping(value = "/printReportRegProcess")
	public ModelAndView printReportRegProcess(@RequestBody String value,
			WebRequest request, HttpServletRequest httpRequest, ModelMap model) {
		ModelAndView mav = new ModelAndView("reportProcess");
		List<ReportRegProcessData> list = new ArrayList<ReportRegProcessData>();
		String fromDate = "";
		String toDate = "";
		JSONObject json = new JSONObject(value);
		FormReportRegProcess inv = new FormReportRegProcess();
		inv.setFromDate(json.getString("fromDate"));
		inv.setToDate(json.getString("toDate"));
		inv.setRegSiteCode(json.getString("regSiteCode"));
		try {
			if (StringUtils.isNotBlank(inv.getFromDate())
					|| StringUtils.isNotBlank(inv.getToDate())
					|| StringUtils.isNotBlank(inv.getRegSiteCode())) {
				if (StringUtils.isNotBlank(inv.getFromDate())) {
					String[] date = inv.getFromDate().split("-");
					fromDate = date[0] + date[1] + date[2];
				}
				if (StringUtils.isNotBlank(inv.getToDate())) {
					String[] date = inv.getToDate().split("-");
					toDate = date[0] + date[1] + date[2];
				}
			}
			List<NicTransaction> listTran = nicTransactionService
					.getByRegDateOrRegSite(fromDate, toDate,
							inv.getRegSiteCode());
			if (listTran != null && listTran.size() > 0) {
				int stt = 1;
				for (NicTransaction tran : listTran) {
					ReportRegProcessData data = new ReportRegProcessData();
					NicRegistrationData reg = tran.getNicRegistrationData();
					data.setTransactionId(tran.getTransactionId());
					data.setName(HelperClass.createFullName(
							reg.getSurnameFull(), reg.getMiddlenameFull(),
							reg.getFirstnameFull()));
					data.setGender(StringUtils.isNotBlank(reg.getGender()) ? (reg
							.getGender().equals("M") ? "Nam" : (reg.getGender()
							.equals("F") ? "Nữ" : "Không rõ")) : null);
					data.setDateOfBirth(reg.getDefDateOfBirth() != null ? DateUtil
							.parseDate(reg.getDateOfBirth(),
									DateUtil.FORMAT_DDdashMMdashYYYY) : "");
					data.setNin(tran.getNin());
					data.setTransactionStatus(HelperClass
							.convertTransactionStatus(tran
									.getTransactionStatus()));
					if (StringUtils.isNotBlank(tran.getRegSiteCode())) {
						SiteRepository siteR = siteService.getSiteRepoById(tran
								.getRegSiteCode());
						if (siteR != null) {
							data.setRegSiteName(siteR.getSiteName());
						} else {
							data.setRegSiteName(tran.getRegSiteCode());
						}
					}
					data.setRegDate(tran.getDateOfApplication() != null ? DateUtil
							.parseDate(tran.getDateOfApplication(),
									DateUtil.FORMAT_DDdashMMdashYYYY) : "");
					data.setStt(stt);
					list.add(data);
					stt++;
				}
				List<CountTranStatus> listCount = nicTransactionService
						.countTranByStatusAndCoditions(fromDate, toDate,
								inv.getRegSiteCode());
				for (CountTranStatus countTranStatus : listCount) {
					countTranStatus.setStatus(HelperClass
							.convertTransactionStatus(countTranStatus
									.getTransactionStatus()));
				}
				model.addAttribute("listCount", listCount);
			}
			model.addAttribute("totalRecord", list.size());
			model.addAttribute("listTransaction", list);
			if (StringUtils.isNotBlank(inv.getFromDate())) {
				String[] date = inv.getFromDate().split("-");
				model.addAttribute("fromDate", date[2] + "-" + date[1] + "-"
						+ date[0]);
			}
			if (StringUtils.isNotBlank(inv.getToDate())) {
				String[] date = inv.getToDate().split("-");
				model.addAttribute("toDate", date[2] + "-" + date[1] + "-"
						+ date[0]);
			}
			if (StringUtils.isNotBlank(inv.getRegSiteCode())) {
				SiteRepository siteR = siteService.getSiteRepoById(inv
						.getRegSiteCode());
				if (siteR != null) {
					model.addAttribute("regSiteCode", siteR.getSiteName());
				} else {
					model.addAttribute("regSiteCode", inv.getRegSiteCode());
				}
			}
			if (httpRequest!=null) {
				HttpSession session = httpRequest.getSession();
				UserSession userSession = (UserSession) session.getAttribute("userSession");
				model.addAttribute("printer", userSession.getUserName());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return mav;
	}

	@RequestMapping(value = "/reportPackView")
	public ModelAndView reportPackView(
			@ModelAttribute(value = "formData") FormReportRegProcess inv,
			WebRequest request, HttpServletRequest httpRequest, ModelMap model) {
		ModelAndView mav = new ModelAndView("reportPackView.show");
		int pageSize = Constants.DEFAULT_PAGE_MAX_SIZE;
		int pageNo = !StringUtils.isEmpty(request.getParameter("pageNo")) ? Integer
				.parseInt(request.getParameter("pageNo")) : 1;
		try {
			List<SiteRepository> listSite = siteService.findAllSite();
			List<NicPersoLocations> printSiteList = persoLocationService
					.findAll();
			if (StringUtils.isNotBlank(inv.getFromDate())
					|| StringUtils.isNotBlank(inv.getToDate())
					|| StringUtils.isNotBlank(inv.getRegSiteCode())
					|| StringUtils.isNotBlank(inv.getPrintSiteCode())) {
				if (StringUtils.isNotBlank(inv.getRegSiteCode())) {
					model.addAttribute("regSiteCode", inv.getRegSiteCode());
				}
				if (StringUtils.isNotBlank(inv.getPrintSiteCode())) {
					model.addAttribute("printSiteCode", inv.getPrintSiteCode());
				}
			} else {
				model.addAttribute("printSiteList", printSiteList);
				model.addAttribute("siteList", listSite);
				return mav;
			}
			int firstResults = (pageNo - 1) < 0 ? 0 : (pageNo - 1) * pageSize;
			PaginatedResult<ReportRegProcessData> pr = nicTransactionService
					.getPageByCodition(inv.getFromDate(), inv.getToDate(),
							inv.getRegSiteCode(), inv.getPrintSiteCode(),
							pageNo, pageSize);
			if (pr.getRows() != null && pr.getRows().size() > 0) {
				int stt = firstResults + 1;
				for (ReportRegProcessData data : pr.getRows()) {
					data.setStt(stt);
					data.setGender(StringUtils.isNotBlank(data.getGender()) ? (data
							.getGender().equals("M") ? "Nam" : (data
							.getGender().equals("F") ? "Nữ" : "Không rõ"))
							: null);
					if (StringUtils.isNotBlank(data.getDocumentStatus())) {
						data.setDocumentStatus(HelperClass.convertDocumentStatus(data
								.getDocumentStatus()));
					}
					if (StringUtils.isNotBlank(data.getRegSiteName())) {
						SiteRepository siteR = siteService.getSiteRepoById(data
								.getRegSiteName());
						if (siteR != null) {
							data.setRegSiteName(siteR.getSiteName());
						}
					}
					if (StringUtils.isNotBlank(data.getPrintSiteName())) {
						NicPersoLocations location = persoLocationService
								.findPersoByCode(data.getPrintSiteName(), null);
						if (location != null) {
							data.setPrintSiteName(location.getName());
						}
					}
					stt++;
				}
			}
			model.addAttribute("pageSize", pageSize);
			model.addAttribute("pageNo", pageNo);
			model.addAttribute("totalPage",
					pagingUtil.totalPage(pr.getTotal(), pageSize));
			model.addAttribute("startIndex",
					pr.getTotal() != 0 ? firstResults + 1 : 0);
			model.addAttribute("totalRecord", pr.getTotal());
			model.addAttribute("endIndex", firstResults + pr.getRowSize());
			model.addAttribute("listTransaction", pr.getRows());
			model.addAttribute("siteList", listSite);
			model.addAttribute("printSiteList", printSiteList);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return mav;
	}

	@RequestMapping(value = "/reportPackForm")
	public ModelAndView reportPackForm(@RequestBody String value,
			WebRequest request, HttpServletRequest httpRequest, ModelMap model) {
		ModelAndView mav = new ModelAndView("reportPackForm");
		List<ReportRegProcessData> list = new ArrayList<ReportRegProcessData>();
		JSONObject json = new JSONObject(value);
		FormReportRegProcess inv = new FormReportRegProcess();
		inv.setFromDate(json.getString("fromDate"));
		inv.setToDate(json.getString("toDate"));
		inv.setRegSiteCode(json.getString("regSiteCode"));
		inv.setPrintSiteCode(json.getString("printSiteCode"));
		try {
			List<ReportRegProcessData> listTran = nicTransactionService
					.getAllByCodition(inv.getFromDate(), inv.getToDate(),
							inv.getRegSiteCode(), inv.getPrintSiteCode());
			if (listTran != null && listTran.size() > 0) {
				int stt = 1;
				for (ReportRegProcessData data : listTran) {
					data.setStt(stt);
					data.setGender(StringUtils.isNotBlank(data.getGender()) ? (data
							.getGender().equals("M") ? "Nam" : (data
							.getGender().equals("F") ? "Nữ" : "Không rõ"))
							: null);
					if (StringUtils.isNotBlank(data.getDocumentStatus())) {
						data.setDocumentStatus(HelperClass.convertDocumentStatus(data
								.getDocumentStatus()));
					}
					if (StringUtils.isNotBlank(data.getRegSiteName())) {
						SiteRepository siteR = siteService.getSiteRepoById(data
								.getRegSiteName());
						if (siteR != null) {
							data.setRegSiteName(siteR.getSiteName());
						}
					}
					if (StringUtils.isNotBlank(data.getPrintSiteName())) {
						NicPersoLocations location = persoLocationService
								.findPersoByCode(data.getPrintSiteName(), null);
						if (location != null) {
							data.setPrintSiteName(location.getName());
						}
					}
					list.add(data);
					stt++;
				}
			}
			model.addAttribute("totalRecord", list.size());
			model.addAttribute("listTransaction", list);
			if (StringUtils.isNotBlank(inv.getFromDate())) {
				String[] date = inv.getFromDate().split("-");
				model.addAttribute("fromDate", date[2] + "-" + date[1] + "-"
						+ date[0]);
			}
			if (StringUtils.isNotBlank(inv.getToDate())) {
				String[] date = inv.getToDate().split("-");
				model.addAttribute("toDate", date[2] + "-" + date[1] + "-"
						+ date[0]);
			} else {
				model.addAttribute("toDate", "hôm nay");
			}
			if (StringUtils.isNotBlank(inv.getRegSiteCode())) {
				SiteRepository siteR = siteService.getSiteRepoById(inv
						.getRegSiteCode());
				if (siteR != null) {
					model.addAttribute("regSiteCode", siteR.getSiteName());
				} else {
					model.addAttribute("regSiteCode", inv.getRegSiteCode());
				}
			}
			if (StringUtils.isNotBlank(inv.getPrintSiteCode())) {
				NicPersoLocations location = persoLocationService
						.findPersoByCode(inv.getPrintSiteCode(), null);
				if (location != null) {
					model.addAttribute("printSiteCode", location.getName());
				}else{
					model.addAttribute("printSiteCode", inv.getPrintSiteCode());
				}
			}
			if (httpRequest!=null) {
				HttpSession session = httpRequest.getSession();
				UserSession userSession = (UserSession) session.getAttribute("userSession");
				model.addAttribute("printer", userSession.getUserName());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return mav;
	}

	@RequestMapping(value = "/reportPersoView")
	public ModelAndView reportPersoView(
			@ModelAttribute(value = "formData") FormReportRegProcess inv,
			WebRequest request, HttpServletRequest httpRequest, ModelMap model) {
		ModelAndView mav = new ModelAndView("reportPersoView.show");
		int pageSize = Constants.DEFAULT_PAGE_MAX_SIZE;
		int pageNo = !StringUtils.isEmpty(request.getParameter("pageNo")) ? Integer
				.parseInt(request.getParameter("pageNo")) : 1;
		String fromDate = "";
		String toDate = "";
		try {
			List<NicPersoLocations> printSiteList = persoLocationService
					.findAll();
			if (StringUtils.isNotBlank(inv.getFromDate())
					|| StringUtils.isNotBlank(inv.getToDate())
					|| StringUtils.isNotBlank(inv.getPrintSiteCode())) {
				if (StringUtils.isNotBlank(inv.getFromDate())) {
					String[] date = inv.getFromDate().split("-");
					fromDate = date[0] + date[1] + date[2];
				}
				if (StringUtils.isNotBlank(inv.getToDate())) {
					String[] date = inv.getToDate().split("-");
					toDate = date[0] + date[1] + date[2];
				}
				if (StringUtils.isNotBlank(inv.getPrintSiteCode())) {
					model.addAttribute("printSiteCode", inv.getPrintSiteCode());
				}
			} else {
				model.addAttribute("printSiteList", printSiteList);
				return mav;
			}
			List<CountTranStatus> listCount = new ArrayList<CountTranStatus>();
			List<RptStatisticsTransmitData> rptDatas = rptStatisticsTransmitDataSerivce
					.findAllByCondition("packLDS", inv.getPrintSiteCode(),
							fromDate, toDate);
			if (rptDatas != null && rptDatas.size() > 0) {
				CountTranStatus count = new CountTranStatus();
				count.setStatus("Đóng gói dữ liệu");
				count.setCount(0);
				for (RptStatisticsTransmitData rptData : rptDatas) {
					count.setCount(count.getCount() + rptData.getTotal());
				}
				listCount.add(count);
			}
			rptDatas = rptStatisticsTransmitDataSerivce
					.findAllByCondition("updateHandoverC",
							inv.getPrintSiteCode(), fromDate, toDate);
			if (rptDatas != null && rptDatas.size() > 0) {
				CountTranStatus count = new CountTranStatus();
				count.setStatus("Nhận danh sách C");
				count.setCount(0);
				for (RptStatisticsTransmitData rptData : rptDatas) {
					count.setCount(count.getCount() + rptData.getTotal());
				}
				listCount.add(count);
			}
			int firstResults = (pageNo - 1) < 0 ? 0 : (pageNo - 1) * pageSize;
			PaginatedResult<SyncQueueDto> pr = queueJobService
					.getPageByCodition(fromDate, toDate,
							inv.getPrintSiteCode(), "IN", pageNo, pageSize);
			if (pr == null) {
				pr = new PaginatedResult<SyncQueueDto>(0, 0,
						new ArrayList<SyncQueueDto>());
			} else {
				if (pr.getRows() != null && pr.getRowSize() > 0) {
					CountTranStatus count = new CountTranStatus();
					count.setStatus("Gửi danh sách B");
					count.setCount(pr.getTotal());
					listCount.add(count);
					for (SyncQueueDto queue : pr.getRows()) {
						for (NicPersoLocations location : printSiteList) {
							if (location.getCode().equals(queue.getSite())) {
								queue.setSite(location.getName());
								break;
							}
						}
					}
				}
			}
			if (listCount != null && listCount.size() > 0) {
				model.addAttribute("listCount", listCount);
			}
			model.addAttribute("pageSize", pageSize);
			model.addAttribute("pageNo", pageNo);
			model.addAttribute("totalPage",
					pagingUtil.totalPage(pr.getTotal(), pageSize));
			model.addAttribute("startIndex",
					pr.getTotal() != 0 ? firstResults + 1 : 0);
			model.addAttribute("totalRecord", pr.getTotal());
			model.addAttribute("endIndex", firstResults + pr.getRowSize());
			model.addAttribute("listTransaction", pr.getRows());
			model.addAttribute("printSiteList", printSiteList);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return mav;
	}

	@RequestMapping(value = "/reportPersoForm")
	public ModelAndView reportPersoForm(@RequestBody String value,
			WebRequest request, HttpServletRequest httpRequest, ModelMap model) {
		ModelAndView mav = new ModelAndView("reportPersoForm");
		JSONObject json = new JSONObject(value);
		FormReportRegProcess inv = new FormReportRegProcess();
		inv.setFromDate(json.getString("fromDate"));
		inv.setToDate(json.getString("toDate"));
		inv.setPrintSiteCode(json.getString("printSiteCode"));
		String fromDate = "";
		String toDate = "";
		try {
			if (StringUtils.isNotBlank(inv.getFromDate())
					|| StringUtils.isNotBlank(inv.getToDate())
					|| StringUtils.isNotBlank(inv.getPrintSiteCode())) {
				if (StringUtils.isNotBlank(inv.getFromDate())) {
					String[] date = inv.getFromDate().split("-");
					fromDate = date[0] + date[1] + date[2];
				}
				if (StringUtils.isNotBlank(inv.getToDate())) {
					String[] date = inv.getToDate().split("-");
					toDate = date[0] + date[1] + date[2];
				}
			}
			List<CountTranStatus> listCount = new ArrayList<CountTranStatus>();
			List<RptStatisticsTransmitData> rptDatas = rptStatisticsTransmitDataSerivce
					.findAllByCondition("packLDS", inv.getPrintSiteCode(),
							fromDate, toDate);
			if (rptDatas != null && rptDatas.size() > 0) {
				CountTranStatus count = new CountTranStatus();
				count.setStatus("Đóng gói dữ liệu");
				count.setCount(0);
				for (RptStatisticsTransmitData rptData : rptDatas) {
					count.setCount(count.getCount() + rptData.getTotal());
				}
				listCount.add(count);
			}
			rptDatas = rptStatisticsTransmitDataSerivce
					.findAllByCondition("updateHandoverC",
							inv.getPrintSiteCode(), fromDate, toDate);
			if (rptDatas != null && rptDatas.size() > 0) {
				CountTranStatus count = new CountTranStatus();
				count.setStatus("Nhận danh sách C");
				count.setCount(0);
				for (RptStatisticsTransmitData rptData : rptDatas) {
					count.setCount(count.getCount() + rptData.getTotal());
				}
				listCount.add(count);
			}
			List<SyncQueueDto> list = queueJobService.getAllByCodition(
					fromDate, toDate, inv.getPrintSiteCode(), "IN");
			if (list != null && list.size() > 0) {
				CountTranStatus count = new CountTranStatus();
				count.setStatus("Gửi danh sách B");
				count.setCount(list.size());
				listCount.add(count);
				for (SyncQueueDto queue : list) {
					NicPersoLocations location = persoLocationService
							.findPersoByCode(queue.getSite(), null);
					if (location != null) {
						queue.setSite(location.getName());
					}
				}
			}
			if (listCount != null && listCount.size() > 0) {
				model.addAttribute("listCount", listCount);
			}
			model.addAttribute("totalRecord", list.size());
			model.addAttribute("listTransaction", list);
			if (StringUtils.isNotBlank(inv.getFromDate())) {
				String[] date = inv.getFromDate().split("-");
				model.addAttribute("fromDate", date[2] + "-" + date[1] + "-"
						+ date[0]);
			}
			if (StringUtils.isNotBlank(inv.getToDate())) {
				String[] date = inv.getToDate().split("-");
				model.addAttribute("toDate", date[2] + "-" + date[1] + "-"
						+ date[0]);
			} else {
				model.addAttribute("toDate", "hôm nay");
			}
			if (StringUtils.isNotBlank(inv.getPrintSiteCode())) {
				NicPersoLocations location = persoLocationService
						.findPersoByCode(inv.getPrintSiteCode(), null);
				if (location != null) {
					model.addAttribute("printSiteCode", location.getName());
				}else{
					model.addAttribute("printSiteCode", inv.getPrintSiteCode());
				}
			}
			if (httpRequest!=null) {
				HttpSession session = httpRequest.getSession();
				UserSession userSession = (UserSession) session.getAttribute("userSession");
				model.addAttribute("printer", userSession.getUserName());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return mav;
	}

	@RequestMapping(value = "/saveActionLog")
	public void saveActionLog(@RequestBody String value,
			WebRequest request, HttpServletRequest httpRequest, ModelMap model) {
		JSONObject json = new JSONObject(value);
		FormReportRegProcess inv = new FormReportRegProcess();
		Throwable throwable = null;
		long startTimeMs = new Date().getTime();
		try {
			inv.setFromDate(json.getString("fromDate"));
			inv.setToDate(json.getString("toDate"));
			inv.setPrintSiteCode(json.getString("printSiteCode"));
			inv.setRegSiteCode(json.getString("regSiteCode"));
			Object[] args = {inv};
			long timeMs = System.currentTimeMillis() - startTimeMs;
			auditAccessLogService.saveAuditAccessLogForWeb(httpRequest, json.getString("functionName"), args,throwable, timeMs);
		} catch(Exception exp){
			exp.printStackTrace();
		}
	}
	
	// Sắp xếp tiếng việt có dấu
	public void createCode() {
		codeVN = new HashMap<>();
		codeVN.put(' ', "000");
		codeVN.put('!', "001");
		codeVN.put('\"', "002");
		codeVN.put('#', "003");
		codeVN.put('$', "004");
		codeVN.put('%', "005");
		codeVN.put('&', "006");
		codeVN.put('\'', "007");
		codeVN.put('(', "008");
		codeVN.put(')', "009");
		codeVN.put('*', "010");
		codeVN.put('+', "011");
		codeVN.put(',', "012");
		codeVN.put('-', "013");
		codeVN.put('.', "014");
		codeVN.put('/', "015");
		codeVN.put('0', "016");
		codeVN.put('1', "017");
		codeVN.put('2', "018");
		codeVN.put('3', "019");
		codeVN.put('4', "020");
		codeVN.put('5', "021");
		codeVN.put('6', "022");
		codeVN.put('7', "023");
		codeVN.put('8', "024");
		codeVN.put('9', "025");
		codeVN.put(':', "026");
		codeVN.put(';', "027");
		codeVN.put('<', "028");
		codeVN.put('=', "029");
		codeVN.put('>', "030");
		codeVN.put('?', "031");
		codeVN.put('@', "032");
		codeVN.put('[', "033");
		codeVN.put('\\', "034");
		codeVN.put(']', "035");
		codeVN.put('^', "036");
		codeVN.put('_', "037");
		codeVN.put('`', "038");

		codeVN.put('a', "039");
		codeVN.put('á', "040");
		codeVN.put('à', "041");
		codeVN.put('ả', "042");
		codeVN.put('ã', "043");
		codeVN.put('ạ', "044");
		codeVN.put('ă', "045");
		codeVN.put('ắ', "046");
		codeVN.put('ằ', "047");
		codeVN.put('ẳ', "048");
		codeVN.put('ẵ', "049");
		codeVN.put('ặ', "050");
		codeVN.put('â', "051");
		codeVN.put('ấ', "052");
		codeVN.put('ầ', "053");
		codeVN.put('ẩ', "054");
		codeVN.put('ẫ', "055");
		codeVN.put('ậ', "056");
		codeVN.put('b', "057");
		codeVN.put('c', "058");
		codeVN.put('d', "059");
		codeVN.put('đ', "060");
		codeVN.put('e', "061");
		codeVN.put('é', "062");
		codeVN.put('è', "063");
		codeVN.put('ẻ', "064");
		codeVN.put('ẽ', "065");
		codeVN.put('ẹ', "066");
		codeVN.put('ê', "067");
		codeVN.put('ế', "068");
		codeVN.put('ề', "069");
		codeVN.put('ể', "070");
		codeVN.put('ễ', "071");
		codeVN.put('ệ', "072");
		codeVN.put('f', "073");
		codeVN.put('g', "074");
		codeVN.put('h', "075");
		codeVN.put('i', "076");
		codeVN.put('í', "077");
		codeVN.put('ì', "078");
		codeVN.put('ỉ', "079");
		codeVN.put('ĩ', "080");
		codeVN.put('ị', "081");
		codeVN.put('j', "082");
		codeVN.put('k', "083");
		codeVN.put('l', "084");
		codeVN.put('m', "085");
		codeVN.put('n', "086");
		codeVN.put('o', "087");
		codeVN.put('ó', "088");
		codeVN.put('ò', "089");
		codeVN.put('ỏ', "090");
		codeVN.put('õ', "091");
		codeVN.put('ọ', "092");
		codeVN.put('ơ', "093");
		codeVN.put('ớ', "094");
		codeVN.put('ờ', "095");
		codeVN.put('ở', "096");
		codeVN.put('ỡ', "097");
		codeVN.put('ợ', "098");
		codeVN.put('ô', "099");
		codeVN.put('ố', "100");
		codeVN.put('ồ', "101");
		codeVN.put('ổ', "102");
		codeVN.put('ỗ', "103");
		codeVN.put('ộ', "104");
		codeVN.put('p', "105");
		codeVN.put('q', "106");
		codeVN.put('r', "107");
		codeVN.put('s', "108");
		codeVN.put('t', "109");
		codeVN.put('u', "110");
		codeVN.put('ú', "111");
		codeVN.put('ù', "112");
		codeVN.put('ủ', "113");
		codeVN.put('ũ', "114");
		codeVN.put('ụ', "115");
		codeVN.put('ư', "116");
		codeVN.put('ứ', "117");
		codeVN.put('ừ', "118");
		codeVN.put('ử', "119");
		codeVN.put('ữ', "120");
		codeVN.put('ự', "121");
		codeVN.put('v', "122");
		codeVN.put('x', "123");
		codeVN.put('y', "124");
		codeVN.put('z', "125");

		codeVN.put('{', "126");
		codeVN.put('|', "127");
		codeVN.put('}', "128");
		codeVN.put('~', "129");
	}

	public String generator(String input) {
		StringBuilder result = new StringBuilder();
		char[] b = input.toLowerCase().toCharArray();
		for (int i = 0; i < b.length; i++) {
			result.append(codeVN.get(b[i]));
		}
		return result.toString();
	}
}
