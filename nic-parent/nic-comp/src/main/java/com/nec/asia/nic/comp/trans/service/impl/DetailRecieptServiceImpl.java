package com.nec.asia.nic.comp.trans.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nec.asia.nic.comp.trans.dao.BorderGateDao;
import com.nec.asia.nic.comp.trans.dao.DetailRecieptDao;
import com.nec.asia.nic.comp.trans.dao.DetailRecieptFeeDao;
import com.nec.asia.nic.comp.trans.dao.NicTransactionDao;
import com.nec.asia.nic.comp.trans.dao.RecieptManagerDao;
import com.nec.asia.nic.comp.trans.domain.BorderGate;
import com.nec.asia.nic.comp.trans.domain.DetailRecieptFee;
import com.nec.asia.nic.comp.trans.domain.EppDetailReciept;
import com.nec.asia.nic.comp.trans.domain.EppRecieptManager;
import com.nec.asia.nic.comp.trans.domain.NicTransaction;
import com.nec.asia.nic.comp.trans.service.BorderGateService;
import com.nec.asia.nic.comp.trans.service.DetailRecieptFeeService;
import com.nec.asia.nic.comp.trans.service.DetailRecieptService;
import com.nec.asia.nic.comp.trans.service.RecieptManagerService;
import com.nec.asia.nic.framework.service.impl.DefaultBusinessServiceImpl;

@Service("detailRecieptService")
public class DetailRecieptServiceImpl extends DefaultBusinessServiceImpl<EppDetailReciept, Long, DetailRecieptDao>
	implements DetailRecieptService {

	@Autowired
	private DetailRecieptDao dao;
	
	@Override
	public List<EppDetailReciept> findAllDetailReciept(String recieptNo) {
		try {
			return dao.findAllEppDetailReciept(recieptNo);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
		}
		return null;
	}
	
	@Override
	public Boolean saveOrUpdateNew(EppDetailReciept e) throws Exception {
		try {
			dao.saveOrUpdate(e);
			return true;
		} catch (Exception ex) {
			ex.printStackTrace();
			throw new Exception(ex);
		}
	}

	@Override
	public EppDetailReciept findDetailReciept(String recieptNo,
			String transactionId) throws Exception {
		//try {
			return dao.findDetailReciept(recieptNo, transactionId);
		//} catch (Exception e) {
			//e.printStackTrace();
			//System.out.println(e.getMessage());
		//}
		//return null;
	}
}
