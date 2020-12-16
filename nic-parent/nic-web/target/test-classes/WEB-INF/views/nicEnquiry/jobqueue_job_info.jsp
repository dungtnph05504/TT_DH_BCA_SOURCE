<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<c:url var="retryUrl" value="/servlet/nicEnquiry/retry" />
<script type="text/javascript"
	src="<c:url value="/resources/js/jquery.expander.js"/>"></script>

<style>
fieldset {
	border: 1px solid #000000;
	padding: 10px;
}
</style>
<script type="text/javascript">
	$(function() {
		/* $("#jobInfoClose_btn").click(function() {
			$("#dialog-approve").dialog('close');
		}); */

		$("#retryTrans").click(
				function() {
					var jobid = $("#jid").val();
					$('.modal').show();
					$.ajax({
						type : "GET",
						url : '${retryUrl}/' + jobid,
						data : $("#nicEnqDetailsForm").serializeArray(),
						success : function(data) {
							if (data == 'success') {
								$("#retrydialog-confirm").dialog('option',
										'title', 'Status');
								$("#retrydialog-confirm").html(
										'Đã hoàn thành công việc:  '
												+ jobid);
								$("#retrydialog-confirm").dialog('open');
							} else if (data == 'fail') {
								$("#retrydialog-confirm").dialog('option',
										'title', 'Status');
								$("#retrydialog-confirm").html(
										'Không thể thử lại công việc :  ' + jobid);
								$("#retrydialog-confirm").dialog('open');
							}

							$('.modal').hide();
						},
						error : function(e) {
							$('#jobQueueDetails').html(
									'Đã có lỗi xảy ra trong khi thử lại công việc:'
											+ jobid);
							$('.modal').hide();
						}
					});
				});

		$("#showErrorBtn_btn").click(function() {
			$("#jobErrorMessageInfo").toggle('fast');
			$("#showErrorBtn_btn").toggle('fast');
			$("#hideErrorBtn_btn").toggle('fast');

		});

		$("#hideErrorBtn_btn").click(function() {
			$("#jobErrorMessageInfo").toggle('fast');
			$("#showErrorBtn_btn").toggle('fast');
			$("#hideErrorBtn_btn").toggle('fast');
		});

		$('div.expander').expander({
			slicePoint : 800,
			expandPrefix : ' ',
			expandText : '[...]',
			userCollapseText : '[^]'
		});

	});
</script>


