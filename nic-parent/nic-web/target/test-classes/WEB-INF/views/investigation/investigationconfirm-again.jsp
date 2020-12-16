<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>
<c:url var="JobInvestigationConfirmUrl" value="/servlet/investigationConfirm/investigationConfirmList" />
<c:url var="searchInvesBAgainUrl" value="/servlet/investigationConfirm/searchInvesBAgain" />
<c:url var="detailInvesBAgainUrl" value="/servlet/investigationConfirm/detailInvesBAgain" />
<c:url var="getFacialByIdUrl" value="/servlet/investigationConfirm/getFacialById" />
<c:url var="getDocumentUrl" value="/servlet/investionProcess/getDocument" />
<c:url var="getUpdateRecUrl" value="/servlet/investigationConfirm/getUpdateRec" />
<c:url var="getPositionSignUrl" value="/servlet/investigationConfirm/getPositionSign" />
<c:url var="savePaymentByIdUrl" value="/servlet/investigationConfirm/savePaymentById" />
<c:url var="getPaymentSignUrl" value="/servlet/investigationConfirm/getPaymentSign" />
<c:url var="getCountArchiveCodeUrl" value="/servlet/investigationConfirm/getCountArchiveCode" />
<c:url var="distroyListBUrl" value="/servlet/investigationConfirm/distroyListB" />
<style>
.cls-mg-bot {
	margin-top: 10px;
}
.jconfirm .jconfirm-box div.jconfirm-content-pane .jconfirm-content {
    white-space: pre-wrap;
}
.form-left {
	
	width: 79%;	
	padding-bottom: 50px;
	float: left;
	transition: width 0.5s;
	-webkit-transition: width 0.5s;
}
.form-right {
	width: 20%;	
	float: left;
	background: #f3f3f3;
	height: 700px;
	transition: width 0.5s;
	-webkit-transition: width 0.5s;
	overflow: hidden;
}
.form-central {
	width: 1%;
	float: left;
	height: 700px;
	text-align: center;
	
}
.clss-font {
	font-size: 12px !important;
}
.top-mag-10 {
	margin-top: 10px;
}
.no-search {
	display: none;
}
.opentb-2 {
}

.dataTables_length {
	display: none;
}
#ajax-load-qa {
	background: rgba(255, 255, 255, .5)
		url('<c:url value="/resources/images/loading_nin.gif" />') 50%
		50% no-repeat;
	position: fixed;
	z-index: 100;
	left: 0;
	top: 0;
	width: 100%;
	height: 100%;
	display: none;
	text-align: center;
}
.colorred {
	color: red;
}	
div#tbDanhSachHS_wrapper .col-sm-12 {
    padding: 0px;
}
div#tbDanhSachHS_wrapper th {
    width: auto !important;
}

div#tbDanhSachHS_wrapper .dataTables_info{padding-left: 10px;}
</style>
<c:url var="passportUrl" value="/servlet/investigationConfirm/passportNoEdit" />
<c:if test="${not empty requestScope.Errors}">
	<div class="border_error">
		<div class="error_left">
			<img align='left'
				src="<c:url value="/resources/images/error_new.jpg" />" width="30"
				height="30" />
		</div>


		<div class="errors">
			<table width="600" height="10" border="0" cellpadding="5">
				<tr>
					<td
						style="padding-left: 5px; font-weight: bold; vertical-align: top;">
						<c:forEach items="${requestScope.Errors}" var="errorMessage">
									${errorMessage}
							</c:forEach>
					</td>
				</tr>
			</table>
		</div>
	</div>
	<br />
</c:if>

<c:if test="${not empty requestScope.messages}">
	<div class="border_success">
		<div class="success_left">
			<img align='left'
				src="<c:url value="/resources/images/success.jpg" />" width="30"
				height="30" />
		</div>


		<div class="success">
			<table width="600" height="10" border="0" cellpadding="5">
				<tr>
					<td
						style="padding-left: 5px; font-weight: bold; vertical-align: top;">
						<c:forEach items="${requestScope.messages}" var="infoMessage">
									${infoMessage}
							</c:forEach>
					</td>
				</tr>
			</table>
		</div>
	</div>
	<br />
</c:if>

