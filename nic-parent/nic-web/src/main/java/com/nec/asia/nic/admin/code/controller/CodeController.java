/**
 * 
 */
package com.nec.asia.nic.admin.code.controller;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.displaytag.tags.TableTagParameters;
import org.displaytag.util.ParamEncoder;
import org.hibernate.criterion.Order;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.ModelAndView;

import com.nec.asia.nic.framework.PaginatedResult;
import com.nec.asia.nic.framework.admin.code.domain.CodeValues;
import com.nec.asia.nic.framework.admin.code.domain.Codes;
import com.nec.asia.nic.framework.admin.code.domain.Parameters;
import com.nec.asia.nic.framework.admin.code.domain.ParametersId;
import com.nec.asia.nic.framework.admin.code.service.CodesService;
import com.nec.asia.nic.framework.admin.code.service.ParametersService;
import com.nec.asia.nic.framework.report.form.CodeValueForm;
import com.nec.asia.nic.framework.report.service.AuditAccessLogService;
import com.nec.asia.nic.util.Constants;
import com.nec.asia.nic.util.pagingUtil;
import com.nec.asia.nic.utils.DateUtil;
import com.nec.asia.nic.web.session.UserSession;


/**
 * @author prasad_karnati
 *
 */

/* 
* Modification History:
*  
* 08 Oct 2013 (Chris Lai):Update to set the ParentCodeValue
* 
* 13 Dec 2013 (Chris Lai) : Add in Audit Log
* 
*/

@Controller
@RequestMapping(value = "/codeMgmt")
public class CodeController {
	
	private static final Logger logger = LoggerFactory.getLogger(CodeController.class);
	
	private static String ASSENDING_ORDER="1";
	private static String DECENDING_ORDER="2";
	@Autowired
	private CodesService codesService;
	
	@Autowired
	private ParametersService parametersService;
	
	@Autowired
	private AuditAccessLogService auditAccessLogService;
	
/*	List<Tag> data = new ArrayList<Tag>();

	CodeController() {
		// init data for testing
		data.add(new Tag(1, "ruby"));
		data.add(new Tag(2, "rails"));
		data.add(new Tag(3, "c / c++"));
		data.add(new Tag(4, ".net"));
		data.add(new Tag(5, "python"));
		data.add(new Tag(6, "java"));
		data.add(new Tag(7, "javascript"));
		data.add(new Tag(8, "jscript"));
	}
	
	@RequestMapping(value = "/getTags", method = RequestMethod.GET)
	public @ResponseBody
	List<Tag> getTags(@RequestParam String tagName) {

		return simulateSearchResult(tagName);

	}
	
	private List<Tag> simulateSearchResult(String tagName) {

		List<Tag> result = new ArrayList<Tag>();

		// iterate a list and filter by tagName
		for (Tag tag : data) {
			if (tag.getTagName().contains(tagName)) {
				result.add(tag);
			}
		}

		return result;
	}*/
	
