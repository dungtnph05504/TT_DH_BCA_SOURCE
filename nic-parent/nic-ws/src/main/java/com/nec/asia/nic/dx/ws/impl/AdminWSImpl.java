package com.nec.asia.nic.dx.ws.impl;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import service.perso.model.FunctionsJson;
import service.perso.model.RolesJson;
import service.perso.model.UsersJson;
import service.perso.model.WorkstationsJson;

import com.nec.asia.nic.comp.admin.code.utils.AdminDTOMapper;
import com.nec.asia.nic.comp.admin.code.utils.CodeRetrievalHelper;
import com.nec.asia.nic.comp.decisionManager.service.BusinessListService;
import com.nec.asia.nic.comp.decisionManager.service.DecisionManagerService;
import com.nec.asia.nic.comp.officalNation.service.OfficalNationService;
import com.nec.asia.nic.comp.signerGovs.domain.SignerGovs;
import com.nec.asia.nic.comp.signerGovs.service.SignerGovsService;
import com.nec.asia.nic.comp.trans.domain.DetailRecieptFee;
import com.nec.asia.nic.comp.trans.domain.EppDetailReciept;
import com.nec.asia.nic.comp.trans.domain.EppRecieptManager;
import com.nec.asia.nic.comp.trans.domain.FeeRecieptPayment;
import com.nec.asia.nic.comp.trans.domain.NicBusinessList;
import com.nec.asia.nic.comp.trans.domain.NicDecisionManager;
import com.nec.asia.nic.comp.trans.domain.NicOfficalNation;
import com.nec.asia.nic.comp.trans.service.DetailRecieptFeeService;
import com.nec.asia.nic.comp.trans.service.DetailRecieptService;
import com.nec.asia.nic.comp.trans.service.FeeRecieptPaymentService;
import com.nec.asia.nic.comp.trans.service.RecieptManagerService;
import com.nec.asia.nic.comp.trans.service.exception.TransactionServiceException;
import com.nec.asia.nic.dx.admin.ChangeUserPassword;
import com.nec.asia.nic.dx.admin.ChangeUserPasswordResponse;
import com.nec.asia.nic.dx.admin.ConfigurationType;
import com.nec.asia.nic.dx.admin.FunctionDto;
import com.nec.asia.nic.dx.admin.GetAuthorizedFunctions;
import com.nec.asia.nic.dx.admin.GetAuthorizedFunctionsResponse;
import com.nec.asia.nic.dx.admin.GetConfigurations;
import com.nec.asia.nic.dx.admin.GetConfigurationsResponse;
import com.nec.asia.nic.dx.admin.GetPaymentMatrix;
import com.nec.asia.nic.dx.admin.GetPaymentMatrixAllRequest;
import com.nec.asia.nic.dx.admin.GetPaymentMatrixAllResponse;
import com.nec.asia.nic.dx.admin.GetPaymentMatrixResponse;
import com.nec.asia.nic.dx.admin.GetProofDocumentMatrix;
import com.nec.asia.nic.dx.admin.GetProofDocumentMatrixResponse;
import com.nec.asia.nic.dx.admin.GetRecieptAllRequest;
import com.nec.asia.nic.dx.admin.MappingAuthenData;
import com.nec.asia.nic.dx.admin.OfficalNationTransaction;
import com.nec.asia.nic.dx.admin.ParaSignerCompare;
import com.nec.asia.nic.dx.admin.ParaSignerCompareRequest;
import com.nec.asia.nic.dx.admin.ParaSignerCompareResponse;
import com.nec.asia.nic.dx.admin.Payment;
import com.nec.asia.nic.dx.admin.ProofDocument;
import com.nec.asia.nic.dx.admin.UserData;
import com.nec.asia.nic.dx.admin.UserRequest;
import com.nec.asia.nic.dx.trans.MainTransaction;
import com.nec.asia.nic.dx.ws.AdminWS;
import com.nec.asia.nic.dx.ws.Constant;
import com.nec.asia.nic.dx.ws.FaultException;
import com.nec.asia.nic.dx.ws.StatusCode;
import com.nec.asia.nic.framework.admin.code.domain.PaymentDef;
import com.nec.asia.nic.framework.admin.code.domain.PaymentDefId;
import com.nec.asia.nic.framework.admin.code.domain.ProofDocumentDef;
import com.nec.asia.nic.framework.admin.code.service.PaymentDefService;
import com.nec.asia.nic.framework.admin.code.service.ProofDocumentDefService;
import com.nec.asia.nic.framework.admin.rbac.domain.Functions;
import com.nec.asia.nic.framework.admin.rbac.domain.Roles;
import com.nec.asia.nic.framework.admin.rbac.domain.Users;
import com.nec.asia.nic.framework.admin.rbac.domain.Workstations;
import com.nec.asia.nic.framework.admin.rbac.service.FunctionService;
import com.nec.asia.nic.framework.admin.rbac.service.RoleService;
import com.nec.asia.nic.framework.admin.rbac.service.UserService;
import com.nec.asia.nic.framework.admin.rbac.service.WorkStationService;
import com.nec.asia.nic.framework.report.service.AuditAccessLogService;
import com.nec.asia.nic.framework.security.ldap.exceptions.ChangePasswordException;
import com.nec.asia.nic.framework.security.ldap.exceptions.IncorrectPasswordException;
import com.nec.asia.nic.framework.security.ldap.exceptions.UserNotFoundException;
import com.nec.asia.nic.framework.security.service.AuthenticationService;
import com.nec.asia.nic.framework.security.service.SecurityService;
import com.nec.asia.nic.util.MiscXmlConvertor;
import com.nec.asia.nic.utils.DateUtil;
import com.nec.asia.nic.web.session.UserSession;

/*
 * Modification History:
 * 1 Mar 2016 (khang): init class
 * 15 Jun 2017 (chris): code merge
 */
public class AdminWSImpl implements AdminWS {
	private static Logger logger = LoggerFactory.getLogger(AdminWSImpl.class);
	private final String serviceName = "AdminWS";
	
	@Autowired
	@Qualifier("authenticationService")
	private AuthenticationService authenticationService;

	@Autowired
	@Qualifier("securityService")
	private SecurityService securityService;
	
	@Autowired
	@Qualifier(value="userService")
	private UserService userService;
	
