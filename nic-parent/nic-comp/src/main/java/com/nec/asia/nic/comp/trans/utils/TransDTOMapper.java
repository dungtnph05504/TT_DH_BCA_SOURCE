package com.nec.asia.nic.comp.trans.utils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.nec.asia.nic.comp.job.dto.LogInfoDTO;
import com.nec.asia.nic.comp.trans.domain.NicDocumentData;
//import com.nec.asia.nic.comp.trans.domain.NicIssuanceData;
//import com.nec.asia.nic.comp.trans.domain.NicMain;
import com.nec.asia.nic.comp.trans.domain.NicRegistrationData;
import com.nec.asia.nic.comp.trans.domain.NicRejectionData;
import com.nec.asia.nic.comp.trans.domain.NicTransaction;
import com.nec.asia.nic.comp.trans.domain.NicTransactionAttachment;
import com.nec.asia.nic.comp.trans.domain.NicTransactionLog;
import com.nec.asia.nic.comp.trans.domain.NicTransactionPayment;
import com.nec.asia.nic.comp.trans.domain.NicTransactionPaymentDetail;
import com.nec.asia.nic.comp.trans.domain.type.TransactionStatus;
//import com.nec.asia.nic.comp.trans.domain.NicTransactionPayment;
import com.nec.asia.nic.dx.common.LogInfo;
import com.nec.asia.nic.dx.trans.FacialImage;
import com.nec.asia.nic.dx.trans.FacialInfo;
import com.nec.asia.nic.dx.trans.Fingerprint;
import com.nec.asia.nic.dx.trans.FingerprintInfo;
import com.nec.asia.nic.dx.trans.IssuanceData;
import com.nec.asia.nic.dx.trans.MainTransaction;
import com.nec.asia.nic.dx.trans.PaymentDetail;
import com.nec.asia.nic.dx.trans.PaymentInfo;
import com.nec.asia.nic.dx.trans.PersonDetail;
import com.nec.asia.nic.dx.trans.ReferenceDocument;
import com.nec.asia.nic.dx.trans.RegistrationData;
import com.nec.asia.nic.dx.trans.RejectionData;
import com.nec.asia.nic.dx.trans.Signature;
import com.nec.asia.nic.dx.trans.SignatureInfo;
import com.nec.asia.nic.dx.trans.TransactionLog;
import com.nec.asia.nic.util.TransLogInfoXmlConvertor;
import com.nec.asia.nic.utils.BaseDTOMapper;
import com.nec.asia.nic.utils.DateUtil;
import com.nec.asia.nic.utils.ImageUtil;
/* 
 * Modification History:
 * 
 * 19 Jul 2013 (chris): fix bug in parseRegistrationDataDBO(), to set FpVerifyScore Field. 
 * 02 Aug 2013 (chris): add fields optionSurname, AddressLine5, AddressLine6, PreferredMailingAddress, OverseasAddress, OverseasCountry.
 * 12 Aug 2013 (chris): add reference documents. 
 * 13 Aug 2013 (chris): fix reference documents not showing.
 * 16 Aug 2013 (chris): fix printdob from string8 to string11.
 * 04 Sep 2013 (chris): to add nicHistory and nicInfo 
 * 06 Sep 2013 (chris): to handle approxDOB as "  -  -YYYY" format.
 * 12 Sep 2013 (chris): to fix approxDOB length issue.
 * 14 Sep 2013 (chris): add packageSequence, packageDate, dispatchID in IssuanceData
 * 17 Sep 2013 (chris): to set ConversionFlag in RegistrationData
 * 28 Sep 2013 (jp)   :	add estCollectionDate in RegistrationData
 * 30 Sep 2013 (chris): to fix expressFlag no parsing
 * 07 Oct 2013 (chris): to handle blank in signature in parseNicTransDocDBOForSignature
 * 26 Nov 2013 (chris): to fix missing cardStatus in parseNicInfo()
 * 03 Dec 2013 (chris): to change minutia template format constants.
 * 05 Dec 2013 (chris): to validate and standardize fingerprint info (indicator, quality, verify score, etc)
 * 12 Dec 2013 (chris): add getReferenceTransactionId()
 * 16 Dec 2013 (chris): to fix null when compare Date for cardStatusChangeTime
 * 18 Dec 2013 (chris): add RejectionData.RejectionType
 * 06 Jan 2014 (chris): to swap firstname, surname in relationshipDTO
 * 16 Jan 2014 (chris): add NicTransactionLog.nin
 * 24 Jan 2014 (chris): to handle null in checkFpData()
 * 20 Mar 2014 (chris): to filter issuance data if ccn is empty.
 * 09 Apr 2014 (chris): to handle null nicChangeStatusLogList in parseNicInfo()
 * 23 May 2014 (chris): add method parseLogInfoDTO(), parseLogInfoXml()
 * 08 Jun 2014 (chris): to fix wrong month logic in printDOB. 
 * 05 Jan 2016 (chris): update for EPP webservice upload and retrieve.
 * 24 Feb 2016 (chris): set empty as default for names it is null.
 * 03 Jun 2016 (chris): add new field personDetail.aliasname
 * 15 Jun 2017 (chris): add mnu binary and mnu format
 */
@Component
public class TransDTOMapper extends BaseDTOMapper {
	private Logger logger = LoggerFactory.getLogger(TransDTOMapper.class);
	
	public static final String DEFAULT_SERIAL_NO 			= NicTransactionAttachment.DEFAULT_SERIAL_NO; //"01"
	private static final String TRANSACTION_STATUS_REJECT   = TransactionStatus.Rejected.getKey();
	public static final String DOC_TYPE_PREFIX_FINGERPRINT = NicTransactionAttachment.DOC_TYPE_FINGERPRINT;
	public static final String DOC_TYPE_PREFIX_MINUTIAETEMPLATE = NicTransactionAttachment.DOC_TYPE_MINUTIA;	
	public static final String DOC_TYPE_PREFIX_PHOTO = NicTransactionAttachment.DOC_TYPE_PREFIX_PHOTO;
	public static final String DOC_TYPE_PREFIX_THUMBNAIL = NicTransactionAttachment.DOC_TYPE_PREFIX_THUMBNAIL;
	public static final String DOC_TYPE_PREFIX_SIGNATURE = NicTransactionAttachment.DOC_TYPE_PREFIX_SIGN;
	public static final String DOC_TYPE_PREFIX_SCANNEDDOC = NicTransactionAttachment.DOC_TYPE_PREFIX_SCANNEDDOC;
	
	public static final String DOC_TYPE_FINGERPRINT 		= NicTransactionAttachment.DOC_TYPE_FINGERPRINT;
	public static final String DOC_TYPE_MINUTIAETEMPLATE 	= NicTransactionAttachment.DOC_TYPE_MINUTIA;
	public static final String DOC_TYPE_TPL 				= NicTransactionAttachment.DOC_TYPE_TPL;
	//SubTypes
	public final static String DOC_TYPE_PHOTO_CAPTURE 		= NicTransactionAttachment.DOC_TYPE_PHOTO_CAPTURE;
	public final static String DOC_TYPE_THUMBNAIL_CAPTURE 	= NicTransactionAttachment.DOC_TYPE_THUMBNAIL_CAPTURE;
	public final static String DOC_TYPE_PHOTO_GREY	 		= NicTransactionAttachment.DOC_TYPE_PHOTO_GREY;
//	public final static String DOC_TYPE_THUMBNAIL_GREY 		= NicTransactionAttachment.DOC_TYPE_THUMBNAIL_GREY;
	public final static String DOC_TYPE_PHOTO_CHIP 			= NicTransactionAttachment.DOC_TYPE_PHOTO_CHIP;
//	public final static String DOC_TYPE_THUMBNAIL_CHIP 		= NicTransactionAttachment.DOC_TYPE_THUMBNAIL_CHIP;
	public final static String DOC_TYPE_PHOTO_CLI 			= NicTransactionAttachment.DOC_TYPE_PHOTO_CLI;
	public final static String DOC_TYPE_PERSO 				= NicTransactionAttachment.DOC_TYPE_PERSO;
	public final static String DOC_TYPE_SIGNATURE 			= NicTransactionAttachment.DOC_TYPE_SIGNATURE;
	public final static String DOC_TYPE_SIGN_FP				= NicTransactionAttachment.DOC_TYPE_SIGN_FP;
	public final static String DOC_TYPE_USER_DECLARATION 	= NicTransactionAttachment.DOC_TYPE_USER_DECLARATION;
	public final static String DOC_TYPE_SC_BIRTH_CERT		= NicTransactionAttachment.DOC_TYPE_SC_BIRTH_CERT;
	public final static String DOC_TYPE_SC_MAR_CERT 		= NicTransactionAttachment.DOC_TYPE_SC_MAR_CERT;
	public final static String DOC_TYPE_SC_ADDR_PROOF		= NicTransactionAttachment.DOC_TYPE_SC_ADDR_PROOF;
	
	public final static String DOC_TYPE_XML_ENCODING 		= NicTransactionAttachment.DOC_TYPE_ENCODING;
	public final static String DOC_TYPE_XML_PERSO			= NicTransactionAttachment.DOC_TYPE_PERSO;
	
	public static final String MNU_FORMAT_SC37_CBEFF 	= "SC37_CBEFF";
	public static final String MNU_FORMAT_PC2 			= "PC2";
	public static final String MNU_FORMAT_OTHER 		= "Other";
	public static final String DEFAULT_MINUTIAE_FORMAT 	= MNU_FORMAT_SC37_CBEFF;//"MINU_CBEFF_SC37_N464";//PC2, ISO_SC37
	
	//NIC_MAIN
	//public static final int DEFAULT_GEN_NO 		= NicMain.DEFAULT_GEN_NO;
	//public static final int DEFAULT_UPDATE_NO 	= NicMain.DEFAULT_UPDATE_NO;
	
	public static final String SIGNATURE_INDICATOR_SIGN = "S";
	//public static final String SIGNATURE_INDICATOR_THUMB = "T";
	//public static final String SIGNATURE_INDICATOR_BLANK = "B";
	
	private final static int   THUMBNAIL_FACE_WIDTH_DEFAULT   = 240; //120
	private final static int   THUMBNAIL_FACE_HEIGHT_DEFAULT  = 320; //160
	private final static float THUMBNAIL_FACE_QUALITY_DEFAULT = 0.5f;
	
	private TransLogInfoXmlConvertor logInfoXmlConvertor = new TransLogInfoXmlConvertor();
	
	//Transaction DTO to Data DBO
	public NicTransaction parseNicTransactionDBO(MainTransaction transactionDTO) {
		NicTransaction nicTransactionDBO = new NicTransaction();
		nicTransactionDBO.setTransactionId(transactionDTO.getTransactionID());
//		nicTransactionDBO.setQueueNumber(transactionDTO.getQueueNumber());
		nicTransactionDBO.setApplnRefNo(transactionDTO.getApplnRefNo());
		nicTransactionDBO.setNin(transactionDTO.getNIN());
		nicTransactionDBO.setDateOfApplication(transactionDTO.getDateOfApplication());
		nicTransactionDBO.setRegSiteCode(transactionDTO.getRegSiteCode());
		nicTransactionDBO.setIssSiteCode(transactionDTO.getIssSiteCode());
		nicTransactionDBO.setTransactionType(transactionDTO.getTransactionType());
//		nicTransactionDBO.setTransactionSubtype(transactionDTO.getTransactionSubtype());
		nicTransactionDBO.setTransactionStatus(transactionDTO.getTransactionStatus());	
		
		//additional fields
		nicTransactionDBO.setEstDateOfCollection(transactionDTO.getEstDateOfCollection());
		nicTransactionDBO.setPassportType(transactionDTO.getPassportType());
		nicTransactionDBO.setIssuingAuthority(transactionDTO.getIssuingAuthority());
		nicTransactionDBO.setValidityPeriod(transactionDTO.getValidatyPeriod());
		nicTransactionDBO.setPriority(transactionDTO.getPriority());
		nicTransactionDBO.setPrevDateOfIssue(transactionDTO.getPrevDateOfIssue());
		nicTransactionDBO.setPrevPassportNo(transactionDTO.getPrevPassportNo());
		
		nicTransactionDBO.setCreateBy(transactionDTO.getCreateBy());
		nicTransactionDBO.setCreateDatetime(transactionDTO.getCreateDateTime());
		nicTransactionDBO.setCreateWkstnId(transactionDTO.getCreateWkstnID());
		nicTransactionDBO.setUpdateBy(transactionDTO.getUpdateBy());
		nicTransactionDBO.setUpdateDatetime(transactionDTO.getUpdateDateTime());
		nicTransactionDBO.setUpdateWkstnId(transactionDTO.getUpdateWkstnID());
		nicTransactionDBO.setInfoPerson(transactionDTO.getInfoPerson());
		nicTransactionDBO.setRecieptNo(transactionDTO.getRecieptNo());
		
		
		NicRegistrationData nicRegDataDBO = this.parseRegistrationDataDBO(transactionDTO.getTransactionID(), null, transactionDTO.getRegistrationData());
		nicTransactionDBO.setNicRegistrationData(nicRegDataDBO);
		//photo
		List<NicTransactionAttachment> nicFacialDocDBOList = this.parseNicTransDocDBOForFacial(transactionDTO.getTransactionID(), transactionDTO.getRegistrationData().getFacialInfo(), nicTransactionDBO);
		nicTransactionDBO.getNicTransactionAttachments().addAll(nicFacialDocDBOList);
		//finger print wsq and tpl
		List<NicTransactionAttachment> nicFPDocDBOList = this.parseNicTransDocDBOForFingerprint(transactionDTO.getTransactionID(), transactionDTO.getRegistrationData().getFingerprintInfo(), nicTransactionDBO);
		nicTransactionDBO.getNicTransactionAttachments().addAll(nicFPDocDBOList);
		//signature
		NicTransactionAttachment nicSignDocDBO = this.parseNicTransDocDBOForSignature(transactionDTO.getTransactionID(), transactionDTO.getRegistrationData().getSignatureInfo(), nicTransactionDBO);
		if (nicSignDocDBO!=null) {
			nicTransactionDBO.getNicTransactionAttachments().add(nicSignDocDBO);
		}
		//reference document
		for (ReferenceDocument refDocDTO : transactionDTO.getRegistrationData().getReferenceDocuments()) {
			NicTransactionAttachment nicRefDocDBO = this.parseNicTransDocDBOForReferenceDocument(transactionDTO.getTransactionID(), refDocDTO, nicTransactionDBO);
			if (nicRefDocDBO!=null)
				nicTransactionDBO.getNicTransactionAttachments().add(nicRefDocDBO);
		}
		
		//payment
		NicTransactionPayment nicTransPaymentDBO = this.parseNicTransPaymentDBO(transactionDTO.getTransactionID(), transactionDTO.getRegistrationData().getPaymentInfo());
		nicTransactionDBO.setNicTransactionPayment(nicTransPaymentDBO);
		
		//nicTransactionDBO.setpackageID(transactionDTO.getPackageID());
		if(StringUtils.isNotEmpty(transactionDTO.getIsPostOffice())){
			if(transactionDTO.getIsPostOffice().equals("Y"))
				nicTransactionDBO.setIsPostOffice(true);
			else
				nicTransactionDBO.setIsPostOffice(false);
		}
		else
			nicTransactionDBO.setIsPostOffice(false);

		return nicTransactionDBO;
	}
	