<!--Content start-->
<form:form modelAttribute="formData" name="formData">
	<div id="idData"></div>
	
					<div class="ov_hidden">

						
						<div class="form-right">
							<div class="col-md-12 col-sm-12" style="padding: 0px;">
								<div class="new-heading-2"  style="font-size: 13px;">DANH SÁCH HỒ SƠ</div>
								<div class="col-md-12 col-sm-12">
									<input type="text" placeholder="Nhập số danh sách A/B" id="packageNumber" style="width: 100%;font-size: 12px;">
								</div>
								<div class="col-md-6 col-sm-6 top-mag-10">
									<input type="text" placeholder="Từ ngày" readonly="readonly" autocomplete="off" id="createDate" style="width: 100%;font-size: 12px;">
								</div>
								<div class="col-md-6 col-sm-6 top-mag-10">
									<input type="text" placeholder="Đến ngày" readonly="readonly" autocomplete="off" id="endDate" style="width: 100%;font-size: 12px;">
								</div>
								<div class="col-md-6 col-sm-6 top-mag-10" style="display: none;">
									<select style="width: 100%;font-size: 12px;" id="styleList">
										<option value="0">Chưa lập B</option>
										<option value="1">Đã lập B</option>
									</select>
								</div>
								<div class="col-md-6 col-sm-6 top-mag-10">
									<button type="button" onclick="doSubmitSave();" class="btn_small btn-success">
										  <span class="glyphicon glyphicon-search"></span> Tìm kiếm
									</button>	
								</div>
								<div class="col-md-12 col-sm-12" style="padding: 0px;max-height: 400px;overflow: auto;" id="div_danhsach">
									<table id="tbDanhSachHS" class="table table-bordered table-sm table-hover" cellspacing="0" width="100%">
										  <thead>
										    <tr>
										      <th class="th-sm" style="min-width: 100px">Số DS
										
										      </th>	
										      <th class="th-sm">Ngày lập
										
										      </th>
										      <th class="th-sm">Người lập
										
										      </th>	
										      <th class="th-sm">SL
										
										      </th>									     
										    </tr>
										  </thead>
										  <!--<c:if test="${not empty dsHoSo}">-->
											  <tbody>
											    <c:forEach items="${dsHoSo}" var="item">
												    <tr>
												      	<td>${item.packageId}</td>
												      	<td>${item.packageDate}</td>
												      	<td>${item.ricName}</td>	
												      	<td>${item.numberTran}</td>												      	
												    </tr>
											    </c:forEach>
											  </tbody>
										  <!--</c:if>-->
										</table>
										<c:if test="${empty dsHoSo}">
										  	<div class="style-no-record opentb-2">Không tìm thấy kết quả</div>
										</c:if>
								</div>
							</div>
						</div>
						<div class="form-central">
							<a href="#" id="no-search" class="no-search">
								<span class="glyphicon glyphicon-forward"></span>
							</a>
							<a href="#" id="ys-search" class="ys-search">
								<span class="glyphicon glyphicon-backward"></span>
							</a>
						</div>
						<div class="form-left">
							<div class="new-heading-2">LẬP LẠI DANH SÁCH B</div>
							<div style="padding-left: 10px;" id="soDS">Số DS:</div>
							<div class="col-sm-12">
								<fieldset class="scheduler-border none-pad-right">
									<legend style="margin-bottom: 0px;" class="scheduler-border">
										<label class="bold-label">Thông tin bổ sung hồ sơ</label>
									</legend>
									<div class="form-group">
										<div class="col-sm-3"></div>
										<label class="col-sm-2 none-padding text-left">Số hồ sơ lưu</label>
										<div class="col-sm-3"
											style="padding-left: 12px; padding-right: 11px;">
											<input type="text" id="codeDoc" style="width: 50%; float: left;"
												class="form-control"
												disabled="disabled" value="${matailieu}"> 
											<input type="text" id="codeStt"
												style="width: 50%; float: left;" maxlength="5"
												class="form-control">
										</div>
										<label class="col-sm-2 none-padding text-left">Số lượng HS đã có</label>
										<div class="col-sm-2" style="padding-left: 15px;">
											<input type="text" id="numberDoc"
												class="form-control "
												disabled="disabled">
										</div>
									</div>
								</fieldset>
							</div>
							<div class="col-md-12 col-sm-12" style="padding: 0px;">
								<div class="col-md-10 col-sm-10" style="padding: 0px;height: 250px;overflow: auto;">
									<div>
								      	<table id="tbChiTietDS" class="table table-bordered table-sm table-hover" cellspacing="0" width="100%">
										  <thead>
										    <tr>
										      <th class="th-sm" style="max-width: 60px;">Đề xuất
										
										      </th>	
										      <th class="th-sm">Họ tên
										
										      </th>
										      <th class="th-sm">Giới tính
										
										      </th>
										      <th class="th-sm">Ngày sinh
										
										      </th>
										      <th class="th-sm">CMND/CCCD
										
										      </th>
										      <th class="th-sm">Nơi sinh
										
										      </th>
										      <th class="th-sm">Loại hồ sơ
										
										      </th>
										      <th class="th-sm">Nội dung đề xuất
										      </th>
										      <th class="th-sm" style="max-width: 50px;">Lệ phí
										      </th>
										      <th class="th-sm" style="display: none;">
										      </th>

										    </tr>
										  </thead>
										  <!--<c:if test="${not empty chiTietDS}">-->
											  <tbody>
											    <c:forEach items="${chiTietDS}" var="item">
												    <tr>
												      	<td>
												      		<select id="deXuat">
												      			<option value="APPROVE">D</option>
												      			<option value="REJECT">K</option>
												      			<option value="REINVESTIGATE">C</option>
												      			<option value="0"></option>
												      		</select>
												      	</td>
												      	<td>${item.fullName}</td>
												      	<td>${item.gender}</td>
												      	<td>${item.dob}</td>
												      	<td>${item.nin}</td>
												      	<td>${item.placeOfBirth}</td>
												      	<td>
												      		<select id="loaiHoSo" disabled="disabled">
												      			<option selected="selected" value="HCDT">Cấp HC điện tử</option>
												      			<option value="HCTT">Cấp HC thường</option>
												      			<option value="AB">Cấp tem AB</option>
												      			<option value="ABTC">Cấp thẻ ABTC</option>
												      		</select>
												      	</td>	
												      	<td>
												      		<textarea rows="3" cols="10" id="noDungDeXuat" name="noDungDeXuat" style="width: 100%;height: 100%"></textarea>
												      	</td>
												      	<td></td>
												      	<td style="display: none;"></td>
												  
												    </tr>
											    </c:forEach>
											  </tbody>
										 <!-- </c:if>-->
										</table>
										<c:if test="${empty chiTietDS}">
										  	<div class="style-no-record opentb-1">Không tìm thấy kết quả</div>
										</c:if>
							      </div>
								</div>
								<div class="col-md-2 col-sm-2">
									<div id="dialog-image_photoCompare" style="display: block;width: 135px;margin-right: 10px;height: 180px;">
										<div class="centerCaption">       
									         <div style="text-align:center">							             									                 
									                        <div class="khongAnhMat">
									                            <img src="<c:url value='/resources/images/No_Image.jpg' />" class="img-border" height="180" width="135" title="Hit Candidate" />
									                        </div>
									                        <div style="color: #000;text-align:center;" id="slHoSo">Tổng số người: </div>
									                  								                    									               
									         </div>								        
									    </div>
								    </div>
								</div>
							</div>
							<div class="col-md-12 col-sm-12">
								<fieldset>
									<legend>Ghi chú</legend>
									<div id="idNoteP"></div>
									<div id="idNoteO"></div>
									<div id="idNoteN"></div>
								</fieldset>
								<div style="height: 10px;"></div>
								<div class="col-md-8 col-sm-8" style="padding: 0px;">
									<span class="col-md-3 col-sm-3">Nội dung đề xuất</span>
									<textarea id="ghiChuDeXuat" rows="3" cols="" class="col-md-9 col-sm-9"></textarea>
								</div>
								<div class="col-md-4 col-sm-4">
									<div class="col-md-5 col-sm-5">
										<input type="button" class="btn btn-success" value="Chung cả DS" id="luuGhiChu"/>
									</div>
									<div class="col-md-7 col-sm-7">
										<div>
											<div class="col-sm-12">
												<input type="checkbox" id="chk1" name="ghiChuChk" value="-DS bổ túc HC"/>
												<label class="clss-font">DS bổ túc HC</label>
											</div>
										
											<div class="col-sm-12">
												<input type="checkbox" id="chk2" name="ghiChuChk" value="-Chưa cấp"/>
												<label class="clss-font">Chưa cấp</label>
											</div>
									
										</div>
									</div>
								</div>
								
							</div>
							<div id="ajax-load-qa"></div>
							<div>
								<div class="col-md-6 col-sm-6 top-mag-10">
									<label style="margin-right 5px;">Chọn nơi gửi:</label>
								</div>
								<div class="col-md-6 col-sm-6 top-mag-10">
									<label style="margin-left: 5px;">Kính gửi: (dùng cho bổ túc h/s có nơi nhận là Bưu Điện)</label>
								</div>
							</div>
							<div>
								<div class="col-md-6 col-sm-6" id="divKhuVuc">
									<div style="border: 1px solid;height: 100px;margin-right: 5px;overflow: auto;padding: 5px 10px;" id="noiNhanKQ">
										<c:forEach items="${siteKhuVuc}" var="item">
											<div id="${item.key}_chk">
												<input type="checkbox" name="ricKhuVuc" value="${item.key}" />
												<label class="clss-font" id="${item.key}">${item.value}</label>
											</div>
										</c:forEach>
									</div>
								</div>
								<div class="col-md-6 col-sm-6">
									<textarea rows="" cols="" style="height: 100px;margin-left: 5px;width: 98%;" id="strKhuVuc"></textarea>
								</div>
							</div>
							<form action="">
								<div class="col-md-6 col-sm-6 top-mag-10">
									<div class="col-md-3 col-sm-3">Ngày trả <span class="colorred">*</span>
									</div>
									<div class="col-md-9 col-sm-9">
										<input type="text" style="width: 80%;" disabled="disabled" id="strNgayTra"/>
									</div>
								</div>
								<div class="col-md-6 col-sm-6 top-mag-10">
									<div class="col-md-3 col-sm-3">Nơi trả kết quả <span class="colorred">*</span>	
									</div>
									<div class="col-md-9 col-sm-9">
										<input type="text" style="width: 100%;" maxlength="200" id="noiTraKQ"/>
									</div>
								</div>
								<div class="col-md-6 col-sm-6 top-mag-10">
									<div class="col-md-3 col-sm-3">Người ký <span class="colorred">*</span>
									</div>
									<div class="col-md-9 col-sm-9">
										<select style="width: 80%;" id="lanhDaoKy">
											<c:forEach items="${usersKhuVuc}" var="item">
												<option value="${item.key}">${item.value}</option>
											</c:forEach>
										</select>
									</div>
								</div>
								<div class="col-md-6 col-sm-6 top-mag-10">
									<div class="col-md-3 col-sm-3">Chức vụ	
									</div>
									<div class="col-md-9 col-sm-9">
										<input type="text" id="chucVuLD" style="width: 100%;" disabled="disabled"/>
									</div>
								</div>
							</form>
						</div>

						<!-- Model tài liệu -->
						<div class="modal fade" id="taiLieuDK" tabindex="-1" role="dialog" aria-labelledby="exampleModalCenterTitle" aria-hidden="true" >
						  <div class="modal-dialog modal-dialog-centered" role="document" style="width: 900px">
						    <div class="modal-content">
						      <div class="modal-header">
						        <h5 class="modal-title" id="exampleModalLongTitle" style="display: inline-block;">TÀI LIỆU ĐÍNH KÈM</h5>
						        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
						          		<img style="width: 25px;height: 25px;" src="<c:url value='/resources/images/dongform.png' />" title="Đóng" />
						        </button>
						      </div>							      
						      <div class="modal-body" style="height: 500px;overflow: auto;padding-left: 20px;" id="idTaiLieu">
						       		
						       		
						      </div>
						      <div class="modal-footer" style="padding: 5px;">
						       		<button type="button" class="btn btn-warning" data-dismiss="modal" aria-label="Close" style="float: right;">
						       			<span class="glyphicon glyphicon-remove"></span> Đóng
						       		</button>
						       		
						      </div>
						    </div>
						  </div>
						</div>	
						<!-- -------------------------------------------------------------------------- -->
						
						<!-- Model in đề xuất -->
						<!--<div class="modal fade" id="inDeXuat" tabindex="-1" role="dialog" aria-labelledby="exampleModalCenterTitle" aria-hidden="true" >
						  <div class="modal-dialog modal-dialog-centered" role="document" style="width: 1100px">
						    <div class="modal-content">
						      <div class="modal-header">
						        <h5 class="modal-title" id="exampleModalLongTitle" style="display: inline-block;">IN DANH SÁCH ĐỀ XUẤT</h5>
						        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
						          		<img style="width: 25px;height: 25px;" src="<c:url value='/resources/images/dongform.png' />" title="Đóng" />
						        </button>
						      </div>					      
						      <div class="modal-body" style="height: 500px;overflow: auto;padding-left: 100px;padding-right: 100px;" id="idDeXuat">
						       		
						       		
						      </div>
						      <div class="modal-footer" style="padding: 5px;">
						       		<button type="button" class="btn btn-warning" data-dismiss="modal" aria-label="Close" style="float: right;">
						       			<span class="glyphicon glyphicon-remove"></span> Đóng
						       		</button>
						       		<button type="button" onclick="inDeXuat();" class="btn btn-success" style="float: right;margin-right: 10px;">
						       			<span class="glyphicon glyphicon-print"></span> In
						       		</button>
						       		
						      </div>
						    </div>
						  </div>
						</div>	-->
						<!-- -------------------------------------------------------------------------- -->
						
						<!-- Model in đề xuất -->
						<div class="modal fade" id="inDeXuat" tabindex="-1" role="dialog" aria-labelledby="exampleModalCenterTitle" aria-hidden="true" >
						  <div class="modal-dialog modal-dialog-centered" role="document" style="width: 1100px">
						    <div class="modal-content">
						      <div class="modal-header">
						        <h5 class="modal-title" id="exampleModalLongTitle" style="display: inline-block;">IN DANH SÁCH ĐỀ XUẤT</h5>
						        <button type="button" onclick="printRecieptD();" class="close" data-dismiss="modal" aria-label="Close">
						          		<img style="width: 25px;height: 25px;" src="<c:url value='/resources/images/dongform.png' />" title="Đóng" />
						        </button>
						      </div>							      
						      <div class="modal-body" style="height: 500px;overflow: auto;padding-left: 100px;padding-right: 100px;" id="idDeXuat">
						       		
						       		
						      </div>
						      <div class="modal-footer" style="padding: 5px;">
						       		<button type="button" class="btn btn-warning" onclick="printRecieptD();" data-dismiss="modal" aria-label="Close" style="float: right;">
						       			<span class="glyphicon glyphicon-remove btn-boot" style="margin-right: 2px;"></span ><span class="btn-boot"> Đóng</span>
						       		</button>
						       		<button type="button" onclick="inDeXuat();" class="btn btn-success" style="float: right;margin-right: 10px;">
						       			<span class="glyphicon glyphicon-print btn-boot" style="margin-right: 2px;"></span ><span class="btn-boot"> In</span>
						       		</button>
						       		
						      </div>	
						    </div>
						  </div>
						</div>	
						<!-- -------------------------------------------------------------------------- -->

						<!-- Model in đề xuất K -->
						<div class="modal fade" id="inDeXuatK" tabindex="-1" role="dialog" aria-labelledby="exampleModalCenterTitle" aria-hidden="true" >
						  <div class="modal-dialog modal-dialog-centered" role="document" style="width: 1100px">
						    <div class="modal-content">
						      <div class="modal-header">
						        <h5 class="modal-title" id="exampleModalLongTitle" style="display: inline-block;">IN DANH SÁCH ĐỀ XUẤT K</h5>
						        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
						          		<img style="width: 25px;height: 25px;" src="<c:url value='/resources/images/dongform.png' />" title="Đóng" />
						        </button>
						      </div>					      
						      <div class="modal-body" style="height: 500px;overflow: auto;padding-left: 100px;padding-right: 100px;" id="idDeXuatK">
						       		
						       		
						      </div>
						      <div class="modal-footer" style="padding: 5px;">
						       		<button type="button" class="btn btn-warning"  data-dismiss="modal" aria-label="Close" style="float: right;">
						       			<span class="glyphicon glyphicon-remove btn-boot" style="margin-right: 2px;"></span ><span class="btn-boot"> Đóng</span>
						       		</button>
						       		<button type="button" onclick="inDeXuatK();" class="btn btn-success" style="float: right;margin-right: 10px;">
						       			<span class="glyphicon glyphicon-print btn-boot" style="margin-right: 2px;"></span ><span class="btn-boot"> In</span>
						       		</button>
						       		
						      </div>
						    </div>
						  </div>
						</div>	
						<!-- -------------------------------------------------------------------------- -->
						
						<!-- Model in đề xuất C -->
						<div class="modal fade" id="inDeXuatC" tabindex="-1" role="dialog" aria-labelledby="exampleModalCenterTitle" aria-hidden="true" >
						  <div class="modal-dialog modal-dialog-centered" role="document" style="width: 1100px">
						    <div class="modal-content">
						      <div class="modal-header">
						        <h5 class="modal-title" id="exampleModalLongTitle" style="display: inline-block;">IN DANH SÁCH ĐỀ XUẤT C</h5>
						        <button type="button" onclick="printRecieptC();" class="close" data-dismiss="modal" aria-label="Close">
						          		<img style="width: 25px;height: 25px;" src="<c:url value='/resources/images/dongform.png' />" title="Đóng" />
						        </button>
						      </div>					      
						      <div class="modal-body" style="height: 500px;overflow: auto;padding-left: 100px;padding-right: 100px;" id="idDeXuatC">
						       		
						       		
						      </div>
						      <div class="modal-footer" style="padding: 5px;">
						       		<button type="button" class="btn btn-warning" onclick="printRecieptC();" data-dismiss="modal" aria-label="Close" style="float: right;">
						       			<span class="glyphicon glyphicon-remove btn-boot" style="margin-right: 2px;"></span ><span class="btn-boot"> Đóng</span>
						       		</button>
						       		<button type="button" onclick="inDeXuatC();" class="btn btn-success" style="float: right;margin-right: 10px;">
						       			<span class="glyphicon glyphicon-print btn-boot" style="margin-right: 2px;"></span ><span class="btn-boot"> In</span>
						       		</button>
						       		
						      </div>
						    </div>
						  </div>
						</div>	
						<!-- -------------------------------------------------------------------------- -->
						
						<!-- Model phí bổ sung -->
						<div class="modal fade" id="thuPhiBS" tabindex="-1" role="dialog" aria-labelledby="exampleModalCenterTitle" aria-hidden="true" >
						  <div class="modal-dialog modal-dialog-centered" role="document" style="width: 800px">
						    <div class="modal-content">
						      <div class="modal-header">
						        <h5 class="modal-title" id="exampleModalLongTitle" style="display: inline-block;">THU PHÍ BỔ SUNG</h5>
						        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
						          		<img style="width: 25px;height: 25px;" src="<c:url value='/resources/images/dongform.png' />" title="Đóng" />
						        </button>
						      </div>		
						      <div class="modal-body" style="height: 350px;overflow: auto;padding:0" id="idPhiBS">
						      
						      </div>						      
						      <div class="modal-footer" style="padding: 5px;">
						       		<button type="button" onclick="savePayment();" style="float: right;" data-dismiss="modal" aria-label="Close" class="btn btn-success">Chọn</button>				
						       		
						      </div>
						    </div>
						  </div>
						</div>	
						<!-- -------------------------------------------------------------------------- -->


						<%
							/* ******************************************************************************************************************** */
						%>
						<%
							/* ************************************* actions for selected jobs - start ******************************************** */
						%>
						<%
							/* ******************************************************************************************************************** */
						%>

					</div>
				
	<div style="display: flex;flex: 0 auto;justify-content: center;">
	<div class="waitingWhenDoneWaiting" style="display:block; position: fixed; bottom:-5px; border-radius: 8px; border: 2px solid; background-color: rgb(255, 255, 255); z-index: +1000;border-color: #2a7044 #2a7044 transparent #2a7044;">
		<div style="margin: 10px"> 
			<button type="button"  onclick="showDocument();" data-toggle="modal" data-target="#taiLieuDK" name="approve" class="btn btn-success">
				<span class="glyphicon glyphicon-file"></span> Tài liệu đính kèm
			</button>
			<button type="button" id="btnInDeXuat"  onclick="printReciept();" name="approve" class="btn btn-success">
				<span class="glyphicon glyphicon-print"></span> In đề xuất
			</button>
		</div>
	</div>	
	</div>
	<div id="infoDialog" style="display: none;">
	</div>
	
	<!--<div class="waitingWhenDoneWaiting" style="display:block; position: fixed; bottom:5px; right:10px; border-radius: 5px; border: 1px solid gray; background-color: rgb(255, 255, 255); z-index: +1000">
			<div style="margin: 10px"> 
				<button type="button"  onclick="openBoxReject();" name="approve" class="btn btn-primary">
					  <span class="glyphicon glyphicon-list-alt"></span> Tạo danh sách
				</button>
				
			</div>
	</div>-->



	

	<c:url var="createHandover" value="/servlet/investigationConfirm/createHandover" />
