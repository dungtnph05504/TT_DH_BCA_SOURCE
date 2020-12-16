<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display" %>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
<c:url var="prodUrl" value="/servlet/investigation/startinvestigation" />
<c:url var="newUrl" value="/servlet/investigation/newInvestigation" />
<c:url var="pendingJobUrl" value="/servlet/investigation/pendingJobList" />
<c:url var="investigationJobUrl" value="/servlet/investigation/investigation" />
<c:url var="invesProcessUrl" value="/servlet/investionProcess/showInvestigation" />
<c:url var="nextProcessUrl" value="/servlet/investionProcess/invesProcess" />
<c:url var="showMessageUrl" value="/servlet/investionProcess/showMessage" />
<c:url var="testOfficeIdUrl" value="/servlet/investionProcess/testOfficeId" />
<c:url var="thongtinchitiet" value="/servlet/central/tracuuhosohochieu" />
<c:url var="getTientringHsUrl" value="/servlet/central/getTientrinhHs" />
<c:url var="getChitietUrl" value="/servlet/central/chitiethoso" />
<c:url var="getInChitietUrl" value="/servlet/central/inchitiet" />
<c:url var="getInLSUrl" value="/servlet/central/inlichsu" />
<c:url var="getInTKUrl" value="/servlet/central/intokhai" />
<c:url var="getInTTKUrl" value="/servlet/central/thongtinkhac" />
<%-- <form:form commandName="jobList" id="form" name="form" > --%>
<style>
<!--

-->
.jconfirm .jconfirm-box div.jconfirm-content-pane .jconfirm-content {
    white-space: pre-wrap;
}
.cls-mg-bot {
	margin-top: 10px;
}


.speech-bubble {
    position: relative;
    background-color: #d9e8fb;
    height: 40px;
    width: 200px;
    margin-right: 60px;
    margin-top: 10px;
    line-height: 35px;
    text-align: center;
}

.speech-bubble7 {
    position: relative;
    background-color: #d3e8d5;
    height: 40px;
    width: 200px;
    margin-right: 60px;
    margin-top: 10px;
    line-height: 35px;
    text-align: center;
}

.speech-bubble8 {
    position: relative;
    background-color: #ffe6ce;
    height: 40px;
    width: 200px;
    margin-right: 60px;
    margin-top: 10px;
    line-height: 35px;
    text-align: center;
}

.speech-bubble9 {
    position: relative;
    background-color: #facecc;
    height: 40px;
    width: 200px;
    margin-right: 60px;
    margin-top: 10px;
    line-height: 35px;
    text-align: center;
}

.hhhh {
    border: 2px solid;
    border-color: #FF0000;
}

.speech-bubble2 {
    position: relative;
    min-height: 80px;
    width: 190px;
    margin: 0px;
    padding: 2px;
    position: absolute;
}

.speech-bor3 {
    position: relative;
    background-color: #000000;
    height: 1px;
    position: absolute;
}

.speech-bor {
    position: relative;
    background-color: #000000;
    height: 1px;
    position: absolute;
}

.speech-bor2 {
    position: relative;
    background-color: #000000;
    width: 1px;
    position: absolute;
}

.speech-bubble:before {
    content: '';
    position: absolute;
    width: 0;
    height: 0;
    border: 20px solid;
    border-top-color: #d9e8fb;
    border-right-color: #d9e8fb;
    border-bottom-color: #d9e8fb;
    border-left-color: #FFFFFF;
    right: 100%;
}

.speech-bubble7:before {
    content: '';
    position: absolute;
    width: 0;
    height: 0;
    border: 20px solid;
    border-top-color: #d3e8d5;
    border-right-color: #d3e8d5;
    border-bottom-color: #d3e8d5;
    border-left-color: #FFFFFF;
    right: 100%;
}

.speech-bubble8:before {
    content: '';
    position: absolute;
    width: 0;
    height: 0;
    border: 20px solid;
    border-top-color: #ffe6ce;
    border-right-color: #ffe6ce;
    border-bottom-color: #ffe6ce;
    border-left-color: #FFFFFF;
    right: 100%;
}

.speech-bubble9:before {
    content: '';
    position: absolute;
    width: 0;
    height: 0;
    border: 20px solid;
    border-top-color: #facecc;
    border-right-color: #facecc;
    border-bottom-color: #facecc;
    border-left-color: #FFFFFF;
    right: 100%;
}

.speech-bor:after {
    content: '';
    position: absolute;
    width: 0;
    height: 0;
    border: 3px solid;
    border-top-color: #FFFFFF;
    border-right-color: #FFFFFF;
    border-bottom-color: #FFFFFF;
    border-left-color: #000000;
    top: -200%;
    left: 100%;
}

