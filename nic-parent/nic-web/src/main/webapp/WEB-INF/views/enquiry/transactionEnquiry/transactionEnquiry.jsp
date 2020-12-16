<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>
<c:url var="fullViewPhotoUrl"
	value="/servlet/transactionEnquiry/photosFullView" />
<c:url var="fpInfoUrl" value="/servlet/transactionEnquiry/fpInfo" />
<c:url var="searchTxnUrl" value="/servlet/transactionEnquiry/search" />
<c:url var="jobEnqDgDataUrl"
	value="/servlet/transactionEnquiry/jobEnqDgData" />
<c:url var="jobEnqTransUrl"
	value="/servlet/transactionEnquiry/jobEnqTrans" />
<c:url var="jobEnqHistoryEventsUrl"
	value="/servlet/transactionEnquiry/jobEnqHistoryEvents" />

<c:url var="txnDetailInitUrl"
	value="/servlet/transactionEnquiry/txnDetailInit" />

<c:url var="showPDFProofDoc"
	value="/servlet/transactionEnquiry/showPDFProofDoc" />
<c:url var="showJPEGProofDoc"
	value="/servlet/transactionEnquiry/showJPEGProofDoc" />
	
<script type="text/javascript"
	src="<c:url value="/resources/js/multiSelect/jquery.multiselect.js" />"></script>

<link rel="stylesheet" type="text/css"
	href="<c:url value="/resources/css/multiSelect/jquery.multiselect.css"/>"></link>

<script type="text/javascript"
	src="<c:url value="/resources/js/multiSelect/jquery.multiselect.filter.js" />"></script>

<link rel="stylesheet" type="text/css"
	href="<c:url value="/resources/css/multiSelect/jquery.multiselect.filter.css"/>"></link>
	
<script type="text/javascript" src="https://code.jquery.com/jquery-migrate-3.0.0.min.js"></script>
<!-- jquery plugin to zoom in and out, rotate the scanned document -->
<script src="<c:url value="/resources/js/jquery.iviewer.js"/>"
	type="text/javascript"></script>
<script src="<c:url value="/resources/js/jquery.mousewheel.min.js"/>"
	type="text/javascript"></script>

<link rel="stylesheet" type="text/css"
	href="<c:url value="/resources/css/jquery.iviewer.css"/>"></link>

<link rel="stylesheet" type="text/css"
	href="<c:url value="/resources/css/jquery.iviewer.css"/>"></link>

<script src="<c:url value="/resources/js/mouseover_investigation.js"/>"
	type="text/javascript"></script>
<script src="<c:url value="/resources/js/jQueryRotate.js"/>"
	type="text/javascript"></script>

<style>
.cls-mg-bot {
	margin-top: 10px;
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

.viewer {
	width: 700px;
	height: 700px;
	border: 1px solid black;
	position: relative;
}

.wrapper {
	overflow: hidden;
}

#dialog-photoFullView {
	background-color: #fff;
	position: fixed;
	top: 50%;
	left: 50%;
	transform: translateX(-50%) translateY(-50%);
}
</style>

