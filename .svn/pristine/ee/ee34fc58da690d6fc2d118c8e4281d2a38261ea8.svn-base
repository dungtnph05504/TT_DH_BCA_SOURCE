/**
 * 
 */
package com.nec.asia.nic.comp.trans.service.impl;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.List;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.binary.Hex;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateUtils;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.ObjectWriter;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.nec.asia.nic.comp.job.dto.RequestLDSPack;
import com.nec.asia.nic.comp.perso.PersoManager;
import com.nec.asia.nic.comp.perso.model.PersonalizationData;
import com.nec.asia.nic.comp.trans.dao.DocumentDataDao;
import com.nec.asia.nic.comp.trans.dao.NicTransactionAttachmentDao;
import com.nec.asia.nic.comp.trans.dao.NicTransactionDao;
import com.nec.asia.nic.comp.trans.dao.NicTransactionLogDao;
import com.nec.asia.nic.comp.trans.domain.BaseModelSingle;
import com.nec.asia.nic.comp.trans.domain.NicDocumentData;
import com.nec.asia.nic.comp.trans.domain.NicDocumentDataId;
import com.nec.asia.nic.comp.trans.domain.NicRegistrationData;
import com.nec.asia.nic.comp.trans.domain.NicTransaction;
import com.nec.asia.nic.comp.trans.domain.NicTransactionAttachment;
import com.nec.asia.nic.comp.trans.domain.NicTransactionLog;
import com.nec.asia.nic.comp.trans.domain.NicUploadJob;
import com.nec.asia.nic.comp.trans.dto.LdsRequestWsDto;
import com.nec.asia.nic.comp.trans.dto.LdsResponseWsDto;
import com.nec.asia.nic.comp.trans.service.DataPackService;
import com.nec.asia.nic.comp.trans.service.NicUploadJobService;
import com.nec.asia.nic.comp.trans.service.PersoService;
import com.nec.asia.nic.comp.trans.service.exception.DataPackServiceException;
import com.nec.asia.nic.comp.trans.service.exception.NicUploadJobServiceException;
import com.nec.asia.nic.framework.admin.code.dao.ParametersDao;
import com.nec.asia.nic.framework.admin.code.domain.CodeValues;
import com.nec.asia.nic.framework.admin.code.domain.Codes;
import com.nec.asia.nic.framework.admin.code.domain.Parameters;
import com.nec.asia.nic.framework.admin.code.domain.ParametersId;
import com.nec.asia.nic.framework.admin.code.service.CodesService;
import com.nec.asia.nic.framework.exception.JaxbXmlConvertorException;
import com.nec.asia.nic.util.JobXmlConvertor;
import com.nec.asia.nic.util.MiscXmlConvertor;
import com.nec.asia.nic.utils.DateUtil;
import com.nec.lds.chip.ChipInfo;

/**
 * The implementation class of DataPackService, it is to integrate with
 * ePassport DLL to pack the DGs data, overall hash, construct the document
 * security object (SOD), sign SOD with CA services.
 * 
 * @author chris_wong
 *
 */
/*
 * Modification History:
 * 
 * 30 May 2017 (chris): implement sample data packing. 06 Jul 2017 (chris):
 * update nationality & country code
 */
@Service("dataPackService")
public class DataPackServiceImpl implements DataPackService {
	public final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private PersoServiceMBID60Impl persoService2;

	@Autowired
	private NicTransactionDao nicTransactionDao;

	@Autowired
	private NicTransactionAttachmentDao nicTransactionDocumentDao;

	@Autowired
	private NicTransactionLogDao nicTransactionLogDao;

	@Autowired
	private ParametersDao parametersDao;

	@Autowired
	private DocumentDataDao documentDataDao;

	@Autowired
	private CodesService codesService;

	@Autowired
	private NicUploadJobService uploadJobSerivce;

	private JobXmlConvertor util = new JobXmlConvertor();

	@Autowired
	private PersoManager persoManager;

