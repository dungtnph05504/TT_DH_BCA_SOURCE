<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display" %>
<link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/select2.min.css"/>"></link> 
<script type="text/javascript" src="<c:url value="/resources/js/select2.min.js"/>"></script>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
<c:url var="prodUrl" value="/servlet/investigation/startinvestigation" />
<c:url var="newUrl" value="/servlet/investigation/newInvestigation" />
<c:url var="pendingJobUrl" value="/servlet/investigation/pendingJobList" />
<c:url var="investigationJobUrl" value="/servlet/investigation/investigation" />
<c:url var="invesProcessUrl" value="/servlet/investionProcess/showInvestigation" />
<c:url var="nextProcessUrl" value="/servlet/investionProcess/invesProcess" />
<c:url var="showMessageUrl" value="/servlet/investionProcess/showMessage" />
<c:url var="testOfficeIdUrl" value="/servlet/investionProcess/testOfficeId" />
<c:url var="thongtinchitiet" value="/servlet/central/danhsachlsxnc/" />
<c:url var="getTientringHsUrl" value="/servlet/central/getTientrinhHs" />
<c:url var="txnDetailChidenUrl" value = "/servlet/central/getChitietChildenXNC"/>
<c:url var="txnDetailInitUrl" value = "/servlet/central/getChitietXNC"/>
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
#ajax-load-qa {
	background: rgba(255, 255, 255, .7)
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
.modal-open .modal {
    overflow: hidden;
    overflow-y: auto !important;
}

