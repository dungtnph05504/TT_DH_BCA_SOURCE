package com.nec.asia.nic.comp.trans.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nec.asia.nic.comp.trans.dao.TempPaymentDetailDao;
import com.nec.asia.nic.comp.trans.domain.TempPaymentDetail;
import com.nec.asia.nic.comp.trans.service.TempPaymentDetailService;
import com.nec.asia.nic.framework.service.impl.DefaultBusinessServiceImpl;


@Service("tempPaymentDetailService")
public class TempPaymentDetailServiceImpl extends DefaultBusinessServiceImpl<TempPaymentDetail, Long, TempPaymentDetailDao> implements TempPaymentDetailService{

	@Autowired
	TempPaymentDetailDao dao;
	
	@Override
	public List<TempPaymentDetail> findTempByTransactionId(String transactionId) {
		// TODO Auto-generated method stub
		return dao.findTempByTransactionId(transactionId);
	}

	@Override
	public void saveOrUpdateTemp(TempPaymentDetail tempPaymentDetail) {
		try {
			TempPaymentDetail temp = dao.findTempByTransactionIdOrType(tempPaymentDetail.getTransactionId(), tempPaymentDetail.getSubtypePayment());
			if(temp == null){
				dao.saveOrUpdate(tempPaymentDetail);				
			}else{
				tempPaymentDetail.setId(temp.getId());
				dao.saveOrUpdate(tempPaymentDetail);
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		
	}

	@Override
	public TempPaymentDetail findTempByTransactionId(String transactionId,
			String type) {
		try {
			return dao.findTempByTransactionIdOrType(transactionId, type);			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}
