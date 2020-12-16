package com.nec.asia.nic.comp.trans.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nec.asia.nic.comp.trans.dao.ArchiveCodeDao;
import com.nec.asia.nic.comp.trans.dao.BorderGateDao;
import com.nec.asia.nic.comp.trans.dao.FlightDao;
import com.nec.asia.nic.comp.trans.dao.NicUploadJobDao;
import com.nec.asia.nic.comp.trans.domain.BorderGate;
import com.nec.asia.nic.comp.trans.domain.EppAirline;
import com.nec.asia.nic.comp.trans.domain.EppAirport;
import com.nec.asia.nic.comp.trans.domain.EppArchiveCode;
import com.nec.asia.nic.comp.trans.domain.EppFlight;
import com.nec.asia.nic.comp.trans.domain.NicUploadJob;
import com.nec.asia.nic.comp.trans.dto.FlightDto;
import com.nec.asia.nic.comp.trans.service.AirlineService;
import com.nec.asia.nic.comp.trans.service.AirportService;
import com.nec.asia.nic.comp.trans.service.ArchiveCodeService;
import com.nec.asia.nic.comp.trans.service.BorderGateService;
import com.nec.asia.nic.comp.trans.service.FlightService;
import com.nec.asia.nic.comp.trans.service.NicUploadJobService;
import com.nec.asia.nic.framework.PaginatedResult;
import com.nec.asia.nic.framework.service.impl.DefaultBusinessServiceImpl;


@Service("flightService")
public class FlightServiceImpl extends
		DefaultBusinessServiceImpl<EppFlight, Long, FlightDao>
		implements FlightService {
	@Autowired
	private FlightDao dao;
	
	@Override
	public EppFlight findByFlightno_Type(String flightNo, String type) throws Exception{
		return dao.findByFlightno_Type(flightNo, type);
	}
	
	@Override
	public boolean updateFlight(EppFlight obj) throws Exception {
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
	public PaginatedResult<FlightDto> findPaginateBySearch(String flightNo,
			String routerCode, String airlineNo, int pageNo, int pageSize) {
		try {
			PaginatedResult<EppFlight> result = dao.findPaginateBySearch(flightNo, routerCode, airlineNo, pageNo, pageSize);
			if(result != null){
				List<FlightDto> list = new ArrayList<FlightDto>();
				int i = (pageNo - 1) * pageSize;
				for(EppFlight epp : result.getRows()){
					FlightDto dto = new FlightDto();
					i++;
					dto.setAirlineNo(epp.getAirlineCode());
					dto.setFlightNo(epp.getFlightNo());
					dto.setFlightRouterCode(epp.getFlightRouterCode());
					dto.setId(epp.getId());
					dto.setName(epp.getName());
					dto.setNote(epp.getNote());
					dto.setStt(i);
					dto.setType(epp.getType());
					list.add(dto);
				}
				PaginatedResult<FlightDto> pr = new PaginatedResult<>(result.getTotal(), pageNo, list);
				return pr;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public Boolean deleteEppFlight(Long id) {
		try {
			EppFlight epp = dao.findById(id);
			if(epp != null){
				dao.delete(epp);
			}
			return true;
		} catch (Exception e) {
			// TODO: handle exception
		}
		return false;
	}

	@Override
	public List<EppFlight> findByFlightRouter(String routerCode) {
		// TODO Auto-generated method stub
		return dao.findByFlightRouter(routerCode);
	}

	@Override
	public void saveOrUpdateFlight(EppFlight epp) {
		dao.saveOrUpdate(epp);
		
	}
}
