<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>
<c:url var="viewSearchList" value="/servlet/listLogCheckConnection/viewSearchList" />
<c:url var="viewList" value="/servlet/listLogCheckConnection/viewList" />
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

<form:form modelAttribute="site" id="site"
	action="" method="get">
	<div class="form-desing">
		<div>
			<div>
				<div class="row">
					<div class="ov_hidden">
						<div class="new-heading-2">TRA CỨU TRẠNG THÁI KẾT NỐI</div>
						<fieldset>
							<legend>Điều kiện tìm kiếm</legend>
							<div style="border: solid 1px #cccc; border-radius: 4px; margin: 2px; min-width: 100%; float: left">
								<div class="col-md-12 col-sm-12">
									<div class="col-md-4 col-sm-4">
	
										<div class="col-md-5 col-sm-5 cls-mg-bot">
											<div class="cla-font">Trung tâm:</div>
										</div>
										<div class="col-md-7 col-sm-7 cls-mg-bot">
											
											<form:select style="width: 100%;" path="siteName"
												id="siteName" name="siteName">
												<form:option value="">-- Tất cả --</form:option>
												<c:forEach items="${siteList}" var="item">
													<form:option value="${item.siteCode}">${item.siteName}</form:option>
												</c:forEach>
											</form:select>
										</div> 
									</div>
										
								</div>
								<div class="col-md-4 col-sm-4"
									style="padding-top: 20px; margin-bottom: 20px; float: inline-end;">
									<button type="button" id="search_btn_data"
										class="btn_small btn-success btn-search"
										style="display: flow-root;">
										<span class="glyphicon glyphicon-search"></span> Tìm kiếm
									</button>
								</div>
							</div>
						</fieldset>
						
						
 						<div class="container-fluid">    
  							<div class="row">
								<div class="col-sm-6">
									<fieldset style="background: white;">
									<legend>Danh sách trung tâm kết nối</legend>
									<div>
										<table id="dtBasicExample" class="table table-bordered table-sm"
											cellspacing="0" width="100%" style="background: white;">
											<thead>
												<tr>
													<th class="th-sm" style="max-width: 100px;">Trung tâm</th>
													<th class="th-sm" style="min-width: 100px;">Ngày tạo kết nối</th>
													<th class="th-sm" style="min-width: 100px;">Thời điểm kết nối mới nhất</th>
												</tr>
											</thead>
											<tbody>
												<c:forEach items="${listLogCheckConnection}" var="item">
													<tr>
														<td>${item.siteName}</td>
														<td>${item.createDatetime}</td>
														<td>${item.lastConnectedDatetime}</td>
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
									</fieldset>
 								</div>
								
								<div class="col-sm-6" >
									<fieldset style="background: white;">
									<legend>Danh sách trung tâm mất kết nối tạm thời</legend>
									<div>
										<table id="dtBasicExampleL" class="table table-bordered table-sm"
											cellspacing="0" width="100%" style="background: white;">
											<thead>
												<tr>
													<th class="th-sm" style="max-width: 100px;">Trung tâm</th>
													<th class="th-sm" style="min-width: 100px;">Thời điểm kết nối mới nhất</th>
												</tr>
											</thead>
											<tbody>
												<c:forEach items="${listLogCheckConnectionLost}" var="item">
													<tr>
														<td>${item.siteName}</td>
														<td>${item.lastConnectedDatetime}</td>
													</tr>
												</c:forEach>
											</tbody>
										</table>
										
										<div class="col-md-5 col-sm-12" style="padding-left: 0px;">
											<div class="dataTables_infoL">Hiển thị ${startIndexLost} đến
												${endIndexLost} của ${totalRecordLost} kết quả</div>
										</div>
										<div class="col-md-7 col-sm-12" style="padding-right: 0px;">
											<ul style="float: right;" class="paginationL" id="paginationL"></ul>
										</div>
										<input type="hidden" name="pageNoL" id="pageNoL" />
									</div> 
									</fieldset>
								</div>
							</div>
						</div> 
						
					</div>
				</div>
			</div>
		</div>
	</div>

</form:form>

<script type="text/javascript">
	var reload = "0";
	$(function() {
		var totalPages = ${totalPage};
		var currentPage = ${pageNo};
		var pageSize = ${pageSize};
		
		var totalPagesL = ${totalPageL};
		var currentPageL = ${pageNoL};
		window.pagObj = $('#pagination').twbsPagination(
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
									$('#pageNo').val(page);
									document.forms["site"].action = '${viewList}';
									document.forms["site"].submit();

								}
							}
						});
		
		window.pagObj = $('#paginationL').twbsPagination(
						{
							totalPages : totalPagesL,
							visiblePages : pageSize,
							startPage : currentPageL,
							next : '<span class=\"glyphicon glyphicon-forward fix-paging\"></span>',
							prev : '<span class=\"glyphicon glyphicon-backward fix-paging\"></span>',
							last : '<span class=\"glyphicon glyphicon-step-forward fix-paging\"></span>',
							first : '<span class=\"glyphicon glyphicon-step-backward fix-paging\"></span>',
							onPageClick : function(event, page) {
								if (currentPageL != page) {
									$('#pageNoL').val(page);
									document.forms["site"].action = '${viewList}';
									document.forms["site"].submit();
		
								}
							}
						});

	});

	$('#search_btn_data').click(function() {
		document.forms["site"].action = '${viewList}';
		document.forms["site"].submit();
	});
</script>
