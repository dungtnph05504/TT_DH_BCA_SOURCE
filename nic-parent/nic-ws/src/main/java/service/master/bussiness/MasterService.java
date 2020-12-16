package service.master.bussiness;

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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import service.master.model.FeeInfo;
import service.master.model.FeeInfos;
import service.master.model.MasterInfo;
import service.master.model.MasterInfos;
import service.master.model.SiteInfo;
import service.master.model.SiteInfos;
import service.master.model.UserInfo;
import service.perso.util.Contants;
import service.transaction.bussiness.TransactionService;
import service.transaction.model.Response;
import service.transaction.model.ResponseBase;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nec.asia.nic.comp.trans.domain.BaseModelSingle;
import com.nec.asia.nic.comp.trans.domain.RptStatisticsTransmitData;
import com.nec.asia.nic.comp.trans.domain.SyncQueueJob;
import com.nec.asia.nic.comp.trans.service.RptStatisticsTransmitDataService;
import com.nec.asia.nic.comp.trans.service.SyncQueueJobService;
import com.nec.asia.nic.comp.trans.utils.RegistrationConstants;
import com.nec.asia.nic.comp.ws.log.domain.CreateMessageException;
import com.nec.asia.nic.comp.ws.log.domain.EppWsLog;
import com.nec.asia.nic.comp.ws.log.service.EppWsLogService;
import com.nec.asia.nic.framework.admin.code.domain.CodeValues;
import com.nec.asia.nic.framework.admin.code.domain.PaymentDef;
import com.nec.asia.nic.framework.admin.code.service.CodesService;
import com.nec.asia.nic.framework.admin.code.service.ParametersService;
import com.nec.asia.nic.framework.admin.code.service.PaymentDefService;
import com.nec.asia.nic.framework.admin.rbac.domain.Roles;
import com.nec.asia.nic.framework.admin.rbac.domain.Users;
import com.nec.asia.nic.framework.admin.rbac.service.RoleService;
import com.nec.asia.nic.framework.admin.rbac.service.UserService;
import com.nec.asia.nic.framework.admin.site.domain.SiteRepository;
import com.nec.asia.nic.framework.admin.site.service.SiteService;
import com.nec.asia.nic.framework.security.service.ADUser;
import com.nec.asia.nic.framework.security.service.exception.ExistingActiveDirectoryException;
import com.nec.asia.nic.framework.security.service.exception.UserCRUDException;
import com.nec.asia.nic.utils.DateUtil;
import com.nec.asia.nic.utils.HelperClass;

@Path("/syncMaster/")
@WebService
@Provider
public class MasterService {

	public final Logger logger = LoggerFactory.getLogger(this.getClass());
	public final static String MODE = "ADD";
	public final static String ACTIVE_SITE_REPOSITORY = "Y";

	@Autowired
	private CodesService codesService;

	@Autowired
	private SiteService siteService;

	@Autowired
	private ParametersService parametersService;

	@Autowired
	private PaymentDefService paymentDefService;

	@Autowired
	private UserService userService;

	@Autowired
	private RoleService roleService;

	@Autowired
	private SyncQueueJobService queueJobService;

	@Autowired
	private RptStatisticsTransmitDataService rptService;
	
	@Autowired
	private EppWsLogService eppWsLogService;

	@GET
	@Produces("application/json")
	@Consumes("application/json")
	@Path("/downloadDataMaster/{type}")
	public Response<MasterInfos> downloadDataMaster(
			@PathParam("type") String type) {
		Response<MasterInfos> response = new Response<MasterInfos>();
		response.setCode(Contants.RESPONSE_CODE_FAIL_API);
		response.setMessage(Contants.RESPONSE_MESSAGE_FAIL_API);
		MasterInfos infos = new MasterInfos();
		try {
			switch (type) {
			case "NATIONALITY":
				List<MasterInfo> listNl = this
						.findDataMasterBySync(RegistrationConstants.CODE_VALUE_NATIONALITY);
				infos.setListInfo(listNl);
				break;
			case "DISTRICT":
				List<MasterInfo> listDt = this
						.findDataMasterBySync(RegistrationConstants.CODE_VALUE_DISTRICT);
				infos.setListInfo(listDt);
				break;
			case "TOWN":
				List<MasterInfo> listTv = this
						.findDataMasterBySync(RegistrationConstants.CODE_VALUE_TOWN_VILLAGE);
				infos.setListInfo(listTv);
				break;
			case "NATION":
				List<MasterInfo> listNt = this
						.findDataMasterBySync(RegistrationConstants.CODE_VALUE_CODE_NATION);
				infos.setListInfo(listNt);
				break;
			case "RELIGION":
				List<MasterInfo> listRl = this
						.findDataMasterBySync(RegistrationConstants.CODE_VALUE_CODE_RELIGION);
				infos.setListInfo(listRl);
				break;
			default:
				break;
			}
			response.setData(infos);
			response.setCode(Contants.RESPONSE_CODE_SUCCESS_API);
			response.setMessage(Contants.RESPONSE_MESSAGE_SUCCESS_API);

			/* Lưu bảng thống kê truyền nhận */
			if (infos.getListInfo().size() > 0) {
				this.saveOrUpRptData(Contants.URL_DOWNLOAD_DATA_MASTER,
						response.getData().getListInfo().size(), type);
			}

			// logger.info("success download data master, time = " + new
			// Date());
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("error download data master, err = " + e.getMessage());
			response.setMessage("ERROR === " + e.getMessage());
		}
		return response;
	}

