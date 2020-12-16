package com.nec.asia.nic.investigation.controller;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.displaytag.tags.TableTagParameters;
import org.displaytag.util.ParamEncoder;
import org.hibernate.criterion.Order;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.ModelAndView;

import com.nec.asia.nic.comp.job.dto.InfoFamillyDto;
import com.nec.asia.nic.comp.trans.domain.BaseModelSingle;
import com.nec.asia.nic.comp.trans.domain.EppPerson;
import com.nec.asia.nic.comp.trans.domain.EppPersonAttchmnt;
import com.nec.asia.nic.comp.trans.domain.EppPersonFamily;
import com.nec.asia.nic.comp.trans.domain.NicDocumentData;
import com.nec.asia.nic.comp.trans.domain.NicRegistrationData;
import com.nec.asia.nic.comp.trans.domain.NicTransaction;
import com.nec.asia.nic.comp.trans.domain.NicTransactionAttachment;
import com.nec.asia.nic.comp.trans.domain.NicTransactionLost;
import com.nec.asia.nic.comp.trans.domain.NicUploadJob;
import com.nec.asia.nic.comp.trans.dto.AssignmentFilterAll;
import com.nec.asia.nic.comp.trans.dto.InfoPersonDto;
import com.nec.asia.nic.comp.trans.dto.NicUploadJobDto;
import com.nec.asia.nic.comp.trans.dto.RICBatchCardInfoDto;
import com.nec.asia.nic.comp.trans.service.DocumentDataService;
import com.nec.asia.nic.comp.trans.service.NicRegistrationDataService;
import com.nec.asia.nic.comp.trans.service.NicTransactionAttachmentService;
import com.nec.asia.nic.comp.trans.service.NicTransactionLostService;
import com.nec.asia.nic.comp.trans.service.NicTransactionService;
import com.nec.asia.nic.comp.trans.service.NicUploadJobService;
import com.nec.asia.nic.framework.PaginatedResult;
import com.nec.asia.nic.framework.admin.code.domain.CodeValues;
import com.nec.asia.nic.framework.admin.code.domain.Parameters;
import com.nec.asia.nic.framework.admin.code.domain.ParametersId;
import com.nec.asia.nic.framework.admin.code.service.CodesService;
import com.nec.asia.nic.framework.dao.DaoException;
import com.nec.asia.nic.investigation.dto.ConfirmLostDto;
import com.nec.asia.nic.investigation.dto.InfoPerson;
import com.nec.asia.nic.investigation.dto.InfoPersons;
import com.nec.asia.nic.util.Constants;
import com.nec.asia.nic.util.RegistrationConstants;
import com.nec.asia.nic.util.pagingUtil;
import com.nec.asia.nic.utils.HelperClass;
import com.nec.asia.nic.web.session.UserSession;


@Controller
@RequestMapping(value="/destroyPassport")
public class DestroyPassportController {
	
	private static final Logger logger = LoggerFactory
			.getLogger(InvestigationController.class);
	
	
	@Autowired
	private NicUploadJobService uploadJobService;	
	
	@Autowired
	private NicTransactionService nicTransactionService;
	
	@Autowired
	private CodesService codesService;
	
	@Autowired
	private DocumentDataService docDataService;
	
	@Autowired
	private NicRegistrationDataService regDataService;
	
	@Autowired
	private NicTransactionAttachmentService nicTransactionAttachmentService;
	
	@Autowired
	private NicTransactionLostService lostService;
	
	@ModelAttribute("reasonLost")
	public Map<String, String> reasonLost() {
		List<CodeValues> codeList = codesService.getAllCodeValues("REASON_LOST", null);
		Map<String, String> reasonLost = new LinkedHashMap<String, String>();		
		for (CodeValues ricCode : codeList) {
			reasonLost.put(ricCode.getId().getCodeValue(), ricCode.getCodeValueDesc());
		}
		return reasonLost;
	}
	
