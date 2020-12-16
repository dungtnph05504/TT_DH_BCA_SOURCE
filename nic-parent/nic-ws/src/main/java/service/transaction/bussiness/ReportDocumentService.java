package service.transaction.bussiness;

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

import jcifs.util.Base64;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import service.perso.model.PassportByPassportNo;
import service.perso.util.Contants;
import service.transaction.model.FindPassportInput;
import service.transaction.model.FindPassportProvinceReturnedByPassportNoOutput;
import service.transaction.model.FindPassportProvinceReturnedOutput;
import service.transaction.model.InvestigationAssignmentData;
import service.transaction.model.Response;
import service.transaction.model.ResponseBase;
import service.transaction.model.StatisticalPassportProvinceReturnedInput;
import service.transaction.model.UpdatePassportProvinceReturnedInput;
import service.transaction.model.UpdatePassportReturnedToProvinceInput;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nec.asia.nic.comp.listHandover.domain.NicListHandover;
import com.nec.asia.nic.comp.listHandover.service.ListHandoverService;
import com.nec.asia.nic.comp.trans.domain.BaseModelSingle;
import com.nec.asia.nic.comp.trans.domain.EppArchive;
import com.nec.asia.nic.comp.trans.domain.EppDocumentReturned;
import com.nec.asia.nic.comp.trans.domain.EppIssuance;
import com.nec.asia.nic.comp.trans.domain.EppListHandoverDetail;
import com.nec.asia.nic.comp.trans.domain.EppPerson;
import com.nec.asia.nic.comp.trans.domain.NicDocumentData;
import com.nec.asia.nic.comp.trans.domain.NicRegistrationData;
import com.nec.asia.nic.comp.trans.domain.NicTransaction;
import com.nec.asia.nic.comp.trans.domain.NicTransactionAttachment;
import com.nec.asia.nic.comp.trans.domain.NicUploadJob;
import com.nec.asia.nic.comp.trans.domain.RptStatisticsTransmitData;
import com.nec.asia.nic.comp.trans.dto.Datadto;
import com.nec.asia.nic.comp.trans.dto.HoSoDto;
import com.nec.asia.nic.comp.trans.dto.InforPassportDto;
import com.nec.asia.nic.comp.trans.dto.ListHandoverDto;
import com.nec.asia.nic.comp.trans.dto.PersonFamiliDto;
import com.nec.asia.nic.comp.trans.dto.StatisticalPassportProvinceReturnedDto;
import com.nec.asia.nic.comp.trans.dto.TranInfor;
import com.nec.asia.nic.comp.trans.service.DocumentDataService;
import com.nec.asia.nic.comp.trans.service.EppArchiveService;
import com.nec.asia.nic.comp.trans.service.EppDocumentReturnedService;
import com.nec.asia.nic.comp.trans.service.EppIssuanceService;
import com.nec.asia.nic.comp.trans.service.EppListHandoverDetailService;
import com.nec.asia.nic.comp.trans.service.EppPersonService;
import com.nec.asia.nic.comp.trans.service.NicRegistrationDataService;
import com.nec.asia.nic.comp.trans.service.NicTransactionAttachmentService;
import com.nec.asia.nic.comp.trans.service.NicTransactionLostService;
import com.nec.asia.nic.comp.trans.service.NicTransactionService;
import com.nec.asia.nic.comp.trans.service.NicUploadJobService;
import com.nec.asia.nic.comp.trans.service.RecieptManagerService;
import com.nec.asia.nic.comp.trans.service.RptStatisticsTransmitDataService;
import com.nec.asia.nic.comp.ws.log.domain.CreateMessageException;
import com.nec.asia.nic.comp.ws.log.domain.EppWsLog;
import com.nec.asia.nic.comp.ws.log.service.EppWsLogService;
import com.nec.asia.nic.framework.PaginatedResult;
import com.nec.asia.nic.framework.admin.code.domain.CodeValues;
import com.nec.asia.nic.framework.admin.code.service.CodesService;
import com.nec.asia.nic.framework.admin.site.domain.SiteRepository;
import com.nec.asia.nic.framework.admin.site.service.SiteService;
import com.nec.asia.nic.utils.DateUtil;
import com.nec.asia.nic.utils.HelperClass;

@Path("/rptDoc/")
@WebService
@Provider
public class ReportDocumentService {
	@Autowired
	private NicTransactionService nicTransactionService;
	@Autowired
	private RecieptManagerService receiptmanagerService;
	@Autowired
	private DocumentDataService documentDataService;
	@Autowired
	private CodesService codesService;
	@Autowired
	private EppPersonService eppPersonService;
	@Autowired
	private NicRegistrationDataService nicRegistrationDataService;
	@Autowired
	private ListHandoverService listHandoverService;
	@Autowired
	private EppListHandoverDetailService eppListHandoverDetailService;
	@Autowired
	private NicTransactionAttachmentService nicTransactionAttachmentService;
	@Autowired
	private EppDocumentReturnedService eppDocumentReturnedService;
	@Autowired
	private SiteService siteService;
	@Autowired
	private NicUploadJobService uploadJobService;
	@Autowired
	private EppWsLogService eppWsLogService;

	@Autowired
	private RptStatisticsTransmitDataService rptService;

	@Autowired
	private EppIssuanceService eppIssuanceService;

	@Autowired
	private EppArchiveService eppArchiveService;

	@Autowired
	private NicTransactionLostService transactionLostService;

	public final Logger logger = LoggerFactory.getLogger(this.getClass());