	//Transaction DBO to Data DTO
	public MainTransaction parseMainTransactionDTO(NicTransaction nicTransactionDBO) {
		MainTransaction transactionDTO = new MainTransaction();
		transactionDTO.setTransactionID(nicTransactionDBO.getTransactionId());
//		transactionDTO.setQueueNumber(nicTransactionDBO.getQueueNumber());
		transactionDTO.setApplnRefNo(nicTransactionDBO.getApplnRefNo());
		transactionDTO.setNIN(nicTransactionDBO.getNin());
		transactionDTO.setDateOfApplication(nicTransactionDBO.getDateOfApplication());
		transactionDTO.setRegSiteCode(nicTransactionDBO.getRegSiteCode());
		transactionDTO.setIssSiteCode(nicTransactionDBO.getIssSiteCode());
		transactionDTO.setTransactionType(nicTransactionDBO.getTransactionType());
//		transactionDTO.setTransactionSubtype(nicTransactionDBO.getTransactionSubtype());
		transactionDTO.setTransactionStatus(nicTransactionDBO.getTransactionStatus());	
		
		//additional fields
		transactionDTO.setEstDateOfCollection(nicTransactionDBO.getEstDateOfCollection());
		transactionDTO.setPassportType(nicTransactionDBO.getPassportType());
		transactionDTO.setIssuingAuthority(nicTransactionDBO.getIssuingAuthority());
		transactionDTO.setValidatyPeriod(nicTransactionDBO.getValidityPeriod());
		transactionDTO.setPriority(nicTransactionDBO.getPriority());
		
		transactionDTO.setCreateBy(nicTransactionDBO.getCreateBy());
		transactionDTO.setCreateDateTime(nicTransactionDBO.getCreateDatetime());
		transactionDTO.setCreateWkstnID(nicTransactionDBO.getCreateWkstnId());
		transactionDTO.setUpdateBy(nicTransactionDBO.getUpdateBy());
		transactionDTO.setUpdateDateTime(nicTransactionDBO.getUpdateDatetime());
		transactionDTO.setUpdateWkstnID(nicTransactionDBO.getUpdateWkstnId());
		
		//registrationDataDTO
//		RegistrationData registrationDataDTO = this.parseRegistrationDataDTO(nicTransactionDBO.getNicRegistrationData(), nicTransactionDBO.getNicTransactionAttachments(), nicTransactionDBO.getNicTransactionPayment());
		RegistrationData registrationDataDTO = this.parseRegistrationDataDTO(nicTransactionDBO.getNicRegistrationData(), nicTransactionDBO.getNicTransactionAttachments());
		transactionDTO.setRegistrationData(registrationDataDTO);
		
		//issuanceDataDTO
		if (CollectionUtils.isNotEmpty(nicTransactionDBO.getNicDocumentDatas())) {
			NicDocumentData documentDataDBO = null;
			//to choose latest IssData.
			for (NicDocumentData temp : nicTransactionDBO.getNicDocumentDatas()) {
				if (documentDataDBO==null) {
					documentDataDBO = temp;
				} else {
					Date chosenDBODate = (documentDataDBO.getUpdateDatetime()!=null? documentDataDBO.getUpdateDatetime(): documentDataDBO.getCreateDatetime());
					Date tempDate = (temp.getUpdateDatetime()!=null? temp.getUpdateDatetime(): temp.getCreateDatetime());
					if (tempDate!=null && chosenDBODate!=null 
							&& DateUtil.isAfterDate(tempDate, chosenDBODate)) {
						documentDataDBO = temp;
					}
				}
			}
			IssuanceData issuanceDataDTO = this.parseIssuanceDataDTO(documentDataDBO);
			transactionDTO.setIssuanceData(issuanceDataDTO);
		}

		return transactionDTO;
	}
	
