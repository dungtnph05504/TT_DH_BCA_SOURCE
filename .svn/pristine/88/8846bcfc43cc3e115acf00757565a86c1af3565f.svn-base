package com.nec.asia.nic.statistical.controller;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.ModelAndView;

import com.nec.asia.nic.comp.trans.domain.StatusticalIfor;
import com.nec.asia.nic.comp.trans.domain.SyncQueueJob;
import com.nec.asia.nic.comp.trans.domain.statusticalLogWs;
import com.nec.asia.nic.comp.trans.service.SyncQueueJobService;
import com.nec.asia.nic.framework.PaginatedResult;
import com.nec.asia.nic.framework.admin.site.domain.SiteGroups;
import com.nec.asia.nic.framework.admin.site.domain.SiteRepository;
import com.nec.asia.nic.framework.admin.site.service.SiteService;
import com.nec.asia.nic.util.pagingUtil;

@Controller
@RequestMapping("/statustical")
public class StatusticalController {
	@Autowired
	private SyncQueueJobService queueJobService;
	
	@Autowired
	private SiteService siteService;
	
	@RequestMapping("/init")
	@ExceptionHandler
	public ModelAndView getSatustical(WebRequest request,HttpServletRequest httpservletRequest,ModelMap model) throws Exception{
		
		ModelAndView mav=new ModelAndView("thongkehangdoi");
		StatusticalForm staForm=new StatusticalForm();
		List<SiteGroups> list =new ArrayList<>();
		List<SiteRepository> listRepository=new ArrayList<>();
		list = siteService.findAllSiteGroubs();
		listRepository  = siteService.findAllSite();
		Map<String,String> map=new HashMap<String,String>();
		Map<String,String> map1=new HashMap<String,String>();
		for(SiteGroups s:list){
			map.put(s.getGroupId(), s.getGroupName());
		}
		for(SiteRepository st : listRepository){
			map1.put(st.getSiteId(), st.getSiteName());
		}
		Date date =new Date();
		SimpleDateFormat dateFormat =new SimpleDateFormat("dd-MM-yyyy");
		String dateStr=dateFormat.format(date);
		
		SimpleDateFormat dateFormat1 =new SimpleDateFormat("yyyyMMdd");
		String dateSearch=dateFormat1.format(date);
		model.addAttribute("map",map);
		model.addAttribute("map1",map1);
		model.addAttribute("dateStr",dateStr);
		mav.addObject("staForm",staForm);
		//get list status condition date today
		List<StatusticalIfor> listStatus=queueJobService.findStatusByDate(dateSearch);	
		model.addAttribute("listStatus",listStatus);
		return mav;
	}
	@RequestMapping("/searchStatustical")
	@ExceptionHandler
	public ModelAndView getStatusticalBySearch(WebRequest request,HttpServletRequest httpservletRequest,ModelMap model,@ModelAttribute("staForm") StatusticalForm sta) throws Exception{
		ModelAndView mav=new ModelAndView("thongkehangdoi");
		List<SiteGroups> list =new ArrayList<>();
		List<SiteRepository> listRepository=new ArrayList<>();
		list = siteService.findAllSiteGroubs();
		listRepository  = siteService.findAllSite();
		Map<String,String> map=new HashMap<String,String>();
		Map<String,String> map1=new HashMap<String,String>();
		for(SiteGroups s:list){
			map.put(s.getGroupId(), s.getGroupName());
		}
		for(SiteRepository st : listRepository){
			map1.put(st.getSiteId(), st.getSiteName());
		}
		model.addAttribute("map",map);
		model.addAttribute("map1",map1);
		String loaiHangDoi = sta.getLoaiHangDoi();
		String dateFrom= sta.getDateFrom();
		String dateTo=sta.getDateTo();
		String receiver= sta.getReceiver();

		List<StatusticalIfor> listStatus=queueJobService.findStatusAll(loaiHangDoi, dateFrom, dateTo, receiver);
		model.addAttribute("listStatus",listStatus);
        model.addAttribute("dateStr","");
		mav.addObject("staForm",sta);
		return mav;
	}
	@RequestMapping("/searchStatusticalWsLog")
	@ResponseBody
	@ExceptionHandler
	public PaginatedResult<SyncQueueJob> getListStatusticalLogWs(ModelMap model,HttpServletRequest request){
		PaginatedResult<SyncQueueJob> pag =new PaginatedResult<SyncQueueJob>();
		try {
			int pageSize=10;
			String objectType = request.getParameter("type");
			String status = request.getParameter("status");
			String dateFrom = request.getParameter("dateFrom");
			String dateTo = request.getParameter("dateTo");
			String receiver = request.getParameter("receiver");
			int pageNo = Integer.parseInt(request.getParameter("page"));
			Date date =new Date();
			SimpleDateFormat dateFormat1 =new SimpleDateFormat("yyyyMMdd");
			String dateSearch=dateFormat1.format(date);
			if(StringUtils.isEmpty(dateFrom) && StringUtils.isEmpty(dateTo)){
				if(pageNo == 0 ){
					pag=queueJobService.findListQueueByDateCurrent(dateSearch, status, receiver, objectType, 1, pageSize);
				}else{
					pag=queueJobService.findListQueueByDateCurrent(dateSearch, status, receiver, objectType, pageNo, pageSize);
				}
			}else{
				if(pageNo == 0 ){
					pag=queueJobService.findListQueueBySearch(dateFrom, dateTo, status, receiver, objectType, 1, pageSize);
				}else{
					pag=queueJobService.findListQueueBySearch(dateFrom, dateTo, status, receiver, objectType, pageNo, pageSize);
				}
			}
			List<SyncQueueJob> listQueue=pag.getRows();
			pag.setTotal(pagingUtil.totalPage(pag.getTotal(), pageSize));
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return pag;
	}
}
