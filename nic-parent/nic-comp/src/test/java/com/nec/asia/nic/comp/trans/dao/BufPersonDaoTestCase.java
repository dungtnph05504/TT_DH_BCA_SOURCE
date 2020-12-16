package com.nec.asia.nic.comp.trans.dao;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.StringWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.sql.Clob;
import java.text.DateFormat;
import java.text.Format;
import java.text.Normalizer;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;
import java.util.regex.Pattern;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.ObjectWriter;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.XML;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.nec.asia.nic.comp.bufPersonInvestigation.dao.BufPersonInvestigationDao;
import com.nec.asia.nic.comp.bufPersonInvestigation.service.BufPersonInvestigationService;
import com.nec.asia.nic.comp.immihistory.domain.ImmiHistory;
import com.nec.asia.nic.comp.immihistory.domain.ImmiHistorys;
import com.nec.asia.nic.comp.job.dto.HandoverSync;
import com.nec.asia.nic.comp.job.dto.InfoFamillyDto;
import com.nec.asia.nic.comp.job.dto.InvestigationListInfoTargetDto;
import com.nec.asia.nic.comp.job.dto.InvestigationListInfoTargetsDto;
import com.nec.asia.nic.comp.job.dto.RequestLDSPack;
import com.nec.asia.nic.comp.job.dto.TransactionSync;
import com.nec.asia.nic.comp.listHandover.domain.NicListHandover;
import com.nec.asia.nic.comp.listHandover.service.ListHandoverService;
import com.nec.asia.nic.comp.trans.domain.BufEppPersonInvestigation;
import com.nec.asia.nic.comp.trans.domain.DetailRecieptFee;
import com.nec.asia.nic.comp.trans.domain.EppPerson;
import com.nec.asia.nic.comp.trans.domain.EppPersonAttchmnt;
import com.nec.asia.nic.comp.trans.domain.EppPersonFamily;
import com.nec.asia.nic.comp.trans.domain.EppRecieptManager;
import com.nec.asia.nic.comp.trans.domain.FeeRecieptPayment;
import com.nec.asia.nic.comp.trans.domain.NicDocumentData;
import com.nec.asia.nic.comp.trans.domain.NicRegistrationData;
import com.nec.asia.nic.comp.trans.domain.NicSearchHitResult;
import com.nec.asia.nic.comp.trans.domain.NicSearchResult;
import com.nec.asia.nic.comp.trans.domain.NicTransaction;
import com.nec.asia.nic.comp.trans.domain.NicTransactionAttachment;
import com.nec.asia.nic.comp.trans.domain.NicTransactionLog;
import com.nec.asia.nic.comp.trans.domain.NicTransactionPackage;
import com.nec.asia.nic.comp.trans.domain.NicTransactionPayment;
import com.nec.asia.nic.comp.trans.domain.NicTransactionPaymentDetail;
import com.nec.asia.nic.comp.trans.domain.NicUploadJob;
import com.nec.asia.nic.comp.trans.domain.RptStatisticsTransmitData;
import com.nec.asia.nic.comp.trans.dto.CountPassport;
import com.nec.asia.nic.comp.trans.dto.EppBufFamilyDto;
import com.nec.asia.nic.comp.trans.dto.EppBufInfoDocDto;
import com.nec.asia.nic.comp.trans.dto.EppPersonDto;
import com.nec.asia.nic.comp.trans.dto.NicHitTransaction;
import com.nec.asia.nic.comp.trans.dto.NicListHandoverDto;
import com.nec.asia.nic.comp.trans.dto.NicPaymentJsonDto;
import com.nec.asia.nic.comp.trans.dto.NicTransactionJsonDto;
import com.nec.asia.nic.comp.trans.dto.NicUploadJobDto;
import com.nec.asia.nic.comp.trans.dto.NicUploadJobJsonDto;
import com.nec.asia.nic.comp.trans.dto.PassportInfo;
import com.nec.asia.nic.comp.trans.service.DetailRecieptFeeService;
import com.nec.asia.nic.comp.trans.service.DetailRecieptService;
import com.nec.asia.nic.comp.trans.service.DocumentDataService;
import com.nec.asia.nic.comp.trans.service.EppInventoryItemsDetailService;
import com.nec.asia.nic.comp.trans.service.EppPersonService;
import com.nec.asia.nic.comp.trans.service.FeeRecieptPaymentService;
import com.nec.asia.nic.comp.trans.service.NicImmiHistoryService;
import com.nec.asia.nic.comp.trans.service.NicRegistrationDataService;
import com.nec.asia.nic.comp.trans.service.NicSearchHitResultService;
import com.nec.asia.nic.comp.trans.service.NicSearchResultService;
import com.nec.asia.nic.comp.trans.service.NicTransactionAttachmentService;
import com.nec.asia.nic.comp.trans.service.NicTransactionPackageService;
import com.nec.asia.nic.comp.trans.service.NicTransactionService;
import com.nec.asia.nic.comp.trans.service.NicUploadJobService;
import com.nec.asia.nic.comp.trans.service.RecieptManagerService;
import com.nec.asia.nic.comp.trans.service.RptStatisticsTransmitDataService;
import com.nec.asia.nic.dx.admin.GetRecieptAllRequest;
import com.nec.asia.nic.dx.trans.BufEppListResponse;
import com.nec.asia.nic.framework.admin.rbac.domain.Functions;
import com.nec.asia.nic.framework.admin.rbac.domain.Roles;
import com.nec.asia.nic.framework.admin.rbac.domain.Users;
import com.nec.asia.nic.framework.admin.rbac.domain.Workstations;
import com.nec.asia.nic.framework.admin.rbac.service.FunctionService;
import com.nec.asia.nic.framework.admin.rbac.service.RoleService;
import com.nec.asia.nic.framework.admin.rbac.service.UserService;
import com.nec.asia.nic.framework.admin.rbac.service.WorkStationService;
import com.nec.asia.nic.framework.admin.site.domain.SiteRepository;
import com.nec.asia.nic.framework.admin.site.service.SiteService;
import com.nec.asia.nic.utils.DateUtil;
import com.nec.asia.nic.utils.HelperClass;
import com.sun.istack.logging.Logger;

import junit.framework.TestCase;


public class BufPersonDaoTestCase extends TestCase {
	
	public static ApplicationContext context = null;
	public static BufPersonInvestigationService bufPersonInvestigationService = null;
	public static NicImmiHistoryService nicImmiHistoryService = null;
	public static NicUploadJobService nicUploadJobService = null;
	public static NicRegistrationDataService nicRegistrationDataService = null;
	public static DocumentDataService documentDataService = null;
	public static EppPersonService eppPersonService = null;
	public static NicTransactionAttachmentService nicTransactionAttachmentService = null;
	public static NicSearchResultDao nicSearchResultDao = null;
	public static NicSearchHitResultService nicSearchHitResultService = null;
	public static UserService userService = null;
	public static RoleService roleService = null;
	public static FunctionService functionService = null; 
	public static WorkStationService workStationService = null;
	public static NicTransactionService nicTransactionService = null;
	/*public static SiteService siteService = null;
	public static NicTransactionPackageService nicTransactionPackageService = null;
	public static UserService userService = null;
	public static NicSearchHitResultService nicSearchHitResultService = null;
	public static NicSearchResultService nicSearchResultService = null;
	public static BufPersonInvestigationDao bufPersonInvestigationDao = null;
	public static RptStatisticsTransmitDataService rptStatisticsTransmitDataService = null;
	public static EppInventoryItemsDetailService eppInventoryItemsDetailService = null;
	public static ListHandoverService listHandoverService = null;
	public static NicTransactionPaymentDao nicTransactionPaymentDao = null;
	public static NicTransactionPaymentDetailDao nicTransactionPaymentDetailDao = null;
	public static NicTransactionLogDao nicTransactionLogDao = null;*/
	public static NicTransactionAttachmentDao transactionDocumentDao = null;
	public static DetailRecieptFeeService detailRecieptFeeService = null;
	public static DetailRecieptService detailRecieptService = null;
	public static FeeRecieptPaymentService feeRecieptPaymentService = null;
	public static RecieptManagerService recieptManagerService = null;
	
	public BufPersonDaoTestCase() {
		init();
	}
	private void checkParameters() {
		String logDir = System.getProperty("jboss.server.log.dir");
		if (logDir==null || logDir.length()==0) {
			log(" jboss.server.log.dir is empty, set it as logs\\");
			System.setProperty("jboss.server.log.dir", "logs\\");
		} else {
			log(" jboss.server.log.dir is "+logDir);
		}
	}
	
	public void init() {
		checkParameters();
		try {
			context = new ClassPathXmlApplicationContext("spring-context.xml");
			bufPersonInvestigationService = context.getBean("bufPersonInvestigationService", BufPersonInvestigationService.class);
			nicImmiHistoryService = context.getBean("nicImmiHistoryService", NicImmiHistoryService.class);
			nicUploadJobService = context.getBean("uploadJobService", NicUploadJobService.class);
			nicRegistrationDataService = context.getBean("nicRegistrationDataService", NicRegistrationDataService.class);
			documentDataService = context.getBean("documentDataService", DocumentDataService.class);
			eppPersonService = context.getBean("eppPersonService", EppPersonService.class);
			nicTransactionAttachmentService = context.getBean("nicTransactionDocumentService", NicTransactionAttachmentService.class);
			nicSearchResultDao = context.getBean("searchResultDao", NicSearchResultDao.class);
			nicSearchHitResultService = context.getBean("nicSearchHitResultService", NicSearchHitResultService.class);
			userService = context.getBean("userService", UserService.class);
			roleService = context.getBean("roleService", RoleService.class);
			functionService = context.getBean("functionService", FunctionService.class);
			workStationService = context.getBean("workstationsService", WorkStationService.class);
			
			detailRecieptFeeService = context.getBean("detailRecieptFeeService", DetailRecieptFeeService.class);
			detailRecieptService = context.getBean("detailRecieptService", DetailRecieptService.class);
			feeRecieptPaymentService = context.getBean("feeRecieptPaymentService", FeeRecieptPaymentService.class);
			recieptManagerService = context.getBean("recieptManagerService", RecieptManagerService.class);
			nicTransactionService = context.getBean("nicTransactionService", NicTransactionService.class);
			transactionDocumentDao = context.getBean("transactionDocumentDao", NicTransactionAttachmentDao.class);
			/*siteService = context.getBean("siteService", SiteService.class);
			nicTransactionPackageService = context.getBean("nicTransactionPackageService", NicTransactionPackageService.class);
			userService = context.getBean("userService", UserService.class);
			nicSearchHitResultService = context.getBean("nicSearchHitResultService", NicSearchHitResultService.class);
			nicSearchResultService = context.getBean("nicSearchResultService", NicSearchResultService.class);
			bufPersonInvestigationDao = context.getBean("bufPersonInvestigationDao", BufPersonInvestigationDao.class);
			rptStatisticsTransmitDataService = context.getBean("rptStatisticsTransmitDataSerivce", RptStatisticsTransmitDataService.class);
			eppInventoryItemsDetailService = context.getBean("eppInventoryItemsDetailService", EppInventoryItemsDetailService.class);
			
			nicTransactionPaymentDao = context.getBean("transactionPaymentDao", NicTransactionPaymentDao.class);
			nicTransactionPaymentDetailDao = context.getBean("nicTransactionPaymentDetailDao", NicTransactionPaymentDetailDao.class);
			nicTransactionLogDao = context.getBean("transactionLogDao", NicTransactionLogDao.class);
			listHandoverService = context.getBean("listHandoverService", ListHandoverService.class);*/
		} catch (Error e) {
			e.printStackTrace();
		}
	}
	
	public static final String DOC_TYPE_PHOTO_CAPTURE = "PH_CAP";
	public static final String DOC_TYPE_PHOTO_CHIP = "PH_CHIP";
	public static final int   THUMBNAIL_FACE_WIDTH_DEFAULT   = 240; //120
	public static final int   THUMBNAIL_FACE_HEIGHT_DEFAULT  = 320; //160
	public static final float THUMBNAIL_FACE_QUALITY_DEFAULT = 0.5f;
	public static final String DEFAULT_SERIAL_NO = "01";
	public static final String DOC_TYPE_THUMBNAIL_CAPTURE = "TH_CAP";
	public static final String DOC_TYPE_FINGERPRINT = "FP";
	public static final String DOC_TYPE_FINGERPRINT_FP_MNU = "FP_MNU";
	public static final String DOC_TYPE_FINGERPRINT_WSQ = "FP_WSQ";
	public static final String DOC_TYPE_MINUTIAETEMPLATE = "MNU";
	public static final String DOC_TYPE_TPL = "TPL";
	public static final String DOC_TYPE_SCAN_DOCUMENT = "SCAN_DOCUMENT";
	
	private NicListHandoverDto mapperHandoverDto(NicListHandover han){
		
		NicListHandoverDto result = new NicListHandoverDto();
		result.setArchiveCode(han.getArchiveCode());
		result.setCountTransaction(han.getCountTransaction());
		result.setCreateBy(han.getCreateBy());
		result.setCreateDate(han.getCreateDate());
		//result.setDescription(han.getDescription());
		result.setHandoverStage(han.getHandoverStage());
		//result.setIsSyncPerso(han.getIsSyncPerso());
		//result.setLeaderId(han.getLeaderId());
		//result.setNumberTran(han.getNumberTran());
		result.setPackageId(han.getPackageId());
		result.setPackageName(han.getPackageName());
		//result.setResultPlace(han.getResultPlace());
		result.setSiteCode(han.getSiteCode());
		//result.setStatus(han.getStatus());
		//result.setTransactionId(han.getTransactionId());
		//result.setTypeList(han.getTypeList());
		//result.setTypeListName(han.getTypeListName());
		result.setUpdateBy(han.getUpdateBy());
		result.setUpdateDate(han.getUpdateDate());
		//result.setUserLeaderProcess(han.getUserLeaderProcess());
		//result.setUsersProcess(han.getUsersProcess());
		return result; 
	}
	
	private NicListHandover mapperHandover(NicListHandoverDto han){
		
		NicListHandover result = new NicListHandover();
		result.setArchiveCode(han.getArchiveCode());
		result.setCountTransaction(han.getCountTransaction());
		result.setCreateBy(han.getCreateBy());
		result.setCreateDate(han.getCreateDate());
		//result.setDescription(han.getDescription());
		result.setHandoverStage(han.getHandoverStage());
		//result.setIsSyncPerso(han.getIsSyncPerso());
		//result.setLeaderId(han.getLeaderId());
		//result.setNumberTran(han.getNumberTran());
		result.setPackageId(han.getPackageId());
		result.setPackageName(han.getPackageName());
		//result.setSiteCode(han.getSiteCode());
		//result.setStatus(han.getStatus());
		//result.setTransactionId(han.getTransactionId());
		//result.setTypeList(han.getTypeList());
		//result.setTypeListName(han.getTypeListName());
		result.setUpdateBy(han.getUpdateBy());
		result.setUpdateDate(han.getUpdateDate());
		//result.setUserLeaderProcess(han.getUserLeaderProcess());
		//result.setUsersProcess(han.getUsersProcess());
		return result; 
	}
	
private NicPaymentJsonDto mapperPayment(NicTransactionPayment payment){
		
		NicPaymentJsonDto paymentJ = new NicPaymentJsonDto();
		paymentJ.setBalance(payment.getBalance());
		paymentJ.setCashReceived(payment.getCashReceived());
		paymentJ.setCollectionOfficerId(payment.getCollectionOfficerId());
		paymentJ.setCreateBy(payment.getCreateBy());
		paymentJ.setCreateDatetime(payment.getCreateDatetime());
		paymentJ.setCreateWkstnId(payment.getCreateWkstnId());
		paymentJ.setFeeAmount(payment.getFeeAmount());
		paymentJ.setNoOfTimeLost(payment.getNoOfTimeLost());
		paymentJ.setPaymentAmount(payment.getPaymentAmount());
		paymentJ.setPaymentDatetime(payment.getPaymentDatetime());
		paymentJ.setPaymentId(payment.getPaymentId());
		paymentJ.setPaymentStatus(payment.getPaymentStatus());
		paymentJ.setReceiptId(payment.getReceiptId());
		paymentJ.setReduceRateAmount(payment.getReduceRateAmount());
		paymentJ.setReduceRateFlag(payment.getReduceRateFlag());
		paymentJ.setTransactionId(payment.getTransactionId());
		paymentJ.setUpdateBy(payment.getUpdateBy());
		paymentJ.setUpdateDatetime(payment.getUpdateDatetime());
		paymentJ.setUpdateWkstnId(payment.getUpdateWkstnId());
		paymentJ.setWaiverFlag(payment.getWaiverFlag());
		paymentJ.setWaiverOfficerId(payment.getWaiverOfficerId());
		paymentJ.setWaiverReason(payment.getWaiverReason());
		
		return paymentJ; 
	}

