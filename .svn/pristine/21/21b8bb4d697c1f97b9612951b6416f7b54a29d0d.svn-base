package com.nec.asia.nic.comp.trans.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nec.asia.nic.comp.trans.dao.BorderGateDao;
import com.nec.asia.nic.comp.trans.dao.FeeRecieptPaymentDao;
import com.nec.asia.nic.comp.trans.dao.RecieptManagerDao;
import com.nec.asia.nic.comp.trans.domain.BaseModelList;
import com.nec.asia.nic.comp.trans.domain.BaseModelSingle;
import com.nec.asia.nic.comp.trans.domain.BorderGate;
import com.nec.asia.nic.comp.trans.domain.EppRecieptManager;
import com.nec.asia.nic.comp.trans.domain.FeeRecieptPayment;
import com.nec.asia.nic.comp.trans.service.BorderGateService;
import com.nec.asia.nic.comp.trans.service.RecieptManagerService;
import com.nec.asia.nic.comp.ws.log.domain.CreateMessageException;
import com.nec.asia.nic.framework.service.impl.DefaultBusinessServiceImpl;

@Service("recieptManagerService")
public class RecieptManagerServiceImpl extends DefaultBusinessServiceImpl<EppRecieptManager, Long, RecieptManagerDao> 
	implements RecieptManagerService {

	@Autowired
	private RecieptManagerDao dao;
	
	@Override
	public BaseModelList<EppRecieptManager> findAllRecieptManager(String recieptNo) {
		try {
			return dao.findAllEppRecieptManager(recieptNo);
		} catch (Exception e) {
			e.printStackTrace();
			return new BaseModelList<EppRecieptManager>(null, false,  CreateMessageException.createMessageException(e) + " - findAllEppRecieptManager - " + recieptNo + " - thất bại.");
		}
	}
	
	@Override
	public BaseModelSingle<Boolean> saveOrUpdateNew(EppRecieptManager e) throws Exception {
		try {
			return this.dao.saveOrUpdateNew(e);
		} catch (Throwable ex) {
			ex.printStackTrace();
			return new BaseModelSingle<Boolean>(false, false,  CreateMessageException.createMessageException(new Exception(ex)) + " - saveOrUpdateNew: EppRecieptManager: recieptNo = " + e.getRecieptNo() + " - thất bại.");
		}
	}
}
