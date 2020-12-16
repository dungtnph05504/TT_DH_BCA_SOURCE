/**
 * 
 */
package com.nec.asia.nic.comp.trans.service.impl;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.InetAddress;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.nec.asia.nic.comp.integration.FileTransferService;
import com.nec.asia.nic.comp.perso.PersoManager;
import com.nec.asia.nic.comp.trans.dao.DocumentDataDao;
//import com.nec.asia.nic.comp.trans.dao.NicIssuanceDataDao;
import com.nec.asia.nic.comp.trans.dao.NicRegistrationDataDao;
import com.nec.asia.nic.comp.trans.dao.NicTransactionAttachmentDao;
import com.nec.asia.nic.comp.trans.dao.NicTransactionDao;
import com.nec.asia.nic.comp.trans.dao.NicTransactionLogDao;
import com.nec.asia.nic.comp.trans.dao.NicUploadJobDao;
import com.nec.asia.nic.comp.trans.domain.NicDocumentData;
import com.nec.asia.nic.comp.trans.domain.NicDocumentDataId;
import com.nec.asia.nic.comp.trans.domain.NicTransaction;
import com.nec.asia.nic.comp.trans.domain.NicTransactionAttachment;
import com.nec.asia.nic.comp.trans.dto.ResponseDTO;
import com.nec.asia.nic.comp.trans.service.NicTransactionService;
import com.nec.asia.nic.comp.trans.service.PersoService;
import com.nec.asia.nic.comp.trans.service.TransactionLogService;
import com.nec.asia.nic.comp.trans.service.exception.PersoServiceException;
import com.nec.asia.nic.comp.trans.service.exception.TransactionServiceException;
import com.nec.asia.nic.framework.admin.code.dao.ParametersDao;
import com.nec.asia.nic.framework.admin.code.domain.Parameters;
import com.nec.asia.nic.framework.admin.code.domain.ParametersId;
import com.nec.asia.nic.framework.admin.code.service.CodesService;
import com.nec.asia.nic.util.JobXmlConvertor;
import com.nec.asia.nic.utils.DateUtil;
import com.nec.lds.chip.ChipInfo;
import com.sshtools.j2ssh.sftp.SftpFile;

/**
 * The implementation class of PersoService, it is to invoke with PERSO File
 * Service to submit the card personalisation request.
 * 
 * @author chris_wong
 */
/*
 * Modification History: 31 May 2017 (chris): add perso2 for temporary solution
 * with MB.
 */
@Service("persoServiceMBID60")
@Transactional
public class PersoServiceMBID60Impl implements PersoService {
	public final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private NicTransactionService nicTransactionService;

	@Autowired
	private NicTransactionAttachmentDao transactionDocumentDao;

	@Autowired
	private NicTransactionLogDao transactionLogDao;

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
	CodesService codesService;

	@Autowired
	NicUploadJobDao uploadJobDao;

	@Autowired
	private PersoManager persoManager;

	private String dataDirectory = StringUtils.EMPTY;

	private String statusDirectory = StringUtils.EMPTY;

	private String rootDirectory = StringUtils.EMPTY;

	private String host = StringUtils.EMPTY;

	private String userId = StringUtils.EMPTY;

	private String passkey = StringUtils.EMPTY;

