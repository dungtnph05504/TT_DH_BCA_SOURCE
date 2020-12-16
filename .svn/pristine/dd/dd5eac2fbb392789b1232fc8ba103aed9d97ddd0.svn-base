package com.nec.asia.nic.security.usermanagement;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
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
import org.springframework.retry.policy.ExceptionClassifierRetryPolicy;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.ModelAndView;

import com.nec.asia.nic.comp.trans.domain.SyncQueueJob;
import com.nec.asia.nic.comp.trans.service.SyncQueueJobService;
import com.nec.asia.nic.comp.ws.log.domain.CreateMessageException;
import com.nec.asia.nic.framework.PaginatedResult;
import com.nec.asia.nic.framework.admin.code.domain.CodeValues;
import com.nec.asia.nic.framework.admin.code.domain.Parameters;
import com.nec.asia.nic.framework.admin.code.domain.ParametersId;
import com.nec.asia.nic.framework.admin.code.service.CodesService;
import com.nec.asia.nic.framework.admin.code.service.ParametersService;
import com.nec.asia.nic.framework.admin.job.dto.UserDto;
import com.nec.asia.nic.framework.admin.rbac.domain.Roles;
import com.nec.asia.nic.framework.admin.rbac.domain.Users;
import com.nec.asia.nic.framework.admin.rbac.service.RoleService;
import com.nec.asia.nic.framework.admin.rbac.service.UserService;
import com.nec.asia.nic.framework.admin.site.dao.impl.SiteGroupsDaoImpl;
import com.nec.asia.nic.framework.admin.site.domain.Department;
import com.nec.asia.nic.framework.admin.site.domain.SiteGroups;
import com.nec.asia.nic.framework.admin.site.domain.SiteRepository;
import com.nec.asia.nic.framework.admin.site.service.DepartmentService;
import com.nec.asia.nic.framework.admin.site.service.SiteService;
import com.nec.asia.nic.framework.controller.AbstractController;
import com.nec.asia.nic.framework.dao.DaoException;
import com.nec.asia.nic.framework.report.service.AuditAccessLogService;
import com.nec.asia.nic.framework.security.ad.ActiveDirectoryService;
import com.nec.asia.nic.framework.security.ad.helper.UserDTO;
import com.nec.asia.nic.framework.security.service.ADUser;
import com.nec.asia.nic.framework.security.service.exception.ExistingActiveDirectoryException;
import com.nec.asia.nic.framework.security.service.exception.UserCRUDException;
import com.nec.asia.nic.security.usermanagement.model.AddEditUserModel;
import com.nec.asia.nic.security.usermanagement.model.MapData;
import com.nec.asia.nic.util.Constants;
import com.nec.asia.nic.util.SiteRepositoryComparator;
import com.nec.asia.nic.util.pagingUtil;
import com.nec.asia.nic.utils.BaseDTOMapper;
import com.nec.asia.nic.utils.DateUtil;
import com.nec.asia.nic.utils.mappers.ObjectMapper;
import com.nec.asia.nic.web.session.UserSession;




/**

 * @author Ambrocio,Paulo Johannes D.
 * @Company: NEC ASIA PACIFIC PTE
 * @since : Jul 22, 2013
 * <p>
 *	Decription here
 * </p>
 * 
 *
 */

/*
 * Modification History:
 * 
 * 20 Dec 2013 (chris): remove securityService use only userService
 * 23 Dec 2013 (chris): change context
 * 24 Dec 2013 (chris): fix for update User method.
 * 30 Dec 2013 (chris lai) : update with audit record
 * 19 Aug 2014 (chris): add reset password audit access log.
 * 12 Jan 2016 (tuenv): add delete AD user method.
 */

@Controller(value="UserManagementController")
@RequestMapping(value="/adminConsole/userManagement")
public class UserManagementController  extends AbstractController {
	private static final Logger logger = LoggerFactory.getLogger(UserManagementController.class);
	private static final ObjectMapper<AddEditUserModel, ADUser> ADD_EDIT_USER_MODEL_MAPPER = new ObjectMapper<AddEditUserModel, ADUser>();
	private static String ASSENDING_ORDER="1";
	private static String DECENDING_ORDER="2";
	private static String SITE_CODE="SITE_CODE";

	public static final String USER_MANAGEMENT_CONTROLLER__AD_LIST__SEARCH__FIRST_NAME   							    = "USER_MANAGEMENT_CONTROLLER__AD_LIST__SEARCH__FIRST_NAME";
	public static final String USER_MANAGEMENT_CONTROLLER__AD_LIST__SEARCH__MIDDLE_NAME 							    = "USER_MANAGEMENT_CONTROLLER__AD_LIST__SEARCH__MIDDLE_NAME";
	public static final String USER_MANAGEMENT_CONTROLLER__AD_LIST__SEARCH__SURNAME      							    = "USER_MANAGEMENT_CONTROLLER__AD_LIST__SEARCH__SURNAME";
	public static final String USER_MANAGEMENT_CONTROLLER__AD_LIST__SEARCH__USERID       							    = "USER_MANAGEMENT_CONTROLLER__AD_LIST__SEARCH__USERID";
	public static final String USER_MANAGEMENT_CONTROLLER__AD_LIST__SEARCH__IS_A_PROGRAMMATIC_CALL_TO_REFRESH_FLEXIGRID	= "USER_MANAGEMENT_CONTROLLER__AD_LIST__SEARCH__IS_A_PROGRAMMATIC_CALL_TO_REFRESH_FLEXIGRID";
	private static final String QUEUE_OBJ_TYPE_USER_ADD = "USER_ADD";
	public static String EMPTY_INPUT="EMPTYEMPTYEMPTY";
	public static String BOOLEAN_STRING_TRUE ="true";
	public static String BOOLEAN_STRING_FALSE="false";
	public static String SUCCESS="SUCCESS";
	public static String ERROR="ERROR";
	public static String ACTIVE_SITE_Y="Y";
	
	@Autowired
	private SyncQueueJobService queueJobService;
	
	@Autowired
//	@Qualifier(value="userService")
	private UserService userService;
	
	@Autowired
//	@Qualifier(value="roleService")
	private RoleService roleService;
	
