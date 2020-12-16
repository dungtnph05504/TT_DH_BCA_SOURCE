<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<c:url var="prodUrl" value="/servlet/investigation/startinvestigation" />
<c:url var="newUrl" value="/servlet/investigation/newInvestigation" />
<c:url var="pendingJobUrl" value="/servlet/investigation/pendingJobList" />
<c:url var="investigationJobUrl"
	value="/servlet/investigation/investigation" />
<c:url var="invesProcessUrl" value="/servlet/storage/searchPassport" />
<c:url var="nextProcessUrl"
	value="/servlet/investionProcess/invesProcess" />
<c:url var="showMessageUrl"
	value="/servlet/investionProcess/showMessage" />
<c:url var="testOfficeIdUrl"
	value="/servlet/investionProcess/testOfficeId" />
<c:url var="getInfoPassportUrl" value="/servlet/storage/getInfoPassport" />
<c:url var="getDataTableUrl" value="/servlet/storage/getDataTable" />
<%-- <form:form commandName="jobList" id="form" name="form" > --%>
<style>
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

<!--
-->
.jconfirm .jconfirm-box div.jconfirm-content-pane .jconfirm-content {
	white-space: pre-wrap;
}

.cls-mg-bot {
	margin-top: 10px;
}

.form-group {
	display: inline !important;
}

div.SumoSelect {
	width: 100%;
}
</style>
<div class="content-item-title">
	<div class="oplog-title__txt">Tra cứu hộ chiếu</div>
