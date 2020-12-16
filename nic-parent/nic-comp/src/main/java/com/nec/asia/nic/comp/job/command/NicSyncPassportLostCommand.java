package com.nec.asia.nic.comp.job.command;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

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
import com.nec.asia.nic.comp.trans.domain.CountryData;
import com.nec.asia.nic.comp.trans.domain.LogJobSchedule;
import com.nec.asia.nic.comp.trans.domain.NicDocumentData;
import com.nec.asia.nic.comp.trans.domain.NicTransaction;
import com.nec.asia.nic.comp.trans.domain.OfficeData;
import com.nec.asia.nic.comp.trans.domain.PlaceData;
import com.nec.asia.nic.comp.trans.domain.QueuesJobSchedule;
import com.nec.asia.nic.comp.trans.domain.RequestCancel;
import com.nec.asia.nic.comp.trans.service.DocumentDataService;
import com.nec.asia.nic.comp.trans.service.NicTransactionService;
import com.nec.asia.nic.comp.trans.utils.APIConstantsBCA;

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

public class NicSyncPassportLostCommand extends BaseCommand<QueuesJobSchedule> implements Command<QueuesJobSchedule>{

	//private static final Logger logger = Logger.getLogger(NicVerifySuccessCommand.class);
	private QueuesJobScheduleService queuesJobScheduleService;
	private NicTransactionService nicTransactionService;
	private LogJobScheduleService logJobScheduleService;
	private DocumentDataService documentDataService;
	NicCommandUtil nicCommandUtil = new NicCommandUtil();
	
	@Override
	public void doSomething(QueuesJobSchedule obj) {
		logger.info("inside NicSyncPassportLostCommand:{} - CODE", obj.getCode());
		//set job type as child key
		this.setState(obj.getTypeLogJob());
		
		// Chỉ lấy bản ghi có TypeTransaction là LOS
		if(obj.getTypeLogJob().equals("LOS")){
			
			//Cập nhật trạng thái cho JOB
			obj.setStatus(QueuesJobSchedule.STATUS_JOB_PENDING);
			queuesJobScheduleService.createQueuesJobSchedule(obj);
			
			String hrefsyncAPI = "/app/rest/v2/entities/epp$CancelDocument";
			String url = APIConstantsBCA.URL_API_BCA;
			String messError = "";
			Boolean resultSync = false;
			String typeTokenBNG = "Bearer";
			String tokenBNG = "";
			try {
				NicTransaction nicTran = nicTransactionService.findById(obj.getCode());
				Collection<NicDocumentData> collectionNicDocumentData = documentDataService
						.findByTransactionId(obj.getCode()).getModel();
				NicDocumentData nicDocumentData = new NicDocumentData();
				for (NicDocumentData item : collectionNicDocumentData) {
					nicDocumentData = item;
				}
				
	
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
							/*
							 * expireTokenBNG = Integer.parseInt(resultToken
							 * .get("expires_in")); typeTokenBNG =
							 * resultToken.get("token_type");
							 */
						}
					}
					/*
					 * if (expireTokenBNG == 0 && tokenBNG != "") {
					 * ReAccessToken(tokenBNG); }
					 */

				} while (tokenBNG == "");

				// /TEST API ĐĂNG KÝ HỦY HỘ CHIẾU
				String urlCountry = "epp$Country";
				String urlPlace = "epp$Place";
				String urlArea = "epp$Area";
				String urlOffice = "epp$Office";
				// /Lấy dữ liệu danh mục quốc gia
				List<CountryData> listCountry = GetDataUtilAPICountry(
						urlCountry, tokenBNG, typeTokenBNG);
				// /Lấy dữ liệu danh mục quốc gia
				List<PlaceData> listPlace = GetDataUtilAPIPlace(urlPlace,
						tokenBNG, typeTokenBNG);
				// /Lấy dữ liệu danh mục khu vực
				List<AreaData> listArea = GetDataUtilAPIArea(urlArea,
						tokenBNG, typeTokenBNG);
				// /Lấy dữ liệu danh mục cơ quan
				List<OfficeData> listOffice = GetDataUtilAPIOffice(
						urlOffice, tokenBNG, typeTokenBNG);

				RequestCancel rqCancel_ = new RequestCancel();
				rqCancel_.setCode(obj.getCode());
				rqCancel_.setDocType("PASSPORT");
				rqCancel_.setDocCode(nicTran.getPrevPassportNo());
				rqCancel_.setReason("LOST");
				String oTemp_ = "BNG"; String oTempName_ = "Bộ ngoại giao";String oTempSite_ = "MB";// Mặc định là từ Bộ ngoại giao do csdlchưa đầy đủ
				OfficeData officeTemp_ = new OfficeData();
				if (listOffice != null) {
					for (OfficeData item : listOffice) {
						if (item.getCode().contains(oTemp_)) {
							oTempName_ = item.getName();
							oTempSite_ = item.getSite();
							break;
						}
					}
				}
				officeTemp_.setActive(false);
				officeTemp_.setName(oTempName_);
				officeTemp_.setCode(oTemp_);
				officeTemp_.setSite(oTempSite_);
				rqCancel_.setOffice(officeTemp_);
				String split = "";
				DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
				if(nicDocumentData != null){
					Date issueTmp = nicDocumentData.getDateOfIssue();
					if(issueTmp != null){
						split = issueTmp.toString()
								.split(" ")[0];
						rqCancel_.setDateOfRegister(split);
					}
				}
				if(StringUtils.isEmpty(split))
				{
					Date now = new Date();
					split = formatter.format(now);
					rqCancel_.setDateOfRegister(split);
				}
				
				String jsonModelCancel = ConvertDataJsonCancel(rqCancel_);
				String requestUrlCancel = url + hrefsyncAPI;
				String resultCancel = sendPostRequestCancel(
						requestUrlCancel, jsonModelCancel, tokenBNG,
						typeTokenBNG);
				if(resultCancel.equals("200"))
					resultSync = true;

				
				logger.info(" completed NicSyncPassportLostCommand - CODE:{}", obj.getCode());

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

	private String ConvertDataJsonCancel(RequestCancel data)
			throws JsonGenerationException, JsonMappingException, IOException {
		ObjectMapper mapper = new ObjectMapper();
		String json = mapper.writeValueAsString(data);
		return json;
	}
	
	// /Đăng ký giấy tờ hủy (Dữ liệu đơn)
		public static String sendPostRequestCancel(String requestUrl, String data,
				String token, String typeToken) {
			StringBuffer jsonString = new StringBuffer();
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
					BufferedReader br = new BufferedReader(new InputStreamReader(
							connection.getInputStream()));
					String line;
					while ((line = br.readLine()) != null) {
						jsonString.append(line);
					}
					br.close();
					connection.disconnect();
					return "200";
				}
			} catch (Exception e) {
				// throw new RuntimeException(e.getMessage());
				return "404";
			}
			return jsonString.toString();
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
		URL url = new URL("http://222.252.27.89:8044/app/rest/v2/oauth/token?"
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

		URL url = new URL("http://222.252.27.89:8044/app/rest/v2/entities/"
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

		URL url = new URL("http://222.252.27.89:8044/app/rest/v2/entities/"
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

		URL url = new URL("http://222.252.27.89:8044/app/rest/v2/entities/"
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

		URL url = new URL("http://222.252.27.89:8044/app/rest/v2/entities/"
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
