package com.nec.asia.nic.comp.job.command;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.text.Normalizer;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateUtils;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.nec.asia.nic.comp.queuesJobSchedule.service.LogJobScheduleService;
import com.nec.asia.nic.comp.queuesJobSchedule.service.QueuesJobScheduleService;
import com.nec.asia.nic.comp.job.command.exception.NicCommandException;
import com.nec.asia.nic.comp.job.dto.DataRegistrationLdsDTO;
import com.nec.asia.nic.comp.job.utils.NicCommandUtil;
import com.nec.asia.nic.comp.trans.domain.AreaData;
import com.nec.asia.nic.comp.trans.domain.AttachmentDoc;
import com.nec.asia.nic.comp.trans.domain.CountryData;
import com.nec.asia.nic.comp.trans.domain.DataJson;
import com.nec.asia.nic.comp.trans.domain.DocAttachment;
import com.nec.asia.nic.comp.trans.domain.DocumentInfo;
import com.nec.asia.nic.comp.trans.domain.LogJobSchedule;
import com.nec.asia.nic.comp.trans.domain.NicDocumentData;
import com.nec.asia.nic.comp.trans.domain.NicRegistrationData;
import com.nec.asia.nic.comp.trans.domain.NicTransaction;
import com.nec.asia.nic.comp.trans.domain.NicTransactionAttachment;
import com.nec.asia.nic.comp.trans.domain.NicTransactionLog;
import com.nec.asia.nic.comp.trans.domain.NicUploadJob;
import com.nec.asia.nic.comp.trans.domain.OfficeData;
import com.nec.asia.nic.comp.trans.domain.PassportInformation;
import com.nec.asia.nic.comp.trans.domain.PersonAttachment;
import com.nec.asia.nic.comp.trans.domain.PersonInformation;
import com.nec.asia.nic.comp.trans.domain.PlaceData;
import com.nec.asia.nic.comp.trans.domain.QueuesJobSchedule;
import com.nec.asia.nic.comp.trans.domain.RequestDocument;
import com.nec.asia.nic.comp.trans.service.DataPackService;
import com.nec.asia.nic.comp.trans.service.DocumentDataService;
import com.nec.asia.nic.comp.trans.service.NicTransactionAttachmentService;
import com.nec.asia.nic.comp.trans.service.NicTransactionService;
import com.nec.asia.nic.comp.trans.service.NicUploadJobService;
import com.nec.asia.nic.comp.trans.service.PersoService;
import com.nec.asia.nic.comp.trans.service.TransactionLogService;
import com.nec.asia.nic.comp.trans.service.exception.DataPackServiceException;
import com.nec.asia.nic.comp.trans.service.exception.PersoServiceException;
import com.nec.asia.nic.comp.trans.service.exception.TransactionServiceException;
import com.nec.asia.nic.comp.trans.utils.APIConstantsBCA;
import com.nec.asia.nic.framework.admin.code.domain.Parameters;
import com.nec.asia.nic.framework.admin.code.service.ParametersService;
import com.nec.asia.nic.util.MiscXmlConvertor;
import com.nec.asia.nic.utils.DateUtil;

/**
 * @author johnpaul_millan
 * @Company : NEC ASIA PACIFIC PTE LTD
 * @Since : Jun 06, 2013
 * <p></p>
 */

/* 
 * Modification History:
 * 21 Nov 2013 (jp) - add logic to change transaction status for rejected txns and CAR txns
 * 06 May 2014 (jp) - add validateTransactionStatusUpdate to avoid overwriting transaction status during reprocess
 */

public class NicLdsProcessCommand extends BaseCommand<QueuesJobSchedule> implements Command<QueuesJobSchedule>{

	//UPDATE STATUS CODE	
	public static final int JOB_STATE = 1;
	public static final int PERSO_REGISTER = 6;
		