	// 2015-Dec-12 (Khang) Change logic to submit perso data to SFTP server
	// instead of call web service
	public void submitPersoData(NicTransaction nicTransaction)
			throws PersoServiceException {
		String transactionId = null;
		String persoRefId = null;
		Date startTime = new Date();
		String logData = null;
		String transactionStatus = null;

		String officerId = SYSTEM_NIC;
		String wkstnId = SYSTEM_NIC;
		try {
			try {
				java.net.InetAddress localMachine = InetAddress.getLocalHost();
				wkstnId = localMachine.getHostName();
			} catch (Exception e) {
			}

			// Step 1: submit prepared data to LDS
			if (nicTransaction != null) {
				transactionId = nicTransaction.getTransactionId();
				String persoXml = this.retrievePersoXml(transactionId);

				logger.info("[before][persoWS.downloadJob] transactionId={}",
						transactionId);
				writePersoJob(transactionId, persoXml);
				logger.info(
						"[ after][persoWS.downloadJob] transactionId={}, persoRefId={}",
						transactionId, persoRefId);

				transactionStatus = TRANSACTION_STATUS_LDS_REGISTER_COMPLETED;
			}
		} catch (Exception e) {
			transactionStatus = TRANSACTION_STATUS_LDS_REGISTER_ERROR;
			throw new PersoServiceException(e);
		} finally {
			// /TRUNG TẠM ĐÓNG ĐỂ KHÔNG LƯU LOG PERSO -> CHỈ Ở DẠNG DATA PREPARE
			/*
			 * try {
			 * nicTransactionService.updateStatusByTransactionId(transactionId,
			 * transactionStatus, officerId, wkstnId); } catch
			 * (TransactionServiceException e) {
			 * logger.error("Failed to update transaction ["
			 * +transactionId+"] status ["+transactionStatus+"].", e); }
			 */
			Date endTime = new Date();
			logger.info("[submitPersoData] transactionId={}, taken={}",
					transactionId, endTime.getTime() - startTime.getTime());
		}
	}

	public void submitPersoDataNEW(NicTransaction nicTransaction,
			ChipInfo chipinfo, Date dateOfIssue, Date dateOfExpiry) throws PersoServiceException {
		String transactionId = null;
		Date startTime = new Date();
		try {
			// Step 1: submit prepared data to LDS
			if (nicTransaction != null) {
				transactionId = nicTransaction.getTransactionId();
				String persoXml = this.retrievePersoXml(transactionId);

				logger.info("[before][persoWS.downloadJob] transactionId={}",
						transactionId);
				String doi = DateUtil.parseDate(dateOfIssue,
						DateUtil.FORMAT_DD_MM_YYYY);
				// Date doE = DateUtils.addYears(new Date(),
				// nicTransaction.getValidityPeriod());
				String doe = DateUtil
						.parseDate(dateOfExpiry, DateUtil.FORMAT_DD_MM_YYYY);
				writePersoJobNew_V5(transactionId, persoXml, doi, doe,
						chipinfo.getDocumentNumber(), chipinfo.getMrz1(),
						chipinfo.getMrz2());
			}
		} catch (Exception e) {
			throw new PersoServiceException(e);
		} finally {
			Date endTime = new Date();
			logger.info("[submitPersoData] transactionId={}, taken={}",
					transactionId, endTime.getTime() - startTime.getTime());
		}
	}

	private String retrievePersoXml(String transactionId)
			throws UnsupportedEncodingException {
		String persoXml = null;
		/* "PERSO" */
		List<NicTransactionAttachment> docList = this.transactionDocumentDao
				.findNicTransactionAttachments(transactionId,
						NicTransactionAttachment.DOC_TYPE_PERSO,
						NicTransactionAttachment.DEFAULT_SERIAL_NO)
				.getListModel();
		if (CollectionUtils.isNotEmpty(docList)) {
			NicTransactionAttachment persoDoc = docList.get(0);
			byte[] persoXmlData = persoDoc.getDocData();

			persoXml = new String(Base64.decodeBase64(persoXmlData), "UTF-8");
			logger.debug("[retrievePersoXml] persoXml: {} ...",
					StringUtils.substring(persoXml, 0, 500));
		}
		return persoXml;
	}