	/*
	 * 1) To prepare payload and call LDS DataPacking method 2) To save the
	 * output xml to transaction document (optional) 3) To prepare complete data
	 * to perso xml format 4) To save the completed perso xml to transaction
	 * document 5) To save the transaction log
	 */
	public void preparePersoData(NicTransaction nicTransaction)
			throws DataPackServiceException {
		String transactionId = null;
		Date startTime = new Date();
		boolean error = false;
		String logData = null;
		NicUploadJob nicU = null;
		try {
			String officerId = SYSTEM_NIC; // "SYSTEM";
			String wkstnId = SYSTEM_NIC; // "SYSTEM";
			try {
				java.net.InetAddress localMachine = InetAddress.getLocalHost();
				wkstnId = localMachine.getHostName();
			} catch (Exception e) {
			}

			if (nicTransaction != null) {
				// Step 1 : To prepare payload
				transactionId = nicTransaction.getTransactionId();
				String nin = nicTransaction.getNin();
				Date dateOfIssue = this.generateDateOfIssue(nicTransaction);
				Date dateOfExpiry = null;
				BaseModelSingle<NicUploadJob> bGetUJ = uploadJobSerivce
						.getUploadJobByTransactionIdSinger(null, transactionId);
				nicU = bGetUJ.getModel();
				if (nicU == null || nicU.getEditTimeExpiry() == null) {
					dateOfExpiry = this.generateDateOfExpiry(nicTransaction,
							dateOfIssue);
				} else {
					dateOfExpiry = nicU.getEditTimeExpiry();
				}

				logger.info("[nicTransaction.getPrevPassportNo()]",
						nicTransaction.getPrevPassportNo());
				logger.info("[transactionId]: {}", transactionId);
				logger.info("[idNumber]: {}", nin);
				logger.info("[dateOfIssue]: {}", dateOfIssue);
				logger.info("[dateOfExpiry]: {}", dateOfExpiry);

				ChipInfo chipInfo = this.prepareChipInfo(nicTransaction,
						dateOfExpiry);

				/*
				 * Tạm đóng môi trường chưa đủ đk LDS.packLDS(chipInfo);
				 */
				// Step 2 : To save the output
				if (chipInfo != null) {
					logger.info(
							"[packLDS result] COM:{}, DG1:{}, DG2:{}, MRZ1:{}, MRZ2:{}",
							new Object[] { "" + chipInfo.getCom(),
									"" + chipInfo.getDg1(),
									"" + chipInfo.getDg2(), chipInfo.getMrz1(),
									chipInfo.getMrz2() });
					// TODO to check error code, if okay then save log and
					// return data.
					// Step 3 : To prepare complete data to perso xml format
					PersonalizationData data = this
							.preparePersonalizationData(nicTransaction,
									chipInfo, dateOfIssue, dateOfExpiry);
					/* Phúc thêm icao vào db 4/1/2020 */
					NicDocumentData nicData = documentDataDao
							.getNicDocumentDataById(
									nicTransaction.getTransactionId())
							.getModel();
					if (nicData != null) {
						logger.info("icao1: " + data.getMrz1() + " - icao2: "
								+ data.getMrz2());
						nicData.setIcaoLine1(data.getMrz1());
						nicData.setIcaoLine2(data.getMrz2());
						documentDataDao.saveOrUpdate(nicData);
					}
					/*--end--*/
					String dg3 = "";
					/*
					 * Tạm đóng môi trường chưa đủ đk try{
					 * if(chipInfo.hasDG3()){
					 * 
					 * String finger01Position = ""; String finger02Position =
					 * ""; byte[] finger01 = null; byte[] finger02 = null; //get
					 * encode fingers from transaction data if
					 * (StringUtils.isNotBlank
					 * (nicTransaction.getNicRegistrationData().getFpEncode()))
					 * { String[] fpEncodes =
					 * StringUtils.split(nicTransaction.getNicRegistrationData
					 * ().getFpEncode(), ","); if (fpEncodes.length>=1)
					 * finger01Position = fpEncodes[0]; if (fpEncodes.length>=2)
					 * finger02Position = fpEncodes[1];
					 * 
					 * finger01 = this.getTransactionDocument(nicTransaction,
					 * NicTransactionAttachment.DOC_TYPE_MINUTIA_CBEFF,
					 * finger01Position); finger02 =
					 * this.getTransactionDocument(nicTransaction,
					 * NicTransactionAttachment.DOC_TYPE_MINUTIA_CBEFF,
					 * finger02Position);
					 * 
					 * RequestLDSPack request = new RequestLDSPack();
					 * request.setPosition1(finger01Position);
					 * request.setPosition2(finger02Position);
					 * request.setsFinger1(Base64.encodeBase64String(finger01));
					 * request.setsFinger2(Base64.encodeBase64String(finger02));
					 * dg3 = GetDG3(request);//Data Dg3
					 * 
					 * if(StringUtils.isNotEmpty(dg3)){
					 * data.setDg3(dg3.replace("\"", "")); } } }
					 * }catch(Exception e){ logger.error("[{}]: {}",
					 * nicTransaction.getTransactionId(), e.getMessage()); }
					 */

					/*
					 * Tạm đóng môi trường chưa đủ đk String persoXml =
					 * persoManager.generateOutputXml(data);
					 */
					String persoXml = "<xml>Perso</xml>";
					logger.info("XML:  " + persoXml);
					// String persoXml = "persoXml";
					// Step 4 : To save the completed perso xml to transaction
					// document

					// to check save or update

					NicTransactionAttachment nicTransDocPerso = null;
					List<NicTransactionAttachment> docResultList = nicTransactionDocumentDao
							.findNicTransactionAttachments(transactionId,
									DOC_TYPE_PERSO,
									NicTransactionAttachment.DEFAULT_SERIAL_NO)
							.getListModel();
					if (CollectionUtils.isNotEmpty(docResultList)) { // update
						nicTransDocPerso = docResultList.get(0);
					}

					// save or upate
					if (nicTransDocPerso == null) {
						nicTransDocPerso = this.prepareTransDocumentDBO(
								transactionId, DOC_TYPE_PERSO, persoXml);
					} else {
						byte[] persoData = org.apache.commons.codec.binary.StringUtils
								.getBytesUtf8(persoXml);
						nicTransDocPerso.setDocData(Base64
								.encodeBase64(persoData));
						nicTransDocPerso.setUpdateBy(officerId);
						nicTransDocPerso.setUpdateDatetime(new Date());
						nicTransDocPerso.setCreateWkstnId(wkstnId);
					}
					nicTransactionDocumentDao.saveOrUpdate(nicTransDocPerso);
					persoService2.submitPersoDataNew(nicTransaction, "",
							chipInfo, null, null);
				}
			}

		} catch (Exception e) {
			logData = MiscXmlConvertor.parseObjectToXml(e);
			throw new DataPackServiceException(e);
		} catch (Throwable t) {
			logData = MiscXmlConvertor.parseObjectToXml(t);
			throw new DataPackServiceException(t);
		} finally {
			// Step 5 : To save the transaction log
			try {
				if (error) {
					this.saveTransactionLog(transactionId,
							TRANSACTION_STATUS_DATA_PREPARATION_ERROR,
							startTime, new Date(), null, logData);
				} else {
					this.saveTransactionLog(transactionId,
							TRANSACTION_STATUS_DATA_PREPARATION_SUCCESS,
							startTime, new Date(), null, null);
				}
			} catch (Exception e) {
				logger.error(
						"Exception in finally block (DataPackService.preparePersoData)",
						e);
			}
		}
	}

