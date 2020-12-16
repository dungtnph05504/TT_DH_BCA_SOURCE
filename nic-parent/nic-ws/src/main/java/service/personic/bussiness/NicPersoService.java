package service.personic.bussiness;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import service.immihistory.model.BaseResponse;
import service.immihistory.model.ItemResponse;
import service.perso.util.Contants;
import service.personic.model.InfoStatus;
import service.personic.model.InventoryDetail;
import service.personic.model.InventoryDetails;
import service.personic.model.InventoryStatus;
import service.personic.model.StatusResponse;
import service.personic.model.SyncStatus;
import service.transaction.model.ResponseBase;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nec.asia.nic.comp.trans.domain.BaseModelSingle;
import com.nec.asia.nic.comp.trans.domain.EppInventory;
import com.nec.asia.nic.comp.trans.domain.EppInventoryItems;
import com.nec.asia.nic.comp.trans.domain.EppInventoryItemsDetail;
import com.nec.asia.nic.comp.trans.domain.RptStatisticsTransmitData;
import com.nec.asia.nic.comp.trans.service.EppInventoryItemsDetailService;
import com.nec.asia.nic.comp.trans.service.EppInventoryItemsService;
import com.nec.asia.nic.comp.trans.service.EppInventoryService;
import com.nec.asia.nic.comp.trans.service.RptStatisticsTransmitDataService;
import com.nec.asia.nic.comp.ws.log.domain.CreateMessageException;
import com.nec.asia.nic.comp.ws.log.domain.EppWsLog;
import com.nec.asia.nic.comp.ws.log.service.EppWsLogService;
import com.nec.asia.nic.framework.admin.code.domain.Parameters;
import com.nec.asia.nic.framework.admin.code.service.ParametersService;
import com.nec.asia.nic.utils.DateUtil;
import com.nec.asia.nic.utils.HelperClass;

@Path("/syncPerso/")
@WebService
@Provider
public class NicPersoService {

	public final Logger logger = LoggerFactory.getLogger(this.getClass());
	private static final String CODE_SYSTEM_PARAMETER = "SYSTEM";
	private static final String CODE_PARAMETER_MAX_SIZE_SYNC_INVEN = "MAX_SIZE_SYNC_INVEN";
	private static final String INVENTORY_DETAIL_SYNC_YES = "Y";
	private static final String INVENTORY_DETAIL_SYNC_NO = "N";
	private static final String CODE_STATUS_SUCCESS = "SUCCESS";
	private static final String CODE_STATUS_FAIL = "FAIL";
	private static final String INVENTORY_DETAIL_SYNC_I = "I";

	@Autowired
	private EppInventoryService inventoryService;

	@Autowired
	private EppInventoryItemsDetailService inventoryItemsDetailService;

	@Autowired
	private EppInventoryItemsService inventoryItemsService;

	@Autowired
	private ParametersService parametersService;

	@Autowired
	private RptStatisticsTransmitDataService rptService;
	
	@Autowired
	private EppWsLogService eppWsLogService;

	@GET
	@Produces("application/json")
	@Consumes("application/json")
	@Path("/downloadInventory/{code}")
	public BaseResponse<InventoryDetails> downloadInventory(
			@PathParam("code") String code) {
		BaseResponse<InventoryDetails> response = new BaseResponse<InventoryDetails>();
		response.setCode(Contants.RESPONSE_CODE_FAIL_API);
		InventoryDetails iDes = new InventoryDetails();
		List<InventoryDetail> iDeList = new ArrayList<InventoryDetail>();
		try {
			int maxTotal = 100;
			Parameters pr = parametersService.getParamDetails(
					CODE_SYSTEM_PARAMETER, CODE_PARAMETER_MAX_SIZE_SYNC_INVEN);
			if (pr != null) {
				maxTotal = Integer.parseInt(pr.getParaShortValue());
			}
			EppInventory eppInv = inventoryService.findInventoryByCode(code,
					INVENTORY_DETAIL_SYNC_YES);
			if (eppInv == null) {
				logger.error("Inventory not found, by code: " + code);
				throw new Exception("Inventory not found");
			}
			Integer invId = eppInv.getId();
			List<EppInventoryItems> itemList = inventoryItemsService
					.findInventoryItems(invId, null);
			if (itemList == null || itemList.size() == 0) {
				logger.error("InventoryItems not found, by inventory id: "
						+ invId);
				throw new Exception("InventoryItems not found");
			}
			Long[] arrItem = new Long[itemList.size()];
			int i = 0;
			for (EppInventoryItems item : itemList) {
				arrItem[i] = item.getId();
				i++;
			}
			List<EppInventoryItemsDetail> itemDetailList = inventoryItemsDetailService
					.findInventoryItemsDetail(arrItem, maxTotal,
							INVENTORY_DETAIL_SYNC_NO, null);
			if (itemDetailList == null || itemDetailList.size() == 0) {
				logger.error("InventoryItemsDetail not found, by inventory id: "
						+ invId);
				throw new Exception("InventoryItemsDetail not found");
			}
			for (EppInventoryItemsDetail detail : itemDetailList) {
				InventoryDetail iDe = new InventoryDetail(detail);
				iDeList.add(iDe);
			}
			iDes.setInventorys(iDeList);
			response.setData(iDes);
			response.setCode(Contants.RESPONSE_CODE_SUCCESS_API);
			response.setMessage(Contants.RESPONSE_MESSAGE_SUCCESS_API);

			/* Lưu bảng thống kê truyền nhận */
			if (iDeList.size() > 0) {
				this.saveOrUpRptData(Contants.URL_DOWNLOAD_INVENTORY,
						iDeList.size(), code);
			}

		} catch (Exception e) {
			e.printStackTrace();
			response.setCode(Contants.CODE_THROW_EXCEPTION);
			response.setMessage(Contants.MESSAGE_EXCEPTION);
			/*
			 * Lưu dữ liệu nếu có Exception
			 */
			String dataResponse = "";
			String keyId = "DI_" + new SimpleDateFormat("ddMMyyyy").format(new Date());
			try {
				dataResponse = new ObjectMapper()
				.writeValueAsString(response);
			} catch (Exception e2) {
				logger.error(e.getMessage());
			}
			this.saveException(Contants.TYPE_DI, Contants.URL_DOWNLOAD_INVENTORY,
					code, keyId, dataResponse, e);
		}
		return response;
	}

