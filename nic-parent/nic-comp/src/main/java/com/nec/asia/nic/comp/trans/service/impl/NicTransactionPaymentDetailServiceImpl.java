package com.nec.asia.nic.comp.trans.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.nec.asia.nic.comp.trans.dao.NicTransactionPaymentDetailDao;
import com.nec.asia.nic.comp.trans.domain.BaseModelList;
import com.nec.asia.nic.comp.trans.domain.BaseModelSingle;
import com.nec.asia.nic.comp.trans.domain.NicTransactionPaymentDetail;
import com.nec.asia.nic.comp.trans.service.NicTransactionPaymentDetaiService;
import com.nec.asia.nic.comp.ws.log.domain.CreateMessageException;

@Service("nicTransactionPaymentDetailService")
public class NicTransactionPaymentDetailServiceImpl implements NicTransactionPaymentDetaiService{

	@Autowired
	private NicTransactionPaymentDetailDao dao;
	
	@Override
	public List<NicTransactionPaymentDetail> findListDetailPaymentByTransactionId(
			String paymentID, String type, String subType, Boolean status) {
		try {
			List<NicTransactionPaymentDetail> list = dao.findListDetailPaymentByTransactionId(paymentID, type, subType, status).getListModel();
			return list;
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ArrayList<NicTransactionPaymentDetail>();
	}

	@Override
	@Transactional(rollbackFor = java.lang.Throwable.class, propagation = Propagation.REQUIRED)
	public BaseModelSingle<Boolean> saveOrUpdatePaymentDetail(NicTransactionPaymentDetail detail) throws Exception {
		try {
			return this.dao.saveOrUpdatePaymentDetail(detail);
		} catch (Throwable e) {
			e.printStackTrace();
			return new BaseModelSingle<Boolean>(false, false,  CreateMessageException.createMessageException(new Exception(e)) + " - saveOrUpdatePaymentDetail - thất bại.");
		}

	}

	@Override
	public BaseModelList<NicTransactionPaymentDetail> findListDetailPaymentList(String paymentID) {
		try {
			return this.dao.findListDetailPaymentList(paymentID);
		} catch (Exception e) {
			e.printStackTrace();
			return new BaseModelList<NicTransactionPaymentDetail>(new ArrayList<NicTransactionPaymentDetail>(), false,  CreateMessageException.createMessageException(e) + " - findListDetailPaymentList - " + paymentID + " - thất bại.");
		}
	}

	@Override
	public BaseModelList<NicTransactionPaymentDetail> findListDetailPaymentList(
			String paymentID, Boolean stage) {
		try {
			return dao.findListDetailPaymentList(paymentID, stage);
		} catch (Exception e) {
			e.printStackTrace();
			return new BaseModelList<NicTransactionPaymentDetail>(null, false,  CreateMessageException.createMessageException(e) + " - findListDetailPaymentList - " + paymentID + " - thất bại.");
		}
	}

	@Override
	public BaseModelSingle<NicTransactionPaymentDetail> findDetailPaymentByTransactionId(
			String paymentID, String type, String subType) {
		try {
			BaseModelList<NicTransactionPaymentDetail> list = dao.findListDetailPaymentByTransactionId(paymentID, type, subType, null);
			if(list.getListModel() != null && list.getListModel().size() > 0)
				return new BaseModelSingle<NicTransactionPaymentDetail>(list.getListModel().get(0), true, null);
			
		} catch (Exception e) {
			e.printStackTrace();
			return new BaseModelSingle<NicTransactionPaymentDetail>(null, false,  CreateMessageException.createMessageException(e) + " - findListDetailPaymentByTransactionId - " + paymentID + " - thất bại.");
		}
		return new BaseModelSingle<NicTransactionPaymentDetail>(null, true, null);
	}
}
