/**
 * 
 */
package com.nec.asia.nic.enquiry.controller;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.ModelAndView;

import com.nec.asia.nic.framework.PaginatedResult;
import com.nec.asia.nic.framework.admin.audit.domain.AuditSessionLogs;
import com.nec.asia.nic.framework.controller.AbstractController;
import com.nec.asia.nic.framework.report.service.AuditSessionLogService;
import com.nec.asia.nic.util.Constants;
import com.nec.asia.nic.util.pagingUtil;
import com.nec.asia.nic.utils.DateUtil;

/**
 * @author sailaja_chinapothula
 * @Company: NEC Asia Pacific Ltd
 * @Since: Jun 18, 2013
 */

@Controller
@RequestMapping(value = "/userSessionEnquiry")
public class UserSessionEnquiryController extends AbstractController{
	
	private static final Logger logger = LoggerFactory.getLogger(EnquiryController.class);
	
	@Autowired
	private AuditSessionLogService auditSessionLogService;
	
	private static String DECENDING_ORDER="desc";
	
	@RequestMapping(value="/userSessionEnquirySearch")
	@ExceptionHandler
	public ModelAndView auditEnquiryAccessLog(WebRequest request, ModelMap model) throws Exception{
		logger.info("NIC User Session Enquiry Page");
		AuditEnquiryInfo auditEnquiryInfo = new AuditEnquiryInfo();
		ModelAndView mav = new ModelAndView("user-session-enquiry");
		model.addAttribute("fnSelected", Constants.HEADING_NIC_USER_SESSION_ENQUIRY);
		//model.addAttribute("jobList", new ArrayList<AuditEnquiryInfo>());
		//phúc edit		
		model.addAttribute("pageSize", 10);
		model.addAttribute("pageNo", 1);
		model.addAttribute("totalPage", 1);
		model.addAttribute("startIndex", 0);
		model.addAttribute("totalRecord", 0);
		model.addAttribute("endIndex", 0);
		model.addAttribute("jobList", new ArrayList<AuditEnquiryInfo>());
		//end
		mav.addObject("userSessionEnquiryForm", auditEnquiryInfo);
		return mav;
	}
	

	@RequestMapping(value = "/userSessEnquirySearchList", method = RequestMethod.POST)
	@ExceptionHandler
	public ModelAndView auditEnquirySearchList(WebRequest request, @ModelAttribute("userSessionEnquiryForm") AuditEnquiryInfo userSessionEnquiryForm, ModelMap model) throws Exception{
		logger.info("Nic User Session Enquiry Search List");
		String userId = userSessionEnquiryForm.getUserId();
		String wkstnId = userSessionEnquiryForm.getWkstnId();
		String logindateFrom = userSessionEnquiryForm.getLoginDateFrom();
		String loginDateTo = userSessionEnquiryForm.getLoginDateTo();
		String logoutDateFrom = userSessionEnquiryForm.getLogoutDateFrom();
		String logoutDateTo = userSessionEnquiryForm.getLogoutDateTo();
		PaginatedResult<AuditEnquiryInfo> auditEnquiryInfoResult = new PaginatedResult<AuditEnquiryInfo>();
		List<AuditEnquiryInfo> auditEnquiryInfoList = new ArrayList<AuditEnquiryInfo>();
		logger.info("The userId entered by user==========="+userId);
		logger.info("The wkstnId entered by user==========="+wkstnId);
		logger.info("The Function Name entered by user==========="+logindateFrom);
		logger.info("The Login Date entered by user==========="+loginDateTo);
		logger.info("The Logout Date entered by user==========="+logoutDateFrom);
		logger.info("The status entered by user==========="+logoutDateTo);
		try {

			String pages = !StringUtils.isEmpty(request.getParameter("pageNo")) ? request.getParameter("pageNo") : "1";
			String rp = request.getParameter("rp");
			String sortedElement = "sessionId";
			if(sortedElement.equalsIgnoreCase("auditDateStr")){
				sortedElement = "auditDate";
			}
			String sortorder = request.getParameter("sortorder") != null ? request.getParameter("sortorder") : "desc";
			String recordPerPage = request.getParameter("rp");
			int currentPage = 1;
			if (!StringUtils.isNotBlank(recordPerPage)) {
				recordPerPage = "50";
			}

			int pageSize = 20;
			if (rp == null || "NaN".equals(rp)) {
				pageSize = 20;
			} else {
				pageSize = Integer.parseInt(rp);
			}
			
			Order hibernateOrder = Order.desc(sortedElement);
			  if(!sortorder.equalsIgnoreCase(UserSessionEnquiryController.DECENDING_ORDER)){
				  hibernateOrder = Order.asc(sortedElement);
			  }
			try{
				  
				  currentPage = Integer.valueOf(pages);
				  
			  }catch(Exception ex){
				  logMessage(ex);
			  }
			pageSize = Constants.PAGE_SIZE_DEFAULT;
			PaginatedResult<AuditSessionLogs> pr = auditSessionLogService.getUserSessionLogList(
					userId, wkstnId, logindateFrom, loginDateTo, logoutDateFrom, logoutDateTo, pageSize, hibernateOrder, currentPage);
			
			if(pr!=null){
				for(AuditSessionLogs accessLog : pr.getRows()){
					AuditEnquiryInfo info = new AuditEnquiryInfo();
					PropertyUtils.copyProperties(info, accessLog);
					if(accessLog.getLoginDate()!=null){
						info.setLoginDateFrom(DateUtil.parseDate(accessLog.getLoginDate(),"dd-MMM-yyyy HH:mm" ));
					}
					
					if(accessLog.getLogoutDate()!=null){
						info.setLogoutDateTo(DateUtil.parseDate(accessLog.getLogoutDate(),"dd-MMM-yyyy HH:mm" ));
					}
					
					auditEnquiryInfoList.add(info);
					
				}
				auditEnquiryInfoResult.setPage(pr.getPage());
				auditEnquiryInfoResult.setRows(auditEnquiryInfoList);
				auditEnquiryInfoResult.setTotal(pr.getTotal());
			}
			//phúc edit
			int firstResults = (currentPage - 1) < 0 ? 0 : (currentPage - 1) * pageSize;
			model.addAttribute("pageSize", pageSize);
			model.addAttribute("pageNo", currentPage);
			model.addAttribute("totalPage", pagingUtil.totalPage(pr.getTotal(), pageSize));
			model.addAttribute("startIndex", pr.getTotal() != 0 ? firstResults + 1 : 0);
			model.addAttribute("totalRecord", pr.getTotal());
			model.addAttribute("endIndex", firstResults + pr.getRowSize());
			model.addAttribute("jobList", auditEnquiryInfoList);
			//end
			} catch (Exception e) {
				e.printStackTrace();
			}
			ModelAndView mav = new ModelAndView("user-session-enquiry");
			mav.addObject("userSessionEnquiryForm", userSessionEnquiryForm);
			return mav;
	}
	
	private void logMessage(Exception ex) {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		  PrintStream ps = new PrintStream(baos);
		  ex.printStackTrace(ps);
		  logger.error(ex.getMessage()+" : \n" + new String(baos.toByteArray()));
	}
}