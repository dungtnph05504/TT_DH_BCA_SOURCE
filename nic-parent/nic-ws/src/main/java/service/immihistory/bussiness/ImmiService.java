package service.immihistory.bussiness;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.jws.WebService;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.ext.Provider;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nec.asia.nic.comp.immihistory.domain.ImmiHistory;
import com.nec.asia.nic.comp.immihistory.domain.ImmiHistoryChilden;
import com.nec.asia.nic.comp.immihistory.domain.ImmiHistoryImages;
import com.nec.asia.nic.comp.immihistory.model.Immihistory;
import com.nec.asia.nic.comp.immihistory.model.ImmihistoryChildren;
import com.nec.asia.nic.comp.immihistory.model.ImmihistoryImage;
import com.nec.asia.nic.comp.trans.dao.NicImmiHistoryChildenDao;
import com.nec.asia.nic.comp.trans.dao.NicImmiHistoryDao;
import com.nec.asia.nic.comp.trans.dao.NicImmiHistoryImagesDao;
import com.nec.asia.nic.comp.trans.domain.BaseModelSingle;
import com.nec.asia.nic.comp.trans.domain.BorderGate;
import com.nec.asia.nic.comp.trans.domain.ConfigurationApi;
import com.nec.asia.nic.comp.trans.domain.EppAirline;
import com.nec.asia.nic.comp.trans.domain.EppAirport;
import com.nec.asia.nic.comp.trans.domain.EppFlight;
import com.nec.asia.nic.comp.trans.domain.EppFlightRouter;
import com.nec.asia.nic.comp.trans.domain.EppFreeVisa;
import com.nec.asia.nic.comp.trans.domain.EppPurpose;
import com.nec.asia.nic.comp.trans.domain.NicDocumentData;
import com.nec.asia.nic.comp.trans.domain.NicRegistrationData;
import com.nec.asia.nic.comp.trans.domain.NicTransaction;
import com.nec.asia.nic.comp.trans.domain.NicTransactionAttachment;
import com.nec.asia.nic.comp.trans.domain.NicUploadJob;
import com.nec.asia.nic.comp.trans.domain.RptStatisticsTransmitData;
import com.nec.asia.nic.comp.trans.domain.SynQueueJobXnc;
import com.nec.asia.nic.comp.trans.service.AirlineService;
import com.nec.asia.nic.comp.trans.service.AirportService;
import com.nec.asia.nic.comp.trans.service.BorderGateService;
import com.nec.asia.nic.comp.trans.service.ConfigurationApiService;
import com.nec.asia.nic.comp.trans.service.DocumentDataService;
import com.nec.asia.nic.comp.trans.service.EppPersonService;
import com.nec.asia.nic.comp.trans.service.FlightRouterService;
import com.nec.asia.nic.comp.trans.service.FlightService;
import com.nec.asia.nic.comp.trans.service.FreeVisaService;
import com.nec.asia.nic.comp.trans.service.NicImmiHistoryService;
import com.nec.asia.nic.comp.trans.service.NicRegistrationDataService;
import com.nec.asia.nic.comp.trans.service.NicTransactionAttachmentService;
import com.nec.asia.nic.comp.trans.service.NicTransactionService;
import com.nec.asia.nic.comp.trans.service.NicUploadJobService;
import com.nec.asia.nic.comp.trans.service.PurposeService;
import com.nec.asia.nic.comp.trans.service.RptStatisticsTransmitDataService;
import com.nec.asia.nic.comp.trans.service.SynQueueJobXncService;
import com.nec.asia.nic.comp.ws.log.domain.CreateMessageException;
import com.nec.asia.nic.comp.ws.log.domain.EppWsLog;
import com.nec.asia.nic.comp.ws.log.service.EppWsLogService;
import com.nec.asia.nic.framework.admin.code.domain.CodeValues;
import com.nec.asia.nic.framework.admin.code.domain.Parameters;
import com.nec.asia.nic.framework.admin.code.service.CodesService;
import com.nec.asia.nic.framework.admin.code.service.ParametersService;
import com.nec.asia.nic.framework.admin.site.domain.SiteRepository;
import com.nec.asia.nic.framework.admin.site.service.SiteService;
import com.nec.asia.nic.utils.DateUtil;
import com.nec.asia.nic.utils.HelperClass;

import service.immihistory.model.AdminStatus;
import service.immihistory.model.AdminsStatus;
import service.immihistory.model.BaseListRequest;
import service.immihistory.model.BaseResponse;
import service.immihistory.model.Immihistorys;
import service.immihistory.model.ItemResponse;
import service.immihistory.model.SupervisorStatus;
import service.immihistory.model.SupervisorsStatus;
import service.perso.model.sync.PassportA72;
import service.perso.model.sync.ResponseFromA72;
import service.perso.util.Contants;
import service.transaction.model.QueueInfo;
import service.transaction.model.ResponseBase;

@Path("/syncImmi/")
@WebService
@Provider
public class ImmiService {

	public final Logger logger = LoggerFactory.getLogger(this.getClass());

	public static final String PARA_NAME_MAX_COUNT_CALL_QUEUE_XNC = "MAX_COUNT_CALL_QUEUE_XNC";
	public static final String OBJ_TYPE_QUEUE_XNC_GT = "GT";
	public static final String OBJ_TYPE_QUEUE_XNC_PP = "PP";
	public static final String OBJ_TYPE_QUEUE_XNC_UP = "UP";
	public static final String OBJ_TYPE_QUEUE_XNC_AIRLINE = "AL";
	public static final String OBJ_TYPE_QUEUE_XNC_AIRPORT = "AP";
	public static final String OBJ_TYPE_QUEUE_XNC_FLIGHT = "FL";
	public static final String OBJ_TYPE_QUEUE_XNC_FLIGHT_ROUTER = "FR";
	public static final String OBJ_TYPE_QUEUE_XNC_FREE_VISA = "FV";
	public static final String OBJ_TYPE_QUEUE_XNC_PURPOSE = "PE";

	@Autowired
	private NicImmiHistoryService immihistoryService;

	@Autowired
	private NicImmiHistoryImagesDao immihistoryImgDao;

	@Autowired
	private NicImmiHistoryChildenDao immihistoryChilDao;

	@Autowired
	private NicImmiHistoryDao immihistoryDao;

	@Autowired
	private ParametersService parametersService;

	@Autowired
	private SynQueueJobXncService queueJobXncService;

	@Autowired
	private BorderGateService borderGateService;

	@Autowired
	private DocumentDataService documentDataService;

	@Autowired
	private NicRegistrationDataService nicRegistrationDataService;

	@Autowired
	private NicTransactionService nicTransactionService;

	@Autowired
	private NicUploadJobService uploadJobSerivce;

	@Autowired
	private CodesService codesService;

	@Autowired
	private NicTransactionAttachmentService nicTransactionAttachmentService;

	@Autowired
	private ConfigurationApiService configurationApiService;

	@Autowired
	private EppPersonService eppPersonService;

	@Autowired
	private AirlineService airlineService;

	@Autowired
	private AirportService airportService;

	@Autowired
	private FlightService flightService;

	@Autowired
	private FlightRouterService flightRouterService;

	@Autowired
	private FreeVisaService freeVisaService;

	@Autowired
	private PurposeService purposeService;

	@Autowired
	private RptStatisticsTransmitDataService rptService;
	
	@Autowired
	private EppWsLogService eppWsLogService;
	
	@Autowired
	private SiteService siteService;

