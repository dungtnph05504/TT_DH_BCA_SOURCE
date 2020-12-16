<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>
<c:url var="fullViewPhotoUrl" value="/servlet/nicEnquiry/photosFullView" />
<c:url var="fpInfoUrl" value="/servlet/nicEnquiry/fpInfo" />
<c:url var="searchJobsUrl" value="/servlet/nicEnquiry/searchJobs" />
<c:url var="jobDetailsUrl" value="/servlet/nicEnquiry/jobDetails" />
<c:url var="jobEnqDgDataUrl" value="/servlet/nicEnquiry/jobEnqDgData" />
<c:url var="jobEnqHitListUrl" value="/servlet/nicEnquiry/jobEnqHitList" />
<c:url var="jobEnqTransUrl" value="/servlet/nicEnquiry/jobEnqTrans" />
<c:url var="jobEnqHistoryEventsUrl"
	value="/servlet/nicEnquiry/jobEnqHistoryEvents" />
<c:url var="jobEnqDetailsInitUrl" value="jobEnqDetailsInit" />

<c:url var="showPDFProofDoc" value="/servlet/nicEnquiry/showPDFProofDoc" />
<c:url var="showJPEGProofDoc"
	value="/servlet/nicEnquiry/showJPEGProofDoc" />
<c:url var="transactionEnquiryUrl" value="/servlet/nicEnquiry/jobQueue" />
<script type="text/javascript"
	src="<c:url value="/resources/js/multiSelect/jquery.multiselect.js" />"></script>

<link rel="stylesheet" type="text/css"
	href="<c:url value="/resources/css/multiSelect/jquery.multiselect.css"/>"></link>

<script type="text/javascript"
	src="<c:url value="/resources/js/multiSelect/jquery.multiselect.filter.js" />"></script>

<link rel="stylesheet" type="text/css"
	href="<c:url value="/resources/css/multiSelect/jquery.multiselect.filter.css"/>"></link>

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
	background: rgba(255, 255, 255, .8)
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
 
<style>
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
			header : '--Chọn--',
			//hide: "explode",
			noneSelectedText : '-- SELECT --'

		});
		$('#drpTraj > button').css('width', '70%');

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
						' Đã có lỗi xảy ra trong khi hiển thị Ảnh');
				$('.modal').hide();
			}
		});
	}

	function getFPinfo(transactionId, nin, fpIndicator, fpQuality, fpEncode,
			fpVerifyScore) {
		$('.modal').show();
		var titleName = 'Thông tin vân tay của mã giao dịch: ' + transactionId;
		$("#dialog-fpView").dialog('option', 'title', titleName);
		$("#dialog-fpView").dialog("option", "width", 750);
		$("#dialog-fpView").dialog("option", "height", 470);//520
		$("#dialog-fpView").html("Loading .....");
		$("#dialog-fpView").dialog('open');
		$
				.ajax({
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
						$('.modal').hide();
					},
					error : function(e) {
						$("#dialog-fpView")
								.html('Đã có lỗi xảy ra trong khi hiển thị thông tin dấu vân tay');
						$('.modal').hide();
					}
				});
	}
</script>


