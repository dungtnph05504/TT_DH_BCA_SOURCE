/**
 * 
 */
package com.nec.asia.nic.persoLocation.controller;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
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
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.ModelAndView;

import com.nec.asia.nic.comp.listHandover.domain.LocationForm;
import com.nec.asia.nic.comp.listHandover.domain.NicPersoLocations;
import com.nec.asia.nic.comp.listHandover.service.PersoLocationsService;
import com.nec.asia.nic.comp.trans.dao.EppInventoryDao;
import com.nec.asia.nic.comp.trans.domain.EppInventory;
import com.nec.asia.nic.comp.trans.domain.EppInventoryItems;
import com.nec.asia.nic.comp.trans.domain.EppInventoryItemsDetail;
import com.nec.asia.nic.comp.trans.domain.PersoSites;
import com.nec.asia.nic.comp.trans.dto.AssignmentFilter;
import com.nec.asia.nic.comp.trans.dto.AssignmentFilterAll;
import com.nec.asia.nic.comp.trans.dto.DetailHandoverDto;
import com.nec.asia.nic.comp.trans.dto.InventoryDto;
import com.nec.asia.nic.comp.trans.dto.InvestigationHitData;
import com.nec.asia.nic.comp.trans.exception.JobNoLongerAssignedToOfficerException;
import com.nec.asia.nic.comp.trans.service.EppInventoryItemsDetailService;
import com.nec.asia.nic.comp.trans.service.EppInventoryItemsService;
import com.nec.asia.nic.comp.trans.service.EppInventoryService;
import com.nec.asia.nic.comp.trans.service.NicTransactionService;
import com.nec.asia.nic.comp.trans.service.NicUploadJobService;
import com.nec.asia.nic.comp.trans.service.PersoSitesService;
import com.nec.asia.nic.framework.PaginatedResult;
import com.nec.asia.nic.framework.admin.code.service.CodesService;
import com.nec.asia.nic.framework.admin.code.service.ParametersService;
import com.nec.asia.nic.framework.admin.code.service.PaymentDefService;
import com.nec.asia.nic.framework.admin.rbac.domain.Users;
import com.nec.asia.nic.framework.admin.rbac.service.UserService;
import com.nec.asia.nic.framework.admin.site.dao.SiteRepositoryDao;
import com.nec.asia.nic.framework.admin.site.domain.SiteRepository;
import com.nec.asia.nic.framework.admin.site.service.SiteService;
import com.nec.asia.nic.framework.controller.AbstractController;
import com.nec.asia.nic.framework.report.service.AuditAccessLogService;
import com.nec.asia.nic.investigation.controller.InvestigationAssignmentData;
import com.nec.asia.nic.reprint.dto.PersoLocationDto;
import com.nec.asia.nic.util.BaseListResponse;
import com.nec.asia.nic.util.Constants;
import com.nec.asia.nic.util.PrintLocation;
import com.nec.asia.nic.util.RequestBase;
import com.nec.asia.nic.util.pagingUtil;
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
@RequestMapping(value = "/persoLocation")
public class PersoLocationController extends AbstractController {

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
	private PersoSitesService persoSitesService;
	
	@Autowired
	@Qualifier("userService")
	private UserService userService;
	
	@Autowired
	private EppInventoryService inventoryService;
	
	@Autowired
	private EppInventoryItemsService inventoryItemsService;
	
	@Autowired
	private EppInventoryItemsDetailService inventoryItemsDetailService;
	
	@Autowired
	private EppInventoryDao inventoryDao;
	
	@Autowired
	private SiteService siteService;

	private static final Logger logger = LoggerFactory.getLogger(PersoLocationController.class);

	@ModelAttribute("userAssignment")
	public Map<String, String> userAssignment() {
		List<Users> codeList = userService.findAll();
		Map<String, String> userAssignment = new LinkedHashMap<String, String>();
		for (Users ricCode : codeList) {
			userAssignment.put(ricCode.getUserId(), ricCode.getUserId());
		}
		return userAssignment;
	}
	
	@ModelAttribute("inventoryCodeList")
	public Map<String, String> inventoryCodeList() {
		List<EppInventory> invList = inventoryService.findAllInvestory(null, "Y");
		Map<String, String> invCodeList = new LinkedHashMap<String, String>();
		for (EppInventory invCode : invList) {
			invCodeList.put(invCode.getCode(), invCode.getName());
		}
		return invCodeList;
	}

	@RequestMapping(value = "/applyFilter")
	public ModelAndView applyFilter(
			@ModelAttribute(value = "formData") InvestigationAssignmentData investigationAssignmentData,
			WebRequest request, HttpServletRequest httpRequest, ModelMap model) {
		logger.info("applyFilter");

		return this.getPersoLocations(investigationAssignmentData,
				request, httpRequest, model, 1, null, null);
	}
	
	@RequestMapping(value = "/addLocation")
	public ModelAndView addLocation(@ModelAttribute(value = "locationForm") LocationForm locationForm,
			WebRequest request, HttpServletRequest httpRequest, ModelMap model) {
		ModelAndView mav = new ModelAndView("persoLocations.add.edit");
		mav.addObject("locationForm", locationForm);
		return mav;
	}
	
	
	@RequestMapping(value = "/addSiteLocation/{id}")
	public ModelAndView addSiteLocation(@ModelAttribute(value = "locationForm") LocationForm locationForm, @PathVariable Long id,
			WebRequest request, HttpServletRequest httpRequest, ModelMap model) {
		ModelAndView mav = new ModelAndView("siteLocations.add.edit");
		List<NicPersoLocations> locaList = persoLocationsService.findPersoByStatus(1, null);
		Map<Long, String> persoMap = new LinkedHashMap<Long, String>();
		Map<String, String> siteMap = new LinkedHashMap<String, String>();
		if(locaList != null){
			for(NicPersoLocations pls : locaList){
				persoMap.put(pls.getIdPerso(), pls.getName());
			}
		}
		List<SiteRepository> siteList = siteService.findAllByActive("Y");
		if(siteList != null){
			for(SiteRepository site : siteList){
				siteMap.put(site.getSiteId(), site.getSiteName());
			}
		}
		model.addAttribute("idPerso", id);
		model.addAttribute("persoMap", persoMap);
		model.addAttribute("siteMap", siteMap);
		mav.addObject("locationForm", locationForm);
		return mav;
	}
	
