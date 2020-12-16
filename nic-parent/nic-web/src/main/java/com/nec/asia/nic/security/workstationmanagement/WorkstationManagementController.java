package com.nec.asia.nic.security.workstationmanagement;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang.StringUtils;
import org.hibernate.criterion.Order;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.ModelAndView;

import com.nec.asia.nic.framework.PaginatedResult;
import com.nec.asia.nic.framework.admin.code.domain.ParametersId;
import com.nec.asia.nic.framework.admin.code.service.ParametersService;
import com.nec.asia.nic.framework.admin.rbac.domain.Roles;
import com.nec.asia.nic.framework.admin.rbac.domain.Workstations;
import com.nec.asia.nic.framework.admin.rbac.service.RoleService;
import com.nec.asia.nic.framework.admin.rbac.service.WorkStationService;
import com.nec.asia.nic.framework.admin.site.domain.SiteRepository;
import com.nec.asia.nic.framework.admin.site.service.SiteService;
import com.nec.asia.nic.framework.controller.AbstractController;
import com.nec.asia.nic.framework.dao.DaoException;
import com.nec.asia.nic.framework.report.service.AuditAccessLogService;
import com.nec.asia.nic.framework.security.service.ADUser;
import com.nec.asia.nic.security.usermanagement.model.AddEditUserModel;
import com.nec.asia.nic.security.workstationmanagement.model.AddEditWorkstationModel;
import com.nec.asia.nic.util.Constants;
import com.nec.asia.nic.util.SiteRepositoryComparator;
import com.nec.asia.nic.util.pagingUtil;
import com.nec.asia.nic.utils.DateUtil;
import com.nec.asia.nic.utils.mappers.ObjectMapper;
import com.nec.asia.nic.web.session.UserSession;

/*
 * Modification History:
 * 
 * 30 Dec 2013 (chris lai) : update with audit record
 */
@Controller(value="WorkstationManagementController")
@RequestMapping(value="/adminConsole/workstationManagement")
public class WorkstationManagementController  extends AbstractController {
	private static final Logger logger = LoggerFactory.getLogger(WorkstationManagementController.class);
	private static final ObjectMapper<AddEditWorkstationModel, Workstations> MAPPER = new ObjectMapper<AddEditWorkstationModel, Workstations>();
	private static String ASSENDING_ORDER="1";
	private static String DECENDING_ORDER="2";

	
	@Autowired
	private RoleService roleService;
	@Autowired
	private ParametersService parametersService;
	
	@Autowired
	private WorkStationService workStationService;
	
	//30 Dec 2013 (chris lai) : update with audit record
	@Autowired
	private AuditAccessLogService auditAccessLogService;
	
	@Autowired
	private SiteService siteService;

	
	public WorkstationManagementController() {
		// TODO Auto-generated constructor stub
	}
	
	
	@RequestMapping(value="")
	@ExceptionHandler
	public ModelAndView listWorkstation(HttpServletRequest request, ModelMap modelMap) throws Exception {
		// data already move to ajax method.
		ModelAndView mav = new ModelAndView("security.usermanagement.workstationlist");
		modelMap.addAttribute("fnSelected",	Constants.HEADING_NIC_WORKSTATION_MANAGEMENT);
		//phúc edit 30/5/2019
		int pageNo = !StringUtils.isEmpty(request.getParameter("pageNo")) ? Integer.parseInt(request.getParameter("pageNo")) : 1;
		int pageSize = Constants.PAGE_SIZE_DEFAULT;
		
		String sortedElement = request.getParameter("sortname") != null ? request.getParameter("sortname") : "wkstnId";
		String order = request.getParameter("sortorder") != null ? request.getParameter("sortorder") : "asc";
		Order hibernateOrder = Order.desc(sortedElement);
		if ("asc".equalsIgnoreCase(order)) {
			hibernateOrder = Order.asc(sortedElement);
		}
		
		String searchWsId = request.getParameter("query");
		logger.debug("Loading the workstation list by key word: queryAjax");
		//pageSize = Constants.DEFAULT_PAGE_MAX_SIZE;
	
		PaginatedResult<Workstations> workstations = workStationService.findAllForPagination(new Workstations(searchWsId), pageNo, pageSize, hibernateOrder);
		List<WorkstationInfo> wsInfos = new ArrayList<WorkstationInfo>();
		if(workstations.getRows() != null){
			int i = (pageNo - 1) * pageSize;	
			for (Workstations wkstnDBO : workstations.getRows()) {
				WorkstationInfo wsInfo = new WorkstationInfo();
	
				try {
					PropertyUtils.copyProperties(wsInfo, wkstnDBO);
				} catch (Exception ex) {
					logMessage(ex);
				}
				i++;
				wsInfo.setStt(i);
				if(wsInfo.getDeleteFlag() != null && wsInfo.getDeleteFlag()){
					wsInfo.setDelFlag("Y");
				}else{
					wsInfo.setDelFlag("N");
				}
				wsInfo.setFmtCreateDate(DateUtil.parseDate(wsInfo.getCreateDate(), DateUtil.FORMAT_DD_MM_YYYY));	
				wsInfo.setFmtUpdateDate(DateUtil.parseDate(wsInfo.getUpdateDate(), DateUtil.FORMAT_DD_MM_YYYY));	
				wsInfos.add(wsInfo);
			}
		}
		//phúc edit
		int firstResults = (pageNo - 1) < 0 ? 0 : (pageNo - 1) * pageSize;
		modelMap.addAttribute("pageSize", pageSize);
		modelMap.addAttribute("pageNo", pageNo);
		modelMap.addAttribute("totalPage", pagingUtil.totalPage(workstations.getTotal(), pageSize));
		modelMap.addAttribute("startIndex", workstations.getTotal() != 0 ? firstResults + 1 : 0);
		modelMap.addAttribute("totalRecord", workstations.getTotal());
		modelMap.addAttribute("endIndex", firstResults + workstations.getRowSize());
		modelMap.addAttribute("jobList", wsInfos);
		//end	
		//modelMap.addAttribute("jobList", wsInfos);
		//PaginatedResult<WorkstationInfo> result = new PaginatedResult<WorkstationInfo>(workstations.getTotal(), workstations.getPage(), wsInfos);
		//end
		return mav;
	}
	

