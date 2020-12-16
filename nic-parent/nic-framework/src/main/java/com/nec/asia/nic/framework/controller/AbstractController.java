package com.nec.asia.nic.framework.controller;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.xml.ws.WebServiceException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.propertyeditors.CustomBooleanEditor;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.ModelAndView;

//import com.nec.asia.nic.dx.ws.FaultException;
import com.nec.asia.nic.framework.PaginatedResult;
import com.nec.asia.nic.framework.service.BusinessService;


/**
 * Abstraction class of controller layer
 * All controller should extends this class for sharing some common functions
 * 
 * @author bright_zheng
 *
 */
/*
 * Modification History:
 * 
 * 23 Dec 2015 (chris): remove method throwFaultException() 
 */
public class AbstractController extends LoggingHandlerExceptionResolver {
	Logger logger = LoggerFactory.getLogger(AbstractController.class);
	//APARNA CHANGE START 18/9/2013
	public static String WEB_SERVICE_ERROR_MSG="Web Service Error. Please Try Again Later.";
	//APARNA CHANGE END 18/9/2013
	/** ajax form validation parameter */
	protected static final String PARAM_NAME_AJAX_FORM_VALIDATION = "_ajax_form_validation";
	
	/** ajax form validation default logical view name */
	protected static final String VIEW_NAME_AJAX_FORM_VALIDATION = "global.view.ajaxFormValidatorResult";
	
	/**
	 * Register custom propertyEditors here
	 * 
	 * @param binder
	 */
	@InitBinder
	protected void initBinder(WebDataBinder binder) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		dateFormat.setLenient(false);
		binder.registerCustomEditor(Date.class,new CustomDateEditor(dateFormat, false));
		binder.registerCustomEditor(boolean.class,new CustomBooleanEditor("TRUE", "FALSE", true));
	}

	/**
	 * Encapsulation of Flexigrid UI component
	 * 
	 * the submit format of Flexigrid is:
	 * 	page=2&rp=20&sortname=partyName&sortorder=asc&query=&qtype=personTitle
	 *
	 * @param request
	 * @param domain
	 * @param service
	 * @return the JsonDataWrapper. It will be automatically transfered to the json string like:
	 * 		{"total":2,"page":1,"rows":[
	 * 			{"personTitle":"Mr","partyName":"Test8325","personDateOfBirth":"1970-07-12"},
	 * 			{"personTitle":"Ms","partyName":"Ms Susan Jones","personDateOfBirth":"1955-11-27"}
	 * 		]}
	 * @throws Exception
	 */
	protected <E, ID extends Serializable> PaginatedResult<E> getPaginatedGridData(
			WebRequest request, E e, BusinessService<E, ID> service) throws Exception{
		
		// parameters we can get from Flexigrid
		int pageNo=Integer.parseInt(request.getParameter("page"));	//current page no.: start from 1
		int pageSize=Integer.parseInt(request.getParameter("rp"));	//per page size: 15
		String orderBy = request.getParameter("sortname");			//Sort by column: e.g. address
		String orderSeq = request.getParameter("sortorder");		//Sort by sequence: asc or desc
		//ignore parameters: query - the query string, qtype - the query column
		
		PaginatedResult<E> result = service.findAllForPagination(e, pageNo, pageSize);		
		
		return result;
		
		/*
		//filter in grid
		String filterBy = request.getParameter("qtype");
		String filterKeyword = request.getParameter("query");
		if(StringUtils.isNotEmpty(filterBy) && StringUtils.isNotEmpty(filterKeyword)){
			map.put(filterBy, filterKeyword);
		}
		
		//get total record count
		int total = 10;		
		
		//order
		String orderBy = request.getParameter("sortname");
		String orderSeq = request.getParameter("sortorder");
		if(StringUtils.isNotEmpty(orderBy) && StringUtils.isNotEmpty(orderSeq)){
			map.put("ORDER_BY_"+orderBy,orderSeq);
		}
		
		//limit
		int page=1,pageSize=Constants.PAGE_SIZE_DEFAULT;
		try{
			page = Integer.parseInt(request.getParameter("page"));
			pageSize = Integer.parseInt(request.getParameter("rp"));
			if(page<1) page=1;
			if(pageSize>500) pageSize=Constants.PAGE_SIZE_DEFAULT;
		}catch(Exception e){
			logger.debug("Should not be error by getting the page/rp of FlexiGrid", e);
			page=1;
			pageSize=Constants.PAGE_SIZE_DEFAULT;
		}finally{
			map.put("LIMIT_offset", (page-1) * pageSize);
			map.put("LIMIT_size", pageSize);
		}
		
		//query
		List<T> list = service.query(map);
		return new JsonDataWrapper<T>(total,page,list);
		*/
	}
	//APARNA CHANGE START 18/9/2013
	@ExceptionHandler(WebServiceException.class)
	public ModelAndView throwWebServiceException(WebServiceException exception){
		 ModelAndView modelAndView = new ModelAndView();
		 modelAndView.setViewName("webservice.error");
		 modelAndView.addObject("msg", WEB_SERVICE_ERROR_MSG);
		return modelAndView;
	}
	
//	@ExceptionHandler(FaultException.class)
//	public ModelAndView throwFaultException(FaultException exception){
//		 ModelAndView modelAndView = new ModelAndView();
//		 modelAndView.setViewName("webservice.error");
//		 modelAndView.addObject("msg", WEB_SERVICE_ERROR_MSG);
//		return modelAndView;
//	}
	
	@ExceptionHandler(Exception.class)
	public ModelAndView throwException(Exception anExc){
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("error");
		modelAndView.addObject("exception", anExc);
		modelAndView.addObject("message", anExc.getLocalizedMessage());
		modelAndView.addObject("messageStack", anExc.getMessage());
		return modelAndView;
	}

}