	//Registration Data DTO to Data DBO
	public NicRegistrationData parseRegistrationDataDBO(String transactionId, String nin, RegistrationData regDataDTO) {
		NicRegistrationData nicRegDataDBO = null;
		if (regDataDTO!=null) {
			nicRegDataDBO = new NicRegistrationData();
			//get RegistrationData
			nicRegDataDBO.setTransactionId(transactionId);
//			nicRegDataDBO.setNicTransaction(new NicTransaction());
//			nicRegDataDBO.getNicTransaction().setTransactionId(transactionId);
//			nicRegDataDBO.setNin(nin);
			//nicRegDataDBO.setNicUploadJobId(Long nicUploadJobId);		
			//nicRegDataDBO.setNicId(Long nicId);
			//nicRegDataDBO.setUploadFlag(String uploadFlag);
			//nicRegDataDBO.setUploadTime(Date uploadTime);
			//nicRegDataDBO.setNicJobCompleteFlag(String nicJobCompleteFlag);
			
//			nicRegDataDBO.setRegCompleteFlag(this.convertFlagToBoolean(regDataDTO.getRegistrationCompleteFlag()));
//			nicRegDataDBO.setRegOfficerId(regDataDTO.getRegistrationOfficerID());
//			nicRegDataDBO.setRegCompleteTime(regDataDTO.getRegistrationCompleteTime());
//			nicRegDataDBO.setVerCompleteFlag(this.convertFlagToBoolean(regDataDTO.getVerificationCompleteFlag()));
//			nicRegDataDBO.setVerOfficerId(regDataDTO.getVerificationOfficerID());
//			nicRegDataDBO.setVerCompleteTime(regDataDTO.getVerificationCompleteTime());
//			nicRegDataDBO.setPayCompleteFlag(this.convertFlagToBoolean(regDataDTO.getPaymentCompleteFlag()));
//			nicRegDataDBO.setPayOfficerId(regDataDTO.getPaymentOfficerID());
//			nicRegDataDBO.setPayCompleteTime(regDataDTO.getPaymentCompleteTime());
//			nicRegDataDBO.setRegTransactionCompleteFlag(this.convertFlagToBoolean(regDataDTO.getTransactionCompletedFlag()));
//			nicRegDataDBO.setFullAmputatedFlag(this.convertFlagToBoolean(regDataDTO.getFullAmputatedFlag()));
//			nicRegDataDBO.setPartialAmputatedFlag(this.convertFlagToBoolean(regDataDTO.getPartialAmputatedFlag()));
//			nicRegDataDBO.setSupervisorDecision(regDataDTO.getSupervisorDecision());
			nicRegDataDBO.setCreateBy(regDataDTO.getCreateBy());
			nicRegDataDBO.setCreateWkstnId(regDataDTO.getCreateWkstnID());
			nicRegDataDBO.setCreateDatetime(regDataDTO.getCreateDateTime());
			nicRegDataDBO.setUpdateBy(regDataDTO.getUpdateBy());
			nicRegDataDBO.setUpdateWkstnId(regDataDTO.getUpdateWkstnID());
			nicRegDataDBO.setUpdateDatetime(regDataDTO.getUpdateDateTime());
	
			//get PersonDetail
			PersonDetail personDetailDTO = regDataDTO.getPersonDetail();
			if (StringUtils.isNotBlank(personDetailDTO.getSurnameFull()))
				nicRegDataDBO.setSurnameFull(personDetailDTO.getSurnameFull());
			else
				nicRegDataDBO.setSurnameFull(StringUtils.EMPTY);
			nicRegDataDBO.setSurnameLine1(personDetailDTO.getSurnameLine1());
			nicRegDataDBO.setSurnameLine2(personDetailDTO.getSurnameLine2());
			nicRegDataDBO.setSurnameLenExceedFlag(this.convertFlagToBoolean(personDetailDTO.getSurnameLenExceedFlag()));
	
			if (StringUtils.isNotBlank(personDetailDTO.getFirstnameFull()))
				nicRegDataDBO.setFirstnameFull(personDetailDTO.getFirstnameFull());
			else
				nicRegDataDBO.setFirstnameFull(StringUtils.EMPTY);
			nicRegDataDBO.setFirstnameLine1(personDetailDTO.getFirstnameLine1());
			nicRegDataDBO.setFirstnameLine2(personDetailDTO.getFirstnameLine2());
			nicRegDataDBO.setFirstnameLenExceedFlag(this.convertFlagToBoolean(personDetailDTO.getFirstnameLenExceedFlag()));
	
			if (StringUtils.isNotBlank(personDetailDTO.getMiddlenameFull()))
				nicRegDataDBO.setMiddlenameFull(personDetailDTO.getMiddlenameFull());
			else
				nicRegDataDBO.setMiddlenameFull(StringUtils.EMPTY);
			nicRegDataDBO.setMiddlenameLine1(personDetailDTO.getMiddlenameLine1());
			nicRegDataDBO.setMiddlenameLine2(personDetailDTO.getMiddlenameLine2());
			nicRegDataDBO.setMiddlenameLenExceedFlag(this.convertFlagToBoolean(personDetailDTO.getMiddlenameLenExceedFlag()));
//			//optionSurname
//			nicRegDataDBO.setOptionSurname(personDetailDTO.getOptionSurname());
			
			nicRegDataDBO.setDateOfBirth(personDetailDTO.getDateOfBirth());
			nicRegDataDBO.setApproxDob(personDetailDTO.getApproxDOB());
			nicRegDataDBO.setPrintDob(personDetailDTO.getPrintDOB());
			//to set PrintDob if it is null
			if (StringUtils.isBlank(nicRegDataDBO.getPrintDob())) {
				if (nicRegDataDBO.getDateOfBirth()!=null) {
					//nicRegDataDBO.setPrintDob(this.convertDateToString11(nicRegDataDBO.getDateOfBirth()));
					//[EPP] assume approxDob is in the format: YYYY-MM-DD
					String arjoPrintDob = DateUtil.parseDate(nicRegDataDBO.getDateOfBirth(), DateUtil.FORMAT_YYYYMMDD);
					nicRegDataDBO.setPrintDob(arjoPrintDob);
				} else if (StringUtils.isNotBlank(nicRegDataDBO.getApproxDob()) 
						&& StringUtils.length(nicRegDataDBO.getApproxDob())==10) {
					//[NIC] assume approxDob is in the format of DG's Date Sharing format: DD-MM-YYYY
					//String dayPart = StringUtils.substring(nicRegDataDBO.getApproxDob(), 0, 2);
					//String monthPart = StringUtils.substring(nicRegDataDBO.getApproxDob(), 3, 5);
					//String yearPart = StringUtils.substring(nicRegDataDBO.getApproxDob(), 6, 10);
					//[EPP] assume approxDob is in the format: YYYY-MM-DD
					String dayPart = StringUtils.substring(nicRegDataDBO.getApproxDob(), 8, 10);
					String monthPart = StringUtils.substring(nicRegDataDBO.getApproxDob(), 5, 7);
					String yearPart = StringUtils.substring(nicRegDataDBO.getApproxDob(), 0, 4);
					String printDob = "";//NIC: dayPart+monthPart+yearPart; EPP: yearPart+monthPart+dayPart
					//NIC:
//					if (StringUtils.equals(dayPart, "00") || StringUtils.equals(dayPart, "  ")) {
//						printDob += "XX";
//					} else {
//						printDob += dayPart;
//					}
//					printDob += " ";
//					//[chris] print dob, month is incorrect - 2014 Jun 08
//					if (StringUtils.equals(monthPart, "01")) {
//						printDob += "Jan";
//					} else if (StringUtils.equals(monthPart, "02")) {
//						printDob += "Feb";
//					} else if (StringUtils.equals(monthPart, "03")) {
//						printDob += "Mar";
//					} else if (StringUtils.equals(monthPart, "04")) {
//						printDob += "Apr";
//					} else if (StringUtils.equals(monthPart, "05")) {
//						printDob += "May";
//					} else if (StringUtils.equals(monthPart, "06")) {
//						printDob += "Jun";
//					} else if (StringUtils.equals(monthPart, "07")) {
//						printDob += "Jul";
//					} else if (StringUtils.equals(monthPart, "08")) {
//						printDob += "Aug";
//					} else if (StringUtils.equals(monthPart, "09")) {
//						printDob += "Sep";
//					} else if (StringUtils.equals(monthPart, "10")) {
//						printDob += "Oct";
//					} else if (StringUtils.equals(monthPart, "11")) {
//						printDob += "Nov";
//					} else if (StringUtils.equals(monthPart, "12")) {
//						printDob += "Dec";
//					} else {//if (StringUtils.equals(monthPart, "00") || StringUtils.equals(monthPart, "  ")) {
//						printDob += "XXX";
//					}
//					//[chris] print dob, month is incorrect - 2014 Jun 08 - end
//					
//					printDob += " ";
//					//if (StringUtils.equals(yearPart, "0000") || StringUtils.equals(monthPart, "    ")) {
//					if (StringUtils.equals(yearPart, "0000") || StringUtils.equals(yearPart, "    ")) {
//						printDob += "XXXX";
//					} else {
//						printDob += yearPart;
//					}
					
					//EPP:
					printDob = yearPart+monthPart+dayPart;
					nicRegDataDBO.setPrintDob(printDob);
				}
			}
			
			nicRegDataDBO.setGender(personDetailDTO.getGender());
			nicRegDataDBO.setMaritalStatus(personDetailDTO.getMaritalStatus());
//			nicRegDataDBO.setMaritalNapoleanCodeFlag(this.convertFlagToBoolean(personDetailDTO.getMaritalNapoleanCodeFlag()));
			
			//EPP additional fields
			nicRegDataDBO.setNationality(personDetailDTO.getNationality());
			nicRegDataDBO.setPlaceOfBirth(personDetailDTO.getPlaceOfBirth());
			nicRegDataDBO.setPrintRemarks1(regDataDTO.getPrintRemark1());
			nicRegDataDBO.setPrintRemarks2(regDataDTO.getPrintRemark2());
			nicRegDataDBO.setAcquiredCitizenship(regDataDTO.getAcquiredCitizenship());
			nicRegDataDBO.setForeignPassportHolder(regDataDTO.getForeignPassportHolder());
			nicRegDataDBO.setOccupationDesc(regDataDTO.getOccupationDesc());
			nicRegDataDBO.setOfficeAddress(regDataDTO.getOfficeAddress());
			nicRegDataDBO.setOfficeContactNo(regDataDTO.getOfficeContactNo());
			nicRegDataDBO.setTravelPurpose(regDataDTO.getTravelPurpose());
			nicRegDataDBO.setAliasName(personDetailDTO.getAliasname());  //03 Jun 2016 - add aliasname for aka
			
			
			
			//get Address
			//Address addressDTO = regDataDTO.getAddress();
			nicRegDataDBO.setAddressLine1(regDataDTO.getAddressLine1());
			nicRegDataDBO.setAddressLine2(regDataDTO.getAddressLine2());
			nicRegDataDBO.setAddressLine3(regDataDTO.getAddressLine3());
			nicRegDataDBO.setAddressLine4(regDataDTO.getAddressLine4());
			nicRegDataDBO.setAddressLine5(regDataDTO.getAddressLine5());
//			nicRegDataDBO.setAddressLine6(addressDTO.getAddressLine6());
			//preferredMailingAddress, overseasAddress, overseasCountry
//			nicRegDataDBO.setPreferredMailingAddress(addressDTO.getPreferredMailingAddress());
			nicRegDataDBO.setOverseasAddress(regDataDTO.getOverseasAddress());
			nicRegDataDBO.setOverseasCountry(regDataDTO.getOverseasCountry());
			
//			nicRegDataDBO.setAddressUpdatedFlag(this.convertFlagToBoolean(addressDTO.getAddressUpdateFlag()));
//			nicRegDataDBO.setAddressUpdatedOfficerId(addressDTO.getUpdateBy());
//			nicRegDataDBO.setAddressUpdatedWkstnId(addressDTO.getUpdateWkstnID());
//			nicRegDataDBO.setAddressUpdatedTime(addressDTO.getUpdateDate());
	
			//get Relationship
			nicRegDataDBO.setFatherFullname(personDetailDTO.getFatherFirstname() + " " + personDetailDTO.getFatherMiddlename() + " " + personDetailDTO.getFatherSurname());
			/*nicRegDataDBO.setFatherSurname(personDetailDTO.getFatherSurname());
			nicRegDataDBO.setFatherMiddlename(personDetailDTO.getFatherMiddlename());*/
			nicRegDataDBO.setFatherCitizenship(personDetailDTO.getFatherCitizenship());
			nicRegDataDBO.setMotherFullname(personDetailDTO.getMotherFirstname() + " " + personDetailDTO.getMotherMiddlename() + " " + personDetailDTO.getMotherSurname());
			//nicRegDataDBO.setMotherSurname(personDetailDTO.getMotherSurname());
			//nicRegDataDBO.setMotherMiddlename(personDetailDTO.getMotherMiddlename());
			nicRegDataDBO.setMotherCitizenship(personDetailDTO.getMotherCitizenship());
			nicRegDataDBO.setSpouseFullname(personDetailDTO.getSpouseFirstname() + " " + personDetailDTO.getSpouseMiddlename() +" " + personDetailDTO.getSpouseSurname());
			//nicRegDataDBO.setSpouseSurname(personDetailDTO.getSpouseSurname());
			//nicRegDataDBO.setSpouseMiddlename(personDetailDTO.getSpouseMiddlename());
			nicRegDataDBO.setSpouseCitizenship(personDetailDTO.getSpouseCitizenship());
			
	//		nicRegDataDBO.setVerDefaultFp(Long verDefaultFp);
			nicRegDataDBO.setFpBypassDecision(this.convertFlagToBoolean(regDataDTO.getFpByPassDecision()));
			nicRegDataDBO.setFpBypassBy(regDataDTO.getFpByPassBy());
			nicRegDataDBO.setFpBypassDatetime(regDataDTO.getFpByPassDateTime());
			nicRegDataDBO.setTotalFp(regDataDTO.getTotalFp());
			nicRegDataDBO.setFpIndicator(regDataDTO.getFpIndicator());
//			nicRegDataDBO.setFpVerifyScore(regDataDTO.getFpVerifyScore());
			nicRegDataDBO.setFpQuality(regDataDTO.getFpQuality());
			nicRegDataDBO.setFpEncode(regDataDTO.getFpEncode());
			nicRegDataDBO.setFpPattern(regDataDTO.getFpPattern());
			nicRegDataDBO.setSignatureFlag(this.convertFlagToBoolean(regDataDTO.getSignatureFlag()));
//			nicRegDataDBO.setSignatureFp(regDataDTO.getSignatureFp());
			nicRegDataDBO.setFacialIndicator(this.convertFlagToBoolean(regDataDTO.getFacialIndicator()));
//			nicRegDataDBO.setSeniorCitizenFlag(this.convertFlagToBoolean(regDataDTO.getSeniorCitizenFlag()));
	//		nicRegDataDBO.setPriority(Boolean priority);
//			nicRegDataDBO.setFpVerifyFlag(this.convertFlagToBoolean(regDataDTO.getFpVerifyFlag()));
//			nicRegDataDBO.setCardVerifyFlag(this.convertFlagToBoolean(regDataDTO.getCardVerifyFlag()));
			//1 Oct 2013 (chris) - start
			//nicRegDataDBO.setExpressFlag(this.convertFlagToBoolean(regDataDTO.getCardVerifyFlag()));
//			nicRegDataDBO.setExpressFlag(this.convertFlagToBoolean(regDataDTO.getExpressFlag()));
			//1 Oct 2013 (chris) - end
			//17 Sep 2013 (chris) - start
			//nicRegDataDBO.setConversionFlag(this.convertFlagToBoolean(regDataDTO.getConversionFlag()));
//			if (StringUtils.isBlank(regDataDTO.getConversionFlag())) {
//				if (StringUtils.startsWith(transactionId, "C")) {
//					nicRegDataDBO.setConversionFlag(Boolean.TRUE);
//				} else {
//					nicRegDataDBO.setConversionFlag(Boolean.FALSE);
//				}
//			} else {
//				nicRegDataDBO.setConversionFlag(this.convertFlagToBoolean(regDataDTO.getConversionFlag()));
//			}
			//17 Sep 2013 (chris) - end
			
			nicRegDataDBO.setEmail(regDataDTO.getEmail());
			nicRegDataDBO.setContactNo(regDataDTO.getContactNo());
			nicRegDataDBO.setJob(regDataDTO.getJob());  //03 Jun 2016 - add aliasname for aka
			//trung editor 20/11
			/*nicRegDataDBO.setMethodReceive(regDataDTO.getMethodReceive());
			nicRegDataDBO.setPrioritize(this.convertFlagToBoolean(regDataDTO.getPrioritize()));*/
			nicRegDataDBO.setReligion(regDataDTO.getReligion());
			nicRegDataDBO.setNation(regDataDTO.getNation());
			nicRegDataDBO.setAddressNin(regDataDTO.getAddressNin());
			nicRegDataDBO.setDateNin(regDataDTO.getDateNin());
			/*nicRegDataDBO.setTypeChild(regDataDTO.getTypeChild());
			nicRegDataDBO.setAddressReceive(regDataDTO.getAddressReceive());
			nicRegDataDBO.setAddressCompany(regDataDTO.getAddressCompany());
			nicRegDataDBO.setFatherDob(regDataDTO.getFatherDob());
			nicRegDataDBO.setMotherDob(regDataDTO.getMotherDob());
			nicRegDataDBO.setSpouseDob(regDataDTO.getSpouseDob());
			nicRegDataDBO.setAddressTempNum(regDataDTO.getAddressTempNum());
			nicRegDataDBO.setAddressTempStreet(regDataDTO.getAddressTempStreet());
			nicRegDataDBO.setAddressTempGuild(regDataDTO.getAddressTempGuild());*/
			nicRegDataDBO.setAddressTempDistrict(regDataDTO.getAddressTempDistrict());
			//nicRegDataDBO.setAddressTempCity(regDataDTO.getAddressTempCity());
			nicRegDataDBO.setAddressTempNation(regDataDTO.getAddressTempNation());
			//nicRegDataDBO.setStManWorking(regDataDTO.getStManWorking());
			nicRegDataDBO.setNumDecision(regDataDTO.getNumDecision());
			//nicRegDataDBO.setGovDecision(regDataDTO.getGovDecision());
			//nicRegDataDBO.setSignerDecision(regDataDTO.getSignerDecision());
			nicRegDataDBO.setNameDepartment(regDataDTO.getNameDepartment());
			//nicRegDataDBO.setAddressDepartment(regDataDTO.getAddressDepartment());
			//nicRegDataDBO.setPhoneDepartment(regDataDTO.getPhoneDepartment());
			nicRegDataDBO.setPosition(regDataDTO.getPosition());
			/*nicRegDataDBO.setRank(regDataDTO.getRank());
			nicRegDataDBO.setPostionEng(regDataDTO.getPostionEng());
			nicRegDataDBO.setCivilServants(regDataDTO.getCivilServants());
			nicRegDataDBO.setLevelRank(regDataDTO.getLevelRank());
			nicRegDataDBO.setQuantum(regDataDTO.getQuantum());
			nicRegDataDBO.setToNation(regDataDTO.getToNation());
			nicRegDataDBO.setTransitNation(regDataDTO.getTransitNation());
			nicRegDataDBO.setPurpose(regDataDTO.getPurpose());
			nicRegDataDBO.setTypeExpense(regDataDTO.getTypeExpense());
			nicRegDataDBO.setEstimateFrom(regDataDTO.getEstimateFrom());
			nicRegDataDBO.setEstimateTo(regDataDTO.getEstimateTo());*/
			//end TRung edit
			
			//FOR LOST/DAMAGE CASE
			/*nicRegDataDBO.setDatePassport(regDataDTO.getDatePassport());
			nicRegDataDBO.setNameProvider(regDataDTO.getNameProvider());
			nicRegDataDBO.setDateCountry(regDataDTO.getDateCountry());
			nicRegDataDBO.setPurposeKind(regDataDTO.getPurposeKind());
			nicRegDataDBO.setReasonKind(regDataDTO.getReasonKind());*/
			
//			nicRegDataDBO.setEstCollectionDate(regDataDTO.getEstCollectionDate());
			checkFpData(nicRegDataDBO, regDataDTO);
			//nicRegDataDBO.setStyleDob(regDataDTO.getStyleDob());

			/*if(StringUtils.isNotEmpty(regDataDTO.getFatherLost())){
				if(regDataDTO.getFatherLost().equals("Y"))
						nicRegDataDBO.setFatherLost(true);
				else
					nicRegDataDBO.setFatherLost(false);
			}
			else
				nicRegDataDBO.setFatherLost(false);*/
			
			/*if(StringUtils.isNotEmpty(regDataDTO.getMotherLost())){
				if(regDataDTO.getMotherLost().equals("Y"))
						nicRegDataDBO.setMotherLost(true);
				else
					nicRegDataDBO.setMotherLost(false);
			}
			else
				nicRegDataDBO.setMotherLost(false);*/
			
			/*nicRegDataDBO.setSfDob(regDataDTO.getSfDob());
			nicRegDataDBO.setSmDob(regDataDTO.getSmDob());
			nicRegDataDBO.setSsDob(regDataDTO.getSsDob());*/
		}
		return nicRegDataDBO;
	}
	/**
	 * Check and Update FpVerifyScore and FpQuality.
	 * FpVerifyScore: 1-9999,2-9999,3-2537,4-2566,5-324,6-9999,7-9999,8-9999,9-9999,10-365 
	 * 	 change to	: 1-9999,2-9999,3-2537,4-2566,5-0324,6-9999,7-9999,8-9999,9-9999,10-0365
	 * FpQuality	: 1-89,2-62,3-72,4-63,5-9,6-70,7-47,8-64,9-55,10-66
	 *   change to	: 1-89,2-62,3-72,4-63,5-09,6-70,7-47,8-64,9-55,10-66
	 *   
	 * @param nicRegDataDBO
	 * @param regDataDTO
	 */
	private void checkFpData(NicRegistrationData nicRegDataDBO, RegistrationData regDataDTO) {
		if (nicRegDataDBO!=null) {
			//check fp quality and fp verify score
			String fpQuality = nicRegDataDBO.getFpQuality();
//			String fpVerifyScore = nicRegDataDBO.getFpVerifyScore();
			String fpIndicator = nicRegDataDBO.getFpIndicator();
			
			//check fp quality 1-99,2-99,3-99,4-99,5-99,6-99,7-99,8-99,9-99,10-99
			if (StringUtils.length(fpQuality)!=50) {
				boolean isExceed50 = StringUtils.length(fpQuality)>50;
				String qualityFormat = isExceed50 ? "%03d" : "%02d";
				
				Map<String, String> fpqMap = new HashMap<String, String>();				
				String[] fpQualityArray = StringUtils.split(fpQuality, ",");
				if (fpQualityArray!=null) { //24 Jan 2014 (chris) - start
					for (String fpq : fpQualityArray) {
						if (StringUtils.contains(fpq, "-") && StringUtils.split(fpq, "-").length==2) {
							int fingerPosition = Integer.parseInt(StringUtils.split(fpq, "-")[0]);
							String qualityStr = StringUtils.split(fpq, "-")[1];
							if (StringUtils.isNotBlank(qualityStr)) {
								Integer qualityInt = Integer.parseInt(qualityStr.trim());
								String quality = String.format(qualityFormat, qualityInt);
								fpqMap.put("" + fingerPosition, quality);
							}
						}
					}
				} //24 Jan 2014 (chris) - end
				for (int i=1; i<=10; i++) {
					if (i==1) 
						fpQuality = "";
					else
						fpQuality += ",";
					String fpData = fpqMap.get(""+i);
					if (StringUtils.isBlank(fpData))
						fpData = String.format(qualityFormat,0);
					String fpqData = i+"-"+fpData;
					fpQuality += fpqData;
				}
				logger.debug("[checkFpData] update FpQuality from [{}] to [{}]", nicRegDataDBO.getFpQuality(), fpQuality);
				if (StringUtils.length(fpQuality)!=60) {
					logger.warn("[checkFpData] update FpQuality failed {}, please check the transaction[{}].", fpQuality, nicRegDataDBO.getTransactionId());
				}
			}
			
			//check fp indicator 1-9999,2-9999,3-2537,4-2566,5-0324,6-9999,7-9999,8-9999,9-9999,10-0365
//			if (StringUtils.length(fpVerifyScore) != 70) {
//				Map<String, String> fpvsMap = new HashMap<String, String>();
//				String[] fpvsArray = StringUtils.split(fpVerifyScore, ",");
//				if (fpvsArray!=null) { //24 Jan 2014 (chris) - start
//					for (String fpvs : fpvsArray) {
//						if (StringUtils.contains(fpvs, "-") && StringUtils.split(fpvs, "-").length == 2) {
//							int fingerPosition = Integer.parseInt(StringUtils.split(fpvs, "-")[0]);
//							String verifyScoreStr = StringUtils.split(fpvs, "-")[1];
//							if (StringUtils.isNotBlank(verifyScoreStr)) {
//								Integer verifyScoreInt = Integer.parseInt(verifyScoreStr.trim());
//								String verifyScore = String.format("%04d", verifyScoreInt);
//								fpvsMap.put("" + fingerPosition, verifyScore);
//							}
//						}
//					}
//				} //24 Jan 2014 (chris) - end
//				for (int i=1; i<=10; i++) {
//					if (i==1) 
//						fpVerifyScore = "";
//					else
//						fpVerifyScore += ",";
//					String fpData = fpvsMap.get(""+i);
//					if (StringUtils.isBlank(fpData))
//						fpData = String.format("%04d",0);
//					String fpvsData = i+"-"+fpData;
//					fpVerifyScore += fpvsData;
//				}
//				logger.debug("[checkFpData] update FpVerifyScore from [{}] to [{}]", nicRegDataDBO.getFpVerifyScore(), fpVerifyScore);
//				if (StringUtils.length(fpVerifyScore)!=70) {
//					logger.warn("[checkFpData] update FpVerifyScore failed, please check the transaction[{}].", nicRegDataDBO.getTransactionId());
//				}
//			}		
//			
			//check fp indicator  1-N,2-N,3-N,4-N,5-N,6-N,7-N,8-N,9-A,10-N
			if (StringUtils.length(fpIndicator) != 40) {
				Map<String, String> fpIndMap = new HashMap<String, String>();
				String[] fpIndArray = StringUtils.split(fpIndicator, ",");
				if (fpIndArray!=null) { //24 Jan 2014 (chris) - start
					for (String fpInd : fpIndArray) {
						if (StringUtils.contains(fpInd, "-") && StringUtils.split(fpInd, "-").length == 2) {
							int fingerPosition = Integer.parseInt(StringUtils.split(fpInd, "-")[0]);
							String fpi = StringUtils.split(fpInd, "-")[1];
							fpIndMap.put("" + fingerPosition, fpi);
						}
					}
				} //24 Jan 2014 (chris) - end
				
				for (int i=1; i<=10; i++) {
					String fpi = fpIndMap.get(""+i);
					if (StringUtils.isBlank(fpi)) {
						fpi = "B";
						fpIndMap.put("" + i, fpi);
					}
					if (!StringUtils.equalsIgnoreCase(fpi, "A") && !StringUtils.equalsIgnoreCase(fpi, "B")) { 
						//if not amputated, then fingerprint binary should be populated
						boolean validFingeprint = false;
						for (Fingerprint fingerprint: regDataDTO.getFingerprintInfo().getFingerprints()) {
							if (fingerprint.getFingerprintPosition().equalsIgnoreCase(""+i) && fingerprint.getFingerprintData()!=null) {
								validFingeprint = true;							
							}
						}
						if (!validFingeprint) {
							logger.warn("[checkFpData] fingerprint binary should not be null.[{}] fp={}.", nicRegDataDBO.getTransactionId(), i);
						}
					}
					
					if (i==1) 
						fpIndicator = "";
					else
						fpIndicator += ",";
					String fpiData = i+"-"+fpi;
					fpIndicator += fpiData;
				}
				logger.debug("[checkFpData] update FpIndicator from [{}] to [{}]", nicRegDataDBO.getFpIndicator(), fpIndicator);
				if (StringUtils.length(fpIndicator)!=40) {
					logger.warn("[checkFpData] update FpIndicator failed, please check the transaction[{}].", nicRegDataDBO.getTransactionId());
				}
			}
			
			nicRegDataDBO.setFpQuality(fpQuality);
//			nicRegDataDBO.setFpVerifyScore(fpVerifyScore);
			nicRegDataDBO.setFpIndicator(fpIndicator);
		}
	}
	
