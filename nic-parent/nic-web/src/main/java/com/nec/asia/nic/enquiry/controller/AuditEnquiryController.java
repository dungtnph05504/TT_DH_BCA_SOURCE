/**
 * 
 */
package com.nec.asia.nic.enquiry.controller;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.commons.beanutils.PropertyUtils;
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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.ModelAndView;

import com.nec.asia.nic.comp.trans.dto.NicEnquiryForm;
import com.nec.asia.nic.comp.trans.service.exception.NicUploadJobServiceException;
import com.nec.asia.nic.framework.PageRequest;
import com.nec.asia.nic.framework.PaginatedResult;
import com.nec.asia.nic.framework.admin.audit.domain.AuditAccessLogs;
import com.nec.asia.nic.framework.admin.code.domain.CodeValues;
import com.nec.asia.nic.framework.admin.code.service.CodesService;
import com.nec.asia.nic.framework.controller.AbstractController;
import com.nec.asia.nic.framework.report.service.AuditAccessLogService;
import com.nec.asia.nic.security.usermanagement.UserManagementController;
import com.nec.asia.nic.util.CodeValueDescComparator;
import com.nec.asia.nic.util.Constants;
import com.nec.asia.nic.util.pagingUtil;
import com.nec.asia.nic.utils.DateUtil;

/**
 * @author sailaja_chinapothula
 * @Company: NEC Asia Pacific Ltd
 * @Since: Jun 18, 2013
 */

@Controller
@RequestMapping(value = "/nicAuditEnquiry")
public class AuditEnquiryController extends AbstractController{
	
	private static final Logger logger = LoggerFactory.getLogger(EnquiryController.class);
	
	@Autowired
	private AuditAccessLogService auditAccessLogService;
	
	private static String DECENDING_ORDER="desc";
	
	@Autowired
	//@Qualifier(value="codesService")
	private CodesService codesService;
	
	@RequestMapping(value="/auditEnquiryAccessLog")
	@ExceptionHandler
	public ModelAndView auditEnquiryAccessLog(WebRequest request, ModelMap model) throws Exception{
		logger.info("NIC Audit Enquiry Page");
		NicEnquiryForm nicEnqForm = new NicEnquiryForm();
		ModelAndView mav = new ModelAndView("audit-enquiry-searchList");
		model.addAttribute("fnSelected", Constants.HEADING_NIC_AUDIT_ENQUIRY);
		//phúc edit
		model.addAttribute("pageSize", 10);
		model.addAttribute("pageNo", 1);
		model.addAttribute("totalPage", 1);
		model.addAttribute("startIndex", 0);
		model.addAttribute("totalRecord", 0);
		model.addAttribute("endIndex", 0);
		model.addAttribute("jobList", new ArrayList<AuditEnquiryInfo>());
		//end
		mav.addObject("nicEnqForm", nicEnqForm);
		return mav;
	}
	
	@RequestMapping(value = "/auditSearchList", method = RequestMethod.POST)
	@ExceptionHandler
	public ModelAndView auditEnquirySearchList(WebRequest request, @ModelAttribute("nicEnqForm") NicEnquiryForm nicEnqForm, ModelMap model) throws Exception{
		logger.info("Nic Audit Enquiry Access Log Search List");
		String userId = nicEnqForm.getUserId();
		String wkstnId = nicEnqForm.getWkstnId();
		String functionName = nicEnqForm.getFunctionName();
		String dateFrom = nicEnqForm.getAuditDateFrom();
		String dateTo = nicEnqForm.getAuditDateTo();
		String status = nicEnqForm.getErrorFlag();
		PaginatedResult<AuditEnquiryInfo> auditEnquiryInfoResult = new PaginatedResult<AuditEnquiryInfo>();
		List<AuditEnquiryInfo> auditEnquiryInfoList = new ArrayList<AuditEnquiryInfo>();
//		logger.info("The userId entered by user==========="+userId);
//		logger.info("The wkstnId entered by user==========="+wkstnId);
//		logger.info("The Function Name entered by user==========="+functionName);
//		logger.info("The Login Date entered by user==========="+dateFrom);
//		logger.info("The Logout Date entered by user==========="+dateTo);
//		logger.info("The status entered by user==========="+status);
		try {

			String pages = !StringUtils.isEmpty(request.getParameter("pageNo")) ? request.getParameter("pageNo") : "1";
			String rp = request.getParameter("rp");
			String sortedElement = request.getParameter("sortname") != null ? request.getParameter("sortname") : "auditDate";
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
			  if(!sortorder.equalsIgnoreCase(AuditEnquiryController.DECENDING_ORDER)){
				  hibernateOrder = Order.asc(sortedElement);
			  }
			try{
				  
				  currentPage = Integer.valueOf(pages);
				  
			  }catch(Exception ex){
				  logMessage(ex);
			  }
			pageSize = Constants.PAGE_SIZE_DEFAULT;
			PaginatedResult<AuditAccessLogs> pr = auditAccessLogService.getAuditAccessLogList(
					userId, wkstnId, functionName, dateFrom, dateTo, status, pageSize, hibernateOrder, currentPage);
			
			if(pr!=null){
				for(AuditAccessLogs accessLog : pr.getRows()){
					AuditEnquiryInfo info = new AuditEnquiryInfo();
					PropertyUtils.copyProperties(info, accessLog);
					if(accessLog.getAuditDate()!=null){
						info.setAuditDateStr(DateUtil.parseDate(accessLog.getAuditDate(),"dd-MMM-yyyy HH:mm" ));
						
					}
					if(!StringUtils.isEmpty(info.getErrorFlag()) && info.getErrorFlag().equals("N")){
						info.setErrorFlag("<span style=\"color: green;\">Thành công</span>");
					}else{
						info.setErrorFlag("<span style=\"color: red;\">Thất bại</span>");
					}
					auditEnquiryInfoList.add(info);
					
				}
				
			}
			auditEnquiryInfoResult.setPage(pr.getPage());
			auditEnquiryInfoResult.setRows(auditEnquiryInfoList);
			auditEnquiryInfoResult.setTotal(pr.getTotal());
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
			ModelAndView mav = new ModelAndView("audit-enquiry-searchList");
			mav.addObject("nicEnqForm", nicEnqForm);
			return mav;
	}
	

	private void logMessage(Exception ex) {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		  PrintStream ps = new PrintStream(baos);
		  ex.printStackTrace(ps);
		  logger.error(ex.getMessage()+" : \n" + new String(baos.toByteArray()));
	}
}