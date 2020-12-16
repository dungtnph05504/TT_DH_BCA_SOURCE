<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib uri="http://displaytag.sf.net" prefix="display" %>

<c:url var="submitUrl" value="/servlet/batchJobMgmt/createJob"/>
<c:url var="jobViewUrl" value="/servlet/batchJobMgmt/viewJob"/>
<c:url var="cancelUrl" value="/servlet/batchJobMgmt/view"/>
<c:url var="jobDeleteUrl" value="/servlet/batchJobMgmt/deleteJob"/>
<c:url var="jobUpdateUrl" value="/servlet/batchJobMgmt/updateJob"/>
<style>
.fix-bottom {
	margin-top: 15px;
}
.fix-bottom-1 {
	margin-top: 25px;
}
</style>
<!-- <div id="main"> -->
<div id="content_main">
<form:form  modelAttribute="eppBatchJobForm"  id="eppBatchJobForm"  name="eppBatchJobForm">
	<div class="container">
	        	<div class="row">
	        		<div class="roundedBorder ov_hidden">
       <div class="new-heading-2">THÊM MỚI CÔNG VIỆC</div>
	   <c:if test="${not empty requestScope.Errors}">
			<div
				style="color: red; background-color: White; border-style: solid; border-color: green; border-width: thin;">
				<c:forEach items="${requestScope.Errors}" var="takla">
					<li>${takla}</li>
				</c:forEach>

			</div>
		</c:if>
		<form:hidden id="mode" path="mode" />
		<div class="col-md-12">
			<div class="col-md-6">
				<div class="col-md-4 fix-bottom-1">					
					<label >Mã công việc<i style="color: red">(*)</i></label>
				</div>
				<div class="col-md-8 fix-bottom-1" style="height: 40px;">
					<c:if test='${not empty eppBatchJobForm.mode}'>
						<span><c:out value="${jobId}" /></span><form:hidden id="jobId" path="jobId" />
					</c:if>
					<c:if test='${empty eppBatchJobForm.mode}'>
						<form:input id="jobId" path="jobId" type="text" size='70' maxlength="10" class="form-control"/>
					</c:if>
				</div>
				<div class="col-md-4 fix-bottom-1">
					<label>Tên công việc<i style="color: red">(*)</i></label>
				</div>
				<div class="col-md-8 fix-bottom-1" style="height: 40px;">
					<c:if test='${not empty eppBatchJobForm.mode}'>
						<span><c:out value="${eppBatchJobForm.jobName}" /></span> <form:hidden id="jobName" path="jobName" />
					</c:if>
					<c:if test='${empty eppBatchJobForm.mode}'>
						<form:input id="jobName" path="jobName" type="text" size='70' maxlength="80" class="form-control"/>
					</c:if>
				</div>
				<div class="col-md-4 fix-bottom-1">
					<label>Tên lớp<i style="color: red">(*)</i></label>
				</div>
				<div class="col-md-8 fix-bottom-1" style="height: 40px;">
					<c:if test='${not empty eppBatchJobForm.mode}'>
						<span><c:out value="${eppBatchJobForm.jobClass}" /></span> <form:hidden id="jobClass" path="jobClass"></form:hidden>
					</c:if>
					<c:if test='${empty eppBatchJobForm.mode}'>
						<form:input id="jobClass" path="jobClass" type="text" class="form-control" size='70' maxlength="128"></form:input>
					</c:if>
				</div>				
			 </div>
			 <div class="col-md-6" style="margin-top: 10px;">
			 	 <div class="col-md-4 fix-bottom">
					<label >Mô tả công việc<i style="color: red">(*)</i></label>
				</div>
				<div class="col-md-8 fix-bottom">
					<form:textarea id="jobDesc" path="jobDesc" rows="4" cols="80" maxlength="200" class="form-control"></form:textarea>
				</div>
				<div class="col-md-4 fix-bottom">
					<label>Thuộc tính việc làm</label>
				</div>
				<div class="col-md-8 fix-bottom">
					<form:textarea id="jobProperties" path="jobProperties" rows="4" cols="80" class="form-control"></form:textarea>
				</div>
			 </div>
          </div>
          <div class="col-md-12" style="text-align: right;margin-top: 30px;">
				<button type="button" class="btn btn-danger" onclick="cancelPag();" id="cancel_button">
					<span class="glyphicon glyphicon-remove"></span> Hủy
				</button>
				<!--<input type="button" id="cancel_button" class="btn btn-danger"  onclick="cancelPag();" value="Hủy" />-->
				<c:if test='${not empty eppBatchJobForm.mode}'>
					<button type="button" class="btn btn-warning" id="del_button" onclick="submitJobDelete()">
						<span class="glyphicon glyphicon-trash"></span> Xóa công việc
					</button>
					<button type="button" class="btn btn-primary" id="jshd_button" onclick="submitJobSchedule()">
						<span class="glyphicon glyphicon-th-list"></span> Lịch làm việc
					</button>
					<button type="button" class="btn btn-primary" id="amd_button" onclick="submitAmend()">
						<span class="glyphicon glyphicon-ok"></span> Sửa đổi
					</button>
					<!--<input type="button" onclick="submitJobDelete()" class="btn btn-warning" id="del_button" value="Xóa công việc" />
					<input type="button" onclick="submitJobSchedule()" class="btn btn-default" id="jshd_button" value="Lịch làm việc" />
					<input type="button" onclick="submitAmend()" class="btn btn-info" id="amd_button" value="Sửa đổi" />-->
				</c:if>
				<c:if test='${empty eppBatchJobForm.mode}'>
					<button type="button" class="btn btn-success" id="add_button" onclick="saveForm('save')">
						<span class="glyphicon glyphicon-ok"></span> Thêm công việc
					</button>
					<!--<input type="button" onclick="saveForm('save')" class="btn btn-success" id="add_button" value="Thêm công việc" />-->
				</c:if> 
          </div>
		<!--<div id="addJobDiv" class="displayTag" style="background-image:url('<%=request.getContextPath() %>/resources/images/head.png');background-repeat: repeat-x; padding: 10px 0 10px 0;">
			<table style="width: 100%; class="data_table">
				<c:if test='${not empty eppBatchJobForm.mode}'>
					<tr>
						<td width="10%" style="padding: 10px 0 5px 5px;"><span>Mã công việc:</span></td>
						<td><c:out value="${jobId}" /> <form:hidden id="jobId" path="jobId" /></td>
					</tr>
					<tr>
						<td width="10%" style="padding: 10px 0 5px 5px;"><span>Tên công việc:</span></td>
						<td><span><c:out value="${eppBatchJobForm.jobName}" /></span> <form:hidden id="jobName" path="jobName" /></td>
					</tr>
					<tr>
						<td style="padding: 10px 0 5px 5px;"><span>Tên lớp:</span></td>
						<td><span><c:out value="${eppBatchJobForm.jobClass}" /></span> <form:hidden id="jobClass" path="jobClass"></form:hidden></td>
					</tr>
				</c:if>
				<c:if test='${empty eppBatchJobForm.mode}'>
					<tr>
						<td width="10%" style="padding: 10px 0 5px 5px;"><span>Mã công việc<span style="COLOR: #ff0000;">*</span>:</span>
						</td>
						<td><span><form:input id="jobId" path="jobId" type="text" size='70'
								maxlength="10" /></span></td>
					</tr>
					<tr>
						<td width="10%" style="padding: 10px 0 5px 5px;"><span>Tên công việc<span style="COLOR: #ff0000;">*</span>:</span>
						</td>
						<td><span><form:input id="jobName" path="jobName" type="text"
								size='70' maxlength="80" /></span></td>
					</tr>
					<tr>
						<td style="padding: 10px 0 5px 5px;"><span>Tên lớp<span style="COLOR: #ff0000;">*</span>:</span>
						</td>
						<td><span><form:input id="jobClass" path="jobClass" type="text"
								size='70' maxlength="128"></form:input></span></td>
					</tr>
				</c:if>
				<tr>
					<td width="10%" style="padding: 10px 0 5px 5px;"><span>Mô tả công việc<span style="COLOR: #ff0000;">*</span>:</span>
					</td>
					<td width="50%" style="padding: 5px 0 5px 5px;"><span><form:textarea id="jobDesc" path="jobDesc" rows="8" cols="80" maxlength="200" style="width: 513px;"></form:textarea></span></td>
				</tr>
				<tr>
					<td width='10%' style="padding: 10px 0 5px 5px;"><span>Thuộc tính việc làm:</span></td>
					<td width="50%"  style="padding: 5px 0 5px 5px;"><span><form:textarea id="jobProperties" path="jobProperties" rows="8" cols="80" style="width: 513px;"></form:textarea></span></td>
				</tr>
			</table>
		</div>-->
		<!--<div id="addbutton" style="background-repeat: repeat-x;height: 50px;margin-top: 5px;">
			<table width="100%" height="46px">
				<tr>
					<td colspan="5" style="text-align: center; padding-right: 10px;"><span>
						<c:if test='${not empty eppBatchJobForm.mode}'>
							<input type="button" onclick="submitAmend()" class="button_grey" style="width:100px; height: 25px;" id="amd_button" value="Sửa đổi" />
							<input type="button" onclick="submitJobSchedule()" class="button_grey" style="width:100px; height: 25px;" id="jshd_button" value="Lịch làm việc" />
							<input type="button" onclick="submitJobDelete()" class="button_grey" style="width:100px; height: 25px;" id="del_button" value="Xóa công việc" />
						</c:if>
						<c:if test='${empty eppBatchJobForm.mode}'>
							<input type="button" onclick="saveForm('save')" class="button_grey" style="width:100px; height: 25px;" id="add_button" value="Thêm công việc" />
						</c:if> 
						<input type="button" class="button_grey" style="width:100px; height: 25px;" id="cancel_button" onclick="cancelPag()" value="Hủy bỏ" />
						</span></td>
				</tr>
			</table>
		</div>-->
		</div>
		</div>
		</div>
	</form:form>
