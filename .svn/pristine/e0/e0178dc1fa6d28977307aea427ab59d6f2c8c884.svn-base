package com.nec.asia.nic.comp.trans.service;

import java.util.List;

import com.nec.asia.nic.comp.trans.domain.BaseModelList;
import com.nec.asia.nic.comp.trans.domain.BaseModelSingle;
import com.nec.asia.nic.comp.trans.domain.EppListHandoverDetail;
import com.nec.asia.nic.framework.service.BusinessService;

public interface EppListHandoverDetailService extends BusinessService<EppListHandoverDetail, Long> {
	public BaseModelSingle<Boolean> insertDataTable(EppListHandoverDetail tran);
	public BaseModelSingle<Boolean> saveHandoverDetail(EppListHandoverDetail tran) throws Exception;
	public BaseModelList<EppListHandoverDetail> getPackageNameByTransactionId(String tranId);
	public BaseModelList<EppListHandoverDetail> getListPackageByPackageId(String packageId, String type);
	public BaseModelSingle<EppListHandoverDetail> getListPackageByPackageIdAndTranID(String packageId, String tranID, String type);
	public EppListHandoverDetail getListPackageByPackageIdAndStage(String[] stage, String tranID);
	
	public BaseModelSingle<EppListHandoverDetail> findTransactionByIdOrType(String transId, String type);
	public void deleteHandoverDetail(String packageOldId, String transactionId,
			String typeList) throws Exception;
	public List<EppListHandoverDetail> findAllByTranIdOrType(
			String transactionId, String typeListC) throws Exception;
}