	@RequestMapping(value = "/editSiteLocation/{idPerso}/{id}")
	public ModelAndView editSiteLocation(@ModelAttribute(value = "locationForm") LocationForm locationForm, @PathVariable Long id, @PathVariable Long idPerso,
			WebRequest request, HttpServletRequest httpRequest, ModelMap model) {
		ModelAndView mav = new ModelAndView("siteLocations.add.edit");
		List<NicPersoLocations> locaList = persoLocationsService.findPersoByStatus(1, null);
		Map<Long, String> persoMap = new LinkedHashMap<Long, String>();
		Map<String, String> siteMap = new LinkedHashMap<String, String>();
		if(locaList != null){
			for(NicPersoLocations pls : locaList){
				persoMap.put(pls.getIdPerso(), pls.getName());
			}
		}
		List<SiteRepository> siteList = siteService.findAllByActive("Y");
		if(siteList != null){
			for(SiteRepository site : siteList){
				siteMap.put(site.getSiteId(), site.getSiteName());
			}
		}
		PersoSites site = persoSitesService.findPersoSitesById(id);
		if(site != null){
			locationForm.setIdPerso(site.getPersoId());
			locationForm.setCode(site.getSiteId());
			locationForm.setStage(site.getActive() ? "Y" : "N");
		}
		model.addAttribute("idPerso", idPerso);
		model.addAttribute("persoMap", persoMap);
		model.addAttribute("siteMap", siteMap);
		mav.addObject("locationForm", locationForm);
		return mav;
	}
	
	@RequestMapping(value = "/editLocation/{id}")
	public ModelAndView editLocation(@ModelAttribute(value = "locationForm") LocationForm locationForm, @PathVariable Long id,
			WebRequest request, HttpServletRequest httpRequest, ModelMap model) {
		ModelAndView mav = new ModelAndView("persoLocations.add.edit");
		NicPersoLocations nicPerso = persoLocationsService.findById(id);
		LocationForm loca = new LocationForm(nicPerso); 
		mav.addObject("locationForm", loca);
		return mav;
	}
	
	@RequestMapping(value = "/saveSiteLocation")
	@ResponseBody
	public String saveSiteLocation(HttpServletRequest httpRequest,@ModelAttribute("locationForm")LocationForm locationForm, ModelMap nodel) throws Exception {
		String result = "";
		try {
			PersoSites persoSites = persoSitesService.findPersoSitesById(locationForm.getIdPerso(), locationForm.getCode());
			if(persoSites != null){
				result = "exist";
			}else{
				PersoSites nicPerso = new PersoSites();
				nicPerso.setPersoId(locationForm.getIdPerso());
				nicPerso.setSiteId(locationForm.getCode());
				nicPerso.setActive(locationForm.getStage().equals("Y") ? true : false);
				boolean check = persoSitesService.createNew(nicPerso);
				if(check){
					result = "success";
				}else{
					result = "fail"; 
				}
			}
		} catch(Exception exp) {
			exp.printStackTrace();
			result = "fail";
		}
		return result;
	}
	
	@RequestMapping(value = "/updateSiteLocation")
	@ResponseBody
	public String updateSiteLocation(HttpServletRequest httpRequest,@ModelAttribute("locationForm")LocationForm locationForm, ModelMap nodel) throws Exception {
		String result = "";
		try {
			PersoSites persoSites = persoSitesService.findPersoSitesById(locationForm.getIdPerso(), locationForm.getCode());
			if(persoSites != null){
				persoSites.setActive(locationForm.getStage().equals("Y") ? true : false);
				boolean check = persoSitesService.createNew(persoSites);
				if(check){
					result = "success";
				}else{
					result = "fail"; 
				}
			}
		} catch(Exception exp) {
			exp.printStackTrace();
			result = "fail";
		}
		return result;
	}
	
	@RequestMapping(value = "/saveLocation")
	@ResponseBody
	public String saveSiteGroups(HttpServletRequest httpRequest,@ModelAttribute("locationForm")LocationForm locationForm, ModelMap nodel) throws Exception {
		String result = "";
		try {
			NicPersoLocations nicPerso = new NicPersoLocations(locationForm);
			nicPerso.setCreateBy(1L);
			nicPerso.setCreateDate(new Date());
			NicPersoLocations location = persoLocationsService.findPersoByCodeAnd(locationForm.getCode(), locationForm.getIdPerso());
			if(location != null){
				result = "exist";
			}else{
				boolean check = persoLocationsService.createRecordPersoLocations(nicPerso);
				if(check){
					result = "success";
				}else{
					result = "fail"; 
				}
			}
		} catch(Exception exp) {
			exp.printStackTrace();
			result = "fail";
		}
		return result;
	}
	
	@RequestMapping(value = "/delLocations/{id}")
	public ModelAndView delLocation(@ModelAttribute(value = "formData") InvestigationAssignmentData formData, @PathVariable Long id,
			WebRequest request, HttpServletRequest httpRequest, ModelMap model) {
		try {
			persoLocationsService.delete(id);	
			model.addAttribute("mes", 1);
		} catch (Exception e) {
			e.printStackTrace();
			model.addAttribute("mes", 0);
		}
		return getPersoLocations(formData, request, httpRequest, model, 1, null, null);
	}
	
