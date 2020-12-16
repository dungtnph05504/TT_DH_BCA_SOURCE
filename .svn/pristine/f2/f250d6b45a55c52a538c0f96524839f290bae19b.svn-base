<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<c:url var="loadFormQueueUrl"
	value="/servlet/central/showTemplatePassport" />
<c:url var="getAddPassportForm" value="/servlet/central/addPassportForm" />
<c:url var="chitietTemplate"
	value="/servlet/central/chitietTemplatePassport" />
<c:url var="editForm" value="/servlet/central/editTemplatePassportForm" />
<c:url var="changeStatus" value="/servlet/central/changeStatus" />
<c:url var="passportTypeSeach" value="/servlet/central/findPassportTypeSeach" />
<c:url var="yearIssueSeach" value="/servlet/central/findYearIssueSeach" />
<style>
.style-width-100 {
	width: 100%;
}

#ajax-load-qa {
	background: rgba(255, 255, 255, .6)
		url('<c:url value="/resources/images/loading_nin.gif" />') 50% 50%
		no-repeat;
	position: fixed;
	z-index: 100;
	left: 0;
	top: 0;
	width: 100%;
	height: 100%;
	display: none;
	text-align: center;
}

.toggle {
	border-radius: 20px;
}

/* The switch - the box around the slider */
.switch {
	position: relative;
	display: inline-block;
	width: 60px;
	height: 34px;
	float: right;
}

/* Hide default HTML checkbox */
.switch input {
	display: none;
}

/* The slider */
.slider {
	position: absolute;
	cursor: pointer;
	top: 0;
	left: 0;
	right: 0;
	bottom: 0;
	background-color: #ccc;
	-webkit-transition: .4s;
	transition: .4s;
}

.slider:before {
	position: absolute;
	content: "";
	height: 26px;
	width: 26px;
	left: 4px;
	bottom: 4px;
	background-color: white;
	-webkit-transition: .4s;
	transition: .4s;
}

input.default:checked+.slider {
	background-color: #444;
}

input.primary:checked+.slider {
	background-color: #2196F3;
}

input.success:checked+.slider {
	background-color: #8bc34a;
}

input.info:checked+.slider {
	background-color: #3de0f5;
}

input.warning:checked+.slider {
	background-color: #FFC107;
}

input.danger:checked+.slider {
	background-color: #f44336;
}

input:focus+.slider {
	box-shadow: 0 0 1px #2196F3;
}

input:checked+.slider:before {
	-webkit-transform: translateX(26px);
	-ms-transform: translateX(26px);
	transform: translateX(26px);
}

/* Rounded sliders */
.slider.round {
	border-radius: 34px;
}

