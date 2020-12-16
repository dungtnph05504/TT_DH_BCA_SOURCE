<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>
<c:url var="txnEditorInitUrl"
	value="/servlet/transactionEnquiry/txnEditorInit" />


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
.modal {
	display: none;
	position: fixed;
	z-index: 9999999;
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
						'Đã xảy ra lỗi khi hiển thị ảnh');
				$('.modal').hide();
			}
		});
	}

	function getFPinfo(transactionId, nin, fpIndicator, fpQuality, fpEncode,
			fpVerifyScore) {
		$('.modal').show();
		var titleName = 'Fingerprints #' + transactionId + " ," + nin;
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
								.html(
										'Đã xảy ra lỗi khi hiển thị thông tin về dấu vân tay');
						$('.modal').hide();
					}
				});
	}
</script>


<form:form modelAttribute="nicEnqForm" id="searchJobsForm"
	action="/servlet/transactionEnquiry/search" method="post">
	<!-- <div id="main"> -->
	<div id="content_main">
		<div class="container">
			<div class="row">
				<div class="roundedBorder ov_hidden">
		<div id="heading_report" align="justify" style='padding: 2px'>Danh sách giao dịch</div>

		<!--********************customised code for now***************************************-->
		<table style="width: 100%; background-color: #E3F1FE" cellspacing="0"
			class="data_table" cellpadding="0" border="0">
			<tr style="padding-top: 10px">
				<td style="border: none; font-weight: bold; width: 15%;">Tên đầy đủ</td>
				<td style="border: none; width: 25%;"><form:input
						path="passportNo" id="passportNo" cssClass="defaultText" size="30"
						maxlength="30" /></td>
				</tr>
			<tr style="padding-top: 10px">
				<td style="border: none; font-weight: bold; width: 15%;">Tên đệm</td>
				<td style="border: none;"><form:input
						path="nicTransaction.transactionId" id="transactionId"
						cssClass="defaultText" size="30" maxlength="30" /></td>
			</tr>
			<tr style="padding-top: 10px">
				<td style="border: none; font-weight: bold; width: 15%;">Họ</td>
				<td style="border: none; width: 25%;"><form:input
						path="registrationData.firstnameFull" id="firstname"
						cssClass="defaultText" size="30" maxlength="30" /></td>
				</tr>
			<tr style="padding-top: 10px">
				<td style="border: none; font-weight: bold; width: 15%;">Quốc tịch</td>
				<td style="border: none; width: 25%;"><form:input
						path="registrationData.surnameFull" id="lastname"
						cssClass="defaultText" size="30" maxlength="30" />
			</tr>
			<tr style="padding-top: 10px">

				<td style="border: none; font-weight: bold; width: 15%;">Giới tính</td>
				<td style="border: none; width: 25%;"><select
					style="width: 30;" id="gender" name="registrationData.gender">
						<option value="">-- Chọn --</option>
						<c:forEach var="opt" items="${nicEnqForm.genderCodeList}">
							<option title="${opt.codeValueDesc}" value="${opt.id.codeValue}">
								<c:out value="${opt.codeValueDesc}" />
							</option>
						</c:forEach>
				</select>
				</td>
				</tr>
			<tr style="padding-top: 10px">				<td style="border: none; font-weight: bold; width: 15%;">Trung tâm phát hành</td>
				<td style="border: none; width: 25%;"><select
					style="width: 30;" id="regSiteCode" name="regSiteCode">
						<option value="">-- Chọn --</option>
						<c:forEach var="opt" items="${nicEnqForm.regSiteList}">
							<option title="${opt.siteName}" value="${opt.siteId}">
								<c:out value="${opt.siteName}" />
							</option>
						</c:forEach>
				</select></td>
			</tr>

			<tr style="padding-top: 10px">

				<td style="border: none; font-weight: bold">Ngày sinh</td>
				<td style="border: none;"><form:input id="dateOfBirth"
						name="dateOfBirth" path="dateOfBirth" cssClass="defaultText"
						size="12" maxlength="12" readonly="true" /></td>
				</tr>
			<tr style="padding-top: 10px"><td style="border: none; font-weight: bold; width: 15%;">Nơi sinh</td>
				<td style="border: none; width: 25%;"><form:input
						path="placeOfBirth" id="lastname"
						cssClass="defaultText" size="30" maxlength="30" />
			</tr>
			<tr>

				<td style="border: none; font-weight: bold">Ưu tiên</td>
				<td style="border: none;"><form:input id="dateOfBirth"
						name="priority" path="priority" cssClass="defaultText"
						size="12" maxlength="12" readonly="true" /></td>
				</tr>
			<tr style="padding-top: 10px"><td style="border: none; font-weight: bold; width: 15%;">Ngày phát hành</td>
				<td style="border: none;"><form:input id="dateOfBirth"
						name="releaseDate" path="releaseDate" cssClass="defaultText"
						size="12" maxlength="12" readonly="true" /></td>
			</tr>
		</table>

		<table style="width: 100%; text-align: right;">
			<tr>
				<td colspan="2" align="right"
					style="padding: 10px; text-align: right;"><input type="button"
					id="search_btn" class="button_grey" value="Cập nhật" />&nbsp; <input
					type="reset" id="reset_btn" class="button_grey" value="Làm lại" /></td>
			</tr>
		</table>

		<div id="searchResult">
			<table id="jobEnquiryFlexGrid"></table>
		</div>
		</div>
		</div>
		</div>
	</div>
	<!-- </div> -->

	<input type="hidden" id="jid" name="jid">
	<input type="hidden" id="txnId" name="txnId">
	<input type="hidden" id="searchUrl" name="searchUrl"
		value="${searchTxnUrl}" />