	private void writePersoJob(String transactionId, String persoXml)
			throws PersoServiceException {
		if (StringUtils.isEmpty(rootDirectory)) {
			this.getFileInfo();
			if (StringUtils.isEmpty(rootDirectory)) {
				throw new PersoServiceException(
						"File Configuration is missing.");
			}
		}

		try {

			String networkFolderPath = rootDirectory + "/" + dataDirectory
					+ "/";
			persoManager.setAuthenticationInfo(host, userId, passkey,
					networkFolderPath);
			// byte[] persoData = persoXml.getBytes();
			// byte[] persoData = persoXml.getBytes("UTF-8"); //work in junit
			// but not work in jboss
			byte[] persoData = org.apache.commons.codec.binary.StringUtils
					.getBytesUtf8(persoXml); // same as getBytes("UTF-8")
			String fileName = "JOB-"
					+ DateUtil.parseDate(new Date(),
							DateUtil.FORMAT_YYYYMMDDHHMMSSSSS) + ".xml";
			persoManager.uploadFile(persoData, fileName);

			// for debug
			try {
				File directory = new File(networkFolderPath);
				if (!directory.exists()) {
					directory.mkdirs();
				}
				FileUtils.writeByteArrayToFile(new File(directory, fileName),
						persoData);

				fileName = "JOB-"
						+ DateUtil.parseDate(new Date(),
								DateUtil.FORMAT_YYYYMMDDHHMMSSSSS)
						+ ".bytes.UTF8" + ".xml";
				persoData = persoXml.getBytes("UTF-8");
				FileUtils.writeByteArrayToFile(new File(directory, fileName),
						persoData);
			} catch (Throwable t) {
				logger.error("Failed to write Perso XML to local file.", t);
			}
			saveDocumentInfo(transactionId, persoXml);
			// logger.debug("[writePersoJob]transactionId:{} write into job: {}",
			// transactionId, fileName);
		} catch (Exception e) {
			logger.error("Failed to upload Perso XML.", e);
			throw new PersoServiceException(
					"Unexpected Error encountered when writePersoJob.", e);
		}
	}

	private void writePersoJobNew(String transactionId, String persoXml,
			String doi, String doe, String ppNo) throws PersoServiceException {
		try {
			saveDocumentInfoNew(transactionId, persoXml, doi, doe, ppNo);
			// logger.debug("[writePersoJob]transactionId:{} write into job: {}",
			// transactionId, fileName);
		} catch (Exception e) {
			logger.error("Failed to upload Perso XML.", e);
			throw new PersoServiceException(
					"Unexpected Error encountered when writePersoJob.", e);
		}
	}

	private void writePersoJobNew_V5(String transactionId, String persoXml,
			String doi, String doe, String ppNo, String icao1, String icao2)
			throws PersoServiceException {
		try {
			saveDocumentInfoNew(transactionId, persoXml, doi, doe, ppNo, icao1,
					icao2);
			// logger.debug("[writePersoJob]transactionId:{} write into job: {}",
			// transactionId, fileName);
		} catch (Exception e) {
			logger.error("Failed to upload Perso XML.", e);
			throw new PersoServiceException(
					"Unexpected Error encountered when writePersoJob.", e);
		}
	}

	private void getFileInfo() throws PersoServiceException {
		ParametersId id = new ParametersId(PARA_SCOPE_SYSTEM,
				PARA_NAME_PERSO_FILE_CONFIG);
		Parameters paraObj = parametersDao.findById(id);
		if (paraObj != null) {
			try {
				/*
				 * #Perso File Properties FILE.PERSO.DATA.DIRECTORY=persoData
				 * FILE.PERSO.STATUS.DIRECTORY=persoStatus
				 * FILE.PERSO.ROOT.DIRECTORY=/perso FILE.HOST=172.16.2.30
				 * FILE.USERID=nic FILE.PASSKEY=Password1
				 */

				Properties properties = parametersDao.readProperties(paraObj);
				if (properties != null) {
					dataDirectory = properties
							.getProperty(FILE_PERSO_DATA_DIRECTORY);
					statusDirectory = properties
							.getProperty(FILE_PERSO_STATUS_DIRECTORY);
					rootDirectory = properties
							.getProperty(FILE_PERSO_ROOT_DIRECTORY);
					host = properties.getProperty(FILE_PERSO_HOST);
					userId = properties.getProperty(FILE_PERSO_USERID);
					passkey = properties.getProperty(FILE_PERSO_PASSKEY);

					if (StringUtils.isBlank(dataDirectory)
							|| StringUtils.isBlank(statusDirectory)
							|| StringUtils.isBlank(rootDirectory)) {
						throw new PersoServiceException(
								"Invalid File configuration for Perso");
					}
				} else {
					logger.warn("No matching Parameter for {} , {} ",
							PARA_SCOPE_SYSTEM, PARA_NAME_PERSO_SFTP_CONFIG);
				}
			} catch (Exception e) {
				throw new PersoServiceException(e);
			}
		}
	}