	@Autowired
	private ProofDocumentDefService proofDocumentDefService = null;
	
	@Autowired
	private PaymentDefService paymentDefService = null;
	
	@Autowired
	private AdminDTOMapper mapper = null;
	
	@Autowired
	private AuditAccessLogService auditAccessLogService;
	
	@Autowired
	private CodeRetrievalHelper codeRetrievalHelper = null;
	
	@Autowired
	private OfficalNationService officalNationService;
	
	@Autowired
	private DecisionManagerService decisionManagerService;
	
	@Autowired
	private BusinessListService businessListService;
	
	@Autowired
	private SignerGovsService signerGovsService;
	
	@Autowired
	private RoleService roleService;
	
	@Autowired
	private FunctionService functionService; 
	
	@Autowired
	private WorkStationService workStationService;
	
	@Autowired
	private DetailRecieptFeeService detailRecieptFeeService;
	
	@Autowired
	private DetailRecieptService detailRecieptService;
	
	@Autowired
	private FeeRecieptPaymentService feeRecieptPaymentService;
	
	@Autowired
	private RecieptManagerService recieptManagerService;
	
	@Override
	public GetAuthorizedFunctionsResponse getAuthorizedFunctions(GetAuthorizedFunctions input) throws FaultException {
		if (input == null)
			throw new FaultException("Input parameter cannot be null");
		logger.info("AdminWSImpl.getAuthorizedFunctions(): begin");
		logger.info("getAuthorizedFunctions() userId: {}, workstationId: {}", input.getUserID() , input.getWorkstationID() );
		GetAuthorizedFunctionsResponse functionResponse = null;
		try {

			logger.debug("before authenticationService.login({},{})", input.getUserID() , input.getWorkstationID() );
			UserSession userSession = authenticationService.login(input.getUserID(), input.getPassword(), input.getWorkstationID());
			logger.debug("after authenticationService.login() : " + (userSession != null));
			if (userSession != null) {
				functionResponse = new GetAuthorizedFunctionsResponse();
				List<String> functionList = securityService.getFunctions(input.getUserID(), input.getWorkstationID());
				if (CollectionUtils.isNotEmpty(functionList)) {
					for (String functionId : functionList) {
						FunctionDto functionDto = new FunctionDto();
						functionDto.setFunctionID(functionId);
						functionDto.setFunctionURL("");
						functionResponse.getFunctions().add(functionDto);
					}
				}
				logger.debug("getAuthorizedFunctions() result.functions.size={}", new Object[] { functionResponse.getFunctions().size() } );
			} else {
				throw new FaultException("Invalid Username or Password");
			}

		} catch (Exception e) {
			logger.error("Exception Encountered in getAuthorizedFunctions()", e);
			throw new FaultException("Exception encountered in getAuthorizedFunctions():" + e.getMessage(), e);
		}
		logger.info("AdminWSImpl.getAuthorizedFunctions(): end");
		return functionResponse;
	}

	private StatusCode getStatusCode(String status){
		if (StringUtils.isEmpty(status)){
			return null;
		}else if (status.equalsIgnoreCase(StatusCode.COMPLETED.getKey())){
			return StatusCode.COMPLETED;
		}else if (status.equalsIgnoreCase(StatusCode.MANDATORY.getKey())){
			return StatusCode.MANDATORY;
		}else if (status.equalsIgnoreCase(StatusCode.INVALID.getKey())){
			return StatusCode.INVALID;
		}else if (status.equalsIgnoreCase(StatusCode.MAX_LENGTH.getKey())){
			return StatusCode.MAX_LENGTH;
		}else if (status.equalsIgnoreCase(StatusCode.USERACCOUNT_INVALID.getKey())){
			return StatusCode.USERACCOUNT_INVALID;
		}else if (status.equalsIgnoreCase(StatusCode.WKSTN_INVALID.getKey())){
			return StatusCode.WKSTN_INVALID;
		}else if (status.equalsIgnoreCase(StatusCode.USERACCOUNT_INACTIVE.getKey())){
			return StatusCode.USERACCOUNT_INACTIVE;
		}else if (status.equalsIgnoreCase(StatusCode.SITECODE_MISMATCH.getKey())){
			return StatusCode.SITECODE_MISMATCH;
		}else if (status.equalsIgnoreCase(StatusCode.PASSWORD_EXPIRED.getKey())){
			return StatusCode.PASSWORD_EXPIRED;
		}else{
			return StatusCode.ERROR;
		}
	}
	
	//@Override
	public GetProofDocumentMatrixResponse getProofDocumentMatrix(GetProofDocumentMatrix input) throws FaultException {
		logger.info("AdminWSImpl.getProofDocumentMatrix(): begin");
		
		GetProofDocumentMatrixResponse response = null;
		try {
			response = new GetProofDocumentMatrixResponse();
			String transactionType = input.getTransactionType();
			String transactionSubtype = input.getTransactionSubtype();
			logger.info("getProofDocumentMatrix() type:{}, subtype:{}", transactionType, transactionSubtype);
			List<ProofDocumentDef> recordList = proofDocumentDefService.findAllByTransType(transactionType, transactionSubtype);
			for (ProofDocumentDef proofDocumentRefDBO : recordList) {
				ProofDocument proofDocumentDefDTO = mapper.parseProofDocumentDefDTO(proofDocumentRefDBO);
				response.getProofDocuments().add(proofDocumentDefDTO);
			}
			logger.debug("getProofDocumentMatrix() result.proofDocuments.size={}", new Object[] { response.getProofDocuments().size() } );
		} catch (Exception e) {
			logger.error("Exception Encountered in getPaymentMatrix()", e);
			throw new FaultException("Exception encountered in getPaymentMatrix():"+e.getMessage(), e);
		}
		logger.info("AdminWSImpl.getProofDocumentMatrix(): end");
		return response;
	}