	@GET
	@Produces("application/json")
	@Consumes("application/json")
	@Path("/downloadSite")
	public Response<SiteInfos> downloadSite() {
		Response<SiteInfos> response = new Response<SiteInfos>();
		response.setCode(Contants.RESPONSE_CODE_FAIL_API);
		response.setMessage(Contants.RESPONSE_MESSAGE_FAIL_API);
		SiteInfos infos = new SiteInfos();
		try {
			List<SiteRepository> listSite = siteService.findAllByActive(null);
			if (listSite != null) {
				List<SiteInfo> list = new ArrayList<SiteInfo>();
				for (SiteRepository site : listSite) {
					SiteInfo info = new SiteInfo();
					info.setSiteId(site.getSiteId());
					info.setSiteFunction(site.getSiteFunctionType());
					info.setSiteName(site.getSiteName());
					info.setCountry(site.getCountry());
					info.setAddress(site.getAddress());
					info.setActive(site.getActiveIndicator());
					info.setA08Id(site.getA08Id());
					info.setGroupId(site.getSiteGroups().getGroupId());
					list.add(info);
				}
				infos.setListInfo(list);
			}
			response.setData(infos);
			response.setCode(Contants.RESPONSE_CODE_SUCCESS_API);
			response.setMessage(Contants.RESPONSE_MESSAGE_SUCCESS_API);

			/* Lưu bảng thống kê truyền nhận */
			if (infos.getListInfo().size() > 0) {
				this.saveOrUpRptData(Contants.URL_DOWNLOAD_SITE, response
						.getData().getListInfo().size(), null);
			}

			// logger.info("success download data site, time = " + new Date());
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("error download data site, err = " + e.getMessage());
			response.setMessage("ERROR === " + e.getMessage());
		}
		return response;
	}

	@GET
	@Produces("application/json")
	@Consumes("application/json")
	@Path("/downloadFeePayment")
	public Response<FeeInfos> downloadFeePayment() {
		Response<FeeInfos> response = new Response<FeeInfos>();
		response.setCode(Contants.RESPONSE_CODE_FAIL_API);
		response.setMessage(Contants.RESPONSE_MESSAGE_FAIL_API);
		FeeInfos infos = new FeeInfos();
		try {
			List<PaymentDef> listPay = paymentDefService.findAll();
			if (listPay != null) {
				List<FeeInfo> list = new ArrayList<FeeInfo>();
				for (PaymentDef def : listPay) {
					FeeInfo info = new FeeInfo();
					info.setCodeFee(def.getTransactionSubtype());
					info.setNameFee(def.getName());
					info.setFeeAmount(def.getFeeAmount());
					info.setTypePayment(def.getTypePayment());
					info.setA08Id(def.getA08Id());
					if (def.getDeleteFlag() != null)
						info.setDeleteFlag(def.getDeleteFlag() ? "Y" : "N");
					list.add(info);
				}
				infos.setListInfo(list);
			}
			response.setData(infos);
			response.setCode(Contants.RESPONSE_CODE_SUCCESS_API);
			response.setMessage(Contants.RESPONSE_MESSAGE_SUCCESS_API);

			/* Lưu bảng thống kê truyền nhận */
			if (infos.getListInfo().size() > 0) {
				this.saveOrUpRptData(Contants.URL_DOWNLOAD_FEE_PAYMENT,
						response.getData().getListInfo().size(), null);
			}

			// logger.info("success download data payment def, time = " + new
			// Date());
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("error download data payment def, err = "
					+ e.getMessage());
			response.setMessage("ERROR === " + e.getMessage());
		}
		return response;
	}

