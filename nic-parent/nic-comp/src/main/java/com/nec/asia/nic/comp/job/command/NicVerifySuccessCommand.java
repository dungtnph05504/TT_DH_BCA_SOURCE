package com.nec.asia.nic.comp.job.command;

import java.util.Date;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.nec.asia.nic.comp.job.utils.NicCommandUtil;
import com.nec.asia.nic.comp.trans.domain.NicTransaction;
import com.nec.asia.nic.comp.trans.domain.NicUploadJob;
import com.nec.asia.nic.comp.trans.service.NicRegistrationDataService;
import com.nec.asia.nic.comp.trans.service.NicTransactionService;
import com.nec.asia.nic.comp.trans.service.NicUploadJobService;

/**
 * @author johnpaul_millan
 * @Company : NEC ASIA PACIFIC PTE LTD
 * @Since : Jun 06, 2013
 * <p></p>
 */

/* 
 * Modification History:
 * 21 Nov 2013 (jp) - add logic to change transaction status for rejected txns and CAR txns
 * 06 May 2014 (jp) - add validateTransactionStatusUpdate to avoid overwriting transaction status during reprocess
 */

public class NicVerifySuccessCommand extends BaseCommand<NicUploadJob> implements Command<NicUploadJob>{

	//private static final Logger logger = Logger.getLogger(NicVerifySuccessCommand.class);
	private NicTransactionService nicTransactionService;
	private NicRegistrationDataService nicRegistrationDataService;
	
	private NicUploadJobService uploadJobService;
	NicCommandUtil nicCommandUtil = new NicCommandUtil();

	@Override
	public void doSomething(NicUploadJob obj) {
		logger.info(" inside NicVerifySuccessCommand:{}", obj.getTransactionId());
		
		try {
			NicTransaction transObj = new NicTransaction();
			
			//updateStatus(obj.getWorkflowJobId(), "", JOB_STATE);
			
			transObj = nicTransactionService.findById(obj.getTransactionId(), false, false, true, false);//has reg data
			
			//23AUG2013 - JP - update nic completed job to Y
			obj = uploadJobService.findById(obj.getWorkflowJobId());
			obj.setJobCompletedFlag(true);
			logger.debug(" before update job:{}", obj.getTransactionId());
			uploadJobService.saveOrUpdate(obj);
			logger.debug(" after update job:{}", obj.getTransactionId());
			if (transObj!=null && transObj.getNicRegistrationData()!=null) {
				transObj.getNicRegistrationData().setWorkflowJobCompleteFlag(true);
				transObj.setUpdateDatetime(new Date());
				nicRegistrationDataService.saveOrUpdate(transObj.getNicRegistrationData());
			}
			//02SEP2013 - JP - update nic_transaction.transaction_status field
			
//			String completedStatus = null;
//			if (StringUtils.equals(obj.getInvestigationStatus() , "04") || StringUtils.equals(obj.getInvestigationStatus() , "05")){
//				completedStatus = NicTransactionService.TRANSACTION_STATUS_NIC_REJECTED;
//			}else{
//				completedStatus = NicTransactionService.TRANSACTION_STATUS_NIC_TX_COMPLETD;
//			}
//
//			if(completedStatus != null && nicCommandUtil.validateTransactionStatusUpdate(obj.getTransactionId(), transObj.getTransactionStatus(), completedStatus)){
//				transObj.setTransactionStatus(completedStatus);
//				nicTransactionService.saveOrUpdate(transObj);
//			}
		
		} catch (Exception e) {
			logger.error("\nflag EXCEPTION:"+e.getMessage() +"\n");
			e.printStackTrace();
			
			this.setState(GOTO_ERROR_CMD);
			
			//updateStatus(obj.getWorkflowJobId(), STATUS_ERROR, PERSO_REGISTER);
		}
		logger.info(" completed NicVerifySuccessCommand:{}", obj.getTransactionId());
	}

	public void setCommandUtil (NicCommandUtil commandUtil) {
		this.nicCommandUtil = commandUtil;
	}

	public void setNicTransactionService(NicTransactionService nicTransactionService) {
		this.nicTransactionService = nicTransactionService;
	}

	public void setUploadJobService(NicUploadJobService uploadJobService) {
		this.uploadJobService = uploadJobService;
	}

	public void setNicRegistrationDataService(NicRegistrationDataService nicRegistrationDataService) {
		this.nicRegistrationDataService = nicRegistrationDataService;
	}
}
