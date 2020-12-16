package com.nec.asia.nic.framework.admin.code.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nec.asia.nic.framework.PaginatedResult;
import com.nec.asia.nic.framework.admin.code.dao.ParametersDao;
import com.nec.asia.nic.framework.admin.code.dao.PaymentDefDao;
import com.nec.asia.nic.framework.admin.code.domain.Parameters;
import com.nec.asia.nic.framework.admin.code.domain.ParametersId;
import com.nec.asia.nic.framework.admin.code.domain.PaymentDef;
import com.nec.asia.nic.framework.admin.code.domain.PaymentDefId;
import com.nec.asia.nic.framework.admin.code.domain.type.ParameterType;
import com.nec.asia.nic.framework.admin.code.dto.PaymentDefDTO;
import com.nec.asia.nic.framework.admin.code.service.PaymentDefService;
import com.nec.asia.nic.framework.service.impl.DefaultBusinessServiceImpl;
import com.nec.asia.nic.utils.BaseDTOMapper;

/**
 * 
 * @author chris_wong
 * 
 */
/* 
 * Modification History:
 *  
 * 18 Aug 2013 (chris): add method to retrieve payment def for replacement transaction.
 * 26 Sep 2013 (chris): add BASIC_FEE_FOR_SC for senior citizen fix rate.
 * 06 Dec 2013 (chris): change reduce amount=TOTAL-BASIC_FEE_FOR_SC instead of return BASIC_FEE_FOR_SC 
 * 19 Aug 2014 (chris): to fix reduceAmount = 0 if ReduceRateFlag is false.
 */