	//[tuenv][13 Jan 2016] - start
	//@RequestMapping(value = "/view", method = RequestMethod.GET)	
	@RequestMapping(value = "/view")
	@ExceptionHandler
	public ModelAndView getCodes(WebRequest request, Model model, @ModelAttribute("codeForm")CodeValueForm codeValForm) throws Exception {	
		
		int pageSize = Constants.PAGE_SIZE_DEFAULT;
		int pageNo = !StringUtils.isEmpty(request.getParameter("pageNo")) ? Integer.parseInt(request.getParameter("pageNo")) : 1;
		int startIndex = 0;
		ParametersId id = new ParametersId();
		id.setParaName(Parameters.MAX_RECORDS_PER_PAGE);
		id.setParaScope(Parameters.SCOPE_SYSTEM);
		String tableId = "row";
		String sortedElement = "codeId";
		String order = CodeController.DECENDING_ORDER;
		
		try {
			String sort = request.getParameter(new ParamEncoder(tableId).encodeParameterName(TableTagParameters.PARAMETER_SORT));
			if (StringUtils.isNotBlank(sort))
				sortedElement = sort;
		} catch (Exception ex) {
			logMessage(ex);
		}
			  
		try {
			String requestOrder = request.getParameter(new ParamEncoder(tableId).encodeParameterName(TableTagParameters.PARAMETER_ORDER));
			if (StringUtils.isNotBlank(requestOrder))
				order = requestOrder;
		} catch (Exception ex) {
			logMessage(ex);
		}
			  
		Order hibernateOrder = Order.desc(sortedElement);
		if (order.equalsIgnoreCase(CodeController.DECENDING_ORDER)) {
			hibernateOrder = Order.asc(sortedElement);
		}
			  
		Parameters parameter = parametersService.findById(id);
		  
		if (parameter != null) {
			String pageSizeDb = parameter.getParaShortValue();

			if (StringUtils.isNotBlank(pageSizeDb) && StringUtils.isNumeric(pageSizeDb)) {
				pageSize = Integer.parseInt(pageSizeDb);
			}
		}
		String pageNumber = request.getParameter((new ParamEncoder(tableId).encodeParameterName(TableTagParameters.PARAMETER_PAGE)));

		if (StringUtils.isNotBlank(pageNumber)
				&& StringUtils.isNumeric(pageNumber)) {
			pageNo = Integer.parseInt(pageNumber);
		}
		if (StringUtils.isNotBlank(pageNumber)) {
			startIndex = (Integer.parseInt(pageNumber) - 1) * pageSize;
		} else {
			startIndex = 10;
		}
		pageSize = Constants.PAGE_SIZE_DEFAULT;
		PaginatedResult<Codes> prCodeList = null;
		if (StringUtils.isNotBlank(codeValForm.getCodeId())) {
			prCodeList = codesService.getCodesByCodeId(codeValForm.getCodeId(),pageNo,pageSize);
		}else{
			prCodeList = codesService.getAllcodes(pageNo,pageSize);
		}
		
	 //PaginatedResult<Codes> prCodeList2 =codesService.getAllForPagination(pageNo, pageSize, hibernateOrder);
		List<Codes> list= new ArrayList<Codes>();
		if (prCodeList != null) {
			list=prCodeList.getRows();
		}
		int i = (pageNo - 1) * pageSize;
		for(Codes c : list){
			i++;
			c.setStt(i);
		}
		//model.addAttribute("jobList", list);
		//phúc edit
		int firstResults = (pageNo - 1) < 0 ? 0 : (pageNo - 1) * pageSize;
		model.addAttribute("codeForm", codeValForm);
		model.addAttribute("pageSize", pageSize);
		model.addAttribute("pageNo", pageNo);
		model.addAttribute("totalPage", pagingUtil.totalPage(prCodeList.getTotal(), pageSize));
		model.addAttribute("startIndex", prCodeList.getTotal() != 0 ? firstResults + 1 : 0);
		model.addAttribute("totalRecord", prCodeList.getTotal());
		model.addAttribute("endIndex", firstResults + prCodeList.getRowSize());
		model.addAttribute("jobList", list);
		//end
		Map codeList = new HashMap();
		codeList.put("codeList", list);
		codeList.put("totalRecords", prCodeList.getTotal());
//		codeList.put("pageSize",pageSize);
		 model.addAttribute("fnSelected", Constants.HEADING_NIC_CODE);
		return  new ModelAndView("codeMgmt.codeView",codeList);
		
	}
	
