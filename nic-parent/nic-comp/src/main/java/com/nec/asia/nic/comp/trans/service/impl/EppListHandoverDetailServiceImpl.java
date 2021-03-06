package com.nec.asia.nic.comp.trans.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nec.asia.nic.comp.trans.dao.EppListHandoverDetailDao;
import com.nec.asia.nic.comp.trans.domain.BaseModelList;
import com.nec.asia.nic.comp.trans.domain.BaseModelSingle;
import com.nec.asia.nic.comp.trans.domain.EppListHandoverDetail;
import com.nec.asia.nic.comp.trans.service.EppListHandoverDetailService;
import com.nec.asia.nic.comp.ws.log.domain.CreateMessageException;
import com.nec.asia.nic.framework.service.impl.DefaultBusinessServiceImpl;


@Service("eppListHandoverDetailService")
public class EppListHandoverDetailServiceImpl 
	extends DefaultBusinessServiceImpl<EppListHandoverDetail, Long, EppListHandoverDetailDao>
	implements EppListHandoverDetailService{

	@Autowired
	private EppListHandoverDetailDao dao;
	
	@Override
	public BaseModelSingle<Boolean> insertDataTable(EppListHandoverDetail tran) {
		try {
			return this.dao.insertDataTable(tran);
		} catch (Exception e) {
			e.printStackTrace();
			return new BaseModelSingle<Boolean>(false, false,
					CreateMessageException.createMessageException(e)
							+ " - insertDataTable - " + tran.getPackageId()
							+ " - thất bại.");
		}
	}

	@Override
	public BaseModelList<EppListHandoverDetail> getPackageNameByTransactionId(
			String tranId) {
		try {
			return this.dao.getListPackageByTransactionId(tranId);
		} catch (Exception e) {
			e.printStackTrace();
			return new BaseModelList<EppListHandoverDetail>(null, false, CreateMessageException.createMessageException(e) + " - getListPackageByTransactionId - " + tranId + " - thất bại.");
		}
	}

	@Override
	public BaseModelList<EppListHandoverDetail> getListPackageByPackageId(
			String packageId, String type) {
		try {
			BaseModelList<EppListHandoverDetail> listPackage = this.dao.getListPackageByPackageId(packageId, type);
			return listPackage;
		} catch (Exception e) {
			e.printStackTrace();
			return new BaseModelList<EppListHandoverDetail>(null, false,  CreateMessageException.createMessageException(e) + " - getListPackageByPackageId: " + packageId + " - thất bại.");
		}
	}

	@Override
	public BaseModelSingle<EppListHandoverDetail> getListPackageByPackageIdAndTranID(
			String packageId, String tranID, String type) {
		try {
			return this.dao.getListPackageByPackageIdAndTranID(packageId, tranID, type);
		} catch (Throwable e) {
			e.printStackTrace();
			return new BaseModelSingle<EppListHandoverDetail>(null, false,  CreateMessageException.createMessageException(new Exception(e)) + " - getListPackageByPackageIdAndTranID - " + packageId + " - " + tranID + " - thất bại.");	
		}
	}

	@Override
	public EppListHandoverDetail getListPackageByPackageIdAndStage(
			String[] stage, String tranID) {
		try {
			EppListHandoverDetail listPackage = this.dao.getListPackageByPackageIdAndStage(stage, tranID);
			return listPackage;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public BaseModelSingle<EppListHandoverDetail> findTransactionByIdOrType(String transId,
			String type) {
		try {
			return this.dao.findTransactionByIdOrType(transId, type);
		} catch (Exception e) {
			e.printStackTrace();
			return new BaseModelSingle<EppListHandoverDetail>(null, false,
					CreateMessageException
							.createMessageException(new Exception(e))
							+ " - findTransactionByIdOrType - "
							+ transId
							+ " - thất bại.");
		}
	}

	@Override
	public BaseModelSingle<Boolean> saveHandoverDetail(EppListHandoverDetail tran) throws Exception {
		try {
			return this.dao.saveHandoverDetail(tran);
		} catch (Throwable e) {
			e.printStackTrace();
			return new BaseModelSingle<Boolean>(false, false,  CreateMessageException.createMessageException(new Exception(e))+ " - saveHandoverDetail - thất bại.");
		}
	}

	@Override
	public void deleteHandoverDetail(String packageOldId, String transactionId,
			String typeList) throws Exception {
		try {
			this.dao.deleteHandoverDetail(packageOldId, transactionId, typeList);
		} catch (Exception e) {
			throw new Exception(CreateMessageException.createMessageException(e)+ " - deleteHandoverDetail - thất bại.");
		}
		
	}

	@Override
	public List<EppListHandoverDetail> findAllByTranIdOrType(
			String transactionId, String typeListC) throws Exception {
		List<EppListHandoverDetail> list = null;
		try {
			list = this.dao.findAllByTranIdOrType(transactionId, typeListC);
		} catch (Exception e) {
			throw new Exception(CreateMessageException.createMessageException(e)+ " - findAllByTranIdOrType - thất bại.");
		}
		return list;
	}

	

}