	@POST
	@Produces("application/json")
	@Consumes("application/json")
	@Path("/uploadUser")
	public ResponseBase uploadUser(UserInfo userIn) {
		ResponseBase response = new ResponseBase();
		response.setCode(Contants.RESPONSE_CODE_FAIL_API);
		response.setMessage(Contants.RESPONSE_MESSAGE_FAIL_API);
		try {
			// [chris][20 Dec 2013] - start
			// logger.info("[saveUser]userId:{}, mode:{}", userIn.getUserId(),
			// "ADD");

			ADUser user = userService.findUserByUserId(userIn.getUserId());
			ADUser origUser = userService.findUserByUserId(userIn.getUserId());

			// logger.info("[check equal]userId '{}' Already Exist.",
			// userIn.getUserId());

			if (origUser != null) {
				response.setMessage(Contants.MESSAGE_FAIL_UPLOAD_USER);
				return response;
			}

			if (origUser == null) {
				user = new ADUser();
			}
			user.setRoles(null);
			user.setEmail(userIn.getEmail());
			user.setPassword(userIn.getPassword());
			if (userIn.getRoles() != null && userIn.getRoles().size() > 0) {
				for (String roleId : userIn.getRoles()) {
					Roles roles = roleService.findById(roleId);
					user.addRole(roles);
				}
			}
			try {

				SiteRepository siteR = siteService.getSiteRepoById(userIn
						.getSiteCode());
				if (siteR != null) {
					user.setSiteGroupCode(siteR.getSiteGroups().getGroupId());
					user.setSiteCode(userIn.getSiteCode());
				}
				/* add thông tin phòng ban + chức vụ */
				user.setDepartment(userIn.getDepartment());
				user.setPosition(userIn.getPosition());
				user.setUserName(userIn.getUserName());
				/*------*/
				if (origUser == null) {
					user.setCreateDate(HelperClass.convertStringToDate(userIn
							.getCreateDate(), userIn.getCreateDate().length()));
					user.setCreateBy(userIn.getCreateBy());
					user.setCreateWkstnId(userIn.getCreateWsktn());
					user.setSystemId("PA");
					userService.createUser(user);
					// logger.info("[saveUser]createUser('{}').",
					// user.getUserId());
				}
			} catch (UserCRUDException e) {
				response.setMessage("Lỗi khi lưu người dùng, err: "
						+ e.getMessage());
				logger.error("UserCRUDException = " + e.getMessage());
				return response;

			} catch (ExistingActiveDirectoryException e) {
				response.setMessage("Lỗi khi lưu người dùng,err : "
						+ e.getMessage());
				logger.error("ExistingActiveDirectoryException = "
						+ e.getMessage());
				return response;
			}
			List<SiteRepository> siteList = siteService
					.findAllByActive(ACTIVE_SITE_REPOSITORY);
			if (siteList != null) {
				for (SiteRepository site : siteList) {
					if (!userIn.getSiteCode().equals(site.getSiteId())) {
						Boolean check = this.addObjToQueueJob(
								userIn.getUserId(),
								RegistrationConstants.QUEUE_OBJ_TYPE_UR,
								site.getSiteId(), userIn.getPassword());
						if (check) {
							/* Lưu bảng thống kê truyền nhận */
							this.saveOrUpRptData(Contants.URL_UPLOAD_USER, 1,
									site.getSiteId());
						}
					}
				}
			}
			response.setCode(Contants.RESPONSE_CODE_SUCCESS_API);
			response.setMessage(Contants.RESPONSE_MESSAGE_SUCCESS_API);
			// logger.info("success upload user to TTDH, time = " + new Date());
		} catch (Exception e) {
			e.printStackTrace();
			response.setCode(Contants.CODE_THROW_EXCEPTION);
			response.setMessage(Contants.MESSAGE_EXCEPTION);
			/*
			 * Lưu dữ liệu nếu có Exception
			 */
			String dataResponse = "";
			String dataRequest = "";
			String keyId = "UU_" + new SimpleDateFormat("ddMMyyyy").format(new Date());
			try {
				dataResponse = new ObjectMapper()
				.writeValueAsString(response);
				dataRequest = new ObjectMapper()
				.writeValueAsString(userIn);
			} catch (Exception e2) {
				logger.error(e2.getMessage());
			}
			this.saveException(Contants.TYPE_UU, Contants.URL_UPLOAD_USER,
					dataRequest, keyId, dataResponse, e);
		}
		return response;
	}

