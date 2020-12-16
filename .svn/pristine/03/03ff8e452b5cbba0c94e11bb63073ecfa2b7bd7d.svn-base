package com.nec.asia.nic.comp.trans.dao;

import java.util.List;

import com.nec.asia.nic.comp.trans.domain.BaseModelList;
import com.nec.asia.nic.comp.trans.domain.BaseModelSingle;
import com.nec.asia.nic.comp.trans.domain.EppListHandoverDetail;
import com.nec.asia.nic.framework.dao.GenericDao;

public interface EppListHandoverDetailDao extends GenericDao<EppListHandoverDetail, Long>{
	BaseModelList<EppListHandoverDetail> getListPackageByTransactionId(String tranId); 
	BaseModelSingle<Boolean> insertDataTable(EppListHandoverDetail tran);
	BaseModelList<EppListHandoverDetail> getListPackageByPackageId(String packageId, String type);
	BaseModelSingle<EppListHandoverDetail> getListPackageByPackageIdAndTranID(String packageId, String tranID, String type);
	public EppListHandoverDetail getListPackageByPackageIdAndStage(String[] stage, String tranID);
	public BaseModelSingle<EppListHandoverDetail> findTransactionByIdOrType(String transId, String type);
	public BaseModelSingle<Boolean> saveHandoverDetail(EppListHandoverDetail tran);
	public void deleteHandoverDetail(String packageOldId, String transactionId,
			String typeList) throws Exception;
	List<EppListHandoverDetail> findAllByTranIdOrType(String transactionId,
			String typeListC)throws Exception;
}
