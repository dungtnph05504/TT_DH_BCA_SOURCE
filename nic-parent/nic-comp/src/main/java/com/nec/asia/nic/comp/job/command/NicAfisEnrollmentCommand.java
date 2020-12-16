package com.nec.asia.nic.comp.job.command;


import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.StringUtils;

import com.nec.asia.baf.spec.cmd.BioCommandServiceException;
import com.nec.asia.baf.spec.transfer.biometrics.facial.BioFaceInfoDto;
import com.nec.asia.baf.spec.transfer.biometrics.fingerprint.BioFingerprintInfoDto;
import com.nec.asia.baf.spec.transfer.biometrics.fingerprint.BioFingerprintSetDto;
import com.nec.asia.baf.spec.transfer.biometrics.fingerprint.BioFpOriginalImageDto;
import com.nec.asia.baf.spec.transfer.biometrics.registration.BioRegistrationDto;
import com.nec.asia.idserver.agent.payload.model.AbstractBiometricData;
import com.nec.asia.idserver.agent.payload.model.IdserverFaceInfo;
import com.nec.asia.idserver.agent.payload.model.IdserverImageFace;
import com.nec.asia.idserver.agent.payload.model.IdserverImageFingerprint;
import com.nec.asia.idserver.agent.payload.model.IdserverPersonDetail;
import com.nec.asia.idserver.agent.payload.model.IdserverRegistration;
import com.nec.asia.idserver.agent.payload.model.result.IdserverScore;
import com.nec.asia.idserver.agent.payload.model.result.IdserverSubject;
import com.nec.asia.idserver.agent.payload.model.type.IdserverFaceFormat;
import com.nec.asia.idserver.agent.payload.model.type.IdserverFaceType;
import com.nec.asia.idserver.agent.payload.model.type.IdserverImageType;
import com.nec.asia.idserver.agent.service.IdserverAgentService;
import com.nec.asia.idserver.agent.service.exception.IdserverAgentServiceException;
import com.nec.asia.idserver.agent.service.impl.DuplicateRegistrationException;
import com.nec.asia.idserver.agent.util.FingerPosition;
import com.nec.asia.nic.comp.job.command.exception.NicCommandException;
import com.nec.asia.nic.comp.job.utils.NicCommandUtil;
import com.nec.asia.nic.comp.trans.domain.NicAfisData;
import com.nec.asia.nic.comp.trans.domain.NicDocumentData;
import com.nec.asia.nic.comp.trans.domain.NicTransaction;
import com.nec.asia.nic.comp.trans.domain.NicTransactionAttachment;
import com.nec.asia.nic.comp.trans.domain.NicTransactionLog;
import com.nec.asia.nic.comp.trans.domain.NicUploadJob;
import com.nec.asia.nic.comp.trans.service.DocumentDataService;
import com.nec.asia.nic.comp.trans.service.NicAfisDataService;
import com.nec.asia.nic.comp.trans.service.NicTransactionAttachmentService;
import com.nec.asia.nic.comp.trans.service.NicTransactionService;
import com.nec.asia.nic.comp.trans.service.NicUploadJobService;
import com.nec.asia.nic.comp.trans.service.TransactionLogService;
import com.nec.asia.nic.comp.trans.service.exception.NicAfisDataServiceException;
import com.nec.asia.nic.comp.trans.service.exception.TransactionServiceException;
import com.nec.asia.nic.util.MiscXmlConvertor;


/*
 * Modification History:
 * 
 * 22 Feb 2016 (cw)     : save afis_key_no first.
 * 24 Mar 2016 (cw)     : use 1:1 or name matching during registration.
 * 14 Apr 2016 (cw)     : to marked afis_data as 'A' Archived status (deleted from BMS) when Fingerprint is missing.
 * 25 Apr 2016 (cw)     : for fingerprint missing case, use name match
 * 06 Jun 2016 (cw)     : handle duplicate registration issue when retry.
 */
public class NicAfisEnrollmentCommand extends BaseCommand<NicUploadJob> implements Command<NicUploadJob>{

