package com.nec.asia.nic.comp.a08database.dao.impl;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import jcifs.util.Base64;

import com.nec.asia.nic.comp.a08database.connect.A08DatabaseConnection;
import com.nec.asia.nic.comp.a08database.dao.GetRecordDetailDao;
import com.nec.asia.nic.comp.a08database.domain.ABTCApproveDetail;
import com.nec.asia.nic.comp.a08database.domain.ABTCDetail;
import com.nec.asia.nic.comp.a08database.domain.CnVpDetail;
import com.nec.asia.nic.comp.a08database.domain.DecAttachment;
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
import com.nec.asia.nic.comp.a08database.domain.PersonFamily;
import com.nec.asia.nic.comp.a08database.domain.ThoiQtDetail;
import com.nec.asia.nic.comp.a08database.domain.TlQtDetail;
import com.nec.asia.nic.comp.a08database.domain.VkHhDetail;
import com.nec.asia.nic.comp.a08database.domain.XmnsDetail;
import com.nec.asia.nic.comp.trans.domain.NicDocumentData;
import com.nec.asia.nic.framework.admin.code.service.CodesService;
import com.nec.asia.nic.utils.DateUtil;

public class GetRecordDetailDaoImpl implements GetRecordDetailDao {

	private static final String TYPE_TIMESTAMP = "Timestamp";
	private static final String TYPE_DATETIME = "Datetime";

	@Autowired
	private CodesService codesService;

	public void setCodesService(CodesService codesService) {
		this.codesService = codesService;
	}

	public final Logger logger = LoggerFactory.getLogger(this.getClass());
	@Override
	public ResultSet getRecordDetailByProcedure(String procedureName,
			String input) throws Exception {
		Connection conn = null;
		try {
			conn = A08DatabaseConnection.connectA08Database();
			if (conn != null) {
				conn.createStatement();
				CallableStatement command = conn.prepareCall(procedureName);
				command.setString(1, input);
				command.registerOutParameter(2, -10);
				command.executeUpdate();
				ResultSet result = (ResultSet) command.getObject(2);
				return result;
			}
		} catch (Exception e) {
			throw e;
		} finally {
			if (conn != null) {
				conn.close();
			}
		}
		return null;
	}

	@Override
	public ResultSet getRecordDetailByFunction(String functionName, String input)
			throws Exception {
		Connection conn = null;
		try {
			conn = A08DatabaseConnection.connectA08Database();
			if (conn != null) {
				CallableStatement command = conn.prepareCall(functionName);
				command.registerOutParameter(1, -10);
				command.setString(2, input);
				command.execute();
				ResultSet result = (ResultSet) command.getObject(1);
				return result;
			}
		} catch (Exception e) {
			throw e;
		}
		return null;
	}

	@Override
	public List<ABTCDetail> getAbtcDetails(String personCode) throws Exception {
		Connection conn = null;
		List<ABTCDetail> listAbtcDetails = null;
		try {
			conn = A08DatabaseConnection.connectA08Database();
			if (conn != null) {
				CallableStatement command = conn
						.prepareCall(A08DatabaseConnection.FUNCTION_GET_ABTC_DETAILS);
				command.registerOutParameter(1, -10);
				command.setString(2, personCode);
				command.execute();
				ResultSet result = (ResultSet) command.getObject(1);
				if (result != null) {
					listAbtcDetails = new ArrayList<ABTCDetail>();
					while (result.next()) {
						ABTCDetail abtcDetail = new ABTCDetail();
						abtcDetail.setMaCaNhan(result.getString("MACANHAN"));
						abtcDetail.setHoTen(result.getString("HO_TEN"));
						abtcDetail.setNgaySinh(result.getString("NGAY_SINH"));
						abtcDetail.setGioiTinh(result.getString("GIOI_TINH"));
						abtcDetail
								.setMaNoiSinh(result.getString("MA_NOI_SINH"));
						abtcDetail.setNoiSinh(result.getString("NOI_SINH"));
						abtcDetail.setDanToc(result.getString("DAN_TOC"));
						abtcDetail.setReceiptNo(result.getString("RECEIPT_NO"));
						abtcDetail.setHoSoId(result.getDouble("HO_SO_ID"));
						abtcDetail.setBuocXl(result.getString("BUOC_XL"));
						abtcDetail.setNganhNghe(result.getString("NGANH_NGHE"));
						abtcDetail.setChucVu(result.getString("CHUC_VU"));
						abtcDetail.setSoDt(result.getString("SO_DT"));
						abtcDetail.setSoFax(result.getString("SO_FAX"));
						abtcDetail.setEmail(result.getString("EMAIL"));
						abtcDetail.setDcThuongTru(result
								.getString("DC_THUONG_TRU"));
						abtcDetail
								.setCqChuQuan(result.getString("CQ_CHU_QUAN"));
						abtcDetail.setDoanhNghiep(result
								.getString("DOANH_NGHIEP"));
						abtcDetail.setSoCv(result.getString("SO_CV"));
						abtcDetail.setNgayCv(result.getString("NGAY_CV"));
						abtcDetail.setNguoiNhapMay(result
								.getString("NGUOI_NHAP_MAY"));
						abtcDetail.setNgayNhapMay(result
								.getString("NGAY_NHAP_MAY"));
						abtcDetail.setSoThe(result.getString("SO_THE"));
						abtcDetail.setNgayIn(result.getString("NGAY_IN"));
						abtcDetail.setTrangThai(result.getString("TRANG_THAI"));
						abtcDetail.setLyDoHuyThe(result
								.getString("LY_DO_HUY_THE"));
						abtcDetail.setGhiChu(result.getString("GHI_CHU"));
						abtcDetail.setSoHc(result.getString("SO_HC"));
						abtcDetail
								.setNgayCapHc(result.getString("NGAY_CAP_HC"));
						abtcDetail.setHanHc(result.getString("HAN_HC"));
						abtcDetail.setApecAppno(result.getString("APEC_APPNO"));
						abtcDetail.setDsPermition(result
								.getString("DS_PERMITION"));
						abtcDetail.setNoiDungDuyet(result
								.getString("NOI_DUNG_DUYET"));
						abtcDetail.setNguoiDeXuat(result
								.getString("NGUOI_DE_XUAT"));
						abtcDetail.setNguoiDuyet1(result
								.getString("NGUOI_DUYET_1"));
						abtcDetail.setNgayDuyet(result.getString("NGAY_DUYET"));
						abtcDetail.setKqHoSo(result.getString("KQ_HO_SO"));
						abtcDetail.setNguoiIn(result.getString("NGUOI_IN"));
						abtcDetail.setSoLuuHs(result.getString("SO_LUU_HS"));
						abtcDetail
								.setSoDsP42p3(result.getString("SO_DS_P42P3"));
						abtcDetail.setNgayDsP42p3(result
								.getString("NGAY_DS_P42P3"));
						abtcDetail
								.setSoDsP32p4(result.getString("SO_DS_P32P4"));
						abtcDetail.setNgayDsP32p4(result
								.getString("NGAY_DS_P32P4"));
						if (result.getBlob("ANH") != null) {
							String anh = Base64.encode(result.getBytes("ANH"));
							abtcDetail.setAnh(anh);
						}
						abtcDetail.setDiaChiNoiLv(result
								.getString("DIA_CHI_NOI_LV"));
						abtcDetail.setHanThe(result.getString("HAN_THE"));
						List<ABTCApproveDetail> listApproveDetails = null;
						// Bổ sung lấy chi tiết ABTC_APPROVE_DETAIL.
						CallableStatement commandDetail = conn
								.prepareCall(A08DatabaseConnection.FUNCTION_GET_ABTC_APPROVE_DETAILS);
						commandDetail.registerOutParameter(1, -10);
						commandDetail.setDouble(2, abtcDetail.getHoSoId());
						commandDetail.execute();
						ResultSet resultDetail = (ResultSet) commandDetail
								.getObject(1);
						if (resultDetail != null) {
							listApproveDetails = new ArrayList<ABTCApproveDetail>();
							while (resultDetail.next()) {
								ABTCApproveDetail approveDetail = new ABTCApproveDetail();
								approveDetail.setQuocGia(resultDetail
										.getString("QUOC_GIA"));
								approveDetail.setAbtcPermissionUid(resultDetail
										.getString("ABTC_PERMISSION_UID"));
								approveDetail.setApecAppno(resultDetail
										.getString("APEC_APPNO"));
								approveDetail.setPermit(resultDetail
										.getString("PERMIT"));
								approveDetail.setPermitNation(resultDetail
										.getString("PERMIT_NATION"));
								approveDetail
										.setTuNgay(createDatetime(
												TYPE_DATETIME, "TU_NGAY",
												resultDetail));
								approveDetail
										.setDenNgay(createDatetime(
												TYPE_DATETIME, "DEN_NGAY",
												resultDetail));
								approveDetail.setCancelDate(createDatetime(
										TYPE_TIMESTAMP, "CANCEL_DATE",
										resultDetail));

								listApproveDetails.add(approveDetail);
							}
						}
						if (commandDetail != null) {
							commandDetail.close();
						}
						if (resultDetail != null) {
							resultDetail.close();
						}
						if (listApproveDetails != null
								&& listApproveDetails.size() > 0) {
							abtcDetail
									.setListApproveDetails(listApproveDetails);
						}

						listAbtcDetails.add(abtcDetail);
					}

				}
			}
			return listAbtcDetails;
		} catch (Exception e) {
			throw e;
		} finally {
			if (conn != null) {
				conn.close();
			}
		}
	}