	// for demo only.
	private String saveDocumentInfo(String transactionId, String persoXml) {
		String documentNumber = "";
		// String printedSite = "HANOI";
		String printedSite = "IN_A08_MB";
		String doi = "";
		String doe = "";
		String dateFormat = PersoService.DATE_FORMAT_FOR_PERSO;

		final String DOC_NUM_START_TAG = "<DataField Name=\"DocumentNumber\">";
		final String DOC_NUM_END_TAG = "</DataField>";
		final String VALUE_START_TAG = "<Value InputFormat=\"Text\">";
		final String VALUE_END_TAG = "</Value>";

		final String DOI_START_TAG = "<DataField Name=\"DateOfIssue\">"; // YYYYMMDD
		final String DOE_START_TAG = "<DataField Name=\"DateOfExpiry\">"; // YYYYMMDD

		int docNumTagStart = StringUtils.indexOf(persoXml, DOC_NUM_START_TAG);
		if (docNumTagStart > 0) {
			int valueTagStart = StringUtils.indexOf(persoXml, VALUE_START_TAG,
					docNumTagStart);
			int valueTagEnd = StringUtils.indexOf(persoXml, VALUE_END_TAG,
					docNumTagStart);

			documentNumber = StringUtils.substring(persoXml, valueTagStart
					+ VALUE_START_TAG.length(), valueTagEnd);
		}

		int doiTagStart = StringUtils.indexOf(persoXml, DOI_START_TAG);
		if (doiTagStart > 0) {
			int valueTagStart = StringUtils.indexOf(persoXml, VALUE_START_TAG,
					doiTagStart);
			int valueTagEnd = StringUtils.indexOf(persoXml, VALUE_END_TAG,
					doiTagStart);

			doi = StringUtils.substring(persoXml, valueTagStart
					+ VALUE_START_TAG.length(), valueTagEnd);
		}

		int doeTagStart = StringUtils.indexOf(persoXml, DOE_START_TAG);
		if (doeTagStart > 0) {
			int valueTagStart = StringUtils.indexOf(persoXml, VALUE_START_TAG,
					doeTagStart);
			int valueTagEnd = StringUtils.indexOf(persoXml, VALUE_END_TAG,
					doeTagStart);

			doe = StringUtils.substring(persoXml, valueTagStart
					+ VALUE_START_TAG.length(), valueTagEnd);
		}

		logger.info(
				"[saveDocumentInfo]transactionId:{} documentNumber: {}, doi: {}, doe: {}",
				new Object[] { transactionId, documentNumber, doi, doe });
		String OFFICER_ID = "SYSTEM";
		String WKSTN_ID = "SYSTEM";
		String status = NicTransactionService.TRANSACTION_STATUS_PERSONALIZED;
		Date newDate = new Date();
		Date doiDate = DateUtil.strToDate(doi, dateFormat);
		Date doeDate = DateUtil.strToDate(doe, dateFormat);

		NicDocumentDataId id = new NicDocumentDataId(transactionId,
				documentNumber);
		NicDocumentData documentData = documentDataDao.findById(id);
		if (documentData == null) {
			documentData = new NicDocumentData();
			documentData.setId(id);
			documentData.setCreateBy(OFFICER_ID);
			documentData.setCreateDatetime(newDate);
			documentData.setCreateWkstnId(WKSTN_ID);
		}

		documentData.setStatus(status);
		documentData.setStatusUpdateTime(newDate);
		// documentData.setChipSerialNo();
		documentData.setPrintingSite(printedSite);
		documentData.setDateOfIssue(doiDate);
		documentData.setDateOfExpiry(doeDate);

		documentData.setUpdateBy(OFFICER_ID);
		documentData.setUpdateDatetime(newDate);
		documentData.setUpdateWkstnId(WKSTN_ID);

		// TRUNG TẠM ĐÓNG ĐỂ CHO SYSTEM PERSO XỬ LÝ SINH MÃ GÓI
		// String packageId = "PKG."+DateUtil.parseDate(new Date(),
		// DateUtil.FORMAT_YYYYMMDDHHMMSSSSS);
		String dispatchId = "DSP."
				+ DateUtil.parseDate(new Date(),
						DateUtil.FORMAT_YYYYMMDDHHMMSSSSS);
		// documentData.setPackageId(packageId);
		// documentData.setPackageDatetime(newDate);
		documentData.setDispatchId(dispatchId);
		documentData.setDispatchDatetime(newDate);

		boolean enableDocumentSaving = false;
		try {
			ParametersId paraId = new ParametersId();
			paraId.setParaName("PASSPORT_DATA_GENERATION");
			paraId.setParaScope("DEMO");
			Parameters parameter = parametersDao.findById(paraId);
			if (parameter != null) {
				String flag = parameter.getParaShortValue();

				if (StringUtils.isNotBlank(flag)) {
					if (Boolean.parseBoolean(flag)) {
						enableDocumentSaving = true;
					}
				}
			}
		} catch (Exception e) {
			logger.info("unable to retrieve parameter: {}", e.getMessage());
		}
		if (enableDocumentSaving) {
			logger.info("saving document: {}, {}", transactionId,
					documentNumber);
			documentDataDao.saveOrUpdate(documentData);
		} else {
			logger.info("skip saving for document: {}, {}", transactionId,
					documentNumber);
		}

		// data logging
		boolean hasValue = true;
		int dataTagStart = 0;
		int count = 0;
		do {
			int valueTagStart = StringUtils.indexOf(persoXml, VALUE_START_TAG,
					dataTagStart);
			int valueTagEnd = StringUtils.indexOf(persoXml, VALUE_END_TAG,
					dataTagStart);
			if (valueTagStart >= 0) {
				count++;
				String dataValue = StringUtils.substring(persoXml,
						valueTagStart + VALUE_START_TAG.length(), valueTagEnd);
				logger.info(
						"[saveDocumentInfo]transactionId:{} documentNumber: {}, value[{}]: {}",
						new Object[] { transactionId, documentNumber, count,
								dataValue });
				dataTagStart = valueTagEnd + VALUE_END_TAG.length();
			} else {
				hasValue = false;
			}
		} while (hasValue);

		return documentNumber;
	}

