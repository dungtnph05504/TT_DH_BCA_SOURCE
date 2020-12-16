<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>

<c:url var="backUrl" value="/servlet/batchJobMnt/scheDetail" />
<c:url var="getScheLogUrl" value="/servlet/batchJobMnt/getScheLog" />
<style>
.fix-form-ra {
	background-color: white;
	margin-top: 30px;
	width: 1200px;
	margin-left: 80px;
	box-shadow: 0px 5px 10px #392f2f;
	padding: 10px;
}	
.fix-bottom-1 {
	margin-top: 15px;
}
</style>
<script type="text/javascript">
	var reload = "0";
	$(function() {
		ajaxScheDetailList();
		
		$("#btn_back").click(function() {
			document.forms["monitorForm"].action = '${backUrl}/'+$("#jobId").val();
			document.forms["monitorForm"].submit();
		});

		$( "#filterByName" ).change(function() {
			var orig = $("#monitorForm").serialize();
			var withoutEmpties = orig.replace(/[^&]+=\.?(?:&|$)/g, '');
			var actionUrl = '${getScheLogUrl}' + '?' + withoutEmpties;
			$("#logListFlexGrid").flexOptions({
				url : actionUrl
				, newp : 1
			}).flexReload();
		});
	});

	function ajaxScheDetailList() {
		var orig = $("#monitorForm").serialize();
		var withoutEmpties = orig.replace(/[^&]+=\.?(?:&|$)/g, '');
		var actionUrl = '${getScheLogUrl}' + '?' + withoutEmpties;
		$('#logListFlexGrid').empty();
		$("#logList").show();
		$("#filterType").show()
		$("#noDataMsg").hide();
		$('#logListFlexGrid').show();
		if (reload == "0") {
			reload = "1";
			$("#logListFlexGrid").flexigrid(
			{
				url : actionUrl,
				dataType : 'json',
				colModel : [
				{
					display : 'Type',
					name : 'detailType',
					width : 50,
					sortable : false,
					align : 'center',
					render : renderImg
				},
				{
					display : 'Id',
					name : 'detailId',
					sortable : true,
					width : 100,
					align : 'left'
				},
				{
					display : 'Description',
					name : 'message',
					sortable : true,
					width : 1130,
					align : 'left'
				}],
				sortname : "detailId",
				sortorder : "asc",
				title : 'Job Execution Details',
				usepager : true,
				useRp : true,
				rp : 20,
				rpOptions : [ 1, 5, 10, 20, 30, 50 ], //allowed per-page values
				procmsg : 'Processing, Please wait ...',
				pagestat : 'Displaying {from} to {to} of {total} records',
				showToggleBtn : false,
				showTableToggleBtn : true,
				singleSelect : true,
				nowrap : false,
				width : 'auto',
				height : 'auto',
				onSuccess : reloadFrom
			});
		} else {
			$("#logListFlexGrid").flexOptions({
				url : actionUrl
				, newp : 1
			}).flexReload();
		}
	}

	function renderImg(detailType) {
		var result = "";
		if (detailType == 'I') {
			result = "<img src=\"<c:url value='/resources/images/info_16.gif'/>\" border='0' width='16' height='16' alt='Completed' />";
		} else if (detailType == 'W') {
			result = "<img src=\"<c:url value='/resources/images/warn_16.gif'/>\" border='0' width='16' height='16' alt='Completed' />";
		} else if (detailType == 'E') {
			result = "<img src=\"<c:url value='/resources/images/exclam_16.gif'/>\" border='0' width='16' height='16' alt='Completed' />";
		}

		return result;
	}

	function reloadFrom() {
		var data = $('#logListFlexGrid tbody').children().length;
		if(data <= 0) {
			$("#noDataMsg").show();
			$("#filterType").hide();
			$("#logList").hide();
		} else {
			$("#noDataMsg").hide();
			$("#filterType").show();
			$("#logList").show();
		}
	}
	
