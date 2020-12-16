package com.nec.asia.nic.listlogcheckconnection.controller;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.swing.plaf.basic.BasicInternalFrameTitlePane.MoveAction;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.ModelAndView;

import com.nec.asia.nic.comp.trans.domain.LogCheckConnection;
import com.nec.asia.nic.comp.trans.service.LogCheckConnectionService;
import com.nec.asia.nic.comp.ws.log.domain.EppWsLog;
import com.nec.asia.nic.framework.PaginatedResult;
import com.nec.asia.nic.framework.admin.code.domain.ParametersId;
import com.nec.asia.nic.framework.admin.code.service.ParametersService;
import com.nec.asia.nic.listLogWs.controller.ListLogWsFrm;
import com.nec.asia.nic.util.Constants;
import com.nec.asia.nic.util.pagingUtil;

@Controller
@RequestMapping(value = "/listLogCheckConnection")
public class ListLogCheckConnectionController {
	@Autowired
	private LogCheckConnectionService logCheckConnectionService;

	@Autowired
	private ParametersService parametersService;

	@RequestMapping(value = "/viewList")
	@ExceptionHandler
	public ModelAndView auditEnquirySearchList(WebRequest request,
			HttpServletRequest httpServletRequest,
			@ModelAttribute("site") Site site,
			ModelMap model) throws Exception {
		ModelAndView mav = new ModelAndView("logCheckConnection.list");
		String pageNo = httpServletRequest.getParameter("pageNo");
		String pageNoL = httpServletRequest.getParameter("pageNoL");
		List<LogCheckConnection> list = new ArrayList<LogCheckConnection>();
		list = logCheckConnectionService.findAllAndOrder("siteName");
		List<LogCheckConnection> listConnectWasLost = new ArrayList<LogCheckConnection>();
		List<Site> listSite = new ArrayList<Site>();
		LogCheckConnection log;
		for (int i = 0; i <= list.size() - 1; i++) {
			log = list.get(list.size() - 1 - i);
			listSite.add(new Site(log.getSiteName(), log.getSiteCode()));
		}
		if (!StringUtils.isBlank(site.getSiteName())) {
			list.clear();
			list.add(logCheckConnectionService.findLogBySiteCode(site.getSiteName()).getModel());
		}
		int pageSize = 10;
		int page = 1;
		int totalRecord = 1;
		int totalPage = 1;
		if (StringUtils.isBlank(pageNo)) {
			page = 1;
		} else {
			page = Integer.parseInt(pageNo);
		}
		PaginatedResult<LogCheckConnection> pag = new PaginatedResult<LogCheckConnection>();
		if (list.size() != 1) {
			pag = logCheckConnectionService.findAllForPagination(page, pageSize);
			list.clear();
			list = pag.getRows();
			totalRecord = pag.getTotal();
			totalPage = pagingUtil.totalPage(pag.getTotal(), pageSize);
		}
		model.addAttribute("siteList", listSite);
		model.addAttribute("pageSize", pageSize);
		
		model.addAttribute("listLogCheckConnection", list);
		model.addAttribute("totalRecord", totalRecord);
		model.addAttribute("totalPage", totalPage);
		model.addAttribute("pageNo", page);
		model.addAttribute("startIndex", (page - 1) * pageSize + 1);
		model.addAttribute("endIndex", (page - 1) * pageSize + list.size());
		
		//set table lost connect
		int pageL = 1;
		int totalPageL = 1;
		int startIndexLost = 0;
		int endIndexLost = 0;
		if (StringUtils.isBlank(pageNoL)) {
			pageL = 1;
		} else {
			pageL = Integer.parseInt(pageNoL);
		}
		listConnectWasLost = logCheckConnectionService.findAllAndOrder("lastConnectedDatetime");
		Date date = new Date();
		
		//parametersId("SYSTEM", "LAST_CONNECTION_TIMEOUT") = null => default = 60min.
		int timeout = 60;
		try {
			timeout = Integer.parseInt(parametersService.findById(new ParametersId("SYSTEM", "LAST_CONNECTION_TIMEOUT")).getParaShortValue());
		} catch (Exception e) {
			// TODO: handle exception
		}
		long check = date.getTime() - timeout*60*1000;
		List<LogCheckConnection> listRs = new ArrayList<LogCheckConnection>();
		for (LogCheckConnection logCheck : listConnectWasLost) {
			if (logCheck.getLastConnectedDatetime().getTime() <= check) {
				listRs.add(logCheck);
			}
		}
		if(listRs.size() > 0){
			startIndexLost = (pageL - 1) * pageSize + 1;
			endIndexLost = listRs.size();
		}
		if(listRs.size() > pageSize){
			int coutP = 0;
			if (listRs.size()%pageSize == 0) {
				totalPageL = listRs.size()/pageSize;
			} else {
				totalPageL = listRs.size()/pageSize + 1;
			}
			if(pageL < totalPageL){
				coutP = 10;
			}else {
				coutP = listRs.size()%pageSize;
			}
			endIndexLost = (pageL - 1) * pageSize + coutP;
		}
		model.addAttribute("totalPageL", totalPageL);
		if (listRs != null && listRs.size() > 0) {
			model.addAttribute("listLogCheckConnectionLost", listRs.subList(startIndexLost - 1, endIndexLost));
		}else{
			model.addAttribute("listLogCheckConnectionLost", listRs);
		}
		model.addAttribute("totalRecordLost", listRs.size());
		model.addAttribute("pageNoL", pageL);
		model.addAttribute("startIndexLost", startIndexLost);
		model.addAttribute("endIndexLost", endIndexLost);
		
		mav.addObject("site", site);
		return mav;
	}	
}
