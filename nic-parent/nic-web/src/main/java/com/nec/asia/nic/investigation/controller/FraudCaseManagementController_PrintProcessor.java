package com.nec.asia.nic.investigation.controller;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.ModelAndView;


import com.nec.asia.nic.comp.job.dto.HitCandidateDTO;
import com.nec.asia.nic.comp.job.dto.LogInfoDTO;
import com.nec.asia.nic.comp.job.dto.SearchHitDto;
import com.nec.asia.nic.comp.trans.dao.DocumentDataDao;
import com.nec.asia.nic.comp.trans.domain.NicRegistrationData;
import com.nec.asia.nic.comp.trans.domain.NicSearchHitResult;
import com.nec.asia.nic.comp.trans.domain.NicSearchResult;
import com.nec.asia.nic.comp.trans.domain.NicTransaction;
import com.nec.asia.nic.comp.trans.domain.NicTransactionAttachment;
import com.nec.asia.nic.comp.trans.domain.NicTransactionLog;
import com.nec.asia.nic.comp.trans.domain.NicUploadJob;
import com.nec.asia.nic.comp.trans.dto.InvestigationHitData;
import com.nec.asia.nic.comp.trans.dto.NicSearchHitResultHitInfoItem;
import com.nec.asia.nic.comp.trans.service.NicAfisDataService;
import com.nec.asia.nic.comp.trans.service.NicRegistrationDataService;
import com.nec.asia.nic.comp.trans.service.NicSearchHitResultService;
import com.nec.asia.nic.comp.trans.service.NicTransactionAttachmentService;
import com.nec.asia.nic.comp.trans.service.NicTransactionService;
import com.nec.asia.nic.comp.trans.service.NicUploadJobService;
import com.nec.asia.nic.comp.trans.service.exception.NicUploadJobServiceException;
import com.nec.asia.nic.comp.trans.service.exception.TransactionServiceException;
import com.nec.asia.nic.comp.trans.service.impl.NicSearchHitResultHitScorerFpImpl;
import com.nec.asia.nic.comp.trans.service.impl.NicSearchHitResultHitScorerTextualImpl;
// import com.nec.asia.nic.dx.trans.CitizenRef;
import com.nec.asia.nic.enquiry.controller.FingerprintInfo;
import com.nec.asia.nic.framework.admin.code.domain.CodeValues;
import com.nec.asia.nic.framework.admin.code.domain.Codes;
import com.nec.asia.nic.framework.admin.code.domain.Parameters;
import com.nec.asia.nic.framework.admin.code.service.CodesService;
import com.nec.asia.nic.framework.admin.code.service.ParametersService;
import com.nec.asia.nic.framework.admin.site.service.SiteService;
import com.nec.asia.nic.framework.common.ImageProperties;
import com.nec.asia.nic.framework.common.ImageUtil;
import com.nec.asia.nic.investigation.Base64Jp2HeaderHelper;
import com.nec.asia.nic.util.CitizenRefXmlConvertor;
import com.nec.asia.nic.util.CodeValueDescComparator;
import com.nec.asia.nic.util.Constants;
import com.nec.asia.nic.util.JobXmlConvertor;
import com.nec.asia.nic.util.TransLogInfoXmlConvertor;
import com.nec.asia.nic.utils.DateUtil;

@Component
public class FraudCaseManagementController_PrintProcessor {

	private static final Logger logger = LoggerFactory.getLogger(FraudCaseManagementController_PrintProcessor.class);

	private static final String PARA_TRANSACTION_ATTACHM_IGNORE_J2K_SCOPE = "SYSTEM";
	private static final String PARA_TRANSACTION_ATTACHM_IGNORE_J2K_VALUE = "TRANSACT2_ATTACHM_IGNORE_J2K";

	private static final String PARA_TX_ATTCH_J2K_CONVERT_AT_SERVER_SCOPE = "SYSTEM";
	private static final String PARA_TX_ATTCH_J2K_CONVERT_AT_SERVER_VALUE = "TX_ATTCH_J2K_CONVERT_AT_SERVER";

	public static final String DATA_DELIMITER = "XXXXX";
	public static final String DATA_DELIMITER_LEFT = "[";
	public static final String DATA_DELIMITER_RIGHT = "]";

	public static JobXmlConvertor jobUtil = new JobXmlConvertor();

	public static CitizenRefXmlConvertor util = new CitizenRefXmlConvertor();

	public static TransLogInfoXmlConvertor logUtil = new TransLogInfoXmlConvertor();

	private static String ASSENDING_ORDER = "1";

	private static String DECENDING_ORDER = "2";

	public static final String STATUS_COMPLETED = "02";

	private static final String OLD_REMARK_TO_BE_IGNORED = "Set To Suspended By System";

	@Autowired
	private ParametersService parametersService;

	@Autowired
	private NicUploadJobService uploadJobService;

	@Autowired
	private CodesService codesService;

	@Autowired
	private NicTransactionAttachmentService nicTransactionAttachmentService;

	@Autowired
	private DocumentDataDao documentDataDao = null;

	@Autowired
	private NicRegistrationDataService nicRegistrationDataService;

	@Autowired
	private NicTransactionService nicTransactionService;

	@Autowired
	private NicSearchHitResultService nicSearchHitResultService;

	@Autowired
	private NicAfisDataService nicAfisDataService;

	@Autowired
	private SiteService eppSiteService;

	public ModelAndView print(@PathVariable long jobId, HttpServletRequest httpRequest, Model model) throws Throwable {
		logger.info("NIC Start Investigation compare");

		return this.print(jobId, httpRequest, model, null);
	}

	public ModelAndView print(long jobId, HttpServletRequest httpRequest, Model model, List<String> listOfMessages)
			throws Throwable {
		logger.info("NIC Start Investigation compare, listOfMessages");

		try {
			this.prepareModelStuff(model);

			ModelAndView modelAndView = new ModelAndView("investigation.fraudCaseManagement.print");

			NicUploadJob jobDetails = uploadJobService.findById(jobId);

			String mainTransactionId = jobDetails.getTransactionId();

			this.initializeModelAndViewForms(modelAndView);

			this.initializeModelAndViewCommonPageDetails(modelAndView, jobDetails, mainTransactionId);

			List<SearchHitDto> hits = this.getAllHits(jobId, mainTransactionId);

			List<InvestigationHit> invHits_all = this.getAllHitsForModelAndView(modelAndView, jobDetails,
					mainTransactionId, hits);
			logger.info("invHits_all.size():" + invHits_all.size());

			List<InvestigationHit> invHits_error = this.getAllHits_error(invHits_all);
			this.processMainCandidateInvestigationInformation(invHits_all, invHits_error);

			List<InvestigationHit> invHits = this.getAllHits_nonError(invHits_all);

			logger.info("invHits.size():" + invHits.size());
			logger.info("invHits_error.size():" + invHits_error.size());

			List<InvestigationHit> invHits_displayThis = null;
			if (invHits.size() > 0) {
				invHits_displayThis = invHits;
				modelAndView.addObject("inv_noHit", new Boolean(false));
			} else {
				if (invHits_error.size() > 0) { 
					invHits_displayThis = invHits_error;
					modelAndView.addObject("inv_noHit", new Boolean(true));
				} else { 
					List<SearchHitDto> hitDtos = new ArrayList<SearchHitDto>();
					hitDtos.add(new SearchHitDto());
					
					List<InvestigationHit> invHits_self = this.getAllHitsForModelAndView(modelAndView, jobDetails,
						mainTransactionId, hitDtos, false);
					logger.info("invHits_self.size():" + invHits_self.size()); 
				
					invHits_displayThis = invHits_self;
					modelAndView.addObject("inv_noHit", new Boolean(true)); 
				}
			}

			modelAndView.addObject("invHits", invHits_displayThis);
			modelAndView.addObject("invHitsSize", invHits_displayThis.size());
			logger.info("computed:  invHits.size():" + invHits_displayThis.size());

			List<String> messages = new ArrayList<String>();

			if (listOfMessages != null) {
				messages.addAll(listOfMessages);
			}

			if (CollectionUtils.isNotEmpty(messages)) {
				httpRequest.setAttribute("messages", messages);
			}

			return modelAndView;
		} catch (Throwable e) {
			e.printStackTrace();
			throw e;
		}
	}