	//Registration Data DTO to Document DBO
//	public List<NicTransactionAttachment> parseTransactionDocDBOList(String transactionId, RegistrationData regDataDTO) {
//		List<NicTransactionAttachment> allTransDocDBOList = new ArrayList<NicTransactionAttachment>();
//		if (regDataDTO!=null) {
//			FacialInfo facialInfoDTO = regDataDTO.getFacialInfo();
//			FingerprintInfo fingerprintInfoDTO = regDataDTO.getFingerprintInfo();
//			List<ScannedDocument> scannedDocDTOList = regDataDTO.getScannedDocuments();
//			SignatureInfo signatureInfoDTO = regDataDTO.getSignatureInfo();
//			
//			allTransDocDBOList.addAll(this.parseNicTransDocDBOForFacial(transactionId, facialInfoDTO));
//			allTransDocDBOList.addAll(this.parseNicTransDocDBOForFingerprint(transactionId, fingerprintInfoDTO));
//			for (ScannedDocument scannedDocDTO : scannedDocDTOList) {
//				allTransDocDBOList.addAll(this.parseNicTransDocDBOForScannedDocument(transactionId, scannedDocDTO));
//			}
//			if (signatureInfoDTO!=null) {
//				//07 Oct 2013 (chris): to handle blank in signature in parseNicTransDocDBOForSignature - start
//				NicTransactionAttachment nicTransDocForSignature = this.parseNicTransDocDBOForSignature(transactionId, signatureInfoDTO);
//				if (nicTransDocForSignature!=null) {
//					allTransDocDBOList.add(nicTransDocForSignature);
//				}
//				//07 Oct 2013 (chris): to handle blank in signature in parseNicTransDocDBOForSignature - end
//			}
//		}
//		return allTransDocDBOList;
//	}
	
	//Registration Data DBO to Registration DTO
	public RegistrationData parseRegistrationDataDTO(NicRegistrationData nicRegDataDBO, Collection<NicTransactionAttachment> nicTransDocDBOList) {
		RegistrationData regDataDTO = null;
		if (nicRegDataDBO!=null) {
			regDataDTO = new RegistrationData();
			if (nicRegDataDBO!=null) {
				regDataDTO.setTransactionID(nicRegDataDBO.getTransactionId());
//				regDataDTO.setRegistrationCompleteFlag(this.convertBooleanToFlag(nicRegDataDBO.getRegCompleteFlag()));
//				regDataDTO.setRegistrationOfficerID(nicRegDataDBO.getRegOfficerId()) ;
//				regDataDTO.setRegistrationCompleteTime(nicRegDataDBO.getRegCompleteTime());
//				regDataDTO.setVerificationCompleteFlag(this.convertBooleanToFlag(nicRegDataDBO.getVerCompleteFlag()));
//				regDataDTO.setVerificationOfficerID(nicRegDataDBO.getVerOfficerId());
//				regDataDTO.setVerificationCompleteTime(nicRegDataDBO.getVerCompleteTime());
//				regDataDTO.setPaymentCompleteFlag(this.convertBooleanToFlag(nicRegDataDBO.getPayCompleteFlag()));
//				regDataDTO.setPaymentOfficerID(nicRegDataDBO.getPayOfficerId());
//				regDataDTO.setPaymentCompleteTime(nicRegDataDBO.getPayCompleteTime());
//				regDataDTO.setTransactionCompletedFlag(this.convertBooleanToFlag(nicRegDataDBO.getRegTransactionCompleteFlag()));
//				regDataDTO.setTransactionUploadedCompletedFlag(this.convertBooleanToFlag(nicRegDataDBO.getUploadFlag()));
//				regDataDTO.setNicJobCompletedFlag(this.convertBooleanToFlag(nicRegDataDBO.getNicJobCompleteFlag()));
//				regDataDTO.setFullAmputatedFlag(this.convertBooleanToFlag(nicRegDataDBO.getFullAmputatedFlag()));
//				regDataDTO.setPartialAmputatedFlag(this.convertBooleanToFlag(nicRegDataDBO.getPartialAmputatedFlag()));
//				regDataDTO.setSupervisorDecision(nicRegDataDBO.getSupervisorDecision());
				regDataDTO.setCreateBy(nicRegDataDBO.getCreateBy());
				regDataDTO.setCreateDateTime(nicRegDataDBO.getCreateDatetime());
				regDataDTO.setCreateWkstnID(nicRegDataDBO.getCreateWkstnId());
				regDataDTO.setUpdateBy(nicRegDataDBO.getUpdateBy());
				regDataDTO.setUpdateDateTime(nicRegDataDBO.getUpdateDatetime());
				regDataDTO.setUpdateWkstnID(nicRegDataDBO.getUpdateWkstnId());
				
				regDataDTO.setFpByPassBy(nicRegDataDBO.getFpBypassBy());
				regDataDTO.setFpByPassDateTime(nicRegDataDBO.getFpBypassDatetime());
				regDataDTO.setFpByPassDecision(this.convertBooleanToFlag(nicRegDataDBO.getFpBypassDecision()));
				if (nicRegDataDBO.getTotalFp()!=null) 	regDataDTO.setTotalFp(nicRegDataDBO.getTotalFp().intValue());
				regDataDTO.setFpIndicator(nicRegDataDBO.getFpIndicator());
				regDataDTO.setFpQuality(nicRegDataDBO.getFpQuality());
				regDataDTO.setFpEncode(nicRegDataDBO.getFpEncode());
//				regDataDTO.setFpVerifyScore(nicRegDataDBO.getFpVerifyScore());
				regDataDTO.setSignatureFlag(this.convertBooleanToFlag(nicRegDataDBO.getSignatureFlag()));
//				regDataDTO.setSignatureFp(nicRegDataDBO.getSignatureFp());
				regDataDTO.setFacialIndicator(this.convertBooleanToFlag(nicRegDataDBO.getFacialIndicator()));
//				regDataDTO.setSeniorCitizenFlag(this.convertBooleanToFlag(nicRegDataDBO.getSeniorCitizenFlag()));
//				regDataDTO.setFpVerifyFlag(this.convertBooleanToFlag(nicRegDataDBO.getFpVerifyFlag()));
//				regDataDTO.setCardVerifyFlag(this.convertBooleanToFlag(nicRegDataDBO.getCardVerifyFlag()));
//				regDataDTO.setExpressFlag(this.convertBooleanToFlag(nicRegDataDBO.getExpressFlag()));
//				regDataDTO.setConversionFlag(this.convertBooleanToFlag(nicRegDataDBO.getConversionFlag()));
				regDataDTO.setEmail(nicRegDataDBO.getEmail());
				regDataDTO.setContactNo(nicRegDataDBO.getContactNo());				
				
//				regDataDTO.setEstCollectionDate(nicRegDataDBO.getEstCollectionDate());

				//EPP additional fields
				regDataDTO.setPrintRemark1(nicRegDataDBO.getPrintRemarks1());
				regDataDTO.setPrintRemark2(nicRegDataDBO.getPrintRemarks2());
				regDataDTO.setAcquiredCitizenship(nicRegDataDBO.getAcquiredCitizenship());
				regDataDTO.setForeignPassportHolder(nicRegDataDBO.getForeignPassportHolder());
				regDataDTO.setOccupationDesc(nicRegDataDBO.getOccupationDesc());
				regDataDTO.setOfficeAddress(nicRegDataDBO.getOfficeAddress());
				regDataDTO.setOfficeContactNo(nicRegDataDBO.getOfficeContactNo());
				regDataDTO.setTravelPurpose(nicRegDataDBO.getTravelPurpose());
				
				regDataDTO.setPersonDetail(this.parsePersonDetailDTO(nicRegDataDBO));
				//regDataDTO.setAddress(this.parseAddressDTO(nicRegDataDBO));
				regDataDTO.setAddressLine1(nicRegDataDBO.getAddressLine1());
				regDataDTO.setAddressLine2(nicRegDataDBO.getAddressLine2());
				regDataDTO.setAddressLine3(nicRegDataDBO.getAddressLine3());
				regDataDTO.setAddressLine4(nicRegDataDBO.getAddressLine4());
				regDataDTO.setOverseasAddress(nicRegDataDBO.getOverseasAddress());
				regDataDTO.setOverseasCountry(nicRegDataDBO.getOverseasCountry());
				//regDataDTO.getRelationships().addAll(this.parseRelationshipDTOList(nicRegDataDBO));
				
				//Bổ sung dữ liệu mới
				regDataDTO.setJob(nicRegDataDBO.getJob());
				//regDataDTO.setMethodReceive(nicRegDataDBO.getMethodReceive());
				//regDataDTO.setPrioritize(nicRegDataDBO.getPrioritize());
				regDataDTO.setReligion(nicRegDataDBO.getReligion());
				regDataDTO.setNation(nicRegDataDBO.getNation());
				//regDataDTO.setAddressReceive(nicRegDataDBO.getAddressReceive());
				regDataDTO.setAddressNin(nicRegDataDBO.getAddressNin());
				regDataDTO.setDateNin(nicRegDataDBO.getDateNin());
				//regDataDTO.setTypeChild(nicRegDataDBO.getTypeChild());
				/*regDataDTO.setAddressCompany(nicRegDataDBO.getAddressCompany());
				regDataDTO.setFatherDob(nicRegDataDBO.getFatherDob());
				regDataDTO.setMotherDob(nicRegDataDBO.getMotherDob());
				regDataDTO.setSpouseDob(nicRegDataDBO.getSpouseDob());
				regDataDTO.setAddressTempNum(nicRegDataDBO.getAddressTempNum());
				regDataDTO.setAddressTempStreet(nicRegDataDBO.getAddressTempStreet());
				regDataDTO.setAddressTempGuild(nicRegDataDBO.getAddressTempGuild());
				regDataDTO.setAddressTempDistrict(nicRegDataDBO.getAddressTempDistrict());
				regDataDTO.setAddressTempCity(nicRegDataDBO.getAddressTempCity());
				regDataDTO.setAddressTempNation(nicRegDataDBO.getAddressTempNation());
				regDataDTO.setStManWorking(nicRegDataDBO.getStManWorking());
				regDataDTO.setNumDecision(nicRegDataDBO.getNumDecision());
				regDataDTO.setGovDecision(nicRegDataDBO.getGovDecision());
				regDataDTO.setNameDepartment(nicRegDataDBO.getNameDepartment());
				regDataDTO.setAddressDepartment(nicRegDataDBO.getAddressDepartment());
				regDataDTO.setPhoneDepartment(nicRegDataDBO.getPhoneDepartment());*/
				regDataDTO.setPosition(nicRegDataDBO.getPosition());
				/*regDataDTO.setRank(nicRegDataDBO.getRank());
				regDataDTO.setPostionEng(nicRegDataDBO.getPostionEng());
				regDataDTO.setCivilServants(nicRegDataDBO.getCivilServants());
				regDataDTO.setLevelRank(nicRegDataDBO.getLevelRank());
				regDataDTO.setQuantum(nicRegDataDBO.getQuantum());
				regDataDTO.setToNation(nicRegDataDBO.getToNation());
				regDataDTO.setTransitNation(nicRegDataDBO.getTransitNation());
				regDataDTO.setPurpose(nicRegDataDBO.getPurpose());
				regDataDTO.setTypeExpense(nicRegDataDBO.getTypeExpense());
				regDataDTO.setEstimateTo(nicRegDataDBO.getEstimateTo());
				regDataDTO.setEstimateFrom(nicRegDataDBO.getEstimateFrom());
				regDataDTO.setAddressLine5(nicRegDataDBO.getAddressLine5());
				regDataDTO.setDatePassport(nicRegDataDBO.getDatePassport());
				regDataDTO.setNameProvider(nicRegDataDBO.getNameProvider());
				regDataDTO.setDateCountry(nicRegDataDBO.getDateCountry());
				regDataDTO.setNameProvider(nicRegDataDBO.getNameProvider());
				regDataDTO.setDateCountry(nicRegDataDBO.getDateCountry());
				regDataDTO.setPurposeKind(nicRegDataDBO.getPurposeKind());
				regDataDTO.setReasonKind(nicRegDataDBO.getReasonKind());*/
				
				List<NicTransactionAttachment> facialDocDBOList = this.getTransactionDocDBOListForFacial(nicTransDocDBOList);
				List<NicTransactionAttachment> fpDocDBOList = this.getTransactionDocDBOListForFingerprint(nicTransDocDBOList);
				List<NicTransactionAttachment> mnuDocDBOList = getTransactionDocDBOListForMinutiaeTemplate(nicTransDocDBOList);
				NicTransactionAttachment signatureDocDBO = this.getTransactionDocDBOForSignature(nicTransDocDBOList); 
				List<NicTransactionAttachment> refDocDBOList = this.getTransactionDocDBOListForReferenceDocument(nicTransDocDBOList);
				NicTransactionAttachment tplDocDBO = this.getTransactionDocDBOForTemplate(nicTransDocDBOList);
				
				regDataDTO.setFacialInfo(this.parseFacialInfoDTO(facialDocDBOList));
				regDataDTO.setFingerprintInfo(this.parseFingerprintInfoDTO(fpDocDBOList, mnuDocDBOList, tplDocDBO));
//				regDataDTO.getScannedDocuments().addAll(this.parseScannedDocumentDTO(scannedDocDBOList));
				regDataDTO.setSignatureInfo(this.parseSignatureInfoDTO(signatureDocDBO));
				
				regDataDTO.getReferenceDocuments().addAll(this.parseReferenceDocumentDTO(refDocDBOList));
			}
//			regDataDTO.setPaymentInfo(this.parsePaymentInfoDTO(nicTransPaymentDBO));
		}
		return regDataDTO;
	}
	
