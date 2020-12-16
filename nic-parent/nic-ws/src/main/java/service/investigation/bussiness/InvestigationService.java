package service.investigation.bussiness;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.jws.WebService;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.ext.Provider;

import jcifs.util.Base64;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nec.asia.nic.comp.a08database.domain.ABTCDetail;
import com.nec.asia.nic.comp.a08database.domain.CnVpDetail;
import com.nec.asia.nic.comp.a08database.domain.DecAttachment;
import com.nec.asia.nic.comp.a08database.domain.DocumentCancelDetail;
import com.nec.asia.nic.comp.a08database.domain.DoiTuongDetail;
import com.nec.asia.nic.comp.a08database.domain.GpXncDetail;
import com.nec.asia.nic.comp.a08database.domain.HsVpDetail;
import com.nec.asia.nic.comp.a08database.domain.ImmigrationHistoryDetail;
import com.nec.asia.nic.comp.a08database.domain.InfoCMT;
import com.nec.asia.nic.comp.a08database.domain.LSCapHoChieu;
import com.nec.asia.nic.comp.a08database.domain.NhanTlDetail;
import com.nec.asia.nic.comp.a08database.domain.PassportStatus;
import com.nec.asia.nic.comp.a08database.domain.PersonFamily;
import com.nec.asia.nic.comp.a08database.domain.ThoiQtDetail;
import com.nec.asia.nic.comp.a08database.domain.TlQtDetail;
import com.nec.asia.nic.comp.a08database.domain.VkHhDetail;
import com.nec.asia.nic.comp.a08database.domain.XmnsDetail;
import com.nec.asia.nic.comp.a08database.domain.DoiTuong;

import service.investigation.model.DataPersonBuff;
import service.investigation.model.DoiTuongQuery;
import service.investigation.model.DownloadBuffInput;
import service.investigation.model.InfoGetDetailBuffPerson;
import service.investigation.model.ObjDetailBuffPerson;
import service.investigation.model.ResponseDownloadBuff;
import service.perso.util.Contants;
import service.transaction.model.Response;
import service.transaction.model.ResponseBase;

import com.nec.asia.nic.comp.a08database.service.GetRecordDetailService;
import com.nec.asia.nic.comp.a08database.service.impl.GetRecordDetailServiceImpl;
import com.nec.asia.nic.comp.bufPersonInvestigation.service.BufPersonInvestigationService;
import com.nec.asia.nic.comp.listHandover.domain.NicListHandover;
import com.nec.asia.nic.comp.trans.domain.BaseModelList;
import com.nec.asia.nic.comp.trans.domain.BaseModelSingle;
import com.nec.asia.nic.comp.trans.domain.BufEppPersonInvestigation;
import com.nec.asia.nic.comp.trans.domain.EppArchive;
import com.nec.asia.nic.comp.trans.domain.EppIdentification;
import com.nec.asia.nic.comp.trans.domain.EppListHandoverDetail;
import com.nec.asia.nic.comp.trans.domain.EppPerson;
import com.nec.asia.nic.comp.trans.domain.NicAfisData;
import com.nec.asia.nic.comp.trans.domain.NicDocumentData;
import com.nec.asia.nic.comp.trans.domain.NicRegistrationData;
import com.nec.asia.nic.comp.trans.domain.NicSearchHitResult;
import com.nec.asia.nic.comp.trans.domain.NicSearchResult;
import com.nec.asia.nic.comp.trans.domain.NicTransaction;
import com.nec.asia.nic.comp.trans.domain.NicTransactionAttachment;
import com.nec.asia.nic.comp.trans.domain.NicUploadJob;
import com.nec.asia.nic.comp.trans.domain.RptStatisticsTransmitData;
import com.nec.asia.nic.comp.trans.domain.SyncQueueJob;
import com.nec.asia.nic.comp.trans.dto.SearchResultInA08Dto;
import com.nec.asia.nic.comp.trans.service.ConfigurationWorkflowService;
import com.nec.asia.nic.comp.trans.service.DataPackService;
import com.nec.asia.nic.comp.trans.service.DocumentDataService;
import com.nec.asia.nic.comp.trans.service.EppArchiveService;
import com.nec.asia.nic.comp.trans.service.EppListHandoverDetailService;
import com.nec.asia.nic.comp.trans.service.EppPersonService;
import com.nec.asia.nic.comp.trans.service.FeeRecieptPaymentService;
import com.nec.asia.nic.comp.trans.service.InquirySearchResultService;
import com.nec.asia.nic.comp.trans.service.NicAfisDataService;
import com.nec.asia.nic.comp.trans.service.NicIdentificationService;
import com.nec.asia.nic.comp.trans.service.NicRegistrationDataService;
import com.nec.asia.nic.comp.trans.service.NicSearchHitResultService;
import com.nec.asia.nic.comp.trans.service.NicSearchResultService;
import com.nec.asia.nic.comp.trans.service.NicTransactionAttachmentService;
import com.nec.asia.nic.comp.trans.service.NicTransactionService;
import com.nec.asia.nic.comp.trans.service.NicUploadJobService;
import com.nec.asia.nic.comp.trans.service.RptStatisticsTransmitDataService;
import com.nec.asia.nic.comp.trans.service.SyncQueueJobService;
import com.nec.asia.nic.comp.ws.log.domain.CreateMessageException;
import com.nec.asia.nic.comp.ws.log.domain.EppWsLog;
import com.nec.asia.nic.comp.ws.log.service.EppWsLogService;
import com.nec.asia.nic.framework.admin.code.dao.ParametersDao;
import com.nec.asia.nic.framework.admin.code.domain.CodeValues;
import com.nec.asia.nic.framework.admin.code.domain.Parameters;
import com.nec.asia.nic.framework.admin.code.domain.ParametersId;
import com.nec.asia.nic.framework.admin.code.service.CodesService;
import com.nec.asia.nic.framework.admin.code.service.ParametersService;
import com.nec.asia.nic.framework.admin.site.domain.SiteRepository;
import com.nec.asia.nic.framework.admin.site.service.SiteService;
import com.nec.asia.nic.utils.DateUtil;
import com.nec.asia.nic.utils.HelperClass;

@Path("/investigation/")
@WebService
@Provider
public class InvestigationService {

	private GetRecordDetailService getRecordDetailService;

	@Autowired
	private EppWsLogService eppWsLogService;

	@Autowired
	private RptStatisticsTransmitDataService rptService;

	@Autowired
	private SyncQueueJobService queueJobService;

	@Autowired
	private BufPersonInvestigationService bufPersonInvestigationService;

	@Autowired
	private NicTransactionAttachmentService nicTransactionAttachmentService;

	@Autowired
	private NicSearchResultService nicSearchResultService;

	@Autowired
	private NicSearchHitResultService nicSearchHitResultService;

	@Autowired
	private SiteService siteService;

	@Autowired
	private ConfigurationWorkflowService configurationWorkflowService;

	@Autowired
	private NicUploadJobService uploadJobService;

	@Autowired
	private InquirySearchResultService inquirySearchResultService;

	@Autowired
	private NicTransactionService nicTransactionService;

	@Autowired
	private EppPersonService eppPersonService;

	@Autowired
	private DocumentDataService documentDataService;

	@Autowired
	private EppListHandoverDetailService listHandoverDetailService;

	@Autowired
	private CodesService codesService;

	@Autowired
	private EppArchiveService eppArchiveService;

	@Autowired
	private ParametersDao parametersDao;

	@Autowired
	private FeeRecieptPaymentService feeRecieptPaymentService;
	@Autowired
	private NicRegistrationDataService nicRegistrationDataService;

	@Autowired
	private NicAfisDataService nicAfisDataService;

	@Autowired
	private ParametersService parametersService;

	@Autowired
	private NicIdentificationService nicIdentificationService;

	@Autowired
	private DataPackService dataPackService;

	public final Logger logger = LoggerFactory.getLogger(this.getClass());

	public InvestigationService() throws Exception {
		getRecordDetailService = new GetRecordDetailServiceImpl();
	}

	/*
	 * Lấy thông tin ABTC
	 */
	@GET
	@Produces("application/json")
	@Consumes("application/json")
	@Path("/getABTCDetail/{personCode}")
	public Response<List<ABTCDetail>> getABTCDetail(
			@PathParam("personCode") String personCode) {
		Response<List<ABTCDetail>> response = new Response<List<ABTCDetail>>();
		response.setCode(Contants.CODE_CONNECT_FAIL);
		response.setMessage(Contants.MESS_CONNECT_FAIL);
		List<ABTCDetail> listAbtcDetails = null;
		try {
			if (StringUtils.isNotBlank(personCode)) {

				listAbtcDetails = getRecordDetailService
						.getAbtcDetails(personCode);

				if (listAbtcDetails != null && listAbtcDetails.size() > 0) {
					/* Lưu bảng thống kê truyền nhận */
					this.saveOrUpRptData(Contants.URL_GET_ABTC_DETAIL,
							listAbtcDetails.size(), null);
				}
				response.setData(listAbtcDetails);
				response.setCode(Contants.CODE_SUCCESS);
				response.setMessage(Contants.MESSAGE_SUCCESS);
			} else {
				response.setCode(Contants.CODE_INPUT_FAILD);
				response.setMessage(Contants.MESSAGE_INPUT_NULL);
			}
		} catch (Exception e) {
			e.printStackTrace();
			response.setCode(Contants.CODE_THROW_EXCEPTION);
			response.setMessage(Contants.MESSAGE_EXCEPTION);
			/*
			 * Lưu dữ liệu nếu có Exception
			 */
			String keyId = "GAD_"
					+ new SimpleDateFormat("ddMMyyyy").format(new Date());
			this.saveException(Contants.TYPE_GAD, Contants.URL_GET_ABTC_DETAIL,
					personCode, keyId, response.toString(), e);
		}
		return response;
	}

	/*
	 * Lấy thông tin giấy phép XNC
	 */
	@GET
	@Produces("application/json")
	@Consumes("application/json")
	@Path("/getGpXncDetail/{personCode}")
	public Response<List<GpXncDetail>> getGpXncDetail(
			@PathParam("personCode") String personCode) {
		Response<List<GpXncDetail>> response = new Response<List<GpXncDetail>>();
		response.setCode(Contants.CODE_CONNECT_FAIL);
		response.setMessage(Contants.MESS_CONNECT_FAIL);
		List<GpXncDetail> listGpXncDetails = null;
		try {
			if (StringUtils.isNotBlank(personCode)) {

				listGpXncDetails = getRecordDetailService
						.getGpXncDetails(personCode);

				if (listGpXncDetails != null && listGpXncDetails.size() > 0) {
					/* Lưu bảng thống kê truyền nhận */
					this.saveOrUpRptData(Contants.URL_GET_GP_XNC_DETAIL,
							listGpXncDetails.size(), null);
				}
				response.setData(listGpXncDetails);
				response.setCode(Contants.CODE_SUCCESS);
				response.setMessage(Contants.MESSAGE_SUCCESS);
			} else {
				response.setCode(Contants.CODE_INPUT_FAILD);
				response.setMessage(Contants.MESSAGE_INPUT_NULL);
			}
		} catch (Exception e) {
			e.printStackTrace();
			response.setCode(Contants.CODE_THROW_EXCEPTION);
			response.setMessage(Contants.MESSAGE_EXCEPTION);
			/*
			 * Lưu dữ liệu nếu có Exception
			 */
			String keyId = "GGXD_"
					+ new SimpleDateFormat("ddMMyyyy").format(new Date());
			this.saveException(Contants.TYPE_GGXD,
					Contants.URL_GET_GP_XNC_DETAIL, personCode, keyId,
					response.toString(), e);
		}
		return response;
	}

	/*
	 * Lấy thông tin trở lại quốc tịch
	 */
	@GET
	@Produces("application/json")
	@Consumes("application/json")
	@Path("/getTlQtDetail/{personCode}")
	public Response<List<TlQtDetail>> getTlQtDetail(
			@PathParam("personCode") String personCode) {
		Response<List<TlQtDetail>> response = new Response<List<TlQtDetail>>();
		response.setCode(Contants.CODE_CONNECT_FAIL);
		response.setMessage(Contants.MESS_CONNECT_FAIL);
		List<TlQtDetail> listTlQtDetails = null;
		try {
			if (StringUtils.isNotBlank(personCode)) {

				listTlQtDetails = getRecordDetailService
						.getTlQtDetails(personCode);

				if (listTlQtDetails != null && listTlQtDetails.size() > 0) {
					/* Lưu bảng thống kê truyền nhận */
					this.saveOrUpRptData(Contants.URL_GET_TL_QT_DETAIL,
							listTlQtDetails.size(), null);
				}
				response.setData(listTlQtDetails);
				response.setCode(Contants.CODE_SUCCESS);
				response.setMessage(Contants.MESSAGE_SUCCESS);
			} else {
				response.setCode(Contants.CODE_INPUT_FAILD);
				response.setMessage(Contants.MESSAGE_INPUT_NULL);
			}
		} catch (Exception e) {
			e.printStackTrace();
			response.setCode(Contants.CODE_THROW_EXCEPTION);
			response.setMessage(Contants.MESSAGE_EXCEPTION);
			/*
			 * Lưu dữ liệu nếu có Exception
			 */
			String keyId = "GTQD_"
					+ new SimpleDateFormat("ddMMyyyy").format(new Date());
			this.saveException(Contants.TYPE_GTQD,
					Contants.URL_GET_TL_QT_DETAIL, personCode, keyId,
					response.toString(), e);
		}
		return response;
	}

