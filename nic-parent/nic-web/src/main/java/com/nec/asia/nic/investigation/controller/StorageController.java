package com.nec.asia.nic.investigation.controller;


import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import javax.persistence.criteria.CriteriaBuilder.In;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.ModelAndView;

import com.nec.asia.nic.comp.job.dto.InfoFamillyDto;
import com.nec.asia.nic.comp.listHandover.domain.NicListHandover;
import com.nec.asia.nic.comp.listHandover.service.ListHandoverService;
import com.nec.asia.nic.comp.trans.domain.BaseModelList;
import com.nec.asia.nic.comp.trans.domain.EppPerson;
import com.nec.asia.nic.comp.trans.domain.EppPersonAttchmnt;
import com.nec.asia.nic.comp.trans.domain.EppPersonFamily;
import com.nec.asia.nic.comp.trans.domain.NicDocumentData;
import com.nec.asia.nic.comp.trans.domain.NicRegistrationData;
import com.nec.asia.nic.comp.trans.domain.NicTransaction;
import com.nec.asia.nic.comp.trans.domain.NicTransactionAttachment;
import com.nec.asia.nic.comp.trans.domain.NicTransactionLost;
import com.nec.asia.nic.comp.trans.domain.NicTransactionPackage;
import com.nec.asia.nic.comp.trans.domain.NicTransactionPaymentDetail;
import com.nec.asia.nic.comp.trans.dto.AttachStorageDto;
import com.nec.asia.nic.comp.trans.dto.InfoStorageDto;
import com.nec.asia.nic.comp.trans.dto.ListStorageDto;
import com.nec.asia.nic.comp.trans.dto.NicUploadJobDto;
import com.nec.asia.nic.comp.trans.service.DocumentDataService;
import com.nec.asia.nic.comp.trans.service.EppPersonService;
import com.nec.asia.nic.comp.trans.service.NicRegistrationDataService;
import com.nec.asia.nic.comp.trans.service.NicTransactionAttachmentService;
import com.nec.asia.nic.comp.trans.service.NicTransactionLostService;
import com.nec.asia.nic.comp.trans.service.NicTransactionPackageService;
import com.nec.asia.nic.comp.trans.service.NicTransactionPaymentDetaiService;
import com.nec.asia.nic.comp.trans.service.NicTransactionService;
import com.nec.asia.nic.comp.trans.service.NicUploadJobService;
import com.nec.asia.nic.framework.PaginatedResult;
import com.nec.asia.nic.framework.admin.code.domain.CodeValues;
import com.nec.asia.nic.framework.admin.code.service.CodesService;
import com.nec.asia.nic.framework.admin.code.service.ParametersService;
import com.nec.asia.nic.framework.admin.rbac.domain.Users;
import com.nec.asia.nic.framework.admin.rbac.service.UserService;
import com.nec.asia.nic.framework.admin.site.domain.SiteRepository;
import com.nec.asia.nic.framework.admin.site.service.SiteService;
import com.nec.asia.nic.framework.dao.DaoException;
import com.nec.asia.nic.framework.report.service.AuditAccessLogService;
import com.nec.asia.nic.investigation.dto.BusinessListDto;
import com.nec.asia.nic.investigation.dto.FormReportRegProcess;
import com.nec.asia.nic.investigation.dto.highchart.HighchartsDto;
import com.nec.asia.nic.util.Constants;
import com.nec.asia.nic.util.RegistrationConstants;
import com.nec.asia.nic.util.pagingUtil;
import com.nec.asia.nic.utils.HelperClass;
import com.nec.asia.nic.web.session.UserSession;

@Controller
@RequestMapping(value = "/storage")
public class StorageController {

	@Autowired
	private NicUploadJobService uploadJobService;

	@Autowired
	private NicTransactionService nicTransactionService;

	@Autowired
	private CodesService codesService;

	@Autowired
	private DocumentDataService documentDataService;

	@Autowired
	private NicRegistrationDataService regDataService;

	@Autowired
	private SiteService siteService;

	@Autowired
	private UserService userService;

	@Autowired
	private EppPersonService eppPersonService;

	@Autowired
	private NicTransactionPaymentDetaiService payDetailService;

	@Autowired
	private ListHandoverService handoverService;

	@Autowired
	private NicTransactionAttachmentService nicTransactionAttachmentService;

	@Autowired
	private NicTransactionPackageService packageService;

	@Autowired
	private ParametersService paraService;
	
	@Autowired
	private NicTransactionLostService tranLostService;
	
	@Autowired
	private AuditAccessLogService auditAccessLogService;

	private static final Logger logger = LoggerFactory
			.getLogger(InvestigationController.class);

	@ModelAttribute("placeOfBirth")
	public Map<String, String> placeOfBirth() {
		List<CodeValues> codeList = codesService.getAllCodeValues(
				"CODE_BirthPlace", null);
		Map<String, String> placeOfBirth = new LinkedHashMap<String, String>();
		placeOfBirth.put("", "Tất cả");
		for (CodeValues ricCode : codeList) {
			placeOfBirth.put(ricCode.getId().getCodeValue(),
					ricCode.getCodeValueDesc());
		}
		return placeOfBirth;
	}

	@ModelAttribute("passportStage")
	public Map<String, String> passportStage() {
		List<CodeValues> codeList = codesService.getAllCodeValues(
				"PASSPORT_STAGE", null);
		Map<String, String> passportStage = new LinkedHashMap<String, String>();
		passportStage.put("", "Tất cả");
		for (CodeValues ricCode : codeList) {
			passportStage.put(ricCode.getId().getCodeValue(),
					ricCode.getCodeValueDesc());
		}
		return passportStage;
	}

	@ModelAttribute("typeStatus")
	public Map<String, String> typeStatus() {
		List<CodeValues> codeList = codesService.getAllCodeValues(
				"TYPE_STATUS", null);
		Map<String, String> typeStatus = new LinkedHashMap<String, String>();
		typeStatus.put("", "Tất cả");
		for (CodeValues ricCode : codeList) {
			typeStatus.put(ricCode.getId().getCodeValue(),
					ricCode.getCodeValueDesc());
		}
		return typeStatus;
	}

	// Dũng thêm hàm lấy datatable Ajax
	@RequestMapping(value = "/getDataTable/")
	@ResponseBody
	public NicUploadJobDto getDataTable(
			@RequestBody InvestigationAssignmentData investigationAssignmentData,
			WebRequest request, HttpServletRequest httpRequest) {
		logger.info("NIC Investigation");
		PaginatedResult<NicUploadJobDto> pr = null;
		try {
			int pageNo = !StringUtils.isEmpty(investigationAssignmentData
					.getPageNo()) ? Integer
					.parseInt(investigationAssignmentData.getPageNo()) : 1;
			HttpSession session = httpRequest.getSession();
			UserSession userSession = (UserSession) session
					.getAttribute("userSession");
			logger.info("The Officer Id or User Id is ===============>>>>>>>>>>"
					+ userSession.getUserName());
			int pageSize = Constants.PAGE_SIZE_DEFAULT;
			String dateIssua = "";
			String dateExp = "";

			if (investigationAssignmentData.getCreateDate() != null
					&& !investigationAssignmentData.getCreateDate().trim()
							.equals("")) {
				dateIssua = HelperClass
						.convertStringToString3(investigationAssignmentData
								.getCreateDate().trim());
			}
			if (investigationAssignmentData.getEndDate() != null
					&& !investigationAssignmentData.getEndDate().trim()
							.equals("")) {
				dateExp = HelperClass
						.convertStringToString3(investigationAssignmentData
								.getEndDate().trim());
			}
			// httpRequest.setAttribute("pendingCount",
			// uploadJobService.getPendingInvestigationsCount());
			if (StringUtils
					.isBlank(investigationAssignmentData.getCreateDate())
					&& StringUtils.isBlank(investigationAssignmentData
							.getEndDate())
					&& StringUtils.isBlank(investigationAssignmentData
							.getPassportType())
					&& StringUtils.isBlank(investigationAssignmentData
							.getPassportNo())
					&& StringUtils.isBlank(investigationAssignmentData
							.getStageLoad())
					&& StringUtils.isBlank(investigationAssignmentData
							.getName())) {
				pr = new PaginatedResult<NicUploadJobDto>(0, pageNo,
						new ArrayList<NicUploadJobDto>());
			} else {
				pr = nicTransactionService.allPassportStorage(
						investigationAssignmentData.getPassportType(),
						investigationAssignmentData.getPassportNo(),
						investigationAssignmentData.getStageLoad(), dateIssua,
						dateExp, investigationAssignmentData.getName(), pageNo,
						pageSize);
			}
			if (pr != null) {
				List<NicUploadJobDto> list = pr.getRows();
				System.out.println(list.size());
				int firstResults = (pageNo - 1) < 0 ? 0 : (pageNo - 1)
						* pageSize;
				NicUploadJobDto dataHtml = new NicUploadJobDto();
				String Stringdatahtml = "";
				int stt = (pageNo - 1) * pageSize;
				for (NicUploadJobDto n : list) {
					stt ++;
					String param;
					if (n.getPriority() == 0) {
						param = "<input type='checkbox' disabled='disabled' checked=\"checked\" id='"
								+ n.getPassportNo()+ "' />";
					} else {
						param = "<input type='checkbox' disabled='disabled'  id='"
								+ n.getPassportNo() + "' />";
					}
					if(n.getStageList() == null){n.setStageList("");}
					n.setStt(stt);
					Stringdatahtml = Stringdatahtml
							+ "<tr> <td class='align-central'>"
							+ n.getStt()
							+ "</td> <td>"
							+ n.getPassportNo() 
							+ "</td><td>"
							+ n.getPassportType() 
							+ "</td><td class='align-central'>"
							+ param
							+ "</td><td class='align-central'>"
							+ n.getDateIssuce() 
							+ "</td><td class='align-central'>"
							+ n.getDateEprity() 
							+ "</td><td>"
							+ n.getRicName() 
							+ "</td><td>"
							+ n.getFullName()
							+ "</td><td>"
							+ n.getStageList()
							+ "</td><td class='align-central'>"
							+ n.getEsColectionDate()
							+ "</td><td class='align-central'><a href='#' onclick=\"chiTietHS('"
							+ n.getPassportNo() 
							+ "');\" data-toggle='modal' data-target='#idChiTiet'><i class=\"glyphicon glyphicon-eye-open\"></i>Xem</a></td></tr>";
					
				}
				dataHtml.setDataHtml(Stringdatahtml);
				dataHtml.setPageNo(pageNo);
				dataHtml.setPageSize(pageSize);
				dataHtml.setTotalPage(pagingUtil.totalPage(pr.getTotal(),
						pageSize));
				dataHtml.setTotalRecord(pr.getTotal());
				dataHtml.setEndIndex(firstResults + pr.getRowSize());
				dataHtml.setStartIndex(pr.getTotal() != 0 ? firstResults + 1
						: 0);

				return dataHtml;
			} else {
				return new NicUploadJobDto();

			}
			// }
		} catch (Exception e) {

			e.printStackTrace();
			return null;
		}

	}