	@POST
	@Produces("application/json")
	@Consumes("application/json")
	@Path("/uploadImmihistory")
	public BaseResponse<List<ItemResponse>> uploadImmihistory(
			Immihistorys immihistorys) {
		BaseResponse<List<ItemResponse>> response = new BaseResponse<List<ItemResponse>>();
		response.setCode(Contants.RESPONSE_CODE_FAIL_API);
		response.setMessage(Contants.RESPONSE_MESSAGE_FAIL_API);
		// String nameApi = "uploadImmihistory";
		// String gateCode = (immihistorys != null && immihistorys.getImmis() !=
		// null && immihistorys.getImmis().size() > 0) ?
		// immihistorys.getImmis().get(0).getBorderGateCode() : "";
		// if(!this.checkConnectApi(nameApi, gateCode)){
		// response.setMessage(Contants.RESPONSE_MESSAGE_CONNECT_API);
		// return response;
		// }
		List<ItemResponse> itemList = new ArrayList<ItemResponse>();
		// logger.info("start save data immihistory to TTDH, start time: " +
		// HelperClass.convertDateToString1(new Date()));
		try {
			if (immihistorys != null && immihistorys.getImmis() != null) {
				for (Immihistory immi : immihistorys.getImmis()) {
					ItemResponse item = new ItemResponse();
					try {
						ImmiHistory immiHistory = new ImmiHistory(immi);
						immihistoryDao.save(immiHistory);
						Long idImmi = immiHistory.getId();
						if (immi.getChildrens() != null) {
							for (ImmihistoryChildren chil : immi.getChildrens()) {
								ImmiHistoryChilden immiChil = new ImmiHistoryChilden(
										chil);
								immiChil.setImmihistoryId(idImmi);
								immihistoryChilDao.save(immiChil);
							}
						}
						if (immi.getImages() != null) {
							for (ImmihistoryImage img : immi.getImages()) {
								ImmiHistoryImages immiImg = new ImmiHistoryImages(
										img);
								immiImg.setImmihistoryId(idImmi);
								immihistoryImgDao.save(immiImg);
							}
						}
						item.setStage(1);
						item.setTransactionId(immi.getTransactionId());
						// Đưa vào hàng đợi đồng bộ đi các cửa khẩu khác
						List<BorderGate> gateList = borderGateService
								.findBorderGateBySync(Contants.FLAG_Y);
						if (gateList != null) {
							for (BorderGate gate : gateList) {
								if (!immi.getBorderGateCode().equals(
										gate.getCode())) {
									Boolean check = this.addObjToQueueXncJob(
											idImmi, OBJ_TYPE_QUEUE_XNC_GT,
											gate.getCode(),
											immi.getTransactionId());
									if (check) {
										/* Lưu bảng thống kê truyền nhận */
										this.saveOrUpRptData(
												Contants.URL_UPLOAD_IMMIHISTORY,
												1, gate.getCode());
									}

								}
							}
						}
						// logger.info("success save immihistory to TTDH, transactionId: "
						// + immi.getTransactionId());
					} catch (Throwable e) {
						logger.error("error save immihistory to TTDH, transactionId: "
								+ immi.getTransactionId()
								+ ", mess: "
								+ e.getMessage());
						item.setStage(0);
						item.setTransactionId(immi.getTransactionId());
					}
					itemList.add(item);
				}
			}
			response.setCode(Contants.RESPONSE_CODE_SUCCESS_API);
			response.setMessage(Contants.RESPONSE_MESSAGE_SUCCESS_API);

		} catch (Exception e) {
			e.printStackTrace();
			response.setCode(Contants.CODE_THROW_EXCEPTION);
			response.setMessage(Contants.MESSAGE_EXCEPTION);
			/*
			 * Lưu dữ liệu nếu có Exception
			 */
			String dataResponse = "";
			String dataRequest = "";
			String keyId = "UIH_" + new SimpleDateFormat("ddMMyyyy").format(new Date());
			try {
				dataResponse = new ObjectMapper()
				.writeValueAsString(response);
				dataRequest = new ObjectMapper()
				.writeValueAsString(immihistorys);
			} catch (Exception e2) {
				logger.error(e2.getMessage());
			}
			this.saveException(Contants.TYPE_UIH, Contants.URL_UPLOAD_IMMIHISTORY,
					dataRequest, keyId, dataResponse, e);
		}
		response.setData(itemList);
		return response;
	}

	@GET
	@Produces("application/json")
	@Consumes("application/json")
	@Path("/downloadImmihistory/{gate}")
	public BaseResponse<List<Immihistory>> downloadImmihistory(
			@PathParam("gate") String gate) {
		BaseResponse<List<Immihistory>> response = new BaseResponse<List<Immihistory>>();
		response.setCode(Contants.RESPONSE_CODE_FAIL_API);
		response.setMessage(Contants.RESPONSE_MESSAGE_FAIL_API);
		// String nameApi = "downloadImmihistory";
		// if(!this.checkConnectApi(nameApi, gate)){
		// response.setMessage(Contants.RESPONSE_MESSAGE_CONNECT_API);
		// return response;
		// }
		List<Immihistory> immiList = new ArrayList<Immihistory>();
		// logger.info("start download data immihistory, gate: " + gate +
		// ", start time: " + HelperClass.convertDateToString1(new Date()));
		try {
			int maxCall = 10;
			Parameters pr = parametersService.getParamDetails(
					Contants.CREATE_BY_SYSTEM,
					PARA_NAME_MAX_COUNT_CALL_QUEUE_XNC);
			if (pr != null) {
				maxCall = Integer.parseInt(pr.getParaShortValue());
			}
			List<SynQueueJobXnc> queueList = queueJobXncService
					.findQueueXncBySite(gate,
							Contants.CODE_STATUS_QUEUE_PENDING, new String[] {
									OBJ_TYPE_QUEUE_XNC_GT,
									OBJ_TYPE_QUEUE_XNC_UP }, maxCall);
			if (queueList != null) {
				for (SynQueueJobXnc queue : queueList) {
					ImmiHistory immi = immihistoryService.findById(queue
							.getIdImmi());
					if (immi != null) {
						Immihistory immihistory = new Immihistory(immi);
						List<ImmihistoryChildren> chilList = new ArrayList<ImmihistoryChildren>();
						List<ImmihistoryImage> imgList = new ArrayList<ImmihistoryImage>();
						/* Update không cần dữ liệu ảnh, trẻ em */
						if (queue.getObjectType().equals(OBJ_TYPE_QUEUE_XNC_GT)) {
							List<ImmiHistoryChilden> immiChilList = immihistoryChilDao
									.findByImmiId(queue.getIdImmi());
							if (immiChilList != null) {
								for (ImmiHistoryChilden immiChil : immiChilList) {
									ImmihistoryChildren chil = new ImmihistoryChildren(
											immiChil);
									chilList.add(chil);
								}
							}
							List<ImmiHistoryImages> immiImgList = immihistoryImgDao
									.findByImmiId(queue.getIdImmi());
							if (immiImgList != null) {
								for (ImmiHistoryImages img : immiImgList) {
									ImmihistoryImage image = new ImmihistoryImage(
											img);
									imgList.add(image);
								}
							}
						}

						immihistory.setChildrens(chilList);
						immihistory.setImages(imgList);
						immihistory.setIdQueue(queue.getId());
						immihistory.setSyncType(queue.getObjectType());
						immiList.add(immihistory);
						queueJobXncService
								.updateStatusQueueXncJob(queue.getId(),
										Contants.CODE_STATUS_QUEUE_DOING);

						/* Lưu bảng thống kê truyền nhận */
						if (immiList.size() > 0) {
							this.saveOrUpRptData(
									Contants.URL_DOWNLOAD_IMMIHISTORY,
									immiList.size(), gate);
						}
					}
				}
			}
			response.setData(immiList);
			response.setCode(Contants.RESPONSE_CODE_SUCCESS_API);
			response.setMessage(Contants.RESPONSE_MESSAGE_SUCCESS_API);

			// logger.info("success download immihistory by gate: " + gate);
		} catch (Exception e) {
			e.printStackTrace();
			response.setCode(Contants.CODE_THROW_EXCEPTION);
			response.setMessage(Contants.MESSAGE_EXCEPTION);
			/*
			 * Lưu dữ liệu nếu có Exception
			 */
			String dataResponse = "";
			String keyId = "DIH_" + new SimpleDateFormat("ddMMyyyy").format(new Date());
			try {
				dataResponse = new ObjectMapper()
				.writeValueAsString(response);
			} catch (Exception e2) {
				logger.error(e2.getMessage());
			}
			this.saveException(Contants.TYPE_DIH, Contants.URL_DOWNLOAD_IMMIHISTORY,
					gate, keyId, dataResponse, e);
		}
		return response;
	}