	/*
	 * Lấy thông tin thôi quốc tịch
	 */
	@GET
	@Produces("application/json")
	@Consumes("application/json")
	@Path("/getThoiQtDetail/{personCode}")
	public Response<List<ThoiQtDetail>> getThoiQtDetail(
			@PathParam("personCode") String personCode) {
		Response<List<ThoiQtDetail>> response = new Response<List<ThoiQtDetail>>();
		response.setCode(Contants.CODE_CONNECT_FAIL);
		response.setMessage(Contants.MESS_CONNECT_FAIL);
		List<ThoiQtDetail> lisThoiQtDetails = null;
		try {
			if (StringUtils.isNotBlank(personCode)) {

				lisThoiQtDetails = getRecordDetailService
						.getThoiQtDetails(personCode);

				if (lisThoiQtDetails != null && lisThoiQtDetails.size() > 0) {
					/* Lưu bảng thống kê truyền nhận */
					this.saveOrUpRptData(Contants.URL_GET_THOI_QT_DETAIL,
							lisThoiQtDetails.size(), null);
				}
				response.setData(lisThoiQtDetails);
				response.setCode(Contants.CODE_SUCCESS);
				response.setMessage(Contants.MESSAGE_SUCCESS);
			} else {
				response.setCode(Contants.CODE_INPUT_FAILD);
				response.setMessage(Contants.MESSAGE_INPUT_NULL);
			}
		} catch (Exception e) {
			e.printStackTrace();
			response.setCode(Contants.CODE_THROW_EXCEPTION);
			response.setMessage(Contants.MESSAGE_EXCEPTION);
			/*
			 * Lưu dữ liệu nếu có Exception
			 */
			String keyId = "GTQD_"
					+ new SimpleDateFormat("ddMMyyyy").format(new Date());
			this.saveException(Contants.TYPE_GTQD,
					Contants.URL_GET_THOI_QT_DETAIL, personCode, keyId,
					response.toString(), e);
		}
		return response;
	}

	/*
	 * Lấy thông tin nhận trở lại (hồi hương, trục xuất)
	 */
	@GET
	@Produces("application/json")
	@Consumes("application/json")
	@Path("/getNhanTlDetail/{personCode}")
	public Response<List<NhanTlDetail>> getNhanTlDetail(
			@PathParam("personCode") String personCode) {
		Response<List<NhanTlDetail>> response = new Response<List<NhanTlDetail>>();
		response.setCode(Contants.CODE_CONNECT_FAIL);
		response.setMessage(Contants.MESS_CONNECT_FAIL);
		List<NhanTlDetail> listNhanTlDetails = null;
		try {
			if (StringUtils.isNotBlank(personCode)) {

				listNhanTlDetails = getRecordDetailService
						.getNhanTlDetails(personCode);

				if (listNhanTlDetails != null && listNhanTlDetails.size() > 0) {
					/* Lưu bảng thống kê truyền nhận */
					this.saveOrUpRptData(Contants.URL_GET_NHAN_TL_DETAIL,
							listNhanTlDetails.size(), null);
				}
				response.setData(listNhanTlDetails);
				response.setCode(Contants.CODE_SUCCESS);
				response.setMessage(Contants.MESSAGE_SUCCESS);
			} else {
				response.setCode(Contants.CODE_INPUT_FAILD);
				response.setMessage(Contants.MESSAGE_INPUT_NULL);
			}
		} catch (Exception e) {
			e.printStackTrace();
			response.setCode(Contants.CODE_THROW_EXCEPTION);
			response.setMessage(Contants.MESSAGE_EXCEPTION);
			/*
			 * Lưu dữ liệu nếu có Exception
			 */
			String keyId = "GNTD_"
					+ new SimpleDateFormat("ddMMyyyy").format(new Date());
			this.saveException(Contants.TYPE_GNTD,
					Contants.URL_GET_NHAN_TL_DETAIL, personCode, keyId,
					response.toString(), e);
		}
		return response;
	}

	/*
	 * Lấy thông tin xác minh nhân sự
	 */
	@GET
	@Produces("application/json")
	@Consumes("application/json")
	@Path("/getXmnsDetail/{personCode}")
	public Response<List<XmnsDetail>> getXmnsDetail(
			@PathParam("personCode") String personCode) {
		Response<List<XmnsDetail>> response = new Response<List<XmnsDetail>>();
		response.setCode(Contants.CODE_CONNECT_FAIL);
		response.setMessage(Contants.MESS_CONNECT_FAIL);
		List<XmnsDetail> listXmnsDetails = null;
		try {
			if (StringUtils.isNotBlank(personCode)) {

				listXmnsDetails = getRecordDetailService
						.getXmnsDetails(personCode);

				if (listXmnsDetails != null && listXmnsDetails.size() > 0) {
					for (XmnsDetail xmnsDetail : listXmnsDetails) {
						CodeValues code = null;
						if (StringUtils.isNotBlank(xmnsDetail
								.getResidentPlaceName())) {
							code = codesService.findByCodeIdAndA08Id(
									CodeValues.CODE_ID_DISTRICT,
									xmnsDetail.getResidentPlaceName());
							if (code != null) {
								xmnsDetail.setResidentPlaceName(code
										.getCodeValueDesc());
							}
						}
						if (StringUtils
								.isNotBlank(xmnsDetail.getPlaceOfBirth())) {
							code = codesService.findByCodeIdAndA08Id(
									CodeValues.CODE_ID_CODE_PLACE_OF_BIRTH,
									xmnsDetail.getPlaceOfBirth());
							if (code != null) {
								xmnsDetail.setPlaceOfBirth(code
										.getCodeValueDesc());
							}
						}
					}
					/* Lưu bảng thống kê truyền nhận */
					this.saveOrUpRptData(Contants.URL_GET_XMNS_DETAIL,
							listXmnsDetails.size(), null);
				}
				response.setData(listXmnsDetails);
				response.setCode(Contants.CODE_SUCCESS);
				response.setMessage(Contants.MESSAGE_SUCCESS);
			} else {
				response.setCode(Contants.CODE_INPUT_FAILD);
				response.setMessage(Contants.MESSAGE_INPUT_NULL);
			}
		} catch (Exception e) {
			e.printStackTrace();
			response.setCode(Contants.CODE_THROW_EXCEPTION);
			response.setMessage(Contants.MESSAGE_EXCEPTION);
			/*
			 * Lưu dữ liệu nếu có Exception
			 */
			String keyId = "GXD_"
					+ new SimpleDateFormat("ddMMyyyy").format(new Date());
			this.saveException(Contants.TYPE_GXD, Contants.URL_GET_ABTC_DETAIL,
					personCode, keyId, response.toString(), e);
		}
		return response;
	}

	/*
	 * Lấy thông tin việt kiều hồi hương
	 */
	@GET
	@Produces("application/json")
	@Consumes("application/json")
	@Path("/getVkHhDetail/{personCode}")
	public Response<List<VkHhDetail>> getVkHhDetail(
			@PathParam("personCode") String personCode) {
		Response<List<VkHhDetail>> response = new Response<List<VkHhDetail>>();
		response.setCode(Contants.CODE_CONNECT_FAIL);
		response.setMessage(Contants.MESS_CONNECT_FAIL);
		List<VkHhDetail> listVkHhDetails = null;
		try {
			if (StringUtils.isNotBlank(personCode)) {

				listVkHhDetails = getRecordDetailService
						.getVkHhDetails(personCode);

				if (listVkHhDetails != null && listVkHhDetails.size() > 0) {
					/* Lưu bảng thống kê truyền nhận */
					this.saveOrUpRptData(Contants.URL_GET_VK_HH_DETAIL,
							listVkHhDetails.size(), null);
				}
				response.setData(listVkHhDetails);
				response.setCode(Contants.CODE_SUCCESS);
				response.setMessage(Contants.MESSAGE_SUCCESS);
			} else {
				response.setCode(Contants.CODE_INPUT_FAILD);
				response.setMessage(Contants.MESSAGE_INPUT_NULL);
			}
		} catch (Exception e) {
			e.printStackTrace();
			response.setCode(Contants.CODE_THROW_EXCEPTION);
			response.setMessage(Contants.MESSAGE_EXCEPTION);
			/*
			 * Lưu dữ liệu nếu có Exception
			 */
			String keyId = "GVHD_"
					+ new SimpleDateFormat("ddMMyyyy").format(new Date());
			this.saveException(Contants.TYPE_GVHD,
					Contants.URL_GET_VK_HH_DETAIL, personCode, keyId,
					response.toString(), e);
		}
		return response;
	}

	/*
	 * Lấy hồ sơ vi phạm
	 */
	@GET
	@Produces("application/json")
	@Consumes("application/json")
	@Path("/getHsVpDetail/{personCode}")
	public Response<List<HsVpDetail>> getHsVpDetail(
			@PathParam("personCode") String personCode) {
		Response<List<HsVpDetail>> response = new Response<List<HsVpDetail>>();
		response.setCode(Contants.CODE_CONNECT_FAIL);
		response.setMessage(Contants.MESS_CONNECT_FAIL);
		List<HsVpDetail> listHsVpDetails = null;
		try {
			if (StringUtils.isNotBlank(personCode)) {

				listHsVpDetails = getRecordDetailService
						.getHsVpDetails(personCode);

				if (listHsVpDetails != null && listHsVpDetails.size() > 0) {
					/* Lưu bảng thống kê truyền nhận */
					this.saveOrUpRptData(Contants.URL_GET_HS_VP_DETAIL,
							listHsVpDetails.size(), null);
				}
				response.setData(listHsVpDetails);
				response.setCode(Contants.CODE_SUCCESS);
				response.setMessage(Contants.MESSAGE_SUCCESS);
			} else {
				response.setCode(Contants.CODE_INPUT_FAILD);
				response.setMessage(Contants.MESSAGE_INPUT_NULL);
			}
		} catch (Exception e) {
			e.printStackTrace();
			response.setCode(Contants.CODE_THROW_EXCEPTION);
			response.setMessage(Contants.MESSAGE_EXCEPTION);
			/*
			 * Lưu dữ liệu nếu có Exception
			 */
			String keyId = "GHVD_"
					+ new SimpleDateFormat("ddMMyyyy").format(new Date());
			this.saveException(Contants.TYPE_GHVD,
					Contants.URL_GET_HS_VP_DETAIL, personCode, keyId,
					response.toString(), e);
		}
		return response;
	}

	/*
	 * Lấy cá nhân vi phạm
	 */
	@GET
	@Produces("application/json")
	@Consumes("application/json")
	@Path("/getCnVpDetail/{personCode}")
	public Response<List<CnVpDetail>> getCnVpDetail(
			@PathParam("personCode") String personCode) {
		Response<List<CnVpDetail>> response = new Response<List<CnVpDetail>>();
		response.setCode(Contants.CODE_CONNECT_FAIL);
		response.setMessage(Contants.MESS_CONNECT_FAIL);
		List<CnVpDetail> listCnVpDetails = null;
		try {
			if (StringUtils.isNotBlank(personCode)) {

				listCnVpDetails = getRecordDetailService
						.getCnVpDetails(personCode);

				if (listCnVpDetails != null && listCnVpDetails.size() > 0) {
					/* Lưu bảng thống kê truyền nhận */
					this.saveOrUpRptData(Contants.URL_GET_CN_VP_DETAIL,
							listCnVpDetails.size(), null);
				}
				response.setData(listCnVpDetails);
				response.setCode(Contants.CODE_SUCCESS);
				response.setMessage(Contants.MESSAGE_SUCCESS);
			} else {
				response.setCode(Contants.CODE_INPUT_FAILD);
				response.setMessage(Contants.MESSAGE_INPUT_NULL);
			}
		} catch (Exception e) {
			e.printStackTrace();
			response.setCode(Contants.CODE_THROW_EXCEPTION);
			response.setMessage(Contants.MESSAGE_EXCEPTION);
			/*
			 * Lưu dữ liệu nếu có Exception
			 */
			String keyId = "GCVD_"
					+ new SimpleDateFormat("ddMMyyyy").format(new Date());
			this.saveException(Contants.TYPE_GCVD,
					Contants.URL_GET_CN_VP_DETAIL, personCode, keyId,
					response.toString(), e);
		}
		return response;
	}

	/*
	 * lấy thông tin chi tiết đối tượng nghi trùng.
	 */
	@GET
	@Produces("application/json")
	@Consumes("application/json")
	@Path("/getDoiTuongDetail/{code}")
	public Response<List<DoiTuongDetail>> getDoiTuongDetail(
			@PathParam("code") String code) {
		Response<List<DoiTuongDetail>> response = new Response<List<DoiTuongDetail>>();
		response.setCode(Contants.CODE_CONNECT_FAIL);
		response.setMessage(Contants.MESS_CONNECT_FAIL);
		List<DoiTuongDetail> listDoiTuongDetails = null;
		try {
			if (StringUtils.isNotBlank(code)) {

				listDoiTuongDetails = getRecordDetailService
						.getDoiTuongDetails(code);

				if (listDoiTuongDetails != null
						&& listDoiTuongDetails.size() > 0) {
					/* Lưu bảng thống kê truyền nhận */
					this.saveOrUpRptData(Contants.URL_GET_DOI_TUONG_DETAIL,
							listDoiTuongDetails.size(), null);
				}
				response.setData(listDoiTuongDetails);
				response.setCode(Contants.CODE_SUCCESS);
				response.setMessage(Contants.MESSAGE_SUCCESS);
			} else {
				response.setCode(Contants.CODE_INPUT_FAILD);
				response.setMessage(Contants.MESSAGE_INPUT_NULL);
			}
		} catch (Exception e) {
			e.printStackTrace();
			response.setCode(Contants.CODE_THROW_EXCEPTION);
			response.setMessage(Contants.MESSAGE_EXCEPTION);
			/*
			 * Lưu dữ liệu nếu có Exception
			 */
			String keyId = "GDTD_"
					+ new SimpleDateFormat("ddMMyyyy").format(new Date());
			this.saveException(Contants.TYPE_GDTD,
					Contants.URL_GET_DOI_TUONG_DETAIL, code, keyId,
					response.toString(), e);
		}
		return response;
	}

