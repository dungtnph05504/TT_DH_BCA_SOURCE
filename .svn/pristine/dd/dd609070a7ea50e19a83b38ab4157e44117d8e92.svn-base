<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>
<c:url var="listHandoverUrl" value="/servlet/listHandover/listHandover" />
<style>
<!--

-->
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
						<div class="new-heading-2">DANH SÁCH ĐÃ BÀN GIAO</div>

						<div style="clear: both"></div>



						<div style="margin: 2px">
						
							<div style="vertical-align: text-top; display: inline-block; width: 100%; float: left">
								<div
									style="border: solid 1px #cccc; margin: 2px; border-radius: 4px;height: 100px;">
									<div style="margin: 2px">
										<div class="col-md-4 col-sm-4">
											<div class="col-md-5 col-sm-5 cls-mg-bot">
												<div class="cla-font">Mã gói bàn giao</div>
											</div>
											<div class="col-md-7 col-sm-7 cls-mg-bot">
												<form:input path="packageCode" type="text" style="width: 100%;" />
											</div>
											<div class="col-md-5 col-sm-5 cls-mg-bot">
												<div class="cla-font">Loại danh sách</div>
											</div>
											<div class="col-md-7 col-sm-7 cls-mg-bot">
												<form:select path="typeList" style="width: 100%;">
													 <form:option value="">Tất cả</form:option>	
													 <form:option value="1">Kiểm duyệt sơ bộ</form:option>
													 <form:option value="2">Xét duyệt</form:option>
													 <form:option value="3">Chờ duyệt in</form:option>
													 <form:option value="4">Mất/hỏng</form:option>
													 <form:option value="5">Gửi Bộ công an</form:option>
													 <form:option value="6">Đồng bộ HC sang Bộ công an</form:option>	
												</form:select>
											</div>
										</div>
										<div class="col-md-4 col-sm-4">
											<div class="col-md-5 col-sm-5 cls-mg-bot">
												<div class="cla-font">Người lập danh sách</div>
											</div>
											<div class="col-md-7 col-sm-7 cls-mg-bot">
												<form:input path="createBy" type="text" style="width: 100%;" />
											</div>
											<div class="col-md-5 col-sm-5 cls-mg-bot">
												<div class="cla-font">Ngày tạo danh sách</div>
											</div>
											<div class="col-md-7 col-sm-7 cls-mg-bot">
												<form:input id="createDate" name="createDate" path="createDate" cssClass="defaultText"
												size="12" maxlength="12" readonly="true" style="width: 100%;margin-right: -20px;"/>
											</div>
										</div>
										<div class="col-md-4 col-sm-4">
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

						

						<br />
							<div>
						      	<table id="dtBasicExample" class="table table-bordered table-sm" cellspacing="0" width="100%">
								  <thead>
								    <tr>
								      <th class="th-sm" style="max-width: 140px;">Mã gói bàn giao
								
								      </th>	
								      <th class="th-sm" style="min-width: 140px;">Nội dung bàn giao
								
								      </th>
								      <th class="th-sm" style="min-width: 140px;">Loại danh sách
								
								      </th>
								      <th class="th-sm" style="min-width: 140px;">Người lập danh sách
								
								      </th>
								      <th class="th-sm" style="min-width: 130px;">Số lượng hồ sơ
								
								      </th>
								      <th class="th-sm" style="min-width: 120px;">Ngày tạo
								
								      </th>
								    </tr>
								  </thead>
								  <tbody>
								    <c:forEach items="${jobList}" var="item">
									    <tr>
									      <td><c:url var="jobUrl" value="/servlet/listHandover/startDetailHandover/${item.packageId}" />
											<a style="color: blue;" href="${jobUrl}"> <c:out value="${item.packageId}" /></a></td>	
									      <td>${item.packageName}</td>
									      <td>${item.typeListName}</td>
									      <td>${item.createBy}</td>
									      <td>${item.numberTran}</td>
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
								requestURI="/servlet/listHandover/listHandover">
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
								<display:column title="Mã gói bàn giao" sortProperty="packageId"
									sortable="false">
									<c:url var="jobUrl" value="/servlet/listHandover/startDetailHandover/${row.packageId}" />
									<a href="${jobUrl}"> <c:out value="${row.packageId}" /></a>
								</display:column>
								
								<display:column property="packageName" sortable="false"
									title="Nội dung bàn giao" maxLength="50" />
								<display:column property="typeListName" sortable="false"
									title="Loại danh sách" maxLength="50" />
								<display:column property="createBy" sortable="false"
									title="Người lập danh sách" maxLength="30" />
								<display:column property="numberTran" sortable="false"
									title="Số lượng hồ sơ" maxLength="30" />	
								<display:column property="createDate"
									title="Ngày tạo" sortable="false"
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
						document.forms["formData"].action = '${listHandoverUrl}';  
						document.forms["formData"].submit();  
					}
				}
			});	
	function doApplyFilter() {
		document.forms["formData"].action = '<c:url value="/servlet/listHandover/applyFilter" />';
		document.forms["formData"].submit();
	}
	$(document).ready(function(){
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
	});
</script>
