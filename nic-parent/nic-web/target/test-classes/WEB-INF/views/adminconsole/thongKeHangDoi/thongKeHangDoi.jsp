<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>
<c:url var="searchById" value="/servlet/listLogWs/getLogWs" />
<c:url var="statusticalUrl"
	value="/servlet/statustical/searchStatustical" />
<c:url var="init" value="/servlet/listLogWs/init" />
<c:url var="searchURL"
	value="/servlet/statustical/searchStatusticalWsLog" />
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

<form:form modelAttribute="staForm" id="staForm" action="" method="post">
	<div class="form-desing">
		<div>
			<div>
				<div class="row">
					<div class="ov_hidden">
						<div class="new-heading-2">THỐNG KÊ HÀNG ĐỢI</div>
						<div
							style="border: solid 1px #cccc; border-radius: 4px; margin: 2px; min-width: 100%; float: left">
							<div class="col-md-12 col-sm-12">
								<div class="col-md-4 col-sm-4">
									<div class="col-md-5 col-sm-5 cls-mg-bot">
										<div class="cla-font">Loại hàng đợi:</div>
									</div>
									<div class="col-md-7 col-sm-7 cls-mg-bot">
										<form:select style="width: 100%;" path="loaiHangDoi" id="type"
											name="type">
											<form:option value="">-- Tất cả --</form:option>
											<form:option value="DA">Danh sách A</form:option>
											<form:option value="DB">Danh sách B</form:option>
											<form:option value="DC">Danh sách C</form:option>
											<form:option value="HS">Hồ sơ xử lý</form:option>
											<form:option value="HSF">Hồ sơ xử lý đầy đủ</form:option>
											<form:option value="UPP">Cập nhật hộ chiếu</form:option>
											<form:option value="PS">Thông tin person</form:option>
											<form:option value="BF">Hồ sơ nghi trùng</form:option>
										</form:select>
									</div>
									<div class="col-md-5 col-sm-5 cls-mg-bot">
										<div class="cla-font">Nơi Nhận:</div>
									</div>
									<div class="col-md-7 col-sm-7 cls-mg-bot">
										<form:select style="width: 100%;" path="receiver"
											id="receiver" name="receiver">
											<form:option value="">-- Tất cả --</form:option>
											<c:forEach items="${map}" var="map">
												<form:option value="${map.key}">${map.value}</form:option>
											</c:forEach>
											<c:forEach items="${map1}" var="map1">
												<form:option value="${map1.key}">${map1.value}</form:option>
											</c:forEach>
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
											path="dateFrom" placeholder="YYYYMMDD" cssClass="defaultText"
											size="12" maxlength="12"
											style="width: 100%;margin-right: -20px;" />
									</div>
									<div class="col-md-5 col-sm-5 cls-mg-bot">
										<div class="cla-font">Kiểm định đến ngày:</div>
									</div>
									<div class="col-md-7 col-sm-7 cls-mg-bot"
										style="position: relative;">
										<form:input type="text" id="dateTo" name="dateTo"
											path="dateTo" placeholder="YYYYMMDD" cssClass="defaultText"
											size="12" maxlength="12"
											style="width: 100%;margin-right: -20px;" />
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
							<div>
							 <c:if test="${not empty dateStr}">
							      <p id="lable" style="color: crimson">
									Thống kê truyền nhận vào ngày : <span
										class="label label-default">${dateStr}</span>
								</p>
							 </c:if>
								<div class="row">
								  <div class="col-md-6">
								    <fieldset>
							       <legend>Danh sách thống kê hàng đợi</legend>
								    <div>
								        <table id="dtBasicExample" class="table table-bordered table-sm"
									cellspacing="0" width="100%">
									<thead>
										<tr>
											<th class="th-sm" style="max-width: 100px;">Loại hàng
												đợi</th>
											<th class="th-sm" style="min-width: 10px;">Thành công</th>
											<th class="th-sm" style="min-width: 140px;">Chờ xử lý</th>
											<th class="th-sm" style="min-width: 150px;">Đang xử lý</th>
											<th class="th-sm" style="min-width: 100px;">Lỗi xử lý</th>
											<th class="th-sm" style="min-width: 100px;">Tổng</th>
											<!-- <th class="th-sm" style="min-width: 70px;">Chi tiết</th> -->
										</tr>
									</thead>
									<tbody>
										<c:forEach items="${listStatus}" var="listStatus">
											<tr style="text-align: center;">
												<td>${listStatus.loaiHangDoi}</td>
												<td><a href="#" class ="glyphicon glyphicon-eye-open"
													onclick="getDetail('${listStatus.loaiHangDoi}','DONE',$('#dateFrom').val(),$('#dateTo').val(),$('#receiver').val())"><span> ${listStatus.done}</span></a></td>
												<td><a href="#" class ="glyphicon glyphicon-eye-open"
													onclick="getDetail('${listStatus.loaiHangDoi}','PENDING',$('#dateFrom').val(),$('#dateTo').val(),$('#receiver').val())"><span> ${listStatus.pending}</span></a></td>
												<td><a href="#" class ="glyphicon glyphicon-eye-open"
													onclick="getDetail('${listStatus.loaiHangDoi}','DOING',$('#dateFrom').val(),$('#dateTo').val(),$('#receiver').val())"><span> ${listStatus.doing}</span></a></td>
												<td><a href="#" class="glyphicon glyphicon-eye-open"
													onclick="getDetail('${listStatus.loaiHangDoi}','NONE',$('#dateFrom').val(),$('#dateTo').val(),$('#receiver').val())"><span> ${listStatus.none}</span></a></td>
												<td>${listStatus.total}</td>
											</tr>
										</c:forEach>
									</tbody>
								</table>
								    </div>
								     
								  </div>
								  <div class="col-md-6">
								   <fieldset>
							       <legend>Danh sách hàng đợi chi tiết</legend>
								     <div>
								    <table id="listWsLog" class="table table-bordered table-sm"
									cellspacing="0" width="100%">
									<thead>
										<th class="th-sm">Mã hàng đợi</th>
										<th class="th-sm">Kiểu hàng đợi</th>
										<th class="th-sm">Trạng thái</th>
