/**
 * 
 */
package com.nec.asia.nic.comp.trans.service.impl;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.InetAddress;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.xml.bind.JAXBContext;
import javax.xml.ws.Holder;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.nec.asia.nic.comp.dispatch.dao.DispatchDataDao;
import com.nec.asia.nic.comp.dispatch.domain.DispatchQueueData;
import com.nec.asia.nic.comp.integration.FileTransferService;
import com.nec.asia.nic.comp.integration.impl.FileTransferSshImpl;
import com.nec.asia.nic.comp.integration.impl.SecureFtpServer;
import com.nec.asia.nic.comp.job.dto.AbstractCardInfoDTO;
import com.nec.asia.nic.comp.job.dto.CardDataDTO;
import com.nec.asia.nic.comp.job.dto.LogInfoDTO;
import com.nec.asia.nic.comp.job.dto.NicPersoInfoDTO;
import com.nec.asia.nic.comp.job.dto.PackageInfoDTO;
import com.nec.asia.nic.comp.job.dto.PersoReferenceDataDTO;
import com.nec.asia.nic.comp.job.dto.TransactionInfoDTO;
import com.nec.asia.nic.comp.perso.dao.PersoInfoDao;
import com.nec.asia.nic.comp.perso.domain.PersoDispatchInfo;
import com.nec.asia.nic.comp.trans.dao.DocumentDataDao;
//import com.nec.asia.nic.comp.trans.dao.NicIssuanceDataDao;
import com.nec.asia.nic.comp.trans.dao.NicRegistrationDataDao;
import com.nec.asia.nic.comp.trans.dao.NicRejectionDataDao;
import com.nec.asia.nic.comp.trans.dao.NicTransactionAttachmentDao;
import com.nec.asia.nic.comp.trans.dao.NicTransactionDao;
import com.nec.asia.nic.comp.trans.dao.NicTransactionLogDao;
import com.nec.asia.nic.comp.trans.dao.NicTransactionReprintDao;
import com.nec.asia.nic.comp.trans.dao.NicUploadJobDao;
import com.nec.asia.nic.comp.trans.domain.NicDocumentData;
import com.nec.asia.nic.comp.trans.domain.NicDocumentDataId;
import com.nec.asia.nic.comp.trans.domain.NicIssuanceData;
import com.nec.asia.nic.comp.trans.domain.NicIssuanceDataId;
import com.nec.asia.nic.comp.trans.domain.NicRegistrationData;
import com.nec.asia.nic.comp.trans.domain.NicRejectionData;
import com.nec.asia.nic.comp.trans.domain.NicTransaction;
import com.nec.asia.nic.comp.trans.domain.NicTransactionAttachment;
import com.nec.asia.nic.comp.trans.domain.NicTransactionLog;
import com.nec.asia.nic.comp.trans.domain.NicTransactionReprint;
import com.nec.asia.nic.comp.trans.domain.NicUploadJob;
import com.nec.asia.nic.comp.trans.domain.type.TransactionStatus;
import com.nec.asia.nic.comp.trans.dto.ResponseDTO;
import com.nec.asia.nic.comp.trans.service.IssuanceDataService;
import com.nec.asia.nic.comp.trans.service.NicTransactionService;
import com.nec.asia.nic.comp.trans.service.PersoService;
import com.nec.asia.nic.comp.trans.service.TransactionLogService;
import com.nec.asia.nic.comp.trans.service.exception.PersoServiceException;
import com.nec.asia.nic.comp.trans.service.exception.TransactionServiceException;
import com.nec.asia.nic.comp.trans.utils.PassportType;
import com.nec.asia.nic.framework.admin.code.dao.ParametersDao;
import com.nec.asia.nic.framework.admin.code.domain.CodeValues;
import com.nec.asia.nic.framework.admin.code.domain.Parameters;
import com.nec.asia.nic.framework.admin.code.domain.ParametersId;
import com.nec.asia.nic.framework.admin.code.service.CodesService;
import com.nec.asia.nic.framework.admin.site.dao.SiteRepositoryDao;
import com.nec.asia.nic.framework.admin.site.domain.SiteRepository;
import com.nec.asia.nic.framework.exception.JaxbXmlConvertorException;
import com.nec.asia.nic.util.JobXmlConvertor;
import com.nec.asia.nic.util.PersoXmlConvertor;
import com.nec.asia.nic.util.TransLogInfoXmlConvertor;
import com.nec.asia.nic.utils.DateUtil;
import com.nec.asia.epp.perso.dispatchInfo.DispatchInfo;
import com.nec.asia.epp.perso.dispatchInfo.DocumentInfoType;
import com.nec.asia.epp.perso.dispatchInfo.DocumentType;
import com.nec.asia.epp.perso.persoData.ApplicationDataType;
import com.nec.asia.epp.perso.persoData.DocTypeType;
import com.nec.asia.epp.perso.persoData.GenderType;
import com.nec.asia.epp.perso.persoData.JobInfoType;
import com.nec.asia.epp.perso.persoData.NameType;
import com.nec.asia.epp.perso.persoData.PersoData;
import com.nec.asia.epp.perso.persoData.RemarkType;
import com.nec.asia.epp.perso.persoStatus.JobType;
import com.nec.asia.epp.perso.persoStatus.PersoStatus;
import com.nec.asia.epp.perso.persoStatus.StatusType;
import com.nec.lds.chip.ChipInfo;
import com.sshtools.j2ssh.sftp.SftpFile;

/**
 * The implementation class of PersoService, it is to invoke with PERSO Web Service to submit the card personalisation request.
 * 
 * @author chris_wong
 */
/*
 * Modification History:
 * 27 Aug 2013 (chris): update NicMain And NicHistory when received perso info.
 * 29 Aug 2013 (chris): modify updateTransactionByPackageId(), to save dispatch id in issuance data.
 * 13 Sep 2013 (chris): add packageSequence in nicIssuanceData
 * 03 Oct 2013 (chris): to handle if perso return persoRefId as 0.
 * 04 Oct 2013 (jp): synchronize call to perso
 * 07 Oct 2013 (chris): to receive perso error data and store in db.
 * 08 Oct 2013 (chris): Add system host name as wkstnID
 * 09 Oct 2013 (chris): to handle perso ref id.
 * 28 Oct 2013 (chris): add transaction log when dispatch
 * 29 Oct 2013 (chris): check dateOfApplication and decide express flag
 * 13 Nov 2013 (chris): Add two new method: GetPackageJobByPackageID, GetPackageJobByTransactionID
 * 11 Dec 2013 (chris): to handle deserialized error and perso permanent reject
 * 06 Jan 2014 (chris): Add jobcancellationservice (perso webservice).
 * 20 Feb 2014 (chris): to insert issuance data if transaction_id not found in nic_issuance_data table.
 * 02 Jun 2014 (chris): to check if package id not found from nic_issuance_data then invoke persoWS.getPackageJobByPackageID().
 * 05 Sep 2014 (chris): enhance logging;
 * 17 Feb 2016 (chris): fix document data not save due to transaction status refactoring.
 * 19 Feb 2016 (chris): remove double Base64 encoded photo binary and signature binary in persoData.xml
 * 28 Mar 2016 (chris): fix npe when status.xml.UpdateTime is null
 * 27 Apr 2016 (chris): validation collection date and overide it to system date.
 * 12 May 2016 (khang): skip perso status.xml when unmashall error
 * 20-May-2016 (khang): Hardcoded for priority of update status folder. 
 *                      Fix issue with perso error status but document data does not exist
 *                      Handle Rejected status by update transaction and document status to KO
 * 20-May-2016 (khang): Added handle for perso 'rejected' status 
 * 31 May 2016 (khang): Update pasport type to support:
 *                          PS-MRCTD Stateless
 *                          PR-MRCTD Refugees
 *                      Update Perso Status to handle Operator Name from Perso
 *                      Long name handling for perso data
 * 27-June-2016 (setia): Added one method to retrieve information of total number of files for every Perso Folder
 * 17 Aug 2016 (chris): add logic for page 2 and page 3 name truncation, to get data from name_line1
 * 19 Aug 2016 (chris): add logic for chip name truncation, to get data from name_line2
 * 19 Aug 2016 (chris): change ARN format for reprint to R-1-xxxx instead of RE-001-xxxx
 * 19 Aug 2016 (khang): modified logic to skip PersoStatus/DispatchInfo if it is not belong to latest reprint instead of ignoring all others in perso/dispatch xml
 */
@Service("persoService")
@Transactional
public class PersoServiceImpl implements PersoService {
    public final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private NicTransactionService nicTransactionService;

    //@Autowired
    //private NicIssuanceDataDao nicIssuanceDataDao;

    @Autowired
    private NicTransactionAttachmentDao transactionDocumentDao;

    @Autowired
    private NicTransactionLogDao transactionLogDao;

    //@Autowired
    //private IssuanceDataService issuanceDataService;

    @Autowired
    private ParametersDao parametersDao;

    @Autowired
    private TransactionLogService transactionLogService;

    @Autowired
    private NicRegistrationDataDao registrationDataDao;

    private JobXmlConvertor util = new JobXmlConvertor();

    @Autowired
    private FileTransferService fileTransferService;

    @Autowired
    private NicTransactionDao transactionDao;

    @Autowired
    private DocumentDataDao documentDataDao;

    @Autowired
    private DispatchDataDao dispatchDataDao;

    @Autowired
    private SiteRepositoryDao siteRepositoryDao;

    @Autowired
    private PersoInfoDao persoInfoDao;

    @Autowired
    CodesService codesService;
    
    @Autowired
    NicUploadJobDao uploadJobDao;
    
    @Autowired
    NicRejectionDataDao nicRejectionDataDao;
    
	@Autowired
	private NicTransactionReprintDao transactionReprintDao;
	
    private SecureFtpServer sftpServer;

    private String dispatchDirectory = StringUtils.EMPTY;

    private String dataDirectory = StringUtils.EMPTY;

    private String statusDirectory = StringUtils.EMPTY;
    
    private String archiveDirectory = StringUtils.EMPTY;

    private String rootDirectory = StringUtils.EMPTY;

    private PersoXmlConvertor persoXmlConverter = new PersoXmlConvertor();

    //     // 2015-Dec-12 (Khang) Change logic to submit perso data to SFTP server instead of call web service
    //    public void submitPersoData(NicTransaction nicTransaction) throws PersoServiceException {
    //        String transactionId = null;
    //        String persoRefId = null;
    //        Date startTime = new Date();
    //        String logData = null;
    //        String transactionStatus = null;
    //        try {
    //            //29 Oct 2013 (chris) - start
    //            String officerId = SYSTEM_NIC; //"SYSTEM";
    //            String wkstnId = SYSTEM_NIC; //"SYSTEM";
    //            try {
    //                java.net.InetAddress localMachine = InetAddress.getLocalHost();
    //                wkstnId = localMachine.getHostName();
    //            } catch (Exception e) {
    //            }
    //            //29 Oct 2013 (chris) - end
    //            //Step 1: submit prepared data to perso
    //            if (nicTransaction != null) {
    //                transactionId = nicTransaction.getTransactionId();
    //                // String nin = nicTransaction.getNin();
    //                // Date dateOfIssue = nicTransaction.getDateOfIssue(); //this.getDateOfIssueFromPersoXml(persoXml);
    //                String persoXml = this.retrievePersoXml(transactionId);
    //                //29 Oct 2013 (chris): add express flag - start
    //                PersoDTO persoDTO = this.parsePersoDTO(persoXml);
    //                int expressThreshold = getExpressThresholdFromParameter();
    //                Date cutoffDate = DateUtil.addDays(nicTransaction.getDateOfApplication(), expressThreshold);
    //                if (DateUtil.isAfterDate(startTime, cutoffDate)) {
    //                    // persoDTO.setExpress(DataPackDTOMapper.PERSO_YES_FLAG);
    //                    logger.info("[preparePersoData] transaction[{}] is after the cutoff day, nic system flag out as express transaction.", nicTransaction.getTransactionId());
    //                    persoXml = this.preparePersoXml(persoDTO);
    //                    //to update
    //                    NicTransactionAttachment nicTransDocPerso = null;
    //                    List<NicTransactionAttachment> docResultList =
    //                        transactionDocumentDao.findNicTransactionAttachments(transactionId, NicTransactionAttachment.DOC_TYPE_PERSO, NicTransactionAttachment.DEFAULT_SERIAL_NO);
    //                    if (CollectionUtils.isNotEmpty(docResultList)) { //update
    //                        nicTransDocPerso = docResultList.get(0);
    //                        nicTransDocPerso.setDocData(Base64.encodeBase64(persoXml.getBytes()));
    //                        nicTransDocPerso.setUpdateBy(officerId);//"SYSTEM");
    //                        nicTransDocPerso.setUpdateDatetime(new Date());
    //                        nicTransDocPerso.setCreateWkstnId(wkstnId);//"SYSTEM");
    //                        transactionDocumentDao.saveOrUpdate(nicTransDocPerso);
    //                    }
    //                }
    //                //29 Oct 2013 (chris): add express flag - end
    //                String encrytedPersoXml = util.encodeString(persoXml);
    //                String persoXmlChecksum = util.computeCheckSum(encrytedPersoXml);
    //                //persoXml = "<![CDATA[" +persoXml+ "]]>";
    //                logger.debug("persoXmlChecksum={}, encrytedPersoXml={}", persoXmlChecksum, encrytedPersoXml);
    //                // logger.info("[before][persoWS.downloadJob] transactionId={}, dateOfIssue={}", transactionId, dateOfIssue);
    //                //04 Oct 2013 (jp): synchronize call to perso
    //                // synchronized (this) {
    //                // persoRefId = persoWS.downloadJob(encrytedPersoXml, persoXmlChecksum);
    //                // }
    //                //04 Oct 2013 (jp): synchronize call to perso - end
    //                logger.info("[ after][persoWS.downloadJob] transactionId={}, persoRefId={}", transactionId, persoRefId);
    //                //03 Oct 2013 (chris) - start
    //                if (StringUtils.equals(persoRefId, DEFAULT_PERSO_REF_ID)) {
    //                    throw new PersoServiceException("Invalid Perso Ref Id received.");
    //                }
    //                //03 Oct 2013 (chris) - end
    //                transactionStatus = TRANSACTION_STATUS_PERSO_REGISTER_COMPLETED;
    //                //Step 2: save perso refId into nicIssuanceData
    //                NicIssuanceData nicIssuanceDataDBO = new NicIssuanceData();
    //                nicIssuanceDataDBO.setId(new NicIssuanceDataId(transactionId, persoRefId));
    //                // nicIssuanceDataDBO.setNin(nin);
    //                // nicIssuanceDataDBO.setDateOfIssue(dateOfIssue);
    //                nicIssuanceDataDBO.setCreateDate(new Date());
    //                nicIssuanceDataDBO.setCreateBy(officerId);//"SYSTEM");
    //                nicIssuanceDataDBO.setCreateWkstnId(wkstnId);//"SYSTEM");
    //                nicIssuanceDataDao.save(nicIssuanceDataDBO);
    //                logger.debug("[submitPersoData] save NicIssuanceData - {}", persoRefId);
    //            }
    //        } catch (Exception e) {
    //            transactionStatus = TRANSACTION_STATUS_PERSO_REGISTER_ERROR;
    //            logData = MiscXmlConvertor.parseObjectToXml(e);
    //            throw new PersoServiceException(e);
    //        } finally {
    //            //Step 3 : To save the transaction log
    //            // try {
    //            // if (StringUtils.isNotBlank(transactionId)) {
    //            // this.saveTransactionLog(transactionId, TRANSACTION_STATE_PERSO_REGISTER, transactionStatus, startTime, new Date(), null, logData);
    //            // }
    //            // } catch (Exception e) {
    //            // logger.error("Exception in finally block (PersoService.submitPersoData)", e);
    //            // }
    //        }
    //    }*/

