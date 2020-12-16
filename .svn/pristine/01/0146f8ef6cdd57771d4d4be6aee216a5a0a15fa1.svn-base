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
<c:url var="invesProcessUrl" value="/servlet/destroyPassport/showListPassport" />
<c:url var="nextProcessUrl" value="/servlet/investionProcess/invesProcess" />
<c:url var="showMessageUrl" value="/servlet/investionProcess/showMessage" />
<c:url var="detailPassportUrl" value="/servlet/destroyPassport/detailPassport" />
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
.width-100 {
	width: 100%;
}
</style>
<div class="content-item-title">
            <div class="oplog-title__txt">Hủy hộ chiếu</div>
        </div>
<div class="content-item-main">
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

  
   <div class="content-item-information">
     
   <div class="form-group form-profile pad-bottom-15">
   		<div class="col-md-10 col-sm-10">
	   		<fieldset class="scheduler-border">
	   			 <legend class="scheduler-border">Thông tin tìm kiếm</legend>
	   			 <div class="col-sm-5 pad-top-10">
	   			 	<span class="col-sm-4">Số danh sách B</span>
	   			 	<div class="col-sm-8">
	   			 		<form:input type="text" class="width-100" path="packageCode" id="soDanhSachB" />
	   			 	</div>
	   			 </div>	
	   			  <div class="col-sm-5 pad-top-10">
	   			 	<span class="col-sm-4">Số hộ chiếu</span>
	   			 	<div class="col-sm-8">
	   			 		<form:input type="text" class="width-100"  path="passportNo" id="soHoChieu" />
	   			 	</div>
	   			 </div>	
		         <div class="col-sm-2 pad-top-10">
		            <button type="button" onclick="doApplyFilter();" class="btn_small btn-success">
			        	<span class="glyphicon glyphicon-search"></span> Tìm kiếm
				    </button>	      
			     </div>                
	   		</fieldset>
   		</div>
        <div class="col-md-2 col-sm-2">
        	<div style="text-align:center;" id="anhMatHC">	
				<img src="<c:url value='/resources/images/No_Image.jpg' />" class="img-border" height="130px" width="90px" />										                           							                							       					                       										                  								                    									               
			</div>	
        </div>           
     </div>
			
						

	
			<div class="form-group">
			<div class="col-sm-12 none-pad-right none-pad-left">
					<fieldset class="scheduler-border">
							<legend class="scheduler-border">Danh sách hồ sơ hộ chiếu</legend>
							<div style="height: 400px; overflow: auto;">
						      	<table id="dtBasicExample" class="table table-bordered table-sm table-hover" cellspacing="0" width="100%">
								  <thead>
								    <tr>								    
								      <th class="th-sm">STT
								
								      </th>								     
								      <th class="th-sm">Họ tên
								
								      </th>
								      <th class="th-sm">Ngày sinh
								
								      </th>
								      <th class="th-sm">Giới tính
								
								      </th>
								      <th class="th-sm">Số hộ chiếu
								
								      </th>
								      <th class="th-sm">CMND/CCCD
								
								      </th>
								      <th class="th-sm">Nơi sinh
								      </th>								      
								      <th class="th-sm" style="max-width: 40px;">
										  <input type="checkbox" id="idCheckAll" />	
								      </th>	
								    </tr>
								  </thead>
								   <tbody>
								  <c:if test="${not empty dsXuLyA}">
									 
									    <c:forEach items="${dsXuLyA}" var="item">
										    <tr>										      
										      <td class="align-central">${item.stt}</td>										     
										      <td>${item.fullName}</td>
										      <td class="align-central">${item.dob}</td>
										      <td>${item.gender}</td>
										      <td id="ppNo">${item.passportNo}</td>										      
										      <td>${item.nin}</td>
										      <td>${item.placeOfBirth}</td>
										      <td class="align-central">
										      		<form:checkbox path="selectedJobIds" value="${item.passportNo}"
															cssClass="${item.currentlyAssignedToAnInvestigationOfficer}" />
										      </td>
										    </tr>
									    </c:forEach>
									 
								  </c:if>
								   </tbody>
								    <c:if test="${empty dsXuLyA}">
								  
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
		
		<div class="form-group">
			<div class="col-md-3">
				<div>Cán bộ in</div>
				<input type="text" class="width-100" id="txtCBIn" disabled="disabled" />
			</div>
			<div class="col-md-3">
				<div>Ngày in</div>
				<input type="text" class="width-100" id="txtNgayIn" disabled="disabled" />
			</div>
			<div class="col-md-3">
				<div>Hạn HC</div>
				<input type="text" class="width-100" id="txtHanHC" disabled="disabled" />
			</div>
			<div class="col-md-3">
				<div>Trạng thái</div>
				<input type="text" class="width-100" id="txtTrangThai" disabled="disabled" />
			</div>
		</div>
		<div class="form-group">
			<div class="col-md-6">
				<div>ICAO 1</div>
				<input type="text" class="width-100" id="txtIcao1" disabled="disabled" />
			</div>
			<div class="col-md-6">
				<div>ICAO 2</div>
				<input type="text" class="width-100" id="txtIcao2" disabled="disabled" />
			</div>
		</div>
		<div class="form-group">
			<div class="col-sm-12">
				<fieldset class="scheduler-border" style="height: 150px;overflow: auto;">
					<legend>Thông tin trẻ em đi kèm</legend>
					<div>
						<table id="tbTreem" class="table table-bordered table-sm table-hover" cellspacing="0" width="100%">
	               			<thead>
	                			<tr>
	                				<th class="th-sm"> STT
	                				</th>
	                				<th class="th-sm"> Họ tên
	                				</th>
	                				<th class="th-sm"> Ngày sinh
	                				</th>		                            				
	                				<th class="th-sm"> Giới tính
	                				</th>
	                				<th class="th-sm"> Trạng thái
	                				</th>
	                				<th class="th-sm"> Ghi chú
	                				</th>		                            				
	                			</tr>
	               			</thead>
	               			<tbody id="ibdtbTreem">
	               				<c:forEach items="${dsTreEm}" var="item">
	                				<tr>
	                					<td></td>
	                					<td></td>
	                					<td></td>
	                					<td></td>
	                					<td></td>
	                					<td></td>
	                				</tr>
	               				</c:forEach>
	               			</tbody>
	               		</table>
					</div>
				</fieldset>
			</div>
		</div>
		<div class="form-group" style="margin-bottom: 30px;">
			<div class="col-sm-12">
				<fieldset class="scheduler-border">
					<legend>Thông tin hủy</legend>
					<span class="col-sm-3" style="text-align: right;">Phân loại lý do hủy<span class="colorred">*</span></span>
					<div class="col-md-8">
						<form:select path="reason" id="phanLoaiLD" style="width: 50%;">
							<c:forEach items="${reasonLost}" var="rs">
								<form:option value="${rs.key}">${rs.value}</form:option>
							</c:forEach>
						</form:select>
					</div>
					<span class="col-sm-3 pad-top-10" style="text-align: right;">Lý do hủy<span class="colorred">*</span></span>
					<div class="col-md-8 pad-top-10">
						<form:textarea rows="3" cols="" style="width: 100%;" id="reasonNoteId" path="reasonNote"></form:textarea>
					</div>
				</fieldset>
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
	<div style="display: flex;flex: 0 auto;justify-content: center;">
	<div class="waitingWhenDoneWaiting" style="display:block; position: fixed; bottom:-5px; border-radius: 8px; border: 2px solid; background-color: rgb(255, 255, 255); z-index: +1000;border-color: #2a7044 #2a7044 transparent #2a7044;">
		<div style="margin: 10px"> 
			<button type="button"  onclick="processHS();" name="approve" class="btn btn-success">
				<span class="glyphicon glyphicon-ban-circle"></span> Hủy hộ chiếu
			</button>
		</div>
	</div>	
	</div>
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
						document.forms["formData"].action = '${invesProcessUrl}';  
						document.forms["formData"].submit();  
					}
				}
			});
		function doSubmitSave(form) {
			form.action = '<c:url value="/servlet/investigation/search" />';
			form.submit();
		}
	</script>
	<script type="text/javascript">
		var check = '';
		function processHS(){
			var mess = checkRong();
			if(mess != ''){
				$.notify(mess, {
					globalPosition: 'bottom left',
					className: 'warn',
				});
				return;
			}
			var note = $('textarea#reasonNoteId').val();
			if(note == ''){
				$.notify('Lý do hủy không được bỏ trống.', {
					globalPosition: 'bottom left',
					className: 'warn',
				});
				return;
			}
			document.forms["formData"].action = '<c:url value="/servlet/destroyPassport/runDestroy" />';
			document.forms["formData"].submit();
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
	
	
		
	
		function doApplyFilter() {
			document.forms["formData"].action = '<c:url value="/servlet/destroyPassport/showListPassport" />';
			document.forms["formData"].submit();
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
		
		$(document).ready(function() {
			$("table#dtBasicExample > tbody > tr").click(function(){
				$('table#dtBasicExample > tbody > tr').removeClass("back-gr");
				$(this).addClass("back-gr");
				var passportId = $(this).find("#ppNo").text(); 
				var url = '${detailPassportUrl}';
				$.ajax({
					url : url,
					cache: false,
					type: "POST",
					data: {
						passportNo : passportId
					},
					success : function(data) {
						$('#txtHanHC').val(data.dateEprity);
						$('#txtTrangThai').val(data.stageList);
						$('#anhMatHC').html(data.photoStr);
						var tb1 = document.getElementById("ibdtbTreem");
						$("#tbTreem tbody").empty();
						for(var i = 0; i < data.listInfo.length; i++){
			        		var row = tb1.insertRow(i);
			        		var cell1 = row.insertCell(0);
			        		var cell2 = row.insertCell(1);
			        		var cell3 = row.insertCell(2);
			        		var cell4 = row.insertCell(3);
			        		var cell5 = row.insertCell(4);
			        		var cell6 = row.insertCell(5);
			        		cell1.innerHTML = data.listInfo[i].stt;
			        		cell2.innerHTML = data.listInfo[i].name;
			        		cell3.innerHTML = data.listInfo[i].dateOfBirth;
			        		cell4.innerHTML = data.listInfo[i].gender;
			        		cell5.innerHTML = '';
			        		cell6.innerHTML = '';
			        	}
					}
				});
			});
		});
		
	</script>
	
</form:form>
</div>