.slider.round:before {
	border-radius: 50%;
}
</style>
<div class="content-item-main">
	<form:form modelAttribute="formData" name="formData"
		aautocomplete="off">
		<div class="col-sm-12">

			<fieldset class="scheduler-border">
				<legend>Điều kiện tìm kiếm</legend>
				<div class="form-group" style="margin-top: 15px;">

					<div class="col-sm-4">
						<label class="col-sm-4 control-label text-right">Mã quốc gia: </label>
						<div class="col-sm-8">
							<form:select id="nationSeach" path="nameNationVie" class="style-width-100" >
								<form:option value="" >-- Tất cả --</form:option>
								<c:forEach items="${resultSeach}" var="resultSeach">
									<form:option value="${resultSeach}" >${resultSeach}</form:option>
								</c:forEach>
							</form:select>
						</div>
					</div>
					<div class="col-sm-4">
						<label class="col-sm-4 control-label text-right">Kiểu hộ chiếu:</label>
						<div class="col-sm-8">
							<form:select  path="passPortType" id="passportTypeSeach"
								class="style-width-100">
								<form:option value="">-- Tất cả --</form:option>
								<form:option value="P" >Hộ chiếu phổ thông</form:option>
								<form:option value="PO" >Hộ chiếu ngoại giao</form:option>
								<form:option value="PD" >Hộ chiếu công vụ</form:option>
							</form:select>
						</div>
					</div>
					<div class="col-sm-4">
						<label class="col-sm-4 control-label text-right">Năm phát hành: </label>
						<div class="col-sm-8">
						<input path="yearIssue" class="style-width-100" value=""
								id="yearIssueSeach" />
						
						</div>
					</div>
				</div>

				<div class="form-group">
					<div class="col-sm-12">
						<button type="button" style="float: right; margin-right: 15px;"
							onclick="doApplyFilter();" class="btn_small btn-success">
							<span class="glyphicon glyphicon-search"></span> Tìm kiếm
						</button>

					</div>
				</div>
			</fieldset>

		</div>
		<div class="col-sm-12" style="height: 900px; overflow: auto;"" >

			<fieldset class="scheduler-border">
				<legend class="scheduler-border">Danh sách hộ chiếu quốc gia</legend>

				<div style="height: 800px; overflow: auto;">
					<table id="dtBasicExample"
						class="table table-bordered table-sm table-hover" cellspacing="0"
						width="100%">
						<thead>
							<tr>
								<th class="th-sm" style="width: 40px;">ID</th>
								<th class="th-sm">Tên quốc gia</th>
								<th class="th-sm">Kiểu hộ chiếu</th>
								<th class="th-sm">Năm phát hành</th>
								<th class="th-sm">Ngày khởi tạo</th>
								<th class="th-sm">Người tạo</th>
								<th class="th-sm">Ngày sửa đổi</th>
								<th class="th-sm">Người sửa đổi</th>
								<th class="th-sm">Miêu tả</th>
								<th class="th-sm">Trạng thái kích hoạt</th>
								<th class="th-sm" style="width: 80px;">Chi tiết</th>
								<th class="th-sm" style="width: 40px;">Sửa</th>
							</tr>

							</tr>
						</thead>


						<c:forEach items="${dsQueue}" var="item" varStatus="count">
							<tr>
								<td>${count.index + 1}</td>
								<td>${item.nameNationVie}</td>
								<td><c:if test="${item.passPortType eq 'P'}">
								HC Phổ Thông
								</c:if>
								<c:if test="${item.passPortType eq 'PD'}">
								HC Công Vụ
								</c:if>
								<c:if test="${item.passPortType eq 'PO'}">
								HC Ngoại Giao
								</c:if>
								</td>
								<td>${item.yearIssue}</td>
								<td>${item.createDatetime}</td>
								<td>${item.createBy}</td>
								<td>${item.modifyDateTime}</td>
								<td>${item.modifyBy}</td>
								<td>${item.description}</td>
								<td><c:choose>
										<c:when test="${item.status}">
											<label class="switch "> <input type="checkbox"
												id="status" class="warning" checked="checked"
												value="${item.id}" /> <span class="slider round"></span>
											</label>
										</c:when>
										<c:otherwise>
											<label class="switch "> <input type="checkbox"
												id="status" class="warning" value="${item.id}" /> <span
												class="slider round"></span>
											</label>
										</c:otherwise>
									</c:choose></td>


								<td align="center"><a href="#"
									onclick="chiTietHS('${item.id}');"><i
										class="glyphicon glyphicon-edit"></i>Chi tiet</a></td>
								<td align="center"><a href="#"
									onclick="editTemplate('${item.id}');"><i
										class="glyphicon glyphicon-edit"></i>Sửa</a></td>
							</tr>
						</c:forEach>
					</table>

					<table class="table e-grid-table e-border">
						<tfoot>
							<tr>
								<th><c:if test="${not empty dsQueue}">

										<div class="e-page-rigth">
											<ul style="float: right;" class="pagination" id="pagination"></ul>
										</div>
										<input type="hidden" name="pageNo" id="pageNo" />
									</c:if>

									<div class="e-page-left" style="font-weight: normal;">
										Hiển thị ${startIndex} đến ${endIndex} của ${totalRecord} kết
										quả</div></th>
							</tr>
						</tfoot>
					</table>
			</fieldset>
		</div>

<div id="ajax-load-qa"></div>
<div class="modal fade" id="checkSiteId" tabindex="-1" role="dialog"
	aria-labelledby="exampleModalCenterTitle" aria-hidden="true">
	<div class="modal-dialog modal-dialog-centered" role="document"
		style="width: 80%; ">
		<div class="modal-content">
			<div class="modal-header">
				<h5 class="modal-title" id="exampleModalLongTitle"
					style="display: inline-block;">CHI TIET</h5>
				<button type="button" class="close" data-dismiss="modal"
					aria-label="Close">
					<img style="width: 25px; height: 25px;"
						src="<c:url value='/resources/images/dongform.png' />"
						title="Đóng" />
				</button>
			</div>
			<div class="modal-body" id="checkSiteForm" style="height: 750px!important;"></div>
			<div class="modal-footer" style="padding: 5px;">
				<button type="button" onclick="" class="btn btn-warning"
					data-dismiss="modal" aria-label="Close" style="float: right;">
					<span class="glyphicon glyphicon-remove btn-boot"
						style="margin-right: 2px;"></span><span class="btn-boot">
						ĐÓNG</span>
				</button>
			</div>
		</div>
	</div>