	@RequestMapping(value = "/showListPassport")
	public ModelAndView showListPassport(
			@ModelAttribute(value = "formData") InvestigationAssignmentData investigationAssignmentData,
			WebRequest request, HttpServletRequest httpRequest, ModelMap model) {
		logger.info("NIC Investigation");
		PaginatedResult<NicUploadJobDto> pr = null;
		//PaginatedResult<NicUploadJobDto> rejectedList = null;
		try {
			int pageNo = !StringUtils.isEmpty(request.getParameter("pageNo")) ? Integer.parseInt(request.getParameter("pageNo")) : 1;
			//int pageSize = 20;
			//int startIndex = 0;
			HttpSession session = httpRequest.getSession();
			UserSession userSession = (UserSession) session
					.getAttribute("userSession");
			logger.info("The Officer Id or User Id is ===============>>>>>>>>>>"
					+ userSession.getUserName());

			// Modified for pagination
//			ParametersId id = new ParametersId();
//			id.setParaName(Parameters.MAX_RECORDS_PER_PAGE);
//			id.setParaScope(Parameters.SCOPE_SYSTEM);
//			String tableId = "row";
//			String sortedElement = "codeId";
//			String order = InvestigationProcessController.DECENDING_ORDER;
//
//			try {
//				String sort = request
//						.getParameter(new ParamEncoder(tableId)
//								.encodeParameterName(TableTagParameters.PARAMETER_SORT));
//				if (StringUtils.isNotBlank(sort))
//					sortedElement = sort;
//			} catch (Exception ex) {
//				logMessage(ex);
//			}
//
//			try {
//				String requestOrder = request
//						.getParameter(new ParamEncoder(tableId)
//								.encodeParameterName(TableTagParameters.PARAMETER_ORDER));
//				if (StringUtils.isNotBlank(requestOrder))
//					order = requestOrder;
//			} catch (Exception ex) {
//				logMessage(ex);
//			}
//
//			Order hibernateOrder = Order.desc(sortedElement);
//			if (order.equalsIgnoreCase(InvestigationProcessController.DECENDING_ORDER)) {
//				hibernateOrder = Order.asc(sortedElement);
//			}
//
//			Parameters parameter = parametersService.findById(id);
//
//			if (parameter != null) {
//				String pageSizeDb = parameter.getParaShortValue();
//
//				if (StringUtils.isNotBlank(pageSizeDb)
//						&& StringUtils.isNumeric(pageSizeDb)) {
//					pageSize = Integer.parseInt(pageSizeDb);
//				}
//			}
			int pageSize = Constants.PAGE_SIZE_DEFAULT;
//			String pageNumber = request.getParameter((new ParamEncoder(tableId)
//					.encodeParameterName(TableTagParameters.PARAMETER_PAGE)));
//
//			if (StringUtils.isNotBlank(pageNumber)
//					&& StringUtils.isNumeric(pageNumber)) {
//				pageNo = Integer.parseInt(pageNumber);
//			}
			httpRequest.setAttribute("pendingCount",
					uploadJobService.getPendingInvestigationsCount());

			if (investigationAssignmentData == null) {
				investigationAssignmentData = new InvestigationAssignmentData();
			}
			
			String toDay = HelperClass.convertDateToString2(Calendar.getInstance().getTime());

			investigationAssignmentData.cleanUpStrings();
			// pr =
			// uploadJobService.findAllForInvestigationPagination(userSession.getUserName(),
			// pageNo, pageSize);
			
			
			pr = nicTransactionService.allPassportDestroy(investigationAssignmentData.getPassportNo(), new String[]{ Constants.CODE_STATUS_ISUANCE, Constants.CODE_STATUS_PERSONALIZED}, HelperClass.convertStringToString3(toDay), pageNo, pageSize);
			
			List<NicUploadJobDto> list = new ArrayList<NicUploadJobDto>();
			if (pr != null) {
				list = pr.getRows();
				model.addAttribute("total", pr.getTotal());
				if (list != null) {
//					for (NicUploadJobDto record : list) {
//						if(StringUtils.isEmpty(record.getInvestigationOfficerId())){
//							record.setFlagOfficerId("0");
//						}else {
//							if(record.getInvestigationOfficerId().equals(userSession.getUserId())){
//								record.setFlagOfficerId("0");
//							}else{
//								record.setFlagOfficerId("1");								
//							}
//						}
//						if(investigationAssignmentData.getTypeList().equals("3")){
//							record.setFlagOfficerId("1");		
//						}
//						String transactionId = record.getTransactionId();
//						if (transactionId != null) {
//							NicTransaction nicTransaction = nicTransactionService
//									.findById(transactionId);
//							if (nicTransaction != null) {
//								record.setDateOfApplication(nicTransaction
//										.getDateOfApplication());
//								record.setEstDateOfCollection(nicTransaction
//										.getEstDateOfCollection());
//								record.setPassportType(nicTransaction
//										.getPassportType());
//								record.setRegSiteCode(nicTransaction
//										.getRegSiteCode());
//								record.setPackageId(nicTransaction.getPackageID());
//								record.setNin(nicTransaction.getNin());
								// record.setPriority(nicTransaction.getPriority());
//								{
//									try {
//										//CodeValues codeValue = codesService.getCodeValueByIdName(Codes.PRIORITY,nicTransaction.getPriority().toString());
//										String priority = codesService.getCodeValueDescByIdName(RegistrationConstants.CODE_PRIORITY_CODE_ID, nicTransaction.getPriority().toString(), "");
//										if (priority != null) {
//											record.setPriorityString(priority);
//										} else {
//											record.setPriorityString("");
//										}
//									} catch (Exception e) {
//										record.setPriorityString(nicTransaction
//												.getPriority() == null ? null
//												: nicTransaction.getPriority()
//														.toString());
//									}
//								}
//								NicRegistrationData reg = nicTransaction.getNicRegistrationData();
//								if(reg != null){
//									record.setFullName(com.nec.asia.nic.comp.trans.utils.HelperClass.createFullName(reg.getSurnameFull(), reg.getMiddlenameFull(), reg.getFirstnameFull()));
//									String dateDob = HelperClass.convertDateToString2(reg.getDateOfBirth());
//									record.setDob(com.nec.asia.nic.utils.HelperClass.loadDateOfBirth(dateDob, reg.getStyleDob()));
//									//record.setDob(HelperClass.convertDateToString2(reg.getDateOfBirth()));
//									if(!StringUtils.isEmpty(reg.getGender())){
//										record.setGender(reg.getGender().equals("M") ? "Nam" : "Nữ");
//									}
//									String noiSinh = codesService.getCodeValueDescByIdName("DISTRICT", reg.getPlaceOfBirth(), "");
//									if(StringUtils.isNotEmpty(noiSinh)){
//										record.setPlaceOfBirth(noiSinh);								
//									}else{
//										record.setPlaceOfBirth(reg.getPlaceOfBirth());	
//									}
//									//record.setPlaceOfBirth(reg.getPlaceOfBirth());
//								}
//							}
//							
//						}
//					}
				} else {
					request.setAttribute("jobDetailsErrorMsg", "Không tìm thấy dữ liệu",
							0);
				}
				Map searchResultMap = new HashMap();
				searchResultMap.put("totalRecords", pr.getTotal());
				//searchResultMap.put("pageSize", pageSize);

				//model.addAttribute("jobList", list);
				//phúc edit
				int firstResults = (pageNo - 1) < 0 ? 0 : (pageNo - 1) * pageSize;
				model.addAttribute("pageSize", pageSize);
				model.addAttribute("pageNo", pageNo);
				model.addAttribute("totalPage", pagingUtil.totalPage(pr.getTotal(), pageSize));
				model.addAttribute("startIndex", pr.getTotal() != 0 ? firstResults + 1 : 0);
				model.addAttribute("totalRecord", pr.getTotal());
				model.addAttribute("endIndex", firstResults + pr.getRowSize());
				model.addAttribute("dsXuLyA", list);
				//end
				model.addAttribute("fnSelected",
						Constants.HEADING_NIC_INVESTIGATION);
				return new ModelAndView("main.huyhochieu.show",
						searchResultMap);
			} else {
				model.addAttribute("fnSelected",
						Constants.HEADING_NIC_INVESTIGATION);
				return new ModelAndView("main.huyhochieu.show", null);
			}
			// }
		} catch (Exception e) {

			e.printStackTrace();
			return null;
		}

	}
	
//	@RequestMapping(value="/runDestroy")
//	public ModelAndView distroyTransaction(@ModelAttribute(value = "formData") InvestigationAssignmentData investigationAssignmentData,
//			WebRequest request, HttpServletRequest httpRequest, ModelMap model) throws Exception {
//			ModelAndView mav = new ModelAndView("main.huyhochieu.show");
//			HttpSession session = httpRequest.getSession();
//			UserSession userSession = (UserSession) session.getAttribute("userSession");
//			PaginatedResult<NicUploadJobDto> pr = null;
//			try {
//				for(int i = 0; i < investigationAssignmentData.getSelectedJobIds().length; i++){
//					NicDocumentData nicData = docDataService.findByDocNumber(investigationAssignmentData.getSelectedJobIds()[i]).getModel();
//					if(nicData != null){
//						nicData.setStatus("NONE");
//						nicData.setActiveFlag(false);
//						nicData.setUpdateDatetime(Calendar.getInstance().getTime());
//						nicData.setUpdateBy(userSession.getUserId());
//						docDataService.saveOrUpdate(nicData);
//					}
//					//Thêm bản ghi vào db
//					NicRegistrationData regData = regDataService.findById(nicData.getId().getTransactionId());
//					NicTransaction txn = nicTransactionService.findById(nicData.getId().getTransactionId());
//					NicTransactionLost lost = new NicTransactionLost();
//					lost.setTransactionId(nicData.getId().getTransactionId());
//					lost.setName(HelperClass.createFullName(regData.getSurnameFull(), regData.getMiddlenameFull(), regData.getFirstnameFull()));
//					lost.setNin(txn.getNin());
//					lost.setDob(regData.getDateOfBirth());
//					lost.setPassportNo((nicData).getId().getPassportNo());
//					lost.setDateIssue(nicData.getDateOfIssue());
//					lost.setDateExrity(nicData.getDateOfExpiry());
//					lost.setPlaceIssue(null);
//					lost.setCreateDate(Calendar.getInstance().getTime());
//					lost.setCreateBy(userSession.getUserId());
//					lost.setReason(investigationAssignmentData.getReason());
//					lost.setReasonNote(investigationAssignmentData.getReasonNote());
//					lostService.saveOrUpdateLost(lost);									
//				}
//			} catch (Exception e) {
//				e.printStackTrace();
//			}	
//			String toDay = HelperClass.convertDateToString2(Calendar.getInstance().getTime());
//			int pageSize = Constants.PAGE_SIZE_DEFAULT;
//			pr = nicTransactionService.allPassportDestroy(investigationAssignmentData.getPassportNo(), new String[]{ Constants.CODE_STATUS_ISUANCE, Constants.CODE_STATUS_PERSONALIZED}, HelperClass.convertStringToString3(toDay), 1, pageSize);
//			if (pr == null) {
//				pr = new PaginatedResult<>(0, 1, new ArrayList<NicUploadJobDto>());
//			}
//			int firstResults = 0;
//			model.addAttribute("pageSize", pageSize);
//			model.addAttribute("pageNo", 1);
//			model.addAttribute("totalPage", pagingUtil.totalPage(pr.getTotal(), pageSize));
//			model.addAttribute("startIndex", pr.getTotal() != 0 ? firstResults + 1 : 0);
//			model.addAttribute("totalRecord", pr.getTotal());
//			model.addAttribute("endIndex", firstResults + pr.getRowSize());
//			model.addAttribute("dsXuLyA", pr.getRows());
//			//end
//			investigationAssignmentData.setReasonNote("");
//			model.addAttribute("fnSelected", Constants.HEADING_NIC_INVESTIGATION);
//			mav.addObject("formData", investigationAssignmentData);
//			return mav;
//	}
	
