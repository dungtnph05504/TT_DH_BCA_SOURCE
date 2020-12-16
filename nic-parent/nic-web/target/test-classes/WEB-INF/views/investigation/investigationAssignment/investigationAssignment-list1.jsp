<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>
<c:url var="investigationJobAssignmentUrl"
	value="/servlet/investigationAssignment/investigationAssignmentList" />
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/jquery-confirm/3.3.2/jquery-confirm.min.css">
<script src="https://cdnjs.cloudflare.com/ajax/libs/jquery-confirm/3.3.2/jquery-confirm.min.js"></script>
<style>
.cls-mg-bot {
	margin-top: 10px;
}
.jconfirm .jconfirm-box div.jconfirm-content-pane .jconfirm-content {
    white-space: pre-wrap;
}
.pagebanner {
	margin-top: 15px;
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
<form:form modelAttribute="formData" name="formData">
	<div class="form-desing">
		<div>
			<div>
				<div class="row">
					<div class="ov_hidden">
						<div class="new-heading-2">DANH SÁCH CHỜ PHÂN CÔNG</div>

						<div style="clear: both"></div>

						<div style="min-height: 10px"></div>
						

						<div style="border: solid 1px #cccccc; border-radius: 4px; margin: 2px;width:80%;float:left;min-height: 150px;">
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
										<div class="cla-font">Mã phiếu bàn giao:</div>
									</div>
									<div class="col-md-7 col-sm-7 cls-mg-bot">
										<form:input path="packageCode" type="text" style="width: 100%;"/>
									</div>
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
								</div>														
								<div class="col-md-4 col-sm-4">
									<div class="col-md-5 col-sm-5 cls-mg-bot" style="position: relative;">
										<div class="cla-font">Ngày nộp đơn:</div>
									</div>
									<div class="col-md-7 col-sm-7 cls-mg-bot">
										<form:input id="createDate" name="createDate" path="createDate" cssClass="defaultText"
										size="12" maxlength="12" readonly="true" style="width: 100%;margin-right: -20px;"/>
									</div>
									<div class="col-md-12" style="margin-top: 40px;">
										<div>
											<button type="button" onclick="doApplyFilter();" class="btn_small btn-primary btn-search">
										        <span class="glyphicon glyphicon-search"></span> Tìm kiếm
										    </button>	
										</div>
									</div>
								</div>


							</div>
						</div>

						<div
							style="border: solid 1px #cccccc; border-radius: 4px;width: 19%; margin: 2px;min-height: 150px; float: right;padding-top:25px">
							<div style="margin: 2px">
									
												<div style="margin: 2px">
													<div style="text-align: center">Cán bộ xử lý</div>
													<div style="text-align: center">
														<%-- <form:input path="assignToId" class="assignToIdStyle"
															type="text" /> --%>
												<form:select path="assignToId" id="assignToIdStyle"
												name="assignToId" class="assignToIdStyle"
												style="font-family: Arial; font-size: 12px;">
													<c:forEach var="entry" items="${userAssignment}">
														<form:option value="${entry.key}" style="margin-right: 5px;" label="${entry.value}" />
				
													</c:forEach>
												</form:select>
													</div>
													<div style="text-align: center;margin-top: 10px;">
														<!--<input type="button" class="btn_small btn-primary"
															value="Phân công" style="" onclick="doAssign();" />-->
														<button type="button" onclick="doAssign();" class="btn_small btn-primary" style="height: 30px;">
													        <span class="glyphicon glyphicon-list-alt"></span> Phân công
													    </button>
													</div>
												</div>
											</div>
										</div>
						<div style="clear: both"></div>

						<br />

						<%--
							int pageSize = 20;
						--%>

							 <div>
						      	<table id="dtBasicExample" class="table table-bordered table-sm" cellspacing="0" width="100%">
								  <thead>
								    <tr>
								      <th class="th-sm" style="max-width: 50px;">ID
								
								      </th>	
								      <th class="th-sm">Mã giao dịch
								
								      </th>
								      <th class="th-sm">Loại hô chiếu
								
								      </th>
								      <th class="th-sm" style="min-width: 140px;">Ngày nộp đơn
								
								      </th>
								      <th class="th-sm" style="min-width: 140px;">Ngày trả kết quả
								
								      </th>
								      <th class="th-sm" style="min-width: 100px;">Ưu tiên
								
								      </th>
								      <th class="th-sm" style="min-width: 120px;">Loại giao dịch
								
								      </th>
								      <th class="th-sm" style="min-width: 120px;">Loại điều tra
								      </th>
								      <th class="th-sm" style="max-width: 50px;">Chọn
								      </th>
								    </tr>
								  </thead>
								  <tbody>
								    <c:forEach items="${jobList}" var="item">
									    <tr>
									      <td>${item.uploadJobId}</td>	
									      <td>${item.transactionId}</td>
									      <td>${item.passportType}</td>
									      <td>${item.dateOfApplication}</td>
									      <td>${item.estDateOfCollectionString}</td>
									      <td>${item.priorityString}</td>
									      <td>${item.jobType}</td>
									      <td>${item.investigationType}</td>
									      <td><form:checkbox path="selectedJobIds" value="${item.uploadJobId}"
										cssClass="${item.currentlyAssignedToAnInvestigationOfficer}" /></td>	
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
							<!--<display:table cellspacing="1" cellpadding="0" id="row"
								sort="external" partialList="true" size="${totalRecords}"
								export="false" class="displayTag" name="jobList" defaultsort="1"
								defaultorder="ascending" pagesize="${pageSize}"
								requestURI="/servlet/investigationAssignment/investigationAssignmentList">
								<display:setProperty name="paging.banner.item_name" value='kết quả'></display:setProperty>
								<display:setProperty name="paging.banner.items_name" value='kết quả'></display:setProperty>
								<display:setProperty name="paging.banner.placement" value='bottom'></display:setProperty>
								<display:setProperty name="paging.banner.no_items_found" value='<span class="pagebanner"> Không tìm thấy dữ liệu. </span>'></display:setProperty>
								<display:setProperty name="paging.banner.one_item_found" value='<span class="pagebanner"> Tìm thấy 1 kết quả. </span>'></display:setProperty>
								<display:setProperty name="paging.banner.all_items_found" value='<span class="pagebanner"> {0} {1} tìm thấy, hiển thị tất cả {2}. </span>'></display:setProperty>
								<display:setProperty name="paging.banner.some_items_found" value='<span class="pagebanner"> {0} {1} tìm thấy, hiển thị từ {2} đến {3}. </span>'></display:setProperty>		
								<display:setProperty name="paging.banner.first" value='<span class="pagelinks"> [Đầu tiên/Trước] {0} [ <a href="{3}">Tiếp</a>/ <a href="{4}">Cuối cùng</a>] </span>'></display:setProperty>		
								<display:setProperty name="paging.banner.last" value='<span class="pagelinks">[ <a href="{1}">Đầu tiên</a>/ <a href="{2}">Trước</a>] {0} [Tiếp/Cuối cùng] </span>'></display:setProperty>								
								<display:setProperty name="paging.banner.full" value='<span class="pagelinks"> [<a href="{1}">Đầu tiên</a>/ <a href="{2}">Trước</a>] {0} [ <a href="{3}">Tiếp</a>/ <a href="{4}">Cuối cùng </a>]</span>'></display:setProperty>													
								<display:column title="ID" property="uploadJobId"
									sortable="false">
								</display:column>
								<display:column property="transactionId" sortable="false"
									title="Mã giao dịch" maxLength="30" />
								<display:column property="passportType" sortable="false"
									title="Loại hộ chiếu" maxLength="50" />
								<display:column property="dateOfApplication"
									title="Ngày nộp đơn" sortable="false"
									format="{0,date,dd-MMM-yyyy}">
								</display:column>
								<display:column property="estDateOfCollectionString"
									sortable="false" title="Ngày trả kết quả" maxLength="30" />
								<display:column property="priorityString" sortable="false"
									title="Ưu tiên" maxLength="30" />
								<display:column property="jobType" sortable="false"
									title="Loại giao dịch" maxLength="30" />
								<display:column property="investigationType" sortable="false"
									title="Loại điều tra" maxLength="30">
								</display:column>
								<display:column title="Chọn" sortable="false">
									<form:checkbox path="selectedJobIds" value="${row.uploadJobId}"
										cssClass="${row.currentlyAssignedToAnInvestigationOfficer}" />
								</display:column>
							</display:table>-->
					



						<%
							/* ******************************************************************************************************************** */
						%>
						<%
							/* ************************************* actions for selected jobs - start ******************************************** */
						%>
						<%
							/* ******************************************************************************************************************** */
						%>

						<!--<c:if test="${totalRecords > 0}">
							<div style="clear: both"></div>

							<div style="min-height: 5px"></div>

							<%-- <div
								style="border: solid 1px gray; border-radius: 4px; margin: 2px">
								<div style="margin: 2px">
									<div
										style="text-align: center; font-weight: bold; margin-top: 2px; margin-bottom: 2px">
										Hành động đối với những việc đã chọn ở trên</div>
									<div>
										<div
											style="vertical-align: text-top; display: inline-block; width: 20%; margin-left: 29%; float: left">
											<div
												style="border: solid 1px gray; border-radius: 4px; margin: 2px; min-height: 74px;">
												<div style="margin: 2px">
													<div style="text-align: center">
														<input type="button" class="btn_small btn-primary"
															value="Chỉ định đã chọn" style="" onclick="doAssign();" />
													</div>
													<div style="text-align: center">Cho tên người dùng</div>
													<div style="text-align: center">
														<form:input path="assignToId" class="assignToIdStyle"
															type="text" />
													</div>
												</div>
											</div>
										</div>
										<div
											style="vertical-align: text-top; display: inline-block; width: 20%; margin-right: 29%; float: left">
											<div
												style="border: solid 1px gray; border-radius: 4px; margin: 2px; min-height: 74px;">
												<div style="margin: 2px">
													<div style="text-align: center">
														<input type="button" class="btn_small btn-primary"
															value="Bỏ việc giao đã chọn" style=""
															onclick="doUnassign();" />
													</div>
												</div>
											</div>
										</div>
									</div>
									<div style="clear: both"></div>
								</div>
							</div> --%>

							<div style="clear: both"></div>
						</c:if>-->

						<%
							/* ******************************************************************************************************************** */
						%>
						<%
							/* ************************************** actions for selected jobs - end ********************************************* */
						%>
						<%
							/* ******************************************************************************************************************** */
						%>

						<%
							/* ******************************************************************************************************************** */
						%>
						<%
							/* ********************************* Actions For All Jobs In The System - start *************************************** */
						%>
						<%
							/* ******************************************************************************************************************** */
						%>

						<div style="clear: both"></div>

						<div style="min-height: 5px"></div>

						<%-- <div
							style="border: solid 1px gray; border-radius: 4px; margin: 2px">
							<div style="margin: 2px">
								<div
									style="text-align: center; font-weight: bold; margin-top: 2px; margin-bottom: 2px">
									Hành động cho tất cả các việc trong hệ thống</div>
								<div>
									<div
										style="vertical-align: text-top; display: inline-block; width: 30%; margin-left: 35%; margin-right: 34%; float: left">
										<div
											style="border: solid 1px gray; border-radius: 4px; margin: 2px">
											<div style="margin: 2px">
												<div style="text-align: center">
													<input type="button" class="btn_small btn-primary"
														value="Bỏ tất cả việc đã giao" style=""
														onclick="doUnassignAllForUser();" />
												</div>
												<div style="text-align: center">Hiện được giao cho tên
													người dùng</div>
												<div style="text-align: center">
													<form:input path="unassignAllForUserUserId"
														class="unassignAllForUserUserIdStyle" type="text" />
												</div>
											</div>
										</div>
									</div>
								</div>
								<div style="clear: both"></div>
							</div>
						</div> --%>

						<div style="clear: both"></div>

						<%
							/* ******************************************************************************************************************** */
						%>
						<%
							/* ********************************** Actions For All Jobs In The System - end **************************************** */
						%>
						<%
							/* ******************************************************************************************************************** */
						%>
					</div>
				</div>
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
					document.forms["formData"].action = '${investigationJobAssignmentUrl}';  
					document.forms["formData"].submit();  
				}
			}
		});
	
	function doApplyFilter() {
		
		document.forms["formData"].action = '<c:url value="/servlet/investigationAssignment/applyFilter" />';
		document.forms["formData"].submit();
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

<c:url var="urlAssign" value="/servlet/investigationAssignment/assign" />

<div id="dialog-confirm-assign"></div>

<script type="text/javascript">
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
	function doAssign() {

		var messages = validateForAssignment();
		if (messages != '') {
			//alert(messages);
			$.alert({
				title: 'Thông báo',
				content: "<img style=\"margin-bottom: 5px;\" src=\"/eppcentral/resources/images/warning_1b.png\">" + messages,
			});
			return;
		}
		else{
			$("#dialog-confirm-assign").dialog('option', 'title','Xác nhận phân công');
			$("#dialog-confirm-assign").html("Tiến hành phân công / phân công lại các hồ sơ bạn đã chọn?");
			$("#dialog-confirm-assign").dialog('open');
		}
	}

	function validateForAssignment() {

		var numberOfJobsInPage = ${totalRecords};

		if (numberOfJobsInPage == 0) {
			return 'Không có công việc có sẵn để phân công.';
		}

		var inp = $(".assignToIdStyle").val();
		if ($.trim(inp).length == 0) {
			return 'Vui lòng nhập ID người dùng để gán.';
		}

		if (($(".currentlyNotAssignedToAnInvestigationOfficer:checked").length + $(".currentlyAssignedToAnInvestigationOfficer:checked").length) == 0) {
			return 'Vui lòng chọn các công việc mà bạn muốn giao.';
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
