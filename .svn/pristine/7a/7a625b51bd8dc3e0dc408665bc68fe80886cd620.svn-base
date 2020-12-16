package com.nec.asia.nic.comp.trans.service.impl;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nec.asia.nic.comp.job.dto.LogInfoDTO;
import com.nec.asia.nic.comp.report.dao.VNicTransactionLogDao;
import com.nec.asia.nic.comp.trans.dao.NicIssuanceDataDao;
import com.nec.asia.nic.comp.trans.dao.NicRegistrationDataDao;
import com.nec.asia.nic.comp.trans.dao.NicTransactionDao;
import com.nec.asia.nic.comp.trans.dao.NicTransactionLogDao;
import com.nec.asia.nic.comp.trans.dao.NicTransactionPaymentDao;
import com.nec.asia.nic.comp.trans.domain.NicRegistrationData;
import com.nec.asia.nic.comp.trans.domain.NicTransaction;
import com.nec.asia.nic.comp.trans.domain.NicTransactionLog;
import com.nec.asia.nic.comp.trans.domain.NicIssuanceData;
import com.nec.asia.nic.comp.trans.dto.CardStatusRptDTO;
import com.nec.asia.nic.comp.trans.dto.ExceptionHandlngRptDto;
import com.nec.asia.nic.comp.trans.dto.RICBatchCardInfoDto;
import com.nec.asia.nic.comp.trans.dto.RICPymtRptDto;
import com.nec.asia.nic.comp.trans.dto.RICTxnRptDto;
import com.nec.asia.nic.comp.trans.dto.RICTxnUpldRptDto;
import com.nec.asia.nic.comp.trans.dto.RICWaiverRptDto;
import com.nec.asia.nic.comp.trans.service.RICReportService;
import com.nec.asia.nic.comp.trans.utils.ReportConstants;
import com.nec.asia.nic.framework.PaginatedResult;
import com.nec.asia.nic.framework.admin.code.domain.CodeValues;
import com.nec.asia.nic.framework.admin.code.service.CodesService;
import com.nec.asia.nic.framework.dao.DaoException;
import com.nec.asia.nic.framework.report.service.AuditAccessLogService;
import com.nec.asia.nic.framework.service.impl.DefaultBusinessServiceImpl;
import com.nec.asia.nic.util.TransLogInfoXmlConvertor;

/*
 * * 16 jan 2014 (priya): added ricSiteCheck for getRicBatchCardInfoRptRecordList,getCardCollectedStatusRptRecordList,cardRejStatusRptCriteria,unColCardStatuSrchCriteria
 */
