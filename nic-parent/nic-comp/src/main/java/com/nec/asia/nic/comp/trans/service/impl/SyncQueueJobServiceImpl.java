package com.nec.asia.nic.comp.trans.service.impl;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nec.asia.nic.comp.listHandover.service.PersoLocationsService;
import com.nec.asia.nic.comp.trans.dao.SyncQueueJobDao;
import com.nec.asia.nic.comp.trans.domain.BaseModelList;
import com.nec.asia.nic.comp.trans.domain.BaseModelSingle;
import com.nec.asia.nic.comp.trans.domain.StatusticalIfor;
import com.nec.asia.nic.comp.trans.domain.SyncQueueJob;
import com.nec.asia.nic.comp.trans.domain.statusticalLogWs;
import com.nec.asia.nic.comp.trans.domain.ConvertQueue;
import com.nec.asia.nic.comp.trans.dto.SyncQueueDto;
import com.nec.asia.nic.comp.trans.service.SyncQueueJobService;
import com.nec.asia.nic.comp.trans.utils.RegistrationConstants;
import com.nec.asia.nic.comp.ws.log.domain.CreateMessageException;
import com.nec.asia.nic.comp.ws.log.domain.EppWsLog;
import com.nec.asia.nic.comp.ws.log.service.EppWsLogService;
import com.nec.asia.nic.framework.PaginatedResult;
import com.nec.asia.nic.framework.admin.code.domain.Parameters;
import com.nec.asia.nic.framework.admin.code.service.ParametersService;
import com.nec.asia.nic.framework.admin.site.domain.SiteRepository;
import com.nec.asia.nic.framework.admin.site.service.SiteService;
import com.nec.asia.nic.framework.service.impl.DefaultBusinessServiceImpl;
import com.nec.asia.nic.utils.DateUtil;

