<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>
<c:url var="searchById" value="/servlet/listLogWs/getLogWs" />
<c:url var="logWsSearchList" value="/servlet/listLogWs/logWsSearchList" />
<c:url var="init" value="/servlet/listLogWs/init" />
<style>
.cls-mg-bot {
	margin-top: 10px;
}

.displayTag tr.odd {
	height: 35px;
}

.displayTag tr.even {
	height: 35px;
}

.modal {
	display: none;
	position: fixed;
	z-index: 1000;
	top: 0;
	left: 0;
	height: 100%;
	width: 100%;
	background: rgba(255, 255, 255, .6)
		url('<c:url value="/resources/images/e921f-ajax-loader.gif" />') 50%
		50% no-repeat;
}
/* When the body has the loading class, we turn
   the scrollbar off with overflow:hidden */
body.loading {
	overflow: hidden;
}

/* Anytime the body has the loading class, our
   modal element will be visible */
body.loading .modal {
	display: block;
}
</style>

<form:form modelAttribute="listLogWsFrm" id="listLogWsFrm" action="" method="post">
	<div class="form-desing">
		<div>
			<div>
				<div class="row">
					<div class="ov_hidden">
						<div class="new-heading-2">TRA CỨU NHẬT KÝ ĐỒNG BỘ</div>
						<div
							style="border: solid 1px #cccc; border-radius: 4px; margin: 2px; min-width: 100%; float: left">
							<div class="col-md-12 col-sm-12">
								<div class="col-md-4 col-sm-4">
									<div class="col-md-5 col-sm-5 cls-mg-bot">
										<div class="cla-font">Mã định danh:</div>
									</div>
									<div class="col-md-7 col-sm-7 cls-mg-bot">
										<form:input path="keyId" id="keyId" cssClass="defaultText"
											size="30" maxlength="30" style="width: 100%;" />
									</div>
									<div class="col-md-5 col-sm-5 cls-mg-bot">
										<div class="cla-font">URL request:</div>
									</div>
									<div class="col-md-7 col-sm-7 cls-mg-bot">
										<form:select style="width: 100%;" path="urlRequest"
											id="urlRequest" name="urlRequest">
											<form:option value="">-- Chọn --</form:option>
											<form:option value="uploadTransaction">ĐỒNG BỘ DANH SÁCH HỒ SƠ</form:option>
											<form:option value="/uploadHandoverA">ĐỒNG BỘ DANH SÁCH A</form:option>
											<form:option value="/uploadHandoverB">ĐỒNG BỘ DANH SÁCH B</form:option>
											<form:option value="/updateHandoverC">ĐỒNG BỘ DANH SÁCH C</form:option>
											<form:option value="updateTransaction">CẬP NHẬT DANH SÁCH HỒ SƠ</form:option>
											<form:option value="dowloadTransaction">TRẢ DANH SÁCH HỒ SƠ</form:option>
											<form:option value="dowloadHandoverA">TRẢ DANH SÁCH HỒ SƠ A</form:option>
											<form:option value="dowloadHandoverB">TRẢ DANH SÁCH HỒ SƠ B</form:option>
											<form:option value="dowloadHandoverC">TRẢ DANH SÁCH HỒ SƠ C</form:option>
										</form:select>
									</div>
								</div>
								<div class="col-md-4 col-sm-4">
									<div class="col-md-5 col-sm-5 cls-mg-bot">
										<div class="cla-font">Kiểm định từ ngày:</div>
									</div>
									<div class="col-md-7 col-sm-7 cls-mg-bot"
										style="position: relative;">
										<form:input type="text" id="dateFrom" name="dateFrom"
											path="logWsDateFrom" placeholder="YYYYMMDD"
											cssClass="defaultText" size="12" maxlength="12"
											style="width: 100%;margin-right: -20px;" />
									</div>
									<div class="col-md-5 col-sm-5 cls-mg-bot">
										<div class="cla-font">Kiểm định đến ngày:</div>
									</div>
									<div class="col-md-7 col-sm-7 cls-mg-bot"
										style="position: relative;">
										<form:input type="text" id="dateTo" name="dateTo"
											path="logWsDateTo" placeholder="YYYYMMDD"
											cssClass="defaultText" size="12" maxlength="12"
											style="width: 100%;margin-right: -20px;" />
									</div>
									<div class="col-md-5 col-sm-5 cls-mg-bot">
										<div class="cla-font">Loại:</div>
									</div>
									<div class="col-md-7 col-sm-7 cls-mg-bot">
										<form:select style="width: 100%;" path="type" id="type"
											name="type">
											<form:option value="">-- Chọn --</form:option>
											<form:option value="DSA">DS_A</form:option>
											<form:option value="DSB">DS_B</form:option>
											<form:option value="DSC">DS_C</form:option>
											<form:option value="HS">HS</form:option>
										</form:select>
									</div>
								</div>
								<div class="col-md-4 col-sm-4"
									style="padding-top: 20px; margin-bottom: 20px; float: inline-end;">
									<button type="button" id="search_btn_data"
										class="btn_small btn-success btn-search"
										style="display: flow-root;">
										<span class="glyphicon glyphicon-search"></span> Tìm kiếm
									</button>
									<button type="button" id="reset_btn_data"
										class="btn_small btn-info btn-reset"
										style="width: 80px; height: 30px; margin-top: 10px; margin-bottom: 20px;">
										<span class="glyphicon glyphicon-refresh"></span> Reset
									</button>
								</div>
							</div>
							<!--<div class="col-md-12" style="text-align: center;margin-top: 20px;margin-bottom: 20px;">
				<button type="button" id="search_btn_data" class="btn_small btn-primary btn-search">
					<span class="glyphicon glyphicon-search"></span> Tìm kiếm
				</button>
				<button type="button" id="reset_btn" class="btn_small btn-primary btn-search">
					<span class="glyphicon glyphicon-refresh"></span> Làm mới
				</button>
			</div>-->

						</div>
						<div>
							<table id="dtBasicExample" class="table table-bordered table-sm"
								cellspacing="0" width="100%">
								<thead>
									<tr>
										<th class="th-sm" style="max-width: 100px;">Mã định danh

										</th>
										<th class="th-sm" style="min-width: 10px;">Loại</th>
										<th class="th-sm" style="min-width: 140px;">Chức năng</th>
										<!--     <th class="th-sm" style="min-width: 140px;">Data_Request
								
								      </th>
								      <th class="th-sm" style="min-width: 150px;">Data_Response
								
								      </th>-->
										<th class="th-sm" style="min-width: 150px;">Ngày tạo</th>
										<th class="th-sm" style="min-width: 100px;">Thông tin lỗi

										</th>
										<th class="th-sm" style="min-width: 70px;">Chi tiết</th>
									</tr>
								</thead>
								<tbody>
									<c:forEach items="${list}" var="item">
										<tr>
											<td>${item.keyId}</td>
											<td>${item.type}</td>
											<td>${item.urlRequest}</td>
											<!--     <td>${item.dataRequest}</td> 
								           <td>${item.dataResponse}</td>
								-->
											<td>${item.createdDate}</td>
											<td>${item.messageErrorLog}</td>
											<td style="text-align: center; vertical-align: middle;"><button
													class="btn btn-success" type="button" onclick="getModal('${item.id}')">
													<span class="glyphicon glyphicon-eye-open"></span>
												</button></td>
										</tr>
									</c:forEach>
								</tbody>
							</table>
							
							<div class="col-md-5 col-sm-12" style="padding-left: 0px;">
								<div class="dataTables_info">Hiển thị ${startIndex} đến
									${endIndex} của ${totalRecord} kết quả</div>
							</div>
							<div class="col-md-7 col-sm-12" style="padding-right: 0px;">
								<ul style="float: right;" class="pagination" id="pagination"></ul>
							</div>
							<input type="hidden" name="pageNo" id="pageNo" />
						</div>
						<!--<div class="col-md-12">
				<div id="searchResult">
					<table id="searchResultFlexGrid"></table>
				</div>
			</div>-->
					</div>
				</div>
			</div>
		</div>
	</div>
	<!--	<input type="hidden" id="jid" name="jid" />
	<input type="hidden" id="txnId" name="txnId" />
	<input type="hidden" id="searchUrl" name="searchUrl"
		value="${auditSearchUrl}" />  -->

