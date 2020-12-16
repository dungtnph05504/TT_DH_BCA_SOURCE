package com.nec.asia.nic.comp.a08database.service;

import java.sql.ResultSet;
import java.util.List;

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

public interface GetRecordDetailService {
	public ResultSet getRecordDetailByProcedure(String procedureName, String input) throws Exception;
	public ResultSet getRecordDetailByFunction(String functionName, String input) throws Exception;
	
	public List<ABTCDetail> getAbtcDetails(String personCode) throws Exception;
	public List<GpXncDetail> getGpXncDetails(String personCode) throws Exception;
	public List<TlQtDetail>	getTlQtDetails(String personCode) throws Exception;
	public List<ThoiQtDetail> getThoiQtDetails(String personCode) throws Exception;
	public List<NhanTlDetail> getNhanTlDetails(String personCode) throws Exception;
	public List<XmnsDetail> getXmnsDetails(String personCode) throws Exception;
	public List<VkHhDetail> getVkHhDetails(String personCode) throws Exception;
	public List<HsVpDetail> getHsVpDetails(String personCode) throws Exception;
	public List<CnVpDetail> getCnVpDetails(String personCode) throws Exception;
	public List<DoiTuongDetail> getDoiTuongDetails(String personCode) throws Exception;
	public List<LSCapHoChieu> getLsCapHoChieus(String personCode) throws Exception;
	
	public List<DoiTuong> getDoiTuongs(String fullname, String dateOfBirth,
			String dateOBType, String country, String gender,
			String passportNo, String identityCardNo) throws Exception;
	public int checkCaNhanHsXnc(String personCode, String type)
			throws Exception;
	public List<ImmigrationHistoryDetail> getImmigrationHistoryDetail(String personCode) throws Exception;
	public List<DocumentCancelDetail> getDocumentCancelDetail(String gender,
			String fullname, String dateOBType, String dateOfBirth,
			String country, String identityCardNo, String passportNo) throws Exception;
	
	public List<InfoCMT> getInfoCmnd(String ip, String idNumber) throws Exception;
	public List<PassportStatus> getListPassportStatus(String passportNo) throws Exception;
}
