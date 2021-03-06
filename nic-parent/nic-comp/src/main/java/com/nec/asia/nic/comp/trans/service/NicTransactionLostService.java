package com.nec.asia.nic.comp.trans.service;

import java.util.Date;
import java.util.List;

import com.nec.asia.nic.comp.trans.domain.BaseModelSingle;
import com.nec.asia.nic.comp.trans.domain.NicTransactionLost;

public interface NicTransactionLostService {
	public BaseModelSingle<Boolean> saveOrUpdateLost(NicTransactionLost lost) throws Exception;
	
	public List<NicTransactionLost> findTransactionLost(Date date);
	
	public BaseModelSingle<NicTransactionLost> findTranLostByConditions(String transactionId, String passportNo, String personCode);

	public List<NicTransactionLost> findPassportCancelled(String passportNo,
			String reason, String name, String gender, String dateOfBirth)throws Exception;

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

	int findQuantityTranLost(Date fromDate, Date toDate, String reason,
			String officeCode) throws Exception;
}