	@POST
	@Path("/updateStatusQueue")
	@Produces("application/json")
	@Consumes("application/json")
	public ResponseBase updateStatusQueue(List<QueueInfo> infos) {
		ResponseBase rep = new ResponseBase();
		rep.setCode(Contants.RESPONSE_CODE_FAIL_API);
		rep.setMessage(Contants.RESPONSE_MESSAGE_FAIL_API);
		try {
			for (QueueInfo queueInfo : infos) {
				queueJobXncService.updateQueueXncJob(queueInfo.getId(),
						queueInfo.getStatus());
			}
			rep.setCode(Contants.RESPONSE_CODE_SUCCESS_API);
			rep.setMessage(Contants.RESPONSE_MESSAGE_SUCCESS_API);

			/* Lưu bảng thống kê truyền nhận */
			this.saveOrUpRptData(Contants.URL_UPDATE_STATUS_QUEUE_IMMI, 1, null);

			// logger.info("success update status queue to TTDH");
		} catch (Exception e) {
			logger.error("error update status queue to TTDH: " + e.getMessage());
			e.printStackTrace();
		}
		return rep;
	}

	@POST
	@Path("/updateSupervisorStatus")
	@Produces("application/json")
	@Consumes("application/json")
	public BaseResponse<List<ItemResponse>> updateSupervisorStatus(
			SupervisorsStatus supervisors) {
		BaseResponse<List<ItemResponse>> response = new BaseResponse<List<ItemResponse>>();
		response.setCode(Contants.RESPONSE_CODE_FAIL_API);
		response.setMessage(Contants.RESPONSE_MESSAGE_FAIL_API);
		List<ItemResponse> itemList = new ArrayList<ItemResponse>();
		try {
			if (supervisors != null) {
				if (supervisors.getStatus() != null) {
					// logger.info("start update supervisor status, size: " +
					// supervisors.getStatus().size() + ", start time: " +
					// HelperClass.convertDateToString1(new Date()));
					for (SupervisorStatus status : supervisors.getStatus()) {
						ItemResponse item = new ItemResponse();
						Boolean blUpdate = immihistoryService.updateSupervisor(
								status.getTransactionId(),
								status.getSupervisorFullname(),
								status.getNote(), status.getBlacklistId(),
								status.getIsPassed());
						if (blUpdate) {
							item.setStage(1);
							// logger.info("success update supervisor status by transactionId: "
							// + status.getTransactionId());
						} else {
							item.setStage(0);
							logger.error("error update supervisor status by transactionId: "
									+ status.getTransactionId());
						}
						item.setTransactionId(status.getTransactionId());
						itemList.add(item);
					}
				}
			}
			response.setCode(Contants.RESPONSE_CODE_SUCCESS_API);
			response.setMessage(Contants.RESPONSE_MESSAGE_SUCCESS_API);
			response.setData(itemList);

			/* Lưu bảng thống kê truyền nhận */
			if (itemList.size() > 0) {
				this.saveOrUpRptData(Contants.URL_UPDATE_SUPERVISOR_STATUS,
						itemList.size(), null);
			}

		} catch (Exception e) {
			e.printStackTrace();
			response.setCode(Contants.CODE_THROW_EXCEPTION);
			response.setMessage(Contants.MESSAGE_EXCEPTION);
			/*
			 * Lưu dữ liệu nếu có Exception
			 */
			String dataResponse = "";
			String dataRequest = "";
			String keyId = "USS_" + new SimpleDateFormat("ddMMyyyy").format(new Date());
			try {
				dataResponse = new ObjectMapper()
				.writeValueAsString(response);
				dataRequest = new ObjectMapper()
				.writeValueAsString(supervisors);
			} catch (Exception e2) {
				logger.error(e2.getMessage());
			}
			this.saveException(Contants.TYPE_USS, Contants.URL_UPDATE_SUPERVISOR_STATUS,
					dataRequest, keyId, dataResponse, e);
		}
		return response;
	}

	@POST
	@Produces("application/json")
	@Consumes("application/json")
	@Path("/updateAdminStatus")
	public BaseResponse<List<ItemResponse>> updateAdminStatus(
			AdminsStatus admins) {
		BaseResponse<List<ItemResponse>> response = new BaseResponse<List<ItemResponse>>();
		response.setCode(Contants.RESPONSE_CODE_FAIL_API);
		response.setMessage(Contants.RESPONSE_MESSAGE_FAIL_API);
		List<ItemResponse> itemList = new ArrayList<ItemResponse>();
		try {
			if (admins != null) {
				if (admins.getStatus() != null) {
					// logger.info("start update admin status, size: " +
					// admins.getStatus().size() + ", start time: " +
					// HelperClass.convertDateToString1(new Date()));
					for (AdminStatus status : admins.getStatus()) {
						ItemResponse item = new ItemResponse();
						Boolean blUpdate = immihistoryService
								.updateAdminStatus(status.getTransactionId(),
										status.getAdminFullname(),
										status.getNote(), status.getIsPassed());
						if (blUpdate) {
							item.setStage(1);
							// logger.info("success update admin status by transactionId: "
							// + status.getTransactionId());
						} else {
							item.setStage(0);
							logger.error("error update admin status by transactionId: "
									+ status.getTransactionId());
						}
						item.setTransactionId(status.getTransactionId());
						itemList.add(item);
					}
				}
			}
			response.setCode(Contants.RESPONSE_CODE_SUCCESS_API);
			response.setMessage(Contants.RESPONSE_MESSAGE_SUCCESS_API);
			response.setData(itemList);

			/* Lưu bảng thống kê truyền nhận */
			if (itemList.size() > 0) {
				this.saveOrUpRptData(Contants.URL_UPDATE_ADMIN_STATUS,
						itemList.size(), null);
			}

		} catch (Exception e) {
			e.printStackTrace();
			response.setCode(Contants.CODE_THROW_EXCEPTION);
			response.setMessage(Contants.MESSAGE_EXCEPTION);
			/*
			 * Lưu dữ liệu nếu có Exception
			 */
			String dataResponse = "";
			String dataRequest = "";
			String keyId = "UAS_" + new SimpleDateFormat("ddMMyyyy").format(new Date());
			try {
				dataResponse = new ObjectMapper()
				.writeValueAsString(response);
				dataRequest = new ObjectMapper()
				.writeValueAsString(admins);
			} catch (Exception e2) {
				logger.error(e2.getMessage());
			}
			this.saveException(Contants.TYPE_UAS, Contants.URL_UPDATE_ADMIN_STATUS,
					dataRequest, keyId, dataResponse, e);
		}
		return response;
	}

	@POST
	@Produces("application/json")
	@Consumes("application/json")
	@Path("/updateImmihistory")
	public BaseResponse<List<ItemResponse>> updateImmihistory(
			Immihistorys immihistorys) {
		BaseResponse<List<ItemResponse>> response = new BaseResponse<List<ItemResponse>>();
		response.setCode(Contants.RESPONSE_CODE_FAIL_API);
		response.setMessage(Contants.RESPONSE_MESSAGE_FAIL_API);
		// String nameApi = "updateImmihistory";
		// String gateCode = (immihistorys != null && immihistorys.getImmis() !=
		// null && immihistorys.getImmis().size() > 0) ?
		// immihistorys.getImmis().get(0).getBorderGateCode() : "";
		// if(!this.checkConnectApi(nameApi, gateCode)){
		// response.setMessage(Contants.RESPONSE_MESSAGE_CONNECT_API);
		// return response;
		// }
		List<ItemResponse> itemList = new ArrayList<ItemResponse>();
		logger.info("start update data immihistory to TTDH, start time: "
				+ HelperClass.convertDateToString1(new Date()));
		try {
			if (immihistorys != null) {
				if (immihistorys.getImmis() != null) {
					for (Immihistory immi : immihistorys.getImmis()) {
						ItemResponse item = new ItemResponse();
						ImmiHistory immiU = immihistoryService
								.updateInfoImmihistory(immi);
						if (immiU != null) {
							item.setStage(1);
							// logger.info("success update immihistory by transactionId: "
							// + immi.getTransactionId());
							/*
							 * Thêm vào hàng đợi đồng bộ đi cửa khẩu khi update
							 * thành công
							 */
							List<BorderGate> gateList = borderGateService
									.findBorderGateBySync(Contants.FLAG_Y);
							if (gateList != null) {
								for (BorderGate gate : gateList) {
									if (!immiU.getBorderGateCode().equals(
											gate.getCode())) {
										this.addObjToQueueXncJob(immiU.getId(),
												OBJ_TYPE_QUEUE_XNC_UP,
												gate.getCode(),
												immiU.getTransactionId());
									}
								}
							}
						} else {
							item.setStage(0);
							logger.error("error update immihistory by transactionId: "
									+ immi.getTransactionId());
						}
						item.setTransactionId(immi.getTransactionId());
						itemList.add(item);
					}
				}
			}
			response.setCode(Contants.RESPONSE_CODE_SUCCESS_API);
			response.setMessage(Contants.RESPONSE_MESSAGE_SUCCESS_API);
			response.setData(itemList);

			/* Lưu bảng thống kê truyền nhận */
			if (itemList.size() > 0) {
				this.saveOrUpRptData(Contants.URL_UPDATE_IMMIHISTORY,
						itemList.size(), null);
			}

		} catch (Exception e) {
			e.printStackTrace();
			response.setMessage(e.getMessage());
			logger.error("error admin status: " + e.getMessage());
		}
		return response;
	}

