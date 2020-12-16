package com.nec.asia.nic.listLogWs.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang.StringUtils;
import org.hibernate.criterion.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.ModelAndView;

import com.nec.asia.nic.comp.trans.dto.NicEnquiryForm;
import com.nec.asia.nic.comp.ws.log.domain.EppWsLog;
import com.nec.asia.nic.comp.ws.log.service.EppWsLogService;
import com.nec.asia.nic.enquiry.controller.AuditEnquiryController;
import com.nec.asia.nic.enquiry.controller.AuditEnquiryInfo;
import com.nec.asia.nic.enquiry.controller.NicUploadJobInfo;
import com.nec.asia.nic.framework.PaginatedResult;
import com.nec.asia.nic.framework.admin.audit.domain.AuditAccessLogs;
import com.nec.asia.nic.framework.controller.AbstractController;
import com.nec.asia.nic.util.Constants;
import com.nec.asia.nic.util.pagingUtil;
import com.nec.asia.nic.utils.DateUtil;
import com.nec.asia.nic.web.session.UserSession;

@Controller
@RequestMapping(value = "/listLogWs")
public class ListLogWsController extends AbstractController {
	@Autowired
	EppWsLogService eppWsLogService;

	@RequestMapping(value = "/init")
	@ExceptionHandler
	public ModelAndView nicEnquiryJobList(WebRequest request,
			HttpServletRequest httpServletRequest, ModelMap model)
			throws Exception {
		// logger.info("Transaction Enquiry Page");
		// logger.info("NIC Audit Enquiry Page");
		// EppWsLog
		model.addAttribute("fnSelected", Constants.HEADING_NIC_LIST_LOGWS);
		ModelAndView mav = new ModelAndView("eppWsLog.list");
		ListLogWsFrm listLogWsFrm = new ListLogWsFrm();
		model.addAttribute("pageSize", 10);
		model.addAttribute("pageNo", 1);
		model.addAttribute("totalPage", 1);
		model.addAttribute("startIndex", 0);
		model.addAttribute("totalRecord", 0);
		model.addAttribute("endIndex", 0);
		// listLogWsFrm=new ListLogWsFrm();

		mav.addObject("listLogWsFrm", listLogWsFrm);
		return mav;

	}

	@RequestMapping(value = "/logWsSearchList", method = RequestMethod.POST)
	@ExceptionHandler
	public ModelAndView auditEnquirySearchList(WebRequest request,
			HttpServletRequest httpServletRequest,
			@ModelAttribute("listLogWsFrm") ListLogWsFrm listLogWsFrm,
			ModelMap model) throws Exception {
		ModelAndView mav = new ModelAndView("eppWsLog.list");
		String pageNo = httpServletRequest.getParameter("pageNo");
		List<EppWsLog> list = new ArrayList<EppWsLog>();
		PaginatedResult<EppWsLog> pag = new PaginatedResult<EppWsLog>();
		int pageSize = 10;
		String keyId = listLogWsFrm.getKeyId();// ma dinh danh
		String urlRequest = listLogWsFrm.getUrlRequest();
		String logWsDateFrom = listLogWsFrm.getLogWsDateFrom();
		String logWsDateTo = listLogWsFrm.getLogWsDateTo();
		String type = listLogWsFrm.getType();
		
		model.addAttribute("fnSelected", Constants.HEADING_NIC_LIST_LOGWS);
		if (pageNo.equals("")) {
			model.addAttribute("pageSize", pageSize);
			model.addAttribute("pageNo", 1);
			model.addAttribute("startIndex", 1);
			pag = eppWsLogService.findAllForPagination(1, 10);
			pag = eppWsLogService.findAllForPagination(keyId, urlRequest,
					logWsDateFrom, logWsDateTo, type, 1, 10);
			list = pag.getRows();
			model.addAttribute("list", list);
			model.addAttribute("endIndex", list.size());
			model.addAttribute("totalRecord", pag.getTotal());
			model.addAttribute("totalPage",
					pagingUtil.totalPage(pag.getTotal(), pageSize));
		} else {
			int startIndex = 0;
			int page = Integer.parseInt(pageNo);
			model.addAttribute("pageSize", pageSize);
			model.addAttribute("pageNo", page);
			// pag = eppWsLogService.findAllForPagination((page), pageSize);
			pag = eppWsLogService.findAllForPagination(keyId, urlRequest,
					logWsDateFrom, logWsDateTo, type, page, pageSize);
			model.addAttribute("totalRecord", pag.getTotal());
			model.addAttribute("totalPage",
					pagingUtil.totalPage(pag.getTotal(), pageSize));
			list = pag.getRows();
			model.addAttribute("list", list);
			if (page == 1)
				startIndex = 1;
			else
				startIndex = (page - 1) * pageSize;
			model.addAttribute("startIndex", startIndex);
			model.addAttribute("endIndex", (page - 1) * pageSize + list.size());
		}
		mav.addObject("listLogWsFrm", listLogWsFrm);
		return mav;
	}
	@RequestMapping(value="/getLogWs/{id}",method=RequestMethod.GET)
	@ResponseBody
	public EppWsLog getLogWsById(@PathVariable("id") long id){
		System.out.println(id);
		EppWsLog epp = eppWsLogService.getWsLogById(id);
		System.out.println(epp.getUrlRequest());
		return epp;
	}
}