	private void processMainCandidateInvestigationInformation(List<InvestigationHit> invHits,
			List<InvestigationHit> invHits_error) throws Exception {

		if (CollectionUtils.isEmpty(invHits)) {
			return;
		}

		List<NicSearchHitResultHitInfoItem> mainCandidateInvestigationInformation = this
				.getMainCandidateInvestigationInformation(invHits_error);
		for (InvestigationHit hit : invHits) {
			hit.setMainCandidateInvestigationInformation(mainCandidateInvestigationInformation);
		}
	}

	private List<NicSearchHitResultHitInfoItem> getMainCandidateInvestigationInformation(
			List<InvestigationHit> invHits_error) throws Exception {

		if (CollectionUtils.isEmpty(invHits_error)) {
			return null;
		}

		String hitInfo = (String) (invHits_error.get(0).getHitCandidate_searchHitResult_hitInfo());

		if (StringUtils.isBlank(hitInfo)) {
			return null;
		}

		String itemsLevel1[] = hitInfo.split(NicSearchHitResult.hitInfo__ITEM__DELIMITER);
		List<NicSearchHitResultHitInfoItem> itemsForDisplay = new ArrayList<NicSearchHitResultHitInfoItem>();

		if (itemsLevel1 != null && itemsLevel1.length > 0) {
			for (String groupEntry : itemsLevel1) {
				String groupEntryTitle = StringUtils.substringBefore(groupEntry,
						NicSearchHitResult.hitInfo__SUB_ITEM__GROUP__DELIMITER);

				itemsForDisplay.add(new NicSearchHitResultHitInfoItem(this.codesService
						.getCodeValueDescByIdName(Codes.SRCH_HIT_RESULT__ITEM, groupEntryTitle, groupEntryTitle),
						this.getTextualHitInfoGroupItems(groupEntry)));
			}
		}

		return itemsForDisplay;
	}

	private List<InvestigationHit> getAllHits_nonError(List<InvestigationHit> invHits_all) {

		List<InvestigationHit> hits = new ArrayList<InvestigationHit>();

		if (CollectionUtils.isEmpty(invHits_all)) {
			return hits;
		}

		for (InvestigationHit hit : invHits_all) {

			if (Collections.disjoint(
					Arrays.asList(new String[] { ((String) hit.getHitCandidate_searchResult_typeSearch()) }),
					NicSearchResult.TYPE_SEARCH__THAT_ARE_MAIN_CANDIDATE_BASED)) {
				hits.add(hit);
			}
		}

		return hits;
	}

	private List<InvestigationHit> getAllHits_error(List<InvestigationHit> invHits_all) {

		List<InvestigationHit> hits = new ArrayList<InvestigationHit>();

		if (CollectionUtils.isEmpty(invHits_all)) {
			return hits;
		}

		for (InvestigationHit hit : invHits_all) {
			if (!(Collections.disjoint(
					Arrays.asList(new String[] { ((String) hit.getHitCandidate_searchResult_typeSearch()) }),
					NicSearchResult.TYPE_SEARCH__THAT_ARE_MAIN_CANDIDATE_BASED))) {
				hits.add(hit);
			}
		}

		return hits;
	}

