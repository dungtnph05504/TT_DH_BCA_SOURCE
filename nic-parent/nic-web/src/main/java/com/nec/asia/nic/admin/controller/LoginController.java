package com.nec.asia.nic.admin.controller;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.ModelAndView;

import com.nec.asia.nic.admin.domain.User;
import com.nec.asia.nic.framework.admin.audit.Auditable;
import com.nec.asia.nic.framework.admin.audit.Auditable.Severity;
import com.nec.asia.nic.framework.admin.audit.domain.AuditSessionLogs;
import com.nec.asia.nic.framework.admin.code.domain.Parameters;
import com.nec.asia.nic.framework.admin.code.domain.ParametersId;
import com.nec.asia.nic.framework.admin.code.service.ParametersService;
import com.nec.asia.nic.framework.admin.rbac.domain.Users;
import com.nec.asia.nic.framework.admin.rbac.service.UserService;
import com.nec.asia.nic.framework.report.service.AuditSessionLogService;
import com.nec.asia.nic.framework.security.ldap.exceptions.IncorrectPasswordException;
import com.nec.asia.nic.framework.security.service.AuthenticationService;
import com.nec.asia.nic.util.Constants;
import com.nec.asia.nic.web.session.UserSession;

/**
 * login controller
 * 
 * @author bright_zheng
 */

/*
 * Modification History:
 * 
 * 14 Jun 2017 (chris): set login mode to password authentication if EPID_LOGIN_REQUIRED is not enabled
 */

@Controller(value = "LoginController")
@RequestMapping(value = "/user")
public class LoginController {
    private static final Logger logger = LoggerFactory.getLogger(LoginController.class);

    @Autowired
    @Qualifier("authenticationService")
    private AuthenticationService authenticationService;

    @Autowired
    @Qualifier("auditSessionLogService")
    private AuditSessionLogService auditSessionLogService;
    
    @Autowired
    @Qualifier("userService")
	private UserService userService;
    
    @Autowired
	private ParametersService parametersService;

    @Auditable(severity = Severity.IMPORTANT, implClass = "defaultServiceAuditService")
    @RequestMapping(value = "/login")
    @ExceptionHandler
    public ModelAndView login(HttpServletRequest request, @ModelAttribute("user") User user, BindingResult result, Model model) throws Exception {
        logger.debug("Start login");
        //String redirect = (String) request.getParameter("redirect");
       
        String redirect = "";
        if (user != null) {
            redirect = user.getRedirect();
        }
        if (StringUtils.isNotEmpty(redirect)) {
            request.setAttribute("redirect", redirect);
        } else {        	
            request.setAttribute("redirect", "1");           
            model.addAttribute("fnSelected", Constants.HEADING_NIC_MAIN);
            return new ModelAndView("global.view.login");
        }

        logger.info("before authenticationService.login(" + user.getAccount() + "," + user.getWorkstationId() + ")");
        UserSession userSession = null; 
        try {
			userSession = authenticationService.login(user.getAccount(), user.getPassword(), user.getWorkstationId());
		} catch (IncorrectPasswordException e) {
			List<String> errors = new LinkedList<String>();
	        errors.add("Mật khẩu không được để trống");
	        request.setAttribute("Errors", errors);
	        model.addAttribute("fnSelected", Constants.HEADING_NIC_MAIN);
	        return new ModelAndView("global.view.login");
		}
		logger.info("after authenticationService.login() : " + (userSession != null));
        if (userSession != null) {
        	
            HttpSession session = request.getSession(true);
            session.setAttribute("userSession", userSession);
            AuditSessionLogs auditSessionLog = new AuditSessionLogs();
            auditSessionLog.setSessionId(request.getRequestedSessionId());
            auditSessionLog.setUserId(userSession.getUserName());
            auditSessionLog.setWkstnId(userSession.getWorkstationId());
            auditSessionLog.setLoginDate(new Date());
            auditSessionLogService.save(auditSessionLog);
            if (CollectionUtils.isNotEmpty(auditSessionLogService.findAll(auditSessionLog))) {
                auditSessionLog = auditSessionLogService.findAll(auditSessionLog).get(0);
            }
           
            session.setAttribute("auditSessionLog", auditSessionLog);
            model.addAttribute("fnSelected", Constants.HEADING_NIC_WELCOME);
            return new ModelAndView("global.view.welcome");
        }

        List<String> errors = new LinkedList<String>();
        errors.add("Sai Tên đăng nhập hoặc Mật khẩu");
        request.setAttribute("Errors", errors);
        model.addAttribute("fnSelected", Constants.HEADING_NIC_MAIN);
        return new ModelAndView("global.view.login");
    }