	// Tra cứu thông tin hồ sơ
	@POST
	@Produces("application/json")
	@Consumes("application/json")
	@Path("/findTransaction")
	public Response<List<Datadto>> findTransaction(
			InvestigationAssignmentData investigationAssignmentData) {
		Response<List<Datadto>> response = new Response<>();
		response.setCode(Contants.RESPONSE_CODE_FAIL_API);
		response.setMessage(Contants.RESPONSE_MESSAGE_FAIL_API);
		PaginatedResult<Datadto> pag = null;
		try {
			if (StringUtils.isEmpty(investigationAssignmentData.getName())
					&& StringUtils.isEmpty(investigationAssignmentData
							.getDateOfBirth())
					&& StringUtils.isEmpty(investigationAssignmentData
							.getGender())
					&& StringUtils.isEmpty(investigationAssignmentData
							.getIdNumber())
					&& StringUtils.isEmpty(investigationAssignmentData
							.getListCode())
					&& StringUtils.isEmpty(investigationAssignmentData
							.getPassportNo())
					&& StringUtils.isEmpty(investigationAssignmentData
							.getPassportType())
					&& StringUtils.isEmpty(investigationAssignmentData
							.getReceipNo())) {

				response.setCode(Contants.CODE_INPUT_FAILD);
				response.setMessage("Cần điền một điều kiện để tìm kiếm!");
				return response;

			}
			pag = nicTransactionService.allPassportStorageT(
					investigationAssignmentData.getName(),
					investigationAssignmentData.getDateOfBirth(),
					investigationAssignmentData.getIdNumber(),
					investigationAssignmentData.getGender(),
					investigationAssignmentData.getListCode(),
					investigationAssignmentData.getPassportNo(),
					investigationAssignmentData.getPassportType(),
					investigationAssignmentData.getReceipNo(),
					investigationAssignmentData.getPage(),
					investigationAssignmentData.getPageSize());
			List<Datadto> list = pag.getRows();
			if (list.size() > 0) {
				for (Datadto datadto : list) {
					if (StringUtils.isNotBlank(datadto.getPlaceOfBirth())) {
						CodeValues code = null;
						code = codesService.findByStringCodeId(
								CodeValues.CODE_ID_CODE_PLACE_OF_BIRTH,
								datadto.getPlaceOfBirth());
						if (code == null) {
							code = codesService.findByStringCodeId(
									CodeValues.CODE_ID_COUNTRY,
									datadto.getPlaceOfBirth());
						}
						datadto.setPlaceOfBirth(code != null ? code
								.getCodeValueDesc() : datadto.getPlaceOfBirth());
					}
				}
				response.setMessage(Contants.RESPONSE_MESSAGE_SUCCESS_API);
				response.setData(list);
				/* Lưu bảng thống kê truyền nhận */
				this.saveOrUpRptData(Contants.URL_FIND_TRANSACTION,
						list.size(), null);
			} else {
				response.setMessage(Contants.STRING_STATUS_CODE_HTTP_NOT_FOUND);
				response.setData(null);
			}
			response.setCode(Contants.RESPONSE_CODE_SUCCESS_API);
		} catch (Exception e) {
			e.printStackTrace();
			response.setCode(Contants.CODE_THROW_EXCEPTION);
			response.setMessage(Contants.MESSAGE_EXCEPTION);
			/*
			 * Lưu dữ liệu nếu có Exception
			 */
			String key = Contants.TYPE_FT + "_"
					+ new SimpleDateFormat("ddMMyyyy").format(new Date());
			String dataRequest = "";
			try {
				dataRequest = new ObjectMapper()
						.writeValueAsString(investigationAssignmentData);
			} catch (Exception e2) {
				logger.error(e.getMessage());
			}
			this.saveException(Contants.TYPE_FT, Contants.URL_FIND_TRANSACTION,
					dataRequest, key, response.toString(), e);
		}

		return response;
	}

	// Tra cứu lịch sử đề nghị cấp hộ chiếu
	@GET
	@Produces("application/json")
	@Consumes("application/json")
	@Path("/getHistoryPassportsIssuing/{transactionID}")
	public Response<List<TranInfor>> getHistoryPassportsIssuing(
			@PathParam("transactionID") String tranId) {
		Response<List<TranInfor>> response = new Response<List<TranInfor>>();
		response.setCode(Contants.RESPONSE_CODE_FAIL_API);
		response.setMessage(Contants.RESPONSE_MESSAGE_FAIL_API);
		SimpleDateFormat dateFormat = new SimpleDateFormat(
				DateUtil.FORMAT_YYYYMMDD);
		List<TranInfor> listT = new ArrayList<TranInfor>();
		List<NicDocumentData> listDoc = null;
		String listTranId = "";
		try {
			BaseModelSingle<NicTransaction> findTran = nicTransactionService
					.findTransactionById(tranId);
			if (!findTran.isError() || findTran.getMessage() != null) {
				throw new Exception(findTran.getMessage());
			}
			NicTransaction nicTranNew = findTran.getModel();
			if (nicTranNew != null) {
				if (StringUtils.isNotBlank(nicTranNew.getPersonCode())) {
					List<EppPerson> listPerson = eppPersonService
							.findListPersonByOrgPersonCode(nicTranNew
									.getPersonCode());
					if (listPerson != null && listPerson.size() > 0) {
						List<NicTransaction> listNicTran = nicTransactionService
								.findTranByListPersonCode(listPerson);
						if (listNicTran != null && listNicTran.size() > 0) {
							for (NicTransaction nicTransaction : listNicTran) {
								listTranId = nicTransaction.getTransactionId();
								listDoc = new ArrayList<NicDocumentData>(
										documentDataService
												.findAllDocByListTranId(listTranId
														.split(",")));
								if (listDoc != null && listDoc.size() > 0) {
									for (NicDocumentData nicDocumentData : listDoc) {
										TranInfor tran = new TranInfor();
										tran.setCancelBy(nicDocumentData
												.getCancelBy());
										tran.setCancelDate(nicDocumentData
												.getCancelDatetime() != null ? dateFormat
												.format(nicDocumentData
														.getCancelDatetime())
												: null);
										tran.setCancelReason(nicDocumentData
												.getCancelReason());
										tran.setCancelType(nicDocumentData
												.getCancelType());
										tran.setChipSerialNo(nicDocumentData
												.getChipSerialNo());
										tran.setPassportNo(nicDocumentData
												.getId().getPassportNo());
										if (nicDocumentData.getDateOfIssue() != null) {
											tran.setDateOfIssue(dateFormat
													.format(nicDocumentData
															.getDateOfIssue()));
										}
										tran.setDateOfExpiry(nicDocumentData
												.getDateOfExpiry() != null ? dateFormat
												.format(nicDocumentData
														.getDateOfExpiry())
												: null);
										tran.setPhoiSerialNo(nicDocumentData
												.getSerialNo());
										tran.setPpStatus(nicDocumentData
												.getStatus());
										NicTransaction nicTran = nicDocumentData
												.getNicTransaction();
										if (nicTran != null) {
											tran = this.setTranInfo(tran,
													nicTran);

										}
										List<NicTransactionAttachment> tranAttach = nicTransactionAttachmentService
												.findNicTransactionAttachments(
														nicTransaction
																.getTransactionId(),
														Contants.DOC_TYPE_PHOTO_CAPTURE,
														"01").getListModel();
										if (tranAttach != null
												&& tranAttach.size() > 0) {
											tran.setPhoto(tranAttach.get(0)
													.getDocData() != null ? Base64
													.encode(tranAttach.get(0)
															.getDocData())
													: null);
										}
										listT.add(tran);
									}
								} else {
									TranInfor tran = new TranInfor();
									tran = this.setTranInfo(tran,
											nicTransaction);
									List<NicTransactionAttachment> tranAttach = nicTransactionAttachmentService
											.findNicTransactionAttachments(
													nicTransaction
															.getTransactionId(),
													Contants.DOC_TYPE_PHOTO_CAPTURE,
													"01").getListModel();
									if (tranAttach != null
											&& tranAttach.size() > 0) {
										tran.setPhoto(tranAttach.get(0)
												.getDocData() != null ? Base64
												.encode(tranAttach.get(0)
														.getDocData())
												: null);
									}
									listT.add(tran);
								}
							}
						}
					}
				}
			}

			if (listT.size() > 0) {
				response.setMessage(Contants.RESPONSE_MESSAGE_SUCCESS_API);
				/* Lưu bảng thống kê truyền nhận */
				this.saveOrUpRptData(
						Contants.URL_GET_HISTORY_PASSPORTS_ISSUING,
						listT.size(), null);
			} else {
				listT = null;
				response.setMessage(Contants.MESSAGE_TRANSACTION_NULL);
			}
			response.setCode(Contants.RESPONSE_CODE_SUCCESS_API);
			response.setData(listT);
		} catch (Exception e) {
			e.printStackTrace();
			response.setCode(Contants.CODE_THROW_EXCEPTION);
			response.setMessage(Contants.MESSAGE_EXCEPTION);
			/*
			 * Lưu dữ liệu nếu có Exception
			 */
			this.saveException(Contants.TYPE_GHPI,
					Contants.URL_GET_HISTORY_PASSPORTS_ISSUING, tranId, tranId,
					response.toString(), e);
		}
		return response;
	}