	private void put1HitIntoModelAndView_main(ModelAndView modelAndView, NicUploadJob jobDetails,
			String mainTransactionId, InvestigationHit invHit) throws Exception, TransactionServiceException {

		/*
		 * MAIN - START
		 */
		{
			String mainCandidatePhoto = null;
			String mainCandidateSignature = null;

			// Main Candidate Photo, Signature and Finger prints.
			List<NicTransactionAttachment> mainCanPh = nicTransactionAttachmentService.getNicTransactionAttachments(
					mainTransactionId, new String[] { NicTransactionAttachment.DOC_TYPE_PHOTO_CAPTURE },
					new String[] { NicTransactionAttachment.DEFAULT_SERIAL_NO });
			// Main Candidate Photo
			if (CollectionUtils.isNotEmpty(mainCanPh)) {
				byte[] photoBinary = this.handleJp2(mainCanPh.get(0).getDocData());
				mainCandidatePhoto = new Base64Jp2HeaderHelper().getBase64WithJp2Header(photoBinary);
			}

			// Main Candidate Finger prints
			Map<String, byte[]> mainFpMap = new HashMap<String, byte[]>();
			Map<String, String> mainFpBase64JpgMap = new HashMap<String, String>();
			Map<String, String> mainFpIndicatorMap = new HashMap<String, String>();

			List<NicTransactionAttachment> mainCanFp = nicTransactionAttachmentService.getNicTransactionAttachments(
					mainTransactionId, new String[] { NicTransactionAttachment.DOC_TYPE_FINGERPRINT }, null);

			if (CollectionUtils.isNotEmpty(mainCanFp)) {
				for (NicTransactionAttachment record : mainCanFp) {
					mainFpMap.put(record.getSerialNo(), record.getDocData());
					mainFpBase64JpgMap.put(record.getSerialNo(), this.getJpgBase64StringFromWsq(record.getDocData()));
				}
			}

			// Main Candidate Details
			NicTransaction mainCandidateListNin = nicTransactionService.findById(mainTransactionId);
			if (mainCandidateListNin != null) {
				invHit.addObject("mainCandidateIORemarks", this.getMainCandidateIORemarks(mainTransactionId,
						NicTransactionLog.TRANSACTION_STAGE_INVESTIGATION,
						NicTransactionLog.TRANSACTION_STATUS_NIC_SUSPENDED));

				invHit.addObject("mainCandidatePreviousPassportNumber", mainCandidateListNin.getPrevPassportNo());
				invHit.addObject("mainCandidatePreviousPassportIssueDate",
						DateUtil.parseDate(mainCandidateListNin.getPrevDateOfIssue(), "dd-MMM-yyyy"));

				// mainCandidateListNin.getNin();
				mainCandidateListNin = nicTransactionService.getTransactionRegistrationData(mainCandidateListNin);

				NicRegistrationData nicRegistrationData = mainCandidateListNin.getNicRegistrationData();

				invHit.addObject("mainCandidateApplicantID", this.getTransactionApplicantID(mainTransactionId));

				this.processOneReferringToCodeTable(mainCandidateListNin.getPassportType(), Codes.PASSPORT_TYPE,
						"mainCandidatePassportType", invHit);
				this.processOneReferringToCodeTable(
						mainCandidateListNin.getPriority() == null ? null
								: mainCandidateListNin.getPriority().toString(),
						Codes.PRIORITY, "mainCandidatePriority", invHit);

				invHit.addObject("mainCandidateDateOfApplication",
						DateUtil.parseDate(mainCandidateListNin.getDateOfApplication(), "dd-MMM-yyyy"));
				invHit.addObject("mainCandidateIssuingAuthority", this.eppSiteService.getSiteRepoAuthority(
						mainCandidateListNin.getIssuingAuthority(), mainCandidateListNin.getIssuingAuthority()));

				this.processOneReferringToCodeTable(mainCandidateListNin.getTransactionType(), Codes.TRANSACTION_TYPE,
						"mainCandidateTransactionType", invHit);
				this.processOneReferringToCodeTable(mainCandidateListNin.getTransactionStatus(),
						Codes.TRANSACTION_STATUS, "mainCandidateApplicationPassportStatus", invHit);
				this.processOneReferringToCodeTable(nicRegistrationData.getNationality(), Codes.NATIONALITY,
						"mainCandidateNationality", invHit);

				{

					List<AttachmentEntry> attachmentEntries = new ArrayList<AttachmentEntry>();
					 
					// GET ALL ATTACHMENTS
					List<NicTransactionAttachment> nicTransactionDocuments = null;
					{
						String[] excludedDocTypes = { NicTransactionAttachment.DOC_TYPE_FINGERPRINT,
								NicTransactionAttachment.DOC_TYPE_MINUTIA, NicTransactionAttachment.DOC_TYPE_MINUTIA_CBEFF,
								NicTransactionAttachment.DOC_TYPE_ENCODING, NicTransactionAttachment.DOC_TYPE_PERSO, 
								NicTransactionAttachment.DOC_TYPE_SIGNATURE, NicTransactionAttachment.DOC_TYPE_SIGN_FP, 
								NicTransactionAttachment.DOC_TYPE_PHOTO_CAPTURE, NicTransactionAttachment.DOC_TYPE_THUMBNAIL_CAPTURE, 
								NicTransactionAttachment.DOC_TYPE_PHOTO_GREY, NicTransactionAttachment.DOC_TYPE_THUMBNAIL_GREY, 
								NicTransactionAttachment.DOC_TYPE_PHOTO_CHIP, NicTransactionAttachment.DOC_TYPE_THUMBNAIL_CHIP, 
								NicTransactionAttachment.DOC_TYPE_PHOTO_CLI, 
								NicTransactionAttachment.DOC_TYPE_TPL };
						try {
							nicTransactionDocuments = this.nicTransactionAttachmentService
									.getTransactionProofDocuments(mainTransactionId, excludedDocTypes);
						} catch (Exception e) {
							nicTransactionDocuments = null;
						}
					}

					// LOOP THROUGH ALL
					{
						if (nicTransactionDocuments != null && nicTransactionDocuments.size() > 0) {
							for (NicTransactionAttachment attachment : nicTransactionDocuments) {
								String description = this.codesService.getCodeValueDescByIdName(Codes.DOC_TYPE, attachment.getDocType(), attachment.getDocType()); 
								this.processBasedOnImageType(attachmentEntries, description, attachment.getDocData()); 
							}
						}
					}  

					invHit.addObject("mainCandidateAttachments", attachmentEntries);
					invHit.addObject("mainCandidateAttachmentSize", attachmentEntries.size());
				}

				// Main Candidate FP Quality
				Map<Integer, FingerprintInfo> mainFpQua = new TreeMap<Integer, FingerprintInfo>();
				String mainFpQuality = mainCandidateListNin.getNicRegistrationData().getFpQuality();
				if (mainFpQuality != null) {
					String mainFpQualityArry[] = mainFpQuality.split(",");
					if (mainFpQualityArry != null) {
						for (int i = 0; i < mainFpQualityArry.length; ++i) {
							FingerprintInfo info = new FingerprintInfo();
							String matchFpArry[] = mainFpQualityArry[i].split("-");

							info.setFpQuaScr(Integer.valueOf(matchFpArry[1]));
							// info.setGoodFPQuaScr(investigationData.goodFpQuasMap.get(Integer.valueOf(matchFpArry[0])));
							// info.setAccetableFPQuaScr(
							// investigationData.acceptFpQuasMap.get(Integer.valueOf(matchFpArry[0])));

							mainFpQua.put(Integer.valueOf(matchFpArry[0]), info);
							logger.info("The main candidate fp Quality " + mainFpQua);
						}
					}
				}

				// Main Candidate FP Verify
				Map<Integer, Integer> mainFpVeri = new TreeMap<Integer, Integer>();

				// Main Candidate FP Encode
				Map<Integer, Double> mainFpEnc = new TreeMap<Integer, Double>();
				String mainFpEncode = mainCandidateListNin.getNicRegistrationData().getFpEncode();
				if (mainFpEncode != null) {
					String mainFpEncodeArry[] = mainFpEncode.split(",");
					if (mainFpEncodeArry != null) {
						for (int i = 0; i < mainFpEncodeArry.length; ++i) {
							mainFpEnc.put(Integer.valueOf(mainFpEncodeArry[i]), Double.valueOf(mainFpEncodeArry[i]));
							logger.info("The main candidate fp encode " + mainFpEnc);
						}
					}
				}

				if (nicRegistrationData != null) {
					 
					invHit.addObject("mainCandidateAlsoKnownAs", nicRegistrationData.getAliasName());
					invHit.addObject("mainCandidateLimitation", nicRegistrationData.getPrintRemarks1());
					invHit.addObject("mainCandidatePosition", nicRegistrationData.getOccupationDesc());

					if (StringUtils.isNotBlank(nicRegistrationData.getFpIndicator())) {
						String[] fpIndicatorMain = nicRegistrationData.getFpIndicator().split(",");
						if (fpIndicatorMain.length > 0) {
							for (String mainInd : fpIndicatorMain) {
								mainFpIndicatorMap.put(mainInd.split("-")[0], mainInd.split("-")[1]);
							}
						}
					}

					List<NicTransactionAttachment> mainCanSig = nicTransactionAttachmentService
							.getNicTransactionAttachments(mainTransactionId,
									new String[] { NicTransactionAttachment.DOC_TYPE_SIGNATURE },
									new String[] { NicTransactionAttachment.DEFAULT_SERIAL_NO });
					// Main Candidate Signature
					if (CollectionUtils.isNotEmpty(mainCanSig)) {
						byte[] signBinary = this.handleJp2(mainCanSig.get(0).getDocData());
						mainCandidateSignature = new Base64Jp2HeaderHelper().getBase64WithJp2Header(signBinary);
						// mainCandidateSignature = signBinary;
					}

					String mainCandidateFN = nicRegistrationData.getFirstnameFull();
					String mainCandidateFNShort = null;
					if (StringUtils.isNotBlank(mainCandidateFN)) {
						if (mainCandidateFN.length() > 15) {
							mainCandidateFNShort = mainCandidateFN.substring(0, 14) + " ...";
						} else {
							mainCandidateFNShort = mainCandidateFN;
						}
					}

					String mainCandidateSN = nicRegistrationData.getSurnameFull();
					String mainCandidateSNShort = null;
					if (StringUtils.isNotBlank(mainCandidateSN)) {
						if (mainCandidateSN.length() > 15) {
							mainCandidateSNShort = mainCandidateSN.substring(0, 14) + " ...";
						} else {
							mainCandidateSNShort = mainCandidateSN;
						}
					}

					String mainCandidateMN = nicRegistrationData.getMiddlenameFull();
					String mainCandidateMNShort = null;
					if (StringUtils.isNotBlank(mainCandidateMN)) {
						if (mainCandidateMN.length() > 15) {
							mainCandidateMNShort = mainCandidateMN.substring(0, 14) + " ...";
						} else {
							mainCandidateMNShort = mainCandidateMN;
						}
					}

					String mainCandidateGender = nicRegistrationData.getGender();
					Date mainCandidateDOB = nicRegistrationData.getDateOfBirth();
					String mainCandDOB = null;
					if (mainCandidateDOB != null) {
						mainCandDOB = DateUtil.parseDate(mainCandidateDOB, "dd-MMM-yyyy");
					}
					String mainCandidateFlatNo = nicRegistrationData.getAddressLine1();
					String mainCandiadteStreetName = nicRegistrationData.getAddressLine2();
					String mainCandidateLocality = nicRegistrationData.getAddressLine3();

					String mainCandidateMaritalStatus = nicRegistrationData.getMaritalStatus();
					String mainCandidateFathersN = nicRegistrationData.getFatherFullname();
					//String mainCandidateFathersSN = nicRegistrationData.getFatherSurname();
					//String mainCandidateFathersMN = nicRegistrationData.getFatherMiddlename();
					String mainCandidateMothersN = nicRegistrationData.getMotherFullname();
					//String mainCandidateMothersSN = nicRegistrationData.getMotherSurname();
					//String mainCandidateMothersMN = nicRegistrationData.getMotherMiddlename();
					String mainCandidateSpouseFN = nicRegistrationData.getSpouseFullname();
					//String mainCandidateSpouseSN = nicRegistrationData.getSpouseSurname();
					//String mainCandidateSpouseMN = nicRegistrationData.getSpouseMiddlename();

					invHit.addObject("mainCandidateFN", mainCandidateFN);
					invHit.addObject("mainCandidateFNShort", mainCandidateFNShort);
					invHit.addObject("mainCandidateSN", mainCandidateSN);
					invHit.addObject("mainCandidateMN", mainCandidateMN);
					invHit.addObject("mainCandidateSNShort", mainCandidateSNShort);
					invHit.addObject("mainCandidateMNShort", mainCandidateMNShort);
					invHit.addObject("mainCandidateGender", mainCandidateGender);
					invHit.addObject("mainCandidateDOB", mainCandDOB);
					invHit.addObject("mainCandidateFlatNo", mainCandidateFlatNo);
					invHit.addObject("mainCandiadteStreetName", mainCandiadteStreetName);
					invHit.addObject("mainCandidateLocality", mainCandidateLocality);
					invHit.addObject("mainCandidateMaritalStatus", mainCandidateMaritalStatus);
					invHit.addObject("mainCandidateFathersN", mainCandidateFathersN);
					//invHit.addObject("mainCandidateFathersSN", mainCandidateFathersSN);
					//invHit.addObject("mainCandidateFathersMN", mainCandidateFathersMN);
					invHit.addObject("mainCandidateMothersN", mainCandidateMothersN);
					//invHit.addObject("mainCandidateMothersSN", mainCandidateMothersSN);
					//invHit.addObject("mainCandidateMothersMN", mainCandidateMothersMN);
					invHit.addObject("mainCandidateSpouseFN", mainCandidateSpouseFN);
					//invHit.addObject("mainCandidateSpouseSN", mainCandidateSpouseSN);
					//invHit.addObject("mainCandidateSpouseMN", mainCandidateSpouseMN);
					invHit.addObject("mainCandidateFpQuality", mainFpQua);
					invHit.addObject("mainCandidateFpEncode", mainFpEnc);
					invHit.addObject("mainCandidateFpVerify", mainFpVeri);

				}
			}

			invHit.addObject("mainCandidatePhoto", mainCandidatePhoto);
			invHit.addObject("mainCandidateSignature", mainCandidateSignature);
			invHit.addObject("mainFpMap", mainFpMap);
			invHit.addObject("mainFpBase64JpgMap", mainFpBase64JpgMap);
			invHit.addObject("mainFpIndicatorMap", mainFpIndicatorMap);
		}
		/*
		 * 1111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111
		 * MAIN - END
		 * 1111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111
		 */

	}

