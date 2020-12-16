package com.nec.asia.nic.comp.trans.service;
import java.util.List;

import com.nec.asia.nic.comp.trans.domain.EppDocumentReturned;
import com.nec.asia.nic.comp.trans.dto.StatisticalPassportProvinceReturnedDto;
import com.nec.asia.nic.framework.PaginatedResult;
import com.nec.asia.nic.framework.service.BusinessService;

public interface EppDocumentReturnedService extends BusinessService<EppDocumentReturned, Long>{
    public boolean insertDataTable(EppDocumentReturned epp);
    public EppDocumentReturned getByPassportNo(String passportNo);
    public PaginatedResult<StatisticalPassportProvinceReturnedDto> allStorage(String dateType,String fromDate,String toDate,String officeId,int pageNo,int pageSize);
    public List<StatisticalPassportProvinceReturnedDto> getAllStorage(String dateType,String fromDate,String toDate,String officeId) throws Exception;
}