<form:form modelAttribute="nicEnqForm" id="searchJobsForm"
	action="/servlet/nicEnquiry/search" method="post">
	<!-- <div id="main"> -->
	<div class="form-desing">
		<div>
        <div class="row">
        <div class="ov_hidden">
		<div class="new-heading-2">TRA CỨU CÔNG VIỆC</div>
		<div style="border: solid 1px #cccc; border-radius: 4px; margin: 2px;min-width:100%;float:left">
			<div class="col-md-12 col-sm-12">
				<div class="col-md-4 col-sm-4">
									<div class="col-md-5 col-sm-5 cls-mg-bot">
										<div class="cla-font">Mã công việc:</div>
									</div>
									<div class="col-md-7 col-sm-7 cls-mg-bot">
										<form:input path="jobId" id="jobId" cssClass="defaultText" size="30" maxlength="30" style="width: 100%;"/>
									</div>
									<div class="col-md-5 col-sm-5 cls-mg-bot">
										<div class="cla-font">Từ ngày:</div>
									</div>
									<div class="col-md-7 col-sm-7 cls-mg-bot" style="position: relative;">
										<form:input path="dateFrom" type="text" id="dateFrom" name="dateFrom" cssClass="defaultText" size="12" maxlength="12" readonly="true" style="width: 100%;margin-right: -20px;"/>
									</div>
									<div class="col-md-5 col-sm-5 cls-mg-bot">
										<div class="cla-font">Loại công việc:</div>
									</div>
									<div class="col-md-7 col-sm-7 cls-mg-bot">
										<select	style="width: 100%;" id="jobType" name="jobType">
												<option value="">-- Chọn --</option>
												<c:forEach var="opt" items="${nicEnqForm.jobTypeCodeList}">
													<option title="${opt.codeValueDesc}" value="${opt.id.codeValue}">
														<c:out value="${opt.codeValueDesc}" />
													</option>
												</c:forEach>
										</select>
									</div>
									<div class="col-md-5 col-sm-5 cls-mg-bot">
										<div class="cla-font">Trung tâm tiếp nhận:</div>
									</div>
									<div class="col-md-7 col-sm-7 cls-mg-bot">
										<select style="width: 100%;" id="regSiteCode" name="regSiteCode">
												<option value="">-- Chọn --</option>
												<c:forEach var="opt" items="${nicEnqForm.regSiteList}">
													<option title="${opt.siteName}" value="${opt.siteId}">
														<c:out value="${opt.siteName}" />
													</option>
												</c:forEach>
										</select>
									</div>
									
								</div>
								<div class="col-md-4 col-sm-4">									
									<div class="col-md-5 col-sm-5 cls-mg-bot">
										<div class="cla-font">Mã giao dịch:</div>
									</div>
									<div class="col-md-7 col-sm-7 cls-mg-bot">
										<form:input path="transactionId" id="transactionId" cssClass="defaultText" size="30" maxlength="30"  style="width: 100%;"/>
									</div>
									<div class="col-md-5 col-sm-5 cls-mg-bot">
										<div class="cla-font">Đến ngày:</div>
									</div>
									<div class="col-md-7 col-sm-7 cls-mg-bot" style="position: relative;">
										<form:input type="text" path="dateTo" id="dateTo" name="dateTo" cssClass="defaultText" size="12" maxlength="12" readonly="true" style="width: 100%;margin-right: -20px;"/>
									</div>
									<div class="col-md-5 col-sm-5 cls-mg-bot">
										<div class="cla-font">Trạng thái giao dịch:</div>
									</div>
									<div class="col-md-7 col-sm-7 cls-mg-bot">
										<select style="width: 100%;" id="transactionStatus" name=transactionStatus>
												<option value="">-- Chọn --</option>
												<c:forEach var="opt" items="${nicEnqForm.txnStatusCodeList}">
													<option title="${opt.codeValueDesc}" value="${opt.id.codeValue}">
														<c:out value="${opt.codeValueDesc}" />
													</option>
												</c:forEach>
										</select>
									</div>
									<div class="col-md-5 col-sm-5 cls-mg-bot">
										<div class="cla-font">Trung tâm phát hành:</div>
									</div>
									<div class="col-md-7 col-sm-7 cls-mg-bot" >
										<select style="width: 100%;" id="issSiteCode" name="issSiteCode">
												<option value="">-- Chọn --</option>
												<c:forEach var="opt" items="${nicEnqForm.issSiteList}">
													<option title="${opt.siteName}" value="${opt.siteId}">
														<c:out value="${opt.siteName}" />
													</option>
												</c:forEach>
										</select>
									</div>
								</div>	
								<div class="col-md-4 col-sm-4">
									<div class="col-md-5 col-sm-5 cls-mg-bot">
										<div class="cla-font">Kiểu xác minh:</div>
									</div>
									<div class="col-md-7 col-sm-7 cls-mg-bot">
										<select style="width: 100%;" id="investigationType" name="investigationType">
												<option value="">-- Chọn --</option>
												<c:forEach var="opt" items="${nicEnqForm.investigationTypeList}">
													<option title="${opt.label}" value="${opt.value}">
														<c:out value="${opt.label}" />
													</option>
												</c:forEach>
										</select>
									</div>
									<div class="col-md-5 col-sm-5 cls-mg-bot">
										<div class="cla-font">Giai đoạn hiện tại:</div>
									</div>
									<div class="col-md-7 col-sm-7 cls-mg-bot">
										<select style="width: 100%;" id="jobState" name="jobState">
												<option value="">-- Chọn --</option>
												<c:forEach var="opt" items="${nicEnqForm.jobStateCodeList}">
													<option title="${opt.codeValueDesc}" value="${opt.id.codeValue}">
														<c:out value="${opt.codeValueDesc}" />
													</option>
												</c:forEach>
										</select>
									</div>
									<div class="col-md-5 col-sm-5 cls-mg-bot">
										<div class="cla-font">Trạng thái xác minh:</div>
									</div>
									<div id="drpTraj" class="col-md-7 col-sm-7 cls-mg-bot">
										<select id="recordStatus" style="width: 100%;" multiple="multiple" name="recordStatus">
												<c:forEach var="opt" items="${nicEnqForm.recordStatusCodeList}">
													<option title="${opt.codeValueDesc}" value="${opt.id.codeValue}">
														<c:out value="${opt.codeValueDesc}" />
													</option>
												</c:forEach>
										</select>
									</div>
									<div class="col-md-5 col-sm-5 cls-mg-bot">
										<div class="cla-font">Hiển thị các giao dịch lỗi:</div>
									</div>
									<div class="col-md-7 col-sm-7 cls-mg-bot" style="position: relative;height: 30px;">
										<form:checkbox path="showErrTrans" id="showErrTrans" />
									</div>
								</div>		
			</div>
			<div class="col-md-12" style="text-align: center;margin-top: 20px;margin-bottom: 20px;">
				<button type="button" id="search_btn" class="btn_small btn-primary btn-search">
					<span class="glyphicon glyphicon-search"></span> Tìm kiếm
				</button>
				<!--<button type="button" id="reset_btn" class="btn_small btn-primary btn-search">
					<span class="glyphicon glyphicon-refresh"></span> Làm mới
				</button>-->
			</div>
			
			</div>
			<div>
						      	<table id="dtBasicExample" class="table table-bordered table-sm" cellspacing="0" width="100%">
								  <thead>
								    <tr>
								      <th class="th-sm" style="max-width: 50px;">ID
								
								      </th>	
								      <th class="th-sm" style="min-width: 150px;">Mã giao dịch
								
								      </th>
								      <th class="th-sm" style="min-width: 140px;">Trạng thái giao dịch
								
								      </th>
								      <th class="th-sm" style="min-width: 120px;">Loại công việc
								
								      </th>
								      <th class="th-sm" style="min-width: 130px;">Giai đoạn công việc
								
								      </th>
								      <th class="th-sm" style="min-width: 130px;">Trạng thái công việc
								
								      </th>
								      <th class="th-sm" style="min-width: 120px;">Mã trung tâm
								
								      </th>
								      <th class="th-sm" style="min-width: 100px;">Người tạo
								
								      </th>
								      <th class="th-sm" style="min-width: 100px;">Ngày tạo
								
								      </th>
								    </tr>
								  </thead>
								  <tbody>
								    <c:forEach items="${jobList}" var="item">
									    <tr>							    									      
									      <td><a style="color: blue;" href="#" onclick="callDialog('${item.jobTxnId}')">${item.jobTxnId}</a></td>
									      <td>${item.transactionId}</td>
									      <td>${item.transactionStatus}</td>	
									      <td>${item.jobType}</td>
									      <td>${item.jobCurrentState}</td>
									      <td>${item.jobCurrentStatus}</td>
									      <td>${item.regSiteDesc}</td>
									      <td>${item.createBy}</td>
									      <td>${item.createDateTime}</td>						     
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
		<!--<div class="col-md-12">
			<div id="searchResult">
				<table id="jobEnquiryFlexGrid"></table>
			</div>
		</div>-->
	</div>
	</div>
	</div>
	</div>
	<!-- </div> -->

	<input type="hidden" id="jid" name="jid">
	<input type="hidden" id="txnId" name="txnId">
	<input type="hidden" id="searchUrl" name="searchUrl"
		value="${searchJobsUrl}" />