	private List<NicTransactionAttachment> getTransactionDocDBOListForFacial(Collection<NicTransactionAttachment> nicTransDocDBOList) {
		List<NicTransactionAttachment> facialTransDocDBOList = new ArrayList<NicTransactionAttachment>();
		for (NicTransactionAttachment docDBO : nicTransDocDBOList) {
			if (StringUtils.startsWith(docDBO.getDocType(),DOC_TYPE_PREFIX_PHOTO) 
					|| StringUtils.startsWith(docDBO.getDocType(),DOC_TYPE_PREFIX_THUMBNAIL) ) {
				facialTransDocDBOList.add(docDBO);
			}
		}
		return facialTransDocDBOList;
	}
	private List<NicTransactionAttachment> getTransactionDocDBOListForFingerprint(Collection<NicTransactionAttachment> nicTransDocDBOList) {
		List<NicTransactionAttachment> fingerprintTransDocDBOList = new ArrayList<NicTransactionAttachment>();
		for (NicTransactionAttachment docDBO : nicTransDocDBOList) {
			if (StringUtils.startsWith(docDBO.getDocType(), DOC_TYPE_PREFIX_FINGERPRINT)) {
				fingerprintTransDocDBOList.add(docDBO);
			}
		}
		return fingerprintTransDocDBOList;
	}
	private NicTransactionAttachment getTransactionDocDBOForTemplate(Collection<NicTransactionAttachment> nicTransDocDBOList) {
		NicTransactionAttachment tplTransDocDBOList = new NicTransactionAttachment();
		for (NicTransactionAttachment docDBO : nicTransDocDBOList) {
			if (StringUtils.startsWith(docDBO.getDocType(), DOC_TYPE_TPL)) {
				tplTransDocDBOList = docDBO;
			}
		}
		return tplTransDocDBOList;
	}
	private List<NicTransactionAttachment> getTransactionDocDBOListForMinutiaeTemplate(Collection<NicTransactionAttachment> nicTransDocDBOList) {
		List<NicTransactionAttachment> mnuTransDocDBOList = new ArrayList<NicTransactionAttachment>();
		for (NicTransactionAttachment docDBO : nicTransDocDBOList) {
			if (StringUtils.startsWith(docDBO.getDocType(), DOC_TYPE_PREFIX_MINUTIAETEMPLATE)) {
				mnuTransDocDBOList.add(docDBO);
			}
		}
		return mnuTransDocDBOList;
	}
	private List<NicTransactionAttachment> getTransactionDocDBOListForScannedDocument(Collection<NicTransactionAttachment> nicTransDocDBOList) {
		List<NicTransactionAttachment> scTransDocDBOList = new ArrayList<NicTransactionAttachment>();
		for (NicTransactionAttachment docDBO : nicTransDocDBOList) {
			if (StringUtils.startsWith(docDBO.getDocType(), DOC_TYPE_PREFIX_SCANNEDDOC)) {
				scTransDocDBOList.add(docDBO);
			}
		}
		return scTransDocDBOList;
	}
	private NicTransactionAttachment getTransactionDocDBOForSignature(Collection<NicTransactionAttachment> nicTransDocDBOList) {
		NicTransactionAttachment signatureTransDocDBO = null;
		for (NicTransactionAttachment docDBO : nicTransDocDBOList) {
			if (StringUtils.startsWith(docDBO.getDocType(), DOC_TYPE_PREFIX_SIGNATURE)) {
				signatureTransDocDBO = docDBO;
				break;
			}
		}
		return signatureTransDocDBO;
	}
	private List<NicTransactionAttachment> getTransactionDocDBOListForReferenceDocument(
			Collection<NicTransactionAttachment> nicTransDocDBOList) {
		List<NicTransactionAttachment> refDocDBOList = new ArrayList<NicTransactionAttachment>();
		for (NicTransactionAttachment docDBO : nicTransDocDBOList) {
			if (StringUtils.startsWith(docDBO.getDocType(),DOC_TYPE_PREFIX_PHOTO) 
					|| StringUtils.startsWith(docDBO.getDocType(),DOC_TYPE_PREFIX_THUMBNAIL) ) {
				continue;
			} else if (StringUtils.startsWith(docDBO.getDocType(), DOC_TYPE_PREFIX_FINGERPRINT)) {
				continue;
			} else if (StringUtils.startsWith(docDBO.getDocType(), DOC_TYPE_PREFIX_MINUTIAETEMPLATE)) {
				continue;
			} else if (StringUtils.startsWith(docDBO.getDocType(), DOC_TYPE_PREFIX_SCANNEDDOC)) {
				continue;
			} else if (StringUtils.startsWith(docDBO.getDocType(), DOC_TYPE_PREFIX_SIGNATURE)) {
				continue;
			} else if (StringUtils.equals(docDBO.getDocType(), DOC_TYPE_XML_ENCODING)) { //to filter for encoding xml
				continue;
			} else if (StringUtils.equals(docDBO.getDocType(), DOC_TYPE_XML_PERSO)) { //to filter for perso xml
				continue;
			} else if (StringUtils.equals(docDBO.getDocType(), DOC_TYPE_TPL)) { //to filter for FMS Template
				continue;
			}
			refDocDBOList.add(docDBO);
		}
		return refDocDBOList;
	}
	
	public String getReferenceTransactionId(MainTransaction transactionDTO) {
		String refTxnId = null;
		if (transactionDTO!=null && transactionDTO.getRegistrationData()!=null 
				&& CollectionUtils.isNotEmpty(transactionDTO.getRegistrationData().getReferenceDocuments())) {
			for (ReferenceDocument docDTO : transactionDTO.getRegistrationData().getReferenceDocuments()) {
				if (StringUtils.equalsIgnoreCase(docDTO.getDocType(), NicTransactionAttachment.DOC_TYPE_REF_TXN_ID)
						&& docDTO.getDocData()!=null) {
					refTxnId = new String(docDTO.getDocData());
				}
			}
		}
		return refTxnId;
	}
	
	public String getReferenceTransactionId(Collection<NicTransactionAttachment> nicTransDocDBOList) {
		String refTxnId = null;
		for (NicTransactionAttachment docDBO : nicTransDocDBOList) {
			if (StringUtils.equalsIgnoreCase(docDBO.getDocType(), NicTransactionAttachment.DOC_TYPE_REF_TXN_ID)
					&& docDBO.getDocData()!=null) {
				refTxnId = new String(docDBO.getDocData());
			}
		}
		return refTxnId;
	}
	
