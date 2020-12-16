package com.nec.asia.nic.comp.trans.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nec.asia.nic.comp.trans.dao.BorderGateDao;
import com.nec.asia.nic.comp.trans.dao.DetailRecieptFeeDao;
import com.nec.asia.nic.comp.trans.dao.FeeRecieptPaymentDao;
import com.nec.asia.nic.comp.trans.dao.RecieptManagerDao;
import com.nec.asia.nic.comp.trans.domain.BaseModelList;
import com.nec.asia.nic.comp.trans.domain.BaseModelSingle;
import com.nec.asia.nic.comp.trans.domain.BorderGate;
import com.nec.asia.nic.comp.trans.domain.DetailRecieptFee;
import com.nec.asia.nic.comp.trans.domain.EppDetailReciept;
import com.nec.asia.nic.comp.trans.domain.EppRecieptManager;
import com.nec.asia.nic.comp.trans.domain.FeeRecieptPayment;
import com.nec.asia.nic.comp.trans.service.BorderGateService;
import com.nec.asia.nic.comp.trans.service.FeeRecieptPaymentService;
import com.nec.asia.nic.comp.trans.service.RecieptManagerService;
import com.nec.asia.nic.comp.ws.log.domain.CreateMessageException;
import com.nec.asia.nic.framework.service.impl.DefaultBusinessServiceImpl;

@Service("feeRecieptPaymentService")
public class FeeRecieptPaymentServiceImpl extends DefaultBusinessServiceImpl<FeeRecieptPayment, Long, FeeRecieptPaymentDao> implements FeeRecieptPaymentService {

	@Autowired
	private FeeRecieptPaymentDao dao;
	
	@Override
	public BaseModelList<FeeRecieptPayment> findAllFeeRecieptPayment(String recieptNo) {
		try {
			return dao.findAllFeeRecieptPayment(recieptNo);
		} catch (Exception e) {
			e.printStackTrace();
			return new BaseModelList<FeeRecieptPayment>(null, false,  CreateMessageException.createMessageException(e) + " - findAllFeeRecieptPayment - " + recieptNo + " - thất bại.");
		}
	}
	
	@Override
	public BaseModelSingle<Boolean> saveOrUpdateNew(FeeRecieptPayment e) {
		try {
			return this.dao.saveOrUpdateNew(e);
		} catch (Exception ex) {
			ex.printStackTrace();
			return new BaseModelSingle<Boolean>(false, false,  CreateMessageException.createMessageException(ex) + " - saveOrUpdateNew:" + e.getRecieptNo() + " - thất bại.");
		}
	}
	
	@Override
	public BaseModelSingle<Boolean> deleteObject(List<FeeRecieptPayment> list) {
		try {
			return this.dao.deleteObject(list);
		} catch (Exception ex) {
			ex.printStackTrace();
			return new BaseModelSingle<Boolean>(false, false,  CreateMessageException.createMessageException(ex) + " - deleteObject - thất bại.");
		}
	}
}
