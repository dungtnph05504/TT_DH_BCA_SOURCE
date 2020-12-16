<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:url var="showExcelResult" value="/servlet/dataSyncMonitorController/generatePDF" />
<c:url var="dataSyncDetailsUrl" value="/servlet/dataSyncMonitorController/getDataSyncDetails" />

<script type="text/javascript">

$(function(){
	document.getElementById('jid').value = '${jobId}';
	document.getElementById('txnId').value = '${transId}';
});
</script>


	<table style="width: 100%;">
	<tr>
		<td>
			<b> Transaction Id:</b>
			<span id="transIdVal"><c:out value="${transId}"/></span>
		</td>
		<td>
			<b> NIN:</b>
			<span id="ninVal"><c:out value="${nin}"/></span>
		</td>
		<td align="right" width="20px">
			&nbsp;
		</td>
	</tr>
	<tr>
		<td colspan="3">
			&nbsp;
		</td>
	</tr>
	<tr>
		<td colspan="3">
			<display:table cellspacing="1" cellpadding="0" id="row"
				export="false" class="displayTag" name="list">
				<display:column title="Created Date" property="createDate" />
				<display:column title="Updated Date" property="updateDate" />
				<display:column title="Sync Mode" property="syncMode" />
				<display:column title="Status" property="latestStatus" />
				<display:column title="Retry Times" property="retryTimes" />
				<display:column title="Error Message" property="errorDesc" />
			</display:table>
		</td>
	</tr>
	<tr>
		<td colspan="3">
			&nbsp;
		</td>
	</tr>
	<tr>
		<td style="text-align: right;" colspan="2">
			<input style="text-align: right;" type="button" id="refresh" onclick="javaScript:refreshReqAndStatusDetails('${transId}','${nin}');" class="button_grey" value="Refresh" />
			
			<c:if test="${isRetryAvai eq 'Y' }">
				<input style="text-align: right;" type="button" id="retryReqAndStatus" onclick="javaScript:retryReqAndStatusTrans('${retryDataSyncId}','${transId}');" class="button_grey" value="Retry" />
			</c:if>
			
			<input style="text-align: right;" type="button" id="closeDetDia" onclick="javaScript:closeDetDia();" class="button_grey" value="Close" />
			
			
		</td>
	</tr>
</table>
