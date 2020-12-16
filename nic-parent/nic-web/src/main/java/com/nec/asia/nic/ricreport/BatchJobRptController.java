package com.nec.asia.nic.ricreport;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.jasperreports.JasperReportsUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.WebRequest;

import com.nec.asia.nic.framework.PaginatedResult;
import com.nec.asia.nic.framework.controller.AbstractController;
import com.nec.asia.nic.comp.trans.dto.BatchJobReportResultDTO;
import com.nec.asia.nic.comp.trans.dto.BatchJobReportGenerateCriteria;
/* 
import com.nec.asia.nic.ric.service.CardIssuanceService;
import com.nec.asia.nic.ric.util.BatchJobConstants;
import com.nec.asia.nic.ric.util.ListUtil;
import com.nec.asia.nic.ric.util.RicApplicationContext;
import com.nec.asia.nic.ric.util.RicSessionContext;
import com.nec.asia.nic.utils.Constants;*/


//@Controller
//@RequestMapping(value = "/batchJobReport")
public class BatchJobRptController extends AbstractController {

	/*@Autowired
	private CardIssuanceService cardIssuanceService;

	@Autowired
	RicSessionContext ricSessionContext;*/

	private List<BatchJobReportResultDTO> records;

	@RequestMapping(value = "/showBatchJobRpt")
	public String showBatchJobRpt(WebRequest request, Model model) {
		records = new ArrayList<BatchJobReportResultDTO>();
		model.addAttribute("fnSelected",
				"RIC_RPT_012 : Batch Job Status Report");
		BatchJobReportGenerateCriteria criteria = new BatchJobReportGenerateCriteria();
		String date = new SimpleDateFormat("d-MMM-yyyy").format(
				new java.util.Date()).toString();
		criteria.setBatchJobStartDate(date);
		criteria.setBatchJobEndDate(date);
		//criteria.setSite(RicApplicationContext.getInstance().getSiteCode());
		/*Map<String, String> jobs = new HashMap<String, String>();
		jobs.put(BatchJobConstants.BATCH_JOB_NAME_PARAMETER_DOWNLOAD,
				BatchJobConstants.BATCH_JOB_NAME_PARAMETER_DOWNLOAD);
		jobs.put(BatchJobConstants.BATCH_JOB_NAME_TRANSACTION_DOWNLOAD,
				BatchJobConstants.BATCH_JOB_NAME_TRANSACTION_DOWNLOAD);
		jobs.put(BatchJobConstants.BATCH_JOB_NAME_TRANSACTION_UPLOAD,
				BatchJobConstants.BATCH_JOB_NAME_TRANSACTION_UPLOAD);
		jobs.put(BatchJobConstants.BATCH_JOB_NAME_TRANSACTIONLOG_UPLOAD,
				BatchJobConstants.BATCH_JOB_NAME_TRANSACTIONLOG_UPLOAD);*/
		model.addAttribute("batchJbRprtSrchCriteria", criteria);
		//model.addAttribute("jobIdList", jobs);
		return "show.batchJobRpt";
	}

/*	@ResponseBody
	@RequestMapping(value = "/showTabluarResult")
	public PaginatedResult<BatchJobReportResultDTO> showTabluarResult(
			WebRequest request, Model model,
			BatchJobReportGenerateCriteria batchJbRprtSrchCriteria)
			throws Exception {
		int page = request.getParameter("page") != null ? Integer
				.parseInt(request.getParameter("page")) : 1;
		int pageSize = request.getParameter("rp") != null ? Integer
				.parseInt(request.getParameter("rp"))
				: Constants.PAGE_SIZE_DEFAULT;
		System.out.println("page >>" + page);
		System.out.println("pageSize >>" + pageSize);
		if (ListUtil.isNotNullAndNotEmpty(records)) {
			records.clear();
		}
		records = cardIssuanceService
				.getBatchJobReportRecordList(batchJbRprtSrchCriteria);
		if (ListUtil.isNotNullAndNotEmpty(records)) {
			PaginatedResult<BatchJobReportResultDTO> result = cardIssuanceService
					.formResultForBatchJobReport(records, page, pageSize);
			return result;
		}
		return null;
	}

	@ResponseBody
	@RequestMapping(value = "/showBatchJobPdf")
	public void showBatchJobPdf(WebRequest request, Model model,
			BatchJobReportGenerateCriteria batchJbRprtSrchCriteria,
			HttpServletResponse response) throws Exception {
		Map<String, Object> parameterMap = new HashMap<String, Object>();
		parameterMap.put("userID", ricSessionContext.getLoggedInUser());
		parameterMap.put("RIC_SITE", RicApplicationContext.getInstance()
				.getSiteCode());
		parameterMap.put("startDate", batchJbRprtSrchCriteria.getBatchJobStartDate());
		parameterMap.put("endDate", batchJbRprtSrchCriteria.getBatchJobEndDate());
		parameterMap.put("JobId", batchJbRprtSrchCriteria.getJobId());
		records = cardIssuanceService
				.getBatchJobReportRecordList(batchJbRprtSrchCriteria);

		PaginatedResult<BatchJobReportResultDTO> result = new PaginatedResult<BatchJobReportResultDTO>(
				records.size(), 0, records);
		JRDataSource JRdataSource = new JRBeanCollectionDataSource(
				result.getRows());
		ByteArrayOutputStream os = new ByteArrayOutputStream();
		try {
			InputStream reportStream = this.getClass().getResourceAsStream(
					"/RIC_BatchJob_Report.jrxml");
			JasperReport jasperReport = JasperCompileManager
					.compileReport(reportStream);
			JasperReportsUtils.renderAsPdf(jasperReport, parameterMap,
					JRdataSource, os);
			response.setContentType("application/pdf");
			response.getOutputStream().write(os.toByteArray());
		} catch (JRException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}*/

	public List<BatchJobReportResultDTO> getRecords() {
		return records;
	}

	public void setRecords(List<BatchJobReportResultDTO> records) {
		this.records = records;
	}
	
	

}
