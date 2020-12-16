package com.nec.asia.nic.comp.job.command;


import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.nec.asia.nic.comp.bufPersonInvestigation.service.BufPersonInvestigationService;
import com.nec.asia.nic.comp.job.utils.NicCommandUtil;
import com.nec.asia.nic.comp.listHandover.service.ListHandoverService;
import com.nec.asia.nic.comp.trans.domain.BaseModelList;
import com.nec.asia.nic.comp.trans.domain.BaseModelSingle;
import com.nec.asia.nic.comp.trans.domain.BufEppPersonInvestigation;
import com.nec.asia.nic.comp.trans.domain.ConfigurationWorkflow;
import com.nec.asia.nic.comp.trans.domain.NicSearchHitResult;
import com.nec.asia.nic.comp.trans.domain.NicSearchResult;
import com.nec.asia.nic.comp.trans.domain.NicTransaction;
import com.nec.asia.nic.comp.trans.domain.NicTransactionLog;
import com.nec.asia.nic.comp.trans.domain.NicUploadJob;
import com.nec.asia.nic.comp.trans.domain.SyncQueueJob;
import com.nec.asia.nic.comp.trans.domain.type.TransactionStatus;
import com.nec.asia.nic.comp.trans.service.ConfigurationWorkflowService;
import com.nec.asia.nic.comp.trans.service.DocumentDataService;
import com.nec.asia.nic.comp.trans.service.NicImmiHistoryService;
import com.nec.asia.nic.comp.trans.service.NicSearchHitResultService;
import com.nec.asia.nic.comp.trans.service.NicSearchResultService;
import com.nec.asia.nic.comp.trans.service.NicTransactionAttachmentService;
import com.nec.asia.nic.comp.trans.service.NicTransactionPackageService;
import com.nec.asia.nic.comp.trans.service.NicTransactionService;
import com.nec.asia.nic.comp.trans.service.NicUploadJobService;
import com.nec.asia.nic.comp.trans.service.SyncQueueJobService;
import com.nec.asia.nic.comp.trans.service.TransactionLogService;
import com.nec.asia.nic.framework.admin.rbac.service.UserService;
import com.nec.asia.nic.framework.admin.site.domain.SiteRepository;
import com.nec.asia.nic.framework.admin.site.service.SiteService;
import com.nec.asia.nic.framework.dao.DaoException;
/*
 * Modification History:
 * 
 * 19 Jan 2016 (cw) - create new cmd to route to investigation
 * 22 Feb 2016 (cw) - insert transaction log (RP) after approved.
 * 19 Apr 2016 (cw) - set update datetime and update by when update transaction status
 * 26 Apr 2016 (cw) - add txn log for FG status, reset job reprocess count
 */
public class NicInitInvestigationCommand extends BaseCommand<NicUploadJob> implements Command<NicUploadJob>{

	//UPDATE STATUS CODE
	public static final int JOB_STATE = 1;
	private static final int INVESTIGATION = 9;
	
	public static final String JOB_STATE_INVESTIGATION = "INVESTIGATION";
	public static final String CODE_STATUS_QUEUE_PENDING = "PENDING";
	public static final String JOB_STATUS_PENDING_INVESTIGATION = "PENDING_INVESTIGATION";
	public static final String QUEUE_OBJ_TYPE_BF = "BF";
	public static final String QUEUE_SITE_TTXL_MB = "18A";
	public static final String QUEUE_SITE_TTXL_MT = "18B";
	public static final String QUEUE_SITE_TTXL_MN = "18C";
	public static final String TRANSACTION_TYPE_LOS = "LOS";
	
	private NicCommandUtil nicCommandUtil;
	private NicUploadJobService uploadJobService;
	private NicTransactionService nicTransactionService;
	private TransactionLogService nicTransactionLogService;
	private NicSearchHitResultService nicSearchHitResultService;
	private NicSearchResultService nicSearchResultService;
	//private NicTransactionAttachmentService transactionAttachmentService;
	//private DocumentDataService documentDataService;
	//private NicImmiHistoryService nicImmiHistoryService;
	//private BufPersonInvestigationService bufPersonInvestigationService;
	private SiteService siteService;
	//private NicTransactionPackageService nicTransactionPackageService;
	//private UserService userService;
	private ListHandoverService listHandoverService;
	private SyncQueueJobService queueJobService;
	private ConfigurationWorkflowService configurationWorkflowService;
	