    @RequestMapping(value = "/logout")
    @ExceptionHandler
    public ModelAndView logout(HttpServletRequest request, Model model) throws Exception {
        HttpSession session = request.getSession();
        UserSession userSession = (UserSession) session.getAttribute("userSession");

        request.setAttribute("userId", userSession.getUserName());
        request.setAttribute("workstationID", userSession.getWorkstationId());

        AuditSessionLogs auditSessionLog = (AuditSessionLogs) session.getAttribute("auditSessionLog");
        if (auditSessionLog != null) {
            auditSessionLog.setLogoutDate(new Date());
            auditSessionLog.setTimeTaken(auditSessionLog.getLogoutDate().getTime() - auditSessionLog.getLoginDate().getTime());
            auditSessionLogService.saveOrUpdate(auditSessionLog);
        }
        session.invalidate();
        model.addAttribute("fnSelected", Constants.HEADING_NIC_LOGOUT);
        return new ModelAndView("global.view.logout");
    } 

    @RequestMapping(value = "/logoutWithSessionTimeout")
    @ExceptionHandler
    public ModelAndView logoutWithSessionTimeout(HttpServletRequest request, Model model) throws Exception {
        HttpSession session = request.getSession();
        UserSession userSession = (UserSession) session.getAttribute("userSession");

        request.setAttribute("userId", userSession.getUserName());
        request.setAttribute("workstationID", userSession.getWorkstationId());

        AuditSessionLogs auditSessionLog = (AuditSessionLogs) session.getAttribute("auditSessionLog");
        auditSessionLog.setLogoutDate(new Date());
        auditSessionLog.setTimeTaken(auditSessionLog.getLogoutDate().getTime() - auditSessionLog.getLoginDate().getTime());
        auditSessionLogService.saveOrUpdate(auditSessionLog);
        session.invalidate();
        model.addAttribute("fnSelected", Constants.HEADING_NIC_LOGOUT);
        return new ModelAndView("global.view.logoutWithSessionTimeout");
    }

    @RequestMapping(value = "/home")
    @ExceptionHandler
    public ModelAndView homePage(HttpServletRequest request, Model model) throws Exception {
        ModelAndView mav = new ModelAndView("global.view.welcome");
        return mav;
    }

    @RequestMapping(value = "/accessDenied")
    @ExceptionHandler
    public ModelAndView accessDenied(WebRequest request, Model model) throws Exception {

        return new ModelAndView("global.view.accessDenied");
    }

    @RequestMapping(value = "/pingServer")
    public @ResponseBody String pingServer() {
        return "OK!! Keeping You Logged In.";
    }
    
    
   /** 
    * 
    * @param request
    * @return
    * @throws Exception
    */    
    @RequestMapping(value = "/loginChk")
    @ResponseBody	
	public String loginCheck(@RequestParam(value = "userId") String userId,	HttpServletRequest request) throws Exception {
		// String userId = request.getParameter("userId");
    	logger.debug("[loginCheck] ========== {} ", userId);
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
			logger.debug("unable to retrieve parameter: {}", e.getMessage());
		}
    	
		Users dbUser = this.userService.findById(userId);
		String status = null;
		if (dbUser!=null && dbUser.isSysAdminFlag()) {
			status = "Y";
		} else if (dbUser!=null && !enableEPID) {
			logger.trace("password authentication enabled: {} ", userId);
			status = "Y";
		} else {
			status = "N";
		}
		
		return status;
	}
}