	//@Override
	public GetPaymentMatrixResponse getPaymentMatrix(GetPaymentMatrix input) throws FaultException {
		logger.info("AdminWSImpl.getPaymentMatrix(): begin");
		GetPaymentMatrixResponse response = null;
		try {
			response = new GetPaymentMatrixResponse();
			String transactionType = input.getTransactionType();
			String transactionSubtype = input.getTransactionSubtype();
			Integer noOfTimeLost = input.getNoOfTimeLost();
			logger.info("getPaymentMatrix() type:{}, subtype:{}, noCardLost:{}.", new Object[]{transactionType, transactionSubtype, noOfTimeLost});
			if (StringUtils.isBlank(transactionType) || StringUtils.isBlank(transactionSubtype) || noOfTimeLost==null) {
				throw new RuntimeException("Transaction type / subtype / noOfTimeLost cannot be null.");
			}
			PaymentDef paymentRefDBO = paymentDefService.findById(new PaymentDefId(transactionType, transactionSubtype, noOfTimeLost.intValue()));
			if (paymentRefDBO==null) {
				if (StringUtils.equals(transactionType, Constant.TRANSACTION_TYPE_REPLACEMENT)) {
					paymentRefDBO = paymentDefService.getPaymentDefForReplacement();
				}
			}
			if (paymentRefDBO!=null) {
				double reduceFeeAmount = paymentDefService.calculateReduceRateFeeAmount(paymentRefDBO);
				Payment paymentDefDTO = mapper.parsePaymentDefDTO(paymentRefDBO);
				paymentDefDTO.setReduceRateFeeAmount(reduceFeeAmount);
				response.getPayments().add(paymentDefDTO);
			} 
			logger.debug("getPaymentMatrix() result.payments.size={}", new Object[] { response.getPayments().size() } );
		} catch (Exception e) {
			logger.error("Exception Encountered in getPaymentMatrix()", e);
			throw new FaultException("Exception encountered in getPaymentMatrix():"+e.getMessage(), e);
		}
		logger.info("AdminWSImpl.getPaymentMatrix(): end");
		return response;
	}

	//@Override
	public ChangeUserPasswordResponse changeUserPassword(ChangeUserPassword input) {
		logger.info("AdminWSImpl.changeUserPassword(): begin");
		ChangeUserPasswordResponse response = new ChangeUserPasswordResponse();
		String userId = input.getUserId();
		String currentPwd = input.getCurrentPwd();
		String newPwd = input.getNewPwd();
		String officerId = input.getOfficerId();
		String workstationId = input.getWorkstationId();
		if (StringUtils.isNotBlank(input.getSiteCode())) {
			if (StringUtils.isNotBlank(workstationId) && StringUtils.length(workstationId)+StringUtils.length(input.getSiteCode())<64) {
				workstationId += "@"+input.getSiteCode();
			}
		}
		logger.info("updateUserPassword() {}, {}", new Object[] { userId, workstationId } );
		StatusCode statusCode = null;

		String functionName = "changePwd";
		Throwable throwable = null;
		long startTimeMs = System.currentTimeMillis();
		boolean valid = true;
		try {
			if (StringUtils.isBlank(userId)) {
				valid = false;
				response.setStatusCode(StatusCode.ADMWS_CP_MANDATORY_FIELD_ERROR.getKey());
				response.setStatusDescription(StatusCode.ADMWS_CP_MANDATORY_FIELD_ERROR.getDesc()+" -- userId");
			} else if (StringUtils.isBlank(currentPwd)) {
				valid = false;
				response.setStatusCode(StatusCode.ADMWS_CP_MANDATORY_FIELD_ERROR.getKey());
				response.setStatusDescription(StatusCode.ADMWS_CP_MANDATORY_FIELD_ERROR.getDesc()+" -- currentPwd");
			} else if (StringUtils.isBlank(newPwd)) {
				valid = false;
				response.setStatusCode(StatusCode.ADMWS_CP_MANDATORY_FIELD_ERROR.getKey());
				response.setStatusDescription(StatusCode.ADMWS_CP_MANDATORY_FIELD_ERROR.getDesc()+" -- newPwd");
			} else if (StringUtils.length(userId)>64) {
				valid = false;
				response.setStatusCode(StatusCode.ADMWS_CP_FIELD_LENGTH_EXCEEDED.getKey());
				response.setStatusDescription(StatusCode.ADMWS_CP_FIELD_LENGTH_EXCEEDED.getDesc()+" -- userId");
			}
			
			if (valid) {
				userService.changePassword(userId, currentPwd , newPwd, officerId, workstationId);
				statusCode = StatusCode.ADMWS_CP_OPERATION_COMPLETED;
			} 
		} catch (UserNotFoundException e) {
			statusCode = StatusCode.ADMWS_CP_INVALID_USERID;
			//throwable = e;
		} catch (IncorrectPasswordException e) {
			statusCode = StatusCode.ADMWS_CP_INVALID_CURRENT_PASSWORD;
			//throwable = e; 
		} catch (ChangePasswordException e) {
			statusCode = StatusCode.ADMWS_CP_INVALID_NEW_PASSWORD;
			//throwable = e; 
		} catch (Exception e) {
			statusCode = StatusCode.ADMWS_CP_UNEXPECTED_ERROR;
			throwable = e; 
		}
		finally {
			if (statusCode!=null) {
				logger.debug("updateUserPassword() result={}, {}", new Object[] { statusCode.getKey(), statusCode.getDesc() } );
				response.setStatusCode(statusCode.getKey());
				response.setStatusDescription(statusCode.getDesc());
			}
			try {
				Object[] args = { userId };
				Object[] responses = { response };
				boolean success = StatusCode.ADMWS_CP_OPERATION_COMPLETED.getKey().equals(response.getStatusCode());
				long timeMs = System.currentTimeMillis() - startTimeMs;
				auditAccessLogService.saveAuditAccessLogForWS(serviceName, functionName, officerId, workstationId, args, responses, throwable, timeMs, success);
			} catch(Exception exp) {
			}
		}
		logger.info("AdminWSImpl.changeUserPassword(): end");
		return response;
	}
	