	//Person Detail DTO
	public PersonDetail parsePersonDetailDTO(NicRegistrationData nicRegDataDBO) {
		PersonDetail personDetailDTO = null;
		if (nicRegDataDBO!=null) {
			personDetailDTO = new PersonDetail();
			personDetailDTO.setSurnameFull(nicRegDataDBO.getSurnameFull());
			personDetailDTO.setSurnameLine1(nicRegDataDBO.getSurnameLine1());
			personDetailDTO.setSurnameLine2(nicRegDataDBO.getSurnameLine2());
			personDetailDTO.setSurnameLenExceedFlag(this.convertBooleanToFlag(nicRegDataDBO.getSurnameLenExceedFlag()));

			personDetailDTO.setFirstnameFull(nicRegDataDBO.getFirstnameFull());
			personDetailDTO.setFirstnameLine1(nicRegDataDBO.getFirstnameLine1());
			personDetailDTO.setFirstnameLine2(nicRegDataDBO.getFirstnameLine2());
			personDetailDTO.setFirstnameLenExceedFlag(this.convertBooleanToFlag(nicRegDataDBO.getFirstnameLenExceedFlag()));

			personDetailDTO.setMiddlenameFull(nicRegDataDBO.getMiddlenameFull());
			personDetailDTO.setMiddlenameLine1(nicRegDataDBO.getMiddlenameLine1());
			personDetailDTO.setMiddlenameLine2(nicRegDataDBO.getMiddlenameLine2());
			personDetailDTO.setMiddlenameLenExceedFlag(this.convertBooleanToFlag(nicRegDataDBO.getMiddlenameLenExceedFlag()));
//			personDetailDTO.setOptionSurname(nicRegDataDBO.getOptionSurname());
			
			personDetailDTO.setAliasname(nicRegDataDBO.getAliasName()); //03 Jun 2016 - add aliasname for aka
			
			personDetailDTO.setDateOfBirth(nicRegDataDBO.getDateOfBirth());
			personDetailDTO.setApproxDOB(nicRegDataDBO.getApproxDob());
			personDetailDTO.setPrintDOB(nicRegDataDBO.getPrintDob());
			personDetailDTO.setGender(nicRegDataDBO.getGender());
			personDetailDTO.setMaritalStatus(nicRegDataDBO.getMaritalStatus());
//			personDetailDTO.setMaritalNapoleanCodeFlag(this.convertBooleanToFlag(nicRegDataDBO.getMaritalNapoleanCodeFlag()));
			
			personDetailDTO.setFatherFirstname(nicRegDataDBO.getFatherFullname());
		/*	personDetailDTO.setFatherSurname(nicRegDataDBO.getFatherSurname());
			personDetailDTO.setFatherMiddlename(nicRegDataDBO.getFatherMiddlename());*/
			personDetailDTO.setFatherCitizenship(nicRegDataDBO.getFatherCitizenship());
			personDetailDTO.setMotherFirstname(nicRegDataDBO.getMotherFullname());
			/*personDetailDTO.setMotherSurname(nicRegDataDBO.getMotherSurname());
			personDetailDTO.setMotherMiddlename(nicRegDataDBO.getMotherMiddlename());*/
			personDetailDTO.setMotherCitizenship(nicRegDataDBO.getMotherCitizenship());
			personDetailDTO.setSpouseFirstname(nicRegDataDBO.getSpouseFullname());
/*			personDetailDTO.setSpouseSurname(nicRegDataDBO.getSpouseSurname());
			personDetailDTO.setSpouseMiddlename(nicRegDataDBO.getSpouseMiddlename());*/
			personDetailDTO.setSpouseCitizenship(nicRegDataDBO.getSpouseCitizenship());
			
			personDetailDTO.setNationality(nicRegDataDBO.getNationality());
			personDetailDTO.setPlaceOfBirth(nicRegDataDBO.getPlaceOfBirth());
		}
		return personDetailDTO;
	}
	//Address DTO
//	public Address parseAddressDTO(NicRegistrationData nicRegDataDBO) {
//		Address addressDTO = null;
//		if (nicRegDataDBO!=null) {
//			addressDTO = new Address();
//			addressDTO.setAddressLine1(nicRegDataDBO.getAddressLine1());
//			addressDTO.setAddressLine2(nicRegDataDBO.getAddressLine2());
//			addressDTO.setAddressLine3(nicRegDataDBO.getAddressLine3());
//			addressDTO.setAddressLine4(nicRegDataDBO.getAddressLine4());
////			addressDTO.setAddressLine5(nicRegDataDBO.getAddressLine5());
////			addressDTO.setAddressLine6(nicRegDataDBO.getAddressLine6());
////			addressDTO.setPreferredMailingAddress(nicRegDataDBO.getPreferredMailingAddress());
//			addressDTO.setOverseasAddress(nicRegDataDBO.getOverseasAddress());
//			addressDTO.setOverseasCountry(nicRegDataDBO.getOverseasCountry());
////			addressDTO.setAddressUpdateFlag(this.convertBooleanToFlag(nicRegDataDBO.getAddressUpdatedFlag()));
////			addressDTO.setUpdateBy(nicRegDataDBO.getAddressUpdatedOfficerId());
////			addressDTO.setUpdateWkstnID(nicRegDataDBO.getAddressUpdatedWkstnId());
////			addressDTO.setUpdateDate(nicRegDataDBO.getAddressUpdatedTime());
//		}
//		return addressDTO;
//	}
//	public List<Relationship> parseRelationshipDTOList(NicRegistrationData nicRegDataDBO) {
//		List<Relationship> relationships = new ArrayList<Relationship>();
//		if (nicRegDataDBO!=null) {
//			//get father
//			if (StringUtils.isNotBlank(nicRegDataDBO.getFatherFirstname()) || 
//					StringUtils.isNotBlank(nicRegDataDBO.getFatherSurname())) { 
////					StringUtils.isNotBlank(nicRegDataDBO.getFatherNin()) ) 
//				Relationship relationshipDTO = new Relationship();
//				relationshipDTO.setRelationshipType("FATHER");
//				
//				//06 Jan 2013 (chris): to swap firstname, surname in relationshipDTO
//				//relationshipDTO.setSurname(nicRegDataDBO.getFatherName());
//				//relationshipDTO.setFirstname(nicRegDataDBO.getFatherSurname());
//				relationshipDTO.setSurname(nicRegDataDBO.getFatherSurname());
//				relationshipDTO.setFirstname(nicRegDataDBO.getFatherFirstname());
//				
////				relationshipDTO.setNin(nicRegDataDBO.getFatherNin());
//				relationships.add(relationshipDTO);
//			}
//			//get mother
//			if (StringUtils.isNotBlank(nicRegDataDBO.getMotherFirstname()) || 
//					StringUtils.isNotBlank(nicRegDataDBO.getMotherSurname())) { 
////					StringUtils.isNotBlank(nicRegDataDBO.getMotherNin()) ) {
//				Relationship relationshipDTO = new Relationship();
//				relationshipDTO.setRelationshipType("MOTHER");
//				
//				//06 Jan 2013 (chris): to swap firstname, surname in relationshipDTO
//				//relationshipDTO.setSurname(nicRegDataDBO.getMotherName());
//				//relationshipDTO.setFirstname(nicRegDataDBO.getMotherSurname());
//				relationshipDTO.setSurname(nicRegDataDBO.getMotherSurname());
//				relationshipDTO.setFirstname(nicRegDataDBO.getMotherFirstname());
//				
////				relationshipDTO.setNin(nicRegDataDBO.getMotherNin());
//				relationships.add(relationshipDTO);
//			}
//			//get spouse
//			if (StringUtils.isNotBlank(nicRegDataDBO.getSpouseFirstname()) || 
//					StringUtils.isNotBlank(nicRegDataDBO.getSpouseSurname())) { 
////					StringUtils.isNotBlank(nicRegDataDBO.getSpouseNin()) ) {
//				Relationship relationshipDTO = new Relationship();
//				relationshipDTO.setRelationshipType("SPOUSE");
//				
//				//06 Jan 2013 (chris): to swap firstname, surname in relationshipDTO
//				//relationshipDTO.setSurname(nicRegDataDBO.getSpouseName());
//				//relationshipDTO.setFirstname(nicRegDataDBO.getSpouseSurname());
//				relationshipDTO.setSurname(nicRegDataDBO.getSpouseSurname());
//				relationshipDTO.setFirstname(nicRegDataDBO.getSpouseFirstname());
//				
////				relationshipDTO.setNin(nicRegDataDBO.getSpouseNin());
//				relationships.add(relationshipDTO);
//			}
//		}
//		return relationships;
//	}
	
	//Facial DTO to DBO
	public List<NicTransactionAttachment> parseNicTransDocDBOForFacial(String transactionId, FacialInfo facialInfoDTO, NicTransaction nicTransaction) {
		List<NicTransactionAttachment> facialDocDBOList = new ArrayList<NicTransactionAttachment>();
		if (facialInfoDTO!=null && CollectionUtils.isNotEmpty(facialInfoDTO.getFacialImages())) {
			boolean createThumbnail = true;
			byte[] photo = null;
			for (FacialImage facialImage : facialInfoDTO.getFacialImages()) {
				NicTransactionAttachment facialDocDBO = new NicTransactionAttachment();
//				facialDocDBO.setNicTransaction(nicTransaction);
				facialDocDBO.setTransactionId(transactionId);
				facialDocDBO.setDocType(facialImage.getDocType());
				facialDocDBO.setSerialNo(DEFAULT_SERIAL_NO);
				facialDocDBO.setDocData(facialImage.getFacialData());
				facialDocDBO.setCreateBy(nicTransaction.getCreateBy());
				facialDocDBO.setCreateDatetime(nicTransaction.getCreateDatetime());
				facialDocDBO.setCreateWkstnId(nicTransaction.getCreateWkstnId());
				//facialDocDBO.setUpdateBy(nicTransaction.getUpdateBy());
				//facialDocDBO.setUpdateDatetime(nicTransaction.getUpdateDatetime());
				//facialDocDBO.setUpdateWkstnId(nicTransaction.getUpdateWkstnId());
				facialDocDBOList.add(facialDocDBO);
				
				if (StringUtils.equals(DOC_TYPE_PHOTO_CAPTURE, facialImage.getDocType())) {
					photo = facialImage.getFacialData();
				}
				if (StringUtils.equals(DOC_TYPE_THUMBNAIL_CAPTURE, facialImage.getDocType())) {
					createThumbnail = false;
				}
			}
			if (createThumbnail && photo!=null) {
				byte[] thumbnail = null;
				try {
					thumbnail = ImageUtil.resizeImageByWidthHeight(photo, THUMBNAIL_FACE_WIDTH_DEFAULT, THUMBNAIL_FACE_HEIGHT_DEFAULT, THUMBNAIL_FACE_QUALITY_DEFAULT);
				} catch(IOException e) {
					
				}
				if (thumbnail!=null) {
					NicTransactionAttachment facialDocDBO = new NicTransactionAttachment();
//					facialDocDBO.setNicTransaction(nicTransaction);
					facialDocDBO.setTransactionId(transactionId);
					facialDocDBO.setDocType(DOC_TYPE_THUMBNAIL_CAPTURE);
					facialDocDBO.setSerialNo(DEFAULT_SERIAL_NO);
					facialDocDBO.setDocData(thumbnail);
					facialDocDBO.setCreateBy(nicTransaction.getCreateBy());
					facialDocDBO.setCreateDatetime(nicTransaction.getCreateDatetime());
					facialDocDBO.setCreateWkstnId(nicTransaction.getCreateWkstnId());
					facialDocDBOList.add(facialDocDBO);
				}
			}
		}
		return facialDocDBOList;
	}
	//Facial DBO to DTO
	public FacialInfo parseFacialInfoDTO(List<NicTransactionAttachment> facialDocDBOList) {
		FacialInfo facialInfoDTO = null;
		if (CollectionUtils.isNotEmpty(facialDocDBOList)) {
			facialInfoDTO = new FacialInfo();
			for (NicTransactionAttachment facialDocDBO : facialDocDBOList) {
				FacialImage facialImageDTO = new FacialImage();
				facialImageDTO.setDocType(facialDocDBO.getDocType());
				facialImageDTO.setFacialData(facialDocDBO.getDocData());
				facialInfoDTO.getFacialImages().add(facialImageDTO);
				
			}
		}
		return facialInfoDTO;
	}
	
	//Finger prints DTO to DBO
	public List<NicTransactionAttachment> parseNicTransDocDBOForFingerprint(String transactionId, FingerprintInfo fingerprintInfoDTO, NicTransaction nicTransaction) {
		List<NicTransactionAttachment> fpDocDBOList = new ArrayList<NicTransactionAttachment>();
		if (fingerprintInfoDTO!=null && CollectionUtils.isNotEmpty(fingerprintInfoDTO.getFingerprints())) {
			//FMS Template (cmlaf + pc2 + fmp5)
			if (fingerprintInfoDTO.getCmlafTemplate()!=null) {
				NicTransactionAttachment fpTplDBO = new NicTransactionAttachment();
//				fpTplDBO.setNicTransaction(nicTransaction);
				fpTplDBO.setTransactionId(transactionId);
				fpTplDBO.setDocType(DOC_TYPE_TPL);
				fpTplDBO.setSerialNo(DEFAULT_SERIAL_NO);
				fpTplDBO.setDocData(fingerprintInfoDTO.getCmlafTemplate());
				//fingerprintDTO.setPurpose(null));
				fpTplDBO.setCreateBy(nicTransaction.getCreateBy());
				fpTplDBO.setCreateDatetime(nicTransaction.getCreateDatetime());
				fpTplDBO.setCreateWkstnId(nicTransaction.getCreateWkstnId());
				//fpTplDBO.setUpdateBy(nicTransaction.getUpdateBy());
				//fpTplDBO.setUpdateDatetime(nicTransaction.getUpdateDatetime());
				//fpTplDBO.setUpdateWkstnId(nicTransaction.getUpdateWkstnId());
				fpDocDBOList.add(fpTplDBO);
			}
			
			//Fingerprint WSQ
			for (Fingerprint fingerprint : fingerprintInfoDTO.getFingerprints()) {
				if (fingerprint==null)	continue;
				NicTransactionAttachment fpDocDBO = new NicTransactionAttachment();
//				fpDocDBO.setNicTransaction(nicTransaction);
				fpDocDBO.setTransactionId(transactionId);
				fpDocDBO.setDocType(DOC_TYPE_FINGERPRINT);
				String fpSerialNo = StringUtils.leftPad(fingerprint.getFingerprintPosition(), 2, '0');
				fpDocDBO.setSerialNo(fpSerialNo);
				fpDocDBO.setDocData(fingerprint.getFingerprintData());
				//fingerprintDTO.setPurpose(null));
				fpDocDBO.setCreateBy(nicTransaction.getCreateBy());
				fpDocDBO.setCreateDatetime(nicTransaction.getCreateDatetime());
				fpDocDBO.setCreateWkstnId(nicTransaction.getCreateWkstnId());
				//fpDocDBO.setUpdateBy(nicTransaction.getUpdateBy());
				//fpDocDBO.setUpdateDatetime(nicTransaction.getUpdateDatetime());
				//fpDocDBO.setUpdateWkstnId(nicTransaction.getUpdateWkstnId());
				fpDocDBOList.add(fpDocDBO);
				
				///*StringUtils.equals(fingerprint.getFingerprintEncodeFlag(),"Y") &&*/ 
				if (fingerprint.getMinutiaData()!=null) {
					NicTransactionAttachment mnuDocDBO = new NicTransactionAttachment();
//					//mnuDocDBO.setNicTransaction(new NicTransaction());
//					//mnuDocDBO.getNicTransaction().setTransactionId(transactionId);
					mnuDocDBO.setTransactionId(transactionId);
					if (StringUtils.isNotBlank(fingerprint.getMinutiaFormat()))
						mnuDocDBO.setDocType(DOC_TYPE_MINUTIAETEMPLATE+"_"+fingerprint.getMinutiaFormat());
					else 
						mnuDocDBO.setDocType(DOC_TYPE_MINUTIAETEMPLATE);
					mnuDocDBO.setSerialNo(fpSerialNo);
					mnuDocDBO.setDocData(fingerprint.getMinutiaData());
					//mnuDTO.setPurpose(null));
					mnuDocDBO.setCreateBy(nicTransaction.getCreateBy());
					mnuDocDBO.setCreateDatetime(nicTransaction.getCreateDatetime());
					mnuDocDBO.setCreateWkstnId(nicTransaction.getCreateWkstnId());
					//mnuDocDBO.setUpdateBy(nicTransaction.getUpdateBy());
					//mnuDocDBO.setUpdateDatetime(nicTransaction.getUpdateDatetime());
					//mnuDocDBO.setUpdateWkstnId(nicTransaction.getUpdateWkstnId());
					fpDocDBOList.add(mnuDocDBO);
				}
			}
		}
		return fpDocDBOList;
	}
	
	private NicTransactionAttachment getMinutiaeTemplateDBO(List<NicTransactionAttachment> mnuDocDBOList, String serialNo) {
		NicTransactionAttachment mnuDBO = null;
		for (NicTransactionAttachment docDBO : mnuDocDBOList) {
			if (StringUtils.equals(serialNo, docDBO.getSerialNo())) {
				mnuDBO = docDBO;
				break;
			}
		}
		return mnuDBO;
	}
	
