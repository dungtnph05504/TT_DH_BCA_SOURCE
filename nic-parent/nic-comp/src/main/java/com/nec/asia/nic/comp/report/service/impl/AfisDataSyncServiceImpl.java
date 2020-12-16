package com.nec.asia.nic.comp.report.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.nec.asia.nic.comp.report.dao.AfisDataSyncDao;
import com.nec.asia.nic.comp.report.service.AfisDataSyncService;



@Service("afisDataSyncService")
@Transactional
public class AfisDataSyncServiceImpl implements AfisDataSyncService {

	@Autowired
	private AfisDataSyncDao afisDataSyncDao = null;

	@Override
	public List<Object[]> getSynchronizionDetails(String selectedMonth)	throws Exception {
		return afisDataSyncDao.getSynchronizionDetails(selectedMonth);
	}

	@Override
	public List<Object[]> getSynchronizionDetailsByDate(String selectedDate) throws Exception {
		return afisDataSyncDao.getSynchronizionDetailsByDate(selectedDate);
	}

	@Override
	public List<Object[]> getSyncRequestAndStatusDetails(String transId) throws Exception {
		return afisDataSyncDao.getSyncRequestAndStatusDetails(transId);
	}

	@Override
	public void resetDataSyncRequest(Long dataSyncId) throws Exception {
		afisDataSyncDao.resetDataSyncRequest(dataSyncId);
		
	}

	@Override
	public List<Object[]> getSynchronizionDetailsByMonth(String selectedMonth) throws Exception {
		return afisDataSyncDao.getSynchronizionDetailsByMonth(selectedMonth);
	}
}