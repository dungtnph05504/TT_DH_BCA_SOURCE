package com.nec.asia.nic.comp.bufPersonInvestigation.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nec.asia.nic.comp.bufPersonInvestigation.dao.BufPersonInvestigationDao;
import com.nec.asia.nic.comp.bufPersonInvestigation.service.BufPersonInvestigationService;
import com.nec.asia.nic.comp.trans.domain.BufEppPersonInvestigation;
import com.nec.asia.nic.comp.ws.log.domain.CreateMessageException;
import com.nec.asia.nic.framework.admin.code.dao.ParametersDao;
import com.nec.asia.nic.framework.service.impl.DefaultBusinessServiceImpl;

/**
 * 
 * @author chris_wong
 * 
 */
/*
 * Modification History:
 * 
 * 18 Aug 2013 (chris): add method to retrieve payment def for replacement
 * transaction. 26 Sep 2013 (chris): add BASIC_FEE_FOR_SC for senior citizen fix
 * rate. 06 Dec 2013 (chris): change reduce amount=TOTAL-BASIC_FEE_FOR_SC
 * instead of return BASIC_FEE_FOR_SC 19 Aug 2014 (chris): to fix reduceAmount =
 * 0 if ReduceRateFlag is false.
 */

@Service("bufPersonInvestigationService")
public class BufPersonInvestigationServiceImpl
		extends
		DefaultBusinessServiceImpl<BufEppPersonInvestigation, Long, BufPersonInvestigationDao>
		implements BufPersonInvestigationService {
	@Autowired
	private ParametersDao parametersDao;

	/** must define the non-argument constructor because we use CGLib proxy */
	public BufPersonInvestigationServiceImpl() {
	}

	@Autowired
	public BufPersonInvestigationServiceImpl(BufPersonInvestigationDao dao) {
		this.dao = dao;
	}

	public BufEppPersonInvestigation findByTranId(String tranId)
			throws Exception {
		try {
			return dao.findByTranId(tranId);
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}

	public List<BufEppPersonInvestigation> findListByTranId(String tranId)
			throws Exception {
		try {
			return dao.findListByTranId(tranId);
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}

	@Override
	public Boolean updateAndCreate(BufEppPersonInvestigation obj) {
		try {
			dao.saveOrUpdate(obj);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public List<BufEppPersonInvestigation> findListByTranMasterId(String tranId)
			throws Exception {
		try {
			return dao.findListByTranMasterId(tranId);
		} catch (Exception e) {
			throw new Exception(
					CreateMessageException.createMessageException(e)
							+ " - findListByTranMasterId thất bại.");
		}
	}

	@Override
	public BufEppPersonInvestigation findBufByTranMasterIdAndPersonCode(
			String tranMasterId, String personCode, String tranId,
			String maCaNhan) throws Exception {
		BufEppPersonInvestigation bufPerson = null;
		try {
			List<BufEppPersonInvestigation> listBufPerson = this.dao
					.findListByTranMasterIdAndPersonCode(tranMasterId,
							personCode, tranId, maCaNhan);
			if (listBufPerson != null && listBufPerson.size() > 0) {
				bufPerson = listBufPerson.get(0);
			}
		} catch (Exception e) {
			throw new Exception(
					CreateMessageException.createMessageException(e)
							+ " - findBufByTranMasterIdAndPersonCode thất bại.");
		}
		return bufPerson;
	}

	@Override
	public Boolean deleteBufPersonByTranMasterId(String tranMasterId)
			throws Exception {
		Boolean check = false;
		try {
			check = this.dao.deleteBufPersonByTranMasterId(tranMasterId);
		} catch (Exception e) {
			throw new Exception(
					CreateMessageException.createMessageException(e)
							+ " - deleteBufPersonByTranMasterId - " + tranMasterId + " - thất bại.");
		}
		return check;
	}
}