	/*
	 * Lấy lịch sử cấp hộ chiếu.
	 */
	@POST
	@Produces("application/json")
	@Consumes("application/json")
	@Path("/getLSCapHoChieu")
	public Response<List<LSCapHoChieu>> getLSCapHoChieu(
			InfoGetDetailBuffPerson request) {
		Response<List<LSCapHoChieu>> response = new Response<List<LSCapHoChieu>>();
		response.setCode(Contants.CODE_CONNECT_FAIL);
		response.setMessage(Contants.MESS_CONNECT_FAIL);
		List<LSCapHoChieu> listLSCapHoChieus = null;
		Date now = new Date();
		try {
			if (!(StringUtils.isBlank(request.getMaCaNhan()) && StringUtils
					.isBlank(request.getPersonCode()))) {

				if (StringUtils.isNotBlank(request.getMaCaNhan())) {
					listLSCapHoChieus = getRecordDetailService
							.getLsCapHoChieus(request.getMaCaNhan());
				}

				if (listLSCapHoChieus != null && listLSCapHoChieus.size() > 0) {
					for (LSCapHoChieu lsCapHoChieu : listLSCapHoChieus) {
						CodeValues code = null;
						if (StringUtils.isNotBlank(lsCapHoChieu
								.getPlaceOfBirth())) {
							code = codesService.findByCodeIdAndA08Id(
									CodeValues.CODE_ID_CODE_PLACE_OF_BIRTH,
									lsCapHoChieu.getPlaceOfBirth());
							if (code != null) {
								lsCapHoChieu.setPlaceOfBirth(code
										.getCodeValueDesc());
							}
						}
						if (StringUtils.isNotBlank(lsCapHoChieu
								.getResidentPlaceName())) {
							code = codesService.findByCodeIdAndA08Id(
									CodeValues.CODE_ID_DISTRICT,
									lsCapHoChieu.getResidentPlaceName());
							if (code != null) {
								lsCapHoChieu.setResidentPlaceName(code
										.getCodeValueDesc());
							}
						}
						if (StringUtils.isNotBlank(lsCapHoChieu
								.getResidentAreaName())) {
							code = codesService.findByCodeIdAndA08Id(
									CodeValues.CODE_ID_TOWN_VILLAGE,
									lsCapHoChieu.getResidentAreaName());
							if (code != null) {
								lsCapHoChieu.setResidentAreaName(code
										.getCodeValueDesc());
							}
						}
						if (StringUtils.isNotBlank(lsCapHoChieu.getPpStatus())) {
							switch (lsCapHoChieu.getPpStatus()) {
							case "1":
								lsCapHoChieu
										.setPpStatus(NicDocumentData.DOCUMENT_DATA_STATUS_ISSUANCE);
								break;
							case "0":
								lsCapHoChieu
										.setPpStatus(NicDocumentData.DOCUMENT_DATA_STATUS_CANCELLED);
								break;
							}
						}
						if (StringUtils.isNotBlank(lsCapHoChieu
								.getPpDateOfExpiry())) {
							if (lsCapHoChieu
									.getPpStatus()
									.equals(NicDocumentData.DOCUMENT_DATA_STATUS_ISSUANCE)
									&& DateUtil.strToDate(
											lsCapHoChieu.getPpDateOfExpiry(),
											DateUtil.FORMAT_YYYYMMDD).getTime() < now
											.getTime()) {
								lsCapHoChieu
										.setPpStatus(NicDocumentData.DOCUMENT_DATA_STATUS_EXPIRED);
							}
						}
						if (StringUtils.isNotBlank(lsCapHoChieu.getStatus())
								&& StringUtils.isNotBlank(lsCapHoChieu
										.getBuocXl())) {
							lsCapHoChieu.setStatus(convertTranStatusA08(
									lsCapHoChieu.getBuocXl(),
									lsCapHoChieu.getStatus()));
						}
					}
				}

				if (StringUtils.isNotBlank(request.getPersonCode())) {

					/* Lấy dữ liệu lịch sử cấp hộ chiếu trên local */
					if (listLSCapHoChieus == null) {
						listLSCapHoChieus = new ArrayList<LSCapHoChieu>();
					}

					List<EppPerson> listPerson = eppPersonService
							.findListPersonByOrgPersonCode(request
									.getPersonCode());

					if (listPerson != null && listPerson.size() > 0) {
						List<NicTransaction> listNicTran = nicTransactionService
								.findTranByListPersonCode(listPerson);
						if (listNicTran != null && listNicTran.size() > 0) {
							for (NicTransaction nicTran : listNicTran) {
								if (listLSCapHoChieus.size() > 0) {
									boolean check = false;
									for (LSCapHoChieu lsCapHoChieu : listLSCapHoChieus) {
										if (nicTran.getTransactionId()
												.equals(lsCapHoChieu
														.getTransactionId())) {
											check = true;
											break;
										}
									}
									if (check) {
										continue;
									}
								}
//								if (nicTran.getTransactionId().equals(
//										request.getTransactionId())) {
//									continue;
//								}
								List<NicDocumentData> listNicDoc = documentDataService
										.findAllDocByListTranId(nicTran
												.getTransactionId().split(","));
								if (listNicDoc != null && listNicDoc.size() > 0) {
									for (NicDocumentData nicDoc : listNicDoc) {
										LSCapHoChieu ls = new LSCapHoChieu();
										ls = this.createLSCapHoChieu(ls, nicTran, listPerson);
										ls.setPrintName(nicDoc.getPrintName());
										ls.setPassportNo(nicDoc.getId()
												.getPassportNo());
										if (nicDoc.getDateOfIssue() != null) {
											ls.setPpDateOfIssue(DateUtil.parseDate(
													nicDoc.getDateOfIssue(),
													DateUtil.FORMAT_YYYYMMDD));
										}
										ls.setPpStatus(nicDoc.getStatus());
										if (nicDoc.getDateOfExpiry() != null) {
											ls.setPpDateOfExpiry(DateUtil.parseDate(
													nicDoc.getDateOfExpiry(),
													DateUtil.FORMAT_YYYYMMDD));
											if (!nicDoc
													.getStatus()
													.equals(NicDocumentData.DOCUMENT_DATA_STATUS_CANCELLED)
													&& nicDoc.getDateOfExpiry()
															.getTime() < now
															.getTime()) {
												ls.setPpStatus(NicDocumentData.DOCUMENT_DATA_STATUS_EXPIRED);
											}
										}
										ls.setPpPlaceOfIssue(this.getSiteName(nicDoc.getPlaceOfIssueCode()));
										ls.setPpSerialNo(nicDoc.getSerialNo());
										ls.setPpIcaoLine1(nicDoc.getIcaoLine1());
										ls.setPpIcaoLine2(nicDoc.getIcaoLine2());
										ls.setReceiveBy(nicDoc.getReceiveBy());
										ls.setIssueBy(nicDoc.getIssueBy());
										if (nicDoc.getIssueDatetime() != null) {
											ls.setIssueDate(DateUtil.parseDate(
													nicDoc.getIssueDatetime(),
													DateUtil.FORMAT_YYYYMMDD));
										}
										if (nicDoc.getPrintDate() != null) {
											ls.setPrintDate(DateUtil.parseDate(
													nicDoc.getPrintDate(),
													DateUtil.FORMAT_YYYYMMDD));
										}
										ls.setCancelBy(nicDoc.getCancelBy());
										ls.setCancelDate(nicDoc
												.getCancelDatetime() != null ? DateUtil.parseDate(
												nicDoc.getCancelDatetime(),
												DateUtil.FORMAT_YYYYMMDD)
												: null);
										ls.setCancelReason(nicDoc
												.getCancelReason());
										ls.setCancelType(nicDoc.getCancelType());
										listLSCapHoChieus.add(ls);
									}
								} else {
									LSCapHoChieu ls = new LSCapHoChieu();
									ls = this.createLSCapHoChieu(ls, nicTran, listPerson);
									listLSCapHoChieus.add(ls);
								}
							}
						}
					}
				}

				if (listLSCapHoChieus != null && listLSCapHoChieus.size() > 0) {
					/* Lưu bảng thống kê truyền nhận */
					this.saveOrUpRptData(Contants.URL_GET_LS_CAP_HO_CHIEU,
							listLSCapHoChieus.size(), null);
				}

				response.setData(listLSCapHoChieus);
				response.setCode(Contants.CODE_SUCCESS);
				response.setMessage(Contants.MESSAGE_SUCCESS);
			} else {
				response.setCode(Contants.CODE_INPUT_FAILD);
				response.setMessage(Contants.MESSAGE_INPUT_NULL);
			}
		} catch (Exception e) {
			e.printStackTrace();
			response.setCode(Contants.CODE_THROW_EXCEPTION);
			response.setMessage(Contants.MESSAGE_EXCEPTION);
			/*
			 * Lưu dữ liệu nếu có Exception
			 */
			String key = Contants.TYPE_GLSCHC + "_"
					+ new SimpleDateFormat("ddMMyyyy").format(new Date());
			String dataRequest = "";
			try {
				dataRequest = new ObjectMapper().writeValueAsString(request);
			} catch (Exception e2) {
				logger.error(e.getMessage());
			}
			this.saveException(Contants.TYPE_GLSCHC,
					Contants.URL_GET_LS_CAP_HO_CHIEU, dataRequest, key,
					response.toString(), e);
		}
		return response;
	}