	@Override
	public void doSomething(NicUploadJob job) {
		this.setState(job.getJobType());//set job type as child key
		
		String jobStatus = null;
		try {
			NicUploadJob jobDBO = uploadJobService.findById(job.getWorkflowJobId());
			//logger.info("-----[{}] job.xml:{}", jobDBO.getTransactionId(), MiscXmlConvertor.parseObjectToXml(jobDBO));
			logger.info("-----[{}] jobDBO.getJobReprocessCount():{}", jobDBO.getTransactionId(), jobDBO.getJobReprocessCount());
			if(StringUtils.isNotBlank(jobDBO.getInvestigationType())){
				logger.info("-----[{}] investigation[{}] already assigned.", jobDBO.getTransactionId(), jobDBO.getInvestigationType());
				if (!StringUtils.equals(NicUploadJob.RECORD_STATUS_APPROVED, jobDBO.getInvestigationStatus()) && !StringUtils.equals(NicUploadJob.RECORD_STATUS_REJECTED, jobDBO.getInvestigationStatus())) {
					logger.info("-----[{}] pending decision in investigation.", jobDBO.getTransactionId());
					this.setState(GOTO_ERROR_CMD);
				} else if (StringUtils.equals(NicUploadJob.RECORD_STATUS_APPROVED, jobDBO.getInvestigationStatus())) {
					logger.info("-----[{}] approved decision in investigation.", jobDBO.getTransactionId());
					if (StringUtils.isBlank(jobDBO.getPersoRegisterStatus())) {
						//insert log for ready for personalization
						//*TODO* TRUNG TẠM THỜI XỬ LÝ THỬ THAY ĐỔI LUỒNG
						if(StringUtils.isBlank(jobDBO.getInvestigationStatus()) || !CheckUploadJobIStatusNEW(jobDBO.getInvestigationStatus())){
							updateStatus(jobDBO.getWorkflowJobId(), STATUS_INITIAL, INVESTIGATION);
						}
						//=====================================
						//updateStatus(jobDBO.getWorkflowJobId(), STATUS_INITIAL, INVESTIGATION);
						this.saveTransactionLog(jobDBO.getTransactionId(), JOB_STATE_INVESTIGATION, TransactionStatus.Personalization.getKey(), new Date(), new Date(), null, null, jobDBO.getJobReprocessCount());
					}
				}
			} else if(StringUtils.equals(jobDBO.getCpdCheckStatus(), STATUS_COMPLETED_WITH_HIT) 
					|| StringUtils.equals(jobDBO.getAfisCheckStatus(), STATUS_COMPLETED_WITH_HIT) 
					|| StringUtils.equals(jobDBO.getAfisVerifyStatus(), STATUS_COMPLETED_WITH_HIT)
					|| StringUtils.equals(jobDBO.getBlacklistCheckStatus(), STATUS_COMPLETED_WITH_HIT)
					|| StringUtils.equals(jobDBO.getIdentificationCheckStatus(), STATUS_COMPLETED_WITH_HIT) 
					){
				if (StringUtils.isBlank(jobDBO.getInvestigationType())) {
					//*TODO* TRUNG TẠM THỜI XỬ LÝ THỬ THAY ĐỔI LUỒNG
					if(StringUtils.isBlank(jobDBO.getInvestigationStatus()) || !CheckUploadJobIStatusNEW(jobDBO.getInvestigationStatus())){
						updateStatus(jobDBO.getWorkflowJobId(), STATUS_INITIAL, INVESTIGATION);
					}
					//=====================================
					//updateStatus(jobDBO.getWorkflowJobId(), STATUS_INITIAL, INVESTIGATION);
					uploadJobService.resetReprocessCount(jobDBO.getWorkflowJobId(), 0); //[cw] 2016 Apr 26
					
					//update nic_transaction.transaction_status field
					//NicTransaction transObj = null;
					//transObj = nicTransactionService.findById(jobDBO.getTransactionId(), false, false, false, false);
					//transObj.setTransactionStatus(NicTransactionService.TRANSACTION_STATUS_NIC_PENDING_INVESTIGATION);
					jobStatus = JOB_STATUS_PENDING_INVESTIGATION;
					//nicTransactionService.saveOrUpdate(transObj); 
					//[cw] to update both update datetime, and status.
					/*Tạm đóng cập nhật trạng thái. 04.08.2020*/
//					nicTransactionService.updateStatusByTransactionId(jobDBO.getTransactionId(), NicTransactionService.TRANSACTION_STATUS_NIC_PENDING_INVESTIGATION, nicCommandUtil.getSystemName(), nicCommandUtil.getHostName());
					this.saveTransactionLog(jobDBO.getTransactionId(), JOB_STATE_INVESTIGATION, NicTransactionService.TRANSACTION_STATUS_NIC_PENDING_INVESTIGATION, new Date(), new Date(), null, null, jobDBO.getJobReprocessCount()); //[cw] 2016 Apr 26
					
					//AFIS CHECK has been approved thru investigation, skip step
					logger.info("-----route to investigation.");
					this.setState(GOTO_ERROR_CMD);
				}
			} else {
				logger.info("-----investigation not required.");
				logger.info("-----[{}] auto approved decision in investigation.", jobDBO.getTransactionId());
				if (StringUtils.isBlank(jobDBO.getPersoRegisterStatus())) {
					//insert log for ready for personalization
					
					//*TODO* TRUNG TẠM THỜI XỬ LÝ THỬ THAY ĐỔI LUỒNG
					if(StringUtils.isBlank(jobDBO.getInvestigationStatus()) || !CheckUploadJobIStatusNEW(jobDBO.getInvestigationStatus())){
						updateStatus(jobDBO.getWorkflowJobId(), STATUS_INITIAL, INVESTIGATION);
					}
					//=====================================
					
					//updateStatus(jobDBO.getWorkflowJobId(), STATUS_INITIAL, INVESTIGATION); TRUNG TẠM ĐÓNG ĐỂ THỬ CÁI TRÊN
					this.saveTransactionLog(jobDBO.getTransactionId(), JOB_STATE_INVESTIGATION, NicTransactionService.TRANSACTION_STATUS_NIC_PENDING_INVESTIGATION, new Date(), new Date(), null, null, jobDBO.getJobReprocessCount());
				}
			}
			
			logger.info("[{}]CHECKING DATA BUF INVESTIGATION ===============", jobDBO.getTransactionId());
			if(!jobDBO.getJobType().equals(TRANSACTION_TYPE_LOS)){
				try {
					//Lấy danh sách nghi trùng đưa vào hành đợi
					logger.info("[{}]-----checking data history Immigration - IssuePassport.", jobDBO.getTransactionId());
					Long idCPD = null;
					Long idBMS = null;
					
					/*String statusTransaction = "Đã phát hành";
					String statusDocumentData = "Đang hoạt động";
					
					Boolean statusDocumentDataLock = false;
		
					List<String> listTranCPD = new ArrayList<String>();
					List<String> listTranBMS = new ArrayList<String>();
					InvestigationListInfoTargetsDto lstR = new InvestigationListInfoTargetsDto();
					List<InvestigationListInfoTargetDto> outputR = new ArrayList<InvestigationListInfoTargetDto>();
					List<ImmiHistory> listImmi = new ArrayList<ImmiHistory>();*/
					
					List<NicSearchResult> searchResults = nicSearchResultService.findAllByJobId(jobDBO.getWorkflowJobId());
					if(searchResults != null && searchResults.size() > 0){
						for(NicSearchResult sR_ : searchResults){
							if(sR_.getTypeSearch().equals(NicSearchResult.TYPE_SEARCH__CPD) && sR_.getHasHit()){
								if(idCPD == null || idCPD < sR_.getSearchResultId()){
									idCPD = sR_.getSearchResultId();									
								}
							}
							else if(sR_.getTypeSearch().equals(NicSearchResult.TYPE_SEARCH__FP_1_N) && sR_.getHasHit() != null && sR_.getHasHit()){
								if(idBMS == null || idBMS < sR_.getSearchResultId()){
									idBMS = sR_.getSearchResultId();									
								}
							}
						}
					}
					
					logger.info("[{}]CHECKING WITH CPD==============", jobDBO.getTransactionId());
					
					/*Lưu danh sách transaction đối tượng nghi trùng đã được đưa vào hàng đợi*/
					List<String> tranList = new ArrayList<String>();
//					NicTransaction txn = nicTransactionService.findById(jobDBO.getTransactionId());
					
					/*if(jobDBO.getWorkflowJobRunAgainCount() == 1){
						this.addObjToQueueJob(job.getTransactionId(), QUEUE_OBJ_TYPE_BF, job.getNicTransaction().getRegSiteCode(), null, jobDBO.getTransactionId());//PA											
					}
					if(jobDBO.getWorkflowJobRunAgainCount() > 1){
						String siteTTXL = this.responseSite(job.getNicTransaction().getRegSiteCode());
						logger.info("GetSiteCode: " + siteTTXL);
						if (StringUtils.isNotBlank(siteTTXL)) {
							this.addObjToQueueJob(job.getTransactionId(), QUEUE_OBJ_TYPE_BF, siteTTXL, null, jobDBO.getTransactionId());											
						}
					}*/
					/*Đưa đối tượng nghi trùng vào hàng đợi chờ đồng bộ về PA và TTXL*/
					/*0101- Đóng 15/07/2020 để thay đổi truy vấn vào bản Buffer
					Boolean checkBuffer = true;
					if(idBMS != null){
						List<NicSearchHitResult> listHitBMS = nicSearchHitResultService.findHitPositions(null, idBMS);
						if(listHitBMS != null && listHitBMS.size() > 0){
							for(NicSearchHitResult sHR : listHitBMS){								
								if(sHR.getDataSource().equals(NicSearchHitResult.hitInfo_DataSource_BMS)){
									//Id của đối tượng trùng
									String transactionId_BMS = sHR.getTransactionIdHit();
									if(this.checkTranIdQueue(tranList, transactionId_BMS)){
										logger.info("save data transaction bms to queue, transaction_bms: " + transactionId_BMS);  									
										if(jobDBO.getWorkflowJobRunAgainCount() == 1){
											this.addObjToQueueJob(transactionId_BMS, QUEUE_OBJ_TYPE_BF, jobDBO.getNicTransaction().getRegSiteCode(), sHR.getHitResultId(), jobDBO.getTransactionId());//PA											
										}
										if(jobDBO.getWorkflowJobRunAgainCount() > 1){
											String siteTTXL = this.responseSite(jobDBO.getNicTransaction().getRegSiteCode());
											this.addObjToQueueJob(transactionId_BMS, QUEUE_OBJ_TYPE_BF, siteTTXL, sHR.getHitResultId(), jobDBO.getTransactionId());											
										}
										tranList.add(transactionId_BMS);
									}
								}
							}
							checkBuffer = false;
						}
					}
					
					if(idCPD != null && (jobDBO.getWorkflowJobRunAgainCount() > 1 || txn.getRegSiteCode().equals(QUEUE_SITE_TTXL_MB) || txn.getRegSiteCode().equals(QUEUE_SITE_TTXL_MT) || txn.getRegSiteCode().equals(QUEUE_SITE_TTXL_MN))){						
						List<NicSearchHitResult> listHitCPD = nicSearchHitResultService.findHitPositions(null, idCPD);
						if(listHitCPD != null && listHitCPD.size() > 0){
							for(NicSearchHitResult sHR : listHitCPD){
								if(sHR.getDataSource().equals(NicSearchHitResult.hitInfo_DataSource_CPD)){
									String transactionId_CPD = sHR.getTransactionIdHit();
									
									if(this.checkTranIdQueue(tranList, transactionId_CPD)){
										logger.info("save data transaction cpd to queue, transaction_cpd: " + transactionId_CPD);  										
										String siteTTXL = this.responseSite(jobDBO.getNicTransaction().getRegSiteCode());
										this.addObjToQueueJob(transactionId_CPD, QUEUE_OBJ_TYPE_BF, siteTTXL, null, jobDBO.getTransactionId());//PA						
										tranList.add(transactionId_CPD);
									}
								}
							}
							checkBuffer = false;
						}
					}
					
					
					if(checkBuffer){
						logger.info("Profile does not have any suspicious objects, transactionId: " + jobDBO.getTransactionId());  										
						if(jobDBO.getWorkflowJobRunAgainCount() == 1 || txn.getRegSiteCode().equals(QUEUE_SITE_TTXL_MB) || txn.getRegSiteCode().equals(QUEUE_SITE_TTXL_MT) || txn.getRegSiteCode().equals(QUEUE_SITE_TTXL_MN)){
							this.addObjToQueueJob(jobDBO.getTransactionId(), QUEUE_OBJ_TYPE_BF, jobDBO.getNicTransaction().getRegSiteCode(), null, null);								
						}else{
							if(jobDBO.getWorkflowJobRunAgainCount() > 1){
								String siteTTXL = this.responseSite(jobDBO.getNicTransaction().getRegSiteCode());
								this.addObjToQueueJob(jobDBO.getTransactionId(), QUEUE_OBJ_TYPE_BF, siteTTXL, null, null);//PA														
							}
						}
					}
					0101 - END */
					
					
					
					///Dữ liệu kiểm tra với CPD
					/* if(idCPD != null){
						//Lấy dữ liệu trùng trong bảng EPP_SEARCH_HIT_RESULT
						List<NicSearchHitResult> listHitCPD = nicSearchHitResultService.findHitPositions(null, idCPD);
						if(listHitCPD != null && listHitCPD.size() > 0){
							for(NicSearchHitResult sHR : listHitCPD){
								String passportNo = "";
								InvestigationListInfoTargetDto outPut = new InvestigationListInfoTargetDto();
								InvestigationListInfoTargetDto outPutP = null;
								//HistoryPassportInfo passportInfo = null;
								if(sHR.getDataSource().equals(NicSearchHitResult.hitInfo_DataSource_CPD)){
									//Lấy Id của đối tượng trùng
									String transactionId_CPD = "";
									transactionId_CPD = sHR.getTransactionIdHit();				
									if(StringUtils.isNotEmpty(transactionId_CPD)){
										//Lấy dữ liệu thông tin hồ sơ của transaction
										NicUploadJob dtUpJob = uploadJobService.getUploadJobByTransactionIdSinger(null, transactionId_CPD);
										NicTransaction nicTran = nicTransactionService.getNicTransactionById(transactionId_CPD);
										if(nicTran != null){
											//Kiểm tra trạng thái của hồ sơ
											if(!nicTran.getTransactionStatus().equals(NicTransaction.TRANSACTION_STATUS_RIC_ISSUED)){//TH chưa phát hành
												statusTransaction = "Đang xử lý - Giai đoạn: " + nicTran.getTransactionStatus();
											}
											else {
												//Thông tin hộ chiếu
												Collection<NicDocumentData> nicDocs = documentDataService.findByTransactionId(transactionId_CPD);
												if(nicDocs != null && nicDocs.size() > 0){
													//passportInfo = new HistoryPassportInfo();
													outPutP = new InvestigationListInfoTargetDto();
													List<NicDocumentData> nicDocs_ = new ArrayList<NicDocumentData>(nicDocs);
													NicDocumentData nicDoc = nicDocs_.get(0);
													passportNo = nicDoc.getId().getPassportNo();
													if(nicDoc.getStatus().equals(NicDocumentData.DOCUMENT_DATA_STATUS_NONE)){
														statusDocumentData = "Đã khóa";
														statusDocumentDataLock = true;
													}
													else if(nicDoc.getStatus().equals(NicDocumentData.DOCUMENT_DATA_STATUS_PERSONALIZED))
													{
														statusDocumentData = "Cá thể hóa";
													}
													
													outPutP.setPackageId_O(nicTran.getPackageID());
													outPutP.setTransactionId_O(transactionId_CPD);
													outPutP.setTypeTransaction_O(nicTran.getTransactionType());
													outPutP.setReg_O(nicTran.getRegSiteCode());
													outPutP.setStatus_O(statusDocumentData);
													outPutP.setPassportNo_O(nicDoc.getId().getPassportNo());
													
													String issuePP = "";
													if(nicDoc.getDateOfIssue() != null){
														String pattern = "dd/MM/yyyy";
														DateFormat df = new SimpleDateFormat(pattern);
														issuePP = df.format(nicDoc.getDateOfIssue());
														outPutP.setIssueDatePassport_O(issuePP);
													}
													
													String receivePP = "";
													if(nicDoc.getReceiveDatetime() != null){
														String pattern = "dd/MM/yyyy";
														DateFormat df = new SimpleDateFormat(pattern);
														receivePP = df.format(nicDoc.getReceiveDatetime());
														
													}
													outPutP.setPayDatePassport_O(receivePP);
													outPutP.setPayPlacePassport_O(nicTran.getIssSiteCode());
													
													outPutP.setPersonRecieve_O("");
													outPutP.setRemark_O("");

												}
												else{
													logger.info("Not found information Document data in database TransactionId: " + transactionId_CPD);
												}	
											}
											
											//Thông tin cá nhân
											NicRegistrationData nicRegis = nicTran.getNicRegistrationData();
											logger.info("[" + nicRegis.getTransactionId() + "]nicRegis: " + nicRegis.getSurnameLine1());
											if(nicRegis != null){
												outPut.setFullName_O(nicRegis.getSurnameLine1());
												//outPut.setGender_O(nicRegis.getGender());
												if(nicRegis.getGender() != null){
													outPut.setGender_O(nicRegis.getGender().equals("M") ? "Nam" : "Nữ");
												}else {
													outPut.setGender_O("Không");
												}
												String dob = "";
												if(nicRegis.getDateOfBirth() != null){
													String pattern = "dd/MM/yyyy";
													DateFormat df = new SimpleDateFormat(pattern);
													dob = df.format(nicRegis.getDateOfBirth());
												}
												outPut.setDob_O(dob);
												outPut.setNin_O(nicTran.getNin());
												outPut.setPob_O(nicRegis.getPlaceOfBirth());
												outPut.setReligion_O(nicRegis.getReligion());
												outPut.setNation_O(nicRegis.getNation());
												outPut.setPhone_O(nicRegis.getContactNo());
												outPut.setTransactionId_O(transactionId_CPD);
												//Xử lý ảnh đối tượng 
												List<NicTransactionAttachment> photoList = transactionAttachmentService
														.findNicTransactionAttachments(
																transactionId_CPD,
																NicTransactionAttachment.DOC_TYPE_PHOTO_CAPTURE,
																NicTransactionAttachment.DEFAULT_SERIAL_NO);
												if (CollectionUtils.isNotEmpty(photoList)
														&& photoList.size() > 0) {
													String photoStr = Base64.encodeBase64String(photoList.get(0)
															.getDocData());
													outPut.setPhoto_O(photoStr);
												}
												
												//Thông tin người thân
												outPut.setFullNameFather_O(nicRegis.getFatherSurname() + " " + nicRegis.getFatherFirstname());
												String fatherDob = "";
												if(nicRegis.getFatherDob() != null){
													String pattern = "dd/MM/yyyy";
													DateFormat df = new SimpleDateFormat(pattern);
													fatherDob = df.format(nicRegis.getFatherDob());
												}
												outPut.setDobFather_O(fatherDob);
												
												outPut.setFullNameMother_O(nicRegis.getMotherSurname() + " " + nicRegis.getMotherFirstname());
												String motherDob = "";
												if(nicRegis.getMotherDob() != null){
													String pattern = "dd/MM/yyyy";
													DateFormat df = new SimpleDateFormat(pattern);
													motherDob = df.format(nicRegis.getMotherDob());
												}
												outPut.setDobMother_O(motherDob);
												
												String spouserDob = "";
												if(nicRegis.getSpouseDob()!= null){
													String pattern = "dd/MM/yyyy";
													DateFormat df = new SimpleDateFormat(pattern);
													spouserDob = df.format(nicRegis.getSpouseDob());
												}
												outPut.setDobSpouser_O(spouserDob);
											}
											outputR.add(outPut);
											
											//else
											//{
												//logger.info("Not found information Registration data in database TransactionId: " + transactionId_CPD);
											//}
											
											//Kiểm tra đối với dữ liệu lịch sử XNC của đối tượng
											
											listImmi = nicImmiHistoryService.findByNinPPno(nicTran.getNin(), passportNo);
											
											//listTranCPD.add(transactionId_CPD);
											//Đóng gói dữ liệu History
											String xmlImmi = "";
											String xmlIssue = "";
											logger.info("[{}]XML data - TID: ", transactionId_CPD);
											if(outPutP != null){
												//lstR.setInfoTarget(outputR);
												JAXBContext jaxbContext = JAXBContext.newInstance(InvestigationListInfoTargetsDto.class);
												Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
												StringWriter sw = new StringWriter();
												jaxbMarshaller.marshal(outPutP, sw);
												xmlIssue = sw.toString();
											}
											
											if(listImmi != null && listImmi.size() > 0){
												ImmiHistorys listImmis = new ImmiHistorys();
												listImmis.setImmiHistory(listImmi);
												JAXBContext jaxbContext = JAXBContext.newInstance(ImmiHistorys.class);
												Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
												StringWriter sw = new StringWriter();
												jaxbMarshaller.marshal(listImmis, sw);
												xmlImmi = sw.toString();
											}
											
											EppBufFamilyDto family = new EppBufFamilyDto();
											List<EppPersonDto> lstPerson = new ArrayList<EppPersonDto>();
											family.setFatherSurname(dtUpJob.getNicTransaction().getNicRegistrationData().getFatherSurname());
											family.setMotherSurname(dtUpJob.getNicTransaction().getNicRegistrationData().getMotherSurname());
											family.setSpouseSurname(dtUpJob.getNicTransaction().getNicRegistrationData().getSpouseSurname());
											
											family.setFatherDob(dtUpJob.getNicTransaction().getNicRegistrationData().getFatherDob());
											family.setMotherDob(dtUpJob.getNicTransaction().getNicRegistrationData().getMotherDob());
											family.setSpouseDob(dtUpJob.getNicTransaction().getNicRegistrationData().getSpouseDob());
											
											String xmlPerson = dtUpJob.getNicTransaction().getInfoPerson();
											
											if(StringUtils.isNotEmpty(xmlPerson)){
												JSONObject xmlJSONObj = XML.toJSONObject(xmlPerson);
												if(xmlJSONObj != null){
													if(xmlJSONObj.has("EPP_PERSONS") && !xmlJSONObj.isNull("EPP_PERSONS")){
														JSONObject resp = xmlJSONObj.getJSONObject("EPP_PERSONS");
														if(resp.has("EPP_PERSON") && !resp.isNull("EPP_PERSON")){
															Object item = resp.get("EPP_PERSON");
															try{
																if (item instanceof JSONArray){
																	JSONArray arrayResp = (JSONArray) item;
																	for (int i = 0; i < arrayResp.length(); i++) {
																		EppPersonDto person = new EppPersonDto();
																		JSONObject myResponse = new JSONObject();
																		myResponse = arrayResp.getJSONObject(i);
																		String id = "";
																		if(myResponse.has("PLACE_OF_BIRTH_ID") && !myResponse.isNull("PLACE_OF_BIRTH_ID")){
																			person.setPlaceOfBirthId(myResponse.getString("PLACE_OF_BIRTH_ID"));
																		}
																		if(myResponse.has("SEARCH_NAME") && !myResponse.isNull("SEARCH_NAME")){
																			person.setSearchName(myResponse.getString("SEARCH_NAME"));
																		}
																		if(myResponse.has("NAME") && !myResponse.isNull("NAME")){
																			person.setName(myResponse.getString("NAME"));
																		}
																		if(myResponse.has("DATE_OF_BIRTH") && !myResponse.isNull("DATE_OF_BIRTH")){
																			person.setDateOfBirth(String.valueOf(myResponse.getInt("DATE_OF_BIRTH")));
																		}
																		if(myResponse.has("GENDER") && !myResponse.isNull("GENDER")){
																			person.setGender(myResponse.getString("GENDER"));
																		}
																		if(myResponse.has("ID") && !myResponse.isNull("ID")){
																			id = String.valueOf(myResponse.getInt("ID"));
																			person.setId(id);
																		}
																		if(myResponse.has("TYPE_") && !myResponse.isNull("TYPE_")){
																			person.setType_(myResponse.getString("TYPE_"));
																		}
																		if(myResponse.has("NATIONALITY_ID") && !myResponse.isNull("NATIONALITY_ID")){
																			person.setNationalityId(myResponse.getString("NATIONALITY_ID"));
																		}
																		
																		lstPerson.add(person);
																	}
															    }
																else
															    {
																	EppPersonDto person = new EppPersonDto();
																	JSONObject myResponse = (JSONObject) item;
																	String id = "";
																	if(myResponse.has("PLACE_OF_BIRTH_ID") && !myResponse.isNull("PLACE_OF_BIRTH_ID")){
																		person.setPlaceOfBirthId(myResponse.getString("PLACE_OF_BIRTH_ID"));
																	}
																	if(myResponse.has("SEARCH_NAME") && !myResponse.isNull("SEARCH_NAME")){
																		person.setSearchName(myResponse.getString("SEARCH_NAME"));
																	}
																	if(myResponse.has("NAME") && !myResponse.isNull("NAME")){
																		person.setName(myResponse.getString("NAME"));
																	}
																	if(myResponse.has("DATE_OF_BIRTH") && !myResponse.isNull("DATE_OF_BIRTH")){
																		person.setDateOfBirth(String.valueOf(myResponse.getInt("DATE_OF_BIRTH")));
																	}
																	if(myResponse.has("GENDER") && !myResponse.isNull("GENDER")){
																		person.setGender(myResponse.getString("GENDER"));
																	}
																	if(myResponse.has("ID") && !myResponse.isNull("ID")){
																		id = String.valueOf(myResponse.getInt("ID"));
																		person.setId(id);
																	}
																	if(myResponse.has("TYPE_") && !myResponse.isNull("TYPE_")){
																		person.setType_(myResponse.getString("TYPE_"));
																	}
																	if(myResponse.has("NATIONALITY_ID") && !myResponse.isNull("NATIONALITY_ID")){
																		person.setNationalityId(myResponse.getString("NATIONALITY_ID"));
																	}
																	
																	lstPerson.add(person);
															    }
															}catch(Exception e){
																
															}
														}
													}
												}
											}
											
											String xmlFamily = "";
											JAXBContext jaxbContext = JAXBContext.newInstance(EppBufFamilyDto.class, EppPersonDto.class);
											Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
											StringWriter sw = new StringWriter();
											jaxbMarshaller.marshal(family, sw);
											xmlFamily = sw.toString();
											
											String status = "Đang xử lý";
											NicDocumentData docData = null;
											Collection<NicDocumentData> docData_ = documentDataService
													.findByTransactionId(dtUpJob.getTransactionId());
											if(docData_ != null && docData_.size() > 0){
												List<NicDocumentData> nicDocs_ = new ArrayList<NicDocumentData>(
														docData_);
												docData = nicDocs_.get(0);
												if(docData == null && dtUpJob.getInvestigationStatus().equals(NicUploadJob.RECORD_STATUS_APPROVE_PERSO)){
													status = "Đã phê duyệt. Chờ in";
												}
												else if(docData != null){
													if(docData.getStatus().equals(NicDocumentData.DOCUMENT_DATA_STATUS_ISSUANCE) && dtUpJob.getNicTransaction().getTransactionStatus().equals(NicTransaction.TRANSACTION_STATUS_RIC_ISSUED)){
														status = "Đã trả hộ chiếu cho công dân";
													}
													else {
														status = "Đã in. Chờ trả hộ chiếu";
													}
												}
												
											}
											EppBufInfoDocDto info = new EppBufInfoDocDto();
											
											info.setTransactionId(dtUpJob.getTransactionId());
											info.setRecieptNo(dtUpJob.getReceiptNo());
											if(dtUpJob.getNicTransaction().getTransactionType().equals("REP"))
												info.setTypeTransaction("Thay thế");
											else if(dtUpJob.getNicTransaction().getTransactionType().equals("REP_LOS"))
												info.setTypeTransaction("Thay thế do mất hỏng");
											else
												info.setTypeTransaction("Cấp mới");
											
											SiteRepository siteR = siteService.getSiteRepoById(dtUpJob.getNicTransaction().getRegSiteCode());
											if(siteR != null)
												info.setRegSiteCode(siteR.getSiteName());
											else
												info.setRegSiteCode(dtUpJob.getNicTransaction().getRegSiteCode());
											
											info.setStatus(status);
											
											
											String nameApprover = "";
											String levelApprover = "";
											if(StringUtils.isNotEmpty(dtUpJob.getNicTransaction().getLeaderOfficerId())){
												Users user = userService.findById(dtUpJob.getNicTransaction().getLeaderOfficerId());
												if(user != null){
													nameApprover = user.getUserName();
													levelApprover = user.getPosition();
												}
												else{
													nameApprover = dtUpJob.getNicTransaction().getLeaderOfficerId();
												}
											}
											info.setNameApprover(nameApprover);
											info.setLevelApprover(levelApprover);
											
											if(docData != null){
												info.setPassportNo(docData.getId().getPassportNo());
												info.setIssuePassportDate(docData.getDateOfIssue());
												info.setReleaseDate(docData.getIssueDatetime());
											}
											info.setReceiver(dtUpJob.getNicTransaction().getReceiver());
											info.setRecieptNo(dtUpJob.getNicTransaction().getPackageID());
											String xmlInfo = "";
											JAXBContext jaxbContextInfo = JAXBContext.newInstance(EppBufInfoDocDto.class);
											Marshaller jaxbMarshallerInfo = jaxbContextInfo.createMarshaller();
											StringWriter swI = new StringWriter();
											jaxbMarshallerInfo.marshal(info, swI);
											xmlInfo = swI.toString();
											
											//Lưu lại dữ liệu History
											logger.info("[{}]SAVE HISTORY =================", transactionId_CPD);
											
											BufEppPersonInvestigation objBuf = bufPersonInvestigationService.findByTranId(transactionId_CPD);
											if(objBuf == null){
												objBuf = new BufEppPersonInvestigation();
												objBuf.setCreateDatetime(new Date());
												objBuf.setCreateBy("SYSTEM");
												objBuf.setCreateWkstnId("SYSTEM-MACHINE");
											}
											else
											{
												objBuf.setUpdateBy("SYSTEM");
												objBuf.setUpdateWkstnId("SYSTEM-MACHINE");
												objBuf.setUpdateDatetime(new Date());
											}
											
											objBuf.setTransactionId(transactionId_CPD);
											objBuf.setDataHistoryPassport(xmlIssue);
											objBuf.setDataImmihistory(xmlImmi);
											objBuf.setDataFamily(xmlFamily);
											objBuf.setDataInfoDocument(xmlInfo);
											bufPersonInvestigationService.saveOrUpdate(objBuf);
											//outputR.add(outPut);
										}
										else
										{
											logger.info("Not found information Transaction in database TransactionId: " + transactionId_CPD);
										}
									}
										
								}
							}
						}
						else
						{
							logger.info("[{}]Not found list hit CPD by idCPD: " + idCPD, jobDBO.getTransactionId());
						}
						
					}*/
					
					//logger.info("[{}]CHECKING WITH BMS=================", jobDBO.getTransactionId());
					///Dữ liệu kiểm tra với BMS
					/*if(idBMS != null){
						//Lấy dữ liệu trùng trong bảng EPP_SEARCH_HIT_RESULT
						List<NicSearchHitResult> listHitBMS = nicSearchHitResultService.findHitPositions(null, idBMS);
						if(listHitBMS != null && listHitBMS.size() > 0){
							for(NicSearchHitResult sHR : listHitBMS){
								String passportNo = "";
								if(sHR.getDataSource().equals(NicSearchHitResult.hitInfo_DataSource_BMS)){
									//Lấy Id của đối tượng trùng
									String transactionId_BMS = "";
									transactionId_BMS = sHR.getTransactionIdHit();
									if(StringUtils.isNotEmpty(transactionId_BMS)){
										//Kiểm tra trong ListCheckCPD có trùng transactionID không. Nếu có thì chỉ thêm dữ liệu BMS vào vị trí đối tượng
										if(listTranCPD.contains(transactionId_BMS) && outputR != null && outputR.size() > 0){
											InvestigationListInfoTargetDto updateObj = outputR.get(listTranCPD.indexOf(transactionId_BMS));
											updateObj.setScoreBMSall_O(sHR.getHitInfo());
											int score = 0;
											//Tính số điểm trung bình cho vân tay
											//Kiểm tra chuỗi dữ liệu
											if(StringUtils.isNotEmpty(sHR.getHitInfo())){
												String[] listHit = sHR.getHitInfo().split(","); //Tách chuỗi theo dấu ","
												if(listHit.length > 0){
													for(int i = 0; i < listHit.length; i++){
													   if(Double.parseDouble((listHit[i].split("=")[1])) > score){
														   double d = Double.parseDouble((listHit[i].split("=")[1]));
														   score = (int) d;
													   }
													}
												}
											}
											updateObj.setScoreBMS_O(score);
											
											//Xóa dữ liệu cũ trong 2 list
											listTranCPD.remove(transactionId_BMS);
											outputR.remove(listTranCPD.indexOf(transactionId_BMS));
											
											//Cập nhật dữ liệu mới
											listTranCPD.add(transactionId_BMS);
											outputR.add(updateObj);
										}
										else{
											InvestigationListInfoTargetDto outPut = new InvestigationListInfoTargetDto();
											InvestigationListInfoTargetDto outPutP = null;
											outPut.setScoreBMSall_O(sHR.getHitInfo());
											int score = 0;
											//Tính số điểm trung bình cho vân tay
											//Kiểm tra chuỗi dữ liệu
											if(StringUtils.isNotEmpty(sHR.getHitInfo())){
												String[] listHit = sHR.getHitInfo().split(","); //Tách chuỗi theo dấu ","
												if(listHit.length > 0){
													for(int i = 0; i < listHit.length; i++){
													   if(Double.parseDouble((listHit[i].split("=")[1])) > score){
														   double d = Double.parseDouble((listHit[i].split("=")[1]));
													   	   score = (int) d;
													   }
													}
												}
											}
											outPut.setScoreBMS_O(score);
											
											//Lấy dữ liệu thông tin hồ sơ của transaction
											NicUploadJob nicUp = uploadJobService.getUploadJobByTransactionIdSinger(null, transactionId_BMS);
											NicTransaction nicTran = nicTransactionService.getNicTransactionById(transactionId_BMS);
											if(nicTran != null){
												//Kiểm tra trạng thái của hồ sơ
												if(!nicTran.getTransactionStatus().equals(NicTransaction.TRANSACTION_STATUS_RIC_ISSUED)){//TH chưa phát hành
													statusTransaction = "Đang xử lý - Giai đoạn: " + nicTran.getTransactionStatus();
												}
												else{
													//Thông tin hộ chiếu
													Collection<NicDocumentData> nicDocs = documentDataService.findByTransactionId(transactionId_BMS);
													if(nicDocs != null && nicDocs.size() > 0){
														List<NicDocumentData> nicDocs_ = new ArrayList<NicDocumentData>(nicDocs);
														NicDocumentData nicDoc = nicDocs_.get(0);
														outPutP = new InvestigationListInfoTargetDto();
														if(nicDoc.getStatus().equals(NicDocumentData.DOCUMENT_DATA_STATUS_NONE)){
															statusDocumentData = "Đã khóa";
															statusDocumentDataLock = true;
															passportNo = nicDoc.getId().getPassportNo();
														}
														else if(nicDoc.getStatus().equals(NicDocumentData.DOCUMENT_DATA_STATUS_PERSONALIZED))
														{
															statusDocumentData = "Cá thể hóa";
														}
														
														outPutP.setPackageId_O(nicTran.getPackageID());
														outPutP.setTransactionId_O(transactionId_BMS);
														outPutP.setTypeTransaction_O(nicTran.getTransactionType());
														outPutP.setReg_O(nicTran.getRegSiteCode());
														outPutP.setStatus_O(statusDocumentData);
														outPutP.setPassportNo_O(nicDoc.getId().getPassportNo());
														
														String issuePP = "";
														if(nicDoc.getDateOfIssue() != null){
															String pattern = "dd/MM/yyyy";
															DateFormat df = new SimpleDateFormat(pattern);
															issuePP = df.format(nicDoc.getDateOfIssue());
														}
														outPutP.setIssueDatePassport_O(issuePP);
														
														String receivePP = "";
														if(nicDoc.getReceiveDatetime() != null){
															String pattern = "dd/MM/yyyy";
															DateFormat df = new SimpleDateFormat(pattern);
															receivePP = df.format(nicDoc.getReceiveDatetime());
														}
														outPutP.setPayDatePassport_O(receivePP);
														outPutP.setPayPlacePassport_O(nicTran.getIssSiteCode());
														outPutP.setPersonRecieve_O("");
														outPutP.setRemark_O("");
														
													}
													else{
														logger.info("Not found information Document data in database TransactionId: " + transactionId_BMS);
													}
												}

												//Thông tin cá nhân
												NicRegistrationData nicRegis = nicTran.getNicRegistrationData();
												logger.info("[" + nicRegis.getTransactionId() + "]nicRegis: " + nicRegis.getSurnameLine1());
												if(nicRegis != null){
													
													outPut.setFullName_O(nicRegis.getSurnameLine1());
													//outPut.setGender_O(nicRegis.getGender());
													if(nicRegis.getGender() != null){
														outPut.setGender_O(nicRegis.getGender().equals("M") ? "Nam" : "Nữ");
													}else {
														outPut.setGender_O("Không");
													}
													String dob = "";
													if(nicRegis.getDateOfBirth() != null){
														String pattern = "dd/MM/yyyy";
														DateFormat df = new SimpleDateFormat(pattern);
														dob = df.format(nicRegis.getDateOfBirth());
													}
													outPut.setDob_O(dob);
													outPut.setNin_O(nicTran.getNin());
													outPut.setPob_O(nicRegis.getPlaceOfBirth());
													outPut.setReligion_O(nicRegis.getReligion());
													outPut.setNation_O(nicRegis.getNation());
													outPut.setPhone_O(nicRegis.getContactNo());
													
													//Xử lý ảnh đối tượng 
													List<NicTransactionAttachment> photoList = transactionAttachmentService
															.findNicTransactionAttachments(
																	transactionId_BMS,
																	NicTransactionAttachment.DOC_TYPE_PHOTO_CAPTURE,
																	NicTransactionAttachment.DEFAULT_SERIAL_NO);
													if (CollectionUtils.isNotEmpty(photoList)
															&& photoList.size() > 0) {
														String photoStr = Base64.encodeBase64String(photoList.get(0)
																.getDocData());
														outPut.setPhoto_O(photoStr);
													}
													
													//Thông tin người thân
													outPut.setFullNameFather_O(nicRegis.getFatherSurname() + " " + nicRegis.getFatherFirstname());
													String fatherDob = "";
													if(nicRegis.getFatherDob() != null){
														String pattern = "dd/MM/yyyy";
														DateFormat df = new SimpleDateFormat(pattern);
														fatherDob = df.format(nicRegis.getFatherDob());
													}
													outPut.setDobFather_O(fatherDob);
													
													outPut.setFullNameMother_O(nicRegis.getMotherSurname() + " " + nicRegis.getMotherFirstname());
													String motherDob = "";
													if(nicRegis.getMotherDob() != null){
														String pattern = "dd/MM/yyyy";
														DateFormat df = new SimpleDateFormat(pattern);
														motherDob = df.format(nicRegis.getMotherDob());
													}
													outPut.setDobMother_O(motherDob);
													
													String spouserDob = "";
													if(nicRegis.getSpouseDob()!= null){
														String pattern = "dd/MM/yyyy";
														DateFormat df = new SimpleDateFormat(pattern);
														spouserDob = df.format(nicRegis.getSpouseDob());
													}
													outPut.setDobSpouser_O(spouserDob);
												}
												outputR.add(outPut);
												
												
												//else
												//{
													//logger.info("Not found information Registration data in database TransactionId: " + transactionId_BMS);
												//}
												
												//Kiểm tra danh sách giấy tờ mất/hủy
												//Tìm kiếm trong danh sách TRANSACTION với kiểu LOS (chỉ tìm với hộ chiếu khóa RIC_DEACTIVATED)
//											    List<NicTransaction> listLost = new ArrayList<NicTransaction>();
//												if(statusDocumentDataLock && nicTran.getTransactionStatus().equals(NicTransaction.TRANSACTION_STATUS_RIC_DEACTIVATED)){
//													//Kiểm tra qua số hộ chiếu
//													NicTransaction nicTranLost_PPno = nicTransactionService.getNicTransactionByPrevPPno(passportNo, "", "");
//													if(nicTranLost_PPno != null)
//														listLost.add(nicTranLost_PPno);
//													
//													//Kiểm tra qua CMND / CCCD (tìm bản ghi đầu tiên)
//													NicTransaction nicTranLost_nin = nicTransactionService.getNicTransactionByPrevPPno("", "", nicTran.getNin());
//													if(nicTranLost_nin != null)
//														listLost.add(nicTranLost_nin);
//												}
//												if(listLost != null && listLost.size() > 0)
//													outputR.setListLost(listLost);
//												
//												//Kiểm tra đối với dữ liệu lịch sử XNC của đối tượng
//												List<ImmiHistory> listImmi = new ArrayList<ImmiHistory>();
//												listImmi = nicImmiHistoryService.findByNinPPno(nicTran.getNin(), passportNo);
//												if(listImmi != null && listImmi.size() > 0)
//													outputR.setListImmi(listImmi);
												
												
												//Kiểm tra đối với dữ liệu lịch sử XNC của đối tượng
												listImmi = nicImmiHistoryService.findByNinPPno(nicTran.getNin(), passportNo);
												listTranBMS.add(transactionId_BMS);
												
												//Đóng gói dữ liệu History
												String xmlImmi = "";
												String xmlIssue = "";
												logger.info("[{}]XML data", nicUp.getTransactionId());
												if(outPutP != null){
													//lstR.setInfoTarget(outputR);
													JAXBContext jaxbContext = JAXBContext.newInstance(InvestigationListInfoTargetsDto.class);
													Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
													StringWriter sw = new StringWriter();
													jaxbMarshaller.marshal(outPutP, sw);
													xmlIssue = sw.toString();
												}
												
												if(listImmi != null && listImmi.size() > 0){
													ImmiHistorys listImmis = new ImmiHistorys();
													listImmis.setImmiHistory(listImmi);
													JAXBContext jaxbContext = JAXBContext.newInstance(ImmiHistorys.class);
													Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
													StringWriter sw = new StringWriter();
													jaxbMarshaller.marshal(listImmis, sw);
													xmlImmi = sw.toString();
												}
												
												EppBufFamilyDto family = new EppBufFamilyDto();
												List<EppPersonDto> lstPerson = new ArrayList<EppPersonDto>();
												family.setFatherSurname(nicUp.getNicTransaction().getNicRegistrationData().getFatherSurname());
												family.setMotherSurname(nicUp.getNicTransaction().getNicRegistrationData().getMotherSurname());
												family.setSpouseSurname(nicUp.getNicTransaction().getNicRegistrationData().getSpouseSurname());
												
												family.setFatherDob(nicUp.getNicTransaction().getNicRegistrationData().getFatherDob());
												family.setMotherDob(nicUp.getNicTransaction().getNicRegistrationData().getMotherDob());
												family.setSpouseDob(nicUp.getNicTransaction().getNicRegistrationData().getSpouseDob());
												
												String xmlPerson = nicUp.getNicTransaction().getInfoPerson();
												
												if(StringUtils.isNotEmpty(xmlPerson)){
													JSONObject xmlJSONObj = XML.toJSONObject(xmlPerson);
													if(xmlJSONObj != null){
														if(xmlJSONObj.has("EPP_PERSONS") && !xmlJSONObj.isNull("EPP_PERSONS")){
															JSONObject resp = xmlJSONObj.getJSONObject("EPP_PERSONS");
															if(resp.has("EPP_PERSON") && !resp.isNull("EPP_PERSON")){
																Object item = resp.get("EPP_PERSON");
																try{
																	if (item instanceof JSONArray){
																		JSONArray arrayResp = (JSONArray) item;
																		for (int i = 0; i < arrayResp.length(); i++) {
																			EppPersonDto person = new EppPersonDto();
																			JSONObject myResponse = new JSONObject();
																			myResponse = arrayResp.getJSONObject(i);
																			String id = "";
																			if(myResponse.has("PLACE_OF_BIRTH_ID") && !myResponse.isNull("PLACE_OF_BIRTH_ID")){
																				person.setPlaceOfBirthId(myResponse.getString("PLACE_OF_BIRTH_ID"));
																			}
																			if(myResponse.has("SEARCH_NAME") && !myResponse.isNull("SEARCH_NAME")){
																				person.setSearchName(myResponse.getString("SEARCH_NAME"));
																			}
																			if(myResponse.has("NAME") && !myResponse.isNull("NAME")){
																				person.setName(myResponse.getString("NAME"));
																			}
																			if(myResponse.has("DATE_OF_BIRTH") && !myResponse.isNull("DATE_OF_BIRTH")){
																				person.setDateOfBirth(String.valueOf(myResponse.getInt("DATE_OF_BIRTH")));
																			}
																			if(myResponse.has("GENDER") && !myResponse.isNull("GENDER")){
																				person.setGender(myResponse.getString("GENDER"));
																			}
																			if(myResponse.has("ID") && !myResponse.isNull("ID")){
																				id = String.valueOf(myResponse.getInt("ID"));
																				person.setId(id);
																			}
																			if(myResponse.has("TYPE_") && !myResponse.isNull("TYPE_")){
																				person.setType_(myResponse.getString("TYPE_"));
																			}
																			if(myResponse.has("NATIONALITY_ID") && !myResponse.isNull("NATIONALITY_ID")){
																				person.setNationalityId(myResponse.getString("NATIONALITY_ID"));
																			}
																			
																			if(StringUtils.isNotEmpty(id)){
																				List<NicTransactionAttachment> nicAttch = transactionAttachmentService.findFacialTEById(jobDBO.getTransactionId(), "PH_TE", id);
																				if(nicAttch != null && nicAttch.size() > 0){
																					NicTransactionAttachment photo = nicAttch.get(0);
																					person.setPictureStr(Base64.encodeBase64String(photo.getDocData()));
																				}
																			}
																			
																			lstPerson.add(person);
																		}
																    }
																	else
																    {
																		EppPersonDto person = new EppPersonDto();
																		JSONObject myResponse = (JSONObject) item;
																		String id = "";
																		if(myResponse.has("PLACE_OF_BIRTH_ID") && !myResponse.isNull("PLACE_OF_BIRTH_ID")){
																			person.setPlaceOfBirthId(myResponse.getString("PLACE_OF_BIRTH_ID"));
																		}
																		if(myResponse.has("SEARCH_NAME") && !myResponse.isNull("SEARCH_NAME")){
																			person.setSearchName(myResponse.getString("SEARCH_NAME"));
																		}
																		if(myResponse.has("NAME") && !myResponse.isNull("NAME")){
																			person.setName(myResponse.getString("NAME"));
																		}
																		if(myResponse.has("DATE_OF_BIRTH") && !myResponse.isNull("DATE_OF_BIRTH")){
																			person.setDateOfBirth(String.valueOf(myResponse.getInt("DATE_OF_BIRTH")));
																		}
																		if(myResponse.has("GENDER") && !myResponse.isNull("GENDER")){
																			person.setGender(myResponse.getString("GENDER"));
																		}
																		if(myResponse.has("ID") && !myResponse.isNull("ID")){
																			id = String.valueOf(myResponse.getInt("ID"));
																			person.setId(id);
																		}
																		if(myResponse.has("TYPE_") && !myResponse.isNull("TYPE_")){
																			person.setType_(myResponse.getString("TYPE_"));
																		}
																		if(myResponse.has("NATIONALITY_ID") && !myResponse.isNull("NATIONALITY_ID")){
																			person.setNationalityId(myResponse.getString("NATIONALITY_ID"));
																		}
																		
																		if(StringUtils.isNotEmpty(id)){
																			List<NicTransactionAttachment> nicAttch = transactionAttachmentService.findFacialTEById(jobDBO.getTransactionId(), "PH_TE", id);
																			if(nicAttch != null && nicAttch.size() > 0){
																				NicTransactionAttachment photo = nicAttch.get(0);
																				person.setPictureStr(Base64.encodeBase64String(photo.getDocData()));
																			}
																		}
																		
																		lstPerson.add(person);
																    }
																}catch(Exception e){
																	
																}
															}
														}
													}
													
													
												}
												
												String xmlFamily = "";
												JAXBContext jaxbContext = JAXBContext.newInstance(EppBufFamilyDto.class, EppPersonDto.class);
												Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
												StringWriter sw = new StringWriter();
												jaxbMarshaller.marshal(family, sw);
												xmlFamily = sw.toString();
												
												String status = "Đang xử lý";
												NicDocumentData docData = null;
												Collection<NicDocumentData> docData_ = documentDataService
														.findByTransactionId(transactionId_BMS);
												if(docData_ != null && docData_.size() > 0){
													List<NicDocumentData> nicDocs_ = new ArrayList<NicDocumentData>(
															docData_);
													docData = new NicDocumentData();
													docData = nicDocs_.get(0);
													if(docData == null && nicUp.getInvestigationStatus().equals(NicUploadJob.RECORD_STATUS_APPROVE_PERSO)){
														status = "Đã phê duyệt. Chờ in";
													}
													else if(docData != null){
														if(docData.getStatus().equals(NicDocumentData.DOCUMENT_DATA_STATUS_ISSUANCE) && nicUp.getNicTransaction().getTransactionStatus().equals(NicTransaction.TRANSACTION_STATUS_RIC_ISSUED)){
															status = "Đã trả hộ chiếu cho công dân";
														}
														else {
															status = "Đã in. Chờ trả hộ chiếu";
														}
													}
												}
												
												EppBufInfoDocDto info = new EppBufInfoDocDto();
												info.setTransactionId(nicUp.getTransactionId());
												info.setRecieptNo(nicUp.getReceiptNo());
												if(nicUp.getNicTransaction().getTransactionType().equals("REP"))
													info.setTypeTransaction("Thay thế");
												else if(nicUp.getNicTransaction().getTransactionType().equals("REP_LOS"))
													info.setTypeTransaction("Thay thế do mất hỏng");
												else
													info.setTypeTransaction("Cấp mới");
												
												SiteRepository siteR = siteService.getSiteRepoById(nicUp.getNicTransaction().getRegSiteCode());
												if(siteR != null)
													info.setRegSiteCode(siteR.getSiteName());
												else
													info.setRegSiteCode(nicUp.getNicTransaction().getRegSiteCode());
												
												info.setStatus(status);
												
												List<NicTransactionPackage> nicP = nicTransactionPackageService.getPackageNameByTransactionId(nicUp.getTransactionId());
												if(nicP != null && nicP.size() > 0){
													for(NicTransactionPackage pack : nicP){
														if(pack.getTypeList() == 8 && pack.getStage().equals("D")){
															info.setRemarkApprove(pack.getNoteLeaderApprove());
															info.setNote(pack.getNoteApprove());
														}
													}
												}
												
												String nameApprover = "";
												String levelApprover = "";
												if(StringUtils.isNotEmpty(nicUp.getNicTransaction().getLeaderOfficerId())){
													Users user = userService.findById(nicUp.getNicTransaction().getLeaderOfficerId());
													if(user != null){
														nameApprover = user.getUserName();
														levelApprover = user.getPosition();
													}
													else{
														nameApprover = nicUp.getNicTransaction().getLeaderOfficerId();
													}
												}
												info.setNameApprover(nameApprover);
												info.setLevelApprover(levelApprover);
												
												if(docData != null){
													info.setPassportNo(docData.getId().getPassportNo());
													info.setIssuePassportDate(docData.getDateOfIssue());
													info.setReleaseDate(docData.getIssueDatetime());
												}
												info.setReceiver(nicUp.getNicTransaction().getReceiver());
												
												String xmlInfo = "";
												JAXBContext jaxbContextInfo = JAXBContext.newInstance(EppBufInfoDocDto.class);
												Marshaller jaxbMarshallerInfo = jaxbContextInfo.createMarshaller();
												StringWriter swI = new StringWriter();
												jaxbMarshallerInfo.marshal(info, swI);
												xmlInfo = swI.toString();
												
												//Lưu lại dữ liệu History
												logger.info("[{}]SAVE HISTORY =================", transactionId_BMS);
												BufEppPersonInvestigation objBuf = bufPersonInvestigationService.findByTranId(transactionId_BMS);
												if(objBuf == null){
													objBuf = new BufEppPersonInvestigation();
													objBuf.setCreateDatetime(new Date());
													objBuf.setCreateBy("SYSTEM");
													objBuf.setCreateWkstnId("SYSTEM-MACHINE");
												}
												else
												{
													objBuf.setUpdateBy("SYSTEM");
													objBuf.setUpdateWkstnId("SYSTEM-MACHINE");
													objBuf.setUpdateDatetime(new Date());
												}
												objBuf.setTransactionId(transactionId_BMS);
												objBuf.setDataHistoryPassport(xmlIssue);
												objBuf.setDataImmihistory(xmlImmi);
												objBuf.setDataFamily(xmlFamily);
												objBuf.setDataInfoDocument(xmlInfo);
												bufPersonInvestigationService.saveOrUpdate(objBuf);
											}
											else
											{
												logger.info("Not found information Transaction in database TransactionId: " + transactionId_BMS);
											}
										}	
									}
								}
							}
						}
						else
						{
							logger.info("Not found list hit BMS by idBMS: " + idBMS);
						}
					}*/
				} catch (Exception e) {
					logger.error("Exception in finally block (CHECKING HISTORY.doSomething)", e);
				}
			}
		}catch (Exception e) {
			logger.error("flag EXCEPTION:"+e.getMessage(), e);
			this.setState(GOTO_ERROR_CMD);
			updateStatus(job.getWorkflowJobId(), STATUS_ERROR, INVESTIGATION);
			nicCommandUtil.setErrorFlag(job.getWorkflowJobId(), true, uploadJobService);
		}finally {
			try {
				
				if (StringUtils.isNotBlank(job.getTransactionId()) && StringUtils.isNotEmpty(jobStatus)) {
					updateStatus(job.getWorkflowJobId(), JOB_STATE_INVESTIGATION, JOB_STATE);
					uploadJobService.updateJobStatus(job.getWorkflowJobId(), jobStatus);
				}
				
				
			} catch (Exception e) {
				logger.error("Exception in finally block (NicSubmitPersoCommand.doSomething)", e);
			}
		}
	}
	
