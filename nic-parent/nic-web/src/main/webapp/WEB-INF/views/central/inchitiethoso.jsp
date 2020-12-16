           <%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>

<div class="col-sm-12">
                        <div style="width:900px; border: 0px solid #999; margin:0 auto; position:relative">
                            <table class="bkg-tranfer" cellpadding="20" cellspacing="0" border="0" style="width:100%">
                                <tbody><tr>
                                    <td style="width:40%; text-align:center">
                                        <b style="font-family:'Times New Roman', Arial; font-size:12pt;font-weight:normal;"> TỔNG CỤC AN NINH </b>
                                        <br>
                                        <b style="font-family:'Times New Roman', Arial; font-size:12pt;"> CỤC QUẢN LÝ XNC </b>
                                    </td>
                                    <td style="width:60%; text-align:center;">
                                        <p style="padding:0; margin:0 auto; letter-spacing:2px; font-weight:bold; font-family:'Times New Roman', Arial; font-size:12pt;"> CỘNG HÒA XÃ HỘI CHỦ NGHĨA VIỆT NAM </p>
                                        <p style="padding:0; margin:0 auto; font-family:'Times New Roman', Arial; font-size:12pt;">Độc lập - Tự do - Hạnh phúc </p>
                                        <hr style="padding:0; margin:0 auto; height:0; background-color:#000; border:0; border-bottom:1px solid #000; width:190px; position:relative; left:-2px">
                                        <i style="padding-left:70px; position:relative; top:10px ; font-size:14px;" class="ng-binding">A08 Miền Bắc, ${tt.ngaythangnam} </i>
                                    </td>
                                </tr>
                                <tr>
                                    <td colspan="2" style="width:100%; text-align:center; padding:15px  0 0 0; margin:0 auto ">
                                        <b style="line-height:2; font-size:18px;font-weight:bold;"> PHIẾU TRA CỨU THÔNG TIN XÉT DUYỆT CẤP HỘ CHIẾU </b><br>
                                    </td>
                                </tr>

                            </tbody></table>
                            <table class="bkg-tranfer" cellpadding="10" cellspacing="0" style="width:880px; margin-top:15px;border:0;">
                                <tbody><tr>
                                    <td>
                                        <u><b>THÔNG TIN HỎI</b></u>
                                        <div style="padding-left:30px; font-family:'Times New Roman', Arial; font-size:12pt; font-weight:400;margin-top:5px;">
                                            <p style="width:100%;margin:0;" class="ng-binding">Họ tên: ${tt.hoten}</p>
                                            <p class="ng-binding">Ngày sinh: ${tt.ngaysinh}</p>
                                        </div>
                                    </td>
                                </tr>
                                <tr>
                                    <td>
                                        <u><b>THÔNG TIN TRẢ LỜI</b></u>
                                        <div style="padding-left:30px; font-family:'Times New Roman', Arial; font-size:12pt; font-weight:400;margin-top:5px;">
                                            <p style="width:100%;margin:0;" class="ng-binding">Số máy tính: ${tt.somaytinh}</p>
                                            <p style="width:100%;margin:0;">Số danh sách địa phương: ${tt.sodanhsachdiaphuong}<span ng-show="traCuuHSHCChiTiet.LIST_CODE_PA_A != null" class="ng-binding ng-hide"></span></p>
                                            <p style="width:100%;margin:0;">Họ tên: <span style="font-weight:bold;" class="ng-binding">${tt.hoten}</span></p>
                                            <div style="width:35%; float:left">Ngày sinh: <b class="ng-binding">${tt.ngaysinh}</b> </div><div style="width:35%; float:left;">Giới tính:<span style="font-weight:bold;" class="ng-binding">${tt.gioitinh}</span></div><div style="width:30%; float:left;">Dân tộc: <span style="font-weight:bold;" class="ng-binding">${tt.dantoc}</span></div>
                                            <p style="width:100%;margin:0;">Nơi sinh: <span style="font-weight:bold;" class="ng-binding">${tt.noisinh}</span></p>
                                            <div style="float:left; width:35%;"> Số CMND/CCCD: <span style="font-weight:bold;" class="ng-binding">${tt.cmnd}</span></div><div style="width:65%; float:left;">Ngày cấp CMND: <span style="font-weight:bold;" class="ng-binding">${tt.ngaycap}</span></div>
                                            <p style="width:100%;margin:0;" class="ng-binding">Địa chỉ: ${tt.diachi}</p>
                                            <p style="width:100%;margin:0;" class="ng-binding">Địa chỉ tạm trú: ${tt.diachitamtru}</p>
                                            <div style="float:left; width:35%" class="ng-binding"> Nghề nghiệp: ${tt.nghenghiep}</div><div style="width:65%; float:left" class="ng-binding">Nơi làm việc: ${tt.noilamviec}</div>
                                            <p style="width:100%"></p><div style="width:35%; float:left;">Số hộ chiếu: <span style="font-weight:bold;" class="ng-binding">${tt.sohochieu}</span> </div><div style="width:35%; float:left;">Ngày cấp HC: ${tt.ngaycaphc}<span style="font-weight:bold;" class="ng-binding"></span></div><div style="width:30%; float:left;">Loại HS: <span style="font-weight:bold;" class="ng-binding">${tt.loaihs}</span></div><p></p>
                                            <p style="width:100%"> </p><div style="width:35%; float:left;">Tình trạng HC: <span style="font-weight:bold;" class="ng-binding">${tt.tinhtranghc}</span> </div><div style="width:35%; float:left; color:#fff">.</div><div style="width:30%; float:left;">Hạn HC: <span style="font-weight:bold;" class="ng-binding">${tt.hanhc}</span></div><p></p>
                                            <p style="width:100%"></p><div style="width:35%; float:left">Ngày hủy HC: <span ng-show="traCuuHSHCChiTiet.STATUS === 'CANCELLED'" class="ng-binding">${tt.ngayhuyhc}</span></div><div style="width:35%; float:left; color:#fff">.</div><div style="width:30%; float:left">Người hủy: ${tt.nguoihuyhc}<span ng-show="traCuuHSHCChiTiet.passport.status === 'CANCELLED'" class="ng-binding"></span></div><p></p>
                                            <p style="width:100%;margin:0;">Lý do hủy: <span ng-show="traCuuHSHCChiTiet.PA_STATUS === 'CANCELLED'" class="ng-binding ng-hide">${tt.lydohuy}</span></p>
                                            <p style="width:100%;margin:0;" class="ng-binding">Đơn vị công tác: ${tt.donvicongtac}</p>
                                            <p style="width:100%" class="ng-binding">Ghi chú: ${tt.ghichu}</p>
                                        </div>
                                    </td>
                                </tr>
                                <tr>
                                    <td>
                                        <div style="padding-left:30px; font-family:'Times New Roman', Arial; font-size:12pt; font-weight:400">
                                            <p style="width:100%;margin:0;">Thông tin bảo lãnh:</p>
                                            <div style="float:left; width:40%" class="ng-binding"> Số hồ sơ lưu: ${tt.sohosoluu}</div>
                                            <div style="width:50%; float:left; font-style:italic;" class="ng-binding">Người ký hộ chiếu: ${tt.nguoikyhochieu}</div>
                                            <p style="width:100%"></p><div style="float:left; width:40%; font-style:italic;" class="ng-binding"> Số danh sách B: ${tt.sodanhsachB}</div><div style="width:50%; float:left; font-style:italic;" class="ng-binding">Người duyệt: ${tt.nguoiduyet}</div> <p></p>
                                            <p style="width:100%"></p><div style="float:left; width:40%; font-style:italic;" class="ng-binding"> Người nhập hồ sơ: ${tt.nguoinhaphs}</div><div style="width:50%; float:left; font-style:italic;" class="ng-binding">Ngày duyệt: ${tt.ngayduyet}</div> <p></p>
                                            <p style="width:100%"></p><div style="float:left; width:40%; font-style:italic;" class="ng-binding"> Người tra hồ sơ: ${tt.nguoitrahs}</div><div style="width:50%; float:left; font-style:italic;" class="ng-binding">Kết quả duyệt: ${tt.ketquaduyet}</div> <p></p>
                                            <p style="width:100%"></p><div style="float:left; width:40%; font-style:italic;" class="ng-binding"> Ngày tra hồ sơ: ${tt.ngaytrahs}</div><div style="width:50%; float:left; font-style:italic;" class="ng-binding">Ngày in hộ chiếu: ${tt.ngayinhc}</div> <p></p>
                                            <p style="width:100%"></p><div style="float:left; width:40%;font-style:italic;" class="ng-binding"> Người đề xuất: ${tt.nguoidexuat}</div><div style="width:50%; float:left;font-style:italic;" class="ng-binding">Người in hộ chiếu: ${tt.nguoiinhc}</div> <p></p>
                                        </div>
                                    </td>
                                </tr>
                            </tbody></table>

                            <table class="bkg-tranfer" cellpadding="20" cellspacing="0" border="0" style="width:100%; margin-top:30px;">
                                <tbody><tr>
                                    <td style="width:40%; text-align:center;vertical-align:top; ">
                                        <b style="font-family:'Times New Roman', Arial; font-size:12pt;">
                                            Ý kiến lãnh đạo phòng
                                        </b>
                                    </td>
                                    <td style="width:60%; text-align:center; ">
                                        <b style="font-family:'Times New Roman', Arial; font-size:12pt;"> Cán bộ thực hiện  </b>
                                    </td>
                                </tr>
                            </tbody></table>
                        </div>
                    </div>