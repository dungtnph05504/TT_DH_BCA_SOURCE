<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>
<%@ taglib prefix="col" uri="colfunction"%>
<c:url var="jobEnqDgDataUrl" value="/servlet/transactionEnquiry/jobEnqDgData" />
<c:url var="jobEnqHitListUrl" value="/servlet/transactionEnquiry/jobEnqHitList" />
<c:url var="txnEnqTransUrl" value="/servlet/transactionEnquiry/txnEnqTrans" />
<c:url var="jobEnqHistoryEventsUrl" value="/servlet/transactionEnquiry/jobEnqHistoryEvents" />
<c:url var="jobEnqDemographicDetailsUrl" value="/servlet/transactionEnquiry/jobEnqDemographicDetails" />
<c:url var="personSummaryUrl" value="/servlet/transactionEnquiry/getPersonSummary" />
<c:url var="passportInfoUrl" value="/servlet/transactionEnquiry/getPassportInfo" />
<c:url var="transDocumentsUrl" value="/servlet/transactionEnquiry/getTransDocuments" />

<c:url var="prioritizeUrl" value="/servlet/transactionEnquiry/prioritize" />
<c:url var="reprintUrl" value="/servlet/transactionEnquiry/reprint" />

<style>
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
.ui-dialog .ui-dialog-content {
	padding-top: 1.5em;
}

.TabbedPanelsTab {
	padding: 0 10px;
}
</style>