	@RequestMapping(value = "/delSiteLocation/{idPerso}/{id}")
	public ModelAndView delSiteLocation(@ModelAttribute(value = "formData") InvestigationAssignmentData inv, @PathVariable long idPerso, @PathVariable long id,
			WebRequest request, HttpServletRequest httpRequest, Model model) throws Throwable {
		try {
			PersoSites sites = persoSitesService.findPersoSitesById(id);
			persoSitesService.delSiteLocation(sites);
			model.addAttribute("mes", 1);
		} catch (Exception e) {
			e.printStackTrace();
			model.addAttribute("mes", 0);
		}
		return detailPersoLocations(inv, idPerso, httpRequest, model);
	}
	
	@RequestMapping(value = "/updateLocation")
	@ResponseBody
	public String updateLocation(HttpServletRequest httpRequest,@ModelAttribute("locationForm")LocationForm locationForm, ModelMap nodel) throws Exception {
		String result = "";
		try {
			NicPersoLocations location = persoLocationsService.findPersoByCode(locationForm.getCode(), locationForm.getIdPerso());
			if(location != null){
				location.setName(locationForm.getName());
				location.setAddress(locationForm.getAddress());
				location.setDescription(locationForm.getDescription());
				location.setStatus(locationForm.getStatus());
				location.setLastModifiedBy(1L);
				location.setLastModifiedDate(new Date());
				boolean check = persoLocationsService.createRecordPersoLocations(location);
				if(check){
					result = "success";
				}else{
					result = "fail"; 
				}
			}
		} catch(Exception exp) {
			exp.printStackTrace();
			result = "fail";
		}
		return result;
	}
	
	
	
	@RequestMapping(value = "/persoLocations")
	public ModelAndView getPersoLocations(
			@ModelAttribute(value = "formData") InvestigationAssignmentData investigationAssignmentData,
			WebRequest request, HttpServletRequest httpRequest, ModelMap model) {
		logger.info("getPersoLocations");

		int pageNo = !StringUtils.isEmpty(request.getParameter("pageNo")) ? Integer.parseInt(request.getParameter("pageNo")) : 1;

		RequestBase rq = new RequestBase();
		rq.setPageSize(20);
		rq.setPageIndex(1);
		rq.setStatus(-1);
		try {
			/*Phúc tạm đóng 23/12/2019 Gọi API bị lỗi
			 * List<PrintLocation> list_ = GetDataAllPrintLocation(rq);
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

								persoLocationsService.createRecordPersoLocations(pl);
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

			for(NicPersoLocations pl: listPerso){
				if(StringUtils.isNotEmpty(pl.getIssuePlace())){
					int exist = StringUtils.indexOf(pl.getIssuePlace(), ',');
					if(exist > 0){
						String[] split = pl.getIssuePlace().split(",");
						for(int i = 0; i < split.length; i++){
							if(StringUtils.isNotEmpty(split[i])){
								PersoSites persoSite = persoSitesService.findPersoSitesByCode(split[i]);
								if(persoSite == null){
									persoSite = new PersoSites();
									persoSite.setSiteId(split[i]);
								}
								persoSite.setPersoId(pl.getIdPerso());
								persoSite.setActive(true);
								persoSitesService.createNew(persoSite);
							}
						}
					}
					else
					{
						PersoSites persoSite = persoSitesService.findPersoSitesByCode(pl.getIssuePlace());
						if(persoSite == null){
							persoSite = new PersoSites();
							persoSite.setSiteId(pl.getIssuePlace());
						}
						persoSite.setPersoId(pl.getIdPerso());
						persoSite.setActive(true);
						persoSitesService.createNew(persoSite);
					}
				}
			}*/
		} catch (Exception e) {
			e.printStackTrace();
		}
		/*String tableId = "row";
		String pageNumber = request.getParameter((new ParamEncoder(tableId)
				.encodeParameterName(TableTagParameters.PARAMETER_PAGE)));
		if (StringUtils.isNotBlank(pageNumber)
				&& StringUtils.isNumeric(pageNumber)) {
			pageNo = Integer.parseInt(pageNumber);
		}*/

		return this.getPersoLocations(investigationAssignmentData, request,
				httpRequest, model, pageNo, null, null);
	}

