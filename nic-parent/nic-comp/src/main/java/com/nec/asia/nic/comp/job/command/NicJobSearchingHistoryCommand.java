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
import com.nec.asia.nic.comp.trans.domain.NicUploadJob;
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
import com.nec.asia.nic.comp.trans.service.NicUploadJobService;
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

public class NicJobSearchingHistoryCommand extends BaseCommand<NicUploadJob> implements Command<NicUploadJob>{

	//private static final Logger logger = Logger.getLogger(NicVerifySuccessCommand.class);
	private NicTransactionService nicTransactionService;
	private NicUploadJobService nicUploadJobService;
	NicCommandUtil nicCommandUtil = new NicCommandUtil();
	
	@Override
	public void doSomething(NicUploadJob job) {
		logger.info("inside NicAfisScreenAsyncCommand:{}", job.getTransactionId());
		
		String logData = null;
		String logInfo = null;
		Date startTime = new Date();
		String transactionStatus = null;
		
		Long jobId = job.getWorkflowJobId();
		this.setState(job.getJobType());//set job type as child key
		
	}

	public void setCommandUtil (NicCommandUtil commandUtil) {
		this.nicCommandUtil = commandUtil;
	}
	
	public void setNicTransactionService(NicTransactionService nicTransactionService) {
		this.nicTransactionService = nicTransactionService;
	}

	public void NicUploadJobService(NicUploadJobService nicUploadJobService) {
		this.nicUploadJobService = nicUploadJobService;
	}
	
}
