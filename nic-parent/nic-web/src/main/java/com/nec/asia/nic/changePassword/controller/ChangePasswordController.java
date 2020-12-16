package com.nec.asia.nic.changePassword.controller;


import java.util.LinkedList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.ModelAndView;

import com.nec.asia.nic.framework.admin.rbac.service.UserService;
import com.nec.asia.nic.framework.controller.AbstractController;
import com.nec.asia.nic.framework.report.service.AuditAccessLogService;
import com.nec.asia.nic.framework.security.ldap.exceptions.ChangePasswordException;
import com.nec.asia.nic.framework.security.ldap.exceptions.IncorrectPasswordException;
import com.nec.asia.nic.framework.security.ldap.exceptions.UserNotFoundException;
import com.nec.asia.nic.framework.security.service.SecurityService;
import com.nec.asia.nic.framework.security.service.ADUser;
import com.nec.asia.nic.util.Constants;
import com.nec.asia.nic.web.session.UserSession;

/**

 * @author Peddi Swapna.
 * @Company: NEC ASIA PACIFIC PTE
 * @since : October 20, 2013
 */

/*
 * Modification History:
 * 
 * 01 Jul 2014 jp : added username and workstation id in changepassword
 * 19 Aug 2014 chris : add change password audit access log.
 */


@Controller(value="changePasswordController")
@RequestMapping(value="/changePasswordController")
public class ChangePasswordController  extends AbstractController {


	/*@Autowired
	@Qualifier(value="securityService")
	private SecurityService securityService;*/
	
	@Autowired
	@Qualifier(value="userService")
	private UserService userService;
	
	@Autowired
	private AuditAccessLogService auditAccessLogService;
	
	public ChangePasswordController() {
		// TODO Auto-generated constructor stub
	}
	
	@RequestMapping(value="/index")
	@ExceptionHandler
	public ModelAndView changePasswordDisplay(HttpServletRequest request, ModelMap model) throws Exception,RuntimeException{
		HttpSession session = request.getSession();
		UserSession userSession = (UserSession) session.getAttribute("userSession");
		
		String changePasswordId = userSession.getUserName();
//		String changePasswordId = "nic";
		ADUser user = userService.findUserByUserId(changePasswordId);
		if(user == null){
			List<String> errors  = new LinkedList<String>();
			errors.add("Invalid userid: "+ changePasswordId +" either the userid does not exist on the database or it does not exist on the Active Directory");
			request.setAttribute("Errors", errors);
		}
		request.setAttribute("changePasswordId", changePasswordId);
		
		ModelAndView mav = new ModelAndView("changePassword.initPage");
		model.addAttribute("fnSelected", Constants.HEADING_NIC_CHANGE_PASSWORD);
		return mav;
	}
	
	@RequestMapping(value="/storePassword",method=RequestMethod.POST)
	@ResponseBody
	@ExceptionHandler
	public String changePassword(HttpServletRequest request, ModelMap modelMap) throws Exception{
		String changePasswordId = request.getParameter("changePasswordId");
		String newPassword = request.getParameter("newPwd");
		String currentPwd = request.getParameter("currentPwd");
		String status = "success";
		// [chris][19 Aug 2014] - start
		String functionName = "Thay đổi mật khẩu";
		Throwable throwable = null;
		long startTimeMs = System.currentTimeMillis();
		// [chris][19 Aug 2014] - end
		
		//01 JUL 2014 - added
		HttpSession session = request.getSession();
		UserSession userSession = (UserSession) session.getAttribute("userSession");
		String userName = userSession.getUserName();
		String wkstnId = userSession.getWorkstationId();

		try {
//			if(userService.authenticate(changePasswordId, currentPwd)){
//				userService.changePassword(changePasswordId, currentPwd , newPassword);
				userService.changePassword(changePasswordId, currentPwd , newPassword, userName, wkstnId);
				status = "success";
//			}else{
//				status ="incorrectPwd";
//			}
		} catch (ChangePasswordException e) {
			status = "fail";
			throwable = e; // [chris][19 Aug 2014]
		} catch (UserNotFoundException e) {
			status = "fail";
			throwable = e; // [chris][19 Aug 2014]
		} catch (IncorrectPasswordException e) {
			status = "incorrectPwd";
			throwable = e; // [chris][19 Aug 2014]
		} 
		catch (Exception e) {
			status = "fail";
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
		modelMap.addAttribute("fnSelected", Constants.HEADING_NIC_CHANGE_PASSWORD);
		return status;
	}
	
}
