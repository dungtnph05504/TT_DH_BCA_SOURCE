package com.nec.asia.nic.security.rolemanagement;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Collections;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.displaytag.tags.TableTagParameters;
import org.displaytag.util.ParamEncoder;
import org.hibernate.criterion.Order;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.nec.asia.nic.framework.PaginatedResult;
import com.nec.asia.nic.framework.admin.code.domain.Parameters;
import com.nec.asia.nic.framework.admin.code.domain.ParametersId;
import com.nec.asia.nic.framework.admin.code.service.ParametersService;
import com.nec.asia.nic.framework.admin.rbac.domain.Functions;
import com.nec.asia.nic.framework.admin.rbac.domain.Roles;
import com.nec.asia.nic.framework.admin.rbac.domain.Workstations;
import com.nec.asia.nic.framework.admin.rbac.service.FunctionService;
import com.nec.asia.nic.framework.admin.rbac.service.RoleService;
import com.nec.asia.nic.framework.admin.site.domain.SiteRepository;
import com.nec.asia.nic.framework.dao.DaoException;
import com.nec.asia.nic.framework.report.service.AuditAccessLogService;
import com.nec.asia.nic.security.rolemanagement.model.AddEditRoleModel;
import com.nec.asia.nic.security.usermanagement.UserManagementController;
import com.nec.asia.nic.security.workstationmanagement.model.AddEditWorkstationModel;
import com.nec.asia.nic.util.Constants;
import com.nec.asia.nic.util.SiteRepositoryComparator;
import com.nec.asia.nic.util.pagingUtil;
import com.nec.asia.nic.utils.mappers.ObjectMapper;
import com.nec.asia.nic.web.session.UserSession;

/*
 * Modification History:
 * 
 * 30 Dec 2013 (chris lai) : update with audit record
 */
@Controller(value = "RoleManagementController")
@RequestMapping(value = "/adminConsole/roleManagement")
public class RoleManagementController {
	private static final Logger logger = LoggerFactory
			.getLogger(UserManagementController.class);
	private static final ObjectMapper<AddEditRoleModel, Roles> MAPPER = new ObjectMapper<AddEditRoleModel, Roles>();
	private static String ASSENDING_ORDER = "1";
	private static String DECENDING_ORDER = "2";

	@Autowired
	@Qualifier(value = "roleService")
	private RoleService roleService;
	@Autowired
	private ParametersService parametersService;

	@Autowired
	@Qualifier(value = "functionService")
	private FunctionService functionService;
	
	//30 Dec 2013 (chris lai) : update with audit record
	@Autowired
	private AuditAccessLogService auditAccessLogService;

	public RoleManagementController() {

	}

