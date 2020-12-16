<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>

<c:url var="ldsDetail" value="/servlet/pendingPassportNo/txnDetailTrans" />
<c:url var="txnDetailInitUrl" value="/servlet/investigationConfirm/txnDetailTrans" />
<c:url var="queuesJobListUrl" value="/servlet/queuesJobListController/queuesJobList" />
<style>

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
						<div class="new-heading-2">DANH SÁCH HÀNG ĐỢI</div>	

						<div style="clear: both"></div>

						<div style="min-height: 10px"></div>

						<br />
						<fieldset>
							<legend>Danh sách hàng đợi</legend>
				
						<div style="height: 400px;">
						      	<table id="dtBasicExample" class="table table-bordered table-sm" cellspacing="0" width="100%">
								  <thead>
								    <tr>
								      <th class="th-sm">ID
								
								      </th>	
								      <th class="th-sm">Mã giao dịch
								
								      </th>
								      <th class="th-sm">Họ tên
								
								      </th>
								      <th class="th-sm">Loại hàng chờ
								
								      </th>
								      <th class="th-sm">Loại giao dịch
								
								      </th>
								      <th class="th-sm">Mô tả thêm
								
								      </th>
								    </tr>
								  </thead>
								  <tbody>
								    <c:forEach items="${jobList}" var="item">
									    <tr>							    									      
									      <td>${item.id}</td>
									      <td>
									      		<c:url var="jobUrl" value="/servlet/queuesJobListController/startDetailQueuesJobList/${item.code}" />
												<a style="color: blue;" href="${jobUrl}"> <c:out value="${item.code}" /></a>
									      </td>
									      <td>${item.name}</td>	
									      <td>${item.typeLogJob}</td>
									      <td>${item.typeTransaction}</td>
									      <td>${item.description}</td>							     
									    </tr>
								    </c:forEach>
								  </tbody>
								</table>
								<c:if test="${empty jobList}">
								  
								   <tbody class="e-not-found ng-scope"><tr>
								  <td colspan="10" style="height: 362px">
								  <div class="e-not-found-center"><div class="e-not-found-title">Không tìm thấy kết quả</div><div class="e-not-found-title-child">Hãy thử tìm kiếm hoặc sử dụng bộ lọc khác</div></div>
								  </td></tr></tbody>
								  
								
								</c:if>
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
					document.forms["formData"].action = '${queuesJobListUrl}';  
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
		document.forms["formData"].action = '<c:url value="/servlet/pendingPassportNo/applyFilterPendingPassportNo" />';
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
	
	$("#createDate").datepicker({
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



