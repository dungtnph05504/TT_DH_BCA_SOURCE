package com.nec.asia.nic.comp.job.command;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang.StringUtils;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.nec.asia.nic.comp.queuesJobSchedule.service.LogJobScheduleService;
import com.nec.asia.nic.comp.queuesJobSchedule.service.QueuesJobScheduleService;
import com.nec.asia.nic.comp.job.utils.NicCommandUtil;
import com.nec.asia.nic.comp.trans.domain.AreaData;
import com.nec.asia.nic.comp.trans.domain.AttachmentDoc;
import com.nec.asia.nic.comp.trans.domain.CountryData;
import com.nec.asia.nic.comp.trans.domain.DataJson;
import com.nec.asia.nic.comp.trans.domain.DocAttachment;
import com.nec.asia.nic.comp.trans.domain.DocumentInfo;
import com.nec.asia.nic.comp.trans.domain.LogJobSchedule;
import com.nec.asia.nic.comp.trans.domain.NicDocumentData;
import com.nec.asia.nic.comp.trans.domain.NicRegistrationData;
import com.nec.asia.nic.comp.trans.domain.NicTransaction;
import com.nec.asia.nic.comp.trans.domain.NicTransactionAttachment;
import com.nec.asia.nic.comp.trans.domain.OfficeData;
import com.nec.asia.nic.comp.trans.domain.PassportInformation;
import com.nec.asia.nic.comp.trans.domain.PersonAttachment;
import com.nec.asia.nic.comp.trans.domain.PersonInformation;
import com.nec.asia.nic.comp.trans.domain.PlaceData;
import com.nec.asia.nic.comp.trans.domain.QueuesJobSchedule;
import com.nec.asia.nic.comp.trans.domain.RequestDocument;
import com.nec.asia.nic.comp.trans.service.DocumentDataService;
import com.nec.asia.nic.comp.trans.service.NicTransactionAttachmentService;
import com.nec.asia.nic.comp.trans.service.NicTransactionService;
import com.nec.asia.nic.comp.trans.utils.APIConstantsBCA;
import com.nec.asia.nic.utils.HelperClass;

/**
 * @author johnpaul_millan
 * @Company : NEC ASIA PACIFIC PTE LTD
 * @Since : Jun 06, 2013
 * <p></p>
 */

/* 
 * Modification History:
 * 21 Nov 2013 (jp) - add logic to change transaction status for rejected txns and CAR txns
 * 06 May 2014 (jp) - add validateTransactionStatusUpdate to avoid overwriting transaction status during reprocess
 */

public class NicSyncPassportCommand extends BaseCommand<QueuesJobSchedule> implements Command<QueuesJobSchedule>{

	//private static final Logger logger = Logger.getLogger(NicVerifySuccessCommand.class);
	private QueuesJobScheduleService queuesJobScheduleService;
	private NicTransactionService nicTransactionService;
	private NicTransactionAttachmentService transactionAttachmentService;
	private LogJobScheduleService logJobScheduleService;
	private DocumentDataService documentDataService;
	NicCommandUtil nicCommandUtil = new NicCommandUtil();
	