@Service("queueJobService")
public class SyncQueueJobServiceImpl extends
		DefaultBusinessServiceImpl<SyncQueueJob, Long, SyncQueueJobDao>
		implements SyncQueueJobService {

	@Autowired
	private SyncQueueJobDao dao;

	@Autowired
	private ParametersService parametersService;

	@Autowired
	private SiteService siteService;

	@Autowired
	private PersoLocationsService locationService;

	@Autowired
	private EppWsLogService EppWsLogService;

	@Override
	public BaseModelSingle<Boolean> saveOrUpdateQueue(SyncQueueJob queue) {
		try {
			return this.dao.saveOrUpdateQueue(queue);
		} catch (Exception e) {
			e.printStackTrace();
			return new BaseModelSingle<Boolean>(false, false,
					CreateMessageException.createMessageException(e)
							+ " - saveOrUpdateQueue: " + queue.getObjectId()
							+ " - thất bại.");
		}

	}

	@Override
	public void updateQueueJob(Long id, String status) throws Exception {
		try {
			SyncQueueJob queue = this.dao.findQueueByInfo(id, null);
			if (queue != null) {
				if (status
						.equals(RegistrationConstants.CODE_UPDATE_SUCCESS_API)) {
					// this.dao.delete(queue);
					queue.setStatus(RegistrationConstants.CODE_STATUS_QUEUE_DONE);
					queue.setSyncTs(Calendar.getInstance().getTime());
					this.dao.saveOrUpdate(queue);
				} else if (status
						.equals(RegistrationConstants.CODE_UPDATE_FAIL_API)) {
					Parameters pr = parametersService
							.getParamDetails(
									RegistrationConstants.PARAMETERS_SCOPE_SYSTEM,
									RegistrationConstants.PARAMETERS_NAME_MAX_COUNT_CALL_QUEUE);
					int maxDefault = 10;
					if (pr != null) {
						maxDefault = Integer.valueOf(pr.getParaShortValue());
					}
					if (queue.getSyncRetry() < maxDefault) {
						queue.setStatus(RegistrationConstants.CODE_STATUS_QUEUE_PENDING);
						queue.setSyncTs(Calendar.getInstance().getTime());
						queue.setSyncRetry(queue.getSyncRetry() + 1);
						this.dao.saveOrUpdate(queue);
					} else {
						queue.setStatus(RegistrationConstants.CODE_STATUS_QUEUE_NONE);
						queue.setSyncTs(Calendar.getInstance().getTime());
						this.dao.saveOrUpdate(queue);
					}
				} else if (status
						.equals(RegistrationConstants.CODE_UPDATE_RECALL_API)) {
					queue.setStatus(RegistrationConstants.CODE_STATUS_QUEUE_PENDING);
					queue.setSyncTs(Calendar.getInstance().getTime());
					this.dao.saveOrUpdate(queue);
				}
			}
		} catch (Exception e) {
			throw new Exception(
					CreateMessageException.createMessageException(e)
							+ " updateQueueJob fail.");
		}

	}

	@Override
	public BaseModelList<SyncQueueJob> findQueueByReceiver(String receiver,
			String status, String objType, int maxTotal) throws Exception {
		List<SyncQueueJob> listSync = new ArrayList<SyncQueueJob>();
		try {
			List<SyncQueueJob> list = this.dao.findQueueByReceiver(receiver,
					status, objType, maxTotal).getListModel();
			if (list != null) {
				if (list.size() <= maxTotal) {
					listSync.addAll(list);
				} else {
					int i = 0;
					for (SyncQueueJob syn : list) {
						listSync.add(syn);
						if (i >= (maxTotal - 1))
							break;
						i++;
					}
				}
			}
		} catch (Throwable e) {
			return new BaseModelList<SyncQueueJob>(null, false,
					CreateMessageException
							.createMessageException(new Exception(e))
							+ " - findQueueByReceiver - "
							+ receiver
							+ " - thất bại.");
		}
		return new BaseModelList<SyncQueueJob>(listSync, true, null);
	}

	@Override
	public BaseModelSingle<Boolean> updateStatusQueueJob(Long id, String status)
			throws Exception {
		try {
			return this.dao.updateStatusQueueJob(id, status);
		} catch (Throwable e) {
			return new BaseModelSingle<Boolean>(false, false,
					CreateMessageException
							.createMessageException(new Exception(e))
							+ " - updateStatusQueueJob - " + id + " - thất bại");
		}
	}

	@Override
	public BaseModelSingle<SyncQueueJob> findQueueByStatus(String receiver,
			String status, String objType) throws Exception {
		try {
			return this.dao.findQueueByStatus(receiver, status, objType);
		} catch (Exception e) {
			return new BaseModelSingle<SyncQueueJob>(null, true,
					CreateMessageException.createMessageException(e)
							+ " - findQueueByStatus - " + status
							+ " - thất bại.");
		}

	}

	@Override
	public BaseModelSingle<SyncQueueJob> findQueueByObjectId(String objectId,
			String receiver, String status, String objType) throws Exception {
		try {
			return this.dao.findQueueByObjectId(objectId, receiver, status,
					objType);
		} catch (Exception e) {
			return new BaseModelSingle<SyncQueueJob>(null, false,
					CreateMessageException.createMessageException(e)
							+ " - findQueueByObjectId - " + objectId
							+ " - thất bại.");
		}

	}

	@Override
	public PaginatedResult<SyncQueueDto> findListQueueBySearch(String objectId,
			String objectType, String status, String receiver, int pageNo,
			int pageSize) {
		PaginatedResult<SyncQueueDto> result = null;
		try {
			PaginatedResult<SyncQueueJob> pr = this.dao.findListQueueBySearch(
					objectId, objectType, status, receiver, pageNo, pageSize);
			List<SyncQueueDto> list = new ArrayList<SyncQueueDto>();
			if (pr != null) {
				int i = (pageNo - 1) * pageSize;
				for (SyncQueueJob queue : pr.getRows()) {
					SyncQueueDto dto = new SyncQueueDto();
					i++;
					dto.setStt(i);
					dto.setId(queue.getId());
					dto.setCode(queue.getObjectId());
					dto.setCreateDate(queue.getCreatedTs());
					dto.setCount(queue.getSyncRetry());
					dto.setDateUpdate(queue.getDateUpdateStatusHanding());
					dto.setType(this.convertObjType(queue.getObjectType()));
					String statusStr = "";
					statusStr = ConvertQueue.converCodeToStatus(queue
							.getStatus());
					dto.setStage(statusStr);
					if (queue.getReceiver().equals("A08_HH")) {
						dto.setSite("Hệ thống hiện hành (hệ thống cũ)");
					} else {
						SiteRepository site = siteService.getSiteRepoById(queue
								.getReceiver());
						dto.setSite(site != null ? site.getSiteName() : queue
								.getReceiver());
					}
					dto.setStageId(queue.getStatus());
					dto.setTypeId(queue.getObjectType());
					list.add(dto);
				}
				result = new PaginatedResult<SyncQueueDto>(pr.getTotal(),
						pageNo, list);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	@Override
	public SyncQueueJob findQueueByInfo(Long id, String status)
			throws Exception {
		try {
			SyncQueueJob queue = this.dao.findQueueByInfo(id, status);
			return queue;
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception(
					CreateMessageException.createMessageException(e)
							+ " findQueueByInfo fail.");
		}
	}

	// String[] objectType={"HS","DA","DB","DC","PP","UPP","HSF","BF","PS"};
	public List<StatusticalIfor> findStatusAll(String loaiHangDoi,
			String dateFrom, String dateTo, String receiver) {
		List<StatusticalIfor> list = new ArrayList<StatusticalIfor>();

		try {
			if (loaiHangDoi.equals("")) {
				for (String str : ConvertQueue.objectType) {
					list.add(new StatusticalIfor(str, this.dao
							.findQueueByPropertyAll(dateFrom, dateTo, "DONE",
									receiver, str).size(), this.dao
							.findQueueByPropertyAll(dateFrom, dateTo,
									"PENDING", receiver, str).size(), this.dao
							.findQueueByPropertyAll(dateFrom, dateTo, "DOING",
									receiver, str).size(), this.dao
							.findQueueByPropertyAll(dateFrom, dateTo, "NONE",
									receiver, str).size(), this.dao
							.findQueueByPropertyAll(dateFrom, dateTo, "",
									receiver, str).size()));
				}
				for (StatusticalIfor st : list) {
					String objectType = st.getLoaiHangDoi();
					st.setLoaiHangDoi(ConvertQueue
							.convertCodeToString(objectType));
				}
				return list;
			} else {
				list.add(new StatusticalIfor(loaiHangDoi, this.dao
						.findQueueByPropertyAll(dateFrom, dateTo, "DONE",
								receiver, loaiHangDoi).size(), this.dao
						.findQueueByPropertyAll(dateFrom, dateTo, "PENDING",
								receiver, loaiHangDoi).size(), this.dao
						.findQueueByPropertyAll(dateFrom, dateTo, "DOING",
								receiver, loaiHangDoi).size(), this.dao
						.findQueueByPropertyAll(dateFrom, dateTo, "NONE",
								receiver, loaiHangDoi).size(), this.dao
						.findQueueByPropertyAll(dateFrom, dateTo, "", receiver,
								loaiHangDoi).size()));
				String type = list.get(0).getLoaiHangDoi();
				list.get(0).setLoaiHangDoi(
						ConvertQueue.convertCodeToString(type));
			}

			return list;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public List<StatusticalIfor> findStatusByDate(String date) {
		List<StatusticalIfor> list = new ArrayList<StatusticalIfor>();
		try {

			for (String str : ConvertQueue.objectType) {
				list.add(new StatusticalIfor(str, this.dao.findQueueByDate(
						date, "DONE", str).size(), this.dao.findQueueByDate(
						date, "PENDING", str).size(), this.dao.findQueueByDate(
						date, "DOING", str).size(), this.dao.findQueueByDate(
						date, "NONE", str).size(), this.dao.findQueueByDate(
						date, "", str).size()));
			}
			for (StatusticalIfor st : list) {
				String objectType = st.getLoaiHangDoi();
				st.setLoaiHangDoi(ConvertQueue.convertCodeToString(objectType));
			}
			return list;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}

		return null;
	}

	@Override
	public List<statusticalLogWs> findQueueByAllProperty(String dateFrom,
			String dateTo, String status, String receiver, String objecType) {
		List<statusticalLogWs> list = new ArrayList<statusticalLogWs>();
		try {
			List<SyncQueueJob> listQueue = this.dao.findQueueByPropertyAll(
					dateFrom, dateTo, status, receiver, objecType);
			if (listQueue != null) {
				for (SyncQueueJob sync : listQueue) {
					statusticalLogWs st = new statusticalLogWs();
					st.setMaHangDoi(sync.getObjectId());
					st.setSoLanGoi(sync.getSyncRetry());
					EppWsLog epp = this.EppWsLogService.getWsLogByKeyId(
							sync.getObjectId()).get(0);
					DateFormat date = new SimpleDateFormat("DD-MM-YYYY");
					st.setNgayGoiCuoi(date.format(epp.getCreatedDate()));
					st.setMessage(epp.getMessageErrorLog());
					list.add(st);
				}
				return list;
			} else {
				return null;
			}

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public PaginatedResult<SyncQueueJob> findListQueueBySearch(String dateFrom,
			String dateTo, String status, String receiver, String objecType,
			int pageNo, int pageSize) {
		// PaginatedResult<statusticalLogWs> pag=new
		// PaginatedResult<statusticalLogWs>();

		try {
			String objectType = ConvertQueue.convertStringToCode(objecType);
			String date = "";
			SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
			PaginatedResult<SyncQueueJob> pag = this.dao
					.findListQueueBySearchAll(dateFrom, dateTo, status,
							receiver, objectType, pageNo, pageSize);
			for (SyncQueueJob syn : pag.getRows()) {
				syn.setObjectType(ConvertQueue.convertCodeToString(syn
						.getObjectType()));
				syn.setStatus(ConvertQueue.converCodeToStatus(syn.getStatus()));
				date = dateFormat.format(syn.getCreatedTs());
				syn.setCreatedTs(dateFormat.parse(date));
				// syn.setCreatedTs(dateFormat.format(syn.getCreatedTs()));
			}
			return pag;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}

		return null;
	}

	public PaginatedResult<SyncQueueJob> findListQueueByDateCurrent(
			String dateCurrent, String status, String receiver,
			String objecType, int pageNo, int pageSize) {
		try {
			String objectType = ConvertQueue.convertStringToCode(objecType);
			String date = "";
			SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
			PaginatedResult<SyncQueueJob> pag = this.dao
					.findListQueueByDateCurrent(dateCurrent, status, receiver,
							objectType, pageNo, pageSize);
			for (SyncQueueJob syn : pag.getRows()) {
				syn.setObjectType(ConvertQueue.convertCodeToString(syn
						.getObjectType()));
				syn.setStatus(ConvertQueue.converCodeToStatus(syn.getStatus()));
				date = dateFormat.format(syn.getCreatedTs());
				syn.setCreatedTs(dateFormat.parse(date));
			}
			return pag;
		} catch (Exception e) {
			e.printStackTrace();
			// TODO: handle exception
		}
		return null;
	}

	@Override
	public SyncQueueJob findSyncQueueJobByObjIdOrReceiver(String objectId,
			String objectType, String receiver, String status) throws Exception {
		try {
			List<SyncQueueJob> list = this.dao
					.findSyncQueueJobByObjIdOrReceiver(objectId, objectType,
							receiver, status);
			if (list != null && list.size() > 0) {
				return list.get(0);
			} else {
				return null;
			}
		} catch (Exception e) {
			throw new Exception(
					CreateMessageException.createMessageException(e)
							+ " - findSyncQueueJobByObjIdOrReceiver thất bại.");
		}
	}

	@Override
	public Boolean saveOrUpdateSyncQueue(SyncQueueJob syncQueueJob)
			throws Exception {
		Boolean check = false;
		try {
			BaseModelSingle<Boolean> baseCheck = this.dao
					.saveOrUpdateQueue(syncQueueJob);
			if (!baseCheck.isError() || baseCheck.getMessage() != null) {
				throw new Exception(baseCheck.getMessage());
			}
			check = true;
		} catch (Exception e) {
			throw new Exception(
					CreateMessageException.createMessageException(e)
							+ " - saveOrUpdateSyncQueue thất bại.");
		}
		return check;
	}

	@Override
	public SyncQueueJob findSyncQueueJobByManyObjType(String transactionId,
			String[] strings, String siteCode, String codeStatusQueuePending)
			throws Exception {
		try {
			List<SyncQueueJob> list = this.dao.findSyncQueueJobByManyObjType(
					transactionId, strings, siteCode, codeStatusQueuePending);
			if (list != null && list.size() > 0) {
				return list.get(0);
			} else {
				return null;
			}
		} catch (Exception e) {
			throw new Exception(
					CreateMessageException.createMessageException(e)
							+ " - findSyncQueueJobByManyObjType thất bại.");
		}
	}

	@Override
	public int countByConditions(String objectType, String site, String status,
			Date date) throws Exception {
		try {
			return this.dao.countByConditions(objectType, site, status, date);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}

	@Override
	public PaginatedResult<SyncQueueDto> getPageByCodition(String fromDate,
			String toDate, String receiver, String objType, int pageNo,
			int pageSize) {
		PaginatedResult<SyncQueueDto> pag = null;
		try {
			Date fDate = null;
			Date tDate = null;
			if (StringUtils.isNotBlank(fromDate)) {
				fDate = DateUtil.strToDate(fromDate, DateUtil.FORMAT_YYYYMMDD);
			}
			if (StringUtils.isNotBlank(toDate)) {
				tDate = new Date(DateUtil.strToDate(toDate,
						DateUtil.FORMAT_YYYYMMDD).getTime() + 86400000L);
			}
			PaginatedResult<SyncQueueJob> pr = this.dao.getPageByCodition(
					fDate, tDate, receiver, objType, pageNo, pageSize);
			if (pr != null && pr.getRows() != null && pr.getRowSize() > 0) {
				pag = new PaginatedResult<SyncQueueDto>(pr.getTotal(),
						pr.getPage(), new ArrayList<SyncQueueDto>());
				int i = (pageNo - 1) < 0 ? 0 : (pageNo - 1) * pageSize;
				for (SyncQueueJob queue : pr.getRows()) {
					SyncQueueDto dto = new SyncQueueDto();
					i++;
					dto.setStt(i);
					dto.setId(queue.getId());
					dto.setCode(queue.getObjectId());
					dto.setCreateDate(queue.getCreatedTs());
					dto.setStrCreateDate(queue.getCreatedTs() != null ? DateUtil
							.parseDate(queue.getCreatedTs(),
									DateUtil.FORMAT_DDdashMMdashYYYY_HH24_MM_SS) : "");
					dto.setCount(queue.getSyncRetry());
					dto.setDateUpdate(queue.getDateUpdateStatusHanding());
					dto.setStrUpdateDate(queue.getDateUpdateStatusHanding() != null ? DateUtil
							.parseDate(queue.getDateUpdateStatusHanding(),
									DateUtil.FORMAT_DDdashMMdashYYYY_HH24_MM_SS) : "");
					dto.setType(this.convertObjType(queue.getObjectType()));
					String statusStr = "";
					statusStr = ConvertQueue.converCodeToStatus(queue
							.getStatus());
					dto.setStage(statusStr);
					dto.setSite(queue.getReceiver());
					dto.setStageId(queue.getStatus());
					dto.setTypeId(queue.getObjectType());
					pag.getRows().add(dto);
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return pag;
	}

	private String convertObjType(String objectType) {
		switch (objectType) {
		case "HS_TO_A08":
			objectType = "Hồ sơ xử lý";
			break;
		case "HS_ISSUANCE":
			objectType = "Hồ sơ trả kết quả";
			break;
		case "DOC_LOST":
			objectType = "Hộ chiếu mất/hủy";
			break;
		case "ARCHIVE":
			objectType = "Số hồ sơ lưu";
			break;
		case "DB_TO_A08":
			objectType = "Danh sách B";
			break;
		case "PP_TO_A08":
			objectType = "Hộ chiếu";
			break;
		case "HS":
			objectType = "Hồ sơ xử lý";
			break;
		case "HSF":
			objectType = "Hồ sơ xử lý đầy đủ";
		case "DA":
			objectType = "Danh sách A";
			break;
		case "DB":
			objectType = "Danh sách B";
			break;
		case "DSC":
			objectType = "Danh sách C";
			break;
		case "DC":
			objectType = "Danh sách C";
			break;
		case "BF":
			objectType = "Hồ sơ nghi trùng";
			break;
		case "IN":
			objectType = "Cá thể hóa";
			break;
		case "PS":
			objectType = "Thông tin Person";
			break;
		case "PP":
			objectType = "Hộ chiếu";
			break;
		case "UPP":
			objectType = "Cập nhật hộ chiếu";
			break;
		case "BF_CPD":
			objectType = "Cá nhân nghi trùng";
			break;
		case "USER_ADD":
			objectType = "Thông tin người dùng";
			break;
		case "DA_TO_A_SUCC":
			objectType = "Danh sách A gửi thành công";
			break;
		case "DOC_RESTORE":
			objectType = "Hộ chiếu khôi phục";
			break;

		default:
			break;
		}
		return objectType;
	}

	@Override
	public List<SyncQueueDto> getAllByCodition(String fromDate, String toDate,
			String receiver, String objType) {
		List<SyncQueueDto> pag = null;
		try {
			Date fDate = null;
			Date tDate = null;
			if (StringUtils.isNotBlank(fromDate)) {
				fDate = DateUtil.strToDate(fromDate, DateUtil.FORMAT_YYYYMMDD);
			}
			if (StringUtils.isNotBlank(toDate)) {
				tDate = new Date(DateUtil.strToDate(toDate,
						DateUtil.FORMAT_YYYYMMDD).getTime() + 86400000L);
			}
			List<SyncQueueJob> pr = this.dao.getPageByCodition(
					fDate, tDate, receiver, objType);
			if (pr != null && pr.size() > 0) {
				pag = new ArrayList<SyncQueueDto>();
				int i = 0;
				for (SyncQueueJob queue : pr) {
					SyncQueueDto dto = new SyncQueueDto();
					i++;
					dto.setStt(i);
					dto.setId(queue.getId());
					dto.setCode(queue.getObjectId());
					dto.setCreateDate(queue.getCreatedTs());
					dto.setStrCreateDate(queue.getCreatedTs() != null ? DateUtil
							.parseDate(queue.getCreatedTs(),
									DateUtil.FORMAT_DDdashMMdashYYYY_HH24_MM_SS) : "");
					dto.setCount(queue.getSyncRetry());
					dto.setDateUpdate(queue.getDateUpdateStatusHanding());
					dto.setStrUpdateDate(queue.getDateUpdateStatusHanding() != null ? DateUtil
							.parseDate(queue.getDateUpdateStatusHanding(),
									DateUtil.FORMAT_DDdashMMdashYYYY_HH24_MM_SS) : "");
					dto.setType(this.convertObjType(queue.getObjectType()));
					String statusStr = "";
					statusStr = ConvertQueue.converCodeToStatus(queue
							.getStatus());
					dto.setStage(statusStr);
					dto.setSite(queue.getReceiver());
					dto.setStageId(queue.getStatus());
					dto.setTypeId(queue.getObjectType());
					pag.add(dto);
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return pag;
	}
}