	@Override
	public List<GpXncDetail> getGpXncDetails(String personCode)
			throws Exception {
		Connection conn = null;
		List<GpXncDetail> listGpXncDetails = null;
		try {
			conn = A08DatabaseConnection.connectA08Database();
			if (conn != null) {
				CallableStatement command = conn
						.prepareCall(A08DatabaseConnection.FUNCTION_GET_GP_XNC_DETAILS);
				command.registerOutParameter(1, -10);
				command.setString(2, personCode);
				command.execute();
				ResultSet result = (ResultSet) command.getObject(1);
				if (result != null) {
					listGpXncDetails = new ArrayList<GpXncDetail>();
					while (result.next()) {
						GpXncDetail gpXncDetail = new GpXncDetail();
						gpXncDetail.setBlInvestBy(result
								.getString("BL_INVEST_BY"));
						gpXncDetail.setBlInvestDate(createDatetime(
								TYPE_TIMESTAMP, "BL_INVEST_DATE", result));
						gpXncDetail.setCancelBy(result.getString("CANCEL_BY"));
						gpXncDetail.setCancelDate(createDatetime(
								TYPE_TIMESTAMP, "CANCEL_DATE", result));
						gpXncDetail.setCancelReason(result
								.getString("CANCEL_REASON"));
						gpXncDetail.setcApprvrDate(createDatetime(
								TYPE_TIMESTAMP, "C_APPRVR_DATE", result));
						gpXncDetail.setcApprvrName(result
								.getString("C_APPRVR_NAME"));
						gpXncDetail.setcListCode(result
								.getString("C_LIST_CODE"));
						gpXncDetail.setcListCreatedDate(createDatetime(
								TYPE_TIMESTAMP, "C_LIST_CREATED_DATE", result));
						gpXncDetail
								.setCreatedBy(result.getString("CREATED_BY"));
						gpXncDetail.setCreatedDate(createDatetime(
								TYPE_TIMESTAMP, "CREATED_DATE", result));
						gpXncDetail.setDocumentCode(result
								.getString("DOCUMENT_CODE"));
						gpXncDetail.setKey(result.getString("MACANHAN"));
						gpXncDetail.setLicenseApprvrName(result
								.getString("LICENSE_APPRVR_NAME"));
						gpXncDetail.setLicenseApprvrPstn(result
								.getString("LICENSE_APPRVR_PSTN"));
						gpXncDetail.setLicenseDate(createDatetime(
								TYPE_TIMESTAMP, "LICENSE_DATE", result));
						gpXncDetail.setLicenseExpire(createDatetime(
								TYPE_TIMESTAMP, "LICENSE_EXPIRE", result));
						gpXncDetail
								.setLicenseNo(result.getString("LICENSE_NO"));
						gpXncDetail.setLicensePoi(result
								.getString("LICENSE_POI"));
						gpXncDetail.setOrgNationality(result
								.getString("ORG_NATIONALITY"));
						gpXncDetail.setPhoneNumber(result
								.getString("PHONE_NUMBER"));
						gpXncDetail.setPurpose(result.getString("PURPOSE"));
						gpXncDetail
								.setReceiptNo(result.getString("RECEIPT_NO"));
						gpXncDetail.setResidenceCard(result
								.getString("RESIDENCE_CARD"));
						gpXncDetail.setResidenceCardDoi(result
								.getString("RESIDENCE_CARD_DOI"));
						gpXncDetail.setResidenceCardPoi(result
								.getString("RESIDENCE_CARD_POI"));
						gpXncDetail.setHoSoId(result.getDouble("HO_SO_ID"));
						listGpXncDetails.add(gpXncDetail);
					}

				}
			}
			return listGpXncDetails;
		} catch (Exception e) {
			throw e;
		} finally {
			if (conn != null) {
				conn.close();
			}
		}
	}

	@Override
	public List<TlQtDetail> getTlQtDetails(String personCode) throws Exception {
		Connection conn = null;
		List<TlQtDetail> listTlQtDetails = null;
		try {
			conn = A08DatabaseConnection.connectA08Database();
			if (conn != null) {
				CallableStatement command = conn
						.prepareCall(A08DatabaseConnection.FUNCTION_GET_TL_QT_DETAILS);
				command.registerOutParameter(1, -10);
				command.setString(2, personCode);
				command.execute();
				ResultSet result = (ResultSet) command.getObject(1);
				if (result != null) {
					listTlQtDetails = new ArrayList<TlQtDetail>();
					while (result.next()) {
						TlQtDetail tlQtDetail = new TlQtDetail();
						tlQtDetail.setMaCaNhan(result.getString("MACANHAN"));
						tlQtDetail.setName(result.getString("NAME"));
						tlQtDetail.setDateOfBirth(result.getString("DOB"));
						tlQtDetail.setGender(result.getString("GENDER"));
						tlQtDetail.setPlaceOfBirth(result
								.getString("PLACE_OF_BIRTH"));
						tlQtDetail.setAddressOfBirth(result
								.getString("ADDRESS_OF_BIRTH"));
						tlQtDetail.setHoSoId(result.getDouble("HO_SO_ID"));
						tlQtDetail.setEthnic(result.getString("ETHNIC"));
						tlQtDetail.setReceiptNo(result.getString("RECEIPT_NO"));
						tlQtDetail.setArchiveCode(result
								.getString("ARCHIVE_CODE"));
						tlQtDetail.setDocumentNo(result
								.getString("DOCUMENT_NO"));
						tlQtDetail.setDocumentDate(result
								.getString("DOCUMENT_DATE"));
						tlQtDetail.setSentOfficeName(result
								.getString("SENT_OFFICE_NAME"));
						tlQtDetail.setPassportNo(result
								.getString("PASSPORT_NO"));
						tlQtDetail.setPpDateOfIssue(result
								.getString("PP_DATE_OF_ISSUE"));
						tlQtDetail.setPpDateOfExpiry(result
								.getString("PP_DATE_OF_EXPIRY"));
						tlQtDetail.setPpPlaceOfIssue(result
								.getString("PP_PLACE_OF_ISSUE"));
						tlQtDetail.setCountryName(result
								.getString("COUNTRY_NAME"));
						tlQtDetail.setRestTmpCountryName(result
								.getString("REST_TMP_COUNTRY_NAME"));
						tlQtDetail.setResidentAddressBfExp(result
								.getString("RESIDENT_ADDRESS_BF_EXP"));
						tlQtDetail.setOccupationBfExp(result
								.getString("OCCUPATION_BF_EXP"));
						tlQtDetail.setOccupationCurrent(result
								.getString("OCCUPATION_CURRENT"));
						tlQtDetail.setWorkAddress(result
								.getString("WORK_ADDRESS"));
						tlQtDetail.setVnNatGiveupDate(result
								.getString("VN_NAT_GIVEUP_DATE"));
						tlQtDetail.setDocumentResult(result
								.getString("DOCUMENT_RESULT"));
						tlQtDetail.setVnNatGiveupReason(result
								.getString("VN_NAT_GIVEUP_REASON"));
						tlQtDetail.setExportReason(result
								.getString("EXPORT_REASON"));
						tlQtDetail.setExportDate(result
								.getString("EXPORT_DATE"));
						tlQtDetail.setComebackReason(result
								.getString("COMEBACK_REASON"));
						tlQtDetail.setVnNameBfExp(result
								.getString("VN_NAME_BF_EXP"));
						tlQtDetail.setVnNameRequest(result
								.getString("VN_NAME_REQUEST"));
						tlQtDetail.setVnChgNameReason(result
								.getString("VN_CHG_NAME_REASON"));
						int[] relation = new int[] { 1, 2, 3, 4, 8 };
						List<PersonFamily> listFamily = getPersonFamily(
								tlQtDetail.getHoSoId(), relation, "TLQT", conn);
						if (listFamily != null && listFamily.size() > 0) {
							tlQtDetail.setFamily(listFamily);
						}
						listTlQtDetails.add(tlQtDetail);
					}
				}
			}
			return listTlQtDetails;
		} catch (Exception e) {
			throw e;
		} finally {
			if (conn != null) {
				conn.close();
			}
		}
	}

	@Override
	public List<ThoiQtDetail> getThoiQtDetails(String personCode)
			throws Exception {
		Connection conn = null;
		List<ThoiQtDetail> lisThoiQtDetails = null;
		try {
			conn = A08DatabaseConnection.connectA08Database();
			if (conn != null) {
				CallableStatement command = conn
						.prepareCall(A08DatabaseConnection.FUNCTION_GET_THOI_QT_DETAILS);
				command.registerOutParameter(1, -10);
				command.setString(2, personCode);
				command.execute();
				ResultSet result = (ResultSet) command.getObject(1);
				if (result != null) {
					lisThoiQtDetails = new ArrayList<ThoiQtDetail>();
					while (result.next()) {
						ThoiQtDetail thoiQtDetail = new ThoiQtDetail();
						thoiQtDetail.setMaCaNhan(result.getString("MACANHAN"));
						thoiQtDetail.setName(result.getString("NAME"));
						thoiQtDetail.setDateOfBirth(result.getString("DOB"));
						thoiQtDetail.setGender(result.getString("GENDER"));
						thoiQtDetail.setPlaceOfBirth(result
								.getString("PLACE_OF_BIRTH"));
						thoiQtDetail.setAddressOfBirth(result
								.getString("ADDRESS_OF_BIRTH"));
						thoiQtDetail.setHoSoId(result.getDouble("HO_SO_ID"));
						thoiQtDetail.setEthnic(result.getString("ETHNIC"));
						thoiQtDetail.setCode(result.getString("CODE"));
						thoiQtDetail.setArchiveCode(result
								.getString("ARCHIVE_CODE"));
						thoiQtDetail.setDocumentNo(result
								.getString("DOCUMENT_NO"));
						thoiQtDetail.setDocumentDate(result
								.getString("DOCUMENT_DATE"));
						thoiQtDetail.setSentOfficeName(result
								.getString("SENT_OFFICE_NAME"));
						thoiQtDetail.setDocumentCount(result
								.getString("DOCUMENT_COUNT"));
						thoiQtDetail.setInvestigateBy(result
								.getString("INVESTIGATE_BY"));
						thoiQtDetail.setStateDutyFfl(result
								.getString("STATE_DUTY_FFL"));
						thoiQtDetail.setExportReason(result
								.getString("EXPORT_REASON"));
						thoiQtDetail.setExportDate(result
								.getString("EXPORT_DATE"));
						thoiQtDetail.setResidentAddress(result
								.getString("RESIDENT_ADDRESS"));
						thoiQtDetail.setAproverName(result
								.getString("APROVER_NAME"));
						thoiQtDetail.setAproverPstn(result
								.getString("APROVER_PSTN"));
						thoiQtDetail.setAproverContent(result
								.getString("APROVER_CONTENT"));
						thoiQtDetail.setCreatedBy(result
								.getString("CREATED_BY"));
						thoiQtDetail.setCreatedDate(result
								.getString("CREATED_DATE"));
						thoiQtDetail.setBlInvestBy(result
								.getString("BL_INVEST_BY"));
						thoiQtDetail.setBlInvestDate(result
								.getString("BL_INVEST_DATE"));
						thoiQtDetail.setFatherName(result
								.getString("FATHER_NAME"));
						thoiQtDetail.setFatherCountry(result
								.getString("FATHER_COUNTRY"));
						thoiQtDetail.setFatherOccupation(result
								.getString("FATHER_OCCUPATION"));
						thoiQtDetail.setFatherAddress(result
								.getString("FATHER_ADDRESS"));
						thoiQtDetail.setMotherName(result
								.getString("MOTHER_NAME"));
						thoiQtDetail.setMotherCountry(result
								.getString("MOTHER_COUNTRY"));
						thoiQtDetail.setMotherOccupation(result
								.getString("MOTHER_OCCUPATION"));
						thoiQtDetail.setMotherAddress(result
								.getString("MOTHER_ADDRESS"));
						thoiQtDetail.setCountryHome(result
								.getString("COUNTRY_HOME"));
						thoiQtDetail.setCurrResidentAddress(result
								.getString("CURR_RESIDENT_ADDRESS"));
						thoiQtDetail.setOccupation(result
								.getString("OCCUPATION"));
						thoiQtDetail.setStatus(result.getString("STATUS"));
						thoiQtDetail.setInvestigateDocNo(result
								.getString("INVESTIGATE_DOC_NO"));
						thoiQtDetail.setInvestigateDate(result
								.getString("INVESTIGATE_DATE"));
						thoiQtDetail.setResponseDocNo(result
								.getString("RESPONSE_DOC_NO"));
						thoiQtDetail.setResponseDate(result
								.getString("RESPONSE_DATE"));
						thoiQtDetail.setLocalResponseDocNo(result
								.getString("LOCAL_RESPONSE_DOC_NO"));
						thoiQtDetail.setLocalResponseDate(result
								.getString("LOCAL_RESPONSE_DATE"));
						thoiQtDetail.setViceMinisterDocNo(result
								.getString("VICE_MINISTER_DOC_NO"));
						thoiQtDetail.setViceMinisterDate(result
								.getString("VICE_MINISTER_DATE"));
						thoiQtDetail.setSuperAproverContent(result
								.getString("SUPER_APROVER_CONTENT"));
						thoiQtDetail.setNotes(result.getString("NOTES"));

						thoiQtDetail.setApproveDate(result
								.getString("APPROVE_DATE"));
						lisThoiQtDetails.add(thoiQtDetail);
					}
				}
			}
			return lisThoiQtDetails;
		} catch (Exception e) {
			throw e;
		} finally {
			if (conn != null) {
				conn.close();
			}
		}
	}