	@GET
	@Produces("application/json")
	@Consumes("application/json")
	@Path("/downloadPassport/{gate}")
	public BaseResponse<ResponseFromA72> downloadPassport(
			@PathParam("gate") String gate) {
		BaseResponse<ResponseFromA72> rep = new BaseResponse<ResponseFromA72>();
		rep.setCode(Contants.RESPONSE_CODE_FAIL_API);
		rep.setMessage(Contants.RESPONSE_MESSAGE_FAIL_API);
		// String nameApi = "downloadPassport";
		// if(!this.checkConnectApi(nameApi, gate)){
		// rep.setMessage(Contants.RESPONSE_MESSAGE_CONNECT_API);
		// return rep;
		// }
		ResponseFromA72 resA72 = new ResponseFromA72();
		List<PassportA72> lstppA72 = new ArrayList<PassportA72>();
		// logger.info("start download passport, gate: " + gate +
		// ", start time: " + HelperClass.convertDateToString1(new Date()));
		try {
			int maxCall = 10;
			Parameters pr = parametersService.getParamDetails(
					Contants.CREATE_BY_SYSTEM,
					PARA_NAME_MAX_COUNT_CALL_QUEUE_XNC);
			if (pr != null) {
				maxCall = Integer.parseInt(pr.getParaShortValue());
			}
			List<SynQueueJobXnc> queueList = queueJobXncService
					.findQueueXncBySite(gate,
							Contants.CODE_STATUS_QUEUE_PENDING,
							new String[] { OBJ_TYPE_QUEUE_XNC_PP }, maxCall);

			if (queueList != null && queueList.size() > 0) {
				for (SynQueueJobXnc queue : queueList) {
					NicDocumentData doc = documentDataService.findByDocNumber(
							queue.getObjectId()).getModel();
					if (doc == null)
						continue;
					NicTransaction tran = doc.getNicTransaction();
					service.perso.model.sync.Person person = new service.perso.model.sync.Person();
					PassportA72 pp = new PassportA72();
					pp.setSignername(doc.getSigner());
					pp.setSignerposition(doc.getPositionSigner());
					pp.setIcaoline1(doc.getIcaoLine1());
					pp.setIcaoline2(doc.getIcaoLine2());
					pp.setStatus(doc.getStatus());
					pp.setIdQueue(queue.getId());
					pp.setCreatedby(doc.getCreateBy());
					pp.setIsepassport(tran.getIsEpassport() ? "Y" : "N");
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
					pp.setCreatest(doc.getCreateDatetime());
					pp.setDateofexpiry(doc.getDateOfExpiry());
					pp.setPassportno(doc.getId().getPassportNo());
					pp.setDateofissue(doc.getDateOfIssue());
					pp.setUpdatets(doc.getUpdateDatetime());
					person.setIssueCode(doc.getPlaceOfIssueCode());
					if (doc.getPlaceOfIssueCode() != null) {
						SiteRepository site = siteService
								.getSiteRepoById(doc
										.getPlaceOfIssueCode());
						if (site != null) {
							person.setIssueName(site
											.getSiteName());
						}
					}
					
					// Lấy dữ liệu cá nhân
					NicRegistrationData regis = nicRegistrationDataService
							.findById(doc.getId().getTransactionId());
					if (regis != null) {
						person.setName(HelperClass.createFullName(regis.getSurnameFull(), regis.getMiddlenameFull(), regis.getFirstnameFull()));
						person.setGender(regis.getGender());
						person.setNationality(regis.getNationality());
						pp.setFpencode(regis.getFpEncode());
						
						/*
						 * if(regis.getDateOfBirth() != null)
						 * person.setDateofbirth
						 * (df.format(regis.getDateOfBirth()));
						 */
						
						person.setDateofbirth(regis.getDateOfBirth());
						person.setPobCode(regis.getPlaceOfBirth());
						String desPob = regis.getPlaceOfBirth();
						if (StringUtils.isNotEmpty(regis.getPlaceOfBirth())) {
							CodeValues codeV = codesService
									.getCodeValueByIdName("CODE_BirthPlace",
											regis.getPlaceOfBirth());
							if (codeV != null) {
								desPob = codeV.getCodeValueDesc();
								person.setPobA08Id(codeV.getA08Id());
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
						person.setFatherDob(regis.getFatherDateOfBirth());
						person.setMotherDob(regis.getMotherDateOfBirth());
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
					NicTransaction nicTran = nicTransactionService.findById(doc
							.getId().getTransactionId());
					if (nicTran != null) {
						person.setIdnumber(nicTran.getNin());
						pp.setType(nicTran.getPassportType());
						pp.setIssuingAuthority(nicTran.getIssuingAuthority());
					}

					// Lấy IdPerson
					BaseModelSingle<NicUploadJob> bGetUJ = uploadJobSerivce
							.getUploadJobByTransactionIdSinger(null,
									nicTran.getTransactionId());
					NicUploadJob nicUp = bGetUJ.getModel();
					if (nicUp != null) {
						person.setIdPerson(nicUp.getNicTransaction()
								.getPersonCode());
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
					queueJobXncService.updateStatusQueueXncJob(queue.getId(),
							Contants.CODE_STATUS_QUEUE_DOING);
					/* Lưu bảng thống kê truyền nhận */
					this.saveOrUpRptData(Contants.URL_DOWNLOAD_PASSPORT, 1,
							gate);
				}
				if (lstppA72 != null && lstppA72.size() > 0) {
					resA72.getPassport().addAll(lstppA72);
				}
				rep.setData(resA72);
			}
			rep.setCode(Contants.RESPONSE_CODE_SUCCESS_API);
			rep.setMessage(Contants.RESPONSE_MESSAGE_SUCCESS_API);
		} catch (Exception e) {
			e.printStackTrace();
			rep.setCode(Contants.CODE_THROW_EXCEPTION);
			rep.setMessage(Contants.MESSAGE_EXCEPTION);
			/*
			 * Lưu dữ liệu nếu có Exception
			 */
			String dataResponse = "";
			String keyId = "DP_" + new SimpleDateFormat("ddMMyyyy").format(new Date());
			try {
				dataResponse = new ObjectMapper()
				.writeValueAsString(rep);
			} catch (Exception e2) {
				logger.error(e2.getMessage());
			}
			this.saveException(Contants.TYPE_DP, Contants.URL_DOWNLOAD_PASSPORT,
					gate, keyId, dataResponse, e);
		}

		return rep;
	}

	@POST
	@Produces("application/json")
	@Consumes("application/json")
	@Path("/syncListAirline")
	public BaseResponse<Boolean> syncListAirline(
			BaseListRequest<EppAirline> data) {
		BaseResponse<Boolean> result = new BaseResponse<Boolean>();
		result.setData(false);
		result.setCode(Contants.RESPONSE_CODE_FAIL_API);
		try {
			if (data != null && data.getData() != null
					&& data.getData().size() > 0) {
				String dataError = "";
				for (EppAirline obj : data.getData()) {
					try {
						EppAirline isExist = airlineService.findAllByCode(obj
								.getCode());
						if (isExist != null) {
							isExist.setCode(obj.getCode());
							isExist.setCountryCode(obj.getCountryCode());
							isExist.setDescriptions(obj.getDescriptions());
							isExist.setLastModifiedBy(obj.getLastModifiedBy());
							isExist.setLastModifiedDate(obj
									.getLastModifiedDate());
							isExist.setName(obj.getName());
							isExist.setClosedTime(obj.getClosedTime());
							airlineService.updateAirline(isExist);
						} else {
							obj.setCreateDate(new Date());
							airlineService.updateAirline(obj);
						}
						/*
						 * Thêm vào hàng đợi đồng bộ đi cửa khẩu khi update
						 * thành công
						 */
						List<BorderGate> gateList = borderGateService
								.findBorderGateBySync(Contants.FLAG_Y);
						if (gateList != null) {
							for (BorderGate gate : gateList) {
								if (!data.getGateCode().equals(gate.getCode())) {
									Boolean check = this.addObjToQueueXncJob(
											null, OBJ_TYPE_QUEUE_XNC_AIRLINE,
											gate.getCode(), obj.getCode());
									if (check) {
										/* Lưu bảng thống kê truyền nhận */
										this.saveOrUpRptData(
												Contants.URL_SYNC_LIST_AIRLINE,
												1, gate.getCode());
									}
								}
							}
						}
					} catch (Exception e) {
						if (obj.getId() != null && obj.getId() > 0)
							dataError += "[UPDATE][CODE: " + obj.getCode()
									+ "] | Message: " + e.getMessage()
									+ System.lineSeparator();
						else
							dataError += "[NEW][CODE: " + obj.getCode()
									+ "] | Message: " + e.getMessage()
									+ System.lineSeparator();
					}
				}
				result.setMessage("[AIRLINE]Du lieu cap nhat/them thanh cong. Ban ghi loi (neu co):"
						+ dataError);
				result.setCode(Contants.RESPONSE_CODE_SUCCESS_API);
				result.setData(true);
			} else {
				result.setCode(Contants.RESPONSE_CODE_NULL_DATA);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			result.setMessage("[AIRLINE]" + e.getMessage());
			e.printStackTrace();
		}

		return result;
	}

	@GET
	@Produces("application/json")
	@Consumes("application/json")
	@Path("/downloadAirline/{gate}")
	public BaseResponse<List<EppAirline>> downloadAirline(
			@PathParam("gate") String gate) {
		BaseResponse<List<EppAirline>> response = new BaseResponse<List<EppAirline>>();
		response.setCode(Contants.RESPONSE_CODE_FAIL_API);
		response.setMessage(Contants.RESPONSE_MESSAGE_FAIL_API);
		List<EppAirline> airlineList = new ArrayList<EppAirline>();
		// logger.info("start download data airline, gate: " + gate +
		// ", start time: " + HelperClass.convertDateToString1(new Date()));
		try {
			int maxCall = 10;
			Parameters pr = parametersService.getParamDetails(
					Contants.CREATE_BY_SYSTEM,
					PARA_NAME_MAX_COUNT_CALL_QUEUE_XNC);
			if (pr != null) {
				maxCall = Integer.parseInt(pr.getParaShortValue());
			}
			List<SynQueueJobXnc> queueList = queueJobXncService
					.findQueueXncBySite(gate,
							Contants.CODE_STATUS_QUEUE_PENDING,
							new String[] { OBJ_TYPE_QUEUE_XNC_AIRLINE },
							maxCall);
			if (queueList != null) {
				for (SynQueueJobXnc queue : queueList) {
					EppAirline epp = airlineService.findAllByCode(queue
							.getObjectId());
					if (epp != null)
						airlineList.add(epp);
				}
			}
			response.setData(airlineList);
			response.setCode(Contants.RESPONSE_CODE_SUCCESS_API);
			response.setMessage(Contants.RESPONSE_MESSAGE_SUCCESS_API);

			/* Lưu bảng thống kê truyền nhận */
			if (airlineList.size() > 0) {
				this.saveOrUpRptData(Contants.URL_DOWNLOAD_AIRLINE,
						airlineList.size(), gate);
			}

			// logger.info("success download airline by gate: " + gate);
		} catch (Exception e) {
			response.setMessage(e.getMessage());
			logger.error("error download airline by gate: " + gate);
		}
		return response;
	}

	@POST
	@Produces("application/json")
	@Consumes("application/json")
	@Path("/syncListAirport")
	public BaseResponse<Boolean> syncListAirport(
			BaseListRequest<EppAirport> data) {
		BaseResponse<Boolean> result = new BaseResponse<Boolean>();
		result.setData(false);
		result.setCode(Contants.RESPONSE_CODE_FAIL_API);
		try {
			if (data != null && data.getData() != null
					&& data.getData().size() > 0) {
				String dataError = "";
				for (EppAirport obj : data.getData()) {
					try {
						EppAirport isExist = airportService.findAllByCode(obj
								.getCode());
						if (isExist != null) {
							isExist.setCode(obj.getCode());
							isExist.setCountryCode(obj.getCountryCode());
							isExist.setDescriptions(obj.getDescriptions());
							isExist.setLastModifiedBy(obj.getLastModifiedBy());
							isExist.setLastModifiedDate(obj
									.getLastModifiedDate());
							isExist.setName(obj.getName());
							isExist.setClosedTime(obj.getClosedTime());
							airportService.updateAirport(isExist);
						} else {
							obj.setCreateDate(new Date());
							airportService.updateAirport(obj);
						}
						/*
						 * Thêm vào hàng đợi đồng bộ đi cửa khẩu khi update
						 * thành công
						 */
						List<BorderGate> gateList = borderGateService
								.findBorderGateBySync(Contants.FLAG_Y);
						if (gateList != null) {
							for (BorderGate gate : gateList) {
								if (!data.getGateCode().equals(gate.getCode())) {
									this.addObjToQueueXncJob(null,
											OBJ_TYPE_QUEUE_XNC_AIRPORT,
											gate.getCode(), obj.getCode());
								}
							}
						}
					} catch (Exception e) {
						if (obj.getId() != null && obj.getId() > 0)
							dataError += "[UPDATE][CODE: " + obj.getCode()
									+ "] | Message: " + e.getMessage()
									+ System.lineSeparator();
						else
							dataError += "[NEW][CODE: " + obj.getCode()
									+ "] | Message: " + e.getMessage()
									+ System.lineSeparator();
					}
				}
				result.setMessage("[AIRPORT]Du lieu cap nhat/them thanh cong. Ban ghi loi (neu co):"
						+ dataError);
				result.setCode(Contants.RESPONSE_CODE_SUCCESS_API);
				result.setData(true);

				/* Lưu bảng thống kê truyền nhận */
				this.saveOrUpRptData(Contants.URL_SYNC_LIST_AIRPORT, 1, null);
			} else {
				result.setCode(Contants.RESPONSE_CODE_NULL_DATA);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			result.setMessage("[AIRPORT]" + e.getMessage());
			e.printStackTrace();
		}

		return result;
	}

	@GET
	@Produces("application/json")
	@Consumes("application/json")
	@Path("/downloadAirport/{gate}")
	public BaseResponse<List<EppAirport>> downloadAirport(
			@PathParam("gate") String gate) {
		BaseResponse<List<EppAirport>> response = new BaseResponse<List<EppAirport>>();
		response.setCode(Contants.RESPONSE_CODE_FAIL_API);
		response.setMessage(Contants.RESPONSE_MESSAGE_FAIL_API);
		List<EppAirport> airportList = new ArrayList<EppAirport>();
		// logger.info("start download data airport, gate: " + gate +
		// ", start time: " + HelperClass.convertDateToString1(new Date()));
		try {
			int maxCall = 10;
			Parameters pr = parametersService.getParamDetails(
					Contants.CREATE_BY_SYSTEM,
					PARA_NAME_MAX_COUNT_CALL_QUEUE_XNC);
			if (pr != null) {
				maxCall = Integer.parseInt(pr.getParaShortValue());
			}
			List<SynQueueJobXnc> queueList = queueJobXncService
					.findQueueXncBySite(gate,
							Contants.CODE_STATUS_QUEUE_PENDING,
							new String[] { OBJ_TYPE_QUEUE_XNC_AIRPORT },
							maxCall);
			if (queueList != null) {
				for (SynQueueJobXnc queue : queueList) {
					EppAirport epp = airportService.findAllByCode(queue
							.getObjectId());
					if (epp != null)
						airportList.add(epp);
				}
			}
			response.setData(airportList);
			response.setCode(Contants.RESPONSE_CODE_SUCCESS_API);
			response.setMessage(Contants.RESPONSE_MESSAGE_SUCCESS_API);

			/* Lưu bảng thống kê truyền nhận */
			if (airportList.size() > 0) {
				this.saveOrUpRptData(Contants.URL_DOWNLOAD_AIRPORT,
						airportList.size(), gate);
			}

			// logger.info("success download airport by gate: " + gate);
		} catch (Exception e) {
			response.setMessage(e.getMessage());
			logger.error("error download airport by gate: " + gate);
		}
		return response;
	}

	@POST
	@Produces("application/json")
	@Consumes("application/json")
	@Path("/syncListFlight")
	public BaseResponse<Boolean> syncListFlight(BaseListRequest<EppFlight> data) {
		BaseResponse<Boolean> result = new BaseResponse<Boolean>();
		result.setData(false);
		result.setCode(Contants.RESPONSE_CODE_FAIL_API);
		try {
			if (data != null && data.getData() != null
					&& data.getData().size() > 0) {
				String dataError = "";
				for (EppFlight obj : data.getData()) {
					try {
						EppFlight isExist = flightService.findByFlightno_Type(
								obj.getFlightNo(), obj.getType());
						if (isExist != null) {
							isExist.setFlightNo(obj.getFlightNo());
							isExist.setFlightRouterCode(obj
									.getFlightRouterCode());
							isExist.setAirlineCode(obj.getAirlineCode());
							isExist.setLastModifiedBy(obj.getLastModifiedBy());
							isExist.setLastModifiedDate(obj
									.getLastModifiedDate());
							isExist.setName(obj.getName());
							isExist.setNote(obj.getNote());
							isExist.setClosedTime(obj.getClosedTime());
							isExist.setType(obj.getType());
							flightService.updateFlight(isExist);
						} else {
							obj.setCreateDate(new Date());
							flightService.updateFlight(obj);
						}
						/*
						 * Thêm vào hàng đợi đồng bộ đi cửa khẩu khi update
						 * thành công
						 */
						List<BorderGate> gateList = borderGateService
								.findBorderGateBySync(Contants.FLAG_Y);
						if (gateList != null) {
							for (BorderGate gate : gateList) {
								if (!data.getGateCode().equals(gate.getCode())) {
									Boolean check = this
											.addToQueueXncJobFlight(
													obj.getType(),
													OBJ_TYPE_QUEUE_XNC_FLIGHT,
													gate.getCode(),
													obj.getFlightNo());
									if (check) {
										/* Lưu bảng thống kê truyền nhận */
										this.saveOrUpRptData(
												Contants.URL_SYNC_LIST_FLIGHT,
												1, gate.getCode());
									}
								}
							}
						}
					} catch (Exception e) {
						if (obj.getId() != null && obj.getId() > 0)
							dataError += "[UPDATE][CODE: " + obj.getFlightNo()
									+ "] | Message: " + e.getMessage()
									+ System.lineSeparator();
						else
							dataError += "[NEW][CODE: " + obj.getFlightNo()
									+ "] | Message: " + e.getMessage()
									+ System.lineSeparator();
					}
				}
				result.setMessage("[FLIGHT]Du lieu cap nhat/them thanh cong. Ban ghi loi (neu co):"
						+ dataError);
				result.setCode(Contants.RESPONSE_CODE_SUCCESS_API);
				result.setData(true);
			} else {
				result.setCode(Contants.RESPONSE_CODE_NULL_DATA);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			result.setMessage("[FLIGHT]" + e.getMessage());
			e.printStackTrace();
		}

		return result;
	}

	@GET
	@Produces("application/json")
	@Consumes("application/json")
	@Path("/downloadFlight/{gate}")
	public BaseResponse<List<EppFlight>> downloadFlight(
			@PathParam("gate") String gate) {
		BaseResponse<List<EppFlight>> response = new BaseResponse<List<EppFlight>>();
		response.setCode(Contants.RESPONSE_CODE_FAIL_API);
		response.setMessage(Contants.RESPONSE_MESSAGE_FAIL_API);
		List<EppFlight> flightList = new ArrayList<EppFlight>();
		// logger.info("start download data flight, gate: " + gate +
		// ", start time: " + HelperClass.convertDateToString1(new Date()));
		try {
			int maxCall = 10;
			Parameters pr = parametersService.getParamDetails(
					Contants.CREATE_BY_SYSTEM,
					PARA_NAME_MAX_COUNT_CALL_QUEUE_XNC);
			if (pr != null) {
				maxCall = Integer.parseInt(pr.getParaShortValue());
			}
			List<SynQueueJobXnc> queueList = queueJobXncService
					.findQueueXncBySite(gate,
							Contants.CODE_STATUS_QUEUE_PENDING,
							new String[] { OBJ_TYPE_QUEUE_XNC_FLIGHT }, maxCall);
			if (queueList != null) {
				for (SynQueueJobXnc queue : queueList) {
					EppFlight epp = flightService.findByFlightno_Type(
							queue.getObjectId(), queue.getFlightType());
					if (epp != null)
						flightList.add(epp);
				}
			}
			response.setData(flightList);
			response.setCode(Contants.RESPONSE_CODE_SUCCESS_API);
			response.setMessage(Contants.RESPONSE_MESSAGE_SUCCESS_API);

			/* Lưu bảng thống kê truyền nhận */
			if (flightList.size() > 0) {
				this.saveOrUpRptData(Contants.URL_DOWNLOAD_FLIGHT,
						flightList.size(), gate);
			}

			// logger.info("success download flight by gate: " + gate);
		} catch (Exception e) {
			response.setMessage(e.getMessage());
			logger.error("error download flight by gate: " + gate);
		}
		return response;
	}

	@POST
	@Produces("application/json")
	@Consumes("application/json")
	@Path("/syncListFlightRouter")
	public BaseResponse<Boolean> syncListFlightRouter(
			BaseListRequest<EppFlightRouter> data) {
		BaseResponse<Boolean> result = new BaseResponse<Boolean>();
		result.setData(false);
		result.setCode(Contants.RESPONSE_CODE_FAIL_API);
		try {
			if (data != null && data.getData() != null
					&& data.getData().size() > 0) {
				String dataError = "";
				for (EppFlightRouter obj : data.getData()) {
					try {
						EppFlightRouter isExist = flightRouterService
								.findByCode(obj.getFlightRouterCode());
						if (isExist != null) {
							isExist.setFlightRouterCode(obj
									.getFlightRouterCode());
							isExist.setFromPlaceCode(obj.getFromPlaceCode());
							isExist.setToPlaceCode(obj.getToPlaceCode());
							isExist.setLastModifiedBy(obj.getLastModifiedBy());
							isExist.setLastModifiedDate(obj
									.getLastModifiedDate());
							isExist.setName(obj.getName());
							isExist.setNote(obj.getNote());
							isExist.setClosedTime(obj.getClosedTime());
							flightRouterService.updateFlightRouter(isExist);
						} else {
							obj.setCreateDate(new Date());
							flightRouterService.updateFlightRouter(obj);
						}
						/*
						 * Thêm vào hàng đợi đồng bộ đi cửa khẩu khi update
						 * thành công
						 */
						List<BorderGate> gateList = borderGateService
								.findBorderGateBySync(Contants.FLAG_Y);
						if (gateList != null) {
							for (BorderGate gate : gateList) {
								if (!data.getGateCode().equals(gate.getCode())) {
									Boolean check = this.addObjToQueueXncJob(
											null,
											OBJ_TYPE_QUEUE_XNC_FLIGHT_ROUTER,
											gate.getCode(),
											obj.getFlightRouterCode());
									/* Lưu bảng thống kê truyền nhận */
									if (check) {
										this.saveOrUpRptData(
												Contants.URL_SYNC_LIST_FLIGHT_ROUTER,
												1, gate.getCode());
									}
								}
							}
						}
					} catch (Exception e) {
						if (obj.getId() != null && obj.getId() > 0)
							dataError += "[UPDATE][CODE: "
									+ obj.getFlightRouterCode()
									+ "] | Message: " + e.getMessage()
									+ System.lineSeparator();
						else
							dataError += "[NEW][CODE: "
									+ obj.getFlightRouterCode()
									+ "] | Message: " + e.getMessage()
									+ System.lineSeparator();
					}
				}
				result.setMessage("[FLIGHT_ROUTER]Du lieu cap nhat/them thanh cong. Ban ghi loi (neu co):"
						+ dataError);
				result.setCode(Contants.RESPONSE_CODE_SUCCESS_API);
				result.setData(true);
			} else {
				result.setCode(Contants.RESPONSE_CODE_NULL_DATA);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			result.setMessage("[FLIGHT_ROUTER]" + e.getMessage());
			e.printStackTrace();
		}

		return result;
	}

	@GET
	@Produces("application/json")
	@Consumes("application/json")
	@Path("/downloadFlightRouter/{gate}")
	public BaseResponse<List<EppFlightRouter>> downloadFlightRouter(
			@PathParam("gate") String gate) {
		BaseResponse<List<EppFlightRouter>> response = new BaseResponse<List<EppFlightRouter>>();
		response.setCode(Contants.RESPONSE_CODE_FAIL_API);
		response.setMessage(Contants.RESPONSE_MESSAGE_FAIL_API);
		List<EppFlightRouter> frList = new ArrayList<EppFlightRouter>();
		// logger.info("start download data flight router, gate: " + gate +
		// ", start time: " + HelperClass.convertDateToString1(new Date()));
		try {
			int maxCall = 10;
			Parameters pr = parametersService.getParamDetails(
					Contants.CREATE_BY_SYSTEM,
					PARA_NAME_MAX_COUNT_CALL_QUEUE_XNC);
			if (pr != null) {
				maxCall = Integer.parseInt(pr.getParaShortValue());
			}
			List<SynQueueJobXnc> queueList = queueJobXncService
					.findQueueXncBySite(gate,
							Contants.CODE_STATUS_QUEUE_PENDING,
							new String[] { OBJ_TYPE_QUEUE_XNC_FLIGHT_ROUTER },
							maxCall);
			if (queueList != null) {
				for (SynQueueJobXnc queue : queueList) {
					EppFlightRouter epp = flightRouterService.findByCode(queue
							.getObjectId());
					if (epp != null)
						frList.add(epp);
				}
			}
			response.setData(frList);
			response.setCode(Contants.RESPONSE_CODE_SUCCESS_API);
			response.setMessage(Contants.RESPONSE_MESSAGE_SUCCESS_API);

			/* Lưu bảng thống kê truyền nhận */
			if (frList.size() > 0) {
				this.saveOrUpRptData(Contants.URL_DOWNLOAD_FLIGHT_ROUTER,
						frList.size(), gate);
			}

			// logger.info("success download flight router by gate: " + gate);
		} catch (Exception e) {
			response.setMessage(e.getMessage());
			logger.error("error download flight router by gate: " + gate);
		}
		return response;
	}

	@POST
	@Produces("application/json")
	@Consumes("application/json")
	@Path("/syncListFreeVisa")
	public BaseResponse<Boolean> syncListFreeVisa(
			BaseListRequest<EppFreeVisa> data) {
		BaseResponse<Boolean> result = new BaseResponse<Boolean>();
		result.setData(false);
		result.setCode(Contants.RESPONSE_CODE_FAIL_API);
		try {
			if (data != null && data.getData() != null
					&& data.getData().size() > 0) {
				String dataError = "";
				for (EppFreeVisa obj : data.getData()) {
					try {
						EppFreeVisa isExist = freeVisaService
								.findByCtryCode_Type_PPType(
										obj.getCountryCode(),
										obj.getPassportType(), obj.getType());
						if (isExist != null) {
							isExist.setFreeDay(obj.getFreeDay());
							isExist.setValidFromDate(obj.getValidFromDate());
							isExist.setValidToDate(obj.getValidToDate());
							isExist.setStatus(obj.getStatus());
							isExist.setPassportExpiredDay(obj
									.getPassportExpiredDay());
							isExist.setStayDay(obj.getStayDay());
							isExist.setValidToDate(obj.getValidToDate());
							isExist.setLastModifiedBy(obj.getLastModifiedBy());
							isExist.setLastModifiedDate(obj
									.getLastModifiedDate());
							isExist.setMaxLastEmmiDay(obj.getMaxLastEmmiDay());
							isExist.setDescriptions(obj.getDescriptions());
							freeVisaService.updateFreeVisa(isExist);
						} else {
							obj.setCreateDate(new Date());
							freeVisaService.updateFreeVisa(obj);
						}
						/*
						 * Thêm vào hàng đợi đồng bộ đi cửa khẩu khi update
						 * thành công
						 */
						List<BorderGate> gateList = borderGateService
								.findBorderGateBySync(Contants.FLAG_Y);
						if (gateList != null) {
							for (BorderGate gate : gateList) {
								if (!data.getGateCode().equals(gate.getCode())) {
									Boolean check = this.addToQueueXncJobVisa(
											obj.getType(),
											obj.getPassportType(),
											OBJ_TYPE_QUEUE_XNC_FREE_VISA,
											gate.getCode(),
											obj.getCountryCode());
									/* Lưu bảng thống kê truyền nhận */
									if (check) {
										this.saveOrUpRptData(
												Contants.URL_SYNC_LIST_FREE_VISA,
												1, gate.getCode());
									}
								}
							}
						}
					} catch (Exception e) {
						if (obj.getId() != null && obj.getId() > 0)
							dataError += "[UPDATE][COUNTRY_CODE: "
									+ obj.getCountryCode() + " | PP_TYPE: "
									+ obj.getPassportType() + " | TYPE:"
									+ obj.getType() + "] | Message: "
									+ e.getMessage() + System.lineSeparator();
						else
							dataError += "[NEW][COUNTRY_CODE: "
									+ obj.getCountryCode() + " | PP_TYPE: "
									+ obj.getPassportType() + " | TYPE:"
									+ obj.getType() + "] | Message: "
									+ e.getMessage() + System.lineSeparator();
					}
				}
				result.setMessage("[FREE_VISA]Du lieu cap nhat/them thanh cong. Ban ghi loi (neu co):"
						+ dataError);
				result.setCode(Contants.RESPONSE_CODE_SUCCESS_API);
				result.setData(true);
			} else {
				result.setCode(Contants.RESPONSE_CODE_NULL_DATA);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			result.setMessage("[FREE_VISA]" + e.getMessage());
			e.printStackTrace();
		}

		return result;
	}

	@GET
	@Produces("application/json")
	@Consumes("application/json")
	@Path("/downloadFreeVisa/{gate}")
	public BaseResponse<List<EppFreeVisa>> downloadFreeVisa(
			@PathParam("gate") String gate) {
		BaseResponse<List<EppFreeVisa>> response = new BaseResponse<List<EppFreeVisa>>();
		response.setCode(Contants.RESPONSE_CODE_FAIL_API);
		response.setMessage(Contants.RESPONSE_MESSAGE_FAIL_API);
		List<EppFreeVisa> frList = new ArrayList<EppFreeVisa>();
		// logger.info("start download data free visa, gate: " + gate +
		// ", start time: " + HelperClass.convertDateToString1(new Date()));
		try {
			int maxCall = 10;
			Parameters pr = parametersService.getParamDetails(
					Contants.CREATE_BY_SYSTEM,
					PARA_NAME_MAX_COUNT_CALL_QUEUE_XNC);
			if (pr != null) {
				maxCall = Integer.parseInt(pr.getParaShortValue());
			}
			List<SynQueueJobXnc> queueList = queueJobXncService
					.findQueueXncBySite(gate,
							Contants.CODE_STATUS_QUEUE_PENDING,
							new String[] { OBJ_TYPE_QUEUE_XNC_FREE_VISA },
							maxCall);
			if (queueList != null) {
				for (SynQueueJobXnc queue : queueList) {
					EppFreeVisa epp = freeVisaService
							.findByCtryCode_Type_PPType(queue.getObjectId(),
									queue.getPassportType(),
									queue.getFlightType());
					if (epp != null)
						frList.add(epp);
				}
			}
			response.setData(frList);
			response.setCode(Contants.RESPONSE_CODE_SUCCESS_API);
			response.setMessage(Contants.RESPONSE_MESSAGE_SUCCESS_API);

			/* Lưu bảng thống kê truyền nhận */
			if (frList.size() > 0) {
				this.saveOrUpRptData(Contants.URL_DOWNLOAD_FREE_VISA,
						frList.size(), gate);
			}

			// logger.info("success download free visa by gate: " + gate);
		} catch (Exception e) {
			response.setMessage(e.getMessage());
			logger.error("error download free visa by gate: " + gate);
		}
		return response;
	}

	@POST
	@Produces("application/json")
	@Consumes("application/json")
	@Path("/syncListPurpose")
	public BaseResponse<Boolean> syncListPurpose(
			BaseListRequest<EppPurpose> data) {
		BaseResponse<Boolean> result = new BaseResponse<Boolean>();
		result.setData(false);
		result.setCode(Contants.RESPONSE_CODE_FAIL_API);
		try {
			if (data != null && data.getData() != null
					&& data.getData().size() > 0) {
				String dataError = "";
				for (EppPurpose obj : data.getData()) {
					try {
						EppPurpose isExist = purposeService.findByCode(obj
								.getPurposeCode());
						if (isExist != null) {
							isExist.setTitle(obj.getTitle());
							isExist.setStatus(obj.getStatus());
							isExist.setNote(obj.getNote());
							isExist.setLastModifiedBy(obj.getLastModifiedBy());
							isExist.setLastModifiedDate(obj
									.getLastModifiedDate());
							purposeService.updatePurpose(isExist);
						} else {
							obj.setCreateDate(new Date());
							purposeService.updatePurpose(obj);
						}
						/*
						 * Thêm vào hàng đợi đồng bộ đi cửa khẩu khi update
						 * thành công
						 */
						List<BorderGate> gateList = borderGateService
								.findBorderGateBySync(Contants.FLAG_Y);
						if (gateList != null) {
							for (BorderGate gate : gateList) {
								if (!data.getGateCode().equals(gate.getCode())) {
									Boolean check = this.addObjToQueueXncJob(
											null, OBJ_TYPE_QUEUE_XNC_PURPOSE,
											gate.getCode(),
											obj.getPurposeCode());
									/* Lưu bảng thống kê truyền nhận */
									if (check) {
										this.saveOrUpRptData(
												Contants.URL_SYNC_LIST_PURPOSE,
												1, gate.getCode());
									}
								}
							}
						}
					} catch (Exception e) {
						if (obj.getId() != null && obj.getId() > 0)
							dataError += "[UPDATE][CODE: "
									+ obj.getPurposeCode() + "] | Message: "
									+ e.getMessage() + System.lineSeparator();
						else
							dataError += "[NEW][CODE: " + obj.getPurposeCode()
									+ "] | Message: " + e.getMessage()
									+ System.lineSeparator();
					}
				}
				result.setMessage("[PURPOSE]Du lieu cap nhat/them thanh cong. Ban ghi loi (neu co):"
						+ dataError);
				result.setCode(Contants.RESPONSE_CODE_SUCCESS_API);
				result.setData(true);
			} else {
				result.setCode(Contants.RESPONSE_CODE_NULL_DATA);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			result.setMessage("[PURPOSE]" + e.getMessage());
			e.printStackTrace();
		}

		return result;
	}

	@GET
	@Produces("application/json")
	@Consumes("application/json")
	@Path("/downloadPurpose/{gate}")
	public BaseResponse<List<EppPurpose>> downloadPurpose(
			@PathParam("gate") String gate) {
		BaseResponse<List<EppPurpose>> response = new BaseResponse<List<EppPurpose>>();
		response.setCode(Contants.RESPONSE_CODE_FAIL_API);
		response.setMessage(Contants.RESPONSE_MESSAGE_FAIL_API);
		List<EppPurpose> ppList = new ArrayList<EppPurpose>();
		// logger.info("start download data purpose, gate: " + gate +
		// ", start time: " + HelperClass.convertDateToString1(new Date()));
		try {
			int maxCall = 10;
			Parameters pr = parametersService.getParamDetails(
					Contants.CREATE_BY_SYSTEM,
					PARA_NAME_MAX_COUNT_CALL_QUEUE_XNC);
			if (pr != null) {
				maxCall = Integer.parseInt(pr.getParaShortValue());
			}
			List<SynQueueJobXnc> queueList = queueJobXncService
					.findQueueXncBySite(gate,
							Contants.CODE_STATUS_QUEUE_PENDING,
							new String[] { OBJ_TYPE_QUEUE_XNC_PURPOSE },
							maxCall);
			if (queueList != null) {
				for (SynQueueJobXnc queue : queueList) {
					EppPurpose epp = purposeService.findByCode(queue
							.getObjectId());
					if (epp != null)
						ppList.add(epp);
				}
			}
			response.setData(ppList);
			response.setCode(Contants.RESPONSE_CODE_SUCCESS_API);
			response.setMessage(Contants.RESPONSE_MESSAGE_SUCCESS_API);

			/* Lưu bảng thống kê truyền nhận */
			if (ppList.size() > 0) {
				this.saveOrUpRptData(Contants.URL_DOWNLOAD_PURPOSE,
						ppList.size(), gate);
			}

			// logger.info("success download purpose by gate: " + gate);
		} catch (Exception e) {
			response.setMessage(e.getMessage());
			logger.error("error download purpose by gate: " + gate);
		}
		return response;
	}

	private Boolean addObjToQueueXncJob(Long immiId, String ObjType,
			String site, String transactionId) {
		try {
			SynQueueJobXnc queue = new SynQueueJobXnc();
			queue.setCreatedTs(Calendar.getInstance().getTime());
			queue.setObjectId(transactionId);
			queue.setObjectType(ObjType);
			queue.setSiteCode(site);
			queue.setStatus(Contants.CODE_STATUS_QUEUE_PENDING);
			queue.setSyncRetry(1);
			queue.setIdImmi(immiId);
			queueJobXncService.saveOrUpdateQueueXnc(queue);
			return true;
		} catch (Exception e) {

		}
		return false;
	}

	private Boolean addToQueueXncJobFlight(String type, String ObjType,
			String site, String transactionId) {
		try {
			SynQueueJobXnc queue = new SynQueueJobXnc();
			queue.setCreatedTs(Calendar.getInstance().getTime());
			queue.setObjectId(transactionId);
			queue.setObjectType(ObjType);
			queue.setSiteCode(site);
			queue.setStatus(Contants.CODE_STATUS_QUEUE_PENDING);
			queue.setSyncRetry(1);
			queue.setFlightType(type);
			queueJobXncService.saveOrUpdateQueueXnc(queue);
			return true;
		} catch (Exception e) {

		}
		return false;
	}

	private Boolean addToQueueXncJobVisa(String type, String passportType,
			String ObjType, String site, String transactionId) {
		try {
			SynQueueJobXnc queue = new SynQueueJobXnc();
			queue.setCreatedTs(Calendar.getInstance().getTime());
			queue.setObjectId(transactionId);
			queue.setObjectType(ObjType);
			queue.setSiteCode(site);
			queue.setStatus(Contants.CODE_STATUS_QUEUE_PENDING);
			queue.setSyncRetry(1);
			queue.setFlightType(type);
			queue.setPassportType(passportType);
			queueJobXncService.saveOrUpdateQueueXnc(queue);
			return true;
		} catch (Exception e) {

		}
		return false;
	}

	public Boolean checkConnectApi(String nameApi, String siteCode) {
		Boolean result = true;
		try {
			ConfigurationApi cfg = configurationApiService.findConfigApiByName(
					nameApi, null);
			if (cfg != null) {
				if (cfg.getCloseAll()) {
					result = false;
				} else if (StringUtils.isNotEmpty(cfg.getCloseMember())
						&& StringUtils.isNotEmpty(siteCode)) {
					if (cfg.getCloseMember().contains(siteCode))
						result = false;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	// save or update rptData
	private void saveOrUpRptData(String type, int count, String siteCode) {
		try {
			RptStatisticsTransmitData rptData = rptService
					.findSingerByConditions(type, siteCode, DateUtil.strToDate(
							HelperClass.convertDateType3(new Date()),
							DateUtil.FORMAT_YYYYMMDD));
			if (rptData != null) {
				rptData.setTotal(rptData.getTotal() + count);
			} else {
				rptData = new RptStatisticsTransmitData();
				rptData.setRptDate(DateUtil.strToDate(
						HelperClass.convertDateType3(new Date()),
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
}