	public ModelAndView getPersoLocations(
			@ModelAttribute(value = "formData") InvestigationAssignmentData investigationAssignmentData,
			WebRequest request, HttpServletRequest httpRequest, ModelMap model,
			int pageNo, List<String> listOfMessages, List<String> listOfErrors) {
		logger.info("getPersoLocations pageNo");
		HttpSession session = httpRequest.getSession();
		UserSession userSession = (UserSession) session.getAttribute("userSession");
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

//		if (investigationAssignmentData == null) {
//			investigationAssignmentData = new InvestigationAssignmentData();
//		}
//
//		investigationAssignmentData.cleanUpStrings();

		PaginatedResult<NicPersoLocations> pr = null;
		try {
			int pageSize = Constants.PAGE_SIZE_DEFAULT;
			pr = persoLocationsService.findAllPersoLocations(pageNo, pageSize,
					new AssignmentFilterAll(investigationAssignmentData.getSearchTransactionId(),null,"", null, null, "" ,"",""));

			List<PersoLocationDto> list = new ArrayList<PersoLocationDto>();
			
			if (pr != null) {
				for(NicPersoLocations per : pr.getRows()){
					PersoLocationDto dto = new PersoLocationDto();
					dto.setId(per.getId());
					dto.setCode(per.getCode());
					dto.setName(per.getName());
					dto.setAddress(per.getAddress());
					dto.setDescription(per.getDescription());
					dto.setIssuePlace(per.getIssuePlace());
					dto.setCreateDate(per.getCreateDate() != null ? HelperClass.convertDateToString2(per.getCreateDate()) : "");
					dto.setIdPerso(per.getIdPerso());
					list.add(dto);
				}
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
				model.addAttribute("fnSelected", Constants.HEADING_NIC_PERSO_LOCATIONS);
				ModelAndView modelAndView = new ModelAndView("persoLocations.list", null);
				investigationAssignmentData.cleanUpForNextPage();
				modelAndView.addObject("formData", investigationAssignmentData);
				return modelAndView;
			} else {
				model.addAttribute("fnSelected", Constants.HEADING_NIC_PERSO_LOCATIONS);
				ModelAndView modelAndView = new ModelAndView("persoLocations.list", null);
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
		int pageNo = !StringUtils.isEmpty(httpRequest.getParameter("pageNo")) ? Integer.parseInt(httpRequest.getParameter("pageNo")) : 1;
		return this.startDetailPersoLocations(id, pageNo,httpRequest, model, null);
	}
	
	
	@RequestMapping(value = { "/detailPersoLocations/{id}" })
	public ModelAndView detailPersoLocations(@ModelAttribute(value = "formData") InvestigationAssignmentData inv,
			@PathVariable long id, HttpServletRequest httpRequest, Model model) throws Throwable {
		ModelAndView mav = new ModelAndView("persoLocations.detail.list");
		int pageNo = !StringUtils.isEmpty(httpRequest.getParameter("pageNo")) ? Integer.parseInt(httpRequest.getParameter("pageNo")) : 1;
		int pageSize = Constants.PAGE_SIZE_DEFAULT;
		PaginatedResult<DetailHandoverDto> sitePr = persoSitesService.findPagiPersoSites(id, pageNo, pageSize);
		List<DetailHandoverDto> siteList = new ArrayList<DetailHandoverDto>();
		if(sitePr != null){
			siteList = sitePr.getRows();
			int firstResults = (pageNo - 1) < 0 ? 0 : (pageNo - 1) * pageSize;
			model.addAttribute("pageSize", pageSize);
			model.addAttribute("pageNo", pageNo);
			model.addAttribute("totalPage", pagingUtil.totalPage(sitePr.getTotal(), pageSize));
			model.addAttribute("startIndex", sitePr.getTotal() != 0 ? firstResults + 1 : 0);
			model.addAttribute("totalRecord", sitePr.getTotal());
			model.addAttribute("endIndex", firstResults + sitePr.getRowSize());
			model.addAttribute("JList", siteList);
		}
		model.addAttribute("idPerso", id);
		mav.addObject("formData", inv);
		return mav;
	}

	public ModelAndView startDetailPersoLocations(long id, int pageNo,
			HttpServletRequest httpRequest, Model model,
			List<String> listOfMessages) throws Throwable {
		logger.info("NIC Start DetailPersoLocations compare, listOfMessages");

		ModelAndView modelAndView = new ModelAndView("persoLocations.compare");
		this.initializeModelAndViewForms(modelAndView);
		int pageSize = 10;
		List<SiteRepository> listSR = siteRepositoryDao.findAll();
		NicPersoLocations jobDetails = persoLocationsService.findById(id);
		List<NicPersoLocations> listPerso = persoLocationsService.findAll();
		//Map<String, Boolean> listSite = new LinkedHashMap<String, Boolean>();
		List<DetailHandoverDto> listSitePage = new ArrayList<DetailHandoverDto>();
		List<DetailHandoverDto> listSite = new ArrayList<DetailHandoverDto>();
		if (listSR != null) {
			for (SiteRepository a : listSR) {
				boolean contains = false;
				if(a.getSiteGroups().getLevelNic().equals("1")){
					SiteRepository siteR = siteRepositoryDao.findById(a.getSiteId());
					String nameSite = "";
					if(siteR != null)
						nameSite = siteR.getSiteName();
					else
						nameSite = a.getSiteId();
					DetailHandoverDto dto = new DetailHandoverDto();
					String sites = jobDetails.getIssuePlace();
					if (!StringUtils.isEmpty(sites)) {
						String[] arraySite = sites.split(",");
						contains = java.util.Arrays.asList(arraySite).contains(a.getSiteId());
						if(contains){
							//listSite.put(a.getSiteId(), contains);
							dto.setSiteId(nameSite);
							dto.setStagePrint(contains);
							listSite.add(dto);
						}
						else{
							Boolean check = true;
							if(listPerso != null && listPerso.size() > 0){
								listPerso.remove(jobDetails);
								for(NicPersoLocations item : listPerso){
									if(!StringUtils.isBlank(item.getIssuePlace())){
										String[] arraySiteA = item.getIssuePlace().split(",");
										if(java.util.Arrays.asList(arraySiteA).contains(a.getSiteId())){
											check = false;
										}
									}
								}
								if(check){
									dto.setSiteId(nameSite);
									dto.setStagePrint(contains);
									listSite.add(dto);
									//listSite.put(a.getSiteId(), contains);
								}
							}
						}
					}
					else {
						Boolean check = true;
						if(listPerso != null && listPerso.size() > 0){
							listPerso.remove(jobDetails);
							for(NicPersoLocations item : listPerso){
								if(!StringUtils.isBlank(item.getIssuePlace())){
									String[] arraySite = item.getIssuePlace().split(",");
									if(java.util.Arrays.asList(arraySite).contains(a.getSiteId())){
										check = false;
									}
								}
							}
							if(check){
								dto.setSiteId(nameSite);
								dto.setStagePrint(contains);
								listSite.add(dto);
							}
								//listSite.put(a.getSiteId(), contains);
						}
					}
				}
			}
		}
		int totalItems = listSite.size() - (pageNo - 1) * pageSize; // 12 - 2
		if(totalItems > pageSize){
			totalItems = pageSize;
		}
		for(int j = (pageNo - 1) * pageSize; j < totalItems + (pageNo - 1) * pageSize; j++){			
			listSitePage.add(listSite.get(j));
		}
		//phúc edit
		int firstResults = (pageNo - 1) < 0 ? 0 : (pageNo - 1) * pageSize;
		model.addAttribute("pageSize", pageSize);
		model.addAttribute("pageNo", pageNo);
		model.addAttribute("totalPage", pagingUtil.totalPage(listSite.size(), pageSize));
		model.addAttribute("startIndex", listSite.size() != 0 ? firstResults + 1 : 0);
		model.addAttribute("totalRecord", listSite.size());
		model.addAttribute("endIndex", firstResults + listSitePage.size());
		model.addAttribute("jobList", listSitePage);
		//end	
		model.addAttribute("idJob", id);
		model.addAttribute("persoLocation", jobDetails.getName());
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
						"forward:/servlet/persoLocation/persoLocations");
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
				"forward:/servlet/persoLocation/persoLocations");
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
	}

