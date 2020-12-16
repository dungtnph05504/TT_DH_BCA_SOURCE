<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/jquery-confirm/3.3.2/jquery-confirm.min.css">
<script src="https://cdnjs.cloudflare.com/ajax/libs/jquery-confirm/3.3.2/jquery-confirm.min.js"></script>
<c:url var="investigationJobAssignment1Url"
	value="/servlet/investigationAssignment/investigationAssignmentList1" />	
<c:url var="getListRic" value="/servlet/investigationAssignment/getListRic" />
<c:url var="showDetailPackageUrl" value="/servlet/investigationAssignment/showDetailPackage" />
<c:url var="loadAssignUrl" value="/servlet/investigationAssignment/loadAssign" />
<style>
.cls-mg-bot {
	margin-top: 20px;
}

.jconfirm .jconfirm-box div.jconfirm-content-pane .jconfirm-content {
    white-space: pre-wrap;
}
.pagebanner {
	margin-top: 15px;
}

fieldset {
	height: 500px;
}

</style>
<c:if test="${not empty requestScope.Errors}">
	<div class="border_error">
		<div class="error_left">
			<img align='left'
				src="<c:url value="/resources/images/error_new.jpg" />" width="30"
				height="30" />
		</div>


		<div class="errors">
			<table width="400" height="10" border="0" cellpadding="5">
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
			<table width="400" height="10" border="0" cellpadding="5">
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
<div class="content-item-title">
            <div class="oplog-title__txt">Danh sách phân công xử lý hồ sơ</div>
        </div>

