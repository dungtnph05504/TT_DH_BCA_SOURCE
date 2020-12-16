package com.nec.asia.nic.comp.trans.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nec.asia.nic.comp.trans.dao.ArchiveCodeDao;
import com.nec.asia.nic.comp.trans.dao.BorderGateDao;
import com.nec.asia.nic.comp.trans.dao.FreeVisaDao;
import com.nec.asia.nic.comp.trans.dao.NicUploadJobDao;
import com.nec.asia.nic.comp.trans.domain.BorderGate;
import com.nec.asia.nic.comp.trans.domain.EppAirline;
import com.nec.asia.nic.comp.trans.domain.EppArchiveCode;
import com.nec.asia.nic.comp.trans.domain.EppFlight;
import com.nec.asia.nic.comp.trans.domain.EppFreeVisa;
import com.nec.asia.nic.comp.trans.domain.NicUploadJob;
import com.nec.asia.nic.comp.trans.dto.VisaDto;
import com.nec.asia.nic.comp.trans.service.AirlineService;
import com.nec.asia.nic.comp.trans.service.AirportService;
import com.nec.asia.nic.comp.trans.service.ArchiveCodeService;
import com.nec.asia.nic.comp.trans.service.BorderGateService;
import com.nec.asia.nic.comp.trans.service.FlightService;
import com.nec.asia.nic.comp.trans.service.FreeVisaService;
import com.nec.asia.nic.comp.trans.service.NicUploadJobService;
import com.nec.asia.nic.framework.PaginatedResult;
import com.nec.asia.nic.framework.service.impl.DefaultBusinessServiceImpl;
import com.nec.asia.nic.utils.HelperClass;


@Service("freeVisaService")
public class FreeVisaServiceImpl extends
		DefaultBusinessServiceImpl<EppFreeVisa, Long, FreeVisaDao>
		implements FreeVisaService {
	@Autowired
	private FreeVisaDao dao;
	
	@Override
	public EppFreeVisa findByCtryCode_Type_PPType(String countryCode, String ppType, String type)  throws Exception{
		return dao.findByCtryCode_Type_PPType(countryCode, ppType, type);
	}
	
	@Override
	public boolean updateFreeVisa(EppFreeVisa obj) throws Exception {
		try {
			if(obj != null){
				if(obj.getId() != null && obj.getId() > 0){
					obj.setLastModifiedDate(new Date());
				}
				this.dao.saveOrUpdate(obj);
				return true;
			}
		} catch (Throwable e) {
			throw new Exception(e.getMessage());
		}
		return false;
	}

	@Override
	public PaginatedResult<VisaDto> findPaginatedByVisa(Integer status,
			String country, int pageNo, int pageSize) {
		try {
			PaginatedResult<EppFreeVisa> result = dao.findPaginatedByVisa(status, country, pageNo, pageSize);
			if(result != null){
				List<VisaDto> list = new ArrayList<VisaDto>();
				int i = (pageNo - 1) * pageSize;
				for(EppFreeVisa epp : result.getRows()){
					VisaDto dto = new VisaDto();
					i++;
					dto.setCountryCode(epp.getCountryCode());
					dto.setCreateDate(epp.getCreateDate() != null ? HelperClass.convertDateTimeToString(epp.getCreateDate()) : "");
					dto.setFreeDay(epp.getFreeDay());
					dto.setFromDate(epp.getValidFromDate() != null ? HelperClass.convertDateToString(epp.getValidFromDate()) : "");
					dto.setToDate(epp.getValidToDate() != null ? HelperClass.convertDateToString(epp.getValidToDate()) : "");
					dto.setId(epp.getId());
					dto.setLastDay(epp.getMaxLastEmmiDay());
					dto.setMaxDay(epp.getStayDay());
					dto.setStt(i);
					dto.setStatus(epp.getStatus() == 1 ? "Hoạt động" : "Khóa");
					list.add(dto);
				}
				PaginatedResult<VisaDto> pr = new PaginatedResult<>(result.getTotal(), pageNo, list);
				return pr;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