	@RequestMapping(value = "")
	@ExceptionHandler
	public ModelAndView listRole(HttpServletRequest request,ModelMap modelMap) throws Exception {
		// pageSize Processing
		int pageSize = 10;
		int currentPage = 1;
		String tableId = "roleList";
		String sortedElement = "roleId";
		String order = RoleManagementController.DECENDING_ORDER;
		ParametersId id = new ParametersId(Parameters.SCOPE_SYSTEM, Parameters.MAX_RECORDS_PER_PAGE);
		Parameters parameters = parametersService.findById(id);
		
		//get pageSize from DB
		if(parameters!=null) {
			 String pageSizeDb = parameters.getParaShortValue();
			 
			 if(StringUtils.isNotBlank(pageSizeDb) && StringUtils.isNumeric(pageSizeDb)) {
				 pageSize = Integer.parseInt(pageSizeDb);
			 }
		}

		try {
			String sort = request.getParameter(new ParamEncoder(tableId).encodeParameterName(TableTagParameters.PARAMETER_SORT));
			if (StringUtils.isNotBlank(sort))
				sortedElement = sort;
		} catch (Exception ex) {
			logMessage(ex);
		}

		try {
			String requestOrder = request.getParameter(new ParamEncoder(tableId).encodeParameterName(TableTagParameters.PARAMETER_ORDER));
			if (StringUtils.isNotBlank(requestOrder))
				order = requestOrder;
		} catch (Exception ex) {
			logMessage(ex);
		}

		Order hibernateOrder = Order.desc(sortedElement);
		if (order.equalsIgnoreCase(RoleManagementController.DECENDING_ORDER)) {
			hibernateOrder = Order.asc(sortedElement);
		}

		try {
			String pageStr = request.getParameter(new ParamEncoder(tableId).encodeParameterName(TableTagParameters.PARAMETER_PAGE));
			if (StringUtils.isNotBlank(pageStr))
				currentPage = Integer.parseInt(pageStr);
		} catch (Exception ex) {
			logMessage(ex);
		}
		currentPage = !StringUtils.isEmpty(request.getParameter("pageNo")) ? Integer.parseInt(request.getParameter("pageNo")) : 1;
		/*Phúc thêm 30/5/2019*/
		pageSize = Constants.PAGE_SIZE_DEFAULT;
		String roleId = !StringUtils.isEmpty(request.getParameter("roleId")) ? request.getParameter("roleId") : "";
		PaginatedResult<Roles> roles = null;
		if (StringUtils.isNotBlank(roleId)) {
			roles = roleService.getPageByRoleId(roleId, currentPage, pageSize, hibernateOrder);
			modelMap.addAttribute("roleId", roleId);
		}else{
			roles = roleService.findAllForPagination(currentPage, pageSize, hibernateOrder);
		}
		
		if(roles != null && roles.getRows() != null){
			int i = (currentPage - 1) * pageSize;
			for(Roles role : roles.getRows()){
				i++;
				role.setStt(i);
				if(role.getDeleteFlag() != null && role.getDeleteFlag()){
					role.setDelFlag("Y");
				}else{
					role.setDelFlag("N");
				}
			}
		}
		ModelAndView mav = new ModelAndView("security.usermanagement.rolelist");
		modelMap.addAttribute("fnSelected", Constants.HEADING_NIC_ROLE_MANAGEMENT);
		//modelMap.addAttribute("jobList", roles.getRows());
		//request.setAttribute("pageSize", pageSize);
		//phúc edit
		int firstResults = (currentPage - 1) < 0 ? 0 : (currentPage - 1) * pageSize;
		modelMap.addAttribute("pageSize", pageSize);
		modelMap.addAttribute("pageNo", currentPage);
		modelMap.addAttribute("totalPage", pagingUtil.totalPage(roles.getTotal(), pageSize));
		modelMap.addAttribute("startIndex", roles.getTotal() != 0 ? firstResults + 1 : 0);
		modelMap.addAttribute("totalRecord", roles.getTotal());
		modelMap.addAttribute("endIndex", firstResults + roles.getRowSize());
		modelMap.addAttribute("jobList", roles.getRows());
		//end	
		request.setAttribute("roles", roles);
		return mav;
	}

	@RequestMapping(value = "/addEditRole")
	@ExceptionHandler
	public ModelAndView addRole(HttpServletRequest request,ModelMap modelMap) throws Exception{
		return addEditRole(request, null,modelMap);
	}

	@RequestMapping(value = "/addEditRole/{roleId}")
	@ExceptionHandler
	public ModelAndView addEditRole(HttpServletRequest request,
			@PathVariable String roleId,ModelMap modelMap)  throws Exception{
		Roles role = null;
		if (StringUtils.isNotBlank(roleId)) {
			role = roleService.findById(roleId);
			if (role == null) {
				String errors ="";
				errors = ("Không tìm thấy quyền: " + roleId);
				modelMap.addAttribute("Errors", errors);
				return listRole(request,modelMap);
			}
			request.setAttribute("role", role);
		}
		ModelAndView mav = new ModelAndView("security.usermanagement.addEditRole");
		AddEditRoleModel model = new AddEditRoleModel();
		mav.addObject("addEditRoleModel", model);
		modelMap.addAttribute("fnSelected", Constants.HEADING_NIC_ROLE_MANAGEMENT);
		setFunctionDisplay(request, role, mav);
		setSiteCode(request,role,mav);
		return mav;
	}
	
