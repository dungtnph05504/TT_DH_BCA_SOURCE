package com.nec.asia.nic.comp.a08database.service.impl;

import java.sql.ResultSet;
import java.util.List;

import org.springframework.stereotype.Service;

import com.nec.asia.nic.comp.a08database.dao.GetRecordDetailDao;
import com.nec.asia.nic.comp.a08database.dao.impl.GetRecordDetailDaoImpl;
import com.nec.asia.nic.comp.a08database.domain.ABTCDetail;
import com.nec.asia.nic.comp.a08database.domain.CnVpDetail;
import com.nec.asia.nic.comp.a08database.domain.DocumentCancelDetail;
import com.nec.asia.nic.comp.a08database.domain.DoiTuong;
import com.nec.asia.nic.comp.a08database.domain.DoiTuongDetail;
import com.nec.asia.nic.comp.a08database.domain.GpXncDetail;
import com.nec.asia.nic.comp.a08database.domain.HsVpDetail;
import com.nec.asia.nic.comp.a08database.domain.ImmigrationHistoryDetail;
import com.nec.asia.nic.comp.a08database.domain.InfoCMT;
import com.nec.asia.nic.comp.a08database.domain.LSCapHoChieu;
import com.nec.asia.nic.comp.a08database.domain.NhanTlDetail;
import com.nec.asia.nic.comp.a08database.domain.PassportStatus;
import com.nec.asia.nic.comp.a08database.domain.ThoiQtDetail;
import com.nec.asia.nic.comp.a08database.domain.TlQtDetail;
import com.nec.asia.nic.comp.a08database.domain.VkHhDetail;
import com.nec.asia.nic.comp.a08database.domain.XmnsDetail;
import com.nec.asia.nic.comp.a08database.service.GetRecordDetailService;
import com.nec.asia.nic.comp.ws.log.domain.CreateMessageException;

@Service("getRecordDetailService")
public class GetRecordDetailServiceImpl implements GetRecordDetailService {

	private GetRecordDetailDao dao;

	public GetRecordDetailServiceImpl() {
		dao = new GetRecordDetailDaoImpl();
	}

	@Override
	public ResultSet getRecordDetailByProcedure(String procedureName,
			String input) throws Exception {
		try {
			return this.dao.getRecordDetailByProcedure(procedureName, input);
		} catch (Exception e) {
			throw e;
		}
	}

	@Override
	public ResultSet getRecordDetailByFunction(String functionName, String input)
			throws Exception {
		try {
			return this.dao.getRecordDetailByFunction(functionName, input);
		} catch (Exception e) {
			throw e;
		}
	}

	@Override
	public List<ABTCDetail> getAbtcDetails(String personCode) throws Exception {
		try {
			return this.dao.getAbtcDetails(personCode);
		} catch (Exception e) {
			throw new Exception(
					CreateMessageException.createMessageException(e)
							+ " - getAbtcDetails thất bại.");
		}
	}

	@Override
	public List<GpXncDetail> getGpXncDetails(String personCode)
			throws Exception {
		try {
			return this.dao.getGpXncDetails(personCode);
		} catch (Exception e) {
			throw new Exception(
					CreateMessageException.createMessageException(e)
							+ " - getGpXncDetails thất bại.");
		}
	}

	@Override
	public List<TlQtDetail> getTlQtDetails(String personCode) throws Exception {
		try {
			return this.dao.getTlQtDetails(personCode);
		} catch (Exception e) {
			throw new Exception(
					CreateMessageException.createMessageException(e)
							+ " - getTlQtDetails thất bại.");
		}
	}

	@Override
	public List<ThoiQtDetail> getThoiQtDetails(String personCode)
			throws Exception {
		try {
			return this.dao.getThoiQtDetails(personCode);
		} catch (Exception e) {
			throw new Exception(
					CreateMessageException.createMessageException(e)
							+ " - getThoiQtDetails thất bại.");
		}
	}

	@Override
	public List<NhanTlDetail> getNhanTlDetails(String personCode)
			throws Exception {
		try {
			return this.dao.getNhanTlDetails(personCode);
		} catch (Exception e) {
			throw new Exception(
					CreateMessageException.createMessageException(e)
							+ " - getNhanTlDetails thất bại.");
		}
	}

