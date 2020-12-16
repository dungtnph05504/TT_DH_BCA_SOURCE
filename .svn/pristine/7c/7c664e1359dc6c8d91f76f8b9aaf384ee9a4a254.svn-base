<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="col" uri="colfunction"%>
<c:url var="cancelPassportUrl" value="/servlet/transactionEnquiry/cancelPassport" />

<script type="text/javascript">
$(function(){

});


function onCancel(passportNo)
{
	var txnId = $("#txnId").val();
	$("#cancelTxnId").val(txnId);
	$("#cancelPassportNo").val(passportNo);

	$("#dialog-cancel-remarks").dialog('open');
}

$("#dialog-cancel-remarks").dialog({
    autoOpen  : false,
    modal     : true,
	width : 600,
	height : 350,
	resizable : false,
    title     : "Hủy nhận xét hộ chiếu",
    buttons   : {
              'OK' : function() {
      			var txnId = $("#cancelTxnId").val();
      			var passportNo = $("#cancelPassportNo").val();
      			var remarks =  $("#txtCancelRemarks").val(); 
				if (remarks == "" || $.trim(remarks).length == 0) {
					$("#retrydialog-confirm").dialog('option','title', 'Warning');
					$("#retrydialog-confirm").html('Nhận xét không được bỏ trống !');
					$("#retrydialog-confirm").dialog('open');
					return;
				} else{
					$(this).dialog('close');
	    			$('.modal').show();
	    			$.ajax({
	    				type : "POST",
	    				url : '${cancelPassportUrl}',
	    				data : {
							transactionId : txnId,
							remarks: remarks,
							passportNo: passportNo
						}, 
	    				success : function(data) {
	    					
	    					if (data == 'success') {
	    						$("#dialog-approve").dialog('close');
	    						callDialog(txnId);
	    						$("#retrydialog-confirm").dialog('option','title', 'Status');
	    						$("#retrydialog-confirm").html('Hủy thành công hộ chiếu số: '+ passportNo);
	    						$("#retrydialog-confirm").dialog('open');
	    					} else if (data == 'fail') {
	    						$("#retrydialog-confirm").dialog('option', 'title', 'Status');
	    						$("#retrydialog-confirm").html('Không thể hủy hộ chiếu số: ' + passportNo);
	    						$("#retrydialog-confirm").dialog('open');
	    					}
	    					$('.modal').hide();
	    				},
	    				error : function(e) {
	    					$('#jobQueueDetails').html(
	    							'Có lỗi xảy ra trong khi hủy hộ chiếu số: ' + passportNo);
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

/* 
function onCancel(passportNo)
{
	var txnId = $("#txnId").val();

	$('.modal').show();
	$.ajax({
		type : "GET",
		url : '${cancelPassportUrl}/' + passportNo + '/' + txnId,
		data : $("#nicEnqDetailsForm").serializeArray(),
		success : function(data) {
			if (data == 'success') {
				$("#retrydialog-confirm").dialog('option','title', 'Status');
				$("#retrydialog-confirm").html('Successfully Cancel the Passport: '+ passportNo);
				$("#retrydialog-confirm").dialog('open');
			} else if (data == 'fail') {
				$("#retrydialog-confirm").dialog('option', 'title', 'Status');
				$("#retrydialog-confirm").html('Failed to Cancel the Passport: ' + passportNo);
				$("#retrydialog-confirm").dialog('open');
			}
			$('.modal').hide();
		},
		error : function(e) {
			$('#jobQueueDetails').html(
					'Error occurred while Cancel the Passport:' + passportNo);
			$('.modal').hide();
		}
	});
}    */
</script>

<c:choose>
	<c:when test="${not empty passportInfolist}">
		<c:forEach var="passportInfo" items="${passportInfolist}">
		<b style="color:blue" >Số hộ chiếu: <c:out value="${passportInfo.passportNo}"></c:out></b>
			<table style="width: 100%; font-size: 12px;" cellspacing="0"
				cellpadding="0">
				<tr>
					<td style="padding: 5px; font-weight: bold;">Mã giao dịch</td>
					<td style="padding: 5px;">:</td>
					<td style="padding: 5px;"><c:out
							value="${passportInfo.transactionId}"></c:out></td>
					<td style="padding: 5px; font-weight: bold;">Số hộ chiếu</td>
					<td style="padding: 5px;">:</td>
					<td style="padding: 5px;"><c:out
							value="${passportInfo.passportNo}"></c:out></td>
				</tr>

				<tr>
					<td style="padding: 5px; font-weight: bold;">Mã công văn</td>
					<td style="padding: 5px;">:</td>
					<td style="padding: 5px;"><c:out
							value="${passportInfo.dispatchId}"></c:out></td>
					<td style="padding: 5px; font-weight: bold;">Mã gói bàn giao</td>
					<td style="padding: 5px;">:</td>
					<td style="padding: 5px;"><c:out
							value="${passportInfo.packageId}"></c:out></td>
				</tr>
				<tr>
					<td style="padding: 5px; font-weight: bold;">Ngày tạo công văn
					</td>
					<td style="padding: 5px;">:</td>
					<td style="padding: 5px;"><c:out
							value="${passportInfo.dispatchDatetime}"></c:out></td>
					<td style="padding: 5px; font-weight: bold;">Ngày tạo gói bàn giao</td>
					<td style="padding: 5px;">:</td>
					<td style="padding: 5px;"><c:out
							value="${passportInfo.packageDatetime}"></c:out></td>
				</tr>

				<tr>
					<td style="padding: 5px; font-weight: bold;">Số serial chip</td>
					<td style="padding: 5px;">:</td>
					<td style="padding: 5px;"><c:out
							value="${passportInfo.chipSerialNo}"></c:out></td>
					<td style="padding: 5px; font-weight: bold;">Trung tâm in</td>
					<td style="padding: 5px;">:</td>
					<td style="padding: 5px;"><c:out
							value="${passportInfo.printingSite}"></c:out></td>
				</tr>

				<tr>
					<td style="padding: 5px; font-weight: bold;">Ngày phát hành</td>
					<td style="padding: 5px;">:</td>
					<td style="padding: 5px;"><c:out
							value="${passportInfo.dateOfIssue}"></c:out></td>
					<td style="padding: 5px; font-weight: bold;">Ngày hết hạn</td>
					<td style="padding: 5px;">:</td>
					<td style="padding: 5px;"><c:out
							value="${passportInfo.dateOfExpiry}"></c:out></td>
				</tr>
				<tr>
					<td style="padding: 5px; font-weight: bold;">Trạng thái hộ chiếu</td>
					<td style="padding: 5px;">:</td>
					<td style="padding: 5px;"><c:out
							value="${passportInfo.status}"></c:out></td>
					<td style="padding: 5px; font-weight: bold;">Ngày cập nhật trạng thái</td>
					<td style="padding: 5px;">:</td>
					<td style="padding: 5px;"><c:out
							value="${passportInfo.statusUpdateTime}"></c:out></td>

				</tr>
				<tr>
					<td style="padding: 5px; font-weight: bold;">Ngày tạo</td>
					<td style="padding: 5px;">:</td>
					<td style="padding: 5px;"><c:out
							value="${passportInfo.createDatetime}"></c:out></td>
					<td style="padding: 5px; font-weight: bold;">Ngày cập nhật</td>
					<td style="padding: 5px;">:</td>
					<td style="padding: 5px;"><c:out
							value="${passportInfo.updateDatetime}"></c:out></td>

				</tr>
				<tr>
					<td style="padding: 5px; font-weight: bold;">Trạng thái đồng bộ</td>
					<td style="padding: 5px;">:</td>
					<td style="padding: 5px;"><c:out
							value="${passportInfo.syncStatus}"></c:out></td>
					<td style="padding: 5px; font-weight: bold;">Lần cuối cùng đồng bộ</td>
					<td style="padding: 5px;">:</td>
					<td style="padding: 5px;"><c:out
							value="${passportInfo.lastSyncDatetime}"></c:out></td>

				</tr>
				<tr>
					<td colspan="6" valign="top" align="center"
						style="text-align: right; padding-top: 10px;">
						<c:if test="${not empty sessionScope.userSession}">
							<c:if test="${passportInfo.isReadyForCancel()}">
								<c:if test="${col:isPreviledged('NIC_TXNENQ_VIEW_CANCEL_PPT',sessionScope.userSession)}">
									<input type="button" class="button_grey" id="cancel" value="Cancel Passport"  onclick="onCancel('${passportInfo.passportNo}')"/>
								</c:if>	
							</c:if> 
						</c:if> 
					</td>
				</tr>
			</table>
		</c:forEach>
	</c:when>
	<c:otherwise>
		<tr>
			<td colspan="2">Không tìm thấy thông tin hộ chiếu để hiển thị</td>
		</tr>
		<!-- <tr>
							<td colspan="2" height="220px">&nbsp;</td>
						</tr> -->
	</c:otherwise>
</c:choose>
<input type="hidden" id="cancelPassportNo" name="cancelPassportNo">
<input type="hidden" id="cancelTxnId" name="cancelTxnId">
<div id="dialog-cancel-remarks" style="display: none;" >
	Ghi nhận xét:<br><textarea name="txtCancelRemarks" id="txtCancelRemarks" cols="60" rows="8" />
</div>