package com.nec.asia.nic.comp.a08database.connect;

import java.sql.Connection;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

public class A08DatabaseConnection {
	
	public static final String GET_ABTC_DETAILS = "{Call PA18.PK_GET_RECORD_DETAILS.SF_GET_ABTC_DETAILS (?, ?) }";
	public static final String GET_GP_XNC_DETAILS = "{Call sp_get_gp_xnc_details (?. ?) }";
	public static final String GET_TL_QT_DETAILS = "{Call sp_get_tl_qt_details (?, ?) }";
	public static final String GET_THOI_QT_DETAILS = "{Call sp_get_thoi_qt_details (?, ?) }";
	public static final String GET_NHAN_TL_DETAILS = "{Call sp_get_nhan_tl_details (?, ?) }";
	public static final String GET_XMNS_DETAILS = "{Call sp_get_xmns_details (?, ?) }";
	public static final String GET_VK_HH_DETAILS = "{Call sp_get_vh_hh_details (?, ?) }";
	public static final String GET_HSVP_DETAILS = "{Call sp_get_hsvp_details (?, ?) }";
	public static final String GET_CN_VP_DETAILS = "{Call sp_get_cn_vp_details (?, ?) }";
	public static final String GET_DOI_TUONG_DETAILS = "{Call sp_get_doi_tuong_details (?, ?) }";
	public static final String GET_TEST = "{? = call EPP_CENTRAL.PKG_TEST.sf_search_queue_out_tbl(?) }";
	public static final String GET_SO_SANH_CA_NHAN = "{ ? = call PA18.PK_KHOP_CA_NHAN.SF_SO_SANH_CA_NHAN_EPP(?,?,?,?,?,?,?,?) }";;
	
	public static final String FUNCTION_GET_ABTC_DETAILS = "{ ? = call PA18.PK_GET_RECORD_DETAILS.SF_GET_ABTC_DETAILS(?) }";
	public static final String FUNCTION_GET_ABTC_APPROVE_DETAILS = "{ ? = call PA18.PK_GET_RECORD_DETAILS.SF_GET_ABTC_APPROVE_DETAILS(?) }";
	public static final String FUNCTION_GET_CN_VP_DETAILS = "{ ? = call PA18.PK_GET_RECORD_DETAILS.SF_GET_CN_VP_DETAILS(?) }";
	public static final String FUNCTION_GET_DOI_TUONG_DETAILS = "{ ? = call PA18.PK_GET_RECORD_DETAILS.SF_GET_DOI_TUONG_DETAILS(?) }";
	public static final String FUNCTION_GET_DOI_TUONG_TN_DETAILS = "{ ? = call PA18.PK_GET_RECORD_DETAILS.SF_GET_DOI_TUONG_TN_DETAILS(?) }";
	public static final String FUNCTION_GET_GP_XNC_DETAILS = "{ ? = call PA18.PK_GET_RECORD_DETAILS.SF_GET_GP_XNC_DETAILS(?) }";
	public static final String FUNCTION_GET_HC_DETAILS = "{ ? = call PA18.PK_GET_RECORD_DETAILS.SF_GET_HC_DETAILS(?) }";
	public static final String FUNCTION_GET_HC_STATUS_DETAILS = "{ ? = call PA18.PK_GET_RECORD_DETAILS.SF_GET_HC_STATUS_DETAILS(?) }";
	public static final String FUNCTION_GET_TKHC_DETAILS = "{ ? = call PA18.PK_GET_RECORD_DETAILS.SF_GET_TKHC_DETAILS(?) }";
	public static final String FUNCTION_GET_THAN_NHAN_DETAILS = "{ ? = call PA18.PK_GET_RECORD_DETAILS.SF_GET_THAN_NHAN_DETAILS(?, ?, ?) }";
	public static final String FUNCTION_GET_HSVP_DETAILS = "{ ? = call PA18.PK_GET_RECORD_DETAILS.SF_GET_HSVP_DETAILS(?) }";
	public static final String FUNCTION_GET_NHAN_TL_DETAILS = "{ ? = call PA18.PK_GET_RECORD_DETAILS.SF_GET_NHAN_TL_DETAILS(?) }";
	public static final String FUNCTION_GET_THOI_QT_DETAILS = "{ ? = call PA18.PK_GET_RECORD_DETAILS.SF_GET_THOI_QT_DETAILS(?) }";
	public static final String FUNCTION_GET_TL_QT_DETAILS = "{ ? = call PA18.PK_GET_RECORD_DETAILS.SF_GET_TL_QT_DETAILS(?) }";
	public static final String FUNCTION_GET_VK_HH_DETAILS = "{ ? = call PA18.PK_GET_RECORD_DETAILS.SF_GET_VH_HH_DETAILS(?) }";
	public static final String FUNCTION_GET_VK_HH_TE_DETAILS = "{ ? = call PA18.PK_GET_RECORD_DETAILS.SF_GET_VH_HH_TE_DETAILS(?) }";
	public static final String FUNCTION_GET_XMNS_DETAILS = "{ ? = call PA18.PK_GET_RECORD_DETAILS.SF_GET_XMNS_DETAILS(?) }";
	public static final String FUNCTION_GET_HS_XNC_DETAILS = "{ ? = call PA18.PK_GET_RECORD_DETAILS.SF_GET_HS_XNC_DETAILS(?) }";
	public static final String FUNCTION_GET_HS_XNC_TE_DETAILS = "{ ? = call PA18.PK_GET_RECORD_DETAILS.SF_GET_HS_XNC_TE_DETAILS(?) }";
	
	public static final String FUNCTION_TRA_CUU_DOI_TUONG_EPP = "{ ? = call PA18.PK_TRA_CUU_DOI_TUONG.SF_TRA_CUU_DOI_TUONG_EPP(?, ?, ?, ?, ?, ?, ?) }";
	public static final String FUNCTION_CHECK_CA_NHAN_HS_XNC = "{ ? = call PA18.SF_CHECK_CA_NHAN_HS_XNC(?, ?) }";
	public static final String FUNCTION_TRA_CUU_GIAY_TO_MAT = "{ ? = call PA18.PK_TRA_CUU_GIAY_TO_HUY.SF_TRA_CUU_GIAY_TO_MAT(?, ?, ?, ?, ?, ?, ?) }";
	
	public A08DatabaseConnection() {
	}
	
//	public static Connection conn;

	public static Connection connectA08Database(){
//		if (conn == null) {
		Connection conn = null;
			try {
				String jndiName = "java:/a08Datasource";
					Context ctx = new InitialContext();
					DataSource ds = (DataSource) ctx.lookup(jndiName);
					if (ds != null) {
						conn = ds.getConnection();
						if (conn != null) {
							return conn;
						}
					}
			} catch (Exception e) {
				e.printStackTrace();
			}
//		}
		return conn;
	}
	
	public static Connection connectDatabaseTest(){
		Connection conn = null;
		try {
			String jndiName = "java:/eppDatasource";
				Context ctx = new InitialContext();
				DataSource ds = (DataSource) ctx.lookup(jndiName);
				if (ds != null) {
					conn = ds.getConnection();
					if (conn != null) {
						return conn;
					}
				}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return conn;
	}
}