	//@Override
	public GetConfigurationsResponse getConfigurations(GetConfigurations input)
			throws FaultException {
		logger.info("AdminWSImpl.getConfigurations(): begin");
		GetConfigurationsResponse configResponse = null;
		try {
			logger.info("getConfigurations() retrievalType:{}, configType:{}", input.getRetrievalType(), input.getConfigType());
			String resultXml = null;
			if (ConfigurationType.CODE.equals(input.getConfigType())) {
				resultXml = codeRetrievalHelper.getCodeXml();
			} else if (ConfigurationType.CODE_VALUE.equals(input.getConfigType())) {
				resultXml = codeRetrievalHelper.getCodeValueXml();
			} else if (ConfigurationType.PARAMETER.equals(input.getConfigType())) {
				resultXml = codeRetrievalHelper.getParameterXml();
			} else {
				logger.warn("getConfigurations() configType[{}] is not supported.", input.getRetrievalType(), input.getConfigType());
			}
			
			configResponse = new GetConfigurationsResponse();
			configResponse.setConfigType(input.getConfigType());
			configResponse.setConfigDataXml(resultXml);
			
			logger.debug("getConfigurations() result.configDataXml.length={}", new Object[] { configResponse.getConfigDataXml()!=null ? configResponse.getConfigDataXml().length() : 0 } );
		} catch (Exception e) {
			logger.error("Exception Encountered in getConfigurations()", e);
			throw new FaultException("Exception encountered in getConfigurations():"+e.getMessage(), e);
		}
		logger.info("AdminWSImpl.getConfigurations(): end");
		return configResponse;
	}
	
	public String uploadNationOffical(OfficalNationTransaction mainOfficalNation) throws FaultException {
		String result = Constant.TRANSACTION_SUCCESS;
		try{
		//Kiểm tra dữ liệu hợp lệ
		if(mainOfficalNation != null){
			String missingData = "Missing data: ";
			
			if(mainOfficalNation.getDecisionNumber() == "" || mainOfficalNation.getDecisionNumber() == null){
				missingData += "[Descision number] ";
			}
			if(mainOfficalNation.getSignDate() == null){
				missingData += "[Sign Date] ";
			}
			if(mainOfficalNation.getCompetentAuthorities() == "" || mainOfficalNation.getCompetentAuthorities() == null){
				missingData += "[Competent Authorities] ";
			}
			if(mainOfficalNation.getCompetentAuthoritiesEng() == "" || mainOfficalNation.getCompetentAuthoritiesEng() == null){
				missingData += "[Competent Authorities Eng] ";
			}
			if(mainOfficalNation.getPurpose() == "" || mainOfficalNation.getPurpose() == null){
				missingData += "[Purpose] ";
			}
			if(mainOfficalNation.getTimePlan() == "" || mainOfficalNation.getTimePlan() == null){
				missingData += "[TimePlan] ";
			}
			if(mainOfficalNation.getTripCost() == "" || mainOfficalNation.getTripCost() == null){
				missingData += "[Trip Cost] ";
			}
			if(mainOfficalNation.getCountryPlan() == "" || mainOfficalNation.getCountryPlan() == null){
				missingData += "[Country Plan] ";
			}
			if(mainOfficalNation.getSerial() <= 0 || mainOfficalNation.getSerial() == null){
				missingData += "[Serial] ";
			}
			if(mainOfficalNation.getFullname() == "" || mainOfficalNation.getFullname() == null){
				missingData += "[Fullname] ";
			}
			
			if(mainOfficalNation.getDOB() == null){
				missingData += "[DOB] ";
			}
			if(mainOfficalNation.getPOB() == "" || mainOfficalNation.getPOB() == null){
				missingData += "[POB] ";
			}
			if(mainOfficalNation.getAddress() == "" || mainOfficalNation.getAddress() == null){
				missingData += "[Address] ";
			}
			
			if(mainOfficalNation.getAgency() == "" || mainOfficalNation.getAgency() == null){
				missingData += "[Agency] ";
			}
			
			if(mainOfficalNation.getPosition() == "" || mainOfficalNation.getPosition() == null){
				missingData += "[Position] ";
			}
			
			if(mainOfficalNation.getPositionEng() == "" || mainOfficalNation.getPositionEng() == null){
				missingData += "[Position Eng] ";
			}
			
			if(mainOfficalNation.getOfficalNationNo() == "" || mainOfficalNation.getOfficalNationNo() == null){
				missingData += "[Offical Nation No] ";
			}
			
			if(missingData != "")
				return missingData;
		}
		else
			return "error: Null data";
	

		//Kiểm tra dữ liệu Quyết định đã tồn tại chưa
		NicDecisionManager ndm = decisionManagerService.findDecisionManagerByCode(mainOfficalNation.getDecisionNumber());
		if(ndm == null){
			NicDecisionManager addNDM = new NicDecisionManager();
			addNDM.setDecisionNumber(mainOfficalNation.getDecisionNumber());
			addNDM.setCompetentAuthorities(mainOfficalNation.getCompetentAuthorities());
			addNDM.setCompetentAuthoritiesEng(mainOfficalNation.getCompetentAuthoritiesEng());
			addNDM.setCountryPlan(mainOfficalNation.getCountryPlan());
			addNDM.setSigner(mainOfficalNation.getSigner());
			addNDM.setSignDate(mainOfficalNation.getSignDate());
			addNDM.setDescription(mainOfficalNation.getDescriptionQD());
			addNDM.setData(mainOfficalNation.getData());
			addNDM.setPurpose(mainOfficalNation.getPurpose());
			addNDM.setTimeplan(mainOfficalNation.getTimePlan());
			addNDM.setTimeLasts(mainOfficalNation.getTimeLasts());
			addNDM.setTripCost(mainOfficalNation.getTripCost());
			addNDM.setInvitingAgency(mainOfficalNation.getInvitingAgency());
			addNDM.setTransitNation(mainOfficalNation.getTransitNation());
			addNDM.setCreateBy("SYSTEM");
			addNDM.setCreateDate(new Date());
			decisionManagerService.createDecisionManager(addNDM);
			logger.info("Create new Decision Manager: " + mainOfficalNation.getDecisionNumber());
			
			ndm = decisionManagerService.findDecisionManagerByCode(mainOfficalNation.getDecisionNumber());
			if(ndm == null)
				return "error: Create Decision Manager";
		}
		//Kiểm tra dữ liệu người trong quyêt định tồn tại chưa
		NicBusinessList nbl = businessListService.findBySerial(mainOfficalNation.getSerial(), mainOfficalNation.getDecisionNumber());
		if(nbl == null){
			NicBusinessList addNBL = new NicBusinessList();
			addNBL.setAddress(mainOfficalNation.getAddress());
			addNBL.setAddressAgency(mainOfficalNation.getAddressAgency());
			addNBL.setAgency(mainOfficalNation.getAgency());
			addNBL.setCurb(mainOfficalNation.getCurb());
			addNBL.setDecisionNumber(mainOfficalNation.getDecisionNumber());
			addNBL.setDescription(mainOfficalNation.getDescription());
			addNBL.setDob(mainOfficalNation.getDOB());
			addNBL.setFullname(mainOfficalNation.getFullname());
			addNBL.setGender(mainOfficalNation.getGender());
			addNBL.setJaw(mainOfficalNation.getJaw());
			addNBL.setPhone(mainOfficalNation.getPhone());
			addNBL.setPosition(mainOfficalNation.getPosition());
			addNBL.setPositionEng(mainOfficalNation.getPositionEng());
			addNBL.setRank(mainOfficalNation.getRank());
			addNBL.setSerial(mainOfficalNation.getSerial());
			addNBL.setType(mainOfficalNation.getType());
			addNBL.setCreateBy("SYSTEM");
			addNBL.setCreateDate(new Date());
			businessListService.createBusinessList(addNBL);
			logger.info("Create new Bussiness: " + mainOfficalNation.getDecisionNumber() + "| STT: " + mainOfficalNation.getSerial());
			
			nbl = businessListService.findBySerial(mainOfficalNation.getSerial(), mainOfficalNation.getDecisionNumber());
			if(nbl == null)
				return "error: Create Business List";
		}
		
		//Thêm dữ liệu Công hàm
		NicOfficalNation non = new NicOfficalNation();
		non.setBusinessID(nbl.getId());
		non.setCreateBy("SYSTEM");
		non.setCreateDate(new Date());
		non.setDecisionNumber(mainOfficalNation.getDecisionNumber());
		non.setFullname(mainOfficalNation.getFullname());
		non.setNationCode(mainOfficalNation.getNationCode());
		non.setOfficalNationNo(mainOfficalNation.getOfficalNationNo());
		non.setPassportNo(mainOfficalNation.getPassportNo());
		non.setPassportType(mainOfficalNation.getPassportType());
		non.setPassportExp(mainOfficalNation.getPassportExpireDate());
		non.setPassportIss(mainOfficalNation.getPassportIssueDate());
		non.setPassportIssuePlace(mainOfficalNation.getPassportIssuePlace());
		non.setStatus("REGISTRATION");
		non.setVisaNo(mainOfficalNation.getVisaNo());
		officalNationService.createOfficalNation(non);
		}
		catch (Exception e) {
				result = "error"+": "+e.getMessage();
		}
		return result;
	}
	