	public void initializeModelAndViewForms(ModelAndView mav) {
		mav.addObject("investigationHitData", new InvestigationHitData());
	}
	
	@RequestMapping(value="/inventoryItems")
	public ModelAndView showInventoryList(@ModelAttribute(value = "formData") InvestigationAssignmentData inv, WebRequest request, ModelMap model, HttpServletRequest httpRequest){
		ModelAndView mav = new ModelAndView("inventory.items.show");
		int pageSize = Constants.PAGE_SIZE_DEFAULT;
		int pageNo = !StringUtils.isEmpty(request.getParameter("pageNo")) ? Integer.parseInt(request.getParameter("pageNo")) : 1;
		PaginatedResult<InventoryDto> pr = inventoryService.findInventoryBySearch(new AssignmentFilter(null, inv.getRegSiteCode(), inv.getPackageCode(), inv.getStageLoad(), inv.getPackageNumber(), inv.getListName(), null), pageNo, pageSize);
		List<InventoryDto> list = new ArrayList<InventoryDto>();
		if(pr != null){
			list = pr.getRows();
		}
		int firstResults = (pageNo - 1) < 0 ? 0 : (pageNo - 1) * pageSize;
		model.addAttribute("pageSize", pageSize);
		model.addAttribute("pageNo", pageNo);
		model.addAttribute("totalPage", pagingUtil.totalPage(pr.getTotal(), pageSize));
		model.addAttribute("startIndex", pr.getTotal() != 0 ? firstResults + 1 : 0);
		model.addAttribute("totalRecord", pr.getTotal());
		model.addAttribute("endIndex", firstResults + pr.getRowSize());
		model.addAttribute("invenList", list);
		mav.addObject("formData", inv);
		return mav;
	}
	
	@RequestMapping(value="/reloadInvDetail/{stage}")
	public ModelAndView reloadInvDetail(@ModelAttribute(value = "formData") InvestigationAssignmentData inv, @PathVariable String stage, WebRequest request, ModelMap model, HttpServletRequest httpRequest){
		ModelAndView mav = new ModelAndView("inventory.items.show");
		int pageSize = Constants.PAGE_SIZE_DEFAULT;
		int pageNo = !StringUtils.isEmpty(request.getParameter("pageNo")) ? Integer.parseInt(request.getParameter("pageNo")) : 1;
		PaginatedResult<InventoryDto> pr = inventoryService.findInventoryBySearch(new AssignmentFilter(null, inv.getRegSiteCode(), inv.getPackageCode(), inv.getStageLoad(), inv.getPackageNumber(), inv.getListName(), null), pageNo, pageSize);
		List<InventoryDto> list = new ArrayList<InventoryDto>();
		if(pr != null){
			list = pr.getRows();
		}
		int firstResults = (pageNo - 1) < 0 ? 0 : (pageNo - 1) * pageSize;
		model.addAttribute("pageSize", pageSize);
		model.addAttribute("pageNo", pageNo);
		model.addAttribute("totalPage", pagingUtil.totalPage(pr.getTotal(), pageSize));
		model.addAttribute("startIndex", pr.getTotal() != 0 ? firstResults + 1 : 0);
		model.addAttribute("totalRecord", pr.getTotal());
		model.addAttribute("endIndex", firstResults + pr.getRowSize());
		model.addAttribute("invenList", list);
		model.addAttribute("mesSave", stage);
		mav.addObject("formData", inv);
		return mav;
	}
	
	@RequestMapping(value="/reloadInvItem/{stage}")
	public ModelAndView reloadInvItem(@ModelAttribute(value = "formData") InvestigationAssignmentData inv, @PathVariable String stage, WebRequest request, ModelMap model, HttpServletRequest httpRequest){
		ModelAndView mav = new ModelAndView("inventory.dt.show");
		int pageSize = Constants.PAGE_SIZE_DEFAULT;
		int pageNo = !StringUtils.isEmpty(request.getParameter("pageNo")) ? Integer.parseInt(request.getParameter("pageNo")) : 1;
		PaginatedResult<InventoryDto> pr = inventoryService.findInventoryItemsBySearch(new AssignmentFilter(null, inv.getRegSiteCode(), inv.getPackageCode(), null, null, null, inv.getPackageNumber()), pageNo, pageSize);
		List<InventoryDto> list = new ArrayList<InventoryDto>();
		if(pr != null){
			list = pr.getRows();
		}
		int firstResults = (pageNo - 1) < 0 ? 0 : (pageNo - 1) * pageSize;
		model.addAttribute("pageSize", pageSize);
		model.addAttribute("pageNo", pageNo);
		model.addAttribute("totalPage", pagingUtil.totalPage(pr.getTotal(), pageSize));
		model.addAttribute("startIndex", pr.getTotal() != 0 ? firstResults + 1 : 0);
		model.addAttribute("totalRecord", pr.getTotal());
		model.addAttribute("endIndex", firstResults + pr.getRowSize());
		model.addAttribute("invenList", list);
		model.addAttribute("mesSave", stage);
		mav.addObject("formData", inv);
		return mav;
	}
	