</div>


<div class="modal fade" id="addPassport" tabindex="-1" role="dialog"
	aria-labelledby="exampleModalCenterTitle" aria-hidden="true">
	<div class="modal-dialog modal-dialog-centered" role="document"
		style="width: 70%;">
		<div class="modal-content">
			<div class="modal-header">
				<h5 class="modal-title" id="exampleModalLongTitle"
					style="display: inline-block;">Thêm hộ chiếu mới</h5>
				<button type="button" class="close" data-dismiss="modal"
					aria-label="Close">
					<img style="width: 25px; height: 25px;"
						src="<c:url value='/resources/images/dongform.png' />"
						title="Đóng" />
				</button>
			</div>
			<div class="modal-body" id="addPassportForm" ></div>
			<div class="modal-footer" style="padding: 5px;">
				<button type="button" onclick="" class="btn btn-warning"
					data-dismiss="modal" aria-label="Close" style="float: right;">
					<span class="glyphicon glyphicon-remove btn-boot"
						style="margin-right: 2px;"></span><span class="btn-boot">
						ĐÓNG</span>
				</button>
				<button type="button" onclick="addPassport();"
					class="btn btn-success" style="float: right; margin-right: 10px;">
					<span class="glyphicon glyphicon-ok btn-boot"
						style="margin-right: 2px;"></span><span class="btn-boot">
						THÊM</span>
				</button>
			</div>
		</div>
	</div>
</div>



<div class="modal fade" id="editId" tabindex="-1" role="dialog"
	aria-labelledby="exampleModalCenterTitle" aria-hidden="true">
	<div class="modal-dialog modal-dialog-centered" role="document"
		style="width: 80%;">
		<div class="modal-content">
			<div class="modal-header">
				<h5 class="modal-title" id="exampleModalLongTitle"
					style="display: inline-block;">Chỉnh sửa Template</h5>
				<button type="button" class="close" data-dismiss="modal"
					aria-label="Close">
					<img style="width: 25px; height: 25px;"
						src="<c:url value='/resources/images/dongform.png' />"
						title="Đóng" />
				</button>
			</div>
			<div class="modal-body" id="editForm" style="height: 750px!important;"></div>
			<div class="modal-footer" style="padding: 5px;">
				<button type="button" onclick="" class="btn btn-warning"
					data-dismiss="modal" aria-label="Close" style="float: right;">
					<span class="glyphicon glyphicon-remove btn-boot"
						style="margin-right: 2px;"></span><span class="btn-boot">
						ĐÓNG</span>
				</button>
				<button type="button" onclick="savePassport();"
					class="btn btn-success" style="float: right; margin-right: 10px;">
					<span class="glyphicon glyphicon-ok btn-boot"
						style="margin-right: 2px;"></span><span class="btn-boot">
						LƯU</span>
				</button>

			</div>
		</div>
	</div>
</div>
<div class="col-sm-12">
		<div style="display: flex; flex: 0 auto; justify-content: center;">
			<div class="waitingWhenDoneWaiting"
				style="display: block; position: fixed; bottom: -5px; border-radius: 8px; border: 2px solid; background-color: rgb(255, 255, 255); z-index: +1000; border-color: #0c537f #0c537f transparent #0c537f;">
				<div style="margin: 10px">
					<button type="button" onclick="showPassportForm();" name="approve"
						class="btn btn-success">
						<span class="glyphicon glyphicon-ok"></span> Thêm Passport
					</button>
				</div>
			</div>
		</div>
	</div>
