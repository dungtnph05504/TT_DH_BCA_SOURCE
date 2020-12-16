<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display" %>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
<script src="https://cdn.ckeditor.com/4.12.1/standard/ckeditor.js"></script>
<c:url var="searchImmiUrl" value="/servlet/central/searchImmi" />

<%-- <form:form commandName="jobList" id="form" name="form" > --%>
<style>

.cls-mg-bot {
	margin-top: 10px;
}
.mr5_border1_p10 {
	margin-right: 5px;
    border: 1px solid #ddd;
}

.form-control {
	height: 20px !important;
}
.font-12 {
	font-weight: normal;
   
    font-size: 12px;
}
.titlechuhoso {
    width: 100%;
    float: left;
    text-align: center;
    margin-bottom: 5px;
}
.titledshoso {
    width: 45%;
    float: left;
    text-align: center;
}
.w45Pc_fl_mb5 {
    width: 45%;
    float: left;
    margin-bottom: 5px;
}
.listdshoso {
    height: 227px;
    overflow-y: auto;
    border: 1px solid #ddd;
}
.btnchucnang {
    width: 10%;
    float: left;
    padding-top: 30px;
    text-align: center;
}
.size-btn{
	width: 40%;
    margin-top: 10px;
}
.btn27 {
	width: 100%;
}
.kinf-1 {
	display: none;
}
</style>
<div class="content-item-title">
            <div class="oplog-title__txt">Tra cứu tổng hợp xuất nhập cảnh</div>
        </div>