	public LdsResponseWsDto preparePersoData_V5(NicTransaction nicTransaction)
			throws DataPackServiceException {
		String transactionId = null;
		Date startTime = new Date();
		String logData = null;
		LdsResponseWsDto res = null;
		try {
			String officerId = SYSTEM_NIC; // "SYSTEM";
			String wkstnId = SYSTEM_NIC; // "SYSTEM";
			try {
				java.net.InetAddress localMachine = InetAddress.getLocalHost();
				wkstnId = localMachine.getHostName();
			} catch (Exception e) {
			}

			if (nicTransaction != null) {
				// Step 1 : To prepare payload
				transactionId = nicTransaction.getTransactionId();
				String nin = nicTransaction.getNin();
				Date dateOfIssue = this.generateDateOfIssue(nicTransaction);
				Date dateOfExpiry = null;
				BaseModelSingle<NicUploadJob> bGetUJ = uploadJobSerivce
						.getUploadJobByTransactionIdSinger(null, transactionId);
				NicUploadJob nicU = bGetUJ.getModel();
				if (nicU == null || nicU.getEditTimeExpiry() == null) {
					dateOfExpiry = this.generateDateOfExpiry(nicTransaction,
							dateOfIssue);
				} else {
					dateOfExpiry = nicU.getEditTimeExpiry();
				}

				ChipInfo chipInfo = this.prepareChipInfo(nicTransaction,
						dateOfExpiry);

				// Step 2 : To save the output
				if (chipInfo != null) {
					ParametersId id = new ParametersId();
					id.setParaName(Parameters.WS_LDS_API);
					id.setParaScope("API");
					Parameters param = parametersDao.findById(id);

					if (param != null
							&& StringUtils.isNotBlank(param.getParaLobValue())) {
						LdsRequestWsDto request = new LdsRequestWsDto();
						request.setDateOfBirth(chipInfo.getDateOfBirth());
						request.setDateOfExpiry(chipInfo.getDateOfExpiry());
						request.setDocumentNumber(chipInfo.getDocumentNumber());
						request.setDocumentType(chipInfo.getDocumentType());
						request.setFinger01(Base64.encodeBase64String(chipInfo
								.getFingerprint01()));
						request.setFinger01Position(chipInfo
								.getFinger01Position());
						request.setFinger02(Base64.encodeBase64String(chipInfo
								.getFingerprint02()));
						request.setFinger02Position(chipInfo
								.getFinger02Position());
						request.setIsEPassport(nicTransaction.getIsEpassport());
						request.setIssueState(chipInfo.getIssueState());
						request.setNameOfHolder(nicTransaction
								.getNicRegistrationData().getSearchName());
						request.setNationality(chipInfo.getNationality());
						request.setOptionalData(chipInfo.getOptionalData());
						request.setPhoto(Base64.encodeBase64String(chipInfo
								.getPhoto()));
						request.setSex(chipInfo.getSex());
						res = GetLDS(request, param.getParaLobValue());

					}

					if (res != null && res.getStatus() == 200) {
						chipInfo.setCom(res.getCom());
						chipInfo.setSod(res.getSod());
						chipInfo.setDg1(res.getDg1());
						chipInfo.setDg2(res.getDg2());
						chipInfo.setDg3(res.getDg3());
						chipInfo.setDg4(res.getDg4());

						chipInfo.setMrz1(res.getMrzLine1());
						chipInfo.setMrz2(res.getMrzLine2());

						// TODO to check error code, if okay then save log and
						// return data.
						// Step 3 : To prepare complete data to perso xml format
						PersonalizationData data = this
								.preparePersonalizationData(nicTransaction,
										chipInfo, dateOfIssue, dateOfExpiry);
						String persoXml = persoManager.generateOutputXml(data);

						// Step 4 : To save the completed perso xml to
						// transaction document
						res.setXml(persoXml);
						// to check save or update
						if (dateOfExpiry != null) {
							String date = DateUtil.parseDate(dateOfExpiry,
									DateUtil.FORMAT_YYYYMMDD);
							res.setDateOfExpiry(date);
						}
						NicTransactionAttachment nicTransDocPerso = null;
						List<NicTransactionAttachment> docResultList = nicTransactionDocumentDao
								.findNicTransactionAttachments(
										transactionId,
										DOC_TYPE_PERSO,
										NicTransactionAttachment.DEFAULT_SERIAL_NO)
								.getListModel();
						if (CollectionUtils.isNotEmpty(docResultList)) { // update
							nicTransDocPerso = docResultList.get(0);
						}

						// save or upate
						if (nicTransDocPerso == null) {
							nicTransDocPerso = this.prepareTransDocumentDBO(
									transactionId, DOC_TYPE_PERSO, persoXml);
						} else {
							byte[] persoData = org.apache.commons.codec.binary.StringUtils
									.getBytesUtf8(persoXml);
							nicTransDocPerso.setDocData(Base64
									.encodeBase64(persoData));
							nicTransDocPerso.setUpdateBy(officerId);
							nicTransDocPerso.setUpdateDatetime(new Date());
							nicTransDocPerso.setCreateWkstnId(wkstnId);
						}
						nicTransactionDocumentDao
								.saveOrUpdate(nicTransDocPerso);

						NicDocumentDataId idDoc = new NicDocumentDataId();
						idDoc.setPassportNo(chipInfo.getDocumentNumber());
						idDoc.setTransactionId(transactionId);
						NicDocumentData nicdel = documentDataDao
								.findById(idDoc);
						if (nicdel == null) {
							persoService2.submitPersoDataNew(nicTransaction,
									"", chipInfo, dateOfIssue, dateOfExpiry);
						}
					}
				}
			}

		} catch (Exception e) {
			logData = MiscXmlConvertor.parseObjectToXml(e);
			throw new DataPackServiceException(e);
		} catch (Throwable t) {
			logData = MiscXmlConvertor.parseObjectToXml(t);
			throw new DataPackServiceException(t);
		} finally {
			// Step 5 : To save the transaction log
			try {
				if (StringUtils.isBlank(res.getXml())) {
					this.saveTransactionLog(transactionId,
							TRANSACTION_STATUS_DATA_PREPARATION_ERROR,
							startTime, new Date(), null, logData);
				} else {
					this.saveTransactionLog(transactionId,
							TRANSACTION_STATUS_DATA_PREPARATION_SUCCESS,
							startTime, new Date(), null, null);
				}
			} catch (Exception e) {
				logger.error(
						"Exception in finally block (DataPackService.preparePersoData)",
						e);
			}
		}
		return res;
	}

