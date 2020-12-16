<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/jquery-confirm/3.3.2/jquery-confirm.min.css">
<script src="https://cdnjs.cloudflare.com/ajax/libs/jquery-confirm/3.3.2/jquery-confirm.min.js"></script>
<c:url var="backBtnUrl" value="/servlet/investigationBca/investigationLoadBcaListConfirm" />
<c:url var="detailUrl" value="/servlet/investigationBca/investigationConfirmBcaDetail" />
<style>
.cls-mg-bot {
	margin-top: 10px;
}
table.displayTag>thead>tr {
	height: 35px;
}
.displayTag tr.odd {
	height: 35px;
}
.displayTag tr.even {
	height: 35px;
}
#ajax-load-qa {
	background: rgba(255, 255, 255, .8)
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
.jconfirm .jconfirm-box div.jconfirm-content-pane .jconfirm-content {
    white-space: pre-wrap;
}
</style>
<c:url var="JobInvestigationPendingBcaUrl" value="/servlet/investigationBca/investigationPendingBcaList" />
<c:url var="JobInvestigationLoadBcaUrl" value="/servlet/investigationBca/investigationLoadBcaList" />
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
<form:form modelAttribute="formData" name="formData" id="formData">
	<div style="padding-left: 20px;padding-right: 20px;background-color: #fff;">
		<div>
			<div>
				<div class="row">
					<div class="ov_hidden">
						<div class="new-heading-2">CHI TIẾT DANH SÁCH ĐÃ XÁC MINH BCA</div>

						<div style="clear: both"></div>

						<div style="min-height: 10px"></div>



						<br />
							<div>
						      	<table id="dtBasicExample" class="table table-bordered table-sm" cellspacing="0" width="100%">
								  <thead>
								    <tr>
								      <th class="th-sm" style="max-width: 50px;">ID
								
								      </th>	
								      <th class="th-sm">Mã giao dịch
								
								      </th>
								      <th class="th-sm">Họ tên
								
								      </th>
								      <th class="th-sm" style="min-width: 140px;">Ngày nộp đơn
								
								      </th>
								      <th class="th-sm" style="min-width: 100px;">Loại hộ chiếu
								
								      </th>
								      <th class="th-sm" style="min-width: 120px;">Loại giao dịch
								
								      </th>
								      <th class="th-sm" style="min-width: 120px;">Xác minh Bca
								      </th>
								      <th class="th-sm" style="min-width: 120px;">Thao tác
								      </th>
								    </tr>
								  </thead>
								  <tbody>
								    <c:forEach items="${jobList}" var="item">
									    <tr>
									      <td><c:url var="jobUrl" value="/servlet/investigationBca/startDetailInvestigationC/${item.uploadJobId}/${listCode}" />
												<a style="color: blue;" href="${jobUrl}"> <c:out value="${item.uploadJobId}" /></a></td>	
									      <td>${item.transactionId}</td>
									      <td>${item.fullName}</td>
									      <td>${item.dateOfApplication}</td>
									      <td>${item.passportType}</td>
									      <td>${item.jobType}</td>
									      <td>${item.validateInfoBca_des}</td>
									      <td>
									      		<button type="button" class="btn btn-sm btn-primary"  onclick="requestToConfirmBCA(${item.uploadJobId}); return false;" name="approve">
													<span class="glyphicon glyphicon-ok"></span> Duyệt
												</button>
												<button type="button" class="btn btn-sm btn-danger"  onclick="openBoxReject(${item.uploadJobId}); return false;" name="reject">
													<span class="glyphicon glyphicon-remove"></span> Từ chối
												</button>
									      </td>	
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
					Không có kết quả nào được tìm thấy.
					<br />
						</c:if>
						<c:if test="${not empty jobList}">
							<display:table cellspacing="1" cellpadding="0" id="row"
								sort="external" partialList="true" size="${totalRecords}"
								export="false" class="displayTag" name="jobList" defaultsort="1"
								defaultorder="ascending" pagesize="${pageSize}"
								requestURI="/servlet/investigationBca/investigationConfirmBcaDetail/${listCode}">								
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
								<display:column title="ID" sortProperty="uploadJobId"
									sortable="false">
									<c:url var="jobUrl" value="/servlet/investigationBca/startDetailInvestigationC/${row.uploadJobId}/${listCode}" />
									<a href="${jobUrl}"> <c:out value="${row.uploadJobId}" /></a>
								</display:column>
								<display:column property="transactionId" sortable="false"
									title="Mã giao dịch" maxLength="30" />
								<display:column property="fullName" sortable="false"
									title="Họ tên đầy đủ" maxLength="50" />
								<display:column property="dateOfApplication"
									title="Ngày nộp đơn" sortable="false"
									format="{0,date,dd-MMM-yyyy}">
								</display:column>
								
									<display:column property="passportType"
									sortable="false" title="Loại hộ chiếu" maxLength="64" />
								<display:column property="jobType" sortable="false"
									title="Loại giao dịch" maxLength="30" />
								<display:column property="validateInfoBca_des"
									sortable="false" title="Xác minh BCA" maxLength="64" />	
								<display:column title="Thao tác" sortable="false">
									<button type="button" class="btn btn-sm btn-primary"  onclick="requestToConfirmBCA(${row.uploadJobId}); return false;" name="approve">
										<span class="glyphicon glyphicon-ok"></span> Duyệt
									</button>
									<button type="button" class="btn btn-sm btn-danger"  onclick="openBoxReject(${row.uploadJobId}); return false;" name="reject">
										<span class="glyphicon glyphicon-remove"></span> Từ chối
									</button>
									
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
	<div class="waitingWhenDoneWaiting" style="display:block; position: fixed; bottom:5px; right:10px; border-radius: 5px; border: 1px solid gray; background-color: rgb(255, 255, 255); z-index: +1000">
		<div style="margin: 10px"> 
			<button type="button" class="btn btn-primary" id="backBtn" name="back">
				<span class="glyphicon glyphicon-backward"></span> Quay lại
			</button>                            
		</div>
	</div>
	<c:url var="rejectUrl_noHit" value="/servlet/investigationBca/reject_noHit_bca" />
	<c:url var="approveUrl_noHit" value="/servlet/investigationBca/approve_conf_bca" />
	<div id="dialog-reject-getRemarks" title="Reject Application - Input Remarks" style="display: none;"> 
			<div style="margin: 2px; text-align:  center">	
				Nhận xét
			</div>
			<input hidden="hidden" name="jobIdinput" id="jobIdinput"/>
			<div>		
				<textarea style="height: 70px; width: 90%; margin-left:  5% " id="jobRejectRemarksHolder" name="jobRejectRemarksHolder" ></textarea>		
			</div> 
	</div> 
	<c:url var="createHandover" value="/servlet/investigationBca/createHandover" />
	
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
///$('#dtBasicExample').DataTable();
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
					document.forms["formData"].action = '${detailUrl}/${listCode}';  
					document.forms["formData"].submit();  
				}
			}
		});	
	function doApplyFilter() {
		document.forms["formData"].action = '<c:url value="/servlet/investigationBca/applyFilterConfirmed" />';
		document.forms["formData"].submit();
	}
	
	function openBoxReject(){
			  $( "#dialog-reject-getRemarks" ).dialog( "open" ); 
		}
		
			$(function() {
			
			    $( "#dialog-reject-getRemarks" ).dialog({
					  modal: true,
				      autoOpen: false,
					  width : 600,
					  height: 300,
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
							
							if ($.trim(inp).length == 0){
								//alert ('Chưa nhập mã danh sách!');
								$.alert({
									title: 'Thông báo',
									content: "<img style=\"margin-bottom: 5px;\" src=\"/eppcentral/resources/images/information_1a.png\">" + " Chưa nhập mã danh sách",
								});
								return;
							}   
							if ($.trim(inp1).length == 0){
								//alert ('Chưa nhập tên danh sách!');
								$.alert({
									title: 'Thông báo',
									content: "<img style=\"margin-bottom: 5px;\" src=\"/eppcentral/resources/images/information_1a.png\">" + " Chưa nhập tên danh sách",
								});
								return;
							}    
				            $( this ).dialog( "close" );
				            requestApprove(inp, inp1);
				         },
				         "Back": function() {
				            $( this ).dialog( "close" );
				         }
				      } 
			    });
		  }); 

		
		function requestApprove(code, name) { 
			$.confirm({
			    title: 'Thông báo',
			    content: "<img style=\"margin-bottom: 5px;\" src=\"/eppcentral/resources/images/tip_1a.png\">" + ' Bạn có muốn tiếp tục thực hiện?',
			    buttons: {
			        "Đồng ý": function () {
			        	document.forms["formData"].currentlyAssignedToUserId.value =  code;
				      	document.forms["formData"].unassignAllForUserUserId.value  =  name;
				 	  	document.forms["formData"].action = '${createHandover}';  
					   	document.forms["formData"].submit();  
			        },
			        "Hủy": function () {
			        	//return false;
			        }			       
			    }
			})
			/*if(confirm("Có chăc chắn muốn thực hiện hành động này?"))
			   {
					document.forms["formData"].currentlyAssignedToUserId.value =  code;
			      	document.forms["formData"].unassignAllForUserUserId.value  =  name;
			 	  	document.forms["formData"].action = '${createHandover}';  
				   	document.forms["formData"].submit();  

			   }
			  else
			    return false;*/
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
		
		
		$('#backBtn').click(function() {  
		    document.forms["formData"].action = "${backBtnUrl}";
		    document.forms["formData"].submit();
		}); 
		
		function requestToConfirmBCA(id) { 
			$.confirm({
			    title: 'Thông báo',
			    content: "<img style=\"margin-bottom: 5px;\" src=\"/eppcentral/resources/images/tip_1a.png\">" + ' Bạn có muốn duyệt bản ghi này?',
			    buttons: {
			        "Đồng ý": function () {
			        	document.forms["formData"].currentlyAssignedToUserId.value =  id;
					 	document.forms["formData"].action = '${approveUrl_noHit}/' + id;  
						document.forms["formData"].submit();   
			        },
			        "Hủy": function () {
			        	//return false;
			        }		       
			    }
			})
			/*if(confirm("Chắc chắn duyệt bản ghi này?"))
			   {
				   document.forms["formData"].currentlyAssignedToUserId.value =  id;
			 	   document.forms["formData"].action = '${approveUrl_noHit}';  
				   document.forms["formData"].submit();  
		
			   }
			  else
			    return false;*/
		}
		
		function openBoxReject(id){
			  $("#jobIdinput").val(id);
			  $( "#dialog-reject-getRemarks" ).dialog( "open" ); 
		}
		  
		$(function() {
			    $( "#dialog-reject-getRemarks" ).dialog({
					  modal: true,
				      autoOpen: false,
					  width : 600,
					  height: 250,
					  resizable: false,
				      show: {
				        effect: "fade",
				        duration: 1000
				      },
				      hide: { },
				      buttons: {
				         "Tiếp tục": function() {
							var inp = $("#jobRejectRemarksHolder").val();
							if ($.trim(inp).length == 0){
								//alert ('Nhập nội dung lý do từ chối!');
								$.alert({
									title: 'Thông báo',
									content: "<img style=\"margin-bottom: 5px;\" src=\"/eppcentral/resources/images/information_1a.png\">" + " Chưa nhập nội dung lý do từ chối",
								});
								return;
							}    
				            $( this ).dialog( "close" );
				            doReject_noHit($("#jobIdinput").val());
				         },
				         "Đóng": function() {
				            $( this ).dialog( "close" );
				         }
				      } 
			    });
		  });
		
		 function doReject_noHit(id) {
			 
			 	document.forms["formData"].currentlyAssignedToUserId.value =  id;
			 	document.forms["formData"].unassignAllForUserUserId.value  =  $('#jobRejectRemarksHolder').val();
			 	document.forms["formData"].action = "${rejectUrl_noHit}"; 
			  	document.forms["formData"].submit();  
		   }
	</script>
	
	<form:input    path="currentlyAssignedToUserId"                       type="hidden" name="currentlyAssignedToUserId"                       value="" />
	<form:input    path="unassignAllForUserUserId"                        type="hidden" name="unassignAllForUserUserId"                        value="" />
	
</form:form>