	//private static final Logger logger = Logger.getLogger(NicAfisEnrollmentCommand.class);
	private NicCommandUtil nicCommandUtil;
	//UPDATE STATUS CODE	
	public static final int JOB_STATE = 1;
	public static final int AFIS_REG = 3;
	//AFIS DATA STATUS
	public static final String INSERTED = "I";
	public static final String DELETED = "D";
	public static final String ARCHIVED = "A"; //14 Apr 2016
		
	//JOB STATE
	public static final String JOB_STATE_AFIS_REG = "AFIS_REGISTER";
	
	private NicTransactionService nicTransactionService;
	private NicUploadJobService uploadJobService;
	private TransactionLogService nicTransactionLogService;
	
	private IdserverAgentService idserverAgentService;
	
	private NicAfisDataService nicAfisDataService;
	private DocumentDataService documentDataService;
	private NicTransactionAttachmentService transactionAttachmentService;
	
	private String afisKeyNo;
	private String afisRefId;
	private String afisStatus;//14 Apr 2016
	
	@Override
	public void doSomething(NicUploadJob obj) {
		
		logger.info("inside NicAfisEnrollmentCommand:{}", obj.getTransactionId());
		
		
		//for logging to transaction logs
		String logData = null;
		Date startTime = new Date();
		String transactionStatus = null;
		//
		
		this.setState(obj.getJobType());//set job type as child key
		
		try {
			
			NicTransaction transObj = new NicTransaction();
			updateStatus(obj.getWorkflowJobId(), JOB_STATE_AFIS_REG, JOB_STATE);
			
			transObj = nicTransactionService.findById(obj.getTransactionId(), false, false, true, false);//has reg data
			
			if(StringUtils.equals(obj.getAfisRegisterStatus(), STATUS_COMPLETED)){
				//AFIS REG has been completed before, skip step
				//transactionStatus = JOB_STATE_AFIS_REG+STAGE_COMPLETED;
				logger.info("-----already completed, skipping step:{}", obj.getTransactionId());
				
			}
			else if(StringUtils.equals(obj.getAfisRegisterStatus(), STATUS_PASS_BY_RECOUNT_MAX)){
				//AFIS REG has been completed before, skip step
				//transactionStatus = JOB_STATE_AFIS_REG+STAGE_COMPLETED;
				logger.info("-----pass by recount max, skipping step:{}", obj.getTransactionId());
			}else if (StringUtils.equals(obj.getAfisRegisterStatus(), STATUS_NO_REQUIRED)){
				logger.info("-----pass by no required, skipping step:{}", obj.getTransactionId());
			} else {
				logger.info("idserverAgentService:"+idserverAgentService);
				
				updateStatus(obj.getWorkflowJobId(), STATUS_INPROGRESS, AFIS_REG);
				transObj = nicTransactionService.findById(obj.getTransactionId(), false, true, true, false);//has trans doc and registration data
				prepareAfisData(transObj);
				
				if(registerBiometrics(transObj)){
					logger.info("flag registered successfully:{}:{}", afisRefId, obj.getTransactionId());
					
					//05 MAR 2014 (jp) - save nic afis ref id to NIC_AFIS_DATA table
					NicAfisData e = new NicAfisData(transObj.getTransactionId(), afisKeyNo, Long.parseLong(afisRefId), afisStatus, new Date(), null);
					//NicAfisData e = new NicAfisData(transObj.getTransactionId(), afisKeyNo, null, null, new Date(), null);
					saveAfisRefId(e);
					
					updateStatus(obj.getWorkflowJobId(), STATUS_COMPLETED, AFIS_REG);
					transactionStatus = JOB_STATE_AFIS_REG+STAGE_COMPLETED;
					uploadJobService.resetReprocessCount(obj.getWorkflowJobId(), 0);
				 } else {
					String afisRefId = nicAfisDataService.findAfisRefIdByTransactionId(obj.getTransactionId());
					if(StringUtils.isBlank(afisRefId)){
						logger.error("flag not registered");
						logData = "Fingerprint list for "+ obj.getTransactionId() +" is empty";
						this.setState(GOTO_ERROR_CMD);
						updateStatus(obj.getWorkflowJobId(), STATUS_ERROR, AFIS_REG);
						transactionStatus = JOB_STATE_AFIS_REG + STAGE_ERROR;
						nicCommandUtil.setErrorFlag(obj.getWorkflowJobId(), true, uploadJobService);
					} else {
						updateStatus(obj.getWorkflowJobId(), STATUS_COMPLETED, AFIS_REG);
						uploadJobService.resetReprocessCount(obj.getWorkflowJobId(), 0);
						logger.info("afisRefId is exist:{}:{}", afisRefId, obj.getTransactionId());
					}
				}
			}
			
		} catch (Exception e) {
			String afisRefId = "";
			try {
				afisRefId = nicAfisDataService.findAfisRefIdByTransactionId(obj.getTransactionId());			
				if(StringUtils.isBlank(afisRefId)){
					logger.error("flag EXCEPTION:",e);
					transactionStatus = JOB_STATE_AFIS_REG + STAGE_ERROR;
					logData = MiscXmlConvertor.parseObjectToXml(e);
					this.setState(GOTO_ERROR_CMD);
					updateStatus(obj.getWorkflowJobId(), STATUS_ERROR, AFIS_REG);
					nicCommandUtil.setErrorFlag(obj.getWorkflowJobId(), true, uploadJobService);
				}
				else {
					updateStatus(obj.getWorkflowJobId(), STATUS_COMPLETED, AFIS_REG);
					uploadJobService.resetReprocessCount(obj.getWorkflowJobId(), 0);
					logger.info("afisRefId is exist:{}:{}", afisRefId, obj.getTransactionId());
				}
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}finally {
			try {
				if (StringUtils.isNotBlank(obj.getTransactionId()) && StringUtils.isNotEmpty(transactionStatus)) {
					uploadJobService.updateJobStatus(obj.getWorkflowJobId(), transactionStatus);
					this.saveTransactionLog(obj.getTransactionId(), JOB_STATE_AFIS_REG, transactionStatus, obj.getJobReprocessCount(), startTime, new Date(), null, logData);						
				}
			} catch (Exception e) {
				logger.error("Exception in finally block (NicAfisEnrollmentCommand.doSomething)", e);
			}
		}
	}
	
	private void prepareAfisData(NicTransaction transObj) throws NicAfisDataServiceException, IdserverAgentServiceException, TransactionServiceException {
		checkAndSetAfisKeyNumber(transObj);
		NicAfisData currentAfisData = nicAfisDataService.findById(transObj.getTransactionId());
		if (currentAfisData==null && StringUtils.isNotBlank(afisKeyNo)) {
			NicAfisData afisData = new NicAfisData(transObj.getTransactionId(), afisKeyNo, null, null, new Date(), null);
			nicAfisDataService.save(afisData);
		}
	}

	//save afis ref id and trans id to NIC_AFIS_DATA table
	private void saveAfisRefId(NicAfisData afisData) throws Exception{
		try{
			
			nicAfisDataService.saveAfisRefId(afisData);
			
		}catch(NicAfisDataServiceException aex){
			throw new NicAfisDataServiceException(aex);
		}catch(Exception ex){
			throw new Exception(ex);
		}
	}
	
	private boolean registerBiometrics(NicTransaction transObj) throws Exception{
		//transaction object has trans docs and reg data
		boolean result = false;
		
		try {
			IdserverRegistration registration = new IdserverRegistration();
			Set<IdserverImageFingerprint> fpList = new HashSet<IdserverImageFingerprint>();
			fpList = buildRegFingerPrints(transObj.getNicTransactionAttachments(), transObj);
			IdserverFaceInfo faceInfo = buildFace(transObj.getNicTransactionAttachments());
			IdserverPersonDetail personDetail = buildPersonDetail(transObj);
			byte[] fpTemplate = buildTemplates(transObj.getNicTransactionAttachments());

			checkAndSetAfisKeyNumber(transObj);
			if (fpTemplate==null && CollectionUtils.isNotEmpty(fpList)) {
				logger.error("Error in Afis Enrollment [{}]- Templates is empty, but have fingerprints.", transObj.getTransactionId());
				throw new NicCommandException("Error in Afis Enrollment - Templates is empty, but have fingerprints.");
			}
			registration.setApplicationRefNo(transObj.getTransactionId());
			registration.setPrimaryExternalId(afisKeyNo);
			registration.setFpList(fpList);
			registration.setFpTemplate(fpTemplate);
			registration.setFace(faceInfo);
			registration.setPersonDetail(personDetail);
			logger.info("after setting registration details");
			
			//14 Apr 2016 (cw) set archived status if not fingerprints.
			if (fpTemplate==null && CollectionUtils.isEmpty(fpList)) {
				afisStatus = ARCHIVED;
			} else {
				afisStatus = INSERTED;
			}
		    
	       //22 Sep 2014 (cw) - handle registered transactions to continue process the job. - start
	    	try {
		    	afisRefId = idserverAgentService.registerBiometricData(registration);
		    	
		    	checkAndUpdateRefId(transObj);
		    	result = true;
	    	} catch (DuplicateRegistrationException dre) {
	    		result = this.handleDuplicateRegistration(transObj);
	    	} catch (Exception re) {
	    		if (StringUtils.contains(re.getMessage(), "already exists in UDB")) {
	    			result = this.handleDuplicateRegistration(transObj);
	    		} else {
	    			throw re;
	    		}
	    	}
		//22 Sep 2014 (cw) - handle registered transactions to continue process the job. - end
	    	return result;
		} catch (IdserverAgentServiceException ie) {
			throw new IdserverAgentServiceException(ie);
		} catch (Exception e) {
			throw new Exception(e);
		}
	}
	
	private boolean handleDuplicateRegistration(NicTransaction transObj) throws Exception {
		boolean result = false; 
		//check the fingerprint set if it can hit with the same transaction id in AFIS, then consider it registered successfully.
		String transactionId = transObj.getTransactionId();
		String nicId = afisKeyNo;
		ArrayList<NicTransactionAttachment> documentList = new ArrayList<NicTransactionAttachment>(transObj.getNicTransactionAttachments());
		List<AbstractBiometricData<IdserverImageFingerprint>> biometricData = new ArrayList<AbstractBiometricData<IdserverImageFingerprint>>();
		logger.info("trans docs count:"+documentList.size());
		biometricData = buildFingerPrints(biometricData, documentList);
		logger.info("Duplicate Registration Found: "+transactionId+", "+nicId);
		if (CollectionUtils.isNotEmpty(biometricData)) {
			IdserverSubject idSvrSubj = idserverAgentService.inquiryFpVerification(biometricData, transactionId, nicId);
			if (idSvrSubj!=null && idSvrSubj.getHitDecision()==true) {
				String scores = "";
				if (CollectionUtils.isNotEmpty(idSvrSubj.getScoreList())) {
					for (IdserverScore idsScore : idSvrSubj.getScoreList()) {
						scores += idsScore.getFingerPosition()+"="+idsScore.getScore()+",";
					}
				}
				logger.info(">>>>> hasHit result:"+idSvrSubj.getHitDecision()+" hit score: "+scores);
			} else {
				logger.info("not hit for : "+transactionId+", "+nicId);
				throw new Exception("Unexpected DuplicateRegistrationException encountered!");
			}
		} else {
			BioRegistrationDto dto = idserverAgentService.retrieveBiometricData(transactionId, false, false, false);
			if(transObj.getNicRegistrationData() != null){
				//compare last name and gender
				if (dto.getPersonDetail()!=null && 
						(!StringUtils.equals(dto.getPersonDetail().getLastName(), transObj.getNicRegistrationData().getSurnameFull()) ||
						!StringUtils.equals(dto.getPersonDetail().getGender(), transObj.getNicRegistrationData().getGender())))
				{
					logger.warn("[{}] data is different between EPP and BAF : [BAF]{},{} vs [EPP]{},{}", new Object[] {transactionId, dto.getPersonDetail().getLastName(), dto.getPersonDetail().getGender(), transObj.getNicRegistrationData().getSurnameFull(), transObj.getNicRegistrationData().getGender()} );
					logger.info("[handleDuplicateRegistration] not hit for : "+transactionId+", "+nicId);
					throw new Exception("Unexpected DuplicateRegistrationException encountered!");
				}
			}
		}
		// [1] true hit, [2] not fingerprints
		// set it as registered completed.
		result = true;
		afisRefId = nicAfisDataService.findAfisRefIdByTransactionId(transactionId);
		logger.info("treat as registered completed.["+transactionId+", "+nicId+"] afisRefId: "+afisRefId);
		return result;
	}
	
	private void checkAndUpdateRefId(NicTransaction transObj) throws IdserverAgentServiceException {
		String transactionId = transObj.getTransactionId();
		//retrieve and update the photo and fingerprint reference IDB id
		BioRegistrationDto dto = idserverAgentService.retrieveBiometricData(transactionId , true, true, false);
		List<NicTransactionAttachment> docList = new ArrayList<NicTransactionAttachment>();
		
		if (dto!=null) {
			String photoLobId = null;
			String thumbnailLobId = null;
			Map<Short, String> fingerprintLobIdMap = new HashMap<Short, String>();
			if (dto.getFaces()!=null && CollectionUtils.isNotEmpty(dto.getFaces().getBioFaceInfoDto())) {
				for (BioFaceInfoDto faceDto : dto.getFaces().getBioFaceInfoDto()) {
					//photo lob id
					if (faceDto.getMugshot()!=null) {
						photoLobId = faceDto.getMugshot().getRecordId();
					}
					//thumbnail lob id
					if (faceDto.getThumbnail()!=null) {
						thumbnailLobId = faceDto.getThumbnail().getRecordId();
					}
				}
			}
		
			//fingerprint lob id
			if (dto.getFingerData()!=null) {
				BioFingerprintSetDto fingerSetDto = (BioFingerprintSetDto) dto.getFingerData();
				if (fingerSetDto.getFingerprints()!=null && CollectionUtils.isNotEmpty(fingerSetDto.getFingerprints().getBioFingerprintInfoDto())) {
					for (BioFingerprintInfoDto fingerDto : fingerSetDto.getFingerprints().getBioFingerprintInfoDto()) {
						if (fingerDto.getOriginalImages()!=null && CollectionUtils.isNotEmpty(fingerDto.getOriginalImages().getBioFpOriginalImageDto())) {
							for (BioFpOriginalImageDto fpOriginalImageDto : fingerDto.getOriginalImages().getBioFpOriginalImageDto()) {
								fingerprintLobIdMap.put(fpOriginalImageDto.getFingerPosition(), fpOriginalImageDto.getLobId());
							}
						}
					}
				}
			}
			
			logger.info("photoLobId: "+photoLobId);
			logger.info("thumbnailLobId: "+thumbnailLobId);
			logger.info("fingerprintLobIdMap: "+fingerprintLobIdMap);
			
			for (NicTransactionAttachment doc : transObj.getNicTransactionAttachments()) {
				if (StringUtils.isNotBlank(photoLobId)) {
					if (StringUtils.equals(NicTransactionAttachment.DOC_TYPE_PHOTO_CAPTURE, doc.getDocType())) {
						doc.setStorageType(NicTransactionAttachment.STORAGE_TYPE_REFERENCE);
						doc.setStorageRefId(photoLobId);
						doc.setUpdateDatetime(new Date());
						doc.setUpdateBy(nicCommandUtil.getSystemName());
						docList.add(doc);
					}
				}
				if (StringUtils.isNotBlank(thumbnailLobId)) {
					if (StringUtils.equals(NicTransactionAttachment.DOC_TYPE_THUMBNAIL_CAPTURE, doc.getDocType())) {
						doc.setStorageType(NicTransactionAttachment.STORAGE_TYPE_REFERENCE);
						doc.setStorageRefId(thumbnailLobId);
						doc.setUpdateDatetime(new Date());
						doc.setUpdateBy(nicCommandUtil.getSystemName());
						docList.add(doc);
					}
				}
				if (MapUtils.isNotEmpty(fingerprintLobIdMap)) {
					if (StringUtils.equals(NicTransactionAttachment.DOC_TYPE_FINGERPRINT, doc.getDocType())) {
						Short position = Short.parseShort(doc.getSerialNo());
						String lobId = fingerprintLobIdMap.get(position);
						if (StringUtils.isNotBlank(lobId)) {
							doc.setStorageType(NicTransactionAttachment.STORAGE_TYPE_REFERENCE);
							doc.setStorageRefId(lobId);
							doc.setUpdateDatetime(new Date());
							doc.setUpdateBy(nicCommandUtil.getSystemName());
							docList.add(doc);
						}
					}
				}
			}
			
			//update document list
			for (NicTransactionAttachment doc : docList) {
				transactionAttachmentService.saveOrUpdate(doc);
			}
		}
	}

	private void checkAndSetAfisKeyNumber(NicTransaction transObj) throws NicAfisDataServiceException, IdserverAgentServiceException, TransactionServiceException {
		String transactionId = transObj.getTransactionId();
		
		String oldPassportNo = transObj.getPrevPassportNo();
		String prevTransId = null;
		if (StringUtils.isNotEmpty(oldPassportNo)) {
			NicDocumentData documentData = documentDataService.findByDocNumber(oldPassportNo).getModel();
			if (documentData != null) {
				prevTransId = documentData.getId().getTransactionId();
				NicAfisData prevAfisData = nicAfisDataService.findById(prevTransId);
				if (prevAfisData != null)  {
					/*IdserverSubject idSvrSubj = null;
					IdserverImageFingerprint fingerprint1 = buildFingerprintTemplates(transObj.getNicTransactionAttachments());
					IdserverImageFingerprint fingerprint2 = buildFingerprintTemplatesByTransactionId(prevTransId);
					if (fingerprint1!=null && fingerprint2!=null) {
						logger.info("[before] inquiryFpComparison - "+transactionId+" vs "+prevTransId);
						idSvrSubj = idserverAgentService.inquiryFpComparison(transactionId, fingerprint1, fingerprint2);
						logger.info("[after] inquiryFpComparison - "+transactionId+" vs "+prevTransId+","+" : "+(idSvrSubj!=null?idSvrSubj.getHitDecision():"null"));
						if (idSvrSubj!=null && Boolean.TRUE.equals(idSvrSubj.getHitDecision())) {
							afisKeyNo = prevAfisData.getAfisKeyNo();
						}
					} */
					
					if (StringUtils.isBlank(afisKeyNo)) { // either FP 1:1 failed or FP is missing.
						logger.info("unable to match fingers - "+transactionId+" vs "+prevTransId);
						//compare names
						if (transObj.getNicRegistrationData() != null) {
							String firstName = transObj.getNicRegistrationData().getFirstnameFull();
							String surname = transObj.getNicRegistrationData().getSurnameFull();
							NicTransaction prevTransObj = nicTransactionService.findById(prevTransId, false, false, true, false);
							if (prevTransObj!=null && prevTransObj.getNicRegistrationData() != null) {
								String prevFirstName = prevTransObj.getNicRegistrationData().getFirstnameFull();
								String prevSurname = prevTransObj.getNicRegistrationData().getSurnameFull();
								logger.info("compare names - [{}][{}][{}] vs [{}][{}][{}]", new Object[]{transactionId, surname, firstName, prevTransId, prevSurname, prevFirstName});
								if (StringUtils.equalsIgnoreCase(firstName, prevFirstName) || StringUtils.equalsIgnoreCase(surname, prevSurname)) {
									afisKeyNo = prevAfisData.getAfisKeyNo();
								}
							}
						}
					}
				}
			}
		}
		
		
		if (StringUtils.isBlank(afisKeyNo)) {
			NicAfisData currentAfisData = nicAfisDataService.findById(transactionId);
			if (currentAfisData!=null && StringUtils.isNotBlank(currentAfisData.getAfisKeyNo())) {
				afisKeyNo = currentAfisData.getAfisKeyNo();
			} else {
				afisKeyNo = nicAfisDataService.getNextAfisKeyNo();
			}
		}
	}
	
	private IdserverImageFingerprint buildFingerprintTemplates(Collection<NicTransactionAttachment> transDocsList){
		IdserverImageFingerprint tpl = null;
		try {
			for(NicTransactionAttachment tDoc : transDocsList){
				if(StringUtils.equalsIgnoreCase(tDoc.getDocType(), "TPL")) {
					logger.info("setting TPL");
					tpl = new IdserverImageFingerprint();
					tpl.setFingerPosition(NicTransactionAttachment.TPL_POSITION);
					tpl.setImageType(IdserverImageType.TPL);
					tpl.setImageData(tDoc.getDocData());
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		logger.info("tpl exists:"+(tpl!=null));
		return tpl;
	}
	
	private IdserverImageFingerprint buildFingerprintTemplatesByTransactionId(String transactionId){
		IdserverImageFingerprint tpl = null;
		try {
			String docType = NicTransactionAttachment.DOC_TYPE_TPL;
			String serialNo = NicTransactionAttachment.DEFAULT_SERIAL_NO;
			List<NicTransactionAttachment> transDocsList = transactionAttachmentService.findNicTransactionAttachments(transactionId, docType, serialNo).getListModel();
			
			for(NicTransactionAttachment tDoc : transDocsList){
				if(StringUtils.equalsIgnoreCase(tDoc.getDocType(), "TPL")) {
					logger.info("setting TPL - {} ", transactionId);
					tpl = new IdserverImageFingerprint();
					tpl.setFingerPosition(NicTransactionAttachment.TPL_POSITION);
					tpl.setImageType(IdserverImageType.TPL);
					tpl.setImageData(tDoc.getDocData());
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		logger.info("tpl exists:"+(tpl!=null));
		return tpl;
	}

	//22 Sep 2014 (cw)
	private List<AbstractBiometricData<IdserverImageFingerprint>> buildFingerPrints(List<AbstractBiometricData<IdserverImageFingerprint>> biometricData, ArrayList<NicTransactionAttachment> transDocsList)
		throws Exception
	{
		try {
			for(NicTransactionAttachment tDoc : transDocsList){
				if(StringUtils.equalsIgnoreCase(tDoc.getDocType(), "FP")){
					IdserverImageFingerprint fingerprint = new IdserverImageFingerprint();
					fingerprint.setFingerPosition(FingerPosition.getInstance(Short.valueOf(tDoc.getSerialNo())).getKey());
					fingerprint.setImageType(IdserverImageType.WSQ);
					fingerprint.setImageData(tDoc.getDocData());		
					biometricData.add(fingerprint);	
				}
			}
			logger.info("converted biometric data size:"+biometricData.size());
		} catch (Exception e) {
			throw e;
		}
		return biometricData;
	}
	//22 Sep 2014 (cw)

	private IdserverPersonDetail buildPersonDetail(NicTransaction transObj) throws Exception {
		IdserverPersonDetail personDetail = null;
		
		try {
			
			if(transObj.getNicRegistrationData() != null){
				personDetail = new IdserverPersonDetail();
				personDetail.setFirstName(transObj.getNicRegistrationData().getFirstnameFull());
				personDetail.setLastName(transObj.getNicRegistrationData().getSurnameFull());
				personDetail.setGender(transObj.getNicRegistrationData().getGender());
				personDetail.setNationality(transObj.getNicRegistrationData().getNationality()); //TEMP hardcode
			}
			
		} catch (Exception e) {
			throw new Exception(e);
		}
		return personDetail;
	}
	
	private IdserverFaceInfo buildFace(Set<NicTransactionAttachment> transDocsList) throws Exception{
		
		IdserverFaceInfo faceInfo = null;
		int count = 0;
		try{
			
			faceInfo = new IdserverFaceInfo ();
			logger.info("transDocsList count:"+transDocsList.size());
			for(NicTransactionAttachment tDoc : transDocsList){
				String doctype = tDoc.getDocType();
				if (tDoc.getDocData()==null)	continue;
				
				if (doctype.contains("PH_CAP") || doctype.contains("TH_CAP")){
					count++;
					
					byte[] facial = tDoc.getDocData();
					IdserverImageFace face = new IdserverImageFace();
					face.setImageData(facial);
					face.setImageFormat(IdserverFaceFormat.JPG);
					
					faceInfo.setPose(IdserverFaceType.FRONTAL);
					
					if(doctype.contains("PH")){
						logger.info("setting photo");
						faceInfo.setMugshot(face);
					}
					if(doctype.contains("TH")){
						logger.info("setting thumbnail");
						faceInfo.setThumbnail(face);

					}
				}
			}
			
			if(count == 0){
				faceInfo = null;
			}
		}catch (Exception e) {
			throw new NicCommandException("Error in buildFace()", e);
		} 
		
		return faceInfo;
	}
	

	private Set<IdserverImageFingerprint> buildRegFingerPrints(Set<NicTransactionAttachment> transDocsList, NicTransaction transObj) throws Exception{
		
		Set<IdserverImageFingerprint> fpSet = new HashSet<IdserverImageFingerprint> ();
	
		try {
			
			for(NicTransactionAttachment tDoc : transDocsList){
				if (tDoc.getDocData()==null)	continue;
				if(StringUtils.isNotBlank(tDoc.getDocType()) && tDoc.getDocType().equalsIgnoreCase("FP")){
					//logger.info("flag FP");
					IdserverImageFingerprint fingerprint = new IdserverImageFingerprint();
					fingerprint.setFingerPosition(FingerPosition.getInstance(Short.valueOf(tDoc.getSerialNo())).getKey());
					
					logger.info("[{}] finger pos:"+fingerprint.getFingerPosition(), transObj.getTransactionId());
					
					fingerprint.setImageType(IdserverImageType.WSQ);
					fingerprint.setImageData(tDoc.getDocData());		
					fpSet.add(fingerprint);	
				}else{
					//logger.info("flag not FP");	
				}
			}
			
		} catch (Exception e) {
			throw new NicCommandException("Error in buildRegFingerPrints()", e);
		} 
			
		return fpSet;
	}
	
	private byte[] buildTemplates(Set<NicTransactionAttachment> transDocsList) throws NicCommandException {
		byte [] fpTemplate = null;
		
		try {
			for(NicTransactionAttachment tDoc : transDocsList){
				if (tDoc.getDocData()==null)	continue;
				
				if(StringUtils.isNotBlank(tDoc.getDocType()) && tDoc.getDocType().equalsIgnoreCase("TPL")){
					fpTemplate = tDoc.getDocData();
				}
			}
		} catch (Exception e) {
			throw new NicCommandException(e);
		} 
//		if (fpTemplate==null) {
//			throw new NicCommandException("Error in Afis Enrollment - Templates is empty.");
//		}
		return fpTemplate;
	}
	
	
	private void updateStatus(long objId, String state, int command){
		try {
			uploadJobService.updateJobState(objId, state, command);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void saveTransactionLog(String transactionId, String transactionStage,String transactionStatus, Integer retryCount, Date startTime, Date endTime, String logInfo, String logData) {
		NicTransactionLog transactionLog = new NicTransactionLog();
		
		transactionLog.setRefId(transactionId);
		transactionLog.setLogCreateTime(new Date());
		transactionLog.setTransactionStage(transactionStage);//TRANSACTION_STATE_PERSONALIZATION);
		transactionLog.setTransactionStatus(transactionStatus);
		transactionLog.setSiteCode(nicCommandUtil.getSystemSiteCodeFromParameter()); //get from 'CURRENT_SITE_CODE'
		transactionLog.setStartTime(startTime);
		transactionLog.setEndTime(endTime);
		transactionLog.setLogInfo(logInfo);
		transactionLog.setLogData(logData);
		//8-OCT-2013 (jp) - added hostname and system
		transactionLog.setWkstnId(nicCommandUtil.getHostName());
		transactionLog.setOfficerId(nicCommandUtil.getSystemName());
		transactionLog.setRetryCount(retryCount); //2016 Jan 05
		
		synchronized (NicTransactionLog.class) {
			nicTransactionLogService.save(transactionLog);
		}
	}
	
	public void setCommandUtil (NicCommandUtil commandUtil) {
		this.nicCommandUtil = commandUtil;
	}

	public void setIdserverAgentService(IdserverAgentService idserverAgentService) {
		this.idserverAgentService = idserverAgentService;
	}

	public void setNicTransactionService(NicTransactionService nicTransactionService) {
		this.nicTransactionService = nicTransactionService;
	}

	public void setUploadJobService(NicUploadJobService uploadJobService) {
		this.uploadJobService = uploadJobService;
	}

	public void setNicTransactionLogService(TransactionLogService nicTransactionLogService) {
		this.nicTransactionLogService = nicTransactionLogService;
	}

	public void setNicAfisDataService(NicAfisDataService nicAfisDataService) {
		this.nicAfisDataService = nicAfisDataService;
	}
	
	public void setDocumentDataService(DocumentDataService documentDataService) {
		this.documentDataService = documentDataService;
	}

	public void setTransactionAttachmentService(NicTransactionAttachmentService transactionAttachmentService) {
		this.transactionAttachmentService = transactionAttachmentService;
	}
}