	@ResponseBody
	@RequestMapping(value="/detailPassport")
	public NicUploadJobDto detailPassport(@RequestParam String passportNo,
			WebRequest request, HttpServletRequest httpRequest, ModelMap model) throws DaoException, JAXBException {
			NicUploadJobDto dto = new NicUploadJobDto();
			NicDocumentData docData = docDataService.findByDocNumber(passportNo).getModel();

			if(docData != null){
				dto.setDateEprity(HelperClass.convertDateToString2(docData.getDateOfExpiry()));
				if(docData.getDateOfExpiry().before(Calendar.getInstance().getTime())){
					dto.setStageList("Hết hạn/Không hiệu lực");
				}else if(!docData.getDateOfExpiry().before(Calendar.getInstance().getTime())){
					dto.setStageList("Có hiệu lực");
				}
				List<NicTransactionAttachment> photoList = nicTransactionAttachmentService.findNicTransactionAttachments(
						docData.getId().getTransactionId() , NicTransactionAttachment.DOC_TYPE_PHOTO_CAPTURE, NicTransactionAttachment.DEFAULT_SERIAL_NO).getListModel();	
				if (CollectionUtils.isNotEmpty(photoList) && photoList.size() > 0) {
					String images = Base64.encodeBase64String(photoList.get(0).getDocData());
					dto.setPhotoStr("<img src=\'data:image/jpg;base64,"+ images +"' class=\"img-border doGetAJpgSafeVersion\" height=\"130\" width=\"90\" />");
				}else{
					dto.setPhotoStr("<img class=\"img-border\" height=\"130\" width=\"90\" src=\"/eppcentral/resources/images/No_Image.jpg\">");
				}
				NicTransaction txn = nicTransactionService.findById(docData.getId().getTransactionId());
				List<InfoPersonDto> listFormat = new ArrayList<InfoPersonDto>();
				if(StringUtils.isNotEmpty(txn.getInfoPerson())){
					JAXBContext jaxbContext = JAXBContext.newInstance(InfoPersons.class);
					Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
					StringReader reader = new StringReader(txn.getInfoPerson());
					InfoPersons persons = (InfoPersons) unmarshaller.unmarshal(reader);
					List<InfoPerson> listPerson = null;
					if(persons != null){
						listPerson = persons.getInfoPersons();						
					}
					listFormat = this.formatDatatoVN(listPerson);								
				}
				dto.setListInfo(listFormat);
			}
			
		return dto;
	}
	