	//Finger prints DBO to DTO
	public FingerprintInfo parseFingerprintInfoDTO(List<NicTransactionAttachment> fpDocDBOList, List<NicTransactionAttachment> mnuDocDBOList, NicTransactionAttachment tplDocDBO) {
		FingerprintInfo fingerprintInfoDTO = new FingerprintInfo();
		for (int i=1; i<=10 ; i++) {
			for (NicTransactionAttachment fpDocDBO : fpDocDBOList) {
				int fingerPosition = Integer.parseInt(fpDocDBO.getSerialNo());
				if (i!=fingerPosition) continue;
				
				Fingerprint fingerprintDTO = new Fingerprint();
				fingerprintDTO.setFingerprintPosition(Integer.toString(fingerPosition));
				fingerprintDTO.setFingerprintData(fpDocDBO.getDocData());		
				NicTransactionAttachment mnuDocDBO = this.getMinutiaeTemplateDBO(mnuDocDBOList, fpDocDBO.getSerialNo());
				if (mnuDocDBO!=null) {
					String minutiaFormat = StringUtils.substringAfter(mnuDocDBO.getDocType(), DOC_TYPE_MINUTIAETEMPLATE+"_");
					//if (StringUtils.isBlank(minutiaFormat))		minutiaFormat = ""; //TODO to set default minutiaFormat if required
					fingerprintDTO.setMinutiaData(mnuDocDBO.getDocData());
					fingerprintDTO.setMinutiaFormat(minutiaFormat);
				}
				fingerprintInfoDTO.getFingerprints().add(fingerprintDTO);	
			}
		}

		if (tplDocDBO!=null) {
			fingerprintInfoDTO.setCmlafTemplate(tplDocDBO.getDocData());
		}

		return fingerprintInfoDTO;
	}
	
	//Signature DTO to DBO	
	public NicTransactionAttachment parseNicTransDocDBOForSignature(String transactionId, SignatureInfo signatureInfoDTO, NicTransaction nicTransaction) {
		NicTransactionAttachment nicSignDocDBO = null;
		if (signatureInfoDTO!=null && CollectionUtils.isNotEmpty(signatureInfoDTO.getSignatures())) { 
			nicSignDocDBO = new NicTransactionAttachment();
//			nicSignDocDBO.setNicTransaction(nicTransaction);
			nicSignDocDBO.setTransactionId(transactionId);
			nicSignDocDBO.setSerialNo(DEFAULT_SERIAL_NO);
			nicSignDocDBO.setDocType(DOC_TYPE_SIGNATURE);
			nicSignDocDBO.setDocData(signatureInfoDTO.getSignatures().get(0).getSignatureData());
	
			nicSignDocDBO.setCreateBy(nicTransaction.getCreateBy());
			nicSignDocDBO.setCreateDatetime(nicTransaction.getCreateDatetime());
			nicSignDocDBO.setCreateWkstnId(nicTransaction.getCreateWkstnId());
			//nicSignDocDBO.setUpdateBy(nicTransaction.getUpdateBy());
			//nicSignDocDBO.setUpdateDatetime(nicTransaction.getUpdateDatetime());
			//nicSignDocDBO.setUpdateWkstnId(nicTransaction.getUpdateWkstnId());
		}
		return nicSignDocDBO;
	}
	//Signature DBO to DTO
	public SignatureInfo parseSignatureInfoDTO(NicTransactionAttachment nicSignDocDBO) {
		SignatureInfo signatureInfoDTO = null;
		if (nicSignDocDBO!=null) { 
			signatureInfoDTO = new SignatureInfo();
			Signature signatureDTO = new Signature();
			signatureDTO.setDocType(nicSignDocDBO.getDocType());
			signatureDTO.setSignatureData(nicSignDocDBO.getDocData());
			signatureInfoDTO.getSignatures().add(signatureDTO);
		}
		return signatureInfoDTO;
	}
	
	//12 Aug 2013 Reference Document
	//Reference Document DTO to DBO
	public NicTransactionAttachment parseNicTransDocDBOForReferenceDocument(String transactionId, ReferenceDocument refDocDTO, NicTransaction nicTransaction) {
		NicTransactionAttachment nicRefDocDBO = null;
		if (refDocDTO!=null) {
			nicRefDocDBO = new NicTransactionAttachment();
//			nicRefDocDBO.setNicTransaction(nicTransaction);
			nicRefDocDBO.setTransactionId(transactionId);
			nicRefDocDBO.setDocType(refDocDTO.getDocType());
			nicRefDocDBO.setSerialNo(refDocDTO.getSerialNo());
			nicRefDocDBO.setDocData(refDocDTO.getDocData());
			//nicRefDocDBO.setPurpose(refDocDTO.getPurpose());
			//nicRefDocDBO.setStatus(refDocDTO.getStatus());
			nicRefDocDBO.setCreateBy(nicTransaction.getCreateBy());
			nicRefDocDBO.setCreateDatetime(nicTransaction.getCreateDatetime());
			nicRefDocDBO.setCreateWkstnId(nicTransaction.getCreateWkstnId());
			nicRefDocDBO.setRemarks(refDocDTO.getRemarks());
			//nicRefDocDBO.setUpdateBy(nicTransaction.getUpdateBy());
			//nicRefDocDBO.setUpdateDatetime(nicTransaction.getUpdateDatetime());
			//nicRefDocDBO.setUpdateWkstnId(nicTransaction.getUpdateWkstnId());
		}
		return nicRefDocDBO;
	}
	//Reference Document DBO to DTO
	public List<ReferenceDocument> parseReferenceDocumentDTO(List<NicTransactionAttachment> nicRefDocDBOList) 
	{
		List<ReferenceDocument> referenceDocumentDTOList = new ArrayList<ReferenceDocument>();
		if (CollectionUtils.isNotEmpty(nicRefDocDBOList)) {
			for (NicTransactionAttachment nicRefDocDBO : nicRefDocDBOList) {
				ReferenceDocument refDocDTO = new ReferenceDocument();
				refDocDTO.setDocType(nicRefDocDBO.getDocType());
				refDocDTO.setSerialNo(nicRefDocDBO.getSerialNo());
				refDocDTO.setDocData(nicRefDocDBO.getDocData());
				//refDocDTO.setPurpose(nicRefDocDBO.getPurpose());
				//refDocDTO.setStatus(nicRefDocDBO.getStatus());
				refDocDTO.setCreateBy(nicRefDocDBO.getCreateBy());
				refDocDTO.setCreateDateTime(nicRefDocDBO.getCreateDatetime());
				refDocDTO.setCreateWkstnID(nicRefDocDBO.getCreateWkstnId());
				refDocDTO.setUpdateBy(nicRefDocDBO.getUpdateBy());
				refDocDTO.setUpdateDateTime(nicRefDocDBO.getUpdateDatetime());
				refDocDTO.setUpdateWkstnID(nicRefDocDBO.getUpdateWkstnId());
				referenceDocumentDTOList.add(refDocDTO);
			}
		}
		return referenceDocumentDTOList;
	}
	

	//payment DTO to DBO
	public NicTransactionPayment parseNicTransPaymentDBO(String transactionId, PaymentInfo paymentInfoDTO) {
		NicTransactionPayment nicTransPaymentDBO = null;
		if (paymentInfoDTO!=null) {
			nicTransPaymentDBO = new NicTransactionPayment();
			nicTransPaymentDBO.setTransactionId(transactionId);
			nicTransPaymentDBO.setNicTransaction(new NicTransaction(transactionId));
			nicTransPaymentDBO.setPaymentId(paymentInfoDTO.getPaymentID());
			nicTransPaymentDBO.setBalance(paymentInfoDTO.getBalance());
			nicTransPaymentDBO.setCashReceived(paymentInfoDTO.getCashReceived());
			nicTransPaymentDBO.setFeeAmount(paymentInfoDTO.getFeeAmount());
			nicTransPaymentDBO.setNoOfTimeLost(paymentInfoDTO.getNoOfTimeLost());
			nicTransPaymentDBO.setPaymentAmount(paymentInfoDTO.getPaymentAmount());		
			nicTransPaymentDBO.setPaymentStatus(paymentInfoDTO.getPaymentStatus());
			nicTransPaymentDBO.setPaymentDatetime(paymentInfoDTO.getPaymentDateTime());
			nicTransPaymentDBO.setReceiptId(paymentInfoDTO.getReceiptID()); //[CR32]
			nicTransPaymentDBO.setReduceRateAmount(paymentInfoDTO.getReduceRateAmount());
			nicTransPaymentDBO.setReduceRateFlag(this.convertFlagToBoolean(paymentInfoDTO.getReduceRateFlag()));
			nicTransPaymentDBO.setWaiverFlag(this.convertFlagToBoolean(paymentInfoDTO.getWaiverFlag()));
			nicTransPaymentDBO.setWaiverOfficerId(paymentInfoDTO.getWaiverOfficerID());
			nicTransPaymentDBO.setWaiverReason(paymentInfoDTO.getWaiverReason());
			
			nicTransPaymentDBO.setCollectionOfficerId(paymentInfoDTO.getCollectOfficerID());
			nicTransPaymentDBO.setCreateBy(paymentInfoDTO.getCreateBy());
			nicTransPaymentDBO.setCreateDatetime(paymentInfoDTO.getCreateDateTime());
			nicTransPaymentDBO.setCreateWkstnId(paymentInfoDTO.getCreateWkstnID());
			nicTransPaymentDBO.setUpdateBy(paymentInfoDTO.getUpdateBy());
			nicTransPaymentDBO.setUpdateDatetime(paymentInfoDTO.getUpdateDateTime());
			nicTransPaymentDBO.setUpdateWkstnId(paymentInfoDTO.getUpdateWkstnID());
		}
		return nicTransPaymentDBO;
	}
	
	//payment DBO to DTO
	public PaymentInfo parsePaymentInfoDTO(NicTransactionPayment nicTransPaymentDBO ) {
		PaymentInfo paymentInfoDTO = null;
		if (nicTransPaymentDBO!=null) {
			paymentInfoDTO = new PaymentInfo();
			paymentInfoDTO.setPaymentID(nicTransPaymentDBO.getPaymentId());
			if (nicTransPaymentDBO.getBalance()!=null)
				paymentInfoDTO.setBalance(nicTransPaymentDBO.getBalance());
			if (nicTransPaymentDBO.getCashReceived()!=null)
				paymentInfoDTO.setCashReceived(nicTransPaymentDBO.getCashReceived());
			paymentInfoDTO.setCollectOfficerID(nicTransPaymentDBO.getCollectionOfficerId());
			if (nicTransPaymentDBO.getFeeAmount()!=null)
				paymentInfoDTO.setFeeAmount(nicTransPaymentDBO.getFeeAmount());
			if (nicTransPaymentDBO.getNoOfTimeLost()!=null)
				paymentInfoDTO.setNoOfTimeLost(nicTransPaymentDBO.getNoOfTimeLost());
			if (nicTransPaymentDBO.getPaymentAmount()!=null)
				paymentInfoDTO.setPaymentAmount(nicTransPaymentDBO.getPaymentAmount());	
			
			paymentInfoDTO.setPaymentStatus(nicTransPaymentDBO.getPaymentStatus());
			paymentInfoDTO.setPaymentDateTime(nicTransPaymentDBO.getPaymentDatetime());
			paymentInfoDTO.setReceiptID(nicTransPaymentDBO.getReceiptId()); //[CR32]
			if (nicTransPaymentDBO.getReduceRateAmount()!=null)
				paymentInfoDTO.setReduceRateAmount(nicTransPaymentDBO.getReduceRateAmount());
			paymentInfoDTO.setReduceRateFlag(this.convertBooleanToFlag(nicTransPaymentDBO.getReduceRateFlag()));
			paymentInfoDTO.setWaiverFlag(this.convertBooleanToFlag(nicTransPaymentDBO.getWaiverFlag()));
			paymentInfoDTO.setWaiverOfficerID(nicTransPaymentDBO.getWaiverOfficerId());
			paymentInfoDTO.setWaiverReason(nicTransPaymentDBO.getWaiverReason());
			
			paymentInfoDTO.setCreateBy(nicTransPaymentDBO.getCreateBy());
			paymentInfoDTO.setCreateDateTime(nicTransPaymentDBO.getCreateDatetime());
			paymentInfoDTO.setCreateWkstnID(nicTransPaymentDBO.getCreateWkstnId());
			paymentInfoDTO.setUpdateBy(nicTransPaymentDBO.getUpdateBy());
			paymentInfoDTO.setUpdateDateTime(nicTransPaymentDBO.getUpdateDatetime());
			paymentInfoDTO.setUpdateWkstnID(nicTransPaymentDBO.getUpdateWkstnId());
		}
		return paymentInfoDTO;
	}
	
	//Issuance Data DTO to DBO
//	public NicIssuanceData parseNicIssuanceDataDBO(String transactionId, IssuanceData issuanceDataDTO) {
//		NicIssuanceData nicIssDataDBO = null;
//		if (issuanceDataDTO!=null) {
//			nicIssDataDBO = new NicIssuanceData();
//			nicIssDataDBO.setNicTransaction(new NicTransaction(transactionId));
//			nicIssDataDBO.setPackageId(issuanceDataDTO.getPackageID());
//			nicIssDataDBO.setPackageSequence(issuanceDataDTO.getPackageSequence());//13 Sep 2013 (chris)
//			nicIssDataDBO.setPackageDate(issuanceDataDTO.getPackageDate());//13 Sep 2013 (chris)
//			nicIssDataDBO.setDispatchId(issuanceDataDTO.getDispatchID());//13 Sep 2013 (chris)
//			nicIssDataDBO.setCcn(issuanceDataDTO.getCcn());
//			nicIssDataDBO.setSamKeyVersion(Short.parseShort(issuanceDataDTO.getSamKeyVersion()));
//			nicIssDataDBO.setCardStatus(issuanceDataDTO.getCardStatus());
//			nicIssDataDBO.setNin(issuanceDataDTO.getNin());
//			nicIssDataDBO.setDateOfIssue(issuanceDataDTO.getDateOfIssue());
//	
//			nicIssDataDBO.setIssuanceDecision(issuanceDataDTO.getIssuanceDecision());
//			nicIssDataDBO.setIssuanceOfficerId(issuanceDataDTO.getIssuanceOfficerID());
//			nicIssDataDBO.setCollectionDate(issuanceDataDTO.getCollectionDate());
//				
//			nicIssDataDBO.setCreateBy(issuanceDataDTO.getCreateBy());
//			nicIssDataDBO.setCreateDate(issuanceDataDTO.getCreateDate());
//			nicIssDataDBO.setCreateWkstnId(issuanceDataDTO.getCreateWkstnID());
//			nicIssDataDBO.setUpdateBy(issuanceDataDTO.getUpdateBy());
//			nicIssDataDBO.setUpdateDate(issuanceDataDTO.getUpdateDate());
//			nicIssDataDBO.setUpdateWkstnId(issuanceDataDTO.getUpdateWkstnID());
//		}
//		return nicIssDataDBO;
//	}