	@RequestMapping(value = {"/getcodevalues/{codeId}"})
	@ExceptionHandler
	public ModelAndView getCodeValues(WebRequest request,@PathVariable String codeId, Model model, @ModelAttribute("codeValueForm")CodeValueForm codeValForm) throws Exception {	
		
		//System.out.println("=======================================>"+codeId);
		int pageSize = 10;
		int pageNo = 1;
		int startIndex = 0;
		ParametersId id = new ParametersId();
		id.setParaName(Parameters.MAX_RECORDS_PER_PAGE);
		id.setParaScope(Parameters.SCOPE_SYSTEM);
		String tableId="row";
		String sortedElement = "id.codes.codeId";
		String order = CodeController.DECENDING_ORDER;
		
		Parameters parameter = parametersService.findById(id);

		if (parameter != null) {
			String pageSizeDb = parameter.getParaShortValue();

			if (StringUtils.isNotBlank(pageSizeDb) && StringUtils.isNumeric(pageSizeDb)) {
				pageSize = Integer.parseInt(pageSizeDb);
			}
		}
		
		String pageNumber = request.getParameter((new ParamEncoder(tableId).encodeParameterName(TableTagParameters.PARAMETER_PAGE)));

		if (StringUtils.isNotBlank(pageNumber) && StringUtils.isNumeric(pageNumber)) {
			pageNo = Integer.parseInt(pageNumber);
		}
		if (StringUtils.isNotBlank(pageNumber)) {
			startIndex = (Integer.parseInt(pageNumber) - 1) * pageSize;
		} else {
			startIndex = 10;
		}
		pageSize = Constants.PAGE_SIZE_DEFAULT;
		pageNo = !StringUtils.isEmpty(request.getParameter("pageNo")) ? Integer.parseInt(request.getParameter("pageNo")) : 1;
		PaginatedResult<CodeValues> prCodeList = null;
		if (codeValForm != null && (StringUtils.isNotBlank(codeValForm.getCodeValue()) || StringUtils.isNotBlank(codeValForm.getCodeValueDesc()))) {
			pageNo = 1;
			prCodeList =codesService.getCodevalueByCoditions(codeId, codeValForm.getCodeValue(), codeValForm.getCodeValueDesc(), pageNo, pageSize);
		}else{
			prCodeList =codesService.getCodevalueByCodeId(codeId, pageNo, pageSize);
		}
		
		List<CodeValues> list= new ArrayList<CodeValues>();
		list = prCodeList.getRows();
		
 		Map<String, Object> codeValueList = new HashMap<String, Object>();
		codeValueList.put("codeValueList", list);
		codeValueList.put("totalRecords", prCodeList.getTotal());
		codeValueList.put("pageSize",pageSize);
		//model.addAttribute("jobList", list);
		//phúc edit
		int firstResults = (pageNo - 1) < 0 ? 0 : (pageNo - 1) * pageSize;
		model.addAttribute("pageSize", pageSize);
		model.addAttribute("pageNo", pageNo);
		model.addAttribute("totalPage", pagingUtil.totalPage(prCodeList.getTotal(), pageSize));
		model.addAttribute("startIndex", prCodeList.getTotal() != 0 ? firstResults + 1 : 0);
		model.addAttribute("totalRecord", prCodeList.getTotal());
		model.addAttribute("endIndex", firstResults + prCodeList.getRowSize());
		model.addAttribute("jobList", list);
		model.addAttribute("codeId", codeId);
		//end		

		ModelAndView  modelView = new ModelAndView ("codeMgmt.codeValView",codeValueList);		
		CodeValueForm codeValueForm = new CodeValueForm();
		codeValueForm.setDisplayTable("Y");
		codeValueForm.setCodeId(codeId);
		
		modelView.addObject("codeValueForm", codeValueForm);
		model.addAttribute("fnSelected", Constants.HEADING_NIC_CODE);

		return  modelView;
	}
	