<!-- 										<th class="th-sm">Ngày tạo</th>
 -->										<th class="th-sm">Số lần gọi</th>
									</thead>
									<tbody id="tblBody">

									</tbody>

								</table>
								<div class="col-md-5 col-sm-12" style="padding-left: 0px;">
									<div class="dataTables_info"></div>
								</div>
								<div class="col-md-7 col-sm-12" style="padding-right: 0px;">
									<ul style="float: right;" class="pagination" id="pagination"></ul>
								</div>
								<input type="hidden" name="pageNo" id="pageNo" />
								     </div>
								
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
	function validSearch() {
		if ($("#type").val() == "" && $("#receiver").val() == ""
				&& $("#dateFrom").val() == "" && $("#dateTo").val() == "") {
			return false;
		}
		return true;
	}
	function load(type, status, dateFrom, dateTo, receiver, page) {
		$('#tblBody').html("");
		$.ajax({
					url : '${searchURL}',
					method : 'POST',
					dataType : 'json',
					data : {
						type : type,
						status : status,
						dateFrom : dateFrom,
						dateTo : dateTo,
						receiver : receiver,
						page : page
					},
					success : function(data) {
						$('#pageNo').val(data.total);
						//document.getelementbyid("pageNo").val(data.total);
						//alert($('#pageNo').val());
						var currentPage = page;
						var pageSize = 10;
						$('#listWsLog').show();
						$('#pagination').twbsPagination('destroy');
						$('#pagination')
								.twbsPagination(
										{
											totalPages : $('#pageNo').val(),
											visiblePages : pageSize,
											startPage : currentPage,
											next : '<span class=\"glyphicon glyphicon-forward fix-paging\"></span>',
											prev : '<span class=\"glyphicon glyphicon-backward fix-paging\"></span>',
											last : '<span class=\"glyphicon glyphicon-step-forward fix-paging\"></span>',
											first : '<span class=\"glyphicon glyphicon-step-backward fix-paging\"></span>',
											onPageClick : function(event, page) {
												if (currentPage != page) {
													currentPage = page;
													//$('#pageNo').val(page);
													load(type, status,
															dateFrom, dateTo,
															receiver, page);
												}
											}
										});
						$.each(data.rows, function(key, value) {
							$('#tblBody').append(
									'<tr style="text-align:center;"><td>'
											+ value.objectId + '</td>  <td>'
											+ value.objectType + '</td>  <td>'
											+ value.status + '</td>      <td>'
											+ value.syncRetry + '</td><tr>');
						});

						//alert(totalpage1);
					},
					error : function(error) {
						alert(error);
					}
				})
		// alert(totalpage1);
	}
	function getDetail(type, status, dateFrom, dateTo, receiver) {
		// alert(type + " " + status + " " + receiver);
		$('#tblBody').html("");
		load(type, status, dateFrom, dateTo, receiver, 1);
		/*  alert($('#pageNo').val());
		 var currentPage = 1;
		var pageSize = 10;
		$('#listWsLog').show();
		$('#pagination')
		.twbsPagination(
				{   
					totalPages : $('#pageNo').val(),				
					visiblePages : pageSize,
					startPage : currentPage,
					next : '<span class=\"glyphicon glyphicon-forward fix-paging\"></span>',
					prev : '<span class=\"glyphicon glyphicon-backward fix-paging\"></span>',
					last : '<span class=\"glyphicon glyphicon-step-forward fix-paging\"></span>',
					first : '<span class=\"glyphicon glyphicon-step-backward fix-paging\"></span>',
					onPageClick : function(event, page) {
						if (currentPage != page) {
							currentPage =page;
							//$('#pageNo').val(page);
								load(type,status,dateFrom,dateTo,receiver,page);
						}
					}
				});  */
	}
	$(function() {
		$('#listWsLog').hide();
		/* var totalPages = ${totalPage};
		var currentPage = ${pageNo};
		var pageSize = ${pageSize};
		$('#listWsLog').hide();
		$('#pagination').hide();
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
					});  */
		
	});
	$("#reset_btn_data").click(function() {
		$("#type").val("");
		$("#receiver").val("");
		$("#dateFrom").val("");
		$("#dateTo").val("");
	});

	$('#search_btn_data').click(function() {
		$('#listWsLog').hide();
		if (validSearch() == false) {
			alert('yêu cầu điền thông tin tối thiểu một trường')
		} else {
			var url = '${statusticalUrl}';
			document.forms["staForm"].action = url;
			document.forms["staForm"].submit();
		}
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

</script>
