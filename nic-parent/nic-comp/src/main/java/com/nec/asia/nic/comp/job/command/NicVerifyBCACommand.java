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
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.json.JSONObject;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.nec.asia.nic.comp.queuesJobSchedule.service.LogJobScheduleService;
import com.nec.asia.nic.comp.queuesJobSchedule.service.QueuesJobScheduleService;
import com.nec.asia.nic.comp.job.utils.NicCommandUtil;
import com.nec.asia.nic.comp.trans.domain.LogJobSchedule;
import com.nec.asia.nic.comp.trans.domain.NicTransaction;
import com.nec.asia.nic.comp.trans.domain.NicTransactionLog;
import com.nec.asia.nic.comp.trans.domain.NicUploadJob;
import com.nec.asia.nic.comp.trans.domain.QueuesJobSchedule;
import com.nec.asia.nic.comp.trans.service.NicRegistrationDataService;
import com.nec.asia.nic.comp.trans.service.NicTransactionAttachmentService;
import com.nec.asia.nic.comp.trans.service.NicTransactionService;
import com.nec.asia.nic.comp.trans.service.NicUploadJobService;
import com.nec.asia.nic.comp.trans.service.TransactionLogService;
import com.nec.asia.nic.comp.trans.utils.APIConstantsBCA;
import com.nec.asia.nic.comp.trans.utils.RequestVerifyInfo;

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

public class NicVerifyBCACommand extends BaseCommand<QueuesJobSchedule> implements Command<QueuesJobSchedule>{

	//private static final Logger logger = Logger.getLogger(NicVerifySuccessCommand.class);
	private QueuesJobScheduleService queuesJobScheduleService;
	private NicTransactionService nicTransactionService;
	private LogJobScheduleService logJobScheduleService;
	private NicUploadJobService uploadJobService;
	
	NicCommandUtil nicCommandUtil = new NicCommandUtil();

