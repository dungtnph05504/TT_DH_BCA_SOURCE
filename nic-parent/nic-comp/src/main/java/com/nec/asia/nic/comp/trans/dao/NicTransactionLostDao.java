package com.nec.asia.nic.comp.trans.dao;

import java.util.Date;
import java.util.List;

import com.nec.asia.nic.comp.trans.domain.BaseModelSingle;
import com.nec.asia.nic.comp.trans.domain.NicTransactionLost;
import com.nec.asia.nic.framework.dao.GenericDao;

public interface NicTransactionLostDao extends GenericDao<NicTransactionLost, Long>{
	
	public List<NicTransactionLost> findTransactionLost(Date date);
	
	public BaseModelSingle<NicTransactionLost> findTranLostByConditions(String transactionId, String passportNo, String personCode);
	
	public BaseModelSingle<Void> saveOrUpdateLost(NicTransactionLost lost) throws Exception;

	public List<NicTransactionLost> findPassportCancelled(String passportNo,
			String reason, String name, String gender, String dateOfBirth);

	public List<NicTransactionLost> findDocCancelToPrint(String createdBy, Boolean printType,
			String diplomaCode, Date dateOfBirth, Date fromDate, Date toDate,
			String gender, String passportNo, String name) throws Exception;

	public List<NicTransactionLost> findDocRestoreToPrint(String diplomaCode,
			String dateOfBirth, Date fromDate, Date toDate, String gender,
			String passportNo, String name) throws Exception;

	public List<NicTransactionLost> findDocCanceled(String diplomaCode,
			String dateOfBirth, Date fromDate, Date toDate, String gender,
			String passportNo, String name, String reason,
			String nationalityCode)throws Exception;

	public Long saveTranLost(NicTransactionLost nicTranLost)throws Exception;

	public NicTransactionLost findDocCanByPassportNo(String passportNo)throws Exception;

	List<NicTransactionLost> findTranLost(Date fromDate, Date toDate, String reason,
			String officeCode) throws Exception;
}
