package com.nec.asia.nic.comp.trans.dao;

import java.util.List;

import com.nec.asia.nic.comp.trans.domain.BaseModelList;
import com.nec.asia.nic.comp.trans.domain.BaseModelSingle;
import com.nec.asia.nic.comp.trans.domain.BorderGate;
import com.nec.asia.nic.comp.trans.domain.DetailRecieptFee;
import com.nec.asia.nic.comp.trans.domain.EppRecieptManager;
import com.nec.asia.nic.framework.dao.GenericDao;

public interface DetailRecieptFeeDao extends GenericDao<DetailRecieptFee, Long>{
	BaseModelList<DetailRecieptFee> findAllDetailRecieptFee(String recieptNo);
	BaseModelSingle<DetailRecieptFee> findDetailRecieptFee(String recieptNo, String code, String number) throws Exception;
	public BaseModelSingle<Boolean> saveOrUpdateNew(DetailRecieptFee e);
}
