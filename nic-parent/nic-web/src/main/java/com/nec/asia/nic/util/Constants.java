/**
 * 
 */
package com.nec.asia.nic.util;

/**
 * @author prasad_karnati
 *
 */
/* 
 * Modification History:
 * 
 * 23 Sep 2013 (chris): fix compile issue.
 * 22 Jan 2015 (Tue): change all titles NIC to EPP
 */
public class Constants {
	public static final String TRANSACTION_TYPE = "LOẠI GIAO DỊCH";
	public static final String TRANSACTION_SUBTYPE = "KIỂU GIAO DỊCH";
	public static final String HEADING_NIC_MAIN = "TRUNG TÂM ĐIỀU HÀNH VÀ XỬ LÝ HỒ SƠ";
	public static final String HEADING_NIC_LOGOUT = "ĐĂNG XUẤT HỆ THỐNG";
	public static final String HEADING_NIC_WELCOME = "TRUNG TÂM ĐIỀU HÀNH VÀ XỬ LÝ HỒ SƠ";
	public static final String HEADING_NIC_INVESTIGATION = "DANH SÁCH XỬ LÝ HỒ SƠ";
	public static final String HEADING_NIC_SYNC = "DANH SÁCH HỘ CHIẾU PHÁT HÀNH";
	
	public static final String HEADING_NIC_INVESTIGATION_SUSPENDED = "DANH SÁCH ĐIỀU TRA BỊ ĐÌNH CHỈ";
	public static final String HEADING_NIC_INVESTIGATION_ASSIGNED = "DANH SÁCH ĐIỀU TRA ĐƯỢC CHỈ ĐỊNH";
	public static final String HEADING_NIC_INVESTIGATION_SUSPENDED_ASSIGNED = "DANH SÁCH ĐÃ PHÂN CÔNG";
	public static final String HEADING_NIC_INVESTIGATION_ASSIGNMENT = "DANH SÁCH CHỜ PHÂN CÔNG";
	public static final String HEADING_NIC_INVESTIGATION_ASSIGNMENT_DETAIL = "CHI TIẾT PHIẾU BÀN GIAO";
	public static final String HEADING_NIC_INVESTIGATION_SUSPENDED_ASSIGNMENT = "ePassport Suspended Investigation Assignment List";
	public static final String HEADING_NIC_SUPERVISOR = "DANH SÁCH HỒ SƠ BỊ TỪ CHỐI";
	public static final String HEADING_NIC_ADMINCON = "BẢNG ĐIỀU KHIỂN QUẢN TRỊ";
	public static final String HEADING_NIC_REPORTS = "BÁO CÁO";
	public static final String HEADING_NIC_BATCHJOBS = "QUẢN LÝ CÔNG VIỆC";
	public static final String HEADING_NIC_BATCHJOBS_APP = "NHẬT KÝ CÔNG VIỆC";
	public static final String HEADING_NIC_BATCHJOB_SCHE = "LỊCH TRÌNH CÔNG VIỆC HÀNG LOẠT";
	public static final String HEADING_NIC_CODE = "QUẢN LÝ DANH MỤC HỆ THỐNG";
	public static final String HEADING_NIC_CODE_VALUE = "ePassport Code Value";
	public static final String HEADING_NIC_REPORT_MGMT = "QUẢN LÝ BÁO CÁO";
	public static final String HEADING_NIC_REPORT_GEN = "ePassport Report Generate Menu";
	public static final String HEADING_NIC_PROOF_DOC = "ePassport Proof Document Matrix";
	public static final String HEADING_NIC_PAYMENT_MATRIX = "QUẢN LÝ THANH TOÁN";
	public static final String HEADING_NIC_SIGN_MATRIX = "QUẢN LÝ CHỮ KÝ";
	public static final String HEADING_NIC_DECISIONMANAGER_MATRIX = "QUẢN LÝ QUYẾT ĐỊNH";
	public static final String HEADING_NIC_OFFICALNATION_MATRIX = "QUẢN LÝ CÔNG HÀM";
	public static final String HEADING_NIC_OFFICALVISA_MATRIX = "QUẢN LÝ QUỐC GIA MIỄN THỊ THỰC";
	public static final String HEADING_NIC_PARAM_MGMT = "CẤU HÌNH THÔNG SỐ HỆ THỐNG";
	public static final String HEADING_NIC_USER_MANAGEMENT = "QUẢN LÝ NGƯỜI DÙNG";
	public static final String HEADING_NIC_ROLE_MANAGEMENT = "QUẢN LÝ PHÂN QUYỀN";
	public static final String HEADING_NIC_WORKSTATION_MANAGEMENT = "QUẢN LÝ MÁY TRẠM";
	public static final String SYSTEM_ID = "MÃ HỆ THỐNG";
	public static final String CODE_ID_SYSTEM_ID = "SYSTEM_ID";
	public static final String HEADING_NIC_TRANS_ENQUIRY = "TRA CỨU GIAO DỊCH";
	//add 
	public static final String HEADING_NIC_LIST_LOGWS = "TRA CỨU NHẬT KÝ ĐỒNG BỘ";
	public static final String HEADING_NIC_PASSPORT_ENQUIRY = "TRA CỨU HỘ CHIẾU";
	public static final String HEADING_NIC_JOB_ENQUIRY = "TRA CỨU CÔNG VIỆC";
	public static final String REPORT_CATEGORY = "DANH MỤC BÁO CÁO";
	public static final String HEADING_NIC_PKD = "PKD";
	public static final String NO = "N";
	public static final String YES = "Y";
	//public static final Object HEADING_NIC_TRANS_ENQUIRY = "ePassport TRANSACTION ENQUIRY";
	public static final int PAGE_SIZE_DEFAULT = 10;
	//public static final String HEADING_NIC_TRANS_ENQUIRY="HEADING_NIC_TRANS_ENQUIRY";
	public static final Object HEADING_NIC_CHANGE_PASSWORD = "THAY ĐỔI MẬT KHẨU";
	public static final String HEADING_NIC_AUDIT_ENQUIRY = "NHẬT KÝ HỆ THỐNG";
	public static final String HEADING_NIC_USER_SESSION_ENQUIRY = "NHẬT KÝ NGƯỜI DÙNG";
	public static final String HEADING_NIC_REPRINT = "ePassport Reprint";
	public static final Object HEADING_DATA_SYNC_MONITORING = "GIÁM SÁT ĐỒNG BỘ DỮ LIỆU";
	public static final String HEADING_EPP_SITE_MANAGEMENT = "QUẢN LÝ CÁC TRUNG TÂM";
	
