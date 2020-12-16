/**
 * 
 */
package com.nec.asia.nic.dx.ws.impl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.nec.asia.nic.comp.trans.domain.DetailRecieptFee;
import com.nec.asia.nic.comp.trans.domain.EppDetailReciept;
import com.nec.asia.nic.comp.trans.domain.EppRecieptManager;
import com.nec.asia.nic.comp.trans.domain.FeeRecieptPayment;
import com.nec.asia.nic.comp.trans.domain.NicDocumentData;
import com.nec.asia.nic.comp.trans.domain.NicDocumentDataId;
import com.nec.asia.nic.comp.trans.domain.NicDocumentHistory;
import com.nec.asia.nic.comp.trans.domain.NicTransaction;
import com.nec.asia.nic.comp.trans.domain.NicTransactionLog;
import com.nec.asia.nic.comp.trans.domain.OfficeData;
import com.nec.asia.nic.comp.trans.domain.RequestCancel;
import com.nec.asia.nic.comp.trans.service.DocumentDataService;
import com.nec.asia.nic.comp.trans.service.DocumentHistoryService;
import com.nec.asia.nic.comp.trans.service.NicTransactionService;
import com.nec.asia.nic.comp.trans.service.TransactionLogService;
import com.nec.asia.nic.dx.admin.GetRecieptAllRequest;
import com.nec.asia.nic.dx.common.ResponseCode;
import com.nec.asia.nic.dx.sync.DispatchInfoStatusUpdate;
import com.nec.asia.nic.dx.sync.PackageInfo;
import com.nec.asia.nic.dx.sync.PackageInfoStatusUpdate;
import com.nec.asia.nic.dx.sync.PassportInfo;
import com.nec.asia.nic.dx.sync.PassportStatusUpdate;
import com.nec.asia.nic.dx.sync.TransStatusUpdate;
import com.nec.asia.nic.dx.sync.UpdateRequest;
import com.nec.asia.nic.dx.ws.FaultException;
import com.nec.asia.nic.dx.ws.StatusCode;
import com.nec.asia.nic.dx.ws.SyncWS;

/**
 * @author chris
 */

/*
 * Modification History:
 * 13 Jan 2016 chris : init service for status update
 * 15 Jan 2016 khang : Implement logic for update transaction status, dispatch info
 * 10 May 2016 khang : Change update dispatch info to update document and transaction status by sql statement
 *                     Change create Document History and Transaction Log to use Hibernate Batch Insert(Bulk Insert)
 * 20 May 2016 khang/chris : update for transaction control for update dispatch info.
 */
public class SyncWSImpl implements SyncWS {
    protected static final Logger logger = LoggerFactory.getLogger(SyncWSImpl.class);

    @Autowired
    private NicTransactionService nicTransactionService = null;

    @Autowired
    private TransactionLogService nicTransactionLogService = null;

    @Autowired
    private DocumentDataService documentDataService = null;

    @Autowired
    private DocumentHistoryService documentHistoryService = null;
    
    public static String tokenBNG = "";
	public static String typeTokenBNG = "";
	public static Integer expireTokenBNG = 0;

