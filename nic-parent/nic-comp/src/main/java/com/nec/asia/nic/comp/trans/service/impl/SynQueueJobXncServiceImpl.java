package com.nec.asia.nic.comp.trans.service.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nec.asia.nic.comp.trans.dao.SynQueueJobXncDao;
import com.nec.asia.nic.comp.trans.domain.SynQueueJobXnc;
import com.nec.asia.nic.comp.trans.service.NicImmiHistoryService;
import com.nec.asia.nic.comp.trans.service.SynQueueJobXncService;
import com.nec.asia.nic.comp.trans.utils.RegistrationConstants;
import com.nec.asia.nic.framework.admin.code.domain.Parameters;
import com.nec.asia.nic.framework.admin.code.service.ParametersService;
import com.nec.asia.nic.framework.service.impl.DefaultBusinessServiceImpl;

@Service("queueJobXncService")
public class SynQueueJobXncServiceImpl extends DefaultBusinessServiceImpl<SynQueueJobXnc, Long, SynQueueJobXncDao> implements SynQueueJobXncService{

	@Autowired
	private SynQueueJobXncDao dao;
	
	@Autowired
	private ParametersService parametersService;
	
	@Autowired
	private NicImmiHistoryService immiService;
	
	@Override
	public void saveOrUpdateQueueXnc(SynQueueJobXnc queue) {
		try {
			this.dao.saveOrUpdate(queue);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	@Override
	public void updateQueueXncJob(Long id, String status) throws Exception {
		try {
			SynQueueJobXnc queue = this.dao.findQueueXncByInfo(id, null);
			if(queue != null){
				if(status.equals(RegistrationConstants.CODE_UPDATE_SUCCESS_API)){
					this.dao.delete(queue);
				}else if(status.equals(RegistrationConstants.CODE_UPDATE_FAIL_API)){
					Parameters pr = parametersService.getParamDetails(RegistrationConstants.PARAMETERS_SCOPE_SYSTEM, RegistrationConstants.PARAMETERS_NAME_MAX_COUNT_CALL_QUEUE_XNC);
					int maxDefault = 10;
					if(pr != null){
						maxDefault = Integer.valueOf(pr.getParaShortValue());
					}
					if(queue.getSyncRetry() < maxDefault){
						queue.setStatus(RegistrationConstants.CODE_STATUS_QUEUE_PENDING);
						queue.setSyncTs(Calendar.getInstance().getTime());
						queue.setSyncRetry(queue.getSyncRetry() + 1);
						this.dao.saveOrUpdate(queue);
					}else{
						queue.setStatus(RegistrationConstants.CODE_STATUS_QUEUE_NONE);
						queue.setSyncTs(Calendar.getInstance().getTime());		
						this.dao.saveOrUpdate(queue);
					}
				}
			}
		} catch (Throwable e) {
			throw new Exception(e);
		}
		
	}

	@Override
	public void updateStatusQueueXncJob(Long id, String status)
			throws Exception {
		try {
			SynQueueJobXnc queue = this.dao.findById(id);
			if(queue != null){
				queue.setStatus(status);
				this.dao.saveOrUpdate(queue);
			}
		} catch (Throwable e) {
			throw new Exception(e.getMessage());
		}
		
	}

	@Override
	public List<SynQueueJobXnc> findQueueXncBySite(String site,
			String status, String[] objType, int maxTotal) throws Exception {
		List<SynQueueJobXnc> listSync = new ArrayList<SynQueueJobXnc>();
		try {
			List<SynQueueJobXnc> list = this.dao.findQueueXncBySite(site, status, objType, maxTotal);
			List<SynQueueJobXnc> dlList = new ArrayList<SynQueueJobXnc>();
			if(list != null){
				for(SynQueueJobXnc syn : list){
					if(syn.getIdImmi() != null){
						if(immiService.checkDownloadQueue(syn.getIdImmi()))
							dlList.add(syn);							
					}else{
						dlList.add(syn);
					}
				}
				
				if(dlList.size() <= maxTotal){
					listSync.addAll(dlList);
				}else{
					int i = 0;
					for(SynQueueJobXnc syn : dlList){
						listSync.add(syn);
						if(i > (maxTotal - 1))
							break;
						i++;
					}
				}
			}
		} catch (Throwable e) {
			throw new Exception("error when the get value by buf in queue: " + e.getMessage());
		}
		return listSync;
	}

	@Override
	public SynQueueJobXnc findQueueXncByStatus(String site, String status,
			String objType) throws Exception {
		return this.dao.findQueueXncByStatus(site, status, objType);
	}

}