	private String getMainCandidateIORemarks(String transactionId, String transactionStage, String transactionStatus) {

		try {
			String logInfo = this.uploadJobService.findAllLogInfo(transactionId, transactionStage, transactionStatus).get(0).getLogInfo();

			if (logInfo == null) {
				return null;
			}

			try {
				String remarks = ((LogInfoDTO) logUtil.unmarshal(logInfo)).getRemark();
				
				if (remarks != null && remarks.equals(FraudCaseManagementController_PrintProcessor.OLD_REMARK_TO_BE_IGNORED)){
					remarks = "";
				} 
				
				return remarks;
			} catch (Exception e) {
				return logInfo;
			}
		} catch (Exception ex) {
			return null;
		}
	}

	private String getJpgBase64StringFromWsq(byte[] docData) {

		if (docData == null) {
			return null;
		}

		try {
//			byte[] convertedBinary = SpidHelper.getInstance("Y").convertImageFormat(docData, SpidHelper.IMAGE_WSQ,
//					SpidHelper.IMAGE_JPG);

			// {
			// AppletImageUtility util = new AppletImageUtility();
			// convertedBinary =
			// util.resizeImage(util.getBufferedImage(convertedBinary), .2,
			// AppletImageUtility.FORMAT_JPEG);
			// }

			//return Base64.encodeBase64String(convertedBinary);
		} catch (Exception ex) {
		}
		return null;
	}

	private void processBasedOnImageType(List<AttachmentEntry> attachmentEntries, String description, byte[] image) {

		boolean ignoreJ2k = false;
		{
			try {
				Parameters parameter = this.parametersService.getParamDetails(
						FraudCaseManagementController_PrintProcessor.PARA_TRANSACTION_ATTACHM_IGNORE_J2K_SCOPE,
						FraudCaseManagementController_PrintProcessor.PARA_TRANSACTION_ATTACHM_IGNORE_J2K_VALUE);
				if (parameter != null) {
					ignoreJ2k = Boolean.valueOf(parameter.getParaShortValue());
				}
			} catch (Exception e) {
			}
		}

		boolean j2kConvertAtServer = false;
		{
			try {
				Parameters parameter = this.parametersService.getParamDetails(
						FraudCaseManagementController_PrintProcessor.PARA_TX_ATTCH_J2K_CONVERT_AT_SERVER_SCOPE,
						FraudCaseManagementController_PrintProcessor.PARA_TX_ATTCH_J2K_CONVERT_AT_SERVER_VALUE);
				if (parameter != null) {
					j2kConvertAtServer = Boolean.valueOf(parameter.getParaShortValue());
				}
			} catch (Exception e) {
			}
		}

		// get type
		String imageType = null;
		{
			try {
				imageType = new ImageUtil().checkImageType(image);
			} catch (Exception e) {
				return;
			}
		}

		if (ignoreJ2k && (StringUtils.isNotBlank(imageType) && imageType.equalsIgnoreCase(ImageUtil.IMAGE_J2K))) {
			return;
		}

		// if (image != null) {
		// try {
		// image = FileUtils.readFileToByteArray(new
		// File("C:/EPP-DEV/angeles/fileToConvert.xxx"));
		// } catch (Exception e) {
		// }
		// }

		attachmentEntries.add(new AttachmentEntry(description, null, image, j2kConvertAtServer));
	}