	// Lấy ảnh tờ khai
	@GET
	@Produces("application/json")
	@Consumes("application/json")
	@Path("/getScanDocument/{transactionID}")
	public Response<String> getScanDocument(
			@PathParam("transactionID") String tranID) {
		Response<String> response = new Response<String>();
		response.setCode(Contants.RESPONSE_CODE_FAIL_API);
		response.setMessage(Contants.RESPONSE_MESSAGE_FAIL_API);
		try {
			List<NicTransactionAttachment> listAttach = nicTransactionAttachmentService
					.findNicTransactionAttachs(tranID, "SCAN_DOCUMENT",
							new String[] { "01", "1" });
			String base64Scan = "";
			if (listAttach != null && listAttach.size() > 0) {
				base64Scan = listAttach.get(0).getDocData() != null ? Base64
						.encode(listAttach.get(0).getDocData()) : null;
				if (!base64Scan.equals("")) {

					response.setMessage(Contants.RESPONSE_MESSAGE_SUCCESS_API);
					/* Lưu bảng thống kê truyền nhận */
					this.saveOrUpRptData(Contants.URL_GET_SCAN_DOCUMENT, 1,
							null);
				} else {
					response.setCode(Contants.STRING_STATUS_CODE_HTTP_NOT_FOUND);
				}
				response.setData(base64Scan);
				response.setCode(Contants.RESPONSE_CODE_SUCCESS_API);

			}
		} catch (Exception e) {
			e.printStackTrace();
			response.setCode(Contants.CODE_THROW_EXCEPTION);
			response.setMessage(Contants.MESSAGE_EXCEPTION);
			/*
			 * Lưu dữ liệu nếu có Exception
			 */
			this.saveException(Contants.TYPE_GSD,
					Contants.URL_GET_SCAN_DOCUMENT, tranID, tranID,
					response.toString(), e);
		}

		return response;
	}