.speech-bubble:after {
    content: '';
    position: absolute;
    width: 0;
    height: 0;
    border: 20px solid;
    border-top-color: #FFFFFF;
    border-right-color: #FFFFFF;
    border-bottom-color: #FFFFFF;
    border-left-color: #d9e8fb;
    /* top: 100%; */
    left: 100%;
}

.speech-bubble7:after {
    content: '';
    position: absolute;
    width: 0;
    height: 0;
    border: 20px solid;
    border-top-color: #FFFFFF;
    border-right-color: #FFFFFF;
    border-bottom-color: #FFFFFF;
    border-left-color: #d3e8d5;
    /* top: 100%; */
    left: 100%;
}

.speech-bubble8:after {
    content: '';
    position: absolute;
    width: 0;
    height: 0;
    border: 20px solid;
    border-top-color: #FFFFFF;
    border-right-color: #FFFFFF;
    border-bottom-color: #FFFFFF;
    border-left-color: #ffe6ce;
    /* top: 100%; */
    left: 100%;
}

.speech-bubble9:after {
    content: '';
    position: absolute;
    width: 0;
    height: 0;
    border: 20px solid;
    border-top-color: #FFFFFF;
    border-right-color: #FFFFFF;
    border-bottom-color: #FFFFFF;
    border-left-color: #facecc;
    /* top: 100%; */
    left: 100%;
}

.modal-open .modal {
    overflow: hidden;
    overflow-y: auto !important;
}

.form-inline .form-control {
    width: 100%;
}

.table-body tr.selected {
    background-color: #7fae46;
}

.selected {
    color: white;
    font-weight: bold;
}

.table-hover > tbody > tr:hover {
    background-color: #f5f5f5;
}