<!--Content start-->
<div class="content-item-main">
<form:form modelAttribute="formData" name="formData">
	<!-- Phúc thêm lưu focus đơn vị tiếp nhận  -->
	<input type="hidden" name="ricId" id="ricId" /> 
	<input type="hidden" name="ricSelect" id="ricSelect" />
	
					<div>
						
						
						<div class="content-item-information">
                <div class="content-item-data" style="margin-top:10px">
                
                        <div class="form-horizontal form-profile">
                            <div class="form-group">
                                <label class="col-sm-1 control-label text-right">Nơi tiếp nhận</label>
                                <div class="col-sm-2">
                                     <select id="selectPlace" name="selectPlace">
											<option value="ALL" ${sAll}>Tất cả</option>
											<option value="XNC" ${sXnc} >Cục xuất nhập cảnh</option>
											<option value="DP" ${sDp} >Địa phương</option>
											<option value="BD" ${sBd} >Bưu điện</option>
										</select>   
                                </div>
                                <label class="col-sm-1 control-label text-right">Trạng thái</label>
                                <div class="col-sm-2">
                                    <form:select path="typeInvestigation" id="idInves" style="width: 100%;">
											<form:option value="0">Chưa phân công</form:option>
											<form:option value="1">Đã phân công</form:option>
										</form:select>
                                </div>

                                <label class="col-sm-1 control-label text-right">Từ ngày</label>
                                <div class="col-sm-1">
                                   <form:input id="createDate" name="createDate" path="createDate"
										size="12" maxlength="12" readonly="true" />
                                </div>
                                <label class="col-sm-1 control-label text-right">Đến ngày</label>
                                <div class="col-sm-1">
                                  <form:input id="endDate" name="endDate" path="endDate" 
										size="12" maxlength="12" readonly="true" />
                                </div>
                                <div class="col-sm-2" style="text-align: center">
                                  <button type="button" onclick="doApplyFilter();" class="btn_small btn-success">
										     <span class="glyphicon glyphicon-search"></span> Tìm kiếm
										</button>	
                                </div>
                            </div>
                        </div>
                 
                </div>
            </div>

					

					
				
	<div class="content-item-information">
					
						<!-- Phúc thay bảng mới 29/5/2018 -->
							<div class="col-md-3 col-sm-12">
								<fieldset class="scheduler-border">
									<legend class="scheduler-border">Tên cơ quan</legend>
									<div class="col-sm-12" style="height: 460px; overflow: auto;">
									<table id="tbTenCQ" class="table table-bordered table-sm table-hover" cellspacing="0" width="100%">
									  <thead>
									    <tr>
									      <th class="th-sm" style="max-width: 150px;">Mã 
									
									      </th>	
									      <th class="th-sm" style="min-width: 150px;">Tên cơ quan
									
									      </th>
									    </tr>
									  </thead>
									  <tbody id="listRic">
									    <c:forEach items="${listSiteRepository}" var="item">
										    <tr id="${item.key}">
										    	<td>${item.key}</td>
										    	<td>${item.value}</td>	
										    </tr>
									    </c:forEach>
									  </tbody>
									</table>
									</div>
								</fieldset>
							</div>
							<div class="col-md-6 col-sm-12">
								<fieldset class="scheduler-border">
									<legend class="scheduler-border">Danh sách A</legend>
									<div class="col-sm-12" style="height: 415px; overflow: auto;">
									<table id="tbDanhSachA" class="table table-bordered table-sm table-hover" cellspacing="0" width="100%">
									  <thead>
									    <tr>
									      <th class="th-sm" style="max-width: 150px;">Số DS tiếp nhận
									
									      </th>	
									      <th class="th-sm" style="min-width: 100px;">Tổng số hồ sơ
									
									      </th>
									      <th class="th-sm" style="min-width: 100px;">Cơ quan
									
									      </th>								     
									      <th class="th-sm" style="max-width: 50px;">Chọn
									      </th>
									    </tr>
									  </thead>
									    <tbody>
									  <c:if test="${not empty jobList}">
										
										    <c:forEach items="${jobList}" var="item">
											    <tr>
											      <td><a href="#" data-backdrop="static" data-keyboard="false" data-toggle="modal" data-target="#chiTietDanhSach" onclick="showDetailPackage('${item.packageId}')">${item.packageId}</a></td>	
											      <td class="align-right">${item.numberTran}</td>
											      <td>${item.ricName}</td>
											      <td class="align-central"><form:checkbox path="packageId" value="${item.packageId}" id="${item.packageId}"
													cssClass="${item.currentlyAssignedToAnInvestigationOfficer}" /></td>	
												  <td style="display: none;">${item.packageId}</td>	
											    </tr>
										    </c:forEach>
										
									  </c:if>
									    </tbody>
									      <c:if test="${empty jobList}">
								  
								   <tbody class="e-not-found ng-scope"><tr>
								  <td colspan="4" style="height: 354px">
								  <div class="e-not-found-center"><div class="e-not-found-title">Không tìm thấy kết quả</div><div class="e-not-found-title-child">Hãy thử tìm kiếm hoặc sử dụng bộ lọc khác</div></div>
								  </td></tr></tbody>
								  
								
								</c:if>
									</table>
									</div>
								<table class="table e-grid-table e-border">
                                <tfoot>
                                    <tr>
                                        <th>
                                        
                                          <c:if test="${not empty jobList}">
										
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
							<div class="col-md-3 col-sm-12">
								<fieldset class="scheduler-border">
									<legend class="scheduler-border">DS cán bộ xử lý</legend>
								<div  class="col-sm-12" style="height: 460px; overflow: auto;">
									<table id="tbCanBoXL" class="table table-bordered table-sm table-hover" cellspacing="0" width="100%">
									  <thead>
									    <tr>
									      <th class="th-sm" style="max-width: 50px;">
									
									      </th>	
									      <th class="th-sm" style="min-width: 150px;">Họ tên
									
									      </th>
									    </tr>
									  </thead>
									  <tbody>
									    <c:forEach items="${userAssignment}" var="item">
										    <tr>
										    	<td class="align-central"><form:checkbox path="selectedJobIds" value="${item.key}" id="${item.key}_CB"/></td>
										    	<td>${item.value}</td>	
										    </tr>
									    </c:forEach>
									  </tbody>
									</table>
									</div>
								</fieldset>
							</div>
							
							<div style="clear: both"></div>
							
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
	      			<p style="display: inline-block;position: absolute;margin-left: 10px;margin-top: 5px;">Bạn muốn xác nhận phân công?</p>
	      		</div>
	      </div>							      
	      <div class="modal-footer" style="padding: 5px;">
	       		<button type="button" class="btn btn-warning" data-dismiss="modal" aria-label="Close" style="float: right;">
	       			<span class="glyphicon glyphicon-remove btn-boot" style="margin-right: 2px;"></span ><span class="btn-boot"> KHÔNG</span>
	       		</button>
	       		<button type="button" onclick="doAssign();" class="btn btn-success" data-dismiss="modal" aria-label="Close" style="float: right;margin-right: 10px;">
	       			<span class="glyphicon glyphicon-ok btn-boot" style="margin-right: 2px;"></span ><span class="btn-boot"> CÓ</span>
	       		</button>
	       		
	      </div>
	    </div>
	  </div>
	</div>	
	<!-- ---------------------------------------------------------------------------->
	<!-- Modal Chi tiết danh sách -->
	<div class="modal fade" id="chiTietDanhSach" tabindex="-1" role="dialog" aria-labelledby="exampleModalCenterTitle" aria-hidden="true" >
	  <div class="modal-dialog modal-dialog-centered" role="document" style="width: 1200px;">
	    <div class="modal-content">
	      <div class="modal-header">
	        <h5 class="modal-title" id="exampleModalLongTitle" style="display: inline-block;">CHI TIẾT DANH SÁCH: <span style="font-size: 14px;" id="maDanhSach"></span></h5>
	        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
	          		<img style="width: 25px;height: 25px;" src="<c:url value='/resources/images/dongform.png' />" title="Đóng" />
	        </button>
	      </div>							      
	      <div class="modal-body" style="height: 500px; overflow: auto;" id="idChiTietDS">
	      		
	      </div>
	      <div class="modal-footer" style="padding: 5px;">
	       		<button type="button" class="btn btn-warning" data-dismiss="modal" aria-label="Close" style="float: right;">
	       			<span class="glyphicon glyphicon-remove btn-boot" style="margin-right: 2px;"></span ><span class="btn-boot"> Đóng</span>
	       		</button>
	       		
	      </div>
	    </div>
	  </div>
	</div>
	<!-- ----------------------------------------------------------------------------------------- -->
	<div style="display: flex;flex: 0 auto;justify-content: center;">
	<div class="waitingWhenDoneWaiting" style="display:block; position: fixed; bottom:-5px; border-radius: 8px; border: 2px solid; background-color: rgb(255, 255, 255); z-index: +1000;border-color: #2a7044 #2a7044 transparent #2a7044;">
		<div style="margin: 10px"> 
			<button type="button" onclick="doAssign();" name="approve" class="btn btn-success">
				  <span class="glyphicon glyphicon-check"></span> Phân công
			</button>
		</div>
	</div>
	</div>
	<%-- <form:input path="transactionType" type="hidden" name="transactionType" value="" />
													<form:input path="createDate" type="hidden" name="createDate" value="" />
													<form:input path="issueDate" type="hidden" name="issueDate" value="" />
													<form:input path="passportType" type="hidden" name="passportType" value="" />
													<form:input path="priority" type="hidden" name="priority" value="" />
													<form:input path="regSiteCode" type="hidden" name="regSiteCode" value="" /> --%>