	// lấy chi tiết hồ sơ
	@GET
	@Produces("application/json")
	@Consumes("application/json")
	@Path("/getDetailTransaction/{tranId}/{passportNo}")
	public Response<InforPassportDto> getDetailTransaction(
			@PathParam("tranId") String tranId,
			@PathParam("passportNo") String passportNo) {
		Response<InforPassportDto> response = new Response<InforPassportDto>();
		response.setCode(Contants.RESPONSE_CODE_FAIL_API);
		response.setMessage(Contants.RESPONSE_MESSAGE_FAIL_API);
		try {
			SimpleDateFormat dateFormat = new SimpleDateFormat(
					DateUtil.FORMAT_YYYYMMDD);
			InforPassportDto passportDto = null;
			NicRegistrationData reg = null;
			List<PersonFamiliDto> listPersonDto = null;
			NicTransaction txn = null;
			NicDocumentData nicDoc = null;
			HoSoDto hoso = null;
			if (!(StringUtils.isBlank(tranId) && StringUtils
					.isBlank(passportNo))) {
				if (StringUtils.isNotBlank(tranId)
						&& !passportNo.equals("null")) {
					nicDoc = documentDataService.findDocDataById(tranId,
							passportNo);
				}
				if (nicDoc != null) {
					hoso = new HoSoDto();
					hoso.setPassportNo(nicDoc.getId().getPassportNo());
					hoso.setChipSerialNo(nicDoc.getChipSerialNo());
					hoso.setPhoiSerialNo(nicDoc.getSerialNo());
					hoso.setPaStatus(nicDoc.getStatus());// trang thai ho chieu
					if (!nicDoc.getStatus().equals(
							NicDocumentData.DOCUMENT_DATA_STATUS_CANCELLED)
							&& nicDoc.getDateOfExpiry() != null
							&& nicDoc.getDateOfExpiry().getTime() < new Date()
									.getTime()) {
						hoso.setPaStatus(NicDocumentData.DOCUMENT_DATA_STATUS_EXPIRED);
					}
					if (nicDoc.getDateOfIssue() != null) {
						hoso.setPaDateOfIssue(dateFormat.format(nicDoc
								.getDateOfIssue()));
					}
					if (nicDoc.getDateOfExpiry() != null) {
						hoso.setPaDateOfExpiry(dateFormat.format(nicDoc
								.getDateOfExpiry()));
					}
					hoso.setIcaoLine1(nicDoc.getIcaoLine1());
					hoso.setIcaoLine2(nicDoc.getIcaoLine2());
					hoso.setPaCreateBy(nicDoc.getPrintName());// nguoi in
					if (nicDoc.getPrintDate() != null) {
						hoso.setPaCreateTs(dateFormat.format(nicDoc
								.getPrintDate()));// ngay
													// in
					}
					if (nicDoc.getPlaceOfIssueCode() != null) {
						SiteRepository site = siteService
								.getSiteRepoById(nicDoc.getPlaceOfIssueCode());
						if (site != null) {
							hoso.setPaPlaceOfIssue(site.getSiteName());
						}
					}

					txn = nicDoc.getNicTransaction();
					hoso.setPpCancelNote(nicDoc.getCancelReason());
					hoso.setPpCancelReason(nicDoc.getCancelType());
					hoso.setPpCancelDate(nicDoc.getCancelDatetime() != null ? dateFormat
							.format(nicDoc.getCancelDatetime()) : null);
					hoso.setPpCanceller(nicDoc.getCancelBy());
				} else {
					txn = nicTransactionService.findTransactionById(tranId)
							.getModel();
				}

				if (txn != null) {
					if (hoso == null) {
						hoso = new HoSoDto();
					}
					List<NicUploadJob> lJobs = uploadJobService
							.findAllByTransactionId(txn.getTransactionId());
					if (lJobs != null && lJobs.size() > 0) {
						NicUploadJob nicUJ = lJobs.get(0);
						hoso.setAssignee(nicUJ.getInvestigationOfficerId());
						if (nicUJ.getInvestigationDate() != null) {
							String date = DateUtil.parseDate(
									nicUJ.getInvestigationDate(),
									DateUtil.FORMAT_YYYYMMDD);
							hoso.setInqApxTs(date);
						}
					}
					// thông tin trả/nhận hộ chiếu.
					EppIssuance eppIssuance = eppIssuanceService
							.findByTranId(txn.getTransactionId());
					if (eppIssuance != null) {
						if (eppIssuance.getDateOfDelivery() != null) {
							hoso.setPaDeliveryDate(DateUtil.parseDate(
									eppIssuance.getDateOfDelivery(),
									DateUtil.FORMAT_YYYYMMDD));
						}
						hoso.setPaDeliveryName(eppIssuance.getDeliveryName());
						hoso.setPaSignName(eppIssuance.getRecipient());
					}
					hoso.setNote(txn.getNote());
					hoso.setDescription(txn.getDescription());
					hoso.setTransactionId(txn.getTransactionId());
					hoso.setReceipNo(txn.getRecieptNo());
					hoso.setStatus(txn.getTransactionStatus());// trang thai
																// ho
																// so
					hoso.setPrevPassportNo(txn.getPrevPassportNo());
					// hoso.setPaPlaceOfIssue(txn.getAppointmentPlace());

					hoso.setIsEpassport(String.valueOf(txn.getIsEpassport()));
					hoso.setPaPlaceOfReceipt(txn.getAppointmentPlace());
					if (txn.getDateOfApplication() != null) {
						String date = DateUtil.parseDate(
								txn.getDateOfApplication(),
								DateUtil.FORMAT_YYYYMMDD);
						hoso.setCreateTs(date);
					}
					// thông tin số hồ sơ lưu
					if (StringUtils.isNotBlank(txn.getArchiveCode())) {
						EppArchive eppArc = eppArchiveService
								.findByTranIdAndArchiveCode(
										txn.getTransactionId(),
										txn.getArchiveCode());
						if (eppArc != null) {
							hoso.setPackNo(eppArc.getPackNo());
							hoso.setPageCount(eppArc.getPageCount() != null ? String
									.valueOf(eppArc.getPageCount()) : "");
							hoso.setStartPage(eppArc.getStartPage() != null ? String
									.valueOf(eppArc.getStartPage()) : "");
						}
					}

					List<NicTransactionAttachment> tranAttach = nicTransactionAttachmentService
							.findNicTransactionAttachments(tranId,
									Contants.DOC_TYPE_PHOTO_CAPTURE, "01")
							.getListModel();
					if (tranAttach != null && tranAttach.size() > 0) {
						hoso.setPhotoBase64(tranAttach.get(0).getDocData() != null ? Base64
								.encode(tranAttach.get(0).getDocData()) : null);
					}
					reg = txn.getNicRegistrationData();
					if (reg != null) {
						hoso.setCreateBy(reg.getCreateByName());
						hoso.setDateOfBirth(dateFormat.format(reg
								.getDateOfBirth()));

						hoso.setName(HelperClass.createFullName(
								reg.getSurnameFull(), reg.getMiddlenameFull(),
								reg.getFirstnameFull()));
						hoso.setGender(reg.getGender());
						hoso.setEthnic(reg.getReligion());
						if (StringUtils.isNotBlank(reg.getNation())) {
							CodeValues codeNation = codesService
									.findByStringCodeId(
											CodeValues.CODE_ID_CODE_NATION,
											reg.getNation());
							hoso.setNation(codeNation != null ? codeNation
									.getCodeValueDesc() : null);
						} else {
							if (txn.getPersonCode() != null) {
								EppPerson person = eppPersonService
										.findPersonByPersonCode(txn
												.getPersonCode());
								if (person != null) {
									hoso.setNation(person.getEthnic());
								}
							}
						}
						hoso.setJob(reg.getJob());
						String address = reg.getAddressLine1();
						if (reg.getAddressLine2() != null) {
							address += ", "
									+ codesService.findByStringCodeId(
											CodeValues.CODE_ID_VILLAGE,
											reg.getAddressLine2())
											.getCodeValueDesc();
						}
						if (reg.getAddressLine3() != null) {
							address += ", "
									+ codesService.findByStringCodeId(
											CodeValues.CODE_ID_TOWN_VILLAGE,
											reg.getAddressLine3())
											.getCodeValueDesc();
						}
						if (reg.getAddressLine4() != null) {
							address += ", "
									+ codesService.findByStringCodeId(
											CodeValues.CODE_ID_DISTRICT,
											reg.getAddressLine4())
											.getCodeValueDesc();
						}
						hoso.setResidentAddress(address);
						String tmpAddress = reg.getAddressTempDetail();
						if (reg.getAddressTempVillage() != null) {
							tmpAddress += ", "
									+ codesService.findByStringCodeId(
											CodeValues.CODE_ID_VILLAGE,
											reg.getAddressTempVillage())
											.getCodeValueDesc();
						}
						if (reg.getAddressTempDistrict() != null) {
							tmpAddress += ", "
									+ codesService.findByStringCodeId(
											CodeValues.CODE_ID_TOWN_VILLAGE,
											reg.getAddressTempDistrict())
											.getCodeValueDesc();
						}
						if (reg.getAddressTempProvince() != null) {
							tmpAddress += ", "
									+ codesService.findByStringCodeId(
											CodeValues.CODE_ID_DISTRICT,
											reg.getAddressTempProvince())
											.getCodeValueDesc();
						}
						hoso.setTmpAddress(tmpAddress);
						hoso.setNin(txn.getNin());
						if (reg.getDateNin() != null) {
							hoso.setDateNin(dateFormat.format(reg.getDateNin()));
						}
						CodeValues codeValues = codesService
								.getCodeValueByIdName(
										CodeValues.CODE_ID_CODE_PLACE_OF_BIRTH,
										reg.getPlaceOfBirth());
						if (codeValues != null) {
							hoso.setPlaceOfBirth(codeValues.getCodeValueDesc());
						} else {
							hoso.setPlaceOfBirth(reg.getPlaceOfBirth());
						}
						codeValues = codesService.findByStringCodeId(
								CodeValues.CODE_ID_COUNTRY,
								reg.getNationality());
						if (codeValues != null) {
							hoso.setNationnalityName(codeValues
									.getCodeValueDesc());
						}
						// xet nhan than
						listPersonDto = new ArrayList<PersonFamiliDto>();
						if (StringUtils.isNotEmpty(reg.getFatherFullname())) {
							PersonFamiliDto fatherData = new PersonFamiliDto();
							fatherData.setName(reg.getFatherFullname());
							fatherData.setGender("M");
							fatherData
									.setRelationship(Contants.RELATIONSHIP_TYPE_FATHER);
							if (reg.getFatherLost() != null) {
								fatherData.setLost(reg.getFatherLost());
							}
							if (reg.getFatherDateOfBirth() != null) {
								String dob = service.perso.util.HelperClass
										.convertDateToString(
												reg.getFatherDateOfBirth(),
												DateUtil.FORMAT_YYYYMMDD);
								fatherData.setDateOfBirth(HelperClass
										.loadDateOfBirths(dob,
												reg.getFatherDefDateOfBirth()));
							}
							listPersonDto.add(fatherData);
						}

						if (StringUtils.isNotEmpty(reg.getMotherFullname())) {
							PersonFamiliDto motherData = new PersonFamiliDto();
							motherData.setName(reg.getMotherFullname());
							motherData.setGender("F");
							motherData
									.setRelationship(Contants.RELATIONSHIP_TYPE_MOTHER);
							if (reg.getMotherLost() != null) {
								motherData.setLost(reg.getMotherLost());
							}
							if (reg.getMotherDateOfBirth() != null) {
								String dob = service.perso.util.HelperClass
										.convertDateToString(
												reg.getMotherDateOfBirth(),
												DateUtil.FORMAT_YYYYMMDD);
								motherData.setDateOfBirth(HelperClass
										.loadDateOfBirths(dob,
												reg.getMotherDefDateOfBirth()));
							}
							listPersonDto.add(motherData);
						}

						if (StringUtils.isNotEmpty(reg.getSpouseFullname())) {
							PersonFamiliDto spouseData = new PersonFamiliDto();
							spouseData.setName(reg.getSpouseFullname());
							spouseData
									.setGender(reg.getGender().equals("M") ? "F"
											: "M");
							spouseData
									.setRelationship(Contants.RELATIONSHIP_TYPE_SPOUSE);
							spouseData.setLost(reg.getSpouseLost());
							if (reg.getSpouseDateOfBirth() != null) {
								String dob = service.perso.util.HelperClass
										.convertDateToString(
												reg.getSpouseDateOfBirth(),
												DateUtil.FORMAT_YYYYMMDD);
								spouseData.setDateOfBirth(HelperClass
										.loadDateOfBirths(dob,
												reg.getSpouseDefDateOfBirth()));
							}
							listPersonDto.add(spouseData);
						}

					}
					// listhandover
					ListHandoverDto list_code_a = null;
					ListHandoverDto list_code_b = null;
					ListHandoverDto list_code_c = null;
					NicListHandover nicA = null;
					NicListHandover nicB = null;
					NicListHandover nicC = null;
					EppListHandoverDetail detailA = null;
					EppListHandoverDetail detailB = null;
					EppListHandoverDetail detailC = null;
					List<EppListHandoverDetail> listHanDetail = eppListHandoverDetailService
							.getPackageNameByTransactionId(tranId)
							.getListModel();
					for (EppListHandoverDetail eppListHandoverDetail : listHanDetail) {
						if (eppListHandoverDetail.getTypeList().equals("A")) {
							nicA = eppListHandoverDetail.getPackageHandover();
							detailA = eppListHandoverDetail;
							continue;
						}
						if (eppListHandoverDetail.getTypeList().equals("B")) {
							if (eppListHandoverDetail.getPackageHandover()
									.getHandoverStatus() != 2) {
								nicB = eppListHandoverDetail
										.getPackageHandover();
								detailB = eppListHandoverDetail;
								continue;
							}
						}
						if (eppListHandoverDetail.getTypeList().equals("C")) {
							nicC = eppListHandoverDetail.getPackageHandover();
							detailC = eppListHandoverDetail;
						}
					}
					if (nicA != null) {
						list_code_a = new ListHandoverDto();
						list_code_a.setPackageId(nicA.getId().getPackageId());
						list_code_a.setApproverName(nicA.getApproveName());
						if (nicA.getApproveDate() != null) {
							list_code_a.setApproverDate(dateFormat.format(nicA
									.getApproveDate()));
						}
						if (nicA.getProposalDate() != null) {
							list_code_a.setCreateTs(dateFormat.format(nicA
									.getProposalDate()));
						}

						list_code_a.setCreateBy(nicA.getCreatorName());
						if (detailA != null) {
							list_code_a
									.setPrpContent(detailA.getProposalNote());
							list_code_a.setApproverContent(detailA
									.getApproveNote());
						}
					}
					if (nicB != null) {
						list_code_b = new ListHandoverDto();

						list_code_b.setApproverName(nicB.getApproveName());
						if (nicB.getApproveDate() != null) {
							list_code_b.setApproverDate(dateFormat.format(nicB
									.getApproveDate()));
							if (detailB.getApproveStage() != null
									&& !detailB.getApproveStage().equals("C")) {
								hoso.setArchiveCode(txn.getArchiveCode());
							}
						}

						if (nicB.getProposalDate() != null) {
							list_code_b.setCreateTs(dateFormat.format(nicB
									.getProposalDate()));
						}
						list_code_b.setPackageId(nicB.getId().getPackageId());
						list_code_b.setCreateBy(nicB.getProposalName());

						if (detailB != null) {
							list_code_b
									.setPrpContent(detailB.getProposalNote());
							list_code_b.setApproverContent(detailB
									.getApproveNote());
							hoso.setProposalStage(detailB.getProposalStage());
							hoso.setApproveStage(detailB.getApproveStage());
						}
					}
					if (nicC != null) {
						list_code_c = new ListHandoverDto();
						list_code_c.setPackageId(nicC.getId().getPackageId());
						list_code_c.setApproverName(nicC.getApproveName());
						if (nicC.getApproveDate() != null) {
							list_code_c.setApproverDate(dateFormat.format(nicC
									.getApproveDate()));
						}
						if (nicC.getCreateDate() != null) {
							list_code_c.setCreateTs(dateFormat.format(nicC
									.getCreateDate()));
						}
						list_code_c.setCreateBy(nicC.getCreatorName());
						if (detailC != null) {
							list_code_c
									.setPrpContent(detailC.getProposalNote());
							list_code_c.setApproverContent(detailC
									.getApproveNote());
						}
					}

					passportDto = new InforPassportDto();
					passportDto.setHandoverA(list_code_a);
					passportDto.setHandoverB(list_code_b);
					passportDto.setHandoverC(list_code_c);
					passportDto.setHoso(hoso);
					passportDto.setNhanthan(listPersonDto);
					response.setMessage(Contants.RESPONSE_MESSAGE_SUCCESS_API);
					/* Lưu bảng thống kê truyền nhận */
					this.saveOrUpRptData(Contants.URL_GET_DETAIL_TRANSACTION,
							1, null);
				} else {
					response.setMessage(Contants.MESSAGE_TRANSACTION_NULL);
				}
				response.setCode(Contants.RESPONSE_CODE_SUCCESS_API);
				response.setData(passportDto);
			}
		} catch (Exception e) {
			e.printStackTrace();
			response.setCode(Contants.CODE_THROW_EXCEPTION);
			response.setMessage(Contants.MESSAGE_EXCEPTION);
			/*
			 * Lưu dữ liệu nếu có Exception
			 */
			this.saveException(Contants.TYPE_GDT,
					Contants.URL_GET_DETAIL_TRANSACTION, tranId, tranId + "_"
							+ passportNo, response.toString(), e);
		}
		return response;
	}