	@RequestMapping(value = "/query_ajax")
	@ResponseBody
	@ExceptionHandler
	public PaginatedResult<WorkstationInfo> queryAjax(WebRequest request) 
		throws Exception {

		int pageNo = request.getParameter("page") != null ? Integer.parseInt(request.getParameter("page")) : 1;
		int pageSize = request.getParameter("rp") != null ? Integer.parseInt(request.getParameter("rp")) : com.nec.asia.nic.framework.Constants.PAGE_SIZE_DEFAULT;
		
		String sortedElement = request.getParameter("sortname") != null ? request.getParameter("sortname") : "wkstnId";
		String order = request.getParameter("sortorder") != null ? request.getParameter("sortorder") : "asc";
		Order hibernateOrder = Order.desc(sortedElement);
		if ("asc".equalsIgnoreCase(order)) {
			hibernateOrder = Order.asc(sortedElement);
		}
		
		String searchWsId = request.getParameter("query");
		logger.debug("Loading the workstation list by key word: queryAjax");

	
		PaginatedResult<Workstations> workstations = workStationService.findAllForPagination(new Workstations(searchWsId), pageNo, pageSize, hibernateOrder);
		
		List<WorkstationInfo> wsInfos = new ArrayList<WorkstationInfo>();
		if(workstations.getRows() != null){
			for (Workstations wkstnDBO : workstations.getRows()) {
				WorkstationInfo wsInfo = new WorkstationInfo();
	
				try {
					PropertyUtils.copyProperties(wsInfo, wkstnDBO);
				} catch (Exception ex) {
					logMessage(ex);
				}
				
				wsInfo.setFmtCreateDate(DateUtil.parseDate(wsInfo.getCreateDate(), DateUtil.FORMAT_DDdashMMMdashYYYY));	
				wsInfo.setFmtUpdateDate(DateUtil.parseDate(wsInfo.getUpdateDate(), DateUtil.FORMAT_DDdashMMMdashYYYY));	
				wsInfos.add(wsInfo);
			}
		}
		PaginatedResult<WorkstationInfo> result = new PaginatedResult<WorkstationInfo>(workstations.getTotal(), workstations.getPage(), wsInfos);
		logger.debug("Exit loading the workstation list by key word: queryAjax " + wsInfos.size());
		return result;
	}
	
	@RequestMapping(value="/addEditWorkStation")
	@ExceptionHandler
	public ModelAndView addWorkstation(HttpServletRequest request, ModelMap modelMap) throws Exception {
		return addEditWorkstation(request, null, modelMap);
	}