	private String saveDocumentInfoNew(String transactionId, String persoXml,
			String doi, String doe, String ppNo) {
		String documentNumber = "";
		// String printedSite = "HANOI";
		String printedSite = "IN_A08_MB";
		String dateFormat = PersoService.DATE_FORMAT_FOR_PERSO;
		documentNumber = ppNo;

		logger.info(
				"[saveDocumentInfo]transactionId:{} documentNumber: {}, doi: {}, doe: {}",
				new Object[] { transactionId, documentNumber, doi, doe });
		String OFFICER_ID = "SYSTEM";
		String WKSTN_ID = "SYSTEM";
		String status = NicTransactionService.TRANSACTION_STATUS_PERSONALIZED;
		Date newDate = new Date();
		Date doiDate = DateUtil.strToDate(doi, dateFormat);
		Date doeDate = DateUtil.strToDate(doe, dateFormat);

		NicDocumentDataId id = new NicDocumentDataId(transactionId,
				documentNumber);
		NicDocumentData documentData = documentDataDao.findById(id);
		if (documentData == null) {
			documentData = new NicDocumentData();
			documentData.setId(id);
			documentData.setCreateBy(OFFICER_ID);
			documentData.setCreateDatetime(newDate);
			documentData.setCreateWkstnId(WKSTN_ID);
		}

		documentData.setStatus(status);
		documentData.setStatusUpdateTime(newDate);
		// documentData.setChipSerialNo();
		documentData.setPrintingSite(printedSite);
		documentData.setDateOfIssue(doiDate);
		documentData.setDateOfExpiry(doeDate);

		documentData.setUpdateBy(OFFICER_ID);
		documentData.setUpdateDatetime(newDate);
		documentData.setUpdateWkstnId(WKSTN_ID);

		// TRUNG TẠM ĐÓNG ĐỂ CHO SYSTEM PERSO XỬ LÝ SINH MÃ GÓI
		// String packageId = "PKG."+DateUtil.parseDate(new Date(),
		// DateUtil.FORMAT_YYYYMMDDHHMMSSSSS);
		String dispatchId = "DSP."
				+ DateUtil.parseDate(new Date(),
						DateUtil.FORMAT_YYYYMMDDHHMMSSSSS);
		// documentData.setPackageId(packageId);
		// documentData.setPackageDatetime(newDate);
		documentData.setDispatchId(dispatchId);
		documentData.setDispatchDatetime(newDate);
		boolean enableDocumentSaving = false;
		try {
			ParametersId paraId = new ParametersId();
			paraId.setParaName("PASSPORT_DATA_GENERATION");
			paraId.setParaScope("DEMO");
			Parameters parameter = parametersDao.findById(paraId);
			if (parameter != null) {
				String flag = parameter.getParaShortValue();

				if (StringUtils.isNotBlank(flag)) {
					if (Boolean.parseBoolean(flag)) {
						enableDocumentSaving = true;
					}
				}
			}
		} catch (Exception e) {
			logger.info("unable to retrieve parameter: {}", e.getMessage());
		}
		if (enableDocumentSaving) {
			logger.info("saving document: {}, {}", transactionId,
					documentNumber);
			documentDataDao.saveOrUpdate(documentData);
		} else {
			logger.info("skip saving for document: {}, {}", transactionId,
					documentNumber);
		}

		return documentNumber;
	}