	// Tìm kiếm thông tin hộ chiếu
	@GET
	@Produces("application/json")
	@Consumes("applicattion/json")
	@Path("/findPassportByPassportNo/{ppNo}")
	public Response<PassportByPassportNo> findPassportByPassportNo(
			@PathParam("ppNo") String ppNo) {
		Response<PassportByPassportNo> response = new Response<PassportByPassportNo>();
		PassportByPassportNo passportDto = null;
		response.setCode(Contants.RESPONSE_CODE_FAIL_API);
		response.setMessage(Contants.RESPONSE_MESSAGE_FAIL_API);
		try {
			SimpleDateFormat dateFormat = new SimpleDateFormat(
					DateUtil.FORMAT_YYYYMMDD);
			NicDocumentData docData = documentDataService.findByDocNumber(ppNo)
					.getModel();
			if (docData != null) {
				passportDto = new PassportByPassportNo();
				passportDto.setChipseriNo(docData.getChipSerialNo());
				passportDto.setPhoiSerialNo(docData.getSerialNo());
				if (docData.getDateOfIssue() != null) {
					passportDto.setDateOfIssue(dateFormat.format(docData
							.getDateOfIssue()));
				}
				if (docData.getDateOfExpiry() != null) {
					passportDto.setDateOfExpiry(dateFormat.format(docData
							.getDateOfExpiry()));
				}
				NicTransaction txn = docData.getNicTransaction();
				passportDto.setPassportNo(docData.getId().getPassportNo());
				if (txn != null) {
					passportDto.setArchiveCode(txn.getArchiveCode());
					passportDto.setNin(txn.getNin());
					passportDto.setReceiptNo(txn.getRecieptNo());
					NicRegistrationData reg = txn.getNicRegistrationData();
					if (reg != null) {
						if (reg.getDateOfBirth() != null) {
							passportDto.setDateOfBirth(dateFormat.format(reg
									.getDateOfBirth()));
						}

						passportDto.setName(HelperClass.createFullName(
								reg.getSurnameFull(), reg.getMiddlenameFull(),
								reg.getFirstnameFull()));
						passportDto.setGender(reg.getGender());
						if (StringUtils.isNotBlank(reg.getPlaceOfBirth())) {
							CodeValues codeV = codesService.findByStringCodeId(
									CodeValues.CODE_ID_CODE_PLACE_OF_BIRTH,
									reg.getPlaceOfBirth());
							if (codeV == null) {
								codeV = codesService.findByStringCodeId(
										CodeValues.CODE_ID_COUNTRY,
										reg.getPlaceOfBirth());
							}
							if (codeV != null) {
								passportDto.setPlaceOfBirth(codeV
										.getCodeValueDesc());
							} else {
								passportDto.setPlaceOfBirth(reg
										.getPlaceOfBirth());
							}
						}
					}
				}
				response.setMessage(Contants.RESPONSE_MESSAGE_SUCCESS_API);
				/* Lưu bảng thống kê truyền nhận */
				this.saveOrUpRptData(Contants.URL_FIND_PASSPORT_BY_PASSPORT_NO,
						1, null);
			} else {
				response.setMessage(Contants.STRING_STATUS_CODE_HTTP_NOT_FOUND);
			}
			response.setCode(Contants.RESPONSE_CODE_SUCCESS_API);
			response.setData(passportDto);

		} catch (Exception e) {
			e.printStackTrace();
			response.setCode(Contants.CODE_THROW_EXCEPTION);
			response.setMessage(Contants.MESSAGE_EXCEPTION);
			/*
			 * Lưu dữ liệu nếu có Exception
			 */
			this.saveException(Contants.TYPE_FPBP,
					Contants.URL_FIND_PASSPORT_BY_PASSPORT_NO, ppNo, ppNo,
					response.toString(), e);
		}
		return response;
	}