    //
    //    public void updatePersoStatus(String cardInfoUploadXml, String xmlCheckSum) throws PersoServiceException {
    //        Date startTime = new Date();
    //        try {
    //            //28 Oct 2013 (chris): add transaction log - start
    //            String officerId = SYSTEM_PERSO;
    //            String wkstnId = SYSTEM_PERSO;
    //            try {
    //                java.net.InetAddress localMachine = InetAddress.getLocalHost();
    //                wkstnId = localMachine.getHostName();
    //            } catch (Exception e) {
    //            }
    //            //28 Oct 2013 (chris): add transaction log - end
    //
    //            //Step 1: check xml checksum
    //            String computedCS = util.computeCheckSum(cardInfoUploadXml);
    //            logger.debug("compare checksum [1]:{} , [2]:{}", xmlCheckSum, computedCS);
    //            if (!StringUtils.equalsIgnoreCase(xmlCheckSum, computedCS)) {
    //                throw new PersoServiceException("The checksum doesn't matched.");
    //            }
    //            //[02 Jul 2013]
    //            cardInfoUploadXml = util.decodeString(cardInfoUploadXml);
    //            logger.debug("Payload Decoded=" + cardInfoUploadXml);
    //            //Step 2: to parse the xml and update the result (ccn, status)
    //            AbstractCardInfoDTO cardInfoDto = parseCardInfoDTO(cardInfoUploadXml);
    //            if (cardInfoDto == null) {
    //                throw new PersoServiceException("Unable to parse the cardInfoDTO.");
    //            }
    //            if (cardInfoDto instanceof PackageInfoDTO) {
    //                PackageInfoDTO packageInfoDto = (PackageInfoDTO) cardInfoDto;
    //                String dispatchId = packageInfoDto.getDispatchId();
    //                List<String> packageIdList = packageInfoDto.getPackageIdList();
    //                if (CollectionUtils.isNotEmpty(packageIdList)) {
    //                    logger.info("[PERSO] update package status: dispatchId:{}, status:{} , packageIdList:{} ", new Object[] {
    //                        dispatchId, packageInfoDto.getStatus(), packageInfoDto.getPackageIdList()
    //                    });
    //                    //02 Jun 2014 (chris): check the package id existence - start
    //                    for (String packageId : packageIdList) {
    //                        List<NicIssuanceData> issuanceDataList = nicIssuanceDataDao.findByPackageId(packageId);
    //                        if (CollectionUtils.isEmpty(issuanceDataList)) {
    //                            this.getPackageJobByPackageID(packageId);
    //                        }
    //                    }
    //                    //02 Jun 2014 (chris): check the package id existence - end
    //
    //                    //                    this.nicIssuanceDataDao.updateTransactionByPackageId(dispatchId, packageIdList, PersoService.TRANSACTION_STATUS_PERSO_PROCESS_COMPLETED, officerId, wkstnId);//SYSTEM_PERSO, SYSTEM_PERSO);
    //
    //                    //28 Oct 2013 (chris): add transaction log - start
    //                    for (String packageId : packageIdList) {
    //                        List<NicIssuanceData> issuanceDataList = nicIssuanceDataDao.findByPackageId(packageId);
    //                        if (CollectionUtils.isNotEmpty(issuanceDataList)) {
    //                            for (NicIssuanceData issuanceData : issuanceDataList) {
    //                                String transactionId = issuanceData.getId().getTransactionId();
    //                                String logData = "dispatchId=" + dispatchId + "packageId=" + packageId;
    //                                this.saveTransactionLog(transactionId, TRANSACTION_STAGE_PERSONALIZATION, TRANSACTION_STATUS_CARD_DISPATCHED, startTime, new Date(), null, logData);
    //                            }
    //                        }
    //                        //02 Jun 2014 (chris): add transaction log if package id not found in DB
    //                        else {
    //                            LogInfoDTO logInfoDTO = new LogInfoDTO();
    //                            logInfoDTO.setReason(NIC_PERSO_ERROR_CODE_PACKAGE_NO_FOUND);
    //                            String logInfo = this.getLogInfoXml(logInfoDTO);
    //                            String logData = "dispatchId=" + dispatchId + "packageId=" + packageId;
    //                            this.saveTransactionLog(packageId, TRANSACTION_STAGE_PERSONALIZATION, TRANSACTION_STATUS_CARD_DISPATCHED, startTime, new Date(), logInfo, logData);
    //                        }
    //                    }
    //                    //28 Oct 2013 (chris): add transaction log - end
    //                } else
    //                    throw new PersoServiceException("List of packageID cannot be empty");
    //            }
    //            //[29 Aug 2013] add update destroy card status, it only update by Inventory.
    //            if (cardInfoDto instanceof TransactionInfoDTO) {
    //                TransactionInfoDTO transactionInfoDTO = (TransactionInfoDTO) cardInfoDto;
    //                String ccn = transactionInfoDTO.getCcn();
    //                String status = transactionInfoDTO.getStatus();
    //                logger.debug("[updateTransactionByCcn], ccn={}, status={}", ccn, status);
    //                this.issuanceDataService.updateTransactionByCcn(ccn, status, officerId, wkstnId); //SYSTEM_PERSO, SYSTEM_PERSO);
    //            }
    //        } catch (Exception ex) {
    //            throw new PersoServiceException(ex);
    //        }
    //    }

