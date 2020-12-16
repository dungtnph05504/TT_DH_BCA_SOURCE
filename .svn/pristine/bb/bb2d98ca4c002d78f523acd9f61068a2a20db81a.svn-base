<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>
<c:url var="JobInvestigationConfirmUrl" value="/servlet/investigationConfirm/investigationConfirmList" />
<c:url var="searchInvesBUrl" value="/servlet/investigationConfirm/searchInvesB" />
<c:url var="detailInvesBUrl" value="/servlet/investigationConfirm/detailInvesB" />
<c:url var="getInfoObjectByIdUrl" value="/servlet/investionProcess/getInfoObjectById" />
<c:url var="getInfoTransactionUrl" value="/servlet/investionProcess/getInfoTransaction" />
<c:url var="getInfoXNCTransactionUrl" value="/servlet/investionProcess/getInfoXNCTransaction" />
<c:url var="getInfoMathuyTransactionUrl" value="/servlet/investionProcess/getInfoMathuyTransaction" />
<c:url var="getLichSuCPTransactionUrl" value="/servlet/investionProcess/getLichSuCPTransaction" />
<c:url var="getStayTransactionUrl" value="/servlet/investionProcess/getStayTransaction" />
<c:url var="getGoTransactionUrl" value="/servlet/investionProcess/getGoTransaction" />
<c:url var="getFingerPrintUrl" value="/servlet/investionProcess/getFingerPrint" />
<c:url var="getFacialUrl" value="/servlet/investionProcess/getFacial1" />
<c:url var="getDocumentUrl" value="/servlet/investionProcess/getDocument" />
<c:url var="showDocumentUrl" value="/servlet/investionProcess/showDocument" />
<c:url var="getDetailXNCTransactionUrl" value="/servlet/investionProcess/getDetailXNCTransaction" />
<c:url var="nextProcessUrl" value="/servlet/investionProcess/invesProcess" />
<c:url var="testLostIdUrl" value="/servlet/investionProcess/testLostId" />
<c:url var="distroyTransactionUrl" value="/servlet/investionProcess/distroyTransaction" />
<c:url var="successPassportUrl" value="/servlet/investionProcess/successPassport" />
<style>
.magin-btm-10 {
	margin-bottom: 10px;
}
.epp-form-group{
	margin-bottom: 5px;
}
.jconfirm .jconfirm-box div.jconfirm-content-pane .jconfirm-content {
    white-space: pre-wrap;
}
.form-left {
	width: 73%;	
	padding-bottom: 50px;
	float: left;
	-webkit-transition: width 0.5s;
	-webkit-transition: width 0.5s;
}
.form-right {
	width: 27%;	
	float: left;
	height: 600px;
	transition: width 0.5s;
	-webkit-transition: width 0.5s;
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

.opentb-2 {
}

.dataTables_length {
	display: none;
}
#ajax-load-qa {
	background: rgba(255, 255, 255, .6)
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

#ajax-load-qa-up {
	background: rgba(255, 255, 255, .5) no-repeat;
	position: fixed;
	left: 0;
	top: 0;
	width: 100%;
	height: 100%;
	display: none;
	text-align: center;
}
.bder-radis {
	border-radius: 0px !important;
}
table#tb_btn tr td input[type="button"] {
	width: 100px;
}
table#tb_btn tr td{
	padding-right: 10px;
}

.font-chuthe {
	font-weight: bold;
	color: red;
}