@Service("paymentDefService")
public class PaymentDefServiceImpl
		extends
		DefaultBusinessServiceImpl<PaymentDef, PaymentDefId, PaymentDefDao>
		implements PaymentDefService {
	@Autowired
	private ParametersDao parametersDao;
	
	/** must define the non-argument constructor because we use CGLib proxy */
	public PaymentDefServiceImpl() {
	}

	@Autowired
	public PaymentDefServiceImpl(PaymentDefDao dao) {
		this.dao = dao;
	}
	
	public PaymentDef getPaymentDefForReplacement() {
		PaymentDef paymentDef = new PaymentDef();
		Parameters parameter = parametersDao.findById(new ParametersId(SCOPE_PAYMENT, PARA_BASIC_FEE));
		if (parameter != null && ParameterType.Integer.equals(parameter.getParaType()) && !Boolean.TRUE.equals(parameter.getDeleteFlag())) {
			int basicFee = (Integer) parameter.getParaValue();
			paymentDef.setId(new PaymentDefId(CODE_REPLACEMENT, "", 1));
			paymentDef.setFeeAmount(new Double(basicFee));
			paymentDef.setWaivableFlag(Boolean.TRUE);
			paymentDef.setReduceRateFlag(Boolean.TRUE);
		}
		return paymentDef;
	}

	public List<PaymentDef> findAllByTransType(String transactionType, String transactionSubtype) {
		return dao.findPaymentDefByTransType(transactionType, transactionSubtype);
	}
	
	public double calculateReduceRateFeeAmount(PaymentDef paymentDef) {
		double reduceRateFeeAmount = 0;
		if (Boolean.TRUE.equals(paymentDef.getReduceRateFlag())) {
			if (paymentDef.getFeeAmount()!=null) {
				double feeAmount = paymentDef.getFeeAmount().doubleValue();
				//26 Sep 2013 (chris) - start
				Parameters parameterFeeForSC = parametersDao.findById(new ParametersId(SCOPE_PAYMENT, PARA_BASIC_FEE_FOR_SC));
				if (parameterFeeForSC != null) {
					ParameterType paraType = parameterFeeForSC.getParaType();
					if (ParameterType.Integer.equals(paraType)) {
						int feeAmountForSC = (Integer) parameterFeeForSC.getParaValue();
						//06 Dec 2013 (chris)
						//reduceRateFeeAmount = feeAmountForSC;
						reduceRateFeeAmount = feeAmount - feeAmountForSC;
						logger.info("TxnType[{}], SubType[{}], NoOfCardLost[{}] Fee[{}] ReducableFee[{}] ", new Object[] {paymentDef.getId().getTransactionType(), paymentDef.getId().getTransactionSubtype(), paymentDef.getId().getNoOfTimeLost(), feeAmount, reduceRateFeeAmount});
					} else {
						logger.warn("Invalid Para Type [{}] unable to calculate the reduce rate.", paraType);
					}
				} else {
					//should be remove if customer don't want this discount feature.
					
					Parameters parameter = parametersDao.findById(new ParametersId(SCOPE_PAYMENT, PARA_REDUCE_AMOUNT));
					if (parameter != null) {
						//Object paraValue = parameter.getParaValue();
						//String paraValue = parameter.getParaShortValue();
						ParameterType paraType = parameter.getParaType();
						if (ParameterType.Percentage.equals(paraType)) {
							double reducePercent = (Double) parameter.getParaValue();
							reduceRateFeeAmount = (1 - reducePercent / 100) * feeAmount;
						} else if (ParameterType.Integer.equals(paraType)) {
							int reduceAmount = (Integer) parameter.getParaValue();
							reduceRateFeeAmount = feeAmount - reduceAmount;
						} else {
							logger.warn("Invalid Para Type [{}] unable to calculate the reduce rate.", paraType);
						}
					}
				}
				//26 Sep 2013 (chris) - end
			}
		} else {
			//19 Aug 2014 (chris) - start
			//if (paymentDef.getFeeAmount()!=null)
			//	reduceRateFeeAmount = paymentDef.getFeeAmount().doubleValue();
			reduceRateFeeAmount = 0;
			logger.info("TxnType[{}], SubType[{}], NoOfCardLost[{}] ReduceRateFlag[{}] ReducableFee[{}] ", new Object[] {paymentDef.getId().getTransactionType(), paymentDef.getId().getTransactionSubtype(), paymentDef.getId().getNoOfTimeLost(), paymentDef.getReduceRateFlag(), reduceRateFeeAmount});
			//19 Aug 2014 (chris) - end
		}
		if (reduceRateFeeAmount<0)
			reduceRateFeeAmount = 0;
		return reduceRateFeeAmount;
	}

	
	@Override
	public PaginatedResult<PaymentDefDTO> findAllPaymentMatrixList(int pageNo,
			int pageSize) throws Exception {
		List<PaymentDefDTO> joblstDto = new ArrayList<PaymentDefDTO>();
		PaginatedResult<PaymentDef>  pr=null;
		PaginatedResult<PaymentDefDTO>  pageResult=new PaginatedResult<PaymentDefDTO>();
		try{
			PaymentDef paymentDef = new PaymentDef();
			pr = dao.findAllForPagination(paymentDef, pageNo, pageSize);
			if(pr!=null && CollectionUtils.isNotEmpty(pr.getRows())){
				List<PaymentDef> jobList = pr.getRows();
				for(PaymentDef record : jobList){
					logger.info("Transaction Type=============>>>>>>>>>>>>>>>>>>>"+record.getId().getTransactionType());
					/*PaymentDefDTO dto=new PaymentDefDTO();
					dto.setTransactionType(record.getId().getTransactionType());
					dto.setTransactionSubtype(record.getId().getTransactionSubtype());
					joblstDto.add(dto);*/
					PaymentDefDTO dto=new PaymentDefDTO();
					dto.setTransactionType(record.getId().getTransactionType());
					dto.setTransactionSubtype(record.getId().getTransactionSubtype());
					joblstDto.add(dto);
					BaseDTOMapper.copyProperties(dto, record);
				}
				pageResult.setRows(joblstDto);
				pageResult.setTotal((int) pr.getTotal());
				pageResult.setPage(pr.getPage());
			}
		}catch(Exception ex){
			ex.printStackTrace();
			logger.error("Error occurred while getting the Payment Matrix list. Exception: "+ex.getMessage());
		}
		return pageResult;
	}

	
	@Override
	public boolean deletePaymentMatrix(PaymentDef payment, String userId, String workstationId) {
		boolean success = false;
		try {
			PaymentDef paymentDBO = dao.findById(payment.getId());
			
			paymentDBO.setDeleteBy(userId);
			paymentDBO.setDeleteDateTime(new Date());
			paymentDBO.setDeleteWkstnId(workstationId);
			paymentDBO.setDeleteFlag(Boolean.TRUE);
			
			dao.deletePaymentMatrix(paymentDBO);
			success = true;
		} catch (Exception e) {
			logger.error("Exception when delete payment def", e);
		}
		return success;
	}

	@Override
	public List<PaymentDef> findPaymentDefByTransType(String transactionType,
			String transactionSubtype, String stylePay) {
		try {
			return dao.findPaymentDefByTransType(transactionType, transactionSubtype, stylePay);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Exception payment def", e.getMessage());
		}
		return new ArrayList<PaymentDef>();
	}

	@Override
	public PaymentDef findOnlyPaymentDefByTransType(String transactionType,
			String transactionSubtype) {
		try {
			return dao.findOnlyPaymentDefByTransType(transactionType, transactionSubtype);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Exception payment def", e.getMessage());
		}
		return new PaymentDef();
	}
}