	@Autowired
	private ParametersService parametersService;
	
	//30 Dec 2013 (chris lai) : update with audit record
	@Autowired
	private AuditAccessLogService auditAccessLogService;
	
//	@Autowired
//	@Qualifier(value="securityService")
//	private SecurityService securityService;
	@Autowired
	//@Qualifier(value="codesService")
	private CodesService codesService;
	
	@Autowired
	private SiteService siteService;
	
	
	@Autowired
	private DepartmentService departmentService;
	
	
	public UserManagementController() {
		// TODO Auto-generated constructor stub
	}
	
	@ModelAttribute("siteList")
	public Map<String, String> siteList() throws DaoException {
		Map<String, String> list = new LinkedHashMap<String, String>();
		List<SiteRepository> listSite = siteService.findAllByActive(ACTIVE_SITE_Y);
		Collections.sort(listSite, new Comparator<SiteRepository>() {			
			@Override
			public int compare(SiteRepository o1, SiteRepository o2) {
				// TODO Auto-generated method stub
				return o1.getSiteName().compareTo(o2.getSiteName());
			}

		});
		for(SiteRepository sr : listSite){
			list.put(sr.getSiteId(), sr.getSiteName());
		}
		return list;
	}
	
	@ModelAttribute("stageList")
	public Map<String, String> stageList() throws DaoException {
		Map<String, String> list = new LinkedHashMap<String, String>();
		list.put("", "Tất cả");
		list.put("Y", "Có liệu lực");
		list.put("N", "Không có hiệu lực");
		return list;
	}
	