<form:form modelAttribute="nicEnqJobInfoForm" id="form1"
	action="/servlet/nicEnquiry/search" method="post">

	<c:choose>
		<c:when test="${not empty nicUploadJob }">
			<table style="width: 100%" cellspacing="0" cellpadding="0">
				<tr>
					<td style="padding: 5px;">Mã công việc</td>
					<td style="padding: 5px;">:</td>
					<td style="padding: 5px;"><c:out
							value="${nicUploadJob.uploadJobId}"></c:out></td>
					<td style="padding: 5px;">Trạng thái kiểm tra CPD</td>
					<td style="padding: 5px;">:</td>
					<td style="padding: 5px;"><c:out
							value="${nicUploadJob.cpdCheckStatus}"></c:out></td>
				</tr>

				<tr>
					<td style="padding: 5px;">Loại giao dịch</td>
					<td style="padding: 5px;">:</td>
					<td style="padding: 5px;"><c:out
							value="${nicUploadJob.jobType}"></c:out></td>
					<td style="padding: 5px;">Trạng thái cá thể hóa</td>
					<td style="padding: 5px;">:</td>
					<td style="padding: 5px;"><c:out
							value="${nicUploadJob.persoRegisterStatus}"></c:out></td>
				</tr>
				<tr>
					<td style="padding: 5px;">Mã giao dịch</td>
					<td style="padding: 5px;">:</td>
					<td style="padding: 5px;"><c:out
							value="${nicUploadJob.transactionId}"></c:out></td>
					<td style="padding: 5px;">Có lỗi công việc</td>
					<td style="padding: 5px;">:</td>
					<td style="padding: 5px;"><c:choose>
							<c:when test="${nicUploadJob.nicJobErrorFlag}">
								<img src=<c:url value='/resources/images/tick.gif'/> height="16"
									width="16" />
							</c:when>
							<c:otherwise>
								<img src=<c:url value='/resources/images/cross.png'/>
									height="16" width="16" />
							</c:otherwise>
						</c:choose></td>
				</tr>
				<tr>
					<td style="padding: 5px;">Trạng thái kiểm tra vân tay</td>
					<td style="padding: 5px;">:</td>
					<td style="padding: 5px;"><c:out
							value="${nicUploadJob.afisCheckStatus}"></c:out></td>
					<td style="padding: 5px;">Thời gian bắt đầu công việc</td>
					<td style="padding: 5px;">:</td>
					<td style="padding: 5px;"><c:out
							value="${nicUploadJob.jobStartDateTime}"></c:out></td>
				</tr>

				<tr>
					<td style="padding: 5px;">Trạng thái đăng ký vân tay</td>
					<td style="padding: 5px;">:</td>
					<td style="padding: 5px;"><c:out
							value="${nicUploadJob.afisRegisterStatus}"></c:out></td>
					<td style="padding: 5px;">Thời gian kết thúc công việc</td>
					<td style="padding: 5px;">:</td>
					<td style="padding: 5px;"><c:out
							value="${nicUploadJob.jobEndDateTime}"></c:out></td>
				</tr>

				<tr>
					<td style="padding: 5px;">Trạng thái xác minh vân tay</td>
					<td style="padding: 5px;">:</td>
					<td style="padding: 5px;"><c:out
							value="${nicUploadJob.afisVerifyStatus}"></c:out></td>
					<td style="padding: 5px;">Thời gian khởi tạo</td>
					<td style="padding: 5px;">:</td>
					<td style="padding: 5px;"><c:out
							value="${nicUploadJob.createDateTime}"></c:out></td>
				</tr>



				<tr>
					<td style="padding: 5px;">Loai điều tra</td>
					<td style="padding: 5px;">:</td>
					<td style="padding: 5px;"><c:out
							value="${nicUploadJob.investigationType}"></c:out></td>
					<td style="padding: 5px;">Thời gian cập nhật</td>
					<td style="padding: 5px;">:</td>
					<td style="padding: 5px;"><c:out
							value="${nicUploadJob.updateDateTime}"></c:out></td>
				</tr>

				<tr>
					<td style="padding: 5px;">Trạng thái điều tra</td>
					<td style="padding: 5px;">:</td>
					<td style="padding: 5px;"><c:out
							value="${nicUploadJob.investigationStatus}"></c:out></td>
					<td style="padding: 5px;">Giai đoạn hiện tại</td>
					<td style="padding: 5px;">:</td>
					<td style="padding: 5px;"><c:out
							value="${nicUploadJob.jobCurrentState}"></c:out></td>
				</tr>
				<tr>
					<td style="padding: 5px;">Cán bộ xử lý</td>
					<td style="padding: 5px;">:</td>
					<td style="padding: 5px;"><c:out
							value="${nicUploadJob.investigationOfficerId}"></c:out></td>
					<td style="padding: 5px;">Trạng thái công việc hiện tại</td>
					<td style="padding: 5px;">:</td>
					<td style="padding: 5px;"><c:out
							value="${nicUploadJob.jobCurrentStatus}"></c:out></td>
				</tr>

				<tr>
					<td style="padding: 5px;">Số lần thử lại</td>
					<td style="padding: 5px;">:</td>
					<td style="padding: 5px;"><c:out
							value="${nicUploadJob.jobReprocessCount}"></c:out></td>
					<td style="padding: 5px;">Công việc hoàn tất</td>
					<td style="padding: 5px;">:</td>
					<td style="padding: 5px;"><c:choose>
							<c:when test="${nicUploadJob.nicJobCompletedFlag}">
								<img src=<c:url value='/resources/images/tick.gif'/> height="16"
									width="16" />
							</c:when>
							<c:otherwise>
								<img src=<c:url value='/resources/images/cross.png'/>
									height="16" width="16" />
							</c:otherwise>
						</c:choose></td>
				</tr>
			</table>
		</c:when>
		<c:otherwise>
			Chi tiết công việc không được tìm thấy.
		</c:otherwise>
	</c:choose>


	<br />
	<br />

	<table style="width: 100%;">
		<tr>
			<td align="center" style="padding: 20px; text-align: right;"><c:choose>
					<c:when test="${showErrorBtn}">
						<input type="button" class="button_grey" id="showErrorBtn_btn"
							value="Show Error Message" />

						<input type="button" class="button_grey" id="hideErrorBtn_btn"
							value="Hide Error Message" style="display: none;" />

					</c:when>
					<c:otherwise>
						&nbsp;
					</c:otherwise>
				</c:choose> 
				<c:if test="${showRetryBtn}">
					<input type="button" class="button_grey" id="retryTrans"
						value="Retry" />
				</c:if>
				<!--  <input type="button" class="button_grey" id="jobInfoClose_btn"
				value="Close" /> --></td>
		</tr>
	</table>

	<c:if test="${showErrorBtn}">
		<fieldset style="display: none;" id="jobErrorMessageInfo">
			<legend>Thông báo lỗi</legend>
			<div class="expander">

				<c:choose>
					<c:when test="${not empty nicUploadJob.errorDesc}">
						<c:out value="${nicUploadJob.errorDesc}"></c:out>
					</c:when>
					<c:otherwise>
				Không tìm thấy thông báo lỗi để hiển thị.
		</c:otherwise>
				</c:choose>
			</div>

		</fieldset>
	</c:if>

</form:form>