<script type="text/javascript">
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
								$('#pageNo').val(page);
								document.forms["formData"].action = '${loadFormQueueUrl}';
								document.forms["formData"].submit();
							}
						}
					});
	function doApplyFilter() {

		var yearIssue = $('#yearIssueSeach').val();
		var err_msg = '';
		 var text = /^[0-9]+$/;
	        if ((!text.test(yearIssue))) {
	            err_msg = 'Hãy nhập năm là chữ số';
				$('#ajax-load-qa').hide();
	        }

	        if (yearIssue.length != 4 && (yearIssue != "")) {
	        	 err_msg = 'Năm không đúng, vui lòng kiểm tra lại';
					$('#ajax-load-qa').hide();
	        }
	        var current_year=new Date().getFullYear();
	        if((yearIssue < 1920) || (yearIssue > current_year))
	            {
	           
	            err_msg = 'Chỉ được nhập vào năm từ 1920 đến năm hiện tại';
	            $('#ajax-load-qa').hide();
	            }
	    
	 if (err_msg != '') {
			$.notify(err_msg, {
				globalPosition : 'bottom left',
				className : 'warn',
			});
			return;
		} 
		document.forms["formData"].action = '${loadFormQueueUrl}';
		document.forms["formData"].submit();
	}
	/* $("#dateFrom").datepicker({
		showOn : "button",
		buttonImage : "<c:url value="/resources/images/calendar.gif" />",
		buttonImageOnly : true,
		changeMonth : true,
		changeYear : true,
		showSecond : true,
		controlType : 'select',
		dateFormat : 'dd-MM-yy',
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
		dateFormat : 'dd-MM-yy',
		yearRange : "-100:+10"
	}).keyup(function(e) {
		if (e.keyCode == 8 || e.keyCode == 46) {
			$.datepicker._clearDate(this);
		}
	}); */
</script>

<script type="text/javascript">
	var idConfig = '';
	function chiTietHS(templatePassportId) {
		//idConfig = id;+
		$('#checkSiteForm').html("");
		var url = '${chitietTemplate}/' + templatePassportId;
		$('#ajax-load-qa').css('display', 'block');
		$.ajax({
			url : url,
			cache : false,
			type : "POST",
			success : function(data) {
				/* 	alert(idConfig); */
				$('#checkSiteForm').html(data);
				$('#checkSiteId').modal();
				$('#ajax-load-qa').css('display', 'none');
			}
		});
	}
</script>


<script type="text/javascript">
	var idConfig = '';
	function showPassportForm() {
		//idConfig = id;
		$('#addPassportForm').html("");
		var url = '${getAddPassportForm}';
		$('#ajax-load-qa').css('display', 'block');
		var year = $('#yearIssuaSeach').val();
		$.ajax({
			url : url,
			cache : false,
			type : "POST",
			success : function(data) {
				/* 	alert(idConfig); */
				$('#addPassportForm').html(data);
				$('#addPassport').modal();
				$('#ajax-load-qa').css('display', 'none');
			},
			error : function(e) {
				$('#ajax-load-qa').hide();
				$.notify('Load Data thất bại', {
					globalPosition : 'bottom left',
					className : 'success',
				});

			}
		});
	}
</script>
<script type="text/javascript">
	var idConfig = '';
	function editTemplate(id) {
		$('#editForm').html("");
		var url = '${editForm}/' + id;
		$('#ajax-load-qa').css('display', 'block');
		idConfig = id;
		$.ajax({
			url : url,
			cache : false,
			type : "POST",
			success : function(data) {
				$('#editForm').html(data);
				$('#editId').modal();
				$('#ajax-load-qa').css('display', 'none');
			}
		});
	}

	

	$('input[id^="status"]').change(function(e) {
		$('#ajax-load-qa').css('display', 'block');
		$('#ajax-load-qa').show();
		var url = '${changeStatus}/' + $(this).val();
		var status = '';
		if ($(this).is(":checked")) {
			status = 'true';
		} else {
			status = 'false';
		}
		var obj = {
			"status" : status
		};
		$.ajax({
			url : url,
			data : JSON.stringify(obj),
			dataType : 'json',
			contentType : 'application/json; charset=utf-8',
			method : 'POST',
			success : function(data) {
				if (data == 1) {
					$('#ajax-load-qa').hide();
					$.notify('Thay đổi trạng thái thành công', {
						globalPosition : 'bottom left',
						className : 'success',
					});
				} else {
					$('#ajax-load-qa').hide();

					$.notify('Thay đổi trạng thái thất bại', {
						globalPosition : 'bottom left',
						className : 'warn',
					});
				}
			},
			error : function(e) {
				$('#ajax-load-qa').hide();

				$.notify('Có lỗi ', {
					globalPosition : 'bottom left',
					className : 'warn',
				});
			}
		});
	});
</script>
<script>
function findPassportTypeSeach(nation) {
	var url = '${getlistPassport}/' + id;
	$('#ajax-load-qa').css('display', 'block');
	idConfig = id;
	$.ajax({
		url : url,
		cache : false,
		type : "POST",
		success : function(data) {
			$('#editForm').html(data);
			$('#editId').modal();
			$('#ajax-load-qa').css('display', 'none');
		}
	});
}



</script>
</form:form>
</div>