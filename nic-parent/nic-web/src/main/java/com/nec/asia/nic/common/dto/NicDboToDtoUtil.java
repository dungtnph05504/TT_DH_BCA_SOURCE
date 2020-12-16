package com.nec.asia.nic.common.dto;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;




//import com.nec.asia.applet.SpidHelper;
import com.nec.asia.nic.comp.trans.domain.NicRegistrationData;
import com.nec.asia.nic.comp.trans.domain.NicTransaction;
import com.nec.asia.nic.comp.trans.domain.NicTransactionAttachment;
import com.nec.asia.nic.comp.trans.domain.NicTransactionLog;
import com.nec.asia.nic.util.RegistrationConstants;
import com.nec.asia.nic.utils.HelperClass;
import com.nec.asia.nic.utils.StringUtil;

@Component("nicDboToDtoUtil")
public class NicDboToDtoUtil {

	public static final String DOC_TYPE_ENCODED_FACE = "PH_CHIP";
	public static final String DOC_TYPE_ENROLLED_FACE = "PH_CAP";
	public static final String DOC_TYPE_PRINTED_FACE = "PH_GREY";
	public static final String DOC_TYPE_THUMBNAIL_FACE = "TH_CAP";
	public static final String DOC_TYPE_THUMBNAIL_GREY_FACE = "TH_GREY";
	public static final String DOC_TYPE_THUMBNAIL_CHIP_FACE = "TH_CHIP";
	public static final String DOC_TYPE_SMALL_PORTRAIT_FACE = "PH_CLI";
	public static final String DOC_TYPE_SCAN_DOC_BIRTH_CERT = "SC_BIRTH_EXT";
	public static final String DOC_TYPE_SCAN_DOC_MARRIAGE_CERT = "SC_MAR_CERT";
	public static final String DOC_TYPE_FINGER_PRINT = "FP";
	public static final String DOC_TYPE_FINGER_PRINT_SIGN_FP = "SIGN_FP";
	public static final String DOC_TYPE_SIGN_IMAGE = "SIGNATURE";
	public static final String DOC_TYPE_USER_DECL = "SC_USER_DECL";

