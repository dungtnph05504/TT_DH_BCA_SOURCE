/**
 * 
 */
package com.nec.asia.nic.proofdocmatrix.controller;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.ModelAndView;

import com.nec.asia.nic.framework.PaginatedResult;
import com.nec.asia.nic.framework.admin.code.domain.CodeValues;
import com.nec.asia.nic.framework.admin.code.domain.Parameters;
import com.nec.asia.nic.framework.admin.code.domain.ParametersId;
import com.nec.asia.nic.framework.admin.code.domain.ProofDocumentDef;
import com.nec.asia.nic.framework.admin.code.domain.ProofDocumentDefId;
import com.nec.asia.nic.framework.admin.code.service.CodesService;
import com.nec.asia.nic.framework.admin.code.service.ParametersService;
import com.nec.asia.nic.framework.admin.code.service.ProofDocumentDefService;
import com.nec.asia.nic.framework.controller.AbstractController;
import com.nec.asia.nic.framework.report.service.AuditAccessLogService;
import com.nec.asia.nic.util.Constants;
import com.nec.asia.nic.utils.DateUtil;
import com.nec.asia.nic.web.session.UserSession;

/**
 * @author sailaja_chinapothula
 * @Company: NEC Asia Pacific Ltd
 * @Since: Jun 24, 2013
 */
/* 
 * Modification History:
 *  
 * 25 Sep 2013 (chris): add audit log
 * 
 * 2 Oct 2013 (Peddi Swapna): Implemented the external pagination by getting the page size from system parameter.
 * 
 * 2 Oct 2013 (Peddi Swapna): Implemented the external sorting.
 * 
 * 30 Dec 2013 (chris lai) : update with audit record
 */
@Controller
@RequestMapping(value = "/proofDocMatrixController")
public class ProofDocMatrixController extends AbstractController {
	
	private static String ASSENDING_ORDER="1";
	private static String DECENDING_ORDER="2";
	
	@Autowired
	private ProofDocumentDefService proofDocumentDefService;
	
	//25 Sep 2013 (chris) start
	@Autowired
	private AuditAccessLogService auditAccessLogService;
	//25 Sep 2013 (chris) end
	
	@Autowired
	//@Qualifier(value="codesService")
	private CodesService codesService;
	
	@Autowired
	private ParametersService parametersService;
	
	private static final Logger logger = LoggerFactory.getLogger(ProofDocMatrixController.class);
	
