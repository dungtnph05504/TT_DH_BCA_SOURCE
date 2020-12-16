package com.nec.asia.nic.comp.trans.dao;

import java.util.Date;
import java.util.List;

import com.nec.asia.nic.comp.immihistory.domain.ImmiHistory;
import com.nec.asia.nic.comp.immihistory.domain.ImmiHistoryInfo;
import com.nec.asia.nic.comp.trans.domain.EppBlacklist;
import com.nec.asia.nic.comp.trans.dto.CountPassport;
import com.nec.asia.nic.comp.trans.dto.ImmiHistoryDto;
import com.nec.asia.nic.comp.trans.dto.NicUploadJobDto;
import com.nec.asia.nic.framework.PaginatedResult;
import com.nec.asia.nic.framework.dao.GenericDao;

/* 
 * Modification History:
 *  
 * 11 Dec 2013 (chris): init class. 
 */
public interface NicImmiHistoryDao extends GenericDao<ImmiHistory, Long> {
	
	public List<ImmiHistory> findByNinPPno (String nin, String passportNo);
	public List<ImmiHistory> findAllByTypeOrGate(String type, String borderGate);
	
	public List<CountPassport> borderGateNC() throws Exception;
	
	public List<CountPassport> borderGateNCVP() throws Exception;
	
	public List<CountPassport> borderGateXC() throws Exception;
	
	public List<CountPassport> borderGateXCVP() throws Exception;
	
	public List<NicUploadJobDto> newImmihistory() throws Exception;
	
	public long listImmihistoryAllCount(NicUploadJobDto model) throws Exception;
	public List<ImmiHistory> listImmihistoryAll(NicUploadJobDto dto, int pageNumber, int pageSize) throws Exception;
	public ImmiHistory findImmiByTransactionId(String transactionId, Long id) throws Exception;
	
	public PaginatedResult<ImmiHistoryDto> getAllImmihistory(String fullName, String dob, String gender, String passportNo, String visaNo, String nin, String nationality, int pageNo, int pageSize);

	public PaginatedResult<ImmiHistoryDto> getAllImmihistoryA(String fullName, int startY, int endY, String gender, String passportNo, String visaNo, String nin, String nationality, int pageNo, int pageSize);
	public PaginatedResult<ImmiHistoryInfo> getListImmihistory(String fullname,String passportNo,
    		String vidaNo,String nationCode,String dob,String gender,String fromDate,String endDate,
    		String typeXnc,String ckXNC,String chuyenBay,String sophieuXNC,String purpose,
    		String sapxep,int pageNo,int pageSite);
	public Integer totalImmi(String fullName, String dob, String gender, String passportNo, String visaNo, String nin, String nationality);

	public Integer totalImmiA(String fullName, int startY, int endY, String gender, String passportNo, String visaNo, String nin, String nationality);
	public Integer totalImmiByBorder(String immiType, String borderGate);
    public ImmiHistory getImmiHistoryById(Long id);
}