	private LSCapHoChieu createLSCapHoChieu(LSCapHoChieu ls,
			NicTransaction nicTran, List<EppPerson> listPerson) throws Exception{
		ls.setApxPersonCode(nicTran.getPersonCode());
		ls.setTransactionId(nicTran.getTransactionId());
		ls.setReceiptNo(nicTran.getRecieptNo());
		try {
			if (nicTran.getIsEpassport()) {
				ls.setIsEpassport("Y");
			} else if (!nicTran.getIsEpassport()) {
				ls.setIsEpassport("N");
			}
		} catch (Exception e) {
			ls.setIsEpassport("N");
		}
		ls.setPriority(nicTran.getPriority().toString()
				.trim());
		ls.setPrevPassportNo(nicTran
				.getPrevPassportNo());
		if (nicTran.getPrevDateOfIssue() != null) {
			ls.setPrevDateOfIssue(DateUtil.parseDate(
					nicTran.getPrevDateOfIssue(),
					DateUtil.FORMAT_YYYYMMDD));
		}
		ls.setType(nicTran.getTransactionType());
		ls.setDescription(nicTran.getDescription());
		// ls.setOfficeName(nicTran);
		ls.setStatus(nicTran.getTransactionStatus());
		ls.setNote(nicTran.getNote());

		BaseModelList<NicTransactionAttachment> basePhotoCapture = nicTransactionAttachmentService
				.findNicTransactionAttachments(
						nicTran.getTransactionId(),
						Contants.DOC_TYPE_PHOTO_CAPTURE,
						null);
		if (!basePhotoCapture.isError()
				|| basePhotoCapture.getMessage() != null) {
			throw new Exception(
					basePhotoCapture.getMessage());
		}
		if (basePhotoCapture.getListModel() != null
				&& basePhotoCapture.getListModel()
						.size() > 0) {
			NicTransactionAttachment photoCapture = basePhotoCapture
					.getListModel().get(0);
			ls.setPhoto(Base64.encode(photoCapture
					.getDocData()));
		}
		BaseModelList<NicTransactionAttachment> baseScanDoc = nicTransactionAttachmentService
				.findNicTransactionAttachments(
						nicTran.getTransactionId(),
						Contants.DOC_TYPE_SCAN_DOCUMENT,
						null);
		if (!baseScanDoc.isError()
				|| baseScanDoc.getMessage() != null) {
			throw new Exception(
					baseScanDoc.getMessage());
		}
		if (baseScanDoc.getListModel() != null
				&& baseScanDoc.getListModel().size() > 0) {
			if (ls.getListAttachments() == null) {
				ls.setListAttachments(new ArrayList<DecAttachment>());
			}
			for (NicTransactionAttachment scanDoc : baseScanDoc
					.getListModel()) {
				DecAttachment dec = new DecAttachment();
				dec.setAnhToKhai(Base64.encode(scanDoc
						.getDocData()));
				dec.setMaHoSo(scanDoc
						.getTransactionId());
				dec.setMaLoaiToKhai("HC");
				dec.setSoTrang(scanDoc.getSerialNo());
				ls.getListAttachments().add(dec);
			}

		}
		if (StringUtils.isNotBlank(nicTran
				.getArchiveCode())) {
			EppArchive eppArchive = eppArchiveService
					.findByTransactionId(nicTran
							.getTransactionId());
			if (eppArchive != null) {
				ls.setPackNo(eppArchive.getPackNo());
				ls.setPageNo(eppArchive.getPageCount());
			}
		}
		ls.setPpType(nicTran.getPassportType());
		ls.setPid(nicTran.getNin());

		NicRegistrationData nicReg = nicTran
				.getNicRegistrationData();
		if (nicReg != null) {
			if (nicReg.getNation() != null) {
				ls.setEthnic(nicReg.getNation());
			} else {
				for (EppPerson person : listPerson) {
					if (person.getPersonCode().equals(
							nicTran.getPersonCode())) {
						ls.setEthnic(person.getEthnic());
					}
				}
			}

			ls.setDateOfIssuePid(nicReg.getDateNin() != null ? DateUtil
					.parseDate(nicReg.getDateNin(),
							DateUtil.FORMAT_YYYYMMDD)
					: null);
			ls.setCreatedBy(nicReg.getCreateByName());
			ls.setPhoneNo(nicReg.getContactNo());
			if (StringUtils.isNotBlank(nicReg
					.getAddressTempDistrict())) {
				String qh_temp = codesService
						.getCodeValueDescByIdName(
								"TOWN_VILLAGE",
								nicReg.getAddressTempDistrict(),
								"");
				ls.setTmpAreaName(qh_temp);
			}
			if (StringUtils.isNotBlank(nicReg
					.getAddressTempProvince())) {
				String th_temp = codesService
						.getCodeValueDescByIdName(
								"DISTRICT",
								nicReg.getAddressTempProvince(),
								"");
				ls.setTmpPlaceName(th_temp);
			}
			if (StringUtils.isNotBlank(nicReg
					.getAddressLine3())) {
				String qh_temp = codesService
						.getCodeValueDescByIdName(
								"TOWN_VILLAGE",
								nicReg.getAddressLine3(),
								"");
				ls.setResidentAreaName(qh_temp);
			}
			if (StringUtils.isNotBlank(nicReg
					.getAddressLine4())) {
				String th_temp = codesService
						.getCodeValueDescByIdName(
								"DISTRICT",
								nicReg.getAddressLine4(),
								"");
				ls.setResidentPlaceName(th_temp);
			}

			ls.setResidentAddress(nicReg
					.getAddressLine1());

			ls.setTmpAddress(nicReg
					.getAddressTempDetail());
			ls.setOccupation(nicReg.getJob());
			ls.setOfficeInfo(nicReg.getOfficeAddress());
			if (StringUtils.isNotBlank(nicReg
					.getAddressNin())) {
				CodeValues codeValue = codesService
						.findByStringCodeId(
								CodeValues.CODE_ID_CODE_ID_PLACE,
								nicReg.getAddressNin());
				if (codeValue != null) {
					ls.setPlaceOfIssuePid(codeValue
							.getCodeValueDesc());
				}
			}
			if (StringUtils.isNotBlank(nicReg
					.getPlaceOfBirth())) {
				CodeValues codeValue = codesService
						.findByStringCodeId(
								CodeValues.CODE_ID_CODE_PLACE_OF_BIRTH,
								nicReg.getPlaceOfBirth());
				if (codeValue != null) {
					ls.setPlaceOfBirth(codeValue
							.getCodeValueDesc());
				}
			}
			if (nicReg.getDateOfBirth() != null) {
				ls.setDateOfBirth(DateUtil.parseDate(
						nicReg.getDateOfBirth(),
						DateUtil.FORMAT_YYYYMMDD));
			}
			ls.setGender(nicReg.getGender());
			ls.setFullName(HelperClass.createFullName(
					nicReg.getSurnameFull(),
					nicReg.getMiddlenameFull(),
					nicReg.getFirstnameFull()));

			List<PersonFamily> listFamily = new ArrayList<PersonFamily>();
			PersonFamily fatherData = new PersonFamily();
			if (StringUtils.isNotEmpty(nicReg
					.getFatherFullname())) {
				fatherData.setName(nicReg
						.getFatherFullname());
				fatherData.setGender("M");
				fatherData.setRelationship("bố");
				fatherData.setTypeDob(nicReg
						.getFatherDefDateOfBirth());
				if (nicReg.getFatherLost() != null) {
					fatherData.setLost(nicReg
							.getFatherLost());
				}
				if (nicReg.getFatherDateOfBirth() != null) {
					String dob = service.perso.util.HelperClass
							.convertDateToString(
									nicReg.getFatherDateOfBirth(),
									DateUtil.FORMAT_YYYYMMDD);
					fatherData
							.setDateOfBirth(HelperClass.loadDateOfBirths(
									dob,
									nicReg.getFatherDefDateOfBirth()));
				}
				fatherData.setTransactionId(nicTran
						.getTransactionId());
				listFamily.add(fatherData);
			}
			PersonFamily motherData = new PersonFamily();
			if (StringUtils.isNotEmpty(nicReg
					.getMotherFullname())) {
				motherData.setName(nicReg
						.getMotherFullname());
				motherData.setGender("F");
				motherData.setRelationship("mẹ");
				motherData.setTypeDob(nicReg
						.getMotherDefDateOfBirth());
				if (nicReg.getMotherLost() != null) {
					motherData.setLost(nicReg
							.getMotherLost());
				}
				if (nicReg.getMotherDateOfBirth() != null) {
					String dob = service.perso.util.HelperClass
							.convertDateToString(
									nicReg.getMotherDateOfBirth(),
									DateUtil.FORMAT_YYYYMMDD);
					motherData
							.setDateOfBirth(HelperClass.loadDateOfBirths(
									dob,
									nicReg.getMotherDefDateOfBirth()));
				}
				motherData.setTransactionId(nicTran
						.getTransactionId());
				listFamily.add(motherData);
			}
			PersonFamily spouseData = new PersonFamily();
			if (StringUtils.isNotEmpty(nicReg
					.getSpouseFullname())) {
				spouseData.setName(nicReg
						.getSpouseFullname());
				spouseData.setGender(nicReg.getGender()
						.equals("M") ? "F" : "M");
				spouseData.setRelationship(nicReg
						.getGender().equals("M") ? "vợ"
						: "chồng");
				spouseData.setTypeDob(nicReg
						.getSpouseDefDateOfBirth());
				spouseData.setLost("N");
				if (nicReg.getSpouseDateOfBirth() != null) {
					String dob = service.perso.util.HelperClass
							.convertDateToString(
									nicReg.getSpouseDateOfBirth(),
									DateUtil.FORMAT_YYYYMMDD);
					spouseData
							.setDateOfBirth(HelperClass.loadDateOfBirths(
									dob,
									nicReg.getSpouseDefDateOfBirth()));
				}
				spouseData.setTransactionId(nicTran
						.getTransactionId());
				listFamily.add(spouseData);
			}
			ls.setFamily(listFamily);

		}
		BaseModelList<EppListHandoverDetail> baseGetHanDetail = listHandoverDetailService
				.getPackageNameByTransactionId(nicTran
						.getTransactionId());
		if (!baseGetHanDetail.isError()
				|| baseGetHanDetail.getMessage() != null) {
			throw new Exception(
					baseGetHanDetail.getMessage());
		}
		List<EppListHandoverDetail> listHanDetail = baseGetHanDetail
				.getListModel();
		NicListHandover listHanA = null;
		NicListHandover listHanB = null;
		NicListHandover listHanC = null;
		EppListHandoverDetail hanDetailB = null;
		if (listHanDetail != null
				&& listHanDetail.size() > 0) {
			for (EppListHandoverDetail hanDetail : listHanDetail) {
				if (hanDetail.getTypeList().equals("A")) {
					listHanA = hanDetail
							.getPackageHandover();
					continue;
				}
				if (hanDetail.getTypeList().equals("B")) {
					listHanB = hanDetail
							.getPackageHandover();
					hanDetailB = hanDetail;
					continue;
				}
				if (hanDetail.getTypeList().equals("C")) {
					listHanC = hanDetail
							.getPackageHandover();
				}
			}
		}
		if (listHanA != null) {
			ls.setaListCode(listHanA.getId()
					.getPackageId());
			ls.setaListCrtBy(listHanA.getCreateBy());
			if (listHanA.getCreateDate() != null) {
				ls.setaListCrtDate(DateUtil.parseDate(
						listHanA.getCreateDate(),
						DateUtil.FORMAT_YYYYMMDD));
			}
		}
		if (listHanB != null) {
			ls.setProposalBy(listHanB.getProposalName());
			if (listHanB.getProposalDate() != null) {
				ls.setProposalDate(DateUtil.parseDate(
						listHanB.getProposalDate(),
						DateUtil.FORMAT_YYYYMMDD));
			}
			ls.setProposalType(hanDetailB
					.getProposalStage());
			ls.setProposalContent(hanDetailB
					.getProposalNote());
			ls.setProposalAprvrName(listHanB
					.getApproveName());
			if (listHanB.getApproveDate() != null) {
				ls.setProposalAprvrDate(DateUtil.parseDate(
						listHanB.getApproveDate(),
						DateUtil.FORMAT_YYYYMMDD));
			}
			ls.setProposalAprvrContent(hanDetailB
					.getApproveNote());
			ls.setProposalAprvrPstn(listHanB
					.getApprovePosition());
			// ls.setDeliveryNote();
			if (listHanB.getDateOfDelivery() != null) {
				ls.setDateOfDelivery(DateUtil.parseDate(
						listHanB.getDateOfDelivery(),
						DateUtil.FORMAT_YYYYMMDD));
			}
			// ls.setArchiveCode(listHanB.getArchiveCode());
			// ls.setRecipient();
			ls.setbListCode(listHanB.getId()
					.getPackageId());
			if (listHanB.getCreateDate() != null) {
				ls.setbListCrtDate(DateUtil.parseDate(
						listHanB.getCreateDate(),
						DateUtil.FORMAT_YYYYMMDD));
			}
			ls.setbListCrtBy(listHanB.getProposalName());
		}
		if (listHanC != null) {
			ls.setcListCode(listHanC.getId()
					.getPackageId());
			ls.setcListCrtBy(listHanC.getCreateBy());
			if (listHanC.getCreateDate() != null) {
				ls.setcListCrtDate(DateUtil.parseDate(
						listHanC.getCreateDate(),
						DateUtil.FORMAT_YYYYMMDD));
			}
		}

		List<NicUploadJob> lJobs = uploadJobService
				.findAllByTransactionId(nicTran
						.getTransactionId());
		if (lJobs != null && lJobs.size() > 0) {
			NicUploadJob nicUJ = lJobs.get(0);
			ls.setBlInvestBy(nicUJ.getUserApproverObj());
			if (nicUJ.getDateApproveInvestigation() != null) {
				ls.setBlInvestDate(DateUtil.parseDate(
						nicUJ.getDateApproveInvestigation(),
						DateUtil.FORMAT_YYYYMMDD));
			}
		}
		ls.setApplicant(nicTran.getApplicant());
		if (nicTran.getCreateDatetime() != null) {
			ls.setCreatedDate(DateUtil.parseDate(
					nicTran.getCreateDatetime(),
					DateUtil.FORMAT_YYYYMMDD));
		}
		// ls.setDocumentNo();
		// ls.setDocumentDate(documentDate);
		// ls.setSentOfficeName(sentOfficeName);
		// ls.setSentOfficeAddr(sentOfficeAddr);
		return ls;
	}

	/*
	 * tra cứu đối tượng nghi trùng.
	 */
	@POST
	@Produces("application/json")
	@Consumes("application/json")
	@Path("/traCuuDoiTuong")
	public Response<List<DoiTuong>> traCuuDoiTuong(DoiTuongQuery doiTuongQuery) {
		Date begin = new Date();
		Response<List<DoiTuong>> response = new Response<List<DoiTuong>>();
		response.setCode(Contants.CODE_CONNECT_FAIL);
		response.setMessage(Contants.MESS_CONNECT_FAIL);
		List<DoiTuong> listDoiTuongs = null;
		int count = 0;
		String dataRequest = "";
		try {
			dataRequest = new ObjectMapper().writeValueAsString(doiTuongQuery);
		} catch (Exception e2) {
			logger.error(e2.getMessage());
		}
		try {
			if (doiTuongQuery != null) {

				listDoiTuongs = getRecordDetailService
						.getDoiTuongs(doiTuongQuery.getHoTen(),
								doiTuongQuery.getNgaySinh(),
								doiTuongQuery.getKieuNgaySinh(),
								doiTuongQuery.getQuocTich(),
								doiTuongQuery.getGioiTinh(),
								doiTuongQuery.getSoHoChieu(),
								doiTuongQuery.getSoCMND());
				/* lấy tên quốc tịch */
				if (listDoiTuongs != null && listDoiTuongs.size() > 0) {
					count = listDoiTuongs.size();
					CodeValues codeValues = null;
					for (DoiTuong doiTuong : listDoiTuongs) {
						if (doiTuong.getCurrentNationalityId() != null) {
							codeValues = codesService
									.getCountryByA08Id(doiTuong
											.getCurrentNationalityId()
											.toString().trim());
							if (codeValues != null) {
								doiTuong.setCurrentNationalityName(codeValues
										.getCodeValueDesc());
							}
						}
						if (doiTuong.getOriginNationalityId() != null) {
							codeValues = codesService
									.getCountryByA08Id(doiTuong
											.getOriginNationalityId()
											.toString().trim());
							if (codeValues != null) {
								doiTuong.setOriginNationalityName(codeValues
										.getCodeValueDesc());
							}
						}
					}
					/* Lưu bảng thống kê truyền nhận */
					this.saveOrUpRptData(Contants.URL_TRA_CUU_DOI_TUONG,
							listDoiTuongs.size(), null);
				}

				response.setData(listDoiTuongs);
				response.setCode(Contants.CODE_SUCCESS);
				response.setMessage(Contants.MESSAGE_SUCCESS);
			} else {
				response.setCode(Contants.CODE_INPUT_FAILD);
				response.setMessage(Contants.MESSAGE_INPUT_NULL);
			}
		} catch (Exception e) {
			e.printStackTrace();
			response.setCode(Contants.CODE_THROW_EXCEPTION);
			response.setMessage(Contants.MESSAGE_EXCEPTION);

			/*
			 * Lưu dữ liệu nếu có Exception
			 */
			String key = Contants.TYPE_TCDT + "_"
					+ new SimpleDateFormat("ddMMyyyy").format(new Date());
			this.saveException(Contants.TYPE_TCDT,
					Contants.URL_TRA_CUU_DOI_TUONG, dataRequest, key,
					response.toString(), e);
		}
		logger.error(dataRequest
				+ "== TIME_TCDT: "
				+ String.valueOf((new Date().getTime() - begin.getTime()) / 1000)
				+ " seconds" + "== LIST_SIZE: " + count);
		return response;
	}