	//JOB STATE
	public static final String JOB_STATE_PERSO_REGISTER= "PERSO_REGISTER";
	//private static final Logger logger = Logger.getLogger(NicVerifySuccessCommand.class);
	private QueuesJobScheduleService queuesJobScheduleService;
	private NicTransactionService nicTransactionService;
	private NicUploadJobService uploadJobService;
	private DataPackService dataPackService;
	private TransactionLogService nicTransactionLogService;
	private PersoService persoService;
	private PersoService persoService2;
	/*private ParametersService parametersService;*/
	NicCommandUtil nicCommandUtil = new NicCommandUtil();
	
	@Override
	public void doSomething(QueuesJobSchedule que) {

		String logData = null;
		Date startTime = new Date();
		String transactionStatus = null;
		NicUploadJob obj = new NicUploadJob();
		try {
			obj = uploadJobService
					.getUploadJobByTransactionIdJob(que.getCode());

			if (que.getTypeTransaction().equals(
					QueuesJobSchedule.TYPE_TRANSACTION_LDS)
					&& !que.getTypeLogJob().equals("LOS")) {

				// Cập nhật trạng thái cho JOB
				que.setStatus(QueuesJobSchedule.STATUS_JOB_PENDING);
				queuesJobScheduleService.createQueuesJobSchedule(que);

				logger.info("inside NicLdsProcessCommand:{}",
						obj.getTransactionId());
				this.setState(obj.getJobType());// set job type as child key

				// Lấy số biên nhận
				/* if(StringUtils.isNotEmpty(obj.getReceiptNo())){ */
				NicTransaction transObj = new NicTransaction();
				updateStatus(obj.getWorkflowJobId(), JOB_STATE_PERSO_REGISTER,
						JOB_STATE);

				/*
				 * if(StringUtils.equals(obj.getPersoRegisterStatus(),
				 * STATUS_COMPLETED)){
				 */
				if (obj == null
						|| obj.getPersoRegisterStatus().equals(
								NicUploadJob.RECORD_STATUS_APPROVED)) {
					// SUBMIT PERSO has been completed before, skip step
					logger.info("-----already completed, skipping step:{}",
							obj.getTransactionId());
				} else {

					updateStatus(obj.getWorkflowJobId(), STATUS_INPROGRESS,
							PERSO_REGISTER);
					transObj.setTransactionId(obj.getTransactionId());
					transObj = nicTransactionService.findById(
							transObj.getTransactionId(), false, true, true,
							false);// has trans doc and registration data

					preparePersoData(transObj);
					submitPersoData(transObj);
					logger.info("perso registered:{}", obj.getTransactionId());

					transactionStatus = JOB_STATE_PERSO_REGISTER
							+ STAGE_COMPLETED;
					updateStatus(obj.getWorkflowJobId(), STATUS_COMPLETED,
							PERSO_REGISTER);
					uploadJobService.resetReprocessCount(
							obj.getWorkflowJobId(), 0);
					logger.info("update perso status:{}",
							obj.getTransactionId());
					
					que.setStatus(QueuesJobSchedule.STATUS_JOB_SUCCESS);
					queuesJobScheduleService.createQueuesJobSchedule(que);
				}
		/*}
		else
		{
			try{
				NicTransaction transObj = new NicTransaction();
				transObj.setTransactionId(obj.getTransactionId());
				transObj = nicTransactionService.findById(transObj.getTransactionId(), false, true, true, false);//has trans doc and registration data
				Boolean isCreate = CheckPersoRegister(obj.getTransactionId(), obj.getTempPassportNo(), obj);
				if(isCreate){
					Parameters para = parametersService.getParamDetails("SYSTEM", "RECIEPT_NO_PARAM");
					if(para != null){
						int number = Integer.parseInt(para.getParaShortValue());
						int numberN = number + 1;
						para.setParaShortValue("" + numberN);
						String res = parametersService.saveParam(para);
						
						if(res.equals("success")){
							Calendar now = Calendar.getInstance();
							int year = now.get(Calendar.YEAR);
							int month = now.get(Calendar.MONTH) + 1; // Note: zero based!
							int day = now.get(Calendar.DAY_OF_MONTH);
							String date = "" + year + month + day;
							String receiptNo = String.format("%09d", number);
							obj.setReceiptNo(para.getParaShortValue() + date + receiptNo);
							uploadJobService.saveOrUpdate(obj);
						}
						else{
							logger.info("update next reciep_no fail.");
						}
					}
				}
				else{
					logger.info("missing data perso or error validate data perso");
				}
			} catch(Exception e){
				logger.info("error: " + e.getMessage());
			}
		}*/
			}
		} catch (Exception e) {
			logger.error("flag EXCEPTION");
			e.printStackTrace();
			transactionStatus = JOB_STATE_PERSO_REGISTER + STAGE_ERROR;
			logData = MiscXmlConvertor.parseObjectToXml(e);

			que.setStatus(QueuesJobSchedule.STATUS_JOB_ERROR);
			queuesJobScheduleService.createQueuesJobSchedule(que);
			
			this.setState(GOTO_ERROR_CMD);
			updateStatus(obj.getWorkflowJobId(), STATUS_ERROR, PERSO_REGISTER);
			nicCommandUtil.setErrorFlag(obj.getWorkflowJobId(), true,
					uploadJobService);

		} finally {
			try {
				if (StringUtils.isNotBlank(obj.getTransactionId())
						&& StringUtils.isNotEmpty(transactionStatus)) {
					uploadJobService.updateJobStatus(obj.getWorkflowJobId(),
							transactionStatus);
					this.saveTransactionLog(obj.getTransactionId(),
							JOB_STATE_PERSO_REGISTER, transactionStatus,
							startTime, new Date(), null, logData,
							obj.getJobReprocessCount());
				}
			} catch (Exception e) {
				logger.error(
						"Exception in finally block (NicLdsProcessCommand.doSomething)",
						e);
			}
		}
	}