<script>
	var loadImg = "0";
	$(function() {

		$("#dialog-approve").dialog({
			autoOpen : false,
			width : 960,
			height : 480,
			modal : true,
			bgiframe : true,
			cache : false,
			closeOnEscape : false
		});

		$("#dialog-photoFullView").dialog({
			autoOpen : false,
			width : 960,
			height : 480,
			modal : true,
			bgiframe : true,
			cache : false,
			closeOnEscape : false
		});

		$("#dialog-fpView").dialog({
			autoOpen : false,
			width : 960,
			height : 480,
			modal : true,
			bgiframe : true,
			cache : false,
			closeOnEscape : false
		});

		$("#recordStatus").multiselectbox({
			selectedList : 1,
			header : 'Choose option(s)',
			//hide: "explode",
			noneSelectedText : '-- Chọn --'

		});

		$("#showErrTrans").change(function() {
			if ($(this).is(":checked")) {
				$("#recordStatus").multiselectbox("disable");
			} else {
				$("#recordStatus").multiselectbox("enable");
			}
		});

		$("#reset_btn").click(function() {
			$("#recordStatus").multiselectbox("enable");
		});

	});

	function RotateScanDoc() {
		var rotatId = "#" + rotImagId + "Doc";
		var src_img = $(rotatId).attr('src');
		var iv1 = $("#viewer").iviewer({
			src : src_img
		});
		if (loadImg == "0") {
			loadImg = "1";
		} else {
			iv1.iviewer('loadImage', src_img);
			return false;
		}
	}

	function closePhotoFullDialog() {
		$("#dialog-photoFullView").dialog('close');
	}

	function showProofDoc(transId, docView, docName, dataType, docDesc) {
		if ((dataType == 'JPEG')) {
			/* var url = "${showJPEGProofDoc}" + "/" + transId + "/" + docName;
			window.open(url); */
			$("#dialog-scan-doc").dialog("option", "width", 730);
			$("#dialog-scan-doc").dialog("option", "height", 790);
			$("#dialog-scan-doc").dialog('option', 'title',
					'Proof Document: ' + docDesc);
			$("#dialog-scan-doc").data('docView', docView).data('docName',
					docName).dialog("open");
			$("#dialog-scan-doc").dialog('option', 'title',
					'Proof Document: ' + docDesc);
			$("#dialog-scan-doc").data('docView', docView).data('docName',
					docName).dialog("open");
		} else {
			var url = "${showPDFProofDoc}" + "/" + transId + "/" + docName;
			window.open(url);
		}
	}

	function openFullViewPhotoDialog(transactionId, nin) {
		$('.modal').show();
		var titleName = 'Photos#' + transactionId;
		if (nin != '') {
			titleName += "," + nin;
		}
		$("#dialog-photoFullView").dialog('option', 'title', titleName);
		$("#dialog-photoFullView").dialog("option", "width", "auto"); //1250 1100
		$("#dialog-photoFullView").dialog("option", "height", "auto"); //850 830
		$("#dialog-photoFullView").html("Loading .....");
		$("#dialog-photoFullView").dialog('open');
		$.ajax({
			type : "GET",
			url : '${fullViewPhotoUrl}',
			data : {
				transactionId : transactionId,
				//nin: nin 
				passportNo : nin
			},
			success : function(data) {
				$("#dialog-photoFullView").html(data);
				$('.modal').hide();
			},
			error : function(e) {
				$("#dialog-photoFullView").html(
						'Đã xảy ra lỗi khi hiển thị Ảnh');
				$('.modal').hide();
			}
		});
	}

	/*function getFPinfo(transactionId, nin, fpIndicator, fpQuality, fpEncode,
			fpVerifyScore) {
		//$('.modal').show();
		var titleName = 'Fingerprints #' + transactionId + " ," + nin;
		$("#dialog-fpView").dialog('option', 'title', titleName);
		$("#dialog-fpView").dialog("option", "width", 750);
		$("#dialog-fpView").dialog("option", "height", 470);//520
		$("#dialog-fpView").html("Loading .....");
		$("#dialog-fpView").dialog('open');
		$.ajax({
					type : "GET",
					url : '${fpInfoUrl}',
					data : {
						transactionId : transactionId,
						//nin: nin,
						passportNo : nin,
						fpIndicator : fpIndicator,
						fpQuality : fpQuality,
						fpEncode : fpEncode,
						fpVerifyScore : fpVerifyScore
					},
					success : function(data) {
						$("#dialog-fpView").html(data);
						//$('.modal').hide();
					},
					error : function(e) {
						$("#dialog-fpView")
								.html(
										'Đã xảy ra lỗi khi hiển thị thông tin về dấu vân tay');
						//$('.modal').hide();
					}
				});
	}*/
</script>