	public static final String TRANSACTION_STAGE_RIC_REGISTRATION	= NicTransactionLog.TRANSACTION_STAGE_RIC_REGISTRATION;
	public static final String TRANSACTION_STAGE_RIC_PEND_SUPERVISOR= NicTransactionLog.TRANSACTION_STAGE_RIC_PEND_SUPERVISOR;
	public static final String TRANSACTION_STAGE_RIC_VERIFICATION	= NicTransactionLog.TRANSACTION_STAGE_RIC_VERIFICATION;
	public static final String TRANSACTION_STAGE_RIC_EXC_HANDLING	= NicTransactionLog.TRANSACTION_STAGE_RIC_EXC_HANDLING;
	public static final String TRANSACTION_STAGE_RIC_PAYMENT		= NicTransactionLog.TRANSACTION_STAGE_RIC_PAYMENT;
	public static final String TRANSACTION_STAGE_RIC_ISSUANCE		= NicTransactionLog.TRANSACTION_STAGE_RIC_ISSUANCE;
	public static final String TRANSACTION_STAGE_RIC_CARD_ISSUED	= NicTransactionLog.TRANSACTION_STAGE_RIC_CARD_ISSUED;
	public static final String TRANSACTION_STAGE_RIC_CARD_REJECTED	= NicTransactionLog.TRANSACTION_STAGE_RIC_CARD_REJECTED;
	public static final String TRANSACTION_STAGE_RIC_REJECTED		= NicTransactionLog.TRANSACTION_STAGE_RIC_REJECTED;
	public static final String TRANSACTION_STAGE_RIC_REG_CANCELLED	= NicTransactionLog.TRANSACTION_STAGE_RIC_REG_CANCELLED;
	
	
	public RicNewRegistrationDTO getDto(NicTransaction nicTransaction) {
		RicNewRegistrationDTO dto = new RicNewRegistrationDTO();

		try {
			
			setFields(dto, nicTransaction);
			setDocumentData(dto, nicTransaction);
			
			dto.setPerson(getNinDetailsDTO(nicTransaction));
			//dto.setTransactionLogList(getTransactionLogDTOList(nicTransaction));
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return dto;
	}

	private void setDocumentData(RicNewRegistrationDTO dto,
			NicTransaction nicTransaction) {
		NicRegistrationData regData = nicTransaction.getNicRegistrationData();
		Set<NicTransactionAttachment> nicTransactionDocuments = nicTransaction.getNicTransactionAttachments();
		if (nicTransactionDocuments != null) {
			String[] fpVerify = new String[10];
			String[] fpIndicators = new String[10];
			String[] fpQty = new String[10];
//			if (!ListUtil.isNotNullAndNotEmpty(nicTransactionDocuments)) {
//				
//			}
			
			if (!StringUtil.isEmpty(regData.getFpIndicator()))
				fpIndicators = regData.getFpIndicator().split(",");
			else
				fpIndicators = null;
			if (!StringUtil.isEmpty(regData.getFpQuality()))
				fpQty = regData.getFpQuality().split(",");
			else
				fpQty = null;
//			if (!StringUtil.isEmpty(regData.getFpVerifyScore()))
//				fpVerify = regData.getFpVerifyScore().split(",");
//			else
				fpVerify = null;

			List<RicRegistrationDocumentDTO> scandocList = new ArrayList<RicRegistrationDocumentDTO>();
			for (NicTransactionAttachment document : nicTransactionDocuments) {
				if (document != null) {
					String decodedDocString = Base64
							.encodeBase64String(document.getDocData());
					if (RegistrationConstants.DOC_TYPE_ENCODED_FACE
							.equalsIgnoreCase(document
									.getDocType())) {
						RicRegistrationFacialDTO facialDTO = new RicRegistrationFacialDTO();
						facialDTO.setFace(decodedDocString);
						dto.setEncodedFace(facialDTO);
					} else if (RegistrationConstants.DOC_TYPE_ENROLLED_FACE
							.equalsIgnoreCase(document
									.getDocType())) {
						RicRegistrationFacialDTO facialDTO = new RicRegistrationFacialDTO();
						facialDTO.setFace(decodedDocString);
						facialDTO.setIndicator(regData.getFacialIndicator());
						dto.setEnrolledFace(facialDTO);
					} else if (RegistrationConstants.DOC_TYPE_PRINTED_FACE
							.equalsIgnoreCase(document
									.getDocType())) {
						RicRegistrationFacialDTO facialDTO = new RicRegistrationFacialDTO();
						facialDTO.setFace(decodedDocString);
						dto.setPrintedFace(facialDTO);
					} else if (RegistrationConstants.DOC_TYPE_THUMBNAIL_FACE
							.equalsIgnoreCase(document
									.getDocType())) {
						//RicRegistrationFacialDTO facialDTO = new RicRegistrationFacialDTO();
						//facialDTO.setFace(decodedDocString);
						//dto.setThumbNailFace(facialDTO);
					} else if (RegistrationConstants.DOC_TYPE_THUMBNAIL_GREY_FACE
							.equalsIgnoreCase(document
									.getDocType())) {
						//RicRegistrationFacialDTO facialDTO = new RicRegistrationFacialDTO();
						//facialDTO.setFace(decodedDocString);
						//dto.setThumbNailPrintedFace(facialDTO);
					} else if (RegistrationConstants.DOC_TYPE_THUMBNAIL_CHIP_FACE
							.equalsIgnoreCase(document
									.getDocType())) {
						//RicRegistrationFacialDTO facialDTO = new RicRegistrationFacialDTO();
						//facialDTO.setFace(decodedDocString);
						//dto.setThumbNailEncodeFace(facialDTO);
					} else if (RegistrationConstants.DOC_TYPE_FINGER_PRINT
							.equalsIgnoreCase(document
									.getDocType())) {
						RicRegistrationFingerPrintDTO fp = new RicRegistrationFingerPrintDTO();
//						fp.setFpJpg(Base64.encodeBase64String(SpidHelper
//								.convertImageFormat(document.getDocData(),
//										SpidHelper.IMAGE_WSQ,
//										SpidHelper.IMAGE_JPG)));
//						fp.setFp(decodedDocString);
//						fp.setMinutiaTemplate(Base64
//								.encodeBase64String(document.getMnuTemplate()));
						if(document.getDocData() != null) {
							fp.setFpJpg(".");
							fp.setFp(".");
						} else {
							fp.setFpJpg(null);
							fp.setFp(null);
						}
						
						if (fpIndicators != null)
							fp.setFpIndicator(getFpValue(fpIndicators, document
									.getSerialNo()));
						if (fpQty != null)
							fp.setFpQuality(getFpValue(fpQty, document
									.getSerialNo()));
						if (fpVerify != null)
							fp.setVerify(getFpValue(fpVerify, document
									.getSerialNo()));
						if (document.getSerialNo()
								.equals(RegistrationConstants.NUMBER_1)) {
							dto.setFingerPrint01(fp);
						} else if (document.getSerialNo()
								.equals(RegistrationConstants.NUMBER_2)) {
							dto.setFingerPrint02(fp);
						} else if (document.getSerialNo()
								.equals(RegistrationConstants.NUMBER_3)) {
							dto.setFingerPrint03(fp);
						} else if (document.getSerialNo()
								.equals(RegistrationConstants.NUMBER_4)) {
							dto.setFingerPrint04(fp);
						} else if (document.getSerialNo()
								.equals(RegistrationConstants.NUMBER_5)) {
							dto.setFingerPrint05(fp);
						} else if (document.getSerialNo()
								.equals(RegistrationConstants.NUMBER_6)) {
							dto.setFingerPrint06(fp);
						} else if (document.getSerialNo()
								.equals(RegistrationConstants.NUMBER_7)) {
							dto.setFingerPrint07(fp);
						} else if (document.getSerialNo()
								.equals(RegistrationConstants.NUMBER_8)) {
							dto.setFingerPrint08(fp);
						} else if (document.getSerialNo()
								.equals(RegistrationConstants.NUMBER_9)) {
							dto.setFingerPrint09(fp);
						} else if (document.getSerialNo()
								.equals(RegistrationConstants.NUMBER_10)) {
							dto.setFingerPrint10(fp);
						}

					} else if (RegistrationConstants.DOC_TYPE_FINGER_PRINT_SIGN_FP
							.equalsIgnoreCase(document
									.getDocType())) {
//						String decodedSignFp = Base64
//								.encodeBase64String(SpidHelper
//										.convertImageFormat(
//												document.getDocData(),
//												SpidHelper.IMAGE_WSQ,
//												SpidHelper.IMAGE_JPG));
//						dto.setSignFp(decodedSignFp);

						if(document.getDocData() != null) {
							dto.setSignFp(".");
						} else {
							dto.setSignFp(null);
						}
						
						dto.setSign(null);
						dto.setUseFpForSignYN(true);

					} else if (RegistrationConstants.DOC_TYPE_SIGN_IMAGE
							.equalsIgnoreCase(document
									.getDocType())
							) {
						dto.setSign(decodedDocString);
						dto.setSignFp(null);
						dto.setUseFpForSignYN(false);
					} else if (RegistrationConstants.DOC_TYPE_USER_DECL
							.equalsIgnoreCase(document
									.getDocType())
							&& document.getDocData() != null) {
						RicRegistrationDocumentDTO usrDecl = new RicRegistrationDocumentDTO();
						//usrDecl.setSignature(decodedDocString);
						//usrDecl.setDocument(decodedDocString);
						usrDecl.setDocumentName(document
								.getDocType());
						usrDecl.setSerialNumber(document.getSerialNo());
						dto.setUserDeclarationDoc(usrDecl);
					} else if (document.getDocType().startsWith("SC_")
							&& document.getDocData() != null) {
						boolean docAdded = false;
						if (scandocList != null && scandocList.size() > 0) {
							for (RicRegistrationDocumentDTO scanDto : scandocList) {
								if (scanDto.getType().equalsIgnoreCase(
										document.getDocType())										
										&& scanDto.getDocument() != null) {
									scanDto.getDocument().add(decodedDocString);
									docAdded = true;
									break;
								}
							}
						}
						if (!docAdded) {
							RicRegistrationDocumentDTO scandoc = new RicRegistrationDocumentDTO();
														if(!StringUtils.isEmpty(document.getDocType())) {
								scandoc.setType(StringUtils.substring(document.getDocType(),3));
							}
							scandoc.setDocumentId(document.getSerialNo());
							List<String> documentList = new ArrayList<String>();
							documentList.add(decodedDocString);
							scandoc.setDocument(documentList);
							scandocList.add(scandoc);
						}

					}
				}
			}
			if (fpIndicators != null) {
				formAmputatedFingers(fpIndicators, dto);
			}
			dto.setDocumentList(scandocList);
		}
	}

	public List<RicRegistrationDocumentDTO> getScanDocDto(
			List<NicTransactionAttachment> nicTransactionDocuments) {

		List<RicRegistrationDocumentDTO> scandocList = new ArrayList<RicRegistrationDocumentDTO>();
		for (NicTransactionAttachment document : nicTransactionDocuments) {
			if (document != null) {
				String decodedDocString = Base64.encodeBase64String(document
						.getDocData());

				if (document.getDocType().startsWith("SC_")
						&& document.getDocData() != null) {
					boolean docAdded = false;
					if (scandocList != null && scandocList.size() > 0) {
						for (RicRegistrationDocumentDTO scanDto : scandocList) {
							if (scanDto.getType().equalsIgnoreCase(
									document.getDocType())
//									&& scanDto.getDocumentName()
//											.equalsIgnoreCase(
//													document.getPurpose())
									&& scanDto.getDocument() != null) {
								scanDto.getDocument().add(decodedDocString);
								docAdded = true;
								break;
							}
						}
					}
					if (!docAdded) {
						RicRegistrationDocumentDTO scandoc = new RicRegistrationDocumentDTO();
//						scandoc.setDocumentName(document.getPurpose());
						if(!StringUtils.isEmpty(document.getDocType())) {
							scandoc.setType(StringUtils.substring(document.getDocType(),3));
						}
						scandoc.setDocumentId(document.getSerialNo());
						List<String> documentList = new ArrayList<String>();
						documentList.add(decodedDocString);
						scandoc.setDocument(documentList);
						scandocList.add(scandoc);
					}
				}
			}
		}

		return scandocList;
	}
	
	private String getFpValue(String[] valueArr, String serialNo) {
		String value = "";
		if (valueArr != null && valueArr.length > 0) {
			for (String str : valueArr) {
				String[] subStr = str.split("-");
				if (subStr != null && subStr.length > 1
						&& subStr[0].equals(serialNo)) {
					value = subStr[1];
					break;
				}
			}
		}
		return value;
	}

	private void formAmputatedFingers(String[] fpIndicators,
			RicNewRegistrationDTO dto) {
		if (getFpValue(fpIndicators, RegistrationConstants.NUMBER_1)
				.equals("A")) {
			dto.setFingerPrint01(new RicRegistrationFingerPrintDTO());
			dto.getFingerPrint01().setFpIndicator("A");
		}
		if (getFpValue(fpIndicators, RegistrationConstants.NUMBER_2)
				.equals("A")) {
			dto.setFingerPrint02(new RicRegistrationFingerPrintDTO());
			dto.getFingerPrint02().setFpIndicator("A");
		}
		if (getFpValue(fpIndicators, RegistrationConstants.NUMBER_3)
				.equals("A")) {
			dto.setFingerPrint03(new RicRegistrationFingerPrintDTO());
			dto.getFingerPrint03().setFpIndicator("A");
		}
		if (getFpValue(fpIndicators, RegistrationConstants.NUMBER_4)
				.equals("A")) {
			dto.setFingerPrint04(new RicRegistrationFingerPrintDTO());
			dto.getFingerPrint04().setFpIndicator("A");
		}
		if (getFpValue(fpIndicators, RegistrationConstants.NUMBER_5)
				.equals("A")) {
			dto.setFingerPrint05(new RicRegistrationFingerPrintDTO());
			dto.getFingerPrint05().setFpIndicator("A");
		}
		if (getFpValue(fpIndicators, RegistrationConstants.NUMBER_6)
				.equals("A")) {
			dto.setFingerPrint06(new RicRegistrationFingerPrintDTO());
			dto.getFingerPrint06().setFpIndicator("A");
		}
		if (getFpValue(fpIndicators, RegistrationConstants.NUMBER_7)
				.equals("A")) {
			dto.setFingerPrint07(new RicRegistrationFingerPrintDTO());
			dto.getFingerPrint07().setFpIndicator("A");
		}
		if (getFpValue(fpIndicators, RegistrationConstants.NUMBER_8)
				.equals("A")) {
			dto.setFingerPrint08(new RicRegistrationFingerPrintDTO());
			dto.getFingerPrint08().setFpIndicator("A");
		}
		if (getFpValue(fpIndicators, RegistrationConstants.NUMBER_9)
				.equals("A")) {
			dto.setFingerPrint09(new RicRegistrationFingerPrintDTO());
			dto.getFingerPrint09().setFpIndicator("A");
		}
		if (getFpValue(fpIndicators, RegistrationConstants.NUMBER_10).equals(
				"A")) {
			dto.setFingerPrint10(new RicRegistrationFingerPrintDTO());
			dto.getFingerPrint10().setFpIndicator("A");
		}

	}
	
	private boolean getBoolean(String facialIndicator) {
		return StringUtils.equalsIgnoreCase(facialIndicator, "Y");
	}

	private void setFields(RicNewRegistrationDTO dto,
			NicTransaction nicTransaction) {
		NicRegistrationData regData = nicTransaction.getNicRegistrationData();
		dto.setPhone(regData.getContactNo());
		dto.setEmail(regData.getEmail());

		dto.setRegSiteCode(nicTransaction.getRegSiteCode());
		dto.setIssueSite(nicTransaction.getIssSiteCode());
		
//		dto.setAddressChangeFlag(regData.getAddressUpdatedFlag());

		dto.setTransactionId(nicTransaction.getTransactionId());
		dto.setTransactionType(nicTransaction.getTransactionType());
//		dto.setTransactionSubType(nicTransaction.getTransactionSubtype());
		
		dto.setTransactionStatus(nicTransaction.getTransactionStatus());
		dto.setTrnasactionStartDate(HelperClass.getCalendarWithoutTimeValueAsString(nicTransaction.getCreateDatetime()));
		dto.setFpEncode(regData.getFpEncode());
//		if (regData.getAddressUpdatedFlag()) {
//			dto.setUpdateTime(HelperClass
//					.getCalendarWithTimeValueAsString(regData
//							.getAddressUpdatedTime()));
//			dto.setUpdatedBy(regData.getAddressUpdatedOfficerId());
//		}		
//		
		
		dto.setTown(regData.getAddressLine4());
//		dto.setDistrict(regData.getAddressLine5());
		dto.setLocality(regData.getAddressLine3());			
		dto.setOldAddress1(regData.getAddressLine1());
		dto.setOldAddress2(regData.getAddressLine2());
		dto.setOldAddress3(regData.getAddressLine3());
		dto.setOldAddress4(regData.getAddressLine4());
//		dto.setOldAddress5(regData.getAddressLine5());
//		dto.setOldAddress6(regData.getAddressLine6());
//		dto.setOldOverseasAddress(regData.getOverseasAddress());
//		dto.setOldOverseasAddressCountry(regData.getOverseasCountry());
//		dto.setOldPreferredMailingAddress(regData.getPreferredMailingAddress());
//		dto.setOverseasCountry(regData.getOverseasCountry());

//		if (regData.getFpVerifyFlag() != null && regData.getFpVerifyFlag()) {
//			dto.setFpVerified(regData.getFpVerifyFlag());
//		} else {
//			dto.setFpVerified(false);
//		}
//		if (regData.getCardVerifyFlag() != null && regData.getCardVerifyFlag()) {
//			dto.setCardVerified(regData.getCardVerifyFlag());
//		} else {
//			dto.setCardVerified(false);
//		}
//		
	}

	private NinDetailsDTO getNinDetailsDTO(NicTransaction nicTransaction) {
		NicRegistrationData registration = nicTransaction.getNicRegistrationData();
		NinDetailsDTO ninDetailsDTO = new NinDetailsDTO();
		ninDetailsDTO.setAddress1(registration.getAddressLine1());
		ninDetailsDTO.setAddress2(registration.getAddressLine2());
		ninDetailsDTO.setAddress3(registration.getAddressLine3());
		ninDetailsDTO.setAddress4(registration.getAddressLine4());
//		ninDetailsDTO.setAddress5(registration.getAddressLine5());
//		ninDetailsDTO.setAddress6(registration.getAddressLine6());
//		ninDetailsDTO.setOverseasAddress(registration.getOverseasAddress());
//		ninDetailsDTO.setOverseasAddressCountry(registration.getOverseasCountry());
//		ninDetailsDTO.setPreferredMailingAddress(registration.getPreferredMailingAddress());
		ninDetailsDTO.setDob(HelperClass.getCalendarWithoutTimeValueAsString(registration.getDateOfBirth()));
		ninDetailsDTO.setFatherName(registration.getFatherFullname());
//		ninDetailsDTO.setFatherSurname(registration.getFatherSurname());
		ninDetailsDTO.setFirstname(registration.getFirstnameFull());
		ninDetailsDTO.setGender(registration.getGender());
		ninDetailsDTO.setMaritalStatus(registration.getMaritalStatus());
//		ninDetailsDTO.setMaidenName(registration.getSurnameBirthFull());
		ninDetailsDTO.setMotherName(registration.getMotherFullname());
//		ninDetailsDTO.setMotherSurname(registration.getMotherSurname());
//		ninDetailsDTO.setNin(registration.getNin());
		ninDetailsDTO.setSurname(registration.getSurnameFull());
//		ninDetailsDTO.setSurnameBirth(registration.getSurnameBirthFull());
		ninDetailsDTO.setGender(registration.getGender());
		ninDetailsDTO.setMaritalStatus(registration.getMaritalStatus());
//		if (registration.getAddressUpdatedFlag()) {
//			ninDetailsDTO.setUpdatedBy(registration.getAddressUpdatedOfficerId());
//			ninDetailsDTO.setUpdateDateTime(HelperClass
//					.getCalendarWithTimeValueAsString(registration
//							.getAddressUpdatedTime()));
//		}
		ninDetailsDTO.setFirstname(registration.getFirstnameFull());
		ninDetailsDTO.setSurname(registration.getSurnameFull());
//		ninDetailsDTO.setSurnameBirth(registration.getSurnameBirthFull());
//		ninDetailsDTO.setOptionSurname(registration.getOptionSurname());

		ninDetailsDTO.setFirstNameLine1(registration.getFirstnameLine1());
		ninDetailsDTO.setSurnameLine1(registration.getSurnameLine1());
//		ninDetailsDTO.setSurnameBirthLine1(registration.getSurnameBirthLine1());

		ninDetailsDTO.setFirstNameLine2(registration.getFirstnameLine2());
		ninDetailsDTO.setSurnameLine2(registration.getSurnameLine2());
//		ninDetailsDTO.setSurnameBirthLine2(registration.getSurnameBirthLine2());
		if (registration.getFirstnameLenExceedFlag())
			ninDetailsDTO.setFirstNameLine3(HelperClass.formLine3(
					registration.getFirstnameLine1()
							+ registration.getFirstnameLine2(),
					registration.getFirstnameFull()));
		if (registration.getSurnameLenExceedFlag())
			ninDetailsDTO.setSurnameLine3(HelperClass.formLine3(
					registration.getSurnameLine1()
							+ registration.getSurnameLine2(),
					registration.getSurnameFull()));
//		if (registration.getSurnameBirthLenExceedFlag())
//			ninDetailsDTO.setSurnameBirthLine3(HelperClass.formLine3(
//					registration.getSurnameBirthLine1()
//							+ registration.getSurnameBirthLine2(),
//					registration.getSurnameBirthFull()));

		ninDetailsDTO.setFirstNameLenExceedFlag(registration
				.getFirstnameLenExceedFlag());
		ninDetailsDTO.setSurnameLenExceedFlag(registration
				.getSurnameLenExceedFlag());
//		ninDetailsDTO.setSurnameAtBirthLenExceedFlag(registration
//				.getSurnameBirthLenExceedFlag());
//		ninDetailsDTO.setOptionSurname(registration.getOptionSurname());
		return ninDetailsDTO;
	}

	public List<RicTransactionLogDTO> getTransactionLogs(
			List<NicTransactionLog> txnLogs) {
		
		List<RicTransactionLogDTO> logDtoList = new ArrayList<RicTransactionLogDTO>();
		for(NicTransactionLog txnLog : txnLogs){
			RicTransactionLogDTO logDto = new RicTransactionLogDTO();

			logDto.setLogId(((Long)txnLog.getLogId()).intValue());
			logDto.setTransactionId(txnLog.getRefId());
			logDto.setRicSitecode(txnLog.getSiteCode());
			logDto.setCounterId(txnLog.getWkstnId());
			logDto.setTransactionStage(txnLog.getTransactionStage());
			logDto.setTransactionStatus(txnLog.getTransactionStatus());
			logDto.setUserId(txnLog.getOfficerId());
			
			String updateTime = HelperClass.getCalendarWithTimeValueAsString(txnLog.getLogCreateTime());
			if(updateTime == "") 
				updateTime = HelperClass.getCalendarWithTimeValueAsString(txnLog.getStartTime());
			
			logDto.setUpdateTime(updateTime);
			logDto.setRemarks(txnLog.getLogInfo());
			
			logDtoList.add(logDto);
		}

		return logDtoList;
	}

	public List<RicTransactionLogDTO> getRicTransactionLogs(
			List<NicTransactionLog> txnLogs) {
		String[] ricTxnStages = new String[] {
				TRANSACTION_STAGE_RIC_REGISTRATION,
				TRANSACTION_STAGE_RIC_PEND_SUPERVISOR,
				TRANSACTION_STAGE_RIC_VERIFICATION,
				TRANSACTION_STAGE_RIC_EXC_HANDLING,
				TRANSACTION_STAGE_RIC_PAYMENT,
				TRANSACTION_STAGE_RIC_ISSUANCE,
				TRANSACTION_STAGE_RIC_CARD_ISSUED,
				TRANSACTION_STAGE_RIC_CARD_REJECTED,
				TRANSACTION_STAGE_RIC_REJECTED,
				TRANSACTION_STAGE_RIC_REG_CANCELLED
		};
		
		List<String> stages = Arrays.asList(ricTxnStages);
		List<RicTransactionLogDTO> logDtoList = new ArrayList<RicTransactionLogDTO>();
		for(NicTransactionLog txnLog : txnLogs){
			if(!stages.contains(txnLog.getTransactionStage())) continue;
			
			RicTransactionLogDTO logDto = new RicTransactionLogDTO();

			logDto.setLogId(((Long)txnLog.getLogId()).intValue());
			logDto.setTransactionId(txnLog.getRefId());
			logDto.setRicSitecode(txnLog.getSiteCode());
			logDto.setCounterId(txnLog.getWkstnId());
			logDto.setTransactionStage(txnLog.getTransactionStage());
			logDto.setTransactionStatus(txnLog.getTransactionStatus());
			logDto.setUserId(txnLog.getOfficerId());
			
			String updateTime = HelperClass.getCalendarWithTimeValueAsString(txnLog.getLogCreateTime());
			if(updateTime == "") 
				updateTime = HelperClass.getCalendarWithTimeValueAsString(txnLog.getStartTime());
			
			logDto.setUpdateTime(updateTime);
			logDto.setRemarks(txnLog.getLogInfo());
			
			logDtoList.add(logDto);
		}

		return logDtoList;
	}


}