	@Override
	public void doSomething(QueuesJobSchedule obj) {
		logger.info("inside NicSyncPassportCommand:{} - CODE", obj.getCode());
		//set job type as child key
		this.setState(obj.getTypeLogJob());
		
		// Chỉ lấy bản ghi có TypeTransaction là SYNC và REG / REP
		if(obj.getTypeTransaction().equals(QueuesJobSchedule.TYPE_TRANSACTION_SYNC) 
				&& !obj.getTypeLogJob().equals("LOS")){
			
			//Cập nhật trạng thái cho JOB
			obj.setStatus(QueuesJobSchedule.STATUS_JOB_PENDING);
			queuesJobScheduleService.createQueuesJobSchedule(obj);
			
			String hrefsyncAPI = "/app/rest/v2/services/epp_DocumentService/syncWithoutProcessing";
			String url = APIConstantsBCA.URL_API_BCA;
			String messError = "";
			Boolean resultSync = false;
			try {
				DataJson jsonData = new DataJson();
				jsonData.setTransactionId(obj.getCode());
				String typeTokenBNG = "Bearer";
				String tokenBNG = "";
				do {
					if (tokenBNG == "" || tokenBNG == null) {
						Map<String, String> resultToken = new HashMap<String, String>();
						try {
							resultToken = GetTokenAPI("bng", "bng");
						} catch (JsonParseException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (JsonMappingException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						if (resultToken != null) {
							tokenBNG = resultToken.get("access_token");
						}
					}
				} while (tokenBNG == "");
				
				String urlCountry = "epp$Country";
				String urlPlace = "epp$Place";
				String urlArea = "epp$Area";
				String urlOffice = "epp$Office";

				// /Lấy dữ liệu danh mục quốc gia
				List<CountryData> listCountry = GetDataUtilAPICountry(urlCountry,
						tokenBNG, typeTokenBNG);
				// /Lấy dữ liệu danh mục quốc gia
				List<PlaceData> listPlace = GetDataUtilAPIPlace(urlPlace, tokenBNG,
						typeTokenBNG);
				// /Lấy dữ liệu danh mục khu vực
				List<AreaData> listArea = GetDataUtilAPIArea(urlArea, tokenBNG,
						typeTokenBNG);
				// /Lấy dữ liệu danh mục cơ quan
				List<OfficeData> listOffice = GetDataUtilAPIOffice(urlOffice,
						tokenBNG, typeTokenBNG);

				NicTransaction nicTransaciton = nicTransactionService
						.getNicTransactionById(jsonData.getTransactionId()).getModel();
				Collection<NicDocumentData> collectionNicDocumentData = documentDataService
						.findByTransactionId(jsonData.getTransactionId()).getModel();
				NicDocumentData nicDocumentData = new NicDocumentData();
				for (NicDocumentData item : collectionNicDocumentData) {
					nicDocumentData = item;
				}
				NicRegistrationData nicRegistrationData = nicTransaciton
						.getNicRegistrationData();
				String[] typeDocAttach = {
						NicTransactionAttachment.DOC_TYPE_PHOTO_CAPTURE,
						NicTransactionAttachment.DOC_TYPE_FINGERPRINT,
				// NicTransactionAttachment.DOC_TYPE_PERSO,
				// NicTransactionAttachment.DOC_TYPE_PHOTO_CHIP,
				// NicTransactionAttachment.DOC_TYPE_PHOTO_CLI,
				// NicTransactionAttachment.DOC_TYPE_PHOTO_GREY,
				// NicTransactionAttachment.DOC_TYPE_REF_COL_SLIP,
				// NicTransactionAttachment.DOC_TYPE_THUMBNAIL_CAPTURE,
				// NicTransactionAttachment.DOC_TYPE_THUMBNAIL_CHIP,
				// NicTransactionAttachment.DOC_TYPE_THUMBNAIL_GREY,
				// NicTransactionAttachment.DOC_TYPE_SIGN_FP
				};
				List<NicTransactionAttachment> nicTransacitonAttachment = transactionAttachmentService
						.getNicTransactionAttachments(jsonData.getTransactionId(),
								typeDocAttach, null);

				DocumentInfo modelDocument = new DocumentInfo();

				modelDocument.setCode(jsonData.getTransactionId());
				modelDocument.setRegistrationNo(jsonData.getTransactionId());
				String dateOfDelivery = nicTransaciton.getEstDateOfCollection()
						.toString().split(" ")[0];
				modelDocument.setDateOfDelivery(dateOfDelivery);
				String passportNo_ = nicDocumentData.getId().getPassportNo();
				// /Lay thong tin ho chieu ============
				PassportInformation ppTemp = new PassportInformation();

				String passportType = "";
				String ppTypeTmp = nicTransaciton.getPassportType().trim();
				if (ppTypeTmp.equals("P")) {
					passportType = "REGULAR";
				} else if (ppTypeTmp.equals("PO")) {
					passportType = "OFFICIAL";
				} else if (ppTypeTmp.equals("PD")) {
					passportType = "DIPLOMATIC";
				} else {
					passportType = "OTHER";
				}
				ppTemp.setPassportNo(passportNo_);
				ppTemp.setType(passportType);
				ppTemp.setIsPassport("Y");
				ppTemp.setFpEnCode(nicTransaciton.getNicRegistrationData()
						.getFpEncode());
				ppTemp.setChipSerialNo(nicDocumentData.getChipSerialNo());
				String doe_ = nicDocumentData.getDateOfExpiry().toString()
						.split(" ")[0];
				
				doe_ = doe_.split("-")[0] + "-" + doe_.split("-")[1] + "-"
						+ doe_.split("-")[2];
				ppTemp.setDateOfExpiry(doe_);
				String doi_ = nicDocumentData.getDateOfIssue().toString()
						.split(" ")[0];
				doi_ = doi_.split("-")[0] + "-" + doi_.split("-")[1] + "-"
						+ doi_.split("-")[2];
				ppTemp.setDateOfIssue(doi_);
				ppTemp.setSignerName(nicDocumentData.getCreateBy());
				ppTemp.setSignerPosition("Can bo xu ly");// Để tạm thời

				String oTemp_ = "BNG";
				String oTempName_ = "Bộ ngoại giao";
				String oTempSite_ = "MB";// Mặc định là từ Bộ ngoại giao do csdl
											// chưa đầy đủ
		/*		if (listOffice != null) {
					for (OfficeData item : listOffice) {
						if (item.getCode().contains(oTemp_)) {
							oTempName_ = item.getName();
							oTempSite_ = item.getSite();
							break;
						}
					}
				}*/
				PlaceData placeTmp = new PlaceData();
				placeTmp.setId("1");
				placeTmp.setCode("HN");
				placeTmp.setName("Hà Nội");
				AreaData areaTmp = new AreaData();
				areaTmp.setId("1");
				areaTmp.setCode("");
				areaTmp.setName("Q. Ba Đình");
				OfficeData officeTmp = new OfficeData();
				officeTmp.setActive(false);
				officeTmp.setName(oTempName_);
				officeTmp.setCode(oTemp_);
				officeTmp.setSite(oTempSite_);
				ppTemp.setPlaceOfIssue(officeTmp);
				ppTemp.setStatus("ACTIVATED");
				modelDocument.setPassport(ppTemp);

				// //===========================

				// /Lay thong tin cong dan ==============
				PersonInformation personTmp = new PersonInformation();
				String fullName_ = HelperClass.createFullName(
						nicRegistrationData.getSurnameFull(), nicRegistrationData.getMiddlenameFull(),
						nicRegistrationData.getFirstnameFull());

				personTmp.setName(fullName_);
				String gender_ = "UNKNOWN";
				String genderTmp_ = nicRegistrationData.getGender();
				if (genderTmp_.equals("M")) {
					gender_ = "MALE";
				} else if (genderTmp_.equals("F")) {
					gender_ = "FEMALE";
				}
				personTmp.setGender(gender_);
				String dob_ = nicRegistrationData.getDateOfBirth().toString()
						.split(" ")[0];
				dob_ = dob_.split("-")[0] + "-" + dob_.split("-")[1] + "-"
						+ dob_.split("-")[2];
				personTmp.setDateOfBirth(dob_);
				String idPlaceOfBirth_ = "202";// /Mặc định là Hà Nội do chưa đủ dữ
												// liệu
				String oTemppob_ = "BNG";
				String oTempNamepob_ = "Bộ ngoại giao";
				String oTempSitepob_ = "MB";// Mặc định là từ Bộ ngoại giao do csdl
											// chưa đầy đủ
				if (listPlace != null) {
					for (PlaceData item : listPlace) {
						if (item.getCode().contains(
								nicRegistrationData.getPlaceOfBirth())) {
							break;
						}
					}
				}
				
				CountryData coutryTmp = new CountryData();
				coutryTmp.setId("542");
				coutryTmp.setName("Vietnam");
				personTmp.setNationality(coutryTmp);
				personTmp.setResidentAddress(nicTransaciton
						.getNicRegistrationData().getAddressLine1());
				PlaceData placeTmp_ = new PlaceData();
				placeTmp_.setActive(false);
				placeTmp_.setName(oTempName_);
				placeTmp_.setCode(oTemp_);
				ppTemp.setPlaceOfIssue(officeTmp);
				personTmp.setPlaceOfBirth(placeTmp);
				personTmp.setIdNumber(nicTransaciton.getNin());
				personTmp.setResidentArea(areaTmp);
				personTmp.setResidentPlace(placeTmp);
				personTmp.setPlaceOfIdIssue(officeTmp);
				modelDocument.setPerson(personTmp);
				// //====================================

				// /Thong tin tai lieu di kem (anh mat va van tay)========
				DocAttachment docAtt = new DocAttachment();
				PersonAttachment perAtt = new PersonAttachment();
				List<AttachmentDoc> listAttachment = new LinkedList<AttachmentDoc>();
				if (nicTransacitonAttachment != null) {
					for (NicTransactionAttachment item : nicTransacitonAttachment) {
						AttachmentDoc attachmentDoc = new AttachmentDoc();
						String typeDoc_ = "OTHER"; // / Tạm thời các tài liệu gửi
													// kèm để chung là OTHER
						Integer serialNo = 0;
						if (item.getDocType().contains("FP")) {
							typeDoc_ = "FP_WSQ";//FINGERPRINT
						} else if (item.getDocType().contains("SCANDOCUMENT")) {
							typeDoc_ = "SCAN_DOCUMENT";
						} else if (item.getDocType().contains("SCANDOCUMENT")) {
							typeDoc_ = "PERSO";
						} else if (item.getDocType().contains("PH_CAP")
								|| item.getDocType().contains("PH_CHIP")
								|| item.getDocType().contains("PH_CLI")
								|| item.getDocType().contains("PH_GREY")
								|| item.getDocType().contains("TH_CAP")
								|| item.getDocType().contains("TH_CHIP")
								|| item.getDocType().contains("TH_GREY")) {
							typeDoc_ = "PH";//PHOTO
						}

						/*attachmentDoc.setFileName(item.getDocType() + "_"
								+ item.getSerialNo());*/
						byte[] encodeByte = Base64.encodeBase64(item.getDocData());
						String encoded = new String(encodeByte, "US-ASCII");
						attachmentDoc.setBase64(encoded);
						serialNo = Integer.parseInt(item.getSerialNo());
						attachmentDoc.setSerialNo(serialNo);
						attachmentDoc.setType(typeDoc_);
						listAttachment.add(attachmentDoc);
						
					}
				}
				docAtt.setDocattachments(listAttachment);
				perAtt.setPerattachments(listAttachment);
				modelDocument.setPerattachment(perAtt);
				modelDocument.setDocattachment(docAtt);
				// //===================================

				if (nicRegistrationData.getContactNo() != ""
						&& nicRegistrationData.getContactNo() != null) {
					modelDocument.setPhoneNo(nicRegistrationData.getContactNo());
				} else {
					modelDocument.setPhoneNo("0");
				}

				modelDocument.setIsEpassport("Y");// Mặc định đồng bộ dữ liệu mới từ NIC
				String priority_ = "NORMAL";
				if (nicTransaciton.getPriority() != null) {
					if (nicTransaciton.getPriority() == 1) {
						priority_ = "HIGH";
					} else if (nicTransaciton.getPriority() == 2) {
						priority_ = "HIGHEST";
					}
				}

				modelDocument.setPriority(priority_);
				String typeTransaction_ = "NEW";
				if (nicTransaciton.getTransactionType().contains("REP")) {
					typeTransaction_ = "RENEW_BY_LOST";
				} else if (nicTransaciton.getTransactionType() != "REP"
						&& nicTransaciton.getTransactionType() != "REG") {
					typeTransaction_ = "OTHER";
				}
				modelDocument.setType(typeTransaction_);

				OfficeData officeTmp_ = new OfficeData();
				officeTmp_.setCode("BNG");
				officeTmp_.setName("Bộ ngoại giao");
				modelDocument.setOffice(officeTmp_);
				modelDocument.setStatus(DocumentInfo.ISSUING);
				// /TEST API ĐỒNG BỘ DỮ LIỆU HC
				RequestDocument requestDocument = new RequestDocument();
				requestDocument.setDocument(modelDocument);
				String jsonModel = ConvertDataJson(modelDocument);
				String requestUrl = url + hrefsyncAPI;
				resultSync = sendPostRequestSync(requestUrl, jsonModel,
						tokenBNG, typeTokenBNG);
				
				logger.info(" completed NicSyncPassportCommand - CODE:{}", obj.getCode());

			} catch (Exception e) {
				logger.error("\nflag EXCEPTION:"+e.getMessage() +"\n");
				e.printStackTrace();
				
				if(StringUtils.isEmpty(messError)){
					messError = "ERROR: " + e.getMessage();
				}
				
				// Cập nhật trạng thái cho JOB
				obj.setStatus(QueuesJobSchedule.STATUS_JOB_ERROR);
				queuesJobScheduleService.createQueuesJobSchedule(obj);
				
				//this.setState(GOTO_ERROR_CMD);
				logger.info(" error NicSyncPassportCommand - CODE:{}", obj.getCode());
			}
			finally {
				//Lưu lại vào log Job
				LogJobSchedule log = new LogJobSchedule();
				log.setTypeTransaction(QueuesJobSchedule.TYPE_TRANSACTION_SYNC);
				log.setTypeLogJob(obj.getTypeLogJob());
				if(!resultSync){
					log.setStatus(QueuesJobSchedule.STATUS_JOB_ERROR);
					// Cập nhật trạng thái cho JOB
					obj.setStatus(QueuesJobSchedule.STATUS_JOB_ERROR);
					queuesJobScheduleService.createQueuesJobSchedule(obj);
				}
				else
				{
					log.setStatus(QueuesJobSchedule.STATUS_JOB_SUCCESS);
				}
				log.setCode(obj.getCode());
				log.setHrefJob(url + hrefsyncAPI);
				log.setCreateDate(new Date());
				log.setModifyDate(new Date());
				log.setDescription(messError);
				logJobScheduleService.createLogJobSchedule(log);
				
				if(resultSync)
					//Xóa bản ghi cũ đã xử lý trong hàng đợi
					queuesJobScheduleService.delete(obj);
			}
		}
	}

	private String ConvertDataJson(DocumentInfo data)
			throws JsonGenerationException, JsonMappingException, IOException {
		ObjectMapper mapper = new ObjectMapper();
		String json = mapper.writeValueAsString(data);
		return json;
	}
	
	// /Đồng bộ dữ liệu hộ chiếu (Dữ liệu đơn)
	public Boolean sendPostRequestSync(String requestUrl, String data,
			String token, String typeToken) {
		try {
			URL url = new URL(requestUrl);
			HttpURLConnection connection = (HttpURLConnection) url
					.openConnection();

			connection.setDoInput(true);
			connection.setDoOutput(true);
			connection.setRequestMethod("POST");
			connection.setRequestProperty("Authorization", typeToken.trim()
					+ " " + token.trim());
			connection.setRequestProperty("Content-Type",
					"application/json; charset=UTF-8");
			OutputStreamWriter writer = new OutputStreamWriter(
					connection.getOutputStream(), "UTF-8");
			writer.write(data);
			writer.close();

			int statusCode = connection.getResponseCode();
			if (statusCode == 200 || statusCode == 201) {

				InputStream content = connection.getInputStream();
				InputStreamReader isr = new InputStreamReader(content);

				int numCharsRead;
				char[] charArray = new char[1024];
				StringBuffer sb = new StringBuffer();
				while ((numCharsRead = isr.read(charArray)) > 0) {
					sb.append(charArray, 0, numCharsRead);
				}
				return true;
				/*
				 * BufferedReader br = new BufferedReader(new InputStreamReader(
				 * connection.getInputStream())); String line; while ((line =
				 * br.readLine()) != null) { jsonString.append(line); }
				 * br.close(); connection.disconnect();
				 */
			}
		} catch (Exception e) {

		}
		return false;
	}
	
	@ExceptionHandler
	public Map<String, String> GetTokenAPI(String username, String password)
			throws JsonParseException, JsonMappingException, IOException {
		Map<String, String> accessToken = new HashMap<String, String>();

		String urlParameters = "username=" + username + "&password=" + password;
		/*
		 * String encoding = Base64.encodeBase64String(("epp:epp12#")
		 * .getBytes("UTF-8"));
		 */

		byte[] postData = urlParameters.getBytes(StandardCharsets.UTF_8);
		int postDataLength = postData.length;
		URL url = new URL("http://192.168.1.15:8044/app/rest/v2/oauth/token?"
				+ urlParameters);
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
			JSONObject arrayResp = myResponse.getJSONObject("data");
			accessToken.put("access_token", arrayResp.getString("token"));
		}

		return accessToken;
	}
	
	public List<CountryData> GetDataUtilAPICountry(String urlParameters,
			String token, String typeToken) throws JsonParseException,
			JsonMappingException, IOException {
		List<CountryData> listResult = new LinkedList<CountryData>();

		URL url = new URL("http://192.168.1.15:8044/app/rest/v2/entities/"
				+ urlParameters);
		HttpURLConnection connection = (HttpURLConnection) url.openConnection();
		connection.setRequestMethod("GET");
		connection.setRequestProperty("Authorization", typeToken.trim() + " "
				+ token.trim());
		connection.setRequestProperty("Content-Type", "application/json");
		connection.connect();

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
			String st = sb.toString();
			JSONArray arrayResp = new JSONArray(st);

			for (int i = 0; i < arrayResp.length(); i++) {

				JSONObject myResponse = new JSONObject();
				myResponse = arrayResp.getJSONObject(i);

				CountryData map = new CountryData();

				map.setName(getMessageFromServerString(myResponse, "name"));
				Integer numCode = getMessageFromServerInt(myResponse, "numCode");
				map.setNumCode(numCode.toString());
				map.setAlpha2Code(getMessageFromServerString(myResponse,
						"alpha2Code"));
				map.setAlpha3Code(getMessageFromServerString(myResponse,
						"alpha3Code"));
				map.setNationality(getMessageFromServerString(myResponse,
						"nationality"));

				if (map != null) {
					listResult.add(map);
				}
			}
		} else {
			// /Trả về log mã lỗi và thông tin lỗi

		}
		return listResult;
	}