	private ImageProperties getImageProperties(byte[] docData) throws Exception {

		if (docData == null) {
			return null;
		}

		return new ImageUtil().getImageProperties(docData);
	}

	private void put1HitIntoModelAndView_hit(ModelAndView modelAndView, NicUploadJob jobDetails,
			String mainTransactionId, Long searchResultId, String hitTransactionId, InvestigationHit invHit,
			String searchResult_typeSearch, String searchHitResult_hitInfo)
					throws Exception, TransactionServiceException {

		/*
		 * 2222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222
		 * HIT - START
		 * 2222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222
		 */
		{
			invHit.addObject("hitCandidate_searchResult_typeSearch", searchResult_typeSearch);
			invHit.addObject("hitCandidate_searchHitResult_hitInfo", searchHitResult_hitInfo);

			// Hit Candidate Photo, Signature and Finger prints.
			String hitCandidatePhoto = null;
			String hitCandidateSignature = null;

			List<NicTransactionAttachment> hitCanPh = nicTransactionAttachmentService.getNicTransactionAttachments(
					hitTransactionId, new String[] { NicTransactionAttachment.DOC_TYPE_PHOTO_CAPTURE },
					new String[] { NicTransactionAttachment.DEFAULT_SERIAL_NO });
			// Hit Candidate Photo
			if (CollectionUtils.isNotEmpty(hitCanPh)) {
				byte[] photoBinary = this.handleJp2(hitCanPh.get(0).getDocData());
				hitCandidatePhoto = new Base64Jp2HeaderHelper().getBase64WithJp2Header(photoBinary);
			}

			Map<String, String> hitFpIndicatorMap = new HashMap<String, String>();
			Map<String, byte[]> hitFpMap = new HashMap<String, byte[]>();
			Map<String, String> hitFpBase64JpgMap = new HashMap<String, String>();

			List<NicTransactionAttachment> hitCanFp = nicTransactionAttachmentService.getNicTransactionAttachments(
					hitTransactionId, new String[] { NicTransactionAttachment.DOC_TYPE_FINGERPRINT }, null);
			// Hit Candidate Finger prints
			if (CollectionUtils.isNotEmpty(hitCanFp)) {
				for (NicTransactionAttachment record : hitCanFp) {
					hitFpMap.put(record.getSerialNo(), record.getDocData());
					hitFpBase64JpgMap.put(record.getSerialNo(), this.getJpgBase64StringFromWsq(record.getDocData()));
				}
			}

			// get list of search types
			Set<String> uniqueList_searchTypesForThisTransactionIdHit = new HashSet<String>();
			uniqueList_searchTypesForThisTransactionIdHit.addAll(this.uploadJobService
					.getAllSearchTypes(jobDetails.getWorkflowJobId(), mainTransactionId, hitTransactionId));

			// To display the percentage match score
			this.processFpMatchInfo(hitTransactionId, invHit, uniqueList_searchTypesForThisTransactionIdHit,
					jobDetails.getWorkflowJobId());

			this.processHitCandidateHitCategories(invHit, uniqueList_searchTypesForThisTransactionIdHit);

			this.processHitCandidateIORemarks(invHit, searchResultId, hitTransactionId);

			this.processTextualMatchInfo(hitTransactionId, invHit, uniqueList_searchTypesForThisTransactionIdHit,
					jobDetails.getWorkflowJobId());

			// Hit Candidate Details
			NicTransaction hitCandidateListNin = nicTransactionService.findById(hitTransactionId);
			if (hitCandidateListNin != null) {
				hitCandidateListNin = nicTransactionService.getTransactionRegistrationData(hitCandidateListNin);

				NicRegistrationData hitNicRegistrationData = hitCandidateListNin.getNicRegistrationData();

				invHit.addObject("hitCandidateApplicantID", this.getTransactionApplicantID(hitTransactionId));

				this.processOneReferringToCodeTable(hitCandidateListNin.getPassportType(), Codes.PASSPORT_TYPE,
						"hitCandidatePassportType", invHit);
				this.processOneReferringToCodeTable(
						hitCandidateListNin.getPriority() == null ? null : hitCandidateListNin.getPriority().toString(),
						Codes.PRIORITY, "hitCandidatePriority", invHit);

				invHit.addObject("hitCandidateDateOfApplication",
						DateUtil.parseDate(hitCandidateListNin.getDateOfApplication(), "dd-MMM-yyyy"));
				invHit.addObject("hitCandidateIssuingAuthority", this.eppSiteService.getSiteRepoAuthority(
						hitCandidateListNin.getIssuingAuthority(), hitCandidateListNin.getIssuingAuthority()));

				this.processOneReferringToCodeTable(hitCandidateListNin.getTransactionType(), Codes.TRANSACTION_TYPE,
						"hitCandidateTransactionType", invHit);
				this.processOneReferringToCodeTable(hitCandidateListNin.getTransactionStatus(),
						Codes.TRANSACTION_STATUS, "hitCandidateApplicationPassportStatus", invHit);
				this.processOneReferringToCodeTable(hitNicRegistrationData.getNationality(), Codes.NATIONALITY,
						"hitCandidateNationality", invHit);

				// Hit Candidate FP Quality
				Map<Integer, FingerprintInfo> hitFpQua = new TreeMap<Integer, FingerprintInfo>();
				String hitFpQuality = hitCandidateListNin.getNicRegistrationData().getFpQuality();
				if (hitFpQuality != null) {
					String hitFpQualityArry[] = hitFpQuality.split(",");
					if (hitFpQualityArry != null) {
						for (int i = 0; i < hitFpQualityArry.length; ++i) {
							String hitFpArry[] = hitFpQualityArry[i].split("-");

							FingerprintInfo info = new FingerprintInfo();

							info.setFpQuaScr(Integer.valueOf(hitFpArry[1]));
							// info.setGoodFPQuaScr(investigationData.goodFpQuasMap.get(Integer.valueOf(hitFpArry[0])));
							// info.setAccetableFPQuaScr(
							// investigationData.acceptFpQuasMap.get(Integer.valueOf(hitFpArry[0])));

							hitFpQua.put(Integer.valueOf(hitFpArry[0]), info);

							logger.info("The hit candidate fp Quality " + hitFpQua);
						}
					}
					logger.info("The fp Quality is =================>>>>>>>>>>>>>"
							+ hitCandidateListNin.getNicRegistrationData().getFpQuality());
				}

				// Hit Candidate FP Encode
				Map<Integer, Double> hitFpEnc = new TreeMap<Integer, Double>();
				String hitFpEncode = hitCandidateListNin.getNicRegistrationData().getFpEncode();
				if (hitFpEncode != null) {
					String hitFpEncodeArry[] = hitFpEncode.split(",");
					if (hitFpEncodeArry != null) {
						for (int i = 0; i < hitFpEncodeArry.length; ++i) {
							hitFpEnc.put(Integer.valueOf(hitFpEncodeArry[i]), Double.valueOf(hitFpEncodeArry[i]));
							logger.info("The hit candidate fp encode " + hitFpEnc);
						}
					}
				}

				if (hitNicRegistrationData != null) {

					invHit.addObject("hitCandidateAlsoKnownAs", hitNicRegistrationData.getAliasName()); 
					invHit.addObject("hitCandidateLimitation", hitNicRegistrationData.getPrintRemarks1());
					invHit.addObject("hitCandidatePosition", hitNicRegistrationData.getOccupationDesc());
					
					if (StringUtils.isNotBlank(hitNicRegistrationData.getFpIndicator())) {
						String[] fpIndicatorHit = hitNicRegistrationData.getFpIndicator().split(",");
						if (fpIndicatorHit.length > 0) {
							for (String hitInd : fpIndicatorHit) {
								try {
									hitFpIndicatorMap.put(hitInd.split("-")[0], hitInd.split("-")[1]);
								} catch (Exception e) {
									e.printStackTrace();
								}
							}

						}
					}

					List<NicTransactionAttachment> hitCanSig = nicTransactionAttachmentService
							.getNicTransactionAttachments(hitTransactionId,
									new String[] { NicTransactionAttachment.DOC_TYPE_SIGNATURE },
									new String[] { NicTransactionAttachment.DEFAULT_SERIAL_NO });
					// Hit Candidate Signature
					if (CollectionUtils.isNotEmpty(hitCanSig)) {
						byte[] signBinary = this.handleJp2(hitCanSig.get(0).getDocData());
						hitCandidateSignature = new Base64Jp2HeaderHelper().getBase64WithJp2Header(signBinary);
						// hitCandidateSignature = signBinary;
					}

					String hitCandidateFN = hitNicRegistrationData.getFirstnameFull();
					String hitCandidateFNShort = null;
					if (hitCandidateFN != null) {

						if (StringUtils.isNotBlank(hitCandidateFN)) {
							if (hitCandidateFN.length() > 15) {
								hitCandidateFNShort = hitCandidateFN.substring(0, 14) + " ...";
							} else {
								hitCandidateFNShort = hitCandidateFN;
							}
						}
					}
					String hitCandidateSNShort = null;
					String hitCandidateSN = hitNicRegistrationData.getSurnameFull();
					if (hitCandidateSN != null) {
						if (StringUtils.isNotBlank(hitCandidateSN)) {
							if (hitCandidateSN.length() > 15) {
								hitCandidateSNShort = hitCandidateSN.substring(0, 14) + " ...";
							} else {
								hitCandidateSNShort = hitCandidateSN;
							}
						}
					}

					String hitCandidateMNShort = null;
					String hitCandidateMN = hitNicRegistrationData.getMiddlenameFull();
					if (StringUtils.isNotBlank(hitCandidateMN)) {
						if (hitCandidateMN.length() > 15) {
							hitCandidateMNShort = hitCandidateMN.substring(0, 14) + " ...";
						} else {
							hitCandidateMNShort = hitCandidateMN;
						}
					}

					String hitCandidateGender = hitNicRegistrationData.getGender();
					Date hitCandidateDOB = hitNicRegistrationData.getDateOfBirth();

					String hitCandDOB = null;
					if (hitCandidateDOB != null) {
						hitCandDOB = DateUtil.parseDate(hitCandidateDOB, "dd-MMM-yyyy");
					}

					String hitCandidateFlatNo = hitNicRegistrationData.getAddressLine1();
					String hitCandidateStreetName = hitNicRegistrationData.getAddressLine2();
					String hitCandidateLocality = hitNicRegistrationData.getAddressLine3();
					String hitCandidateMaritalStatus = hitNicRegistrationData.getMaritalStatus();
					String hitCandidateFathersN = hitNicRegistrationData.getFatherFullname();
					//String hitCandidateFathersSN = hitNicRegistrationData.getFatherSurname();
					//String hitCandidateFathersMN = hitNicRegistrationData.getFatherMiddlename();
					String hitCandidateMothersN = hitNicRegistrationData.getMotherFullname();
					//String hitCandidateMothersSN = hitNicRegistrationData.getMotherSurname();
					//String hitCandidateMothersMN = hitNicRegistrationData.getMotherMiddlename();
					String hitCandidateSpouseFN = hitNicRegistrationData.getSpouseFullname();
					//String hitCandidateSpouseSN = hitNicRegistrationData.getSpouseSurname();
					//String hitCandidateSpouseMN = hitNicRegistrationData.getSpouseMiddlename();
					invHit.addObject("hitCandidateListTransId", hitCandidateListNin.getTransactionId());
					invHit.addObject("hitCandidatePhoto", hitCandidatePhoto);
					invHit.addObject("hitCandidateSignature", hitCandidateSignature);
					invHit.addObject("hitCandidateFN", hitCandidateFN);
					invHit.addObject("hitCandidateFNShort", hitCandidateFNShort);
					invHit.addObject("hitCandidateSN", hitCandidateSN);
					invHit.addObject("hitCandidateSNShort", hitCandidateSNShort);
					invHit.addObject("hitCandidateMNShort", hitCandidateMNShort);
					invHit.addObject("hitCandidateGender", hitCandidateGender);
					invHit.addObject("hitCandidateDOB", hitCandDOB);
					invHit.addObject("hitCandidateFlatNo", hitCandidateFlatNo);
					invHit.addObject("hitCandidateStreetName", hitCandidateStreetName);
					invHit.addObject("hitCandidateLocality", hitCandidateLocality);
					invHit.addObject("hitCandidateMaritalStatus", hitCandidateMaritalStatus);
					invHit.addObject("hitCandidateFathersN", hitCandidateFathersN);
					//invHit.addObject("hitCandidateFathersSN", hitCandidateFathersSN);
					//invHit.addObject("hitCandidateFathersMN", hitCandidateFathersMN);
					invHit.addObject("hitCandidateMothersN", hitCandidateMothersN);
					//invHit.addObject("hitCandidateMothersSN", hitCandidateMothersSN);
					//invHit.addObject("hitCandidateMothersMN", hitCandidateMothersMN);
					invHit.addObject("hitCandidateSpouseFN", hitCandidateSpouseFN);
					//invHit.addObject("hitCandidateSpouseSN", hitCandidateSpouseSN);
					//invHit.addObject("hitCandidateSpouseMN", hitCandidateSpouseMN);
					invHit.addObject("hitCandidateFpQuality", hitFpQua);
					invHit.addObject("hitCandidateFpEncode", hitFpEnc);
				}
			}
			invHit.addObject("hitFpMap", hitFpMap);
			invHit.addObject("hitFpBase64JpgMap", hitFpBase64JpgMap);
			invHit.addObject("hitFpIndicatorMap", hitFpIndicatorMap);

			invHit.addObject("hitTransactionId", hitTransactionId);
			invHit.addObject("searchResultId", searchResultId);
		}
		/*
		 * 2222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222
		 * HIT - END
		 * 2222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222
		 */
	}