<script type="text/javascript">
	var TabbedPanels1 = new Spry.Widget.TabbedPanels("TabbedPanels1", {
		defaultPanel : 1
	});

	$(function() {


		$("#close_btn").click(function() {
			$("#dialog-approve").dialog('close');
		});

		var transId = $("#txnId").val();

		if (transId == "" || trim(transId) == "") {
			$('#transactionInfo').html('Không tìm thấy thông tin giao dịch nào hiển thị');
		} else {
			$('.modal').show();
			$.ajax({
				type : "GET",
				url : '${txnEnqTransUrl}/' + transId,
				data : $("#nicEnqDetailsForm").serializeArray(),
				success : function(data) {
					$('#transactionInfo').html(data);
					$('.modal').hide();
				}
			});
		}


		$("#dialog-reprint-confirm").dialog({
			modal : true,
			autoOpen : false,
			width : 500,
			resizable : true,
			show : {
				effect : "fade",
				duration : 200
			},
			buttons : {
				Yes : function() {
					$("#cancelPptFlag").val("Y");
					$(this).dialog('close');
					$("#dialog-reprint-remarks").dialog("open");
				},
				No : function() {
					$("#cancelPptFlag").val("N");
					$(this).dialog('close');
					$("#dialog-reprint-remarks").dialog("open");
				}
			}
		});

		
		$("#retrydialog-confirm")
				.dialog(
						{
							modal : true,
							autoOpen : false,
							width : 500,
							resizable : true,
							show : {
								effect : "fade",
								duration : 200
							},
							hide : {
							//effect: "explode",
							//duration: 1000
							},
							buttons : {
								Ok : function() {
									$(this).dialog("close");
									var transId = $("#txnId").val();
									$('.modal').show();
									$.ajax({
										type : "GET",
										url : '${txnEnqTransUrl}/'
												+ transId,
										data : $("#nicEnqDetailsForm")
												.serializeArray(),
										success : function(data) {
											$('#transactionInfo').html(
													data);
											$('.modal').hide();
										},
										error : function(e) {
											$('#transactionInfo').html(
													'Đã xảy ra lỗi trong khi tải thông tin giao dịch cho giao dịch:'
															+ transId);
											$('.modal').hide();
										}
									});
								}
							}
						});

		$('#TabbedPanels1 li:eq(0)').on('click', function() {
			var txnId = $("#txnId").val();
			$('.modal').show();
			$.ajax({
				type : "GET",
				url : '${txnEnqTransUrl}/' + txnId,
				data : $("#nicEnqDetailsForm").serializeArray(),
				success : function(data) {
					$('#transactionInfo').html(data);
					$('.modal').hide();
				}
			});
		});

		
		$('#TabbedPanels1 li:eq(1)').on('click', function() {
			var txnId = $("#txnId").val();
			$('.modal').show();
			$.ajax({
				type : "GET",
				url : '${transDocumentsUrl}/' + txnId,
				data : $("#nicEnqDetailsForm").serializeArray(),
				success : function(data) {
					$('#transDocuments').html(data);
					$('.modal').hide();
				}
			});
		});

		$('#TabbedPanels1 li:eq(2)').on('click', function() {
			var txnId = $("#txnId").val();
			if (txnId == "" || trim(txnId) == "") {
				$('#hitList').html('Không tìm thấy giao dịch tra cứu được hiển thị');
			} else {
				$('.modal').show();
				$.ajax({
					type : "GET",
					url : '${jobEnqHitListUrl}/' + txnId,
					data : $("#nicEnqDetailsForm").serializeArray(),
					success : function(data) {
						$('#hitList').html(data);
						$('.modal').hide();
					}
				});
			}
		});


		$('#TabbedPanels1 li:eq(3)').on('click', function() {
			var txnId = $("#txnId").val();
			$('.modal').show();
			$.ajax({
				type : "GET",
				url : '${jobEnqHistoryEventsUrl}/' + txnId,
				data : $("#nicEnqDetailsForm").serializeArray(),
				success : function(data) {
					$('#historyEvents').html(data);
					$('.modal').hide();
				}
			});
		});

		$('#TabbedPanels1 li:eq(4)').on('click', function() {
			var txnId = $("#txnId").val();
			$('.modal').show();
			$.ajax({
				type : "GET",
				url : '${personSummaryUrl}/' + txnId,
				data : $("#nicEnqDetailsForm").serializeArray(),
				success : function(data) {
					$('#personSummary').html(data);
					$('.modal').hide();
				}
			});
		});
		
		$('#TabbedPanels1 li:eq(5)').on('click', function() {
			var txnId = $("#txnId").val();
			$('.modal').show();
			$.ajax({
				type : "GET",
				url : '${passportInfoUrl}/' + txnId,
				data : $("#nicEnqDetailsForm").serializeArray(),
				success : function(data) {
					$('#passportInfo').html(data);
					$('.modal').hide();
				}
			});
		});
	});

	
	$("#dialog-reprint-remarks").dialog({
	    autoOpen  : false,
	    modal     : true,
		width : 600,
		height : 350,
		resizable : false,
	    title     : "Reprint Remarks",
	    buttons   : {
	              'OK' : function() {
	      			var txnId = $("#txnId").val();
	      			var remarks =  $("#txtRemarks").val(); 
	      			var cancelPptFlag = $("#cancelPptFlag").val();
					if (remarks == "" || $.trim(remarks).length == 0) {
						$("#retrydialog-confirm").dialog('option','title', 'Warning');
						$("#retrydialog-confirm").html('Nhận xét không được để trống!');
						$("#retrydialog-confirm").dialog('open');
						return;
					} else{
						$(this).dialog('close');
		    			$('.modal').show();
		    			$.ajax({
		    				type : "POST",
		    				url : '${reprintUrl}',
		    				data : {
								transactionId : txnId,
								remarks: remarks,
								cancelPptFlag: cancelPptFlag
							}, 
		    				success : function(data) {
		    					
		    					if (data == 'success') {
		    						$("#dialog-approve").dialog('close');
		    						callDialog(txnId);
		    						$("#retrydialog-confirm").dialog('option','title', 'Status');
		    						$("#retrydialog-confirm").html('In lại giao dịch thành công: '+ txnId);
		    						$("#retrydialog-confirm").dialog('open');
		    					} else if (data == 'fail') {
		    						$("#retrydialog-confirm").dialog('option', 'title', 'Status');
		    						$("#retrydialog-confirm").html('Không thể in lại giao dịch : ' + txnId);
		    						$("#retrydialog-confirm").dialog('open');
		    					}
		    					$('.modal').hide();
		    				},
		    				error : function(e) {
		    					$("#retrydialog-confirm").dialog('option', 'title', 'Error');
	    						$("#retrydialog-confirm").html('Không thể in lại giao dịch : ' + txnId);
	    						$("#retrydialog-confirm").dialog('open');
		    					$('.modal').hide();
		    				}
		    			});
						}
	              },
	              'Cancel' : function() {
	                  $(this).dialog('close');
	              }
		}
	});

	$("#dialog-prioritize-remarks").dialog({
	    autoOpen  : false,
	    modal     : true,
		width : 600,
		height : 350,
		resizable : false,
	    title     : "Prioritize Remarks",
	    buttons   : {
	              'OK' : function() {
	      			var txnId = $("#txnId").val();
	      			var remarks =  $("#txtPrioritizeRemarks").val(); 
					if (remarks == "" || $.trim(remarks).length == 0) {
						$("#retrydialog-confirm").dialog('option','title', 'Warning');
						$("#retrydialog-confirm").html('Nhận xét không được để trống!');
						$("#retrydialog-confirm").dialog('open');
						return;
					} else{
						$(this).dialog('close');
		    			$('.modal').show();
		    			$.ajax({
							type : "POST",
							url : '${prioritizeUrl}',
							data : {
								transactionId : txnId,
								remarks: remarks
							}, 
							success : function(data) {
								if (data == 'success') {
									$("#retrydialog-confirm").dialog('option','title', 'Status');
									$("#retrydialog-confirm").html('Xác định ưu tiên giao dịch thành công: '+ txnId);
									$("#retrydialog-confirm").dialog('open');
								} else if (data == 'fail') {
									$("#retrydialog-confirm").dialog('option', 'title', 'Status');
									$("#retrydialog-confirm").html('Không thể ưu tiên giao dịch : ' + txnId);
									$("#retrydialog-confirm").dialog('open');
								}
								$('.modal').hide();
							},
							error : function(e) {
								$('#transactionInfo').html(
										'Đã xảy ra lỗi trong khi ưu tiên giao dịch:' + jobid);
								$('.modal').hide();
							}
						});
						}
	              },
	              'Cancel' : function() {
	                  $(this).dialog('close');
	              }
			}
	});
	
	$("#reprint").click(function() {
		$("#dialog-reprint-confirm").dialog('option','title', 'Question');
		$("#dialog-reprint-confirm").html('Bạn có muốn hủy hộ chiếu cũ?');
		$("#dialog-reprint-confirm").dialog('open');
	});

	$("#prioritize").click(function() {
		$("#dialog-prioritize-remarks").dialog("open");
	});
	function trim(str) {
		return str.replace(/^\s+|\s+$/g, "");
	}