	private Boolean checkTranIdQueue(List<String> list, String tranId){
		Boolean check = true;
		try {
			for(String transactionId : list){
				if(transactionId.equals(tranId)){
					check = false;
					break;
				}
			}
		} catch (Throwable e) {}
		return check;
	}
	
	private String responseSite(String regSiteCode) throws DaoException{
		String siteHanA = "";
		BaseModelSingle<ConfigurationWorkflow> cf = configurationWorkflowService.findSiteRepositoryBySite(regSiteCode, new Date(), "XL", true);
		if(cf.isError()){
			ConfigurationWorkflow cfwA = cf.getModel();
			if(cfwA != null){
				siteHanA = cfwA.getSiteIdTo();
				logger.info("GetSiteCode: " + siteHanA);
			}else{
				for (int i = 0; i < 3; i++) {
					SiteRepository site = siteService.getSiteRepoById(regSiteCode);
					if(site != null){
						siteHanA = site.getSiteGroups().getGroupId();
					}
					logger.info("GetSiteCode: " + (i + 1) + siteHanA);
					if (StringUtils.isNotBlank(siteHanA)) {
						break;
					}
				}
			}
		}
		return siteHanA;
	}
	
	private void addObjToQueueJob(String transactionId, String ObjType, String receiver, Long bmsId, String idMaster){
		try {
			SyncQueueJob queue = new SyncQueueJob();
			queue.setCreatedTs(new Date());
			queue.setObjectId(transactionId);
			queue.setObjectType(ObjType);
			queue.setReceiver(receiver);
			queue.setStatus(CODE_STATUS_QUEUE_PENDING);
			queue.setSyncRetry(1);
			queue.setBmsId(bmsId);
			queue.setTransactionIdMaster(idMaster);
			queueJobService.saveOrUpdateQueue(queue);			
		} catch (Throwable e) {}
	} 
	