	@Override
	public List<NhanTlDetail> getNhanTlDetails(String personCode)
			throws Exception {
		Connection conn = null;
		List<NhanTlDetail> listNhanTlDetails = null;
		try {
			conn = A08DatabaseConnection.connectA08Database();
			if (conn != null) {
				CallableStatement command = conn
						.prepareCall(A08DatabaseConnection.FUNCTION_GET_NHAN_TL_DETAILS);
				command.registerOutParameter(1, -10);
				command.setString(2, personCode);
				command.execute();
				ResultSet result = (ResultSet) command.getObject(1);
				if (result != null) {
					listNhanTlDetails = new ArrayList<NhanTlDetail>();
					while (result.next()) {
						NhanTlDetail nhanTlDetail = new NhanTlDetail();
						nhanTlDetail.setMaCaNhan(result.getString("MACANHAN"));
						nhanTlDetail.setName(result.getString("NAME"));
						nhanTlDetail.setDateOfBirth(result.getString("DOB"));
						nhanTlDetail.setGender(result.getString("GENDER"));
						nhanTlDetail.setPlaceOfBirth(result
								.getString("PLACE_OF_BIRTH"));
						nhanTlDetail.setAddressOfBirth(result
								.getString("ADDRESS_OF_BIRTH"));
						nhanTlDetail.setHoSoId(result.getDouble("HO_SO_ID"));
						nhanTlDetail.setExpatriateCode(result
								.getString("EXPATRIATE_CODE"));
						nhanTlDetail.setArchiveCode(result
								.getString("ARCHIVE_CODE"));
						nhanTlDetail.setEthnic(result.getString("ETHNIC"));
						nhanTlDetail.setReligion(result.getString("RELIGION"));
						nhanTlDetail.setCmBackAddress(result
								.getString("CM_BACK_ADDRESS"));
						nhanTlDetail.setExportReason(result
								.getString("EXPORT_REASON"));
						nhanTlDetail.setResidentAddressBfExp(result
								.getString("RESIDENT_ADDRESS_BF_EXP"));
						nhanTlDetail.setDocumentType(result
								.getString("DOCUMENT_TYPE"));
						nhanTlDetail.setDocumentNo(result
								.getString("DOCUMENT_NO"));
						nhanTlDetail.setDocumentDoi(result
								.getString("DOCUMENT_DOI"));
						nhanTlDetail.setPassportNo(result
								.getString("PASSPORT_NO"));
						nhanTlDetail.setDocumentCode(result
								.getString("DOCUMENT_CODE"));
						nhanTlDetail.setDocumentDate(result
								.getString("DOCUMENT_DATE"));
						nhanTlDetail.setSentOfficeName(result
								.getString("SENT_OFFICE_NAME"));
						nhanTlDetail.setExpatriateCountry(result
								.getString("EXPATRIATE_COUNTRY"));
						nhanTlDetail.setOvrseaDocList(result
								.getString("OVRSEA_DOC_LIST"));
						nhanTlDetail.setAprvrDocList(result
								.getString("APRVR_DOC_LIST"));
						nhanTlDetail.setCmBackPreList(result
								.getString("CM_BACK_PRE_LIST"));
						nhanTlDetail.setCmBackReaList(result
								.getString("CM_BACK_REA_LIST"));
						nhanTlDetail.setCmBackDate(result
								.getString("CM_BACK_DATE"));
						nhanTlDetail.setBlInvestBy(result
								.getString("BL_INVEST_BY"));
						nhanTlDetail.setBlInvestDate(result
								.getString("BL_INVEST_DATE"));
						nhanTlDetail.setCreatedBy(result
								.getString("CREATED_BY"));
						nhanTlDetail.setCreatedDate(result
								.getString("CREATED_DATE"));
						nhanTlDetail.setProcessStep(result
								.getString("PROCESS_STEP"));
						nhanTlDetail.setBussDeptCmmt(result
								.getString("BUSS_DEPT_CMMT"));
						nhanTlDetail.setNotes(result.getString("NOTES"));
						nhanTlDetail.setVerifiedResult(result
								.getString("VERIFIED_RESULT"));
						nhanTlDetail.setAprvrCompliment(result
								.getString("APRVR_COMPLIMENT"));
						nhanTlDetail.setVerifiedDocNo(result
								.getString("VERIFIED_DOC_NO"));
						nhanTlDetail.setVerifiedDocDate(result
								.getString("VERIFIED_DOC_DATE"));
						nhanTlDetail.setLocalResponseDocNo(result
								.getString("LOCAL_RESPONSE_DOC_NO"));
						nhanTlDetail.setLocalResponseDate(result
								.getString("LOCAL_RESPONSE_DATE"));
						nhanTlDetail.setDsqDocCode(result
								.getString("DSQ_DOC_CODE"));
						nhanTlDetail.setDsqDocDate(result
								.getString("DSQ_DOC_DATE"));
						nhanTlDetail.setThongHanhDate(result
								.getString("THONG_HANH_DATE"));
						nhanTlDetail.setThongHanhNo(result
								.getString("THONG_HANH_NO"));
						nhanTlDetail.setThongHanhNoiCap(result
								.getString("THONG_HANH_NOI_CAP"));
						int[] relation = new int[] { 1, 2, 3, 4, 8 };
						List<PersonFamily> listFamily = getPersonFamily(
								nhanTlDetail.getHoSoId(), relation, "HH", conn);
						if (listFamily != null && listFamily.size() > 0) {
							nhanTlDetail.setFamily(listFamily);
						}
						listNhanTlDetails.add(nhanTlDetail);
					}
				}
			}
			return listNhanTlDetails;
		} catch (Exception e) {
			throw e;
		} finally {
			if (conn != null) {
				conn.close();
			}
		}
	}

	@Override
	public List<XmnsDetail> getXmnsDetails(String personCode) throws Exception {
		Connection conn = null;
		List<XmnsDetail> listXmnsDetails = null;
		try {
			conn = A08DatabaseConnection.connectA08Database();
			if (conn != null) {
				CallableStatement command = conn
						.prepareCall(A08DatabaseConnection.FUNCTION_GET_XMNS_DETAILS);
				command.registerOutParameter(1, -10);
				command.setString(2, personCode);
				command.execute();
				ResultSet result = (ResultSet) command.getObject(1);
				if (result != null) {
					listXmnsDetails = new ArrayList<XmnsDetail>();
					while (result.next()) {
						XmnsDetail xmnsDetail = new XmnsDetail();
						xmnsDetail.setPersonCode(result.getString("MACANHAN"));
						xmnsDetail.setName(result.getString("NAME"));
						xmnsDetail.setDob(result.getString("DOB"));
						xmnsDetail.setGender(result.getString("GENDER"));
						xmnsDetail.setPlaceOfBirth(result
								.getString("PLACE_OF_BIRTH"));
						xmnsDetail.setAddressOfBirth(result
								.getString("ADDRESS_OF_BIRTH"));
						xmnsDetail.setEthnic(result.getString("ETHNIC"));
						xmnsDetail.setReceiptNo(result.getString("RECEIPT_NO"));
						xmnsDetail.setReceiptDate(createDatetime(TYPE_TIMESTAMP, "RECEIPT_DATE", result));
						xmnsDetail
								.setIoDocCode(result.getString("IO_DOC_CODE"));
						xmnsDetail.setIoDocDateOfIssue(result
								.getString("IO_DOC_DATE_OF_ISSUE"));
						xmnsDetail.setIoDocPlaceOfIssue(result
								.getString("IO_DOC_PLACE_OF_ISSUE"));
						xmnsDetail.setResidentPlaceName(result
								.getString("RESIDENT_PLACE_NAME"));
						xmnsDetail.setResidentAddress(result
								.getString("RESIDENT_ADDRESS"));
						xmnsDetail.setResidentCountry(result
								.getString("RESIDENT_COUNTRY"));
						xmnsDetail.setExportReason(result
								.getString("EXPORT_REASON"));
						xmnsDetail.setPassportReqReason(result
								.getString("PASSPORT_REQ_REASON"));
						xmnsDetail.setDescription(result
								.getString("DESCRIPTION"));
						xmnsDetail.setDocumentResult(result
								.getString("DOCUMENT_RESULT"));
						xmnsDetail.setCreatedBy(result.getString("CREATED_BY"));
						xmnsDetail.setCreatedDate(result
								.getString("CREATED_DATE"));
						xmnsDetail.setBlInvestBy(result
								.getString("BL_INVEST_BY"));
						xmnsDetail.setBlInvestDate(result
								.getString("BL_INVEST_DATE"));
						xmnsDetail.setModifyInfoBy(result
								.getString("MODIFY_INFO_BY"));
						xmnsDetail.setModifyInfoDate(result
								.getString("MODIFY_INFO_DATE"));
						xmnsDetail.setRequestType(result
								.getString("REQUEST_TYPE"));
						xmnsDetail.setDocumentType(result
								.getString("DOCUMENT_TYPE"));
						xmnsDetail.setVrfDocumentNo(result
								.getString("VRF_DOCUMENT_NO"));
						xmnsDetail.setVrfDocumentDate(result
								.getString("VRF_DOCUMENT_DATE"));
						xmnsDetail.setCancelPpListCode(result
								.getString("CANCEL_PP_LIST_CODE"));
						xmnsDetail.setVerifiedResult(result
								.getString("VERIFIED_RESULT"));
						xmnsDetail
								.setaListCode(result.getString("A_LIST_CODE"));
						xmnsDetail
								.setaListDate(result.getString("A_LIST_DATE"));
						xmnsDetail.setApprvListCode(result
								.getString("APPRV_LIST_CODE"));
						xmnsDetail.setApprvListDate(result
								.getString("APPRV_LIST_DATE"));
						xmnsDetail.setDocumentStatus(result
								.getString("DOCUMENT_STATUS"));
						xmnsDetail.setDsqDocCode(result
								.getString("DSQ_DOC_CODE"));
						xmnsDetail.setDsqDocDate(result
								.getString("DSQ_DOC_DATE"));
						xmnsDetail.setA27DocCode(result
								.getString("A27_DOC_CODE"));
						xmnsDetail.setApproveBy(result.getString("APPROVE_BY"));
						xmnsDetail.setApproveDate(result
								.getString("APPROVE_DATE"));
						xmnsDetail.setDocumentNo(result
								.getString("DOCUMENT_NO"));
						xmnsDetail.setDocumentDate(result
								.getString("DOCUMENT_DATE"));
						xmnsDetail.setSentOfficeName(result
								.getString("SENT_OFFICE_NAME"));
						xmnsDetail.setDocNo(result.getString("DOC_NO"));
						xmnsDetail.setRestTmpCountryName(result
								.getString("REST_TMP_COUNTRY_NAME"));
						xmnsDetail.setLocalResponseDocNo(result
								.getString("LOCAL_RESPONSE_DOC_NO"));
						xmnsDetail.setLocalResponseDate(result
								.getString("LOCAL_RESPONSE_DATE"));
						xmnsDetail.setRootPersonMapDate(result
								.getString("ROOT_PERSON_MAP_DATE"));
						xmnsDetail.setUpdatedBy(result.getString("UPDATED_BY"));
						xmnsDetail.setUpdatedDate(result
								.getString("UPDATED_DATE"));
						xmnsDetail.setHoSoId(result.getDouble("HO_SO_ID"));
						xmnsDetail
								.setIoDocType(result.getString("IO_DOC_TYPE"));
						int[] relation = new int[] { 1, 2, 3, 4, 8 };
						List<PersonFamily> listFamily = getPersonFamily(
								xmnsDetail.getHoSoId(), relation, "NS", conn);
						if (listFamily != null && listFamily.size() > 0) {
							xmnsDetail.setFamily(listFamily);
						}
						listXmnsDetails.add(xmnsDetail);
					}
				}
			}
			return listXmnsDetails;
		} catch (Exception e) {
			throw e;
		} finally {
			if (conn != null) {
				conn.close();
			}
		}
	}