<form:form modelAttribute="nicEnqForm" id="searchJobsForm"
	action="/servlet/transactionEnquiry/search" method="post">
	<!-- <div id="main"> -->
	<div class="form-desing">
		<div>
					<div class="row">
						<div class="ov_hidden">
		<div class="new-heading-2">TRA CỨU GIAO DỊCH HỘ CHIẾU</div>
		<fieldset>
			<legend>Thông tin tìm kiếm</legend>
		
			<div class="col-md-12 col-sm-12">
				<div class="col-md-4 col-sm-4">
									<div class="col-md-5 col-sm-5 cls-mg-bot">
										<div class="cla-font">Số hộ chiếu:</div>
									</div>
									<div class="col-md-7 col-sm-7 cls-mg-bot">
										<form:input path="passportNo" id="passportNo" cssClass="defaultText" size="30" maxlength="30" style="width: 100%;"/>
									</div>
									<div class="col-md-5 col-sm-5 cls-mg-bot">
										<div class="cla-font">Họ tên:</div>
									</div>
									<div class="col-md-7 col-sm-7 cls-mg-bot">
										<form:input path="firstName" id="firstName" cssClass="defaultText" size="30" maxlength="30" style="width: 100%;"/>
									</div>
									<!--<div class="col-md-5 col-sm-5 cls-mg-bot">
										<div class="cla-font">Tên:</div>
									</div>
									<div class="col-md-7 col-sm-7 cls-mg-bot">
										<form:input path="lastName" id="lastName" cssClass="defaultText" size="30" maxlength="30" style="width: 100%;"/>
									</div>-->
									
								</div>
								<div class="col-md-4 col-sm-4">									
									<div class="col-md-5 col-sm-5 cls-mg-bot">
										<div class="cla-font">Mã giao dịch:</div>
									</div>
									<div class="col-md-7 col-sm-7 cls-mg-bot">
										<form:input path="transactionId" id="transactionId" cssClass="defaultText" size="30" maxlength="30" style="width: 100%;"/>
									</div>
									<!--<div class="col-md-5 col-sm-5 cls-mg-bot">
										<div class="cla-font">Tên đệm:</div>
									</div>
									<div class="col-md-7 col-sm-7 cls-mg-bot" style="position: relative;">
										<form:input path="middleName" id="middleName" cssClass="defaultText" size="30" maxlength="30" style="width: 100%;"/>
									</div>-->
									<!--<div class="col-md-5 col-sm-5 cls-mg-bot">
										<div class="cla-font">Số hồ sơ:</div>
									</div>
									<div class="col-md-7 col-sm-7 cls-mg-bot">
										<form:input path="receiptNo" id="receiptNo" cssClass="defaultText" size="30" maxlength="30" style="width: 100%;"/>
									</div>-->
									<div class="col-md-5 col-sm-5 cls-mg-bot">
										<div class="cla-font">Ngày sinh:</div>
									</div>
									<div class="col-md-7 col-sm-7 cls-mg-bot" style="position: relative;">
										<form:input id="dateOfBirth" name="dateOfBirth" path="dateOfBirth" cssClass="defaultText" size="12" maxlength="12" readonly="true" style="width: 100%;margin-right: -20px;"/>
									</div>
								</div>	
								<div class="col-md-4 col-sm-4">
									<div class="col-md-5 col-sm-5 cls-mg-bot">
										<div class="cla-font">Giới tính:</div>
									</div>
									<div class="col-md-7 col-sm-7 cls-mg-bot">
										<select style="width: 100%;" id="gender" name="gender">
												<option value="">-- Chọn --</option>
												<c:forEach var="opt" items="${genderList}">
													<option title="${opt.codeValueDesc}" value="${opt.id.codeValue}">
														<c:out value="${opt.codeValueDesc}" />
													</option>
												</c:forEach>
										</select>
									</div>
									
									
									<div class="col-md-12" style="margin-top: 10px;margin-bottom: 10px;">
										<button type="button" style="float: right;" id="search_btn" class="btn_small btn-success btn-search">
											<span class="glyphicon glyphicon-search"></span> Tìm kiếm
										</button>				
									</div>
								</div>					
			</div>
			</fieldset>
			
		
		<!--<div class="col-md-12">
			<div id="searchResult">
				<table id="jobEnquiryFlexGrid"></table>
			</div>
		</div>-->
		<fieldset>
			<legend>Danh sách giao dịch</legend>
		
				<div style="height: 400px; overflow: auto;">
			      	<table id="dtBasicExample" class="table table-bordered table-sm" cellspacing="0" width="100%">
					  <thead>
					    <tr>
					      <th class="th-sm">Mã giao dịch
					
					      </th>	
					      <th class="th-sm">CMND/CCCD
					
					      </th>
					      <th class="th-sm">Ngày nộp hồ sơ
					
					      </th>
					      <th class="th-sm">Ngày trả 
					
					      </th>
					      <th class="th-sm">Họ tên
					
					      </th>
					      
					      <th class="th-sm">Loại giao dịch
					
					      </th>	
					      <th class="th-sm">Trạng thái
					
					      </th>	
					      <th class="th-sm">Nơi phát hành
					
					      </th>								   
					    </tr>
					  </thead>
					  <tbody>
					  <c:if test="${not empty jobList}">
					    <c:forEach items="${jobList}" var="item">
						    <tr>							    									      									      
						      <td><a style="color: blue;" href="#" onclick="callDialog('${item.transactionId}')">${item.transactionId}</a></td>
						      <td>${item.nin}</td>
						      <td align="center">${item.datTimeOfApplication}</td>
						      <td align="center">${item.estCollectionDate}</td>
						      <td>${item.fullName}</td>
						      <td>${item.transactionType}</td>
						      <td>${item.transactionStatus}</td>
						      <td>${item.regSiteCode}</td>									      						     
						    </tr>
					    </c:forEach>
					  </c:if>
					  </tbody>
					  <c:if test="${empty jobList}">
								  
						   <tbody class="e-not-found ng-scope"><tr>
						  <td colspan="10" style="height: 362px">
						  <div class="e-not-found-center"><div class="e-not-found-title">Không tìm thấy kết quả</div><div class="e-not-found-title-child">Hãy thử tìm kiếm hoặc sử dụng bộ lọc khác</div></div>
						  </td></tr></tbody>
						  
						
						</c:if>
					</table>	 
					<input type="hidden" name="pageNo" id="pageNo" /> 
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
			    <!-- Message lưu hồ sơ -->
				<div class="modal fade" id="idChiTiet" tabindex="-1" role="dialog" aria-labelledby="exampleModalCenterTitle" aria-hidden="true" >
				  <div class="modal-dialog modal-dialog-centered" role="document" style="width: 1200px;">
				    <div class="modal-content">
				      <div class="modal-header">
				        <h5 class="modal-title" id="exampleModalLongTitle" style="display: inline-block;">CHI TIẾT HỒ SƠ</h5>
				        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
				          		<img style="width: 25px;height: 25px;" src="<c:url value='/resources/images/dongform.png' />" title="Đóng" />
				        </button>
				      </div>
				      <div class="modal-body" id="divChiTiet" style="height: 800px;">
				      		
				      </div>							      
				      <div class="modal-footer" style="padding: 5px;">
				       		<button type="button" class="btn btn-warning" data-dismiss="modal" aria-label="Close" style="float: right;">
				       			<span class="glyphicon glyphicon-remove"></span > Đóng
				       		</button>	       		
				      </div>
				    </div>
				  </div>
				</div>	
				<!-- ---------------------------------------------------------------------------->
				<div id="ajax-load-qa"></div>
		</div>
		</div>
		</div>
	</div>
	<!-- </div> -->

	<input type="hidden" id="txnId" name="txnId">
	<input type="hidden" id="searchUrl" name="searchUrl"
		value="${searchTxnUrl}" />