	@RequestMapping(value="")
	@ExceptionHandler
	public ModelAndView listUser(HttpServletRequest request,Model model) throws Exception {
		  // pageSize Processing
		  //int pageSize = 10;
		  //int currentPage = 1;
		 // int pageNo = 1;
//		  int pageSize = 10000;
//		  ParametersId id = new ParametersId();
//		  id.setParaName(Parameters.MAX_RECORDS_PER_PAGE);
//		  id.setParaScope(Parameters.SCOPE_SYSTEM);
		  //String tableId="row";		  
		  //String tableId="userList";
		  //String sortedElement = "userId";
		  //String order = UserManagementController.DECENDING_ORDER;
		    //Phúc edit 30/5/2019
		    int pageNo = !StringUtils.isEmpty(request.getParameter("pageNo")) ? Integer.parseInt(request.getParameter("pageNo")) : 1;
			//int pageSize = request.getParameter("rp") != null ? Integer.parseInt(request.getParameter("rp")) : com.nec.asia.nic.framework.Constants.PAGE_SIZE_DEFAULT;
			int pageSize = Constants.PAGE_SIZE_DEFAULT;
			String sortedElement = request.getParameter("sortname") != null ? request.getParameter("sortname") : "userId";
			String order = request.getParameter("sortorder") != null ? request.getParameter("sortorder") : "asc";
			Order hibernateOrder = Order.desc(sortedElement);
			if ("asc".equalsIgnoreCase(order)) {
				hibernateOrder = Order.asc(sortedElement);
			}
			String nameUser = request.getParameter("nameUser") != null ? request.getParameter("nameUser") : "";
			String loginUser = request.getParameter("loginUser") != null ? request.getParameter("loginUser") : "";
			String siteName = request.getParameter("siteName") != null ? request.getParameter("siteName") : "";
			String stageUser = request.getParameter("stageUser") != null ? request.getParameter("stageUser") : "";
			
			Boolean flag = null;
			if(StringUtils.isNotEmpty(stageUser)){
				flag = stageUser.equals("Y") ? false : true;
			}
			
			model.addAttribute("nameUser", nameUser);
			model.addAttribute("loginUser", loginUser);
			model.addAttribute("siteName", siteName);
			model.addAttribute("stageUser", stageUser);
			
			String searchUserId = request.getParameter("query");
			logger.debug("Loading the user list by key word: queryAjax");
//			PaginatedResult<Users> users = userService.findAllForPagination(pageNo, pageSize, hibernateOrder);		
			//PaginatedResult<Users> users = userService.findAllForPagination(new Users(searchUserId), pageNo, pageSize, hibernateOrder);
			PaginatedResult<UserDto> users = userService.findAllForPaginationDto(new Users(searchUserId), nameUser, loginUser, siteName, flag, pageNo, pageSize, hibernateOrder);
			List<UserDto> userInfos = new ArrayList<UserDto>();
			if(users == null){
				users = new PaginatedResult<>(0, pageNo, new ArrayList<UserDto>());
//				for (Users userDBO : users.getRows()) {
//					UsersInfo usersInfo = new UsersInfo();
//		
//					try {
//						PropertyUtils.copyProperties(usersInfo, userDBO);
//						ADUser user = userService.findUserByUserId(usersInfo.getUserId());
//						//usersInfo.setFirstName(user.getFirstName() +" "+user.getLastName());
//						if (user != null) {
//							usersInfo.setFirstName(user.getFirstName() +" "+user.getLastName());
//							usersInfo.setUserName(user.getUserName());
//						}
//					} catch (Exception ex) {
//						logMessage(ex);
//					}
//					
//					usersInfo.setSiteGroupCode(userDBO.getSiteGroupCode());
//					usersInfo.setFmtCreateDate(DateUtil.parseDate(usersInfo.getCreateDate(), DateUtil.FORMAT_DD_MM_YYYY));	
//					usersInfo.setFmtUpdateDate(DateUtil.parseDate(usersInfo.getUpdateDate(), DateUtil.FORMAT_DD_MM_YYYY));	
//					userInfos.add(usersInfo);
//				}
			}else{
				userInfos = users.getRows();
			}
			//phúc edit
			int firstResults = (pageNo - 1) < 0 ? 0 : (pageNo - 1) * pageSize;
			model.addAttribute("pageSize", pageSize);
			model.addAttribute("pageNo", pageNo);
			model.addAttribute("totalPage", pagingUtil.totalPage(users.getTotal(), pageSize));
			model.addAttribute("startIndex", users.getTotal() != 0 ? firstResults + 1 : 0);
			model.addAttribute("totalRecord", users.getTotal());
			model.addAttribute("endIndex", firstResults + users.getRowSize());
			model.addAttribute("jobList", userInfos);
			//end		

			//model.addAttribute("jobList", userInfos);
		    //end
//		  
//		  try{
//			String sort = request.getParameter(new ParamEncoder(tableId).encodeParameterName(TableTagParameters.PARAMETER_SORT));  
//			if(StringUtils.isNotBlank(sort))
//				sortedElement = sort; 
//		  }catch(Exception ex){
//			 logMessage(ex);  
//		  }
//		  
//		  try{
//			String requestOrder = request.getParameter(new ParamEncoder(tableId).encodeParameterName(TableTagParameters.PARAMETER_ORDER));
//			if(StringUtils.isNotBlank(requestOrder))
//				order = requestOrder;
//		  }catch(Exception ex){
//			  logMessage(ex);  
//		  }
//		  
//		  Order hibernateOrder = Order.desc(sortedElement);
//		  if(order.equalsIgnoreCase(UserManagementController.DECENDING_ORDER)){
//			  hibernateOrder = Order.asc(sortedElement);
//		  }
//		  Parameters parameters = parametersService.findById(id);
//		  if(parameters!=null){
//				 String pageSizeDb = parameters.getParaShortValue();
//				 
//				 if(StringUtils.isNotBlank(pageSizeDb) && StringUtils.isNumeric(pageSizeDb)){
//					 pageSize = Integer.parseInt(pageSizeDb);
//				 }
//			  }
//		     
//		      
//		  try{
//			  String paraCurrentPage = request.getParameter((new ParamEncoder(tableId).encodeParameterName(TableTagParameters.PARAMETER_PAGE)));
//			  if (StringUtils.isNotBlank(paraCurrentPage)) {
//				  currentPage = Integer.valueOf(request.getParameter((new ParamEncoder(tableId).encodeParameterName(TableTagParameters.PARAMETER_PAGE))));
//			  }
//		  }catch(Exception ex){
//			  logMessage(ex);
//		  }
//		  
//		  
//		  PaginatedResult<Users> users = userService.findAllForPagination(currentPage, pageSize, hibernateOrder);
//		  List<UsersInfo> userInfos = new ArrayList<UsersInfo>();
//		  for(Users userDBO : users.getRows()){
//			  UsersInfo usersInfo = new UsersInfo();
//			  
//			  try{
//				  PropertyUtils.copyProperties(usersInfo, userDBO);
//				  User user = userService.findUserByUserId(usersInfo.getUserId());
//				  if(user!=null){
//					  usersInfo.setFirstName(user.getFirstName());
//					  usersInfo.setUserName(user.getUserName());
//				  }
//			  }catch(Exception ex){
//				  logMessage(ex);  
//			  }
//			  userInfos.add(usersInfo);
//		  }
		  
		  ModelAndView mav = new ModelAndView("security.usermanagement.userlist");
		
//		  request.setAttribute("users", users);
//		  request.setAttribute("userInfos", userInfos);
//		  request.setAttribute("pageSize",pageSize);
		  model.addAttribute("fnSelected", Constants.HEADING_NIC_USER_MANAGEMENT);
	      return mav;
	}

	
	@RequestMapping(value = "/query_ajax")
	@ResponseBody
	@ExceptionHandler
	public PaginatedResult<UsersInfo> queryAjax(WebRequest request) 
		throws Exception {

		int pageNo = request.getParameter("page") != null ? Integer.parseInt(request.getParameter("page")) : 1;
		int pageSize = request.getParameter("rp") != null ? Integer.parseInt(request.getParameter("rp")) : com.nec.asia.nic.framework.Constants.PAGE_SIZE_DEFAULT;
		
		String sortedElement = request.getParameter("sortname") != null ? request.getParameter("sortname") : "userId";
		String order = request.getParameter("sortorder") != null ? request.getParameter("sortorder") : "asc";
		Order hibernateOrder = Order.desc(sortedElement);
		if ("asc".equalsIgnoreCase(order)) {
			hibernateOrder = Order.asc(sortedElement);
		}
		
		String searchUserId = request.getParameter("query");
		logger.debug("Loading the user list by key word: queryAjax");
//		PaginatedResult<Users> users = userService.findAllForPagination(pageNo, pageSize, hibernateOrder);		
		PaginatedResult<Users> users = userService.findAllForPagination(new Users(searchUserId), pageNo, pageSize, hibernateOrder);
		List<UsersInfo> userInfos = new ArrayList<UsersInfo>();
		if(users.getRows() != null){
			for (Users userDBO : users.getRows()) {
				UsersInfo usersInfo = new UsersInfo();
	
				try {
					PropertyUtils.copyProperties(usersInfo, userDBO);
					ADUser user = userService.findUserByUserId(usersInfo.getUserId());
					//usersInfo.setFirstName(user.getFirstName() +" "+user.getLastName());
					if (user != null) {
						usersInfo.setFirstName(user.getFirstName() +" "+user.getLastName());
						usersInfo.setUserName(user.getUserName());
					}
				} catch (Exception ex) {
					logMessage(ex);
				}
				
				usersInfo.setFmtCreateDate(DateUtil.parseDate(usersInfo.getCreateDate(), DateUtil.FORMAT_DD_MM_YYYY));	
				usersInfo.setFmtUpdateDate(DateUtil.parseDate(usersInfo.getUpdateDate(), DateUtil.FORMAT_DD_MM_YYYY));	
				userInfos.add(usersInfo);
			}
		}
		
		PaginatedResult<UsersInfo> result = new PaginatedResult<UsersInfo>(users.getTotal(), users.getPage(), userInfos);
		logger.debug("Exit loading the user list by key word: queryAjax " + userInfos.size());
		return result;
	}
	
	@RequestMapping(value="/addEditUser")
	@ExceptionHandler
	public ModelAndView addUser1(HttpServletRequest request,Model model) throws Exception {
		
		return addEditUser(request, null,model);
	}