	@RequestMapping(value = "/searchPassport")
	public ModelAndView getInvestionJob(
			@ModelAttribute(value = "formData") InvestigationAssignmentData investigationAssignmentData,
			WebRequest request, HttpServletRequest httpRequest, ModelMap model) {
		logger.info("NIC Investigation");
		PaginatedResult<NicUploadJobDto> pr = null;
		try {
			int pageNo = !StringUtils.isEmpty(request.getParameter("pageNo")) ? Integer
					.parseInt(request.getParameter("pageNo")) : 1;
			HttpSession session = httpRequest.getSession();
			UserSession userSession = (UserSession) session
					.getAttribute("userSession");
			logger.info("The Officer Id or User Id is ===============>>>>>>>>>>"
					+ userSession.getUserName());
			int pageSize = Constants.PAGE_SIZE_DEFAULT;
			String dateIssua = "";
			String dateExp = "";

			if (investigationAssignmentData.getCreateDate() != null
					&& !investigationAssignmentData.getCreateDate().trim()
							.equals("")) {
				dateIssua = HelperClass
						.convertStringToString3(investigationAssignmentData
								.getCreateDate().trim());
			}
			if (investigationAssignmentData.getEndDate() != null
					&& !investigationAssignmentData.getEndDate().trim()
							.equals("")) {
				dateExp = HelperClass
						.convertStringToString3(investigationAssignmentData
								.getEndDate().trim());
			}
			// httpRequest.setAttribute("pendingCount",
			// uploadJobService.getPendingInvestigationsCount());
			if (StringUtils
					.isBlank(investigationAssignmentData.getCreateDate())
					&& StringUtils.isBlank(investigationAssignmentData
							.getEndDate())
					&& StringUtils.isBlank(investigationAssignmentData
							.getPassportType())
					&& StringUtils.isBlank(investigationAssignmentData
							.getPassportNo())
					&& StringUtils.isBlank(investigationAssignmentData
							.getStageLoad())
					&& StringUtils.isBlank(investigationAssignmentData
							.getName())) {
				pr = new PaginatedResult<NicUploadJobDto>(0, pageNo,
						new ArrayList<NicUploadJobDto>());
			} else {
				pr = nicTransactionService.allPassportStorage(
						investigationAssignmentData.getPassportType(),
						investigationAssignmentData.getPassportNo(),
						investigationAssignmentData.getStageLoad(), dateIssua,
						dateExp, investigationAssignmentData.getName(), pageNo,
						pageSize);
			}
			if (pr != null) {
				List<NicUploadJobDto> list = pr.getRows();
				int i = 1;
				for (NicUploadJobDto n : list) {
					n.setStt(i);
					i++;
				}
				model.addAttribute("total", pr.getTotal());
				// phúc edit
				int firstResults = (pageNo - 1) < 0 ? 0 : (pageNo - 1)
						* pageSize;
				model.addAttribute("pageSize", pageSize);
				model.addAttribute("pageNo", pageNo);
				model.addAttribute("totalPage",
						pagingUtil.totalPage(pr.getTotal(), pageSize));
				model.addAttribute("startIndex",
						pr.getTotal() != 0 ? firstResults + 1 : 0);
				model.addAttribute("totalRecord", pr.getTotal());
				model.addAttribute("endIndex", firstResults + pr.getRowSize());
				model.addAttribute("dsXuLyA", list);
				// end
				model.addAttribute("fnSelected",
						Constants.HEADING_NIC_INVESTIGATION);
				ModelAndView modelAndView = new ModelAndView(
						"luutru.tracuuhochieu");
				modelAndView.addObject("formData", investigationAssignmentData);
				return modelAndView;
			} else {
				model.addAttribute("dsXuLyA", new ArrayList<NicUploadJobDto>());
				model.addAttribute("fnSelected",
						Constants.HEADING_NIC_INVESTIGATION);
				ModelAndView modelAndView = new ModelAndView(
						"luutru.tracuuhochieu");
				modelAndView.addObject("formData", investigationAssignmentData);
				return modelAndView;
			}
			// }
		} catch (Exception e) {

			e.printStackTrace();
			return null;
		}

	}

	public String convertImageFormatWsqToJpg1(byte[] imageBase64String) {
		String convertedBase64String = "";
		try {
			convertedBase64String = Base64.encodeBase64String(HelperClass
					.ConvertWSQToJPG(imageBase64String));
			// System.out.println("The image binary after base64 conversion===="+convertedBase64String);
		} catch (Exception ex) {
			System.out
					.println("Error occured while converting the wsq image to jpeg"
							+ ex.getMessage());
		}
		return convertedBase64String;
	}