	public List<PlaceData> GetDataUtilAPIPlace(String urlParameters,
			String token, String typeToken) throws JsonParseException,
			JsonMappingException, IOException {
		List<PlaceData> listResult = new LinkedList<PlaceData>();

		URL url = new URL("http://192.168.1.15:8044/app/rest/v2/entities/"
				+ urlParameters);
		HttpURLConnection connection = (HttpURLConnection) url.openConnection();
		connection.setRequestMethod("GET");
		connection.setRequestProperty("Authorization", typeToken.trim() + " "
				+ token.trim());
		connection.setRequestProperty("Content-Type", "application/json");
		connection.connect();

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
			String st = sb.toString();
			JSONArray arrayResp = new JSONArray(st);

			for (int i = 0; i < arrayResp.length(); i++) {

				JSONObject myResponse = new JSONObject();
				myResponse = arrayResp.getJSONObject(i);

				PlaceData map = new PlaceData();

				map.setName(getMessageFromServerString(myResponse, "name"));
				map.setCode(getMessageFromServerString(myResponse, "code"));
				map.setActive(getMessageFromServerBoolean(myResponse, "active"));

				if (map != null) {
					listResult.add(map);
				}
			}
		} else {
			// /Trả về log mã lỗi và thông tin lỗi

		}
		return listResult;
	}

	public List<AreaData> GetDataUtilAPIArea(String urlParameters,
			String token, String typeToken) throws JsonParseException,
			JsonMappingException, IOException {
		List<AreaData> listResult = new LinkedList<AreaData>();

		URL url = new URL("http://192.168.1.15:8044/app/rest/v2/entities/"
				+ urlParameters);
		HttpURLConnection connection = (HttpURLConnection) url.openConnection();
		connection.setRequestMethod("GET");
		connection.setRequestProperty("Authorization", typeToken.trim() + " "
				+ token.trim());
		connection.setRequestProperty("Content-Type", "application/json");
		connection.connect();

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
			String st = sb.toString();
			JSONArray arrayResp = new JSONArray(st);

			for (int i = 0; i < arrayResp.length(); i++) {

				JSONObject myResponse = new JSONObject();
				myResponse = arrayResp.getJSONObject(i);

				AreaData map = new AreaData();

				map.setName(getMessageFromServerString(myResponse, "name"));
				map.setActive(getMessageFromServerBoolean(myResponse, "active"));

				if (map != null) {
					listResult.add(map);
				}
			}
		} else {
			// /Trả về log mã lỗi và thông tin lỗi

		}
		return listResult;
	}

	public List<OfficeData> GetDataUtilAPIOffice(String urlParameters,
			String token, String typeToken) throws JsonParseException,
			JsonMappingException, IOException {
		List<OfficeData> listResult = new LinkedList<OfficeData>();

		URL url = new URL("http://192.168.1.15:8044/app/rest/v2/entities/"
				+ urlParameters);
		HttpURLConnection connection = (HttpURLConnection) url.openConnection();
		connection.setRequestMethod("GET");
		connection.setRequestProperty("Authorization", typeToken.trim() + " "
				+ token.trim());
		connection.setRequestProperty("Content-Type", "application/json");
		connection.connect();

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
			String st = sb.toString();
			JSONArray arrayResp = new JSONArray(st);

			for (int i = 0; i < arrayResp.length(); i++) {

				JSONObject myResponse = new JSONObject();
				myResponse = arrayResp.getJSONObject(i);

				OfficeData map = new OfficeData();

				map.setName(getMessageFromServerString(myResponse, "name"));
				map.setCode(getMessageFromServerString(myResponse, "code"));
				map.setActive(getMessageFromServerBoolean(myResponse, "active"));
				map.setAddress(getMessageFromServerString(myResponse, "address"));

				if (map != null) {
					listResult.add(map);
				}
			}
		} else {
			// /Trả về log mã lỗi và thông tin lỗi

		}
		return listResult;
	}
	
	public String getMessageFromServerString(JSONObject response, String key) {
		return ((response.has(key) && !response.isNull(key))) ? response
				.getString(key) : null;
	}

	public Integer getMessageFromServerInt(JSONObject response, String key) {
		return ((response.has(key) && !response.isNull(key))) ? response
				.getInt(key) : null;
	}

	public Long getMessageFromServerLong(JSONObject response, String key) {
		return ((response.has(key) && !response.isNull(key))) ? response
				.getLong(key) : null;
	}

	public Boolean getMessageFromServerBoolean(JSONObject response, String key) {
		return ((response.has(key) && !response.isNull(key))) ? response
				.getBoolean(key) : null;
	}
	
	public void setCommandUtil (NicCommandUtil commandUtil) {
		this.nicCommandUtil = commandUtil;
	}
	
	public void setNicTransactionService(NicTransactionService nicTransactionService) {
		this.nicTransactionService = nicTransactionService;
	}

	public void setTransactionAttachmentService(NicTransactionAttachmentService transactionAttachmentService) {
		this.transactionAttachmentService = transactionAttachmentService;
	}
	
	
	public void setQueuesJobScheduleService(QueuesJobScheduleService queuesJobScheduleService) {
		this.queuesJobScheduleService = queuesJobScheduleService;
	}
	
	public void setLogJobScheduleService(LogJobScheduleService logJobScheduleService) {
		this.logJobScheduleService = logJobScheduleService;
	}
	
	public void setDocumentDataService(DocumentDataService documentDataService) {
		this.documentDataService = documentDataService;
	}
}