	private void setSiteCode(HttpServletRequest request,Roles role,ModelAndView modelAndView){

		try {
			//List<SiteRepository> siteList = siteService.findAllSite();
			if (true) {
				//Collections.sort(siteList, new SiteRepositoryComparator());

				Map<String, String> systemMap = new LinkedHashMap<String, String>();
				//for (SiteRepository site : siteList) {
				systemMap.put("PA", "PA");
				systemMap.put("A_TTXL", "Trung tâm xử lý");
				systemMap.put("A_TTDH", "Trung tâm điều hành");
				//}
				
				if(systemMap!=null){			
					AddEditRoleModel addEditRoleModel = (AddEditRoleModel) modelAndView.getModel().get("addEditRoleModel");
					
					addEditRoleModel.setSystemMap(systemMap);
					if(role !=null){
						addEditRoleModel.setSystemId(role.getSystemId());
					}
				}
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
	}

	private void setFunctionDisplay(HttpServletRequest request, Roles role, ModelAndView modelAndView) {
		List<Functions> functionList = functionService.findAll();
		List<String> unassignedFunctions = null;
		List<String> assignedFunctions = null;
		//[01 Feb 2016][Tue] - add
		Map<String, String > unassignedFuncMaps = null;
		Map<String, String > assignedFuncMaps = null;
		
		for (Functions function : functionList) {
			if (role != null) {
				if (role.containsFunction(function)) {
					if (assignedFunctions == null) {
						assignedFunctions = new LinkedList<String>();
					}
					assignedFunctions.add(function.getFunctionId());
					
					//[01 Feb 2016][Tue] - add
					if (assignedFuncMaps == null) {
						assignedFuncMaps = new LinkedHashMap<String, String>();
					}
					assignedFuncMaps.put(function.getFunctionId(), function.getFunctionDesc());
					//End
					continue;
				}
			}
			if (unassignedFunctions == null) {
				unassignedFunctions = new LinkedList<String>();
			}
			unassignedFunctions.add(function.getFunctionId());
			
			//[01 Feb 2016][Tue] - add
			if (unassignedFuncMaps == null) {
				unassignedFuncMaps = new LinkedHashMap<String, String>();
			}
			unassignedFuncMaps.put(function.getFunctionId(), function.getFunctionDesc());
			//End

		}
		AddEditRoleModel addEditRoleModel = (AddEditRoleModel) modelAndView.getModel().get("addEditRoleModel");
		addEditRoleModel.setUnassignedFunctions(unassignedFunctions);
		addEditRoleModel.setAssignedFunctions(assignedFunctions);
		//[01 Feb 2016][Tue] - add
		addEditRoleModel.setAssignedFuncMaps(assignedFuncMaps);
		addEditRoleModel.setUnassignedFuncMaps(unassignedFuncMaps);
		//End
	}

	@RequestMapping(value = "/saveRole")
	@ExceptionHandler
	public ModelAndView saveRole(
			HttpServletRequest request,
			@ModelAttribute("addEditRoleModel") AddEditRoleModel addEditRoleModel,ModelMap modelMap) throws Exception {
		//30 Dec 2013 (chris lai) : update with audit record
		long startTimeMs = System.currentTimeMillis();
		Throwable throwable = null;
		String functionName = "Cập nhật quyền";
		
		Roles role = roleService.findById(addEditRoleModel.getRoleId());
		Roles origRole = roleService.findById(addEditRoleModel.getRoleId());
		if (origRole == null) {
			role = new Roles();
		}
		MAPPER.mapObject(addEditRoleModel, role);
		role.setFunctions(null);
		if (addEditRoleModel.getAssignedFunctions() != null
				&& addEditRoleModel.getAssignedFunctions().size() > 0) {
			for (String functionId : addEditRoleModel.getAssignedFunctions()) {
				Functions functionDBO = functionService.findById(functionId);
				role.addRole(functionDBO);
			}
		}
		HttpSession session = request.getSession();
		UserSession userSession = (UserSession) session.getAttribute("userSession");

		try {
			String messages = "";
			role.setUpdateDate(new Date());
			role.setUpdateBy(userSession.getUserName());
			role.setUpdateWkstnId(userSession.getWorkstationId());
			role.setSystemId(addEditRoleModel.getSystemId());
			if (origRole == null) {
				role.setCreateDate(new Date());
				role.setCreateBy(userSession.getUserName());
				role.setUpdateWkstnId(userSession.getWorkstationId());
				roleService.save(role);
				messages = ("Thêm mới quyền: " + role.getRoleId() +" thành công");
				functionName = "Thêm mới quyền " + role.getRoleId();
			} else {
				roleService.saveOrUpdate(role);
				messages=("Cập nhật quyền: " + role.getRoleId() +" thành công");
				//30 Dec 2013 (chris lai) : update with audit record
				functionName = "Cập nhật quyền " + role.getRoleId();
			}
			modelMap.addAttribute("messages", messages);
		} catch (Exception e) {
			String error = "";
			error = ("Cập nhật quyền không thành công.");
			logMessage(e);
			modelMap.addAttribute("Errors", error);
			addEditRole(request, addEditRoleModel.getRoleId(),modelMap);
			//30 Dec 2013 (chris lai) : update with audit record
			e.printStackTrace();	
			throwable = e;
			return listRole(request,modelMap);
		}finally {
			//30 Dec 2013 (chris lai) : update with audit record
			try {
				Object[] args = { addEditRoleModel };
				long timeMs = System.currentTimeMillis() - startTimeMs;
				auditAccessLogService.saveAuditAccessLogForWeb(request, functionName,args,throwable, timeMs);
			} catch(Exception exp){
			}
		}

		String messages = "";
		messages=("Cập nhật quyền: " + role.getRoleId() + " thành công.");
		modelMap.addAttribute("messages", messages);
		return listRole(request,modelMap);
	}

	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	@ExceptionHandler
	public ModelAndView deleteRole(HttpServletRequest request,ModelMap modelMap) throws Exception {
		String deleteId = request.getParameter("deleteId");
		Roles role = roleService.findById(deleteId);
		//30 Dec 2013 (chris lai) : update with audit record
		long startTimeMs = System.currentTimeMillis();
		Throwable throwable = null;
		
		try {
			roleService.delete(role);
		} catch (Exception e) {
			String error = "";
			error = ("Không xóa được quyền: " + deleteId +", Lý do: Quyền này đã được gán cho người dùng");
			modelMap.addAttribute("Errors", error);
			logMessage(e);
			//30 Dec 2013 (chris lai) : update with audit record
			e.printStackTrace();
			throwable = e;
			return listRole(request,modelMap);
		}finally {
			//30 Dec 2013 (chris lai) : update with audit record
			try {
				String functionName = "Xóa quyền "+ deleteId;
				Object[] args = { role };
				long timeMs = System.currentTimeMillis() - startTimeMs;
				auditAccessLogService.saveAuditAccessLogForWeb(request, functionName,args,throwable, timeMs);
			} catch(Exception exp){
			}
		}
		String messages = "";
		messages=("Đã xóa quyền:" + deleteId + " thành công.");
		modelMap.addAttribute("messages", messages);
		return listRole(request,modelMap);
	}

	private void logMessage(Exception ex) {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		PrintStream ps = new PrintStream(baos);
		ex.printStackTrace(ps);
		logger.error(ex.getMessage() + " : \n" + new String(baos.toByteArray()));
	}
}