.table-hover > tbody > tr:hover td {
    color: black;
    cursor: pointer;
}
</style>
<form:form modelAttribute="formData" name="formData" > 
			<c:if test="${not empty requestScope.Errors}">
				<div style="color:Red;background-color:White;border-style:solid;border-color:Red;border-width:thin;">
					<c:forEach items="${requestScope.Errors}" var="errorMessage">
						<li>
							${errorMessage}
						</li>
					</c:forEach>
					
				</div>
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
								<td style="padding-left: 5px;font-weight: bold; vertical-align: top;">
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
<div class="form-desing">
   <div> 
   <div>
   <div class="row">
   <div class="ov_hidden">
      <div class="new-heading-2">TRA CỨU HỒ SƠ HỘ CHIẾU</div>
      <div style="clear: both"></div>
			<div style="min-height: 10px"></div>
				<div class="form-inline">
					<div class = "col-sm-10 col-md-10 col-lg-10">
                <div class="col-sm-4">
                    <label class="control-label" style="margin-bottom:5px;">Họ và tên</label>
                    <form:input path="name" id="name" name="name" class="form-control" type="text" />
                </div>
                <div class="col-sm-4">
                    <label class="control-label" style="margin-bottom:5px;">Ngày sinh</label>
                    <form:input type="text" path="dob" id="dob" name="dob" class="form-control" />
                </div>
                <div class="col-sm-4">
                    <label class="control-label" style="margin-bottom:5px;">Giới tính</label>
                   	<form:select path="gender" id="gender" name="gender" class="form-control" style="font-family: Arial; font-size: 12px;width: 100%;">
                            <option value="" selected="selected">Chọn giới tính</option>
                            <option value="M">Nam</option>
                            <option value="F">Nữ</option>
                            <option value="O">Khác</option>
                     </form:select>   
                </div>
                <div class="col-sm-4">
                    <label class="control-label" style="margin-bottom:5px;">Số CMND/CCCD</label>
                   	<form:input type="text" path="nin" id="nin" name="nin" class="form-control" />                  
                </div>
                <div class="col-sm-4">
                    <label class="control-label" style="margin-bottom:5px;">Số hộ chiếu</label>
                    <form:input type="text" path="passportNo" id="passportNo" name="passportNo" class="form-control" />
                </div>
                <div class="col-sm-4">
                    <label class="control-label" style="margin-bottom:5px;">Loại hộ chiếu</label>
                    <form:select path="passportType" id="passportType" name="passportType" class="form-control" style="font-family: Arial; font-size: 12px;width: 100%;">
                            <option value="" selected="selected">Tất cả</option>
                            <option value="P">Phổ thông</option>
                            <option value="PO">Ngoại giao</option>
                            <option value="PD">Công vụ</option>
                    </form:select>   
                </div>
                <div class="col-sm-4">
                    <label class="control-label" style="margin-bottom:5px;">Số biên nhận</label>
                    <form:input path="packageNumber" id="packageNumber" name="packageNumber" class="form-control" type="text" />
                </div>
                <div class="col-sm-4">
                    <label class="control-label" style="margin-bottom:5px;">Số DS</label>
                    </br>
                    <form:input type="text" path="packageCode" id="packageCode" name="packageCode" class="form-control" style="width:48%; float:left"/>
                    <form:select path="typeList" id="typeList" name="typeList" class="form-control"
											style="font-family: Arial; font-size: 12px;width: 52%;">
							<option value="" selected="selected">Tất cả DS</option>
                            <option value="A">Danh sách A</option>
                            <option value="B">Danh sách B</option>
                            <option value="C">Danh sách C</option>
					</form:select>
                </div>
                <div class="col-sm-12 col-md-12 col-lg-12">
                     <div class="pull-right">
                            <button class="btn color-blue" type="button" onclick="doApplyFilter()"><span class="glyphicon glyphicon-search"></span>Tìm kiếm</button>
                        	<button class="btn color-blue" type="reset" ng-show="RoleBtnDeleteInfoSearch" ng-click="TraCuuHSHCXoaDieuKien()"><span class="glyphicon glyphicon-trash"></span>Xóa điều kiện</button>
                     </div>   
            	</div>
            </div>
  			<div class="col-sm-2 col-md-2 col-lg-2">
                   <div class="hinhanh">
                       <img id="imagPhoto" src="/eppcentral/resources/images/No_Image.jpg" width="100">
                   </div>     
            </div>
	  		<br />
			<br />
			<div class="col-sm-12 col-md-12 col-lg-12">
					<fieldset class="scheduler-border">
							<legend>Danh sách hồ sơ hộ chiếu</legend>
							<div>
						      	<table id="dtBasicExample" class="table table-bordered table-sm table-hover" cellspacing="0" width="100%">
								  <thead>
								    <tr>
								      <th class="th-sm">
										 STT
								      </th>	
								      <th class="th-sm">Họ tên
								
								      </th>
								      <th class="th-sm">Giới tính
								
								      </th>
								      <th class="th-sm">Ngày sinh
								
								      </th>
								      <th class="th-sm">Nơi sinh
								
								      </th>
								      <th class="th-sm">Số CMND/CCCD
								
								      </th>
								      <th class="th-sm">Số hộ chiếu
								
								      </th>
								    </tr>
								  </thead>
								  <c:if test="${not empty dsXuLyA}">
									  <tbody>
									  	 <c:forEach items="${dsXuLyA}" var="item">
										    <tr id="id_${item.transactionId}" onclick="getDetailTrans('${item.transactionId}')">
										      <td class="align-central">
										      	  ${item.stt}
											  </td>	
										      <td>${item.fullName}</td>
										      <td>${item.gender}</td>
										      <td>${item.packageDate}</td>
										      <td>${item.placeOfBirth}</td>
										      <td>${item.nin}</td>
										      <td>${item.passportNo}</td>
										    </tr>
									    </c:forEach>
									  </tbody>
								  </c:if>
								</table>
								<input hidden="hiddden" id="transIDHidden" />
								<c:if test="${empty dsXuLyA}">
									 <div class="style-no-record">Không tìm thấy kết quả</div>
								</c:if>
								<c:if test="${not empty dsXuLyA}">
									<div class="col-md-5 col-sm-12" style="padding-left: 0px;">
										<div class="dataTables_info">Hiển thị ${startIndex} đến ${endIndex} của ${totalRecord} kết quả</div>
									</div>
									<div class="col-md-7 col-sm-12" style="padding-right: 0px;">
										<ul style="float: right;" class="pagination" id="pagination"></ul>
									</div>	
									<input type="hidden" name="pageNo" id="pageNo" />
								</c:if> 
						      </div>
						  </fieldset>
						  </div>
						  <div class="col-sm-12 col-md-12 col-lg-12 disable-controll">
                        <fieldset class="scheduler-border">
                            <legend class="scheduler-border"></legend>
                            <div>
                                <label class="col-sm-2 control-label text-right" for="soBienNhan">Số biên nhận</label>
                                <div class="col-sm-2">
                                    <input type="text" class="form-control" name="soBienNhan" id="soBienNhan" disabled="">
                                </div>
                                <label class="col-sm-2 control-label text-right" for="tinhTrangHoSo">Tình trạng hồ sơ</label>
                                <div class="col-sm-2">
                                    <input type="text" class="form-control" name="tinhtranghoso" id="tinhtranghoso" disabled="">
                                </div>
                                <label class="col-sm-2 control-label text-right">KQ hồ sơ</label>
                                <div class="col-sm-2">
                                    <input type="text" class="form-control" name="kqhoso" id="kqhoso" disabled="">
                                </div>
                                <div style="clear:both;height:5px;"></div>

                                <label class="col-sm-2 control-label text-right" for="soDsA">Số DS A</label>
                                <div class="col-sm-2">
                                    <input type="text" class="form-control" name="sodanhsachA" id="sodanhsachA" disabled="">
                                </div>
                                <label class="col-sm-2 control-label text-right" for="nguoiLapA">Người lập</label>
                                <div class="col-sm-2">
                                    <input type="text" value="" class="form-control" name="nguoilapA" id="nguoilapA" disabled="">
                                </div>
                                <label class="col-sm-2 control-label text-right" for="nguoiLapA">Ngày lập</label>
                                <div class="col-sm-2">
                                    <input type="text" value="" class="form-control" name="ngaylapA" id="ngaylapA" disabled="">
                                </div>
                                <div style="clear:both;height:5px;"></div>

                                <label class="col-sm-2 control-label text-right" for="soDsB">Số DS B</label>
                                <div class="col-sm-2">
                                    <input type="text" class="form-control"  name="sodanhsachB" id="sodanhsachB" disabled="">
                                </div>
                                <label class="col-sm-2 control-label text-right" for="nguoiLapB">Người lập</label>
                                <div class="col-sm-2">
                                    <input type="text" value="" class="form-control" name="nguoilapB" id="nguoilapB" disabled="">
                                </div>
                                <label class="col-sm-2 control-label text-right">Ngày lập</label>
                                <div class="col-sm-2">
                                    <input type="text" value="" class="form-control" name="ngaylapB" id="ngaylap" disabled="">
                                </div>

                                <div style="clear:both;height:5px;"></div>
                                <label class="col-sm-2 control-label text-right" for="nguoiDuyet">Người duyệt DS B</label>
                                <div class="col-sm-2">
                                    <input type="text" value="" class="form-control" name="nguoiduyetDSB" id="nguoiduyetDSB" disabled="">
                                </div>
                                <label class="col-sm-2 control-label text-right" for="nguoiDuyet">Ngày duyệt DS B</label>
                                <div class="col-sm-2">
                                    <input type="text" class="form-control" name="ngayduyetDSB" id="ngayduyetDSB" disabled="">
                                </div>
                                <div style="clear:both;height:5px;"></div>
                                <label class="col-sm-2 control-label text-right" for="yKienDuyet">Ý kiến duyệt DS B</label>
                                <div class="col-sm-6">
                                    <textarea class="form-control ng-binding" rows="3" name="ykienDSB" id="ykienDSB" disabled=""></textarea>
                                </div>
                                <div style="clear:both;height:5px;"></div>
                                <label class="col-sm-2 control-label text-right" for="soDsC">Số DS C</label>
                                <div class="col-sm-2">
                                    <input type="text" class="form-control" name="sodsC" id="sodsC" disabled="">
                                </div>
                                <label class="col-sm-2 control-label text-right">Ngày lập</label>
                                <div class="col-sm-2">
                                    <input type="text" class="form-control" name="ngaylapC" id="ngaylapC" disabled="">
                                </div>
                                <div style="clear:both;height:5px;"></div>
                                <label class="col-sm-2 control-label text-right" for="noiDungDeXuat">Nội dung đề xuất</label>
                                <div class="col-sm-10">
                                    <textarea class="form-control ng-binding" rows="3" name="noidungdexuat" id="noidungdexuat" disabled=""></textarea>
                                </div>
                            </div>
                        </fieldset>
                    </div>
                    <div class="col-sm-12 disable-controll">
                        <fieldset class="scheduler-border">
                            <legend class="scheduler-border">Thông tin hộ chiếu</legend>
                            <div>
                                <label class="col-sm-2 control-label text-right">Số hộ chiếu</label>
                                <div class="col-sm-2">
                                    <input type="text" class="form-control" name="sohochieu" id="sohochieu" disabled="">
                                </div>
                                <label class="col-sm-2 control-label text-right">Ngày cấp</label>
                                <div class="col-sm-2">
                                    <input type="text" class="form-control" name="ngaycapHc" id="ngaycapHc" disabled="">
                                </div>
                                <label class="col-sm-2 control-label text-right">Hạn HC</label>
                                <div class="col-sm-2">
                                    <input type="text" class="form-control" name="hanHc" id="hanHc" disabled="">
                                </div>
                                <div style="clear:both;height:5px;"></div>
                                <label class="col-sm-2 control-label text-right">Người in</label>
                                <div class="col-sm-2">
                                    <input type="text" class="form-control " name="nguoiin" id="nguoiin" disabled="">
                                </div>
                                <label class="col-sm-2 control-label text-right">Ngày in</label>
                                <div class="col-sm-2">
                                    <input type="text" class="form-control" name="ngayin" id="ngayin" disabled="">
                                </div>
                                <label class="col-sm-2 control-label text-right">Tình trạng</label>
                                <div class="col-sm-2">
                                    <input type="text" class="form-control" name="tinhtrang" id="tinhtrang" disabled="">
                                </div>
                                <div style="clear:both;height:5px;"></div>
                                <label class="col-sm-2 control-label text-right">Người trả</label>
                                <div class="col-sm-2">
                                    <input type="text" class="form-control" name="nguoitra" id="nguoitra" disabled="">
                                </div>
                                <label class="col-sm-2 control-label text-right">Ngày trả</label>
                                <div class="col-sm-2">
                                    <input type="text" class="form-control" id="ngaytra" name="ngaytra" disabled="">
                                </div>
                                <label class="col-sm-2 control-label text-right">Nơi trả hộ chiếu</label>
                                <div class="col-sm-2">
                                    <input type="text" class="form-control" id="noitrahochieu" name="noitrahochieu" disabled="">
                                </div>
                                <div style="clear:both;height:5px;"></div>
                                <label class="col-sm-2 control-label text-right">Người nhận</label>
                                <div class="col-sm-2">
                                    <input type="text" class="form-control" id="nguoinhan" name="nguoinhan" disabled="">
                                </div>
                                <label class="col-sm-2 control-label text-right">Số HS lưu</label>
                                <div class="col-sm-2">
                                    <input type="text" class="form-control" id="soHsluu" name="soHsluu" disabled="">
                                </div>
                                <label class="col-sm-2 control-label text-right">Hộ chiếu điện tử</label>
                                <div class="col-sm-2">
                                	<input type="checkbox" disabled="" id="hochieudientu" name="hochieudientu">
                                </div>
                                <div style="clear:both;height:5px;"></div>


                                <label class="col-sm-2 control-label text-right">ICAO 1</label>
                                <div class="col-sm-4">
                                    <input type="text" class="form-control" disabled="" id="icao1" name="icao1">
                                </div>
                                <label class="col-sm-2 control-label text-right">ICAO 2</label>
                                <div class="col-sm-4">
                                    <input type="text" class="form-control" disabled="" id="icao2" name="icao2">
                                </div>
                                <div style="clear:both;height:5px;"></div>
                                <div class="div-children" style="display:none">
                                    <div class="col-sm-2">
                                        <span><b>Trẻ em đi kèm</b></span>
                                    </div>
                                    <div class="col-sm-10">
                                    </div>
                                    <div style="clear:both;height:5px;"></div>
                                    <!-- ngRepeat: children in Childs -->
                                </div>
                                
                                <div style="clear:both;height:5px;"></div>
                                <div class="col-sm-2 col-md-2 col-lg-2">
                                </div>
                            </div>
                        </fieldset>
                    </div>
                    <div class="col-sm-12">
	                    <fieldset class="scheduler-border disable-controll">
	                        <legend class="scheduler-border"></legend>
	                        <div>
	                            <label class="col-sm-2 control-label text-right">Ngày tra</label>
	                            <div class="col-sm-2">
	                                <input type="text" class="form-control" disabled="" id="ngaytradt" name="ngaytradt">
	                            </div>
	                            <label class="col-sm-2 control-label text-right">Người tra ĐT</label>
	                            <div class="col-sm-2">
	                                <input type="text" class="form-control" disabled="" id="nguoitradt" name="nguoitradt">
	                            </div>
	                            <label class="col-sm-2 control-label text-right">Người nhập máy</label>
	                            <div class="col-sm-2">
	                                <input type="text" class="form-control" disabled="" id="nguoinhapmay" name="nguoinhapmay">
	                            </div>
	                            <div style="clear:both;height:5px;"></div>
	                            <label class="col-sm-2 control-label text-right">Ngày nhập</label>
	                            <div class="col-sm-2">
	                                <input type="text" class="form-control" disabled="" id="ngaynhap" name="ngaynhap">
	                            </div>
	                            
	                            <label class="col-sm-2 control-label text-right"></label>
	                            <div class="col-sm-2">
	
	                            </div>
	                            <div style="clear:both;height:5px;"></div>
	                        </div>
	                    </fieldset>
                	</div>
					</div>
					</div>
					<div style="display: flex;flex: 0 auto;justify-content: center;">
						<div id="btm-toolbar">
	                        <ul class="line" style="margin-bottom: 2px;">
	                            <li>
	                                <button class="btn color-blue" type="button" id="TraCuuHSHCInChiTiet" data-toggle="modal" data-target="#danhsachCT" onclick="showtientrinhHS()"><span class="glyphicon glyphicon-print"></span>&nbsp; In chi tiết</button>
	                            </li>
	                            <li>
	                                <button class="btn color-blue" type="button" id="TraCuuLS" data-toggle="modal" data-target="#danhsachLS" onclick="showLS()"><span class="glyphicon glyphicon-print"></span>&nbsp; In lịch sử</button>
	                            </li>
	                            <li>
	                                <button class="btn color-blue" type="button" id="TraCuuToKhai" data-toggle="modal" data-target="#danhsachTK" onclick="showTK()"><span class="glyphicon glyphicon-print"></span>&nbsp; In tờ khai</button>
	                            </li>
	                            <li>
	                                <button class="btn color-blue" type="button" id="ThongTinKhac" data-toggle="modal" data-target="#danhsachTTK" onclick="showTTK()"><span class="glyphicon glyphicon-list-alt"></span>&nbsp; Thông tin khác</button>
	                            </li>
                        	</ul>
                    	</div>
                    </div>
						<!-- Model phí bổ sung -->
							<div class="modal fade" id="danhsachCT" role="dialog">
							  <div class="modal-dialog modal-dialog-centered" role="document" style="width: 1000px;">
							    <div class="modal-content">
								      <div class="modal-header">
								        <h5 class="modal-title" id="exampleModalLongTitle" style="display: inline-block;">IN CHI TIẾT</h5>
								        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
								          		<img style="width: 25px;height: 25px;" src="<c:url value='/resources/images/dongform.png' />" title="Đóng" />
								        </button>
								      </div>		
								      <div class="modal-body" id="PrintContent" style="font-family:'Times New Roman', Arial, Tahoma">
									     
									  </div>
								      <div class="modal-footer">
							                <div class="pull-right" style="padding: 10px">
							                    <button class="btn color-blue" type="button" data-flag="false" onclick="printDiv('PrintContent');"><span class="glyphicon glyphicon-print"></span>&nbsp;&nbsp;In thông tin</button>
							                    <button class="btn color-orangeyellow" data-dismiss="modal" tabindex="13"><span class="glyphicon glyphicon-remove"></span>&nbsp;&nbsp;Đóng</button>
							                </div>
							           </div>
							  		</div>
								</div>	
							</div>
							
							<div class="modal fade" id="danhsachLS" role="dialog">
							  <div class="modal-dialog modal-dialog-centered" role="document" style="width: 1000px;">
							    <div class="modal-content">
								      <div class="modal-header">
								        <h5 class="modal-title" id="exampleModalLongTitle" style="display: inline-block;">IN LỊCH SỬ</h5>
								        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
								          		<img style="width: 25px;height: 25px;" src="<c:url value='/resources/images/dongform.png' />" title="Đóng" />
								        </button>
								      </div>		
								      <div class="modal-body" id="PrintContentLS" style="font-family:'Times New Roman', Arial, Tahoma">
									     
									  </div>
								      <div class="modal-footer">
							                <div class="pull-right" style="padding: 10px">
							                    <button class="btn color-blue" type="button" data-flag="false" onclick="printDiv('PrintContentLS');"><span class="glyphicon glyphicon-print"></span>&nbsp;&nbsp;In thông tin</button>
							                    <button class="btn color-orangeyellow" data-dismiss="modal" tabindex="13"><span class="glyphicon glyphicon-remove"></span>&nbsp;&nbsp;Đóng</button>
							                </div>
							           </div>
							  		</div>
								</div>	
							 </div>
							
							<div class="modal fade" id="danhsachTK" role="dialog">
							  <div class="modal-dialog modal-dialog-centered" role="document" style="width: 1000px;">
							    <div class="modal-content">
								      <div class="modal-header">
								        <h5 class="modal-title" id="exampleModalLongTitle" style="display: inline-block;">IN TỜ KHAI</h5>
								        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
								          		<img style="width: 25px;height: 25px;" src="<c:url value='/resources/images/dongform.png' />" title="Đóng" />
								        </button>
								      </div>		
								      <div class="modal-body" id="PrintContentTK" style="font-family:'Times New Roman', Arial, Tahoma">
									     
									  </div>
								      <div class="modal-footer">
							                <div class="pull-right" style="padding: 10px">
							                    <button class="btn color-blue" type="button" data-flag="false" onclick="printDiv('PrintContentTK');"><span class="glyphicon glyphicon-print"></span>&nbsp;&nbsp;In thông tin</button>
							                    <button class="btn color-orangeyellow" data-dismiss="modal" tabindex="13"><span class="glyphicon glyphicon-remove"></span>&nbsp;&nbsp;Đóng</button>
							                </div>
							           </div>
							  		</div>
								</div>	
							 </div>
							
							<div class="modal fade" id="danhsachTTK" role="dialog">
							  <div class="modal-dialog modal-dialog-centered" role="document" style="width: 1000px;">
							    <div class="modal-content">
								      <div class="modal-header">
								        <h5 class="modal-title" id="exampleModalLongTitle" style="display: inline-block;">THÔNG TIN KHÁC</h5>
								        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
								          		<img style="width: 25px;height: 25px;" src="<c:url value='/resources/images/dongform.png' />" title="Đóng" />
								        </button>
								      </div>		
								      <div class="modal-body" id="PrintContentTTK" style="font-family:'Times New Roman', Arial, Tahoma">
									     
									  </div>
								      <div class="modal-footer">
							                <div class="pull-right" style="padding: 10px">
							                    <button class="btn color-blue" type="button" data-flag="false" onclick="printDiv('PrintContentTTK');"><span class="glyphicon glyphicon-print"></span>&nbsp;&nbsp;In thông tin</button>
							                    <button class="btn color-orangeyellow" data-dismiss="modal" tabindex="13"><span class="glyphicon glyphicon-remove"></span>&nbsp;&nbsp;Đóng</button>
							                </div>
							           </div>
							  		</div>
								</div>	
							 </div>
						</div>	
						<!-- ---------------------------------------------------------------------------->