.form-width {
    width: 100%;
}
</style>
<form:form modelAttribute="formData" name="formData" autocomplete="off"> 
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
      <div class="new-heading-2">TRA CỨU LỊCH SỬ XUẤT NHẬP CẢNH</div>
      <div style="clear: both"></div>
			<div style="min-height: 10px"></div>
						<div>
							<div>
							<div>
							<div class="form-inline">
				<fieldset>
					<legend>Điều kiện tìm kiếm</legend>
				<div class="col-md-12 col-sm-12">
				   <div class="col-md-4 col-sm-4">
                    <label class="col-md-5 col-sm-5 cls-mg-bot">Số hộ chiếu</label>
                    <div class="col-md-7 col-sm-7 cls-mg-bot">
                        <form:input path="passportNo" id="passportNo" class="form-width" type="text" style="height: 25px;" />
                    </div>
                    
                    <label class="col-md-5 col-sm-5 sls-mg-bot" style="margin-top: 8px;">Họ và tên</label>
                    <div class="col-md-7 col-sm-7 cls-mg-bot">
                        <form:input path="fullName" id="fullName" style="height: 25px;" class="form-width" type="text" />
                    </div>
                      <label class="col-md-5 col-sm-5 cls-mg-bot">Cửa khẩu xuất/nhập</label>
                    <div class="col-md-7 col-sm-7 cls-mg-bot">
                       <form:select class="js-example-basic-single"  path="ckXnc" id="ckXnc"
											style="font-family: Arial; font-size: 12px;width: 100%;height: 25px;margin-bottom: 5px;">
											<c:forEach var="entry" items="${listBorder}">
												<form:option value="${entry.key}" style="margin-right: 5px;" label="${entry.value}" />
											</c:forEach>
						</form:select>
                    </div>
                    <label class="col-md-5 col-sm-5 cls-mg-bot">Mã quốc gia</label>
                    <div class="col-md-7 col-sm-7 cls-mg-bot">
                        <form:select class="js-example-basic-single" path="nationCode" id="nationCode" style="height: 25px;font-family: Arial; font-size: 12px;width: 100%;">
                            <c:forEach var="entry" items="${listCountry}">
								<form:option value="${entry.key}" style="margin-right: 5px;" label="${entry.value}" />
							 </c:forEach>

                        </form:select>
                    </div>
                      <label class="col-md-5 col-sm-5 cls-mg-bot">Từ ngày</label>
                    <div class="col-md-7 col-sm-7 cls-mg-bot">
                        <form:input path="fromDate" id="fromDate" class="form-width"  type="text" style="height: 25px;"/>
                    </div>
                  </div>
                <div class="col-md-4 col-sm-4">
                     <label class="col-md-5 col-sm-5 cls-mg-bot">Số thị thực</label>
                    <div class="col-md-7 col-sm-7 cls-mg-bot">
                        <form:input path="visaNo" id="visaNo" style="height: 25px;" class="form-width" type="text" />
                    </div>
                     <label class="col-md-5 col-sm-5 cls-mg-bot">Ngày sinh</label>
                    <div class="col-md-7 col-sm-7 cls-mg-bot">
                        <form:input type="text" path="dateOfBirth" id="dateOfBirth" style="height: 25px;margin-bottom: 5px;" class="form-width" />
                    </div>
                       <label class="col-md-5 col-sm-5 cls-mg-bot">Loại nhập xuất</label>
                    <div class="col-md-7 col-sm-7 cls-mg-bot">
                        <form:select path="typeXnc" id="typeXnc" style="font-family: Arial; font-size: 12px;width: 100%;height: 25px;">
                            <form:option value="" selected="selected">Tất cả</form:option>
                            <form:option value="N">Nhập</form:option>
                            <form:option value="X">Xuất</form:option>
                        </form:select>
                    </div>
                        <label class="col-md-5 col-sm-5 cls-mg-bot">Mục đích</label>
                    <div class="col-md-7 col-sm-7 cls-mg-bot">
                        <form:input path="purpose" id="purpose" style="height: 25px;" class="form-width" type="text" />
                    </div>
                      <label class="col-md-5 col-sm-5 cls-mg-bot">Đến ngày</label>
                    <div class="col-md-7 col-sm-7 cls-mg-bot">
                        <form:input path="endDate" id="endDate" class="form-width"  type="text" style="height: 25px;"/>
                    </div>
                  
                </div>
                <div class="col-md-4 col-sm-4">
                     <label class="col-md-5 col-sm-5 cls-mg-bot">Số phiếu XNC</label>
                    <div class="col-md-7 col-sm-7 cls-mg-bot">
                        <form:input path="soPhieuXnc" id="soPhieuXnc" style="height: 25px;" class="form-width" type="text" />
                    </div>
                    <label class="col-md-5 col-sm-5 cls-mg-bot">Giới tính</label>
                    <div class="col-md-7 col-sm-7 cls-mg-bot">
                        <form:select path="gender" id="gender" style="height: 25px;font-family: Arial; font-size: 12px;width: 100%;">
                            <form:option value="" selected="selected">Tất cả</form:option>
                            <form:option value="M">Nam</form:option>
                            <form:option value="F">Nữ</form:option>
                            <form:option value="O">Khác</form:option>
                        </form:select>
                    </div>
                     <input type="hidden" name="temp" value="T" /> 
                     <label class="col-md-5 col-sm-5 cls-mg-bot">Chuyến bay</label>
                    <div class="col-md-7 col-sm-7 cls-mg-bot">
                        <form:input path="chuyenBay" id="chuyenBay" style="height: 25px;" class="form-width" type="text" />
                    </div>
                    <div class="col-sm-12" style="margin-top: 20px;">
                        <div class="pull-right">
                            <button class="btn color-blue" type="button" onclick="doApplyFilter()"><span class="glyphicon glyphicon-search"></span>Tìm kiếm</button>
                        </div>
                    </div>
                 </div>
                             
			</div>
					 
                </fieldset>
            </div>
							</div>
						</div>
			</div>

	  		<br />
			<br />
					<fieldset>
							<legend>Danh sách hồ sơ</legend>
							<div style="height: 400px;">
						      	<table id="dtBasicExample" class="table table-bordered table-sm table-hover" cellspacing="0" width="100%">
								  <thead>
								    <tr>
								      <th class="th-sm">Họ tên
								
								      </th>
								      <th class="th-sm">Ngày sinh
								
								      </th>
								      <th class="th-sm">Giới tính
								
								      </th>
								      <th class="th-sm">Mã Quốc tịch
								
								      </th>
								      <th class="th-sm">Số hộ chiếu
								
								      </th>
								      <th class="th-sm">Số thị thực
								
								      </th>
								      <th class="th-sm">Cửa khẩu xuất/nhập
								
								      </th>
								      <th class="th-sm">Xuất nhập
								
								      </th>
								      <th class="th-sm">Ngày xuất/nhập
								
								      </th>
								      
								      <th class="th-sm">Số XNC
								
								      </th>
								      <th class="th-sm">Chức năng
								
								      </th>
								    </tr>
								  </thead>
									  <tbody>
									  <c:if test="${not empty dsXuLyA}">
									  	 <c:forEach items="${dsXuLyA}" var="item">
										    <tr>								 
										      <td>${item.fullName}</td>
										      <td align="center">${item.dateOfBirth}</td>
										      <td>${item.gender}</td>
										      <td>${item.nationCode}</td>
										      <td>${item.passportNo}</td>
										      <td align="center">${item.visaNo}</td>
										      <td>${item.ckXnc}</td>
										      <td>${item.typeXnc}</td>
										      <td>${item.issueDate}</td>
										      <td>${item.soPhieuXnc}</td>
										      <td class="align-central" ><a href="#" onclick="chiTietHS('${item.soPhieuXnc}')" data-toggle="modal" data-target="#idChiTiet"><i class="glyphicon glyphicon-eye-open"></i>Xem</a></td>		
										    </tr>
									    </c:forEach>
									  </c:if>
									  </tbody>
									  <c:if test="${empty dsXuLyA}">
								  
										   <tbody class="e-not-found ng-scope"><tr>
										  <td colspan="12" style="height: 362px">
										  <div class="e-not-found-center"><div class="e-not-found-title">Không tìm thấy kết quả</div><div class="e-not-found-title-child">Hãy thử tìm kiếm hoặc sử dụng bộ lọc khác</div></div>
										  </td></tr></tbody>
										  
										
										</c:if>
								</table>
								
								
						      </div>
						      <table class="table e-grid-table e-border">
                                <tfoot>
                                    <tr>
                                        <th>
                                        
                                          <c:if test="${not empty dsXuLyA}">
										
										<div class="e-page-rigth">
											<ul style="float: right;" class="pagination" id="pagination"></ul>
										</div>
										<input type="hidden" name="pageNo" id="pageNo" /> 
									</c:if>
                                        
                                        <div class="e-page-left" style="font-weight: normal;">
											Hiển thị ${startIndex} đến ${endIndex} của ${totalRecord} kết quả
										</div>
                                        
                                          
                                            
                                        </th>
                                    </tr>
                                </tfoot>
                            </table>
						  </fieldset>
						</div>
					</div>
						<!-- Model phí bổ sung -->
						<div class="modal fade" id="chiTietTT" tabindex="-1" role="dialog" aria-labelledby="exampleModalCenterTitle" aria-hidden="true" >
						  <div class="modal-dialog modal-dialog-centered" role="document" style="width: 1200px">
						    <div class="modal-content">
						      <div class="modal-header">
						        <h5 class="modal-title" id="exampleModalLongTitle" style="display: inline-block;">CHI TIẾT LỊCH SỬ XNC</h5>
						        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
						          		<img style="width: 25px;height: 25px;" src="<c:url value='/resources/images/dongform.png' />" title="Đóng" />
						        </button>
						      </div>		
						      <div class="modal-body" id="danhsachTT" style="height: 800px;">
				 <div role="tabpanel">
                    <!-- Nav tabs -->
                     <ul class="nav nav-tabs" role="tablist">
                       <li role="presentation" class="active" id="a"><a href="#detailXnc" aria-controls="uploadTab" role="tab" data-toggle="tab">THÔNG TIN CHI TIẾT XNC</a>

                      </li>
                        <li role="presentation" id="b"><a href="#attachChild" aria-controls="browseTab" role="tab" data-toggle="tab">TRẺ EM ĐI KÈM</a>

                       </li>
                    </ul> 
                    <!-- Tab panes -->
                    <div class="tab-content" style="overflow-y: auto;">
                        <div role="tabpanel" class="tab-pane active" id="detailXnc">
                            <div class="container">     
    <div class="row">
      <div class="col-sm-3" id="images">
          <img id="images1" src="#" style="width: 170px; height: 200px;"/>
      </div>
      <div class="col-sm-3">
           <h5><b>Người</b>: <label id="nguoi1"></label></h5>
           <h5><b>Quốc Gia</b>: <label id="quocgia1"></label></h5>
           <h5><b>Tên đầy đủ</b>: <label id="fullName1"></label></h5>
           <h5><b>Giới tính</b>: <label id="sex1"></label></h5>
           <h5><b>Nơi sinh</b>: <label id="noisinh1"></label></h5>
           <h5><b>Ngày sinh</b>: <label id="dob1"></label></h5>
           <h5><b>CMND</b>: <label></label id="cmnd1"></h5>
           <h5><b>Loại xuất/nhập</b>: <label id ="loaiXN1"></label></h5>
           <h5><b>Trạng thái</b>: <label  id="status1"></label></h5>
           <h5><b>Ngày tạo</b>: <label id="dateCreate1"></label></h5>
           <h5><b>Ngày chỉnh sửa</b>:<label id="dateEdit1"></label></h5>   
      </div>
       <div class="col-sm-3">
          <h5><b>Số hộ chiếu</b>: <label id="passportNo1"></label></h5>
           <h5><b>Loại hộ chiếu</b>: <label id="passportType1"></label></h5>
           <h5><b>Ngày hết hạn</b>: <label id="dateExpire1"></label></h5>
           <h5><b>Nơi cấp hộ chiếu</b>: <label id="noiCap1"></label></h5>
           <h5><b>Số Icao của hộ chiếu</b>: <label id="icaoNo1"></label></h5>     
           </div>
      <div class="col-sm-3">
          <h5><b>Số chuyến bay</b>: <label id="flightNo1"></label></h5>
           <h5><b>Số thị thực</b>: <label id="visaNo1"></label></h5>
           <h5><b>Ngày cấp thị thực</b>: <label id="ngayCapTT1"></label></h5>
           <h5><b>Nơi cấp thị thực</b>: <label id="noiCapTT1"></label></h5>
           <h5><b>Kí hiệu thị thực</b>: <label id="khTT1"></label></h5>
           <h5><b>Loại thị thực</b>: <label id="loaiTT1"></label></h5>
           <h5><b>Giá trị thị thực</b>: <label id="giatriTT1"></label></h5>
           <h5><b>Tạm trú đến</b>: <label id ="hanTamTru1"></label></h5>
           <h5><b>Mục đích</b>: <label  id="purpose1"></label></h5>
           <h5><b>Ghi chú của kiểm soát</b>: <label id="noteKS1"></label></h5>
           <h5><b>Ghi chú của giám sát</b>: <label id="noteGS1"></label"></h5>
           <h5><b>Cửa khẩu</b>: <label id="CK1"></label></h5>   
           <h5><b>Máy trạm</b>: <label id="maytram1"></label></h5>  
           </div>
    </div>
  </div>
                        </div>
                        <div role="tabpanel" class="tab-pane" id="attachChild">
                                  <p id="p" style="color: red;"></p>
                           		  <table id="tableChild" class="table table-bordered table-sm table-hover" cellspacing="0" width="100%">
								   <thead>
								    <tr>
								      <th class="th-sm">ID
								
								      </th>
								      <th class="th-sm">Tên Trẻ Em
								
								      </th>
								     <!--  <th class="th-sm">Ngày Sinh 
								
								      </th>--> 
								      <th class="th-sm">Giới Tính
								
								      </th>
								      <th class="th-sm">Quan Hệ Gia Đình
								
								      </th>
								      <th class="th-sm">Mã Nơi Sinh
								
								      </th>
								      <th class="th-sm">Địa Thường Trú
								
								      </th>
		
								    </tr>
								  </thead>
									  <tbody id="tblBody">
									<!--<c:if test="${not empty dsXuLyA}">
									  	 <c:forEach items="${dsXuLyA}" var="item">
										    <tr>								 
										      <td>${item.fullName}</td>
										      <td align="center">${item.dateOfBirth}</td>
										      <td>${item.gender}</td>
										      <td>${item.nationCode}</td>
										      <td>${item.passportNo}</td>
										      <td align="center">${item.visaNo}</td>
										      <td>${item.ckXnc}</td>
										    </tr>
									    </c:forEach>
									  </c:if>  --> 
									  
									 </tbody>
								<!-- <c:if test="${empty dsXuLyA}">
								  
										   <tbody class="e-not-found ng-scope"><tr>
										  <td colspan="12" style="height: 362px">
										  <div class="e-not-found-center"><div class="e-not-found-title">Không tìm thấy kết quả</div><div class="e-not-found-title-child">Hãy thử tìm kiếm hoặc sử dụng bộ lọc khác</div></div>
										  </td></tr></tbody>
										  
										
										</c:if> -->	  
								</table>
                        </div>
                    </div>
                  </div>
						      </div>
						       <div class="modal-footer" style="padding: 5px;">
				       		      <button type="button" class="btn btn-warning" data-dismiss="modal" aria-label="Close" style="float: right;">
				       			     <span class="glyphicon glyphicon-remove"></span > Đóng
				       		      </button>	       		
				               </div> 
						    </div>
						  </div>
						</div>	  
						<!-- -------------------------------------------------------------------------- -->
              <!--  <div class="modal fade" id="idChiTiet" tabindex="-1" role="dialog" aria-labelledby="exampleModalCenterTitle" aria-hidden="true" >
				  <div class="modal-dialog modal-dialog-centered" role="document" style="width: 1200px;">
				    <div class="modal-content">
				      <div class="modal-header">
				        <h5 class="modal-title" id="exampleModalLongTitle" style="display: inline-block;">CHI TIẾT LỊCH SỬ XNC</h5>
				        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
				          		<img style="width: 25px;height: 25px;" src="<c:url value='/resources/images/dongform.png' />" title="Đóng" />
				        </button>
				      </div>
				      <div class="modal-body" id="divChiTiet" style="height: 800px;">
				      		
				      </div>							      
				      <div class="modal-footer" style="padding: 5px;">
				       		<button type="button" class="btn btn-warning" data-dismiss="modal" aria-label="Close" style="float: right;">
				       			<span class="glyphicon glyphicon-remove"></span > Đóng
				       		</button>	       		
				      </div>
				    </div>
				  </div>
				</div>	--> 

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
	var immiId="";
	//var tmp = ${log};
	var object = {};
	$(document).ready(function() {
	//	alert(tmp);
	//	if(tmp == 0){
	//		$.notify('Cần tối thiểu 2 tiêu chí tìm kiếm.', {
		//		globalPosition: 'bottom left',
		//		className: 'warn',
		//	});
	//	}
	    $('.js-example-basic-multiple').select2();
	    $('.js-example-basic-single').select2();
	});
	function chiTietHS(id){
		$('#ajax-load-qa').show();
		//document.getElementById('txnId').value = txnId;
    
	     immiId = id;
	    // $('#ajax-load-qa').show();
	 		$.ajax({
				type : "GET",
				url : "${txnDetailInitUrl}/" + id,				
				success : function(data) {
					$('#ajax-load-qa').hide();
					if(data.image != ""){
						$("#images1").attr('src', "data:image/png;base64," + data.data);
					}else{
						$("#images1").attr('src', '/eppcentral/resources/images/No_Image.jpg');
					}
					//$("#images1").attr("src",'data:image/jpg;base64,'+ data.data);
					//$('#images').html('<img src="data:image/jpg;base64,'+ data.data + '" />');
					$('#loaiXN1').html(data.im.immiType);
					$('#fullName1').html(data.im.fullName);
					$('#nguoi1').html(data.im.personType);
					$('#quocgia1').html(data.im.countryCode);
					$('#sex1').html(data.im.gender);
					$('#noisinh1').html(data.im.placeOfBirthCode);
					$('#dob1').html(data.dateOfBirth);
					$('#cmnd1').html(data.im.identityCardNo);
					$('#status1').html(data.adminResult);
					$('#dateCreate1').html(data.createTime);
					$('#dateEdit1').html(data.lastModifiedTime);
					$('#passportNo1').html(data.im.passportNo);
					$('#passportType1').html(data.im.passportType);
					$('#dateExpire1').html(data.passportExpiredDate);
					$('#noiCap1').html(data.im.passportIssuePlaceCode);
					$('#icaoNo1').html(data.im.icaoLine);
					$('#flightNo1').html(data.im.flightNo);
					$('#visaNo1').html(data.im.visaNo);
					$('#ngayCapTT1').html(data.visaIssueDate);
					$('#noiCapTT1').html(data.im.visaIssuePlaceCode);
					$('#khTT1').html(data.im.visaSymbolCode);
					$('#loaiTT1').html(data.im.visaTypeCode);
					$('#giatriTT1').html(data.im.visaValue);
					$('#hanTamTru1').html(data.residenceUntilDate);
					$('#purpose1').html(data.im.purposeName);
					$('#noteKS1').html(data.im.gateNote);
					$('#noteGS1').html(data.im.supervisorNote);
					$('#CK1').html(data.im.borderGateCode);
					$('#maytram1').html(data.im.workstationCode);
					
				},
				error: $('#ajax-load-qa').hide()
			});
	 		
	 		//alert(immiId);
			$.ajax({
				type : "GET",
				url : '${txnDetailChidenUrl}/' + immiId,
		//		data : $("#nicEnqDetailsForm").serializeArray(),
				success : function(data) {
					if(data.length > 0){
					//	var tb = $('#tableChild').DataTable();	
					//	tb.clear();
					$('#tableChild').show();
					$('#tblBody').empty();
					$('#p').hide();
			        	for(var i = 0; data.length; i++){
			        		//tb.row.add([
			        		   //          data[i].id,
			        		  //           data[i].fullName,
			        		  //           data[i].gender,
			        		  //           data[i].familyrelationshipCode,
			        		  //           data[i].placeOfBirthCode,
			        		  //           data[i].address
			        		// ]).draw(false);
			        		$('#tblBody').append('<tr><td>'
			        		                      + data[i].id
			        		                      +'</td>  <td>'
			        		                      + data[i].fullName
			        		                      +'</td>  <td>'
			        		                      + data[i].gender
			        		                      + '</td>  <td>'
			        		                      + data[i].familyrelationshipCode
			        		                      + '</td>  <td>'
			        		                      + data[i].placeOfBirthCode 
			        		                      + '</td>  <td>'
			        		                      + data[i].address 
			        		                      + '</td></tr>'
			        		                       
			        		);
			        	}
						
					}else{	
						//$("#tbodyid").empty();
						$('#tblBody').empty();
						$('#tableChild').hide();
						$('#p').show();
						$('#p').html("không có trẻ em đi kèm");
					}
				}
			});
		$('#chiTietTT').modal();
		
	}
		var pattern = /^([0-2][0-9]|(3)[0-1])(\/)(((0)[0-9])|((1)[0-2]))(\/)\d{4}$/;
		function doApplyFilter() {
			var dem = 0;
		if($('#ckXnc').val() ==null || $('#ckXnc').val() == ''){
			dem = dem + 1;
		}
		if($('#fullName').val() ==null || $('#fullName').val() == ''){
			dem = dem + 1;
		}
		if($('#gender').val() ==null || $('#gender').val() == ''){
			dem = dem + 1;
		}
		if($('#purpose').val() ==null || $('#purpose').val() == ''){
			dem = dem + 1;
		}
		if($('#chuyenBay').val() ==null || $('#chuyenBay').val() == ''){
			dem = dem + 1;
		}
		if($('#typeXnc').val() ==null || $('#typeXnc').val() == ''){
			dem = dem + 1;
		}
		if($('#nationCode').val() ==null || $('#nationCode').val() == ''){
			dem = dem + 1;
		}
		if($('#dateOfBirth').val() ==null || $('#dateOfBirth').val() == ''){
			dem = dem + 1;
		}
		if($('#fromDate').val() ==null || $('#fromDate').val() == ''){
			dem = dem + 1;
		}
		 if(($('#passportNo').val() ==null || $('#passportNo').val() == '') && ($('#fullName').val()==null || $('#fullName').val()=='') && ($('#fromDate').val() == null || $('#fromDate').val() == '') && ($('#visaNo').val() ==null || $('#visaNo').val() == '') && ($('#dateOfBirth').val() ==null || $('#dateOfBirth').val() == '')
		    		&& ($('#gender').val() ==null || $('#gender').val() == '') && ($('#chuyenBay').val() ==null || $('#chuyenBay').val() == '') && ($('#soPhieuXnc').val() ==null || $('#soPhieuXnc').val() == '')
		    		&& ($('#purpose').val() ==null || $('#purpose').val() == '') && ($('#endDate').val() ==null || $('#endDate').val() == '')
		    		&& ($('#typeXnc').val() ==null || $('#typeXnc').val() == '') && ($('#nationCode').val() ==null || $('#nationCode').val() == '') 
		    		&& ($('#ckXnc').val() ==null || $('#ckXnc').val() == '')){
		    	$.notify('Cần chọn tiêu chí tìm kiếm.', {
					globalPosition: 'bottom left',
					className: 'warn',
				});
		    	 return;
		    }
		if($('#endDate').val() ==null || $('#endDate').val() == ''){
			dem = dem + 1;
		}
		if(($('#passportNo').val() ==null || $('#passportNo').val() == '') && ($('#soPhieuXnc').val() ==null || $('#soPhieuXnc').val() == '') 
				&& ($('#visaNo').val() ==null || $('#visaNo').val() == '') && dem > 8){
			$.notify('Cần chọn thêm tiêu chí tìm kiếm.', {
				globalPosition: 'bottom left',
				className: 'warn',
			});
	    	 return;
		}
	   
	    
		// $.alert({
		//    title: 'Thông báo',
		//    content: "<img style=\"margin-bottom: 5px;\" src=\"/ric-web/resources/images/information_1a.png\">" + ' Xin nhập đầy thông tin muốn thay đổi',
		//    buttons: {   
		//        "Đóng": function () {}
		//    }
		//   });
		
	 //	&& ($('#typeXnc').val() ==null || $('#typeXnc').val() == '') && ($('#purpose').val() ==null || $('#purpose').val() == '') && ($('#endDate').val() ==null || $('#endDate').val() == '') && ($('#endDate').val() ==null || $('#endDate').val() == '') 
	 //   		 
	//    		 	($('#nationCode').val() ==null || $('#nationCode').val() == '') && ($('#ckXnc').val()==null || $('#ckXnc').val()=='')
			document.forms["formData"].action = '${thongtinchitiet}';
			document.forms["formData"].submit();
		}
	
		
		$('#dateOfBirth').mask("00/00/0000", { placeholder: "__/__/____" });
		$("#dateOfBirth").datepicker({
			//showOn : "button",
			//buttonImage : "",
			//buttonImageOnly : true,
			changeMonth : true,
			changeYear : true,
			showSecond : true,
			controlType : 'select',
			dateFormat : 'dd/mm/yy',
			maxDate : 0,
			yearRange : "-100:+0", 
		}).keyup(function(e) {
			if (e.keyCode == 8 || e.keyCode == 46) {
				$.datepicker._clearDate(this);
			}
		});
		$('#endDate').mask("00/00/0000", { placeholder: "__/__/____" });
		$("#endDate").datepicker({
			changeMonth : true,
			changeYear : true,
			showSecond : true,
			controlType : 'select',
			dateFormat : 'dd/mm/yy',
			 maxDate : 0,
			yearRange : "-100:+0"
		}).keyup(function(e) {
			
		});
		$('#fromDate').mask("00/00/0000", { placeholder: "__/__/____" });
		$("#fromDate").datepicker({
			changeMonth : true,
			changeYear : true,
			showSecond : true,
			controlType : 'select',
			dateFormat : 'dd/mm/yy',
			maxDate : 0,
			yearRange : "-100:+0"
		}).keyup(function(e) {
			
		});
		function showtientrinhHS(value){
			$('#danhsachTT').html("");
			var url = '${getTientringHsUrl}';
			$.ajax({
				url : url,
				cache: false,
				data : JSON.stringify(value),
				contentType : 'application/json',
				type: "POST",
				success : function(data) {
					$('#danhsachTT').html(data);							
				}
			});
		}
		
	
      
		//$('#endDate').datepicker().datepicker('setDate', new Date());
	</script>
	</div>
	</div>
	</div>
</form:form>