    @Override
    public ResponseCode updateStatus(UpdateRequest input) throws FaultException {
        ResponseCode result = new ResponseCode();//"failed";
        try {
            boolean updated = false;            
            if (input != null && input.getTransactionStatusInfo() != null) {
            	TransStatusUpdate transStatusInput = input.getTransactionStatusInfo();
            	
            	
            	/*TODO:TẠM ĐÓNG ĐỂ LÊN PHƯƠNG ÁN XỬ LÝ SAU*/
            	/*if(transStatusInput.getTransactionStatus().equals("RIC_DEACTIVATED")){
                	do{
        				if(tokenBNG == ""){
        					Map<String,String> resultToken = GetTokenAPI("bng","bng");
        					if(resultToken!= null){
        						tokenBNG = resultToken.get("access_token");
        						expireTokenBNG = Integer.parseInt(resultToken.get("expires_in"));
        						typeTokenBNG = resultToken.get("token_type");
        					}
        				}
        				if(expireTokenBNG == 0 && tokenBNG != "")
        				{
        					ReAccessToken(tokenBNG);
        				}
        				
        			}while(tokenBNG == "" || expireTokenBNG == 0);
                	
                	Collection<NicDocumentData> collectionNicDocumentData = documentDataService.findByTransactionId(transStatusInput.getTransactionID());
        			NicDocumentData nicDocumentData = new NicDocumentData();
        			for(NicDocumentData item : collectionNicDocumentData)
        			{
        				nicDocumentData = item;
        			}
                	
                	///TEST API ĐĂNG KÝ HỦY HỘ CHIẾU
        			RequestCancel rqCancel_ = new RequestCancel();
        			rqCancel_.setCode(transStatusInput.getTransactionID());
        			rqCancel_.setDocType("PASSPORT");
        			String passportNo_ = nicDocumentData.getId().getPassportNo();
        			rqCancel_.setDocCode(passportNo_);
        			rqCancel_.setReason("LOST");
        			String oTemp_ =  "56003"; // Mặc định là từ Bộ ngoại giao do csdl chưa đầy đủ\
        			
        			if(listOffice != null){
        				for(OfficeData item : listOffice)
        				{
        					if(item.getName().contains(nicTransaciton.getIssuingAuthority())){
        						oTemp_ = item.getId();
        						break;
        					}
        				}
        			}
        			
        			OfficeData officeTemp_ = new OfficeData();
        			//officeTemp_.setId(oTemp_);
        			rqCancel_.setOffice(officeTemp_);
        			
        			DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        			Date issueTmp =  nicDocumentData.getDateOfIssue();
        			String split = nicDocumentData.getDateOfIssue().toString().split(" ")[0];
        			rqCancel_.setDateOfRegister(split);
        			
        			String jsonModelCancel = ConvertDataJsonCancel(rqCancel_);
        			String requestUrlCancel = "http://27.72.57.171:8080/app/rest/v2/entities/epp$CancelDocument";
        			int resultCancel = 0;
        			int i = 1;
        			do{
        			logger.info("So lan goi Sync A72: - " + i);
        			resultCancel = sendPostRequestCancel(requestUrlCancel, jsonModelCancel, tokenBNG, typeTokenBNG);
        			if(resultCancel == 200 || resultCancel == 201 || i == 5){
        				break;
        			}
        			i++;
        			}while(resultCancel != 200);
                }*/
            	
               
                if (StringUtils.isBlank(transStatusInput.getTransactionID()))
                    throw new FaultException(StatusCode.SYNCWS_MANDATORY_FIELD_ERROR.getDesc() + "[FieldName: TransactionID]", StatusCode.SYNCWS_MANDATORY_FIELD_ERROR.convertToFaultDetail());

                String transactionId = transStatusInput.getTransactionID();
                NicTransaction transaction = nicTransactionService.findById(transactionId);
                if (transaction == null) {
                    throw new FaultException(StatusCode.SYNCWS_DATA_NOT_FOUND.getDesc() + "[FieldName: Transaction Data]", StatusCode.SYNCWS_DATA_NOT_FOUND.convertToFaultDetail());
                }

                String transactionStatus = transStatusInput.getTransactionStatus();
                Date statusUpdateDatetime = (transStatusInput.getUpdateDateTime() == null) ? new Date() : transStatusInput.getUpdateDateTime();
                String userId = transStatusInput.getUpdateBy();
                String workstationId = transStatusInput.getUpdateWkstnID();
               

                transaction.setTransactionStatus(transactionStatus);
                transaction.setUpdateDatetime(statusUpdateDatetime);
                transaction.setUpdateBy(userId);
                transaction.setUpdateWkstnId(workstationId);

                nicTransactionService.saveOrUpdate(transaction);
                
                String transactionStage = getTransactionStage(transStatusInput.getTransactionStatus());
                NicTransactionLog transactionLog = new NicTransactionLog();
                transactionLog.setRefId(transactionId);
                transactionLog.setLogCreateTime(new Date());
                //transactionLog.setTransactionStage(NicTransactionService.TRANSACTION_STAGE_DISPATCH);
                transactionLog.setTransactionStage(transactionStage);
                transactionLog.setTransactionStatus(transStatusInput.getTransactionStatus());
                transactionLog.setStartTime(statusUpdateDatetime);
                transactionLog.setEndTime(statusUpdateDatetime);
                transactionLog.setOfficerId(transStatusInput.getUpdateBy());
                transactionLog.setWkstnId(workstationId);

                nicTransactionLogService.save(transactionLog);

                updated = true;
                
            }

            if (input != null && input.getDispatchInfo() != null) {
                DispatchInfoStatusUpdate dispatchInfo = input.getDispatchInfo();
                String dispatchId = dispatchInfo.getDispatchID();
                String dispatchStatus = dispatchInfo.getStatus();
                String userId = dispatchInfo.getUpdateBy();
                String workstationId = dispatchInfo.getUpdateWkstnID();
                Date dispatchDateTime = dispatchInfo.getDispatchDateTime();
                if (CollectionUtils.isEmpty(dispatchInfo.getPackageInfoList())) {
                    throw new FaultException(StatusCode.SYNCWS_MANDATORY_FIELD_ERROR.getDesc() + "[FieldName: DispatchInfo]", StatusCode.SYNCWS_MANDATORY_FIELD_ERROR.convertToFaultDetail());
                }
                if (StringUtils.isBlank(dispatchId)) {
                    throw new FaultException(StatusCode.SYNCWS_MANDATORY_FIELD_ERROR.getDesc() + "[FieldName: DispatchID]", StatusCode.SYNCWS_MANDATORY_FIELD_ERROR.convertToFaultDetail());
                }
                if (StringUtils.isBlank(dispatchStatus)) {
                    throw new FaultException(StatusCode.SYNCWS_MANDATORY_FIELD_ERROR.getDesc() + "[FieldName: Status]", StatusCode.SYNCWS_MANDATORY_FIELD_ERROR.convertToFaultDetail());
                }
                if (StringUtils.length(dispatchInfo.getStatus()) > 30) {
                    throw new FaultException(
                        StatusCode.SYNCWS_FIELD_LENGTH_EXCEEDED.getDesc() + "[FieldName: Status, FieldLength: 30]", StatusCode.SYNCWS_FIELD_LENGTH_EXCEEDED.convertToFaultDetail());
                }
                if (dispatchDateTime == null) {
                    throw new FaultException(StatusCode.SYNCWS_MANDATORY_FIELD_ERROR.getDesc() + "[FieldName: DispatchDateTime]", StatusCode.SYNCWS_MANDATORY_FIELD_ERROR.convertToFaultDetail());
                }

                List<PackageInfo> pacakgeInfoList = dispatchInfo.getPackageInfoList();
                for (PackageInfo packageInfo : pacakgeInfoList) {
                    String packageId = packageInfo.getPackageID();
                    Date packageDate = packageInfo.getPackageDateTime();
                    if (StringUtils.isBlank(packageId)) {
                        throw new FaultException(StatusCode.SYNCWS_MANDATORY_FIELD_ERROR.getDesc() + "[FieldName: PackageID]", StatusCode.SYNCWS_MANDATORY_FIELD_ERROR.convertToFaultDetail());
                    }
                    if (packageDate == null) {
                        throw new FaultException(StatusCode.SYNCWS_MANDATORY_FIELD_ERROR.getDesc() + "[FieldName: PackageDateTime]", StatusCode.SYNCWS_MANDATORY_FIELD_ERROR.convertToFaultDetail());
                    }

                    List<PassportInfo> passportInfoList = packageInfo.getPassportInfoList();
                    if (CollectionUtils.isEmpty(passportInfoList)) {
                        throw new FaultException(StatusCode.SYNCWS_MANDATORY_FIELD_ERROR.getDesc() + "[FieldName: PassportInfo]", StatusCode.SYNCWS_MANDATORY_FIELD_ERROR.convertToFaultDetail());
                    }

                    List<String> passportNoList = new ArrayList<String>();
                    List<String> transactionIdList = new ArrayList<String>();
                    List<NicDocumentHistory> documentHistoryList = new ArrayList<NicDocumentHistory>();
                    List<NicTransactionLog> transactionLogList = new ArrayList<NicTransactionLog>();
                    for (PassportInfo passportInfo : passportInfoList) {
                        String transactionId = passportInfo.getTransactionID();
                        String passportNo = passportInfo.getPassportNo();
                        if (StringUtils.isBlank(transactionId)) {
                            throw new FaultException(StatusCode.SYNCWS_MANDATORY_FIELD_ERROR.getDesc() + "[FieldName: TransactionId]", StatusCode.SYNCWS_MANDATORY_FIELD_ERROR.convertToFaultDetail());
                        }

                        if (StringUtils.isBlank(passportNo)) {
                            throw new FaultException(StatusCode.SYNCWS_MANDATORY_FIELD_ERROR.getDesc() + "[FieldName: PassportNo]", StatusCode.SYNCWS_MANDATORY_FIELD_ERROR.convertToFaultDetail());
                        }

                        passportNoList.add(passportNo);
                        transactionIdList.add(transactionId);

						NicDocumentHistory documentHistory = new NicDocumentHistory();
						documentHistory.setPassportNo(passportNo);
						documentHistory.setStatus(dispatchStatus);
						documentHistory.setStatusUpdateBy(userId);
						documentHistory.setStatusUpdateTime(dispatchDateTime);
						documentHistoryList.add(documentHistory);

						NicTransactionLog transactionLog = new NicTransactionLog();
						Date statusUpdateDatetime = new Date();
						transactionLog.setRefId(transactionId);transactionLog.setLogCreateTime(statusUpdateDatetime);
						transactionLog.setTransactionStage(NicTransactionService.TRANSACTION_STAGE_DISPATCH);
						transactionLog.setTransactionStatus(dispatchStatus);
						transactionLog.setStartTime(statusUpdateDatetime);
						transactionLog.setEndTime(statusUpdateDatetime);
						transactionLog.setOfficerId(userId);
						transactionLog.setWkstnId(workstationId);
						transactionLogList.add(transactionLog);
//                        
//                        NicDocumentDataId documentDataId = new NicDocumentDataId(passportInfo.getTransactionID(), passportInfo.getPassportNo());
//                        NicDocumentData documentData = documentDataService.findById(documentDataId);
//                        if (documentData == null) {
//                            throw new FaultException(StatusCode.SYNCWS_DATA_NOT_FOUND.getDesc() + "[FieldName: Document Data]", StatusCode.SYNCWS_DATA_NOT_FOUND.convertToFaultDetail());
//                        }
//
//
//                        
//                        documentData.setPackageId(packageId);
//                        documentData.setPackageDatetime(packageDate);
//                        documentData.setDispatchId(dispatchId);
//                        documentData.setDispatchDatetime(dispatchDateTime);
//
//                        documentData.setStatus(dispatchStatus);
//                        documentData.setStatusUpdateTime(dispatchDateTime);
//                        documentData.setUpdateBy(userId);
//                        documentData.setUpdateWkstnId(workstationId);
//
//                        documentDataService.saveOrUpdate(documentData);
//
//                        NicDocumentHistory documentHistory = new NicDocumentHistory();
//                        documentHistory.setPassportNo(passportNo);
//                        documentHistory.setStatus(dispatchStatus);
//                        documentHistory.setStatusUpdateBy(userId);
//                        documentHistory.setStatusUpdateTime(dispatchDateTime);
//
//                        documentHistoryService.save(documentHistory);
//                        
//                        // Update Dispatch Status for Transaction
//                        NicTransaction transaction = nicTransactionService.findById(transactionId);
//                        if (transaction == null) {
//                            throw new FaultException(StatusCode.SYNCWS_DATA_NOT_FOUND.getDesc() + "[FieldName: Transaction Data]", StatusCode.SYNCWS_DATA_NOT_FOUND.convertToFaultDetail());
//                        }
//
//                        Date statusUpdateDatetime = new Date();
//                        transaction.setTransactionStatus(dispatchStatus);
//                        transaction.setUpdateDatetime(statusUpdateDatetime);
//                        transaction.setUpdateBy(userId);
//                        transaction.setUpdateWkstnId(workstationId);
//
//                        nicTransactionService.saveOrUpdate(transaction);
//
//                        NicTransactionLog transactionLog = new NicTransactionLog();
//                        transactionLog.setRefId(transactionId);
//                        transactionLog.setLogCreateTime(statusUpdateDatetime);
//                        transactionLog.setTransactionStage(NicTransactionService.TRANSACTION_STAGE_DISPATCH);
//                        transactionLog.setTransactionStatus(dispatchStatus);
//                        transactionLog.setStartTime(statusUpdateDatetime);
//                        transactionLog.setEndTime(statusUpdateDatetime);
//                        transactionLog.setOfficerId(userId);
//                        transactionLog.setWkstnId(workstationId);
//
//                        nicTransactionLogService.save(transactionLog);

					}

					if (CollectionUtils.isNotEmpty(passportNoList)) {
						int count = documentDataService.countExistingPassportByPassportNoList(passportNoList);
						if (passportNoList.size() != count) {
							throw new FaultException(StatusCode.SYNCWS_PASSPORT_NOT_FOUND.getDesc() + "[Uploading Passport count: " + passportNoList.size() 
								+", System count: "+count + "]", StatusCode.SYNCWS_PASSPORT_NOT_FOUND.convertToFaultDetail());
						}

						//documentDataService.updatePackageDispatchInfoByPassportNoList(passportNoList, packageId, packageDate, dispatchId, dispatchDateTime, dispatchStatus, new Date(), userId, workstationId);
						//[chris] 2015 May 20 - Move all update/insert logic to one method for transaction control.
						documentDataService.updatePackageDispatchInfo(passportNoList, transactionIdList,
								documentHistoryList, transactionLogList,  packageId,
								packageDate, dispatchId, dispatchDateTime, dispatchStatus, dispatchDateTime,
								userId, workstationId);
					}

//					if (CollectionUtils.isNotEmpty(transactionIdList)) {
//						nicTransactionService.updateStatusByTxnIdList(transactionIdList, dispatchStatus, userId, workstationId);
//					}
//
//					if (CollectionUtils.isNotEmpty(documentHistoryList)) {
//						documentHistoryService.addDocumentHistory(documentHistoryList);
//					}
//
//					if (CollectionUtils.isNotEmpty(transactionLogList)) {
//						nicTransactionLogService.addTransactionLog(transactionLogList);
//					}
				}
                updated = true;
            }

            if (input != null && input.getPassportStatusInfo() != null) {
                PassportStatusUpdate passportStatusInfo = input.getPassportStatusInfo();
                if (StringUtils.isBlank(passportStatusInfo.getPassportNo())) {
                    throw new FaultException(StatusCode.SYNCWS_MANDATORY_FIELD_ERROR.getDesc() + "[FieldName: PassportNo]", StatusCode.SYNCWS_MANDATORY_FIELD_ERROR.convertToFaultDetail());
                }
                if (StringUtils.isBlank(passportStatusInfo.getStatus())) {
                    throw new FaultException(StatusCode.SYNCWS_MANDATORY_FIELD_ERROR.getDesc() + "[FieldName: Status]", StatusCode.SYNCWS_MANDATORY_FIELD_ERROR.convertToFaultDetail());
                }
                if (StringUtils.length(passportStatusInfo.getStatus()) > 30) {
                    throw new FaultException(
                        StatusCode.SYNCWS_FIELD_LENGTH_EXCEEDED.getDesc() + "[FieldName: Status, FieldLength: 30]", StatusCode.SYNCWS_FIELD_LENGTH_EXCEEDED.convertToFaultDetail());
                }
                String passportNo = passportStatusInfo.getPassportNo();
                String passportStatus = passportStatusInfo.getStatus();
                Date updateDateTime = passportStatusInfo.getUpdateDateTime();
                String userId = passportStatusInfo.getUpdateBy();
                String workstationId = passportStatusInfo.getUpdateWkstnID();
                NicDocumentData documentData = documentDataService.findByDocNumber(passportNo).getModel();
                if (documentData == null) {
                    throw new FaultException(StatusCode.SYNCWS_DATA_NOT_FOUND.getDesc() + "[FieldName: Document Data]", StatusCode.SYNCWS_DATA_NOT_FOUND.convertToFaultDetail());
                }
                
                if (StringUtils.equals(passportStatus, "OK") || StringUtils.equals(passportStatus, "ISSUANCE") || StringUtils.equals(passportStatus, "ACTIVE") || StringUtils.equals(passportStatus, TRANSACTION_STATUS_RIC_CARD_ISSUED)) { //ISSUED
                	if (documentData.getActiveFlag()==null)
                		documentData.setActiveFlag(Boolean.TRUE);
                	if (documentData.getIssuedFlag()==null)
                		documentData.setIssuedFlag(Boolean.TRUE);
                	documentData.setIssueBy(userId);
                	documentData.setIssueDatetime(updateDateTime);
                }
                
                if (StringUtils.equals(passportStatus, "RD") || StringUtils.equals(passportStatus, "RECEPTION") || StringUtils.equals(passportStatus, TRANSACTION_STATUS_RIC_CARD_RECEIVED)) { //RECEIVED
                	if (documentData.getActiveFlag()==null) { //BEFORE ISSUE can update to received.
                		documentData.setReceiveBy(userId);
                    	documentData.setReceiveDatetime(updateDateTime);
                	}
                }
                
                if (StringUtils.equals(passportStatus, "KO") || StringUtils.equals(passportStatus, "REJECTION") || StringUtils.equals(passportStatus, TRANSACTION_STATUS_RIC_CARD_REJECTED)) { //REJECTED
            		documentData.setRejectBy(userId);
                	documentData.setRejectDatetime(updateDateTime);
                	if (documentData.getActiveFlag()!=null) { //AFTER ISSUE, then reject should set Active to false
                		documentData.setActiveFlag(Boolean.FALSE);
                	}
                }
                
                if (StringUtils.equals(passportStatus, "CC") || StringUtils.equals(passportStatus, "CANCELLED")) { //CANCELLED
            		documentData.setCancelBy(userId);
                	documentData.setCancelDatetime(updateDateTime);
                	if (documentData.getActiveFlag()!=null) { //AFTER ISSUE, then cancel should set Active to false
                		documentData.setActiveFlag(Boolean.FALSE);
                	}
                }
                
                documentData.setStatus(passportStatus);
                documentData.setStatusUpdateTime(updateDateTime);
                documentData.setUpdateBy(userId);
                documentData.setUpdateWkstnId(workstationId);

                documentDataService.saveOrUpdate(documentData);

                NicDocumentHistory documentHistory = new NicDocumentHistory();
                documentHistory.setPassportNo(passportNo);
                documentHistory.setStatus(passportStatus);
                documentHistory.setStatusUpdateBy(userId);
                documentHistory.setStatusUpdateTime(updateDateTime);

                documentHistoryService.save(documentHistory);
                
                updated = true;
            }
            if (input != null && input.getPackageInfo() != null) {
                PackageInfoStatusUpdate packageInfo = input.getPackageInfo();
                if (CollectionUtils.isEmpty(packageInfo.getPassportInfoList())) {
                    throw new FaultException(StatusCode.SYNCWS_MANDATORY_FIELD_ERROR.getDesc() + "[FieldName: PassportInfo]", StatusCode.SYNCWS_MANDATORY_FIELD_ERROR.convertToFaultDetail());
                }
                if (StringUtils.isBlank(packageInfo.getPackageID())) {
                    throw new FaultException(StatusCode.SYNCWS_MANDATORY_FIELD_ERROR.getDesc() + "[FieldName: PackageID]", StatusCode.SYNCWS_MANDATORY_FIELD_ERROR.convertToFaultDetail());
                }
                if (StringUtils.length(packageInfo.getPackageID()) > 36) {
                    throw new FaultException(
                        StatusCode.SYNCWS_FIELD_LENGTH_EXCEEDED.getDesc() + "[FieldName: PackageID, FieldLength: 36]", StatusCode.SYNCWS_FIELD_LENGTH_EXCEEDED.convertToFaultDetail());
                }
                String packageId = packageInfo.getPackageID();
                Date updateDateTime = packageInfo.getUpdateDateTime();
                String userId = packageInfo.getUpdateBy();
                String workstationId = packageInfo.getUpdateWkstnID();
                
                List<PassportInfo> passportInfoList = packageInfo.getPassportInfoList();
                for (PassportInfo passportInfo : passportInfoList) {
                    String transactionId = passportInfo.getTransactionID();
                    String passportNo = passportInfo.getPassportNo();
                    if (StringUtils.isBlank(transactionId)) {
                        throw new FaultException(StatusCode.SYNCWS_MANDATORY_FIELD_ERROR.getDesc() + "[FieldName: TransactionId]", StatusCode.SYNCWS_MANDATORY_FIELD_ERROR.convertToFaultDetail());
                    }

                    if (StringUtils.isBlank(passportNo)) {
                        throw new FaultException(StatusCode.SYNCWS_MANDATORY_FIELD_ERROR.getDesc() + "[FieldName: PassportNo]", StatusCode.SYNCWS_MANDATORY_FIELD_ERROR.convertToFaultDetail());
                    }
                    NicDocumentDataId documentDataId = new NicDocumentDataId(passportInfo.getTransactionID(), passportInfo.getPassportNo());
                    NicDocumentData documentData = documentDataService.findById(documentDataId);
                    if (documentData == null) {
                        throw new FaultException(StatusCode.SYNCWS_DATA_NOT_FOUND.getDesc() + "[FieldName: Document Data]", StatusCode.SYNCWS_DATA_NOT_FOUND.convertToFaultDetail());
                    }

                    documentData.setPackageId(packageId);
                    documentData.setPackageDatetime(updateDateTime);
                    documentData.setUpdateBy(userId);
                    documentData.setUpdateWkstnId(workstationId);

                    documentDataService.saveOrUpdate(documentData);

                }

            }
            if (updated) {
                result.setStatusCode(StatusCode.SYNCWS_UPDATE_SUCCESS.getKey());//"Successfully";
                result.setDescription(StatusCode.SYNCWS_UPDATE_SUCCESS.getDesc());
            }
        } catch (Exception ex) {
            throw new FaultException(StatusCode.SYNCWS_UNEXPECTED_ERROR.getDesc() + "[Exception: " + ex.getMessage() + "]", StatusCode.SYNCWS_UNEXPECTED_ERROR.convertToFaultDetail());
        }
        logger.info("SyncWSImpl.updateStatus(): " + result.getStatusCode());
        return result;
    }
    