</form:form>

	<div id="msgDialog" title="Thông báo"></div>
	<div class="isa_info" align="center">
		<span style="font-size: 40" id="msgDialogSpan"></span>
	</div>
	<div id="dialog-approve"></div>


	<script type="text/javascript">
		var reload = "0";
		$(function() {
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
								document.forms["searchJobsForm"].action = '${searchTxnUrl}';  
								document.forms["searchJobsForm"].submit();  
							}
						}
					});
			$(document).on(
					'keyup',
					function(event) {
						if (event.keyCode == 27) {
							if ($("#dialog-photoFullView").is(':visible')) {
								$("#dialog-photoFullView").dialog('close');
							} else if ($("#dialog-fpView").is(':visible')) {
								$("#dialog-fpView").dialog('close');
							} else if ($("#dialog-scan-doc").is(':visible')) {
								$("#dialog-scan-doc").dialog('close');
							} else if ($("#infoDialog").is(':visible')) {
								$("#infoDialog").dialog('close');
							} else if ($("#dialog-approve").is(':visible')
									&& !$("#dialog-photoFullView").is(
											':visible')
									&& !$("#dialog-fpView").is(':visible')
									&& !$("#dialog-scan-doc").is(':visible')
									&& !$("#infoDialog").is(':visible')) {
								$("#dialog-approve").dialog('close');
							}
						}
					});

			$(document).on("click", "#search_btn", function() {
				//transQueryList();
				//var orig = $("#searchJobsForm").serialize();
				//var withoutEmpties = orig.replace(/[^&]+=\.?(?:&|$)/g, '');
				//var test = $("#searchUrl").val() + '?' + withoutEmpties;
				document.forms["searchJobsForm"].action =  $("#searchUrl").val();  
				document.forms["searchJobsForm"].submit();
				/*$.ajax({
					url : test,
					cache: false,
					type: "POST",					
					success : function(data) {
						if(data.length > 0){
							var tb = $('#dtBasicExample').DataTable();	
							tb.clear();
				        	for(var i = 0; data.length; i++){
				        		tb.row.add([
				        		             "<a style=\"color: blue;\" href=\"#\" onclick=\"callDialog('" + data[i].transactionId + "')\">" + data[i].transactionId + "</a>",
				        		             data[i].applnRefNo,
				        		             data[i].datTimeOfApplication,
				        		             data[i].estCollectionDate,
				        		             data[i].firstName,
				        		             data[i].lastName,
				        		             data[i].transactionType,
				        		             data[i].transactionStatus,
				        		             data[i].regSiteCode
				        		]).draw(false);
				        	}
							
						}else{							
							$.alert({
								title: 'Thông báo',
								content: "<img style=\"margin-bottom: 5px;\" src=\"/eppcentral/resources/images/warning_1b.png\">" + ' Không tìm thấy dữ liệu',
								 buttons: {   
				        		        "Đóng": function () {}
				        		    }
							});
						}
					}
				});*/
			});

			$("#msgDialog").dialog({
				width : 500,
				resizable : false,
				modal : true,
				autoOpen : false,
				show : {
					effect : "blind",
					duration : 1000
				},
				hide : {
					//effect : "explode",
					duration : 100
				},
				close : function() {
					$(this).dialog("close");
				},
				buttons : {
					"Đóng" : function() {
						$(this).dialog("close");
					}
				}
			});

			$("#reset_btn").click(function() {
				$("#searchResult").hide();
				$("#txnId").val("");
				$("#transactionId").val("");
				$("#firstName").val("");
				$("#lastName").val("");
				$("#middleName").val("");
				$("#receiptNo").val("");
				$("#gender").val("");
				$("#dateOfBirth").val("");

				$("#jobEnquiryFlexGrid").empty();
				$("#jobEnquiryFlexGrid").hide();

			});

		});

		function validSearch() {
			if ($("#passportNo").val() == ""
					&& $("#transactionId").val() == ""
					&& $("#receiptNo").val() == ""
					&& $("#firstName").val() == ""
					&& $("#lastName").val() == ""
					&& $("#middleName").val() == "") {
				return false
			}
			return true;
		}

		var jsondata;
		function transQueryList() {
			if (validSearch() == false) {
				$("#msgDialogSpan").html(
						"Vui lòng nhập chính xác một hoặc nhiều tiêu chí tìm kiếm");
				$("#msgDialog").dialog("open");
				return false;
			}

			var orig = $("#searchJobsForm").serialize();
			var withoutEmpties = orig.replace(/[^&]+=\.?(?:&|$)/g, '');
			var test = $("#searchUrl").val() + '?' + withoutEmpties;
			$("#txnId").val("");
			$('#jobEnquiryFlexGrid').empty();
			$("#searchResult").show();
			$('#jobEnquiryFlexGrid').show();
			if (reload == "0") {
				reload = "1";
				$("#jobEnquiryFlexGrid").flexigrid({
					url : test,
					dataType : 'json',
					colModel : [ {
						display : 'Mã giao dịch',
						name : 'transactionId',
						width : 220,
						align : 'left',
						render : renderView
					}, {
						display : 'Số hồ sơ',
						name : 'applnRefNo',
						width : 150,
						sortable : false,
						align : 'left'
					}, {
						display : 'Ngày nộp hồ sơ',
						name : 'datTimeOfApplication',
						width : 200,
						sortable : false,
						align : 'left'
					}, {
						display : 'Ngày trả kết quả',
						name : 'estCollectionDate',
						width : 150,
						sortable : false,
						align : 'left'
					}, {
						display : 'Tên',
						name : 'firstName',
						width : 150,
						sortable : false,
						align : 'left'
					}, {
						display : 'Tên đệm',
						name : 'middleName',
						width : 150,
						sortable : false,
						align : 'left'
					}, {
						display : 'Họ',
						name : 'lastName',
						width : 150,
						sortable : false,
						align : 'left'
					}, {
						display : 'Loại giao dịch',
						name : 'transactionType',
						width : 240,
						sortable : false,
						align : 'left'
					}, {
						display : 'Trạng thái giao dịch',
						name : 'transactionStatus',
						width : 310,
						sortable : false,
						align : 'left'
					}, {
						display : 'Nơi phát hành',
						name : 'regSiteCode',
						width : 260,
						sortable : false,
						align : 'left'
					} ],
					sortname : "Mã giao dịch",
					sortorder : "asc",
					title : 'Danh sách giao dịch',
					usepager : true,
					useRp : true,
					rp : 20,
					showTableToggleBtn : true,
					height : 400,
					singleSelect : true,
					nowrap : false

				});
			} else {
				$("#jobEnquiryFlexGrid").flexOptions({
					url : test,
					newp : 1
				}).flexReload();
			}

			function vlidateData() {
				var rowlength = document.getElementById("jobEnquiryFlexGrid").rows.length;
				if (rowlength > 500) {
					$("#msgDialogSpan").html(
							"Hệ thống sẽ trả lại tối đa 500 bản ghi");
					$("#msgDialog").dialog("open");
					return false;
				}
			}
		}

		function renderView(content) {
			var id = content.split(",");
			var txnId = id[0];

			return "<a href=\"#\" onclick=\"callDialog('" + txnId + "')\">"
					+ txnId + "</a>";

		}

		function callDialog(txnId) {
			/*$('.modal').show();
			document.getElementById('txnId').value = txnId;
			var titleName = "Chi tiết tra cứu giao dịch";
			$("#dialog-approve").dialog('option', 'title', titleName);
			$("#dialog-approve").dialog("option", "width", 1200);
			$("#dialog-approve").dialog("option", "height", 600);
			$("#dialog-approve").dialog("option", "maxHeight", 600);
			$("#dialog-approve").dialog("option", "resizable", false);
			$("#dialog-approve").dialog('open');
			$.ajax({
				type : "GET",
				url : "${txnDetailInitUrl}/" + txnId,
				success : function(data) {
					$("#dialog-approve").html(data);
					$('.modal').hide();
				},
				error: $('.modal').hide()
			});*/
			$('#ajax-load-qa').show();
			document.getElementById('txnId').value = txnId;
			$.ajax({
				type : "GET",
				url : "${txnDetailInitUrl}/" + txnId,
				success : function(data) {
					$('#divChiTiet').html(data);
					$('#idChiTiet').modal();
					$('#ajax-load-qa').hide();
				},
				error: $('#ajax-load-qa').hide()
			});
		}

		function doSubmitSearch(form) {
			$('.modal').show();
			form.action = '<c:url value="/servlet/transactionEnquiry/search" />';
			form.submit();
			// $('.modal').hide();
		}

		$("#dateOfBirth").datepicker({
			//showOn : "button",
			//buttonImage : "<c:url value="/resources/images/calendar.gif" />",
			//buttonImageOnly : true,
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

		$('#dateOfBirth').datepicker().datepicker('setDate', "");
	</script>


	<div id="dialog-photoFullView" style="display: none;"></div>
	<div id="dialog-fpView" style="display: none;"></div>
	<c:if test="${viewFPFalg eq 'Y' }">
				<applet name="investigationApplet"
			code="com.nec.asia.applet.InvestigationApplet.class"
			codebase="<%=request.getContextPath()%>/applet"
			id="investigationApplet" archive="nic-applet.jar,spid6.jar"
			height="1" width="1" mayscript="mayscript">
			<param name="verify" value="N">
		</applet>
	</c:if>
	<div class="modal" />