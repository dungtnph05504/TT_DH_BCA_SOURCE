package com.nec.asia.nic.investigation.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.ModelAndView;

import com.nec.asia.nic.comp.trans.dao.impl.DateFormat;
import com.nec.asia.nic.comp.trans.domain.BorderGate;
import com.nec.asia.nic.comp.trans.dto.BorderGateDto;
import com.nec.asia.nic.comp.trans.domain.EppAirline;
import com.nec.asia.nic.comp.trans.dto.AirlineDto;
import com.nec.asia.nic.comp.trans.service.AirlineService;
import com.nec.asia.nic.comp.trans.service.BorderGateService;
import com.nec.asia.nic.framework.PaginatedResult;
import com.nec.asia.nic.framework.admin.code.domain.CodeValues;
import com.nec.asia.nic.util.Constants;
import com.nec.asia.nic.util.pagingUtil;
import com.nec.asia.nic.web.session.UserSession;


@Controller
@RequestMapping("/borderGate")
public class InvestigationBordergateController {

	
	//test
	@Autowired
	private AirlineService airlineService;
	
	@Autowired
	private BorderGateService borderGateService;
	
	private static final Logger logger = LoggerFactory.getLogger(InvestigationBordergateController.class);
	
	public static final String CODE_BORDERGATE1 = "Cảng sân bay";
	public static final String CODE_BORDERGATE2 = "Cảng hàng không";
	public static final String CODE_BORDERGATE3 = "Cảng biển";
	
	
	@RequestMapping(value="/showBorderGate")
	public ModelAndView showBorderGate(@ModelAttribute(value = "formData") InvestigationAssignmentData inv, WebRequest request, HttpServletRequest httpRequest, ModelMap model){
		int mesg = !StringUtils.isEmpty(request.getParameter("result")) ? Integer.parseInt(request.getParameter("result")) : -2;
		int pageNo = !StringUtils.isEmpty(request.getParameter("pageNo")) ? Integer.parseInt(request.getParameter("pageNo")) : 1;
		model.addAttribute("mesg", mesg);
		ModelAndView mav = new ModelAndView("show.bordergate");
		int pageSize = Constants.DEFAULT_PAGE_MAX_SIZE;
		PaginatedResult<com.nec.asia.nic.comp.trans.dto.BorderGateDto> pr = borderGateService.findAllBorderGate(inv.getCodeBordergate(),  pageNo, pageSize);
		List<BorderGateDto> list = new ArrayList<BorderGateDto>();
		if(pr == null){
			pr = new PaginatedResult<>(0, pageNo, new ArrayList<BorderGateDto>());
		}else{
			list = pr.getRows();
		}
		int firstResults = (pageNo - 1) < 0 ? 0 : (pageNo - 1) * pageSize;
		model.addAttribute("pageSize", pageSize);
		model.addAttribute("pageNo", pageNo);
		model.addAttribute("totalPage", pagingUtil.totalPage(pr.getTotal(), pageSize));
		model.addAttribute("startIndex", pr.getTotal() != 0 ? firstResults + 1 : 0);
		model.addAttribute("totalRecord", pr.getTotal());
		model.addAttribute("endIndex", firstResults + pr.getRowSize());
		model.addAttribute("listBorderGate", list);
		return mav;
		
	}
	
	
	@ResponseBody
	@RequestMapping(value="/neweditBorderGate")
	public ModelAndView saveAirline(HttpServletRequest request, ModelMap model){
		ModelAndView mav = new ModelAndView("add.edit.borderGate");
		try {	
			BorderGate epp = new BorderGate();
			model.addAttribute("dto", epp);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return mav;
	}
	
	@ResponseBody
	@RequestMapping(value="/editBorderGate")
	public ModelAndView editAirline(@RequestParam String value, ModelMap model){
		ModelAndView mav = new ModelAndView("add.edit.borderGate");
		try {
			BorderGate epp = borderGateService.findBorderGateByCode(value);
			if(epp == null){
				epp = new BorderGate();
			}
			model.addAttribute("dto", epp);
		} catch (Exception e) {
			// TODO: handle exception
		}
		return mav;
	}
	
	@ResponseBody
	@RequestMapping(value="/saveChangeBorderGate")
	public Integer saveBorderGate(@RequestBody String yourArray, ModelMap model, HttpServletRequest httpRequest){
		JSONObject onb = new JSONObject(yourArray);
		HttpSession session = httpRequest.getSession();
		UserSession userSession = (UserSession) session.getAttribute("userSession");
		Boolean check = true;
		try {
			String codeBordergate = (onb.has("codeBordergate") && !onb.isNull("codeBordergate")) ? onb.getString("codeBordergate") : "";
			String name = (onb.has("name") && !onb.isNull("name")) ? onb.getString("name") : "";
			String StringborderGateKind = (onb.has("borderGateKind") && !onb.isNull("borderGateKind")) ? onb.getString("borderGateKind") : "";
			String desc = (onb.has("description") && !onb.isNull("description")) ? onb.getString("description") : "";
			String idItem = (onb.has("id") && !onb.isNull("id")) ? onb.getString("id") : "";
			Long id = null;
			if(StringUtils.isNotEmpty(idItem)){
				id = Long.parseLong(idItem);
			}
			Long borderGateKind = Long.parseLong(StringborderGateKind);
			BorderGate epp =  borderGateService.findBorderGateByCode(codeBordergate);
			if((id == null && epp != null) || (id != null && epp != null &&  id.intValue() != epp.getId().intValue() )){
				check = false;
			}
			if(!check)
				return -1;
			
			BorderGate borderGate = new BorderGate();
			if (id != null) {
				BorderGate gate =  borderGateService.findBorderGatebyId(id);
				borderGate.setId(id);
				borderGate.setBorderGateKind(borderGateKind);
				borderGate.setCode(codeBordergate);
				borderGate.setDescription(desc);
				borderGate.setLastModifiedBy( userSession.getUserName());
				borderGate.setLastModifiedDate( new Date());
				borderGate.setCreateBy(gate.getCreateBy());
				borderGate.setCreateDate(gate.getCreateDate());
				borderGate.setName(name);
			}else {
				borderGate  = new BorderGate(id, userSession.getUserName(), userSession.getUserName(), new Date(), new Date(), codeBordergate, name, borderGateKind, desc, "");
			}
			
			borderGateService.saveOrUpdateBorderGate(borderGate);
			return 1;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}
	
	@RequestMapping(value="/deleteBorderGate/{idBG}")
	public ModelAndView deleteAirline(@ModelAttribute(value = "formData") InvestigationAssignmentData inv, @PathVariable Long idBG, WebRequest request, HttpServletRequest httpRequest, ModelMap model){
		try {
			Boolean result = borderGateService.deleteBorderGate(idBG);
			model.addAttribute("result", 0);
			if(result){
				model.addAttribute("result", 1);
			}
		} catch (Exception e) {
			logger.error("error delete airline, err === " + e.getMessage());
		}
		ModelAndView mav = new ModelAndView("show.bordergate");
		int pageSize = Constants.DEFAULT_PAGE_MAX_SIZE;
		int pageNo = !StringUtils.isEmpty(request.getParameter("pageNo")) ? Integer.parseInt(request.getParameter("pageNo")) : 1;
		PaginatedResult<BorderGateDto> pr = borderGateService.findAllBorderGate(inv.getCodeBordergate(),  pageNo, pageSize);
		List<BorderGateDto> list = new ArrayList<BorderGateDto>();
		if(pr == null){
			pr = new PaginatedResult<>(0, pageNo, new ArrayList<BorderGateDto>());
		}else{
			list = pr.getRows();
		}
		int firstResults = (pageNo - 1) < 0 ? 0 : (pageNo - 1) * pageSize;
		model.addAttribute("pageSize", pageSize);
		model.addAttribute("pageNo", pageNo);
		model.addAttribute("totalPage", pagingUtil.totalPage(pr.getTotal(), pageSize));
		model.addAttribute("startIndex", pr.getTotal() != 0 ? firstResults + 1 : 0);
		model.addAttribute("totalRecord", pr.getTotal());
		model.addAttribute("endIndex", firstResults + pr.getRowSize());
		model.addAttribute("listBorderGate", list);
		return mav;
	}
}