	@RequestMapping(value="/proofDocMatrix")
	@ExceptionHandler
	public ModelAndView proofDocMatrix(WebRequest request, ModelMap model ,@ModelAttribute ProofDocumentDef form) throws Exception{
		logger.info("Proof Document Matrix");
		PaginatedResult<ProofDocumentDef> pr = null;
		try {
			// NicUploadJob nicUploadJob = null;
			int pageNo = 1;
			// int pageSize = 10000;
			ParametersId id = new ParametersId();
			id.setParaName(Parameters.MAX_RECORDS_PER_PAGE);
			id.setParaScope(Parameters.SCOPE_SYSTEM);
			String tableId = "row";
			String sortedElement = "id.documentId";
			String order = ProofDocMatrixController.DECENDING_ORDER;
			  
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
			if (order.equalsIgnoreCase(ProofDocMatrixController.DECENDING_ORDER)) {
				hibernateOrder = Order.asc(sortedElement);
			}
				  
			Parameters parameter = parametersService.findById(id);
			int pageSize = 20;
			if (parameter != null) {
				String pageSizeDb = parameter.getParaShortValue();

				if (StringUtils.isNotBlank(pageSizeDb) && StringUtils.isNumeric(pageSizeDb)) {
					pageSize = Integer.parseInt(pageSizeDb);
				}
			}
			
			int startIndex = 0;
			ProofDocumentDef proofDocumentDef = new ProofDocumentDef();
		      
			String pageNumber = request.getParameter((new ParamEncoder(tableId).encodeParameterName(TableTagParameters.PARAMETER_PAGE)));
		      
			if (StringUtils.isNotBlank(pageNumber) && StringUtils.isNumeric(pageNumber)) {
				pageNo = Integer.parseInt(pageNumber);
			}
			if (StringUtils.isNotBlank(pageNumber)) {
				startIndex = (Integer.parseInt(pageNumber) - 1) * pageSize;
			} else {
				startIndex = 10;
			}
		      
			proofDocumentDef.setDeleteFlag("N");
		    pr = proofDocumentDefService.getAllForPagination(pageNo, pageSize, hibernateOrder);
		    
			List<ProofDocumentDef> list = new ArrayList<ProofDocumentDef>();
			List<String> newRegistrationList = new ArrayList<String>();
			List<String> newRegistration = new ArrayList<String>();
			List<String> replacement = new ArrayList<String>();
			List<String> replacementList = new ArrayList<String>();
			List<String> updateParticulars = new ArrayList<String>();
			List<String> updateParticularsList = new ArrayList<String>();
			
		    Map<String, Object> proofDocMap = proofDocumentDefService.findProofDocumentDefForMatrix();		      
		    Map<String, List<String>> transactionTypeMap = (Map<String, List<String>>) proofDocMap.get("transactionTypeMap");
		    
		    List<String> documentIdList = (List<String>) proofDocMap.get("documentIdList");
		    String [][]  indicatorMatrix = (String [][]) proofDocMap.get("requiredIndicatorMatrix");
		    
		    List<CodeValues> codeValList = proofDocumentDefService.getCodeValue(Constants.TRANSACTION_SUBTYPE);
		       
			for (Map.Entry<String, List<String>> emap : transactionTypeMap.entrySet()) {
				if (emap.getKey().equals("REG")) {
					newRegistrationList = emap.getValue();
					for (String name : newRegistrationList) {
						for (CodeValues value : codeValList) {
							if (name.equals(value.getId().getCodeValue())) {
								newRegistration.add(value.getCodeValueDesc());
								break;
							}
						}
					}
				} else if (emap.getKey().equals("REP")) {
					replacementList = emap.getValue();
					for (String name : replacementList) {
						for (CodeValues value : codeValList) {
							if (name.equals(value.getId().getCodeValue())) {
								replacement.add(value.getCodeValueDesc());
								break;
							}
						}
					}
				} else {
					updateParticularsList = emap.getValue();
					for (String name : updateParticularsList) {
						for (CodeValues value : codeValList) {
							if (name.equals(value.getId().getCodeValue())) {
								updateParticulars.add(value.getCodeValueDesc());
								break;
							}
						}
					}
				}
			}
		     
		      
			Map<String, Object> columnMap = new TreeMap<String, Object>();
			List<ProofDocumentDef> documentList = proofDocumentDefService.findAllDistinctProofDocuments();
			Set<ProofDocumentDef> setItems = new LinkedHashSet<ProofDocumentDef>(documentList);
			documentList.clear();
			documentList.addAll(setItems);
			int i = 0;
			for (String documentId : documentIdList) {
				for (ProofDocumentDef proofDoc : documentList) {
					if (documentId.equals(proofDoc.getId().getDocumentId())) {
						columnMap.put(proofDoc.getDocumentDesc(), indicatorMatrix[i]);
						++i;
						break;
					}
				}
			}
			proofDocumentDef.setNewRegistration(newRegistration);
			proofDocumentDef.setReplacement(replacement);
			proofDocumentDef.setUpdateParticulars(updateParticulars);
			proofDocumentDef.setColumnMap(columnMap);
			if (request.getAttribute("status", 1) != null) {
				proofDocumentDef.setStatus((String) request.getAttribute("status", 1));
				request.setAttribute("status", "empty", 1);
				proofDocumentDef.setDocumentId(form.getDocumentId());
			}
		     // proofDocForm.setTransactionTypeMap(transactionTypeMap);
		     
		      if(pr!=null){
			    list = pr.getRows();
			    if(list!=null){
				logger.info("==========Nic Proof Document List======>"+ list.size());
				
					
					
				model.addAttribute("proofDocumentDef", proofDocumentDef);
				Map searchResultMap = new HashMap();
				searchResultMap.put("list", list);
				searchResultMap.put("totalRecords", pr.getTotal());
				searchResultMap.put("pageSize",pageSize);
				model.addAttribute("list", list);	
				model.addAttribute("fnSelected", Constants.HEADING_NIC_PROOF_DOC);
				return new ModelAndView("proof-doc-matrix",searchResultMap);
		    
			    }
			    }
		} catch (Exception e) {
			throw new Exception(e);
		}
		return new ModelAndView("proof-doc-matrix",null);
	}
	