	@Override
	public List<VkHhDetail> getVkHhDetails(String personCode) throws Exception {
		Connection conn = null;
		List<VkHhDetail> listVkHhDetails = null;
		try {
			conn = A08DatabaseConnection.connectA08Database();
			if (conn != null) {
				CallableStatement command = conn
						.prepareCall(A08DatabaseConnection.FUNCTION_GET_VK_HH_DETAILS);
				command.registerOutParameter(1, -10);
				command.setString(2, personCode);
				command.execute();
				ResultSet result = (ResultSet) command.getObject(1);
				if (result != null) {
					listVkHhDetails = new ArrayList<VkHhDetail>();
					while (result.next()) {
						VkHhDetail vkHhDetail = new VkHhDetail();
						vkHhDetail.setMaCaNhan(result.getString("MACANHAN"));
						vkHhDetail.setDocumentNo(result
								.getString("DOCUMENT_NO"));
						vkHhDetail.setDocumentDate(result
								.getString("DOCUMENT_DATE"));
						vkHhDetail.setSentOfficeName(result
								.getString("SENT_OFFICE_NAME"));
						vkHhDetail.setHoSoId(result.getDouble("HO_SO_ID"));
						vkHhDetail.setReceiptNo(result.getString("RECEIPT_NO"));
						vkHhDetail.setReligion(result.getString("RELIGION"));
						vkHhDetail
								.setOccupation(result.getString("OCCUPATION"));
						vkHhDetail.setWorkAddress(result
								.getString("WORK_ADDRESS"));
						vkHhDetail.setRestTmpCountryName(result
								.getString("REST_TMP_COUNTRY_NAME"));
						vkHhDetail.setResidentAddressBfExp(result
								.getString("RESIDENT_ADDRESS_BF_EXP"));
						vkHhDetail.setIoDocCode(result.getString("IO_DOC_CODE"));
						vkHhDetail
								.setIoDocType(result.getString("IO_DOC_TYPE"));
						vkHhDetail.setIoDocDateOfIssue(result
								.getString("IO_DOC_DATE_OF_ISSUE"));
						vkHhDetail.setIoDocDateOfExpiry(result
								.getString("IO_DOC_DATE_OF_EXPIRY"));
						vkHhDetail.setIoDocPlaceOfIssue(result
								.getString("IO_DOC_PLACE_OF_ISSUE"));
						vkHhDetail.setVnWorkShortDesc(result
								.getString("VN_WORK_SHORT_DESC"));
						vkHhDetail.setHouseOccpFile(result
								.getString("HOUSE_OCCP_FILES"));
						vkHhDetail.setComebackReason(result
								.getString("COMEBACK_REASON"));
						vkHhDetail.setDescription(result
								.getString("DESCRIPTION"));
						vkHhDetail.setSponsorName(result
								.getString("SPONSOR_NAME"));
						vkHhDetail.setSponsorRelationship(result
								.getString("SPONSOR_RELATIONSHIP"));
						vkHhDetail.setSponsorGender(result
								.getString("SPONSOR_GENDER"));
						vkHhDetail.setSponsorDob(result
								.getString("SPONSOR_DOB"));
						vkHhDetail.setSponsorIdNumber(result
								.getString("SPONSOR_ID_NUMBER"));
						vkHhDetail.setSponsorResPlace(result
								.getString("SPONSOR_RES_PLACE"));
						vkHhDetail.setSponsorResAddress(result
								.getString("SPONSOR_RES_ADDRESS"));
						vkHhDetail.setBlInvestBy(result
								.getString("BL_INVEST_BY"));
						vkHhDetail.setBlInvestDate(result
								.getString("BL_INVEST_DATE"));
						vkHhDetail.setAproverName(result
								.getString("APROVER_NAME"));
						vkHhDetail.setArchiveCode(result
								.getString("ARCHIVE_CODE"));
						vkHhDetail.setCreatedDate(result
								.getString("CREATED_DATE"));
						vkHhDetail.setExportReason(result
								.getString("EXPORT_REASON"));
						vkHhDetail.setExportDate(result
								.getString("EXPORT_DATE"));
						vkHhDetail.setExportType(result
								.getString("EXPORT_TYPE"));
						vkHhDetail.setSponsorIdDate(result
								.getString("SPONSOR_ID_DATE"));
						vkHhDetail.setSponsorIdPlace(result
								.getString("SPONSOR_ID_PLACE"));
						vkHhDetail.setSponsorOccupation(result
								.getString("SPONSOR_OCCUPATION"));
						vkHhDetail.setSponsorWorkAdress(result
								.getString("SPONSOR_WORK_ADRESS"));
						vkHhDetail.setStatus(result.getString("STATUS"));
						vkHhDetail.setAproverDate(result
								.getString("APROVED_DATE"));
						vkHhDetail.setSponsorDate(result
								.getString("SPONSOR_DATE"));
						vkHhDetail.setProposalBy(result
								.getString("PROPOSAL_BY"));
						vkHhDetail.setProposalDate(result
								.getString("PROPOSAL_DATE"));

						List<PersonFamily> listF = null;
						CallableStatement commandFamily = conn
								.prepareCall(A08DatabaseConnection.FUNCTION_GET_VK_HH_TE_DETAILS);
						commandFamily.registerOutParameter(1, -10);
						commandFamily.setString(2, vkHhDetail.getReceiptNo());
						commandFamily.execute();
						ResultSet resultFamily = (ResultSet) commandFamily
								.getObject(1);
						if (resultFamily != null) {
							listF = new ArrayList<PersonFamily>();
							while (resultFamily.next()) {
								PersonFamily person = new PersonFamily();
								person.setDateOfBirth(resultFamily
										.getString("NGAY_SINH_TE"));
								person.setName(resultFamily.getString("TEN_TE"));
								person.setGender(convertToGender(resultFamily
										.getString("GIOI_TINH_TE")));
								
								person.setRelationship(resultFamily
										.getString("QHGD_TE"));
								person.setTransactionId(resultFamily
										.getString("SO_MAY_TINH_CHINH"));
								listF.add(person);
							}
						}
						if (commandFamily != null) {
							commandFamily.close();
						}
						if (resultFamily != null) {
							resultFamily.close();
						}
						if (listF != null && listF.size() > 0) {
							vkHhDetail.setFamily(listF);
						}

						listVkHhDetails.add(vkHhDetail);
					}
				}
			}
			return listVkHhDetails;
		} catch (Exception e) {
			throw e;
		} finally {
			if (conn != null) {
				conn.close();
			}
		}
	}

	@Override
	public List<HsVpDetail> getHsVpDetails(String personCode) throws Exception {
		Connection conn = null;
		List<HsVpDetail> listHsVpDetails = null;
		try {
			conn = A08DatabaseConnection.connectA08Database();
			if (conn != null) {
				CallableStatement command = conn
						.prepareCall(A08DatabaseConnection.FUNCTION_GET_HSVP_DETAILS);
				command.registerOutParameter(1, -10);
				command.setString(2, personCode);
				command.execute();
				ResultSet result = (ResultSet) command.getObject(1);
				if (result != null) {
					listHsVpDetails = new ArrayList<HsVpDetail>();
					while (result.next()) {
						HsVpDetail hsVpDetail = new HsVpDetail();
						hsVpDetail.setSoDangKy(result.getString("SO_DANG_KY"));
						hsVpDetail.setDonViLapId(result
								.getDouble("DON_VI_LAP_ID"));
						hsVpDetail.setChiTietDonViLap(result
								.getString("CHI_TIET_DON_VI_LAP"));
						hsVpDetail.setNguoiDuyet(result
								.getString("NGUOI_DUYET"));
						hsVpDetail.setChucVuNguoiDuyet(result
								.getString("CHUC_VU_NGUOI_DUYET"));
						hsVpDetail.setCapBacNguoiDuyet(result
								.getString("CAP_BAC_NGUOI_DUYET"));
						hsVpDetail
								.setCanBoQlhs(result.getString("CAN_BO_QLHS"));
						hsVpDetail.setNgayPhatHien(createDatetime(
								TYPE_DATETIME, "NGAY_PHAT_HIEN", result));
						hsVpDetail.setNgayDangKy(createDatetime(TYPE_DATETIME,
								"NGAY_DANG_KY", result));
						hsVpDetail.setMaNoiXayRa(result
								.getString("MA_NOI_XAY_RA"));
						hsVpDetail.setNoiXayRa(result.getString("NOI_XAY_RA"));
						hsVpDetail
								.setTenVuViec(result.getString("TEN_VU_VIEC"));
						hsVpDetail.setNguoiToChucThietHai(result
								.getString("NGUOI_TO_CHUC_THIET_HAI"));
						hsVpDetail.setNgayChuyenCqDieuTra(createDatetime(
								TYPE_DATETIME, "NGAY_CHUYEN_CQ_DIEU_TRA",
								result));
						hsVpDetail.setCoQuanDieuTra(result
								.getString("CO_QUAN_DIEU_TRA"));
						hsVpDetail.setNgayKetThuc(createDatetime(TYPE_DATETIME,
								"NGAY_KET_THUC", result));
						hsVpDetail.setMaLyDoKetThuc(result
								.getString("MA_LY_DO_KET_THUC"));
						hsVpDetail.setLyDoKetThuc(result
								.getString("LY_DO_KET_THUC"));
						hsVpDetail.setSoLuu(result.getString("SO_LUU"));
						hsVpDetail.setNgayNopLuu(createDatetime(TYPE_DATETIME,
								"NGAY_NOP_LUU", result));
						hsVpDetail.setGhiChu(result.getString("GHI_CHU"));

						listHsVpDetails.add(hsVpDetail);
					}
				}
			}
			return listHsVpDetails;
		} catch (Exception e) {
			throw e;
		} finally {
			if (conn != null) {
				conn.close();
			}
		}
	}