	// Chris Lai (13 Dec 2013) : Add in Audit Log
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	@ResponseBody
	@ExceptionHandler
	public String save(HttpServletRequest httpRequest, @ModelAttribute("codeValueForm")CodeValueForm codeValueForm, BindingResult result, WebRequest request)	throws Exception {
		// Chris Lai (13 Dec 2013) : Add in Audit Log
		long startTimeMs = System.currentTimeMillis();
		Throwable throwable = null;
		Exception failSave = new Exception("Failure at CodeController.save");
		
        HttpSession session = httpRequest.getSession();
        UserSession userSession = (UserSession) session.getAttribute("userSession");
        
		String userId = "SYSTEM";
		String wkstnId = "";
		Date   currentDate = DateUtil.getSystemTimestamp();
		if (StringUtils.isNotBlank(userSession.getUserName())) {
			userId = userSession.getUserName();
		} 
		if (StringUtils.isNotBlank(userSession.getUserName())) {
			wkstnId = userSession.getWorkstationId();
		} else {
			java.net.InetAddress localMachine = InetAddress.getLocalHost();
			wkstnId = localMachine.getHostName();
		}
		String status = codesService.saveCodeDefinition(codeValueForm, userId, currentDate, wkstnId);

		/*ModelAndView mav =null;
			if(status.equals("success")){
				request.setAttribute("status", "newCode", 1);
				return new ModelAndView("forward:/servlet/codeMgmt/getcodevalues/"+codeValueForm.getCodeId().trim());
				 
				
			}
			
			codeValueForm.setStatus("fail");
			return new ModelAndView("codeMgmt.saveorupdate","codeValueForm",codeValueForm);*/
		// Chris Lai (13 Dec 2013) : Add in Audit Log
		if(!status.equals("success")){
			throwable = failSave;
		}
		try {
			String functionName = "Thêm mới danh mục hệ thống";
			Object[] args = { codeValueForm };
			long timeMs = System.currentTimeMillis() - startTimeMs;
			auditAccessLogService.saveAuditAccessLogForWeb(httpRequest, functionName, args, throwable, timeMs);
		} catch(Exception exp){
		}
		
		return status;		
	}
	@RequestMapping(value = {"/editCode/{codeParams}"})	
	@ExceptionHandler
	public ModelAndView editCode(WebRequest request,@PathVariable String codeParams,Model model) throws Exception {
		String codeId="";
		String codeName="";
		if(codeParams.contains("&&")){
			String params [] =codeParams.split("&&");
			codeId=params[0];
			codeName=params[1];
		} else if(codeParams!=null){
			codeId=codeParams;
		}
		CodeValues codeValueDBO = codesService.getCodeValueByIdName(codeId, codeName);
		
		CodeValueForm codeValueForm = new CodeValueForm();
		codeValueForm.setCodeId( codeValueDBO.getId().getCodeId());
		codeValueForm.setCodeValue(codeValueDBO.getId().getCodeValue());
		codeValueForm.setCodeValueDesc(codeValueDBO.getCodeValueDesc());
		codeValueForm.setCodePriority(String.valueOf(codeValueDBO.getCodePriority()));
		codeValueForm.setCodeName(codeValueDBO.getId().getCodeId());
		//08 Oct 2013 (Chris Lai): Update to set the ParentCodeValue
		codeValueForm.setParentCodeValue(codeValueDBO.getParentCodeValue());
		codeValueForm.setDisplayTable("Y");

		model.addAttribute("fnSelected", Constants.HEADING_NIC_CODE);
		logger.debug(" Exit from edit  codeValue : edit  ");
		return new ModelAndView ("codeMgmt.saveorupdate", "codeValueForm", codeValueForm);
	}
	