	//TRUNG THÊM CHO DANH SÁCH XÉT DUYỆT
	public static final String HEADING_NIC_INVESTIGATION_BOSS = "DANH SÁCH XÉT DUYỆT";
	//TRUNG THÊM CHO DANH SÁCH XÁC THỰC TỪ BỘ CÔNG AN
	public static final String HEADING_NIC_INVESTIGATION_BCA = "DANH SÁCH XÁC MINH BỘ CÔNG AN";
	//TRUNG THÊM CHO DANH SÁCH CHI TIẾT XÁC MINH
	public static final String HEADING_NIC_INVESTIGATION_BCA_DETAIl = "DANH SÁCH CHI TIẾT XÁC MINH BCA";
	//TRUNG THÊM CHO DANH SÁCH KIỂM DUYỆT SƠ BỘ
	public static final String HEADING_NIC_INVESTIGATION_CONFIRM = "DANH SÁCH KIỂM DUYỆT SƠ BỘ";
	//TRUNG THÊM CHO DANH SÁCH KIỂM DUYỆT HỒ SƠ TỪ ĐẠI SỨ QUÁN GỬI SANG
	public static final String HEADING_NIC_INVESTIGATION_CONFIRM_DSQ = "DANH SÁCH KIỂM DUYỆT HỒ SƠ TỪ CƠ QUAN ĐẠI DIỆN";
	//TRUNG THÊM CHO DANH SÁCH DUYỆT IN
	public static final String HEADING_NIC_APPROVE_PERSO = "DANH SÁCH DUYỆT IN";
	//TRUNG THÊM CHO DANH SÁCH BỊ TỪ CHỐI
	public static final String HEADING_NIC_REJECT = "DANH SÁCH HỒ SƠ BỊ TỪ CHỐI";
	//TRUNG THÊM CHO DANH SÁCH MẤT / HỎNG
	public static final String HEADING_NIC_LOST_DAMAGE = "DANH SÁCH HỘ CHIẾU MẤT, HỎNG";
	//TRUNG THÊM CHO DANH SÁCH BÀN GIAO
	public static final String HEADING_NIC_LIST_HANDOVER = "DANH SÁCH BÀN GIAO";
	//TRUNG THÊM CHO DANH SÁCH ĐIỂM IN
	public static final String HEADING_NIC_PERSO_LOCATIONS = "DANH SÁCH TRUNG TÂM CÁ THỂ HÓA";
	//TRUNG THÊM CHO DANH SÁCH CHỜ CẤP SỐ HỘ CHIẾU
	public static final String HEADING_NIC_PENDING_PASSPORTNO = "DANH SÁCH CHỜ CẤP SỐ HỘ CHIẾU";
	//TRUNG THÊM CHO DANH SÁCH BÁO CÁO GIAO DICH CƠ QUAN ĐẠI DIỆN
	public static final String HEADING_NIC_REPORT_CQDD = "BÁO CÁO GIAO DICH CƠ QUAN ĐẠI DIỆN";
	//TRUNG THÊM CHO DANH SÁCH HÀNG CHỜ CÔNG VIỆC
	public static final String HEADING_NIC_PENDING_QUEUES_JOB_SCHEDULE = "DANH SÁCH HÀNG CHỜ CÔNG VIỆC";
	public static final int INVESTIGATION_STAGE_PENDING = 1;//Trạng thái đã gưi bộ công an
	public static final int INVESTIGATION_STAGE_CONFIRM = 2;//Trạng thái đã có kết quả từ bca
	public static final int DEFAULT_PAGE_MAX_SIZE = 10;//Max size sử dụng khi thay bảng
	
	public static final String ROLE_ID_NIC_LANHDAOPHEDUYET = "NIC_LANHDAOPHEDUYET";//Max size sử dụng khi thay bảng
	public static final String PARA_NAME_HANDOVER = "COUNT_KEY_HANDOVER";//Max size sử dụng khi thay bảng
	public static final String PACKAGE_NAME_CODE = "Danh sách B";
	public static final String CODE_ID_POSITION_SIGN_B = "POSITION_SIGN_B";
	public static final String CODE_ID_POSITION = "POSITION";
	public static final String CODE_RELIGION_ID = "CODE_RELIGION";
	public static final String CODE_STATUS_ISUANCE = "ISSUANCE"; 
	public static final String CODE_STATUS_PERSONALIZED = "PERSONALIZED";
	public static final String CODE_DISTRICT_ID = "DISTRICT";
	public static final String CODE_TOWN_VILLAGE_ID = "TOWN_VILLAGE";

}
