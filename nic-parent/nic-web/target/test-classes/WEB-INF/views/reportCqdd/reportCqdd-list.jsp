<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>

<c:url var="passportUrl" value="/servlet/investigationConfirm/passportNoEdit" />
<c:url var="reportCqddUrl" value="/servlet/reportCQDD/reportCqdd" />
<style>
.cls-mg-bot {
	margin-top: 10px;
}
tr {
	height: 35px;
}
.displayTag tr.odd {
	height: 35px;
}
.displayTag tr.even {
	height: 35px;
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
						<div class="new-heading-2">BÁO CÁO GIAO DỊCH(CƠ QUAN ĐẠI DIỆN)</div>	

						<div style="clear: both"></div>

						<div style="min-height: 10px"></div>

						<div style="margin: 2px">
							<div style="margin: 2px">
							<div class="cla-form-bca">
								<div style="margin: 2px">
								<div class="col-md-4 col-sm-4">
									<div class="col-md-5 col-sm-5 cls-mg-bot">
										<div class="cla-font">Mã giao dịch:</div>
									</div>
									<div class="col-md-7 col-sm-7 cls-mg-bot">
										<form:input path="searchTransactionId" type="text" style="width: 100%;"/>
									</div>
									<div class="col-md-5 col-sm-5 cls-mg-bot">
										<div class="cla-font">Loại giao dịch:</div>
									</div>
									<div class="col-md-7 col-sm-7 cls-mg-bot">
										<form:select path="transactionType" id="transactionTypeId"
											name="transactionType" class="transactionTypeId"
											style="font-family: Arial; font-size: 12px;width: 100%;">
											<c:forEach var="entry" items="${transactionType}">
												 <form:option value="${entry.key}" style="margin-right: 5px;" label="${entry.value}" />
											</c:forEach>
										</form:select>
									</div>
									<div class="col-md-5 col-sm-5 cls-mg-bot">
										<div class="cla-font">Loại hộ chiếu:</div>
									</div>
									<div class="col-md-7 col-sm-7 cls-mg-bot">
										<form:select path="passportType" id="passportTypeId"
											name="passportType" class="passportTypeId"
											style="font-family: Arial; font-size: 12px;width: 100%;">
											<c:forEach var="entry" items="${passportType}">
												<form:option value="${entry.key}" style="margin-right: 5px;" label="${entry.value}" />
											</c:forEach>
										</form:select>
									</div>
									
								</div>
								<div class="col-md-4 col-sm-4">
									<div class="col-md-5 col-sm-5 cls-mg-bot">
										<div class="cla-font">Mức độ ưu tiên:</div>
									</div>
									<div class="col-md-7 col-sm-7 cls-mg-bot">
										<form:select path="priority" id="priorityId"
											 name="priority" class="priorityId"
											 style="font-family: Arial; font-size: 12px;width: 100%;">
											 <c:forEach var="entry" items="${priorityCode}">
												 <form:option value="${entry.key}" style="margin-right: 5px;" label="${entry.value}" />
											 </c:forEach>
										</form:select>
									</div>
									<div class="col-md-5 col-sm-5 cls-mg-bot" style="position: relative;">
										<div class="cla-font">Từ ngày:</div>
									</div>
									<div class="col-md-7 col-sm-7 cls-mg-bot">
										<form:input id="createDate" name="createDate" path="createDate" cssClass="defaultText"
										size="12" maxlength="12" readonly="true" style="width: 100%;margin-right: -20px;"/>
									</div>
									<div class="col-md-5 col-sm-5 cls-mg-bot" style="position: relative;">
										<div class="cla-font">Đến ngày:</div>
									</div>
									<div class="col-md-7 col-sm-7 cls-mg-bot">
										<form:input id="issueDate" name="issueDate" path="issueDate" cssClass="defaultText"
										size="12" maxlength="12" readonly="true" style="width: 100%;margin-right: -20px;"/>
									</div>
									
								</div>
								<div class="col-md-4 col-sm-4">
									<div class="col-md-5 col-sm-5 cls-mg-bot">
										<div class="cla-font">Nơi đăng ký:</div>
									</div>
									<div class="col-md-7 col-sm-7 cls-mg-bot">
										<form:select path="regSiteCode" id="regSiteCode"
											name="regSiteCode" class="regSiteCode"
											style="font-family: Arial; font-size: 12px;width: 100%;">
											<c:forEach var="entry" items="${listSiteRepository}">
												<form:option value="${entry.key}" style="margin-right: 5px;" label="${entry.value}" />
											</c:forEach>
										</form:select>
									</div>
									<div class="col-md-5 col-sm-5 cls-mg-bot">
										<div class="cla-font">Trạng thái giao dịch:</div>
									</div>
									<div class="col-md-7 col-sm-7 cls-mg-bot">
										<form:select path="typeInvestigation" id="typeInvestigationId"
										name="typeInvestigation" class="typeInvestigationId"
										style="font-family: Arial; font-size: 12px;width: 100%;">
											<c:forEach var="entry" items="${investigationTypeCode}">
												<form:option value="${entry.key}" style="margin-right: 5px;" label="${entry.value}" />
											</c:forEach>
										</form:select>
									</div>
									<div class="col-md-12">
										<div style="margin-bottom: 10px;margin-top: 10px;">
											<button type="button" onclick="doApplyFilter();" class="btn_small btn-primary btn-search">
										        <span class="glyphicon glyphicon-search"></span> Tìm kiếm
										    </button>
											
										</div>
									</div>
								</div>
							
						

							</div>
						</div>
								
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
								      <th class="th-sm" style="min-width: 130px;">Mã giao dịch
								
								      </th>
								      <th class="th-sm" style="min-width: 140px;">Họ tên
								
								      </th>
								      <th class="th-sm" style="min-width: 120px;">Ngày nộp đơn
								
								      </th>
								      <th class="th-sm" style="min-width: 140px;">Ngày trả kết quả
								
								      </th>
								      <th class="th-sm" style="min-width: 90px;">Ưu tiên
								
								      </th>
								      <th class="th-sm" style="min-width: 120px;">Nơi đăng ký
								
								      </th>
								      <th class="th-sm" style="min-width: 120px;">Loại hộ chiếu
								
								      </th>
								      <th class="th-sm" style="min-width: 120px;">Loại giao dịch
								
								      </th>
								      <th class="th-sm" style="min-width: 90px;">Chi chú
								
								      </th>
								    </tr>
								  </thead>
								  <tbody>
								    <c:forEach items="${jobList}" var="item">
									    <tr>							    									      
									      <td>${item.uploadJobId}</td>
									      <td>${item.transactionId}</td>
									      <td>${item.fullName}</td>	
									      <td>${item.dateOfApplication}</td>
									      <td>${item.estDateOfCollectionString}</td>
									      <td>${item.priorityString}</td>
									      <td>${item.regSiteCode}</td>
									      <td>${item.passportType}</td>
									      <td>${item.jobType}</td>
									      <td><c:url var="noteInvestigationN" value="${item.noteInvestigation}" />
											<a onclick="historyEventDataSmark_clicked('${noteInvestigationN}')">
												<span> 	
													<img src="<c:url value="/resources/images/clipboard_16.png"/>" alt="Xem ghi chú điều tra"  title="Xem ghi chú điều tra" border="0" />
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
								requestURI="/servlet/reportCQDD/reportCqdd">
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
								<display:column title="ID" property="uploadJobId"
									sortable="false">
									
								</display:column>
								<display:column property="transactionId" sortable="false"
									title="Mã giao dịch" maxLength="30" />
								<display:column property="fullName" sortable="false"
									title="Họ tên đầy đủ" maxLength="50" />
								<display:column property="dateOfApplication"
									title="Ngày nộp đơn" sortable="false"
									format="{0,date,dd-MMM-yyyy}">
								</display:column>
								<display:column property="estDateOfCollectionString"
									sortable="false" title="Ngày trả kết quả" maxLength="30" />
								<display:column property="priorityString" sortable="false"
									title="Ưu tiên" maxLength="30" />
								<display:column property="regSiteCode"
									sortable="false" title="Nơi đăng ký hồ sơ" maxLength="64" />
								<display:column property="passportType"
									sortable="false" title="Loại hộ chiếu" maxLength="64" />
								<display:column property="jobType" sortable="false"
									title="Loại giao dịch" maxLength="30" />
								
								<display:column title="Ghi chú" sortProperty="noteInvestigation" sortable="false">
									<c:url var="noteInvestigationN" value="${row.noteInvestigation}" />
									<a onclick="historyEventDataSmark_clicked('${noteInvestigationN}')">
										<span> 	
											<img src="<c:url value="/resources/images/clipboard_16.png"/>" alt="Xem ghi chú điều tra"  title="Xem ghi chú điều tra" border="0" />
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
	
	<div id="infoDialog" style="display: none;">
	</div>
	
	<!--<div class="waitingWhenDoneWaiting" style="display:block; position: fixed; bottom:5px; right:10px; border-radius: 5px; border: 1px solid gray; background-color: rgb(255, 255, 255); z-index: +1000">
			<div style="margin: 10px"> 
				<input type="button" class="btn btn-primary"              onclick="openBoxReject();" name="approve"  value="Tạo danh sách" /> 
				&nbsp; 
			</div>
	</div> -->
	<c:url var="createHandover" value="/servlet/investigationBca/createHandover" />
	<div id="dialog-changePeriodValidate" title="Thay đổi năm hộ chiếu" style="display: none;"> 
			<div class="form-group">		
				<label for="nowPeriod" style="margin-top: 20px;">Mã giao dịch: </label>
				<label  id="transLbl" name="transLbl" style="color: #337ab7;"></label>
				<br />
				<label for="nowPeriod" style="margin-top: 15px;">Số năm hộ chiếu hiện tại: </label>
				<label  id="nowPeriod" name="nowPeriod" style="color: #337ab7;"></label>
				<br />
				<label for="inputPeriod" style="margin-top: 15px;">Số năm thay đổi</label>
				<input class="form-control" id="inputPeriod" name="inputPeriod" />
				<br />
			</div> 
	</div> 


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
				    <!-- <option value="1">Bàn giao sang Bộ Công an</option> -->
				    <option value="2">Bàn giao sang Xét duyệt</option>
				  </select>
				</div>
			</div> 
	</div> 

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
					document.forms["formData"].action = '${reportCqddUrl}';  
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
		document.forms["formData"].action = '<c:url value="/servlet/reportCQDD/applyFilter" />';
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
	
	/*=====================================================================*/
	/*=========== XỬ LÝ THAY ĐỔI SỐ HỘ CHIẾU ==============================*/
	function openChangePassport(code, np){
		  document.getElementById('transLbl').innerHTML = code;
		  document.getElementById('nowPeriod').innerHTML = np;
		  $("#inputPeriod").val(np);
		  $( "#dialog-changePeriodValidate" ).dialog( "open" ); 
	}
	
	$(function() {
		    $("#dialog-changePeriodValidate" ).dialog({
				  modal: true,
			      autoOpen: false,
				  width : 600,
				  height: 290,
				  resizable: false,
			      show: {
			        effect: "fade",
			        duration: 1000
			      },
			      hide: { },
			      buttons: {
			         "Xác nhận": function() {
						var inp = $("#inputPeriod").val();
						var code = document.getElementById('transLbl').innerText;
						if ($.trim(inp).length == 0){
							alert ('Không được để trống năm hộ chiếu');
							return;
						}   
						if (isNaN(inp)) 
						{
						    alert("Giá trị nhập vào phải là số");
						    return ;
						}
						
			            $( this ).dialog( "close" );
			            var contents = {
								   passportNo:  "",
								   passportYear: inp,
								   transactionId: code
						}
			            $.ajax({
					 	      type: "POST",
					 	      url: "${passportUrl}", 
					 	      data: contents,
					 	      dataType: "json",
					 	      contentType: "application/json; charset=utf-8",
					 	      success: function(response) {
					 	    	 		alert('Đã cập nhật thành công!');
				                    	window.location.reload(true);
				                },
				                error: function(e) {
				                    alert('Error: ' + e);
				                } 
					 	});
			         },
			         "Đóng": function() {
			            $( this ).dialog( "close" );
			         }
			      } 
		    });
	  }); 
	/*=====================================================================*/
	/*=========== END -- XỬ LÝ THAY ĐỔI SỐ HỘ CHIẾU ==============================*/
	
	
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
	
	$('#createDate').datepicker().datepicker('setDate', ""); */
	
	$(function() {
		var fromDate = $("#createDate").datepicker({
			buttonImage : "<c:url value="/resources/images/calendar.gif" />",
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
			buttonImage : "<c:url value="/resources/images/calendar.gif" />",
			changeMonth: true,
	        changeYear : true,
	        dateFormat : 'dd-M-yy',
	        numberOfMonths: 1
	    });
	});
</script>
	
	<form:input    path="currentlyAssignedToUserId"                       type="hidden" name="currentlyAssignedToUserId"                       value="" />
	<form:input    path="unassignAllForUserUserId"                        type="hidden" name="unassignAllForUserUserId"                        value="" />
	<form:input    path="assignToId"                        type="hidden" name="assignToId"                        value="" />
</form:form>