	@RequestMapping(value="/addProofDocMatrix")
	@ExceptionHandler
	public ModelAndView addProofDocMatrix(WebRequest request, ModelMap model) throws Exception{
		logger.info("Add Proof Doc Matrix");
		ProofDocumentDef form= new ProofDocumentDef();
		List<CodeValues>  transactionTypes =codesService.findAllByCodeId("TRANSACTION_TYPE");
		List<CodeValues>  transactionSubTypes =codesService.findAllByCodeId("TRANSACTION_SUBTYPE");
		
		request.setAttribute("transactionTypes", transactionTypes,1);
		request.setAttribute("transactionSubTypes", transactionSubTypes,1);
		
		ModelAndView mav = new ModelAndView("addproof-doc");
		model.addAttribute("fnSelected", Constants.HEADING_NIC_PROOF_DOC);
	    mav.addObject("proofDocumentDef", form);
		return mav;
		
	}
	
	@RequestMapping(value="/updateProofDoc/{params}")
	@ExceptionHandler
	public ModelAndView updateProofDocMatrix(WebRequest request, @PathVariable String params ,@ModelAttribute ProofDocumentDef list, ModelMap model) throws Exception{
		logger.info("Update Proof Doc Matrix");
		ProofDocumentDef form= new ProofDocumentDef();
		String paramArr []=null;
		if(params.contains(",")){
			paramArr = params.split(",");			
		}
		if(paramArr[0]!=null){
			form.setDocumentId(paramArr[0]);
		}
		if(paramArr[1]!=null){
			form.setDocumentDesc(paramArr[1]);
		}
		if(paramArr[2]!=null){
			form.setTransactionType(paramArr[2]);
		}
		if(paramArr[3]!=null){
			form.setTransactionSubtype(paramArr[3]);
		}
		if(paramArr[4]!=null){
			form.setRequireIndicator(paramArr[4]);
		}

		ModelAndView mav = new ModelAndView("proof-doc-edit");
		model.addAttribute("fnSelected", Constants.HEADING_NIC_PROOF_DOC);
	    mav.addObject("proofDocumentDef", form);
		return mav;
		
	}
	@RequestMapping(value="/proofDocsMatrix")
	@ExceptionHandler
	public ModelAndView getProofDocMatrix(WebRequest request, @ModelAttribute ProofDocumentDef form, ModelMap model) throws Exception{
		try {
			Map<String, Object> proofDocMap = proofDocumentDefService.findProofDocumentDefForMatrix();

			Map<String, List<String>> transactionTypeMap = (Map<String, List<String>>) proofDocMap.get("transactionTypeMap");
			form.setTransactionTypeMap(transactionTypeMap);

			logger.info("===============" + proofDocMap.size());
		} catch (Exception exp) {
			exp.printStackTrace();

		}
		ModelAndView mav = new ModelAndView();
		model.addAttribute("fnSelected", Constants.HEADING_NIC_PROOF_DOC);
	    mav.addObject("proof-doc-matrix", form);
		return mav;
		
	}
	