	// lưu thông tin hộ chiếu địa phương trả lại.
	@POST
	@Produces("application/json")
	@Consumes("application/json")
	@Path("/updatePassportProvinceReturned")
	public ResponseBase updatePassportProvinceReturned(
			UpdatePassportProvinceReturnedInput input) {
		ResponseBase response = new ResponseBase();
		response.setCode(Contants.RESPONSE_CODE_FAIL_API);
		response.setMessage(Contants.RESPONSE_MESSAGE_FAIL_API);
		try {
			EppDocumentReturned epp = new EppDocumentReturned();
			epp.setReceivedDate(DateUtil.strToDate(input.getReceivedDate(),
					DateUtil.FORMAT_YYYYMMDD));
			epp.setReceivedName(input.getReceivedName());
			epp.setCreateBy(input.getCreateBy());
			epp.setNote(input.getNote());
			epp.setOfficeCode(input.getOfficeCode());
			epp.setPassportNo(input.getPassportNo());
			epp.setStorageNo(input.getStorageNo());
			epp.setCreateDate(new Date());
			boolean check = eppDocumentReturnedService.insertDataTable(epp);
			if (check) {
				response.setCode(Contants.RESPONSE_CODE_SUCCESS_API);
				response.setMessage(Contants.RESPONSE_MESSAGE_SUCCESS_API);
				/* Lưu bảng thống kê truyền nhận */
				this.saveOrUpRptData(
						Contants.URL_UPDATE_PASSPORT_PROVINCE_RETURNED, 1, null);
			} else {
				response.setCode(Contants.RESPONSE_CODE_FAIL_API);
				response.setMessage(Contants.RESPONSE_MESSAGE_FAIL_API);
			}

		} catch (Exception e) {
			e.printStackTrace();
			response.setCode(Contants.CODE_THROW_EXCEPTION);
			response.setMessage(Contants.MESSAGE_EXCEPTION);
			/*
			 * Lưu dữ liệu nếu có Exception
			 */
			String key = Contants.TYPE_UPPR + "_"
					+ new SimpleDateFormat("ddMMyyyy").format(new Date());
			String dataRequest = "";
			try {
				dataRequest = new ObjectMapper().writeValueAsString(input);
			} catch (Exception e2) {
				logger.error(e.getMessage());
			}
			this.saveException(Contants.TYPE_UPPR,
					Contants.URL_UPDATE_PASSPORT_PROVINCE_RETURNED,
					dataRequest, key, response.toString(), e);
		}
		return response;
	}

	// thong ke ho chieu dia phuong tra lai
	@POST
	@Produces("application/json")
	@Consumes("application/json")
	@Path("/statisticalPassportProvinceReturned")
	public Response<List<StatisticalPassportProvinceReturnedDto>> statisticalPassportProvinceReturned(
			StatisticalPassportProvinceReturnedInput input) {
		Response<List<StatisticalPassportProvinceReturnedDto>> response = new Response<List<StatisticalPassportProvinceReturnedDto>>();
		response.setCode(Contants.RESPONSE_CODE_FAIL_API);
		response.setMessage(Contants.RESPONSE_MESSAGE_FAIL_API);
		List<StatisticalPassportProvinceReturnedDto> listResult = null;
		try {
			if (input != null && StringUtils.isNotBlank(input.getDateType())) {

				listResult = eppDocumentReturnedService.getAllStorage(
						input.getDateType(), input.getFromDate(),
						input.getToDate(), input.getOfficeId());
				if (listResult != null && listResult.size() > 0) {
					/* Lưu bảng thống kê truyền nhận */
					this.saveOrUpRptData(
							Contants.URL_STATISTICAL_PASSPORT_PROVINCE_RETURNED,
							listResult.size(), null);
				}
				response.setData(listResult);
				response.setCode(Contants.RESPONSE_CODE_SUCCESS_API);
				response.setMessage(Contants.RESPONSE_MESSAGE_SUCCESS_API);
			}
		} catch (Exception e) {
			e.printStackTrace();
			response.setCode(Contants.CODE_THROW_EXCEPTION);
			response.setMessage(Contants.MESSAGE_EXCEPTION);
			/*
			 * Lưu dữ liệu nếu có Exception
			 */
			String key = Contants.TYPE_SPPR + "_"
					+ new SimpleDateFormat("ddMMyyyy").format(new Date());
			String dataRequest = "";
			try {
				dataRequest = new ObjectMapper().writeValueAsString(input);
			} catch (Exception e2) {
				logger.error(e.getMessage());
			}
			this.saveException(Contants.TYPE_SPPR,
					Contants.URL_STATISTICAL_PASSPORT_PROVINCE_RETURNED,
					dataRequest, key, response.toString(), e);
		}
		return response;
	}