  ///Đăng ký giấy tờ hủy (Dữ liệu đơn)
  	public static int sendPostRequestCancel(String requestUrl, String data, String token, String typeToken) {
  			StringBuffer jsonString = new StringBuffer();
  			int statusCode = 0;
  		    try {
  		        URL url = new URL(requestUrl);
  		        HttpURLConnection connection = (HttpURLConnection) url.openConnection();

  		        connection.setDoInput(true);
  		        connection.setDoOutput(true);
  		        connection.setRequestMethod("POST");
  		        connection.setRequestProperty("Authorization", typeToken.trim()	 + " " + token.trim());
  		        connection.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
  		        OutputStreamWriter writer = new OutputStreamWriter(connection.getOutputStream(), "UTF-8");
  		        writer.write(data);
  		        writer.close();
  		        Thread.sleep(100);
  		        statusCode = connection.getResponseCode();
  			    if(statusCode == 200 || statusCode == 201){
  			        BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
  			        String line;
  			        while ((line = br.readLine()) != null) {
  			                jsonString.append(line);
  			        }
  			        br.close();
  			        connection.disconnect();
  			        
  		        }
  		    } catch (Exception e) {
  		            throw new RuntimeException(e.getMessage());
  		    }
  		    logger.info("Sync A72: - " + statusCode + " - " +jsonString.toString());
  		    return statusCode;
  		}
  	