	@RequestMapping(value="/addEditWorkStation/{wkstnId}")
	@ExceptionHandler
	public ModelAndView addEditWorkstation(HttpServletRequest request,@PathVariable String wkstnId,ModelMap modelMap) throws Exception {
		Workstations workstation =null;
		if(StringUtils.isNotBlank(wkstnId)){
			workstation = workStationService.findById(wkstnId);
			//check for invalid user entry either from db or url
			if(workstation == null){
				List<String> errors  = new LinkedList<String>();
				errors.add("Máy trạm: "+ wkstnId +" không hợp lệ.");
				request.setAttribute("Errors", errors);
				return listWorkstation(request, modelMap);
			}
			
			request.setAttribute("workstation", workstation);
			
		}
		
		
		ModelAndView mav = new ModelAndView("security.usermanagement.addEditWorkStation");
		AddEditWorkstationModel model = new AddEditWorkstationModel();
		mav.addObject("addEditWorkstationModel", model);
		setWorkstationDisplay(request,workstation,mav);
		setSiteCode(request,workstation,mav);
		modelMap.addAttribute("fnSelected", Constants.HEADING_NIC_WORKSTATION_MANAGEMENT);
		return mav;
	}
	
	private void setSiteCode(HttpServletRequest request,Workstations workstation,ModelAndView modelAndView){

		try {
			List<SiteRepository> siteList = siteService.findAllSite();
			if (siteList != null) {
				Collections.sort(siteList, new SiteRepositoryComparator());

				Map<String, String> sitCodeMap = new LinkedHashMap<String, String>();
				for (SiteRepository site : siteList) {
					sitCodeMap.put(site.getSiteId(), site.getSiteName());
				}
				
				if(sitCodeMap!=null){			
					AddEditWorkstationModel addEditWorkstationModel = (AddEditWorkstationModel) modelAndView.getModel().get("addEditWorkstationModel");
					
					addEditWorkstationModel.setSitCodeMap(sitCodeMap);
					if(workstation !=null){
						addEditWorkstationModel.setSiteCode(workstation.getSiteCode());
					}
				}
			}
		} catch (DaoException e) {
			logger.error(e.getMessage());
		}
	}
	
	
	
	private void setWorkstationDisplay(HttpServletRequest request,Workstations workstation,ModelAndView modelAndView){
		List<Roles> roleList = roleService.findAll();
		List<String> unassignedRoles = null;
		List<String> assignedRoles = null;
		//[01 Feb 2016][Tue] - add
		Map<String, String > unassignedRoleMaps = null;
		Map<String, String > assignedRoleMaps = null;
		for(Roles role: roleList){
			if(workstation!=null){
				if(workstation.containsRole(role)){
					if(assignedRoles==null){
						assignedRoles = new LinkedList<String>();
					}
					assignedRoles.add(role.getRoleId());
					
					//[01 Feb 2016][Tue] - add
					if (assignedRoleMaps == null) {
						assignedRoleMaps = new LinkedHashMap<String, String>();
					}
					assignedRoleMaps.put(role.getRoleId(), role.getRoleDesc());
					//End
					
					continue;
				}
			}
			
			if(unassignedRoles == null){
				unassignedRoles = new LinkedList<String>();
			}
			unassignedRoles.add(role.getRoleId());
			
			//[01 Feb 2016][Tue] - add
			if (unassignedRoleMaps == null) {
				unassignedRoleMaps = new LinkedHashMap<String, String>();
			}
			unassignedRoleMaps.put(role.getRoleId(), role.getRoleDesc());
			//End
		}
		AddEditWorkstationModel addEditWorkstationModel = (AddEditWorkstationModel) modelAndView.getModel().get("addEditWorkstationModel");
		addEditWorkstationModel.setUnassignedRoles(unassignedRoles);
		addEditWorkstationModel.setAssignedRoles(assignedRoles);
		//[01 Feb 2016][Tue] - add
		addEditWorkstationModel.setAssignedRoleMaps(assignedRoleMaps);
		addEditWorkstationModel.setUnassignedRoleMaps(unassignedRoleMaps);
	}
	
	
	@RequestMapping(value="/saveWorkstation")
	@ExceptionHandler
	public ModelAndView saveWorkstation(HttpServletRequest request,@ModelAttribute("addEditWorkstationModel") AddEditWorkstationModel addEditWorkstationModel,ModelMap modelMap) throws Exception{
		//30 Dec 2013 (chris lai) : update with audit record
		long startTimeMs = System.currentTimeMillis();
		Throwable throwable = null;
		String functionName = "workStationMgmtSave";
		try{
			Workstations workstation = workStationService.findById(addEditWorkstationModel.getWkstnId());
			Workstations origWorkstation= workStationService.findById(addEditWorkstationModel.getWkstnId());
			if(origWorkstation == null){
				workstation = new Workstations();
			}
			MAPPER.mapObject(addEditWorkstationModel, workstation);
			workstation.setRoles(null);
			if(addEditWorkstationModel.getAssignedRoles() != null && addEditWorkstationModel.getAssignedRoles().size() > 0){
				for(String role : addEditWorkstationModel.getAssignedRoles()){
					Roles roleDBO = roleService.findById(role);
					workstation.addRole(roleDBO);
				}
			}
			
			HttpSession session = request.getSession();
			UserSession userSession = (UserSession) session.getAttribute("userSession");
			try {
				String messages = "";
				workstation.setUpdateDate(new Date());
				workstation.setUpdateBy(userSession.getUserName());
				workstation.setUpdateWkstnId(userSession.getWorkstationId());
				//Set siteGroup theo site code
				SiteRepository siteR = siteService.getSiteRepoById(addEditWorkstationModel.getSiteCode());
				if(siteR != null){					
					workstation.setSiteCode(addEditWorkstationModel.getSiteCode());
				}
				if(origWorkstation == null){
					workstation.setCreateDate(new Date());
					workstation.setCreateBy(userSession.getUserName());
					workstation.setUpdateWkstnId(userSession.getWorkstationId());
					workStationService.save(workstation);	
					messages = ("Thêm mới máy trạm: " + workstation.getWkstnId() +" thành công.");
				}else{
					workStationService.saveOrUpdate(workstation);
					messages = ("Cập nhật máy trạm: " + workstation.getWkstnId() +" thành công.");
					//30 Dec 2013 (chris lai) : update with audit record
					functionName = "Cập nhật máy trạm " + workstation.getWkstnId();
				}
				modelMap.addAttribute("messages", messages);
			} catch (Exception e) {
				String error = "";
				error = ("Xảy ra lỗi trong khi cập nhật máy trạm: " + workstation.getWkstnId());
				logMessage(e);
				modelMap.addAttribute("Errors", error);
				addEditWorkstation(request, addEditWorkstationModel.getWkstnId(), modelMap);
				return listWorkstation(request, modelMap);
			}
	//		List<String> messages = new LinkedList<String>();
	//		messages.add("Succesful saving of workstation : " + workstation.getWkstnId() + ".");
	//		request.setAttribute("messages", messages);
		}catch(Exception ex){
			String error = "";
			error = ("Xảy ra lỗi trong khi cập nhật máy trạm: " + addEditWorkstationModel.getWkstnId()+", Lý do: "+ex.getMessage());
			modelMap.addAttribute("Errors", error);
			//30 Dec 2013 (chris lai) : update with audit record
			ex.printStackTrace();	
			throwable = ex;
		}
		finally {
			try {
				//30 Dec 2013 (chris lai) : update with audit record
				Object[] args = { addEditWorkstationModel };
				long timeMs = System.currentTimeMillis() - startTimeMs;
				auditAccessLogService.saveAuditAccessLogForWeb(request, functionName,args,throwable, timeMs);
			} catch(Exception exp){
			}
		}
		modelMap.addAttribute("fnSelected", Constants.HEADING_NIC_WORKSTATION_MANAGEMENT);
		return listWorkstation(request, modelMap);
	}
	