    @Transactional(rollbackFor = PersoServiceException.class, propagation = Propagation.REQUIRED)
    public void receiveNicPersoInfo(String nicPersoInfoUploadXml, String xmlCheckSum) throws PersoServiceException {
    	throw new PersoServiceException("Function not working.");
    	
        //boolean receivedFromWS = true;
        //this.receiveNicPersoInfo(nicPersoInfoUploadXml, xmlCheckSum, receivedFromWS);
    }

//    private void receiveNicPersoInfo(String nicPersoInfoUploadXml, String xmlCheckSum, boolean receivedFromWS) throws PersoServiceException {
//        String transactionId = null;
//        String persoRefId = null;
//        String logData = null;
//        String logInfo = null;
//        String transactionStatus = null;
//        Date startTime = new Date();
//        try {
//            //Step 1: check xml checksum
//            String computedCS = util.computeCheckSum(nicPersoInfoUploadXml);
//            logger.debug("compare checksum [1]:{} , [2]:{}", xmlCheckSum, computedCS);
//            if (!StringUtils.equalsIgnoreCase(xmlCheckSum, computedCS)) {
//                logger.error("checksum mismatch error [1]:{} , [2]:{}", xmlCheckSum, computedCS);
//                throw new PersoServiceException("The checksum doesn't matched.");
//            }
//            //[02 Jul 2013]
//            nicPersoInfoUploadXml = util.decodeString(nicPersoInfoUploadXml);
//            logger.debug("Payload Decoded=" + nicPersoInfoUploadXml);
//            //Step 2: to parse the xml 
//            NicPersoInfoDTO nicPersoInfoDTO = parseNicPersoInfoDTO(nicPersoInfoUploadXml);
//            if (nicPersoInfoDTO == null) {
//                logger.error("Unable to parse the nicPersoInfo. xmlLen:{} , xml(100):{}", StringUtils.length(nicPersoInfoUploadXml), StringUtils.substring(nicPersoInfoUploadXml, 0, 100));
//                throw new PersoServiceException("Unable to parse the nicPersoInfo.");
//            }
//            //Step 2.1: save the result (ccn, samKeyVersion, etc)
//            if (CollectionUtils.isNotEmpty(nicPersoInfoDTO.getCardDataList())) {//(nicPersoInfoDTO.getLogData()!=null) {
//                logger.info("Packaging Completed[{}]. CardDataList.size={}", nicPersoInfoDTO.getPackageID(), nicPersoInfoDTO.getCardDataList().size());
//                for (CardDataDTO cardDataDTO : nicPersoInfoDTO.getCardDataList()) {
//                    transactionId = cardDataDTO.getTransactionID();
//                    persoRefId = cardDataDTO.getPersoRefID();
//                    String ccn = cardDataDTO.getCcn();
//                    Short samKeyVersion = 0;
//                    if (StringUtils.isNotBlank(cardDataDTO.getSAMKeyVersion())) {
//                        samKeyVersion = new Short(cardDataDTO.getSAMKeyVersion());
//                    } else {
//                        throw new PersoServiceException("Unable to get SamKeyVersion.");
//                    }
//                    String packageId = nicPersoInfoDTO.getPackageID();
//                    String packageSequence = cardDataDTO.getPackageSequence();//13 Sep 2013 (chris)
//                    //populate stub class of IssuanceData from input 
//                    NicIssuanceDataId id = new NicIssuanceDataId(transactionId, persoRefId);
//                    NicIssuanceData nicIssuanceData = nicIssuanceDataDao.findById(id);
//                    if (nicIssuanceData == null) {
//                        //09 Sep 2013 (chris): to handle perso ref id. - start
//                        List<NicIssuanceData> nicIssDataList = nicIssuanceDataDao.findByTransactionId(transactionId);
//                        if (CollectionUtils.isNotEmpty(nicIssDataList) && nicIssDataList.size() == 1) {
//                            NicIssuanceData nicIssData = nicIssDataList.get(0);
//                            nicIssuanceData = nicIssData;
//                            logger.warn("transactionId[{}], persoRefId[{}] not found, replaced by transactionId[{}], persoRefId[{}] ", new String[] {
//                                transactionId, persoRefId, nicIssuanceData.getId().getTransactionId(), nicIssuanceData.getId().getPersoRefId()
//                            });
//                        } else {
//                            //09 Sep 2013 (chris): to handle perso ref id. - end
//                            //20 Feb 2014 (chris): to insert record instead of throw exception - start
//                            //throw new PersoServiceException("No existing card data found for transactionId:" + transactionId +"; perso refID:" + persoRefId);
//                            logger.info("transactionId[{}], persoRefId[{}] not found, inserting new record.", new String[] {
//                                transactionId, persoRefId
//                            });
//                            NicTransaction nicTransactionDBO = nicTransactionService.findById(transactionId, false, false, false, false);
//                            if (nicTransactionDBO != null) {
//                                //								String nin = nicTransactionDBO.getNin();
//                                //								Date dateOfIssue = nicTransactionDBO.getDateOfIssue();
//                                NicIssuanceData nicIssData = new NicIssuanceData();
//                                nicIssData.setId(id);
//                                //								nicIssData.setNin(nin);
//                                //								nicIssData.setDateOfIssue(dateOfIssue);
//                                nicIssData.setCreateDate(new Date());
//                                nicIssData.setCreateBy(SYSTEM_PERSO);//"SYSTEM");
//                                nicIssData.setCreateWkstnId(SYSTEM_PERSO);//"SYSTEM");
//                                nicIssuanceData = nicIssData;
//                            } else {
//                                throw new PersoServiceException("No existing card data found for transactionId:" + transactionId + "; perso refID:" + persoRefId);
//                            }
//                            //20 Feb 2014 (chris): to insert record instead of throw exception - end
//                        }
//                    }
//
//                    if (StringUtils.isBlank(nicIssuanceData.getCcn()) || StringUtils.isBlank(nicIssuanceData.getPackageId())) {
//                        if (StringUtils.isBlank(nicIssuanceData.getNin()) && StringUtils.isNotBlank(cardDataDTO.getNin())) {
//                            nicIssuanceData.setNin(cardDataDTO.getNin());
//                        }
//                        String cardDataDTOXml = this.getCardDataDTOXml(cardDataDTO);
//                        logData = cardDataDTOXml;
//                        nicIssuanceData.setCcn(ccn);
//                        nicIssuanceData.setSamKeyVersion(samKeyVersion);
//                        nicIssuanceData.setUpdateBy(SYSTEM_PERSO);
//                        nicIssuanceData.setUpdateWkstnId(SYSTEM_PERSO);
//                        nicIssuanceData.setUpdateDate(new Date());
//
//                        if (StringUtils.isNotBlank(packageId) && StringUtils.isBlank(nicIssuanceData.getPackageId())) {
//                            nicIssuanceData.setPackageId(packageId);
//                        }
//                        if (StringUtils.isNotBlank(packageSequence) && StringUtils.isBlank(nicIssuanceData.getPackageSequence())) {
//                            nicIssuanceData.setPackageSequence(packageSequence);//13 Sep 2013 (chris)
//                        }
//                        if (nicIssuanceData.getPackageDate() == null) {
//                            nicIssuanceData.setPackageDate(new Date());
//                        }
//                        logger.info("[receiveNicPersoInfo] received data for transactionId:{}, persoRefId:{}, ccn:{}, packageId:{}", new Object[] {
//                            transactionId, persoRefId, ccn, packageId
//                        });
//                        nicIssuanceDataDao.saveOrUpdate(nicIssuanceData);
//
//                        //this.issuanceDataService.updateTransactionByTransactionId(transactionId, PersoService.TRANSACTION_STATUS_CARD_PERSONALIZED, null, PersoService.SYSTEM_PERSO, PersoService.SYSTEM_PERSO);
//                        
//                        //update nicmain
//                        //[2013 Aug 27] move logic to nicMainService
//                        NicTransaction nicTransactionDBO = nicTransactionService.findById(transactionId, true, true, true, false);
//                        //						nicMainService.updateNicMainAndHistory(nicTransactionDBO, ccn);
//                    } else {
//                        logger.info("[receiveNicPersoInfo] data exists for transactionId:{}, persoRefId:{}, ccn:{}, packageId:{}", new Object[] {
//                            transactionId, persoRefId, nicIssuanceData.getCcn(), nicIssuanceData.getPackageId()
//                        });
//                    }
//                    //Step 3: save transaction log
//                    if (receivedFromWS) { //[2014 Jun 03] no need to log to transaction log if nic pull data from CPC.
//                        Date endTime = new Date();
//                        transactionStatus = TRANSACTION_STATUS_CARD_PERSONALIZED;
//                        this.saveTransactionLog(transactionId, TRANSACTION_STAGE_PERSONALIZATION, transactionStatus, startTime, endTime, logInfo, logData);
//                    }
//                }
//            } else if (nicPersoInfoDTO.getLogInfo() != null) {
//                transactionId = nicPersoInfoDTO.getTransactionID();
//                persoRefId = nicPersoInfoDTO.getPersoRefID();
//                String reprintCount = nicPersoInfoDTO.getReprintCount();
//                String nin = nicPersoInfoDTO.getNin();
//
//                //Construct LogInfo that indicate Card is error or rejected
//                transactionStatus = NicTransactionService.TRANSACTION_STATUS_PERSO_ERROR;
//
//                logger.info("[NicPersoInfo] transactionId:{}, persoRefId:{}, transactionStatus:{}, reprintCount:{}, nin:{}", new Object[] {
//                    transactionId, persoRefId, transactionStatus, reprintCount, nin
//                });
//                logInfo = this.getLogInfoXml(nicPersoInfoDTO.getLogInfo());
//                //07 Oct 2013 (chris) - start
//                logData = nicPersoInfoUploadXml;
//                if (StringUtils.isNotBlank(persoRefId) && StringUtils.equals(transactionId, "0")) {
//                    //13 Nov 2013 (chris) - start
//                    //transactionId = persoRefId;					
//                    List<NicIssuanceData> nicIssDataList = nicIssuanceDataDao.findByPersoRefId(persoRefId);
//                    if (CollectionUtils.isNotEmpty(nicIssDataList) && nicIssDataList.size() == 1) {
//                        NicIssuanceData nicIssData = nicIssDataList.get(0);
//                        transactionId = nicIssData.getId().getTransactionId();
//                        logger.info("transactionId[{}], persoRefId[{}] ", new Object[] {
//                            transactionId, persoRefId
//                        });
//                    } else {
//                        logger.error("persoRefId[{}] cannot find the matching issuance data", new Object[] {
//                            persoRefId
//                        });
//                    }
//                    //13 Nov 2013 (chris) - end
//                }
//                //07 Oct 2013 (chris) - end
//                this.issuanceDataService.updateTransactionByTransactionId(transactionId, transactionStatus, null, PersoService.SYSTEM_PERSO, PersoService.SYSTEM_PERSO);
//
//                //Step 3: save transaction log
//                Date endTime = new Date();
//                this.saveTransactionLog(transactionId, TRANSACTION_STAGE_PERSONALIZATION, transactionStatus, startTime, endTime, logInfo, logData);
//
//                //to handle perso reject and data de-serialize error
//                if (StringUtils.equalsIgnoreCase(nicPersoInfoDTO.getLogInfo().getReason(), PERSO_ERROR_CODE_DESERIALIZE_DATA_ERROR)
//                    || StringUtils.equalsIgnoreCase(nicPersoInfoDTO.getLogInfo().getReason(), PERSO_ERROR_CODE_PERSO_QC_PERMANENT_REJECT)) {
//                    //to check no card data in same transaction_id
//                    boolean hasCardPrinted = false;
//                    if (!StringUtils.equals(transactionId, "0")) {
//                        List<NicIssuanceData> nicIssDataList = nicIssuanceDataDao.findByTransactionId(transactionId);
//                        if (CollectionUtils.isNotEmpty(nicIssDataList)) {
//                            for (NicIssuanceData nicIssData : nicIssDataList) {
//                                if (StringUtils.isNotBlank(nicIssData.getCcn())) {
//                                    hasCardPrinted = true;
//                                    logger.warn("transactionId[{}] has a printed card[{}], need to check the record manually.", new Object[] {
//                                        transactionId, nicIssData.getCcn()
//                                    });
//                                    break;
//                                }
//                            }
//                        }
//                    }
//
//                    if (!hasCardPrinted) {
//                        logger.info("TransactionId[{}] don't have printed card.", new Object[] {
//                            transactionId
//                        });
//                        NicRegistrationData regData = registrationDataDao.findById(transactionId);
////						if (regData!=null && regData.getNicUploadJobId()!=null) {
////							Long uploadJobId = regData.getNicUploadJobId();
////							NicUploadJob nicUploadJob = uploadJobDao.findById(uploadJobId);
////							logger.info("TransactionId[{}] have nicUploadJob as {}, current state:{} .", new Object[] {transactionId, nicUploadJob.getUploadJobId(), nicUploadJob.getJobCurrentState()});
////							if (nicUploadJob!=null) {
////								//to update nic_upload_job.perso_register_status as '09', nic_upload_job.job_reprocess_count as '0'
////								if (StringUtils.equalsIgnoreCase(nicPersoInfoDTO.getLogInfo().getReason(), PERSO_ERROR_CODE_DESERIALIZE_DATA_ERROR)) {
////									//to update old issuance data as cancelled
////									NicIssuanceDataId id = new NicIssuanceDataId(transactionId, persoRefId);
////									NicIssuanceData nicIssuanceData = nicIssuanceDataDao.findById(id);
////									if (nicIssuanceData!=null) {
////										nicIssuanceData.setCancelStatus(NicIssuanceData.CANCEL_STATUS_CANCELLED);
////										nicIssuanceData.setCancelReason(NicIssuanceData.CANCEL_REASON_REJECTED_BY_SYSTEM_PERSO);
////										nicIssuanceData.setUpdateBy(PersoService.SYSTEM_PERSO);
////										nicIssuanceData.setUpdateWkstnId(PersoService.SYSTEM_PERSO);
////										nicIssuanceData.setUpdateDate(new Date());
////										logger.info("transactionId[{}], persoRefId[{}] updating as cancelled : REJECTED_BY_SYSTEM_PERSO.", new Object[] {transactionId, persoRefId});
////										nicIssuanceDataDao.saveOrUpdate(nicIssuanceData);
////									}
////									nicUploadJob.setPersoRegisterStatus(NicUploadJob.RECORD_STATUS_ERROR);
////									nicUploadJob.setJobReprocessCount(0);
////									logger.info("transactionId[{}], uploadJobId[{}] updated for resubmit to perso.", new Object[] {transactionId, nicUploadJob.getUploadJobId()});
////									uploadJobDao.save(nicUploadJob);
////								//to update nic_upload_job.investigation_status as '00', investigation_type as 'PERSO'
////								} else if (StringUtils.equalsIgnoreCase(nicPersoInfoDTO.getLogInfo().getReason(), PERSO_ERROR_CODE_PERSO_QC_PERMANENT_REJECT)) {
////									//to update old issuance data as cancelled
////									NicIssuanceDataId id = new NicIssuanceDataId(transactionId, persoRefId);
////									NicIssuanceData nicIssuanceData = nicIssuanceDataDao.findById(id);
////									if (nicIssuanceData!=null) {
////										nicIssuanceData.setCancelStatus(NicIssuanceData.CANCEL_STATUS_CANCELLED);
////										nicIssuanceData.setCancelReason(NicIssuanceData.CANCEL_REASON_REJECTED_BY_CPC);
////										nicIssuanceData.setUpdateBy(PersoService.SYSTEM_PERSO);
////										nicIssuanceData.setUpdateWkstnId(PersoService.SYSTEM_PERSO);
////										nicIssuanceData.setUpdateDate(new Date());
////										logger.info("transactionId[{}], persoRefId[{}] updating as cancelled : REJECTED_BY_CPC.", new Object[] {transactionId, persoRefId});
////										nicIssuanceDataDao.saveOrUpdate(nicIssuanceData);
////									}
////									nicUploadJob.setInvestigationType(NicUploadJob.INVESTIGATION_TYPE_PERSO);
////									nicUploadJob.setInvestigationStatus(NicUploadJob.RECORD_STATUS_INITIAL);
////									nicUploadJob.setInvestigationOfficerId(null);
////									nicUploadJob.setUpdateBy(PersoService.SYSTEM_PERSO);
////									nicUploadJob.setUpdateWkstnId(PersoService.SYSTEM_PERSO);
////									nicUploadJob.setUpdateDate(new Date());
////									
////									logger.info("transactionId[{}], uploadJobId[{}] updated for routing to investigation.", new Object[] {transactionId, nicUploadJob.getUploadJobId()});
////									uploadJobDao.save(nicUploadJob);
////								} 
////							}
////						} else {
////							logger.warn("TransactionId[{}] don't have matching job, need to check the record manually.", new Object[] {transactionId});
////						}
//                    }
//                }
//            } else {
//                logger.warn("No card data/log data from from this request: {} ", new Object[] {
//                    nicPersoInfoUploadXml
//                });
//                //throw new PersoServiceException("Invalid card data from perso for transactionId:" + transactionId +"; perso refID:" + persoRefId);
//            }
//
//        } catch (Exception e) {
//            throw new PersoServiceException(e);
//        }
//    }