	List<InfoPersonDto> formatDatatoVN(List<InfoPerson> list){
		List<InfoPersonDto> listInfo = new ArrayList<InfoPersonDto>();
		if(list != null && list.size() > 0){
			int i = 0;
			for(InfoPerson info : list){
				i++;
				InfoPersonDto dto = new InfoPersonDto();
				dto.setStt(i);
				dto.setGender(info.getGender().equals("M") ? "Nam" : "Nữ");
				dto.setDateOfBirth(HelperClass.convertStringToStringTk(info.getDateOfBirth(), 0));
//				String pob = codesService.getCodeValueDescByIdName(RegistrationConstants.CODE_ID_BirthPlace, info.getPlaceOfBirthId(), "");
//				if(StringUtils.isNotEmpty(pob)){
//					info.setPlaceOfBirthId(pob);
//				}
				String noiSinh = codesService.getCodeValueDescByIdName("DISTRICT", info.getPlaceOfBirthId(), "");
				if(StringUtils.isNotEmpty(noiSinh)){
					dto.setPlaceOfBirthId(noiSinh);								
				}else{
					dto.setPlaceOfBirthId(info.getPlaceOfBirthId());	
				}
				dto.setName(info.getName());
				listInfo.add(dto);
			}
		}
		return listInfo;
	}
}