	@RequestMapping(value="/delete",method=RequestMethod.POST)
	@ExceptionHandler
	public ModelAndView deleteWorkstation(HttpServletRequest request, ModelMap modelMap) throws Exception {
		String deleteId = request.getParameter("deleteId");
		Workstations workstation = workStationService.findById(deleteId);
		//30 Dec 2013 (chris lai) : update with audit record
		long startTimeMs = System.currentTimeMillis();
		Throwable throwable = null;
		
		try{
			if(workstation!=null){
				workStationService.delete(workstation);
			}
		}catch (Exception e) {	
			String error = "";
			error = ("Xảy ra lỗi trong khí xóa máy trạm: " + deleteId);
			modelMap.addAttribute("Errors", error);
			logMessage(e);
			//30 Dec 2013 (chris lai) : update with audit record
			e.printStackTrace();
			throwable = e;
		}finally {
			try {
				//30 Dec 2013 (chris lai) : update with audit record
				String functionName = "Xoá máy trạm " + deleteId;
				Object[] args = { workstation };
				long timeMs = System.currentTimeMillis() - startTimeMs;
				auditAccessLogService.saveAuditAccessLogForWeb(request, functionName,args,throwable, timeMs);
			} catch(Exception exp){
			}
		}
		String messages = "";
		messages = ("Xóa máy trạm: " + deleteId + " thành cồng.");
		modelMap.addAttribute("messages", messages);
		modelMap.addAttribute("fnSelected", Constants.HEADING_NIC_WORKSTATION_MANAGEMENT);
		return listWorkstation(request, modelMap);
	}

	
	private void logMessage(Exception ex) {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		  PrintStream ps = new PrintStream(baos);
		  ex.printStackTrace(ps);
		  logger.error(ex.getMessage()+" : \n" + new String(baos.toByteArray()));
	}

}