    @Transactional(rollbackFor = PersoServiceException.class, propagation = Propagation.REQUIRED)
    public void updateCardStatus(String cardInfoUploadXml, String xmlCheckSum) throws PersoServiceException {
    	throw new PersoServiceException("Function not working.");
    	
//    	Date startTime = new Date();
//        try {
//            //28 Oct 2013 (chris): add transaction log - start
//            String officerId = SYSTEM_PERSO;
//            String wkstnId = SYSTEM_PERSO;
//            try {
//                java.net.InetAddress localMachine = InetAddress.getLocalHost();
//                wkstnId = localMachine.getHostName().toUpperCase();
//            } catch (Exception e) {
//            }
//            //28 Oct 2013 (chris): add transaction log - end
//
//            //Step 1: check xml checksum
//            String computedCS = util.computeCheckSum(cardInfoUploadXml);
//            logger.debug("compare checksum [1]:{} , [2]:{}", xmlCheckSum, computedCS);
//            if (!StringUtils.equalsIgnoreCase(xmlCheckSum, computedCS)) {
//                throw new PersoServiceException("The checksum doesn't matched.");
//            }
//            //[02 Jul 2013]
//            cardInfoUploadXml = util.decodeString(cardInfoUploadXml);
//            logger.debug("Payload Decoded=" + cardInfoUploadXml);
//            //Step 2: to parse the xml and update the result (ccn, status)
//            AbstractCardInfoDTO cardInfoDto = parseCardInfoDTO(cardInfoUploadXml);
//            if (cardInfoDto == null) {
//                throw new PersoServiceException("Unable to parse the cardInfoDTO.");
//            }
//            //[01 Aug 2013] remove update card status, it only update by RIC.
//            //			if (cardInfoDto instanceof CcnInfoDTO) {
//            //				CcnInfoDTO ccnInfoDto = (CcnInfoDTO) cardInfoDto;
//            //				List<String> ccnList = ccnInfoDto.getCcnList();
//            //				for (String ccn : ccnList) {
//            //					this.issuanceDataService.updateStatusByCCn(ccn, cardInfoDto.getStatus());					
//            //				}
//            //			} else 
//            if (cardInfoDto instanceof PackageInfoDTO) {
//                PackageInfoDTO packageInfoDto = (PackageInfoDTO) cardInfoDto;
//                String dispatchId = packageInfoDto.getDispatchId();
//                List<String> packageIdList = packageInfoDto.getPackageIdList();
//                if (CollectionUtils.isNotEmpty(packageIdList)) {
//                    logger.info("[PERSO] update package status: dispatchId:{}, status:{} , packageIdList:{} ", new Object[] {
//                        dispatchId, packageInfoDto.getStatus(), packageInfoDto.getPackageIdList()
//                    });
//                    //02 Jun 2014 (chris): check the package id existence - start
//                    for (String packageId : packageIdList) {
//                        List<NicIssuanceData> issuanceDataList = nicIssuanceDataDao.findByPackageId(packageId);
//                        if (CollectionUtils.isEmpty(issuanceDataList)) {
//                            this.getPackageJobByPackageID(packageId);
//                        }
//                    }
//                    //02 Jun 2014 (chris): check the package id existence - end
//
//                    this.nicIssuanceDataDao.updateTransactionByPackageId(dispatchId, packageIdList, NicTransactionService.TRANSACTION_STATUS_PERSO_QC_COMPLETED, officerId, wkstnId);//SYSTEM_PERSO, SYSTEM_PERSO);
//
//                    //28 Oct 2013 (chris): add transaction log - start
//                    for (String packageId : packageIdList) {
//                        List<NicIssuanceData> issuanceDataList = nicIssuanceDataDao.findByPackageId(packageId);
//                        if (CollectionUtils.isNotEmpty(issuanceDataList)) {
//                            for (NicIssuanceData issuanceData : issuanceDataList) {
//                                String transactionId = issuanceData.getId().getTransactionId();
//                                String logData = "dispatchId=" + dispatchId + "packageId=" + packageId;
//                                this.saveTransactionLog(transactionId, TRANSACTION_STAGE_PERSONALIZATION, TRANSACTION_STATUS_CARD_DISPATCHED, startTime, new Date(), null, logData);
//                            }
//                        }
//                        //02 Jun 2014 (chris): add transaction log if package id not found in DB
//                        else {
//                            LogInfoDTO logInfoDTO = new LogInfoDTO();
//                            logInfoDTO.setReason(NIC_PERSO_ERROR_CODE_PACKAGE_NO_FOUND);
//                            String logInfo = this.getLogInfoXml(logInfoDTO);
//                            String logData = "dispatchId=" + dispatchId + "packageId=" + packageId;
//                            this.saveTransactionLog(packageId, TRANSACTION_STAGE_PERSONALIZATION, TRANSACTION_STATUS_CARD_DISPATCHED, startTime, new Date(), logInfo, logData);
//                        }
//                    }
//                    //28 Oct 2013 (chris): add transaction log - end
//                } else
//                    throw new PersoServiceException("List of packageID cannot be empty");
//            }
//            //[29 Aug 2013] add update destroy card status, it only update by Inventory.
//            if (cardInfoDto instanceof TransactionInfoDTO) {
//                TransactionInfoDTO transactionInfoDTO = (TransactionInfoDTO) cardInfoDto;
//                String ccn = transactionInfoDTO.getCcn();
//                String status = transactionInfoDTO.getStatus();
//                logger.debug("[updateTransactionByCcn], ccn={}, status={}", ccn, status);
//                this.issuanceDataService.updateTransactionByCcn(ccn, status, officerId, wkstnId); //SYSTEM_PERSO, SYSTEM_PERSO);
//            }
//        } catch (Exception ex) {
//            throw new PersoServiceException(ex);
//        }
    }

    public void getPackageJobByPackageID(String packageId) throws PersoServiceException {
    	throw new PersoServiceException("Function not working.");
    	
//        try {
//            Holder<String> xml = new Holder<String>();
//            Holder<String> checksum = new Holder<String>();
//            Holder<String> getPackageJobByPackageIDResult = new Holder<String>();
//            //			synchronized (this) {
//            //				persoWS.getPackageJobByPackageID(packageId, xml, checksum, getPackageJobByPackageIDResult);
//            //			}
//            if (xml != null && StringUtils.isNotBlank(xml.value) && checksum != null && StringUtils.isNotBlank(checksum.value)) {
//                logger.info("receive from Perso");
//                this.receiveNicPersoInfo(xml.value, checksum.value, false);
//            } else {
//                logger.warn("No data receive from Perso.");
//            }
//        } catch (Exception e) {
//            throw new PersoServiceException(e);
//        }
    }

    public void getPackageJobByTransactionID(String transactionId) throws PersoServiceException {
    	throw new PersoServiceException("Function not working.");
    	
//        try {
//            Holder<String> xml = new Holder<String>();
//            Holder<String> checksum = new Holder<String>();
//            Holder<String> getPackageJobByTransactionIDResult = new Holder<String>();
//            //			synchronized (this) {
//            //				persoWS.getPackageJobByTransactionID(transactionId, xml, checksum, getPackageJobByTransactionIDResult);
//            //			}
//            if (xml != null && StringUtils.isNotBlank(xml.value) && checksum != null && StringUtils.isNotBlank(checksum.value)) {
//                logger.info("receive from Perso");
//                this.receiveNicPersoInfo(xml.value, checksum.value, false);
//            } else {
//                logger.warn("No data receive from Perso.");
//            }
//        } catch (Exception e) {
//            throw new PersoServiceException(e);
//        }
    }

    /* Jobcancellationservice */
    /**
     * Perso Job Cancellation Service
     * Return Code Description
     * 0 Success � Job cancelled
     * 1 Failed � Can try to request on NIC end
     * 2 Denied � Record already past the job cancellation allowance period. Retry will return same result
     */
    public ResponseDTO cancelJobByTransactionId(String transactionId, String officerId, String workstationId) throws PersoServiceException {
        logger.info("[cancelJobByTransactionId] transactionId:{}, officerId:{}, workstationId:{}", new Object[] {
            transactionId, officerId, workstationId
        });
        ResponseDTO responseDTO = new ResponseDTO();
        Date startTime = new Date();
        LogInfoDTO logInfoDTO = new LogInfoDTO();
        String transactionStatus = TRANSACTION_STATUS_PERSO_CANCEL_ERROR;
        try {
            Holder<Integer> cancelJobByTransactionIDResult = new Holder<Integer>();
            Holder<String> serviceMessage = new Holder<String>();
            //			jobCancellationWS.cancelJobByTransactionID(transactionId, serviceMessage, cancelJobByTransactionIDResult);
            int code = cancelJobByTransactionIDResult.value;
            String message = serviceMessage.value;
            responseDTO.setServiceCode(Integer.toString(code));
            responseDTO.setServiceMessage(message);
            logger.info("[cancelJobByTransactionId] transactionId:{}, code:{}, message:{}.", new Object[] {
                transactionId, code, message
            });
            logInfoDTO.setRemark(message);
            switch (code) {
                case PERSO_CANCEL_SUCCEED:
                    transactionStatus = TRANSACTION_STATUS_PERSO_CANCEL_COMPLETED;
                    break;
                case PERSO_CANCEL_FAILED:
                    transactionStatus = TRANSACTION_STATUS_PERSO_CANCEL_ERROR;
                    break;
                case PERSO_CANCEL_DENIED:
                    transactionStatus = TRANSACTION_STATUS_PERSO_CANCEL_REJECTED;
                    break;
            }
        } catch (Exception e) {
            throw new PersoServiceException(e);
        } finally {
            String siteCode = this.getCurrentSiteCodeFromParameter();
            String logInfo = this.getLogInfoXml(logInfoDTO);
            Long logId =
                transactionLogService.saveTransactionLog(transactionId, TRANSACTION_STAGE_PERSO_CANCEL, transactionStatus, officerId, workstationId, siteCode, startTime, new Date(), logInfo, null).getModel();
            logger.info("[cancelJobByTransactionId] save NicTransactionLog completed, transactionId:{}, logId:{} ", transactionId, logId);
        }
        return responseDTO;
    }

    private NicPersoInfoDTO parseNicPersoInfoDTO(String nicPersoInfoUploadXml) throws JaxbXmlConvertorException {
        NicPersoInfoDTO nicPersoInfoDTO = null;
        PersoReferenceDataDTO dataExchangeDTO = (PersoReferenceDataDTO) util.unmarshal(nicPersoInfoUploadXml);
        if (dataExchangeDTO != null) {
            nicPersoInfoDTO = dataExchangeDTO.getNicPersoInfo();
        }
        return nicPersoInfoDTO;
    }

    private AbstractCardInfoDTO parseCardInfoDTO(String cardInfoUploadXml) throws JaxbXmlConvertorException {
        AbstractCardInfoDTO cardInfoDTO = null;
        PersoReferenceDataDTO dataExchangeDTO = (PersoReferenceDataDTO) util.unmarshal(cardInfoUploadXml);
        if (dataExchangeDTO != null) {
            if (dataExchangeDTO.getCardInfoDto() != null)
                cardInfoDTO = dataExchangeDTO.getCardInfoDto();
            else if (dataExchangeDTO.getPackageInfoDto() != null)
                cardInfoDTO = dataExchangeDTO.getPackageInfoDto();
            else if (dataExchangeDTO.getTransactionInfoDto() != null)
                cardInfoDTO = dataExchangeDTO.getTransactionInfoDto();
        }
        return cardInfoDTO;
    }

    private String getCardDataDTOXml(CardDataDTO cardDataDto) {
        String result = null;
        try {
            result = util.marshal(cardDataDto);
        } catch (JaxbXmlConvertorException jaxb) {
            logger.error("Error Occured when getCardDataDTOXml. caused:" + jaxb.getMessage(), jaxb);
        }
        return result;
    }

    private String getLogInfoXml(LogInfoDTO logInfo) {
        String result = null;
        try {
            TransLogInfoXmlConvertor convertor = new TransLogInfoXmlConvertor();
            result = convertor.marshal(logInfo);
        } catch (JaxbXmlConvertorException jaxb) {
            logger.error("Error Occured when getLogDataXML. caused:" + jaxb.getMessage(), jaxb);
        }
        return result;
    }

    private String getCurrentSiteCodeFromParameter() {
        String currentSiteCode = "NICDC";
        Parameters parameter = parametersDao.findById(new ParametersId(PARA_SCOPE_SYSTEM, PARA_NAME_SYSTEM_SITE_CODE));
        if (parameter != null) {
            currentSiteCode = (String) parameter.getParaValue();
        } else {
            logger.warn("No matching Parameter for {} , {} ", PARA_SCOPE_SYSTEM, PARA_NAME_SYSTEM_SITE_CODE);
        }
        return currentSiteCode;
    }

    private void saveTransactionLog(String transactionId, String transactionStage, String transactionStatus, Date startTime, Date endTime, String logInfo, String logData) {
        //08 Oct 2013 (chris) - start
        String officerId = SYSTEM_PERSO; //"SYSTEM";
        String wkstnId = SYSTEM_PERSO; //"SYSTEM";
        try {
            java.net.InetAddress localMachine = InetAddress.getLocalHost();
            wkstnId = localMachine.getHostName().toUpperCase();
        } catch (Exception e) {
        }
        //08 Oct 2013 (chris) - end
        NicTransactionLog transactionLog = new NicTransactionLog();
        transactionLog.setRefId(transactionId);
        transactionLog.setLogCreateTime(new Date());
        transactionLog.setTransactionStage(transactionStage);//TRANSACTION_STATE_PERSONALIZATION);
        transactionLog.setTransactionStatus(transactionStatus);
        transactionLog.setSiteCode(this.getCurrentSiteCodeFromParameter()); //get from 'CURRENT_SITE_CODE'
        transactionLog.setStartTime(startTime);
        transactionLog.setEndTime(endTime);
        transactionLog.setLogInfo(logInfo);
        transactionLog.setLogData(logData);
        //08 Oct 2013 (chris) - start
        transactionLog.setOfficerId(officerId);
        transactionLog.setWkstnId(wkstnId);
        //08 Oct 2013 (chris) - end
        synchronized (NicTransactionLog.class) {
            transactionLogDao.save(transactionLog);
        }
    }