	private void updateStatus(long objId, String state, int command){
		try {
			uploadJobService.updateJobState(objId, state, command);
		} catch (Exception e) {
			logger.info("exception in updateStatus:"+e.getMessage());	
			e.printStackTrace();
		}
	}
	
	private void saveTransactionLog(String transactionId, String transactionStage,String transactionStatus, Date startTime, Date endTime, String logInfo, String logData, Integer retryCount) {
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
		transactionLog.setWkstnId(nicCommandUtil.getHostName());
		transactionLog.setOfficerId(nicCommandUtil.getSystemName());
		transactionLog.setRetryCount(retryCount);
		synchronized (NicTransactionLog.class) {
			try {
				nicTransactionLogService.saveOrUpdate(transactionLog);
			} catch (Throwable t) {
				logger.warn("exception in insert transaction log [{}][{}][{}]: {} ", new Object[] {transactionId, transactionStage, transactionStatus, t.getMessage()});
			}
		}
	}
	
	private Boolean CheckUploadJobIStatusNEW(String status){
		switch (status) {
		case NicUploadJob.RECORD_STATUS_IN_PROGRESS:
			return true;
		case NicUploadJob.RECORD_STATUS_APPROVED:
			return true;
		case NicUploadJob.RECORD_STATUS_APPROVE_BOSS:
			return true;
		case NicUploadJob.RECORD_STATUS_CONFIRMED:
			return true;
		case NicUploadJob.RECORD_STATUS_CONFIRMED_DSQ:
			return true;
		case NicUploadJob.RECORD_STATUS_PENDING_BCA:
			return true;
		case NicUploadJob.RECORD_STATUS_CONFIRMED_BCA:
			return true;
		case NicUploadJob.RECORD_STATUS_PENDING_BOSS:
			return true;
		case NicUploadJob.RECORD_STATUS_APPROVE_PERSO:
			return true;
		default:
			return false;
		}
	}
	
