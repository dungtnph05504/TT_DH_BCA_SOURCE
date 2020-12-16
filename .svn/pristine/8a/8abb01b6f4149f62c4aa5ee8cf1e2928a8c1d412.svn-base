<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/jquery-confirm/3.3.2/jquery-confirm.min.css">
<script src="https://cdnjs.cloudflare.com/ajax/libs/jquery-confirm/3.3.2/jquery-confirm.min.js"></script>
<c:url var="JobInvestigationConfirmedBcaUrl" value="/servlet/investigationBca/investigationLoadBcaListConfirm" />
<style>
.cls-mg-bot {
	margin-top: 10px;
	margin-bottom: 10px;
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
<c:url var="JobInvestigationConfirmedBcaUrl" value="/servlet/investigationBca/investigationConfirmedBcaList" />
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
<form:form modelAttribute="formData" name="formData">
	<div style="padding-left: 20px;padding-right: 20px;background-color: #fff;">
		<div>
			<div>
				<div class="row">
					<div class="ov_hidden"> 
						<!--<div id="heading">
							<div id="heading_left" style="font-weight: bold; background:gray; cursor:pointer;margin-right:5px" align="justify">
								<a style="color:#fff;text-decoration:none" onclick=jobInvestigationPendingBcaFunction();>Danh sách Chờ xác minh từ BCA </a>
							</div>
							<div id="heading_left" style="font-weight: bold; background:gray; cursor:pointer;text-decoration:none;color:#fff; margin-right: 5px;" align="justify">
								<a style="color:#fff;text-decoration:none" onclick=jobInvestigationLoadBcaFunction();>Danh sách đã gửi BCA</a>
							</div>
							<div id="heading_left" style="font-weight: bold;" align="justify">
								Danh sách Đã xác minh từ BCA 
							</div>
						</div>-->
						<div class="new-heading-3 col-md-4"><a onclick=jobInvestigationPendingBcaFunction();>DANH SÁCH CHỜ XÁC MINH BCA</a></div>
						<div class="new-heading-3 col-md-4"><a onclick=jobInvestigationLoadBcaFunction();>DANH SÁCH GỬI BCA</a></div>
						<div class="new-heading-2 col-md-4">DANH SÁCH ĐÃ XÁC MINH BCA</div>
						<div style="clear: both"></div>

						<div style="min-height: 10px"></div>

						<div style="margin: 2px">
							<div class="cla-form-bca">
								<div style="margin: 2px">
								<div class="col-md-4 col-sm-4">
									<div class="col-md-5 col-sm-5 cls-mg-bot">
										<div class="cla-font">Mã danh sách:</div>
									</div>
									<div class="col-md-7 col-sm-7 cls-mg-bot">
										<form:input path="packageCode" type="text" style="width: 100%;"/>
									</div>
								</div>
								<div class="col-md-4 col-sm-4">								
									<div class="col-md-5 col-sm-5 cls-mg-bot" style="position: relative;">
										<div class="cla-font">Ngày tạo danh sách:</div>
									</div>
									<div class="col-md-7 col-sm-7 cls-mg-bot">
										<form:input id="createDate" name="createDate" path="createDate" cssClass="defaultText"
										size="12" maxlength="12" readonly="true" style="width: 100%;margin-right: -20px;"/>
									</div>								
								</div>							
								<div class="col-md-4">
									<div style="margin-bottom: 10px;margin-top: 10px;">
										<button type="button" onclick="doApplyFilter();" class="btn_small btn-primary btn-search">
									        <span class="glyphicon glyphicon-search"></span> Tìm kiếm
									    </button>										
									</div>
								</div>
								

							</div>
						</div>				
						</div>
						<div id="ajax-load-qa"></div>

						<div style="clear: both"></div>

						<br />
							<div>
						      	<table id="dtBasicExample" class="table table-bordered table-sm" cellspacing="0" width="100%">
								  <thead>
								    <tr>
								      <th class="th-sm" style="max-width: 150px;">Mã danh sách
								
								      </th>	
								      <th class="th-sm" style="min-width: 140px;">Tên danh sách
								
								      </th>
								      <th class="th-sm" style="min-width: 140px;">Số lượng hồ sơ
								
								      </th>
								      <th class="th-sm" style="min-width: 140px;">Người tạo danh sách
								
								      </th>
								      <th class="th-sm" style="min-width: 140px;">Ngày tạo danh sách
								
								      </th>								 
								  </thead>
								  <tbody>
								    <c:forEach items="${jobList}" var="item">
									    <tr>
									      <td><c:url var="jobUrl" value="/servlet/investigationBca/investigationConfirmBcaDetail/${item.listCode}" />
											<a style="color: blue;" href="${jobUrl}"> <c:out value="${item.listCode}" /></a></td>	
									      <td>${item.listName}</td>
									      <td>${item.numberTran}</td>
									      <td>${item.createBy}</td>
									      <td>${item.createDate}</td>
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
								requestURI="/servlet/investigationBca/investigationLoadBcaListConfirm">
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
								<display:column title="Mã danh sách" sortProperty="listCode"
									sortable="false">
									<c:url var="jobUrl" value="/servlet/investigationBca/investigationConfirmBcaDetail/${row.listCode}" />
									<a href="${jobUrl}"> <c:out value="${row.listCode}" /></a>
								</display:column>
								<display:column property="listName" sortable="false"
									title="Tên danh sách" maxLength="30" />
								<display:column property="numberTran" sortable="false"
									title="Số lượng hồ sơ" maxLength="50" />
								<display:column property="createBy" sortable="false"
									title="Người tạo danh sách" maxLength="50" />	
								<display:column property="createDate"
									title="Ngày tạo danh sách" sortable="false"
									format="{0,date,dd-MMM-yyyy}">
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
	<!--<div class="waitingWhenDoneWaiting" style="display:block; position: fixed; bottom:5px; right:10px; border-radius: 5px; border: 1px solid gray; background-color: rgb(255, 255, 255); z-index: +1000">
			<div style="margin: 10px"> 
				<button type="button"  onclick="openBoxReject();" name="approve" class="btn btn-primary">
					  <span class="glyphicon glyphicon-list-alt"></span> Tạo danh sách
				</button>
			</div>
	</div>-->
	<c:url var="createHandover" value="/servlet/investigationBca/createHandover" />
	
	<div id="dialog-reject-getRemarks" title="Tạo danh sách bàn giao" style="display: none;"> 
			<div class="form-group">		
				<label for="codeHandover" style="display:none">Mã danh sách bàn giao</label>
				<input class="form-control"  id="codeHandover" name="codeHandover" style="display:none"/>	
				<br />
				<label for="nameHandover">Tên danh sách bàn giao</label>
				<input class="form-control" id="nameHandover" name="nameHandover" />
				<!-- <br />
				<div class="form-group">
				  <label for="typeHandover">Loại danh sách:</label>
				  <select class="form-control" id="typeHandover" name="typeHandover">
				    <option value="1">Bàn giao sang Bộ Công an</option>
				    <option value="2">Bàn giao sang Xét duyệt</option>
				  </select>
				</div> -->
			</div> 
	</div> 
	
	<form:input    path="currentlyAssignedToUserId"                       type="hidden" name="currentlyAssignedToUserId"                       value="" />
	<form:input    path="unassignAllForUserUserId"                        type="hidden" name="unassignAllForUserUserId"                        value="" />
</form:form>



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
					document.forms["formData"].action = '${JobInvestigationConfirmedBcaUrl}';  
					document.forms["formData"].submit();  
				}
			}
		});	
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
			         "Tiếp tục": function() {
						var inp = $("#codeHandover").val();
						var inp1 = $("#nameHandover").val();
						/* 
						if ($.trim(inp).length == 0){
							alert ('Chưa nhập mã danh sách!');
							return;
						}    */
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
			         "Đóng": function() {
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

	function doApplyFilter() {
		document.forms["formData"].action = '<c:url value="/servlet/investigationBca/applyFilterConfirm1" />';
		document.forms["formData"].submit();
	}
	
	function jobInvestigationPendingBcaFunction() {
		$('#ajax-load-qa').css('display', 'block');
		document.forms["investigationMenuForm"].action= '${JobInvestigationPendingBcaUrl}';
		document.forms["investigationMenuForm"].submit();	
	}
	
	function jobInvestigationConfirmedBcaFunction() {
		$('#ajax-load-qa').css('display', 'block');
		document.forms["investigationMenuForm"].action= '${JobInvestigationConfirmedBcaUrl}';
		document.forms["investigationMenuForm"].submit();	
	}
	
	function jobInvestigationLoadBcaFunction() {
		$('#ajax-load-qa').css('display', 'block');
		document.forms["investigationMenuForm"].action= '${JobInvestigationLoadBcaUrl}';
		document.forms["investigationMenuForm"].submit();	
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
</script>