	@Override
	public List<CnVpDetail> getCnVpDetails(String personCode) throws Exception {
		Connection conn = null;
		List<CnVpDetail> listCnVpDetails = null;
		try {
			conn = A08DatabaseConnection.connectA08Database();
			if (conn != null) {
				CallableStatement command = conn
						.prepareCall(A08DatabaseConnection.FUNCTION_GET_CN_VP_DETAILS);
				command.registerOutParameter(1, -10);
				command.setString(2, personCode);
				command.execute();
				ResultSet result = (ResultSet) command.getObject(1);
				if (result != null) {
					listCnVpDetails = new ArrayList<CnVpDetail>();
					while (result.next()) {
						CnVpDetail cnVpDetail = new CnVpDetail();
						cnVpDetail.setMaCaNhan(result.getString("MACANHAN"));
						cnVpDetail.setNguoiViPham(result
								.getString("NGUOI_VI_PHAM"));
						cnVpDetail.setGioiTinhNvp(result
								.getString("GIOI_TINH_NVP"));
						cnVpDetail.setNgaySinhNvp(result
								.getString("NGAY_SINH_NVP"));
						cnVpDetail.setNgheNghiepNvp(result
								.getString("NGHE_NGHIEP_NVP"));
						cnVpDetail
								.setDiaChiNvp(result.getString("DIA_CHI_NVP"));
						cnVpDetail.setSoHcNvp(result.getString("SO_HC_NVP"));
						cnVpDetail.setLoaiGiayToNvp(result
								.getString("LOAI_GIAY_TO_NVP"));
						cnVpDetail.setCqCapGiayToNvp(result
								.getString("CQ_CAP_GIAY_TO_NVP"));
						cnVpDetail.setNgayCapHcNvp(result
								.getString("NGAY_CAP_HC_NVP"));
						cnVpDetail.setQuocTichNvp(result
								.getString("QUOC_TICH_NVP"));
						cnVpDetail
								.setSoBienBan(result.getString("SO_BIEN_BAN"));
						cnVpDetail.setThoiDiemLap(result
								.getString("THOI_DIEM_LAP"));
						cnVpDetail.setHsViPhamId(result
								.getDouble("HS_VI_PHAM_ID"));
						cnVpDetail.setDonViLapId(result
								.getDouble("DON_VI_LAP_ID"));
						cnVpDetail.setChiTietDonViLap(result
								.getString("CHI_TIET_DON_VI_LAP"));
						cnVpDetail.setHanhViViPham(result
								.getString("HANH_VI_VI_PHAM"));
						cnVpDetail.setNguoiLapBbvp(result
								.getString("NGUOI_LAP_BBVP"));
						cnVpDetail.setCvuNguoiLapBbvp(result
								.getString("CVU_NGUOI_LAP_BBVP"));
						cnVpDetail.setNguoiLamChung(result
								.getString("NGUOI_LAM_CHUNG"));
						cnVpDetail.setNgheNghiepNlc(result
								.getString("NGHE_NGHIEP_NLC"));
						cnVpDetail
								.setDiaChiNlc(result.getString("DIA_CHI_NLC"));
						listCnVpDetails.add(cnVpDetail);
					}
				}
			}
			return listCnVpDetails;
		} catch (Exception e) {
			throw e;
		} finally {
			if (conn != null) {
				conn.close();
			}
		}
	}

	@Override
	public List<DoiTuongDetail> getDoiTuongDetails(String personCode)
			throws Exception {
		Connection conn = null;
		List<DoiTuongDetail> listDoiTuongDetails = null;
		try {
			conn = A08DatabaseConnection.connectA08Database();
			if (conn != null) {
				CallableStatement command = conn
						.prepareCall(A08DatabaseConnection.FUNCTION_GET_DOI_TUONG_DETAILS);
				command.registerOutParameter(1, -10);
				command.setString(2, personCode);
				command.execute();
				ResultSet result = (ResultSet) command.getObject(1);
				if (result != null) {
					listDoiTuongDetails = new ArrayList<DoiTuongDetail>();
					while (result.next()) {
						DoiTuongDetail doiTuongDetail = new DoiTuongDetail();
						doiTuongDetail.setDiaChiThuongTru(result
								.getString("DIA_CHI_THUONG_TRU"));
						doiTuongDetail.setNoiLamViec(result
								.getString("NOI_LAM_VIEC"));
						doiTuongDetail.setNoiOHienNay(result
								.getString("NOI_O_HIEN_NAY"));
						doiTuongDetail.setQueQuan(result.getString("QUE_QUAN"));
						doiTuongDetail.setTenDonViDangKy(result
								.getString("TEN_DON_VI_DANG_KY"));
						doiTuongDetail.setNguoiKyCv(result
								.getString("NGUOI_KY_CV"));
						doiTuongDetail.setNguoiDuyetGiaiToa(result
								.getString("NGUOI_DUYET_GIAI_TOA"));
						doiTuongDetail.setChucVuGiaiToa(result
								.getString("CHUC_VU_GIAI_TOA"));
						doiTuongDetail.setDanToc(result.getString("DAN_TOC"));
						doiTuongDetail.setDcCanBaoTin(result
								.getString("DC_CAN_BAO_TIN"));
						doiTuongDetail.setDoiTuongId(result
								.getDouble("DOI_TUONG_ID"));
						doiTuongDetail.setDoiTuongTtiDangKyId(result
								.getDouble("DOI_TUONG_TTI_DANG_KY_ID"));
						doiTuongDetail.setDonViDangKy(result
								.getString("DON_VI_DANG_KY"));
						doiTuongDetail.setDvCanBaoTin(result
								.getString("DV_CAN_BAO_TIN"));
						doiTuongDetail.setGhiChu(result.getString("GHI_CHU"));
						doiTuongDetail.setGioiTinh(result
								.getString("GIOI_TINH"));
						doiTuongDetail.setLoaiDoiTuong(result
								.getString("LOAI_DOI_TUONG"));
						doiTuongDetail.setLyDoDangKy(result
								.getString("LY_DO_DANG_KY"));
						doiTuongDetail.setLyDoGiaiToa(result
								.getString("LY_DO_GIAI_TOA"));
						doiTuongDetail.setNgayCvDangKy(result
								.getString("NGAY_CV_DANG_KY"));
						doiTuongDetail.setNgayDangKy(result
								.getString("NGAY_DANG_KY"));
						doiTuongDetail.setNgayGiaiToa(result
								.getString("NGAY_GIAI_TOA"));
						doiTuongDetail.setNgayHetHan(result
								.getString("NGAY_HET_HAN"));
						doiTuongDetail.setNgayNhapMay(result
								.getString("NGAY_NHAP_MAY"));
						doiTuongDetail.setNgaySinh(createDatetime(
								TYPE_DATETIME, "NGAY_SINH", result));
						doiTuongDetail.setNgheNghiep(result
								.getString("NGHE_NGHIEP"));
						doiTuongDetail.setNguoiCanBaoTin(result
								.getString("NGUOI_CAN_BAO_TIN"));
						// doiTuongDetail.setNguoiDangKyCv(result.getString("NGUOI_DANG_KY_CV"));
						doiTuongDetail.setNguoiNhapMay(result
								.getString("NGUOI_NHAP_MAY"));
						doiTuongDetail.setNoiSinh(result.getString("NOI_SINH"));
						doiTuongDetail.setQuocTichGoc(result
								.getString("QUOC_TICH_GOC"));
						doiTuongDetail.setQuocTichHn(result
								.getString("QUOC_TICH_HN"));
						doiTuongDetail.setSoCvDangKy(result
								.getString("SO_CV_DANG_KY"));
						doiTuongDetail.setSoCvGiaiToa(result
								.getString("SO_CV_GIAI_TOA"));
						doiTuongDetail.setSoDangKy(result
								.getString("SO_DANG_KY"));
						doiTuongDetail.setTelBaoTin(result
								.getString("TEL_BAO_TIN"));
						doiTuongDetail.setTenChinh(result
								.getString("TEN_CHINH"));
						doiTuongDetail.setTonGiao(result.getString("TON_GIAO"));
						doiTuongDetail.setXuatNhap(result
								.getString("XUAT_NHAP"));
						doiTuongDetail.setNguoiDuyet(result.getString("NGUOI_DUYET"));
						if (result.getBlob("ANH") != null) {
							String anh = Base64.encode(result.getBytes("ANH"));
							doiTuongDetail.setAnh(anh);
						}
						
						List<PersonFamily> personFamilies = null;
						CallableStatement commandTn = conn
								.prepareCall(A08DatabaseConnection.FUNCTION_GET_DOI_TUONG_TN_DETAILS);
						commandTn.registerOutParameter(1, -10);
						commandTn.setDouble(2, doiTuongDetail.getDoiTuongId());
						commandTn.execute();
						ResultSet resultTn = (ResultSet) commandTn.getObject(1);
						if (resultTn != null) {
							personFamilies = new ArrayList<PersonFamily>();
							while (resultTn.next()) {
								PersonFamily personFamily = new PersonFamily();
								personFamily.setName(resultTn.getString("TEN"));
								personFamily.setDateOfBirth(resultTn.getString("NGAY_SINH"));
								personFamily.setGender(resultTn.getString("GIOI_TINH"));
								personFamily.setAddress(resultTn.getString("DIA_CHI"));
								personFamily.setRelationship(resultTn.getString("QUAN_HE_GD"));
								personFamilies.add(personFamily);
							}
						}
						if (commandTn != null) {
							commandTn.close();
						}
						if (resultTn != null) {
							resultTn.close();
						}
						if (personFamilies != null && personFamilies.size() > 0) {
							doiTuongDetail.setFamily(personFamilies);
						}
						
						listDoiTuongDetails.add(doiTuongDetail);
					}
				}
			}
			return listDoiTuongDetails;
		} catch (Exception e) {
			throw e;
		} finally {
			if (conn != null) {
				conn.close();
			}
		}
	}

