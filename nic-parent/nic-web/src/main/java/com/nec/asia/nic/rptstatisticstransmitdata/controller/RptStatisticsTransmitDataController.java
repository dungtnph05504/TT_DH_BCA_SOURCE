package com.nec.asia.nic.rptstatisticstransmitdata.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.ModelAndView;

import com.nec.asia.nic.comp.trans.domain.RptStatisticsTransmitData;
import com.nec.asia.nic.comp.trans.service.RptStatisticsTransmitDataService;
import com.nec.asia.nic.framework.PaginatedResult;
import com.nec.asia.nic.framework.admin.site.dao.SiteRepositoryDao;
import com.nec.asia.nic.util.pagingUtil;
import com.nec.asia.nic.utils.HelperClass;

@Controller
@RequestMapping(value = "/listRptStatisticsTransmitData")
public class RptStatisticsTransmitDataController {

	@Autowired
	private RptStatisticsTransmitDataService rptDataService;

	@Autowired
	private SiteRepositoryDao siteResDao;

	@RequestMapping(value = "/viewListRptData")
	@ExceptionHandler
	public ModelAndView auditEnquirySearchList(WebRequest request,
			HttpServletRequest httpServletRequest,
			@ModelAttribute("rptSearchTime") RptSearchTime rptSearchTime,
			ModelMap model) throws Exception {
		ModelAndView mav = new ModelAndView("rptData.list");
		String pageNo = httpServletRequest.getParameter("pageNo");
		List<RptStatisticsTransmitData> listRptSTData = new ArrayList<RptStatisticsTransmitData>();
		List<RptData> listRptData = new ArrayList<RptData>();
		List<String> listType = rptDataService.getAllType();
		int pageSize = 10;
		int page = 1;
		int totalRecord = 1;
		int totalPage = 1;
		int startIndex = 0;
		if (StringUtils.isBlank(pageNo)) {
			page = 1;
		} else {
			page = Integer.parseInt(pageNo);
		}

		PaginatedResult<RptStatisticsTransmitData> pagSearch = rptDataService
				.findAllForPagination(rptSearchTime.getTimeFrom(),
						rptSearchTime.getTimeTo(), rptSearchTime.getType(),
						page, pageSize);

		listRptSTData = pagSearch.getRows();

		for (int i = 0; i < listRptSTData.size(); i++) {
			RptData rData = new RptData();
			RptStatisticsTransmitData rptSTD = listRptSTData.get(i);
			rData.setFunction(rptSTD.getType());
			rData.setRptDate(HelperClass.convertDateToString2(rptSTD
					.getRptDate()));
			rData.setSiteCode(rptSTD.getSiteCode());
			rData.setTotal(rptSTD.getTotal());
			rData.setUpdateDatetime(HelperClass.convertDateToString1(rptSTD
					.getUpdateDatetime()));
			listRptData.add(rData);
		}

		totalRecord = pagSearch.getTotal();
		totalPage = pagingUtil.totalPage(pagSearch.getTotal(), pageSize);
		
		if (listRptData != null && listRptData.size() > 0) {
			startIndex = (page - 1) * pageSize + 1;
		}
		
		model.addAttribute("listType", listType);
		model.addAttribute("pageSize", pageSize);
		model.addAttribute("listRptData", listRptData);
		model.addAttribute("totalRecord", totalRecord);
		model.addAttribute("totalPage", totalPage);
		model.addAttribute("pageNo", page);
		model.addAttribute("startIndex", startIndex);
		model.addAttribute("endIndex",
				(page - 1) * pageSize + listRptData.size());
		mav.addObject("rptSearchTime", rptSearchTime);
		return mav;
	}
}