	@Override
	public void doSomething(QueuesJobSchedule obj) {
		logger.info("inside NicVerifyBCACommand:{} - CODE", obj.getCode());
		//set job type as child key
		this.setState(obj.getTypeLogJob());
		
		String hrefsyncAPI = "/app/rest/v2/queries/epp$Investigate/findBlackList";
		String url = APIConstantsBCA.URL_API_BCA;
		String messError = "";
		Boolean sync = false;
		try {
			// Chỉ lấy bản ghi có TypeTransaction là AUTH và REG / REP
			if (obj.getTypeTransaction().equals(
					QueuesJobSchedule.TYPE_TRANSACTION_AUTHENTICATION)
					&& !obj.getTypeLogJob().equals("LOS")) {

				// Cập nhật trạng thái cho JOB
				obj.setStatus(QueuesJobSchedule.STATUS_JOB_PENDING);
				queuesJobScheduleService.createQueuesJobSchedule(obj);
				
				try{
					
					ObjectMapper mapper = new ObjectMapper();
					String jsonModel = "";
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
					NicTransaction nicTransaction = nicTransactionService.findById(obj.getCode());
					// /Xu ly ket qua tu BCA
					RequestVerifyInfo requestDocument = new RequestVerifyInfo();
					requestDocument.setIdNumber(nicTransaction.getNin());
					requestDocument.setName(nicTransaction.getNicRegistrationData()
							.getSurnameLine1());
					requestDocument.setDateOfBirth(new SimpleDateFormat("dd-MM-yyyy")
							.format(nicTransaction.getNicRegistrationData()
									.getDateOfBirth()));
					requestDocument.setPlaceOfBirth(nicTransaction
							.getNicRegistrationData().getPlaceOfBirth());
					requestDocument.setPassportNo(nicTransaction.getPrevPassportNo());

					// Đánh dấu dữ liệu trả về từ BCA - false: chưa có hoặc lấy lỗi /
					// true:
					// là đã có và lấy đc về

					jsonModel = mapper.writeValueAsString(requestDocument);
					String requestUrl = url + hrefsyncAPI;
					String resultSync = sendPostAuthentication(requestUrl, jsonModel,
							tokenBNG, "Bearer");

					if (!resultSync.equals("441") && !StringUtils.isEmpty(resultSync)) {
						
						
						
						List<NicUploadJob> lstUpload = uploadJobService
								.findAllByTransactionId(obj.getCode());
						NicUploadJob nicU = new NicUploadJob();
						nicU = lstUpload.get(0);
						nicU.setValidateInfoBca(2);
						uploadJobService.saveOrUpdate(nicU);
						
						uploadJobService.approveJobStatus_Confirmed(
								Long.valueOf(nicU.getWorkflowJobId()),
								"SYSTEM", "BAF-NIC-DEV",
								NicTransactionLog.TRANSACTION_STAGE_INVESTIGATION,
								NicTransactionLog.TRANSACTION_STATUS_NIC_PENING_BCA,
								NicUploadJob.RECORD_STATUS_CONFIRMED_BCA);
						sync = true;
					}
				} catch (Exception e) {
					messError = "ERROR: " + e.getMessage();
				}
			}
			else {
				logger.info(
						" skip NicVerifyBCACommand - CODE:{}",
						obj.getCode());
			}
		} catch (Exception e) {
			logger.error("\nflag EXCEPTION:" + e.getMessage() + "\n");
			e.printStackTrace();
			
			if (StringUtils.isEmpty(messError)) {
				messError = "ERROR: " + e.getMessage();
			}
			//this.setState(GOTO_ERROR_CMD);
			logger.info(" error NicVerifyBCACommand - CODE:{}", obj.getCode());
		} finally {
			// Chỉ lấy bản ghi có TypeTransaction là AUTH và REG / REP
			if (obj.getTypeTransaction().equals(
					QueuesJobSchedule.TYPE_TRANSACTION_AUTHENTICATION)
					&& !obj.getTypeLogJob().equals("LOS")) {
				// Lưu lại vào log Job
				LogJobSchedule log = new LogJobSchedule();
				log.setTypeTransaction(QueuesJobSchedule.TYPE_TRANSACTION_AUTHENTICATION);
				log.setTypeLogJob(obj.getTypeLogJob());
				if (!sync){
					log.setStatus(QueuesJobSchedule.STATUS_JOB_ERROR);
					// Cập nhật trạng thái cho JOB
					obj.setStatus(QueuesJobSchedule.STATUS_JOB_ERROR);
					queuesJobScheduleService.createQueuesJobSchedule(obj);
				}
				else {
					log.setStatus(QueuesJobSchedule.STATUS_JOB_SUCCESS);
				}
				log.setCode(obj.getCode());
				log.setHrefJob(url + hrefsyncAPI);
				log.setCreateDate(new Date());
				log.setModifyDate(new Date());
				log.setDescription(messError);
				logJobScheduleService.createLogJobSchedule(log);

				if (sync)
					// Xóa bản ghi cũ đã xử lý trong hàng đợi
					queuesJobScheduleService.delete(obj);
			}
		}
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
		/*
		 * connection.setRequestMethod("POST");
		 * connection.setRequestProperty("Authorization", "Bearer " +
		 * encoding.trim()); connection.setRequestProperty("Content-Type",
		 * "application/x-www-form-urlencoded"); connection.connect();
		 */

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
			/*
			 * for (int i = 0; i < arrayResp.length(); i++) { JSONObject jk =
			 * new JSONObject(); jk = arrayResp.getJSONObject(i);
			 * accessToken.put("access_token", jk.getString("token")); }
			 */

			/*
			 * Integer expires_in = myResponse.getInt("expires_in");
			 * accessToken.put("expires_in", expires_in.toString());
			 * accessToken.put("token_type",
			 * myResponse.getString("token_type")); accessToken.put("scope",
			 * myResponse.getString("scope"));
			 */
		}

		return accessToken;
	}
	
	public String sendPostAuthentication(String requestUrl, String data,
			String token, String typeToken) {
		String result = "";
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

				InputStream content = connection.getInputStream();
				Charset charset = Charset.forName("UTF8");
				InputStreamReader isr = new InputStreamReader(content, charset);

				int numCharsRead;
				char[] charArray = new char[1024];
				StringBuffer sb = new StringBuffer();
				while ((numCharsRead = isr.read(charArray)) > 0) {
					sb.append(charArray, 0, numCharsRead);
				}
				result = sb.toString();
				/*
				 * accessToken.put("access_token",
				 * arrayResp.getString("token"));
				 */
				/*
				 * BufferedReader br = new BufferedReader(new InputStreamReader(
				 * connection.getInputStream())); String line; while ((line =
				 * br.readLine()) != null) { jsonString.append(line); }
				 * br.close(); connection.disconnect();
				 */
			}
		} catch (Exception e) {
			return "441";
		}
		return result;
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
	
	public void setUploadJobService(NicUploadJobService uploadJobService) {
		this.uploadJobService = uploadJobService;
	}
}