	@RequestMapping(value="/proofDocsUpdate")
	@ResponseBody
	@ExceptionHandler
	public String proofDocUpdate(WebRequest request, HttpServletRequest httpRequest, ModelMap model , @ModelAttribute ProofDocumentDef form) throws Exception{
		String status="";
		//25 Sep 2013 (chris) start
		long startTimeMs = System.currentTimeMillis();
		Throwable throwable = null;
		//25 Sep 2013 (chris) end
		try{
			
			HttpSession session = httpRequest.getSession();
			UserSession userSession = (UserSession) session.getAttribute("userSession");
			logger.info("[proofDocUpdate] The User Id is ===============>>>>>>>>>>" + userSession.getUserName());
			String userId = "SYSTEM";
			String wkstnId = "";
			if (StringUtils.isNotBlank(userSession.getUserName())) {
				userId = userSession.getUserName();
			} 
			if (StringUtils.isNotBlank(userSession.getUserName())) {
				wkstnId = userSession.getWorkstationId();
			} else {
				java.net.InetAddress localMachine = InetAddress.getLocalHost();
				wkstnId = localMachine.getHostName();
			}
			//ProofDocumentDef obj= new ProofDocumentDef();
			//ProofDocumentDefId proofDocId= new ProofDocumentDefId();
			//proofDocId.setDocumentId(form.getDocumentId());
			//proofDocId.setTransactionType(form.getTransactionType());
			//proofDocId.setTransactionSubtype(form.getTransactionSubtype());			
			//obj.setId(proofDocId);
			//obj.setDocumentDesc(form.getDocumentDesc());
			//obj.setRequireIndicator(form.getRequireIndicator());
			//form.setUpdateBy("SYSTEM");
			//form.setUpdateDate(DateUtil.getSystemTimestamp());
			//form.setUpdateWkstnId(localMachine.getHostName());
			
			form.setUpdateBy(userId);
			form.setUpdateDate(DateUtil.getSystemTimestamp());
			form.setUpdateWkstnId(wkstnId);
			
			status = proofDocumentDefService.updateProofDocMatrix(form);
			form.setStatus(status);		
			List<String> messages  = new ArrayList<String>();
			messages.add("Proof Document:"+form.getDocumentId()+" saved successfully");
			request.setAttribute("messages", messages,1);			
		}catch(Exception exp){
			exp.printStackTrace();
			//25 Sep 2013 (chris) start
			throwable = exp;
			//25 Sep 2013 (chris) end
			status="success";
		}
		//25 Sep 2013 (chris) start
		finally {
			try {
				String functionName = "proofDocsUpdate";
				Object[] args = { form };
				long timeMs = System.currentTimeMillis() - startTimeMs;
				auditAccessLogService.saveAuditAccessLogForWeb(httpRequest, functionName, args, throwable, timeMs);
			} catch(Exception exp){
			}
		}
		//25 Sep 2013 (chris) end
		
		ModelAndView mav = new ModelAndView("proof-doc-edit");
	    mav.addObject("proofDocumentDef", form);
	    model.addAttribute("fnSelected", Constants.HEADING_NIC_PROOF_DOC);
	    
	    return status;
//	    return proofDocMatrix(request,model, form);
		
	}
	
	@RequestMapping(value="/proofDocCreate")
	@ResponseBody
	@ExceptionHandler
	public String proofDocCreate(WebRequest request, HttpServletRequest httpRequest, ModelMap model , @ModelAttribute ProofDocumentDef form) throws Exception {
		//30 Dec 2013 (chris lai) : update with audit record
		long startTimeMs = System.currentTimeMillis();
		Throwable throwable = null;
		
		String status="";
		try{
			HttpSession session = httpRequest.getSession();
			UserSession userSession = (UserSession) session.getAttribute("userSession");
			logger.info("[proofDocCreate] The User Id is ===============>>>>>>>>>>" + userSession.getUserName());
			String userId = "SYSTEM";
			String wkstnId = "";
			if (StringUtils.isNotBlank(userSession.getUserName())) {
				userId = userSession.getUserName();
			} 
			if (StringUtils.isNotBlank(userSession.getUserName())) {
				wkstnId = userSession.getWorkstationId();
			} else {
				java.net.InetAddress localMachine = InetAddress.getLocalHost();
				wkstnId = localMachine.getHostName();
			}
			
			java.net.InetAddress localMachine = InetAddress.getLocalHost();
			ProofDocumentDef obj= new ProofDocumentDef();
			ProofDocumentDefId proofDocId= new ProofDocumentDefId();
			proofDocId.setDocumentId(form.getDocumentId());
			proofDocId.setTransactionType(form.getTransactionType());
			proofDocId.setTransactionSubtype(form.getTransactionSubtype());		
			
			ProofDocumentDef orgProofDocumentDef = proofDocumentDefService.findById(proofDocId);
			if(orgProofDocumentDef !=null){
				status="alreadyExists";
				return status;
			}
			obj.setId(proofDocId);
			obj.setDocumentDesc(form.getDocumentDesc());
			obj.setRequireIndicator(form.getRequireIndicator());
			//obj.setCreateBy(form.getCreateBy());
			//obj.setCreateDate(DateUtil.getSystemTimestamp());
			//obj.setCreateWkstnId(localMachine.getHostName());
			obj.setCreateBy(userId);
			obj.setCreateDate(DateUtil.getSystemTimestamp());
			obj.setCreateWkstnId(wkstnId);
			
			proofDocumentDefService.saveOrUpdate(obj);
			status="success";
			
			List<String> messages  = new ArrayList<String>();
			messages.add("Proof Document:"+form.getDocumentId()+" created successfully");
			request.setAttribute("messages", messages,1);
			
		}catch(Exception exp){
			exp.printStackTrace();	
			status="fail";
			List<String> messages  = new ArrayList<String>();
			messages.add("Error occurred while creating the Proof Document:"+form.getDocumentId());
			request.setAttribute("messages", messages,1);
			ModelAndView mav = new ModelAndView("addproof-doc");
			model.addAttribute("fnSelected", Constants.HEADING_NIC_PROOF_DOC);
			mav.addObject("proofDocumentDef", form);
			//30 Dec 2013 (chris lai) : update with audit record
			throwable = exp;
		}
		finally {
			//30 Dec 2013 (chris lai) : update with audit record
			try {
				String functionName = "proofDocsCreate";
				Object[] args = { form };
				long timeMs = System.currentTimeMillis() - startTimeMs;
				auditAccessLogService.saveAuditAccessLogForWeb(httpRequest, functionName, args, throwable, timeMs);
			} catch(Exception exp){
			}
		}
		return status;
		
	}
	