</form:form>

<div id="msgDialog" title="Alert">
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
				transQueryList();
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
				$("#nicTransaction.transactionId").val("");
				$("#jobId").val("");
				$("#registrationData.firstnameFull").val("");
				$("#registrationData.surnameFull").val("");
				$("#registrationData.middlenameFull").val("");
				$("#nicTransaction.applnRefNo").val("");
				$("#registrationData.gender").val("");
				$("#dateOfBirth").val("");

				$("#jobEnquiryFlexGrid").empty();
				$("#jobEnquiryFlexGrid").hide();

			});

		});

		function validSearch() {
			if ($("#passportNo").val() == "" && $("#transactionId").val() == ""
					&& $("#applnRefNo").val() == ""
					&& $("#firstname").val() == ""
					&& $("#lastname").val() == ""
					&& $("#middlename").val() == "") {
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
						width : 180,
						align : 'left',
						render : renderView
					}, {
						display : 'Số hồ sơ',
						name : 'applnRefNo',
						width : 150,
						sortable : false,
						align : 'left'
					}, {
						display : 'Ngày nộp đơn',
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
						display : 'Trung tâm tiếp nhận',
						name : 'regSiteCode',
						width : 260,
						sortable : false,
						align : 'left'
					} ],
					sortname : "transactionId",
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
			$('.modal').show();
			document.getElementById('txnId').value = txnId;
			var titleName = "Chi tiết tra cứu giao dịch";
			$("#dialog-approve").dialog('option', 'title', titleName);
			$("#dialog-approve").dialog("option", "width", 1200);
			$("#dialog-approve").dialog("option", "height", 480);
			$("#dialog-approve").dialog("option", "maxHeight", 480);
			$("#dialog-approve").dialog("option", "resizable", false);
			$("#dialog-approve").dialog('open');
			$.ajax({
				type : "GET",
				url : "${txnEnqDetailsInitUrl}",
				success : function(data) {
					$("#dialog-approve").html(data);
					$('.modal').hide();
				}
			});
		}

		function doSubmitSearch(form) {
			$('.modal').show();
			form.action = '<c:url value="/servlet/transactionEnquiry/search" />';
			form.submit();
			// $('.modal').hide();
		}

		$("#dateOfBirth").datepicker({
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

		$('#dateOfBirth').datepicker().datepicker('setDate', "");
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