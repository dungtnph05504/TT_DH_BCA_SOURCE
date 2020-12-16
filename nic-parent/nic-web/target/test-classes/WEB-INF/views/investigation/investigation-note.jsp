<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>
<script type="text/javascript" src="<c:url value="/resources/js/SPID_Common.js" />"></script>
<script type="text/javascript" src="<c:url value="/resources/js/SPID_PortraitCapture.js" />"></script>
<c:url var="JobInvestigationConfirmUrl" value="/servlet/investigationConfirm/investigationConfirmList" />
<c:url var="searchUpdateUrl" value="/servlet/investigationConfirm/searchUpdate" />
<c:url var="detailInvesBUrl" value="/servlet/investigationConfirm/detailInvesB" />
<c:url var="getFacialByIdUrl" value="/servlet/investigationConfirm/getFacialById" />
<c:url var="getDocumentUrl" value="/servlet/investionProcess/getDocument" />
<c:url var="getInsertRecUrl" value="/servlet/investigationConfirm/getInsertRec" />
<c:url var="getPositionSignUrl" value="/servlet/investigationConfirm/getPositionSign" />
<c:url var="getPaymentSignUrl" value="/servlet/investigationConfirm/getPaymentSign" />
<c:url var="savePaymentByIdUrl" value="/servlet/investigationConfirm/savePaymentById" />
<c:url var="getCountArchiveCodeUrl" value="/servlet/investigationConfirm/getCountArchiveCode" />
<c:url var="detailTranBUrl" value="/servlet/investigationConfirm/detailTranB" />

<style>
.cls-mg-bot {
	margin-top: 10px;
}

.form-left {
	
	width: 80%;	
	padding-bottom: 50px;
	float: left;
	transition: width 0.5s;
	-webkit-transition: width 0.5s;
}
.form-right {
	width: 20%;	
	float: left;
	background: #f3f3f3;
	height: 700px;
	transition: width 0.5s;
	-webkit-transition: width 0.5s;
	overflow: hidden;
}

.clss-font {
	font-size: 12px !important;
}
.top-mag-10 {
	margin-top: 10px;
}

.opentb-2 {
}

.dataTables_length {
	display: none;
}
#ajax-load-qa {
	background: rgba(255, 255, 255, .5)
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
.colorred {
	color: red;
}
div#tbDanhSachHS_wrapper .col-sm-12 {
    padding: 0px;
}
div#tbDanhSachHS_wrapper th {
    width: auto !important;
}
.form-control{
	font-size: 12px;
	height: 24px;
}
.txt-right {
	text-align: right;
}
div#tbDanhSachHS_wrapper .dataTables_info{padding-left: 10px;}
.image-box1, .ep-image-box1, .ep-img-hc1{
	width: 180px;
	height: 198px;
}
</style>
<c:url var="passportUrl" value="/servlet/investigationConfirm/passportNoEdit" />
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
	<div id="idData"></div>
	