	@Override
	public List<LSCapHoChieu> getLsCapHoChieus(String personCode)
			throws Exception {
		Connection conn = null;
		List<LSCapHoChieu> listLSCapHoChieus = null;
		try {
			conn = A08DatabaseConnection.connectA08Database();
			if (conn != null) {
				CallableStatement command = conn
						.prepareCall(A08DatabaseConnection.FUNCTION_GET_HC_DETAILS);
				command.registerOutParameter(1, -10);
				command.setString(2, personCode);
				command.execute();
				ResultSet result = (ResultSet) command.getObject(1);
				if (result != null) {
					listLSCapHoChieus = new ArrayList<LSCapHoChieu>();
					while (result.next()) {
						LSCapHoChieu lsCapHoChieu = new LSCapHoChieu();
						lsCapHoChieu
								.setApplicant(result.getString("APPLICANT"));
						lsCapHoChieu.setApxOrgPerson(result
								.getString("APX_ORG_PERSON"));
						lsCapHoChieu.setApxPersonCode(result
								.getString("APX_PERSON_CODE"));
						lsCapHoChieu.setArchiveCode(result
								.getString("ARCHIVE_CODE"));
						lsCapHoChieu.setBlInvestBy(result
								.getString("BL_INVEST_BY"));
						lsCapHoChieu.setBlInvestDate(result
								.getString("BL_INVEST_DATE"));
						lsCapHoChieu.setcListCode(result
								.getString("C_LIST_CODE"));
						lsCapHoChieu.setcListCrtBy(result
								.getString("C_LIST_CRT_BY"));
						lsCapHoChieu.setcListCrtDate(result
								.getString("C_LIST_CRT_DATE"));
						lsCapHoChieu.setCreatedBy(result
								.getString("CREATED_BY"));
						lsCapHoChieu.setCreatedDate(result
								.getString("CREATED_DATE"));
						lsCapHoChieu.setDateOfDelivery(result
								.getString("DATE_OF_DELIVERY"));
						lsCapHoChieu.setDeliveryNote(result
								.getString("DELIVERY_NOTE"));
						lsCapHoChieu.setDeliveryOfficeName(result
								.getString("DELIVERY_OFFICE_NAME"));
						// lsCapHoChieu.setDescription(result
						// .getString("DESCRIPTION"));
						lsCapHoChieu.setDocumentDate(createDatetime(
								TYPE_TIMESTAMP, "DOCUMENT_DATE", result));
						lsCapHoChieu.setDocumentNo(result
								.getString("DOCUMENT_NO"));
						lsCapHoChieu.setIsEpassport(result
								.getString("IS_EPASSPORT"));
						lsCapHoChieu.setOccupation(result
								.getString("OCCUPATION"));
						lsCapHoChieu.setOfficeInfo(result
								.getString("OFFICE_INFO"));
						lsCapHoChieu.setOfficeName(result
								.getString("OFFICE_NAME"));
						lsCapHoChieu.setPassportNo(result
								.getString("PASSPORT_NO"));
						lsCapHoChieu.setPhoneNo(result.getString("PHONE_NO"));
						lsCapHoChieu.setPpDateOfExpiry(result
								.getString("PP_DATE_OF_EXPIRY"));
						lsCapHoChieu.setPpDateOfIssue(result
								.getString("PP_DATE_OF_ISSUE"));
						lsCapHoChieu.setPpIcaoLine1(result
								.getString("PP_ICAO_LINE1"));
						lsCapHoChieu.setPpIcaoLine2(result
								.getString("PP_ICAO_LINE2"));
						lsCapHoChieu.setPpPlaceOfIssue(result
								.getString("PP_PLACE_OF_ISSUE"));
						lsCapHoChieu.setPpSerialNo(result
								.getString("PP_SERIAL_NO"));
						lsCapHoChieu.setPpStatus(result.getString("PP_STATUS"));
						lsCapHoChieu.setPpType(result.getString("PP_TYPE_"));
						lsCapHoChieu.setPrevDateOfIssue(createDatetime(
								TYPE_TIMESTAMP, "PREV_DATE_OF_ISSUE", result));
						lsCapHoChieu.setPrevPassportNo(result
								.getString("PREV_PASSPORT_NO"));
						lsCapHoChieu.setPriority(result.getString("PRIORITY"));
						lsCapHoChieu.setProposalAprvrContent(result
								.getString("PROPOSAL_APRVR_CONTENT"));
						lsCapHoChieu.setProposalAprvrDate(result
								.getString("PROPOSAL_APRVR_DATE"));
						lsCapHoChieu.setProposalAprvrName(result
								.getString("PROPOSAL_APRVR_NAME"));
						lsCapHoChieu.setProposalAprvrPstn(result
								.getString("PROPOSAL_APRVR_PSTN"));
						lsCapHoChieu.setProposalBy(result
								.getString("PROPOSAL_BY"));
						lsCapHoChieu.setProposalContent(result
								.getString("PROPOSAL_CONTENT"));
						lsCapHoChieu.setProposalDate(createDatetime(
								TYPE_TIMESTAMP, "PROPOSAL_DATE", result));
						lsCapHoChieu.setProposalType(result
								.getString("PROPOSAL_TYPE_"));
						lsCapHoChieu.setReceiptNo(result
								.getString("RECEIPT_NO"));
						lsCapHoChieu
								.setRecipient(result.getString("RECIPIENT"));
						lsCapHoChieu.setResidentAddress(result
								.getString("RESIDENT_ADDRESS"));
						lsCapHoChieu.setResidentAreaName(result
								.getString("RESIDENT_AREA_NAME"));
						lsCapHoChieu.setResidentPlaceName(result
								.getString("RESIDENT_PLACE_NAME"));
						lsCapHoChieu.setSentOfficeAddr(result
								.getString("SENT_OFFICE_ADDRESS"));
						lsCapHoChieu.setSentOfficeName(result
								.getString("SENT_OFFICE_NAME"));
						lsCapHoChieu.setStatus(result.getString("STATUS"));
						lsCapHoChieu.setTmpAddress(result
								.getString("TEMP_ADDRESS"));
						lsCapHoChieu.setTmpAreaName(result
								.getString("TEMP_AREA_NAME"));
						lsCapHoChieu.setTmpPlaceName(result
								.getString("TEMP_PLACE_NAME"));
						lsCapHoChieu.setTransactionId(result.getString("CODE"));
						lsCapHoChieu.setType(result.getString("TYPE_"));

						// bo sung
						lsCapHoChieu.setaListCode(result
								.getString("A_LIST_CODE"));
						lsCapHoChieu.setaListCrtBy(result
								.getString("A_LIST_CRT_BY"));
						lsCapHoChieu.setaListCrtDate(result
								.getString("A_LIST_CRT_DATE"));
						lsCapHoChieu.setAddressOfBirth(result
								.getString("ADDRESS_OF_BIRTH"));
						lsCapHoChieu.setbListCode(result
								.getString("B_LIST_CODE"));
						lsCapHoChieu.setbListCrtBy(result
								.getString("B_LIST_CRT_BY"));
						lsCapHoChieu.setbListCrtDate(result
								.getString("B_LIST_CRT_DATE"));
						lsCapHoChieu.setDateOfBirth(result.getString("DOB"));
						lsCapHoChieu.setEthnic(result.getString("ETHNIC"));
						lsCapHoChieu.setFullName(result.getString("FULLNAME"));
						lsCapHoChieu.setGender(result.getString("GENDER"));
						lsCapHoChieu.setIssueBy(result.getString("ISSUE_BY"));
						lsCapHoChieu.setIssueDate(result
								.getString("ISSUE_DATE"));
						lsCapHoChieu.setNote(result.getString("NOTE"));
						lsCapHoChieu.setPackNo(result.getString("PACK_NO"));
						if (result.getBytes("PHOTO") != null) {
							lsCapHoChieu.setPhoto(Base64.encode(result
									.getBytes("PHOTO")));
						}
						lsCapHoChieu.setPid(result.getString("PID"));
						lsCapHoChieu.setPlaceOfBirth(result
								.getString("PLACE_OF_BIRTH"));
						lsCapHoChieu.setPlaceOfIssuePid(result
								.getString("PLACE_OF_ISSUE_PID"));
						lsCapHoChieu.setReceiveBy(result
								.getString("RECEIVE_BY"));
						lsCapHoChieu.setHoSoId(result.getDouble("HO_SO_ID"));
						lsCapHoChieu.setBuocXl(result.getString("DOC_STEP"));
						
						lsCapHoChieu.setCancelBy(result.getString("CANCEL_BY"));
						lsCapHoChieu.setCancelDate(createDatetime(
								TYPE_TIMESTAMP, "CANCEL_DATE", result));
						lsCapHoChieu.setCancelReason(result.getString("CANCEL_REASON"));
						lsCapHoChieu.setDateOfIssuePid(result.getString("DATE_OF_ISSUE_PID"));
						lsCapHoChieu.setCancelType(this.convertCancelType(result.getString("CANCEL_TYPE")));
						lsCapHoChieu.setPrintDate(this.createDatetime(TYPE_TIMESTAMP, "PP_PRINT_DATE", result));
						lsCapHoChieu.setPrintName(result.getString("PP_PRINT_NAME"));
						
						List<DecAttachment> listAtt = null;
						// Bổ sung lấy chi tiết ABTC_APPROVE_DETAIL.
						CallableStatement commandTkhc = conn
								.prepareCall(A08DatabaseConnection.FUNCTION_GET_TKHC_DETAILS);
						commandTkhc.registerOutParameter(1, -10);
						commandTkhc.setString(2,
								lsCapHoChieu.getTransactionId());
						commandTkhc.execute();
						ResultSet resultTkhc = (ResultSet) commandTkhc
								.getObject(1);
						if (resultTkhc != null) {
							listAtt = new ArrayList<DecAttachment>();
							while (resultTkhc.next()) {
								DecAttachment decAtt = new DecAttachment();
								if (resultTkhc.getBytes("ANH_TO_KHAI") != null) {
									decAtt.setAnhToKhai(Base64
											.encode(resultTkhc
													.getBytes("ANH_TO_KHAI")));
								}
								decAtt.setMaHoSo(resultTkhc
										.getString("MA_HO_SO"));
								decAtt.setMaLoaiToKhai(resultTkhc
										.getString("MA_LOAI_TO_KHAI"));
								decAtt.setSoTrang(resultTkhc
										.getString("SO_TRANG"));
								listAtt.add(decAtt);
							}
						}
						if (commandTkhc != null) {
							commandTkhc.close();
						}
						if (resultTkhc != null) {
							resultTkhc.close();
						}
						if (listAtt != null && listAtt.size() > 0) {
							lsCapHoChieu.setListAttachments(listAtt);
						}
						int[] relation = new int[] { 1, 2, 3, 4 };
						List<PersonFamily> listFamily = getPersonFamily(
								lsCapHoChieu.getHoSoId(), relation, "HC", conn);
						if (listFamily != null && listFamily.size() > 0) {
							lsCapHoChieu.setFamily(listFamily);
						}

						listLSCapHoChieus.add(lsCapHoChieu);
					}
				}
			}
			return listLSCapHoChieus;
		} catch (Exception e) {
			throw e;
		} finally {
			if (conn != null) {
				conn.close();
			}
		}
	}