</div>
 <!-- </div> -->
 <div id="msgDialog" title="Cảnh báo" />
 <div align="left">
 <ol id="msgDialogSpan" >
 </ol>
</div>
<script>
function saveForm(task) {
	
	if (($("#jobId").val() != "") && !/^[a-zA-Z0-9]+$/.test($("#jobId").val())) {
		$("#msgDialogSpan").html("Mã công việc không phải là chữ số.");
		$("#msgDialog").dialog("open");
		return false;
	}
	
	if (validInputData() == false) {
		var errMsg = "Đã xảy ra lỗi sau";
		if ($("#jobId").val() == "") {
			errMsg += "<li>Mã công việc là bắt buộc</li>";
		}
		if ($("#jobName").val() == "") {
			errMsg += "<li>Tên công việc là bắt buộc</li>";
		}
		if ($("#jobClass").val() == "" ) {
			errMsg += "<li>Tên lớp là bắt buộc</li>";
		}
		if ($("#jobDesc").val() == "") {
			errMsg += "<li>Mô tả công việc là bắt buộc</li>";
		}
		
		$("#msgDialogSpan").html(errMsg);
		$("#msgDialog").dialog("open");
		return false;
	}
	
	document.forms["eppBatchJobForm"].action = '${submitUrl}';
	document.forms["eppBatchJobForm"].submit();
	$(".border_success").show();

	}
	function submitJobSchedule() {
		document.forms["eppBatchJobForm"].action = '${jobViewUrl}/'+$("#jobId").val();
		document.forms["eppBatchJobForm"].submit();

	}
	function cancelPag() {

		document.forms["eppBatchJobForm"].action = '${cancelUrl}';
		document.forms["eppBatchJobForm"].submit();
	}

	function submitJobDelete() {
		document.forms["eppBatchJobForm"].action = '${jobDeleteUrl}';
		document.forms["eppBatchJobForm"].submit();

	}

	function submitAmend() {
		document.forms["eppBatchJobForm"].action = '${jobUpdateUrl}';
		document.forms["eppBatchJobForm"].submit();

	}

	function validInputData() {
		if ($("#jobId").val() == "" || $("#jobName").val() == ""
				|| $("#jobClass").val() == "" || $("#jobDesc").val() == "") {
			return false
		}
		return true;
	}

	$(function() {
		$("#msgDialog").dialog({
			width : 400,
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
	});
</script>