	// Chris Lai (13 Dec 2013) : Add in Audit Log
	@RequestMapping(value = {"/deleteCode"})
	@ExceptionHandler
	public ModelAndView deleteCode(HttpServletRequest httpRequest, @ModelAttribute("codeValueForm")CodeValueForm codeValueForm, BindingResult result,Model model) throws Exception {
		// Chris Lai (13 Dec 2013) : Add in Audit Log
		long startTimeMs = System.currentTimeMillis();
		Throwable throwable = null;
		Exception failDelete = new Exception();
		
        HttpSession session = httpRequest.getSession();
        UserSession userSession = (UserSession) session.getAttribute("userSession");
		
		CodeValueForm codeValueFormResponse = new CodeValueForm();
		//[tuenv][13 Jan 2016] - Modify	
		int pageSize = 20;	
		ParametersId id = new ParametersId();
		id.setParaName(Parameters.MAX_RECORDS_PER_PAGE);
		id.setParaScope(Parameters.SCOPE_SYSTEM);
		Parameters parameter = parametersService.findById(id);
		if (parameter != null) {
			String pageSizeDb = parameter.getParaShortValue();
			if (StringUtils.isNotBlank(pageSizeDb) && StringUtils.isNumeric(pageSizeDb)) {
				pageSize = Integer.parseInt(pageSizeDb);
			}
		}
		String userId = "SYSTEM";
		String wkstnId = "";
		Date   currentDate = DateUtil.getSystemTimestamp();
		if (StringUtils.isNotBlank(userSession.getUserName())) {
			userId = userSession.getUserName();
		} 
		if (StringUtils.isNotBlank(userSession.getUserName())) {
			wkstnId = userSession.getWorkstationId();
		} else {
			java.net.InetAddress localMachine = InetAddress.getLocalHost();
			wkstnId = localMachine.getHostName();
		}
		
		String status = "";
		ModelAndView  modelView = null;
		if(StringUtils.isEmpty(codeValueForm.getCodeValue())) {
			status = codesService.deleteCode(codeValueForm.getCodeId(), userId, currentDate, wkstnId);
			if(status.equals("success")){
				status="success";			
				codeValueFormResponse.setStatus(status);
				codeValueFormResponse.setMessage("Xóa thành công mã: " + codeValueForm.getCodeId());
			}else {
				status="fail";
				codeValueFormResponse.setStatus(status);
				codeValueFormResponse.setMessage("Xóa không thành công mã: " + codeValueForm.getCodeId());			
				throwable = failDelete;
			}
			
			PaginatedResult<Codes> prCodeList =codesService.getAllcodes(1,pageSize);
			List<Codes> list= new ArrayList<Codes>();
			list=prCodeList.getRows();
			Map codeList = new HashMap();
			codeList.put("codeList", list);
			codeList.put("totalRecords", prCodeList.getTotal());
			codeList.put("pageSize",pageSize);
			model.addAttribute("fnSelected", Constants.HEADING_NIC_CODE);
			modelView = new ModelAndView("codeMgmt.codeView",codeList);
			
		} else {
			status = codesService.deleteCodeValue(codeValueForm.getCodeId(), codeValueForm.getCodeValue(), userId, currentDate, wkstnId);
			if(status.equals("success")){
				status="success";			
				codeValueFormResponse.setStatus(status);
				codeValueFormResponse.setMessage("Xóa thành công mã: " + codeValueForm.getCodeValue());
				//[tuenv][14 Jan 2016] - add
				codeValueFormResponse.setCodeId(codeValueForm.getCodeId());
			}else {
				status="fail";
				codeValueFormResponse.setStatus(status);
				codeValueFormResponse.setMessage("Xóa không thành công mã: " + codeValueForm.getCodeValue());			
				throwable = failDelete;
			}
			
			PaginatedResult<CodeValues> prCodeList =codesService.getCodevalueByCodeId(codeValueForm.getCodeId(), 1, pageSize);
			List<CodeValues> list= new ArrayList<CodeValues>();
			list=prCodeList.getRows();
			Map codeValueList = new HashMap();
			codeValueList.put("codeValueList", list);
			codeValueList.put("totalRecords", prCodeList.getTotal());
			codeValueList.put("pageSize",pageSize);
			modelView = new ModelAndView ("codeMgmt.codeValView",codeValueList);
			model.addAttribute("fnSelected", Constants.HEADING_NIC_CODE);
			codeValueFormResponse.setDisplayTable("Y");
			modelView.addObject("codeValueForm", codeValueFormResponse);
		}
		
		// Chris Lai (13 Dec 2013) : Add in Audit Log
		try {
			String functionName = "Xóa danh mục hệ thống";
			Object[] args = { codeValueForm };
			long timeMs = System.currentTimeMillis() - startTimeMs;
			auditAccessLogService.saveAuditAccessLogForWeb(httpRequest, functionName, args, throwable, timeMs);
		} catch(Exception exp){}
		
		
		return  modelView;
		
	}
	// Chris Lai (13 Dec 2013) : Add in Audit Log
	@RequestMapping(value = {"/updateCodeValue"} )
	@ResponseBody
	@ExceptionHandler
	public String updateCodeValue(HttpServletRequest httpRequest,@ModelAttribute("codeValueForm")CodeValueForm codeValueForm, BindingResult result) throws Exception {	
		logger.info("[updateCodeValue] - {} {} ", new Object[] { codeValueForm!=null?codeValueForm.getCodeId():"", codeValueForm!=null?codeValueForm.getCodeValue():"" } );
		
		// Chris Lai (13 Dec 2013) : Add in Audit Log
		long startTimeMs = System.currentTimeMillis();
		Throwable throwable = null;
		Exception failUpdate = new Exception();
		
        HttpSession session = httpRequest.getSession();
        UserSession userSession = (UserSession) session.getAttribute("userSession");
		String userId = "SYSTEM";
		String wkstnId = "";
		Date   currentDate = DateUtil.getSystemTimestamp();
		if (StringUtils.isNotBlank(userSession.getUserName())) {
			userId = userSession.getUserName();
		} 
		if (StringUtils.isNotBlank(userSession.getUserName())) {
			wkstnId = userSession.getWorkstationId();
		} else {
			java.net.InetAddress localMachine = InetAddress.getLocalHost();
			wkstnId = localMachine.getHostName();
		}
		
		String status = codesService.updateCodeValue(codeValueForm, userId, currentDate, wkstnId);
		if(status.equals("Success")){
			status="success";
		} else {
			status="fail";
			throwable = failUpdate;
		}

		// Chris Lai (13 Dec 2013) : Add in Audit Log
		try {
			String functionName = "Cập nhật danh mục hệ thống";
			Object[] args = { codeValueForm };
			long timeMs = System.currentTimeMillis() - startTimeMs;
			auditAccessLogService.saveAuditAccessLogForWeb(httpRequest, functionName, args, throwable, timeMs);
		} catch(Exception exp){
		}
		logger.info("[updateCodeValue] - {}", status);
		return  status;
	}
	