<div class="ov_hidden" style="margin-bottom: 30px;">

					
						<div class="form-right">
							<div class="col-md-12 col-sm-12" style="padding: 0px;">
								<div class="new-heading-2" style="font-size: 13px;">DANH SÁCH HỒ SƠ</div>
								<div class="col-md-12 col-sm-12">
									<input type="text" placeholder="Nhập số danh sách B" id="packageNumber" style="width: 100%;font-size: 12px;">
								</div>
								<div class="col-md-6 col-sm-6 top-mag-10">
									<input type="text" placeholder="Từ ngày" readonly="readonly" autocomplete="off" id="createDate" style="width: 100%;font-size: 12px;">
								</div>
								<div class="col-md-6 col-sm-6 top-mag-10">
									<input type="text" placeholder="Đến ngày" readonly="readonly" autocomplete="off" id="endDate" style="width: 100%;font-size: 12px;">
								</div>								
								<div class="col-md-6 col-sm-6 top-mag-10">
									<button type="button" onclick="doSubmitSave();" class="btn_small btn-success">
										  <span class="glyphicon glyphicon-search"></span> Tìm kiếm
									</button>	
								</div>
								<div class="col-md-12 col-sm-12" style="padding: 0px;max-height: 400px;overflow: auto;" id="div_danhsach">
									<table id="tbDanhSachHS" class="table table-bordered table-sm table-hover" cellspacing="0" width="100%">
										  <thead>
										    <tr>										     
										      <th class="th-sm">Mã hồ sơ
										
										      </th>
										      <th class="th-sm">Họ tên
										
										      </th>											      									     
										    </tr>
										  </thead>
										  <!--<c:if test="${not empty dsHoSo}">-->
											  <tbody>
											    <c:forEach items="${dsHoSo}" var="item">
												    <tr style="min-width: 100px;">
												      	<td></td>
												      	<td></td>												      											      	
												    </tr>
											    </c:forEach>
											  </tbody>
										  <!--</c:if>-->
										</table>
										<c:if test="${empty dsHoSo}">
										  	<div class="style-no-record opentb-2">Không tìm thấy kết quả</div>
										</c:if>
								</div>
							</div>
						</div>						
						<div class="form-left">
							<div class="new-heading-2">CẬP NHẬT THÔNG TIN HỒ SƠ</div>
							<div>
								<div class="form-group">
									<label class="col-sm-2 control-label txt-right">Mã hồ sơ</label>
									<div class="col-sm-4">
										<input type="text" id="maHoSo" readonly="readonly" class="form-control" placeholder="Mã hồ sơ">
									</div>
									<label class="col-sm-2 control-label txt-right">Số CMND/CCCD</label>
									<div class="col-sm-4">
										<input type="text" id="chungMinhND" class="form-control" placeholder="Nhập số CMND/CCCD">
									</div>
								</div>
								<div class="form-group">
									<label class="col-sm-2 control-label txt-right">Họ và tên</label>
									<div class="col-sm-4">
										<input type="text" id="hoTen" class="form-control" placeholder="Nhập đầy đủ họ tên tiếng Việt có dấu">
									</div>
									<label class="col-sm-2 control-label txt-right">Ngày sinh</label>
									<div class="col-sm-4">
										<input type="text" class="form-control" id="dateOfBirth" placeholder="Đủ ngày, tháng, năm sinh">
									</div>
								</div>
								<div class="form-group">
									<label class="col-sm-2 control-label txt-right">Địa chỉ</label>
									<div class="col-sm-10">
										<input type="text" id="diaChi" class="form-control" placeholder="Nhập số nhà, tên đường, tổ/xóm, khu phố/ thôn, tên phường/ xã">
									</div>					
								</div>
							</div>
							<div>
								<fieldset>
									<legend>Thông tin sinh trắc</legend>
									<div class="col-sm-12">
										<div class="ep-box">
											<div class="e-label-title">
												<span>Nhập ảnh</span>
											</div>
										</div>
										<div class="ep-table">
											<table class="table table-bordered">
												<thead>
													<tr>
														<th>Ảnh chụp</th>
														<th>Ảnh in</th>
														<th>Ảnh mã hóa</th>
													</tr>
												</thead>
												<tbody>
													<tr>
														<td style="text-align: center;">
															<div class="cm-image fw">
																<div id="anhChup" class="image-box ep-image-box ep-img-hc ep-cus-height">
																	<span class="icon">
																		<i class="glyphicon glyphicon-user"></i>
																	</span>
																	
																</div>
															</div>
														</td>
														<td style="text-align: center;">
															<div class="cm-image fw">
																<div id="anhIn" class="image-box ep-image-box ep-img-hc ep-cus-height">
																	<span class="icon">
																		<i class="glyphicon glyphicon-user"></i>
																	</span>
																	
																</div>
															</div>
														</td>
														<td style="text-align: center;">
															<div class="cm-image fw">
																<div id="anhMaHoa" class="image-box ep-image-box ep-img-hc ep-cus-height">
																	<span class="icon">
																		<i class="glyphicon glyphicon-user"></i>
																	</span>
																	
																</div>
															</div>
														</td>
													</tr>
												</tbody>
											</table>
										</div>
									</div>
									<div class="col-sm-12">
										<div class="ep-box">
											<div class="e-label-title">
												<span>Chụp vân tay</span>
											</div>
										</div>
										<div class="ep-table">
											<table class="table table-bordered">
												<tbody>
													<tr >
														<th style="text-align: center;">Ngón cái phải</th>
														<th style="text-align: center;">Ngón trỏ phải</th>
														<th style="text-align: center;">Ngón giữa phải</th>
														<th style="text-align: center;">Ngón áp út phải</th>
														<th style="text-align: center;">Ngón út phải</th>
													</tr>
													<tr>
														<td class="order-count" style="text-align: center;">
															<div class="cm-image fw">
																<div id="vantay01" class="image-box1 ep-image-box1 ep-img-hc1">
																	<span class="icon">
																		<i class="glyphicon glyphicon-pencil"></i>
																		
																	</span>
																</div>
															</div>
														</td>
														<td class="gross-revenue" style="text-align: center;">
															<div class="cm-image fw">
																<div id="vantay02" class="image-box1 ep-image-box1 ep-img-hc1">
																	<span class="icon">
																		<i class="glyphicon glyphicon-pencil"></i>
																		<img alt="" src="" />
																	</span>
																</div>
															</div>
														</td>
														<td class="net-revenue" style="text-align: center;">
															<div class="cm-image fw">
																<div id="vantay03" class="image-box1 ep-image-box1 ep-img-hc1">
																	<span class="icon">
																		<i class="glyphicon glyphicon-pencil"></i>
																		<img alt="" src="" />
																	</span>
																</div>
															</div>
														</td>
														<td class="net-revenue" style="text-align: center;">
															<div class="cm-image fw">
																<div id="vantay04" class="image-box1 ep-image-box1 ep-img-hc1">
																	<span class="icon">
																		<i class="glyphicon glyphicon-pencil"></i>
																		<img alt="" src="" />
																	</span>
																</div>
															</div>
														</td>
														<td class="net-revenue" style="text-align: center;">
															<div class="cm-image fw">
																<div id="vantay05" class="image-box1 ep-image-box1 ep-img-hc1">
																	<span class="icon">
																		<i class="glyphicon glyphicon-pencil"></i>
																		<img alt="" src="" />
																	</span>
																</div>
															</div>
														</td>
													</tr>
													<tr>
														<th style="text-align: center;">Ngón cái trái</th>
														<th style="text-align: center;">Ngón trỏ trái</th>
														<th style="text-align: center;">Ngón giữa trái</th>
														<th style="text-align: center;">Ngón áp út trái</th>
														<th style="text-align: center;">Ngón út trái</th>
													</tr>
													<tr>
														<td class="order-count" style="text-align: center;">
															<div class="cm-image fw">
																<div id="vantay06" class="image-box1 ep-image-box1 ep-img-hc1">
																	<span class="icon">
																		<i class="glyphicon glyphicon-pencil"></i>
																		<img alt="" src="" />
																	</span>
																</div>
															</div>
														</td>
														<td class="gross-revenue" style="text-align: center;">
															<div class="cm-image fw">
																<div id="vantay07" class="image-box1 ep-image-box1 ep-img-hc1">
																	<span class="icon">
																		<i class="glyphicon glyphicon-pencil"></i>
																		<img alt="" src="" />
																	</span>
																</div>
															</div>
														</td>
														<td class="net-revenue" style="text-align: center;">
															<div class="cm-image fw">
																<div id="vantay08" class="image-box1 ep-image-box1 ep-img-hc1">
																	<span class="icon">
																		<i class="glyphicon glyphicon-pencil"></i>
																		<img alt="" src="" />
																	</span>
																</div>
															</div>
														</td>
														<td class="net-revenue" style="text-align: center;">
															<div class="cm-image fw">
																<div id="vantay09" class="image-box1 ep-image-box1 ep-img-hc1">
																	<span class="icon">
																		<i class="glyphicon glyphicon-pencil"></i>
																		<img alt="" src="" />
																	</span>
																</div>
															</div>
														</td>
														<td class="net-revenue" style="text-align: center;">
															<div class="cm-image fw">
																<div id="vantay10" class="image-box1 ep-image-box1 ep-img-hc1">
																	<span class="icon">
																		<i class="glyphicon glyphicon-pencil"></i>
																		<img alt="" src="" />
																	</span>
																</div>
															</div>
														</td>
													</tr>
													
												</tbody>
											</table>
										</div>
									</div>
								</fieldset>
							</div>
							
								

							
							<div id="ajax-load-qa"></div>
							
							
							
						</div>

						<!-- Model tài liệu -->
						<div class="modal fade" id="taiLieuDK" tabindex="-1" role="dialog" aria-labelledby="exampleModalCenterTitle" aria-hidden="true" >
						  <div class="modal-dialog modal-dialog-centered" role="document" style="width: 900px">
						    <div class="modal-content">
						      <div class="modal-header">
						        <h5 class="modal-title" id="exampleModalLongTitle" style="display: inline-block;">TÀI LIỆU ĐÍNH KÈM</h5>
						        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
						          		<img style="width: 25px;height: 25px;" src="<c:url value='/resources/images/dongform.png' />" title="Đóng" />
						        </button>
						      </div>							      
						      <div class="modal-body" style="height: 500px;overflow: auto;padding-left: 20px;" id="idTaiLieu">
						       		
						       		
						      </div>
						      <div class="modal-footer" style="padding: 5px;">
						       		<button type="button" class="btn btn-warning" data-dismiss="modal" aria-label="Close" style="float: right;">
						       			<span class="glyphicon glyphicon-remove btn-boot" style="margin-right: 2px;"></span ><span class="btn-boot"> Đóng</span>	
						       		</button>
						       		
						      </div>
						    </div>
						  </div>
						</div>	
						<!-- -------------------------------------------------------------------------- -->
						
						<!-- Model phí bổ sung -->
						<div class="modal fade" id="thuPhiBS" tabindex="-1" role="dialog" aria-labelledby="exampleModalCenterTitle" aria-hidden="true" >
						  <div class="modal-dialog modal-dialog-centered" role="document" style="width: 800px">
						    <div class="modal-content">
						      <div class="modal-header">
						        <h5 class="modal-title" id="exampleModalLongTitle" style="display: inline-block;">THU PHÍ BỔ SUNG</h5>
						        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
						          		<img style="width: 25px;height: 25px;" src="<c:url value='/resources/images/dongform.png' />" title="Đóng" />
						        </button>
						      </div>	
						      <div class="modal-body" style="height: 350px;overflow: auto;padding: 0;" id="idPhiBS">
						      </div>						      
						      <div class="modal-footer" style="padding: 5px;">
						       		<button type="button" onclick="savePayment();" style="float: right;" data-dismiss="modal" aria-label="Close" class="btn btn-success">Chọn</button>				
						      </div>
						    </div>
						  </div>
						</div>	
						

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
				
	<div style="display: flex;flex: 0 auto;justify-content: center;">
	<div class="waitingWhenDoneWaiting" style="display:block; position: fixed; bottom:-5px; border-radius: 8px; border: 2px solid; background-color: rgb(255, 255, 255); z-index: +1000;border-color: #2a7044 #2a7044 transparent #2a7044;">
		<div style="margin: 10px"> 
			<button type="button"  onclick="" id="imgFacialId" name="approve" class="btn btn-success">
				<span class="glyphicon glyphicon-camera btn-boot" style="margin-right: 2px;"></span ><span class="btn-boot"> Chụp ảnh</span>	
			</button>
			<button type="button"  onclick="" id="imgFingerId" name="approve" class="btn btn-success">
				<span class="glyphicon glyphicon-camera btn-boot" style="margin-right: 2px;"></span ><span class="btn-boot"> Chụp vân tay</span>	
			</button>
			<button type="button"  onclick="" id="btnUpdate" name="approve" class="btn btn-success">
				<span class="glyphicon glyphicon-refresh btn-boot" style="margin-right: 2px;"></span ><span class="btn-boot"> Cập nhật</span>	
			</button>
			<button type="button"  onclick="" id="btnRemove" name="approve" class="btn btn-success">
				<span class="glyphicon glyphicon-remove btn-boot" style="margin-right: 2px;"></span ><span class="btn-boot"> Hủy bỏ</span>	
			</button>
		</div>
	</div>	
	</div>
	<div id="infoDialog" style="display: none;">
	</div>
	
	<!--<div class="waitingWhenDoneWaiting" style="display:block; position: fixed; bottom:5px; right:10px; border-radius: 5px; border: 1px solid gray; background-color: rgb(255, 255, 255); z-index: +1000">
			<div style="margin: 10px"> 
				<button type="button"  onclick="openBoxReject();" name="approve" class="btn btn-primary">
					  <span class="glyphicon glyphicon-list-alt"></span> Tạo danh sách
				</button>
				
			</div>
	</div>-->
	

	
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
	
	var tbDanhSach = $('#tbDanhSachHS').DataTable({
		"ordering": false,
		"pagingType": "simple"
	});
	var arrTranId = [];
	var danhSachB = '';
	var DOC_ID = 'null';
	var code = '';
	var stt = '';
	var noiTra = '';
	var idBefore = '';
	/*var totalPages = ${totalPage};
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
					document.forms["formData"].action = '${JobInvestigationConfirmUrl}';  
					document.forms["formData"].submit();  
				}
			}
		});*/
	function historyEventDataSmark_clicked(itemTriggered){	 
		//info = info.replace(/\n/g,"<br/>");
		$("#infoDialog").dialog('option', 'title', "Log Data");
		$("#infoDialog").text(itemTriggered);
		$("#infoDialog").dialog('open');
	}
		
		function inDeXuat(){
			window.print();
		}
		
		function savePayment(){		
			var payArray = {};
			var chkPay = document.getElementsByName("payChked");
			for(var i = 0; i < chkPay.length; i++){
				if(chkPay[i].checked){
					payArray[chkPay[i].value] = "T";
				}else{
					payArray[chkPay[i].value] = "F";
				}
			}			
			/*if($('#phiTheoMa').text() != ''){
				payArray['JsonPC'] = $( "#idMaPhiChinh option:selected" ).text();
				payArray['JsonSum'] = $('#phiTheoMa').text();
				payArray['JsoBanDau'] = $('#maBanDau').text();
			}*/
			var url = '${savePaymentByIdUrl}/' + DOC_ID;
			$.ajax({
				url : url,
				type: "POST",
				data : JSON.stringify(payArray),
				contentType : 'application/json',
				success : function(data) {
								
				}
			});
			//$('#thuPhiBS').hide();
		}
		
		function showDocument(){
			$('#idTaiLieu').html("");
			var url = '${getDocumentUrl}/' + DOC_ID;
			$('#ajax-load-qa').css('display', 'block');

			$.ajax({
				url : url,
				cache: false,
				type: "POST",
				success : function(data) {
					if(data != ''){
						$('#idTaiLieu').html(data);
					}else{
						$('#idTaiLieu').html("<div>Không có tài liệu.</div>");
					}
					$('#ajax-load-qa').css('display', 'none');				
				}
			});
		}	
		
		function showPayment(value){
			$('#idPhiBS').html("");
			var url = '${getPaymentSignUrl}/' + value;
			$('#ajax-load-qa').css('display', 'block');

			$.ajax({
				url : url,
				cache: false,
				type: "POST",
				success : function(data) {
					$('#idPhiBS').html(data);					
					$('#ajax-load-qa').css('display', 'none');				
				}
			});
		}	
		
		
	$(function(){
		
		
		$("#createDate").datepicker({
			//showOn : "button",
			//buttonImage : "",
			//buttonImageOnly : true,
			changeMonth : true,
			changeYear : true,
			showSecond : true,
			controlType : 'select',
			dateFormat : 'dd/mm/yy',
			yearRange : "-100:+10"
		}).keyup(function(e) {
			if (e.keyCode == 8 || e.keyCode == 46) {
				$.datepicker._clearDate(this);
			}
		});
		$('#createDate').datepicker().datepicker('setDate', new Date());
		$("#endDate").datepicker({
			//showOn : "button",
			//buttonImage : "",
			//buttonImageOnly : true,
			changeMonth : true,
			changeYear : true,
			showSecond : true,
			controlType : 'select',
			dateFormat : 'dd/mm/yy',
			yearRange : "-100:+10"
		}).keyup(function(e) {
			if (e.keyCode == 8 || e.keyCode == 46) {
				$.datepicker._clearDate(this);
			}
		});
		$('#endDate').datepicker().datepicker('setDate', new Date());
		
		$("#dateOfBirth").datepicker({
			//showOn : "button",
			//buttonImage : "",
			//buttonImageOnly : true,
			changeMonth : true,
			changeYear : true,
			showSecond : true,
			controlType : 'select',
			dateFormat : 'dd/mm/yy',
			yearRange : "-100:+10"
		}).keyup(function(e) {
			if (e.keyCode == 8 || e.keyCode == 46) {
				$.datepicker._clearDate(this);
			}
		});
		//$('#dateOfBirth').datepicker().datepicker('setDate', new Date());
		
		doSubmitSave();
		
		$("#imgFacialId").click(function() {	
			/*if ($("#capturefacialBtnId").val() == "Capture") {
				$("#capturefacialBtnId").val("Recapture");
			}
			$("#facial_capture_div").empty();*/
			InitPortraitDevice();
				
		});

	});
	
	function InitPortraitDevice() {
		  jQuery.support.cors = true;

		 
		  var postData = {
		    deviceID: PC_DEVICE_ID
		  };
		  var dataSet = JSON.stringify(postData);
		

		  var urlString = ws_urlbase + "SPID_Portrait_InitDev";

		  $.ajax({
		    type: 'POST',
		    cache: false,
		    url: urlString,
		    contentType: "application/json; charset=utf-8",
		    dataType: "json",
		    crossDomain: true,
		    data: JSON.stringify(postData),

		    success: function (response) {

		    	CapturePortrait();	
		    	
		     
		    },

		    error: function (jqXHR, textStatus, errorThrown) {
		      console.log(jqXHR);
		      console.log(textStatus);
		      console.log(errorThrown);
		      alert(textStatus);
		    }
		  });
		}
	
	function CapturePortrait() {
		  jQuery.support.cors = true;
		 
	
		  var ptData = {
		    Mode: PC_CAPTURE_MODE,
		    Format: PC_IMAGE_FORMAT,
		    Width: PC_WIDTH,
		    Height: PC_HEIGHT,
		    FixedTrack: PC_FIXED_TRACK,
		    DeviceIndex: PC_DEVICE_INDEX
		  };
		  var postData = {
		    ptInfo: ptData
		  };

		  var urlString =  ws_urlbase + "SPID_Portrait_Capture";

		  $.ajax({
		    type: 'POST',
		    cache: false,
		    url: urlString,
		    contentType: "application/json; charset=utf-8",
		    dataType: "json",
		    crossDomain: true,
		    data: JSON.stringify(postData),

		    success: function (response) {
		     //alert('2');	
		      var result = response;
		   
		      if (result.Result == 0) {
		    	  var ptImage = result.PTImg;

		    	  $("#facial_capture_div").empty();

					var settings = {
					          "async": true,
					          "crossDomain": true,
					          "url": "/ric-web/servlet/SPIDUtil/getFacialByPos",
					          "method": "POST",
					          "headers": {
					              "content-type": "application/x-www-form-urlencoded",
					              "cache-control": "no-cache",
					             
					          },
					          "data": {
					              "imageJpgBase64": ptImage
					          }
						 }

					      $.ajax(settings).done(function (response) {
					        
								document.getElementById("facialEnrolled").src = "data:image/jpg;base64,"+ response.enrolledFace;
								document.getElementById("facialEncoded").src = "data:image/jpg;base64,"	+  response.encodedFace;
								document.getElementById("facialPrinted").src = "data:image/jpg;base64,"	+ response.printedFace;							
								$("#facial_capture_div").append('<input id="enrolledFaceHdnInpt" type="hidden" name="enrolledFace.face" value="'+response.enrolledFace+'"/>');
								$("#facial_capture_div").append('<input id="encodedFaceHdnInpt" type="hidden" name="encodedFace.face" value="'+response.encodedFace+'"/>');
								$("#facial_capture_div").append('<input id="printedFaceHdnInpt" type="hidden" name="printedFace.face" value="'+response.printedFace+'"/>');
								$("#facial_capture_div").append('<input id="smallPortraitCliFaceHdnInpt" type="hidden" name="smallPortraitCliFace.face" value="'+response.smallClipFace+'"/>');
								$("#facial_capture_div").append('<input id="thumbNailFaceHdnInpt" type="hidden" name="thumbNailFace.face" value="'+response.thumbnailEnrolledFace+'"/>');
								$("#facial_capture_div").append('<input id="thumbNailEncodeFaceHdnInpt" type="hidden" name="thumbNailEncodeFace.face" value="'+response.thumbnailEncodedFace+'"/>');
								$("#facial_capture_div").append('<input id="thumbNaiPrintedFaceHdnInpt" type="hidden" name="thumbNailPrintedFace.face" value="'+response.thumbnailEnrolledFace+'"/>');
								document.getElementById("facialEnrolledFull").src = "data:image/jpg;base64,"+ response.enrolledFace;
								document.getElementById("facialEncodedFull").src = "data:image/jpg;base64,"+ response.encodedFace;
								document.getElementById("facialPrintedFull").src = "data:image/jpg;base64,"+ response.printedFace;
								document.forms["ricTransactionInfoForm"].facialEnrolledIndicatorHdnInpt.value = 70;	
						       
					      });

			    	  
					
		       
		      }
		     
		    },

		    error: function (jqXHR, textStatus, errorThrown) {
		      //alert('1');
		      console.log(jqXHR);
		      console.log(textStatus);
		      console.log(errorThrown);
		      alert(textStatus);
		    }
		  });
		}

	function doSubmitSave() {
		
		$('#ajax-load-qa').css('display', 'block');
		var packageNumber = $('#packageNumber').val();
		var createDate = $('#createDate').val();
		var endDate = $('#endDate').val();

		
		$.ajax({
			url : '${searchUpdateUrl}',
			cache: false,
			type: "POST",
			data:{
				packageNumber: packageNumber,
				createDate: createDate,
				endDate: endDate,
			},
			success : function(data) {
				idBefore = '';
				$('#ajax-load-qa').css('display', 'none');
				var tb = $('#tbDanhSachHS').DataTable();	
				tb.clear().draw(false);
				if(data.length > 0){
					$('.opentb-2').css('display', 'none');
		        	for(var i = 0; i < data.length; i++){
		        		tb.row.add([
		        		             data[i].transactionId,
		        		             data[i].fullName
		        		            
		        		]).draw(false);
		        	$('#tbDanhSachHS tr:eq('+(i+1)+') td:eq(1)').addClass("align-central");
		        	}
		        	$('#tbDanhSachHS tbody tr').show();
		        	$('#div_danhsach .dataTables_paginate ').css("display", 'block');
					$('#div_danhsach .dataTables_info').css("display", 'block');
				}else{	
					$('#tbDanhSachHS tbody tr').hide();
					$('#div_danhsach .dataTables_paginate ').css("display", 'none');
					$('#div_danhsach .dataTables_info').css("display", 'none');
					$('.opentb-2').css('display', 'block');
				}
			}
		});
	}
	

	
	/*=====================================================================*/
	/*=========== XỬ LÝ THAY ĐỔI SỐ HỘ CHIẾU ==============================*/
	function openChangePassport(code, np){
		  document.getElementById('transLbl').innerHTML = code;
		  document.getElementById('nowPeriod').innerHTML = np;
		  $("#inputPeriod").val(np);
		  $( "#dialog-changePeriodValidate" ).dialog( "open" ); 
	}
	
	function emptyChitietDanhSach() {
		
	}
	
	function loadPosition(){
		var chucVuLD = $('#lanhDaoKy option:selected').val();
		var url = '${getPositionSignUrl}/' + chucVuLD;
		 $.ajax({
				type : "POST",
				url : url,
				success: function(data) {
					$('#chucVuLD').val(data.ricName);
		        },
		        error: function(e){}
		 });
	}
	
	
	$(function() {
		
		
	     
	    $('#tbDanhSachHS tbody').on('click', 'tr', function(){
	        var dataDSHS = tbDanhSach.row(this).data();
	        if(idBefore == dataDSHS[0]){
	        	return;
	        }
	        idBefore = dataDSHS[0];
	        $('#ajax-load-qa').css('display', 'block');
	        $('#tbDanhSachHS > tbody > tr').removeClass("back-gr");
	    	$(this).addClass("back-gr");
	    	var url = '${detailTranBUrl}';
	    	$.ajax({
				type : "POST",
				url : url,
				data:{
					transactionId: dataDSHS[0]					
				},
				success: function(data) {
					$('#maHoSo').val(data.transactionId);
					$('#chungMinhND').val(data.nin);
					$('#hoTen').val(data.fullName);
					$('#dateOfBirth').val(data.dob);
					$('#diaChi').val(data.address1);
					if(data.listImg.length > 0){
						for(var i = 0; i < data.listImg.length; i++){
							if(data.listImg[i].type == 'PH_CAP'){
								$('#anhChup').html("<img src=\"data:image/jpg;base64,"+ data.listImg[i].base64 +"\" class=\"doGetAJpgSafeVersion\" width=\"135\" />");
							}
							if(data.listImg[i].type == 'PH_CHIP'){
								$('#anhIn').html("<img src=\"data:image/jpg;base64,"+ data.listImg[i].base64 +"\" class=\"doGetAJpgSafeVersion\" width=\"135\" />");
								$('#anhMaHoa').html("<img src=\"data:image/jpg;base64,"+ data.listImg[i].base64 +"\" class=\"doGetAJpgSafeVersion\" width=\"135\" />");
							}
						}
					}
					var arrFp = ['01', '02', '03', '04', '05', '06', '07', '08', '09', '10'];
					if(data.listFinger.length > 0){
						for(var i = 0; i < data.listFinger.length; i++){
							$('#vantay' + data.listFinger[i].serialNo).html("<img src=\"data:image/jpg;base64,"+ data.listFinger[i].base64 +"\" class=\"doGetAJpgSafeVersion\" width=\"180\" height=\"198\"/>");
							var k = arrFp.indexOf(data.listFinger[i].serialNo);
							if (k != -1) {
								arrFp.splice(k,1);
							}
						}
					}
					for(var i = 0; i < arrFp.length; i++){
						$('#vantay' + arrFp[i]).html("<img src=\"<c:url value='/resources/images/noFp.png' />\" height=\"198\" width=\"180\" />");
					}
					$('#ajax-load-qa').css('display', 'none');
		        },
		        error: function(e){}
			});
	    });
	    
	   
		
	});	
	/*=====================================================================*/
	/*=========== END -- XỬ LÝ THAY ĐỔI SỐ HỘ CHIẾU ==============================*/
	
	
	/*$("#createDate").datepicker({
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
	
	$('#createDate').datepicker().datepicker('setDate', "");*/
</script>
	
	<form:input    path="currentlyAssignedToUserId"                       type="hidden" name="currentlyAssignedToUserId"                       value="" />
	<form:input    path="unassignAllForUserUserId"                        type="hidden" name="unassignAllForUserUserId"                        value="" />
	<form:input    path="assignToId"                        type="hidden" name="assignToId"                        value="" />
</form:form>