	@Override
	public List<XmnsDetail> getXmnsDetails(String personCode) throws Exception {
		try {
			return this.dao.getXmnsDetails(personCode);
		} catch (Exception e) {
			throw new Exception(
					CreateMessageException.createMessageException(e)
							+ " - getXmnsDetails thất bại.");
		}
	}

	@Override
	public List<VkHhDetail> getVkHhDetails(String personCode) throws Exception {
		try {
			return this.dao.getVkHhDetails(personCode);
		} catch (Exception e) {
			throw new Exception(
					CreateMessageException.createMessageException(e)
							+ " - getVkHhDetails thất bại.");
		}
	}

	@Override
	public List<HsVpDetail> getHsVpDetails(String personCode) throws Exception {
		try {
			return this.dao.getHsVpDetails(personCode);
		} catch (Exception e) {
			throw new Exception(
					CreateMessageException.createMessageException(e)
							+ " - getHsVpDetails thất bại.");
		}
	}

	@Override
	public List<CnVpDetail> getCnVpDetails(String personCode) throws Exception {
		try {
			return this.dao.getCnVpDetails(personCode);
		} catch (Exception e) {
			throw new Exception(
					CreateMessageException.createMessageException(e)
							+ " - getCnVpDetails thất bại.");
		}
	}

	@Override
	public List<DoiTuongDetail> getDoiTuongDetails(String personCode)
			throws Exception {
		try {
			return this.dao.getDoiTuongDetails(personCode);
		} catch (Exception e) {
			throw new Exception(
					CreateMessageException.createMessageException(e)
							+ " - getDoiTuongDetails thất bại.");
		}
	}

	@Override
	public List<LSCapHoChieu> getLsCapHoChieus(String personCode)
			throws Exception {
		try {
			return this.dao.getLsCapHoChieus(personCode);
		} catch (Exception e) {
			throw new Exception(
					CreateMessageException.createMessageException(e)
							+ " - getLsCapHoChieus thất bại.");
		}
	}

	@Override
	public List<DoiTuong> getDoiTuongs(String fullname, String dateOfBirth,
			String dateOBType, String country, String gender,
			String passportNo, String identityCardNo) throws Exception {
		try {
			return this.dao.getDoiTuongs(fullname, dateOfBirth, dateOBType,
					country, gender, passportNo, identityCardNo);
		} catch (Exception e) {
			throw new Exception(
					CreateMessageException.createMessageException(e)
							+ " - getDoiTuongs thất bại.");
		}
	}

	@Override
	public int checkCaNhanHsXnc(String personCode, String type)
			throws Exception {
		try {
			return this.dao.checkCaNhanHsXnc(personCode, type);
		} catch (Exception e) {
			throw new Exception(
					CreateMessageException.createMessageException(e)
							+ " - checkCaNhanHsXnc thất bại.");
		}
	}

	@Override
	public List<ImmigrationHistoryDetail> getImmigrationHistoryDetail(
			String personCode) throws Exception {
		try {
			return this.dao.getImmigrationHistoryDetail(personCode);
		} catch (Exception e) {
			throw new Exception(
					CreateMessageException.createMessageException(e)
							+ " - getImmigrationHistoryDetail thất bại.");
		}
	}

	@Override
	public List<DocumentCancelDetail> getDocumentCancelDetail(String gender,
			String fullname, String dateOBType, String dateOfBirth,
			String country, String identityCardNo, String passportNo)
			throws Exception {
		try {
			return this.dao.getDocumentCancelDetail(fullname, dateOfBirth,
					dateOBType, country, gender, passportNo, identityCardNo);
		} catch (Exception e) {
			throw new Exception(
					CreateMessageException.createMessageException(e)
							+ " - getDocumentCancelDetail thất bại.");
		}
	}

	@Override
	public List<InfoCMT> getInfoCmnd(String ip, String idNumber) throws Exception {
		List<InfoCMT> listInfo = null;
		try {
			listInfo = this.dao.getInfoCmnd(ip, idNumber);
		} catch (Exception e) {
			throw new Exception(CreateMessageException.createMessageException(e)
					+ " - getInfoCmnd thất bại.");
		}
		return listInfo;
	}

	@Override
	public List<PassportStatus> getListPassportStatus(String passportNo)
			throws Exception {
		List<PassportStatus> list = null;
		try {
			list = this.dao.getListPassportStatus(passportNo);
		} catch (Exception e) {
			throw new Exception(CreateMessageException.createMessageException(e)
					+ " - getListPassportStatus thất bại.");
		}
		return list;
	}
}