	private void processHitCandidateIORemarks(InvestigationHit invHit, Long searchResultId, String hitTransactionId) {

		try {
			invHit.addObject("hitCandidateIORemarks", this.nicSearchHitResultService
					.findHitPositions(hitTransactionId, searchResultId).get(0).getVerifyDecisionRemarks());
		} catch (Exception e) {
			invHit.addObject("hitCandidateIORemarks", "");
		}
	}

	private void processHitCandidateHitCategories(InvestigationHit invHit,
			Set<String> uniqueList_searchTypesForThisTransactionIdHit) {

		Set<String> uniqueDescriptionsList = new HashSet<String>();
		for (String searchTypeCode : uniqueList_searchTypesForThisTransactionIdHit) {
			String desc = this.codesService.getCodeValueDescByIdName(Codes.SEARCH_TYPE, searchTypeCode, "");
			if (desc.trim().length() > 0) {
				uniqueDescriptionsList.add(desc);
			}
		}

		List<String> sortedDescs = new ArrayList<String>();
		sortedDescs.addAll(uniqueDescriptionsList);
		Collections.sort(sortedDescs);

		String hitCandidatehitCategories = "";
		for (String aDesc : sortedDescs) {
			if (hitCandidatehitCategories.length() > 0) {
				hitCandidatehitCategories = hitCandidatehitCategories + ", ";
			}
			hitCandidatehitCategories = hitCandidatehitCategories + aDesc;
		}

		invHit.addObject("hitCandidatehitCategories", hitCandidatehitCategories);
	}