	@POST
	@Produces("application/json")
	@Consumes("application/json")
	@Path("/updateStatusSync")
	public ResponseBase updateStatusSync(InfoStatus status) {
		ResponseBase response = new ResponseBase();
		response.setCode(Contants.RESPONSE_CODE_FAIL_API);
		try {
			if (status != null && status.getStatus() != null) {
				for (SyncStatus synS : status.getStatus()) {
					EppInventoryItemsDetail items = inventoryItemsDetailService
							.findById(synS.getId());
					if (items != null) {
						if (synS.getStatus().equals(CODE_STATUS_SUCCESS)) {
							items.setSyncStatus(INVENTORY_DETAIL_SYNC_YES);
							items.setUpdateTs(new Date());
							items.setUpdatedBy(Contants.CREATE_BY_SYSTEM);
							inventoryItemsDetailService.saveOrUpdate(items);
						}
					}
				}
				response.setCode(Contants.RESPONSE_CODE_SUCCESS_API);
				response.setMessage(Contants.RESPONSE_MESSAGE_SUCCESS_API);

				/* Lưu bảng thống kê truyền nhận */
				this.saveOrUpRptData(Contants.URL_UPDATE_STATUS_SYNC, 1, null);

			} else {
				logger.error("Info status is null.");
				throw new Exception("Info status is null");
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
			response.setMessage(e.getMessage());
		}
		return response;
	}

	@POST
	@Produces("application/json")
	@Consumes("application/json")
	@Path("/updateStatusInv")
	public BaseResponse<List<StatusResponse>> updateStatusInv(InfoStatus status) {
		BaseResponse<List<StatusResponse>> response = new BaseResponse<List<StatusResponse>>();
		response.setCode(Contants.RESPONSE_CODE_FAIL_API);
		List<StatusResponse> list = new ArrayList<StatusResponse>();
		try {
			if (status != null && status.getInvStatus() != null) {
				for (InventoryStatus inv : status.getInvStatus()) {
					StatusResponse sr = new StatusResponse();
					sr.setId(inv.getId());
					try {
						EppInventoryItemsDetail items = inventoryItemsDetailService
								.findByConditions(inv.getChipSeriesNo(),
										inv.getDocChars(), inv.getDocNum());
						if (items != null) {
							items.setStatus(inv.getStatus());
							items.setUpdateTs(new Date());
							items.setUpdatedBy(Contants.CREATE_BY_SYSTEM);
							inventoryItemsDetailService.saveOrUpdate(items);
						}
						sr.setStage(1);
					} catch (Throwable e) {
						e.printStackTrace();
						logger.error("error update status, chip: "
								+ inv.getChipSeriesNo() + ", char: "
								+ inv.getDocChars() + ", num: "
								+ inv.getDocNum());
						sr.setStage(0);
					}
					list.add(sr);
				}
				response.setCode(Contants.RESPONSE_CODE_SUCCESS_API);
				response.setMessage(Contants.RESPONSE_MESSAGE_SUCCESS_API);

				/* Lưu bảng thống kê truyền nhận */
				this.saveOrUpRptData(Contants.URL_UPDATE_STATUS_INV, 1, null);

			} else {
				logger.error("Info status is null.");
				throw new Exception("Info status is null");
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
			String keyId = "USI_" + new SimpleDateFormat("ddMMyyyy").format(new Date());
			try {
				dataResponse = new ObjectMapper()
				.writeValueAsString(response);
				dataRequest = new ObjectMapper()
				.writeValueAsString(status);
			} catch (Exception e2) {
				logger.error(e.getMessage());
			}
			this.saveException(Contants.TYPE_USI, Contants.URL_UPDATE_STATUS_INV,
					dataRequest, keyId, dataResponse, e);
		}
		return response;
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
}