</form:form>

</div>

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
	function doApplyFilter() {

		document.forms["formData"].action = '<c:url value="/servlet/investigationAssignment/applyFilter1" />';
		document.forms["formData"].submit();
	}
	
	$("#createDate").datepicker({
		//showOn : "button",
		//buttonImage : "<c:url value="/resources/images/calendar9.png" />",
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
	
	//$('#createDate').datepicker().datepicker('setDate', new Date());
	
	$("#endDate").datepicker({
		//showOn : "button",
		//buttonImage : "<c:url value="/resources/images/calendar9.png" />",
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
	
	//$('#createDate').datepicker().datepicker('setDate', "");endDate
</script>

<%
	/* ******************************************************************************************************************** */
%>
<%
	/* ************************************************** apply filter - end ********************************************** */
%>
<%
	/* ******************************************************************************************************************** */
%>

<%
	/* ******************************************************************************************************************** */
%>
<%
	/* *************************************************** assign - start ************************************************* */
%>
<%
	/* ******************************************************************************************************************** */
%>

<c:url var="urlAssign" value="/servlet/investigationAssignment/assign1" />

<div id="dialog-confirm-assign"></div>

<script type="text/javascript">
	//$('#dtBasicExample').DataTable();
	//$('.dataTables_length').addClass('bs-select');
	var totalPages = ${totalPage};
	var currentPage = ${pageNo};
	var pageSize = ${pageSize};
	window.pagObj = $('#pagination').twbsPagination({
			totalPages: totalPages,
			visiblePages: 5,
			startPage: currentPage,
			next: '<span class=\"glyphicon glyphicon-forward fix-paging\"></span>',
			prev: '<span class=\"glyphicon glyphicon-backward fix-paging\"></span>',
			last: '<span class=\"glyphicon glyphicon-step-forward fix-paging\"></span>',
			first: '<span class=\"glyphicon glyphicon-step-backward fix-paging\"></span>',
			onPageClick: function (event, page) {
				if (currentPage != page) {
					$('#pageNo').val(page);
					$('#ricId').val('${formData.regSiteCode}');	
					document.forms["formData"].action = '${investigationJobAssignment1Url}';  
					document.forms["formData"].submit();  
				}
			}
		});
	$("#dialog-confirm-assign").dialog({
		modal : true,
		autoOpen : false,
		width : 500,
		resizable : true,
		show : {
			effect : "fade",
			duration : 200
		},
		hide : {},
		buttons : {
			"Đồng ý" : function() {
				document.forms["formData"].action = "${urlAssign}";
				document.forms["formData"].submit();
			},
			"Hủy" : function() {
				$(this).dialog("close");
			}
		}
	});
	
</script>

<script type="text/javascript">

	function showDetailPackage(packageId){
		//alert(packageId);
		$('#idChiTietDS').html("");
		var url = '${showDetailPackageUrl}';
		$('#ajax-load-qa').css('display', 'block');
	
		$.ajax({
			url : url,
			cache: false,
			type: "POST",
			data:{
				packageNo : packageId,
			},
			success : function(data) {
				if(data != ''){
					$('#idChiTietDS').html(data);
					$('#maDanhSach').text(packageId);
				}else{
					$('#idChiTietDS').html("<div>Không có tài liệu.</div>");
				}
				$('#ajax-load-qa').css('display', 'none');				
			}
		});
	}

	function doAssign() {

		var messages = validateForAssignment();
		if (messages != '') {
			//alert(messages);
			/*$.alert({
				title: 'Thông báo',
				content: "<img style=\"margin-bottom: 5px;\" src=\"/eppcentral/resources/images/warning_1b.png\">" + messages,
			});*/
			$.notify(messages, {
				globalPosition: 'bottom left',
				className: 'warn',
			});
			return;
		}

		//$("#dialog-confirm-assign").dialog('option', 'title','Xác nhận phân công');
		//$("#dialog-confirm-assign").html("Tiến hành phân công / phân công lại các hồ sơ bạn đã chọn?");
		//$("#dialog-confirm-assign").dialog('open');
			/* $.confirm({
			    title: 'Xác nhận phân công',
			    content: "<img style=\"margin-bottom: 5px;\" src=\"/eppcentral/resources/images/question-1.png\">" + ' Bạn có muốn tiếp tục thực hiện.',
			    buttons: {			       		       
			        "Có": function () {
			        	document.forms["formData"].action = "${urlAssign}";
						document.forms["formData"].submit();
			        },
			        "Không": function () {
			        }
			    }
			}); */
		document.forms["formData"].action = "${urlAssign}";
		document.forms["formData"].submit();
	}

	function validateForAssignment() {

		var numberOfJobsInPage = ${totalRecords};

		if (numberOfJobsInPage == 0) {
			return 'Không có danh sách để phân công.';
		}

		//var inp = $(".assignToIdStyle").val();
		var dsCanBo = document.getElementsByName("selectedJobIds");
		var checkDS = false;
		for(var i = 0; i < dsCanBo.length; i++){
			if(dsCanBo[i].checked){
				checkDS = true;
				break;
			}
		}		
		if (!checkDS) {
			return 'Vui lòng chọn cán bộ để phân công.';
		}

		if (($(".currentlyNotAssignedToAnInvestigationOfficer:checked").length + $(".currentlyAssignedToAnInvestigationOfficer:checked").length) == 0) {
			return 'Vui lòng chọn danh sách hồ sơ để phân công.';
		}

		return '';
	}
</script>

<%
	/* ******************************************************************************************************************** */
%>
<%
	/* **************************************************** assign - end ************************************************** */
%>
<%
	/* ******************************************************************************************************************** */
%>

<%
	/* ******************************************************************************************************************** */
%>
<%
	/* ************************************************* unassign - start ************************************************* */
%>
<%
	/* ******************************************************************************************************************** */
%>

<c:url var="urlUnassign"
	value="/servlet/investigationAssignment/unassign" />

<div id="dialog-confirm-unassign"></div>

<script type="text/javascript">
	$(document).ready(function() {
		
		if('${formData.regSiteCode}' != ''){
		    $('#${formData.regSiteCode}').css('background-color', '#7fae46');
		    $('#${formData.regSiteCode} > td').css('color', '#fff');	    	
	    }
		
		$("#selectPlace").change(function() {
			var stage = $(this).find('td:first').text();
	    	$('#ricId').val(stage);	
	    	var rSel = $('#selectPlace').val();
	    	$('#ricSelect').val(rSel);	
			
	    	document.forms["formData"].action = '${investigationJobAssignment1Url}';  
			document.forms["formData"].submit();
			
			/* var sl = $( "#selectPlace" ).val();
			var url = '${getListRic}/' + sl;
			$.ajax({
				url : url,
				type: "POST",
				contentType : 'text/html;charset=UTF-8',
				success : function(data) {
					$("#listRic").empty();
					if(data != 'error'){
						$('#listRic').html(data);
					}		
				}
			}); */
		})
		
	    $("table#tbTenCQ > tbody > tr").click(function(){
	    	//alert($(this).find('td:first').text());
	    	var stage = $(this).find('td:first').text();
	    	$('#ricId').val(stage);	
	    	var rSel = $('#selectPlace').val();
	    	$('#ricSelect').val(rSel);	
			
	    	document.forms["formData"].action = '${investigationJobAssignment1Url}';  
			document.forms["formData"].submit();  
	    });
	    //alert('${formData.regSiteCode}');
	    if('${formData.regSiteCode}' != ''){
		    $('#${formData.regSiteCode}').css('background-color', '#7fae46');
		    $('#${formData.regSiteCode} > td').css('color', '#fff');	    	
	    }
	    var thanhCong = '${thanhcong}';
	    var loiPhanCong = '${loi}';
	    if(thanhCong != ''){
	    	$.notify(thanhCong, {
				globalPosition: 'bottom left',
				className: 'success',
			});
	    }
		if(loiPhanCong != ''){
			$.notify(loiPhanCong, {
				globalPosition: 'bottom left',
				className: 'error',
			});
	    }
		
		
		var style = '${formData.typeInvestigation}';
		if(style == '1'){
			 $("table#tbDanhSachA > tbody > tr").click(function(){
		    	var packageId = $(this).find('td:last').text();
		    	var url = "${loadAssignUrl}";
		    	var id = '#' + packageId;
		    	//alert(id);
		    	//$('#tbDanhSachA > tbody > tr').css('background-color', '#fff');
		    	//$('#tbDanhSachA > tbody > tr > td').css('color', '#333333');
		    	//$('#tbDanhSachA > tbody > tr > td > a').css('color', '#333333');
		    	$('#tbDanhSachA > tbody > tr').removeClass("back-gr");
		    	$(this).addClass("back-gr");
		    	//$(id).css('background-color', '#7fae46');
		    	//$(id + ' > td').css('color', '#fff');	
		    	//$(id + ' > td > a').css('color', '#fff');	
		    	$('#tbDanhSachA input[type="checkbox"]').prop('checked', false);
		    	$('#tbDanhSachA input[type="checkbox"]' + id).prop('checked', true);
		    	
		    	$.ajax({
					type : "POST",
					url : url,
					data: {
						packageNo : packageId
					},
					success: function(data) {
						$('#tbCanBoXL > tbody input[type="checkbox"]').prop('checked', false);
						$('#tbCanBoXL > tbody input[type="checkbox"]').attr("disabled", false);
						if(data.length > 0){
							for(var i = 0; i < data.length; i++){
								var idCech = '#' + data[i] + '_CB';
								$(idCech).prop('checked', true);
								$(idCech).attr("disabled", true);
							}
						}
			        },
			        error: function(e){}
				}); 
		    });		
		}
		
	});
	$("#dialog-confirm-unassign").dialog(
			{
				modal : true,
				autoOpen : false,
				width : 500,
				resizable : true,
				show : {
					effect : "fade",
					duration : 200
				},
				hide : {},
				buttons : {
					Ok : function() {
						$('.currentlyNotAssignedToAnInvestigationOfficer')
								.each(function() {
									$(this).prop('checked', false);
								});
						document.forms["formData"].action = "${urlUnassign}";
						document.forms["formData"].submit();
					},
					Cancel : function() {
						$(this).dialog("close");
					}
				}
			});
</script>

<script type="text/javascript">
	function doUnassign() {

		var messages = validateForUnassignment();
		if (messages != '') {
			//alert(messages);
			$.alert({
				title: 'Thông báo',
				content: "<img style=\"margin-bottom: 5px;\" src=\"/eppcentral/resources/images/warning_1b.png\">" + messages,
			});
			return;
		}

		$("#dialog-confirm-unassign").dialog('option', 'title',
				'Xác nhận chưa phân công');
		$("#dialog-confirm-unassign").html(
				"Tiến hành bỏ phân công việc bạn đã chọn?");
		$("#dialog-confirm-unassign").dialog('open');
	}

	function validateForUnassignment() {

		var numberOfJobsInPage = $
		{
			totalRecords
		}
		;

		if (numberOfJobsInPage == 0) {
			return 'Không có sẵn công việc để hủy bỏ.';
		}

		if ($(".currentlyAssignedToAnInvestigationOfficer:checked").length == 0) {
			return 'Vui lòng chọn các công việc (hiện đang được giao)\n mà bạn muốn hủy chỉ định.';
		}

		return '';
	}
</script>

<%
	/* ******************************************************************************************************************** */
%>
<%
	/* ************************************************** unassign - end ************************************************** */
%>
<%
	/* ******************************************************************************************************************** */
%>


<%
	/* ******************************************************************************************************************** */
%>
<%
	/* ******************************************** unassignAllForUser - start ******************************************** */
%>
<%
	/* ******************************************************************************************************************** */
%>

<c:url var="urlUnassignAllForUser"
	value="/servlet/investigationAssignment/unassignAllForUser" />

<div id="dialog-confirm-unassignAllForUser"></div>

<script type="text/javascript">
	$("#dialog-confirm-unassignAllForUser").dialog({
		modal : true,
		autoOpen : false,
		width : 500,
		resizable : true,
		show : {
			effect : "fade",
			duration : 200
		},
		hide : {},
		buttons : {
			Ok : function() {
				document.forms["formData"].action = "${urlUnassignAllForUser}";
				document.forms["formData"].submit();
			},
			Cancel : function() {
				$(this).dialog("close");
			}
		}
	});
</script>

<script type="text/javascript">
	function doUnassignAllForUser() {

		var messages = validateForUnassignAllForUser();
		if (messages != '') {
			//alert(messages);
			$.alert({
				title: 'Thông báo',
				content: "<img style=\"margin-bottom: 5px;\" src=\"/eppcentral/resources/images/warning_1b.png\">" + messages,
			});
			return;
		}

		$("#dialog-confirm-unassignAllForUser").dialog('option', 'title',
				'Xác nhận chưa chuyển');
		$("#dialog-confirm-unassignAllForUser").html("Tiếp tục bỏ gán tất cả các công việc được gán cho mã người dùng đã nhập?");
		$("#dialog-confirm-unassignAllForUser").dialog('open');
	}

	function validateForUnassignAllForUser() {

		var inp = $(".unassignAllForUserUserIdStyle").val();
		if ($.trim(inp).length == 0) {
			return 'Vui lòng nhập ID hiện tại được gán cho người dùng.';
		}

		return '';
	}
	
</script>

<%
	/* ******************************************************************************************************************** */
%>
<%
	/* ********************************************* unassignAllForUser - end ********************************************* */
%>
<%
	/* ******************************************************************************************************************** */
%>