	private void processFpMatchInfo(String hitTransactionId, InvestigationHit invHit,
			Set<String> searchTypesForThisTransactionIdHit, Long workflowId) throws Exception {

		if (Collections.disjoint(searchTypesForThisTransactionIdHit,
				NicSearchResult.TYPE_SEARCH__THAT_ARE_FINGER_BASED)) {
			invHit.addObject("investigationForm", null);
			return;
		}

		try {
			NicSearchHitResult nicSearchHitResult = this.uploadJobService
					.getAllSearchHitResult(workflowId, hitTransactionId,
							NicSearchResult.TYPE_SEARCH__THAT_ARE_FINGER_BASED, new NicSearchHitResultHitScorerFpImpl())
					.get(0);

			Map<Integer, Double> hitMatchs = new TreeMap<Integer, Double>();

			HitCandidateDTO hitCandidateDTO = new HitCandidateDTO();

			String hitPositionArry[] = nicSearchHitResult.getHitInfo().split(",");
			if (hitPositionArry != null) {
				for (int i = 0; i < hitPositionArry.length; ++i) {
					String matchArry[] = hitPositionArry[i].split("=");
					hitMatchs.put(Integer.valueOf(matchArry[0]), Double.valueOf(matchArry[1]) / 100);
					logger.info("The hit match score " + hitMatchs);
				}
			}

			if (!hitMatchs.containsKey(1)) {
				hitMatchs.put(Integer.valueOf(1), 0.1);
			}
			if (!hitMatchs.containsKey(2)) {
				hitMatchs.put(Integer.valueOf(2), 0.1);
			}
			if (!hitMatchs.containsKey(3)) {
				hitMatchs.put(Integer.valueOf(3), 0.1);
			}
			if (!hitMatchs.containsKey(4)) {
				hitMatchs.put(Integer.valueOf(4), 0.1);
			}
			if (!hitMatchs.containsKey(5)) {
				hitMatchs.put(Integer.valueOf(5), 0.1);
			}
			if (!hitMatchs.containsKey(6)) {
				hitMatchs.put(Integer.valueOf(6), 0.1);
			}
			if (!hitMatchs.containsKey(7)) {
				hitMatchs.put(Integer.valueOf(7), 0.1);
			}
			if (!hitMatchs.containsKey(8)) {
				hitMatchs.put(Integer.valueOf(8), 0.1);
			}
			if (!hitMatchs.containsKey(9)) {
				hitMatchs.put(Integer.valueOf(9), 0.1);
			}
			if (!hitMatchs.containsKey(10)) {
				hitMatchs.put(Integer.valueOf(10), 0.1);
			}
			hitCandidateDTO.setMatchScore(hitMatchs);
			invHit.addObject("investigationForm", hitCandidateDTO);
		} catch (Exception e) {
			invHit.addObject("investigationForm", null);
		}
	}

	private void processTextualMatchInfo(String hitTransactionId, InvestigationHit invHit,
			Set<String> searchTypesForThisTransactionIdHit, Long workflowId) {

		if (Collections.disjoint(searchTypesForThisTransactionIdHit,
				NicSearchResult.TYPE_SEARCH__THAT_ARE_TEXT_BASED)) {
			invHit.addObject("hitCandidateHitInfo", null);
			return;
		}

		try {
			NicSearchHitResult nicSearchHitResult = this.uploadJobService.getAllSearchHitResult(workflowId,
					hitTransactionId, NicSearchResult.TYPE_SEARCH__THAT_ARE_TEXT_BASED,
					new NicSearchHitResultHitScorerTextualImpl()).get(0);

			String itemsLevel1[] = nicSearchHitResult.getHitInfo().split(NicSearchHitResult.hitInfo__ITEM__DELIMITER);
			List<NicSearchHitResultHitInfoItem> itemsForDisplay = new ArrayList<NicSearchHitResultHitInfoItem>();

			if (itemsLevel1 != null && itemsLevel1.length > 0) {
				for (String groupEntry : itemsLevel1) {
					String groupEntryTitle = StringUtils.substringBefore(groupEntry,
							NicSearchHitResult.hitInfo__SUB_ITEM__GROUP__DELIMITER);

					itemsForDisplay
							.add(new NicSearchHitResultHitInfoItem(
									this.codesService.getCodeValueDescByIdName(Codes.SRCH_HIT_RESULT__ITEM,
											groupEntryTitle, groupEntryTitle),
							this.getTextualHitInfoGroupItems(groupEntry)));
				}
			}

			invHit.addObject("hitCandidateHitInfo", itemsForDisplay);
		} catch (Exception e) {
			invHit.addObject("hitCandidateHitInfo", null);
		}

	}

	private List<NicSearchHitResultHitInfoItem> getTextualHitInfoGroupItems(String groupEntry) throws Exception {

		List<NicSearchHitResultHitInfoItem> subItemsForDisplay = new ArrayList<NicSearchHitResultHitInfoItem>();

		if (groupEntry.indexOf(NicSearchHitResult.hitInfo__SUB_ITEM__GROUP__DELIMITER) == -1) {
			logger.info("[NIC Investigation -1[" + subItemsForDisplay.size() + "]]");
			return subItemsForDisplay;
		}

		String groupEntryDataGroup = StringUtils.substringAfter(groupEntry,
				NicSearchHitResult.hitInfo__SUB_ITEM__GROUP__DELIMITER);

		String itemsLevel2[] = groupEntryDataGroup.split(NicSearchHitResult.hitInfo__SUB_ITEM__GROUP_ITEM__DELIMITER);

		if (itemsLevel2 == null || itemsLevel2.length == 0) {
			logger.info("[NIC Investigation itemsLevel2[" + subItemsForDisplay.size() + "]]");
			return subItemsForDisplay;
		}

		for (String groupEntryDataItem : itemsLevel2) {
			subItemsForDisplay.add(new NicSearchHitResultHitInfoItem(this.codesService.getCodeValueDescByIdName(
					Codes.SRCH_HIT_RESULT__ITEM_SUB_ITEM, groupEntryDataItem, groupEntryDataItem), null));
		}

		logger.info("[NIC Investigation[" + subItemsForDisplay.size() + "]]");
		return subItemsForDisplay;
	}