</form:form>

<div id="msgDialog" title="Cảnh báo">
	<div class="isa_info" align="center">
		<span style="font-size: 40" id="msgDialogSpan"></span>
	</div>
	<div id="dialog-approve"></div>

	<div class="modal">
		<!-- Place at bottom of page -->
	</div>

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
					document.forms["searchJobsForm"].action = '${searchJobsUrl}';  
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
			$('#search_btn').click(function(){
				//transQueryList();
				//alert("1");
				//var test = $("#searchUrl").val();
				//alert(withoutEmpties);
				document.forms["searchJobsForm"].action = '${searchJobsUrl}';  
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
				        		             "<a href=\"#\" onclick=\"callDialog('" + data[i].jobTxnId + "')\">" + data[i].jobTxnId + "</a>",
				        		             data[i].transactionId,
				        		             data[i].transactionStatus,
				        		             data[i].jobType,
				        		             data[i].jobCurrentState,
				        		             data[i].jobCurrentStatus,
				        		             data[i].regSiteDesc,
				        		             data[i].createBy,
				        		             data[i].createDateTime
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
					Ok : function() {
						$(this).dialog("close");
					}
				}
			});

			$("#resetAllBtn").click(function() {
				$("#searchResult").hide();
				$("#transactionId").val("");
				$("#regSiteCode").val("");
				$("#jobType").val("");
				$("#txnStatus").val("");
				$("#issSiteCode").val("");
				$("#recordStatus").val("");
				$("#dateFrom").val("");
				$("#dateTo").val("");
				$("#jobEnquiryFlexGrid").empty();
				$("#jobEnquiryFlexGrid").hide();

			});

		});

		function validSearch() {
			if ($("#jobId").val() == ""
					&& $("#transactionId").val() == ""
					&& $("#regSiteCode").val() == ""
					&& $("#jobType").val() == ""
					&& $("#txnStatus").val() == ""
					&& $("#issSiteCode").val() == ""
					&& $("#dateFrom").val() == ""
					&& $("#dateTo").val() == ""
					&& ($("#recordStatus").val() == "" || $("#recordStatus")
							.val() == null)) {
				return false
			}
			return true;
		}

		var jsondata;
		/*function transQueryList() {
			if (validSearch() == false) {
				$("#msgDialogSpan").html(
						"Vui lòng nhập chính xác một hoặc nhiều tiêu chí tìm kiếm");
				$("#msgDialog").dialog("open");
				return false;
			}
			var orig = $("#searchJobsForm").serialize();
			var withoutEmpties = orig.replace(/[^&]+=\.?(?:&|$)/g, '');
			var test = $("#searchUrl").val() + '?' + withoutEmpties;
			$('#jobEnquiryFlexGrid').empty();
			$("#searchResult").show();
			$('#jobEnquiryFlexGrid').show();
			if (reload == "0") {
				reload = "1";
				$("#jobEnquiryFlexGrid").flexigrid({
					url : test,
					dataType : 'json',
					colModel : [ {
						display : 'Mã công việc',
						name : 'jobTxnId',
						width : 100,
						sortable : true,
						align : 'left',
						render : renderView
					}, {
						display : 'Mã giao dịch',
						name : 'transactionId',
						width : 180,
						align : 'left'
					}, {
						display : 'Trạng thái giao dịch',
						name : 'transactionStatus',
						width : 310,
						sortable : false,
						align : 'left'
					}, {
						display : 'Loại công việc',
						name : 'jobType',
						width : 240,
						sortable : false,
						align : 'left'
					}, {
						display : 'Giai đoạn công việc',
						name : 'jobCurrentState',
						width : 135,
						sortable : false,
						align : 'left'
					}, {
						display : 'Trạng thái công việc',
						name : 'jobCurrentStatus',
						width : 220,
						sortable : false,
						align : 'left'
					}, {
						display : 'Mã trung tâm',
						name : 'regSiteDesc',
						width : 260,
						sortable : false,
						align : 'left'
					}, {
						display : 'Người tạo',
						name : 'createBy',
						width : 135,
						sortable : false,
						align : 'left'
					}, {
						display : 'Ngày tạo',
						name : 'createDateTime',
						width : 135,
						sortable : false,
						align : 'left'
					} ],
					sortname : "workflowJobId",
					sortorder : "asc",
					title : 'Danh sách công việc',
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
							"Hệ thống sẽ trả lại tối đa 500 kết quả");
					$("#msgDialog").dialog("open");
					return false;
				}
			}
		}*/

		function renderView(content) {
			var id = content.split(",");
			var jobId = id[0];
			var transId = id[1];
			return "<a href=\"#\" onclick=\"callDialog('" + jobId + "','"
					+ transId + "')\">" + jobId + "</a>";

		}

		function callDialog(jobId, transId) {
			$('.modal').show();
			document.getElementById('jid').value = jobId;
			document.getElementById('txnId').value = transId;
			var titleName = "Job Enquiry Detail";
			$("#dialog-approve").dialog('option', 'title', titleName);
			$("#dialog-approve").dialog("option", "width", 1200);
			$("#dialog-approve").dialog("option", "height", 480);
			$("#dialog-approve").dialog("option", "maxHeight", 480);
			$("#dialog-approve").dialog("option", "resizable", false);
			$("#dialog-approve").dialog('open');
			$.ajax({
				type : "GET",
				url : "${jobEnqDetailsInitUrl}",
				success : function(data) {
					$("#dialog-approve").html(data);
					$('.modal').hide();
				}
			});
		}

		function doSubmitSearch(form) {
			$('.modal').show();
			form.action = '<c:url value="/servlet/nicEnquiry/search" />';
			form.submit();
			// $('.modal').hide();
		}

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
		
		//var date = new Date();
		//date.setDate(date.getDate() - 30);
		$('#dateFrom').datepicker().datepicker('setDate', '');
		$('#dateTo').datepicker().datepicker('setDate', '');
	</script>


	<div id="dialog-photoFullView" style="display: none;"></div>
	<div id="dialog-fpView" style="display: none;"></div>
	<c:if test="${viewFPFalg eq 'Y' }">
		<%-- 		<applet name="investigationApplet"
			code="com.nec.asia.applet.InvestigationApplet.class"
			codebase="<%=request.getContextPath()%>/applet"
			id="investigationApplet" archive="nic-applet.jar,spid6.jar"
			height="1" width="1" mayscript="mayscript">
			<param name="verify" value="N">
		</applet> --%>
	</c:if>
	<div class="modal" />