	// tra cuu ho chieu dia phuong trả lại
	@POST
	@Produces("application/json")
	@Consumes("application/json")
	@Path("/findPassportProvinceReturned")
	public Response<FindPassportProvinceReturnedOutput> findPassportProvinceReturned(
			FindPassportInput input) {
		Response<FindPassportProvinceReturnedOutput> response = new Response<FindPassportProvinceReturnedOutput>();
		response.setCode(Contants.RESPONSE_CODE_FAIL_API);
		response.setMessage(Contants.RESPONSE_MESSAGE_FAIL_API);
		FindPassportProvinceReturnedOutput output = null;
		try {
			if (!(StringUtils.isBlank(input.getDateOfBirth())
					&& StringUtils.isBlank(input.getGender())
					&& StringUtils.isBlank(input.getName()) && StringUtils
						.isBlank(input.getPassportNo()))) {
				NicDocumentData docData = documentDataService
						.findPassportByConditions(input.getPassportNo(),
								input.getName(), input.getGender(),
								input.getDateOfBirth(), null, null).getModel();
				SimpleDateFormat dateFormat = new SimpleDateFormat(
						DateUtil.FORMAT_YYYYMMDD);
				if (docData != null) {
					output = new FindPassportProvinceReturnedOutput();
					output.setPassportNo(docData.getId().getPassportNo());
					output.setPhoiSerialNo(docData.getSerialNo());
					output.setChipSerialNo(docData.getChipSerialNo());
					if (docData.getDateOfIssue() != null) {
						output.setDateOfIssue(dateFormat.format(docData
								.getDateOfIssue()));
					}
					if (docData.getDateOfExpiry() != null) {
						output.setDateOfExpiry(dateFormat.format(docData
								.getDateOfExpiry()));
					}
					output.setPrintName(docData.getPrintName());

					NicTransaction nic = docData.getNicTransaction();
					if (nic != null) {
						output.setArchiveCode(nic.getArchiveCode());
						output.setReceiptNo(nic.getRecieptNo());
						output.setNin(nic.getNin());
					}
					NicRegistrationData reg = nic.getNicRegistrationData();
					if (reg != null) {
						output.setName(HelperClass.createFullName(
								reg.getSurnameFull(), reg.getMiddlenameFull(),
								reg.getFirstnameFull()));
						output.setGender(reg.getGender());
						if (reg.getDateOfBirth() != null) {
							output.setDateOfBirth(dateFormat.format(reg
									.getDateOfBirth()));
						}
						if (StringUtils.isNotBlank(reg.getPlaceOfBirth())) {
							CodeValues codeV = codesService.findByStringCodeId(
									CodeValues.CODE_ID_CODE_PLACE_OF_BIRTH,
									reg.getPlaceOfBirth());
							if (codeV == null) {
								codeV = codesService.findByStringCodeId(
										CodeValues.CODE_ID_COUNTRY,
										reg.getPlaceOfBirth());
							}
							if (codeV != null) {
								output.setPlaceOfBirth(codeV.getCodeValueDesc());
							} else {
								output.setPlaceOfBirth(reg.getPlaceOfBirth());
							}
						}
						String address = reg.getAddressLine1();
						if (reg.getAddressLine2() != null) {
							address += ", "
									+ codesService.findByStringCodeId(
											CodeValues.CODE_ID_VILLAGE,
											reg.getAddressLine2())
											.getCodeValueDesc();
						}
						if (reg.getAddressLine3() != null) {
							address += ", "
									+ codesService.findByStringCodeId(
											CodeValues.CODE_ID_TOWN_VILLAGE,
											reg.getAddressLine3())
											.getCodeValueDesc();
						}
						if (reg.getAddressLine4() != null) {
							address += ", "
									+ codesService.findByStringCodeId(
											CodeValues.CODE_ID_DISTRICT,
											reg.getAddressLine4())
											.getCodeValueDesc();
						}
						if (address != null) {
							output.setResidentAddress(address);
						}
						if (reg.getDateNin() != null) {
							output.setDateOfIdIssue(dateFormat.format(reg
									.getDateNin()));
						}
						if (StringUtils.isNotBlank(reg.getAddressNin())) {
							SiteRepository siteR = siteService
									.getSiteRepoById(reg.getAddressNin());
							if (siteR != null) {
								output.setPlaceOfIdIssue(siteR.getSiteName());
							} else {
								output.setPlaceOfIdIssue(reg.getAddressNin());
							}
						}
					}
					EppDocumentReturned epp = eppDocumentReturnedService
							.getByPassportNo(docData.getId().getPassportNo());
					if (epp != null) {
						output.setReceiptReturn(epp.getReceiptNo());
						output.setStorageNo(epp.getStorageNo());
						if (StringUtils.isNotBlank(epp.getOfficeCode())) {
							SiteRepository siteR = siteService
									.getSiteRepoById(epp.getOfficeCode());
							if (siteR != null) {
								output.setOfficeName(siteR.getSiteName());
							} else {
								output.setOfficeName(epp.getOfficeCode());
							}
						}
						if (epp.getReceivedDate() != null) {
							output.setReceiptDate(dateFormat.format(epp
									.getReceivedDate()));
						}
						output.setReceiptName(epp.getReceivedName());
						output.setIdEppDocumentReturnedId(epp.getId());

						output.setHandoverName(epp.getReturnName());
						if (epp.getReturnDate() != null) {
							output.setHandoverDate(dateFormat.format(epp
									.getReturnDate()));
						}
					}
					response.setMessage(Contants.RESPONSE_MESSAGE_SUCCESS_API);
					/* Lưu bảng thống kê truyền nhận */
					this.saveOrUpRptData(
							Contants.URL_FIND_PASSPORT_PROVINCE_RETURNED, 1,
							null);
				} else {
					response.setMessage(Contants.STRING_STATUS_CODE_HTTP_NOT_FOUND);
				}
			} else {
				response.setCode(Contants.CODE_INPUT_FAILD);
				response.setMessage(Contants.MESSAGE_INPUT_NULL
						+ " Phải có ít nhất một giá trị điều kiện đầu vào.");
				return response;
			}
			response.setCode(Contants.RESPONSE_CODE_SUCCESS_API);
			response.setData(output);
		} catch (Exception e) {
			e.printStackTrace();
			response.setCode(Contants.CODE_THROW_EXCEPTION);
			response.setMessage(Contants.MESSAGE_EXCEPTION);
			/*
			 * Lưu dữ liệu nếu có Exception
			 */
			String key = Contants.TYPE_FPPR + "_"
					+ new SimpleDateFormat("ddMMyyyy").format(new Date());
			String dataRequest = "";
			try {
				dataRequest = new ObjectMapper().writeValueAsString(input);
			} catch (Exception e2) {
				logger.error(e.getMessage());
			}
			this.saveException(Contants.TYPE_FPPR,
					Contants.URL_FIND_PASSPORT_PROVINCE_RETURNED, dataRequest,
					key, response.toString(), e);
		}
		return response;
	}

