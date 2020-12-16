package com.nec.asia.nic.comp.trans.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nec.asia.nic.comp.trans.dao.EppDocumentReturnedDao;
import com.nec.asia.nic.comp.trans.domain.EppDocumentReturned;
import com.nec.asia.nic.comp.trans.dto.StatisticalPassportProvinceReturnedDto;
import com.nec.asia.nic.comp.trans.service.EppDocumentReturnedService;
import com.nec.asia.nic.comp.ws.log.domain.CreateMessageException;
import com.nec.asia.nic.framework.PaginatedResult;
import com.nec.asia.nic.framework.service.impl.DefaultBusinessServiceImpl;
import com.nec.asia.nic.utils.DateUtil;

@Service("eppDocumentReturnedService")
public class EppDocumentReturnedServiceImpl
		extends
		DefaultBusinessServiceImpl<EppDocumentReturned, Long, EppDocumentReturnedDao>
		implements EppDocumentReturnedService {
	@Autowired
	private EppDocumentReturnedDao dao;

	@Override
	public boolean insertDataTable(EppDocumentReturned epp) {
		// TODO Auto-generated method stub
		try {
			return this.dao.insertDataTable(epp);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return false;
		}
		// return false;
	}

	@Override
	public EppDocumentReturned getByPassportNo(String passportNo) {
		try {
			List<EppDocumentReturned> list = this.dao
					.getByPassportNo(passportNo);
			if (list != null && list.size() > 0) {
				return list.get(0);
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		return null;
	}

	@Override
	public PaginatedResult<StatisticalPassportProvinceReturnedDto> allStorage(
			String dateType, String fromDate, String toDate, String officeId,
			int pageNo, int pageSize) {
		PaginatedResult<StatisticalPassportProvinceReturnedDto> pag = null;
		try {
			if (dateType.equals("TNN")) {
				pag = this.dao.allStorageReceipt(fromDate, toDate, officeId,
						pageNo, pageSize);
				return pag;
			} else if (dateType.equals("TNN_CT")) {
				pag = this.dao.allStorageReceipt(fromDate, toDate, officeId,
						pageNo, pageSize);
				List<StatisticalPassportProvinceReturnedDto> list = pag
						.getRows();
				int dem = 0;
				List<StatisticalPassportProvinceReturnedDto> listNew = new ArrayList<StatisticalPassportProvinceReturnedDto>();
				for (StatisticalPassportProvinceReturnedDto sto : list) {
					if (sto.getReturnDate() == null) {
						dem = dem + 1;
						listNew.add(sto);
					}
					pag.setRows(listNew);
					pag.setTotal(dem);
				}
				if (dem == 0) {
					pag.setRows(null);
					pag.setTotal(0);
				}
				return pag;
			} else if (dateType.equals("TNT")) {
				pag = this.dao.allStorageReceived(fromDate, toDate, officeId,
						pageNo, pageSize);
				return pag;
			} else {
				pag = this.dao.allStorageReceipt(fromDate, toDate, officeId,
						pageNo, pageSize);
				List<StatisticalPassportProvinceReturnedDto> list = pag
						.getRows();
				int dem = 0;
				List<StatisticalPassportProvinceReturnedDto> listNew = new ArrayList<StatisticalPassportProvinceReturnedDto>();
				for (StatisticalPassportProvinceReturnedDto sto : list) {
					if (sto.getReturnDate() != null) {
						dem = dem + 1;
						listNew.add(sto);
					}
					pag.setRows(listNew);
					pag.setTotal(dem);
				}
				if (dem == 0) {
					pag.setRows(null);
					pag.setTotal(0);
				}
				return pag;
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		return null;
	}

	@Override
	public List<StatisticalPassportProvinceReturnedDto> getAllStorage(
			String dateType, String fromDate, String toDate, String officeId)
			throws Exception {
		List<StatisticalPassportProvinceReturnedDto> listResult = null;
		SimpleDateFormat dateFormat = new SimpleDateFormat(DateUtil.FORMAT_DD_MM_YY);
		String dateFrom = null;
		String dateTo = null;
		try {
			if (StringUtils.isNotBlank(fromDate)) {
				Date dateF = DateUtil.strToDate(fromDate, DateUtil.FORMAT_YYYYMMDD);
				dateFrom = dateFormat.format(dateF);
			}
			if (StringUtils.isNotBlank(toDate)) {
				Date dateT = DateUtil.strToDate(toDate, DateUtil.FORMAT_YYYYMMDD);
				dateTo = dateFormat.format(dateT);
			}
			if (dateType.equals("TNN")) {
				listResult = this.dao.getAllStorageReceipt(dateFrom, dateTo,
						officeId);
			} else if (dateType.equals("TNN_CT")) {
				List<StatisticalPassportProvinceReturnedDto> listNew = this.dao.getAllStorageReceipt(dateFrom, dateTo,
						officeId);
				if (listNew != null && listNew.size() > 0) {
					listResult = new ArrayList<StatisticalPassportProvinceReturnedDto>();
					for (StatisticalPassportProvinceReturnedDto sto : listNew) {
						if (sto.getReturnDate() == null) {
							listResult.add(sto);
						}
					}
				}
				
			} else if (dateType.equals("TNT")) {
				listResult = this.dao.getAllStorageReceived(dateFrom, dateTo, officeId);
			} else if (dateType.equals("TNN_DT")){
				List<StatisticalPassportProvinceReturnedDto> list = this.dao.getAllStorageReceipt(dateFrom, dateTo, officeId);
				if (list != null && list.size() > 0) {
					listResult = new ArrayList<StatisticalPassportProvinceReturnedDto>();
					for (StatisticalPassportProvinceReturnedDto sto : list) {
						if (sto.getReturnDate() != null) {
							listResult.add(sto);
						}
					}
				}
			}
		} catch (Exception e) {
			throw new Exception(
					CreateMessageException.createMessageException(e)
							+ " - getAllStorage thất bại.");
		}
		return listResult;
	}
}