	@ExceptionHandler
	public LdsResponseWsDto GetLDS(LdsRequestWsDto request, String paramUrl)
			throws JsonParseException, JsonMappingException, IOException {
		LdsResponseWsDto response = null;
		String urlParameters = "IsEPassport="
				+ (request.getIsEPassport() ? "true" : "false")
				+ "&DocumentType=" + request.getDocumentType() + "&IssueState="
				+ request.getIssueState() + "&DateOfBirth="
				+ request.getDateOfBirth() + "&DateOfExpiry="
				+ request.getDateOfExpiry() + "&DocumentNumber="
				+ request.getDocumentNumber() + "&NameOfHolder="
				+ request.getNameOfHolder() + "&Nationality="
				+ request.getNationality() + "&OptionalData="
				+ request.getOptionalData() + "&Sex=" + request.getSex()
				+ "&Photo=" + request.getPhoto() + "&Finger01="
				+ request.getFinger01() + "&Finger01Position="
				+ request.getFinger01Position() + "&Finger02="
				+ request.getFinger02() + "&Finger02Position="
				+ request.getFinger02Position();

		byte[] postData = urlParameters.getBytes(StandardCharsets.UTF_8);
		int postDataLength = postData.length;
		URL url = new URL(paramUrl);
		HttpURLConnection connection = (HttpURLConnection) url.openConnection();
		connection.setDoOutput(true);
		connection.setInstanceFollowRedirects(false);
		connection.setRequestMethod("POST");
		connection.setRequestProperty("Content-Type",
				"application/x-www-form-urlencoded");
		connection.setRequestProperty("Content-Length",
				Integer.toString(postDataLength));
		connection.setUseCaches(false);
		try (DataOutputStream wr = new DataOutputStream(
				connection.getOutputStream())) {
			wr.write(postData);
		}

		int statusCode = connection.getResponseCode();
		if (statusCode == 200) {
//			InputStream content = connection.getInputStream();
//			InputStreamReader isr = new InputStreamReader(content);
//
//			int numCharsRead;
//			char[] charArray = new char[1024];
//			StringBuffer sb = new StringBuffer();
//			while ((numCharsRead = isr.read(charArray)) > 0) {
//				sb.append(charArray, 0, numCharsRead);
//			}
			BufferedReader buff = new BufferedReader(new InputStreamReader(
					connection.getInputStream(), "UTF-8"));
//			String st = sb.toString();
			String st = buff.readLine();
			JSONObject myResponse = new JSONObject(st);
			response = new LdsResponseWsDto();
			response.setStatus(myResponse.getInt("Status"));
			response.setMessage(myResponse.getString("Message"));
			if (!myResponse.isNull("Com"))
				response.setCom(myResponse.getString("Com").getBytes());
			if (!myResponse.isNull("Sod"))
				response.setSod(myResponse.getString("Sod").getBytes());
			if (!myResponse.isNull("Dg1"))
				response.setDg1(myResponse.getString("Dg1").getBytes());
			if (!myResponse.isNull("Dg2"))
				response.setDg2(myResponse.getString("Dg2").getBytes());
			if (!myResponse.isNull("Dg3"))
				response.setDg3(myResponse.getString("Dg3").getBytes());
			if (!myResponse.isNull("Dg4"))
				response.setDg4(myResponse.getString("Dg4").getBytes());
			response.setMrzLine1(myResponse.getString("MrzLine1"));
			response.setMrzLine2(myResponse.getString("MrzLine2"));
		}
		return response;
	}