	@RequestMapping(value="/addEditUser/{userId}")
	@ExceptionHandler
	public ModelAndView addEditUser(HttpServletRequest request,@PathVariable String userId,Model model1) throws Exception {
		ADUser user =null;

		if (this.isAnAddAction(userId)) {
			this.initializeSearchAdFilters(request);
		}
		Map<String, String> positionMaps = new HashMap<String, String>();
		Map<String, String> departmentMaps = new HashMap<String, String>();
		if(StringUtils.isNotBlank(userId)){
			//[chris][20 Dec 2013] - start
			//user = securityService.findUserByUserName(userId);
			user = userService.findUserByUserId(userId);
			//[chris][20 Dec 2013] - end
			
			//check for invalid user entry either from db or url
			if(user == null){
				
				//String errors  = (String) request.getAttribute("Errors");
				//if(errors==null)
					//errors = "";
				String errors = ("Không tìm thấy người dùng: "+ userId);
				model1.addAttribute("Errors", errors);
				model1.addAttribute("fnSelected", Constants.HEADING_NIC_USER_MANAGEMENT);
				return listUser(request,model1);
			}

			try {
				request.setAttribute("isUserExistingInAd", this.userService.isUserExistedInAd(userId));
			} catch (Exception e) {
				//String errors = (String) request.getAttribute("Errors");
				//if (errors == null)
					//errors = "";
				String errors = ("Đã có lỗi khi kiểm tra người dùng: " + userId);
				model1.addAttribute("Errors", errors);
				model1.addAttribute("fnSelected", Constants.HEADING_NIC_USER_MANAGEMENT);
				return listUser(request, model1);
			}
			Users users = userService.findByUserId(userId);
			if(users != null){
				user.setPosition(users.getPosition());
				user.setDepartment(users.getDepartment());
				List<Department> departmentList = departmentService.findBySiteId(users.getSiteCode());
				if(departmentList != null){
					for(Department dp : departmentList){
						departmentMaps.put(dp.getCodeDepartment(), dp.getNameDepartment());
					}
				}
			}
			
			request.setAttribute("userEdit", user);
		}else{
			List<Department> departmentList = departmentService.findAllDepartment();
			if(departmentList != null){
				for(Department dp : departmentList){
					departmentMaps.put(dp.getCodeDepartment(), dp.getNameDepartment());
				}
			}
		}
		List<CodeValues> valueList = codesService.findAllByCodeId(Constants.CODE_ID_POSITION);
		
		if(valueList != null){
			for(CodeValues cv : valueList){
				positionMaps.put(cv.getId().getCodeValue(), cv.getCodeValueDesc());
			}
		}
		ModelAndView mav = new ModelAndView("security.usermanagement.addEditUser");		
		AddEditUserModel model = new AddEditUserModel();
		model.setPositionMaps(positionMaps);
		model.setDepartmentMaps(departmentMaps);
		mav.addObject("addEditUserModel", model);
		setRoleDisplay(request,user,mav);
		setSiteCode(request,user,mav);
		model1.addAttribute("fnSelected", Constants.HEADING_NIC_USER_MANAGEMENT);
		return mav;
	}
	