	@RequestMapping(value = "/getInfoPassport/{ppNo}")
	public ModelAndView getInfoXNCTransaction(@PathVariable String ppNo,
			WebRequest request, HttpServletRequest httpRequest, ModelMap model)
			throws DaoException {
		ModelAndView mav = new ModelAndView("storage-detail");
		BusinessListDto dto = new BusinessListDto();
		try {
			NicDocumentData docData = documentDataService.findByDocNumber(ppNo)
					.getModel();
			if (docData != null) {
				dto.setSoHC(docData.getId().getPassportNo());
				dto.setNgayCapHC(HelperClass.convertDateToString2(docData
						.getDateOfIssue()));
				dto.setNgayHetHan(HelperClass.convertDateToString2(docData
						.getDateOfExpiry()));
				if (docData.getStatus().equals("NONE")) {
					dto.setTrangThai("Hủy/Hỏng/Mất");
				} else if (docData.getStatus().equals("ISSUANCE")) {
					dto.setTrangThai("Phát hành");
				} else if (docData.getDateOfExpiry().before(
						Calendar.getInstance().getTime())) {
					dto.setTrangThai("Hết hạn/Không hiệu lực");
				} else if (!docData.getDateOfExpiry().before(
						Calendar.getInstance().getTime())) {
					dto.setTrangThai("Có hiệu lực");
				}
				dto.setIcao1(docData.getIcaoLine1());
				dto.setIcao2(docData.getIcaoLine2());
				dto.setNguoiKy(docData.getSigner());
				dto.setChucVu(docData.getPositionSigner());
			}
			NicTransaction txn = nicTransactionService.findById(docData.getId()
					.getTransactionId());
			dto.setStylePP(1);
			dto.setStyleFP(0);
			if (txn != null) {
				dto.setNin(txn.getNin());
				dto.setLoaiHC(codesService.getCodeValueDescByIdName(
						RegistrationConstants.CODE_ID_PASSPORT,
						txn.getPassportType(), ""));
				SiteRepository sr = siteService.getSiteRepoById(txn
						.getIssSiteCode());
				if (sr != null) {
					dto.setNoiCap(sr.getSiteName());
				}
				try {
					Users user = null; // userService.findByUserId(txn.getLeaderOfficerId());
					if (user != null) {
						dto.setNguoiKy(user.getUserName());
						dto.setChucVu(user.getPosition());
					}

				} catch (Exception e) {
				}
				if (txn.getIsEpassport() != null
						&& txn.getIsEpassport().equals("Y")) {
					dto.setStylePP(0);
				}
				List<NicTransactionAttachment> listAt = nicTransactionAttachmentService
						.findNicTransactionAttachments(txn.getTransactionId(),
								"FP", null).getListModel();
				if (CollectionUtils.isNotEmpty(listAt)) {
					dto.setStyleFP(1);
				}
			}
			NicRegistrationData regData = regDataService.findById(docData
					.getId().getTransactionId());
			if (regData != null) {
				dto.setFullName(HelperClass.createFullName(
						regData.getSurnameFull(), regData.getMiddlenameFull(),
						regData.getFirstnameFull()));
				dto.setGenderDesc(regData.getGender().equals("M") ? "Nam"
						: "Nữ");
				String date = HelperClass.convertDateToString2(regData
						.getDateOfBirth());
				dto.setDob(HelperClass.loadDateOfBirth(date,
						regData.getDefDateOfBirth()));
				dto.setNgayCapCMND(HelperClass.convertDateToString2(regData
						.getDateNin()));
				dto.setTonGiao(codesService.getCodeValueDescByIdName(
						RegistrationConstants.CODE_RELIGION_CODE_ID,
						regData.getReligion(), ""));
				dto.setDanToc(codesService.getCodeValueDescByIdName(
						RegistrationConstants.CODE_NATION_CODE_ID,
						regData.getNation(), ""));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		mav.addObject("root", dto);
		return mav;
	}

	// Dũng thêm hàm lấy data bằng ajax 19/11
	@RequestMapping(value = "/getdatatableSearchPerson")
	@ResponseBody
	public NicUploadJobDto getdatatableSearchPerson(
			@RequestBody InvestigationAssignmentData investigationAssignmentData,
			WebRequest request, HttpServletRequest httpRequest, ModelMap model) {
		logger.info("NIC Investigation");
		PaginatedResult<NicUploadJobDto> pr = null;
		// PaginatedResult<NicUploadJobDto> rejectedList = null;
		try {
			int pageNo = !StringUtils.isEmpty(investigationAssignmentData.getPageNo()) ? Integer
					.parseInt(investigationAssignmentData.getPageNo()) : 1;
			int pageSize = 20;
	
			pageSize = Constants.PAGE_SIZE_DEFAULT;

			if (investigationAssignmentData.getTypeList() == null) {
				investigationAssignmentData.setTypeList("");
			}
			if (StringUtils.isNotBlank(investigationAssignmentData.getName())
					|| StringUtils.isNotBlank(investigationAssignmentData
							.getGender())
					|| StringUtils.isNotBlank(investigationAssignmentData
							.getDob())
					|| StringUtils.isNotBlank(investigationAssignmentData
							.getPob())
					|| StringUtils.isNotBlank(investigationAssignmentData
							.getNin())
					|| StringUtils.isNotBlank(investigationAssignmentData
							.getTypeList())) {
				pr = eppPersonService.findAllEppPerson3(
						HelperClass.removeAccent(investigationAssignmentData.getName()
								.toUpperCase()), investigationAssignmentData
								.getGender(), investigationAssignmentData
								.getDob(),
						investigationAssignmentData.getPob(),
						investigationAssignmentData.getNin(),
						investigationAssignmentData.getTypeList(), pageNo,
						pageSize);
				if (pr != null) {
					List<NicUploadJobDto> list = pr.getRows();
					int i = (pageNo - 1) * pageSize;
					System.out.println(list.size());
					int firstResults = (pageNo - 1) < 0 ? 0 : (pageNo - 1)
							* pageSize;
					NicUploadJobDto dataHtml = new NicUploadJobDto();
					String Stringdatahtml = "";
					for (NicUploadJobDto n : list) {
						if(n.getFullName() == null){n.setFullName("");}
						if(n.getGender() == null){n.setGender("");}
						if(n.getDob() == null){n.setDob("");}
						if(n.getPlaceOfBirth() == null){n.setPlaceOfBirth("");}
						if(n.getNin() == null){n.setNin("");}
						if(n.getDateNin() == null){n.setDateNin("");}
						if(n.getAddressNin() == null){n.setAddressNin("");}
						i++;
//						String param;
						/*if (n.getPriority() == 0) {
							param = "<input type='checkbox' disabled='disabled' checked=\"checked\" id='"
									+ n.getPassportNo() + "' />";
						} else {
							param = "<input type='checkbox' disabled='disabled'  id='"
									+ n.getPassportNo() + "' />";
						}*/
						n.setStt(i);
						Stringdatahtml = Stringdatahtml +
								 "<tr><td class=\"align-central\">"
								 + n.getStt()
								 + "</td> <td>"
								 + n.getFullName() 
								 + "</td> <td>"
								 + n.getGender() 
								 + "</td> <td class=\"align-central\">"
								 + n.getDob()
								 + "</td> <td>"
								 + n.getPlaceOfBirth()
								 + "</td> <td>"
								 + n.getNin()
								 + "</td> <td class=\"align-central\">"
								 + n.getDateNin()
								 + "</td> <td>"
								 + n.getAddressNin() 
								 + "</td> <td class=\"align-central\"><a href=\"#\" onclick=\"chiTietHS('"
								 + n.getUploadJobId()
								 + "', '"
								 + n.getTransactionId() 
								 + "');\" data-toggle=\"modal\" data-target=\"#idChiTiet\"><i class=\"glyphicon glyphicon-eye-open\"></i>Xem</a></td></tr>";
					
					}
					dataHtml.setDataHtml(Stringdatahtml);
					dataHtml.setPageNo(pageNo);
					dataHtml.setPageSize(pageSize);
					dataHtml.setTotalPage(pagingUtil.totalPage(pr.getTotal(),
							pageSize));
					dataHtml.setTotalRecord(pr.getTotal());
					dataHtml.setEndIndex(firstResults + pr.getRowSize());
					dataHtml.setStartIndex(pr.getTotal() != 0 ? firstResults + 1
							: 0);

					return dataHtml;
				} else {
					return new NicUploadJobDto();

				}
			}
			
			// }
		} catch (Exception e) {
			
			e.printStackTrace();
			return new NicUploadJobDto();
		}
		return new NicUploadJobDto();
			
	}
	
	@RequestMapping(value = "/searchPerson")
	public ModelAndView searchPerson(
			@ModelAttribute(value = "formData") InvestigationAssignmentData investigationAssignmentData,
			WebRequest request, HttpServletRequest httpRequest, ModelMap model) {
		logger.info("NIC Investigation");
		PaginatedResult<NicUploadJobDto> pr = null;
		// PaginatedResult<NicUploadJobDto> rejectedList = null;
		try {
			int pageNo = !StringUtils.isEmpty(request.getParameter("pageNo")) ? Integer
					.parseInt(request.getParameter("pageNo")) : 1;
			int pageSize = 20;
			// int startIndex = 0;
			HttpSession session = httpRequest.getSession();
			UserSession userSession = (UserSession) session
					.getAttribute("userSession");
			logger.info("The Officer Id or User Id is ===============>>>>>>>>>>"
					+ userSession.getUserName());
			pageSize = Constants.PAGE_SIZE_DEFAULT;
			if (investigationAssignmentData.getTypeList() == null) {
				investigationAssignmentData.setTypeList("");
			}
			if (StringUtils.isNotBlank(investigationAssignmentData.getName())
					|| StringUtils.isNotBlank(investigationAssignmentData
							.getGender())
					|| StringUtils.isNotBlank(investigationAssignmentData
							.getDob())
					|| StringUtils.isNotBlank(investigationAssignmentData
							.getPob())
					|| StringUtils.isNotBlank(investigationAssignmentData
							.getNin())
					|| StringUtils.isNotBlank(investigationAssignmentData
							.getTypeList())) {
				pr = eppPersonService.findAllEppPerson3(
						HelperClass.removeAccent(investigationAssignmentData.getName()
								.toUpperCase()), investigationAssignmentData
								.getGender(), investigationAssignmentData
								.getDob(),
						investigationAssignmentData.getPob(),
						investigationAssignmentData.getNin(),
						investigationAssignmentData.getTypeList(), pageNo,
						pageSize);
				if (pr != null) {
					List<NicUploadJobDto> list = pr.getRows();
					model.addAttribute("total", pr.getTotal());
					if (list != null) {
						int i = 1;
						for (NicUploadJobDto n : list) {
							n.setStt(i);
							i++;
						}
					} else {
						request.setAttribute("jobDetailsErrorMsg",
								"Không tìm thấy dữ liệu", 0);
					}
					Map searchResultMap = new HashMap();
					searchResultMap.put("totalRecords", pr.getTotal());
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
					model.addAttribute("fnSelected",
							Constants.HEADING_NIC_INVESTIGATION);
					ModelAndView modelAndView = new ModelAndView(
							"luutru.danhsachcongdan");
					modelAndView.addObject("formData",
							investigationAssignmentData);
					return modelAndView;
				}
			}
			model.addAttribute("dsXuLyA", new ArrayList<NicUploadJobDto>());
			model.addAttribute("fnSelected",
					Constants.HEADING_NIC_INVESTIGATION);
			ModelAndView modelAndView = new ModelAndView(
					"luutru.danhsachcongdan");
			modelAndView.addObject("formData", investigationAssignmentData);
			return modelAndView;
			// }
		} catch (Exception e) {

			e.printStackTrace();
			return null;
		}

	}

	@RequestMapping(value = "/getInfoPerson/{personId}/{tranId}")
	public ModelAndView getInfoPerson(@PathVariable String personId,
			@PathVariable String tranId, WebRequest request,
			HttpServletRequest httpRequest, ModelMap model) throws DaoException {
		ModelAndView mav = new ModelAndView("person-detail");
		NicUploadJobDto dto = new NicUploadJobDto();
		try {
			List<EppPersonAttchmnt> listAtt = null;
			BaseModelList<EppPersonFamily> baseFindPerson = new BaseModelList<>(null, true, null);
			EppPerson ep = eppPersonService.getPersonById(Long
					.parseLong(personId));
			NicRegistrationData reg = regDataService.findById(tranId);
			if (ep != null) {
				dto.setFullName(ep.getName());
				dto.setGender(ep.getGender().equals("MALE") ? "Nam" : "Nữ");
				// dto.setDob(HelperClass.convertStringToStringTk(ep.getDateOfBirth(),
				// 0));
				String dob = HelperClass.convertStringToStringTk(
						ep.getDateOfBirth(), 0);
				if (reg != null) {
					dto.setDob(HelperClass.loadDateOfBirth(dob,
							reg.getDefDateOfBirth()));
				}
				// dto.setPlaceOfBirth(ep.getPlaceOfBirthId());
				// String noiSinh =
				// codesService.getCodeValueDescByIdName("DISTRICT",
				// ep.getPlaceOfBirthCode(), "");
				// if(StringUtils.isNotEmpty(noiSinh)){
				// dto.setPlaceOfBirth(noiSinh);
				// }else{
				dto.setPlaceOfBirth(ep.getPlaceOfBirthName());
				// }
				// String nation =
				// codesService.getCodeValueDescByIdName("CODE_NATION",
				// ep.getEthnic(), "");
				// if(StringUtils.isNotEmpty(nation)){
				// dto.setNation(nation);
				// }else{
				dto.setNation(ep.getEthnic());
				// }
				// String religion =
				// codesService.getCodeValueDescByIdName("CODE_RELIGION",
				// ep.getReligion(), "");
				// if(StringUtils.isNotEmpty(religion)){
				// dto.setReligion(religion);
				// }else{
				dto.setReligion(ep.getReligion());
				// }
				dto.setNin(ep.getIdNumber());
				dto.setAddressNin(ep.getPlaceOfIdIssueName());
				// String noiCap =
				// codesService.getCodeValueDescByIdName("CODE_IDPlace",
				// ep.getPlaceIfIdIssueId(), "");
				// if(StringUtils.isNotEmpty(noiCap)){
				// dto.setAddressNin(noiCap);
				// }else{
				// String ncNin =
				// codesService.getCodeValueDescByIdName("CODE_IDPlace",
				// reg.getAddressNin(), "");
				// if(StringUtils.isNotEmpty(ncNin)){
				// dto.setAddressNin(ncNin);
				// }
				// }
				// if(StringUtils.isNotEmpty(ep.getDateOfIdIssue())){
				dto.setDateNin(HelperClass.convertStringToStringTk(
						ep.getDateOfIdIssue(), 0));
				// }
				dto.setStageList("N");
				// if(ep.getFpFlag() != null && ep.getFpFlag().equals("Y"))
				// dto.setStageList("Y");
				listAtt = eppPersonService
						.findAllEppPersonAttchmnt1(
								new String[] { "SCAN_DOCUMENT" }, ep.getPersonId());
				baseFindPerson = eppPersonService
						.findAllEppPersonFamily1(ep.getPersonId());
			}
			List<String> attPerson = new ArrayList<String>();
			
			if (CollectionUtils.isNotEmpty(listAtt)) {
				for (EppPersonAttchmnt att : listAtt) {
					attPerson.add(att.getBase64());
				}
			}
			dto.setImgList(attPerson);
			List<InfoFamillyDto> listFa = new ArrayList<InfoFamillyDto>();
			List<EppPersonFamily> epf = baseFindPerson.getListModel();
			if (CollectionUtils.isNotEmpty(epf)) {
				for (EppPersonFamily fa : epf) {
					InfoFamillyDto dto2 = new InfoFamillyDto();
					dto2.setFullName_F(fa.getName());
					if (fa.getGender() != null) {
						if (fa.getGender().equals("M")) {
							dto2.setGender_F("Nam");
						} else if (fa.getGender().equals("F")) {
							dto2.setGender_F("Nữ");
						} else {
							dto2.setGender_F(fa.getGender());
						}
					}
					// dto2.setDob_F(fa.getDateOfBirth());
					String dobFa = HelperClass.convertStringToStringTk(
							fa.getDateOfBirth(), 0);

					// dto2.setStage_F(fa.getRelationship());
					switch (fa.getRelationship()) {
					case "1":
						dto2.setStage_F("Bố");
						dto2.setDob_F(HelperClass.loadDateOfBirth(dobFa,
								reg.getFatherDefDateOfBirth()));
						break;
					case "2":
						dto2.setStage_F("Mẹ");
						dto2.setDob_F(HelperClass.loadDateOfBirth(dobFa,
								reg.getMotherDefDateOfBirth()));
						break;
					case "3":
						dto2.setStage_F("Vợ");
						dto2.setDob_F(HelperClass.loadDateOfBirth(dobFa,
								reg.getSpouseDefDateOfBirth()));
						break;
					case "4":
						dto2.setStage_F("Chồng");
						dto2.setDob_F(HelperClass.loadDateOfBirth(dobFa,
								reg.getSpouseDefDateOfBirth()));
						break;
					default:
						break;
					}
					listFa.add(dto2);
				}
			}
			dto.setInfoFamily(listFa);
		} catch (Exception e) {
			e.printStackTrace();
		}
		mav.addObject("root", dto);
		return mav;
	}
	
	
	//dũng thêm hàm lấy data bằng ajax 20/11
	@RequestMapping(value = "/getdatatableSearchTransaction")
	@ResponseBody
	public NicUploadJobDto getdatatableSearchTransaction(
			@RequestBody InvestigationAssignmentData investigationAssignmentData,
			WebRequest request, HttpServletRequest httpRequest, ModelMap model) {
		logger.info("NIC Investigation");
		PaginatedResult<NicUploadJobDto> pr = null;
		try {
			int pageNo = !StringUtils.isEmpty(investigationAssignmentData.getPageNo()) ? Integer
					.parseInt(investigationAssignmentData.getPageNo()) : 1;
			HttpSession session = httpRequest.getSession();
			UserSession userSession = (UserSession) session
					.getAttribute("userSession");
			logger.info("The Officer Id or User Id is ===============>>>>>>>>>>"
					+ userSession.getUserName());

			int pageSize = Constants.PAGE_SIZE_DEFAULT;
			httpRequest.setAttribute("pendingCount",
					uploadJobService.getPendingInvestigationsCount());

			pr = nicTransactionService.allTransactionStorage(
					investigationAssignmentData.getSearchTransactionId(),
					investigationAssignmentData.getPackageCode(),
					investigationAssignmentData.getPassportNo(),
					investigationAssignmentData.getNin(),
					investigationAssignmentData.getName(),
					investigationAssignmentData.getStageLoad(),
					investigationAssignmentData.getPassPortStage(),
					investigationAssignmentData.getTypeInvestigation(), pageNo,
					pageSize);
			if (pr != null) {
				List<NicUploadJobDto> list = pr.getRows();
				int i = 1;
				int firstResults = (pageNo - 1) < 0 ? 0 : (pageNo - 1)
						* pageSize;
				NicUploadJobDto dataHtml = new NicUploadJobDto();
				String Stringdatahtml = "";
				for (NicUploadJobDto n : list) {
					if(n.getPassportNo() == null){n.setPassportNo("");}
					int stt = 1;
					String param;
					if (n.getPriority() == 0) {
						param = "<input type='checkbox' disabled='disabled' checked=\"checked\" id=\""
								+ n.getPassportNo()+"_CB" + "\" />";
					} else {
						param = "<input type='checkbox' disabled='disabled'  id=\""
								+ n.getPassportNo() +"_CB" + "\" />";
					}
					n.setStt(i);
					
					if(n.getTransactionId() == null){n.setTransactionId("");}
					if(n.getRecieptNo() == null){n.setRecieptNo("");}
					if(n.getFullName() == null){n.setFullName("");}
					if(n.getPhone() == null){n.setPhone("");}
					if(n.getPassportType() == null){n.setPassportType("");}
					if(n.getRicName() == null){n.setRicName("");}
					if(n.getTransactionStatus() == null){n.setTransactionStatus("");}
					if(n.getTransactionStatus() == null){n.setTransactionStatus("");}
					
					Stringdatahtml = Stringdatahtml
							+  "<tr> <td class=\"align-central\">"
							+ n.getStt()
							+ " </td> <td>"
							+ n.getTransactionId()
							+ "</td><td>"
							+ n.getRecieptNo()
							+ "</td><td>"
							+ n.getPassportNo()
							+ "</td> <td>"
							+ n.getFullName()
							+ "</td> <td>"
							+ n.getPhone()
							+ "</td><td class=\"align-central\">"
							+ param
							+ " </td> <td>"
							+ n.getPassportType()
							+ "</td> <td>"
							+ n.getRicName()
							+ "</td> <td>"
							+ n.getTransactionStatus()
							+ "</td><td align=\"center\">"
							+ n.getEsColectionDate()
							+ "</td><td>"
							+ n.getStatus()
							+ "</td><td class=\"align-central\"><a href=\"#\" onclick=\"chiTietHS('"
							+ n.getTransactionId()
							+ "', '"
							+ n.getTransactionStatus()
							+ "');\" data-toggle=\"modal\" data-target=\"#idChiTiet\"><i class=\"glyphicon glyphicon-eye-open\"></i>Xem</a></td> </tr>";
					i++;
				}
				dataHtml.setDataHtml(Stringdatahtml);
				dataHtml.setPageNo(pageNo);
				dataHtml.setPageSize(pageSize);
				dataHtml.setTotalPage(pagingUtil.totalPage(pr.getTotal(),
						pageSize));
				dataHtml.setTotalRecord(pr.getTotal());
				dataHtml.setEndIndex(firstResults + pr.getRowSize());
				dataHtml.setStartIndex(pr.getTotal() != 0 ? firstResults + 1
						: 0);

				return dataHtml;
			} else {
				return new NicUploadJobDto();

			}
			// }
		} catch (Exception e) {

			e.printStackTrace();
			return null;
		}

	}
	

	@RequestMapping(value = "/searchTranaction")
	public ModelAndView searchTranaction(
			@ModelAttribute(value = "formData") InvestigationAssignmentData investigationAssignmentData,
			WebRequest request, HttpServletRequest httpRequest, ModelMap model) {
		logger.info("NIC Investigation");
		PaginatedResult<NicUploadJobDto> pr = null;
		// PaginatedResult<NicUploadJobDto> rejectedList = null;
		try {
			int pageNo = !StringUtils.isEmpty(request.getParameter("pageNo")) ? Integer
					.parseInt(request.getParameter("pageNo")) : 1;
			// int pageSize = 20;
			// int startIndex = 0;
			HttpSession session = httpRequest.getSession();
			UserSession userSession = (UserSession) session
					.getAttribute("userSession");
			logger.info("The Officer Id or User Id is ===============>>>>>>>>>>"
					+ userSession.getUserName());

			// Modified for pagination
			// ParametersId id = new ParametersId();
			// id.setParaName(Parameters.MAX_RECORDS_PER_PAGE);
			// id.setParaScope(Parameters.SCOPE_SYSTEM);
			// String tableId = "row";
			// String sortedElement = "codeId";
			// String order = InvestigationProcessController.DECENDING_ORDER;
			//
			// try {
			// String sort = request
			// .getParameter(new ParamEncoder(tableId)
			// .encodeParameterName(TableTagParameters.PARAMETER_SORT));
			// if (StringUtils.isNotBlank(sort))
			// sortedElement = sort;
			// } catch (Exception ex) {
			// logMessage(ex);
			// }
			//
			// try {
			// String requestOrder = request
			// .getParameter(new ParamEncoder(tableId)
			// .encodeParameterName(TableTagParameters.PARAMETER_ORDER));
			// if (StringUtils.isNotBlank(requestOrder))
			// order = requestOrder;
			// } catch (Exception ex) {
			// logMessage(ex);
			// }
			//
			// Order hibernateOrder = Order.desc(sortedElement);
			// if
			// (order.equalsIgnoreCase(InvestigationProcessController.DECENDING_ORDER))
			// {
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
			// String pageNumber = request.getParameter((new
			// ParamEncoder(tableId)
			// .encodeParameterName(TableTagParameters.PARAMETER_PAGE)));
			//
			// if (StringUtils.isNotBlank(pageNumber)
			// && StringUtils.isNumeric(pageNumber)) {
			// pageNo = Integer.parseInt(pageNumber);
			// }
			// if(StringUtils.isNotEmpty(investigationAssignmentData.getCreateDate())){
			// investigationAssignmentData.setCreateDate(HelperClass.convertMonthWordToMonthNumber(investigationAssignmentData.getCreateDate()));
			// }
			// if(StringUtils.isNotEmpty(investigationAssignmentData.getEndDate())){
			// investigationAssignmentData.setEndDate(HelperClass.convertMonthWordToMonthNumber(investigationAssignmentData.getEndDate()));
			// }
			httpRequest.setAttribute("pendingCount",
					uploadJobService.getPendingInvestigationsCount());

			pr = nicTransactionService.allTransactionStorage(
					investigationAssignmentData.getSearchTransactionId(),
					investigationAssignmentData.getPackageCode(),
					investigationAssignmentData.getPassportNo(),
					investigationAssignmentData.getNin(),
					investigationAssignmentData.getName(),
					investigationAssignmentData.getStageLoad(),
					investigationAssignmentData.getPassPortStage(),
					investigationAssignmentData.getTypeInvestigation(), pageNo,
					pageSize);
			if (pr != null) {
				List<NicUploadJobDto> list = pr.getRows();
				model.addAttribute("total", pr.getTotal());
				if (list != null) {
					// for (NicUploadJobDto record : list) {
					// if(StringUtils.isEmpty(record.getInvestigationOfficerId())){
					// record.setFlagOfficerId("0");
					// }else {
					// if(record.getInvestigationOfficerId().equals(userSession.getUserId())){
					// record.setFlagOfficerId("0");
					// }else{
					// record.setFlagOfficerId("1");
					// }
					// }
					// if(investigationAssignmentData.getTypeList().equals("3")){
					// record.setFlagOfficerId("1");
					// }
					// String transactionId = record.getTransactionId();
					// if (transactionId != null) {
					// NicTransaction nicTransaction = nicTransactionService
					// .findById(transactionId);
					// if (nicTransaction != null) {
					// record.setDateOfApplication(nicTransaction
					// .getDateOfApplication());
					// record.setEstDateOfCollection(nicTransaction
					// .getEstDateOfCollection());
					// record.setPassportType(nicTransaction
					// .getPassportType());
					// record.setRegSiteCode(nicTransaction
					// .getRegSiteCode());
					// record.setPackageId(nicTransaction.getPackageID());
					// record.setNin(nicTransaction.getNin());
					// // record.setPriority(nicTransaction.getPriority());
					// {
					// try {
					// //CodeValues codeValue =
					// codesService.getCodeValueByIdName(Codes.PRIORITY,nicTransaction.getPriority().toString());
					// String priority =
					// codesService.getCodeValueDescByIdName(RegistrationConstants.CODE_PRIORITY_CODE_ID,
					// nicTransaction.getPriority().toString(), "");
					// if (priority != null) {
					// record.setPriorityString(priority);
					// } else {
					// record.setPriorityString("");
					// }
					// } catch (Exception e) {
					// record.setPriorityString(nicTransaction
					// .getPriority() == null ? null
					// : nicTransaction.getPriority()
					// .toString());
					// }
					// }
					// NicRegistrationData reg =
					// nicTransaction.getNicRegistrationData();
					// if(reg != null){
					// record.setFullName(com.nec.asia.nic.comp.trans.utils.HelperClass.createFullName(reg.getSurnameFull(),
					// reg.getMiddlenameFull(), reg.getFirstnameFull()));
					// String dateDob =
					// HelperClass.convertDateToString2(reg.getDateOfBirth());
					// record.setDob(com.nec.asia.nic.utils.HelperClass.loadDateOfBirth(dateDob,
					// reg.getStyleDob()));
					// //record.setDob(HelperClass.convertDateToString2(reg.getDateOfBirth()));
					// if(!StringUtils.isEmpty(reg.getGender())){
					// record.setGender(reg.getGender().equals("M") ? "Nam" :
					// "Nữ");
					// }
					// record.setPlaceOfBirth(reg.getPlaceOfBirth());
					// }
					// }
					//
					// }
					// }
				} else {
					request.setAttribute("jobDetailsErrorMsg",
							"Không tìm thấy dữ liệu", 0);
				}
				Map searchResultMap = new HashMap();
				searchResultMap.put("totalRecords", pr.getTotal());
				// searchResultMap.put("pageSize", pageSize);

				// model.addAttribute("jobList", list);
				// phúc edit
				int firstResults = (pageNo - 1) < 0 ? 0 : (pageNo - 1)
						* pageSize;
				model.addAttribute("pageSize", pageSize);
				model.addAttribute("pageNo", pageNo);
				model.addAttribute("totalPage",
						pagingUtil.totalPage(pr.getTotal(), pageSize));
				model.addAttribute("startIndex",
						pr.getTotal() != 0 ? firstResults + 1 : 0);
				model.addAttribute("totalRecord", pr.getTotal());
				model.addAttribute("endIndex", firstResults + pr.getRowSize());
				model.addAttribute("dsXuLyA", list);
				// end
				model.addAttribute("fnSelected",
						Constants.HEADING_NIC_INVESTIGATION);
				ModelAndView modelAndView = new ModelAndView(
						"luutru.danhsachhoso");
				modelAndView.addObject("formData", investigationAssignmentData);
				return modelAndView;
			} else {
				model.addAttribute("dsXuLyA", new ArrayList<NicUploadJobDto>());
				model.addAttribute("fnSelected",
						Constants.HEADING_NIC_INVESTIGATION);
				ModelAndView modelAndView = new ModelAndView(
						"luutru.danhsachhoso");
				modelAndView.addObject("formData", investigationAssignmentData);
				return modelAndView;
			}
			// }
		} catch (Exception e) {

			e.printStackTrace();
			return null;
		}

	}

	@RequestMapping(value = "/getInfoTransaction")
	public ModelAndView getInfoTransaction(@RequestParam String transId,
			@RequestParam String status, WebRequest request,
			HttpServletRequest httpRequest, ModelMap model) throws DaoException {
		ModelAndView mav = new ModelAndView("tran-detail");
		NicUploadJobDto dto = new NicUploadJobDto();
		try {
			NicTransaction txn = nicTransactionService.findById(transId);
			dto.setStylePP(1);
			if (txn != null) {
				dto.setTransactionId(txn.getTransactionId());
				dto.setRecieptNo(txn.getRecieptNo());
				dto.setNin(txn.getNin());
				SiteRepository sr = siteService.getSiteRepoById(txn
						.getIssSiteCode());
				if (sr != null) {
					dto.setRicName(sr.getSiteName());
				}
				dto.setPriorityDesc(txn.getPriority() == 0 ? "Thông thường"
						: "Ưu tiên");
				dto.setEsColectionDate(txn.getEstDateOfCollection() != null ? HelperClass
						.convertDateToString2(txn.getEstDateOfCollection())
						: "");
				if (txn.getIsEpassport() != null
						&& txn.getIsEpassport().equals("Y")) {
					dto.setStylePP(0);
				}
				dto.setPassportBefore(txn.getPrevPassportNo());
				dto.setDateIssBefore(txn.getPrevDateOfIssue() != null ? HelperClass
						.convertDateToString2(txn.getPrevDateOfIssue()) : "");
			}
			NicRegistrationData reg = regDataService.findById(transId);
			if (reg != null) {
				dto.setFullName(HelperClass.createFullName(
						reg.getSurnameFull(), reg.getMiddlenameFull(),
						reg.getFirstnameFull()));
				String date = HelperClass.convertDateToString2(reg
						.getDateOfBirth());
				dto.setDob(HelperClass.loadDateOfBirth(date,
						reg.getDefDateOfBirth()));
				// dto.setPlaceOfBirth(reg.getPlaceOfBirth());
				String noiSinh = codesService.getCodeValueDescByIdName(
						"DISTRICT", reg.getPlaceOfBirth(), "");
				if (StringUtils.isNotEmpty(noiSinh)) {
					dto.setPlaceOfBirth(noiSinh);
				} else {
					dto.setPlaceOfBirth(reg.getPlaceOfBirth());
				}
				dto.setDateNin(reg.getDateNin() != null ? HelperClass
						.convertDateToString2(reg.getDateNin()) : "");
				dto.setAddressNin(codesService.getCodeValueDescByIdName(
						"CODE_IDPlace", reg.getAddressNin(), ""));
				dto.setGender(reg.getGender().equals("M") ? "Nam" : "Nữ");
				String address1 = reg.getAddressLine1();
				String ht = codesService.getCodeValueDescByIdName(
						RegistrationConstants.CODE_TOWN, reg.getAddressLine4(),
						"");
				String tp = codesService.getCodeValueDescByIdName(
						RegistrationConstants.CODE_DISTRICT,
						reg.getAddressLine5(), "");
				if (StringUtils.isNotEmpty(ht)) {
					address1 += ", " + ht;
				}
				if (StringUtils.isNotEmpty(tp)) {
					address1 += ", " + tp;
				}
				dto.setAddress1(address1);
				dto.setPhone(reg.getContactNo());
				String address2 = "";// reg.getAddressTempStreet();
				String ht2 = codesService.getCodeValueDescByIdName(
						RegistrationConstants.CODE_TOWN,
						reg.getAddressTempDistrict(), "");
				// String tp2 =
				// codesService.getCodeValueDescByIdName(RegistrationConstants.CODE_DISTRICT,
				// reg.getAddressTempCity(), "");
				if (StringUtils.isNotEmpty(ht2)) {
					address2 += ", " + ht2;
				}
				/*
				 * if(StringUtils.isNotEmpty(tp2)){ address2 += ", " + tp2; }
				 */
				dto.setAddress2(address2);
				String job = codesService.getCodeValueDescByIdName("CODE_JOB",
						reg.getJob(), "");
				if (StringUtils.isNotEmpty(job)) {
					dto.setJob(job);
				} else {
					dto.setJob(reg.getJob());
				}
				// dto.setAddressCompany(reg.getAddressCompany());
				NicDocumentData docData = documentDataService
						.getDocumentDataById(transId).getModel();
				if (docData != null) {
					dto.setPassportNo(docData.getId().getPassportNo());
				}
				dto.setTransactionStatus(status);
			}

			BaseModelList<NicTransactionPaymentDetail> baseFindDPay = payDetailService
					.findListDetailPaymentList(txn.getNicTransactionPayment()
							.getPaymentId(), true);
			List<NicTransactionPaymentDetail> listPay = baseFindDPay
					.getListModel();
			List<InfoStorageDto> lists = new ArrayList<InfoStorageDto>();
			if (CollectionUtils.isNotEmpty(listPay)) {
				for (NicTransactionPaymentDetail pt : listPay) {
					InfoStorageDto dto2 = new InfoStorageDto();
					dto2.setTenPhi(codesService.getCodeValueDescByIdName("R",
							pt.getSubTypePayment(), ""));
					dto2.setSoTien(this.formatTienTeVN(pt.getPaymentAmount()));
					lists.add(dto2);
				}
			}
			dto.setInfoStorage(lists);
			NicListHandover han1 = handoverService.findHandoverByTransactionId(
					transId, 7, null, null);
			List<ListStorageDto> listL = new ArrayList<ListStorageDto>();
			if (han1 != null) {
				ListStorageDto dto2 = new ListStorageDto();
				dto2.setMaDS(han1.getId().getPackageId());
				dto2.setLoạiDS("A");
				if (txn != null) {
					SiteRepository site = siteService.getSiteRepoById(txn
							.getIssSiteCode());
					if (site != null)
						dto2.setCoQuan(site.getSiteName());
				}
				listL.add(dto2);
			}
			List<NicListHandover> lHan2 = handoverService
					.findAllHandoverByTransactionId(transId,
							NicListHandover.TYPE_LIST_B, 0, null)
					.getListModel();
			if (lHan2 != null) {
				for (NicListHandover han2 : lHan2) {
					ListStorageDto dto2 = new ListStorageDto();
					dto2.setMaDS(han2.getId().getPackageId());
					dto2.setLoạiDS("B");
					// dto2.setCoQuan(han2.getResultPlace());
					/*
					 * if(txn.getLeaderOfficerId() != null){ Users users =
					 * userService.findByUserId(txn.getLeaderOfficerId());
					 * if(users != null){ dto2.setNguoiKy(users.getUserName());
					 * dto2.setChucVu(users.getPosition());
					 * dto.setLeaderId(users.getUserName());
					 * dto.setPosition(users.getPosition()); }else{
					 * dto2.setNguoiKy(txn.getLeaderOfficerId()); } }
					 */
					NicTransactionPackage np = packageService
							.getListPackageByPackageIdAndTranID(han2.getId()
									.getPackageId(), transId);
					if (np != null) {
						dto.setTypeApprove(np.getStage());
						dto.setLeaderNote(np.getNoteApprove());
					}
					listL.add(dto2);
					// dto.setPlacePassport(han2.getResultPlace());
				}
			}
			/*
			 * if(han2 != null){ ListStorageDto dto2 = new ListStorageDto();
			 * dto2.setMaDS(han2.getPackageId()); dto2.setLoạiDS("B");
			 * dto2.setCoQuan(han2.getResultPlace());
			 * if(txn.getLeaderOfficerId() != null){ Users users =
			 * userService.findByUserId(txn.getLeaderOfficerId()); if(users !=
			 * null){ dto2.setNguoiKy(users.getUserName());
			 * dto2.setChucVu(users.getPosition());
			 * dto.setLeaderId(users.getUserName());
			 * dto.setPosition(users.getPosition()); } } NicTransactionPackage
			 * np =
			 * packageService.getListPackageByPackageIdAndTranID(han2.getPackageId
			 * (), transId); if(np != null){ dto.setTypeApprove(np.getStage());
			 * dto.setLeaderNote(np.getNoteApprove()); } listL.add(dto2);
			 * dto.setPlacePassport(han2.getResultPlace()); }
			 */
			NicListHandover han3 = handoverService.findHandoverByTransactionId(
					transId, 8, 1, null);
			if (han3 != null) {
				ListStorageDto dto2 = new ListStorageDto();
				dto2.setMaDS(han3.getId().getPackageId());
				dto2.setLoạiDS("B");
				// dto2.setCoQuan(han3.getResultPlace());
				/*
				 * if(txn.getLeaderOfficerId() != null){ Users users =
				 * userService.findByUserId(txn.getLeaderOfficerId()); if(users
				 * != null){ dto2.setNguoiKy(users.getUserName());
				 * dto2.setChucVu(users.getPosition());
				 * dto.setLeaderId(users.getUserName());
				 * dto.setPosition(users.getPosition()); }else{
				 * dto2.setNguoiKy(txn.getLeaderOfficerId()); } }
				 */
				NicTransactionPackage np = packageService
						.getListPackageByPackageIdAndTranID(han3.getId()
								.getPackageId(), transId);
				if (np != null) {
					dto.setTypeApprove(np.getStage());
					dto.setLeaderNote(np.getNoteApprove());
				}
				listL.add(dto2);
				// dto.setPlacePassport(han3.getResultPlace());
			}
			NicDocumentData docData = documentDataService.getDocumentDataById(
					transId).getModel();
			if (docData != null) {
				ListStorageDto dto3 = new ListStorageDto();
				dto3.setMaDS(docData.getPackageId());
				dto3.setLoạiDS("C");
				// dto3.setCoQuan(han3 != null ? han3.getResultPlace() : "");
				listL.add(dto3);
			}
			dto.setInfoList(listL);
			dto.setImgPhoto("");
			List<NicTransactionAttachment> listAttch = nicTransactionAttachmentService
					.findNicTransactionAttachments(transId, null, null)
					.getListModel();
			List<AttachStorageDto> listA = new ArrayList<AttachStorageDto>();
			if (CollectionUtils.isNotEmpty(listAttch)) {
				for (NicTransactionAttachment at : listAttch) {
					AttachStorageDto dto2 = new AttachStorageDto();
					dto2.setTransactionId(transId);
					dto2.setTenFile(at.getDocType());
					if (at.getDocType().equals("FP")) {
						dto2.setLoaiFile("Ảnh vân tay dạng PNG");
					}
					if (at.getDocType().equals("MNU_CBEFF")) {
						dto2.setLoaiFile("Ảnh vân tay dạng MNU_CBEFF");
					}
					if (at.getDocType().equals("PH_CAP")) {
						dto2.setLoaiFile("Ảnh chủ hồ sơ");
						String img = Base64.encodeBase64String(at.getDocData());
						dto.setImgPhoto(img);
					}
					if (at.getDocType().equals("PH_CHIP")) {
						dto2.setLoaiFile("Ảnh trong chip");
					}
					if (at.getDocType().equals("SCAN_DOCUMENT")) {
						dto2.setLoaiFile("Ảnh tài liệu/ tờ khai");
					}
					dto2.setStt(StringUtils.isNotEmpty(at.getSerialNo()) ? Integer
							.parseInt(at.getSerialNo()) : 0);
					listA.add(dto2);
				}
			}
			Collections.sort(listA, new Comparator<AttachStorageDto>() {
				@Override
				public int compare(AttachStorageDto o1, AttachStorageDto o2) {
					int value1 = o1.getTenFile().compareTo(o2.getTenFile());
					if (value1 == 0) {
						int value2 = o1.getStt() - o2.getStt();
						return value2;
					}
					return value1;
				}

			});
			dto.setInfoAttach(listA);
		} catch (Exception e) {
			e.printStackTrace();
		}
		mav.addObject("root", dto);
		return mav;
	}

	// public int compare(AttachStorageDto o1, AttachStorageDto o2) {
	// int value1 = o1.getTenFile().compareTo(o2.getTenFile());
	// if (value1 == 0) {
	// int value2 = o1.getStt() - o2.getStt();
	// return value2;
	// }
	// return value1;
	// }

	public String formatTienTeVN(Object object) {
		String amoutTo = "";
		try {
			Locale localeVN = new Locale("vi", "VN");
			NumberFormat currencyVN = NumberFormat
					.getCurrencyInstance(localeVN);
			amoutTo = currencyVN.format(object);
			if (amoutTo.contains("đ")) {
				amoutTo = amoutTo.replace("đ", "VNĐ");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return amoutTo;
	}

	@ResponseBody
	@RequestMapping(value = "/getFacialById")
	public String getFacialByTransactionId(@RequestParam String tranactionId,
			@RequestParam String type, WebRequest request,
			HttpServletRequest httpRequest, ModelMap model) throws Exception {
		String photoStr = "";
		List<NicTransactionAttachment> photoList = nicTransactionAttachmentService
				.findNicTransactionAttachments(tranactionId, type, null)
				.getListModel();
		if (CollectionUtils.isNotEmpty(photoList) && photoList.size() > 0) {
			String images = Base64.encodeBase64String(photoList.get(0)
					.getDocData());
			photoStr = "<img src=\'data:image/jpg;base64,"
					+ images
					+ "' class=\"img-border doGetAJpgSafeVersion\" height=\"250\" width=\"200\" />";
		} else {
			photoStr = "<img class=\"img-border\" height=\"250\" width=\"200\" src=\"/eppcentral/resources/images/No_Image.jpg\">";
		}
		return photoStr;
	}
	
//	@RequestMapping(value = "/issuanceStatistic")
//	public ModelAndView issuanceStatistic(
//			@ModelAttribute(value = "formData") FormReportRegProcess inv,
//			WebRequest request, HttpServletRequest httpRequest, ModelMap model) {
//		ModelAndView mav = new ModelAndView("issuanceStatistic");
//		int pageSize = Constants.DEFAULT_PAGE_MAX_SIZE;
//		int pageNo = !StringUtils.isEmpty(request.getParameter("pageNo")) ? Integer
//				.parseInt(request.getParameter("pageNo")) : 1;
//		try {
//			List<SiteRepository> listSite = siteService.findAllSite();
//			List<EppListHandoverDetail> hanDetals = persoLocationService
//					.findAll();
//			if (StringUtils.isNotBlank(inv.getFromDate())
//					|| StringUtils.isNotBlank(inv.getToDate())
//					|| StringUtils.isNotBlank(inv.getRegSiteCode())
//					|| StringUtils.isNotBlank(inv.getPrintSiteCode())) {
//				if (StringUtils.isNotBlank(inv.getRegSiteCode())) {
//					model.addAttribute("regSiteCode", inv.getRegSiteCode());
//				}
//				if (StringUtils.isNotBlank(inv.getPrintSiteCode())) {
//					model.addAttribute("printSiteCode", inv.getPrintSiteCode());
//				}
//			} else {
//				model.addAttribute("printSiteList", printSiteList);
//				model.addAttribute("siteList", listSite);
//				return mav;
//			}
//			int firstResults = (pageNo - 1) < 0 ? 0 : (pageNo - 1) * pageSize;
//			PaginatedResult<ReportRegProcessData> pr = nicTransactionService
//					.getPageByCodition(inv.getFromDate(), inv.getToDate(),
//							inv.getRegSiteCode(), inv.getPrintSiteCode(),
//							pageNo, pageSize);
//			if (pr.getRows() != null && pr.getRows().size() > 0) {
//				int stt = firstResults + 1;
//				for (ReportRegProcessData data : pr.getRows()) {
//					data.setStt(stt);
//					data.setGender(StringUtils.isNotBlank(data.getGender()) ? (data
//							.getGender().equals("M") ? "Nam" : (data
//							.getGender().equals("F") ? "Nữ" : "Không rõ"))
//							: null);
//					if (StringUtils.isNotBlank(data.getDocumentStatus())) {
//						data.setDocumentStatus(HelperClass.convertDocumentStatus(data
//								.getDocumentStatus()));
//					}
//					if (StringUtils.isNotBlank(data.getRegSiteName())) {
//						SiteRepository siteR = siteService.getSiteRepoById(data
//								.getRegSiteName());
//						if (siteR != null) {
//							data.setRegSiteName(siteR.getSiteName());
//						}
//					}
//					if (StringUtils.isNotBlank(data.getPrintSiteName())) {
//						NicPersoLocations location = persoLocationService
//								.findPersoByCode(data.getPrintSiteName(), null);
//						if (location != null) {
//							data.setPrintSiteName(location.getName());
//						}
//					}
//					stt++;
//				}
//			}
//			model.addAttribute("pageSize", pageSize);
//			model.addAttribute("pageNo", pageNo);
//			model.addAttribute("totalPage",
//					pagingUtil.totalPage(pr.getTotal(), pageSize));
//			model.addAttribute("startIndex",
//					pr.getTotal() != 0 ? firstResults + 1 : 0);
//			model.addAttribute("totalRecord", pr.getTotal());
//			model.addAttribute("endIndex", firstResults + pr.getRowSize());
//			model.addAttribute("listTransaction", pr.getRows());
//			model.addAttribute("siteList", listSite);
//			model.addAttribute("printSiteList", printSiteList);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//
//		return mav;
//	}

//	@RequestMapping(value = "/reportPackForm")
//	public ModelAndView reportPackForm(@RequestBody String value,
//			WebRequest request, HttpServletRequest httpRequest, ModelMap model) {
//		ModelAndView mav = new ModelAndView("reportPackForm");
//		List<ReportRegProcessData> list = new ArrayList<ReportRegProcessData>();
//		JSONObject json = new JSONObject(value);
//		FormReportRegProcess inv = new FormReportRegProcess();
//		inv.setFromDate(json.getString("fromDate"));
//		inv.setToDate(json.getString("toDate"));
//		inv.setRegSiteCode(json.getString("regSiteCode"));
//		inv.setPrintSiteCode(json.getString("printSiteCode"));
//		try {
//			List<ReportRegProcessData> listTran = nicTransactionService
//					.getAllByCodition(inv.getFromDate(), inv.getToDate(),
//							inv.getRegSiteCode(), inv.getPrintSiteCode());
//			if (listTran != null && listTran.size() > 0) {
//				int stt = 1;
//				for (ReportRegProcessData data : listTran) {
//					data.setStt(stt);
//					data.setGender(StringUtils.isNotBlank(data.getGender()) ? (data
//							.getGender().equals("M") ? "Nam" : (data
//							.getGender().equals("F") ? "Nữ" : "Không rõ"))
//							: null);
//					if (StringUtils.isNotBlank(data.getDocumentStatus())) {
//						data.setDocumentStatus(HelperClass.convertDocumentStatus(data
//								.getDocumentStatus()));
//					}
//					if (StringUtils.isNotBlank(data.getRegSiteName())) {
//						SiteRepository siteR = siteService.getSiteRepoById(data
//								.getRegSiteName());
//						if (siteR != null) {
//							data.setRegSiteName(siteR.getSiteName());
//						}
//					}
//					if (StringUtils.isNotBlank(data.getPrintSiteName())) {
//						NicPersoLocations location = persoLocationService
//								.findPersoByCode(data.getPrintSiteName(), null);
//						if (location != null) {
//							data.setPrintSiteName(location.getName());
//						}
//					}
//					list.add(data);
//					stt++;
//				}
//			}
//			model.addAttribute("totalRecord", list.size());
//			model.addAttribute("listTransaction", list);
//			if (StringUtils.isNotBlank(inv.getFromDate())) {
//				String[] date = inv.getFromDate().split("-");
//				model.addAttribute("fromDate", date[2] + "-" + date[1] + "-"
//						+ date[0]);
//			}
//			if (StringUtils.isNotBlank(inv.getToDate())) {
//				String[] date = inv.getToDate().split("-");
//				model.addAttribute("toDate", date[2] + "-" + date[1] + "-"
//						+ date[0]);
//			} else {
//				model.addAttribute("toDate", "hôm nay");
//			}
//			if (StringUtils.isNotBlank(inv.getRegSiteCode())) {
//				SiteRepository siteR = siteService.getSiteRepoById(inv
//						.getRegSiteCode());
//				if (siteR != null) {
//					model.addAttribute("regSiteCode", siteR.getSiteName());
//				} else {
//					model.addAttribute("regSiteCode", inv.getRegSiteCode());
//				}
//			}
//			if (StringUtils.isNotBlank(inv.getPrintSiteCode())) {
//				NicPersoLocations location = persoLocationService
//						.findPersoByCode(inv.getPrintSiteCode(), null);
//				if (location != null) {
//					model.addAttribute("printSiteCode", location.getName());
//				}else{
//					model.addAttribute("printSiteCode", inv.getPrintSiteCode());
//				}
//			}
//			if (httpRequest!=null) {
//				HttpSession session = httpRequest.getSession();
//				UserSession userSession = (UserSession) session.getAttribute("userSession");
//				model.addAttribute("printer", userSession.getUserName());
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//
//		return mav;
//	}
	
	
	/*@RequestMapping(value = "/saveActionLog")
	public void saveActionLog(@RequestBody String value,
			WebRequest request, HttpServletRequest httpRequest, ModelMap model) {
		JSONObject json = new JSONObject(value);
		InvestigationAssignmentData inv = new InvestigationAssignmentData();
		Throwable throwable = null;
		long startTimeMs = new Date().getTime();
		try {
			inv.setCreateDate(json.getString("fromDate"));
			inv.setEndDate(json.getString("toDate"));
			inv.setReason(json.getString("reason"));
			inv.setOfficeCode(json.getString("officeCode"));
			Object[] args = {inv};
			long timeMs = System.currentTimeMillis() - startTimeMs;
			auditAccessLogService.saveAuditAccessLogForWeb(httpRequest, json.getString("functionName"), args,throwable, timeMs);
			System.out.println(args);
		} catch(Exception exp){
			exp.printStackTrace();
		}
	}*/
	
	@RequestMapping(value = "/showReportTranLost")
	public ModelAndView showReportTranLost(
			@ModelAttribute(value = "formData") InvestigationAssignmentData investigationAssignmentData,
			WebRequest request, HttpServletRequest httpRequest, ModelMap model) {
		Throwable throwable = null;
		long startTimeMs = System.currentTimeMillis();
		try {
			ModelAndView modelAndView = new ModelAndView("storage.showTranLost");
			SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
			List<InvestigationAssignmentData> datas = new ArrayList<InvestigationAssignmentData>();
			if (investigationAssignmentData.getCreateDate() != null && investigationAssignmentData.getEndDate() != null) {
				Date fromDate = format.parse(investigationAssignmentData.getCreateDate());
				Date toDate = format.parse(investigationAssignmentData.getEndDate());
				long getDiff = TimeUnit.MILLISECONDS.toDays((toDate.getTime()  - fromDate.getTime())) ;
				Calendar calfromDate = Calendar.getInstance();
				Calendar caltoDate = Calendar.getInstance();
				
				if (getDiff<=10) {
					
					calfromDate.setTime(fromDate);
					caltoDate.setTime(fromDate);
					caltoDate.add(Calendar.DAY_OF_YEAR, 1);
					for (int i = 0; i < getDiff; i++) {
						InvestigationAssignmentData param = new InvestigationAssignmentData();
						param.setQuantityList(Integer.toString(tranLostService.findQuantityTranLost(calfromDate.getTime(), caltoDate.getTime(), investigationAssignmentData.getReason(), investigationAssignmentData.getOfficeCode())));
						param.setDateList(format.format(calfromDate.getTime()).substring(0,2).concat("-"+format.format(caltoDate.getTime())));
						datas.add(param);
						calfromDate.add(Calendar.DAY_OF_YEAR, 1);
						caltoDate.add(Calendar.DAY_OF_YEAR, 1);
					}
				}
				if (getDiff>10&& getDiff<20) {
					
					int step =  (int)Math.floor(getDiff/5);
					calfromDate.setTime(fromDate);
					caltoDate.setTime(fromDate);
					caltoDate.add(Calendar.DAY_OF_YEAR, 1);
					Calendar caltoDate1 =caltoDate;
					caltoDate1.add(Calendar.DAY_OF_YEAR, -1);
					caltoDate.add(Calendar.DAY_OF_YEAR, step);
					for (int i = 0; i < 5; i++) {
						InvestigationAssignmentData param = new InvestigationAssignmentData();
						param.setQuantityList(Integer.toString(tranLostService.findQuantityTranLost(calfromDate.getTime(), caltoDate.getTime(), investigationAssignmentData.getReason(), investigationAssignmentData.getOfficeCode())));
						param.setDateList(format.format(calfromDate.getTime()).substring(0,2).concat("-"+format.format(caltoDate1.getTime())));
						datas.add(param);
						calfromDate.add(Calendar.DAY_OF_YEAR, step);
						if (i==3) {
							caltoDate.setTime(toDate);
						}else {
							caltoDate.add(Calendar.DAY_OF_YEAR, step);
						}
					}
				}
				if (getDiff>20) {
					int step =  (int)Math.floor(getDiff/10);
					calfromDate.setTime(fromDate);
					caltoDate.setTime(fromDate);
					caltoDate.add(Calendar.DAY_OF_YEAR, 1);
					Calendar caltoDate1 =caltoDate;
					caltoDate1.add(Calendar.DAY_OF_YEAR, -1);
					caltoDate.add(Calendar.DAY_OF_YEAR, step);
					for (int i = 0; i < 10; i++) {
						InvestigationAssignmentData param = new InvestigationAssignmentData();
						param.setQuantityList(Integer.toString(tranLostService.findQuantityTranLost(calfromDate.getTime(), caltoDate.getTime(), investigationAssignmentData.getReason(), investigationAssignmentData.getOfficeCode())));
						param.setDateList(format.format(calfromDate.getTime()).substring(0,2).concat("-"+format.format(caltoDate1.getTime())));
						datas.add(param);
						calfromDate.add(Calendar.DAY_OF_YEAR, step);
						if (i==8) {
							caltoDate.setTime(toDate);
						}else {
							caltoDate.add(Calendar.DAY_OF_YEAR, step);
						}
					}
					
				}
				model.addAttribute("dataChart", datas);
			}
			
			return modelAndView;
		} catch (Exception e) {
			throwable = e;
			e.printStackTrace();
			System.out.println(e.getMessage());
		}finally{
			try {
				Object[] args = { investigationAssignmentData };
				long timeMs = System.currentTimeMillis() - startTimeMs;
				auditAccessLogService.saveAuditAccessLogForWeb(httpRequest, "Thống kê hồ sơ bị hủy", args, throwable, timeMs);
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}

		return null;
	}
	
	
	
	
}