	private Date generateDateOfIssue(NicTransaction nicTransaction) {
		Date dateOfIssue = new Date();
		// TODO get date of approval
		return dateOfIssue;
	}

	private Date generateDateOfExpiry(NicTransaction nicTransaction,
			Date dateOfIssue) {
		Date dateOfExpiry = null;
		if (nicTransaction != null) {
			Date prevDateOfExpiry = dateOfIssue;
			// to get previous date of expiry
			// if (StringUtils.isNotBlank(nicTransaction.getPrevPassportNo())) {
			// List<NicDocumentData> documentDataList =
			// this.documentDataDao.findAllByDocNumber(nicTransaction.getPrevPassportNo());
			// for (NicDocumentData documentData : documentDataList) {
			// if (documentData.getDateOfExpiry()!=null &&
			// DateUtil.isAfterDate(documentData.getDateOfExpiry(),
			// prevDateOfExpiry) ) {
			// prevDateOfExpiry = documentData.getDateOfExpiry();
			// }
			// }
			// }
			logger.debug("TransactionId[{}] should be start from {} ",
					nicTransaction.getTransactionId(), prevDateOfExpiry);
			/* tính tuổi */
			NicRegistrationData nicReg = nicTransaction
					.getNicRegistrationData();
			if (nicReg != null) {
				if (nicReg.getDateOfBirth() != null) {
					Long now = new Date().getTime() / (86400 * 1000L);
					Long birthDay = nicReg.getDateOfBirth().getTime()
							/ (86400 * 1000L);
					String paramName = "";
					long age = (now - birthDay) / 365;
					if (age < 14
							/*|| (age == 14 && age * 365 == (now - birthDay))*/) {
						paramName = Parameters.PARA_NAME_BELOW_14;
					} else {
						paramName = Parameters.PARA_NAME_ABOVE_14;
					}
					ParametersId id = new ParametersId(
							Parameters.PARA_SCOPE_VALIDITY_PERIOD, paramName);
					Parameters param = parametersDao.findById(id);
					if (param != null) {
						dateOfExpiry = DateUtils.addYears(prevDateOfExpiry,
								Integer.parseInt(param.getParaShortValue()));
					}
				}
			}
			// if (nicTransaction.getValidityPeriod()!=null) {
			// int numberOfYear = nicTransaction.getValidityPeriod();
			// dateOfExpiry = DateUtils.addYears(prevDateOfExpiry,
			// numberOfYear);
			// }
			logger.debug(
					"TransactionId[{}] should be validate for {} years until {} ",
					new Object[] { nicTransaction.getTransactionId(),
							nicTransaction.getValidityPeriod(), dateOfExpiry });
		}
		return dateOfExpiry;
	}

