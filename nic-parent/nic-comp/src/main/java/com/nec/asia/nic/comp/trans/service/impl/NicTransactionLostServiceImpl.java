package com.nec.asia.nic.comp.trans.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nec.asia.nic.comp.trans.dao.NicTransactionLostDao;
import com.nec.asia.nic.comp.trans.domain.BaseModelSingle;
import com.nec.asia.nic.comp.trans.domain.NicTransactionLost;
import com.nec.asia.nic.comp.trans.service.NicTransactionLostService;
import com.nec.asia.nic.comp.ws.log.domain.CreateMessageException;

@Service
public class NicTransactionLostServiceImpl implements NicTransactionLostService {

	@Autowired
	private NicTransactionLostDao dao;

	@Override
	public BaseModelSingle<Boolean> saveOrUpdateLost(NicTransactionLost lost)
			throws Exception {
		try {
			BaseModelSingle<Void> baseSave = this.dao.saveOrUpdateLost(lost);
			return new BaseModelSingle<Boolean>(true, baseSave.isError(),
					baseSave.getMessage());
		} catch (Throwable e) {
			e.printStackTrace();
			return new BaseModelSingle<Boolean>(false, false,
					CreateMessageException
							.createMessageException(new Exception(e))
							+ " - saveOrUpdateLost - thất bại.");
		}
	}

	@Override
	public List<NicTransactionLost> findTransactionLost(Date date) {
		try {
			return dao.findTransactionLost(date);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public BaseModelSingle<NicTransactionLost> findTranLostByConditions(
			String transactionId, String passportNo, String personCode) {
		try {
			return this.dao.findTranLostByConditions(transactionId, passportNo,
					personCode);
		} catch (Exception e) {
			return new BaseModelSingle<NicTransactionLost>(null, false,
					CreateMessageException.createMessageException(e)
							+ " - findTranLostByConditions - thất bại.");
		}
	}

	@Override
	public List<NicTransactionLost> findPassportCancelled(String passportNo,
			String reason, String name, String gender, String dateOfBirth)
			throws Exception {
		List<NicTransactionLost> list = null;
		try {
			list = this.dao.findPassportCancelled(passportNo, reason, name,
					gender, dateOfBirth);
		} catch (Exception e) {
			throw new Exception(
					CreateMessageException.createMessageException(e)
							+ " - findPassportCancelled - thất bại.");
		}
		return list;
	}

	@Override
	public List<NicTransactionLost> findDocCancelToPrint(String createdBy, Boolean printType,
			String diplomaCode, Date dateOfBirth, Date fromDate, Date toDate,
			String gender, String passportNo, String name) throws Exception {
		List<NicTransactionLost> list = null;
		try {
			list = this.dao.findDocCancelToPrint(createdBy, printType, diplomaCode,
					dateOfBirth, fromDate, toDate, gender, passportNo, name);
		} catch (Exception e) {
			throw new Exception(
					CreateMessageException.createMessageException(e)
							+ " - findDocCancelToPrint - thất bại.");
		}
		return list;
	}

	@Override
	public List<NicTransactionLost> findDocRestoreToPrint(String diplomaCode,
			String dateOfBirth, Date fromDate, Date toDate, String gender,
			String passportNo, String name) throws Exception {
		List<NicTransactionLost> list = null;
		try {
			list = this.dao.findDocRestoreToPrint(diplomaCode, dateOfBirth,
					fromDate, toDate, gender, passportNo, name);
		} catch (Exception e) {
			throw new Exception(
					CreateMessageException.createMessageException(e)
							+ " - findDocRestoreToPrint - thất bại.");
		}
		return list;
	}

	@Override
	public List<NicTransactionLost> findDocCanceled(String diplomaCode,
			String dateOfBirth, Date fromDate, Date toDate, String gender,
			String passportNo, String name, String reason,
			String nationalityCode) throws Exception {
		List<NicTransactionLost> list = null;
		try {
			list = this.dao.findDocCanceled(diplomaCode, dateOfBirth, fromDate,
					toDate, gender, passportNo, name, reason, nationalityCode);
		} catch (Exception e) {
			throw new Exception(
					CreateMessageException.createMessageException(e)
							+ " - findDocCanceled - thất bại.");
		}
		return list;
	}

	@Override
	public Long saveTranLost(NicTransactionLost nicTranLost) throws Exception {
		try {
			return  this.dao.saveTranLost(nicTranLost);
		} catch (Throwable e) {
			e.printStackTrace();
			throw new Exception(
					CreateMessageException
							.createMessageException(new Exception(e))
							+ " - saveTranLost - thất bại.");
		}
	}

	@Override
	public int findQuantityTranLost(Date fromDate, Date toDate, String reason, String officeCode)
			throws Exception {
		try {
			List<NicTransactionLost> list = null;
			if (fromDate != null && toDate != null ) {
				list = this.dao.findTranLost(fromDate, toDate, reason, officeCode);
			}
			return list.size();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}

	
}
