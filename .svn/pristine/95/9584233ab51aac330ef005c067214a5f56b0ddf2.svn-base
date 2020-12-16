<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>
<c:url var="JobInvestigationConfirmUrl" value="/servlet/investigationConfirm/investigationConfirmList" />
<c:url var="searchInvesBAgainUrl" value="/servlet/investigationConfirm/searchInvesBAgain" />
<c:url var="searchApproveUrl" value="/servlet/investigationConfirm/searchApproveNic" />
<c:url var="detailInvesBAgainUrl" value="/servlet/investigationConfirm/detailInvesApprove" />
<c:url var="getPaymentByIdUrl" value="/servlet/investigationConfirm/getApprovePayment" />
<c:url var="getDocumentUrl" value="/servlet/investionProcess/getDocument" />
<c:url var="getApproveLeaderUrl" value="/servlet/investigationConfirm/getApproveLeader" />
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
									<input type="text" placeholder="Nhập số danh sách B" id="packageNumber" style="width: 100%;font-size: 12px;">
								</div>
								<div class="col-md-6 col-sm-6 top-mag-10">
									<input type="text" placeholder="Từ ngày" autocomplete="off" readonly="readonly" id="createDate" style="width: 100%;font-size: 12px;">
								</div>
								<div class="col-md-6 col-sm-6 top-mag-10">
									<input type="text" placeholder="Đến ngày" autocomplete="off" readonly="readonly" id="endDate" style="width: 100%;font-size: 12px;">
								</div>
								<div class="col-md-6 col-sm-6 top-mag-10">
									<select style="width: 100%;font-size: 12px;" id="styleList">
										<option value="0">Chưa duyệt</option>
										<option value="">Tất cả</option>
										<option value="1">Đã duyệt</option>
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
										      <th class="th-sm" style="min-width: 100px;">Số DS
										      </th>	
										      <th class="th-sm">Ngày lập
										
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
							<div class="new-heading-2">PHÊ DUYỆT ĐƠN ĐỀ NGHỊ CẤP HỘ CHIẾU</div>
							<div class="col-md-12 col-sm-12" style="padding: 0px;">
								<div class="col-md-10 col-sm-10" style="padding: 0px;height: 250px;overflow: auto;overflow-x: hidden;overflow-y: auto;" id="div_phuphi">
									<div>
								      	<table id="tbPhuPhi" class="table table-bordered table-sm table-hover" cellspacing="0" width="100%">
										  <thead>
										    <tr>
										      <th class="th-sm">Mã
										
										      </th>	
										      <th class="th-sm">Tên
										
										      </th>
										      <th class="th-sm">SL
										
										      </th>
										      <th class="th-sm">Đơn giá
										
										      </th>
										      <th class="th-sm">Thành tiền
										
										      </th>										  										 
										    </tr>
										  </thead>
										  <!--<c:if test="${not empty chiTietDS}">-->
											  <tbody>
											    <c:forEach items="${ctPhuPhi}" var="phi_item">
												    <tr>
												      	<td>${phi_item.maPhuPhi}</td>
												  		<td>${phi_item.tenPhuPhi}</td>
												  		<td>1</td>
												  		<td>${phi_item.soTien}</td>
												  		<td>${phi_item.soTien}</td>
												    </tr>
											    </c:forEach>
											  </tbody>
										 <!-- </c:if>-->
										</table>
										<c:if test="${empty ctPhuPhi}">
										  	<div class="style-no-record opentb-3">Không tìm thấy kết quả</div>
										</c:if>
							      </div>
							      <div style="float: right;margin-right: 10px;">Tổng tiền: <label id="idTongTien">${phi_item.soTien}</label></div>
								</div>
								<div class="col-md-2 col-sm-2">
									<div id="dialog-image_photoCompare" style="display: block;width: 100px;margin-right: 10px;height: 150px;">
										<div class="centerCaption">       
									         <div style="text-align:center">							             									                 
									             <div class="khongAnhMat">
									                  <img src="<c:url value='/resources/images/No_Image.jpg' />"  width="100" title="Hit Candidate" />
									             </div>									                       									                  								                    									               
									         </div>								        
									    </div>
								    </div>
								    <input type="text" id="codeDoc" hidden="hidden"> 
									<input type="text" id="codeStt" hidden="hidden">
								</div>
							</div>
							<div class="col-md-12 col-sm-12" style=" padding-left: 0;">
								<div style="height: 350px;overflow-x: hidden;overflow-y: auto;" id="div_chitiet">
							      	<table id="tbChiTietDS" class="table table-bordered table-sm table-hover" cellspacing="0" width="100%">
									  <thead>
									    <tr>
									      <th class="th-sm" style="max-width: 60px;">Đề xuất
									
									      </th>	
									      <th class="th-sm" style="max-width: 50px;">STT
									
									      </th>	
									      <th class="th-sm">Họ tên
									
									      </th>
									      <th class="th-sm">Giới tính
									
									      </th>
									      <th class="th-sm">Ngày sinh
									
									      </th>
									      <th class="th-sm">Nội dung đề xuất
									
									      </th>
									      <th class="th-sm">Ghi chú
									
									      </th>
									      <th class="th-sm" style="display: none;">
									
									      </th>

									    </tr>
									  </thead>
									  <!--<c:if test="${not empty chiTietDS}">-->
										  <tbody>
										    <c:forEach items="${chiTietDSDuyet}" var="item">
											    <tr>
											      	<td>
											      		<select id="deXuat">
											      			<option value="APPROVE">D</option>
											      			<option value="REJECT">K</option>
											      			<option value="REINVESTIGATE">C</option>
											      			<option value="0"></option>
											      		</select>
											      	</td>
											      	<td></td>
											      	<td>${item.fullName}</td>
											      	<td>${item.gender}</td>
											      	<td>${item.dob}</td>
											      	<td></td>
											      	<td></td>
											      	<td style="display: none;"></td>
											  
											    </tr>
										    </c:forEach>
										  </tbody>
									 <!-- </c:if>-->
									</table>
									<c:if test="${empty chiTietDSDuyet}">
									  	<div class="style-no-record opentb-1">Không tìm thấy kết quả</div>
									</c:if>
						      </div>
								
							</div>
							<div id="ajax-load-qa"></div>			
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
						      <div class="modal-footer" style="height: 500px;overflow: auto;padding-left: 20px;" id="idTaiLieu">
						      </div>
						    </div>
						  </div>
						</div>	
						<!-- -------------------------------------------------------------------------- -->
						
						<!-- Model in đề xuất -->
						<div class="modal fade" id="inDeXuat" tabindex="-1" role="dialog" aria-labelledby="exampleModalCenterTitle" aria-hidden="true" >
						  <div class="modal-dialog modal-dialog-centered" role="document" style="width: 1100px">
						    <div class="modal-content">
						      <div class="modal-header">
						        <h5 class="modal-title" id="exampleModalLongTitle" style="display: inline-block;">IN DANH SÁCH ĐỀ XUẤT</h5>
						        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
						          		<img style="width: 25px;height: 25px;" src="<c:url value='/resources/images/dongform.png' />" title="Đóng" />
						        </button>
						      </div>							      
						      <div class="modal-footer" style="height: 500px;overflow: auto;padding-left: 100px;padding-right: 100px;" id="idDeXuat">
						       		
						       		
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
			<button type="button" id="idPheDuyet"  onclick="printReciept();" data-toggle="modal" class="btn btn-success">
				<span class="glyphicon glyphicon-pencil"></span> Phê duyệt
			</button>
			<button type="button"  onclick="" data-toggle="modal" data-target="#inDeXuat" name="approve" class="btn btn-success">
				<span class="glyphicon glyphicon-pencil"></span> Ký duyệt
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
	var tbChiTiet = $('#tbChiTietDS').DataTable({
		"ordering": false,
		"pagingType": "simple"
	});
	$('#tbDanhSachHS').DataTable({
		"ordering": false,
		"pagingType": "simple"
	});
	$('#tbPhuPhi').DataTable({
		"ordering": false,
		"pagingType": "simple"
	});
	var idBefore = ''; 
	var arrTranId = [];
	var danhSachB = '';
	var DOC_ID = 'null';
	var stl = '';
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
		
		function emptyChitietDanhSach() {
			var tb = $('#tbChiTietDS').DataTable();	
			tb.clear();
			$('#tbChiTietDS tbody tr').hide();
			$('#div_chitiet .dataTables_paginate ').css("display", 'none');
			$('#div_chitiet .dataTables_info').css("display", 'none');
			$(".khongAnhMat").html("<img src=\"<c:url value='/resources/images/No_Image.jpg' />\"   width=\"135\" />");
			$('.opentb-1').css('display', 'block');
		}
		
		function emptyChitietPhi() {
			var tb = $('#tbPhuPhi').DataTable();	
			tb.clear();
			$('#tbPhuPhi tbody tr').hide();
			$('#div_phuphi .dataTables_paginate ').css("display", 'none');
			$('#div_phuphi .dataTables_info').css("display", 'none');
			$('#idTongTien').text('');
			$('.opentb-3').css('display', 'block');
		}
		
		function printReciept() {
			//$('#idDeXuat').html("");
			if(danhSachB == ''){
				$.notify("Không có hồ sơ để phê duyệt.", {
					globalPosition: 'bottom left',
					className: 'warn',
				});
				return; 
			}
			
			var code = $('#codeDoc').val();
			var stt = $('#codeStt').val();
			var yourArray = {};
			var url = '${getApproveLeaderUrl}/' + danhSachB + '/' + code + '/' + stt;
			var checkDeXuat = true;
			for(var i = 0; i < arrTranId.length; i++){
				var noiDungDX = $('textarea#dx_' + arrTranId[i]).val();
				yourArray[arrTranId[i]] = noiDungDX;
				yourArray['key_' + arrTranId[i]] = $('#keydx_'+ arrTranId[i] +' option:selected').val();
				if(noiDungDX == ''){
					checkDeXuat = false;
					break;
				}
				
			}
			if(checkDeXuat == false){
				$.notify("Nội dung đề xuất không được để trống!", {
					globalPosition: 'bottom left',
					className: 'warn',
				});
				return; 
			}
			$('#ajax-load-qa').css('display', 'block');
			yourArray["codeListB"] = idBefore;
			$.ajax({
				url : url,
				type: "POST",
				data : JSON.stringify(yourArray),
				contentType : 'application/json',
				success : function(data) {
					emptyChitietPhi();
					emptyChitietDanhSach();
					doSubmitSave();
					if(data == 'Y'){
						danhSachB = '';
						$.notify("Phê duyệt danh sách thành công.", {
							globalPosition: 'bottom left',
							className: 'success',
						});
						
					}else{
						$.notify("Phê duyệt danh sách không thành công.", {
							globalPosition: 'bottom left',
							className: 'error',
						});
					}
					$('#idPheDuyet').attr('disabled', true);
					idBefore = '';
					$('#ajax-load-qa').css('display', 'none');				
				}
			});
		}

	$(function(){
		
		
		
		
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
	});

	function doSubmitSave() {
		
		$('#ajax-load-qa').css('display', 'block');
		var packageNumber = $('#packageNumber').val();
		var createDate = $('#createDate').val();
		var endDate = $('#endDate').val();
		var styleList = $('#styleList').find(":selected").val();
		stl = styleList;
		if(styleList == '1'){
			$('#idPheDuyet').attr('disabled', true);
		}else{
			$('#idPheDuyet').attr('disabled', false);
		}
		$.ajax({
			url : '${searchApproveUrl}',
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
	        if(stl == '0'){
		        $('#idPheDuyet').attr('disabled', false);
	        }
	    	danhSachB = '';
	    	arrTranId = [];
	    	$('#ajax-load-qa').css('display', 'block');
	        $('#tbDanhSachHS > tbody > tr').removeClass("back-gr");
	    	$(this).addClass("back-gr");
	    	var url = '${detailInvesBAgainUrl}';
	    	idBefore = dataDSHS[0];
	    	$.ajax({
				type : "POST",
				url : url,
				data:{
					packageNo: dataDSHS[0]
				},
				success: function(data) {
					//$('#ajax-load-qa').css('display', 'none');				  
					var tb = $('#tbChiTietDS').DataTable();	
					tb.clear();
					if(data.length > 0){
						$('.opentb-1').css('display', 'none');
						/*Mặc định ảnh mặt đầu tiên*/
			        	$(".khongAnhMat").html(data[0].photoStr);
			        	$("#codeDoc").val(data[0].archiveCode);
			        	$("#codeStt").val(data[0].archiveCodeStt);
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
										 '</select>',
										 (i + 1),
			        		             data[i].fullName,
			        		             data[i].gender,
			        		             data[i].dob,
			        		             data[i].leaderNote,			        		            
			        		             '<textarea maxlength="500" rows="3" cols="10" style="color: #000;width: 100%;height: 100%" name="noiDungDeXuat" id="dx_'+data[i].transactionId+'" ></textarea>',
			        		             data[i].transactionId
			        		       
			        		]).draw(false);
			        		$('#tbChiTietDS tr:eq('+(i+1)+') td:eq(1)').addClass("align-central");
			        		$('#tbChiTietDS tr:eq('+(i+1)+') td:eq(4)').addClass("align-central");
			        		$('#tbChiTietDS tr:eq('+(i+1)+') td:last').css('display', 'none');
			        		$('textarea#dx_' + data[i].transactionId).val(data[i].leaderNote);//dữ liệu nội dung đề xuất
			        		$('#keydx_' + data[i].transactionId).val(data[i].stageList);//kiểu đề xuất
			        		if(stl == '1'){
			        			$('textarea#dx_' + data[i].transactionId).attr('disabled', true);
			        		}else{
			        			$('textarea#dx_' + data[i].transactionId).attr('disabled', false);
			        		}
			        		if(data[0].handoverStatus == 'C'){
			        			$('textarea#dx_' + data[i].transactionId).attr('disabled', true);
			        			$('#idPheDuyet').attr('disabled', true);
			        		}else{
			        			$('textarea#dx_' + data[i].transactionId).attr('disabled', false);
			        			$('#idPheDuyet').attr('disabled', false);
			        		}
			        	}
			        	/*-------------------------------------------------*/			      
			        	$("#tbChiTietDS tbody tr:first").addClass("back-gr");
			        	DOC_ID = $("#tbChiTietDS tbody tr:first td:last").text();
			        	focusLine1(DOC_ID);
			        	$('.dataTables_paginate ').css("display", 'block');
						$('.dataTables_info').css("display", 'block');
					}else{		
						$('.dataTables_paginate ').hide();
						$('.dataTables_info').hide();
						$('.opentb-1').css('display', 'block');
					}
		        },
		        error: function(e){}
			});
	    });
	    
	    ///var tbChiTiet = $('#tbChiTietDS').DataTable();
	     
	    $('#tbChiTietDS tbody').on('click', 'tr', function(){
	    	 var dataCTHS = tbChiTiet.row(this).data();
	    	 if(DOC_ID == dataCTHS[7]){
		        	return;
		     }
	    	 $('#ajax-load-qa').css('display', 'block');	
	    	 $('#tbChiTietDS > tbody > tr').removeClass("back-gr");
	    	 $(this).addClass("back-gr");
	    	 var url = '${getPaymentByIdUrl}/' + dataCTHS[7];
	    	 DOC_ID = dataCTHS[7];
		     $.ajax({
					type : "POST",
					url : url,
					success: function(data) {
						var tb = $('#tbPhuPhi').DataTable();	
						tb.clear();
						if(data.length > 0){
				        	for(var i = 0; i < data.length; i++){
				        		tb.row.add([
				        		    data[i].maPhuPhi,
				        		    data[i].tenPhuPhi,
				        		    data[i].soLuong,
				        		    data[i].soTien,
				        		    data[i].soTien
				        		]).draw(false);
				        		$('#tbPhuPhi tr:eq('+(i+1)+') td:eq(2)').addClass("align-right");				        		
				        	}
							
						}
						$('#div_phuphi .dataTables_paginate ').css("display", 'none');
						$('#div_phuphi .dataTables_info').css("display", 'none');
						$(".khongAnhMat").html(data[0].photoStr);
						$('#idTongTien').text(data[0].tongTien);
						$('.opentb-3').css('display', 'none');
						$('#ajax-load-qa').css('display', 'none');
			        },
			        error: function(e){}
			 });
	    });
	    
	    function focusLine1(docID) {	    	 
	    	 var url = '${getPaymentByIdUrl}/' + docID;
		     $.ajax({
					type : "POST",
					url : url,
					success: function(data) {
						var tb = $('#tbPhuPhi').DataTable();	
						tb.clear();
						if(data.length > 0){
				        	for(var i = 0; i < data.length; i++){
				        		tb.row.add([
				        		    data[i].maPhuPhi,
				        		    data[i].tenPhuPhi,
				        		    data[i].soLuong,
				        		    data[i].soTien,
				        		    data[i].soTien
				        		]).draw(false);
				        		$('#tbPhuPhi tr:eq('+(i+1)+') td:eq(2)').addClass("align-right");
				        	}
							
						}		
						$('#div_phuphi .dataTables_paginate ').css("display", 'none');
						$('#div_phuphi .dataTables_info').css("display", 'none');
						$('#idTongTien').text(data[0].tongTien);
						$('.opentb-3').css('display', 'none');
						$('#ajax-load-qa').css('display', 'none');
			        },
			        error: function(e){}
			 });
	    }
		
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
	  }); 
	/*=====================================================================*/
	/*=========== END -- XỬ LÝ THAY ĐỔI SỐ HỘ CHIẾU ==============================*/
	

</script>
	
	
</form:form>