	@RequestMapping(value="/reloadInventory/{stage}")
	public ModelAndView reloadInventory(@ModelAttribute(value = "formData") InvestigationAssignmentData inv, @PathVariable String stage, WebRequest request, ModelMap model, HttpServletRequest httpRequest){
		ModelAndView mav = new ModelAndView("inventory.show");
		int pageSize = Constants.PAGE_SIZE_DEFAULT;
		int pageNo = !StringUtils.isEmpty(request.getParameter("pageNo")) ? Integer.parseInt(request.getParameter("pageNo")) : 1;
		PaginatedResult<EppInventory> pr = inventoryService.findAllForPagination(pageNo, pageSize);
		List<EppInventory> list = new ArrayList<EppInventory>();
		if(pr != null){
			list = pr.getRows();
		}
		int firstResults = (pageNo - 1) < 0 ? 0 : (pageNo - 1) * pageSize;
		model.addAttribute("pageSize", pageSize);
		model.addAttribute("pageNo", pageNo);
		model.addAttribute("totalPage", pagingUtil.totalPage(pr.getTotal(), pageSize));
		model.addAttribute("startIndex", pr.getTotal() != 0 ? firstResults + 1 : 0);
		model.addAttribute("totalRecord", pr.getTotal());
		model.addAttribute("endIndex", firstResults + pr.getRowSize());
		model.addAttribute("invenList", list);
		model.addAttribute("mesSave", stage);
		mav.addObject("formData", inv);
		return mav;
	}
	
	@RequestMapping(value="/deleteItems/{itemId}")
	public ModelAndView deleteItems(@ModelAttribute(value="formData") InvestigationAssignmentData inv, @PathVariable String itemId,WebRequest request, HttpServletRequest httpRequest, ModelMap model){
		Long id = Long.parseLong(itemId);
		Boolean mesDel = inventoryItemsDetailService.deleteInventory(id);
		if(mesDel){
			model.addAttribute("mesDel", 1);
		}else{
			model.addAttribute("mesDel", 0);
		}
		return this.showInventoryList(inv, request, model, httpRequest);
	}
	
	@RequestMapping(value="/deleteInv/{itemId}")
	public ModelAndView deleteInv(@ModelAttribute(value="formData") InvestigationAssignmentData inv, @PathVariable String itemId,WebRequest request, HttpServletRequest httpRequest, ModelMap model){
		Integer id = Integer.parseInt(itemId);
		Boolean mesDel = inventoryService.deleteInv(id);
		if(mesDel){
			model.addAttribute("mesDel", 1);
		}else{
			model.addAttribute("mesDel", 0);
		}
		return this.inventory(inv, request, model, httpRequest);
	}
	
	@ResponseBody
	@RequestMapping(value="/addInventory")
	public ModelAndView addInventory(WebRequest request, HttpServletRequest httpRequest, ModelMap model){
		ModelAndView mav = new ModelAndView("inventory.show.popup");
		Map<String, String> statues = new HashMap<String, String>();
		statues.put("Y", "Đang hoạt động");
		statues.put("N", "Không hoạt động");
		model.addAttribute("statusList", statues);
		model.addAttribute("style", "add");
		model.addAttribute("dto", new EppInventory());
		return mav;
	}
	
	@ResponseBody
	@RequestMapping(value="/addInventoryItem")
	public ModelAndView addInventoryItem(WebRequest request, HttpServletRequest httpRequest, ModelMap model){
		ModelAndView mav = new ModelAndView("inventory.item.popup");
		List<EppInventory> invs = inventoryService.findAllInvestory(null, "Y");
		Map<Integer, String> invList = new HashMap<Integer, String>();
		if(invs != null){
			for(EppInventory epp : invs){
				invList.put(epp.getId(), epp.getName());
			}
		}
		model.addAttribute("invList", invList);
		Map<Integer, String> categoryList = new HashMap<Integer, String>();
		categoryList.put(1, "Hộ chiếu thường");
		categoryList.put(2, "Hộ chiếu điện tử");
		model.addAttribute("categoryList", categoryList);
		model.addAttribute("style", "add");
		model.addAttribute("dto", new InventoryDto());
		return mav;
	}
	
	@RequestMapping(value="/delInventoryItem/{itemId}")
	public ModelAndView delInventoryItem(@ModelAttribute(value="formData") InvestigationAssignmentData inv, @PathVariable String itemId,WebRequest request, HttpServletRequest httpRequest, ModelMap model){
		Long id = Long.parseLong(itemId);
		Boolean mesDel = inventoryItemsService.deleteInvItem(id);
		if(mesDel){
			model.addAttribute("mesDel", 1);
		}else{
			model.addAttribute("mesDel", 0);
		}
		return this.inventoryShow(inv, request, model, httpRequest);
	}
	
	@ResponseBody
	@RequestMapping(value="/editInventoryItem/{itemId}")
	public ModelAndView editInventoryItem(@PathVariable String itemId,WebRequest request, HttpServletRequest httpRequest, ModelMap model){
		ModelAndView mav = new ModelAndView("inventory.item.popup");
		List<EppInventory> invs = inventoryService.findAllInvestory(null, "Y");
		Map<Integer, String> invList = new HashMap<Integer, String>();
		if(invs != null){
			for(EppInventory epp : invs){
				invList.put(epp.getId(), epp.getName());
			}
		}
		model.addAttribute("invList", invList);
		Map<Integer, String> categoryList = new HashMap<Integer, String>();
		categoryList.put(1, "Hộ chiếu thường");
		categoryList.put(2, "Hộ chiếu điện tử");
		model.addAttribute("categoryList", categoryList);
		model.addAttribute("style", "edit");
		Long id = null;
		if(StringUtils.isNotEmpty(itemId)){
			id = Long.parseLong(itemId);
		}
		EppInventoryItems items = inventoryItemsService.findInventoryItemsById(id, null);
		if(items != null){
			InventoryDto dto = new InventoryDto(items);
			model.addAttribute("dto", dto);
		}
		return mav;
	}
	