div#ttCaNhan lable {
	font-weight: bold;
}

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
	<c:forEach items="${dsJob}" var="chk_process">
		<form:hidden path="selectedJobIds" value="${chk_process}"/>
	</c:forEach>
	<c:forEach items="${loadJob}" var="chk_load">
		<form:hidden path="loadJobIds" value="${chk_load}"/>
	</c:forEach>
	<form:hidden path="jobId" value="${jobCompare}"/>
	<form:hidden path="stageLoad" value="${stage}"/>
	<div class="form-desing">
		<div>
			<div>
				<div class="row">
					<div class="ov_hidden">

						<div class="new-heading-2">KHỚP THÔNG TIN CÁ NHÂN
							<div style="color: red;float: right;margin-right: 40px;font-size: 20px;">${stt} <span style="font-size: 20px;">/</span>${count}</div>
						</div>
						<div style="clear: both"></div>

						<div style="min-height: 10px"></div>
						
								
						<div class="form-right">
							<div class="col-md-12 col-sm-12">
								<div class="col-md-12 col-sm-12" style="margin-bottom: 30px;">
									<div class="font-chuthe">Họ tên: ${chuHS.fullName}</div>
									<div class="font-chuthe">Ngày sinh: ${chuHS.dob}</div>
									<div class="font-chuthe">CMND/CCCD: ${chuHS.nin}</div>
									<div style="text-align: center;">
										
										<fieldset style="padding-bottom: 30px;">
											<legend>Đối chiếu ảnh mặt</legend>
											<div style="text-align: center;display: inline-block;">
											<div id="dialog-image_photoCompare" style="display: block;width: 125px;height: 170px;margin-top: 10px;margin-right: 5px;">
												<div class="centerCaption">       
											         <label>Chủ hồ sơ</label>							             									                 
											         <div style="text-align:center;" id="chuHoSo">
														    <c:choose>
											                    <c:when test="${not empty chuHS.photo}">
											                        <div id="coAnhMat">
											                            <img src="data:image/jpg;base64,${chuHS.photo}"
											                                 class="img-border doGetAJpgSafeVersion" height="170" width="125" />
											
											                        </div>
											                    </c:when>
											                    <c:otherwise>
											                        <div id="khongCoAnh">
											                      
											                            <img src="<c:url value='/resources/images/No_Image.jpg' />" class="img-border" height="170" width="125" title="Chủ hồ sơ" />
											                        </div>
											                    </c:otherwise>
											                </c:choose>									       					                       										                  								                    									               
											         </div>								        
											    </div>
										    </div>
										    <div id="dialog-image_photoCompare" style="display: block;width: 125px;height: 170px;margin-top: 10px;margin-left: 5px;">
												<div class="centerCaption">       
											         <label>Tham chiếu</label>								             									                 
											         <div style="text-align:center;" id="thamChieu">	
														    <c:choose>
											                    <c:when test="${not empty HSTrung1.photo_O}">
											                        <div id="coAnhMat">
											                            <img src="data:image/jpg;base64,${HSTrung1.photo_O}"
											                                 class="img-border doGetAJpgSafeVersion" height="170" width="125" />
											
											                        </div>
											                    </c:when>
											                    <c:otherwise>
											                        <div id="khongCoAnh">
											                      
											                            <img src="<c:url value='/resources/images/No_Image.jpg' />" class="img-border" height="170" width="125" title="Tham chiếu" />										                           
											                        </div>
											                    </c:otherwise>
											                </c:choose>									       					                       										                  								                    									               
											         </div>								        
											    </div>
										    </div>
										    </div>
										</fieldset>
									</div>
								</div>
								<div class="col-md-12 col-sm-12">
									<fieldset style="padding: 30px 10px;">
										<legend>Thông tin chủ hồ sơ</legend>
										<div id="gioiTinh">- Giới tính: ${chuHS.gender}</div>
										<div id="thuongTru">- Hộ khẩu thường trú: ${chuHS.address}</div>
										<div id="danToc">- Dân tộc: ${chuHS.nation}</div>
										<div id="tonGiao">- Tôn giáo: ${chuHS.religion}</div>
									</fieldset>
								</div>
							</div>
						</div>
						<div id="ajax-load-qa"></div>	
						<div id="ajax-load-qa-up"></div>
							<!-- Model tạm trú -->
							<div class="modal fade" id="tamTru" tabindex="-1" role="dialog" aria-labelledby="exampleModalCenterTitle" aria-hidden="true" >
							  <div class="modal-dialog modal-dialog-centered" role="document" style="width: 875px;">
							    <div class="modal-content">
							      <div class="modal-header">
							        <h5 class="modal-title" id="exampleModalLongTitle" style="display: inline-block;">TẠM TRÚ</h5>
							        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
							          		<img style="width: 25px;height: 25px;" src="<c:url value='/resources/images/dongform.png' />" title="Đóng" />
							        </button>
							      </div>							      
							      <div class="modal-body" style="height: 350px; overflow: auto;" id="idTamTru">
							       		
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
							<!-- Model tài liệu -->
							<div class="modal fade" id="taiLieuDK" tabindex="-1" role="dialog" aria-labelledby="exampleModalCenterTitle" aria-hidden="true" >
							  <div class="modal-dialog modal-dialog-centered" role="document" style="width: 1000px">
							    <div class="modal-content">
							      <div class="modal-header">
							        <h5 class="modal-title" id="exampleModalLongTitle" style="display: inline-block;">TÀI LIỆU ĐÍNH KÈM</h5>
							        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
							          		<img style="width: 25px;height: 25px;" src="<c:url value='/resources/images/dongform.png' />" title="Đóng" />
							        </button>
							      </div>							      
							      <div class="modal-body" style="height: 500px;overflow: auto;" id="idTaiLieu">
							       		
							       		
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
							<!-- Model thông hành  -->
							<div class="modal fade" id="thongHanh" tabindex="-1" role="dialog" aria-labelledby="exampleModalCenterTitle" aria-hidden="true" >
							  <div class="modal-dialog modal-dialog-centered" role="document" style="width: 1000px">
							    <div class="modal-content">
							      <div class="modal-header">
							        <h5 class="modal-title" id="exampleModalLongTitle" style="display: inline-block;">THÔNG HÀNH</h5>
							        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
							          		<img style="width: 25px;height: 25px;" src="<c:url value='/resources/images/dongform.png' />" title="Đóng" />
							        </button>
							      </div>							      
							      <div class="modal-body" style="height: 450px;overflow: auto;" id="idThongHanh">
							      		
							      </div>
							      <div class="modal-footer" style="padding: 5px;">
							       		<button type="button" class="btn btn-warning" data-dismiss="modal" aria-label="Close" style="float: right;">
							       			<span class="glyphicon glyphicon-remove"></span> Đóng
							       		</button>
							       		
							      </div>
							    </div>
							  </div>
							</div>	
							<!-- ---------------------------------------------------------------------------->
								<!-- Modal Lịch sử cấp phát -->
							<div class="modal fade" id="lichsuCP" tabindex="-1" role="dialog" aria-labelledby="exampleModalCenterTitle" aria-hidden="true" >
							  <div class="modal-dialog modal-dialog-centered" role="document" style="width: 1200px;">
							    <div class="modal-content">
							      <div class="modal-header">
							        <h5 class="modal-title" id="exampleModalLongTitle" style="display: inline-block;">CẤP PHÁT HỘ CHIẾU</h5>
							        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
							          		<img style="width: 25px;height: 25px;" src="<c:url value='/resources/images/dongform.png' />" title="Đóng" />
							        </button>
							      </div>							      
							      <div class="modal-body" style="height: 500px; overflow: auto;" id="idCapPhat">
							      		
							      </div>
							      <div class="modal-footer" style="padding: 5px;">
							       		<button type="button" class="btn btn-warning" data-dismiss="modal" aria-label="Close" style="float: right;">
							       			<span class="glyphicon glyphicon-remove"></span> Đóng
							       		</button>
							       		
							      </div>
							    </div>
							  </div>
							</div>
							<!-- ----------------------------------------------------------------------------------------- -->
							<!-- Modal XNC -->
							<div class="modal fade" id="thongTinXNC" tabindex="-1" role="dialog" aria-labelledby="exampleModalCenterTitle" aria-hidden="true" >
							  <div class="modal-dialog modal-dialog-centered" role="document" style="width: 1000px;">
							    <div class="modal-content">
							      <div class="modal-header">
							        <h5 class="modal-title" id="exampleModalLongTitle" style="display: inline-block;">XUẤT NHẬP CẢNH</h5>
							        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
							          		<img style="width: 25px;height: 25px;" src="<c:url value='/resources/images/dongform.png' />" title="Đóng" />
							        </button>
							      </div>							      
							      <div class="modal-body" style="height: 450px; overflow: auto;" id="ttXuatNhap">
							      		
							      </div>
							      <div class="modal-footer" style="padding: 5px;">
							       		<button type="button" class="btn btn-warning" data-dismiss="modal" aria-label="Close" style="float: right;">
							       			<span class="glyphicon glyphicon-remove"></span> Đóng
							       		</button>
							       		
							      </div>
							    </div>
							  </div>
							</div>
							<!-- ----------------------------------------------------------------------------------------- -->
							<!-- Modal Mất/hủy -->
							<div class="modal fade" id="thongTinMathuy" tabindex="-1" role="dialog" aria-labelledby="exampleModalCenterTitle" aria-hidden="true" >
							  <div class="modal-dialog modal-dialog-centered" role="document" style="width: 1000px;">
							    <div class="modal-content">
							      <div class="modal-header">
							        <h5 class="modal-title" id="exampleModalLongTitle" style="display: inline-block;">DANH SÁCH MẤT/HỦY</h5>
							        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
							          		<img style="width: 25px;height: 25px;" src="<c:url value='/resources/images/dongform.png' />" title="Đóng" />
							        </button>
							      </div>							      
							      <div class="modal-body" style="height: 450px; overflow: auto;" id="ttMathuy">
							      		
							      </div>
							      <div class="modal-footer" style="padding: 5px;">
							       		<button type="button" class="btn btn-warning" data-dismiss="modal" aria-label="Close" style="float: right;">
							       			<span class="glyphicon glyphicon-remove"></span> Đóng
							       		</button>
							       		
							      </div>
							    </div>
							  </div>
							</div>
							<!-- -------------------------------------------------------------------------------------------- -->
							<!-- Modal thông tin cá nhân -->
							<div class="modal fade" id="thongTinHS" tabindex="-1" role="dialog" aria-labelledby="exampleModalCenterTitle" aria-hidden="true" >
							  <div class="modal-dialog modal-dialog-centered" role="document" style="width: 1200px;">
							    <div class="modal-content">
							      <div class="modal-header">
							        <h5 class="modal-title" id="exampleModalLongTitle" style="display: inline-block;">CHI TIẾT THÔNG TIN HỒ SƠ</h5>
							        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
							          		<img style="width: 25px;height: 25px;" src="<c:url value='/resources/images/dongform.png' />" title="Đóng" />
							        </button>
							      </div>							      
							      <div class="modal-body" style="height: 400px;">
							       		
							       		<div class="col-md-4 col-md-4">
											<fieldset style="padding: 30px 10px;">
												<legend>Thông tin chủ hồ sơ</legend>
												<div>Họ tên: <label id="hoTen">${chuHS.fullName}</label></div>
												<div>Ngày sinh: <label id="ngaySinh">${chuHS.dob}</label></div>
												<div>CMND/CCCD: <label id="chungMinh">${chuHS.nin}</label></div>
												<div>Hộ khẩu thường trú: <label id="thuongTru">${chuHS.address}</label></div>
												<div>Dân tộc: <label id="danToc">${chuHS.nation}</label></div>
												<div>Tôn giáo: <label id="tonGiao">${chuHS.religion}</label></div>
												<div><label id="tonGiao1">Giới tính: ${chuHS.gender}</label></div>
											</fieldset>
										</div>
										<div class="col-md-8 col-md-8" id="ttCaNhan">
										</div>
							      </div>
							      <div class="modal-footer" style="padding: 5px;">
							       		<button type="button" class="btn btn-warning" data-dismiss="modal" aria-label="Close" style="float: right;">
							       			<span class="glyphicon glyphicon-remove"></span> Đóng
							       		</button>
							       		
							      </div>
							    </div>
							  </div>
							</div>							
						<div class="form-left">
							<div class="col-md-12 col-sm-12" style="padding: 0px;">
								<fieldset>
									<legend>Danh sách cá nhân nghi trùng</legend>
										<div style="height: 350px;overflow-y: auto;overflow-x: hidden;">
									      	<table id="dsNghiTrung" class="table table-bordered table-sm table-hover" cellspacing="0" width="100%">
											  <thead>
											    <tr>
											      <th class="th-sm">Họ tên
											
											      </th>											 
											      <th class="th-sm">Giới tính
											
											      </th>
											      <th class="th-sm">Ngày sinh
											
											      </th>
											      <th class="th-sm">CMND/CCCD
											
											      </th>
											      <th class="th-sm">Sinh trắc
											
											      </th>
											      <th class="th-sm">Nơi sinh
											
											      </th>
											      <th class="th-sm">Số HC/TH
											      </th>
											      <th class="th-sm">Thông tin
											      </th>	
											      <th class="th-sm" style="display: none;"></th>							     
											    </tr>
											  </thead>										
											  <tbody>
												   <c:forEach items="${dsBiTrung}" var="itm_trung">
													    <tr>
													      	<td>${itm_trung.fullName_O}
													      		<form:checkbox path="processJobIds" id="chk_${itm_trung.transactionId_O}" style="display:none;"  value="${itm_trung.transactionId_O}"/>
													      		<form:checkbox path="saveFullName" id="chkName_${itm_trung.transactionId_O}" style="display:none;"  value="${itm_trung.fullName_O}"/>
													      	</td>
													      	
													      	<td>${itm_trung.gender_O}</td>
													      	<td class="align-central">${itm_trung.dob_O}</td>
													      	<td>${itm_trung.nin_O}</td>
													      	<td class="align-right">${itm_trung.scoreBms_O}</td>													      	
													      	<td>${itm_trung.pob_O}</td>
													      	<td>${itm_trung.passportNo_O}</td>
													      	<td><a href="#" data-toggle="modal" onclick="xemThongTinCN('${itm_trung.transactionId_O}');" data-target="#thongTinHS">Xem</a></td>													      											      													  
													    	<td style="display: none;">${itm_trung.transactionId_O}</td>
													    </tr>											   
												   </c:forEach>
												  
											</tbody>								
											</table>
											<c:if test="${empty dsBiTrung}">
											  	<div class="style-no-record opentb-1">Không tìm thấy kết quả</div>
											</c:if>
								      </div>
								</fieldset>
							</div>	
							<div class="col-md-12 col-sm-12">
								<table id="tb_btn">
									<tr>
										<td><input type="button" value="X/N cảnh" id="btn_XNCanh" data-toggle="modal" data-target="#thongTinXNC" class="btn btn-success"/> </td>
										<td><input type="button" value="ABTC VN" disabled="disabled" id="" class="btn btn-success"/> </td>
										<td><input type="button" value="Vi phạm" disabled="disabled" id="" class="btn btn-success"/> </td>
										<td><input type="button" value="Duyệt nhập" disabled="disabled" id="" class="btn btn-success"/> </td>
										<td><input type="button" value="Nhận trở lại" disabled="disabled" id="" class="btn btn-success"/> </td>
										<td><input type="button" value="Kết hôn" disabled="disabled" id="" class="btn btn-success"/> </td>
										<td><input type="button" value="Về thường trú" disabled="disabled" id="" class="btn btn-success"/> </td>
										<td><input type="button" value="Thôi QT" disabled="disabled" id="" class="btn btn-success"/> </td>
									</tr>
									<tr style="height: 10px;"></tr>
									<tr>
										<td><input type="button" value="Hộ chiếu" id="btn_hoChieu" data-toggle="modal" data-target="#lichsuCP" class="btn btn-success"/> </td>
										<td><input type="button" value="Tạm trú" id="btn_tamTru" data-toggle="modal" data-target="#tamTru" class="btn btn-success"/> </td>
										<td><input type="button" value="TT Tổng hợp" id="btn_tongHop" class="btn btn-success"/> </td>
										<td><input type="button" value="Xác minh NS" disabled="disabled" id="" class="btn btn-success"/> </td>
										<td><input type="button" value="Lện TX" id="" disabled="disabled" class="btn btn-success"/> </td>
										<td><input type="button" value="Xin con nuôi" disabled="disabled" id="" class="btn btn-success"/> </td>
										<td><input type="button" value="Nhập QT" id="" disabled="disabled" class="btn btn-success"/> </td>
										<td><input type="button" value="Trở lại QT" id="" disabled="disabled" class="btn btn-success"/> </td>
									</tr>
									<tr style="height: 10px;"></tr>
									<tr>
										<td><input type="button" value="Thông hành" id="btn_thongHanh" data-toggle="modal" data-target="#thongHanh" class="btn btn-success"/> </td>
										<td><input type="button" value="GP XNC" id="" disabled="disabled" class="btn btn-success"/> </td>
										<td><input type="button" value="Xác nhận NS" id="" disabled="disabled" class="btn btn-success"/> </td>	
										<td><input type="button" value="Mất/hủy" id="btn_Mathuy" data-toggle="modal" data-target="#thongTinMathuy" class="btn btn-success"/> </td>									
									</tr>
								</table>
							</div>	
													
							<div class="col-md-12 col-sm-12" style="margin-top: 10px;" id="idPrintFinger">
								
							</div>
						</div>
						<div class="col-md-6 magin-btm-10">
							<fieldset>
								<legend>Ghi chú</legend>
								<div style="height: 100px;overflow: auto;">
									<div>${noteP}</div>
									<div>${noteO}</div>
									<div>${noteN}</div>
								</div>
							</fieldset>
						</div>
						<div class="col-md-6 magin-btm-10">
							<fieldset>
								<legend>Thêm ghi chú</legend>
								<form:textarea rows="" cols="" path="listName" style="height: 100px;overflow: auto;width: 100%;"></form:textarea>
							</fieldset>
						</div>


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
				</div>
			</div>
		</div>
	</div>
	<div style="display: flex;flex: 0 auto;justify-content: center;">
	<div class="waitingWhenDoneWaiting" style="display:block; position: fixed; bottom:-5px; border-radius: 8px; border: 2px solid; background-color: rgb(255, 255, 255); z-index: +1000;border-color: #2a7044 #2a7044 transparent #2a7044;">
		<div style="margin: 10px"> 
			<button type="button"  onclick="showDocument();" data-toggle="modal" data-target="#taiLieuDK" name="approve" class="btn btn-success">
				<span class="glyphicon glyphicon-file"></span> Tài liệu đính kèm
			</button>
			<button type="button" id="btn_Khop"  onclick="saveCompare();" name="approve" class="btn btn-success">
				<span class="glyphicon glyphicon-ok"></span> Khớp 
			</button>
			<button type="button"  onclick="noSaveCompare();" name="approve" class="btn btn-success">
				<span class="glyphicon glyphicon-info-sign"></span> Không khớp
			</button>
			<button type="button"  onclick="stopCheck();" name="approve" class="btn btn-success">
				<span class="glyphicon glyphicon-stop"></span> Ngừng khớp	
			</button>
			<c:if test="${stt != count}">
				<button type="button"  onclick="nextTransaction()" name="approve" class="btn btn-success">
					<span class="glyphicon glyphicon-circle-arrow-right btn-boot" style="margin-right: 2px;"></span ><span class="btn-boot"> Hồ sơ tiếp theo</span>
				</button>
			</c:if>
			<c:if test="${stt == count}">
				<button type="button"  onclick="nextTransaction()" name="approve" class="btn btn-success">
					<span class="glyphicon glyphicon-circle-arrow-left btn-boot" style="margin-right: 2px;"></span ><span class="btn-boot"> Quay lại</span>
				</button>
			</c:if>
		</div>
	</div>	
	</div>
	<!-- Message lưu hồ sơ -->
	<div class="modal fade" id="messageLHS" tabindex="-1" role="dialog" aria-labelledby="exampleModalCenterTitle" aria-hidden="true" >
	  <div class="modal-dialog modal-dialog-centered" role="document" style="width: 300px;">
	    <div class="modal-content">
	      <div class="modal-header">
	        <h5 class="modal-title" id="exampleModalLongTitle" style="display: inline-block;">THÔNG BÁO</h5>
	        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
	          		<img style="width: 25px;height: 25px;" src="<c:url value='/resources/images/dongform.png' />" title="Đóng" />
	        </button>
	      </div>
	      <div class="modal-body" id="idMessage">
	      		<div>
	      			<i class="glyphicon glyphicon-question-sign" style="font-size: 2em;"></i>
	      			<p style="display: inline-block;position: absolute;margin-left: 10px;margin-top: 5px;">Chủ hồ sơ không có cá nhân nghi trùng?</p>
	      		</div>
	      </div>							      
	      <div class="modal-footer" style="padding: 5px;">
	       		<button type="button" class="btn btn-warning" data-dismiss="modal" aria-label="Close" style="float: right;">
	       			<span class="glyphicon glyphicon-remove btn-boot" style="margin-right: 2px;"></span ><span class="btn-boot"> KHÔNG</span>
	       		</button>
	       		<button type="button" onclick="nextTransaction();" class="btn btn-success" data-dismiss="modal" aria-label="Close" style="float: right;margin-right: 10px;">
	       			<span class="glyphicon glyphicon-circle-arrow-right btn-boot" style="margin-right: 2px;"></span ><span class="btn-boot"> HỒ SƠ TIẾP THEO</span>
	       		</button>
	       		
	      </div>
	    </div>
	  </div>
	</div>	
	<!-- ---------------------------------------------------------------------------->
	<!-- Model hủy hộ chiếu  -->
	<div class="modal fade" id="huyHoChieu" tabindex="-1" role="dialog" aria-labelledby="exampleModalCenterTitle" aria-hidden="true" >
	  <div class="modal-dialog modal-dialog-centered" role="document" style="width: 1000px">
	    <div class="modal-content">
	      <div class="modal-header">
	        <h5 class="modal-title" id="exampleModalLongTitle" style="display: inline-block;">THÔNG TIN HỘ CHIẾU ĐỢI HỦY</h5>
	        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
	          		<img style="width: 25px;height: 25px;" src="<c:url value='/resources/images/dongform.png' />" title="Đóng" />
	        </button>
	      </div>							      
	      <div class="modal-body" style="height: 450px;" id="idHuyHC">
	      		
	      </div>
	      <div class="modal-footer" style="padding: 5px;">
	       		<button type="button" class="btn btn-warning" data-dismiss="modal" aria-label="Close" style="float: right;">
	       			<span class="glyphicon glyphicon-remove"></span> Đóng
	       		</button>
	       		<button type="button" onclick="destroyTran();" class="btn btn-success" data-dismiss="modal" aria-label="Close" style="float: right;margin-right: 10px;">
	       			<span class="glyphicon glyphicon-print"></span > Ghi nhận
	       		</button>
	      </div>
	    </div>
	  </div>
	</div>	
	<!-- ---------------------------------------------------------------------------->
	
	<!--<div class="waitingWhenDoneWaiting" style="display:block; position: fixed; bottom:5px; right:10px; border-radius: 5px; border: 1px solid gray; background-color: rgb(255, 255, 255); z-index: +1000">
			<div style="margin: 10px"> 
				<button type="button"  onclick="openBoxReject();" name="approve" class="btn btn-primary">
					  <span class="glyphicon glyphicon-list-alt"></span> Tạo danh sách
				</button>
				
			</div>
	</div>-->
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
	var checkLost = '';
	function nextTransaction(){
		$('#ajax-load-qa').css('display', 'block');
		document.forms["formData"].action = '<c:url value="/servlet/investionProcess/nextInfoByJobID" />';
		document.forms["formData"].submit();
	}

	var tbNghiTrung = $('#dsNghiTrung').DataTable({
		"ordering": false,
		"pagingType": "simple"
	});
	var ID_XNC = 'null';

	$('#danhsachXNC').DataTable({
		"ordering": false,
		"pagingType": "simple"
	});
	
	/*$('#tbTreEm').DataTable({
		"ordering": false
	});*/
	function historyEventDataSmark_clicked(itemTriggered){	 
		//info = info.replace(/\n/g,"<br/>");
		$("#infoDialog").dialog('option', 'title', "Log Data");
		$("#infoDialog").text(itemTriggered);
		$("#infoDialog").dialog('open');
	}
	
	function showDocument(){
		$('#idTaiLieu').html("");
		var url = '${getDocumentUrl}/${idMaster}';
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

	function destroyTran(){
		var tranId =  $('input[name="processJobIds"]:checked').val();
		var url = '${successPassportUrl}';
		var yourArray = {};
		yourArray['tranid'] = tranId;
		yourArray['name'] = $('#lostHoTen').val();
		yourArray['dob'] = $('#lostNgaySinh').val();
		yourArray['nin'] = $('#lostCMND').val();
		yourArray['ppno'] = $('#lostSoHC').val();
		yourArray['datepp'] = $('#lostNgayCap').val();
		yourArray['datepp1'] = $('#lostHanHC').val();
		yourArray['placepp'] = $('#lostNoiCap').val();
		yourArray['datelost'] = $('#lostNgayBaoMat').val();
		$.ajax({
			type : "POST",
			url : url,
			data : JSON.stringify(yourArray),
			contentType : 'application/json',
			success: function(data) {
				if(data == 'Y'){
					document.forms["formData"].action = '<c:url value="/servlet/investionProcess/saveCompareJob/Y" />';
					document.forms["formData"].submit();
				}else{
					$.notify('Hủy hộ chiếu không thành công!', {
						globalPosition: 'bottom left',
						className: 'error',
					});
				}
		    },
		    error: function(e){}
		 });
	}
	
	$(function(){
		
			$('#danhsachXNC').DataTable({
				"ordering": false
			});
			if('${showBody}' == 'N'){
				$('#dsNghiTrung tbody').css('display', 'none');
			}else{
				//Chọn bản ghi đầu tiên
				ID_XNC = $("#dsNghiTrung tbody tr:first td:last").text();
				
			}
			if(ID_XNC != ''){
				$('#ajax-load-qa').css('display', 'block');
				$("#dsNghiTrung tbody tr:first").addClass("back-gr");
				$("#dsNghiTrung tbody tr:first input[type='checkbox']").prop('checked', true);
				setTimeout(function(){
					var urlFinger = '${getFingerPrintUrl}/${jobCompare}/' + ID_XNC;
					
					$.ajax({
						type : "POST",
						url : urlFinger,
						success: function(data) {
							$('#idPrintFinger').html(data);
							$('#ajax-load-qa').css('display', 'none');
				        },
				        error: function(e){}
				 	});			    
				}, 50);
			}
			
			
			//---------------------------------
			
			/*---Trường hợp không có hồ sơ trùng thoát ra ngay---*/
			
			
			if('${action}' == 'Y'){
				//alert('0');
				if('${khongTrung}' == 'Y'){
					//noSaveCompare();
					$('#btn_Khop').attr('disabled', 'true');
					$('#messageLHS').modal();
				}
			}else{
				//alert('1');
				stopCheck();
			}
			
			//----------------------------------------------------
			
			
			
		
	     
		   $('#dsNghiTrung tbody').on('click', 'tr', function(){
		    	 var dataNT = tbNghiTrung.row(this).data();
		    	 if(ID_XNC == dataNT[8]){
		    		 return;
		    	 }
		    	 $('#ajax-load-qa').css('display', 'block');	
		    	 $('#dsNghiTrung > tbody > tr').removeClass("back-gr");
		    	 $(this).addClass("back-gr");
		    	 $("#dsNghiTrung tbody input[type='checkbox']").prop('checked', false);
		    	 $("#chk_" + dataNT[8]).prop('checked', true);
		    	 $('#chkName_' + dataNT[8]).prop('checked', true);
		         ID_XNC = dataNT[8];
		         var Url = '${getFingerPrintUrl}/${jobCompare}/' + ID_XNC;					
				 $.ajax({
					type : "POST",
					url : Url,
					success: function(data) {
						$('#idPrintFinger').html(data);
						var urlLink = '${getFacialUrl}/' + ID_XNC;
						facialImport(urlLink);
						//$('#ajax-load-qa').css('display', 'none');
				    },
				    error: function(e){}
				 });	
		    });
			
		$('#btn_XNCanh').click(function(){
			$('#ttXuatNhap').html("");
			var url = '${getInfoXNCTransactionUrl}/' + ID_XNC;
			$('#ajax-load-qa').css('display', 'block');
			$.ajax({
				url : url,
				cache: false,
				type: "POST",
				success : function(data) {
					$('#ttXuatNhap').html(data);
					$('#ajax-load-qa').css('display', 'none');				
				}
			});
		})
		
		$('#btn_Mathuy').click(function(){
			$('#ttMathuy').html("");
			var url = '${getInfoMathuyTransactionUrl}/' + ID_XNC;
			$('#ajax-load-qa').css('display', 'block');
			$.ajax({
				url : url,
				cache: false,
				type: "POST",
				success : function(data) {
					$('#ttMathuy').html(data);
					$('#ajax-load-qa').css('display', 'none');				
				}
			});
		})
		
		
		$('#btn_hoChieu').click(function(){
			$('#idCapPhat').html("");
			var url = '${getLichSuCPTransactionUrl}/' + ID_XNC;
			$('#ajax-load-qa').css('display', 'block');
			$.ajax({
				url : url,
				cache: false,
				type: "POST",
				success : function(data) {
					$('#idCapPhat').html(data);
					$('#ajax-load-qa').css('display', 'none');				
				}
			});
		});
		
		$('#btn_tamTru').click(function(){
			$('#idTamTru').html("");
			var url = '${getStayTransactionUrl}/' + ID_XNC;
			$('#ajax-load-qa').css('display', 'block');
			$.ajax({
				url : url,
				cache: false,
				type: "POST",
				success : function(data) {
					$('#idTamTru').html(data);
					$('#ajax-load-qa').css('display', 'none');				
				}
			});
		});
		
		$('#btn_thongHanh').click(function(){
			$('#idThongHanh').html("");
			var url = '${getGoTransactionUrl}/' + ID_XNC;
			$('#ajax-load-qa').css('display', 'block');
			$.ajax({
				url : url,
				cache: false,
				type: "POST",
				success : function(data) {
					$('#idThongHanh').html(data);
					$('#ajax-load-qa').css('display', 'none');				
				}
			});
		});
		
		
		$("#infoDialog").dialog( {
			autoOpen : false,
			width : 750,
			height : 120,
			modal : true,
			bgiframe : true,
			cache :false,
			closeOnEscape: false
		});
	});
	
	function facialImport(urlLink){
		$.ajax({
			type : "POST",
			url : urlLink,
			success: function(data) {
				$('#thamChieu').html(data);
				$('#ajax-load-qa').css('display', 'none');
		    },
		    error: function(e){}
		 });
	}
	
	function showFormLost(transId){
		var urlLink = '${distroyTransactionUrl}';
		$('#idHuyHC').html('');		
		$.ajax({
			type : "POST",
			url : urlLink,
			data : {
				transactionId : transId
			},
			success: function(data) {
				$('#idHuyHC').html(data);	
				$('#huyHoChieu').modal();
		    },
		    error: function(e){}
		 });
	}
	
	
	function testLostId(transId){
		var url = '${testLostIdUrl}';
		$.ajax({
			url : url,
			cache: false,
			async:false,
			type: "POST",
			data: {
				transactionId : transId
			},
			success : function(data) {
				checkLost = data;
			}
		});
	}
	
	
	function xemThongTinCN(value){
		$('#ttCaNhan').html("");
		var url = '${getInfoTransactionUrl}/' + value;
		$('#ajax-load-qa').css('display', 'block');
		$.ajax({
			url : url,
			cache: false,
			type: "POST",
			success : function(data) {
				$('#ttCaNhan').html(data);
				$('#ajax-load-qa').css('display', 'none');				
			}
		});
	}
	
	
	function stopCheck() {
		document.forms["formData"].action = '${nextProcessUrl}/${stage}';
		document.forms["formData"].submit();
	}
	
	
	function saveCompare() {
		var tranId =  $('input[name="processJobIds"]:checked').val();
		$('#ajax-load-qa').css('display', 'block');
		testLostId(tranId);
		if(checkLost == 'Y'){
			//showFormLost(tranId);
			var urlLink = '${distroyTransactionUrl}';
			$('#idHuyHC').html('');		
			$.ajax({
				type : "POST",
				url : urlLink,
				data : {
					transactionId : tranId
				},
				success: function(data) {
					$('#ajax-load-qa').css('display', 'none');
					$('#idHuyHC').html(data);	
					$('#huyHoChieu').modal();
			    },
			    error: function(e){}
			 });
		}
		if(checkLost == 'N'){
			document.forms["formData"].action = '<c:url value="/servlet/investionProcess/saveCompareJob/Y" />';
			document.forms["formData"].submit();
		}
	}
	
	function noSaveCompare() {
		document.forms["formData"].action = '<c:url value="/servlet/investionProcess/saveCompareJob/N" />';
		document.forms["formData"].submit();
	}

	function doSubmitSave() {
		
		/*$('#ajax-load-qa').css('display', 'block');
		var packageNumber = $('#packageNumber').val();
		var createDate = $('#createDate').val();
		var endDate = $('#endDate').val();
		var styleList = $('#styleList').find(":selected").val();
		$.ajax({
			url : '${searchInvesBUrl}',
			cache: false,
			type: "POST",
			data:{
				packageNumber: packageNumber,
				createDate: createDate,
				endDate: endDate,
				styleList: styleList,
			},
			success : function(data) {
				$('#ajax-load-qa').css('display', 'none');
				var tb = $('#tbDanhSachHS').DataTable();	
				tb.clear();
				if(data.length > 0){
					$('.opentb-2').css('display', 'none');
		        	for(var i = 0; i < data.length; i++){
		        		tb.row.add([
		        		             data[i].packageId,
		        		             data[i].packageDate,
		        		             data[i].ricName	        		            
		        		]).draw(false);
		        	}					
				}else{					
					$('.opentb-2').css('display', 'block');
				}
			}
		});*/
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
		
		var loiKhopTT = '${loiKhop}';
		if(loiKhopTT != ''){
			$.notify(loiKhopTT, {
				globalPosition: 'bottom left',
				className: 'error',
			});
	    }
	
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