	//@Override
	public ParaSignerCompareResponse getParaSignerCompare(ParaSignerCompareRequest input) throws FaultException {
			logger.info("AdminWSImpl.getParaSignerCompare(): begin");
			ParaSignerCompareResponse response = null;
			try {
				response = new ParaSignerCompareResponse();
				String type = input.getType();
				
				logger.info("getParaSignerCompare() type:{}", new Object[]{type});
				if (StringUtils.isBlank(type)) {
					throw new RuntimeException("Type cannot be null.");
				}
				List<SignerGovs> signerGovs = signerGovsService.findListSignerByCode("");
				if (signerGovs != null && signerGovs.size()  > 0) {
					
					for(SignerGovs item : signerGovs){
						ParaSignerCompare obj = new ParaSignerCompare();
						obj.setActive(item.getActive());
						obj.setCodeGovernment(item.getCodeGovernment());
						obj.setCodeSigner(item.getCodeSigner());
						obj.setCreateBy(item.getCreateBy());
						obj.setCreateDate(item.getCreateDate());
						obj.setDescription(item.getDescription());
						obj.setDocData(item.getDocData());
						obj.setNameSigner(item.getNameSigner());
						obj.setUpdateBy(item.getUpdateBy());
						obj.setUpdateDate(item.getUpdateDate());
						if(input.getType().equals("ALL") || input.getType().equals(item.getActive()))
							response.getParaSignerCompares().add(obj);
					}
				}
				else {
					logger.info("AdminWSImpl.getParaSignerCompare(): not found list signerGovs");
				} 
				logger.debug("getParaSignerCompare() result.payments.size={}", new Object[] { response.getParaSignerCompares().size() } );
			} catch (Exception e) {
				logger.error("Exception Encountered in getParaSignerCompare()", e);
				throw new FaultException("Exception encountered in getParaSignerCompare():"+e.getMessage(), e);
			}
			logger.info("AdminWSImpl.getParaSignerCompare(): end");
			return response;
	}
	
	//@Override
	public GetPaymentMatrixAllResponse getPaymentMatrixAll(GetPaymentMatrixAllRequest input) throws FaultException {
			logger.info("AdminWSImpl.getPaymentMatrixAll(): begin");
			GetPaymentMatrixAllResponse response = null;
			try {
				response = new GetPaymentMatrixAllResponse();
				String type = input.getType();
				logger.info("getPaymentMatrixAll() type:{}.", new Object[]{type});
				
				List<PaymentDef> paymentRefDBO  = paymentDefService.findAll();
				
				if (paymentRefDBO!=null && paymentRefDBO.size() > 0) {
					for(PaymentDef item : paymentRefDBO){
						double reduceFeeAmount = paymentDefService.calculateReduceRateFeeAmount(item);
						Payment paymentDefDTO = mapper.parsePaymentDefDTO(item);
						paymentDefDTO.setReduceRateFeeAmount(reduceFeeAmount);
						response.getPayments().add(paymentDefDTO);
					}
					
				} 
				logger.debug("getPaymentMatrixAll() result.payments.size={}", new Object[] { response.getPayments().size() } );
			} catch (Exception e) {
				logger.error("Exception Encountered in getPaymentMatrixAll()", e);
				throw new FaultException("Exception encountered in getPaymentMatrixAll():"+e.getMessage(), e);
			}
			logger.info("AdminWSImpl.getPaymentMatrixAll(): end");
			return response;
	}
	
	public String findResultOfficalNation(String officalNationNo){
		String result = "";
		//Tìm theo số công hàm
		if(officalNationNo != ""){
			NicOfficalNation non = officalNationService.findOfficalNationByCode(officalNationNo);
			if(non == null)
				return "error: Not found";
			else
			{
				result = "status: " + non.getStatus();
				if(non.getStatus() == "VERIFY"){
					result += " | VisaNo: " + non.getVisaNo();
				}
			}
		}
		return result;
	}
	
