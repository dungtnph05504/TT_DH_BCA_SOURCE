<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>

<div class="col-sm-12">
                        <div style="width:900px; border: 0px solid #999; margin:0 auto; position:relative;">
                            <div style="width:100%;margin-bottom:10px;text-align:center;">
                                <b style="font-family:'Times New Roman', Arial; font-size:16pt;">THÔNG TIN KHÁC</b>
                            </div>
                            <fieldset class="scheduler-border">
                                <legend class="scheduler-border">Thân nhân</legend>
                                <table class="tableHoChieu" cellpadding="20" cellspacing="0" border="0" style="width:100%;margin-left:1%; margin-bottom:10px;">
                                    <thead>
                                        <tr>
                                            <td style="background-color:#e6e6e6; text-align:center;font-weight:bold;border:1px solid #ddd;padding:5px;">Quan hệ</td>
                                            <td style="background-color:#e6e6e6; text-align:center;font-weight:bold;border:1px solid #ddd;padding:5px;">Họ và tên</td>
                                            <td style="background-color:#e6e6e6; text-align:center;font-weight:bold;border:1px solid #ddd;padding:5px;">Ngày sinh</td>
                                        </tr>
                                    </thead>
                                    <tbody>
                                        <c:forEach items="${tt}" var="item">
										    <tr>
										      <td>${item.stage_F}</td>
										      <td>${item.fullName_F}</td>
										      <td>${item.dob_F}</td>
										    </tr>
									    </c:forEach>
                                    </tbody>
                                </table>
                            </fieldset>
                            
                            <fieldset class="scheduler-border" style="overflow: auto;">
                                <legend class="scheduler-border">Bị chú hộ chiếu</legend>
                                
                                
                            <table class="table e-grid-table table-bordered table-hover e-table-fix-head">
                                    <thead>
                                        <tr>
                                            <th class="e-cursor-pointer" style="background-color: rgb(230, 230, 230); position: relative; top: -0.7px;"><div class="e-th-table-boder text-center">Người ký</div></th>
                                            <th class="e-cursor-pointer" style="background-color: rgb(230, 230, 230); position: relative; top: -0.7px;"><div class="e-th-table-boder text-center">Chức vụ</div></th>
                                            <th class="e-cursor-pointer" style="background-color: rgb(230, 230, 230); position: relative; top: -0.7px;"><div class="e-th-table-boder text-center">Người in</div></th>
                                            <th class="e-cursor-pointer" style="background-color: rgb(230, 230, 230); position: relative; top: -0.7px;"><div class="e-th-table-boder text-center">Ngày in</div></th>
                                            <th class="e-cursor-pointer" style="background-color: rgb(230, 230, 230); position: relative; top: -0.7px;"><div class="e-th-table-boder text-center">Nội dung tiếng Việt</div></th>
                                            <th class="e-cursor-pointer" style="background-color: rgb(230, 230, 230); position: relative; top: -0.7px;"><div class="e-th-table-boder text-center">Nội dung tiếng Anh</div></th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                        <!-- ngRepeat: annotation in Annotations -->
                                    </tbody>
                                </table></fieldset>

                            <fieldset class="scheduler-border">
                                <legend class="scheduler-border">Thông tin AB</legend>
                                <table class="tableHoChieu" cellpadding="20" cellspacing="0" border="0" style="width:98%;margin-left:1%; margin-bottom:10px;">
                                    <thead>
                                        <tr>
                                            <td style="background-color:#e6e6e6; text-align:center;font-weight:bold;border:1px solid #ddd;padding:5px;">Số AB</td>
                                            <td style="background-color:#e6e6e6; text-align:center;font-weight:bold;border:1px solid #ddd;padding:5px;">Được miễn thị thực nước</td>
                                            <td style="background-color:#e6e6e6; text-align:center;font-weight:bold;border:1px solid #ddd;padding:5px;">Người ký</td>
                                            <td style="background-color:#e6e6e6; text-align:center;font-weight:bold;border:1px solid #ddd;padding:5px;">Ngày cấp</td>
                                            <td style="background-color:#e6e6e6; text-align:center;font-weight:bold;border:1px solid #ddd;padding:5px;">Ngày hết hạn</td>
                                        </tr>
                                    </thead>
                                    
                                </table>
                            </fieldset>

                        </div>
                    </div>