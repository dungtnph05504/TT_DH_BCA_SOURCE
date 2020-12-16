package service.perso.bussiness;

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
import java.text.Normalizer;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
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
import javax.xml.bind.JAXBException;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateUtils;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.web.bind.annotation.ExceptionHandler;

import service.perso.model.CancelPassportDetail;
import service.perso.model.DataRegistrationLDS;
import service.perso.model.DocumentPrintedResidenceInfo;
import service.perso.model.HandoverPassportInfo;
import service.perso.model.HanvdoverRequest;
import service.perso.model.ListPassportUpdateInfo;
import service.perso.model.PassportInfo;
import service.perso.model.PassportUpdateInfo;
import service.perso.model.PrevPassport;
import service.perso.model.PropertyModel;
import service.perso.model.ReprintPassportInfo;
import service.perso.model.ResponseBase;
import service.perso.model.TransactionPerso;
import service.perso.model.UpdateLDSPackageDto;
import service.perso.model.UpdatePackageRequest;
import service.perso.model.sync.PassportA72;
import service.perso.model.sync.ResponseFromA72;
import service.perso.util.Contants;
import service.perso.util.HelperClass;
import service.transaction.bussiness.TransactionService;
import service.transaction.model.PassportStatusInfo;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nec.asia.nic.comp.job.dto.DataRegistrationLdsDTO;
import com.nec.asia.nic.comp.job.utils.NicCommandUtil;
import com.nec.asia.nic.comp.listHandover.domain.NicListHandover;
import com.nec.asia.nic.comp.listHandover.domain.NicListHandoverId;
import com.nec.asia.nic.comp.listHandover.service.ListHandoverService;
import com.nec.asia.nic.comp.queuesJobSchedule.service.QueuesJobScheduleService;
import com.nec.asia.nic.comp.trans.dao.DocumentDataDao;
import com.nec.asia.nic.comp.trans.domain.BaseModelList;
import com.nec.asia.nic.comp.trans.domain.BaseModelSingle;
import com.nec.asia.nic.comp.trans.domain.BorderGate;
import com.nec.asia.nic.comp.trans.domain.EppDocumentPrintedResidence;
import com.nec.asia.nic.comp.trans.domain.EppListHandoverDetail;
import com.nec.asia.nic.comp.trans.domain.EppPerson;
import com.nec.asia.nic.comp.trans.domain.NicDocumentData;
import com.nec.asia.nic.comp.trans.domain.NicDocumentDataId;
import com.nec.asia.nic.comp.trans.domain.NicDocumentHistory;
import com.nec.asia.nic.comp.trans.domain.NicRegistrationData;
import com.nec.asia.nic.comp.trans.domain.NicTransaction;
import com.nec.asia.nic.comp.trans.domain.NicTransactionAttachment;
import com.nec.asia.nic.comp.trans.domain.NicTransactionLog;
import com.nec.asia.nic.comp.trans.domain.NicUploadJob;
import com.nec.asia.nic.comp.trans.domain.QueuesJobSchedule;
import com.nec.asia.nic.comp.trans.domain.RptStatisticsTransmitData;
import com.nec.asia.nic.comp.trans.domain.SynQueueJobXnc;
import com.nec.asia.nic.comp.trans.domain.SyncQueueJob;
import com.nec.asia.nic.comp.trans.dto.InfoPassportC;
import com.nec.asia.nic.comp.trans.dto.LdsResponseWsDto;
import com.nec.asia.nic.comp.trans.dto.NicUploadJobDto;
import com.nec.asia.nic.comp.trans.service.BorderGateService;
import com.nec.asia.nic.comp.trans.service.ConfigurationWorkflowService;
import com.nec.asia.nic.comp.trans.service.DataPackService;
import com.nec.asia.nic.comp.trans.service.DocumentDataService;
import com.nec.asia.nic.comp.trans.service.DocumentHistoryService;
import com.nec.asia.nic.comp.trans.service.EppDocumentPrintedResidenceService;
import com.nec.asia.nic.comp.trans.service.EppInventoryItemsDetailService;
import com.nec.asia.nic.comp.trans.service.EppListHandoverDetailService;
import com.nec.asia.nic.comp.trans.service.EppPersonService;
import com.nec.asia.nic.comp.trans.service.NicRegistrationDataService;
import com.nec.asia.nic.comp.trans.service.NicTransactionAttachmentService;
import com.nec.asia.nic.comp.trans.service.NicTransactionPackageService;
import com.nec.asia.nic.comp.trans.service.NicTransactionService;
import com.nec.asia.nic.comp.trans.service.NicUploadJobService;
import com.nec.asia.nic.comp.trans.service.RptStatisticsTransmitDataService;
import com.nec.asia.nic.comp.trans.service.SynQueueJobXncService;
import com.nec.asia.nic.comp.trans.service.SyncQueueJobService;
import com.nec.asia.nic.comp.trans.service.TransactionLogService;
import com.nec.asia.nic.comp.trans.service.exception.NicUploadJobServiceException;
import com.nec.asia.nic.comp.trans.service.exception.TransactionServiceException;
import com.nec.asia.nic.comp.ws.log.dao.EppWsLogDao;
import com.nec.asia.nic.comp.ws.log.domain.CreateMessageException;
import com.nec.asia.nic.comp.ws.log.domain.EppWsLog;
import com.nec.asia.nic.comp.ws.log.service.EppWsLogService;
import com.nec.asia.nic.framework.PaginatedResult;
import com.nec.asia.nic.framework.admin.code.domain.CodeValues;
import com.nec.asia.nic.framework.admin.code.domain.Codes;
import com.nec.asia.nic.framework.admin.code.domain.Parameters;
import com.nec.asia.nic.framework.admin.code.service.CodesService;
import com.nec.asia.nic.framework.admin.code.service.ParametersService;
import com.nec.asia.nic.framework.admin.site.domain.SiteRepository;
import com.nec.asia.nic.framework.admin.site.service.SiteService;
import com.nec.asia.nic.utils.DateUtil;

@Path("/perso/")
@WebService
@Provider
public class PersoService {

	public static final int PERSO_REGISTER = 6;
	public static final String STATUS_COMPLETED = "02";
	public static final String STATUS_ERROR = "09";
	public static final String JOB_STATE_PERSO_REGISTER = "PERSO_REGISTER";
	public static final String PERSO_PRINTED = "PERSO_PRINTED";
	public static final String PERSO_PACKED_LDS_COMPLETED = "PERSO_PACKED_LDS_COMPLETED";
	public static final String JOB_STATE_PASSPORT_REGISTER = "PROVIDE_PASSPORT_NO";
	public static final String STAGE_COMPLETED = "_COMPLETED";
	public static final String STAGE_ERROR = "_ERROR";
	public static final String OBJECT_TYPE_PERSO = "PS";
	public static String tokenA08 = "";

	public final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private NicUploadJobService uploadJobService;

	@Autowired
	private NicTransactionService nicTransactionService;

	@Autowired
	private NicRegistrationDataService nicRegistrationDataService;

	@Autowired
	private NicUploadJobService uploadJobSerivce;

	@Autowired
	private TransactionLogService nicTransactionLogService;

	@Autowired
	private NicTransactionAttachmentService nicTransactionAttachmentService;

	@Autowired
	private DocumentDataDao documentDataDao;

	@Autowired
	private DocumentDataService documentDataService;

	@Autowired
	private ApplicationContext appContext;

	@Autowired
	private DataPackService dataPackService;

	@Autowired
	private CodesService codesService;

	@Autowired
	private ListHandoverService listHandoverService;

	@Autowired
	private NicTransactionPackageService nicTransactionPackageService;

	@Autowired
	private com.nec.asia.nic.comp.trans.service.PersoService persoService2;

	@Autowired
	private ParametersService parametersService;

	@Autowired
	private QueuesJobScheduleService queuesJobScheduleService;

	@Autowired
	private SiteService siteService;

	@Autowired
	private EppInventoryItemsDetailService eppInventoryItemsDetailService;

	@Autowired
	private ConfigurationWorkflowService configurationWorkflowService;

	@Autowired
	private SyncQueueJobService queueJobService;

	@Autowired
	private EppListHandoverDetailService eppListHandoverDetailService;

	@Autowired
	private EppWsLogDao eppWsLogDao;

	@Autowired
	private EppWsLogService eppWsLogService;

	@Autowired
	private RptStatisticsTransmitDataService rptService;

	@Autowired
	private DocumentHistoryService documentHistoryService;

	@Autowired
	private BorderGateService borderGateService;

	@Autowired
	private SynQueueJobXncService queueJobXncService;

	@Autowired
	private EppPersonService eppPersonService;

	@Autowired
	private EppDocumentPrintedResidenceService eppDocumentPrintedResidenceService;

	@GET
	@Produces("application/json")
	@Consumes("application/json")
	@Path("/listPendingDocument/p={pageNo}")
	public ResponseBase<List<PassportInfo>> listPendingDocument(
			@PathParam("pageNo") int pageNo) {
		PaginatedResult<NicUploadJobDto> pr = null;
		List<NicUploadJobDto> list = new ArrayList<NicUploadJobDto>();
		List<PassportInfo> passport = new ArrayList<PassportInfo>();

		PropertyModel prModel = new PropertyModel();
		prModel.setCode(-1);
		ResponseBase<List<PassportInfo>> rep = new ResponseBase<List<PassportInfo>>();
		try {
			pr = uploadJobService.findAllForApprovePaginationALL("UserPerso",
					pageNo, 20, null, null);
			if (pr != null) {
				list = pr.getRows();
				for (NicUploadJobDto item : list) {

					PassportInfo obj = new PassportInfo();
					NicTransaction nicTransaction = nicTransactionService
							.findById(item.getTransactionId());
					NicRegistrationData nicRegistrationData = nicRegistrationDataService
							.findById(item.getTransactionId());
					obj.setFullName(nicRegistrationData.getSurnameLine1());
					obj.setDob(new java.sql.Date(nicRegistrationData
							.getDateOfBirth().getTime()).toString());
					obj.setAddress(nicRegistrationData.getAddressLine1());
					obj.setGender(nicRegistrationData.getGender());
					obj.setPassportType(nicTransaction.getPassportType());
					obj.setPid(nicTransaction.getNin());
					// obj.setPlaceOfBirth(nicRegistrationData.getPlaceOfBirth());
					obj.setPlaceOfIssue(nicTransaction.getIssSiteCode());
					obj.setTransactionId(nicTransaction.getTransactionId());
					List<CodeValues> lstCodeV = codesService
							.getParentCodeValues("CODE_BirthPlace");
					if (lstCodeV != null && lstCodeV.size() > 0) {
						for (CodeValues code : lstCodeV) {
							if (code.getCodeValueDesc()
									.trim()
									.equals(nicRegistrationData
											.getPlaceOfBirth().trim())) {
								obj.setPlaceOfBirth(code.getId().getCodeValue());
							}
						}
					}
					// Tạm thời đang fix cố định nơi perso
					/*
					 * if (nicTransaction.getPrintPerso() != null &&
					 * nicTransaction.getPrintPerso() > 0)
					 * obj.setPlacePersoId(nicTransaction.getPrintPerso()); else
					 * obj.setPlacePersoId((long) 1);
					 */
					try {
						List<NicTransactionAttachment> nicTransAttach = nicTransactionAttachmentService
								.findNicTransactionAttachments(
										item.getTransactionId(), "PH_CAP", "01")
								.getListModel();
						if (nicTransAttach != null)
							obj.setPicture(nicTransAttach.get(0).getDocData());
					} catch (Exception e) {
					}

					passport.add(obj);

				}

				rep.setResponse(passport);
				prModel.setCode(99);
				prModel.setMessage("");

				/* Lưu bảng thống kê truyền nhận */
				if (passport.size() > 0) {
					this.saveOrUpRptData(Contants.URL_LIST_PENDING_DOCUMENT, 1,
							null);
				}

			}
		} catch (Exception e) {
			e.printStackTrace();
			prModel.setCode(414);
			prModel.setMessage(Contants.MESSAGE_EXCEPTION);
			/*
			 * Lưu dữ liệu nếu có Exception
			 */
			String dataResponse = "";
			String keyId = "LPD_"
					+ new SimpleDateFormat("ddMMyyyy").format(new Date());
			try {
				dataResponse = new ObjectMapper().writeValueAsString(rep);
			} catch (Exception e2) {
				logger.error(e2.getMessage());
			}
			this.saveException(Contants.TYPE_LPD,
					Contants.URL_LIST_PENDING_DOCUMENT, String.valueOf(pageNo),
					keyId, dataResponse, e);
		}

		rep.setProperty(prModel);
		return rep;
	}