	@ResponseBody
	@RequestMapping(value="/editInventory/{invId}")
	public ModelAndView editInventory(@PathVariable String invId, WebRequest request, HttpServletRequest httpRequest, ModelMap model){
		ModelAndView mav = new ModelAndView("inventory.show.popup");
		Map<String, String> statues = new HashMap<String, String>();
		statues.put("Y", "Đang hoạt động");
		statues.put("N", "Không hoạt động");
		model.addAttribute("statusList", statues);
		model.addAttribute("style", "edit");
		Integer id = Integer.parseInt(invId);
		EppInventory inv = null;
		if(id != null){
			inv = inventoryService.findInvById(id);	
		}else{
			inv = new EppInventory();
		}
		model.addAttribute("dto", inv);
		return mav;
	}
	
	@ResponseBody
	@RequestMapping(value="/saveChangeInv")
	public Integer saveChangeInv(@RequestBody String yourArray, ModelMap model, HttpServletRequest httpRequest){
		JSONObject onb = new JSONObject(yourArray);
		try {
			String tenKho = (onb.has("tenKho") && !onb.isNull("tenKho")) ? onb.getString("tenKho") : "";
			String maKho = (onb.has("maKho") && !onb.isNull("maKho")) ? onb.getString("maKho") : "";
			String trangThai = (onb.has("trangThai") && !onb.isNull("trangThai")) ? onb.getString("trangThai") : "";
			String diaChi = (onb.has("diaChi") && !onb.isNull("diaChi")) ? onb.getString("diaChi") : "";
			String ghiChu = (onb.has("ghiChu") && !onb.isNull("ghiChu")) ? onb.getString("ghiChu") : "";
			String idKho = (onb.has("idKho") && !onb.isNull("idKho")) ? onb.getString("idKho") : "";
			Integer id = null;
			if(StringUtils.isNotEmpty(idKho)){
				id = Integer.parseInt(idKho);
			}
			EppInventory epp = new EppInventory();
			epp.setId(id);
			epp.setCode(maKho);
			epp.setName(tenKho);
			epp.setDescription(ghiChu);
			epp.setActive(trangThai);
			epp.setAddress(diaChi);
			inventoryService.saveOrUpdate(epp);
			return 1;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}
	
	@ResponseBody
	@RequestMapping(value="/saveChangeInvItem")
	public Integer saveChangeInvItem(@RequestBody String yourArray, ModelMap model, HttpServletRequest httpRequest){
		JSONObject onb = new JSONObject(yourArray);
		HttpSession session = httpRequest.getSession();
		UserSession userSession = (UserSession) session.getAttribute("userSession");
		try {
			String maKho = (onb.has("maKho") && !onb.isNull("maKho")) ? onb.getString("maKho") : "";
			String soBG = (onb.has("soBG") && !onb.isNull("soBG")) ? onb.getString("soBG") : "";
			String ngayTN = (onb.has("ngayTN") && !onb.isNull("ngayTN")) ? onb.getString("ngayTN") : "";
			String ngayTNhan = null;
			if(StringUtils.isNotEmpty(ngayTN)){
				ngayTNhan = HelperClass.convertStringToString2(ngayTN);
			}
			String loaiHC = (onb.has("loaiHC") && !onb.isNull("loaiHC")) ? onb.getString("loaiHC") : "";
			String nguoiTN = (onb.has("nguoiTN") && !onb.isNull("nguoiTN")) ? onb.getString("nguoiTN") : "";
			String nguoiBG = (onb.has("nguoiBG") && !onb.isNull("nguoiBG")) ? onb.getString("nguoiBG") : "";
			String soLo = (onb.has("soLo") && !onb.isNull("soLo")) ? onb.getString("soLo") : "";
			String maPhieu = (onb.has("maPhieu") && !onb.isNull("maPhieu")) ? onb.getString("maPhieu") : "";
			Long id = null;
			if(StringUtils.isNotEmpty(maPhieu)){
				id = Long.parseLong(maPhieu);
			}
			EppInventoryItems eppInv = new EppInventoryItems(id, new Date(), userSession.getUserId(), Integer.parseInt(maKho), soBG, ngayTNhan, nguoiTN, "I", soLo, Integer.parseInt(loaiHC), nguoiBG);
			inventoryItemsService.saveOrUpdate(eppInv);
			return 1;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}
	
	@ResponseBody
	@RequestMapping(value="/addInvItems")
	public ModelAndView addInvItems(WebRequest request, HttpServletRequest httpRequest, ModelMap model){
		ModelAndView mav = new ModelAndView("inventory.detail.popup");
		List<String> itemList = inventoryDao.getAllInventoryItems();
		List<String> batchList = inventoryDao.getAllBatchNo();
		Map<String, String> items = new HashMap<String, String>();
		if(itemList != null){
			for(String key : itemList){
				items.put(key, key);
			}
		}
		Map<String, String> batchs = new HashMap<String, String>();
		if(batchList != null){
			for(String key : batchList){
				batchs.put(key, key);
			}
		}
		Map<String, String> statues = new HashMap<String, String>();
		statues.put("I", "Chưa sử dụng");
		statues.put("D", "Đã in");
		statues.put("H", "In hỏng");
		statues.put("L", "Phôi lỗi");
		model.addAttribute("itemList", items);
		model.addAttribute("batchList", batchs);
		model.addAttribute("statusList", statues);
		model.addAttribute("style", "add");
		model.addAttribute("dto", new InventoryDto());
		return mav;
	}
	
	@ResponseBody
	@RequestMapping(value="/editInvItems/{id}")
	public ModelAndView editInvItems(@PathVariable String id, WebRequest request, HttpServletRequest httpRequest, ModelMap model){
		ModelAndView mav = new ModelAndView("inventory.detail.popup");
		List<String> itemList = inventoryDao.getAllInventoryItems();
		List<String> batchList = inventoryDao.getAllBatchNo();
		Map<String, String> items = new HashMap<String, String>();
		if(itemList != null){
			for(String key : itemList){
				items.put(key, key);
			}
		}
		Map<String, String> batchs = new HashMap<String, String>();
		if(batchList != null){
			for(String key : batchList){
				batchs.put(key, key);
			}
		}
		Map<String, String> statues = new HashMap<String, String>();
		statues.put("I", "Chưa sử dụng");
		statues.put("D", "Đã in");
		statues.put("H", "In hỏng");
		statues.put("L", "Phôi lỗi");
		model.addAttribute("itemList", items);
		model.addAttribute("batchList", batchs);
		model.addAttribute("statusList", statues);
		model.addAttribute("style", "edit");
		Long itemId = Long.parseLong(id);
		EppInventoryItemsDetail epp = inventoryItemsDetailService.findById(itemId);
		if(epp != null){
			InventoryDto dto = new InventoryDto(epp);
			model.addAttribute("dto", dto);			
		}else{
			model.addAttribute("dto", new InventoryDto());			
		}
		return mav;
	}
	
	@ResponseBody
	@RequestMapping(value="/saveChangeItems")
	public Integer saveChangeItems(@RequestBody String yourArray, ModelMap model, HttpServletRequest httpRequest){
		JSONObject onb = new JSONObject(yourArray);
		HttpSession session = httpRequest.getSession();
		UserSession userSession = (UserSession) session.getAttribute("userSession");
		try {
			String maPhieu = (onb.has("maPhieu") && !onb.isNull("maPhieu")) ? onb.getString("maPhieu") : "";
			String trangThai = (onb.has("trangThai") && !onb.isNull("trangThai")) ? onb.getString("trangThai") : "";
			String soLo = (onb.has("soLo") && !onb.isNull("soLo")) ? onb.getString("soLo") : "";
			String soChip = (onb.has("soChip") && !onb.isNull("soChip")) ? onb.getString("soChip") : "";
			String soPhoiC = (onb.has("soPhoiC") && !onb.isNull("soPhoiC")) ? onb.getString("soPhoiC") : "";
			String soPhoiN = (onb.has("soPhoiN") && !onb.isNull("soPhoiN")) ? onb.getString("soPhoiN") : "";
			String maPhoi = (onb.has("maPhoi") && !onb.isNull("maPhoi")) ? onb.getString("maPhoi") : "";
			Long id = null;
			if(StringUtils.isNotEmpty(maPhoi)){
				id = Long.parseLong(maPhoi);
			}
			EppInventoryItemsDetail epp = new EppInventoryItemsDetail();
			epp.setId(id);
			epp.setInventoryItemsId(Long.parseLong(maPhieu));
			epp.setStatus(trangThai);
			epp.setBatchNo(soLo);
			epp.setChipSeriesNo(soChip);
			epp.setDocChars(soPhoiC);
			epp.setDocNum(soPhoiN);
			epp.setSyncStatus("N");
			epp.setUpdatedBy(userSession.getUserId());
			epp.setUpdateTs(new Date());
			inventoryItemsDetailService.saveOrUpdate(epp);
			return 1;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}
	
	@RequestMapping(value="/inventory")
	public ModelAndView inventory(@ModelAttribute(value = "formData") InvestigationAssignmentData inv, WebRequest request, ModelMap model, HttpServletRequest httpRequest){
		ModelAndView mav = new ModelAndView("inventory.show");
		int pageSize = Constants.PAGE_SIZE_DEFAULT;
		int pageNo = !StringUtils.isEmpty(request.getParameter("pageNo")) ? Integer.parseInt(request.getParameter("pageNo")) : 1;
		PaginatedResult<EppInventory> pr = inventoryService.findAllForPagination(pageNo, pageSize);
		List<EppInventory> list = new ArrayList<EppInventory>();
		if(pr != null){
			list = pr.getRows();
		}
		int firstResults = (pageNo - 1) < 0 ? 0 : (pageNo - 1) * pageSize;
		model.addAttribute("pageSize", pageSize);
		model.addAttribute("pageNo", pageNo);
		model.addAttribute("totalPage", pagingUtil.totalPage(pr.getTotal(), pageSize));
		model.addAttribute("startIndex", pr.getTotal() != 0 ? firstResults + 1 : 0);
		model.addAttribute("totalRecord", pr.getTotal());
		model.addAttribute("endIndex", firstResults + pr.getRowSize());
		model.addAttribute("invenList", list);
		mav.addObject("formData", inv);
		return mav;
	}
	
	@RequestMapping(value="/inventoryShow")
	public ModelAndView inventoryShow(@ModelAttribute(value = "formData") InvestigationAssignmentData inv, WebRequest request, ModelMap model, HttpServletRequest httpRequest){
		ModelAndView mav = new ModelAndView("inventory.dt.show");
		int pageSize = Constants.PAGE_SIZE_DEFAULT;
		int pageNo = !StringUtils.isEmpty(request.getParameter("pageNo")) ? Integer.parseInt(request.getParameter("pageNo")) : 1;
		PaginatedResult<InventoryDto> pr = null;
		try {
			pr = inventoryService.findInventoryItemsBySearch(new AssignmentFilter(null, inv.getRegSiteCode(), inv.getPackageCode(), null, null, null, inv.getPackageNumber()), pageNo, pageSize);
		} catch (Exception e) {
			e.printStackTrace();
		}
		List<InventoryDto> list = new ArrayList<InventoryDto>();
		if(pr != null){
			list = pr.getRows();
		}
		int firstResults = (pageNo - 1) < 0 ? 0 : (pageNo - 1) * pageSize;
		model.addAttribute("pageSize", pageSize);
		model.addAttribute("pageNo", pageNo);
		model.addAttribute("totalPage", pagingUtil.totalPage(pr.getTotal(), pageSize));
		model.addAttribute("startIndex", pr.getTotal() != 0 ? firstResults + 1 : 0);
		model.addAttribute("totalRecord", pr.getTotal());
		model.addAttribute("endIndex", firstResults + pr.getRowSize());
		model.addAttribute("invenList", list);
		mav.addObject("formData", inv);
		return mav;
	}
}
