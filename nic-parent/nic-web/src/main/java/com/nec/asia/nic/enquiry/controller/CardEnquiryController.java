/**
 * 
 */
package com.nec.asia.nic.enquiry.controller;

import java.util.List;
import java.util.ArrayList;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.WebRequest;
import com.nec.asia.nic.comp.trans.domain.NicIssuanceData;
import com.nec.asia.nic.comp.trans.domain.NicTransaction;
import com.nec.asia.nic.comp.trans.dto.NicIssuanceDataDto;
import com.nec.asia.nic.comp.trans.dto.NicTransactionDto;
import com.nec.asia.nic.comp.trans.service.NicTransactionService;
import com.nec.asia.nic.comp.trans.service.IssuanceDataService;
import com.nec.asia.nic.framework.admin.code.domain.CodeValues;
import com.nec.asia.nic.framework.admin.code.service.CodesService;
import com.nec.asia.nic.framework.PageRequest;
import com.nec.asia.nic.framework.PaginatedResult;
import com.nec.asia.nic.framework.controller.AbstractController;

/**
 * @author franco_conte
 * @Company: NEC Asia Pacific Ltd
 * @Since: Sep 2, 2013
 */

/* 
 * Modification History:
 *  
 * 20 Apr 2017 (chris): remove this controller.
 * 
 */
//@Controller
//@RequestMapping(value = "/cardEnquiry")
public class CardEnquiryController extends AbstractController {

	private static final Logger logger = LoggerFactory.getLogger(CardEnquiryController.class);

	//@Autowired
	//private NicTransactionService nicTransactionService;
	
	@Autowired
	private IssuanceDataService issuanceDataService;
	
	@Autowired
	private CodesService codesService;
	

	@RequestMapping(value = "/init")
	@ExceptionHandler
	public String txnEnquiryInit(WebRequest request, ModelMap model) throws Exception{
		try {
			model.addAttribute("nicIssuance", new NicIssuanceData());
			return "show.cardEnquiry";
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@ResponseBody
	@RequestMapping(value = "/search", method = RequestMethod.POST)
	@ExceptionHandler
	public PaginatedResult<NicIssuanceData> nicIssuanceDataSearch(WebRequest request, ModelMap model, NicIssuanceData nicIssuanceData) throws Exception {
		PaginatedResult<NicIssuanceData> pr = null;
		try {

			String pages = request.getParameter("page");
			String sortname = request.getParameter("sortname");
			String sortorder = request.getParameter("sortorder");
			String rp = request.getParameter("rp");
			String recordPerPage = request.getParameter("rp");

			if (!StringUtils.isNotBlank(recordPerPage)) {
				recordPerPage = "50";
			}

			int pageSize = 20;
			if (rp == null || "NaN".equals(rp)) {
				// pageSize =
				// Integer.parseInt(configurationService.getUserAdminConsoleJobQueueMaxRecordsPerPage(getUserSession(request).getUserId()).getSettings());
			} else {
				pageSize = Integer.parseInt(rp);
			}

			int startIndex = 0;
			String pageNumber = pages;
			if (StringUtils.isNotBlank(pageNumber)) {
				startIndex = (Integer.parseInt(pageNumber) - 1) * pageSize;
			}
			PageRequest pageRequest = new PageRequest();
			pageRequest.setCalculateRecordCount(true);
			pageRequest.setFirstRowIndex(startIndex);
			pageRequest.setMaxRecords(pageSize);
			pageRequest.setSortorder(sortorder);
			pageRequest.setSortname(sortname);
			pageRequest.setPageNo(Integer.parseInt(pageNumber));

			//pr = nicTransactionService.findAllForPagination(pageRequest, nicIssuanceData);
			//move from nicTransactionService to issuanceDataService
			NicIssuanceDataDto nicIssuanceDataDto = getDto(nicIssuanceData);
			PaginatedResult<NicIssuanceDataDto> prDto = issuanceDataService.findAllForPagination(pageRequest, nicIssuanceDataDto);
			pr = this.getDbo(prDto);
			return pr;
		} catch (Exception e) {

			e.printStackTrace();
			return null;
		}
	}

	private NicIssuanceDataDto getDto(NicIssuanceData dbo) {
		NicIssuanceDataDto dto = new NicIssuanceDataDto();
		BeanUtils.copyProperties(dbo, dto);
		if(dbo.getNicTransaction() != null) {
			NicTransactionDto nicTransaction = new NicTransactionDto();
			BeanUtils.copyProperties(dbo.getNicTransaction(), nicTransaction);
			dto.setNicTransactionDto(nicTransaction);
		}
		return dto;
	}
	
	private NicIssuanceData getDbo(NicIssuanceDataDto dto) {
		NicIssuanceData dbo = new NicIssuanceData();
		BeanUtils.copyProperties(dto, dbo);
		if(dto.getNicTransactionDto() != null) {
			NicTransaction nicTransaction = new NicTransaction();
			BeanUtils.copyProperties(dto.getNicTransactionDto(), nicTransaction);
			dbo.setNicTransaction(nicTransaction);
		}
		return dbo;
	}
	
	private PaginatedResult<NicIssuanceData> getDbo(PaginatedResult<NicIssuanceDataDto> prDto) {
		PaginatedResult<NicIssuanceData> prDbo = new PaginatedResult();
		prDbo.setTotal(prDto.getTotal());
		prDbo.setPage(prDto.getPage());
		List<NicIssuanceData> rows = new ArrayList<NicIssuanceData>();
		for (NicIssuanceDataDto dto : prDto.getRows()) {
			NicIssuanceData dbo = this.getDbo(dto);
			rows.add(dbo);
		}
		prDbo.setRows(rows);
		return prDbo;
	}
	
	private String getCodeValueDesc(String codeId, String codeVal) {
		String desc = "UNKNOWN";
		try {
			CodeValues code = codesService.getCodeValueByIdName(codeId, codeVal);
			if(code != null) {
				desc = code.getCodeValueDesc();
			}
		} catch (Exception e) {
		}
		return desc;
	}


	
}
