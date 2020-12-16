package com.nec.asia.nic.comp.trans.dao;

import java.util.List;

import com.nec.asia.nic.comp.trans.domain.EppDocumentReturned;
import com.nec.asia.nic.comp.trans.dto.StatisticalPassportProvinceReturnedDto;
import com.nec.asia.nic.framework.PaginatedResult;
import com.nec.asia.nic.framework.dao.GenericDao;

public interface EppDocumentReturnedDao  extends GenericDao<EppDocumentReturned, Long>{
    public boolean insertDataTable(EppDocumentReturned epp);
    public List<EppDocumentReturned> getByPassportNo(String passportNo);
    public PaginatedResult<StatisticalPassportProvinceReturnedDto> allStorageReceipt(String fromDate,String toDate,String officeId,int pageNo,int pageSize);
    public PaginatedResult<StatisticalPassportProvinceReturnedDto> allStorageReceived(String fromDate,String toDate,String officeId,int pageNo,int pageSize);
    public List<StatisticalPassportProvinceReturnedDto> getAllStorageReceipt(String fromDate,String toDate,String officeId) throws Exception;
    public List<StatisticalPassportProvinceReturnedDto> getAllStorageReceived(String fromDate,String toDate,String officeId) throws Exception;
}