	@RequestMapping(value = {"/requestToSaveUpdate"})
	@ExceptionHandler
	public ModelAndView requestToSaveUpdate(@ModelAttribute("codeValueForm")CodeValueForm codeValForm, BindingResult result,Model model) throws Exception {		
		CodeValueForm codeValueForm =new CodeValueForm();
		//[tuenv][13 Jan 2016] - start
		//codeValueForm.setCodeName(codeValForm.getCodeId());
		if(null != codeValForm.getCodeId()) {
			codeValueForm.setCodeName(codeValForm.getCodeId());
			codeValueForm.setCodeId(codeValForm.getCodeId());
		}
		
		model.addAttribute("fnSelected", Constants.HEADING_NIC_CODE);
		return new ModelAndView ("codeMgmt.saveorupdate","codeValueForm",codeValueForm);
	}
	@RequestMapping(value = {"/cancelAndBack"})	
	@ExceptionHandler
	public ModelAndView cancelAndBack(@ModelAttribute("codeValueForm")CodeValueForm codeValueForm) throws Exception {	
		
		
		//[tuenv][13 Jan 2016] - Modify	
		int pageSize = 20;	
		ParametersId id = new ParametersId();
		id.setParaName(Parameters.MAX_RECORDS_PER_PAGE);
		id.setParaScope(Parameters.SCOPE_SYSTEM);
		Parameters parameter = parametersService.findById(id);
		if(parameter!=null){
			 String pageSizeDb = parameter.getParaShortValue();
			 if(StringUtils.isNotBlank(pageSizeDb) && StringUtils.isNumeric(pageSizeDb)){
				 pageSize = Integer.parseInt(pageSizeDb);
			 }
		  }
		
		PaginatedResult<CodeValues> prCodeList =codesService.getCodevalueByCodeId(codeValueForm.getCodeName(), 1, 20);
		
		 List<CodeValues> list= new ArrayList<CodeValues>();
		 list=prCodeList.getRows();
		Map<String, Object> codeValueList = new HashMap<String, Object>();
		codeValueList.put("codeValueList", list);
		codeValueList.put("totalRecords", prCodeList.getTotal());
		codeValueList.put("pageSize",pageSize);
		
		ModelAndView  modelView = new ModelAndView ("codeMgmt.codeValView", codeValueList);		
		CodeValueForm codeValueFormResponse = new CodeValueForm();
		codeValueFormResponse.setDisplayTable("Y");
		codeValueFormResponse.setCodeId(codeValueForm.getCodeName());
		modelView.addObject("codeValueForm", codeValueFormResponse);
		
		//return  new ModelAndView ("codeMgmt.codeValView",codeValueList);
		
		return  modelView;
	}
	
	private void logMessage(Exception ex) {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		  PrintStream ps = new PrintStream(baos);
		  ex.printStackTrace(ps);
		  logger.error(ex.getMessage()+" : \n" + new String(baos.toByteArray()));
	}

}
