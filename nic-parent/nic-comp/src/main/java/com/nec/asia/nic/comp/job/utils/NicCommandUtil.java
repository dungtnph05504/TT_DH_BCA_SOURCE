package com.nec.asia.nic.comp.job.utils;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.ObjectUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.nec.asia.nic.comp.job.command.exception.NicCommandException;
import com.nec.asia.nic.comp.job.dto.CpdReferenceDataDTO;
import com.nec.asia.nic.comp.trans.domain.NicRegistrationData;
import com.nec.asia.nic.comp.trans.domain.NicUploadJob;
import com.nec.asia.nic.comp.trans.domain.type.TransactionStatus;
import com.nec.asia.nic.comp.trans.service.NicUploadJobService;
import com.nec.asia.nic.framework.admin.code.domain.Parameters;
import com.nec.asia.nic.framework.admin.code.domain.ParametersId;
import com.nec.asia.nic.framework.admin.code.service.ParametersService;

/* 
 * Modification History:
 * 6 May 2014 (jp) - add validateTransactionStatusUpdate to avoid overwriting transaction status during reprocess
 * 18 Jan 2016 (cw) - add annotation @Component @Autowired
 */
@Component("commandUtil")
public class NicCommandUtil {
	private static final Logger logger = Logger.getLogger(NicCommandUtil.class);

	@Autowired
	private ParametersService parametersService; // "parametersService"

	public static final String PARA_SCOPE_SYSTEM = Parameters.SCOPE_SYSTEM;
	public static final String PARA_NAME_SYSTEM_SITE_CODE = "SYSTEM_SITE_CODE";
	public static final String PARA_NAME_CURRENT_SYSTEM_ID = "CURRENT_SYSTEM_ID";

	public static final String NIC_SYSTEM = "SYSTEM";

	private String systemName = null;
	private String systemSiteCode = null;

	public String getSystemName() {

		try {
			if (StringUtils.isBlank(systemName)) {
				logger.info("parametersService:" + parametersService);

				Parameters parameter = parametersService
						.findById(new ParametersId(PARA_SCOPE_SYSTEM,
								PARA_NAME_CURRENT_SYSTEM_ID));
				if (parameter != null) {
					systemName = (String) parameter.getParaValue();
					logger.info("Cached systemName: " + systemName);
				} else {
					logger.info("No matching Parameter for "
							+ PARA_SCOPE_SYSTEM + " , "
							+ PARA_NAME_CURRENT_SYSTEM_ID);
					systemName = NIC_SYSTEM;
				}
			}
		} catch (Exception e) {
			logger.info("No matching Parameter for " + PARA_SCOPE_SYSTEM
					+ " , " + PARA_NAME_CURRENT_SYSTEM_ID);
			systemName = NIC_SYSTEM;
		}
		return systemName;
	}

	public String getHostName() {

		String hostname = "";

		try {
			hostname = InetAddress.getLocalHost().getHostName().toUpperCase();
		} catch (UnknownHostException e) {
			hostname = "";
		}

		return hostname;
	}

	public CpdReferenceDataDTO parseRegDataToCpdDto(NicRegistrationData regData) {

		CpdReferenceDataDTO dto = null;

		try {

			if (regData == null)
				throw new NicCommandException("Registration Data is null");

			dto = new CpdReferenceDataDTO();

			dto.setFirstName(regData.getFirstnameFull());
			dto.setSurname(regData.getSurnameFull());

			dto.setSex(regData.getGender());
			// dto.setNin(regData.getNin());
			dto.setMaritalStatus(regData.getMaritalStatus());
			dto.setBirthDate(regData.getDateOfBirth());
			//
			dto.setBirthDateApprox(regData.getApproxDob());

			dto.setMotherFullname(regData.getMotherFullname());
			// dto.setMotherNin(regData.getMotherNin());

			dto.setFatherFullname(regData.getFatherFullname());
			// dto.setFatherNin(regData.getFatherNin());

		} catch (NicCommandException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return dto;

	}

	public String getSystemSiteCodeFromParameter() {
		try {
			if (StringUtils.isBlank(systemSiteCode)) {
				logger.info("parametersService:" + parametersService);

				Parameters parameter = parametersService
						.findById(new ParametersId(PARA_SCOPE_SYSTEM,
								PARA_NAME_SYSTEM_SITE_CODE));
				if (parameter != null) {
					systemSiteCode = (String) parameter.getParaValue();
					logger.info("Cached systemSiteCode: " + systemSiteCode);
				} else {
					logger.info("No matching Parameter for "
							+ PARA_SCOPE_SYSTEM + " , "
							+ PARA_NAME_SYSTEM_SITE_CODE);
					systemSiteCode = "001";
				}
			}
		} catch (Exception e) {
			logger.info("No matching Parameter for " + PARA_SCOPE_SYSTEM
					+ " , " + PARA_NAME_SYSTEM_SITE_CODE);
			systemSiteCode = "001";
		}
		return systemSiteCode;
	}

	public void setErrorFlag(Long jobId, boolean flag,
			NicUploadJobService uploadJobService) {

		try {

			if (ObjectUtils.equals(jobId, null))
				throw new NicCommandException("job id should not be null");

			if (uploadJobService == null)
				throw new NicCommandException("upload job svc cannot be found");

			NicUploadJob job = uploadJobService.findById(jobId);

			if (job == null) {
				throw new NicCommandException("job id " + jobId
						+ " cannot be found");
			} else {
				//job.setJobErrorFlag(flag);
				job.setJobEndTime(new Date());
				uploadJobService.saveOrUpdate(job);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public boolean validateTransactionStatusUpdate(String transactionId,
			String oldStatus, String newStatus) {
		boolean updateReq = true;

		TransactionStatus oldTransactionStatus = TransactionStatus
				.getInstance(oldStatus);
		TransactionStatus newTransactionStatus = TransactionStatus
				.getInstance(newStatus);
		Integer oldStatusId = oldTransactionStatus != null ? oldTransactionStatus
				.getOrder() : -1;
		Integer newStatusId = newTransactionStatus != null ? newTransactionStatus
				.getOrder() : 99;
		if (newStatusId != null && oldStatusId != null
				&& newStatusId.compareTo(oldStatusId) < 0) {
			updateReq = false;
		}
		logger.info("transactionid[" + transactionId + "],oldStatus["
				+ oldStatus + "],newStatus[" + newStatus + "],RESULT["
				+ updateReq + "]");
		return updateReq;
	}

	public void setParametersService(ParametersService parametersService) {
		this.parametersService = parametersService;
	}

}
