package com.nec.asia.nic.comp.trans.service;

import java.util.List;

import com.nec.asia.nic.comp.immihistory.domain.ImmiHistory;
import com.nec.asia.nic.comp.immihistory.domain.ImmiHistoryResult;
import com.nec.asia.nic.comp.trans.dto.NicUploadJobDto;
import com.nec.asia.nic.framework.service.BusinessService;

public interface NicImmiHistoryResultService extends BusinessService<ImmiHistoryResult, Long> {
	public Integer getCountImmiByType(String code, int type, int result); //type = 0 thất bại, type = 1 thành công, result = 0 nhập cảnh, 1 xuất cảnh

	public ImmiHistoryResult getByImmiId(Long id);
	
	public List<ImmiHistoryResult> getListByImmiId(Long[] id, Boolean error);
	
}