	/*
	 * tra cứu nghi trùng.
	 */
	@POST
	@Produces("application/json")
	@Consumes("application/json")
	@Path("/downloadBuff")
	public Response<ResponseDownloadBuff> downloadBuff(DownloadBuffInput input) {
		Response<ResponseDownloadBuff> response = new Response<ResponseDownloadBuff>();
		response.setCode(Contants.CODE_CONNECT_FAIL);
		response.setMessage(Contants.MESS_CONNECT_FAIL);
		ResponseDownloadBuff dataResponse = null;
		List<DataPersonBuff> listDataPersonBuffs = null;
		try {
			if (input != null) {
				dataResponse = new ResponseDownloadBuff();
				dataResponse.setTransactionCode(input.getTransactionId());
				/* Kiểm tra hàng đợi */
				SyncQueueJob syncQueueJob = queueJobService
						.findSyncQueueJobByManyObjType(
								input.getTransactionId(), new String[] {
										Contants.QUEUE_OBJ_TYPE_BF_CPD,
										Contants.QUEUE_OBJ_TYPE_BF_AFIS },
								input.getSiteCode(),
								Contants.CODE_STATUS_QUEUE_PENDING);

				if (syncQueueJob != null) {
					if (syncQueueJob.getObjectType().equals(
							Contants.QUEUE_OBJ_TYPE_BF_CPD)) {
						dataResponse.setBuffType("CPD");
					} else if (syncQueueJob.getObjectType().equals(
							Contants.QUEUE_OBJ_TYPE_BF_AFIS)) {
						dataResponse.setBuffType("AFIS");
					}
					/* Lấy dữ liệu nghi trùng trong bảng cá nhân nghi trùng */
					List<BufEppPersonInvestigation> listBuf = bufPersonInvestigationService
							.findListByTranMasterId(syncQueueJob.getObjectId());
					dataResponse.setTransactionCode(syncQueueJob.getObjectId());
					dataResponse.setIdQueue(syncQueueJob.getId());
					listDataPersonBuffs = new ArrayList<DataPersonBuff>();
					if (listBuf.size() > 0) {
						for (BufEppPersonInvestigation bufPerson : listBuf) {
							DataPersonBuff dataPersonBuff = new DataPersonBuff();
							// dataPersonBuff.setIdQueue(syncQueueJob.getId());
							dataPersonBuff.setApxPersonCcode(bufPerson
									.getPersonCode());
							dataPersonBuff.setOrgPerson(bufPerson
									.getOrgPerson());
							dataPersonBuff.setDateOfBirth(bufPerson
									.getDateOfBirth());
							dataPersonBuff.setEthNic(bufPerson.getEthnic());
							dataPersonBuff.setFatherName(bufPerson
									.getFatherName());
							dataPersonBuff.setFatherNationality(bufPerson
									.getFatherNationality());
							dataPersonBuff.setFatherOccupation(bufPerson
									.getFatherOccupation());
							dataPersonBuff.setGender(bufPerson.getGender());
							dataPersonBuff.setIdNumber(bufPerson.getIdNumber());
							dataPersonBuff.setMaCaNhan(bufPerson.getMaCaNhan());
							if (bufPerson.getMatchPoint() != null) {
								try {
									Double mp = Double.valueOf(bufPerson
											.getMatchPoint());
									dataPersonBuff.setMatchPoint(mp);
								} catch (Exception e) {
									throw e;
								}
							}
							dataPersonBuff.setMotherName(bufPerson
									.getMotherName());
							dataPersonBuff.setMotherNationality(bufPerson
									.getMotherNationality());
							dataPersonBuff.setMotherOccupation(bufPerson
									.getMotherOccupation());
							dataPersonBuff.setName(bufPerson.getName());
							dataPersonBuff.setNationalityName(bufPerson
									.getNationalityName());
							dataPersonBuff.setOccupation(bufPerson
									.getOccupation());
							dataPersonBuff.setOfficeInfo(bufPerson
									.getOfficeInfo());
							dataPersonBuff.setOtherName(bufPerson
									.getOtherName());
							dataPersonBuff.setPassportNo(bufPerson
									.getPassportNo());
							dataPersonBuff.setPlaceOfBirthName(bufPerson
									.getPlaceOfBirthName());
							dataPersonBuff.setReligion(bufPerson.getReligion());
							dataPersonBuff.setResidentAddress(bufPerson
									.getResidentAddress());
							dataPersonBuff.setResidentPlaceName(bufPerson
									.getResidentPlaceName());
							dataPersonBuff.setSearchName(bufPerson
									.getSearchName());
							if (bufPerson.getCreateDatetime() != null) {
								dataPersonBuff.setSearchTs(DateUtil.parseDate(
										bufPerson.getCreateDatetime(),
										DateUtil.FORMAT_YYYYMMDDHHMMSS));
							}
							dataPersonBuff.setSrc(bufPerson.getType());
							dataPersonBuff.setTempAddress(bufPerson
									.getTmpAddress());
							dataPersonBuff.setTransactionId(bufPerson
									.getTransactionId());
							dataPersonBuff.setTransactionMasterId(bufPerson
									.getTransactionMasterId());
							dataPersonBuff.setDataType(bufPerson.getDataType());
							if (StringUtils.isNotBlank(dataPersonBuff
									.getPassportNo())) {
								List<PassportStatus> listPassportStatus = null;
								if (StringUtils.isNotBlank(dataPersonBuff
										.getSrc())) {
									if (bufPerson.getSrc().equals("A08")) {
										listPassportStatus = getRecordDetailService
												.getListPassportStatus(dataPersonBuff
														.getPassportNo());
									} else if (bufPerson.getSrc().equals(
											"LOCAL")) {
										String[] listPassportNo = dataPersonBuff
												.getPassportNo().split(",");
										List<NicDocumentData> listDoc = documentDataService
												.findDocByListPassportNo(listPassportNo);
										if (listDoc != null
												&& listDoc.size() > 0) {
											listPassportStatus = new ArrayList<PassportStatus>();
											for (NicDocumentData nicDocumentData : listDoc) {
												PassportStatus passportStatus = new PassportStatus();
												passportStatus
														.setPassportNo(nicDocumentData
																.getId()
																.getPassportNo());
												passportStatus
														.setDateOfExpiry(DateUtil.parseDate(
																nicDocumentData
																		.getDateOfExpiry(),
																DateUtil.FORMAT_YYYYMMDD));
												passportStatus
														.setDateOfIssue(DateUtil.parseDate(
																nicDocumentData
																		.getDateOfIssue(),
																DateUtil.FORMAT_YYYYMMDD));
												passportStatus
														.setStatus(nicDocumentData
																.getStatus());
												if (nicDocumentData
														.getDateOfExpiry() != null
														&& nicDocumentData
																.getStatus()
																.equals(NicDocumentData.DOCUMENT_DATA_STATUS_ISSUANCE)
														&& nicDocumentData
																.getDateOfExpiry()
																.getTime() < new Date()
																.getTime()) {
													passportStatus
															.setStatus(NicDocumentData.DOCUMENT_DATA_STATUS_EXPIRED);
												}

												passportStatus
														.setCancelType(nicDocumentData
																.getCancelType());
												listPassportStatus
														.add(passportStatus);
											}
										}
									}
								}

								if (listPassportStatus != null
										&& listPassportStatus.size() > 0) {
									dataPersonBuff
											.setListPassportStatus(listPassportStatus);
								}
							}
							listDataPersonBuffs.add(dataPersonBuff);
						}
					}
					/* Cập nhật trạng thái bản ghi hàng đợi */
					syncQueueJob.setStatus(Contants.CODE_STATUS_QUEUE_DOING);
					queueJobService.saveOrUpdateSyncQueue(syncQueueJob);

					response.setCode(Contants.CODE_SUCCESS);
					response.setMessage(Contants.MESSAGE_SUCCESS);

				} else {
					response.setCode(Contants.CODE_DATA_NOT_FOUND);
					response.setMessage(Contants.RESPONSE_MESSAGE_NULL_DATA);
				}
				dataResponse.setListData(listDataPersonBuffs);
				response.setData(dataResponse);

				if (listDataPersonBuffs != null
						&& listDataPersonBuffs.size() > 0) {
					/* Lưu bảng thống kê truyền nhận */
					this.saveOrUpRptData(Contants.URL_TRA_CUU_DOI_TUONG,
							listDataPersonBuffs.size(), null);
				}

			} else {
				response.setCode(Contants.CODE_INPUT_FAILD);
				response.setMessage(Contants.MESSAGE_INPUT_NULL);
			}
		} catch (Exception e) {
			e.printStackTrace();
			response.setCode(Contants.CODE_THROW_EXCEPTION);
			response.setMessage(Contants.MESSAGE_EXCEPTION);
			/*
			 * Lưu dữ liệu nếu có Exception
			 */
			String key = Contants.TYPE_DB + "_"
					+ new SimpleDateFormat("ddMMyyyy").format(new Date());
			if (StringUtils.isNotBlank(input.getTransactionId())) {
				key = input.getTransactionId();
			}
			String dataRequest = "";
			try {
				dataRequest = new ObjectMapper().writeValueAsString(input);
			} catch (Exception e2) {
				logger.error(e.getMessage());
			}
			this.saveException(Contants.TYPE_DB, Contants.URL_DOWNLOAD_BUF,
					dataRequest, key, response.toString(), e);
		}
		return response;
	}