	/*
	 * @GET
	 * 
	 * @Produces("application/json")
	 * 
	 * @Consumes("application/json")
	 * 
	 * @Path("/listPendingDocumentByHandover/h={handoverId}") public
	 * ResponseBase<List<HandoverPassportInfo>>
	 * listPendingDocumentByHandover(@PathParam("handoverId") String handoverId)
	 * { List<PassportInfo> passport = new ArrayList<PassportInfo>();
	 * List<HandoverPassportInfo> handoverlist = new
	 * ArrayList<HandoverPassportInfo>();
	 * 
	 * if(StringUtils.isNotEmpty(handoverId) && handoverId.equals("test")){
	 * handoverId = ""; }
	 * 
	 * PropertyModel prModel = new PropertyModel(); prModel.setStatus(-1);
	 * ResponseBase<List<HandoverPassportInfo>> rep = new
	 * ResponseBase<List<HandoverPassportInfo>>(); try { //Tìm các danh sách đã
	 * hoàn thành List<NicListHandover> prHandover = null; prHandover =
	 * listHandoverService.findListHandoverByCriteria(handoverId, 8, 1);
	 * if(prHandover != null && prHandover.size() > 0){ for(NicListHandover hd :
	 * prHandover){ HandoverPassportInfo hPP_ = new HandoverPassportInfo();
	 * hPP_.setHandoverId(hd.getPackageId());
	 * 
	 * //Tìm danh sách transaction List<NicTransactionPackage> lspack_
	 * =nicTransactionPackageService
	 * .getListPackageByPackageId(hd.getPackageId()); if(lspack_ != null &&
	 * lspack_.size() > 0){ for(NicTransactionPackage pack_ : lspack_){ String
	 * transId = pack_.getTransactionId(); NicUploadJob nicU =
	 * uploadJobSerivce.getUploadJobByTransactionIdSinger(null, transId);
	 * 
	 * //Kiểm tra dữ liệu workflow có phù hợp không
	 * if(StringUtils.isNotEmpty(nicU.getJobApproveStatus()) &&
	 * nicU.getJobApproveStatus().equals("1") &&
	 * nicU.getInvestigationStatus().equals
	 * (NicUploadJob.RECORD_STATUS_APPROVE_PERSO) &&
	 * (StringUtils.isEmpty(nicU.getPersoRegisterStatus()) ||
	 * nicU.getPersoRegisterStatus().equals(NicUploadJob.RECORD_STATUS_ERROR))
	 * && StringUtils.isEmpty(nicU.getTempPassportNo()) ){
	 * 
	 * PassportInfo obj = new PassportInfo(); NicTransaction nicTransaction =
	 * nicTransactionService .findById(nicU.getTransactionId());
	 * NicRegistrationData nicRegistrationData = nicRegistrationDataService
	 * .findById(nicU.getTransactionId());
	 * obj.setFullName(nicRegistrationData.getSurnameLine1()); obj.setDob(new
	 * java.sql.Date(nicRegistrationData .getDateOfBirth().getTime()));
	 * obj.setAddress(nicRegistrationData.getAddressLine1());
	 * obj.setGender(nicRegistrationData.getGender());
	 * obj.setPassportType(nicTransaction.getPassportType());
	 * obj.setPid(nicTransaction.getNin());
	 * obj.setPlaceOfBirth(nicRegistrationData.getPlaceOfBirth());
	 * obj.setPlaceOfIssue(nicTransaction.getIssSiteCode());
	 * obj.setTransactionId(nicTransaction.getTransactionId());
	 * 
	 * // Tạm thời đang fix cố định nơi perso if (nicTransaction.getPrintPerso()
	 * != null && nicTransaction.getPrintPerso() > 0)
	 * obj.setPlacePersoId(nicTransaction.getPrintPerso()); else
	 * obj.setPlacePersoId((long) 1); try { List<NicTransactionAttachment>
	 * nicTransAttach = nicTransactionAttachmentService
	 * .findNicTransactionAttachments( nicU.getTransactionId(), "PH_CAP", "01");
	 * if (nicTransAttach != null)
	 * obj.setPicture(nicTransAttach.get(0).getDocData()); } catch (Exception e)
	 * { }
	 * 
	 * passport.add(obj); } }
	 * 
	 * if(passport != null && passport.size() > 0)
	 * hPP_.setListPassportInfo(passport);
	 * 
	 * if(hPP_.getListPassportInfo() != null &&
	 * hPP_.getListPassportInfo().size() > 0) handoverlist.add(hPP_); } }
	 * rep.setResponse(handoverlist); prModel.setStatus(99);
	 * prModel.setMessage(""); } } catch (Exception e) { // TODO Auto-generated
	 * catch block prModel.setMessage(e.getMessage()); e.printStackTrace(); }
	 * 
	 * rep.setProperty(prModel); return rep; }
	 */

	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("/updateStatusHandover")
	public ResponseBase<Boolean> updateStatusHandover(HanvdoverRequest req) {
		ResponseBase<Boolean> rep = new ResponseBase<Boolean>();
		PropertyModel prModel = new PropertyModel();
		prModel.setCode(-1);
		prModel.setMessage("error");
		rep.setResponse(false);
		// Tìm danh sách theo Id
		try {
			NicListHandover hd = listHandoverService.findHandoverByCriteria(
					req.getHandoverId(), NicListHandover.TYPE_LIST_B, 1)
					.getModel();
			if (hd != null) {
				// Cập nhật trạng thái đồng bộ cho Danh sách
				/*
				 * if(req.getStatus() == 1) hd.setIsSyncPerso(true); else
				 * hd.setIsSyncPerso(false);
				 */
				listHandoverService.createRecordHandover(hd);
				rep.setResponse(true);
				prModel.setCode(99);
				prModel.setMessage("success");

				/* Lưu bảng thống kê truyền nhận */
				this.saveOrUpRptData(Contants.URL_UPDATE_STATUS_HANDOVER, 1,
						null);

			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		rep.setProperty(prModel);
		return rep;
	}

	@GET
	@Produces("application/json")
	@Consumes("application/json")
	@Path("/listPendingDocumentByHandover/{code}")
	public ResponseBase<HandoverPassportInfo> listPendingDocumentByHandover(
			@PathParam("code") String code) {
		List<PassportInfo> passport = new ArrayList<PassportInfo>();
		PropertyModel prModel = new PropertyModel();
		prModel.setCode(-1);
		ResponseBase<HandoverPassportInfo> rep = new ResponseBase<HandoverPassportInfo>();
		try {
			// Tìm các danh sách đã hoàn thành
			Boolean loops = true;
			do {
				NicListHandover hd = null;
				BaseModelSingle<SyncQueueJob> baseFindQ = queueJobService
						.findQueueByStatus(code,
								Contants.CODE_STATUS_QUEUE_PENDING,
								TransactionService.CONFIG_TYPE_CA_THE_HOA);
				SyncQueueJob queue = baseFindQ.getModel();
				if (queue != null) {
					com.nec.asia.nic.comp.trans.domain.BaseModelSingle<NicListHandover> nicHan = listHandoverService
							.findByPackageId(new NicListHandoverId(queue
									.getObjectId(), NicListHandover.TYPE_LIST_B));
					hd = nicHan.getModel();
				}
				/*
				 * Bỏ phần này lấy danh sách trong hàng đợi
				 * List<NicUploadJobDto> persoListB =
				 * nicTransactionService.listPackageIDPerso(code); if(persoListB
				 * != null){ for(NicUploadJobDto dto : persoListB){
				 * NicListHandover nicHan =
				 * listHandoverService.findByPackageId(dto.getPackageId());
				 * ConfigurationWorkflow cfw =
				 * configurationWorkflowService.findSiteRepositoryBySite
				 * (nicHan.getSiteCode(), new Date(),
				 * TransactionService.CONFIG_TYPE_CA_THE_HOA); if(cfw == null){
				 * hd = nicHan; break; } } }
				 */
				if (hd != null) {
					// hd =
					// listHandoverService.findByPackageId(listNic.get(0).getPackageId());
					HandoverPassportInfo hPP_ = new HandoverPassportInfo();
					hPP_.setHandoverId(hd.getId().getPackageId());
					hPP_.setCreateBy(hd.getCreatorName());
					if (hd.getCreateDate() != null) {
						String date = DateUtil.parseDate(hd.getCreateDate(),
								DateUtil.FORMAT_YYYYMMDD);
						hPP_.setCreateDate(date);
					}
					hPP_.setIdQueue(queue.getId());
					// Tìm danh sách transaction
					BaseModelList<EppListHandoverDetail> lspack_ = eppListHandoverDetailService
							.getListPackageByPackageId(hd.getId()
									.getPackageId(), hd.getId().getTypeList());
					if (!lspack_.isError() || lspack_.getMessage() != null) {
						throw new Exception(lspack_.getMessage());
					}
					if (lspack_.getListModel() != null
							&& lspack_.getListModel().size() > 0) {
						for (EppListHandoverDetail pack_ : lspack_
								.getListModel()) {
							if (("D").equals(pack_.getApproveStage())) {
								String transId = pack_.getTransactionId();
								BaseModelSingle<NicUploadJob> bGetUJ = uploadJobSerivce
										.getUploadJobByTransactionIdSinger(
												null, transId);
								NicUploadJob nicU = bGetUJ.getModel();
								PassportInfo obj = new PassportInfo();
								NicTransaction nicTransaction = nicU
										.getNicTransaction();
								NicRegistrationData nicRegistrationData = nicTransaction
										.getNicRegistrationData();
								/* Lấy danh sách A */
								EppListHandoverDetail detailListA = eppListHandoverDetailService
										.findTransactionByIdOrType(
												nicU.getTransactionId(),
												NicListHandover.TYPE_LIST_A)
										.getModel();

								obj.setFullName(com.nec.asia.nic.utils.HelperClass
										.createFullName(nicRegistrationData
												.getSurnameFull(),
												nicRegistrationData
														.getMiddlenameFull(),
												nicRegistrationData
														.getFirstnameFull()));
								// String onlyY = new java.sql.Date(
								// nicRegistrationData.getDateOfBirth()
								// .getTime()).toString();
								// String split[] = onlyY.split("-");
								// String year = split[0];
								// String month = split[1];

								if (nicRegistrationData.getDateOfBirth() != null) {
									if (nicRegistrationData.getDefDateOfBirth()
											.equals("D"))
										obj.setDob(DateUtil.parseDate(
												nicRegistrationData
														.getDateOfBirth(),
												DateUtil.FORMAT_YYYYMMDD));
									else if (nicRegistrationData
											.getDefDateOfBirth().equals("Y")) {
										obj.setDob(DateUtil.parseDate(
												nicRegistrationData
														.getDateOfBirth(),
												DateUtil.FORMAT_YYYY));
									} else {
										obj.setDob(DateUtil.parseDate(
												nicRegistrationData
														.getDateOfBirth(),
												"yyyyMM"));
									}
								}
								String address = "";
								if (nicRegistrationData.getAddressLine1() != null) {
									address = nicRegistrationData
											.getAddressLine1();
								}
								if (nicRegistrationData.getAddressLine2() != null) {
									address += ", "
											+ codesService.findByStringCodeId(
													CodeValues.CODE_ID_VILLAGE,
													nicRegistrationData
															.getAddressLine2())
													.getCodeValueDesc();
								}
								if (nicRegistrationData.getAddressLine3() != null) {
									address += ", "
											+ codesService
													.findByStringCodeId(
															CodeValues.CODE_ID_TOWN_VILLAGE,
															nicRegistrationData
																	.getAddressLine3())
													.getCodeValueDesc();
								}
								if (nicRegistrationData.getAddressLine4() != null) {
									address += ", "
											+ codesService
													.findByStringCodeId(
															CodeValues.CODE_ID_DISTRICT,
															nicRegistrationData
																	.getAddressLine4())
													.getCodeValueDesc();
								}
								obj.setDocType(nicTransaction.getTransactionSubType());
								obj.setAddress(address);
								obj.setGender(nicRegistrationData.getGender());
								obj.setPassportType(nicTransaction
										.getPassportType());
								obj.setPid(nicTransaction.getNin());
								obj.setPlaceOfBirth(nicRegistrationData
										.getPlaceOfBirth());
								obj.setPlaceOfIssue(nicTransaction
										.getIssSiteCode());
								obj.setTransactionId(nicTransaction
										.getTransactionId());
								obj.setPlaceOfBirth(nicRegistrationData
										.getPlaceOfBirth().trim());
								obj.setNationality(nicRegistrationData
										.getNationality());
								obj.setReceiptNo(nicTransaction.getRecieptNo());
								if (nicTransaction.getIsEpassport().equals("Y"))
									obj.setStylePassport(true);
								else
									obj.setStylePassport(false);

								obj.setHandoverA(detailListA != null ? detailListA
										.getPackageId() : null);
								if (nicTransaction.getEstDateOfCollection() != null) {
									obj.setEstOfRecieve(DateUtil.parseDate(
											nicTransaction
													.getEstDateOfCollection(),
											DateUtil.FORMAT_YYYYMMDD));
								}
								obj.setRegSiteCode(nicTransaction
										.getRegSiteCode());
								SiteRepository siteR = siteService
										.getSiteRepoById(nicTransaction
												.getIssSiteCode());
								if (siteR != null)
									obj.setNamePlaceOfIssue(siteR.getSiteName());
								else
									obj.setNamePlaceOfIssue(nicTransaction
											.getIssSiteCode());

								// Lấy chi tiết địa chỉ=====================
								String district = "";
								String town_village = "";
								if (StringUtils.isNotEmpty(nicRegistrationData
										.getAddressLine4())) {
									CodeValues value = codesService
											.getCodeValueByIdName(
													"TOWN_VILLAGE",
													nicRegistrationData
															.getAddressLine4());
									if (value != null)
										town_village = value.getCodeValueDesc();
								}

								if (StringUtils.isNotEmpty(nicRegistrationData
										.getAddressLine5())) {
									CodeValues value = codesService
											.getCodeValueByIdName("DISTRICT",
													nicRegistrationData
															.getAddressLine5());
									if (value != null)
										district = value.getCodeValueDesc();
								}
								String detailAddress = nicRegistrationData
										.getAddressLine1();
								if (StringUtils.isNotEmpty(town_village)) {
									detailAddress = detailAddress + ", "
											+ town_village;
								}

								if (StringUtils.isNotEmpty(district)) {
									detailAddress = detailAddress + ", "
											+ district;
								}
								obj.setDetailaddress(detailAddress);
								// ===========================================

								// Tạm thời đang fix cố định nơi perso
								/*
								 * if (nicTransaction.getPrintPerso() != null &&
								 * nicTransaction.getPrintPerso() > 0)
								 * obj.setPlacePersoId
								 * (nicTransaction.getPrintPerso()); else
								 * obj.setPlacePersoId((long) 1);
								 */
								try {
									List<NicTransactionAttachment> nicTransAttach = nicTransactionAttachmentService
											.getNicTransactionAttachments(
													nicU.getTransactionId(),
													new String[] { Contants.DOC_TYPE_PHOTO_CAPTURE },
													new String[] { "01", "1" });
									if (nicTransAttach != null)
										obj.setPicture(nicTransAttach.get(0)
												.getDocData());
								} catch (Exception e) {
								}

								// Lấy dữ liệu trẻ em đi kèm
								/*
								 * EppPersonsDto persons = new EppPersonsDto();
								 * List<EppPersonDto> lstPerson = new
								 * ArrayList<EppPersonDto>(); String xmlPerson =
								 * nicTransaction .getInfoPerson();
								 * 
								 * if (StringUtils.isNotEmpty(xmlPerson)) {
								 * JSONObject xmlJSONObj = XML
								 * .toJSONObject(xmlPerson); if (xmlJSONObj !=
								 * null) { if (xmlJSONObj.has("EPP_PERSONS") &&
								 * !xmlJSONObj .isNull("EPP_PERSONS")) {
								 * JSONObject resp = xmlJSONObj
								 * .getJSONObject("EPP_PERSONS"); if
								 * (resp.has("EPP_PERSON") &&
								 * !resp.isNull("EPP_PERSON")) { Object item =
								 * resp .get("EPP_PERSON"); try { if (item
								 * instanceof JSONArray) { JSONArray arrayResp =
								 * (JSONArray) item; for (int i = 0; i <
								 * arrayResp .length(); i++) { EppPersonDto
								 * person = new EppPersonDto(); JSONObject
								 * myResponse = new JSONObject(); myResponse =
								 * arrayResp .getJSONObject(i); String id = "";
								 * if (myResponse .has("PLACE_OF_BIRTH_ID") &&
								 * !myResponse .isNull("PLACE_OF_BIRTH_ID")) {
								 * person.setPlaceOfBirthId(myResponse
								 * .getString("PLACE_OF_BIRTH_ID")); } if
								 * (myResponse .has("SEARCH_NAME") &&
								 * !myResponse .isNull("SEARCH_NAME")) {
								 * person.setSearchName(myResponse
								 * .getString("SEARCH_NAME")); } if (myResponse
								 * .has("NAME") && !myResponse .isNull("NAME"))
								 * { person.setName(myResponse
								 * .getString("NAME")); } if (myResponse
								 * .has("DATE_OF_BIRTH") && !myResponse
								 * .isNull("DATE_OF_BIRTH")) {
								 * person.setDateOfBirth(String
								 * .valueOf(myResponse
								 * .getInt("DATE_OF_BIRTH"))); } if (myResponse
								 * .has("GENDER") && !myResponse
								 * .isNull("GENDER")) {
								 * person.setGender(myResponse
								 * .getString("GENDER")); } if (myResponse
								 * .has("ID") && !myResponse .isNull("ID")) { id
								 * = String .valueOf(myResponse .getInt("ID"));
								 * person.setId(id); } if (myResponse
								 * .has("TYPE_") && !myResponse
								 * .isNull("TYPE_")) {
								 * person.setType_(myResponse
								 * .getString("TYPE_")); } if (myResponse
								 * .has("NATIONALITY_ID") && !myResponse
								 * .isNull("NATIONALITY_ID")) {
								 * person.setNationalityId(myResponse
								 * .getString("NATIONALITY_ID")); }
								 * 
								 * // Lấy dữ liệu if (StringUtils
								 * .isNotEmpty(id)) {
								 * List<NicTransactionAttachment> nicAttch =
								 * nicTransactionAttachmentService
								 * .findFacialTEById( nicU.getTransactionId(),
								 * "PH_TE", id); if (nicAttch != null &&
								 * nicAttch .size() > 0) {
								 * person.setPicture(nicAttch .get(0)
								 * .getDocData()); } }
								 * 
								 * lstPerson .add(person); } } else {
								 * EppPersonDto person = new EppPersonDto();
								 * JSONObject myResponse = (JSONObject) item;
								 * String id = ""; if (myResponse
								 * .has("PLACE_OF_BIRTH_ID") && !myResponse
								 * .isNull("PLACE_OF_BIRTH_ID")) {
								 * person.setPlaceOfBirthId(myResponse
								 * .getString("PLACE_OF_BIRTH_ID")); } if
								 * (myResponse .has("SEARCH_NAME") &&
								 * !myResponse .isNull("SEARCH_NAME")) {
								 * person.setSearchName(myResponse
								 * .getString("SEARCH_NAME")); } if (myResponse
								 * .has("NAME") && !myResponse .isNull("NAME"))
								 * { person.setName(myResponse
								 * .getString("NAME")); } if (myResponse
								 * .has("DATE_OF_BIRTH") && !myResponse
								 * .isNull("DATE_OF_BIRTH")) {
								 * person.setDateOfBirth(String
								 * .valueOf(myResponse
								 * .getInt("DATE_OF_BIRTH"))); } if (myResponse
								 * .has("GENDER") && !myResponse
								 * .isNull("GENDER")) {
								 * person.setGender(myResponse
								 * .getString("GENDER")); } if (myResponse
								 * .has("ID") && !myResponse .isNull("ID")) { id
								 * = String .valueOf(myResponse .getInt("ID"));
								 * person.setId(id); } if (myResponse
								 * .has("TYPE_") && !myResponse
								 * .isNull("TYPE_")) {
								 * person.setType_(myResponse
								 * .getString("TYPE_")); } if (myResponse
								 * .has("NATIONALITY_ID") && !myResponse
								 * .isNull("NATIONALITY_ID")) {
								 * person.setNationalityId(myResponse
								 * .getString("NATIONALITY_ID")); }
								 * 
								 * // Lấy dữ liệu if (StringUtils
								 * .isNotEmpty(id)) {
								 * List<NicTransactionAttachment> nicAttch =
								 * nicTransactionAttachmentService
								 * .findFacialTEById( nicU.getTransactionId(),
								 * "PH_TE", id); if (nicAttch != null &&
								 * nicAttch .size() > 0) {
								 * person.setPicture(nicAttch .get(0)
								 * .getDocData()); } }
								 * 
								 * lstPerson.add(person); } } catch (Exception
								 * e) {
								 * 
								 * } } } } } if (lstPerson != null &&
								 * lstPerson.size() > 0) {
								 * persons.setEpp_person(lstPerson); }
								 * 
								 * obj.setEpp_persons(persons);
								 */

								// dữ liệu hộ chiếu cũ gần nhất theo mã cá nhân
								// (nếu có)
								if (StringUtils.isNotBlank(nicTransaction
										.getPersonCode())) {
									List<EppPerson> listPerson = new ArrayList<EppPerson>();
									NicDocumentData docDataRs = null;
									List<NicDocumentData> listDocRs = new ArrayList<NicDocumentData>();
									EppPerson person = eppPersonService
											.findPersonByPersonCode(nicTransaction
													.getPersonCode());
									if (person != null) {
										listPerson.add(person);
										if (StringUtils.isNotBlank(person
												.getOrgPerson())) {
											listPerson = eppPersonService
													.findListPersonByOrgPersonCode(person
															.getOrgPerson());
										}
									}
									if (listPerson != null
											&& listPerson.size() > 0) {
										for (EppPerson eppPerson : listPerson) {
											List<NicTransaction> listTranOrg = nicTransactionService
													.findTranByPersonCode(eppPerson
															.getPersonCode());
											for (NicTransaction nicTran : listTranOrg) {
												List<NicDocumentData> listDocData = documentDataService
														.findListDocByTranId(nicTran
																.getTransactionId());
												if (listDocData != null
														&& listDocData.size() > 0) {
													listDocRs
															.addAll(listDocData);
												}
											}
										}
									}
									if (listDocRs.size() > 0) {
										for (NicDocumentData docData : listDocRs) {
											if (docDataRs == null
													|| (docDataRs != null && docData
															.getCreateDatetime()
															.getTime() > docDataRs
															.getCreateDatetime()
															.getTime())) {
												docDataRs = docData;
											}
										}
									}
									if (docDataRs != null) {
										SimpleDateFormat fm = new SimpleDateFormat(
												DateUtil.FORMAT_YYYYMMDD);
										NicTransaction tranRs = docDataRs
												.getNicTransaction();
										NicRegistrationData regRs = tranRs != null ? tranRs
												.getNicRegistrationData()
												: null;
										PrevPassport prevPassport = new PrevPassport();
										prevPassport.setCancelBy(docDataRs
												.getCancelBy());
										prevPassport
												.setCancelDate(docDataRs
														.getCancelDatetime() != null ? DateUtil.parseDate(
														docDataRs
																.getCancelDatetime(),
														DateUtil.FORMAT_YYYYMMDD)
														: null);
										prevPassport.setCancelReason(docDataRs
												.getCancelReason());
										prevPassport.setCancelType(docDataRs
												.getCancelType());
										prevPassport.setChipSerialNo(docDataRs
												.getChipSerialNo());
										prevPassport.setDateOfIssue(docDataRs
												.getDateOfIssue() != null ? fm
												.format(docDataRs
														.getDateOfIssue())
												: null);
										prevPassport.setDateOfExpiry(docDataRs
												.getDateOfExpiry() != null ? fm
												.format(docDataRs
														.getDateOfExpiry())
												: null);
										// prevPassport.setDescription("");
										prevPassport.setIcaoLine1(docDataRs
												.getIcaoLine1());
										prevPassport.setIcaoLine2(docDataRs
												.getIcaoLine2());
										prevPassport.setIsEpassport(tranRs
												.getIsEpassport());
										prevPassport.setPassportNo(docDataRs
												.getId().getPassportNo());
										prevPassport.setPassportType(docDataRs
												.getPassportType());
										prevPassport.setPid(tranRs.getNin());
										prevPassport
												.setPlaceOfIssueId(docDataRs
														.getPlaceOfIssueCode());
										SiteRepository siteIss = siteService
												.getSiteRepoById(docDataRs
														.getPlaceOfIssueCode());
										prevPassport
												.setPlaceOfIssueName(siteIss != null ? siteIss
														.getSiteName() : null);
										prevPassport.setSignerName(docDataRs
												.getSigner());
										prevPassport
												.setSignerPosition(docDataRs
														.getPositionSigner());
										prevPassport.setStatus(docDataRs
												.getStatus());
										prevPassport.setPhoiSerialNo(docDataRs
												.getSerialNo());
										if (regRs != null) {
											prevPassport
													.setDateOfBirth(regRs
															.getDateOfBirth() != null ? fm.format(regRs
															.getDateOfBirth())
															: null);
											prevPassport
													.setDefDateOfBirth(regRs
															.getDefDateOfBirth());
											prevPassport
													.setFullName(com.nec.asia.nic.utils.HelperClass.createFullName(
															regRs.getSurnameFull(),
															regRs.getMiddlenameFull(),
															regRs.getFirstnameFull()));
											prevPassport.setGender(regRs
													.getGender());
										}
										obj.setPrevPassport(prevPassport);
									}
								}

								passport.add(obj);

								/*
								 * update trạng thái hồ sơ nicTransaction
								 * .setTransactionStatus(Contants.
								 * TRANSACTION_STATUS_PERSO_REGISTER_COMPLETED);
								 * nicTransactionService
								 * .saveOrUpdateTransaction(nicTransaction);
								 */
							}
						}
						if (passport != null && passport.size() > 0) {
							hPP_.setListPassportInfo(passport);
							hPP_.setAmountDoc(passport.size());
							rep.setResponse(hPP_);
							prModel.setCode(99);
							prModel.setMessage("");
							queueJobService.updateStatusQueueJob(queue.getId(),
									Contants.CODE_STATUS_QUEUE_DOING);

							/* Lưu bảng thống kê truyền nhận */
							if (queue.getDateUpdateStatusHanding() == null) {
								this.saveOrUpRptData(
										Contants.URL_LIST_PENDING_DOCUMENT_BY_HANDOVER,
										1, code);
							}
							break;
						} else {
							// hd.setIsSyncPerso(true);
							listHandoverService.createRecordHandover(hd);
						}
					} else {
						// hd.setIsSyncPerso(true);
						listHandoverService.createRecordHandover(hd);
					}
				} else {
					break;
				}
			} while (loops);
		} catch (Exception e) {
			e.printStackTrace();
			prModel.setCode(414);
			prModel.setMessage(Contants.MESSAGE_EXCEPTION);
			/*
			 * Lưu dữ liệu nếu có Exception
			 */
			String dataResponse = "";
			String keyId = "LPDBH_"
					+ new SimpleDateFormat("ddMMyyyy").format(new Date());
			try {
				dataResponse = new ObjectMapper().writeValueAsString(rep);
			} catch (Exception e2) {
				logger.error(e2.getMessage());
			}
			this.saveException(Contants.TYPE_LPDBH,
					Contants.URL_LIST_PENDING_DOCUMENT_BY_HANDOVER, code,
					keyId, dataResponse, e);
		}

		rep.setProperty(prModel);
		return rep;
	}

	@POST
	@Produces("application/json")
	@Consumes("application/json")
	@Path("/updatePassportNo")
	public ResponseBase<List<InfoPassportC>> updatePassportNo(
			ListPassportUpdateInfo listPPInfo) {
		ResponseBase<List<InfoPassportC>> rep = new ResponseBase<List<InfoPassportC>>();
		PropertyModel prModel = new PropertyModel();
		List<InfoPassportC> listInfoPP = null;
		prModel.setCode(Integer.parseInt(Contants.CODE_INPUT_FAILD));
		try {
			if (listPPInfo.getListInfo() != null
					&& listPPInfo.getListInfo().size() > 0) {
				for (PassportUpdateInfo request : listPPInfo.getListInfo()) {
					String trans = request.getTransactionId();
					String ppno = request.getPassportNo();
					if (StringUtils.isNotBlank(trans)
							&& StringUtils.isNotBlank(ppno)) {

						/* cập nhật chipSerialNo nếu có */
						if (StringUtils.isNotBlank(request.getChipSerialNo())) {
							NicDocumentData nicDocData = documentDataService
									.findDocDataById(trans, ppno);
							if (nicDocData != null) {
								nicDocData.setChipSerialNo(request
										.getChipSerialNo());
								nicDocData.setSerialNo(request.getSerialNo());
								BaseModelSingle<Void> baseSaveDocData = documentDataService
										.saveOrUpdateDocData(nicDocData);
								if (!baseSaveDocData.isError()
										|| baseSaveDocData.getMessage() != null) {
									throw new Exception(
											baseSaveDocData.getMessage());
								}
							}
						}

						// === Xử lý tạo biên nhận ===============
						BaseModelSingle<NicUploadJob> bGetUJ = uploadJobService
								.getUploadJobByTransactionIdSinger(null, trans);
						NicUploadJob obj = bGetUJ.getModel();
						NicTransaction transObj = nicTransactionService
								.findById(trans, false, true, true, false);// has
																			// trans
																			// doc
																			// and
																			// registration
																			// data
						if (obj == null) {
							prModel.setCode(Contants.ENUM_STATUS_CODE_HTTP_ERROR);
							prModel.setMessage("Không có dữ liệu hồ sơ hộ chiếu.");
							break;
						}
						// Boolean isCreate = CheckPersoRegister(trans,
						// obj.getTempPassportNo(), obj);
						// if (isCreate) {
						// Parameters para = parametersService
						// .getParamDetails("SYSTEM",
						// "RECIEPT_NO_PARAM");
						// if (para != null) {
						// int number = Integer.parseInt(para
						// .getParaShortValue());
						// int numberN = number + 1;
						// para.setParaShortValue("" + numberN);
						// parametersService.saveOrUpdate(para);
						//
						// Calendar now = Calendar.getInstance();
						// int year = now.get(Calendar.YEAR);
						// int month = now.get(Calendar.MONTH) + 1; // Note:
						// // zero
						// // based!
						// int day = now.get(Calendar.DAY_OF_MONTH);
						// String date = "" + year + month + day;
						// String receiptNo = String
						// .format("%09d", number);
						// obj.setReceiptNo(para.getParaLobValue() + date
						// + receiptNo);
						// uploadJobService.saveOrUpdate(obj);
						// }
						// } else {
						/*
						 * logger.info(
						 * "missing data perso or error validate data perso" );
						 */
						// }

						// Tạo hàng đợi
						/*
						 * QueuesJobSchedule que = new QueuesJobSchedule();
						 * que.setCode(trans);
						 * que.setTypeTransaction(QueuesJobSchedule
						 * .TYPE_TRANSACTION_LDS );
						 * que.setPassportType(transObj.getPassportType());
						 * que.setDescription("Tao moi xu ly dong goi LDS. ");
						 * que.setCode(QueuesJobSchedule.STATUS_JOB_PENDING);
						 * que.setTypeLogJob(transObj.getTransactionType());
						 * queuesJobScheduleService.saveOrUpdate(que);
						 */

						/* update trạng thái hồ sơ */
						transObj.setTransactionStatus(Contants.TRANSACTION_STATUS_PERSO_REGISTER_COMPLETED);
						nicTransactionService.saveOrUpdateTransaction(transObj);

						/* Đưa vào hàng đợi */
						// String siteCode = transObj.getRegSiteCode();
						// ConfigurationWorkflow cfw =
						// configurationWorkflowService
						// .findSiteRepositoryBySite(
						// siteCode,
						// new Date(),
						// TransactionService.CONFIG_TYPE_CA_THE_HOA,
						// true).getModel();
						// if (cfw != null) {
						// this.addObjToQueueJob(trans, OBJECT_TYPE_PERSO,
						// cfw.getSiteIdTo(), null);
						// /* Lưu bảng thống kê truyền nhận */
						// this.saveOrUpRptData(
						// Contants.URL_UPDATE_PASSPORT_NO, 1,
						// cfw.getSiteIdTo());
						// } else {
						// String code = nicTransactionService
						// .getSitePerso(trans);
						// if (StringUtils.isNotEmpty(code)) {
						// this.addObjToQueueJob(trans, OBJECT_TYPE_PERSO,
						// code, null);
						// /* Lưu bảng thống kê truyền nhận */
						// this.saveOrUpRptData(
						// Contants.URL_UPDATE_PASSPORT_NO, 1,
						// code);
						// }
						//
						// }
						// =======================================

						List<NicUploadJob> nicUploadJobs = uploadJobSerivce
								.findAllByTransactionId(trans);
						if (nicUploadJobs != null) {
							NicUploadJob nicUploadJob = nicUploadJobs.get(0);
							nicUploadJob.setTempPassportNo(ppno);
							uploadJobSerivce.saveOrUpdateService(nicUploadJob);
							// xóa dữ liệu cũ nếu có, tìm theo số hồ sơ
							// tạo bản ghi mới với trạng thái INIT và lưu lại
							// PersoStatus persoStatus = new PersoStatus();

							Date startTime = new Date();
							String transactionStatus = JOB_STATE_PASSPORT_REGISTER
									+ STAGE_COMPLETED;
							this.saveTransactionLog(trans,
									JOB_STATE_PASSPORT_REGISTER,
									transactionStatus, startTime, new Date(),
									null, "",
									nicUploadJob.getJobReprocessCount());

							// try {
							// String docChar = ppno.replaceAll("[^A-Za-z]+",
							// "");
							// String docNum = ppno.replaceAll("\\D+", "");
							// EppInventoryItemsDetail epp =
							// eppInventoryItemsDetailService
							// .findByCondition(docChar, docNum);
							// if (epp != null) {
							// epp.setStatus(EppInventory.DA_IN);
							// epp.setUpdateTs(new Date());
							// eppInventoryItemsDetailService
							// .saveOrUpdate(epp);
							// }
							// } catch (Exception ex) {
							//
							// }

							prModel.setCode(Integer
									.parseInt(Contants.CODE_SUCCESS));
							prModel.setMessage(Contants.MESSAGE_SUCCESS);

							/*
							 * uploadJobService.approveJobStatus(
							 * Long.valueOf(nicUploadJob.getUploadJobId()),
							 * "PERSO", "PERSO-WORKSTATION",
							 * NicTransactionLog.TRANSACTION_STAGE_INVESTIGATION
							 * ,
							 * NicTransactionLog.TRANSACTION_STATUS_NIC_APPROVED
							 * );
							 */

							try {
								/*
								 * ==============================================
								 * ======== ====================
								 */
								/*
								 * ==== GỬI THÔNG TIN SANG A08 ĐỂ ĐÓNG GÓI
								 * ==================
								 */

								/*
								 * DataRegistrationLDS response =
								 * prepareDataLds(trans, ppno, nicUploadJob);
								 * int count = 0;
								 * while(StringUtils.isEmpty(tokenA08)){
								 * getTokenA08(); count++; if(count > 3){break;}
								 * }
								 * 
								 * 
								 * // / Kiểm tra lấy token từ A08 if
								 * (!StringUtils.isEmpty(tokenA08)) {
								 * ObjectMapper mapper = new ObjectMapper();
								 * String jsonModel = mapper
								 * .writeValueAsString(response); String
								 * requestUrl =
								 * "http://192.168.1.15:8044/app/rest/v2/PersoData/createPassport"
								 * ; String getResult =
								 * sendPostRequestSync(requestUrl, jsonModel,
								 * tokenA08, "Bearer");
								 * 
								 * if (!StringUtils.isEmpty(getResult)) { //
								 * /Luu ma goi tin
								 * nicUploadJob.setReceiptNo(getResult);
								 * uploadJobSerivce.saveOrUpdate(nicUploadJob);
								 * } } else { // TODO: Xử lý gọi lại theo JOB }
								 */
								/*
								 * ==== END --- GỬI THÔNG TIN SANG A08 ĐỂ ĐÓNG
								 * GÓI ==================
								 */
								/*
								 * ==============================================
								 * ======== ====================
								 */
							} catch (Exception e) {

							}

						} else {
							prModel.setCode(404);
							prModel.setMessage("Khong tim thay du lieu ban ghi - "
									+ trans);
							rep.setProperty(prModel);
							return rep;
						}
					} else {
						prModel.setMessage("Thieu du lieu dau vao. TransationId="
								+ trans + " / passportNo=" + ppno);
						rep.setProperty(prModel);
						return rep;
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			prModel.setCode(414);
			prModel.setMessage(Contants.MESSAGE_EXCEPTION);
			/*
			 * Lưu dữ liệu nếu có Exception
			 */
			String dataResponse = "";
			String dataRequest = "";
			String keyId = "UPN_"
					+ new SimpleDateFormat("ddMMyyyy").format(new Date());
			try {
				dataResponse = new ObjectMapper().writeValueAsString(rep);
				dataRequest = new ObjectMapper().writeValueAsString(listPPInfo);
			} catch (Exception e2) {
				logger.error(e2.getMessage());
			}
			this.saveException(Contants.TYPE_UPN,
					Contants.URL_UPDATE_PASSPORT_NO, dataRequest, keyId,
					dataResponse, e);
		}
		rep.setProperty(prModel);
		return rep;
	}

	@POST
	@Produces("application/json")
	@Consumes("application/json")
	@Path("/updatePassportNoOld/{trans}/{ppNo}")
	public ResponseBase<Boolean> updatePassportNoOld(
			@PathParam("trans") String trans, @PathParam("ppNo") String ppno) {
		ResponseBase<Boolean> rep = new ResponseBase<Boolean>();
		PropertyModel prModel = new PropertyModel();
		rep.setResponse(false);
		prModel.setCode(-1);

		if (trans != "" && ppno != "") {
			try {
				// === Xử lý tạo biên nhận ===============
				BaseModelSingle<NicUploadJob> bGetUJ = uploadJobService
						.getUploadJobByTransactionIdSinger(null, trans);
				NicUploadJob obj = bGetUJ.getModel();
				NicTransaction transObj = nicTransactionService.findById(trans,
						false, true, true, false);// has trans doc and
													// registration data
				Boolean isCreate = CheckPersoRegister(trans,
						obj.getTempPassportNo(), obj);
				if (isCreate) {
					Parameters para = parametersService.getParamDetails(
							"SYSTEM", "RECIEPT_NO_PARAM");
					if (para != null) {
						int number = Integer.parseInt(para.getParaShortValue());
						int numberN = number + 1;
						para.setParaShortValue("" + numberN);
						parametersService.saveOrUpdate(para);

						Calendar now = Calendar.getInstance();
						int year = now.get(Calendar.YEAR);
						int month = now.get(Calendar.MONTH) + 1; // Note: zero
																	// based!
						int day = now.get(Calendar.DAY_OF_MONTH);
						String date = "" + year + month + day;
						String receiptNo = String.format("%09d", number);
						obj.setReceiptNo(para.getParaShortValue() + date
								+ receiptNo);
						uploadJobService.saveOrUpdate(obj);
					}
				} else {
					/*
					 * logger.info("missing data perso or error validate data perso"
					 * );
					 */
				}

				// Tạo hàng đợi
				QueuesJobSchedule que = new QueuesJobSchedule();
				que.setCode(trans);
				que.setTypeTransaction(QueuesJobSchedule.TYPE_TRANSACTION_LDS);
				que.setPassportType(transObj.getPassportType());
				que.setDescription("Tao moi xu ly dong goi LDS. ");
				que.setStatus(QueuesJobSchedule.STATUS_JOB_PENDING);
				que.setTypeLogJob(transObj.getTransactionType());
				queuesJobScheduleService.saveOrUpdate(que);

				// =======================================

				List<NicUploadJob> nicUploadJobs = uploadJobSerivce
						.findAllByTransactionId(trans);
				if (nicUploadJobs != null) {
					NicUploadJob nicUploadJob = nicUploadJobs.get(0);
					nicUploadJob.setTempPassportNo(ppno);
					uploadJobSerivce.saveOrUpdate(nicUploadJob);

					NicTransaction nicTransaction = nicUploadJob
							.getNicTransaction();
					/* update trạng thái hồ sơ */
					nicTransaction
							.setTransactionStatus(Contants.TRANSACTION_STATUS_PERSO_REGISTER_COMPLETED);
					nicTransactionService
							.saveOrUpdateTransaction(nicTransaction);

					Date startTime = new Date();
					String transactionStatus = JOB_STATE_PASSPORT_REGISTER
							+ STAGE_COMPLETED;
					this.saveTransactionLog(trans, JOB_STATE_PASSPORT_REGISTER,
							transactionStatus, startTime, new Date(), null, "",
							nicUploadJob.getJobReprocessCount());

					rep.setResponse(true);
					prModel.setCode(99);
					prModel.setMessage("");

					/* Lưu bảng thống kê truyền nhận */
					this.saveOrUpRptData(Contants.URL_UPDATE_PASSPORT_NO_OLD,
							1, null);

					/*
					 * uploadJobService.approveJobStatus(
					 * Long.valueOf(nicUploadJob.getUploadJobId()), "PERSO",
					 * "PERSO-WORKSTATION",
					 * NicTransactionLog.TRANSACTION_STAGE_INVESTIGATION,
					 * NicTransactionLog.TRANSACTION_STATUS_NIC_APPROVED);
					 */

					try {
						/*
						 * ======================================================
						 * ====================
						 */
						/*
						 * ==== GỬI THÔNG TIN SANG A08 ĐỂ ĐÓNG GÓI
						 * ==================
						 */

						/*
						 * DataRegistrationLDS response = prepareDataLds(trans,
						 * ppno, nicUploadJob); int count = 0;
						 * while(StringUtils.isEmpty(tokenA08)){ getTokenA08();
						 * count++; if(count > 3){break;} }
						 * 
						 * 
						 * // / Kiểm tra lấy token từ A08 if
						 * (!StringUtils.isEmpty(tokenA08)) { ObjectMapper
						 * mapper = new ObjectMapper(); String jsonModel =
						 * mapper .writeValueAsString(response); String
						 * requestUrl =
						 * "http://192.168.1.15:8044/app/rest/v2/PersoData/createPassport"
						 * ; String getResult = sendPostRequestSync(requestUrl,
						 * jsonModel, tokenA08, "Bearer");
						 * 
						 * if (!StringUtils.isEmpty(getResult)) { // /Luu ma goi
						 * tin nicUploadJob.setReceiptNo(getResult);
						 * uploadJobSerivce.saveOrUpdate(nicUploadJob); } } else
						 * { // TODO: Xử lý gọi lại theo JOB }
						 */
						/*
						 * ==== END --- GỬI THÔNG TIN SANG A08 ĐỂ ĐÓNG GÓI
						 * ==================
						 */
						/*
						 * ======================================================
						 * ====================
						 */
					} catch (Exception e) {

					}

				} else {
					prModel.setCode(-404);
					prModel.setMessage("Khong tim thay du lieu ban ghi");
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block

				prModel.setMessage(e.getMessage());
				e.printStackTrace();
			}
		} else {
			prModel.setCode(-101);
			prModel.setMessage("Thieu du lieu dau vao. TransationId=" + trans
					+ " / passportNo=" + ppno);
		}
		rep.setProperty(prModel);
		return rep;
	}

	@POST
	@Produces("application/json")
	@Consumes("application/json")
	@Path("/syncPassportByDay/{time}")
	public ResponseBase<ResponseFromA72> syncPassportByDay(
			@PathParam("time") String time) {
		ResponseBase<ResponseFromA72> rep = new ResponseBase<ResponseFromA72>();
		ResponseFromA72 resA72 = new ResponseFromA72();
		List<PassportA72> lstppA72 = new ArrayList<PassportA72>();

		PropertyModel prModel = new PropertyModel();
		prModel.setCode(-1);
		prModel.setMessage("error");
		try {
			Date date = new Date();
			if (StringUtils.isNotEmpty(time)) {
				try {
					// Cat chuoi ngay gio theo dang dd-MM-yyyy_HH-mm-ss
					String splitDate = time.split("_")[0];
					String splitTime = time.split("_")[1].replace("-", ":");
					// Kiểm tra cấu trúc time
					DateFormat format = new SimpleDateFormat(
							"dd-MM-yyyy HH:mm:ss", Locale.ENGLISH);
					date = format.parse(splitDate + " " + splitTime);
				} catch (Exception e) {
				}
			}

			List<NicDocumentData> lstData = documentDataDao.findSyncByDay(date);
			if (lstData != null && lstData.size() > 0) {
				for (NicDocumentData doc : lstData) {
					service.perso.model.sync.Person person = new service.perso.model.sync.Person();
					PassportA72 pp = new PassportA72();
					pp.setCreatedby(doc.getCreateBy());
					/*
					 * String pattern = "dd/MM/yyyy  hh:mm:ss"; DateFormat df =
					 * new SimpleDateFormat(pattern); if(doc.getCreateDatetime()
					 * != null)
					 * pp.setCreatest(df.format(doc.getCreateDatetime()));
					 * if(doc.getDateOfExpiry() != null)
					 * pp.setDateofexpiry(df.format(doc.getDateOfExpiry()));
					 * pp.setPassportno(doc.getId().getPassportNo());
					 * if(doc.getDateOfIssue() != null)
					 * pp.setDateofissue(df.format(doc.getDateOfIssue()));
					 */

					// Lấy dữ liệu cá nhân
					NicRegistrationData regis = nicRegistrationDataService
							.getNicDataById(doc.getId().getTransactionId());
					if (regis != null) {
						person.setName(com.nec.asia.nic.utils.HelperClass
								.createFullName(regis.getSurnameFull(),
										regis.getMiddlenameFull(),
										regis.getFirstnameFull()));
						person.setGender(regis.getGender());
						person.setNationality(regis.getNationality());

						/*
						 * if(regis.getDateOfBirth() != null)
						 * person.setDateofbirth
						 * (df.format(regis.getDateOfBirth()));
						 */
						person.setPobCode(regis.getPlaceOfBirth());
						String desPob = regis.getPlaceOfBirth();
						if (StringUtils.isNotEmpty(regis.getPlaceOfBirth())) {
							CodeValues codeV = codesService
									.getCodeValueByIdName("CODE_BirthPlace",
											regis.getPlaceOfBirth());
							if (codeV != null) {
								desPob = codeV.getCodeValueDesc();
							}
						}
						person.setPobName(desPob);
						String address = regis.getAddressLine1();
						String px = codesService.getCodeValueDescByIdName(
								"TOWN_VILLAGE", regis.getAddressLine4(), "");
						String tp = codesService.getCodeValueDescByIdName(
								"DISTRICT", regis.getAddressLine5(), "");
						if (StringUtils.isNotEmpty(px)) {
							address += ", " + px;
						}
						if (StringUtils.isNotEmpty(tp)) {
							address += ", " + tp;
						}
						person.setAddress(address);

						person.setFatherName(regis.getFatherFullname());
						person.setMotherName(regis.getMotherFullname());
						/*
						 * if(regis.getFatherDob() != null){ try{ String
						 * pattern1 = "dd/MM/yyyy"; DateFormat df1 = new
						 * SimpleDateFormat(pattern1);
						 * person.setFatherDob(df1.format
						 * (regis.getFatherDob())); }catch(Exception e){
						 * 
						 * } } if(regis.getMotherDob() != null){ try{ String
						 * pattern1 = "dd/MM/yyyy"; DateFormat df1 = new
						 * SimpleDateFormat(pattern1);
						 * person.setMotherDob(df1.format
						 * (regis.getMotherDob())); }catch(Exception e){
						 * 
						 * } }
						 */
					}

					// Lấy thông tin hồ sơ
					NicTransaction nicTran = nicTransactionService
							.getNicTransactionById(
									doc.getId().getTransactionId()).getModel();
					if (nicTran != null) {
						person.setIdnumber(nicTran.getNin());
						pp.setType(nicTran.getPassportType());
					}

					// Lấy IdPerson
					BaseModelSingle<NicUploadJob> bGetUJ = uploadJobSerivce
							.getUploadJobByTransactionIdSinger(null,
									nicTran.getTransactionId());
					NicUploadJob nicUp = bGetUJ.getModel();
					if (nicUp != null) {
						// person.setIdPerson(nicUp.getOriginalyPersonId());
					}

					// Lấy tài liệu đính kèm
					List<NicTransactionAttachment> lstattch = nicTransactionAttachmentService
							.getNicTransactionAttachments(
									doc.getId().getTransactionId(),
									new String[] {
											NicTransactionAttachment.DOC_TYPE_PHOTO_CAPTURE,
											NicTransactionAttachment.DOC_TYPE_FINGERPRINT },
									null);

					if (lstattch != null && lstattch.size() > 0) {
						for (NicTransactionAttachment item : lstattch) {
							service.perso.model.sync.Attachment attch = new service.perso.model.sync.Attachment();
							if (item.getDocType()
									.equals(NicTransactionAttachment.DOC_TYPE_PHOTO_CAPTURE)) {
								attch.setBase64(Base64.encodeBase64String(item
										.getDocData()));
								attch.setSerialNo(Integer.parseInt(item
										.getSerialNo()));
								attch.setType("PH");
								person.getAttachment().add(attch);
							} else {
								String encode1 = regis.getFpEncode().trim()
										.split(",")[0];
								String encode2 = regis.getFpEncode().trim()
										.split(",")[1];
								String srNo = item.getSerialNo();
								if (StringUtils.isNotEmpty(regis.getFpEncode())) {
									Integer split1 = Integer.parseInt(encode1);
									Integer split2 = Integer.parseInt(encode2);
									Integer serialNo = Integer.parseInt(srNo);
									if (item.getDocType()
											.equals(NicTransactionAttachment.DOC_TYPE_FINGERPRINT)
											&& (serialNo == split1 || serialNo == split2)) {
										attch.setBase64(Base64
												.encodeBase64String(item
														.getDocData()));
										attch.setSerialNo(serialNo);
										attch.setType("FP_WSQ");
										person.getAttachment().add(attch);
									}
								}
							}
						}
					}

					pp.setPerson(person);
					lstppA72.add(pp);
				}
			}

		} catch (Exception e) {

		}

		if (lstppA72 != null && lstppA72.size() > 0) {
			resA72.getPassport().addAll(lstppA72);

			/* Lưu bảng thống kê truyền nhận */
			this.saveOrUpRptData(Contants.URL_SYNC_PASSPORT_BY_DAY,
					lstppA72.size(), null);
		}

		rep.setResponse(resA72);
		rep.setProperty(prModel);
		return rep;
	}

	public static Boolean CheckPersoRegister(String trans, String ppno,
			NicUploadJob nicUploadJob) {
		try {
			DataRegistrationLdsDTO response = new DataRegistrationLdsDTO();
			NicTransaction nicTran = nicUploadJob.getNicTransaction();

			Date doE = DateUtils.addYears(new Date(),
					nicTran.getValidityPeriod());
			String dateOfExpiry = DateUtil.parseDate(doE,
					DateUtil.FORMAT_YYYYMMDD);
			String dateOfIssue = DateUtil.parseDate(new Date(),
					DateUtil.FORMAT_YYYYMMDD);
			String nameOfHolder = getNameOfHolder(nicTran
					.getNicRegistrationData().getSurnameFull(), nicTran
					.getNicRegistrationData().getFirstnameFull(), nicTran
					.getNicRegistrationData().getMiddlenameFull());
			String dateOfBirth = DateUtil.parseDate(nicTran
					.getNicRegistrationData().getDateOfBirth(),
					DateUtil.FORMAT_YYYYMMDD);
			String passportType = nicTran.getPassportType();
			String nationality = nicTran.getNicRegistrationData()
					.getNationality();
			String issueState = "VNM";
			String passportNo = ppno;
			String searchName = removeAccent(nameOfHolder).toUpperCase();
			String gender = nicTran.getNicRegistrationData().getGender();
			String nin = nicTran.getNin();
			String placeOfBirth = nicTran.getNicRegistrationData()
					.getPlaceOfBirth();
			String priority = "NORMAL";
			if (nicTran.getPriority() == 1)
				priority = "HIGH";
			else if (nicTran.getPriority() == 2)
				priority = "HIGHEST";

			Boolean hasDG3 = false;
			String finger01Position = "";
			String finger02Position = "";
			String finger01 = "";
			String finger02 = "";
			// get encode fingers from transaction data
			if (StringUtils.isNotBlank(nicTran.getNicRegistrationData()
					.getFpEncode())) {
				String[] fpEncodes = StringUtils.split(nicTran
						.getNicRegistrationData().getFpEncode(), ",");
				if (fpEncodes.length >= 1)
					finger01Position = fpEncodes[0];
				if (fpEncodes.length >= 2)
					finger02Position = fpEncodes[1];
				byte[] finger01_ = null;
				byte[] finger02_ = null;
				finger01_ = getTransactionDocument(nicTran,
						NicTransactionAttachment.DOC_TYPE_MINUTIA_CBEFF,
						finger01Position);
				finger02_ = getTransactionDocument(nicTran,
						NicTransactionAttachment.DOC_TYPE_MINUTIA_CBEFF,
						finger02Position);

				if (finger01_ != null && finger02_ != null) {
					hasDG3 = true;
					finger01 = Base64.encodeBase64String(finger01_);
					finger02 = Base64.encodeBase64String(finger02_);
				}
			}
			byte[] photo_ = getTransactionDocument(nicTran,
					NicTransactionAttachment.DOC_TYPE_PHOTO_CHIP,
					NicTransactionAttachment.DEFAULT_SERIAL_NO);
			String photo = Base64.encodeBase64String(photo_);

			if (StringUtils.isNotEmpty(dateOfBirth)
					|| StringUtils.isNotEmpty(dateOfExpiry)
					|| StringUtils.isNotEmpty(dateOfIssue)
					|| StringUtils.isNotEmpty(photo)
					|| StringUtils.isNotEmpty(issueState)
					|| StringUtils.isNotEmpty(nameOfHolder)
					|| StringUtils.isNotEmpty(nationality)
					|| StringUtils.isNotEmpty(nin)
					|| StringUtils.isNotEmpty(passportNo)
					|| StringUtils.isNotEmpty(gender)
					|| StringUtils.isNotEmpty(passportType)
					|| StringUtils.isNotEmpty(placeOfBirth)
					|| StringUtils.isNotEmpty(nicTran.getIssuingAuthority())
					|| StringUtils.isNotEmpty(ppno)
					|| StringUtils.isNotEmpty(priority)
					|| StringUtils.isNotEmpty(searchName)) {

			} else
				return false;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

			return false;
		}

		return true;
	}

	@POST
	@Produces("application/json")
	@Consumes("application/json")
	@Path("/updateStatusPerso")
	public ResponseBase<Boolean> updateStatusPerso(UpdateLDSPackageDto request) {
		ResponseBase<Boolean> rep = new ResponseBase<Boolean>();
		PropertyModel prModel = new PropertyModel();
		Date startTime = new Date();
		rep.setResponse(false);
		prModel.setCode(-1);
		String logData = "";
		String transactionStatus = "";
		String statusPerso = "";

		try {
			if (StringUtils.isNotEmpty(request.getTrans())
					&& StringUtils.isNotEmpty(request.getPassportNo())) {
				List<NicUploadJob> nicUploadJobs = uploadJobSerivce
						.findAllByTransactionId(request.getTrans());
				if (nicUploadJobs != null) {
					NicUploadJob nicUploadJob = nicUploadJobs.get(0);

					if ((StringUtils.isBlank(nicUploadJob
							.getPersoRegisterStatus())
							|| nicUploadJob.getPersoRegisterStatus().equals(
									"09") || nicUploadJob
							.getPersoRegisterStatus().equals("01"))
							&& StringUtils.isNotBlank(nicUploadJob
									.getTempPassportNo())
							&& nicUploadJob.getTempPassportNo().equals(
									request.getPassportNo())) {

						NicDocumentDataId id = new NicDocumentDataId();
						id.setPassportNo(request.getPassportNo());
						id.setTransactionId(request.getTrans());
						NicDocumentData nicdel = documentDataService
								.findById(id);
						String passportStatus = "";
						String tranStatus = "";
						if (request.getStatus() == 1) {
							tranStatus = Contants.TRANSACTION_STATUS_PERSO_PRINTED;
							transactionStatus = PERSO_PRINTED + STAGE_COMPLETED;
							statusPerso = STATUS_COMPLETED;
							passportStatus = NicTransactionService.TRANSACTION_STATUS_PERSONALIZED;
						} else {
							tranStatus = Contants.TRANSACTION_STATUS_APPROVE_D;
							transactionStatus = PERSO_PRINTED + STAGE_ERROR;
							statusPerso = STATUS_ERROR;

							// Cập nhật lại trạng thái in hỏng hộ chiếu.
							passportStatus = NicDocumentData.DOCUMENT_DATA_STATUS_CANCELLED;
							if (nicdel != null) {
								nicdel.setCancelDatetime(new Date());
								nicdel.setActiveFlag(false);
								nicdel.setCancelType(NicDocumentData.DOCUMENT_DATA_CANCEL_TYPE_IN_HONG);
							}

							// Xóa dữ liệu hộ chiếu tạm
							nicUploadJob.setTempPassportNo("");
							nicUploadJob.setReceiptNo("");
							uploadJobSerivce.saveOrUpdate(nicUploadJob);

							String transactionStatusPP = "RE_"
									+ JOB_STATE_PASSPORT_REGISTER + "_PENDING";
							this.saveTransactionLog(request.getTrans(),
									JOB_STATE_PASSPORT_REGISTER,
									transactionStatusPP, startTime, new Date(),
									null, "",
									nicUploadJob.getJobReprocessCount());
						}
						// cập nhật hộ chiếu sau khi in.
						if (nicdel != null) {
							nicdel.setCreateBy(request.getPrintName());
							nicdel.setChipSerialNo(request.getChipSerial());
							nicdel.setSerialNo(request.getPhoiSerial());
							nicdel.setStatus(passportStatus);
							nicdel.setPrintName(request.getPrintName());
							nicdel.setPrinterSerial(request.getPrintSerial());
							if (StringUtils.isNotBlank(request.getPrintDate())) {
								Date date = DateUtil.strToDate(
										request.getPrintDate(),
										DateUtil.FORMAT_YYYYMMDD);
								nicdel.setPrintDate(date);
							}
							documentDataService.saveOrUpdateDocData(nicdel);
						}
						uploadJobService.updateJobState(
								nicUploadJob.getWorkflowJobId(), statusPerso,
								PERSO_REGISTER);
						uploadJobService.updateJobStatus(
								nicUploadJob.getWorkflowJobId(),
								transactionStatus);
						this.saveTransactionLog(request.getTrans(),
								PERSO_PRINTED, transactionStatus, startTime,
								new Date(), null, logData,
								nicUploadJob.getJobReprocessCount());

						rep.setResponse(true);
						prModel.setCode(99);
						prModel.setMessage("Trang thai Perso cap nhat: "
								+ transactionStatus);

						/* cập nhật trạng thái hồ sơ */
						NicTransaction nicTran = nicTransactionService
								.findTransactionById(request.getTrans())
								.getModel();
						nicTran.setTransactionStatus(tranStatus);
						nicTransactionService.saveOrUpdateTransaction(nicTran);

						/* Lưu bảng thống kê truyền nhận */
						this.saveOrUpRptData(Contants.URL_UPDATE_STATUS_PERSO,
								1, null);
					} else {
						prModel.setCode(Contants.ENUM_STATUS_CODE_HTTP_ERROR_INPUT);
						prModel.setMessage("Trang thai ho chieu da duoc cap nhat. passportNo="
								+ request.getPassportNo());
					}
				} else {
					prModel.setCode(Contants.ENUM_STATUS_CODE_HTTP_NOT_FOUND);
					prModel.setMessage("Khong tim thay du lieu ban ghi");
				}
			} else {
				prModel.setCode(Contants.ENUM_STATUS_CODE_HTTP_ERROR_INPUT);
				prModel.setMessage("Thieu du lieu dau vao. TransationId="
						+ request.getTrans() + "- ppNo="
						+ request.getPassportNo() + "- status="
						+ request.getStatus());
			}

		} catch (Exception e) {
			e.printStackTrace();
			prModel.setCode(414);
			prModel.setMessage(Contants.MESSAGE_EXCEPTION);
			/*
			 * Lưu dữ liệu nếu có Exception
			 */
			String dataResponse = "";
			String dataRequest = "";
			String keyId = "USP_"
					+ new SimpleDateFormat("ddMMyyyy").format(new Date());
			try {
				dataResponse = new ObjectMapper().writeValueAsString(rep);
				dataRequest = new ObjectMapper().writeValueAsString(request);
			} catch (Exception e2) {
				logger.error(e2.getMessage());
			}
			this.saveException(Contants.TYPE_USP,
					Contants.URL_UPDATE_STATUS_PERSO, dataRequest, keyId,
					dataResponse, e);
		}

		rep.setProperty(prModel);
		return rep;
	}

	/*
	 * Hủy hộ chiếu in lỗi hoặc khôi phục phôi
	 */
	@POST
	@Produces("application/json")
	@Consumes("application/json")
	@Path("/updateStatusPassport")
	public ResponseBase<Boolean> updateStatusPassport(
			CancelPassportDetail request) {
		ResponseBase<Boolean> rep = new ResponseBase<Boolean>();
		PropertyModel prModel = new PropertyModel();
		Date startTime = new Date();
		rep.setResponse(false);
		rep.setProperty(prModel);
		prModel.setCode(-1);
		String logData = "";
		String transactionStatus = "";
		String statusPerso = "";
		String transactionStage = "";
		boolean check = true;
		try {
			if (StringUtils.isNotEmpty(request.getTransactionId())
					&& StringUtils.isNotEmpty(request.getPassportNo())
					&& StringUtils.isNotBlank(request.getCancelType())) {
				List<NicUploadJob> nicUploadJobs = uploadJobSerivce
						.findAllByTransactionId(request.getTransactionId());
				if (nicUploadJobs != null) {
					NicUploadJob nicUploadJob = nicUploadJobs.get(0);
					NicTransaction tran = nicUploadJob.getNicTransaction();
					if (tran.getTransactionStatus().equals(
							Contants.TRANSACTION_STATUS_RIC_ISSUED)) {
						rep.getProperty().setCode(202);
						rep.getProperty().setMessage(
								"Hộ chiếu đã trả cho công dân.");
						return rep;
					}
					NicDocumentDataId id = new NicDocumentDataId();
					id.setPassportNo(request.getPassportNo());
					id.setTransactionId(request.getTransactionId());
					NicDocumentData nicdel = documentDataService.findById(id);
					if (nicdel == null) {
						rep.getProperty().setMessage(
								"Không tìm thấy bản ghi hồ sơ hộ chiếu.");
						return rep;
					}
					if (request.getCancelType().equals(
							NicDocumentData.DOCUMENT_DATA_CANCEL_TYPE_IN_HONG)) {
						List<EppListHandoverDetail> listHanDetail = eppListHandoverDetailService
								.findAllByTranIdOrType(nicdel.getId()
										.getTransactionId(),
										NicListHandover.TYPE_LIST_C);
						if (listHanDetail != null && listHanDetail.size() > 0) {
							for (EppListHandoverDetail eppHanDetails : listHanDetail) {
								eppListHandoverDetailService
										.deleteHandoverDetail(eppHanDetails
												.getPackageId(), eppHanDetails
												.getTransactionId(),
												eppHanDetails.getTypeList());
							}
						}
						transactionStatus = PERSO_PRINTED + STAGE_ERROR;
						statusPerso = STATUS_ERROR;

						// Cập nhật lại trạng thái in hỏng hộ chiếu.
						if (nicdel != null) {
							nicdel.setCancelDatetime(new Date());
							nicdel.setActiveFlag(false);
							nicdel.setCancelType(NicDocumentData.DOCUMENT_DATA_CANCEL_TYPE_IN_HONG);
							nicdel.setCancelReason(request.getCancelReason());
						}

						// cập nhật trạng thái hộ chiếu
						if (nicdel != null) {
							nicdel.setCancelBy(request.getCancelByName());
							nicdel.setStatus(NicDocumentData.DOCUMENT_DATA_STATUS_CANCELLED);
							documentDataService.saveOrUpdateDocData(nicdel);
						}
						uploadJobService.updateJobState(
								nicUploadJob.getWorkflowJobId(), statusPerso,
								PERSO_REGISTER);
						uploadJobService.updateJobStatus(
								nicUploadJob.getWorkflowJobId(),
								transactionStatus);

					} else if (request
							.getCancelType()
							.equals(NicDocumentData.DOCUMENT_DATA_CANCEL_TYPE_RESTORE_DRAFT)) {
						transactionStage = NicDocumentData.DOCUMENT_DATA_CANCEL_TYPE_RESTORE_DRAFT;
						documentDataService.deletePassport(nicdel);
					} else {
						check = false;
					}
					if (check) {
						// Xóa dữ liệu hộ chiếu tạm
						nicUploadJob.setTempPassportNo("");
						nicUploadJob.setReceiptNo("");
						uploadJobSerivce.saveOrUpdate(nicUploadJob);

						/* cập nhật trạng thái hồ sơ */
						tran.setTransactionStatus(Contants.TRANSACTION_STATUS_APPROVE_D);
						nicTransactionService.saveOrUpdateTransaction(tran);

						this.saveTransactionLog(tran.getTransactionId(),
								transactionStage, transactionStatus, startTime,
								new Date(), null, logData,
								nicUploadJob.getJobReprocessCount());

						rep.setResponse(true);
						prModel.setCode(200);
						prModel.setMessage(Contants.MESSAGE_SUCCESS);

						/* Lưu bảng thống kê truyền nhận */
						this.saveOrUpRptData(
								Contants.URL_CANCEL_PASSPORT_BY_STEP, 1, null);
					}
				} else {
					prModel.setCode(Contants.ENUM_STATUS_CODE_HTTP_NOT_FOUND);
					prModel.setMessage("Không tìm thấy dữ liệu bản ghi hồ sơ hộ chiếu.");
				}
			} else {
				prModel.setCode(Contants.ENUM_STATUS_CODE_HTTP_ERROR_INPUT);
				prModel.setMessage("Thieu du lieu dau vao. TransationId = "
						+ request.getTransactionId() + " - ppNo = "
						+ request.getPassportNo() + " - CancelType = "
						+ request.getCancelType());
			}

		} catch (Exception e) {
			e.printStackTrace();
			prModel.setCode(414);
			prModel.setMessage(Contants.MESSAGE_EXCEPTION);
			/*
			 * Lưu dữ liệu nếu có Exception
			 */
			String dataResponse = "";
			String dataRequest = "";
			String keyId = request.getTransactionId();
			try {
				dataResponse = new ObjectMapper().writeValueAsString(rep);
				dataRequest = new ObjectMapper().writeValueAsString(request);
			} catch (Exception e2) {
				logger.error(e2.getMessage());
			}
			this.saveException(Contants.TYPE_CPBS,
					Contants.URL_CANCEL_PASSPORT_BY_STEP, dataRequest, keyId,
					dataResponse, e);
		}

		rep.setProperty(prModel);
		return rep;
	}

	private void saveTransactionLog(String transactionId,
			String transactionStage, String transactionStatus, Date startTime,
			Date endTime, String logInfo, String logData, Integer retryCount) {
		NicCommandUtil nicCommandUtil = new NicCommandUtil();
		NicTransactionLog transactionLog = new NicTransactionLog();
		transactionLog.setRefId(transactionId);
		transactionLog.setLogCreateTime(new Date());
		transactionLog.setTransactionStage(transactionStage);// TRANSACTION_STATE_PERSONALIZATION);
		transactionLog.setTransactionStatus(transactionStatus);
		transactionLog.setSiteCode(nicCommandUtil
				.getSystemSiteCodeFromParameter()); // get from
													// 'CURRENT_SITE_CODE'
		transactionLog.setStartTime(startTime);
		transactionLog.setEndTime(endTime);
		transactionLog.setLogInfo(logInfo);
		transactionLog.setLogData(logData);
		// 8-OCT-2013 (jp) - added hostname and system
		transactionLog.setWkstnId(nicCommandUtil.getHostName());
		transactionLog.setOfficerId(nicCommandUtil.getSystemName());
		transactionLog.setRetryCount(retryCount);
		synchronized (NicTransactionLog.class) {
			nicTransactionLogService.saveOrUpdate(transactionLog);
		}
	}

	@POST
	@Produces("application/json")
	@Consumes("application/json")
	@Path("/detailTransaction/{trans}")
	public ResponseBase<PassportInfo> detailTransaction(
			@PathParam("trans") String trans) {
		ResponseBase<PassportInfo> rep = new ResponseBase<PassportInfo>();
		PropertyModel prModel = new PropertyModel();
		prModel.setCode(-1);

		if (trans != "") {
			try {
				NicTransaction nicTrans = nicTransactionService.findById(trans);
				NicRegistrationData nicRegistrationData = nicRegistrationDataService
						.findById(trans);

				Collection<NicDocumentData> nicDocumentDatas = documentDataService
						.findByTransactionId(trans).getModel();
				if (nicTrans != null) {
					PassportInfo obj = new PassportInfo();
					obj.setFullName(nicRegistrationData.getSurnameLine1());
					obj.setDob(new java.sql.Date(nicRegistrationData
							.getDateOfBirth().getTime()).toString());
					obj.setAddress(nicRegistrationData.getAddressLine1());
					obj.setGender(nicRegistrationData.getGender());
					obj.setPassportType(nicTrans.getPassportType());
					obj.setPid(nicTrans.getNin());
					obj.setPlaceOfBirth(nicRegistrationData.getPlaceOfBirth());
					obj.setPlaceOfIssue(nicTrans.getIssSiteCode());
					// obj.setPlacePersoId(nicTrans.getPrintPerso());
					obj.setTransactionId(nicTrans.getTransactionId());
					List<NicTransactionAttachment> nicTransAttach = nicTransactionAttachmentService
							.findNicTransactionAttachments(trans, "PH_CAP",
									"01").getListModel();
					obj.setPicture(nicTransAttach.get(0).getDocData());
					String nationality = this.codesService
							.getCodeValueDescByIdName(Codes.NATIONALITY,
									nicRegistrationData.getNationality(),
									"VIETNAMESE");
					obj.setCountryCode(nicRegistrationData.getNationality());
					obj.setNationality(nationality);
					if (!nicDocumentDatas.isEmpty()) {
						for (NicDocumentData it : nicDocumentDatas) {
							if (it.getDateOfIssue() != null) {
								obj.setDateOfIssue(DateUtil.parseDate(
										it.getDateOfIssue(),
										DateUtil.FORMAT_YYYYMMDD));
							}
							if (it.getDateOfExpiry() != null) {
								obj.setDateOfExpiry(DateUtil.parseDate(
										it.getDateOfExpiry(),
										DateUtil.FORMAT_YYYYMMDD));
							}
							break;
						}
					}
					rep.setResponse(obj);

					prModel.setCode(99);
					prModel.setMessage("");

					/* Lưu bảng thống kê truyền nhận */
					this.saveOrUpRptData(Contants.URL_DETAIL_TRANSACTION, 1,
							null);

				} else {
					prModel.setCode(-404);
					prModel.setMessage("Khong tim thay du lieu ban ghi");
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				prModel.setMessage(e.getMessage());
				e.printStackTrace();
			}
		} else {
			prModel.setCode(-101);
			prModel.setMessage("Thieu du lieu dau vao. TransationId=" + trans);
		}
		rep.setProperty(prModel);
		return rep;
	}

	@POST
	@Produces("application/json")
	@Consumes("application/json")
	@Path("/getXmlPerso/{trans}/{site}")
	public ResponseBase<byte[]> getXmlPerso(@PathParam("trans") String trans,
			@PathParam("site") String site) {
		ResponseBase<byte[]> rep = new ResponseBase<byte[]>();
		PropertyModel prModel = new PropertyModel();
		prModel.setCode(-404);
		prModel.setMessage("Chua co goi du lieu XML");
		rep.setProperty(prModel);
		if (StringUtils.isNotEmpty(trans) && StringUtils.isNotEmpty(site)) {
			try {
				// List<NicUploadJob> nicUploadJobs =
				// uploadJobSerivce.findAllByTransactionId(trans);
				// SyncQueueJob queue =
				// queueJobService.findQueueByObjectId(trans, site,
				// Contants.CODE_STATUS_QUEUE_PENDING, OBJECT_TYPE_PERSO);
				/*
				 * NicTransaction nicTrans =
				 * nicTransactionService.findById(trans, false, true, true,
				 * false);
				 */
				// if (queue != null) {
				NicUploadJob nicUploadJob = uploadJobSerivce
						.findUploadJobByTransId(trans).getModel();
				// int count = 0;
				/*
				 * while(StringUtils.isEmpty(tokenA08)){ getTokenA08(); count++;
				 * if(count > 3){break;} }
				 */
				// String receiptNo = nicUploadJob.getReceiptNo();
				/*
				 * if(StringUtils.isEmpty(receiptNo)){ int countR = 0;
				 * DataRegistrationLDS response = prepareDataLds(trans,
				 * nicUploadJob.getTempPassportNo(), nicUploadJob); // Kiểm tra
				 * lấy token từ A08 if (!StringUtils.isEmpty(tokenA08)) {
				 * ObjectMapper mapper = new ObjectMapper(); String jsonModel =
				 * mapper.writeValueAsString(response); String requestUrl =
				 * "http://192.168.1.15:8044/app/rest/v2/PersoData/createPassport"
				 * ; while(StringUtils.isEmpty(receiptNo) && countR < 3){ String
				 * getResult = sendPostRequestSync(requestUrl,jsonModel,
				 * tokenA08, "Bearer"); if (!StringUtils.isEmpty(getResult)) {
				 * ///Luu ma goi tin nicUploadJob.setReceiptNo(getResult);
				 * uploadJobSerivce.saveOrUpdate(nicUploadJob); receiptNo =
				 * getResult; } else countR++; } } else {
				 * prModel.setStatus(-404);
				 * prModel.setMessage("Khong tao duoc token tu A08");
				 * rep.setProperty(prModel); return rep; } }
				 */

				/*
				 * if (StringUtils.isEmpty(receiptNo)) {
				 * prModel.setStatus(-404);
				 * prModel.setMessage("Khong tim thay du lieu ban ghi");
				 * rep.setProperty(prModel); return rep; } else {
				 */
				/*
				 * if(StringUtils.isBlank(nicTrans.getNicSiteCode())){ byte[]
				 * xmlByte = GetDataXml_A08(receiptNo, tokenA08, "Bearer"); if
				 * (xmlByte != null) { rep.setResponse(xmlByte); if
				 * (nicUploadJob.getPersoRegisterStatus() == null ||
				 * (!nicUploadJob.getPersoRegisterStatus().equals( "02") &&
				 * !nicUploadJob .getJobCurrentStatus().equals(
				 * JOB_STATE_PERSO_REGISTER + STAGE_COMPLETED))) {
				 * 
				 * List<NicDocumentData> nicdd_ = documentDataDao
				 * .findAllByTransactionId(nicUploadJob .getTransactionId());
				 * if(!StringUtils.isBlank(nicUploadJob .getTempPassportNo()) &&
				 * nicdd_.isEmpty()){ this.LDSPacketDocument(nicUploadJob); } }
				 * 
				 * prModel.setStatus(99); prModel.setMessage(""); } else {
				 * prModel.setStatus(-404);
				 * prModel.setMessage("Chua co goi du lieu xml tu A08");
				 * rep.setProperty(prModel); return rep; } } else { if
				 * (nicUploadJob.getPersoRegisterStatus() == null ||
				 * (!nicUploadJob.getPersoRegisterStatus().equals( "02") &&
				 * !nicUploadJob .getJobCurrentStatus().equals(
				 * JOB_STATE_PERSO_REGISTER + STAGE_COMPLETED))) {
				 * 
				 * List<NicDocumentData> nicdd_ = documentDataDao
				 * .findAllByTransactionId(nicUploadJob .getTransactionId());
				 * if(!StringUtils.isBlank(nicUploadJob .getTempPassportNo()) &&
				 * nicdd_.isEmpty()){ this.LDSPacketDocument(nicUploadJob); } }
				 * 
				 * prModel.setStatus(99); prModel.setMessage(""); }
				 */
				byte[] xmlByte = null;
				if (nicUploadJob.getPersoRegisterStatus() == null
						|| (!nicUploadJob.getPersoRegisterStatus().equals("02") && !nicUploadJob
								.getJobCurrentStatus().equals(
										JOB_STATE_PERSO_REGISTER
												+ STAGE_COMPLETED))) {
					BaseModelList<NicDocumentData> baseFindAllDocData = documentDataDao
							.findAllByTransactionId(nicUploadJob
									.getTransactionId());
					List<NicDocumentData> nicdd_ = baseFindAllDocData
							.getListModel();
					if (!StringUtils.isBlank(nicUploadJob.getTempPassportNo())
							&& nicdd_.isEmpty()) {
						this.LDSPacketDocument(nicUploadJob);
					}
				}
				BaseModelList<NicDocumentData> baseFindAllDocData = documentDataDao
						.findAllByTransactionId(nicUploadJob.getTransactionId());
				List<NicDocumentData> nicddN_ = baseFindAllDocData
						.getListModel();
				if (nicddN_ != null) {
					List<NicTransactionAttachment> attchlist = nicTransactionAttachmentService
							.findNicTransactionAttachments(
									nicUploadJob.getTransactionId(), "PERSO",
									"01").getListModel();
					if (attchlist != null && attchlist.size() > 0) {
						NicTransactionAttachment attch = new NicTransactionAttachment();
						attch = attchlist.get(0);
						if (attch.getDocData() != null) {
							xmlByte = Base64.decodeBase64(attch.getDocData());
							rep.setResponse(xmlByte);
							prModel.setCode(99);
							prModel.setMessage("success");

							/* Lưu bảng thống kê truyền nhận */
							this.saveOrUpRptData(Contants.URL_GET_XML_PERSO, 1,
									site);
						}
					}
				}
				/*
				 * 
				 * }
				 */

				/*
				 * if (nicUploadJob.getPersoRegisterStatus() == null ||
				 * (!nicUploadJob.getPersoRegisterStatus().equals( "02") &&
				 * !nicUploadJob .getJobCurrentStatus().equals(
				 * JOB_STATE_PERSO_REGISTER + STAGE_COMPLETED))) { // Xu ly
				 * perso // logger.info("-------------- PersoThread_TRUNG: " +
				 * // Thread.currentThread().getName());
				 * 
				 * appContext =
				 * ApplicationContextProvider.getApplicationContext();
				 * baseExecutor = (NicCommandExecutor)
				 * appContext.getBean("baseExecutorPerso");
				 * baseExecutor.doCommand(nicUploadJob);
				 * 
				 * // logger.info("-------------- PersoThread_TRUNG: " + //
				 * Thread.currentThread().getName() + " End.");
				 * 
				 * NicSubmitPersoCommand command = new NicSubmitPersoCommand();
				 * command.doSomething(nicUploadJob);
				 * 
				 * 
				 * List<NicDocumentData> nicdd_ = documentDataDao
				 * .findAllByTransactionId(nicUploadJob .getTransactionId());
				 * if(!StringUtils.isBlank(nicUploadJob .getTempPassportNo()) &&
				 * nicdd_.isEmpty()){ int re =
				 * this.LDSPacketDocument(nicUploadJob); } if
				 * (!StringUtils.isBlank(nicUploadJob .getTempPassportNo()) &&
				 * nicdd_.isEmpty()) { int re =
				 * this.LDSPacketDocument(nicUploadJob); if (re == -1) {
				 * prModel.setStatus(-105);
				 * prModel.setMessage("Loi khi xu ly dong goi LDS");
				 * rep.setProperty(prModel); return rep; } } else {
				 * prModel.setStatus(-106);
				 * prModel.setMessage("Chua co so ho chieu");
				 * rep.setProperty(prModel); return rep; } }
				 */

				/** TẠM ĐÓNG ĐỂ CHỈ LẤY GÓI TỪ A08 **/
				/*
				 * List<NicTransactionAttachment> nicTransAttach =
				 * nicTransactionAttachmentService
				 * .findNicTransactionAttachments(trans, "PERSO", "01"); if
				 * (nicTransAttach != null) { byte[] data =
				 * Base64.decodeBase64(nicTransAttach.get(0) .getDocData());
				 * rep.setResponse(data); prModel.setStatus(99);
				 * prModel.setMessage(""); } else { prModel.setStatus(-404);
				 * prModel.setMessage("Khong tim thay du lieu ban ghi"); }
				 */
				// queueJobService.updateStatusQueueJob(queue.getId(),
				// Contants.CODE_STATUS_QUEUE_DOING);
				/*
				 * }else{ prModel.setStatus(-101); prModel.setMessage(
				 * "Không thấy dữ liệu trong hàng đợi. TransationId=" + trans);
				 * }
				 */
			} catch (Exception e) {
				e.printStackTrace();
				prModel.setCode(414);
				prModel.setMessage(Contants.MESSAGE_EXCEPTION);
				/*
				 * Lưu dữ liệu nếu có Exception
				 */
				String dataResponse = "";
				try {
					dataResponse = new ObjectMapper().writeValueAsString(rep);
				} catch (Exception e2) {
					logger.error(e2.getMessage());
				}
				this.saveException(Contants.TYPE_GXP,
						Contants.URL_GET_XML_PERSO, trans, site, dataResponse,
						e);
			}
		} else {
			prModel.setCode(-101);
			prModel.setMessage("Thiếu dữ liệu đầu vào. TransationId=" + trans);
		}
		rep.setProperty(prModel);
		return rep;
	}

	@POST
	@Produces("application/json")
	@Consumes("application/json")
	@Path("/packLDS")
	public ResponseBase<LdsResponseWsDto> packLDS(UpdateLDSPackageDto trans) {
		ResponseBase<LdsResponseWsDto> rep = new ResponseBase<LdsResponseWsDto>();
		LdsResponseWsDto response = new LdsResponseWsDto();
		PropertyModel prModel = new PropertyModel();
		prModel.setCode(-1);
		prModel.setMessage("Chua co goi du lieu XML");
		rep.setProperty(prModel);
		NicUploadJob nicUploadJob = null;
		InfoPassportC infoPP = null;
		if (StringUtils.isNotEmpty(trans.getTrans())) {
			try {

				nicUploadJob = uploadJobSerivce.findUploadJobByTransId(
						trans.getTrans()).getModel();

				/*
				 * kiểm tra số hộ chiếu đang hoạt động ứng với số CMND.
				 */
				NicTransaction transObj = nicTransactionService.findById(
						trans.getTrans(), false, true, true, false);// has
				// trans
				// doc
				// and
				// registration
				// data
				if (transObj != null) {
					List<NicTransaction> listTran = nicTransactionService
							.findTranByNinAndTypePassport(transObj.getNin(),
									transObj.getPassportType());
					if (listTran != null && listTran.size() > 0) {
						for (NicTransaction nicTransaction : listTran) {
							Collection<NicDocumentData> nicDoc = documentDataService
									.findByTransactionId(
											nicTransaction.getTransactionId())
									.getModel();
							List<NicDocumentData> listDoc = (List<NicDocumentData>) nicDoc;
							if (listDoc != null && listDoc.size() > 0) {
								for (NicDocumentData nicDocumentData : listDoc) {
									if (nicDocumentData.getDateOfExpiry()
											.getTime() > new Date().getTime()
											&& (nicDocumentData
													.getStatus()
													.equals(Contants.DOC_STATUS_ISSUANCE)
													|| nicDocumentData
															.getStatus()
															.equals(Contants.DOC_STATUS_PERSONALIZED) || nicDocumentData
													.getStatus()
													.equals(NicDocumentData.DOCUMENT_DATA_STATUS_PACKED))) {
										infoPP = new InfoPassportC();
										infoPP.setChipSerialNo(nicDocumentData
												.getChipSerialNo());
										if (nicDocumentData.getDateOfExpiry() != null) {
											String date = DateUtil.parseDate(
													nicDocumentData
															.getDateOfExpiry(),
													DateUtil.FORMAT_YYYYMMDD);
											infoPP.setDateOfExpiry(date);
										}
										if (nicDocumentData.getDateOfIssue() != null) {
											String date = DateUtil.parseDate(
													nicDocumentData
															.getDateOfIssue(),
													DateUtil.FORMAT_YYYYMMDD);
											infoPP.setDateOfIssue(date);
										}
										infoPP.setIcaoLine1(nicDocumentData
												.getIcaoLine1());
										infoPP.setIcaoLine2(nicDocumentData
												.getIcaoLine2());
										infoPP.setPassportNo(nicDocumentData
												.getId().getPassportNo());
										infoPP.setSignerName(nicDocumentData
												.getSigner());
										infoPP.setSignerPosition(nicDocumentData
												.getPositionSigner());
										infoPP.setStatus(nicDocumentData
												.getStatus());
										NicTransaction nicTran = nicDocumentData
												.getNicTransaction();
										if (nicTran != null) {
											NicRegistrationData nicReg = nicTran
													.getNicRegistrationData();
											if (nicReg != null) {
												infoPP.setFpEncode(nicReg
														.getFpEncode());
											}
											infoPP.setPlaceOfIssueId(nicDocumentData.getPlaceOfIssueCode());
											infoPP.setPlaceOfIssueName(this.getSiteName(nicDocumentData.getPlaceOfIssueCode()));
											infoPP.setPassportType(nicTran
													.getPassportType());
											infoPP.setIsEpassport(nicTran
													.getIsEpassport() != null ? (nicTran
													.getIsEpassport().equals(
															"Y") ? true : false)
													: false);

										}
										if (nicUploadJob != null) {
											infoPP.setPersonId(nicUploadJob != null ? nicUploadJob
													.getNicTransaction()
													.getPersonCode()
													+ "" : null);
										}
										prModel.setCode(Integer
												.parseInt(Contants.CODE_DATA_EXIST));
										prModel.setMessage("Đã tồn tại số hộ chiếu đang hoạt động đối với CMND: "
												+ transObj.getNin());
										response.setInfoPassport(infoPP);
										rep.setResponse(response);
										rep.setProperty(prModel);
										// /* Lưu bảng thống kê truyền nhận */
										// this.saveOrUpRptData(
										// Contants.URL_GET_LDS, 1, null);
										// return rep;
									}
								}
							}
						}
					}
				}

				if (nicUploadJob != null
						&& StringUtils.isNotBlank(nicUploadJob
								.getTempPassportNo())) {
					/*
					 * kiểm tra tồn tại của số hộ chiếu đã gán.
					 */
					List<NicDocumentData> checkExist = documentDataService
							.findValidPassportById(nicUploadJob
									.getTempPassportNo());
					if (checkExist != null && checkExist.size() > 0) {
						prModel.setCode(203);
						prModel.setMessage("Số hộ chiếu: "
								+ nicUploadJob.getTempPassportNo()
								+ Contants.MESSAGE_EXIST);
					} else {
						response = this.LDSPacketDocument_V5(nicUploadJob);
						if (response != null && response.getStatus() == 200) {
							if (this.checkDataResponseLDS(response,
									nicUploadJob.getNicTransaction()
											.getIsEpassport())) {
								prModel.setCode(Contants.ENUM_STATUS_CODE_HTTP_SUCESS);
								prModel.setMessage(Contants.MESSAGE_SUCCESS);
								rep.setResponse(response);
								/* Lưu bảng thống kê truyền nhận */
								this.saveOrUpRptData(Contants.URL_GET_LDS, 1,
										nicUploadJob.getNicTransaction()
												.getIssuingAuthority());
							}
						} else {
							prModel.setCode(response.getStatus());
							prModel.setMessage(response.getMessage());
						}
					}
				} else {
					prModel.setCode(Contants.ENUM_STATUS_CODE_HTTP_ERROR);
					prModel.setMessage("Chua co du lieu ho so ho chieu");
				}

			} catch (Exception e) {
				e.printStackTrace();
				prModel.setCode(414);
				prModel.setMessage(Contants.MESSAGE_EXCEPTION);
				/*
				 * Lưu dữ liệu nếu có Exception
				 */
				String dataResponse = "";
				String dataRequest = "";
				String keyId = "";
				if (StringUtils.isNotBlank(trans.getTrans())) {
					keyId = trans.getTrans();
				} else {
					keyId = "PLDS_"
							+ new SimpleDateFormat("ddMMyyyy")
									.format(new Date());
				}
				try {
					dataResponse = new ObjectMapper().writeValueAsString(rep);
					dataRequest = new ObjectMapper().writeValueAsString(trans);
				} catch (Exception e2) {
					logger.error(e2.getMessage());
				}
				this.saveException(Contants.TYPE_PLDS, Contants.URL_GET_LDS,
						dataRequest, keyId, dataResponse, e);
			}
		} else {
			prModel.setCode(Contants.ENUM_STATUS_CODE_HTTP_ERROR_INPUT);
			prModel.setMessage(Contants.MESSAGE_INPUT_NULL);
		}
		rep.setProperty(prModel);
		return rep;
	}

	@POST
	@Produces("application/json")
	@Consumes("application/json")
	@Path("/updatePackageId/{trans}/{ppNo}/{packageId}")
	public ResponseBase<Boolean> updatePackageId(
			@PathParam("trans") String trans, @PathParam("ppNo") String ppNo,
			@PathParam("packageId") String packageId) {
		ResponseBase<Boolean> rep = new ResponseBase<Boolean>();
		PropertyModel prModel = new PropertyModel();
		Date newDate = new Date();
		rep.setResponse(false);
		prModel.setCode(-1);
		if (trans != "" && packageId != "" && ppNo != "") {
			try {
				NicDocumentDataId id = new NicDocumentDataId(trans, ppNo);
				NicDocumentData documentData = documentDataDao.findById(id);

				if (documentData != null) {
					documentData.setPackageId(packageId);
					documentData.setPackageDatetime(newDate);
					documentDataDao.saveOrUpdate(documentData);
					rep.setResponse(true);
					prModel.setCode(99);
					prModel.setMessage("");

					/* Lưu bảng thống kê truyền nhận */
					this.saveOrUpRptData(Contants.URL_UPDATE_PACKAGE_ID, 1,
							null);

				} else {
					prModel.setCode(-404);
					prModel.setMessage("Khong tim thay du lieu ban ghi");
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				prModel.setMessage(e.getMessage());
				e.printStackTrace();
			}
		} else {
			prModel.setCode(-101);
			prModel.setMessage("Thieu du lieu dau vao. TransationId=" + trans
					+ " / packageId=" + packageId + " / passportNo=" + ppNo);
		}
		rep.setProperty(prModel);
		return rep;
	}

	/* Tunt: viet service cap nhat danh sach ban giao theo danh sach cho teca */
	@POST
	@Produces("application/json")
	@Consumes("application/json")
	@Path("/updatePackageIdList")
	public ResponseBase<Boolean> updatePackageIdList(UpdatePackageRequest req) {
		ResponseBase<Boolean> rep = new ResponseBase<Boolean>();
		PropertyModel prModel = new PropertyModel();
		Date newDate = new Date();
		rep.setResponse(false);
		prModel.setCode(-1);
		int count = 0;
		try {
			String issSite = "";
			if (req != null) {
				/* Thêm vào bảng danh sách handover */
				NicListHandover bHandover = listHandoverService
						.findByPackageId(
								new NicListHandoverId(req.getPackageId(),
										NicListHandover.TYPE_LIST_C))
						.getModel();
				if (bHandover == null) {
					NicListHandover handoverC = new NicListHandover();
					handoverC.setId(new NicListHandoverId(req.getPackageId(),
							NicListHandover.TYPE_LIST_C));
					// handoverC.setPackageId(req.getPackageId());
					handoverC.setPackageName(Contants.CODE_PACKAGE_NAME_C);
					handoverC.setCountTransaction(req.getAmount());
					// handoverC.setTypeList(NicListHandover.TYPE_LIST_C);
					handoverC.setHandoverStatus(0);
					;
					// handoverC.setCodeSyncXl(true);
					handoverC.setCreateDate(Calendar.getInstance().getTime());
					handoverC.setCreateBy(Contants.CREATE_BY_SYSTEM);
					listHandoverService.saveHandover(handoverC);
				}
				/*--end--*/
				for (service.perso.model.TransactionPerso t : req
						.getTransactions()) {
					NicTransaction txn = nicTransactionService.findById(t
							.getTransactionId());
					if (txn != null)
						issSite = txn.getIssSiteCode();
					try {
						NicDocumentDataId id = new NicDocumentDataId(
								t.getTransactionId(), t.getPassportNo());
						NicDocumentData documentData = documentDataDao
								.findById(id);

						if (documentData != null) {
							documentData.setPackageId(req.getPackageId());
							documentData.setPackageDatetime(newDate);
							documentDataDao.saveOrUpdate(documentData);
							count++;
							/* Thêm vào bảng chi tiết handover */
							EppListHandoverDetail nicTp = eppListHandoverDetailService
									.getListPackageByPackageIdAndTranID(
											req.getPackageId(),
											t.getTransactionId(),
											NicListHandover.TYPE_LIST_C)
									.getModel();
							if (nicTp == null) {
								EppListHandoverDetail tranPack = new EppListHandoverDetail();
								tranPack.setPackageId(req.getPackageId());
								tranPack.setTransactionId(t.getTransactionId());
								eppListHandoverDetailService
										.saveHandoverDetail(tranPack);
							}
							/*--end--*/
						} else {
							prModel.setMessage("Số hộ chiếu: "
									+ t.getPassportNo() + ", số hồ sơ: "
									+ t.getTransactionId()
									+ " không tìm thấy trên ttdh");
						}
					} catch (Exception e) {
						e.printStackTrace();
						throw new Exception(e);
					}
					/* Cập nhật trạng thái hồ sơ */
					txn.setTransactionStatus(Contants.TRANSACTION_STATUS_CREATED_C);
					nicTransactionService.saveOrUpdateTransaction(txn);
				}
				if (req.getTransactions().size() == count) {
					rep.setResponse(true);
					prModel.setCode(99);
					prModel.setMessage("OK");

				} else {

					prModel.setMessage("Cập nhật thiếu hồ sơ");
				}
				Boolean check = this.addObjToQueueJob(req.getPackageId(),
						Contants.QUEUE_OBJ_TYPE_DC, issSite, null).getModel();
				if (check) {
					/* Lưu bảng thống kê truyền nhận */
					this.saveOrUpRptData(Contants.URL_UPDATE_PACKAGE_ID_LIST,
							1, issSite);

				}
			} else {
				prModel.setCode(-101);
				prModel.setMessage("Thiếu dữ liệu đầu vào");
			}
		} catch (Exception e) {
			e.printStackTrace();
			prModel.setCode(414);
			prModel.setMessage(Contants.MESSAGE_EXCEPTION);
			/*
			 * Lưu dữ liệu nếu có Exception
			 */
			String dataResponse = "";
			String dataRequest = "";
			String keyId = "UPIL_"
					+ new SimpleDateFormat("ddMMyyyy").format(new Date());
			if (StringUtils.isNotBlank(req.getPackageId())) {
				keyId = req.getPackageId();
			}
			try {
				dataResponse = new ObjectMapper().writeValueAsString(rep);
				dataRequest = new ObjectMapper().writeValueAsString(req);
			} catch (Exception e2) {
				logger.error(e2.getMessage());
			}
			this.saveException(Contants.TYPE_UPIL,
					Contants.URL_UPDATE_PACKAGE_ID_LIST, dataRequest, keyId,
					dataResponse, e);
		}
		rep.setProperty(prModel);
		return rep;
	}

	/*
	 * Lưu thông tin in bị trú
	 */
	@POST
	@Produces("application/json")
	@Consumes("application/json")
	@Path("/printedResidence")
	public ResponseBase<Boolean> printedResidence(
			DocumentPrintedResidenceInfo req) {
		ResponseBase<Boolean> rep = new ResponseBase<Boolean>();
		PropertyModel prModel = new PropertyModel();
		rep.setResponse(false);
		prModel.setCode(-1);
		try {
			String issSite = "";
			if (req != null && StringUtils.isNotBlank(req.getPassportNo())
					&& StringUtils.isNotBlank(req.getPrintContent())
					&& StringUtils.isNotBlank(req.getPrintSiteCode())
					&& StringUtils.isNotBlank(req.getTransactionId())) {

				EppDocumentPrintedResidence docPr = new EppDocumentPrintedResidence();
				docPr.setCreateBy(req.getCreateBy());
				docPr.setCreateByName(req.getCreateByName());
				docPr.setCreateDatetime(new Date());
				docPr.setPassportNo(req.getPassportNo());
				docPr.setPrintAgency(req.getPrintAgency());
				docPr.setPrintBy(req.getPrintBy());
				docPr.setPrintByName(req.getPrintByName());
				docPr.setPrintContent(req.getPrintContent());
				docPr.setPrintContentEn(req.getPrintContentEn());
				docPr.setPrintDate(StringUtils.isNotBlank(req.getPrintDate()) ? DateUtil
						.strToDate(req.getPrintDate(), DateUtil.FORMAT_YYYYMMDD)
						: null);
				docPr.setPrintSiteCode(req.getPrintSiteCode());
				docPr.setSigner(req.getSigner());
				docPr.setSignerPosition(req.getSignerPosition());
				docPr.setTransactionId(req.getTransactionId());
				issSite = docPr.getPrintSiteCode();
				eppDocumentPrintedResidenceService
						.saveDocPrintedResidence(docPr);

				/* Lưu bảng thống kê truyền nhận */
				this.saveOrUpRptData(Contants.URL_PRINTED_RESIDENCE, 1, issSite);
				rep.setResponse(true);
				prModel.setCode(200);
				prModel.setMessage(Contants.MESSAGE_SUCCESS);
			} else {
				prModel.setCode(500);
				prModel.setMessage("Số hồ sơ, số hộ chiếu, nội dung in và mã trung tâm in không được phép để trống.");
			}
		} catch (Exception e) {
			e.printStackTrace();
			prModel.setCode(414);
			prModel.setMessage(Contants.MESSAGE_EXCEPTION);
			/*
			 * Lưu dữ liệu nếu có Exception
			 */
			String dataResponse = "";
			String dataRequest = "";
			String keyId = "UPIL_"
					+ new SimpleDateFormat("ddMMyyyy").format(new Date());
			if (StringUtils.isNotBlank(req.getPassportNo())) {
				keyId = req.getPassportNo();
			}
			try {
				dataResponse = new ObjectMapper().writeValueAsString(rep);
				dataRequest = new ObjectMapper().writeValueAsString(req);
			} catch (Exception e2) {
				logger.error(e2.getMessage());
			}
			this.saveException(Contants.TYPE_PR,
					Contants.URL_PRINTED_RESIDENCE, dataRequest, keyId,
					dataResponse, e);
		}
		rep.setProperty(prModel);
		return rep;
	}

	private int LDSPacketDocument(NicUploadJob obj) {
		NicCommandUtil nicCommandUtil = new NicCommandUtil();
		int res = -1;
		int JOB_STATE = 1;
		int PERSO_REGISTER = 6;
		String STATUS_INPROGRESS = "01";
		try {

			NicTransaction transObj = new NicTransaction();
			// updateStatus(obj.getWorkflowJobId(), JOB_STATE_PERSO_REGISTER,
			// JOB_STATE);
			uploadJobService.updateJobState(obj.getWorkflowJobId(),
					JOB_STATE_PERSO_REGISTER, JOB_STATE);

			if (StringUtils.equals(obj.getPersoRegisterStatus(),
					STATUS_COMPLETED)) {
				// SUBMIT PERSO has been completed before, skip step

			} else {
				uploadJobService.updateJobState(obj.getWorkflowJobId(),
						STATUS_INPROGRESS, PERSO_REGISTER);
				transObj.setTransactionId(obj.getTransactionId());
				transObj = nicTransactionService.findById(
						transObj.getTransactionId(), false, true, true, false);

				dataPackService.preparePersoData(transObj);
				uploadJobService.updateJobState(obj.getWorkflowJobId(),
						STATUS_COMPLETED, PERSO_REGISTER);
				uploadJobService.resetReprocessCount(obj.getWorkflowJobId(), 0);
			}

		} catch (Exception e) {
			e.printStackTrace();
			try {
				uploadJobService.updateJobState(obj.getWorkflowJobId(),
						STATUS_ERROR, PERSO_REGISTER);
			} catch (NicUploadJobServiceException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			nicCommandUtil.setErrorFlag(obj.getWorkflowJobId(), true,
					uploadJobService);

		} finally {
		}
		return res;

	}

	private LdsResponseWsDto LDSPacketDocument_V5(NicUploadJob obj) {
		NicCommandUtil nicCommandUtil = new NicCommandUtil();
		int JOB_STATE = 1;
		int PERSO_REGISTER = 6;
		String STATUS_INPROGRESS = "01";
		LdsResponseWsDto res = null;
		try {

			NicTransaction transObj = new NicTransaction();
			// updateStatus(obj.getWorkflowJobId(), JOB_STATE_PERSO_REGISTER,
			// JOB_STATE);
			uploadJobService.updateJobState(obj.getWorkflowJobId(),
					JOB_STATE_PERSO_REGISTER, JOB_STATE);

			// if (StringUtils.equals(obj.getPersoRegisterStatus(),
			// STATUS_COMPLETED)) {
			// // SUBMIT PERSO has been completed before, skip step
			// res = new LdsResponseWsDto();
			// res.setStatus(Contants.ENUM_STATUS_CODE_HTTP_NOT_FOUND);
			// res.setMessage("SUBMIT PERSO has been completed before, skip step");
			// } else {
			uploadJobService.updateJobState(obj.getWorkflowJobId(),
					STATUS_INPROGRESS, PERSO_REGISTER);
			transObj.setTransactionId(obj.getTransactionId());
			transObj = nicTransactionService.findById(
					transObj.getTransactionId(), false, true, true, false);

			res = dataPackService.preparePersoData_V5(transObj);
			String transactionStatus = "";
			if (res != null) {
				if (res.getStatus() == 200) {
					transactionStatus = PERSO_PACKED_LDS_COMPLETED
							+ "_COMPLETED";
					uploadJobService.resetReprocessCount(
							obj.getWorkflowJobId(), 0);
				} else {
					transactionStatus = PERSO_PACKED_LDS_COMPLETED + "_ERROR";
				}
				uploadJobSerivce.updateJobStatus(obj.getWorkflowJobId(),
						transactionStatus);
			}
			// }
		} catch (Exception e) {
			e.printStackTrace();
			try {
				uploadJobService.updateJobState(obj.getWorkflowJobId(),
						STATUS_ERROR, PERSO_REGISTER);
			} catch (NicUploadJobServiceException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			nicCommandUtil.setErrorFlag(obj.getWorkflowJobId(), true,
					uploadJobService);

		}
		return res;
	}

	private static String getNameOfHolder(String surname, String firstname,
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

	private static String getNameOfHolder(String surname, String firstname,
			String middlename, String space) {
		String nameOfHolder = "";
		if (StringUtils.isNotEmpty(surname)) {
			nameOfHolder += StringUtils.trim(surname);
		}

		if (StringUtils.isNotEmpty(middlename)) {
			if (StringUtils.length(nameOfHolder) > 0) {
				nameOfHolder += space;
			}
			nameOfHolder += StringUtils.trim(middlename);
		}

		if (StringUtils.isNotEmpty(firstname)) {
			if (StringUtils.length(nameOfHolder) > 0) {
				nameOfHolder += space;
			}
			nameOfHolder += StringUtils.trim(firstname);
		}
		return nameOfHolder;
	}

	private static byte[] getTransactionDocument(NicTransaction nicTransaction,
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
		return null;
	}

	private static String removeAccent(String s) {

		String temp = Normalizer.normalize(s, Normalizer.Form.NFD);
		Pattern pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
		return pattern.matcher(temp).replaceAll("");
	}

	private void getTokenA08() {
		do {
			int countToken = 0;
			if (tokenA08 == "" || tokenA08 == null) {
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
					tokenA08 = resultToken.get("access_token");
				} else {
					countToken++;
				}
			}
			/*
			 * if (expireTokenBNG == 0 && tokenBNG != "") {
			 * ReAccessToken(tokenBNG); }
			 */
			if (countToken == 3)
				break;
		} while (tokenA08 == "");
	}

	@ExceptionHandler
	private Map<String, String> GetTokenAPI(String username, String password)
			throws JsonParseException, JsonMappingException, IOException {
		Map<String, String> accessToken = new HashMap<String, String>();

		String urlParameters = "username=" + username + "&password=" + password;
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

	private Boolean checkDataResponseLDS(LdsResponseWsDto obj,
			Boolean isEpassport) {
//		if ((isEpassport) && (obj.getCom() == null || obj.getSod() == null))
//			return false;
		if (StringUtils.isBlank(obj.getMrzLine1())
				|| StringUtils.isBlank(obj.getMrzLine2()))
			return false;

		return true;
	}

	private String sendPostRequestSync(String requestUrl, String data,
			String token, String typeToken) {
		String response = "";
		// StringBuffer jsonString = new StringBuffer();
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
				String st = sb.toString();
				JSONObject myResponse = new JSONObject(st);
				if (!myResponse.toString().equals("{}")) {
					response = myResponse.getString("data");
				}
			}
		} catch (Exception e) {

		}
		return response;
	}

	private byte[] GetDataXml_A08(String urlParameters, String token,
			String typeToken) throws JsonParseException, JsonMappingException,
			IOException {

		byte[] res = null;
		try {
			URL url = new URL(
					"http://192.168.1.15:8044/app/rest/v2/PersoData/findByReceiptNo?receiptNo="
							+ urlParameters);
			HttpURLConnection connection = (HttpURLConnection) url
					.openConnection();
			connection.setRequestMethod("GET");
			connection.setRequestProperty("Authorization", typeToken.trim()
					+ " " + token.trim());
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
				JSONObject arrayResp = new JSONObject(st);
				if (!arrayResp.toString().equals("{}")) {
					String code = arrayResp.getString("code");
					if (code.equals("200")) {
						JSONObject data = arrayResp.getJSONObject("data");
						JSONArray result = data.getJSONArray("result");
						for (int i = 0; i < result.length(); i++) {

							JSONObject myResponse = new JSONObject();
							myResponse = result.getJSONObject(i);
							String xmlData = myResponse.getString("XML_DATA")
									.replaceAll("\r\n", " ");
							res = xmlData.getBytes("UTF-8");
							// String xmlFormat =
							// XML.toJSONObject(xmlData).toString();
						}
					}
				}

			} else {
				// /Trả về log mã lỗi và thông tin lỗi

			}
		} catch (Exception e) {

		}
		return res;
	}

	public DataRegistrationLDS prepareDataLds(String trans, String ppno,
			NicUploadJob nicUploadJob) throws TransactionServiceException {
		DataRegistrationLDS response = new DataRegistrationLDS();
		NicTransaction nicTran = new NicTransaction();
		nicTran = nicTransactionService.findById(trans, false, true, true,
				false);
		Date doE = DateUtils.addYears(new Date(), nicTran.getValidityPeriod());
		String dateOfExpiry = DateUtil.parseDate(doE, DateUtil.FORMAT_YYYYMMDD);
		String dateOfIssue = DateUtil.parseDate(new Date(),
				DateUtil.FORMAT_YYYYMMDD);
		String nameOfHolder = getNameOfHolder(nicTran.getNicRegistrationData()
				.getSurnameFull(), nicTran.getNicRegistrationData()
				.getFirstnameFull(), nicTran.getNicRegistrationData()
				.getMiddlenameFull());
		String dateOfBirth = DateUtil.parseDate(nicTran
				.getNicRegistrationData().getDateOfBirth(),
				DateUtil.FORMAT_YYYYMMDD);
		String passportType = nicTran.getPassportType();
		String nationality = nicTran.getNicRegistrationData().getNationality();
		String issueState = "VNM";
		String passportNo = ppno;
		String searchName = removeAccent(nameOfHolder).toUpperCase();
		String gender = nicTran.getNicRegistrationData().getGender();
		String nin = nicTran.getNin();
		String placeOfBirth = nicTran.getNicRegistrationData()
				.getPlaceOfBirth();
		String priority = "NORMAL";
		if (nicUploadJob.getNicTransaction().getPriority() == 1)
			priority = "HIGH";
		else if (nicUploadJob.getNicTransaction().getPriority() == 2)
			priority = "HIGHEST";

		Boolean hasDG3 = false;
		String finger01Position = "";
		String finger02Position = "";
		String finger01 = "";
		String finger02 = "";
		// get encode fingers from transaction data
		if (StringUtils.isNotBlank(nicTran.getNicRegistrationData()
				.getFpEncode())) {
			String[] fpEncodes = StringUtils.split(nicTran
					.getNicRegistrationData().getFpEncode(), ",");
			if (fpEncodes.length >= 1)
				finger01Position = fpEncodes[0];
			if (fpEncodes.length >= 2)
				finger02Position = fpEncodes[1];
			byte[] finger01_ = null;
			byte[] finger02_ = null;
			finger01_ = this.getTransactionDocument(nicTran,
					NicTransactionAttachment.DOC_TYPE_MINUTIA_CBEFF,
					finger01Position);
			finger02_ = this.getTransactionDocument(nicTran,
					NicTransactionAttachment.DOC_TYPE_MINUTIA_CBEFF,
					finger02Position);

			if (finger01_ != null && finger02_ != null) {
				hasDG3 = true;
				finger01 = Base64.encodeBase64String(finger01_);
				finger02 = Base64.encodeBase64String(finger02_);
			}
		}
		byte[] photo_ = this.getTransactionDocument(nicTran,
				NicTransactionAttachment.DOC_TYPE_PHOTO_CHIP,
				NicTransactionAttachment.DEFAULT_SERIAL_NO);
		String photo = Base64.encodeBase64String(photo_);

		response.setDateOfBirth(dateOfBirth);
		response.setDateOfExpire(dateOfExpiry);
		response.setDateOfIssue(dateOfIssue);
		response.setDescription("");
		if (hasDG3) {
			response.setFinger01(finger01);
			response.setFinger02(finger02);
			response.setPositionFinger01(finger01Position);
			response.setPositionFinger02(finger02Position);
		}
		response.setPhoto(photo);
		response.setIssueState(issueState);
		response.setNameOfHolder(nameOfHolder);
		response.setNationality(nationality);
		response.setNin(nin);
		response.setPassportNo(passportNo);
		response.setGender(gender);
		response.setPassportType(passportType);
		response.setPlaceOfBirth(placeOfBirth);
		response.setPlaceOfIssue(nicTran.getIssuingAuthority());
		response.setPlaceOfIssueId("56003");
		response.setPriority(priority);
		response.setSearchName(searchName);
		response.setSignerName("");
		response.setSignerPosition("");
		response.setHasDG3(hasDG3);

		return response;
	}

	// ===============================
	// ==== LẤY KẾT QUẢ SỐ BIÊN NHẬN =================================

	@POST
	@Produces("application/json")
	@Consumes("application/json")
	@Path("/getRecieptNoByTran/{trans}")
	public ResponseBase<String> getRecieptNoByTran(
			@PathParam("trans") String trans) {
		ResponseBase<String> rep = new ResponseBase<String>();
		PropertyModel prModel = new PropertyModel();
		prModel.setCode(-101);
		if (trans != "") {
			try {
				BaseModelSingle<NicUploadJob> bGetUJ = uploadJobSerivce
						.getUploadJobByTransactionIdSinger(null, trans);
				NicUploadJob nicU = bGetUJ.getModel();
				// Kiểm tra dữ liệu workflow có phù hợp không
				if (nicU != null && StringUtils.isNotEmpty(nicU.getReceiptNo())) {
					prModel.setCode(99);
					prModel.setMessage("Gui ma bien nhan.");
					rep.setResponse(nicU.getReceiptNo());

					/* Lưu bảng thống kê truyền nhận */
					this.saveOrUpRptData(Contants.URL_GET_RECIEPT_NO_BY_TRAN,
							1, null);
				} else {
					prModel.setMessage("Ho so khong hop le. TranId: " + trans);
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				prModel.setMessage(e.getMessage());
				e.printStackTrace();
			}
		} else {
			prModel.setMessage("Thieu du lieu dau vao. TranId: " + trans);
		}
		rep.setProperty(prModel);
		return rep;
	}

	// ==== LẤY GÓI XML ============================================
	@POST
	@Produces("application/json")
	@Consumes("application/json")
	@Path("/getPackageXML/{trans}")
	public ResponseBase<byte[]> getPackageXML(@PathParam("trans") String trans) {
		ResponseBase<byte[]> rep = new ResponseBase<byte[]>();
		PropertyModel prModel = new PropertyModel();
		prModel.setCode(-1);
		if (StringUtils.isNotEmpty(trans)) {
			try {
				byte[] xmlByte = null;
				List<NicTransactionAttachment> perso = nicTransactionAttachmentService
						.findNicTransactionAttachments(trans,
								NicTransactionAttachment.DOC_TYPE_PERSO,
								NicTransactionAttachment.DEFAULT_SERIAL_NO)
						.getListModel();
				if (CollectionUtils.isNotEmpty(perso) && perso.size() > 0) {
					xmlByte = perso.get(0).getDocData();
				}
				if (xmlByte != null) {
					rep.setResponse(xmlByte);
					prModel.setCode(99);
					prModel.setMessage("");

					/* Lưu bảng thống kê truyền nhận */
					this.saveOrUpRptData(Contants.URL_GET_PACKAGE_XML, 1, null);

				} else {
					prModel.setCode(-404);
					prModel.setMessage("Chua co goi du lieu xml tu A08");
					rep.setProperty(prModel);
					return rep;
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				prModel.setMessage(e.getMessage());
				e.printStackTrace();
			}
		} else {
			prModel.setCode(-101);
			prModel.setMessage("Thieu du lieu dau vao. TransationId=" + trans);
		}
		rep.setProperty(prModel);
		return rep;
	}

	@GET
	@Produces("application/json")
	@Consumes("application/json")
	@Path("/getListIssued/{siteCode}")
	public ResponseBase<List<PassportStatusInfo>> getListIssued(
			@PathParam("siteCode") String siteCode) {
		ResponseBase<List<PassportStatusInfo>> result = new ResponseBase<List<PassportStatusInfo>>();
		PropertyModel prModel = new PropertyModel();
		prModel.setCode(Contants.ENUM_STATUS_CODE_HTTP_ERROR);
		prModel.setMessage(Contants.STRING_STATUS_CODE_HTTP_ERROR);
		try {
			if (StringUtils.isNotEmpty(siteCode)) {
				BaseModelList<SyncQueueJob> baseFindQByR = queueJobService
						.findQueueByReceiver(siteCode,
								Contants.CODE_STATUS_QUEUE_PENDING,
								Contants.QUEUE_OBJ_TYPE_ISSUE_C,
								Contants.MAX_DEFAULT_GET_LIST_RECORDS);
				List<SyncQueueJob> queues = baseFindQByR.getListModel();
				if (queues != null && queues.size() > 0) {
					List<PassportStatusInfo> listData = new ArrayList<PassportStatusInfo>();
					for (SyncQueueJob q : queues) {
						NicDocumentData nicData = documentDataService
								.getDocumentDataById(q.getObjectId())
								.getModel();
						if (nicData != null) {
							PassportStatusInfo data = new PassportStatusInfo();
							data.setPassportNo(nicData.getId().getPassportNo());
							data.setTransactionId(q.getObjectId());
							data.setPassportStatus("I");
							data.setUpdateBy(nicData.getUpdateBy());
							data.setUpdateDate(HelperClass.convertDateToString(
									nicData.getUpdateDatetime(), "yyyyMMdd"));
							data.setUpdateWkstnID(nicData.getUpdateWkstnId());
							listData.add(data);
						}
					}
					if (listData.size() > 0) {
						result.setResponse(listData);
						prModel.setCode(Contants.ENUM_STATUS_CODE_HTTP_SUCESS);
						prModel.setMessage(Contants.STRING_STATUS_CODE_HTTP_SUCESS);

						/* Lưu bảng thống kê truyền nhận */
						this.saveOrUpRptData(Contants.URL_GET_LIST_ISSUED,
								listData.size(), siteCode);
					} else {
						prModel.setCode(Contants.ENUM_STATUS_CODE_HTTP_SUCESS);
						prModel.setMessage(Contants.STRING_STATUS_CODE_HTTP_NOT_FOUND);
					}
				} else {
					prModel.setCode(Contants.ENUM_STATUS_CODE_HTTP_SUCESS);
					prModel.setMessage(Contants.STRING_STATUS_CODE_HTTP_NOT_FOUND);
				}
			} else {
				prModel.setCode(Contants.ENUM_STATUS_CODE_HTTP_ERROR_INPUT);
				prModel.setMessage(Contants.RESPONSE_MESSAGE_FAIL_API);
			}
		} catch (Exception e) {
			e.printStackTrace();
			prModel.setCode(414);
			prModel.setMessage(Contants.MESSAGE_EXCEPTION);
			/*
			 * Lưu dữ liệu nếu có Exception
			 */
			String dataResponse = "";
			String keyId = "GLI_"
					+ new SimpleDateFormat("ddMMyyyy").format(new Date());
			try {
				dataResponse = new ObjectMapper().writeValueAsString(result);
			} catch (Exception e2) {
				logger.error(e2.getMessage());
			}
			this.saveException(Contants.TYPE_GLI, Contants.URL_GET_LIST_ISSUED,
					siteCode, keyId, dataResponse, e);
		}
		result.setProperty(prModel);
		return result;
	}

	@POST
	@Produces("application/json")
	@Consumes("application/json")
	@Path("/updateHandoverC")
	public ResponseBase<Boolean> updateHandoverC(UpdatePackageRequest listIssue)
			throws JAXBException {
		ResponseBase<Boolean> result = new ResponseBase<Boolean>();
		PropertyModel prModel = new PropertyModel();
		prModel.setCode(Contants.ENUM_STATUS_CODE_HTTP_ERROR);
		prModel.setMessage(Contants.STRING_STATUS_CODE_HTTP_ERROR);
		// Boolean WFDONE = false;
		String siteCode = "";
		String regSiteCode = "";
		String printSite = "";
		EppWsLog eppWsLog = new EppWsLog();
		eppWsLog.setType(Contants.TYPE_DSC);
		eppWsLog.setUrlRequest(Contants.URL_UPDATE_HANDOVER_C);

		try {
			eppWsLog.setDataRequest(new ObjectMapper()
					.writeValueAsString(listIssue));
		} catch (IOException e1) {
			logger.error(e1.getMessage() + " - convert object to json fail.");
			e1.printStackTrace();

		}
		eppWsLog.setKeyId(listIssue.getPackageId());
		try {
			if (listIssue != null && listIssue.getTransactions() != null
					&& listIssue.getTransactions().size() > 0) {

				/* Thêm vào bảng danh sách handover */
				BaseModelSingle<NicListHandover> bFindH = listHandoverService
						.findByPackageId(new NicListHandoverId(listIssue
								.getPackageId(), NicListHandover.TYPE_LIST_C));
				if (!bFindH.isError() || bFindH.getMessage() != null) {
					throw new Exception(bFindH.getMessage());
				}
				if (bFindH.getModel() == null) {
					NicListHandover handoverC = new NicListHandover();
					handoverC.setId(new NicListHandoverId(listIssue
							.getPackageId(), NicListHandover.TYPE_LIST_C));
					handoverC.setPackageName(Contants.CODE_PACKAGE_NAME_C);
					handoverC.setCountTransaction(listIssue.getAmount());
					handoverC.setHandoverStatus(0);
					handoverC.setCreateDate(Calendar.getInstance().getTime());
					handoverC.setCreateBy(listIssue.getUserName());
					handoverC.setCreatorName(listIssue.getFullName());
					BaseModelSingle<Void> bSaveH = listHandoverService
							.saveHandover(handoverC);
					if (!bSaveH.isError() || bSaveH.getMessage() != null) {
						throw new Exception(bSaveH.getMessage());
					}
				}
				/*--end--*/
				if (listIssue.getTransactions() != null) {
					for (service.perso.model.TransactionPerso tran : listIssue
							.getTransactions()) {
						// Kiểm tra đủ hồ sơ hết chưa?
						BaseModelSingle<NicTransaction> bFindT = nicTransactionService
								.findTransactionById(tran.getTransactionId());
						if (!bFindT.isError() || bFindT.getMessage() != null) {
							throw new Exception(bFindT.getMessage());
						}
						NicTransaction txn = bFindT.getModel();
						if (txn == null) {
							prModel.setCode(Contants.ENUM_RESPONSE_CODE_FAIL_NOT_ENOUGH_HS);
							prModel.setMessage("Chưa upload đủ hồ sơ. Hồ sơ đang thiếu hiện tại: "
									+ tran.getTransactionId());
							result.setProperty(prModel);
							return result;
						} else {

						}

						String trans = tran.getTransactionId();
						BaseModelSingle<NicUploadJob> bGetUJ = uploadJobService
								.getUploadJobByTransactionIdSinger(null, trans);
						if (!bGetUJ.isError() || bGetUJ.getMessage() != null) {
							throw new Exception(bGetUJ.getMessage());
						}
						NicUploadJob obj = bGetUJ.getModel();
						siteCode = obj.getNicTransaction().getIssSiteCode();
						regSiteCode = obj.getNicTransaction().getRegSiteCode();
						printSite = tran.getPrintSite();
						Date startTime = new Date();
						String transactionStatus = JOB_STATE_PASSPORT_REGISTER
								+ STAGE_COMPLETED;
						// this.saveTransactionLog(trans,
						// JOB_STATE_PASSPORT_REGISTER, transactionStatus,
						// startTime, new Date(), null, "",
						// obj.getJobReprocessCount());
						BaseModelSingle<NicUploadJob> bGUJ = uploadJobService
								.getUploadJobByTransactionIdSinger(null, trans);
						if (!bGUJ.isError() || bGUJ.getMessage() != null) {
							throw new Exception(bGUJ.getMessage());
						}
						obj = bGUJ.getModel();
						NicDocumentData data = documentDataService
								.findDocDataById(obj.getTransactionId(),
										obj.getTempPassportNo());
						data.setSigner(tran.getSigner());
						data.setPositionSigner(tran.getPositionSigner());
						// Hoald thêm cập nhật trạng thái hộ chiếu.
						if (data.getActiveFlag() == null)
							data.setActiveFlag(Boolean.TRUE);
						if (data.getIssuedFlag() == null)
							data.setIssuedFlag(Boolean.TRUE);
						data.setIssueBy(listIssue.getUserName());
						if (StringUtils.isNotBlank(listIssue.getCreatedDate())) {
							data.setIssueDatetime(DateUtil.strToDate(
									listIssue.getCreatedDate(),
									DateUtil.FORMAT_YYYYMMDD));
						}
						data.setStatus(NicDocumentData.DOCUMENT_DATA_STATUS_ISSUANCE);
						//
						// data.setSerialNo(tran.getSerialPhoi());
						BaseModelSingle<Void> baseSOUDoc = documentDataDao
								.saveOrUpdateDocumentData(data);
						if (!baseSOUDoc.isError()
								|| baseSOUDoc.getMessage() != null) {
							throw new Exception(baseSOUDoc.getMessage());
						}

						NicDocumentHistory documentHistory = new NicDocumentHistory();
						documentHistory.setPassportNo(data.getId()
								.getPassportNo());
						documentHistory.setStatus(data.getStatus());
						documentHistory.setStatusUpdateBy(data.getIssueBy());
						documentHistory.setStatusUpdateTime(data
								.getIssueDatetime());

						documentHistoryService.save(documentHistory);
						// end
						BaseModelSingle<EppListHandoverDetail> bGetHD = eppListHandoverDetailService
								.getListPackageByPackageIdAndTranID(
										listIssue.getPackageId(),
										tran.getTransactionId(),
										NicListHandover.TYPE_LIST_C);
						if (!bGetHD.isError() || bGetHD.getMessage() != null) {
							throw new Exception(bGetHD.getMessage());
						}
						EppListHandoverDetail tp = bGetHD.getModel();
						if (tp == null) {
							tp = new EppListHandoverDetail();
							tp.setPackageId(listIssue.getPackageId());
							tp.setTransactionId(tran.getTransactionId());
							tp.setTypeList(NicListHandover.TYPE_LIST_C);
							BaseModelSingle<Boolean> bSaveHD = eppListHandoverDetailService
									.saveHandoverDetail(tp);
							if (!bSaveHD.isError()
									|| bSaveHD.getMessage() != null) {
								throw new Exception(bSaveHD.getMessage());
							}
						}
						txn.setTransactionStatus(NicTransaction.TRANSACTION_STATUS_CREATED_C);
						BaseModelSingle<Boolean> baseSaveTran = nicTransactionService
								.saveOrUpdateTransaction(txn);
						if (!baseSaveTran.isError()
								|| baseSaveTran.getMessage() != null) {
							throw new Exception(baseSaveTran.getMessage());
						}
						// this.saveTransactionLog(txn.getTransactionId(),
						// obj.getJobCurrentStage(), transactionStatus,
						// startTime,
						// endTime, logInfo, logData, retryCount);

						// đưa vào hàng đợi gửi về các cửa khẩu
						List<BorderGate> borderList = borderGateService
								.findBorderGateBySync(Contants.FLAG_Y);
						if (borderList != null) {
							for (BorderGate gate : borderList) {
								this.addObjToQueueXncJob(
										TransactionService.OBJ_TYPE_QUEUE_XNC_PP,
										gate.getCode(), data.getId()
												.getPassportNo());
							}
						}
					}
				}
				// if(WFDONE){
				BaseModelSingle<Boolean> bAddQJR = this.addObjToQueueJob(
						listIssue.getPackageId(), Contants.QUEUE_OBJ_TYPE_DC,
						regSiteCode, null);
				if (!bAddQJR.isError() || bAddQJR.getMessage() != null) {
					throw new Exception(bAddQJR.getMessage());
				}
				if (!siteCode.equals(regSiteCode)) {

					if (siteCode.equals(Contants.QUEUE_SITE_CODE_MB)
							|| siteCode.equals(Contants.QUEUE_SITE_CODE_MT)
							|| siteCode.equals(Contants.QUEUE_SITE_CODE_MN)) {
						BaseModelSingle<Boolean> bAddQJ = this
								.addObjToQueueJob(listIssue.getPackageId(),
										Contants.QUEUE_OBJ_TYPE_DC, siteCode,
										null);
						if (!bAddQJ.isError() || bAddQJ.getMessage() != null) {
							throw new Exception(bAddQJ.getMessage());
						}
					} else {
						for (service.perso.model.TransactionPerso tran : listIssue
								.getTransactions()) {
							BaseModelSingle<Boolean> bAddQJ = this
									.addObjToQueueJob(tran.getTransactionId(),
											Contants.QUEUE_OBJ_TYPE_HS_FULL,
											siteCode, null);
							if (!bAddQJ.isError()
									|| bAddQJ.getMessage() != null) {
								throw new Exception(bAddQJ.getMessage());
							}
						}
					}

				}
				if (bAddQJR.getModel()) {
					/* Lưu bảng thống kê truyền nhận */
					this.saveOrUpRptData(Contants.URL_UPDATE_HANDOVER_C, 1,
							printSite);
				}
				// }
				result.setResponse(true);
				prModel.setCode(Contants.ENUM_STATUS_CODE_HTTP_SUCESS);
				prModel.setMessage(Contants.STRING_STATUS_CODE_HTTP_SUCESS);

				// Lưu hàng đợi đồng bộ sang hệ thống cũ
				this.addObjToQueueJob(listIssue.getPackageId(),
						Contants.QUEUE_OBJ_TYPE_DC,
						Contants.QUEUE_RECEIVER_A08_HH, null);

			} else {
				prModel.setCode(Contants.ENUM_STATUS_CODE_HTTP_ERROR_INPUT);
				prModel.setMessage(Contants.RESPONSE_MESSAGE_FAIL_API);
			}
		} catch (Exception e) {
			String error = Contants.STRING_STATUS_CODE_HTTP_ERROR;
			error += error + " || " + e.getMessage();
			prModel.setCode(Integer.parseInt(Contants.CODE_THROW_EXCEPTION));
			prModel.setMessage(Contants.MESSAGE_EXCEPTION);
			eppWsLog.setDataResponse(prModel.toString());
			eppWsLog.setMessageErrorLog(e.getMessage()
					+ " - updateHandoverC thất bại.");
			eppWsLog.setCreatedDate(new Date());
			BaseModelSingle<Boolean> baseCheck = eppWsLogService
					.inserDataTable(eppWsLog);
			if (!baseCheck.getModel() || baseCheck.getMessage() != null) {
				logger.error(baseCheck.getMessage() + " - save EppWsLog fail.");
			}
		}

		result.setProperty(prModel);
		return result;
	}

	/*
	 * Hủy hộ chiếu do in hỏng (sai Icao line), đóng gói mới và trả xml.
	 */
	@POST
	@Produces("application/json")
	@Consumes("application/json")
	@Path("/reprintPassport")
	public ResponseBase<ReprintPassportInfo> reprintPassport(
			UpdateLDSPackageDto request) {
		ResponseBase<ReprintPassportInfo> rep = new ResponseBase<ReprintPassportInfo>();
		PropertyModel prModel = new PropertyModel();
		Date startTime = new Date();
		rep.setResponse(null);
		rep.setProperty(prModel);
		prModel.setCode(-1);
		String logData = "";
		String transactionStatus = "";
		String statusPerso = "";
		String transactionStage = "";
		LdsResponseWsDto response = new LdsResponseWsDto();
		NicUploadJob nicUploadJob = null;
		InfoPassportC infoPP = null;
		ReprintPassportInfo reprinInfo = null;
		try {
			if (StringUtils.isNotEmpty(request.getTransactionId())
					&& StringUtils.isNotEmpty(request.getPassportNo())
					&& StringUtils.isNotEmpty(request.getPrevPassportNo())) {
				request.setTrans(request.getTransactionId());
				reprinInfo = new ReprintPassportInfo();
				reprinInfo.setCheckUpdatePassportNo(false);
				reprinInfo.setCheckUpdateStatusPassport(false);
				/*
				 * Cập nhật trạng thái in lỗi
				 */
				List<NicUploadJob> nicUploadJobs = uploadJobSerivce
						.findAllByTransactionId(request.getTransactionId());
				if (nicUploadJobs != null) {
					nicUploadJob = nicUploadJobs.get(0);
					NicTransaction tran = nicUploadJob.getNicTransaction();
					NicDocumentDataId id = new NicDocumentDataId();
					id.setPassportNo(request.getPrevPassportNo());
					id.setTransactionId(request.getTransactionId());
					NicDocumentData nicdel = documentDataService.findById(id);
					if (nicdel == null) {
						rep.getProperty().setMessage(
								"Không tìm thấy bản ghi hồ sơ hộ chiếu.");
						return rep;
					}
					// List<EppListHandoverDetail> listHanDetail =
					// eppListHandoverDetailService
					// .findAllByTranIdOrType(nicdel.getId()
					// .getTransactionId(),
					// NicListHandover.TYPE_LIST_C);
					// if (listHanDetail != null && listHanDetail.size() > 0) {
					// for (EppListHandoverDetail eppHanDetails : listHanDetail)
					// {
					// eppListHandoverDetailService
					// .deleteHandoverDetail(eppHanDetails
					// .getPackageId(), eppHanDetails
					// .getTransactionId(),
					// eppHanDetails.getTypeList());
					// }
					// }
					transactionStatus = PERSO_PRINTED + STAGE_ERROR;
					statusPerso = STATUS_ERROR;

					// Cập nhật lại trạng thái in hỏng hộ chiếu.
					if (nicdel != null) {
						nicdel.setCancelDatetime(new Date());
						nicdel.setActiveFlag(false);
						nicdel.setCancelType(NicDocumentData.DOCUMENT_DATA_CANCEL_TYPE_IN_HONG);
						nicdel.setCancelBy(request.getCancelBy());
						nicdel.setCancelReason("In hỏng do sai Icao line");
						nicdel.setStatus(NicDocumentData.DOCUMENT_DATA_STATUS_CANCELLED);
						documentDataService.saveOrUpdateDocData(nicdel);
					}
					// uploadJobService.updateJobState(
					// nicUploadJob.getWorkflowJobId(), statusPerso,
					// PERSO_REGISTER);
					// uploadJobService.updateJobStatus(
					// nicUploadJob.getWorkflowJobId(), transactionStatus);
					// Xóa dữ liệu hộ chiếu tạm
					nicUploadJob.setTempPassportNo("");
					nicUploadJob.setReceiptNo("");
					uploadJobSerivce.saveOrUpdate(nicUploadJob);

					// cập nhật trạng thái hồ sơ
					// tran.setTransactionStatus(Contants.TRANSACTION_STATUS_APPROVE_D);
					// nicTransactionService.saveOrUpdateTransaction(tran);

					this.saveTransactionLog(tran.getTransactionId(),
							transactionStage, transactionStatus, startTime,
							new Date(), null, logData,
							nicUploadJob.getJobReprocessCount());
					reprinInfo.setCheckUpdateStatusPassport(true);

					/*
					 * gán số hộ chiếu mới
					 */
					if (reprinInfo.getCheckUpdateStatusPassport()) {
						String trans = request.getTransactionId();
						String ppno = request.getPassportNo();
						if (StringUtils.isNotBlank(trans)
								&& StringUtils.isNotBlank(ppno)) {

							// cập nhật chipSerialNo nếu có
							if (StringUtils.isNotBlank(request.getChipSerial())) {
								NicDocumentData nicDocData = documentDataService
										.findDocDataById(trans, ppno);
								if (nicDocData != null) {
									nicDocData.setChipSerialNo(request
											.getChipSerial());
									nicDocData.setSerialNo(request
											.getPhoiSerial());
									BaseModelSingle<Void> baseSaveDocData = documentDataService
											.saveOrUpdateDocData(nicDocData);
									if (!baseSaveDocData.isError()
											|| baseSaveDocData.getMessage() != null) {
										throw new Exception(
												baseSaveDocData.getMessage());
									}
								}
							}

							// === Xử lý tạo biên nhận ===============
							BaseModelSingle<NicUploadJob> bGetUJ = uploadJobService
									.getUploadJobByTransactionIdSinger(null,
											trans);
							NicUploadJob obj = bGetUJ.getModel();
							// NicTransaction transObj = nicTransactionService
							// .findById(trans, false, true, true, false);
							if (obj == null) {
								prModel.setCode(Contants.ENUM_STATUS_CODE_HTTP_ERROR);
								prModel.setMessage("Không có dữ liệu hồ sơ hộ chiếu.");
								rep.setProperty(prModel);
								return rep;
							}

							// update trạng thái hồ sơ
							// transObj.setTransactionStatus(Contants.TRANSACTION_STATUS_PERSO_REGISTER_COMPLETED);
							// nicTransactionService.saveOrUpdateTransaction(transObj);

							nicUploadJobs = uploadJobSerivce
									.findAllByTransactionId(trans);
							if (nicUploadJobs != null) {
								nicUploadJob = nicUploadJobs.get(0);
								nicUploadJob.setTempPassportNo(ppno);
								uploadJobSerivce
										.saveOrUpdateService(nicUploadJob);
								startTime = new Date();
								transactionStatus = JOB_STATE_PASSPORT_REGISTER
										+ STAGE_COMPLETED;
								this.saveTransactionLog(trans,
										JOB_STATE_PASSPORT_REGISTER,
										transactionStatus, startTime,
										new Date(), null, "",
										nicUploadJob.getJobReprocessCount());
								reprinInfo.setCheckUpdatePassportNo(true);
							} else {
								prModel.setCode(404);
								prModel.setMessage("Khong tim thay du lieu ban ghi - "
										+ trans);
								rep.setProperty(prModel);
								return rep;
							}
						} else {
							prModel.setMessage("Thieu du lieu dau vao. TransationId="
									+ trans + " / passportNo=" + ppno);
							rep.setProperty(prModel);
							return rep;
						}
					}
					/*
					 * đóng gói LDS
					 */
					if (reprinInfo.getCheckUpdatePassportNo()) {
						nicUploadJob = uploadJobSerivce.findUploadJobByTransId(
								request.getTrans()).getModel();

						// kiểm tra số hộ chiếu đang hoạt động ứng với số CMND.
						NicTransaction transObj = nicTransactionService
								.findById(request.getTrans(), false, true,
										true, false);
						if (transObj != null) {
							List<NicTransaction> listTran = nicTransactionService
									.findTranByNinAndTypePassport(
											transObj.getNin(),
											transObj.getPassportType());
							if (listTran != null && listTran.size() > 0) {
								for (NicTransaction nicTransaction : listTran) {
									Collection<NicDocumentData> nicDoc = documentDataService
											.findByTransactionId(
													nicTransaction
															.getTransactionId())
											.getModel();
									List<NicDocumentData> listDoc = (List<NicDocumentData>) nicDoc;
									if (listDoc != null && listDoc.size() > 0) {
										for (NicDocumentData nicDocumentData : listDoc) {
											if (nicDocumentData
													.getDateOfExpiry()
													.getTime() > new Date()
													.getTime()
													&& (nicDocumentData
															.getStatus()
															.equals(Contants.DOC_STATUS_ISSUANCE)
															|| nicDocumentData
																	.getStatus()
																	.equals(Contants.DOC_STATUS_PERSONALIZED) || nicDocumentData
															.getStatus()
															.equals(NicDocumentData.DOCUMENT_DATA_STATUS_PACKED))) {
												infoPP = new InfoPassportC();
												infoPP.setChipSerialNo(nicDocumentData
														.getChipSerialNo());
												if (nicDocumentData
														.getDateOfExpiry() != null) {
													String date = DateUtil
															.parseDate(
																	nicDocumentData
																			.getDateOfExpiry(),
																	DateUtil.FORMAT_YYYYMMDD);
													infoPP.setDateOfExpiry(date);
												}
												if (nicDocumentData
														.getDateOfIssue() != null) {
													String date = DateUtil
															.parseDate(
																	nicDocumentData
																			.getDateOfIssue(),
																	DateUtil.FORMAT_YYYYMMDD);
													infoPP.setDateOfIssue(date);
												}
												infoPP.setIcaoLine1(nicDocumentData
														.getIcaoLine1());
												infoPP.setIcaoLine2(nicDocumentData
														.getIcaoLine2());
												infoPP.setPassportNo(nicDocumentData
														.getId()
														.getPassportNo());
												infoPP.setSignerName(nicDocumentData
														.getSigner());
												infoPP.setSignerPosition(nicDocumentData
														.getPositionSigner());
												infoPP.setStatus(nicDocumentData
														.getStatus());
												NicTransaction nicTran = nicDocumentData
														.getNicTransaction();
												if (nicTran != null) {
													NicRegistrationData nicReg = nicTran
															.getNicRegistrationData();
													if (nicReg != null) {
														infoPP.setFpEncode(nicReg
																.getFpEncode());
													}
													infoPP.setPlaceOfIssueId(nicDocumentData.getPlaceOfIssueCode());
													infoPP.setPlaceOfIssueName(this.getSiteName(nicDocumentData.getPlaceOfIssueCode()));
													infoPP.setPassportType(nicTran
															.getPassportType());
													infoPP.setIsEpassport(nicTran
															.getIsEpassport() != null ? (nicTran
															.getIsEpassport()
															.equals("Y") ? true
															: false) : false);

												}
												if (nicUploadJob != null) {
													infoPP.setPersonId(nicUploadJob != null ? nicUploadJob
															.getNicTransaction()
															.getPersonCode()
															+ ""
															: null);
												}
												prModel.setCode(Integer
														.parseInt(Contants.CODE_DATA_EXIST));
												prModel.setMessage("Đã tồn tại số hộ chiếu đang hoạt động đối với CMND: "
														+ transObj.getNin());
												response.setInfoPassport(infoPP);
												reprinInfo
														.setPackLDSInfo(response);
												rep.setProperty(prModel);
												return rep;
											}
										}
									}
								}
							}
						}

						if (nicUploadJob != null
								&& StringUtils.isNotBlank(nicUploadJob
										.getTempPassportNo())) {
							// kiểm tra tồn tại của số hộ chiếu đã gán.
							List<NicDocumentData> checkExist = documentDataService
									.findValidPassportById(nicUploadJob
											.getTempPassportNo());
							if (checkExist != null && checkExist.size() > 0) {
								prModel.setCode(203);
								prModel.setMessage("Số hộ chiếu: "
										+ nicUploadJob.getTempPassportNo()
										+ Contants.MESSAGE_EXIST);
								rep.setProperty(prModel);
								return rep;
							} else {
								response = this
										.LDSPacketDocument_V5(nicUploadJob);
								if (response != null
										&& response.getStatus() == 200) {
									if (this.checkDataResponseLDS(response,
											nicUploadJob.getNicTransaction()
													.getIsEpassport())) {
										prModel.setCode(Contants.ENUM_STATUS_CODE_HTTP_SUCESS);
										prModel.setMessage(Contants.MESSAGE_SUCCESS);
										reprinInfo.setPackLDSInfo(response);
										/* Lưu bảng thống kê truyền nhận */
										this.saveOrUpRptData(
												Contants.URL_GET_LDS, 1,
												nicUploadJob
														.getNicTransaction()
														.getIssuingAuthority());
									}
								} else {
									prModel.setCode(response.getStatus());
									prModel.setMessage(response.getMessage());
									rep.setProperty(prModel);
									return rep;
								}
							}
						} else {
							prModel.setCode(Contants.ENUM_STATUS_CODE_HTTP_ERROR);
							prModel.setMessage("Chua co du lieu ho so ho chieu");
							rep.setProperty(prModel);
							return rep;
						}
					}

					rep.setResponse(reprinInfo);
					prModel.setCode(200);
					prModel.setMessage(Contants.MESSAGE_SUCCESS);

				} else {
					prModel.setCode(Contants.ENUM_STATUS_CODE_HTTP_NOT_FOUND);
					prModel.setMessage("Không tìm thấy dữ liệu bản ghi hồ sơ hộ chiếu.");
				}
			} else {
				prModel.setCode(Contants.ENUM_STATUS_CODE_HTTP_ERROR_INPUT);
				prModel.setMessage("Thieu du lieu dau vao. TransationId = "
						+ request.getTransactionId() + " - ppNo = "
						+ request.getPassportNo() + " - prevPPNo = "
						+ request.getPrevPassportNo());
			}

		} catch (Exception e) {
			e.printStackTrace();
			prModel.setCode(414);
			prModel.setMessage(Contants.MESSAGE_EXCEPTION);
			/*
			 * Lưu dữ liệu nếu có Exception
			 */
			String dataResponse = "";
			String dataRequest = "";
			String keyId = request.getTransactionId();
			try {
				dataResponse = new ObjectMapper().writeValueAsString(rep);
				dataRequest = new ObjectMapper().writeValueAsString(request);
			} catch (Exception e2) {
				logger.error(e2.getMessage());
			}
			this.saveException("RPP", "reprintPassport", dataRequest, keyId,
					dataResponse, e);
		}
		rep.setProperty(prModel);
		return rep;
	}

	/*
	 * Hủy hộ chiếu in lỗi
	 */
	@POST
	@Produces("application/json")
	@Consumes("application/json")
	@Path("/updatePrintFail")
	public ResponseBase<Boolean> updatePrintFail(CancelPassportDetail request) {
		ResponseBase<Boolean> rep = new ResponseBase<Boolean>();
		PropertyModel prModel = new PropertyModel();
		Date startTime = new Date();
		rep.setResponse(false);
		rep.setProperty(prModel);
		prModel.setCode(-1);
		String logData = "";
		String transactionStatus = "";
		String statusPerso = "";
		String transactionStage = "";
		boolean check = true;
		try {
			if (StringUtils.isNotEmpty(request.getTransactionId())
					&& StringUtils.isNotEmpty(request.getPassportNo())
					&& StringUtils.isNotBlank(request.getCancelType())) {
				List<NicUploadJob> nicUploadJobs = uploadJobSerivce
						.findAllByTransactionId(request.getTransactionId());
				if (nicUploadJobs != null) {
					NicUploadJob nicUploadJob = nicUploadJobs.get(0);
					NicTransaction tran = nicUploadJob.getNicTransaction();
					NicDocumentDataId id = new NicDocumentDataId();
					id.setPassportNo(request.getPassportNo());
					id.setTransactionId(request.getTransactionId());
					NicDocumentData nicdel = documentDataService.findById(id);
					if (nicdel == null) {
						rep.getProperty().setMessage(
								"Không tìm thấy bản ghi hồ sơ hộ chiếu.");
						return rep;
					}
					if (request.getCancelType().equals(
							NicDocumentData.DOCUMENT_DATA_CANCEL_TYPE_IN_HONG)) {
						List<EppListHandoverDetail> listHanDetail = eppListHandoverDetailService
								.findAllByTranIdOrType(nicdel.getId()
										.getTransactionId(),
										NicListHandover.TYPE_LIST_C);
						if (listHanDetail != null && listHanDetail.size() > 0) {
							for (EppListHandoverDetail eppHanDetails : listHanDetail) {
								eppListHandoverDetailService
										.deleteHandoverDetail(eppHanDetails
												.getPackageId(), eppHanDetails
												.getTransactionId(),
												eppHanDetails.getTypeList());
							}
						}
						transactionStatus = PERSO_PRINTED + STAGE_ERROR;
						statusPerso = STATUS_ERROR;

						// Cập nhật lại trạng thái in hỏng hộ chiếu.
						if (nicdel != null) {
							nicdel.setCancelDatetime(new Date());
							nicdel.setActiveFlag(false);
							nicdel.setCancelType(NicDocumentData.DOCUMENT_DATA_CANCEL_TYPE_IN_HONG);
							nicdel.setCancelReason("In hỏng do sai Icao line");
							nicdel.setCancelBy(request.getCancelByName());
							nicdel.setStatus(NicDocumentData.DOCUMENT_DATA_STATUS_CANCELLED);
							documentDataService.saveOrUpdateDocData(nicdel);
						}
						uploadJobService.updateJobState(
								nicUploadJob.getWorkflowJobId(), statusPerso,
								PERSO_REGISTER);
						uploadJobService.updateJobStatus(
								nicUploadJob.getWorkflowJobId(),
								transactionStatus);

					}
					if (check) {
						// Xóa dữ liệu hộ chiếu tạm
						nicUploadJob.setTempPassportNo("");
						nicUploadJob.setReceiptNo("");
						uploadJobSerivce.saveOrUpdate(nicUploadJob);

						/* cập nhật trạng thái hồ sơ */
						tran.setTransactionStatus(Contants.TRANSACTION_STATUS_APPROVE_D);
						nicTransactionService.saveOrUpdateTransaction(tran);

						this.saveTransactionLog(tran.getTransactionId(),
								transactionStage, transactionStatus, startTime,
								new Date(), null, logData,
								nicUploadJob.getJobReprocessCount());

						rep.setResponse(true);
						prModel.setCode(200);
						prModel.setMessage(Contants.MESSAGE_SUCCESS);

					}
				} else {
					prModel.setCode(Contants.ENUM_STATUS_CODE_HTTP_NOT_FOUND);
					prModel.setMessage("Không tìm thấy dữ liệu bản ghi hồ sơ hộ chiếu.");
				}
			} else {
				prModel.setCode(Contants.ENUM_STATUS_CODE_HTTP_ERROR_INPUT);
				prModel.setMessage("Thieu du lieu dau vao. TransationId = "
						+ request.getTransactionId() + " - ppNo = "
						+ request.getPassportNo() + " - CancelType = "
						+ request.getCancelType());
			}

		} catch (Exception e) {
			e.printStackTrace();
			prModel.setCode(414);
			prModel.setMessage(Contants.MESSAGE_EXCEPTION);
			/*
			 * Lưu dữ liệu nếu có Exception
			 */
			String dataResponse = "";
			String dataRequest = "";
			String keyId = request.getTransactionId();
			try {
				dataResponse = new ObjectMapper().writeValueAsString(rep);
				dataRequest = new ObjectMapper().writeValueAsString(request);
			} catch (Exception e2) {
				logger.error(e2.getMessage());
			}
			this.saveException("UPF", "updatePrintFail", dataRequest, keyId,
					dataResponse, e);
		}

		rep.setProperty(prModel);
		return rep;
	}

	@POST
	@Produces("application/json")
	@Consumes("application/json")
	@Path("/updateSuccessPrinted")
	public ResponseBase<Boolean> updateSuccessPrinted(
			UpdateLDSPackageDto request) {
		ResponseBase<Boolean> rep = new ResponseBase<Boolean>();
		PropertyModel prModel = new PropertyModel();
		Date startTime = new Date();
		rep.setResponse(false);
		prModel.setCode(-1);
		String logData = "";
		String transactionStatus = "";
		String statusPerso = "";

		try {
			if (StringUtils.isNotEmpty(request.getTrans())
					&& StringUtils.isNotEmpty(request.getPassportNo())) {
				List<NicUploadJob> nicUploadJobs = uploadJobSerivce
						.findAllByTransactionId(request.getTrans());
				if (nicUploadJobs != null) {
					NicUploadJob nicUploadJob = nicUploadJobs.get(0);

					if ((StringUtils.isBlank(nicUploadJob
							.getPersoRegisterStatus())
							|| nicUploadJob.getPersoRegisterStatus().equals(
									"09") || nicUploadJob
							.getPersoRegisterStatus().equals("01"))
							&& StringUtils.isNotBlank(nicUploadJob
									.getTempPassportNo())
							&& nicUploadJob.getTempPassportNo().equals(
									request.getPassportNo())) {

						NicDocumentDataId id = new NicDocumentDataId();
						id.setPassportNo(request.getPassportNo());
						id.setTransactionId(request.getTrans());
						NicDocumentData nicdel = documentDataService
								.findById(id);
						// cập nhật hộ chiếu sau khi in.
						if (nicdel != null) {
							nicdel.setSigner(request.getSigner());
							nicdel.setPositionSigner(request
									.getPositionSigner());
							// Hoald thêm cập nhật trạng thái hộ chiếu.
							if (nicdel.getActiveFlag() == null)
								nicdel.setActiveFlag(Boolean.TRUE);
							if (nicdel.getIssuedFlag() == null)
								nicdel.setIssuedFlag(Boolean.TRUE);
							nicdel.setIssueBy(request.getPrintName());
							if (StringUtils.isNotBlank(request.getPrintDate())) {
								nicdel.setIssueDatetime(DateUtil.strToDate(
										request.getPrintDate(),
										DateUtil.FORMAT_YYYYMMDD));
							}
							nicdel.setStatus(NicDocumentData.DOCUMENT_DATA_STATUS_ISSUANCE);
							nicdel.setCreateBy(request.getPrintName());
							nicdel.setChipSerialNo(request.getChipSerial());
							nicdel.setSerialNo(request.getPhoiSerial());
							nicdel.setPrintName(request.getPrintName());
							nicdel.setPrinterSerial(request.getPrintSerial());
							if (StringUtils.isNotBlank(request.getPrintDate())) {
								Date date = DateUtil.strToDate(
										request.getPrintDate(),
										DateUtil.FORMAT_YYYYMMDD);
								nicdel.setPrintDate(date);
							}
							documentDataService.saveOrUpdateDocData(nicdel);
							
							List<EppListHandoverDetail> hanDetails = eppListHandoverDetailService.findAllByTranIdOrType(nicdel.getId().getTransactionId(), NicListHandover.TYPE_LIST_C);
							if (hanDetails != null && hanDetails.size() > 0) {
								for (EppListHandoverDetail hanDetail : hanDetails) {
									String issSiteCode = nicdel.getNicTransaction().getIssSiteCode();
									String regSiteCode = nicdel.getNicTransaction().getRegSiteCode();
									BaseModelSingle<Boolean> bAddQJR = this.addObjToQueueJob(
											hanDetail.getPackageId(), Contants.QUEUE_OBJ_TYPE_DC,
											regSiteCode, null);
									if (!bAddQJR.isError() || bAddQJR.getMessage() != null) {
										throw new Exception(bAddQJR.getMessage());
									}
									if (!issSiteCode.equals(regSiteCode)) {

										if (issSiteCode.equals(Contants.QUEUE_SITE_CODE_MB)
												|| issSiteCode.equals(Contants.QUEUE_SITE_CODE_MT)
												|| issSiteCode.equals(Contants.QUEUE_SITE_CODE_MN)) {
											BaseModelSingle<Boolean> bAddQJ = this
													.addObjToQueueJob(hanDetail.getPackageId(),
															Contants.QUEUE_OBJ_TYPE_DC, issSiteCode,
															null);
											if (!bAddQJ.isError() || bAddQJ.getMessage() != null) {
												throw new Exception(bAddQJ.getMessage());
											}
										}
									}
								}
							}
						}
						// uploadJobService.updateJobState(
						// nicUploadJob.getWorkflowJobId(), statusPerso,
						// PERSO_REGISTER);
						// uploadJobService.updateJobStatus(
						// nicUploadJob.getWorkflowJobId(),
						// transactionStatus);
						this.saveTransactionLog(request.getTrans(),
								PERSO_PRINTED, transactionStatus, startTime,
								new Date(), null, logData,
								nicUploadJob.getJobReprocessCount());

						rep.setResponse(true);
						prModel.setCode(99);
						prModel.setMessage("Trang thai Perso cap nhat: "
								+ transactionStatus);

						/* cập nhật trạng thái hồ sơ */
						// NicTransaction nicTran = nicTransactionService
						// .findTransactionById(request.getTrans())
						// .getModel();
						// nicTran.setTransactionStatus(Contants.TRANSACTION_STATUS_PERSO_PRINTED);
						// nicTransactionService.saveOrUpdateTransaction(nicTran);

						/* Lưu bảng thống kê truyền nhận */
						this.saveOrUpRptData(Contants.URL_UPDATE_STATUS_PERSO,
								1, null);
					} else {
						prModel.setCode(Contants.ENUM_STATUS_CODE_HTTP_ERROR_INPUT);
						prModel.setMessage("Trang thai ho chieu da duoc cap nhat. passportNo="
								+ request.getPassportNo());
					}
				} else {
					prModel.setCode(Contants.ENUM_STATUS_CODE_HTTP_NOT_FOUND);
					prModel.setMessage("Khong tim thay du lieu ban ghi");
				}
			} else {
				prModel.setCode(Contants.ENUM_STATUS_CODE_HTTP_ERROR_INPUT);
				prModel.setMessage("Thieu du lieu dau vao. TransationId="
						+ request.getTrans() + "- ppNo="
						+ request.getPassportNo() + "- status="
						+ request.getStatus());
			}

		} catch (Exception e) {
			e.printStackTrace();
			prModel.setCode(414);
			prModel.setMessage(Contants.MESSAGE_EXCEPTION);
			/*
			 * Lưu dữ liệu nếu có Exception
			 */
			String dataResponse = "";
			String dataRequest = "";
			String keyId = "USP_"
					+ new SimpleDateFormat("ddMMyyyy").format(new Date());
			try {
				dataResponse = new ObjectMapper().writeValueAsString(rep);
				dataRequest = new ObjectMapper().writeValueAsString(request);
			} catch (Exception e2) {
				logger.error(e2.getMessage());
			}
			this.saveException(Contants.TYPE_USP,
					Contants.URL_UPDATE_STATUS_PERSO, dataRequest, keyId,
					dataResponse, e);
		}

		rep.setProperty(prModel);
		return rep;
	}

	private PropertyModel checkValidateUpdatePackageRequest(
			UpdatePackageRequest listIssue) {
		if (StringUtils.isEmpty(listIssue.getPackageId())) {
			return new PropertyModel(
					Integer.parseInt(Contants.CODE_INPUT_FAILD), "PackageId"
							+ Contants.MESSAGE_INPUT_NULL);
		}
		if (StringUtils.isEmpty(listIssue.getFullName())) {
			return new PropertyModel(
					Integer.parseInt(Contants.CODE_INPUT_FAILD), "FullName"
							+ Contants.MESSAGE_INPUT_NULL);
		}
		if (StringUtils.isEmpty(listIssue.getUserName())) {
			return new PropertyModel(
					Integer.parseInt(Contants.CODE_INPUT_FAILD), "UserName"
							+ Contants.MESSAGE_INPUT_NULL);
		}
		if (StringUtils.isEmpty(listIssue.getCreatedDate())) {
			return new PropertyModel(
					Integer.parseInt(Contants.CODE_INPUT_FAILD), "CreatedDate"
							+ Contants.MESSAGE_INPUT_NULL);
		} else if (!checkTypeLong(listIssue.getCreatedDate())) {
			return new PropertyModel(
					Integer.parseInt(Contants.CODE_INPUT_FAILD), "CreatedDate"
							+ Contants.MESSAGE_INPUT_TYPE_ERROR);
		} else if (!checkLengthDate(listIssue.getCreatedDate())) {
			return new PropertyModel(
					Integer.parseInt(Contants.CODE_INPUT_FAILD), "CreatedDate"
							+ Contants.MESSAGE_INPUT_FORMAT_ERROR
							+ " - length != 8");
		} else if (!checkFormatDate(listIssue.getCreatedDate())) {
			return new PropertyModel(
					Integer.parseInt(Contants.CODE_INPUT_FAILD), "CreatedDate"
							+ Contants.MESSAGE_INPUT_FORMAT_ERROR
							+ " - format != yyyyMMdd");
		}
		if (listIssue.getTransactions() == null
				|| listIssue.getTransactions().size() == 0) {
			return new PropertyModel(
					Integer.parseInt(Contants.CODE_INPUT_FAILD), "Transactions"
							+ Contants.MESSAGE_INPUT_NULL);
		} else {
			for (TransactionPerso tp : listIssue.getTransactions()) {
				PropertyModel prModel = this.checkValidateTransactions(tp);
				if (prModel != null) {
					return prModel;
				}
			}
		}
		return null;
	}

	private PropertyModel checkValidateTransactions(TransactionPerso tran) {
		if (StringUtils.isEmpty(tran.getTransactionId())) {
			return new PropertyModel(
					Integer.parseInt(Contants.CODE_INPUT_FAILD),
					tran.getTransactionId() + " - TransactionId"
							+ Contants.MESSAGE_INPUT_NULL);
		}
		if (StringUtils.isEmpty(tran.getPassportNo())) {
			return new PropertyModel(
					Integer.parseInt(Contants.CODE_INPUT_FAILD),
					tran.getTransactionId() + " - PassportNo"
							+ Contants.MESSAGE_INPUT_NULL);
		}
		// if(StringUtils.isEmpty(tran.getSerialPhoi())){
		// return new PropertyModel(Integer.parseInt(Contants.CODE_INPUT_FAILD),
		// tran.getTransactionId() + " - SerialPhoi" +
		// Contants.MESSAGE_INPUT_NULL);
		// }
		if (StringUtils.isEmpty(tran.getIcaoLine1())) {
			return new PropertyModel(
					Integer.parseInt(Contants.CODE_INPUT_FAILD),
					tran.getTransactionId() + " - IcaoLine1"
							+ Contants.MESSAGE_INPUT_NULL);
		}
		if (StringUtils.isEmpty(tran.getIcaoLine2())) {
			return new PropertyModel(
					Integer.parseInt(Contants.CODE_INPUT_FAILD),
					tran.getTransactionId() + " - IcaoLine2"
							+ Contants.MESSAGE_INPUT_NULL);
		}
		if (StringUtils.isEmpty(tran.getSigner())) {
			return new PropertyModel(
					Integer.parseInt(Contants.CODE_INPUT_FAILD),
					tran.getTransactionId() + " - Signer"
							+ Contants.MESSAGE_INPUT_NULL);
		}
		if (StringUtils.isEmpty(tran.getPositionSigner())) {
			return new PropertyModel(
					Integer.parseInt(Contants.CODE_INPUT_FAILD),
					tran.getTransactionId() + " - PositionSigner"
							+ Contants.MESSAGE_INPUT_NULL);
		}
		if (StringUtils.isEmpty(tran.getPrintSite())) {
			return new PropertyModel(
					Integer.parseInt(Contants.CODE_INPUT_FAILD),
					tran.getTransactionId() + " - PrintSite"
							+ Contants.MESSAGE_INPUT_NULL);
		}
		if (StringUtils.isEmpty(tran.getDateOfIssue())) {
			return new PropertyModel(
					Integer.parseInt(Contants.CODE_INPUT_FAILD),
					tran.getTransactionId() + " - DateOfIssue"
							+ Contants.MESSAGE_INPUT_NULL);
		} else if (!checkTypeLong(tran.getDateOfIssue())) {
			return new PropertyModel(
					Integer.parseInt(Contants.CODE_INPUT_FAILD),
					tran.getTransactionId() + " - DateOfIssue"
							+ Contants.MESSAGE_INPUT_TYPE_ERROR);
		} else if (!checkLengthDate(tran.getDateOfIssue())) {
			return new PropertyModel(
					Integer.parseInt(Contants.CODE_INPUT_FAILD),
					tran.getTransactionId() + " - DateOfIssue"
							+ Contants.MESSAGE_INPUT_FORMAT_ERROR
							+ " - length != 8");
		} else if (!checkFormatDate(tran.getDateOfIssue())) {
			return new PropertyModel(
					Integer.parseInt(Contants.CODE_INPUT_FAILD),
					tran.getTransactionId() + " - DateOfIssue"
							+ Contants.MESSAGE_INPUT_FORMAT_ERROR
							+ " - format != yyyyMMdd");
		}
		if (StringUtils.isEmpty(tran.getDateOfExpire())) {
			return new PropertyModel(
					Integer.parseInt(Contants.CODE_INPUT_FAILD),
					tran.getTransactionId() + " - DateOfExpire"
							+ Contants.MESSAGE_INPUT_NULL);
		} else if (!checkTypeLong(tran.getDateOfExpire())) {
			return new PropertyModel(
					Integer.parseInt(Contants.CODE_INPUT_FAILD),
					tran.getTransactionId() + " - DateOfExpire"
							+ Contants.MESSAGE_INPUT_TYPE_ERROR);
		} else if (!checkLengthDate(tran.getDateOfExpire())) {
			return new PropertyModel(
					Integer.parseInt(Contants.CODE_INPUT_FAILD),
					tran.getTransactionId() + " - DateOfExpire"
							+ Contants.MESSAGE_INPUT_FORMAT_ERROR
							+ " - length != 8");
		} else if (!checkFormatDate(tran.getDateOfExpire())) {
			return new PropertyModel(
					Integer.parseInt(Contants.CODE_INPUT_FAILD),
					tran.getTransactionId() + " - DateOfExpire"
							+ Contants.MESSAGE_INPUT_FORMAT_ERROR
							+ " - format != yyyyMMdd");
		}
		return null;
	}

	private Boolean saveDocumentInfoNew(String transactionId, String doi,
			String doe, String ppNo) {
		try {
			String documentNumber = "";
			String printedSite = "HANOI";
			// String doi = "";
			// String doe = "";
			String dateFormat = com.nec.asia.nic.comp.trans.service.PersoService.DATE_FORMAT_FOR_PERSO;
			documentNumber = ppNo;
			/*
			 * final String DOC_NUM_START_TAG =
			 * "<DataField Name=\"DocumentNumber\">"; final String
			 * DOC_NUM_END_TAG = "</DataField>"; final String VALUE_START_TAG =
			 * "<Value InputFormat=\"Text\">"; final String VALUE_END_TAG =
			 * "</Value>";
			 * 
			 * final String DOI_START_TAG = "<DataField Name=\"DateOfIssue\">";
			 * //YYYYMMDD final String DOE_START_TAG =
			 * "<DataField Name=\"DateOfExpiry\">"; //YYYYMMDD
			 * 
			 * int docNumTagStart = StringUtils.indexOf(persoXml,
			 * DOC_NUM_START_TAG); if (docNumTagStart > 0) { int valueTagStart =
			 * StringUtils.indexOf(persoXml, VALUE_START_TAG , docNumTagStart);
			 * int valueTagEnd = StringUtils.indexOf(persoXml, VALUE_END_TAG ,
			 * docNumTagStart);
			 * 
			 * documentNumber = StringUtils.substring(persoXml,
			 * valueTagStart+VALUE_START_TAG.length(), valueTagEnd); }
			 * 
			 * int doiTagStart = StringUtils.indexOf(persoXml, DOI_START_TAG);
			 * if (doiTagStart > 0) { int valueTagStart =
			 * StringUtils.indexOf(persoXml, VALUE_START_TAG , doiTagStart); int
			 * valueTagEnd = StringUtils.indexOf(persoXml, VALUE_END_TAG ,
			 * doiTagStart);
			 * 
			 * doi = StringUtils.substring(persoXml,
			 * valueTagStart+VALUE_START_TAG.length(), valueTagEnd); }
			 * 
			 * int doeTagStart = StringUtils.indexOf(persoXml, DOE_START_TAG);
			 * if (doeTagStart > 0) { int valueTagStart =
			 * StringUtils.indexOf(persoXml, VALUE_START_TAG , doeTagStart); int
			 * valueTagEnd = StringUtils.indexOf(persoXml, VALUE_END_TAG ,
			 * doeTagStart);
			 * 
			 * doe = StringUtils.substring(persoXml,
			 * valueTagStart+VALUE_START_TAG.length(), valueTagEnd); }
			 */

			// logger.info("[saveDocumentInfo]transactionId:{} documentNumber: {}, doi: {}, doe: {}",
			// new Object[] { transactionId, documentNumber, doi, doe} );
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
			// documentData.setPrintingSite(printedSite);
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

			documentDataDao.saveOrUpdate(documentData);
			return true;
		} catch (Exception e) {
		}
		return false;
	}

	private BaseModelSingle<Boolean> addObjToQueueJob(String transactionId,
			String ObjType, String receiver, String status) {
		try {
			// BaseModelSingle<SyncQueueJob> exist =
			// queueJobService.findQueueByObjectId(transactionId, receiver,
			// status, ObjType);
			// if(exist.getMessage() == null && exist.isError() &&
			// exist.getModel() == null){
			SyncQueueJob queue = new SyncQueueJob();
			queue.setCreatedTs(Calendar.getInstance().getTime());
			queue.setObjectId(transactionId);
			queue.setObjectType(ObjType);
			queue.setReceiver(receiver);
			queue.setTranStatus(status);
			queue.setStatus(Contants.CODE_STATUS_QUEUE_PENDING);
			queue.setSyncRetry(1);
			return queueJobService.saveOrUpdateQueue(queue);
			// }
			// return new BaseModelSingle<Boolean>(false, true, null);
		} catch (Exception e) {
			return new BaseModelSingle<Boolean>(false, false, e.getMessage()
					+ " - addObjToQueueJob - " + transactionId + " - thất bại.");
		}
	}

	// check type int
	private boolean checkTypeLong(String input) {
		try {
			Long.parseLong(input);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	// check length DATE yyyyMMdd
	private boolean checkLengthDate(String date) {
		if (date.length() == 8) {
			return true;
		} else {
			return false;
		}
	}

	// check length DATETIME yyyyMMddHHmmss
	private boolean checkLengthDateTime(String date) {
		if (date.length() == 14) {
			return true;
		} else {
			return false;
		}
	}

	// check format yyyyMMdd
	private SimpleDateFormat simpleDateFormat;

	private boolean checkFormatDate(String date) {
		simpleDateFormat = new SimpleDateFormat(
				com.nec.asia.nic.utils.HelperClass.STR_FORMAT_DATE_yyyyMMdd);
		try {
			Date d = simpleDateFormat.parse(date.trim());
			if (simpleDateFormat.format(d).equals(date.trim())) {
				return true;
			}
		} catch (java.text.ParseException e) {
			return false;
		}
		return false;
	}

	// check format yyyyMMddHHmmss
	private boolean checkFormatDatetime(String datetime) {
		simpleDateFormat = new SimpleDateFormat(
				com.nec.asia.nic.utils.HelperClass.STR_FORMAT_DATE_yyyyMMdd);
		try {
			Date d = simpleDateFormat.parse(datetime.trim());
			if (simpleDateFormat.format(d).equals(datetime.trim())) {
				return true;
			}
		} catch (java.text.ParseException e) {
			return false;
		}
		return false;
	}

	// check exist and save Exception - single
	private PropertyModel checkExistAndSaveException(BaseModelSingle base,
			EppWsLog eWL, PropertyModel pModel) {
		pModel.setCode(Integer.parseInt(Contants.CODE_THROW_EXCEPTION));
		pModel.setMessage(Contants.MESSAGE_EXCEPTION);
		eWL.setDataResponse(pModel.toString());
		eWL.setMessageErrorLog(base.getMessage());
		eWL.setCreatedDate(new Date());
		BaseModelSingle<Boolean> baseCheck = eppWsLogService
				.inserDataTable(eWL);
		if (!baseCheck.getModel() || baseCheck.getMessage() != null) {
			logger.error(baseCheck.getMessage() + " - save EppWsLog fail.");
		}
		return pModel;
	}

	// check exist and save Exception - list
	private PropertyModel checkExistAndSaveExceptions(BaseModelList base,
			EppWsLog eWL, PropertyModel pModel) {
		pModel.setCode(Integer.parseInt(Contants.CODE_THROW_EXCEPTION));
		pModel.setMessage(Contants.MESSAGE_EXCEPTION);
		eWL.setDataResponse(pModel.toString());
		eWL.setMessageErrorLog(base.getMessage());
		eWL.setCreatedDate(new Date());
		BaseModelSingle<Boolean> baseCheck = eppWsLogService
				.inserDataTable(eWL);
		if (!baseCheck.getModel() || baseCheck.getMessage() != null) {
			logger.error(baseCheck.getMessage() + " - save EppWsLog fail.");
		}
		return pModel;
	}

	// save or update rptData
	private void saveOrUpRptData(String type, int count, String siteCode) {
		try {
			RptStatisticsTransmitData rptData = rptService
					.findSingerByConditions(type, siteCode, DateUtil.strToDate(
							com.nec.asia.nic.utils.HelperClass
									.convertDateType3(new Date()),
							DateUtil.FORMAT_YYYYMMDD));
			if (rptData != null) {
				rptData.setTotal(rptData.getTotal() + count);
			} else {
				rptData = new RptStatisticsTransmitData();
				rptData.setRptDate(DateUtil.strToDate(
						com.nec.asia.nic.utils.HelperClass
								.convertDateType3(new Date()),
						DateUtil.FORMAT_YYYYMMDD));
				rptData.setTotal(count);
				rptData.setType(type);
				rptData.setSiteCode(siteCode);
			}
			rptData.setUpdateDatetime(new Date());
			rptService.saveOrUpdateData(rptData);
		} catch (Exception e) {
			throw e;
		}
	}

	/*
	 * Lưu Exception
	 */
	private void saveException(String type, String urlRequest,
			String dataRequest, String keyId, String dataResponse, Exception e) {
		EppWsLog eppWsLog = new EppWsLog();
		eppWsLog.setType(type);
		eppWsLog.setUrlRequest(urlRequest);
		eppWsLog.setDataRequest(dataRequest);
		eppWsLog.setKeyId(keyId);
		eppWsLog.setDataResponse(dataResponse);
		eppWsLog.setMessageErrorLog(CreateMessageException
				.createMessageException(e) + " - " + urlRequest + " - fail.");
		eppWsLog.setCreatedDate(new Date());
		BaseModelSingle<Boolean> baseCheck = eppWsLogService
				.inserDataTable(eppWsLog);
		if (!baseCheck.getModel() || baseCheck.getMessage() != null) {
			logger.error(e.getMessage() + " - save EppWsLog fail.");
		}
	}

	/*
	 * Lưu hàng đợi gửi về cửa khẩu.
	 */
	private void addObjToQueueXncJob(String ObjType, String site,
			String passportNo) {
		try {
			SynQueueJobXnc queue = new SynQueueJobXnc();
			queue.setCreatedTs(Calendar.getInstance().getTime());
			queue.setObjectId(passportNo);
			queue.setObjectType(ObjType);
			queue.setSiteCode(site);
			queue.setStatus(Contants.CODE_STATUS_QUEUE_PENDING);
			queue.setSyncRetry(1);
			queueJobXncService.saveOrUpdateQueueXnc(queue);
		} catch (Exception e) {

		}
	}
	private String getSiteName(String placeOfIssueCode) {
		try {
			SiteRepository site = siteService.getSiteRepoById(placeOfIssueCode);
			String siteId = "DSQ_" + placeOfIssueCode;
			if (site == null) {
				site = siteService.getSiteRepoById(siteId);
			}
			if (site != null) {
				placeOfIssueCode = site.getSiteName();
			}
		} catch (Exception e) {
		}
		return placeOfIssueCode;
	}
}