    // NIC 1.0
    /**
     * @param nicTransaction
     * @throws PersoServiceException
     */
    @Override
    public void submitPersoData(NicTransaction nicTransaction, String submittingArn) throws PersoServiceException {
        String transactionId = null;
//        Date startTime = new Date();
//        String logData = null;
//        String transactionStatus = null;
        try {
            // Step 1: submit prepared data to perso
            if (nicTransaction != null) {
                transactionId = nicTransaction.getTransactionId();
                PersoData persoData = new PersoData();
                JobInfoType jobInfo = new JobInfoType();
                ApplicationDataType applicationData = new ApplicationDataType();
                NameType printName = new NameType();
                NameType fullname = new NameType();
                RemarkType remarks = new RemarkType();

                logger.debug("Submit perso data for transaction: " + transactionId);
                NicRegistrationData nicRegistrationData = nicTransaction.getNicRegistrationData();

                // Job Info
                if (StringUtils.isNotBlank(submittingArn)) {
                    jobInfo.setArn(submittingArn);
                } else {
                    jobInfo.setArn(transactionId);
                }
   
                jobInfo.setCaptureDate(nicTransaction.getDateOfApplication());
                jobInfo.setCollectionDate(nicTransaction.getEstDateOfCollection());

                //[cw] 2016 Apr 27 - validate max number of days range for Date of Collection.
                int maxNumDaysForPrinting = 60;
                Parameters paramMaxNumDaysForPrinting = parametersDao.findById(new ParametersId(PARA_SCOPE_PERSO, PARA_NAME_MAX_NUM_DAYS_FOR_PRINTING));
                if (paramMaxNumDaysForPrinting != null && StringUtils.isNumeric(paramMaxNumDaysForPrinting.getParaShortValue())) {
                	maxNumDaysForPrinting = Integer.parseInt(paramMaxNumDaysForPrinting.getParaShortValue());
                }
                if (jobInfo.getCollectionDate()!=null) {
                	Date systemDate = DateUtil.getSystemDate();
                	int diff = DateUtil.dateDiff(systemDate, jobInfo.getCollectionDate());
                	if (Math.abs(diff) > maxNumDaysForPrinting) {
                		logger.warn("[PersoService] submitPersoData - Invalid CollectionDate: "+jobInfo.getCollectionDate()+" override it to "+systemDate+" for transaction: " + transactionId);
                		jobInfo.setCollectionDate(systemDate);
                	}
                }
                //[cw] 2016 Apr 27 - validate max number of days range for Date of Collection - end
                
                if (StringUtils.isNotBlank(nicTransaction.getIssSiteCode())) {
                    SiteRepository site = siteRepositoryDao.findBySiteId(nicTransaction.getIssSiteCode());
                    if (site == null) {
                        throw new PersoServiceException("[PersoService] submitPersoData - Cannot find Site Repository with Site Code:" + nicTransaction.getIssSiteCode());
                    }

                    jobInfo.setCollectLocation(nicTransaction.getIssSiteCode() + "_" + site.getSiteName());
                }
                
                if (nicTransaction.getPriority()!=null) {
                    jobInfo.setPriority(nicTransaction.getPriority().intValue());
                } else {
                    jobInfo.setPriority(NicUploadJob.JOB_PRIORITY_NORMAL);
                }
                

				// Full Name
				if (StringUtils.isNotBlank(nicRegistrationData.getSurnameFull())) {
					fullname.setSurname(nicRegistrationData.getSurnameFull());
				} else {
					fullname.setSurname(StringUtils.EMPTY);
				}

				if (StringUtils.isNotBlank(nicRegistrationData.getFirstnameFull())) {
					fullname.setGivenName(nicRegistrationData.getFirstnameFull());
				} else {
					fullname.setGivenName(StringUtils.EMPTY);
				}

				if (StringUtils.isNotBlank(nicRegistrationData.getMiddlenameFull())) {
					fullname.setMiddleName(nicRegistrationData.getMiddlenameFull());
				} else {
					fullname.setMiddleName(StringUtils.EMPTY);
				}

                // Print Name
                if (StringUtils.isNotBlank(nicRegistrationData.getSurnameLine1())) {
                    printName.setSurname(nicRegistrationData.getSurnameLine1());
                } else if (StringUtils.isNotBlank(nicRegistrationData.getSurnameFull())){
                    printName.setSurname(nicRegistrationData.getSurnameFull());
                } else {
                	printName.setSurname(StringUtils.EMPTY);
                }
                
                if (StringUtils.isNotBlank(nicRegistrationData.getFirstnameLine1())) {
                	printName.setGivenName(nicRegistrationData.getFirstnameLine1());
                } else if (StringUtils.isNotBlank(nicRegistrationData.getFirstnameFull())){
                	printName.setGivenName(nicRegistrationData.getFirstnameFull());
                } else {
                	printName.setGivenName(StringUtils.EMPTY);
                }
                
                if (StringUtils.isNotBlank(nicRegistrationData.getMiddlenameLine1())) {
                	printName.setMiddleName(nicRegistrationData.getMiddlenameLine1());
                } else if (StringUtils.isNotBlank(nicRegistrationData.getMiddlenameFull())){
                	printName.setMiddleName(nicRegistrationData.getMiddlenameFull());
                } else {
                	printName.setMiddleName(StringUtils.EMPTY);
                }
                
                //[cw] 2016 Aug 17 - for name truncation
                boolean autoNameTruncationFlag = this.getParamAsBool(PARA_SCOPE_PERSO, PARA_NAME_APPLY_NAME_TRUNCATION, false);
                if (autoNameTruncationFlag) {
                	String surname = StringUtils.isNotBlank(nicRegistrationData.getSurnameLine1()) ? StringUtils.trim(nicRegistrationData.getSurnameLine1()) :StringUtils.EMPTY;
                	String givenName = StringUtils.isNotBlank(nicRegistrationData.getFirstnameLine1()) ? StringUtils.trim(nicRegistrationData.getFirstnameLine1()) :StringUtils.EMPTY;
                	String middleName = StringUtils.isNotBlank(nicRegistrationData.getMiddlenameLine1()) ? StringUtils.trim(nicRegistrationData.getMiddlenameLine1()) :StringUtils.EMPTY;
                	
                	String surnameChip = StringUtils.isNotBlank(nicRegistrationData.getSurnameLine2()) ? StringUtils.trim(nicRegistrationData.getSurnameLine2()) :StringUtils.EMPTY;
                	String givenNameChip = StringUtils.isNotBlank(nicRegistrationData.getFirstnameLine2()) ? StringUtils.trim(nicRegistrationData.getFirstnameLine2()) :StringUtils.EMPTY;
                	String middleNameChip = StringUtils.isNotBlank(nicRegistrationData.getMiddlenameLine2()) ? StringUtils.trim(nicRegistrationData.getMiddlenameLine2()) :StringUtils.EMPTY;
                	
                	if (Boolean.TRUE.equals(nicRegistrationData.getSurnameLenExceedFlag())) {
                		printName.setSurname(surname);
                	}
                	if (Boolean.TRUE.equals(nicRegistrationData.getFirstnameLenExceedFlag())) {
                		printName.setGivenName(givenName);
                	}
                	if (Boolean.TRUE.equals(nicRegistrationData.getMiddlenameLenExceedFlag())) {
                		printName.setMiddleName(middleName);
                	}
                	//[cw] 2016 Aug 19 - for name truncation on chip
            		fullname.setSurname(surnameChip);
            		fullname.setGivenName(givenNameChip);
            		fullname.setMiddleName(middleNameChip);
                }

                applicationData.setDocType(getPersoDataDocType(nicTransaction.getPassportType()));
                applicationData.setDuration(nicTransaction.getValidityPeriod().intValue());
                if (StringUtils.isNotBlank(nicTransaction.getIssuingAuthority())) {
                    SiteRepository site = siteRepositoryDao.findBySiteId(nicTransaction.getIssuingAuthority());
                    if (site == null) {
                        logger.warn("Submitting perso data, transaction [{}] doesn't have a valid site repository for issuing authority {}: " , new Object[] {
                        		transactionId, nicTransaction.getIssuingAuthority()
                        });
                        //throw new PersoServiceException("[PersoService] submitPersoData - Cannot find Site Repository with Site Code:" + nicTransaction.getIssuingAuthority());
                    } else {
                        applicationData.setAuthority(site.getAuthority());
                    }
                }
                applicationData.setFullName(fullname);
                applicationData.setPrintName(printName);

                // Get Nationality Description from System Code Value
                if (StringUtils.isNotBlank(nicRegistrationData.getNationality())) {
                    applicationData.setNat(nicRegistrationData.getNationality());
                    CodeValues nationalityCode = codesService.getCodeValueByIdName(CodesService.CODE_ID_NATIONALITY, nicRegistrationData.getNationality());
                    if (nationalityCode != null)
                        applicationData.setNatFull(nationalityCode.getCodeValueDesc());
                }

                applicationData.setDob(nicRegistrationData.getPrintDob());
                applicationData.setPob(nicRegistrationData.getPlaceOfBirth());
                applicationData.setGender(GenderType.valueOf(nicRegistrationData.getGender()));

                // Remarks
                // Khang (01-Apr-2016) : Add parameter for support remark
                boolean isRemarkSupported = this.getParamAsBool(PARA_SCOPE_PERSO, PARA_NAME_IS_REMARK_SUPPORTED, false); //[cw] 2016 Aug 17 - code refactoring

                if (isRemarkSupported) {
	                if (StringUtils.isBlank(nicRegistrationData.getPrintRemarks1())) {
	                    remarks.setLimitation(StringUtils.EMPTY);
	                } else {
	                    remarks.setLimitation(nicRegistrationData.getPrintRemarks1());
	                }

	                if (StringUtils.isBlank(nicRegistrationData.getOccupationDesc())) {
	                	 remarks.setPosition(StringUtils.EMPTY);
	                } else {
	                    remarks.setPosition(nicRegistrationData.getOccupationDesc());
	                }

	                if (StringUtils.isBlank(nicRegistrationData.getPrintRemarks2())) {
	                    remarks.setFullname(StringUtils.EMPTY);
	                } else {
	                    remarks.setFullname(nicRegistrationData.getPrintRemarks2());
	                }

	                if (StringUtils.isBlank(nicRegistrationData.getAliasName())) {
	                    remarks.setAka(StringUtils.EMPTY);
	                } else {
	                    remarks.setAka(nicRegistrationData.getAliasName());
	                }
                } else {
                	remarks.setAka(StringUtils.EMPTY);
                	remarks.setLimitation(StringUtils.EMPTY);
                	remarks.setFullname(StringUtils.EMPTY);
                	remarks.setPosition(StringUtils.EMPTY);
                }
                applicationData.setRemarks(remarks);

                List<NicTransactionAttachment> docPhotoList =
                    transactionDocumentDao.findNicTransactionAttachments(transactionId, NicTransactionAttachment.DOC_TYPE_PHOTO_CHIP, NicTransactionAttachment.DEFAULT_SERIAL_NO).getListModel();
                if (CollectionUtils.isNotEmpty(docPhotoList)) {
                    NicTransactionAttachment nicTransactionDocument = docPhotoList.get(0);
                    //applicationData.setPhoto(Base64.encodeBase64(nicTransactionDocument.getDocData()));
                    if (nicTransactionDocument.getDocData() != null) {
                        applicationData.setPhoto(nicTransactionDocument.getDocData());
                    } else {
                        throw new PersoServiceException("[PersoService] submitPersoData - TXN_ID: " + transactionId + " doesn't have Photo Chip document data");
                    }
                } else {
                	throw new PersoServiceException("[PersoService] submitPersoData - TXN_ID: " + transactionId + " doesn't have Photo Chip attachment");
                }

                List<NicTransactionAttachment> docSignatureList =
                    transactionDocumentDao.findNicTransactionAttachments(transactionId, NicTransactionAttachment.DOC_TYPE_SIGNATURE, NicTransactionAttachment.DEFAULT_SERIAL_NO).getListModel();
                boolean isDefaultSignature = false;
                if (CollectionUtils.isNotEmpty(docSignatureList)) {
                    NicTransactionAttachment nicTransactionDocument = docSignatureList.get(0);
                    //applicationData.setSignature(Base64.encodeBase64(nicTransactionDocument.getDocData()));
                    if (nicTransactionDocument.getDocData() != null && nicTransactionDocument.getDocData().length>0) {
                        applicationData.setSignature(nicTransactionDocument.getDocData());
                    } else {
                        isDefaultSignature = true;
                    }
                } else {
                    isDefaultSignature = true;
                }
                
                // Get default empty signature from parameter 
                if (isDefaultSignature) {
                    ParametersId paramSigId = new ParametersId(PARA_SCOPE_PERSO, PARA_NAME_DEFAULT_SIGNATURE_IMAGE);
                    Parameters paramSig = parametersDao.findById(paramSigId);
                    if (paramSig != null && StringUtils.isNotBlank(paramSig.getParaLobValue())) {
                        byte[] signature = Base64.decodeBase64(paramSig.getParaLobValue().getBytes());
                        applicationData.setSignature(signature);
                    } else {
                    	throw new PersoServiceException("[PersoService] submitPersoData - TXN_ID: " + transactionId + " doesn't have Signature attachment");
                    }
                }

                persoData.setJobInfo(jobInfo);
                persoData.setApplicationData(applicationData);

                // Step 2 : Prepare Perso XML Data
                JAXBContext context = JAXBContext.newInstance(PersoData.class);
                String persoXML = persoXmlConverter.marshal(persoData, context);
                if (StringUtils.isBlank(persoXML)) {
                    throw new PersoServiceException("Error marshalling obj to xml, obj: " + persoData);
                }

                try { 
					if (!fileTransferService.isConnected()) {
					    this.sftpServer = prepareSftpServerInfo();
					    fileTransferService.connect(sftpServer);
					}

//		            [Khang] 2016-02-17: Change to use rootDirectory from config
//		            String rootDirectory = fileTransferService.getCwd();
//		            if (StringUtils.isNotBlank(rootDirectory) && !rootDirectory.endsWith("/")) {
//		                rootDirectory = rootDirectory + "/";
//		            }
		            if (!rootDirectory.endsWith("/")) {
		                rootDirectory = rootDirectory + "/";
		            }
		            
		            String fullFilename = StringUtils.EMPTY;
		            if (StringUtils.isNotBlank(submittingArn)) {
		            	fullFilename = rootDirectory + dataDirectory + "/" + submittingArn + ".xml";
		            } else {
		            	fullFilename = rootDirectory + dataDirectory + "/" + nicTransaction.getTransactionId() + ".xml";
		            }
					
					fileTransferService.putFile(persoXML.getBytes("UTF-8"), fullFilename, true);
				} catch (Exception e1) {
					logger.error(e1.getMessage() + "\n" + e1.getStackTrace());
					throw new PersoServiceException(e1);
				} 
//                finally {
//		        	try {
//						fileTransferService.disconnect();
//					} catch (Exception e) {
//						logger.error("Exception in disconnect sftp:", e);
//					}
//				}

//[chris] commentted, no need to insert log
//                // Step 3 : To save the transaction log
//                try {
//                    if (StringUtils.isNotBlank(transactionId)) {
//                        this.saveTransactionLog(transactionId, TRANSACTION_STATUS_PERSO_REGISTER_COMPLETED, transactionStatus, startTime, new Date(), null, logData);
//                    }
//                } catch (Exception e) {
//                    logger.error("Exception in finally block (PersoService.submitPersoData)", e);
//                }
            }

        } catch (Exception e) {
//            transactionStatus = TRANSACTION_STATUS_PERSO_REGISTER_ERROR;
//            logData = MiscXmlConvertor.parseObjectToXml(e);
        	logger.error(e.getMessage() + "\n" + e.getStackTrace());
        	
            throw new PersoServiceException(e);
        }
    }
    