	@ResponseBody
	@RequestMapping(value="/loadDepartment/{siteId}", method=RequestMethod.GET)
	public List<MapData> loadDepartment(@PathVariable String siteId, HttpServletRequest request, ModelMap model){
		List<MapData> mapList = new ArrayList<MapData>();
		try {
			//mapList.add(new MapData("", "--- Chọn ---"));
			List<Department> list = departmentService.findBySiteId(siteId);
			if(list != null && list.size() > 0) {
				for(Department dp : list){
					MapData data = new MapData(dp.getCodeDepartment(), dp.getNameDepartment());
					mapList.add(data);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return mapList;
	}
	 
	private boolean isAnAddAction(String userId) {

		return StringUtils.isBlank(userId);
	}
 
	private void initializeSearchAdFilters(HttpServletRequest request) {

		request.getSession()
				.setAttribute(UserManagementController.USER_MANAGEMENT_CONTROLLER__AD_LIST__SEARCH__FIRST_NAME, null);
		request.getSession()
				.setAttribute(UserManagementController.USER_MANAGEMENT_CONTROLLER__AD_LIST__SEARCH__MIDDLE_NAME, null);
		request.getSession().setAttribute(UserManagementController.USER_MANAGEMENT_CONTROLLER__AD_LIST__SEARCH__SURNAME,
				null);
		request.getSession().setAttribute(UserManagementController.USER_MANAGEMENT_CONTROLLER__AD_LIST__SEARCH__USERID,
				null);
		request.getSession().setAttribute(UserManagementController.USER_MANAGEMENT_CONTROLLER__AD_LIST__SEARCH__IS_A_PROGRAMMATIC_CALL_TO_REFRESH_FLEXIGRID,
				null);
	}
	
	@RequestMapping(value="/registerFP/{userId}")
	@ExceptionHandler
	public ModelAndView registerFP(HttpServletRequest request,@PathVariable String userId,Model model1) throws Exception {
		ADUser user =null;
		if(StringUtils.isNotBlank(userId)){
			//[chris][20 Dec 2013] - start
			//user = securityService.findUserByUserName(userId);
			user = userService.findUserByUserId(userId);
			//[chris][20 Dec 2013] - end
			
			//check for invalid user entry either from db or url
			if(user == null){
				
				List<String> errors  = (List<String>) request.getAttribute("Errors");
				if(errors==null)
					errors = new LinkedList<String>();
				errors.add("Người dùng: "+ userId +" không hợp lệ, người dùng không tồn tại trong CSDL hoặc không tồn tại trong LDAP");
				request.setAttribute("Errors", errors);
				 model1.addAttribute("fnSelected", Constants.HEADING_NIC_USER_MANAGEMENT);
				return listUser(request,model1);
			}
			
			request.setAttribute("userEdit", user);
			
		}
		
		HttpSession session = request.getSession();
		UserSession userSession = (UserSession) session.getAttribute("userSession");
		//ModelAndView mav = new ModelAndView("security.usermanagement.addEditUser");
		ModelAndView mav = new ModelAndView("security.usermanagement.enrollFP");
		AddEditUserModel model = new AddEditUserModel();
		model.setEmail(userId);
		model.setBusinessId(userId);
		model.setUserId(userId);
		model.setUserName(userId);
		model.setFirstName(userSession.getFirstName());
		//userSession.getUserName()
		mav.addObject("addEditUserModel", model);
		setRoleDisplay(request,user,mav);
		setSiteCode(request,user,mav);
		model1.addAttribute("fnSelected", Constants.HEADING_NIC_USER_MANAGEMENT);
		return mav;
	}
	
	
	
	private void setRoleDisplay(HttpServletRequest request,ADUser user,ModelAndView modelAndView){
		List<Roles> roleList = roleService.findAll();
		List<String> unassignedRoles = null;
		List<String> assignedRoles = null;
		//[01 Feb 2016][Tue] - add
		Map<String, String > unassignedRoleMaps = null;
		Map<String, String > assignedRoleMaps = null;
		for(Roles role: roleList){
			if(user!=null){
				if(user.containsRole(role)){
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
		AddEditUserModel addEditUserModel = (AddEditUserModel) modelAndView.getModel().get("addEditUserModel");
		addEditUserModel.setUnassignedRoles(unassignedRoles);
		addEditUserModel.setAssignedRoles(assignedRoles);
		//[01 Feb 2016][Tue] - add
		addEditUserModel.setAssignedRoleMaps(assignedRoleMaps);
		addEditUserModel.setUnassignedRoleMaps(unassignedRoleMaps);
		
	}
	private void setSiteCode(HttpServletRequest request,ADUser user,ModelAndView modelAndView){

		try {
			List<SiteRepository> siteList = siteService.findAllSite();
			if (siteList != null) {
				Collections.sort(siteList, new SiteRepositoryComparator());

				Map<String, String> sitCodeMap = new LinkedHashMap<String, String>();
				for (SiteRepository site : siteList) {
					sitCodeMap.put(site.getSiteId(), site.getSiteName());
				}
				
				if(sitCodeMap!=null){			
					AddEditUserModel addEditUserModel = (AddEditUserModel) modelAndView.getModel().get("addEditUserModel");
					
					addEditUserModel.setSitCodeMap(sitCodeMap);
					if(user !=null){
						 addEditUserModel.setSiteCode(user.getSiteCode());
					}
				}
			}
		} catch (DaoException e) {
			logger.error(e.getMessage());
		}
	}
	

	@RequestMapping(value="/saveUserTest")
	@ExceptionHandler
	public ModelAndView addUser(HttpServletRequest request,@ModelAttribute("addEditUserModel") AddEditUserModel addEditUserModel,Model model) throws Exception{
		//30 Dec 2013 (chris lai) : update with audit record
		long startTimeMs = System.currentTimeMillis();
		Throwable throwable = null;
		String functionName = "Thêm mới người dùng ";
		
		try{
			if(userService.isUserExistedInAdAndDB(addEditUserModel.getUserName())){
				List<String> error = new LinkedList<String>();
				error.add(" Người dùng '" + addEditUserModel.getUserName()+"' đã tồn tại.");
				request.setAttribute("Errors", error);
				return listUser(request,model);
			}else{
				List<String> messages = new LinkedList<String>();
				ADUser user = new ADUser();
				BaseDTOMapper.copyProperties(user, addEditUserModel);
				/*add thông tin phòng ban + chức vụ*/
				user.setDepartment(addEditUserModel.getDepartment());
				user.setPosition(addEditUserModel.getPosition());
				/*------*/
				userService.createUser(user);
				messages.add("Thê mới người dùng : " + user.getUserId() + " thành công");
				functionName = "Thêm mới người dùng " + user.getUserId();
				request.setAttribute("messages", messages);
				model.addAttribute("fnSelected", Constants.HEADING_NIC_USER_MANAGEMENT);
				return listUser(request,model);
			}
		}catch(Exception ex){
			logger.error("Error occurred while adding the user. Exception:"+ex.getMessage());
			List<String> error = new LinkedList<String>();
			error.add("Xảy ra lỗi trong khi thêm Người dùng: "+addEditUserModel.getUserName());
			request.setAttribute("Errors", error);
			//30 Dec 2013 (chris lai) : update with audit record
			ex.printStackTrace();	
			throwable = ex;
			return listUser(request,model);
		}finally {
			//30 Dec 2013 (chris lai) : update with audit record
			try {
				
				Object[] args = { addEditUserModel };
				long timeMs = System.currentTimeMillis() - startTimeMs;
				auditAccessLogService.saveAuditAccessLogForWeb(request, functionName,args,throwable, timeMs);
			} catch(Exception exp){
			}
		}

	}
	
//	@InitBinder
//	public void initBinder(WebDataBinder binder){
//		binder.registerCustomEditor(List.class, "addEditUserModel.unassignedRoles", new CustomCollectionEditor(List.class));
//	}
	
	@RequestMapping(value="/saveUser")
	@ExceptionHandler
	public ModelAndView saveUser(HttpServletRequest request,@ModelAttribute("addEditUserModel") AddEditUserModel addEditUserModel,Model model) throws Exception{
		//30 Dec 2013 (chris lai) : update with audit record
		long startTimeMs = System.currentTimeMillis();
		Throwable throwable = null;
		String functionName = "userMgmtAdd";

		try{
			//[chris][20 Dec 2013] - start
			logger.info("[saveUser]userId:{}, mode:{}", addEditUserModel.getUserId(), addEditUserModel.getMode());
			
			//User user = securityService.findUserByUserName(addEditUserModel.getUserName());
			//User origUser = securityService.findUserByUserName(addEditUserModel.getUserName());
			
			ADUser user = userService.findUserByUserId(addEditUserModel.getUserId());
			ADUser origUser = userService.findUserByUserId(addEditUserModel.getUserId());
			//[chris][20 Dec 2013] - end
			if(origUser!=null && StringUtils.isNotBlank(addEditUserModel.getMode()) && addEditUserModel.getMode().equalsIgnoreCase("ADD")){
				//if(addEditUserModel.getUserName().equals(origUser.getUserName())) {
					logger.info("[saveUser]userId '{}' Already Exist.", addEditUserModel.getUserId());
					//List<String> error = new LinkedList<String>();
					String error = "";
					error = ("Mã người dùng '" + addEditUserModel.getUserId() +"' Đã tồn tại.");
					request.setAttribute("Errors", error);
					return listUser(request,model);
				//}
			}
		
			if (origUser == null) {
				user = new ADUser();
				ADD_EDIT_USER_MODEL_MAPPER.mapObject(addEditUserModel, user);
			} else {
				ADD_EDIT_USER_MODEL_MAPPER.mapObject(addEditUserModel, user, "");
				user.setDeleteFlag(origUser.isDeleteFlag());
				user.setSysAdminFlag(origUser.getSysAdminFlag()); 
			}
			user.setRoles(null);
			if (addEditUserModel.getAssignedRoles() != null
					&& addEditUserModel.getAssignedRoles().size() > 0) {
				for (String roleId : addEditUserModel.getAssignedRoles()) {
					Roles roles = roleService.findById(roleId);
					user.addRole(roles);
				}
			}
			HttpSession session = request.getSession();
			UserSession userSession = (UserSession) session.getAttribute("userSession");
			try {
				String messages = "";
				user.setUpdateDate(new Date());
				user.setUpdateBy(userSession.getUserName());
				user.setUpdateWkstnId(userSession.getWorkstationId());
				
				//Set siteGroup theo site code
				SiteRepository siteR = siteService.getSiteRepoById(addEditUserModel.getSiteCode());
				if(siteR != null){
					user.setSiteGroupCode(siteR.getSiteGroups().getGroupId());
					user.setSiteCode(addEditUserModel.getSiteCode());
				}
				/*add thông tin phòng ban + chức vụ*/
				user.setDepartment(addEditUserModel.getDepartment());
				user.setPosition(addEditUserModel.getPosition());
				user.setUserName(addEditUserModel.getSiteCode() + "_" + addEditUserModel.getFirstName() + " " + addEditUserModel.getLastName());
				user.setPassword(addEditUserModel.getPassword());
				/*------*/
				if (origUser == null) {
					user.setCreateDate(new Date());
					user.setCreateBy(userSession.getUserName());
					// [tuenv][12 Jan 2015] - start add
					//user.setUpdateWkstnId(userSession.getWorkstationId());
					user.setCreateWkstnId(userSession.getWorkstationId());
 					user.setSystemId(userSession.getSystemId());
					// [chris][20 Dec 2013] - start
					// securityService.createUser(user);
					userService.createUser(user);
					logger.info("[saveUser]createUser('{}').", user.getUserId());
					// [chris][20 Dec 2013] - end
					messages = ("Thêm mới người dùng : " + user.getUserId() + " thành công.");
					this.addObjToQueueJob(user.getUserId(), QUEUE_OBJ_TYPE_USER_ADD, user.getSiteCode());
				} else {
					// [chris][20 Dec 2013] - start
					// securityService.updateUser(user);
					userService.updateUser(user);
					logger.info("[saveUser]updateUser('{}').", user.getUserId());
					// [chris][20 Dec 2013] - end
					messages = ("Cập nhật người dùng : " + user.getUserId()+ " thành công.");
					//30 Dec 2013 (chris lai) : update with audit record
					functionName = "Cập nhật người dùng " + user.getUserId();
				}
				request.setAttribute("messages", messages);
			} catch (UserCRUDException e) {
				String error = "";
				error = ("Lỗi khi lưu người dùng : " + user.getUserName());
				logMessage(e);
				request.setAttribute("Errors", error);
				// addEditUser(request, addEditUserModel.getUserName());
				return listUser(request, model);
			} catch (ExistingActiveDirectoryException e) {
				String error = "";
				error = "Xảy ra lỗi trong khi lưu người dùng.";
				logMessage(e);
				request.setAttribute("Errors", error);
				// addEditUser(request, addEditUserModel.getUserName());
				return listUser(request, model);
			}
			String messages = "";
			//messages.add("Successfully Saved The User : " + user.getUserName()+ ".");
			messages = ("Lưu thành công người dùng: " + user.getUserId()+ ".");
			request.setAttribute("messages", messages);
		} catch (Exception ex) {
			logMessage(ex);
			String errors = "";
			errors = ("Xảy ra lỗi trong khi lưu người dùng: " + addEditUserModel.getUserName() + ".");
			request.setAttribute("Errors", errors);
			//30 Dec 2013 (chris lai) : update with audit record
			ex.printStackTrace();	
			throwable = ex;
		}finally {
			//30 Dec 2013 (chris lai) : update with audit record
			try {
				Object[] args = { addEditUserModel };
				long timeMs = System.currentTimeMillis() - startTimeMs;
				auditAccessLogService.saveAuditAccessLogForWeb(request, functionName,args,throwable, timeMs);
			} catch(Exception exp){
			}
		}
		
		model.addAttribute("fnSelected", Constants.HEADING_NIC_USER_MANAGEMENT);
		
		//14 Jun 2017 (chris) : use parameter to determine user FP enrolment.
		boolean enableEPID = false;
		try {
			ParametersId id = new ParametersId();
			id.setParaName("EPID_LOGIN_REQUIRED");
			id.setParaScope(Parameters.SCOPE_SYSTEM);
			Parameters parameter = parametersService.findById(id);
			if (parameter != null) {
				String epidFlag = parameter.getParaShortValue();
	
				if (StringUtils.isNotBlank(epidFlag)) {
					if (Boolean.TRUE.equals(epidFlag)) {
						enableEPID = true;
					}
				}
			}
		} catch (Exception e) {
			logMessage(e);
		}
		
		if (enableEPID)
			return registerFP(request,addEditUserModel.getUserId().toUpperCase(), model);
		else 
			return listUser(request, model);
		//14 Jun 2017 (chris) : use parameter to determine user FP enrolment - end.
		
		////return listUser(request, model);
		//return registerFP(request,addEditUserModel.getUserId().toUpperCase(), model);
	}
	
	@RequestMapping(value="/delete",method=RequestMethod.POST)
	@ExceptionHandler
	public ModelAndView deleteUser(HttpServletRequest request,Model model) throws Exception {
		//30 Dec 2013 (chris lai) : update with audit record
		long startTimeMs = System.currentTimeMillis();
		Throwable throwable = null;

		String deleteId = null;
		try {
			deleteId = request.getParameter("deleteId");
			// [chris][20 Dec 2013] - start
			//User user = securityService.findUserByUserName(deleteId);
			ADUser user = userService.findUserByUserId(deleteId);
			// [chris][20 Dec 2013] - end
			Users userDBO = userService.findById(deleteId);
			if (userDBO != null & user == null) {
				logger.info("[deleteUser] DB only ('{}').", deleteId);
				userService.delete(userDBO);
			}
			if (user != null) {
				logger.info("[deleteUser] DB & AD ('{}').", deleteId);
				// [chris][20 Dec 2013] - start
				//securityService.deleteUser(user);
				userService.deleteUser(deleteId);	
				// [chris][20 Dec 2013] - end
			}
			
			
			String messages = "";
			messages = ("Đã xóa người dùng: " + deleteId + " thành cồng.");
			model.addAttribute("messages", messages);

		} catch (Exception e) {
			logMessage(e);
			String error = "";
			error = ("Lỗi khi xóa người dùng : " + deleteId);
			model.addAttribute("Errors", error);
			logMessage(e);
			//30 Dec 2013 (chris lai) : update with audit record
			e.printStackTrace();	
			throwable = e;

		}finally {
			//30 Dec 2013 (chris lai) : update with audit record
			try {
				Object[] args = null;
				String functionName = "Xóa người dùng " + deleteId;
				long timeMs = System.currentTimeMillis() - startTimeMs;
				auditAccessLogService.saveAuditAccessLogForWeb(request, functionName,args,throwable, timeMs);
			} catch(Exception exp){
			}
		}

		model.addAttribute("fnSelected", Constants.HEADING_NIC_USER_MANAGEMENT);
		return listUser(request,model);
	}
	
	
	@RequestMapping(value="/changePass",method=RequestMethod.POST)
	@ExceptionHandler
	public ModelAndView changePasswordDisplay(HttpServletRequest request,Model model) throws Exception {
		String changePasswordId = request.getParameter("changePasswordId");
		// [chris][20 Dec 2013] - start
		//User user = securityService.findUserByUserName(changePasswordId);
		ADUser user = userService.findUserByUserId(changePasswordId);
		// [chris][20 Dec 2013] - end
		if(user == null){
			List<String> errors  = new LinkedList<String>();
			errors.add("Người dùng: "+ changePasswordId +" không hợp lệ, người dùng không tồn tại trong CSDL hoặc không tồn tại trong LDAP");
			request.setAttribute("Errors", errors);
			return listUser(request,model);
		}
		request.setAttribute("changePasswordId", changePasswordId);
		model.addAttribute("fnSelected", Constants.HEADING_NIC_USER_MANAGEMENT);
		return new ModelAndView("security.usermanagement.changePassword");
	}
	
	@RequestMapping(value="/storePassword",method=RequestMethod.POST)
	@ResponseBody
	@ExceptionHandler
	public String changePassword(HttpServletRequest request, ModelMap modelMap) throws Exception{
		String changePasswordId = request.getParameter("changePasswordId");
		String newPassword = request.getParameter("newPwd");
		String status ="success";
		// [chris][19 Aug 2014] - start
		String functionName = "Thay đổi mật khẩu người dùng ";
		Throwable throwable = null;
		long startTimeMs = System.currentTimeMillis();
		// [chris][19 Aug 2014] - end
		try {
			String userId = "";
			String workstationId = "";
			HttpSession session = request.getSession();
			UserSession userSession = (UserSession) session.getAttribute("userSession");
			if (userSession!=null) {
				userId = userSession.getUserName();
				workstationId = userSession.getWorkstationId();
				functionName = "Thay đổi mật khẩu người dùng " + userId;
			}
			// [chris][20 Dec 2013] - start
			//securityService.resetPassword(changePasswordId, newPassword);
			userService.resetPassword(changePasswordId, newPassword, userId, workstationId);
			// [chris][20 Dec 2013] - end
			status ="success";
			
			this.addObjToQueueJob(changePasswordId, QUEUE_OBJ_TYPE_USER_ADD, userService.findUserByUserId(changePasswordId).getUserId());
		} 
		catch (Exception e) {
			logMessage(e);
			status ="fail";
			throwable = e; // [chris][19 Aug 2014]
		}
		// [chris][19 Aug 2014] - start
		finally {
			try {
				Object[] args = { changePasswordId };
				long timeMs = System.currentTimeMillis() - startTimeMs;
				auditAccessLogService.saveAuditAccessLogForWeb(request, functionName, args, throwable, timeMs);
			} catch(Exception exp){
			}
		}
		// [chris][19 Aug 2014] - end
		modelMap.addAttribute("fnSelected", Constants.HEADING_NIC_USER_MANAGEMENT);
		
		return status;
	}
	
	@ResponseBody
	@RequestMapping(value = "/storeADSearchKeyToSession/{search_firstName}/{search_middleName}/{search_surName}/{search_userid}/{dummyToForceRefresh}", method = RequestMethod.GET)
	@ExceptionHandler
	public String storeADSearchKeyToSession(@PathVariable String search_firstName,
			@PathVariable String search_middleName, @PathVariable String search_surName,
			@PathVariable String search_userid, @PathVariable String dummyToForceRefresh,
			HttpServletRequest httpServletRequest, WebRequest request, ModelMap model) throws Exception {
		logger.info("storeADSearchKeyToSession()");

		try {
			httpServletRequest.getSession().setAttribute(
					UserManagementController.USER_MANAGEMENT_CONTROLLER__AD_LIST__SEARCH__FIRST_NAME,
					this.cleanInput(search_firstName));
			httpServletRequest.getSession().setAttribute(
					UserManagementController.USER_MANAGEMENT_CONTROLLER__AD_LIST__SEARCH__MIDDLE_NAME,
					this.cleanInput(search_middleName));
			httpServletRequest.getSession().setAttribute(
					UserManagementController.USER_MANAGEMENT_CONTROLLER__AD_LIST__SEARCH__SURNAME,
					this.cleanInput(search_surName));
			httpServletRequest.getSession().setAttribute(
					UserManagementController.USER_MANAGEMENT_CONTROLLER__AD_LIST__SEARCH__USERID,
					this.cleanInput(search_userid));
			httpServletRequest.getSession().setAttribute(
					UserManagementController.USER_MANAGEMENT_CONTROLLER__AD_LIST__SEARCH__IS_A_PROGRAMMATIC_CALL_TO_REFRESH_FLEXIGRID,
					true);

			return UserManagementController.SUCCESS;
		} catch (Exception e) {
			this.logMessage(e);
			return UserManagementController.ERROR;
		}
	}
	
	private String cleanInput(String input) {
		if (StringUtils.isBlank(input)) {
			return null;
		}

		if (input.equalsIgnoreCase(UserManagementController.EMPTY_INPUT)) {
			return null;
		}

		return input;
	}
	
	@ResponseBody
	@RequestMapping(value = "/existsInAd/{userId}/{dummyToForceRefresh}", method = RequestMethod.GET)
	@ExceptionHandler
	public String existsInAd(@PathVariable String userId,
			@PathVariable String dummyToForceRefresh, HttpServletRequest httpServletRequest, WebRequest request,
			ModelMap model) throws Exception {
		logger.info("existsInAd()");

		try {
			if (this.userService.isUserExistedInAd(userId)) {
				return UserManagementController.BOOLEAN_STRING_TRUE;
			} else {
				return UserManagementController.BOOLEAN_STRING_FALSE;
			}
		} catch (Exception e) {
			this.logMessage(e);
			return UserManagementController.ERROR;
		}
	} 
	
	@ResponseBody
	@RequestMapping(value = "/existsInDb/{userId}/{dummyToForceRefresh}", method = RequestMethod.GET)
	@ExceptionHandler
	public String existsInDb(@PathVariable String userId,
			@PathVariable String dummyToForceRefresh, HttpServletRequest httpServletRequest, WebRequest request,
			ModelMap model) throws Exception {
		logger.info("existsInDb()");

		try {
			if (this.userService.isUserExistedInDb(userId)) {
				return UserManagementController.BOOLEAN_STRING_TRUE;
			} else {
				return UserManagementController.BOOLEAN_STRING_FALSE;
			}
		} catch (Exception e) {
			this.logMessage(e);
			return UserManagementController.ERROR;
		}
	} 
 
	@RequestMapping(value = "/adList")
	@ResponseBody
	@ExceptionHandler
	public PaginatedResult<UserDTO> adList(HttpServletRequest httpServletRequest, WebRequest request) throws Exception {
		logger.info("adList()");

		String search_firstName = (String) httpServletRequest.getSession()
				.getAttribute(UserManagementController.USER_MANAGEMENT_CONTROLLER__AD_LIST__SEARCH__FIRST_NAME);
		String search_middleName = (String) httpServletRequest.getSession()
				.getAttribute(UserManagementController.USER_MANAGEMENT_CONTROLLER__AD_LIST__SEARCH__MIDDLE_NAME);
		String search_surName = (String) httpServletRequest.getSession()
				.getAttribute(UserManagementController.USER_MANAGEMENT_CONTROLLER__AD_LIST__SEARCH__SURNAME);
		String search_userid = (String) httpServletRequest.getSession()
				.getAttribute(UserManagementController.USER_MANAGEMENT_CONTROLLER__AD_LIST__SEARCH__USERID);
		Boolean search_isAProgrammaticCallToRefreshFlexigrid = (Boolean) httpServletRequest.getSession().getAttribute(
				UserManagementController.USER_MANAGEMENT_CONTROLLER__AD_LIST__SEARCH__IS_A_PROGRAMMATIC_CALL_TO_REFRESH_FLEXIGRID);

		logger.info("loading the user list: search_firstName                        [" + search_firstName + "]");
		logger.info("loading the user list: search_middleName                       [" + search_middleName + "]");
		logger.info("loading the user list: search_surName             			    [" + search_surName + "]");
		logger.info("loading the user list: search_userid                           [" + search_userid + "]");
		logger.info("loading the user list: search_searchKeysFreshlySetAndUnusedYet ["
				+ search_isAProgrammaticCallToRefreshFlexigrid + "]");

		int pageNo = 0;
		if (search_isAProgrammaticCallToRefreshFlexigrid != null && search_isAProgrammaticCallToRefreshFlexigrid) {
			httpServletRequest.getSession().setAttribute(
					UserManagementController.USER_MANAGEMENT_CONTROLLER__AD_LIST__SEARCH__IS_A_PROGRAMMATIC_CALL_TO_REFRESH_FLEXIGRID,
					false);
			pageNo = 1;
		} else {
			pageNo = request.getParameter("page") != null ? Integer.parseInt(request.getParameter("page")) : 1;
		}

		int pageSize = request.getParameter("rp") != null ? Integer.parseInt(request.getParameter("rp"))
				: com.nec.asia.nic.framework.Constants.PAGE_SIZE_DEFAULT;

		PaginatedResult<UserDTO> users = this.userService.findAdUsersNotInDb(search_firstName, search_middleName,
				search_surName, search_userid, pageNo, pageSize);

		logger.info("Exit loading the user list: users.getPage() " + users.getPage());
		logger.info("Exit loading the user list: users.getPage() " + users.getTotal());

		return users;
	}
	 
	private void logMessage(Exception ex) {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		PrintStream ps = new PrintStream(baos);
		ex.printStackTrace(ps);
		logger.error(ex.getMessage() + " : \n" + new String(baos.toByteArray()));
	}
	
	//Tạo bản ghi hàng đợi trả Account về trung tâm.
	private Boolean addObjToQueueJob(String transactionId,
			String ObjType, String receiver)throws Exception {
		try {
			SyncQueueJob queue = new SyncQueueJob();
			queue.setCreatedTs(Calendar.getInstance().getTime());
			queue.setObjectId(transactionId);
			queue.setObjectType(ObjType);
			queue.setReceiver(receiver);
			queue.setTranStatus(null);
			queue.setStatus("PENDING");
			queue.setSyncRetry(1);
			boolean check = queueJobService
					.saveOrUpdateQueue(queue).getModel();
			return check;
			// }
		} catch (Exception e) {
			throw new Exception(
					CreateMessageException.createMessageException(e)
							+ " - addObjToQueueJob:" + transactionId
							+ " - thất bại.");
		}
	}
}
