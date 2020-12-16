<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>

<c:url var="jobDetailsUrl" value="/servlet/nicEnquiry/jobDetails" />
<c:url var="jobEnqDgDataUrl" value="/servlet/nicEnquiry/jobEnqDgData" />
<c:url var="jobEnqHitListUrl" value="/servlet/nicEnquiry/jobEnqHitList" />
<c:url var="jobEnqTransUrl" value="/servlet/nicEnquiry/jobEnqTrans" />
<c:url var="jobEnqHistoryEventsUrl"
	value="/servlet/nicEnquiry/jobEnqHistoryEvents" />
<c:url var="jobEnqJobDetailsUrl"
	value="/servlet/nicEnquiry/jobEnqJobDetails" />
<c:url var="jobEnqDemographicDetailsUrl"
	value="/servlet/nicEnquiry/jobEnqDemographicDetails" />
<c:url var="personSummaryUrl"
	value="/servlet/nicEnquiry/getPersonSummary" />
<c:url var="transDocumentsUrl"
	value="/servlet/nicEnquiry/getTransDocuments" />

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

		$("#close_btn").click(function(){
			 $("#dialog-approve").dialog('close');	
		});
	
		var jobid = $("#jid").val();

		if (jobid == "" || trim(jobid) == "") {
			$('#jobQueueDetails').html('Không tìm thấy chi tiết công việc để hiển thị');
		} else {
			//$('.modal').show();
			$.ajax({
				type : "GET",
				url : '${jobEnqJobDetailsUrl}/' + jobid,
				data : $("#nicEnqDetailsForm").serializeArray(),
				success : function(data) {
					$('#jobQueueDetails').html(data);
					//$('.modal').hide();
				}
			});
		}

		
		$("#retrydialog-confirm").dialog({
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
				"Đồng ý" : function() {
					$(this).dialog("close");
					var jobid = $("#jid").val();
					//$('.modal').show();
					$
							.ajax({
								type : "GET",
								url : '${jobEnqJobDetailsUrl}/'
										+ jobid,
								data : $("#nicEnqDetailsForm")
										.serializeArray(),
								success : function(data) {
									$('#jobQueueDetails').html(
											data);
									$('.modal').hide();
								},
								error : function(e) {
									$('#jobQueueDetails').html(
											'Đã có lỗi xảy ra khi lấy thông tin chi tiết cho công việc:'
													+ jobid);
									//$('.modal').hide();
								}
							});
				}
			}
		});
		/* 
		 $('#TabbedPanels1 li:eq(0)').on('click', function() {
		 var jobid = $("#jid").val();
		 if (jobid == "" || trim(jobid) == "") {
		 $('#jobQueueDetails').html('No Job Details found to display');
		 } else {
		 $('.modal').show();
		 $.ajax({
		 type : "GET",
		 url : '${jobEnqJobDetailsUrl}/' + jobid,
		 data : $("#nicEnqDetailsForm").serializeArray(),
		 success : function(data) {
		 $('#jobQueueDetails').html(data);
		 $('.modal').hide();
		 }
		 });
		 }
		 });

		 $('#TabbedPanels1 li:eq(1)').on(
		 'click',
		 function() {
		 var jobid = $("#jid").val();
		 if (jobid == "" || trim(jobid) == "") {
		 $('#demographicDetails').html(
		 'No Demographic Details found to display');
		 } else {
		 $('.modal').show();
		 $.ajax({
		 type : "GET",
		 url : '${jobEnqDemographicDetailsUrl}/' + jobid,
		 data : $("#nicEnqDetailsForm").serializeArray(),
		 success : function(data) {
		 $('#demographicDetails').html(data);
		 $('.modal').hide();
		 }
		 });
		 }
		 });

		 $('#TabbedPanels1 li:eq(4)').on('click', function() {
		 var jobid = $("#jid").val();
		 if (jobid == "" || trim(jobid) == "") {
		 $('#hitList').html('No Hit Records found to display');
		 } else {
		 $('.modal').show();
		 $.ajax({
		 type : "GET",
		 url : '${jobEnqHitListUrl}/' + jobid,
		 data : $("#nicEnqDetailsForm").serializeArray(),
		 success : function(data) {
		 $('#hitList').html(data);
		 $('.modal').hide();
		 }
		 });
		 }
		 });

		 $('#TabbedPanels1 li:eq(5)').on('click', function() {
		 var txnId = $("#txnId").val();
		 $('.modal').show();
		 $.ajax({
		 type : "GET",
		 url : '${jobEnqTransUrl}/' + txnId,
		 data : $("#nicEnqDetailsForm").serializeArray(),
		 success : function(data) {
		 $('#transactionInfo').html(data);
		 $('.modal').hide();
		 }
		 });
		 });

		 $('#TabbedPanels1 li:eq(6)').on('click', function() {
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

		 $('#TabbedPanels1 li:eq(2)').on('click', function() {
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

		 $('#TabbedPanels1 li:eq(3)').on('click', function() {
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

		 });
		 */

		$('#TabbedPanels1 li:eq(0)').on('click', function() {
			var jobid = $("#jid").val();
			if (jobid == "" || trim(jobid) == "") {
				$('#jobQueueDetails').html('Không tìm thấy chi tiết công việc để hiển thị');
			} else {
				//$('.modal').show();
				$.ajax({
					type : "GET",
					url : '${jobEnqJobDetailsUrl}/' + jobid,
					data : $("#nicEnqDetailsForm").serializeArray(),
					success : function(data) {
						$('#jobQueueDetails').html(data);
						//$('.modal').hide();
					}
				});
			}
		});

		/* 	$('#TabbedPanels1 li:eq(1)').on(
		 'click',
		 function() {
		 var jobid = $("#jid").val();
		 if (jobid == "" || trim(jobid) == "") {
		 $('#demographicDetails').html(
		 'No Demographic Details found to display');
		 } else {
		 $('.modal').show();
		 $.ajax({
		 type : "GET",
		 url : '${jobEnqDemographicDetailsUrl}/' + jobid,
		 data : $("#nicEnqDetailsForm").serializeArray(),
		 success : function(data) {
		 $('#demographicDetails').html(data);
		 $('.modal').hide();
		 }
		 });
		 }
		 }); */
		 $('#TabbedPanels1 li:eq(1)').on('click', function() {
			var txnId = $("#txnId").val();
			//$('.modal').show();
			$.ajax({
				type : "GET",
				url : '${transDocumentsUrl}/' + txnId,
				data : $("#nicEnqDetailsForm").serializeArray(),
				success : function(data) {
					$('#transDocuments').html(data);
					//$('.modal').hide();
				}
			});
		});
			
		$('#TabbedPanels1 li:eq(2)').on('click', function() {
			var jobid = $("#jid").val();
			if (jobid == "" || trim(jobid) == "") {
				$('#hitList').html('Không tìm thấy kết quả để hiển thị');
			} else {
				//$('.modal').show();
				$.ajax({
					type : "GET",
					url : '${jobEnqHitListUrl}/' + jobid,
					data : $("#nicEnqDetailsForm").serializeArray(),
					success : function(data) {
						$('#hitList').html(data);
						//$('.modal').hide();
					}
				});
			}
		});

		$('#TabbedPanels1 li:eq(3)').on('click', function() {
			var txnId = $("#txnId").val();
			//$('.modal').show();
			$.ajax({
				type : "GET",
				url : '${jobEnqTransUrl}/' + txnId,
				data : $("#nicEnqDetailsForm").serializeArray(),
				success : function(data) {
					$('#transactionInfo').html(data);
					//$('.modal').hide();
				}
			});
		});

		$('#TabbedPanels1 li:eq(4)').on('click', function() {
			var txnId = $("#txnId").val();
			//$('.modal').show();
			$.ajax({
				type : "GET",
				url : '${jobEnqHistoryEventsUrl}/' + txnId,
				data : $("#nicEnqDetailsForm").serializeArray(),
				success : function(data) {
					$('#historyEvents').html(data);
					//$('.modal').hide();
				}
			});
		});

		$('#TabbedPanels1 li:eq(5)').on('click', function() {
			var txnId = $("#txnId").val();
			//$('.modal').show();
			$.ajax({
				type : "GET",
				url : '${personSummaryUrl}/' + txnId,
				data : $("#nicEnqDetailsForm").serializeArray(),
				success : function(data) {
					$('#personSummary').html(data);
					//$('.modal').hide();
				}
			});
		});

	});

	function trim(str) {
		return str.replace(/^\s+|\s+$/g, "");
	}
