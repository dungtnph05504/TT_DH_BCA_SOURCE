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
                                        <b style="font-family:'Times New Roman', Arial; font-size:13pt;font-weight:normal;"> CỤC QUẢN LÝ XNC </b>
                                        <br><b style="font-family:'Times New Roman', Arial; font-size:13pt;font-weight:bold;">PHÒNG 2</b>
                                        <br><b style="font-family:'Times New Roman', Arial; font-size:13pt;">..................</b>
                                        <br><b style="font-family:'Times New Roman', Arial; font-size:13pt;font-weight:normal;">Số:........../P2</b>
                                    </td>
                                    <td style="width:60%; text-align:center;">
                                        <p style="padding:0; margin:0 auto; letter-spacing:2px; font-weight:bold; font-family:'Times New Roman', Arial; font-size:13pt;"> CỘNG HÒA XÃ HỘI CHỦ NGHĨA VIỆT NAM </p>
                                        <p style="padding:0; margin:0 auto; font-family:'Times New Roman', Arial; font-size:13pt;">Độc lập - Tự do - Hạnh phúc </p>
                                        <hr style="padding:0; margin:0 auto; height:0; background-color:#000; border:0; border-bottom:1px solid #000; width:190px; position:relative; left:-2px">
                                        <i style="padding-left:80px; position:relative; top:10px ; font-size:14px;" class="ng-binding">A08 Miền Bắc, ${tt.ngaythangnam} </i>
                                    </td>
                                </tr>
                                <tr>
                                    <td colspan="2" style="width:100%; text-align:center; padding-top:30px 0 0 0; margin:0 auto; ">
                                        <b style="line-height:2; font-size:18px;font-weight:bold;"> LỊCH SỬ HỒ SƠ CẤP HỘ CHIẾU </b><br>
                                    </td>
                                </tr>
                                <tr>
                                    <td colspan="2" style="width:100%; padding:20px  0 0 0; padding-left:30px;padding-right:30px; margin:0 auto ">
                                        <div class="row">
                                            <div class="col-sm-12">
                                                <div style="padding-left:20px;">
                                                    <b style="line-height:2; font-size:14px;font-style: italic;padding-left:90px;font-weight:normal;"> Kính gửi:......................................................................................... </b><br>
                                                    <b style="line-height:2; font-size:14px;font-weight:bold;">Theo đề nghị của các đồng chí (........ ngày..../....../......) tra cứu việc cấp hộ chiếu của:</b><br>
                                                </div>
                                                <table class="bkg-tranfer" cellpadding="10" cellspacing="0" style="width:880px; margin-top:15px;border:0;">
                                                    <tbody><tr>
                                                        <td>
                                                            <div style="padding-left:50px; font-family:'Times New Roman', Arial; font-size:13pt; font-weight:400">
                                                                <p class="ng-binding">Họ và tên: ${tt.hoten}</p>
                                                                <p class="ng-binding">Giới tính: ${tt.gioitinh}</p>
                                                                <p class="ng-binding">Ngày sinh: ${tt.ngaysinh}</p>
                                                                <p class="ng-binding">Nơi sinh: ${tt.noisinh}</p>
                                                            </div>
                                                        </td>
                                                    </tr>
                                                </tbody></table>
                                                <div style="text-align:center;">
                                                    <p style="font-family:'Times New Roman', Arial; font-size:13pt;text-decoration: underline;">CÁC LẦN ĐỀ NGHỊ CẤP HỘ CHIẾU</p>
                                                </div>
                                                <div style="padding-left:50px; font-family:'Times New Roman', Arial; font-size:13pt; font-weight:400">
                                                    <p style="font-weight:bold;" class="ng-binding">1 / Số máy tính: </p>
                                                    <p class="ng-binding">Số CMND/CCCD: ${tt.cmnd}</p>
                                                    <p class="ng-binding">Địa chỉ: ${tt.diachi}</p>
                                                    <p class="ng-binding">Nghề nghiệp: ${tt.nghenghiep}</p>
                                                    <p class="ng-binding">Ghi chú: ${tt.ghichu}</p>
                                                </div>
                                                <table class="tableHoChieu" cellpadding="20" cellspacing="0" border="0" style="width:97%;margin-left:47px; margin-top:20px;margin-bottom:20px;font-family:'Times New Roman', Arial; font-size:13pt;">
                                                    <thead>
                                                        <tr>
                                                            <td style="background-color:#e6e6e6; text-align:center;font-weight:bold;border:1px solid #ddd;padding:5px;font-family:'Times New Roman', Arial; font-size:13pt;">STT</td>
                                                            <td style="background-color:#e6e6e6; text-align:center;font-weight:bold;border:1px solid #ddd;padding:5px;font-family:'Times New Roman', Arial; font-size:13pt;">Số hộ chiếu</td>
                                                            <td style="background-color:#e6e6e6; text-align:center;font-weight:bold;border:1px solid #ddd;padding:5px;font-family:'Times New Roman', Arial; font-size:13pt;">Seri HC</td>
                                                            <td style="background-color:#e6e6e6; text-align:center;font-weight:bold;border:1px solid #ddd;padding:5px;font-family:'Times New Roman', Arial; font-size:13pt;">Ngày cấp</td>
                                                            <td style="background-color:#e6e6e6; text-align:center;font-weight:bold;border:1px solid #ddd;padding:5px;font-family:'Times New Roman', Arial; font-size:13pt;">Tình trạng HC</td>
                                                            <td style="background-color:#e6e6e6; text-align:center;font-weight:bold;border:1px solid #ddd;padding:5px;font-family:'Times New Roman', Arial; font-size:13pt;">Hạn HC</td>
                                                        </tr>
                                                    </thead>
                                                   <c:if test="${not empty tt.docData}">
													  <tbody>
													  	 <c:forEach items="${tt.docData}" var="item">
														    <tr>
														      <td class="align-central">
														      	  ${item.syncStatus}
															  </td>	
														      <td>${item.id.passportNo}</td>
														      <td>${item.chipSerialNo}</td>
														      <td>${item.dispatchId}</td>
														      <td>${item.status}</td>
														      <td>${item.issueBy}</td>
														    </tr>
													    </c:forEach>
													  </tbody>
												  </c:if>
                                                </table>
                                                <div style="padding-left:30px;height:140px; font-family:'Times New Roman', Arial; font-size:13pt; font-weight:400">
                                                    <p>Xin trao đổi để các đồng chí rõ./.</p>
                                                    <p style="font-weight:bold;padding-left:30px;">CÁN BỘ TRA CỨU</p>
                                                </div>
                                                <div style="padding-left:30px; font-family:'Times New Roman', Arial; font-size:13pt; font-weight:400">
                                                    <p style="font-weight:bold;text-decoration: underline;margin:0;">Nơi nhận: </p>
                                                    <p style="margin:0;">-Như trên </p>
                                                    <p style="margin:0;">-Lưu P2</p>
                                                </div>
                                            </div>
                                        </div>
                                    </td>
                                </tr>
                            </tbody></table>

                        </div>
                    </div>