	public String officalNationUpdateStatus(String officalNationNo){
		String result = Constant.TRANSACTION_SUCCESS;
		try{
			NicOfficalNation non = officalNationService.findOfficalNationByCode(officalNationNo);
			if(non != null){
				non.setStatus("RECIVED");
				officalNationService.saveOrUpdate(non);
			}
			else
				return "error: not found";
		}catch (Exception e) {
			result = "error"+": "+e.getMessage();
		}
		return result;
	}
	
	public String officalNationUpdateStatusFail(String officalNationNo){
		String result = Constant.TRANSACTION_SUCCESS;
		try{
			NicOfficalNation non = officalNationService.findOfficalNationByCode(officalNationNo);
			if(non != null){
				non.setStatus("REFUSE");
				officalNationService.saveOrUpdate(non);
			}
			else
				return "error: not found";
		}catch (Exception e) {
			result = "error"+": "+e.getMessage();
		}
		return result;
	}
	
	@Override
	public UserData getAuthenUser(UserRequest request) throws FaultException {
		UserData result = new UserData();
		try{
			List<Users> lstU = userService.getListUserBySiteCode(request.getSiteCode());
			List<Roles> lstR = roleService.findBySystemId(request.getSystemId());
			List<Functions> lstF = functionService.findBySystemId(request.getSystemId());
			List<Workstations> lstW = workStationService.findBySiteCode(request.getSiteCode());
			
			if(lstF != null && lstF.size() > 0){
				for(Functions f: lstF){
					com.nec.asia.nic.dx.admin.Functions fj = mapperFunction(f);
					result.getLstFunc().add(fj);
				}
				
			}
			if(lstU != null && lstU.size() > 0){
				for(Users u : lstU){
					com.nec.asia.nic.dx.admin.Users uj = mapperUser(u);
					result.getLstUser().add(uj);
				}
				
			}
			if(lstW != null && lstW.size() > 0){
				for(Workstations w : lstW){
					com.nec.asia.nic.dx.admin.Workstations wj = mapperWkst(w);
					result.getLstWkst().add(wj);
				}
			}
			if(lstR != null && lstR.size() > 0){
				for(Roles r : lstR){
					com.nec.asia.nic.dx.admin.Roles rj = mapperRole(r);
					result.getLstRole().add(rj);
				}
			}
		}
		catch(Exception e){
			e.printStackTrace();
		}
		return result;
	}
	
	@Override
	public MappingAuthenData getMappingAuthentication(UserRequest user) throws FaultException {
		MappingAuthenData mapping = new MappingAuthenData();
		try{
			if(StringUtils.isNotEmpty(user.getUserId())){
				List<Roles> roleUser = this.userService.getRolesForUser(user.getUserId());
				if(roleUser != null && roleUser.size() > 0)
				{				
					List<String> rolesIdList = new ArrayList<String>();
					for(Roles r : roleUser){
						rolesIdList.add(r.getRoleId());
					}
					
					if(rolesIdList != null && rolesIdList.size() > 0){
						for(String r : rolesIdList){
							mapping.getRolesUser().add(r);
						}
					}
				}
			}
			
			if(StringUtils.isNotEmpty(user.getWorkstation())){
				List<Roles> roleWskt = this.userService.getRolesForWorstation(user.getWorkstation());
				if(roleWskt != null && roleWskt.size() > 0)
				{				
					List<String> rolesIdList = new ArrayList<String>();
					for(Roles r : roleWskt){
						rolesIdList.add(r.getRoleId());
					}
					
					if(rolesIdList != null && rolesIdList.size() > 0){
						for(String r : rolesIdList){
							mapping.getRolesWorkstation().add(r);
						}
					}
				}
			}
			
			if(StringUtils.isNotEmpty(user.getRole())){
				Roles role = roleService.findById(user.getRole());
				if(role != null)
				{				
					List<String> functionIdList = new ArrayList<String>();
					for(Functions r : role.getFunctions()){
						functionIdList.add(r.getFunctionId());
					}
					if(functionIdList != null && functionIdList.size() > 0){
						for(String r : functionIdList){
							mapping.getFunctionsRole().add(r);
						}
					}
				}
			}
			
		}
		catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return mapping;
	}
	