    //[cw] 2016 Aug 17 - for name truncation
    private boolean getParamAsBool(String scope, String name, boolean defaultValue) {
		boolean value = defaultValue;
		Parameters param = parametersDao.findById(new ParametersId(scope, name));
		if (param != null) {
			if (StringUtils.equalsIgnoreCase(param.getParaShortValue(), "Y") 
					|| StringUtils.equalsIgnoreCase(param.getParaShortValue(), "true")) {
				value = true;
			} else if (StringUtils.equalsIgnoreCase(param.getParaShortValue(), "N") 
					|| StringUtils.equalsIgnoreCase(param.getParaShortValue(), "false")) {
				value = false;
			}
		}
		return value;
	}

    @Override
    public void updatePersoStatus() throws PersoServiceException {
        try {

            if (!fileTransferService.isConnected()) {
                this.sftpServer = prepareSftpServerInfo();
                fileTransferService.connect(sftpServer);
            }
//            [Khang] 2016-02-17: Change to use rootDirectory from config
//            String rootDirectory = fileTransferService.getCwd();
//            if (StringUtils.isNotBlank(rootDirectory) && !rootDirectory.endsWith("/")) {
//                rootDirectory = rootDirectory + "/";
//            }
            if (!rootDirectory.endsWith("/")) {
                rootDirectory = rootDirectory + "/";
            }

            String persoStatusDirectory = rootDirectory + statusDirectory;
            logger.debug("[updatePersoStatus] - List all files under directory: " + persoStatusDirectory);
            List<SftpFile> statusFolders = this.listFiles(persoStatusDirectory);
            List<SftpFile> statusFolderList = new ArrayList<>();
            
            if (CollectionUtils.isNotEmpty(statusFolders)) {

            	// (20-May-2016) Khang: Hardcoded for priority of update status folder 
            	Map<String, SftpFile> statusFolderMap = new HashMap<>();
            	 for (SftpFile statusFolder : statusFolders) {
            		 statusFolderMap.put(statusFolder.getFilename(), statusFolder);
            	 }

            	 if (statusFolderMap.containsKey("imported")) {
            		 statusFolderList.add(statusFolderMap.get("imported"));
            	 }
            	 
            	 if (statusFolderMap.containsKey("pendingPerso")) {
            		 statusFolderList.add(statusFolderMap.get("pendingPerso"));
            	 }
            	 
            	 if (statusFolderMap.containsKey("printed")) {
            		 statusFolderList.add(statusFolderMap.get("printed"));
            	 }
            	 
            	 if (statusFolderMap.containsKey("error")) {
            		 statusFolderList.add(statusFolderMap.get("error"));
            	 }
            	 
            	 if (statusFolderMap.containsKey("qcCompleted")) {
            		 statusFolderList.add(statusFolderMap.get("qcCompleted"));
            	 }
            	 
            	 if (statusFolderMap.containsKey("rejected")) {
            		statusFolderList.add(statusFolderMap.get("rejected"));
            	 }

                for (SftpFile statusFolder : statusFolderList) {
                    logger.debug(statusFolder.getFilename());

                    List<SftpFile> statusFileList = this.listFiles(statusFolder.getAbsolutePath());
                    if (CollectionUtils.isNotEmpty(statusFileList)) {
                    	logger.debug("[updatePersoStatus] - Number of files: " + statusFileList.size());
                        for (SftpFile file : statusFileList) {
                            if (!file.canRead() || !file.getFilename().toUpperCase().endsWith(".XML")) {
                                continue;
                            }
                            logger.debug("[updatePersoStatus] - Reading: " + file.getAbsolutePath());
                            byte[] remoteFileBinary = fileTransferService.readRemoteFile(file.getAbsolutePath());
                            if (remoteFileBinary != null) {
                                String objXml = new String(remoteFileBinary, StandardCharsets.UTF_8);
                                PersoStatus persoObj = null;
								try {
									persoObj = (PersoStatus) persoXmlConverter.unmarshal(objXml);
								} catch (Exception e) {
									logger.error(e.getMessage());
									continue;
								}
                                if (persoObj != null) {
                                	boolean success = saveTransactionInfo(persoObj, file.getAbsolutePath());
                                    if (success) {
                                        String statusFolderPath = rootDirectory + archiveDirectory +"/"+ statusDirectory +"/"+ statusFolder.getFilename();
                                        boolean isFolderExist =fileTransferService.changeRemoteDirectory(statusFolderPath);
                                        if (isFolderExist == false) {
                            				boolean isCreated =fileTransferService.mkDir(statusFolderPath, true);
                            				if (!isCreated) {
                            					logger.warn("Cannot create backup folder: " + statusFolderPath);
                            					continue;
                            				}
                            			}

                                        String archiveFilePath = statusFolderPath + "/"+DateUtil.parseDate(new Date(), DateUtil.FORMAT_YYYYMMDD) ;
                                        boolean isExist =fileTransferService.changeRemoteDirectory(archiveFilePath);
                                        if (isExist == false) {
                            				boolean isCreated =fileTransferService.mkDir(archiveFilePath, true);
                            				if (!isCreated) {
                            					logger.warn("Cannot create backup folder: " + archiveFilePath);
                            					continue;
                            				}
                            			}

                            			fileTransferService.moveFile(file.getAbsolutePath(), archiveFilePath + "/" + file.getFilename(), true);
                                        logger.debug("Backed up: " + archiveFilePath + "/" + file.getFilename());
                                    } else {
                                    	logger.warn("Cannot save perso status from: " +  file.getAbsolutePath());
                                    }
                                }
                            }
                        }
                    }
                }
            }

        } catch (Exception e) {
            throw new PersoServiceException(e);
        } 
//        finally {
//        	try {
//				fileTransferService.disconnect();
//			} catch (Exception e) {
//				logger.error("Exception in disconnect sftp:", e);
//			}
//		}
    }

    @Override
    public void updateDispatchInfo() throws PersoServiceException {
        try {

            if (!fileTransferService.isConnected()) {
                this.sftpServer = prepareSftpServerInfo();
                fileTransferService.connect(sftpServer);
            }
            
//          [Khang] 2016-02-17: Change to use rootDirectory from config
//          String rootDirectory = fileTransferService.getCwd();
//          if (StringUtils.isNotBlank(rootDirectory) && !rootDirectory.endsWith("/")) {
//              rootDirectory = rootDirectory + "/";
//          }
          if (!rootDirectory.endsWith("/")) {
              rootDirectory = rootDirectory + "/";
          }
          
          String persoDispatchInfoDirectory = rootDirectory + dispatchDirectory;
            logger.debug("[updateDispatchInfo] - List all files under directory: " + persoDispatchInfoDirectory);
            List<SftpFile> dispatchFileList = this.listFiles(persoDispatchInfoDirectory);

            if (CollectionUtils.isNotEmpty(dispatchFileList)) {
            	logger.debug("[updateDispatchInfo] - Number of files: " + dispatchFileList.size());
                for (SftpFile file : dispatchFileList) {
                    if (!file.canRead() || !file.getFilename().toUpperCase().endsWith(".XML")) {
                        continue;
                    }
                    
                    logger.debug("[updateDispatchInfo] - Reading: " + file.getAbsolutePath());
                    byte[] remoteFileBinary = fileTransferService.readRemoteFile(file.getAbsolutePath());
                    if (remoteFileBinary != null) {
                        String objXml = new String(remoteFileBinary, StandardCharsets.UTF_8);
                        DispatchInfo dispatchObj = null;
						try {
							dispatchObj = (DispatchInfo) persoXmlConverter.unmarshal(objXml);
						} catch (Exception e) {
							logger.error(e.getMessage());
							continue;
						}
                        if (dispatchObj != null) {
                        	boolean success = saveDispatchInfo(dispatchObj, file.getAbsolutePath());
//                            if (success) {
//                                String archiveFilePath = rootDirectory + archiveDirectory + file.getAbsolutePath();
//                                fileTransferService.moveFile(file.getAbsolutePath(), archiveFilePath, true);
//                                logger.debug("Backed up: " + archiveFilePath);
//                            } else {
//                            	logger.warn("Cannot save dispatch info from: " +  file.getAbsolutePath());
//                            }
                            if (success) {
                                //String archiveFilePath = rootDirectory + archiveDirectory + file.getAbsolutePath();
                                String dispatchFolderPath = rootDirectory + archiveDirectory +"/"+ dispatchDirectory;
                                boolean isFolderExist =fileTransferService.changeRemoteDirectory(dispatchFolderPath);
                                if (isFolderExist == false) {
                    				boolean isCreated =fileTransferService.mkDir(dispatchFolderPath, true);
                    				if (!isCreated) {
                    					logger.warn("Cannot create backup folder: " + dispatchFolderPath);
                    					continue;
                    				}
                    			}

                                String archiveFilePath = dispatchFolderPath +"/"+DateUtil.parseDate(new Date(), DateUtil.FORMAT_YYYYMMDD) ;
                                boolean isExist =fileTransferService.changeRemoteDirectory(archiveFilePath);
                                if (isExist == false) {
                    				boolean isCreated =fileTransferService.mkDir(archiveFilePath, true);
                    				if (!isCreated) {
                    					logger.warn("Cannot create backup folder: " + archiveFilePath);
                    					continue;
                    				}
                    			}

                    			fileTransferService.moveFile(file.getAbsolutePath(), archiveFilePath + "/" + file.getFilename(), true);
                                logger.debug("Backed up: " + archiveFilePath + "/" + file.getFilename());
                            } else {
                            	logger.warn("Cannot save dispatch info from: " +  file.getAbsolutePath());
                            }
                        }
                    }
                }
            }

            
        } catch (Exception e) {
            throw new PersoServiceException(e);
        }
//        finally {
//        	try {
//				fileTransferService.disconnect();
//			} catch (Exception e) {
//				logger.error("Exception in disconnect sftp:", e);
//			}
//		}
    }