</div>
<div class="content-item-main">
	<form:form modelAttribute="formData" name="formData" autocomplete="off"
		id="formData">
		<c:if test="${not empty requestScope.Errors}">
			<div
				style="color: Red; background-color: White; border-style: solid; border-color: Red; border-width: thin;">
				<c:forEach items="${requestScope.Errors}" var="errorMessage">
					<li>${errorMessage}</li>
				</c:forEach>

			</div>
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


		<div class="content-item-information">

			<div class="form-group form-profile pad-bottom-15">
				<div class="col-sm-4">
					<label class="col-sm-4 control-label text-right pad-top-10">Loại
						hộ chiếu</label>
					<div class="col-sm-8 pad-top-10 none-pad-left">
						<form:select path="passportType" id="passportType"
							name="passportType" class="cls-passport1"
							style="font-family: Arial; font-size: 12px;width: 100%;">
							<form:option value="" style="margin-right: 5px;" label="Tất cả" />
							<form:option value="HAJI-P" style="margin-right: 5px;"
								label="HaJJ Passport" />
							<form:option value="MRP-P" style="margin-right: 5px;"
								label="Machine Readable Passport" />
							<form:option value="P" style="margin-right: 5px;"
								label="Phổ thông" />
							<form:option value="PD" style="margin-right: 5px;"
								label="Ngoại giao" />
							<form:option value="PO" style="margin-right: 5px;"
								label="Công vụ" />
							<form:option value="PR" style="margin-right: 5px;" label="MRCTDR" />
							<form:option value="PS" style="margin-right: 5px;" label="MRCTDS" />
						</form:select>
					</div>
				</div>
				<div class="col-sm-4">
					<label class="col-sm-4 control-label text-right pad-top-10">Số
						hộ chiếu</label>
					<div class="col-sm-8 pad-top-10 none-pad-left">
						<form:input path="passportNo" id="passportNo" type="text"
							style="width: 100%;" />
					</div>
					
				</div>
				<div class="col-sm-4 form-group">
					<label class="col-sm-4 control-label text-right pad-top-10">Trạng
						thái</label>
					<div class="col-sm-8 pad-top-10 none-pad-left">
						<form:select path="stageLoad" id="stageLoad" name="stageLoad"
							class="cls-satge1"
							style="font-family: Arial; font-size: 12px;width: 100%;">
							<form:option value="" style="margin-right: 5px;" label="Tất cả" />
							<form:option value="1" style="margin-right: 5px;"
								label="Có hiệu lực" />
							<form:option value="2" style="margin-right: 5px;" label="Hủy" />
							<form:option value="3" style="margin-right: 5px;" label="Hỏng" />
							<form:option value="4" style="margin-right: 5px;" label="Hết hạn" />
							<form:option value="5" style="margin-right: 5px;"
								label="Khởi tạo" />
							<form:option value="6" style="margin-right: 5px;"
								label="Phát hành" />
							<form:option value="7" style="margin-right: 5px;" label="Mất" />
							<form:option value="8" style="margin-right: 5px;"
								label="Đã đóng gói" />
							<form:option value="9" style="margin-right: 5px;"
								label="Hoàn thành in" />
						</form:select>
					</div>
				</div>
				<div class="col-sm-4 form-group">
					<label class="col-sm-4 control-label text-right">Ngày cấp</label>
					<div class="col-sm-8 none-pad-left">
						<form:input path="createDate" id="createDate" type="text"
							style="width: 100%;" />
					</div>
				</div>
				
				<div class="col-sm-4 form-group">
					<label class="col-sm-4 control-label text-right">Ngày hết
						hạn</label>
					<div class="col-sm-8 none-pad-left">
						<form:input path="endDate" id="endDate" type="text"
							style="width: 100%;" />
					</div>
				</div>
				<div class="col-sm-4 form-group">
					<label class="col-sm-4 control-label text-right ">Họ và tên</label>
					<div class="col-sm-8 none-pad-left">
						<form:input path="name" id="name" type="text" style="width: 100%;" />
					</div>
				</div>
				<div class="col-sm-12" style="padding-right: 30px;">
					<button onclick="doApplyFilter();" type="button"
						style="float: right;" class="btn_small btn-success"
						id="getDataTable">
						<span class="glyphicon glyphicon-search"></span> Tìm kiếm
					</button>
				</div>
			</div>




			<div class="form-group">
				<div class="col-sm-12 none-pad-right none-pad-left"
					style="margin-bottom: 50px;">
					<!--<fieldset class="scheduler-border">
							<legend class="scheduler-border">Hồ sơ cần xử lý</legend>-->
					<div style="height: 400px; overflow: auto;">
						<table id="dtBasicExample"
							class="table table-bordered table-sm table-hover" cellspacing="0"
							width="100%">
							<thead>
								<tr>
									<th class="th-sm" style="width: 40px;">STT</th>
									<th class="th-sm">Số HC</th>
									<th class="th-sm">Loại HC</th>
									<th class="th-sm">Là HC điện tử</th>
									<th class="th-sm">Ngày cấp</th>
									<th class="th-sm">Ngày hết hiệu lực</th>
									<th class="th-sm">Nơi cấp</th>
									<th class="th-sm">Họ và tên</th>
									<th class="th-sm">Trạng thái</th>
									<th class="th-sm">Ngày tạo</th>
									<th class="th-sm">Thao tác</th>
								</tr>
							</thead>
							
							<c:if test="${empty dsXuLyA}">

								<tbody class="e-not-found ng-scope">
									<tr>
										<td colspan="11" style="height: 362px">
											<div class="e-not-found-center">
												<div class="e-not-found-title" >Không tìm thấy kết quả</div>
												<div class="e-not-found-title-child">Hãy thử tìm kiếm
													hoặc sử dụng bộ lọc khác</div>
											</div>
										</td>
									</tr>
								</tbody>


							</c:if>
						</table>


					</div>

					<table class="table e-grid-table e-border">
						<tfoot>
							<tr>
								<th>
										<div class="e-page-rigth">
											<ul style="float: right;" class="pagination" id="pagination"></ul>
										</div>
										<input type="hidden" name="pageNo" id="pageNo" />
									<div class="e-page-left" style="font-weight: normal;">
										Hiển thị ${startIndex} đến ${endIndex} của ${totalRecord} kết
										quả</div></th>
							</tr>
						</tfoot>
					</table>
					<!--</fieldset>-->

				</div>
			</div>
		</div>

		<!-- Message lưu hồ sơ -->
		<div class="modal fade" id="idChiTiet" tabindex="-1" role="dialog"
			aria-labelledby="exampleModalCenterTitle" aria-hidden="true">
			<div class="modal-dialog modal-dialog-centered" role="document"
				style="width: 1200px;">
				<div class="modal-content">
					<div class="modal-header">
						<h5 class="modal-title" id="exampleModalLongTitle"
							style="display: inline-block;">CHI TIẾT HỘ CHIẾU</h5>
						<button type="button" class="close" data-dismiss="modal"
							aria-label="Close">
							<img style="width: 25px; height: 25px;"
								src="<c:url value='/resources/images/dongform.png' />"
								title="Đóng" />
						</button>
					</div>
					<div class="modal-body" id="divChiTiet" style="height: 800px;">

					</div>
					<div class="modal-footer" style="padding: 5px;">
						<button type="button" class="btn btn-warning" data-dismiss="modal"
							aria-label="Close" style="float: right;">
							<span class="glyphicon glyphicon-remove"></span> Đóng
						</button>
					</div>
				</div>
			</div>
		</div>
		<!-- ---------------------------------------------------------------------------->
		<!--<div style="display: flex;flex: 0 auto;justify-content: center;">
	<div class="waitingWhenDoneWaiting" style="display:block; position: fixed; bottom:-5px; border-radius: 8px; border: 2px solid; background-color: rgb(255, 255, 255); z-index: +1000;border-color: #2a7044 #2a7044 transparent #2a7044;">
		<div style="margin: 10px"> 
			<button type="button"  onclick="processHS();" name="approve" class="btn btn-success">
				<span class="glyphicon glyphicon-list-alt"></span> Xử lý hồ sơ
			</button>
		</div>
	</div>	
	</div>-->
		<div id="ajax-load-qa"></div>
		<div id="dialog-confirm"></div>

		<script type="text/javascript">
			$(document).ready(function() {
				$('.cls-passport1').SumoSelect();
				$('.cls-satge1').SumoSelect();
			});
			var pattern = /^([0-2][0-9]|(3)[0-1])(\/)(((0)[0-9])|((1)[0-2]))(\/)\d{4}$/;
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
										doApplyFilter(); 
									}
								}
							});
			function doSubmitSave(form) {

				form.action = '<c:url value="/servlet/investigation/search" />';
				form.submit();
			}
		</script>

		<script type="text/javascript">
			var check = '';
			function processHS() {
				var url = '${showMessageUrl}';
				var arrJob = '';
				var arrWfj = '';
				var arrChke = document.getElementsByName("selectedJobIds");
				//alert(arrChke.length);
				for (var i = 0; i < arrChke.length; i++) {
					if (arrChke[i].checked == true) {
						//alert($('#idshow_' + arrChke[i].value).val());
						arrJob += $('#idshow_' + arrChke[i].value).val() + ",";
						arrWfj += arrChke[i].value + ",";
					}
				}
				var mess = checkRong();
				if (mess != '') {
					$.notify(mess, {
						globalPosition : 'bottom left',
						className : 'warn',
					});
					return;
				}
				testOfficeId(arrWfj);
				//alert(check);
				if (check != '') {
					var mes = 'Hồ sơ ' + check + ' đã được cán bộ khác xử lý!';
					$.notify(mes, {
						globalPosition : 'bottom left',
						className : 'warn',
					});
					return;
				}
				$.ajax({
					url : url,
					cache : false,
					type : "POST",
					data : {
						arrJob : arrJob
					},
					success : function(data) {
						if (data == 'Y') {
							btnXuLyHoSo('N');
						} else {
							$("#messageLHS").modal();
						}
					}
				});
			}
		

		

			function chiTietHS(val) {
				$('#divChiTiet').html("");
				var url = '${getInfoPassportUrl}/' + val;
				$('#ajax-load-qa').css('display', 'block');
				$.ajax({
					url : url,
					cache : false,
					type : "POST",
					success : function(data) {
						$('#divChiTiet').html(data);
						$('#ajax-load-qa').css('display', 'none');
					}
				});
			}

			function testOfficeId(arrJob) {
				var url = '${testOfficeIdUrl}';
				$.ajax({
					url : url,
					cache : false,
					async : false,
					type : "POST",
					data : {
						arrJob : arrJob
					},
					success : function(data) {
						check = data;
					}
				});
			}

			function btnXuLyHoSo(value) {

				/* $.confirm({
				    title: 'Thông báo',
				    content: "<img style=\"margin-bottom: 5px;\" src=\"/eppcentral/resources/images/question-1.png\">" + ' Bạn có muốn tra cứu tiếp hồ sơ',
				    buttons: {
				        "Có": function () {
							document.forms["formData"].action = '<c:url value="/servlet/investionProcess/invesProcess" />';
							document.forms["formData"].submit();
				        },
				        "Không": function () {
				        	//return false;
				        }		       
				    }
				}) */
				document.forms["formData"].action = '${nextProcessUrl}/'
						+ value;
				document.forms["formData"].submit();
			}
			
			
			function doApplyPagination(){
				var issD = $('#createDate').val();
				var expD = $('#endDate').val();
				var err = '';
				if (issD.trim() != '' && !issD.trim().match(pattern)) {
					err = 'Định dạng ngày cấp không đúng.'
				} else if (expD.trim() != '' && !expD.trim().match(pattern)) {
					err = 'Định dạng ngày hết hạn không đúng.'
				}
				if (err != '') {
					$.notify(err, {
						globalPosition : 'bottom left',
						className : 'warn',
					});
					return;
				}
				//dũng sửa code load form bằng ajax
				var passportType = $('#passportType').val();
				var passportNo = $('#passportNo').val();
				var stageLoad = $('#stageLoad').val();
				var createDate = $('#createDate').val();
				var endDate = $('#endDate').val();
				var name = $('#name').val();
				var pageNo = $("#pageNo").val();
				$('#ajax-load-qa').css('display', 'block');
				$('#ajax-load-qa').show();
				var investigationAssignmentData = {
						"passportType": passportType,
						"passportNo" : passportNo,
						"stageLoad" : stageLoad,
						"createDate" : createDate,
						"endDate" : endDate,
						 "name" : name,
						 "pageNo" : pageNo
						 };
				var url = '${getDataTableUrl}/';
				 $.ajax({
					url : url,
					data : JSON.stringify(investigationAssignmentData),  
					dataType : 'json',
					contentType : 'application/json; charset=utf-8',
					method: 'POST',
					success : function(data) {
						$('#dtBasicExample > tbody').html(data.dataHtml); 
						$('#formData > div.content-item-information > div:nth-child(2) > div > table > tfoot > tr > th > div.e-page-left').text("Hiển thị "+data.startIndex+" đến "+data.endIndex+" của "+data.totalRecord+" kết quả");
							var totalPages = data.totalPage;
							if (data.dataHtml= "") {
								$('#dtBasicExample').html("<tbody class=\"e-not-found ng-scope\"><tr><td colspan=\"11\" style=\"height: 362px\"><div class=\"e-not-found-center\"><div class=\"e-not-found-title\" >Không tìm thấy kết quả</div><div class=\"e-not-found-title-child\">Hãy thử tìm kiếm hoặc sử dụng bộ lọc khác</div></div></td></tr></tbody>"); 
							}
							var currentPage = data.pageNo;
							var pageSize = data.pageSize; 
						/* 	alert(currentPage); */
						$('#pagination').twbsPagination('destroy');
						$('#pagination').twbsPagination({
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
														doApplyPagination(); 
													}
												} 
											});
						$('#ajax-load-qa').hide();
					},
					error : function(XMLHttpRequest, textStatus, errorThrown){
						$('#ajax-load-qa').hide();
					}
				});   
			}
			function doApplyFilter() {
				var issD = $('#createDate').val();
				var expD = $('#endDate').val();
				var err = '';
				if (issD.trim() != '' && !issD.trim().match(pattern)) {
					err = 'Định dạng ngày cấp không đúng.'
				} else if (expD.trim() != '' && !expD.trim().match(pattern)) {
					err = 'Định dạng ngày hết hạn không đúng.'
				}
				if (err != '') {
					$.notify(err, {
						globalPosition : 'bottom left',
						className : 'warn',
					});
					return;
				}
				//dũng sửa code load form bằng ajax
				var passportType = $('#passportType').val();
				var passportNo = $('#passportNo').val();
				var stageLoad = $('#stageLoad').val();
				var createDate = $('#createDate').val();
				var endDate = $('#endDate').val();
				var name = $('#name').val();
				var pageNo = "";
				$('#ajax-load-qa').css('display', 'block');
				$('#ajax-load-qa').show();
				var investigationAssignmentData = {
						"passportType": passportType,
						"passportNo" : passportNo,
						"stageLoad" : stageLoad,
						"createDate" : createDate,
						"endDate" : endDate,
						 "name" : name,
						 "pageNo" : pageNo
						 };
				var url = '${getDataTableUrl}/';
				 $.ajax({
					url : url,
					data : JSON.stringify(investigationAssignmentData),  
					dataType : 'json',
					contentType : 'application/json; charset=utf-8',
					method: 'POST',
					success : function(data) {
						$('#dtBasicExample > tbody').html(data.dataHtml); 
						$('#formData > div.content-item-information > div:nth-child(2) > div > table > tfoot > tr > th > div.e-page-left').text("Hiển thị "+data.startIndex+" đến "+data.endIndex+" của "+data.totalRecord+" kết quả");
							var totalPages = data.totalPage;
							if (data.dataHtml= "") {
								$('#dtBasicExample').html("<tbody class=\"e-not-found ng-scope\"><tr><td colspan=\"11\" style=\"height: 362px\"><div class=\"e-not-found-center\"><div class=\"e-not-found-title\" >Không tìm thấy kết quả</div><div class=\"e-not-found-title-child\">Hãy thử tìm kiếm hoặc sử dụng bộ lọc khác</div></div></td></tr></tbody>"); 
							}
							var currentPage = data.pageNo;
							var pageSize = data.pageSize; 
						/* 	alert(currentPage); */
						$('#pagination').twbsPagination('destroy');
						$('#pagination').twbsPagination({
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
														doApplyPagination(); 
													}
												} 
											});
						$('#ajax-load-qa').hide();
					},
					error : function(XMLHttpRequest, textStatus, errorThrown){
						$('#ajax-load-qa').hide();
					}
				});   
				
			 /* 	 document.forms["formData"].action = '<c:url value="/servlet/storage/searchPassport" />';
				document.forms["formData"].submit();   */
			}

			function doSubmitNew1(form) {
				$("#dialog-confirm").dialog('option', 'title',
						'Tình trạng công việc đang chờ xử lý');
				$("#dialog-confirm").html(
						"Có công việc đang chờ xử lý? Bạn có muốn tiếp tục?");
				$("#dialog-confirm").dialog('open');
			}

			function checkRong() {
				var kiemTra = false;
				var chkHoSo = document.getElementsByName("selectedJobIds");
				for (var i = 0; i < chkHoSo.length; i++) {
					if (chkHoSo[i].checked) {
						kiemTra = true;
						break;
					}
				}
				if (kiemTra) {
					return '';
				} else {
					return 'Bạn chưa chọn hồ sơ nào';
				}
			}

			//var stage = 0;
			$('#idCheckAll').change(function() {
				var total = document.getElementsByName("selectedJobIds");
				var root = document.getElementById("idCheckAll");
				for (var i = 0; i < total.length; i++) {
					if (total[i].disabled == true) {
						continue;
					}
					if (root.checked == true) {
						total[i].checked = true;
					} else {
						total[i].checked = false;
					}
				}
			});

			$('#createDate').mask("00/00/0000", {
				placeholder : "__/__/____"
			});

			$("#createDate").datepicker({
				//showOn : "button",
				//buttonImage : "<c:url value="/resources/images/calendar9.png" />",
				//buttonImageOnly : true,
				changeMonth : true,
				changeYear : true,
				showSecond : true,
				controlType : 'select',
				dateFormat : 'dd/mm/yy',
				yearRange : "-100:+10"
			}).keyup(function(e) {

			});

			//$('#createDate').datepicker().datepicker('setDate', new Date());
			$('#endDate').mask("00/00/0000", {
				placeholder : "__/__/____"
			});
			$("#endDate").datepicker({
				//showOn : "button",
				//buttonImage : "<c:url value="/resources/images/calendar9.png" />",
				//buttonImageOnly : true,
				changeMonth : true,
				changeYear : true,
				showSecond : true,
				controlType : 'select',
				dateFormat : 'dd/mm/yy',
				yearRange : "-100:+10"
			}).keyup(function(e) {

			})
		</script>

	</form:form>
</div>