	/*
	 * lấy chi tiết ảnh.
	 */
	@POST
	@Produces("application/json")
	@Consumes("application/json")
	@Path("/getDetailBuffPerson")
	public Response<ObjDetailBuffPerson> getDetailBuffPerson(
			InfoGetDetailBuffPerson infoInput) {
		Response<ObjDetailBuffPerson> response = new Response<ObjDetailBuffPerson>();
		response.setCode(Contants.CODE_CONNECT_FAIL);
		response.setMessage(Contants.MESS_CONNECT_FAIL);
		ObjDetailBuffPerson bufDetail = null;
		try {
			if (infoInput != null) {
				if (StringUtils.isNotBlank(infoInput.getTransactionMasterId())) {
					/* Kiểm tra tồn tại của dữ liệu nghi trùng */
					if (StringUtils.isNotBlank(infoInput.getPersonCode())
							&& StringUtils.isNotBlank(infoInput.getMaCaNhan())
							&& infoInput.getMaCaNhan().equals(
									infoInput.getPersonCode())) {
						infoInput.setPersonCode(null);
					}
					BufEppPersonInvestigation bufPerson = bufPersonInvestigationService
							.findBufByTranMasterIdAndPersonCode(
									infoInput.getTransactionMasterId(),
									infoInput.getPersonCode(),
									infoInput.getTransactionId(),
									infoInput.getMaCaNhan());
					if (bufPerson != null) {
						bufDetail = new ObjDetailBuffPerson();
						bufDetail
								.setTransactionId(bufPerson.getTransactionId());
						bufDetail.setPersonCode(bufPerson.getPersonCode());

						if (StringUtils.isNotBlank(bufPerson.getSrc())) {
							if (bufPerson.getSrc().equals(
									Contants.BUF_PERSON_SRC_LOCAL)) {
								/* Lấy dữ liệu trên local */
								List<NicTransactionAttachment> listNicTranAttach = nicTransactionAttachmentService
										.findAttachmentsByTranId(bufDetail
												.getTransactionId());
								for (NicTransactionAttachment nicTranAttach : listNicTranAttach) {
									if (nicTranAttach.getDocType().equals(
											Contants.DOC_TYPE_PHOTO_CAPTURE)) {
										bufDetail
												.setPhoto(this
														.convertByteToBase64(nicTranAttach
																.getDocData()));
										continue;
									} else if (nicTranAttach
											.getDocType()
											.equals(Contants.DOC_TYPE_FINGERPRINT)) {
										/* lấy dữ liệu vân tay */
										if (nicTranAttach.getSerialNo().equals(
												"01")) {
											bufDetail
													.setFP_01(this
															.convertByteToBase64(nicTranAttach
																	.getDocData()));
											continue;
										}
										if (nicTranAttach.getSerialNo().equals(
												"02")) {
											bufDetail
													.setFP_02(this
															.convertByteToBase64(nicTranAttach
																	.getDocData()));
											continue;
										}
										if (nicTranAttach.getSerialNo().equals(
												"03")) {
											bufDetail
													.setFP_03(this
															.convertByteToBase64(nicTranAttach
																	.getDocData()));
											continue;
										}
										if (nicTranAttach.getSerialNo().equals(
												"04")) {
											bufDetail
													.setFP_04(this
															.convertByteToBase64(nicTranAttach
																	.getDocData()));
											continue;
										}
										if (nicTranAttach.getSerialNo().equals(
												"05")) {
											bufDetail
													.setFP_05(this
															.convertByteToBase64(nicTranAttach
																	.getDocData()));
											continue;
										}
										if (nicTranAttach.getSerialNo().equals(
												"06")) {
											bufDetail
													.setFP_06(this
															.convertByteToBase64(nicTranAttach
																	.getDocData()));
											continue;
										}
										if (nicTranAttach.getSerialNo().equals(
												"07")) {
											bufDetail
													.setFP_07(this
															.convertByteToBase64(nicTranAttach
																	.getDocData()));
											continue;
										}
										if (nicTranAttach.getSerialNo().equals(
												"08")) {
											bufDetail
													.setFP_08(this
															.convertByteToBase64(nicTranAttach
																	.getDocData()));
											continue;
										}
										if (nicTranAttach.getSerialNo().equals(
												"09")) {
											bufDetail
													.setFP_09(this
															.convertByteToBase64(nicTranAttach
																	.getDocData()));
											continue;
										}
										if (nicTranAttach.getSerialNo().equals(
												"10")) {
											bufDetail
													.setFP_10(this
															.convertByteToBase64(nicTranAttach
																	.getDocData()));
										}
									}
								}
							} else if (bufPerson.getSrc().equals(
									Contants.BUF_PERSON_SRC_A08)) {
								/* lấy dữ liệu A08 nếu có */
								if (bufPerson.getPhoto() != null) {
									bufDetail.setPhoto(this
											.convertByteToBase64(bufPerson
													.getPhoto()));
								}
							}
						}

						/* lấy bộ đếm dữ liệu nghi trùng từ A08 */
						/*
						 * CountDetailInfo countDetail = new CountDetailInfo();
						 * if (StringUtils.isNotBlank(infoInput.getMaCaNhan()))
						 * { countDetail.setAbtcCount(getRecordDetailService
						 * .checkCaNhanHsXnc(infoInput.getMaCaNhan(),
						 * Contants.TYPE_COUNT_ABTC)); countDetail
						 * .setGiayPhepXNCCount(getRecordDetailService
						 * .checkCaNhanHsXnc( infoInput.getMaCaNhan(),
						 * Contants.TYPE_COUNT_GP));
						 * countDetail.setHoiHuongCount(getRecordDetailService
						 * .checkCaNhanHsXnc(infoInput.getMaCaNhan(),
						 * Contants.TYPE_COUNT_HH));
						 * countDetail.setHsViPhamCount(getRecordDetailService
						 * .checkCaNhanHsXnc(infoInput.getMaCaNhan(),
						 * Contants.TYPE_COUNT_VP));
						 * countDetail.setThoiQTCount(getRecordDetailService
						 * .checkCaNhanHsXnc(infoInput.getMaCaNhan(),
						 * Contants.TYPE_COUNT_TQT));
						 * 
						 * countDetail
						 * .setThongTinCapHCCount(getRecordDetailService
						 * .checkCaNhanHsXnc( infoInput.getMaCaNhan(),
						 * Contants.TYPE_COUNT_HC));
						 * countDetail.setTroLaiQTCount(getRecordDetailService
						 * .checkCaNhanHsXnc(infoInput.getMaCaNhan(),
						 * Contants.TYPE_COUNT_TLQT)); countDetail
						 * .setVkHoiHuongCount(getRecordDetailService
						 * .checkCaNhanHsXnc( infoInput.getMaCaNhan(),
						 * Contants.TYPE_COUNT_VH)); countDetail
						 * .setXacMinhNSCount(getRecordDetailService
						 * .checkCaNhanHsXnc( infoInput.getMaCaNhan(),
						 * Contants.TYPE_COUNT_NS)); countDetail
						 * .setLichSuXuatNhapCanhCount(getRecordDetailService
						 * .checkCaNhanHsXnc( infoInput.getMaCaNhan(),
						 * Contants.TYPE_COUNT_LS_XNC)); }
						 * 
						 * Bổ sung thông tin nghi trùng trên Local nếu có
						 * personCode
						 * 
						 * Long count = 0L; if
						 * (StringUtils.isNotBlank(bufPerson.getPersonCode())) {
						 * List<NicTransaction> listNicTran =
						 * nicTransactionService .findTranByPersonCode(bufPerson
						 * .getPersonCode()); if (listNicTran != null &&
						 * listNicTran.size() > 0) { count = (long)
						 * listNicTran.size(); } }
						 * countDetail.setThongTinCapHCCount(countDetail
						 * .getThongTinCapHCCount() +
						 * Integer.parseInt(count.toString().trim()));
						 * bufDetail.setCountDetail(countDetail);
						 */
						try {
							NicSearchResult nicResult = nicSearchResultService
									.findSearchResultByTranIdAndTypeSearch(
											bufPerson.getTransactionMasterId(),
											Contants.TYPE_OF_SEARCH_AFIS);
							if (nicResult != null) {
								NicSearchHitResult nicHitResult = nicSearchHitResultService
										.findSearchHitResultByTranIdAndSearchId(
												bufPerson.getTransactionId(),
												nicResult.getSearchResultId());
								if (nicHitResult != null) {
									bufDetail.setMatchPointDetai(nicHitResult
											.getHitInfo());
								}
							}
						} catch (Exception e) {
							throw e;
						}
					} else {
						response.setCode(Contants.CODE_INPUT_FAILD);
						response.setMessage(Contants.STRING_STATUS_CODE_HTTP_NOT_FOUND);
						return response;
					}
					if (bufDetail != null) {
						/* Lưu bảng thống kê truyền nhận */
						this.saveOrUpRptData(
								Contants.URL_GET_DETAIL_BUFF_PERSON, 1, null);
					}

					response.setData(bufDetail);
					response.setCode(Contants.CODE_SUCCESS);
					response.setMessage(Contants.MESSAGE_SUCCESS);
				} else {
					response.setCode(Contants.CODE_INPUT_FAILD);
					response.setMessage(Contants.MESSAGE_INPUT_NULL);
				}
			} else {
				response.setCode(Contants.CODE_INPUT_FAILD);
				response.setMessage(Contants.MESSAGE_INPUT_NULL);
			}
		} catch (Exception e) {
			e.printStackTrace();
			response.setCode(Contants.CODE_THROW_EXCEPTION);
			response.setMessage(Contants.MESSAGE_EXCEPTION);
			/*
			 * Lưu dữ liệu nếu có Exception
			 */
			String key = Contants.TYPE_GDBP + "_"
					+ new SimpleDateFormat("ddMMyyyy").format(new Date());
			if (StringUtils.isNotBlank(infoInput.getTransactionId())) {
				key = infoInput.getTransactionId();
			}
			String dataRequest = "";
			try {
				dataRequest = new ObjectMapper().writeValueAsString(infoInput);
			} catch (Exception e2) {
				logger.error(e.getMessage());
			}
			this.saveException(Contants.TYPE_GDBP,
					Contants.URL_GET_DETAIL_BUFF_PERSON, dataRequest, key,
					response.toString(), e);
		}
		return response;
	}

	/*
	 * Tra cứu CMTND/CCCD.
	 */
	@GET
	@Produces("application/json")
	@Consumes("application/json")
	@Path("/getInfoCMT/{transactionId}")
	public Response<List<InfoCMT>> getInfoCMT(
			@PathParam("transactionId") String transactionId) {
		Response<List<InfoCMT>> response = new Response<List<InfoCMT>>();
		response.setCode(Contants.CODE_CONNECT_FAIL);
		response.setMessage(Contants.MESS_CONNECT_FAIL);
		List<InfoCMT> listInfoCMT = null;
		String idNumber = "";
		NicUploadJob nicUploadJob = null;
		NicTransaction trans = null;
		boolean check = true;
		try {
			if (StringUtils.isNotBlank(transactionId)) {
				/* Lấy dữ liệu Job */
				List<NicUploadJob> listUploadJob = uploadJobService
						.findAllByTransactionId(transactionId);
				if (listUploadJob != null && listUploadJob.size() > 0) {
					nicUploadJob = listUploadJob.get(0);
					trans = nicUploadJob.getNicTransaction();
					idNumber = trans.getNin();
				} else {
					check = false;
				}
				if (check && StringUtils.isNotBlank(idNumber)) {
					/* Kiểm tra trạng thái check CMND */
					if (StringUtils.isNotBlank(nicUploadJob
							.getIdentificationCheckStatus())
							&& nicUploadJob.getIdentificationCheckStatus()
									.equals("03")) {
						/* Lấy dữ liệu đã có */
						List<EppIdentification> listIdent = nicIdentificationService
								.findByTranId(trans.getTransactionId());
						if (listIdent != null && listIdent.size() > 0) {
							listInfoCMT = new ArrayList<InfoCMT>();
							for (EppIdentification eppIdentification : listIdent) {
								InfoCMT infoCMT = new InfoCMT();
								if (eppIdentification.getAnhTruoc() != null) {
									infoCMT.setBase64AnhTruoc((Base64
											.encode(eppIdentification
													.getAnhTruoc())));
								}
								if (eppIdentification.getAnhSau() != null) {
									infoCMT.setBase64AnhSau(Base64
											.encode(eppIdentification
													.getAnhSau()));
								}
								infoCMT.setCmId(eppIdentification.getCmId());
								infoCMT.setCtvt1(eppIdentification.getCtVt1());
								infoCMT.setCtvt2(eppIdentification.getCtVt2());
								infoCMT.setDauVetRieng(eppIdentification
										.getDauVetRieng());
								infoCMT.setHoTen(eppIdentification.getHoTen());
								infoCMT.setHoTenCha(eppIdentification
										.getHoTenCha());
								infoCMT.setHoTenMe(eppIdentification
										.getHoTenMe());
								infoCMT.setHoTenVC(eppIdentification
										.getHoTenVc());
								infoCMT.setImgID(eppIdentification.getImgId());
								infoCMT.setMaDanToc(eppIdentification
										.getMaDanToc());
								infoCMT.setMaDP(eppIdentification.getMaDp());
								infoCMT.setMaDPCap(eppIdentification
										.getMaDpCap());
								infoCMT.setMaDPNQ(eppIdentification.getMaDpNq());
								infoCMT.setMaDPTT(eppIdentification.getMaDpTt());
								infoCMT.setMaGioiTinh(eppIdentification
										.getMaGioiTinh());
								infoCMT.setMaHocVan(eppIdentification
										.getMaHocVan());
								infoCMT.setMaNgheNghiep(eppIdentification
										.getMaNgheNghiep());
								infoCMT.setMaPXTT(eppIdentification.getMaPxTt());
								infoCMT.setMaTonGiao(eppIdentification
										.getMaTonGiao());
								infoCMT.setNamSinh(eppIdentification
										.getNamSinh());
								infoCMT.setNgayCapCu(eppIdentification
										.getNgayCapCu());
								infoCMT.setNgayKhai(eppIdentification
										.getNgayKhai());
								infoCMT.setNgaySinh(eppIdentification
										.getNgaySinh());
								infoCMT.setNguyenQuan(eppIdentification
										.getNguyenQuan());
								infoCMT.setNoiLamViec(eppIdentification
										.getNoiLamViec());
								infoCMT.setNoiSinh(eppIdentification
										.getNoiSinh());
								infoCMT.setSoCMND(eppIdentification.getSoCmnd());
								infoCMT.setSoCMNDCu(eppIdentification
										.getSoCmndCu());
								infoCMT.setSoNhaTT(eppIdentification
										.getSoNhaTt());
								infoCMT.setTenAnhSau(eppIdentification
										.getTenAnhSau());
								infoCMT.setTenAnhTruoc(eppIdentification
										.getTenAnhTruoc());
								infoCMT.setTenKhac(eppIdentification
										.getTenKhac());
								infoCMT.setThonPhoTT(eppIdentification
										.getThonPhoTt());
								infoCMT.setThuMuc(eppIdentification.getThuMuc());
								listInfoCMT.add(infoCMT);
							}
						}
						/*
						 * Gọi lại API lấy dữ liệu tra cứu CMND khi job chưa
						 * chạy hoặc job báo lỗi
						 */
					} else if (StringUtils.isBlank(nicUploadJob
							.getIdentificationCheckStatus())
							|| nicUploadJob.getIdentificationCheckStatus()
									.equals("09")) {
						/* Lấy địa chỉ IP */
						ParametersId id = new ParametersId(
								Contants.PARA_SCOPE_CMND,
								Contants.PARA_NAME_URL);
						Parameters param = parametersDao.findById(id);
						String ip = "";
						if (param != null) {
							ip = param.getParaLobValue();
						} else {
							return response;
						}
						/* Lấy dữ liệu từ API */
						listInfoCMT = getRecordDetailService.getInfoCmnd(ip,
								idNumber);
					}
				}

				if (listInfoCMT != null && listInfoCMT.size() > 0) {
					/* Lưu bảng thống kê truyền nhận */
					this.saveOrUpRptData(Contants.URL_GET_INFO_CMT,
							listInfoCMT.size(), null);
					response.setMessage(Contants.MESSAGE_SUCCESS);
				} else {
					response.setMessage("Không có kết quả.");
				}

				response.setCode(Contants.CODE_SUCCESS);
				response.setData(listInfoCMT);
			} else {
				response.setCode(Contants.CODE_INPUT_FAILD);
				response.setMessage(Contants.MESSAGE_INPUT_NULL);
			}
		} catch (Exception e) {
			e.printStackTrace();
			response.setCode(Contants.CODE_THROW_EXCEPTION);
			response.setMessage(Contants.MESSAGE_EXCEPTION);
			/*
			 * Lưu dữ liệu nếu có Exception
			 */
			String key = Contants.TYPE_GIC + "_"
					+ new SimpleDateFormat("ddMMyyyy").format(new Date());
			String dataRequest = "";
			if (StringUtils.isNotBlank(idNumber)) {
				key = idNumber;
				dataRequest = idNumber;
			}
			this.saveException(Contants.TYPE_GIC, Contants.URL_GET_INFO_CMT,
					dataRequest, key, response.toString(), e);
		}
		return response;
	}