<div class="content-item-main">
<form:form modelAttribute="formData" name="formData" > 
    <div class="content-item-information" style="margin-top: 50px;">
    	<div style="margin-bottom: 80px;">
    		<div class="col-sm-10">
    			<fieldset>
    				<legend>Điều kiện</legend>
    				<div>
    					<div class="col-sm-3">
    						<label class="control-label">Họ tên</label>
    						<form:input type="text" class="form-control" id="txtFullName" path="name" />
    					</div>
    					<div class="col-sm-1">
    						<label class="control-label">Đảo tên</label>
    						<input type="checkbox" class="form-control" />
    					</div>
    					<div class="col-sm-2">
    						<form:select id="stylDob" path="typeList" style="width: 100%;margin-top: 20px;">
    							<form:option value="D">Đủ ngày tháng năm</form:option>
    							<form:option value="Y">Năm</form:option>
    						</form:select>
    					</div>
    					<div class="col-sm-3 kinf">
    						<label class="control-label">Ngày sinh</label>
    						<form:input type="text" class="form-control" id="txtDob" path="dob"/>
    					</div>
    					<div class="col-sm-1 kinf-1">
    						<label class="control-label">Từ năm</label>
    						<form:input type="text" class="form-control" id="txtStartYear" path="startYear"/>
    					</div>
    					<div class="col-sm-1 kinf-1">
    						<label class="control-label">Đến năm</label>
    						<form:input type="text" class="form-control" id="txtEndYear" path="endYear" />
    					</div>
    					<div class="col-sm-1 kinf-1">
    						
    					</div>
    					<div class="col-sm-3">
    						<label class="col-sm-12 control-label">Giới tính</label>
    						<div class="col-sm-12">	    						
	    						<form:select path="gender">
	    							<form:option value="">Chọn giới tính</form:option>
	    							<form:option value="M">Nam</form:option>
	    							<form:option value="F">Nữ</form:option>
	    							<form:option value="N">Không xác định</form:option>
	    						</form:select>
    						</div>
    					</div>
    					<div class="col-sm-12" style="height: 10px;"></div>
    					<div class="col-sm-3">
    						<label class="control-label">Số hộ chiếu, Thông hành</label><br /><br />
    						<form:input type="text" class="form-control"  path="passportNo" />
    					</div>
    					<div class="col-sm-3">
    						<label class="control-label">Số thị thực, Thẻ du lịch<br />Thẻ tạm trú, thường trú</label>
    						<form:input type="text" class="form-control"  path="visaNo" />
    					</div>
    					<div class="col-sm-3">
    						<label class="control-label">Số CMND/CCCD</label><br /><br />
    						<form:input type="text" class="form-control"  path="nin" />
    					</div>
    					<div class="col-sm-3">
    						<label class="control-label">Quốc tịch</label><br /><br />
    						<form:select class="form-group" id="nationality" path="pob">
    							<form:option value="">Chọn quốc tịch</form:option>
		    					<c:forEach items="${danToc}" var="iem">
			    					<form:option value="${iem.key}">${iem.value}</form:option>
		    					</c:forEach>
		    				</form:select>
    					</div>
    					<div class="col-sm-12" style="height: 10px;"></div>
    					<div class="col-sm-2">
    						<label class="control-label" style="float: right;">Chọn
	    						<input type="checkbox" id="allChk1"  checked="checked"/>
    						</label>
    						<br>
    						<label class="control-label" style="float: right;">Nhập cảnh
	    						<input type="checkbox" name="selectedJob1"  checked="checked"/>
    						</label>
    						<br>
    						<label class="control-label" style="float: right;">Xuất cảnh
    							<input type="checkbox" name="selectedJob1"  checked="checked"/>
    						</label>
    						<br>
    						<label class="control-label" style="float: right;">Phiếu tạm trú
	    						<input type="checkbox" name="selectedJob1"  checked="checked"/>
    						</label>
    						<br>
    						<label class="control-label" style="float: right;">CQ có NNN
	    						<input type="checkbox" name="selectedJob1"  checked="checked"/>
    						</label>
    					</div>
    					<div class="col-sm-2">
    						<label class="control-label" style="float: right;">Chọn
	    						<input type="checkbox" id="allChk2" checked="checked"/>
    						</label>
    						<br>
    						<label class="control-label" style="float: right;">Hộ chiếu
	    						<input type="checkbox" name="selectedJob2" checked="checked"/>
    						</label>
    						<br>
    						<label class="control-label" style="float: right;">Thông hành
	    						<input type="checkbox" name="selectedJob2" checked="checked"/>
    						</label>
    						<br>
    						<label class="control-label" style="float: right;">GP XNC
	    						<input type="checkbox" name="selectedJob2" checked="checked"/>
    						</label>
    						<br>
    						<label class="control-label" style="float: right;">ABTC VN
	    						<input type="checkbox" name="selectedJob2"  checked="checked"/>
    						</label>
    					</div>
    					<div class="col-sm-2">
    						<label class="control-label" style="float: right;">Chọn
	    						<input type="checkbox" id="allChk3"  checked="checked"/>
    						</label>
    						<br>
    						<label class="control-label" style="float: right;">Vi phạm
	    						<input type="checkbox" name="selectedJob3" checked="checked"/>
    						</label>
    						<br>
    						<label class="control-label" style="float: right;">Trục xuất
	    						<input type="checkbox" name="selectedJob3"  checked="checked"/>
    						</label>
    						<br>
    						<label class="control-label" style="float: right;">XM NS
	    						<input type="checkbox" name="selectedJob3" checked="checked"/>
    						</label>
    						<br>
    						<label class="control-label" style="float: right;">XN NS
	    						<input type="checkbox" name="selectedJob3" checked="checked"/>
    						</label>
    					</div>
    					<div class="col-sm-2">
    						<label class="control-label" style="float: right;">Chọn
	    						<input type="checkbox" id="allChk4"  checked="checked"/>
    						</label>
    						<br>
    						<label class="control-label" style="float: right;">Duyệt nhập
	    						<input type="checkbox" name="selectedJob4" checked="checked"/>
    						</label>
    						<br>
    						<label class="control-label" style="float: right;">ABTC NN
	    						<input type="checkbox" name="selectedJob4"  checked="checked"/>
    						</label>
    						<br>
    						<label class="control-label" style="float: right;">Thẻ du lịch
	    						<input type="checkbox" name="selectedJob4"  checked="checked"/>
    						</label>
    						<br>
    						<label class="control-label" style="float: right;">Miễn thị thực
	    						<input type="checkbox" name="selectedJob4" checked="checked"/>
    						</label>
    					</div>
    					<div class="col-sm-2" style="padding-top: 15px;">
    						
    						<label class="control-label" style="float: right;">Thị thực
	    						<input type="checkbox" name="selectedJob4"  checked="checked"/>
    						</label>
    						<br>
    						<label class="control-label" style="float: right;">Thẻ tạm trú
	    						<input type="checkbox" name="selectedJob4" checked="checked"/>
    						</label>
    						<br>
    						<label class="control-label" style="float: right;">Thẻ thường trú
	    						<input type="checkbox" name="selectedJob4"  checked="checked"/>
    						</label>
    						<br>
    						<label class="control-label" style="float: right;">NNN chết
	    						<input type="checkbox" name="selectedJob4"  checked="checked"/>
    						</label>
    					</div>
    					<div class="col-sm-2">
    						<label class="control-label" style="float: right;">Chọn
	    						<input type="checkbox" id="allChk5"  checked="checked"/>
    						</label>
    						<br>
    						<label class="control-label" style="float: right;">Nhận trở lại
	    						<input type="checkbox" name="selectedJob5" checked="checked"/>
    						</label>
    						<br>
    						<label class="control-label" style="float: right;">NVN về thường trú
	    						<input type="checkbox" name="selectedJob5" checked="checked"/>
    						</label>
    						<br>
    						<label class="control-label" style="float: right;">Hồ sơ tư pháp
	    						<input type="checkbox" name="selectedJob5" checked="checked"/>
    						</label>
    					</div>
    				</div>
    			</fieldset>
    			
    		</div>
    		<div class="col-sm-2">
    			<div> 				
					<img src="<c:url value='/resources/images/No_Image.jpg' />" class="img-border" height="130px" width="90px" />										                           							                							       					                       										                  								                    									               			
    			</div>
    			<div>
    				<label class="control-label">Ảnh hồ sơ</label>
    				<input type="text" class="form-control" />
    			</div>
    			<div>
    				<label class="control-label">Mã cá nhân</label>
    				<input type="text" class="form-control" />
    			</div>
    		</div>
			<div class="col-sm-12" style="height: 10px;"></div>
   			<div class="col-sm-12 col-md-12" style="text-align: center;">
   			
	   				
		       		<button type="button" onclick="searchImmi();" class="btn btn-success" data-dismiss="modal" aria-label="Close" style="margin-right: 10px;">
		       			<span class="glyphicon glyphicon-search"></span > LIỆT KÊ
		       		</button>
		       		<button type="button" onclick="" class="btn btn-success" data-dismiss="modal" aria-label="Close" style="margin-right: 10px;">
		       			<span class="glyphicon glyphicon-trash"></span > XÓA ĐIỀU KIỆN
		       		</button>
		       		<button type="button" onclick="" class="btn btn-success" data-dismiss="modal" aria-label="Close">
		       			<span class="glyphicon glyphicon-print"></span > LƯU ẢNH
		       		</button>
   			
   			</div>
			<div class="col-sm-12">
				<fieldset>
					<legend>Danh sách khách</legend>
					<div>
						<table id="tbKhachXNC" style="min-height: 80px;overflow: auto;" class="table table-bordered table-sm table-hover" cellspacing="0" width="100%">
	               			<thead>
	                			<tr>
	                				<th class="th-sm"> Họ và tên
	                				</th>
	                				<th class="th-sm"> Giới tính
	                				</th>
	                				<th class="th-sm"> Ngày sinh
	                				</th>		                            				
	                				<th class="th-sm"> Quốc tịch
	                				</th>
	                				<th class="th-sm"> Số hộ chiếu
	                				</th>
	                				<th class="th-sm"> Số thị thực
	                				</th>
	                				<th class="th-sm"> Số CMND/CCCD
	                				</th>
	                				<th class="th-sm"> Nơi sinh
	                				</th>		                            				
	                			</tr>
	               			</thead>
	               			<tbody id="tbdKhachXNC">
	               				<c:forEach items="${dsXNC}" var="item">
	                				<tr>
	                					<td>${item.fullName}</td>
	                					<td>${item.gender}</td>
	                					<td align="center">${item.dob}</td>
	                					<td>${item.countryCode}</td>
	                					<td>${item.passportNo}</td>
	                					<td>${item.visaNo}</td>
	                					<td>${item.nin}</td>
	                					<td>${item.pob}</td>
	                				</tr>
	               				</c:forEach>
	               			</tbody>
	               			<c:if test="${empty dsXNC}">
								  
								   <tbody class="e-not-found ng-scope"><tr>
								  <td colspan="10" style="height: 362px">
								  <div class="e-not-found-center"><div class="e-not-found-title">Không tìm thấy kết quả</div><div class="e-not-found-title-child">Hãy thử tìm kiếm hoặc sử dụng bộ lọc khác</div></div>
								  </td></tr></tbody>
								  
								
								</c:if>
	               		</table>
					</div>
					 <table class="table e-grid-table e-border">
                            <tfoot>
                                <tr>
                                    <th>
                                    
                                      <c:if test="${not empty dsXNC}">
						
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
			<div style="clear:both;height:15px;"></div>
			<div class="col-sm-12">
                <table class="borderless">
                    <tbody>
                        <tr>

                            <td><button class="btn color-blue btn-sm btn-custom btn27" ><i aria-hidden="true"></i>X/N cảnh</button></td>
                            <td><button class="btn color-blue btn-sm btn-custom disabled btn27" ><i aria-hidden="true"></i>Hộ chiếu</button></td>
                            <td><button class="btn color-blue btn-sm btn-custom disabled btn27" ><i aria-hidden="true"></i>ABTC VN</button></td>
                            <td><button class="btn color-blue btn-sm btn-custom disabled btn27" ><i aria-hidden="true"></i>Vi phạm</button></td>
                            <td><button class="btn color-blue btn-sm btn-custom disabled btn27" ><i aria-hidden="true"></i>Duyệt nhập</button></td>
                            <td><button class="btn color-blue btn-sm btn-custom disabled btn27" ><i aria-hidden="true"></i>Thị thực</button></td>
                            <td><button class="btn color-blue btn-sm btn-custom disabled btn27" ><i aria-hidden="true"></i>Nhận trở lại</button></td>
                            <td><button class="btn color-blue btn-sm btn-custom disabled btn27" ><i aria-hidden="true"></i>Kết hôn</button></td>
                            <td><button class="btn color-blue btn-sm btn-custom disabled btn27" ><i aria-hidden="true"></i>Nhập QT</button></td>
                        </tr>
                        <tr>
                            <td><button class="btn color-blue btn-sm btn-custom btn27" ><i aria-hidden="true"></i>Tạm trú</button></td>
                            <td><button class="btn color-blue btn-sm btn-custom btn27" ><i aria-hidden="true"></i></button></td>
                            <td><button class="btn color-blue btn-sm btn-custom btn27" ><i aria-hidden="true">Xác minh NS</i></button></td>
                            <td><button class="btn color-blue btn-sm btn-custom disabled btn27" ><i aria-hidden="true"></i>Lệnh TX</button></td>
                            <td><button class="btn color-blue btn-sm btn-custom disabled btn27" ><i aria-hidden="true"></i>ABTC NN</button></td>
                            <td><button class="btn color-blue btn-sm btn-custom disabled btn27" ><i aria-hidden="true"></i>Thẻ tạm trú</button></td>
                            <td><button class="btn color-blue btn-sm btn-custom disabled btn27" ><i aria-hidden="true"></i>Về thường trú</button></td>
                            <td><button class="btn color-blue btn-sm btn-custom disabled btn27" ><i aria-hidden="true"></i>Xin con nuôi</button></td>
                            <td><button class="btn color-blue btn-sm btn-custom disabled btn27" ><i aria-hidden="true"></i>Trở lại QT</button></td>
                        </tr>
                        <tr>
                            <td><button class="btn color-blue btn-sm btn-custom btn27" ><i aria-hidden="true"></i>Thông hành</button></td>
                            <td><button class="btn color-blue btn-sm btn-custom disabled btn27" ><i aria-hidden="true"></i>GP XNC</button></td>
                            <td><button class="btn color-blue btn-sm btn-custom disabled btn27" ><i aria-hidden="true"></i>Xác nhận NS</button></td>
                            <td><button class="btn color-blue btn-sm btn-custom disabled btn27" ><i aria-hidden="true"></i>TT Tổng hợp</button></td>
                            <td><button class="btn color-blue btn-sm btn-custom disabled btn27" ><i aria-hidden="true"></i>Thẻ du lịch</button></td>
                            <td><button class="btn color-blue btn-sm btn-custom disabled btn27" ><i aria-hidden="true"></i>Thường trú</button></td>
                            <td><button class="btn color-blue btn-sm btn-custom disabled btn27" ><i aria-hidden="true"></i>Miễn T.T</button></td>
                            <td><button class="btn color-blue btn-sm btn-custom disabled btn27" ><i aria-hidden="true"></i>NNN chết</button></td>
                            <td><button class="btn color-blue btn-sm btn-custom disabled btn27" ><i aria-hidden="true"></i>Thôi QT</button></td>
                        </tr>
                    </tbody>
                </table>
            </div>
            <div style="clear:both;height:15px;"></div>
            <div class="col-sm-12">
                <div class="col-sm-2">
                    <div class="form-group">
                        <label class="col-sm-12 control-label text-left">Người duyệt</label>
                        <div class="col-sm-12">
                            <select class="form-group">
		    					<c:forEach items="${usersKhuVuc}" var="iem">
			    					<option value="${iem.key}">${iem.value}</option>
		    					</c:forEach>
		    				</select>
                        </div>
                    </div>
                </div>
                <div class="col-sm-2">
                    <label class="control-label text-right">Chức vụ</label>
                    <input type="text" class="form-control">
                </div>
                <div class="col-sm-4">
                    <label class="control-label text-right">Kính gửi</label>
                    <input type="text" class="form-control">
                </div>
                <div class="col-sm-2">
                    <label class="control-label text-right">Số CV</label>
                    <input type="text" class="form-control">
                </div>
                <div class="col-sm-2">
                    <label class="control-label text-right">Ngày CV</label>
                    <input type="text" class="form-control">
                </div>
                <div style="clear:both;height:5px;"></div>
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
	      			<p style="display: inline-block;position: absolute;margin-left: 10px;margin-top: 5px;">Bạn có muốn tra cứu tiếp hồ sơ?</p>
	      		</div>
	      </div>							      
	      <div class="modal-footer" style="padding: 5px;">
	       		<button type="button" onclick="btnXuLyHoSo('N');" class="btn btn-warning" data-dismiss="modal" aria-label="Close" style="float: right;">
	       			<span class="glyphicon glyphicon-remove btn-boot" style="margin-right: 2px;"></span ><span class="btn-boot"> KHÔNG</span>
	       		</button>
	       		<button type="button" onclick="btnXuLyHoSo('Y');" class="btn btn-success" data-dismiss="modal" aria-label="Close" style="float: right;margin-right: 10px;">
	       			<span class="glyphicon glyphicon-ok btn-boot" style="margin-right: 2px;"></span ><span class="btn-boot"> CÓ</span>
	       		</button>
	       		
	      </div>
	    </div>
	  </div>
	</div>	
	<!-- ---------------------------------------------------------------------------->
	<!--<div style="display: flex;flex: 0 auto;justify-content: center;">
	<div class="waitingWhenDoneWaiting" style="display:block; position: fixed; bottom:-5px; border-radius: 8px; border: 2px solid; background-color: rgb(255, 255, 255); z-index: +1000;border-color: #2a7044 #2a7044 transparent #2a7044;">
		<div style="margin: 10px"> 
			<button type="button"  onclick="processHS();" name="approve" class="btn btn-success">
				<span class="glyphicon glyphicon-list-alt"></span> In chi tiết HS
			</button>
			<button type="button"  onclick="processHS();" name="approve" class="btn btn-success">
				<span class="glyphicon glyphicon-list-alt"></span> In danh sách HS
			</button>
		</div>
	</div>	
	</div>-->