	private String getTransactionApplicantID(String transactionId) {

		if (transactionId == null) {
			return "";
		}

		try {
			String afisKeyNo = this.nicAfisDataService.findByTransactionId(transactionId).getAfisKeyNo();
			if (afisKeyNo == null) {
				return "";
			}
			return afisKeyNo;
		} catch (Exception e) {
			return "";
		}
	}

	private void processOneReferringToCodeTable(String key, String codeTableKey, String modelItemName,
			InvestigationHit investigationHit) {
		if (key == null) {
			investigationHit.addObject(modelItemName, "");
		} else {
			CodeValues codeValue = codesService.getCodeValueByIdName(codeTableKey, key);
			if (codeValue != null) {
				investigationHit.addObject(modelItemName, codeValue.getCodeValueDesc());
			} else {
				investigationHit.addObject(modelItemName, key);
			}
		}
	}

	private List<SearchHitDto> getAllHits(long jobId, String mainTransactionId) throws NicUploadJobServiceException {

		return uploadJobService.getAllHitsForJobId_1PerHitTransactionId_anySearchResultId(jobId, true, true,
				mainTransactionId);
	}

	private List<InvestigationHit> getAllHitsForModelAndView(ModelAndView modelAndView, NicUploadJob jobDetails,
			String mainTransactionId, List<SearchHitDto> hits) {
		
		return this.getAllHitsForModelAndView(  modelAndView,   jobDetails,
				  mainTransactionId,  hits, true);
		
	}

	private List<InvestigationHit> getAllHitsForModelAndView(ModelAndView modelAndView, NicUploadJob jobDetails,
			String mainTransactionId, List<SearchHitDto> hits, boolean includeHitCandidate) {

		List<InvestigationHit> invHits = new ArrayList<InvestigationHit>();

		try {
			InvestigationHit invHit_saveTime_mainCandidateInfo = null;

			for (SearchHitDto hit : hits) {
				try {
					InvestigationHit invHit = new InvestigationHit();
					{
						if (invHit_saveTime_mainCandidateInfo == null) {
							this.put1HitIntoModelAndView_main(modelAndView, jobDetails, mainTransactionId, invHit);
							{
								invHit_saveTime_mainCandidateInfo = new InvestigationHit();
								BeanUtils.copyProperties(invHit, invHit_saveTime_mainCandidateInfo);
							}
						} else {
							BeanUtils.copyProperties(invHit_saveTime_mainCandidateInfo, invHit);
						}
					}

					{
						if (includeHitCandidate) {
							this.put1HitIntoModelAndView_hit(modelAndView, jobDetails, mainTransactionId,
									hit.getSearchResultId(), hit.getHitTransactionId(), invHit,
									hit.getSearchResult_typeSearch(), hit.getSearchHitResult_hitInfo());
						}
					}

					invHits.add(invHit);
				} catch (Exception e) {
					e.printStackTrace();
					continue;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return invHits;
	}

	private void initializeModelAndViewCommonPageDetails(ModelAndView modelAndView, NicUploadJob jobDetails,
			String mainTransactionId) throws NicUploadJobServiceException {
		// formatdate
		String jobUploadTime = null;

		// date of application
		NicTransaction nicTransaction = nicTransactionService.findById(jobDetails.getTransactionId());
		if (nicTransaction != null) {
			jobUploadTime = DateUtil.parseDate(nicTransaction.getDateOfApplication(), "dd-MMM-yyyy");
		}
		modelAndView.addObject("jobUploadTime", jobUploadTime);

		String jobType = null;
		if (jobDetails != null && StringUtils.isNotBlank(jobDetails.getJobType())) {
			CodeValues codeValue = codesService.getCodeValueByIdName(Codes.JOB_TYPE, jobDetails.getJobType());

			if (codeValue != null) {
				jobType = codeValue.getCodeValueDesc();
			} else {
				jobType = jobDetails.getJobType();
			}
		}
		modelAndView.addObject("jobType", jobType);
		modelAndView.addObject("jobDetails", jobDetails);
	}

	private void initializeModelAndViewForms(ModelAndView mav) {
		mav.addObject("investigationHitData", new InvestigationHitData());
	}

	private void prepareModelStuff(Model model) {
		model.addAttribute("fnSelected", Constants.HEADING_NIC_INVESTIGATION);
	}

	private void logMessage(Exception ex) {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		PrintStream ps = new PrintStream(baos);
		ex.printStackTrace(ps);
		logger.error(ex.getMessage() + " : \n" + new String(baos.toByteArray()));
	}

	private List<CodeValues> getCodeValueList(String codeId) throws NicUploadJobServiceException {
		List<CodeValues> codeValueList = codesService.findAllByCodeId(codeId);
		if (codeValueList != null) {
			Collections.sort(codeValueList, new CodeValueDescComparator());
		}

		return codeValueList;
	}

	private byte[] handleJp2(byte[] image) {

		if (image == null) {
			return image;
		}

		// if (image != null) {
		// try {
		// return FileUtils.readFileToByteArray(new
		// File("C:/EPP-DEV/angeles/fileToConvert.xxx"));
		// } catch (Exception e) {
		// return null;
		// }
		// }

		boolean ignoreJ2k = false;
		{
			try {
				Parameters parameter = this.parametersService.getParamDetails(
						FraudCaseManagementController_PrintProcessor.PARA_TRANSACTION_ATTACHM_IGNORE_J2K_SCOPE,
						FraudCaseManagementController_PrintProcessor.PARA_TRANSACTION_ATTACHM_IGNORE_J2K_VALUE);
				if (parameter != null) {
					ignoreJ2k = Boolean.valueOf(parameter.getParaShortValue());
				}
			} catch (Exception e) {
			}
		}

		// get type
		String imageType = null;
		{
			try {
				imageType = new ImageUtil().checkImageType(image);
			} catch (Exception e) {
				return null;
			}
		}

		if (StringUtils.isBlank(imageType)) {
			return null;
		}

		if (imageType.equalsIgnoreCase(ImageUtil.IMAGE_J2K)) {
			if (ignoreJ2k) {
				return null;
			} else {
				boolean j2kConvertAtServer = false;
				{
					try {
						Parameters parameter = this.parametersService.getParamDetails(
								FraudCaseManagementController_PrintProcessor.PARA_TX_ATTCH_J2K_CONVERT_AT_SERVER_SCOPE,
								FraudCaseManagementController_PrintProcessor.PARA_TX_ATTCH_J2K_CONVERT_AT_SERVER_VALUE);
						if (parameter != null) {
							j2kConvertAtServer = Boolean.valueOf(parameter.getParaShortValue());
						}
					} catch (Exception e) {
					}
				}

				if (j2kConvertAtServer) {
					try {
						//return SpidHelper.getInstance("Y").convertImageFormat(image, SpidHelper.IMAGE_J2K,
								//SpidHelper.IMAGE_JPG);
					} catch (Exception e) {
					}
					return null;
				} else {
					return image;
				}
			}
		}

		return image;
	}

}