	// tim kiem thong tin ho chieu dia phuong tra lai
	@GET
	@Produces("application/json")
	@Consumes("application/json")
	@Path("/findPassportProvinceReturnedByPassportNo/{ppNo}")
	public Response<FindPassportProvinceReturnedByPassportNoOutput> findPassportProvinceReturnedByPassportNo(
			@PathParam("ppNo") String ppNo) {
		Response<FindPassportProvinceReturnedByPassportNoOutput> response = new Response<FindPassportProvinceReturnedByPassportNoOutput>();
		FindPassportProvinceReturnedByPassportNoOutput output = null;
		response.setCode(Contants.RESPONSE_CODE_FAIL_API);
		response.setMessage(Contants.RESPONSE_MESSAGE_FAIL_API);
		try {
			EppDocumentReturned eppDocumentReturned = eppDocumentReturnedService
					.getByPassportNo(ppNo);
			if (eppDocumentReturned == null
					|| (eppDocumentReturned != null && eppDocumentReturned
							.getReturnDate() == null)) {
				response.setMessage("Địa phương chưa trả hộ chiếu.");
			} else {
				BaseModelSingle<NicDocumentData> baseFindDocData = documentDataService
						.findByDocNumber(ppNo);
				if (!baseFindDocData.isError()
						|| baseFindDocData.getMessage() != null) {
					throw new Exception(baseFindDocData.getMessage());
				}
				NicDocumentData docData = baseFindDocData.getModel();
				SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
				if (docData != null) {
					output = new FindPassportProvinceReturnedByPassportNoOutput();
					output.setSeriPassport(docData.getSerialNo());
					output.setPhoiSerialNo(docData.getSerialNo());
					if (docData.getDateOfIssue() != null) {
						output.setDateRange(dateFormat.format(docData
								.getDateOfIssue()));
					}
					if (docData.getDateOfExpiry() != null) {
						output.setExpirationDatePassport(dateFormat
								.format(docData.getDateOfExpiry()));
					}
					output.setPassportNo(docData.getId().getPassportNo());
					NicTransaction txn = docData.getNicTransaction();
					if (txn != null) {
						output.setReceiptNo(txn.getRecieptNo());
						output.setDocumentCode(txn.getArchiveCode());
						output.setNin(txn.getNin());
						if (StringUtils.isNotBlank(txn.getIssSiteCode())) {
							SiteRepository siteR = siteService
									.getSiteRepoById(txn.getIssSiteCode());
							if (siteR != null) {
								output.setPlacePayPassport(siteR.getSiteName());
							}
						}
						NicRegistrationData reg = txn.getNicRegistrationData();
						if (reg != null) {
							output.setName(HelperClass.createFullName(
									reg.getSurnameFull(),
									reg.getMiddlenameFull(),
									reg.getFirstnameFull()));
							output.setGender(reg.getGender());
							if (reg.getDateOfBirth() != null) {
								output.setDateOfBirth(dateFormat.format(reg
										.getDateOfBirth()));
							}
							if (StringUtils.isNotBlank(reg.getPlaceOfBirth())) {
								CodeValues codeV = codesService
										.findByStringCodeId(
												CodeValues.CODE_ID_CODE_PLACE_OF_BIRTH,
												reg.getPlaceOfBirth());
								if (codeV == null) {
									codeV = codesService.findByStringCodeId(
											CodeValues.CODE_ID_COUNTRY,
											reg.getPlaceOfBirth());
								}
								if (codeV != null) {
									output.setPlaceOfBirth(codeV
											.getCodeValueDesc());
								} else {
									output.setPlaceOfBirth(reg
											.getPlaceOfBirth());
								}
							}
						}
					}

					EppDocumentReturned epp = eppDocumentReturnedService
							.getByPassportNo(ppNo);
					if (epp != null) {
						if (epp.getReceivedDate() != null) {
							output.setReceivedDate(dateFormat.format(epp
									.getReceivedDate()));
						}
						output.setReceivedUser(epp.getReceivedName());
						output.setNumberSave(epp.getStorageNo());
						output.setNote(epp.getNote());
					}
					/* Lưu bảng thống kê truyền nhận */
					this.saveOrUpRptData(
							Contants.URL_FIND_PASSPORT_PROVINCE_RETURNED_BY_PASSPORT_NO,
							1, null);
					response.setMessage(Contants.RESPONSE_MESSAGE_SUCCESS_API);
				} else {
					response.setMessage(Contants.STRING_STATUS_CODE_HTTP_NOT_FOUND);
				}
			}
			response.setCode(Contants.RESPONSE_CODE_SUCCESS_API);
			response.setData(output);
		} catch (Exception e) {
			e.printStackTrace();
			response.setCode(Contants.CODE_THROW_EXCEPTION);
			response.setMessage(Contants.MESSAGE_EXCEPTION);
			/*
			 * Lưu dữ liệu nếu có Exception
			 */
			this.saveException(
					Contants.TYPE_FPPRBP,
					Contants.URL_FIND_PASSPORT_PROVINCE_RETURNED_BY_PASSPORT_NO,
					ppNo, ppNo, response.toString(), e);
		}
		return response;
	}

	// Trả hộ chiếu địa phương trả lại
	@POST
	@Consumes("application/json")
	@Produces("application/json")
	@Path("/updatePassportReturnedToProvince")
	public ResponseBase updatePassportReturnedToProvince(
			UpdatePassportReturnedToProvinceInput input) {
		ResponseBase response = new ResponseBase();
		try {
			EppDocumentReturned epp = eppDocumentReturnedService
					.getByPassportNo(input.getPassportNo());
			if (epp != null) {
				epp.setReceiptNo(input.getReceiptNo());
				epp.setReturnName(input.getReceivedUser());
				if (StringUtils.isNotBlank(input.getReceivedDate())) {
					Date date = DateUtil.strToDate(input.getReceivedDate(),
							DateUtil.FORMAT_YYYYMMDD);
					epp.setReturnDate(date);
				}
				boolean check = eppDocumentReturnedService.insertDataTable(epp);
				if (check) {
					response.setCode(Contants.RESPONSE_CODE_SUCCESS_API);
					response.setMessage(Contants.RESPONSE_MESSAGE_SUCCESS_API);
					/* Lưu bảng thống kê truyền nhận */
					this.saveOrUpRptData(
							Contants.URL_UPDATE_PASSPORT_RETURNED_TO_PROVINCE,
							1, null);
				} else {
					response.setCode(Contants.RESPONSE_CODE_FAIL_API);
					response.setMessage(Contants.RESPONSE_MESSAGE_FAIL_API);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			response.setCode(Contants.CODE_THROW_EXCEPTION);
			response.setMessage(Contants.MESSAGE_EXCEPTION);
			/*
			 * Lưu dữ liệu nếu có Exception
			 */
			String key = Contants.TYPE_UPRTP + "_"
					+ new SimpleDateFormat("ddMMyyyy").format(new Date());
			String dataRequest = "";
			try {
				dataRequest = new ObjectMapper().writeValueAsString(input);
			} catch (Exception e2) {
				logger.error(e.getMessage());
			}
			this.saveException(Contants.TYPE_UPRTP,
					Contants.URL_UPDATE_PASSPORT_RETURNED_TO_PROVINCE,
					dataRequest, key, response.toString(), e);
		}

		return response;
	}

	/* convert transaction to tranInfo */
	private TranInfor setTranInfo(TranInfor tran, NicTransaction nicTran) {
		CodeValues codeV = null;
		String address = "";
		tran.setNin(nicTran.getNin());
		tran.setDescription(nicTran.getDescription());
		NicRegistrationData nicReg = nicTran.getNicRegistrationData();
		if (nicReg != null) {
			address = nicReg.getAddressLine1();
			if (nicReg.getAddressLine2() != null) {
				codeV = codesService.findByStringCodeId(
						CodeValues.CODE_ID_VILLAGE, nicReg.getAddressLine2());
				if (codeV != null) {
					address += ", " + codeV.getCodeValueDesc();
				}
			}
			if (nicReg.getAddressLine3() != null) {
				codeV = codesService.findByStringCodeId(
						CodeValues.CODE_ID_TOWN_VILLAGE,
						nicReg.getAddressLine3());
				if (codeV != null) {
					address += ", " + codeV.getCodeValueDesc();
				}
			}
			if (nicReg.getAddressLine4() != null) {
				codeV = codesService.findByStringCodeId(
						CodeValues.CODE_ID_DISTRICT, nicReg.getAddressLine4());
				if (codeV != null) {
					address += ", " + codeV.getCodeValueDesc();
				}
			}
			tran.setJob(nicReg.getJob());
			tran.setTransactionId(nicTran.getTransactionId());
			tran.setResidentAddress(address);
		}
		return tran;
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