</form:form>

<div id="msgDialog" title="Cảnh báo">
	<div class="isa_info" align="center">
		<span style="font-size: 40" id="msgDialogSpan"></span>
	</div>
</div>
<div id="dialog-approve"></div>

<div class="modal">
	<!-- Place at bottom of page -->
</div>
<div class="modal fade" id="myModal" role="dialog" style="z-index:10000">
	<div class="modal-dialog modal-lg">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal">&times;</button>
				<h4 class="modal-title">Chi tiết</h4>
			</div>
			<div class="modal-body">
				   <div>
				       <div class="col-md-12 col-sm-4" style="text-align: inherit">
						<h3 style="text-align: center">Data Request</h3>
						<textarea id="dataRequest" name="dataRequest" rows="15" style="width: 100%; margin-bottom: 20px;" readonly="readonly"></textarea>
					</div>
				   </div>
					
					
					<div>
					    <div clas="col-md-12 col-sm-4" style="text-align: inherit">
						<h3 style="text-align: center">Data Reponse</h3>
						<textarea id="dataResponse" name="dataResponse" rows="10" readonly="readonly"
							style="width: 100%;width: 1020px;margin-left: 15px"></textarea>

					    </div>
					</div>
					
				
			</div>
			<div class="modal-footer">
				<button type="button" data-dismiss="modal" style="float:right; margin-right:50px;height: 35px;margin-bottom:auto; padding: 5px;margin-bottom: 3px;
                   margin-top: 3px; "  class="btn btn-warning"><span class="glyphicon glyphicon-remove" ></span> Đóng</button>
			</div>
		</div>
	</div>