	@Override
	public String uploadRecieptInformation(GetRecieptAllRequest request) {
		String mes = "";		
		if (request != null) {

			try {
				if (request.getEppRecieptManagers() != null
						&& request.getEppRecieptManagers().size() > 0) {
					for(com.nec.asia.nic.dx.admin.EppRecieptManager fee : request.getEppRecieptManagers()){
						EppRecieptManager dbfee = mappingEppRecieptManager(fee);
						List<EppRecieptManager> isExist = recieptManagerService.findAllRecieptManager(dbfee.getRecieptNo()).getListModel();
						if(isExist == null || isExist.size() < 1){
							Boolean success = recieptManagerService.saveOrUpdateNew(dbfee).getModel();
							if(!success)
								mes += "Fail[EppRecieptManager]";
						}
					}
				}
			} catch (Exception e) {
				mes += "Error[EppRecieptManager] :" + e.getMessage();
			}

			try {
				if (request.getDetailRecieptFees() != null
						&& request.getDetailRecieptFees().size() > 0) {
						for(com.nec.asia.nic.dx.admin.DetailRecieptFee fee : request.getDetailRecieptFees()){
							DetailRecieptFee dbfee = mappingDetailRecieptFees(fee);
							List<DetailRecieptFee> isExist = detailRecieptFeeService.findAllDetailRecieptFee(dbfee.getRecieptNo()).getListModel();
							if(isExist == null || isExist.size() < 1){
								Boolean success = detailRecieptFeeService.saveOrUpdateNew(dbfee).getModel();
								if(!success)
									mes += "Fail[DetailRecieptFee]";
							}
							else {
								Boolean check = true;
								for(DetailRecieptFee p : isExist){
									if(dbfee.getCodeBill().equals(p.getCodeBill()) && dbfee.getNumberBill().equals(p.getNumberBill())){
										check = false;
										break;
									}
								}
								if(check){
									Boolean success = detailRecieptFeeService.saveOrUpdateNew(dbfee).getModel();
									if(!success)
										mes += "Fail[EppDetailReciept]";
								}
							}
						}
				}
			} catch (Exception e) {
				mes += "Error[DetailRecieptFee] :" + e.getMessage();
			}
			try {
				if (request.getEppDetailReciepts() != null
						&& request.getEppDetailReciepts().size() > 0) {
					for(com.nec.asia.nic.dx.admin.EppDetailReciept fee : request.getEppDetailReciepts()){
						EppDetailReciept dbfee = mappingEppDetailReciept(fee);
						List<EppDetailReciept> isExist = detailRecieptService.findAllDetailReciept(dbfee.getRecieptNo());
						if(isExist == null || isExist.size() < 1){
							Boolean success = detailRecieptService.saveOrUpdateNew(dbfee);
							if(!success)
								mes += "Fail[EppDetailReciept]";
						}
						else
						{
							Boolean check = true;
							for(EppDetailReciept p : isExist){
								if(dbfee.getTransactionId().equals(p.getTransactionId())){
									check = false;
									break;
								}
							}
							if(check){
								Boolean success = detailRecieptService.saveOrUpdateNew(dbfee);
								if(!success)
									mes += "Fail[EppDetailReciept]";
							}
						}
					}
				}
			} catch (Exception e) {
				mes += "Error[EppDetailReciept] :" + e.getMessage();
			}
			
			try {
				if (request.getEppRecieptPayments() != null
						&& request.getEppRecieptPayments().size() > 0) {
					for(com.nec.asia.nic.dx.admin.EppRecieptPayment fee : request.getEppRecieptPayments()){
						FeeRecieptPayment dbfee = mappingEppRecieptPayment(fee);
						List<FeeRecieptPayment> isExist = feeRecieptPaymentService.findAllFeeRecieptPayment(dbfee.getRecieptNo()).getListModel();
						if(isExist == null || isExist.size() < 1){
							Boolean success = feeRecieptPaymentService.saveOrUpdateNew(dbfee).getModel();
							if(!success)
								mes += "Fail[FeeRecieptPayment]";
						}
						else
						{
							Boolean check = true;
							for(FeeRecieptPayment p : isExist){
								if(dbfee.getTypePayment().equals(p.getTypePayment())){
									check = false;
									break;
								}
							}
							if(check){
								Boolean success = feeRecieptPaymentService.saveOrUpdateNew(dbfee).getModel();
								if(!success)
									mes += "Fail[EppDetailReciept]";
							}
						}
					}
				}
			} catch (Exception e) {
				mes += "Error[FeeRecieptPayment] :" + e.getMessage();
			}
			if(StringUtils.isEmpty(mes)){
				mes = "success";
			}
		}

		return mes;
	}

	private DetailRecieptFee mappingDetailRecieptFees(
			com.nec.asia.nic.dx.admin.DetailRecieptFee map) {
		DetailRecieptFee obj = new DetailRecieptFee();
		obj.setCodeBill(map.getCodeBill());
		obj.setCreateBy(map.getCreateBy());
		obj.setCreateDate(map.getCreateDate());
		obj.setDescription(map.getDescription());
		obj.setModifyBy(map.getModifyBy());
		obj.setModifyDate(map.getModifyDate());
		obj.setNumberBill(map.getNumberBill());
		obj.setPrice(map.getPrice());
		obj.setPriceFlag(map.getPriceFlag());
		obj.setReason(map.getReason());
		obj.setRecieptNo(map.getRecieptNo());
		return obj;
	}

	private EppDetailReciept mappingEppDetailReciept(
			com.nec.asia.nic.dx.admin.EppDetailReciept map) {
		EppDetailReciept obj = new EppDetailReciept();
		obj.setAddress(map.getAddress());
		obj.setAuthenticationCode(map.getAuthenticationCode());
		obj.setCheckTxn(map.getChecktxn());
		obj.setCreateBy(map.getCreateBy());
		obj.setCreateDate(map.getCreateDate());
		obj.setDescription(map.getDescription());
		obj.setDob(map.getDob());
		obj.setFullname(map.getFullname());
		obj.setModifyBy(map.getModifyBy());
		obj.setModifyDate(map.getModifyDate());
		obj.setNin(map.getNin());
		obj.setRecieptNo(map.getRecieptNo());
		obj.setSerialNo(map.getSerialNo());
		obj.setTransactionId(map.getTransactionId());
		return obj;
	}

	private EppRecieptManager mappingEppRecieptManager(
			com.nec.asia.nic.dx.admin.EppRecieptManager map) {
		EppRecieptManager obj = new EppRecieptManager();
		/*Phúc đóng 26/12/2019
		 * obj.setAuthenticationCode(map.getAuthenticationCode());
		obj.setCodeBill(map.getCodeBill());
		obj.setCompetentAuthorities(map.getCompetentAuthorities());
		obj.setCount(map.getCount());
		obj.setCreateBy(map.getCreateBy());
		obj.setCreateDate(map.getCreateDate());
		obj.setDateDob(map.getDateDob());
		obj.setDataPrint(map.getDataPrint());
		obj.setDateResultPlan(map.getDateResultPlan());
		obj.setDescription(map.getDescription());
		obj.setDob(map.getDob());
		obj.setFullname(map.getFullname());
		obj.setModifyBy(map.getModifyBy());
		obj.setModifyDate(map.getModifyDate());
		obj.setNationPlan(map.getNationPlan());
		obj.setNinNumber(map.getNinNumber());
		obj.setNote(map.getNote());
		obj.setNumberBill(map.getNumberBill());
		obj.setOfficers(map.getOfficers());
		obj.setPaymentAmount(map.getPaymentAmount());
		obj.setPaymentFlag(map.getPaymentFlag());
		obj.setPayPlan(map.getPayPlan());
		obj.setPhone(map.getPhone());
		obj.setReasonPayment(map.getReasonPayment());
		obj.setReceiveFlag(map.getReceiveFlag());
		obj.setRecieptName(map.getRecieptName());
		obj.setRecieptNo(map.getRecieptNo());
		obj.setReferPersonNo(map.getReferPersonNo());
		obj.setStatus(map.getStatus());
		obj.setSubmitter(map.getSubmitter());*/
		return obj;
	}

