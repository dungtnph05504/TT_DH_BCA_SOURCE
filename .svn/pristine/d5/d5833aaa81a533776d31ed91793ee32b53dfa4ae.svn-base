package service.perso.bussiness;

import java.io.StringWriter;
import java.text.DateFormat;
import java.text.Format;
import java.text.Normalizer;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;
import java.util.regex.Pattern;

import javax.jws.WebService;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.ext.Provider;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.xmlbeans.impl.xb.ltgfmt.Code;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.ObjectWriter;
import org.codehaus.jackson.type.TypeReference;
import org.hibernate.metamodel.source.annotations.xml.mocker.MockHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;

import service.perso.model.EditDataRegistration;
import service.perso.model.FullBlackList;
import service.perso.model.FullIdentification;
import service.perso.model.FunctionsJson;
import service.perso.model.HandoverSync;
import service.perso.model.ListBlackList;
import service.perso.model.ListIdentification;
import service.perso.model.ListStatusHandover;
import service.perso.model.MappingAuthenData;
import service.perso.model.MoreInformation;
import service.perso.model.PassportInfo;
import service.perso.model.PropertyModel;
import service.perso.model.RequestDataSpecial;
import service.perso.model.ResponseBase;
import service.perso.model.RetrieveDocumentDataRequest;
import service.perso.model.RetrievePassportDocumentRequest;
import service.perso.model.RolesJson;
import service.perso.model.StatusHandover;
import service.perso.model.TransactionPerso;
import service.perso.model.TransactionSyncRequest;
import service.perso.model.UserData;
import service.perso.model.UserRequest;
import service.perso.model.UsersJson;
import service.perso.model.WorkstationsJson;
import service.perso.model.XLHoSoInfo;
import service.perso.model.sync.DataSyncOther;
import service.perso.util.Contants;

import com.nec.asia.nic.comp.admin.code.utils.CodeRetrievalHelper;
import com.nec.asia.nic.comp.bufPersonInvestigation.service.BufPersonInvestigationService;
import com.nec.asia.nic.comp.job.dto.InvestigationListInfoTargetsDto;
import com.nec.asia.nic.comp.job.dto.TransactionSync;
import com.nec.asia.nic.comp.listHandover.domain.NicListHandover;
import com.nec.asia.nic.comp.listHandover.domain.NicListHandoverId;
import com.nec.asia.nic.comp.listHandover.service.ListHandoverService;
import com.nec.asia.nic.comp.trans.dao.DocumentDataDao;
import com.nec.asia.nic.comp.trans.dao.NicBlacklistAttachmentDao;
import com.nec.asia.nic.comp.trans.dao.NicBlacklistDao;
import com.nec.asia.nic.comp.trans.dao.NicIdentificationAttachmentDao;
import com.nec.asia.nic.comp.trans.dao.NicIdentificationDao;
import com.nec.asia.nic.comp.trans.dao.NicRegistrationDataDao;
import com.nec.asia.nic.comp.trans.dao.NicSearchResultDao;
import com.nec.asia.nic.comp.trans.dao.NicTransactionLogDao;
import com.nec.asia.nic.comp.trans.dao.NicTransactionPaymentDao;
import com.nec.asia.nic.comp.trans.dao.NicTransactionPaymentDetailDao;
import com.nec.asia.nic.comp.trans.domain.BaseModelList;
import com.nec.asia.nic.comp.trans.domain.BaseModelSingle;
import com.nec.asia.nic.comp.trans.domain.EppAirline;
import com.nec.asia.nic.comp.trans.domain.EppBlacklist;
import com.nec.asia.nic.comp.trans.domain.EppBlacklistAttachment;
import com.nec.asia.nic.comp.trans.domain.EppIdentification;
import com.nec.asia.nic.comp.trans.domain.EppIdentificationAttachmnt;
import com.nec.asia.nic.comp.trans.domain.EppPerson;
import com.nec.asia.nic.comp.trans.domain.EppPersonAttchmnt;
import com.nec.asia.nic.comp.trans.domain.EppPersonFamily;
import com.nec.asia.nic.comp.trans.domain.FamilyData;
import com.nec.asia.nic.comp.trans.domain.NicDocumentData;
import com.nec.asia.nic.comp.trans.domain.NicRegistrationData;
import com.nec.asia.nic.comp.trans.domain.NicSearchHitResult;
import com.nec.asia.nic.comp.trans.domain.NicSearchResult;
import com.nec.asia.nic.comp.trans.domain.NicTransaction;
import com.nec.asia.nic.comp.trans.domain.NicTransactionAttachment;
import com.nec.asia.nic.comp.trans.domain.NicTransactionLog;
import com.nec.asia.nic.comp.trans.domain.NicTransactionLost;
import com.nec.asia.nic.comp.trans.domain.NicTransactionPackage;
import com.nec.asia.nic.comp.trans.domain.NicTransactionPayment;
import com.nec.asia.nic.comp.trans.domain.NicTransactionPaymentDetail;
import com.nec.asia.nic.comp.trans.domain.NicUploadJob;
import com.nec.asia.nic.comp.trans.domain.RptStatisticsTransmitData;
import com.nec.asia.nic.comp.trans.dto.CountPassport;
import com.nec.asia.nic.comp.trans.dto.NicHitTransaction;
import com.nec.asia.nic.comp.trans.dto.NicListHandoverDto;
import com.nec.asia.nic.comp.trans.dto.NicPaymentJsonDto;
import com.nec.asia.nic.comp.trans.dto.NicTransactionJsonDto;
import com.nec.asia.nic.comp.trans.dto.NicTransactionPaymentDto;
import com.nec.asia.nic.comp.trans.dto.NicUploadJobDto;
import com.nec.asia.nic.comp.trans.dto.NicUploadJobJsonDto;
import com.nec.asia.nic.comp.trans.dto.RetrieveDocumentDataResponse;
import com.nec.asia.nic.comp.trans.service.AirlineService;
import com.nec.asia.nic.comp.trans.service.AirportService;
import com.nec.asia.nic.comp.trans.service.DocumentDataService;
import com.nec.asia.nic.comp.trans.service.EppPersonService;
import com.nec.asia.nic.comp.trans.service.FlightRouterService;
import com.nec.asia.nic.comp.trans.service.FlightService;
import com.nec.asia.nic.comp.trans.service.FreeVisaService;
import com.nec.asia.nic.comp.trans.service.NicRegistrationDataService;
import com.nec.asia.nic.comp.trans.service.NicSearchHitResultService;
import com.nec.asia.nic.comp.trans.service.NicSearchResultService;
import com.nec.asia.nic.comp.trans.service.NicTransactionAttachmentService;
import com.nec.asia.nic.comp.trans.service.NicTransactionLostService;
import com.nec.asia.nic.comp.trans.service.NicTransactionPackageService;
import com.nec.asia.nic.comp.trans.service.NicTransactionService;
import com.nec.asia.nic.comp.trans.service.NicUploadJobService;
import com.nec.asia.nic.comp.trans.service.PurposeService;
import com.nec.asia.nic.comp.trans.service.RptStatisticsTransmitDataService;
import com.nec.asia.nic.comp.trans.service.TransactionLogService;
import com.nec.asia.nic.comp.trans.service.impl.NicTransactionPaymentDetailServiceImpl;
import com.nec.asia.nic.dx.admin.ConfigurationType;
import com.nec.asia.nic.dx.admin.GetConfigurations;
import com.nec.asia.nic.dx.admin.GetConfigurationsResponse;
import com.nec.asia.nic.dx.trans.BufEppDataPerson;
import com.nec.asia.nic.dx.trans.BufEppListRequest;
import com.nec.asia.nic.dx.trans.BufEppListResponse;
import com.nec.asia.nic.dx.trans.BufEppPerson;
import com.nec.asia.nic.dx.trans.BufEppPersonDoc;
import com.nec.asia.nic.dx.trans.BufEppPersonInvestigation;
import com.nec.asia.nic.dx.trans.BufEppRequest;
import com.nec.asia.nic.dx.ws.FaultException;
import com.nec.asia.nic.framework.PaginatedResult;
import com.nec.asia.nic.framework.admin.code.domain.CodeValues;
import com.nec.asia.nic.framework.admin.code.domain.Codes;
import com.nec.asia.nic.framework.admin.code.service.CodesService;
import com.nec.asia.nic.framework.admin.rbac.domain.Functions;
import com.nec.asia.nic.framework.admin.rbac.domain.Roles;
import com.nec.asia.nic.framework.admin.rbac.domain.Users;
import com.nec.asia.nic.framework.admin.rbac.domain.Workstations;
import com.nec.asia.nic.framework.admin.rbac.service.FunctionService;
import com.nec.asia.nic.framework.admin.rbac.service.RoleService;
import com.nec.asia.nic.framework.admin.rbac.service.UserService;
import com.nec.asia.nic.framework.admin.rbac.service.WorkStationService;
import com.nec.asia.nic.framework.admin.site.dao.SiteRepositoryDao;
import com.nec.asia.nic.framework.admin.site.domain.SiteRepository;
import com.nec.asia.nic.framework.admin.site.service.SiteService;
import com.nec.asia.nic.utils.DateUtil;
import com.nec.asia.nic.utils.HelperClass;


@Path("/syncNic/")
@WebService
@Provider
public class SyncNicService {
	public final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private NicUploadJobService uploadJobService;

	@Autowired
	private NicTransactionService nicTransactionService;

	@Autowired
	private NicRegistrationDataService nicRegistrationDataService;
	
	@Autowired
	private NicRegistrationDataService nicRegistrationDao;
	
	@Autowired
	private NicTransactionLogDao nicTransactionLogDao;

	@Autowired
	private NicTransactionAttachmentService nicTransactionAttachmentService;

	@Autowired
	private DocumentDataDao documentDataDao;

	@Autowired
	private DocumentDataService documentDataService;
	
	@Autowired
	private ListHandoverService listHandoverService;
	
	@Autowired
	private NicTransactionPaymentDao nicTransactionPaymentDao;
	
	@Autowired
	private NicTransactionPaymentDetailDao nicTransactionPaymentDetailDao;
	
	@Autowired
	private CodesService codesService;
	
	@Autowired
	private BufPersonInvestigationService bufPersonInvestigationService;
	
	@Autowired
	private RptStatisticsTransmitDataService rptStatisticsTransmitDataService;
	
	@Autowired
	private NicSearchResultDao nicSearchResultDao;
	
	@Autowired
	private NicTransactionPackageService nicTransactionPackageService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private RoleService roleService;
	
	@Autowired
	private FunctionService functionService; 
	
	@Autowired
	private WorkStationService workStationService;
	
	@Autowired
	private NicIdentificationAttachmentDao nicIdentificationAttachmentDao;
	
	@Autowired
	private NicIdentificationDao nicIdentificationDao;
	
	@Autowired
	private NicBlacklistAttachmentDao nicBlacklistAttachmentDao;
	
	@Autowired
	private NicBlacklistDao nicBlacklistDao;
	
	@Autowired
	private SiteService siteService;
	
	@Autowired
	private NicTransactionLostService lostService;
	
	@Autowired
	private NicSearchHitResultService nicSearchHitResultService;
	
	@Autowired
	private NicSearchResultService nicSearchResultService;
	
	@Autowired
	private EppPersonService eppPersonService;
	
	@Autowired
	private CodeRetrievalHelper codeRetrievalHelper;
	
	@Autowired
	private RptStatisticsTransmitDataService rptService;
	