  	private String ConvertDataJsonCancel(RequestCancel data) throws JsonGenerationException, JsonMappingException, IOException{
		ObjectMapper mapper = new ObjectMapper();
		String json = mapper.writeValueAsString(data);
		return json;
	}
    
    private Map<String, String> GetTokenAPI(String username, String password) throws JsonParseException, JsonMappingException, IOException{
		Map<String, String> accessToken = new HashMap<String, String>();
		
		String urlParameters  = "grant_type=password&username="+username+"&password=" + password;
        String encoding = Base64.encodeBase64String(("epp:epp12#").getBytes("UTF-8"));

        URL url = new URL ("http://27.72.57.171:8080/app/rest/v2/oauth/token?" + urlParameters);
        HttpURLConnection connection =  (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("POST");
        connection.setRequestProperty("Authorization", "Basic " + encoding.trim());
        connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
        connection.connect();
        
        int statusCode = connection.getResponseCode();
        if(statusCode == 200){
	        InputStream content = connection.getInputStream();
	        InputStreamReader isr = new InputStreamReader(content);
	
			int numCharsRead;
			char[] charArray = new char[1024];
			StringBuffer sb = new StringBuffer();
			while ((numCharsRead = isr.read(charArray)) > 0) {
				sb.append(charArray, 0, numCharsRead);
			}
			String st = sb.toString();
			JSONObject myResponse = new JSONObject(st);
			accessToken.put("access_token", myResponse.getString("access_token"));
			Integer expires_in = myResponse.getInt("expires_in");
			accessToken.put("expires_in", expires_in.toString());
			accessToken.put("token_type", myResponse.getString("token_type"));
			accessToken.put("scope", myResponse.getString("scope"));
        }
		
		return accessToken;
	}
	
	private Boolean ReAccessToken(String oldToken)throws JsonParseException, JsonMappingException, IOException{
		String urlParameters  = "token=Bearer+" + oldToken + "&token_type=access_token";
        String encoding = Base64.encodeBase64String(("epp:epp12#").getBytes("UTF-8"));
        
        URL url = new URL ("http://27.72.57.171:8080/app/rest/v2/oauth/revoke?" + urlParameters);
        HttpURLConnection connection =  (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("POST");
        connection.setRequestProperty("Authorization", "Basic " + encoding.trim());
        connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
        connection.connect();
        
        int statusCode = connection.getResponseCode();
        if(statusCode == 200){
        	return true;
        }
        return false;
	}
    
	public final static String TRANSACTION_STAGE_RIC_ISSUANCE			 	= "ISSUANCE";
	public final static String TRANSACTION_STAGE_RIC_CARD_RECEPTION		 	= "CARD_RECEPTION";
	public final static String TRANSACTION_STAGE_RIC_CARD_ISSUED		 	= "CARD_ISSUED";
	public final static String TRANSACTION_STAGE_RIC_CARD_REJECTED			= "CARD_REJECTED";
	public final static String TRANSACTION_STAGE_RIC_REJECTED			 	= "REJECTED";
	
	public final static String TRANSACTION_STATUS_RIC_CARD_RECEIVED 	 	= "RIC_CARD_RECEIVED";
	public final static String TRANSACTION_STATUS_RIC_CARD_ISSUED 	 		= "RIC_CARD_ISSUED";
	public final static String TRANSACTION_STATUS_RIC_CARD_REJECTED 	 	= "RIC_CARD_REJECTED";

    private String getTransactionStage(String transactionStatus) {
    	String stage = "";
    	
    	if (StringUtils.equals(transactionStatus, "RD") || StringUtils.equals(transactionStatus, TRANSACTION_STATUS_RIC_CARD_RECEIVED)) {
    		stage = TRANSACTION_STAGE_RIC_CARD_RECEPTION;
    	} else if (StringUtils.equals(transactionStatus, "OK") || StringUtils.equals(transactionStatus, TRANSACTION_STATUS_RIC_CARD_ISSUED)) {
    		stage = TRANSACTION_STAGE_RIC_ISSUANCE;
    	} else if (StringUtils.equals(transactionStatus, "KO") || StringUtils.equals(transactionStatus, TRANSACTION_STATUS_RIC_CARD_REJECTED)) {
    		stage = TRANSACTION_STAGE_RIC_ISSUANCE;
    	} 
    	
    	return stage; 
    }
    
    @Override
    public String echo(String input) throws FaultException {
        logger.info("SyncWSImpl.echo(): {}", input);
        return "received echo input: " + input;
    }

    public void setNicTransactionLogService(TransactionLogService nicTransactionLogService) {
        this.nicTransactionLogService = nicTransactionLogService;
    }

    public void setNicTransactionService(NicTransactionService nicTransactionService) {
        this.nicTransactionService = nicTransactionService;
    }

    public void setDocumentHistoryService(DocumentHistoryService documentHistoryService) {
        this.documentHistoryService = documentHistoryService;
    }
}