	@Override
	public List<DoiTuong> getDoiTuongs(String fullname, String dateOfBirth,
			String dateOBType, String country, String gender,
			String passportNo, String identityCardNo) throws Exception {
		Connection conn = null;
		List<DoiTuong> listDoituongs = null;
		try {
			conn = A08DatabaseConnection.connectA08Database();
			if (conn != null) {
				CallableStatement command = conn
						.prepareCall(A08DatabaseConnection.FUNCTION_TRA_CUU_DOI_TUONG_EPP);
				command.registerOutParameter(1, -10);
				command.setString(2, fullname);
				command.setString(3, dateOfBirth);
				command.setString(4, dateOBType);
				command.setString(5, country);
				command.setString(6, gender);
				command.setString(7, passportNo);
				command.setString(8, identityCardNo);
				command.execute();
				ResultSet result = (ResultSet) command.getObject(1);
				if (result != null) {
					listDoituongs = new ArrayList<DoiTuong>();
					while (result.next()) {
						DoiTuong doiTuong = new DoiTuong();
						doiTuong.setAddress(result.getString("ADDRESS"));
						doiTuong.setCurrentNationalityId(result
								.getLong("CURRENT_NATIONALITY_ID"));
						doiTuong.setDateOfBirth(result
								.getString("DATE_OF_BIRTH"));
						doiTuong.setEthNic(result.getString("ETHNIC"));
						doiTuong.setExpire(result.getString("EXPIRE_"));
						doiTuong.setGender(result.getString("GENDER"));
						doiTuong.setIdNumber(result.getString("ID_NUMBER"));
						doiTuong.setKey(result.getDouble("KEY_"));
						doiTuong.setName(result.getString("NAME"));
						doiTuong.setNickName(result.getString("NICK_NAME"));
						doiTuong.setNote(result.getString("NOTE"));
						doiTuong.setOriginNationalityId(result
								.getLong("ORIGIN_NATIONALITY_ID"));
						doiTuong.setPlaceOfBirthName(result
								.getString("PLACE_OF_BIRTH_NAME"));
						doiTuong.setPpNumber(result.getString("PP_NUMBER"));
						doiTuong.setRegisteredNo(result
								.getString("REGISTERED_NO"));
						doiTuong.setReligion(result.getString("RELIGION"));
						doiTuong.setType(result.getString("TYPE_"));

						listDoituongs.add(doiTuong);
					}
				}
			}
			return listDoituongs;
		} catch (Exception e) {
			throw e;
		}finally {
			if (conn != null) {
				conn.close();
			}
		}
	}

	@Override
	public int checkCaNhanHsXnc(String personCode, String type)
			throws Exception {
		Connection conn = null;
		int count = 0;
		try {
			conn = A08DatabaseConnection.connectA08Database();
			if (conn != null) {
				CallableStatement command = conn
						.prepareCall(A08DatabaseConnection.FUNCTION_CHECK_CA_NHAN_HS_XNC);
				command.registerOutParameter(1, 2);
				command.setString(2, personCode);
				command.setString(3, type);
				command.execute();
				count = command.getInt(1);
				
				if (command != null) {
					command.close();
				}
			}
			return count;
		} catch (Exception e) {
			throw e;
		} finally {
			if (conn != null) {
				conn.close();
			}
		}
	}

	@Override
	public List<ImmigrationHistoryDetail> getImmigrationHistoryDetail(
			String personCode) throws Exception {
		Connection conn = null;
		List<ImmigrationHistoryDetail> listImmigHDetail = null;
		try {
			conn = A08DatabaseConnection.connectA08Database();
			if (conn != null) {
				CallableStatement command = conn
						.prepareCall(A08DatabaseConnection.FUNCTION_GET_HS_XNC_DETAILS);
				command.registerOutParameter(1, -10);
				command.setString(2, personCode);
				command.execute();
				ResultSet result = (ResultSet) command.getObject(1);
				if (result != null) {
					listImmigHDetail = new ArrayList<ImmigrationHistoryDetail>();
					while (result.next()) {
						ImmigrationHistoryDetail iHDetail = new ImmigrationHistoryDetail();
						iHDetail.setImmiId(result.getLong("IMMI_ID"));
						iHDetail.setImmiType(result.getString("IMMI_TYPE"));
						iHDetail.setFullName(result.getString("FULLNAME"));
						iHDetail.setCountryCode(result
								.getString("COUNTRY_CODE"));
						iHDetail.setCountryName(result
								.getString("COUNTRY_NAME"));
						iHDetail.setGender(result.getString("GENDER"));
						iHDetail.setPassportExpiredDate(createDatetime(
								TYPE_TIMESTAMP, "PASSPORT_EXPIRED_DATE", result));
						iHDetail.setPassportNo(result.getString("PASSPORT_NO"));
						iHDetail.setPassportType(result
								.getString("PASSPORT_TYPE"));
						iHDetail.setDateOfBirth(result
								.getString("DATE_OF_BIRTH"));
						iHDetail.setVisano(result.getString("VISANO"));
						iHDetail.setVisaType(result.getString("VISA_TYPE"));
						iHDetail.setFlightNo(result.getString("FLIGHT_NO"));
						iHDetail.setFlightPath(result.getString("FLIGHT_PATH"));
						iHDetail.setFlightFrom(result.getString("FLIGHT_FROM"));
						iHDetail.setFlightTo(result.getString("FLIGHT_TO"));
						iHDetail.setReason(result.getString("REASON"));
						iHDetail.setTemporaryplace(result
								.getString("TEMPORARYPLACE"));
						iHDetail.setBorderGate(result.getString("BORDER_GATE"));
						String immi = result.getString("IMMIGRATION_DATE");
						if (StringUtils.isNotBlank(immi)) {
							String[] dateTime = immi.split(" ");
							String date = dateTime[0];
							String time = dateTime[1];
							String[] timeArr = time.split(":");
							time = timeArr[0] + timeArr[1] + timeArr[2];
							String ppEDate = date + time;
							iHDetail.setImmigrationDate(ppEDate);
						}
						iHDetail.setIoGateNo(result.getString("IO_GATE_NO"));
						// iHDetail.setNationality(result.getString("NATIONALITY"));
						iHDetail.setOrgNationality(result
								.getString("ORG_NATIONALITY"));
						iHDetail.setVnResAddress(result
								.getString("VN_RES_ADDRESS"));
						iHDetail.setFrResAddress(result
								.getString("FR_RES_ADDRESS"));
						iHDetail.setOccupation(result.getString("OCCUPATION"));
						iHDetail.setPassportPoi(result
								.getString("PASSPORT_POI"));
						iHDetail.setNumberOfChild(result
								.getInt("NUMBER_OF_CHILD"));
						iHDetail.setVisaSymbol(result.getString("VISA_SYMBOL"));
						iHDetail.setVisaDate(result.getString("VISA_DATE"));
						iHDetail.setOfficeToVisit(result
								.getString("OFFICE_TO_VISIT"));
						iHDetail.setNote(result.getString("NOTE"));
						iHDetail.setGateuserName(result
								.getString("GATEUSER_NAME"));
						iHDetail.setSupervisorName(result
								.getString("SUPERVISOR_NAME"));
						if (result.getBlob("PHOTO") != null) {
							iHDetail.setPhoto(Base64.encode(result
									.getBytes("PHOTO")));
						}
						iHDetail.setPlaceOfBirth(result
								.getString("PLACE_OF_BIRTH"));
						iHDetail.setVisaPlaceOfIssue(result
								.getString("VISA_PLACE_OF_ISSUE"));
						iHDetail.setImmiNo(result.getString("IMMI_NO"));
						iHDetail.setIsChecked(result.getString("IS_CHECKED"));
						iHDetail.setExpectedExport(result
								.getString("EXPECTED_EXPORT"));
						
						List<PersonFamily> childs = null;
						CallableStatement cmChild = conn
								.prepareCall(A08DatabaseConnection.FUNCTION_GET_HS_XNC_TE_DETAILS);
						cmChild.registerOutParameter(1, -10);
						cmChild.setLong(2, iHDetail.getImmiId());
						cmChild.execute();
						ResultSet rsChild = (ResultSet) cmChild.getObject(1);
						if (rsChild != null) {
							childs = new ArrayList<PersonFamily>();
							while (rsChild.next()) {
								PersonFamily child = new PersonFamily();
								child.setId(rsChild.getString("ID"));
								child.setRelationship(rsChild.getString("QUAN_HE"));
								child.setName(rsChild.getString("TEN"));
								child.setDateOfBirth(rsChild.getString("NGAY_SINH"));
								child.setGender(rsChild.getString("GIOI_TINH"));
								child.setPlaceOfBirth(rsChild.getString("NOI_SINH"));
								childs.add(child);
							}
						}
						if (cmChild != null) {
							cmChild.close();
						}
						if (rsChild != null) {
							rsChild.close();
						}
						if (childs != null && childs.size() > 0) {
							iHDetail.setChilds(childs);
						}
						listImmigHDetail.add(iHDetail);
					}
				}
			}
			return listImmigHDetail;
		} catch (Exception e) {
			throw e;
		} finally {
			if (conn != null) {
				conn.close();
			}
		}
	}

	@Override
	public List<DocumentCancelDetail> getDocumentCancelDetail(String fullname,
			String dateOfBirth, String dateOBType, String country,
			String gender, String passportNo, String identityCardNo)
			throws Exception {
		Connection conn = null;
		List<DocumentCancelDetail> list = null;
		try {
			conn = A08DatabaseConnection.connectA08Database();
			if (conn != null) {
				CallableStatement command = conn
						.prepareCall(A08DatabaseConnection.FUNCTION_TRA_CUU_GIAY_TO_MAT);
				command.registerOutParameter(1, -10);
				command.setString(2, fullname);
				command.setString(3, dateOfBirth);
				command.setString(4, dateOBType);
				command.setString(5, country);
				command.setString(6, gender);
				command.setString(7, passportNo);
				command.setString(8, identityCardNo);
				command.execute();
				ResultSet result = (ResultSet) command.getObject(1);
				if (result != null) {
					list = new ArrayList<DocumentCancelDetail>();
					while (result.next()) {
						DocumentCancelDetail docCancelDetail = new DocumentCancelDetail();
						docCancelDetail.setGiayToId(result
								.getString("GIAY_TO_ID"));
						docCancelDetail.setLoaiGtoXNC(result
								.getString("LOAI_GTO_XNC"));
						docCancelDetail.setSoGto(result.getString("SO_GTO"));
						docCancelDetail
								.setLoaiGto(result.getString("LOAI_GTO"));
						docCancelDetail
								.setSeriGto(result.getString("SERI_GTO"));
						docCancelDetail.setNgayCap(createDatetime(
								TYPE_TIMESTAMP, "NGAY_CAP", result));
						docCancelDetail.setTen(result.getString("TEN"));
						docCancelDetail.setTenKD(result.getString("TEN_KD"));
						docCancelDetail.setGioiTinh(result
								.getString("GIOI_TINH"));
						docCancelDetail.setNgaySinh(createDatetime(
								TYPE_TIMESTAMP, "NGAY_SINH", result));
						docCancelDetail.setCqDangKy(result
								.getString("CQ_DANG_KY"));
						docCancelDetail.setNgayDangKy(createDatetime(
								TYPE_TIMESTAMP, "NGAY_DANG_KY", result));
						docCancelDetail.setTrangThai(result
								.getString("TRANG_THAI"));
						docCancelDetail.setNguoiDuyet(result
								.getString("NGUOI_DUYET"));
						docCancelDetail.setQuocTich(result
								.getString("QUOC_TICH_ID"));
						docCancelDetail.setNoiCap(result.getString("NOI_CAP"));
						docCancelDetail.setMatHetGiaTri(result
								.getString("MAT_HET_GIA_TRI"));
						docCancelDetail.setGhiChu(result.getString("GHI_CHU"));
						docCancelDetail.setNgayNhapMay(createDatetime(
								TYPE_TIMESTAMP, "NGAY_NHAP_MAY", result));
						docCancelDetail.setNguoiNhapMay(result
								.getString("NGUOI_NHAP_MAY"));
						docCancelDetail.setNgayBoSung(createDatetime(
								TYPE_TIMESTAMP, "NGAY_BO_SUNG", result));
						docCancelDetail.setNguoiBoSung(result
								.getString("NGUOI_BO_SUNG"));

						list.add(docCancelDetail);
					}
				}
			}
			return list;
		} catch (Exception e) {
			throw e;
		} finally {
			if (conn != null) {
				conn.close();
			}
		}
	}