	public void setCommandUtil (NicCommandUtil commandUtil) {
		this.nicCommandUtil = commandUtil;
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
	
	//thêm
	public void setNicSearchHitResultService(NicSearchHitResultService nicSearchHitResultService) {
		this.nicSearchHitResultService = nicSearchHitResultService;
	}
	

	
	public void setNicSearchResultService(NicSearchResultService nicSearchResultService) {
		this.nicSearchResultService = nicSearchResultService;
	}

	/*public void setTransactionAttachmentService(NicTransactionAttachmentService transactionAttachmentService) {
		this.transactionAttachmentService = transactionAttachmentService;
	}
	
	public void setDocumentDataService(DocumentDataService documentDataService) {
		this.documentDataService = documentDataService;
	}
	
	public void setNicImmiHistoryService(NicImmiHistoryService nicImmiHistoryService) {
		this.nicImmiHistoryService = nicImmiHistoryService;
	}
	
	
	
	
	public void setNicTransactionPackageService(NicTransactionPackageService nicTransactionPackageService) {
		this.nicTransactionPackageService = nicTransactionPackageService;
	}
	
	public void setUserService(UserService userService) {
		this.userService = userService;
	}*/
	
	/*public void setBufPersonInvestigationService(BufPersonInvestigationService bufPersonInvestigationService) {
		this.bufPersonInvestigationService = bufPersonInvestigationService;
	}*/

	public void setSiteService(SiteService siteService) {
		this.siteService = siteService;
	}
	
	public ListHandoverService getListHandoverService() {
		return listHandoverService;
	}

	public void setListHandoverService(ListHandoverService listHandoverService) {
		this.listHandoverService = listHandoverService;
	}

	public SyncQueueJobService getQueueJobService() {
		return queueJobService;
	}

	public void setQueueJobService(SyncQueueJobService queueJobService) {
		this.queueJobService = queueJobService;
	}

	public ConfigurationWorkflowService getConfigurationWorkflowService() {
		return configurationWorkflowService;
	}

	public void setConfigurationWorkflowService(
			ConfigurationWorkflowService configurationWorkflowService) {
		this.configurationWorkflowService = configurationWorkflowService;
	}
	
	
	
}


