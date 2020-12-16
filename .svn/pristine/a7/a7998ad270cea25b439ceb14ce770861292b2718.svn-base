<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>

<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/jquery-confirm/3.3.2/jquery-confirm.min.css">
<script src="https://cdnjs.cloudflare.com/ajax/libs/jquery-confirm/3.3.2/jquery-confirm.min.js"></script>

<c:url var="ldsDetail" value="/servlet/pendingPassportNo/txnDetailTrans" />
<c:url var="txnDetailInitUrl" value="/servlet/investigationConfirm/txnDetailTrans" />
<c:url var="logJobListUrl" value="/servlet/queuesJobListController/listLogJobSchedule" />

<style>
.cls-mg-bot {
	margin-top: 10px;
}
table#row >tr {
	height: 35px;
}
.displayTag tr.odd {
	height: 35px;
}
.displayTag tr.even {
	height: 35px;
}
.jconfirm .jconfirm-box div.jconfirm-content-pane .jconfirm-content {
    white-space: pre-wrap;
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
	<div class="form-desing">
		<div>
			<div>
				<div class="row">
					<div class="ov_hidden">
						<div class="new-heading-2">DANH SÁCH DỮ LIỆU LOG JOB</div>	

						<div style="clear: both"></div>

						<div style="min-height: 10px"></div>

							<div style="border: solid 1px #cccccc; border-radius: 4px; margin: 2px;float:left">
							<div style="margin: 2px">
								<div class="col-md-4 col-sm-4">
									<div class="col-md-5 col-sm-5 cls-mg-bot">
										<div class="cla-font">Mã giao dịch:</div>
									</div>
									<div class="col-md-7 col-sm-7 cls-mg-bot">
										<form:input path="searchTransactionId" type="text"
											style="width: 100%;" />
									</div>

									<div class="col-md-5 col-sm-5 cls-mg-bot">
										<div class="cla-font">Loại giao dịch:</div>
									</div>
									<div class="col-md-7 col-sm-7 cls-mg-bot">
										<form:select path="transactionType" id="transactionTypeId"
											name="transactionType" class="transactionTypeId"
											style="font-family: Arial; font-size: 12px;width: 100%">
											<form:option value="">-- Chọn -- </form:option>
											<form:option value="AUTH">Gửi xác minh</form:option>
											<form:option value="RNO">Lấy số biên nhận</form:option>
											<form:option value="LDS">Kiểm tra đóng gói LDS</form:option>
											<form:option value="SYNC">Đồng bộ hộ chiếu phát hành</form:option>
										</form:select>
									</div>
									<div class="col-md-5 col-sm-5 cls-mg-bot">
										<div class="cla-font">Từ ngày</div>
									</div>
									<div class="col-md-7 col-sm-7 cls-mg-bot" style="position: relative;">
										<form:input id="createDate" name="createDate"
											path="createDate" cssClass="defaultText" size="12"
											maxlength="12" readonly="true"
											style="width: 100%;margin-right: -20px;" />
									</div>
								</div>
								<div class="col-md-4 col-sm-4">
									<div class="col-md-5 col-sm-5 cls-mg-bot">
										<div class="cla-font">Trạng thái</div>
									</div>
									<div class="col-md-7 col-sm-7 cls-mg-bot">
										<form:select path="passportType" id="passportTypeId"
													name="passportType" class="passportTypeId"
													style="font-family: Arial; font-size: 12px;width: 100%">
														<form:option value="">-- Chọn -- </form:option>
														<form:option value="NEW">Mới</form:option>
														<form:option value="PENDING">Đang xử lý</form:option>
														<form:option value="SUCCESS">Thành công</form:option>
														<form:option value="ERROR">Lỗi</form:option>
										</form:select>
									</div>
									<div class="col-md-5 col-sm-5 cls-mg-bot">
										<div class="cla-font">Loại hàng chờ</div>
									</div>
									<div class="col-md-7 col-sm-7 cls-mg-bot">
										<form:select path="regSiteCode" id="regSiteCodeId"
													name="regSiteCode" class="regSiteCodeId"
													style="font-family: Arial; font-size: 12px;width: 100%;">
														<form:option value="">-- Chọn -- </form:option>
														<form:option value="REG">Đăng ký / Thay thế</form:option>
														<form:option value="LOS">Mất / hỏng</form:option>
										</form:select>
									</div>
									<div class="col-md-5 col-sm-5 cls-mg-bot">
										<div class="cla-font">Đến ngày</div>
									</div>
									<div class="col-md-7 col-sm-7 cls-mg-bot" style="position: relative;">
										<form:input id="issueDate" name="issueDate"
											path="issueDate" cssClass="defaultText" size="12"
											maxlength="12" readonly="true"
											style="width: 100%;margin-right: -20px;" />
									</div>
								</div>
								<div class="col-md-4 col-sm-4">
									<div style="margin-bottom: 10px;margin-top: 75px;">
										<button type="button" onclick="doApplyFilter();" class="btn_small btn-primary btn-search">
									        <span class="glyphicon glyphicon-search"></span> Tìm kiếm
									    </button>	
										
									</div>
								</div>
								<div style="clear: both"></div>

							</div>
						</div>

						<div style="clear: both"></div>

						<br />
						<div>
						      	<table id="dtBasicExample" class="table table-bordered table-sm" cellspacing="0" width="100%">
								  <thead>
								    <tr>
								      <th class="th-sm" style="max-width: 50px;">ID
								
								      </th>	
								      <th class="th-sm" style="min-width: 150px;">Mã giao dịch
								
								      </th>
								      <th class="th-sm" style="min-width: 120px;">Trạng thái
								
								      </th>
								      <th class="th-sm" style="min-width: 140px;">Loại hàng chờ
								
								      </th>
								      <th class="th-sm" style="min-width: 150px;">Loại giao dịch
								
								      </th>
								      <th class="th-sm" style="min-width: 150px;">Thời gian thực hiện
								
								      </th>
								      <th class="th-sm" style="min-width: 100px;">Mô tả 
								
								      </th>
								    </tr>
								  </thead>
								  <tbody>
								    <c:forEach items="${jobList}" var="item">
									    <tr>							    									      
									      <td>${item.id}</td>
									      <td>${item.code}</td>
									      <td>${item.status}</td>	
									      <td>${item.typeLogJob}</td>
									      <td>${item.typeTransaction}</td>
									      <td>${item.createDate}</td>
									      <td><c:url var="descriptionN" value="${item.description}" />
												<a onclick="historyEventDataSmark_clicked('${descriptionN}')">
													<span> 	
														<img src="<c:url value="/resources/images/clipboard_16.png"/>" alt="Xem mô tả"  title="Xem mô tả" border="0" />
								  					</span> 
							  					</a></td>							     
									    </tr>
								    </c:forEach>
								  </tbody>
								</table>
								<div class="col-md-5 col-sm-12" style="padding-left: 0px;">
									<div class="dataTables_info">Hiển thị ${startIndex} đến ${endIndex} của ${totalRecord} kết quả</div>
								</div>
								<div class="col-md-7 col-sm-12" style="padding-right: 0px;">
									<ul style="float: right;" class="pagination" id="pagination"></ul>
								</div>			 
								<input type="hidden" name="pageNo" id="pageNo" /> 
						      </div>
						<%--
							int pageSize = 20;
						--%>

						<!--<c:if test="${empty jobList}">
					Không có bản ghi nào được tìm thấy.
					<br />
						</c:if>
						<c:if test="${not empty jobList}">
							<display:table cellspacing="1" cellpadding="0" id="row"
								sort="external" partialList="true" size="${totalRecords}"
								export="false" class="displayTag" name="jobList" defaultsort="1"
								defaultorder="ascending" pagesize="${pageSize}"
								requestURI="/servlet/queuesJobListController/listLogJobSchedule">
								<display:setProperty name="paging.banner.item_name" value='kết quả'></display:setProperty>
								<display:setProperty name="paging.banner.items_name" value='kết quả'></display:setProperty>
								<display:setProperty name="paging.banner.placement" value='bottom'></display:setProperty>
								<display:setProperty name="paging.banner.no_items_found" value='<span class="pagebanner"> Không tìm thấy dữ liệu. </span>'></display:setProperty>
								<display:setProperty name="paging.banner.one_item_found" value='<span class="pagebanner"> Tìm thấy 1 kết quả. </span>'></display:setProperty>
								<display:setProperty name="paging.banner.all_items_found" value='<span class="pagebanner"> {0} {1} tìm thấy, hiển thị tất cả {2}. </span>'></display:setProperty>
								<display:setProperty name="paging.banner.some_items_found" value='<span class="pagebanner"> {0} {1} tìm thấy, hiển thị từ {2} đến {3}. </span>'></display:setProperty>		
								<display:setProperty name="paging.banner.first" value='<span class="pagelinks"> [Đầu tiên/Trước] {0} [ <a href="{3}">Tiếp</a>/ <a href="{4}">Cuối cùng</a>] </span>'></display:setProperty>		
								<display:setProperty name="paging.banner.last" value='<span class="pagelinks">[ <a href="{1}">Đầu tiên</a>/ <a href="{2}">Trước</a>] {0} [Tiếp/Cuối cùng] </span>'></display:setProperty>								
								<display:setProperty name="paging.banner.full" value='	<span class="pagelinks"> [<a href="{1}">Đầu tiên</a>/ <a href="{2}">Trước</a>] {0} [ <a href="{3}">Tiếp</a>/ <a href="{4}">Cuối cùng </a>]</span>'></display:setProperty>															
								<display:column title="ID" property="id"
									sortable="false">
									
								</display:column>
								
								<display:column property="code" sortable="false"
									title="Mã giao dịch" maxLength="50" />
								
								<display:column property="status"
									sortable="false" title="Trạng thái" maxLength="50" />
								<display:column property="typeLogJob"
									sortable="false" title="Loại hàng chờ" maxLength="50" />
								<display:column property="typeTransaction" sortable="false"
									title="Loại giao dịch" maxLength="30" />
								<display:column property="createDate"
									title="Thời gian thực hiện" sortable="false">
								</display:column>
								<display:column title="Mô tả thêm" sortProperty="description" sortable="false">
									<c:url var="descriptionN" value="${row.description}" />
									<a onclick="historyEventDataSmark_clicked('${descriptionN}')">
										<span> 	
											<img src="<c:url value="/resources/images/clipboard_16.png"/>" alt="Xem mô tả"  title="Xem mô tả" border="0" />
					  					</span> 
				  					</a>
								</display:column>
								
							</display:table>
						</c:if>-->



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
	
	<div id="dialog-approve"></div>
	<div id="dialog-approve-lds"></div>
	<div id="infoDialog" style="display: none;">
	</div>
	
<!-- 	<div class="waitingWhenDoneWaiting" style="display:block; position: fixed; bottom:5px; right:10px; border-radius: 5px; border: 1px solid gray; background-color: rgb(255, 255, 255); z-index: +1000">
			<div style="margin: 10px"> 
				<input type="button" class="btn btn-primary"              onclick="openBoxReject();" name="approve"  value="Tạo danh sách" /> 
				&nbsp; 
			</div>
	</div> -->

	<div id="dialog-reject-getRemarks" title="Tạo danh sách bàn giao" style="display: none;"> 
			<div class="form-group">		
				<label for="codeHandover">Mã danh sách bàn giao</label>
				<input class="form-control"  id="codeHandover" name="codeHandover" />	
				<br />
				<label for="nameHandover">Tên danh sách bàn giao</label>
				<input class="form-control" id="nameHandover" name="nameHandover" />
				<br />
				<div class="form-group">
				  <label for="typeHandover">Loại danh sách:</label>
				  <select class="form-control" id="typeHandover" name="typeHandover">
				    <option value="1">Bàn giao sang Bộ Công an</option>
				    <option value="2">Bàn giao cho Xét duyệt</option>
				  </select>
				</div>
			</div> 
	</div> 

	<c:url var="createHandover" value="/servlet/listHandover/createHandover" />
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
	
//$('#dtBasicExample').DataTable();
//$('.dataTables_length').addClass('bs-select');
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
					document.forms["formData"].action = '${logJobListUrl}';  
					document.forms["formData"].submit();  
				}
			}
		});	
	function historyEventDataSmark_clicked(itemTriggered){	 
		//info = info.replace(/\n/g,"<br/>");
		$("#infoDialog").dialog('option', 'title', "Log Data");
		$("#infoDialog").text(itemTriggered);
		$("#infoDialog").dialog('open');
	}

	$(function(){
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


	
	function openBoxReject(){
		  $( "#dialog-reject-getRemarks" ).dialog( "open" ); 
	}
	
		$(function() {
		    $( "#dialog-reject-getRemarks" ).dialog({
				  modal: true,
			      autoOpen: false,
				  width : 600,
				  height: 350,
				  resizable: false,
			      show: {
			        effect: "fade",
			        duration: 1000
			      },
			      hide: { },
			      buttons: {
			         "Continue": function() {
						var inp = $("#codeHandover").val();
						var inp1 = $("#nameHandover").val();
						var inp2 = $("#typeHandover").val();
						
						if ($.trim(inp).length == 0){
							alert ('Chưa nhập mã danh sách!');
							return;
						}   
						if ($.trim(inp1).length == 0){
							alert ('Chưa nhập tên danh sách!');
							return;
						}    
			            $( this ).dialog( "close" );
			            requestApprove(inp, inp1, inp2);
			         },
			         "Back": function() {
			            $( this ).dialog( "close" );
			         }
			      } 
		    });
	  }); 

	function doApplyFilter() {
		document.forms["formData"].action = '<c:url value="/servlet/queuesJobListController/applyFilter" />';
		document.forms["formData"].submit();
	}
	
	function requestApprove(code, name, type) { 
		if(confirm("Có chăc chắn muốn thực hiện hành động này?"))
		   {
				document.forms["formData"].currentlyAssignedToUserId.value =  code;
		      	document.forms["formData"].unassignAllForUserUserId.value  =  name;
		      	document.forms["formData"].assignToId.value  =  type;
		 	  	document.forms["formData"].action = '${createHandover}';  
			   	document.forms["formData"].submit();  

		   }
		  else
		    return false;
	}
	
	/* $("#createDate").datepicker({
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
	
	$('#createDate').datepicker().datepicker('setDate', "");
	
	$("#issueDate").datepicker({
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
	
	$('#issueDate').datepicker().datepicker('setDate', ""); */
	
	$(function() {
		var fromDate = $("#createDate").datepicker({
			buttonImage : "<c:url value='/resources/images/calendar.gif' />",
	        changeMonth: true,
	        changeYear : true,
	        numberOfMonths: 1,
	        dateFormat : 'dd-M-yy',
	        onSelect: function(selectedDate) {
	            var instance = $(this).data("datepicker");
	            var date = $.datepicker.parseDate(instance.settings.dateFormat || $.datepicker._defaults.dateFormat, selectedDate, instance.settings);
	            date.setDate(date.getDate()+1);
	            toDate.datepicker("option", "minDate", date);
	    	}
		});
	    
	    var toDate = $("#issueDate").datepicker({
			buttonImage : "<c:url value='/resources/images/calendar.gif' />",
			changeMonth: true,
	        changeYear : true,
	        dateFormat : 'dd-M-yy',
	        numberOfMonths: 1
	    });
	});
	
	//TRUNG THÊM GỌI THÔNG TIN CHI TIẾT BẢN GHI
	function callDialog(txnId, jobId_) {
		//var txnId =document.getElementById('transId_').value;
		/* var txnId = '${nicData.transactionId}'; */
		$.ajax({
			type : "GET",
			url : "${txnDetailInitUrl}/" + txnId + "/" + jobId_,
			success : function(data) {
				$('.modal').show();
				var titleName = "Thông tin chi tiết bản ghi";
				$("#dialog-approve").dialog('option', 'title', titleName);
				$("#dialog-approve").dialog("option", "width", 1200);
				$("#dialog-approve").dialog("option", "height", 600);
				$("#dialog-approve").dialog("option", "maxHeight", 600);
				$("#dialog-approve").dialog("option", "resizable", false);
				$("#dialog-approve").dialog('open');
				$("#dialog-approve").html(data);
				$('.modal').hide();
			},
			error: $('.modal').hide()
		});
	}
	
	$("#dialog-approve").dialog({
		autoOpen : false,
		width : 960,
		height : 480,
		modal : true,
		bgiframe : true,
		cache : false,
		closeOnEscape : false
	});
	
	
	//TRUNG THÊM GỌI THÔNG TIN CHI TIẾT BẢN GHI LDS
	function callDialogLDS(txnId) {
		//var txnId =document.getElementById('transId_').value;
		/* var txnId = '${nicData.transactionId}'; */
		$.ajax({
			type : "GET",
			url : "${ldsDetail}/" + txnId,
			success : function(data) {
				$('.modal').show();
				var titleName = "Thông tin chi tiết bản ghi";
				$("#dialog-approve-lds").dialog('option', 'title', titleName);
				$("#dialog-approve-lds").dialog("option", "width", 1200);
				$("#dialog-approve-lds").dialog("option", "height", 600);
				$("#dialog-approve-lds").dialog("option", "maxHeight", 600);
				$("#dialog-approve-lds").dialog("option", "resizable", false);
				$("#dialog-approve-lds").dialog('open');
				$("#dialog-approve-lds").html(data);
				$('.modal').hide();
			},
			error: $('.modal').hide()
		});
	}
	
	$("#dialog-approve-lds").dialog({
		autoOpen : false,
		width : 960,
		height : 480,
		modal : true,
		bgiframe : true,
		cache : false,
		closeOnEscape : false
	});
</script>
	
	<form:input    path="currentlyAssignedToUserId"                       type="hidden" name="currentlyAssignedToUserId"                       value="" />
	<form:input    path="unassignAllForUserUserId"                        type="hidden" name="unassignAllForUserUserId"                        value="" />
	<form:input    path="assignToId"                        type="hidden" name="assignToId"                        value="" />
</form:form>



