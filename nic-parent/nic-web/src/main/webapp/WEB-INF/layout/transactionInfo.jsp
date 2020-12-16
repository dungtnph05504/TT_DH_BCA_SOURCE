<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<style>
.subjectsmallFontRed {
	font-family: Verdana, Arial, Helvetica, sans-serif;
	text-decoration: none;
	font-size: 11px;
	color: #FF0A0A;
	line-height: normal;
	font-weight: bold;
	text-transform: lower;
	text-align: left;
	height: 25px;
	text-indent: 5px;
}

.description_slimplain_red {
	font-size: 11px;
	font-family: Verdana, Arial, Helvetica, sans-serif;
	text-decoration: none;
	font-weight: normal;
	height: 20px;
	color: #FF0A0A;
}
</style>
<script type="text/javascript">
	$(function() {
		$("#dialog-TransactionLog").dialog({
			modal : true,
			autoOpen : false,
			width : 1200,
			height : 300,
			closeOnEscape : false,
			show : {
				effect : "blind",
				duration : 1000
			},
			hide : {
				//effect : "explode",
				duration : 1000
			}
		});
		$("#viewTransLog").click(function() {
			$("#dialog-TransactionLog").dialog("open");
		});
	});
</script>
<table
	style="border: 1px SOLID; border-radius: 10px; width: 1260px; background-color: #DDDDDD; table-layout: fixed;">
	<tr>
		<c:choose>
			<c:when
				test="${newRegistration.transactionStage == 'PEND_SUPERVISOR'}">
				<td style="padding-left: 5px; text-align: left">
					<div style="height: 15px" id="sub_heading_new">TRANSACTION
						INFORMATION</div>
				</td>
				<td colspan="2" class="description_slimplain_red"
					style="padding-left: 5px; text-align: left">Escalation
					Reason:&nbsp;<span class='subjectsmallFontRed'
					id="escalationReason">${newRegistration.transLog.reason}</span>
				</td>
				<td colspan="4" class="description_slimplain_red"
					style="text-align: left">Remarks:&nbsp;<span
					class='subjectsmallFontRed' id="escalationRemarks">${newRegistration.transLog.remarks}</span>
				</td>
			</c:when>
			<c:otherwise>
				<td colspan="5">
					<div style="height: 15px" id="sub_heading_new">TRANSACTION INFORMATION</div>
					<div align="right">
						<input type="button" class=" btn_blue gradient" id="viewTransLog" value="Transaction Log" />
					</div>
				</td>
			</c:otherwise>
		</c:choose>
	</tr>
	<tr>
		<td style="width: 330px; padding-left: 5px; text-align: left"><span
			class='description_slimplain'>Transaction Id:</span>&nbsp;<span
			class='subjectsmall' id="trnasIdSpan">${newRegistration.transactionId}</span>
		</td>
		<td style="width: 250px; text-align: left;"><span
			class='description_slimplain'>Transaction Type:</span>&nbsp;<span
			class='subjectsmall' id="transTypeSpan">${newRegistration.transactionTypeDesc}</span>
		</td>
		<td style="width: 230px; text-align: left;"><span
			class='description_slimplain'>SubType:</span>&nbsp;<span
			class='subjectsmall' id="transSubTypeSpan">${newRegistration.transactionSubTypeDesc}</span>
		</td>
		<td style="width: 230px; text-align: left;"><span
			class='description_slimplain'>Date of Application:</span><span
			class='subjectsmall'>&nbsp;${newRegistration.trnasactionStartDate}</span>
		</td>
		<td style="width: 220px; text-align: left"><span
			class='description_slimplain'>Current Stage:</span><span
			class='subjectsmall'>&nbsp;${newRegistration.transactionStageDesc}</span>
		</td>
	</tr>
</table>
<div id="dialog-TransactionLog" title="Transaction Log" >
	<table>
		<tr>
			<td class="subjectsmall" width="250px">DATE/TIME</td>
			<td class="subjectsmall" width="250px">SITE</td>
			<td class="subjectsmall" width="250px">COUNTER/SERVER</td>
			<td class="subjectsmall" width="250px">USER ID</td>
			<td class="subjectsmall" width="250px">STAGE</td>
			<td class="subjectsmall" width="250px">ACTION</td>
			<td class="subjectsmall" width="250px">REMARKS</td>
		</tr>
		<c:forEach var="transLogItem"
			items="${newRegistration.transactionLogList}">
			<tr>
				<td width="250px" class='description'>${transLogItem.updateTime}</td>
				<td width="250px" class='description'>${transLogItem.ricSitecode}</td>
				<td width="250px" class='description'>${transLogItem.counterId}</td>
				<td width="250px" class='description'>${transLogItem.userId}</td>
				<td width="250px" class='description'>${transLogItem.transactionStage}</td>
				<td width="250px" class='description'>${transLogItem.transactionStatus}</td>
				<td width="250px" class='description'>${transLogItem.remarks}</td>
			</tr>
		</c:forEach>
	</table>
</div>
