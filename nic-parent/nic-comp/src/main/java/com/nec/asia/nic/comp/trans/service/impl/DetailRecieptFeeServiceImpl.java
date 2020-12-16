package com.nec.asia.nic.comp.trans.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nec.asia.nic.comp.trans.dao.BorderGateDao;
import com.nec.asia.nic.comp.trans.dao.DetailRecieptFeeDao;
import com.nec.asia.nic.comp.trans.dao.NicTransactionDao;
import com.nec.asia.nic.comp.trans.dao.RecieptManagerDao;
import com.nec.asia.nic.comp.trans.domain.BaseModelList;
import com.nec.asia.nic.comp.trans.domain.BaseModelSingle;
import com.nec.asia.nic.comp.trans.domain.BorderGate;
import com.nec.asia.nic.comp.trans.domain.DetailRecieptFee;
import com.nec.asia.nic.comp.trans.domain.EppRecieptManager;
import com.nec.asia.nic.comp.trans.domain.NicTransaction;
import com.nec.asia.nic.comp.trans.service.BorderGateService;
import com.nec.asia.nic.comp.trans.service.DetailRecieptFeeService;
import com.nec.asia.nic.comp.trans.service.RecieptManagerService;
import com.nec.asia.nic.comp.ws.log.domain.CreateMessageException;
import com.nec.asia.nic.framework.service.impl.DefaultBusinessServiceImpl;

@Service("detailRecieptFeeService")
public class DetailRecieptFeeServiceImpl extends DefaultBusinessServiceImpl<DetailRecieptFee, Long, DetailRecieptFeeDao>
	implements DetailRecieptFeeService {

	@Autowired
	private DetailRecieptFeeDao dao;
	
	@Override
	public BaseModelList<DetailRecieptFee> findAllDetailRecieptFee(String recieptNo) {
		try {
			return dao.findAllDetailRecieptFee(recieptNo);
		} catch (Exception e) {
			e.printStackTrace();
			return new BaseModelList<DetailRecieptFee>(null, false,  CreateMessageException.createMessageException(e) + " - findAllDetailRecieptFee - " + recieptNo + " - thất bại.");
		}
	}
	
	@Override
	public BaseModelSingle<Boolean> saveOrUpdateNew(DetailRecieptFee e) throws Exception {
		try {
			return this.dao.saveOrUpdateNew(e);
		} catch (Throwable ex) {
			ex.printStackTrace();
			return new BaseModelSingle<Boolean>(false, false,  CreateMessageException.createMessageException(new Exception(ex)) + " - saveOrUpdateNew: " + e.getNumberBill() + " - thất bại.");
		}
	}

	@Override
	public BaseModelSingle<DetailRecieptFee> findDetailRecieptFee(String recieptNo, String code,
			String number) throws Exception {
		try {
			return dao.findDetailRecieptFee(recieptNo, code, number);			
		} catch (Exception e) {
			return new BaseModelSingle<DetailRecieptFee>(null, false, CreateMessageException.createMessageException(e) + " - findDetailRecieptFee - " + recieptNo + " - thất bại.");
		}
	}
}
