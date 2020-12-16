/**
 * 
 */
package com.nec.asia.nic.comp.trans.service;

import java.util.List;

import javax.persistence.criteria.CriteriaBuilder.In;

import oracle.net.ano.SupervisorService;

import com.nec.asia.nic.comp.immihistory.domain.ImmiHistory;
import com.nec.asia.nic.comp.immihistory.domain.ImmiHistoryInfo;
import com.nec.asia.nic.comp.immihistory.model.Immihistory;
import com.nec.asia.nic.comp.job.dto.ImmiHistoryData;
import com.nec.asia.nic.comp.trans.domain.EppBlacklist;
import com.nec.asia.nic.comp.trans.domain.NicTransactionAttachment;
import com.nec.asia.nic.comp.trans.dto.CountPassport;
import com.nec.asia.nic.comp.trans.dto.ImmiHistoryDto;
import com.nec.asia.nic.comp.trans.dto.NicUploadJobDto;
import com.nec.asia.nic.framework.PaginatedResult;
import com.nec.asia.nic.framework.service.BusinessService;

/**
 * @author sailaja_chinapothula
 * @Company: NEC Asia Pacific Ltd
 * @Since: Jun 12, 2013
 */
/*
 * Modification History:
 * 07 April 2014 (Peddi Swapna): Added getTransactionProofDocuments method to get the proof documents.
 */
public interface NicImmiHistoryService extends BusinessService<ImmiHistory, Long> {
	
	public List<ImmiHistory> findByNinPPno (String nin, String passportNo);
	public List<ImmiHistoryData> findImmiByNinPPno (String nin, String passportNo) throws Exception;
	public List<ImmiHistory> findAllByTypeOrGate(String type, String borderGate);
	
	public List<CountPassport> borderGateNC() throws Exception;
	
	public List<CountPassport> borderGateNCVP() throws Exception;
	
	public List<CountPassport> borderGateXC() throws Exception;
	
	public List<CountPassport> borderGateXCVP() throws Exception;
	
	public List<NicUploadJobDto> newImmihistory() throws Exception;
	
	public List<ImmiHistory> listImmihistoryAll(NicUploadJobDto dto, int pageNumber, int pageSize);
	
	public long listImmihistoryAllCount(NicUploadJobDto dto);
	
	public void saveOrUpdateImmi(ImmiHistory immiHistory) throws Exception;
	
	public Boolean updateSupervisor(String transactionId, String supervisorName, String note, String blacklistId, Integer isPass) throws Exception;
	
	public Boolean updateAdminStatus(String transactionId, String adminName, String note, Integer isPass) throws Exception;

	public Boolean checkDownloadQueue(Long idImmi);	
	
	public ImmiHistory updateInfoImmihistory(Immihistory immi);
	
	public PaginatedResult<ImmiHistoryDto> getAllImmihistory(String fullName, String dob, String gender, String passportNo, String visaNo, String nin, String nationality, int pageNo, int pageSize);
    public PaginatedResult<ImmiHistoryInfo> getListImihistory(String fullname,String passportNo,
    		String vidaNo,String nationCode,String dob,String gender,String fromDate,String endDate,
    		String typeXnc,String ckXNC,String chuyenBay,String sophieuXNC,String purpose,
    		String sapxep,int pageNo,int pageSite);
	public PaginatedResult<ImmiHistoryDto> getAllImmihistoryA(String fullName, int startY, int endY, String gender, String passportNo, String visaNo, String nin, String nationality, int pageNo, int pageSize);
	public Integer totalImmiByBorder(String immiType, String borderGate);
	public ImmiHistory getImmiHistoryById(Long id);
	public void deleteChildeByImmiId(Long id);
}