	private String saveDocumentInfoNew(String transactionId, String persoXml,
			String doi, String doe, String ppNo, String icao1, String icao2) {
		String documentNumber = "";
		// String printedSite = "HANOI";
		String printedSite = "IN_A08_MB";
		String dateFormat = PersoService.DATE_FORMAT_FOR_PERSO;
		documentNumber = ppNo;

		logger.info(
				"[saveDocumentInfo]transactionId:{} documentNumber: {}, doi: {}, doe: {}",
				new Object[] { transactionId, documentNumber, doi, doe });
		String OFFICER_ID = "SYSTEM";
		String WKSTN_ID = "SYSTEM";
		Parameters param = parametersDao.findById(new ParametersId("SYSTEM", "CURRENT_SYSTEM_ID"));
		if (param != null) {
			OFFICER_ID = param.getParaShortValue();
		}
		String status = NicTransactionService.TRANSACTION_STATUS_PACKED;
		Date newDate = new Date();
		Date doiDate = DateUtil.strToDate(doi, dateFormat);
		Date doeDate = DateUtil.strToDate(doe, dateFormat);
		NicTransaction nicTran = null;
		NicDocumentDataId id = new NicDocumentDataId(transactionId,
				documentNumber);
		NicDocumentData documentData = documentDataDao.findById(id);
		if (documentData == null) {
			documentData = new NicDocumentData();
			documentData.setId(id);
			documentData.setCreateBy(OFFICER_ID);
			documentData.setCreateDatetime(newDate);
			documentData.setCreateWkstnId(WKSTN_ID);

			try {
				nicTran = nicTransactionService.findTransactionById(
						transactionId).getModel();
			} catch (TransactionServiceException e) {
				logger.error("findTransactionById: " + transactionId
						+ " fail. " + e.getMessage());
			}
		} else {
			nicTran = documentData.getNicTransaction();
		}

		documentData.setStatus(status);
		documentData.setStatusUpdateTime(newDate);
		// documentData.setChipSerialNo();
		documentData.setPrintingSite(printedSite);
		documentData.setDateOfIssue(doiDate);
		documentData.setDateOfExpiry(doeDate);

		// documentData.setUpdateBy(OFFICER_ID);
		// documentData.setUpdateDatetime(newDate);
		// documentData.setUpdateWkstnId(WKSTN_ID);

		// TRUNG TẠM ĐÓNG ĐỂ CHO SYSTEM PERSO XỬ LÝ SINH MÃ GÓI
		// String packageId = "PKG."+DateUtil.parseDate(new Date(),
		// DateUtil.FORMAT_YYYYMMDDHHMMSSSSS);
		String dispatchId = "DSP."
				+ DateUtil.parseDate(new Date(),
						DateUtil.FORMAT_YYYYMMDDHHMMSSSSS);
		// documentData.setPackageId(packageId);
		// documentData.setPackageDatetime(newDate);
		documentData.setDispatchId(dispatchId);
		documentData.setDispatchDatetime(newDate);
		documentData.setIcaoLine1(icao1);
		documentData.setIcaoLine2(icao2);
		// Hoald thêm 17.08.2020
		if (nicTran != null) {
			documentData.setPassportType(nicTran.getPassportType());
			documentData.setPlaceOfIssueCode(nicTran.getIssuingAuthority());
		}
		boolean enableDocumentSaving = false;
		try {
			ParametersId paraId = new ParametersId();
			paraId.setParaName("PASSPORT_DATA_GENERATION");
			paraId.setParaScope("DEMO");
			Parameters parameter = parametersDao.findById(paraId);
			if (parameter != null) {
				String flag = parameter.getParaShortValue();

				if (StringUtils.isNotBlank(flag)) {
					if (Boolean.parseBoolean(flag)) {
						enableDocumentSaving = true;
					}
				}
			}
		} catch (Exception e) {
			logger.info("unable to retrieve parameter: {}", e.getMessage());
		}
		if (enableDocumentSaving) {
			logger.info("saving document: {}, {}", transactionId,
					documentNumber);
			documentDataDao.saveOrUpdate(documentData);
		} else {
			logger.info("skip saving for document: {}, {}", transactionId,
					documentNumber);
		}

		return documentNumber;
	}