</script>


<form:form modelAttribute="nicEnqDetailsForm" id="form1" action="/servlet/nicEnquiry/search">

	<div id="TabbedPanels1" class="TabbedPanels">
		<ul class="TabbedPanelsTabGroup" >
			<li class="TabbedPanelsTab" tabindex="0">Chi tiết công việc</li>
			<!-- <li class="TabbedPanelsTab" tabindex="1">Demographic Data</li> -->
			<!-- <li class="TabbedPanelsTab" tabindex="5">Person Summary</li> -->
			<li class="TabbedPanelsTab" tabindex="1">Tài liệu</li>
			<li class="TabbedPanelsTab" tabindex="2">Danh sách trùng</li>
			<li class="TabbedPanelsTab" tabindex="3">Thông tin giao dịch</li>
			<li class="TabbedPanelsTab" tabindex="4">Trạng thái giao dịch</li>
			<li class="TabbedPanelsTab" tabindex="5">Thông tin cá nhân</li>
		</ul>
		<div class="TabbedPanelsContentGroup" style="width: auto; height: 355px; max-height:360px; overflow:auto;">
			<div class="TabbedPanelsContent" id="jobQueueDetails"></div>
			<!-- <div class="TabbedPanelsContent" id="demographicDetails"></div> -->
			<!-- <div class="TabbedPanelsContent" id="personSummary"></div> -->
			<div class="TabbedPanelsContent" id="transDocuments"></div>
			<div class="TabbedPanelsContent" id="hitList"></div>
			<div class="TabbedPanelsContent" id="transactionInfo"></div>
			<div class="TabbedPanelsContent" id="historyEvents"></div>
			<div class="TabbedPanelsContent" id="personSummary"></div>
		</div>

	</div>
	<table style="width:100%;">
    	<tr>
        	<td valign="top"  align="center" style="text-align: right; padding-top: 20px;" >
		      <input type="button" class="btn btn-info btn-sm" id="close_btn"  value="Đóng"/>
	        </td>
	    </tr>
      </table>
</form:form>
<div id="retrydialog-confirm" style="display: none;"></div>
<div class="modal" />