	@Override
	public List<InfoCMT> getInfoCmnd(String ip, String idNumber)
			throws Exception {
		List<InfoCMT> listInfoCmnd = null;
		try {
			/* Kết nối tới API */
			URL url = new URL(ip + "api/InvestigationCMND?pid=" + idNumber);
			HttpURLConnection con = (HttpURLConnection) url.openConnection();
			con.setRequestProperty("Content-Type",
					"application/json; charset=UTF-8");
			BufferedReader buff = new BufferedReader(new InputStreamReader(
					con.getInputStream(), "UTF-8"));
			if (con.getResponseCode() != 200) {
				con.disconnect();
				throw new Exception("Lỗi khi truy vấn dữ liệu.");
			}
			String out = buff.readLine();
			JSONArray jsonArray = new JSONArray(out);
			if (jsonArray.length() > 0) {
				listInfoCmnd = new ArrayList<InfoCMT>();
				for (int i = 0; i < jsonArray.length(); i++) {
					/* Đọc dữ liệu Json nếu có */
					JSONObject jsonObj = jsonArray.getJSONObject(i);
					InfoCMT infoCMT = new InfoCMT();
					infoCMT.setNguyenQuan(jsonObj.getString("nguyenQuan"));
					infoCMT.setHoTen(jsonObj.getString("hoTen"));
					infoCMT.setTenAnhSau(jsonObj.getString("tenAnhSau"));
					infoCMT.setMaDanToc(jsonObj.getString("maDanToc"));
					infoCMT.setMaNgheNghiep(jsonObj.getString("maNgheNghiep"));
					infoCMT.setMaGioiTinh(jsonObj.getString("maGioiTinh"));
					infoCMT.setSoCMNDCu(jsonObj.getString("soCMNDCu"));
					infoCMT.setHoTenVC(jsonObj.getString("hoTenVC"));
					infoCMT.setMaHocVan(jsonObj.getString("maHocVan"));
					infoCMT.setThonPhoTT(jsonObj.getString("thonPhoTT"));
					infoCMT.setSoNhaTT(jsonObj.getString("soNhaTT"));
					infoCMT.setMaPXTT(jsonObj.getString("maPXTT"));
					infoCMT.setMaDPCap(jsonObj.getString("maDPCap"));
					infoCMT.setTenKhac(jsonObj.getString("tenKhac"));
					infoCMT.setCmId(jsonObj.getString("cmId"));
					infoCMT.setImgID(jsonObj.getString("imgID"));
					infoCMT.setLinkAnhSau(jsonObj.getString("linkAnhSau"));
					infoCMT.setTenAnhTruoc(jsonObj.getString("tenAnhTruoc"));
					infoCMT.setDauVetRieng(jsonObj.getString("dauVetRieng"));
					infoCMT.setNgaySinh(jsonObj.getString("ngaySinh"));
					infoCMT.setLinkAnhTruoc(jsonObj.getString("linkAnhTruoc"));
					infoCMT.setMaTonGiao(jsonObj.getString("maTonGiao"));
					infoCMT.setNgayKhai(jsonObj.getString("ngayKhai"));
					infoCMT.setBase64AnhTruoc(jsonObj
							.getString("base64anhTruoc"));
					infoCMT.setNoiSinh(jsonObj.getString("noiSinh"));
					infoCMT.setBase64AnhSau(jsonObj.getString("base64anhSau"));
					infoCMT.setSoCMND(jsonObj.getString("soCMND"));
					infoCMT.setMaDPTT(jsonObj.getString("maDPTT"));
					infoCMT.setNoiLamViec(jsonObj.getString("noiLamViec"));
					infoCMT.setHoTenMe(jsonObj.getString("hoTenMe"));
					infoCMT.setMaDP(jsonObj.getString("maDP"));
					infoCMT.setThuMuc(jsonObj.getString("thuMuc"));
					infoCMT.setMaDPNQ(jsonObj.getString("maDPNQ"));
					infoCMT.setNamSinh(jsonObj.getString("namSinh"));
					infoCMT.setCtvt1(jsonObj.getString("ctvt1"));
					infoCMT.setHoTenCha(jsonObj.getString("hoTenCha"));
					infoCMT.setCtvt2(jsonObj.getString("ctvt2"));
					infoCMT.setNgayCapCu(jsonObj.getString("ngayCapCu"));

					listInfoCmnd.add(infoCMT);
				}
			}

			buff.close();
			con.disconnect();
		} catch (Exception e) {
			throw new Exception(e);
		}
		return listInfoCmnd;
	}
	

	@Override
	public List<PassportStatus> getListPassportStatus(String passportNo)
			throws Exception {
		Connection conn = null;
		List<PassportStatus> list = null;
		try {
			conn = A08DatabaseConnection.connectA08Database();
			if (conn != null) {
				CallableStatement command = conn
						.prepareCall(A08DatabaseConnection.FUNCTION_GET_HC_STATUS_DETAILS);
				command.registerOutParameter(1, -10);
				command.setString(2, passportNo);
				command.execute();
				ResultSet result = (ResultSet) command.getObject(1);
				if (result != null) {
					list = new ArrayList<PassportStatus>();
					while (result.next()) {
						PassportStatus passportStatus = new PassportStatus();
						passportStatus.setPassportNo(result.getString("PASSPORT_NO"));
						passportStatus.setDateOfIssue(createDatetime(TYPE_TIMESTAMP, "DATE_OF_ISSUE", result));
						passportStatus.setDateOfExpiry(createDatetime(TYPE_TIMESTAMP, "DATE_OF_EXPIRE", result));
						passportStatus.setStatus(createPassportStatus(result.getString("STATUS"), result.getDate("DATE_OF_EXPIRE")));
						passportStatus.setCancelType(convertCancelType(result.getString("CANCEL_TYPE")));
						list.add(passportStatus);
					}
				}
				if (command != null) {
					command.close();
				}
				if (result != null) {
					result.close();
				}
			}
			return list;
		} catch (Exception e) {
			throw e;
		} finally {
			if (conn != null) {
				conn.close();
			}
		}
	}
	
	//convert Status passport A08
	private String createPassportStatus(String string, Date dateOfExpiry) {
		if (StringUtils.isNotBlank(string)) {
			if (string.equals("Hiện hành")) {
				if (dateOfExpiry != null && dateOfExpiry.getTime() > new Date().getTime()) {
					string = NicDocumentData.DOCUMENT_DATA_STATUS_ISSUANCE;
				} else {
					string = NicDocumentData.DOCUMENT_DATA_STATUS_EXPIRED;
				}
			}else{
				string = NicDocumentData.DOCUMENT_DATA_STATUS_CANCELLED;
			}
		}
		return string;
	}

	//convert cancelType A08
	private String convertCancelType(String cancelType){
		if (StringUtils.isNotBlank(cancelType)) {
			if (cancelType.equals("IN_HONG")) {
				cancelType = "PRINT_FAIL";
			}else if (cancelType.equals("BAO_MAT")) {
				cancelType = "LOST";
			}else if (cancelType.equals("BI_HUY")) {
				cancelType = "DAMAGE";
			}else if (cancelType.equals("DONG_DAU_HUY")) {
				cancelType = "RENEW";
			}else if (cancelType.equals("THU_HOI")) {
				cancelType = "REVOKED";
			}else if (cancelType.equals("DIA_PHUONG_TRA_VE") || cancelType.equals("HC_TRA_LAI")) {
				cancelType = "RETURNED";
			}
		}
		return cancelType;
	}

	// convert các kiểu dữ liệu thời gian về định dạng "yyyyMMdd"
	private String createDatetime(String type, String key, ResultSet result) {
		try {
			switch (type) {
			case TYPE_TIMESTAMP:
				if (result.getTimestamp(key) != null) {
					String ppEDate = DateUtil.parseDate(
							result.getTimestamp(key), DateUtil.FORMAT_YYYYMMDD);
					return ppEDate;
				}

			case TYPE_DATETIME:
				if (StringUtils.isNotBlank(result.getString(key))) {
					Date date = DateUtil.strToDate(result.getString(key),
							DateUtil.FORMAT_DD_MM_YYYY);
					String dateStr = DateUtil.parseDate(date,
							DateUtil.FORMAT_YYYYMMDD);
					return dateStr;
				}
			default:
			}
		} catch (Exception e) {
		}
		return "";
	}

	// convert relationshipCode to relationship
	private String createRelationship(int i) {
		switch (i) {
		case 1:
			return "bố";
		case 2:
			return "mẹ";
		case 3:
			return "vợ";
		case 4:
			return "chồng";
		case 8:
			return "con";
		default:
			return null;
		}
	}

	//lấy dữ liệu nhân thân qua function từ A08
	private List<PersonFamily> getPersonFamily(Double hoSoId, int[] relation, String loaiHsXnc,
			Connection conn) {
		List<PersonFamily> list = null;
		try {
			list = new ArrayList<PersonFamily>();
			for (int i : relation) {
				CallableStatement commandFamily = conn
						.prepareCall(A08DatabaseConnection.FUNCTION_GET_THAN_NHAN_DETAILS);
				commandFamily.registerOutParameter(1, -10);
				commandFamily.setDouble(2, hoSoId);
				commandFamily.setString(3, loaiHsXnc);
				commandFamily.setInt(4, i);
				commandFamily.execute();
				ResultSet resultFamily = (ResultSet) commandFamily.getObject(1);
				if (resultFamily != null) {
					while (resultFamily.next()) {
						PersonFamily person = new PersonFamily();
						person.setDateOfBirth(createDatetime(TYPE_DATETIME,
								"NGAY_SINH", resultFamily));
						person.setName(resultFamily.getString("TEN"));
						person.setGender(resultFamily.getString("GIOI_TINH"));
						person.setPersonCode(resultFamily
								.getString("MA_CA_NHAN"));
						person.setRelationship(createRelationship(i));
						list.add(person);
					}
				}
				if (commandFamily != null) {
					commandFamily.close();
				}
				if (resultFamily != null) {
					resultFamily.close();
				}
			}
		} catch (Exception e) {
		}
		return list;
	}
	
	//convert sex to code
	private String convertToGender(String sex) {
		if (StringUtils.isNotBlank(sex)) {
			switch (sex) {
			case "Nam":
				return "M";
			case "Nữ":
				return "F";
			}
		}
		return "";
	}

}