</script>
<form:form modelAttribute="eppJobMonitorForm" id="monitorForm" name="monitorForm">
	<div class="fix-form-ra row">
		<div id="heading_report" align="justify" colspan='2' style='padding: 2px'>Giám sát việc làm</div>	
		<button type="button" class="btn btn-info" id="btn_back" style="float: right;margin-top: -50px;">
			<span class="glyphicon glyphicon-remove"></span> Quay lại
		</button>
		<!--<input type="button" id="btn_back" class="btn-sm btn-info" value="Quay lại" style="margin-top: -40px;margin-right: 10px;;font-weight: 250;margin-bottom: 10px;float: right;"/>-->	
		<div class="col-md-12">
			<div class="col-md-6">
				<div class="col-md-4 fix-bottom-1">
					<label for="countryCode">Mã bản ghi</label>
				</div>
				<div class="col-md-8 fix-bottom-1">
					<form:hidden id="logId" path="logId" />
					<span class="form-control"><c:out value="${eppJobMonitorForm.logId}" /></span>
				</div>
				<div class="col-md-4 fix-bottom-1">
					<label for="countryCode">Mã công việc</label>
				</div>
				<div class="col-md-8 fix-bottom-1">
					<form:hidden id="jobId" path="jobId" />
					<span class="form-control"><c:out value="${eppJobMonitorForm.jobId}" /></span>
				</div>
				<div class="col-md-4 fix-bottom-1">
					<label for="countryCode">Tên công việc</label>
				</div>
				<div class="col-md-8 fix-bottom-1">
					<span class="form-control"><c:out value="${eppJobMonitorForm.jobName}" /></span>
				</div>				
			 	 <div class="col-md-4 fix-bottom-1">
					<label for="countryCode">Chấp hành ngày/giờ</label>
				</div>
				<div class="col-md-8 fix-bottom-1">
					<span class="form-control"><c:out value="${eppJobMonitorForm.executionDate}" /></span>
				</div>
				<div class="col-md-4 fix-bottom-1">
					<label for="countryCode">Hoàn thành ngày/giờ</label>
				</div>
				<div class="col-md-8 fix-bottom-1">
					<span class="form-control"><c:out value="${eppJobMonitorForm.completeDate}" /></span>
				</div>
				<div class="col-md-4 fix-bottom-1">
					<label for="countryCode">Trạng thái</label>
				</div>
				<div class="col-md-8 fix-bottom-1" style="height: 30px;">
					<c:if test="${0 eq eppJobMonitorForm.status}">
						<img src="<c:url value="/resources/images/tick_16.gif"/>"
								border="0" width="16" height="16" alt="Completed" />
					</c:if> 
					<c:if test="${1 eq eppJobMonitorForm.status}">
						<img src="<c:url value="/resources/images/warn_16.gif"/>"
								border="0" width="16" height="16" alt="Completed With Errors" />
					</c:if> 
					<c:if test="${2 eq eppJobMonitorForm.status}">
						<img src="<c:url value="/resources/images/exclam_16.gif"/>"
								border="0" width="16" height="16" alt="Error" />
					</c:if>
				</div>
				<div class="col-md-4 fix-bottom-1">
					<label for="countryCode">Tin nhắn</label>
				</div>
				<div class="col-md-8 fix-bottom-1">
					<span class="form-control"><c:out value="${eppJobMonitorForm.message}" /></span>
				</div>
				<div class="col-md-4 fix-bottom-1">
					<label for="countryCode">Ghi chú</label>
				</div>
				<div class="col-md-8 fix-bottom-1">
					<span class="form-control"><c:out value="${eppJobMonitorForm.detail}" /></span>
				</div>
				<div class="col-md-4 fix-bottom-1">
					<label for="countryCode">Chi tiết thực hiện công việc</label>
				</div>
				<div class="col-md-12 fix-bottom-1">
					<textarea class="form-control" rows="3" id="comment" readonly="readonly">Không tìm thấy gì để hiển thị.</textarea>
				</div>
          </div>			
	</div>
	</div>
	<!--<div id="content_main">
		<div id="heading_report" align="justify" colspan='2'
			style='padding: 2px'>Giám sát việc làm</div>
		<br /> <br />
		<br />
		<div class="displayTag"
			style="background-image:url('<%=request.getContextPath()%>/resources/images/head.png');background-repeat: repeat-x; margin-bottom: 5px; padding-bottom: 5px;">
			<table style="width: 100%;" id="scheRecord">
				<tr>
				<form:hidden id="logId" path="logId" />
					<td width="15%" style="padding: 10px 0 5px 5px;"><span>Mã bản ghi:</span></td>
					<td style="padding: 10px 5px 5px 20px;"><span><c:out
							value="${eppJobMonitorForm.logId}" /></span></td>
				</tr>
				<tr>
					<form:hidden id="jobId" path="jobId" />
					<td width="15%" style="padding: 0 0 5px 5px;"><span>Mã công việc:</span></td>
					<td style="padding: 0 5px 5px 20px;"><span><c:out
							value="${eppJobMonitorForm.jobId}" /></span></td>
				</tr>
				<tr>
					<td width="15%" style="padding: 0 0 5px 5px;"><span>Tên công việc:</td>
					<td style="padding: 0 0 5px 20px;"><span><c:out
							value="${eppJobMonitorForm.jobName}" /></td>
				</tr>
			</table>
		</div>

		<div class="displayTag"
			style="background-image:url('<%=request.getContextPath()%>/resources/images/head.png');background-repeat: repeat-x; margin-bottom: 5px; padding-bottom: 5px;">
			<table style="width: 100%;" id="scheRecord">
				<tr>
					<td width="15%" style="padding: 10px 0 5px 5px;"><span>Chấp hành
						Ngày/Giờ:</span></td>
					<td style="padding: 10px 5px 5px 20px;"><span><c:out
							value="${eppJobMonitorForm.executionDate}" /></span></td>
				</tr>
				<tr>
					<td width="15%" style="padding: 0 0 5px 5px;"><span>Hoàn thành
						Ngày/Giờ:</span></td>
					<td style="padding: 0 5px 5px 20px;"><span><c:out
							value="${eppJobMonitorForm.completeDate}" /></span></td>
				</tr>
				<tr>
					<td width="15%" style="padding: 0 0 5px 5px;"><span>Trạng thái:</span></td>
					<td style="padding: 0 0 5px 20px;"><c:if
							test="${0 eq eppJobMonitorForm.status}">
							<img src="<c:url value="/resources/images/tick_16.gif"/>"
								border="0" width="16" height="16" alt="Completed" />
						</c:if> <c:if test="${1 eq eppJobMonitorForm.status}">
							<img src="<c:url value="/resources/images/warn_16.gif"/>"
								border="0" width="16" height="16" alt="Completed With Errors" />
						</c:if> <c:if test="${2 eq eppJobMonitorForm.status}">
							<img src="<c:url value="/resources/images/exclam_16.gif"/>"
								border="0" width="16" height="16" alt="Error" />
						</c:if></td>
				</tr>
				<tr>
					<td width="15%" style="padding: 0 0 5px 5px;"><span>Tin nhắm:</span></td>
					<td style="padding: 0 0 5px 20px;"><span><c:out
							value="${eppJobMonitorForm.message}" /></span></td>
				</tr>
				<tr>
					<td width="15%" style="padding: 0 0 5px 5px;"><span>Ghi chú:</span></td>
					<td style="padding: 0 0 5px 20px;"><span><c:out
							value="${eppJobMonitorForm.detail}" /></span></td>
				</tr>
			</table>
		</div>
		<div class="displayTag" id="noDataMsg"
			style="background-image:url('<%=request.getContextPath()%>/resources/images/head.png');background-repeat: repeat-x; margin-bottom: 5px; padding-bottom: 5px;display: none;">
			<fieldset>
     				<legend><span> Job Execution Details</span></legend>
			<span style="padding: 5px; font-style: italic;">Không tìm thấy gì để hiển thị.</span>
			</fieldset>
		</div>
		<div style="padding: 5px; display: none;" id="filterType">
			<table width='100%' align="center">
				<tr>
					<td>
						<div id="list">
							<table align="right">
								<tr>
									<td><span style="cursor: pointer;"> <form:select
												id="filterByName" path="filterByName">
												<c:forEach var="maplist" items="${filterType}"
													varStatus="status1">
													<form:option value="${maplist.value}"
														label="${maplist.label }" />
												</c:forEach>
											</form:select>
									</span></td>
								</tr>
							</table>
						</div>
					</td>
				</tr>
			</table>
		</div>
		
		<div id="logList" style="display: none;">
			<table id="logListFlexGrid" ></table>
		</div>
		
		<div id="addbutton" class="displayTag"
			style="background-image:url('<%=request.getContextPath()%>/resources/images/head.png');background-repeat: repeat-x;height: 50px;margin-top: 5px;">
			<table width="100%" height="46px">
				<tr>
					<td colspan="2" style="text-align: right; padding-right: 10px;">
						<input id="btn_back" type="button" class="button_grey"
						style="width: 100px; height: 25px;" value="Quay lại" />
					</td>
				</tr>
			</table>
		</div>
		</div>-->
</form:form>