	@Transactional(rollbackFor = PersoServiceException.class, propagation = Propagation.REQUIRED)
	public void receiveNicPersoInfo(String nicPersoInfoUploadXml,
			String xmlCheckSum) throws PersoServiceException {
		throw new PersoServiceException("Function not working.");

	}

	@Transactional(rollbackFor = PersoServiceException.class, propagation = Propagation.REQUIRED)
	public void updateCardStatus(String cardInfoUploadXml, String xmlCheckSum)
			throws PersoServiceException {
		throw new PersoServiceException("Function not working.");

	}

	public void getPackageJobByPackageID(String packageId)
			throws PersoServiceException {
		throw new PersoServiceException("Function not working.");

	}

	public void getPackageJobByTransactionID(String transactionId)
			throws PersoServiceException {
		throw new PersoServiceException("Function not working.");

	}

	public ResponseDTO cancelJobByTransactionId(String transactionId,
			String officerId, String workstationId)
			throws PersoServiceException {
		throw new PersoServiceException("Function not working.");
	}

	@Override
	public void submitPersoData(NicTransaction nicTransaction,
			String submittingArn) throws PersoServiceException {
		this.submitPersoData(nicTransaction);
	}

	@Override
	public void submitPersoDataNew(NicTransaction nicTransaction,
			String submittingArn, ChipInfo chipinfo, Date dateOfIssue, Date dateOfExpiry)
			throws PersoServiceException {
		this.submitPersoDataNEW(nicTransaction, chipinfo, dateOfIssue, dateOfExpiry);
	}

	@Override
	public void updatePersoStatus() throws PersoServiceException {
		throw new PersoServiceException("Function not working.");
	}

	@Override
	public void updateDispatchInfo() throws PersoServiceException {
		throw new PersoServiceException("Function not working.");
	}

	@Override
	public List<SftpFile> listFiles(String directory)
			throws PersoServiceException {
		throw new PersoServiceException("Function not working.");
	}

	@Override
	public Map<String, Integer> getFolderStatistics()
			throws PersoServiceException {
		throw new PersoServiceException("Function not working.");
	}

}
