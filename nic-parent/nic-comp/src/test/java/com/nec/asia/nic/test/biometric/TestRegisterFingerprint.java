package com.nec.asia.nic.test.biometric;

import java.util.HashSet;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.nec.asia.baf.spec.transfer.biometrics.facial.BioFaceInfoDto;
import com.nec.asia.baf.spec.transfer.biometrics.fingerprint.BioFingerprintInfoDto;
import com.nec.asia.baf.spec.transfer.biometrics.fingerprint.BioFingerprintSetDto;
import com.nec.asia.baf.spec.transfer.biometrics.fingerprint.BioFpBiometricTemplateDto;
import com.nec.asia.baf.spec.transfer.biometrics.fingerprint.BioFpOriginalImageDto;
import com.nec.asia.baf.spec.transfer.biometrics.registration.BioRegistrationDto;
import com.nec.asia.idserver.agent.payload.model.IdserverFaceInfo;
import com.nec.asia.idserver.agent.payload.model.IdserverImageFace;
import com.nec.asia.idserver.agent.payload.model.IdserverImageFingerprint;
import com.nec.asia.idserver.agent.payload.model.IdserverPersonDetail;
import com.nec.asia.idserver.agent.payload.model.IdserverRegistration;
import com.nec.asia.idserver.agent.payload.model.type.IdserverFaceFormat;
import com.nec.asia.idserver.agent.payload.model.type.IdserverFaceType;
import com.nec.asia.idserver.agent.service.IdserverAgentService;
import com.nec.asia.idserver.agent.service.exception.IdserverAgentServiceException;
import com.nec.asia.nic.framework.springSupport.SpringServiceManager;
import com.nec.asia.nic.util.MiscXmlConvertor;

public class TestRegisterFingerprint extends TestBase {
	protected static final Logger logger = LoggerFactory.getLogger(TestRegisterFingerprint.class);
	
	String transactionId = "NIC_07"; ////duplicate is not allowed (application_ref_no)
    String personId = "NIC006";   // person_ext_primary_id
	
	 
	public void xtestRegisterFp () throws Exception {
	       IdserverAgentService idsvrAgentService = (IdserverAgentService )SpringServiceManager.getBean("idserverAgentService");
	       //construct main object
	       IdserverRegistration registration = new IdserverRegistration ();
	       registration.setApplicationRefNo(transactionId);
	       registration.setPrimaryExternalId(personId);
	       
	       Set<IdserverImageFingerprint> fpList = new HashSet<IdserverImageFingerprint> ();
	       
	       IdserverFaceInfo faceInfo = new IdserverFaceInfo ();
	       faceInfo.setPose(IdserverFaceType.FRONTAL);
	       IdserverImageFace face = this.buildFace("Alice", IdserverFaceFormat.JPG);
	       faceInfo.setMugshot(face);
	       faceInfo.setThumbnail(face);
	       
	       registration.setFace(faceInfo);
	       
	       fpList.add(buildFingerprint("set4/", "1", true));
	       fpList.add(buildFingerprint("set4/", "2", false));
	       fpList.add(buildFingerprint("set4/", "3", false));
	       fpList.add(buildFingerprint("set4/", "4", false));
	       fpList.add(buildFingerprint("set4/", "5", false));
	       fpList.add(buildFingerprint("set4/", "6", false));
	       fpList.add(buildFingerprint("set4/", "7", false));
	       fpList.add(buildFingerprint("set4/", "8", false));
	       fpList.add(buildFingerprint("set4/", "9", false));
	       fpList.add(buildFingerprint("set4/", "10", false));
	       registration.setFpList(fpList);
	       
	       byte [] fpTemplate = this.loadFingerprintTemplate("set4/99");
	       registration.setFpTemplate(fpTemplate);
	       
	       IdserverPersonDetail personDetail = buildPersonDetail("Firstname", "Lastname", "M", "XXA");
	       registration.setPersonDetail(personDetail);
	       
	       idsvrAgentService.registerBiometricData(registration);
	       //idsvrAgentService.registerBiometricData(transactionId, personId, fpList, faceInfo, personDetail);
	}

	
	public void testGetBiometricData() throws IdserverAgentServiceException {
		IdserverAgentService idsvrAgentService = (IdserverAgentService )SpringServiceManager.getBean("idserverAgentService");
		String transactionId = "004-2016-000009";
		BioRegistrationDto dto = idsvrAgentService.retrieveBiometricData(transactionId, true, true, false);
		
		logger.info("[retrieveBiometricData] result: " + ReflectionToStringBuilder.toString(dto, ToStringStyle.MULTI_LINE_STYLE));
		
		logger.info("xml: "+MiscXmlConvertor.parseObjectToXml(dto));
		
		if (dto!=null) {
			String photoLobId = null;
			String thumbnailLobId = null;
			String fingerRTLobId = null;
			String fingerRILobId = null;
			String fingerLTLobId = null;
			String fingerLILobId = null;
			String tplLobId = null;
			
			Short FINGER_POSITION_RIGHT_THUMB = 1;
			Short FINGER_POSITION_RIGHT_INDEX = 2;
			Short FINGER_POSITION_LEFT_THUMB = 6;
			Short FINGER_POSITION_LEFT_INDEX = 7;
			
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
								if (FINGER_POSITION_RIGHT_THUMB.equals(fpOriginalImageDto.getFingerPosition())) {
									fingerRTLobId = fpOriginalImageDto.getLobId();
								} else if (FINGER_POSITION_RIGHT_INDEX.equals(fpOriginalImageDto.getFingerPosition())) {
									fingerRILobId = fpOriginalImageDto.getLobId();
								} else if (FINGER_POSITION_LEFT_THUMB.equals(fpOriginalImageDto.getFingerPosition())) {
									fingerLTLobId = fpOriginalImageDto.getLobId();
								} else if (FINGER_POSITION_LEFT_INDEX.equals(fpOriginalImageDto.getFingerPosition())) {
									fingerLILobId = fpOriginalImageDto.getLobId();
								}
							}
						}
					}
				}
			}
			//tpl
			if (dto.getBioFpBiometricTemplates()!=null && CollectionUtils.isNotEmpty(dto.getBioFpBiometricTemplates().getBioFpBiometricTemplateDto())) {
				for (BioFpBiometricTemplateDto templateDto :dto.getBioFpBiometricTemplates().getBioFpBiometricTemplateDto()) {
					tplLobId = templateDto.getTemplateLobId().toString();
					logger.info("templateDto: "+templateDto.getTemplateName());
				}
			}
			
			
			logger.info("photoLobId: "+photoLobId);
			logger.info("thumbnailLobId: "+thumbnailLobId);
			logger.info("fingerRTLobId: "+fingerRTLobId);
			logger.info("fingerRILobId: "+fingerRILobId);
			logger.info("fingerLTLobId: "+fingerLTLobId);
			logger.info("fingerLILobId: "+fingerLILobId);
			logger.info("tplLobId: "+tplLobId);
		}
	}
}