	//Issuance Data DBO to DTO
	public IssuanceData parseIssuanceDataDTO(NicDocumentData dbo) { //(NicIssuanceData nicIssDataDBO) {
		IssuanceData dto = null;
		if (dbo!=null) {
			dto = new IssuanceData();
			dto.setTransactionID(dbo.getId().getTransactionId()); 
			dto.setPassportNo(dbo.getId().getPassportNo());
			
			dto.setChipSerialNo(dbo.getChipSerialNo());
			dto.setPrintingSite(dbo.getPrintingSite());
			dto.setDateOfIssue(dbo.getDateOfIssue());
			dto.setDateOfExpiry(dbo.getDateOfExpiry());			
			dto.setStatus(dbo.getStatus());
			dto.setStatusUpdateTime(dbo.getStatusUpdateTime());
			
			dto.setPackageID(dbo.getPackageId());
			dto.setPackageDateTime(dbo.getPackageDatetime());
			dto.setDispatchID(dbo.getDispatchId());
			dto.setDispatchDateTime(dbo.getDispatchDatetime());
			
			dto.setIssuedFlag(this.convertBooleanToFlag(dbo.getIssuedFlag()));
			dto.setActiveFlag(this.convertBooleanToFlag(dbo.getActiveFlag()));
			dto.setReceiveBy(dbo.getReceiveBy());
			dto.setReceiveDateTime(dbo.getReceiveDatetime());
			dto.setIssueBy(dbo.getIssueBy());
			dto.setIssueDateTime(dbo.getIssueDatetime());
			dto.setRejectBy(dbo.getRejectBy());
			dto.setRejectDateTime(dbo.getRejectDatetime());
			dto.setCancelBy(dbo.getCancelBy());
			dto.setCancelDateTime(dbo.getCancelDatetime());
			
			dto.setCreateBy(dbo.getCreateBy());
			dto.setCreateDateTime(dbo.getCreateDatetime());
			dto.setCreateWkstnID(dbo.getCreateWkstnId());
			dto.setUpdateBy(dbo.getUpdateBy());
			dto.setUpdateDateTime(dbo.getUpdateDatetime());
			dto.setUpdateWkstnID(dbo.getUpdateWkstnId());
			
			dto.setSyncStatus(dbo.getSyncStatus());
			dto.setLastSyncDateTime(dbo.getLastSyncDatetime());
		}
		return dto;
	}
	
	public PaymentDetail parsePaymentDetailDataDTO(NicTransactionPaymentDetail dbo) { //(NicIssuanceData nicIssDataDBO) {
		PaymentDetail dto = null;
		if (dbo!=null) {
			dto = new PaymentDetail();
			dto.setPaymentID(dbo.getPaymentId());
			dto.setCreateBy(dbo.getCreateBy());
			dto.setCreateDate(dbo.getCreateDate());
			dto.setCreateWsktnId(dbo.getCreateWsktnId());
			dto.setNote(dbo.getNote());
			dto.setPaymentAmount(dbo.getPaymentAmount());
			dto.setStatusFee(dbo.isStatusFee() ? "Y" : "N");
			dto.setSubTypePayment(dbo.getSubTypePayment());
			dto.setTypePayment(dbo.getTypePayment());
			dto.setUpdateBy(dbo.getUpdateBy());
			dto.setUpdateDate(dbo.getUpdateDate());
			dto.setUpdateWsktnId(dbo.getUpdateWsktnId());
		}
		return dto;
	}
	
	public NicTransactionPaymentDetail parseTransactionPaymentDetailDataDTO(PaymentDetail dbo) { //(NicIssuanceData nicIssDataDBO) {
		NicTransactionPaymentDetail dto = null;
		if (dbo!=null) {
			dto = new NicTransactionPaymentDetail();
			dto.setPaymentId(dbo.getPaymentID());
			dto.setCreateBy(dbo.getCreateBy());
			dto.setCreateDate(dbo.getCreateDate());
			dto.setCreateWsktnId(dbo.getCreateWsktnId());
			dto.setNote(dbo.getNote());
			dto.setPaymentAmount(dbo.getPaymentAmount());
			dto.setStatusFee(dbo.getStatusFee().equals("Y") ? true : false);
			dto.setSubTypePayment(dbo.getSubTypePayment());
			dto.setTypePayment(dbo.getTypePayment());
			dto.setUpdateBy(dbo.getUpdateBy());
			dto.setUpdateDate(dbo.getUpdateDate());
			dto.setUpdateWsktnId(dbo.getUpdateWsktnId());
		}
		return dto;
	}
	
	public List<IssuanceData> parseIssuanceDataDTO(List<NicDocumentData> dboList) {
		List<IssuanceData> dtoList = new ArrayList<IssuanceData>();
		if (CollectionUtils.isNotEmpty(dboList)) {
			for (NicDocumentData documentDataDBO : dboList) {
				IssuanceData issuanceDataDTO = this.parseIssuanceDataDTO(documentDataDBO);
				dtoList.add(issuanceDataDTO);
			}
		}
		return dtoList;
	}
	
	public List<PaymentDetail> parsePaymentDetailDataDTO(List<NicTransactionPaymentDetail> dboList) {
		List<PaymentDetail> dtoList = new ArrayList<PaymentDetail>();
		if (CollectionUtils.isNotEmpty(dboList)) {
			for (NicTransactionPaymentDetail documentDataDBO : dboList) {
				PaymentDetail issuanceDataDTO = this.parsePaymentDetailDataDTO(documentDataDBO);
				dtoList.add(issuanceDataDTO);
			}
		}
		return dtoList;
	}
	
	//Rejection Data DBO to DTO
	public RejectionData parseRejectionDataDTO(NicRejectionData nicRejectionDataDBO, String transactionStatus) {
		RejectionData rejectionDataDTO = null;
		if (nicRejectionDataDBO!=null) {
			rejectionDataDTO = new RejectionData();
			rejectionDataDTO.setTransactionID(nicRejectionDataDBO.getTransactionId());
			rejectionDataDTO.setTransactionStatus(transactionStatus);
			//rejectionDataDTO.setRejectionType(nicRejectionDataDBO.getRejectionType());
			rejectionDataDTO.setRejectReason(nicRejectionDataDBO.getRejectReason());
			rejectionDataDTO.setRejectRemarks(nicRejectionDataDBO.getRejectRemarks());
			rejectionDataDTO.setRejectDateTime(nicRejectionDataDBO.getRejectDatetime());
			rejectionDataDTO.setRejectBy(nicRejectionDataDBO.getRejectBy());
		}
		return rejectionDataDTO;
	}
	
	public List<RejectionData> parseRejectionDataDTO(List<NicRejectionData> dboList) {
		List<RejectionData> dtoList = new ArrayList<RejectionData>();
		if (CollectionUtils.isNotEmpty(dboList)) {
			for (NicRejectionData dbo : dboList) {
				RejectionData dto = this.parseRejectionDataDTO(dbo, TRANSACTION_STATUS_REJECT);
				dtoList.add(dto);
			}
		}
		return dtoList;
	}
	
	public TransactionLog parseTransactionLogDTO(NicTransactionLog nicTransactionLogDBO) {
		TransactionLog transactionLogDTO = null;
		if (nicTransactionLogDBO!=null) {
			transactionLogDTO = new TransactionLog();
			
			/**
			 * copy fields: logCreateTime, transactionStage, transactionStatus, siteCode, startTime, endTime, logData, nin
			 */
			//note: java.lang.IllegalArgumentException: Cannot invoke com.nec.asia.nic.dx.trans.TransactionLog.setLogInfo on bean class 'class com.nec.asia.nic.dx.trans.TransactionLog' - argument type mismatch - had objects of type "java.lang.String" but expected signature "com.nec.asia.nic.dx.common.LogInfo"
			//copyProperties(transactionLogDTO, nicTransactionLogDBO);
			transactionLogDTO.setTransactionID(nicTransactionLogDBO.getRefId());
			transactionLogDTO.setLogCreateTime(nicTransactionLogDBO.getLogCreateTime());
			transactionLogDTO.setTransactionStage(nicTransactionLogDBO.getTransactionStage());
			transactionLogDTO.setTransactionStatus(nicTransactionLogDBO.getTransactionStatus());
			transactionLogDTO.setSiteCode(nicTransactionLogDBO.getSiteCode());
			transactionLogDTO.setStartTime(nicTransactionLogDBO.getStartTime());
			transactionLogDTO.setEndTime(nicTransactionLogDBO.getEndTime());
			transactionLogDTO.setLogData(nicTransactionLogDBO.getLogData());
//			transactionLogDTO.setNin(nicTransactionLogDBO.getNin()); // 16 Jan 2014 (chris)
			
			transactionLogDTO.setWkstnID(nicTransactionLogDBO.getWkstnId());
			transactionLogDTO.setOfficerID(nicTransactionLogDBO.getOfficerId());
			
			String logInfoXml = nicTransactionLogDBO.getLogInfo();
			try {
				if (StringUtils.isNotBlank(logInfoXml)) {
					LogInfoDTO logInfoDBO = (LogInfoDTO) logInfoXmlConvertor.unmarshal(logInfoXml);
					LogInfo logInfo = new LogInfo();
					logInfo.setReason(logInfoDBO.getReason());
					logInfo.setRemark(logInfoDBO.getRemark());
					transactionLogDTO.setLogInfo(logInfo );
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return transactionLogDTO;
	}
	
	public LogInfoDTO parseLogInfoDTO(String logInfoXml) {
		LogInfoDTO logInfoDTO = new LogInfoDTO();
		try {
			logInfoDTO = (LogInfoDTO) logInfoXmlConvertor.unmarshal(logInfoXml);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return logInfoDTO;
	}
	
	public String parseLogInfoXml(LogInfoDTO logInfoDTO) {
		String logInfoXml = "";
		try {
			logInfoXml = (String) logInfoXmlConvertor.marshal(logInfoDTO);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return logInfoXml;
	}
	
	public List<TransactionLog> parseTransactionLogDTOList(List<NicTransactionLog> nicTransactionLogDBOList) {
		List<TransactionLog> transactionLogDTOList = null;
		
		if (CollectionUtils.isNotEmpty(nicTransactionLogDBOList)) {
			transactionLogDTOList = new ArrayList<TransactionLog>();
			for (NicTransactionLog nicTransactionLog : nicTransactionLogDBOList) {
				transactionLogDTOList.add(this.parseTransactionLogDTO(nicTransactionLog));
			}
		}
		return transactionLogDTOList;
	}
	
	//Transaction Data DTO to DBO
	public NicTransactionLog parseNicTransactionLogDBO(TransactionLog transactionLogDTO) {
		NicTransactionLog nicTransactionLogDBO = null;
		if (transactionLogDTO!=null) {
			nicTransactionLogDBO = new NicTransactionLog();
			/**
			 * copy fields: logCreateTime, transactionStage, transactionStatus, siteCode, startTime, endTime, logData
			 */
			// 16 Jan 2014 (chris)
			//copyProperties(nicTransactionLogDBO, transactionLogDTO);
			nicTransactionLogDBO.setLogCreateTime(transactionLogDTO.getLogCreateTime());
			nicTransactionLogDBO.setTransactionStage(transactionLogDTO.getTransactionStage());
			nicTransactionLogDBO.setTransactionStatus(transactionLogDTO.getTransactionStatus());
			nicTransactionLogDBO.setSiteCode(transactionLogDTO.getSiteCode());
			nicTransactionLogDBO.setStartTime(transactionLogDTO.getStartTime());
			nicTransactionLogDBO.setEndTime(transactionLogDTO.getEndTime());
			nicTransactionLogDBO.setLogData(transactionLogDTO.getLogData());
//			nicTransactionLogDBO.setNin(transactionLogDTO.getNin()); // 16 Jan 2014 (chris)
			
			nicTransactionLogDBO.setRefId(transactionLogDTO.getTransactionID());
			nicTransactionLogDBO.setWkstnId(transactionLogDTO.getWkstnID());
			nicTransactionLogDBO.setOfficerId(transactionLogDTO.getOfficerID());
			if (transactionLogDTO.getLogInfo()!=null) {
				LogInfoDTO logInfo = new LogInfoDTO();
				logInfo.setReason(transactionLogDTO.getLogInfo().getReason());
				logInfo.setRemark(transactionLogDTO.getLogInfo().getRemark());
				
				String logInfoXml = null;
				try {
					logInfoXml = logInfoXmlConvertor.marshal(logInfo);
				} catch (Exception e) {
					e.printStackTrace();
				}
				nicTransactionLogDBO.setLogInfo(logInfoXml);
			}
		}
		return nicTransactionLogDBO;
	}
	
	public List<NicTransactionLog> parseNicTransactionLogDBOList(List<TransactionLog> transactionLogDTOList) {
		List<NicTransactionLog> nicTransactionLogDBOList = null;
		
		if (CollectionUtils.isNotEmpty(transactionLogDTOList)) {
			nicTransactionLogDBOList = new ArrayList<NicTransactionLog>();
			for (TransactionLog transactionLog : transactionLogDTOList) {
				NicTransactionLog logDBO = this.parseNicTransactionLogDBO(transactionLog);
				if (logDBO!=null)
					nicTransactionLogDBOList.add(logDBO);
			}
		}
		return nicTransactionLogDBOList;
	}

	/**
	 * utility methods
	 * get document binary
	 */
	public byte[] getDocBinaryByType(NicTransaction nicTransaction, String docType) {
		byte[] docBinary = null;
		if (nicTransaction!=null) {
			if (CollectionUtils.isNotEmpty(nicTransaction.getNicTransactionAttachments())) {
				for (NicTransactionAttachment doc: nicTransaction.getNicTransactionAttachments()) {
					if (StringUtils.equals(docType, doc.getDocType())) {
						docBinary = doc.getDocData();
						break;
					}
				}
			}
		}
		return docBinary;
	}
}