</script>


<form:form modelAttribute="nicEnqDetailsForm" id="enqDetailsForm" action="/servlet/transactionEnquiry/search">
<div class="container">
			<div class="row">
				<div class="roundedBorder ov_hidden">
	<div id="TabbedPanels1" class="TabbedPanels">
		<ul class="TabbedPanelsTabGroup">
			<li class="TabbedPanelsTab" tabindex="0">Thông tin giao dịch</li>
			<li class="TabbedPanelsTab" tabindex="1">Tài liệu</li>
			<li class="TabbedPanelsTab" tabindex="2">Danh sách trùng</li>
			<li class="TabbedPanelsTab" tabindex="3">Nhật ký sự kiện</li>
			<li class="TabbedPanelsTab" tabindex="4">Tóm tắt về Người</li>
			<li class="TabbedPanelsTab" tabindex="5">Thông tin hộ chiếu</li>
		</ul>
		<div class="TabbedPanelsContentGroup"
			style="width: auto; height: 480px; max-height: 480px; overflow: auto;">
			<div class="TabbedPanelsContent" id="transactionInfo"></div>
			<div class="TabbedPanelsContent" id="transDocuments"></div>
			<div class="TabbedPanelsContent" id="hitList"></div>
			<div class="TabbedPanelsContent" id="historyEvents"></div>
			<div class="TabbedPanelsContent" id="personSummary"></div>
			<div class="TabbedPanelsContent" id="passportInfo"></div>
		</div>

	</div>
	<table style="width: 100%; height: 100%">
		<tr>
			<td valign="middle" align="center"
				style="text-align: right; padding-top: 5px">
				<c:if test="${not empty sessionScope.userSession}">
					<c:if test="${showPrioritizeBtn}">
						<c:if test="${col:isPreviledged('NIC_TXNENQ_VIEW_PRIORITIZE',sessionScope.userSession)}">
							<input type="button" class="btn_small btn-primary" id="prioritize" value="Ưu tiên" />
						</c:if>	
					</c:if> 
					<c:if test="${showReprintBtn}">
						<c:if test="${col:isPreviledged('NIC_TXNENQ_VIEW_REPRINT',sessionScope.userSession)}">
							<input type="button" class="btn_small btn-primary" id="reprint" value="In lại" />
						</c:if>	
						
					</c:if> 
				</c:if> 
				<input type="button" class="btn_small btn-primary" id="close_btn" value="Đóng" />
			</td>
		</tr>
	</table>
</div>
</div>
</div>
</form:form>
<input type="hidden" id="cancelPptFlag" name="cancelPptFlag">
<div id="retrydialog-confirm" style="display: none;" />
<div id="dialog-reprint-confirm" style="display: none;" />
<div class="modal" />
<div id="dialog-reprint-remarks" style="display: none;" >
	Nhập nhận xét:<br><textarea name="txtRemarks" id="txtRemarks" cols="60" rows="8"/>
</div>
<div id="dialog-prioritize-remarks" style="display: none;" >
	Nhập nhận xét:<br><textarea name="txtPrioritizeRemarks" id="txtPrioritizeRemarks" cols="60" rows="8" />
</div>