	private String GetDG3(RequestLDSPack urlParameters) throws JsonParseException, JsonMappingException,
			IOException {
	
		String res = null;
		try {
			URL url = new URL(
					"http://192.168.1.27:700/LDSPack/CreateDG3");
			ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
			String json = ow.writeValueAsString(urlParameters);
			HttpURLConnection connection = (HttpURLConnection) url
					.openConnection();
			connection.setRequestMethod("POST");
			connection.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
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
	
			} else {
				///Trả về log mã lỗi và thông tin lỗi
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return res;
	}
	
	public static String removeAccent(String s) {
		  
		  String temp = Normalizer.normalize(s, Normalizer.Form.NFD);
		  Pattern pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
		  return pattern.matcher(temp).replaceAll("");
	}
	
	public List<NicHitTransaction> findPersonOrginal(Long personId){
		List<NicHitTransaction> result = null;
		try {
			
			do{
//				EppPerson person =  eppPersonService.getPersonById(personId);
//				personId = null;
//				if(person != null && person.getOriginId() != null && person.getOriginId() > 0){
//					result = new ArrayList<NicHitTransaction>();
//					List<NicUploadJob> nicUp = nicUploadJobService.findAllByPersonID(person.getOriginId());
//					if(nicUp != null && nicUp.size() > 0){
//						NicHitTransaction add = new NicHitTransaction();
//						add.setTranid(nicUp.get(0).getTransactionId());
//						add.setPersonId(person.getOriginId());
//						add.setHitId(99999999l);
//						result.add(add);
//						EppPerson personExist =  eppPersonService.getPersonById(person.getOriginId());
//						if(personExist != null && personExist.getOriginId() != null && personExist.getOriginId() > 0){
//							personId = personExist.getOriginId();
//						} 
//					} 
//				}
			} while (personId != null);
			
		} catch (Exception e){
			
		}
		return result;
	}
	public class UsersJson implements java.io.Serializable {

		private String userId;
		private Boolean sysAdminFlag;
		private String siteCode;
		private Date userStartDate;
		private Date userEndDate;
		private Boolean activeIndicator;
		private String systemId;
		private String createBy;
		private String createWkstnId;
		private Date createDate;
		private String updateBy;
		private String updateWkstnId;
		private Date updateDate;
		private String deleteBy;
		private String deleteWkstnId;
		private Date deleteDate;
		private Boolean deleteFlag;
		private Date dateOfPwdExpiry;
		private String siteGroupCode;
		private String userName;
		private String position;

		public UsersJson() {
		}

		public UsersJson(String userId) {
			this.userId = userId;
		}

		public UsersJson(String userId, Boolean sysAdminFlag, String siteCode,
				Date userStartDate, Date userEndDate, Boolean activeIndicator,
				String systemId, String createBy, String createWkstnId,
				Date createDate, String updateBy, String updateWkstnId,
				Date updateDate, String deleteBy, String deleteWkstnId,
				Date deleteDate, Boolean deleteFlag, Date dateOfPwdExpiry, String siteGroupCode
				) {
			this.userId = userId;
			this.sysAdminFlag = sysAdminFlag;
			this.siteCode = siteCode;
			this.userStartDate = userStartDate;
			this.userEndDate = userEndDate;
			this.activeIndicator = activeIndicator;
			this.systemId = systemId;
			this.createBy = createBy;
			this.createWkstnId = createWkstnId;
			this.createDate = createDate;
			this.updateBy = updateBy;
			this.updateWkstnId = updateWkstnId;
			this.updateDate = updateDate;
			this.deleteBy = deleteBy;
			this.deleteWkstnId = deleteWkstnId;
			this.deleteDate = deleteDate;
			this.deleteFlag = deleteFlag;
			this.dateOfPwdExpiry = dateOfPwdExpiry;
			this.siteGroupCode = siteGroupCode;
		}

		

		public String getUserName() {
			return userName;
		}

		public void setUserName(String userName) {
			this.userName = userName;
		}

		public String getPosition() {
			return position;
		}

		public void setPosition(String position) {
			this.position = position;
		}

		public String getSiteGroupCode() {
			return siteGroupCode;
		}

		public void setSiteGroupCode(String siteGroupCode) {
			this.siteGroupCode = siteGroupCode;
		}

		public Date getDateOfPwdExpiry() {
			return dateOfPwdExpiry;
		}

		public void setDateOfPwdExpiry(Date dateOfPwdExpiry) {
			this.dateOfPwdExpiry = dateOfPwdExpiry;
		}

		public String getUserId() {
			return this.userId;
		}

		public void setUserId(String userId) {
			this.userId = userId;
		}

		public Boolean getSysAdminFlag() {
			return this.sysAdminFlag;
		}
		
		public boolean isSysAdminFlag() {
			return this.sysAdminFlag!=null && this.sysAdminFlag.booleanValue();
		}

		public void setSysAdminFlag(Boolean sysAdminFlag) {
			this.sysAdminFlag = sysAdminFlag;
		}

		public String getSiteCode() {
			return this.siteCode;
		}

		public void setSiteCode(String siteCode) {
			this.siteCode = siteCode;
		}

		public Date getUserStartDate() {
			return this.userStartDate;
		}

		public void setUserStartDate(Date userStartDate) {
			this.userStartDate = userStartDate;
		}

		public Date getUserEndDate() {
			return this.userEndDate;
		}

		public void setUserEndDate(Date userEndDate) {
			this.userEndDate = userEndDate;
		}

		public Boolean getActiveIndicator() {
			return this.activeIndicator;
		}
		
		public boolean isActiveIndicator() {
			return this.activeIndicator!=null && this.activeIndicator.booleanValue();
		}

		public void setActiveIndicator(Boolean activeIndicator) {
			this.activeIndicator = activeIndicator;
		}

		public String getSystemId() {
			return this.systemId;
		}

		public void setSystemId(String systemId) {
			this.systemId = systemId;
		}

		public String getCreateBy() {
			return this.createBy;
		}

		public void setCreateBy(String createBy) {
			this.createBy = createBy;
		}

		public String getCreateWkstnId() {
			return this.createWkstnId;
		}

		public void setCreateWkstnId(String createWkstnId) {
			this.createWkstnId = createWkstnId;
		}

		public Date getCreateDate() {
			return this.createDate;
		}

		public void setCreateDate(Date createDate) {
			this.createDate = createDate;
		}

		public String getUpdateBy() {
			return this.updateBy;
		}

		public void setUpdateBy(String updateBy) {
			this.updateBy = updateBy;
		}

		public String getUpdateWkstnId() {
			return this.updateWkstnId;
		}

		public void setUpdateWkstnId(String updateWkstnId) {
			this.updateWkstnId = updateWkstnId;
		}

		public Date getUpdateDate() {
			return this.updateDate;
		}

		public void setUpdateDate(Date updateDate) {
			this.updateDate = updateDate;
		}

		public String getDeleteBy() {
			return this.deleteBy;
		}

		public void setDeleteBy(String deleteBy) {
			this.deleteBy = deleteBy;
		}

		public String getDeleteWkstnId() {
			return this.deleteWkstnId;
		}

		public void setDeleteWkstnId(String deleteWkstnId) {
			this.deleteWkstnId = deleteWkstnId;
		}

		public Date getDeleteDate() {
			return this.deleteDate;
		}

		public void setDeleteDate(Date deleteDate) {
			this.deleteDate = deleteDate;
		}

		public Boolean getDeleteFlag() {
			return this.deleteFlag;
		}
		
		public boolean isDeleteFlag() {
			return this.deleteFlag!=null && this.deleteFlag.booleanValue();
		}

		public void setDeleteFlag(Boolean deleteFlag) {
			this.deleteFlag = deleteFlag;
		}
	}
	
	public class WorkstationsJson implements java.io.Serializable {

		private String wkstnId;
		private String wkstnDesc;
		private String wkstnType;
		private Boolean accessibleFlag;
		private String counterPriority;
		private String siteCode;
		private String systemId;
		private String createBy;
		private String createWkstnId;
		private Date createDate;
		private String updateBy;
		private String updateWkstnId;
		private Date updateDate;
		private String deleteBy;
		private String deleteWkstnId;
		private Date deleteDate;
		private Boolean deleteFlag;
		
		public String getWkstnId() {
			return wkstnId;
		}
		public void setWkstnId(String wkstnId) {
			this.wkstnId = wkstnId;
		}
		public String getWkstnDesc() {
			return wkstnDesc;
		}
		public void setWkstnDesc(String wkstnDesc) {
			this.wkstnDesc = wkstnDesc;
		}
		public String getWkstnType() {
			return wkstnType;
		}
		public void setWkstnType(String wkstnType) {
			this.wkstnType = wkstnType;
		}
		public Boolean getAccessibleFlag() {
			return accessibleFlag;
		}
		public void setAccessibleFlag(Boolean accessibleFlag) {
			this.accessibleFlag = accessibleFlag;
		}
		public String getCounterPriority() {
			return counterPriority;
		}
		public void setCounterPriority(String counterPriority) {
			this.counterPriority = counterPriority;
		}
		public String getSiteCode() {
			return siteCode;
		}
		public void setSiteCode(String siteCode) {
			this.siteCode = siteCode;
		}
		public String getSystemId() {
			return systemId;
		}
		public void setSystemId(String systemId) {
			this.systemId = systemId;
		}
		public String getCreateBy() {
			return createBy;
		}
		public void setCreateBy(String createBy) {
			this.createBy = createBy;
		}
		public String getCreateWkstnId() {
			return createWkstnId;
		}
		public void setCreateWkstnId(String createWkstnId) {
			this.createWkstnId = createWkstnId;
		}
		public Date getCreateDate() {
			return createDate;
		}
		public void setCreateDate(Date createDate) {
			this.createDate = createDate;
		}
		public String getUpdateBy() {
			return updateBy;
		}
		public void setUpdateBy(String updateBy) {
			this.updateBy = updateBy;
		}
		public String getUpdateWkstnId() {
			return updateWkstnId;
		}
		public void setUpdateWkstnId(String updateWkstnId) {
			this.updateWkstnId = updateWkstnId;
		}
		public Date getUpdateDate() {
			return updateDate;
		}
		public void setUpdateDate(Date updateDate) {
			this.updateDate = updateDate;
		}
		public String getDeleteBy() {
			return deleteBy;
		}
		public void setDeleteBy(String deleteBy) {
			this.deleteBy = deleteBy;
		}
		public String getDeleteWkstnId() {
			return deleteWkstnId;
		}
		public void setDeleteWkstnId(String deleteWkstnId) {
			this.deleteWkstnId = deleteWkstnId;
		}
		public Date getDeleteDate() {
			return deleteDate;
		}
		public void setDeleteDate(Date deleteDate) {
			this.deleteDate = deleteDate;
		}
		public Boolean getDeleteFlag() {
			return deleteFlag;
		}
		public void setDeleteFlag(Boolean deleteFlag) {
			this.deleteFlag = deleteFlag;
		}
	}

	public class RolesJson implements java.io.Serializable {

		private String roleId;
		private String roleDesc;
		private String systemId;
		private String createBy;
		private String createWkstnId;
		private Date createDate;
		private String updateBy;
		private String updateWkstnId;
		private Date updateDate;
		private String deleteBy;
		private String deleteWkstnId;
		private Date deleteDate;
		private Boolean deleteFlag;

		public RolesJson() {
		}

		public RolesJson(String roleId) {
			this.roleId = roleId;
		}

		public RolesJson(String roleId, String roleDesc, String systemId,
				String createBy, String createWkstnId, Date createDate,
				String updateBy, String updateWkstnId, Date updateDate,
				String deleteBy, String deleteWkstnId, Date deleteDate,
				Boolean deleteFlag) {
			this.roleId = roleId;
			this.roleDesc = roleDesc;
			this.systemId = systemId;
			this.createBy = createBy;
			this.createWkstnId = createWkstnId;
			this.createDate = createDate;
			this.updateBy = updateBy;
			this.updateWkstnId = updateWkstnId;
			this.updateDate = updateDate;
			this.deleteBy = deleteBy;
			this.deleteWkstnId = deleteWkstnId;
			this.deleteDate = deleteDate;
			this.deleteFlag = deleteFlag;
		}

		public String getRoleId() {
			return this.roleId;
		}

		public void setRoleId(String roleId) {
			this.roleId = roleId;
		}

		public String getRoleDesc() {
			return this.roleDesc;
		}

		public void setRoleDesc(String roleDesc) {
			this.roleDesc = roleDesc;
		}

		public String getSystemId() {
			return this.systemId;
		}

		public void setSystemId(String systemId) {
			this.systemId = systemId;
		}

		public String getCreateBy() {
			return this.createBy;
		}

		public void setCreateBy(String createBy) {
			this.createBy = createBy;
		}

		public String getCreateWkstnId() {
			return this.createWkstnId;
		}

		public void setCreateWkstnId(String createWkstnId) {
			this.createWkstnId = createWkstnId;
		}

		public Date getCreateDate() {
			return this.createDate;
		}

		public void setCreateDate(Date createDate) {
			this.createDate = createDate;
		}

		public String getUpdateBy() {
			return this.updateBy;
		}

		public void setUpdateBy(String updateBy) {
			this.updateBy = updateBy;
		}

		public String getUpdateWkstnId() {
			return this.updateWkstnId;
		}

		public void setUpdateWkstnId(String updateWkstnId) {
			this.updateWkstnId = updateWkstnId;
		}

		public Date getUpdateDate() {
			return this.updateDate;
		}

		public void setUpdateDate(Date updateDate) {
			this.updateDate = updateDate;
		}

		public String getDeleteBy() {
			return this.deleteBy;
		}

		public void setDeleteBy(String deleteBy) {
			this.deleteBy = deleteBy;
		}

		public String getDeleteWkstnId() {
			return this.deleteWkstnId;
		}

		public void setDeleteWkstnId(String deleteWkstnId) {
			this.deleteWkstnId = deleteWkstnId;
		}

		public Date getDeleteDate() {
			return this.deleteDate;
		}

		public void setDeleteDate(Date deleteDate) {
			this.deleteDate = deleteDate;
		}

		public Boolean getDeleteFlag() {
			return this.deleteFlag;
		}

		public void setDeleteFlag(Boolean deleteFlag) {
			this.deleteFlag = deleteFlag;
		}
	}

	public class UserData {
		private List<Functions> lstfunc = new ArrayList<Functions>();
		private List<RolesJson> lstrole = new ArrayList<RolesJson>();
		private List<UsersJson> lstuser = new ArrayList<UsersJson>();
		private List<WorkstationsJson> lstwkst = new ArrayList<WorkstationsJson>();
		
		public List<Functions> getLstfunc() {
			return lstfunc;
		}
		public void setLstfunc(List<Functions> lstfunc) {
			this.lstfunc = lstfunc;
		}
		public List<RolesJson> getLstrole() {
			return lstrole;
		}
		public void setLstrole(List<RolesJson> lstrole) {
			this.lstrole = lstrole;
		}
		public List<UsersJson> getLstuser() {
			return lstuser;
		}
		public void setLstuser(List<UsersJson> lstuser) {
			this.lstuser = lstuser;
		}
		public List<WorkstationsJson> getLstwkst() {
			return lstwkst;
		}
		public void setLstwkst(List<WorkstationsJson> lstwkst) {
			this.lstwkst = lstwkst;
		}
	}
	
	private UsersJson mapperUser(Users u){
		UsersJson uJson = new UsersJson();
		uJson.setActiveIndicator(u.getActiveIndicator());
		uJson.setCreateBy(u.getCreateBy());
		uJson.setCreateDate(u.getCreateDate());
		uJson.setCreateWkstnId(u.getCreateWkstnId());
		uJson.setDateOfPwdExpiry(u.getDateOfPwdExpiry());
		uJson.setDeleteBy(u.getDeleteBy());
		uJson.setDeleteDate(u.getDeleteDate());
		uJson.setDeleteFlag(u.getDeleteFlag());
		uJson.setDeleteWkstnId(u.getDeleteWkstnId());
		uJson.setPosition(u.getPosition());
		uJson.setSiteCode(u.getSiteCode());
		uJson.setSiteGroupCode(u.getSiteGroupCode());
		uJson.setSysAdminFlag(u.getSysAdminFlag());
		uJson.setSystemId(u.getSystemId());
		uJson.setUpdateBy(u.getUpdateBy());
		uJson.setUpdateDate(u.getUpdateDate());
		uJson.setUpdateWkstnId(u.getUpdateWkstnId());
		uJson.setUserEndDate(u.getUserEndDate());
		uJson.setUserId(u.getUserId());
		uJson.setUserName(u.getUserName());
		uJson.setUserStartDate(u.getUserStartDate());		
		return uJson;
	}
	
	private RolesJson mapperRole(Roles r){
		RolesJson rJson = new RolesJson();
		rJson.setCreateBy(r.getCreateBy());
		rJson.setCreateDate(r.getCreateDate());
		rJson.setCreateWkstnId(r.getCreateWkstnId());
		rJson.setDeleteBy(r.getDeleteBy());
		rJson.setDeleteDate(r.getDeleteDate());
		rJson.setDeleteFlag(r.getDeleteFlag());
		rJson.setDeleteWkstnId(r.getDeleteWkstnId());
		rJson.setRoleDesc(r.getRoleDesc());
		rJson.setRoleId(r.getRoleId());
		rJson.setSystemId(r.getSystemId());
		rJson.setUpdateBy(r.getUpdateBy());
		rJson.setUpdateDate(r.getUpdateDate());
		rJson.setUpdateWkstnId(r.getUpdateWkstnId());
		return rJson;
	}
	
	private WorkstationsJson mapperWkst(Workstations w){
		WorkstationsJson wJson = new WorkstationsJson();
		wJson.setAccessibleFlag(w.getAccessibleFlag());
		wJson.setCounterPriority(w.getCounterPriority());
		wJson.setCreateBy(w.getCreateBy());
		wJson.setCreateDate(w.getCreateDate());
		wJson.setCreateWkstnId(w.getCreateWkstnId());
		wJson.setDeleteBy(w.getDeleteBy());
		wJson.setDeleteDate(w.getDeleteDate());
		wJson.setDeleteFlag(w.getDeleteFlag());
		wJson.setDeleteWkstnId(w.getDeleteWkstnId());
		wJson.setSiteCode(w.getSiteCode());
		wJson.setSystemId(w.getSystemId());
		wJson.setUpdateBy(w.getUpdateBy());
		wJson.setUpdateDate(w.getUpdateDate());
		wJson.setUpdateWkstnId(w.getUpdateWkstnId());
		wJson.setWkstnDesc(w.getWkstnDesc());
		wJson.setWkstnId(w.getWkstnId());
		wJson.setWkstnType(w.getWkstnType());
		return wJson;
	}
	
	private DetailRecieptFee mappingDetailRecieptFees(
			com.nec.asia.nic.dx.admin.DetailRecieptFee map) {
		DetailRecieptFee obj = new DetailRecieptFee();
		obj.setCodeBill(map.getCodeBill());
		obj.setCreateBy(map.getCreateBy());
		obj.setCreateDate(map.getCreateDate());
		obj.setDescription(map.getDescription());
		obj.setModifyBy(map.getModifyBy());
		obj.setModifyDate(map.getModifyDate());
		obj.setNumberBill(map.getNumberBill());
		obj.setPrice(map.getPrice());
		obj.setPriceFlag(map.getPriceFlag());
		obj.setReason(map.getReason());
		obj.setRecieptNo(map.getRecieptNo());
		return obj;
	}
	
	private EppRecieptManager mappingEppRecieptManager(
			com.nec.asia.nic.dx.admin.EppRecieptManager map) {
		EppRecieptManager obj = new EppRecieptManager();
		/*obj.setAuthenticationCode(map.getAuthenticationCode());
		obj.setCodeBill(map.getCodeBill());
		obj.setCompetentAuthorities(map.getCompetentAuthorities());
		obj.setCount(map.getCount());
		obj.setCreateBy(map.getCreateBy());
		obj.setCreateDate(map.getCreateDate());
		obj.setDateDob(map.getDateDob());
		obj.setDataPrint(map.getDataPrint());
		obj.setDateResultPlan(map.getDateResultPlan());
		obj.setDescription(map.getDescription());
		obj.setDob(map.getDob());
		obj.setFullname(map.getFullname());
		obj.setModifyBy(map.getModifyBy());
		obj.setModifyDate(map.getModifyDate());
		obj.setNationPlan(map.getNationPlan());
		obj.setNinNumber(map.getNinNumber());
		obj.setNote(map.getNote());
		obj.setNumberBill(map.getNumberBill());
		obj.setOfficers(map.getOfficers());
		obj.setPaymentAmount(map.getPaymentAmount());
		obj.setPaymentFlag(map.getPaymentFlag());
		obj.setPayPlan(map.getPayPlan());
		obj.setPhone(map.getPhone());
		obj.setReasonPayment(map.getReasonPayment());
		obj.setReceiveFlag(map.getReceiveFlag());
		obj.setRecieptName(map.getRecieptName());
		obj.setRecieptNo(map.getRecieptNo());
		obj.setReferPersonNo(map.getReferPersonNo());
		obj.setStatus(map.getStatus());
		obj.setSubmitter(map.getSubmitter());*/
		return obj;
	}
	public void testIMMI(){
		
		
		try {
			/*EppPerson personObj = eppPersonService.findByStringCode("ps006");
			if(personObj != null){
				String a = personObj.getCountryOfBirth();
			}*/
			/*List<FeeRecieptPayment> listExist = feeRecieptPaymentService.findAllFeeRecieptPayment("QN4200505001");
			//Xoa di neu da ton tai
			if(listExist != null && listExist.size() > 0)
				feeRecieptPaymentService.deleteObject(listExist);*/
			
			List<NicTransactionAttachment> nicAttachments = transactionDocumentDao.findNicTransactionAttachmentsInTypes("HPP200511084001", 
					new String[] {DOC_TYPE_PHOTO_CAPTURE, DOC_TYPE_PHOTO_CHIP, DOC_TYPE_THUMBNAIL_CAPTURE});
			
			if(nicAttachments != null && nicAttachments.size() > 0){
				String baseS = "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";

					for(NicTransactionAttachment attach : nicAttachments){
						NicTransactionAttachment facialChipDBO = attach;		
						facialChipDBO.setDocData(Base64.decodeBase64(baseS));
						facialChipDBO.setStorageRefId("");
						facialChipDBO.setUpdateBy("SYSTEM");
						facialChipDBO.setUpdateByName("SYSTEM");
						facialChipDBO.setUpdateDatetime(new Date());
						transactionDocumentDao.saveOrUpdate(facialChipDBO);
					}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		/*try {
			String tranid = "18AB1191012003011";
			NicUploadJob jobDBO = nicUploadJobService.getUploadJobByTransactionIdSinger(null, tranid);
			if(!jobDBO.getJobType().equals("LOS")){
				try {
					//Kiểm tra dữ liệu lịch sử XNC
					//Danh sách BMS / CPD nghi trùng
					//logger.info("[{}]-----checking data history Immigration - IssuePassport.", jobDBO.getTransactionId());
					Long idCPD = null;
					Long idBMS = null;
					
					String statusTransaction = "Đã phát hành";
					String statusDocumentData = "Đang hoạt động";
					
					Boolean statusDocumentDataLock = false;
		
					List<String> listTranCPD = new ArrayList<String>();
					List<String> listTranBMS = new ArrayList<String>();
					InvestigationListInfoTargetsDto lstR = new InvestigationListInfoTargetsDto();
					List<InvestigationListInfoTargetDto> outputR = new ArrayList<InvestigationListInfoTargetDto>();
					List<ImmiHistory> listImmi = new ArrayList<ImmiHistory>();
					
					List<NicSearchResult> searchResults = nicSearchResultService.findAllByJobId(jobDBO.getWorkflowJobId());
					if(searchResults != null && searchResults.size() > 0){
						for(NicSearchResult sR_ : searchResults){
							if(sR_.getTypeSearch().equals(NicSearchResult.TYPE_SEARCH__CPD) && sR_.getHasHit()){
								idCPD = sR_.getSearchResultId();
							}
							else if(sR_.getTypeSearch().equals(NicSearchResult.TYPE_SEARCH__FP_1_N) && sR_.getHasHit() != null && sR_.getHasHit()){
								idBMS = sR_.getSearchResultId();
							}
						}
					}
					
					//logger.info("[{}]CHECKING WITH CPD==============", jobDBO.getTransactionId());
					///Dữ liệu kiểm tra với CPD
					if(idCPD != null){
						//Lấy dữ liệu trùng trong bảng EPP_SEARCH_HIT_RESULT
						List<NicSearchHitResult> listHitCPD = nicSearchHitResultService.findHitPositions(null, idCPD);
						if(listHitCPD != null && listHitCPD.size() > 0){
							for(NicSearchHitResult sHR : listHitCPD){
								String passportNo = "";
								InvestigationListInfoTargetDto outPut = new InvestigationListInfoTargetDto();
								InvestigationListInfoTargetDto outPutP = null;
								if(sHR.getDataSource().equals(NicSearchHitResult.hitInfo_DataSource_CPD)){
									//Lấy Id của đối tượng trùng
									String transactionId_CPD = "";
									transactionId_CPD = sHR.getTransactionIdHit();
									
									if(StringUtils.isNotEmpty(transactionId_CPD)){
										//Lấy dữ liệu thông tin hồ sơ của transaction
										NicUploadJob dtUpJob = nicUploadJobService.getUploadJobByTransactionIdSinger(null, transactionId_CPD);
										NicTransaction nicTran = nicTransactionService.getNicTransactionById(transactionId_CPD);
										if(nicTran != null){
											//Kiểm tra trạng thái của hồ sơ
											if(!nicTran.getTransactionStatus().equals(NicTransaction.TRANSACTION_STATUS_RIC_ISSUED)){//TH chưa phát hành
												statusTransaction = "Đang xử lý - Giai đoạn: " + nicTran.getTransactionStatus();
											}
											else {
												//Thông tin hộ chiếu
												Collection<NicDocumentData> nicDocs = documentDataService.findByTransactionId(transactionId_CPD);
												if(nicDocs != null && nicDocs.size() > 0){
													outPutP = new InvestigationListInfoTargetDto();
													List<NicDocumentData> nicDocs_ = new ArrayList<NicDocumentData>(nicDocs);
													NicDocumentData nicDoc = nicDocs_.get(0);
													passportNo = nicDoc.getId().getPassportNo();
													if(nicDoc.getStatus().equals(NicDocumentData.DOCUMENT_DATA_STATUS_NONE)){
														statusDocumentData = "Đã khóa";
														statusDocumentDataLock = true;
													}
													else if(nicDoc.getStatus().equals(NicDocumentData.DOCUMENT_DATA_STATUS_PERSONALIZED))
													{
														statusDocumentData = "Cá thể hóa";
													}
													
													outPutP.setPackageId_O(nicTran.getPackageID());
													outPutP.setTransactionId_O(transactionId_CPD);
													outPutP.setTypeTransaction_O(nicTran.getTransactionType());
													outPutP.setReg_O(nicTran.getRegSiteCode());
													outPutP.setStatus_O(statusDocumentData);
													outPutP.setPassportNo_O(nicDoc.getId().getPassportNo());
													
													String issuePP = "";
													if(nicDoc.getDateOfIssue() != null){
														String pattern = "dd/MM/yyyy";
														DateFormat df = new SimpleDateFormat(pattern);
														issuePP = df.format(nicDoc.getDateOfIssue());
													}
													outPutP.setIssueDatePassport_O(issuePP);
													
													String receivePP = "";
													if(nicDoc.getReceiveDatetime() != null){
														String pattern = "dd/MM/yyyy";
														DateFormat df = new SimpleDateFormat(pattern);
														receivePP = df.format(nicDoc.getReceiveDatetime());
													}
													outPutP.setPayDatePassport_O(receivePP);
													outPutP.setPayPlacePassport_O(nicTran.getIssSiteCode());
													
													outPutP.setPersonRecieve_O("");
													outPutP.setRemark_O("");

												}
												else{
													//logger.info("Not found information Document data in database TransactionId: " + transactionId_CPD);
												}
													
											}
											
											//Thông tin cá nhân
											NicRegistrationData nicRegis = nicTran.getNicRegistrationData();
											//logger.info("[" + nicRegis.getTransactionId() + "]nicRegis: " + nicRegis.getSurnameLine1());
											if(nicRegis != null){
												outPut.setFullName_O(nicRegis.getSurnameLine1());
												//outPut.setGender_O(nicRegis.getGender());
												if(nicRegis.getGender() != null){
													outPut.setGender_O(nicRegis.getGender().equals("M") ? "Nam" : "Nữ");
												}else {
													outPut.setGender_O("Không");
												}
												String dob = "";
												if(nicRegis.getDateOfBirth() != null){
													String pattern = "dd/MM/yyyy";
													DateFormat df = new SimpleDateFormat(pattern);
													dob = df.format(nicRegis.getDateOfBirth());
												}
												outPut.setDob_O(dob);
												outPut.setNin_O(nicTran.getNin());
												outPut.setPob_O(nicRegis.getPlaceOfBirth());
												outPut.setReligion_O(nicRegis.getReligion());
												outPut.setNation_O(nicRegis.getNation());
												outPut.setPhone_O(nicRegis.getContactNo());
												outPut.setTransactionId_O(transactionId_CPD);
												//Xử lý ảnh đối tượng 
												List<NicTransactionAttachment> photoList = nicTransactionAttachmentService
														.findNicTransactionAttachments(
																transactionId_CPD,
																NicTransactionAttachment.DOC_TYPE_PHOTO_CAPTURE,
																NicTransactionAttachment.DEFAULT_SERIAL_NO);
												if (CollectionUtils.isNotEmpty(photoList)
														&& photoList.size() > 0) {
													String photoStr = Base64.encodeBase64String(photoList.get(0)
															.getDocData());
													outPut.setPhoto_O(photoStr);
												}
												
												//Thông tin người thân
												outPut.setFullNameFather_O(nicRegis.getFatherSurname() + " " + nicRegis.getFatherFirstname());
												String fatherDob = "";
												if(nicRegis.getFatherDob() != null){
													String pattern = "dd/MM/yyyy";
													DateFormat df = new SimpleDateFormat(pattern);
													fatherDob = df.format(nicRegis.getFatherDob());
												}
												outPut.setDobFather_O(fatherDob);
												
												outPut.setFullNameMother_O(nicRegis.getMotherSurname() + " " + nicRegis.getMotherFirstname());
												String motherDob = "";
												if(nicRegis.getMotherDob() != null){
													String pattern = "dd/MM/yyyy";
													DateFormat df = new SimpleDateFormat(pattern);
													motherDob = df.format(nicRegis.getMotherDob());
												}
												outPut.setDobMother_O(motherDob);
												
												String spouserDob = "";
												if(nicRegis.getSpouseDob()!= null){
													String pattern = "dd/MM/yyyy";
													DateFormat df = new SimpleDateFormat(pattern);
													spouserDob = df.format(nicRegis.getSpouseDob());
												}
												outPut.setDobSpouser_O(spouserDob);
											}
											outputR.add(outPut);
											
											else
											{
												logger.info("Not found information Registration data in database TransactionId: " + transactionId_CPD);
											}
											
											//Kiểm tra đối với dữ liệu lịch sử XNC của đối tượng
											
											listImmi = nicImmiHistoryService.findByNinPPno(nicTran.getNin(), passportNo);
											
											//listTranCPD.add(transactionId_CPD);
											//Đóng gói dữ liệu History
											String xmlImmi = "";
											String xmlIssue = "";
											//logger.info("[{}]XML data - TID: ", transactionId_CPD);
											if(outPutP != null){
												//lstR.setInfoTarget(outputR);
												JAXBContext jaxbContext = JAXBContext.newInstance(InvestigationListInfoTargetsDto.class);
												Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
												StringWriter sw = new StringWriter();
												jaxbMarshaller.marshal(outPutP, sw);
												xmlIssue = sw.toString();
											}
											
											if(listImmi != null && listImmi.size() > 0){
												ImmiHistorys listImmis = new ImmiHistorys();
												listImmis.setImmiHistory(listImmi);
												JAXBContext jaxbContext = JAXBContext.newInstance(ImmiHistorys.class);
												Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
												StringWriter sw = new StringWriter();
												jaxbMarshaller.marshal(listImmis, sw);
												xmlImmi = sw.toString();
											}
											
											EppBufFamilyDto family = new EppBufFamilyDto();
											List<EppPersonDto> lstPerson = new ArrayList<EppPersonDto>();
											family.setFatherSurname(dtUpJob.getNicTransaction().getNicRegistrationData().getFatherSurname());
											family.setMotherSurname(dtUpJob.getNicTransaction().getNicRegistrationData().getMotherSurname());
											family.setSpouseSurname(dtUpJob.getNicTransaction().getNicRegistrationData().getSpouseSurname());
											
											family.setFatherDob(dtUpJob.getNicTransaction().getNicRegistrationData().getFatherDob());
											family.setMotherDob(dtUpJob.getNicTransaction().getNicRegistrationData().getMotherDob());
											family.setSpouseDob(dtUpJob.getNicTransaction().getNicRegistrationData().getSpouseDob());
											
											String xmlPerson = dtUpJob.getNicTransaction().getInfoPerson();
											
											if(StringUtils.isNotEmpty(xmlPerson)){
												JSONObject xmlJSONObj = XML.toJSONObject(xmlPerson);
												if(xmlJSONObj != null){
													if(xmlJSONObj.has("EPP_PERSONS") && !xmlJSONObj.isNull("EPP_PERSONS")){
														JSONObject resp = xmlJSONObj.getJSONObject("EPP_PERSONS");
														if(resp.has("EPP_PERSON") && !resp.isNull("EPP_PERSON")){
															Object item = resp.get("EPP_PERSON");
															try{
																if (item instanceof JSONArray){
																	JSONArray arrayResp = (JSONArray) item;
																	for (int i = 0; i < arrayResp.length(); i++) {
																		EppPersonDto person = new EppPersonDto();
																		JSONObject myResponse = new JSONObject();
																		myResponse = arrayResp.getJSONObject(i);
																		String id = "";
																		if(myResponse.has("PLACE_OF_BIRTH_ID") && !myResponse.isNull("PLACE_OF_BIRTH_ID")){
																			person.setPlaceOfBirthId(myResponse.getString("PLACE_OF_BIRTH_ID"));
																		}
																		if(myResponse.has("SEARCH_NAME") && !myResponse.isNull("SEARCH_NAME")){
																			person.setSearchName(myResponse.getString("SEARCH_NAME"));
																		}
																		if(myResponse.has("NAME") && !myResponse.isNull("NAME")){
																			person.setName(myResponse.getString("NAME"));
																		}
																		if(myResponse.has("DATE_OF_BIRTH") && !myResponse.isNull("DATE_OF_BIRTH")){
																			person.setDateOfBirth(String.valueOf(myResponse.getInt("DATE_OF_BIRTH")));
																		}
																		if(myResponse.has("GENDER") && !myResponse.isNull("GENDER")){
																			person.setGender(myResponse.getString("GENDER"));
																		}
																		if(myResponse.has("ID") && !myResponse.isNull("ID")){
																			id = String.valueOf(myResponse.getInt("ID"));
																			person.setId(id);
																		}
																		if(myResponse.has("TYPE_") && !myResponse.isNull("TYPE_")){
																			person.setType_(myResponse.getString("TYPE_"));
																		}
																		if(myResponse.has("NATIONALITY_ID") && !myResponse.isNull("NATIONALITY_ID")){
																			person.setNationalityId(myResponse.getString("NATIONALITY_ID"));
																		}
																		
																		lstPerson.add(person);
																	}
															    }
																else
															    {
																	EppPersonDto person = new EppPersonDto();
																	JSONObject myResponse = (JSONObject) item;
																	String id = "";
																	if(myResponse.has("PLACE_OF_BIRTH_ID") && !myResponse.isNull("PLACE_OF_BIRTH_ID")){
																		person.setPlaceOfBirthId(myResponse.getString("PLACE_OF_BIRTH_ID"));
																	}
																	if(myResponse.has("SEARCH_NAME") && !myResponse.isNull("SEARCH_NAME")){
																		person.setSearchName(myResponse.getString("SEARCH_NAME"));
																	}
																	if(myResponse.has("NAME") && !myResponse.isNull("NAME")){
																		person.setName(myResponse.getString("NAME"));
																	}
																	if(myResponse.has("DATE_OF_BIRTH") && !myResponse.isNull("DATE_OF_BIRTH")){
																		person.setDateOfBirth(String.valueOf(myResponse.getInt("DATE_OF_BIRTH")));
																	}
																	if(myResponse.has("GENDER") && !myResponse.isNull("GENDER")){
																		person.setGender(myResponse.getString("GENDER"));
																	}
																	if(myResponse.has("ID") && !myResponse.isNull("ID")){
																		id = String.valueOf(myResponse.getInt("ID"));
																		person.setId(id);
																	}
																	if(myResponse.has("TYPE_") && !myResponse.isNull("TYPE_")){
																		person.setType_(myResponse.getString("TYPE_"));
																	}
																	if(myResponse.has("NATIONALITY_ID") && !myResponse.isNull("NATIONALITY_ID")){
																		person.setNationalityId(myResponse.getString("NATIONALITY_ID"));
																	}
																	
																	lstPerson.add(person);
															    }
															}catch(Exception e){
																
															}
														}
													}
												}
											}
											
											String xmlFamily = "";
											JAXBContext jaxbContext = JAXBContext.newInstance(EppBufFamilyDto.class, EppPersonDto.class);
											Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
											StringWriter sw = new StringWriter();
											jaxbMarshaller.marshal(family, sw);
											xmlFamily = sw.toString();
											
											String status = "Đang xử lý";
											NicDocumentData docData = null;
											Collection<NicDocumentData> docData_ = documentDataService
													.findByTransactionId(jobDBO.getTransactionId());
											if(docData_ != null && docData_.size() > 0){
												List<NicDocumentData> nicDocs_ = new ArrayList<NicDocumentData>(
														docData_);
												docData = nicDocs_.get(0);
												if(docData == null && jobDBO.getInvestigationStatus().equals(NicUploadJob.RECORD_STATUS_APPROVE_PERSO)){
													status = "Đã phê duyệt. Chờ in";
												}
												else if(docData != null){
													if(docData.getStatus().equals(NicDocumentData.DOCUMENT_DATA_STATUS_ISSUANCE) && jobDBO.getNicTransaction().getTransactionStatus().equals(NicTransaction.TRANSACTION_STATUS_RIC_ISSUED)){
														status = "Đã trả hộ chiếu cho công dân";
													}
													else {
														status = "Đã in. Chờ trả hộ chiếu";
													}
												}
											}
											EppBufInfoDocDto info = new EppBufInfoDocDto();
											info.setTransactionId(dtUpJob.getTransactionId());
											info.setRecieptNo(dtUpJob.getReceiptNo());
											if(dtUpJob.getNicTransaction().getTransactionType().equals("REP"))
												info.setTypeTransaction("Thay thế");
											else if(dtUpJob.getNicTransaction().getTransactionType().equals("REP_LOS"))
												info.setTypeTransaction("Thay thế do mất hỏng");
											else
												info.setTypeTransaction("Cấp mới");
											
											SiteRepository siteR = siteService.getSiteRepoById(dtUpJob.getNicTransaction().getRegSiteCode());
											if(siteR != null)
												info.setRegSiteCode(siteR.getSiteName());
											else
												info.setRegSiteCode(dtUpJob.getNicTransaction().getRegSiteCode());
											
											info.setStatus(status);
											
											List<NicTransactionPackage> nicP = nicTransactionPackageService.getPackageNameByTransactionId(dtUpJob.getTransactionId());
											NicTransactionPackage lastPack = null;
											if(nicP != null && nicP.size() > 0){
												lastPack = new NicTransactionPackage();
												long bigId = 0;
												for(NicTransactionPackage pack : nicP){
													if(pack.getTypeList() == 8 && bigId < pack.getId()){
														bigId = pack.getId();
													}
												}
											}
											info.setRemarkApprove(lastPack.getNoteLeaderApprove());
											info.setNote(lastPack.getNoteApprove());
											String nameApprover = "";
											String levelApprover = "";
											if(StringUtils.isNotEmpty(dtUpJob.getNicTransaction().getLeaderOfficerId())){
												Users user = userService.findById(dtUpJob.getNicTransaction().getLeaderOfficerId());
												if(user != null){
													nameApprover = user.getUserName();
													levelApprover = user.getPosition();
												}
												else{
													nameApprover = dtUpJob.getNicTransaction().getLeaderOfficerId();
												}
											}
											info.setNameApprover(nameApprover);
											info.setLevelApprover(levelApprover);
											
											if(docData != null){
												info.setPassportNo(docData.getId().getPassportNo());
												info.setIssuePassportDate(docData.getDateOfIssue());
												info.setReleaseDate(docData.getIssueDatetime());
												
											}
											info.setReceiver(dtUpJob.getNicTransaction().getReceiver());
											info.setRecieptNo(dtUpJob.getNicTransaction().getPackageID());
											String xmlInfo = "";
											JAXBContext jaxbContextInfo = JAXBContext.newInstance(EppBufInfoDocDto.class);
											Marshaller jaxbMarshallerInfo = jaxbContextInfo.createMarshaller();
											StringWriter swI = new StringWriter();
											jaxbMarshallerInfo.marshal(info, swI);
											xmlInfo = swI.toString();
											
											//Lưu lại dữ liệu History
											//logger.info("[{}]SAVE HISTORY =================", transactionId_CPD);
											
											BufEppPersonInvestigation objBuf = bufPersonInvestigationService.findByTranId(transactionId_CPD);
											if(objBuf == null){
												objBuf = new BufEppPersonInvestigation();
												objBuf.setCreateDatetime(new Date());
												objBuf.setCreateBy("SYSTEM");
												objBuf.setCreateWkstnId("SYSTEM-MACHINE");
											}
											else
											{
												objBuf.setUpdateBy("SYSTEM");
												objBuf.setUpdateWkstnId("SYSTEM-MACHINE");
												objBuf.setUpdateDatetime(new Date());
											}
											
											objBuf.setTransactionId(transactionId_CPD);
											objBuf.setDataHistoryPassport(xmlIssue);
											objBuf.setDataImmihistory(xmlImmi);
											objBuf.setDataFamily(xmlFamily);
											objBuf.setDataInfoDocument(xmlInfo);
											bufPersonInvestigationService.saveOrUpdate(objBuf);
											//outputR.add(outPut);
										}
										else
										{
											//logger.info("Not found information Transaction in database TransactionId: " + transactionId_CPD);
										}
									}
										
								}
							}
						}
						else
						{
							//logger.info("[{}]Not found list hit CPD by idCPD: " + idCPD, jobDBO.getTransactionId());
						}
						
					}
					
					//logger.info("[{}]CHECKING WITH BMS=================", jobDBO.getTransactionId());
					///Dữ liệu kiểm tra với BMS
					if(idBMS != null){
						//Lấy dữ liệu trùng trong bảng EPP_SEARCH_HIT_RESULT
						List<NicSearchHitResult> listHitBMS = nicSearchHitResultService.findHitPositions(null, idBMS);
						if(listHitBMS != null && listHitBMS.size() > 0){
							for(NicSearchHitResult sHR : listHitBMS){
								String passportNo = "";
								if(sHR.getDataSource().equals(NicSearchHitResult.hitInfo_DataSource_BMS)){
									//Lấy Id của đối tượng trùng
									String transactionId_BMS = "";
									transactionId_BMS = sHR.getTransactionIdHit();
									if(transactionId_BMS.equals("18A1191014001001")){
										String a = transactionId_BMS;
										String b = a;
									}
									if(transactionId_BMS.equals("18AB1191012003011")){
										String a = transactionId_BMS;
										String b = a;
									}
									if(StringUtils.isNotEmpty(transactionId_BMS)){
										//Kiểm tra trong ListCheckCPD có trùng transactionID không. Nếu có thì chỉ thêm dữ liệu BMS vào vị trí đối tượng
										if(listTranCPD.contains(transactionId_BMS) && outputR != null && outputR.size() > 0){
											InvestigationListInfoTargetDto updateObj = outputR.get(listTranCPD.indexOf(transactionId_BMS));
											updateObj.setScoreBMSall_O(sHR.getHitInfo());
											int score = 0;
											//Tính số điểm trung bình cho vân tay
											//Kiểm tra chuỗi dữ liệu
											if(StringUtils.isNotEmpty(sHR.getHitInfo())){
												String[] listHit = sHR.getHitInfo().split(","); //Tách chuỗi theo dấu ","
												if(listHit.length > 0){
													for(int i = 0; i < listHit.length; i++){
													   if(Double.parseDouble((listHit[i].split("=")[1])) > score){
														   double d = Double.parseDouble((listHit[i].split("=")[1]));
														   score = (int) d;
													   }
													}
												}
											}
											updateObj.setScoreBMS_O(score);
											
											//Xóa dữ liệu cũ trong 2 list
											listTranCPD.remove(transactionId_BMS);
											outputR.remove(listTranCPD.indexOf(transactionId_BMS));
											
											//Cập nhật dữ liệu mới
											listTranCPD.add(transactionId_BMS);
											outputR.add(updateObj);
										}
										else{
											
											InvestigationListInfoTargetDto outPut = new InvestigationListInfoTargetDto();
											InvestigationListInfoTargetDto outPutP = null;
											outPut.setScoreBMSall_O(sHR.getHitInfo());
											int score = 0;
											//Tính số điểm trung bình cho vân tay
											//Kiểm tra chuỗi dữ liệu
											if(StringUtils.isNotEmpty(sHR.getHitInfo())){
												String[] listHit = sHR.getHitInfo().split(","); //Tách chuỗi theo dấu ","
												if(listHit.length > 0){
													for(int i = 0; i < listHit.length; i++){
													   if(Double.parseDouble((listHit[i].split("=")[1])) > score){
														   double d = Double.parseDouble((listHit[i].split("=")[1]));
													   	   score = (int) d;
													   }
													}
												}
											}
											outPut.setScoreBMS_O(score);
											
											//Lấy dữ liệu thông tin hồ sơ của transaction
											NicTransaction nicTran = nicTransactionService.getNicTransactionById(transactionId_BMS);
											if(nicTran.getTransactionId().equals("18A1191014001001")){
												String a = nicTran.getTransactionId();
												String b = a;
											}
											if(nicTran.getTransactionId().equals("18AB1191012003011")){
												String a = nicTran.getTransactionId();
												String b = a;
											}
											if(nicTran != null){
												//Kiểm tra trạng thái của hồ sơ
												if(!nicTran.getTransactionStatus().equals(NicTransaction.TRANSACTION_STATUS_RIC_ISSUED)){//TH chưa phát hành
													statusTransaction = "Đang xử lý - Giai đoạn: " + nicTran.getTransactionStatus();
												}
												else{
													//Thông tin hộ chiếu
													
													Collection<NicDocumentData> nicDocs = documentDataService.findByTransactionId(transactionId_BMS);
													if(nicDocs != null && nicDocs.size() > 0){
														List<NicDocumentData> nicDocs_ = new ArrayList<NicDocumentData>(nicDocs);
														NicDocumentData nicDoc = nicDocs_.get(0);
														outPutP = new InvestigationListInfoTargetDto();
														if(nicDoc.getStatus().equals(NicDocumentData.DOCUMENT_DATA_STATUS_NONE)){
															statusDocumentData = "Đã khóa";
															statusDocumentDataLock = true;
															passportNo = nicDoc.getId().getPassportNo();
														}
														else if(nicDoc.getStatus().equals(NicDocumentData.DOCUMENT_DATA_STATUS_PERSONALIZED))
														{
															statusDocumentData = "Cá thể hóa";
														}
														
														outPutP.setPackageId_O(nicTran.getPackageID());
														outPutP.setTransactionId_O(transactionId_BMS);
														outPutP.setTypeTransaction_O(nicTran.getTransactionType());
														outPutP.setReg_O(nicTran.getRegSiteCode());
														outPutP.setStatus_O(statusDocumentData);
														outPutP.setPassportNo_O(nicDoc.getId().getPassportNo());
														
														String issuePP = "";
														if(nicDoc.getDateOfIssue() != null){
															String pattern = "dd/MM/yyyy";
															DateFormat df = new SimpleDateFormat(pattern);
															issuePP = df.format(nicDoc.getDateOfIssue());
														}
														outPutP.setIssueDatePassport_O(issuePP);
														
														String receivePP = "";
														if(nicDoc.getReceiveDatetime() != null){
															String pattern = "dd/MM/yyyy";
															DateFormat df = new SimpleDateFormat(pattern);
															receivePP = df.format(nicDoc.getReceiveDatetime());
														}
														outPutP.setPayDatePassport_O(receivePP);
														outPutP.setPayPlacePassport_O(nicTran.getIssSiteCode());
														outPutP.setPersonRecieve_O("");
														outPutP.setRemark_O("");
														
													}
													else{
														//logger.info("Not found information Document data in database TransactionId: " + transactionId_BMS);
													}
												}

												//Thông tin cá nhân
												NicRegistrationData nicRegis = nicTran.getNicRegistrationData();
												//logger.info("[" + nicRegis.getTransactionId() + "]nicRegis: " + nicRegis.getSurnameLine1());
												if(nicRegis != null){
													
													outPut.setFullName_O(nicRegis.getSurnameLine1());
													//outPut.setGender_O(nicRegis.getGender());
													if(nicRegis.getGender() != null){
														outPut.setGender_O(nicRegis.getGender().equals("M") ? "Nam" : "Nữ");
													}else {
														outPut.setGender_O("Không");
													}
													String dob = "";
													if(nicRegis.getDateOfBirth() != null){
														String pattern = "dd/MM/yyyy";
														DateFormat df = new SimpleDateFormat(pattern);
														dob = df.format(nicRegis.getDateOfBirth());
													}
													outPut.setDob_O(dob);
													outPut.setNin_O(nicTran.getNin());
													outPut.setPob_O(nicRegis.getPlaceOfBirth());
													outPut.setReligion_O(nicRegis.getReligion());
													outPut.setNation_O(nicRegis.getNation());
													outPut.setPhone_O(nicRegis.getContactNo());
													
													//Xử lý ảnh đối tượng 
													List<NicTransactionAttachment> photoList = nicTransactionAttachmentService
															.findNicTransactionAttachments(
																	transactionId_BMS,
																	NicTransactionAttachment.DOC_TYPE_PHOTO_CAPTURE,
																	NicTransactionAttachment.DEFAULT_SERIAL_NO);
													if (CollectionUtils.isNotEmpty(photoList)
															&& photoList.size() > 0) {
														String photoStr = Base64.encodeBase64String(photoList.get(0)
																.getDocData());
														outPut.setPhoto_O(photoStr);
													}
													
													//Thông tin người thân
													outPut.setFullNameFather_O(nicRegis.getFatherSurname() + " " + nicRegis.getFatherFirstname());
													String fatherDob = "";
													if(nicRegis.getFatherDob() != null){
														String pattern = "dd/MM/yyyy";
														DateFormat df = new SimpleDateFormat(pattern);
														fatherDob = df.format(nicRegis.getFatherDob());
													}
													outPut.setDobFather_O(fatherDob);
													
													outPut.setFullNameMother_O(nicRegis.getMotherSurname() + " " + nicRegis.getMotherFirstname());
													String motherDob = "";
													if(nicRegis.getMotherDob() != null){
														String pattern = "dd/MM/yyyy";
														DateFormat df = new SimpleDateFormat(pattern);
														motherDob = df.format(nicRegis.getMotherDob());
													}
													outPut.setDobMother_O(motherDob);
													
													String spouserDob = "";
													if(nicRegis.getSpouseDob()!= null){
														String pattern = "dd/MM/yyyy";
														DateFormat df = new SimpleDateFormat(pattern);
														spouserDob = df.format(nicRegis.getSpouseDob());
													}
													outPut.setDobSpouser_O(spouserDob);
												}
												outputR.add(outPut);
												
												
												else
												{
													logger.info("Not found information Registration data in database TransactionId: " + transactionId_BMS);
												}
												
												//Kiểm tra danh sách giấy tờ mất/hủy
												//Tìm kiếm trong danh sách TRANSACTION với kiểu LOS (chỉ tìm với hộ chiếu khóa RIC_DEACTIVATED)
												List<NicTransaction> listLost = new ArrayList<NicTransaction>();
												if(statusDocumentDataLock && nicTran.getTransactionStatus().equals(NicTransaction.TRANSACTION_STATUS_RIC_DEACTIVATED)){
													//Kiểm tra qua số hộ chiếu
													NicTransaction nicTranLost_PPno = nicTransactionService.getNicTransactionByPrevPPno(passportNo, "", "");
													if(nicTranLost_PPno != null)
														listLost.add(nicTranLost_PPno);
													
													//Kiểm tra qua CMND / CCCD (tìm bản ghi đầu tiên)
													NicTransaction nicTranLost_nin = nicTransactionService.getNicTransactionByPrevPPno("", "", nicTran.getNin());
													if(nicTranLost_nin != null)
														listLost.add(nicTranLost_nin);
												}
												if(listLost != null && listLost.size() > 0)
													outputR.setListLost(listLost);
												
												//Kiểm tra đối với dữ liệu lịch sử XNC của đối tượng
												List<ImmiHistory> listImmi = new ArrayList<ImmiHistory>();
												listImmi = nicImmiHistoryService.findByNinPPno(nicTran.getNin(), passportNo);
												if(listImmi != null && listImmi.size() > 0)
													outputR.setListImmi(listImmi);
												
												
												//Kiểm tra đối với dữ liệu lịch sử XNC của đối tượng
												listImmi = nicImmiHistoryService.findByNinPPno(nicTran.getNin(), passportNo);
												listTranBMS.add(transactionId_BMS);
												
												//Đóng gói dữ liệu History
												String xmlImmi = "";
												String xmlIssue = "";
												//logger.info("[{}]XML data", jobDBO.getTransactionId());
												if(outPutP != null){
													//lstR.setInfoTarget(outputR);
													JAXBContext jaxbContext = JAXBContext.newInstance(InvestigationListInfoTargetsDto.class);
													Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
													StringWriter sw = new StringWriter();
													jaxbMarshaller.marshal(outPutP, sw);
													xmlIssue = sw.toString();
												}
												
												if(listImmi != null && listImmi.size() > 0){
													ImmiHistorys listImmis = new ImmiHistorys();
													listImmis.setImmiHistory(listImmi);
													JAXBContext jaxbContext = JAXBContext.newInstance(ImmiHistorys.class);
													Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
													StringWriter sw = new StringWriter();
													jaxbMarshaller.marshal(listImmis, sw);
													xmlImmi = sw.toString();
												}
												
												EppBufFamilyDto family = new EppBufFamilyDto();
												List<EppPersonDto> lstPerson = new ArrayList<EppPersonDto>();
												family.setFatherSurname(jobDBO.getNicTransaction().getNicRegistrationData().getFatherSurname());
												family.setMotherSurname(jobDBO.getNicTransaction().getNicRegistrationData().getMotherSurname());
												family.setSpouseSurname(jobDBO.getNicTransaction().getNicRegistrationData().getSpouseSurname());
												
												family.setFatherDob(jobDBO.getNicTransaction().getNicRegistrationData().getFatherDob());
												family.setMotherDob(jobDBO.getNicTransaction().getNicRegistrationData().getMotherDob());
												family.setSpouseDob(jobDBO.getNicTransaction().getNicRegistrationData().getSpouseDob());
												
												String xmlPerson = jobDBO.getNicTransaction().getInfoPerson();
												
												if(StringUtils.isNotEmpty(xmlPerson)){
													JSONObject xmlJSONObj = XML.toJSONObject(xmlPerson);
													if(xmlJSONObj != null){
														if(xmlJSONObj.has("EPP_PERSONS") && !xmlJSONObj.isNull("EPP_PERSONS")){
															JSONObject resp = xmlJSONObj.getJSONObject("EPP_PERSONS");
															if(resp.has("EPP_PERSON") && !resp.isNull("EPP_PERSON")){
																Object item = resp.get("EPP_PERSON");
																try{
																	if (item instanceof JSONArray){
																		JSONArray arrayResp = (JSONArray) item;
																		for (int i = 0; i < arrayResp.length(); i++) {
																			EppPersonDto person = new EppPersonDto();
																			JSONObject myResponse = new JSONObject();
																			myResponse = arrayResp.getJSONObject(i);
																			String id = "";
																			if(myResponse.has("PLACE_OF_BIRTH_ID") && !myResponse.isNull("PLACE_OF_BIRTH_ID")){
																				person.setPlaceOfBirthId(myResponse.getString("PLACE_OF_BIRTH_ID"));
																			}
																			if(myResponse.has("SEARCH_NAME") && !myResponse.isNull("SEARCH_NAME")){
																				person.setSearchName(myResponse.getString("SEARCH_NAME"));
																			}
																			if(myResponse.has("NAME") && !myResponse.isNull("NAME")){
																				person.setName(myResponse.getString("NAME"));
																			}
																			if(myResponse.has("DATE_OF_BIRTH") && !myResponse.isNull("DATE_OF_BIRTH")){
																				person.setDateOfBirth(String.valueOf(myResponse.getInt("DATE_OF_BIRTH")));
																			}
																			if(myResponse.has("GENDER") && !myResponse.isNull("GENDER")){
																				person.setGender(myResponse.getString("GENDER"));
																			}
																			if(myResponse.has("ID") && !myResponse.isNull("ID")){
																				id = String.valueOf(myResponse.getInt("ID"));
																				person.setId(id);
																			}
																			if(myResponse.has("TYPE_") && !myResponse.isNull("TYPE_")){
																				person.setType_(myResponse.getString("TYPE_"));
																			}
																			if(myResponse.has("NATIONALITY_ID") && !myResponse.isNull("NATIONALITY_ID")){
																				person.setNationalityId(myResponse.getString("NATIONALITY_ID"));
																			}
																			
																			if(StringUtils.isNotEmpty(id)){
																				List<NicTransactionAttachment> nicAttch = nicTransactionAttachmentService.findFacialTEById(jobDBO.getTransactionId(), "PH_TE", id);
																				if(nicAttch != null && nicAttch.size() > 0){
																					NicTransactionAttachment photo = nicAttch.get(0);
																					person.setPictureStr(Base64.encodeBase64String(photo.getDocData()));
																				}
																			}
																			
																			lstPerson.add(person);
																		}
																    }
																	else
																    {
																		EppPersonDto person = new EppPersonDto();
																		JSONObject myResponse = (JSONObject) item;
																		String id = "";
																		if(myResponse.has("PLACE_OF_BIRTH_ID") && !myResponse.isNull("PLACE_OF_BIRTH_ID")){
																			person.setPlaceOfBirthId(myResponse.getString("PLACE_OF_BIRTH_ID"));
																		}
																		if(myResponse.has("SEARCH_NAME") && !myResponse.isNull("SEARCH_NAME")){
																			person.setSearchName(myResponse.getString("SEARCH_NAME"));
																		}
																		if(myResponse.has("NAME") && !myResponse.isNull("NAME")){
																			person.setName(myResponse.getString("NAME"));
																		}
																		if(myResponse.has("DATE_OF_BIRTH") && !myResponse.isNull("DATE_OF_BIRTH")){
																			person.setDateOfBirth(String.valueOf(myResponse.getInt("DATE_OF_BIRTH")));
																		}
																		if(myResponse.has("GENDER") && !myResponse.isNull("GENDER")){
																			person.setGender(myResponse.getString("GENDER"));
																		}
																		if(myResponse.has("ID") && !myResponse.isNull("ID")){
																			id = String.valueOf(myResponse.getInt("ID"));
																			person.setId(id);
																		}
																		if(myResponse.has("TYPE_") && !myResponse.isNull("TYPE_")){
																			person.setType_(myResponse.getString("TYPE_"));
																		}
																		if(myResponse.has("NATIONALITY_ID") && !myResponse.isNull("NATIONALITY_ID")){
																			person.setNationalityId(myResponse.getString("NATIONALITY_ID"));
																		}
																		
																		if(StringUtils.isNotEmpty(id)){
																			List<NicTransactionAttachment> nicAttch = nicTransactionAttachmentService.findFacialTEById(jobDBO.getTransactionId(), "PH_TE", id);
																			if(nicAttch != null && nicAttch.size() > 0){
																				NicTransactionAttachment photo = nicAttch.get(0);
																				person.setPictureStr(Base64.encodeBase64String(photo.getDocData()));
																			}
																		}
																		
																		lstPerson.add(person);
																    }
																}catch(Exception e){
																	
																}
															}
														}
													}
													
													
												}
												
												String xmlFamily = "";
												JAXBContext jaxbContext = JAXBContext.newInstance(EppBufFamilyDto.class, EppPersonDto.class);
												Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
												StringWriter sw = new StringWriter();
												jaxbMarshaller.marshal(family, sw);
												xmlFamily = sw.toString();
												
												String status = "Đang xử lý";
												NicDocumentData docData = null;
												Collection<NicDocumentData> docData_ = documentDataService
														.findByTransactionId(transactionId_BMS);
												if(docData_ != null && docData_.size() > 0){
													List<NicDocumentData> nicDocs_ = new ArrayList<NicDocumentData>(
															docData_);
													docData = new NicDocumentData();
													docData = nicDocs_.get(0);
													if(docData == null && jobDBO.getInvestigationStatus().equals(NicUploadJob.RECORD_STATUS_APPROVE_PERSO)){
														status = "Đã phê duyệt. Chờ in";
													}
													else if(docData != null){
														if(docData.getStatus().equals(NicDocumentData.DOCUMENT_DATA_STATUS_ISSUANCE) && jobDBO.getNicTransaction().getTransactionStatus().equals(NicTransaction.TRANSACTION_STATUS_RIC_ISSUED)){
															status = "Đã trả hộ chiếu cho công dân";
														}
														else {
															status = "Đã in. Chờ trả hộ chiếu";
														}
													}
												}
												
												EppBufInfoDocDto info = new EppBufInfoDocDto();
												info.setTransactionId(jobDBO.getTransactionId());
												info.setRecieptNo(jobDBO.getReceiptNo());
												if(jobDBO.getNicTransaction().getTransactionType().equals("REP"))
													info.setTypeTransaction("Thay thế");
												else if(jobDBO.getNicTransaction().getTransactionType().equals("REP_LOS"))
													info.setTypeTransaction("Thay thế do mất hỏng");
												else
													info.setTypeTransaction("Cấp mới");
												
												SiteRepository siteR = siteService.getSiteRepoById(jobDBO.getNicTransaction().getRegSiteCode());
												if(siteR != null)
													info.setRegSiteCode(siteR.getSiteName());
												else
													info.setRegSiteCode(jobDBO.getNicTransaction().getRegSiteCode());
												
												info.setStatus(status);
												
												List<NicTransactionPackage> nicP = nicTransactionPackageService.getPackageNameByTransactionId(jobDBO.getTransactionId());
												NicTransactionPackage lastPack = null;
												if(nicP != null && nicP.size() > 0){
													lastPack = new NicTransactionPackage();
													long bigId = 0;
													for(NicTransactionPackage pack : nicP){
														if(pack.getTypeList() == 8 && bigId < pack.getId()){
															bigId = pack.getId();
														}
													}
												}
												info.setRemarkApprove(lastPack.getNoteLeaderApprove());
												info.setNote(lastPack.getNoteApprove());
												
												String nameApprover = "";
												String levelApprover = "";
												if(StringUtils.isNotEmpty(jobDBO.getNicTransaction().getLeaderOfficerId())){
													Users user = userService.findById(jobDBO.getNicTransaction().getLeaderOfficerId());
													if(user != null){
														nameApprover = user.getUserName();
														levelApprover = user.getPosition();
													}
													else{
														nameApprover = jobDBO.getNicTransaction().getLeaderOfficerId();
													}
												}
												info.setNameApprover(nameApprover);
												info.setLevelApprover(levelApprover);
												
												if(docData != null){
													info.setPassportNo(docData.getId().getPassportNo());
													info.setIssuePassportDate(docData.getDateOfIssue());
													info.setReleaseDate(docData.getIssueDatetime());
												}
												info.setReceiver(jobDBO.getNicTransaction().getReceiver());
												
												String xmlInfo = "";
												JAXBContext jaxbContextInfo = JAXBContext.newInstance(EppBufInfoDocDto.class);
												Marshaller jaxbMarshallerInfo = jaxbContextInfo.createMarshaller();
												StringWriter swI = new StringWriter();
												jaxbMarshallerInfo.marshal(info, swI);
												xmlInfo = swI.toString();
												
												//Lưu lại dữ liệu History
												//logger.info("[{}]SAVE HISTORY =================", transactionId_BMS);
												BufEppPersonInvestigation objBuf = bufPersonInvestigationService.findByTranId(transactionId_BMS);
												if(objBuf == null){
													objBuf = new BufEppPersonInvestigation();
													objBuf.setCreateDatetime(new Date());
													objBuf.setCreateBy("SYSTEM");
													objBuf.setCreateWkstnId("SYSTEM-MACHINE");
												}
												else
												{
													objBuf.setUpdateBy("SYSTEM");
													objBuf.setUpdateWkstnId("SYSTEM-MACHINE");
													objBuf.setUpdateDatetime(new Date());
												}
												objBuf.setTransactionId(transactionId_BMS);
												objBuf.setDataHistoryPassport(xmlIssue);
												objBuf.setDataImmihistory(xmlImmi);
												objBuf.setDataFamily(xmlFamily);
												objBuf.setDataInfoDocument(xmlInfo);
												bufPersonInvestigationService.saveOrUpdate(objBuf);
											}
											else
											{
												//logger.info("Not found information Transaction in database TransactionId: " + transactionId_BMS);
											}
										}	
									}
								}
							}
						}
						else
						{
							//logger.info("Not found list hit BMS by idBMS: " + idBMS);
						}
					}
				} catch (Exception e) {
					//logger.error("Exception in finally block (CHECKING HISTORY.doSomething)", e);
				}
			}
			
		} catch (Exception e) {
			log("Exception: " + e.getMessage());
			// TODO: handle exception
		}*/
		/*try {

			String urlS = "http://192.168.1.222:8880/eppws/services/rest/syncNic/getTransactionById";
			URL url = new URL(urlS);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setDoInput(true);
			conn.setDoOutput(true);
			conn.setRequestMethod("POST");
			conn.setRequestProperty("Content-Type",
					"application/json; charset=UTF-8");
			
			String input = "{\"transactionId\":\"HP1190808008001\", \"passportNo\":\"\"}";

			OutputStreamWriter writer = new OutputStreamWriter(
					conn.getOutputStream(), "UTF-8");
			writer.write(input);
			writer.close();

			if (conn.getResponseCode() != HttpURLConnection.HTTP_CREATED && conn.getResponseCode() != HttpURLConnection.HTTP_OK) {
				throw new RuntimeException("Failed : HTTP error code : "
					+ conn.getResponseCode());
			}

			BufferedReader br = new BufferedReader(new InputStreamReader(
					(conn.getInputStream())));

			String output;
			System.out.println("Output from Server .... \n");
			while ((output = br.readLine()) != null) {
				ObjectMapper mapper = new ObjectMapper();
				//ResponseBase<List<TransactionSync>> res = mapper.readValue(output, ResponseBase.class);
				System.out.println(output);
			}

			conn.disconnect();

		  } catch (MalformedURLException e) {

			e.printStackTrace();

		  } catch (IOException e) {

			e.printStackTrace();

		 }*/
		/*try {
			NicRegistrationData nicRegistrationData = nicRegistrationDataService.findById("PA_HP1190828004004");
			String onlyY = new java.sql.Date(nicRegistrationData
					.getDateOfBirth().getTime()).toString();
			String split[] = onlyY.split("-");
			String year = split[0];
			String month = split[1];
			String day = split[2];
			
			List<NicUploadJobDto> listNic = nicTransactionService.listPackageIDPerso("MB", "");
			if(listNic != null && listNic.size() > 0){
				for(NicUploadJobDto dto : listNic){
					NicListHandover nicHandover = listHandoverService.findByPackageId(dto.getPackageId());
					if(nicHandover != null){
						
					}
				}
			}
			
			
			//Lấy danh sách gói đồng bộ
			List<HandoverSync> lstHan = new ArrayList<HandoverSync>();
			String datefrom = "22-08-2019";
			List<CountPassport> handoverA = listHandoverService.countHandoverA(datefrom);
			List<CountPassport> handoverAProcess = listHandoverService.countHandoverAProcess(datefrom);
			if(handoverAProcess != null && handoverAProcess.size() > 0){
				for(CountPassport cap : handoverAProcess){
					for(CountPassport ca : handoverA){
						if(cap.getRegSite().equals(ca.getRegSite()) && cap.getTotal().equals(ca.getTotal())){
							HandoverSync han = new HandoverSync();
							List<String> lstTran = new ArrayList<String>();
							han.setSize(ca.getTotal());
							han.setPackID(ca.getRegSite());
							List<String> listTran = listHandoverService.listTransactionA(ca.getRegSite());
							for(String tran : listTran){
								lstTran.add(tran);
							}
							han.setListTran(lstTran);
							lstHan.add(han);
							break;
						}
					}
				}
			}
			
			String tranId = "TUNT-2019-009211";
			NicUploadJob nicUp = nicUploadJobService.getUploadJobByTransactionIdSinger(null, tranId);
			List<NicTransactionAttachment> attachment = nicTransactionAttachmentService.findNicTransactionAttachments(tranId, null, null);
			TransactionSync tran = new TransactionSync();
			if(nicUp != null){
				tran.setNicUp(nicUp);
			}
			
			if(attachment != null && attachment.size() > 0){
				tran.setListattachment(attachment);
			}
			
			String xmlFamily = "";
			JAXBContext jaxbContext = JAXBContext.newInstance(TransactionSync.class, NicUploadJob.class, NicTransactionAttachment.class, NicListHandover.class);
			Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
			StringWriter sw = new StringWriter();
			jaxbMarshaller.marshal(tran, sw);
			xmlFamily = sw.toString();
			
		} catch (Exception e) {
			log("Exception: " + e.getMessage());
			// TODO: handle exception
		}*/
		
		
		/*try{
			Long idBMS = 2362l;
			NicUploadJob jobDBO = nicUploadJobService.findById(44044L);
			List<String> listTranCPD = new ArrayList<String>();
			List<String> listTranBMS = new ArrayList<String>();
			List<InvestigationListInfoTargetDto> outputR = new ArrayList<InvestigationListInfoTargetDto>();
			String statusTransaction = "Đã phát hành";
			String statusDocumentData = "Đang hoạt động";
			Boolean statusDocumentDataLock = false;
			List<ImmiHistory> listImmi = new ArrayList<ImmiHistory>();
			InvestigationListInfoTargetsDto lstR = new InvestigationListInfoTargetsDto();
			
			//Lấy dữ liệu trùng trong bảng EPP_SEARCH_HIT_RESULT
			List<NicSearchHitResult> listHitBMS = nicSearchHitResultService.findHitPositions(null, idBMS);
			if(listHitBMS != null && listHitBMS.size() > 0){
				for(NicSearchHitResult sHR : listHitBMS){
					String passportNo = "";
					if(sHR.getDataSource().equals(NicSearchHitResult.hitInfo_DataSource_BMS)){
						//Lấy Id của đối tượng trùng
						String transactionId_BMS = "";
						transactionId_BMS = sHR.getTransactionIdHit();
						if(StringUtils.isNotEmpty(transactionId_BMS)){
							//Kiểm tra trong ListCheckCPD có trùng transactionID không. Nếu có thì chỉ thêm dữ liệu BMS vào vị trí đối tượng
							if(listTranCPD.contains(transactionId_BMS) && outputR != null && outputR.size() > 0){
								InvestigationListInfoTargetDto updateObj = outputR.get(listTranCPD.indexOf(transactionId_BMS));
								updateObj.setScoreBMSall_O(sHR.getHitInfo());
								int score = 0;
								//Tính số điểm trung bình cho vân tay
								//Kiểm tra chuỗi dữ liệu
								if(StringUtils.isNotEmpty(sHR.getHitInfo())){
									String[] listHit = sHR.getHitInfo().split(","); //Tách chuỗi theo dấu ","
									if(listHit.length > 0){
										for(int i = 0; i < listHit.length; i++){
										   if(Double.parseDouble((listHit[i].split("=")[1])) > score){
											   double d = Double.parseDouble((listHit[i].split("=")[1]));
											   score = (int) d;
										   }
										}
									}
								}
								updateObj.setScoreBMS_O(score);
								
								//Xóa dữ liệu cũ trong 2 list
								listTranCPD.remove(transactionId_BMS);
								outputR.remove(listTranCPD.indexOf(transactionId_BMS));
								
								//Cập nhật dữ liệu mới
								listTranCPD.add(transactionId_BMS);
								outputR.add(updateObj);
							}
							else{
								InvestigationListInfoTargetDto outPut = new InvestigationListInfoTargetDto();
								InvestigationListInfoTargetDto outPutP = null;
								outPut.setScoreBMSall_O(sHR.getHitInfo());
								int score = 0;
								//Tính số điểm trung bình cho vân tay
								//Kiểm tra chuỗi dữ liệu
								if(StringUtils.isNotEmpty(sHR.getHitInfo())){
									String[] listHit = sHR.getHitInfo().split(","); //Tách chuỗi theo dấu ","
									if(listHit.length > 0){
										for(int i = 0; i < listHit.length; i++){
										   if(Double.parseDouble((listHit[i].split("=")[1])) > score){
											   double d = Double.parseDouble((listHit[i].split("=")[1]));
										   	   score = (int) d;
										   }
										}
									}
								}
								outPut.setScoreBMS_O(score);
								
								//Lấy dữ liệu thông tin hồ sơ của transaction
								NicUploadJob dtUpJob = nicUploadJobService.getUploadJobByTransactionIdSinger(null, transactionId_BMS);
								NicTransaction nicTran = nicTransactionService.getNicTransactionById(transactionId_BMS);
								if(nicTran != null){
									//Kiểm tra trạng thái của hồ sơ
									if(!nicTran.getTransactionStatus().equals(NicTransaction.TRANSACTION_STATUS_RIC_ISSUED)){//TH chưa phát hành
										statusTransaction = "Đang xử lý - Giai đoạn: " + nicTran.getTransactionStatus();
									}
									else{
										//Thông tin hộ chiếu
										Collection<NicDocumentData> nicDocs = documentDataService.findByTransactionId(transactionId_BMS);
										if(nicDocs != null && nicDocs.size() > 0){
											List<NicDocumentData> nicDocs_ = new ArrayList<NicDocumentData>(nicDocs);
											NicDocumentData nicDoc = nicDocs_.get(0);
											outPutP = new InvestigationListInfoTargetDto();
											if(nicDoc.getStatus().equals(NicDocumentData.DOCUMENT_DATA_STATUS_NONE)){
												statusDocumentData = "Đã khóa";
												statusDocumentDataLock = true;
												passportNo = nicDoc.getId().getPassportNo();
											}
											else if(nicDoc.getStatus().equals(NicDocumentData.DOCUMENT_DATA_STATUS_PERSONALIZED))
											{
												statusDocumentData = "Cá thể hóa";
											}
											
											outPutP.setPackageId_O(nicTran.getPackageID());
											outPutP.setTransactionId_O(transactionId_BMS);
											outPutP.setTypeTransaction_O(nicTran.getTransactionType());
											outPutP.setReg_O(nicTran.getRegSiteCode());
											outPutP.setStatus_O(statusDocumentData);
											outPutP.setPassportNo_O(nicDoc.getId().getPassportNo());
											
											String issuePP = "";
											if(nicDoc.getDateOfIssue() != null){
												String pattern = "dd/MM/yyyy";
												DateFormat df = new SimpleDateFormat(pattern);
												issuePP = df.format(nicDoc.getDateOfIssue());
											}
											outPutP.setIssueDatePassport_O(issuePP);
											
											String receivePP = "";
											if(nicDoc.getReceiveDatetime() != null){
												String pattern = "dd/MM/yyyy";
												DateFormat df = new SimpleDateFormat(pattern);
												receivePP = df.format(nicDoc.getReceiveDatetime());
											}
											outPutP.setPayDatePassport_O(receivePP);
											outPutP.setPayPlacePassport_O(nicTran.getIssSiteCode());
											outPutP.setPersonRecieve_O("");
											outPutP.setRemark_O("");
											
										}
										else{
											//logger.info("Not found information Document data in database TransactionId: " + transactionId_BMS);
										}
									}
	
									//Thông tin cá nhân
									NicRegistrationData nicRegis = nicTran.getNicRegistrationData();
									//logger.info("[" + nicRegis.getTransactionId() + "]nicRegis: " + nicRegis.getSurnameLine1());
									if(nicRegis != null){
										
										outPut.setFullName_O(nicRegis.getSurnameLine1());
										//outPut.setGender_O(nicRegis.getGender());
										if(nicRegis.getGender() != null){
											outPut.setGender_O(nicRegis.getGender().equals("M") ? "Nam" : "Nữ");
										}else {
											outPut.setGender_O("Không");
										}
										String dob = "";
										if(nicRegis.getDateOfBirth() != null){
											String pattern = "dd/MM/yyyy";
											DateFormat df = new SimpleDateFormat(pattern);
											dob = df.format(nicRegis.getDateOfBirth());
										}
										outPut.setDob_O(dob);
										outPut.setNin_O(nicTran.getNin());
										outPut.setPob_O(nicRegis.getPlaceOfBirth());
										outPut.setReligion_O(nicRegis.getReligion());
										outPut.setNation_O(nicRegis.getNation());
										outPut.setPhone_O(nicRegis.getContactNo());
										
										//Xử lý ảnh đối tượng 
										List<NicTransactionAttachment> photoList = nicTransactionAttachmentService
												.findNicTransactionAttachments(
														transactionId_BMS,
														NicTransactionAttachment.DOC_TYPE_PHOTO_CAPTURE,
														NicTransactionAttachment.DEFAULT_SERIAL_NO);
										if (CollectionUtils.isNotEmpty(photoList)
												&& photoList.size() > 0) {
											String photoStr = Base64.encodeBase64String(photoList.get(0)
													.getDocData());
											outPut.setPhoto_O(photoStr);
										}
										
										//Thông tin người thân
										outPut.setFullNameFather_O(nicRegis.getFatherSurname() + " " + nicRegis.getFatherFirstname());
										String fatherDob = "";
										if(nicRegis.getFatherDob() != null){
											String pattern = "dd/MM/yyyy";
											DateFormat df = new SimpleDateFormat(pattern);
											fatherDob = df.format(nicRegis.getFatherDob());
										}
										outPut.setDobFather_O(fatherDob);
										
										outPut.setFullNameMother_O(nicRegis.getMotherSurname() + " " + nicRegis.getMotherFirstname());
										String motherDob = "";
										if(nicRegis.getMotherDob() != null){
											String pattern = "dd/MM/yyyy";
											DateFormat df = new SimpleDateFormat(pattern);
											motherDob = df.format(nicRegis.getMotherDob());
										}
										outPut.setDobMother_O(motherDob);
										
										String spouserDob = "";
										if(nicRegis.getSpouseDob()!= null){
											String pattern = "dd/MM/yyyy";
											DateFormat df = new SimpleDateFormat(pattern);
											spouserDob = df.format(nicRegis.getSpouseDob());
										}
										outPut.setDobSpouser_O(spouserDob);
									}
									outputR.add(outPut);
									
									
									else
									{
										logger.info("Not found information Registration data in database TransactionId: " + transactionId_BMS);
									}
									
									//Kiểm tra danh sách giấy tờ mất/hủy
									//Tìm kiếm trong danh sách TRANSACTION với kiểu LOS (chỉ tìm với hộ chiếu khóa RIC_DEACTIVATED)
									List<NicTransaction> listLost = new ArrayList<NicTransaction>();
									if(statusDocumentDataLock && nicTran.getTransactionStatus().equals(NicTransaction.TRANSACTION_STATUS_RIC_DEACTIVATED)){
										//Kiểm tra qua số hộ chiếu
										NicTransaction nicTranLost_PPno = nicTransactionService.getNicTransactionByPrevPPno(passportNo, "", "");
										if(nicTranLost_PPno != null)
											listLost.add(nicTranLost_PPno);
										
										//Kiểm tra qua CMND / CCCD (tìm bản ghi đầu tiên)
										NicTransaction nicTranLost_nin = nicTransactionService.getNicTransactionByPrevPPno("", "", nicTran.getNin());
										if(nicTranLost_nin != null)
											listLost.add(nicTranLost_nin);
									}
									if(listLost != null && listLost.size() > 0)
										outputR.setListLost(listLost);
									
									//Kiểm tra đối với dữ liệu lịch sử XNC của đối tượng
									List<ImmiHistory> listImmi = new ArrayList<ImmiHistory>();
									listImmi = nicImmiHistoryService.findByNinPPno(nicTran.getNin(), passportNo);
									if(listImmi != null && listImmi.size() > 0)
										outputR.setListImmi(listImmi);
									
									
									//Kiểm tra đối với dữ liệu lịch sử XNC của đối tượng
									listImmi = nicImmiHistoryService.findByNinPPno(nicTran.getNin(), passportNo);
									listTranBMS.add(transactionId_BMS);
									
									//Đóng gói dữ liệu History
									String xmlImmi = "";
									String xmlIssue = "";
									//logger.info("[{}]XML data", jobDBO.getTransactionId());
									if(outPutP != null){
										//lstR.setInfoTarget(outPutP);
										JAXBContext jaxbContext = JAXBContext.newInstance(InvestigationListInfoTargetsDto.class);
										Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
										StringWriter sw = new StringWriter();
										jaxbMarshaller.marshal(outPutP, sw);
										xmlIssue = sw.toString();
									}
									
									if(listImmi != null && listImmi.size() > 0){
										ImmiHistorys listImmis = new ImmiHistorys();
										listImmis.setImmiHistory(listImmi);
										JAXBContext jaxbContext = JAXBContext.newInstance(ImmiHistorys.class);
										Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
										StringWriter sw = new StringWriter();
										jaxbMarshaller.marshal(listImmis, sw);
										xmlImmi = sw.toString();
									}
									
									EppBufFamilyDto family = new EppBufFamilyDto();
									List<EppPersonDto> lstPerson = new ArrayList<EppPersonDto>();
									family.setFatherSurname(dtUpJob.getNicTransaction().getNicRegistrationData().getFatherSurname());
									family.setMotherSurname(dtUpJob.getNicTransaction().getNicRegistrationData().getMotherSurname());
									family.setSpouseSurname(dtUpJob.getNicTransaction().getNicRegistrationData().getSpouseSurname());
									
									family.setFatherDob(dtUpJob.getNicTransaction().getNicRegistrationData().getFatherDob());
									family.setMotherDob(dtUpJob.getNicTransaction().getNicRegistrationData().getMotherDob());
									family.setSpouseDob(dtUpJob.getNicTransaction().getNicRegistrationData().getSpouseDob());
									
									String xmlPerson = dtUpJob.getNicTransaction().getInfoPerson();
									
									if(StringUtils.isNotEmpty(xmlPerson)){
										JSONObject xmlJSONObj = XML.toJSONObject(xmlPerson);
										if(xmlJSONObj != null){
											if(xmlJSONObj.has("EPP_PERSONS") && !xmlJSONObj.isNull("EPP_PERSONS")){
												JSONObject resp = xmlJSONObj.getJSONObject("EPP_PERSONS");
												if(resp.has("EPP_PERSON") && !resp.isNull("EPP_PERSON")){
													Object item = resp.get("EPP_PERSON");
													try{
														if (item instanceof JSONArray){
															JSONArray arrayResp = (JSONArray) item;
															for (int i = 0; i < arrayResp.length(); i++) {
																EppPersonDto person = new EppPersonDto();
																JSONObject myResponse = new JSONObject();
																myResponse = arrayResp.getJSONObject(i);
																String id = "";
																if(myResponse.has("PLACE_OF_BIRTH_ID") && !myResponse.isNull("PLACE_OF_BIRTH_ID")){
																	person.setPlaceOfBirthId(myResponse.getString("PLACE_OF_BIRTH_ID"));
																}
																if(myResponse.has("SEARCH_NAME") && !myResponse.isNull("SEARCH_NAME")){
																	person.setSearchName(myResponse.getString("SEARCH_NAME"));
																}
																if(myResponse.has("NAME") && !myResponse.isNull("NAME")){
																	person.setName(myResponse.getString("NAME"));
																}
																if(myResponse.has("DATE_OF_BIRTH") && !myResponse.isNull("DATE_OF_BIRTH")){
																	person.setDateOfBirth(String.valueOf(myResponse.getInt("DATE_OF_BIRTH")));
																}
																if(myResponse.has("GENDER") && !myResponse.isNull("GENDER")){
																	person.setGender(myResponse.getString("GENDER"));
																}
																if(myResponse.has("ID") && !myResponse.isNull("ID")){
																	id = String.valueOf(myResponse.getInt("ID"));
																	person.setId(id);
																}
																if(myResponse.has("TYPE_") && !myResponse.isNull("TYPE_")){
																	person.setType_(myResponse.getString("TYPE_"));
																}
																if(myResponse.has("NATIONALITY_ID") && !myResponse.isNull("NATIONALITY_ID")){
																	person.setNationalityId(myResponse.getString("NATIONALITY_ID"));
																}
																
																if(StringUtils.isNotEmpty(id)){
																	List<NicTransactionAttachment> nicAttch = nicTransactionAttachmentService.findFacialTEById(jobDBO.getTransactionId(), "PH_TE", id);
																	if(nicAttch != null && nicAttch.size() > 0){
																		NicTransactionAttachment photo = nicAttch.get(0);
																		person.setPictureStr(Base64.encodeBase64String(photo.getDocData()));
																	}
																}
																
																lstPerson.add(person);
															}
													    }
														else
													    {
															EppPersonDto person = new EppPersonDto();
															JSONObject myResponse = (JSONObject) item;
															String id = "";
															if(myResponse.has("PLACE_OF_BIRTH_ID") && !myResponse.isNull("PLACE_OF_BIRTH_ID")){
																person.setPlaceOfBirthId(myResponse.getString("PLACE_OF_BIRTH_ID"));
															}
															if(myResponse.has("SEARCH_NAME") && !myResponse.isNull("SEARCH_NAME")){
																person.setSearchName(myResponse.getString("SEARCH_NAME"));
															}
															if(myResponse.has("NAME") && !myResponse.isNull("NAME")){
																person.setName(myResponse.getString("NAME"));
															}
															if(myResponse.has("DATE_OF_BIRTH") && !myResponse.isNull("DATE_OF_BIRTH")){
																person.setDateOfBirth(String.valueOf(myResponse.getInt("DATE_OF_BIRTH")));
															}
															if(myResponse.has("GENDER") && !myResponse.isNull("GENDER")){
																person.setGender(myResponse.getString("GENDER"));
															}
															if(myResponse.has("ID") && !myResponse.isNull("ID")){
																id = String.valueOf(myResponse.getInt("ID"));
																person.setId(id);
															}
															if(myResponse.has("TYPE_") && !myResponse.isNull("TYPE_")){
																person.setType_(myResponse.getString("TYPE_"));
															}
															if(myResponse.has("NATIONALITY_ID") && !myResponse.isNull("NATIONALITY_ID")){
																person.setNationalityId(myResponse.getString("NATIONALITY_ID"));
															}
															
															if(StringUtils.isNotEmpty(id)){
																List<NicTransactionAttachment> nicAttch = nicTransactionAttachmentService.findFacialTEById(jobDBO.getTransactionId(), "PH_TE", id);
																if(nicAttch != null && nicAttch.size() > 0){
																	NicTransactionAttachment photo = nicAttch.get(0);
																	person.setPictureStr(Base64.encodeBase64String(photo.getDocData()));
																}
															}
															
															lstPerson.add(person);
													    }
													}catch(Exception e){
														
													}
												}
											}
										}
										
										
									}
									
									String xmlFamily = "";
									JAXBContext jaxbContext = JAXBContext.newInstance(EppBufFamilyDto.class, EppPersonDto.class);
									Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
									StringWriter sw = new StringWriter();
									jaxbMarshaller.marshal(family, sw);
									xmlFamily = sw.toString();
									
									String status = "Đang xử lý";
									NicDocumentData docData = documentDataService.getDocumentDataById(dtUpJob.getTransactionId());
									if(docData == null && jobDBO.getInvestigationStatus().equals(NicUploadJob.RECORD_STATUS_APPROVE_PERSO)){
										status = "Đã phê duyệt. Chờ in";
									}
									else if(docData != null){
										if(docData.getStatus().equals(NicDocumentData.DOCUMENT_DATA_STATUS_ISSUANCE) && dtUpJob.getNicTransaction().getTransactionStatus().equals(NicTransaction.TRANSACTION_STATUS_RIC_ISSUED)){
											status = "Đã trả hộ chiếu cho công dân";
										}
										else {
											status = "Đã in. Chờ trả hộ chiếu";
										}
									}
									
									EppBufInfoDocDto info = new EppBufInfoDocDto();
									info.setTransactionId(dtUpJob.getTransactionId());
									info.setRecieptNo(dtUpJob.getReceiptNo());
									if(dtUpJob.getNicTransaction().getTransactionType().equals("REP"))
										info.setTypeTransaction("Thay thế");
									else if(dtUpJob.getNicTransaction().getTransactionType().equals("REP_LOS"))
										info.setTypeTransaction("Thay thế do mất hỏng");
									else
										info.setTypeTransaction("Cấp mới");
									
									SiteRepository siteR = siteService.getSiteRepoById(dtUpJob.getNicTransaction().getRegSiteCode());
									if(siteR != null)
										info.setRegSiteCode(siteR.getSiteName());
									else
										info.setRegSiteCode(dtUpJob.getNicTransaction().getRegSiteCode());
									
									info.setStatus(status);
									
									List<NicTransactionPackage> nicP = nicTransactionPackageService.getPackageNameByTransactionId(dtUpJob.getTransactionId());
									String remarkApprove = "";
									if(nicP != null && nicP.size() > 0){
										for(NicTransactionPackage pack : nicP){
											if(pack.getTypeList() == 8 && pack.getStage().equals("D")){
												remarkApprove = pack.getNoteApprove();
												break;
											}
										}
									}
									info.setRemarkApprove(remarkApprove);
									
									String nameApprover = "";
									String levelApprover = "";
									if(StringUtils.isNotEmpty(dtUpJob.getNicTransaction().getLeaderOfficerId())){
										Users user = userService.findById(dtUpJob.getNicTransaction().getLeaderOfficerId());
										if(user != null){
											nameApprover = user.getUserName();
											levelApprover = user.getPosition();
										}
										else{
											nameApprover = dtUpJob.getNicTransaction().getLeaderOfficerId();
										}
									}
									info.setNameApprover(nameApprover);
									info.setLevelApprover(levelApprover);
									
									if(docData != null){
										info.setPassportNo(docData.getId().getPassportNo());
										info.setIssuePassportDate(docData.getDateOfIssue());
										info.setReleaseDate(docData.getIssueDatetime());
										
									}
									info.setReceiver(dtUpJob.getNicTransaction().getReceiver());
									
									String xmlInfo = "";
									JAXBContext jaxbContextInfo = JAXBContext.newInstance(EppBufInfoDocDto.class);
									Marshaller jaxbMarshallerInfo = jaxbContextInfo.createMarshaller();
									StringWriter swI = new StringWriter();
									jaxbMarshallerInfo.marshal(info, swI);
									xmlInfo = swI.toString();
									
									//Lưu lại dữ liệu History
									//logger.info("[{}]SAVE HISTORY =================", jobDBO.getTransactionId());
									BufEppPersonInvestigation objBuf = bufPersonInvestigationService.findByTranId(transactionId_BMS);
									if(objBuf == null){
										objBuf = new BufEppPersonInvestigation();
										objBuf.setCreateDatetime(new Date());
										objBuf.setCreateBy("SYSTEM");
										objBuf.setCreateWkstnId("SYSTEM-MACHINE");
									}
									else
									{
										objBuf.setUpdateBy("SYSTEM");
										objBuf.setUpdateWkstnId("SYSTEM-MACHINE");
										objBuf.setUpdateDatetime(new Date());
									}
									objBuf.setTransactionId(transactionId_BMS);
									objBuf.setDataHistoryPassport(xmlIssue);
									objBuf.setDataImmihistory(xmlImmi);
									objBuf.setDataFamily(xmlFamily);
									objBuf.setDataInfoDocument(xmlInfo);
									bufPersonInvestigationService.saveOrUpdate(objBuf);
								}
								else
								{
									//logger.info("Not found information Transaction in database TransactionId: " + transactionId_BMS);
								}
							}	
						}
					}
				}
			}
			else
			{
				//logger.info("Not found list hit BMS by idBMS: " + idBMS);
			}
		} catch (Exception e) {
			// TODO: handle exception
		}*/
		
		/*try {
			BufEppPersonInvestigation bufInves = new BufEppPersonInvestigation();
			com.nec.asia.nic.comp.trans.domain.BufEppPersonInvestigation bufInvesDomain = bufPersonInvestigationService.findByTranId("HP1190807006002");
			if(bufInvesDomain != null){
				bufInves.setDataHistoryPassport(bufInvesDomain.getDataHistoryPassport());
				bufInves.setCreateBy(bufInvesDomain.getCreateBy());
				bufInves.setCreateDatetime(new Date());
				bufInves.setDataImmihistory(bufInvesDomain.getDataImmihistory());
				bufInves.setDataInfoDocument(bufInvesDomain.getDataInfoDocument());
				bufInves.setDataFamily(bufInvesDomain.getDataFamily());
				bufInves.setTransactionId("HP1190807006002");
			}
		} catch (Exception e) {
			// TODO: handle exception
		}*/
	
		/*String pp = "TD0000020";
		String number = pp.replaceAll("\\D+","");
		String charA =  pp.replaceAll("[^A-Za-z]+", "");
		String ok = "";*/
		/*try {

		 EppInvestoryItemsDetail epp = eppInventoryItemsDetailService.findByCondition("TD", "0000020");
		 if(epp != null)
		 {
			 epp.setStatus("D");
			 epp.setUpdateTs(new Date());
			 eppInventoryItemsDetailService.saveOrUpdate(epp);
		 }
			  
		} catch (Exception e) {
			// TODO: handle exception
		}*/
		
		/*String time = "26-08-2019_14-32-16";
		String splitDate = time.split("_")[0];
		String splitTime = time.split("_")[1].replace("-", ":");
		//Kiểm tra cấu trúc time
		DateFormat format = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss", Locale.ENGLISH);
		try {
			Date date = format.parse(splitDate + " " + splitTime);
			String a = "";
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
		/*List<RptStatisticsTransmitData> lstRptKQ;
		try {
			lstRptKQ = rptStatisticsTransmitDataService.findBySiteCode("A08");
			if(lstRptKQ != null && lstRptKQ.size() > 0){
				RptStatisticsTransmitData data = lstRptKQ.get(0);
				int num = data.getTotal();
				data.setTotal(num + 1);
				data.setUpdateDatetime(new Date());
				rptStatisticsTransmitDataService.saveOrUpdateData(data);
			}
			else
			{
				RptStatisticsTransmitData data = new RptStatisticsTransmitData();
				data.setRptDate(new Date());
				data.setSiteCode("A08");
				data.setTotal(1);
				data.setType("TRA_KQTC");
				data.setUpdateDatetime(new Date());
				rptStatisticsTransmitDataService.saveOrUpdateData(data);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
		
		/*try{
			String statusDocumentData = "Đang hoạt động";
			String statusTransaction = "Đã phát hành";
			Boolean statusDocumentDataLock = false;
			Long idCPD = null;
			List<String> listTranCPD = new ArrayList<String>();
			List<String> listTranBMS = new ArrayList<String>();
			InvestigationListInfoTargetsDto lstR = new InvestigationListInfoTargetsDto();
			List<InvestigationListInfoTargetDto> outputR = new ArrayList<InvestigationListInfoTargetDto>();
			List<ImmiHistory> listImmi = new ArrayList<ImmiHistory>();
			NicUploadJob jobDBO = nicUploadJobService.findById(43962l);
			
			List<NicSearchResult> searchResults = nicSearchResultService.findAllByJobId(jobDBO.getWorkflowJobId());
			if(searchResults != null && searchResults.size() > 0){
				for(NicSearchResult sR_ : searchResults){
					if(sR_.getTypeSearch().equals(NicSearchResult.TYPE_SEARCH__CPD) && sR_.getHasHit()){
						idCPD = sR_.getSearchResultId();
					}
				}
			}
			List<NicSearchHitResult> listHitCPD = nicSearchHitResultService.findHitPositions(null, idCPD);
			if(listHitCPD != null && listHitCPD.size() > 0){
				for(NicSearchHitResult sHR : listHitCPD){
					String passportNo = "";
					InvestigationListInfoTargetDto outPut = new InvestigationListInfoTargetDto();
					
					if(sHR.getDataSource().equals(NicSearchHitResult.hitInfo_DataSource_CPD)){
						//Lấy Id của đối tượng trùng
						String transactionId_CPD = sHR.getTransactionIdHit();
						transactionId_CPD = "HP1190814019129";
						if(StringUtils.isNotEmpty(transactionId_CPD)){
							//Lấy dữ liệu thông tin hồ sơ của transaction
							NicTransaction nicTran = nicTransactionService.getNicTransactionById(transactionId_CPD);
							if(nicTran != null){
								//Kiểm tra trạng thái của hồ sơ
								if(!nicTran.getTransactionStatus().equals(NicTransaction.TRANSACTION_STATUS_RIC_ISSUED)){//TH chưa phát hành
									statusTransaction = "Đang xử lý - Giai đoạn: " + nicTran.getTransactionStatus();
								}
								else {
									//Thông tin hộ chiếu
									Collection<NicDocumentData> nicDocs = documentDataService.findByTransactionId(transactionId_CPD);
									if(nicDocs != null && nicDocs.size() > 0){
										List<NicDocumentData> nicDocs_ = new ArrayList<NicDocumentData>(nicDocs);
										NicDocumentData nicDoc = nicDocs_.get(0);
										passportNo = nicDoc.getId().getPassportNo();
										if(nicDoc.getStatus().equals(NicDocumentData.DOCUMENT_DATA_STATUS_NONE)){
											statusDocumentData = "Đã khóa";
											statusDocumentDataLock = true;
										}
										else if(nicDoc.getStatus().equals(NicDocumentData.DOCUMENT_DATA_STATUS_PERSONALIZED))
										{
											statusDocumentData = "Cá thể hóa";
										}
										
										outPut.setPackageId_O(nicTran.getPackageID());
										outPut.setTransactionId_O(transactionId_CPD);
										outPut.setTypeTransaction_O(nicTran.getTransactionType());
										outPut.setReg_O(nicTran.getRegSiteCode());
										outPut.setStatus_O(statusDocumentData);
										outPut.setPassportNo_O(nicDoc.getId().getPassportNo());
										
										String issuePP = "";
										if(nicDoc.getDateOfIssue() != null){
											String pattern = "dd/MM/yyyy";
											DateFormat df = new SimpleDateFormat(pattern);
											issuePP = df.format(nicDoc.getDateOfIssue());
										}
										outPut.setIssueDatePassport_O(issuePP);
										
										String receivePP = "";
										if(nicDoc.getReceiveDatetime() != null){
											String pattern = "dd/MM/yyyy";
											DateFormat df = new SimpleDateFormat(pattern);
											receivePP = df.format(nicDoc.getReceiveDatetime());
										}
										outPut.setPayDatePassport_O(receivePP);
										outPut.setPayPlacePassport_O(nicTran.getIssSiteCode());
										outPut.setPersonRecieve_O("");
										outPut.setRemark_O("");
	
									}
									else{
										//logger.info("Not found information Document data in database TransactionId: " + transactionId_CPD);
									}
										//Thông tin cá nhân
										NicRegistrationData nicRegis = nicTran.getNicRegistrationData();
										//logger.info("[" + nicRegis.getTransactionId() + "]nicRegis: " + nicRegis.getSurnameLine1());
										if(nicRegis != null){
											outPut.setFullName_O(nicRegis.getSurnameLine1());
											//outPut.setGender_O(nicRegis.getGender());
											if(nicRegis.getGender() != null){
												outPut.setGender_O(nicRegis.getGender().equals("M") ? "Nam" : "Nữ");
											}else {
												outPut.setGender_O("Không");
											}
											String dob = "";
											if(nicRegis.getDateOfBirth() != null){
												String pattern = "dd/MM/yyyy";
												DateFormat df = new SimpleDateFormat(pattern);
												dob = df.format(nicRegis.getDateOfBirth());
											}
											outPut.setDob_O(dob);
											outPut.setNin_O(nicTran.getNin());
											outPut.setPob_O(nicRegis.getPlaceOfBirth());
											outPut.setReligion_O(nicRegis.getReligion());
											outPut.setNation_O(nicRegis.getNation());
											outPut.setPhone_O(nicRegis.getContactNo());
											outPut.setTransactionId_O(transactionId_CPD);
											//Xử lý ảnh đối tượng 
											List<NicTransactionAttachment> photoList = nicTransactionAttachmentService
													.findNicTransactionAttachments(
															transactionId_CPD,
															NicTransactionAttachment.DOC_TYPE_PHOTO_CAPTURE,
															NicTransactionAttachment.DEFAULT_SERIAL_NO);
											if (CollectionUtils.isNotEmpty(photoList)
													&& photoList.size() > 0) {
												String photoStr = Base64.encodeBase64String(photoList.get(0)
														.getDocData());
												outPut.setPhoto_O(photoStr);
											}
											
											//Thông tin người thân
											outPut.setFullNameFather_O(nicRegis.getFatherSurname() + " " + nicRegis.getFatherFirstname());
											String fatherDob = "";
											if(nicRegis.getFatherDob() != null){
												String pattern = "dd/MM/yyyy";
												DateFormat df = new SimpleDateFormat(pattern);
												fatherDob = df.format(nicRegis.getFatherDob());
											}
											outPut.setDobFather_O(fatherDob);
											
											outPut.setFullNameMother_O(nicRegis.getMotherSurname() + " " + nicRegis.getMotherFirstname());
											String motherDob = "";
											if(nicRegis.getMotherDob() != null){
												String pattern = "dd/MM/yyyy";
												DateFormat df = new SimpleDateFormat(pattern);
												motherDob = df.format(nicRegis.getMotherDob());
											}
											outPut.setDobMother_O(motherDob);
											
											String spouserDob = "";
											if(nicRegis.getSpouseDob()!= null){
												String pattern = "dd/MM/yyyy";
												DateFormat df = new SimpleDateFormat(pattern);
												spouserDob = df.format(nicRegis.getSpouseDob());
											}
											outPut.setDobSpouser_O(spouserDob);
										}
										outputR.add(outPut);
								}
								}
								else
								{
									//logger.info("Not found information Registration data in database TransactionId: " + transactionId_CPD);
								}
								
								//Kiểm tra đối với dữ liệu lịch sử XNC của đối tượng
								listImmi = nicImmiHistoryService.findByNinPPno(nicTran.getNin(), passportNo);
								
								//listTranCPD.add(transactionId_CPD);
								//Đóng gói dữ liệu History
								String xmlImmi = "";
								String xmlIssue = "";
								//logger.info("[{}]XML data", jobDBO.getTransactionId());
								if(outputR != null && outputR.size() > 0){
									lstR.setInfoTarget(outputR);
									JAXBContext jaxbContext = JAXBContext.newInstance(InvestigationListInfoTargetsDto.class);
									Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
									StringWriter sw = new StringWriter();
									jaxbMarshaller.marshal(lstR, sw);
									xmlIssue = sw.toString();
								}
								
								if(listImmi != null && listImmi.size() > 0){
									ImmiHistorys listImmis = new ImmiHistorys();
									listImmis.setImmiHistory(listImmi);
									JAXBContext jaxbContext = JAXBContext.newInstance(ImmiHistorys.class);
									Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
									StringWriter sw = new StringWriter();
									jaxbMarshaller.marshal(listImmis, sw);
									xmlImmi = sw.toString();
								}
								
								EppBufFamilyDto family = new EppBufFamilyDto();
								List<EppPersonDto> lstPerson = new ArrayList<EppPersonDto>();
								family.setFatherSurname(jobDBO.getNicTransaction().getNicRegistrationData().getFatherSurname());
								family.setMotherSurname(jobDBO.getNicTransaction().getNicRegistrationData().getMotherSurname());
								family.setSpouseSurname(jobDBO.getNicTransaction().getNicRegistrationData().getSpouseSurname());
								
								family.setFatherDob(jobDBO.getNicTransaction().getNicRegistrationData().getFatherDob());
								family.setMotherDob(jobDBO.getNicTransaction().getNicRegistrationData().getMotherDob());
								family.setSpouseDob(jobDBO.getNicTransaction().getNicRegistrationData().getSpouseDob());
								
								String xmlPerson = jobDBO.getNicTransaction().getInfoPerson();
								
								if(StringUtils.isNotEmpty(xmlPerson)){
									JSONObject xmlJSONObj = XML.toJSONObject(xmlPerson);
									if(xmlJSONObj != null){
										if(xmlJSONObj.has("EPP_PERSONS") && !xmlJSONObj.isNull("EPP_PERSONS")){
											JSONObject resp = xmlJSONObj.getJSONObject("EPP_PERSONS");
											if(resp.has("EPP_PERSON") && !resp.isNull("EPP_PERSON")){
												Object item = resp.get("EPP_PERSON");
												try{
													if (item instanceof JSONArray){
														JSONArray arrayResp = (JSONArray) item;
														for (int i = 0; i < arrayResp.length(); i++) {
															EppPersonDto person = new EppPersonDto();
															JSONObject myResponse = new JSONObject();
															myResponse = arrayResp.getJSONObject(i);
															String id = "";
															if(myResponse.has("PLACE_OF_BIRTH_ID") && !myResponse.isNull("PLACE_OF_BIRTH_ID")){
																person.setPlaceOfBirthId(myResponse.getString("PLACE_OF_BIRTH_ID"));
															}
															if(myResponse.has("SEARCH_NAME") && !myResponse.isNull("SEARCH_NAME")){
																person.setSearchName(myResponse.getString("SEARCH_NAME"));
															}
															if(myResponse.has("NAME") && !myResponse.isNull("NAME")){
																person.setName(myResponse.getString("NAME"));
															}
															if(myResponse.has("DATE_OF_BIRTH") && !myResponse.isNull("DATE_OF_BIRTH")){
																person.setDateOfBirth(String.valueOf(myResponse.getInt("DATE_OF_BIRTH")));
															}
															if(myResponse.has("GENDER") && !myResponse.isNull("GENDER")){
																person.setGender(myResponse.getString("GENDER"));
															}
															if(myResponse.has("ID") && !myResponse.isNull("ID")){
																id = String.valueOf(myResponse.getInt("ID"));
																person.setId(id);
															}
															if(myResponse.has("TYPE_") && !myResponse.isNull("TYPE_")){
																person.setType_(myResponse.getString("TYPE_"));
															}
															if(myResponse.has("NATIONALITY_ID") && !myResponse.isNull("NATIONALITY_ID")){
																person.setNationalityId(myResponse.getString("NATIONALITY_ID"));
															}
															
															lstPerson.add(person);
														}
												    }
													else
												    {
														EppPersonDto person = new EppPersonDto();
														JSONObject myResponse = (JSONObject) item;
														String id = "";
														if(myResponse.has("PLACE_OF_BIRTH_ID") && !myResponse.isNull("PLACE_OF_BIRTH_ID")){
															person.setPlaceOfBirthId(myResponse.getString("PLACE_OF_BIRTH_ID"));
														}
														if(myResponse.has("SEARCH_NAME") && !myResponse.isNull("SEARCH_NAME")){
															person.setSearchName(myResponse.getString("SEARCH_NAME"));
														}
														if(myResponse.has("NAME") && !myResponse.isNull("NAME")){
															person.setName(myResponse.getString("NAME"));
														}
														if(myResponse.has("DATE_OF_BIRTH") && !myResponse.isNull("DATE_OF_BIRTH")){
															person.setDateOfBirth(String.valueOf(myResponse.getInt("DATE_OF_BIRTH")));
														}
														if(myResponse.has("GENDER") && !myResponse.isNull("GENDER")){
															person.setGender(myResponse.getString("GENDER"));
														}
														if(myResponse.has("ID") && !myResponse.isNull("ID")){
															id = String.valueOf(myResponse.getInt("ID"));
															person.setId(id);
														}
														if(myResponse.has("TYPE_") && !myResponse.isNull("TYPE_")){
															person.setType_(myResponse.getString("TYPE_"));
														}
														if(myResponse.has("NATIONALITY_ID") && !myResponse.isNull("NATIONALITY_ID")){
															person.setNationalityId(myResponse.getString("NATIONALITY_ID"));
														}
														
														lstPerson.add(person);
												    }
												}catch(Exception e){
													
												}
											}
										}
									}
								}
								
								String xmlFamily = "";
								JAXBContext jaxbContext = JAXBContext.newInstance(EppBufFamilyDto.class, EppPersonDto.class);
								Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
								StringWriter sw = new StringWriter();
								jaxbMarshaller.marshal(family, sw);
								xmlFamily = sw.toString();
								
								String status = "Đang xử lý";
								NicDocumentData docData = documentDataService.getDocumentDataById(jobDBO.getTransactionId());
								if(docData == null && jobDBO.getInvestigationStatus().equals(NicUploadJob.RECORD_STATUS_APPROVE_PERSO)){
									status = "Đã phê duyệt. Chờ in";
								}
								else if(docData != null){
									if(docData.getStatus().equals(NicDocumentData.DOCUMENT_DATA_STATUS_ISSUANCE) && jobDBO.getNicTransaction().getTransactionStatus().equals(NicTransaction.TRANSACTION_STATUS_RIC_ISSUED)){
										status = "Đã trả hộ chiếu cho công dân";
									}
									else {
										status = "Đã in. Chờ trả hộ chiếu";
									}
								}
								
								EppBufInfoDocDto info = new EppBufInfoDocDto();
								info.setTransactionId(jobDBO.getTransactionId());
								info.setRecieptNo(jobDBO.getReceiptNo());
								if(jobDBO.getNicTransaction().getTransactionType().equals("REP"))
									info.setTypeTransaction("Thay thế");
								else if(jobDBO.getNicTransaction().getTransactionType().equals("REP_LOS"))
									info.setTypeTransaction("Thay thế do mất hỏng");
								else
									info.setTypeTransaction("Cấp mới");
								
								SiteRepository siteR = siteService.getSiteRepoById(jobDBO.getNicTransaction().getRegSiteCode());
								if(siteR != null)
									info.setRegSiteCode(siteR.getSiteName());
								else
									info.setRegSiteCode(jobDBO.getNicTransaction().getRegSiteCode());
								
								info.setStatus(status);
								
								List<NicTransactionPackage> nicP = nicTransactionPackageService.getPackageNameByTransactionId(jobDBO.getTransactionId());
								String remarkApprove = "";
								if(nicP != null && nicP.size() > 0){
									for(NicTransactionPackage pack : nicP){
										if(pack.getTypeList() == 8 && pack.getStage().equals("D")){
											remarkApprove = pack.getNoteApprove();
											break;
										}
									}
								}
								info.setRemarkApprove(remarkApprove);
								
								String nameApprover = "";
								String levelApprover = "";
								if(StringUtils.isNotEmpty(jobDBO.getNicTransaction().getLeaderOfficerId())){
									Users user = userService.findById(jobDBO.getNicTransaction().getLeaderOfficerId());
									if(user != null){
										nameApprover = user.getUserName();
										levelApprover = user.getPosition();
									}
									else{
										nameApprover = jobDBO.getNicTransaction().getLeaderOfficerId();
									}
								}
								info.setNameApprover(nameApprover);
								info.setLevelApprover(levelApprover);
								
								if(docData != null){
									info.setPassportNo(docData.getId().getPassportNo());
									info.setIssuePassportDate(docData.getDateOfIssue());
									info.setReleaseDate(docData.getIssueDatetime());
									
								}
								info.setReceiver(jobDBO.getNicTransaction().getReceiver());
								
								String xmlInfo = "";
								JAXBContext jaxbContextInfo = JAXBContext.newInstance(EppBufInfoDocDto.class);
								Marshaller jaxbMarshallerInfo = jaxbContextInfo.createMarshaller();
								StringWriter swI = new StringWriter();
								jaxbMarshallerInfo.marshal(info, swI);
								xmlInfo = swI.toString();
								
								//Lưu lại dữ liệu History
								//logger.info("[{}]SAVE HISTORY =================", jobDBO.getTransactionId());
								
								BufEppPersonInvestigation objBuf = bufPersonInvestigationService.findByTranId(transactionId_CPD);
								if(objBuf == null){
									objBuf = new BufEppPersonInvestigation();
									objBuf.setCreateDatetime(new Date());
									objBuf.setCreateBy("SYSTEM");
									objBuf.setCreateWkstnId("SYSTEM-MACHINE");
								}
								else
								{
									objBuf.setUpdateBy("SYSTEM");
									objBuf.setUpdateWkstnId("SYSTEM-MACHINE");
									objBuf.setUpdateDatetime(new Date());
								}
								
								objBuf.setTransactionId(transactionId_CPD);
								objBuf.setDataHistoryPassport(xmlIssue);
								objBuf.setDataImmihistory(xmlImmi);
								objBuf.setDataFamily(xmlFamily);
								objBuf.setDataInfoDocument(xmlInfo);
								bufPersonInvestigationService.saveOrUpdate(objBuf);
								//outputR.add(outPut);
							}
							else
							{
								//logger.info("Not found information Transaction in database TransactionId: " + transactionId_CPD);
							}
						}	
					}
				}
		} catch (Exception e) {
			e.printStackTrace();
		}*/
	}
	
	/*public void testDao_updatePackageDispatchInfoByPassportNoList() {
		log("start updatePackageDispatchInfoByPassportNoList dao");

		try {
			List<String> passportNoList = Arrays.asList(new String[]{"AA0010003" , "EC0000002"}); 
			String packageId= "2000000010040";
			Date packageDateTime = DateUtil.strToDate("20160421", DateUtil.FORMAT_YYYYMMDD);
			String dispatchId= "DSP04201604250001";
			Date dispatchDateTime = DateUtil.strToDate("20160422", DateUtil.FORMAT_YYYYMMDD);
			String status= "ST";
			Date statusUpdateTime = DateUtil.strToDate("20160422", DateUtil.FORMAT_YYYYMMDD);
			String officerId= "EPP";
			String workstationId = "SYSTEM";
			
			documentDataDao.updatePackageDispatchInfoByPassportNoList(passportNoList, packageId, packageDateTime, dispatchId, dispatchDateTime, status, statusUpdateTime, officerId, workstationId);
			BufEppPersonInvestigation objBuf = bufPersonInvestigationDao.findByTranId("RIC01-2019-009171");
			
			if(objBuf == null){
				objBuf = new BufEppPersonInvestigation();
				objBuf.setCreateDatetime(new Date());
				objBuf.setCreateBy("SYSTEM");
				objBuf.setCreateWkstnId("SYSTEM-MACHINE");
			}
			else
			{
				objBuf.setUpdateBy("SYSTEM");
				objBuf.setUpdateWkstnId("SYSTEM-MACHINE");
				objBuf.setUpdateDatetime(new Date());
			}
			
			objBuf.setTransactionId("TESING");
			objBuf.setDataHistoryPassport("TEST THOI");
			objBuf.setDataImmihistory("TEST THOI");
			bufPersonInvestigationDao.saveOrUpdate(objBuf);
			
			String tranID = "MB1190821001001";
			//Tìm kiếm tranId
			NicUploadJob nicUp = nicUploadJobService.getUploadJobByTransactionIdSinger(null, tranID);
			if(nicUp != null){
				EppBufFamilyDto family = new EppBufFamilyDto();
				List<EppPersonDto> lstPerson = new ArrayList<EppPersonDto>();
				family.setFatherSurname(nicUp.getNicTransaction().getNicRegistrationData().getFatherSurname());
				family.setMotherSurname(nicUp.getNicTransaction().getNicRegistrationData().getMotherSurname());
				family.setSpouseSurname(nicUp.getNicTransaction().getNicRegistrationData().getSpouseSurname());
				
				family.setFatherDob(nicUp.getNicTransaction().getNicRegistrationData().getFatherDob());
				family.setMotherDob(nicUp.getNicTransaction().getNicRegistrationData().getMotherDob());
				family.setSpouseDob(nicUp.getNicTransaction().getNicRegistrationData().getSpouseDob());
				
				String xmlPerson = nicUp.getNicTransaction().getInfoPerson();
				
				if(StringUtils.isNotEmpty(xmlPerson)){
					JSONObject xmlJSONObj = XML.toJSONObject(xmlPerson);
					if(xmlJSONObj != null){
						if(xmlJSONObj.has("EPP_PERSONS") && !xmlJSONObj.isNull("EPP_PERSONS")){
							JSONObject resp = xmlJSONObj.getJSONObject("EPP_PERSONS");
							if(resp.has("EPP_PERSON") && !resp.isNull("EPP_PERSON")){
								Object item = resp.get("EPP_PERSON");
								try{
									if (item instanceof JSONArray){
										JSONArray arrayResp = (JSONArray) item;
										for (int i = 0; i < arrayResp.length(); i++) {
											EppPersonDto person = new EppPersonDto();
											JSONObject myResponse = new JSONObject();
											myResponse = arrayResp.getJSONObject(i);
											String id = "";
											if(myResponse.has("PLACE_OF_BIRTH_ID") && !myResponse.isNull("PLACE_OF_BIRTH_ID")){
												person.setPlaceOfBirthId(myResponse.getString("PLACE_OF_BIRTH_ID"));
											}
											if(myResponse.has("SEARCH_NAME") && !myResponse.isNull("SEARCH_NAME")){
												person.setSearchName(myResponse.getString("SEARCH_NAME"));
											}
											if(myResponse.has("NAME") && !myResponse.isNull("NAME")){
												person.setName(myResponse.getString("NAME"));
											}
											if(myResponse.has("DATE_OF_BIRTH") && !myResponse.isNull("DATE_OF_BIRTH")){
												person.setDateOfBirth(String.valueOf(myResponse.getInt("DATE_OF_BIRTH")));
											}
											if(myResponse.has("GENDER") && !myResponse.isNull("GENDER")){
												person.setGender(myResponse.getString("GENDER"));
											}
											if(myResponse.has("ID") && !myResponse.isNull("ID")){
												id = String.valueOf(myResponse.getInt("ID"));
												person.setId(id);
											}
											if(myResponse.has("TYPE_") && !myResponse.isNull("TYPE_")){
												person.setType_(myResponse.getString("TYPE_"));
											}
											if(myResponse.has("NATIONALITY_ID") && !myResponse.isNull("NATIONALITY_ID")){
												person.setNationalityId(myResponse.getString("NATIONALITY_ID"));
											}
											
											lstPerson.add(person);
										}
								    }
									else
								    {
										EppPersonDto person = new EppPersonDto();
										JSONObject myResponse = (JSONObject) item;
										String id = "";
										if(myResponse.has("PLACE_OF_BIRTH_ID") && !myResponse.isNull("PLACE_OF_BIRTH_ID")){
											person.setPlaceOfBirthId(myResponse.getString("PLACE_OF_BIRTH_ID"));
										}
										if(myResponse.has("SEARCH_NAME") && !myResponse.isNull("SEARCH_NAME")){
											person.setSearchName(myResponse.getString("SEARCH_NAME"));
										}
										if(myResponse.has("NAME") && !myResponse.isNull("NAME")){
											person.setName(myResponse.getString("NAME"));
										}
										if(myResponse.has("DATE_OF_BIRTH") && !myResponse.isNull("DATE_OF_BIRTH")){
											person.setDateOfBirth(String.valueOf(myResponse.getInt("DATE_OF_BIRTH")));
										}
										if(myResponse.has("GENDER") && !myResponse.isNull("GENDER")){
											person.setGender(myResponse.getString("GENDER"));
										}
										if(myResponse.has("ID") && !myResponse.isNull("ID")){
											id = String.valueOf(myResponse.getInt("ID"));
											person.setId(id);
										}
										if(myResponse.has("TYPE_") && !myResponse.isNull("TYPE_")){
											person.setType_(myResponse.getString("TYPE_"));
										}
										if(myResponse.has("NATIONALITY_ID") && !myResponse.isNull("NATIONALITY_ID")){
											person.setNationalityId(myResponse.getString("NATIONALITY_ID"));
										}
										
										lstPerson.add(person);
								    }
								}catch(Exception e){
									
								}
							}
						}
					}
				}
				
				String xmlFamily = "";
				JAXBContext jaxbContext = JAXBContext.newInstance(EppBufFamilyDto.class, EppPersonDto.class);
				Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
				StringWriter sw = new StringWriter();
				jaxbMarshaller.marshal(family, sw);
				xmlFamily = sw.toString();
				log("xmlFamily: " + xmlFamily);
				
				String status = "Đang xử lý";
				NicDocumentData docData = documentDataService.getDocumentDataById(nicUp.getTransactionId());
				if(docData == null && nicUp.getInvestigationStatus().equals(NicUploadJob.RECORD_STATUS_APPROVE_PERSO)){
					status = "Đã phê duyệt. Chờ in";
				}
				else if(docData != null){
					if(docData.getStatus().equals(NicDocumentData.DOCUMENT_DATA_STATUS_ISSUANCE) && nicUp.getNicTransaction().getTransactionStatus().equals(NicTransaction.TRANSACTION_STATUS_RIC_ISSUED)){
						status = "Đã trả hộ chiếu cho công dân";
					}
					else {
						status = "Đã in. Chờ trả hộ chiếu";
					}
				}
				
				EppBufInfoDocDto info = new EppBufInfoDocDto();
				info.setTransactionId(nicUp.getTransactionId());
				info.setRecieptNo(nicUp.getReceiptNo());
				if(nicUp.getNicTransaction().getTransactionType().equals("REP"))
					info.setTypeTransaction("Thay thế");
				else if(nicUp.getNicTransaction().getTransactionType().equals("REP_LOS"))
					info.setTypeTransaction("Thay thế do mất hỏng");
				else
					info.setTypeTransaction("Cấp mới");
				
				SiteRepository siteR = siteService.getSiteRepoById(nicUp.getNicTransaction().getRegSiteCode());
				if(siteR != null)
					info.setRegSiteCode(siteR.getSiteName());
				else
					info.setRegSiteCode(nicUp.getNicTransaction().getRegSiteCode());
				
				info.setStatus(status);
				
				List<NicTransactionPackage> nicP = nicTransactionPackageService.getPackageNameByTransactionId(nicUp.getTransactionId());
				String remarkApprove = "";
				if(nicP != null && nicP.size() > 0){
					for(NicTransactionPackage pack : nicP){
						if(pack.getTypeList() == 8 && pack.getStage().equals("D")){
							remarkApprove = pack.getNoteApprove();
							break;
						}
					}
				}
				info.setRemarkApprove(remarkApprove);
				
				String nameApprover = "";
				String levelApprover = "";
				if(StringUtils.isNotEmpty(nicUp.getNicTransaction().getLeaderOfficerId())){
					Users user = userService.findById(nicUp.getNicTransaction().getLeaderOfficerId());
					if(user != null){
						nameApprover = user.getUserName();
						levelApprover = user.getPosition();
					}
					else{
						nameApprover = nicUp.getNicTransaction().getLeaderOfficerId();
					}
				}
				info.setNameApprover(nameApprover);
				info.setLevelApprover(levelApprover);
				
				if(docData != null){
					info.setPassportNo(docData.getId().getPassportNo());
					info.setIssuePassportDate(docData.getDateOfIssue());
					info.setReleaseDate(docData.getIssueDatetime());
					
				}
				info.setReceiver(nicUp.getNicTransaction().getReceiver());
				
				String xmlInfo = "";
				JAXBContext jaxbContextInfo = JAXBContext.newInstance(EppBufInfoDocDto.class);
				Marshaller jaxbMarshallerInfo = jaxbContextInfo.createMarshaller();
				StringWriter swI = new StringWriter();
				jaxbMarshallerInfo.marshal(info, swI);
				xmlInfo = swI.toString();
				log("xmlInfo: " + xmlInfo);
				
				BufEppPersonInvestigation objBuf = bufPersonInvestigationService.findByTranId(tranID);
				if(objBuf == null){
					objBuf = new BufEppPersonInvestigation();
					objBuf.setCreateDatetime(new Date());
					objBuf.setCreateBy("SYSTEM");
					objBuf.setCreateWkstnId("SYSTEM-MACHINE");
				}
				else
				{
					objBuf.setUpdateBy("SYSTEM");
					objBuf.setUpdateWkstnId("SYSTEM-MACHINE");
					objBuf.setUpdateDatetime(new Date());
				}
				
				objBuf.setTransactionId(tranID);
				if(StringUtils.isNotEmpty(objBuf.getDataHistoryPassport())){
					objBuf.setDataHistoryPassport("");
				}
				
				if(StringUtils.isNotEmpty(objBuf.getDataImmihistory())){
					objBuf.setDataImmihistory("");
				}
				
				objBuf.setDataFamily(xmlFamily);
				objBuf.setDataInfoDocument(xmlInfo);
				Boolean check = bufPersonInvestigationService.updateAndCreate(objBuf);
				if(check){
					log(" luu thanh cong");
				}
				//outputR.add(outPut);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		log(" end  updatePackageDispatchInfoByPassportNoList dao");
	}
	
	public void testDaoImmiHistory(){
		
		try {
			List<String> passportNoList = Arrays.asList(new String[]{"AA0010003" , "EC0000002"}); 
			String packageId= "2000000010040";
			Date packageDateTime = DateUtil.strToDate("20160421", DateUtil.FORMAT_YYYYMMDD);
			String dispatchId= "DSP04201604250001";
			Date dispatchDateTime = DateUtil.strToDate("20160422", DateUtil.FORMAT_YYYYMMDD);
			String status= "ST";
			Date statusUpdateTime = DateUtil.strToDate("20160422", DateUtil.FORMAT_YYYYMMDD);
			String officerId= "EPP";
			String workstationId = "SYSTEM";
			
			documentDataDao.updatePackageDispatchInfoByPassportNoList(passportNoList, packageId, packageDateTime, dispatchId, dispatchDateTime, status, statusUpdateTime, officerId, workstationId);
			List<ImmiHistory> listImmi = nicImmiHistoryService.findByNinPPno("689876565", "");
			if(listImmi != null && listImmi.size() > 0){
				ImmiHistorys listImmis = new ImmiHistorys();
				listImmis.setImmiHistory(listImmi);
				JAXBContext jaxbContext = JAXBContext.newInstance(ImmiHistorys.class);
				Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
				StringWriter sw = new StringWriter();
				jaxbMarshaller.marshal(listImmis, sw);
				String xmlImmi = sw.toString();
				log("listImmi: " + xmlImmi);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		log(" end  updatePackageDispatchInfoByPassportNoList dao");
		
	}
	
	public void testInvestigationListInfoTargetDto(){
		
		try {
			List<String> passportNoList = Arrays.asList(new String[]{"AA0010003" , "EC0000002"}); 
			String packageId= "2000000010040";
			Date packageDateTime = DateUtil.strToDate("20160421", DateUtil.FORMAT_YYYYMMDD);
			String dispatchId= "DSP04201604250001";
			Date dispatchDateTime = DateUtil.strToDate("20160422", DateUtil.FORMAT_YYYYMMDD);
			String status= "ST";
			Date statusUpdateTime = DateUtil.strToDate("20160422", DateUtil.FORMAT_YYYYMMDD);
			String officerId= "EPP";
			String workstationId = "SYSTEM";
			
			documentDataDao.updatePackageDispatchInfoByPassportNoList(passportNoList, packageId, packageDateTime, dispatchId, dispatchDateTime, status, statusUpdateTime, officerId, workstationId);
			InvestigationListInfoTargetDto dto = new InvestigationListInfoTargetDto();
			dto.setAddress_O("XEM");
			dto.setAddressNin_O("03123912931");
			dto.setByA_O("OK OK");
			InfoFamillyDto familyO = new InfoFamillyDto();
			familyO.setDob_F("12/10/2019");
			familyO.setFullName_F("Nguyen Thanh Tri");
			familyO.setGender_F("M");
			familyO.setStage_F("stage_F");
			familyO.setToFamilly("tofamily");
			dto.getInfoFa().add(familyO);
			
			JAXBContext jaxbContext = JAXBContext.newInstance(InvestigationListInfoTargetDto.class);
			Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
			StringWriter sw = new StringWriter();
			jaxbMarshaller.marshal(dto, sw);
			String xmlImmi = sw.toString();
			log("listImmi: " + xmlImmi);
				
		} catch (Exception e) {
			e.printStackTrace();
		}
		log(" end  updatePackageDispatchInfoByPassportNoList dao");
		
	}*/
	
	public static void log(String message) {
		System.out.println(message);
	}
}