	@RequestMapping(value="/proofDocsDelete")
	@ExceptionHandler
	public String proofDocDelete(WebRequest request, HttpServletRequest httpRequest, ModelMap model, @ModelAttribute ProofDocumentDef form) throws Exception {
		String status="";

		long startTimeMs = System.currentTimeMillis();
		Throwable throwable = null;
		try{
			HttpSession session = httpRequest.getSession();
			UserSession userSession = (UserSession) session.getAttribute("userSession");
			logger.info("[proofDocCreate] The User Id is ===============>>>>>>>>>>" + userSession.getUserName());
			String userId = "SYSTEM";
			String wkstnId = "";
			if (StringUtils.isNotBlank(userSession.getUserName())) {
				userId = userSession.getUserName();
			} 
			if (StringUtils.isNotBlank(userSession.getUserName())) {
				wkstnId = userSession.getWorkstationId();
			} else {
				java.net.InetAddress localMachine = InetAddress.getLocalHost();
				wkstnId = localMachine.getHostName();
			}
			
			//TODO if soft delete failed then need to retrieve then set to soft delete. 
			//ProofDocumentDef obj= new ProofDocumentDef();
			//ProofDocumentDefId proofDocId= new ProofDocumentDefId();
			//proofDocId.setDocumentId(form.getDocumentId());
			//proofDocId.setTransactionType(form.getTransactionType());
			//proofDocId.setTransactionSubtype(form.getTransactionSubtype());			
			//obj.setId(proofDocId);
			//obj.setDocumentDesc(form.getDocumentDesc());
			//obj.setRequireIndicator(form.getRequireIndicator());
			//form.setDeleteBy("SYSTEM");
			//form.setDeleteDate(DateUtil.getSystemTimestamp());
			//form.setDeleteWkstnId(localMachine.getHostName());
			
			form.setDeleteBy(userId);
			form.setDeleteDate(DateUtil.getSystemTimestamp());
			form.setDeleteWkstnId(wkstnId);

			form.setDeleteFlag("Y");
			status = proofDocumentDefService.deleteProofDocMatrix(form);
			form.setStatus(status);	
			
			List<String> messages  = new ArrayList<String>();
			messages.add("Proof Document:"+form.getDocumentId()+" deleted successfully");
			request.setAttribute("messages", messages,1);
			
		}catch(Exception exp){
			exp.printStackTrace();
			status="fail";
			//30 Dec 2013 (chris lai) : update with audit record
			throwable = exp;
			
		}finally {
			//30 Dec 2013 (chris lai) : update with audit record
			try {
				String functionName = "proofDocsDelete";
				Object[] args = { form };
				long timeMs = System.currentTimeMillis() - startTimeMs;
				auditAccessLogService.saveAuditAccessLogForWeb(httpRequest, functionName, args, throwable, timeMs);
			} catch(Exception exp){
									}
		}
		ModelAndView mav = new ModelAndView("/servlet/proofDocMatrixController/proofDocMatrix");
	    mav.addObject("proofDocumentDef", form);
	    model.addAttribute("fnSelected", Constants.HEADING_NIC_PROOF_DOC);
	    request.setAttribute("status", status, 1);		
		return "forward:"+"proofDocMatrix";
//	    return status;
	}
	
	private void logMessage(Exception ex) {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		  PrintStream ps = new PrintStream(baos);
		  ex.printStackTrace(ps);
		  logger.error(ex.getMessage()+" : \n" + new String(baos.toByteArray()));
	}

	
}