<%
	/* ******************************************************************************************************************** */
%>
<%
	/* ************************************************ apply filter - start ********************************************** */
%>
<%
	/* ******************************************************************************************************************** */
%>

<script type="text/javascript">
	$('#tbChiTietDS').DataTable({
		"ordering": false,
		"pagingType": "simple"
	});
	$('#tbDanhSachHS').DataTable({
		"ordering": false,
		"pagingType": "simple"
	});
	var idBefore = ''; 
	var arrTranId = [];
	var danhSachB = '';
	var DOC_ID = 'null';
	var noiTra = '';
	/*var totalPages = ${totalPage};
	var currentPage = ${pageNo};
	var pageSize = ${pageSize};
	window.pagObj = $('#pagination').twbsPagination({
			totalPages: totalPages,
			visiblePages: pageSize,
			startPage: currentPage,
			next: '<span class=\"glyphicon glyphicon-forward fix-paging\"></span>',
			prev: '<span class=\"glyphicon glyphicon-backward fix-paging\"></span>',
			last: '<span class=\"glyphicon glyphicon-step-forward fix-paging\"></span>',
			first: '<span class=\"glyphicon glyphicon-step-backward fix-paging\"></span>',
			onPageClick: function (event, page) {
				if (currentPage != page) {
					$('#pageNo').val(page);
					document.forms["formData"].action = '${JobInvestigationConfirmUrl}';  
					document.forms["formData"].submit();  
				}
			}
		});*/
	
		
		function showDocument(){
			$('#idTaiLieu').html("");
			var url = '${getDocumentUrl}/' + DOC_ID;
			$('#ajax-load-qa').css('display', 'block');

			$.ajax({
				url : url,
				cache: false,
				type: "POST",
				success : function(data) {
					if(data != ''){
						$('#idTaiLieu').html(data);
					}else{
						$('#idTaiLieu').html("<div>Không có tài liệu.</div>");
					}
					$('#ajax-load-qa').css('display', 'none');				
				}
			});
		}	
		
		/*function printReciept() {
			$('#idDeXuat').html("");
			if(danhSachB == ''){
				$.notify("Không có hồ sơ để xử lý!", {
					globalPosition: 'bottom left',
					className: 'warn',
				});
				return; 
			}
			var yourArray = {};
			var url = '${getUpdateRecUrl}/' + danhSachB + "/" + $('#lanhDaoKy').val();
			$('#ajax-load-qa').css('display', 'block');
			for(var i = 0; i < arrTranId.length; i++){
				yourArray[arrTranId[i]] = $('textarea#dx_' + arrTranId[i]).val();
				yourArray['key_' + arrTranId[i]] = $('#keydx_'+ arrTranId[i] +' option:selected').val();
			}
			yourArray["codeListB"] = idBefore;
			$.ajax({
				url : url,
				type: "POST",
				data : JSON.stringify(yourArray),
				contentType : 'application/json',
				success : function(data) {
					if(data != ''){
						$('#idDeXuat').html(data);
						$.notify("Cập nhật danh sách B thành công", {
							globalPosition: 'bottom left',
							className: 'success',
						});
						
					}else{
						$('#idDeXuat').html("<div>Không có đề xuất.</div>");
					}
					$('#ajax-load-qa').css('display', 'none');				
				}
			});
		}*/

		function loadPosition(){
			var chucVuLD = $('#lanhDaoKy option:selected').val();
			var url = '${getPositionSignUrl}/' + chucVuLD;
			 $.ajax({
					type : "POST",
					url : url,
					success: function(data) {
						$('#chucVuLD').val(data.ricName);
			        },
			        error: function(e){}
			 });
		}
		
		function printRecieptC(){
			//$('#idDeXuat').html("");
			$('#idDeXuatK').html("");

			var code = $('#codeDoc').val();
			var stt = $('#codeStt').val();
			//$('#idDeXuatC').html("");	
			if(stt == ''){
				stt = '-1';
				//$('#inDeXuat').modal('hide');
				/*$.notify("Số thứ tự hồ sơ lưu không được để trống!", {
					globalPosition: 'bottom left',
					className: 'warn',
				});
				return;*/ 
			}
			var arrOff = [];
			var yourArray = {};
			for(var i = 0; i < arrTranId.length; i++){
				var dexuat = $('#keydx_'+ arrTranId[i] +' option:selected').val();
				if(dexuat != 'D' && dexuat != 'C'){
					yourArray[arrTranId[i]] = $('textarea#dx_' + arrTranId[i]).val();
					yourArray['key_' + arrTranId[i]] = dexuat;
					arrOff.push(dexuat);
				}
			}			
			var testK = false;
			for(var i = 0; i < arrOff.length; i++){
				if(arrOff[i] == 'K'){
					testK = true;
				}				
			}			
			
			var url = '${getUpdateRecUrl}/' + danhSachB + "/" + $('#lanhDaoKy').val() + '/' + code + '/' + stt + "/N/" + noiTra;
			$('#ajax-load-qa').css('display', 'block');
			$.ajax({
				url : url,
				type: "POST",
				data : JSON.stringify(yourArray),
				contentType : 'application/json',
				success : function(data) {
					//doSubmitSave();
					//emptyChitietDanhSach();
					if(data != ''){
						//$.noConflict();
						if(testK){
							 //$(document).ready(function($){
								$("#inDeXuatK").modal();
								$('#idDeXuatK').html(data);
							 //});
						}
					}else{
						$('#idDeXuat').html("<div>Không có đề xuất.</div>");
					}
					$('#ajax-load-qa').css('display', 'none');		
					
				}
			});
		}
		
		function printRecieptD(){
			//$('#idDeXuat').html("");
			$('#idDeXuatK').html("");
			$('#idDeXuatC').html("");	

			var code = $('#codeDoc').val();
			var stt = $('#codeStt').val();
			if(stt == ''){
				stt = '-1';
				//$('#inDeXuat').modal('hide');
				/*$.notify("Số thứ tự hồ sơ lưu không được để trống!", {
					globalPosition: 'bottom left',
					className: 'warn',
				});
				return;*/ 
			}
			var arrOff = [];
			var yourArray = {};
			for(var i = 0; i < arrTranId.length; i++){
				var dexuat = $('#keydx_'+ arrTranId[i] +' option:selected').val();
				if(dexuat != 'D'){
					yourArray[arrTranId[i]] = $('textarea#dx_' + arrTranId[i]).val();
					yourArray['key_' + arrTranId[i]] = dexuat;
					arrOff.push(dexuat);
				}
			}			
			var testK = false;
			var testC = false;
			for(var i = 0; i < arrOff.length; i++){
				if(arrOff[i] == 'K'){
					testK = true;
				}
				if(arrOff[i] == 'C'){
					testC = true;
				}
			}			
			
			var url = '${getUpdateRecUrl}/' + danhSachB + "/" + $('#lanhDaoKy').val() + '/' + code + '/' + stt + "/N/" + noiTra;
			$('#ajax-load-qa').css('display', 'block');
			$.ajax({
				url : url,
				type: "POST",
				data : JSON.stringify(yourArray),
				contentType : 'application/json',
				success : function(data) {
					//doSubmitSave();
					//emptyChitietDanhSach();
					if(data != ''){
						if(testC){
							//$(document).ready(function($){
								$("#inDeXuatC").modal();
								$('#idDeXuatC').html(data);
							 //});
						}else if(testK){
							//$(document).ready(function($){
								$("#inDeXuatK").modal();
								$('#idDeXuatK').html(data);
							 //});
						}
					}else{
						$('#idDeXuat').html("<div>Không có đề xuất.</div>");
					}
					$('#ajax-load-qa').css('display', 'none');				
				}
			});
		}
		
		function printReciept() {
			$('#idDeXuat').html("");
			$('#idDeXuatK').html("");
			$('#idDeXuatC').html("");

			var code = $('#codeDoc').val();
			var stt = $('#codeStt').val();
			noiTra = $("#noiTraKQ").val();
			//alert(code);
			if(danhSachB == ''){
				//$('#inDeXuat').modal('hide');
				$.notify("Không có hồ sơ để xử lý!", {
					globalPosition: 'bottom left',
					className: 'warn',
				});
				return; 
			}
			if(stt == ''){
				stt = '-1';
				//$('#inDeXuat').modal('hide');
				/*$.notify("Số thứ tự hồ sơ lưu không được để trống!", {
					globalPosition: 'bottom left',
					className: 'warn',
				});
				return;*/ 
			}
			if($('#noiTraKQ').val() == ''){
				//$('#inDeXuat').modal('hide');
				$.notify("Nơi trả kết quả không được để trống!", {
					globalPosition: 'bottom left',
					className: 'warn',
				});
				return; 
			}
			var arrOff = [];
			var yourArray = {};
			var checkDeXuat = true;
			for(var i = 0; i < arrTranId.length; i++){
				var noiDungDX = $('textarea#dx_' + arrTranId[i]).val();
				yourArray[arrTranId[i]] = noiDungDX;
				var dexuat = $('#keydx_'+ arrTranId[i] +' option:selected').val();
				yourArray['key_' + arrTranId[i]] = dexuat;
				arrOff.push(dexuat);
				if(noiDungDX == '' && dexuat != '0'){
					checkDeXuat = false;
					break;
				}
			}
			if(checkDeXuat == false){
				//$('#inDeXuat').modal('hide');
				$.notify("Nội dung đề xuất không được để trống!", {
					globalPosition: 'bottom left',
					className: 'warn',
				});
				return; 
			}
			yourArray["codeListB"] = idBefore;
			var testD = false;
			var testK = false;
			var testC = false;
			for(var i = 0; i < arrOff.length; i++){
				if(arrOff[i] == 'K'){
					testK = true;
				}
				if(arrOff[i] == 'D'){
					testD = true;
				}
				if(arrOff[i] == 'C'){
					testC = true;
				}
			}
			if(!testD && !testC && !testK){
				var urld = '${distroyListBUrl}';
				$.ajax({
					url : urld,
					type: "POST",
					data : {
						handoverId: idBefore,
						danhSachB: danhSachB
					},
					success : function(data) {
						doSubmitSave();		
						$.notify("Không có hồ sơ nào được duyệt nên không có danh sách B được tạo. Danh sách B cũ đã được hủy.", {
							globalPosition: 'bottom left',
							className: 'success',
						});												
						$('#ajax-load-qa').css('display', 'none');		
						$('#btnInDeXuat').attr('disabled', true);
					}
				});				
				return;
			}
			
			var url = '${getUpdateRecUrl}/' + danhSachB + "/" + $('#lanhDaoKy').val() + '/' + code + '/' + stt + "/Y/" + noiTra;
			$('#ajax-load-qa').css('display', 'block');
			$.ajax({
				url : url,
				type: "POST",
				data : JSON.stringify(yourArray),
				contentType : 'application/json',
				success : function(data) {
					doSubmitSave();
					//emptyChitietDanhSach();
					if(data != ''){
						if(testD){
							//alert('1');
							//$(document).ready(function($){
								$("#inDeXuat").modal();
								$('#idDeXuat').html(data);
							//});
						}else if(testC){
							//$(function($) {
								$("#inDeXuatC").modal();
								$('#idDeXuatC').html(data);
							//})
						}else if(testK){
							//$(document).ready(function($){
								$("#inDeXuatK").modal();
								$('#idDeXuatK').html(data);
							//});
						}
						
						$.notify("Lập lại danh sách B thành công", {
							globalPosition: 'bottom left',
							className: 'success',
						});
						
					}else{
						$('#idDeXuat').html("<div>Không có đề xuất.</div>");
					}
					$('#ajax-load-qa').css('display', 'none');		
					$('#btnInDeXuat').attr('disabled', true);
					idBefore = '';
				}
			});
		}
		
	$(function(){
		
		$('#lanhDaoKy').change(function(){
			var chucVuLD = $('#lanhDaoKy option:selected').val();
			var url = '${getPositionSignUrl}/' + chucVuLD;
			 $.ajax({
					type : "POST",
					url : url,
					success: function(data) {
						$('#chucVuLD').val(data.ricName);
			        },
			        error: function(e){}
			 });
		});
		 
		
		
		$("#createDate").datepicker({
			//showOn : "button",
			//buttonImage : "",
			//buttonImageOnly : true,
			changeMonth : true,
			changeYear : true,
			showSecond : true,
			controlType : 'select',
			dateFormat : 'dd/mm/yy',
			yearRange : "-100:+10"
		}).keyup(function(e) {
			if (e.keyCode == 8 || e.keyCode == 46) {
				$.datepicker._clearDate(this);
			}
		});
		$('#createDate').datepicker().datepicker('setDate', new Date());
		$("#endDate").datepicker({
			//showOn : "button",
			//buttonImage : "",
			//buttonImageOnly : true,
			changeMonth : true,
			changeYear : true,
			showSecond : true,
			controlType : 'select',
			dateFormat : 'dd/mm/yy',
			yearRange : "-100:+10"
		}).keyup(function(e) {
			if (e.keyCode == 8 || e.keyCode == 46) {
				$.datepicker._clearDate(this);
			}
		});
		$('#endDate').datepicker().datepicker('setDate', new Date());
		$("#infoDialog").dialog( {
			autoOpen : false,
			width : 750,
			height : 120,
			modal : true,
			bgiframe : true,
			cache :false,
			closeOnEscape: false
		});
		doSubmitSave();
		loadPosition();
	});
	
	function savePayment(){
		var payArray = {};
		var chkPay = document.getElementsByName("payChked");
		for(var i = 0; i < chkPay.length; i++){
			if(chkPay[i].checked){
				payArray[chkPay[i].value] = "T";
			}else{
				payArray[chkPay[i].value] = "F";
			}
		}
		/*if($('#phiTheoMa').text() != ''){
			payArray['JsonPC'] = $( "#idMaPhiChinh option:selected" ).text();
			payArray['JsonSum'] = $('#phiTheoMa').text();
			payArray['JsoBanDau'] = $('#maBanDau').text();
		}*/
		var url = '${savePaymentByIdUrl}/' + DOC_ID;
		$.ajax({
			url : url,
			type: "POST",
			data : JSON.stringify(payArray),
			contentType : 'application/json',
			success : function(data) {
							
			}
		});
	}
	
	function showPayment(value){
		$('#idPhiBS').html("");
		var url = '${getPaymentSignUrl}/' + value;
		$('#ajax-load-qa').css('display', 'block');

		$.ajax({
			url : url,
			cache: false,
			type: "POST",
			success : function(data) {
				$('#idPhiBS').html(data);					
				$('#ajax-load-qa').css('display', 'none');				
			}
		});
	}	
	
	function emptyChitietDanhSach() {
		//$.noConflict();
		var tb = $('#tbChiTietDS').DataTable();	
		tb.clear();
		$('#tbChiTietDS tbody tr').hide();
		$('#div_chitiet .dataTables_paginate ').css("display", 'none');
		$('#div_chitiet .dataTables_info').css("display", 'none');
		$(".khongAnhMat").html("<img src=\"<c:url value='/resources/images/No_Image.jpg' />\" class=\"img-border\" height=\"180\" width=\"135\" />");
		$('.opentb-1').css('display', 'block');
		$('#codeStt').val('');
		$('#numberDoc').val('');
		$("#idNoteP").text('');
		$('textarea#ghiChuDeXuat').val('');
		$("#idNoteO").text('');
		$("#noiTraKQ").val('');
		$("#strNgayTra").val('');
		var chkKV = document.getElementsByName("ghiChuChk");
		for(var i = 0; i < chkKV.length; i++){
			chkKV[i].checked  = false;
		}   
	} 

	function doSubmitSave() {
		
		$('#ajax-load-qa').css('display', 'block');
		var packageNumber = $('#packageNumber').val();
		var createDate = $('#createDate').val();
		var endDate = $('#endDate').val();
		var styleList = $('#styleList').find(":selected").val();
		$.ajax({
			url : '${searchInvesBAgainUrl}',
			cache: false,
			type: "POST",
			data:{
				packageNumber: packageNumber,
				createDate: createDate,
				endDate: endDate,
				styleList: styleList,
			},
			success : function(data) {
				idBefore = '';
				$('#ajax-load-qa').css('display', 'none');
				var tb = $('#tbDanhSachHS').DataTable();	
				tb.clear();
				if(data.length > 0){
					$('.opentb-2').css('display', 'none');
		        	for(var i = 0; i < data.length; i++){
		        		tb.row.add([
		        		             data[i].packageId,
		        		             data[i].packageDate,
		        		             data[i].ricName,
		        		             data[i].numberTran
		        		]).draw(false);
		        		$('#tbDanhSachHS tr:eq('+(i+1)+') td:eq(1)').addClass("align-central");
		        	}
		        	$('#tbDanhSachHS tbody tr').show();
		        	$('#div_danhsach .dataTables_paginate ').css("display", 'block');
					$('#div_danhsach .dataTables_info').css("display", 'block');
				}else{	
					$('#tbDanhSachHS tbody tr').hide();
					$('#div_danhsach .dataTables_paginate ').css("display", 'none');
					$('#div_danhsach .dataTables_info').css("display", 'none');				
					$('.opentb-2').css('display', 'block');
				}
			}
		});
	}
	

	
	/*=====================================================================*/
	/*=========== XỬ LÝ THAY ĐỔI SỐ HỘ CHIẾU ==============================*/
	function openChangePassport(code, np){
		  document.getElementById('transLbl').innerHTML = code;
		  document.getElementById('nowPeriod').innerHTML = np;
		  $("#inputPeriod").val(np);
		  $( "#dialog-changePeriodValidate" ).dialog( "open" ); 
	}
	
	$(function() {
		
		var tbDanhSach = $('#tbDanhSachHS').DataTable();
		
	    $('#tbDanhSachHS tbody').on('click', 'tr', function(){
	        var dataDSHS = tbDanhSach.row(this).data();
	        if(idBefore == dataDSHS[0]){
	        	return;
	        }
	        $('#btnInDeXuat').attr('disabled', false);
	        var dcKhuVuc = '';
	    	danhSachB = '';
	    	arrTranId = [];
	    	$('#ajax-load-qa').css('display', 'block');
	        $('#tbDanhSachHS > tbody > tr').removeClass("back-gr");
	    	$(this).addClass("back-gr");
	    	emptyChitietDanhSach();
	    	var url = '${detailInvesBAgainUrl}';
	    	idBefore = dataDSHS[0];
	    	$.ajax({
				type : "POST",
				url : url,
				data:{
					packageNo: dataDSHS[0]
				},
				success: function(data) {
					$('#ajax-load-qa').css('display', 'none');
				    $('div#soDS').text('Số DS: ' + dataDSHS[0]);
					var tb = $('#tbChiTietDS').DataTable();	
					tb.clear();
					if(data.length > 0){
						$('.opentb-1').css('display', 'none');
						/*Mặc định ảnh mặt đầu tiên*/
			        	$(".khongAnhMat").html(data[0].photoStr);
						$('#lanhDaoKy').val(data[0].leaderId);
						$('#chucVuLD').val(data[0].position);
						$('#idNoteP').text(data[0].noteApprovePerson);
						$('#idNoteO').text(data[0].noteApproveObj);
						$('#idNoteN').text(data[0].noteApproveNin);
			        	/*-------------------------------------------------*/
			        	/*Add data và bảng*/
			        	for(var i = 0; i < data.length; i++){
			        		danhSachB += data[i].transactionId + ",";
			        		arrTranId.push(data[i].transactionId);
			        		tb.row.add([
			        		             '<select id="keydx_'+data[i].transactionId+'" style="color: #000;">' +
												'<option value="D">D</option>' +
												'<option value="K">K</option>' +
												'<option value="C">C</option>' +
												'<option value="0"></option>' +
										 '</select>',
			        		             data[i].fullName,
			        		             data[i].gender,
			        		             data[i].dob,
			        		             data[i].nin,
			        		             data[i].placeOfBirth,
			        		             '<select id="loaiHoSo" disabled="disabled" style="color: #000;">' +
											 '<option selected="selected" value="HCDT">Cấp HC điện tử</option>'
											+'<option value="HCTT">Cấp HC thường</option>'
											+'<option value="AB">Cấp tem AB</option>'
											+'<option value="ABTC">Cấp thẻ ABTC</option>'
										+'</select>',
			        		             '<textarea maxlength="500" rows="3" cols="10" style="color: #000;width: 100%;height: 100%;" name="noDungDeXuat" id="dx_'+data[i].transactionId+'" ></textarea>',
			        		             "<a data-toggle=\"modal\" onclick=\"showPayment('"+data[i].transactionId+"');\" data-target=\"#thuPhiBS\" href=\"#\"><img src=\"/eppcentral/resources/images/lephi_1.png\"></a>",
			        		             data[i].transactionId
			        		       
			        		]).draw(false);
			        		
			        		$('#tbChiTietDS tr:eq('+(i+1)+') td:eq(3)').addClass("align-central");
			        		$('#tbChiTietDS tr:eq('+(i+1)+') td:last').css('display', 'none');
			        		$('textarea#dx_' + data[i].transactionId).val(data[i].leaderNote);//dữ liệu nội dung đề xuất
			        		$('#keydx_' + data[i].transactionId).val(data[i].stageList);//kiểu đề xuất
			        	}
			        	/*-------------------------------------------------*/
			        	/*Thêm số lượng hồ sơ*/
			        	$('#slHoSo').text('Tổng số người: ' + data[0].count);
			        	/*-------------------------------------------------*/
			        	/*Chọn khu vực, import khu vực*/
			        	var itemKV = document.getElementsByName("ricKhuVuc");
			            for(var j = 0; j < itemKV.length; j++){
			            	if(itemKV[j].value == data[0].regSiteCode){
			            		itemKV[j].checked = true;
			            		dcKhuVuc = $('label#' + itemKV[j].value).text();
			            	}else{
			            		itemKV[j].checked = false;
			            	}
			            }
			        	$('textarea#strKhuVuc').val(dcKhuVuc);
			        	$('#noiTraKQ').val(dcKhuVuc);
			        	$('#strNgayTra').val(data[0].esColectionDate);
			        	/*-------------------------------------------------*/
			        	$("#tbChiTietDS tbody tr:first").addClass("back-gr");
			        	DOC_ID = $("#tbChiTietDS tbody tr:first td:last").text();
					}else{
						
						$('.opentb-1').css('display', 'block');
					}
		        },
		        error: function(e){}
			});
	    });
	    
	    var tbChiTiet = $('#tbChiTietDS').DataTable();
	     
	    $('#tbChiTietDS tbody').on('click', 'tr', function(){
	    	 var dataCTHS = tbChiTiet.row(this).data();
	    	 if(DOC_ID == dataCTHS[9]){
		        	return;
		     }
	    	 $('#ajax-load-qa').css('display', 'block');	
	    	 $('#tbChiTietDS > tbody > tr').removeClass("back-gr");
	    	 $(this).addClass("back-gr");
	    	 var url = '${getFacialByIdUrl}/' + dataCTHS[9];
	    	 DOC_ID = dataCTHS[9];
		     $.ajax({
					type : "POST",
					url : url,
					success: function(data) {
						$('#ajax-load-qa').css('display', 'none');
						$(".khongAnhMat").html(data.photoStr);
						$('#strNgayTra').val(data.esColectionDate);
						$('#idNoteP').text(data.noteApprovePerson);
						$('#idNoteO').text(data.noteApproveObj);
						$('#idNoteN').text(data.noteApproveNin);
						$('#slHoSo').text('Tổng số người: ' + data.count);
			        },
			        error: function(e){}
			 });
	    });
		
		$('#no-search').click(function(){
			$('.form-left').css('width', '79%');
			$('.form-right').css('width', '20%');
			$('.ys-search').css('display', 'block');
			$('.no-search').css('display', 'none');
		});
		$('#ys-search').click(function(){
			$('.form-left').css('width', '99%');
			$('.form-right').css('width', '0%');
			$('.ys-search').css('display', 'none');
			$('.no-search').css('display', 'block');
		});
		$('#chk1').click(function(){
			var ghiChu = '';
			var item = document.getElementsByName("ghiChuChk");
        	for(var i = 0; i < item.length; i++){
        		if(item[i].checked){
        			ghiChu += item[i].value + " ";
        		}
        	}
        	$("textarea#ghiChuDeXuat").val(ghiChu);
		});
		
		$('#chk2').click(function(){
			var ghiChu = '';
			var item = document.getElementsByName("ghiChuChk");
        	for(var i = 0; i < item.length; i++){
        		if(item[i].checked){
        			ghiChu += item[i].value + " ";
        		}
        	}
        	$("textarea#ghiChuDeXuat").val(ghiChu);
		});
		
		$('#luuGhiChu').click(function(){
			var importGhiChu = $("textarea#ghiChuDeXuat").val();
			var item = document.getElementsByName("noDungDeXuat");
        	for(var i = 0; i < item.length; i++){
        		item[i].value = importGhiChu;
        	}
		});
		
		$('div#divKhuVuc input[type="checkbox"]').click(function(){
			var chkKhuVuc = document.getElementsByName("ricKhuVuc");
			var strKhuVuc = '';
            for(var j = 0; j < chkKhuVuc.length; j++){
            	if(chkKhuVuc[j].checked){
            		strKhuVuc += $('label#' + chkKhuVuc[j].value).text() + ",";
            	}
            }
        	$('textarea#strKhuVuc').val(strKhuVuc.substring(0, strKhuVuc.length - 1));
        	$('#noiTraKQ').val(strKhuVuc.substring(0, strKhuVuc.length - 1));
		});
		
		$('#codeStt').keyup(function(){
			var code = $('#codeDoc').val();
			var stt = $('#codeStt').val();
			var url = '${getCountArchiveCodeUrl}/' + code + '/' + stt;
			 $.ajax({
					type : "POST",
					url : url,
					success: function(data) {
						if(data == -1){
							$('#numberDoc').val('');
						}
						else{
							$('#numberDoc').val(data);
						}
			        },
			        error: function(e){}
			 });
		});
	  }); 
	/*=====================================================================*/
	/*=========== END -- XỬ LÝ THAY ĐỔI SỐ HỘ CHIẾU ==============================*/
	
	
	/*$("#createDate").datepicker({
		showOn : "button",
		buttonImage : "<c:url value="/resources/images/calendar.gif" />",
		buttonImageOnly : true,
		changeMonth : true,
		changeYear : true,
		showSecond : true,
		controlType : 'select',
		dateFormat : 'dd-M-yy',
		yearRange : "-100:+10"
	}).keyup(function(e) {
		if (e.keyCode == 8 || e.keyCode == 46) {
			$.datepicker._clearDate(this);
		}
	});
	
	$('#createDate').datepicker().datepicker('setDate', "");*/
</script>
	
	<form:input    path="currentlyAssignedToUserId"                       type="hidden" name="currentlyAssignedToUserId"                       value="" />
	<form:input    path="unassignAllForUserUserId"                        type="hidden" name="unassignAllForUserUserId"                        value="" />
	<form:input    path="assignToId"                        type="hidden" name="assignToId"                        value="" />
</form:form>