<div id="dialog-confirm"></div>
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
						document.forms["formData"].action = '${searchImmiUrl}';  
						document.forms["formData"].submit();  
					}
				}
			});
	
		function searchImmi(){
			document.forms["formData"].action = '${searchImmiUrl}';
			document.forms["formData"].submit();
		}
	
		var check = '';
		function processHS(){
			
		}
		
		
		//var stage = 0;
		$('#allChk1').change(function(){
			var total = document.getElementsByName("selectedJob1");
			var root = document.getElementById("allChk1");
			for(var i = 0; i < total.length; i++){
				if(root.checked == true){
					total[i].checked = true;
				}else{
					total[i].checked = false;
				}
			}
		});
		
		//var stage = 0;
		$('#allChk2').change(function(){
			var total = document.getElementsByName("selectedJob2");
			var root = document.getElementById("allChk2");
			for(var i = 0; i < total.length; i++){
				if(root.checked == true){
					total[i].checked = true;
				}else{
					total[i].checked = false;
				}
			}
		});
		
		//var stage = 0;
		$('#allChk3').change(function(){
			var total = document.getElementsByName("selectedJob3");
			var root = document.getElementById("allChk3");
			for(var i = 0; i < total.length; i++){
				if(root.checked == true){
					total[i].checked = true;
				}else{
					total[i].checked = false;
				}
			}
		});
		
		//var stage = 0;
		$('#allChk4').change(function(){
			var total = document.getElementsByName("selectedJob4");
			var root = document.getElementById("allChk4");
			for(var i = 0; i < total.length; i++){
				if(root.checked == true){
					total[i].checked = true;
				}else{
					total[i].checked = false;
				}
			}
		});
		
		//var stage = 0;
		$('#allChk5').change(function(){
			var total = document.getElementsByName("selectedJob5");
			var root = document.getElementById("allChk5");
			for(var i = 0; i < total.length; i++){
				if(root.checked == true){
					total[i].checked = true;
				}else{
					total[i].checked = false;
				}
			}
		});
		
		$("#txtDob").datepicker({
			changeMonth : true,
			changeYear : true,
			showSecond : true,
			controlType : 'select',
			dateFormat : 'dd/mm/yy',
			yearRange : "-100:+10"
		}).keyup(function(e) {
			
		});
		
		$('#txtDob').mask("00/00/0000", { placeholder: "__/__/____" });
		$('#txtStartYear').mask("0000", { placeholder: "____" });
		$('#txtEndYear').mask("0000", { placeholder: "____" });
		
		
		$('#stylDob').change(function(){
			var concept = $('#stylDob').find(":selected").val();
			if(concept == 'D'){
				$('.kinf-1').css('display', 'none');
				$('.kinf').css('display', 'block');
			}
			if(concept == 'Y'){
				$('.kinf-1').css('display', 'block');
				$('.kinf').css('display', 'none');
			}
		});
		
		$(document).ready(function() {
			var concept = $('#stylDob').find(":selected").val();
			if(concept == 'D'){
				$('.kinf-1').css('display', 'none');
				$('.kinf').css('display', 'block');
			}
			if(concept == 'Y'){
				$('.kinf-1').css('display', 'block');
				$('.kinf').css('display', 'none');
			}
		});
	</script>
	
</form:form>
</div>