</div>
<script type="text/javascript">
function getModal(id){
	 $.ajax({
		       url:"${searchById}/" + id,
		       method : 'GET',
		       dataType: 'json',
		       success: function (data) {
	                    $('#modal-body').html("");
	                    $('#dataRequest').html(data.dataRequest);
	                    $('#dataResponse').html(data.dataResponse);
	                //    tongtien=0;
	                   // document.getElementById('gia').value=tongtien;
	                },
	                error: function (error) {
	                    alert(error);
	                }
		    })
	   $('#myModal').modal('show');
       }
	function validSearch() {
		if ($("#keyId").val() == "" && $("#urlRequest").val() == ""
				&& $("#type").val() == "" && $("#dateFrom").val() == ""
				&& $("#dateTo").val() == "") {
			return false
		}
		return true;
	}
	var reload = "0";
	$(function() {
		//$('#dtBasicExample').DataTable();
		//$('.dataTables_length').addClass('bs-select');
		var totalPages = ${totalPage};
		var currentPage = ${pageNo};
		var pageSize = ${pageSize};
		window.pagObj = $('#pagination')
				.twbsPagination(
						{
							totalPages : totalPages,
							visiblePages : pageSize,
							startPage : currentPage,
							next : '<span class=\"glyphicon glyphicon-forward fix-paging\"></span>',
							prev : '<span class=\"glyphicon glyphicon-backward fix-paging\"></span>',
							last : '<span class=\"glyphicon glyphicon-step-forward fix-paging\"></span>',
							first : '<span class=\"glyphicon glyphicon-step-backward fix-paging\"></span>',
							onPageClick : function(event, page) {
								if (currentPage != page) {
									//if(validSearch()!=false){
									$('#pageNo').val(page);
									document.forms["listLogWsFrm"].action = '${logWsSearchList}';
									document.forms["listLogWsFrm"].submit();
									//	}else{
									//	alert("yêu cầu điền thông tin tối thiểu một trường");
									//		document.forms["listLogWsFrm"].action = '${init}';  
									//		document.forms["listLogWsFrm"].submit();  
									//	}

								}
							}
						});
		$(document).on("click", "#search_btn", function() {
			//transQueryList(); 
		});
       
		//tam dong
		//	$("#msgDialog").dialog({
		//	width : 1200, 
		//	resizable : false,
		//	modal : true,
		//	autoOpen : false,
		//	show : {
		//		effect : "blind",
		//		duration : 1000
		//	},
		//	hide : {
		//effect : "explode",
		//		duration : 100
		//	},
		//	close : function() {
		//		$(this).dialog("close"); 
		//	},
		//	buttons : {
		//		"Đóng" : function() {
		//					$(this).dialog("close");
		//			 }
		//		}
		//	});
		//tam dong
		

	});
     
	$("#reset_btn_data").click(function() {
		//$("#searchResult").hide(); 
		document.forms["listLogWsFrm"].action = '${init}';
		document.forms["listLogWsFrm"].submit();
		$("#keyId").val("");
		$("#urlRequest").val("");
		$("#dateFrom").val("");
		//$("#dateFrom").val("");
		$("#dateTo").val("");
		$("#type").val("");
		//$("#searchResultFlexGrid").hide();
	});
	
	
	$('#search_btn_data').click(function() {
		//auditEnquiryList();
		if (validSearch() == false) {
			alert('yêu cầu điền thông tin tối thiểu một trường')
		} else {
			var url = '${logWsSearchList}';
			//var orig = $("#searchJobsForm").serialize();
			//var withoutEmpties = orig.replace(/[^&]+=\.?(?:&|$)/g, '');
			//var test = url + '?' + withoutEmpties;  
			document.forms["listLogWsFrm"].action = url;
			document.forms["listLogWsFrm"].submit();
		}

		/*$.ajax({
			url : test,
			cache: false,
			type: "POST",
			success : function(data) {
				if(data.length > 0){
					var tb = $('#dtBasicExample').DataTable();	
					tb.clear();
		        	for(var i = 0; data.length; i++){
		        		tb.row.add([
		        		             data[i].auditDateStr,
		        		             data[i].functionName,
		        		             data[i].wkstnId,
		        		             data[i].userId,
		        		             data[i].serverId,
		        		             data[i].sessionId,
		        		             data[i].errorFlag
		        		]).draw(false);
		        	}
					
				}else{				
					$.alert({
						title: 'Thông báo',
						content: "<img style=\"margin-bottom: 5px;\" src=\"/eppcentral/resources/images/warning_1b.png\">" + ' Không tìm thấy dữ liệu',
						 buttons: {   
		        		        "Đóng": function () {}
		        		    }
					});
				}
			}
		});*/
	});

	$("#dateFrom").datepicker({
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

	$("#dateTo").datepicker({
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

	//$('#dateFrom').datepicker().datepicker('setDate',new Date());
	//$('#dateTo').datepicker().datepicker('setDate',new Date());

	//Audit Enquiry Flexigrid

	/*function auditEnquiryList() { 
	  //$('.modal').show();
	  var url = '${auditSearchUrl}';
	  var orig = $("#searchJobsForm").serialize();
	  var withoutEmpties = orig.replace(/[^&]+=\.?(?:&|$)/g, '');
	  var test = url + '?' + withoutEmpties;  
	  if(reload=="0"){
	   reload="1";
	   $("#searchResultFlexGrid").flexigrid({ 
	    url:test,
	    dataType : 'json',
	    colModel : [
					{display : 'Ngày yêu cầu', name : 'auditDateStr', width : 120, sortable : true, align : 'left'}, 
					{display : 'Chức năng', name : 'functionName', width : 140, align : 'left' },
					{display : 'Mã máy trạm', name : 'wkstnId', width : 160, sortable : true, align : 'left' }, 
					{display : 'Tên người dùng', name : 'userId', width : 140, sortable : true, align : 'left' }, 
					{display : 'Mã máy chủ', name : 'serverId', width : 160, sortable : true, align : 'left' },
					{display : 'Mã phiên', name : 'sessionId', width : 280, sortable : true, align : 'left' },
					{display : 'Trạng thái', name : 'errorFlag', width : 140, sortable : true, align : 'left', render: renderStatus },
					{display : '', name : 'fixBootstrap', width : 10, sortable : true, align : 'left'}
				], 
	    title : 'Kiểm tra yêu cầu - Nhật ký truy cập',
	    usepager : true,
	    sortname : 'auditDate',
	    sortorder : 'desc',
	    useRp : true,
	    showTableToggleBtn : true,   
	    height : 280,
	    singleSelect : true,
	    nowrap : false,
	    resizable : false
	   });
	  }else{
	   $("#searchResultFlexGrid").flexOptions({ 
	    url:test, newp:1 
	   }).flexReload()
	  }
	  
	 }*/

	//});
	//dong
//	function renderStatus(content, currentRow) {
//		if (content == 'N') {
//			return "<font color='green'><b>Thành công</b></font>";
//		} else {
//			return "<font color='red'><b><b>Lỗi</b></font>";
//		}
//	}
</script>