<script type="text/javascript">
	var totalPages = ${totalPage};
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
						document.forms["formData"].action = '${thongtinchitiet}';  
						document.forms["formData"].submit();  
					}
				}
			});
		function doSubmitSave(form) {
			form.action = '<c:url value="/servlet/central/search" />';
			form.submit();
		}
	</script>
	<script type="text/javascript">
		function getDetailTrans(tranId){
			var url = '${getChitietUrl}/' + tranId;
			$('tr').removeClass('selected');
			$('#id_'+ tranId).addClass('selected');
			$('#transIDHidden').val('');
			$('#transIDHidden').val(tranId);
			$.ajax({
				url : url,
				cache: false,
				type: "GET",
				success : function(data) {
					if(data.endding){
						$("#soBienNhan").val(data.soBienNhan);
						$("#tinhtranghoso").val(data.tinhtranghoso);
						$("#kqhoso").val(data.kqhoso);
						$("#sodanhsachA").val(data.sodanhsachA);
						$("#nguoilapA").val(data.nguoilapA);
						$("#ngaylapA").val(data.ngaylapA);
						$("#sodanhsachB").val(data.sodanhsachB);
						$("#nguoilapB").val(data.nguoilapB);
						$("#ngaylapB").val(data.ngaylapB);
						$("#nguoiduyetDSB").val(data.nguoiduyetDSB);
						$("#ngayduyetDSB").val(data.ngayduyetDSB);
						$("#ykienDSB").val(data.ykienDSB);
						$("#sodsC").val(data.sodsC);
						$("#ngaylapC").val(data.ngaylapC);
						$("#noidungdexuat").val(data.noidungdexuat);
						
						$("#sohochieu").val(data.sohochieu);
						$("#ngaycapHc").val(data.ngaycapHc);
						$("#hanHc").val(data.hanHc);
						$("#nguoiin").val(data.nguoiin);
						$("#ngayin").val(data.ngayin);
						$("#tinhtrang").val(data.tinhtrang);
						$("#nguoitra").val(data.nguoitra);
						$("#ngaytra").val(data.ngaytra);
						$("#noitrahochieu").val(data.noitrahochieu);
						$("#nguoinhan").val(data.nguoinhan);
						$("#soHsluu").val(data.soHsluu);
						if(data.hochieudientu){
							$("#hochieudientu").attr('checked', 'checked');
						}
						else{
							$("#hochieudientu").removeAttr('checked');
						}
						$("#icao1").val(data.icao1);
						$("#icao2").val(data.icao2);
						
						$("#ngaytradt").val(data.ngaytradt);
						$("#nguoitradt").val(data.nguoitradt);
						$("#nguoinhapmay").val(data.nguoinhapmay);
						$("#ngaynhap").val(data.ngaynhap);
						
						if(data.image != ""){
							$("#imagPhoto").attr('src', "data:image/png;base64," + data.image);
						}else{
							$("#imagPhoto").attr('src', '/eppcentral/resources/images/No_Image.jpg');
						}
					}
				}
			});
		}
		
		var check = '';
		function processHS(){
			var url = '${showMessageUrl}';
			var arrJob = '';
			var arrWfj = '';
			var arrChke = document.getElementsByName("selectedJobIds");
			//alert(arrChke.length);
			for(var i = 0; i < arrChke.length; i++){
				if(arrChke[i].checked == true){
					//alert($('#idshow_' + arrChke[i].value).val());
					arrJob += $('#idshow_' + arrChke[i].value).val() + ",";
					arrWfj += arrChke[i].value + ",";
				}
			}
			var mess = checkRong();
			if(mess != ''){
				$.notify(mess, {
					globalPosition: 'bottom left',
					className: 'warn',
				});
				return;
			}
			testOfficeId(arrWfj);
			//alert(check);
			if(check != ''){
				var mes = 'Hồ sơ ' + check + ' đã được cán bộ khác xử lý!';
				$.notify(mes, {
					globalPosition: 'bottom left',
					className: 'warn',
				});
				return;
			}
			$.ajax({
				url : url,
				cache: false,
				type: "POST",
				data: {
					arrJob : arrJob
				},
				success : function(data) {
					if(data == 'Y'){
						btnXuLyHoSo('N');
					}else{
						$("#messageLHS").modal();
					}	
				}
			});
		}
		
		function testOfficeId(arrJob){
			var url = '${testOfficeIdUrl}';
			$.ajax({
				url : url,
				cache: false,
				async:false,
				type: "POST",
				data: {
					arrJob : arrJob
				},
				success : function(data) {
					check = data;
				}
			});
		}
	
	
		function btnXuLyHoSo(value) {
			
			/* $.confirm({
			    title: 'Thông báo',
			    content: "<img style=\"margin-bottom: 5px;\" src=\"/eppcentral/resources/images/question-1.png\">" + ' Bạn có muốn tra cứu tiếp hồ sơ',
			    buttons: {
			        "Có": function () {
						document.forms["formData"].action = '<c:url value="/servlet/investionProcess/invesProcess" />';
						document.forms["formData"].submit();
			        },
			        "Không": function () {
			        	//return false;
			        }		       
			    }
			}) */
			document.forms["formData"].action = '${nextProcessUrl}/' + value;
			document.forms["formData"].submit();
		}
	
		function doApplyFilter() {
			document.forms["formData"].action = '${thongtinchitiet}';
			document.forms["formData"].submit();
		}
	
		function doSubmitNew(form) {
			document.forms["form"].action = "${newUrl}";
			document.forms["form"].submit();
		}
		$( "#dialog-confirm" ).dialog({
			modal: true,
		      autoOpen: false,
			  width : 500,
			  resizable: true,
		      show: {
		        effect: "fade",
		        duration: 200
		      },
		      hide: {
		        //effect: "explode",
		        //duration: 1000
		      },
			   buttons:{
		    "Đồng ý": function() {
		    	document.forms["form"].action = "${newUrl}";
				document.forms["form"].submit();
		    },
			"Hủy": function() {
				$(this).dialog("close");
		    }
		   }
		});
		
		function doSubmitNew1(form) {
			 $("#dialog-confirm").dialog('option', 'title', 'Tình trạng công việc đang chờ xử lý');
			 $("#dialog-confirm").html("Có công việc đang chờ xử lý? Bạn có muốn tiếp tục?");
			 $("#dialog-confirm").dialog( 'open' );			
		}
		
		function checkRong(){
			var kiemTra = false;
			var chkHoSo = document.getElementsByName("selectedJobIds");
        	for(var i = 0; i < chkHoSo.length; i++){
        		if(chkHoSo[i].checked){
        			kiemTra = true;
        			break;
        		}
        	}
        	if(kiemTra){
        		return '';
        	}else{
        		return 'Bạn chưa chọn hồ sơ nào';
        	}
		}
		
		//var stage = 0;
		$('#idCheckAll').change(function(){
			var total = document.getElementsByName("selectedJobIds");
			var root = document.getElementById("idCheckAll");
			for(var i = 0; i < total.length; i++){
				if(total[i].disabled == true){
					continue;
				}
				if(root.checked == true){
					total[i].checked = true;
				}else{
					total[i].checked = false;
				}
			}
		});
		
		$("#createBy").datepicker({
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
		//$('#createBy').datepicker().datepicker('setDate', new Date());
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
		
		function showtientrinhHS(){
			$('#PrintContent').html("");
			var txtint = $('#transIDHidden').val();
			var url = '${getInChitietUrl}/' + txtint;
			$.ajax({
				url : url,
				cache: false,
				contentType : 'application/json',
				type: "GET",
				success : function(data) {
					$('#PrintContent').html(data);							
				}
			});
		}
		
		function showLS(){
			$('#PrintContentLS').html("");
			var txtint = $('#transIDHidden').val();
			var url = '${getInLSUrl}/' + txtint;
			$.ajax({
				url : url,
				cache: false,
				contentType : 'application/json',
				type: "GET",
				success : function(data) {
					$('#PrintContentLS').html(data);							
				}
			});
		}	
		
		function showTK(){
			$('#PrintContentTK').html("");
			var txtint = $('#transIDHidden').val();
			var url = '${getInTKUrl}/' + txtint;
			$.ajax({
				url : url,
				cache: false,
				contentType : 'application/json',
				type: "GET",
				success : function(data) {
					$('#PrintContentTK').html(data);							
				}
			});
		}	
		
		function showTTK(){
			$('#PrintContentTTK').html("");
			var txtint = $('#transIDHidden').val();
			var url = '${getInTTKUrl}/' + txtint;
			$.ajax({
				url : url,
				cache: false,
				contentType : 'application/json',
				type: "GET",
				success : function(data) {
					$('#PrintContentTTK').html(data);							
				}
			});
		}
		
		function printDiv(id) 
		{

		  var divToPrint=document.getElementById(id);

		  var newWin=window.open('','Print-Window');

		  newWin.document.open();

		  newWin.document.write('<html><body onload="window.print()">'+ divToPrint.innerHTML + '</body></html>');

		  newWin.document.close();

		  setTimeout(function(){newWin.close();},10);

		}
		//$('#endDate').datepicker().datepicker('setDate', new Date());
	</script>
	</div>
	</div>
	</div>
</form:form>