	/*
	 * Tra cứu lịch sử Xuất Nhập cảnh
	 */
	@POST
	@Produces("application/json")
	@Consumes("application/json")
	@Path("/getImmhistoryDetail")
	public Response<List<ImmigrationHistoryDetail>> getImmhistoryDetail(
			InfoGetDetailBuffPerson request) {
		Response<List<ImmigrationHistoryDetail>> response = new Response<List<ImmigrationHistoryDetail>>();
		response.setCode(Contants.CODE_CONNECT_FAIL);
		response.setMessage(Contants.MESS_CONNECT_FAIL);
		List<ImmigrationHistoryDetail> listImmigHDetail = null;
		try {
			if (StringUtils.isNotBlank(request.getMaCaNhan())) {

				List<EppPerson> listPerson = eppPersonService
						.findListPersonByOrgPersonCode(request.getMaCaNhan());

				if (listPerson != null && listPerson.size() > 0) {
					listImmigHDetail = new ArrayList<ImmigrationHistoryDetail>();
					for (EppPerson eppPerson : listPerson) {
						listImmigHDetail.addAll(getRecordDetailService
								.getImmigrationHistoryDetail(eppPerson
										.getPersonCode()));
					}
				} else {
					listImmigHDetail = getRecordDetailService
							.getImmigrationHistoryDetail(request.getMaCaNhan());
				}

				if (listImmigHDetail != null && listImmigHDetail.size() > 0) {
					/* Lưu bảng thống kê truyền nhận */
					this.saveOrUpRptData(
							Contants.URL_GET_IMMIGRATION_HISTORY_DETAIL,
							listImmigHDetail.size(), null);
				}

				response.setData(listImmigHDetail);
				response.setCode(Contants.CODE_SUCCESS);
				response.setMessage(Contants.MESSAGE_SUCCESS);
			} else {
				response.setCode(Contants.CODE_INPUT_FAILD);
				response.setMessage(Contants.MESSAGE_INPUT_NULL);
			}
		} catch (Exception e) {
			e.printStackTrace();
			response.setCode(Contants.CODE_THROW_EXCEPTION);
			response.setMessage(Contants.MESSAGE_EXCEPTION);
			/*
			 * Lưu dữ liệu nếu có Exception
			 */
			String key = Contants.TYPE_GIHD + "_"
					+ new SimpleDateFormat("ddMMyyyy").format(new Date());
			String dataRequest = "";
			if (StringUtils.isNotBlank(request.getMaCaNhan())) {
				key = request.getMaCaNhan();
				dataRequest = request.getMaCaNhan();
			}
			this.saveException(Contants.TYPE_GIHD,
					Contants.URL_GET_IMMIGRATION_HISTORY_DETAIL, dataRequest,
					key, response.toString(), e);
		}
		return response;
	}

	/*
	 * Tra cứu giấy tờ mất hủy
	 */
	@POST
	@Produces("application/json")
	@Consumes("application/json")
	@Path("/getDocumentCancelDetail")
	public Response<List<DocumentCancelDetail>> getDocumentCancelDetail(
			DoiTuongQuery request) {
		Response<List<DocumentCancelDetail>> response = new Response<List<DocumentCancelDetail>>();
		response.setCode(Contants.CODE_CONNECT_FAIL);
		response.setMessage(Contants.MESS_CONNECT_FAIL);
		List<DocumentCancelDetail> listDocumentCancelDetail = null;
		try {
			if (request != null) {

				listDocumentCancelDetail = getRecordDetailService
						.getDocumentCancelDetail(request.getGioiTinh(),
								request.getHoTen(), request.getKieuNgaySinh(),
								request.getNgaySinh(), request.getQuocTich(),
								request.getSoCMND(), request.getSoHoChieu());

				if (listDocumentCancelDetail != null
						&& listDocumentCancelDetail.size() > 0) {
					CodeValues codeValues = null;
					for (DocumentCancelDetail docCD : listDocumentCancelDetail) {
						if (docCD.getQuocTich() != null) {
							codeValues = codesService.getCountryByA08Id(docCD
									.getQuocTich().toString().trim());
							if (codeValues != null) {
								docCD.setQuocTich(codeValues.getCodeValueDesc());
							}
						}
					}

					/* Lưu bảng thống kê truyền nhận */
					this.saveOrUpRptData(
							Contants.URL_GET_DOCUMENT_CANCEL_DETAIL,
							listDocumentCancelDetail.size(), null);
				}

				response.setData(listDocumentCancelDetail);
				response.setCode(Contants.CODE_SUCCESS);
				response.setMessage(Contants.MESSAGE_SUCCESS);
			} else {
				response.setCode(Contants.CODE_INPUT_FAILD);
				response.setMessage(Contants.MESSAGE_INPUT_NULL);
			}
		} catch (Exception e) {
			e.printStackTrace();
			response.setCode(Contants.CODE_THROW_EXCEPTION);
			response.setMessage(Contants.MESSAGE_EXCEPTION);
			/*
			 * Lưu dữ liệu nếu có Exception
			 */
			String key = Contants.TYPE_GDCD + "_"
					+ new SimpleDateFormat("ddMMyyyy").format(new Date());
			String dataRequest = "";
			try {
				dataRequest = new ObjectMapper().writeValueAsString(request);
			} catch (Exception e2) {
				logger.error(e.getMessage());
			}
			this.saveException(Contants.TYPE_GDCD,
					Contants.URL_GET_DOCUMENT_CANCEL_DETAIL, dataRequest, key,
					response.toString(), e);
		}
		return response;
	}

	/*
	 * Tra cứu giấy tờ mất hủy
	 */
	@POST
	@Produces("application/json")
	@Consumes("application/json")
	@Path("/getCheckBuffPerson")
	public Response<List<BufEppPersonInvestigation>> getCheckBuffPerson(
			DoiTuongQuery request) {
		Response<List<BufEppPersonInvestigation>> response = new Response<List<BufEppPersonInvestigation>>();
		response.setCode(Contants.CODE_CONNECT_FAIL);
		response.setMessage(Contants.MESS_CONNECT_FAIL);
		List<BufEppPersonInvestigation> listBuff = null;
		try {
			if (request != null) {

				Map<String, Object> validationFieldsMap = this
						.constructDemographicCheckingFields(request);

				StringBuffer hitInfoBuffer = new StringBuffer();
				for (String fieldName : validationFieldsMap.keySet()) {
					hitInfoBuffer.append(fieldName).append(",");
				}

				List<NicRegistrationData> hitList = nicRegistrationDataService
						.findAllByFields(validationFieldsMap);

				List<NicTransaction> hitListNin = nicTransactionService
						.findAllByFields(request.getSoCMND());

				// Tìm theo số hộ chiếu cũ
				List<NicDocumentData> hitListPassport = documentDataService
						.findAllByDocNumber(request.getSoHoChieu());

				NicSearchResult searchResult = new NicSearchResult();
				searchResult.setCreateDatetime(new Date());
				searchResult.setHasHit(false);
				boolean isError = false;
				Map<String, String> afisKeyCache = new HashMap<String, String>();

				if (!isError) {
					// 3) to check demographic
					if (CollectionUtils.isNotEmpty(hitList)) {
						// construct search hit result
						for (NicRegistrationData candidate : hitList) {
							NicAfisData candidateAfisData = nicAfisDataService
									.findByTransactionId(candidate
											.getTransactionId());
							/*
							 * if (candidateAfisData == null) continue; // chris
							 * handle npe [2016 Feb 17]
							 */

							// Thay đổi theo dữ liệu đồng bộ [2019 Oct 30]
							if (candidateAfisData == null) {
								candidateAfisData = new NicAfisData();
								candidateAfisData.setAfisKeyNo("");
							}
							afisKeyCache.put(candidate.getTransactionId(),
									candidateAfisData.getAfisKeyNo());
							searchResult.getHitList().add(
									buildSearchHitResult(candidate
											.getTransactionId(), afisKeyCache
											.get(candidate.getTransactionId()),
											"APPLICATION IN PROGRESS", "CPD"));
						}

					}
					// 1. Trung thêm check CMND
					if (CollectionUtils.isNotEmpty(hitListNin)) {
						for (NicTransaction candidate : hitListNin) {
							boolean checkExist = false;
							for (NicSearchHitResult nicSHR : searchResult
									.getHitList()) {
								if (candidate.getTransactionId().equals(
										nicSHR.getTransactionIdHit())) {
									checkExist = true;
									break;
								}
							}
							if (checkExist) {
								continue;
							}
							NicAfisData candidateAfisData = nicAfisDataService
									.findByTransactionId(candidate
											.getTransactionId());
							/*
							 * if (candidateAfisData == null) continue; // chris
							 * handle npe [2016 Feb 17]
							 */

							// Thay đổi theo dữ liệu đồng bộ [2019 Oct 30]
							if (candidateAfisData == null) {
								candidateAfisData = new NicAfisData();
								candidateAfisData.setAfisKeyNo("");
							}
							afisKeyCache.put(candidate.getTransactionId(),
									candidateAfisData.getAfisKeyNo());
							searchResult.getHitList().add(
									buildSearchHitResult(candidate
											.getTransactionId(), afisKeyCache
											.get(candidate.getTransactionId()),
											"APPLICATION IN PROGRESS", "CPD"));
						}
					}
					// End 1
					// Hoald thêm check theo số hộ chiếu cũ: prevPassportNo
					if (CollectionUtils.isNotEmpty(hitListPassport)) {
						for (NicDocumentData candidate : hitListPassport) {
							boolean checkExist = false;
							for (NicSearchHitResult nicSHR : searchResult
									.getHitList()) {
								if (candidate.getId().getTransactionId()
										.equals(nicSHR.getTransactionIdHit())) {
									checkExist = true;
									break;
								}
							}
							if (checkExist) {
								continue;
							}
							NicAfisData candidateAfisData = nicAfisDataService
									.findByTransactionId(candidate.getId()
											.getTransactionId());
							/*
							 * if (candidateAfisData == null) continue; // chris
							 * handle npe [2016 Feb 17]
							 */

							// Thay đổi theo dữ liệu đồng bộ [2019 Oct 30]
							if (candidateAfisData == null) {
								candidateAfisData = new NicAfisData();
								candidateAfisData.setAfisKeyNo("");
							}
							afisKeyCache.put(candidate.getId()
									.getTransactionId(), candidateAfisData
									.getAfisKeyNo());
							searchResult.getHitList().add(
									buildSearchHitResult(candidate.getId()
											.getTransactionId(), afisKeyCache
											.get(candidate.getId()
													.getTransactionId()),
											"APPLICATION IN PROGRESS", "CPD"));
						}
					}
					// end check prevPassportNo
				}
				if (searchResult.getHitList().size() > 0) {
					searchResult.setHasHit(true);
				}

				// Xu ly lay ket qua tu A08
				SearchResultInA08Dto rq = new SearchResultInA08Dto();
				rq.setpGTinh(request.getGioiTinh());
				rq.setpHoTen(HelperClass.removeAccent(request.getHoTen())
						.toUpperCase());
				rq.setpKieuNS(request.getKieuNgaySinh());
				rq.setpMaCaNhan(request.getPersonCode());
				rq.setpNgaySinh(request.getNgaySinh());
				rq.setpQTich(request.getQuocTich());
				rq.setpSoCMND(request.getSoCMND());
				rq.setpSoHC(request.getSoHoChieu());

				listBuff = this.processResultInvestigationA08(rq,
						searchResult.getHitList());

				response.setData(listBuff);
				response.setCode(Contants.CODE_SUCCESS);
				response.setMessage(Contants.MESSAGE_SUCCESS);
			} else {
				response.setCode(Contants.CODE_INPUT_FAILD);
				response.setMessage(Contants.MESSAGE_INPUT_NULL);
			}
		} catch (Exception e) {
			e.printStackTrace();
			response.setCode(Contants.CODE_THROW_EXCEPTION);
			response.setMessage(Contants.MESSAGE_EXCEPTION);
			/*
			 * Lưu dữ liệu nếu có Exception
			 */
			String key = Contants.TYPE_GCB + "_"
					+ new SimpleDateFormat("ddMMyyyy").format(new Date());
			String dataRequest = "";
			try {
				dataRequest = new ObjectMapper().writeValueAsString(request);
			} catch (Exception e2) {
				logger.error(e.getMessage());
			}
			this.saveException(Contants.TYPE_GCB, "getCheckBuffPerson",
					dataRequest, key, response.toString(), e);
		}
		return response;
	}