	@GET
	@Produces("application/json")
	@Consumes("application/json")
	@Path("/downloadUser/{site}")
	public Response<UserInfo> downloadUser(@PathParam("site") String site) {
		Response<UserInfo> response = new Response<UserInfo>();
		response.setCode(Contants.RESPONSE_CODE_FAIL_API);
		response.setMessage(Contants.RESPONSE_MESSAGE_FAIL_API);
		try {
			BaseModelSingle<SyncQueueJob> baseFindQ = queueJobService
					.findQueueByStatus(site,
							RegistrationConstants.CODE_STATUS_QUEUE_PENDING,
							RegistrationConstants.QUEUE_OBJ_TYPE_UR);
			SyncQueueJob queue = baseFindQ.getModel();
			if (queue != null) {
				UserInfo info = new UserInfo();
				Users users = userService.findByUserId(queue.getObjectId());
				if (users != null) {
					info.setActive(users.getActiveIndicator() ? "Y" : "N");
					info.setCreateBy(users.getCreateBy());
					String crDate = service.perso.util.HelperClass
							.convertDateToString(
									users.getCreateDate(),
									TransactionService.STR_FORMAT_DATE_yyyyMMddHHmmss);
					info.setCreateDate(crDate);
					info.setCreateWsktn(users.getCreateWkstnId());
					info.setDepartment(users.getDepartment());
					info.setPassword(users.getPasswordTemp());
					info.setPosition(users.getPosition());
					info.setSiteCode(users.getSiteCode());
					info.setSysAdminFlag(users.getSysAdminFlag() ? "Y" : "N");
					info.setUserId(users.getUserId());
					info.setUserName(users.getUserName());
					List<String> roles = new ArrayList<String>();
					if (users.getRoles() != null) {
						for (Roles role : users.getRoles()) {
							roles.add(role.getRoleId());
						}
					}
					info.setRoles(roles);
				}
				response.setData(info);
				response.setCode(Contants.RESPONSE_CODE_SUCCESS_API);
				response.setMessage(Contants.RESPONSE_MESSAGE_SUCCESS_API);

				// logger.info("success download user, time = " + new Date());
				queueJobService.updateStatusQueueJob(queue.getId(),
						Contants.CODE_STATUS_QUEUE_DOING);

				/* Lưu bảng thống kê truyền nhận */
				if (info.getUserId() != null
						&& queue.getDateUpdateStatusHanding() == null) {
					this.saveOrUpRptData(Contants.URL_DOWNLOAD_USER, 1, site);
				}

			} else {
				response.setCode(Contants.RESPONSE_CODE_SUCCESS_API);
				response.setMessage(Contants.RESPONSE_MESSAGE_NULL_DATA);
			}
		} catch (Exception e) {
			e.printStackTrace();
			response.setCode(Contants.CODE_THROW_EXCEPTION);
			response.setMessage(Contants.MESSAGE_EXCEPTION);
			/*
			 * Lưu dữ liệu nếu có Exception
			 */
			String dataResponse = "";
			String keyId = "DU_" + new SimpleDateFormat("ddMMyyyy").format(new Date());
			try {
				dataResponse = new ObjectMapper()
				.writeValueAsString(response);
			} catch (Exception e2) {
				logger.error(e2.getMessage());
			}
			this.saveException(Contants.TYPE_DU, Contants.URL_DOWNLOAD_USER,
					site, keyId, dataResponse, e);
		}
		return response;
	}

	public List<MasterInfo> findDataMasterBySync(String codeId) {
		try {
			List<CodeValues> codeList = codesService.findAllByCodeId(codeId);
			if (codeList != null) {
				List<MasterInfo> list = new ArrayList<MasterInfo>();
				for (CodeValues code : codeList) {
					MasterInfo info = new MasterInfo();
					// info.setCodeId(code.getId().getCodeId());
					info.setCode(code.getId().getCodeValue());
					info.setName(code.getCodeValueDesc());
					info.setParentCode(code.getParentCodeValue());
					info.setPriority(code.getCodePriority());
					if (code.getDeleteFlag() != null)
						info.setDeleteFlag(code.getDeleteFlag() ? "Y" : "N");
					info.setA08ID(code.getA08Id());
					list.add(info);
				}
				return list;
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("error get list nationality, err = " + e.getMessage());
		}
		return null;
	}

	private Boolean addObjToQueueJob(String userId, String ObjType,
			String receiver, String password) {
		try {
			if (queueJobService.findQueueByObjectId(userId, receiver, password,
					ObjType).getModel() == null) {
				SyncQueueJob queue = new SyncQueueJob();
				queue.setCreatedTs(Calendar.getInstance().getTime());
				queue.setObjectId(userId);
				queue.setObjectType(ObjType);
				queue.setReceiver(receiver);
				queue.setTranStatus(password);
				queue.setStatus(Contants.CODE_STATUS_QUEUE_PENDING);
				queue.setSyncRetry(1);
				queueJobService.saveOrUpdateQueue(queue);
				return true;
			}
		} catch (Exception e) {

		}
		return false;
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