    /**
     * @param dispatchInfo
     * @return
     * @throws TransactionServiceException
     */
    private boolean saveDispatchInfo(DispatchInfo dispatchInfo, String xmlPath) throws TransactionServiceException {
        boolean status = false;
        try {
            DocumentType docType = dispatchInfo.getDocuments();
            List<DocumentInfoType> documentInfoTypes = docType.getDocuments();
            if (CollectionUtils.isNotEmpty(documentInfoTypes)) {
                for (DocumentInfoType documentInfoType : documentInfoTypes) {
                	String transactionId = documentInfoType.getArn();
                    if (StringUtils.isNotBlank(documentInfoType.getArn())) {
                    	// Check if ARN comes from Reprint Transaction
                    	if (transactionId.startsWith("RE-") || transactionId.startsWith("R-") ) {
            				NicTransactionReprint transactionRepprint = transactionReprintDao.findByRefArn(transactionId);
            				if (transactionRepprint != null) {
            					NicTransactionReprint latestTransactionReprint = transactionReprintDao.getLatestReprintByTransactionId(transactionRepprint.getId().getTransactionId());
            					if (StringUtils.equalsIgnoreCase(transactionRepprint.getId().getRefArn(), latestTransactionReprint.getId().getRefArn())) {
            						transactionId = transactionRepprint.getId().getTransactionId();
            						logger.info("Received dispatch info for transaction: "+ transactionRepprint.getId().getTransactionId() + ", RefARN: " + transactionRepprint.getId().getRefArn());
            					} else {
									logger.warn(
										"Received dispatch info for transaction: " + transactionRepprint.getId().getTransactionId() + ", RefARN: " + transactionRepprint.getId().getRefArn()
											+ " however it does not belong to latest reprint transaction. Ignoring this dispatch info.");
									// return true;
									continue;
            					}
            				}
                    	}
                        NicTransaction nicTransaction = transactionDao.getNicTransactionById(transactionId).getModel();
                        if (nicTransaction != null) {
                            DispatchQueueData dispatchQueueData = this.dispatchDataDao.findQueueData(transactionId,documentInfoType.getDocumentNum() );
                            String collectionLocation = null;
                            if (dispatchQueueData==null) {
                            	 dispatchQueueData = new DispatchQueueData();
                            	 dispatchQueueData.setBatchNumber(dispatchInfo.getBatchNum());
                                 dispatchQueueData.setDocumentType(dispatchInfo.getDocType().value());
                                 dispatchQueueData.setPriority( dispatchInfo.getPriority() );
                                 dispatchQueueData.setBatchSize( dispatchInfo.getBatchSize() );
                                 // Collection Location = SiteCode_SiteName
                                 collectionLocation = dispatchInfo.getCollectLocation();
                                 if (collectionLocation.contains("_") && !collectionLocation.startsWith("_")) {
                                     dispatchQueueData.setCollectionSite(collectionLocation.split("_")[0]);
                                 } else {
                                     dispatchQueueData.setCollectionSite(collectionLocation);
                                 }
                                 dispatchQueueData.setCollectionDate(dispatchInfo.getCollectionDate());
                                 dispatchQueueData.setStatus('A');
                                 dispatchQueueData.setTransactionId(transactionId);
                                 dispatchQueueData.setPassportNo(documentInfoType.getDocumentNum());
                                 dispatchDataDao.save(dispatchQueueData);                                
                            } else {
	                           	 dispatchQueueData.setBatchNumber(dispatchInfo.getBatchNum());
	                             dispatchQueueData.setDocumentType(dispatchInfo.getDocType().value());
	                             dispatchQueueData.setPriority( dispatchInfo.getPriority() );
	                             dispatchQueueData.setBatchSize( dispatchInfo.getBatchSize() );
	                             // Collection Location = SiteCode_SiteName
	                             collectionLocation = dispatchInfo.getCollectLocation();
	                             if (collectionLocation.contains("_") && !collectionLocation.startsWith("_")) {
	                                 dispatchQueueData.setCollectionSite(collectionLocation.split("_")[0]);
	                             } else {
	                                 dispatchQueueData.setCollectionSite(collectionLocation);
	                             }
	                             dispatchQueueData.setCollectionDate(dispatchInfo.getCollectionDate());
	                             dispatchQueueData.setStatus('A');
	                             dispatchDataDao.saveOrUpdate(dispatchQueueData);
                            }
                                                        
                            // Save data for auditing
                            try {
                                PersoDispatchInfo persoDispatchInfo = new PersoDispatchInfo();
                                persoDispatchInfo.setApplnRefNo(transactionId);
                                persoDispatchInfo.setBatchNumber(dispatchInfo.getBatchNum());
                                persoDispatchInfo.setBatchSize(dispatchInfo.getBatchSize());
                                persoDispatchInfo.setCollectionDate(dispatchInfo.getCollectionDate());
//                                persoDispatchInfo.setCollectionSite(dispatchInfo.getCollectLocation());
                                if (collectionLocation.contains("_") && !collectionLocation.startsWith("_")) {
                                	persoDispatchInfo.setCollectionSite(collectionLocation.split("_")[0]);
                                } else {
                                	persoDispatchInfo.setCollectionSite(collectionLocation);
                                }
                                persoDispatchInfo.setDocumentType(dispatchInfo.getDocType().value());
                                persoDispatchInfo.setPassportNo(documentInfoType.getDocumentNum());
                                persoDispatchInfo.setPriority(dispatchInfo.getPriority());
                                persoDispatchInfo.setSourceLocation(xmlPath);
                                persoInfoDao.createPersoDispatchInfo(persoDispatchInfo);
                            } catch (Exception e) {
                                logger.error("Error when save PersoDispatchInfo from: " + xmlPath + ", ex: " + e.getMessage());
                            }

                            status = true;
                        } else {
                            logger.warn("Warning due to cannot find corresponding transactionId=: " + transactionId + " for dispatch info");
                        	
                        }
                    }
                }
            }
        } catch (Exception e) {
            throw new TransactionServiceException(e);
        }
        return status;
    }

    /**
     * @param persoStatus
     * @return
     * @throws TransactionServiceException
     */
    private boolean saveTransactionInfo(PersoStatus persoStatus, String xmlPath) throws TransactionServiceException {
        
        String wkstnId = SYSTEM_PERSO; //"SYSTEM";
        boolean status = false;
        try {
            java.net.InetAddress localMachine = InetAddress.getLocalHost();
            wkstnId = localMachine.getHostName().toUpperCase();
        } catch (Exception e) {
        }

        try {
            if (persoStatus.getJobs() != null) {
                JobType jobType = persoStatus.getJobs();
                String transactionStatus = getTransactionStatus(persoStatus.getStatus());
                List<com.nec.asia.epp.perso.persoStatus.JobInfoType> jobInfos = jobType.getJobs();
                if (CollectionUtils.isNotEmpty(jobInfos)) {
                    for (com.nec.asia.epp.perso.persoStatus.JobInfoType jobInfo : jobInfos) {
                        if (StringUtils.isNotBlank(jobInfo.getArn())) {
                        	String transactionId = jobInfo.getArn();
                        	String passportNo = jobInfo.getDocumentNum();
                        	String officerId = SYSTEM_PERSO;
                        	// Check if ARN comes from Reprint Transaction
                        	if (transactionId.startsWith("RE-") || transactionId.startsWith("R-") ) { 
                				NicTransactionReprint transactionRepprint = transactionReprintDao.findByRefArn(transactionId);
                				if (transactionRepprint != null) {
                					NicTransactionReprint latestTransactionReprint = transactionReprintDao.getLatestReprintByTransactionId(transactionRepprint.getId().getTransactionId());
                					if (StringUtils.equalsIgnoreCase(transactionRepprint.getId().getRefArn(), latestTransactionReprint.getId().getRefArn())) {
                						transactionId = transactionRepprint.getId().getTransactionId();
                						logger.info("Received perso status for transaction: "+ transactionRepprint.getId().getTransactionId() + ", RefARN: " + transactionRepprint.getId().getRefArn());
                					} else {
										logger.warn(
											"Received perso status for transaction: " + transactionRepprint.getId().getTransactionId() + ", RefARN: " + transactionRepprint.getId().getRefArn()
												+ " however it does not belong to latest reprint transaction. Ignoring this status.");
										// return true;
										continue;
                					}
                				} else logger.debug("TransactionRepprint is empty with transactionId:" + transactionId);
                        	} else logger.debug("Transaction ID Without RE-:" + transactionId);
                            NicTransaction nicTransaction = transactionDao.getNicTransactionById(transactionId).getModel();
                            if (nicTransaction != null) {
                            	if (StringUtils.isNotBlank(jobInfo.getOperator())) {
                            		officerId = jobInfo.getOperator();
                            	}
                            	
                            	Date statusDate = (jobInfo.getStatusDate() == null)?persoStatus.getStatusDate():jobInfo.getStatusDate();
                                NicTransactionLog oldTransactionLog = transactionLogDao.getLatestTransactionLog(nicTransaction.getTransactionId(), NicTransactionService.TRANSACTION_STAGE_PERSO);
                                Date newDate = new Date();
                                NicTransactionLog transactionLog = new NicTransactionLog();
                                transactionLog.setRefId(nicTransaction.getTransactionId());
                                transactionLog.setLogCreateTime(newDate);
                                transactionLog.setTransactionStage(NicTransactionService.TRANSACTION_STAGE_PERSO);
                                transactionLog.setTransactionStatus(transactionStatus);
                                //transactionLog.setSiteCode(this.getCurrentSiteCodeFromParameter()); //get from 'CURRENT_SITE_CODE'
                                transactionLog.setSiteCode(jobInfo.getPrintingSite());
                                transactionLog.setStartTime(statusDate);
                                transactionLog.setEndTime(statusDate);
                                transactionLog.setLogInfo(jobInfo.getErrorCode() == null ? "" : jobInfo.getErrorCode().value());
                                transactionLog.setLogData(jobInfo.getErrorMessage());
                                transactionLog.setOfficerId(officerId);
                                transactionLog.setWkstnId(wkstnId);

                                transactionLogDao.save(transactionLog);

                                // Check if needed to update Transaction Status in EPP_TRANSACTION table to new status
                                if (oldTransactionLog == null || oldTransactionLog.getStartTime() == null ||
                                		(transactionLog.getStartTime() != null && 
                                		oldTransactionLog.getStartTime() != null && 
                                		DateUtil.isAfterDate(transactionLog.getStartTime(), oldTransactionLog.getStartTime()))) {
                                    transactionDao.updateStatusByTransactionId(nicTransaction.getTransactionId(), transactionStatus, officerId, wkstnId);
                                }

                                // Create Document Data in the case of Passport is printed
                                
                                //[chris] refactor transaction status
                                //if (NicTransactionService.TRANSACTION_STATUS_PERSO_PRINTED.equals(transactionStatus)) {
                                if (TransactionStatus.Printed.getKey().equals(transactionStatus)) {
                                    if (StringUtils.isNotBlank(passportNo) && StringUtils.isNotBlank(transactionId)) {
                                    	NicDocumentDataId id = new NicDocumentDataId(transactionId, passportNo);
                                        NicDocumentData documentData = documentDataDao.findById(id);
                                        if (documentData == null) {
                                            documentData = new NicDocumentData();
                                            documentData.setId(id);
                                            documentData.setCreateBy(officerId);
                                            documentData.setCreateDatetime(newDate);
                                            documentData.setCreateWkstnId(wkstnId);
                                        }

                                        documentData.setStatus(transactionStatus);
                                        documentData.setStatusUpdateTime(jobInfo.getStatusDate());
                                        documentData.setChipSerialNo(jobInfo.getChipSerialNum());
                                        documentData.setPrintingSite(jobInfo.getPrintingSite());
                                        documentData.setDateOfIssue(jobInfo.getDoi());
                                        documentData.setDateOfExpiry(jobInfo.getDoe());

                                        documentData.setUpdateBy(officerId);
                                        documentData.setUpdateDatetime(new Date());
                                        documentData.setUpdateWkstnId(wkstnId);

                                        documentDataDao.saveOrUpdate(documentData);
                                    }
                                } else if (TransactionStatus.QcCompleted.getKey().equals(transactionStatus)) {
                                	NicDocumentDataId docId = new  NicDocumentDataId(transactionId, passportNo);
                                	NicDocumentData documentData = documentDataDao.findById(docId);
                                    if (documentData != null) {
                                    	if (TransactionStatus.QcCompleted.getKey().equals(transactionStatus)
                                    		&& !TransactionStatus.Active.getKey().equals(documentData.getStatus())
                                    		&& !TransactionStatus.Cancelled.getKey().equals(documentData.getStatus())
                                    		&& !TransactionStatus.Dispatched.getKey().equals(documentData.getStatus())
                                    		&& !TransactionStatus.Received.getKey().equals(documentData.getStatus())) {
	                                    	documentData.setStatus(transactionStatus);
	                                        documentData.setStatusUpdateTime(statusDate);
	                                        documentData.setUpdateBy(officerId);
	                                        documentData.setUpdateDatetime(new Date());
	                                        documentData.setUpdateWkstnId(wkstnId);
	                                        
	                                        documentDataDao.saveOrUpdate(documentData);
                                    	}
                                    }
                                }
                                else if (TransactionStatus.Faulted.getKey().equals(transactionStatus) || TransactionStatus.Rejected.getKey().equals(transactionStatus) ) {
                                	if (StringUtils.isNotBlank(passportNo)) {
	                                	NicDocumentDataId docId = new  NicDocumentDataId(transactionId, passportNo);
	                                	NicDocumentData documentData = documentDataDao.findById(docId);
	                                	if (documentData != null) {
		                                	documentData.setStatus(transactionStatus);
		                                    documentData.setStatusUpdateTime(statusDate);
		                                    documentData.setUpdateBy(officerId);
		                                    documentData.setUpdateDatetime(new Date());
		                                    documentData.setUpdateWkstnId(wkstnId);
		                                    documentDataDao.saveOrUpdate(documentData);
	                                	}
                                	}
                                	
                                	if (TransactionStatus.Rejected.getKey().equals(transactionStatus)) {
                                		List<NicUploadJob> workflowJobList = uploadJobDao.findAllByTransactionId(transactionId).getListModel();
                                		if (CollectionUtils.isNotEmpty(workflowJobList)) {
                                			for (NicUploadJob nicUploadJob : workflowJobList) {
                                				NicRejectionData nicRejectionData = new NicRejectionData();
                                				nicRejectionData.setTransactionId(transactionId);
                                				nicRejectionData.setWorkflowJobId(nicUploadJob.getWorkflowJobId());
                                				nicRejectionData.setRejectBy(officerId);
                                				nicRejectionData.setRejectDatetime(statusDate);
                                				nicRejectionData.setRejectReason("REJECTED IN PERSO");
                                				nicRejectionData.setRejectRemarks(jobInfo.getErrorMessage());
                                				
                                				nicRejectionDataDao.saveOrUpdate(nicRejectionData);
                                			}
                                		}
                                	}
                                } 

                                // Save data for auditing
                                try {
                                    com.nec.asia.nic.comp.perso.domain.PersoStatus persoStatusData = new com.nec.asia.nic.comp.perso.domain.PersoStatus();
                                    persoStatusData.setApplnRefNo(transactionId);
                                    persoStatusData.setChipSerialNo(jobInfo.getChipSerialNum());
                                    persoStatusData.setCreateDatetime(newDate);
                                    persoStatusData.setDateOfExpiry(jobInfo.getDoe());
                                    persoStatusData.setDateOfIssue(jobInfo.getDoi());
                                    persoStatusData.setErrorCode(jobInfo.getErrorCode() == null ? "" : jobInfo.getErrorCode().value());
                                    persoStatusData.setErrorMessage(jobInfo.getErrorMessage());
                                    persoStatusData.setPassportNo(passportNo);
                                    persoStatusData.setPrintingSite(jobInfo.getPrintingSite());
                                    persoStatusData.setSourceLocation(xmlPath);
                                    persoStatusData.setStatusDate(statusDate);
                                    persoStatusData.setTransactionId(transactionId);
                                    persoStatusData.setTransactionStatus(transactionStatus);

                                    persoInfoDao.createPersoStatus(persoStatusData);
                                } catch (Exception e) {
                                    logger.error("Error when save PersoStatus from: " + xmlPath + ", ex: " + e.getMessage());
                                }
                                status = true;
                            } else {
                                logger.error("Warning due to cannot find corresponding transactionId=: " + transactionId + " for perso status info");
                            	
                            }
                            
                        }
                    }
                }
            }
        } catch (Exception e) {
            throw new TransactionServiceException(e);
        }
        return status;
    }