	private FeeRecieptPayment mappingEppRecieptPayment(
			com.nec.asia.nic.dx.admin.EppRecieptPayment map) {
		FeeRecieptPayment obj = new FeeRecieptPayment();
		obj.setAmount(map.getAmount());
		obj.setCreateBy(map.getCreateBy());
		obj.setCreateDate(map.getCreateDate());
		obj.setDescription(map.getDescription());
		obj.setUpdateBy(map.getUpdateBy());
		obj.setUpdateDate(map.getUpdateDate());
		obj.setPrice(map.getPrice());
		obj.setRecieptNo(map.getRecieptNo());
		obj.setRmPaymentFlag(map.getRmPaymentFlag());
		obj.setTotal(map.getTotal());
		obj.setTypePayment(map.getTypePayment());
		obj.setUnit(map.getUnit());
		return obj;
	}
	
	private com.nec.asia.nic.dx.admin.Users mapperUser(Users u){
		com.nec.asia.nic.dx.admin.Users uJson = new com.nec.asia.nic.dx.admin.Users();
		if(u.getActiveIndicator() != null && u.getActiveIndicator())
			uJson.setActiveIndicator("Y");
		else
			uJson.setActiveIndicator("N");
		uJson.setCreateBy(u.getCreateBy());
		uJson.setCreateDate(u.getCreateDate());
		uJson.setCreateWkstnId(u.getCreateWkstnId());
		uJson.setDateOfPwdExpiry(u.getDateOfPwdExpiry());
		uJson.setDeleteBy(u.getDeleteBy());
		uJson.setDeleteDate(u.getDeleteDate());
		if(u.getDeleteFlag() != null && u.getDeleteFlag())
			uJson.setDeleteFlag("Y");
		else
			uJson.setDeleteFlag("N");
		uJson.setDeleteWkstnId(u.getDeleteWkstnId());
		uJson.setPosition(u.getPosition());
		uJson.setSiteCode(u.getSiteCode());
		uJson.setSiteGroupCode(u.getSiteGroupCode());
		if(u.getSysAdminFlag() != null && u.getSysAdminFlag())
			uJson.setSysAdminFlag("Y");
		else
			uJson.setSysAdminFlag("N");
		uJson.setSystemId(u.getSystemId());
		uJson.setUpdateBy(u.getUpdateBy());
		uJson.setUpdateDate(u.getUpdateDate());
		uJson.setUpdateWkstnId(u.getUpdateWkstnId());
		uJson.setUserEndDate(u.getUserEndDate());
		uJson.setUserId(u.getUserId());
		uJson.setUserName(u.getUserName());
		uJson.setUserStartDate(u.getUserStartDate());		
		return uJson;
	}
	
	private com.nec.asia.nic.dx.admin.Roles mapperRole(Roles r){
		com.nec.asia.nic.dx.admin.Roles rJson = new com.nec.asia.nic.dx.admin.Roles();
		rJson.setCreateBy(r.getCreateBy());
		rJson.setCreateDate(r.getCreateDate());
		rJson.setCreateWkstnId(r.getCreateWkstnId());
		rJson.setDeleteBy(r.getDeleteBy());
		rJson.setDeleteDate(r.getDeleteDate());
		if(r.getDeleteFlag() != null && r.getDeleteFlag())
			rJson.setDeleteFlag("Y");
		else
			rJson.setDeleteFlag("N");
		rJson.setDeleteWkstnId(r.getDeleteWkstnId());
		rJson.setRoleDesc(r.getRoleDesc());
		rJson.setRoleId(r.getRoleId());
		rJson.setSystemId(r.getSystemId());
		rJson.setUpdateBy(r.getUpdateBy());
		rJson.setUpdateDate(r.getUpdateDate());
		rJson.setUpdateWkstnId(r.getUpdateWkstnId());
		return rJson;
	}
	
	private com.nec.asia.nic.dx.admin.Workstations mapperWkst(Workstations w){
		com.nec.asia.nic.dx.admin.Workstations wJson = new com.nec.asia.nic.dx.admin.Workstations();
		if(w.getAccessibleFlag() != null && w.getAccessibleFlag())
			wJson.setAccessibleFlag("Y");
		else
			wJson.setAccessibleFlag("N");
		wJson.setCounterPriority(w.getCounterPriority());
		wJson.setCreateBy(w.getCreateBy());
		wJson.setCreateDate(w.getCreateDate());
		wJson.setCreateWkstnId(w.getCreateWkstnId());
		wJson.setDeleteBy(w.getDeleteBy());
		wJson.setDeleteDate(w.getDeleteDate());
		if(w.getDeleteFlag() != null && w.getDeleteFlag())
			wJson.setDeleteFlag("Y");
		else
			wJson.setDeleteFlag("N");
		wJson.setDeleteWkstnId(w.getDeleteWkstnId());
		wJson.setSiteCode(w.getSiteCode());
		wJson.setSystemId(w.getSystemId());
		wJson.setUpdateBy(w.getUpdateBy());
		wJson.setUpdateDate(w.getUpdateDate());
		wJson.setUpdateWkstnId(w.getUpdateWkstnId());
		wJson.setWkstnDesc(w.getWkstnDesc());
		wJson.setWkstnId(w.getWkstnId());
		wJson.setWkstnType(w.getWkstnType());
		return wJson;
	}
	
	private com.nec.asia.nic.dx.admin.Functions mapperFunction(Functions r){
		com.nec.asia.nic.dx.admin.Functions fJson = new com.nec.asia.nic.dx.admin.Functions();
		fJson.setCreateBy(r.getCreateBy());
		fJson.setCreateDate(r.getCreateDate());
		fJson.setCreateWkstnId(r.getCreateWkstnId());
		fJson.setDeleteBy(r.getDeleteBy());
		fJson.setDeleteDate(r.getDeleteDate());
		if(r.getDeleteFlag() != null && r.getDeleteFlag())
			fJson.setDeleteFlag("Y");
		else
			fJson.setDeleteFlag("N");
		fJson.setDeleteWkstnId(r.getDeleteWkstnId());
		fJson.setFunctionCategory(r.getFunctionCategory());
		fJson.setFunctionDesc(r.getFunctionDesc());
		fJson.setFunctionId(r.getFunctionId());
		fJson.setFunctionUrl(r.getFunctionUrl());
		fJson.setSystemId(r.getSystemId());
		fJson.setUpdateBy(r.getUpdateBy());
		fJson.setUpdateDate(r.getUpdateDate());
		fJson.setUpdateWkstnId(r.getUpdateWkstnId());
		return fJson;
	}
}