	private void preparePersoData(NicTransaction transObj) throws Exception {

		try {
			if (transObj.getNicRegistrationData() == null) {
				throw new NicCommandException("Transaction Data not found");
			} else {
				dataPackService.preparePersoData(transObj);
			}
		} catch (DataPackServiceException de) {
			logger.error("[Error in preparePersoData]", de);
			throw de;
		} catch (Exception e) {
			logger.error("[Exception in preparePersoData]", e);
			throw e;
		}
	}

	private void submitPersoData(NicTransaction transObj)
			throws PersoServiceException {
		PersoServiceException exception = null;
		// try {
		// logger.info("before submitting perso data");
		// persoService.submitPersoData(transObj, ""); //SFTP
		// logger.info("after submitting perso data");
		// } catch (PersoServiceException e) {
		// //throw new PersoServiceException(e);
		// exception = e;
		// }

		try {
			logger.info("before submitting perso data to job server");
			persoService2.submitPersoData(transObj, ""); // FILE
			logger.info("after submitting perso data to job server");
		} catch (PersoServiceException e) {
			// throw new PersoServiceException(e);
			exception = e;
		}

		if (exception != null) {
			throw exception;
		}
	}

	private void updateStatus(long objId, String state, int command) {
		try {
			uploadJobService.updateJobState(objId, state, command);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void saveTransactionLog(String transactionId,
			String transactionStage, String transactionStatus, Date startTime,
			Date endTime, String logInfo, String logData, Integer retryCount) {
		NicTransactionLog transactionLog = new NicTransactionLog();
		transactionLog.setRefId(transactionId);
		transactionLog.setLogCreateTime(new Date());
		transactionLog.setTransactionStage(transactionStage);// TRANSACTION_STATE_PERSONALIZATION);
		transactionLog.setTransactionStatus(transactionStatus);
		transactionLog.setSiteCode(nicCommandUtil
				.getSystemSiteCodeFromParameter()); // get from
													// 'CURRENT_SITE_CODE'
		transactionLog.setStartTime(startTime);
		transactionLog.setEndTime(endTime);
		transactionLog.setLogInfo(logInfo);
		transactionLog.setLogData(logData);
		// 8-OCT-2013 (jp) - added hostname and system
		transactionLog.setWkstnId(nicCommandUtil.getHostName());
		transactionLog.setOfficerId(nicCommandUtil.getSystemName());
		transactionLog.setRetryCount(retryCount);
		synchronized (NicTransactionLog.class) {
			nicTransactionLogService.saveOrUpdate(transactionLog);
		}
	}

	private Boolean CheckPersoRegister(String trans, String ppno,
			NicUploadJob nicUploadJob) {
		try {
			DataRegistrationLdsDTO response = new DataRegistrationLdsDTO();
			NicTransaction nicTran = new NicTransaction();

			nicTran = nicTransactionService.findById(trans, false, true, true,
					false);

			Date doE = DateUtils.addYears(new Date(),
					nicTran.getValidityPeriod());
			String dateOfExpiry = DateUtil.parseDate(doE,
					DateUtil.FORMAT_YYYYMMDD);
			String dateOfIssue = DateUtil.parseDate(new Date(),
					DateUtil.FORMAT_YYYYMMDD);
			String nameOfHolder = getNameOfHolder(nicTran
					.getNicRegistrationData().getSurnameFull(), nicTran
					.getNicRegistrationData().getFirstnameFull(), nicTran
					.getNicRegistrationData().getMiddlenameFull());
			String dateOfBirth = DateUtil.parseDate(nicTran
					.getNicRegistrationData().getDateOfBirth(),
					DateUtil.FORMAT_YYYYMMDD);
			String passportType = nicTran.getPassportType();
			String nationality = nicTran.getNicRegistrationData()
					.getNationality();
			String issueState = "VNM";
			String passportNo = ppno;
			String searchName = removeAccent(nameOfHolder).toUpperCase();
			String gender = nicTran.getNicRegistrationData().getGender();
			String nin = nicTran.getNin();
			String placeOfBirth = nicTran.getNicRegistrationData()
					.getPlaceOfBirth();
			String priority = "NORMAL";
			if (nicUploadJob.getNicTransaction().getPriority() == 1)
				priority = "HIGH";
			else if (nicUploadJob.getNicTransaction().getPriority() == 2)
				priority = "HIGHEST";

			Boolean hasDG3 = false;
			String finger01Position = "";
			String finger02Position = "";
			String finger01 = "";
			String finger02 = "";
			// get encode fingers from transaction data
			if (StringUtils.isNotBlank(nicTran.getNicRegistrationData()
					.getFpEncode())) {
				String[] fpEncodes = StringUtils.split(nicTran
						.getNicRegistrationData().getFpEncode(), ",");
				if (fpEncodes.length >= 1)
					finger01Position = fpEncodes[0];
				if (fpEncodes.length >= 2)
					finger02Position = fpEncodes[1];
				byte[] finger01_ = null;
				byte[] finger02_ = null;
				finger01_ = this.getTransactionDocument(nicTran,
						NicTransactionAttachment.DOC_TYPE_MINUTIA_CBEFF,
						finger01Position);
				finger02_ = this.getTransactionDocument(nicTran,
						NicTransactionAttachment.DOC_TYPE_MINUTIA_CBEFF,
						finger02Position);

				if (finger01_ != null && finger02_ != null) {
					hasDG3 = true;
					finger01 = Base64.encodeBase64String(finger01_);
					finger02 = Base64.encodeBase64String(finger02_);
				}
			}
			byte[] photo_ = this.getTransactionDocument(nicTran,
					NicTransactionAttachment.DOC_TYPE_PHOTO_CHIP,
					NicTransactionAttachment.DEFAULT_SERIAL_NO);
			String photo = Base64.encodeBase64String(photo_);

			if (StringUtils.isNotEmpty(dateOfBirth)
					|| StringUtils.isNotEmpty(dateOfExpiry)
					|| StringUtils.isNotEmpty(dateOfIssue)
					|| StringUtils.isNotEmpty(photo)
					|| StringUtils.isNotEmpty(issueState)
					|| StringUtils.isNotEmpty(nameOfHolder)
					|| StringUtils.isNotEmpty(nationality)
					|| StringUtils.isNotEmpty(nin)
					|| StringUtils.isNotEmpty(passportNo)
					|| StringUtils.isNotEmpty(gender)
					|| StringUtils.isNotEmpty(passportType)
					|| StringUtils.isNotEmpty(placeOfBirth)
					|| StringUtils.isNotEmpty(nicTran.getIssuingAuthority())
					|| StringUtils.isNotEmpty(ppno)
					|| StringUtils.isNotEmpty(priority)
					|| StringUtils.isNotEmpty(searchName)) {

			} else
				return false;
		} catch (TransactionServiceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}

		return true;
	}

	private String removeAccent(String s) {

		String temp = Normalizer.normalize(s, Normalizer.Form.NFD);
		Pattern pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
		return pattern.matcher(temp).replaceAll("");
	}

	private String getNameOfHolder(String surname, String firstname,
			String middlename) {
		String nameOfHolder = "";
		if (StringUtils.isNotEmpty(surname)) {
			nameOfHolder += StringUtils.trim(surname);
		}

		if (StringUtils.isNotEmpty(middlename)) {
			if (StringUtils.length(nameOfHolder) > 0) {
				nameOfHolder += " ";
			}
			nameOfHolder += StringUtils.trim(middlename);
		}

		if (StringUtils.isNotEmpty(firstname)) {
			if (StringUtils.length(nameOfHolder) > 0) {
				nameOfHolder += " ";
			}
			nameOfHolder += StringUtils.trim(firstname);
		}
		return nameOfHolder;
	}

	private byte[] getTransactionDocument(NicTransaction nicTransaction,
			String docType, String serialNo) {
		if (nicTransaction != null) {
			if (CollectionUtils.isNotEmpty(nicTransaction
					.getNicTransactionAttachments())) {
				for (NicTransactionAttachment nicTransactionDoc : nicTransaction
						.getNicTransactionAttachments()) {
					if (StringUtils.equals(docType,
							nicTransactionDoc.getDocType())
							&& StringUtils.equals(serialNo,
									nicTransactionDoc.getSerialNo())) {
						return nicTransactionDoc.getDocData();
					}
				}
				// if serialNo cannot get exact match then convert to number to
				// match if required.
				if (StringUtils.isNumeric(serialNo)) {
					int iSerialNo = Integer.parseInt(serialNo);
					for (NicTransactionAttachment nicTransactionDoc : nicTransaction
							.getNicTransactionAttachments()) {
						if (StringUtils.equals(docType,
								nicTransactionDoc.getDocType())) {
							if (StringUtils.isNumeric(nicTransactionDoc
									.getSerialNo())) {
								int iLoopSN = Integer
										.parseInt(nicTransactionDoc
												.getSerialNo());
								if (iSerialNo == iLoopSN) {
									return nicTransactionDoc.getDocData();
								}
							}
						}
					}
				}
			}
		}
		return null;
	}

	public void setCommandUtil(NicCommandUtil commandUtil) {
		this.nicCommandUtil = commandUtil;
	}

	public void setNicTransactionService(
			NicTransactionService nicTransactionService) {
		this.nicTransactionService = nicTransactionService;
	}

	public void setUploadJobService(NicUploadJobService uploadJobService) {
		this.uploadJobService = uploadJobService;
	}

	public void setNicTransactionLogService(
			TransactionLogService nicTransactionLogService) {
		this.nicTransactionLogService = nicTransactionLogService;
	}

	public void setPersoService(PersoService persoService) {
		this.persoService = persoService;
	}

	public DataPackService getDataPackService() {
		return dataPackService;
	}

	public void setDataPackService(DataPackService dataPackService) {
		this.dataPackService = dataPackService;
	}

	public void setPersoService2(PersoService persoService2) {
		this.persoService2 = persoService2;
	}
}