	private ChipInfo prepareChipInfo(NicTransaction nicTransaction,
			Date dateOfExpiry) throws JaxbXmlConvertorException,
			NicUploadJobServiceException {
		ChipInfo chipInfo = new ChipInfo();
		// DG1
		NicRegistrationData regData = nicTransaction.getNicRegistrationData();
		String dob = DateUtil.parseDate(regData.getDateOfBirth(),
				DateUtil.FORMAT_YYMMDD);
		String doe = DateUtil.parseDate(dateOfExpiry, DateUtil.FORMAT_YYMMDD);
		String passportNo = "";
		String passportType = nicTransaction.getPassportType();
		String issueState = "VNM"; // nicTransaction.getIssuingAuthority();
		String nationality = regData.getNationality();
		String nameOfHolder = "";
		nameOfHolder = this.getNameOfHolder(regData.getSurnameFull(),
				regData.getFirstnameFull(), regData.getMiddlenameFull());
		// assigned by inventory system
		// passportNo = documentDataDao.getNextPassportNo(); //Đóng tự động gen
		// số hộ chiếu
		// passportNo =
		// documentDataDao.getNicDocumentDataById(nicTransaction.getTransactionId()).getId().getPassportNo();//
		// [09-Mar] Trung lấy dữ liệu sô hộ chiếu từ db
		// 16-04 - Trung - Lấy số hộ chiếu trong bảng Workflow Job
		passportNo = uploadJobSerivce.getUploadJobByTransactionId(regData
				.getTransactionId());
		chipInfo.setHasDG1(true);
		chipInfo.setDateOfBirth(dob);
		chipInfo.setDateOfExpiry(doe);
		chipInfo.setDocumentNumber(passportNo);
		chipInfo.setDocumentType(passportType);
		chipInfo.setIssueState(issueState);
		chipInfo.setNameOfHolder(nameOfHolder);
		chipInfo.setSurnameStart(1);
		chipInfo.setSurnameLen(regData.getSurnameFull().length());
		chipInfo.setNationality(nationality);
		chipInfo.setOptionalData(StringUtils.isNotBlank(nicTransaction.getNin())? nicTransaction.getNin() : "");
		chipInfo.setSex(regData.getGender());

		byte[] mugshotColour = null;
		// get mugshot from transaction data
		mugshotColour = this.getTransactionDocument(nicTransaction,
				NicTransactionAttachment.DOC_TYPE_PHOTO_CHIP,
				NicTransactionAttachment.DEFAULT_SERIAL_NO);

		if (mugshotColour != null) {
			chipInfo.setHasDG2(true);
			chipInfo.setPhoto(mugshotColour);
			logger.info("[DG2]: included:{} size:{} ",
					new Object[] { chipInfo.hasDG2(), mugshotColour.length });
		} else {
			chipInfo.setHasDG2(false);
			// TODO throw data exception
		}

		String finger01Position = "";
		String finger02Position = "";
		byte[] finger01 = null;
		byte[] finger02 = null;
		// get encode fingers from transaction data
		if (StringUtils.isNotBlank(regData.getFpEncode())) {
			String[] fpEncodes = StringUtils.split(regData.getFpEncode(), ",");
			if (fpEncodes.length >= 1)
				finger01Position = fpEncodes[0];
			if (fpEncodes.length >= 2)
				finger02Position = fpEncodes[1];

			finger01 = this.getTransactionDocument(nicTransaction,
					NicTransactionAttachment.DOC_TYPE_MINUTIA_CBEFF,
					finger01Position);
			finger02 = this.getTransactionDocument(nicTransaction,
					NicTransactionAttachment.DOC_TYPE_MINUTIA_CBEFF,
					finger02Position);
		}

		if (StringUtils.isNotBlank(finger01Position) && finger01 != null) {
			chipInfo.setHasDG3(true);
			chipInfo.setFinger01Position(finger01Position);
			chipInfo.setFinger02Position(finger02Position);
			chipInfo.setFingerprint01(finger01);
			chipInfo.setFingerprint02(finger02);
			int size1 = finger01.length;
			int size2 = (finger02 != null) ? finger02.length : 0;

			logger.info(
					"[DG3]: included:{} fp1:{} fp2:{} ; size1:{} size2:{} ",
					new Object[] { chipInfo.hasDG3(),
							chipInfo.getFinger01Position(),
							chipInfo.getFinger02Position(), size1, size2 });
		} else {
			chipInfo.setHasDG3(false);
			// TODO throw data exception if required
		}

		chipInfo.setHasDG4(false);
		// chipInfo.setIris01Position("1");
		// chipInfo.setIris02Position("2");
		// chipInfo.setIris01(iris01);
		// chipInfo.setIris02(iris02);a

		return chipInfo;
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

	private String getGivenName(String firstname, String middlename) {
		String getGivenName = "";

		if (StringUtils.isNotEmpty(middlename)) {
			getGivenName += StringUtils.trim(middlename);
		}
		if (StringUtils.isNotEmpty(firstname)) {
			if (StringUtils.length(getGivenName) > 0) {
				getGivenName += " ";
			}
			getGivenName += StringUtils.trim(firstname);
		}

		return getGivenName;
	}

	private PersonalizationData preparePersonalizationData(
			NicTransaction nicTransaction, ChipInfo chipInfo, Date dateOfIssue,
			Date dateOfExpiry) throws UnsupportedEncodingException {
		PersonalizationData data = new PersonalizationData();

		// String dateFormat = DateUtil.FORMAT_YYYYMMDD;
		// String dateFormat = DateUtil.FORMAT_DD_MM_YYYY;
		String dateFormat = PersoService.DATE_FORMAT_FOR_PERSO;

		// DG1
		NicRegistrationData regData = nicTransaction.getNicRegistrationData();
		String surname = StringUtils.trim(regData.getSurnameFull());
		String givenName = this.getGivenName(regData.getFirstnameFull(),
				regData.getMiddlenameFull()); // StringUtils.trim(regData.getFirstnameFull()+" "+regData.getMiddlenameFull());
		String dob = DateUtil.parseDate(regData.getDateOfBirth(), dateFormat);
		String doi = DateUtil.parseDate(dateOfIssue, dateFormat);
		String doe = DateUtil.parseDate(dateOfExpiry, dateFormat);
		// String nationality = "VIETNAMESE"; //regData.getNationality()
		String nationality = this.codesService.getCodeValueDescByIdName(
				Codes.NATIONALITY, regData.getNationality(), "VIETNAMESE");
		String jobName = "ID60Job_"
				+ DateUtil.parseDate(new Date(),
						DateUtil.FORMAT_YYYYMMDDHHMMSSSSS); // = null /* note:
															// cannot use dot */
		String type = null;
		String priority = nicTransaction.getPriority() != null ? nicTransaction
				.getPriority().toString() : "";
		String pob = this.codesService.getCodeValueDescByIdName(
				Codes.PLACEOFBIRTH, regData.getPlaceOfBirth(),
				regData.getPlaceOfBirth());
		data = new PersonalizationData(jobName, type, priority);
		data.setTransactionId(nicTransaction.getTransactionId());
		data.setPassportType(chipInfo.getDocumentType());
		data.setCountryCode(chipInfo.getIssueState());
		data.setDocumentNumber(chipInfo.getDocumentNumber());
		data.setLastName(surname);
		data.setFirstName(givenName);
		// data.setMiddleName("");
		data.setFullName(chipInfo.getNameOfHolder());
		data.setNationality(nationality);
		data.setNationalIdNumber(chipInfo.getOptionalData());
		data.setSex(chipInfo.getSex());
		data.setDateOfBirth(dob); // YYYYMMDD
		data.setPlaceOfBirth(pob);
		data.setDateOfIssue(doi); // YYYYMMDD
		/* Phúc Convert issuingAuthority 6/1/2020 */
		// data.setPlaceOfIssue(nicTransaction.getIssuingAuthority());
		CodeValues code = codesService.getCodeValueByIdName(
				"ISSUING_AUTHORITY", nicTransaction.getIssuingAuthority());
		data.setPlaceOfIssue(code != null ? code.getCodeValueDesc() : null);
		/*-- end --*/
		data.setDateOfExpiry(doe);
		byte[] mugshotForPrint = null;
		// get mugshot from transaction data
		mugshotForPrint = this.getTransactionDocument(nicTransaction,
				NicTransactionAttachment.DOC_TYPE_PHOTO_CAPTURE,
				NicTransactionAttachment.DEFAULT_SERIAL_NO);
		data.setPhotoInline64Encoded(Base64.encodeBase64String(mugshotForPrint));
		// data.setFingerprint1Inline(fingerprint1Inline);
		// data.setFingerprint1Position("1");
		// data.setFingerprint2Inline(fingerprint2Inline);
		// data.setFingerprint2Position("6");

		// for chip encoding
		data.setMrz1(chipInfo.getMrz1());
		data.setMrz2(chipInfo.getMrz2());
		if (chipInfo.getCom() != null && chipInfo.getCom().length > 0)
			data.setCom(Hex.encodeHexString(chipInfo.getCom()).toUpperCase()); // --
																				// Tam
																				// dong
																				// 15/02/2020
		if (chipInfo.getDg1() != null && chipInfo.hasDG1())
			data.setDg1(Hex.encodeHexString(chipInfo.getDg1()).toUpperCase());
		if (chipInfo.getDg2() != null && chipInfo.hasDG2())
			data.setDg2(Hex.encodeHexString(chipInfo.getDg2()).toUpperCase());
		if (chipInfo.getDg3() != null && chipInfo.hasDG3()) {
			data.setDg3(Hex.encodeHexString(chipInfo.getDg3()).toUpperCase());
		}
		if (chipInfo.getSod() != null && chipInfo.getSod().length > 0)
			data.setSod(Hex.encodeHexString(chipInfo.getSod()).toUpperCase()); // --
																				// Tam
																				// dong
																				// 15/02/2020
		return data;
	}

	public byte[] getTransactionDocument(NicTransaction nicTransaction,
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
		logger.info("[data not found]: {} {} {}",
				new Object[] { nicTransaction.getTransactionId(), docType,
						serialNo });
		return null;
	}

	private void saveTransactionLog(String transactionId,
			String transactionStatus, Date startTime, Date endTime,
			String logInfo, String logData) {
		String officerId = "SYSTEM";
		String wkstnId = "SYSTEM";
		try {
			java.net.InetAddress localMachine = InetAddress.getLocalHost();
			wkstnId = localMachine.getHostName();
		} catch (Exception e) {
		}

		NicTransactionLog transactionLog = new NicTransactionLog();
		transactionLog.setRefId(transactionId);
		transactionLog.setLogCreateTime(new Date());
		transactionLog.setTransactionStage(TRANSACTION_STATE_DATA_PREPARATION);
		transactionLog.setTransactionStatus(transactionStatus);
		transactionLog.setSiteCode(this.getCurrentSiteCodeFromParameter()); // get
																			// from
																			// 'CURRENT_SITE_CODE'
		transactionLog.setStartTime(startTime);
		transactionLog.setEndTime(endTime);
		transactionLog.setLogInfo(logInfo);
		transactionLog.setLogData(logData);
		transactionLog.setOfficerId(officerId);
		transactionLog.setWkstnId(wkstnId);

		synchronized (NicTransactionLog.class) {
			nicTransactionLogDao.save(transactionLog);
		}
	}

	private int getSamKeyVersionFromParameter() {
		int keyVersion = 0;
		Parameters parameter = parametersDao.findById(new ParametersId(
				PARA_SCOPE_SYSTEM, PARA_NAME_SAM_KEY_VERSION));
		if (parameter != null) {
			keyVersion = (Integer) parameter.getParaValue();
		} else {
			logger.warn("No matching Parameter for {} , {} ",
					PARA_SCOPE_SYSTEM, PARA_NAME_SAM_KEY_VERSION);
		}
		return keyVersion;
	}

	private String getCurrentSiteCodeFromParameter() {
		String currentSiteCode = null;
		Parameters parameter = parametersDao.findById(new ParametersId(
				PARA_SCOPE_SYSTEM, PARA_NAME_CURRENT_SITE_CODE));
		if (parameter != null) {
			currentSiteCode = (String) parameter.getParaValue();
		} else {
			logger.warn("No matching Parameter for {} , {} ",
					PARA_SCOPE_SYSTEM, PARA_NAME_CURRENT_SITE_CODE);
		}
		return currentSiteCode;
	}

	private NicTransactionAttachment prepareTransDocumentDBO(
			String transactionId, String docType, String dataPackXml)
			throws UnsupportedEncodingException {
		NicTransactionAttachment nicTransDoc = new NicTransactionAttachment();
		nicTransDoc.setTransactionId(transactionId);
		nicTransDoc.setSerialNo(NicTransactionAttachment.DEFAULT_SERIAL_NO);
		nicTransDoc.setDocType(docType);
		// nicTransDoc.setDocData(dataPackXml.getBytes());
		nicTransDoc.setDocData(Base64.encodeBase64(dataPackXml
				.getBytes("UTF-8")));
		nicTransDoc.setCreateBy("SYSTEM");
		nicTransDoc.setCreateDatetime(new Date());
		nicTransDoc.setCreateWkstnId("SYSTEM");
		return nicTransDoc;
	}

	private String GetDG3(RequestLDSPack urlParameters) {
		String res = null;
		try {
			URL url = new URL("http://192.168.1.27:700/LDSPack/CreateDG3");
			ObjectWriter ow = new ObjectMapper().writer()
					.withDefaultPrettyPrinter();
			String json = ow.writeValueAsString(urlParameters);
			HttpURLConnection connection = (HttpURLConnection) url
					.openConnection();
			connection.setRequestMethod("POST");
			connection.setRequestProperty("Content-Type",
					"application/json; charset=UTF-8");
			connection.setRequestProperty("Accept", "application/json");
			connection.setDoOutput(true);
			OutputStream os = connection.getOutputStream();
			os.write(json.toString().getBytes("UTF-8"));
			os.close();

			int statusCode = connection.getResponseCode();
			if (statusCode == 200) {
				InputStream content = connection.getInputStream();
				Charset charset = Charset.forName("UTF8");
				InputStreamReader isr = new InputStreamReader(content, charset);

				int numCharsRead;
				char[] charArray = new char[1024];
				StringBuffer sb = new StringBuffer();
				while ((numCharsRead = isr.read(charArray)) > 0) {
					sb.append(charArray, 0, numCharsRead);
				}
				res = sb.toString();
				if (StringUtils.isNotEmpty(res))
					res.replace("\"", "");
			} else {
				// /Trả về log mã lỗi và thông tin lỗi
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return res;
	}

}