	@GET
	@Produces("application/json")
	@Consumes("application/json")
	@Path("/syncHandover/{datefrom}")
	public ResponseBase<List<HandoverSync>> syncHandover(@PathParam("datefrom") String datefrom) {
		PropertyModel prModel = new PropertyModel();
		prModel.setCode(-1);
		prModel.setMessage("Null data");
		ResponseBase<List<HandoverSync>> rep = new ResponseBase<List<HandoverSync>>();
		List<HandoverSync> listH = new ArrayList<HandoverSync>();
		try {
			//Lấy danh sách gói đồng bộ
			/*List<CountPassport> handoverA = listHandoverService.countHandoverA(datefrom);
			List<CountPassport> handoverAProcess = listHandoverService.countHandoverAProcess(datefrom);
			if(handoverAProcess != null && handoverAProcess.size() > 0){
				for(CountPassport cap : handoverAProcess){
					for(CountPassport ca : handoverA){
						NicListHandover handover = listHandoverService.findByPackageId(ca.getRegSite());
						if(cap.getRegSite().equals(ca.getRegSite()) && ca.getTotal().equals(handover.getCountTransaction()) && cap.getTotal().equals(handover.getCountTransaction())){
							HandoverSync han = new HandoverSync();
							List<String> lstTran = new ArrayList<String>();
							han.setSize(ca.getTotal());
							han.setPackID(ca.getRegSite());
							List<String> listTran = listHandoverService.listTransactionA(ca.getRegSite());
							for(String tran : listTran){
								lstTran.add(tran);
							}
							han.setListTran(lstTran);
							listH.add(han);
							break;
						}
					}
				}
			}*/
			
			//List<CountPassport> handoverA = listHandoverService.countHandoverA(datefrom);
			List<CountPassport> handoverAProcess = listHandoverService.countHandoverAProcess(datefrom);
			if(handoverAProcess != null && handoverAProcess.size() > 0){
				for(CountPassport cap : handoverAProcess){
					NicListHandover handover = listHandoverService.findByPackageId(new NicListHandoverId(cap.getRegSite(), NicListHandover.TYPE_LIST_A)).getModel();
					
					if(cap.getTotal().equals(handover.getCountTransaction())){
						HandoverSync han = new HandoverSync();
						List<String> lstTran = new ArrayList<String>();
						han.setSize(cap.getTotal());
						han.setPackID(cap.getRegSite());
						han.setSiteCode(handover.getSiteCode());
						List<String> listTran = listHandoverService.listTransactionA(cap.getRegSite());
						for(String tran : listTran){
							lstTran.add(tran);
						}
						han.setListTran(lstTran);
						listH.add(han);
						break;
					}
				}
			}
			
			if(listH != null && listH.size() > 0){
//				logger.info(String.valueOf(listH.size()));
				
				ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
				String json = ow.writeValueAsString(listH);
//				logger.info(json);
				
				prModel.setCode(99);
				prModel.setMessage("Size data: " + listH.size());
				rep.setResponse(listH);
				
				/* Lưu bảng thống kê truyền nhận*/
				this.saveOrUpRptData(Contants.URL_SYNC_HANDOVER, listH.size(), null);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			prModel.setMessage(e.getMessage());
			e.printStackTrace();
		}
		rep.setProperty(prModel);
		return rep;
	}
	
	@POST
	@Produces("application/json")
	@Consumes("application/json")
	@Path("/syncHandoverStatus")
	public ResponseBase<Boolean> syncHandoverStatus(ListStatusHandover data) {
		PropertyModel prModel = new PropertyModel();
		prModel.setCode(-1);
		prModel.setMessage("Null data");
		ResponseBase<Boolean> rep = new ResponseBase<Boolean>(); 
		rep.setResponse(false);
		
		try {
			if(data == null || data.getListHan() == null || data.getListHan().size() < 0){
				prModel.setMessage("Du lieu dau vao khong dung");
				return rep;
			}
			else
			{
				for(StatusHandover han : data.getListHan()){
					NicListHandover handover = listHandoverService.findByPackageId(new NicListHandoverId(han.getHandoverId(), null)).getModel();
					if(handover != null){
						//handover.setStatusSyncXl(han.getStatus());
						listHandoverService.saveOrUpdate(handover);
					}
				}
				prModel.setCode(99);
				prModel.setMessage("success");
				rep.setResponse(true);
				
				/* Lưu bảng thống kê truyền nhận*/
				this.saveOrUpRptData(Contants.URL_SYNC_HANDOVER_STATUS, 1, null);
			}
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			prModel.setMessage(e.getMessage());
			e.printStackTrace();
		}
		rep.setProperty(prModel);
		return rep;
	}
	
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("/getTransactionById")
	public ResponseBase<TransactionSync> getTransactionById(TransactionPerso tranx) {
		PropertyModel prModel = new PropertyModel();
		prModel.setCode(-1);
		prModel.setMessage("Null data");
		ResponseBase<TransactionSync> rep = new ResponseBase<TransactionSync>();
		String tranid = "";
		if(org.apache.commons.lang.StringUtils.isNotEmpty(tranx.getTransactionId())){
			tranid = tranx.getTransactionId();
		}
		else {
			prModel.setMessage("Không tìm thấy dữ liệu Tranid");
			rep.setProperty(prModel);
			return rep;
		}
		
		try {
			BaseModelSingle<NicUploadJob> bGetUJ = uploadJobService.getUploadJobByTransactionIdSinger(null, tranid);
			NicUploadJob nicUp = bGetUJ.getModel();
			if(nicUp == null){
				prModel.setMessage("Không tìm thấy dữ liệu NicUploadJob. TranId: " + tranid);
				rep.setProperty(prModel);
				return rep;
			}
			List<NicTransactionAttachment> listattachment = nicTransactionAttachmentService.findNicTransactionAttachments(tranid, null, null).getListModel();
			if(listattachment == null || listattachment.size() < 0){
				prModel.setMessage("Không tìm thấy dữ liệu NicTransactionAttachment");
				rep.setProperty(prModel);
				return rep;
			}
			BaseModelList<NicTransactionLog> baseFindAllTranLog = nicTransactionLogDao.findAllByRefIdAndTransStateList(tranid, null);
			List<NicTransactionLog> listLog = baseFindAllTranLog.getListModel();
			if(listLog == null || listLog.size() < 0){
				prModel.setMessage("Không tìm thấy dữ liệu NicTransactionLog");
				rep.setProperty(prModel);
				return rep;
			}
			
			NicTransactionPayment payment = nicUp.getNicTransaction().getNicTransactionPayment();
			List<NicTransactionPaymentDetail> pdetail = null;
			if(payment == null){
				prModel.setMessage("Không tìm thấy dữ liệu NicTransactionPayment");
				rep.setProperty(prModel);
				return rep;
			}
			else{
				pdetail = new ArrayList<NicTransactionPaymentDetail>();
				BaseModelList<NicTransactionPaymentDetail> baseFindDP = nicTransactionPaymentDetailDao.findListDetailPaymentList(payment.getPaymentId());
				pdetail = baseFindDP.getListModel();
			}
			
			TransactionSync tran = new TransactionSync();
			ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
			if(nicUp != null){
				NicUploadJobJsonDto nicUpjson = new NicUploadJobJsonDto();
				nicUpjson.setAfisCheckStatus(nicUp.getAfisCheckStatus());
				nicUpjson.setAfisRegisterStatus(nicUp.getAfisRegisterStatus());
				nicUpjson.setAfisUpdateStatus(nicUp.getAfisUpdateStatus());
				nicUpjson.setAfisVerifyStatus(nicUp.getAfisVerifyStatus());
				//nicUpjson.setApproveFlag(nicUp.getApproveFlag());
				nicUpjson.setCpdCheckStatus(nicUp.getCpdCheckStatus());
				nicUpjson.setCreateBy(nicUp.getCreateBy());
				nicUpjson.setCreateDatetime(nicUp.getCreateDatetime());
				nicUpjson.setCreateWkstnId(nicUp.getCreateWkstnId());
				nicUpjson.setDateApproveInvestigation(nicUp.getDateApproveInvestigation());
				nicUpjson.setDateApproveNin(nicUp.getDateApproveNin());
				nicUpjson.setDateApprovePerson(nicUp.getDateApprovePerson());
				//nicUpjson.setDatePackage(nicUp.getDatePackage());
				//nicUpjson.setEstDateOfCollection(nicUp.getEstDateOfCollection());
				nicUpjson.setInvestigationOfficerId(nicUp.getInvestigationOfficerId());
				nicUpjson.setInvestigationStatus(nicUp.getInvestigationStatus());
				nicUpjson.setInvestigationType(nicUp.getInvestigationType());
				nicUpjson.setJobApproveStatus(nicUp.getJobApproveStatus());
				nicUpjson.setJobCompletedFlag(nicUp.getJobCompletedFlag());
				nicUpjson.setJobCreatedTime(nicUp.getCreateDatetime());
				nicUpjson.setJobCurrentStage(nicUp.getJobCurrentStage());
				nicUpjson.setJobCurrentStatus(nicUp.getJobCurrentStatus());
				nicUpjson.setJobEndTime(nicUp.getJobEndTime());
				//nicUpjson.setJobErrorFlag(nicUp.getJobErrorFlag());
				nicUpjson.setJobPriority(nicUp.getJobPriority());
				nicUpjson.setJobReprocessCount(nicUp.getJobReprocessCount());
				nicUpjson.setJobStartTime(nicUp.getJobStartTime());
				nicUpjson.setJobType(nicUp.getJobType());
				nicUpjson.setNoteApproveNin(nicUp.getNoteApproveNin());
				nicUpjson.setNoteApproveObj(nicUp.getNoteApproveObj());
				nicUpjson.setNoteApprovePerson(nicUp.getNoteApprovePerson());
				//nicUpjson.setNumberTran(nicUp.getNumberTran());
				nicUpjson.setOriginalyBlacklistId(nicUp.getOriginalyBlacklistId());
				//nicUpjson.setOriginalyId(nicUp.getOriginalyId());
				//nicUpjson.setOriginalyPersonId(nicUp.getOriginalyPersonId());
				//nicUpjson.setPackageId(nicUp.getPackageId());
				nicUpjson.setPersoRegisterStatus(nicUp.getPersoRegisterStatus());
				nicUpjson.setPriority(nicUp.getNicTransaction().getPriority());
				nicUpjson.setReceiptNo(nicUp.getReceiptNo());
				nicUpjson.setRicName(nicUp.getNicTransaction().getIssSiteCode());
				nicUpjson.setTempPassportNo(nicUp.getTempPassportNo());
				nicUpjson.setTransactionId(nicUp.getTransactionId());
				nicUpjson.setUpdateBy(nicUp.getUpdateBy());
				nicUpjson.setUpdateDatetime(nicUp.getUpdateDatetime());
				nicUpjson.setUpdateWkstnId(nicUp.getUpdateWkstnId());
				nicUpjson.setValidateInfoBca(nicUp.getValidateInfoBca());
				nicUpjson.setWorkflowJobId(nicUp.getWorkflowJobId());
				
				String json = ow.writeValueAsString(nicUpjson);
				tran.setNicUp(json);
				
				NicRegistrationData regis = nicUp.getNicTransaction().getNicRegistrationData();
				String jsonR = ow.writeValueAsString(regis);
				tran.setRegis(jsonR);
				
				NicTransactionJsonDto nicTranJ = new NicTransactionJsonDto();
				NicTransaction nicTran = nicUp.getNicTransaction();
				nicTranJ.setApplnRefNo(nicTran.getApplnRefNo());
				nicTranJ.setCreateBy(nicTran.getCreateBy());
				nicTranJ.setCreateDatetime(nicTran.getCreateDatetime());
				nicTranJ.setCreateWkstnId(nicTran.getCreateWkstnId());
				nicTranJ.setDateOfApplication(nicTran.getDateOfApplication());
				nicTranJ.setEstDateOfCollection(nicTran.getEstDateOfCollection());
				nicTranJ.setIsPostOffice(nicTran.getIsPostOffice());
				nicTranJ.setInfoPerson(nicTran.getInfoPerson());
				nicTranJ.setIssSiteCode(nicTran.getIssSiteCode());
				nicTranJ.setIssuingAuthority(nicTran.getIssuingAuthority());
				//nicTranJ.setLeaderOfficerId(nicTran.getLeaderOfficerId());
				nicTranJ.setNicSiteCode(nicTran.getNicSiteCode());
				nicTranJ.setNin(nicTran.getNin());
				//nicTranJ.setNoteHandoverB(nicTran.getNoteHandoverB());
				nicTranJ.setPackageID(nicTran.getPackageId());
				nicTranJ.setPassportType(nicTran.getPassportType());
				nicTranJ.setPrevDateOfIssue(nicTran.getPrevDateOfIssue());
				nicTranJ.setPrevPassportNo(nicTran.getPrevPassportNo());
				//nicTranJ.setPrintPerso(nicTran.getPrintPerso());
				nicTranJ.setPriority(nicTran.getPriority());
				nicTranJ.setRecieptNo(nicTran.getRecieptNo());
				nicTranJ.setRegSiteCode(nicTran.getRegSiteCode());
				//nicTranJ.setSyncPassport(nicTran.getIsEpassport().equals("Y") ? true : false);
				nicTranJ.setTransactionId(nicTran.getTransactionId());
				nicTranJ.setTransactionStatus(nicTran.getTransactionStatus());
				nicTranJ.setTransactionType(nicTran.getTransactionType());
				nicTranJ.setUpdateBy(nicTran.getUpdateBy());
				nicTranJ.setUpdateDatetime(nicTran.getUpdateDatetime());
				nicTranJ.setUpdateWkstnId(nicTran.getUpdateWkstnId());
				nicTranJ.setValidityPeriod(nicTran.getValidityPeriod());
				nicTranJ.setPassportStyle(nicTran.getIsEpassport().equals("Y") ? true : false);
				nicTranJ.setPassportType(nicTran.getPassportType());
				
				/*
				nicTranJ.setDateCountryDesc(nicTran.getDateCountryDesc());
				nicTranJ.setDateDecisionDesc(nicTran.getDateDecisionDesc());
				nicTranJ.setDateNinDesc(nicTran.getDateNinDesc());
				nicTranJ.setDateOfBirthDesc(nicTran.getDateOfBirthDesc());
				nicTranJ.setDatePassportDesc(nicTran.getDatePassportDesc());*/
				
				String jsonT = ow.writeValueAsString(nicTranJ);
				tran.setTran(jsonT);
			}
			
			if(listattachment != null && listattachment.size() > 0){
				String json = ow.writeValueAsString(listattachment);
				tran.setListattachment(json);
			}
			
			if(listLog != null && listLog.size() > 0){
				String json = ow.writeValueAsString(listLog);
				tran.setListLog(json);
				//tran.setListLog(listLog);
			}
			
			if(pdetail != null){
				String jsonPdetail = ow.writeValueAsString(pdetail);
				tran.setListPdetail(jsonPdetail);
			}
			
			if(payment != null){
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
				
				String jsonP = ow.writeValueAsString(paymentJ);
				tran.setPayment(jsonP);
			}
			
			List<NicTransactionPackage> hpackage = nicTransactionPackageService.getPackageNameByTransactionId(tranid);
			if(hpackage == null || hpackage.size() < 0){
				prModel.setMessage("Không tìm thấy dữ liệu NicTransactionPackage");
				rep.setProperty(prModel);
				return rep;
			}
			else
			{
				String jsonPack = ow.writeValueAsString(hpackage);
				tran.setHandoverPackage(jsonPack);
			}
			
			rep.setResponse(tran);
			prModel.setMessage("success");
			prModel.setCode(99);
			
			/* Lưu bảng thống kê truyền nhận*/
			this.saveOrUpRptData(Contants.URL_GET_TRANSACTION_BY_ID, 1, null);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			prModel.setMessage(e.getMessage());
			e.printStackTrace();
		}

		rep.setProperty(prModel);
		return rep;
	}
	
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("/dowloadBufPerson")
	public ResponseBase<BufEppDataPerson> dowloadBufPerson(BufEppRequest request) {

		PropertyModel prModel = new PropertyModel();
		prModel.setCode(-1);
		prModel.setMessage("Null data");
		ResponseBase<BufEppDataPerson> rep = new ResponseBase<BufEppDataPerson>();
		try{
			BufEppDataPerson result = new BufEppDataPerson();
			result.setResultCheck("N");
			Boolean resultCPD = false;
			if(StringUtils.isNotEmpty(request.getTransactionId()) && (request.getTransCPD() != null || request.getTransBMS() != null)
					&& request.getTransactionMasterId() != null){
				
				List<NicUploadJob> listUpload = uploadJobService.findAllByTransactionId(request.getTransactionMasterId());
				NicUploadJob uploadJob = listUpload.get(0);
				List<NicUploadJob> listUploadOb = uploadJobService.findAllByTransactionId(request.getTransactionId());				
				NicUploadJob uploadJobOb = listUploadOb.get(0);
				//EppPerson person = null;
				//List<EppPerson> lstPer = eppPersonService.findByGlobalId(uploadJobOb.getOriginalyPersonId());
				//if(lstPer != null && lstPer.size() > 0){
					//person = lstPer.get(0);
				//}
				if(StringUtils.isNotEmpty(uploadJob.getInvestigationStatus())){
					result.setResultCheck("Y");
					BufEppPerson bufPeson = new BufEppPerson();
					
					if(request.getTransCPD() != null){
						//if(person != null){
							//bufPeson.setGlobalId(person.getGlobalId() != null ? person.getGlobalId().toString() :"");
							//bufPeson.setOriginId(person.getOriginId() != null ? person.getOriginId().toString() :"");
						//}
						//logger.info("Info - TrasacitonID: " + request.getTransactionId() + " || idCPD: " + request.getTransCPD() + " || tranMaster: " + request.getTransactionMasterId());
							resultCPD = true;
							String sfDob = "";
							String smDob = "";
							String ssDob = "";
							//Lấy dữ liệu thông tin hồ sơ của transaction
							NicTransaction nicTran = nicTransactionService.getNicTransactionById(request.getTransactionId()).getModel();
							if(nicTran != null){
								NicRegistrationData regOrg = nicTran
										.getNicRegistrationData();
								if (regOrg != null) {
									sfDob = regOrg.getFatherDefDateOfBirth();
									smDob = regOrg.getMotherDefDateOfBirth();
									ssDob = regOrg.getSpouseDefDateOfBirth();
									String address = regOrg.getAddressLine1();
									String px = "";
									String tp = "";
									if(StringUtils.isNotEmpty(regOrg.getAddressLine4())){
										try {
											px =  codesService.getCodeValueDescByIdName("TOWN_VILLAGE", regOrg.getAddressLine4(), "");
										} catch (Exception e) {
											// TODO: handle exception
										}
										
										try {
											tp = codesService.getCodeValueDescByIdName("DISTRICT", regOrg.getAddressLine5(), "");
										} catch (Exception e) {
											// TODO: handle exception
										}
									}
									if(StringUtils.isNotEmpty(px)){
										address += ", " + px;
									}
									if(StringUtils.isNotEmpty(tp)){
										address += ", " + tp;
									}
									bufPeson.setAddressResident(address);
									bufPeson.setCreateBy("SYSTEM");
									bufPeson.setCreateDateTime(new Date());
									bufPeson.setCreateWkstnID("SYSTEM-WKST");
									bufPeson.setDob(regOrg.getDateOfBirth());
									bufPeson.setFullname(regOrg.getSurnameLine1());
									bufPeson.setGender(regOrg.getGender());
									bufPeson.setIssueDateNin(regOrg.getDateNin());
									//bufPeson.setIssueDatePassport(regOrg.getDatePassport());
									bufPeson.setIssuePlaceNin(regOrg.getAddressNin());
									bufPeson.setNation(regOrg.getNation());
									bufPeson.setNin(nicTran.getNin());
									bufPeson.setNote("");
									bufPeson.setStyleDob(regOrg.getDefDateOfBirth());
									
									String desPob = regOrg.getPlaceOfBirth();
									if(StringUtils.isNotEmpty(regOrg.getPlaceOfBirth())){
										CodeValues codeV = codesService.getCodeValueByIdName("CODE_BirthPlace", regOrg.getPlaceOfBirth());
										if(codeV != null){
											desPob = codeV.getCodeValueDesc();
										}
									}
									bufPeson.setPobDes(desPob);
									bufPeson.setHandoverA(nicTran.getRecieptNo());
									bufPeson.setInfoPerson(nicTran.getInfoPerson());
									NicListHandover listH = listHandoverService.findHandoverByCriteria(nicTran.getPackageId(), NicListHandover.TYPE_LIST_A, null).getModel();
									if(listH != null){
										try{
											Format formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
											Date dateS = new java.sql.Date(listH.getCreateDate().getTime());
											String s = formatter.format(dateS).split(" ")[0];
											bufPeson.setCreateDateHandover(s);
										}catch(Exception e){ }
										
										try{
											if(StringUtils.isNotEmpty(listH.getCreateBy())){
												Users user = userService.findByUserId(listH.getCreateBy());
												if(user != null){
													bufPeson.setCreateByHandover(user.getUserName());
												}
												else{
													bufPeson.setCreateByHandover(listH.getCreateBy());
												}
											}
										}catch(Exception e){ }
									}
									
									if(StringUtils.isNotEmpty(regOrg.getJob())){
										CodeValues codeV = codesService.getCodeValueByIdName("CODE_JOB", regOrg.getJob());
										if(codeV != null){
											bufPeson.setJobs(codeV.getCodeValueDesc());
										}
									}
									
									
									// Thông tin hộ chiếu
									Collection<NicDocumentData> nicDocs = documentDataService
											.findByTransactionId(request
													.getTransactionId()).getModel();
									if (nicDocs != null && nicDocs.size() > 0) {
										List<NicDocumentData> nicDocs_ = new ArrayList<NicDocumentData>(
												nicDocs);
										NicDocumentData nicDoc = nicDocs_.get(0);
										bufPeson.setPassportNo(nicDoc.getId()
												.getPassportNo());
										bufPeson.setPassportStatus(nicDoc.getStatus()); // ISSUANCE: phát hành - NONE: tạm khóa
										if(nicDoc.getStatus() != null && nicDoc.getStatus().equals("ISSUANCE"))
											bufPeson.setIssueFlag("Y");
										else
											bufPeson.setIssueFlag("N");

										Format formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
										
										if(nicDoc.getDateOfIssue() != null){
											Date dateS = new java.sql.Date(nicDoc.getDateOfIssue().getTime());
											String s = formatter.format(dateS).split(" ")[0];
											bufPeson.setIssuePassport(s);
										}
										
										if(nicDoc.getDateOfExpiry() != null){
											Date dateS = new java.sql.Date(nicDoc.getDateOfExpiry().getTime());
											String s = formatter.format(dateS).split(" ")[0];
											bufPeson.setExpiredPassport(s);
										}
										
										if(nicDoc.getCreateDatetime() != null){
											Date dateS = new java.sql.Date(nicDoc.getCreateDatetime().getTime());
											String t = formatter.format(dateS).split(" ")[0];
											bufPeson.setPrintDateC(t);
										}
										
										if(nicDoc.getPackageDatetime() != null){
											Date dateS = new java.sql.Date(nicDoc.getPackageDatetime().getTime());
											String t = formatter.format(dateS).split(" ")[0];
											bufPeson.setCreateDateC(t);
										}
										
										SiteRepository site = siteService.getSiteRepoById(nicTran.getIssSiteCode());
										if(site != null)
											bufPeson.setIssuePlacePassport(site.getSiteName());
										
										if(nicTran.getPassportType().equals("PO"))
											bufPeson.setPassportType("CÔNG VỤ");
										else if(nicTran.getPassportType().equals("PD"))
											bufPeson.setPassportType("NGOẠI GIAO");
										else
											bufPeson.setPassportType("PHỔ THÔNG");
										bufPeson.setSerial(nicDoc.getChipSerialNo());
										bufPeson.setIcaoLineOne(nicDoc.getIcaoLine1());
										bufPeson.setIcaoLineTwo(nicDoc.getIcaoLine2());
									}
									bufPeson.setPhone(regOrg.getContactNo());
									bufPeson.setPob(regOrg.getPlaceOfBirth());
									bufPeson.setReligion(regOrg.getReligion());
									bufPeson.setTransacionId(request.getTransactionId());
		
									bufPeson.setFatherLost("N");
									bufPeson.setMotherLost("N");
									bufPeson.setFatherLost(regOrg.getFatherLost());
									bufPeson.setMotherLost(regOrg.getMotherLost());
									
									// Thông tin ng thân
									FamilyData family = new FamilyData();
		
									String fatherName = regOrg.getFatherFullname();
									String motherName = regOrg.getMotherFullname();
									String spouseName = regOrg.getSpouseFullname();
		
									family.setFatherName(fatherName.trim());
									family.setMotherName(motherName.trim());
									family.setSpouseName(spouseName.trim());
		
									if (regOrg.getFatherDateOfBirth() != null) {
										family.setFatherDob(regOrg.getFatherDateOfBirth());
									}
									if (regOrg.getMotherDateOfBirth() != null) {
										family.setMotherDob(regOrg.getMotherDateOfBirth());
									}
									if (regOrg.getSpouseDateOfBirth() != null) {
										family.setSpouseDob(regOrg.getSpouseDateOfBirth());
									}
		
									JAXBContext jaxbContext = JAXBContext
											.newInstance(FamilyData.class);
									Marshaller jaxbMarshaller = jaxbContext
											.createMarshaller();
									StringWriter sw = new StringWriter();
									jaxbMarshaller.marshal(family, sw);
									String xmlFamily = sw.toString();
		
									bufPeson.setFamilyData(xmlFamily);
								}
							}
							List<NicTransactionPackage> lstPack = nicTransactionPackageService.getPackageNameByTransactionId(request.getTransactionId());
							if(lstPack != null && lstPack.size() > 0){
								for(NicTransactionPackage pack : lstPack){
									if(pack.getTypeList() == 8 && pack.getStage().equals("D")){
										NicListHandover han = listHandoverService.findByPackageId(new NicListHandoverId(pack.getPackageId(), null)).getModel();						
										if(han != null){
											if(han.getCreateDate() != null){
												Format formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
												if (han.getCreateDate() != null){
													Date dateS = new java.sql.Date(han.getCreateDate().getTime());
													String s = formatter.format(dateS);
													bufPeson.setApproveDateB(s.split(" ")[0]);
												}
												if (han.getUpdateDate() != null){
													Date dateA = new java.sql.Date(han.getUpdateDate().getTime());
													String a = formatter.format(dateA);
													bufPeson.setRequestDateB(a.split(" ")[0]);
												}
											}
										}
										bufPeson.setApproveNoteB(pack.getNoteLeaderApprove());
										bufPeson.setRequestNoteB(pack.getNoteApprove());
									}
								}
							}
							
							/*if (nicTran.getLeaderOfficerId() != null){
								Users user_ = userService.findByUserId(nicTran.getLeaderOfficerId());
								if(user_ != null){
									bufPeson.setApproveUserB(user_.getUserName());
								} else {
									bufPeson.setApproveUserB(nicTran.getLeaderOfficerId());
								}
							}*/
							if (uploadJobOb.getInvestigationOfficerId() != null){
								Users user_ = userService.findByUserId(uploadJobOb.getInvestigationOfficerId());
								if(user_ != null){
									bufPeson.setRequestUserB(user_.getUserName());
								} else {
									bufPeson.setRequestUserB(uploadJobOb.getInvestigationOfficerId());
								}
							}
							
							//Lấy dữ liệu ảnh/tài liệu đính kèm/vân tay
							List<BufEppPersonDoc> bufDocList = new ArrayList<BufEppPersonDoc>();
							List<NicTransactionAttachment> docList = nicTransactionAttachmentService
									.getNicTransactionAttachments(
											request.getTransactionId(),
											new String[] { NicTransactionAttachment.DOC_TYPE_PHOTO_CAPTURE, 
												NicTransactionAttachment.DOC_TYPE_FINGERPRINT},
											null);
							if(docList != null && docList.size() > 0){
								for(NicTransactionAttachment doc : docList){
									BufEppPersonDoc bufDoc = new BufEppPersonDoc();
									bufDoc.setCreateBy("SYSTEM");
									bufDoc.setCreateDateTime(new Date());
									bufDoc.setCreateWkstnID("SYSTEM-WKST");
									bufDoc.setDocData(doc.getDocData());
									bufDoc.setDocType(doc.getDocType());
									bufDoc.setPersonIdDoc(doc.getRemarks());
									bufDoc.setSerial(doc.getSerialNo());
									bufDoc.setTransacionId(request.getTransactionId());
									bufDocList.add(bufDoc);
								}
							}
							
							BufEppPersonInvestigation bufInves = new BufEppPersonInvestigation();
							com.nec.asia.nic.comp.trans.domain.BufEppPersonInvestigation bufInvesDomain = bufPersonInvestigationService.findByTranId(request.getTransactionId());
							if(bufInvesDomain != null){
								//bufInves.setDataHistoryPassport(bufInvesDomain.getDataHistoryPassport());
								bufInves.setCreateBy(bufInvesDomain.getCreateBy());
								bufInves.setCreateDateTime(new Date());
								//bufInves.setDataImmihistory(bufInvesDomain.getDataImmihistory());
								//bufInves.setDataInfo(bufInvesDomain.getDataInfoDocument());
								//bufInves.setDataFamily(bufInvesDomain.getDataFamily());
								bufInves.setTransacionId(request.getTransactionId());
								bufInves.setSfDob(sfDob);
								bufInves.setSmDob(smDob);
								bufInves.setSsDob(ssDob);
							}
							
							if(bufDocList != null && bufDocList.size() > 0){
								for(BufEppPersonDoc doc : bufDocList){
									bufPeson.getBufEppPersonDoc().add(doc);
								}	
							}
							
							bufPeson.setBufEppPersonInvestigation(bufInves);
					}
					
					if(request.getTransBMS() != null){
						//logger.info("Info - TrasacitonID: " + request.getTransactionId() + " || idBMS: " + request.getTransBMS() +  " || tranMaster: " + request.getTransactionMasterId());
						List<NicSearchHitResult> listHitBMS = nicSearchHitResultService
								.findHitPositionsHit(null, request.getTransBMS());
						if (listHitBMS != null && listHitBMS.size() > 0) {
							for (NicSearchHitResult sHR : listHitBMS) {
								if (sHR.getDataSource().equals(NicSearchHitResult.hitInfo_DataSource_BMS)) {
									int score = 0;
									// Tính số điểm trung bình cho vân tay
									// Kiểm tra chuỗi dữ liệu
									if (StringUtils.isNotEmpty(sHR.getHitInfo())) {
										// Tách chuỗi theo dấu ","
										String[] listHit = sHR.getHitInfo().split(",");
										if (listHit.length > 0) {
											for (int i = 0; i < listHit.length; i++) {
												if (Double.parseDouble((listHit[i]
														.split("=")[1])) > score) {
													double d = Double
															.parseDouble((listHit[i]
																	.split("=")[1]));
													score = (int) d;
												}
											}
										}
		
										bufPeson.setScoreBMS("" + score);
										bufPeson.setDetailScoreBMS(sHR.getHitInfo());
										
										if(!resultCPD){
											//if(person != null){
												//bufPeson.setGlobalId(person.getGlobalId() != null ? person.getGlobalId().toString() :"");
												//bufPeson.setOriginId(person.getOriginId() != null ? person.getOriginId().toString() :"");
											//}
											//Lấy dữ liệu thông tin hồ sơ của transaction
											NicTransaction nicTran = nicTransactionService.getNicTransactionById(request.getTransactionId()).getModel();
											if(nicTran != null){
												NicRegistrationData regOrg = nicTran.getNicRegistrationData();
												if(regOrg != null){
													String address = regOrg.getAddressLine1();
													String px = codesService.getCodeValueDescByIdName("TOWN_VILLAGE", regOrg.getAddressLine4(), "");
													String tp = codesService.getCodeValueDescByIdName("DISTRICT", regOrg.getAddressLine5(), "");
													if(StringUtils.isNotEmpty(px)){
														address += ", " + px;
													}
													if(StringUtils.isNotEmpty(tp)){
														address += ", " + tp;
													}
													bufPeson.setAddressResident(address);
													bufPeson.setCreateBy("SYSTEM");
													bufPeson.setCreateDateTime(new Date());
													bufPeson.setCreateWkstnID("SYSTEM-WKST");
													bufPeson.setDob(regOrg.getDateOfBirth());
													bufPeson.setFullname(regOrg.getSurnameLine1());
													bufPeson.setGender(regOrg.getGender());
													bufPeson.setIssueDateNin(regOrg.getDateNin());
													//bufPeson.setIssueDatePassport(regOrg.getDatePassport());
													bufPeson.setIssuePlaceNin(regOrg.getAddressNin());
													bufPeson.setNation(regOrg.getNation());
													bufPeson.setNin(nicTran.getNin());
													bufPeson.setNote("");
													bufPeson.setStyleDob(regOrg.getDefDateOfBirth());
													String desPob = regOrg.getPlaceOfBirth();
													if(StringUtils.isNotEmpty(regOrg.getPlaceOfBirth())){
														CodeValues codeV = codesService.getCodeValueByIdName("CODE_BirthPlace", regOrg.getPlaceOfBirth());
														if(codeV != null){
															desPob = codeV.getCodeValueDesc();
														}
													}
													bufPeson.setPobDes(desPob);
													bufPeson.setHandoverA(nicTran.getRecieptNo());
													bufPeson.setInfoPerson(nicTran.getInfoPerson());
													NicListHandover listH = listHandoverService.findHandoverByCriteria(nicTran.getPackageId(), NicListHandover.TYPE_LIST_A, null).getModel();
													if(listH != null){
														try{
															Format formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
															Date dateS = new java.sql.Date(listH.getCreateDate().getTime());
															String s = formatter.format(dateS).split(" ")[0];
															bufPeson.setCreateDateHandover(s);
														}catch(Exception e){ }
														
														try{
															if(StringUtils.isNotEmpty(listH.getCreateBy())){
																Users user = userService.findByUserId(listH.getCreateBy());
																if(user != null){
																	bufPeson.setCreateByHandover(user.getUserName());
																}
																else{
																	bufPeson.setCreateByHandover(listH.getCreateBy());
																}
															}
														}catch(Exception e){ }
													}
													
													if(StringUtils.isNotEmpty(regOrg.getJob())){
														CodeValues codeV = codesService.getCodeValueByIdName("CODE_JOB", regOrg.getJob());
														if(codeV != null){
															bufPeson.setJobs(codeV.getCodeValueDesc());
														}
														else
														{
															bufPeson.setJobs(regOrg.getJob());
														}
													}
													//Thông tin hộ chiếu
													Collection<NicDocumentData> nicDocs = documentDataService.findByTransactionId(request.getTransactionId()).getModel();
													if(nicDocs != null && nicDocs.size() > 0){
														List<NicDocumentData> nicDocs_ = new ArrayList<NicDocumentData>(nicDocs);
														NicDocumentData nicDoc = nicDocs_.get(0);
														bufPeson.setPassportNo(nicDoc.getId().getPassportNo());
														bufPeson.setPassportStatus(nicDoc.getStatus()); //ISSUANCE: phát hành - NONE: tạm khóa
														Format formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
														if(nicDoc.getStatus() != null && nicDoc.getStatus().equals("ISSUANCE"))
															bufPeson.setIssueFlag("Y");
														else
															bufPeson.setIssueFlag("N");
														
														if(nicDoc.getDateOfIssue() != null){
															Date dateS = new java.sql.Date(nicDoc.getDateOfIssue().getTime());
															String s = formatter.format(dateS).split(" ")[0];
															bufPeson.setIssuePassport(s);
														}
														
														if(nicDoc.getDateOfExpiry() != null){
															Date dateS = new java.sql.Date(nicDoc.getDateOfExpiry().getTime());
															String s = formatter.format(dateS).split(" ")[0];
															bufPeson.setExpiredPassport(s);
														}
														
														if(nicDoc.getCreateDatetime() != null){
															Date dateS = new java.sql.Date(nicDoc.getCreateDatetime().getTime());
															String t = formatter.format(dateS).split(" ")[0];
															bufPeson.setPrintDateC(t);
														}
														
														if(nicDoc.getPackageDatetime() != null){
															Date dateS = new java.sql.Date(nicDoc.getPackageDatetime().getTime());
															String t = formatter.format(dateS).split(" ")[0];
															bufPeson.setCreateDateC(t);
														}
														
														SiteRepository site = siteService.getSiteRepoById(nicTran.getIssSiteCode());
														if(site != null)
															bufPeson.setIssuePlacePassport(site.getSiteName());
														
														if(nicTran.getPassportType().equals("PO"))
															bufPeson.setPassportType("CÔNG VỤ");
														else if(nicTran.getPassportType().equals("PD"))
															bufPeson.setPassportType("NGOẠI GIAO");
														else
															bufPeson.setPassportType("PHỔ THÔNG");
														bufPeson.setSerial(nicDoc.getChipSerialNo());
														bufPeson.setIcaoLineOne(nicDoc.getIcaoLine1());
														bufPeson.setIcaoLineTwo(nicDoc.getIcaoLine2());
													}
													bufPeson.setPhone(regOrg.getContactNo());
													bufPeson.setPob(regOrg.getPlaceOfBirth());
													bufPeson.setReligion(regOrg.getReligion());
													bufPeson.setTransacionId(request.getTransactionId());
													
													bufPeson.setFatherLost("N");
													bufPeson.setMotherLost("N");
													bufPeson.setFatherLost(regOrg.getFatherLost());
													bufPeson.setMotherLost(regOrg.getMotherLost());
													
													//Thông tin ng thân
													FamilyData family = new FamilyData();
													
													String fatherName = regOrg.getFatherFullname();
													String motherName = regOrg.getMotherFullname();
													String spouseName =regOrg.getSpouseFullname();
													
													family.setFatherName(fatherName.trim());
													family.setMotherName(motherName.trim());
													family.setSpouseName(spouseName.trim());
													
													if(regOrg.getFatherDateOfBirth() != null){
														family.setFatherDob(regOrg.getFatherDateOfBirth());
													}
													if(regOrg.getMotherDateOfBirth() != null){
														family.setMotherDob(regOrg.getMotherDateOfBirth());
													}
													if(regOrg.getSpouseDateOfBirth() != null){
														family.setSpouseDob(regOrg.getSpouseDateOfBirth());
													}
													
													JAXBContext jaxbContext = JAXBContext.newInstance(FamilyData.class);
													Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
													StringWriter sw = new StringWriter();
													jaxbMarshaller.marshal(family, sw);
													String xmlFamily = sw.toString();
													
													bufPeson.setFamilyData(xmlFamily);
												}
													//}
												//}
												List<NicTransactionPackage> lstPack = nicTransactionPackageService.getPackageNameByTransactionId(request.getTransactionId());
												if(lstPack != null && lstPack.size() > 0){
													for(NicTransactionPackage pack : lstPack){
														if(pack.getTypeList() == 8 && pack.getStage().equals("D")){
															NicListHandover han = listHandoverService.findByPackageId(new NicListHandoverId(pack.getPackageId(), null)).getModel();						
															if(han != null){
																if(han.getCreateDate() != null){
																	Format formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
																	if (han.getCreateDate() != null){
																		Date dateS = new java.sql.Date(han.getCreateDate().getTime());
																		String s = formatter.format(dateS);
																		bufPeson.setApproveDateB(s.split(" ")[0]);
																	}
																	if (han.getUpdateDate() != null){
																		Date dateA = new java.sql.Date(han.getUpdateDate().getTime());
																		String a = formatter.format(dateA);
																		bufPeson.setRequestDateB(a.split(" ")[0]);
																	}
																}
															}
															bufPeson.setApproveNoteB(pack.getNoteLeaderApprove());
															bufPeson.setRequestNoteB(pack.getNoteApprove());
														}
													}
												}
												
												/*if (nicTran.getLeaderOfficerId() != null){
													Users user_ = userService.findByUserId(nicTran.getLeaderOfficerId());
													if(user_ != null){
														bufPeson.setApproveUserB(user_.getUserName());
													} else {
														bufPeson.setApproveUserB(nicTran.getLeaderOfficerId());
													}
												}*/
												if (uploadJobOb.getInvestigationOfficerId() != null){
													Users user_ = userService.findByUserId(uploadJobOb.getInvestigationOfficerId());
													if(user_ != null){
														bufPeson.setRequestUserB(user_.getUserName());
													} else {
														bufPeson.setRequestUserB(uploadJobOb.getInvestigationOfficerId());
													}
												}
												
												//Lấy dữ liệu ảnh/tài liệu đính kèm/vân tay
												List<BufEppPersonDoc> bufDocList = new ArrayList<BufEppPersonDoc>();
												List<NicTransactionAttachment> docList = nicTransactionAttachmentService
														.getNicTransactionAttachments(
																request.getTransactionId(),
																new String[] { NicTransactionAttachment.DOC_TYPE_PHOTO_CAPTURE, 
																	NicTransactionAttachment.DOC_TYPE_FINGERPRINT,
																	NicTransactionAttachment.DOC_TYPE_ENROLLED_SUB },
																null);
												if(docList != null && docList.size() > 0){
													for(NicTransactionAttachment doc : docList){
														BufEppPersonDoc bufDoc = new BufEppPersonDoc();
														bufDoc.setCreateBy("SYSTEM");
														bufDoc.setCreateDateTime(new Date());
														bufDoc.setCreateWkstnID("SYSTEM-WKST");
														bufDoc.setDocData(doc.getDocData());
														bufDoc.setDocType(doc.getDocType());
														bufDoc.setSerial(doc.getSerialNo());
														bufDoc.setPersonIdDoc(doc.getRemarks());
														bufDoc.setTransacionId(request.getTransactionId());
														bufDocList.add(bufDoc);
													}
												}
												
												BufEppPersonInvestigation bufInves = new BufEppPersonInvestigation();
												com.nec.asia.nic.comp.trans.domain.BufEppPersonInvestigation bufInvesDomain = bufPersonInvestigationService.findByTranId(request.getTransactionId());
												if(bufInvesDomain != null){
													///bufInves.setDataHistoryPassport(bufInvesDomain.getDataHistoryPassport());
													bufInves.setCreateBy(bufInvesDomain.getCreateBy());
													bufInves.setCreateDateTime(new Date());
													//bufInves.setDataImmihistory(bufInvesDomain.getDataImmihistory());
													//bufInves.setDataFamily(bufInvesDomain.getDataFamily());
													//bufInves.setDataInfo(bufInvesDomain.getDataInfoDocument());
													bufInves.setTransacionId(request.getTransactionId());
													bufInves.setSfDob(regOrg.getFatherDefDateOfBirth());
													bufInves.setSmDob(regOrg.getMotherDefDateOfBirth());
													bufInves.setSsDob(regOrg.getSpouseDefDateOfBirth());
												}
												
												if(bufDocList != null && bufDocList.size() > 0){
													for(BufEppPersonDoc doc : bufDocList){
														bufPeson.getBufEppPersonDoc().add(doc);
													}	
												}
												
												bufPeson.setBufEppPersonInvestigation(bufInves);
												//result.getBufEppPerson().add(bufPeson);
											}
										}
									}
								}
								break;
							}
						}
					}
					result.getBufEppPerson().add(bufPeson);
					
					if(StringUtils.isNotEmpty(uploadJob.getNicTransaction().getPersonCode())){
						//Lấy dữ liệu hồ sơ khớp
						//Tìm dữ liệu registration Data của orginalId
						try{
							NicRegistrationData regOrg = uploadJob.getNicTransaction().getNicRegistrationData();
							if(regOrg != null){
								bufPeson.setAddressResident(regOrg.getAddressLine1());
								bufPeson.setCreateBy("SYSTEM");
								bufPeson.setCreateDateTime(new Date());
								bufPeson.setCreateWkstnID("SYSTEM");
								bufPeson.setDob(regOrg.getDateOfBirth());
								bufPeson.setFullname(regOrg.getSurnameLine1());
								bufPeson.setGender(regOrg.getGender());
								bufPeson.setIssueDateNin(regOrg.getDateNin());
								//bufPeson.setIssueDatePassport(regOrg.getDatePassport());
								bufPeson.setIssuePlaceNin(regOrg.getAddressNin());
								bufPeson.setNation(regOrg.getNation());
								bufPeson.setNin(uploadJob.getNicTransaction().getNin());
								bufPeson.setNote("");
							}
						}
						catch(Exception e){
							//logger.error("RegistrationData-Error: " + e.getMessage());
						}
					}
				}
			}
			
			rep.setResponse(result);
			prModel.setMessage("success");
			prModel.setCode(99);
			
			/* Lưu bảng thống kê truyền nhận*/
			if (result.getBufEppPerson().size() > 0) {
				this.saveOrUpRptData(Contants.URL_DOWNLOAD_BUF_PERSON_SYNCNIC, 1, null);
			}
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			prModel.setMessage(e.getMessage());
			e.printStackTrace();
		}

		rep.setProperty(prModel);
		return rep;
	}
	
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("/downloadListBufIdPerson")
	public ResponseBase<BufEppListRequest> downloadListBufIdPerson(TransactionPerso tranx) {
		PropertyModel prModel = new PropertyModel();
		prModel.setCode(-1);
		prModel.setMessage("Null data");
		ResponseBase<BufEppListRequest> rep = new ResponseBase<BufEppListRequest>();
		BufEppListRequest result = new BufEppListRequest();
		result.setResultCheck("N");
		try{
			if(StringUtils.isNotEmpty(tranx.getTransactionId())){
				//Kiểm tra kết quả trả về AFIS và CPD đã có chưa
				BaseModelSingle<NicUploadJob> bGetUJ = uploadJobService.getUploadJobByTransactionIdSinger(null, tranx.getTransactionId());
				NicUploadJob listUpload = bGetUJ.getModel();
				if(listUpload != null){
					NicUploadJob uploadJob = listUpload;
					if(StringUtils.isNotEmpty(uploadJob.getInvestigationStatus())){
						result.setResultCheck("Y");
						List<RptStatisticsTransmitData> lstRptKQ = rptStatisticsTransmitDataService.findBySiteCode("A08");
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
						
						//Danh sách BMS / CPD nghi trùng
						Long idCPD = null;
						Long idBMS = null;
						
						List<NicSearchResult> searchResults = nicSearchResultDao.findAllByJobId(uploadJob.getWorkflowJobId());
						if(searchResults != null && searchResults.size() > 0) {
							for(NicSearchResult sR_ : searchResults){
								if(sR_.getTypeSearch().equals(NicSearchResult.TYPE_SEARCH__CPD) && sR_.getHasHit()){
									idCPD = sR_.getSearchResultId();
								}
								else if(sR_.getTypeSearch().equals(NicSearchResult.TYPE_SEARCH__FP_1_N) && sR_.getHasHit() != null && sR_.getHasHit()){
									idBMS = sR_.getSearchResultId();
								}
							}
						}
						
						if(idCPD == null && idBMS == null){
							//logger.info("==== Null data check AFIS");
						}
						else{
							if(idCPD != null){
								List<NicHitTransaction> mappingCPD = new ArrayList<NicHitTransaction>();
								List<NicHitTransaction> listHitCPD = nicSearchHitResultService.findHitBySource(uploadJob.getTransactionId(), "CPD", uploadJob.getNicTransaction().getRegSiteCode(), idCPD);
								if(listHitCPD != null && listHitCPD.size() > 0){
									for(NicHitTransaction hits : listHitCPD){
										if(mappingCPD == null || mappingCPD.size() < 1){
											mappingCPD.add(hits);
										}
										else
										{
											Boolean exist = false;
											Boolean samePersonId = true;
											for(NicHitTransaction map : mappingCPD){
												if(map.getTranid().equals(hits.getTranid())){
													exist = true;
													break;
												} else {
													if(map.getPersonId() != null && hits.getPersonId() != null){
														EppPerson personHit = eppPersonService.getPersonById(hits.getPersonId());
														EppPerson personMap =  eppPersonService.getPersonById(map.getPersonId());
														/*if(personHit != null && personMap != null && personHit.getOriginId() != null && personMap.getOriginId() != null && personMap.getOriginId().equals(personHit.getOriginId())){
															NicUploadJob nicHit = uploadJobService.getUploadJobByTransactionIdSinger(null, hits.getTranid());
															NicUploadJob nicSame = uploadJobService.getUploadJobByTransactionIdSinger(null, map.getTranid());
															exist = true;
															if(nicHit.getNicTransaction().getDateOfApplication().after(nicSame.getNicTransaction().getDateOfApplication())) {
																mappingCPD.add(hits);
																mappingCPD.remove(map);
																break;
															}
														}*/
													}
												}
											}
											if(!exist || !samePersonId)
												mappingCPD.add(hits);
										}
									}
								}
							
								List<NicHitTransaction> mapHit = new ArrayList<NicHitTransaction>();
								for(NicHitTransaction map : mappingCPD) {
									if(map.getPersonId() != null) {
										mapHit = findPersonOrginal(map.getPersonId());
									}
								}
								if(mapHit != null && mapHit.size() > 0){
									for(NicHitTransaction map : mapHit){
										for(NicHitTransaction mapCPD : mappingCPD){
											if(!map.getTranid().equals(mapCPD.getTranid())){
												mappingCPD.add(map);
											}
										}
									}
								}
								if(mappingCPD.size() > 0) {
									for (NicHitTransaction entry : mappingCPD) {
										//Lấy Id của đối tượng trùng
										BufEppListResponse res = new BufEppListResponse();
										res.setIdHitSearchCPD(entry.getHitId());
										res.setTranId(entry.getTranid());
										result.getBufEppListResponse().add(res); 	
							        }
								}
							}
							
							logger.info("CHECKING WITH BMS=================");
							///Dữ liệu kiểm tra với BMS
							if(idBMS != null){
								//Lấy dữ liệu trùng trong bảng EPP_SEARCH_HIT_RESULT
								/*List<String> listHitCPD = null;//nicSearchHitResultService.findHitBySource(tranx.getTransactionId(), "BMS");
								if(listHitCPD != null && listHitCPD.size() > 0){
									for(String hits : listHitCPD){
										List<NicSearchHitResult> lstSr = nicSearchHitResultService.findHitPositions(hits, idBMS); 
										NicUploadJob nicHit = uploadJobService.getUploadJobByTransactionIdSinger(null, hits);
										if(nicHit != null){
											if(nicHit != null && nicHit.getNicTransaction().getRegSiteCode().equals(listUpload.getNicTransaction().getRegSiteCode())){
												if(lstSr != null && lstSr.size() > 0){
													Long hitId = lstSr.get(0).getHitResultId();
													mappingBMS.put(hits, hitId);
													
													if (mappingPersonBMS.size() > 0){
														if(!mappingPersonBMS.containsValue(nicHit.getOriginalyPersonId())){
															mappingPersonBMS.put(nicHit.getTransactionId(), nicHit.getOriginalyPersonId());
														} else {
															String sameValue = "";
															for (Entry<String, Long> entry : mappingPersonBMS.entrySet()) {
													            if (entry.getValue() != null && entry.getValue().equals(nicHit.getOriginalyPersonId())) {
													            	sameValue = entry.getKey();
													            	NicUploadJob nicSame = uploadJobService.getUploadJobByTransactionIdSinger(null, sameValue);
																	if(nicHit.getNicTransaction().getDateOfApplication().before(nicSame.getNicTransaction().getDateOfApplication())) {
																		mappingPersonBMS.put(nicHit.getTransactionId(), nicHit.getOriginalyPersonId());
																		mappingPersonBMS.remove(sameValue);
																	}
													            }
													        }
														}
													} else {
														mappingPersonBMS.put(nicHit.getTransactionId(), nicHit.getOriginalyPersonId());
													}
												}	
											}
										}
									}
								}*/
								
								List<NicHitTransaction> mappingBMS = new ArrayList<NicHitTransaction>();
								List<NicHitTransaction> listHitBMS = nicSearchHitResultService.findHitBySource(uploadJob.getTransactionId(), "BMS", uploadJob.getNicTransaction().getRegSiteCode(), idBMS);
								if(listHitBMS != null && listHitBMS.size() > 0){
									for(NicHitTransaction hits : listHitBMS){
										if(mappingBMS == null || mappingBMS.size() < 1){
											mappingBMS.add(hits);
										}
										else
										{
											Boolean exist = false;
											Boolean samePersonId = true;
											for(NicHitTransaction map : mappingBMS){
												if(map.getTranid().equals(hits.getTranid())){
													exist = true;
													break;
												} else {
													if(map.getPersonId() != null && hits.getPersonId() != null){
														EppPerson personHit = eppPersonService.getPersonById(hits.getPersonId());
														EppPerson personMap =  eppPersonService.getPersonById(map.getPersonId());
														/*if(personHit != null && personMap != null && personHit.getOriginId() != null && personMap.getOriginId() != null && personMap.getOriginId().equals(personHit.getOriginId())){
															NicUploadJob nicHit = uploadJobService.getUploadJobByTransactionIdSinger(null, hits.getTranid());
															NicUploadJob nicSame = uploadJobService.getUploadJobByTransactionIdSinger(null, map.getTranid());
															exist = true;
															if(nicHit.getNicTransaction().getDateOfApplication().after(nicSame.getNicTransaction().getDateOfApplication())) {
																mappingBMS.add(hits);
																mappingBMS.remove(map);
																break;
															}
														}*/
													}
												}
											}
											if(!exist || !samePersonId)
												mappingBMS.add(hits);
										}
									}
								}
							
								List<NicHitTransaction> mapHit = new ArrayList<NicHitTransaction>();
								for(NicHitTransaction map : mappingBMS){
									if(map.getPersonId() != null){
										mapHit = findPersonOrginal(map.getPersonId());
									}
								}
								
								if(mapHit != null && mapHit.size() > 0){
									for(NicHitTransaction map : mapHit){
										for(NicHitTransaction mapBMS : mappingBMS){
											if(!map.getTranid().equals(mapBMS.getTranid())){
													mappingBMS.add(map);
											}
										}
									}
								}
								
								if(mappingBMS.size() > 0){
									for (NicHitTransaction entry : mappingBMS) {
										//Lấy Id của đối tượng trùng
						            	Boolean existId = false;
										//Kiểm tra trong ListCheckCPD có trùng transactionID không. Nếu có thì chỉ thêm dữ liệu BMS vào vị trí đối tượng
										if(result != null && result.getBufEppListResponse().size() > 0){
											for(BufEppListResponse res : result.getBufEppListResponse()){
												if(res.getTranId().equals(entry.getTranid()))
												{
													existId = true;
													res.setIdHitSearchBMS(entry.getHitId());
												}
											}
										}
										if(!existId){
											BufEppListResponse res = new BufEppListResponse();
											res.setIdHitSearchBMS(entry.getHitId());
											res.setTranId(entry.getTranid());
											result.getBufEppListResponse().add(res);
										}  	
							        }
								}
							}
							else
							{
								logger.info("Not found list hit BMS by idBMS: " + idBMS);
							}
						}
					}
					else{
						//logger.info("Not found result BMS/CPD");
					}
				}
				else
				{
					//logger.info("Not found transactionId" );
				}
			}
			rep.setResponse(result);
			prModel.setMessage("success");
			prModel.setCode(99);
			
			/* Lưu bảng thống kê truyền nhận*/
			if (result.getBufEppListResponse().size() > 0) {
				this.saveOrUpRptData(Contants.URL_DOWNLOAD_LIST_BUF_PERSON, 1, null);
			}
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			prModel.setMessage(e.getMessage());
			e.printStackTrace();
		}
	
		rep.setProperty(prModel);
		return rep;
	}
	
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("/dataResponseProcess")
	public ResponseBase<Boolean> dataResponseProcess(String json) {
		ObjectMapper mapper = new ObjectMapper();
		TransactionSyncRequest request = new TransactionSyncRequest();
		PropertyModel prModel = new PropertyModel();
		prModel.setCode(-1);
		prModel.setMessage("Null data");
		ResponseBase<Boolean> rep = new ResponseBase<Boolean>();
		rep.setResponse(false);
		BufEppListRequest result = new BufEppListRequest();
		result.setResultCheck("N");
		try{
			request = mapper.readValue(json, TransactionSyncRequest.class);
			if(request.getHandover() != null && request.getHandover().size() > 0){
				for(NicListHandoverDto dto : request.getHandover()){
					NicListHandover han = listHandoverService.findByPackageId(new NicListHandoverId(dto.getPackageId(), dto.getTypeList())).getModel();
					NicListHandover han_ = new NicListHandover();
					if(han != null)
						han_ = mapperHandover(han, dto);
					else
						han_ = mapperHandover(null, dto);
					//han_.setStatusSyncXl(true);
					listHandoverService.saveOrUpdate(han_);
				}
			}
			
			if(request.getListHandover() != null && request.getListHandover().size() > 0){
				for(NicTransactionPackage dto : request.getListHandover()){
					try{
						NicTransactionPackage pack_ = nicTransactionPackageService.getListPackageByPackageIdAndTranID(dto.getPackageId(), dto.getTransactionId());
						if(pack_ == null)
							dto.setId(null);
						else
							dto.setId(pack_.getId());
						nicTransactionPackageService.insertDataTable(dto);
					}
					catch(Exception e){
						prModel.setMessage(e.getMessage());
					}
				}
			}
			
			if(request.getListLog() != null && request.getListLog().size() > 0){
				for(NicTransactionLog dto: request.getListLog()){
					try{
						nicTransactionLogDao.saveOrUpdate(dto);
					}catch(Exception e){
						prModel.setMessage(e.getMessage());
					}
				}
			}
			
			if(request.getListPdetail() != null && request.getListPdetail().size() > 0){
				for(NicTransactionPaymentDetail dto: request.getListPdetail()){
					try{
						List<NicTransactionPaymentDetail> lstPayment = nicTransactionPaymentDetailDao.findListDetailPaymentByTransactionId(dto.getPaymentId(), dto.getTypePayment(), dto.getSubTypePayment(), null).getListModel();
						if(lstPayment != null && lstPayment.size() > 0){
							dto.setId(lstPayment.get(0).getId());
						}
						nicTransactionPaymentDetailDao.saveOrUpdate(dto);
					}catch(Exception e){
						prModel.setMessage(e.getMessage());
					}
				}
			}
			
			if(request.getNicUp() != null){
				Long idPerson = null;
				Boolean createPerson = false;
				Long idOrginal = null;
				BaseModelSingle<NicUploadJob> bGetUJ = uploadJobService.getUploadJobByTransactionIdSinger(null, request.getNicUp().getTransactionId());
				NicUploadJob upJob_ = bGetUJ.getModel();
				NicUploadJob upjob = mapperNicUploadJob(upJob_, request.getNicUp());
				if(upjob.getInvestigationStatus().equals(NicUploadJob.RECORD_STATUS_APPROVED) && request.getPersonFlag()){
					//Truy vấn dữ liệu khớp với đối tượng
					if(StringUtils.isNotEmpty(upjob.getNicTransaction().getPersonCode())){ // Nếu có kiểm tra PersonId đối tượng
						BaseModelSingle<NicUploadJob> bGetUJob = uploadJobService.getUploadJobByTransactionIdSinger(null, upjob.getNicTransaction().getPersonCode());
						NicUploadJob uploadJob = bGetUJob.getModel();
						if(uploadJob != null){
							NicRegistrationData regis = nicRegistrationDataService.getNicDataById(uploadJob.getTransactionId());
							String fullname = removeAccent(regis.getSurnameLine1().toUpperCase());
							String gender = regis.getGender().equals("M") ? "MALE" : "FEMALE";
							String patternJ = "dd-MMM-yyyy";
							DateFormat df = new SimpleDateFormat(patternJ);      
							// Using DateFormat format method we can create a string 
							// representation of a date with the defined format.
							String dob = regis.getPrintDob();
							String pob = regis.getPlaceOfBirth();
							//Kiểm tra đối tượng có PersonId ko
							//Nếu không có tạo một PersonID cho đối tượng
							if(uploadJob.getNicTransaction().getPersonCode() == null){
									EppPerson newPer = new EppPerson();
									newPer.setDateOfBirth(dob);
									newPer.setCreateTs(new Date());
									String issueDateNin = "";
									if(regis.getDateNin() != null)
										issueDateNin= df.format(regis.getDateNin());
									newPer.setEthnic(regis.getNation());
									newPer.setGender(gender);
									newPer.setIdNumber(uploadJob.getNicTransaction().getNin());
									newPer.setName(regis.getSurnameLine1());
									/*newPer.setDateOfIdIssue(HelperClass.convertMonthWordToMonthNumber1(issueDateNin));
									newPer.setNationalityId(regis.getNationality());
									newPer.setOccupation(regis.getJob());
									newPer.setOfficeInfo(regis.getAddressCompany());
									newPer.setPlaceOfBirthId(pob);*/
									newPer.setReligion(regis.getReligion());
									String qh = "";
									String tt = "";
									if(StringUtils.isNotEmpty(regis.getAddressLine4())){
										CodeValues codeV = codesService.getCodeValueByIdName(Codes.TOWN_VILLAGE, regis.getAddressLine4());
										if(codeV != null){
											qh = ", " + codeV.getCodeValueDesc();
										}
									}
									if(StringUtils.isNotEmpty(regis.getAddressLine4())){
										CodeValues codeV = codesService.getCodeValueByIdName(Codes.DISTRICT, regis.getAddressLine4());
										if(codeV != null){
											tt = ", " + codeV.getCodeValueDesc();
										}
									}
									String address = regis.getAddressLine1() + qh + tt;
									/*newPer.setPlaceIfIdIssueId(regis.getAddressNin());
									newPer.setResidentAddress(address);
									newPer.setResidentAreaId(regis.getAddressLine5());
									newPer.setResidentPlaceId(regis.getAddressLine4());
									newPer.setSearchName(removeAccent(regis.getSurnameLine1().toUpperCase()));
									newPer.setTmpAddress(regis.getAddressTempStreet());
									newPer.setTmpPlaceId(regis.getAddressTempDistrict());
									newPer.setTmpAreaId(regis.getAddressTempCity());
									newPer.setFpFlag("N");*/
									Collection<NicDocumentData> nicDocs;
									try {
										nicDocs = documentDataService.findByTransactionId(uploadJob.getTransactionId()).getModel();
										if(nicDocs != null && nicDocs.size() > 0){
											List<NicDocumentData> nicDocs_ = new ArrayList<NicDocumentData>(nicDocs);
											NicDocumentData nicDoc = nicDocs_.get(0);
											//newPer.setPassportNo(nicDoc.getId().getPassportNo());
										}
										List<NicTransactionAttachment> docFp = nicTransactionAttachmentService.getNicTransactionAttachments(
												uploadJob.getTransactionId(), new String[] {NicTransactionAttachment.DOC_TYPE_FINGERPRINT}, null);
										if(docFp != null && docFp.size() > 0){
											//newPer.setFpFlag("Y");
										}
											
									} catch (Exception e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
									}
									eppPersonService.saveOrUpdateData(newPer);
									
									List<EppPerson> lstP = eppPersonService.findAllEppPerson2(fullname, gender, dob, pob);
									if(lstP != null && lstP.size() > 0){
										idPerson = lstP.get(0).getPersonId();
										
										//Cập nhật lại GlobalID
										EppPerson pGlobal = lstP.get(0);
										//pGlobal.setGlobalId(idPerson);
										eppPersonService.saveOrUpdateData(pGlobal);
									}
									
									try {
										List<NicTransactionAttachment> docList = nicTransactionAttachmentService
												.getNicTransactionAttachments(
														uploadJob.getTransactionId(),
														new String[] { NicTransactionAttachment.DOC_TYPE_PHOTO_CAPTURE, 
															NicTransactionAttachment.KEY_SCAN_DOCUMENT },
														null);
										if(docList != null && docList.size() > 0){
											for(NicTransactionAttachment doc : docList){
												//Kiểm tra 
												EppPersonAttchmnt attchmnt = new EppPersonAttchmnt();
												attchmnt.setFileName(doc.getDocType());
												attchmnt.setPersonId(idPerson);
												attchmnt.setSerialNo(Integer.parseInt(doc.getSerialNo()));
												attchmnt.setType_(doc.getDocType());
												attchmnt.setBase64(Base64.encodeBase64String(doc.getDocData()));
												eppPersonService.saveOrUpdateDataAttchmnt(attchmnt);
											}
										}
									} catch (Exception e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
									}

									try {
										//Thong tin nguoi than
										//Thông tin bố
										if(StringUtils.isNotEmpty(regis.getFatherFullname())){
											EppPersonFamily family = new EppPersonFamily();
											family.setCreatedBy("SYSTEM");
											family.setCreateTs(new Date());
											family.setName(regis.getFatherFullname());
											family.setGender("M");
											family.setRelationship("1"); //Bố
											family.setSubjectPerson(idPerson);
											if(StringUtils.isNotEmpty(regis.getFatherDefDateOfBirth())){
												if(regis.getFatherDateOfBirth() != null){
													SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
													String dobf = sdf.format(regis.getFatherDateOfBirth());
													family.setDateOfBirth(dobf);
												}
											}
											family.setLost(regis.getFatherLost());
											family.setPlaceOfBirthCode("Không xác định");
											eppPersonService.saveOrUpdateDataFamily(family);
										}
										//Thông tin mẹ
										if(StringUtils.isNotEmpty(regis.getMotherFullname())){
											EppPersonFamily family = new EppPersonFamily();
											family.setCreatedBy("SYSTEM");
											family.setCreateTs(new Date());
											family.setName(regis.getMotherFullname());
											family.setGender("F");
											family.setRelationship("2"); //Mẹ
											family.setSubjectPerson(idPerson);
											if(StringUtils.isNotEmpty(regis.getMotherDefDateOfBirth())){
												if(regis.getMotherDateOfBirth() != null){
													SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
													String dobm = sdf.format(regis.getMotherDateOfBirth());
													family.setDateOfBirth(dobm);
												}
											}
											family.setLost(regis.getMotherLost());
											family.setPlaceOfBirthCode("Không xác định");
											eppPersonService.saveOrUpdateDataFamily(family);
										}
										
										//Thông tin vợ/chồng
										if(StringUtils.isNotEmpty(regis.getSpouseFullname())){
											EppPersonFamily family = new EppPersonFamily();
											family.setCreatedBy("SYSTEM");
											family.setCreateTs(new Date());
											family.setName(regis.getSpouseFullname());
											if(regis.getGender().equals("M")){
												family.setGender("F");
												family.setRelationship("3"); //Vợ
											}
											else{
												family.setGender("M");
												family.setRelationship("4"); //Chồng
											}
											family.setSubjectPerson(idPerson);
											if(StringUtils.isNotEmpty(regis.getSpouseDefDateOfBirth())){
												if(regis.getSpouseDateOfBirth() != null){
													SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
													String dobs = sdf.format(regis.getSpouseDateOfBirth());
													family.setDateOfBirth(dobs);
												}
											}
											family.setPlaceOfBirthCode("Không xác định");
											eppPersonService.saveOrUpdateDataFamily(family);
										}
										
									} catch (Exception e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
									}
									
									//uploadJob.setOriginalyPersonId(null);
									uploadJobService.saveOrUpdate(uploadJob);
								}
								else {
									//idPerson = uploadJob.getOriginalyPersonId();
								}
							
								//Kiểm tra person có tồn tại
								EppPerson person = eppPersonService.getPersonById(idPerson);	
								if(person != null){
									NicRegistrationData regisMain = upjob.getNicTransaction().getNicRegistrationData();
									String fullnameMain = removeAccent(regisMain.getSurnameLine1().toUpperCase());
									String genderMain = regisMain.getGender().equals("M") ? "MALE" : "FEMALE";     
									String dobMain = regisMain.getPrintDob();
									String pobMain = regisMain.getPlaceOfBirth();
									/*if(!fullnameMain.equals(person.getSearchName().toUpperCase()) || !genderMain.equals(person.getGender())
											|| !dobMain.equals(person.getDateOfBirth()) || !pobMain.equals(person.getPlaceOfBirthId())){
										idOrginal = uploadJob.getOriginalyPersonId();
										idPerson = null;
										createPerson = true;
									}
									else{
										idOrginal = null;
										idPerson = uploadJob.getOriginalyPersonId();
									}*/
								}
								else
									createPerson = true;
						}
						else {
							createPerson = true;
						}
					}
					else {
						createPerson = true;
					}
					
					if(createPerson){
						NicRegistrationData regis = upjob.getNicTransaction().getNicRegistrationData();
						String fullname = removeAccent(regis.getSurnameLine1().toUpperCase());
						String gender = regis.getGender().equals("M") ? "MALE" : "FEMALE";
						String pattern = "dd-MMM-yyyy";
						DateFormat df = new SimpleDateFormat(pattern);      
						// Using DateFormat format method we can create a string 
						// representation of a date with the defined format.
						String dob = regis.getPrintDob();
						String pob = regis.getPlaceOfBirth();
						EppPerson newPer = new EppPerson();
						newPer.setDateOfBirth(dob);
						newPer.setCreateTs(new Date());
						String issueDateNin = "";
						if(regis.getDateNin() != null)
							issueDateNin= df.format(regis.getDateNin());
						/*newPer.setDateOfIdIssue(HelperClass.convertMonthWordToMonthNumber1(issueDateNin));
						newPer.setEthnic(regis.getNation());
						newPer.setGender(gender);
						newPer.setIdNumber(upjob.getNicTransaction().getNin());
						newPer.setName(regis.getSurnameLine1());
						newPer.setNationalityId(regis.getNationality());
						newPer.setOccupation(regis.getJob());
						newPer.setOfficeInfo(regis.getAddressCompany());
						newPer.setPlaceOfBirthId(pob);*/
						newPer.setReligion(regis.getReligion());
						String qh = "";
						String tt = "";
						if(StringUtils.isNotEmpty(regis.getAddressLine4())){
							CodeValues codeV = codesService.getCodeValueByIdName(Codes.TOWN_VILLAGE, regis.getAddressLine4());
							if(codeV != null){
								qh = ", " + codeV.getCodeValueDesc();
							}
						}
						if(StringUtils.isNotEmpty(regis.getAddressLine4())){
							CodeValues codeV = codesService.getCodeValueByIdName(Codes.DISTRICT, regis.getAddressLine4());
							if(codeV != null){
								tt = ", " + codeV.getCodeValueDesc();
							}
						}
						String address = regis.getAddressLine1() + qh + tt;
						/*newPer.setPlaceIfIdIssueId(regis.getAddressNin());
						newPer.setResidentAddress(address);
						newPer.setResidentAreaId(regis.getAddressLine5());
						newPer.setResidentPlaceId(regis.getAddressLine4());
						newPer.setSearchName(removeAccent(regis.getSurnameLine1().toUpperCase()));
						newPer.setTmpAddress(regis.getAddressTempStreet());
						newPer.setTmpPlaceId(regis.getAddressTempDistrict());
						newPer.setTmpAreaId(regis.getAddressTempCity());
						newPer.setFpFlag("N");
						newPer.setOriginId(idOrginal);*/
						Collection<NicDocumentData> nicDocs;
						try {
							nicDocs = documentDataService.findByTransactionId(upjob.getTransactionId()).getModel();
							if(nicDocs != null && nicDocs.size() > 0){
								List<NicDocumentData> nicDocs_ = new ArrayList<NicDocumentData>(nicDocs);
								NicDocumentData nicDoc = nicDocs_.get(0);
								//newPer.setPassportNo(nicDoc.getId().getPassportNo());
							}
							List<NicTransactionAttachment> docFp = nicTransactionAttachmentService.getNicTransactionAttachments(
									upjob.getTransactionId(), new String[] {NicTransactionAttachment.DOC_TYPE_FINGERPRINT}, null);
							if(docFp != null && docFp.size() > 0){
								//newPer.setFpFlag("Y");
							}
								
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						eppPersonService.saveOrUpdateData(newPer);
						
						List<EppPerson> lstP = eppPersonService.findAllEppPerson2(fullname, gender, dob, pob);
						if(lstP != null && lstP.size() > 0){
							idPerson = lstP.get(0).getPersonId();
							
							//Cập nhật lại GlobalID
							EppPerson pGlobal = lstP.get(0);
							//pGlobal.setGlobalId(idPerson);
							eppPersonService.saveOrUpdateData(pGlobal);
						}
						
						try {
							List<NicTransactionAttachment> docList = nicTransactionAttachmentService
									.getNicTransactionAttachments(
											upjob.getTransactionId(),
											new String[] { NicTransactionAttachment.DOC_TYPE_PHOTO_CAPTURE, 
												NicTransactionAttachment.KEY_SCAN_DOCUMENT},
											null);
							if(docList != null && docList.size() > 0){
								for(NicTransactionAttachment doc : docList){
									EppPersonAttchmnt attchmnt = new EppPersonAttchmnt();
									attchmnt.setFileName(doc.getDocType());
									attchmnt.setPersonId(idPerson);
									attchmnt.setSerialNo(Integer.parseInt(doc.getSerialNo()));
									attchmnt.setType_(doc.getDocType());
									attchmnt.setBase64(Base64.encodeBase64String(doc.getDocData()));
									eppPersonService.saveOrUpdateDataAttchmnt(attchmnt);
								}
							}
							
							try {
								//Thong tin nguoi than
								//Thông tin bố
								if(StringUtils.isNotEmpty(regis.getFatherFullname())){
									EppPersonFamily family = new EppPersonFamily();
									family.setCreatedBy("SYSTEM");
									family.setCreateTs(new Date());
									family.setName(regis.getFatherFullname());
									family.setGender("M");
									family.setRelationship("1"); //Bố
									family.setSubjectPerson(idPerson);
									if(StringUtils.isNotEmpty(regis.getFatherDefDateOfBirth())){
										if(regis.getFatherDateOfBirth() != null){
											SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
											String dobf = sdf.format(regis.getFatherDateOfBirth());
											family.setDateOfBirth(dobf);
										}
									}
									family.setLost(regis.getFatherLost());
									family.setPlaceOfBirthCode("Không xác định");
									eppPersonService.saveOrUpdateDataFamily(family);
								}
								//Thông tin mẹ
								if(StringUtils.isNotEmpty(regis.getMotherFullname())){
									EppPersonFamily family = new EppPersonFamily();
									family.setCreatedBy("SYSTEM");
									family.setCreateTs(new Date());
									family.setName(regis.getMotherFullname());
									family.setGender("F");
									family.setRelationship("2"); //Mẹ
									family.setSubjectPerson(idPerson);
									if(StringUtils.isNotEmpty(regis.getMotherDefDateOfBirth())){
										if(regis.getMotherDateOfBirth() != null){
											SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
											String dobm = sdf.format(regis.getMotherDateOfBirth());
											family.setDateOfBirth(dobm);
										}
									}
									family.setLost(regis.getMotherLost());
									family.setPlaceOfBirthCode("Không xác định");
									eppPersonService.saveOrUpdateDataFamily(family);
								}
								
								//Thông tin vợ/chồng
								if(StringUtils.isNotEmpty(regis.getSpouseFullname())){
									EppPersonFamily family = new EppPersonFamily();
									family.setCreatedBy("SYSTEM");
									family.setCreateTs(new Date());
									family.setName(regis.getSpouseFullname());
									if(regis.getGender().equals("M")){
										family.setGender("F");
										family.setRelationship("3"); //Vợ
									}
									else{
										family.setGender("M");
										family.setRelationship("4"); //Chồng
									}
									family.setSubjectPerson(idPerson);
									if(StringUtils.isNotEmpty(regis.getSpouseDefDateOfBirth())){
										if(regis.getSpouseDateOfBirth() != null){
											SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
											String dobs = sdf.format(regis.getSpouseDateOfBirth());
											family.setDateOfBirth(dobs);
										}
									}
									family.setPlaceOfBirthCode("Không xác định");
									eppPersonService.saveOrUpdateDataFamily(family);
								}
								
							} catch (Exception e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				}
				
				//upjob.setOriginalyPersonId(null);
				uploadJobService.saveOrUpdate(upjob);
			}
			
			if(request.getPayment() != null){
				NicTransactionPayment pay = nicTransactionPaymentDao.findGetPaymentByTransaction(request.getPayment().getTransactionId()).getModel();
				NicTransactionPayment pay_ = mapperNicPayment(pay, request.getPayment());
				nicTransactionPaymentDao.saveOrUpdate(pay_);
			}
			
			if(request.getTran() != null){
				NicTransaction tran = nicTransactionService.getNicTransactionById(request.getTran().getTransactionId()).getModel();
				NicTransaction tran_ = mapperNicTransaction(tran, request.getTran());
				nicTransactionService.saveOrUpdate(tran_);
			}
			
			rep.setResponse(true);
			prModel.setMessage("success");
			prModel.setCode(99);
			
			/* Lưu bảng thống kê truyền nhận*/
			this.saveOrUpRptData(Contants.URL_DATA_RESPONSE_PROCESS, 1, null);
		
		} catch (Exception e) {
			// TODO Auto-generated catch block
			prModel.setMessage(e.getMessage());
			e.printStackTrace();
		}
	
		rep.setProperty(prModel);
		return rep;
	}
	
	private NicListHandover mapperHandover(NicListHandover result, NicListHandoverDto han){
		if(result == null)
			result = new NicListHandover();
//		result.setArchiveCode(han.getArchiveCode());
		result.setCountTransaction(han.getCountTransaction());
		result.setCreateBy(han.getCreateBy());
		result.setCreateDate(han.getCreateDate());
		//result.setDescription(han.getDescription());
		result.setHandoverStage(han.getHandoverStage());
		//result.setIsSyncPerso(han.getIsSyncPerso());
		//result.setLeaderId(han.getLeaderId());
		//result.setNumberTran(1);
		result.setId(new NicListHandoverId(han.getPackageId(), han.getTypeList()));
//		result.setPackageId(han.getPackageId());
		result.setPackageName(han.getPackageName());
		//result.setResultPlace(han.getResultPlace());
		result.setSiteCode(han.getSiteCode());
		result.setHandoverStatus(han.getStatus());
		//result.setTransactionId(han.getTransactionId());
		//result.setTypeList(han.getTypeList());
		//result.setTypeListName(han.getTypeListName());
		result.setUpdateBy(han.getUpdateBy());
		result.setUpdateDate(han.getUpdateDate());
		result.setApproveUser(han.getUserLeaderProcess());
		result.setProcessUsers(han.getUsersProcess());
		result.setProposalDate(han.getProposalDate());
		result.setApproveDate(han.getApproveDate());
		return result; 
	}
	
	private NicUploadJob mapperNicUploadJob(NicUploadJob result, NicUploadJobJsonDto han){
		if(result == null)
			result = new NicUploadJob();
		
		result.setAfisCheckStatus(han.getAfisCheckStatus());
		result.setAfisRegisterStatus(han.getAfisRegisterStatus());
		result.setAfisUpdateStatus(han.getAfisUpdateStatus());
		result.setAfisVerifyStatus(han.getAfisVerifyStatus());
		//result.setApproveFlag(han.getApproveFlag());
		result.setCpdCheckStatus(han.getCpdCheckStatus());
		result.setCreateBy(han.getCreateBy());
		result.setCreateDatetime(han.getCreateDatetime());
		result.setCreateWkstnId(han.getCreateWkstnId());
		result.setDateApproveInvestigation(han.getDateApproveInvestigation());
		result.setDateApproveNin(han.getDateApproveNin());
		result.setDateApprovePerson(han.getDateApprovePerson());
		//result.setDatePackage(han.getDatePackage());
		//result.setEstDateOfCollection(han.getEstDateOfCollection());
		result.setInvestigationOfficerId(han.getInvestigationOfficerId());
		result.setInvestigationStatus(han.getInvestigationStatus());
		result.setInvestigationType(han.getInvestigationType());
		result.setJobApproveStatus(han.getJobApproveStatus());
		result.setJobCompletedFlag(han.getJobCompletedFlag());
		result.setJobCreatedTime(han.getJobCreatedTime());
		result.setJobCurrentStage(han.getJobCurrentStage());
		result.setJobCurrentStatus(han.getJobCurrentStatus());
		result.setJobEndTime(han.getJobEndTime());
		result.setJobStartTime(han.getJobStartTime());
		//result.setJobErrorFlag(han.getJobErrorFlag());
		result.setJobPriority(han.getJobPriority());
		result.setJobReprocessCount(han.getJobReprocessCount());
		result.setJobType(han.getJobType());
		result.setNoteApproveNin(han.getNoteApproveNin());
		result.setNoteApproveObj(han.getNoteApproveObj());
		result.setNoteApprovePerson(han.getNoteApprovePerson());
		//result.setNumberTran(han.getNumberTran());
		result.setOriginalyBlacklistId(han.getOriginalyBlacklistId());
		result.getNicTransaction().setPersonCode(han.getOriginalyId());
		//result.setOriginalyPersonId(han.getOriginalyPersonId());
		//result.setPackageId(han.getPackageId());
		result.setPersoRegisterStatus(han.getPersoRegisterStatus());
		result.getNicTransaction().setPriority(han.getPriority());
		result.setReceiptNo(han.getReceiptNo());
		result.getNicTransaction().setIssSiteCode(han.getRicName());
		result.setTempPassportNo(han.getTempPassportNo());
		result.setUpdateBy(han.getUpdateBy());
		result.setUpdateDatetime(han.getUpdateDatetime());
		result.setUpdateWkstnId(han.getUpdateWkstnId());
		result.setValidateInfoBca(han.getValidateInfoBca());
		result.setTransactionId(han.getTransactionId());
		result.setEditTimeExpiry(han.getEditTimeExpiry());
		return result; 
	}
	
	private NicTransactionPayment mapperNicPayment(NicTransactionPayment result, NicPaymentJsonDto pay){
		if(result == null)
			result = new NicTransactionPayment();
		result.setBalance(pay.getBalance());
		result.setCashReceived(pay.getCashReceived());
		result.setCollectionOfficerId(pay.getCollectionOfficerId());
		result.setCreateBy(pay.getCreateBy());
		result.setCreateDatetime(pay.getCreateDatetime());
		result.setCreateWkstnId(pay.getCreateWkstnId());
		result.setFeeAmount(pay.getFeeAmount());
		result.setNoOfTimeLost(pay.getNoOfTimeLost());
		result.setPaymentAmount(pay.getPaymentAmount());
		result.setPaymentDatetime(pay.getPaymentDatetime());
		result.setPaymentId(pay.getPaymentId());
		result.setPaymentStatus(pay.getPaymentStatus());
		result.setReceiptId(pay.getReceiptId());
		result.setReduceRateAmount(pay.getReduceRateAmount());
		result.setReduceRateFlag(pay.getReduceRateFlag());
		result.setTransactionId(pay.getTransactionId());
		result.setUpdateBy(pay.getUpdateBy());
		result.setUpdateDatetime(pay.getUpdateDatetime());
		result.setUpdateWkstnId(pay.getUpdateWkstnId());
		result.setWaiverFlag(pay.getWaiverFlag());
		result.setWaiverOfficerId(pay.getWaiverOfficerId());
		result.setWaiverReason(pay.getWaiverReason());
		
		return result; 
	}
	
	private NicTransaction mapperNicTransaction(NicTransaction result, NicTransactionJsonDto tran){
		
		if(result == null)
			result = new NicTransaction();
		result.setApplnRefNo(tran.getApplnRefNo());
		result.setCreateBy(tran.getCreateBy());
		result.setCreateDatetime(tran.getCreateDatetime());
		result.setCreateDesc(tran.getCreateDesc());
		result.setCreateWkstnId(tran.getCreateWkstnId());
		result.setDateCountryDesc(tran.getDateCountryDesc());
		result.setDateDecisionDesc(tran.getDateDecisionDesc());
		result.setDateNinDesc(tran.getDateNinDesc());
		result.setDateOfApplication(tran.getDateOfApplication());
		result.setDateOfBirthDesc(tran.getDateOfBirthDesc());
		result.setDatePassportDesc(tran.getDatePassportDesc());
		result.setEstDateOfCollection(tran.getEstDateOfCollection());
		result.setEstimateFromDesc(tran.getEstimateFromDesc());
		result.setFatherDobDesc(tran.getFatherDobDesc());
		result.setInfoPerson(tran.getInfoPerson());
		result.setIsPostOffice(tran.getIsPostOffice());
		result.setIssSiteCode(tran.getIssSiteCode());
		result.setIssuingAuthority(tran.getIssuingAuthority());
		//result.setLeaderOfficerId(tran.getLeaderOfficerId());
		result.setMotherDobDesc(tran.getMotherDobDesc());
		result.setNationDesc(tran.getNationDesc());
		result.setNicSiteCode(tran.getNicSiteCode());
		result.setNin(tran.getNin());
		//result.setNoteHandoverB(tran.getNoteHandoverB());
		result.setPackageId(tran.getPackageID());
		result.setPrevDateOfIssue(tran.getPrevDateOfIssue());
		result.setPrevPassportNo(tran.getPrevPassportNo());
		//result.setPrintPerso(tran.getPrintPerso());
		result.setPriority(tran.getPriority());
//		result.setReceiver(tran.getReceiver());
		result.setRecieptNo(tran.getRecieptNo());
		result.setRegSiteCode(tran.getRegSiteCode());
		result.setReligionDesc(tran.getReligionDesc());
		result.setSpouseDobDesc(tran.getSpouseDobDesc());
		//result.setSyncPassport(tran.getSyncPassport());
		result.setTransactionId(tran.getTransactionId());
		result.setTransactionStatus(tran.getTransactionStatus());
		result.setTransactionType(tran.getTransactionType());
		result.setUpdateBy(tran.getUpdateBy());
		result.setUpdateDatetime(tran.getUpdateDatetime());
		result.setUpdateWkstnId(tran.getUpdateWkstnId());
		result.setValidityPeriod(tran.getValidityPeriod());
		return result; 
	}
	
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("/syncUserData")
	public ResponseBase<UserData> syncUserData(UserRequest user) {
		PropertyModel prModel = new PropertyModel();
		prModel.setCode(-1);
		prModel.setMessage("Null data");
		ResponseBase<UserData> rep = new ResponseBase<UserData>();
		UserData uData = new UserData();
		List<RolesJson> lstrole = new ArrayList<RolesJson>();
		List<UsersJson> lstuser = new ArrayList<UsersJson>();
		List<FunctionsJson> lstfunc = new ArrayList<FunctionsJson>();
		List<WorkstationsJson> lstwkst = new ArrayList<WorkstationsJson>();
		try{
			List<Users> lstU = userService.getListUserBySiteCode(user.getSiteCode());
			List<Roles> lstR = roleService.findBySystemId(user.getSystemId());
			List<Functions> lstF = functionService.findBySystemId(user.getSystemId());
			List<Workstations> lstW = workStationService.findBySiteCode(user.getSiteCode());
			
			if(lstF != null && lstF.size() > 0){
				for(Functions f: lstF){
					FunctionsJson fj = mapperFunction(f);
					lstfunc.add(fj);
				}
				uData.setLstfunc(lstfunc);
			}
			if(lstU != null && lstU.size() > 0){
				for(Users u : lstU){
					UsersJson uj = mapperUser(u);
					lstuser.add(uj);
				}
				uData.setLstuser(lstuser);
			}
			if(lstW != null && lstW.size() > 0){
				for(Workstations w : lstW){
					WorkstationsJson wj = mapperWkst(w);
					lstwkst.add(wj);
				}
				uData.setLstwkst(lstwkst);
			}
			if(lstR != null && lstR.size() > 0){
				for(Roles r : lstR){
					RolesJson rj = mapperRole(r);
					lstrole.add(rj);
				}
				uData.setLstrole(lstrole);
			}
			
			rep.setResponse(uData);
			prModel.setCode(99);
			prModel.setMessage("success");
			
			/* Lưu bảng thống kê truyền nhận*/
			this.saveOrUpRptData(Contants.URL_SYNC_USER_DATA, 1, user.getSiteCode());
			
			/*if(lstU != null && lstU.size() > 0){
				for(Users us : lstU){
					List<String> userFunctions = userService.getFunctionIdsForUserId(us.getUserId());
					Set<String>  roleList= new HashSet<String>();
					Set<Roles> roles = us.getRoles();
					if(roles!= null){
						for(Roles role: roles){
							roleList.add(role.getRoleId());
						}
					}
				}
			}
			List<Workstations> lstW = workStationService.findAll();
			if(lstW != null && lstW.size() > 0){
				for(Workstations w : lstW){
					List<String> workStationFunctions = userService.getFunctionIdsForWorkstation(w.getWkstnId());
				}
			}*/
		}
		catch (Exception e) {
			// TODO Auto-generated catch block
			prModel.setMessage(e.getMessage());
			e.printStackTrace();
		}
		
		rep.setProperty(prModel);
		return rep;
	}
	
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("/getMappingAuthentication")
	public ResponseBase<MappingAuthenData> getMappingAuthentication(UserRequest user) {
		PropertyModel prModel = new PropertyModel();
		prModel.setCode(-1);
		prModel.setMessage("Null data");
		ResponseBase<MappingAuthenData> rep = new ResponseBase<MappingAuthenData>();
		MappingAuthenData mapping = new MappingAuthenData();
		try{
			if(StringUtils.isNotEmpty(user.getUserId())){
				List<Roles> roleUser = this.userService.getRolesForUser(user.getUserId());
				if(roleUser != null && roleUser.size() > 0)
				{				
					List<String> rolesIdList = new ArrayList<String>();
					for(Roles r : roleUser){
						rolesIdList.add(r.getRoleId());
					}
					mapping.setRolesUser(rolesIdList);
				}
			}
			
			if(StringUtils.isNotEmpty(user.getWorkstation())){
				List<Roles> roleWskt = this.userService.getRolesForWorstation(user.getWorkstation());
				if(roleWskt != null && roleWskt.size() > 0)
				{				
					List<String> rolesIdList = new ArrayList<String>();
					for(Roles r : roleWskt){
						rolesIdList.add(r.getRoleId());
					}
					mapping.setRolesWorkstation(rolesIdList);
				}
			}
			
			if(StringUtils.isNotEmpty(user.getRole())){
				Roles role = roleService.findById(user.getRole());
				if(role != null)
				{				
					List<String> functionIdList = new ArrayList<String>();
					for(Functions r : role.getFunctions()){
						functionIdList.add(r.getFunctionId());
					}
					mapping.setFunctionsRole(functionIdList);
				}
			}
			rep.setResponse(mapping);
			prModel.setCode(99);
			prModel.setMessage("success");
			
			/* Lưu bảng thống kê truyền nhận*/
			this.saveOrUpRptData(Contants.URL_GET_MAPPING_AUTHENTICATION, 1, user.getSiteCode());
		}
		catch (Exception e) {
			// TODO Auto-generated catch block
			prModel.setMessage(e.getMessage());
			e.printStackTrace();
		}
		
		rep.setProperty(prModel);
		return rep;
	}
	
//	@POST
//	@Produces(MediaType.APPLICATION_JSON)
//	@Consumes(MediaType.APPLICATION_JSON)
//	@Path("/updateLostTransaction")
//	public ResponseBase<Boolean> updateLostTransaction(NicTransactionLost lost) {
//		PropertyModel prModel = new PropertyModel();
//		prModel.setStatus(-1);
//		prModel.setMessage("Null data");
//		ResponseBase<Boolean> rep = new ResponseBase<Boolean>();
//		try{
//			if(lost != null){
//				if(StringUtils.isNotEmpty(lost.getTransactionId())){
//					lostService.saveOrUpdateLost(lost);
//					rep.setResponse(true);
//					prModel.setMessage("success");
//					prModel.setStatus(99);
//					
//					/* Lưu bảng thống kê truyền nhận*/
//					this.saveOrUpRptData(Contants.URL_UPDATE_LOST_TRANSACTION, 1, lost.getSiteCode());
//					
//					try{
//						//TEMP: Hủy hộ chiếu sau khi khai báo
//						if(StringUtils.isNotEmpty(lost.getPassportNo())){
//							NicDocumentData ppExist = documentDataService.findByDocNumber(lost.getPassportNo()).getModel();
//							if(ppExist != null){
//								ppExist.setActiveFlag(false);
//								ppExist.setStatus("NONE");
//								ppExist.setStatusUpdateTime(new Date());
//								documentDataService.saveOrUpdate(ppExist);
//							}
//						}
//					}catch (Exception e){ }
//				}
//				else
//				{
//					prModel.setMessage("null transactionId");
//				}
//			}
//		}
//		catch (Exception e) {
//			// TODO Auto-generated catch block
//			prModel.setMessage(e.getMessage());
//			e.printStackTrace();
//		}
//		rep.setProperty(prModel);
//		return rep;
//	}
	
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("/syncDocumentLostCancel/{date}")
	public ResponseBase<List<NicTransactionLost>> syncDocumentLostCancel(String date) {
		PropertyModel prModel = new PropertyModel();
		prModel.setCode(-1);
		prModel.setMessage("Null data");
		ResponseBase<List<NicTransactionLost>> result = new ResponseBase<List<NicTransactionLost>>();
		List<NicTransactionLost> data = new ArrayList<NicTransactionLost>();
		try{
			Date date_ = null;
			
			if(StringUtils.isNotEmpty(date))
			{
				SimpleDateFormat formatter = new SimpleDateFormat("dd-MMM-yyyy");
				try{
					date_ = formatter.parse(date);
				}
				catch(Exception e){ }
			}
			if(date_ != null)
				data = lostService.findTransactionLost(date_);
			
			if(data != null && data.size() > 0){
				prModel.setCode(99);
				prModel.setMessage("success");
				result.setResponse(data);
				
				/* Lưu bảng thống kê truyền nhận*/
				this.saveOrUpRptData(Contants.URL_SYNC_DOCUMENT_LOST_CANCEL, data.size(), null);
				
			}
		}catch (Exception e) {
			// TODO Auto-generated catch block
			prModel.setMessage(e.getMessage());
			e.printStackTrace();
		}
		result.setProperty(prModel);
		
		return result;
	}
	
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("/dowloadListBlacklist")
	public ResponseBase<MoreInformation> dowloadListBlacklist(RequestDataSpecial tranx) {
		PropertyModel prModel = new PropertyModel();
		prModel.setCode(-1);
		prModel.setMessage("null");
		ResponseBase<MoreInformation> result = new ResponseBase<MoreInformation>();
		MoreInformation data = new MoreInformation();
		try{
			if(StringUtils.isNotEmpty(tranx.getTransactionId())){
				/*NicUploadJob jobDBO = uploadJobService.getUploadJobByTransactionIdSinger(null, tranx.getTransactionId());
				if(jobDBO != null){
					Long idBL = null;
					List<NicSearchResult> searchResults = nicSearchResultService.findAllByJobId(jobDBO.getWorkflowJobId());
					if(searchResults != null && searchResults.size() > 0){
						for(NicSearchResult sR_ : searchResults){
							if(sR_.getTypeSearch().equals(NicSearchResult.TYPE_SEARCH__BLACKLIST) && sR_.getHasHit()){
								if((idBL == null) || (idBL != null && idBL <  sR_.getSearchResultId()))
									idBL = sR_.getSearchResultId();
							}
						}
					}
					List<EppBlacklist> blackList = null;
					if(idBL != null){
						List<NicSearchHitResult> listHitBL = nicSearchHitResultService
								.findHitPositions(null, idBL);
						if(listHitBL != null){
							blackList = new ArrayList<EppBlacklist>();							
							for(NicSearchHitResult hit: listHitBL){
								if(StringUtils.isNotEmpty(hit.getTransactionIdHit())){
									Long id = Long.valueOf(hit.getTransactionIdHit()).longValue();
									EppBlacklist blacklist = nicBlacklistDao.findById(id);
									if(blacklist != null){
										blackList.add(blacklist);
									}
								}
							}
						}
					}
					
					
					String xmlBl = "";
					if(blackList != null){
						ListBlackList listBL = new ListBlackList();
						listBL.setListBl(blackList);
						JAXBContext jaxbContext = JAXBContext.newInstance(ListBlackList.class);
						Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
						StringWriter sw = new StringWriter();
						jaxbMarshaller.marshal(listBL, sw);
						xmlBl = sw.toString();
					}
					
					if(StringUtils.isNotEmpty(xmlBl)){
						data.setBlacklistXml(xmlBl);
						result.setResponse(data);
						prModel.setMessage("success");
						prModel.setCode(99);
					}
				}*/
				int page = 1;
				if(StringUtils.isNotEmpty(tranx.getPage())){
					try{
						page = Integer.parseInt(tranx.getPage());
						  // is an integer!
					} catch (NumberFormatException e) {
						prModel.setMessage("fail - page input wrong");
						result.setProperty(prModel);
						return result;
					}
				}
				else
				{
					prModel.setMessage("fail - page input empty");
					result.setProperty(prModel);
					return result;
				}
				
				List<String> listHitBL = nicSearchHitResultService
						.findListHitSpecial(tranx.getTransactionId(), NicSearchResult.TYPE_SEARCH__BLACKLIST, page, 20);
				List<FullBlackList> blackList = null;
				if(listHitBL != null){
					blackList = new ArrayList<FullBlackList>();							
					for(String hit: listHitBL){
						if(StringUtils.isNotEmpty(hit)){
							Long id = Long.valueOf(hit).longValue();
							EppBlacklist blacklist = nicBlacklistDao.findById(id);
							List<EppBlacklistAttachment> lstattacht = nicBlacklistAttachmentDao.findAttachmentById(id);
							EppBlacklistAttachment attacht = null;
							if(lstattacht != null && lstattacht.size() > 0){
								attacht = lstattacht.get(0);
							}
							if(blacklist != null){
								FullBlackList full = new FullBlackList();
								full.setBlacklist(blacklist);
								if(attacht != null)
									full.setAtachment(attacht);
								blackList.add(full);
							}
						}
					}
				}
				
				String xmlBl = "";
				if(blackList != null){
					ListBlackList listBL = new ListBlackList();
					listBL.setListBl(blackList);
					JAXBContext jaxbContext = JAXBContext.newInstance(ListBlackList.class);
					Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
					StringWriter sw = new StringWriter();
					jaxbMarshaller.marshal(listBL, sw);
					xmlBl = sw.toString();
				}
				
				if(StringUtils.isNotEmpty(xmlBl)){
					data.setBlacklistXml(xmlBl);
					result.setResponse(data);
					prModel.setMessage("success");
					prModel.setCode(99);
					
					/* Lưu bảng thống kê truyền nhận*/
					this.saveOrUpRptData(Contants.URL_DOWNLOAD_LIST_BLACKLIST, 1, null);
				}
			}	
		}
		catch (Exception e) {
			// TODO Auto-generated catch block
			prModel.setMessage(e.getMessage());
			e.printStackTrace();
		}
		result.setProperty(prModel);
		return result;
	}
	
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("/dowloadListIdentification")
	public ResponseBase<MoreInformation> dowloadListIdentification(RequestDataSpecial tranx) {
		PropertyModel prModel = new PropertyModel();
		prModel.setCode(-1);
		prModel.setMessage("null");
		ResponseBase<MoreInformation> result = new ResponseBase<MoreInformation>();
		MoreInformation data = new MoreInformation();
		try{
			if(StringUtils.isNotEmpty(tranx.getTransactionId())){
				int page = 1;
				if(StringUtils.isNotEmpty(tranx.getPage())){
					try{
						page = Integer.parseInt(tranx.getPage());
						  // is an integer!
					} catch (NumberFormatException e) {
						prModel.setMessage("fail - page input wrong");
						result.setProperty(prModel);
						return result;
					}
				}
				else
				{
					prModel.setMessage("fail - page input empty");
					result.setProperty(prModel);
					return result;
				}
				
				List<String> listHitId = nicSearchHitResultService
						.findListHitSpecial(tranx.getTransactionId(), NicSearchResult.TYPE_SEARCH__IDENTIFICATION, page, 20);
				List<FullIdentification> identifi = null;
				if(listHitId != null){
					identifi = new ArrayList<FullIdentification>();							
					for(String hit: listHitId){
						if(StringUtils.isNotEmpty(hit)){
							Long id = Long.valueOf(hit).longValue();
							EppIdentification identification = nicIdentificationDao.findById(id);
							List<EppIdentificationAttachmnt> lstattacht = nicIdentificationAttachmentDao.findAttachmentById(id);
							EppIdentificationAttachmnt attacht = null;
							if(lstattacht != null && lstattacht.size() > 0){
								attacht = lstattacht.get(0);
							}
							if(identifi != null){
								FullIdentification full = new FullIdentification();
								full.setIdentifi(identification);
								if(attacht != null)
									full.setAtachment(attacht);
								identifi.add(full);
							}
						}
					}
				}
				
				String xmlId = "";
				if(identifi != null){
					ListIdentification listBL = new ListIdentification();
					listBL.setListID(identifi);
					JAXBContext jaxbContext = JAXBContext.newInstance(ListIdentification.class);
					Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
					StringWriter sw = new StringWriter();
					jaxbMarshaller.marshal(listBL, sw);
					xmlId = sw.toString();
				}
				
				if(StringUtils.isNotEmpty(xmlId)){
					data.setIdentificationXml(xmlId);
					result.setResponse(data);
					prModel.setMessage("success");
					prModel.setCode(99);
					
					/* Lưu bảng thống kê truyền nhận*/
					this.saveOrUpRptData(Contants.URL_DOWNLOAD_LIST_IDENTIFICATION, 1, null);
				}
			}	
		}
		catch (Exception e) {
			// TODO Auto-generated catch block
			prModel.setMessage(e.getMessage());
			e.printStackTrace();
		}
		result.setProperty(prModel);
		return result;
	}
	
	@POST
	@Produces("application/json")
	@Consumes("application/json")
	@Path("/retrieveDocumentData")
	public ResponseBase<List<RetrieveDocumentDataResponse>> retrieveDocumentData(RetrieveDocumentDataRequest data) {
		PropertyModel prModel = new PropertyModel();
		prModel.setCode(-1);
		prModel.setMessage("Null data");
		ResponseBase<List<RetrieveDocumentDataResponse>> result = new ResponseBase<List<RetrieveDocumentDataResponse>>();
		List<RetrieveDocumentDataResponse> response = new ArrayList<RetrieveDocumentDataResponse>();
		try{
			Date issueDate = null;
			Date expireDate = null;
			SimpleDateFormat formatter = new SimpleDateFormat("dd-MMM-yyyy");
			try{
				issueDate = formatter.parse(data.getIssueDatePassport());
			}
			catch(Exception e){ }
			try{
				expireDate = formatter.parse(data.getExpireDatePassport());
			}
			catch(Exception e){ }
			
			response = documentDataService.getlistDocumentData(data.getSiteCode(), data.getPassportNo(), issueDate, data.getTypePassport(), expireDate, data.getHandoverB());
			if(response != null && response.size() > 0){
				for(RetrieveDocumentDataResponse rp : response){
					if(rp.getGender().equals("M"))
						rp.setGender("Nam");
					else if(rp.getGender().equals("F"))
						rp.setGender("Nữ");
					else 
						rp.setGender("Khác");
				}
				
				result.setResponse(response);
				prModel.setMessage("success");
				prModel.setCode(99);
				
				/* Lưu bảng thống kê truyền nhận*/
				if (response.size() > 0) {
					this.saveOrUpRptData(Contants.URL_RETRIEVE_DOCUMENT_DATA, response.size(), null);
				}
				
			}
		}
		catch (Exception e) {
			// TODO Auto-generated catch block
			prModel.setMessage(e.getMessage());
			e.printStackTrace();
		}
		result.setProperty(prModel);
		return result;
	}
	
	@POST
	@Produces("application/json")
	@Consumes("application/json")
	@Path("/retrieveDocumentPassport")
	public ResponseBase<List<NicUploadJobDto>> retrieveDocumentPassport(NicUploadJobDto data) {
		PropertyModel prModel = new PropertyModel();
		prModel.setCode(-1);
		prModel.setMessage("Null data");
		ResponseBase<List<NicUploadJobDto>> response = new ResponseBase<List<NicUploadJobDto>>();
		List<NicUploadJobDto> dataR = new ArrayList<NicUploadJobDto>();
		try{
			Integer total = (int) (long)nicTransactionService.getTracuuhosohcCount(data);
			if(total > 0){
				if(data.getPage() == null || data.getPage() < 1)
					data.setPage(1);
				dataR = nicTransactionService.allTracuuhosohc(data, data.getPage(), 20);
				if(dataR != null && dataR.size() > 0){
					for(NicUploadJobDto dto : dataR){
						if(dto.getGender().equals("M"))
							dto.setGender("Nam");
						else if(dto.getGender().equals("F"))
							dto.setGender("Nữ");
						else 
							dto.setGender("Khác");
						
						if(dto.getDatePackage() != null)
							dto.setPackageDate(dto.getDatePackage().toString());
					}
					
					response.setResponse(dataR);
					prModel.setCode(99);
					prModel.setMessage("success");
					
					/* Lưu bảng thống kê truyền nhận*/
					if (dataR.size() > 0) {
						this.saveOrUpRptData(Contants.URL_RETRIEVE_DOCUMENT_PASSPORT, dataR.size(), null);
					}
				}
			}
		}
		catch (Exception e) {
			// TODO Auto-generated catch block
			prModel.setMessage(e.getMessage());
			e.printStackTrace();
		}
		
		response.setProperty(prModel);
		return response;
	}
	
	@POST
	@Produces("application/json")
	@Consumes("application/json")
	@Path("/retrieveDetailDocumentData")
	public ResponseBase<RetrieveDocumentDataResponse> retrieveDetailDocumentData(RetrieveDocumentDataRequest data) {
		PropertyModel prModel = new PropertyModel();
		prModel.setCode(-1);
		prModel.setMessage("Null data");
		ResponseBase<RetrieveDocumentDataResponse> result = new ResponseBase<RetrieveDocumentDataResponse>();
		RetrieveDocumentDataResponse response = new RetrieveDocumentDataResponse();
		try{
			response = documentDataService.detailDocumentData(data.getPassportNo());
			if(response != null){
				result.setResponse(response);
				prModel.setMessage("success");
				prModel.setCode(99);
				
				/* Lưu bảng thống kê truyền nhận*/
				this.saveOrUpRptData(Contants.URL_RETRIEVE_DETAIL_DOCUMENT_DATA, 1, null);
			}
		}
		catch (Exception e) {
			// TODO Auto-generated catch block
			prModel.setMessage(e.getMessage());
			e.printStackTrace();
		}
		
		result.setProperty(prModel);
		return result;
	} 
	
	@POST
	@Produces("application/json")
	@Consumes("application/json")
	@Path("/getConfigurations")
	public ResponseBase<GetConfigurationsResponse> getConfigurations(GetConfigurations input)
			throws FaultException {
		PropertyModel prModel = new PropertyModel();
		prModel.setCode(-1);
		prModel.setMessage("Null data");
		ResponseBase<GetConfigurationsResponse> result = new ResponseBase<GetConfigurationsResponse>();
		logger.info("AdminWSImpl.getConfigurations(): begin");
		GetConfigurationsResponse configResponse = null;
		try {
			logger.info("getConfigurations() retrievalType:{}, configType:{}", input.getRetrievalType(), input.getConfigType());
			String resultXml = null;
			if (ConfigurationType.CODE.equals(input.getConfigType())) {
				resultXml = codeRetrievalHelper.getCodeXml();
			} else if (ConfigurationType.CODE_VALUE.equals(input.getConfigType())) {
				resultXml = codeRetrievalHelper.getCodeValueXml();
			} else if (ConfigurationType.PARAMETER.equals(input.getConfigType())) {
				resultXml = codeRetrievalHelper.getParameterXml();
			} else {
				logger.warn("getConfigurations() configType[{}] is not supported.", input.getRetrievalType(), input.getConfigType());
			}
			
			configResponse = new GetConfigurationsResponse();
			configResponse.setConfigType(input.getConfigType());
			configResponse.setConfigDataXml(resultXml);
			
//			logger.debug("getConfigurations() result.configDataXml.length={}", new Object[] { configResponse.getConfigDataXml()!=null ? configResponse.getConfigDataXml().length() : 0 } );
			
			result.setResponse(configResponse);
			prModel.setMessage("success");
			prModel.setCode(99);
			
			/* Lưu bảng thống kê truyền nhận*/
			if (resultXml != null) {
				this.saveOrUpRptData(Contants.URL_GET_CONFIGURATION, 1, null);
			}
			
		} catch (Exception e) {
			logger.error("Exception Encountered in getConfigurations()", e);
			throw new FaultException("Exception encountered in getConfigurations():"+e.getMessage(), e);
		}
		logger.info("AdminWSImpl.getConfigurations(): end");
		
		return result;
	}
	
	@POST
	@Produces("application/json")
	@Consumes("application/json")
	@Path("/editDataRegistration")
	public ResponseBase<Boolean> editDataRegistration(EditDataRegistration data) {
		PropertyModel prModel = new PropertyModel();
		prModel.setCode(-1);
		prModel.setMessage("fail");
		ResponseBase<Boolean> result = new ResponseBase<Boolean>();
		result.setResponse(false);
		try{
		    NicRegistrationData regis =	nicRegistrationDataService.getNicDataById(data.getTransactionId());
		    if(StringUtils.isNotEmpty(data.getAddressline1())){
		    	regis.setAddressLine1(data.getAddressline1());
		    }
		    if(StringUtils.isNotEmpty(data.getAddressline4())){
		    	regis.setAddressLine4(data.getAddressline4());
		    }
		    if(StringUtils.isNotEmpty(data.getAddressline5())){
		    	regis.setAddressLine5(data.getAddressline5());
		    }
		    if(StringUtils.isNotEmpty(data.getFullname())){
		    	regis.setSurnameLine1(data.getFullname());
		    }
		    if(StringUtils.isNotEmpty(data.getSurname())){
		    	regis.setSurnameFull(data.getSurname());
		    }
		    if(StringUtils.isNotEmpty(data.getMiddlename())){
		    	regis.setMiddlenameFull(data.getMiddlename());
		    }
		    if(StringUtils.isNotEmpty(data.getLastname())){
		    	regis.setFirstnameFull(data.getLastname());
		    }
		    if(StringUtils.isNotEmpty(data.getPrintDob())){
		    	regis.setPrintDob(data.getPrintDob());
		    }
		    if(StringUtils.isNotEmpty(data.getStyleDob())){
		    	regis.setDefDateOfBirth(data.getStyleDob());
		    }
		    if(StringUtils.isNotEmpty(data.getDob())){
		    	try{
		    		Date date1 = new SimpleDateFormat("dd/MM/yyyy").parse(data.getDob());
		    		regis.setDateOfBirth(date1);
		    	}
		    	catch(Exception e){ }
		    }
		    nicRegistrationDataService.saveOrUpdate(regis);
		    result.setResponse(true);
			prModel.setMessage("success");
			prModel.setCode(99);
			
			/* Lưu bảng thống kê truyền nhận*/
			this.saveOrUpRptData(Contants.URL_EDIT_DATA_REGISTRATION, 1, null);
		}
		catch (Exception e) {
			// TODO Auto-generated catch block
			prModel.setMessage(e.getMessage());
			e.printStackTrace();
		}
		
		result.setProperty(prModel);
		return result;
	}
	
	
	private UsersJson mapperUser(Users u){
		UsersJson uJson = new UsersJson();
		if(u.getActiveIndicator() != null && u.getActiveIndicator())
			uJson.setActiveIndicator("Y");
		else
			uJson.setActiveIndicator("N");
		uJson.setCreateBy(u.getCreateBy());
		uJson.setCreateDate(u.getCreateDate());
		uJson.setCreateWkstnId(u.getCreateWkstnId());
		uJson.setDateOfPwdExpiry(u.getDateOfPwdExpiry());
		uJson.setDeleteBy(u.getDeleteBy());
		uJson.setDeleteDate(u.getDeleteDate());
		if(u.getDeleteFlag() != null && u.getDeleteFlag())
			uJson.setDeleteFlag("Y");
		else
			uJson.setDeleteFlag("N");
		uJson.setDeleteWkstnId(u.getDeleteWkstnId());
		uJson.setPosition(u.getPosition());
		uJson.setSiteCode(u.getSiteCode());
		uJson.setSiteGroupCode(u.getSiteGroupCode());
		if(u.getSysAdminFlag() != null && u.getSysAdminFlag())
			uJson.setSysAdminFlag("Y");
		else
			uJson.setSysAdminFlag("N");
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
		if(w.getAccessibleFlag() != null && w.getAccessibleFlag())
			wJson.setAccessibleFlag("Y");
		else
			wJson.setAccessibleFlag("N");
		wJson.setCounterPriority(w.getCounterPriority());
		wJson.setCreateBy(w.getCreateBy());
		wJson.setCreateDate(w.getCreateDate());
		wJson.setCreateWkstnId(w.getCreateWkstnId());
		wJson.setDeleteBy(w.getDeleteBy());
		wJson.setDeleteDate(w.getDeleteDate());
		if(w.getDeleteFlag() != null && w.getDeleteFlag())
			wJson.setDeleteFlag("Y");
		else
			wJson.setDeleteFlag("N");
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
	
	private FunctionsJson mapperFunction(Functions r){
		FunctionsJson fJson = new FunctionsJson();
		fJson.setCreateBy(r.getCreateBy());
		fJson.setCreateDate(r.getCreateDate());
		fJson.setCreateWkstnId(r.getCreateWkstnId());
		fJson.setDeleteBy(r.getDeleteBy());
		fJson.setDeleteDate(r.getDeleteDate());
		if(r.getDeleteFlag() != null && r.getDeleteFlag())
			fJson.setDeleteFlag("Y");
		else
			fJson.setDeleteFlag("N");
		fJson.setDeleteWkstnId(r.getDeleteWkstnId());
		fJson.setFunctionCategory(r.getFunctionCategory());
		fJson.setFunctionDesc(r.getFunctionDesc());
		fJson.setFunctionId(r.getFunctionId());
		fJson.setFunctionUrl(r.getFunctionUrl());
		fJson.setSystemId(r.getSystemId());
		fJson.setUpdateBy(r.getUpdateBy());
		fJson.setUpdateDate(r.getUpdateDate());
		fJson.setUpdateWkstnId(r.getUpdateWkstnId());
		return fJson;
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
				EppPerson person =  eppPersonService.getPersonById(personId);
				personId = null;
				/*if(person != null && person.getOriginId() != null && person.getOriginId() > 0){
					result = new ArrayList<NicHitTransaction>();
					List<NicUploadJob> nicUp = uploadJobService.findAllByPersonID(person.getOriginId());
					if(nicUp != null && nicUp.size() > 0){
						NicHitTransaction add = new NicHitTransaction();
						add.setTranid(nicUp.get(0).getTransactionId());
						add.setPersonId(person.getOriginId());
						add.setHitId(99999999l);
						result.add(add);
						EppPerson personExist =  eppPersonService.getPersonById(person.getOriginId());
						if(personExist != null && personExist.getOriginId() != null && personExist.getOriginId() > 0){
							personId = personExist.getOriginId();
						} 
					} 
				}*/
			} while (personId != null);
			
		} catch (Exception e){
			
		}
		return result;
	}
	//save or update rptData
			private void saveOrUpRptData(String type, int count, String siteCode){
				try {
					RptStatisticsTransmitData rptData = rptService.findSingerByConditions(type, siteCode, DateUtil.strToDate(com.nec.asia.nic.utils.HelperClass.convertDateType3(new Date()), DateUtil.FORMAT_YYYYMMDD));
					if (rptData != null) {
						rptData.setTotal(rptData.getTotal() + count);
					} else {
						rptData = new RptStatisticsTransmitData();
						rptData.setRptDate(DateUtil.strToDate(com.nec.asia.nic.utils.HelperClass.convertDateType3(new Date()), DateUtil.FORMAT_YYYYMMDD));
						rptData.setTotal(count);
						rptData.setType(type);
						rptData.setSiteCode(siteCode);
					}
					rptData.setUpdateDatetime(new Date());
					rptService.saveOrUpdateData(rptData);
				} catch (Exception e) {
					// TODO: handle exception
				}
			}
}