	/*
	 * Test
	 */
	@POST
	@Produces("application/json")
	@Consumes("application/json")
	@Path("/getDetailTest")
	public ResponseBase getDetailTest(List<InfoGetDetailBuffPerson> listInfo) {
		ResponseBase response = new ResponseBase();
		response.setCode(Contants.CODE_CONNECT_FAIL);
		response.setMessage(Contants.MESS_CONNECT_FAIL);
		String input = "";
		SimpleDateFormat fm = new SimpleDateFormat("yyyyMMdd");
		try {
			if (listInfo.size() > 0) {
				List<ImmigrationHistoryDetail> listImmigHDetail = null;

				String messOut = "";
				// for (InfoGetDetailBuffPerson info : listInfo) {
				// input = info.getPersonCode();
				// listImmigHDetail = getRecordDetailService
				// .getImmigrationHistoryDetail(info.getPersonCode());
				// if (listImmigHDetail != null && listImmigHDetail.size() > 0)
				// {
				// for (ImmigrationHistoryDetail immigrationHistoryDetail :
				// listImmigHDetail) {
				// if (immigrationHistoryDetail.getChilds() != null) {
				// messOut += " == " + input;
				// break;
				// }
				// }
				// }
				// }
				List<NicUploadJob> listJob = uploadJobService
						.getTransactionJobsInQueue();
				messOut = String.valueOf(listJob.size());
				response.setCode("200");
				response.setMessage("Done: = " + messOut);
			} else {
				response.setCode(Contants.CODE_INPUT_FAILD);
				response.setMessage(Contants.MESSAGE_INPUT_NULL);
			}
		} catch (Exception e) {
			e.printStackTrace();
			response.setCode(Contants.CODE_THROW_EXCEPTION);
			// response.setMessage(Contants.MESSAGE_EXCEPTION);
		}
		return response;
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
	 * Lưu thống kê truyền nhận
	 */
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
	 * convert byte to base64
	 */
	public String convertByteToBase64(byte[] b) {
		String base = null;
		try {
			base = Base64.encode(b);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return base;
	}

	/*
	 * convert trang thai ho so A08
	 */
	private String convertTranStatusA08(String buocXl, String kqHoSo) {
		String status = "";
		if (buocXl == "A18_DUYET" || buocXl == "PA18_DUYET") {
			if (kqHoSo == "A18_BTHS") {
				status = "APPROVE_K";
			} else if (kqHoSo == "A18_KHONG_CAP"
					|| kqHoSo == "PA18_KHONG_DUYET") {
				status = "APPROVE_C";
			} else {
				status = "APPROVE_D";
			}
		} else {
			if (buocXl == "CHUYEN_A18" || buocXl == "DA_NHAP_TO_KHAI") {
				status = "RIC_UPLOADED";
			} else if (buocXl == "A18_DANG_XU_LY" || buocXl == "DANG_XU_LY") {
				status = "INVESTIGATION_PROCESSING";
			} else if (buocXl == "A18_DE_XUAT"
					|| buocXl == "A18_CHUYEN_DE_XUAT"
					|| buocXl == "PA18_DE_XUAT" || buocXl == "A18_DEXUAT"
					|| buocXl == "A18_DELETE_XUAT") {
				status = "CREATED_B";
			} else if (buocXl == "IN_XAC_MINH") {
				status = "APPROVE_K";
			} else if (buocXl == "IN_HO_CHIEU" || buocXl == "DA_IN_HO_CHIEU") {
				status = "PERSO_PRINTED";
			} else if (buocXl == "LAP_DS_TRA_KQ"
					|| buocXl == "LAP_DS_TRA_KET_QUA") {
				status = "CREATED_C";
			} else if (buocXl == "TRA_KET_QUA") {
				status = "RIC_ISSUED";
			}
		}
		if (status.isEmpty()) {
			status = "RIC_ISSUED";
		}

		return status;
	}

	/*
	 * tạo bản ghi hàng đợi.
	 */
	private Boolean addObjToQueueJob(String transactionId, String ObjType,
			String receiver, String status, String function) throws Exception {
		try {
			SyncQueueJob queue = new SyncQueueJob();
			queue.setCreatedTs(Calendar.getInstance().getTime());
			queue.setObjectId(transactionId);
			queue.setObjectType(ObjType);
			queue.setReceiver(receiver);
			queue.setTranStatus(status);
			queue.setStatus(Contants.CODE_STATUS_QUEUE_PENDING);
			queue.setSyncRetry(1);
			BaseModelSingle<Boolean> baseSaveQueue = queueJobService
					.saveOrUpdateQueue(queue);
			if (!baseSaveQueue.isError() || baseSaveQueue.getMessage() != null) {
				throw new Exception(baseSaveQueue.getMessage());
			}
			boolean check = baseSaveQueue.getModel();
			if (StringUtils.isNotBlank(function) && check) {
				/* Lưu bảng thống kê truyền nhận */
				this.saveOrUpRptData(function, 1, receiver);
			}
			return check;
			// }
		} catch (Exception e) {
			throw new Exception(
					CreateMessageException.createMessageException(e)
							+ " - addObjToQueueJob:" + transactionId
							+ " - thất bại.");
		}
	}

	/*
	 * 
	 */
	private Map<String, Object> constructDemographicCheckingFields(
			DoiTuongQuery regData) {
		Map<String, Object> fieldsMap = new HashMap<String, Object>();
		if (StringUtils.isNotBlank(regData.getHoTen())) {
			fieldsMap.put("searchName",
					HelperClass.removeAccent(regData.getHoTen()).toUpperCase());
		}
		if (regData.getNgaySinh() != null)
			fieldsMap.put("dateOfBirth", DateUtil.strToDate(
					regData.getNgaySinh(), DateUtil.FORMAT_YYYYMMDD));

		if (StringUtils.isNotBlank(regData.getGioiTinh()))
			fieldsMap.put("gender", regData.getGioiTinh());

		return fieldsMap;
	}

	private NicSearchHitResult buildSearchHitResult(String transactionId,
			String afisKeyNo, String hitInfo, String dataSource) {
		NicSearchHitResult hitCandidate = new NicSearchHitResult();
		hitCandidate.setTransactionIdHit(transactionId); // Search Side's
															// transaction id
		hitCandidate.setAfisKeyNoHit(afisKeyNo);
		hitCandidate.setHitInfo(hitInfo);
		hitCandidate.setDataSource("CPD");
		hitCandidate.setCreateDatetime(new Date());
		hitCandidate.setHitDecision(true);
		return hitCandidate;
	}

	// Xu ly ket qua tu a08
	private List<BufEppPersonInvestigation> processResultInvestigationA08(
			SearchResultInA08Dto request, Collection<NicSearchHitResult> listCPD) {
		List<BufEppPersonInvestigation> inveslist = new ArrayList<BufEppPersonInvestigation>();
		try {
			// Lay ket qua tu a08
			List<BufEppPersonInvestigation> list = inquirySearchResultService
					.getCheckResultA08(request);

			if (list != null && list.size() > 0) {
				for (BufEppPersonInvestigation i : list) {
					i.setOrgPerson(i.getMaCaNhan());
					inveslist.add(i);
				}
			}
			if (listCPD != null && listCPD.size() > 0) {
				for (NicSearchHitResult item : listCPD) {
					Boolean exist = false;
					String codePersonHit = "";
					String orgPersonHit = "";
					String macanhanHit = "";
					BaseModelSingle<NicTransaction> hisTran = nicTransactionService
							.findTransactionById(item.getTransactionIdHit());
					if (hisTran != null && hisTran.getModel() != null) {
						codePersonHit = hisTran.getModel().getPersonCode();
						EppPerson personHit = eppPersonService
								.findPersonByPersonCode(codePersonHit);
						if (personHit != null
								&& StringUtils.isNotBlank(personHit
										.getOrgPerson())) {
							orgPersonHit = personHit.getOrgPerson();
						} else {
							orgPersonHit = codePersonHit;
						}
					}
					if (inveslist != null && inveslist.size() > 0) {
						for (BufEppPersonInvestigation i : inveslist) {
							logger.info("MaCaNhan: " + codePersonHit + " : "
									+ i.getMaCaNhan());
							if (StringUtils.isNotEmpty(codePersonHit)
									&& codePersonHit.equals(i.getMaCaNhan())
									|| (i.getMaCaNhan().equals(orgPersonHit))
									|| i.getOrgPerson().equals(orgPersonHit)) {
								i.setPersonCode(codePersonHit);
								exist = true;
								break;
							}
						}
					}
					if (!exist) {
						DateFormat sdf = new SimpleDateFormat("yyyyMMdd");
						BufEppPersonInvestigation buff = new BufEppPersonInvestigation();
						NicUploadJob upload = uploadJobService
								.getUploadJobByTransactionIdSinger(null,
										item.getTransactionIdHit()).getModel();
						NicRegistrationData regis = upload.getNicTransaction()
								.getNicRegistrationData();
						buff.setType("CPD");
						buff.setTransactionId(item.getTransactionIdHit());
						buff.setSrc("LOCAL");
						buff.setMaCaNhan(macanhanHit);
						buff.setPersonCode(codePersonHit);
						buff.setOrgPerson(orgPersonHit);
						buff.setName(HelperClass.createFullName(
								regis.getSurnameFull(),
								regis.getMiddlenameFull(),
								regis.getFirstnameFull()));
						buff.setOtherName(regis.getAliasName());
						buff.setGender(regis.getGender());
						buff.setDateOfBirth(regis.getDateOfBirth() != null ? DateUtil
								.parseDate(regis.getDateOfBirth(),
										DateUtil.FORMAT_YYYYMMDD) : "");
						String px = codesService.getCodeValueDescByIdName(
								"CODE_BirthPlace", regis.getPlaceOfBirth(), "");
						buff.setPlaceOfBirthName(px);
						buff.setIdNumber(upload.getNicTransaction().getNin());
						if (regis.getDateNin() != null)
							buff.setDateOfIdIssue(sdf.format(regis.getDateNin()));
						String nc = codesService.getCodeValueDescByIdName(
								"CODE_IDPlace", regis.getAddressNin(), "");
						buff.setPlaceOfIdIssueName(nc);
						String dt = codesService.getCodeValueDescByIdName(
								"CODE_NATION", regis.getNation(), "");
						buff.setEthnic(dt);
						String tg = codesService.getCodeValueDescByIdName(
								"CODE_RELIGION", regis.getReligion(), "");
						buff.setReligion(tg);
						buff.setSearchName(regis.getSearchName());
						String na = codesService.getCodeValueDescByIdName(
								"COUNTRY", regis.getNationality(), "");
						buff.setNationalityName(na);
						buff.setResidentPlaceName("");
						String detail = "";
						if (StringUtils.isNotBlank(regis.getAddressLine1())) {
							detail = regis.getAddressLine1();
						}
						String xp = codesService.getCodeValueDescByIdName(
								"VILLAGE", regis.getAddressLine2(), "");
						String qh = codesService.getCodeValueDescByIdName(
								"TOWN_VILLAGE", regis.getAddressLine3(), "");
						String th = codesService.getCodeValueDescByIdName(
								"DISTRICT", regis.getAddressLine4(), "");
						if (StringUtils.isNotBlank(xp)) {
							detail += ", " + xp;
						}
						if (StringUtils.isNotBlank(qh)) {
							detail += ", " + qh;
						}
						if (StringUtils.isNotBlank(th)) {
							detail += ", " + th;
						}
						buff.setResidentAddress(detail);
						String detail_temp = "";
						if (StringUtils
								.isNotBlank(regis.getAddressTempDetail())) {
							detail_temp = regis.getAddressTempDetail();
						}
						String xp_temp = codesService.getCodeValueDescByIdName(
								"VILLAGE", regis.getAddressTempVillage(), "");
						String qh_temp = codesService.getCodeValueDescByIdName(
								"TOWN_VILLAGE", regis.getAddressTempDistrict(),
								"");
						String th_temp = codesService.getCodeValueDescByIdName(
								"DISTRICT", regis.getAddressTempProvince(), "");
						if (StringUtils.isNotBlank(xp_temp)) {
							detail_temp += ", " + xp_temp;
						}
						if (StringUtils.isNotBlank(qh_temp)) {
							detail_temp += ", " + qh_temp;
						}
						if (StringUtils.isNotBlank(th_temp)) {
							detail_temp += ", " + th_temp;
						}
						buff.setTmpAddress(detail_temp);
						buff.setOccupation(regis.getJob());
						buff.setOfficeInfo(regis.getOfficeAddress());
						buff.setFatherName(regis.getFatherFullname());
						buff.setFatherNationality(regis.getFatherCitizenship());
						buff.setFatherOccupation("");
						buff.setMotherName(regis.getMotherFullname());
						buff.setMotherNationality(regis.getMotherCitizenship());
						buff.setMotherOccupation("");

						List<EppPerson> listPerson = eppPersonService
								.findListPersonByOrgPersonCode(codePersonHit);

						if (listPerson != null && listPerson.size() > 0) {
							List<NicTransaction> listNicTran = nicTransactionService
									.findTranByListPersonCode(listPerson);
							if (listNicTran != null && listNicTran.size() > 0) {
								List<NicDocumentData> listDocData = new ArrayList<NicDocumentData>();
								for (NicTransaction nicTran : listNicTran) {
									listDocData.addAll(documentDataService
											.findListDocByTranId(nicTran
													.getTransactionId()));
								}
								if (listDocData != null
										&& listDocData.size() > 0) {
									String passportNo = "";
									int index = 0;
									for (NicDocumentData nicDocumentData : listDocData) {
										if (index == 0) {
											passportNo = nicDocumentData
													.getId().getPassportNo();
										} else {
											passportNo += ","
													+ nicDocumentData.getId()
															.getPassportNo();
										}
										index++;
									}
									buff.setPassportNo(passportNo);
								}
							}
						}

						// try {
						// BaseModelList<NicTransactionAttachment> photo =
						// nicTransactionAttachmentService
						// .findNicTransactionAttachments(
						// item.getTransactionIdHit(),
						// "PH_CAP", null);
						// buff.setPhoto((photo != null
						// && photo.getListModel() != null && photo
						// .getListModel().size() > 0) ? photo
						// .getListModel().get(0).getDocData() : null);
						// } catch (Exception e) {
						// }
						buff.setCreateBy("SYSTEM");
						buff.setCreateDatetime(new Date());
						buff.setDataType("HC");
						inveslist.add(buff);
					}
				}
			}
		} catch (Exception e) {
			logger.error(" ExceptionSaveBuff: "
					+ CreateMessageException.createMessageException(e));
		}
		return inveslist;
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