@Service("ricReportService")
public class RICReportServiceImpl extends
		DefaultBusinessServiceImpl<NicTransaction, String, NicTransactionDao>
		implements RICReportService {

	public RICReportServiceImpl() {
	}

	@Autowired
	public RICReportServiceImpl(NicTransactionDao dao) {
		this.dao = dao;
	}

	@Autowired
	private NicRegistrationDataDao registrationDataDao = null;
	@Autowired
	private CodesService codeService = null;
	@Autowired
	private NicTransactionPaymentDao nicTransactionPaymentDao = null;
	@Autowired
	private NicIssuanceDataDao nicIssuanceDataDao = null;
	@Autowired
	private VNicTransactionLogDao vNicTransactionLogDao = null;

	
	@Autowired
	private NicTransactionLogDao transactionLogDao = null;
	
	public static TransLogInfoXmlConvertor util = new TransLogInfoXmlConvertor();

	@Override
	public PaginatedResult<RICTxnRptDto> getRicTxnRptRecordList(
			RICTxnRptDto ricTxnRptCriteria, int pageNo, int pageSize) {
		List<RICTxnRptDto> recordList = new ArrayList<RICTxnRptDto>();
		SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss");
		int serialNum = (pageNo - 1) * pageSize;
		try {
			if (ricTxnRptCriteria.getStartDate() != null) {
				ricTxnRptCriteria.setTxnStartDate(df.parse(ricTxnRptCriteria
						.getStartDate() + " 00:00:00"));
			}
			if (ricTxnRptCriteria.getEndDate() != null) {
				ricTxnRptCriteria.setTxnEndDate(df.parse(ricTxnRptCriteria
						.getEndDate() + " 23:59:59"));
			}
			if (ricTxnRptCriteria.getEstColStartDt() != null) {
				ricTxnRptCriteria.setEstColStartDate(df.parse(ricTxnRptCriteria
						.getEstColStartDt() + " 00:00:00"));
			}
			if (ricTxnRptCriteria.getEstColEndDt() != null) {
				ricTxnRptCriteria.setEstColEndDate(df.parse(ricTxnRptCriteria
						.getEstColEndDt() + " 23:59:59"));
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}
		PaginatedResult<NicRegistrationData> result = registrationDataDao
				.getRicTxnRptRecordList(ricTxnRptCriteria, pageNo, pageSize);
		try {
			if (result != null) {
				for (NicRegistrationData row : result.getRows()) {
					RICTxnRptDto record = new RICTxnRptDto();
					serialNum = serialNum + 1;
					record.setSerialNo(Integer.toString(serialNum));
//					record.setNin(row.getNin());
//					record.setTransactionNo(row.getNicTransaction()
//							.getApplnRefNo());
//					if (row.getNicTransaction().getTransactionType() != null) {
//						record.setTxnType(codeService.getCodeValueByIdName(
//								ReportConstants.CODE_ID_TRANSACTION_TYPE,
//								row.getNicTransaction().getTransactionType())
//								.getCodeValueDesc());
//					}
//					if (row.getNicTransaction().getTransactionSubtype() != null) {
//						CodeValues txnSubTyp = codeService.getCodeValueByIdName(
//										ReportConstants.CODE_ID_TRANSACTION_SUBTYPE,
//										row.getNicTransaction()
//												.getTransactionSubtype());
//						record.setTxnSubType((txnSubTyp != null) ? txnSubTyp
//								.getCodeValueDesc() : row.getNicTransaction()
//								.getTransactionSubtype());
//					}
//					if (row.getNicTransaction().getTransactionStatus() != null) {
//						CodeValues txnStatus = codeService.getCodeValueByIdName(
//										ReportConstants.CODE_ID_TRANSACTION_STATUS,
//										row.getNicTransaction().getTransactionStatus());
//						record.setTxnStatus((txnStatus != null) ? txnStatus
//								.getCodeValueDesc() : row.getNicTransaction()
//								.getTransactionStatus());
//					}
//					if (row.getNicTransaction().getDateOfApplication() != null) {
//						record.setApplicationDate(df.format(row
//								.getNicTransaction().getDateOfApplication()));
//					}
//					if (row.getNicTransaction().getUpdateDate() != null) {
//						record.setLastModifiedDate(df.format(row
//								.getNicTransaction().getUpdateDate()));
//					}
					record.setFirstName(row.getFirstnameFull());
					record.setSurName(row.getSurnameFull());
//					/record.setSurNameBirth(row.getSurnameBirthFull());
					if (row.getGender() != null) {
						record.setGender(codeService.getCodeValueByIdName(
								ReportConstants.CODE_ID_GENDER,
								row.getGender()).getCodeValueDesc());
					}
//					if (row.getEstCollectionDate() != null) {
//						record.setEstColStartDt(df.format(row
//								.getEstCollectionDate()));
//					}
					recordList.add(record);
				}
				PaginatedResult<RICTxnRptDto> json = new PaginatedResult<RICTxnRptDto>(
						result.getTotal(), pageNo, recordList);
				return json;

			} else {
				return null;
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}

	}

	@Override
	public PaginatedResult<RICPymtRptDto> getRicPymtRptRecordList(
			RICPymtRptDto ricPymtRptCriteria, int pageNo, int pageSize) {
		SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss");
		try {
			if (ricPymtRptCriteria.getStartDate() != null) {
				Date fromDate = df.parse(ricPymtRptCriteria.getStartDate()
						+ " 00:00:00");
				ricPymtRptCriteria.setTxnStartDate(fromDate);
			}
			if (ricPymtRptCriteria.getEndDate() != null) {
				Date toDate = df.parse(ricPymtRptCriteria.getEndDate()
						+ " 23:59:59");
				ricPymtRptCriteria.setTxnEndDate(toDate);
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}
		List<RICPymtRptDto> recordListPg = new ArrayList<RICPymtRptDto>();
		PaginatedResult<RICPymtRptDto> result = nicTransactionPaymentDao
				.getRicPymtRptRecordList(ricPymtRptCriteria, pageNo, pageSize);
		int total = result.getTotal();
		/*
		 * System.out.println("Page >>"+pageNo);
		 * System.out.println("pageSize >>"+pageSize);
		 */
		int maxNoPage = 1;
		if (total > pageSize) {
			maxNoPage = (int) Math.ceil(((double) total / pageSize));
			// System.out.println("maxNoPage >>"+maxNoPage);
		}
		int firstResults = (pageNo - 1) < 0 ? 0 : (pageNo - 1) * pageSize;
		int maxResults = pageSize;
		if (pageNo == maxNoPage) {
			maxResults = total % pageSize;
			// System.out.println("total % pageSize >>"+maxResults);
			// Swapna:(30082013): Fixed pagination issue.
			if (maxResults == 0) {
				maxResults = pageSize;
				// System.out.println("maxResults==0 >>"+maxResults);
			}
		}
		if (maxResults + firstResults <= total) {
			maxResults = maxResults + firstResults;
			// System.out.println("maxResults+firstResults >>"+maxResults);
		}
		try {
			/*
			 * System.out.println("firstResults >>"+firstResults);
			 * System.out.println("maxResults >>"+maxResults);
			 */
			if (result != null && result.getTotal() > 0) {
				for (int j = firstResults; j <= result.getTotal(); j++) {
					if (j < maxResults) {
						// System.out.println("i >>"+j);
						RICPymtRptDto record = new RICPymtRptDto();
						firstResults = firstResults + 1;
						record.setSerialNo(Integer.toString(firstResults));
						record.setCollectedBy(result.getRows().get(j)
								.getCollectedBy());
						record.setNin(result.getRows().get(j).getNin());
						record.setPymtNo(result.getRows().get(j).getPymtNo());
						record.setTxnNo(result.getRows().get(j).getTxnNo());
						if (result.getRows().get(j).getTxnType() != null
								|| result.getRows().get(j).getTxnType() != "") {
							record.setTxnType(codeService
									.getCodeValueByIdName(
											ReportConstants.CODE_ID_TRANSACTION_TYPE,
											result.getRows().get(j)
													.getTxnType())
									.getCodeValueDesc());
						}
						if (result.getRows().get(j).getTxnSubType() != null
								|| result.getRows().get(j).getTxnSubType() != "") {
							record.setTxnSubType(codeService
									.getCodeValueByIdName(
											ReportConstants.CODE_ID_TRANSACTION_SUBTYPE,
											result.getRows().get(j)
													.getTxnSubType())
									.getCodeValueDesc());
						}
						record.setPymtAmt(result.getRows().get(j).getPymtAmt());
						record.setPymtStatus(result.getRows().get(j)
								.getPymtStatus());
						record.setPymtDate(result.getRows().get(j)
								.getPymtDate());
						record.setFirstName(result.getRows().get(j)
								.getFirstName());
						record.setSurName(result.getRows().get(j).getSurName());
						record.setSurNameBirth(result.getRows().get(j)
								.getSurNameBirth());
						record.setCitizenType(result.getRows().get(j)
								.getCitizenType());
						recordListPg.add(record);
					}
				}
				PaginatedResult<RICPymtRptDto> json = new PaginatedResult<RICPymtRptDto>(
						result.getTotal(), pageNo, recordListPg);
				result = json;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return result;
	}

	@Override
	public PaginatedResult<RICWaiverRptDto> getRicWaiverRptRecordList(
			RICWaiverRptDto ricWaiverRptCriteria, int pageNo, int pageSize) {
		SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss");
		try {
			if (ricWaiverRptCriteria.getStartDate() != null) {
				Date fromDate = df.parse(ricWaiverRptCriteria.getStartDate()
						+ " 00:00:00");
				ricWaiverRptCriteria.setTxnStartDate(fromDate);
			}
			if (ricWaiverRptCriteria.getEndDate() != null) {
				Date toDate = df.parse(ricWaiverRptCriteria.getEndDate()
						+ " 23:59:59");
				ricWaiverRptCriteria.setTxnEndDate(toDate);
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}
		List<RICWaiverRptDto> recordListPg = new ArrayList<RICWaiverRptDto>();
		PaginatedResult<RICWaiverRptDto> result = nicTransactionPaymentDao
				.getRicWaiverRptRecordList(ricWaiverRptCriteria, pageNo,
						pageSize);
		int total = result.getTotal();
		int maxNoPage = 1;
		if (total > pageSize) {
			maxNoPage = (int) Math.ceil(((double) total / pageSize));
		}
		int firstResults = (pageNo - 1) < 0 ? 0 : (pageNo - 1) * pageSize;
		int maxResults = pageSize;
		if (pageNo == maxNoPage) {
			maxResults = total % pageSize;
			if (maxResults == 0) {
				maxResults = pageSize;
			}
		}
		if (maxResults + firstResults <= total) {
			maxResults = maxResults + firstResults;
		}
		try {
			if (result != null && result.getTotal() > 0) {
				for (int j = firstResults; j <= result.getTotal(); j++) {
					if (j < maxResults) {
						RICWaiverRptDto record = new RICWaiverRptDto();
						firstResults = firstResults + 1;
						record.setSerialNo(Integer.toString(firstResults));
						record.setWaiverNo(result.getRows().get(j)
								.getWaiverNo());
						record.setTxnNo(result.getRows().get(j).getTxnNo());
						record.setNin(result.getRows().get(j).getNin());
						record.setFirstName(result.getRows().get(j)
								.getFirstName());
						record.setSurName(result.getRows().get(j).getSurName());
						record.setSurNameBirth(result.getRows().get(j)
								.getSurNameBirth());
						if (result.getRows().get(j).getTxnType() != null
								|| result.getRows().get(j).getTxnType() != "") {
							record.setTxnType(codeService
									.getCodeValueByIdName(
											ReportConstants.CODE_ID_TRANSACTION_TYPE,
											result.getRows().get(j)
													.getTxnType())
									.getCodeValueDesc());
						}
						if (result.getRows().get(j).getTxnSubType() != null
								|| result.getRows().get(j).getTxnSubType() != "") {
							record.setTxnSubType(codeService
									.getCodeValueByIdName(
											ReportConstants.CODE_ID_TRANSACTION_SUBTYPE,
											result.getRows().get(j)
													.getTxnSubType())
									.getCodeValueDesc());
						}
						record.setCitizenType(result.getRows().get(j)
								.getCitizenType());
						record.setWaiverAmt(result.getRows().get(j)
								.getWaiverAmt());
						record.setWaiverDate(result.getRows().get(j)
								.getWaiverDate());
						record.setWaiverReason(result.getRows().get(j)
								.getWaiverReason());
						record.setWaiverOfficer(result.getRows().get(j)
								.getWaiverOfficer());
						record.setServedBy(result.getRows().get(j)
								.getServedBy());
						recordListPg.add(record);
					}
				}
				PaginatedResult<RICWaiverRptDto> json = new PaginatedResult<RICWaiverRptDto>(
						result.getTotal(), pageNo, recordListPg);
				result = json;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return result;
	}

	@Override
	public PaginatedResult<RICTxnUpldRptDto> getRicTxnUploadRptRecordList(
			RICTxnUpldRptDto ricTxnUpldRptCriteria, int pageNo, int pageSize) {
		List<RICTxnUpldRptDto> recordList = new ArrayList<RICTxnUpldRptDto>();
		SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss");
		int serialNum = (pageNo - 1) * pageSize;
		try {
			if (ricTxnUpldRptCriteria.getStartDate() != null) {
				ricTxnUpldRptCriteria.setTxnStartDate(df
						.parse(ricTxnUpldRptCriteria.getStartDate()
								+ " 00:00:00"));
			}
			if (ricTxnUpldRptCriteria.getEndDate() != null) {
				ricTxnUpldRptCriteria
						.setTxnEndDate(df.parse(ricTxnUpldRptCriteria
								.getEndDate() + " 23:59:59"));
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}
		PaginatedResult<NicRegistrationData> result = registrationDataDao
				.getRicTxnUploadRptRecordList(ricTxnUpldRptCriteria, pageNo,
						pageSize);
		System.out.println("Result in result >>" + result.getTotal());
		try {
			if (result != null) {
				for (NicRegistrationData row : result.getRows()) {
					RICTxnUpldRptDto record = new RICTxnUpldRptDto();
					serialNum = serialNum + 1;
					record.setSerialNo(Integer.toString(serialNum));
					record.setTransactionNo(row.getTransactionId());
//					record.setTransactionNo(row.getNicTransaction().getApplnRefNo());
//					record.setNin(row.getNin());
//					if (row.getNicTransaction().getTransactionType() != null) {
//						record.setTxnType(codeService.getCodeValueByIdName(
//								ReportConstants.CODE_ID_TRANSACTION_TYPE,
//								row.getNicTransaction().getTransactionType())
//								.getCodeValueDesc());
//					}
//					if (row.getNicTransaction().getTransactionSubtype() != null) {
//						CodeValues txnSubTyp = codeService
//								.getCodeValueByIdName(
//										ReportConstants.CODE_ID_TRANSACTION_SUBTYPE,
//										row.getNicTransaction()
//												.getTransactionSubtype());
//						record.setTxnSubType((txnSubTyp != null) ? txnSubTyp
//								.getCodeValueDesc() : row.getNicTransaction()
//								.getTransactionSubtype());
//					}
					record.setFirstName(row.getFirstnameFull());
					record.setSurName(row.getSurnameFull());
//					record.setSurNameBirth(row.getSurnameBirthFull());
//					if (row.getGender() != null) {
//						record.setGender(codeService.getCodeValueByIdName(
//								ReportConstants.CODE_ID_GENDER,
//								row.getGender()).getCodeValueDesc());
//					}
//					record.setTxnServedBy(row.getNicTransaction().getCreateBy());
//					if (row.getNicTransaction().getTransactionStatus() != null) {
//						CodeValues txnStatus = codeService
//								.getCodeValueByIdName(
//										ReportConstants.CODE_ID_TRANSACTION_STATUS,
//										row.getNicTransaction()
//												.getTransactionSubtype());
//						record.setTxnStatus((txnStatus != null) ? txnStatus
//								.getCodeValueDesc() : row.getNicTransaction()
//								.getTransactionSubtype());
//					}
//					record.setApplicationDate(df.format(row.getNicTransaction()
//							.getDateOfApplication()));
//					record.setTxnUpldStatus("YES");
					recordList.add(record);
				}
				PaginatedResult<RICTxnUpldRptDto> json = new PaginatedResult<RICTxnUpldRptDto>(
						result.getTotal(), pageNo, recordList);
				System.out.println("Result in Service >>" + json.getTotal());
				return json;

			} else {
				return null;
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}

	}

	@Override
	public PaginatedResult<RICTxnRptDto> getRicRejTxnRptRecordList(
			RICTxnRptDto ricRejTxnRptCriteria, int pageNo, int pageSize) {
		List<RICTxnRptDto> recordList = new ArrayList<RICTxnRptDto>();
		SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss");
		int serialNum = (pageNo - 1) * pageSize;
		try {
			if (ricRejTxnRptCriteria.getRejStartDate() != null) {
				ricRejTxnRptCriteria.setTxnRejStartDate(df
						.parse(ricRejTxnRptCriteria.getRejStartDate()
								+ " 00:00:00"));
			}
			if (ricRejTxnRptCriteria.getRejEndDate() != null) {
				ricRejTxnRptCriteria.setTxnRejEndDate(df
						.parse(ricRejTxnRptCriteria.getRejEndDate()
								+ " 23:59:59"));
			}
			if (ricRejTxnRptCriteria.getStartDate() != null) {
				ricRejTxnRptCriteria.setTxnStartDate(df
						.parse(ricRejTxnRptCriteria.getStartDate()
								+ " 00:00:00"));
			}
			if (ricRejTxnRptCriteria.getEndDate() != null) {
				ricRejTxnRptCriteria
						.setTxnEndDate(df.parse(ricRejTxnRptCriteria
								.getEndDate() + " 23:59:59"));
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}
		System.out.println("Entering into the Service");
		List<RICTxnRptDto> result = null;
		try {
			result = dao.getRicRejTxnRptRecordList(ricRejTxnRptCriteria);
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		int total = result.size();
		int maxNoPage = 1;
		if (total > pageSize) {
			maxNoPage = (int) Math.ceil(((double) total / pageSize));
		}
		int firstResults = (pageNo - 1) < 0 ? 0 : (pageNo - 1) * pageSize;
		int maxResults = pageSize;
		if (pageNo == maxNoPage) {
			maxResults = total % pageSize;
			if (maxResults == 0) {
				maxResults = pageSize;
			}
		}
		if (maxResults + firstResults <= total) {
			maxResults = maxResults + firstResults;
		}
		try {
			if (result != null && result.size() > 0) {
				for (int j = firstResults; j <= result.size(); j++) {
					if (j < maxResults) {
						RICTxnRptDto record = new RICTxnRptDto();
						firstResults = firstResults + 1;
						record.setSerialNo(Integer.toString(firstResults));
						record.setTransactionNo(result.get(j)
								.getTransactionNo());
						record.setNin(result.get(j).getNin());
						if (result.get(j).getTxnType() != null) {
							record.setTxnType(codeService
									.getCodeValueByIdName(
											ReportConstants.CODE_ID_TRANSACTION_TYPE,
											result.get(j).getTxnType())
									.getCodeValueDesc());
						}
						if (result.get(j).getTxnSubType() != null) {
							CodeValues txnSubTyp = codeService
									.getCodeValueByIdName(
											ReportConstants.CODE_ID_TRANSACTION_SUBTYPE,
											result.get(j).getTxnSubType());
							record.setTxnSubType((txnSubTyp != null) ? txnSubTyp
									.getCodeValueDesc() : result.get(j)
									.getTxnSubType());
						}
						record.setFirstName(result.get(j).getFirstName());
						record.setSurName(result.get(j).getSurName());
						record.setSurNameBirth(result.get(j).getSurNameBirth());
						record.setTxnServedBy(result.get(j).getTxnServedBy());
						record.setApplicationDate(result.get(j)
								.getApplicationDate());
						if (result.get(j).getLastModifiedDate() != null) {
							record.setLastModifiedDate(result.get(j)
									.getLastModifiedDate());
						}
						record.setReason(result.get(j).getReason());
						record.setRemarks(result.get(j).getRemarks());
						recordList.add(record);
					}
				}
				PaginatedResult<RICTxnRptDto> json = new PaginatedResult<RICTxnRptDto>(
						total, pageNo, recordList);
				return json;
			} else {
				return null;
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}

	}

	@Override
	public PaginatedResult<RICBatchCardInfoDto> getRicBatchCardInfoRptRecordList(
			RICBatchCardInfoDto ricBatchCardInfoRptCriteria, int pageNo,
			int pageSize) {
		System.out.println("Entering into the Service");
		List<RICBatchCardInfoDto> recordList = new ArrayList<RICBatchCardInfoDto>();
		SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss");
		try {
			if (ricBatchCardInfoRptCriteria.getTxnStartDate() != null) {
				ricBatchCardInfoRptCriteria.setTransStartDate(df
						.parse(ricBatchCardInfoRptCriteria.getTxnStartDate()
								+ " 00:00:00"));
			}
			if (ricBatchCardInfoRptCriteria.getTxnEndDate() != null) {
				ricBatchCardInfoRptCriteria.setTransEndDate(df
						.parse(ricBatchCardInfoRptCriteria.getTxnEndDate()
								+ " 23:59:59"));
			}
			if (ricBatchCardInfoRptCriteria.getCardPkgStartDate() != null) {
				ricBatchCardInfoRptCriteria.setCrdPkgStartDate(df
						.parse(ricBatchCardInfoRptCriteria
								.getCardPkgStartDate() + " 00:00:00"));
			}
			if (ricBatchCardInfoRptCriteria.getCardPkgEndDate() != null) {
				ricBatchCardInfoRptCriteria.setCrdPkgEndDate(df
						.parse(ricBatchCardInfoRptCriteria.getCardPkgEndDate()
								+ " 23:59:59"));
			}
			if (ricBatchCardInfoRptCriteria.getCardIssueDate() != null) {
				ricBatchCardInfoRptCriteria.setCrdIssueStartDate(df
						.parse(ricBatchCardInfoRptCriteria.getCardIssueDate()
								+ " 00:00:00"));
			}
			if (ricBatchCardInfoRptCriteria.getCardIssueEndDate() != null) {
				ricBatchCardInfoRptCriteria.setCrdIssueEndDate(df
						.parse(ricBatchCardInfoRptCriteria
								.getCardIssueEndDate() + " 23:59:59"));
			}
			if (ricBatchCardInfoRptCriteria.getCardExpiryDate() != null) {
				ricBatchCardInfoRptCriteria.setCrdExpiryStartDate(df
						.parse(ricBatchCardInfoRptCriteria.getCardExpiryDate()
								+ " 00:00:00"));
			}
			if (ricBatchCardInfoRptCriteria.getCardExpiryEndDate() != null) {
				ricBatchCardInfoRptCriteria.setCrdExpiryEndDate(df
						.parse(ricBatchCardInfoRptCriteria
								.getCardExpiryEndDate() + " 23:59:59"));
			}

		} catch (ParseException e) {
			e.printStackTrace();
		}
		List<RICBatchCardInfoDto> result = null;
		try {
			result = nicIssuanceDataDao
					.getRicBatchCardInfoRptRecordList(ricBatchCardInfoRptCriteria);
		} catch (DaoException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		int total = result.size();
		int maxNoPage = 1;
		if (total > pageSize) {
			maxNoPage = (int) Math.ceil(((double) total / pageSize));
		}
		int firstResults = (pageNo - 1) < 0 ? 0 : (pageNo - 1) * pageSize;
		int maxResults = pageSize;
		if (pageNo == maxNoPage) {
			maxResults = total % pageSize;
			if (maxResults == 0) {
				maxResults = pageSize;
			}
		}
		if (maxResults + firstResults <= total) {
			maxResults = maxResults + firstResults;
		}
		try {
			if (result != null && result.size() > 0) {
				for (int j = firstResults; j <= result.size(); j++) {
					if (j < maxResults) {
						RICBatchCardInfoDto record = new RICBatchCardInfoDto();
						firstResults = firstResults + 1;
						record.setSerialNo(Integer.toString(firstResults));
						record.setBatchId(result.get(j).getBatchId());
						record.setNin(result.get(j).getNin());
						record.setTxnNo(result.get(j).getTxnNo());
						record.setCcNo(result.get(j).getCcNo());
						record.setCardIssueDate(result.get(j)
								.getCardIssueDate());
						record.setCardStatus(result.get(j).getCardStatus());
						record.setCardExpiryDate(result.get(j)
								.getCardExpiryDate());// Date of Expiry need to
														// set
						record.setApplicationDate(result.get(j)
								.getApplicationDate());
						record.setPackageDate(result.get(j).getPackageDate());
						record.setPkgSeqNo(result.get(j).getPkgSeqNo());
						record.setFirstName(result.get(j).getFirstName());
						record.setSurName(result.get(j).getSurName());
						record.setGender(result.get(j).getGender());
						recordList.add(record);
					}
				}
				PaginatedResult<RICBatchCardInfoDto> json = new PaginatedResult<RICBatchCardInfoDto>(
						total, pageNo, recordList);
				return json;
			} else {
				return null;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public PaginatedResult<CardStatusRptDTO> getCardCollectedStatusRptRecordList(
			CardStatusRptDTO cardColStatusRptCriteria, int pageNo, int pageSize) {
		System.out.println("Entering into the Service");
		List<CardStatusRptDTO> recordList = new ArrayList<CardStatusRptDTO>();
		SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss");
		try {
			if (cardColStatusRptCriteria.getCardCollStartDt() != null) {
				cardColStatusRptCriteria.setCrdCollStartDt(df
						.parse(cardColStatusRptCriteria.getCardCollStartDt()
								+ " 00:00:00"));
			}
			if (cardColStatusRptCriteria.getCardCollEndDt() != null) {
				cardColStatusRptCriteria.setCrdCollEndDt(df
						.parse(cardColStatusRptCriteria.getCardCollEndDt()
								+ " 23:59:59"));
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}
		List<CardStatusRptDTO> result = null;
		try {
			result = nicIssuanceDataDao
					.getCardCollectedStatusRptRecordList(cardColStatusRptCriteria);
			System.out.println("records nicIssuanceDataDao Result >>"
					+ result.size());
		} catch (DaoException e1) {
			e1.printStackTrace();
		}
		int total = result.size();
		int maxNoPage = 1;
		if (total > pageSize) {
			maxNoPage = (int) Math.ceil(((double) total / pageSize));
		}
		int firstResults = (pageNo - 1) < 0 ? 0 : (pageNo - 1) * pageSize;
		int maxResults = pageSize;
		if (pageNo == maxNoPage) {
			maxResults = total % pageSize;
			if (maxResults == 0) {
				maxResults = pageSize;
			}
		}
		if (maxResults + firstResults <= total) {
			maxResults = maxResults + firstResults;
		}
		try {
			if (result != null && result.size() > 0) {
				for (int j = firstResults; j <= result.size(); j++) {
					if (j < maxResults) {
						CardStatusRptDTO record = new CardStatusRptDTO();
						firstResults = firstResults + 1;
						record.setSerialNo(Integer.toString(firstResults));
						record.setTransactionNo(result.get(j)
								.getTransactionNo());
						record.setNin(result.get(j).getNin());
						record.setCcnNo(result.get(j).getCcnNo());
						record.setCardIssuDt(result.get(j).getCardIssuDt());
						record.setCardStatus(result.get(j).getCardStatus());
						record.setPkgSeqNo(result.get(j).getPkgSeqNo());
						record.setPkgId(result.get(j).getPkgId());
						record.setIssuedBy(result.get(j).getIssuedBy());
						record.setFirstName(result.get(j).getFirstName());
						record.setSurName(result.get(j).getSurName());
						record.setSurNameAtBirth(result.get(j)
								.getSurNameAtBirth());
						recordList.add(record);
					}
				}
				PaginatedResult<CardStatusRptDTO> json = new PaginatedResult<CardStatusRptDTO>(
						total, pageNo, recordList);
				System.out.println("records Result >>" + recordList.size());
				return json;
			} else {
				return null;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

	}

	@Override
	public PaginatedResult<CardStatusRptDTO> getCardRejectedRptRecordList(
			CardStatusRptDTO cardRejStatusRptCriteria, int pageNo, int pageSize) {
		System.out.println("Entering into the Service");
		List<CardStatusRptDTO> recordList = new ArrayList<CardStatusRptDTO>();
		SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss");
		try {
			if (cardRejStatusRptCriteria.getCardCollStartDt() != null) {
				cardRejStatusRptCriteria.setCrdCollStartDt(df
						.parse(cardRejStatusRptCriteria.getCardCollStartDt()
								+ " 00:00:00"));
			}
			if (cardRejStatusRptCriteria.getCardCollEndDt() != null) {
				cardRejStatusRptCriteria.setCrdCollEndDt(df
						.parse(cardRejStatusRptCriteria.getCardCollEndDt()
								+ " 23:59:59"));
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}
		List<CardStatusRptDTO> result = null;
		try {
			result = nicIssuanceDataDao
					.getCardRejectedRptRecordList(cardRejStatusRptCriteria);
		} catch (DaoException e1) {
			e1.printStackTrace();
		}
		int total = result.size();
		int maxNoPage = 1;
		if (total > pageSize) {
			maxNoPage = (int) Math.ceil(((double) total / pageSize));
		}
		int firstResults = (pageNo - 1) < 0 ? 0 : (pageNo - 1) * pageSize;
		int maxResults = pageSize;
		if (pageNo == maxNoPage) {
			maxResults = total % pageSize;
			if (maxResults == 0) {
				maxResults = pageSize;
			}
		}
		if (maxResults + firstResults <= total) {
			maxResults = maxResults + firstResults;
		}
		try {
			if (result != null && result.size() > 0) {
				for (int j = firstResults; j <= result.size(); j++) {
					if (j < maxResults) {
						CardStatusRptDTO record = new CardStatusRptDTO();
						firstResults = firstResults + 1;
						record.setSerialNo(Integer.toString(firstResults));
						record.setTransactionNo(result.get(j)
								.getTransactionNo());
						record.setNin(result.get(j).getNin());
						record.setCcnNo(result.get(j).getCcnNo());
						record.setCardRejectDt(result.get(j).getCardRejectDt());
						record.setIssuedBy(result.get(j).getIssuedBy());
						record.setPkgSeqNo(result.get(j).getPkgSeqNo());
						record.setPkgId(result.get(j).getPkgId());
						record.setRemarks(result.get(j).getRemarks());
						record.setRejectionReason(result.get(j)
								.getRejectionReason());
						record.setFirstName(result.get(j).getFirstName());
						record.setSurName(result.get(j).getSurName());
						record.setSurNameAtBirth(result.get(j)
								.getSurNameAtBirth());
						recordList.add(record);
					}
				}
				PaginatedResult<CardStatusRptDTO> json = new PaginatedResult<CardStatusRptDTO>(
						total, pageNo, recordList);
				return json;
			} else {
				return null;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public PaginatedResult<RICTxnRptDto> getUNCollectedStatusRptRecordList(
			RICTxnRptDto unColCardStatuSrchCriteria, int pageNo, int pageSize) {

		List<RICTxnRptDto> recordList = new ArrayList<RICTxnRptDto>();
		SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss");
		SimpleDateFormat df1 = new SimpleDateFormat("dd-MMM-yyyy");
		int serialNum = (pageNo - 1) * pageSize;
		try {
			if (unColCardStatuSrchCriteria.getStartDate() != null) {
				unColCardStatuSrchCriteria.setTxnStartDate(df
						.parse(unColCardStatuSrchCriteria.getStartDate()
								+ " 00:00:00"));
			}
			if (unColCardStatuSrchCriteria.getEndDate() != null) {
				unColCardStatuSrchCriteria.setTxnEndDate(df
						.parse(unColCardStatuSrchCriteria.getEndDate()
								+ " 23:59:59"));
			}
			if (unColCardStatuSrchCriteria.getEstColStartDt() != null) {
				unColCardStatuSrchCriteria.setEstColStartDate(df
						.parse(unColCardStatuSrchCriteria.getEstColStartDt()
								+ " 00:00:00"));
			}
			if (unColCardStatuSrchCriteria.getEstColEndDt() != null) {
				unColCardStatuSrchCriteria.setEstColEndDate(df
						.parse(unColCardStatuSrchCriteria.getEstColEndDt()
								+ " 23:59:59"));
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}

		try {
			PaginatedResult<NicRegistrationData> result = registrationDataDao
					.getRicUnCollectedCardStatusRptRecordList(
							unColCardStatuSrchCriteria, pageNo, pageSize);
			if (result != null) {
				for (NicRegistrationData row : result.getRows()) {
					RICTxnRptDto record = new RICTxnRptDto();
					serialNum = serialNum + 1;
					record.setSerialNo(Integer.toString(serialNum));
					record.setTransactionNo(row.getTransactionId());
//					record.setNin(row.getNin());
					record.setSurName(row.getSurnameFull());
					record.setFirstName(row.getFirstnameFull());
//					record.setSurNameBirth(row.getSurnameBirthFull());
					if (row.getGender() != null) {
						record.setGender(codeService.getCodeValueByIdName(
								ReportConstants.CODE_ID_GENDER,
								row.getGender()).getCodeValueDesc());
					}
//					record.setApplicationDate(df1.format(row
//							.getNicTransaction().getDateOfApplication()));
//					if (row.getNicTransaction().getTransactionStatus() != null) {
//						CodeValues txnStatus = codeService
//								.getCodeValueByIdName(
//										ReportConstants.CODE_ID_TRANSACTION_STATUS,
//										row.getNicTransaction()
//												.getTransactionStatus());
//						record.setTxnStatus((txnStatus != null) ? txnStatus
//								.getCodeValueDesc() : row.getNicTransaction()
//								.getTransactionStatus());
//					}
//					record.setEstColStartDt(df1.format(row.getEstCollectionDate()));
//					Calendar cal = Calendar.getInstance();
//					cal.setTime(row.getEstCollectionDate());
//					record.setNoOfDays(getNoOfDays(cal));
					record.setContactNo(row.getContactNo());
					record.setEmailAddr(row.getEmail());
					record.setHouseNo(row.getAddressLine1());
					record.setStreet(row.getAddressLine2());
					record.setLocality(row.getAddressLine3());
					record.setCity(row.getAddressLine4());
//					record.setDistrict(row.getAddressLine5());
//					record.setSiteCode(row.getNicTransaction().getRegSiteCode());
//					record.setCollectionSiteCode(row.getNicTransaction().getIssSiteCode());
//					NicIssuanceData iss = nicIssuanceDataDao.getLatestNicIssuanceData(row.getTransactionId());
//					if(iss!=null ){
//						record.setPackageId(iss.getPackageId());
//					}
					recordList.add(record);
				}
				PaginatedResult<RICTxnRptDto> json = new PaginatedResult<RICTxnRptDto>(
						result.getTotal(), pageNo, recordList);
				return json;

			} else {
				return null;
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	} 

	@Override
	public PaginatedResult<CardStatusRptDTO> getCardExpiredStatusRptRecordList(
			CardStatusRptDTO cardExpiredSrchCriteria, int pageNo, int pageSize) {

		System.out.println("Entering into the Service");
		List<CardStatusRptDTO> recordList = new ArrayList<CardStatusRptDTO>();
		SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss");
		try {
			if (cardExpiredSrchCriteria.getCardCollStartDt() != null) {
				cardExpiredSrchCriteria.setCrdCollStartDt(df
						.parse(cardExpiredSrchCriteria.getCardCollStartDt()
								+ " 00:00:00"));
			}
			if (cardExpiredSrchCriteria.getCardCollEndDt() != null) {
				cardExpiredSrchCriteria.setCrdCollEndDt(df
						.parse(cardExpiredSrchCriteria.getCardCollEndDt()
								+ " 23:59:59"));
			}
			if (cardExpiredSrchCriteria.getTxnStartDt() != null) {
				cardExpiredSrchCriteria.setTranStartDt(df
						.parse(cardExpiredSrchCriteria.getTxnStartDt()
								+ " 00:00:00"));
			}
			if (cardExpiredSrchCriteria.getTxnEndDt() != null) {
				cardExpiredSrchCriteria.setTranEndDt(df
						.parse(cardExpiredSrchCriteria.getTxnEndDt()
								+ " 23:59:59"));
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}
		List<CardStatusRptDTO> result = null;
		try {
			result = nicIssuanceDataDao
					.getCardExpiredStatusRptRecordList(cardExpiredSrchCriteria);
		} catch (DaoException e1) {
			e1.printStackTrace();
		}
		int total = result.size();
		int maxNoPage = 1;
		if (total > pageSize) {
			maxNoPage = (int) Math.ceil(((double) total / pageSize));
		}
		int firstResults = (pageNo - 1) < 0 ? 0 : (pageNo - 1) * pageSize;
		int maxResults = pageSize;
		if (pageNo == maxNoPage) {
			maxResults = total % pageSize;
			if (maxResults == 0) {
				maxResults = pageSize;
			}
		}
		if (maxResults + firstResults <= total) {
			maxResults = maxResults + firstResults;
		}
		try {
			if (result != null && result.size() > 0) {
				for (int j = firstResults; j <= result.size(); j++) {
					if (j < maxResults) {
						CardStatusRptDTO record = new CardStatusRptDTO();
						firstResults = firstResults + 1;
						record.setSerialNo(Integer.toString(firstResults));
						record.setTransactionNo(result.get(j)
								.getTransactionNo());
						record.setNin(result.get(j).getNin());
						record.setCcnNo(result.get(j).getCcnNo());
						record.setFirstName(result.get(j).getFirstName());
						record.setSurName(result.get(j).getSurName());
						record.setSurNameAtBirth(result.get(j)
								.getSurNameAtBirth());
						record.setTxnDt(result.get(j).getTxnDt());
						record.setExpiryDt(result.get(j).getExpiryDt());
						record.setPkgSeqNo(result.get(j).getPkgSeqNo());
						record.setPkgId(result.get(j).getPkgId());
						recordList.add(record);
					}
				}
				PaginatedResult<CardStatusRptDTO> json = new PaginatedResult<CardStatusRptDTO>(
						total, pageNo, recordList);
				return json;
			} else {
				return null;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

	}

	@Override
	public PaginatedResult<CardStatusRptDTO> getCardReActRptRecordList(
			CardStatusRptDTO cardReActSrchCriteria, int pageNo, int pageSize) {
		System.out.println("Entering into the Service");
		List<CardStatusRptDTO> recordList = new ArrayList<CardStatusRptDTO>();
		SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss");
		try {
			if (cardReActSrchCriteria.getCardCollStartDt() != null) {
				cardReActSrchCriteria.setCrdCollStartDt(df
						.parse(cardReActSrchCriteria.getCardCollStartDt()
								+ " 00:00:00"));
			}
			if (cardReActSrchCriteria.getCardCollEndDt() != null) {
				cardReActSrchCriteria.setCrdCollEndDt(df
						.parse(cardReActSrchCriteria.getCardCollEndDt()
								+ " 23:59:59"));
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}
		List<CardStatusRptDTO> result = null;
		try {
			result = vNicTransactionLogDao.getCardReActRptRecordList(cardReActSrchCriteria);
		} catch (DaoException e1) {
			e1.printStackTrace();
		}
		int total = result.size();
		int maxNoPage = 1;
		if (total > pageSize) {
			maxNoPage = (int) Math.ceil(((double) total / pageSize));
		}
		int firstResults = (pageNo - 1) < 0 ? 0 : (pageNo - 1) * pageSize;
		int maxResults = pageSize;
		if (pageNo == maxNoPage) {
			maxResults = total % pageSize;
			if (maxResults == 0) {
				maxResults = pageSize;
			}
		}
		if (maxResults + firstResults <= total) {
			maxResults = maxResults + firstResults;
		}
		try {
			if (result != null && result.size() > 0) {
				for (int j = firstResults; j <= result.size(); j++) {
					if (j < maxResults) {
						CardStatusRptDTO dto = new CardStatusRptDTO();
						firstResults = firstResults + 1;
						dto.setSerialNo(Integer.toString(firstResults));
						dto.setTransactionNo(result.get(j).getSerialNo());
						dto.setNin(result.get(j).getNin());
						dto.setCcnNo(result.get(j).getCcnNo());
						dto.setFirstName(result.get(j).getFirstName());
						dto.setSurName(result.get(j).getSurName());
						dto.setSurNameAtBirth(result.get(j).getSurNameAtBirth());
						dto.setReDeActivationDate(result.get(j)
								.getReDeActivationDate());
						dto.setCardStatus(result.get(j).getCardStatus());
						dto.setRemarks(result.get(j).getRemarks());
						dto.setIssuedBy(result.get(j).getIssuedBy());
						recordList.add(dto);
					}
				}
				PaginatedResult<CardStatusRptDTO> json = new PaginatedResult<CardStatusRptDTO>(
						total, pageNo, recordList);
				return json;
			} else {
				return new PaginatedResult<CardStatusRptDTO>(0, 0,recordList);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return new PaginatedResult<CardStatusRptDTO>(0, 0,recordList);
		}
	}

	@Override
	public PaginatedResult<CardStatusRptDTO> getCardDeActRptRecordList(
			CardStatusRptDTO cardDeActSrchCriteria, int pageNo, int pageSize) {
		System.out.println("Entering into the Service");
		List<CardStatusRptDTO> recordList = new ArrayList<CardStatusRptDTO>();
		SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss");
		try {
			if (cardDeActSrchCriteria.getCardCollStartDt() != null) {
				cardDeActSrchCriteria.setCrdCollStartDt(df
						.parse(cardDeActSrchCriteria.getCardCollStartDt()
								+ " 00:00:00"));
			}
			if (cardDeActSrchCriteria.getCardCollEndDt() != null) {
				cardDeActSrchCriteria.setCrdCollEndDt(df
						.parse(cardDeActSrchCriteria.getCardCollEndDt()
								+ " 23:59:59"));
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}
		List<CardStatusRptDTO> result = null;
		try {
			result = vNicTransactionLogDao.getCardDeActRptRecordList(cardDeActSrchCriteria);
					
		} catch (DaoException e1) {
			e1.printStackTrace();
		}
		int total = result.size();
		int maxNoPage = 1;
		if (total > pageSize) {
			maxNoPage = (int) Math.ceil(((double) total / pageSize));
		}
		int firstResults = (pageNo - 1) < 0 ? 0 : (pageNo - 1) * pageSize;
		int maxResults = pageSize;
		if (pageNo == maxNoPage) {
			maxResults = total % pageSize;
			if (maxResults == 0) {
				maxResults = pageSize;
			}
		}
		if (maxResults + firstResults <= total) {
			maxResults = maxResults + firstResults;
		}
		try {
			if (result != null && result.size() > 0) {
				for (int j = firstResults; j <= result.size(); j++) {
					if (j < maxResults) {
						CardStatusRptDTO dto = new CardStatusRptDTO();
						firstResults = firstResults + 1;
						dto.setSerialNo(Integer.toString(firstResults));
						dto.setTransactionNo(result.get(j).getSerialNo());
						dto.setNin(result.get(j).getNin());
						dto.setCcnNo(result.get(j).getCcnNo());
						dto.setFirstName(result.get(j).getFirstName());
						dto.setSurName(result.get(j).getSurName());
						dto.setSurNameAtBirth(result.get(j).getSurNameAtBirth());
						dto.setReDeActivationDate(result.get(j)
								.getReDeActivationDate());
						dto.setCardStatus(result.get(j).getCardStatus());
						dto.setRemarks(result.get(j).getRemarks());
						dto.setIssuedBy(result.get(j).getIssuedBy());
						recordList.add(dto);
					}
				}
				PaginatedResult<CardStatusRptDTO> json = new PaginatedResult<CardStatusRptDTO>(
						total, pageNo, recordList);
				return json;
			} else {
				return new PaginatedResult<CardStatusRptDTO>(0, 0,recordList);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return new PaginatedResult<CardStatusRptDTO>(0, 0,recordList);
		}
	}

	@Override
	public List<CardStatusRptDTO> getCardRejectedRptRecordListForPDF(
			CardStatusRptDTO cardRejStatusRptCriteria) {
		System.out.println("Entering into the Service");
		List<CardStatusRptDTO> recordList = new ArrayList<CardStatusRptDTO>();
		SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss");
		try {
			if (cardRejStatusRptCriteria.getCardCollStartDt() != null) {
				cardRejStatusRptCriteria.setCrdCollStartDt(df
						.parse(cardRejStatusRptCriteria.getCardCollStartDt()
								+ " 00:00:00"));
			}
			if (cardRejStatusRptCriteria.getCardCollEndDt() != null) {
				cardRejStatusRptCriteria.setCrdCollEndDt(df
						.parse(cardRejStatusRptCriteria.getCardCollEndDt()
								+ " 23:59:59"));
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}
		List<CardStatusRptDTO> result = null;
		try {
			result = nicIssuanceDataDao
					.getCardRejectedRptRecordList(cardRejStatusRptCriteria);
		} catch (DaoException e1) {
			e1.printStackTrace();
		}
		try {
			if (result != null && result.size() > 0) {
				for (int j = 0; j < result.size(); j++) {
					CardStatusRptDTO record = new CardStatusRptDTO();
					record.setTransactionNo(result.get(j).getTransactionNo());
					record.setNin(result.get(j).getNin());
					record.setCcnNo(result.get(j).getCcnNo());
					record.setCardRejectDt(result.get(j).getCardRejectDt());
					record.setIssuedBy(result.get(j).getIssuedBy());
					record.setPkgSeqNo(result.get(j).getPkgSeqNo());
					record.setPkgId(result.get(j).getPkgId());
					record.setRemarks(result.get(j).getRemarks());
					record.setRejectionReason(result.get(j)
							.getRejectionReason());
					record.setFirstName(result.get(j).getFirstName());
					record.setSurName(result.get(j).getSurName());
					record.setSurNameAtBirth(result.get(j).getSurNameAtBirth());
					recordList.add(record);
				}
			}
			return recordList;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public PaginatedResult<RICTxnRptDto> getRicExprsPrintRptRecordList(
			RICTxnRptDto exprsPrintRptSrchCriteria, int pageNo, int pageSize) {
		List<RICTxnRptDto> recordList = new ArrayList<RICTxnRptDto>();
		SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss");
		SimpleDateFormat df1 = new SimpleDateFormat("dd-MMM-yyyy");
		int serialNum = (pageNo - 1) * pageSize;
		try {
			if (exprsPrintRptSrchCriteria.getStartDate() != null) {
				exprsPrintRptSrchCriteria.setTxnStartDate(df.parse(exprsPrintRptSrchCriteria
						.getStartDate() + " 00:00:00"));
			}
			if (exprsPrintRptSrchCriteria.getEndDate() != null) {
				exprsPrintRptSrchCriteria.setTxnEndDate(df.parse(exprsPrintRptSrchCriteria
						.getEndDate() + " 23:59:59"));
			}
			if (exprsPrintRptSrchCriteria.getEstColStartDt() != null) {
				exprsPrintRptSrchCriteria.setEstColStartDate(df.parse(exprsPrintRptSrchCriteria
						.getEstColStartDt() + " 00:00:00"));
			}
			if (exprsPrintRptSrchCriteria.getEstColEndDt() != null) {
				exprsPrintRptSrchCriteria.setEstColEndDate(df.parse(exprsPrintRptSrchCriteria
						.getEstColEndDt() + " 23:59:59"));
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}
	 
			PaginatedResult<NicRegistrationData> result = registrationDataDao
					.getRicExprsPrintRptRecordList(exprsPrintRptSrchCriteria, pageNo, pageSize); 
			 System.out.println("list in Service >>"+result.getTotal()+"  " +result);
			 PaginatedResult<RICTxnRptDto> json=null;
		try {
			if (result != null) { 
//				for (NicRegistrationData row : result.getRows()) { 
//					RICTxnRptDto record = new RICTxnRptDto();
//					serialNum = serialNum + 1; 
//					record.setSerialNo(Integer.toString(serialNum));
//					record.setNin(row.getNin());
//					record.setTransactionNo(row.getTransactionId());
//					record.setSurName(row.getSurnameFull());
//					record.setFirstName(row.getFirstnameFull()); 
//					record.setSurNameBirth(row.getSurnameBirthFull());
//					record.setContactNo(row.getContactNo()); 
//					if (row.getNicTransaction().getTransactionStatus() != null) {
//						CodeValues txnStatus = codeService
//								.getCodeValueByIdName(
//										ReportConstants.CODE_ID_TRANSACTION_STATUS,
//										row.getNicTransaction()
//												.getTransactionStatus());
//						record.setTxnStatus((txnStatus != null) ? txnStatus
//								.getCodeValueDesc() : row.getNicTransaction()
//								.getTransactionStatus());
//					} 
//					record.setApplicationDate(df1.format(row
//							.getNicTransaction().getDateOfApplication()));
//					record.setEstColStartDt(df1.format(row
//							.getEstCollectionDate()));
//					record.setExpressPrinting("YES"); 
//					record.setExpressDays(getExprsDays(row.getEstCollectionDate(),row
//							.getNicTransaction().getDateOfApplication())); 
//					recordList.add(record);
//				 
//				}
				System.out.println("In Service >>"+recordList.size());
			  json = new PaginatedResult<RICTxnRptDto>(
						result.getTotal(), pageNo, recordList);
				System.out.println("In Service >>"+json.getRows().size());
				 
			} 
		} catch (Exception e) { 
			e.printStackTrace();
			return null;
		}
		return json;
		
	}

	@Override
	public PaginatedResult<ExceptionHandlngRptDto> getExceptionHandlingRptRecordList(
			ExceptionHandlngRptDto excepHandlingRptSrchCriteria, int pageNo, int pageSize) throws Exception {
		List<ExceptionHandlngRptDto> recordList = new ArrayList<ExceptionHandlngRptDto>();
		LogInfoDTO logDTO = null; 
		SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss");
		SimpleDateFormat df1 = new SimpleDateFormat("dd-MMM-yyyy");
		int serialNum = (pageNo - 1) * pageSize;
		try {
			if (excepHandlingRptSrchCriteria.getForwardStartDt() != null) {
				excepHandlingRptSrchCriteria.setFwdStartDt(df.parse(excepHandlingRptSrchCriteria
						.getForwardStartDt() + " 00:00:00"));
			}
			if (excepHandlingRptSrchCriteria.getForwardEndDt() != null) {
				excepHandlingRptSrchCriteria.setFwdEndDt(df.parse(excepHandlingRptSrchCriteria
						.getForwardEndDt() + " 23:59:59"));
			}
			if (excepHandlingRptSrchCriteria.getResolutionStartDt() != null) {
				excepHandlingRptSrchCriteria.setResStartDt(df.parse(excepHandlingRptSrchCriteria
						.getResolutionStartDt() + " 00:00:00"));
			}
			if (excepHandlingRptSrchCriteria.getResolutionEndDt() != null) {
				excepHandlingRptSrchCriteria.setResEndDt(df.parse(excepHandlingRptSrchCriteria
						.getResolutionEndDt() + " 23:59:59"));
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}
	 
			PaginatedResult<NicTransactionLog> result = transactionLogDao
					.getExceptionHandlingRptRecordList(excepHandlingRptSrchCriteria, pageNo, pageSize); 
			 System.out.println("list in Service >>"+result.getTotal()+"  " +result);
			 PaginatedResult<ExceptionHandlngRptDto> json=null;
		try {
			if (result != null) { 
				for (NicTransactionLog row : result.getRows()) { 
					ExceptionHandlngRptDto dto = new ExceptionHandlngRptDto();
					serialNum = serialNum + 1; 
					dto.setSerialNo(String.valueOf(serialNum));
					dto.setTransactionNo(row.getRefId());
//					dto.setNin(dao.findById(row.getRefId()).getNin());
					if (row.getLogInfo() != null) {   
						String logInfo=row.getLogInfo(); 
						try {
							if(logInfo!=null){
								logDTO = (LogInfoDTO) util.unmarshal(logInfo);
								dto.setRemarks(logDTO.getRemark());
								dto.setReason(logDTO.getReason());
							}else{
								dto.setRemarks("");
								dto.setReason("");
							} 
						} catch (Exception e) {
							e.printStackTrace();
						}  
					}   
					if(row.getStartTime()!=null){
						dto.setTransferDate(df1.format(row.getStartTime()));
					}
					if(row.getEndTime()!=null){
						dto.setResolutionDate(df1.format(row.getEndTime()));
					} 
					recordList.add(dto);
				 
				}
				System.out.println("In Service >>"+recordList.size());
			  json = new PaginatedResult<ExceptionHandlngRptDto>(
						result.getTotal(), pageNo, recordList);
				System.out.println("In Service >>"+json.getRows().size()); 
			} 
		} catch (Exception e) { 
			e.printStackTrace();
			return null;
		}
		return json;
		
	}

	@Override
	public PaginatedResult<RICTxnRptDto> getCardDeliveryStatusRptRecordList(
			RICTxnRptDto cardDelStatuSrchCriteria, int pageNo, int pageSize)
			throws Exception {
		
		List<RICTxnRptDto> recordList = new ArrayList<RICTxnRptDto>();
		SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss");
		SimpleDateFormat df1 = new SimpleDateFormat("dd-MMM-yyyy");
		int serialNum = (pageNo - 1) * pageSize;
		try {
			if (cardDelStatuSrchCriteria.getEstColStartDt() != null) {
				cardDelStatuSrchCriteria.setEstColStartDate(df.parse(cardDelStatuSrchCriteria
						.getEstColStartDt() + " 00:00:00"));
			}
			if (cardDelStatuSrchCriteria.getEstColEndDt() != null) {
				cardDelStatuSrchCriteria.setEstColEndDate(df.parse(cardDelStatuSrchCriteria
						.getEstColEndDt() + " 23:59:59"));
			} 
		} catch (ParseException e) {
			e.printStackTrace();
		}
		PaginatedResult<NicRegistrationData> result = registrationDataDao
				.getCardDeliveryStatusRptRecordList(cardDelStatuSrchCriteria, pageNo, pageSize);
		System.out.println("result"+result.getTotal());
		try {
			if (result != null) {
//				for (NicRegistrationData row : result.getRows()) {
//					RICTxnRptDto record = new RICTxnRptDto();
//					serialNum = serialNum + 1;
//					record.setSerialNo(Integer.toString(serialNum));
//					record.setTransactionNo(row.getTransactionId());  
//					record.setTxnDate(df1.format(row.getNicTransaction().getDateOfApplication())); 
//					record.setNin(row.getNin()); 
//					record.setSurName(row.getSurnameFull());
//					record.setSurNameBirth(row.getSurnameBirthFull());
//					record.setFirstName(row.getFirstnameFull()); 
//					record.setEstColStartDt(df1.format(row.getEstCollectionDate()));  
//					CodeValues txnStatus = codeService
//							.getCodeValueByIdName(
//									ReportConstants.CODE_ID_TRANSACTION_STATUS,
//									row.getNicTransaction()
//											.getTransactionStatus());
//					record.setTxnStatus((txnStatus != null) ? txnStatus
//							.getCodeValueDesc() : row.getNicTransaction()
//							.getTransactionStatus());
//					record.setContactNo(row.getContactNo());
//					record.setReadyForColl("NO");
//					recordList.add(record);
//				}
				PaginatedResult<RICTxnRptDto> json = new PaginatedResult<RICTxnRptDto>(
						result.getTotal(), pageNo, recordList);
				return json;

			} else {
				return null;
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace(); 
			return new PaginatedResult<RICTxnRptDto>(0, 0,recordList);
		}

	}

	@Override
	public PaginatedResult<RICTxnRptDto> getRicLostNfoundRptRecordList(
			RICTxnRptDto lostNfoundSrchCriteria, int pageNo, int pageSize)
			throws Exception {	  
		System.out.println("Entering into the Service");
		List<RICTxnRptDto> recordList = new ArrayList<RICTxnRptDto>();
		SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss");
		SimpleDateFormat df1 = new SimpleDateFormat("dd-MMM-yyyy");
		try {
			if (lostNfoundSrchCriteria.getStartDate() != null) {
				lostNfoundSrchCriteria.setTxnStartDate(df.parse(lostNfoundSrchCriteria
						.getStartDate() + " 00:00:00"));
			}
			if (lostNfoundSrchCriteria.getEndDate() != null) {
				lostNfoundSrchCriteria.setTxnEndDate(df.parse(lostNfoundSrchCriteria
						.getEndDate() + " 23:59:59"));
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}
		List<RICTxnRptDto> result = null;
		try {
			result = nicIssuanceDataDao
					.getRicLostNfoundRptRecordList(lostNfoundSrchCriteria);
		} catch (DaoException e1) {
			e1.printStackTrace();
		}
		int total = result.size();
		int maxNoPage = 1;
		if (total > pageSize) {
			maxNoPage = (int) Math.ceil(((double) total / pageSize));
		}
		int firstResults = (pageNo - 1) < 0 ? 0 : (pageNo - 1) * pageSize;
		int maxResults = pageSize;
		if (pageNo == maxNoPage) {
			maxResults = total % pageSize;
			if (maxResults == 0) {
				maxResults = pageSize;
			}
		}
		if (maxResults + firstResults <= total) {
			maxResults = maxResults + firstResults;
		}
		try {
			if (result != null && result.size() > 0) {
				for (int j = firstResults; j <= result.size(); j++) {
					if (j < maxResults) {
						RICTxnRptDto dto = new RICTxnRptDto();
						firstResults = firstResults + 1;
						dto.setSerialNo(Integer.toString(firstResults));
						dto.setApplicationDate(result.get(j).getApplicationDate());
						dto.setTxnType(codeService.getCodeValueByIdName(
								ReportConstants.CODE_ID_TRANSACTION_TYPE,
								result.get(j).getTxnType())
								.getCodeValueDesc()); 
						dto.setTxnSubType(codeService.getCodeValueByIdName(
								ReportConstants.CODE_ID_TRANSACTION_SUBTYPE,
								result.get(j).getTxnSubType())
								.getCodeValueDesc());
						dto.setNin(result.get(j).getNin());
						dto.setTxnStatus(codeService.getCodeValueByIdName(
								ReportConstants.CODE_ID_TRANSACTION_STATUS,
								result.get(j).getTxnStatus())
								.getCodeValueDesc());
						dto.setCardStatus(result.get(j).getCardStatus());  
						NicRegistrationData regData=registrationDataDao.findById(result.get(j).getTransactionNo());
						dto.setSurName(regData
								.getSurnameFull());
						dto.setFirstName(regData.getFirstnameFull());
//						dto.setSurNameBirth(regData.getSurnameBirthFull());
						dto.setContactNo(regData.getContactNo());
						if ("SUSPENDED"
								.equalsIgnoreCase(dto.getCardStatus())) {
							Calendar cal = Calendar.getInstance();
							cal.setTime(result.get(j)
									.getCardStatusChngTime()); 
							dto.setSuspendedDays(getNoOfDays(cal));
						} else {
							dto.setSuspendedDays("");
						}
						recordList.add(dto);
					}
				}
				PaginatedResult<RICTxnRptDto> json = new PaginatedResult<RICTxnRptDto>(
						total, pageNo, recordList);
				return json;
			} else {
				return new PaginatedResult<RICTxnRptDto>(0, 0,recordList);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return new PaginatedResult<RICTxnRptDto>(0, 0,recordList);
		}
	
		
}
 

	public String getExprsDays(Date date1, Date date2) { 
		Calendar cal1 = Calendar.getInstance();
		Calendar cal2 = Calendar.getInstance();
		cal1.setTime(date1);
		cal2.setTime(date2); 
		int numberOfDays = 0;
		while (cal1.before(cal2)) {
			if ((Calendar.SATURDAY != cal1.get(Calendar.DAY_OF_WEEK))
					&& (Calendar.SUNDAY != cal1.get(Calendar.DAY_OF_WEEK))) {
				numberOfDays++;
				cal1.add(Calendar.DATE, 1);
			} else {
				cal1.add(Calendar.DATE, 1);
			}
		}
		return Integer.toString(numberOfDays);
	}
	
	public String getNoOfDays(Calendar cal1) {
		DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
		Calendar cal2 = Calendar.getInstance();
		int numberOfDays = 0;
		while (cal1.before(cal2)) {
			numberOfDays++;
			cal1.add(Calendar.DATE, 1);
		} 
		return Integer.toString(numberOfDays);
	}

}