    /**
     * @param persoStatus
     * @return
     */
    private String getTransactionStatus(StatusType persoStatus) {
        String transactionStatus = StringUtils.EMPTY;
        switch (persoStatus) {
            case ERROR:
                transactionStatus = TransactionStatus.Faulted.getKey();
                break;
            case IMPORTED:
                transactionStatus = TransactionStatus.Imported.getKey();
                break;
            case PENDING_PERSO:
                transactionStatus =TransactionStatus.PendingPerso.getKey();
                break;
            case PRINTED:
                transactionStatus = TransactionStatus.Printed.getKey();
                break;
            case QC_COMPLETED:
                transactionStatus = TransactionStatus.QcCompleted.getKey();
                break;
            case REJECTED:
                transactionStatus = TransactionStatus.Rejected.getKey();
            default:
                break;
        }
        return transactionStatus;
    }

    /*
     * (non-Javadoc)
     * @see com.nec.asia.nic.comp.trans.service.PersoService#listFiles(java.lang.String)
     */
    @Override
    public List<SftpFile> listFiles(String directory) throws PersoServiceException {
        List<SftpFile> listFiles = null;
        try {
            if (!fileTransferService.isConnected()) {
                this.sftpServer = prepareSftpServerInfo();
                fileTransferService.connect(sftpServer);
            }
            
            if (StringUtils.isBlank(directory)) {
                listFiles = fileTransferService.ls();
            } else {
            	listFiles = fileTransferService.ls(directory);
            }
        } catch (FileNotFoundException e) {
        	logger.debug("Folder "+ directory +" is empty.");
        } catch (IOException e) {
            throw new PersoServiceException(e);
        } catch (Exception e) {
            throw new PersoServiceException(e);
        }
        if (CollectionUtils.isNotEmpty(listFiles)) {
            List<SftpFile> statusFolderList = removeEmptyItems(listFiles);
            return statusFolderList;
        }

        return listFiles;
    }
    
    @Override
    public Map<String, Integer> getFolderStatistics () throws PersoServiceException {
    	Map<String, Integer> folderMap = new HashMap<>();
    	try {

    		if (!fileTransferService.isConnected()) {
                this.sftpServer = prepareSftpServerInfo();
                fileTransferService.connect(sftpServer);
    		}
    		
 		   if (!rootDirectory.endsWith("/")) {
               rootDirectory = rootDirectory + "/";
           }
 		   
 		   //Check perso status directory
 		   String persoStatusDirectory = rootDirectory + statusDirectory;
   	
 		   List<SftpFile> folderList = this.listFiles(persoStatusDirectory);
 		 
 		   for (SftpFile folder : folderList) {   
 			  //System.out.println("Filename=" + folder.getFilename() + " absolute path=" + folder.getAbsolutePath());
 			   
 			  List<SftpFile> statusFileList = this.listFiles(folder.getAbsolutePath());
              logger.debug("Folder Name:{} - Number of files:{} ",folder.getFilename(), statusFileList.size());
              folderMap.put(folder.getFilename(), new Integer(statusFileList.size()) );
 			    
 		   }
 		   
 		   
 		   //Check perso data directory
  		   String persoDataDirectory = rootDirectory + dataDirectory;
 		   List<SftpFile> persoDataFileList = this.listFiles(persoDataDirectory);
           logger.debug("Folder Name:{} - Number of files:{} ", dataDirectory, persoDataFileList.size());
           folderMap.put(dataDirectory, new Integer(persoDataFileList.size()) ); 
 		   
 		   
 		   //check dispatch info directory
 		  String persoDispatchInfoDirectory = rootDirectory + dispatchDirectory;
		  List<SftpFile> dispatchFileList = this.listFiles(persoDispatchInfoDirectory);
          logger.debug("Folder Name:{} - Number of files:{} ", dispatchDirectory, dispatchFileList.size());
          folderMap.put(dispatchDirectory, new Integer(dispatchFileList.size()) ); 
    	
    	}catch (Exception ex) {
    		throw new PersoServiceException (ex);
    	}
    	
    	return folderMap;
    }

    private List<SftpFile> removeEmptyItems(List<SftpFile> listFiles) {
        List<SftpFile> sortedListFiles = new ArrayList<SftpFile>();
        for (SftpFile file : listFiles) {
            String fileName = file.getFilename().replace(".", StringUtils.EMPTY);
            if (StringUtils.isNotBlank(fileName)) {
                sortedListFiles.add(file);
            }
        }

        return sortedListFiles;
    }

    private SecureFtpServer prepareSftpServerInfo() throws PersoServiceException {
        SecureFtpServer sftpServer = new SecureFtpServer();
        ParametersId id = new ParametersId(PARA_SCOPE_SYSTEM, PARA_NAME_PERSO_SFTP_CONFIG);
        Parameters paraObj = parametersDao.findById(id);
        if (paraObj != null) {
            try {
                /*
                 * #Perso SFTP Properties
                 * SFTP.HOST=172.16.2.35
                 * SFTP.PORT=22
                 * SFTP.USERID=perso2
                 * SFTP.PASSKEY=Password1
                 * SFTP.DISPATCH.INFO.DIRECTORY=dispatchInfo
                 * SFTP.PERSO.DATA.DIRECTORY=persoData
                 * SFTP.PERSO.STATUS.DIRECTORY=persoStatus
                 * SFTP.PERSO.ARCHIVE.DIRECTORY=archive/persoStatus
                 * SFTP.PERSO.ROOT.DIRECTORY=/home/perso2
                 */

                Properties properties = parametersDao.readProperties(paraObj);
                if (properties != null) {
                    String host = properties.getProperty(SFTP_PERSO_HOST);
                    String port = properties.getProperty(SFTP_PERSO_PORT);
                    String userId = properties.getProperty(SFTP_PERSO_USERID);
                    String passkey = properties.getProperty(SFTP_PERSO_PASSKEY);
                    dispatchDirectory = properties.getProperty(SFTP_PERSO_DISPATCH_DIRECTORY);
                    dataDirectory = properties.getProperty(SFTP_PERSO_DATA_DIRECTORY);
                    statusDirectory = properties.getProperty(SFTP_PERSO_STATUS_DIRECTORY);
                    archiveDirectory = properties.getProperty(SFTP_PERSO_ARCHIVE_DIRECTORY);
                    rootDirectory = properties.getProperty(SFTP_PERSO_ROOT_DIRECTORY);

                    if (StringUtils.isBlank(host) || StringUtils.isBlank(port) || StringUtils.isBlank(userId) 
                        || StringUtils.isBlank(passkey) || StringUtils.isBlank(dispatchDirectory)
                        || StringUtils.isBlank(dataDirectory) || StringUtils.isBlank(statusDirectory) 
                        || StringUtils.isBlank(archiveDirectory) || StringUtils.isBlank(rootDirectory)) {
                        throw new PersoServiceException("Invalid SFTP configuration for Perso");
                    }

                    sftpServer.setHost(host);
                    sftpServer.setPort(Integer.valueOf(port));
                    sftpServer.setUserId(userId);
                    sftpServer.setPasskey(passkey);
                } else {
                    logger.warn("No matching Parameter for {} , {} ", PARA_SCOPE_SYSTEM, PARA_NAME_PERSO_SFTP_CONFIG);
                }
            } catch (Exception e) {
                throw new PersoServiceException(e);
            }
        }

        fileTransferService = new FileTransferSshImpl();
        return sftpServer;
    }

    private DocTypeType getPersoDataDocType(String docType) {
        DocTypeType persoDocType = null;
        PassportType documentType = PassportType.fromValue(docType);
        switch (documentType) {
            case DIPLOMATIC:
                persoDocType = DocTypeType.DIPLOMATIC;
                break;
            case REGULAR:
                persoDocType = DocTypeType.REGULAR;
                break;
            case OFFICIAL:
                persoDocType = DocTypeType.OFFICIAL;
                break;
            case MRCTDR:
                persoDocType = DocTypeType.MRCTDR;
                break;
            case MRCTDS:
                persoDocType = DocTypeType.MRCTDS;
                break;
            case HAJJ:
                persoDocType = DocTypeType.HAJJ;
                break;
            case MRP:
                persoDocType = DocTypeType.MRP;
                break;
            default:
                persoDocType = DocTypeType.REGULAR;
                break;
        }
        return persoDocType;
    }
    
    public void funcTrung(String transactionId, String persoXml) throws PersoServiceException{
		//trung thử in file tại đây
		try {

			if (!fileTransferService.isConnected()) {
			    this.sftpServer = prepareSftpServerInfo();
			    fileTransferService.connect(sftpServer);
			}

            if (!rootDirectory.endsWith("/")) {
                rootDirectory = rootDirectory + "/";
            }
            
            String fullFilename = StringUtils.EMPTY;
            fullFilename = rootDirectory + dataDirectory + "/" + "TRUNG-" + transactionId + ".xml";

			
			fileTransferService.putFile(persoXml.getBytes("UTF-8"), fullFilename, true);
		} catch (Exception e1) {
			logger.error("LOI DO THANG NAY - " + e1.getMessage() + "\n" + e1.getStackTrace());
			throw new PersoServiceException(e1);
		} 
		//=====TRUNG
    }
    @Override
    public void submitPersoDataNew(NicTransaction nicTransaction, String submittingArn, ChipInfo chipinfo, Date dateOfIssue, Date dateOfExpiry) throws PersoServiceException {
    }
}
