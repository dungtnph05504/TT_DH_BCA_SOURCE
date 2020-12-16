package com.nec.asia.nic.comp.trans.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nec.asia.nic.comp.immihistory.domain.ImmiHistoryResult;
import com.nec.asia.nic.comp.trans.dao.NicBlacklistDao;
import com.nec.asia.nic.comp.trans.dao.NicImmiHistoryResultDao;
import com.nec.asia.nic.comp.trans.domain.EppBlacklist;
import com.nec.asia.nic.comp.trans.service.NicImmiHistoryResultService;
import com.nec.asia.nic.framework.service.impl.DefaultBusinessServiceImpl;

@Service
public class NicImmiHistoryResultServiceImpl 
		extends DefaultBusinessServiceImpl<ImmiHistoryResult, Long, NicImmiHistoryResultDao>
		implements NicImmiHistoryResultService {

	@Autowired
	private NicImmiHistoryResultDao dao;
	
	@Override
	public Integer getCountImmiByType(String code, int type, int result) {
		try {
			
		} catch (Exception e) {
			// TODO: handle exception
		}
		return 0;
	}

	public ImmiHistoryResult getByImmiId(Long id){
		ImmiHistoryResult result = new ImmiHistoryResult();
		try {
			result = dao.getByImmiId(id);
		} catch (Exception e) {
			// TODO: handle exception
		}
		return result;
	}
	
	public List<ImmiHistoryResult> getListByImmiId(Long[] id, Boolean error) {
		return dao.getListByImmiId(id, error);
	}
}
