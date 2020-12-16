<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<style type="text/css">
 
#heading_report {
    width: 450px; 
    }
 
</style>

<script type="text/javascript">



$(function(){
});
</script>


	<table style="width: 100%;">
	<tr style="text-align: right;">
		<td>
			&nbsp;
		</td>
	</tr>
	<tr>
		<td>
			<display:table cellspacing="1" cellpadding="0" id="row"
				export="false" class="displayTag" name="list">
				<display:column title="Transaction Id" >
		 			<a href="#" onclick="javaScript:openSyncDetails('${row.transId}','${row.nin}' );"> ${row.transId} </a>
				</display:column>
				<display:column title="NIN" property="nin" />
				<display:column title="Transaction Type" property="jobType" />
				<display:column title="Transaction Status" property="transactionStatus" />
				<display:column title="Reg. Site Name" property="siteName" />
				<display:column title="Retry Times" property="retryTimes"  />
				<display:column title="Latest Status" property="latestStatus" />
				<display:column title="Error Message" property="errorDesc" maxLength="30" />
				<display:column title="Actions">
					<c:choose>
						<c:when test="${not empty row.latestStatusCode}">
							<input type="button"
								onclick="javaScript:openSyncDetails('${row.transId}','${row.nin}' );"
								class="button_grey" value="View Sync Details" />
								
							<c:if test="${row.latestStatusCode eq '4000'}">
							
							&nbsp;
								<input type="button"
								onclick="javaScript:retryDetailedTrans('${row.dataSyncId}','${row.transId}');"
								class="button_grey" value="Retry" />
							</c:if>
						</c:when>
						<c:otherwise>
							&nbsp;
						</c:otherwise>
					</c:choose>
				</display:column>
			</display:table>
		</td>
	</tr>
	<tr>
		<td>
			&nbsp;
		</td>
	</tr>
	<tr>
		<td style="text-align: right;">
			<input style="text-align: right;" type="button" id="refresh" onclick="javaScript:refreshViewDetails();" class="button_grey" value="Refresh" />
			<c:if test="${not empty list }">
				<input style="text-align: right;" type="button" id="generatePDF" onclick="javaScript:generateViewDetailsPDF();" class="button_grey" value="Generate PDF" />
			</c:if>
			<input style="text-align: right;" type="button" id="back" onclick="javaScript:goPrevPage();" class="button_grey" value="Back" />
		</td>
	</tr>
</table>
